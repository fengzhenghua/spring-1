<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>订单详情</title>
	
    <link rel="stylesheet" type="text/css" href="../../style/css/public.css" />
    <link rel="stylesheet" type="text/css" href="../../style/css/orderDetail.css" />
    <link rel="stylesheet" type="text/css" href="../../style/css/taskDealLivingExamine.css" />
    <script type="text/javascript" src="../../js/common/jquery-1.11.1.min.js"></script>
    <!-- <script type="text/javascript" src="../../js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../../js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script> -->
    <script type="text/javascript" src="../../js/common/WebUtil.js"></script>
    <script type="text/javascript" src="../../js/common/public.js"></script>
    <script type="text/javascript" src="../../js/UocBaseJs/orderDetail.js"></script>
  <!-- 活体审核-弹出页面 -->
<link href="../../js/video-js/video-js.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../js/video-js/video.js"></script>
<script>
    videojs.options.flash.swf = '../../js/video-js/video-js.swf';
</script>
   
</head>
<body>
	<div class="container">
		<div class="menu_bar">
			<div class="menu_head">
				<div class="min_order">
					<div class="left font16">订单列表</div>
					<!-- <div class="right menu_switch_btn">&gt;&gt;</div> -->
				</div>
			</div>
			<div class="menu_body">
				<div class="issue">
					<div class="min_order">
						<div class="order_level1" is_parent="true" is_loaded="true" id="master_service">
							<!-- <b on_off="on">▲</b><i class="icon_sale"></i><span id="sale_order_no">S123456123456789987654</span> -->
							<!-- <div class="order_level2" is_parent="true" is_loaded="true">
								<b on_off="on">▲</b><i class="icon_goods"></i><span id="F2016121312345611111">F2016121312345611111</span>
								<div class="order_level3" is_parent="false" is_loaded="true">
									<i class="icon_service"></i><span class="active" id="E2016121312345611111">E2016121312345611111</span>
								</div>
								<div class="order_level3" is_parent="false" is_loaded="true">
									<i class="icon_service"></i><span>serv123456789012345678</span>
								</div>
							</div>
							<div class="order_level2" is_parent="true" is_loaded="false">
								<b on_off="off">▼</b><i class="icon_goods"></i><span id="F20161213123456111111">F20161213123456111111</span>
							</div> -->
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="detail_area">
			<!-- 流程图 -->
			<div class="detail_item">
				<div class="flow_title">当前订单<span id="select_order_no"></span>已经进入分配处理1小时1分钟，请尽快处理！</div>
				<div class="flow_chart" id="proc_view_list">
					<!-- 需在流程节点加载完后设置ul宽度：ul宽度 = (200 * 节点数 - 124)px -->
					<!-- <ul class="flow_list grab" style="width:1076px;">
						<li class="flow_node done">
							<b>1</b>
							<div>
								<p>流程匹配</p>
								<p>2016-12-13<br/>10:47:40</p>
							</div>
						</li>
						<li class="flow_arrow done"></li>
						<li class="flow_node doing">
							<b>2</b>
							<div>
								<p>分配处理</p>
								<p>2016-12-13<br/>10:48:29</p>
							</div>
						</li>
						<li class="flow_arrow wait"></li>
						<li class="flow_node wait">
							<b>3</b>
							<div>
								<p>外呼确认</p>
							</div>
						</li>
						<li class="flow_arrow wait"></li>
						<li class="flow_node wait">
							<b>4</b>
							<div>
								<p>开户处理</p>
							</div>
						</li>
						<li class="flow_arrow wait"></li>
						<li class="flow_node wait">
							<b>5</b>
							<div>
								<p>其他处理</p>
							</div>
						</li>
						<li class="flow_arrow wait"></li>
						<li class="flow_node wait">
							<b>6</b>
							<div>
								<p>其他处理</p>
							</div>
						</li>
					</ul> -->
				</div>
			</div>
			<!-- 流程操作记录 -->
			<div class="detail_item">
				<ul class="flow_log_head">
					<li class="line">
						<i class="pucker_down" control="foldable">▼</i>
						<div class="width20per">操作时间</div><div class="width15per">操作类型</div><div class="width50per">操作描述</div><div>操作员工</div>
					</li>
				</ul>
				<ul class="flow_log_body" id="proc_log_list">
				</ul>
				<!-- <ul class="flow_log_body">
					<li class="line" on_off="on">
						<div class="width20per">2016-12-13 10:47:40</div><div class="width15per">流程匹配</div><div class="width50per">匹配成功</div><div class="width15per">CJF10012</div>
					</li>
					<li class="line" on_off="on">
						<div class="width20per">2016-12-13 11:48:29</div><div class="width15per">分配处理</div><div class="width50per">分配完成</div><div class="width15per">CJF10012</div>
					</li>
					<li class="line" on_off="on">
						<div class="width20per">2016-12-13 11:48:29</div><div class="width15per">其他处理</div><div class="width50per">XXXXXXXX</div><div class="width15per">CJF10012</div>
					</li>
					<li class="line hide" on_off="off">
						<div class="width20per">2016-12-13 11:48:29</div><div class="width15per">其他处理</div><div class="width50per">XXXXXXXX</div><div class="width15per">CJF10012</div>
					</li>
					<li class="line hide" on_off="off">
						<div class="width20per">2016-12-13 11:48:29</div><div class="width15per">其他处理</div><div class="width50per">XXXXXXXX</div><div class="width15per">CJF10012</div>
					</li>
				</ul> -->
			</div>
			<!-- 客户信息 -->
			<div class="detail_item" id="order_detail">
				<!-- <ul class="detail_info_head">
					<li class="line">
						<div>客户信息</div>
					</li>
				</ul>
				<ul class="detail_info_body">
					<li class="line">
						<div class="one_third">客户名称：张三</div><div class="one_third">证件号码：5XXXXXXXXXXXXXXXXX</div><div class="one_third">证件地址：重庆市渝中区XX路XX小区</div>
					</li>
					<li class="line">
						<div class="one_third">客户名称：张三</div><div class="one_third">证件号码：5XXXXXXXXXXXXXXXXX</div><div class="one_third">证件地址：重庆市渝中区XX路XX小区XX单元XX-XX号</div>
					</li>
				</ul> -->
			</div>
			<!-- 活体审核 -->
			<div class="detail_item" id="live_check">
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
						</div><div class="idcard_area">
							<img src="../../style/images/idcard.jpg" width="100%" height="100%"/>
						</div>
					</li>
				</ul>
			</div>
			<!-- 物流信息 -->
			<div class="detail_item" id="logisticsInfo">
				<ul class="detail_info_head">
					<li class="line">
						<div class="mright10">物流信息</div><div class="btn btn_default font12" id="sendGoodsAgainBtn">► 重发货</div>
					</li>
				</ul>
				<ul class="detail_info_body" id="logisticsList">					
					<li class="line"><div class="width20per">无物流信息</div></li>
					<!-- <li class="line first">
						<div class="width20per">2016-12-13 11:48:29</div><div class="width80per">收件，收件人：张三</div>
					</li>
					<li class="line">
						<div class="width20per">2016-12-13 11:48:29</div><div class="width80per">您的订单已经发货</div>
					</li>
					<li class="line">
						<div class="width20per">2016-12-13 11:48:29</div><div class="width80per">您的订单已经拣货完成</div>
					</li>
					<li class="line">
						<div class="width20per">2016-12-13 11:48:29</div><div class="width80per">您的订单已经打印完毕</div>
					</li>
					<li class="line">
						<div class="width20per">2016-12-13 11:48:29</div><div class="width80per">您提交了订单，请等待系统确认</div>
					</li> -->
				</ul>
			</div>
		</div>
	</div>
	<!-- 发货-弹出页面 -->
	<div class="pop_container" id="popSendGoods" style="display:none;">
		<div class="pop_panel width70per">
			<div class="pop_close_btn"><i></i></div>
			<div class="pop_detail">
				<jsp:include page="taskDealSendGoods.jsp" flush="true"/>
			</div>
		</div>
	</div>
</body>
</html>