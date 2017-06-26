$(document).ready(function() {
	$("#query").click(function() {
		selectLog();
	});
});

function selectLog() {
	var operId = $('#operId').val();
	var tele_type = $.trim($("#tele_type").val());
	var start_time = $.trim($("#date_begin").val());
	var end_time = $.trim($("#date_end").val());
	var device_number = $.trim($("#device_number").val());
	if (device_number == "请输入手机号码") {
		 device_number = "";
	}
	if (tele_type == "电信类型"){
		tele_type = "";
	}
	var data= {
		"oper_id":operId,
		"tele_type":tele_type,
		"create_date":start_time,
		"end_date":end_time,
		"device_number":device_number
	};
	var GetURl = application.fullPath + "/authority/customer/queryUnifiedLogQuery";
	$.ajax({
		url: GetURl,
		type: 'post',
		waitMsg: "查询订单",
		data : data,
		success: function(message) {
			$('#paylogTable>tbody>tr').remove();
			var logpayList= message.args.log_pay_cs_vo;
			var htmlNew = '';
			if(logpayList != null || logpayList.length != 0){
				for (var i = 0; i <logpayList.length; i++) {
					device = logpayList[i];
					var discountFee = 0;
					var totalFee = 0;
					var payedFee = 0;
					if(null==device.discount_fee){
						discountFee = 0;
		            }else{
		            	discountFee =device.discount_fee;
		            }
					if(null==device.total_fee){
						totalFee = 0;
					}else{
						totalFee = device.total_fee;
					}
					if(null==device.payed_fee){
						payedFee = 0;
					}else{
						payedFee = device.payed_fee;
					}
					
					var html ='<tr>'
						+'<td class="text_center">'+device.pay_cs_id+'</td>'
			            +'<td class="text_center">'+device.tele_type+'</td>'
			            +'<td class="text_center">'+device.device_number+'</td>'
			            +'<td class="text_center">'+device.code_name+'</td>'
			            +'<td class="text_center">'+totalFee+'</td>'
						+'<td class="text_center">'+discountFee+'</td>'
			            +'<td class="text_center">'+payedFee+'</td>'
			            +'<td class="text_center">'+device.oper_id+'</td>'
			            +'<td class="text_center">'+device.create_date+'</td>'
			            +'</tr>';
						htmlNew += html;
				}
			}
			
			$('#paylogTable>tbody').append(htmlNew);
		},
		error:function(message){
			$.alert("系统失败，请稍后再试。");
		}
	});
}