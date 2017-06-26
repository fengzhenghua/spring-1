package com.tydic.unicom.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 * 
 * @author supeng
 * 
 */
public class StringUtil {

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		if (null == str)
			return true;
		if ("".equals(str))
			return true;
		return false;
	}

	public static String toString(Object obj) {
		if (null == obj)
			return "";
		return obj.toString();
	}
	
	/**
	 * 判断字符串是否为手机号
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号  
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    } 
	
	/**
	 * 判断字符串是否为身份证号码
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isIDCard(String IDStr) {
        String errorInfo = "";// 记录错误信息
        
        IDStr = IDStr.toLowerCase();
        
        String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",
                "3", "2" };
        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2" };
        String Ai = "";
        // ================ 号码的长度 15位或18位 ================
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            errorInfo = "身份证号码长度应该为15位或18位。";
            return false;
        }
        // =======================(end)========================

        // ================ 数字 除最后以为都为数字 ================
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
            return false;
        }
        // =======================(end)========================

        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
            errorInfo = "身份证生日无效。";
            return false;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(
                            strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                errorInfo = "身份证生日不在有效范围。";
                return false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            errorInfo = "身份证月份无效";
            return false;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            errorInfo = "身份证日期无效";
            return false;
        }
        // =====================(end)=====================

        // ================ 地区码时候有效 ================
        Hashtable h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            errorInfo = "身份证地区编码错误。";
            return false;
        }
        // ==============================================

        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (IDStr.length() == 18) {
            if (Ai.equals(IDStr) == false) {
                errorInfo = "身份证无效，不是合法的身份证号码";
                return false;
            }
        } else {
            return true;
        }
        // =====================(end)=====================
        return true;
    }

    /**
     * 功能：设置地区编码
     * 
     * @return Hashtable 对象
     */
    @SuppressWarnings("unchecked")
    private static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    /**
     * 功能：判断字符串是否为数字
     * 
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：判断字符串是否为日期格式
     * 
     * @param str
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }
	
    /**
	 * 判断字符串是否为中文名
	 * 
	 * @param str
	 * @return
	 */
    public static boolean isChineseName(String name) {
    	Pattern pattern = Pattern.compile("^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]){2,5}$");
    	Matcher matcher = pattern.matcher(name);
    	if(matcher.find()){
    		return true;
    	}
    	return false;
    }
    
    /**
	 * 判断字符串是否为英文名
	 * 
	 * @param str
	 * @return
	 */
    public static boolean isEnglishName(String name) {
    	Pattern pattern = Pattern.compile("^[A-Za-z]+$");
    	Matcher matcher = pattern.matcher(name);
    	if(matcher.find()){
    		return true;
    	}
    	return false;
    }
    /**
	 * 判断字符串是否为宽带号
	 * 
	 * @param str
	 * @return
	 */
    public static boolean isLanDeviceNumber(String deviceNumber){
    	if(deviceNumber.length()>6){
    		String tmp = deviceNumber.substring(0, 6);
    		if("077102".equals(tmp)
    				||"077302".equals(tmp)
    				||"077202".equals(tmp)
    				||"077402".equals(tmp)
    				||"077502".equals(tmp)
    				||"077602".equals(tmp)
    				||"077702".equals(tmp)
    				||"077802".equals(tmp)
    				||"077902".equals(tmp)
    				||"077108".equals(tmp)
    				||"077408".equals(tmp)
    				||"077508".equals(tmp)
    				||"077008".equals(tmp)
    				||"077208".equals(tmp)
    			){
    			return true;
    		}
    	}
    	
    	return false;
    }
    /**
     * 返回N位随机数,格式:小写字母+数字
     */
    public static String getRdStr (int N){
    	String intStr = "0123456789";
        String charStr = "";
        
        //随机数范围
        String str = intStr + charStr;
        
        String retStr = "";
        
        int len = str.length();
        
        //生成N位随机数
        for(int i=0;i<N;i++){
        	Random rd = new Random();
        	int r = rd.nextInt(len);
        	String tmpStr = str.charAt(r)+"";
        	retStr += tmpStr;
        	
        }
        return retStr;
    }
    
//    public static void main(String[] args) {
//    	boolean b = isIDCard("45060219851111011x");
//    	System.out.println(b);
//    }
    
  
	//YUN-914  ALL_(全国版)自助终端凭条打印功能  杨枝蕃 begin
	//是否中文
	public static boolean isGBK(char ch) {
		String str = ""+ch;
		return str.getBytes().length>1;
	}
	
	//将字符串分为list，如遇中文则多一个字符，避免乱码
	public static List<String> pageStringWithGbk(String str,int pageCharSize) {
		List<String> strArr = new ArrayList<String>();
		
		if (str==null || "".equals(str) || pageCharSize<=0) {
			return strArr;
		}
		
		String sub="";
		int charsLeft = pageCharSize;
		for (int i=0; i<=str.length(); i++) {
			
			if (i==str.length()) { //最后一个
				if (!"".equals(sub)) {
					strArr.add(sub);
				}
				break;
			}
			
			sub += str.charAt(i);
			
			//charsLeft -= (""+str.charAt(i)).getBytes().length;
			charsLeft--;
			if (isGBK(str.charAt(i))) {
				charsLeft--;
			}
			
			if (charsLeft<=0) {
				strArr.add(sub);
				charsLeft = pageCharSize;
				sub="";
			}
			
		}
		
		
		return strArr;
	}
	//YUN-914  ALL_(全国版)自助终端凭条打印功能 end
}
