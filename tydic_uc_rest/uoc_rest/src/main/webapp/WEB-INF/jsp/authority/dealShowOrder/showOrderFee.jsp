<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@include file="../common/common.jsp"%>
 <%@ page import="com.tydic.unicom.util.DateUtil" %>
 <%@page import="org.jasig.cas.client.util.AssertionHolder"%>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单详情</title>
<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/showOrder.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/showOrderFee.js"></script>
<script type="text/javascript" src="<%=fullPath%>js/posPayFee.js"></script>
<script type="text/javascript">
	var umsocx;
</script>
</head>

<body onload="initPos()">
  <input type="hidden"  id="payFlag"  value="${payFlag}"/><!--收费标示 --> 
  <input type="hidden"  id="payType"  value="${payType}"/><!--是pos机刷卡还是坐席收费标志--> 
  <input type="hidden"  id="acc_nbr"  value="${accNbr}"/><!--用户号码 -->   
  <input type="hidden"  id="order_id"  value="${orderId}"/><!--订单id-->   
  <input type="hidden" id="tele_type" value="${teleType}" /> 
  <input type="hidden"  id="order_status"  value="${order_status}"/><!--订单状态--> 
    
    
    <input type="hidden"  id="order_sub_type"  value="${order_sub_type}"/><!--订单类型-->   
    <input type="hidden"  id="id_type"  value="${idTypeCard}"/>
    
<div class="show">
  <div class="show_title_bg">
    	<div class="show_title"><a href="###" class="del">删除订单</a><a href="###" class="move">转移订单</a><a href="###" class="delay">迟延订单</a><!--<a href="###">返回</a>-->订单详情</div>
    </div>
    <div class="status">
        <table width="860" border="0" align="center" cellpadding="0" cellspacing="0" align="center">
          <tr>
            <td height="40" colspan="6"><div class="progress_1"><img id="sytleProgress"  src="<%=fullPath%>images/three_long_1.png" /></div><!--ps:原始为第一状态，以后为：<div class="progress_1 progress_2"></div> 即每个状态追加1，共到5--></td>
          </tr>
         <tr>
            <td width="134" height="20" align="left">&nbsp;</td>
            <td width="103" align="left">预订单生成</td>
            <td width="201" align="center">&nbsp;</td>
            <td width="137" align="center">待收费</td>
            <td width="205" align="center">&nbsp;</td>
            <td width="83" align="right">竣工</td>
          </tr>
        </table>
    </div>
  <div class="order_info">
    	<div class="order_info_title">
        <a href="###" id="shenSuoDingGou" class="down">收缩</a><span>1</span> 收费信息<!--如果是展开，请用：<a href="###" class="">展开</a>   如果是收缩：<div class="order_info_title order_info_boder"> 即追加：order_info_boder样式。<div class="box">追加hide()方法或display:none;即可-->
        </div>
   	<div class="box" id="box1">
            <h2>收费资料</h2>
              <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="62" height="24" align="right">订单编号：</td>
                  <td width="376" class="dashed">${orderId}</td>
                  <td width="80" align="right">业务类型：</td>
                  <td width="356">${orderSubNmae}</td>
                </tr>
                  <tr>
                    <td height="24" align="right">证件类型：</td>
                    <td class="dashed">${idType}</td>
                    <td align="right">证件号码：</td>
                    <td><p>${idNumber}</p></td>
                  </tr>
                   <tr>
                    <td height="24" align="right">客户姓名：</td>
                    <td class="dashed">${customerName}</td>
                    <td align="right">&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
              </table>
              <div class="line"></div>
          
        </div>

    	<div class="order_info_title">
        <a href="###" class="down"  id="shenSuoFeiYong">收缩</a><span>2</span> 费用信息<!--如果是展开，请用：<a href="###" class="">展开</a>-->
        </div>
   	<div class="box" id="box3">
   	  <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
   	        <c:forEach var="infoOrderFeeDetailVo" items="${feeList}" varStatus="i">
                  <tr>
                    <td width="100" height="24" align="right">收费类型：</td>
                    <td width="788">${infoOrderFeeDetailVo.fee_name }</td>
                  </tr>
                  <tr>
                    <td width="100" height="24" align="right">预存话费：</td>
                    <td width="788">${infoOrderFeeDetailVo.real_fee }元</td>
                  </tr>
              </c:forEach>
                  <tr>
                    <td height="24" width="100" align="right">合计：</td>
                    <td>${totalFee}元 <span class="red" id="getFee"></span></td>
                  </tr>
      </table>
      <div class="line line_per" id="linePer"></div>
      <ul class="pay" id="pay">
      	<li>支付方式： </li>
      		<li id="payTypePc">
      	<t:select id="pay_type" codeType="pay_type" ></t:select></td></li>
      	
      	  	<li id="payTypeMobile">
      	<t:select id="pay_type_mobile" codeType="pay_type_mobile" ></t:select></td></li>
        <!--  <li><select id="" class="money">
        	<option>坐席收现</option>
        	<option>POS机刷卡</option>
        	<option>支付宝扫码支付</option>
        	<option>微信支付</option>
        	<option>营业电子交款(员工)</option>
        </select></li>-->
        <li><a href="###" onclick="dealFee();" class="view_btn" id="btnGetFee">收费</a></li>
        <li><a href="###" class="view_btn" id="btnDaYin" >收据打印</a></li>
        <div class="clear"></div>
      </ul>
   	</div>
   	<!--YUN-744   NX_代理商开户/缴费佣金功能 -->
 	<c:if test="${not empty comm_fee}">
     	<div class="order_info_title">
			<a href="###" id="shenSuoCommission" class="down">收缩</a><span>3</span> 佣金信息<!--如果是展开，请用：<a href="###" class="">展开</a>   如果是收缩：<div class="order_info_title order_info_boder"> 即追加：order_info_boder样式。<div class="box">追加hide()方法或display:none;即可-->
        </div>
		<div class="box" id="boxCommission">
            <h2>佣金资料</h2>
				<table width="874" border="0" align="center" cellpadding="0" cellspacing="0">   
				<tr>
					<td width="100" height="24" align="right">金额：</td>
					<td width="788">${comm_fee}</td>
				</tr>                
                </table>
                <div class="line"></div>          
        </div>
    </c:if>
        
  </div>
    
    
    
    
  <div class="ok" id="okModule"><a href="###" id="okSubmit" >完成</a></div>

