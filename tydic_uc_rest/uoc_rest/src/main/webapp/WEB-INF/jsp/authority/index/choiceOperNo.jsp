<%@page import="com.tydic.unicom.crm.web.uss.constants.ControllerMappings"%>
<%@include file="../common/common.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <%
 /* Object  req_bindflag=request.getAttribute("bindflag");
	if (req_bindflag!=null){
		System.out.println("req_bindflag="+req_bindflag);


	}else{
		System.out.println("req_bindflag is null ");
	}
	Object  bindoperno=request.getAttribute("bindoperno");
	if (req_bindflag!=null){
		System.out.println("bindoperno="+bindoperno);


	}else{
		System.out.println("req_bindflag is null ");
	}*/

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择工号</title>
<script type="text/javascript" src="<%=fullPath%>js/url_helper.js"></script>
<script type="text/javascript">
function isNullOrEmpty(strVal) {
	if (strVal == '' || strVal == null || strVal == undefined) {
	return true;
	} else {
	return false;
	}
}
var bind_oper_no;
function bind_confirm(){
	var commend_no = $("input[name='checkbox']:checked").val();
	var oper_no= $("#bindoperno").val();
	var mr=Math.random();
	var URL = application.fullPath + 'authority/index/bind?commend_no='+commend_no+'&oper_no='+oper_no+'&mr='+mr;
	//var URL = application.fullPath + 'authority/index/bind?commend_no='+commend_no+'&oper_no='+oper_no;
	//var URL = application.fullPath + 'authority/index/bssbind?commend_no='+commend_no+'&oper_no='+oper_no;
	$.ajax({
		url:URL,
		type:'get',
		waitMsg:"读取中..",
		success:function(bindfalg){
			if (bindfalg){
				alert("绑定成功！");
				$("#bind_agent_develops").hide();
				window.location.href=application.fullPath + 'authority/index/choiceOperNo';
			}else{
				alert("绑定失败！");
			}

		}

	});
};
function queryMyagentDev(){
	var qry_dev_name=$.trim($("#qry_dev_name").val());
	var qry_dev_code=$.trim($("#qry_dev_code").val());
	var qry_linkman_phone=$.trim($("#qry_linkman_phone").val());
	if (isNullOrEmpty(qry_dev_name)&&isNullOrEmpty(qry_dev_code)&&isNullOrEmpty(qry_linkman_phone)){
		alert("发展人名称 ,发展人编码,电话：至少有一个不为空");
		return;
	}else{

	}
	/*var URL = application.fullPath + 'authority/index/queryInfoAgentDevelopersForBinding?oper_no='+bind_oper_no;
	if(!(isNullOrEmpty(qry_dev_name))){
		URL=URL+'&dev_name='+qry_dev_name;
	}
	if(!(isNullOrEmpty(qry_dev_code))){
		URL=URL+'&dev_code='+qry_dev_code;
	}
	if(!(isNullOrEmpty(qry_linkman_phone))){
		URL=URL+'&linkman_phone='+qry_linkman_phone;
	}*/
	var URL = application.fullPath + 'authority/index/queryInfoAgentDevelopersForBinding';

	var data1={
			"oper_no":bind_oper_no,
			"dev_name":qry_dev_name,
			"dev_code":qry_dev_code,
			"linkman_phone":qry_linkman_phone
			   };
	$.ajax({
		url:URL,
		//type:'get',
		type:'post',
		data:data1,
		waitMsg:"读取中..",
		success:function(bindlists){
			if (bindlists == null || bindlists == undefined ||bindlists.length==0){
				alert("无对应发展人");
			}else{
				var lists=$("#agentdevloperslist");
				$(".mybindlist").remove();
				for(var j = 0;j < bindlists.length;j++){
	        		var data = bindlists[j];
	        		var line;
	        		//首行选中，添加变量 oper_no
	        		if(j==0){
	        			line='<tr class="mybindlist">';
	        			line=line+'<td height="26" align="center" bgcolor="#F8F8F8"><input type="radio" name="checkbox" value="'+data.dev_code+'" checked="checked"/></td>';
	        			line=line+'<td align="center" bgcolor="#F8F8F8">'+data.dev_code+'</td>';
	        			line=line+'<td align="center" bgcolor="#F8F8F8">'+data.linkman_phone+'</td>';
	        			line=line+'<td align="center" bgcolor="#F8F8F8">'+data.dev_name+'</td>';
	        			line=line+'<td align="center" bgcolor="#F8F8F8">'+data.chnl_name+'</td>';
	        			line=line+'<input type="hidden"  id="bindoperno"  value="'+data.oper_no+'">';
	        			line=line+'</tr>';
	        			lists.after(line);
	        		}else{
	        			line='<tr class="mybindlist">';
	        			line=line+'<td height="26" align="center" bgcolor="#F8F8F8"><input type="radio" name="checkbox" value="'+data.dev_code+'" /></td>';
	        			line=line+'<td align="center" bgcolor="#F8F8F8">'+data.dev_code+'</td>';
	        			line=line+'<td align="center" bgcolor="#F8F8F8">'+data.linkman_phone+'</td>';
	        			line=line+'<td align="center" bgcolor="#F8F8F8">'+data.dev_name+'</td>';
	        			line=line+'<td align="center" bgcolor="#F8F8F8">'+data.chnl_name+'</td>';
	        			line=line+'</tr>';
	        			lists.after(line);
	        		}
	        	}
			}
		}
	});
};
function showQuerydia(v_oper_no){
	showdiv("bind_agent_develops");
	bind_oper_no=v_oper_no;
	$("#bind_agent_develops").show();
};
function bind_close(v_oper_no){
	$(".mybindlist").remove();
	$("#bind_agent_develops").hide();
	hidediv("bind_agent_develops");

};

