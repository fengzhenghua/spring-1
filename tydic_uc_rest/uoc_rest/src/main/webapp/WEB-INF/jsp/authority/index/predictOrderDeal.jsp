<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@include file="../common/common.jsp"%>
 <%@ page import="com.tydic.unicom.util.DateUtil" %>
 <%-- <%@ taglib prefix="t" uri="http://tydic.com/jsp/core" %> --%>
 <%@page import="org.jasig.cas.client.util.AssertionHolder"%>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单处理</title>

<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/pre_accept.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/share1.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/card.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/getIdWcfCard.js"></script>
<script type="text/javascript" src="<%=fullPath%>js/predictOrderDeal.js"></script>
<script type="text/javascript" src="<%=fullPath%>js/writeCard.js"></script>
</head>

<body>
 <input type="hidden" id = "imsi" value="" />
 <input type="hidden" id = "capacityTypeCode" value="" />
 <input type="hidden" id = "resKindCode" value="" />
 <input type="hidden" id = "procId" value="" /> 
 <input type="hidden" id = "activeId" value="" /> 
 <input type="hidden" id = "cardData" value="" /> 
 <input type="hidden" id="wcf_order_id"  value="${wcf_order_id}"></input>
 <!--  <input type="hidden" id = "sequence" value="${sequence}" /> 
 <input type="hidden" id = "Order_number" value="${Order_number}" /> 
 -->
 
