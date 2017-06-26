<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
    <script type="text/javascript" src="../js/jquery-easyui-1.3.5/jquery.min.js"></script>   
    <script type="text/javascript" src="../js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>   
    <script type="text/javascript" src="../js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>   
    <link rel="stylesheet" type="text/css" href="../js/jquery-easyui-1.3.5/themes/default/easyui.css" />   
    <link rel="stylesheet" type="text/css" href="../js/jquery-easyui-1.3.5/themes/icon.css" />
    <style>
 
</style>
</head>

	
<body style="width:1024x; height:400px; margin:0 auto;background:url(../style/images/main.jpg);">
<div  id="login" style="background:url(../style/images/login.jpg);margin-top:150px;margin-left:200px; width:588px; height:351px;">
 <div style=" width:200px; height:1px;"></div>
    <div id="rr" style="margin-top:150px;margin-left:290px; width:300px; height:50px;">
         <p>中国联通AIM应用管理系统|重庆分公司</p>
         <p>工 号： <input type="text"  name="tex" id="manager" class="textbox"></input></p>
         <p>密 码： <input type="password"  name="pwd" id="password" class="textbox"></input></p>
         <a id="tt" href="#" class="easyui-linkbutton" onclick="login()">登陆</a>
         <a id="Reset" href="###" class="easyui-linkbutton">重置</a>
         <div id="yejiao" style="margin-top:20px;margin-left:-280px; width:400px; height:1px;">©2010深圳天源迪科信息技术股份有限公司</div>
   </div>
    
   </div>
  

</body>
<script type="text/javascript">

$(function(){
	
	$('#manager').tooltip({
		position:'right',
		content:'请输正确的工号',
		showEvent:'click'
	});
	
	$('#password').tooltip({
		position:'right',
		content:'请输正确的密码',
		showEvent:'click'
	});
	

});
 
 function login(){
		var manger=$('#manager').val();
		var password=$('#password').val();
		alert(123);
		if(manger=='tgh'){
			if(password=='123'){
				var login="{'loginCode':'CJF001','name':'SEQ_NAME','phone':'18996239623'}";
				$.ajax({
					type:"POST",
					url:"jsp/landAgent.jsp",
					data:{url:"http://135.24.251.53:8090/jsonRequest/HttpServletHandler",Param:login},
					dataType:"json",
					success:function(data, textStatus){
						alert(data);
						alert(data.code+data.codeName);
						
				
						
						window.location.href='../jspPage/main.jsp';
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
						console.log(XMLHttpRequest);
					}
				});
				
			}else{
				alert("密码错误！");
			}
		}else{
			alert("工号不存在！");
		}
 }

</script>
</html>