<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
<%@ taglib prefix="t" uri="http://tydic.com/jsp/core" %>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>产品打包</title>

<link href="<%=fullPath%>css/share1.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/card.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/showOrder.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/pre_accept.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/jquery.jUploader-1.0.js"></script>
<script type="text/javascript" src="<%=fullPath%>js/productPage.js"></script> 
</head>
<body>
  <div class="show" style="height:auto;">
  		<div class="info">
            <div class="title text_big">
                <span class="text_large24">1</span>终端信息
            </div>           
            <ul class="detail">
                <div class="padding_blank10"></div>
                <div style="*padding-left:20px;">
                    <li class="list" style="*display:inline;width:910px;">
                        <div class="right_data">
                          <a href="javascript:void(0)" onClick="queryPhonetri('');"><div class="input_button">选择</div></a>
		                 <a href="javascript:void(0)" id="add_tag"><div class="input_button">新增</div></a>
		                 <!-- <a href="javascript:void(0)" id="queryRule"><div class="input_button">删除</div></a>    -->                    
                        </div>
                       
                    </li>                 
                    <li class="list" style="*display:inline;">
                        <div class="order_row">
                          <div class="order_cell" style="width:250px;">机型代码</div>                                               
                          <div class="order_cell" style="width:250px;">机型名称</div>
                          <div class="order_cell" style="width:250px;">颜色</div>
                            
                        </div>
                    </li>
                    <div id="list_1">
                     <li class="list" style="*display:inline;">
                     <div class="order_row white_bgcolor">
                     <div class="order_cell white_bgcolor" style="width:250px;"></div>
                     <div class="order_cell white_bgcolor" style="width:250px;"></div>
                     <div class="order_cell white_bgcolor" style="width:250px;"></div>
                    
                     </div>
                     </li>
                     </div>

                    </div>
                        
                  <div class="padding_blank"></div>
                				 
                     </ul>            
                </div>
            
		<div class="info">
            <div class="title text_big">
                <span class="text_large24">2</span>产品配置
            </div>           
            <ul class="detail">
                <div class="padding_blank10"></div>
                <div style="*padding-left:20px;">
                    <li class="list" style="*display:inline;width:910px;">
                        <div class="right_data">
                          <a href="javascript:void(0)" onClick="queryProduct('');"><div class="input_button">选择</div></a>		                                 
                        </div>
                       
                    </li>

                    <li class="list" style="*display:inline;">
                        <div class="order_row">
                          <div class="order_cell" style="width:250px;">产品编码</div>                                               
                          <div class="order_cell" style="width:250px;">产品名称</div>
                          <div class="order_cell" style="width:250px;">产品详情</div>
                            
                        </div>
                    </li>
                    <div id="list_2">
                     <li class="list" style="*display:inline;">
                     <div class="order_row white_bgcolor">
                     <div class="order_cell white_bgcolor" style="width:250px;"></div>
                     <div class="order_cell white_bgcolor" style="width:250px;"></div>
                     <div class="order_cell white_bgcolor" style="width:250px;"></div>
                    
                     </div>
                     </li>
                    </div>
                    
                </div>
                
                <div class="padding_blank"></div>
                
            </ul>
            
        </div>
     	<div class="info">
            <div class="title text_big">
                <span class="text_large24">3</span>总部合约活动配置
            </div>           
            <ul class="detail">
                <div class="padding_blank10"></div>
                <div style="*padding-left:20px;">
                    <li class="list" style="*display:inline;width:910px;">
                        <div class="right_data">
                          <a href="javascript:void(0)" onClick="queryZbActivity('');"><div class="input_button">选择</div></a>		                                 
                        </div>
                       
                    </li>

                    <li class="list" style="*display:inline;">
                        <div class="order_row">
                          <div class="order_cell" style="width:300px;">总部活动代码</div>                                               
                          <div class="order_cell" style="width:300px;">总部活动名称</div>
                           
                        </div>
                    </li>
                    <div id="list_3">
                     <li class="list" style="*display:inline;">
                     <div class="order_row white_bgcolor">
                     <div class="order_cell white_bgcolor" style="width:300px;"></div>                               
                     <div class="order_cell white_bgcolor" style="width:300px;"></div>
                     </div>
                     </li>
                    </div>
                    
                </div>
                
                <div class="padding_blank"></div>
                
            </ul>
            
        </div>    
        
        	<div class="info">
            <div class="title text_big">
                <span class="text_large24">4</span>本地合约活动配置
            </div>           
            <ul class="detail">
                <div class="padding_blank10"></div>
                <div style="*padding-left:20px;">
                    <li class="list" style="*display:inline;width:910px;">
                        <div class="right_data">
                          <a href="javascript:void(0)" onClick="querylocalnetActivity('')"><div class="input_button">选择</div></a>		                                 
                        </div>
                       
                    </li>

                    <li class="list" style="*display:inline;">
                        <div class="order_row">
                          <div class="order_cell" style="width:300px;">活动代码</div>                                               
                          <div class="order_cell" style="width:300px;">活动名称</div>
                           
                        </div>
                    </li>
                    <div id="list_4">
                     <li class="list" style="*display:inline;">
                     <div class="order_row white_bgcolor">
                     <div class="order_cell white_bgcolor" style="width:300px;"></div>                               
                     <div class="order_cell white_bgcolor" style="width:300px;"></div>
                     </div>
                     </li>
                    </div>
                    
                </div>
                
                <div class="padding_blank"></div>
                
            </ul>
            
        </div> 
        
      
        	<div class="info">
            <div class="title text_big">
                <span class="text_large24">5</span>叠加服务套餐
            </div>           
            <ul class="detail">
                <div class="padding_blank10"></div>
                <div style="*padding-left:20px;">
                    
                    <li class="list" style="*display:inline;width:910px;">
                        <div class="right_data">
                          <a href="javascript:void(0)" onClick="queryywb('')"><div class="input_button">选择</div></a>		                                 
                        </div>
                       
                    </li>
                    <li class="list" style="*display:inline;">
                        <div class="order_row">
                          <div class="order_cell" style="width:300px;">服务套餐编码</div>                                               
                          <div class="order_cell" style="width:300px;">服务套餐名称</div>
                           
                        </div>
                    </li>
                    <div id="list_5">
                     <li class="list" style="*display:inline;">
                     <div class="order_row white_bgcolor">
                     <div class="order_cell white_bgcolor" style="width:300px;"></div>                               
                     <div class="order_cell white_bgcolor" style="width:300px;"></div>
                     </div>
                     </li>
                    </div>
                    
                </div>
                
                <div class="padding_blank"></div>
                
            </ul>
            
        </div>      
     	<div class="info">
            <div class="title text_big">
                <span class="text_large24">6</span>设置生效地市
            </div>           
            <ul class="detail">
                <div class="padding_blank10"></div>
                <div style="*padding-left:20px;">
                    
                    <li class="list" style="*display:inline;width:910px;">
                       <div class="left">
                            <div class="left_lable">生效地市选择：</div>
                          	<select type="select" name="city_list" id="city_list" style="width:100px" onchange="">
            	             <option value="*" selected="selected">全区</option>  
          	                 </select>
                        </div>
                       
                    </li>
                
                </div>
                
                <div class="padding_blank"></div>
                
            </ul>
            
        </div>     
        <div class="info">
            <div class="title text_big">
                <span class="text_large24">7</span>图片上传
            </div>           
            <ul class="detail">

                <div style="*padding-left:20px;">
                    <li class="list float_left" style="*display:inline;width:335px; height:400px;">
                       <div class="">
							<img id="upload_ImgTag" src="<%=fullPath%>/images/product_defunt.jpg" width="335" height="400"/>
                        </div>
                    </li>
                   <li class="list" style="*display:inline;">
                        <div class="left">
                            <a href="javascript:void(0)">
                               <div id="uploadbutton" class="input_button">上传</div>
                            </a>
                        </div>
                    </li>
                  <li class="list" style="*display:inline;">
                        <div class="left">
                         <font color="red"> 目前只支持jpeg、jpg、 gif、png格式图片,上传图片不能大于4M</font>
                        </div>
                    </li>
                     <div class="clear"></div>
                    <li class="list">
                        <div class="left">
                            <div class="left_lable">产品打包说明：</div>
                            <div class="left_data">
                                 <textarea rows="3" cols="32" id="img_desc" class="name"></textarea>   
                            </div>
                        </div>            
                    </li>
                    
                  
                </div>
                
                <div class="padding_blank"></div>
                
            </ul>
            
        </div>  
         <div class="padding_blank10"></div>
          <div class="padding_blank10"></div>
         
         <li class="list" style="*display:inline;">
            <div class="left"></div>
            <div class="left_data">
                <a href="javascript:void(0)" onClick="pageSelect();">
                   <div class="input_button">添加</div>
                </a>
            </div>
        </li>
        <div class="info">
            <div class="title text_big">
                <span class="text_large24">8</span>已设置数据
            </div>           
            <ul class="detail">
                <div class="padding_blank10"></div>
                <div style="*padding-left:20px; overflow-x:scroll;">
                    
                    <div class="padding_blank10"></div>
                    <li class="list" style="*display:inline;">
                        <div class="order_row" style=" width:2000px;">
                          <div class="order_cell" style="width:83px;">序号</div>
                            <div class="order_cell" style="width:100px;">图片</div>
                            <div class="order_cell" style="width:100px;">机型代码</div>                         
                            <div class="order_cell" style="width:150px;">机型名称</div>
                            <div class="order_cell" style="width:100px;">颜色</div>
                            <div class="order_cell" style="width:100px;">产品编码</div>                            
                            <div class="order_cell" style="width:150px;">产品名称</div>
                            <div class="order_cell" style="width:150px;">总部活动代码</div>
                            <div class="order_cell" style="width:150px;">总部活动名称</div>                          
                            <div class="order_cell" style="width:150px;">本地活动代码</div>
                            <div class="order_cell" style="width:150px;">本地活动名称</div>
                            <div class="order_cell" style="width:100px;">服务套餐编码</div>
                            <div class="order_cell" style="width:150px;">服务套餐名称</div>
                            <!--  <div class="order_cell" style="width:100px;">营销活动代码</div>
                            <div class="order_cell" style="width:150px;">营销活动名称</div>-->
                            <div class="order_cell" style="width:100px;">生效地市</div>
                           
                        </div>
                    </li>
                  <div id="list_img">
                     <li class="list" style="*display:inline;">
                            <div class="order_row white_bgcolor" style=" width:2000px;">
                                <div class="order_cell" style="width:83px;"></div>                                                               
                                <div class="order_cell" style="width:100px;"></div>                               
                                <div class="order_cell" style="width:150px;"></div>
                                <div class="order_cell" style="width:100px;"></div>                               
                                <div class="order_cell" style="width:150px;"></div>
                                <div class="order_cell" style="width:150px;"></div>                               
                                <div class="order_cell" style="width:100px;"></div>
                                <div class="order_cell" style="width:150px;"></div>                               
                                <div class="order_cell" style="width:100px;"></div>
                                <div class="order_cell" style="width:150px;"></div>                               
                                <div class="order_cell" style="width:100px;"></div>
                                <div class="order_cell" style="width:150px;"></div>                               
                               <!--  <div class="order_cell" style="width:100px;"></div>
                                <div class="order_cell" style="width:150px;"></div> -->                              
                                <div class="order_cell" style="width:100px;"></div>
                            </div>
                     	</li>
                    </div>
                    <div class="padding_blank10"></div>
                </div>
               
            </ul>
            
        </div>  
         
       <div class="padding_blank10"></div>
         <li class="text_large">
                <div class="ok" id="step2_next_div" ><a id="step2_next" onClick="allInsert()" style="cursor:pointer;">批量保存</a></div>
                               
            </li>
    	
    </div> 
    </ul>


   
    
    	
  <div class="clear"></div>