function showdiv(popWinId) {
	document.getElementById("bg_mask").style.display ="block";
	document.getElementById(popWinId).style.display ="block";
};
function hidediv(popWinId) {
	document.getElementById("bg_mask").style.display ='none';
	document.getElementById(popWinId).style.display ='none';
};
var redUrl="";
window.onload=function(){
	//兼容ie6
	/*if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.split(";")[1].match(/6./i)=="6.")
	{
		DD_belatedPNG.fix('img,.search');
	} */
	$("#bind_confirm").click(bind_confirm);
	$("#queryMyagentDev").click(queryMyagentDev);
	$("#bind_close").click(bind_close);

	var myurl = new objURL();
	redUrl = myurl.get('redurl');
	if(redUrl != undefined){
		redUrl = ("&redurl=" + redUrl);
	} else{
		redUrl = "";
	}

	var operNo = myurl.get('oper_no');

    $(".login").each(function () {
        if (redUrl != "") {
            $(this).attr("href", $(this).attr("href") + redUrl);
        }

        if ($(this).attr("href").indexOf(operNo) > 0) {
            window.location.href = $(this).attr("href");
        }
    });
};

</script>
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/share1.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/default.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/share.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/check_income.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/response_mobi2.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery-ligerui-2.25/js/core/base.js" type="text/javascript"></script>
<script src="<%=basePath%>js/jquery-ligerui-2.25/js/plugins/ligerGrid.js" type="text/javascript"></script>
</head>
<body>

<div class="banner">
	<div class="box">
    	<div class="l">
    	  <div class="top_content">
    		<img src="<%=fullPath%>images/banner_income.png" />
    	   <div class="top_info">
    	    <a href="javascript:;" onclick="parent.window.location.href='<%=fullPath%>authority/index/logout';return false;" class="code_exit">安全退出</a>
    	   </div>
    	  </div>
    	</div>
    </div>
</div>
<div class="show" style="OVERFLOW-X: hidden; OVERFLOW-Y: auto;height:350px;">
    <div class="show_title_bg">
        <div class="show_title"><span class="crumb">当前位置：账号选择</span>账号选择
        </div>
    </div>
  <div class="show_big_title">请选择子工号登陆：</div>
    <table width="936" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#e7e7e7">
      <tr>
        <td width="290" height="40" align="center" bgcolor="#ececec">营业厅名称</td>
        <td width="150" align="center" bgcolor="#ececec">员工工号</td>
        <td width="190" align="center" bgcolor="#ececec">员工姓名</td>
        <td width="170" align="center" bgcolor="#ececec">人员角色</td>
        <td align="center" bgcolor="#ececec">操作</td>
      </tr>
      <c:forEach items="${list}" var="i">

      <tr>
        <td height="40" align="center" bgcolor="#f8f8f8">${i.dept_name}</td>
        <td align="center" bgcolor="#f8f8f8">${i.oper_no}</td>
        <td align="center" bgcolor="#f8f8f8">${i.oper_name}</td>
        <td align="center" bgcolor="#f8f8f8">${i.role_name}</td>
       <%--  add by haolong YUN-143  --%>
        <c:if test="${i.commend_no}"></c:if>
        <c:choose>
			      <c:when test="${not empty i.commend_no or i.role_id eq 2 or province_code eq 'nx'}"><td align="center" bgcolor="#f8f8f8"><a href="<%=fullPath%>authority/index/index?oper_no=${i.oper_no}" class="login">登陆</a></td></c:when>
            <c:otherwise><td align="center" bgcolor="#f8f8f8"><a href="#" onclick="showQuerydia('${i.oper_no}');" class="login" >绑定</a></td></c:otherwise>
		  </c:choose>
      </tr>
    </c:forEach>
    </table>
