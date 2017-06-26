<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@include file="../common/common.jsp"%>
 <%@ page import="com.tydic.unicom.util.DateUtil" %>
 <%@page import="org.jasig.cas.client.util.AssertionHolder"%>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单详情</title>
<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/showOrder.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/showOrderChange.js"></script>
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
  <input type="hidden"  id="apweb_url"  value="${apweb_url}"/><!--无纸化地址   --> 
  <input type="hidden" id="change_order_type" value="${change_order_type}"/><!--//修改bug 变更pdf协议显示  --> 
  <input type="hidden" id="template_type_of_change" value="${template_type_of_change}"/>  
  <input type="hidden"  id="order_sub_type"  value="${order_sub_type}"/><!--订单类型-->   
  <input type="hidden"  id="id_type"  value="${idTypeCard}"/>
  <input type="hidden"  id="terminal_id"  value="${terminal_id}"/>
  <input type="hidden"  id="mkt_activity"  value="${mkt_activity}"/>
  <input type="hidden"  id="mkt_code"  value="${mkt_code}"/>
  
  <input type="hidden"  id="old_customer_id"  value="${old_customer_id}"/>   
  <input type="hidden"  id="old_id_type"  value="${old_id_type}"/>
  <input type="hidden"  id="customer_id"  value="${idNumber}"/>
  <input type="hidden"  id="guo_hu_charge_code"  value="${feeList[0].fee_id}"/>
   <input type="hidden"  id="customer_name"  value="${customerName}"/>
   <input type="hidden"  id="wt_flag"  value="${wt_flag}"/>
   <input type="hidden" value="${jsessionid}" id="jsessionid" />
   <input type="hidden" value="${auth_adress}" id="auth_adress" />
