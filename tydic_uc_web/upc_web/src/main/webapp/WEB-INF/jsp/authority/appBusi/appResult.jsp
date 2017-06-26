<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tydic.unicom.util.DateUtil"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta charset="utf-8">
	<link href="<%=fullPath%>css/common/public.css" rel="stylesheet" type="text/css" />
	<link href="<%=fullPath%>css/appCss/cashierDesk.css" rel="stylesheet" type="text/css" />
	<link href="<%=fullPath%>css/appCss/payCommon.css" rel="stylesheet" type="text/css" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=yes" />
<%-- 	<script type="text/javascript" src="<%=fullPath%>js/jquery/jquery.min.js" ></script> --%>
<%-- 	<script type="text/javascript" src="<%=fullPath%>js/WebUtil-cq.js"></script> --%>
	<script type="text/javascript" src="<%=fullPath%>js/appBusi/appResult.js"></script>
</head>
<body>
	<div class="pay-succ" style="display:none;">
		<div class="icon icon-pay icon-pay-succ align-center"></div>
		<p class="align-center">支付成功</p>
		<p class="align-center">页面将在<b class="color-red" id="num1">5</b>秒后进行跳转</p>
		<div class="bar-btn btn-green" id="jump1">立即跳转</div>		
	</div>
	<!-- 支付失败 -->
	<div class="pay-false" style="display:none;">
		<div class="icon icon-pay icon-pay-false align-center"></div>
		<p class="align-center">支付失败</p>
		<p id="PayMsgText"></p>
		<p class="align-center">页面将在<b class="color-red" id="num2">5</b>秒后进行跳转</p>
		<div class="bar-btn btn-green" id="jump2">立即跳转</div>		
	</div>

	<!-- <div id="appPayMsg" class="dialog"
		style="z-index: 399; display: none">
		<div id="PayMsgText" class="tip"></div>
		<div class="btn">
			<a href="javascript:closePayMsg();" id="hrefPayMsgOk">确 定</a>
		</div>
	</div> -->
</body>
</html>