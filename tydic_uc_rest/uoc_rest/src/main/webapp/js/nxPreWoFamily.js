var submitFlag = true;
$(document).ready(function () {
	$("div[name='dingdan_type_select']").click(function(){
		$(this).toggleClass("radio_checked").toggleClass("radio");
		$(this).siblings(".radio_checked").toggleClass("radio_checked").toggleClass("radio");
	});
	$("div[name='chanpin_type_select']").click(function(){
		$(this).toggleClass("radio_checked").toggleClass("radio");
		$(this).siblings(".radio_checked").toggleClass("radio_checked").toggleClass("radio");
	});
	$("div[name='first_type_select']").click(function(){
		$(this).toggleClass("radio_checked").toggleClass("radio");
		$(this).siblings(".radio_checked").toggleClass("radio_checked").toggleClass("radio");
	});
	$("div[name='orderbelong_type_select']").click(function(){
		$(this).toggleClass("radio_checked").toggleClass("radio");
		$(this).siblings(".radio_checked").toggleClass("radio_checked").toggleClass("radio");
	});
	
	$("#acc_nbr_GSM,#acc_nbr_WCDMA").blur(function(){
		if( $(this).val()!="" ){
			var ofr_type = $("div[name='orderbelong_type_select'][class='radio_checked']").attr("value");
			$.post("checkPreFamilyNum",{device_number:$(this).val(),ofr_type:ofr_type},function(data){
				if(data.result!="" && data.result!="," ){
					$.alert("号码"+data.result+"的号码类型与订单类型不一致！")
					submitFlag = false;
					$(this).val("");
				}
				submitFlag = true;
			},"json");
		}
	});
	
	$("#btn_submit").click(function(){
		$.post(application.fullPath + "rest/allTake/savePreInfoOrder",{tele_type:"M165",jsessionid:$("#jsessionid").val(),oper_no:$("#oper_no").val(),pass_word:$("#pass_word").val(),device_number:$("#acc_nbr_GSM").val(),cust_id:$("#id_number").val(),activity_id:"无"},function(data){
			if(data.args.result==true){
				var orderId = data.args.order_id;
				var attrdata = {
						"tele_type":"M165",
						"customer_name":$("#customer_name").val()!=""?$("#customer_name").val():"未记录",
						"id_number":$("#id_number").val()!=""?$("#id_number").val():"未记录",
						"acc_nbr_GSM":$("#acc_nbr_GSM").val()!=""?$("#acc_nbr_GSM").val():"未记录",
						"acc_nbr_WCDMA":$("#acc_nbr_WCDMA").val()!=""?$("#acc_nbr_WCDMA").val():"未记录",
						"order_type":$("div[name='dingdan_type_select'][class='radio_checked']").attr("value")!=""?$("div[name='dingdan_type_select'][class='radio_checked']").attr("value"):"未记录",
						"product_type":$("div[name='chanpin_type_select'][class='radio_checked']").attr("value")!=""?$("div[name='chanpin_type_select'][class='radio_checked']").attr("value"):"未记录",
						"first_month_fee":$("div[name='first_type_select'][class='radio_checked']").attr("value")!=""?$("div[name='first_type_select'][class='radio_checked']").attr("value"):"未记录",
						"order_belong":$("div[name='orderbelong_type_select'][class='radio_checked']").attr("value")!=""?$("div[name='orderbelong_type_select'][class='radio_checked']").attr("value"):"未记录",
						"pay_no_165":$("#pay_no_165").val()!=""?$("#pay_no_165").val():"未记录",
						"account_code":$("#account_code").val()!=""?$("#account_code").val():"未记录",
						"access_mode":$("#access_mode").val()!=""?$("#access_mode").val():"未记录",
						"speed_level":$("#speed_level").val()!=""?$("#speed_level").val():"未记录",
						"address_name":$("#address_name").val()!=""?$("#address_name").val():"未记录",
						"activity_name":$("#activity_name").val()!=""?$("#activity_name").val():"未记录",
						"fee_info":$("#fee_info").val()!=""?$("#fee_info").val():"未记录",
						"share_product":$("#share_product").val()!=""?$("#share_product").val():"未记录",
						"2G_add_package":$("#2G_add_package").val()!=""?$("#2G_add_package").val():"未记录",
						"3G_add_package":$("#3G_add_package").val()!=""?$("#3G_add_package").val():"未记录",
						"lan_add_package":$("#lan_add_package").val()!=""?$("#lan_add_package").val():"未记录",
						"fixed_line_package":$("#fixed_line_package").val()!=""?$("#fixed_line_package").val():"未记录",
						"backup_info":$("#backup_info").val()!=""?$("#backup_info").val():"未记录"
				};
				var src;
				if(submitFlag){
					$.post(application.fullPath + "rest/allTake/preInfoOrderAttrUpdate",{order_id:orderId,jsessionid:$("#jsessionid").val(),order_json:JSON.stringify(attrdata)},function(data){
						if(data.args.result==true){
							$.alert("保存成功！");
						}
					},"json");
				}
			}
		},"json");
	});
});