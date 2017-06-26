<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta charset="utf-8">
<title>开卡激活-录制检验</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=yes" />
<link href="../../css/pc/common/oppoPublic.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../js/Plugin/jquery/jquery-1.11.1.min.js" ></script>
<script type="text/javascript" src="../../js/pc/oppocard/openCardActiveVideo.js" ></script>
</head>

<body>
<div class="main-page" style="display:block;">
	<ul class="content">
    	<div class="scene_shoot nobgcolor">
            <div class="scene_shoot_img">
                <img src="../../images/pc/oppoCard/open_card_video.jpg" style="width:88%;height:378px;+width:82%;+height:366px;border:0;"/>
                
                <input type="file" id="video_file" name="file"  value="上传" accept="image/*;capture=camera" style="display:none;" />
                <button id="video_upload_btn" style="display:none;">视频上传</button>
            </div>
        </div>
        
        <div class="float_center" style="width:70%;>width:100%;">
        	请使用<span class="red">前置摄像头</span>，录制<span class="red">6秒</span>视频录制时请<span class="red">匀速朗读</span>一遍下方数字
        </div>
        <div class="padding_blank10"></div>
        
        <div class="float_center">
            <div class="open_card_number_list">
                <div class="open_card_number">3</div>
                <div class="open_card_number">6</div>
                <div class="open_card_number">0</div>
                <div class="open_card_number">8</div>
            </div>
        </div>
        
        <li class="clear"></li>
        <li class="bottom_blank"></li>
            
	</ul>
	<!-- <div class="bottom">
		<div id="next_flow_step" class="next next_blank">准备好了，开始录制</div>
	</div> -->
	<div class="bbtn zb_width zb_ml">
		<div id="next_flow_step">准备好了，开始录制</div>
	</div> 
</div>

<div class="dialog" id = "pop_confirm">
	<div class="tip">活体校验通过。请等待内容审核</div>
	<hr>
	<div class="btn"><a href="javascript:;" id="pop_btn_oK">确定关闭</a></div>
</div>
</body>
</html>