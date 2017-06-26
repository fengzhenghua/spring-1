<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="../../js/UocBaseJs/taskDealRevokeOrder.js"></script>
<!-- 撤单审核 -->
<div class="detail_item">
	<ul class="detail_info_head">
		<li class="line">
			<div>撤单审核</div>
		</li>
	</ul>
	<ul class="detail_info_body">
		<li class="line">
			<div class="one_third">
				撤单类型：<span id="revokeType"></span>
			</div><div class="one_third">
				撤单原因：<span id="revokeReason"></span>
			</div><div class="one_third">
				撤单环节：<span id="revokeTache"></span>
			</div>
		</li>
		<li class="line">
			<div class="one_third">
				发起人工号：<span id="operNo"></span>
			</div><div class="one_third">
				发起时间：<span id="operTime"></span>
			</div>
		</li>
		<li class="line">
			<div class="width100per">
				备注：<span class="input_box width500"><input type="text" id="remarks"/></span>
			</div>
		</li>
		<li class="line">
			<div class="btn btn_primary" id="revokePassBtn">审核通过</div>
			<div class="btn btn_error" id="revokeRejectBtn">审核不通过</div>
		</li>
	</ul>
</div>
