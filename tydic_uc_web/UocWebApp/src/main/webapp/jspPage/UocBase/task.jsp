<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>任务列表</title>
    <link rel="stylesheet" type="text/css" href="../../style/css/public.css" />
    <link rel="stylesheet" type="text/css" href="../../style/css/task.css" />
    <script type="text/javascript" src="../../js/common/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../../js/common/WebUtil.js"></script>
    <script type="text/javascript" src="../../js/common/public.js"></script>
    <script type="text/javascript" src="../../js/UocBaseJs/task.js"></script>
    <script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
	<div class="container">
		<div class="operate_bar">
			<div class="right">
				<div class="btn btn_default" id="taskAllotBtn">任务分配</div>
				<div class="btn btn_default" id="taskPackageSetBtn">任务包设置</div>
			</div>
			<div class="btn btn_info whole_refresh" id="refreshBtn"><i class="refresh_icon"></i><span>刷新</span></div>
		</div>
		<div class="task_card">
			<div class="card_item" id="taskOuttimeList">
				<div class="card_head card_timeout_head">
					<div class="left font16">逾期任务</div>
					<i class="search_icon"></i>
					<div class="right font_smaller">任务&ensp;&middot;&ensp;<span name="taskCount">0</span></div>
					<i class="refresh_icon"></i>
				</div>
				<div class="card_body">
					<!-- 动态加载 -->
					<!-- <div class="issue">
						<div issue="flag">
							<span class="label">写卡</span>
						</div>
						<div issue="title" class="card_hover">
							<p><span class="label">订单ID：</span><span class="text">S0832016111715570550043353</span></p>
							<p><span class="label">业务号码：</span><span class="text">18612341234</span></p>
							<p><span class="label">业务名称：</span><span class="text">“智慧沃家”新装</span></p>
						</div>
						<div issue="sup_info">
							<div class="times" time_type="start_time">
								<span class="label">创建时间：</span><span class="text" color_attr="color_default">2016-12-07 08:00:00</span>
							</div>
							<div class="times" time_type="residual_time">
								<span class="label">逾期时长：</span><span class="text" color_attr="color_over">20天 00:15:45</span>
							</div>
						</div>
					</div>
					<div class="issue">
						<div issue="flag">
							<span class="label">写卡</span>
						</div>
						<div issue="title" class="card_hover">
							<p><span class="label">订单ID：</span><span class="text">S0832016111814154621818665</span></p>
							<p hide><span class="label">订单类型：</span><span class="text">100</span></p>
							<p><span class="label">业务号码：</span><span class="text">18612341234</span></p>
							<p><span class="label">业务名称：</span><span class="text">4G开户</span></p>
						</div>
						<div issue="sup_info">
							<div class="times" time_type="start_time">
								<span class="label">创建时间：</span><span class="text" color_attr="color_default">2016-12-07 08:00:00</span>
							</div>
							<div class="times" time_type="residual_time">
								<span class="label">逾期时长：</span><span class="text" color_attr="color_over">20天 00:15:45</span>
							</div>
						</div>
					</div> -->
				</div>
				<div class="card_foot">
					<div class="more_issues">查看更多</div>
					<div class="count_issues">已加载：<span name="taskLoadCount">0</span></div>
					<div class="count_issues">剩余：<span name="taskNotLoadCount">0</span></div>
				</div>
			</div>
			<div class="card_item" id="taskIntimeList">
				<div class="card_head card_timein_head">
					<div class="left font16">正常任务</div>
					<i class="search_icon"></i>
					<div class="right font_smaller">任务&ensp;&middot;&ensp;<span name="taskCount">0</span></div>
					<i class="refresh_icon"></i>
				</div>
				<div class="card_body">
					<!-- 动态加载 -->
					<!-- <div class="issue" color_attr="border_much">
						<div issue="flag">
							<span class="label">写卡</span>
						</div>
						<div issue="title" class="card_hover">
							<p><span class="label">订单ID：</span><span class="text">S0832016111715570550043353</span></p>
							<p><span class="label">业务号码：</span><span class="text">18612341234</span></p>
							<p><span class="label">业务名称：</span><span class="text">“智慧沃家”新装</span></p>
						</div>
						<div issue="sup_info">
							<div class="times" time_type="start_time">
								<span class="label">创建时间：</span><span class="text" color_attr="color_default">2016-12-07 08:00:00</span>
							</div>
							<div class="times" time_type="residual_time">
								<span class="label">倒计时长：</span><span class="text" color_attr="color_much">5天 07:15:45</span>
							</div>
						</div>
					</div>
					<div class="issue" color_attr="border_less">
						<div issue="flag">
							<span class="label">写卡</span>
						</div>
						<div issue="title" class="card_hover">
							<p><span class="label">订单ID：</span><span class="text">2222222</span></p>
							<p><span class="label">业务号码：</span><span class="text">18612341234</span></p>
							<p><span class="label">业务名称：</span><span class="text">“智慧沃家”新装</span></p>
						</div>
						<div issue="sup_info">
							<div class="times" time_type="start_time">
								<span class="label">创建时间：</span><span class="text" color_attr="color_default">2016-12-07 08:00:00</span>
							</div>
							<div class="times" time_type="residual_time">
								<span class="label">倒计时长：</span><span class="text" color_attr="color_less">0天 00:20:45</span>
							</div>
						</div>
					</div> -->
				</div>
				<div class="card_foot">
					<div class="more_issues">查看更多</div>
					<div class="count_issues">已加载：<span name="taskLoadCount">0</span></div>
					<div class="count_issues">剩余：<span name="taskNotLoadCount">0</span></div>
				</div>
			</div>
			<div class="card_item" id="taskNotgrabList">
				<div class="card_head card_dept_head">
					<div class="left font16">部门任务</div>
					<i class="search_icon"></i>
					<div class="right font_smaller">任务&ensp;&middot;&ensp;<span name="taskCount">0</span></div>
					<i class="refresh_icon"></i>
					<div class="btn_right" id="taskPackageGetBtn">按任务包领取</div>
					<div class="btn_right" id="taskGetBtn">领取</div>
				</div>
				<div class="card_body">
					<!-- 动态加载 -->
					<!-- <div class="issue">
						<div issue="flag">
							<span class="label">写卡</span>
						</div>
						<div issue="operate"></div>
						<div issue="title" class="card_hover club_ml">
							<p><span class="label">订单ID：</span><span class="text">S0832016111715570550043353</span></p>
							<p><span class="label">业务号码：</span><span class="text">18612341234</span></p>
							<p><span class="label">业务名称：</span><span class="text">“智慧沃家”新装</span></p>
						</div>
						<div issue="sup_info">
							<div class="times" time_type="start_time">
								<span class="label">创建时间：</span><span class="text" color_attr="color_default">2016-12-07 08:00:00</span>
							</div>
						</div>
					</div>
					<div class="issue">
						<div issue="flag">
							<span class="label">审核</span>
						</div>
						<div issue="operate"></div>
						<div issue="title" class="card_hover club_ml">
							<p><span class="label">订单ID：</span><span class="text">S0832016111814154621818665</span></p>
							<p><span class="label">业务号码：</span><span class="text">18612341234</span></p>
							<p><span class="label">业务名称：</span><span class="text">4G开户</span></p>
						</div>
						<div issue="sup_info">
							<div class="times" time_type="start_time">
								<span class="label">创建时间：</span><span class="text" color_attr="color_default">2016-12-07 08:00:00</span>
							</div>
						</div>
					</div> -->
				</div>
				<div class="card_foot">
					<div class="more_issues">查看更多</div>
					<div class="count_issues">已加载：<span name="taskLoadCount">0</span></div>
					<div class="count_issues">剩余：<span name="taskNotLoadCount">0</span></div>
				</div>
			</div>
		</div>
	</div>
	<!-- 任务-弹出页面 -->
	<div class="pop_container" id="popTask" style="display:none;">
		<div class="pop_panel width85per">
			<div class="pop_close_btn"><i></i></div>
			<div class="pop_detail">
				<!-- 流程图 -->
				<div class="detail_item" id="proc_view">
					<div class="flow_title">当前订单[sale123456789012345678]已经进入分配处理1小时1分钟，请尽快处理！</div>
					<div class="flow_chart">
						<!-- 需在流程节点加载完后设置ul宽度：ul宽度 = (200 * 节点数 - 124)px -->
						<ul class="flow_list grab" style="width:1076px;">
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
						</ul>
					</div>
				</div>
				<!-- 流程操作记录 -->
				<div class="detail_item" id="proc_log">
					<ul class="flow_log_head">
						<li class="line">
							<i class="pucker_down" control="foldable">▼</i>
							<div class="width20per">操作时间</div><div class="width15per">操作类型</div><div class="width50per">操作描述</div><div>操作员工</div>
						</li>
					</ul>
					<ul class="flow_log_body">
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
					</ul>
				</div>
				<!-- 任务信息 -->
				<div class="detail_item" id="task_detail">
					<ul class="detail_info_head">
						<li class="line">
							<div>任务信息</div>
						</li>
					</ul>
					<ul class="detail_info_body">
						<li class="line">
							<div class="one_third">任务名称：</div><div class="one_third">业务号码：</div><div class="one_third">业务名称：</div>
						</li>
						<li class="line">
							<div class="one_third">创建时间：</div><div class="one_third">逾期时间：</div>
						</li>
					</ul>
				</div>
				<!-- 订单信息 -->
				<div class="detail_item" id="order_detail">
					<ul class="detail_info_head">
						<li class="line">
							<div>订单信息</div>
						</li>
					</ul>
					<ul class="detail_info_body">
						<li class="line">
							<div class="one_third">订单ID：</div><div class="one_third">XXXX：</div><div class="one_third">XXXX：</div>
						</li>
						<li class="line">
							<div class="one_third">创建时间：</div>
						</li>
					</ul>
				</div>
				<!-- 任务处理页面容器 -->
				<div id="taskDealArea"></div>
				<!-- 领取任务 -->
				<div class="detail_last_item hide" id="taskBtnArea">
					<ul class="detail_info_body">
						<li class="line">
							<div class="btn btn_primary">领取任务</div>
						</li>
					</ul>
				</div>
				<!-- 异常处理 -->
				<div class="detail_last_item hide" id="taskProcessArea">
					<ul class="detail_info_body">
						<li class="line">
							<div class="btn btn_primary">任务处理</div>
							<!-- <div class="btn btn_error" id="revokeOrderBtn">撤单</div> -->
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<!-- 分配-弹出页面 -->
	<div class="pop_container" id="popAllot" style="display:none;">
		<div class="pop_panel width20per">
			<div class="pop_close_btn"><i></i></div>
			<div class="pop_detail">
				<!-- 分配到部门 -->
				<div class="detail_item" oper_type="400">
					<ul class="short_info_head" control="foldable">
						<li class="line">分配到部门</li>
					</ul>
					<ul class="short_info_body" on_off="off">
						<li class="input_box">
							<input type="text" class="search_input" placeholder="输入部门..." />
							<i class="search_icon"></i>
						</li>
						<li class="line">
							<i class="icon_department"></i><span>沙坪坝互联网部</span>
						</li>
						<li class="line">
							<i class="icon_department"></i><span>杨家坪互联网部</span>
						</li>
						<li class="line">
							<i class="icon_department"></i><span>大坪互联网部</span>
						</li>
						<li class="line">
							<i class="icon_department"></i><span>南坪坝互联网部</span>
						</li>
					</ul>
					<ul class="short_info_foot" control="foldable">
						<li class="line">&bull;&bull;&bull;&ensp;所有部门</li>
					</ul>
				</div>
				<!-- 分配到员工 -->
				<div class="detail_item" oper_type="300">
					<ul class="short_info_head" control="foldable">
						<li class="line">分配到员工</li>
					</ul>
					<ul class="short_info_body" on_off="off">
						<li class="input_box">
							<input type="text" class="search_input" placeholder="输入员工..." />
							<i class="search_icon"></i>
						</li>
						<li class="line">
							<i class="icon_staff"></i><span>张三</span>
						</li>
						<li class="line">
							<i class="icon_staff"></i><span>李四</span>
						</li>
						<li class="line">
							<i class="icon_staff"></i><span>王五</span>
						</li>
					</ul>
					<ul class="short_info_foot" control="foldable">
						<li class="line">&bull;&bull;&bull;&ensp;所有员工</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<!-- 筛选-弹出页面 -->
	<div class="pop_container" id="popSearch" style="display:none;">
		<div class="pop_panel width20per">
			<div class="pop_close_btn"><i></i></div>
			<div class="pop_detail">
				<!-- 环节 -->
				<div class="detail_item" id="tache_code">
					<ul class="short_info_head" control="foldable">
						<li class="line">环节</li>
					</ul>
					<ul class="short_info_body" on_off="off">
						<li class="line">
							<i class="icon_link"></i><span>写卡</span>
						</li>
						<li class="line">
							<i class="icon_link"></i><span>审核</span>
						</li>
						<li class="line">
							<i class="icon_link"></i><span>激活</span>
						</li>
						<li class="line">
							<i class="icon_link"></i><span>物流</span>
						</li>
					</ul>
					<ul class="short_info_foot" control="foldable">
						<li class="line">&bull;&bull;&bull;&ensp;所有环节</li>
					</ul>
				</div>
				<!-- 部门 -->
				<div class="detail_item" id="accpet_depart_no">
					<ul class="short_info_head" control="foldable">
						<li class="line">部门</li>
					</ul>
					<ul class="short_info_body" on_off="off">
						<li class="line">
							<i class="icon_department"></i><span>沙坪坝</span>
						</li>
						<li class="line">
							<i class="icon_department"></i><span>杨家坪</span>
						</li>
					</ul>
					<ul class="short_info_foot" control="foldable">
						<li class="line">&bull;&bull;&bull;&ensp;所有部门</li>
					</ul>
				</div>
				<!-- 业务 -->
				<div class="detail_item" id="oper_code">
					<ul class="short_info_head" control="foldable">
						<li class="line">业务</li>
					</ul>
					<ul class="short_info_body" on_off="off">
						<li class="line">
							<i class="icon_business"></i><span>4G开户</span>
						</li>
						<li class="line">
							<i class="icon_business"></i><span>宽带开户</span>
						</li>
						<li class="line">
							<i class="icon_business"></i><span>智慧沃家开户</span>
						</li>
					</ul>
					<ul class="short_info_foot" control="foldable">
						<li class="line">&bull;&bull;&bull;&ensp;所有业务</li>
					</ul>
				</div>
				<!-- 产品 -->
				<div class="detail_item" id="prod_code">
					<ul class="short_info_head" control="foldable">
						<li class="line">产品</li>
					</ul>
					<ul class="short_info_body" on_off="off">
						<li class="line">
							<i class="icon_product"></i><span>386套餐</span>
						</li>
						<li class="line">
							<i class="icon_product"></i><span>286套餐</span>
						</li>
						<li class="line">
							<i class="icon_product"></i><span>186套餐</span>
						</li>
					</ul>
					<ul class="short_info_foot" control="foldable">
						<li class="line">&bull;&bull;&bull;&ensp;所有产品</li>
					</ul>
				</div>
				<!-- 受理时间 -->
				<div class="detail_item">
					<ul class="short_info_head">
						<li class="line">受理时间</li>
					</ul>
					<ul class="short_info_body">
						<li class="input_box">
							<input type="text" class="search_text" id="accept_time_start" placeholder="开始时间..." onclick="WdatePicker();" />
						</li>
						<li class="input_box">
							<input type="text" class="search_text" id="accept_time_end" placeholder="结束时间..." onclick="WdatePicker();" />
						</li>
					</ul>
				</div>
				<!-- 订单编号 -->
				<div class="detail_item">
					<ul class="short_info_head">
						<li class="line">订单编号</li>
					</ul>
					<ul class="short_info_body">
						<li class="input_box">
							<input type="text" class="search_text" id="order_no" placeholder="订单编号..." />
						</li>
					</ul>
				</div>
				<!-- 业务号码 -->
				<div class="detail_item">
					<ul class="short_info_head">
						<li class="line">业务号码</li>
					</ul>
					<ul class="short_info_body">
						<li class="input_box">
							<input type="text" class="search_text" id="acc_nbr" placeholder="业务号码..." />
						</li>
					</ul>
				</div>
				<!-- 区域 -->
				<div class="detail_item">
					<ul class="short_info_head">
						<li class="line">区域</li>
					</ul>
					<ul class="short_info_body">
						<li class="input_box" id="area_code">
							<select>
								<option value="cq">重庆</option>
								<!-- <option value="gx">广西</option>
								<option value="hn">海南</option> -->
							</select>
						</li>
						<li class="input_box">
							<select>
								<option value="all">全部</option>
								<!-- <option value="v1">AA区</option>
								<option value="v2">BB区</option> -->
							</select>
						</li>
					</ul>
				</div>
				<!-- 客户名称 -->
				<div class="detail_item">
					<ul class="short_info_head">
						<li class="line">客户名称</li>
					</ul>
					<ul class="short_info_body">
						<li class="input_box">
							<input type="text" class="search_text" id="cust_name" placeholder="客户名称..." />
						</li>
					</ul>
				</div>
				<!-- 客户证件 -->
				<div class="detail_item">
					<ul class="short_info_head">
						<li class="line">客户证件</li>
					</ul>
					<ul class="short_info_body">
						<li class="input_box">
							<input type="text" class="search_text" id="cert_no" placeholder="客户证件..." />
						</li>
					</ul>
				</div>
			</div>
			<!-- 按钮 -->
			<div class="pop_operate">
				<div class="btn btn_primary">确 定</div>
			</div>
		</div>
	</div>
	<!-- 撤单-模态框 -->
	<div class="modal_layer" belong="mask" id="revokeOrderModal">
		<div class="bg_cover"></div>
		<div class="modal_box">
			<div class="modal_top">
				<span class="modal_title">撤单原因</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle">
				<div class="reason_option" id="reason_list">
					<!-- <p><i></i><span>原因1</span></p>
					<p><i></i><span>原因2</span></p>
					<p><i></i><span>原因3</span></p>
					<p><i></i><span>原因4</span></p>
					<p><i></i><span>原因5</span></p>
					<p><i></i><span>原因6</span></p> -->
				</div>
				<div class="reason_other">
					<p>其他原因：</p>
					<p><textarea id="other_reason"></textarea></p>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" id="revokeOrderConfirmBtn">确 定</div>
				<div class="btn btn_error" id="revokeOrderCancelBtn">取 消</div>
			</div>
		</div>
	</div>
</body>
</html>