</div>

<div class="bg_mask" id="bg_mask">
	<iframe class="bg_mask_iframe"> </iframe>
</div>

<!-- 弹出窗口终端信息选择： -->
<div class="pop_win" id="fee_search" style="display:none;">
    <div class="msgbox">
    	<a href="javascript:hidediv('fee_search');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">终端查询</span></li>
        </ul>
            <div class="band_phone_small">
                <input type="text" class="search_input_small" placeholder="输入机型名称模糊查询" id="TariffInput"></input>
                <a href="javascript:void(0)"><div class="input_button" id="searchTariff">搜  索</div></a>
            </div>
        <div class="scroll_v" style="position:relative;">
         
		<ul id="phoneTri_list"> 
	          <li class="list" style="*display:inline;">
                 <div class="order_row">
                   <div class="order_cell" style="width:100px;">机型代码</div>                                               
                   <div class="order_cell" style="width:160px;">机型名称</div>
                   <div class="order_cell" style="width:100px;">颜色</div>
                     <div class="order_cell" style="width:100px;"></div>                      
                 </div>
                </li>
                <li class="list" style="*display:inline;">
                     <div class="order_row white_bgcolor">
                     <div class="order_cell white_bgcolor" style="width:100px;">2322</div>                               
                     <div class="order_cell white_bgcolor" style="width:160px;">爱国者</div>
                     <div class="order_cell white_bgcolor" style="width:100px;">白色</div>
                     <div  class="order_cell white_bgcolor" style="width:100px;">
                        <input name="phoneTrinal_check" type="radio"></input>
                    </div>
                     </div>
               </li>                             
        </ul>           
        </div>
        <div class="line_dashed_h"></div>
         <div class="center" id="phone_check_tag"><font color="red" id="phone_check_text"></font></div> 
		<ul>			  
         <li >
        <a href="javascript:void(0)" onClick="phoneTrinalConfirm();"><div class="input_button">确 定</div></a> 
        <a href="javascript:void(0)" onClick="deleteTrinalConfirm();"><div class="input_button">删除</div></a> 
       	<a href="javascript:void(0)" onClick="phoneTrinalBack('fee_search');"><div class="input_button">返回</div></a>    		
        </li>
        </ul>      
           
    </div>
