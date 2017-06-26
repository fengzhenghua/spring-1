package com.tydic.unicom.crawler.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tydic.unicom.crawler.business.task.OrderManualAccount;
import com.tydic.unicom.crawlerframe.util.JavaScriptEngine;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SysUtil {
	
	public static final Logger LOG = LoggerFactory.getLogger(SysUtil.class);

	public static String trim(Object obj) {
		String ts = getStr(obj).trim();
		ts = ts.replaceAll(" ", "");
		ts = ts.replaceAll("\\s", "");
		return ts;
	}

	public static String trimandcolon(Object obj) {
		String ts = SysUtil.trim(obj);
		ts = ts.replaceAll("：", "");
		ts = ts.replaceAll(":", "");
		return ts;
	}

	/**
	 * 根据正则表达式替换REG 中的内容
	 * 
	 * @param obj
	 * @param reg
	 * @param rs
	 * @return
	 */
	public static String repaly(Object obj, String reg, String rs) {
		String tmp = SysUtil.getStr(obj);
		if (tmp.isEmpty())
			return "";
		tmp = tmp.replaceAll(reg, "");
		return tmp;
	}

	public static String getStrandtrim(Object obj) {
		String ts = SysUtil.trim(obj);
		if (ts.isEmpty())
			return "";
		else
			ts = SysUtil.trim(ts);
		return ts;
	}

	public static String getStr(Object obj) {
		if (obj == null || obj.toString().isEmpty()) {
			return "";
		}
		return obj.toString();
	}

	/**
	 * 从 A:B 这种格式中得到一个 MAP对象 A为KEY B为val
	 * 
	 * @param f
	 * @return
	 */
	public static Map getmapfromstr(String f) {
		Map m = new HashMap();
		String key = "";
		String val = "";
		f = f.replace("：", ":");

		if (f.indexOf(":") > 0) {
			if (f.indexOf(":") == f.length()) {
				key = f;
				m.put(f, "");
			} else {
				key = f.split(":")[0];
				if (f.split(":").length > 1)
					val = f.split(":")[1];
				else
					val = "";
				m.put(key, val);
			}
		} else {
			key = "Nul" + String.valueOf(System.currentTimeMillis());
			val = f;
			m.put(key, val);
		}
		return m;
	}

	/** jsoninfo **/

	public static Map<String, Object> jsonToMap(String jsonStr) throws Exception {
		// 最外层解析
		if (jsonStr != null && jsonStr.startsWith("{") && jsonStr.endsWith("}")) {
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject json = JSONObject.fromObject(jsonStr);
			for (Object k : json.keySet()) {
				Object v = json.get(k);
				// 如果内层还是数组的话，继续解析
				if (v instanceof JSONArray) {
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					Iterator<JSONObject> it = ((JSONArray) v).iterator();
					while (it.hasNext()) {
						JSONObject json2 = it.next();
						list.add(jsonToMap(json2.toString()));
					}
					map.put(k.toString(), list);
				} else {
					Map<String, Object> m = jsonToMap(v.toString());
					if (m == null)
						map.put(k.toString(), v);
					else
						map.put(k.toString(), m);
				}
			}
			return map;
		} else {
			return null;
		}
	}

	public static Map<String, String> jsonToMapStr(String jsonStr) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		// 一层解析
		if (jsonStr != null && jsonStr.startsWith("{") && jsonStr.endsWith("}")) {
			JSONObject json = JSONObject.fromObject(jsonStr);
			for (Object k : json.keySet()) {
				Object v = json.get(k);
				map.put(k.toString(), v.toString());
			}
			return map;
		} else {
			return map;
		}
	}

	/**
	 * 全域查找，找到就返回 FFF.DDD
	 * 
	 * @return
	 */
	public static String jsonfindall(String json, String pagename) {
		if (SysUtil.getStr(json).isEmpty())
			return "";
		if (json.trim().startsWith("{") && json.trim().endsWith("}")) {
			JSONObject jsonobj = JSONObject.fromObject(json);
			String[] sp = pagename.split("\\.");
			if (sp.length == 1) {
				Object a = jsonobj.get(sp[0]);
				// 没有找到值
				if (a == null)
					return "";
				return a.toString();
			} else if (sp.length < 1) {
				return "";
			}
			String pn = pagename.substring(pagename.indexOf(".") + 1);
			Object restr = jsonobj.get(sp[0]);
			if (restr == null)
				return "";
			else {
				String re = SysUtil.jsonfindall(restr.toString(), pn);
				return re;
			}
		} else
			return json;
	}

	public static String mapToJson(Map<String, Object> map) {
		JSONObject jsonObject1 = JSONObject.fromObject(map);
		return jsonObject1.toString();
	}

	/**
	 * 把源文件转换成模板文件
	 * 
	 * @param s
	 *            原文件
	 * @param t
	 *            模板文件
	 * @return
	 */
	public static String templatetransformation(String s, String t) {
		// System.out.println(s);
		JSONObject sjb = JSONObject.fromObject(s);
		JSONObject tjb = JSONObject.fromObject(t);
		JSONObject tmpjb = new JSONObject();
		// 最外层解析
		for (Object k : tjb.keySet()) {
			Object v = tjb.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {

			} else if (v instanceof JSONObject) {
				String m = templatetransformation(s, v.toString());
				tmpjb.put(k, m);
			} else {
				String m = paresFormat(v.toString(), s);
				tmpjb.put(k, m);
			}
		}
		return tmpjb.toString();
	}

	/**
	 * 通过v中的转意字符在S 中查找</br>
	 * 1、检查格式，JAVASCRPIT的格式是 #J{(.*)}#，这种格式就运行JAVAscript</br>
	 * 2、检查格式  #R{(.*)}# ，这种格式是运行正则表达式使用的。</br>
	 * 3、#(.*) 直接引用格式</br>
	 * 4、`(.*) 命令引用格式，这个主要用于JAVA直接函数生成，也可以用JAVASCRIPT生成，如果不改动固定就用这个方式，JAVA运行比JAVASCRIPT快</br>
	 * @param v
	 *            转移字符
	 * @param s
	 *            查找的JSON字符</br>
	 * @return
	 */
	public static String paresFormat(String v, String s) {
		synchronized (s) {
			if (v == null || v.isEmpty()) {
				return "";
			} else {
//				LOG.info("####得到格式字符 ："+v);
				String format = RegExfind(v, "^\\\"(.*)\\\"$", false);
				if(format.isEmpty()){
					format = RegExfind(v, "^\\\"(.*)", false);
				}
				if(format.isEmpty()){
					format = RegExfind(v, "(.*)\\\"$", false);
				}
				if(format.isEmpty()){
					format =v;
				}
				v= format;
				//查找JAVASCRIPT
				String context = RegExfind(v, "#J\\{(.*)\\}#",false);
				if(!context.isEmpty()){
					String tmpv = v.replaceAll("#J\\{.*\\}#", "");
					String[] pararray = tmpv.split(",");
					String[] par = null;
					if(pararray!=null && pararray.length>0){
						par = new String[pararray.length];
						for(int i = 1 ; i < pararray.length; i++){
							System.out.println(pararray[i]);
							par[i-1] = jsonfindall(s, pararray[i]);
						}
					}
					JavaScriptEngine javaScriptEngine = new JavaScriptEngine();
					return javaScriptEngine.eval(context, par);
				}
				//查找正则表达式
				context = RegExfind(v, "#R\\{(.*)\\}#",false);
				if(!context.isEmpty()){
					String tmpv = v.replaceAll("#R\\{.*\\}#", "");
					String[] pararray = tmpv.split(",");
					String par = null;
					if(pararray!=null && pararray.length>0){
						par =  jsonfindall(s, pararray[1]);
//						par = new String[pararray.length];
//						for(int i = 0 ; i < pararray.length; i++){
//							par[i] = jsonfindall(s, pararray[i]);
//						}
					}
					String f = par.toString();
					f = RegExfind(f, context, false);
					System.out.println("===============" + par + "   " + f);
					return f;
				}
				//如果不是就说明是直接引用格式
				//检查是否需要转意，如果不需要就不转
				if (v.charAt(0) == '#') {
					String f = jsonfindall(s, v.substring(1));
					return f;
				}
				if (v.charAt(0) == '`') {
					String name = v.substring(1);
					switch (name) {
					case "DATE32": {
						return SysUtil.getDateYMDHMS();
					}
					default:
						break;
					}
					return "";
				} else {
					return v.toString();
				}
			}
		}
	}

	/**
	 * 全域查找，找到就返回 FFF.DDD isadd 如果为TRUE 就说明如果找不到就增加
	 * 
	 * @return
	 */
	public static JSONObject jsonreplace(String json, String pagename, String replacestr, boolean isadd) {

		System.out.println(json);
		String res ;
		HashMap<String, Object> data = new HashMap<String, Object>();
		// 将json字符串转换成jsonObject
		try {
			Map hm = SysUtil.jsonToMap(json);

			String[] arraystr = pagename.split("\\.");
			int tc = arraystr.length;
			System.out.println(tc);
			Map tmpmap = hm;
			for (int i = 0; i < arraystr.length; i++) {
				if (i == (tc - 1)) {
					if (tmpmap!=null && (tmpmap.get(arraystr[i]) != null || isadd)){
						tmpmap.put(arraystr[i], replacestr);
					}
					break;
				}
				tmpmap = (Map) tmpmap.get(arraystr[i]);
				System.out.println(tmpmap);
			}
			res = SysUtil.mapToJson(hm);
			return JSONObject.fromObject(res);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  JSONObject.fromObject(json);
	}

	public static JSONObject jsonreplace(String json, String pagename, String replacestr) {
		return SysUtil.jsonreplace(json, pagename, replacestr, false);
	}

	public static String getdata(String format) {
		Date date = new Date();
		SimpleDateFormat si = new SimpleDateFormat(format);
		return si.format(date);
	}

	public static String getDateYMD() {
		return getdata("YYYY-MM-dd");
	}

	public static String getDateYMDHMS() {
		return getdata("YYYY-MM-dd hh:mm:ss");
	}

	/**
	 * 
	 * @param str			传入的字符
	 * @param regEx			查找的正则表达式内容
	 * @param include		是否包含查找的正则表达式文本
	 * @return	1,2,3,4,5
	 */
	public static String RegExfinds(String str, String regEx,boolean include) {
		synchronized (str) {
			// 编译正则表达式
			Pattern pattern = Pattern.compile(regEx);
			// 忽略大小写的写法
			// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(str);
			String fd = "";
			// 查找字符串中是否有匹配正则表达式的字符/字符串
			while (matcher.find()) {
				if(!include){
					String tmp = "";
					try{
						tmp = matcher.group(1);
					}catch(Exception e){
						//没有找到包含的字符串，这个错误一般是在正则表达式中没有包含  ()照成的
						tmp = matcher.group(0);
					}
					fd = fd + "," + tmp;
				}else{
					fd = fd + "," + matcher.group(0);
				}
			}
			return fd;
		}
	}

	/**
	 * 
	 * @param str			传入的字符
	 * @param regEx			查找的正则表达式内容
	 * @param include		是否包含查找的正则表达式文本 true 包含，false不包含
	 * @return
	 */
	public static String RegExfind(String str, String regEx,boolean include) {
		synchronized (str) {
			// 编译正则表达式
			Pattern pattern = Pattern.compile(regEx);
			// 忽略大小写的写法
			// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(str);
			// 查找字符串中是否有匹配正则表达式的字符/字符串
			if (matcher.find()) {
				if(!include){
					String tmp = "";
					try{
						tmp = matcher.group(1);
					}catch(Exception e){
						LOG.info(regEx + ": 未找到分组，请检查正则表达式中是否写了分组符号  ()");
						//没有找到包含的字符串，这个错误一般是在正则表达式中没有包含  ()照成的
						tmp = matcher.group(0);
					}
					return tmp;
				}else{
					return matcher.group(0);
				}
			} else
				return "";
		}
	}

	// params.json_info.serial_number
	public static void main(String[] args) {
//		String f = "{\"SERVICE_NAME\":\"createSaleOrder\",\"params\":{\"jsession_id\":\"SF0540\",\"order_type\":\"A101\",\"accept_no\":\"7815822049\",\"flow_flag\":\"101\",\"accept_type\":\"101\",\"accept_system\":\"001\",\"accept_time\":\"2017-04-01 03:28:57\",\"province_code\":\"83\",\"pay_flag\":\"11\",\"express_flag\":\"10\",\"auto_confirm\":\"1\",\"json_info\":{\"serial_number\":\"18523195774(重庆)\",\"first_mon_bill_mode\":\"01\",\"product_id\":\"全国版\",\"ser_type\":\"2\",\"cert_address\":\"重庆市巴南区接龙镇桥边村黄家屋基组37号\",\"cert_expire_date\":\"2050-12-31\",\"cert_num\":\"500113199403016725(18位身份证)\",\"cert_type\":\"500113199403016725(18位身份证)\",\"contact_address\":\"重庆，重庆市，渝中区，测试测试测试测试\",\"customer_name\":\"胡冰洁\",\"contact_phone\":\"18623457767\",\"oper_code\":\"openLocal4G\",\"customer_sex\":\"1\",\"receive_name\":\"胡冰洁\",\"receive_phone\":\"18623457767\",\"receive_province\":\"重庆，重庆市，渝中区，测试测试测试测试\",\"receive_city\":\"重庆，重庆市，渝中区，测试测试测试测试\",\"receive_area\":\"重庆，重庆市，渝中区，测试测试测试测试\",\"receive_address\":\"重庆，重庆市，渝中区，测试测试测试测试\",\"total_fee\":\"openHn01\",\"pay_type\":\"10\"}}}";
//		f = "{\"SERVICE_NAME\":\"createSaleOrder\",\"params\":{\"jsession_id\":\"SF0540\",\"order_type\":\"A101\",\"accept_no\":\"7815822049\",\"flow_flag\":\"101\",\"accept_type\":\"101\",\"accept_system\":\"001\",\"accept_time\":\"2017-04-05 11:58:49\",\"province_code\":\"83\",\"pay_flag\":\"11\",\"express_flag\":\"10\",\"auto_confirm\":\"1\",\"json_info\":{\"serial_number\":\"18523195774\",\"first_mon_bill_mode\":\"01\",\"product_id\":\"全国版\",\"ser_type\":\"2\",\"cert_address\":\"重庆市巴南区接龙镇桥边村黄家屋基组37号\",\"cert_expire_date\":\"2050-12-31\",\"cert_num\":\"500113199403016725\",\"cert_type\":\"18位身份证\",\"contact_address\":\"重庆，重庆市，渝中区，1\",\"customer_name\":\"胡冰洁\",\"contact_phone\":\"18623457767\",\"oper_code\":\"openLocal4G\",\"customer_sex\":\"1\",\"receive_name\":\"胡冰洁\",\"receive_phone\":\"18623457767\",\"receive_province\":\"重庆\",\"receive_city\":\"重庆市\",\"receive_area\":\"渝中区\",\"receive_address\":\"重庆，重庆市，渝中区，1\",\"total_fee\":\"openHn01\",\"pay_type\":\"10\"}}}";
//		
//		
//		JSONObject json = SysUtil.jsonreplace(f, "params.json_info.cert_type", "fdfdf");
//		// JSONObject json = SysUtil.jsonreplace(f,
//		// "params.json_info.serial_number", "fdfdf");
//		System.out.println(json.toString());
//
////		System.out.println("   " +SysUtil.paresFormat("\"#R{\\d{11}}#,context.1.号码\"", "{}"));
//		//#R{(.*)\\(},context.2.证件\
////		String v = "重庆，重庆市，渝中区，1" ;
////		String f = RegExfind(v, "(.*)，", false);
////		System.out.println(f);
		JSONObject js = JSONObject.fromObject("{}");
		JSONArray ja = new JSONArray();
		ja.add("1");
		ja.add("1");
		ja.add("1");
		
		js.put("data", ja.toString());
		
		System.out.println(js.toString());
		
		
		
		
		
	}

}