<div class="show" id="callLevel">
	<div class="show_title_bg">
    	<div class="show_title"><a href="###" class="del">删除订单</a><a href="###" class="move">转移订单</a><a href="###" class="delay">迟延订单</a><!--<a href="###">返回</a>-->订单详情</div>
    </div>
    <div class="status">
        <table width="860" border="0" align="center" cellpadding="0" cellspacing="0" align="center">
          <tr>
            <td height="40" colspan="4" align="center"><div><img id="sytleProgress"  src="<%=fullPath%>images/tow_1.png" /></div><!--ps:原始为第一状态，以后为：<div class="progress_1 progress_2"></div> 即每个状态追加1，共到5--></td>
          </tr>
         <tr>
            <td width="134" height="20" align="left">&nbsp;</td>
            <td width="140" align="right">预订单生成</td>
            <td width="240" align="center">竣工</td>
            <td width="40" align="right">&nbsp;</td>
          </tr>
        </table>
    </div>
	<div class="order_info">
	    	<div class="order_info_title">
	        <a href="###" id="shenSuoBianGeng" class="down">收缩</a><span>1</span> 变更信息<!--如果是展开，请用：<a href="###" class="">展开</a>   如果是收缩：<div class="order_info_title order_info_boder"> 即追加：order_info_boder样式。<div class="box">追加hide()方法或display:none;即可-->
	        </div>
	   	<div class="box" id="box1">
	            <h2>客户资料</h2>
	              <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
	                <tr>
	                  <td width="62" height="24" align="right">订单编号：</td>
	                  <td width="376" class="dashed">${orderId}</td>
	                  <td width="80" align="right">&nbsp;</td>
	                  <td width="356">&nbsp;</td>
	                </tr>
	                
	                <c:choose>  
						<c:when test="${change_order_type=='8'}"> 
							<tr>
			                    <td height="24" align="right">客户姓名：</td>
			                    <td class="dashed">${old_customer_name}</td>
			                    <td align="right">&nbsp;</td>
			                    <td>&nbsp;</td>
	                  		</tr>
	                  		<tr>
			                    <td height="24" align="right">证件类型：</td>
			                    <td class="dashed">身份证</td>
			                    <td align="right">证件号码：</td>
			                    <td><p>${old_customer_id}</p></td>
	                  		</tr>
						</c:when>
					<c:otherwise>
							<tr>
			                    <td height="24" align="right">客户姓名：</td>
			                    <td class="dashed">${customerName}</td>
			                    <td align="right">&nbsp;</td>
			                    <td>&nbsp;</td>
	                  		</tr>
	                  		<tr>
			                    <td height="24" align="right">证件类型：</td>
			                    <td class="dashed">${idTypeCard}</td>
			                    <td align="right">证件号码：</td>
			                    <td><p>${idNumber}</p></td>
	                  		</tr>
					</c:otherwise>  
					</c:choose>	   
	                 
	              </table>
	              <div class="line"></div>
	          
	        </div>
		<div class="order_info_title">
	        <a href="###" class="down"  id="shenSuoYeWu">收缩</a><span>2</span> 变更业务信息：${orderSubName}<!--如果是展开，请用：<a href="###" class="">展开</a>-->
	    </div>
	    <div class="box" id="tarriffbox2"  style="display:none;">
              <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="74" height="24" align="right">用户号码：</td>
                  <td width="800">${accNbrString}</td>
                </tr>
                  <tr>
                    <td height="24" align="right">原套餐：</td>
                    <td >${orig_product_name}</td>
                  </tr>
                  <tr>
                    <td height="24" align="right" valign="top">新套餐：</td>
                    <td>${product_name}</td>
                  </tr>
              </table>
      		<div class="line line_per"></div>
              <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="74" height="24" align="right">取消业务包：</td>
                  <td width="800">${cancel_ywb}</td>
                </tr>
                  <tr>
                    <td height="24" align="right" valign="top">新增业务包：</td>
                    <td >${add_ywb}</td>
                  </tr>
              </table>
      		<div class="line line_per"></div>
              <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="74" height="24" align="right">新增活动：</td>
                  <td width="800">${activity_name}</td>
                </tr>
                  <tr>
                    <td height="24" align="right" valign="top">可选活动：</td>
                    <td ></td>
                  </tr>
                  <tr>
                    <td height="24" align="right" valign="top">活动期：</td>
                    <td ></td>
                  </tr>
              </table>
			  <div class="line line_per"></div>
              <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="74" height="24" align="right">后机信息：</td>
                  <td width="800">${terminal_desc}</td>
                </tr>
                  <tr>
                    <td height="24" align="right" valign="top">IMEI码：</td>
                    <td >${terminal_id}</td>
                  </tr>               
                  <tr>
                    <td height="24" align="right" valign="top">生效时间：</td>
                    <td >${change_date}</td>
                  </tr>
              </table>
      		<div class="line line_per"></div>
			
			<ul class="usim">
				<li>备注：</li>
				<li><textarea></textarea></li>
			</ul>
      </div>
	    <div class="box" id="callLevelbox2" style="display:none;">
	              <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
	                <tr>
	                  <td width="62" height="24" align="right">用户号码：</td>
	                  <td width="812">${accNbrString} &nbsp;&nbsp;&nbsp;&nbsp;<span style="background:url(images/tip.png) no-repeat center;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span class="red"></span></td>
	                </tr>
	              </table>
			    <div class="line line_per"></div>
	              <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
	                <tr>
	                  <td width="76" height="24" align="right">原长途状态：</td>
	                  <td width="354" class="dashed">
	                  	<c:if test="${orig_call_level=='3' }">国际长途</c:if>
	                  	<c:if test="${orig_call_level!='3' }">国内长途</c:if>
	                  </td>
	                  <td width="80" align="right">新长途状态：</td>
	                  <td width="356">国内长途
	                  	<c:if test="${call_level=='03' }">国际长途</c:if>
	                  	<c:if test="${call_level!='03' }">国内长途</c:if>
	                  </td>
	                </tr>
	                  <tr>
	                    <td height="24" align="right">原漫游状态：</td>
	                    <td class="dashed">
	                    	<c:if test="${orig_roam_level=='2' }">国际漫游</c:if>
	                  		<c:if test="${orig_roam_level!='2' }">国内漫游</c:if>
	                    </td>
	                    <td align="right">新漫游状态：</td>
	                    <td>
	                    	<c:if test="${roam_level=='02' }">国际漫游</c:if>
	                  		<c:if test="${roam_level!='02' }">国内漫游</c:if>
	                    </td>
	                  </tr>
	                  <tr>
	                    <td height="24" align="right">生效时间：</td>
	                    <td class="dashed">${eff_date}</td>
	                    <td align="right">失效时间：</td>
	                    <td>${exp_date}</td>
	                  </tr>
	              </table>
			    <div class="line line_per"></div>
				
				<ul class="usim">
					<li>备注：</li>
					<li><textarea></textarea></li>
				</ul>
	      </div>
	      <div class="box" id="openOrClosebox2" style="display:none;">
	             <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
	               <tr>
	                 <td width="82" height="24" align="right">用户号码：</td>
	                 <td width="356" class="dashed">${accNbrString}</td>
	                 <td width="80" align="right"></td>
	                 <td width="356"></td>
	               </tr>
	                 <tr>
	                   <td height="24" align="right">号码原状态：</td>
	                   <td >${orig_status}</td>
	                   <td align="right">&nbsp;</td>
	                   <td>&nbsp;</td>
	                 </tr>
	                 <tr>
	                   <td height="24" align="right">号码新状态：</td>
	                   <td >${now_status}</td>
	                   <td align="right">&nbsp;</td>
	                   <td>&nbsp;</td>
	                 </tr>
	             </table>
	     		<div class="line line_per"></div>
			<ul class="usim">
				<li>备注：</li>
				<li><textarea></textarea></li>
			</ul>
	     </div>
	     <div class="box" id="colseAccountbox2" style="display:none;">
              <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="62" height="24" align="right">用户号码：</td>
                  <td width="376">${accNbrString}</td>
                  <td width="80" align="right"></td>
                  <td width="356"></td>
                </tr>
                  <tr>
                    <td height="36" align="right">销户原因：</td>
                    <td colspan="3">${destroied_reason_id_name}</td>
                  </tr>
                  <!-- <tr>
                    <td height="36" align="left" colspan="4">信号不好的位置位置：</td>
                  </tr>
                  <tr>
                    <td height="36" align="left" colspan="4"><input type="text" class="closeAcount_info"/></td>
                  </tr> -->
              </table>
			<ul class="usim">
				<li>备注：</li>
				<li><textarea></textarea></li>
			</ul>
      </div>
      <div class="box" id="businessbox2" style="display:none;">
              <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="74" height="24" align="right">用户号码：</td>
                  <td width="800">${accNbrString}</td>
                </tr>
                  <tr>
                    <td height="24" align="right">取消业务包：</td>
                    <td >${cancel_ywb}</td>
                  </tr>
                  <tr>
                    <td height="24" align="right" valign="top">新增业务包：</td>
                    <td >${add_ywb}</td>
                  </tr>
                  <tr>
                    <td height="24" align="right" valign="top">生效时间：</td>
                    <td >${change_date}</td>
                  </tr>
              </table>
      		<div class="line line_per"></div>
			<ul class="usim">
				<li>备注：</li>
				<li><textarea></textarea></li>
			</ul>
      </div>
      <div class="box" id="redeemPointsbox2" style="display:none;">
              <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="62" height="24" align="right">用户号码：</td>
                  <td width="356" class="dashed">${accNbrString}</td>
                  <td width="80" align="right"></td>
                  <td width="356"></td>
                </tr>
                  <tr>
                    <td height="24" align="right">兑换内容：</td>
                    <td >${ofr_name}</td>
                    <td align="right">&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr>
                    <td height="24" align="right">生效日期：</td>
                    <td >${eff_date}</td>
                    <td align="right">&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
              </table>
      		<div class="line line_per"></div>
			<ul class="usim">
				<li>备注：</li>
				<li><textarea></textarea></li>
			</ul>
      </div>
      <div class="box" id="remakeCardbox2" style="display:none;">
              <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="72" height="24" align="right">用户号码：</td>
                  <td width="376">${accNbr}</td>
                  <td width="80" align="right"></td>
                  <td width="356"></td>
                </tr>
                  <tr style="display:none;">
                    <td height="36" align="right">USIM卡号：</td>
                    <td colspan="3"><input type="text"  class="sim_input"/><a href="#" class="view_btn"> 读 卡 </a> <a href="###" class="view_btn">一键写卡</a></li></td>
                  </tr>
              </table>
      		<div class="line line_per"></div>
			<ul class="usim">
				<li>备注：</li>
				<li><textarea></textarea></li>
			</ul>
      </div>
      <div class="box" id="transferbox2" style="display:none;">
              <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="82" height="24" align="right">用户号码：</td>
                  <td width="356" class="dashed">${accNbr}</td>
                  <td width="80" align="right"></td>
                  <td width="356"></td>
                </tr>
                  <tr>
                    <td height="24" align="right">新客户姓名：</td>
                    <td class="dashed">${customerName}</td>
                    <td align="right">&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr>
                    <td height="24" align="right">新证件类型：</td>
                    <td class="dashed">身份证</td>
                    <td align="right">新证件号码：</td>
                    <td><p>${idNumber}</p></td>
                  </tr>
                  <!-- YUN-778
                  <tr>
                    <td height="24" align="right">新联系电话：</td>
                    <td class="dashed">18677118818</td>
                    <td align="right">&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr>
                    <td height="24" align="right">新证件地址：</td>
                    <td class="dashed">南宁市江南区淡村路14号3单元101号房</td>
                    <td align="right">&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr>
                    <td height="24" align="right">新联系地址：</td>
                    <td class="dashed">南宁市江南区淡村路14号3单位101号房</td>
                    <td align="right">&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr> -->
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
	                      	我已阅读并同意
	                      	<c:choose>  
							   <c:when test="${change_order_type=='1'}"> 
									<a href="#" style="text-decoration:underline;" onclick="wpkbiangeng();"> 《中国联通业务客户变更服务协议》 </a> 
							   </c:when>
							   <c:when test="${change_order_type=='4'}"> 
									<a href="#" style="text-decoration:underline;" onclick="wpkbiangeng();"> 《中国联通业务客户变更服务协议》 </a> 
							   </c:when>
							   <c:when test="${change_order_type=='8'}"> 
									<a href="#" style="text-decoration:underline;" onclick="wpkbiangeng();"> 《中国联通业务客户变更服务协议》 </a> 
							   </c:when>
							   <c:when test="${change_order_type=='7'}"> 
									<input name="radio_tiaokuan_info" type="radio" id="a10" value="pass_innet" checked="checked" /> 
                            		<a href="#" style="text-decoration:underline;color:#FF0000;font-weight:bolder"  onclick="wpkbiangeng();"> 《中国联通业务客户变更服务协议》</a>
							   </c:when>
							   <c:when test="${change_order_type=='9'}"> 
									<a href="#" style="text-decoration:underline;" onclick="wpkbiangeng();"> 《中国联通业务客户变更服务协议》 </a> 
							   </c:when>         
							   <c:otherwise>
									<a href="#" style="text-decoration:underline;" onclick="payment();"> 《中国联通业务客户入网服务协议》 </a>
									<a href="#" style="text-decoration:underline;" onclick="termsPreferential();">《中国联通业务优惠方案补充协议》 </a>    
							   </c:otherwise>  
							</c:choose>	                      	
						  <p>同意签署协议前，请仔细阅读协议条款。根据<span class="red">《中国人民共和国电子签名法》</span>，该电子协议由中国联合通信网络通信有限公司广西壮族自治区分公司以电子存档方式保存，具备法律效力。</p>
						</td>
	                  </tr>
	              </table>
	              <div class="line line_per"></div>
	              <div class="elc">
	              	<a href="###" onclick="apweb();">同意协议及电子签名</a>
	              </div>
	           </form>
	      </div>
	   	<div class="order_info_title">
	       <a href="###" class="down"  id="shenSuoFeiYong">收缩</a><span>4</span> 收费信息：<!--如果是展开，请用：<a href="###" class="">展开</a>-->
	    </div>
	   	<div class="box" id="box4">
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
	      <div style="display: none;" class="out_div">
			<img class="pic-idPic" src='' /><div style='width:100%;text-align:left;'><span style='color:#F00;font-size:20px'>手机支付宝扫描二维码支付<button id="closetwocode" >关闭</button></span></div>
		  </div>
	   	</div>
	 
	        
	  </div>
  <div style="display:none" class="ok" id="okModule"><a href="###" id="okSubmit" >完成</a></div>

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
