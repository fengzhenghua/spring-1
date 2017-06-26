<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ page import="com.tydic.unicom.util.DateUtil"%> 
 <%@include file="../common/commonapp.jsp"%>--%>
<%@include file="appResult.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta charset="utf-8">
	<title>收银台</title>
<%-- <link href="<%=fullPath%>css/common/public.css" rel="stylesheet" type="text/css" />
	<link href="<%=fullPath%>css/appCss/cashierDesk.css" rel="stylesheet" type="text/css" />
	<link href="<%=fullPath%>css/appCss/payCommon.css" rel="stylesheet" type="text/css" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=yes" />
	<script type="text/javascript" src="<%=fullPath%>js/jquery/jquery.min.js" ></script>
	<script type="text/javascript" src="<%=fullPath%>js/WebUtil-cq.js"></script> --%>
	<script type="text/javascript" src="<%=fullPath%>js/appBusi/appCashierDesk.js"></script>
</head>
<body>
	<div class="main-page">
		<div id="head_buttons" class="top">
			<div class="top_left" id="clickBack"><span class="top_left_icon"></span><span>返回</span></div>
			<div class="top_title">收银台</div>
		</div>
		<div class="top_blank"></div>
		<ul class="border-bottom padding5">
			<li class="item"><span class="fl">订单金额</span><span class="fr color-gray">￥<b class="pay-money color-red" id="real_fee">${info_order.real_fee}</b></span></li>
			<li class="item"><span class="fl">订单编号</span><span id = "order_id" class="fr color-gray">${info_order.order_id}</span></li>
			<li class="item"><span class="fl">商品描述</span><span class="fr color-gray">${info_order.detail_name}</span></li>
		</ul>
		<div class="separator"></div>
		<div class="method border-bottom pay-title">支付方式</div>
		<ul class="char-icon" id="payment_list">
		<c:forEach var="pt" items="${payTypeList}">
		<c:choose>
		 <c:when test="${pt.pay_type=='21' && pt.show_flag=='0'}">
			<li class="payment_mode list-lg" id="payZhifubaoQrCode">
				<div class="left">
					<div class="oneline">
					<span class="icon-left-lgicon icon-alipay"></span>
				</div>
				</div>
				<div class="icon-right icon-more"></div>
				<div class="mid">
					<div class="holder">
						<div class="pay-title">支付宝支付</div>
						<div class="pay-tip">二维码支付</div>
					</div>
				</div>
			</li>
		</c:when>
		
		 <c:when test="${pt.pay_type=='11' && pt.show_flag=='0'}">
			<li class="payment_mode list-lg" id="payWeixinQrCode">
				<div class="left">
					<div class="oneline">
					<span class="icon-left-lgicon icon-wechat"></span>
				</div>
				</div>
				<div class="icon-right icon-more"></div>
				<div class="mid">
					<div class="holder">
						<div class="pay-title">微信支付</div>
						<div class="pay-tip">二维码支付</div>
					</div>
				</div>
			</li>
		</c:when>
		
		 <c:when test="${pt.pay_type=='20' && pt.show_flag=='0'}">
			<li class="payment_mode list-lg" id="payZhifubaoBarCode">
				<div class="left">
					<div class="oneline">
					<span class="icon-left-lgicon icon-alipay"></span>
				</div>
				</div>
				<div class="icon-right icon-more"></div>
				<div class="mid">
					<div class="holder">
						<div class="pay-title">支付宝支付</div>
						<div class="pay-tip">条码支付</div>
					</div>
				</div>
			</li>
		</c:when>
		
		 <c:when test="${pt.pay_type=='10' && pt.show_flag=='0'}">
			<li class="payment_mode list-lg" id="payWeixinBarCode">
				<div class="left">
					<div class="oneline">
					<span class="icon-left-lgicon icon-wechat"></span>
				</div>
				</div>
				<div class="icon-right icon-more"></div>
				<div class="mid">
					<div class="holder">
						<div class="pay-title">微信支付</div>
						<div class="pay-tip">条码支付</div>
					</div>
				</div>
			</li>
		</c:when>
		
		 <c:when test="${pt.pay_type=='30' && pt.show_flag=='0'}">
			<li class="payment_mode list-lg" id="payInCash">
				<div class="left">
					<div class="oneline">
					<span class="icon-left-lgicon icon-cash"></span>
				</div>
				</div>
				<div class="mid">
					<div class="holder">
						<div class="pay-title">现金支付</div>
						<div class="pay-tip">确认收款金额</div>
					</div>
				</div>
			</li>
		</c:when>
		</c:choose>
		</c:forEach>
	</ul>
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


