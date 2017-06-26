<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>订单明细报表</title>
	
    <link rel="stylesheet" type="text/css" href="../../style/css/public.css" />
    <link rel="stylesheet" type="text/css" href="../../style/css/logisticsDetail.css" />    
    <script type="text/javascript" src="../../js/common/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../../js/common/WebUtil.js"></script>
    <script type="text/javascript" src="../../js/common/public.js"></script>
    <script type="text/javascript" src="../../js/UocBaseJs/orderDetailStatistics.js"></script>
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
						
						<div class="btn btn_primary mleft10" id="searchBtn">查询</div>
						<div class="btn btn_default mleft5" id="resetBtn">重置</div>
					</div>
				</li>
			</ul>
		</div>
		<div>
			<table class="info_list">
				<thead>
					<tr>
						<th width="8%">订单号码</th>
						<th width="8%">业务名称</th>
						<th width="5%">服务号码</th>
						<th width="3%">撤单标记</th>
						<th width="3%">订购状态</th>
						<th width="6%">创建时间</th>
						<th width="6%">完工时间</th>
						<th width="3%">受理工号</th>
						<th width="3%">受理员工</th>
						<th width="6%">受理渠道名称</th>
						<th width="3%">渠道编码</th>
						<th width="4%">受理渠道类型</th>
						<th width="3%">流程编码</th>
						<th width="5%">产品名称</th>
						<th width="5%">产品编码</th>
						<th width="8%">首月计费方式</th>
						<th width="3%">漫游级别</th>
						<th width="3%">呼叫级别</th>
						<th width="3%">客户名称</th>
						<th width="5%">联系电话</th>
						<th width="15%">地址</th>
						<th width="5%">证件类型</th>
						<th width="7%">证件号码</th>
						<th width="10%">证件地址</th>
						<th width="5%">证件有效期</th>
						<th width="3%">发展人</th>
						<th width="5%">发展人编码</th>
						<th width="3%">发展类型</th>
						<th width="5%">发展渠道名称</th>
						<th width="5%">发展渠道编码</th>
						<th width="5%">发展对象编码</th>
					</tr>
				</thead>
				<tbody id="report_list">
					<!-- <tr>
						<td width="8%">3831170330000124247</td>
						<td width="8%">海南触点开户-发货</td>
						<td width="5%">18696616417</td>
						<td width="3%">正常单</td>
						<td width="3%">流程中</td>
						<td width="6%">2017-03-29 21:11:41</td>
						<td width="6%"></td>
						<td width="3%">CF0540</td>
						<td width="3%">余浩</td>
						<td width="6%">江北红旗河沟营业厅</td>
						<td width="3%">JB</td>
						<td width="4%">1010300</td>
						<td width="3%">531651</td>
						<td width="5%">QQ大王卡</td>
						<td width="5%">89950166</td>
						<td width="8%">标准资费（免首月月租）</td>
						<td width="3%">漫游级别</td>
						<td width="3%">呼叫级别</td>
						<td width="3%">陈俊</td>
						<td width="5%">17318247192</td>
						<td width="15%">重庆重庆市开县重庆市开州区云枫街道职业教育中心</td>
						<td width="5%">18位身份证</td>
						<td width="7%">50023420000426631</td>
						<td width="10%">渠口镇双丰村3组11号</td>
						<td width="5%">2030</td>
						<td width="3%">王健豪</td>
						<td width="5%">8305708581</td>
						<td width="3%">发展类型</td>
						<td width="5%">发展渠道名称</td>
						<td width="5%">发展渠道编码</td>
						<td width="5%">发展对象编码</td>
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