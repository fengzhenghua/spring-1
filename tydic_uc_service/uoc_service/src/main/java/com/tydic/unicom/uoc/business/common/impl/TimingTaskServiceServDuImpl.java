package com.tydic.unicom.uoc.business.common.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskInstPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskInstServ;
import com.tydic.unicom.uoc.business.common.interfaces.TimingTaskServiceServDu;
import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.business.order.sale.interfaces.InfoSaleOrderBusinessServDu;
import com.tydic.unicom.uoc.service.common.vo.PropertiesParamVo;
import com.tydic.unicom.uoc.service.task.interfaces.GrantServiceServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class TimingTaskServiceServDuImpl implements TimingTaskServiceServDu{

	private static Logger logger = Logger.getLogger(TimingTaskServiceServDuImpl.class);
	
	@Autowired
	private ProcInstTaskInstServ procInstTaskInstServ;
	
	@Autowired
	private InfoSaleOrderBusinessServDu infoSaleOrderBusinessServDu;
	
	@Autowired
	private GrantServiceServDu grantServiceServDu;
	
	@Autowired
	@Qualifier("propertiesParamVo")
	private PropertiesParamVo propertiesParamVo;
	
	@Override
	public UocMessage cancleOrder() throws Exception {
		logger.info("=================不激活25天自动撤单===================");
		UocMessage uocMessage = new UocMessage();
		int successnTime = 0;
		int failTime = 0;
		String cancleOperNo = propertiesParamVo.getCancel_oper_no();
		ProcInstTaskInstPo procInstTaskInstPoQuery = new ProcInstTaskInstPo();
		procInstTaskInstPoQuery.setTache_code("S00017");
		procInstTaskInstPoQuery.setTask_state("100");
		List<ProcInstTaskInstPo> list = procInstTaskInstServ.queryTaskInstForCancleOrder(procInstTaskInstPoQuery);
		if(list != null && list.size()>0){
			for(int i=0;i<list.size();i++){
				ParaVo cancleOrderParaVo = new ParaVo();
				cancleOrderParaVo.setJsession_id(cancleOperNo);
				cancleOrderParaVo.setOrder_no(list.get(i).getOrder_no());
				cancleOrderParaVo.setOrder_type(list.get(i).getOrder_type());
				UocMessage cancleOrderResult = infoSaleOrderBusinessServDu.cancelOrder(cancleOrderParaVo);
				if(RespCodeContents.SUCCESS.equals(cancleOrderResult.getRespCode())){
					successnTime++;
				}
				else{
					failTime++;
				}
			}
		}
		logger.info("===========不激活25天自动撤单完成，撤单成功"+successnTime+"条，撤单失败"+failTime+"条==========");
		uocMessage.setRespCode(RespCodeContents.SUCCESS);
		uocMessage.setContent("不激活25天自动撤单完成，撤单成功"+successnTime+"条，撤单失败"+failTime+"条");
		return uocMessage;
	}

	@Override
	public UocMessage syncCardCompare() throws Exception {
		logger.info("==================机卡比对=====================");
		UocMessage uocMessage = new UocMessage();
		uocMessage = grantServiceServDu.cardCompare();
		logger.info("=================机卡比对结果==============ErrorCode:"+uocMessage.getRespCode()+" Content:"+uocMessage.getContent());
		return uocMessage;
	}

}
