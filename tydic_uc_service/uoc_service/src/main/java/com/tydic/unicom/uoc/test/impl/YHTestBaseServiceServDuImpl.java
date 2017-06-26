package com.tydic.unicom.uoc.test.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tydic.uda.service.S;
import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.uoc.base.uochis.po.InfoOfrOrderHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoOfrOrderHisServ;
import com.tydic.unicom.uoc.business.common.interfaces.TimingTaskServiceServDu;
import com.tydic.unicom.uoc.common.vo.WebRedisVo;
import com.tydic.unicom.uoc.service.activiti.interfaces.ProcessDefinitionServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.ProcessHistoryServDu;
import com.tydic.unicom.uoc.service.common.interfaces.FileServiceServDu;
import com.tydic.unicom.uoc.service.common.interfaces.GetIdServDu;
import com.tydic.unicom.uoc.service.common.interfaces.PushMsgToWebAppServDu;
import com.tydic.unicom.uoc.service.es.interfaces.EsServiceServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderBaseDu;
import com.tydic.unicom.uoc.test.interfaces.YHTestBaseServiceServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;

public class YHTestBaseServiceServDuImpl implements YHTestBaseServiceServDu{
	
	private static Logger logger = Logger.getLogger(YHTestBaseServiceServDuImpl.class);
	
	@Autowired
	private ProcessHistoryServDu processHistoryServDu;
	
	@Autowired
	private ProcessDefinitionServDu processDefinitionServDu;
	
	@Autowired
	private GetIdServDu getIdServDu;
	
	@Autowired
	private InfoOfrOrderHisServ infoOfrOrderHisServ;
	
	@Autowired
	private FileServiceServDu fileServiceServDu;
	
	@Autowired
	private EsServiceServDu esServiceServDu;
	
	@Autowired
	private RedisServiceServ redisServiceServ;
	
	@Autowired
	private PushMsgToWebAppServDu pushMsgToWebAppServDu;
	
	@Autowired
	private InfoServiceOrderBaseDu infoServiceOrderBaseDu;
	
	@Autowired
	private TimingTaskServiceServDu timingTaskServiceServDu;
	
	@Override
	public Map<String,Object> testAll(String fileUrl) throws Exception{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String indexName = "uoc_test1";
		String typeName = "01-18";
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		Map<String,Object> map2 = new HashMap<String,Object>();
		Map<String,Object> mapVo = new HashMap<String,Object>();
		Map<String,Object> mapVo1 = new HashMap<String,Object>();
		Map<String,Object> mapVo2 = new HashMap<String,Object>();
		List<Map<String,Object>> listsub1 = new ArrayList<Map<String,Object>>();
		mapVo.put("area_code", "cq");
		mapVo.put("ext_field_2", "test_field_2 value");
		listsub1.add(mapVo);
		
		mapVo1.put("area_code", "cq");
		mapVo1.put("ext_field_2", "test_field_3 value");
		mapVo2.put("area_code", "cq");
		mapVo2.put("ext_field_2", "test_field_4 value");
		List<Map<String,Object>> listsub = new ArrayList<Map<String,Object>>();
		listsub.add(mapVo1);
		listsub.add(mapVo2);
		//第一条数据
		map1.put("id", "3333333333333333333");//一定要有
		map1.put("sale_order_no", "3333333333333333333");//一定要有
		map1.put("info_sale_ext", listsub1);//动态
		
		
		//第二条数据
		map2.put("id", "4444444444444444444");
		map2.put("sale_order_no", "4444444444444444444");
		map2.put("info_sale_ext", listsub);
		
		list.add(map1);
		list.add(map2);
		boolean isok = esServiceServDu.batchInsert(indexName, typeName, list);
		resultMap.put("result", isok);
		return resultMap;
	}

	@Override
	public UocMessage testProcessHistoryServDu(){
		String processInstanceId = "127501";
		String orderNo = "454645";
			UocMessage uocMessage = new UocMessage();
			try {
				uocMessage = processHistoryServDu.findProcessHistoryInfo(processInstanceId, orderNo);
				return uocMessage;
			} catch (Exception e) {
				e.printStackTrace();
				uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
				uocMessage.setContent("获取流程实例历史信息服务接口异常");
				return uocMessage;
			}
	}

	@Override
	public UocMessage testProcessDefinitionServDu() {
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = processDefinitionServDu.findProcessDefinition();
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("获取流程定义信息服务接口异常");
			return uocMessage;
		}
	}

	@Override
	public UocMessage testGetIdServDu(String serv_order_no, String order_type,String jsession_id) {
		UocMessage result = new UocMessage();
		try {
			result = timingTaskServiceServDu.cancleOrder();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			result.setContent("异常");
			return result;
		}
		/*try {
			result = pushMsgToWebAppServDu.pushMsgToWebAppByRedis(operNo, busTpye);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			result.setContent("异常");
			return result;
		}*/
	}

	@Override
	public UocMessage testException() throws Exception{
		logger.info("==========testException==============");
		InfoOfrOrderHisPo infoOfrOrderHisPo = new InfoOfrOrderHisPo(); 
		infoOfrOrderHisPo.setAccept_depart_name("11");
		infoOfrOrderHisPo.setAccept_depart_no("11");
		infoOfrOrderHisPo.setAccept_oper_name("11");
		infoOfrOrderHisPo.setAccept_oper_no("11");
		infoOfrOrderHisPo.setAccept_time("2016-11-11 00:00:00");
		infoOfrOrderHisPo.setArea_code("11");
		infoOfrOrderHisPo.setCancle_flag("11");
		infoOfrOrderHisPo.setCreate_time("2016-11-11 00:00:00");
		infoOfrOrderHisPo.setEssKey("1");
		infoOfrOrderHisPo.setFinish_time("2016-12-11 00:00:00");
		infoOfrOrderHisPo.setOfr_id("11");
		infoOfrOrderHisPo.setOfr_name("11");
		infoOfrOrderHisPo.setOfr_order_no("11");
		infoOfrOrderHisPo.setOrd_mod_code("11");
		infoOfrOrderHisPo.setOrder_state("11");
		infoOfrOrderHisPo.setPart_month("11");
		infoOfrOrderHisPo.setProvince_code("11");
		infoOfrOrderHisPo.setSale_order_no("11");
		infoOfrOrderHisServ.createInfoOfrOrderHisPo(infoOfrOrderHisPo);
		String str ="222";
		if(!str.equals("0000")){
			UocMessage message = new UocMessage();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("业务异常");
			throw new UocException(message);
		}
		UocMessage uocmessage = new UocMessage();
		uocmessage.setRespCode(RespCodeContents.SUCCESS);
		uocmessage.setContent("ok");
		return uocmessage;
	}
	
}
