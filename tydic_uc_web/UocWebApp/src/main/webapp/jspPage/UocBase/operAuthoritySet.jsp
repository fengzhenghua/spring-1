<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>工号权限设置</title>
	
    <link rel="stylesheet" type="text/css" href="../../style/css/public.css" />
    <link rel="stylesheet" type="text/css" href="../../style/css/operAuthoritySet.css" />    
    <script type="text/javascript" src="../../js/common/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../../js/common/WebUtil.js"></script>
    <script type="text/javascript" src="../../js/common/public.js"></script>
    <script type="text/javascript" src="../../js/UocBaseJs/operAuthoritySet.js"></script>
</head>
<body>
	<div class="container">
		<div class="detail_item">
			<ul class="detail_info_body mtop15">
				<li class="line">
					<div class="width100per">
						<span class="bold mright5">工号</span>
						<span class="input_box">
							<select id="">
								<option value="">--请选择工号--</option>
							</select>
						</span>
						
						<span class="bold mleft10 mright5">角色</span>
						<span class="input_box">
							<select id="">
								<option value="">--请选择角色--</option>
							</select>
						</span>
						
						<div class="btn btn_primary mleft10" id="searchBtn">查询</div>
						<div class="btn btn_default mleft5" id="resetBtn">重置</div>
					</div>
				</li>
				<li class="line mtop5">
					<div class="width100per">
						<div class="btn btn_info mleft5" id="authorityAddBtn">新增权限</div>
					</div>
				</li>
			</ul>
		</div>
		<table class="info_list">
			<thead>
				<tr>
					<th width="45%">工号</th>
					<th width="45%">角色</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="authorityList">
				<tr>
					<td>TEST01</td>
					<td>测试角色1</td>
					<td class="text_center">
						<div class="btn btn_link" name="delBtn">删除</div>
					</td>
				</tr>
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
	<!-- 新增权限-模态框 -->
	<div class="modal_layer" belong="mask" id="authorityAddModal">
		<div class="bg_cover"></div>
		<div class="modal_box">
			<div class="modal_top">
				<span class="modal_title">新增权限</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle">
				<div class="detail_item">
					<ul class="detail_info_body mtop15">
						<li class="line">
							<div class="width100per">
								<span class="bold mright5">工号</span>
								<span class="input_box width200">
									<select id="">
										<option value="">--请选择工号--</option>
									</select>
								</span>
								
								<span class="bold mleft25 mright5">角色</span>
								<span class="input_box width200">
									<select id="">
										<option value="">--请选择角色--</option>
									</select>
								</span>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" id="authorityAddConfirmBtn">保 存</div>
				<div class="btn btn_default mleft10" id="authorityAddCancelBtn">取 消</div>
			</div>
		</div>
	</div>
</body>
</html>