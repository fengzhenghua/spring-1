<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="../../js/UocBaseJs/taskDealSendGoods.js"></script>
<script type="text/javascript" src="../../js/common/printer.js"></script>
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
</style>
<!-- 发货 -->
<div class="detail_item">
	<ul class="detail_info_head">
		<li class="line">
			<div>发货</div>
		</li>
	</ul>
	<ul class="detail_info_body">
		<li class="line">
			<div class="bold">收货人信息</div>
		</li>
		<li class="line">
			<div class="one_third" style="width:30%;">
				收件姓名：<span id="contact_name"></span>
			</div><div class="one_third" style="width:30%;">
				联系电话：<span id="contact_tel"></span>
			</div><div class="one_third" style="width:30%;">
				收件地址：<span id="contact_address"></span>
			</div>
		</li>
		<li class="line">
			<div class="bold">发货人信息</div>
		</li>
		<li class="line">
			<div class="one_third" style="width:30%;">
				寄件姓名：<span id="j_contact"></span>
			</div><div class="one_third" style="width:30%;">
				联系电话：<span id="j_tel"></span>
			</div><div class="one_third" style="width:30%;">
				寄件地址：<span id="j_address"></span>
			</div>
		</li>
		<li class="line">
			<div class="bold">发货信息</div>
		</li>
		<li class="line">
			<div class="one_third" style="width:30%;vertical-align: middle;">
				货物名称：<span class="input_box width200"><input type="text" id="goods_name" placeholder="请输入货物名称"/></span>
			</div>
			<div class="one_third" style="vertical-align: middle;">
				<span class="space6">备注</span>：<span class="input_box width200"><input type="text" id="remarks"/></span>
			</div>
			<div class="one_third" style="vertical-align: middle;">
				<div class="btn btn_primary" id="saveSendInfoBtn">保存发货信息</div>
			</div>
		</li>
		<li class="line">
			<div class="one_third" style="width:30%;">
				需要保价：<span class="radio_box mleft5"><input type="checkbox" id="isInsure"/></span>
			</div>
			<div class="one_third" style="width:30%;">
				需要回单：<span class="radio_box mleft5"><input type="checkbox" id="isReturn"/></span>
			</div>
			<div class="one_third" style="width:30%;display:none;">
				保价金额（元）：<span class="input_box width150"><input type="text" id="insure_charge" placeholder="请输入保价金额"/></span>
			</div>
		</li>
		<li class="line" style="display: none;">
			<div class="one_third" style="width:30%;">
				代收货款卡号：<span id="cod_account"/></span>
			</div>
			<div class="one_third" style="width:30%;">
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
			<div class="one_third" style="width:30%;">
				运单号：<span class="" id="logistics_no"></span>
			</div>
			<div class="one_third" style="width:30%;display:none;">
				回单号：<span class="" id="return_tracking_no"></span>
			</div>
		</li>
		<li class="line">
			<div class="btn btn_primary" id="billPreviewBtn">快递单预览</div>
			<div class="btn btn_primary" style="width:7%;" id="sendGoodsBtn">发货</div>
			<!-- <div class="btn btn_primary" id="billPrintBtn">快递单打印</div> -->
			<div class="btn btn_error" style="width:7%;" id="revokeOrderBtn">撤单</div>
			<div class="btn btn_error" id="forceFlowBtn">强制流转</div>
		</li>
	</ul>
</div>
