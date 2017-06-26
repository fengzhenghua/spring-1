<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<head>
<title>客户服务</title>
<link href="<%=fullPath%>css/customer.css" rel="stylesheet"
	type="text/css" />
<script src="<%=fullPath%>js/customerServ.js" type="text/javascript" />
<script type="text/javascript">
	$(function() {
		$(".sale_mail").mouseover(function() {
			$(".notice_show").show();
		}).mouseout(function() {
			$(".notice_show").hide();
		});
	});
</script>
</head>
<body>
	<div class="crumbs">
		当前位置: <a href="#">首页</a> > <a href="#">客户服务</a>
	</div>
	<div class="sale_content">
		<div class="content">
			<div class="line"></div>
			<table width="1000" border="0" cellspacing="0" cellpadding="0"
				class="customer_search">
				<tr>
					<td width="78" height="30" align="center">证件类型：</td>
					<td width="140">
					<t:select id="card_type" codeType="id_type" clazz="sel">
					</t:select>
					</td>
					<td width="78" align="center">证件号码：</td>
					<td width="240"><label> <input type="text"
							name="textfield" class="txt_input" id='card_no' />
					</label></td>
					<td width="78" align="center">业务号码：</td>
					<td width="240"><input type="text" name="textfield2"
						id='server_no' class="txt_input" /></td>
					<td width="146"><div class="btn">
							<a href="#" class="search" id='query'>查询</a><a href="#"
								class="reset" id='reset'>重置</a>
						</div></td>
				</tr>
			</table>
			<div class="scrool">
				<div class="scrool_l">
					<table width="204" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="62" height="22">客户名称：</td>
							<td width="142" id='customer_name'></td>
						</tr>
						<tr>
							<td height="22">证件类型：</td>
							<td id='cardtype'></td>
						</tr>
						<tr>
							<td height="22">证件号码：</td>
							<td id='cardno'></td>
						</tr>
						<tr>
							<td height="22">客户等级：</td>
							<td><div id = 'leve'></div></td>
						</tr>
						<tr>
							<td height="22">客户经理：</td>
							<td id='cust_man'></td>
						</tr>
					</table>
				</div>
				<div class="scrool_r">
					<div class="arrow_l"><a href="javascript:;" class="no" id="arrow_l"></a></div>
					<div class="arrow_r"><a href="javascript:;" class="no" id="arrow_r"></a></div>
					<div class="section" style="overflow: hidden;height: 100px;">
						<ul id='custom_info_div' style="margin-left: 0px;">
						</ul>
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="function">
				<div class="menu">
					<a href="#" class="menu_1">客户资料</a><a href="#" class="menu_2">账户资料</a><a
						href="#" class="menu_3">用户资料</a><a href="#" class="menu_4 hover">收费管理</a><a
						href="#" class="menu_5">销户</a><a href="#" class="menu_6">产品变更</a><a
						href="#" class="menu_7 ">服务状态变更</a><a href="#" class="menu_8">补换卡</a><a
						href="#" class="menu_9">票据打印</a>
				</div>
				<div id="sub_context_div">
					
				</div>
				
				
				<div id="pay_type_html" style="display:none">
					<t:select id="pay_type" codeType="pay_type" clazz="sel"></t:select>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
