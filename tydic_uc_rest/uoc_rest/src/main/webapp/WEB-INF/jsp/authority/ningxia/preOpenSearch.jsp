<%@page import="com.tydic.unicom.crm.web.uss.constants.UrlsMappings"%>
<%@page import="com.tydic.unicom.crm.web.uss.constants.ControllerMappings"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>开户预受理单查询</title>
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="<%=fullPath%>css/share.css" >
<style type="text/css">
.list_nx{padding:4px 0px 0px 0px; width:874px; height:26px; margin:0 auto; vertical-align:central;}
.left_data_nx{float:left;}
.left_data_nx input {padding-left:4px; margin-right:4px; border:1px solid #e7e7e7; height:24px; line-height:24px; width: 148px;}
</style>
<script type="text/javascript" src="<%=fullPath%>js/preOpen.js"></script>
<script type="text/javascript">
var fullPath = '<%=fullPath%>';
</script>
</head>
<body>
<div class="show">
	<div class="info">
		<ul class="detail">
			<form id="searchfom" method="post">
				<li class="list_nx">
	        		<div class="left_data_nx">
	        			手机号码：<input type="text" id="device_number" name="" value=""></input>
	        		</div>
	        		<div class="left_data_nx">
	        			身份证号：<input type="text" id="cust_id" name="" value=""></input>
	        		</div>
	        		<div class="left_data_nx" style="padding-left: 57px;">
	                    <a href="javascript:void(0)" id="search"><div class="input_button">查  询</div></a>
	                </div>
	        	</li>
			</form>
				<li class="list" style="height: 350px;">
	         		<!-- jqGrid 数据 table -->
					<table id="rlist"></table>
					<!-- jqGrid 分页 div  -->
					<div id="gridPager"></div>
	         	</li>
		</ul>
	</div>
</div>
</body>
</html>