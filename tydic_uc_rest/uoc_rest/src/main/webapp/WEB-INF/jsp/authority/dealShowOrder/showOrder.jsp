
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@include file="../common/common.jsp"%>
 <%@ page import="com.tydic.unicom.util.DateUtil" %>
 <%@page import="org.jasig.cas.client.util.AssertionHolder"%>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单详情</title>
<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/showOrder.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />

</head>

<body onload="initPos()">
 <input type="hidden"  id="progress"  value="${progress}"/><!--记录订单详情进度 -->
  <input type="hidden"  id="payFlag"  value="${payFlag}"/><!--收费标示 --> 
  <input type="hidden"  id="payType"  value="${payType}"/><!--是pos机刷卡还是坐席收费标志--> 
  <input type="hidden"  id="acc_nbr"  value="${accNbr}"/><!--用户号码 -->   
  <input type="hidden"  id="order_id"  value="${orderId}"/><!--订单id-->   
  <input type="hidden" id="tele_type" value="${teleType}" />
  <input type="hidden" id = "writeWay" value="${writeWay}" /><!--模拟写卡还是正式写卡方式0代表模拟写卡1代表正式写卡-->
 <input type="hidden"  id="prepayFlag"  value="${prepayFlag}"/>
 <input type="hidden"  id="id_number"  value="${idNumber}"/>
  <input type="hidden"  id="product_name"  value="${product_name}"/>
 <input type="hidden"  id="imsi"  value=""/>
 <input type="hidden" id = "activeId" value="" />
 <input type="hidden" id = "cardData" value="" />
  <input type="hidden" id = "cardType" value="${cardType}" />
 <input type="hidden" id = "capacityTypeCode" value="" />
 <input type="hidden" id = "resKindCode" value="" />
 <input type="hidden" id = "procId" value="" /> 
  <input type="hidden"  id="order_source"  value="${order_source}"/>
  <input type="hidden"  id="order_status"  value="${order_status}"/><!--订单状态--> 
 <input type="hidden"  id="apweb_url"  value="${apweb_url}"/><!--无纸化地址   -->
 <input type="hidden"  id="good_templateid"  value="${good_templateid}"/><!--靓号模板ID   -->
 <input type="hidden"  id="preferential_templateid"  value="${preferential_templateid}"/><!--优惠协议模版ID   -->
 <input type="hidden"  id="local_templateid"  value="${local_templateid}"/><!--本地协议模版ID   -->
 <input type="hidden"  id="apweb_value"  value="1"/> <!--协议及电子签名序号-->
 <input type="hidden"  id="order_sub_type"  value="${order_sub_type}"/><!--订单类型--> 
 <input type="hidden"  id="id_type"  value="${idTypeCard}"/>
 <input type="hidden"  id="province_code"  value="${province_code}"/>
 <input type="hidden"  id="termsInnetFlag"  value="${termsInnetFlag}"/><!--入网协议   -->
 <input type="hidden"  id="termsGoodFlag"  value="${termsGoodFlag}"/><!--靓号协议-->
 <input type="hidden"  id="termsPreferentialFlag"  value="${termsPreferentialFlag}"/><!--优惠协议  -->
 <input type="hidden"  id="pdf_resign_time"  value="${pdf_resign_time}"/><!--重签时间差  -->
    <input type="hidden" value="${jsessionid}" id="jsessionid" />
     <input type="hidden"  id="wt_flag"  value="${wt_flag}"/>
     <input type="hidden"  id="big_agent_flag"  value="${big_agent_flag}"/>
  <input type="hidden"  id="pass_templateid"  value="${pass_templateid}"/><!--护照协议模版ID   -->
  <input type="hidden"  id="terms_pass_innet_flag"  value="${terms_pass_innet_flag}"/><!--护照协议   -->
