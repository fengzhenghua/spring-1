<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>发货</title>
    <link rel="stylesheet" type="text/css" href="../../style/css/public.css" />
    <script type="text/javascript" src="../../js/common/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../../js/common/highcharts.js"></script>
    <script type="text/javascript" src="../../js/common/WebUtil.js"></script>
    <script type="text/javascript" src="../../js/common/public.js"></script>
    <script type="text/javascript" src="../../js/UocBaseJs/sendGoods.js"></script>
    
    <style>
    	.sButton{
    		width:5%;
    		text-align:center;
    		vertical-align:middle;
    		font-weight:bold;
    		-moz-border-radius:4px;
    		-webkit-border-radius:4px;
    		border-radius:4px;
    		cursor:pointer;
    	}
    	.space6{letter-spacing:11px;}
    	.space8{display:inline-block; *zoom:1; *display:inline;width:24px; height:16px;}
    </style>
</head>
<body>
	<div class="container" style="margin-left:50px;margin-right:40px;">
		<!-- 发货 -->
		<div class="detail_item">
			<ul class="detail_info_head" style="margin-left:12px;">
				<li class="line">
					<div><i class="l-i"></i>&nbsp;&nbsp;发货</div>
				</li>
			</ul>
			<ul class="detail_info_body">
				<li class="line background brs">
					<div class="one_third" style="width:26%;">
						SIM卡号：<span class="input_box width200"><input type="text" id="sim_id" placeholder="请输入SIM卡号" oninput="chcekInput(value);"/></span>
					</div>
					<div class="one_third" style="width:25%;">
						设备号：<span class="input_box width200"><input type="text" id="acc_nbr" placeholder="请输入设备号"/></span>
					</div>
					<div class="one_third" style="width:28%;">
						服务订单号：<span class="input_box width200"><input type="text" id="serv_order_no" placeholder="请输入服务订单号"/></span>
					</div>
					<div class="btn_primary input_box sButton" id="query_info_sf">查询</div>
				</li>
			</ul>
			<!-- <hr style="border:none;border-top:1px solid #CCCCCC;"/> -->
			<ul class="detail_info_body">
				<li class="line A color1">
					<div class="bold">收货人信息</div>
				</li>
				<li class="line">
					<div class="one_third" style="width:25%;margin-left:18px;">
						收件姓名：<span class="input_box width200"><input type="text" id="contact_name" placeholder=""/></span>
					</div><div class="one_third" style="width:25%;">
						联系电话：<span class="input_box width200"><input type="text" id="contact_tel" placeholder=""/></span>
					</div>
					<div class="one_third" style="width:25%;">
						收件地址：<span class="input_box width200"><input type="text" id="contact_address" placeholder=""/></span>
					</div>
				</li>
				<hr style="border:none;border-top:1px solid #CCCCCC;"/>
				<li class="line A color2">
					<div class="bold">发货人信息</div>
				</li>
				<li class="line">
					<div class="one_third" style="width:25%;margin-left:18px;">
						寄件姓名：<span class="input_box width200"><input type="text" id="j_contact" placeholder="请输入寄件人姓名"/></span>
					</div>
					<div class="one_third" style="width:25%;">
						联系电话：<span class="input_box width200"><input type="text" id="j_tel" placeholder="请输入寄件人联系电话"/></span>
					</div>
					<div class="one_third" style="width:25%;">
						寄件地址：<span class="input_box width200"><input type="text" id="j_address" placeholder="请输入寄件人地址信息"/></span>
					</div>
				</li>
				<hr style="border:none;border-top:1px solid #CCCCCC;"/>
				<li class="line A color3">
					<div class="bold">发货信息</div>
				</li>
				<li class="line">
					<div class="one_third" style="width:25%;margin-left:18px;">
						货物名称：<span class="input_box width200"><input type="text" id="goods_name" placeholder="请输入货物名称"/></span>
					</div>
					<div class="one_third" style="width:25%;">
						<span class="space6">备注</span>：<span class="input_box width200"><input type="text" id="remarks"/></span>
					</div>
					<div class="btn_primary input_box sButton" style="width:8%;" id="saveSendInfoBtn">保存发货信息</div>
				</li>
				<li class="line">
					<div class="one_third" style="width:25%;margin-left:18px;">
						需要保价：<span class="radio_box mleft5"><input type="checkbox" id="isInsure"/></span>
					</div>
					<div class="one_third" style="width:30%;">
						需要回单：<span class="radio_box mleft5"><input type="checkbox" id="isReturn"/></span>
					</div>
					<div class="one_third block" style="width:25%;display:none;margin-left:18px;margin-top:10px;">
						保价金额（元）：<span class="input_box width200"><input type="text" id="insure_charge" placeholder="请输入保价金额"/></span>
					</div>
				</li>
				<li class="line" style="display: none;">
					<div class="one_third" style="width:25%;">
						代收货款卡号：<span id="cod_account"/></span>
					</div>
					<div class="one_third" style="width:25%;">
						代收货款金额（元）：<span id="cod_charge"/></span>
					</div>
				</li>
				<!-- <li class="line">
					<div class="btn btn_primary" id="saveSendInfoBtn">保存发货信息</div>
				</li> -->
				<!-- <li class="line">
					<div class="btn btn_primary" id="sendGoodsBtn">发货</div>
				</li> -->
				<li class="line">
					<div class="one_third" style="width:25%;margin-left:18px;">
						运单号：<span class="" id="logistics_no"></span>
					</div>
					<div class="one_third" style="width:25%;display:none;">
						回单号：<span class="" id="return_tracking_no"></span>
					</div>
				</li>
				<hr style="border:none;border-top:1px solid #CCCCCC;"/>
				<li class="line">
					<div class="btn btn_primary" id="billPreviewBtn">快递单预览</div>
					<div class="btn btn_primary" style="width:7%;" id="sendGoodsBtn">发货</div>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>