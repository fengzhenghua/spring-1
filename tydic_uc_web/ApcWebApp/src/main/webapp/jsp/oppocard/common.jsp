<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	String path = subPath+"/conf/ApcWebApp.properties";
	Properties props = new Properties();
	File file = new File(path);
	InputStream in = new FileInputStream(file);
	props.load(in);
	String host = props.getProperty("apc.rest.host");
	in.close();
%>
<input type="hidden" id="apc_rest_host" value="<%=host %>"/>