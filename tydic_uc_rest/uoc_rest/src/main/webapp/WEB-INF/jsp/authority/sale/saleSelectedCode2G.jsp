<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
	<link href="<%=fullPath %>/css/share.css" rel="stylesheet" type="text/css" />
	<link href="<%=fullPath %>/css/sale_selected_code.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=fullPath %>/js/sale_selected_code_2g.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>选择2G套餐</title>
</head>
<body>
	<input id="full_path_value" name="full_path_value" value="<%=fullPath%>" type="hidden">
  	<!-- 选择号码页面中获取的数据 -->
  	<input type="hidden" id="acc_nbr" 	value="${codeOfrNumberVo.acc_nbr}">		<!-- 设备号码 -->
	<input type="hidden" id="tele_type" value="${codeOfrNumberVo.tele_type }">	<!-- 电信类型 -->
	<input type="hidden" id="good_type" value="${codeOfrNumberVo.good_type }">	<!-- 靓号类型 -->
	<input type="hidden" id="pay_flag" 	value="${codeOfrNumberVo.pay_flag }">	<!-- 付费类型 -->
  	<div class="crumbs">当前位置: 
  		<a href="javascript:void(0);">销售(2G)</a>> 
  		<a href="javascript:void(0);">选号</a>> 
  		<a href="javascript:void(0);">选套餐及活动</a>
  	</div>
  	<div class="sale_content">
  		<div class="right">
  		<!-- 根据套餐名称查询条件 -->
		<div class="selected_code">
			已选择号码: ${codeOfrNumberVo.acc_nbr}<span>含${codeOfrNumberVo.fee}元预存话费</span>
		</div>
		<!-- 过滤条件 -->
		<div class="select_code">
			<div class="search_blur">过滤条件</div>		
			<dl class="define">
				<dt>模糊查询：</dt>
				<dd>
					<dl class="search_code">
						<dt><input type="text" class="text" id="txt_search" value="套餐名称" /></dt>
						<dd><input type="text" value="搜索" class="btn" id="search_btn"/></dd>
					</dl>
				</dd>
				<div class="clear"></div>
			</dl>
			<dl id="ofr_sub_type_dl">
				<dt>套餐类型：</dt>
					<dd>
						<t:li codeType="package_type" checkFirst="true" checkClass="package" ></t:li>
					</dd>
				<div class="clear"></div>
			</dl>			
			<dl id="month_fee_dl">
				<dt>月消费金额：</dt>
					<dd >
						<t:li codeType="month_fee"></t:li>
					</dd>
				<div class="clear"></div>
			</dl>			
			<dl id="month_call_duration_dl">
				<dt>月通话时长：</dt>
					<dd>
						<t:li codeType="month_call_duration"></t:li>
					</dd>
				<div class="clear"></div>
			</dl>			
			<dl class="define" id="month_net_duration_dl">
				<dt>月上网流量：</dt>
					<dd>
						<t:li codeType="month_net_duration"></t:li>
					</dd>
				<div class="clear"></div>
			</dl>
		</div>
		<div class="selected_line" id="selected_line"></div>
		<!-- 已经选中的过滤条件 -->
		<div class="selected" id="selected_conditions">
			<dl>
				<dt>您已经选择：</dt>
					<dd>
						<a href="javascript:void(0);" class="selected_ofr_type" id="ofr_sub_type_a" style="display: none"><span>&nbsp;</span></a>
				   		<a href="javascript:void(0);" class="selected_month_fee"  id="month_fee_a" style="display: none"><span>&nbsp;</span></a>
				   		<a href="javascript:void(0);" class="selected_month_call_duration" id="month_call_duration_a" style="display: none"><span>&nbsp;</span></a>
				   		<a href="javascript:void(0);" class="selected_month_net_duration" id="month_net_duration_a" style="display: none"><span>&nbsp;</span></a>
				   		<a href="javascript:void(0);" class="clear_condition" id="clear_condition_a" style="display: none" >清除筛选条件</a>
				  	</dd>
				<div class="clear"></div>
			</dl>
		</div>
		<!-- 过滤条件的隐藏字段 -->
		<input type="hidden" id="ofr_sub_type_hidden" >
   		<input type="hidden" id="month_fee_hidden" >
   		<input type="hidden" id="month_call_duration_hidden" >
   		<input type="hidden" id="month_net_duration_hidden" >
   		<!-- 套餐展示区 -->
		<div class="mobile_code">
			<table width="998" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="54" height="43" align="right">排序：</td>
				<td width="350">
					<ul class="sort">
						<li><a href="javascript:void(0);">号码升序</a></li>
						<li><a href="javascript:void(0);">号码降序</a></li>
						<li><a href="javascript:void(0);">价格升序</a></li>
						<li><a href="javascript:void(0);">价格降序</a></li>
						<div class="clear"></div>
					</ul>
				</td>
				<td style="padding-right:20px;">
				</td>
			  </tr>
			</table>
			<div  class="warp_order" id="sale_selected_code">
		    </div>
			<div class="warp_order">
				<div class="clear"></div>
				<div class="line"></div>
				<div class="pages" id="layerPaging"></div>		
			</div>
		</div>
		<div class="clear"></div>
		<form action="saleOpen" id="saleOpenForm" method="post">
			<!-- 前一页传过来的参数 -->
			<input type="hidden" name="acc_nbr" 		value="${codeOfrNumberVo.acc_nbr}">		<!--设备号码  -->
			<input type="hidden" name="acc_nbr_fee"  	value="${codeOfrNumberVo.fee}">			<!--号码预存话费  -->
			<input type="hidden" name="tele_type"  		value="${codeOfrNumberVo.tele_type}">	<!--电信类型  -->
			<input type="hidden" name="good_type" 		value="${codeOfrNumberVo.good_type }">	<!-- 靓号类型 -->
			<!-- 套餐字段 -->
			<input type="hidden" id="ofr_id_result" 		name="product_id" >			<!--套餐ID -->
			<input type="hidden" id="ofr_name_result" 		name="product_name" >		<!--套餐名称  -->
			<input type="hidden" id="ofr_desc_result" 		name="product_desc" >		<!--套餐描述  -->
			<input type="hidden" id="market_price_result" 	name="market_price" >		<!--套餐价格  -->
			<input type="hidden" id="ofr_status_result" 	name="ofr_status" >			<!--套餐状态  -->
			<input type="hidden" id="eff_flag_result" 		name="eff_flag" >			<!--生效标志  -->
			<input type="hidden" id="eff_date_result" 		name="eff_date" >			<!--生效时间  -->
			<input type="hidden" id="exp_date_result" 		name="exp_date" >			<!--失效时间  -->
			<input type="hidden" id="oper_date_result" 		name="oper_date" >			<!--操作时间  -->
			<input type="hidden" id="exclude_code_result" 	name="exclude_code" >		<!--  -->
			<input type="hidden" id="rule_id_result" 		name="rule_id" >			<!--销售品产品选择关系规则  -->
			<input type="hidden" id="ofr_type_result" 		name="ofr_type" >			<!--电信类型编码  -->
			<input type="hidden" id="ofr_sub_type_result" 	name="ofr_sub_type_3g" >	<!--套餐类型  -->
			<input type="hidden" id="bss_product_result" 	name="bss_product" >		<!--  -->
			<input type="hidden" id="brand_code_result" 	name="brand_code" >			<!--品牌编码-->
			<input type="hidden" id="pay_flag_result" 		name="prepay_flag" >		<!--付费标志 -->
		</form>
		<div class="open_submit">
			<a href="javascript:void(0);" class="ok" id="btn_submit">确 定</a>
			<a href="javascript:void(0);" class="cancel" id="btn_cancel">取 消</a>
		</div>
  	</div>
  	</div>
</body>
</html>