<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
<%@ taglib prefix="t" uri="http://tydic.com/jsp/core" %>
<%@include file="../common/common.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>销售 - 开户</title>
<script type="text/javascript" src="<%=fullPath%>js/saleOpen.js"></script>
<link href="<%=fullPath%>css/sale_open.css" rel="stylesheet" type="text/css" />
</head>
<body>
<input type="hidden"  id="devCode"  value="${devCode}"/>

<div class="crumbs">当前位置: 销售 > 选号 > 选套餐及活动 > 其他资料</div>
<div class="sale_content">
	<div class="content">
	<form action="addOrderSubmit" method="post" id = "form_check">
		<h2 class="title">商品信息</h2>
		<table width="1000" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#d1d7df" class="shop">
		  <tr>
			<th width="207" height="32" bgcolor="#eeeeee">设备号码</th>
			<th width="270" bgcolor="#eeeeee">销售品</th>
			<th width="156" bgcolor="#eeeeee">电信类型</th>
			<th width="210" bgcolor="#eeeeee">终端机型</th>
			<th width="157" bgcolor="#eeeeee">本地网</th>
		  </tr>
		  
		  <tr>
			<td height="32" colspan="5" bgcolor="#f9f9f9"><table width="998" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="208" height="32">${infoOrderPackageVo.acc_nbr}<input type = "hidden" value = "${infoOrderPackageVo.acc_nbr}" id="acc_nbr" name="acc_nbr"></td>
				<td width="271">${infoOrderPackageVo.product_name}<input type = "hidden" value = "${infoOrderPackageVo.product_name}" id="product_name" name="product_name"></td>
				<td width="157">${infoOrderPackageVo.tele_type}<input type = "hidden" value = "${infoOrderPackageVo.tele_type}" id="tele_type" name="tele_type"></td>
				<td width="211">${infoOrderPackageVo.terminal_type}<input type = "hidden" value = "${infoOrderPackageVo.terminal_type}" id="terminal_type" name="terminal_type"></td>
				<td><t:lable codeType="local_net" codeId="${infoOrderPackageVo.local_net}"></t:lable><input type = "hidden" value = "${infoOrderPackageVo.local_net}" id="local_net" name="local_net"></td>
			  </tr>
			</table></td>
		  </tr>
		</table>
		<h2 class="title margin_top">客户信息</h2>
		<div class="box">
		
			<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" class="custom">
			  <tr>
				<td width="105" height="32" align="right"><span class="star">*</span> 证件类型：</td>
				<td colspan="3" width="895"><t:select id="id_type" codeType="id_type" name="id_type"></t:select></td>
			  </tr>
			  <tr>
				<td height="32" width="105" align="right"><span class="star">*</span> 证件号码：</td>
				<td width="255"><input id="id_number" type="text" name="id_number"  class="id"/><a href="#" class="id_confirm" id="machine_confirm">国政通验证</a></td>
				<td width="105" align="right"><span class="star">*</span> 证件到期时间：</td>
				<td width="535">
				<div class="date_relative">
					<t:date clazz="txt_mail txt_date" id="auth_end_date" name="auth_end_date" value=""></t:date>
				</div></td>
			  </tr>
			  <tr>
			    <td align="right"><span class="star">*</span>证件地址：</td>
			    <td colspan="3"><input id="auth_adress" type="text" name="auth_adress" class="id_addr" style="width: 880px;" /></td>
		      </tr>
			</table>

			<div class="line"></div>
			
			<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" class="custom">
			  <tr>
				<td width="105" height="32" align="right"><span class="star">*</span> 客户名称：</td>
				<td width="255"><input id="customer_name" type="text" name="customer_name" class="txt_input" /></td>
				<td width="105" align="right"><span class="star">*</span>客户性别：</td>
				<td width="205"><t:select id="customer_sex" codeType="cust_sex" name="customer_sex" disabled="true"></t:select></td>
				<td width="112" align="right">出生日期：</td>
				<td width="218"><div class="dateRelative">
					<t:date clazz="txt_mail txt_date" id="born_date" name="born_date" value=""></t:date>
				</div></td>
			  </tr>
			  <tr>
				<td width="105" height="32" align="right"><span class="star">*</span> 联系人姓名：</td>
				<td width="255"><input type="text" id="contactPerson"name="contactPerson" class="txt_input" /></td>
				<td width="105" align="right"><span class="star">*</span> 联系人电话：</td>
				<td><input type="text" name="contactPhone" id="contactPhone" class="txt_mail" /></td>
				<td align="right"><span class="star">*</span> 联系人地址：</td>
				<td><input type="text" name="contactAddress" id="contactAddress"class="txt_mail" /></td>
			  </tr>
			  <tr>
				<td align="right" width="105">备注：</td>
				<td colspan="5" width="895"><input id="remark_desc" type="text" name="remark_desc" class="txt_input" style="width:880px;" /></td>
			  </tr>
		  </table>
		</div>
		
		<!-- 发展人信息相关 -->
		
		<h2 class="title margin_top">发展人信息</h2>
	  <div class="box">
	    <table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" class="custom">
				  <tr>
					<td width="105" height="32" align="right"><span class="star">*</span>发展人名称：</td>
					<td width="255"><input type="text" id="devolop_name" name="devolop_name" class="txt_mail" readonly="readonly" /><a href="#" class="order_search" id="to_developer_qry"></a></td>
					<td width="105" align="right"><span class="star">*</span> 发展人编码：</td>
					<td width="205"><input type="text" id="devolop_post" name="devolop_post" class="txt_mail" readonly="readonly"/></td>
					<td width="112" align="right"><span class="star ">*</span>发展人手机号：</td>
					<td><input type="text" id="devolop_phone" name="devolop_phone" class="txt_mail" readonly="readonly"/></td>
				  </tr>
				  <tr>
					<td height="32" align="right"><span class="star">*</span>发展渠道编码：</td>
					<td><input type="text" id="devolop_channel_id" name="devolop_channel_id" class="txt_input" readonly="readonly"/></td>
					<td align="right"><span class="star">*</span> 发展渠道名称：</td>
					<td><input type="text" id="devolop_channel_name" name="devolop_channel_name" class="txt_mail" readonly="readonly"/></td>
					<td align="right"><span class="star "></td>
					<td></td>
				  </tr>
	    </table>
      </div>
      
		<!-- 发展人信息相关 -->
		
		
		
		<h2 class="title margin_top">经办人信息</h2>
		<div class="box">
			<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" class="custom">
				  <tr>
					<td width="105" height="32" align="right">经办人姓名：</td>
					<td width="255"><input id="handler_name" type="text" name="handler_name" class="txt_input" /></td>
					<td width="105" align="right">证件类型：</td>
					<td width="205"><t:select id="handler_id_type" codeType="id_type" name="handler_id_type"></t:select></td>
					<td width="112" align="right">证件号码：</td>
					<td><input id="handler_id_number" type="text" name="handler_id_number" class="txt_mail" /></td>
				  </tr>
				  <tr>
					<td align="right">联系地址：</td>
					<td><input id="handler_contact_address" type="text" name="handler_contact_address" class="txt_input" /></td>
					<td align="right">备注：</td>
					<td><input id="handler_remark_desc" type="text" name="handler_remark_desc" class="txt_mail" /></td>
				  </tr>
		  </table>
		</div>		
		<h2 class="title margin_top">邮寄信息</h2>
		<div class="box">
			<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" class="custom">
				  <tr>
					<td width="105" height="32" align="right">账单寄送：</td>
					<td width="255"><t:select id="bill_send" codeType="bill_send" name="bill_send"></t:select></td>
					<td width="105" align="right" id="logistics_type_text">寄送方式：</td>
					<td width="205" ><t:select id="logistics_type" codeType="logistics_type" name="logistics_type"></t:select></td>
					<%-- <td width="205"><t:cascadeSelect id="logistics_type" name="logistics_type" codeType="logistics_type" rootValue="0"></t:cascadeSelect></td> --%>
					<td width="112" align="right" id="send_content_text">寄送内容：</td>
					<td ><t:select id="send_content" codeType="send_content" name="send_content"></t:select></td>
				    <%-- <td ><t:cascadeSelect id="send_content" name="send_content" codeType="send_content" rootValue="0>100"</t:cascadeSelect></td> --%>
				  </tr>
		  </table>
		</div>
		<h2 class="title margin_top">其他</h2>
		<div class="box">
			<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" class="custom">
				  <tr>
					<td align="right" width="105"><span class="star ">*</span>初始信用度：</td>
					<td width="895"><input id="credit_first" type="text" name="credit_first" class="txt_input" value="0"/></td>
				  </tr>
		  	</table>
		  </div>
		<h2 class="title margin_top" id = "first_month_fee_title">入网当月资费</h2>
		<div class="box" id = "first_month_fee">
			<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" class="custom">
				  <tr>
					<td width="40" height="32" align="right">&nbsp;</td>
					<td width="131" align="left"><label><input name="first_month_fee" type="radio" value="01" checked="checked" />
						套餐包外资费</label></td>
					<td width="114" align="left"><label><input type="radio" name="first_month_fee" value="02" />
				   		 全月套餐</label></td>
					<td align="left"><label><input type="radio" name="first_month_fee" value="03" />
				   		 套餐减半</label></td>
				  </tr>
		  </table>
		</div>
		<!--  
		<h2 class="title margin_top" id="card_kind_title">卡类型</h2>
		<div class="box" id="card_kind">
			<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" class="custom">
				  <tr>
					<td width="40" height="32" align="right">&nbsp;</td>
					<td align="left"><label class="p_right"><input name="radio_card" id="radio_card_1" type="radio" value="01" />成卡</label><label class="p_right"><input type="radio" id="radio_card_0"name="radio_card" value="02" />白卡</label><label class="warning">提示：一旦确认30分钟后才可以更改，请慎重操作！</label></td>
				  </tr>
		  </table>
		</div>
		-->
		
		<!-- 隐藏域start -->
		  <input type = "hidden" value = "${infoOrderPackageVo.acc_nbr_fee}" id="acc_nbr_fee" name="acc_nbr_fee">
		  <input type = "hidden" value = "${infoOrderPackageVo.product_id}" id="product_id" name="product_id">
		  <input type = "hidden" value = "${infoOrderPackageVo.market_price}" id="market_price" name="market_price">
		  <input type = "hidden" value = "${infoOrderPackageVo.fee}" id="fee" name="fee">
		  <input type = "hidden" value = "${infoOrderPackageVo.activity_name}" id="activity_name" name="activity_name">
		  <input type = "hidden" value = "${infoOrderPackageVo.activity_id}" id="activity_id" name="activity_id">
		  <input type = "hidden" value = "${infoOrderPackageVo.essKey}" id="essKey" name="essKey">
		  <input type = "hidden" value = "${infoOrderPackageVo.ofr_sub_type_3g}" id="ofr_sub_type_3g" name="ofr_sub_type_3g">
		  <input type = "hidden" id="customer_id" type="text" name="customer_id" class="txt_input" />
		  <input type = "hidden" value = "${infoOrderPackageVo.product_desc}" id="product_desc" name="product_desc">
		  <input type = "hidden" value = "${infoOrderPackageVo.product_chose_id}" id="product_chose_id" name="product_chose_id">
		  <input type = "hidden" value = "${infoOrderPackageVo.product_type}" id="product_type" name="product_type">
		  <input type = "hidden" value = "${infoOrderPackageVo.eff_date}" id="eff_date" name="eff_date">
		  <input type = "hidden" value = "${infoOrderPackageVo.exp_date}" id="exp_date" name="exp_date">
		  <input type = "hidden" value = "${infoOrderPackageVo.brand_code}" id="brand_code" name="brand_code">
		  <input type = "hidden" value = "${infoOrderPackageVo.prepay_flag}" id="prepay_flag" name="prepay_flag">
		  <input type = "hidden" value = "${infoOrderPackageVo.rank_money}" id="rank_money" name="rank_money">
		  <input type = "hidden" value = "${infoOrderPackageVo.default_value}" id="default_value" name="default_value">
		  <input type = "hidden" value = "${infoOrderPackageVo.terminal_brand}" id="terminal_brand" name="terminal_brand">
		  <input type = "hidden" value = "${infoOrderPackageVo.terminal_fee}" id="terminal_fee" name="terminal_fee">
		  <input type = "hidden" value = "${infoOrderPackageVo.terminal_id}" id="terminal_id" name="terminal_id">
		  <input type = "hidden" value = "${infoOrderPackageVo.activity_type}" id="activity_type" name="activity_type">
		  <input type = "hidden" value = "${infoOrderPackageVo.activity_desc}" id="activity_desc" name="activity_desc">
		  <input type = "hidden" value = "${infoOrderPackageVo.join_activity_item}" id="join_activity_item" name="join_activity_item">
		  <input type = "hidden" value = "${infoOrderPackageVo.guarantee_type}" id="guarantee_type" name="guarantee_type">
		  <input type = "hidden" value = "${infoOrderPackageVo.self_phone}" id="self_phone" name="self_phone">
		  <input type = "hidden" value = "${infoOrderPackageVo.join_activity_plan}" id="join_activity_plan" name="join_activity_plan">
		  <input type = "hidden" value = "${infoOrderPackageVo.terminal_seleted}" id="terminal_seleted" name="terminal_seleted">
		  <input type = "hidden" value = "${infoOrderPackageVo.good_type}" id="good_type" name="good_type">
		  <input type = "hidden" value = "" id="input_customer_sex" name="customer_sex">
		  <input type = "hidden" value = "${infoOrderPackageVo.activity_type_name}" id="activity_type_name" name="activity_type_name">
		  <input type = "hidden" value = "${infoOrderPackageVo.guarantee_type_name}" id="guarantee_type_name" name="guarantee_type_name">
		  <input type = "hidden" value = "${infoOrderPackageVo.terminal_style_result}" id="terminal_style_result" name="terminal_style_result">
		   <!-- 发展人信息 
			<input type = "hidden"  id="devolop_name" type="text" name="devolop_name" class="txt_input" value="${reqVo.devolop_name}"/>
			<input type = "hidden"  id="devolop_post" type="text" name="devolop_post" class="txt_mail" value="${reqVo.devolop_post}"/>
			<input type = "hidden"  id="devolop_phone" type="text" name="devolop_phone" class="txt_mail" value="${reqVo.devolop_phone}"/>
			<input type = "hidden"  id="devolop_channel_id" type="text" name="devolop_channel_id" class="txt_input" value="${reqVo.devolop_channel_id}"/>
			<input type = "hidden"  id="devolop_channel_name" type="text" name="devolop_channel_name" class="txt_mail" value="${reqVo.devolop_channel_name}"/>
		  -->
		  <input type = "hidden" value = "0" id="okBtnEnable" name="okBtnEnable">
		  <input type = "hidden" value = "" id="okBtnEnableInfo" name="okBtnEnableInfo">
		  <input type = "hidden" value = "" name="born_date" id="born_date_hidden">
		  <input type = "hidden" value = "" name="auth_end_date" id="auth_end_date_hidden">
		  <!-- 隐藏域end -->
		<div class="open_submit"><a href="###" class="ok" id="order_submit">确 定</a><a href="###" class="cancel" id="order_cancel">取 消</a></div>
	  </form>
	</div>
</div>
</body>
