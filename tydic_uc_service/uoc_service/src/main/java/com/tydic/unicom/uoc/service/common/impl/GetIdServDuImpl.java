package com.tydic.unicom.uoc.service.common.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.uoc.base.uoccode.po.SequencesPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.GetIdServ;
import com.tydic.unicom.uoc.service.common.interfaces.GetIdServDu;
import com.tydic.unicom.uoc.service.common.vo.SequencesVo;
import com.tydic.unicom.webUtil.UocMessage;

@Service("GetIdServDu")
public class GetIdServDuImpl implements GetIdServDu{
	
	Logger logger = Logger.getLogger(GetIdServDuImpl.class);
	
	@Autowired
	private GetIdServ getIdServ;
	
	@Autowired
	private RedisServiceServ redisServiceServ;

	@Override
	public String getId(String type, String provinceCode, String areaCode,String orderNo) throws Exception {
		
		logger.info("======================获取系统内部id========================"+type+provinceCode+areaCode);
		String system_id = "";
		String redisKey = "";
		//拼装redis查询key
		if(StringUtils.isEmpty(areaCode)){
			redisKey = "Seq_" + type + "_" + provinceCode + "_*";
		}
		else{
			redisKey = "Seq_" + type + "_" + provinceCode + "_" + areaCode;
		}
		UocMessage redisResultMsg = redisServiceServ.queryDataFromRedis(redisKey);
		if(!"0000".equals(redisResultMsg.getRespCode())){
			logger.info("============从redis获取到数据失败===============");
			SequencesPo SequencesPo = new SequencesPo();
			SequencesPo.setSeq_code(type);
			SequencesPo.setProvince_code(provinceCode);
			if(StringUtils.isEmpty(areaCode)){
				SequencesPo.setArea_code("*");
			}
			else{
				SequencesPo.setArea_code(areaCode);
			}
			logger.info("====================去数据库查询sequences===================");
			SequencesPo sequencesPoResult = getIdServ.querySequencesBySeqCodeAndProvinceCodeAndAreaCode(SequencesPo);
			//拼接数据传递给redis
			RedisData createRedisData = new RedisData();
			createRedisData.setId(redisKey);
			Map<String,Object> redisDataMap = sequencesPoResult.convertThis2Map();
			redisDataMap.put("stepRest", sequencesPoResult.getSeq_tail_step());
			redisDataMap.put("currNumber", sequencesPoResult.getSeq_tail_curr());
			//设置复位标志
			String dateTime = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
			String[] flags = dateTime.split("-");
			String flag = "";
			switch(sequencesPoResult.getSeq_tail_cir_type()){
			case "100": flag = flags[5];break;
			case "101": flag = flags[4];break;
			case "102": flag = flags[3];break;
			case "103": flag = flags[2];break;
			case "105": flag = flags[1];break;
			case "106": flag = flags[0];break;
			case "107": flag = "never";break;
			}
			redisDataMap.put("resetFlag", flag);
			createRedisData.setMap(redisDataMap);
			logger.info("==============向redis缓存数据=============");
			redisServiceServ.createDataToRedis(createRedisData);
			logger.info("==============更新数据库数据=============");
			sequencesPoResult.setSeq_tail_curr(String.valueOf(Integer.parseInt(sequencesPoResult.getSeq_tail_curr())+Integer.parseInt(sequencesPoResult.getSeq_tail_step())));
			getIdServ.updateSequences(sequencesPoResult);
			system_id = getSystemId(redisDataMap,orderNo);
			logger.info("===============获得的系统内部id=============="+system_id);
		}
		else{
			logger.info("================从redis获取到数据成功============");
			RedisData redisData = (RedisData) redisResultMsg.getArgs().get("RedisDataResult");
			Map<String,Object> redisDataMap = redisData.getMap();
			system_id = getSystemId(redisDataMap,orderNo);
			logger.info("===============获得的系统内部id=============="+system_id);
		}
		return system_id;
	}
	
