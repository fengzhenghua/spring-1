<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
<%@ taglib prefix="t" uri="http://tydic.com/jsp/core" %>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>云销售PC版</title>

<link href="<%=fullPath%>css/share1.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/card.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/showOrder.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/pre_accept.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/openAccountPC.js"></script>
<!--[if lt IE 9]>
    <script type="text/javascript" src="<%=fullPath%>js/json2.js"></script>
<![endif]-->

</head>
<body>
<object classid="clsid:10946843-7507-44FE-ACE8-2B3483D179B7" id="CVR_IDCard" name="CVR_IDCard" width="0" height="0"></object>
<div name="reader_context" id="reader_context">

  <div class="show">
  
     <ul id="step0">
    <div class="info">
    	<div class="title text_big">
        	<span class="text_large24">1</span>选择开户类型
        </div>
		
    	<ul class="detail" >
    	 <div class="select">
           <form id="form1" name="form1" method="post" action="">
       <div class="select_list">
           <div class="div_float">
                <a href="javascript:void(0);" id="teleType2G_sel" class="g">2G</a><a href="javascript:void(0);" class="g" id="teleType3G_sel">3G</a><a href="javascript:void(0);" class="g"  id="teleType4G_sel" >4G</a>
            </div></div>
            </form></div>
        </ul>
        
    </div> 
    <div class="padding_blank"></div>    
    </ul>
  
   <ul id="step1">
    <div class="info">
    	<div class="title text_big">
        	<span class="text_large24" id="title1">1</span>用户资料
        </div>
        <ul class="detail">
          <li class="list">
            <div class="left">
            	<div class="left_lable">选择型号：
            	<select name="id_card_mech" id="id_card_mech" >
		    	    	<option value ="crvu">通用 </option>
		  	        </select>
            	</div>      		 
		  	     <div class="left_data"> <a href="javascript:void(0);"><div class="input_button" id="btn_load">读  取</div></a><a href="javascript:void(0);"><div class="input_button" id="btn_load_test">模拟读取</div></a></div>        
		  	</div>  
		  	 <div class="right">
		  	 		<div class="left_lable">校验信息：</div>
                    <div class="left_data" id="cust_check_info">未校验
               		</div>        
                </div>      
            </li>
        	<li class="list">
            	<div class="left">
                	<div class="left_lable">证件类型：</div>
                    <div class="left_data">身份证</div>
                </div>
                <div class="right">
                	<div class="left_lable">证件号码：</div>
                    <div class="left_data" id="id_number">
               		</div>        
                </div>
            </li>          
            <li class="list">
            	<div class="left">
                	<div class="left_lable">证件名称：</div>
                    <div class="left_data" id="customer_name"></div>
                </div>
                <div class="right">
                	<div class="left_lable">证件地址：</div>
                    <div class="left_data" id="auth_adress"></div>
                </div>            
            </li>
            
            <li class="list">
            	<div class="left">
                	<div class="left_lable">联系电话：</div>
                    <div class="left_data">
                		<input type="text" placeholder="请输入联系电话" id="connect_phone"></input>
               		</div>
                </div>
                <div class="right">
                	<div class="left_lable">联系地址：</div>
                    <div class="left_data">
                		<input type="text" placeholder="请输入联系地址" id="connect_addr" class="width_32"></input>
               		</div>
                </div>            
            </li>
        	
            <li class="list">
            	<div class="line_dashed_h"></div>
            </li>
            
            <li class="list text_big">
                <div class="list_title">本人身份证</div>
            </li>
            <li>
            	<table width="938" border="0" align="center" cellpadding="0" cellspacing="0" class="auto">
                  <tr>
                    <td width="468" align="center" style="border-right:1px dashed #CCC;"><table width="340" border="0" align="center" cellpadding="0" cellspacing="0" class="auto">
                      <tr>
                        <td height="216" valign="top" class="card_bg_front"><table width="340" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="24"></td>
                          </tr>
                        </table>
                          <table width="340" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="55" height="26"><div style="width:55px;"></div></td>
                              <td width="81" height="26" align="left" id="bg_card_name"></td>
                              <td width="68" height="26"></td>
                              <td colspan="2" rowspan="4" valign="top" width="136" align="left"><img src="<%=fullPath%>images/photo.png" width="94" height="118" class="card_img" id="idCradImage"/></td>
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
                              <td height="61" colspan="2" valign="top" class="card_addr"><div style="width:149px;  height:64px; overflow:hidden;" id="bg_card_born_addrss"></div></td>
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
                        <td height="216" valign="top" class="card_bg_reverse"><table width="340" border="0" cellspacing="0" cellpadding="0">
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
                              <td align="left"  id="bg_card_valid"></td>
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
            
        <div class="band_dashed text_large">
            <div class="ok_disabled" id="step1_next_div"><a  id="step1_next"  href="javascript:void(0);">确  定</a></div>
            <!-- <div class="ok_disabled"><a href="javascript:void(0);">确  定</a></div> -->
        </div>    
    </div>
	<div class="padding_blank"></div>    
    </ul>   
    <ul id="step2"> 
    <div class="info">
    	<ul class="detail_white">
        <div class="title_tab">        
            <span class="text_large24" id="title2">2</span>
        
            <div class="tab_menu">
                <a href="javascript:void(0);" class="current" id="tab_phone">手机</a>
            </div>
            <div class="tab_menu">
                <a href="javascript:void(0);"  class="" id="tab_number">号码</a>
            </div>
            <div class="tab_menu">
                <a href="javascript:void(0);"  class="" id="tab_fee">资费</a>
            </div>         
        </div>    
        <div class="clear"></div> 
        </ul> 
    
        <div id="phone" >
            <div class="band_phone">
                <input type="text" id="searchMobileInput" class="search_input" placeholder="请输入品牌、型号、颜色、营业厅名称进行模糊搜索"></input>
            	<a href="javascript:void(0);" id="searchMobile"><div class="search_button text_large">搜  索</div></a>
            </div>
            
            <ul class="detail_white" id="phone_content">            
            </ul>       
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
                                  <!-- <div class="div_float div_float_ie6" id="showMob3G"> 
                                   <t:select id="mob_section_3G" name = "mob_section_3G" codeType="mob_section_3G" ></t:select>
                                </div>
                                <div class="div_float div_float_ie6" id="showMob4G"> 
                                   <t:select id="mob_section_4G" name = "mob_section_4G" codeType="mob_section_4G" ></t:select>
                                </div>-->
    
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
            	<div class="wrap" id="list">
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
        
        <div id="fee" style="display:none;">
        	<ul class="detail_white">
        		<div class="line_red_top"></div>
        		<div id ="mode_select">
        		 <div class="handle">
                    <div class="handle_btn_left handle_btn_left_clicked" id="mode_scene"><a href="javascript:void(0)" >场景模式办理</a></div>
                    <div class="handle_btn_right " id="mode_quick"><a href="javascript:void(0)">快速业务办理</a></div>
                </div>				
            	</div>
				<div class="clear"></div>
                <div id="mode_scene_detail">
        		<li class="list_white">
                    <div class="left">
                        <div class="left_lable"><span class="space24"></span><span class="space24"></span>资费名称：
                            <input type="text" id="tariff_select" placeholder="" class="input_text width_21" readOnly="true" ></input>
                            <span class="bold red">*</span>
                        </div>                    
                        <div class="left_lable">                        	
                            <a href="javascript:void(0)" onClick="GetTariffName('','0')">
                                <div class="input_button">选  择</div>
                            </a>
                        </div>
                        <!--  <div class="left_lable">
                            <a href="javascript:void(0)" onClick="showdiv('fee_condition_select')">
                                <div class="button_able">智  选</div>
                            </a>
                        </div>-->
                    </div>
                    <div class="right" id="first_month_fee">
                        <div class="left_lable"><span class="space24"></span><span class="space24"></span>入网当月资费：
                            <t:select id="first_month_fee_select" name = "first_month_fee_select"  codeType="first_month_fee" ></t:select>
                        	<span class="bold red">*</span>
                        </div>
							
                    </div>                    
                </li>
                <li class="list_white">
                	<div class="left">
                        <div class="left_lable"><span class="space36"></span><span id="ywb">可选业务包</span>：
                            <input type="text" id="ywb_select" placeholder="" class="input_text width_21" readOnly="true" ></input>
                        </div>                    
                        <div class="left_lable">
                            <span class="space6"></span>
                            <a href="javascript:void(0)" onClick="GetYwbName('','0')">
                                <div class="input_button">选  择</div>
                            </a>
                        </div>
                    </div>
                    <div class="right">
                        <div class="left_lable"><span class="space36"></span><span class="space36"></span>活动类型：
                            <select id="activity_type_select"  class="select_down" onChange="GetGuaranteeType();">
                      	  </select>
                        </div>
              
                    </div>
                </li>
                <li class="list_white">
                	<div class="right">
                        <div class="left_lable"><span class="space36"></span><span class="space36"></span>担保类型：
                             <select id="guarantee_type_select"  class="select_down" >
                            <option></option>
                      	  </select>
                        </div>                    
                    </div>
                </li>
                <li class="list_white">
                	<div class="left">
                        <div class="left_lable"><span class="space24"></span><span class="space24"></span>终端串号：
                            <input type="text" id="mobile_no" placeholder="" class="input_text width_21" ></input>
                        </div>                    
                        <div class="left_lable">
                            <span class="space6"></span>
                            <a href="javascript:void(0)" onClick="CheckMobileNo('0')" id="check_mobile1">
                                <div class="input_button">校  验</div>
                            </a>
                            <a href="javascript:void(0)" onClick="CancelMobileNo()" id="check_mobile2">
                                <div class="input_button" >清  空</div>
                            </a>
                        </div>
                    </div>
                    <div class="right">
                        <div class="left_lable"><span class="space36"></span><span class="space36"></span>可选活动：
                            <input type="text" id="activity_select" placeholder="" readOnly="true"  class="input_text width_20"></input>
                        </div>
                        <div class="left_lable">
                            <a href="javascript:void(0)" onClick="GetActivityName('','0')">
                                <div class="input_button">选  择</div>
                            </a>
                        </div>
                    </div>
                </li>
                <li class="list_white">
                	<div class="left">
                        <div class="left_lable"><span class="space36"></span><span class="space36"></span>品牌：
                            <input type="text" id="brand" placeholder="" class="input_text width_21" readOnly="true" ></input>
                        </div>                    
                    </div>
                       <div class="right" id="huodongywb">
                        <div class="left_lable"><span class="space36"></span><span class="space24"></span>活动业务包：
                            <input type="text" id="gift_activity_select" placeholder="" readOnly="true"  class="input_text width_20"></input>
                        </div>
                        <div class="left_lable">
                            <a href="javascript:void(0)" onClick="GetGiftActivityName('','0')">
                                <div class="input_button">选  择</div>
                            </a>
                        </div>
                    </div>
                </li>
                <li class="list_white">
                	<div class="left">
                        <div class="left_lable"><span class="space36"></span><span class="space36"></span>型号：
                            <input type="text" id="model"  placeholder="" class="input_text width_21" readOnly="true" ></input>
                        </div>                    
                    </div>
                    <div class="right" id="dev_div">
                        <div class="left_lable"><span class="space36"></span><span class="space24"></span><span class="space24"></span>发展人：
                            <input type="text" id="dev_name" placeholder="" readOnly="true"  class="input_text width_20"></input>
                            <span class="bold red">*</span>
                        </div>
                        <div class="left_lable">
                            <a href="javascript:void(0)" onClick="QueryDevName('')">
                                <div class="input_button">选  择</div>
                            </a>
                        </div>
                    </div>
                </li>
                <li class="list_white">
                	<div class="left">
                        <div class="left_lable"><span class="space36"></span><span class="space36"></span>颜色：
                            <input type="text" id="color"  placeholder="" class="input_text width_21" readOnly="true" ></input>
                        </div>                    
                    </div>
                </li>
                </div>
                
                 <ul id="mode_quick_detail">
                <div class="handle_type" id="quick_detail_handle_type">
                	<a href="javascript:void(0)"><div class="handle_type_title" id="mode_scene_1">明星手机直降，赠送48G流量</div></a>
                    <a href="javascript:void(0)"><div class="handle_type_title handle_type_title_clicked" id="mode_scene_2">一年电话免费打</div></a>
                    <a href="javascript:void(0)"><div class="handle_type_title" id="mode_scene_3">99元本地存费送机合约</div></a>
                    <a href="javascript:void(0)"><div class="handle_type_title" id="mode_scene_4">购机直降合约</div></a>
                </div>
                
                <div class="handle_detail">
                	<div class="handle_detail_row">
                        <div class="float_left"><span class="space24"></span>终端串号：</div>
                        <div class="float_left"><input type="text" placeholder="" class="input_text" id="mobile_no_group" ></input></div>
                        <div class="left_lable">
                             <a href="javascript:void(0)" onClick="CheckMobileNo('1')" id="check_mobile3">
                                <div class="input_button">校  验</div>
                            </a>
                            <a href="javascript:void(0)" onClick="CancelMobileNo()" id="check_mobile4">
                                <div class="input_button" >清  空</div>
                            </a>
                        </div>
                    </div>
                    <div class="handle_detail_row">
                        <div class="float_left"><span class="space36"></span><span class="space12"></span>品牌：</div>
                        <div class="float_left" id="group_brand"></div>
                    </div>
                    <div class="handle_detail_row">
                        <div class="float_left"><span class="space36"></span><span class="space12"></span>型号：</div>
                        <div class="float_left" id="group_model"></div>
                    </div>
                    <div class="handle_detail_row">
                        <div class="float_left"><span class="space36"></span><span class="space12"></span>颜色：</div>
                        <div class="float_left" id="group_color"></div>
                    </div>
                    
                    <div class="handle_detail_row">
                        <div class="float_left"><span class="space24"></span>组合名称：</div>
                        <div class="float_left"><input type="text" id="group_product_select" placeholder="" class="input_text width_21" readOnly="true" ></input></div>
                        <div class="left_lable">
                             <a href="javascript:void(0)" onClick="GetGroupName('','0')">
                                <div class="input_button">选  择</div>
                            </a>
                        </div>
                    </div>
                    
                    <div class="handle_detail_row">
                        <div class="float_left">入网当月资费：</div>
                        <div class="float_left">
                        	<t:select id="first_month_fee_select_group" name = "first_month_fee_select_group"  codeType="first_month_fee" ></t:select>
                        </div>
                    </div>
                    <div class="handle_detail_row red">
                        <div class="float_left"><span class="space24"></span>温馨提醒：</div>
                        <div class="float_left" id="rwzf_hint"></div>
                    </div>
                    
                    <div class="handle_detail_row" id="agent_flag">
                     	<div class="float_left"><span class="space36"></span>发展人：</div>
                        <div class="float_left">
                            <input type="text" id="dev_name_busi"  flag="true" placeholder="" readOnly="true"  class="input_text width_21"></input>
                            <span class="bold red">*</span>
                        </div>
                        <div class="left_lable">
                            <a href="javascript:void(0)" onClick="QueryDevName('1')">
                                <div class="input_button">选  择</div>
                            </a>
                        </div>
                    </div>
                    
                    <div class="handle_detail_row">
                        <div class="float_left"><span class="space24"></span>组合描述：</div>
                        <div class="float_left" id="group_product_detail"></div>
                    </div>
                    
                     
                    
                    
                    <div class="padding_blank10"></div>
                    <div class="padding_blank10"></div>
                    <div class="padding_blank10"></div>
                    <div class="padding_blank10"></div>
                    <div class="padding_blank10"></div>
                    <div class="padding_blank10"></div>
                    <div class="padding_blank10"></div>
                    <div class="padding_blank10"></div>
                    <div class="padding_blank10"></div>
                    <div class="text_large">
                        <div class="ok_disabled" id="group_next_div"><a id="group_next" href="javascript:void(0)">下一步</a></div>
                        <!-- <div class="ok_disabled"><a href="###">确  定</a></div> -->
                    </div>
                    <div class="padding_blank10"></div>
                    
                </div>                 
                 
                 
                 </ul>
                
                <div class="line_dashed_h"></div>
                
                
            
            </ul>
       
        </div>
         <li class="text_large">
                <div class="ok_disabled" id="step2_next_div" ><a id="step2_next">确  定</a></div>
                <!-- <div class="ok_disabled"><a href="javascript:void(0);">确  定</a></div> -->
            </li>
        <div class="padding_blank"></div>
    	
    </div> 
    </ul>
    <ul id="step3">   
    <div class="info">
    	<div class="title text_big">
        	<span class="text_large24">3</span>意向订单
        </div>
		
        <ul class="detail">
        	<li class="list">
            	<div class="left">
                    <div class="left_lable">用户姓名：</div>
                    <div class="left_data">
                		<input id="c_customer_name" type="text" placeholder=""  readOnly="true" ></input>
               		</div>
                   <!--  <a href="#step1"><div class="input_button" onClick="GoToStep(1)">修  改</div></a>  -->
                </div>
                <div class="right">
                	<div class="left_lable">证件号码：</div>      
                	<div class="left_data" id="c_id_number"></div>              
                </div>            
            </li>
            <div class="line_dashed_h"></div>
			
            <li class="list bold">号码：</li>             
            <li class="list">
            	<div class="left">
                	<div class="left_lable"></div>
                    <div class="left_data">
                		<input type="text" id="c_number" placeholder="" readOnly="true" ></input>
               		</div>
                    <a><div class="input_button" onClick="GoToStep(2)">修  改</div></a>
                </div>
            </li>
            <li class="list">
            	<div class="left" id="c_number_detail"></div>
            </li>          
            <div class="line_dashed_h"></div>
            
            <li class="list bold">资费：</li>             
            <li class="list">
            	<div class="left">
                	<div class="left_lable">套<span class="space24"></span>餐：</div>
                    <div class="left_data">
                		<input type="text" id="c_tariff" placeholder="" readOnly="true" class="width_32"></input>
               		</div>                    
                </div>
                <div class="right">
                	<div class="left_lable">当&nbsp;月&nbsp;资&nbsp;费：</div>
                    <div class="left_data">
                		<input type="text" id="c_first_month_fee" placeholder="" readOnly="true" ></input>
               		</div>
                    <a><div class="input_button" onClick="GoToStep(3)">修  改</div></a>
                </div>
            </li>
            <li class="list">
            	<div class="left">
                	<div class="left_lable">活<span class="space24"></span>动：</div>
                    <div class="left_data">
                		<input type="text" id="c_activity" placeholder="" readOnly="true" class="width_32"></input>
               		</div>                    
                </div>
                <div class="right">
                	<div class="left_lable">活动业务包：</div>
                    <div class="left_data">
                		<input type="text" id="c_discntName" placeholder="" readOnly="true"></input>
               		</div>                    
                </div>

            </li>
            <div class="line_dashed_h"></div>
            <ul id="yixiang_phone">
            <li class="list bold">手机：</li>             
            <li class="list">
            	<div class="left">
                	<div class="left_lable"></div>
                    <div class="left_data">
                		<input type="text" id="c_phone" placeholder="" readOnly="true"></input>
               		</div>
                    <a><div class="input_button" onClick="GoToStep(4)">修  改</div></a>            
                </div>
            </li>
         	<li class="list">
            	<div class="left">
                	<div class="left_lable"></div>
                    <div class="left_data">
                		<input type="text" id="c_mobile_no" placeholder="" readOnly="true"></input>
               		</div>                    
                </div>
            </li>
            <div class="line_dashed_h"></div>
            </ul>

        </ul>
        
		<div class="padding_blank"></div>
            
        <div class="band_dashed text_large">
            <div class="ok" id="step3_next_div" ><a id="step3_next" href="javascript:void(0);">确  定</a></div>
            <!-- <div class="ok_disabled"><a href="javascript:void(0);">确  定</a></div> -->
        </div>   
    	
  </div> 
     </ul>
     <ul id="step4">
    <div class="info">
    	<div class="title text_big">
        	<span class="text_large24">4</span>费用确认
        </div>

    	<ul class="detail" >
           <ul id="fee_list">
            <!--<li class="list bold red">
                <a class="tip" href="javascript:void(0)" onClick="showdiv('charge_apply')">合约预存款
                    <div class="ang_z">&#9670 </div>
                    <div class="ang_y">&#9670 </div>
                    <span class="tip_info">点击可申请优惠减免</span>
                </a>
            </li>        
            <li class="list">
            	<div class="left">
                	<div class="left_lable">
                    	应收：<span class="bold">100</span>&nbsp;元
                    </div>
                    <div class="right_data">
                    	实收：<input type="text" placeholder="0.00" class="input_text width_8 text_normal_b">&nbsp;元
               		</div>
                </div>
            </li>
            <div class="line_dashed_h"></div>-->     
            </ul> 
          
            
            <li class="list bold">总金额：
            <span class="bold" id="fee_all"></span>&nbsp;元 </li>    
            <div class="line_dashed_h"></div>
            
            <li class="list" id ="pay_type_select">
            	<div class="left">
                	<div class="left_lable">支付方式：
                    	<t:select id="pay_type_mobile_2G" name = "pay_type_mobile_2G" codeType="pay_type_mobile_2G" onchange="payTypeChange(this);" ></t:select>
                    	<t:select id="pay_type_mobile_3G" name = "pay_type_mobile_3G" codeType="pay_type_mobile_3G" onchange="payTypeChange(this);"></t:select>
                    	<t:select id="pay_type_mobile_4G" name = "pay_type_mobile_4G" codeType="pay_type_mobile_4G" onchange="payTypeChange(this);"></t:select>
                    </div>   
                    
                   <!-- <div class="left_lable">
                    	<a href="javascript:void(0);" onclick="paymentOrderBill();">
                            <div class="button">
                                <div class="button_disabled">票据打印</div>
                            </div>
                        </a>
                    </div>-->      
                    
                </div>
                <div class="right" id="pos_info" style="display:none">
                   <div class="left_lable">流水号：
                    </div>   
                    <div class="left_data">
                    <input type="text" placeholder="" id="pos_sn"></input>
                     </div> 
                   </div>
                
            </li>
            <li class="list"></li>
            
	        <div class="band_dashed text_large">
	        	 <div class="ok" id="btnGetFee" ><a onClick="getFee();" href="javascript:void(0);">重新获取费用</a></div>
	             <div class="ok" id="step4_next_div" ><a id="step4_next" href="javascript:void(0);">确  定</a></div>	           
	            <!-- <div class="ok_disabled"><a href="javascript:void(0);">确  定</a></div> -->
	        </div>   

        </ul>
        
    </div> 
    </ul>
   
    
    	
  <div class="clear"></div>
