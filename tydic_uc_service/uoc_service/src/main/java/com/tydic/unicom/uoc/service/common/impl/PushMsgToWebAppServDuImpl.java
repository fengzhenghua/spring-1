package com.tydic.unicom.uoc.service.common.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.common.vo.WebRedisVo;
import com.tydic.unicom.uoc.service.common.interfaces.PushMsgToWebAppServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;
@Service("PushMsgToWebAppServDu")
public class PushMsgToWebAppServDuImpl implements PushMsgToWebAppServDu{

	private static Logger logger = Logger.getLogger(PushMsgToWebAppServDuImpl.class);
	
	@Override
	public UocMessage pushMsgToWebAppByRedis(String operNo, String msgTpye) throws Exception {
		UocMessage uocMessage = new UocMessage();
		//去redis查询数据
		String redisQueryKey = "uoc_push_msg";
		WebRedisVo queryResult = S.get(WebRedisVo.class).get(redisQueryKey);
		//没有数据
		if(queryResult == null ){
			WebRedisVo addRedisData = new WebRedisVo();
			addRedisData.setId(redisQueryKey);
			Map<String,Object> dataMap = new HashMap<String,Object>();
			JSONObject msgObj = new JSONObject();
			msgObj.put(msgTpye, "1");
			dataMap.put(operNo, msgObj);
			addRedisData.setMap(dataMap);
			String resultKey = (String) S.get(WebRedisVo.class).create(addRedisData);
			if(StringUtils.isEmpty(resultKey)){
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("向前台推送消息失败");
				return uocMessage;
			}
		}
		else{
			Map<String,Object> dataMap = queryResult.getMap();
			JSONObject msgObj = (JSONObject) dataMap.get(operNo);
			if(msgObj == null){
				JSONObject msgAddObj = new JSONObject();
				msgAddObj.put(msgTpye, "1");
				dataMap.put(operNo, msgAddObj);
			}
			else{
				if(msgObj.get(msgTpye) != null){
					msgObj.put(msgTpye, String.valueOf(Integer.parseInt(msgObj.getString(msgTpye))+1));
				}
				else{
					msgObj.put(msgTpye, "1");
				}
				dataMap.put(operNo, msgObj);
			}
			queryResult.setMap(dataMap);
			int updateResult = S.get(WebRedisVo.class).update(queryResult);
			if(updateResult != 0){
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("向前台推送消息失败");
				return uocMessage;
			}
		}
		uocMessage.setRespCode(RespCodeContents.SUCCESS);
		uocMessage.setContent("向前台推送消息成功");
		return uocMessage;
	}

}
