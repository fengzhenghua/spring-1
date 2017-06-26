<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript" src="./js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="./js/public.js"></script>
<link rel="stylesheet" type="text/css" href="./css/common/public.css" />
<link rel="stylesheet" type="text/css" href="./css/common/common.css" />
<link rel="stylesheet" type="text/css" href="./css/pc/crawlPage.css" />
<script type="text/javascript" src="./js/index.js"></script>
<!-- <script type="text/javascript" src="./js/main.js"></script> -->
<%@ include file="include.jsp"%>
<script>

	$(document).ready(
			function() {
				$.loading.show('等待系统初始化!');
				
						$.ajax({
									url : BASEURL
											+ '/rest/getInfoServiceRest/crawlergetusernamemethod',
									crossDomain : true == !(document.all),
									type : 'POST', //GET
									async : true, //或false,是否异步
									data : {
										json_info : "{\"jsession_id\":\""
												+ username + "\"}"
									},
									timeout : TIMEOUT, //超时时间
									dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
									beforeSend : function(xhr) {
										console.log(xhr);
										console.log('发送前');
										//等待查询信息
									},
									success : function(data, textStatus, jqXHR) {
										if (data.content != null) {
											console.log('检查用户');
											$('#login_user').val(data.content);
											checklogin();
										} else {
											showerr(data);
										}

									},
									error : function(xhr, textStatus) {
										console.log('错误')
										console.log(xhr);
									},
									complete : function() {
										console.log('结束')
										$.loading.hide();
									}
								});
					}
	);
	//检查是否session正确，如果正确跳过登录，如果不正确显示登录
	function checklogin() {
		if ($('#login_safecode').val() != '') {
			login();
			return;
		}

		$.ajax({
			crossDomain : true == !(document.all),
			url : BASEURL + '/rest/loginServiceRest/isLogin',
			type : 'POST', //GET
			async : true, //或false,是否异步
			data : {
				json_info : '{"user":"' + $('#login_user').val() + '","pwd":"'
						+ $('#login_password').val() + '"}'
			},
			timeout : TIMEOUT, //超时时间
			dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
			beforeSend : function(xhr) {
				console.log(xhr)
				console.log('发送前')
			},
			success : function(data, textStatus, jqXHR) {
				showinfo(data);
				if (data.respCode == '0000' || data.respCode == '0') {
					hidelogin();
					showmain();

				} else {
					$('#logindiv').show();
				}
			},
			error : function(xhr, textStatus) {
				console.log('错误');
				console.log(xhr);
				console.log(textStatus);
				$('#logindiv').show();
			},
			complete : function() {
				console.log('结束');
			}
		});
	};

	//登录商城网站
	function login() {
		$.ajax({
			crossDomain : true == !(document.all),
			url : BASEURL + '/rest/loginServiceRest/login',
			type : 'POST', //GET
			async : true, //或false,是否异步
			data : {
				json_info : '{"user":"' + $('#login_user').val() + '","pwd":"'
						+ $('#login_password').val() + '","safecode":"'
						+ $('#login_safecode').val() + '"}'
			},
			timeout : TIMEOUT, //超时时间
			dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
			beforeSend : function(xhr) {
				console.log(xhr)
				console.log('发送前')
			},
			success : function(data, textStatus, jqXHR) {
				showinfo(data);
				if (data.respCode == '0') {
					hidelogin();
					showmain();
				}
			},
			error : function(xhr, textStatus) {
				console.log('错误')
				console.log(xhr)
				console.log(textStatus)
			},
			complete : function() {
				console.log('结束');
				$('#login_safecode').val('');
			}
		});
	};

	//获取验证码
	function getsafecode() {
		$.ajax({
			crossDomain : true == !(document.all),
			url : BASEURL + '/rest/getInfoServiceRest/getSafeCode',
			type : 'POST', //GET
			async : true, //或false,是否异步
			data : {
				json_info : '{"user":"' + $('#login_user').val()
						+ '","type":"1"}'
			},
			timeout : TIMEOUT, //超时时间
			dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
			beforeSend : function(xhr) {
				console.log(xhr)
				console.log('发送前')
			},
			success : function(data, textStatus, jqXHR) {
				showinfo(data);
			},
			error : function(xhr, textStatus) {
				console.log('错误')
				console.log(xhr)
				console.log(textStatus)
			},
			complete : function() {
				console.log('结束')
			}
		});
	};

	//抓取订单
	function manualaccout(obj) {
		console.log(obj.disabled);
		$.loading.show('请等待获取数据中...');
		if (obj.disabled) {
			return;
		}

		$.ajax({
			crossDomain : true == !(document.all),
			url : BASEURL + '/rest/crawlerServiceRest/crawlerManualAccount',
			type : 'POST', //GET
			async : true, //或false,是否异步
			data : {
				json_info : '{"user":"' + $('#login_user').val() + '","pwd":"'
						+ $('#login_password').val() + '"}'
			},
			timeout : TIMEOUT + 100000, //超时时间
			dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
			beforeSend : function(xhr) {
				console.log(xhr)
				console.log('获取数据发送前');
				obj.disabled = true;
				$('#crwalsize').text('--');
				$('#crwalOK').text('--');
				$('#crawlNO').text('--');
			},
			success : function(data, textStatus, jqXHR) {
				console.log(data);
				$('#crwalsize').text(data.args.crwalsize);
				$('#crwalOK').text(data.args.crwalOK);
				$('#crawlNO').text(data.args.crawlNO);
				obj.disabled = false;
			},
			error : function(xhr, textStatus) {
				console.log('错误')
				console.log(xhr)
				console.log(textStatus)
				obj.disabled = false;
				$.loading.hide();
			},
			complete : function() {
				obj.disabled = false;
				console.log('结束');
				$('#crawlertime').text(getNowFormatDate());
				$.loading.hide();
				manualaccoutCraeteOrder(null);
			}
		});
	};

	//创建订单
	function manualaccoutCraeteOrder(obj) {
		if (obj == null) {
			obj = $('#btnmanualaccoutCraeteOrder');
		}
		$.loading.show('请等待生成订单中...');
		if (obj.disabled) {
			return;
		}
		$
				.ajax({
					crossDomain : true == !(document.all),
					url : BASEURL
							+ '/rest/crawlerServiceRest/crawlerManualAccountCreateOrder',
					type : 'POST', //GET
					async : true, //或false,是否异步
					data : {
						json_info : '{"user":"' + $('#login_user').val()
								+ '","pwd":"' + $('#login_password').val()
								+ '"}'
					},
					timeout : TIMEOUT + 100000, //超时时间
					dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
					beforeSend : function(xhr) {
						console.log(xhr)
						console.log('创建订单发送前');
						$('#cratouoccount').text('--');
						$('#cratouoccountok').text('--');
						$('#cratouoccountno').text('--');
						obj.disabled = true;
					},
					success : function(data, textStatus, jqXHR) {
						console.log(data);
						$('#cratouoccount').text(data.args.cratouoccount);
						$('#cratouoccountok').text(data.args.cratouoccountok);
						$('#cratouoccountno').text(data.args.cratouoccountno);
						loadgriddata(data);
						obj.disabled = false;
					},
					error : function(xhr, textStatus) {
						console.log('错误')
						console.log(xhr)
						console.log(textStatus)
						obj.disabled = false;
					},
					complete : function() {
						obj.disabled = false;
						console.log('结束');
						$.loading.hide();
					}
				});

	};

	//回写卡数据
	function writeBackManualAccountMethod(obj) {
		if (obj.disabled) {
			return;
		}
		$
				.ajax({
					crossDomain : true == !(document.all),
					url : BASEURL
							+ '/rest/writeBackServiceRest/writeBackManualAccount',
					type : 'POST', //GET
					async : true, //或false,是否异步
					data : {
						json_info : '{"user":"' + $('#login_user').val()
								+ '","pwd":"' + $('#login_password').val()
								+ '"}'
					},
					timeout : TIMEOUT + 100000, //超时时间
					dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
					beforeSend : function(xhr) {
						console.log(xhr)
						console.log('创建订单发送前');
						obj.disabled = true;
						$('#writeMallICCount').text('--');
						$('#writeMallICOK').text('--');
						$('#writeMallICNO').text('--');
					},
					success : function(data, textStatus, jqXHR) {
						console.log(data);
						console.log(data.args.crwalsize);
						console.log(data.args.crwalOK);
						console.log(data.args.crawlNO);
						$('#writeMallICCount').text(data.args.writeMallICCount);
						$('#writeMallICOK').text(data.args.writeMallICOK);
						$('#writeMallICNO').text(data.args.writeMallICNO);
						obj.disabled = false;

					},
					error : function(xhr, textStatus) {
						console.log('错误')
						console.log(xhr)
						console.log(textStatus)
						obj.disabled = false;
					},
					complete : function() {
						console.log('结束')
						obj.disabled = false;
					}
				});
	};

	//回写物流数据
	function writeBackExpress(obj) {
		if (obj.disabled) {
			return;
		}
		$.ajax({
			crossDomain : true == !(document.all),
			url : BASEURL + '/rest/writeBackServiceRest/writeBackExpress',
			type : 'POST', //GET
			async : true, //或false,是否异步
			data : {
				json_info : '{"user":"' + $('#login_user').val() + '","pwd":"'
						+ $('#login_password').val() + '"}'
			},
			timeout : TIMEOUT + 100000, //超时时间
			dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
			beforeSend : function(xhr) {
				console.log(xhr)
				console.log('创建订单发送前');
				$('#craToMallExpressCount').text('--');
				$('#craToMallExpressOk').text('--');
				$('#craToMallExpressNo').text('--');
				obj.disabled = true;
			},
			success : function(data, textStatus, jqXHR) {
				console.log(data);
				console.log(data.args.crwalsize);
				console.log(data.args.crwalOK);
				console.log(data.args.crawlNO);
				$('#craToMallExpressCount').text(data.args.writeMallICCount);
				$('#craToMallExpressOk').text(data.args.writeMallICOK);
				$('#craToMallExpressNo').text(data.args.writeMallICNO);
				obj.disabled = false;

			},
			error : function(xhr, textStatus) {
				console.log('错误')
				console.log(xhr)
				console.log(textStatus)
				obj.disabled = false;
			},
			complete : function() {
				console.log('结束')
				obj.disabled = false;
			}
		});
	};

	//爬取信息查询
	function getOrderInfoList(obj) {
		$.loading.show('请等待查询数据中...');
		if (obj.disabled) {
			return;
		}
		$.ajax({
			crossDomain : true == !(document.all),
			url : BASEURL + '/rest/getInfoServiceRest/getOrderInfoList',
			type : 'POST', //GET
			async : true, //或false,是否异步
			data : {
				json_info : '{"ci_uuid":"' + $('#getorderinfolistinput').val()
						+ '","user":"' + $('#login_user').val()
						+ '","order_status":"'+ $('#getorderinfolistselect').val() + '"}'
			},
			timeout : TIMEOUT + 100000, //超时时间
			dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
			beforeSend : function(xhr) {
				console.log(xhr)
				console.log('创建订单发送前');
				obj.disabled = true;
			},
			success : function(data, textStatus, jqXHR) {
				console.log(data);
				obj.disabled = false;
				loadgriddata(data);
			},
			error : function(xhr, textStatus) {
				console.log('错误')
				console.log(xhr)
				console.log(textStatus)
				obj.disabled = false;
			},
			complete : function() {
				console.log('结束');
				$.loading.hide();
				getstatuscount();
				obj.disabled = false;
			}
		});
	}

	function getstatuscount(){
		$.ajax({
			crossDomain : true == !(document.all),
			url : BASEURL + '/rest/getInfoServiceRest/getCountStatus',
			type : 'POST', //GET
			async : true, //或false,是否异步
			data : {
				json_info : '{"user":"' + $('#login_user').val()+ '"}'
			},
			timeout : TIMEOUT + 10000, //超时时间
			dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
			beforeSend : function(xhr) {
				console.log(xhr)
				console.log('创建订单发送前');
			},
			success : function(data, textStatus, jqXHR) {
				console.log(data);
				showstatuscount(data);
			},
			error : function(xhr, textStatus) {
				console.log('错误')
				console.log(xhr)
				console.log(textStatus)
			},
			complete : function() {
				console.log('结束');
				$.loading.hide();
			}
		});
	}	
	
	function hidelogin() {
		$('#logindiv').hide();
	}

	function showmain() {
		$('#maindiv').show();
		//if($("#maindiv").is(":hidden")){
		getstatuscount();
	}

	function showinfo(json) {
		$('#logindiv #login-info').text(json.content);
		$('#logindiv #login-info').show();
	}
	function showerr(json) {
		$('#diverr #diverrshow').text(json.content);
		$('#diverr').show();
	}
	//readonly

	function getNowFormatDate() {
		var date = new Date();
		var seperator1 = "-";
		var seperator2 = ":";
		var month = date.getMonth() + 1;
		var strDate = date.getDate();
		if (month >= 1 && month <= 9) {
			month = "0" + month;
		}
		if (strDate >= 0 && strDate <= 9) {
			strDate = "0" + strDate;
		}
		var currentdate = date.getFullYear() + seperator1 + month + seperator1
				+ strDate + " " + date.getHours() + seperator2
				+ date.getMinutes() + seperator2 + date.getSeconds();
		return currentdate;
	}

	var datalist = {};
	function loadgriddata(data) {
		//console.log(data.args.listjson);
		//myGrid.parse(data.args.listjson,"js");
		$("#info_list_body").html('');
		if (data.args.listjson == "") {
			return;
		}
		var resjson = data.args.listjson;
		if(resjson==null){
			$.message.success("未找到订单信息!");
			return;
		}
		if (typeof(resjson.data) == "undefined"){ 
			var resjson = eval('(' + data.args.listjson + ')');
		}
		if (typeof(resjson.data) == "undefined"){ 
			$.message.success("未找到需要处理的订单!");
			return;
		}
		console.log(resjson.data);
		
		resjson["head"] = [ {
			"id" : "stateimgs",
			"width" : "45",
			"type" : "img",
			"align" : "right",
			"value" : ""
		}, {
			"id" : "",
			"width" : "35",
			"type" : "ch",
			"align" : "right",
			"value" : "#master_checkbox"
		}, {
			"id" : "uid",
			"width" : "200",
			"type" : "ro",
			"align" : "right",
			"value" : "订单号"
		}, {
			"id" : "serial_number",
			"width" : "100",
			"type" : "ro",
			"align" : "right",
			"value" : "业务号码"
		}, {
			"id" : "product",
			"width" : "150",
			"type" : "ro",
			"align" : "right",
			"value" : "订单类型"
		}, {
			"id" : "customer_name",
			"width" : "80",
			"type" : "ro",
			"align" : "right",
			"value" : "开户名"
		}, {
			"id" : "saleorderno",
			"width" : "180",
			"type" : "ro",
			"align" : "right",
			"value" : "销售订单号"
		}, {
			"id" : "servorderno",
			"width" : "180",
			"type" : "ro",
			"align" : "right",
			"value" : "服务订单号"
		}, {
			"id" : "statecon",
			"width" : "600",
			"type" : "ro",
			"align" : "left",
			"value" : "状态"
		}, ];

		//myGrid.parse(resjson,"js");
		console.log(resjson)
		for(i = 0;i< resjson.data.length;i++){
			addList('#info_list_body',resjson.data[i]);
		}
		
	}

	function addList(className,item) {
		var html = '';
		var imgcon='';
		if(getstr(item.uid) != null ){
			if(getstr(item.stateimgs)==""){
				imgcon = '';
			}else{
				imgcon='<img src="./css/imgs/'+getstr(item.stateimgs)+'" width="20" height="20"></img>';
			}
		html += '<tr id="'+item.orderid+'"><td class="td1">'+imgcon
				+'</td>'
				/* + '<td class="td2 text_alignl">'+getstr(item.orderid)+'</td>' */
				+ '<td class="td2 text_alignl"><a class="href" href="/crawler_rest/toDetail.jsp?ciuuid='+item.ciuuid+'&username='+username+'" target="_blank"></a>'+getstr(item.orderid)+'</td>'
				+ '<td class="td3 text_alignl">'+getstr(item.product)+'</td>'
				+ '<td class="td4 text_alignc">'+getstr(item.serial_number)+'</td>'
				+ '<td class="td5 text_alignc">'+getstr(item.customer_name)+'</td>'
				+ '<td class="td6 text_alignc">'+getstr(item.saleorderno)+'</td>' 
				/* + '<td class="td7 text_alignc" onclick="winopentest(\''+getstr(item.servorderno)+'\')"><span class="href cursor"  label="订单查询" url="jspPage/UocBase/orderDetail.jsp" menu_id=""></span>'+getstr(item.servorderno)+'</td>' */
				+ '<td class="td7 text_alignc" >'+getstr(item.servorderno)+'</td>'
				
				+ '<td class="td6 text_alignc">'+getstr(item.cbssnum)+'</td>' 
				+ '<td class="td6 text_alignc">'+getstr(item.usim)+'</td>' 
				+ '<td class="td6 text_alignc">'+getstr(item.logistics_no)+'</td>' 
				
				+ '<td class="td7 text_alignl">'+getstr(item.statecon)+'</td>'
				+ '</tr>';
		$(className).append(html);
	}	
	}
	
	function getstr(obj){
		if (typeof(obj) == "undefined"){
			return "&nbsp;"
		}else{
			return obj;
		}
	}
	
	function showstatuscount(data){
		if (data.args.listjson == "") {
			return;
		}
		var resjson = data.args.listjson;
		if(resjson==null){
			return;
		}
		if (typeof(resjson) == "undefined"){ 
			var resjson = eval('(' + data.args.listjson + ')');
		}
		$('#scddcount').text('0');
		$('#tbztkcount').text('0');
		$('#tbztwcount').text('0');
		if (typeof(resjson.S0) != "undefined"){ 
			$('#scddcount').text(resjson.S0);
		}
		if (typeof(resjson.S430) != "undefined"){ 
			$('#tbztkcount').text(resjson.S430);
		}
		if (typeof(resjson.S530) != "undefined"){ 
			$('#tbztwcount').text(resjson.S530);
		}
		
	}
	
	
	function winopentest(order_no){
		var uocurl = 'http://123.147.223.93:8091/UocWebApp/jspPage/UocBase/orderDetail.jsp?jsession_id='+jsession_id+'&order_no=' + order_no+"&tache_code=''&finish_flag=101";
		window.open(uocurl,"_blank")
	}
