<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/jsp/oppocard/common.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>属性配置</title>
    <link rel="stylesheet" type="text/css" href="../css/pc/public.css" />
    <link rel="stylesheet" type="text/css" href="../css/pc/contact.css" />    
    <script type="text/javascript" src="../js/Plugin/jquery/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../js/Plugin/qrCode/jquery.qrcode.min.js"></script>
    <script type="text/javascript" src="../js/Plugin/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="../js/pc/common/public.js"></script>
    <script type="text/javascript" src="../js/pc/common/WebUtil.js"></script>
    <script type="text/javascript" src="../js/pc/contactCreateAttr.js"></script>
</head>
<body>
	<div class="container">
		<div>
			<div class="btn btn_link font14" id="returnBtn">&lt;&lt;返回</div>
		</div>
		<div class="detail_item">
			<ul class="detail_info_body mtop15">
				<li class="line mtop5">
					<div class="width100per">
						<div class="btn btn_info mleft5" id="addApAttrBtn">新增触点属性</div>
						<div class="btn btn_info mleft5" id="refreshApAttrBtn">刷新缓存</div>
					</div>
				</li>
			</ul>
		</div>
		<table class="info_list">
			<thead>
				<tr>
					<th width="20%">触点方案名称</th>
					<th width="10%">属性类型</th>
					<th width="10%">属性名称</th>
					<th width="10%">属性的键</th>
					<th width="30%">属性的值</th>
					<th width="15%">操作</th>
				</tr>
			</thead>
			<tbody id="apAttrList">
				<tr>
					<td>测试0419-1451</td>
					<td>微信配置</td>
					<td>token</td>
					<td>asjdkahfuirkfbkjashfahflashfkjahekjfhkajhf</td>
					<td class="text_center">
						<div class="btn btn_link" name="editBtn">修改</div>
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
					<option value="10" selected="true">10</option>
					<option value="20">20</option>
				</select>				
			</span>
			<span class="page_turn" id="pageFirst">&lt;&lt;</span>
			<span class="page_turn" id="pageUp">&lt;</span>
			<span class="input_box width40"><input type="text" class="text_center" id="pageNo" value="1" readonly/></span>
			<span class="page_turn" id="pageDown">&gt;</span>
			<span class="page_turn" id="pageLast">&gt;&gt;</span>
			<span class="mright10">共<b class="mleft5 mright5" id="totalPage">0</b>页</span>	
			<div class="btn btn_default mleft25" id="exportBtn">导出</div>
		</div>
	</div>
	<!-- 触点属性配置-模态框 -->
	<div class="modal_layer" belong="mask" id="apAttrModal">
		<div class="bg_cover"></div>
		<div class="modal_box">
			<div class="modal_top">
				<span class="modal_title">触点属性配置</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle">
				<div class="detail_item">
					<ul class="detail_info_body mtop15">
						<li class="line">
							<div class="width100per">
								<span class="bold mright5">属性类型</span>
								<span class="input_box width200">
									<input type="hidden" id="modalId" />
									<input type="text" class="cs_pointer" id="modalApAttrType" no="1" value="默认类型" placeholder="请选择属性类型" readonly/>
								</span>
							</div>
						</li>
						<li class="line mtop5">
							<div class="width100per">
								<span class="bold mright5">属性名称</span>
								<span class="input_box width200">
									<input type="text" id="modalApAttrName" placeholder="请输入属性名称"/>
								</span>
							</div>
						</li>
						<li class="line mtop5">
							<div class="width100per">
								<span class="bold mright5">属性的键</span>
								<span class="input_box width200">
									<input type="text" id="modalApAttrId" placeholder="请输入属性的键"/>
								</span>
							</div>
						</li>
						<li class="line mtop5">
							<div class="width100per">
								<span class="bold mright5">属性的值</span>
								<span class="input_box width200">
									<input type="text" id="modalApAttrValue" placeholder="请输入属性的值"/>
								</span>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" id="apAttrConfirmBtn">保 存</div>
				<div class="btn btn_default" id="apAttrCancelBtn">取 消</div>
			</div>
		</div>
	</div>
</body>
</html>