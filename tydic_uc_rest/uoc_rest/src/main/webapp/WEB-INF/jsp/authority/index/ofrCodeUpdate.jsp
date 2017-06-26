<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
<%@ taglib prefix="t" uri="http://tydic.com/jsp/core" %>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 加载jQuery CSS -->
<link href="<%=fullPath%>css/share1.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/card.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/ofrCodeConfig.js"></script>
</head>
<body>
<div name="reader_context" id="reader_context">
<div class="show">
    <div class="show_title_bg">
        <div class="show_title">产品详情</div>
    </div>
    <div class="card">
    
    <div id="control_idcard_info" ><!-- 控制身份信息隐藏的div 暂时没加上 -->
 
    <table width="938" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#e7e7e7"  class="auto auto_table">
      <tr>
        <td width="115" height="32" align="right" bgcolor="#f8f8f8">产品名称：</td>
        <td width="350" bgcolor="#FFFFFF" id="customer_name">
        <input type="text" size="30" id="ofr_name" value="${codeOfr.ofr_name}" class="name" />
        </td>
        <td width="115" align="right" bgcolor="#F8F8F8">产品描述：<br /></td>
        <td width="358" bgcolor="#FFFFFF" >
        <textarea rows="6" cols="50" id="ofr_desc" class="name">${codeOfr.ofr_desc}</textarea>        
        </td>
      </tr>
      <tr>
        <td height="32" align="right" bgcolor="#f8f8f8">生效标识：</td>
        <td bgcolor="#FFFFFF">
          <select name="eff_flag" id="eff_flag" >
              <c:if test="${codeOfr.eff_flag=='Y'}">
    	    	<option value ="Y" selected = "selected">生效</option>
    	    	<option value ="N">失效</option>
    	    	</c:if>
    	    	<c:if test="${codeOfr.eff_flag=='N'}">
    	    	<option value ="Y">生效</option>
    	    	<option value ="N" selected = "selected">失效</option>
    	    	</c:if>
  	        </select>
        </td>
        <td align="right" bgcolor="#F8F8F8">产品类型：<br /></td>
        <td bgcolor="#FFFFFF" >
        <input type="text" size="20" id="ofr_type" value="${codeOfr.ofr_type}" class="name" />
        </td>
      </tr>
      <tr>
        <td height="32"  align="right" bgcolor="#f8f8f8">电信类型：</td>
        <td bgcolor="#FFFFFF">
        <input type="text" size="20" id="tele_type" value="${codeOfr.tele_type}" class="name" />
        </td>
        <td align="right" bgcolor="#F8F8F8">BSS产品ID：<br /></td>
        <td bgcolor="#FFFFFF">
        <input type="text" size="20" id="bss_product" value="${codeOfr.bss_product}" class="name" />
        </td>
      </tr>
      <tr>
        <td height="32"  align="right" bgcolor="#f8f8f8">付费标识：</td>
        <td bgcolor="#FFFFFF">
         <select name="pay_flag" id="pay_flag" >
              <c:if test="${codeOfr.pay_flag=='1'}">
    	    	<option value ="1" selected = "selected">后付费</option>
    	    	<option value ="2">预付费</option>
    	    	</c:if>
    	    	<c:if test="${codeOfr.pay_flag=='2'}">
    	    	<option value ="1" >后付费</option>
    	    	<option value ="2" selected = "selected">预付费</option>
    	    	</c:if>
  	        </select>
      
        </td>
        <td align="right" bgcolor="#F8F8F8">产品ID：<br /></td>
        <td bgcolor="#FFFFFF">
        <input type="text" size="20" id="product_id" value="${codeOfr.product_id}" class="name" />
        </td>
      </tr>
      
    <tr>
   
    </table>
    <br/>
    <div bgcolor="#FFFFFF"  style="text-align:center;">
      <a href="javascript:void(0)" onClick="update();"><div class="input_button">修改</div></a>
      <a href="javascript:void(0)" onClick="window.close();"><div class="input_button">关闭</div></a></div>
    </div>
    <!---------------------------------------- 隐藏信息 ----------------------------------->
	<input type="hidden" name="ofr_id" value="${codeOfr.ofr_id}" id="ofr_id" >
	<input type="hidden" name="oldeff_flag" value="${codeOfr.eff_flag}" id="oldeff_flag" >
	<input type="hidden" name="product_id" value="${codeOfr.product_id}" id="product_id" >
	
  </div>

</div>

<div class="copyright">
Copyright © 2014 China unicom All Right Reserved
</div>
</body>
</html>