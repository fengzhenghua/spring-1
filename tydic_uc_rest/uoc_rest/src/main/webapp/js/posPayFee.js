//初始化pos机
function initPos(){
	umsocx = document.getElementById("umsocxId");
}
function lpad(str,length){
	for (var j=0;j<length;j++) {
		str = "0" + str;
	}
	return str;
}
function payment(){
	var payType= $("#pay_type").val();
	var order_id= $("#order_id").val();
	$("#apweb_value").val("1");
	window.open(application.fullPath+"authority/wkprint/openAccWkprint?order_id="+order_id+"&pay_type="+payType,"order_idNew"+order_id);
}
function openWojia(){
	var payType= $("#pay_type").val();
	var order_id= $("#order_id").val();
	$("#apweb_value").val("1");
	window.open(application.fullPath+"authority/wkprint/openWojiaWkprint?order_id="+order_id+"&pay_type="+payType,"order_idNew"+order_id);
}
function m165Acc(){
	var payType= $("#pay_type").val();
	var order_id= $("#order_id").val();
	$("#apweb_value").val("1");
	window.open(application.fullPath+"authority/wkprint/m165AccWkprint?order_id="+order_id+"&pay_type="+payType,"order_idNew"+order_id);
}
function termsGood(){
	var payType= $("#pay_type").val();
	var order_id= $("#order_id").val();
	var apweb_value=$("#apweb_value");
	apweb_value.val("2");
	window.open(application.fullPath+"authority/wkprint/termsGoodWkprint?order_id="+order_id+"&pay_type="+payType,"order_idNew"+order_id);
}
function termsM165(){
	var payType= $("#pay_type").val();
	var order_id= $("#order_id").val();
	$("#apweb_value").val("3");
	window.open(application.fullPath+"authority/wkprint/m165Wkprint?order_id="+order_id+"&pay_type="+payType,"order_idNew"+order_id);
}
function termsWjt(){
	var payType= $("#payType").val();
	var order_id= $("#order_id").val();
	$("#apweb_value").val("3");
	window.open(application.fullPath+"authority/wkprint/m165WjtWkprint?order_id="+order_id+"&pay_type="+payType,"order_idNew"+order_id);
}
function termsPreferential(){
	var payType= $("#pay_type").val();
	var order_id= $("#order_id").val();
	$("#apweb_value").val("4");
	window.open(application.fullPath+"authority/wkprint/termsPreferential?order_id="+order_id+"&pay_type="+payType,"order_idNew"+order_id);
}

function paymentOrderBill(){
	if(window.confirm('该功能只打收据~请勿打发票!')){
		var payType= $("#pay_type").val();
		var order_id= $("#order_id").val();
		var wt_flag= $("#wt_flag").val();
		window.open(application.fullPath+"authority/wkprint/paymentorderbill?order_id="+order_id+"&wt_flag="+wt_flag+"&pay_type="+payType,"order_idNew"+order_id);
	}
	
}
function paymentBill(){
//打印票据方法
	if(window.confirm('该功能只打收据~请勿打发票!')){
    var server_no = $.trim($('#acc_nbr').val());
	var card_type = $.trim($('#id_type').val());
	var payment = $('#discount_totalCosts_input').val(); //本次支付
	var service_sn  = $('#order_id').val();
//	console.log("全局变量service_sn"+service_sn);
//	console.log("全局变量service_sn"+service_sn);
//	console.log("全局变量service_sn"+service_sn);
	window.open(application.fullPath+"authority/wkprint/paymentbill?server_no="+server_no+"&id_type="+card_type+"&payment="+payment+"&service_sn="+service_sn,"service_sn"+service_sn);
	}
}
//本地合约协议
function termsLocal(args){
	var term_flag =args;
	var payType= $("#pay_type").val();
	var order_id= $("#order_id").val();
	window.open(application.fullPath+"authority/wkprint/termsLocal?order_id="+order_id+"&pay_type="+payType+"&term_flag="+term_flag,"order_idNew"+order_id);
}
function wpkbiangeng(){	
	var order_id= $("#order_id").val();
	//$("#apweb_value").val("1");
	window.open(application.fullPath+"authority/wkprint/wkpChange?order_id="+order_id);
}
function archivesWkp(print_mode) {
	var order_id= $("#order_id").val();
	window.open(application.fullPath+"authority/wkprint/archivesWkprint?order_id="+order_id+"&print_mode="+print_mode);
}

function transfer23To4Wkp(){
	var order_id= $("#order_id").val();
	window.open(application.fullPath+"authority/wkprint/transfer23To4Wkprint?order_id="+order_id);
}

function woTVAcc(){
	var payType= $("#pay_type").val();
	var order_id= $("#order_id").val();
	$("#apweb_value").val("1");
	window.open(application.fullPath+"authority/wkprint/woTVWkprint?order_id="+order_id+"&pay_type="+payType,"order_idNew"+order_id);
}
////  明星手机合约
//function termsStarPhone(){
//	var payType= $("#pay_type").val();
//	var order_id= $("#order_id").val();
//	window.open(application.fullPath+"authority/wkprint/termsStarPhone?order_id="+order_id+"&pay_type="+payType,"order_idNew"+order_id)
//}
////99元本地合约非酷派7251
//function termsNotCoolpad7251(){
//	var payType= $("#pay_type").val();
//	var order_id= $("#order_id").val();
//	window.open(application.fullPath+"authority/wkprint/termsNotCoolpad7251?order_id="+order_id+"&pay_type="+payType,"order_idNew"+order_id)
//}
//// 99元本地合约酷派7251
//function termsCoolpad7251(){
//	var payType= $("#pay_type").val();
//	var order_id= $("#order_id").val();
//	window.open(application.fullPath+"authority/wkprint/termsCoolpad7251?order_id="+order_id+"&pay_type="+payType,"order_idNew"+order_id)
//}
//// 合约三折购
//function termsThreeDiscount(){
//	var payType= $("#pay_type").val();
//	var order_id= $("#order_id").val();
//	window.open(application.fullPath+"authority/wkprint/termsThreeDiscount?order_id="+order_id+"&pay_type="+payType,"order_idNew"+order_id)
//}


