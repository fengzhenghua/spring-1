//var apc_rest_host="http://localhost:8080/apc_rest/";
var oper_no="";
var serv_order_no="";
var order_id="";
var ap_id="";
$(document).ready(function () {
	//从url中取参数
	oper_no = HTML_UTLS.getParam("oper_no");
	ap_id = HTML_UTLS.getParam("ap_id");
	getOperNo();
	$("#next_flow_step").bind("click", function(){
		queryJTOrderSim();
		//getApcInfoBySimAndDeviceNumber();
	});
	
});


/**
 * 根据卡号查询中台订单
 */

function queryJTOrderSim(){
	
	var contact_tel = $("#contact_tel").val();
	var iccid = $("#iccid").val();
	var acc_nbr = $("#acc_nbr").val();
	
	
	if(iccid.length != 8){
		DIALOG_UTIL.showTypeDialog("error","请输入ICCID后八位！");
		return;
	}
	
	var dataJson={
			"sim_id"  : iccid,
		    "acc_nbr":acc_nbr,
		    "contact_tel":contact_tel,
		    "oper_no":oper_no
	};
	DIALOG_UTIL.showTypeDialog("loading", "");
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoOrderServiceRest/queryOrderInfoFromUoc",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:dataJson,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			//$.loading.show("正在加载");
		},
		success: function(data) {
			//调用能力平台成功
			if (data.respCode=="0000") {
				//中台返回成功
				if(data.args.code=="200"){
					var json_info=JSON.parse(data.args.json_info);
					if(json_info.serv_order_list!=null&&json_info.serv_order_list.length>0){
						serv_order_no=json_info.serv_order_list[0].serv_order_no;
						queryOrderId();
					}else{
						DIALOG_UTIL.showTypeDialog("error", "没有查询到相应服务订单号！！！");
					}
				}
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "查询中台订单信息Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}



/**
 * 根据中台服务订单号查询本地order_id
 */
function queryOrderId(){
	var dataJson={
			"serv_order_no":serv_order_no
	};
	DIALOG_UTIL.showTypeDialog("loading", "");
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoCommonServiceRest/getApcOrderId",
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
				order_id = data.args.order_id;
				CACHE_UTIL.setSessionItem("order_id", order_id);
				CACHE_UTIL.setSessionItem("oper_no", oper_no);
				CACHE_UTIL.setSessionItem("serv_order_no", serv_order_no);
				CACHE_UTIL.setSessionItem("ap_id", ap_id);
				window.location.href = "idcardCamera.html";
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "查询本地order_idAjax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}
	

/**
 * 激活流程通过卡号和号码获取触点信息
 */
function getApcInfoBySimAndDeviceNumber(){
	var contact_tel = $("#contact_tel").val();
	var iccid = $("#iccid").val();
	var acc_nbr = $("#acc_nbr").val();
	
	if(iccid.length != 8){
		DIALOG_UTIL.showTypeDialog("error","请输入ICCID后八位！");
		return;
	}
	
	if(acc_nbr==""||acc_nbr==null||acc_nbr==undefined){
		DIALOG_UTIL.showTypeDialog("error","请输入电话卡号码！");
		return;
	}
	
	var dataJson={
			"sim_id"  : iccid,
		    "acc_nbr":acc_nbr,
		    "contact_phone":contact_tel
	};
	DIALOG_UTIL.showTypeDialog("loading", "");
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoCommonServiceRest/getApcInfoBySimAndDeviceNumber",
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
				order_id = data.args.order_id;
				oper_no = data.args.oper_no;
				serv_order_no=data.args.serv_order_no;
				CACHE_UTIL.setSessionItem("order_id", order_id);
				CACHE_UTIL.setSessionItem("oper_no", oper_no);
				CACHE_UTIL.setSessionItem("serv_order_no", serv_order_no);
				window.location.href = "idcardCamera.html";
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "查询本地order_idAjax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});

}


/**
 * 主要是取工号信息
 */

function getOperNo(){
	DIALOG_UTIL.showTypeDialog("loading", "");
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/ap/queryApMsg",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:{"ap_id":ap_id},
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			//$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				//如果url中取不到工号，就取接口返回的绑定工号
				if(oper_no==null||oper_no==""||oper_no==undefined){
					oper_no=data.args.ap_info.bind_oper;
					if(oper_no==""||oper_no==null||oper_no==undefined){
						DIALOG_UTIL.showTypeDialog("error", "系统没有配置相应工号信息！！！");
						return;
					}
				}
				
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "取工号信息Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}





