<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tydic.unicom.util.DateUtil"%>
<%@ taglib prefix="t" uri="http://tydic.com/jsp/core"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>收银台</title>

<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/payCommon.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/upcBusi/payZhifubaoBarCode.js"></script>
<!--[if lt IE 9]>
    <script type="text/javascript" src="<%=fullPath%>js/json2.js"></script>
<![endif]-->

</head>
<body>
	<div class="show">
		<div class="top_area">
			<div class="title_info">
				<span class="title_name">收银台</span>
			</div>
		</div>
		<div class="middle_area">
			<div class="order_info">
				<div class="order_left">
					<p class="order_prompt1">订单提交成功，请您尽快付款！订单号：<span id=""><c:out value="${info_order.order_id}"/></span></p>
					<p class="order_prompt2">请您在提交订单后<span class="red" id="">24小时</span>内完成支付，否则订单会自动取消。</p>
				</div>
				<div class="order_right">
					<p class="order_money">应付金额<span class="red" id=""><fmt:formatNumber value="${info_order.real_fee}" pattern="### ##0.00"/></span>元</p>
					<p class="order_more"><span>订单详情<i>▼</i></span></p>
				</div>
			</div>
			<div class="payment_info">
				<div class="payment_title">
					<span class="payment_label">支付宝支付</span>
				</div>
				<div class="pay_code_area">
					<div class="pay_code_left">
						<div class="bar_code">
							<div class="bar_title">条码串号：</div>
							<div class="bar_inputbox"><input type="text" id="authCode"/></div>
							<div class="bar_prompt">提示：用扫描枪或手输用户的付款条码串号</div>
							<div class="bar_btn" id="scanBtn">确认支付</div>
						</div>
					</div>
					<div class="pay_code_right">
						<div class="prompt_pic phone_barcode"></div>
					</div>
					<div class="clear"></div>
				</div>
				<div class="payment_change">
					<a class="payment_a" href="javascript:void(0);">
						<i class="arrow_left">&lt;</i>
						<strong>选择其他支付方式</strong>
					</a>
				</div>
			</div>
		</div>
		<div class="padding_blank"></div>
		<div class="padding_blank"></div>
	</div>

	<div class="bg_mask" id="bg_mask">
		<iframe class="bg_mask_iframe"></iframe>
	</div>
	
	<input type="hidden" id="order_id" value="${info_order.order_id}"/>
	<input type="hidden" id="pay_type" value="${pay_type}"/>
	
	<div class="copyright"> Copyright © 2014 China unicom All Right Reserved</div>
</body>
</html>