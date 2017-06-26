<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>销售 - 待处理的订单 - 配卡</title>

<link href="<%=fullPath%>css/order_processing_card.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/orderProcessingCard.js"></script>
<script type="text/javascript" src="<%=fullPath%>js/writeCard.js"></script>
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
<input type="hidden" id = "writeWay" value="${writeWay}" /><!--模拟写卡还是正式写卡方式0代表模拟写卡1代表正式写卡-->
 <input type="hidden"  id="prepayFlag"  value="${prepayFlag}"/>
 <input type="hidden"  id="imsi"  value=""/>
 <input type="hidden" id = "activeId" value="" />
 <input type="hidden" id = "cardData" value="" />
  <input type="hidden" id = "cardType" value="${cardType}" />
 <input type="hidden" id = "capacityTypeCode" value="" />
 <input type="hidden" id = "resKindCode" value="" />
 <input type="hidden" id = "procId" value="" />
  <input type="hidden" id = "submitFlag" value="" /> <!-- 设置一个标志位对于白卡开户的时候，如果不写卡，不读卡的情况下不能点击确定提交-->
	<div class="crumbs">当前位置: 待处理的订单 > 配卡</div>
	<div class="sale_content">
		<div class="content">
			<table width="1000" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="175" valign="top" bgcolor="#EEEEEE" class="c_left">
						<div class="wrap">
							<ul>
								<li class="first"><a href="#" class="hover">订单处理</a></li>
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
										<td width="66" height="30" align="right">订单号：</td>
										<td width="154">${map['order_id']}</td>
										<td width="66" align="right" class="line_v">客户名称：</td>
										<td width="104">${map['customer_name']}</td>
										<td width="66" align="right" class="line_v">证件类型：</td>
										<td width="92"><t:lable codeType="id_type" codeId="${map['id_type']}"></t:lable></td>
										<td width="66" align="right" class="line_v">证件号码：</td>
										<td width="164">${map['id_number']}</td>
									</tr>
									<tr>
										<td height="30" align="right">本地网：</td>
										<td><t:lable codeType="local_net" codeId="${map['local_net']}"></t:lable></td>
										<td align="right" class="line_v">设备号码：</td>
										<td>${map['acc_nbr']}</td>
										<td align="right" class="line_v">电信类别：</td>
										<td>${map['tele_type']}</td>
										<td align="right" class="line_v">套餐名称：</td>
										<td>${map['product_name']}</td>
									</tr>
									<tr>
										<td height="30" align="right">下单时间：</td>
										<td>${map['create_date']}</td>
										<td class="line_v" align="right">终端机型：</td>
										<td>${map['terminal_type']}</td>
										
									</tr>
									<tr>
									<input type="hidden" id="acc_nbr" value="${map['acc_nbr']}" />
									<input type="hidden" id="order_id" value="${map['order_id']}" />
									<input type="hidden" id="accountOpenEnable" value="0" />
									<input type="hidden" id="id_number" value="${map['id_number']}" />
									<!-- 将校验通过的卡号存入此控件中，用于判断成卡开户时的卡号是否是资源校验时的卡号 -->
									<input type="hidden" id="iccid" value="0" />
									<input type="hidden" id="tele_type" value="${map['tele_type']}" />
									<input type="hidden" id="terminalType" value="${map['terminal_type']}" />
									</tr>
								</table>


							</div>
							<h2 class="title margin_top">配卡</h2>
							<div class="right_box">
								<table width="778" border="0" align="center" cellpadding="0"
									cellspacing="0">
								<c:if test="${map['tele_type']=='2G'&&map['card_kind']=='02'}">
								 <tr>
                                        <td width="70" height="30" align="center"><font color="red"> * </font>卡类型：</td>
										<td width="100" >
										
											<select name="resourcesType" id="resourcesType">
												<option value="02" >白卡</option>
										    </select>
										 </td>
                                         <td width="60" align="right">ICCID：</td>
                                         <td><input type="text" name="resourcesCode" id="resourcesCode" class="id" />
                                         <a href="#" class="id_read" id="readCard"  >读卡</a><a href="#" id="writeCard" class="id_write">一键写卡</a></td>
                                     </tr>
								 </c:if>	
								<c:if test="${map['tele_type']=='3G'||map['tele_type']=='4G'}">
									<tr>
								  <td width="70" height="30" align="center"><font color="red"> * </font>卡类型：</td>
										<td width="100" >
										
											<select name="resourcesType" id="resourcesType">
												<option value="02" >白卡</option>
										    </select>
										 </td>
                                         <td width="60" align="right">ICCID：</td>
                                         <td><input type="text" name="resourcesCode" id="resourcesCode" class="id" />
                                         <a href="#" class="id_read" id="readCard"  >读卡</a><a href="#" id="writeCard" class="id_write">一键写卡</a></td>
									</tr>
								</c:if>
								</table>
								<div class="line"></div>
								<div class="open_submit">
									<a  href="###" class="ok" id="accountOpen" >确 定</a>
									<a href="###" class="cancel" id="cancel">取 消</a>

								</div>
								<div class="card_reader" style="display:none">
									<OBJECT id="CardReader" codebase="<%=fullPath%>js/CardX.ocx#version=1,0,0,1" classid=clsid:43E4D4FC-3CD8-459A-AAA1-698C1288DE93>
									<PARAM NAME="_Version" VALUE="65536">
									<PARAM NAME="_ExtentX" VALUE="2646">
									<PARAM NAME="_ExtentY" VALUE="1323">
									<PARAM NAME="_StockProps" VALUE="0">
									</OBJECT>
								</div>
							</div>
						</div>
					</td>
				</tr>
			</table>

		</div>
	</div>

</body>
</html>
