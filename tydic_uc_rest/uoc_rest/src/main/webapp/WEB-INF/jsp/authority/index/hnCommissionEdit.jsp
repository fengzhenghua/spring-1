<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<%@ page import="java.util.Date" %>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
 <%@page import="org.jasig.cas.client.util.AssertionHolder"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>佣金规则新增</title>
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/pre_accept.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/hnCommissionEdit.js"></script>
<script type="text/javascript" >
</script>

</head>

<body>
<input type="hidden" name="create_oper_id" id="create_oper_id" value="${infoRuleCommissionVo.create_oper_id}">
<input type="hidden" name = "dept_no" id = "dept_no" value="${infoRuleCommissionVo.cost_dept}"/>
<div class="show" style=" height:552px;">
    
    <div class="info">
    	<div class="title text_big">
        	<span class="text_large24"></span>基本属性
        </div>
        
        <ul class="detail">
        	<div class="padding_blank10"></div>
        	
        	<li class="list padding_no_t" style=" *height:auto;">
        		<div class="left">
                    <div class="left_lable">规则名称：</div>
                    <div class="left_lable">
                		<input type="text" id="rule_name" placeholder="" class="input_text"></input>
                		<span class="red">*
               		</div>
                </div>
                <div class="right">
                    <div class="left_lable"></span>结算类型：</div>
                    <div class="left_lable">
                		 <select class="select_down gray_bgcolor_f8" style="width:171px; min-width:171px;" id="settle_type">
                            <option value="-1">----请选择----</option>
                            <option value="1">日结</option>
                            <option value="2">月结</option>
                        </select>
               		</div>
                </div>
                          
            </li>
            <div class="padding_blank10"></div>
        	
        	<li class="list padding_no_t" style=" *height:auto;"> 
        		<div class="left">
                    <div class="left_lable"></span>订单类型：</div>
                    <div class="left_lable">
                		 <select class="select_down gray_bgcolor_f8" style="width:171px; min-width:171px;" id="order_sub_type">
                            <option value="-1">----请选择----</option>
                            <option value="1">开户</option>
                            <option value="2">活动变更</option>
                            <option value="3">产品变更</option>
                        </select>
               		</div>
                </div>
                <div class="right">
                    <div class="left_lable"></span>渠道类型：</div>
                    <div class="left_lable">
                		 <select class="select_down gray_bgcolor_f8" style="width:171px; min-width:171px;" id="chnl_type">
                            <option value="-1">----请选择----</option>
                            <option value="01">中小渠道</option>
                            <option value="02">自有厅</option>
                            <option value="03">自由渠道</option>
                        </select>
               		</div>
                </div>
            	      
            </li>
            <div class="padding_blank10"></div>
            <li class="list padding_no_t" style=" *height:auto;">
            	<div class="left">
                	<div class="left_lable">规则政策：</div>
                     <div class="left_lable">
                		<input type="text" id="rule_quota" placeholder="" class="input_text"></input>
               		</div>
                    <a href="javascript:void(0)" onClick="getRuleQuota()">
                         <div class="input_button">选  择</div>
                    </a>
                </div>  
            </li>
        	<div class="padding_blank10"></div>
            
            <div class="line_dashed_h"></div>
            
            <div class="padding_blank10"></div>
            
        	<li class="list padding_no_t" style=" *height:auto;">
            	<div class="left">
                    <div class="left_lable"></span>产品虚分组：</div>
                    <div class="left_lable">
                		<input type="text" id="product_type" placeholder="" class="input_text" readOnly="true"></input>
               		</div>
                    <a href="javascript:void(0)" onClick="getProductModel()">
                                <div class="input_button">选  择</div>
                            </a>
                </div>
                <div class="right">
                	<div class="left_lable"></span>终端虚分组：</div>
                    <div class="left_lable">
                		<input type="text" id="terminal_type" placeholder="" class="input_text" readOnly="true"></input>
               		</div>
                    <a href="javascript:void(0)" onClick="getTerminalModel()">
                                <div class="input_button">选  择</div>
                            </a>         
                </div>            
            </li>
            <div class="padding_blank10"></div>
            <li class="list padding_no_t" style=" *height:auto;">
            	<div class="left">
                	<div class="left_lable">活动虚分组：</div>
                     <div class="left_lable">
                		<input type="text" id="activity_group" placeholder="" class="input_text"></input>
               		</div>
                    <a href="javascript:void(0)" onClick="getActivityGroup()">
                         <div class="input_button">选  择</div>
                    </a>
                </div> 
                 
                <div class="right">
                	<div class="left_lable">活动类型：</div>
                     <div class="left_lable">
                		<input type="text" id="busi_type" placeholder="" class="input_text"></input>
               		</div>
                    <a href="javascript:void(0)" onClick="getOrderType()">
                                <div class="input_button">选  择</div>
                            </a>
                </div>
            </li>
            <div class="padding_blank10"></div>
            
            <div class="line_dashed_h"></div>
            
            <div class="padding_blank10"></div>
            
        	<li class="list padding_no_t" style=" *height:auto;">
            	<div class="left">
                    <div class="left_lable">成本类型：</div>
                    <select class="select_down gray_bgcolor_f8" style="width:171px; min-width:171px;" id="comm_type">
                            <option value="-1">----请选择----</option>
                            <option value="102">省公司规则</option>
                            <option value="103">分公司规则</option>
                            <option value="104">网格规则</option>
                        </select>
               		<span class="red">*
                </div>
                <div class="right">
                	<div class="left_lable">成本部门：</div>
                	  <div class="left_lable">
                		<input type="text" id="comm_dept" placeholder="" class="input_text"  readOnly="true"></input>
               		</div>
               		<a href="javascript:void(0)" onClick="getCommDept()">
                                <div class="input_button">选  择</div>
                        </a>
                     <span class="red">*
                </div>     
            </li>
            <div class="padding_blank10"></div>
        	<li class="list padding_no_t" style=" *height:auto;">
            	<div class="left">
                    <div class="left_lable">员工奖励：</div>
                    <div class="left_lable">
                		<input type="text" id="base1" placeholder="" class="input_text"></input>
                		<span class="red">*
               		</div>
                </div>
                <div class="right">
                	<div class="left_lable"><span class="space4"></span>店长奖励：</div>
                    <div class="left_lable">
                		<input type="text" id="base2" placeholder="" class="input_text"></input>
                		<span class="red">*
               		</div>
                </div>     
            </li>
            <div class="padding_blank10"></div>
            
        	<li class="list padding_no_t" style=" *height:auto;">
            	<div class="left">
                    <div class="left_lable">三级奖励：</div>
                    <div class="left_lable">
                		<input type="text" id="base3" placeholder="" class="input_text"  readOnly="true" value="0"></input>
               		</div>
                </div>
                <div class="right">
                	<div class="left_lable"><span class="space4"></span>四级奖励：</div>
                    <div class="left_lable">
                		<input type="text" id="base4" placeholder="" class="input_text"  readOnly="true" value="0"></input>
               		</div>
                </div>     
            </li>
            <div class="padding_blank10"></div>
            <div class="line_dashed_h"></div>
            <li class="list padding_no_t" style=" *height:auto;">
            	<div class="left">
                    <div class="left_lable">生效时间：</div>
                    <div class="left_lable">
                		 <t:date id="smeeting" name="smeeting" minDateElId="smeeting" value="<%=DateUtil.getCurrentDate() %>" maxDate="2050-01-01"></t:date>
               		</div>
                </div>
                <div class="right">
                	<div class="left_lable"><span class="space4"></span>失效时间：</div>
                    <div class="left_lable">
                		<t:date id="emeeting" name="emeeting" minDateElId="smeeting" value="<%=DateUtil.getAfterMonth(new Date(), 1) %>" maxDate="2050-01-01"></t:date>
               		</div>
                </div>     
            </li>
        </ul>
        <div class="padding_blank10"></div>
        <!--  
        <div class="bottom_blank"></div>
        <div class="bottom_blank"></div>
        -->
        <div class="bottom_blank"></div>
        <div class="">
            <div class="left ">
                <div class="button_ok right" style=" margin-right:10px;" id = "saveRuleComm">提 交</div>
            </div>
            <div class="left ">
                <div class="button_cancel white_bgcolor left_lable" style=" margin-left:10px;" id = "cancelRuleComm">取 消</div>
            </div>
        </div>
        
    </div> 
    
  <div class="padding_blank"></div>
  <div class="clear"></div>
