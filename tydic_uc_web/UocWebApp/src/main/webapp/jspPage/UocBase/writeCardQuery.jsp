<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>写卡查询</title>	
    <link rel="stylesheet" type="text/css" href="../../style/css/public.css" />
    <link rel="stylesheet" type="text/css" href="../../style/css/writeCardQuery.css" />    
    <script type="text/javascript" src="../../js/common/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../../js/common/WebUtil.js"></script>
    <script type="text/javascript" src="../../js/common/public.js"></script>
    <script type="text/javascript" src="../../js/UocBaseJs/writeCardQuery.js"></script>
	<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
	
	<div class="container">
		<div class="detail_item">
			<ul class="detail_info_body mtop15">
				<li class="line">
					<div class="width100per">					
						<span class="bold mright5">处理结果</span>
						<span class="input_box width150">
							<select id="query_type">
								<option value="">--请选择处理结果--</option>
								<option value="1">写卡失败</option>
								<option value="2">发货失败</option>
								<option value="0">处理成功</option>
							</select>
						</span>
						<span class="bold mleft10 mright5">订单号：</span>
						<span class="input_box width150"><input type="text" id="order_no"/></span>
						<span class="bold mleft10 mright5">起始卡号</span>
						<span class="input_box width150"><input type="text" id="start_no"/></span>
						<span class="bold mleft10 mright5">结束卡号</span>
						<span class="input_box width150"><input type="text" id="end_no"/></span>						
					</div>
				</li>
				<li class="line mtop5">
					<div class="width100per">
						<span class="bold mright5">设备号码</span>
						<span class="input_box width150"><input type="text" id="dev_no"/></span>
						<span class="bold mleft10 mright5">客户名称</span>
						<span class="input_box width150"><input type="text" id="cust_name"/></span>
						<span class="bold mleft10 mright5">起始时间</span>
						<input type="text" id="time_start" onclick="WdatePicker();"/>	
						<span class="mleft5 mright5">结束时间</span>
						<input type="text" id="time_end" onclick="WdatePicker();"/>					
						<div class="btn btn_primary mleft5" id="searchBtn">查询</div>
					</div>
				</li>
			</ul>
		</div>
		<table class="info_list" id="orderList">
			<thead>
				<tr>
					<th>订单号</th>
					<th>卡号</th>
					<th>客户姓名</th>
					<th>设备号</th>
					<th>写卡时间</th>
					<th>写卡工号</th>
					<th>写卡结果</th>
					<th class="input_box width100">失败描述</th>
				</tr>
			</thead>
			<tbody id="serv_order_list">
				 <tr>
					<td class="width210 text-left">E8312017010711541797493168</td>
					<td class="width100 text-left">13222222222</td>
					<td>张小五五</td>
					<td>0943849</td>
					<td class="width140 text-left">2016-12-23 00:00:00</td>
					<td>CPG007</td>
					<td>成功</td>
					<td class="width300 text-left">写卡成功写卡成功写卡成功写卡成功写卡成功写卡成功写卡成功写卡成功</td>
				</tr>
				<tr>
					<td class="width210 text-left">E8312017010711541797493168</td>
					<td class="width100 text-left">13222222222</td>
					<td>张小五五</td>
					<td>0943849</td>
					<td class="width140 text-left">2016-12-23 00:00:00</td>
					<td>CPG007</td>
					<td>写卡中</td>
					<td class="width300 text-left">写卡中写卡中写卡中写卡中</td>
				</tr>
				<tr>
					<td class="width210 text-left">E8312017010711541797493168</td>
					<td class="width100 text-left">13222278542</td>
					<td>张小五五</td>
					<td>0943849</td>
					<td class="width140 text-left">2016-12-23 00:00:00</td>
					<td>CPG007</td>
					<td>失败</td>
					<td class="width300 text-left">写卡失败写卡失败败写卡失败写卡失败</td>
				</tr>
				<tr>
					<td class="width210 text-left">E8312017010711541797493168</td>
					<td class="width100 text-left">13222222222</td>
					<td>张小五五</td>
					<td>0943849</td>
					<td class="width140 text-left">2016-12-23 00:00:00</td>
					<td>CPG007</td>
					<td>写卡中</td>
					<td class="width300 text-left">写卡中写卡中写卡中写卡中</td>
				</tr>
				<tr>
					<td class="width210 text-left">E8312017010711541797493168</td>
					<td class="width100 text-left">13222278542</td>
					<td>张小五五</td>
					<td>0943849</td>
					<td class="width140 text-left">2016-12-23 00:00:00</td>
					<td>CPG007</td>
					<td>失败</td>
					<td class="width300 text-left">写卡失败写卡失败败写卡失败写卡失败</td>
				</tr>
			</tbody>
		</table>
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
</body>
</html>