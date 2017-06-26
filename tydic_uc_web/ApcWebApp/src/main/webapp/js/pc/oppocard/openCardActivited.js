$(document).ready(function () {
	$("#next_flow_step").bind("click", function(){
		
//		var contact_phone = $("#contact_phone").val();
//		var iccid = $("#iccid").val();
//		
//		
//		if(iccid.length != 8){
//			DIALOG_UTIL.showTypeDialog("error","请输入ICCID后八位！");
//			return;
//		}
//		
//		DIALOG_UTIL.showTypeDialog("loading", "");
//		var registClient = {
//				onComplete: function (args) {
//					
//					CACHE_UTIL.setSessionItem("jsessionid", args.jsessionid);
//					CACHE_UTIL.setSessionItem("order_id", args.order_id);
//					window.location.href = "idcardCamera.html";
//				},
//				onError: function(error) {
//				},
//				onException: function(status, errorInfo, hint) {
//				},
//				onFinally: function () {
//					DIALOG_UTIL.hideDialog("", "loading");
//				}
//		};
//		WebUtil.doPost(URLS.URL_API_HOST + '/rest/oppocard/checkOrder', {
//			"contact_phone" : contact_phone,
//			"iccid":iccid
//
//		}, true,registClient);
		
		var contact_phone = $("#contact_phone").val();
		var iccid = $("#iccid").val();
		
		
		if(iccid.length != 8){
			DIALOG_UTIL.showTypeDialog("error","请输入ICCID后八位！");
			return;
		}
		
		DIALOG_UTIL.showTypeDialog("loading", "");
		var registClient = {
				onComplete: function (args) {
					CACHE_UTIL.setSessionItem("jsessionid", args.jsessionid);
					CACHE_UTIL.setSessionItem("order_id", args.order_id);
					CACHE_UTIL.setSessionItem("serv_order_no", args.serv_order_no);
					window.location.href = "idcardCamera.html";
				},
				onError: function(error) {
				},
				onException: function(status, errorInfo, hint) {
				},
				onFinally: function () {
					DIALOG_UTIL.hideDialog("", "loading");
				}
		};
		WebUtil.doPost(URLS.URL_API_HOST + 'rest/oppocard/queryJTOrderSim', {
		    "sim_id"  : iccid,
		    "contact_tel":contact_phone
		}
		, true,registClient);
	});
	
});