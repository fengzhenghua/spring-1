package com.tydic.unicom.apc.service.pub.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;
import com.tydic.unicom.apc.base.pub.interfaces.BssService;
import com.tydic.unicom.apc.business.constants.Constant;
import com.tydic.unicom.apc.business.req.vo.ResponseVo;
import com.tydic.unicom.apc.business.req.vo.UniHeadVo;
import com.tydic.unicom.apc.business.req.vo.YouTuUniBodyVo;
import com.tydic.unicom.apc.business.req.vo.YouTuUniBssVo;
import com.tydic.unicom.apc.service.common.vo.PropertiesParamVo;
import com.tydic.unicom.apc.service.pub.interfaces.OppoDSFaceServDu;

@Service("OppoDSFaceServDu")
public class OppoDSFaceServDuImpl implements OppoDSFaceServDu{

	private static Logger logger = Logger.getLogger(OppoDSFaceServDuImpl.class);
	
	@Autowired
	private BssService bssService;
	
	@Autowired
	@Qualifier("propertiesParamVo")
	private PropertiesParamVo propertiesParamVo;
	
	@Override
	public Map<String, String> getLipLanguageInfo(String appid, String bucket) throws Exception {
		JSONObject json = new JSONObject();
		json.put("appid", appid);
		json.put("bucket", bucket);
		
		String jsonstr = json.toString();
		String buInterf = "livegetfour";
		return callYoutu(buInterf, jsonstr);
	}

	/**
	 * 内部方法
	 * */
	private Map<String, String> callYoutu(String buInterf, String jsonstr){
		Long sequence = System.currentTimeMillis();
		UniHeadVo uniHead = new UniHeadVo();
		uniHead.setTRAN_ID(sequence + "");
		uniHead.setORIG_DOMAIN(Constant.ORIG_DOMAIN);
		uniHead.setSERVICE_NAME("callYoutu");
		uniHead.setACTION_CODE(Constant.ACTION_CODE);
		
		YouTuUniBodyVo uniBody = new YouTuUniBodyVo();
		uniBody.setBuInterf(buInterf);
		uniBody.setJsonstr(jsonstr);
		YouTuUniBssVo uniBss = new YouTuUniBssVo();
		
		uniBss.setUNI_HEAD(uniHead);
		uniBss.setUNI_BODY(uniBody);
		XStream xStream = new XStream();
		xStream.processAnnotations(YouTuUniBssVo.class);
		xStream.autodetectAnnotations(true);
		String uniBssXmlStr = xStream.toXML(uniBss).replace("__", "_").replace("&quot;", "\"");
		String returnUniBssXmlStr = bssService.callWebServiceByAxis2(propertiesParamVo.getUss_url(), Constant.USS_ACTION, uniBssXmlStr);
		
		Map<String, String> map = new HashMap<String, String>();
		if (null != returnUniBssXmlStr){
			uniBss = (YouTuUniBssVo)xStream.fromXML(returnUniBssXmlStr);
			ResponseVo response = uniBss.getUNI_HEAD().getRESPONSE();
			String rsp_code = response.getRSP_CODE();		
			map.put("RESP_CODE", rsp_code);
			map.put("RESP_DESC", response.getRSP_DESC());	
			if ("00000".equals(rsp_code)) {
				map.put("jsonstr", uniBss.getUNI_BODY().getJsonstr());
			}		
			return map;
		}
		return null;
	}

	@Override
	public Map<String, String> oppoLivingExamine(String appid, String bucket,
			String validate_data, String video, String idcard_number,
			String idcard_name) throws Exception {

		JSONObject json = new JSONObject();
		json.put("appid", appid);
		json.put("bucket", bucket);
		json.put("validate_data", validate_data);
		json.put("video", video);
		json.put("idcard_number", idcard_number);
		json.put("idcard_name", idcard_name);
		
		String jsonstr = json.toString();
		String buInterf = "idcardlivedetectfour";
		
		return callYoutu(buInterf, jsonstr);		
	}
}
