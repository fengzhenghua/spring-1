<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>自动流程处理</title>
    <link rel="stylesheet" type="text/css" href="../../style/css/public.css" />
    <link rel="stylesheet" type="text/css" href="../../style/css/autoFlowProcess.css" />
    <script type="text/javascript" src="../../js/common/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../../js/common/public.js"></script>
    <script type="text/javascript" src="../../js/common/WebUtil.js"></script>
    <script type="text/javascript" src="../../js/UocBaseJs/autoFlowProcess.js"></script>
    <script type="text/javascript" src="../../js/UocBaseJs/cardFunctions.js"></script>
    <script type="text/javascript" src="../../js/common/printer.js"></script>
</head>
<body>
	<div class="container">
		<div class="title"><i></i>&nbsp;&nbsp;代写卡订单查询</div>
		
		<div class="three mtop10 br">
			<div class="top">
				<ul class="detail_info_body">
					<li class="line">
						<div class="width100per clearfix">
							<span class="left">	
								<span class="input_box width200">
									<input class="o-input br" type="text" id="search_info" placeholder="请输入订单号、姓名">
								</span>
								
								<span class="bold">信息来源：</span>
								<span class="input_box width150">
									<select id="order_source">
										<option value="">总部商城</option>
									</select>
								</span>

								<span class="bold mleft10">产品组：</span>
								<span class="input_box width150">
									<select id="product_group">
										<option value="">--请选择产品组--</option>
									</select>
								</span>

								<div class="btn btn_primary mleft10" id="search_btn">查询订单</div>
								<div class="btn btn_primary mleft10" id="start_btn">开始处理</div>
							</span>
							<span class="right">
								<!-- <span class="o-li br cursor">
									<i class="li-i-o i1"></i>
									<span class="li-span-o" id="write_fail_query">&nbsp;写卡失败</span>
								</span> -->

								<span class="o-li br cursor">
									<i class="li-i-o i2"></i>
									<span class="li-span-o" id="scan_resend">&nbsp;重发货处理</span>
								</span>

								<span class="o-li br cursor">
									<i class="li-i-o i3"></i>
									<span class="li-span-o" id="process_query">&nbsp;已处理订单查询</span>
								</span>
							</span>
						</div>
					</li>
				</ul>
			</div>

			<div class="bottom">
				<table  class="info_list">
					<thead>
						<tr>
							<th>订单号</th>
							<th>业务号码</th>
							<th>开户名</th>
							<th>创建日期</th>
							<th>CB单号</th>
							<th>ICCID</th>
							<th>物流单号</th>
							<th>处理结果</th>
						</tr>
					</thead>
					<tbody id="order_list">
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<!-- 扫码发货-模态框 -->
	<div class="modal_layer" belong="mask" id="scanResendModal">
		<div class="bg_cover"></div>
		<div class="modal_box">
			<div class="modal_top">
				<span class="modal_title">扫码发货</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle">
				<div class="width100per clearfix">
					请扫描SIM卡号：<span class="input_box width200"><input type="text" id="sim_id" placeholder="请扫描SIM卡号" oninput="chcekInput(value);"/></span>
				</div>
				<div class="width100per clearfix">
					处理结果：<span class="width100per clearfix" id="send_result"></span>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" id="scanResendConfirmBtn">处理完成</div>
			</div>
		</div>
	</div>
</body>
</html>