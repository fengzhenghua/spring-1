
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@include file="../common/common.jsp"%>
 <%@ page import="com.tydic.unicom.util.DateUtil" %>
 <%@page import="org.jasig.cas.client.util.AssertionHolder"%>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单详情</title>
<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/showOrder.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/showWoOrder.js"></script>
<script type="text/javascript" src="<%=fullPath%>js/writeCard.js"></script>
<script type="text/javascript" src="<%=fullPath%>js/posPayFee.js"></script>
<script type="text/javascript">
	var umsocx;
</script>
</head>

<body onload="initPos()">
 <input type="hidden"  id="progress"  value="${progress}"/><!--记录订单详情进度 -->
  <input type="hidden"  id="payFlag"  value="${payFlag}"/><!--收费标示 --> 
  <input type="hidden"  id="payType"  value="${payType}"/><!--是pos机刷卡还是坐席收费标志--> 
  <input type="hidden"  id="acc_nbr"  value="${master_acc_nbr}"/><!--用户号码 -->   
  <input type="hidden"  id="order_id"  value="${orderId}"/><!--订单id-->   
  <input type="hidden" id="tele_type" value="${teleType}" />  
  <input type="hidden" id = "writeWay" value="${writeWay}" /><!--模拟写卡还是正式写卡方式0代表模拟写卡1代表正式写卡-->
 <input type="hidden"  id="prepayFlag"  value="1"/>
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
 <input type="hidden"  id="apweb_value"  value="1"/> <!--协议及电子签名序号-->
 <input type="hidden"  id="order_sub_type"  value="${order_sub_type}"/><!--订单类型--> 
 <input type="hidden"  id="id_type"  value="${idTypeCard}"/>
 <input type="hidden"  id="end_open"  value="${end_open}"/>
 <input type="hidden"  id="province_code"  value="${province_code}"/>
 <input type="hidden"  id="write_nubmer"  value="${writeCakList}"/>
 <input type="hidden"  id="wo_type"  value="${wo_type}" /><!-- 宽带单装 -->
 <input type="hidden"  id="termsInnetFlag"  value="${termsInnetFlag}"/><!--入网协议   -->
 <input type="hidden"  id="termsGoodFlag"  value="${termsGoodFlag}"/><!--靓号协议-->
 <input type="hidden"  id="termsPreferentialFlag"  value="${termsPreferentialFlag}"/><!--优惠协议  -->
 <input type="hidden"  id="termsWjtFlag"  value="${termsWJTFlag}"/><!--沃家庭协议  -->
 <input type="hidden"  id="termsLanFlag"  value="${termsLanFlag}"/><!--宽带庭协议  -->
 
 <input type="hidden"  id="wo_fa_phone_number"  value="${wo_fa_phone_number}"/><!--沃家庭旧手机号-->
 <input type="hidden"  id="wo_fa_lan_number"  value="${wo_fa_lan_number}"/><!--沃家庭旧宽带业务号码-->
 <input type="hidden"  id="is_writecar"  value="${is_writecar}"/><!--智慧沃家是否有写卡号码-->
 <input type="hidden"  id="wo_writed_number"  value="${wo_writed_number}"/><!--智慧沃家已写卡号码-->
  
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
        <table width="860" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <c:if test="${wo_type == '0' || wo_type == '1'}"><td height="40" colspan="6"><div class="progress_1"><img id="sytleProgress" src="<%=fullPath%>images/five_long_3.png" width="862" height="40" /></div><!--ps:原始为第一状态，以后为：<div class="progress_1 progress_2"></div> 即每个状态追加1，共到5--></td>         	
            </c:if>            
         	<c:if test="${wo_type == '2'||wo_type == '3'||wo_type == '4'}"><td height="40" colspan="6"><div class="progress_1"><img id="sytleProgress" src="<%=fullPath%>images/four_long_3.jpg" width="862" height="40" /></div></td>
         	</c:if>
          </tr>
				<c:if test="${(wo_type == '0'&&is_writecar!='0') || (wo_type == '1'&&is_writecar!='0')||(wo_type == '3'&&wo_fa_phone_number=='')||(wo_type == '3'&&wo_fa_phone_number==null)}">
					<tr>
						<td width="134" height="20" align="left">&nbsp;</td>
						<td width="103" align="left">开户</td>
						<td width="201" align="center">待收费</td>
						<td width="137" align="center">待写卡</td>
						<td width="205" align="center">开户完成</td>
						<td width="83" align="right">竣工</td>
					</tr>
				</c:if>

				<c:if test="${wo_type == '2'||is_writecar=='0'||(wo_type == '3'&&wo_fa_phone_number!=''&&wo_fa_phone_number!=null)||wo_type == '4'}">
					<tr>
						<td width="200" height="20" align="left">&nbsp;</td>
						<td width="103" align="left">开户</td>
						<td width="260" align="center">待收费</td>						
						<td width="190" align="center">开户完成</td>
						<td width="150" align="right">竣工</td>
					</tr>
				</c:if>
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
                
             <c:if test="${wo_type == '0' || wo_type == '1'}"><h2>智慧沃家套餐</h2></c:if>
             <c:if test="${wo_type == '2' }"><h2>宽带套餐</h2></c:if>
              <c:if test="${wo_type == '3' }"><h2>沃家庭套餐信息</h2></c:if>
              <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
              <c:if test="${wo_type == '0' || wo_type == '1' }">
                <tr>
                  <td width="86" height="24" align="right"><span class="dashed">共享流量包：</span></td>
                  <td width="800">${flow_package }</td>
                </tr>
                </c:if>
                 <c:if test="${wo_type == '3'}">
                <tr>
                  <td width="86" height="24" align="right"><span class="dashed">沃家庭套餐：</span></td>
                  <td width="800">${wojttaocanIdName }</td>
                </tr>
                </c:if>
                  <tr>
                    <td height="24" align="right"><span class="dashed">宽带套餐：</span></td>
                    <td>${taocanName}</td>
                  </tr>
                  <tr>
                    <td height="24" align="right"><span class="dashed">号码套餐：</span></td>
                    <td>${product_name}</td>
                  </tr>
                  <tr>
                    <td height="24" align="right"><span class="dashed">活动类型：</span></td>
                    <td>${activity_type_name}</td>
                  </tr>
                  <tr>
                    <td height="24" align="right"><span class="dashed">可选活动：</span></td>
                    <td>${activity_name}</td>
                  </tr>
              </table>
              <div class="line"></div>
    
           <h2>宽带资料</h2>
              <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="86" height="24" align="right">业务号码：</td>
                  <td width="800">${lanywNumber}</td>
                </tr>
                  <tr>
                    <td height="24" align="right">上网账号：</td>
                    <td>${onlineNmber}</td>
                  </tr>
                  
                  <tr>
                    <td height="24" align="right">装机地址：</td>
                    <td>${installAddress}</td>                    
                  </tr>
                  <tr>
                     <td width= "86"  height ="24" align="right"> 详细地址：</td >
                    <td> ${addressDetailAdd}</td >
                  </tr>
                  
                   <!-- 
                  <tr>
                    <td height="24" align="right">接入方式名称：</td>
                    <td>${lanType}</td>
                  </tr>
                  <tr>
                    <td height="24" align="right">终端提供方式：</td>
                    <td>免费使用</td>
                  </tr>
                  <tr>
                    <td height="24" align="right">终端类型：</td>
                    <td>DSL MODEM</td>
                  </tr>
                   -->
              </table>
              <div class="line"></div>
     <h2>联系方式</h2>
            <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="42" height="24" align="right"><span class="dashed">联系人</span>：</td>
                <td width="376">${lancustomerName}</td>
              </tr>
              <tr>
                <td width="42" height="24" align="right"><span class="dashed">联系电话</span>：</td>
                <td width="376">${phoneNumber}</td>
              </tr>
            </table>
            <div class="line"></div>
            <h2>用户号码</h2>
            <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="42" height="24" align="right"><span class="dashed">用户号码</span>：</td>
                 <c:if test="${wo_type == '4'||wo_type == '2'}">
                   <td width="386">${accNbr}</td>
                 </c:if>
                  <c:if test="${wo_type == '0'||wo_type == '1'||wo_type == '3'}">
                    <td width="386">${master_acc_nbr}</td>
                 </c:if>
              </tr>
              <tr>
                <td width="42" height="24" align="right"><span class="dashed">首次预存</span>：</td>
                <td width="386">${master_nbr_fee }元 </td>
              </tr>
              <tr>
                <td width="42" height="24" align="right"><span class="dashed">月承诺消费</span>：</td>
                <td width="386">${master_low_fee}元 </td>
              </tr>
            </table>
        </div>
    	<div class="order_info_title" id="tiaokuan_info">
        <a href="###" class="down" id="shenSuoTiaoKuan">收缩</a><span id="xuhao1">2</span> 条款协议<!--如果是展开，请用：<a href="###" class="">展开</a>-->
        </div>
        <div class="box" id="box2">
        	<form action="" method="">
              <table width="874" border="0" align="center" cellpadding="0" cellspacing="0">
                 
                   
                   <tr>
                     <div>
                         <td height="24" align="left">我已阅读并同意                          		
                         <c:if test="${termsInnetFlag=='1'&& wo_type != '2' && province_code != 'cq'}"> 
                         	<input name="radio_tiaokuan_info" type="radio" id="a1" value="innet" checked="checked" /> 
                            <a href="#" style="text-decoration:underline;color:#FF0000;font-weight:bolder"  onclick="payment();"> ${termsInnet} </a>
                         </c:if>
                         <c:if test="${termsInnetFlag=='1'&& wo_type != '2' && province_code == 'cq'}"> 
                         	<input name="radio_tiaokuan_info" type="radio" id="a1" value="innet" checked="checked" /> 
                            <a href="#" style="text-decoration:underline;color:#FF0000;font-weight:bolder"  onclick="openWojia();"> ${termsInnet} </a>
                         </c:if>
                         <c:if test="${termsInnetFlag=='1'&& wo_type == '2'}">  
                         	<input type="radio" name="radio_tiaokuan_info" id="a3" value="innet" />
                            <a href="#" style="text-decoration:underline;color:#FF0000;font-weight:bolder"  onclick="m165Acc();">${termsInnet}</a>
                         </c:if>   
                         <c:if test="${termsGoodFlag=='1'}">
                         	<input type="radio" name="radio_tiaokuan_info" id="a2" value="good" />  
                            <a href="#" style="text-decoration:underline;color:#FF0000;font-weight:bolder"  onclick="termsGood();">${termsGood} </a>
                         </c:if>
                         <c:if test="${termsM165Flag=='1'}">  
                         	<input type="radio" name="radio_tiaokuan_info" id="a3" value="m165" />
                            <a href="#" style="text-decoration:underline;color:#FF0000;font-weight:bolder"  onclick="termsM165();">${termsM165} </a>
                         </c:if>
                                                
                        <c:if test="${termsLanFlag=='1'}">  
                         	<input type="radio" name="radio_tiaokuan_info" id="a5" value="m165" />
                            <a href="#" style="text-decoration:underline;color:#FF0000;font-weight:bolder"  onclick="m165Acc();">${termsLan} </a>
                         </c:if>
                         <c:if test="${termsPreferentialFlag=='1'}">
                         	<input type="radio" name="radio_tiaokuan_info" id="a4" value="preferential" />  
                            <a href="#" style="text-decoration:underline;color:#FF0000;font-weight:bolder"  onclick="termsPreferential();">${termsPreferential} </a>
                         </c:if>
                         
                         <c:if test= "${termsWJTFlag=='1' || termsWJTAllFlag=='1'}">
                            <input type= "radio" name ="radio_tiaokuan_info" id="a6" value="wojt" checked= "checked" />  
                            <a href= "#" style="text-decoration :underline;color:#FF0000; font-weight:bolder "  onclick="termsWjt();" >${termsWJT} </a>
                         </c:if>
                          <c:if test= "${woTV_terms_flag=='1'}">
                            <input type= "radio" name ="radio_tiaokuan_info" id="a6" value="wo_tv" checked= "checked" />  
                            <a href= "#" style="text-decoration :underline;color:#FF0000; font-weight:bolder "  onclick="woTVAcc();" >${woTV_terms_name} </a>
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
              <div class="elc">
              	<c:if test="${province_code !='cq' }">
              	<a href="###" onClick="apweb();">同意协议及电子签名</a>
              	<!-- <a href="###" class="view_btn" id="btnDaYin" onclick="payment();">免填单打印</a> -->
              	</c:if>
              </div>              
           </form>
      </div>
        
    	<div class="order_info_title" >
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
      </table>
      <div class="line line_per" id="linePer"></div>
      <ul class="pay" id="pay">
      	<li>支付方式： </li>
      	<li><ul id="payTypePc">
      		<t:select id="pay_type_LAN" codeType="pay_type_LAN" ></t:select>
      	</ul></li>
      	    <li>
      	  	<ul id="payTypeMobile">
      	<t:select id="pay_type_mobile" codeType="pay_type_mobile" ></t:select></td></ul></li>       	   
      	<c:if test="${wo_fa_phone_number!=null&&wo_fa_phone_number!=''}">  
         <li><a href="###" onclick="dealFee();" class="view_btn" id="btnGetFee">沃家庭收费</a></li>
        </c:if>
        <c:if test="${wo_fa_phone_number==null||wo_fa_phone_number==''}">  
         <li><a href="###" onclick="dealFee();" class="view_btn" id="btnGetFee">收费</a></li>
        </c:if>
        <li><a href="###" class="view_btn" id="btnDaYinBillData" onclick="paymentOrderBill();">收据打印</a></li>
        <div class="clear"></div>
      </ul>
   	</div>
      <div class="order_info_title" id="usim_title">
        <a href="###" class="down" id="shenSuoKa">收缩</a><span id="xuhao3">4</span> USIM卡<!--如果是展开，请用：<a href="###" class="">展开</a>-->
        </div>        
        <div class="box" id="box4">
          <ul class="usim">
           
           <c:if test="${order_status=='A30'}">  
           <li><input type="text"  id="resourcesCode" value="${card_number}" class="text"   />
             <lable id="number_list_tag">
            &nbsp;&nbsp;写卡手机号:&nbsp;
	           <select type="select" id="number_list" name="number_list" style="width:150px">
	            <option value="" selected="selected">请选择--</option>
	           </select>
	           </lable>
	           </li>
             </c:if>
            <c:if test="${order_status!='A20'&&order_status!='A30'&&writeWay=='0'}">  
             <li><input type="text" value="请读卡" id="resourcesCode" onfocus="this.value=''" onblur="if(this.value==''){this.value='请读卡'}"  class="text" />
              <lable id="number_list_tag">
              &nbsp;&nbsp;写卡手机号:&nbsp;
	           <select type="select" id="number_list" name="number_list" style="width:150px" >
	            <option value="" selected="selected">请选择--</option>
	           </select>
	           </lable>
	           </li>
             </c:if>
             <c:if test="${order_status!='A20'&&order_status!='A30'&&writeWay!='0'}">
            <li><input type="text" value="请读卡" id="resourcesCode" onfocus="this.value=''" onblur="if(this.value==''){this.value='请读卡'}" readonly="readonly" class="text" />
              <lable id="number_list_tag">&nbsp;&nbsp;写卡手机号:&nbsp;
	           <select type="select" id="number_list" name="number_list" style="width:150px" >
	            <option value="" selected="selected">请选择--</option>
	           </select></lable>
            </li>
             </c:if> 
             <li><a href="###" class="view_btn"  id="readCard" >读卡</a> <a href="###" id="writeCard"  class="view_btn">一键写卡</a> <!-- <a href="###" id="sumbwjt"  class="view_btn">沃家庭收费</a> --></li>
                                   
            <div class="clear"></div>
          </ul>
      </div>           
 		<div class="order_info_title">
        <a href="###" class="down" id="shenSuoZhiFu">收缩</a>
        <c:if test="${wo_type == '0' || wo_type == '1'}"><span id="xuhao3">5</span> </c:if>
        <c:if test="${wo_type == '2' }"><span id="xuhao3">4</span> </c:if>
                交付方式<!--如果是展开，请用：<a href="###" class="">展开</a>-->
        </div>
        <div class="box" id="box5">
          <ul class="usim">
          <c:choose>
            <c:when  test="${give_type=='1'}">
          	<li>
          	  <input type="radio" name="radio" id="b" value="b" checked="checked"  />
          	  <label for="b">前台</label>
       	    </li>
            <li><input type="radio" name="radio" id="b2" value="b" />
          	  <label for="b2">快递 （只支持EMS或顺丰货到付款方式）</label></li>
            <li class="indent" id="indent1" style="display:none"><input type="text" id="receivePhone" value="请输入收货人号码"  onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入收货人号码'}" class="text short_text" /><input type="text" id="receiveAddress" value="请输入收货人地址" onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入收货人地址'}"  class="text" /></li>
            <li><input type="radio" name="radio" id="b3" value="b" />
          	  <label for="b3">择日自提</label></li>
            <li class="indent"  id="indent2" style="display:none"> <t:date id="end_time" name="end_time" minDateElId="start_time"  value="<%=DateUtil.getCurrentDate() %>"  minDate="<%=DateUtil.getCurrentDate()%>"  ></t:date>
            </li>
             </c:when>
               <c:when test="${give_type=='2'}">
          	<li>
          	  <input type="radio" name="radio" id="b" value="b"   />
          	  <label for="b">前台</label>
       	    </li>
            <li><input type="radio" name="radio" id="b2" value="b" checked="checked"/>
          	  <label for="b2">快递 （只支持EMS或顺丰货到付款方式）</label></li>
            <li class="indent" id="indent1" ><input type="text" value="${receive_phone}" id="receivePhone"  class="text short_text" /><input type="text" id="receiveAddress"  value="${receive_address}"  class="text" /></li>
            <li><input type="radio" name="radio" id="b3" value="b" />
          	  <label for="b3">择日自提</label></li>
            <li class="indent"  id="indent2" style="display:none"> <t:date id="end_time" name="end_time" minDateElId="start_time"  value="<%=DateUtil.getCurrentDate() %>"  minDate="<%=DateUtil.getCurrentDate()%>"  ></t:date>
            </li>
             </c:when>
               <c:when test="${give_type=='3'}">
          	<li>
          	  <input type="radio" name="radio" id="b" value="b"   />
          	  <label for="b">前台</label>
       	    </li>
            <li><input type="radio" name="radio" id="b2" value="b" />
          	  <label for="b2">快递 （只支持EMS或顺丰货到付款方式）</label></li>
            <li class="indent" id="indent1" style="display:none"><input type="text" id="receivePhone" value="请输入收货人号码" onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入收货人号码'}"  class="text short_text" /><input type="text" id="receiveAddress" value="请输入收货人地址" onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入收货人地址'}"  class="text" /></li>
            <li><input type="radio" name="radio" id="b3" value="b" checked="checked"/>
          	  <label for="b3">择日自提</label></li>
            <li class="indent"  id="indent2"> <t:date id="end_time" name="end_time" minDateElId="start_time"  value="${collect_date}"  minDate="<%=DateUtil.getCurrentDate()%>"  ></t:date>
            </li>
             </c:when>
             <c:otherwise> 
             	<li>
          	  <input type="radio" name="radio" id="b" value="b" checked="checked"  />
          	  <label for="b">前台</label>
       	    </li>
            <li><input type="radio" name="radio" id="b2" value="b" />
          	  <label for="b2">快递 （只支持EMS或顺丰货到付款方式）</label></li>
            <li class="indent" id="indent1" style="display:none" ><input type="text" id="receivePhone" value="请输入收货人号码" onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入收货人号码'}"  class="text short_text" /><input type="text" id="receiveAddress" value="请输入收货人地址" onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入收货人地址'}"  class="text" /></li>
            <li><input type="radio" name="radio" id="b3" value="b" />
          	  <label for="b3">择日自提</label></li>
            <li class="indent"  id="indent2" style="display:none"> <t:date id="end_time" name="end_time" minDateElId="start_time"  value="<%=DateUtil.getCurrentDate() %>"  minDate="<%=DateUtil.getCurrentDate()%>"  ></t:date>
            </li>
             </c:otherwise> 
           </c:choose>
          </ul>
    	</div> 
    	      	         
  </div>
    
    
    
    
  <div class="ok" id="okModule"><a href="###" id="okSubmit" onclick="okSumbit()">完成</a></div>
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
