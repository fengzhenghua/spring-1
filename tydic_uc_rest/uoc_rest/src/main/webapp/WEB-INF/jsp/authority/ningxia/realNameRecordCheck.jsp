<%@page import="com.tydic.unicom.crm.web.uss.constants.UrlsMappings"%>
<%@page import="com.tydic.unicom.crm.web.uss.constants.ControllerMappings"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实名返档审核</title>
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/pre_accept.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.list_nx{padding:4px 0px 0px 0px; width:874px; height:26px; margin:0 auto; vertical-align:central;}
.left_data_nx{float:left;}
.left_data_nx input {padding-left:4px; margin-right:4px; border:1px solid #e7e7e7; height:24px; line-height:24px; width: 148px;}
</style>
<script type="text/javascript" src="<%=fullPath%>js/realNameCheck.js"></script>
<script type="text/javascript">
var fullPath = '<%=fullPath%>';
</script>
</head>
<body style="font-size:14px;">

	<div class="show">
	    <div class="info">
	       <ul class=" detail_white">
       	
            <!-- <div class="padding_blank10"></div>
            <div class="title text_big"><span class="text_big">▏</span>实名返档</div>
            <div class="padding_blank10"></div>
            <div class="line_red_top"></div> -->
            
            <div class="padding_blank10"></div>
            <div class="list padding_no_t">
                <div class="handle_btn_left"><a href="<%=fullPath%>authority/realNameCheck/realNameAudit" name="menu2">实名返档</a></div>
                <div class="handle_btn_right handle_btn_right_clicked"><a href="<%=fullPath%>authority/realNameCheck/realNameRecordCheck" name="menu2">实名返档审核</a></div>
            </div>
       </ul>
       <div class="padding_blank10"></div>
	        <ul class="detail">
	        	<form id="searchfom" method="post">
	        	<li class="list_nx">
	        		<div class="left_data_nx">
	        			身份证号码：<input type="text" id="identity" name=""></input>
	        		</div>
	        		<div class="left_data_nx">
	        			手机号码：<input type="text" id="phone" name="" value=""></input>
	        		</div>
	        		<div class="left_data_nx">
	        			工单号码：<input type="text" id="workid" name="" value=""></input>
	        		</div>
	        	</li>
	        	<li class="list_nx">
	        		<div class="left_data_nx" style="padding-left: 12px;">
	        			开始日期：<input type="text" id="startDate" readonly="readonly" onfocus="WdatePicker({maxDate: '#F{$dp.$D(\'endDate\')}'});"></input>
	        		</div>
	        		<div class="left_data_nx">
	        			结束日期：<input type="text" id="endDate" readonly="readonly" onfocus="WdatePicker({minDate: '#F{$dp.$D(\'startDate\')}'});"></input>
	        		</div>
	        		<div class="left_data_nx">
	        			<input type="radio" name="audit_flag" checked="checked" style="width:30px;" value="0"/>未审核
	        			<input type="radio" name="audit_flag" style="width:30px;" value="1"/>审核已通过
	        			<input type="radio" name="audit_flag" style="width:30px;" value="2"/>审核未通过
	        		</div>
	        		<div class="left_data_nx" style="padding-left: 10px;">
	                    <a href="javascript:void(0)" id="search"><div class="input_button" style="font-size:14px;">查  询</div></a>
	                </div>
	        	</li>
	            
	            </form>
	           
	         	<li class="list" style="height: 240px;">
	         		<!-- jqGrid 数据 table -->
					<table id="rlist"></table>
					<!-- jqGrid 分页 div  -->
					<div id="gridPager"></div>
	         	</li>           	
			</ul>
       		 <div class="padding_blank"></div>
       		 
       		 
	            <ul class="detail">
	            <li class="list">
	            	
	            	<table width="938" border="0" align="center" cellpadding="0" cellspacing="0" class="auto">
	                  <tr>
	                    <td width="468" align="center" style="border-top:1px dashed #CCC; border-right:1px dashed #CCC;border-bottom:1px dashed #CCC;">
	                        <table width="415" border="0" align="center" cellpadding="0" cellspacing="0" class="auto">
	                          <!-- <tr><td width="75">身份证正面：</td></tr> -->
	                          <tr>
                              	
	                            <td height="216" valign="top" class="">
	                                <table width="340" border="0" cellspacing="0" cellpadding="0">
	                                  <tr>
	                                    <td>
	                                    	<a class="" rel="group" href="javascript:;" id="cFronta">
	                                        <img src="<%=fullPath%>images/card_front.png" width="340" height="216" class="" id="card_front"/>
	                                        </a>
	                                    </td>
	                                  </tr>
	                                </table>
	                            </td>
	                          </tr>
	                        </table>
	                    </td>
	                    <td width="468" align="center" style="border-top:1px dashed #CCC;border-bottom:1px dashed #CCC;">
	                    	<table width="415" border="0" align="center" cellpadding="0" cellspacing="0" class="auto">
	                         <!--  <tr><td width="75">身份证反面：</td></tr> -->
	                          <tr>
                              	
	                            <td height="216" valign="top" class="">
	                            <table width="340" border="0" cellspacing="0" cellpadding="0">
	                              <tr>
	                                <td id="">
	                                	<a class="" rel="group" href="javascript:;" id="cBacka">
	                                    <img src="<%=fullPath%>images/card_reverse.png" width="340" height="216" class="" id="card_back"/>
	                                	</a>
	                                </td>
	                              </tr>
	                            </table>
	                            </td>
	                          </tr>
	                        </table>
	                     </td>
	                  </tr>
	                </table>
	                <table width="938" border="0" align="center" cellpadding="0" cellspacing="0" class="auto">
	                  <tr>
	                    <td width="468" align="center" style="border-bottom:1px dashed #CCC;">
	                        <table width="395" border="0" align="center" cellpadding="0" cellspacing="0" class="auto">
	                          <!-- <tr><td width="75" height="30" valign="bottom">手持身份证：</td></tr> -->
	                          <tr>
                              	
	                            <td height="216" valign="center" class="">
	                                <table width="340" border="0" cellspacing="0" cellpadding="0">
	                                  <tr>
	                                    <td valign="center" style="display:inline; text-align:center; margin:0 auto;" id="card_in_hand_td">
	                                    	<h2>手持身份证</h2>
	                                        <%-- <img src="<%=fullPath%>images/photo.png" width="340" height="216" class="" id="card_in_hand"/> --%>
	                                    </td>
	                                  </tr>
	                                </table>
	                            </td>
	                          </tr>
	                        </table>
	                    </td>
	                    <td width="468" align="center" style="border-bottom:1px dashed #CCC;">
	                        <table width="435" border="10" cellpadding="20" cellspacing="16" class="auto" id="info_table">
	                          <tr>
	                          	<th style="text-align: left;height: 30px;width: 80px;">姓<span class="space36"></span>名：</th>
	                          	<td style="width: 110px;">
	                          		<input type="text" id="cert_name" readonly="readonly" ></input>
	                          	</td>
	                          	<th style="text-align: left;height: 20px;width: 80px;">民<span class="space36"></span>族：</th>
	                          	<td style="width: 100px;">
	                          		<input type="text" id="nation" readonly="readonly" ></input>
	                          	</td>
	                          </tr>
	                           <tr>
	                          	<th style="text-align: left;height: 30px">性<span class="space36"></span>别：</th>
	                          	<td>
	                          		<input type="text" id="sex" readonly="readonly" ></input>
	                          	</td>
	                          	<th style="text-align: left;height: 30px;width: 90px;">出<span class="space4"></span>生<span class="space4"></span>日<span class="space4"></span>期：</th>
	                          	<td>
	                          		<input type="text" id="birthday" readonly="readonly" ></input>
	                          	</td>
	                          </tr>
	                          <tr>
	                          	<th style="text-align: left;height: 30px">身<span class="space4"></span>份<span class="space4"></span>证<span class="space4"></span>号：</th>
	                          	<td>
	                          		<input type="text" id="cert_id" readonly="readonly" ></input>
	                          	</td>
	                          	<th style="text-align: left;height: 30px">有<span class="space12"></span>效<span class="space12"></span>期：</th>
	                          	<td>
	                          		<input type="text" id="exp_date" readonly="readonly" ></input>
	                          	</td>
	                          </tr>
	                          <tr>
	                          	<th style="text-align: left;height: 30px">手<span class="space4"></span>机<span class="space4"></span>号<span class="space4"></span>码：</th>
	                          	<td colspan="3">
	                          		<input type="text" id="device_number" readonly="readonly" ></input>
	                          	</td>
	                          </tr>
	                          <tr>
	                          	<th style="text-align: left;height: 30px">地<span class="space36"></span>址：</th>
	                          	<td colspan="3">
	                          		<input type="text" id="cert_addr" readonly="readonly" style="width:380px" ></input>
	                          	</td>
	                          </tr>
	                          <tr>
	                          	<td colspan="4" height="50">
	                          		<input type="hidden" id="row_id" value=""/>
	                          		<input type="hidden" id="order_id" value=""/>
	                          		<a href="javascript:void(0)" id="update" style="padding-left: 70px;font-size:16px;"><div class="input_button">纠 错</div>
	                          		<a href="javascript:void(0)" id="record" style="padding-left: 70px;font-size:16px;"><div class="input_button">通 过</div>
	                          		<a href="javascript:void(0)" id="veto" style="padding-left: 15px;font-size:16px;"><div class="input_button">不通过</div></a>
	                          	</td>
	                          </tr>
	                          <tr name="reason_tr" style="display: none;">
	                          	<th style="text-align: left;height: 30px;width:120px;">审核不通过原因：</th>
	                          	<td colspan="3">
	                          		<textarea rows="5" cols="30" id="nopass_reason"></textarea>
	                          		
	                          	</td>
	                          </tr>
	                          <tr name="reason_tr" style="display: none;">
	                          	<td colspan="4" height="50">
	                          		<a href="javascript:void(0)" id="nopass_cancel" style="padding-left: 170px;"><div class="input_button">取消</div></a>
	                          		<a href="javascript:void(0)" id="nopass" style="padding-left: 10px;"><div class="input_button">确定</div></a>
	                          	</td>
	                          </tr>
	                        </table>
	                    </td>
	                  </tr>
	                </table>
	                
	            </li>
	         </ul>
        		<div class="padding_blank"></div>
            
	    </div>
	  <div class="bottom_blank"></div>
	  <div class="clear"></div>
	</div>
	
<div id="dialog"></div>
</body>
</html>