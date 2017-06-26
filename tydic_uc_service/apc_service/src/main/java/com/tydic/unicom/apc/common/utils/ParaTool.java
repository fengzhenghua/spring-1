package com.tydic.unicom.apc.common.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.hibernate.util.xml.XmlDocument;
import org.springframework.beans.BeanUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
//import com.tydic.unicom.apc.service.pub.interfaces.CodeListServDu;
import com.tydic.unicom.vdsBase.po.BasePo;
import com.tydic.unicom.webUtil.BaseVo;

public class ParaTool {
	/* 调用ftp取得图片String */
	public static InputStream BufferedImage2InputStream(BufferedImage image) throws IOException {
		// TODO Auto-generated method stub
		ByteArrayOutputStream os = new ByteArrayOutputStream();  
    	ImageIO.write(image, "JPG", os);  
    	InputStream is = new ByteArrayInputStream(os.toByteArray());  
		return is;
	} 
	/*public static String getPicStringContent(String picLink,
			FileManagerServ fileManagerServ) {
		String content = "";
		if (picLink == null || picLink.equals("")) {
			return content;
		}
		try {
			InputStream is = fileManagerServ.getInputStreamById(picLink);
			byte[] mybyte = input2byte(is);
			BASE64Encoder encoder = new BASE64Encoder();
			content = encoder.encode(mybyte);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return content;
	}*/