</div>

<div class="bg_mask" id="bg_mask">
	<iframe class="bg_mask_iframe"> </iframe>
</div>



<!-- 弹出窗口 手机-调拨申请： -->
<div class="pop_win" id="phone_apply" style="display:none">
    <div class="msgbox">
        <ul class="text_big">
            <li><span class="bold">调拨申请：</span></li>
        </ul>
        <div class="line_dashed_h"></div>
        <ul>
            <li id="phone_from"><span class="bold">从：</span>南宁市江南区星光大道34号香格里拉嘉路自营厅-3G销售店</li>
            <li id="phone_to"><span class="bold">至：</span>南宁市江南区白沙大道24号白沙嘉路自营厅-3G销售店</li>
        </ul>
        <div class="line_dashed_h"></div>
        <ul>
            <li id="phone_model">机型：苹果(APPLE)iPhone 6 Plus (A1542金)</li>
            <li id="phone_num">数量：1台</li>
        </ul>
        <div class="line_dashed_h"></div>
        <ul>
            <li class="center">
                <a href="javascript:void(0)" id="diaobo"><div class="input_button">申 请</div></a>
                <a href="javascript:void(0)" onClick="hidediv('phone_apply')"><div class="input_button">取 消</div></a>
            </li>          
        </ul>
    </div>
