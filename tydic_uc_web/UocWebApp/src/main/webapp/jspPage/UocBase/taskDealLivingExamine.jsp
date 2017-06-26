<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="../../style/css/taskDealLivingExamine.css" />
<script type="text/javascript" src="../../js/UocBaseJs/taskDealLivingExamine.js"></script>
<!-- 活体审核-弹出页面 -->
<link href="../../js/video-js/video-js.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../js/video-js/video.js"></script>
<script>
    videojs.options.flash.swf = '../../js/video-js/video-js.swf';
</script>
<!-- 活体审核 -->
<div class="detail_item">
	<ul class="detail_info_head">
		<li class="line">
			<div>活体审核</div>
		</li>
	</ul>
	<ul class="detail_info_body">
		<li class="line">
			<div class="bold">入网资料详情</div>
		</li>
		<li class="line">
			<div class="one_third">
				证件姓名：<span id="idcardName"></span>
			</div><div class="one_third">
				证件号码：<span id="idcardNum"></span>
			</div><div class="one_third">
				证件地址：<span id="idcardAddr"></span>
			</div>
		</li>
		<li class="line">
			<div class="image_area">
				<div class="thumbnail_box">
					<ul id="thumbnailList">
						<li><img id="showPic1" src="../../style/images/photo_default.jpg" width="100%" height="100%"/></li>
						<li><img id="showPic2" src="../../style/images/photo_default.jpg" width="100%" height="100%"/></li>
						<li><img id="showPic3" src="../../style/images/photo_default.jpg" width="100%" height="100%"/></li>
						<li><img id="showPic4" src="../../style/images/photo_default.jpg" width="100%" height="100%"/></li>
						<li><img id="showPic5" src="../../style/images/photo_default.jpg" width="100%" height="100%"/></li>
						<li><img id="showPic6" src="../../style/images/photo_default.jpg" width="100%" height="100%"/></li>
						<li><img id="showPic7" src="../../style/images/photo_default.jpg" width="100%" height="100%"/></li>
						<li type="video"><img id="showVideo" src="../../style/images/video_default.png" width="100%" height="100%"/></li>
					</ul>
				</div>
				<div class="photo_box" id="photoBox">
					<img src="../../style/images/photo_default.jpg" height="100%"/>
				</div>
				<div id="videoBox" style="display:none;">
					<video id="videoPlayer" class="video-js vjs-default-skin vjs-big-play-centered" controls preload="none"
								width="490" height="370" poster="../../style/images/video_default.png" data-setup="{}">
							<source id="video_source1" src="" type='video/mp4' />
					</video>
				</div>
			</div>
			<div class="idcard_area">
				<img src="../../style/images/idcard.jpg" width="100%" height="100%"/>
			</div>
		</li>
		<li class="line">
			<div class="btn btn_primary" id="livingExaPassBtn">通过</div>
			<div class="btn btn_error" id="revokeOrderBtn">撤单</div>
			<div class="btn btn_error" id="forceFlowBtn">强制流转</div>
		</li>
	</ul>
</div>
