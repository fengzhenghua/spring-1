<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="../../js/UocBaseJs/taskDealReadWriteCard.js"></script>
<script type="text/javascript" src="../../js/UocBaseJs/writeCard.js"></script>
<!-- USIM卡 -->
<div class="detail_item">
	<ul class="detail_info_head">
		<li class="line">
			<div>USIM卡</div>
		</li>
	</ul>
	<ul class="detail_info_body">
		<li class="line">
			<div class="width100per">
				<span class="input_box width250"><input type="text" id="card_no" placeholder="请读卡"/></span>
			</div>
		</li>
		<li class="line">
			<div class="btn btn_primary" id="readCardBtn">读卡</div>
			<div class="btn btn_primary" id="writeCardBtn">一键写卡</div>
			<div class="btn btn_error" id="revokeOrderBtn">撤单</div>
			<div class="btn btn_error" id="forceFlowBtn">强制流转</div>
		</li>
	</ul>
</div>
