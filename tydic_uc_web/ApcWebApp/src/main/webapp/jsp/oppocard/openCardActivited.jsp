<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta charset="utf-8">
<title>开卡激活</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=yes" />
<link href="../../css/pc/common/oppoPublic.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../js/Plugin/jquery/jquery-1.11.1.min.js" ></script>
<script type="text/javascript" src="../../js/pc/oppocard/openCardActivited.js" ></script>

</head>

<body>
<div class="main-page" style="display:block;">
	<!-- 
	<div id="head_buttons" class="top black-bgcolor">
		<div id="allt_left" class="top_left"></div>
		<div id="allt_right" class=""></div>
		<div class="top_title white">开卡激活</div>
	</div>
	<div class="top_blank"></div>
     -->
	<ul class="content char6">
		<li class="c-li">
		    <span class="c-one">1</span>
            <span class="c-shuru">输入订单信息验证</span>
        </li> 
		<li class="c-li height-ie6 clearfix  background">
			<div class="lianxi l ml_ie"><span class="lx-phone">联系电话：</span><input class="input-ie6" type="text" placeholder="" value="" id="contact_phone"></div>
			<div class="lianxi l"><span class="eight">ICCID后8位：</span><input class="input-ie6" type="text" placeholder="" value="" id="iccid"></div>
		</li>
        <div class=" padding_blank"></div>
        <li class="bao">
	        <div class="float_center">
	        	<div class="open_card_iccid_title">您收到的电话卡</div>
	        </div>
	        
	        <div class="float_center">
	            <div class="open_card_active">
	            	<div class="open_card_iccid_tip">ICCID后8位在这里</div>
	            </div>
	        </div>
	     </li>
		<li class="clear"></li>
		<li class="bottom_blank"></li>
	</ul>
	<!-- <div class="bottom">
		<div id="next_flow_step" class="next next_blank">下一步，身份验证</div>
	</div> -->
	<div class="bbtn zb_width zb_ml">
		<div id="next_flow_step">下一步，身份验证</div>
	</div>
</div>

</body>
</html>