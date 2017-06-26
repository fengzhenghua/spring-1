<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tydic.unicom.util.DateUtil"%>
<%@ taglib prefix="t" uri="http://tydic.com/jsp/core"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>综合变更</title>

<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/card.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/showOrder.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/pre_accept.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/cqPcMultipleChange.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/cqPcMultipleChange.js"></script>
<script type="text/javascript" src="<%=fullPath%>js/posPayFee.js"></script>
<!--[if lt IE 9]>
    <script type="text/javascript" src="<%=fullPath%>js/json2.js"></script>
<![endif]-->

</head>
<body>
	<object classid="clsid:10946843-7507-44FE-ACE8-2B3483D179B7" id="CVR_IDCard" name="CVR_IDCard" width="0" height="0"></object>
	<input type="hidden" id = "operNo" value="${operNo}" />
	<input type="hidden" id = "jsessionid" value="${jsessionid}" />
	<input type="hidden" id = "teleType"/>
	<input type="hidden" id = "productId"/>
	<input type="hidden" name="messageFlag" value="0" id="messageFlag">
	<input type="hidden" id="id_card_mech" name="id_card_mech" value="crvu">
	<div class="show" style="*height:913px;">
    <div class="info">
        <div class="title text_big">
            <span class="text_large24">1</span>证件查询
        </div>
        
        <ul class="detail">
        	<div class="padding_blank10"></div>
            <li class="list_padding_zero">
            	<div class="left">
                	<div class="left_lable">证件类型：</div>
                    <div class="left_lable_quarter">
                    	<select id="" class="select_down">
                            <option>身份证</option>
                            <!-- <option>护照</option>
                            <option>警察证</option>
                            <option>军官证</option> -->
                        </select>
                    </div>
                    <div class="left_lable">证件编号：</div>
                    <div class="left_data_quarter"><input type="text" id="idNumber" name="idNumber" placeholder=""></input></div>
                    <a href="javascript:void(0);"><div class="input_button" id="btn_load">读二代证</div></a>
                    <!-- <a href="javascript:void(0);"><div class="input_button" id="btn_load_test">模拟读取</div></a> -->
                </div>
                <div class="right">
                	<div class="left_lable">设备号码：</div>
                   	<div class="left_data_quarter"><input type="text" id="deviceNumber" name="deviceNumber" placeholder=""></input></div>
                    <div class="left_lable">
                    	<div class="left_data"><span class=" space12"></span></div>
                    	<div class="left_data"><a id="qryInfo" class="search">查询</a></div>
                        <div class="left_data"><span class=" space12"></span></div>
                        <div class="left_data"><a id="clearInput" class="search_no_circle">置 空</a></div>
                    </div>
                </div>
            </li>
            <div class="padding_blank10"></div>
        </ul>
        <div class="padding_blank10"></div>
    </div> 
    
    <div class="info">
        <div class="title text_big">
            <span class="text_large24">2</span>查询结果
        </div>
        <ul class="detail">
			<div class="padding_blank10"></div>
			<div id="custDiv">
				<div class="table_title">客户信息</div>
				<table class="table_infos" id="custInfos">
					<thead>
						<tr>
							<th width="25%">客户名称</th>
							<th width="25%">证件类型</th>
							<th width="25%">证件编号</th>
							<th>客户级别</th>
						</tr>
					</thead>
					<tbody>
						<tr radioName="radio_1">
							<td class="text_center" id="custName"></td><!-- 张某某 -->
							<td class="text_center" id="idType"></td><!-- 身份证 -->
							<td class="text_center" id="iddNumber"></td><!-- 520222222222222222 -->
							<td class="text_center" id="cardType"></td><!-- 金卡 -->
						</tr>
					</tbody>
				</table>
				<div class="padding_blank10"></div>
			</div>
			<div id="deviceDiv">
				<div class="table_title">设备信息（<span class="red">*必选</span>）</div>
				<table class="table_infos table_optional" id="deviceInfos">
					<thead>
						<tr>
							<th width="4%"></th>
							<th width="20%">设备号码</th>
							<th width="8%">用户状态</th>
							<th width="8%">品牌</th>
							<th width="26%">产品</th>
							<th width="26%">装机地址</th>
							<th>归属系统</th>
						</tr>
					</thead>
					<tbody>
						<tr device_number="" tele_type="" product_code="">
							<td></td>
							<td class="text_center"></td>
							<td class="text_center"></td>
							<td class="text_center"></td>
							<td class="text_center"></td>
							<td class="text_center"></td>
							<td class="text_center"></td>
						</tr>
						<!-- 动态加载 device_number:设备号码 tele_type:电信类型 product_code:产品编码 -->
						<%-- <tr device_number="18600000000" tele_type="" product_code="">
							<td><input type="checkbox" name="chk_device"/></td>
							<td class="text_center">18600000000</td>
							<td class="text_center">正在使用</td>
							<td class="text_center">沃</td>
							<td class="text_center">186套餐</td>
							<td class="text_center"></td>
							<td class="text_center">CBSS</td>
						</tr>
						<tr device_number="dsl1307111223@comcis" tele_type="" product_code="">
							<td><input type="checkbox" name="chk_device"/></td>
							<td class="text_center">dsl1307111223@comcis</td>
							<td class="text_center">正在使用</td>
							<td class="text_center"></td>
							<td class="text_center">[SA8245]636元8M包年组合包</td>
							<td class="text_center">重庆********************</td>
							<td class="text_center">BSS</td>
						</tr> --%>
					</tbody>
				</table>
				<div class="padding_blank10"></div>
			</div>
			<div id="preOrderDiv">
				<div class="table_title">预订单信息（<span class="red">可选</span>）</div>
				<table class="table_infos table_optional" id="preOrderInfos">
					<thead>
						<tr>
							<th width="4%"></th>
							<th width="20%">订单号</th>
							<th width="20%">证件号</th>
							<th width="20%">设备号码</th>
							<th width="16%">订单类型</th>
							<th>订单信息</th>
						</tr>
					</thead>
					<tbody>
						<tr pre_order_id="">
							<td></td>
							<td class="text_center"></td>
							<td class="text_center"></td>
							<td class="text_center"></td>
							<td class="text_center"></td>
							<td class="text_center"></td>
						</tr>
						<!-- 动态加载 pre_order_id:预订单号 -->
						<%-- <tr pre_order_id="1234">
							<td><input type="checkbox" name="chk_pre_order"/></td>
							<td class="text_center">1234</td>
							<td class="text_center">520222222222222222</td>
							<td class="text_center">13018341458</td>
							<td class="text_center">产品变更</td>
							<td class="text_center">换产品G1289</td>
						</tr> --%>
					</tbody>
				</table>
				<div class="padding_blank10"></div>
			</div>
		</ul>
        <div class="padding_blank10"></div>
    </div> 
    
    <div class="info">
        <div class="title text_big">
            <span class="text_large24">3</span>业务办理
        </div>
        
        <ul class="detail">
        	<div class="padding_blank10"></div>
            
            <div class="tabbox" id="tabbox">
				
                <ul class="tabs" id="tabs">
                    <li tab_name="productChng" tab="tab1">
                    	<div class="tab_img tab_product_change"></div>
                    	<a href="javascript:void(0);">产品变更</a>
                        <div orderId="1" class="tab_icon_up"></div>
                    </li>
                    <li tab_name="dinnerChng" tab="tab2">
                    	<div class="tab_img tab_set_change"></div>
                    	<a href="javascript:void(0);">套餐变更</a>
                        <div orderId="2" class="tab_icon_up"></div>
                    </li>
                    <li tab="tab3">
                    	<div class="tab_img tab_repair_card"></div>
                        <a href="javascript:void(0);">补 卡</a>
                        <div class="tab_icon_up"></div>
                    </li>
                    <li tab="tab4">
                    	<div class="tab_img tab_transfer_name"></div>
                        <a href="javascript:void(0);">过 户</a>
                        <div class="tab_icon_up"></div>
                    </li>
                    <li tab="tab5">
                    	<div class="tab_img tab_cancel_name"></div>
                        <a href="javascript:void(0);">销 户</a>
                        <div class="tab_icon_up"></div>
                    </li>
                    <li tab="tab6">
                    	<div class="tab_img tab_stop_open"></div>
                        <a href="javascript:void(0);">停开机</a>
                        <div class="tab_icon_up"></div>
                    </li>
                    <li tab="tab7">
                    	<div class="tab_img tab_international_roam"></div>
                    	<a href="javascript:void(0);">国际漫游</a>
                        <div class="tab_icon_up"></div>
                    </li>
                    <li tab="tab8">
                    	<div class="tab_img tab_half_year_bag"></div>
                    	<a href="javascript:void(0);">半年包</a>
                        <div class="tab_icon_up"></div>
                    </li>
                </ul>
                
                <ul class="tab_conbox">
                    <li id="tab1" class="tab_con">
                       
                       <ul class="">
                       		<li class="list_padding_zero">
                                    
								<div class="left" style="width:282px;">
                                    <div class="left_lable">产<span class="space24"></span>品：</div>
                                    <div class="left_data_w130">
                                        <input type="text" placeholder="" id="product_select"></input>
                                        <a  onClick="showProductInfo('','0')"><div class="input_button">选  择</div></a>
                                    </div>
                                </div>
                                
                                <div class="left" style="width:294px;">
                                    <div class="left_lable">业<span class="space5"></span>务<span class="space5"></span>包：</div>
                                    <div class="left_data_w130">
                                        <input type="text" placeholder="" id="prod_pkg_sel"/>
                                        <a  onClick="prodBusiPkgSel('','0')"><div class="input_button">选  择</div></a>
                                    </div>
                                </div>
                                
                                <div class="right" style="width:294px;">
                                    <div class="left_lable">生效方式：</div>
                                    <div class="left_data_quarter" id="prodValidType">次月生效</div>
                                </div>
                            </li>
                            
                            <div class="padding_blank10"></div>
                            
                            <div style="*line-height:10px;"></div>
                            
                            <li class="list_padding_zero" id="activity_show">
                                    
								<div class="left" style="width:282px;">
                                    <div class="left_lable">活动类型：</div>
                                    <div class="left_data_w130">
                                        <select id="activityType" class="select_down" style=" width:217px; *width:215px;"  onChange="GetGuaranteeType();">
                                        </select>
                                    </div>
                                </div>
                                
                                <div class="left" style="width:294px;">
			                        <div class="left_lable">担保类型：</div>
			                        <div class="left_data_w130">
			                            <select id="guarantee_type_select"  class="select_down" style=" width:217px; *width:215px;">
			                      	  	</select>
			                        </div>                    
			                    </div>
                                
                                <div class="left" style="width:294px;">
                                    <div class="left_lable">可选活动：</div>
                                    <div class="left_data_w130">
                                        <input type="text" placeholder="" id="activity_select"></input>
                                        <a onclick="showActivityInfo('','0')"><div class="input_button">选  择</div></a>
                                    </div>
                                </div>
                                
                                
                            </li>
                            
                            <div class="padding_blank10"></div>
                            
                            <div style="*line-height:10px;"></div>
                            
                            <li class="list_padding_zero">
                            	<div class="right" style="width:294px;" id="chuanhao">
                                    <div class="left_lable">终端串号：</div>
                                    <div class="left_data_w130">
                                        <input type="text" placeholder="" id="mobileNo"></input>
                                        <a onclick="terminalCheck()"><div class="input_button">校  验</div></a>
                                    </div>
                                </div>
                            </li>
                            
                            <div class="padding_blank10"></div>
                            
                            <li class="list_padding_zero" id="pinpai">
                                <div class="right" style="width:294px;">
                                    <div class="left_lable">品<span class="space24"></span>牌：</div>
                                    <div class="left_data_w130">
                                        <input type="text" placeholder="" id="brand"></input>
                                    </div>
                                </div>
                            </li>
                            <div class="padding_blank10"></div>
                            
                            <li class="list_padding_zero" id="xinghao">
                                <div class="right" style="width:294px;">
                                    <div class="left_lable">型<span class="space24"></span>号：</div>
                                    <div class="left_data_w130">
                                        <input type="text" placeholder="" id="model"></input>
                                    </div>
                                </div>
                            </li>
                            <div class="padding_blank10"></div>
                            
                            <li class="list_padding_zero" id="yanse">
                                <div class="right" style="width:294px;">
                                    <div class="left_lable">颜<span class="space24"></span>色：</div>
                                    <div class="left_data_w130">
                                        <input type="text" placeholder="" id="color"></input>
                                    </div>
                                </div>
                            </li>
                            
                        </ul>
                       
                    </li>
                        
                    <li id="tab2" class="tab_con">
                        <ul class="">
                            <li class="list_padding_zero">
                                    
								<div class="left" style="width:282px;">
                                    <div class="left_lable">订购类型：</div>
                                    <div class="left_data_w130">
                                        <select id="busiPgkOperType" class="select_down" style=" width:217px; *width:215px;">
                                        	<option></option>
                                            <option value="0">订购套餐</option>
                                            <option value="1">退订套餐</option>
                                        </select>
                                    </div>
                                </div>
                                
                                <div class="left" style="width:294px;">
                                    <div class="left_lable">业<span class="space5"></span>务<span class="space5"></span>包：</div>
                                    <div class="left_data_w130">
                                        <input type="text" placeholder="" id="pkg_select"></input>
                                        <a onclick="busiPkgSel('','0')"><div class="input_button">选  择</div></a>
                                    </div>
                                </div>
                                
                                <div class="right" style="width:294px;">
                                    <div class="left_lable">生效方式：</div>
                                    <div class="left_data_w130">
                                        <select id="validType" class="select_down">
                                        	<option value=""></option>
                                            <option value="立即生效">立即生效</option>
                                            <option value="次日生效">次日生效</option>
                                            <option value="次月生效">次月生效</option>
                                        </select>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </li>
                    
                    <li id="tab3" class="tab_con">
                        33333333333
                    </li>
                    
                    <li id="tab4" class="tab_con">
                        44444444444444
                    </li>
                    <li id="tab5" class="tab_con">
                        55555555555555
                    </li>
                    <li id="tab6" class="tab_con">
                        66666666666666
                    </li>
                    <li id="tab7" class="tab_con">
                        77777777777777
                    </li>
                    <li id="tab8" class="tab_con">
                        88888888888888
                    </li>
                </ul>
    		</div>
    		
            <div class="padding_blank10"></div>
		</ul>
        <div class="padding_blank10"></div>
    </div> 
	
 	<div class="info">
        <div class="title text_big">
            <span class="text_large24">4</span>费用展示
        </div>
        <ul class="detail" >
           <ul id="fee_list">
           </ul> 
           
           <li class="list bold">总金额：
            	<span class="bold" id="fee_all"></span>&nbsp;元 </li>    
           <div class="line_dashed_h"></div>
        </ul>  
        <!-- <ul class="detail">
        	<div class="padding_blank10"></div>
            <li class="list_padding_zero" style="*display:inline;">
                <div class="order_row" style="width:402px; ">
                    <div class="order_cell">费用</div>
                    <div class="order_cell">应收</div>
                    <div class="order_cell">实收</div>
                </div>
            </li>
            
            <li class="list" style="*display:inline;">
                <div class="order_row white_bgcolor" style="width:402px; ">
                    <div class="order_cell">费用1</div>
                    <div class="order_cell">10</div>
                    <div class="order_cell">20</div>
                </div>
            </li>
            <li class="list" style="*display:inline;">
                <div class="order_row white_bgcolor" style="width:402px; ">
                    <div class="order_cell">费用2</div>
                    <div class="order_cell">10</div>
                    <div class="order_cell">20</div>
                </div>
            </li>
            <li class="list" style="*display:inline;">
                <div class="order_row white_bgcolor" style="width:402px; ">
                    <div class="order_cell">合计</div>
                    <div class="order_cell"></div>
                    <div class="order_cell"></div>
                </div>
            </li>
            <div class="padding_blank10"></div>
		</ul> -->
        <div class="padding_blank10"></div>
    </div> 
        
    <div class="padding_blank"></div> 
    <div class="padding_blank"></div>
    <li class="text_large text_center">
        <div class="ok" style="display:inline;">
        	<a href="javascript:void(0);" id="commitOrder">确  认</a>
        </div>
        <div class="ok" style="display:inline;">
			<a href="javascript:void(0);" onClick="">电子签名</a>
		</div>
    </li> 
    <div class="padding_blank"></div>
  
