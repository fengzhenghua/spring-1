<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="appResult.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta charset="utf-8">
	<title>收银台</title>
	<script type="text/javascript" src="<%=fullPath%>js/appBusi/appPayZhifubaoBarCode.js"></script>	
</head>
<body>
	<div class="main-page">
		<div class="top">
			<div class="top_left" id="clickBack"><span class="top_left_icon"></span><span>返回</span></div>
			<div class="top_title">支付宝条码支付</div>
		</div>
		<div class="top_blank"></div>
		<section>
			<p class="order-money">订单金额:<b class="color-red" id="real_fee">${info_order.real_fee}</b>元</p>
			<input type = "hidden" id="order_id" value="${info_order.order_id}"/>
			<input type = "hidden" id="pay_type" value="${pay_type}"/>
			<div class="bar-inputbox"><label>条码串号：</label><input type="text" class="bar-code" id="authCode"/></div>
			<div class="prompt">
				<P class="bar-prompt">请打开支付宝->付款功能</P>
				<P class="bar-prompt">用扫描枪或手输用户的付款条码串号</P>	
			</div>		
			<div class="bar-btn btn-red" id="payBtn">确认支付</div>
		</section>
	</div>
	<!-- 支付成功 -->
	<!-- <div class="pay-succ" style="display:none;">
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