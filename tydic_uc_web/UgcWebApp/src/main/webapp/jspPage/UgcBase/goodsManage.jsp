<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jspPage/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品管理</title>
<link rel="stylesheet" type="text/css" href="../../style/css/common/public.css" />
<link rel="stylesheet" type="text/css" href="../../style/css/UgcBaseCss/goodsManage.css" />
<script type="text/javascript" src="../../js/common/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="../../js/common/WebUtil.js"></script>
<script type="text/javascript" src="../../js/common/public.js"></script>
<script type="text/javascript" src="../../js/UgcBaseJs/goodsManage.js"></script>
</head>
<body>
	<div class="container">
		<!-- 头部 -->
		<div class="search">
			<ul class="s-ul clearfix">
				<li class="s-li w l clearfix">
					<label class="s-label l">ID</label>
					<div class="s-input l">
						<input class="br" type="text" value="" id="goods_id">
					</div>
				</li>
				<li class="s-li w l clearfix">
					<label class="s-label l">名称</label>
					<div class="s-input l">
						<input class="br" type="text" value="" id="goods_name">
					</div>
				</li>
				<li class="s-li w l clearfix">
					<label class="s-label l">描述</label>
					<div class="s-input l">
						<input class="br" type="text" value="" id="goods_desc">
					</div>
				</li>
				<li class="s-li w l clearfix">
					<label class="s-label l">状态</label>
					<span class="input_box">
						<select class="n-select br" name="goods_state" id="goods_state">
							<option value="">--请选择--</option>
						</select>
					</span>
					
				</li>
				<li class="s-li w l clearfix">
					<label class="s-label l">类型</label>
					<span class="input_box">
						<select class="n-select br" name="goods_type" id="goods_type">
							<option value="">--请选择--</option>
						</select>
					</span>
				</li>
				<li class="s-li l clearfix">
					<div class="btn btn_primary l" id="searchBtn">查询</div>
					<div class="btn btn_default mleft5 l" id="resetBtn">重置</div>
				</li>
			</ul>
		</div>
		<!-- 头部end -->
		
		<!-- 新增商品 -->
		<div class="new-goods">
			<div class="add click">
				<span class="arrow-right arrow-down jia click" id="add_goods">商&nbsp;品</span> 
				<span class="arrow-right arrow-down sku click" id="sku_manage">sku管理</span> 
			</div>
		</div>
		<!-- 新增商品隐藏框 -->
		<div class="new-goods-hidden">
			<ul class="n-ul" style="display:none;">
				<li class="f-li">
					<b><i></i>商品名称：</b>
					<input class="task-package-name" placeholder="点我输入商品名称哦" id="new_goods_name"/>
				</li>
				<li class="n-li">
					<table class="n-table" border="0" cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th class="n-th n-w">ID</th>
								<th class="n-th n-w">描述</th>
								<th class="n-th n-w">状态</th>
								<th class="n-th n-w">类型</th>
								<th ></th>
								<th ></th>
							</tr>
						</thead>
						<tbody>
						<!-- 加一条 -->
							<tr>
								<td class="n-td td-w">
									<input class="n-input br" type="text" value="" id="new_goods_id">
								</td>
								<td class="n-td td-w">
									<input class="n-input br" type="text" value="" id="new_goods_desc">
								</td>
								<td class="n-td td-w">
									<select class="n-select br" name="goods_state" id="new_goods_state">
										<option value="">--请选择--</option>
									</select>
								</td>
								<td class="n-td td-w">
									<select class="n-select br" name="goods_type" id="new_goods_type">
										<option value="">--请选择--</option>
									</select>
								</td>
								<td>
									<div class="btn btn_primary" name="search_btn">选择sku</div>
								</td>
							</tr>
							<!-- 加一条end -->
						</tbody>
					</table>
				</li>
				<li class="last-item">			
					<span class="save">保存</span>
					<span class="cancel">取消</span>					
				</li>
			</ul>
		</div>
		<!-- 新增商品隐藏框end -->
		<!-- 新增商品 end -->
		
		<!-- 查询结果显示框 -->
		<div class="task-package" id="goods_define_list">
			<!-- 编辑部分隐藏框 -->
			<!-- <ul class="task-package" style="display:none;">
				<li class="f-li">
					<b>商品名称：</b>
					<input class="task-package-name color" value="XXXXX新增商品1" placeholder="点我输入商品名称哦" id="newTaskPackageName"/>
				</li>
				<li class="n-li">
					<table class="n-table" border="0" cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th class="n-th n-w">ID</th>
								<th class="n-th n-w">描述</th>
								<th class="n-th n-w">触点</th>
								<th class="n-th n-w">状态</th>
								<th class="n-th n-w">类型</th>
								<th ></th>
								<th ></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="n-td td-w">
									<input class="n-input br" type="text" value="e">
								</td>
								<td class="n-td td-w">
									<input class="n-input br" type="text" value="d">
								</td>
								<td class="n-td td-w">
									<input class="n-input br" type="text" value="f">
								</td>
								<td class="n-td td-w">
									<select class="n-select br">
										<option value="">--请选择--</option>
										<option value="" selected>d</option>
									</select>
								</td>
								<td class="n-td td-w">
									<select class="n-select br">
										<option value="">--请选择--</option>
										<option value="" selected>d</option>
									</select>
								</td>
								<td>
									<div class="btn btn_primary" id="searchBtn">选择</div>
								</td>
								<td>
									<i class="del-btn"></i>
								</td>
							</tr>
						</tbody>
					</table>
				</li>
				<li>
					<span class="add-list" id="goodsAdd"><i></i></span>
				</li>
				<li class="last-item">			
					<span class="save">保存</span>
					<span  class="cancel sku-cancel">取消</span>					
				</li>
			</ul>
			编辑部分隐藏框 end
			<ul class="task-list">
				<li class="rf-li">
					<i class="edit-btn sku-edit-btn"></i>					
					<span>XXXXX新增商品1</span>
					<i class="del-btn"></i>	
				</li>
				<li class="r-li">
					<table class="s-table" cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th class="r-th">ID</th>
								<th class="r-th">描述</th>
								<th class="r-th">状态</th>
								<th class="r-th">类型</th>
								<th class="r-th">创建时间</th>
								<th class="r-th">创建人</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="r-td">e</td>
								<td class="r-td">d</td>
								<td class="r-td">f</td>
								<td class="r-td">d</td>
								<td class="r-td">d</td>
								<td class="r-td">sdf</td>
							</tr>						
						</tbody>
					</table>
				</li>
			</ul> -->
		</div>
		<!-- 查询结果显示框 end-->
		
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
		</div>
	</div>
	<!-- sku选择-模态框 -->
	<div class="modal_layer" belong="mask" id="skuSelectModal">
		<div class="bg_cover"></div>
		<div class="modal_box">
			<div class="modal_top">
				<span class="modal_title">sku选择</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle" style="margin-top:0;">
				<div class="detail_last_item">
					<ul class="detail_info_body">
						<li class="line">
							<div class="width100per text_center">
								<span class="input_box width150"><input type="text" id="sku_search" placeholder="请输入sku名称"/></span>
								<div class="btn btn_primary mleft10" id="skuSearchBtn">查询</div>
							</div>
						</li>
						<li class="line">
							<div class="option_box" type="checkbox" id="skuSelectList">
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" id="skuSelectConfirmBtn">确 定</div>
				<div class="btn btn_default" id="skuSelectCancelBtn">取 消</div>
			</div>
		</div>
	</div>
</body>
</html>