<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="format-detection" content="telephone=no" />
<title>微信支付</title>
<link href="../../css/pc/common/oppoPublic.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../js/pc/oppocard/pay.js" ></script>

<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=yes" />
</head>

<body>
<div class="main-page" style="display:block;">
	<div class="top_blank"></div>
	 
	<ul class="content">
    	<div class="padding_blank"></div>
        
    	<div class="QR_code">
        	<img src="" id="PIC_QR"/>
        </div>
        <div class="padding_blank10"></div>
        <div class="float_center">
        	<div class="red">微信支付：<span><span id="fee_all"></span> 元</span></div>
            <div class="padding-lr" id="tip1">请长按图片识别二维码进行支付，<p>或用另一台手机的微信扫描屏幕上的二维码支付；</div>
            <div class="padding-lr" id="tip2">支付完成前请不要关闭此页面。</div>
        </div>
        
        

		<li class="clear"></li>
		<li class="padding_blank"></li>
	</ul>
	<div class="bottom" style="display:none" id="bottom_done">
		<div id="next_flow_step" class="next next_blank">完 成</div>
	</div>
</div>

</body>
</html>


