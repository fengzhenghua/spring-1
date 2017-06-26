<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
<%@ page import="java.util.Date" %>
 <%@page import="org.jasig.cas.client.util.AssertionHolder"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>自助佣金</title>
<link href="<%=fullPath%>css/hnSelfHelpCommission.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/pre_accept.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/hnSelfHelpCommissionList.js"></script>
<script type="text/javascript" >
</script>

</head>

<body>
<input type="hidden" id = "operNo" value="${operNo}"/>
<input type="hidden" id = "operKind" value="${operKind}"/>
<input type="hidden" id = "deptNo" value="${deptNo}"/>
<input type="hidden" id = "chnlCode" value="${chnlCode}"/>
<div class="show" style=" height:530px;">
    
    <div class="info">
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
                    <select class="select_down gray_bgcolor_f8" style="width:190px; min-width:171px;" id="comm_type">
                            <option value="-1">----请选择----</option>
                            <option value="102">省公司</option>
                            <option value="103">分公司</option>
                            <option value="104">网格</option>
                        </select>
                </div>
                 <div class="left">
                  <div class="left_lable"></span>成本部门：</div>
                    <div class="left_lable">
                		<input type="buttn" id="comm_dept" placeholder="" class="input_text" onclick="getCommDept()" readOnly="true"></input>
               		</div>
               		<!--  
                    <a href="javascript:void(0)" onClick="getCommDept()">
                                <div class="input_button">选  择</div>
                            </a>
                    -->
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
            <div style="width:874px; height:308px; overflow:scroll; margin:0 auto; border:1px solid #cccccc;">
            <table class="table_infos_paylog" id="paylogTable">
			<thead>
				<tr>
				    <th nowrap>佣金规则ID</th>
					<th nowrap>业务类型</th>
					<th nowrap>佣金金额</th>
					<th nowrap>佣金日期</th>
					<th nowrap>订单ID</th>
					<th nowrap>设备号码</th>
					<th nowrap>产品名称</th>
					<th nowrap>录入人员</th>
					<th nowrap>省公司</th>
					<th nowrap>分公司</th>
					<th nowrap>网格</th>
					<th nowrap>省份渠道编码</th>
					<th nowrap>成本类型</th> 
					<th nowrap>订单来源</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		</div>
		<div class="padding_blank10"></div>
     <!-- 分页 -->
     <div class="padding_blank10"></div>
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
        </ul>
        <div class="padding_blank10"></div>
        
    </div> 
        
  <div class="padding_blank"></div>
  <div class="clear"></div>
</div>
<div class="bg_mask" id="bg_mask">
	<iframe class="bg_mask_iframe"> </iframe>
</div>
<div class="pop_win" id="fee_search" style="display:none;">
    <div class="msgbox">
    	<a href="javascript:hidediv('fee_search');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">网格</span></li>
        </ul>
            <div class="band_phone_small">
                <input type="text" class="search_input_small" placeholder="输入网格关键字模糊查询" id="TariffInput"></input>
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
<div class="pop_win" id="fee_search_chnl" style="display:none;">
    <div class="msgbox">
    	<a href="javascript:hidediv('fee_search_chnl');"><div class="msgbox_close"></div></a>
        <ul class="text_big">
            <li><span class="bold">网格</span></li>
        </ul>
            <div class="band_phone_small">
                <input type="text" class="search_input_small" placeholder="输入网格关键字模糊查询" id="TariffInput"></input>
                <a href="javascript:void(0)"><div class="input_button" id="searchTariff">搜  索</div></a>
            </div>
        <div class="scroll_v" style="position:relative;">
		<ul id="tariff_list_chnl">                       
            <li class="list">
            </li>
        </ul>
        </div>
        <div class="line_dashed_h"></div>
		<ul>
         <li class="center">
        <a href="javascript:void(0)" onClick="tariffConfirmChnl();"><div class="input_button">确 定</div></a> 
       	<a href="javascript:void(0)" onClick="tariffDeletChnl();"><div class="input_button">清 空</div></a>    		
        </li></ul>      
           
    </div>
</div>
</div>
</body>
</html>
 