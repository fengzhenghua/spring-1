<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="../../js/UocBaseJs/taskDealExamine.js"></script>
<!-- 审核 -->
<div class="detail_item">
	<ul class="detail_info_head">
		<li class="line">
			<div>审核</div>
		</li>
	</ul>
	<ul class="detail_info_body">
		<li class="line">
			<div class="one_third">
				用户姓名：<span id="exaUserName"></span>
			</div><div class="one_third">
				身份证号码：<span id="exaIdNum"></span>
			</div>
		</li>
		<li class="line">
			<div class="one_third">
				收货人姓名：<span id="exaReceiveName"></span>
				<span class=""><input type="text" id="exaReceiveNameChange"/></span>
			</div><div class="one_third">
				收货人电话：<span id="exaPhone"></span>
				<span class=""><input type="text" id="exaPhoneChange" onkeyup="value=value.replace(/[^\d]/g,'')"/></span>
			</div>
		</li>
		<li class="line">
			<div class="width100per">
				收货地址：
				<div class="inline" id="exaRecAddr">
					<span class="" op_val="" id="exaRecShowProvince"></span>
					<span class="mleft5" op_val="" id="exaRecShowCity"></span>
					<span class="mleft5" op_val="" id="exaRecShowDistrict"></span>
					<span class="mleft5" id="exaRecShowDetail"></span>
				</div>
				<div class="inline hide" id="exaRecUpdate">
					<span class="input_box">
						<select id="exaRecProvince" >
							<!-- <option value="">重庆</option>	 -->						
						</select>
					</span>
					<span class="input_box mleft5">
						<select id="exaRecCity"  >							
							<!-- <option value="">重庆市</option> -->
						</select>
					</span>
					<span class="input_box">
						<select id="exaRecDistrict" >							
							<!-- <option value="">渝中区</option> -->
						</select>
					</span>
					<span class="input_box width300  mleft5"><input type="text" id="exaRecDetail"/></span>
				</div>
			</div>
		</li>
		<li class="line" id="delivery_region">
		  <div class="">
				自提区域：
			
			<div class="inline" id="exaDelivery">
				<span class="" op_val="" id="exaDeliveryRegion"></span>
				<span class="mleft5" op_val="" id="exaDeliveryDeptNo"></span>
			</div>
			<div class="inline hide" id="exaRecUpdate2">
				<span class="input_box">
					<select id="deliveryRegionList" >							
<!-- 						<option value="">渝中区</option> -->
					</select>
				</span>
				<span class="input_box">
					<select id="deliveryDeptNoList" >							
<!-- 						<option value="">测试营业厅</option> -->
					</select>
				</span>
			</div>
		 </div>
		</li>
		<div class="btn btn_default mleft5" id="exaRecUpdateBtn">更正</div>
		<li class="line">
			<!-- <div class="btn btn_primary" id="exaValidateBtn">审核校验</div> -->
			<div class="btn btn_primary" id="exaPassBtn">确认</div>
			<div class="btn btn_error" id="revokeOrderBtn">撤单</div>
			<div class="btn btn_error" id="forceFlowBtn">强制流转</div>
		</li>
	</ul>
</div>
