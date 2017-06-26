
var order_id = "";
var jsessionid = "";
var tele_type = "";
var device_number = "";
var fee_all = "";
var callingSearch = false; //是否在执行轮询操作
var timersearch = null;

var mcht_flag = "";		//微信标志
var apc_rest_host="http://localhost:8080/apc_rest/";
$(document).ready(function () {
	
	jsessionid = CACHE_UTIL.getSessionItem("jsessionid");
	order_id = CACHE_UTIL.getSessionItem("order_id");
	tele_type = CACHE_UTIL.getSessionItem("tele_type");
	device_number = CACHE_UTIL.getSessionItem("selected_number");
	fee_all = CACHE_UTIL.getSessionItem("market_price");
	
	$("#fee_all").html(fee_all);
	
	//GetWxQr();
	GetWxQrApc();
});

/*function GetWxQr() {
	DIALOG_UTIL.showTypeDialog("loading","");
	var registClient = {
			onComplete: function(args) {
				$("#PIC_QR").attr('src',("data:image/jpeg;base64,"+args.content)); //二维码图片
				
				//启动支付结果轮询
				timersearch = setInterval(searchStatus, 1000);
				
			},
			onError: function(error) {
				if (error.content=="该订单已经支付！" || error.content=="生成二维码失败！该订单已支付") {
					paySuccess();
				}
				else {
					DIALOG_UTIL.showTypeDialog("error",error.content);
				}
			},
			onException: function(status, errorInfo, hint) {
			},
			onFinally:function(){
				DIALOG_UTIL.hideDialog("","loading");
			}
	};
	var jsonData = {
    		jsessionid:jsessionid,           	
			order_id : order_id,
			tele_type:tele_type,
			body:"微信开户（1元流量王）",
			totalFee:fee_all, //费用的单位为元
			deviceInfo:device_number,
			mcht_flag:mcht_flag
	};
	
	WebUtil.doPost(URLS.URL_API_HOST + "rest/pay/wxQrCode",jsonData, true, registClient,{ignoreDefaultErrorHandler:true});
	
}*/


//支付结果轮询函数
/*function searchStatus() {
	//console.info("callingSearch="+callingSearch);
	
	if (callingSearch) {
		return;
	}
	
	var registClient = {
			onComplete: function(args) {
				if (args.type=="success") {
					clearInterval(timersearch);
					paySuccess();
				}
			},
			onError: function(error) {
			},
			onException: function(status, errorInfo, hint) {
			},
			onFinally:function(){
				callingSearch = false;
			}
	};
	var jsonData = {
    		jsessionid:jsessionid,           	
			order_id : order_id
	};
	
	callingSearch = true;
	WebUtil.doPost(URLS.URL_API_HOST + "rest/pay/searchStatus",jsonData, true, registClient,{ignoreDefaultErrorHandler:true});
};*/

/*function paySuccess() {
	DIALOG_UTIL.showTypeDialog("loading", "");
    var registClient = {
        onComplete: function (args) {
        	createSaleOrder();
        },
        onError: function(error) {
        	wxpayRefund();
			DIALOG_UTIL.showTypeDialog("error", "出现异常，费用已退。出错信息："+error.content);
		},
		onException: function(status, errorInfo, hint) {
			wxpayRefund();
			DIALOG_UTIL.showTypeDialog("error", errorInfo);
		},
		onFinally: function () {
			//DIALOG_UTIL.hideDialog("", "loading");
		}
    };
    
    WebUtil.doPost(URLS.URL_API_HOST + "rest/orderInfo/orderInfoStatusUpdate", {
        "jsessionid" : jsessionid,
        "order_id"   : order_id,
        "bill_type"  : '100'
    }, true, registClient);
};*/

function numberOccupy() {
	DIALOG_UTIL.showTypeDialog("loading", "");
	var registClient = {
			onComplete: function (args) {
				if( args.state_code == "0000" ){
					// alert("流程走完！");
					$("#PIC_QR").attr('src','../../img/720/payment_ok.png');
		        	$("#tip1").html("支付成功！订单号："+order_id);
		        	$("#tip2").html("电话卡将在订单信息审核完成后寄出，感谢使用！");
		        	$("#bottom_done").show();
		        	
		        	$("#next_flow_step").unbind().bind("click", function() {
		        		WeixinJSBridge.call('closeWindow');
		        	});
				}
				else {
					//DIALOG_UTIL.hideDialog("", "loading");
					wxpayRefund();
					DIALOG_UTIL.showTypeDialog("error", "号码预占失败，已支付的费用已退，请选择其它的号码");
				}
			},
			onError: function(error) {
				wxpayRefund();
				DIALOG_UTIL.showTypeDialog("error", "号码预占失败，已支付的费用已退，请选择其它的号码重试");
			},
			onException: function(status, errorInfo, hint) {
				wxpayRefund();
				DIALOG_UTIL.showTypeDialog("error", errorInfo);
			},
			onFinally: function () {
				DIALOG_UTIL.hideDialog("", "loading");
			}
	};
	WebUtil.doPost(URLS.URL_API_HOST + 'rest/oppocard/numberOccupy', {
		"jsessionid"   : jsessionid,
		"order_id"     : order_id,
		"acc_nbr"      : device_number,
		"ser_type"     : "1",
		"tele_type"    : tele_type,
		"occupiedFlag" : "3"  //号码状态标识：0 不预占；1 预占；2 预订（未付费）；3 预订（付费）；4 释放资源
	}, true,registClient);
};

