<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>销售</title>
<link href="<%=fullPath%>css/sale.css" rel="stylesheet" type="text/css" />
<script src="<%=fullPath%>js/queryNumber.js" type="text/javascript"></script>
 <script type="text/javascript">
$(function(){
//$(".position").hide();
	var card_generation = $("#card_generation").val();
	if(card_generation=="3G"){
		$("#ofr_sub_type_3g").show();
	}else{
		$("#ofr_sub_type_3g").hide();
	}
});
</script> 
</head>
<body>
    <div class="sale_content sale_content_none">
        <div class="sale_shop"> 
            <div class="sale_code">
                <h2>号码查询</h2>
                <ul>
                    <li>
                    	<t:select id="card_generation" codeType="card_generation"></t:select>
                    </li>
                    <li  id="ofr_sub_type_3g" style="display: none" class="radio">
                    <strong>开户类型：</strong>
                    <t:radio   codeType="ofr_sub_type_3g" name="ofr_sub_type_3g" checkFirst="true"></t:radio>
                    </li> 
                    <li class="tip" ><input id="query_num" type="text" value="请输入后8位中2~4位数字查询" class="relative"/><br /><div id="query_num_tishi" class="relative_show"></div></li>
                    <li><input name="submit" type="submit" id="submit" value="提交"
                        class="submit" /> <input name="reset" type="reset" id="reset"
                        value="重置" class="reset" /></li>
                </ul>
            </div>
            <div class="clear"></div>
         </div>
    </div>


</body>
</html>