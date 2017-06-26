<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>物流配送明细报表</title>
	
    <link rel="stylesheet" type="text/css" href="../../style/css/public.css" />
    <link rel="stylesheet" type="text/css" href="../../style/css/logisticsDetail.css" />    
    <script type="text/javascript" src="../../js/common/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../../js/common/WebUtil.js"></script>
    <script type="text/javascript" src="../../js/common/public.js"></script>
    <script type="text/javascript" src="../../js/UocBaseJs/logisticsDetail.js"></script>
	<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
	
	<div class="container">
		<div class="detail_item">
			<ul class="detail_info_body mtop15">
				<li class="line">
					<div class="width100per">
						<span class="bold mright5">起始时间</span>
						<span class="input_box width150"><input type="text" id="start_time" onclick="WdatePicker();"/></span>						
						<span class="bold mleft5 mright5">结束时间</span>
						<span class="input_box width150"><input type="text" id="end_time" onclick="WdatePicker();"/></span>
						
						<span class="bold mleft10 mright5">受理部门</span>
						<span class="input_box">
							<select id="accept_depart_no">
							</select>
						</span>
						
						<div class="btn btn_primary mleft10" id="searchBtn">查询</div>
						<div class="btn btn_default mleft5" id="resetBtn">重置</div>
					</div>
				</li>
			</ul>
		</div>
		<div style="width:100%;">
			<table class="info_list">
				<thead>
					<tr>
						<th width="3%">省份</th>
						<th width="3%">区域</th>
						<th width="9%">订单号</th>
						<th width="4%">交付状态</th>
						<th width="7%">交付时间</th>
						<th width="9%">物流单号</th>
						<th width="7%">创建时间</th>
						<th width="4%">受理工号</th>
						<th width="6%">受理部门</th>
						<th width="8%">货品名称</th>
						<th width="3%">收货人</th>
						<th width="10%">收货地址</th>
						<th width="5%">收货电话</th>
						<th width="3%">发货人</th>
						<th width="10%">发货地址</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody id="report_list">
					<!-- <tr>
						<td>重庆市</td>
						<td>渝中区</td>
						<td>3831170213000071002</td>
						<td>代收货</td>
						<td>2017-01-25 08:00:00</td>
						<td>3831170213000071111</td>
						<td>2017-02-15 08:00:00</td>
						<td>TEST01</td>
						<td>江北区营业厅</td>
						<td>iphone6s</td>
						<td>张三</td>
						<td>重庆市江北区XX小区X-XX</td>
						<td>18611112222</td>
						<td>李四</td>
						<td>重庆市渝中区某某仓库</td>
						<td>这里有备注</td>
					</tr> -->
				</tbody>
			</table>
		</div>
		<div class="pagination pf">
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
			<div class="btn btn_default mleft25" id="exportBtn">导出</div>
		</div>
	</div>
</body>
</html>