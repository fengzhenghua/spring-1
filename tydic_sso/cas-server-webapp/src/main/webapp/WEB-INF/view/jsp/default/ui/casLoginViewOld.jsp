<%--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License.  You may obtain a
    copy of the License at the following location:

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.tydic.unicom.crm.listener.GlobalConfig" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    String fullPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="css/share.css" rel="stylesheet" type="text/css"/>
    <link href="css/login.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        #username {
            text-transform: uppercase;
        }
    </style>
    <script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>

    <script type="text/javascript" src="js/png_transparent.js"></script>
    <script type="text/javascript" src="js/url_helper.js"></script>
    <script type="text/javascript">
        var getSessionId = '<%=GlobalConfig.newInstance().get("getSessionId")%>';
        var getVerifyCode = '<%=GlobalConfig.newInstance().get("getVerifyCode")%>';
        var provinceCode = '<%=GlobalConfig.newInstance().get("province_code")%>';
        $(function () {
            var myurl = new objURL();
            //兼容ie6
            /* if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.split(";")[1].match(/6./i)=="6.")
             {
             DD_belatedPNG.fix('');
             }  */
            if (provinceCode == "cq") {
                document.title = "重庆联通电子流智能营业厅";
                document.getElementById("noyx").style.display = 'none';
            } else if (provinceCode == "nx") {
                document.title = "宁夏联通电子流智能营业厅";
            } else if(provinceCode == "gx"){
                document.title = "广西联通电子流智能营业厅";
                $(".copyright").removeClass("copyright").addClass("copyright_v2");
                $(".login").addClass("login_v2");
                $("#login_code_style").show();
            }

            if (self != top && myurl.get('pwd') == undefined) {
                try{
                    window.parent.document.location.href = '<%=fullPath%>';
                }
                catch(e){}
            }
            initSessionId();

            //用户名 监听事件
            $('#username').keypress(function (e) {
                $('#username_close').show();
            });
            //密码 输入时候添加监听事件
            $('#password').keypress(function (e) {
                $('#password_close').show();
            });

            $(function () {
                $(document).keypress(function (e) {
                    if (e.keyCode == 13)
                        login();
                });
            });

            if (getCookies("tydic_uss_username") != null) {
                $("#username").val(getCookies("tydic_uss_username"));
            }
            var tag = false;
            if ($("#msg").text() != "") {
                $("#err_password").show();
                tag = true;
            } else {
                $("#err_password").hide();
            }

            $("#username").bind("keydown", function (e) {
                if (e.keyverifyCode == 13) {
                    $("#password").focus();
                }
            });
            $("#password").bind("blur", function () {
                if ($.trim($(this).val()) == "") {
                    $("#null_password").show();
                } else {
                    $("#null_password").hide();
                }
            });
            $("#password").bind("keydown", function (e) {
                if (e.keyverifyCode == 13) {
                    $("#verifyCode").focus();
                }
            });
            $("#verifyCode").bind("blur", function () {
                if ($.trim($(this).val()) == "") {
                    $("#null_verifyCode").show();
                } else {
                    $("#null_verifyCode").hide();
                }
            });
            $("#verifyCode").bind("keydown", function (e) {
                if (e.keyCode == 13) {
                    login();
                }
            });

            if (myurl.get("pwd") != undefined) {
                var isError = false;

                $("div .error").each(function(){
                    if ($(this).css("display") != "none"){
                        isError = true;
                    }
                });

                if (!isError){
                    $("#username").val(myurl.get('usn'));
                    $("#password").val(myurl.get('pwd'));
                    $("#first").attr("checked", "");
                    $("#second").attr("checked", "");
                    $("#ulp").attr("checked", "checked");
                    setTimeout(function(){
                        login();
                    },500);
                }
            }
        });


        function login() {
            checkRoleType();
            
            if(provinceCode =="gx"){
				//效验验证码
           		 var loginVerifyflag =checkLoginVerifyCode();
            	if(!loginVerifyflag){
            		$("#error5").show();
            		return;
          		  }
            }
            
            if ($.trim($("#username").val()) == '' || $.trim($("#username").val()) == '请输入统一工号或手机号码') {
                $("#error1").show();
                $("#err_password").hide();
                $("#error2").hide();

                return;
            } else {
                $("#error1").hide();

            }

            if ($.trim($("#password").val()) == "") {
                $("#error2").show();
                $("#err_password").hide();
                $("#error1").hide();
                return;
            } else {
                $("#error2").hide();
            }

            var username = $("#username").val().toUpperCase();
            addCookies("tydic_uss_username", username, {
                expires: 30
            });

            $("#username").val(username);

            $("#loginForm").submit();
        }
        function hideUserName() {
            $("#null_username").hide();
        }
        function hidePassWord() {
            $("#null_password").hide();
        }
        function hideverifyCode() {
            $("#null_verifyCode").hide();
        }

        function changeColorSubOver() {
            $(".btn-submit")
                    .attr(
                            {
                                "style": "width:52px;background:#CB000A; border:1px solid #CB000A; color:#FFF;cursor:pointer;cursor:hand;"
                            });
        }
        function changeColorSubOut() {
            $(".btn-submit")
                    .attr(
                            {
                                "style": "width:52px;background:#EDEDED; border:1px solid #ACACAC; color:#333;"
                            });
        }
        function changeColorResOver() {
            $(".btn-reset")
                    .attr(
                            {
                                "style": "width:52px;background:#CB000A; border:1px solid #CB000A; color:#FFF;cursor:pointer;cursor:hand;"
                            });
        }
        function changeColorResOut() {
            $(".btn-reset")
                    .attr(
                            {
                                "style": "width:52px;background:#EDEDED; border:1px solid #ACACAC; color:#333;"
                            });
        }

        function initSessionId() {
            $
                    .ajax({
                        url: getSessionId,
                        type: "post",
                        dataType: "json",
                        success: function (message) {
                            $("#jsessionid").val(message.args.jsessionid);
                            $("#verifyCodeImg")
                                    .attr(
                                            "src",
                                            getVerifyCode
                                            + message.args.jsessionid);
                        },
                        error: function () {

                        }
                    });
        }
        // 添加Cookie
        function addCookies(name, value, options) {
            if (arguments.length > 1 && name != null) {
                if (options == null) {
                    options = {};
                }
                if (value == null) {
                    options.expires = -1;
                }
                if (typeof options.expires == "number") {
                    var time = options.expires;
                    var expires = options.expires = new Date();
                    expires.setTime(expires.getTime() + time * 1000 * 60 * 60 * 24);
                }
                document.cookie = encodeURIComponent(String(name))
                        + "="
                        + encodeURIComponent(String(value))
                        + (options.expires ? "; expires="
                        + options.expires.toUTCString() : "")
                        + (options.path ? "; path=" + options.path : "")
                        + (options.domain ? "; domain=" + options.domain : ""),
                        (options.secure ? "; secure" : "");
            }
        }
        //删除cookie
        function delCookie(name) {
            addCookies(name, null, {
                expires: -1
            });
        }
        //获得 cookie
        function getCookies(name) {
            if (name != null) {
                var value = new RegExp("(?:^|; )"
                        + encodeURIComponent(String(name)) + "=([^;]*)")
                        .exec(document.cookie);
                return value ? decodeURIComponent(value[1]) : null;
            }
        }

        function refreshVerifyCode() {
            var src = $("#verifyCodeImg").attr("src");
            $("#verifyCodeImg").attr("src", "");
            if (src.indexOf("&") >= 0) {
                src = src.substring(0, src.indexOf("&"));
            }
            $("#verifyCodeImg").attr("src", src += "&rd=" + new Date().getTime());
        }

        function hideErrorMes() {
            $("#null_username").hide();
            $("#null_password").hide();
            $("#null_verifyCode").hide();
        }

        function usernameClose() {
            $("#username").val('');
            $('#username_close').hide();
            $("#username").focus();
        }
        function passwordClose() {
            $("#password").val('');
            $('#password_close').hide();
            $("#password").focus();
        }

        function checkRoleType() {
            if ($("#first").attr("checked")) {
                var s = $("#first").val();
                $("#roleType").val(s);
            }
            if ($("#second").attr("checked")) {
                var s = $("#second").val();
                $("#roleType").val(s);
            }
            if ($("#ulp").attr("checked")) {
                var s = $("#ulp").val();
                $("#roleType").val(s);
            }
        }
        function username_onfocus() {
            if ($('#username').val() == '请输入统一工号或手机号码') {
                $('#username').val('');
                $('#username_close').hide();
            } else {
                if ($('#username').val() != '') {
                    $('#username_close').show();
                } else {
                    $('#username_close').hide();
                }
            }
        }
        function username_onblur() {
            if ($('#username').val() == '') {
                $('#username').val('请输入统一工号或手机号码');
                $('#username_close').hide();
            }
            if ($('#username').val() != '' && $('#username').val() != '请输入统一工号或手机号码') {
                $('#username_close').show();
            }
        }
        function username_close_onblur() {
            if ($('#usernames').val() == '') {
                $('#usernames').val('请输入统一工号或手机号码');
            }
        }

        function password_onfocus() {
            if ($('#password').val() != '') {
                $('#password_close').show();
            }
        }

        function password_onblur() {
            if ($('#password').val() == '') {
                $('#password_close').hide();
            }
        }
        //点击找回密码
        function findPassword(){
            if(provinceCode !="gx" && provinceCode !="nx" && provinceCode !="ah"){
                alert("暂时不支持密码找回功能");
                return;
            }
            emptyInput();
//         	$(".pop_win").show();
            showdiv("find_password_win");
        }
        function findPasswordCancel(){
//         	$(".pop_win").hide();
            hidediv("find_password_win");
        }
        function findPasswordSubmit(){
            var uniform_info_oper =$("#uniform_info_oper").val();
            var mobile_no =$("#mobile_no").val();
            var verifyCode =$("#verifyCode").val();
            var newPassword =$("#newPassword").val();
            var newPassword_2 =$("#newPassword_2").val();
            if(uniform_info_oper =="" || mobile_no ==""){
                alert("请输入统一工号或手机号码");
                return;
            }
            if(verifyCode =="" ){
                alert("请输入验证码");
                return;
            }
            if(newPassword =="" ){
                alert("请输入新密码");
                return;
            }
            if(newPassword_2 =="" ){
                alert("请输入确认新密码");
                return;
            }
            if(newPassword != newPassword_2){
                alert("密码确认不一致");
                return;
            }

            var data={
                "uniform_info_oper":uniform_info_oper,
                "mobile_no":mobile_no,
                "verify_code":verifyCode,
                "newPassword":newPassword
            };
            var url ='<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()%>'+'/uss_web/rest/codeTool/findPassword';
            $.ajax({
                url: url,
                data:data,
                type: "post",
                dataType: "json",
                success: function (message) {
//                 	console.info(message);
                    if(message.type =="error"){
                        alert(message.content);

                    }else{
                        alert("新密码设置成功");
                        findPasswordCancel();
                    }

                },
                error: function () {

                }
            });

        }
        function verifyCode(){
        	if(provinceCode =="ah"){
        		 alert("安徽暂无短信接口"); 
                 $("#verifyCode").attr("readonly",true).val("dic");
                 return;
        	}
 
            var uniform_info_oper =$("#uniform_info_oper").val();
            var mobile_no =$("#mobile_no").val();
            if(uniform_info_oper =="" || mobile_no ==""){
                alert("请输入统一工号或手机号码");
                return;
            }

            var data={
                "uniform_info_oper":uniform_info_oper,
                "mobile_no":mobile_no
            };
            var url ='<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()%>'+'/uss_web/rest/codeTool/generateVerifyCode';

            $.ajax({
                url: url,
                data:data,
                type: "post",
                dataType: "json",
                success: function (message) {
//                		console.info(message);
                    if(message.type =="error"){
                        alert(message.content);

                    }else{
                        alert("验证码已发送到"+mobile_no+"，请接收");
                    }

                },
                error: function () {

                }
            });
        }
        
        function emptyInput(){
        	  $("#uniform_info_oper").val("");
              $("#mobile_no").val("");
              $("#verifyCode").val("");
              $("#newPassword").val("");
              $("#newPassword_2").val("");
        }
        
        function showdiv(popWinId) {
            document.getElementById("bg_mask").style.display ='block';
            document.getElementById(popWinId).style.display ='block';

        }
        function hidediv(popWinId) {
            document.getElementById("bg_mask").style.display ='none';
            document.getElementById(popWinId).style.display ='none';
        }
        function queryAgentVerifyRelation(username){
        	var URL = '<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()%>' + '/uss_web/rest/agentVerifyRelation/queryAgentVerifyRelation';
        	var data1={
        			"uniform_info_oper":username
        			   };
        	$.ajax({
        		url:URL,
        		//type:'get',
        		type:'post',
        		data:data1,
        		waitMsg:"读取中..",
        		success:function(message){
        			var rlist=message.args.rlist;
        			var result=(message.type=="success"?true:false);
        			//查询成功
        			if(result){
        				if ( rlist== null || rlist == undefined ||rlist.length==0){
        					$.alert("无对应的手机号码绑定信息");					
        				}else{        					
        					for(var j = 0;j < rlist.length;j++){
        						
        		        		var data={  "mobile_no":rlist[j].bind_device_number };
        		            	var url ='<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()%>'+'/uss_web/rest/codeTool/generateVerifyCodeForLogin';

        		              	$.ajax({
        		                  url: url,
        		                  data:data,
        		                  type: "post",
        		                  dataType: "json",
        		                  success: function (message) {
        		                      if(message.type =="error"){
        		                          alert(message.content);
        		                      }else{
        		                          //alert("验证码已发送到"+mobile_no+"，请接收");
        		                      }
        		                  },
        		                  error: function () {

        		                  }
        		              	});
        					}	        	
        				}
        		    //查询失败
        			}else{
        				$.alert("查询失败:"+message.content);
        			}
        		},
        		error: function (XMLHttpRequest, textStatus,errorThrown) {
                    var status = XMLHttpRequest.status;
                    var hint = "网络繁忙，请稍后再试!";//通用万金油提示，都懂得。                
                    var error = hint + ",后台接口报错!";
                    $.alert(error);
                }
        	});	
        };
        //登录验证码
        function verifyCodeForLogin(){
          var username =$("#username").val().toUpperCase();
		  if(username == ""){
			  alert("请输入统一工号");
              return		  
		  }
		  
		  var url2 ='<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()%>'+'/uss_web/rest/oper/queryUniformInfoOperByUniformOper';
		 //判断是手机号登录还是统一工号
		 if(isNaN(username)){
			 var data2 ={
					 "uniform_info_oper":username		  
			  };
		 }else{
			 var data2 ={					  
					  "oper_device_number":username			
			  };
		 }
		 
		  
		  var mobile_no ="";
		  var bind_flag ="";
		  $.ajax({
              url: url2,
              data:data2,
              type: "post",
              dataType: "json",
              async:false,
              success: function (message) {
//              		console.info(message);
                  if(message.type =="success"){
                      mobile_no =message.args.Oper_device_number;
                      bind_flag=message.args.bind_flag;
                  }
              },
              error: function () {

              }
          });
         
          if(!mobile_no){
        	  alert("此工号无对应手机联系信息");
        	  return;
          }
          //代理商工号  群发短信到马仔
          if(bind_flag=='1'){        	 
        	  //查询代理商马仔电话,并发送短信
        	  queryAgentVerifyRelation(username);
        	 
          }
          
          var data={                 
                      "mobile_no":mobile_no,
                      "uniform_info_oper":$("#username").val().toUpperCase()
                  };
          var url ='<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()%>'+'/uss_web/rest/codeTool/generateVerifyCodeForLogin';

            $.ajax({
                url: url,
                data:data,
                type: "post",
                dataType: "json",
                success: function (message) {
//                		console.info(message);
                    if(message.type =="error"){
                        alert(message.content);

                    }else{
                    	if(bind_flag=='1'){        	 
        	  				alert("验证码已发送到"+mobile_no+"，及其绑定的其他号码，请接收");        	 
          				}else{
          					alert("验证码已发送到"+mobile_no+"，请接收");
          				}                        
                    }

                },
                error: function () {

                }
            });
        }
        //检验登录验证码
        function checkLoginVerifyCode(){
          var verifyCodeFlag =false;
          var verify_code =$("#login_verify_code").val();
          if(verify_code =="" || verify_code ==null){
        	  return false;
          }
          
          var url2 ='<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()%>'+'/uss_web/rest/codeTool/checkVerifyCodeForLogin';
   		  var data2 ={
   				  "verify_code":verify_code,
                  "uniform_info_oper":$("#username").val().toUpperCase()
   		  };
   		  $.ajax({
                 url: url2,
                 data:data2,
                 type: "post",
                 dataType: "json",
                 async:false,
                 success: function (message) {
//                 		console.info(message);
                     if(message.type =="success"){
                    	 verifyCodeFlag =true;
                     }else{
                    	 verifyCodeFlag =false;
                     }
                 },
                 error: function () {

                 }
             });
   		  
   		   return verifyCodeFlag;
        	
        }
        

    </script>
    <title>广西联通电子流智能营业厅</title>
