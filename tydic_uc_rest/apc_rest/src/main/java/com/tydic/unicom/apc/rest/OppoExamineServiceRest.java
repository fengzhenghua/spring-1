package com.tydic.unicom.apc.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.apc.business.pub.interfaces.OppoExamineServiceServDu;
import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.OPPO_EXAMINE_SERVICE_REST)
public class OppoExamineServiceRest {

	private static Logger logger = Logger.getLogger(OppoExamineServiceRest.class);
	
	@Autowired
	private OppoExamineServiceServDu oppoExamineServiceServDu;
	
	@RequestMapping(value = UrlsMappings.GET_WX_JS_CONFIG,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage getWXJSConfig(String url, String mcht_flag){
		logger.info("============获取微信配置参数==========");
		UocMessage uocMessage= new UocMessage();
		try {
			uocMessage = oppoExamineServiceServDu.getWXJSConfig(url, mcht_flag);
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("获取微信配置参数异常");
			return uocMessage;
		}
	}
	
	@RequestMapping(value = UrlsMappings.DOWNLOAD_WX_FILE,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage downloadWXFile(String oper_no,String order_id,String mcht_flag,String server_id,String validate_data,String video_server_id,String flag){
		logger.info("===============下载微信文件===============");
		UocMessage uocMessage= new UocMessage();
		try {
			uocMessage = oppoExamineServiceServDu.downloadWXFile(oper_no, order_id, mcht_flag, server_id,validate_data,video_server_id,flag);
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("下载微信文件异常");
			return uocMessage;
		}
	}
	
	@RequestMapping(value = UrlsMappings.GET_LIP_LANUAGE_INFO,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage getLipLanguageInfo(String mcht_flag,String url){
		logger.info("=============获取唇语数据=================");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = oppoExamineServiceServDu.getLipLanguageInfo(mcht_flag, url);
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("获取唇语数据异常");
			return uocMessage;
		}
	}
	
	@RequestMapping(value = UrlsMappings.OPPO_LIVING_EXAMINE,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage oppoLivingExamine(String order_id,String validate_data,String video,String video_file_name,String video_server_id){
		logger.info("==============活体审核============");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = oppoExamineServiceServDu.oppoLivingExamineSubmit(order_id, validate_data, video, video_file_name,video_server_id);
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("活体审核异常");
			return uocMessage;
		}
	}
}
