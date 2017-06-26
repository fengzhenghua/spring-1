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

<div class="show">
    <div class="info">
    	
    	<ul class="detail_white">
            <div class="padding_blank10"></div>
            <div class="title text_big"><span class="text_big">▏</span>缴费</div>
            <div class="padding_blank10"></div>
            <div class="line_red_top"></div>
       </ul>
       <div class="padding_blank10"></div>
             
        <div class="charge_border">
        	<div class="charge_area">
            	<div class="left_lable text_large bold">缴<span class="space4"></span>费<span class="space4"></span>号<span class="space4"></span>码：</div>
                <div class="left_data">
                    <input type="number" class="width_25 text_large" id="charge_phone" value="" maxlength="11" placeholder="请输入缴费号码。"></input>
                </div>
                <a href="javascript:void(0)" id="numCheck"><div class="input_button">查  询</div></a>
                <div class="padding_blank10"></div>
                <div class="left_lable text_large bold">金<span class="space4"></span>额<span class="space4"></span>(<span class="space4"></span>元<span class="space4"></span>)：</div>
                <div class="left_data">
                    <input type="number" class="width_25 text_large" id="charge_value" value="" maxlength="5" placeholder="请输入缴费金额，单位元。"></input>
                </div>
            </div>

            <div class="padding_blank" id="numInfo"></div>
            <div class="padding_blank"></div>
            <div class="padding_blank"></div>
            <div class="padding_blank"></div>
            <div class="padding_blank"></div>
            <div class="padding_blank"></div>
            <div class="padding_blank"></div>
            <div class="padding_blank"></div>
            <div class="text_large">
                <div class="text_center">
                    <span class="ok" id="btn_submit"><a href="javascript:void(0)" >提  交</a></span>
                </div>
            </div>
        
		</div>
        
    </div>
  <div class="bottom_blank"></div>
  <div class="clear"></div>
</div>

<div class="bg_mask" id="bg_mask">
	<iframe class="bg_mask_iframe"> </iframe>
</div>

<div class="pop_win" id="dialog_pay_type" style="display:none;">
    <div class="msgbox">
    	<a href="javascript:hidediv('dialog_pay_type');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">选择支付方式</span></li>
        </ul>
        <div class="scroll_v" style="position:relative;">
		<ul id="pay_type_list">                       
            <li class="list">
                <div class="left">
                    <div class="left_lable">现金</div>
                    <div class="right_data">
                        <input name="pay_type_check" type="radio" value="10"></input>
                    </div>
                </div>
            </li>
            <li class="list">
                <div class="left">
                    <div class="left_lable">支付宝 </div>
                    <div class="right_data">
                        <input name="pay_type_check" type="radio" value="19"></input>
                    </div>
                </div>
            </li>
            <li class="list">
                <div class="left">
                    <div class="left_lable">微信支付(联通) </div>
                    <div class="right_data">
                        <input name="pay_type_check" type="radio" value="20_nx"></input>
                    </div>
                </div>
            </li>
            <li class="list">
                <div class="left">
                    <div class="left_lable">微信支付(迪科) </div>
                    <div class="right_data">
                        <input name="pay_type_check" type="radio" value="20"></input>
                    </div>
                </div>
            </li>
        </ul>
        </div>
        <div class="line_dashed_h"></div>
		<ul>
         <li class="center">
        <a href="javascript:void(0)" onClick="payTypeConfirm();"><div class="input_button">确 定</div></a> 
       	<a href="javascript:hidediv('dialog_pay_type');"><div class="input_button">取 消</div></a>    		
        </li></ul>      
           
    </div>
</div>

<!-- 弹出窗口 二维码扫描 -->
<div class="pop_win" id="dialog_charge_pop" style="display:none">
    <div class="msgbox">
    	<div>
			<img alt="" src="" style="width:200px; height:200px" id="pic-idPic">
		</div>
        <ul>
         <li class="center">
       	<a href="javascript:hidediv('dialog_charge_pop');"><div class="input_button"> 取  消</div></a>    		
        </li></ul>
        
    </div>
</div>

<input type="hidden" value="${jsessionid}" id="jsessionid" />
<input type="hidden" value="${oper_no}" id="oper_no" />
</body>
</html>