/*function wxpayRefund() {
	var registClient = {
			onComplete: function (args) {
			},
			onError: function(error) {
			},
			onException: function(status, errorInfo, hint) {
			},
			onFinally: function () {
			}
	};
	WebUtil.doPost(URLS.URL_API_HOST + 'rest/pay/wxpayRefund', {
		"jsessionid"   : jsessionid,
		"order_id"     : order_id
	}, true,registClient,{ignoreDefaultErrorHandler:true});
};*/
//更新订单内容
function attrUpdate(attrdata) {
	
	DIALOG_UTIL.showTypeDialog("loading", "");
	var registClient = {
			onComplete: function (args) {
				// 2.预占号码
				//numberOccupy();
				
				//改成支付，然后支付完成再长预占
//				pay();
				//numberOccupy();
				
			},
			onError: function(error) {
				wxpayRefund();
				DIALOG_UTIL.showTypeDialog("error", error.content);
			},
			onException: function(status, errorInfo, hint) {
				wxpayRefund();
				DIALOG_UTIL.showTypeDialog("error", errorInfo);
			},
			onFinally: function () {
				//DIALOG_UTIL.hideDialog("", "loading");
			}
	};
	var reg=new RegExp(":null,","g");
	WebUtil.doPost(URLS.URL_API_HOST + 'rest/orderInfo/orderInfoAttrUpdate', {
		"jsessionid": jsessionid,
		"order_id"  : order_id,
		"page_code" : "0",
		"order_json": JSON.stringify(attrdata).replace(reg,':"",')

	}, true,registClient);
};

//在中台创建订单
/*function createSaleOrder() {
	var registClient = {
			onComplete: function (args) {
//				var allFee_json = {"activity_desc":"存0元送10元话费(海南)","activity_id":"90131486","activity_name":"存0元送10元话费(海南)","activity_type":"CFSF001","activity_type_name":"存费送费"};
//	        	numberOccupy();
				
				$("#PIC_QR").attr('src','../../img/720/payment_ok.png');
	        	$("#tip1").html("支付成功！订单号："+order_id);
	        	$("#tip2").html("电话卡将在订单信息审核完成后寄出，感谢使用！");
	        	$("#bottom_done").show();
	        	
	        	$("#next_flow_step").unbind().bind("click", function() {
	        		WeixinJSBridge.call('closeWindow');
	        	});
			},
			onError: function(error) {
			},
			onException: function(status, errorInfo, hint) {
			},
			onFinally: function () {
			}
	};
	WebUtil.doPost(URLS.URL_API_HOST + 'rest/oppocard/createJTOrder', {
	    "jsessionid":jsessionid,
	    "order_id"  : order_id
	}
	, true,registClient);
};*/


/*********************************迁移代码后改造js****************************************/
function GetWxQrApc() {
	var dataJson={
			jsessionid:jsessionid,           	
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
		url: apc_rest_host + "rest/pay/wxQrCode",
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
				timersearch = setInterval(searchStatusApc, 1000);
				//searchStatusApc();
			} else {
				if (data.content=="该订单已经支付！" || data.content=="生成二维码失败！该订单已支付") {
					paySuccessApc();
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
function searchStatusApc() {
	
	if (callingSearch) {
		return;
	}
	
	callingSearch = true;
	
	var dataJson={
			jsessionid:jsessionid,           	
			order_id : order_id
	};
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/pay/searchStatus",
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
				clearInterval(timersearch);
				paySuccessApc();
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "支付结果查询Ajax请求失败!");
		},
		complete:function(){
			callingSearch = false;
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}


/**
 * 支付成功
 */
function paySuccessApc() {
	var dataJson={
			"jsessionid" : jsessionid,
	        "order_id"   : order_id,
	        "bill_type"  : '100'
	};
	DIALOG_UTIL.showTypeDialog("loading", "");
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/orderInfo/orderInfoStatusUpdate",
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
				createSaleOrderApc();
			} else {
				wxpayRefundApc();
				DIALOG_UTIL.showTypeDialog("error", "出现异常，费用已退。出错信息："+data.content);
			}
		},
		error: function(data) {
			wxpayRefundApc();
			DIALOG_UTIL.showTypeDialog("error", "出现异常，费用已退。出错信息："+data.content);
		},
		complete:function(){
			callingSearch = false;
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}



/**
 * 在中台创建订单
 */
function createSaleOrderApc() {
	
	var dataJson={
			"jsessionid":jsessionid,
		    "order_id"  : order_id
	};
	DIALOG_UTIL.showTypeDialog("loading", "");
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppocard/createJTOrder",
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
				$("#PIC_QR").attr('src','../../img/720/payment_ok.png');
	        	$("#tip1").html("支付成功！订单号："+order_id);
	        	$("#tip2").html("电话卡将在订单信息审核完成后寄出，感谢使用！");
	        	$("#bottom_done").show();
	        	
	        	$("#next_flow_step").unbind().bind("click", function() {
	        		WeixinJSBridge.call('closeWindow');
	        	});
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "更新订单Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
	
}


/**
 * 微信退款
 */

function wxpayRefundApc() {
	
	var dataJson={
			"jsessionid"   : jsessionid,
			"order_id"     : order_id
	};
	DIALOG_UTIL.showTypeDialog("loading", "");
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/pay/wxpayRefund",
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















