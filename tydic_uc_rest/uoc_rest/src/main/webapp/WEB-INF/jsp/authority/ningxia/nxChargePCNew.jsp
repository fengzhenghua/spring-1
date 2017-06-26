<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>沃受理缴费</title>
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/pre_accept.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/nxchargepc.js"></script>
</head>
<body>

<div class="show" style="*height:512px;">
    <div class="info">
    	
    	<ul class="detail_white">
            <div class="padding_blank10"></div>
            <div class="title text_big"><span class="text_big">▏</span>全面支持2G、3G、4G缴费</div>
            <div class="padding_blank10"></div>
            <div class="line_red_top"></div>
       </ul>
       <div class="padding_blank10"></div>
       <div class="padding_blank"></div>

        
       <div class="charge_area">
		<div class="charge_img"></div>
        <div class="charge_vertical"></div>
        	<ul>
            	<li class="list left" style=" width:454px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">缴<span class="space4"></span>费<span class="space4"></span>号<span class="space4"></span>码：</div>
                        <div class="left_data">
                            <input type="text" class="width_25 text_large" id="charge_phone" placeholder="请输入缴费号码。" 
                            		value="" onkeypress="if(event.keyCode==13) {numCheck.click();return false;}"></input>
                        </div>
                        <div class="left_data"><a href="#" class="search" id="numCheck">>查询</a></div>
                    </div>
                </li>
				
                <li class="list left" style=" width:454px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">姓<span class="space36"></span><span class="space9"></span>名：</div>
                        <div class="left_data text_large" id="custname"></div>
                    </div>
                </li>
            	
                <li class="list left" style=" width:454px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">余<span class="space36"></span><span class="space9"></span>额：</div>
                        <div class="left_data text_large"><span class="red" id="balance"></span></div>
                    </div>
                </li>
            	
                <li class="list left" style=" width:454px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">金<span class="space4"></span>额<span class="space4"></span>(<span class="space4"></span>元<span class="space4"></span>)：</div>
                        <div class="left_data">
                            <input type="number" class="width_25 text_large" placeholder="请输入缴费金额，单位元。" id="charge_value"
                            		onkeypress="subEvn(event);"></input>
                        </div>
                    </div>
                </li>
                
                <div id="pay_way">
                <!--  
	            <li class="list left" style=" width:454px; height:36px;" id="pay_type_select_list">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">支<span class="space4"></span>付<span class="space4"></span>方<span class="space4"></span>式：</div>
                        <div name="pay_type_select" class="radio_checked" value="10"></div>
                        <div class="left_data text_large">
                        	现金支付
                        </div>
                        <span class="space4"></span>
                        <div name="pay_type_select" class="radio" value="20_nx"></div>
                        <div class="left_data text_large">
                        	微信支付
                        </div>
                    </div>
                </li>
                -->
               </div>
            </ul>
            <div class="clear"></div>
            <div class="charge_line"></div>
            
            <div class="padding_blank"></div>
            <div class="text_large">
                <div class="text_center">
                    <span class="ok" id="btn_submit"><a href="javascript:void(0)" id="btn_submit">提  交</a></span>
                </div>
            </div>
        </div>

    </div>
  <div class="padding_blank"></div>
  <div class="bottom_blank"></div>
  
</div>

<div class="bg_mask" id="bg_mask">
	<iframe class="bg_mask_iframe"> </iframe>
</div>


<!-- 弹出窗口 二维码扫描 -->
<div class="pop_win" id="dialog_charge_pop" style="display:none">
    <li class="text_center" style=" width:492px; height:492px;">
   		<img class="text_center" alt="" src="" style="width:300px; height:300px" id="pic-idPic">
      	<div class="clear"></div>
      	<div class="charge_line"></div>
      	<div class="padding_blank"></div>
		<div class="text_center"><a href="javascript:hidediv('dialog_charge_pop');"><div class="input_button"> 取  消</div></a></div>  		
   	</li>
</div>

<input type="hidden" value="${jsessionid}" id="jsessionid" />
<input type="hidden" value="${oper_no}" id="oper_no" />
</body>
</html>