<!-- pos刷卡返回流水隐藏信息 -->
<div style="display:none">
    <input id="oper_no" name="oper_no" type="hidden" value="${oper_no}">
    <td><input name="payed_fee" id="discount_totalCosts_input" type="hidden" value="${totalFee}"/></td>
	<!--返回码:--><input type="hidden" size=2 maxlength=2 id="rspCode"><br>
	<!--银行行号:--><input type="hidden" size=4 maxlength=4 id="bankCode"><br>
	<!--卡号:--><input type="hidden" size=20 maxlength=20 id="cardNo"><br>
	<!--有效期:--><input type="hidden" size=4 maxlength=4 id="expr"><br>
	<!--批次号:--><input type="hidden" size=6 maxlength=6 id="batch"><br>
	<!--流水号:--><input type="hidden" size=6 maxlength=6 id="trace"><br>
	<!--支付金额:--><input type="hidden" size=12 maxlength=12 id="rspAmount"><br>
	<!--支付金额  隐藏传值,免单位转换:--><input type="hidden" size=12 maxlength=12 id="charge"><br>
	<!--返回中文提示:--><input type="hidden" size=40 maxlength=40 id="rspChin"><br>
	<!--商户号:--><input type="hidden" size=15 maxlength=15 id="mchtId"><br>
	<!--终端号:--><input type="hidden" size=8 maxlength=8 id="termId"><br>
	<!--参考号:--><input type="hidden" size=12 maxlength=12 id="reference"><br>
	<!--交易日期:--><input type="hidden" size=4 maxlength=4 id="transDate"><br>
	<!--交易时间:--><input type="hidden" size=6 maxlength=6 id="transTime"><br>
	<!--授权号:--><input type="hidden" size=6 maxlength=6 id="authNo"><br>
	<!--结算日期:--><input type="hidden" size=4 maxlength=4 id="settleDate"><br>
	<!--增值域响应信息:--><input type="hidden" size=50 maxlength=100 id="appendResField"><br>
	<!--备注信息:--><input type="hidden" size=50 maxlength=100 id="memo"><br>
</div>
    
    
    
    
    
    
    
</div>

<div class="copyright">
Copyright © 2014 China unicom All Right Reserved
</div>
<OBJECT id="umsocxId" height=0 width=0 classid=clsid:5099c9c4-beca-44fe-a42c-8656fbb5a0f3></OBJECT>
</body>
</html>
