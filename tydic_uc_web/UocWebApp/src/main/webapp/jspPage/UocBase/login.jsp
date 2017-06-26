<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<title>中国联通一体化服务平台</title>
	<link rel="stylesheet" type="text/css" href="../../style/css/public.css" />
    <link rel="stylesheet" type="text/css" href="../../style/css/login.css" />
    <script type="text/javascript" src="../../js/common/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../../js/common/WebUtil.js"></script>
    <script type="text/javascript" src="../../js/common/public.js"></script>
    <script type="text/javascript" src="../../js/UocBaseJs/login.js"></script>
</head>

<!-- 现在用云销售的CAS认证，此登录页面目前未使用 -->
<body>
<header>
	<div class="logo">
		<p class="logo-img"><img src="../../style/images/icon/logo.png"/></p>
		<div class="logo-txt">
			<h2>中国联通一体化服务平台</h2>
			<div>Uified Service Platform</div>
		</div>
	</div>
</header>
<section>
	<div class="main">
		<div class="login">
			<div class="form-group">
				<h1>用户登录</h1>
			</div>			
			<div class="form-group">
				<i class="icon icon1-position account-icon"></i>
				<input type="text" class="account" id="account" placeholder="您的工号/手机号"/>
				<i class="icon icon2-position close"></i>
			</div>
			<div class="form-group">
				<i class="icon icon1-position pwd-icon"></i>
				<input type="password" class="pwd" id="pwd" placeholder="请输入密码"/>
				<i class="icon icon2-position close"></i>
			</div>		
			<div class="form-group">
				<input type="submit" class="login-btn" id="loginBtn" value="登录"/>
			</div>	
			<div class="form-group" id="info-bar" style="display:none;">
				<span class="info" id="info">您还未输入您的帐号</span>
			</div>          
		</div>
	</div>
</section>
<footer>
	<div>Copyright © 2016 China unicom All Right Reserved</div>
</footer>
</body>
</html>