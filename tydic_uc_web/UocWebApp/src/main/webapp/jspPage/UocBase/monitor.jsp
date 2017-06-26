<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>监控</title>
    <link rel="stylesheet" type="text/css" href="../../style/css/public.css" />
    <link rel="stylesheet" type="text/css" href="../../style/css/monitor.css" />
    <script type="text/javascript" src="../../js/common/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../../js/common/highcharts.js"></script>
    <script type="text/javascript" src="../../js/common/WebUtil.js"></script>
    <script type="text/javascript" src="../../js/common/public.js"></script>
    <script type="text/javascript" src="../../js/UocBaseJs/monitor.js"></script>
    <script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
	<div class="container">
		<div class="chart_line">
			<!-- 逾期占比 -->
			<div class="chart_part">
				<div class="chart_pie" id="chartPie1"></div>
				<div class="chart_content">
					<div class="content_btn">
						<div class="btn btn_default" name="searchBtns" idx="1">► 筛选</div>
					</div>
					<ul class="content_list">
						<li>未逾期：<span id="not_late">60</span></li>
						<li>&lt;1小时：<span id="late_num1">60</span></li>
						<li>&lt;2小时：<span id="late_num2">60</span></li>
						<li>&lt;12小时：<span id="late_num3">60</span></li>
						<li>&lt;24小时：<span id="late_num4">60</span></li>
						<li>&gt;24小时及其他：<span id="late_num5">60</span></li>
					</ul>
				</div>
			</div>
			<!-- 环节占比 -->
			<div class="chart_part">
				<div class="chart_pie" id="chartPie2"></div>
				<div class="chart_content">
					<div class="content_btn">
						<div class="btn btn_default" name="searchBtns" idx="2">► 筛选</div>
					</div>
					<ul class="content_list" id="tacheRate">
						<li>预订单创建：40</li>
						<li>预订单信息录入：40</li>
						<li>资源确认：20</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="chart_line">
			<!-- 竣工占比 -->
			<div class="chart_part">
				<div class="chart_pie" id="chartPie3"></div>
				<div class="chart_content">
					<div class="content_btn">
						<div class="btn btn_default" name="searchBtns" idx="3">► 筛选</div>
					</div>
					<ul class="content_list">
						<li>未竣工数：<span id="uncompelete_num">60</span></li>
						<li>竣工数：<span id="compelete_num">40</span></li>
					</ul>
				</div>
			</div>
			<!-- 撤单占比 -->
			<div class="chart_part">
				<div class="chart_pie" id="chartPie4"></div>
				<div class="chart_content">
					<div class="content_btn">
						<div class="btn btn_default" name="searchBtns" idx="4">► 筛选</div>
					</div>
					<ul class="content_list">
						<li>撤单数：<span id="cancel_num">60</span></li>
						<li>有效订单数：<span id="valid_num">40</span></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<!-- 逾期占比-筛选-弹出页面 -->
	<div class="pop_container" name="popSearchParts" idx="1" style="display:none;">
		<div class="pop_panel width20per">
			<div class="pop_close_btn"><i></i></div>
			<div class="pop_detail">
				<!-- 状态 -->
				<div class="detail_item" id="">
					<ul class="short_info_head" control="foldable">
						<li class="line">状态</li>
					</ul>
					<ul class="short_info_body" name="taskState">
						<li class="line">
							<i class="icon_business"></i><span>未完成任务</span>
						</li>
						<li class="line">
							<i class="icon_business"></i><span>已完成任务</span>
						</li>
						<li class="line">
							<i class="icon_business"></i><span>全部任务</span>
						</li>
					</ul>
					<ul class="short_info_foot" control="foldable">
						<li class="line" name="sel_state_code1">&bull;&bull;&bull;&ensp;选择状态</li>
					</ul>
				</div>
				<!-- 业务 -->
				<div class="detail_item" id="">
					<ul class="short_info_head" control="foldable">
						<li class="line">业务</li>
					</ul>
					<ul class="short_info_body" name="operCodes">
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
						<li class="line" name="sel_oper_code4">&bull;&bull;&bull;&ensp;所有业务</li>
					</ul>
				</div>
				<!-- 产品 -->
				<div class="detail_item" id="">
					<ul class="short_info_head" control="foldable">
						<li class="line">产品</li>
					</ul>
					<ul class="short_info_body" name="prodCodes">
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
						<li class="line" name="sel_prod_code4">&bull;&bull;&bull;&ensp;所有产品</li>
					</ul>
				</div>
				<!-- 受理部门 -->
				<div class="detail_item" id="">
					<ul class="short_info_head" control="foldable">
						<li class="line">受理部门</li>
					</ul>
					<ul class="short_info_body" name="departCodes"> 
						<li class="line">
							<i class="icon_department"></i><span>沙坪坝</span>
						</li>
						<li class="line">
							<i class="icon_department"></i><span>杨家坪</span>
						</li>
					</ul>
					<ul class="short_info_foot" control="foldable">
						<li class="line" name="sel_depart_code4">&bull;&bull;&bull;&ensp;所有部门</li>
					</ul>
				</div>
				<!-- 受理工号 -->
				<div class="detail_item" id="">
					<ul class="short_info_head" control="foldable">
						<li class="line">受理员工</li>
					</ul>
					<ul class="short_info_body" name="operNo"> 
						<li class="line">
							<i class="icon_staff"></i><span></span>
						</li>
						<li class="line">
							<i class="icon_staff"></i><span></span>
						</li>
					</ul>
					<ul class="short_info_foot" control="foldable">
						<li class="line" name="sel_oper_no4">&bull;&bull;&bull;&ensp;所有员工</li>
					</ul>
				</div>
				<!-- 创建时间 -->
				<div class="detail_item">
					<ul class="short_info_head">
						<li class="line">创建时间</li>
					</ul>
					<ul class="short_info_body">
						<li class="input_box">
							<input type="text" class="search_text" id="start_time4" placeholder="开始时间..." onclick="WdatePicker();" />
						</li>
						<li class="input_box">
							<input type="text" class="search_text" id="end_time4" placeholder="结束时间..." onclick="WdatePicker();" />
						</li>
					</ul>
				</div>
			</div>
			<!-- 按钮 -->
			<div class="pop_operate">
				<div class="btn btn_primary" id="overdue_rate_search">确 定</div>
			</div>
		</div>
	</div>
	<!-- 环节占比-筛选-弹出页面 -->
	<div class="pop_container" name="popSearchParts" idx="2" style="display:none;">
		<div class="pop_panel width20per">
			<div class="pop_close_btn"><i></i></div>
			<div class="pop_detail">
				<!-- 状态 -->
				<div class="detail_item" id="">
					<ul class="short_info_head" control="foldable">
						<li class="line">状态</li>
					</ul>
					<ul class="short_info_body" name="taskState">
						<li class="line">
							<i class="icon_business"></i><span>未完成任务</span>
						</li>
						<li class="line">
							<i class="icon_business"></i><span>已完成任务</span>
						</li>
						<li class="line">
							<i class="icon_business"></i><span>全部任务</span>
						</li>
					</ul>
					<ul class="short_info_foot" control="foldable">
						<li class="line" name="sel_state_code1">&bull;&bull;&bull;&ensp;选择状态</li>
					</ul>
				</div>
				<!-- 业务 -->
				<div class="detail_item" id="">
					<ul class="short_info_head" control="foldable">
						<li class="line">业务</li>
					</ul>
					<ul class="short_info_body" name="operCodes">
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
						<li class="line"  name="sel_oper_code3">&bull;&bull;&bull;&ensp;所有业务</li>
					</ul>
				</div>
				<!-- 产品 -->
				<div class="detail_item" id="">
					<ul class="short_info_head" control="foldable">
						<li class="line">产品</li>
					</ul>
					<ul class="short_info_body" name="prodCodes">
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
						<li class="line" name="sel_prod_code3">&bull;&bull;&bull;&ensp;所有产品</li>
					</ul>
				</div>
				<!-- 受理部门 -->
				<div class="detail_item" id="">
					<ul class="short_info_head" control="foldable">
						<li class="line">受理部门</li>
					</ul>
					<ul class="short_info_body hide" name="departCodes">
						<li class="line">
							<i class="icon_department"></i><span>沙坪坝</span>
						</li>
						<li class="line">
							<i class="icon_department"></i><span>杨家坪</span>
						</li>
					</ul>
					<ul class="short_info_foot" control="foldable">
						<li class="line" name="sel_depart_code3">&bull;&bull;&bull;&ensp;所有部门</li>
					</ul>
				</div>
				<!-- 受理工号 -->
				<div class="detail_item" id="">
					<ul class="short_info_head" control="foldable">
						<li class="line">受理员工</li>
					</ul>
					<ul class="short_info_body hide" name="operNo"> 
						<li class="line">
							<i class="icon_staff"></i><span></span>
						</li>
						<li class="line">
							<i class="icon_staff"></i><span></span>
						</li>
					</ul>
					<ul class="short_info_foot" control="foldable">
						<li class="line" name="sel_oper_no3">&bull;&bull;&bull;&ensp;所有员工</li>
					</ul>
				</div>
				<!-- 创建时间 -->
				<div class="detail_item">
					<ul class="short_info_head">
						<li class="line">创建时间</li>
					</ul>
					<ul class="short_info_body">
						<li class="input_box">
							<input type="text" class="search_text" id="start_time3" placeholder="开始时间..." onclick="WdatePicker();" />
						</li>
						<li class="input_box">
							<input type="text" class="search_text" id="end_time3" placeholder="结束时间..." onclick="WdatePicker();" />
						</li>
					</ul>
				</div>
				<!-- 逾期状态 -->