</div>

<!-- 弹出窗口终端信息选择添加： -->
<div class="pop_win" id="add_phonetrinal" style="display:none;">
    <div class="msgbox">
    	<a href="javascript:hidediv('add_phonetrinal');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">终端添加</span></li>
        </ul>         
        <div class="scroll_v" style="position:relative;">
         
        <ul>
         <li >           
                              机型代码：<input type="text"  value="请输入机型代码" onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入机型代码'}" id="terminal_model" class="input_text width_18"></input>
         </li>
         <li class="center"> 
         </li>
          <li >   
                                  机型名称：<input type="text"  value="请输入机型名称" onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入机型名称'}" id="terminal_info" class="input_text width_18"></input>
          </li>
          <li class="center"> 
         </li>
          <li >   
                                  机型颜色：<input type="text"  value="请输入机型颜色" onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入机型颜色'}" id="terminal_color" class="input_text width_18"></input>
          </li>
        </ul>                 
        <div class="center" id="check_tag"><font color="red" id="check_text"></font></div>
        </div>
        <div class="line_dashed_h"></div>
		<ul>
         <li class="center">
        <a href="javascript:void(0)" onClick="addTrinalConfirm();"><div class="input_button">添加</div></a> 
       	<a href="javascript:void(0)" onClick="phoneTrinalBack('add_phonetrinal');"><div class="input_button">返回</div></a>    		
        </li></ul>      
           
    </div>
