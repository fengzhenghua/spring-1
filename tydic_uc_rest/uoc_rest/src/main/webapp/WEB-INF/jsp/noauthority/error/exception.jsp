<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*" isErrorPage="true"%>
<%@ page import="com.tydic.unicom.exception.BusinessException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/common.jsp"%>
<%
	String oper_no = session.getAttribute("oper_no").toString();
	BusinessException businessException = null;
	try {
		businessException = (BusinessException) exception;
	} catch (Exception e) {

	}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>统一销售服务系统</title>
<link href="<%=fullPath%>css/error.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var times = 6;
function clock() {
	times = times - 1;
	document.getElementById('time').innerHTML = times;
	if (times <= 0) {
		var urlherf = '<%=fullPath%>authority/index/index?oper_no='+'<%=oper_no%>';
		if (self != top) {
			window.parent.document.location.href = urlherf;
		} else {
			window.location.href = urlherf;
		}
	} else {
		window.setTimeout('clock()', 1000);
	}
}

function backIndex() {
	var urlherf = '<%=fullPath%>authority/index/index?oper_no='+'<%=oper_no%>';
	if (self != top) {
		window.parent.document.location.href = urlherf;
	} else {
		window.location.href = urlherf;
	}
}
function returnBtnClick(url) {
	var urlherf = '<%=fullPath%>'+url;
	if (self != top) {
		window.parent.document.location.href = urlherf;
	} else {
		window.location.href = urlherf;
	}
}
</script>
</head>
<body onload="clock();">
	<div class="sale_header">
		<div class="sale_logo">
			<img src="<%=fullPath%>/images/sale_logo.jpg" />
		</div>
		<div class="sale_header_right"></div>
		<div class="clear"></div>
	</div>
	<div class="sale_nav_bg"></div>
	<div class="content">
		<div class="error">
			<%
				if (null != businessException) {
			%>
			<p class="yellow"><%=businessException.getTitle()%>
			</p>
			<p class="reason">
				错误原因：<%=businessException.getContent()%>
			</p>
			<p class="back">
				<strong id="time">5</strong>秒之后页面自动跳转，您还可以：<a href="javascript:;"
					onclick="returnBtnClick('<%=businessException.getReturnBtnUrl()%>');"><%=businessException.getReturnBtnTxt()%></a>
			</p>
			<%
				} else {
			%>
			<p class="yellow">抱歉，您访问的页面地址有误或不存在！</p>
			<p class="reason">
				错误原因：<%=exception.getMessage()%></p>
			<p class="back">
				<strong id="time">5</strong>秒之后页面自动跳转，您还可以：<a href="javascript:;"
					onclick="backIndex();">返回首页</a>
			</p>
			<%
				}
			%>
		</div>
	</div>
</body>
</html>
