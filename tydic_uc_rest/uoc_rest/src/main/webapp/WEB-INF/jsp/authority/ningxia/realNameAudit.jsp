<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>实名返档页面(PC端)</title>
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/pre_accept.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/realNameAudit.js"></script>

</head>
<body>
<object classid="clsid:10946843-7507-44FE-ACE8-2B3483D179B7" id="CVR_IDCard" name="CVR_IDCard" width="0" height="0"></object>

<div class="show">
    <div class="info">
    	
    	<ul class=" detail_white">
       	
            <!-- <div class="padding_blank10"></div>
            <div class="title text_big"><span class="text_big">▏</span>实名返档</div>
            <div class="padding_blank10"></div>
            <div class="line_red_top"></div> -->
            
            <div class="padding_blank10"></div>
            <div class="list padding_no_t">
                <div class="handle_btn_left handle_btn_left_clicked"><a href="<%=fullPath%>authority/realNameCheck/realNameAudit" name="menu2">实名返档</a></div>
                <div class="handle_btn_right"><a href="<%=fullPath%>authority/realNameCheck/realNameRecordCheck" name="menu2">实名返档审核</a></div>
            </div>
       </ul>
       <div class="padding_blank10"></div>
             
        <ul class="detail">
        	<div class="padding_blank10"></div>
        	<li class="list padding_no_t ">
            	<div class="left">
                	<div class="left_lable">手机号码：</div>
                    <div class="left_data">
                		<input type="text" class="width_25" id="device_number"></input>
               		</div>
                    <a href="javascript:void(0)" id="numCheck"><div class="input_button">验  证</div></a>
                </div>

                <div class="right">
                	<div class="left_lable">选择型号：<span class="space4"></span>
                    	<select name="id_card_mech" id="id_card_mech" >
                            <option value ="crvu">通用 </option>
                        </select>
                    </div>                    
                    <div class="left_lable">
                        <a href="javascript:void(0)"><div class="input_button" id="btn_load">读  取 </div></a>
                    </div>
                </div>
            </li>

         	<div class="padding_blank10"></div>
		</ul>
        <div class="padding_blank"></div>
        <ul class="detail">
            <li>
            	<table width="938" border="0" align="center" cellpadding="0" cellspacing="0" class="auto">
                  <tr>
                    <td width="468" align="center" style="border-right:1px dashed #CCC;"><table width="340" border="0" align="center" cellpadding="0" cellspacing="0" class="auto">
                      <tr><td align="left">身份证正面：</td></tr>
                      <tr>
                        <td height="216" valign="top" class="card_bg_front"><table width="340" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="24"></td>
                          </tr>
                        </table>
                          <table width="340" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="55" height="26"><div style="width:55px;"></div></td>
                              <td width="81" height="26" align="left" id="PIC_Name"></td>
                              <td width="68" height="26"></td>
                              <td colspan="2" rowspan="4" valign="top" width="136" align="left"><img src="<%=fullPath%>images/photo.png" width="94" height="118" class="card_img" id="PIC_Photo"/></td>
                            </tr>
                            <tr>
                              <td width="55" height="26"></td>
                              <td height="26" align="left" id="PIC_Sex"></td>
                              <td height="26" align="left" id="PIC_Nation"></td>
                            </tr>
                            <tr>
                              <td width="55" height="26"></td>
                              <td height="26" colspan="2"><table width="149" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                  <td width="57" align="left" id="PIC_y"></td>
                                  <td width="33" align="left" id="PIC_m"></td>
                                  <td width="59" align="left" id="PIC_d"></td>
                                </tr>
                              </table></td>
                            </tr>
                            <tr>
                              <td width="55"></td>
                              <td height="61" colspan="2" valign="top" class="card_addr"><div style="width:149px;  height:64px; overflow:hidden;" id="PIC_Address"></div></td>
                            </tr>
                          </table>
                          <table width="340" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="110" height="26">&nbsp;</td>
                              <td width="230" align="left" id="PIC_CardNo"></td>
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
                      <tr><td align="left">身份证反面：</td></tr>
                      <tr>
                        <td height="216" valign="top" class="card_bg_reverse"><table width="340" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                              <td height="148">&nbsp;</td>
                            </tr>
                        </table>
                          <table width="340" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="135" height="30">&nbsp;</td>
                              <td width="205" align="left" id="PIC_IssuedAt"></td>
                            </tr>
                            <tr>
                              <td height="26">&nbsp;</td>
                              <td align="left" id="PIC_valid"></td>
                            </tr>
                          </table></td>
                      </tr>
                    </table></td>
                  </tr>
                </table>
               
            </li> 
            <li class="list"></li> 
            </ul>
        <div class="padding_blank"></div>
        <ul class="detail">
            <li class="list">
            	<div class="left">
                	<div class="left_lable">姓<span class="space36"></span>名：</div>
                    <div class="left_data" id="ID_Name"></div>
                </div>
                <div class="right">
                	<div class="left_lable">民<span class="space36"></span>族：</div>
                    <div class="left_data" id="ID_Nation"></div>
                </div>            
            </li>
            <li class="list">
            	<div class="left">
                	<div class="left_lable">性<span class="space36"></span>别：</div>
                    <div class="left_data" id="ID_Sex"></div>
                </div>
                <div class="right">
                	<div class="left_lable">出<span class="space4"></span>生<span class="space4"></span>日<span class="space4"></span>期：</div>
                    <div class="left_data" id="ID_Born"></div>
                </div>            
            </li>
            <li class="list">
            	<div class="left">
                	<div class="left_lable">地<span class="space36"></span>址：</div>
                    <div class="left_data" id="ID_Address"></div>
                </div>
                <div class="right">
                	<div class="left_lable">身<span class="space4"></span>份<span class="space4"></span>证<span class="space4"></span>号：</div>
                    <div class="left_data" id="ID_CardNo"></div>
                </div>            
            </li>
            <li class="list">
            	<div class="left">
                	<div class="left_lable">有<span class="space4"></span>效<span class="space4"></span>期<span class="space4"></span>限：</div>
                    <div class="left_data" id="ID_valid"></div>
                </div>
                <div class="right">
                	<div class="left_lable">手<span class="space4"></span>机<span class="space4"></span>号<span class="space4"></span>码：</div>
                    <div class="left_data" id="ID_PhoneNumber"></div>
                </div>   
            </li>
            
        
        </ul>
        <div class="padding_blank"></div>
        <div class="text_large">
            <div class="text_center">
             	<span class="ok" id="btn_clean"><a href="javascript:void(0)" >清  除</a></span>
            	<span class="ok" id="btn_commit"><a href="javascript:void(0)" >返  档</a></span>
            </div>
        </div>
    </div>
  <div class="bottom_blank"></div>
  <div class="clear"></div>
</div>
<input type="hidden" value="${jsessionid}" id="jsessionid" />
<input type="hidden" value="" id="auth_end_date" />
<input type="hidden" value="" id="auth_begin_date" />
<input type="hidden" name="messageFlag" value="0" id="messageFlag" />
<input type="hidden" id="id_card_image_path" 	    name="id_card_image_path"       value="<%=fullPath%>picture/idcard/idCardImag"/>
<input type="hidden" id="id_card_image_path_blank" 	name="id_card_image_path_blank" value="<%=fullPath%>images/photo.png"/>
</body>
</html>
