<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
	<head>
		<link href="<%=fullPath %>/css/share.css" rel="stylesheet" type="text/css" />
		<link href="<%=fullPath %>/css/sale_selected_code.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=fullPath %>/js/sale_selected_code.js"></script>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>销售 - 选套餐及活动</title>
	</head>
  	<body>
  	<input id="full_path_value" name="full_path_value" value="<%=fullPath%>" type="hidden">
  	<!-- 选择号码页面中获取的数据 -->
  	<input type="hidden" id="acc_nbr" value="${codeOfrNumberVo.acc_nbr}"><!-- 设备号码 -->
	<input type="hidden" id="tele_type" value="${codeOfrNumberVo.tele_type }"><!-- 电信类型 -->
	<input type="hidden" id="ofr_sub_type_3g" value="${codeOfrNumberVo.ofr_sub_type_3g}"><!--3G套餐制定类型  -->
	<input type="hidden" id="good_type" value="${codeOfrNumberVo.good_type }"<!-- 靓号类型 -->
  	<div class="crumbs">当前位置: 
  		<a href="javascript:void(0);">首页</a>> 
  		<a href="javascript:void(0);">选号</a>> 
  		<a href="javascript:void(0);">选套餐及活动</a>
  	</div>
	<div class="sale_content">
		<div class="right">
		<div class="selected_code">
			<a href="javascript:void(0);" id="back_selected_btn" class="back_selected"></a>已选择号码: ${codeOfrNumberVo.acc_nbr}<span>含${codeOfrNumberVo.fee}元预存话费</span>
		</div>
		<div class="select_code">
			<div class="search_blur">过滤条件</div>		
			<dl class="define">
				<dt>模糊查询：</dt>
				<dd>
					<dl class="search_code">
						<dt><input type="text" class="text" id="txt_search" value="套餐名称" /></dt>
						<dd><input type="text" value="搜索" class="btn" id="search_btn"/></dd>
					</dl></dd>
				<div class="clear"></div>
			</dl>
			<dl id="ofr_type_dl">
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
		<div class="selected" id="selected_conditions">
			<dl>
				<dt>您已经选择：</dt>
					<dd>
						<a href="javascript:void(0);" class="selected_ofr_type" id="ofr_type_a" style="display: none">${ofr_type}<span>&nbsp;</span></a>
				   		<a href="javascript:void(0);" class="selected_month_fee"  id="month_fee_a" style="display: none">${month_fee}<span>&nbsp;</span></a>
				   		<a href="javascript:void(0);" class="selected_month_call_duration" id="month_call_duration_a" style="display: none">${month_call_duration}<span>&nbsp;</span></a>
				   		<a href="javascript:void(0);" class="selected_month_net_duration" id="month_net_duration_a" style="display: none">${month_net_duration}<span>&nbsp;</span></a>
				   		<a href="javascript:void(0);" class="clear_condition" id="clear_condition_a" style="display: none" >清除筛选条件</a>
				  	</dd>
				<div class="clear"></div>
			</dl>
		</div>
		<input type="hidden" id="ofr_type_hidden" value="${ofr_type}">
   		<input type="hidden" id="month_fee_hidden" value="${month_fee}">
   		<input type="hidden" id="month_call_duration_hidden" value="${month_call_duration}">
   		<input type="hidden" id="month_net_duration_hidden" value="${month_net_duration}">
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
				<div class="pages" id="layerPaging">
	       		</div>		
			</div>
		</div>
		<div class="clear"></div>
		<c:if test="${codeOfrNumberVo.tele_type==null}">
	  	<h2 class="title margin_top" id="margin_top" style="display:none">活动信息</h2>
	  	<div class="box" id="box"  style="display:none">
	  		<div id="activity_info" style="display:none">
		  		<ul class="plan" id="select_plan" style="display:none;">
					<li><input name="join_plan" id="join_plan" type="radio" value="1" /> 参加合约计划</li>
					<li><input name="join_plan" id="no_join_plan" type="radio" value="0" checked="checked" /> 不参加合约计划</li>
				</ul>
				<ul class="plan"  id="select_self_net" style="display:none;">
					<li><input name="self_join_net" id="self_join_net" type="radio" value="1" checked="checked"/> 非自备机入网</li>
					<li><input name="self_join_net" id="no_self_join_net" type="radio" value="2"  /> 自备机入网</li>
				</ul>
	  		</div>
			<div id="show_plan"  style="display:none">
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
				    <td width="66" align="right">
				  		<div id="can_select_activity_lbl" style="display:none;">可选活动：</div>  
				    </td>
				    <td width="260">
					    <select name="can_select_activity" id="can_select_activity" style="display:none;">
		                </select>
	                </td>
				  </tr>
				</table>
			</div>
			<div id="self_phone_information" style="display:none;"><!-- 自备机信息 -->
				<h3>自备机信息：<span class="red" id="self_phone_terimal_info"></span></h3>
				<table width="978" border="0" align="center" cellpadding="0" cellspacing="0">
				  <tr>
					<td width="80" height="32" align="right">自备机类型：</td>
					<td width="238">
						<select id="self_phone_type">
		                </select>
		            </td>
				    <td width="90" align="right">参加合约计划：</td>
				    <td width="244">
					    <select id="self_phone_join_plan">
					      <option value="1">参加</option>
					      <option value="0">不参加</option>
		                </select></td>
				    <td width="66" align="right" id="lbl_self_phone_activity_name">可选活动：</td>
				    <td width="260">
					    <select id="self_phone_activity_name">
		                </select>
	                </td>
				  </tr>
				  <tr>
			    	<td height="32" align="right"><span class="red" >*</span>终端串号：</td>
			    	<td colspan="5">
				    	<input type="text" id="txt_self_phone_terimal_id" class="id id_width" />
				    	<a href="javascript:void(0);" id="btn_self_phone_terimal" class="id_read" id>校验</a>
				    	<span class="red" id="self_phone_terimal_checked_pass"></span>
			    	</td>
		      	</tr>
			  	<tr>
			    	<td height="32" align="right">&nbsp;</td>
			    	<td colspan="5">&nbsp;</td>
		      	</tr>
				</table>		
			</div>
			<div id="console_info" style="display:none;">
				<div class="line"></div>
				<h3>终端信息：</h3>
				<table width="978" border="0" align="center" cellpadding="0" cellspacing="0">
				  <tr>
					<td width="66" height="32" align="right"><span class="red" >*</span>终端串号：</td>
					<td>
						<input type="text" name="textfield" class="id" id="txt_console_info" />
						<a href="javascript:void(0);" id="btn_read_console_info" class="id_read">校验</a>
						<span class="red" id="console_checked_pass"></span>
					</td>
			      </tr>
				</table>
				<div class="comfirm">
					<table width="968" border="0" align="center" cellpadding="0" cellspacing="0">
					  <tr>
						<td width="66" height="38">终端品牌：</td>
						<td width="200" id="activity_terminalBrandDesc"></td>
						<td width="66">终端型号：</td>
						<td width="200" id="activity_terminalModelDesc"></td>
						<td width="66">终端价格：</td>
						<td width="200" id="activity_terminalFeeDesc"></td>
						<!-- <td><a href="javascript:void(0);" class="id_confirm" id="btn_ok_console">确定选择</a></td> -->
					  </tr>
					</table>
				</div>
			</div>
			<div id="activity_desc" style="display:none;">
				<h3>活动描述：<span class="red" id="activity_actDescSpan_info"></span></h3>
				<div id="activity_actDescSpan" class="description">
				</div>
				<div class="description">
					<br/><a href="javascript:void(0);" class="id_confirm id_confirm_margin"  id="btn_ok_desc">确定选择</a>
				</div>
			</div>
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
	                <td height="32" colspan="5" bgcolor="#f9f9f9"><table width="968" border="0" cellspacing="0" cellpadding="0">
	                    <tr>
	                      <td width="236" height="32"><div id="can_select_activity_val"></div></td>
	                      <td width="309"><div id="activity_type_val"></div></td>
	                      <td width="275"><div id="guarantee_type_val"></div></td>
	                      <td width="148"><a href="javascript:void(0);" id="btn_delete_activity_item">删除</a></td>
	                      
	                      <input id="activity_id_val" type="hidden">	<!--活动ID-->
	                      <input id="activity_name_val" type="hidden">	<!--活动名称  -->
	                      <input id="activity_desc_val" type="hidden">	<!--活动描述  -->
	                      <input id="activity_fee_val" type="hidden">	<!--活动预存费用  -->
	                      
	                      <input id="storedFee_val" type="hidden">		<!--活动预存费用  -->
	                      <input id="thawMon_val" type="hidden">		<!-- 协议期内次月开始每月返还 -->
	                      <input id="presenetedFee_val" type="hidden">	<!-- 赠送 -->
	                      <input id="activityProtper_val" type="hidden"><!-- 在网时间不少于月 -->
	                    </tr>
	                </table></td>
	              </tr>
	            </table>
			</div>
			</div>
			
		</div>
		</c:if>
		<form action="saleOpen" id="saleOpenForm" method="post">
			<!-- 前一页传过来的参数 -->
			<input type="hidden" id="acc_nbr_result" name="acc_nbr" value="${codeOfrNumberVo.acc_nbr}"><!--设备号码  -->
			<input type="hidden" id="acc_nbr_result" name="acc_nbr_fee"  value="${codeOfrNumberVo.fee}"><!--号码预存话费  -->
			<input type="hidden" id="tele_type_result" name="tele_type"  value="${codeOfrNumberVo.tele_type}"><!--电信类型  -->
			<input type="hidden" name="ofr_sub_type_3g" value="${codeOfrNumberVo.ofr_sub_type_3g}" >
			<input type="hidden" name="good_type" value="${codeOfrNumberVo.good_type }"<!-- 靓号类型 -->
			<!-- 套餐字段 -->
			<input type="hidden" id="ofr_id_result" name="product_id" ><!--产品ID  -->
			<input type="hidden" id="ofr_name_result" name="product_name" ><!--产品名称  -->
			<input type="hidden" id="market_price_result" name="market_price" ><!-- 产品价格 -->
			<input type="hidden" id="ofr_desc_result" name="product_desc" ><!-- 产品描述 -->
			<input type="hidden" id="eff_date_result" name="eff_date" ><!-- 起止时间 -->
			<input type="hidden" id="exp_date_result" name="exp_date" ><!-- 终止时间 -->
			<input type="hidden" id="bss_product_result" name="bss_product" ><!-- bss产品ID -->
			<input type="hidden" id="product_chose_id_result" name="product_chose_id" ><!--选中的产品的ID  -->
			<input type="hidden" id="brand_code_result" name="brand_code" ><!--品牌编码  -->
			<input type="hidden" id="prepayFlag_result" name="prepay_flag" ><!-- 付费标识：1-后付费 -->
			<input type="hidden" id="rankMoney_result" name="rank_money" ><!-- 月套餐费用 -->
			<input type="hidden" id="productType_result" name="product_type" ><!-- 猜测为产品类别 -->
			<input type="hidden" id="defaultValue_result" name="default_value" ><!--默认长途漫游级别  -->
			<!-- 活动字段 -->
			<c:if test="${codeOfrNumberVo.tele_type!='2G'&&codeOfrNumberVo.tele_type!='3G'&&codeOfrNumberVo.tele_type!='4G'}">
				<input type="hidden" id="terminal_brand_result" name="terminal_brand"  ><!-- 终端品牌 -->
				<input type="hidden" id="terminal_model_result" name="terminal_type"  ><!-- 终端机型 -->
				<input type="hidden" id="terminal_fee_result" name="terminal_fee"  ><!--终端价格  -->
				<input type="hidden" id="terminal_id_result" name="terminal_id"  ><!-- 终端串号 -->
				<input type="hidden" id="stored_fee_result" name="fee"  ><!-- 活动预存费用 -->
				<input type="hidden" id="activity_id_result" name="activity_id"  ><!-- 活动Id -->
				<input type="hidden" id="activity_name_result" name="activity_name"  ><!-- 活动名称 -->
				<input type="hidden" id="activity_type_result" name="activity_type"  ><!--活动类型  -->
				<input type="hidden" id="activity_desc_result" name="activity_desc"  ><!-- 活动描述 -->
				<input type="hidden" id="join_activity_item_result" name="join_activity_item"  ><!-- 可选活动 -->
				<input type="hidden" id="guarantee_type_result" name="guarantee_type"  ><!-- 担保类型 -->
				<input type="hidden" id="self_phone_result" name="self_phone"  ><!-- 自备机类型 非自备机入网  0   自备机：1-->
				<input type="hidden" id="join_activity_plan_result" name="join_activity_plan"  ><!-- 参加活动计划   1-参加合约计划，0-不参加合约计划-->
				<input type="hidden" id="terminal_seleted" name="terminal_seleted"><!-- 是否有终端       无终端：0，有终端：1 -->
			</c:if>
		</form>
		<div class="open_submit"><a href="javascript:void(0);" class="ok" id="btn_submit">确 定</a><a href="javascript:void(0);" class="cancel" id="btn_cancel">取 消</a></div>
	</div>
</div>
<script>
  $( document ).tooltip();
</script>
  	</body>
</html>
