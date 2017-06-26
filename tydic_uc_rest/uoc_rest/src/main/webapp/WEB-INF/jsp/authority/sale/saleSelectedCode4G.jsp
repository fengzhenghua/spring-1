<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
	<link href="<%=fullPath %>/css/share.css" rel="stylesheet" type="text/css" />
	<link href="<%=fullPath %>/css/sale_selected_code.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=fullPath %>/js/sale_selected_code_4g.js"></script>
	<script type="text/javascript">
	$(function() {
		$("#terminal_style_name").hide();
		$("#terminal_style").hide();
	});
</script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>选择4G普通套餐及活动</title>
</head>
<body>
	<input id="full_path_value" name="full_path_value" value="<%=fullPath%>" type="hidden">
  	<!-- 选择号码页面中获取的数据 -->
  	<input type="hidden" id="acc_nbr" 	value="${codeOfrNumberVo.acc_nbr}">		<!-- 设备号码 -->
	<input type="hidden" id="tele_type" value="${codeOfrNumberVo.tele_type }">	<!-- 电信类型 -->
	<input type="hidden" id="good_type" value="${codeOfrNumberVo.good_type }">	<!-- 靓号类型 -->
	<input type="hidden" id="pay_flag" 	value="${codeOfrNumberVo.pay_flag }">	<!-- 付费类型 -->
	<input type="hidden" id="limit_money" 	value="${codeOfrNumberVo.limit_money }">	<!-- 付费类型 -->
  	<div class="crumbs">当前位置: 
  		<a href="javascript:void(0);">销售(4G)</a>> 
  		<a href="javascript:void(0);">选号</a>> 
  		<a href="javascript:void(0);">选套餐及活动</a>
  	</div>
  	<div class="sale_content">
  		<div class="right">
	  		<!-- 根据套餐名称查询条件 -->
			<div class="selected_code">
				已选择号码: ${codeOfrNumberVo.acc_nbr}<span>含${codeOfrNumberVo.fee}元预存话费</span>
			</div>
			<div class="select_code">
				<div class="search_blur">过滤条件</div>
				<dl id="month_fee_dl">
				<dt>月消费金额：</dt>
						<dd >
							<t:li codeType="month_fee"></t:li>
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
					   		<a href="javascript:void(0);" class="selected_month_fee"  id="month_fee_a" style="display: none"><span>&nbsp;</span></a>
					   		<a href="javascript:void(0);" class="clear_condition" id="clear_condition_a" style="display: none" >清除筛选条件</a>
					  	</dd>
					<div class="clear"></div>
				</dl>
			</div>
			<!-- 过滤条件的隐藏字段 -->
	   		<input type="hidden" id="month_fee_hidden" >
			<!-- 套餐展示区 开始-->
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
			<!-- 套餐展示区 结束-->
			<!-- 活动展示区 开始-->
			<h2 class="title margin_top" id="margin_top" style="display:none">活动信息</h2>
			<!--id="box" 整个活动 -->	
	  		<div class="box" id="box"  style="display:none">
	  			<!--  参加合约计划 或者 自备机入网-->
	  			<div id="select_activity_plan" style="display:none">
		  			<ul class="plan" id="activity_plan">
					</ul>
	  			</div>
				<div id="show_activity"  style="display:none">
					<!--id="activity_information" 活动类型，担保类型，可选活动 -->
					<div id="activity_information">
					<h3>活动选择：</h3>
						<table width="978" border="0" align="center" cellpadding="0" cellspacing="0">
				  			<tr>
								<td width="66" height="32" align="right">活动类型：</td>
								<td width="260">
									<select name="activity_type" id="activity_type" >
	                				</select>
	                			</td>
				    			<td width="66" align="right" >
				    				<div id="guarantee_type_lbl" style="display:none;">担保类型：</div>
				    			</td>
				    			<td width="260">
				    				<select name="guarantee_type" id="guarantee_type" style="display:none;">
	                				</select>
	                			</td>
	                			<td width="66" align="right" >
				    				<div id="terminal_style_name" style="display:none;">终端类型：</div>
				    			</td>
				    			<td width="260">
	                				<t:select id="terminal_style" codeType="terminal_style"></t:select>
	                			</td><!--
				    			<td width="66" align="right">
				  					<div id="activity_name_lbl" style="display:none;">可选活动：</div>  
				    			</td>
				    			<td width="260">
					    			<select name="activity_name" id="activity_name" style="display:none;">
		                			</select>
	                			</td>-->
				  			</tr>
				  			<tr>
				  				<td width="66" align="right" id="terminal_brand_td">
				    				<div id="terminal_brand" style="display:none;">品牌：</div>
				    			</td>
				    			<td width="260" id="terminal_brand_name_td">
				    				<select name="terminal_brand_name" id="terminal_brand_name" style="display:none;">
	                				</select>
	                			</td>
				  				<td width="66" align="right" id="terminal_model_td">
				    				<div id="terminal_model" style="display:none;">型号：</div>
				    			</td>
				    			<td width="260" id="terminal_model_name_td">
				    				<select name="terminal_model_name" id="terminal_model_name" style="display:none;">
	                				</select>
	                			</td>
				  				<td width="66" align="right">
				  					<div id="activity_name_lbl" style="display:none;">可选活动：</div>  
				    			</td>
				    			<td width="260">
					    			<select name="activity_name" id="activity_name" style="display:none;">
		                			</select>
	                			</td>
				  			</tr>
						</table>
					</div>
					<!-- id="terminal_info" 终端录入 -->
					<div id="terminal_info" style="display:none;">
						<div class="line"></div>
						<h3>终端信息：</h3>
						<table width="978" border="0" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td width="66" height="32" align="right">
									<span class="red" >*</span>终端串号：
								</td>
								<td>
									<!-- 终端信息 -->
									<input type="text" name="textfield" class="id" id="terminal_id" />
									<!--<a href="javascript:void(0);" id="btn_read_terminal_id" class="id_read">校验</a>-->
									<!-- 显示是否通过终端校验 -->
									<span class="red" id="terminal_checked"></span>
								</td>
					      	</tr>
						</table>
					</div>
					<!-- id="activity_desc" 活动描述详细信息 -->
					<div id="activity_desc" style="display:none;">
						<!-- 显示 活动预存费用，协议期内次月开始每月返还,赠送,在网时间不少于月等。 -->
						<h3>活动描述：<span class="red" id="activity_actDescSpanFee"></span></h3>
						<!-- 显示活动信息 -->
						<div id="activity_actDescSpan" class="description">
						</div>
						<div class="description">
							<br/><a href="javascript:void(0);" class="id_confirm id_confirm_margin"  id="btn_selected_activity_item">确定选择</a>
						</div>
					</div>
					<!-- id="activity_selected_item" 已经选中得活动信息 -->
					<div id="activity_selected_item" style="display:none;">
						<div class="line"></div>
						<table width="968" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#d1d7df" class="shop">
	              			<tr>
	                			<th width="235" height="32" bgcolor="#eeeeee">已选择活动</th>
	                			<th width="307" bgcolor="#eeeeee">活动类型</th>
	                			<th width="274" bgcolor="#eeeeee">担保类型</th>
	                			<th width="148" bgcolor="#eeeeee">操作</th>
	              			</tr>
	              			<tr>
	                			<td height="32" colspan="5" bgcolor="#f9f9f9">
	                				<table width="968" border="0" cellspacing="0" cellpadding="0">
	                    				<tr>
	                      					<td width="236" height="32"><div id="txt_activity_name_val"></div></td>
	                      					<td width="309"><div id="txt_activity_type_val"></div></td>
	                      					<td width="275"><div id="txt_guarantee_type_val"></div></td>
	                      					<td width="148"><a href="javascript:void(0);" id="btn_delete_activity_item">删除</a></td>
	                      					<input id="activity_id_val" type="hidden">				<!--活动ID-->
	                      					<input id="activity_name_val" type="hidden">			<!--活动名称  -->
	                      					<input id="activity_type_val" type="hidden">			<!--活动类型  -->
	                      					<input id="activity_type_name_val" type="hidden">			<!--活动类型名称  -->
	                      					<input id="activity_guarantee_type_val" type="hidden">	<!--担保类型  -->
	                      					<input id="activity_guarantee_name_val" type="hidden">	<!--担保类型名称  -->
	                      					<input id="activity_desc_val" type="hidden">			<!--活动描述  -->
	                      					<input id="activity_fee_val" type="hidden">				<!--活动预存费用  -->
	                      					<input id="activity_eff_date_val" type="hidden">		<!--活动生效时间  -->
	                      					<input id="activity_exp_date_val" type="hidden">		<!--活动失效时间  -->
	                      					<input id="activity_eff_flag_val" type="hidden">		<!--活动生效标志  -->
	                    				</tr>
	                				</table>
	                			</td>
	              			</tr>
	            		</table>
					</div><!-- id="activity_selected_item" 已经选中得活动信息 end-->
				</div><!-- id="show_activity" end  -->
			</div><!--id="box" 整个活动 end-->
			<!-- 活动展示区 结束-->
			<!-- 表单 -->
			<form action="saleOpen" id="saleOpenForm" method="post">
				<!-- 前一页传过来的参数 -->
				<input type="hidden" id="acc_nbr_result" 		name="acc_nbr" 		value="${codeOfrNumberVo.acc_nbr}" >	<!--设备号码  -->
				<input type="hidden" id="acc_nbr_fee_result" 	name="acc_nbr_fee"  value="${codeOfrNumberVo.fee}" >		<!--号码预存话费  -->
				<input type="hidden" 							name="good_type" 	value="${codeOfrNumberVo.good_type }" >	<!--靓号类型 -->
				<input type="hidden" 							name="tele_type" 	value="${codeOfrNumberVo.tele_type }" >	<!--电信类型  -->
				<input type="hidden" 							name="prepay_flag" 	value="${codeOfrNumberVo.pay_flag }" >	<!--付费标志 -->
				<!-- 套餐字段 -->
				<input type="hidden" id="ofr_id_result" 		name="ofr_id" >			<!--套餐ID  -->
				<input type="hidden" id="ofr_name_result" 		name="product_name" >	<!--套餐名称  -->
				<input type="hidden" id="ofr_desc_result" 		name="product_desc" >	<!--套餐描述  -->
				<input type="hidden" id="market_price_result" 	name="market_price" >	<!--套餐价格  -->
				<input type="hidden" id="ofr_status_result" 	name="ofr_status" >		<!--套餐状态  -->
				<input type="hidden" id="eff_flag_result" 		name="eff_flag" >		<!--生效标志  -->
				<input type="hidden" id="eff_date_result" 		name="eff_date" >		<!--生效时间  -->
				<input type="hidden" id="exp_date_result" 		name="exp_date" >		<!--失效时间  -->
				<input type="hidden" id="oper_date_result" 		name="oper_date" >		<!--操作时间  -->
				<input type="hidden" id="exclude_code_result" 	name="exclude_code" >	<!--  -->
				<input type="hidden" id="rule_id_result" 		name="rule_id" >		<!--销售品产品选择关系规则  -->
				<input type="hidden" id="ofr_type_result" 		name="ofr_type" >		<!--电信类型编码  -->
				<input type="hidden" id="ofr_sub_type_result" 	name="ofr_sub_type_3g" ><!--套餐类型  -->
				<input type="hidden" id="bss_product_result" 	name="bss_product" >	<!--bss套餐编码 -->
				<input type="hidden" id="brand_code_result" 	name="brand_code" >		<!--品牌编码-->
				<input type="hidden" id="zb_product_result" 	name="product_id">		<!--ess套餐编码 -->
				<!-- 活动字段 -->
				<input type="hidden" id="activity_id_result" 		name="activity_id">				<!--活动ID-->
	            <input type="hidden" id="activity_name_result" 		name="activity_name" >			<!--活动名称  -->
	            <input type="hidden" id="activity_type_result"  	name="activity_type" >			<!--活动类型  -->
	            <input type="hidden" id="activity_type_name_result"  	name="activity_type_name" >			<!--活动类型  -->
	            <input type="hidden" id="activity_guarantee_type_result"  name="guarantee_type" >	<!--担保类型  -->
	            <input type="hidden" id="activity_guarantee_type_name_result"  name="guarantee_type_name" >	<!--担保名称  -->
	            <input type="hidden" id="activity_desc_result"  	name="activity_desc" >			<!--活动描述  -->
	            <input type="hidden" id="activity_fee_result"  		name="fee" >					<!--活动预存费用  -->
	            <input type="hidden" id="activity_eff_date_result"  name="activity_eff_date" >		<!--活动生效时间  -->
	            <input type="hidden" id="activity_exp_date_result"  name="activity_exp_date" >		<!--活动失效时间  -->
	            <input type="hidden" id="activity_eff_flag_result"  name="activity_eff_flag" >		<!--活动生效标志  -->
	            <input type="hidden" id="terminal_brand_result" 	name="terminal_brand"  >		<!-- 终端品牌 -->
				<input type="hidden" id="terminal_model_result" 	name="terminal_type"  >			<!-- 终端机型 -->
				<input type="hidden" id="terminal_fee_result" 		name="terminal_fee"  >			<!--终端价格  -->
				<input type="hidden" id="terminal_id_result" 		name="terminal_id"  >			<!-- 终端串号 -->
				<input type="hidden" id="terminal_style_result" 		name="terminal_style_result"  >			<!-- 终端类型 -->
			</form>
			<div class="open_submit">
				<a href="javascript:void(0);" class="ok" id="btn_submit">确 定</a>
				<a href="javascript:void(0);" class="cancel" id="btn_cancel">取 消</a>
			</div>
		</div>
	</div>
	<script>
  		$( document ).tooltip();
  	</script>
</body>
</html>