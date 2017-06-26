<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
<%@ page import="java.util.Date" %>
<%
	String nowDate = DateUtil.getCurrentDate(); 
	String beforeDate = DateUtil.getAfterMonth(new Date(), -1);
	String load_oper_no=(String)request.getAttribute("load_oper_no");
%>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>销售</title>

<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/list.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/pre_accept.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=fullPath%>js/unifiedLogQuery.js"></script>
</head>
<body>
<input type="hidden" id = "operId" value="${operId}" />
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
          	<t:select id="tele_type" name = "tele_type" codeType="tele_type" ></t:select>
        </div>
        <div class="div_float">
          <!--<select name="time" id="time">
            <option>时间</option>
          </select>-->
         <!--  < input type="text" value="请点击选择时间" id="time" /> -->
          <t:date id="date_begin" name="date_begin"  maxDateElId="date_end"  value="<%=beforeDate%>"   maxDate="2050-01-01"></t:date>
        </div>
        <div class="left_lable">到：</div>
        <div class="div_float">
          <!--<select name="time" id="time">
            <option>时间</option>
          </select>-->
         <!--  < input type="text" value="请点击选择时间" id="time" /> -->
         <t:date id="date_end" name="date_end"   minDateElId="date_begin" value="<%=nowDate%>"   maxDate="2050-01-01"></t:date>
        </div>
        <div class="div_float div_float_none" style="float: right;">
        	<dl>
        	  <dd>搜索:</dd>
              <dd><input type="text" id="device_number" value="请输入手机号码" onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入手机号码'}" class="tel" /></dd>
              <dd class="none"><a href="#" id="query" class="search">查询</a></dd>
            </dl>
        </div>
	</div>
	
	</form>
</div>
<input type="hidden" id = "operNo" value="${LIUJ}" />
<div class="show"> 
	<div class="info" id="phoneInfo">
        <div class="title text_big">
        查询结果
        </div>
        <table class="table_infos_paylog" id="paylogTable">
			<thead>
				<tr>
					<th width="13%">工单编号</th>
					<th width="8%">电信类型</th>
					<th width="18%">设备号码</th>
					<th width="8%">缴费类型</th>
					<th width="8%">原始金额</th>
					<th width="8%">减免金额</th>
					<th width="8%">实收金额</th>
					<th width="8%">接待人员</th>
					<th >接待日期</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>

     
      <div class="padding_blank10"></div>
    </div> 
</div>
</div>

<div class="copyright">
Copyright © 2014 China unicom All Right Reserved
</div>
</body>
</html>