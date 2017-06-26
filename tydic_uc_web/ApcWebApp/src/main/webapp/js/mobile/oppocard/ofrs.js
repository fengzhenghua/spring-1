var apc_rest_host="http://localhost:8080/apc_rest/";
$(document).ready(function () {
	
	
	//getOfrs();
	getOfrsApc();
	$("#next_flow_step").unbind("click").bind("click", submitOrderApc);
	
	//$("#next_flow_step").unbind("click").bind("click", submitOrder);
	
	$("#show_detail").unbind("click").bind("click", function() {
		DIALOG_UTIL.showDialog("expenses_deatil", true);
	});
	$("#hide_detail").unbind("click").bind("click", function() {
		DIALOG_UTIL.hideDialog("expenses_deatil");
	});
	
	$("div[name='ofr_div']").click(function(){
		$("div[name='ofr_div']").each(function() {
			$(this).removeClass("open_card_clicked");
		});
		
		$(this).addClass("open_card_clicked");
		ofr_idx = parseInt($(this).attr("data"));
		$("#ofr_desc").html(listOfr[ofr_idx].ofr_desc);
	});
});

var listOfr = null;
//var ofr_id = null;
var jsessionid;
var order_id;
var ofr_idx = null;//下标

/*function getOfrs() {
	
	CACHE_UTIL.clearSessionData();
	DIALOG_UTIL.showTypeDialog("loading", "");
	
	var registClient = {
			onComplete: function (args) {
				listOfr = args.listOfr;
				if (listOfr!=null&&listOfr!=undefined&&listOfr.length==2) {
					//ofr_id = listOfr[0].ofr_id;
					//ofr_idx = 0;
					$("#ofr_name0").html(listOfr[0].ofr_name);
					$("#ofr_name1").html(listOfr[1].ofr_name);
					$("div[name='ofr_div']").first().click();
				}
				jsessionid = args.jsessionid;
				CACHE_UTIL.setSessionItem("jsessionid", jsessionid);
			},
			onError: function (error) {
				DIALOG_UTIL.showTypeDialog("error", error.content);
			},
			onException: function (status, errorInfo, hint) {
			},
			onFinally: function () {
				DIALOG_UTIL.hideDialog("", "loading");
			}
		};
	    
	    WebUtil.doPost(apc_rest_host + "rest/oppocard/getOfrs",{
	    }, true, registClient);
};*/

/*function submitOrder() {
	if (ofr_idx == null) {
		return;
	}
	
	//取选中的资费
	var ofrVo;
	
	ofrVo = listOfr[ofr_idx];
	
	DIALOG_UTIL.showTypeDialog("loading", "");
	
	//提交订单
	var registClient = {
			onComplete: function (args) {
				order_id = args.order_info.order_id;
				CACHE_UTIL.setSessionItem("order_id", order_id);
				CACHE_UTIL.setSessionItem("tele_type", ofrVo.tele_type);
				CACHE_UTIL.setSessionItem("product_id", ofrVo.product_id);
				CACHE_UTIL.setSessionItem("product_name", ofrVo.ofr_name);
				CACHE_UTIL.setSessionItem("market_price", ofrVo.market_price);
				attrUpdate(ofrVo);
			},
			onError: function (error) {
				DIALOG_UTIL.hideDialog("", "loading");
				DIALOG_UTIL.showTypeDialog("error", error.content);
			},
			onException: function (status, errorInfo, hint) {
				DIALOG_UTIL.hideDialog("", "loading");
				DIALOG_UTIL.showTypeDialog("error", errorInfo);
			},
			onFinally: function () {
				
			}
		};
	
	WebUtil.doPost(apc_rest_host + 'rest/orderInfo/orderInfoSubmit', {
		jsessionid   : jsessionid,
		tele_type    : ofrVo.tele_type,
		order_source : "300"
	}, true, registClient);
	
};*/