</head>
<html>
<body>
<div class="logo">
</div>

<form:form method="post" id="loginForm" commandName="${commandName}"
           htmlEscape="true">
    <div class="login" ><!-- 需要验证码时，把login增加login_v2  2016-6-14 lgw -->
        <dl>
            <dt>登录方式：
            </dt>
            <dd>
                <div class="box">
                    <form:input
                            id="username" value="请输入统一工号或手机号码" class="text" onfocus="username_onfocus()" onblur="username_onblur()"
                            accesskey="${userNameAccessKey}" path="username" tabindex="1"
                            autocomplete="off" htmlEscape="true" onkeypress=""/>

                    <div class="close" style="display:none" id="username_close" onclick="usernameClose()" onblur="username_close_onblur()"></div>
                </div>

                    <%--  <form:errors style="display:none;" path="*" id="msg"
                        cssClass="errors" element="div" htmlEscape="false" /> --%>

            </dd>
        </dl>
        <dl>
            <dt>密　　码：</dt>
            <dd>
                <div class="box">
                    <form:password
                            id="password" path="password" class="text" onfocus="password_onfocus()"
                            onblur="password_onblur()"
                            accesskey="${passwordAccessKey}" htmlEscape="true" tabindex="2"
                            autocomplete="off"/>

                    <div class="close" style="display:none" id="password_close" onclick="passwordClose()"></div>


                </div>
                <div class="error" id="error1" style="display: none;">请输入统一工号或手机号码</div>

                <div class="error" id="error2" style="display: none;">请输入密码</div>

                <div class="error" id="error3" style="display: none;">工号输入错误，请重新输入</div>
                <div class="error" id="error4" style="display: none;">密码输入错误，请重新输入</div>
                <div class="error" id="error5" style="display: none;">验证码输入错误，请重新输入</div>
                <div class="error" id="err_password" style="display: none;">您的统一工号或密码有误或手机号码不唯一或您没权限</div>

            </dd>
        </dl>
		
        <!-- 需要验证码时，再变为可见 2016-6-14 lgw -->
        <dl style="display:none;" id="login_code_style">
            <dt>校&nbsp;&nbsp;验&nbsp;&nbsp;码：</dt>
            <dd>
                <div class="box_short">
                    <input id="login_verify_code" name="validate" tabindex="3" value="请输入校验码" class="text" onfocus="this.value=''" onblur="" type="text" value="" autocomplete="off"/>
                    <div class="close" style="display:none" onclick="" onblur=""></div>
                </div>
                <div class="btn_short">
                    <a href="###" onclick="verifyCodeForLogin();">获取手机验证码</a>
                </div>
            </dd>
        </dl>

        <form:input cssClass="required" cssErrorClass="error"
                    id="jsessionid" size="25" tabindex="3"
                    style="width: 62px;display:none;"
                    accesskey="${jsessionidAccessKey}" path="jsessionid"
                    autocomplete="off" htmlEscape="true"/>

        <!-- <div class="error" id="error1" style="display: none;">请输入统一工号</div>
        <div class="error" id="error2" style="display: none;">请输入密码</div>

        <div class="error" id="error3" style="display: none;">工号输入错误，请重新输入</div>
        <div class="error" id="error4" style="display: none;">密码输入错误，请重新输入</div>
         -->
        <form:input cssClass="required" cssErrorClass="error"
                    id="roleType"
                    style="display:none;"
                    path="roleType"
                    autocomplete="off" htmlEscape="true"/>


        <dl id="noyx">
            <dt class="sit">座席授权：</dt>
            <dd>
                <ul>
                    <li><input name="a" id="first" type="radio" value="2" class="radio"/><label for="first"><span> 共享座席</span> ( 授权人仅限营业厅经理、值班经理 )</label><span class="space16"></span><span class="space16"></span><span ><a href="javascript:findPassword()" style="font-weight:300; text-decoration:underline;">忘记密码？</a></span></li>
                    <li><input name="a" id="second" type="radio" value="1" checked="checked" class="radio"/><label for="second"><span> 营业座席</span> ( 由营业人员在营业前台使用 )</label></li>
                    <li style="display: none"><input name="a" id="ulp" type="radio" value="99" class="radio"/>ULP登陆</li>
                </ul>
            </dd>
        </dl>

        <div class="clear"></div>
        <div class="btn_login">

            <div class="btn">
                <input type="hidden" id="lt" name="lt" value="${loginTicket}"/> <input
                    type="hidden" name="execution" value="${flowExecutionKey}"/> <input
                    type="hidden" name="_eventId" value="submit"/>
                <a href="###" onclick="login()">登陆</a>
            </div>
        </div>
    </div>

    <!-- 弹出窗口 -->
    <div class="pop_win" id="find_password_win" style="display:none">
        <div class="msgbox">
            <div class="">
                <li><span class="text_large bold">密码找回：</span></li>
            </div>
            <div class="line_dashed_h" style="width:492px;"></div>
            <ul style="width:492px;">
                <li class="list" style="width:307px;">
                    <div class="left_lable text_large ">统<span class="space5"></span>一<span class="space5"></span>工<span class="space5"></span>号：</div>
                    <div class="left_data">
                        <input type="text" class="width_20 text_large yellow_bgcolor" id="uniform_info_oper" placeholder="统一工号"></input>
                    </div>
                </li>
                <li class="list" style="width:307px;">
                    <div class="left_lable text_large ">手<span class="space16"></span>机<span class="space16"></span>号：</div>
                    <div class="left_data">
                        <input type="text" class="width_20 text_large yellow_bgcolor" id="mobile_no" placeholder="手机号"></input>
                    </div>
                </li>
                <li class="list" style="width:308px;">
                    <div class="left_lable text_large ">验<span class="space16"></span>证<span class="space16"></span>码：</div>
                    <div class="left_data">
                        <input type="text" class="text_large yellow_bgcolor" style="width:115px" id="verifyCode" placeholder="验证码"></input>
                    </div>
                    <a href="javascript:verifyCode()"><div class="input_button">免费获取</div></a>
                </li>
                <li class="list" style="width:307px;">
                    <div class="left_lable text_large ">新<span class="space16"></span>密<span class="space16"></span>码：</div>
                    <div class="left_data">
                        <input type="password" class="width_20 text_large" id="newPassword" placeholder="新密码"></input>
                    </div>
                </li>
                <li class="list" style="width:307px;">
                    <div class="left_lable text_large ">新密码确认：</div>
                    <div class="left_data">
                        <input type="password" class="width_20 text_large" id="newPassword_2" placeholder="新密码确认"></input>
                    </div>
                </li>
            </ul>
            <div class="line_dashed_h" style="width:492px;"></div>
            <ul>
                <li class="center">
                    <a href="javascript:findPasswordSubmit()"><div class="input_button">提  交</div></a>
                    <a href="javascript:findPasswordCancel()"><div class="input_button">取  消</div></a>
                </li>
            </ul>
        </div>
    </div>


    <form:errors style="display:none;" path="*" id="msg"
                 cssClass="errors" element="div" htmlEscape="false"/>
</form:form>
<div class="bg_mask" id="bg_mask">
    <iframe class="bg_mask_iframe"> </iframe>
</div>

<!-- 需要验证码时，把copyright换成copyright_v2  2016-6-14 lgw -->
<div class="copyright">Copyright © 2014 China unicom All Right Reserved
</div>

</body>
</html>


