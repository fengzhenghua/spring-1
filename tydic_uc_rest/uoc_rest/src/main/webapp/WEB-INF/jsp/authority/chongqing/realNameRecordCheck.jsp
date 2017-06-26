<%@page import="com.tydic.unicom.crm.web.uss.constants.UrlsMappings"%>
<%@page import="com.tydic.unicom.crm.web.uss.constants.ControllerMappings"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实名返档审核</title>
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="<%=fullPath%>css/share.css" >


<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/commCheck.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>js/jquery-ligerui-2.25/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />

<script src="<%=basePath%>js/jquery-ligerui-2.25/js/core/base.js" type="text/javascript"></script> 
<script src="<%=basePath%>js/jquery-ligerui-2.25/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="<%=basePath%>js/jquery-ligerui-2.25/js/plugins/ligerDialog.js" type="text/javascript"></script>
<script src="<%=fullPath%>js/json2.js" type="text/javascript"></script> 


<style type="text/css">
.list_nx{padding:4px 0px 0px 0px; width:874px; height:26px; margin:0 auto; vertical-align:central;}
.left_data_nx{float:left;}
.left_data_nx input {padding-left:4px; margin-right:4px; border:1px solid #e7e7e7; height:24px; line-height:24px; width: 148px;}
</style>
<script type="text/javascript" src="<%=fullPath%>js/cqRealNameCheck.js"></script>
<script type="text/javascript">
var fullPath = '<%=fullPath%>';
</script>
</head>
<body>
	<div class="show">
        <div class="box box_long" >
        		<div class="table_box">
			        <table width="980" border="0" align="center" cellpadding="0" cellspacing="0">
			          <tr>
			            <td><div class="left_data_nx">
        					身份证号码：<input type="text" id="identity" name=""></input>
        					</div>
        				</td>
			            <td><div class="left_data_nx">
        					手机号码：<input type="text" id="phone" name="" value=""></input>
        					</div>
        				</td>
			            <td>
			            	<div class="left_data_nx" style="padding-left: 12px;">
        					开始日期：<input type="text" id="startDate" readonly="readonly" onfocus="WdatePicker({maxDate: '#F{$dp.$D(\'endDate\')}'});"></input>
        					</div>
        				</td>
			            <td>
			            	<div class="left_data_nx">
        					结束日期：<input type="text" id="endDate" readonly="readonly" onfocus="WdatePicker({minDate: '#F{$dp.$D(\'startDate\')}'});"></input>
        					</div>
        				</td>
        				<td>
			            	<div class="left_data_nx">
  		      					<a href="javascript:void(0)" id="search"><div class="input_button">查  询</div></a>
        					</div>
        				</td>
			          </tr>
			        </table>
			    </div>
			</div>
			<div class="box box_long" style="padding:0 0 10px 0;height:380px;">
				<table id="rlist"></table>
				<div id="gridPager"></div>
				<!-- <li class="list" style="height: 240px;">
					<table id="rlist"></table>
					<div id="gridPager"></div>
	         	</li>   -->
			</div>
			<div class="clear"></div>
		</div>
	    <div class="info">
	            	<table width="938" border="0" align="center" cellpadding="0" cellspacing="0" class="auto">
	                  <tr>
	                    <td width="300" align="center" style="border-top:1px dashed #CCC; border-right:1px dashed #CCC;border-bottom:1px dashed #CCC;">
	                        <table width="300" border="0" align="center" cellpadding="0" cellspacing="0" class="auto">
	                          <!-- <tr><td width="75">身份证正面：</td></tr> -->
	                          <tr>
                              	
	                            <td height="216" valign="top" class="">
	                                <table width="340" border="0" cellspacing="0" cellpadding="0">
	                                  <tr>
	                                    <td>
	                                        <img src="<%=fullPath%>images/card_front.png" width="340" height="216" class="" id="card_front" ondblclick="enlargeImage1(this)" onclick="smallImage(this)"/>
	                                    </td>
	                                  </tr>
	                                </table>
	                            </td>
	                          </tr>
	                        </table>
	                    </td>
	                    <td width="300" align="center" style="border-top:1px dashed #CCC;border-bottom:1px dashed #CCC;">
	                    	<table width="300" border="0" align="center" cellpadding="0" cellspacing="0" class="auto">
	                         <!--  <tr><td width="75">身份证反面：</td></tr> -->
	                          <tr>
                              	
	                            <td height="216" valign="top" class="">
	                            <table width="340" border="0" cellspacing="0" cellpadding="0">
	                              <tr>
	                                <td>
	                                    <img src="<%=fullPath%>images/card_reverse.png" width="340" height="216" class="" id="card_back" ondblclick="enlargeImage1(this)" onclick="smallImage(this)"/>
	                                </td>
	                              </tr>
	                            </table>
	                            </td>
	                          </tr>
	                        </table>
	                     </td>
	                     
	                     <td width="300" align="center" style="border-bottom:1px dashed #CCC;">
	                        <table width="300" border="0" align="center" cellpadding="0" cellspacing="0" class="auto">
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
	                     
	                  </tr>
	                </table>
	            </div>    
	            <div  class="show">
	            	<div class="box box_long" >
        			<div class="show_big_title"><strong>.</strong>证件信息如下：</div>
	                <table width="980" border="0" align="center" cellpadding="0" cellspacing="0" class="auto">
	                  <tr>
	                    <td width="980" align="center" style="border-bottom:0px dashed #CCC;">
	                        <table width="900" border="0" cellpadding="10" cellspacing="10" class="auto">
	                          <tr>
	                          	<th style="text-align: left;height: 25px;width: 60px;">姓<span class="space24"></span>名：</th>
								<td id="cert_name" style="width: 300px;" align="left"></td>
								<th style="text-align: left;height: 25px;width: 60px;">民<span class="space24"></span>族：</th>
								<td id="nation" style="width: 300px;" align="left"></td>
							  </tr>
							  <tr>
								<th style="text-align: left;height: 25px;width: 60px;">性<span class="space24"></span>别：</th>
								<td id="sex" style="width: 300px;" align="left"></td>
								<th style="text-align: left;height: 25px;width: 60px;">出生日期：</th>
								<td id="birthday" style="width: 300px;" align="left"></td>
							  </tr>
							  <tr>
								<th style="text-align: left;height: 25px;width: 60px;">身份证号：</th>
								<td id="cert_id" style="width: 300px;" align="left"></td>
								<th style="text-align: left;height: 25px;width: 60px;">有<span class="space6"></span>效<span class="space6"></span>期：</th>
								<td id="exp_date" style="width: 300px;" align="left"></td>
						      </tr>
							  <tr>
								<th style="text-align: left;height: 25px">手机号码：</th>
								<td id="device_number" style="width: 300px;" align="left"></td>
							  </tr>
							  <tr>
								<th style="text-align: left;height: 25px">证件地址：</th>
								<td align="left"><div  class="left_data_nx">
									<input type="text" id="cert_addr" name="" style="width:320px;" ></input></div>
								</td>
							  </tr>
							  <tr class="">
								<th style="text-align: left;height: 25px;width: 80px;">联<span class="space6"></span>系<span class="space6"></span>人：</th>
								<td id="link_name" style="width: 110px;" align="left"></td>
								<th style="text-align: left;height: 25px;width: 80px;">联系电话：</th>
								<td id="link_phone" style="width: 110px;" align="left"></td>
							  </tr>
							  <tr>
								<th style="text-align: left;height: 25px">联系地址：</th>
								<td id="cust_addr" style="width: 220px;" align="left"></td>
							  </tr>
	                        </table>
	                	</td>
	                </tr>
	            </table>
	            <div class="show_big_title"><strong>.</strong>请进行审核操作：</div>
	            <table width="938" border="0" align="center" cellpadding="0" cellspacing="0" class="auto">
					<tr>
						<td width="960">
							<div class="left_data_nx">
        						不通过原因：<t:select id="reason" name = "reason" codeType="real_check_reason"></t:select>
        					</div>
        				</td>
        			</tr>
        			<tr>
			            <td width="960">
			            	<div class="left_data_nx">
        						备<span class="space18"></span><span class="space18"></span>注：
        						<textarea type="text" id="remark" name="" value="" cols="80" rows="4"></textarea>
        					</div>
        				</td>
                    </tr>
                </table>
				<table width="938" border="0" align="center" cellpadding="0" cellspacing="0" class="auto">
					<tr>
						<input type="hidden" id="row_id" value=""/>
                   		<input type="hidden" id="order_id" value=""/>
						<td align="center" height="30">
                       		<a href="javascript:void(0)" id="record" style="padding-left: 15px;"><div class="input_button_right">返  档</div>
                       	</td>
                       	<td align="left" height="30">
                       		<a href="javascript:void(0)" id="veto" style="padding-left: 15px;"><div class="input_button">审核不通过</div></a>
                       	</td>
                    </tr>
                </table>   
        	<div class="padding_blank"></div>
            
	    </div>
	  <div class="bottom_blank"></div>
	  <div class="clear"></div>
	</div>
	

</body>
</html>