<div class="show" style="height:auto;">
    <div class="info">
    	<div class="title text_big">
        	<span class="text_large24">1</span>订单编号
        </div>
		
        <ul >
        	<li class="list">
            	<div class="left">
            	<div class="left_data">
                    <div class="left_lable">订<span class="space4"></span>单<span class="space4"></span>编<span class="space4"></span>号：</div>
                    <div class="left_data" id="Order_number" style="">${Order_number}</div>
                   
                </div>
                  
                <!--  <input type="text" placeholder="沃创富订单号查询" id="wcf_order_id"></input>-->
               	
                   <!--  <div class="left_data"><a href="#" class="search" onClick="getWcfOrder();">查询</a></div>--> 
                </div>
            </li>
                                       
        </ul>
        <div class="padding_blank10"></div>
  </div> 
  
  <div id="card_con">
  <object classid="clsid:10946843-7507-44FE-ACE8-2B3483D179B7" id="CVR_IDCard" name="CVR_IDCard" width="0" height="0"></object>
  <div name="reader_context" id="reader_context">
 <div class="show_title_bg">
        <div class="show_title">证件详情</div>
   </div>
  <div class="card" >
    
    <table width="936" border="0" align="center" cellpadding="0" cellspacing="0" class="auto">
      <tr>
        <td width="110" height="40">选择身份证阅读器：</td>
        <td width="160">
        
    	<div class="div_float div_float_ie6">
    	    <select name="id_card_mech" id="id_card_mech" >
    	    	<option value ="crvu">通用</option>
  	        </select>
    	</div>
        
        </td>
        <td><a href="###" class="read" id="btn_load">读取</a>&nbsp;&nbsp;<a href="###" class="read" id="btn_ckeckback">返回</a></td>
       
      </tr>
    </table>
    
    <div id="control_idcard_info" ><!-- 控制身份信息隐藏的div 暂时没加上 -->
   	<div class="border">
    <table width="938" border="0" align="center" cellpadding="0" cellspacing="0" class="auto">
      <tr>
        <td width="468" align="center" style="border-right:1px dashed #CCC;"><table width="340" border="0" align="center" cellpadding="0" cellspacing="0" class="auto">
          <tr>
            <td height="216" valign="top" class="bg_f"><table width="340" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="24"></td>
              </tr>
            </table>
              <table width="340" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="55" height="26"><div style="width:55px;"></div></td>
                  <td width="81" height="26" align="left" id="bg_card_name"></td>
                  <td width="68" height="26"></td>
                  <td colspan="2" rowspan="4" valign="top" width="136" align="left"><img src="<%=fullPath%>images/photo.png" width="94" height="118" class="img" id="idCradImage"/></td>
                </tr>
                <tr>
                  <td width="55" height="26"></td>
                  <td height="26" align="left" id="bg_card_sex"></td>
                  <td height="26" align="left" id="bg_card_nation"></td>
                </tr>
                <tr>
                  <td width="55" height="26"></td>
                  <td height="26" colspan="2"><table width="149" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="57" align="left" id="bg_card_born_year"></td>
                      <td width="33" align="left" id="bg_card_born_month"></td>
                      <td width="59" align="left" id="bg_card_born_day"></td>
                    </tr>
                  </table></td>
                </tr>
                <tr>
                  <td width="55"></td>
                  <td height="61" colspan="2" valign="top" class="addr"><div style="width:149px;  height:64px; overflow:hidden;" id="bg_card_born_addrss"></div></td>
                </tr>
              </table>
              <table width="340" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="110" height="26">&nbsp;</td>
                  <td width="230" align="left" id="bg_card_born_id_number"></td>
                </tr>
              </table>
              <table width="340" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>&nbsp;</td>
                </tr>
              </table></td>
          </tr>
        </table></td>
        <td width="468" align="center"><table width="340" border="0" align="center" cellpadding="0" cellspacing="0" class="auto">
          <tr>
            <td height="216" valign="top" class="bg_r"><table width="340" border="0" cellspacing="0" cellpadding="0">
              <tr>
                  <td height="148">&nbsp;</td>
                </tr>
            </table>
              <table width="340" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="135" height="30">&nbsp;</td>
                  <td width="205" align="left" id="bg_card_idcard_addr"></td>
                </tr>
                <tr>
                  <td height="26">&nbsp;</td>
                  <td align="left" id="bg_card_valid"></td>
                </tr>
              </table></td>
          </tr>
        </table></td>
      </tr>
    </table>
    </div>
    <table width="938" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#e7e7e7"  class="auto auto_table">
      <tr>
        <td width="115" height="32" align="right" bgcolor="#f8f8f8">姓　　名：</td>
        <td width="350" bgcolor="#FFFFFF" id="customer_name"></td>
        <td width="115" align="right" bgcolor="#F8F8F8">性　　别：<br /></td>
        <td width="358" bgcolor="#FFFFFF" id="customer_sex"></td>
      </tr>
      <tr>
        <td height="32" align="right" bgcolor="#f8f8f8">民　　族：</td>
        <td bgcolor="#FFFFFF" id="nation_id"></td>
        <td align="right" bgcolor="#F8F8F8">出生日期：<br /></td>
        <td bgcolor="#FFFFFF" id="born_date_val"></td>
      </tr>
      <tr>
        <td height="32" align="right" bgcolor="#f8f8f8">住　　址：</td>
        <td bgcolor="#FFFFFF" id="auth_adress"></td>
        <td align="right" bgcolor="#F8F8F8">最新住址：<br /></td>
        <td bgcolor="#FFFFFF"></td>
      </tr>
      <tr>
        <td height="32" align="right" bgcolor="#f8f8f8">签发机关：</td>
        <td bgcolor="#FFFFFF" id="idcard_addr"></td>
        <td align="right" bgcolor="#F8F8F8">有 效 期：</td>
        <td bgcolor="#FFFFFF" id="valid"></td>
      </tr>
      <tr>
        <td height="32" align="right" bgcolor="#f8f8f8">公民身份证号码：</td>
        <td colspan="3" bgcolor="#FFFFFF" id="id_number"></td>
      </tr>
    </table>
    </div>
    <!---------------------------------------- 隐藏信息 ----------------------------------->
	<input type="hidden" name="messageFlag" value="0" id="messageFlag" >
	<input type="hidden" name="PhotoBuffer" id="photo_buffer_val">
	<input type="hidden" name="ActivityLFrom" id="start_date_val">
	<input type="hidden" name="ActivityLTo"id="end_date_val" >
	<input type="hidden" id="id_number_result" 		name="id_number" >			<!--身份证  -->
	<input type="hidden" id="id_addr_result" 		name="id_addr" >	<!--证件地址  -->
	<input type="hidden" id="id_police_result" 		name="id_police" >	<!--签发地址  -->
	<input type="hidden" id="custom_name_result" 	name="custom_name" >	<!--客户姓名-->
	<input type="hidden" id="custom_sex_result" 	name="custom_sex" >		<!--客户性别  -->
	<input type="hidden" id="custom_birth_result" 	name="custom_birt" >		<!--出生日期  -->
	<input type="hidden" id="custom_nation_result" 	name="custom_nation" >		<!--民族  -->
	<input type="hidden" id="start_date_result" 	name="start_date" >		<!--生效时间  -->
	<input type="hidden" id="end_date_result" 		name="end_date" >		<!--实效时间  -->
	<input type="hidden" id="photo_code_result" 	name="photo_code" >	<!-- 照片编码 -->
	<input type="hidden" id="id_card_image_path" 	name="id_card_image_path" value="<%=fullPath%>picture/idcard/idCardImag">
	<input type="hidden" id = "writeWay" value="${write_way}" /><!--模拟写卡还是正式写卡方式0代表模拟写卡1代表正式写卡-->
	<input type="hidden" id = "province_code" value="${province_code}" />
	
  </div>
   
  </div>
  </div>
  
     <div id="number" style="display:none;">
        	<!--<ul class="band_phone">
	            <div class="detail_white">
                	<li class="list_white">
                    	<div class="left">
			            </div>
                    </li>
                 </div>
            </ul>-->		
            <div class="detail_white">
                <div class="line_red_top"></div>
                <div class="list_white">
                    
                    <div class="select">
                        <form id="form1" name="form1" method="post" action="">
                        <div class="select_list">
                            <div class="div_float">
                            <!--      <a id="teleType2G" class="g g_current">2G</a><a class="g" id="teleType3G">3G</a><a class="g"  id="teleType4G" >4G</a>
                            -->
                            </div>
                            <div class="div_float div_float_ie6" id="showMob2G"> 
                                   <select id="mob_section" name = "mob_section">
                                   <option value="10000">号段选择</option>
                                   <option value="185">185</option>
                                   <option value="186">186</option>
                                   <option value="145">145</option>
                                   <option value="175">175</option>
                                   <option value="176">176</option>                                   
                                   <option value="156">156</option>
                                   <option value="155">155</option>
                                   <option value="132">132</option>
                                   <option value="131">131</option>
                                   <option value="130">130</option>
                                   </select>
                                </div>
                                
                           <div class="div_float div_float_ie6" id="show3G">
                               <t:select id="perrty_type_pc" name = "perrty_type_pc" codeType="perrty_type_pc" ></t:select>
                            </div>
                            
                            <div class="div_float div_float_ie6" id="show2G">
                               <t:select id="good_type" name = "good_type" codeType="good_type" ></t:select>
                            </div>
                            <div class="div_float div_float_none">
                                <dl>
                                  <dt>搜索：</dt>
                                  <dd><input type="text"  id="fuzzy_query" value="请输入后1-8位数字查询号码" onfocus="this.value=''" onblur="haoduanOnblur();" class="tel" />
                                  </dd>
                                   <dd class="none"><a id="query" class="search">查询</a></dd>
                                  
                                   <table>
                                   <tr>
                                   <td>
                                   <dt style="width:18px;"></dt>
                                    <dd>
                                    <a href="###" class="read" style="height:30px;" id="btn_getNumberback">返回</a>
                                    </dd>
                                   </td>
                                  </tr>
                                  </table>
                                  <!-- 
                                  <dd class="none"><a id="query" class="search">查询</a></dd>
                                  <dd class="none"><a class="search" id="btn_getNumberback">返回</a></dd>
                                   -->
                                  <div class="clear"></div>
                                </dl>
                            </div>
                            <div class="clear"></div>
                        </div>
                        </form>
                    </div>
              </div>
          </div>            
          <div class="padding_blank"></div>   
            <ul class="detail_white">
            	<div class="wrap" id="haomalist">
				</div>
            </ul>           
            <ul class="detail_white">
                <span class="text_large bold red" id="number_title1">备选号码（点击进行预占）:</span>
				
                    <div class="wrap" id="compareInfo">               
                     </div>
                   
                              
                    
                  <!--  <div class="margin_top_10">
                        <div class="ok"><a href="javascript:void(0);" class="text_large">发送到外屏</a></div>
                    </div> -->
             </ul>
             <div class="padding_blank"></div>
              <ul class="detail_white">
                 <span class="text_large bold red" id="number_title2">预占号码:</span>
				
                    <div class="wrap" id="lockInfo">   </div>  
                  <!--  <div class="margin_top_10">
                        <div class="ok"><a href="javascript:void(0);" class="text_large">发送到外屏</a></div>
                    </div> -->
             </ul>
             
             
            
            
            <div class="padding_blank"></div>
        </div>
  <div class="line_dashed_h"></div> 
  <div id="order_con" style="height:auto;">
  		<div class="info" style="height:auto;">
    	<div class="title text_big">
        	<span class="text_large24">2</span>订单信息
        </div>
		
        <ul  style="height:auto;" >
             <li class="list bold">
            	<div class="left">
                    <div class="left_lable">客户信息：</div>
                </div>
                <div style="width:10%; float:right;height:26px;line-height:26px;">
                    <a href="###"><div class="input_button" id="IDckeck_btb" onClick="checkeCarId();">校 验</div></a>
                </div>
            </li>     
            <li class="list">
            <div class="left">
                    <div class="left_lable">证<span class="space4"></span>件<span class="space4"></span>类<span class="space4"></span>型：</div>
                    <div class="left_data" id="id_type">身份证</div>
                </div>
                <div class="right">
                	<div class="left_lable">业务类型：</div>  
                    <div class="left_data" >开户</div> 
                               
                </div>            
            	<div class="left">
                    <div class="left_lable">客<span class="space4"></span>户<span class="space4"></span>姓<span class="space4"></span>名：</div>
                    <div class="left_data" id="custom_name_pc"></div>
                </div>
                <div class="right">
                	<div class="left_lable">证件号码：</div>  
                    <div class="left_data" id="id_nbr"></div> 
                    <input type="hidden" id="order_id_result" 	name="order_id_result" >                 
                </div>            
            </li>
            
            <div class="line_dashed_h"></div>	    	
		   <li class="list bold">
            	<div class="left">
                    <div class="left_lable">终端信息：</div>
                </div>
                
            </li>       
        	<li class="list">
            	<div class="left">
                	<div class="left_lable">终<span class="space4"></span>端<span class="space4"></span>串<span class="space4"></span>号：</div>
                    <div class="left_data">
                		<input type="text" placeholder="" id="terminal_code" ></input>
               		</div>
                    <a href="###"><div class="input_button" id="terminal_btb" onClick="getTerminalInfo();">校 验</div></a>
                    
                
                </div>
            </li>
            <li class="list">
             <div class="left">
                	 <div class="left_lable">品牌：</div>
                    <div class="left_data" id="pinpai"></div>           
                </div>  
             <div class="right">
                	<div class="left_lable">机型：</div>  
                    <div class="left_data" id="jixing"></div> 
                                   
                </div>  
            </li>
        
		    <div class="padding_blank10"></div>

               
           <div class="line_dashed_h"></div>
             <li class="list bold">
            	<div class="left">
                    <div class="left_lable">用户号码：</div>
                </div>
                <div style="width:10%; float:right;height:26px;line-height:26px;">
                    <a href="###"><div class="input_button" id="haoma_btb" onClick="showNumber();">选择</div></a>
                </div>
            </li>  
            <li class="list">
            
                <div class="left">
                	<div class="left_lable">用户号码：</div>  
                    <div class="left_data" id="acc_nbr"></div>                  
                </div>            
            </li>
            <div class="line_dashed_h"></div>
             <li class="list bold">
            	<div class="left">
                    <div class="left_lable">用户资费：</div>
                </div>
            </li>  
            <li class="list">
            	<div class="left">
                    <div class="left_lable">资<span class="space4"></span>费<span class="space4"></span>信<span class="space4"></span>息：</div>
                    <div class="left_data" id="ofr_name"></div>
                </div>
                <div class="right">
                	<div class="left_lable">生效方式：</div>  
                    <div class="left_data" id="first_month_fee_desc"></div>                  
                </div>            
            </li>
			 <li class="list">
            	<div class="left">
                    <div class="left_lable">活<span class="space4"></span>动<span class="space4"></span>类<span class="space4"></span>型：
                    <select id="activity_name" name="activity_name"  style="width: 155px">																
				    </select>
                    </div>
                </div>
                <div class="right">
                	<div class="left_lable">活动期：
                	<select id="activity_date" name="activity_date"  style="width: 155px">																
				    </select>
                	</div>
                	     
                               
                </div>            
            </li>
            <div class="line_dashed_h"></div> 
            <li class="list bold">
            	<div class="left">
                    <div class="left_lable">费用信息：</div>
                </div>
            </li>
             <ul class="list" id="fee_list">
             </ul>             
            <li class="list">
            	<div class="left">
                    <div class="left_lable">合<span class="space36"></span>计：</div>
                    <div class="left_data" ><span id="fee_total">0</span><span>.00元</span><span class="red" id="fee_flag"></span></div>
                </div>
            </li> 
             <li class="list">
             </li>
            <li class="list">
            <div class="left">
	            <div class="left_lable">收费方式：</div>
	            <div class="left_data" >
	            <select id="pay_type" name="pay_type"  onchange="typeChange()" style="width: 155px">											
					<option value="10">现金</option>
					<option value="15">post刷卡</option>
				</select>   
				</div>
				 <div class="left_data" >
				 <div  onclick="saveFee();" class="input_button" id="btnGetFee">收费</div>
				 </div>
				 <div class="left_data" >
				 <div href="###" class="input_button" id="btnDaYinBillData" onclick="paymentOrderBill();">收据打印</div>
				 </div>
			</div>
			
			</li>   
        </ul>
        
		<div class="padding_blank10"></div>
  </div> 
  
  <div class="line_dashed_h"></div> 
    <div class="info" id="act_div">
    	<div class="title text_big">
        	<span class="text_large24">4</span>USIM卡
        </div>
		
        <ul >
        	
             <li class="list"><input type="text" value="请读卡" id="resourcesCode" onfocus="this.value=''" onblur="if(this.value==''){this.value='请读卡'}"  class="text" /></li>           
             <li class="list"><a href="###" class="input_button"  id="readCard" >读卡</a> <a href="###" id="writeCard"  class="input_button">一键写卡</a></li>
             <li class="list"></li>        
          </ul>          
		<div class="padding_blank10"></div>
  </div> 
    <div class="padding_blank"></div>
    <div class="padding_blank"></div>
    <!--  
    <li class="text_large">
        <div class="ok_disabled" id="btn_submit"><a href="###" onClick="submitWcfOrder();">确  认</a></div>
    </li> -->
    <div class="padding_blank"></div>         
  </div>
  <div class="bg_mask" id="bg_mask">
	<iframe class="bg_mask_iframe"> </iframe>
 </div>
 <div class="loading_gif" id="loading">
	<font color="red" id="in_text">加载中...</font>
</div>
 
  <div class="copyright" >
Copyright © 2014 China unicom All Right Reserved
</div>
</div>
<OBJECT id="umsocxId" height=0 width=0 classid=clsid:5099c9c4-beca-44fe-a42c-8656fbb5a0f3></OBJECT>
 <input id="jsessionid" name="jsessionid" type="hidden" value="${jsessionid}">
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
 <script type="text/javascript">
	var umsocx;
</script>   
</body>
</html>
