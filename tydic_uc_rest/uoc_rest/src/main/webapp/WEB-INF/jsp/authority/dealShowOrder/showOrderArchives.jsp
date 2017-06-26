<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@include file="../common/common.jsp"%>
 <%@ page import="com.tydic.unicom.util.DateUtil" %>
 <%@page import="org.jasig.cas.client.util.AssertionHolder"%>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单详情</title>
<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/showOrder.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/showOrderArchives.js"></script>
<script type="text/javascript" src="<%=fullPath%>js/posPayFee.js"></script> 
<script type="text/javascript">
	var umsocx;
</script>
</head>
<body onload="initPos()">
  <input type="hidden"  id="order_id"  value="${order_id}"/><!--订单id-->
  <input type="hidden"  id="order_sub_type"  value="${order_sub_type}"/><!--订单类型-->
  <input type="hidden"  id="acc_nbr"  value="${acc_nbr}"/><!--用户号码 -->
  <input type="hidden"  id="id_type"  value="${id_type}"/>
  <input type="hidden"  id="id_number" value="${id_number}"/>
  <input type="hidden"  id="customer_name" value="${customer_name}"/>
<div class="show" id="callLevel">
	<div class="show_title_bg">
		<div class="show_title"><a href="###" class="del">删除订单</a><a href="###" class="move">转移订单</a><a href="###" class="delay">迟延订单</a><!--<a href="###">返回</a>-->订单详情</div>
	</div>
	<div class="status">
		<table width="860" border="0" align="center" cellpadding="0" cellspacing="0" align="center">
			<tr>
				<td height="40" colspan="4" align="center"><div><img id="sytleProgress"  src="<%=fullPath%>images/tow_2.png" /></div></td>
			</tr>
			<tr>
				<td width="134" height="20" align="left">&nbsp;</td>
				<td width="140" align="right">返档单生成</td>
				<td width="240" align="center">竣工</td>
				<td width="40" align="right">&nbsp;</td>
			</tr>
		</table>
	</div>
	<div class="order_info">
		<div class="order_info_title">
			<a href="###" id="shenSuoFanDang" class="down">收缩</a><span>1</span> 返档信息<!--如果是展开，请用：<a href="###" class="">展开</a>   如果是收缩：<div class="order_info_title order_info_boder"> 即追加：order_info_boder样式。<div class="box">追加hide()方法或display:none;即可-->
		</div>
		<div class="box" id="box1">
			<h2>客户资料</h2>
			<table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="62" height="24" align="right">订单编号：</td>
					<td width="376" class="dashed">${order_id}</td>
					<td width="80" align="right">&nbsp;</td>
					<td width="356">&nbsp;</td>
				</tr>
				<tr>
					<td height="24" align="right">客户姓名：</td>
					<td class="dashed">${customer_name}</td>
					<td align="right">&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td height="24" align="right">证件类型：</td>
					<td class="dashed">${id_type_display}</td>
					<td align="right">证件号码：</td>
					<td><p>${id_number}</p></td>
				</tr>
			</table>
			<div class="line"></div>
		</div>
		<div class="order_info_title">
			<a href="###" class="down"  id="shenSuoYeWu">收缩</a><span>2</span> 返档业务信息：<!--如果是展开，请用：<a href="###" class="">展开</a>-->
	    </div>
		<div class="box" id="box2" style="display:none;">
			<table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="82" height="24" align="right">用户号码：</td>
					<td width="356" class="dashed">${acc_nbr}</td>
					<td width="80" align="right"></td>
					<td width="356"></td>
				</tr>
				<tr>
				<td height="24" align="right">客户姓名：</td>
					<td class="dashed">${customer_name}</td>
					<td align="right">&nbsp;</td>
                    <td>&nbsp;</td>
				</tr>
				<tr>
					<td height="24" align="right">证件类型：</td>
					<td class="dashed">身份证</td>
 					<td align="right">证件号码：</td>
					<td><p>${id_number}</p></td>
				</tr>
			</table>
			<div class="line line_per"></div>
			<ul class="usim">
				<li>备注：</li>
				<li><textarea></textarea></li>
			</ul>
		</div>
		<div class="order_info_title">
	       <a href="###" class="down"  id="shenSuoTiaoKuan">收缩</a><span>3</span> 条款协议<!--如果是展开，请用：<a href="###" class="">展开</a>-->
	    </div>
	    <div class="box" id="box3">
			<form action="" method="">
				<table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<td height="24" align="left">
							我已阅读并同意<a href="#" style="text-decoration:underline;" onclick="archivesWkp('mtd');"> 《中国联通业务客户返档服务协议》 </a>
							<p>同意签署协议前，请仔细阅读协议条款。根据<span class="red">《中国人民工和国电子签名法》</span>，该电子协议由中国联合通信网络通信有限公司广西壮族自治区分公司以电子存档方式保存，具备法律效力。</p>
						</td>
					</tr>
				</table>
				<div class="line line_per"></div>
				<div class="elc">
					<a href="###" onclick="archivesWkp('wzh');">同意协议及电子签名</a>
				</div>
			</form>
		</div>
	</div>
</div>

<div class="copyright">
Copyright © 2014 China unicom All Right Reserved
</div>
<OBJECT id="umsocxId" height=0 width=0 classid=clsid:5099c9c4-beca-44fe-a42c-8656fbb5a0f3></OBJECT>
</body>
</html>