</div>

<!-- 弹出窗口产品选择： -->
<div class="pop_win" id="product_search" style="display:none;">
    <div class="msgbox">
    	<a href="javascript:hidediv('product_search');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">产品查询</span></li>
        </ul>
            <div class="band_phone_small">
                <input type="text" class="search_input_small" placeholder="输入产品名称模糊查询" id="ProductInput"></input>
                <a href="javascript:void(0)"><div class="input_button" id="searchProduct">搜  索</div></a>
            </div>
        <div class="scroll_v" style="position:relative;">
         
		<ul id="product_list"> 
	          <li class="list" style="*display:inline;">
                 <div class="order_row">
                   <div class="order_cell" style="width:100px;">产品编码</div>                                               
                   <div class="order_cell" style="width:160px;">产品名称</div>
                     <div class="order_cell" style="width:100px;"></div>                      
                 </div>
                </li>
                <li class="list" style="*display:inline;">
                     <div class="order_row white_bgcolor">
                     <div class="order_cell white_bgcolor" style="width:100px;">2322</div>                               
                     <div class="order_cell white_bgcolor" style="width:160px;">爱国者</div>
                     <div  class="order_cell white_bgcolor" style="width:100px;">
                        <input name="fee_search_check" type="radio"></input>
                    </div>
                     </div>
               </li>                  
            
        </ul>
        </div>
        <div class="line_dashed_h"></div>
		<ul>
         <li class="center">
        <a href="javascript:void(0)" onClick="productConfirm();"><div class="input_button">确 定</div></a> 
       	<a href="javascript:void(0)" onClick="phoneTrinalBack('product_search');"><div class="input_button">返回</div></a>    		
        </li></ul>      
           
    </div>
