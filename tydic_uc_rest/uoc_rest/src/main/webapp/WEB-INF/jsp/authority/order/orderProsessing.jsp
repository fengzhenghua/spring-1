<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
<%@include file="../common/common.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>销售</title>
<link href="<%=fullPath%>css/order_processing.css" rel="stylesheet" type="text/css" />
	<script src="<%=fullPath%>js/orderProcessing.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$("#grid tr:odd").addClass("odd");
		$("#grid tr:even").addClass("even");
		$("#start_time").addClass("width");
		$("#end_time").addClass("width");
		
	});
</script>
</head>
<body>
<div class="crumbs">当前位置: <a href="###">首页</a> > <a href="###">订单处理</a></div>
	<div class="sale_content">
		<div class="content">
			<table width="1000" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="175" valign="top" bgcolor="#EEEEEE" class="c_left">
						<div class="wrap">
							<ul>

								<!-- <li class="first"><a href="#">待处理订单</a></li>
								<li class="second"><a href="#">已处理订单</a></li> -->

								<li class="first"><a href="#" class="hover">订单处理</a></li>
								<li class="third"><a href="#">我发起的订单</a></li>
							</ul>

						</div>

					</td>
					<td valign="top" class="c_right">
						<div class="wrap_right">
							<div class="can">您能查询 1 年内的订单！</div>
							
							<table width="810" border="0" cellspacing="0" cellpadding="0"
								class="search_list">
								<tr>
									<td width="60" height="40" align="center">起始时间</td>
									<td width="70"><div class="date_relative">
											<t:date id="start_time" name="start_time" maxDateElId="end_time" value="<%=DateUtil.getCurrentDate() %>" minDate="2013-01-01"></t:date>
										</div></td>
									<td width="60" align="center">终止时间</td>
									<td width="70"><div class="date_relative">
											<t:date id="end_time" name="end_time" minDateElId="start_time"  value="<%=DateUtil.getCurrentDate() %>"  maxDate="<%=DateUtil.getCurrentDate() %>"></t:date>
										</div></td>
									<td width="60" align="center">订单类型</td>
									<td width="70">
                                       <t:select id="order_sub_type_status" name = "order_sub_type_status" codeType="order_sub_type_status"></t:select>
									</td>
									<td width="60" align="center" height="40">查询状态</td>
									<td>
                                       <t:select id="order_processing_status" name = "order_processing_status" codeType="order_processing_status"></t:select>
									</td>
								<tr>
									<td width="60" align="center" height="40">客户名称</td>
									<td width="70"><input type="text" id="cust_name" class="c_name" style="width:166px;"></td>
									<td widht="60"></td>
									<td colspan="4"><a href="#" id="query" class="btn_search">查询</a></td>
									<td></td>
								</tr>
								
							</table>
                            
							<div class="box_right">
                              <table width="808"  border="0" cellpadding="0" cellspacing="1" id="grid" class="box_list">
                              	<tr>
									<th width="180" height="32">订单编号</th>
									<th width="110">下单时间</th>
									<th width="80">电信类型</th>
									<th width="100">订单类型</th>
									<th width="110">客户姓名</th>
									<th width="120">当前状态</th>
									<th width="108">操作</th>
						 		</tr>
                              </table>  
                              <div id="showData" ></div>
                              <div id="pager"></div>
						    </div>
					</td>
				</tr>
			</table>
		</div>
	</div>




</body>
</html>