</div>

<div class="bg_mask" id="bg_mask">
	<iframe class="bg_mask_iframe"> </iframe>
</div>

<!-- 套餐变更  弹出窗口 业务包选择： -->
<div class="pop_win" id="busi_package" style="display:none;" >
    <div class="msgbox">
    	<a href="javascript:hidediv('busi_package');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">可选业务包：</span></li>
        </ul>
            <div class="band_phone_small">
                <input type="text" class="search_input_small" placeholder="输入业务包关键字模糊查询"  id="PkgInput"></input>
                <a href="javascript:void(0)" onClick=""><div class="input_button" id="searchPkg">搜  索</div></a>
            </div>
         <div class="scroll_v">
		<ul id="ywb_list">                        
            <div class="line_dashed_h"></div>
        </ul>
        </div>
        <ul>
         <li class="center">
        <a href="javascript:void(0)" onClick="selBusiPkg();"><div class="input_button">确 定</div></a> 
       	<a href="javascript:void(0)" onClick="BusiPkgDelete();"><div class="input_button">清 空</div></a>    		
        </li></ul>
    </div>
</div>

<!-- 弹出产品选择选择： -->
<div class="pop_win" id="product_info" style="display:none;" >
    <div class="msgbox">
    	<a href="javascript:hidediv('product_info');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">可选产品：</span></li>
        </ul>
            <div class="band_phone_small">
                <input type="text" class="search_input_small" placeholder="输入产品关键字模糊查询"  id="ProductInput"></input>
                <a href="javascript:void(0)" onClick=""><div class="input_button" id="searchProduct">搜  索</div></a>
            </div>
         <div class="scroll_v">
		<ul id="product_list">                        
            <div class="line_dashed_h"></div>
        </ul>
        </div>
        <ul>
         <li class="center">
        <a href="javascript:void(0)" onClick="productConfirm();"><div class="input_button">确 定</div></a> 
       	<a href="javascript:void(0)" onClick="productDelete();"><div class="input_button">清 空</div></a>    		
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
<!-- 产品变更  弹出窗口 业务包选择： -->
<div class="pop_win" id="prod_busi_package" style="display:none;" >
    <div class="msgbox">
    	<a href="javascript:hidediv('prod_busi_package');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">可选业务包：</span></li>
        </ul>
            <div class="band_phone_small">
                <input type="text" class="search_input_small" placeholder="输入业务包关键字模糊查询"  id="ProdPkgInput"></input>
                <a href="javascript:void(0)" onClick=""><div class="input_button" id="searchProdPkg">搜  索</div></a>
            </div>
         <div class="scroll_v">
		<ul id="prod_ywb_list">                        
            <div class="line_dashed_h"></div>
        </ul>
        </div>
        <ul>
         <li class="center">
        <a href="javascript:void(0)" onClick="selProdBusiPkg();"><div class="input_button">确 定</div></a> 
       	<a href="javascript:void(0)" onClick="ProdBusiPkgDelete();"><div class="input_button">清 空</div></a>    		
        </li></ul>
    </div>
</div>
</body>
</html>