</div>

<!-- 弹出窗口总部合约活动选择： -->
<div class="pop_win" id="zbactivity_search" style="display:none;">
    <div class="msgbox">
    	<a href="javascript:hidediv('zbactivity_search');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">活动查询</span></li>
        </ul>
            <div class="band_phone_small">
                <input type="text" class="search_input_small" placeholder="输入活动名称模糊查询" id="zbactivityInput"></input>
                <a href="javascript:void(0)"><div class="input_button" id="searchzbactivity">搜  索</div></a>
            </div>
        <div class="scroll_v" style="position:relative;">
		<ul id="zbactivity_list"> 
	          <li class="list" style="*display:inline;">
                 <div class="order_row">
                   <div class="order_cell" style="width:100px;">总部活动代码</div>                                               
                   <div class="order_cell" style="width:160px;">活动名称</div>
                     <div class="order_cell" style="width:100px;"></div>                      
                 </div>
                </li>
                <li class="list" style="*display:inline;">
                     <div class="order_row white_bgcolor">
                     <div class="order_cell white_bgcolor" style="width:100px;"></div>                               
                     <div class="order_cell white_bgcolor" style="width:160px;"></div>
                     <div  class="order_cell white_bgcolor" style="width:100px;">
                        <input name="fee_search_check" type="radio"></input>
                    </div>
                     </div>
               </li>                  
            
        </ul>
        </div>
        <div class="line_dashed_h"></div>
		<ul>
         <li class="center">
        <a href="javascript:void(0)" onClick="zbactivityConfirm();"><div class="input_button">确 定</div></a> 
       	<a href="javascript:void(0)" onClick="phoneTrinalBack('zbactivity_search');"><div class="input_button">返回</div></a>    		
        </li></ul>      
           
    </div>
</div>