</div>

 

<!-- 弹出窗口 资费-套餐选择： -->
<div class="pop_win" id="fee_search" style="display:none;">
    <div class="msgbox">
    	<a href="javascript:hidediv('fee_search');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">资费</span></li>
        </ul>
            <div class="band_phone_small">
                <input type="text" class="search_input_small" placeholder="输入资费关键字模糊查询" id="TariffInput"></input>
                <a href="javascript:void(0)"><div class="input_button" id="searchTariff">搜  索</div></a>
            </div>
        <div class="scroll_v" style="position:relative;">
		<ul id="tariff_list">                       
            <li class="list">
                <div class="left">
                    <div class="left_lable">沃3G-基本套餐A（66）</div>
                    <div class="right_data">
                        <input name="fee_search_check" type="radio"></input>
                    </div>
                </div>
            </li>
        </ul>
        </div>
        <div class="line_dashed_h"></div>
		<ul>
         <li class="center">
        <a href="javascript:void(0)" onClick="tariffConfirm();"><div class="input_button">确 定</div></a> 
       	<a href="javascript:void(0)" onClick="tariffDelete();"><div class="input_button">清 空</div></a>    		
        </li></ul>      
           
    </div>
</div>


<!-- 弹出窗口 组合选择： -->
<div class="pop_win" id="group_search" style="display:none;">
    <div class="msgbox">
    	<a href="javascript:hidediv('group_search');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">组合</span></li>
        </ul>
            <div class="band_phone_small">
                <input type="text" class="search_input_small" placeholder="输入资费关键字模糊查询" id="GroupInput"></input>
                <a href="javascript:void(0)"><div class="input_button" id="searchGroup">搜  索</div></a>
            </div>
        <div class="scroll_v">
		<ul id="group_list">                       
            <li class="list">
                <div class="left">
                    <div class="left_lable"></div>
                    <div class="right_data">
                        <input name="group_search_check" type="radio"></input>
                    </div>
                </div>
            </li>
        </ul>
        </div>
        <div class="line_dashed_h"></div>
		<ul>
         <li class="center">
        <a href="javascript:void(0)" onClick="groupConfirm();"><div class="input_button">确 定</div></a> 
       	<a href="javascript:void(0)" onClick="groupDelete();"><div class="input_button">清 空</div></a>    		
        </li></ul>      
           
    </div>
