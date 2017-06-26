<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
<%@include file="../common/common.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>销售</title>
<link href="<%=fullPath%>css/order_processing.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(function() {
		 $("#bss_iframe_main").height($(window).height()-157); 
		$("#grid tr:odd").addClass("odd");
		$("#grid tr:even").addClass("even");
		$("#start_time").addClass("width");
		$("#end_time").addClass("width");
		
	});
	
	/**
	*点击菜单操作
	*/
	function bssIframeLoad(menu_id){
		$("#menu_"+menu_id).addClass("hover");
		var url  = application.fullPath+"authority/bssBusi/bssInitWeb?menu_id="+menu_id;
		$("#bss_iframe_main").attr("src",url);
	}
	
	function SetWinHeight(obj){
	     var win=obj;
	     if (document.getElementById)
	     {
	         if (win && !window.opera)
	         {
	           /*   if (win.contentDocument && win.contentDocument.body.offsetHeight) 

	                win.height = win.contentDocument.body.offsetHeight; 
	            else if(win.Document && win.Document.body.scrollHeight) */
	               //  win.height = win.Document.body.scrollHeight;
	           $("#bss_iframe_main").height(1000); 
	         }
	     }
	 }

</script>
</head>
<body>
	<div class="crumbs">当前位置: <a href="###">首页</a> > <a href="###">BSS</a>
	</div>
	<div class="sale_content">
		<div class="content">
			<table width="1000" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="175" valign="top" bgcolor="#EEEEEE" class="c_left">
						<div class="wrap">
							<ul>
								<c:forEach var="menuVo" items="${listMenuVo}" varStatus="i">
								<li class="first"><a href="#"  id="menu_${menuVo.menu_id}" onclick="bssIframeLoad('${menuVo.menu_id}')">${menuVo.menu_name}</a></li>
								</c:forEach>
							</ul>

						</div>

					</td>
					<td valign="top" class="c_right">
						<div id="bss_web_div"></div>
						<!-- <div class="wrap_right"> -->
							<iframe onload="javascript:SetWinHeight(this)"    padding-top="0" frameborder="0" name="bss_iframe_main" id="bss_iframe_main" width="100%" src=""></iframe>
						<!-- </div> -->
					</td>
				</tr>
			</table>
		</div>
	</div>



</body>
</html>