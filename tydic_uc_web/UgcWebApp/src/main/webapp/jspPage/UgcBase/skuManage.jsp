<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jspPage/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>sku管理</title>
<link rel="stylesheet" type="text/css" href="../../style/css/common/public.css" />
<link rel="stylesheet" type="text/css" href="../../style/css/UgcBaseCss/skuManage.css" />
<script type="text/javascript" src="../../js/common/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="../../js/common/WebUtil.js"></script>
<script type="text/javascript" src="../../js/common/public.js"></script>
<script type="text/javascript" src="../../js/UgcBaseJs/skuManage.js"></script>
</head>
<body>
	<div class="container">
		<!-- 返回 -->
		<div class="mtop10">
			<div class="font14 back" id="return_goods_btn"><span>返回</span></div>
		</div>
		<!-- 返回end -->
		
		<!-- 查询选项 -->
		<div class="search">
			<ul class="filter">
				<li>
					<label>ID</label>
					<div class="select">
						<input type="text" class="text-box" placeholder="请输入sku编号" id="sku_id"/>
					</div>
				</li>
				<li>
					<label>名称</label>
					<div class="select">
						<input type="text" class="text-box" placeholder="请输入sku名称" id="sku_name"/>
					</div>
				</li>
				<li>
					<div class="btn btn_primary" id="sku_search_btn">查询</div>
					<div class="btn btn_default mleft5" id="sku_reset_btn">重置</div>
				</li>
			</ul>			
		</div>
		<!-- 查询选项end -->
		<!-- 新增 -->
		<div class="new-task-package">
			<div class="add">
				<span class="arrow-right arrow-down jia" id="sku-attr">新增</span> 
			</div>
		</div>
		<!-- sku属性管理隐藏框 -->
		<div class="sku-attr-hidden">
			<ul class="st-ul" style="display:none">
				<li class="f-li">
					<b><i></i>sku管理名称：</b>
					<input class="task-package-name" placeholder="点我输入sku管理名称哦" id="sku_edit_sku_name"/>
				</li>
				<li class="n-li">
					<table class="s-table" cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th class="r-th">编号</th>
								<th class="r-th">描述</th>
								<th class="r-th">类型</th>
								<th class="r-th">操作</th>
							</tr>
						</thead>
						<tbody>
							<tr no="1" id="sku_edit_tr">
								<td class="r-td">
									<input id="sku_edit_sku_id" class="st-input br" type="text" value="" placeholder="请输入sku编号">
								</td>
								<td class="r-td">
									<input id="sku_edit_sku_desc" class="st-input br" type="text" value="" placeholder="请输入sku描述">
								</td>
								<td class="r-td">
									<select id="sku_edit_sku_type" class="s-select br" placeholder="请选择sku类型">
										<!-- <option value="1">默认类型</option> -->
									</select>
								</td>
								<td class="r-td">
									<span class="save" id="sku_edit_save">保存</span>
									<span class="cancel" id="sku_edit_cancel">取消</span>
								</td>
							</tr>
						</tbody>
					</table>
				</li>
				<!-- <li>
					<span class="add-list a-ml" id="skuAdd"><i></i></span>
				</li>
				<li class="last-item">			
					<span class="save">保存</span>
					<span  class="cancel">取消</span>					
				</li> -->
			</ul>
		</div>
		<!-- sku属性管理隐藏框  end-->
		<!-- 新增 end-->
		
		<!-- 查询结果显示框 -->
		<div class="task-package" id="sku_list">
			<!-- 编辑部分隐藏框 -->
			<ul class="task-list" style="display:none">
				<li class="f-li">
					<b>sku管理名称：</b>
					<input class="task-package-name color" value="XXXXX新增管理1" placeholder="点我输入sku管理名称哦" id="newTaskPackageName"/>
				</li>
				<li class="n-li">
					<table class="s-table" cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th class="s-th">SKU_ID</th>
								<th class="s-th">类型</th>
								<th class="s-th">描述</th>
								<th class="s-th">操作</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="s-td">
									<input class="st-input br" type="text" value="e">
								</td>
								<td class="s-td">
									<input class="st-input br" type="text" value="d">
								</td>
								<td class="s-td">
									<select class="s-select br">
										<option value="">--请选择--</option>
										<option value="" selected>d</option>
									</select>
								</td>
								<td>
									<span class="save">保存</span>
									<span  class="cancel cancel-edit">取消</span>					
								</td>
								<td>
									<i class="del-btn-check"></i>
								</td>
							</tr>
						</tbody>
					</table>
				</li>
				<!-- <li>
					<span class="add-list a-ml" id="skuAdd"><i></i></span>
				</li> -->
				<!-- <li class="last-item">			
					<span class="save">保存</span>
					<span  class="cancel cancel-edit">取消</span>					
				</li> -->
				</li>
			</ul>
			<!-- 编辑部分隐藏框 end -->
			<!-- <ul class="task-list">
				<li class="rf-li">
					<i class="edit-btn"></i>					
					<span>XXXXX新增管理1</span>
					<i class="del-btn"></i>	
				</li>
				<li class="r-li">
					<table class="s-table" cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th class="r-th">ID</th>
								<th class="r-th">描述</th>
								<th class="r-th">类型</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="r-td">e</td>
								<td class="r-td">d</td>
								<td class="r-td">d</td>
							</tr>						
						</tbody>
					</table>
				</li>
			</ul> -->
		<!-- 查询结果显示框 end-->
		<div class="task-package">
		</div>
	</div>
</body>
</html>