/**
 * @ProjectName: tydic_javaBase
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yangfei
 * @date: 2014年11月6日 上午11:16:22
 * @Title: BaseRequestRo.java
 * @Package com.tydic.unicom.service.ecaop.ro
 * @Description: TODO
 */
package com.tydic.unicom.service.ecaop.vo;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.tydic.unicom.service.ecaop.constants.Constant;
import com.tydic.unicom.service.ecaop.service.interfaces.Validator;
import com.tydic.unicom.util.DateUtil;

/**
 * <p>
 * </p>
 * @author yangfei 2014年11月6日 上午11:16:22
 * @ClassName BaseRequestRo
 * @Description TODO ecaop请求基本类
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年11月6日
 * @modify by reason:{方法名}:{原因}
 */
public class BaseRequestVo implements Validator {
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	protected String method;
	protected String timestamp = DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
	protected String appkey = Constant.ECAOP_APP_KEY;
	protected String sign = Constant.ECAOP_SIGN_MD5;
	protected String apptx = String.valueOf(apptx(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase())).replaceAll(
	        "-", "");
	protected String bizkey = Constant.ECAOP_DEFALUT_BIZKEY;
	
	private Long apptx(String s) {
		Long sum = 0l;
		int tmp = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= '0' && c <= '9') {
				tmp = c - '0';
			} else
				if (c >= 'A' && c <= 'F') {
					tmp = c - 'A' + 10;
				} else {
					break;
				}
			sum = sum * 16 + tmp;
		}
		return sum;
	}
	
	public String convert2JSON() {
		JSONObject jsonObject = JSONObject.fromObject(this);
		removeEmpNodesObj(jsonObject);
		return jsonObject.get("msg").toString();
	}
	
	public String requestMsg() {
		StringBuffer sb = new StringBuffer("method=");
		sb.append(method);
		sb.append("&");
		sb.append("appkey=");
		sb.append(appkey);
		sb.append("&");
		sb.append("apptx=");
		sb.append(apptx);
		sb.append("&");
		sb.append("timestamp=");
		sb.append(URLEncoder.encode(timestamp));
		sb.append("&");
		sb.append("bizkey=");
		sb.append(bizkey);
		sb.append("&");
		sb.append("msg=");
		sb.append(convert2JSON());
		return sb.toString();
	}
	
	public String requestLanMsg(String bizkey) {
		StringBuffer sb = new StringBuffer("method=");
		sb.append(method);
		sb.append("&");
		sb.append("appkey=");
		sb.append(appkey);
		sb.append("&");
		sb.append("apptx=");
		sb.append(apptx);
		sb.append("&");
		sb.append("timestamp=");
		sb.append(timestamp);
		sb.append("&");
		sb.append("bizkey=");
		sb.append(bizkey);
		sb.append("&");
		sb.append("msg=");
		sb.append(convert2JSON());
		return sb.toString();
	}
	
	public String requestMsgString() {
		StringBuffer sb = new StringBuffer("");		
		sb.append(convert2JSON());
		return sb.toString();
	}
	
	
	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getAppkey() {
		return appkey;
	}
	
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	
	public String getSign() {
		return sign;
	}
	
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String getApptx() {
		return apptx;
	}
	
	public void setApptx(String apptx) {
		this.apptx = apptx;
	}
	
	public String getBizkey() {
		return bizkey;
	}
	
	public void setBizkey(String bizkey) {
		this.bizkey = bizkey;
	}
	
	@Override
	public Boolean validate(Object object) throws Exception {
		BaseMsg msg = (BaseMsg)object;
		if (msg == null) {
			throw new Exception("参数[msg]为空");
		} else {
			String operatorId = msg.getOperatorId();
			if (operatorId == null || operatorId.equals("")) {
				throw new Exception("参数[操作员ID]为空");
			}
			String province = msg.getProvince();
			if (province == null || province.equals("")) {
				throw new Exception("参数[省份]为空");
			}
			String city = msg.getCity();
			if (city == null || city.equals("")) {
				throw new Exception("参数[地市]为空");
			}
			String district = msg.getDistrict();
			if (district == null || district.equals("")) {
				throw new Exception("参数[区县]为空");
			}
			String channelId = msg.getChannelId();
			if (channelId == null || channelId.equals("")) {
				throw new Exception("参数[渠道编码]为空");
			}
			String channelType = msg.getChannelType();
			if (channelType == null || channelType.equals("")) {
				throw new Exception("参数[渠道类型]为空");
			}
		}
		return null;
	}
	
	public static boolean removeEmpNodesObj(JSONObject json){
		
		ArrayList<String> nodeNames = decodeJSONObject(json);//获取json下第一级成员名称
		
		try {
			for(int i=0;i<nodeNames.size();i++){//循环处理当前层级json对象下第一级成员
				
			    String name=(String) nodeNames.get(i); 
			    Object o=json.get(name);  
			    
			    if(o == null || "".equals(o.toString()) || "null".equals(o.toString()) || "[]".equals(o.toString())){
					   json.discard(name);	//移除空节点
				}
			    
			    if(o instanceof JSONObject){
			    	JSONObject jsonTmp = (JSONObject)o;
			    	
			    	boolean flag = removeEmpNodesObj(jsonTmp);//递归调用
				    if(!flag){
				    	return false;
				    }
			    }else if(o instanceof JSONArray){ //针对list对象进行处理
			    	JSONArray ja = (JSONArray)o;
			    	for(int j=0;j<ja.size();j++){
			    		JSONObject jaTmp=ja.getJSONObject(j); 
			    		boolean flag = removeEmpNodesObj(jaTmp);
			    		if(!flag){
				    		return false;
				    	}
			    	}
			    }
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}
		
		return true;
	}

	public static ArrayList<String> decodeJSONObject(JSONObject json) {
		Iterator<String> keys = json.keys();
		ArrayList<String> list = new ArrayList<String>();
		Object o;
		String key;
		while (keys.hasNext()) {
			key = keys.next();
			o = json.get(key);
			list.add(key);
		}
		
		return list;
	}
	
}
