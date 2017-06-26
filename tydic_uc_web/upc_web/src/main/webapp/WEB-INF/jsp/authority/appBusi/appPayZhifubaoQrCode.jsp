<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ page import="com.tydic.unicom.util.DateUtil"%> 
 <%@include file="../common/commonapp.jsp"%>--%>
<%@include file="appResult.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta charset="utf-8">
	<title>收银台</title>
<%-- 	<link href="<%=fullPath%>css/common/public.css" rel="stylesheet" type="text/css" />
	<link href="<%=fullPath%>css/appCss/payCommon.css" rel="stylesheet" type="text/css" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=yes" />
	<link href="<%=fullPath%>css/appCss/productList.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=fullPath%>js/jquery/jquery.min.js" ></script>
	<script type="text/javascript" src="<%=fullPath%>js/WebUtil-cq.js"></script> --%>
	<script type="text/javascript" src="<%=fullPath%>js/common/jquery.qrcode.min.js" ></script>
	<script type="text/javascript" src="<%=fullPath%>js/appBusi/appPayZhifubaoQrCode.js"></script>	
</head>
<body>
	<div class="main-page">
		<div class="top">
			<div class="top_left" id="clickBack"><span class="top_left_icon"></span><span>返回</span></div>
			<div class="top_title">支付宝二维码支付</div>
		</div>
		<div class="top_blank"></div>
		<section>
		<p class="order-money">订单金额:<b class="color-red" id="real_fee">${info_order.real_fee}</b>元</p>
		<input type = "hidden" id="order_id" value="${info_order.order_id}"/>
		<input type = "hidden" id="pay_type" value="${pay_type}"/>
<%-- 		<p class="order-money">订单信息<span class="color-red" id="order_id">${info_order.order_id}</span></p> --%>
<%-- 		<p class="order-money">支付类型<span class="color-red" id="pay_type">${pay_type}</span></p> --%>
			<div class="qr-code" id="qrCode">
				<p>正在加载...</p>
			</div>
			<div class="prompt">
				<P class="bar-prompt" id="hint1">二维码将在<span class="color-red" id="expiryDate">115</span>秒后自动失效</P>
				<P class="bar-prompt" id="hint2" style="display:none;">二维码已失效，<a href="#" id="retrieve">重新获取</a></P>
				<P class="bar-prompt">请使用支付宝扫一扫上面的二维码进行支付</P>	
			</div>		
		</section>
	</div>
	<!-- 支付成功 -->
<!-- 	<div class="pay-succ" style="display:none;">
		<div class="icon icon-pay icon-pay-succ align-center"></div>
		<p class="align-center">支付成功</p>
		<p class="align-center">页面将在<b class="color-red" id="num1">5</b>秒后进行跳转</p>
		<div class="bar-btn btn-green" id="jump1">立即跳转</div>		
	</div>
	支付失败
	<div class="pay-false" style="display:none;">
		<div class="icon icon-pay icon-pay-false align-center"></div>
		<p class="align-center">支付失败</p>
		<p class="align-center">页面将在<b class="color-red" id="num2">5</b>秒后进行跳转</p>
		<div class="bar-btn btn-green" id="jump2">立即跳转</div>		
	</div> -->
	</body>
</html>