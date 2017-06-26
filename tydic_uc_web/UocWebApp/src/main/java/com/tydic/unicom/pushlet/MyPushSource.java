package com.tydic.unicom.pushlet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.common.vo.WebRedisVo;

import nl.justobjects.pushlet.core.Dispatcher;
import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.EventPullSource;
import nl.justobjects.pushlet.core.Session;
import nl.justobjects.pushlet.core.SessionManager;

public class MyPushSource extends EventPullSource{  

	private static Logger logger = Logger.getLogger(MyPushSource.class);
	@Override
	protected long getSleepTime() {
		return 4000;
	}

	@Override
	protected Event pullEvent() {
		boolean needUpdateRedis = false;
		 Event event = Event.createDataEvent("/pushMsg/uoc");
		 
		 Session[] sessions = SessionManager.getInstance().getSessions();
		 if (sessions.length > 0) { 
			 String operNos = "";
             StringBuilder str = new StringBuilder("");  
             for (int i = 0; i < sessions.length; i++) {  
                 if (sessions[i].getId().equals("guest")) continue;  
                 if (!str.toString().equals("")) str.append(",");   
                 str.append(sessions[i].getId());  
             }
             operNos = str.toString();
             if(!"".equals(operNos)){
            	 logger.info("====================>>>>>>>>前台在线工号："+operNos);
            	 Map<String,Object> map = getRedisData();
            	 if(map != null){
            		 logger.info("=============>>>>>>>>获取到redis内推送的信息");
            		 String[] operNoArray = operNos.split(",");
            		 for(int i=0;i<operNoArray.length;i++){
            			 if(map.containsKey(operNoArray[i])){
            				 needUpdateRedis = true;
            				 event.setField(operNoArray[i],map.get(operNoArray[i]).toString());
            				 map.remove(operNoArray[i]);
            			 }
            		 }
            		 if(needUpdateRedis){
            			updateRedisData(map);
            			needUpdateRedis = false;
            		 }
//            		 updateRedisData(map);
            	 }
             }
		 }
         return event;
	}
	
	public boolean updateRedisData(Map<String,Object> map){
//		ApplicationContext context = new ClassPathXmlApplicationContext("uda.xml");
		WebRedisVo updateWebRedisVo = new WebRedisVo();
		updateWebRedisVo.setId("uoc_push_msg");
		updateWebRedisVo.setMap(map);
		int result = S.get(WebRedisVo.class).update(updateWebRedisVo);
		logger.info("====================>>>>>>>>更新redis结果："+result);
		if(result == 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	private Map<String,Object> getRedisData(){
		Map<String,Object> map = new HashMap<String,Object>();
//		ApplicationContext context = new ClassPathXmlApplicationContext("uda.xml");
		WebRedisVo result = S.get(WebRedisVo.class).get("uoc_push_msg");
		if(result == null){
			return null;
		}
		map = result.getMap();
		return map;
	}
}