<!-- 				<div class="detail_item" id=""> -->
<!-- 					<ul class="short_info_head" control="foldable"> -->
<!-- 						<li class="line">逾期状态</li> -->
<!-- 					</ul> -->
<!-- 					<ul class="short_info_body hide"> -->
<!-- 						<li class="line"> -->
<!-- 							<i class="icon_time"></i><span>逾期</span> -->
<!-- 						</li> -->
<!-- 						<li class="line"> -->
<!-- 							<i class="icon_time"></i><span>未逾期</span> -->
<!-- 						</li> -->
<!-- 					</ul> -->
<!-- 					<ul class="short_info_foot" control="foldable"> -->
<!-- 						<li class="line" name="">&bull;&bull;&bull;&ensp;所有状态</li> -->
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
			<!-- 按钮 -->
			<div class="pop_operate">
				<div class="btn btn_primary" id="task_statistics_search">确 定</div>
			</div>
		</div>
	</div>
	<!-- 竣工占比-筛选-弹出页面 -->
	<div class="pop_container" name="popSearchParts" idx="3" style="display:none;">
		<div class="pop_panel width20per">
			<div class="pop_close_btn"><i></i></div>
			<div class="pop_detail">
				<!-- 业务 -->
				<div class="detail_item" id="">
					<ul class="short_info_head" control="foldable">
						<li class="line">业务</li>
					</ul>
					<ul class="short_info_body"  name="operCodes">
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
						<li class="line" name="sel_oper_code2">&bull;&bull;&bull;&ensp;所有业务</li>
					</ul>
				</div>
				<!-- 产品 -->
				<div class="detail_item" id="">
					<ul class="short_info_head" control="foldable">
						<li class="line">产品</li>
					</ul>
					<ul class="short_info_body" name="prodCodes">
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
						<li class="line" name="sel_prod_code2">&bull;&bull;&bull;&ensp;所有产品</li>
					</ul>
				</div>
				<!-- 受理部门 -->
				<div class="detail_item" id="">
					<ul class="short_info_head" control="foldable">
						<li class="line">受理部门</li>
					</ul>
					<ul class="short_info_body hide" name="departCodes">
						<li class="line">
							<i class="icon_department"></i><span>沙坪坝</span>
						</li>
						<li class="line">
							<i class="icon_department"></i><span>杨家坪</span>
						</li>
					</ul>
					<ul class="short_info_foot" control="foldable">
						<li class="line" name="sel_depart_code2">&bull;&bull;&bull;&ensp;所有部门</li>
					</ul>
				</div>
				<!-- 受理工号 -->
				<div class="detail_item" id="">
					<ul class="short_info_head" control="foldable">
						<li class="line">受理员工</li>
					</ul>
					<ul class="short_info_body hide" name="operNo"> 
						<li class="line">
							<i class="icon_staff"></i><span></span>
						</li>
						<li class="line">
							<i class="icon_staff"></i><span></span>
						</li>
					</ul>
					<ul class="short_info_foot" control="foldable">
						<li class="line" name="sel_oper_no2">&bull;&bull;&bull;&ensp;所有员工</li>
					</ul>
				</div>
				<!-- 受理时间 -->
				<div class="detail_item">
					<ul class="short_info_head">
						<li class="line">受理时间</li>
					</ul>
					<ul class="short_info_body">
						<li class="input_box">
							<input type="text" class="search_text" id="start_time2" placeholder="开始时间..." onclick="WdatePicker();" />
						</li>
						<li class="input_box">
							<input type="text" class="search_text" id="end_time2" placeholder="结束时间..." onclick="WdatePicker();" />
						</li>
					</ul>
				</div>
			</div>
			<!-- 按钮 -->
			<div class="pop_operate">
				<div class="btn btn_primary" id="complete_rate_search">确 定</div>
			</div>
		</div>
	</div>
	<!-- 撤单占比-筛选-弹出页面 -->
	<div class="pop_container" name="popSearchParts" idx="4" style="display:none;">
		<div class="pop_panel width20per">
			<div class="pop_close_btn"><i></i></div>
			<div class="pop_detail">
				<!-- 业务 -->
				<div class="detail_item" id="">
					<ul class="short_info_head" control="foldable">
						<li class="line">业务</li>
					</ul>
					<ul class="short_info_body" name="operCodes">
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
						<li class="line" name="sel_oper_code">&bull;&bull;&bull;&ensp;所有业务</li>
					</ul>
				</div>
				<!-- 产品 -->
				<div class="detail_item" id="">
					<ul class="short_info_head" control="foldable">
						<li class="line">产品</li>
					</ul>
					<ul class="short_info_body" name="prodCodes">
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
						<li class="line" name="sel_prod_code">&bull;&bull;&bull;&ensp;所有产品</li>
					</ul>
				</div>
				<!-- 受理部门 -->
				<div class="detail_item" id="">
					<ul class="short_info_head" control="foldable">
						<li class="line">受理部门</li>
					</ul>
					<ul class="short_info_body hide" name="departCodes">
						<li class="line">
							<i class="icon_department"></i><span>沙坪坝</span>
						</li>
						<li class="line">
							<i class="icon_department"></i><span>杨家坪</span>
						</li>
					</ul>
					<ul class="short_info_foot" control="foldable">
						<li class="line" name="sel_depart_code">&bull;&bull;&bull;&ensp;所有部门</li>
					</ul>
				</div>
				<!-- 受理工号 -->
				<div class="detail_item" id="">
					<ul class="short_info_head" control="foldable">
						<li class="line">受理员工</li>
					</ul>
					<ul class="short_info_body hide" name="operNo"> 
						<li class="line">
							<i class="icon_staff"></i><span></span>
						</li>
						<li class="line">
							<i class="icon_staff"></i><span></span>
						</li>
					</ul>
					<ul class="short_info_foot" control="foldable">
						<li class="line" name="sel_oper_no">&bull;&bull;&bull;&ensp;所有员工</li>
					</ul>
				</div>
				<!-- 受理时间 -->
				<div class="detail_item">
					<ul class="short_info_head">
						<li class="line">受理时间</li>
					</ul>
					<ul class="short_info_body">
						<li class="input_box">
							<input type="text" class="search_text" id="start_time" placeholder="开始时间..." onclick="WdatePicker();" />
						</li>
						<li class="input_box">
							<input type="text" class="search_text" id="end_time" placeholder="结束时间..." onclick="WdatePicker();" />
						</li>
					</ul>
				</div>
			</div>
			<!-- 按钮 -->
			<div class="pop_operate">
				<div class="btn btn_primary" id="cancel_rate_search">确 定</div>
			</div>
		</div>
	</div>
</body>
</html>