</div>
<%--  style="display: none;"
<div class="cover"><iframe name="left" id="left" frameborder="0" height="100%" scrolling="no" width="100%" style="Z-INDEX: 1;WIDTH:100%; HEIGHT:100%;OVERFLOW: visible" src="<%=fullPath%><%=ControllerMappings.USS_WEB_INDEX %>/iframeCover.html"></iframe></div>--%>
<div class="show-select" id ="bind_agent_develops"  style="display: none;">
	<div class="icon-close" id="bind_close"></div>
	<div class="select-title">需要绑定默认发展人：</div>
	<div class="search-input">
		<div class="leble">发展人名称：</div>
		<div class="input"><input class="branch-code" type="text"  value="" id="qry_dev_name" /></div>
		<div class="leble">发展人编码：</div>
		<div class="input"><input class="branch-code" type="text"  value="" id="qry_dev_code" /></div>
		<div class="leble">发展人号码：</div>
		<div class="input"><input class="branch-code" type="text"  value="" id="qry_linkman_phone" /></div>
		<a href="#" id="queryMyagentDev">查 询</a>	</div>
	<div class="select-table">
		<table width="936" border="0" cellspacing="1" cellpadding="0" bgcolor="#E7E7E7">

			<tr id ="agentdevloperslist">
				<td height="34" width="70" align="center" bgcolor="#e7e7e7"><strong>选择</strong></td>
				<td width="120" align="center" bgcolor="#e7e7e7"><strong>发展人编码</strong></td>
				<td width="120" align="center" bgcolor="#e7e7e7"><strong>发展人号码</strong></td>
				<td width="120" align="center" bgcolor="#e7e7e7"><strong>发展人名称</strong></td>
				<td align="center" bgcolor="#e7e7e7"><strong>渠道名称</strong></td>
			</tr>
		</table>
	</div>
	<div class="pages">
   	  <div class="pages_l"><a href="###" class="robots" id="bind_confirm">确 定</a></div>

    	<div class="clear"></div>
    </div>
</div>
 <div class="show">
 <div class="wrap">
	<div class="download">
         <div class="download_body">
        	<ul>
            	<li class="ios"><a href="${ios_download_url}" target="_blank"></a></li>
                <li class="android"><a href="${android_download_url}"></a></li>
<!--         	    <li></li> -->
<%--             	<li class="download_scan_ios"><img src="<%=fullPath%>images/download_scan_sys_ios.png"></li> --%>
<%--             	<li class="download_scan_android"><img src="<%=fullPath%>images/download_scan_sys_android.png"></li> --%>
<!--         	    <li></li> -->

            </ul>
            <div class="scan">
                <div class="scan_img"><img src="<%=fullPath%>images/download_scan_sys_ios.png"></div>
                <div class="scan_img"><img src="<%=fullPath%>images/download_scan_sys_android.png"></div>
            </div>
            <div class="clear"></div>
        </div>
      <div class="download_instruction">
        安卓版运行要求,苹果系统不需要以下设置<br>
        1) 安装手机QQ浏览器，高于或等于5.1正式版（<a href="http://163.177.158.81/soft.imtt.qq.com/browser/channel/liberty_spread/70/363/qqbrowser_5.7.2.1400_20820.apk?mkey=550abee1120e398c&f=d488&p=.apk">点击下载</a>）。<br>
        2) 手机安卓系统版本高于或等于4.0版本。<br>
        3) 手机空余内存大于500M<br>
		4) 安卓客户端请使用qq扫码<br>
        </div>
    </div>

</div>
</div>
<div class="copyright">
Copyright © 2014 China unicom All Right Reserved
</div>

<div class="bg_mask" id="bg_mask">
	<iframe class="bg_mask_iframe"> </iframe>
</div>

</body>
</html>