<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>工号组设置</title>
	
    <link rel="stylesheet" type="text/css" href="../../style/css/public.css" />
    <link rel="stylesheet" type="text/css" href="../../style/css/taskRule.css" />    
    <script type="text/javascript" src="../../js/common/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../../js/common/WebUtil.js"></script>
    <script type="text/javascript" src="../../js/common/public.js"></script>
    <script type="text/javascript" src="../../js/UocBaseJs/taskSpecialRule.js"></script>
</head>
<body>
	<div class="container">
		<div>
			<div class="btn btn_link font14" id="returnBtn">&lt;&lt;返回</div>
		</div>
		<div class="detail_item">
			<ul class="detail_info_body mtop15">
				<li class="line">
					<div class="width100per">
						<span class="bold mright5">工号组ID</span>
						<span class="input_box width150">
							<input type="text" class="cs_pointer" id="ruleId" no="" placeholder="请选择目标工号组" readonly/>
						</span>
						
						<span class="bold mleft10 mright5">目标工号</span>
						<span class="input_box">
							<input type="text" class="cs_pointer" id="target_oper_no" no="" placeholder="请选择目标工号" readonly/>
						</span>
						
						<span class="bold mleft10 mright5">归属部门</span>
						<span class="input_box">
							<input type="text" class="cs_pointer" id="accept_depart_no" no="" placeholder="请选择归属部门" readonly/>
						</span>
						
						<div class="btn btn_primary mleft10" id="searchBtn">查询</div>
						<div class="btn btn_default mleft5" id="resetBtn">重置</div>
					</div>
				</li>
				<li class="line mtop5">
					<div class="width100per">
						<div class="btn btn_info mleft5" id="specialRuleAddBtn">新增工号组</div>
					</div>
				</li>
			</ul>
		</div>
		<table class="info_list">
			<thead>
				<tr>
					<th width="30%">工号组ID</th>
					<th width="20%">目标工号</th>
					<th width="10%">目标工号权重</th>
					<th width="20%">归属部门</th>
					<th width="10%">归属部门默认权重</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="specialRuleList">
				<tr>
					<td>111</td>
					<td>TEST01</td>
					<td></td>
					<td>35</td>
					<td>35</td>
					<td class="text_center">
						<div class="btn btn_link" name="editBtn">修改</div>
						<div class="btn btn_link" name="delBtn">删除</div>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="pagination">
			<span class="mright10">共<b class="mleft5 mright5" id="totalCount">0</b>条</span>			
			<!-- <span class="input_box width40" >
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
			<div class="btn btn_default mleft25" id="exportBtn">导出</div> -->
		</div>
	</div>
	<!-- 特殊规则-模态框 -->
	<div class="modal_layer" belong="mask" id="specialRuleModal">
		<div class="bg_cover"></div>
		<div class="modal_box">
			<div class="modal_top">
				<span class="modal_title">工号组</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle">
				<div class="detail_item">
					<ul class="detail_info_body mtop15">
						<li class="line">
							<div class="width100per">
								<span class="bold mright5">工号组ID</span>
								<span class="input_box width200">
									<input type="text" class="cs_pointer" id="modalRuleId" no="" placeholder="请选择目标工号组" readonly/>
								</span>
							</div>
						</li>
						<li class="line mtop5">
							<div class="width100per">
								<span class="bold mright5">目标工号</span>
								<span class="input_box width200">
									<input type="text" class="cs_pointer" id="modal_target_oper_no" no="" placeholder="请选择目标工号" readonly/>
								</span>
								
								<span class="bold mleft10 mright5">归属部门</span>
								<span class="input_box width150">
									<input type="text" class="cs_pointer" id="modal_accept_depart_no" no="" placeholder="请选择归属部门" readonly/>
								</span>
							</div>
						</li>
						<li class="line">
							<div class="width100per"  id="box">
								<span class="bold mright5">工号权重</span>
								<span  id="addInput" class="input_box" style="width:88%;border:0;margin-left:0px;"></span>
							</div>
						</li>
						<!-- <li class="line mtop5" style="position:relative" >
							<div class="width100per" id="box">
								<span class="bold mright5">工号权重</span>
							</div>
							<div  id="addInput" style="position:absolute;top:-3px;left:0;"></div>
						</li> -->
						<li class="line mtop5" style="display:none;">
							<div class="width100per">
								<span class="bold mright5">归属部门默认权重</span>
								<span class="input_box width200"><input type="text" id="modalDepartProportion"/></span>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" id="specialRuleConfirmBtn">保 存</div>
				<div class="btn btn_default" id="specialRuleCancelBtn">取 消</div>
			</div>
		</div>
	</div>
	<!-- 目标工号-模态框 -->
	<div class="modal_layer" belong="mask" id="targetOperNoModal">
		<div class="bg_cover"></div>
		<div class="modal_box">
			<div class="modal_top">
				<span class="modal_title">目标工号</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle" style="margin-top:0;">
				<div class="detail_last_item">
					<ul class="detail_info_body">
						<li class="line">
							<div class="width100per text_center">
								<span class="input_box width200"><input type="text" id="target_oper_no_search" style="color:#d94032;" placeholder="数据量较大，请输入姓名"/></span>
								<div class="btn btn_primary mleft10" id="targetOperNoSearchBtn">查询</div>
							</div>
						</li>
						<li class="line">
							<div class="option_box" type="checkbox" id="targetOperNoList">
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" id="targetOperNoConfirmBtn">确 定</div>
				<div class="btn btn_default" id="targetOperNoCancelBtn">取 消</div>
			</div>
		</div>
	</div>
	<!-- 归属部门-模态框 -->
	<div class="modal_layer" belong="mask" id="acceptDepartNoModal">
		<div class="bg_cover"></div>
		<div class="modal_box">
			<div class="modal_top">
				<span class="modal_title">归属部门</span><span class="modal_close">x</span>
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
								<span class="width50per"><a no="*">任意部门</a></span>
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
	<!-- 工号组-模态框 -->
	<div class="modal_layer" belong="mask" id="operNoGroupModal">
		<div class="bg_cover"></div>
		<div class="modal_box">
			<div class="modal_top">
				<span class="modal_title">工号组</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle" style="margin-top:0;">
				<div class="detail_last_item">
					<ul class="detail_info_body">
						<li class="line">
							<div class="width100per text_center">
								<span class="input_box width150"><input type="text" id="oper_no_group_search" placeholder="请输入工号组名称"/></span>
								<div class="btn btn_primary mleft10" id="operNoGroupSearchBtn">查询</div>
								<div class="btn btn_primary mleft10" id="operNoGroupAddBtn">新增</div>
							</div>
						</li>
						<li class="line">
							<div class="option_box" type="radio" id="operNoGroupList">
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" id="operNoGroupConfirmBtn">确 定</div>
				<div class="btn btn_default" id="operNoGroupCancelBtn">取 消</div>
			</div>
		</div>
	</div>
</body>
</html>