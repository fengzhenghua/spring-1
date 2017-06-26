<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.jasig.cas.client.authentication.AttributePrincipal"  %>
<%@ page import="java.util.*"  %>
<%@ page import="java.io.*"  %>
<%
	//获取rest访问地址
	String realPath = request.getRealPath("");
	realPath = realPath.replaceAll("\\\\", "/");
	int last = realPath.lastIndexOf("/");
	String subPath = realPath.substring(0, last);
	int lastSecond = subPath.lastIndexOf("/");
	subPath = subPath.substring(0, lastSecond);
	String path = subPath+"/conf/UocWebApp.properties";
	Properties props = new Properties();
	File file = new File(path);
	InputStream in = new FileInputStream(file);
	props.load(in);
	String host = props.getProperty("host");
	String card_reader_flag = props.getProperty("card_reader_flag");
	String gzt_flag = props.getProperty("gzt_flag");
	String task_time_ratio = props.getProperty("task_time_ratio");
	String tab_count_limit = props.getProperty("tab_count_limit");
	String cas_logout = props.getProperty("cas_logout");
	String sf_flag = props.getProperty("sf_flag");
	String sms_code = props.getProperty("sms_code");
	String device_adr = props.getProperty("device_adr");
	in.close();

	//获取jsessionid
	String jsession_id = "";
	AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
	 if (principal != null) {
		Map<String, Object> attributes = principal.getAttributes();
		if (attributes != null) {
			if (attributes.get("jsession_id") != null) {
				jsession_id = attributes.get("jsession_id").toString();
			}
		}
	}
%>
<input type="hidden" id="jsession_id" value="<%=jsession_id %>"/>
<input type="hidden" id="rest_host" value="<%=host %>"/>
<input type="hidden" id="card_reader_flag" value="<%=card_reader_flag %>"/>
<input type="hidden" id="gzt_flag" value="<%=gzt_flag %>"/>
<input type="hidden" id="task_time_ratio" value="<%=task_time_ratio %>"/>
<input type="hidden" id="tab_count_limit" value="<%=tab_count_limit %>"/>
<input type="hidden" id="cas_logout" value="<%=cas_logout %>"/>
<input type="hidden" id="sf_flag" value="<%=sf_flag %>"/>
<input type="hidden" id="sms_code" value="<%=sms_code %>"/>
<input type="hidden" id="device_adr" value="<%=device_adr %>"/>
