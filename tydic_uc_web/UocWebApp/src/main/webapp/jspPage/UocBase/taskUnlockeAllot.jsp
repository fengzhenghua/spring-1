<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>任务解锁指派</title>
	
    <link rel="stylesheet" type="text/css" href="../../style/css/public.css" />
    <link rel="stylesheet" type="text/css" href="../../style/css/orderQuery.css" />
    <link rel="stylesheet" type="text/css" href="../../style/css/writeCard.css" />
    <script type="text/javascript" src="../../js/common/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../../js/common/WebUtil.js"></script>
    <script type="text/javascript" src="../../js/common/public.js"></script>
    <script type="text/javascript" src="../../js/UocBaseJs/taskUnlockeAllot.js"></script>
    <script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
    <style>
    	.select_check{float:left; width:20px; height:20px;background:url("../../style/images/icon/selected_circle.png");}
    	.select_checked{float:left; width:20px; height:20px;background:url("../../style/images/icon/selected_circle_active.png");}
    </style>
</head>
<body>
	
	<div class="container">
		<div class="detail_item">
			<ul class="detail_info_body mtop15">
				<li class="line">
					<div class="width100per">
						<span class="bold mleft10 mright5">受理时间</span>
						<span class="input_box width150"><input type="text" id="accept_time_start" onclick="WdatePicker();"/></span>						
						<span class="mleft5 mright5">TO</span>
						<span class="input_box width150"><input type="text" id="accept_time_end" onclick="WdatePicker();"/></span>
						
						<span class="bold mleft10 mright5">区域</span>
						<span class="input_box mleft5">
							<select id="province_code">
								<!-- <option value="cq">重庆</option> -->
							</select>
						</span>
						<span class="input_box mleft5">
							<select id="">
								<option value="all">全部</option>
							</select>
						</span>
					</div>
				</li>
				<li class="line mtop5">
					<div class="width100per">
						<span class="bold mleft24 mright5">环节</span>
						<span class="option_box" id="tache_code">
							<!-- <a>审核</a><a>写卡</a><a>发货</a><a>激活</a><div class="btn btn_link vertical_baseline" name="more">更多</div>
							<div class="more_option hide">
								<a>审核1</a><a>写卡1</a><a>发货1</a>
							</div> -->
						</span>
						<span class="bold mleft10 mright5">订单ID</span>
						<span class="input_box width150"><input type="text" id="order_no"/></span>
						<div class="btn btn_primary mleft5" id="searchBtn">查询</div>
						<div class="btn btn_default mleft5" id="resetBtn">重置</div>
						<div class="btn btn_primary mleft5" id="unlockeBtn">解锁</div>
						<div class="btn btn_primary mleft5" id="allotBtn">指派</div>
					</div>
				</li>
				<li class="line mtop5 hide" control="foldable">
					<div class="width100per">
						<span class="bold mright5">订单编号</span>
						<span class="input_box width150"><input type="text" id="order_code"/></span>
						<span class="bold mleft10 mright5">客户名称</span>
						<span class="input_box width150"><input type="text" id="cust_name"/></span>
						<span class="bold mleft10 mright5">证件号码</span>
						<span class="input_box width150"><input type="text" id="cert_no"/></span>
						<span class="bold mleft10 mright5">任务性质</span>
						<span class="option_box" id="person_flag">
							<a code_id="0">个人任务</a><a code_id="1">部门任务</a>
						</span>
					</div>
				</li>
				<li class="line mtop5 hide" control="foldable">
					<div class="width100per">
						<span class="bold mright5">业务号码</span>
						<span class="input_box width150"><input type="text" id="acc_nbr"/></span>
						<span class="bold mleft10 mright5">业务类型</span>
						<span class="input_box">
							<select id="oper_code">
								<!-- <option value="4G开户">4G开户</option> -->
							</select>
						</span>
						<span class="bold mleft10 mright5">产品</span>
						<span class="option_box" id="prod_code">
							<!-- <a>腾讯王卡</a><a>滴滴王卡</a><div class="btn btn_link vertical_baseline" name="more">更多</div>
							<div class="more_option hide">
								<a>测试1</a><a>测试2</a><a>测试3</a>
							</div> -->
						</span>
					</div>
				</li>
				<li class="line mtop5 hide" control="foldable">
					<div class="width100per">
						<span class="bold mright5">受理工号</span>
						<span class="input_box">
							<select id="accept_oper_no">
								<!-- <option value="JB">重庆</option> -->
							</select>
						</span>
						<!-- <span class="input_box width150"><input type="text" id="accept_oper_no"/></span> -->
						<span class="bold mleft10 mright5">受理部门</span>
						<span class="input_box">
							<select id="accept_depart_no">
								<!-- <option value="JB">重庆</option> -->
							</select>
						</span>
						<span class="bold mleft10 mright5">SIM卡号</span>
						<span class="input_box width150"><input type="text" id="sim_id"/></span>
					</div>
				</li>
				<li class="more_condition">
					<div class="more_condition_btn" on_off="off"><span>更多选项</span><i>▼</i></div>
				</li>
			</ul>
		</div>
		<table class="info_list" id="taskList">
			<thead>
				<tr>
					<td style="width:3%;"><div class="all_select select_check"></div></td>
					<th>订单标识</th>
					<th>产品信息</th>
					<th>客户信息</th>
				</tr>
			</thead>
			<tbody id="task_list">
				<!--  <tr>
					<td class="width30per link" title="点击查看详情" order_id="E8312017010711541797493168">
						<p>订单ID：E8312017010711541797493168</p>
						<p>订单编码：762833000022</p>
						<p>受理系统：网上商城</p>
						<p>受理时间：2016-12-23 00:00:00</p>
					</td>
					<td class="width40per">
						<p>4G全国套餐老用户全加存费送费/业务【网厅专享】【校园专享】</p>
						<p>套餐：4G全国76元套餐</p>
						<p>业务号码：13222222222</p>
					</td>
					<td class="width10per text_center">
						<p>128.00</p>
						<p>在线支付</p>
					</td>
					<td>
						<p>客户名称：张*三</p>
						<p>证件号码：5****************5</p>
					</td>
				</tr> -->
				<!--<tr>
					<td class="width30per link" title="点击查看详情" order_id="E2016121312345622222">
						<p>订单ID：E2016121312345622222</p>
						<p>订单编码：762833000022</p>
						<p>受理系统：网上商城</p>
						<p>受理时间：2016-12-23 00:00:00</p>
					</td>
					<td class="width40per">
						<p>4G全国套餐老用户全加存费送费/业务【网厅专享】【校园专享】</p>
						<p>套餐：4G全国76元套餐</p>
						<p>业务号码：13222222222</p>
					</td>
					<td class="width10per text_center">
						<p>128.00</p>
						<p>在线支付</p>
					</td>
					<td>
						<p>客户名称：张*三</p>
						<p>证件号码：5****************5</p>
					</td>
				</tr>--> 
			</tbody>
		</table>
		<div class="pagination">
			<span class="mright10">共<b class="mleft5 mright5" id="totalCount">0</b>条</span>			
			<span class="input_box width40" >
				<select id="pageSize" value="">
					<option value="5">5</option>
					<option value="10">10</option>
					<option value="20">20</option>
				</select>				
			</span>
			<span class="page_turn" id="pageFirst">&lt;&lt;</span>
			<span class="page_turn" id="pageUp">&lt;</span>
			<span class="input_box width40"><input type="text" class="text_center" id="pageNo" value="1"/></span>
			<span class="page_turn" id="pageDown">&gt;</span>
			<span class="page_turn" id="pageLast">&gt;&gt;</span>
			<span class="mright10">共<b class="mleft5 mright5" id="totalPage">0</b>页</span>	
			<div class="btn btn_default mleft25" id="exportBtn">导出</div>
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
						<li class="line">部门</li>
					</ul>
					<ul class="short_info_body" id="depart_list">
						<!-- <li class="input_box">
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
						</li> -->
					</ul>
					<ul class="short_info_foot" control="foldable">
						<li class="line">&bull;&bull;&bull;&ensp;所有部门</li>
					</ul>
				</div>
				<!-- 分配到员工 -->
				<div class="detail_item" oper_type="300">
					<ul class="short_info_head" control="foldable">
						<li class="line">员工</li>
					</ul>
					<ul class="short_info_body" id="oper_list">
						<!-- <li class="input_box">
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
						</li> -->
					</ul>
					<ul class="short_info_foot" control="foldable">
						<li class="line">&bull;&bull;&bull;&ensp;所有员工</li>
					</ul>
				</div>
			</div>
			<!-- 按钮 -->
			<div class="pop_operate">
				<div class="btn btn_primary" id="unlocke_allot_btn">确 定</div>
			</div>
		</div>
	</div>
	
</body>
</html>