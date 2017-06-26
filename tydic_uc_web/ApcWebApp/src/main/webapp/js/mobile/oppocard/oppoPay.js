var order_id = "";
var oper_no = "";
var tele_type = "";
var device_number = "";
var fee_all = "";
var market_price="";
var advancePay="";
var callingSearch = false; //是否在执行轮询操作
var timersearch = null;

var mcht_flag = "";		//微信标志
//var apc_rest_host="http://localhost:8080/apc_rest/";
var contact_phone="";
var product_id="";
var receive_address="";
var receive_province = "";
var receive_city = "";
var receive_area = "";
var pay_type="20";
var cert_address="";
var cert_expire_date="";
var customer_name="";
var customer_sex="";
var cert_num="";
var recom_person_id="";
var recom_person_name="";
var oper_code="";
var serv_order_no="";
var sale_order_no="";
var pay_flag="N";
//保存到属性表,Y表示创建中台订单成功
var uoc_order_success="N";
//关注公众号二维码，用于后续激活
var wxPublicQrCode="";
//即时激励发展人
var reward_oper_no="";
//触点类型
var ap_type="";
//费用数组
var fee_info;
var uoc_total_fee="";

$(document).ready(function () {
	//取页面缓存数据
	oper_no = CACHE_UTIL.getSessionItem("oper_no");
	tele_type = CACHE_UTIL.getSessionItem("tele_type");
	device_number = CACHE_UTIL.getSessionItem("selected_number");
	//fee_all = CACHE_UTIL.getSessionItem("market_price");
	market_price = CACHE_UTIL.getSessionItem("market_price");
	advancePay = CACHE_UTIL.getSessionItem("advancePay");
	var totalFee=parseFloat(market_price)+parseFloat(advancePay);
	fee_all=totalFee+"";
	order_id = CACHE_UTIL.getSessionItem("order_id");
	cert_address = CACHE_UTIL.getSessionItem("addr");
	cert_num = CACHE_UTIL.getSessionItem("certId");
	customer_name = CACHE_UTIL.getSessionItem("certName");
	customer_sex = CACHE_UTIL.getSessionItem("sex");
	contact_phone = CACHE_UTIL.getSessionItem("contact_phone");
	product_id = CACHE_UTIL.getSessionItem("product_id");
	receive_address = CACHE_UTIL.getSessionItem("receive_address");
	receive_province = CACHE_UTIL.getSessionItem("receive_province");
	receive_city = CACHE_UTIL.getSessionItem("receive_city");
	receive_area = CACHE_UTIL.getSessionItem("receive_area");
	recom_person_id=CACHE_UTIL.getSessionItem("dev_id");
	recom_person_name=CACHE_UTIL.getSessionItem("dev_name");
	oper_code=CACHE_UTIL.getSessionItem("oper_code");
	mcht_flag=CACHE_UTIL.getSessionItem("ap_id");
	reward_oper_no=CACHE_UTIL.getSessionItem("reward_oper_no");
	ap_type=CACHE_UTIL.getSessionItem("ap_type");
	fee_info=CACHE_UTIL.getSessionItem("fee_info");
	uoc_total_fee=CACHE_UTIL.getSessionItem("uoc_total_fee");
	
	$("#fee_all").html(fee_all);
	
	GetWxQr();
	//fhc();
});

/**
 * 二维码
 */
