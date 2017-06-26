<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@include file="../common/common.jsp"%>
  <%@ page import="com.tydic.unicom.util.DateUtil" %>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>销售</title>

<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/saleIndexNew.js"></script>
</head>
<body>
<input type="hidden" id="province_code" value="${province_code}" /> <!--省份标识 -->
<input type="hidden" id = "role_type" value="${role_type}" /><!--//判断是营业还是共享坐席 营业坐席是不需要输入6为验证码-->
<div class="select">
    <form id="form1" name="form1" method="post" action="">
	<div class="select_list">
    	<div class="div_float div_float_ie6">
    	    <select name="receive" id="receive" >
    	      <option>接待人员</option>
    	      <c:forEach var="infoBcsScoreVo" items="${infoBcsScoreVoShowList}" varStatus="i">
    	      <option>${infoBcsScoreVo.login_id} ${infoBcsScoreVo.login_name}</option>
    	      </c:forEach>
  	        </select>
    	</div>
        <div class="div_float div_float_ie6">
           <t:select id="order_sub_type_status" name = "order_sub_type_status" codeType="order_sub_type_status" ></t:select>
        </div>
        <div class="div_float div_float_ie6">
          <t:select id="order_processing_status" name = "order_processing_status" codeType="order_processing_status" ></t:select>
        </div>
        <div class="div_float">
          <!--<select name="time" id="time">
            <option>时间</option>
          </select>-->
          <!-- input type="text" value="请点击选择时间" id="time" /> -->
          <t:date id="end_time" name="end_time" minDateElId="start_time"  value="<%=DateUtil.getCurrentDate() %>"   maxDate="2050-01-01"></t:date>
        </div>
        <div class="div_float div_float_none">
        	<dl>
           	  <dt>搜索：</dt>
              <dd><input type="text" id="device_number" value="请输入手机号码" onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入手机号码'}" class="tel" /></dd>
              <dd><input type="text" id="cust_name" value="姓名" onfocus="this.value=''" onblur="if(this.value==''){this.value='姓名'}" class="name" /></dd>
              <dd><input type="text" id="order_ids" value="云销售订单编号/沃创富提货单/身份证号" title="云销售订单编号/沃创富提货单/身份证号" onfocus="this.value=''" onblur="if(this.value==''){this.value='云销售订单编号/沃创富提货单/身份证号'}" class="order" /></dd>
              <dd class="none"><a href="#" id="query" class="search">查询</a></dd>
              <div class="clear"></div>
            </dl>
        </div>
        <div class="clear"></div>
	</div>
	</form>
</div>



<div class="list" id="list">
</div>

<div class="copyright">
Copyright © 2014 China unicom All Right Reserved
</div>
</body>
</html>