</div>


<div class="bg_mask" id="bg_mask">
	<iframe class="bg_mask_iframe"> </iframe>
</div>

<div class="pop_win" id="activity_search" style="display:none;">
    <div class="msgbox">
    	<a href="javascript:hidediv('activity_search');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">活动虚分组</span></li>
        </ul>
            <div class="band_phone_small">
                <input type="text" class="search_input_small" placeholder="输入虚分组关键字模糊查询" id="TariffActivityInput"></input>
                <a href="javascript:void(0)" onClick="getActivityGroup();"><div class="input_button" id="searchTariffActivity">搜  索</div></a>
            </div>
        <div class="scroll_v" style="position:relative;">
		<ul id="activity_list">                       
            <li class="list">
            </li>
        </ul>
        </div>
        <div class="line_dashed_h"></div>
		<ul>
         <li class="center">
        <a href="javascript:void(0)" onClick="tariffConfirmActivity();"><div class="input_button">确 定</div></a> 
       	<a href="javascript:void(0)" onClick="tariffDeleteActivity();"><div class="input_button">清 空</div></a>    		
        </li></ul>               
    </div>
</div>
</div>

<div class="pop_win" id="rule_search" style="display:none;">
    <div class="msgbox">
    	<a href="javascript:hidediv('rule_search');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">规则政策</span></li>
        </ul>
            <div class="band_phone_small">
                <input type="text" class="search_input_small" placeholder="输入政策关键字模糊查询" id="TariffRuleQuotaInput"></input>
                <a href="javascript:void(0)" onClick="getRuleQuota();"><div class="input_button" id="searchTariffRuleQuota">搜  索</div></a>
            </div>
        <div class="scroll_v" style="position:relative;">
		<ul id="ruleQuota_list">                       
            <li class="list">
            </li>
        </ul>
        </div>
        <div class="line_dashed_h"></div>
		<ul>
         <li class="center">
        <a href="javascript:void(0)" onClick="tariffConfirmRuleQuota();"><div class="input_button">确 定</div></a> 
       	<a href="javascript:void(0)" onClick="tariffDeleteRuleQuota();"><div class="input_button">清 空</div></a>    		
        </li></ul>               
    </div>
