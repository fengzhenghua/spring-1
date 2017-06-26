<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
<%@ page import="java.util.Date" %>
 <%@page import="org.jasig.cas.client.util.AssertionHolder"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>销售录入</title>
<link href="<%=fullPath%>css/hnSelfHelpCommission.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/pre_accept.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/hnSalesList.js"></script>
<script type="text/javascript" >

</script>

</head>
<body>
<input type="hidden" id = "operNo" value="${operNo}"/>
<div class="show" style=" height:552px;">
    
    <div class="info">
    <!--  
    	<div class="detail_white">
        	<div class="padding_blank10"></div>
			<div class="btn_add float_left" id = "addRuleComm">新增</div>
            <div class="btn_edit float_left margin_left_1">修改</div>
            <div class="btn_del float_left margin_left_1">删除</div>
            <div class="padding_blank10"></div>
        </div> 
       -->
        <ul class="detail" style="height:515px;">
        	<div class="padding_blank10"></div>
        	 <li class="list_padding_zero">
                <div class="left">
                	<div class="left_lable bold">查询条件</div>
                </div>
            </li>
            <div class="padding_blank10"></div>
            <li class="list_padding_zero">
                <div class="left">
                	<div class="left_lable">业务号码：</div>
                    <div class="left_lable">
                    	<div class="left_data"><input type="text" id="order_number" placeholder="请输入业务号码" style="width:185px; min-width:160px;"></input></div>
                    </div>
                </div>
                <div class="left">
                	<div class="left_lable">渠道编码：</div>
                    <div class="left_lable">
                    	<div class="left_data"><input type="text" id="chnl_code" placeholder="请输入渠道编码" style="width:185px; min-width:160px;"></input></div>
                    </div>
                </div>
                
            </li>
            
             <div class="padding_blank10"></div>
           <li class="list_padding_zero">
                <div class="left">
                	 <div class="left_lable">开始时间：</div>
                    <div class="left_lable">
                		 <t:date id="smeeting" name="smeeting" minDateElId="smeeting" value="<%=DateUtil.getLastWeekDay()%>" maxDate="2050-01-01"></t:date>
               		</div>
               		</div>
                 <div class="left">
                	 <div class="left_lable">结束时间：</div>
                    <div class="left_lable">
                		 <t:date id="emeeting" name="smeeting" minDateElId="smeeting" value="<%=DateUtil.getCurrentDate() %>" maxDate="2050-01-01"></t:date>
               		</div>
               		</div>
            </li>
            <div class="padding_blank2"></div>
            <div class="right"><a href="#"  id="query" class="search">查询</a></div>
           <div class="padding_blank10"></div>
            <li class="list_padding_zero">
                <div class="left">
                	<div class="left_lable bold">查询结果</div>
                </div>
            </li>
			<div style="width:874px; height:340px; overflow:scroll; margin:0 auto; border:1px solid #cccccc;">
            <table class="table_infos_paylog" id="paylogTable">
			<thead>
				<tr>
				    <th width="3%">*</th>
					<th width="8%">业务号码</th>
					<th width="8%">活动类型</th>
					<th width="8%">录入工号</th>
					<th width="8%">渠道编码</th>
					<th width="12%">业务受理时间</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
          </div>
          <div class="padding_blank10"></div>
   <!-- 分页 -->
	<div id="pageControl" style="text-align: right; width: 905px;">
	   <input type="button" value="首页" onClick="topPage()" id="topPage">
	   <input type="button" value="上一页" onClick="previousPage()" id="previousPage"> 
	   <input type="button" value="下一页" onClick="nextPage()" id="nextPage"> 
	   <input type="button" value="尾页" onClick="bottomPage()" id="bottomPage">
		<!-- 直接跳转 -->
		&nbsp;当前第&nbsp;<a id="pageNo"></a>页&nbsp;&nbsp; 共<a id="totalPages"></a>页-向 &nbsp;
	    <input type="text" id="jumpTo"style="width: 20px; text-align: center; FONT-SIZE: 10px;" />页
		<input type="button" value="GO" onClick="jumpTo()" />
	 </div>
	 <div class="padding_blank"></div>
  <div class="clear"></div>
</div>  
        </ul>

        <div class="padding_blank10"></div>
        
    </div> 


</body>
</html>
 