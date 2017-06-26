package com.tydic.unicom.crawler.common;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.crawler.dao.po.Crawler_SKU_definePo;

public class SysPar {

	static Logger logger = LoggerFactory.getLogger(SysPar.class);
	/** 区划编码 **/
	public static HashMap coderegion = new HashMap<>();
	/** 套餐对照 **/
	public static HashMap<String ,Crawler_SKU_definePo> sukdef = new HashMap<String ,Crawler_SKU_definePo>();
	/** 用户管理 **/
	public static HashMap user = new HashMap<>();
	/** 参数文件信息 **/
	public static ProParamVo ppvo;
	
	public static String getCert_type(String str){
		if(str.equals("15位身份证")){
			return "01";
		}
		if(str.equals("18位身份证")){
			return "02";
		}
		if(str.equals("驾驶证")){
			return "03";
		}
		if(str.equals("军官证")){
			return "04";
		}
		if(str.equals("教师证")){
			return "05";
		}
		if(str.equals("学生证")){
			return "06";
		}
		if(str.equals("营业执照")){
			return "07";
		}
		if(str.equals("护照")){
			return "08";
		}
		return "99";
	}
	
	/**
	 * 通过身份证得到性别
	 * @param str
	 * @return
	 */
	public static String getCustomer_sex(String str){
		String tmp = SysUtil.RegExfind(SysUtil.getStr(str), "(\\d*)", false);
		if(tmp.length() > 3){
			tmp = tmp.substring(tmp.length()-2,tmp.length()-1);
			
			if(new Integer(tmp) % 2 ==0)
				return "0";
			else
				return "1";
		}
		return tmp;
	}
	
}
