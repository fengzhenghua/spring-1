<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>销售</title>
<script type="text/javascript">
$(function(){
//$(".position").hide();
});

</script>
</head>
<body>

<div class="top_new">
	<div class="top_bg">
        <div class="top_content">
        	<img src="<%=fullPath%>images/banner_income.png"/>              
            <div class="top_info">
              <c:if test="${uniform_info_oper.substring(2, uniform_info_oper.length()>=4?4:uniform_info_oper.length())=='DL'}">
	    	   		<div class="float_right button_data" style=" margin:3px 25px 0px 0px;*width:118px; _margin-right:10px;">
	        				<div class="button_data_right"></div>
	                    	<div class="button_data_left"></div>
	        				<div class="button_data_txt" id="shoujihaomabdwh" name="shoujihaomabdwh">手机号码绑定维护</div>
	                </div>
	           </c:if> 
               <a href="javascript:;" onclick="parent.window.location.href='<%=fullPath%>authority/index/logout';return false;" class="code_exit">安全退出</a><a class="tip" style="width:310px;*width:300px;_width:300px;"  href="javascript:void(0)"><div class="hall_name" style="width:310px;*width:300px;_width:300px;">${info_Dept.dept_name } <span>|</span> 授权人：${info_Oper.oper_name } ${info_Oper.oper_no } <span>|</span></div> 
               <span class="tip_info">${info_Dept.dept_name } <span>|</span> 授权人：${info_Oper.oper_name } ${info_Oper.oper_no } <span>|</span> 授权时间：${newDtae}<span></span> 发展人：${devName}|<span>|</span>${devCode}|<span>|</span>${chnlName}</span>	</a>
            </div>
            
        <c:if test="${role_type=='1'}">
         <div class="seat text_large24">营业座席</div>
        </c:if>
        <c:if test="${role_type!='1'}">
         <div class="seat text_large24">共享座席</div>
        </c:if>
            
                          
        </div>            
    </div>
</div>

</body>
</html>