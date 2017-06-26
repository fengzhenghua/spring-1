<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" uri="http://tydic.com/jsp/core" %>
<%
	String basePath= request.getScheme()+ "://"+ request.getServerName()+ ":"+ request.getServerPort() + "/tydic_webBase/";
	String fullPath= request.getScheme()+ "://"+ request.getServerName()+ ":"+ request.getServerPort()+ request.getContextPath()+ "/";
	String menuId = "";
	Object object = request.getParameter("menuId");
	if(null != object){
		menuId = object.toString();
	}
%>
<%-- <script type="text/javascript" src="<%=basePath%>js/jquery-1.8.0.min.js"></script> --%>
<%-- <script type="text/javascript" src="<%=fullPath%>js/jquery.js"></script> --%>
<script type="text/javascript" src="<%=basePath%>statics/js/jquerylib/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>statics/js/jquerylib/jqueryui/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.jqGrid.src.js"></script>
<script type="text/javascript" src="<%=basePath%>js/tydic.jqgrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/tydic.dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>js/tydic.ajax.js"></script>
<script type="text/javascript" src="<%=basePath%>js/tydic.easing.js"></script>
 <script type="text/javascript" src="<%=basePath%>js/jquery.Pages.js"></script>
<script type="text/javascript" src="<%=basePath%>js/tydic.Pages.js"></script> 
<script type="text/javascript" src="<%=basePath%>js/tydic.accordion.js"></script>
<script type="text/javascript" src="<%=basePath%>js/tydic.validate.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.validate.js"></script>
<script type="text/javascript" src="<%=basePath%>js/datePicker/WdatePicker.js"></script>
<!-- <script type="text/javascript" src="<%=fullPath%>js/getIdCard.js"></script> -->
<link type="text/css" rel="stylesheet" href="<%=fullPath%>css/share.css" >
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/tydic.jqgrid.css">
 <link type="text/css" rel="stylesheet" href="<%=basePath%>css/tydic.dialog.css">
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/tydic.Pages.css">
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/tydic.validate.css">
<link type="text/css" rel="stylesheet" href="<%=basePath%>statics/js/jquerylib/jqueryui/ui-lightness/jquery-ui-1.10.4.custom.css">
<script type="text/javascript" src="<%=fullPath%>js/common.js"></script>

<script type="text/javascript">
 	var application = {};
	application.basePath = '<%=basePath%>';
	application.fullPath = '<%=fullPath%>';
	var menuId = '<%=menuId%>';
	if('' != menuId){
		if(parent){
			parent.$("a[index_list=1]").each(function(){
				if($(this).attr("class")=="hover"){
					$(this).removeClass();
					$(this).addClass('sale_enter sale_enter_none');
				}
			});
			parent.$("#index_menu_"+menuId).removeClass();
			parent.$("#index_menu_"+menuId).addClass('hover');
		}
	}
	history.forward(); 
</script>
<!-- <object classid="clsid:10946843-7507-44FE-ACE8-2B3483D179B7" id="CVR_IDCard" name="CVR_IDCard" width="0" height="0"></object>
<div name="reader_context" id="reader_context">
</div> -->