<div class="show">
  <div class="show_title_bg">
    	<div class="show_title">
    	<c:if test="${province_code !='cq' }">
    		<a href="###" class="del">删除订单</a><a href="###" class="move">转移订单</a><a href="###" class="delay">迟延订单</a>
    	</c:if>
    	<!--<a href="###">返回</a>-->订单详情
    	</div>
    </div>
    <div class="status">
        <table width="860" border="0" align="center" cellpadding="0" cellspacing="0" align="center">
          <tr>
            <td height="40" colspan="6"><div class="pro_five_1"  ><img id="sytleProgress"  src="<%=fullPath%>images/five_long_1.png" /></div><!--ps:原始为第一状态，以后为：<div class="progress_1 progress_2"></div> 即每个状态追加1，共到5--></td>
          </tr>
           <tr>
            <td width="134" height="20" align="left">&nbsp;</td>
            <td width="103" align="left">预订单生成</td>
            <td width="201" align="center">终端调拨</td>
            <td width="137" align="center">待收费</td>
            <td width="205" align="center">待写卡</td>
            <td width="83" align="right">竣工</td>
          </tr>
        </table>
    </div>
  <div class="order_info">
    	<div class="order_info_title">
        <a href="###" id="shenSuoDingGou" class="down">收缩</a><span id="xuhao0">1</span> 订购信息<!--如果是展开，请用：<a href="###" class="">展开</a>   如果是收缩：<div class="order_info_title order_info_boder"> 即追加：order_info_boder样式。<div class="box">追加hide()方法或display:none;即可-->
        </div>
   	<div class="box" id="box1">
            <h2>客户信息</h2>
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
    
            <h2>用户号码</h2>
              <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="74" height="24" align="right"><span class="dashed">用户号码：</span></td>
                  <td width="800">${accNbrString}</td>
                </tr>
                  <tr>
                    <td height="24" align="right"><span class="dashed">首次预存：</span></td>
                    <td>${acc_nbr_fee}元</td>
                  </tr>
                  <tr>
                    <td height="24" align="right"><span class="dashed">月承诺消费：</span></td>
                    <td>${low_fee}元</td>
                  </tr>
              </table>
              <div class="line"></div>
    
            <h2>用户资费</h2>
              <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="86" height="24" align="right">资费信息：</td>
                  <td width="788">${product_name}</td>
                </tr>
                  <tr>
                    <td height="24" align="right">当月生效方式：</td>
                    <td>${first_month_fee}</td>
                  </tr>
                  <tr>
                    <td height="24" align="right">活动类型：</td>
                    <td>${activity_type_name}</td>
                  </tr>              
                  <tr>
                    <td height="24" align="right">活动信息：</td>
                    <td>${activity_name}</td>
                  </tr>
                  <tr>
                    <td height="24" align="right">活动业务包：</td>
                    <td>${discnt_name}</td>
                  </tr>
              </table>
              <div class="line"></div>
    
            <h2>手机信息</h2>
            <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="62" height="24" align="right"><span class="dashed">品牌型号</span>：</td>
                <td width="376" class="dashed">${terminal_type}</td>
                <td width="80" align="right">IMEI码：</td>
                <td width="356">${terminal_id}</td>
              </tr>
            </table>
            <div class="line"></div>
        </div>
    	<div class="order_info_title" id="tiaokuan_info">
        <a href="###" class="down" id="shenSuoTiaoKuan">收缩</a><span id="xuhao1">2</span> 条款协议<!--如果是展开，请用：<a href="###" class="">展开</a>-->
        </div>
        <div class="box" id="box2">
        	<form action="" method="">
              <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
                  <!-- <tr>
                    <td height="24" align="left"><input name="radio" type="radio" id="a" value="a" checked="checked" />
                      <label for="a">
                    《中国联通业务客户入网服务协议》(已签订) </label></td>
                  </tr>
                  <tr>
                    <td height="24" align="left"><input type="radio" name="radio" id="a2" value="a" />
                    <label for="a2">《优质号码服务协议》</label></td>
                  </tr>
                  <tr>
                    <td height="24" align="left"><input type="radio" name="radio" id="a3" value="a" />
                    <label for="a3">《中国联通宽带FTTH业务补充协议》</label></td>
                  </tr>
                  <tr>
                    <td height="24" align="left"><input type="radio" name="radio" id="a4" value="a" />
                    <label for="a4">《中国联通业务优惠方案补充协议》</label></td>
                  </tr>
                   -->
                   
                   <tr>
                     <div>
                         <td height="24" align="left">我已阅读并同意   
                         <!-- 护照的开户协议，优先显示 -->
                         <c:choose>
                         <c:when test="${terms_pass_innet_flag=='1'}"> 
                         	<input name="radio_tiaokuan_info" type="radio" id="a10" value="pass_innet" checked="checked" /> 
                            <a href="#" style="text-decoration:underline;color:#FF0000;font-weight:bolder"  onclick="payment();"> ${termsInnet} </a>
                         </c:when>                       		
                         <c:when test="${termsInnetFlag=='1'}"> 
                         	<input name="radio_tiaokuan_info" type="radio" id="a1" value="innet" checked="checked" /> 
                            <a href="#" style="text-decoration:underline;color:#FF0000;font-weight:bolder"  onclick="payment();"> ${termsInnet} </a>
                         </c:when>
                         </c:choose>
                         <c:if test="${termsGoodFlag=='1'}">
                         	<input type="radio" name="radio_tiaokuan_info" id="a2" value="good" />  
                            <a href="#" style="text-decoration:underline;color:#FF0000;font-weight:bolder"  onclick="termsGood();">${termsGood} </a>
                         </c:if>
                         <c:if test="${termsM165Flag=='1'}">  
                         	<!--<input type="radio" name="radio_tiaokuan_info" id="a3" value="m165" />-->
                            <a href="#" style="text-decoration:underline;color:#FF0000;font-weight:bolder"  onclick="termsM165();">${termsM165} </a>
                         </c:if>
                         <c:if test="${termsPreferentialFlag=='1'}">
                         	<input type="radio" name="radio_tiaokuan_info" id="a4" value="preferential" />  
                            <a href="#" style="text-decoration:underline;color:#FF0000;font-weight:bolder"  onclick="termsPreferential();">${termsPreferential} </a>
                         </c:if>
                         <!--新增本地活动协议  -->
                          <c:if test="${terms_freeCall_flag=='1'}">
                         	<input type="radio" name="radio_tiaokuan_info" id="a5" value="termsFreeCall" />  
                            <a href="#" style="text-decoration:underline;color:#FF0000;font-weight:bolder"  onclick="termsLocal('termsFreeCall');">${terms_freeCall} </a>
                         </c:if>
                          <c:if test="${terms_starPhone_flag=='1'}">
                         	<input type="radio" name="radio_tiaokuan_info" id="a6" value="termsStarPhone" />  
                            <a href="#" style="text-decoration:underline;color:#FF0000;font-weight:bolder"  onclick="termsLocal('termsStarPhone');">${terms_starPhone} </a>
                         </c:if>
                          <c:if test="${terms_notCoolpad7251_flag=='1'}">
                         	<input type="radio" name="radio_tiaokuan_info" id="a7" value="termsNotCoolpad7251" />  
                            <a href="#" style="text-decoration:underline;color:#FF0000;font-weight:bolder"  onclick="termsLocal('termsNotCoolpad7251');">${terms_notCoolpad7251} </a>
                         </c:if>
                          <c:if test="${terms_coolpad7251_flag=='1'}">
                         	<input type="radio" name="radio_tiaokuan_info" id="a8" value="termsCoolpad7251" />  
                            <a href="#" style="text-decoration:underline;color:#FF0000;font-weight:bolder"  onclick="termsLocal('termsCoolpad7251');">${terms_coolpad7251} </a>
                         </c:if>
                          <c:if test="${terms_threeDiscount_flag=='1'}">
                         	<input type="radio" name="radio_tiaokuan_info" id="a9" value="termsThreeDiscount" />  
                            <a href="#" style="text-decoration:underline;color:#FF0000;font-weight:bolder"  onclick="termsLocal('termsThreeDiscount');">${terms_threeDiscount} </a>
                         </c:if>
                         </td>                       
                     </div>
                   </tr>
                   <c:if test="${province_code =='gx' }">
                   <tr>
						<td height="24" align="left">同意签署协议前，请仔细阅读协议条款。根据《中华人民共和国电子签名法》，该电子协议由中国联合网络通信有限公司广西壮族自治区分公司以电子存档方式保存，具备法律效力。</td>
                   </tr>
                   </c:if>
                   <c:if test="${province_code =='cq' }">
                   <tr>
						<td height="24" align="left">同意签署协议前，请仔细阅读协议条款。根据《中华人民共和国电子签名法》，该电子协议由中国联合网络通信有限公司重庆分公司以电子存档方式保存，具备法律效力。</td>
                   </tr>
                   </c:if>
              </table>
              <div class="line line_per"></div>
            <%--   <div class="elc" id="tiaokuanConfirm">
              	<c:if test="${province_code  != 'cq' }">              	
              	<a href="###" onClick="apweb();">同意协议及电子签名</a>
              	<!-- <a href="###" class="view_btn" id="btnDaYin" onclick="payment();">免填单打印</a> -->
              	</c:if>
              </div> --%>  
               
              <li class="list_white">            	
                <div class="left" id="tiaokuanConfirm">               
                <c:if test="${province_code  != 'cq' }">
              		<a href="###"><div class="input_button_long"  onClick="apweb(1);">同意协议及电子签名</div></a> 
              	<!-- <a href="###" class="view_btn" id="btnDaYin" onclick="payment();">免填单打印</a> -->
              	</c:if>                          	            	
            	<c:if test="${province_code  == 'gx' }">
              	 	<a href="###"><div class="input_button_long" id="reSign" onClick="apweb(2);">重签</div></a>          	
              	</c:if>             	                               	                       
                </div>
            </li>
           
           </form>
      </div>
        
    	<div class="order_info_title" id="feiyong_xinxi">
        <a href="###" class="down"  id="shenSuoFeiYong">收缩</a><span id="xuhao2">3</span> 费用信息<!--如果是展开，请用：<a href="###" class="">展开</a>-->
        </div>
   	<div class="box" id="box3">
   	  <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
   	        <c:forEach var="infoOrderFeeDetailVo" items="${feeList}" varStatus="i">
                  <tr>
                    <td width="300" height="24" align="left">${infoOrderFeeDetailVo.fee_name }</td>
                    <td width="100" align="center">:</td>
                    <td width="788">${infoOrderFeeDetailVo.real_fee }元</td>
                  </tr>
              </c:forEach>
                  <tr>
                    <td height="24" width="300" align="left">合计</td>
                     <td width="100" align="center">:</td>
                    <td>${totalFee}元 <span class="red" id="getFee"></span></td>
                  </tr>
                  <tr id="payCsId" style="display:none;">
                    <td height="24" width="300" align="left">支付宝订单号</td>
                     <td width="100" align="center">:</td>
                    <td>${pay_cs_id}</td>
                  </tr>
      </table>
      <div class="line line_per" id="linePer"></div>
      <ul class="pay" id="pay">
      	<li>支付方式： </li>
      	<li><ul id="payTypePc">
      	<t:select id="pay_type_2G" codeType="pay_type_2G" ></t:select>
      	<t:select id="pay_type_3G" codeType="pay_type_3G" ></t:select>
      	<t:select id="pay_type_4G" codeType="pay_type_4G" ></t:select></ul></li>
      	  <li>
      	  	<ul id="payTypeMobile">
      	<t:select id="pay_type_mobile" codeType="pay_type_mobile" ></t:select></td></ul></li>
        <!--  <li><select id="" class="money">
        	<option>坐席收现</option>
        	<option>POS机刷卡</option>
        	<option>支付宝扫码支付</option>
        	<option>微信支付</option>
        	<option>营业电子交款(员工)</option>
        </select></li>-->
        <li><a href="###" onclick="dealFee();" class="view_btn" id="btnGetFee">收费</a></li>
        <li><a href="###" class="view_btn" id="btnDaYinBillData" onclick="paymentOrderBill();">收据打印</a></li>
        <div class="clear"></div>
      </ul>
      <div style="display: none;" class="out_div">
		<img class="pic-idPic" src='' /><div style='width:100%;text-align:left;'><span style='color:#F00;font-size:20px'><span id="PayTips">手机支付宝扫描二维码支付</span><button id="closetwocode" >关闭</button></span></div>
	  </div>
   	</div>
        
		<div class="order_info_title">
        <a href="###" class="down" id="shenSuoKa">收缩</a><span id="xuhao3">4</span> USIM卡<!--如果是展开，请用：<a href="###" class="">展开</a>-->
        </div>
        <div class="box" id="box4">
          <ul class="usim">
          	<li id="whiteOrAdult" style="display:none;">
          		<input type="radio" name="cardRadio" id="whiteCard" checked="checked">白卡
          		<input type="radio" name="cardRadio" id="adultCard">成卡
          	</li>
          	<div id="whiteCardDiv">
           <c:if test="${order_status=='A20'||order_status=='A30'}">  
           <li><input type="text"  id="resourcesCode" value="${card_number}" class="text" readonly="readonly"  /></li>
             </c:if>
            <c:if test="${order_status!='A20'&&order_status!='A30'&&writeWay=='0'}">  
             <li><input type="text" value="请读卡" id="resourcesCode" onfocus="this.value=''" onblur="if(this.value==''){this.value='请读卡'}"  class="text" /></li>
             </c:if>
             <c:if test="${order_status!='A20'&&order_status!='A30'&&writeWay!='0'}">
            <li><input type="text" value="请读卡" id="resourcesCode" onfocus="this.value=''" onblur="if(this.value==''){this.value='请读卡'}" readonly="readonly" class="text" /></li>
             </c:if>
            <li><a href="###" class="view_btn"  id="readCard" >读卡</a> <a href="###" id="writeCard"  class="view_btn">一键写卡</a><!-- <a href="javascript:;" id="alipayrefunda"  class="view_btn">支付宝支付退款</a> --></li>
            </div>
            <div id="adultCardDiv" style="display: none;">
            	<li>
            		<input type="text"  id="adultCardCode" onfocus="this.value=''"  onblur="if(this.value==''){this.value='请输入成卡卡号'}" value="请输入成卡卡号" class="text" />
            	</li>
            	<li>
            		<a href="javascript:;" class="view_btn"  id="readAdultCard" >读卡</a> <a href="javascript:;" id="adultWriteCard"  class="view_btn">成卡开户</a>
            	</li>
            </div>
            <div class="clear"></div>
          </ul>
      </div>   
        
        