</div>




<!-- 弹出窗口 资费-可选业务包： -->
<div class="pop_win" id="fee_option_busi" style="display:none">
    <div class="msgbox">
    	<a href="javascript:hidediv('fee_option_busi');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">可选业务包：</span></li>
        </ul>
            <div class="band_phone_small">
                <input type="text" class="search_input_small" placeholder="输入业务包关键字模糊查询" id="YwbInput"></input>
                <a href="javascript:void(0)" onClick=""><div class="input_button" id="searchYwb">搜  索</div></a>
            </div>
         <div class="scroll_v">
		<ul id="ywb_list">                        
            <div class="line_dashed_h"></div>
        </ul>
        </div>
        <ul>
         <li class="center">
        <a href="javascript:void(0)" onClick="ywbConfirm();"><div class="input_button" id="ywb_sure">确 定</div></a> 
       	<a href="javascript:void(0)" onClick="ywbDelete();"><div class="input_button">清 空</div></a>    		
        </li></ul>   
        
    </div>
</div>



<!-- 弹出窗口 资费-可选活动： -->
<div class="pop_win" id="fee_active_option" style="display:none">
    <div class="msgbox">
    	<a href="javascript:hidediv('fee_active_option');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">可选活动：</span></li>
        </ul>
            <div class="band_phone_small">
                <input type="text" class="search_input_small" placeholder="输入活动关键字模糊查询"  id="ActivityInput"></input>
                <a href="javascript:void(0)" onClick=""><div class="input_button" id="searchActivity">搜  索</div></a>
            </div>
         <div class="scroll_v">
		<ul id="activity_list">                        
            <div class="line_dashed_h"></div>
        </ul>
        </div>
        <ul>
         <li class="center">
        <a href="javascript:void(0)" onClick="activityConfirm();"><div class="input_button">确 定</div></a> 
       	<a href="javascript:void(0)" onClick="activityDelete();"><div class="input_button">清 空</div></a>    		
        </li></ul>   
        
    </div>
