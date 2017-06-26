package com.tydic.unicom.uoc.business.common.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tydic.unicom.uoc.business.common.interfaces.ActivemqServDu;
import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.pub.common.service.interfaces.InfoMessageQueueHisServ;
import com.tydic.unicom.uoc.pub.common.service.interfaces.InfoMessageQueueServ;
import com.tydic.unicom.uoc.pub.common.service.po.ActivemqSendPo;
import com.tydic.unicom.uoc.pub.common.service.po.InfoMessageQueueHisPo;
import com.tydic.unicom.uoc.pub.common.service.po.InfoMessageQueuePo;
import com.tydic.unicom.uoc.pub.common.service.po.TaskInst;
import com.tydic.unicom.uoc.service.common.interfaces.ActivemqSendServDu;
import com.tydic.unicom.uoc.service.common.vo.PropertiesParamVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;

public class ActivemqServDuImpl implements ActivemqServDu {

	Logger logger = Logger.getLogger(ActivemqServDuImpl.class);
	@Autowired
	private InfoMessageQueueServ infoMessageQueueServ;
	
	@Autowired
	private InfoMessageQueueHisServ infoMessageQueueHisServ;
	
	@Autowired
	@Qualifier("propertiesParamVo")
	private PropertiesParamVo propertiesParamVo;
	
	@Autowired
	private ActivemqSendServDu activemqSendServDu;
	/**
	 * 101	消息队列发送 UOC0072
	 * @throws Exception
	 */
	@Override
	public UocMessage createSendActivemq(ParaVo vo) throws Exception {
		String flag =propertiesParamVo.getActivemqSend();
		if("no".equals(flag)){		
			return null;
		}
		String areaCodeList = propertiesParamVo.getActivemqAreaCodeList();
		if (areaCodeList==null || "".equals(areaCodeList)){
			return null;
		}
		
		UocMessage message =new UocMessage();
		String total_num =vo.getTotal_num(); //空值时取1
		String remainder =vo.getRemainder(); //空值时取0
		if(total_num ==null || "".equals(total_num)){
			total_num ="1";
		}
		if(remainder ==null || "".equals(remainder)){
			remainder ="0";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date=new Date();
		String nowDate=sdf.format(date);
		String month=nowDate.substring(4,6).trim();
		String part_month=Integer.toString(Integer.parseInt(month));//获取当前月份
		Calendar calendar=Calendar.getInstance();//获取当前日历
		calendar.setTime(date);//设置当前时间的日历
		calendar.add(calendar.MONTH, -1);//设置前一个月
		Date lastDate=calendar.getTime();//获取上个月的时间
		String lastMonth =sdf.format(lastDate).substring(4,6).trim();
		String part_month1=Integer.toString(Integer.parseInt(lastMonth));//获取上个月的月份
		
		List<InfoMessageQueuePo> list =new ArrayList<InfoMessageQueuePo>();
		String[]areas = areaCodeList.split(",");
		//当月数据
		for(String area_code:areas){
			InfoMessageQueuePo infoMessageQueue =new InfoMessageQueuePo();
			infoMessageQueue.setTotal_num(total_num);
			infoMessageQueue.setRemainder(remainder);
			infoMessageQueue.setArea_code(area_code);
			infoMessageQueue.setPart_month(part_month);
			List<InfoMessageQueuePo> infoMessageQueues =infoMessageQueueServ.queryAllMessageQueue(infoMessageQueue);
			if(infoMessageQueues !=null && infoMessageQueues.size()>0){
				list.addAll(infoMessageQueues);
			}
		}
		//上月数据
		for(String area_code:areas){
			InfoMessageQueuePo infoMessageQueue =new InfoMessageQueuePo();
			infoMessageQueue.setTotal_num(total_num);
			infoMessageQueue.setRemainder(remainder);
			infoMessageQueue.setArea_code(area_code);
			infoMessageQueue.setPart_month(part_month1);
			List<InfoMessageQueuePo> infoMessageQueues =infoMessageQueueServ.queryAllMessageQueue(infoMessageQueue);
			if(infoMessageQueues !=null && infoMessageQueues.size()>0){
				list.addAll(infoMessageQueues);
			}
		}
		
		
		if(list!=null && list.size()>0){
			for(InfoMessageQueuePo po:list){
				ActivemqSendPo activemqSendPo =new ActivemqSendPo();
				activemqSendPo.setOrder_id(po.getOrder_id());
				activemqSendPo.setOrder_type(po.getOrder_type());
				activemqSendPo.setService_code(po.getService_code());
				activemqSendPo.setJson_in(po.getJson_in());
				String task_inst_obj_json =po.getTask_inst_object_json();
				if(task_inst_obj_json !=null && !"".equals(task_inst_obj_json)){
					JSONObject obj = new JSONObject().fromObject(task_inst_obj_json);
					TaskInst taskInst = (TaskInst)JSONObject.toBean(obj,TaskInst.class);
					activemqSendPo.setTaskInst(taskInst);	
				}
			message	=activemqSendServDu.sendMessage(activemqSendPo, po.getQueue_id());
			 if("0000".equals(message.getRespCode())){
				 boolean b =infoMessageQueueServ.deleteInfoMessageQueueById(po);
				 if(b){
					 InfoMessageQueueHisPo hisPo =new InfoMessageQueueHisPo();
					 BeanUtils.copyProperties(po,hisPo);
					 boolean backup_flag =infoMessageQueueHisServ.createInfoMessageQueueHis(hisPo);	
					 if(!backup_flag){
						 logger.info("-----------发送消息队列成功备份失败--------order_id-"+po.getOrder_id());
						 message.setRespCode(RespCodeContents.SERVICE_FAIL);
						 message.setContent("消息队列中间表备份失败");
						 throw new UocException(message);
					 }
				 }else{
					 logger.info("-----------发送消息队列成功删除原数据失败--------order_id-"+po.getOrder_id());
				 }
			 }else{
				 logger.info("-----------发送消息队列失败-------------order_id-"+po.getOrder_id());
			 }
			}
			
		}
		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("消息队列发送完成");
		return message;
	}

}