<!--  		<div class="order_info_title"> -->
<!--         <a href="###" class="down" id="shenSuoZhiFu">收缩</a><span id="xuhao4">5</span> 交付方式如果是展开，请用：<a href="###" class="">展开</a> -->
<!--         </div> -->
<!--         <div class="box" id="box5"> -->
<!--           <ul class="usim"> -->
<%--           <c:choose> --%>
<%--             <c:when  test="${give_type=='1'}"> --%>
<!--           	<li> -->
<!--           	  <input type="radio" name="radio" id="b" value="b" checked="checked"  /> -->
<!--           	  <label for="b">前台</label> -->
<!--        	    </li> -->
<!--             <li><input type="radio" name="radio" id="b2" value="b" /> -->
<!--           	  <label for="b2">快递 （只支持EMS或顺丰货到付款方式）</label></li> -->
<!--             <li class="indent" id="indent1" style="display:none"><input type="text" id="receivePhone" value="请输入收货人号码"  onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入收货人号码'}" class="text short_text" /><input type="text" id="receiveAddress" value="请输入收货人地址" onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入收货人地址'}"  class="text" /></li> -->
<!--             <li><input type="radio" name="radio" id="b3" value="b" /> -->
<!--           	  <label for="b3">择日自提</label></li> -->
<%--             <li class="indent"  id="indent2" style="display:none"> <t:date id="end_time" name="end_time" minDateElId="start_time"  value="<%=DateUtil.getCurrentDate() %>"  minDate="<%=DateUtil.getCurrentDate()%>"  ></t:date> --%>
<!--             </li> -->
<%--              </c:when> --%>
<%--                <c:when test="${give_type=='2'}"> --%>
<!--           	<li> -->
<!--           	  <input type="radio" name="radio" id="b" value="b"   /> -->
<!--           	  <label for="b">前台</label> -->
<!--        	    </li> -->
<!--             <li><input type="radio" name="radio" id="b2" value="b" checked="checked"/> -->
<!--           	  <label for="b2">快递 （只支持EMS或顺丰货到付款方式）</label></li> -->
<%--             <li class="indent" id="indent1" ><input type="text" value="${receive_phone}" id="receivePhone"  class="text short_text" /><input type="text" id="receiveAddress"  value="${receive_address}"  class="text" /></li> --%>
<!--             <li><input type="radio" name="radio" id="b3" value="b" /> -->
<!--           	  <label for="b3">择日自提</label></li> -->
<%--             <li class="indent"  id="indent2" style="display:none"> <t:date id="end_time" name="end_time" minDateElId="start_time"  value="<%=DateUtil.getCurrentDate() %>"  minDate="<%=DateUtil.getCurrentDate()%>"  ></t:date> --%>
<!--             </li> -->
<%--              </c:when> --%>
<%--                <c:when test="${give_type=='3'}"> --%>
<!--           	<li> -->
<!--           	  <input type="radio" name="radio" id="b" value="b"   /> -->
<!--           	  <label for="b">前台</label> -->
<!--        	    </li> -->
<!--             <li><input type="radio" name="radio" id="b2" value="b" /> -->
<!--           	  <label for="b2">快递 （只支持EMS或顺丰货到付款方式）</label></li> -->
<!--             <li class="indent" id="indent1" style="display:none"><input type="text" id="receivePhone" value="请输入收货人号码" onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入收货人号码'}"  class="text short_text" /><input type="text" id="receiveAddress" value="请输入收货人地址" onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入收货人地址'}"  class="text" /></li> -->
<!--             <li><input type="radio" name="radio" id="b3" value="b" checked="checked"/> -->
<!--           	  <label for="b3">择日自提</label></li> -->
<%--             <li class="indent"  id="indent2"> <t:date id="end_time" name="end_time" minDateElId="start_time"  value="${collect_date}"  minDate="<%=DateUtil.getCurrentDate()%>"  ></t:date> --%>
<!--             </li> -->
<%--              </c:when> --%>
<%--              <c:otherwise>  --%>
<!--              	<li> -->
<!--           	  <input type="radio" name="radio" id="b" value="b" checked="checked"  /> -->
<!--           	  <label for="b">前台</label> -->
<!--        	    </li> -->
<!--             <li><input type="radio" name="radio" id="b2" value="b" /> -->
<!--           	  <label for="b2">快递 （只支持EMS或顺丰货到付款方式）</label></li> -->
<!--             <li class="indent" id="indent1" style="display:none" ><input type="text" id="receivePhone" value="请输入收货人号码" onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入收货人号码'}"  class="text short_text" /><input type="text" id="receiveAddress" value="请输入收货人地址" onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入收货人地址'}"  class="text" /></li> -->
<!--             <li><input type="radio" name="radio" id="b3" value="b" /> -->
<!--           	  <label for="b3">择日自提</label></li> -->
<%--             <li class="indent"  id="indent2" style="display:none"> <t:date id="end_time" name="end_time" minDateElId="start_time"  value="<%=DateUtil.getCurrentDate() %>"  minDate="<%=DateUtil.getCurrentDate()%>"  ></t:date> --%>
<!--             </li> -->
<%--              </c:otherwise>  --%>
<%--            </c:choose> --%>
<!--           </ul> -->
<!--     	</div>  -->
    	<!-- 新增默认业务包 20151208 -->  