</div>


<!-- 弹出窗口 资费-活动业务包： -->
<div class="pop_win" id="gift_active_option" style="display:none">
    <div class="msgbox">
    	<a href="javascript:hidediv('gift_active_option');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">可选活动：</span></li>
        </ul>
            <div class="band_phone_small">
                <input type="text" class="search_input_small" placeholder="输入活动关键字模糊查询"  id="GiftActivityInput"></input>
                <a href="javascript:void(0)" onClick=""><div class="input_button" id="searchGiftActivity">搜  索</div></a>
            </div>
         <div class="scroll_v">
		<ul id="gift_activity_list">                        
            <div class="line_dashed_h"></div>
        </ul>
        </div>
        <ul>
         <li class="center">
        <a href="javascript:void(0)" onClick="giftActivityConfirm();"><div class="input_button">确 定</div></a> 
       	<a href="javascript:void(0)" onClick="giftActivityDelete();"><div class="input_button">清 空</div></a>    		
        </li></ul>   
        
    </div>
</div>



<!-- 弹出窗口 提示信息 -->
<div class="pop_win" id="pop_win_msg" style="display:none">
    <div class="msgbox">
    	<a href="javascript:hidediv('pop_win_msg');"><div class="msgbox_close"></div></a>
        <ul class="msg">
        	<li>
            <div><span class="space24"></span><span class="text_big" id="err_msg">错误信息在此填写，如：读卡错误,请检查您的控件或驱动是否正确安装最新版本</span></div>
            <div class="line_dashed_h"></div>
            <li></li>
            <li></li>
            <li></li>
            <div class="clear"></div>
            <li class="center">
                <a href="javascript:void(0)" onClick="hidediv('pop_win_msg');"><div class="input_button">确  定</div></a>
        </ul>
    </div>
