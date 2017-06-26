package com.tydic.unicom.uoc.service.common.impl;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.pub.common.service.interfaces.ActivemqSendServ;
import com.tydic.unicom.uoc.pub.common.service.interfaces.InfoMessageQueueServ;
import com.tydic.unicom.uoc.pub.common.service.po.ActivemqSendPo;
import com.tydic.unicom.uoc.pub.common.service.po.InfoMessageQueuePo;
import com.tydic.unicom.uoc.pub.common.service.po.TaskInst;
import com.tydic.unicom.uoc.service.common.interfaces.ActivemqSendServDu;
import com.tydic.unicom.uoc.service.common.interfaces.GetIdServDu;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;

@Service("ActivemqSendServDu")
public class ActivemqSendServDuImpl implements ActivemqSendServDu{

	Logger logger = Logger.getLogger(ActivemqSendServDuImpl.class);
	
	@Autowired
	private ActivemqSendServ activemqSendServ;
	@Autowired
	private InfoMessageQueueServ infoMessageQueueServ;
	@Autowired
	private GetIdServDu getIdServDu;
	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;
	/**
	 *  BASE0009
	 */
	@Override
	public UocMessage createMessageQueue(ActivemqSendPo activemqSendPo,
			String queue_id) throws Exception {
		UocMessage message =new UocMessage();
		String order_id =activemqSendPo.getOrder_id();
		if(order_id ==null || "".equals(order_id)){		
			logger.info("order_id不能为空");
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("order_id不能为空");
			return message;
		}
		Map<String,String> map =StrUtil.splitStringFromOrderNo(order_id);
		String part_month=map.get("part_month");	
		String area_code=map.get("area_code");
		
		InfoMessageQueuePo po =new InfoMessageQueuePo();
		String id = getIdServDu.getId("createLogId", activemqSendPo.getProvince_code(), "*", "");
		po.setId(id);
		po.setOrder_id(order_id);
		po.setOrder_type(activemqSendPo.getOrder_type());
		po.setArea_code(area_code);
		po.setPart_month(part_month);
		po.setProvince_code(activemqSendPo.getProvince_code());
		po.setQueue_id(queue_id);
		po.setJson_in(activemqSendPo.getJson_in());
		po.setService_code(activemqSendPo.getService_code());
		TaskInst taskInst =activemqSendPo.getTaskInst();
		String task_inst_json  ="";
		if(taskInst !=null){
			JSONObject json = JSONObject.fromObject(taskInst);
			task_inst_json =json.toString();
		}
		po.setTask_inst_object_json(task_inst_json);
		Boolean res =infoMessageQueueServ.createInfoMessageQueue(po);
		if(!res){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("写入消息队列中间表失败");
			throw new UocException(message);
		}
		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("写入消息队列中间表成功");
		return message;
	}
	/**
	 *  BASE0033
	 */
	@Override
	public UocMessage sendMessage(ActivemqSendPo activemqSendPo, String queue_id)
			throws Exception {
		return activemqSendServ.SendMessage(activemqSendPo, queue_id);
	}

}