</script>
<link rel="stylesheet" href="./css/login.css" type="text/css" />
</head>
<body>
	
	<div name="diverr" id="diverr" class="page-con" style="display: none">
		<div style="margin-top: 25px; position: relative; height: 90px;">
		</div>
		<div class="login-input" style="margin-top: 20px; position: relative;">
			<div class="diverrshow" id="diverrshow">12</div>
		</div>
	</div>
	<div name="logindiv" id="logindiv" class="page-con"
		style="display: none">
		<div style="margin-top: 25px; position: relative;">
			<input name="login_user" id="login_user" value="" class="txt"
				type="text" placeholder='用户名' />
		</div>
		<div class="login-input" style="margin-top: 20px; position: relative;">
			<div>密码</div>
			<input name="login_password" id="login_password" class="txt"
				type="password" placeholder='密码' value="" />
		</div>
		<div class="login-input" style="margin-top: 20px; position: relative;">
			<div>验证码</div>
			<input name="login_safecode" id="login_safecode" class="txt"
				type="text" placeholder='验证码' />
		</div>
		<div id="login-info" class="login-input"
			style="margin-top: 20px; position: relative;"></div>
		<div style="margin-top: 20px;">
			<span id="signin_btn" onclick="getsafecode();" class="button"
				style="width: 45%">获取验证码</span> <span id="signin_btn"
				onclick="checklogin();" class="button" style="width: 45%">登录</span>
		</div>
	</div>
	<div name="maindiv" id="maindiv" style="display: none; top: 0px;">
		<div class="container" style="height: 95px;">
			<!-- <div class="title">
				<i></i>&nbsp;&nbsp;订单查询
			</div> -->
			<div class="br" style="background:#e6e6e6;">
			<div class="item">
				<span class="o-span"> <input id='getorderinfolistinput'
					name='getorderinfolistinput' class="o-input br" type="text"
					placeholder="请输入订单号、卡号、姓名">
				</span> <span class="t-span"> <select class="o-select"
					id='getorderinfolistselect' name='getorderinfolistselect'>
						<option value="">无</option>
						<option value="0">爬取成功	</option>
						<option value="410">创建订单成功	</option>
						<option value="430">写卡成功</option>
						<option value="450">同步写卡成功</option>
						<option value="530">物流成功</option>
						<option value="550">同步物流成功</option>
				</select>
				</span>
				<div class="btn btn_primary mleft10 mt" onclick='getOrderInfoList(this);'>查询</div>
				
				<span class="o-li br cursor relative"  id="btnmanualaccout" onclick="manualaccout(this);"> 
					<i class="li-i-o i1"></i> 
					<span class="li-span-o" name="btnmanualaccout">&nbsp;获取数据</span>
					
				</span> 
				<span class="o-li br cursor relative" id="btnmanualaccoutCraeteOrder" onclick="manualaccoutCraeteOrder(this);"> 
					<span class="num absolute br text_alignc" id="scddcount" name="scddcount">0</span>
					<i class="li-i-o i2"></i> 
					<span class="li-span-o" name="btnmanualaccoutCraeteOrder">&nbsp;生成订单</span>
				</span> 
				<span class="o-li br cursor relative" id="btnwriteBackManualAccountMethod" onclick="writeBackManualAccountMethod(this);"> 
					<span class="num absolute br text_alignc" id="tbztkcount" name="tbztkcount">0</span>
					<i class="li-i-o i3"></i>
					<span class="li-span-o" name="btnwriteBackManualAccountMethod">&nbsp;同步状态【卡】</span>
				</span>
				<span class="o-li br cursor relative" id="btnwriteBackExpress" onclick="writeBackExpress(this);">
					<span class="num absolute br text_alignc" id="tbztwcount" name="tbztwcount">0</span>
					<i class="li-i-o i3"></i> 
					<span class="li-span-o" name="btnwriteBackExpress" >&nbsp;同步状态【物】</span>
				</span>
			</div>
		<!-- divend -->
			<div class="bottom">
				<div name="griddiv" id="griddiv" style="top: 0px;overflow-y:auto;">
					<table class="info_list" id="info_list" style="width:2000px">
						<thead>
							<tr>
								<th width="40px">&nbsp;</th>
								<th width="200px">订单号</th>
								<th width="180px">订单类型</th>
								<th width="95px">业务号码</th>
								<th width="100px">开户名</th>
								<th width="180px">销售订单号</th>
								<th width="240px">服务订单号</th>
								<th width="220px">CB单号</th>
								<th width="220px">CCID</th>
								<th width="220px">物流单号</th>
								<th width="220px">状态</th>
							</tr>
						</thead>
						<tbody class="info_list_body" id="info_list_body">
							<!-- <td class="td1"></td>
							<td class="td2"><a class="href" href="#" target="_blank"></a>4917050470462562</td>
							<td class="td3">4G全国76元套餐（新）</td>
							<td class="td4">18581075094</td>
							<td class="td5">张亚亚</td>
							<td class="td6">1831170525000129198</td>
							<td class="td7"><span class="href cursor"  label="订单查询" url="jspPage/UocBase/orderDetail.jsp" menu_id=""></span>3831170525000124959</td>
							<td class="td7"></td> -->
						</tbody>
					</table>
			</div>
		</div>
		</div>
		</div>
	</div>
	<div class="page cursor" style="display:none">
		<span class="page1">第<b>1</b>页</span>
		<span class="page1">共<b>1</b>页</span>
		<span class="page1">共<b>1</b>条</span>
		<span class="page1">第<b>一</b>页</span>
		<span class="page1">上<b>一</b>页</span>
		<span class="page1">下<b>一</b>页</span>
		<span class="page1">最后页</span>
		<span class="page2">跳转到 <input type="number" /> 页</span>
		<span class="page3 br">GO</span>
	</div>
</body>
</html>