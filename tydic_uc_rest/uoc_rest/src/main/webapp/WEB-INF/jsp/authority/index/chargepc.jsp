<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>沃受理缴费</title>
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/pre_accept.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/chargepc.js"></script>
<script type="text/javascript" src="<%=fullPath%>js/json2.js"></script>
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
                        <div class="left_data"><a href="#" class="search" id="numCheck">查询</a></div>
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
                   <div class="right">
                        <div class="right_data" style=" margin-right:35px; font-size:14px;">
                            <a href="###"><div class="input_button" id="fee_detail">费用详情</div></a>
                        </div>
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
                
	            <li class="list left" style=" width:454px; height:36px;" id="pay_type_select_list">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">支<span class="space4"></span>付<span class="space4"></span>方<span class="space4"></span>式：</div>
                        <div name="pay_type_select" class="radio_checked" value="10"></div>
                        <div class="left_data text_large">
                        	现金支付
                        </div>
                        <!-- 
                        <span class="space4"></span>
                        <div name="pay_type_select" class="radio" value="20_nx"></div>
                        <div class="left_data text_large">
                        	微信支付
                        </div>
                         -->
                    </div>
                </li>
               
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

<!-- 弹出窗口 客户选择： -->
<div class="pop_win" id="cust_search" style="display:none;">
    <div class="msgbox">
    	<a href="javascript:hidediv('cust_search');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">号码信息选择</span></li>
        </ul>
        <div class="scroll_v" style="position:relative;">
		<ul id="tariff_list">                       
            <li class="list">
                <div class="left">
                    <div class="left_lable">沃3G-基本套餐A（66）</div>
                    <div class="right_data">
                        <input name="cust_search_check" type="radio"></input>
                    </div>
                </div>
            </li>
        </ul>
        </div>
        <div class="line_dashed_h"></div>
		<ul>
         <li class="center">
        <a href="javascript:void(0)" onClick="custConfirm();"><div class="input_button">确 定</div></a> 
        </li></ul>      
           
    </div>
</div>
<!-- 弹出窗口 费用详情 begin 2016-8-8 -->
<div class="pop_win" id="fee_info" style="display:none">
    <div class="msgbox">
    	<div class="padding_blank10"></div>
        <div class="float_middle text_large bold">
            费用详情
        </div>
        <div class="padding_blank10"></div>
        
        <div class="line_dashed_h"></div>
        <ul class="text_big" style=" padding:10px 0px 10px 50px;">
            <li class="list">
            	<div class="left">
                	<div class="left_lable">
                    	当月实时话费：<span class="red" id="rt_fee">0.00</span>元
                    </div>
                </div>
            </li>
            <li class="list">
            	<div class="left">
                	<div class="left_lable">
                    	当前可用余额：<span class="red" id="ava_fee">0.00</span>元
                    </div>
                </div>
            </li>
            <li class="list">
            	<div class="left">
                	<div class="left_lable">
                    	普<span class="space3"></span>通<span class="space3"></span>预<span class="space3"></span>存<span class="space3"></span>款：<span class="red" id="pre_store">0.00</span>元
                    </div>
                </div>
            </li>
            <li class="list" id="share_li">
            	<div class="left">
                	<div class="left_lable">
                    	当月可用分摊款：<span class="red" id="share_fee">0.00</span>元
                    </div>
                </div>
            </li>
            <li class="list">
            	<div class="left">
                	<div class="left_lable">
                    	当<span class="space8"></span>月<span class="space8"></span>赠<span class="space8"></span>款：<span class="red" id="rt_gant">0.00</span>元
                    </div>
                </div>
            </li>
            <li class="list" id="his_li">
            	<div class="left">
                	<div class="left_lable">
                    	历<span class="space8"></span>史<span class="space8"></span>欠<span class="space8"></span>费：<span class="red" id="his_arr">0.00</span>元
                    </div>
                </div>
            </li>
            <li class="list" id="dedit_li">
            	<div class="left">
                	<div class="left_lable">
                    	违<span class="space18"></span>约<span class="space18"></span>金：<span class="red" id="dedit">0.00</span>元
                    </div>
                </div>
            </li>
            
        </ul>
        
        <div class="line_dashed_h"></div>
        <div class="padding_blank10"></div>
        
        <div class="center_one">
       		<a href="javascript:void(0)" onClick="hidediv('fee_info');"><div class="input_button">确 定</div></a>
        </div>
        
    </div>
</div>
<!-- 弹出窗口 费用详情 end 2016-8-8 -->

</div>
<input type="hidden" value="${jsessionid}" id="jsessionid" />
<input type="hidden" value="${oper_no}" id="oper_no" />
<input type="hidden" value="${province_code}" id="province_code" />
<input type="hidden" value="${iscolse}" id="iscolse" />
</body>
</html>
