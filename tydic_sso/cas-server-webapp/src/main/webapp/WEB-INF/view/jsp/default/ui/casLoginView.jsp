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
<%@ page import="java.util.*"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    String fullPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
	String ulpFlag = request.getParameter("ulpFlag");
	String oper_no = request.getParameter("oper_no");
%>
<input type="hidden" id="ulpFlag" value="<%=ulpFlag %>"/>
<input type="hidden" id="oper_no" value="<%=oper_no %>"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>中国联通一体化服务平台</title>
	<link rel="stylesheet" type="text/css" href="css/public.css" />
    <link rel="stylesheet" type="text/css" href="css/login.css" />
    <script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="js/url_helper.js"></script>
    <script type="text/javascript">
        var getSessionId = '<%=GlobalConfig.newInstance().get("getSessionId")%>';
        var getVerifyCode = '<%=GlobalConfig.newInstance().get("getVerifyCode")%>';
        var provinceCode = '<%=GlobalConfig.newInstance().get("province_code")%>';
        $(function () {
            var myurl = new objURL();
            if (self != top && myurl.get('pwd') == undefined) {
                try{
                    window.parent.document.location.href = '<%=fullPath%>';
                }
                catch(e){}
            }
            initSessionId();
            
            $(document).keypress(function (e) {
                if (e.keyCode == 13)
                    login();
            });

            if (getCookies("tydic_uoc_username") != null) {
                $("#username").val(getCookies("tydic_uoc_username"));
            }
            
            /*清空按钮事件*/
        	$(".close").on('mousedown',function(){
        		var $this=$(this);
        		$this.prev().val("");
        	});
        	/*清空按钮事件的显示或隐藏*/
        	$("#username").focus(function(){
        		var $this=$(this);
        		$this.next().show();
        	});
        	$("#username").blur(function(){
        		var $this=$(this);
        		$this.next().hide();
        	});
        	$("#password").focus(function(){
        		var $this=$(this);
        		$this.next().show();
        	});
        	$("#password").blur(function(){
        		var $this=$(this);
        		$this.next().hide();
        	});
            
        	/*登录按钮事件*/
        	$("#loginBtn").on('click',function(){
				login();
        	});
        	
 			var msg_type = $("#msg").text();
			if (msg_type != "") {
				$('.info').text('您的工号或密码有误或您没权限');
				$('#info-bar').fadeIn();
            } else {
            	$('#info-bar').fadeOut();
            }
        });
        
        function login() {
        	if ($.trim($("#username").val()) == "") {
    			$('#info-bar').fadeIn();
    			$('.info').text('您还未输入您的工号');
    			return false;
    		}
    		if ($("#roleType").val()!="autoLogin"&&$.trim($("#password").val()) == "") {
    			$('#info-bar').fadeIn();
    			$('.info').text('请输入密码');
    			return false;
    		}
   			$('#info-bar').fadeOut();
    		
   			if ($("#roleType").val()!="autoLogin"){
    			$("#roleType").val("1");
   			}
    		
   			var username = $("#username").val().toUpperCase();
   			$("#username").val(username); // 工号转换成大写再提交到服务器
   			
   			if ($("#roleType").val()!="autoLogin"){
	   			addCookies("tydic_uoc_username", username, {
	                expires: 30
	            });
   			}else{
   			 	addCookies("tydic_uoc_autoLogin", username, {
	                expires: 120
	            });
   			}
           
   			$("#loginForm").submit();
        }

        function initSessionId() {
            $.ajax({
                url: getSessionId,
                type: "post",
                dataType: "json",
                crossDomain: true == !(document.all),
                success: function (message) {
                    $("#jsessionid").val(message.args.jsessionId);
                   	var ulpFlag = $('#ulpFlag').val();
                   	var oper_no = $('#oper_no').val().toUpperCase();
                    //获取标志，自动登录
                    if(ulpFlag==1){
                    	if($("#msg").text()==""){
			            	$("#username").val(oper_no);
			                $("#roleType").val("autoLogin");
			                
			                addCookies("tydic_uoc_autoLogin", oper_no, {
				                expires: 120
				            });
			                
					        $("#loginForm").submit();
            			}else{
            				$('.info').text('该工号不能登入中台系统，请重新输入工号');
            				$("#roleType").val("autoLogin");
            				delCookie("tydic_uoc_autoLogin");
            			}
                    }
                },
                error: function (message) {
                	
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
    </script>
</head>
<body>
<div class="header">
	<div class="logo">
		<p class="logo-img"><img src="images/login/logo.png"/></p>
		<div class="logo-txt">
			<h2>中国联通一体化服务平台</h2>
			<div>Unified Service Platform</div>
		</div>
	</div>
</div>
<div class="section">
	<div class="main">
		<form:form method="post" id="loginForm" commandName="${commandName}" htmlEscape="true">
			<div class="login">
				<div class="form-group">
					<h1>用户登录</h1>
				</div>			
				<div class="form-group">
					<i class="icon icon1-position account-icon"></i>
					<form:input class="account" id="username" placeholder="您的工号/手机号"
                            accesskey="${userNameAccessKey}" path="username" tabindex="1"
                            autocomplete="off" htmlEscape="true"/>
					<i class="icon icon2-position close"></i>
				</div>
				<div class="form-group">
					<i class="icon icon1-position pwd-icon"></i>
					<form:password class="pwd" id="password" placeholder="请输入密码"
                            accesskey="${passwordAccessKey}" path="password" tabindex="2"
                            autocomplete="off" htmlEscape="true"/>
					<i class="icon icon2-position close"></i>
				</div>		
				<div class="form-group">
					<form:errors style="display:none;" path="*" id="msg"
							cssClass="errors" element="div" htmlEscape="false"/>
					<form:input id="jsessionid" size="25" style="width: 62px;display:none;" 
		                    cssClass="required" cssErrorClass="error"
		                    accesskey="${jsessionidAccessKey}" path="jsessionid" tabindex="3"
		                    autocomplete="off" htmlEscape="true"/>
                    <form:input id="roleType" style="display:none;" 
		                    cssClass="required" cssErrorClass="error" path="roleType"
		                    autocomplete="off" htmlEscape="true"/>
                    <input type="hidden" id="lt" name="lt" value="${loginTicket}"/>
                    <input type="hidden" name="execution" value="${flowExecutionKey}"/>
                    <input type="hidden" name="_eventId" value="submit"/>
					<input type="button" class="login-btn" id="loginBtn" value="登录"/>
					<p class="onekey-package">
						<a href="http://135.24.252.60:9094/setup.exe">一键设置安装包</a>
					</p>
				</div>	
				<div class="form-group" id="info-bar" style="display:none;">
					<span class="info" id="info"></span>
				</div>
			</div>
		</form:form>
	</div>
</div>
<div class="footer">
	<div>Copyright © 2016 China unicom All Right Reserved</div>
</div>
</body>
</html>


