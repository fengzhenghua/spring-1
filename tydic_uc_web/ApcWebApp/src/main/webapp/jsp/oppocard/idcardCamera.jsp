<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common.jsp" %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta charset="utf-8">
<title>开卡激活-身份验证</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=yes" />
<link href="../../css/pc/common/oppoPublic.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../js/Plugin/jquery/jquery-1.11.1.min.js" ></script>
<script type="text/javascript" src="../../js/pc/oppocard/idcardCamera.js" ></script>
</head>

<body>
<div class="main-page" style="display:block;">
	<!-- 
	<div id="head_buttons" class="top black-bgcolor">
		<div id="allt_left" class="top_left"><span class="top_left_icon"></span><span class="white">返回</span></div>
		<div id="allt_right" class=""></div>
		<div class="top_title white top_title_small">开卡激活-身份验证</div>
	</div>
	<div class="top_blank"></div>
     -->
	<ul class="content clearfix">
    	<div class="padding_blank10"></div>
    	<div class="zhengmian zm-ie6 l">
	        <div class="content_sub_grey">
	        	<div class="padding_blank10"></div>
	        	<img src="../../images/pc/oppoCard/scene_shoot_bg.png" class="open_card_id" id="front_img"/>
	            <div class="padding_blank10"></div>
	        </div>
	        <div class="content_sub_grey">
	        	<div class="open_card_camera">
	            	<div class="open_card_camera_bg"></div>
	                <span id="front_text">正面拍照</span>
	            </div>
	        </div>
        </div>
        <!-- <div class="padding_blank10"></div> -->
        <div class="biemian bm-ie6 l">
	        <div class="content_sub_grey">
	        	<div class="padding_blank10"></div>
	        	<img src="../../images/pc/oppoCard/scene_shoot_bg.png" class="open_card_id" id="back_img"/>
	            <div class="padding_blank10"></div>
	        </div>
	         <div class="content_sub_grey">
	        	<div class="open_card_camera">
	            	<div class="open_card_camera_bg"></div>
	                <span id="back_text">背面拍照</span>
	            </div>
	        </div>
        </div>
        <div class="padding_blank10"></div>
        
        <li class="clear"></li>
        <li class="bottom_blank"></li>
            
	</ul>
	<!-- <div class="bottom">
		<div id="next_flow_step" class="next next_blank">下一步，人脸检测</div>
	</div> -->
	<div class="bbtn zb_width zb_ml zb_mt">
		<div id="next_flow_step">下一步，人脸检测</div>
	</div>
</div>

</body>
</html>