<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jspPage/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>sku属性管理</title>
<link rel="stylesheet" type="text/css" href="../../style/css/common/public.css" />
<link rel="stylesheet" type="text/css" href="../../style/css/UgcBaseCss/skuManage.css" />
<script type="text/javascript" src="../../js/common/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="../../js/common/WebUtil.js"></script>
<script type="text/javascript" src="../../js/common/public.js"></script>
<script type="text/javascript" src="../../js/UgcBaseJs/skuAttrManage.js"></script>
</head>
<body>
	<div class="container">
		<div class="mtop10">
			<div class="font14 back" id="return_sku_btn"><span>返回</span></div>
		</div>
		
		<!-- 查询选项 -->
		<!-- <div class="search">
			<ul class="filter">
				<li>
					<label>ID</label>
					<div class="select">
						<input type="text" class="text-box" placeholder="请输入商品名称" id="pkg_name"/>
					</div>
				</li>
				<li>
					<label>名称</label>
					<div class="select">
						<input type="text" class="text-box" placeholder="请输入商品名称" id="pkg_name"/>
					</div>
				</li>
				<li>
					<label>描述</label>
					<div class="select">
						<input type="text" class="text-box" placeholder="请输入商品描述" id="pkg_name"/>
					</div>
				</li>
				<li>
					<label>类型</label>
					<div class="select">
						<input type="text" placeholder="— 请选择 —" readonly id="prod"/>
						<b class="caret caret-down" query_type="prod"></b>
						<ul>
						</ul>
					</div>
				</li>
				<li>
					<div class="btn btn_primary" id="searchBtn">查询</div>
					<div class="btn btn_default mleft5" id="resetBtn">重置</div>
				</li>
			</ul>			
		</div> -->
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
					<b><i></i>sku属性编辑</b>
					<!-- <input class="task-package-name" placeholder="点我输入sku管理名称哦" id="sku_edit_sku_name"/> -->
				</li>
				<li class="n-li">
					<table class="s-table" cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th class="r-th">编号</th>
								<th class="r-th">类型</th>
								<th class="r-th">属性名</th>
								<th class="r-th">属性值</th>
								<th class="r-th">操作</th>
							</tr>
						</thead>
						<tbody>
							<tr no="1" id="sku_attr_edit_tr">
								<td class="r-td">
									<input id="sku_attr_edit_id" class="st-input br" type="text" value="" placeholder="请输入sku编号">
								</td>
								<td class="r-td">
									<select id="sku_attr_edit_attr_type" class="s-select br" placeholder="请选择sku属性类型">
										<!-- <option value="1">默认类型</option> -->
									</select>
								</td>
								<td class="r-td">
									<select id="sku_attr_edit_attr_key" class="s-select br" placeholder="请选择sku属性名">
										<!-- <option value="1">默认类型</option> -->
									</select>
								</td>
								<td class="r-td">
									<input id="sku_attr_edit_attr_value" class="st-input br" type="text" value="" placeholder="请输入sku属性值">
								</td>
								<td class="r-td">
									<span class="save" id="sku_attr_edit_save">保存</span>
									<span class="cancel" id="sku_attr_edit_cancel">取消</span>
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
		<div class="task-list" id="sku_attr_list">
			<ul class="task-list">
				<li class="rf-li">
					<i class="edit-btn"></i>		
					<span id="sku_name_span">XXXXX新增管理1</span>
					<!-- <i class="del-btn"></i>	 -->
				</li>
				<li class="r-li">
					<table class="s-table" cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th class="r-th">编号</th>
								<th class="r-th">类型</th>
								<th class="r-th">属性名</th>
								<th class="r-th">属性键</th>
								<th class="r-th">属性值</th>
								<th class="r-th">操作</th>
							</tr>
						</thead>
						<tbody>
							<!-- <tr>
								<td class="r-td">e</td>
								<td class="r-td">d</td>
								<td class="r-td">d</td>
								<td class="r-td">r</td>
							</tr>
							<tr>
								<td class="r-td">e</td>
								<td class="r-td">d</td>
								<td class="r-td">d</td>
								<td class="r-td">r</td>
							</tr>	
							<tr>
								<td class="r-td">e</td>
								<td class="r-td">d</td>
								<td class="r-td">d</td>
								<td class="r-td">r</td>
							</tr>	
							<tr>
								<td class="r-td">e</td>
								<td class="r-td">d</td>
								<td class="r-td">d</td>
								<td class="r-td">r</td>
							</tr>	
							<tr>
								<td class="r-td">e</td>
								<td class="r-td">d</td>
								<td class="r-td">d</td>
								<td class="r-td">r</td>
							</tr>	
							<tr>
								<td class="r-td">e</td>
								<td class="r-td">d</td>
								<td class="r-td">d</td>
								<td class="r-td">r</td>
							</tr> -->							
						</tbody>
					</table>
				</li>
			</ul>
		</div>
		<!-- 查询结果显示框 end-->
	</div>
</body>
</html>