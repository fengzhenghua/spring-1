<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
 <%@page import="org.jasig.cas.client.util.AssertionHolder"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>佣金规则修改</title>
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/pre_accept.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/hnCommissionModify.js"></script>
<script type="text/javascript" >
</script>

</head>

<body>
<input type="hidden" name="rule_id" id="rule_id" value="${infoRuleCommissionVo.rule_id}">
<div class="show" style=" height:552px;">
    
    <div class="info">
    	<div class="title text_big">
        	<span class="text_large24"></span>基本属性
        </div>
        
        <ul class="detail">
        	<div class="padding_blank10"></div>
        	<li class="list padding_no_t" style=" *height:auto;">
            	<div class="left">
                    <div class="left_lable">活动类型：</div>
                    <div class="left_lable">
                		<input type="text" id="order_type" placeholder="" class="input_text" disabled></input>
               		</div>
                </div>
                <div class="right">
                	<div class="left_lable">渠道类型：</div>
                    <div class="left_lable">
                		<input type="text" id="chnl_type" placeholder="" class="input_text" disabled></input>
               		</div>             
                </div>            
            </li>
            <div class="padding_blank10"></div>
            
            <div class="line_dashed_h"></div>
            
            <div class="padding_blank10"></div>
        	<li class="list padding_no_t" style=" *height:auto;">
            	<div class="left">
                    <div class="left_lable">产品类型：</div>
                    <div class="left_lable">
                		<input type="text" id="product_type" placeholder="" class="input_text" disabled></input>
               		</div>
                </div>
                <div class="right">
                	<div class="left_lable">终端类型：</div>
                    <div class="left_lable">
                		<input type="text" id="terminal_type" placeholder="" class="input_text" disabled></input>
               		</div>
                </div>            
            </li>
            <div class="padding_blank10"></div>
            
            <div class="line_dashed_h"></div>
            
            <div class="padding_blank10"></div>
        	<li class="list padding_no_t" style=" *height:auto;">
            	<div class="left">
                    <div class="left_lable"></span>成本类型：</div>
                        <div class="left_lable">
                		<input type="text" id="comm_type" placeholder="" class="input_text"  disabled></input>
               		</div>
                </div>
                <div class="right">
                	 <div class="left_lable">规则状态：</div>
                    <div class="left_lable">
                		<input type="text" id="flag" placeholder="" class="input_text" disabled></input>
               		</div>
                </div>     
            </li>
            <div class="padding_blank10"></div>
        	<li class="list padding_no_t" style=" *height:auto;">
            	<div class="left">
                    <div class="left_lable">员工奖励：</div>
                    <div class="left_lable">
                		<input type="text" id="base1" placeholder="" class="input_text"></input>
               		</div>
                </div>
                <div class="right">
                	<div class="left_lable"><span class="space4"></span>店长奖励：</div>
                    <div class="left_lable">
                		<input type="text" id="base2" placeholder="" class="input_text"></input>
               		</div>
                </div>     
            </li>
            <div class="padding_blank10"></div>
            
        	<li class="list padding_no_t" style=" *height:auto;">
            	<div class="left">
                    <div class="left_lable">三级奖励：</div>
                    <div class="left_lable">
                		<input type="text" id="base3" placeholder="" class="input_text"></input>
               		</div>
                </div>
                <div class="right">
                	<div class="left_lable"><span class="space4"></span>四级奖励：</div>
                    <div class="left_lable">
                		<input type="text" id="base4" placeholder="" class="input_text"></input>
               		</div>
                </div>     
            </li>
            <div class="padding_blank10"></div>
            
        </ul>
        <div class="padding_blank10"></div>
        <!--  
        <div class="bottom_blank"></div>
        <div class="bottom_blank"></div>
        -->
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

</body>
</html>
