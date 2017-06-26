<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" uri="http://tydic.com/jsp/core" %>
<%
	String basePath= "http://localhost:8080/tydic_webBase/";
	String fullPath= request.getScheme()+ "://"+ request.getServerName()+ ":"+ request.getServerPort()+ request.getContextPath()+ "/";
%>
<%-- <script type="text/javascript" src="<%=basePath%>js/jquery-1.8.0.min.js"></script> --%>
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
<link type="text/css" rel="stylesheet" href="<%=fullPath%>css/share.css" >
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/tydic.jqgrid.css">
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/tydic.dialog.css">
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/tydic.Pages.css">
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/tydic.validate.css">
<link type="text/css" rel="stylesheet" href="<%=basePath%>statics/js/jquerylib/jqueryui/ui-lightness/jquery-ui-1.10.4.custom.css">
<script type="text/javascript">
 	var application = {};
	application.basePath = '<%=basePath%>';
	application.fullPath = '<%=fullPath%>';
	/* document.onkeydown = function(e){ var e=e||window.event var evs=e.srcElement?e.srcElement:e.target; if(evs.type!="password" && evs.type!="text"){  if(e.keyCode==8){   if(window.event){    e.keyCode=0;     e.returnValue=false;   }else{    e.preventDefault();   }  }else if(e.altKey && ((e.keyCode==37)||(e.keyCode==39))){   if(window.event){    e.returnValue=false;   }else{    e.preventDefault();   }  } }}
	window.history.forward(1); 
	function   document.oncontextmenu(){event.returnValue=false;}
	function   window.onhelp(){return   false}   
	function   document.onkeydown()  {      
		if ((window.event.altKey)&& ((window.event.keyCode==37)|| (window.event.keyCode==39))) {           
			event.returnValue=false;      
		}            
		if   ((event.keyCode==8)  ||  (event.keyCode==116)|| (event.ctrlKey   &&   event.keyCode==82)){     
			event.keyCode=0;            
			event.returnValue=false;           
		}     
		if   (event.keyCode==122){event.keyCode=0;event.returnValue=false;}     
		if   (event.ctrlKey   &&   event.keyCode==78)   event.returnValue=false;       
		if   (event.shiftKey   &&   event.keyCode==121)event.returnValue=false;         
		if   (window.event.srcElement.tagName   ==   "A "   &&   window.event.shiftKey)  window.event.returnValue   =   false;                          
		if   ((window.event.altKey)&&(window.event.keyCode==115)) {              
			window.showModelessDialog( "about:blank ", " ", "dialogWidth:1px;dialogheight:1px ");              
			return   false;      
		}  
	}   */
	history.forward(); 
</script>