	/**获取系统id*/
	public String getSystemId(Map<String,Object> map,String orderNo) throws Exception{
		String systemId = "";
		String headNum = "";
		String middleNum = "";
		String endNum = "";
		int headNumLength = 0;
		
		if(map.get("seq_head") != null && (!"".equals(map.get("seq_head")))){
			headNum = map.get("seq_head").toString();
		}
		if(!StringUtils.isEmpty(headNum)){
			headNumLength = headNum.length();
		}
		middleNum = getDate(map.get("seq_time_str").toString(),orderNo,headNumLength,Integer.parseInt(map.get("seq_tail_lenth").toString()));

		if("101".equals(map.get("seq_tail_type").toString())){
			logger.info("=============生成序列数=============");
			endNum = GetSequenceNumber(map);
		}
		else if("100".equals(map.get("seq_tail_type").toString())){
			logger.info("=============生成随机数=============");
			endNum = GetRandomNum(Integer.parseInt(map.get("seq_tail_lenth").toString()));
		}
		
		systemId = headNum + middleNum + endNum;
		return systemId;
	}
	
	/**获取指定格式的时间*/
	public String getDate(String dateTimeType,String orderNo,int headNumLength,int endNumLength) throws Exception{
		String middleNum = "";
		if(orderNo == null || orderNo.equals("")){
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat(dateTimeType);
			middleNum = format.format(date);
		}
		else{
			middleNum = orderNo.substring(headNumLength, (orderNo.length()-endNumLength));
		}
		return middleNum;
	}
	
