<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <div class="sale_header">
    <div class="sale_logo">
        <img src="<%=fullPath%>images/sale_logo.jpg" />
    </div>
    <div class="sale_header_right">
        
        <a href="javascript:;" onclick="parent.window.location.href='<%=fullPath%>authority/index/logout';"" class="sale_exit">退出</a>
        <a href="###" class="sale_line"></a>
        <c:forEach var="infoAuth" items="${info_Authority_List}">
            <a href="javascript:void(0)" class="sale_enter" onclick="javascript:window.open('${infoAuth.authority_url}${infoAuth.authority_index}')">${infoAuth.authority_name}</a>
        </c:forEach>
        <a href="###" class="sale_line"></a>
          <a href="javascript:void(0)" title="切换子工号，请点击此处！" onclick="parent.window.location.href='<%=fullPath%>authority/index/choiceOperNo'" style="display:inline-block;line-height:24px;float:right; height:24px; width:32px; no-repeat center ;"><font color="red">[切换]</font></a>
        
        <a href="###" class="sale_line"></a>
        <a href="javascript:void(0)" class="sale_boss" >工号：${info_Oper.oper_no } ${info_Oper.oper_name }</a><!--<a href="###" class="sale_search">搜索</a><a href="###" class="sale_input_bg"><input type="text" value="" /></a>-->
    </div>
    <div class="clear"></div>
</div>
</body>
</html>