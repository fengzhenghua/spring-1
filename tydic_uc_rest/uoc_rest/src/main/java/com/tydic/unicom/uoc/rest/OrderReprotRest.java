package com.tydic.unicom.uoc.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.uoc.business.common.interfaces.EsReportDataServDu;
import com.tydic.unicom.uoc.business.common.vo.ServOrderListESInVo;
import com.tydic.unicom.uoc.business.common.vo.ServOrderListESOutVo;
import com.tydic.unicom.uoc.business.order.service.interfaces.ServiceOrderExprotServDu;
import com.tydic.unicom.util.ExcelExportorUtil;
import com.tydic.unicom.util.vo.ExcelBean;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

/**
 *
 * <p></p>
 * @author heguoyong 2017年4月13日 下午2:33:32
 * @ClassName OrderReprotRest
 * @Description 订单报表接口
 * @version V1.0
 */
@RestController
@RequestMapping(value = ControllerMappings.EXPROT_ORDER_INFO_REST)
public class OrderReprotRest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ServiceOrderExprotServDu serviceOrderExprotServ;
	@Autowired
	private EsReportDataServDu esReportDataServDu;
	/**
	 *
	 * @author heguoyong 2017年4月14日 上午10:31:25
	 * @Method: exprotAuditOrderInfo
	 * @Description: 导出审核订单
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = UrlsMappings.EXPROT_AUDIT_ORDER_INFO , method = RequestMethod.GET)
	public void exprotAuditOrderInfo(ServOrderListESInVo paramVo,HttpServletRequest request,HttpServletResponse response) {
		if(logger.isDebugEnabled()){
			logger.info("开始导出订单报表，输入参数"+paramVo.toString());
		}
		ExcelBean excel = new ExcelBean();
		try {
			UocMessage message = esReportDataServDu.queryServOrderListES(paramVo);
			Map<String, Object> resMap = message.getArgs();
			List<ServOrderListESOutVo> listOut = new ArrayList<ServOrderListESOutVo>();
			if (resMap != null && resMap.get("info_serv_order_list") != null) {
				Map<String, Object> mapList = (Map<String, Object>) resMap.get("info_serv_order_list");
				listOut = (List<ServOrderListESOutVo>) mapList.get("info_serv_order");
			}
			//处理数据
			List<Object[]> excleList = new ArrayList<Object[]>();
			for (ServOrderListESOutVo vo : listOut) {
				String operNm = "";
				if ("open01".equals(vo.getOper_code())) {
					operNm = "本厅审核流程";
				} else if ("open02".equals(vo.getOper_code())) {
					operNm = "中台集中审核流程";
				}
				String deal_oper_no="";
				deal_oper_no = vo.getDeal_oper_no().equals("Auto") ? "Auto(系统自动处理)" : vo.getDeal_oper_no();

				Object[] temp = new Object[]{
						operNm,
	        			vo.getServ_order_no(),
	        			vo.getAcc_nbr(),
	        			vo.getCreate_time().substring(0,10),
	        			vo.getCreate_time().substring(11,vo.getCreate_time().length()),
	        			vo.getAccpet_depart_name(),
	        			vo.getAccept_oper_no(),
	        			vo.getCust_name(),
	        			vo.getCust_phone(),
	        			vo.getCust_address(),
	        			vo.getCert_no(),
	        			deal_oper_no,
	        			vo.getDeal_date(),
	        			vo.getDeal_time(),
	        			vo.getAudit_state()
	        	};
				excleList.add(temp);
			}

			excel.setName("订单详情");
	        excel.setSheetName("销售订单");
	        excel.setHeaderCenter("销售订单");
	        excel.setTableHeader(new String[]{
	        		"业务名称",
	        		"订单编号",
	        		"服务号码",
	        		"受理日期",
	        		"受理时间",
	        		"受理渠道名称",
	        		"受理工号",
	        		"客户名称",
	        		"联系电话",
	        		"地址",
	        		"证件号码",
	        		"中台受理工号",
	        		"中台受理日期",
	        		"中台受理时间",
	        		"审核状态"
	        });
	        excel.setSheetData(excleList);
			ExcelExportorUtil.exportByExcelBean(excel, response);
		} catch (Exception e) {
			logger.error("导出订单报表失败"+e.getMessage(),e);
		}
	}
	/**
	 *
	 * @author heguoyong 2017年4月14日 下午4:38:10
	 * @Method: queryAuditOrderInfo
	 * @Description: 查询报表
	 */
	@RequestMapping(value = UrlsMappings.QUERY_AUDIT_ORDER_INFO, method = RequestMethod.POST)
	public UocMessage queryAuditOrderInfo(ServOrderListESInVo vo) {
		UocMessage message = new UocMessage();
		if(logger.isDebugEnabled()){
			logger.info("开始查询订单报表，输入参数" + vo.toString());
		}
		try {
			message = esReportDataServDu.queryServOrderListES(vo);
		} catch (Exception e) {
			logger.error("查询审核订单失败" + e.getMessage(), e);
			message.setContent("查询审核订单失败");
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
		}
		return message;
	}
}