/*function attrUpdate(ofrVo) {
	
	var fee_info = [{
		"rel_amount" : ""+ofrVo.market_price,
		"rec_amount" : ""+ofrVo.market_price,
		"fee_code"   : "100000",
		"fee_class"  : "2",
		"fee_name"   : "普通预存款"
	}];
	
	var concard_flag = "0";//第一个产品参加活动，第二个产品不参加
	if (ofr_idx==0) {
		concard_flag = "oppo";
	}
	
	var attrdata = {
			"ofr_id"          : ofrVo.ofr_id,
			"product_id"      : ofrVo.product_id,
			"product_name"    : ofrVo.ofr_name,
			"prepay_flag"     : ofrVo.pay_flag,
			"tele_type"       : ofrVo.tele_type,
			"product_desc"    : ofrVo.ofr_desc,
			"first_month_fee" : "01", //套外
			"market_price"    : ofrVo.market_price,
			"fee_all"         : "0",
			"pay_type"        : "20",
			"concard_flag"    : concard_flag,
			"fee_info"        : fee_info
	};
	
	DIALOG_UTIL.showTypeDialog("loading", "");
	var registClient = {
			onComplete: function (args) {
				//跳转选号
				window.location.href = "base.html";
			},
			onError: function(error) {
				DIALOG_UTIL.showTypeDialog("error", error.content);
			},
			onException: function(status, errorInfo, hint) {
				
				DIALOG_UTIL.showTypeDialog("error", errorInfo);
			},
			onFinally: function () {
				DIALOG_UTIL.hideDialog("", "loading");
			}
	};
	var reg=new RegExp(":null,","g");
	WebUtil.doPost(apc_rest_host + 'rest/orderInfo/orderInfoAttrUpdate', {
		"jsessionid": jsessionid,
		"order_id"  : order_id,	
		"page_code" : "5",	
		"order_json": JSON.stringify(attrdata).replace(reg,':"",')

	}, true,registClient);
};*/


//****************迁移代码后修改js*********************fhc

/**
 * 取资费
 */
function getOfrsApc(){
	CACHE_UTIL.clearSessionData();
	DIALOG_UTIL.showTypeDialog("loading", "");
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppocard/getOfrs",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:{},
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			//$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				listOfr = data.args.listOfr;
				if (listOfr!=null&&listOfr!=undefined&&listOfr.length==2) {
					//ofr_id = listOfr[0].ofr_id;
					//ofr_idx = 0;
					$("#ofr_name0").html(listOfr[0].ofr_name);
					$("#ofr_name1").html(listOfr[1].ofr_name);
					$("div[name='ofr_div']").first().click();
				}
				jsessionid = data.args.jsessionid;
				CACHE_UTIL.setSessionItem("jsessionid", jsessionid);
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "查询资费Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}

/**
 * 生成订单
 */
function submitOrderApc(){
	if (ofr_idx == null) {
		return;
	}
	
	//取选中的资费
	var ofrVo;
	
	ofrVo = listOfr[ofr_idx];
	var dataJson={
			jsessionid   : jsessionid,
			tele_type    : ofrVo.tele_type,
			order_source : "300"//订单来源
	};
	DIALOG_UTIL.showTypeDialog("loading", "");
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/orderInfo/orderInfoSubmit",
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
				order_id = data.args.order_info.order_id;
				CACHE_UTIL.setSessionItem("order_id", order_id);
				CACHE_UTIL.setSessionItem("tele_type", ofrVo.tele_type);
				CACHE_UTIL.setSessionItem("product_id", ofrVo.product_id);
				CACHE_UTIL.setSessionItem("product_name", ofrVo.ofr_name);
				CACHE_UTIL.setSessionItem("market_price", ofrVo.market_price);
				attrUpdateApc(ofrVo);
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "创建订单Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}


/**
 * 更新订单属性表
 * @param ofrVo
 */
function attrUpdateApc(ofrVo) {
	var fee_info = [{
		"rel_amount" : ""+ofrVo.market_price,
		"rec_amount" : ""+ofrVo.market_price,
		"fee_code"   : "100000",
		"fee_class"  : "2",
		"fee_name"   : "普通预存款"
	}];
	
	var concard_flag = "0";//第一个产品参加活动，第二个产品不参加
	if (ofr_idx==0) {
		concard_flag = "oppo";
	}
	
	var attrdata = {
			"ofr_id"          : ofrVo.ofr_id,
			"product_id"      : ofrVo.product_id,
			"product_name"    : ofrVo.ofr_name,
			"prepay_flag"     : ofrVo.pay_flag,
			"tele_type"       : ofrVo.tele_type,
			"product_desc"    : ofrVo.ofr_desc,
			"first_month_fee" : "01", //套外
			"market_price"    : ofrVo.market_price,
			"fee_all"         : "0",
			"pay_type"        : "20",
			"concard_flag"    : concard_flag,
			"fee_info"        : fee_info
	};
	var reg=new RegExp(":null,","g");
	var dataJson={
			"jsessionid": jsessionid,
			"order_id"  : order_id,	
			"page_code" : "5",	
			"order_json": JSON.stringify(attrdata).replace(reg,':"",')
	}
	DIALOG_UTIL.showTypeDialog("loading", "");
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/orderInfo/orderInfoAttrUpdate",
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
				//跳转选号
				window.location.href = "base.html";
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "更新订单属性表Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}