	/**生成序列数*/
	public String GetSequenceNumber(Map<String,Object> map) throws Exception{
		String sequenceNumber = "";
		String dateTime = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		String[] flags = dateTime.split("-");
		String flag = "";
		switch(map.get("seq_tail_cir_type").toString()){
		case "100": flag = flags[5];break;
		case "101": flag = flags[4];break;
		case "102": flag = flags[3];break;
		case "103": flag = flags[2];break;
		case "105": flag = flags[1];break;
		case "106": flag = flags[0];break;
		case "107": flag = "never";break;
		}
		
		//永不复位
		if(flag.equals("never")){
			//总数已用完
			if(Integer.parseInt(map.get("currNumber").toString())>=Integer.parseInt(map.get("seq_tail_end").toString())){
				sequenceNumber = String.valueOf(Integer.parseInt(map.get("seq_tail_begin").toString()+1));
				logger.info("============更新redis============");
				RedisData updateRedisDate = new RedisData();
				updateRedisDate.setId("Seq_"+map.get("seq_code").toString()+"_"+map.get("province_code").toString()+"_"+map.get("area_code").toString());
				Map<String,Object> updateRedisMap = map;
				updateRedisMap.put("seq_tail_curr", String.valueOf(Integer.parseInt(map.get("seq_tail_begin").toString())+Integer.parseInt(map.get("seq_tail_step").toString())));
				updateRedisMap.put("stepRest", String.valueOf(Integer.parseInt(map.get("seq_tail_step").toString())-1));
				updateRedisMap.put("currNumber", sequenceNumber);
				updateRedisDate.setMap(updateRedisMap);
				redisServiceServ.updateDataToRedis(updateRedisDate);
				logger.info("============更新数据库===========");
				SequencesPo sequencesPoQuery = new SequencesPo();
				sequencesPoQuery.setSeq_code(map.get("seq_code").toString());
				sequencesPoQuery.setProvince_code(map.get("province_code").toString());
				sequencesPoQuery.setArea_code(map.get("area_code").toString());
				SequencesPo sequencesPoUpdate = getIdServ.querySequencesBySeqCodeAndProvinceCodeAndAreaCode(sequencesPoQuery);
				sequencesPoUpdate.setSeq_tail_curr(String.valueOf(Integer.parseInt(map.get("seq_tail_begin").toString())+Integer.parseInt(map.get("seq_tail_step").toString())));
				getIdServ.updateSequences(sequencesPoUpdate);
			}
			//总数未用完
			else{
				//步数已用完
				if(Integer.parseInt(map.get("stepRest").toString()) == 0){
					sequenceNumber = String.valueOf(Integer.parseInt(map.get("currNumber").toString())+1);
					logger.info("==============更新redis=================");
					RedisData updateRedisDate = new RedisData();
					updateRedisDate.setId("Seq_"+map.get("seq_code").toString()+"_"+map.get("province_code").toString()+"_"+map.get("area_code").toString());
					Map<String,Object> updateRedisMap = map;
					updateRedisMap.put("stepRest", String.valueOf(Integer.parseInt(map.get("seq_tail_step").toString())-1));
					updateRedisMap.put("currNumber", sequenceNumber);
					updateRedisMap.put("seq_tail_curr", String.valueOf(Integer.parseInt(map.get("seq_tail_curr").toString())+Integer.parseInt(map.get("seq_tail_step").toString())));
					updateRedisDate.setMap(updateRedisMap);
					redisServiceServ.updateDataToRedis(updateRedisDate);
					logger.info("==========================更新数据库=============");
					SequencesPo sequencesPoQuery = new SequencesPo();
					sequencesPoQuery.setSeq_code(map.get("seq_code").toString());
					sequencesPoQuery.setProvince_code(map.get("province_code").toString());
					sequencesPoQuery.setArea_code(map.get("area_code").toString());
					SequencesPo sequencesPoUpdate = getIdServ.querySequencesBySeqCodeAndProvinceCodeAndAreaCode(sequencesPoQuery);
					sequencesPoUpdate.setSeq_tail_curr(String.valueOf(Integer.parseInt(map.get("seq_tail_curr").toString())+Integer.parseInt(map.get("seq_tail_step").toString())));
					
				}
				else{
					sequenceNumber = String.valueOf(Integer.parseInt(map.get("currNumber").toString())+1);
					logger.info("==============更新redis=================");
					RedisData updateRedisDate = new RedisData();
					updateRedisDate.setId("Seq_"+map.get("seq_code").toString()+"_"+map.get("province_code").toString()+"_"+map.get("area_code").toString());
					Map<String,Object> updateRedisMap = map;
					updateRedisMap.put("stepRest", String.valueOf(Integer.parseInt(map.get("seq_tail_step").toString())-1));
					updateRedisMap.put("currNumber", sequenceNumber);
					updateRedisMap.put("seq_tail_curr", String.valueOf(Integer.parseInt(map.get("seq_tail_curr").toString())+Integer.parseInt(map.get("seq_tail_step").toString())));
					updateRedisDate.setMap(updateRedisMap);
					redisServiceServ.updateDataToRedis(updateRedisDate);
				}
			}
		}
		else{
			if(flag.equals(map.get("resetFlag").toString())){
				sequenceNumber = String.valueOf(Integer.parseInt(map.get("currNumber").toString())+1);
				logger.info("==============更新redis=================");
				RedisData updateRedisDate = new RedisData();
				updateRedisDate.setId("Seq_"+map.get("seq_code").toString()+"_"+map.get("province_code").toString()+"_"+map.get("area_code").toString());
				Map<String,Object> updateRedisMap = map;
				updateRedisMap.put("stepRest", String.valueOf(Integer.parseInt(map.get("seq_tail_step").toString())-1));
				updateRedisMap.put("currNumber", sequenceNumber);
				updateRedisMap.put("seq_tail_curr", String.valueOf(Integer.parseInt(map.get("seq_tail_curr").toString())+Integer.parseInt(map.get("seq_tail_step").toString())));
				updateRedisDate.setMap(updateRedisMap);
				redisServiceServ.updateDataToRedis(updateRedisDate);
			}
			else{
				sequenceNumber = String.valueOf(Integer.parseInt(map.get("seq_tail_begin").toString()+1));
				logger.info("==============更新redis=================");
				RedisData updateRedisDate = new RedisData();
				updateRedisDate.setId("Seq_"+map.get("seq_code").toString()+"_"+map.get("province_code").toString()+"_"+map.get("area_code").toString());
				Map<String,Object> updateRedisMap = map;
				updateRedisMap.put("stepRest", String.valueOf(Integer.parseInt(map.get("seq_tail_step").toString())-1));
				updateRedisMap.put("currNumber", sequenceNumber);
				updateRedisMap.put("seq_tail_curr", String.valueOf(Integer.parseInt(map.get("seq_tail_curr").toString())+Integer.parseInt(map.get("seq_tail_step").toString())));
				updateRedisDate.setMap(updateRedisMap);
				redisServiceServ.updateDataToRedis(updateRedisDate);
			}
		}
		//补位
		if("1".equals(map.get("seq_tail_rightjustify").toString())){
			int fillSeatLength = Integer.parseInt(map.get("seq_tail_lenth").toString())-sequenceNumber.length();
			for(int i=0;i<fillSeatLength;i++){
				sequenceNumber = "0" + sequenceNumber;
			}
		}
		
		return sequenceNumber;
	}
	
	/**获取指定位数的随机数*/
	public String GetRandomNum(int length) throws Exception{
		String randomNum = "";
		for(int i=0;i<length;i++){
			randomNum += (int)(Math.random()*10);
		}
		return randomNum;
	}
}
