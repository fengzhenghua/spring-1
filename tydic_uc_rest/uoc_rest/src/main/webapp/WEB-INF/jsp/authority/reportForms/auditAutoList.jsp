<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../common/common.jsp" %>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
<%@ page import="java.util.Date" %>

<%
    String nowDate = DateUtil.getCurrentDate();
    String beforeDate = DateUtil.getAfterMonth(new Date(), -1);
%>
    
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>统一销售服务系统,自助终端报表</title>
    <link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css"/>
    <link href="<%=fullPath%>css/auditAutoList.css" rel="stylesheet" type="text/css"/>
    <link href="<%=basePath%>js/jquery-ligerui-2.25/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css"/>
    <script src="<%=basePath%>js/jquery-ligerui-2.25/js/core/base.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/jquery-ligerui-2.25/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="<%=fullPath%>js/json2.js" type="text/javascript"></script>
    <script src="<%=fullPath%>js/auditAutoList.js" type="text/javascript"></script>
    
    <script type="text/javascript">
        var dateTime = {
            beforeDate: "<%=beforeDate%>",
            nowDate: "<%=nowDate%>"
        };
    </script>

</head>
<body>
	<input type="hidden"  id="apweb_url"  value="${apweb_url}"/><!--无纸化地址   -->
	<div class="show">
	    <div class="show_title_bg">
	        <div class="show_title" id="auto_terminal_list">
				自助终端报表
	        </div>
	        <div class="clear"></div>
	    </div>
	    <div class="box box_long" id="report_date_box">
	        <div class="show_big_title"><strong>.</strong>请选择统计报表的时间：</div>
	        <div class="context_audit" style="background:#f8f8f8;width: 900px">
	            <div class="table_box" style="float: left;">
		            <div class="left" border="0" align="center" cellpadding="0" cellspacing="0">
		                <div>
							<div class="left" width="212"> <t:date id="date_begin" name="date_begin"  maxDateElId="date_end"  value="<%=nowDate%>"  ></t:date></div>
							<div class="left" style="width: 24px;margin: 5px auto;text-align: center; font-weight:bold;">到</div><!-- width="24" align="center" -->
							<div class="left"width="220"> <t:date id="date_end" name="date_end"   minDateElId="date_begin"  value="<%=nowDate%>"  maxDate="<%=nowDate%>"  ></t:date></div>
		                </div>
		            </div>
		            
		        	<div class="left"><a id="reset" href="javascript:void(0);">重 置</a></div>
			        <div class="left"><a id="confirm" href="javascript:void(0);">查 询</a></div>
		    	</div>
	        </div>
	        <div class="clear"></div>
	    </div>
	    <div id="result_place" style="display: block;">
		    <div id="result_list" class="box box_long" style="padding:0 0 10px 0;">
		        <div id="rep_report_title" class="show_big_title"><strong>.</strong>报表结果展示：</div>
		        <div id="rep_report_list"></div>
		        <div class="clear"></div>
		    </div>
		    <div class="clear"></div>
	    </div>
	   	<div id="dashed_excel" class="dashed">
			<div><a id="export" href="javascript:void(0);">导 出</a></div>
	   	</div>
	</div>
	
	<div id="divCoverIframe" class="cover" style="display:none;">
		  <iframe id="coverIframe" name="left"  frameborder="0" height="100%" scrolling="no" width="100%" style="Z-INDEX: 1;WIDTH:100%; HEIGHT:100%;OVERFLOW: visible" src="<%=fullPath%>authority/reportForms/coverIframe">
	    </iframe>
	</div>
	
</body>
</html>