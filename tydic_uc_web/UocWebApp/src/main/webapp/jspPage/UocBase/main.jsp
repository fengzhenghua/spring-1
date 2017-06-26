<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>中国联通一体化服务平台</title>
	<link rel="icon" type="image/x-icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="style/css/public.css" />
    <link rel="stylesheet" type="text/css" href="style/css/main.css" />
    <script type="text/javascript" src="js/common/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="js/common/WebUtil.js"></script>
    <script type="text/javascript" src="js/common/core.js"></script>
    <script type="text/javascript" src="js/common/constant.js"></script>
    <script type="text/javascript" src="js/common/public.js"></script>
    <script type="text/javascript" src="js/ajax-pushlet-client.js" ></script>
    <script type="text/javascript" src="js/common/main.js"></script>
</head>
<body>
	<div class="top_bar">
		<div class="left mleft15">
			<div class="left_cell">
				<img class="logo_img" src="style/images/icon/logo.png" />
			</div><div class="left_cell">
				<p class="logo_cn">中国联通一体化服务平台</p>
				<p class="logo_en">Unified Service Platform</p>
			</div>
		</div>
		<div class="right mright15">
			<div class="right_cell">
				<span>授权人：</span><span id="operInfo"></span>
			</div>
			<div class="right_cell" id="taskJoinBtnDiv" style="display: none;">
				<div class="btn btn_link mtop16" id="taskJoinBtn">签入</div>
			</div>
			<div class="right_cell" id="taskJoiningDiv" style="display: none;">
				<div class="btn mtop16" id="taskJoinBtnTest"><div ><font style="color:#C0C0C0">签入</font><font style="color:red">（签入中）</font></div></div>
			</div>
			<div class="right_cell" id="taskExitBtnDiv" style="display: none;">
				<div class="btn btn_link mtop16" id="taskExitBtn">签出</div>
			</div>
			<div class="right_cell">
				<div class="btn btn_link mtop16" id="logoutBtn">退出</div>
			</div>
		</div>
	</div>
	<div class="left_bar" id="menuList">
		<!-- <ul class="menu_level1">
			<li label="订单" url="" menu_id="" offset_top="0">
				<i class="icon_order"></i>
				<span>订单</span>
				<ul class="menu_level2 hide">
					<li label="订单查询" url="orderQuery.jsp" menu_id="">
						<span>订单查询</span>
					</li>
				</ul>
			</li>
			<li label="任务" url="" menu_id="" offset_top="0">
				<i class="icon_task"></i>
				<span>任务</span>
				<ul class="menu_level2 hide">
					<li label="任务列表" url="task.jsp" menu_id="">
						<span>任务列表</span>
					</li>
					<li label="监控视图" url="monitor.jsp" menu_id="">
						<span>监控视图</span>
					</li>
				</ul>
			</li>
			<li label="参数配置" url="" menu_id="" offset_top="0">
				<i class="icon_config"></i>
				<span>参数配置</span>
			</li>
		</ul> -->
	</div>
	<div class="tab_bar" id="tabList">
		<ul class="tab_box">
			<li label="首页" class="active">
				<span>首页</span>
			</li>
		</ul>
	</div>
	<div class="page_frame" id="pageFrame">
		<iframe label="首页" src="jspPage/UocBase/homePage.jsp" scrolling="yes" frameborder="0" style="width:100%; height:100%;"></iframe>
	</div>
	<div class="bottom_bar">
		<font>©2017 深圳天源迪科信息技术股份有限公司 版权所有</font>
	</div>
</body>
</html>