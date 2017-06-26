<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tydic.unicom.util.DateUtil"%>
<%@ taglib prefix="t" uri="http://tydic.com/jsp/core"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>我的主页</title>

<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/home.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/home.js"></script>
<!--[if lt IE 9]>
    <script type="text/javascript" src="<%=fullPath%>js/json2.js"></script>
<![endif]-->

</head>
<body>
	<div class="show" style="height:auto;">
		<div class="padding_blank10"></div>
		<div class="container">
			<div class="top_area">
				<span class="top_sign" id="device_type" style="visibility:hidden;">2G</span>
				<div class="top_search">
					<input type="text" placeholder="请输入业务号码搜索常用功能" id="input_number"/>
					<img src="<%=fullPath%>images/home_search.png" width="24" height="24" id="btn_search"/>
				</div>
				<div class="top_list">
					<div class="top_arrow float_left">
						<i class="arrow_left" id="btn_left"></i>
					</div>
					<div class="top_options float_left" id="dtjzlb">
						<!-- 动态加载 -->
						<div class="top_option" id="top_detail_1" style="background:url(<%=fullPath%>images/home_3g_change.png) no-repeat left top;">
							<div class="top_text">3G套餐变更</div>
						</div>
						<div class="top_option" id="top_detail_2" style="background:url(<%=fullPath%>images/home_supply_card.png) no-repeat left top;">
							<div class="top_text">补卡</div>
						</div>
						<div class="top_option" id="top_detail_3" style="background:url(<%=fullPath%>images/home_replace_card.png) no-repeat left top;">
							<div class="top_text">换卡</div>
						</div>
						<div class="top_option" id="top_detail_4" style="background:url(<%=fullPath%>images/home_roam_change.png) no-repeat left top;">
							<div class="top_text">变更漫游</div>
						</div>
						<div class="top_option" id="top_detail_5" style="background:url(<%=fullPath%>images/home_product_change.png) no-repeat left top;">
							<div class="top_text">产品变更</div>
						</div>
						<div class="top_option" id="top_detail_6" style="background:url(<%=fullPath%>images/home_product_change.png) no-repeat left top;">
							<div class="top_text">test1（测试两行内容）</div>
						</div>
						<div class="top_option" id="top_detail_7" style="background:url(<%=fullPath%>images/home_product_change.png) no-repeat left top;">
							<div class="top_text">test2</div>
						</div>
					</div>
					<div class="top_arrow float_right">
						<i class="arrow_right" id="btn_right"></i>
					</div>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="quick_area">
				<div class="business_head">
					<i class="quick_accept_icon"></i>
					<span>快速受理通道</span>
				</div>
				<div class="business_body">
					<div class="quick_options" id="kssltd">
						<!-- 动态加载 -->
						<div class="quick_option" id="" style="background:url(<%=fullPath%>images/home_phone_treaty_down.png) no-repeat left top;">
							<div class="hot_icon"><img src="<%=fullPath%>images/home_hot.png" width="42" height="26"/></div>
							<div class="quick_text">明星手机合约直降</div>
						</div>
						<div class="quick_option" id="" style="background:url(<%=fullPath%>images/home_one_year_free.png) no-repeat left top;">
							<div class="hot_icon"><img src="<%=fullPath%>images/home_hot.png" width="42" height="26"/></div>
							<div class="quick_text">一年电话免费打</div>
						</div>
						<div class="quick_option" id="" style="background:url(<%=fullPath%>images/home_deposit_gift_treaty.png) no-repeat left top;">
							<div class="hot_icon"></div>
							<div class="quick_text">99元本地存费送机合约</div>
						</div>
						<div class="quick_option" id="" style="background:url(<%=fullPath%>images/home_buy_down.png) no-repeat left top;">
							<div class="hot_icon"></div>
							<div class="quick_text">购机直降</div>
						</div>
						<div class="quick_option" id="" style="background:url(<%=fullPath%>images/home_clear_treaty.png) no-repeat left top;">
							<div class="hot_icon"></div>
							<div class="quick_text">清库机合约</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="history_area float_left">
				<div class="business_head">
					<i class="business_history_icon"></i>
					<span>常用业务</span>
				</div>
				<div class="business_body">
					<div class="history_options" id="cywyls">
						<!-- 动态加载 -->
						<div class="history_option" id="">
							<div class="history_text">统一开户</div>
						</div>
						<div class="history_option" id="">
							<div class="history_text">统一订单处理</div>
						</div>
						<div class="history_option" id="">
							<div class="history_text">改账户定制关系</div>
						</div>
						<div class="history_option" id="">
							<div class="history_text">拆机</div>
						</div>
					</div>
				</div>
			</div>
			<div class="jump_area float_right" style="max-height:202px;">
				<div class="business_head">
					<a href="javascript:void(0);">更多&gt;&gt;</a>
					<i class="jump_page_icon"></i>
					<span>系统快捷入口</span>
				</div>
				<div class="business_body">
					<div class="jump_options">
						<a href="javascript:void(0);" onclick="authorizeDialog('CBSS');">
							<div class="jump_option" id="">
								<div class="jump_text">C</div>
								<div class="jump_text_full">CBSS</div>
							</div>
						</a>
						<a href="javascript:void(0);" onclick="">
							<div class="jump_option" id="">
								<div class="jump_text">B</div>
								<div class="jump_text_full">BSS</div>
							</div>
						</a>
						<a href="javascript:void(0);" onclick="">
							<div class="jump_option" id="">
								<div class="jump_text">E</div>
								<div class="jump_text_full">ESS</div>
							</div>
						</a>
					</div>
				</div>
				<!-- 弹出窗口 授权登录： -->
				<div class="pop_win" id="authorize_login" style="width:350px; position:relative; top:-155px; left:15px; display:none;">
					<div class="msgbox" style="min-height:auto;">
						<a href="javascript:hidediv('authorize_login');">
							<div class="msgbox_close"></div>
						</a>
						<ul class="text_big" style="padding:15px 0px 0px 0px;">
							<li>
								<span class="bold">授权登录</span>
								<span class="bold" id="sysCode"></span>
							</li>
						</ul>
						<div class="line_dashed_h"></div>
						<div class="text_center" id="inputArea" style="padding:10px 0px;">
							<span>登录密码：</span>
							<input type="password" id="loginPwd" placeholder="请输入登录密码" style="padding:2px;"/>
						</div>
						<ul style="padding:5px 0px 15px 0px;">
							<li class="center">
								<a href="javascript:void(0)" id="confirmBtn">
									<div class="input_button">确 定</div>
								</a>
								<a href="javascript:void(0)" id="modifyPwdBtn" style="display:none;">修改密码</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="padding_blank"></div>
	</div>

	<div class="bg_mask" id="bg_mask">
		<iframe class="bg_mask_iframe"> </iframe>
	</div>
	
	<input type="hidden" id="img_dir" value="<%=fullPath%>images/"/>
	<input type="hidden" id="oper_no" value="${oper_no}"/>
	<input type="hidden" id="operNo" value=""/>
	<input type="hidden" id="password" value=""/>
	
	<div class="copyright"> Copyright © 2014 China unicom All Right Reserved</div>
