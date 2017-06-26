package com.tydic.unicom.uoc.business.order.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.uoc.business.order.service.interfaces.OrderDataBakServDu;
import com.tydic.unicom.uoc.service.backup.interfaces.BackupDataServDu;
import com.tydic.unicom.uoc.service.common.impl.ToolSpring;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;

public class OrderDataBakServDuImpl implements OrderDataBakServDu{

	Logger logger = Logger.getLogger(OrderDataBakServDuImpl.class);

	@Autowired
	private BackupDataServDu backupDataServDu;

	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;
	
	public UocMessage createOrderDataBakupForActivemq(String  json_in) throws Exception{
		
		if(jsonToBeanServDu == null){
			logger.info("====================jsonToBeanServDu is null============================"+jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		Map<String, Object> map =jsonToBeanServDu.jsonToMap(json_in);
		
		String order_no=(String) map.get("order_no");
		String oper_type=(String) map.get("oper_type");
		UocMessage message=createOrderDataBakup(order_no,oper_type);
		return message;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public UocMessage createOrderDataBakup(String order_no,String oper_type)throws Exception{
		UocMessage message=new UocMessage();
		if(backupDataServDu == null){
			logger.info("====================backupDataServDu is null============================"+backupDataServDu);
			backupDataServDu = (BackupDataServDu) ToolSpring.getBean("BackupDataServDu");
		}
		//按照订单号查询备份数据
		UocMessage queryMessage=backupDataServDu.queryRawData(order_no, oper_type);
		if(!"0000".equals(queryMessage.getRespCode().toString())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("查询备份数据失败");
			return message;
		}

		//写入历史表
		Map<String, Object> dataMap=(Map<String, Object>) queryMessage.getArgs().get("backData");

		if(dataMap!=null){
			UocMessage insertMessage=backupDataServDu.insertRawData(dataMap, oper_type);
			if(!"0000".equals(insertMessage.getRespCode().toString())){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("写入历史表失败");
				return message;
			}
		}else{
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("需要备份的书为空");
			return message;
		}

		//删除原有数据
		UocMessage deleteMessage=backupDataServDu.deleteRawData(order_no, oper_type,dataMap);
		if(!"0000".equals(deleteMessage.getRespCode().toString())){
			logger.error("备份数据删除原有数据失败");
			//抛出业务异常
			UocMessage uocExceptionMsg = new UocMessage();
			uocExceptionMsg.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocExceptionMsg.setContent("备份数据删除原有数据失败");
			throw new UocException(uocExceptionMsg);
		}

		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("订单数据备份成功");
		return message;
	}
}
