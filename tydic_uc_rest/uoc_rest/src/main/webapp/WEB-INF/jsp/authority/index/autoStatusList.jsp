<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
<%@ taglib prefix="t" uri="http://tydic.com/jsp/core" %>
<%@include file="../common/common.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>自助终端视图</title>

<!-- <link href="<%=fullPath%>css/share1.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/card.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/showOrder.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/pre_accept.css" rel="stylesheet" type="text/css" /> -->

<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/share1.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/default.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/share.css" rel="stylesheet" type="text/css" /
<link href="<%=fullPath%>css/check_income.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/response_mobi2.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=fullPath%>js/autoStatusList.js"></script>

</head>
<body>

	<div name="reader_context" id="reader_context">
  		<div class="show">
			<div class="info">
	 			<div class="detail_white">
	 				<!-- 自助机心跳信息表 -->
	 				<div class="show_big_title">自助终端监控视图：
		 				<div style="float: right;font-size: 10px">
		 					<input id="mac_name" type="text" placeholder="请输入自助终端名称" />
		 					<input id="name_search" type="submit" value="搜索"/>
		 					<input id="name_reset" type="reset" value="重置"/>
		 				</div>
	 					<div class="clear"></div>
	 				</div>
						<table id="log" width="936" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#e7e7e7">
					    	<tr>
					        	<td width="290" height="40" align="center" bgcolor="#ececec" style="display:none">mac地址</td>
					        	<td width="150" height="40" align="center" bgcolor="#ececec">自助终端名称</td>
					        	<td width="100" align="center" bgcolor="#ececec">维护员姓名</td>
					        	<td width="150" align="center" bgcolor="#ececec">发卡机状态</td>
					        	<td width="118" align="center" bgcolor="#ececec">空白箱状态</td>
					        	<td width="118" align="center" bgcolor="#ececec">废卡箱状态</td>
					        	<td width="150" align="center" bgcolor="#ececec">打印机状态</td>
					        	<td align="center" bgcolor="#ececec">记录时间</td>
					      	</tr>
				      		<tr>
				      			<td colspan="4" font-size="8"> — —：表示暂无此项数据。</td>
				      		</tr>
			      		</table>
	 				<div class="clear"></div> 
	 			</div>
	 			<div class="clear"></div> 
 			</div>
 			<div class="clear"></div> 
		</div>
		<div class="clear"></div> 
	</div>
 	

</body>
</html>