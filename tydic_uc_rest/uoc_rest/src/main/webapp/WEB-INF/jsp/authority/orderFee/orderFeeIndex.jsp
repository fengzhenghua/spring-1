<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
<%@include file="../common/common.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>销售</title>
<link href="<%=fullPath%>css/order_fee_processing.css" rel="stylesheet" type="text/css" />
	<script src="<%=fullPath%>js/orderFeeProcessing.js" type="text/javascript"></script>
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
								<!-- <li class="third"><a href="#">我发起的订单</a></li> -->
						 	</ul>

						</div>

					</td>
					<td valign="top" class="c_right">
						<div class="wrap_right">
							<div class="can">您能查询 1 年内的订单！</div>
							
							<table width="810" border="0" cellspacing="0" cellpadding="0"
								class="search_list">
								<tr>
									<!--  <td width="60" height="70" align="right">起始时间</td>
									<td width="150"><div class="date_relative">
											<t:date id="start_time" name="start_time" maxDateElId="end_time" value="<%=DateUtil.getCurrentDate() %>" minDate="2013-01-01"></t:date>
										</div></td>
									<td width="120" align="right">终止时间</td>
									<td width="150"><div class="date_relative">
											<t:date id="end_time" name="end_time" minDateElId="start_time"  value="<%=DateUtil.getCurrentDate() %>"  maxDate="<%=DateUtil.getCurrentDate() %>"></t:date>
										</div></td>
									<td width="120" align="right">客户名称</td>
									<td width="100"><input type="text" id="cust_name" class="c_name"></td>-->
									<td width="80" height="70" align="right">订单随机码：</td>
									<td width="210"><input type="text" id="rand_id" class="c_name" style="width:195px;"></td>
									<td><a href="#" id="query" class="btn_search">查询</a></td>
									<!-- 隐藏字段btn_search_flag 判断是从菜单进入订单列表还是缴费成功后进入订单列表 -->
									<input type="hidden" name="btn_search_flag" id="btn_search_flag" value="${infoOrderBaseMessVo.btn_search_flag}">
									<!-- 隐藏字段    用于填充订单查询条件 -->
									<input type="hidden" id="hidden_start_time" value="${infoOrderBaseMessVo.start_time}"><br>
									<input type="hidden" id="hidden_end_time" value="${infoOrderBaseMessVo.end_time}"><br>
									<input type="hidden" id="hidden_cust_name" value="${infoOrderBaseMessVo.cust_name}"><br>
								</tr>
							</table>
                            
							<div class="box_right">
                              <table width="808"  border="0" cellpadding="0" cellspacing="1" id="grid" class="box_list">
                              	<tr>
									<th width="100" height="32">订单编号</th>
									<th width="130">下单时间</th>
									<th width="100">电信类型</th>
									<th width="130">订单类型</th>
									<th width="110">客户姓名</th>
								<!-- 	<th width="120">当前状态</th> -->
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