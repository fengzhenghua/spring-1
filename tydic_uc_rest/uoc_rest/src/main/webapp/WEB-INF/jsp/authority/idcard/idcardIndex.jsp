<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
<%@ taglib prefix="t" uri="http://tydic.com/jsp/core" %>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 加载jQuery CSS -->
<script type="text/javascript" src="<%=fullPath%>js/getIdCard.js"></script>
<link href="<%=fullPath%>css/share1.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/card.css" rel="stylesheet" type="text/css" />
</head>
<body>
<object classid="clsid:10946843-7507-44FE-ACE8-2B3483D179B7" id="CVR_IDCard" name="CVR_IDCard" width="0" height="0"></object>
<div name="reader_context" id="reader_context">
<div class="show">
    <div class="show_title_bg">
        <div class="show_title">订单详情</div>
    </div>



    <div class="card">
    
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
        <td><a href="###" class="read" id="btn_load">读取</a></td>
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
  </div>

</div>

<div class="copyright">
Copyright © 2014 China unicom All Right Reserved
</div>
</body>
</html>