<%--        <c:if test="${dinner_service !=null && dinner_service !='' }">   --%>
<!--         <div class="order_info_title"> -->
<!--         <a href="###" class="down" id="shenSuoDinner">收缩</a><span id="xuhao5">6</span> 业务部默认增加如果是展开，请用：<a href="###" class="">展开</a> -->
<!--         </div> -->
<!--         <div class="box" id="box6"> -->
<!--         	  <table width="874" border="0" align="center" cellpadding="0" cellspacing="0"> -->
<!--         		<tr> -->
<!--         		 <td height="24" width="86" align="right">业务包名称：</td> -->
<%--         		 <td id="dinnerService">${dinner_service }</td> --%>
<!--         		</tr> -->
<!--         	</table> -->
<!--         	<ul class="usim"> -->
<%--         	 <li><a href="###" id="dinnerConfirm" class="view_btn" onclick="tarrifChangeOrderSubmit('${order_status}');">确定</a></li> --%>
<!--        		</ul> -->
<!--         </div> -->
<%--          <input type="hidden" id = "pkg_code" value="${pkg_code }" />  --%>
<%--          <input type="hidden" id = "pkg_name" value="${dinner_service }" />  --%>
<%--         </c:if> --%>

    	 <c:if test="${teleType=='2G'||teleType=='3G'}">        
        <div class="order_info_title">
        <a href="###" class="down" id="shenSuoAc">收缩</a><span id="xuhao4">5</span> 参与活动<!--如果是展开，请用：<a href="###" class="">展开</a>-->
        </div>
        <div class="box" id="box7">
          <ul class="usim">
           
            <li>
            <c:choose>
            	<c:when test="${code_mkt_markets!=''||code_mkt_feeback!=''}">
            		<c:if test="${code_mkt_markets!=''}">
    					<input type="checkbox" id="mkt2" checked="checked" disabled="disabled"/>
    					<label for="mkt2">营销活动</label>
    					&nbsp; &nbsp; &nbsp;
    				</c:if>
    				<c:if test="${code_mkt_feeback!=''}">
    					<input type="checkbox" id="backMKt2" checked="checked" disabled="disabled"/>
    					<label for="backMKt2">活动退费</label>
    					&nbsp; &nbsp; &nbsp;
    				</c:if>
            	</c:when> 
            	<c:otherwise>
            	<input type="radio" name="noAc" id="noAc" checked="checked" value="noAc" onclick="docheckdBox('noAc')" />
              	<label for="noAc">不参与</label>
            	&nbsp; &nbsp; &nbsp;
            	<input type="checkbox" name="backMKt" id="backMKt" checked="true" value="backMKt" onclick="docheckdBox('backMKt')" />
             	<label for="backMKt">活动退费</label>
            	&nbsp; &nbsp; &nbsp;
            	<input type="checkbox" name="mkt" id="mkt" value="mkt" onclick="docheckdBox('mkt')" />
            	<label for="mkt">营销活动</label>
    			&nbsp; &nbsp; &nbsp;
            	</c:otherwise> 
            </c:choose>
            
    	<c:if test="${teleType=='3G'}">        
            <input type="checkbox" name="x99" id="x99" value="x99" onclick="docheckdBox('x99')" />
            <label for="x99">3G X99合约</label>
         </c:if>    
            </li>
           <li id="backTag" name="backTag" style="display:none">
                                   活动退费:&nbsp;
           <select type="select" name="backMKtS" id="backMKtS" onchange="change('backMKtS');">
           <option value="" selected="selected">请选择--</option>
           </select>
           </li>
           <li id="mktTag" name="mktTag" style="display:none">
                                      营销活动:&nbsp;
           <select type="select" name="mktS" id="mktS" onchange="change('mktS');">
           <option value="" selected="selected">请选择--</option>
           </select>
           </li>
           <c:if test="${code_mkt_markets!=''}">
           	<li id="mktTag2" name="mktTag">
                                      营销活动:&nbsp;
           <select type="select" name="mktS" id="mktS2"  disabled="disabled">
           <option value=${code_mkt_markets} selected="selected">${code_name_markets}</option>
           </select>
           </li>
           </c:if>
           <c:if test="${code_mkt_feeback!=''}">
           <li id="backTag2" name="backTag">
                                   活动退费:&nbsp;
           <select type="select" name="backMKtS" id="backMKtS2" disabled="disabled">
           <option value=${code_mkt_feeback} selected="selected">${code_name_feeback}</option>
           </select>
           </li>
           </c:if>
           <li id="imeiTag" name="imeiTag" style="display:none">
                                      手机串号:&nbsp;<input name="imei" id="imei">&nbsp;<a id="imeiBtn" href="javascript:void(0)" class="view_btn"  onclick="checkIMEI();">校验</a>
           </li>
           <li id="dangweiTag" name="dangweiTag" style="display:none">                      
                                       选择档位:&nbsp;
           	<select type="select" name="x99levels" id="x99levels" style="width:300px" onchange="change('x99levels');">
            	<option value="" selected="selected">请选择--</option>   
          	 </select> 
           </li>
           <li id="priceSelectTag" name="priceSelectTag" style="display:none">                   
                                      选择价格:&nbsp;
           	<select type="select" name="priceSelect" id="priceSelect" style="width:300px" onchange="">
            	<option value="" selected="selected">请选择--</option>  
          	 </select>
          	</li>
           <li id="doAc" style="display:none">          
            <a href="javascript:void(0)" class="view_btn"  onclick="doXyAction();">完成</a> 
           </li>
            <div class="clear"></div>
          </ul>
      </div>   
      </c:if>  
      <!--YUN-744   NX_代理商开户/缴费佣金功能 -->
      <c:if test="${not empty comm_fee}">
		<div class="order_info_title">
        <a href="###" id="shenSuoCommission" class="down">收缩</a><span id="xuhaoCommission"></span> 佣金信息<!--如果是展开，请用：<a href="###" class="">展开</a>   如果是收缩：<div class="order_info_title order_info_boder"> 即追加：order_info_boder样式。<div class="box">追加hide()方法或display:none;即可-->
        </div>
		<div class="box" id="boxCommission">             
            <h2>佣金信息</h2>
            <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="74" height="24" align="right"><span class="dashed">佣金金额：</span></td>
                  <td width="800">${comm_fee} 元</td>
                </tr>             
            </table>
            <div class="line"></div>            
        </div>
		</c:if>
  </div>
    
    
    
    
  <div style="display:none" class="ok" id="okModule"><a href="###" id="okSubmit" onclick="okSumbit()">完成</a></div>
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
<OBJECT id="umsocxId" height=0 width=0 classid=clsid:5099c9c4-beca-44fe-a42c-8656fbb5a0f3></OBJECT>
<script type="text/javascript" src="<%=fullPath%>js/showOrder.js"></script>
<script type="text/javascript" src="<%=fullPath%>js/writeCard.js"></script>
<script type="text/javascript" src="<%=fullPath%>js/posPayFee.js"></script>
<script type="text/javascript">
	var umsocx;
</script>

<div class="copyright">
Copyright © 2014 China unicom All Right Reserved
</div>

</body>
</html>
