package com.tydic.unicom.uoc.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.uoc.business.common.interfaces.GetDepartAndOperInfoServDu;
import com.tydic.unicom.uoc.business.common.interfaces.GetOptionalTacheServDu;
import com.tydic.unicom.uoc.business.common.interfaces.GetRegionInfoServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.GET_INFO_SERVICE)
public class GetInfoServiceRest {

	private static Logger logger = Logger.getLogger(GetInfoServiceRest.class);
	
	@Autowired
	private GetOptionalTacheServDu getOptionalTacheServDu;
	
	@Autowired
	private GetRegionInfoServDu getRegionInfoServDu;
	
	@Autowired
	private GetDepartAndOperInfoServDu getDepartAndOperInfoServDu;
	
	@RequestMapping(value = UrlsMappings.GET_OPTIONAL_TACHE , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getOptionalTache(String jsession_id){
		logger.info("===========获取可选环节(rest)============jsession_id:"+jsession_id);
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = getOptionalTacheServDu.getOptionalTache(jsession_id);
			return uocMessage;
		}catch(UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		}catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("获取可选环节接口异常");
			return uocMessage;
		}
	}
	
	@RequestMapping(value = UrlsMappings.GET_REGION_INFO , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getRegionInfo(String jsession_id,String region_level,String upper_region_id){
		logger.info("=================获取地区信息(rest)====================jsession_id:"+jsession_id+" region_level:"+region_level+" upper_region_id:"+upper_region_id);
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = getRegionInfoServDu.getRegionInfo(jsession_id, region_level, upper_region_id);
			return uocMessage;
		}catch(UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		}catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("获取地区信息接口异常");
			return uocMessage;
		}

	}
	
	@RequestMapping(value = UrlsMappings.GET_OPER_INFO , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getOperInfo(String jsessionId, String operNo,String operName){
		logger.info("===============获取可选工号信息（rest）===================");
		UocMessage uocMessage = new UocMessage();

		try {
			uocMessage = getDepartAndOperInfoServDu.getOperInfo(jsessionId, operNo, operName);
		}catch(UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("获取可选工号信息接口异常");
			return uocMessage;
		}
		return uocMessage;
	}
	
	@RequestMapping(value = UrlsMappings.GET_DEPART_INFO , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getDepartInfo(String jsession_id, String region_id,
			String depart_no, String depart_name, String query_type){
		logger.info("==================获取可选部门信息（rest）==================");
		UocMessage uocMessage = new UocMessage();

		try {
			uocMessage = getDepartAndOperInfoServDu.getDepartInfoNew(jsession_id, region_id, depart_no,depart_name,query_type);
		}catch(UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("获取可选部门信息接口异常");
			return uocMessage;
		}
		return uocMessage;
	}
	
	@RequestMapping(value = UrlsMappings.GET_DEFAULT_PROVINCE_CITY_AREA_INFO , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getDefaultProvinceCityAreaInfo(String jsession_id){
		logger.info("=================获取默认省份，城市和区域信息(rest)===============");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = getRegionInfoServDu.getDefaultProvinceCityAreaInfo(jsession_id);
		}catch(UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("获取默认省份，城市和区域信息异常");
			return uocMessage;
		}
		return uocMessage;
	}
	
	@RequestMapping(value = UrlsMappings.GET_SHELLING_INFO, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getShellingInfo(String jsession_id,String region_id, String dept_no,String dept_name){
		logger.info("==================UOC0078 	获取可选营业厅==================");
		UocMessage uocMessage = new UocMessage();

		try {
			uocMessage = getDepartAndOperInfoServDu.getShellingInfo(jsession_id, region_id, dept_no,dept_name);
		}catch(UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("获取可选部门信息接口异常");
			return uocMessage;
		}
		return uocMessage;
	}
	
}
