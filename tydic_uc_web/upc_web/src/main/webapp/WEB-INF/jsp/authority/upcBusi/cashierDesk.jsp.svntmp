<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tydic.unicom.util.DateUtil"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>收银台</title>

<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/payCommon.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/upcBusi/cashierDesk.js"></script>
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
					<p class="order_more" id="order_more_btn"><span>订单详情<i>▼</i></span></p>
				</div>
			</div>
			
			<div class="payment_info" id="payChoice">
				<div class="payment_title">
					<span class="payment_tag">支付方式</span>
				</div>
				<div class="payment_list">
					<c:forEach var="pt" items="${payTypeList}">
						<c:choose>
                           <c:when test="${pt.pay_type=='21' && pt.show_flag=='0'}">
	                           <div class="payment_mode" id="payZhifubaoQrCode"><!-- 支付宝二维码 -->
									<i class="icon icon_alipay"></i>
									<p class="describe">二维码支付</p>
								</div>
                           </c:when>
                           
                           <c:when test="${pt.pay_type=='11' && pt.show_flag=='0'}">
		                        <div class="payment_mode" id="wxQrPayChoice"><!-- 微信二维码 -->
									<i class="icon icon_wechat"></i>
									<p class="describe">二维码支付</p>
								</div>
                           </c:when>
                           
                           <c:when test="${pt.pay_type=='20' && pt.show_flag=='0'}">
                           		<div class="payment_mode" id="payZhifubaoBarCode"><!-- 支付宝条形码 -->
									<i class="icon icon_alipay"></i>
									<p class="describe">条形码支付</p>
								</div>
                           </c:when>
                           
                           <c:when test="${pt.pay_type=='10' && pt.show_flag=='0'}">
                           		<div class="payment_mode" id="wxScanPayChoice"><!-- 微信条形码 -->
									<i class="icon icon_wechat"></i>
									<p class="describe">条形码支付</p>
								</div>
                           </c:when>
                           
                            <c:when test="${pt.pay_type=='30' && pt.show_flag=='0'}">
                           		<div class="payment_mode" id="cashPay"><!-- 微信条形码 -->
									<i class="icon icon-cash"></i>
									<p class="describe">现金支付</p>
								</div>
                           </c:when>
                        </c:choose>
					</c:forEach>
				</div>
			</div>
		</div>
		<div class="padding_blank"></div>
	</div>

	<div class="bg_mask" id="bg_mask">
		<iframe class="bg_mask_iframe"></iframe>
	</div>
	
	
	<div class="pop_win" id="goods_detail_div" style="display:none">
	    <div class="msgbox">
	    	<a href="javascript:hidediv('goods_detail_div');"><div class="msgbox_close"></div></a>
	        <ul class="text_big">
	            <li><span class="bold">订单总金额：<span  id="real_fee" class="red" id=""><fmt:formatNumber value="${info_order.real_fee}" pattern="### ##0.00"/></span>元</p></span></li>
	        </ul>
	        <div class="scroll_v">
				<ul id="goods_detail_list" style="font-size:14px;">
					<li class="list"><div class="left"><div class="left_lable">测试</div><div class="right_data">300.00元</div></div></li>
					<li class="list"><div class="left"><div class="left_lable">测试</div><div class="right_data">300.00元</div></div></li>
					<li class="list"><div class="left"><div class="left_lable">测试</div><div class="right_data">300.00元</div></div></li>
					<li class="list"><div class="left"><div class="left_lable">测试</div><div class="right_data">300.00元</div></div></li>
					<li class="list"><div class="left"><div class="left_lable">测试</div><div class="right_data">300.00元</div></div></li>
		        </ul>
	        </div>
	        <ul>
		        <li class="center">
		        <a href="javascript:hidediv('goods_detail_div');"><div class="input_button">确 定</div></a> 
		        </li>
	        </ul>   
	        
	    </div>
	</div>
	
	<input type="hidden" id="order_id" value="${info_order.order_id}"/>
	
	<div class="copyright"> Copyright © 2014 China unicom All Right Reserved</div>
</body>
</html>