</body>
<script type="text/javascript">
	function showdiv(popWinId) {
		if (!window.XMLHttpRequest) { 
			for(var i=0;i<selects.length;i++){
				if(selects[i].style.display!='none'){
					selects_display.push(selects[i]);
					selects[i].style.display = "none";
				};
			}
		}
		document.getElementById("bg_mask").style.display ='block';
		document.getElementById(popWinId).style.display ='block';
	}
	function hidediv(popWinId) {
		if (!window.XMLHttpRequest) { 
			for(var i=0;i<selects_display.length;i++){
				selects_display[i].style.display = "";
			}
		}
		document.getElementById("bg_mask").style.display ='none';
		document.getElementById(popWinId).style.display ='none';
	}
	
	// 弹出授权登录窗口
	function authorizeDialog(sysCode) {
		var operNo = $('#operNo').val();
		var password = $('#password').val();
		if (password == null || password == '') {
			$('#inputArea').show();
			$('#modifyPwdBtn').hide();
		} else {
			$('#inputArea').hide();
			$('#modifyPwdBtn').show();
		}
		$('#sysCode').text('(' + sysCode + ')');
		showdiv('authorize_login');
		
		// 授权登录 确定
		$('#confirmBtn').unbind();
		$('#confirmBtn').bind('click', function() {
			// 修改授权登录密码
			if ($('#loginPwd').val() != '') {
				password = $('#loginPwd').val();
				$.ajax({
					type: 'post',
					url: '/changePwd.action',
					data: {
						'method': 'saveSystemPwd',
						'newPasswd': password,
						'system': sysCode
					},
					dataType: 'json',
					success: function(message) {
						console.log('success: ' + message);
					},
					error: function(message) {
						//console.log('error: ' + message);
					}
				});
			}
			window.open("https://10.1.1.78/essframe?service=page/LoginProxy&login_type=redirectLogin&STAFF_ID=" + operNo + "&LOGIN_PASSWORD=" + password + "&LOGIN_PROVINCE_CODE=83&service=direct%2F1%2FHome%2F%24Form &sp=S0&Form0 =LOGIN_PROVINCE_REDIRECT_URL%2CAUTH_TYPE%2CCAPTURE_URL%2CWHITE_LIST_LOGIN%2CIPASS_LOGIN%2CIPASS_SERVICE_URL%2CIPASS_LOGIN_PROVINCE%2CIPASS_LOGINOUT_DOMAIN%2CSIGNATURE_CODE%2CSIGNATURE_DATA%2CIPASS_ACTIVATE%2CSTAFF_ID%2C%24FormConditional%2C%24FormConditional%240%2CLOGIN_PROVINCE_CODE%2C%24FormConditional%241%2C%24FormConditional%242%2C%24FormConditional%243%2C%24FormConditional%244%2C%24FormConditional%245%2C%24TextField%2C%24TextField%240%2C%24TextField%241%2C%24TextField%242%2C%24TextField%243%2C%24TextField%244%2C%24TextField%245%2C%24TextField%246%2C%24TextField%247%2C%24TextField%248%2C%24TextField%249%2C%24TextField%2410%2C%24TextField%2411%2C%24TextField%2412%2C%24TextField%2413%2C%24TextField%2414%2C%24TextField%2415%2C%24TextField%2416%2C%24TextField%2417%2C%24TextField%2418%2C%24TextField%2419%2C%24TextField%2420%2C%24TextField%2421%2C%24TextField%2422%2C%24TextField%2423%2C%24TextField%2424%2C%24TextField%2425%2C%24TextField%2426%2C%24TextField%2427%2C%24TextField%2428%2C%24TextField%2429%2C%24TextField%2430&%24FormConditional=F&%24FormConditional%240=T&%24FormConditional%241=T&%24FormConditional%242=T&%24FormConditional%243=F&%24FormConditional%244=F&%24FormConditional%245=F &LOGIN_PROVINCE_REDIRECT_URL=&AUTH _TYPE=0&CAPTURE_URL=%2Fimage%3Fmode%3Dvalidate%26width%3D60%26height%3D20 &WHITE_LIST_LOGIN=&IPASS _LOGIN=&IPASS_SERVICE_URL= &IPASS_LOGIN_PROVINCE=&IPASS _LOGINOUT_DOMAIN=&SIGNATURE_CODE= &SIGNATURE_DATA=&IPASS _ACTIVATE=");
		});
		// 授权登录 密码修改框
		$('#modifyPwdBtn').unbind();
		$('#modifyPwdBtn').bind('click', function() {
			$('#inputArea').show();
			$('#modifyPwdBtn').hide();
		});
	}
</script>
</html>