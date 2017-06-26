<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>销售-待处理的订单</title>
<link href="<%=fullPath%>css/orderProcessingPay.css" rel="stylesheet" type="text/css" />
<script src="<%=fullPath%>js/orderProcessingPay.js" type="text/javascript"/>
<script type="text/javascript">
	$(function() {
		$(".box_list tr td table tr:odd").addClass("odd");
		$(".box_list tr td table tr:even").addClass("even");
		$(".sale_mail").mouseover(function() {
			$(".notice_show").show();
		}).mouseout(function() {
			$(".notice_show").hide();
		});
	});
</script>
</head>
<body>
<form id="orderProPaySub" action="<%=fullPath %>authority/order/orderProPaySub" method="post">
<div class="crumbs">当前位置: <a href="###">首页</a> > <a href="###">待收费</a></div>
	<div class="sale_content">
		<div class="content">
			<table width="1000" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="175" valign="top" bgcolor="#EEEEEE" class="c_left">
						<div class="wrap">
							<ul>
								<!-- <li class="first"><a href="#" class="hover">待收费订单</a></li> -->
							<!-- 	<li class="second"><a href="#">已处理订单</a></li> -->
								<%-- <li class="third"><a href="<%=fullPath %>authority/order/orderProsessing">订单处理</a></li> --%>
								<li class = "first"  ><a href="##" class = "hover">订单处理</a></li>
								<li class="third"><a href="#">我发起的订单</a></li>
							</ul>

						</div>

					</td>
					<td valign="top" class="c_right">
						<div class="wrap_right">
							<h2 class="title margin_top">基本信息</h2>
							<div class="right_box">
								<table width="778" border="0" align="center" cellpadding="0"
									cellspacing="0">
									<tr>
										<td width="66" height="30" align="right">订 单 号：</td>
										<td width="154" id="order_id">${infoOrderBaseMessVo.order_id}</td>
										<td width="66" align="right" class="line_v">客户名称：</td>
										<td width="104" id="customer_name_baseInfo">${infoOrderBaseMessVo.customer_name}</td>
										<td width="92" align="right" class="line_v">证件类型：</td>
										<td width="92" id="id_type_baseInfo">${infoOrderBaseMessVo.id_type_name}</td>
										<td width="66" align="right" class="line_v">证件号码：</td>
										<td width="164" id="id_number_baseInfo">${infoOrderBaseMessVo.id_number}</td>
										<input type="hidden" id="id_number_baseInfo_hidden" value="${infoOrderBaseMessVo.id_number}"/>
									</tr>
									<tr>
										<td align="right" >本 地 网：</td>
										<td height="30"><t:lable codeType="local_net" codeId="${infoOrderBaseMessVo.local_net}"></t:lable></td>
										<td height="30" align="right" class="line_v">设备号码：</td>
										<td height="30">${infoOrderBaseMessVo.acc_nbr}</td>										
										<td align="right" class="line_v">电信类别：</td>
										<td id="tele_type">${infoOrderBaseMessVo.tele_type}</td>
										<td height="30" align="right"  class="line_v">套餐名称：</td>
									   <td height="30">${infoOrderBaseMessVo.product_name}</td>
									</tr>
									<tr>
										<td align="right" class="right">下单时间：</td>
										<td>${infoOrderBaseMessVo.creat_date}</td>
										<td height="30" align="right"  class="line_v">终端机型：</td>
										<td height="30" id="terminal_type">${infoOrderBaseMessVo.terminal_type}</td>
										<td align="right" class="line_v">是否实名认证：</td>
										<td id="real_name">${infoOrderBaseMessVo.is_attestation}</td>
										<td align="right" class="line_v" id="card_type">卡类型：</td>
										<td id="baseInfo_card_kind">${infoOrderBaseMessVo.base_card_kind}</td>
									</tr>
									<tr id="info_iccid">
										<td align="right" class="line_v">ICCID：</td>
										<td id="baseInfo_iccid">${infoOrderBaseMessVo.iccid}</td>
									</tr>
									<tr style="display:none">
										<td><input name="tele_type" value="${infoOrderBaseMessVo.tele_type}"/>  </td>
										<td><input name="customer_id" id="customer_id" value=""/>  </td>
									</tr>
								</table>
							</div>
							<h2 class="title margin_top">费用信息</h2>
							<div class="right_box">
								<table width="788" border="0" align="center" cellpadding="0"
									cellspacing="0">
									<tr>
										<td width="94" height="30" align="right">支付方式：</td>
										<td width="150"><t:select id="pay_type" codeType="pay_type"></t:select></td>
										<input type="hidden" name="pay_type" id="pay_type_hidden"/>
										<td height="30" align="right" style="display:none" class="posliushui">POS流水：</td>
										<td colspan="5" style="display:none" class="posliushui"><input id="reference_number" type="text" name="textfield3" class="id" /></td>
									<!-- 	<td width="90" height="30" align="right"><span class="star">*</span>发票编码：</td>
										<td width="220"><input type="text" id="invoice_code" name="invoice_code" class="id"/></td> -->
										<%-- <td width="60" height="30" align="right" class="none" id="card_kind_name"><span class="star">*</span> 卡类型：</td>
										<td id="card_kind_select" class="none"><t:select id="card_kind" codeType="card_kind"></t:select></td> --%>
											<!--  <input type="hidden" name="card_kind" id="card_kind_hidden">  -->
									</tr>
								<!-- 	<tr id="reference_number_tr" style="display:none">
										
									</tr> -->
									
								</table>
								<div class="box">
									<table width="760" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#d1d7df" class="box_list">
										<tr class="not_bg">
											<th width="178" height="32" bgcolor="#eeeeee">费用名称</th>
											<th width="135" bgcolor="#eeeeee">费用</th>
											<th width="135" bgcolor="#eeeeee">实收金额</th>
											<th width="135" bgcolor="#eeeeee">减免金额</th>
											<th width="174" bgcolor="#eeeeee">减免原因</th>
										</tr>
										<tr>
											 <td height="32" colspan="5" bgcolor="#FFFFFF">
											 	
												     <table width="758" border="0" cellspacing="0" cellpadding="0" id="table_feeList">
															<c:forEach var="orderFee" items="${feeList}" varStatus="i">
			        											<tr>
																	<td width="178" height="32">${orderFee.fee_name }</td>
																	<td id="old_fee_${i.index }" width="136">${orderFee.orig_fee }</td>
																	<td id="new_fee_${i.index }" width="136">${orderFee.orig_fee }</td>
																	<td width="136"><input name="discountList[${i.index }].discount_fee" key="${i.index }" id="discount_fee_${i.index }" onblur="countFee(this)" onfocus="cleanDefaultValue(this)"  type="text" value="0"/></td>
			                                                        <td  width="172"><input name="discountList[${i.index }].discount_reason" id="discount_reason_${i.index }" type="text" value=""/></td>
															    </tr>
															    <tr style="display:none">
			                                                        <td><input name="discountList[${i.index }].fee_detail_id" width="172"value="${orderFee.fee_detail_id}"/></td>
			                                                        <td><input name="discountList[${i.index }].order_id" width="172"value="${infoOrderBaseMessVo.order_id}"/></td>
			                                                        <td><input name="discountList[${i.index }].discount_type" width="172" value="400"/></td>
			                                                        <td><input name="discountList[${i.index }].fee_id" width="172" value="${orderFee.fee_id }"/></td>
			                                                        <td><input name="discountList[${i.index }].fee_id_type" width="172" value="${orderFee.fee_id_type }"/></td>
			                                                        <td><input name="discountList[${i.index }].fee_name" value="${orderFee.fee_name }" /></td>
			                                                        <td><input name="discountList[${i.index }].orig_fee" value="${orderFee.orig_fee }"/></td>
			                                                        <td><input id="new_fee_none_${i.index }" name="discountList[${i.index }].real_fee" value="${orderFee.orig_fee }"/></td>
																	
															    </tr>
		        											</c:forEach>
													</table>
													<table id="order_info" style="display:none">
														<tr>
													    	<td><input name="payed_fee" id="discount_totalCosts_input" value='${feeVo.fee_num}'/></td>
													    	<td><input name="total_fee" id="total_fee" value="${feeVo.fee_num}"></input></td>
													    	<td><input name="discount_fee" id="discount_fee" value="0"></input></td>
													    	<td><input name="order_id"  value="${infoOrderBaseMessVo.order_id}"></input></td>
													    	<td><input name="order_source" id="order_source" value="${infoOrderBaseMessVo.order_source}"></input></td>
													    	<td><input name="invoice_flag"  value="0"></input></td>
	       												</tr>
													</table>
											 </td>
										</tr>
									</table>
									<div id="discount_totalCosts" class="total">总计：${feeVo.fee_num}</div>
								</div>

								<div class="open_submit">
									<input id="check_cust_flag" type="text" name="check_cust_flag" value="false" style="display:none"/>
									<input id="info_confirm_flag" type="text" name="info_confirm_flag" value="false" style="display:none"/>
									<a href="javascript:void(0)" onclick="javascript:orderSub();" class="ok"  id = "queding">确 定</a>
									<%-- <a href="javascript:void(0)" onclick="javascript:infoPrint(${infoOrderBaseMessVo.order_id});" class="free"  id="miantiandan">免填单打印</a> --%>
									<a href="javascript:void(0)" class="free"  id="miantiandan">免填单打印</a>
									<a href="javascript:void(0)" class="cancel" id="goBack">取 消</a>
								</div>

							</div>
						</div>
					</td>
				</tr>
			</table>

		</div>
	</div>
</form>

</body>
</html>