function GetWxQr() {
	var dataJson={
			oper_no:oper_no,
			order_id : order_id,
			tele_type:tele_type,
			body:"微信开户（1元流量王）",
			totalFee:fee_all, //费用的单位为元
			deviceInfo:device_number,
			mcht_flag:mcht_flag
	};
	DIALOG_UTIL.showTypeDialog("loading", "");
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoPayServiceRest/getWxQrCode",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:dataJson,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			//$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				$("#PIC_QR").attr('src',("data:image/jpeg;base64,"+data.content)); //二维码图片
				
				//启动支付结果轮询
				timersearch = setInterval(searchStatus, 1000);
				//searchStatus();
			} else {
				if (data.content=="该订单已经支付！" || data.content=="生成二维码失败！该订单已支付") {
					$("#PIC_QR").attr('src','../../images/mobile/oppoCard/payment_ok.png');
					$("#tip1").html("该订单已经成功支付过！");
		        	$("#tip2").html("");
		        	$("#bottom_done").show();
		        	
		        	$("#next_flow_step").unbind().bind("click", function() {
		        		WeixinJSBridge.call('closeWindow');
		        	});
					//updateOrderAttr();
				}else{
					DIALOG_UTIL.showTypeDialog("error", data.content);
				}
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "校验Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}



/**
 * 支付结果轮询函数
 */
function searchStatus() {
	
	if (callingSearch) {
		return;
	}
	
	callingSearch = true;
	
	var dataJson={
			oper_no:oper_no,           	
			order_id : order_id
	};
	//DIALOG_UTIL.showTypeDialog("loading", "");
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoPayServiceRest/getPayResult",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:dataJson,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			//$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				$("#tip1").html("支付成功！");
	        	$("#tip2").html("正在下单中，请稍候...");
				pay_flag="Y";
				clearInterval(timersearch);
				$("#PIC_QR").attr('src','../../images/mobile/oppoCard/payment_ok.png');
				createUocOrder();
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "支付结果查询Ajax请求失败!");
		},
		complete:function(){
			callingSearch = false;
			//DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}


/**
 * 在中台创建订单
 */
function createUocOrder() {
	DIALOG_UTIL.showTypeDialog("loading", "");
	var activity_id=CACHE_UTIL.getSessionItem("activity_id");
	var goodNumFee=parseFloat(advancePay)*100;
	var feeInfo=JSON.parse(fee_info);
	var total_fee=parseFloat(uoc_total_fee)*100;
	if(goodNumFee>0){
		total_fee=total_fee+goodNumFee;
		for(var i=0;i<feeInfo.length;i++){
			if(feeInfo[i].fee_id=="100000"){
				feeInfo[i].orig_fee=parseFloat(feeInfo[i].orig_fee)+goodNumFee+"";
				feeInfo[i].real_fee=parseFloat(feeInfo[i].real_fee)+goodNumFee+"";
			}else{
				if(i==feeInfo.length-1){
					var fee_result={
							"fee_id":"100000",
							"fee_category":"2",
							"orig_fee":goodNumFee+"",
							"relief_fee":"0.00",
							"relief_result":"无",
							"real_fee":goodNumFee+"",
							"fee_des":"营业普通预存款"
					};
					feeInfo.push(fee_result);
				}
			}
		}
	}
	
	var all_fee_info=JSON.stringify(feeInfo);
	
	var dataJson={
			"oper_no":oper_no,
		    "order_id":order_id,
		    "pay_type":pay_type,
			"serial_number":device_number,
			"recom_person_id":recom_person_id,
			"recom_person_name":recom_person_name,
			"first_mon_bill_mode":"01",
			"product_id":product_id,
			"cert_address":cert_address,
			"cert_expire_date":"",
			"customer_name":customer_name,
			"customer_sex":customer_sex,
			"cert_num":cert_num,
			"cert_type":"02",
			"contact_address":receive_address,
			"contact_phone":contact_phone,
			"receive_name":customer_name,
			"receive_phone":contact_phone,
			"receive_province":receive_province,
			"receive_city":receive_city,
			"receive_area":receive_area,
			"receive_address":receive_address,
			"total_fee":total_fee,
			"oper_code":oper_code,
			"cod_charge":"",
			"activity_id":isNullUtil("1",activity_id),
			"ap_id":mcht_flag,
			"reward_oper_no":isNullUtil("1",reward_oper_no),
			"ap_type":isNullUtil("1",ap_type),
			"fee_info":all_fee_info
	};
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoUocOrderServiceRest/createUocOrder",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:dataJson,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			//$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				serv_order_no=data.args.serv_order_no;
				sale_order_no=data.args.sale_order_no;
				wxPublicQrCode=data.args.wxPublicQrCode;
				uoc_order_success="Y";
				//$("#PIC_QR").attr('src','../../images/mobile/oppoCard/payment_ok.png');
	        	$("#tip1").html("支付成功！订单号："+serv_order_no);
	        	$("#tip2").html("电话卡将在订单信息审核完成后寄出，感谢使用！");
	        	$("#bottom_done").show();
	        	//隐藏重下单按钮
	        	$("#re_do").hide();
				$("#re_create_order").unbind("click");
				
	        	$("#next_flow_step").unbind().bind("click", function() {
	        		WeixinJSBridge.call('closeWindow');
	        	});
	        	
	        	//更新订单属性表
	        	updateOrderAttr();
			} else {
				$("#re_do").show();
				$("#re_create_order").unbind().bind("click", function() {
					createUocOrder();
	        	});
				DIALOG_UTIL.showTypeDialog("error", data.content);
				DIALOG_UTIL.hideDialog("", "loading");
			}
		},
		error: function(data) {
			$("#re_do").show();
			$("#re_create_order").unbind().bind("click", function() {
				createUocOrder();
        	});
			DIALOG_UTIL.showTypeDialog("error", "创建中台订单Ajax请求失败!");
			DIALOG_UTIL.hideDialog("", "loading");
		},
		complete:function(){
			//DIALOG_UTIL.hideDialog("", "loading");
		}
	});
	
}



