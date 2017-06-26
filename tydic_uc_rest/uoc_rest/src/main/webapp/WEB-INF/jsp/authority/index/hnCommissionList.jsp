<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
 <%@page import="org.jasig.cas.client.util.AssertionHolder"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>佣金规则</title>
<link href="<%=fullPath%>css/hnSelfHelpCommission.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/pre_accept.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/hnCommissionList.js"></script>
<script type="text/javascript" >
</script>

</head>

<body>
<input type="hidden" id = "operNo" value="${operNo}"/>
<input type="hidden" id = "deptNo" value="${deptNo}"/>
<div class="show" style=" height:580px;">
    
    <div class="info"> 
    	<div class="detail_white">
        	<div class="padding_blank10"></div>
			<div class="btn_add float_left" id = "addRuleComm">新增</div>
			<div class="btn_edit float_left margin_left_1" id ="updateRuleComm">修改</div>
            <div class="btn_del float_left margin_left_1" id = "delRuleComm">删除</div>
            <div class="padding_blank10"></div>
        </div> 
        <ul class="detail" style="height:490px;">
        	<div class="padding_blank10"></div>
        	<li class="list_padding_zero">
                <div class="left">
                	<div class="left_lable bold">查询条件</div>
                </div>
            </li>
            <div class="padding_blank10"></div>
              <li class="list_padding_zero">
                <div class="left">
                	<div class="left_lable">成本类型：</div>
                    	 <select class="select_down gray_bgcolor_f8" style="width:171px; min-width:171px;" id="comm_type">
                            <option value="101">----请选择----</option>
                            <option value="102">省公司</option>
                            <option value="103">分公司</option>
                            <option value="104">网格</option>
                        </select>
                </div>
                <div class="left">
                	<div class="left_lable">是否生效：</div>
                    	 <select class="select_down gray_bgcolor_f8" style="width:171px; min-width:171px;" id="effect_flag">
                            <option value="-1">----请选择----</option>
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                </div>
            </li>
            <div class="padding_blank10"></div>
            <!--
            <div style="width:920px;">  
            <div class="right"><a href="#"  id="updateRuleComm" class="btn_del float_left margin_left_1"">删除</a></div>
            <div class="right"><a href="#"  id="delRuleComm" class="btn_edit float_left margin_left_1"">修改</a></div>
            <div class="right"><a href="#"  id="addRuleComm" class="btn_add float_left"">新增</a></div>
           </div>
            -->
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
				    <th width="25px">*</th>
				    <th nowrap>成本类型</th>
					<th nowrap>成本部门</th>
					<th nowrap>渠道类型</th>
					<th nowrap>订单类型</th>
					<th nowrap>活动类型</th>
					<th nowrap>产品类型</th>
					<th nowrap>终端类型</th>
					<th nowrap>店员奖励</th>
					<th nowrap>主管奖励</th>
					<th nowrap>三级奖励</th>
					<th nowrap>四级奖励</th>
					<th nowrap>生效时间</th>
					<th nowrap>失效时间</th>
					<th nowrap>规则状态</th>
					<!-- <th width="15%">创建时间</th> -->
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		</div>
        </ul>
        <div class="padding_blank10"></div>
		  <!-- 分页 -->
	  <div id="pageControl" style="text-align: right; width: 935px;">
	   <input type="button" value="首页" onClick="topPage()" id="topPage">
	   <input type="button" value="上一页" onClick="previousPage()" id="previousPage"> 
	   <input type="button" value="下一页" onClick="nextPage()" id="nextPage"> 
	   <input type="button" value="尾页" onClick="bottomPage()" id="bottomPage">
		<!-- 直接跳转 -->
		&nbsp;当前第&nbsp;<a id="pageNo"></a>页&nbsp;&nbsp; 共<a id="totalPages"></a>页-向 &nbsp;
	    <input type="text" id="jumpTo"style="width: 20px; text-align: center; FONT-SIZE: 10px;" />页
		<input type="button" value="GO" onClick="jumpTo()" />
	 </div>
        <div class="padding_blank10"></div>
        <div class="padding_blank10"></div>
        
    </div> 
        
  <div class="padding_blank"></div>
  <div class="clear"></div>
</div>

</body>
</html>
 