</div>
</div>
<div class="pop_win" id="fee_search" style="display:none;">
    <div class="msgbox">
    	<a href="javascript:hidediv('fee_search');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">活动类型</span></li>
        </ul>
            <div class="band_phone_small">
                <input type="text" class="search_input_small" placeholder="输入资费关键字模糊查询" id="TariffInput"></input>
                <a href="javascript:void(0)"><div class="input_button" id="searchTariff">搜  索</div></a>
            </div>
        <div class="scroll_v" style="position:relative;">
		<ul id="tariff_list">                       
            <li class="list">
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
</div>
<div class="pop_win" id="fee_search1" style="display:none;">
    <div class="msgbox">
    	<a href="javascript:hidediv('fee_search1');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">终端虚分组</span></li>
        </ul>
            <div class="band_phone_small">
                <input type="text" class="search_input_small" placeholder="输入资费关键字模糊查询" id="TariffInput"></input>
                <a href="javascript:void(0)"><div class="input_button" id="searchTariff">搜  索</div></a>
            </div>
        <div class="scroll_v" style="position:relative;">
		<ul id="tariff_list1">                       
            <li class="list">
            </li>
        </ul>
        </div>
        <div class="line_dashed_h"></div>
		<ul>
         <li class="center">
        <a href="javascript:void(0)" onClick="tariffConfirmTerminal();"><div class="input_button">确 定</div></a> 
       	<a href="javascript:void(0)" onClick="tariffDeleteTerminal();"><div class="input_button">清 空</div></a>    		
        </li></ul>      
           
    </div>
</div>
</div>
<div class="pop_win" id="fee_search2" style="display:none;">
    <div class="msgbox">
    	<a href="javascript:hidediv('fee_search2');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">产品虚分组</span></li>
        </ul>
            <div class="band_phone_small">
                <input type="text" class="search_input_small" placeholder="输入资费关键字模糊查询" id="TariffInput"></input>
                <a href="javascript:void(0)"><div class="input_button" id="searchTariff">搜  索</div></a>
            </div>
        <div class="scroll_v" style="position:relative;">
		<ul id="tariff_list2">                       
            <li class="list">
            </li>
        </ul>
        </div>
        <div class="line_dashed_h"></div>
		<ul>
         <li class="center">
        <a href="javascript:void(0)" onClick="tariffConfirmProduct();"><div class="input_button">确 定</div></a> 
       	<a href="javascript:void(0)" onClick="tariffDeleteProduct();"><div class="input_button">清 空</div></a>    		
        </li></ul>      
           
    </div>
    
</div>
</div>
</div>
<div class="pop_win" id="fee_search_branch" style="display:none;">
    <div class="msgbox">
    	<a href="javascript:hidediv('fee_search_branch');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">分公司</span></li>
        </ul>
            <div class="band_phone_small">
                <input type="text" class="search_input_small" placeholder="输入分公司关键字模糊查询" id="TariffInput"></input>
                <a href="javascript:void(0)"><div class="input_button" id="searchTariff">搜  索</div></a>
            </div>
        <div class="scroll_v" style="position:relative;">
		<ul id="tariff_list_branch">                       
            <li class="list">
            </li>
        </ul>
        </div>
        <div class="line_dashed_h"></div>
		<ul>
         <li class="center">
        <a href="javascript:void(0)" onClick="tariffConfirmBranch();"><div class="input_button">确 定</div></a> 
       	<a href="javascript:void(0)" onClick="tariffDeleteBranch();"><div class="input_button">清 空</div></a>    		
        </li></ul>      
        </div>
    </div>
</div>
<div class="pop_win" id="fee_search_grid" style="display:none;">
    <div class="msgbox">
    	<a href="javascript:hidediv('fee_search_grid');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">分公司</span></li>
        </ul>
            <div class="band_phone_small">
                <input type="text" class="search_input_small" placeholder="输入网格关键字模糊查询" id="TariffInput"></input>
                <a href="javascript:void(0)"><div class="input_button" id="searchTariff">搜  索</div></a>
            </div>
        <div class="scroll_v" style="position:relative;">
		<ul id="tariff_list_grid">                       
            <li class="list">
            </li>
        </ul>
        </div>
        <div class="line_dashed_h"></div>
		<ul>
         <li class="center">
        <a href="javascript:void(0)" onClick="tariffConfirmGrid();"><div class="input_button">确 定</div></a> 
       	<a href="javascript:void(0)" onClick="tariffDeleteGrid();"><div class="input_button">清 空</div></a>    		
        </li></ul>      
        </div>
    </div>
</div>
</div>
</body>
</html>
