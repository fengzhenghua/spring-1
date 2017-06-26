<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>中台审核报表</title>
	
    <link rel="stylesheet" type="text/css" href="../../style/css/public.css" />
    <link rel="stylesheet" type="text/css" href="../../style/css/orderQuery.css" />    
    <link rel="stylesheet" type="text/css" href="../../style/css/logisticsDetail.css" />    
    <script type="text/javascript" src="../../js/common/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../../js/common/WebUtil.js"></script>
    <script type="text/javascript" src="../../js/common/public.js"></script>
    <script type="text/javascript" src="../../js/UocBaseJs/orderMiddleAuditStatistics.js"></script>
	<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
	
	<div class="container">
		<div class="detail_item">
			<ul class="detail_info_body mtop15">
				<li class="line">
					<div class="width100per">
						<span class="bold mright5">起始时间</span>
						<span class="input_box width150"><input type="text" id="start_time" onclick="WdatePicker();"/></span>						
						<span class="bold mleft5 mright5">结束时间</span>
						<span class="input_box width150"><input type="text" id="end_time" onclick="WdatePicker();"/></span>
						
						<span class="bold mleft10 mright5">受理工号</span>
						<span class="input_box">
							<input type="text" class="cs_pointer" id="accept_oper_no" no="" placeholder="请选择受理工号" readonly/>
						</span>
						
						<span class="bold mleft10 mright5">受理部门</span>
						<span class="input_box">
							<input type="text" class="cs_pointer" id="accept_depart_no" no="" placeholder="请选择受理部门" readonly/>
						</span>
						
						<!-- <span class="bold mleft10 mright5">中台受理工号</span>
						<span class="input_box">
							<input type="text" class="cs_pointer" id="deal_oper_no" no="" placeholder="请选择受理部门" readonly/>
						</span>
						
						<span class="bold mleft10 mright5">中台受理部门</span>
						<span class="input_box">
							<input type="text" class="cs_pointer" id="deal_depart_no" no="" placeholder="请选择受理部门" readonly/>
						</span>
						
						<div class="btn btn_primary mleft10" id="searchBtn">查询</div>
						<div class="btn btn_default mleft5" id="resetBtn">重置</div> -->
					</div>
				</li>
				<li class="line mtop5">	
					<span class="bold mright5">中台受理工号</span>
					<span class="input_box">
						<input type="text" class="cs_pointer" id="deal_oper_no" no="" placeholder="请选择受理部门" readonly/>
					</span>
					
					<span class="bold mleft10 mright5">中台受理部门</span>
					<span class="input_box">
						<input type="text" class="cs_pointer" id="deal_depart_no" no="" placeholder="请选择受理部门" readonly/>
					</span>
					
					<div class="btn btn_primary mleft10" id="searchBtn">查询</div>
					<div class="btn btn_default mleft5" id="resetBtn">重置</div>
				</li>
			</ul>
		</div>
		<div style="margin-bottom:60px;">
			<table class="info_list">
				<thead>
					<tr>
						<!-- <th width="8%">业务名称</th> -->
						<th width="8%">订单编号</th>
						<th width="5%">服务号码</th>
						<th width="5%">受理日期</th>
						<th width="3%">受理时间</th>
						<th width="6%">受理渠道名称</th>
						<th width="3%">受理工号</th>
						<th width="3%">客户名称</th>
						<th width="5%">联系电话</th>
						<th width="15%">地址</th>
						<th width="7%">证件号码</th>
						<th width="5%">中台受理工号</th>
						<th width="5%">中台受受理日期</th>
						<th width="3%">中台受受理时间</th>
						<th width="3%">审核状态</th>
					</tr>
				</thead>
				<tbody id="report_list">
				
				</tbody>
			</table>
		</div>
		
		<div class="pagination pf" style="background:#fff;width:100%;">
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
	<!-- 受理工号-模态框 -->
	<div class="modal_layer" belong="mask" id="acceptOperNoModal">
		<div class="bg_cover"></div>
		<div class="modal_box">
			<div class="modal_top">
				<span class="modal_title">受理工号</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle" style="margin-top:0;">
				<div class="detail_last_item">
					<ul class="detail_info_body">
						<li class="line">
							<div class="width100per text_center">
								<span class="input_box width200"><input type="text" id="accept_oper_no_search" style="color:#d94032;" placeholder="数据量较大，请输入姓名"/></span>
								<div class="btn btn_primary mleft10" id="acceptOperNoSearchBtn">查询</div>
							</div>
						</li>
						<li class="line">
							<div class="option_box" type="radio" id="acceptOperNoList">
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" id="acceptOperNoConfirmBtn">确 定</div>
				<div class="btn btn_default" id="acceptOperNoCancelBtn">取 消</div>
			</div>
		</div>
	</div>
	<!-- 受理部门-模态框 -->
	<div class="modal_layer" belong="mask" id="acceptDepartNoModal">
		<div class="bg_cover"></div>
		<div class="modal_box">
			<div class="modal_top">
				<span class="modal_title">受理部门</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle" style="margin-top:0;">
				<div class="detail_last_item">
					<ul class="detail_info_body">
						<li class="line">
							<div class="width100per text_center">
								<span class="input_box width200"><input type="text" id="accept_depart_no_search" style="color:#d94032;" placeholder="数据量较大，请输入部门名称"/></span>
								<div class="btn btn_primary mleft10" id="acceptDepartNoSearchBtn">查询</div>
							</div>
						</li>
						<li class="line">
							<div class="option_box" type="radio" id="acceptDepartNoList">
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" id="acceptDepartNoConfirmBtn">确 定</div>
				<div class="btn btn_default" id="acceptDepartNoCancelBtn">取 消</div>
			</div>
		</div>
	</div>
</body>
</html>