</div>

<!-- 弹出窗口 可选发展人包： -->
<div class="pop_win" id="dev_option" style="display:none">
    <div class="msgbox">
    	<a href="javascript:hidediv('dev_option');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">可选发展人：</span></li>
        </ul>
            <div class="band_phone_small">
                <input type="text" class="search_input_small" placeholder="输入发展人名字或手机号模糊查询"  id="dev_name_input"></input>
                <a href="javascript:void(0)" onClick=""><div class="input_button" id="searchDev">搜  索</div></a>
            </div>
         <div class="scroll_v">
		<ul id="dev_list">                        
            <div class="line_dashed_h"></div>
        </ul>
        </div>
        <ul>
         <li class="center">
        <a href="javascript:void(0)" onClick="devConfirm();"><div class="input_button">确 定</div></a> 
       	<a href="javascript:void(0)" onClick="devDelete();"><div class="input_button"> 取  消</div></a>    		
        </li></ul>   
        
    </div>
</div>
	
	
	<input type="hidden" id="customer_sex" 	value="0">
	<input type="hidden" id="nation_id" 	value="0">
	<input type="hidden" id="born_date_val" 	value="0">
	<input type="hidden" id="idcard_addr" 	value="0">
	<input type="hidden" id="valid" 	value="0">
	<input type="hidden" id="order_id" 	value="0">
	
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
	<input type="hidden" id="province_code"  value="${province_code}"/><!--省份标识 --> 
	<input type="hidden" id="write_way"  value="${write_way}"/><!--模拟写卡标识 --> 
	<input type="hidden" id="ms_flag"  value="${ms_flag}"/><!--末梢代理商标识 --> 
	<input type="hidden" id="wt_flag"  value="${wt_flag}"/><!--协同标识 --> 
	<input type="hidden" id="ori_oper_no"  value="${ori_oper_no}"/><!--原生操作员 --> 
	<input type="hidden" id="jsessionid"  value="${jsessionid}"/><!--jsessionid -->
	
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
	<!--备注信息:--><input type="hidden" size=50 maxlength=100 id="memo">
 

<div class="copyright">
Copyright © 2014 China unicom All Right Reserved
</div>

</body>
</html>