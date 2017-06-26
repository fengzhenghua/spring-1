<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tydic.unicom.util.DateUtil"%>
<%@ taglib prefix="t" uri="http://tydic.com/jsp/core"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>组织机构管理</title>

<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>js/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/departmentManage.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/zTree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="<%=fullPath%>js/departmentManage.js"></script>
<!--[if lt IE 9]>
    <script type="text/javascript" src="<%=fullPath%>js/json2.js"></script>
<![endif]-->

</head>
<body>
	<div class="show" style="height:auto;">
		<div class="padding_blank10"></div>
		<div class="detail">
			<div class="padding_blank10"></div>
			<div class="directory_area">
				<div class="ztree" id="ztree_dept_div">
				</div>
			</div>
			<div class="content_area">
				<div class="dept_info">
					<div class="dept_row">
						<div class="dept_column">
							<span class="bold">部门编码：</span>
							<span id="dept_no_label"></span>
						</div>
						<div class="dept_column">
							<span class="bold">部门名称：</span>
							<span id="dept_name_label"></span>
						</div>
					</div>
					<div class="dept_row">
						<div class="dept_column">
							<span class="bold">部门类型：</span>
							<span id="dept_type_label"></span>
						</div>
						<div class="dept_column">
							<span class="bold">上级部门：</span>
							<span id="parent_dept_name_label"></span>
						</div>
					</div>
				</div>
				<div class="oper_info">
					<table class="oper_table" id="oper_info_list">
						<thead>
							<tr>
								<%-- <th width="10%">&nbsp;</th> --%> <!-- 预留checkbox列 -->
								<th width="25%">工号</th>
								<th width="25%">姓名</th>
								<th>联系电话</th>
							</tr>
						</thead>
						<tbody>
							<!-- 动态加载 -->
							<tr oper_no="">
								<%-- <td class="text_center"><input type="checkbox" name="chk_opers" /></td> --%>
								<td class="text_center">&nbsp;</td>
								<td class="text_center">&nbsp;</td>
								<td class="text_center">&nbsp;</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="padding_blank10"></div>
		</div>
		<div class="padding_blank"></div>
	</div>

	<div class="bg_mask" id="bg_mask">
		<iframe class="bg_mask_iframe"> </iframe>
	</div>
	
	<input type="hidden" id="img_dir" value="<%=fullPath%>images/"/>
	<input type="hidden" id="oper_no" value="${oper_no}"/>
	<input type="hidden" id="dept_no" value="${dept_no}"/>
	<input type="hidden" id="province_code" value="${province_code}"/>
	
	<div class="copyright"> Copyright © 2014 China unicom All Right Reserved</div>
</body>
</html>