/**
 * 微信退款
 */

function wxpayRefund() {
	var dataJson={
			"oper_no":oper_no,
			"order_id":order_id
	};
	DIALOG_UTIL.showTypeDialog("loading", "");
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoPayServiceRest/wxpayRefund",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:dataJson,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			//$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "退款Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
	
}


/**
 * 更新订单属性表
 */

function updateOrderAttr(){
	
	var dataJson={
			"order_id":order_id,
			"serv_order_no":serv_order_no,
			"sale_order_no":sale_order_no,
			"pay_flag":pay_flag,
			"uoc_order_success":uoc_order_success,
			"sim_id":"N"
	};
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoOrderServiceRest/updateOppoOrderInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:dataJson,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			//$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				$("#re_do").hide();
				$("#re_create_order").unbind("click");
				$("#order_success_tip").html("支付并下单成功，订单号为："+serv_order_no);
				//隐藏完成按钮
				$("#bottom_done").hide();
				//显示关注的公众号二维码
				$("#PUBLIC_QR").attr('src',(wxPublicQrCode)); //二维码图片
				DIALOG_UTIL.showDialog("qr_code_pop", true);
				$("#qr_code_pop").css("top","15px");
				//关闭二维码窗口
				$("#close_all_page").unbind().bind("click", function() {
	        		WeixinJSBridge.call('closeWindow');
	        	});
				//关闭窗口
				$("#back_main_page").unbind().bind("click", function() {
					$("#bottom_done").show();
					DIALOG_UTIL.hideDialog("qr_code_pop");
	        	});
			} else {
				$("#re_do").show();
				$("#re_create_order").unbind().bind("click", function() {
					createUocOrder();
	        	});
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			$("#re_do").show();
			$("#re_create_order").unbind().bind("click", function() {
				createUocOrder();
        	});
			DIALOG_UTIL.showTypeDialog("error", "更新订单属性表Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}


/*function fhc(){
	var str="http://open.weixin.qq.com/qr/code/?username=gh_9efbe4393f62";
	$("#order_success_tip").html("支付并下单成功，订单号为：3831170306000097001");
	$("#PUBLIC_QR").attr('src',str); //二维码图片
	DIALOG_UTIL.showDialog("qr_code_pop", true);
}*/