	public static final byte[] input2byte(InputStream inStream)
			throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = inStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}

	public static BasePo vo2po(BaseVo vo, Class class1) {
		// TODO Auto-generated method stub
		BasePo po = null;
		try {
			po = (BasePo) class1.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BeanUtils.copyProperties(vo, po);
		return po;
	}

	public static BaseVo po2vo(BasePo po, Class class1) {
		// TODO Auto-generated method stub
		BaseVo vo = null;
		try {
			vo = (BaseVo) class1.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(po!=null){
			BeanUtils.copyProperties(po, vo);
		}
		return vo;
	}
	public static Object copyProperties(Object resource, Class class1) {
		// TODO Auto-generated method stub
		Object objTarget = null;
		try {
			objTarget = (Object) class1.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(resource!=null){
			BeanUtils.copyProperties(resource, objTarget);
		}
		return objTarget;
	}
	public static List po2vo(List listPo, Class class1) {
		// TODO Auto-generated method stub
		if (listPo == null) {
			return null;
		} else if (listPo.size() == 0) {
			return new ArrayList<BaseVo>();
		} else {
			List<BaseVo> listVo = new ArrayList<BaseVo>();
			for (int i = 0; i < listPo.size(); i++) {
				listVo.add(po2vo((BasePo) listPo.get(i), class1));
			}
			return listVo;
		}
	}

	public static org.w3c.dom.Document createRootDoument() {
		org.w3c.dom.Document document = null;
		// TODO Auto-generated method stub
		try {
			javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory
					.newInstance();
			javax.xml.parsers.DocumentBuilder builder = factory
					.newDocumentBuilder();
			document = builder.newDocument();
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		}
		return document;
	}

	public static Element AppendChild(Node element, String prefix,
			String elementName) {
		return AppendChild(element, prefix + elementName);
	}

	public static Element AppendChild(Node element, String elementName) {
		// TODO Auto-generated method stub
		Document document = null;
		if (element instanceof XmlDocument||element instanceof DocumentImpl) {
			document = (Document) element;
		} else {
			document = GetRootDocument((Element) element);
		}
		Element child = document.createElement(elementName);
		element.appendChild(child);
		return child;
	}

	public static Document GetRootDocument(Element element) {

		if (element.getParentNode() instanceof XmlDocument||element.getParentNode() instanceof DocumentImpl) {
			return (Document) element.getParentNode();
		} else {
			return GetRootDocument((Element) element.getParentNode());
		}

	}

	public static void SetLastEleAttrValue(Element element, String prefix,
			String key, String value) {
		SetLastEleAttrValue(element, prefix + key, value);
	}

	public static void SetLastEleAttrValue(Element element, String key,
			String value) {
		if ("".equals(value) || value == null) {
			value = "^^MY_EMPTY_VALUE^^";
		}
		Document document = GetRootDocument(element);

		Element ekey = document.createElement(key);
		org.w3c.dom.Text evalue = document.createTextNode(value);

		element.appendChild(ekey);
		ekey.appendChild(evalue);

	}

	public static String TransforDocumentToXmlString(Document document) {
		// TODO Auto-generated method stub
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = null;
		try {
			t = tf.newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.setOutputProperty("encoding", "UTF-8");// 解决中文问题，试过用GBK不行
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			t.transform(new DOMSource(document), new StreamResult(bos));
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String xmlStr = bos.toString();

		xmlStr = replaceString(xmlStr, "#ply#", "=");
		xmlStr = replaceString(xmlStr, "^$", "&");
		xmlStr = replaceString(xmlStr, "~!", "+");
		xmlStr = replaceString(xmlStr,
				"<?xml version=\"1.0\" encoding=\"GB2312\"?>", "");
		xmlStr = replaceString(xmlStr,
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
		xmlStr = replaceString(xmlStr, "^^MY_EMPTY_VALUE^^", "");

		return xmlStr;
	}

	public static String replaceString(String strOld, String strLook,
			String strReplace) {
		String strNew = "";
		if (strOld == null) { // 为null返回""
			return "";
		}

		if (strLook == null || strReplace == null) { // 其中一个为null,返回oldstr;
			return strOld;
		}

		int i = strOld.indexOf(strLook);
		if (i != -1) {
			while (i != -1) {
				strNew = strNew + strOld.substring(0, i) + strReplace;
				strOld = strOld.substring(i + strLook.length());
				// i=strOld.indexOf(strLook);
				i = strOld.indexOf(strLook);
			}
			strNew = strNew + strOld;
		} else {
			strNew = strOld;
		}
		return strNew; // 返回组成的新串

	}

	public static boolean isEmptyString(String str) {
		// TODO Auto-generated method stub
		if(str==null){
			return true;
		}
		else if("".equals(str.trim())){
			return true;
		}
		else{
			return false;
		}
	}
	/*拷贝源字段中非空域替换目标域*/
	public static void CopyUnnullValues( Object source,Object target) {

		Class _class = source.getClass();
		Field[] f = _class.getDeclaredFields();
		for (int j = 0; j < f.length; j++) {
			f[j].setAccessible(true);
			String key = f[j].getName();
			String value = "";
			try {
				value = String.valueOf(f[j].get(source));
			} catch (IllegalArgumentException e) {
				// private不处理
				continue;
			} catch (IllegalAccessException e) {
				// private不处理
				continue;
			}
			if(value==null||"".equals(value)||"null".equals(value)){
				continue;
			}
			try {
				Field _f = getMatchedField(f[j], target.getClass());
				if (_f != null)
					_f.set(target, value);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}
	}
	public static Field getMatchedField(Field field, Class class1) {
		// TODO Auto-generated method stub
		Field[] f = class1.getDeclaredFields();
		for (int j = 0; j < f.length; j++) {
			f[j].setAccessible(true);
			String key = f[j].getName();
			if (key.equals(field.getName())) {
				return f[j];
			}
		}
		return null;
	}
	/*覆盖目标空字段领域*/
	public static void FilledNullValues(Object source,Object target) {
		// TODO Auto-generated method stub
		Class _class = source.getClass();
		Field[] f = _class.getDeclaredFields();
		for (int j = 0; j < f.length; j++) {
			f[j].setAccessible(true);
			String key = f[j].getName();
			String value = "";
			try {
				value = String.valueOf(f[j].get(source));
			} catch (IllegalArgumentException e) {
				// private不处理
				continue;
			} catch (IllegalAccessException e) {
				// private不处理
				continue;
			}
			if(value==null||"".equals(value)||"null".equals(value)){
				continue;
			}
			try {
				Field _f = getMatchedField(f[j], target.getClass());
				String _value = String.valueOf(_f.get(target));
				if(_value==null||"".equals(_value)||"null".equals(_value)){
					if (_f != null)
						_f.set(target, value);
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}
	}
	public static String GetCurrentDateTime() {
		// TODO Auto-generated method stub
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyyMMddHHmmss");
		String datetime = tempDate.format(new java.util.Date());
		/*
		 SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		 String datetime = tempDate.format(new java.util.Date());
		 */
		//        Calendar now=Calendar.getInstance();
		//        String time=now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH)+" "+now.get(Calendar.HOUR_OF_DAY)+":"+now.get(Calendar.MINUTE)+":"+now.get(Calendar.SECOND);
		return datetime;
	}
	public static String GetCurrentDateTimeFormatLong() {
		// TODO Auto-generated method stub
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		/*
		 SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		 String datetime = tempDate.format(new java.util.Date());
		 */
		//        Calendar now=Calendar.getInstance();
		//        String time=now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH)+" "+now.get(Calendar.HOUR_OF_DAY)+":"+now.get(Calendar.MINUTE)+":"+now.get(Calendar.SECOND);
		return datetime;
	}
	public static String GetCurrentDateTimeFormatShort() {
		// TODO Auto-generated method stub
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd");
		String datetime = tempDate.format(new java.util.Date());
		/*
		 SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		 String datetime = tempDate.format(new java.util.Date());
		 */
		//        Calendar now=Calendar.getInstance();
		//        String time=now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH)+" "+now.get(Calendar.HOUR_OF_DAY)+":"+now.get(Calendar.MINUTE)+":"+now.get(Calendar.SECOND);
		return datetime;
	}
	public static String GetDayAfter(String startDate, int interval) {
		String temp;
		int year;
		int month;
		int day;
		String yearStr = "";
		String monthStr = "";
		String dayStr = "";

		yearStr = startDate.substring(0, 4);
		year = Integer.parseInt(yearStr);
		monthStr = startDate.substring(4, 6);
		month = Integer.parseInt(monthStr);
		dayStr = startDate.substring(6, 8);
		day = Integer.parseInt(dayStr);
		GregorianCalendar gc = new GregorianCalendar(year, month - 1, day);
		gc.add(Calendar.DAY_OF_YEAR, interval);

		month = gc.get(Calendar.MONTH) + 1;
		day = gc.get(Calendar.DAY_OF_MONTH);

		monthStr = Integer.toString(month);
		dayStr = Integer.toString(day);

		if (month < 10) {
			monthStr = "0" + Integer.toString(month);
		}
		if (day < 10) {
			dayStr = "0" + Integer.toString(day);
		}

		temp = gc.get(Calendar.YEAR) + monthStr + dayStr;

		return temp;
	}
	public static int parseInt(String param) {
		try {
			return Integer.parseInt(param);
		} catch (Exception e) {
			return 0;
		}
	}

	public static boolean isEmpty(List list) {
		// TODO Auto-generated method stub
		if (list == null) {
			return true;
		} else if (list.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	//首字母转小写
    public static String toLowerCaseFirstOne(String s)
    {
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    //首字母转大写
    public static String toUpperCaseFirstOne(String s)
    {
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

	public static double parseDouble(String param) {
		try {
			return Double.parseDouble(param);
		} catch (Exception e) {
			return 0;
		}
	}

	public static boolean PatternEquals(String gs,String regExp) {
		// 原本标准是${[a]{3}},?表示0和或1个匹配(和*的区别是数量上),*表示任意多个用于代替{3}，.表示通配符用于代替[a]
		Matcher m = Pattern.compile(regExp, 0).matcher(gs);
		while (m.find()) {
			return true;
		}
		return false;
	}
	public static boolean withIn(String v, String[] v_ins) {
		if (v == null) {
			return false;
		}
		if (v_ins == null) {
			return false;
		}
		for (int i = 0; i < v_ins.length; i++) {
			if (v_ins[i].equals(v)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 加密
	 * 
	 * @param passwd
	 * @return
	 */
	public static String getEncrypt(String passwd) {
		if ("".equals(passwd) || passwd == null) {
			return "";
		}
		StringBuffer msg = new StringBuffer(64);
		int key_len = 8;
		int iTmp = 0;
		int key[] = { 0x4A, 0x50, 0x4C, 0x53, 0x43, 0x57, 0x43, 0x44 };
		for (int i = 0; i < passwd.length(); i++) {
			iTmp = (int) passwd.charAt(i) ^ key[i % key_len];
			msg.append((char) (((iTmp >> 4) & 0x0f) + 'a'));
			msg.append((char) ((iTmp & 0x0f) + 'a'));
		}
		return msg.toString();
	}

	/**
	 * 解密
	 * 
	 * @param passwd
	 * @return
	 */
	public static String getDecrypt(String passwd) {
		if ("".equals(passwd) || passwd == null) {
			return "";
		}
		int key[] = { 0x4A, 0x50, 0x4C, 0x53, 0x43, 0x57, 0x43, 0x44 };
		int key_len = 8;
		StringBuffer msg = new StringBuffer(64);
		int len = passwd.length() / 2;
		int iTmp, i;
		for (i = 0; i < len; i++) {
			iTmp = (passwd.charAt(i * 2) - 'a') << 4;
			iTmp |= passwd.charAt(i * 2 + 1) - 'a';
			msg.append((char) (iTmp ^ key[i % key_len]));
		}
		return msg.toString();
	}

	public static Node selectNodeByNodeName(NodeList childNodes, String _key) {
		// TODO Auto-generated method stub
		if(childNodes!=null){
			for(int i=0;i<childNodes.getLength();i++){
				String key=childNodes.item(i).getNodeName();
				String value=childNodes.item(i).getTextContent();
				if(_key.equals(key.substring(key.indexOf(":")+1, key.length()))){
					return childNodes.item(i);
				}
			}
		}
		return null;
	}

	public static String getNodeValueByNodeName(NodeList childNodes, String _key) {
		// TODO Auto-generated method stub
		Node node=ParaTool.selectNodeByNodeName(childNodes, _key);
		if(node==null){
			return "";
		}
		else{
			return node.getTextContent();
		}
	}
	
	public static String dividePercent(String value){
		try {
			value=new BigDecimal(value).divide(new BigDecimal("100")).toString();
			return value;
		} catch (Exception e) {
		}
		return "0";
	}
	public static String multiplyPercent(String value){
		try {
			value=new BigDecimal(value).multiply(new BigDecimal("100")).toString();
			return value;
		} catch (Exception e) {
		}
		return "0";
	}
	
	/*public static String GetProvinceCode() {
		// 判断jsession是否合法

		try {
			CodeListServDu codeListServDu = (CodeListServDu) ToolSpring.getBean("CodeListServDu");
			String provinceCode = codeListServDu
					.getCodeListByTypeCode("province_code");
			return provinceCode;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "cq";
		}

	}*/
	public static String analyzeXmlServiceName(String xml) {
		// TODO Auto-generated method stub
				String regExp = "SERVICE_NAME>.*?</";
				Matcher m = Pattern.compile(regExp, 0).matcher(xml);
				while (m.find()) {
					String match = m.group();
					String serviceName=match.substring(match.indexOf(">")+1, match.lastIndexOf("<"));
					return serviceName;
					// match = match.replaceAll("\\$\\{(.*?)\\}", "$1"); //
					// 替换去除左右两边的括号为空
					
				}
				return "";
	}
	
	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception { 
		Object ret = beanClass.newInstance();
		Field[] f = beanClass.getDeclaredFields();
		
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()) {
			Object key = iter.next();
			Object val = map.get(key);
			for (int j = 0; j < f.length; j++) {
				f[j].setAccessible(true);
				String name = f[j].getName();
				if (name.equals(key) && val != null) {
					try {
						f[j].set(ret, val);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						//private不处理
						//                                                e.printStackTrace();
					} catch (IllegalAccessException e) {
						// private不处理
						//                                                e.printStackTrace();
					}
					break;
				}
			}
		}
		return ret;
		
	}
}