<!-- 弹出窗口本地合约活动选择： -->
<div class="pop_win" id="localnetactivity_search" style="display:none;">
    <div class="msgbox">
    	<a href="javascript:hidediv('localnetactivity_search');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">活动查询</span></li>
        </ul>
            <div class="band_phone_small">
                <input type="text" class="search_input_small" placeholder="输入活动名称模糊查询" id="localnetactivityInput"></input>
                <a href="javascript:void(0)"><div class="input_button" id="searchlocalnetactivity">搜  索</div></a>
            </div>
        <div class="scroll_v" style="position:relative;">
         
		<ul id="localnetactivity_list"> 
	          <li class="list" style="*display:inline;">
                 <div class="order_row">
                   <div class="order_cell" style="width:100px;">本地活动代码</div>                                               
                   <div class="order_cell" style="width:160px;">本地活动名称</div>
                     <div class="order_cell" style="width:100px;"></div>                      
                 </div>
                </li>
                <li class="list" style="*display:inline;">
                     <div class="order_row white_bgcolor">
                     <div class="order_cell white_bgcolor" style="width:100px;"></div>                               
                     <div class="order_cell white_bgcolor" style="width:160px;"></div>
                     <div  class="order_cell white_bgcolor" style="width:100px;">
                        <input name="localnet_search_check" type="radio"></input>
                    </div>
                     </div>
               </li>                  
            
        </ul>
        </div>
        <div class="line_dashed_h"></div>
		<ul>
         <li class="center">
        <a href="javascript:void(0)" onClick="localnetactivityConfirm();"><div class="input_button">确 定</div></a> 
       	<a href="javascript:void(0)" onClick="phoneTrinalBack('localnetactivity_search');"><div class="input_button">返回</div></a>    		
        </li></ul>      
           
    </div>
</div>
<!-- 弹出窗口叠加服务套餐选择： -->
<div class="pop_win" id="ywb_search" style="display:none;">
    <div class="msgbox">
    	<a href="javascript:hidediv('ywb_search');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">活动查询</span></li>
        </ul>
            <div class="band_phone_small">
                <input type="text" class="search_input_small" placeholder="输入服务套餐名称模糊查询" id="ywb_searchInput"></input>
                <a href="javascript:void(0)"><div class="input_button" id="searchywb">搜  索</div></a>
            </div>
        <div class="scroll_v" style="position:relative;">
         
		<ul id="ywb_list"> 
	          <li class="list" style="*display:inline;">
                 <div class="order_row">
                   <div class="order_cell" style="width:100px;">服务套餐编码</div>                                               
                   <div class="order_cell" style="width:160px;">服务套餐名称</div>
                     <div class="order_cell" style="width:100px;"></div>                      
                 </div>
                </li>
                <li class="list" style="*display:inline;">
                     <div class="order_row white_bgcolor">
                     <div class="order_cell white_bgcolor" style="width:100px;"></div>                               
                     <div class="order_cell white_bgcolor" style="width:160px;"></div>
                     <div  class="order_cell white_bgcolor" style="width:100px;">
                        <input name="ywb_search_check" type="radio"></input>
                    </div>
                     </div>
               </li>                  
            
        </ul>
        </div>
        <div class="line_dashed_h"></div>
		<ul>
         <li class="center">
        <a href="javascript:void(0)" onClick="ywbConfirm();"><div class="input_button">确 定</div></a> 
       	<a href="javascript:void(0)" ><div class="input_button">返回</div></a>    		
        </li></ul>      
           
    </div>
</div>
<!-- 弹出窗口大图片显示 -->
<div class="pop_win" id="max_img" style="display:none;">
    <div class="msgbox">
    	<a href="javascript:hidediv('max_img');"><div class="msgbox_close"></div></a>      
            <div class="band_phone_small">
          
		    <img id="max_ImgTag" src="<%=fullPath%>/images/product_defunt.jpg" width="335" height="400"/>
                 
            </div>
      
        <div class="line_dashed_h"></div>
		<ul>
         <li class="center">
       	<a href="javascript:hidediv('max_img');" ><div class="input_button">返回</div></a>    		
        </li></ul>      
           
    </div>
</div>	
<input type="hidden" id="jsessionid" value="${jsessionid}">
	
<div class="copyright">
Copyright © 2014 China unicom All Right Reserved
</div>
</div>
</body>
</html>