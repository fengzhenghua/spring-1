	var pay_type = '';
	var order_id = '';
	
$(document).ready(function() {
	//给pay_type赋值，如果订单属性表中pay_type不为空则有值，否则默认
	var init_pay_type = $("#init_pay_type_hidden").val();
	if(init_pay_type != null && init_pay_type !=""){
		$("#pay_type").val(init_pay_type);
		//如果是pos刷卡，则显示刷卡按钮
		if(init_pay_type == "18"){
			$("#PosPay").show();
			$("#liushuihao").show();
			$("#trace_compare").show();
		}else {
			$("#PosPay").hide();
			$("#liushuihao").hide();
			$("#trace_compare").hide();
		}
	}
	//加载时支付方式隐藏字段赋值
	pay_type = $("#pay_type") .val();
	$("#pay_type_hidden").val(pay_type);
	if(pay_type == '10'){
		//初始化页面移除确定按钮的点击事件
		$('#queding').removeClass('ok');
		$('#queding').attr('class','free');
	}
	/*判断选择的支付方式，并出现相应的支付方式输入框*/
	$("#pay_type").change(function(){
		/*获取支付方式*/
		pay_type = $("#pay_type").val();
		$("#pay_type_hidden").val(pay_type);
		/*当支付方式值为10时，表示现金支付，参考号，支票，转账等输入框均不显示*/
		if(pay_type == '10'){
			$("#PosPay").hide();
			$("#liushuihao").hide();
			$("#trace_compare").hide();
			$('#queding').removeClass('ok');
			$('#queding').attr('class','free');
		}if(pay_type == '18'){/*当支付方式值为18时，表示POS刷卡，参考号显示，支票，转账等输入框不显示*/
			$("#PosPay").show();
			$("#liushuihao").show();
			$("#trace_compare").show();
			$('#queding').removeClass('free');
			$('#queding').attr('class','ok');
		}
		/*当支付方式值为11时，表示现金支票，参考号不显示，支票信息显示，转账信息输入框不显示*/
		if(pay_type == '11'){
			//相应支付方式进行隐藏和显示相关信息
		}
		/*当支付方式值为12时，表示转账支票，参考号和支票信息不显示，转账信息输入框显示*/
		if(pay_type == '12'){
			//相应支付方式进行隐藏和显示相关信息
		}
	});
	
	/**
	 * 取消按钮
	 */
	$("#goBack").click(function(){
		var url = application.fullPath + "authority/orderFee/orderFeeIndex";
		$.confirm('确认取消吗？',
		'确认提示',
		function cancel_ok() {
			window.location.href = url;
		},
		function cancel_cancel() {
			
		});
	});

});

function cleanDefaultValue(obj){
	var id= $(obj).attr("id");
	if($("#"+id).val() == 0){
		$("#"+id).val('');
	}
}

/**
*减免费用计算
*/
function countFee(obj){
	var id= $(obj).attr("id");
	if($("#"+id).val()==null||$("#"+id).val()==''){
		$("#"+id).val('0');
		return false;
	}
	//当前减免费用  如果新的减免费用和原来的减免费用一致，不处理
	var cur_dis_fee = parseFloat($(obj).attr("value"));
	//新的减免费用
	var new_dis_fee = $("#"+id).val();
	var reg = /^\d+(\.\d{1,2})?$/;
	if(!reg.test(new_dis_fee)){  
		$("#"+id).val(cur_dis_fee);
		$("#"+id).focus();
		$.alert("请输入最多两位小数的数字!");  
		return false;
    }  
	var current_dis_fee = '';
	if(isNaN(cur_dis_fee)){
		current_dis_fee=0;
	}else{
		current_dis_fee = cur_dis_fee;
	}
	
	if(new_dis_fee==current_dis_fee){
		return false;
	}
	
	//
	var order_attr_id = $(obj).attr("key");
	//新费用Id
	var new_fee_id="new_fee_"+order_attr_id;
	var new_fee_none_id="new_fee_none_"+order_attr_id;
	
	//原来费用Id
	var old_fee_id="old_fee_"+order_attr_id;
	
	//单个总费用
	var old_fee =  parseFloat($("#"+old_fee_id).text());
	
	if(isNaN(old_fee)){
		return false;
	}
	if(new_dis_fee>old_fee){
		$("#"+id).val(current_dis_fee);
		
		$.alert("减免费用不能大于本总费用！");
		return false;
	}
	//单个优惠后的费用
	var start_new_fee =  $("#"+new_fee_id).text();
	if(start_new_fee==null||start_new_fee==''){
		return false;
	}
	//优惠后的总费用
	var new_fee =old_fee-new_dis_fee;
	
	var discount_totalCosts = $("#discount_totalCosts_input").val();
	if(new_fee<start_new_fee){
		discount_totalCosts = discount_totalCosts-(start_new_fee-new_fee);
	}else if(new_fee>start_new_fee){
		discount_totalCosts= parseFloat(discount_totalCosts) + parseFloat(new_fee-start_new_fee);
	}
	
	$("#"+new_fee_id).text((parseFloat(new_fee)).toFixed(2));
	$("#"+new_fee_none_id).attr("value",(parseFloat(new_fee)).toFixed(2));
	//把最新的减免费用保存
	$("#"+id).attr("value",(parseFloat(new_dis_fee)).toFixed(2));
	
	//计算总费用
	$("#discount_totalCosts_input").attr("value",(parseFloat(discount_totalCosts)).toFixed(2));
	$("#discount_totalCosts").html("总计："+(parseFloat(discount_totalCosts)).toFixed(2));
	
	//总减免金额 discount_fee
	$("#discount_fee").attr("value",(parseFloat($("#total_fee").val()-discount_totalCosts)).toFixed(2));
}

/**
*提交
*/
function orderSub(){
	var pay_type = $("#pay_type").val();
	var order_flag = $("#order_flag").val();
	//现金支付
	if(pay_type == '10'){
		if(order_flag=='1'){
			$("#updateOrderFeeSub").submit();	
		}
		else{
		//更新订单支付状态
		updateOrderFeeSub();
		}
	}
	//pos刷卡支付
	if(pay_type == '18'){
		//判断是否支付成功
		var pay_flag_hidden = $("#pay_flag_hidden").val();
		if("0"==pay_flag_hidden){
			$.alert("请先支付！");
			return;
		}
        if(order_flag=='1'){
        	$("#updateOrderFeeSub").submit();
		}
		else{
		//更新订单支付状态
		updateOrderFeeSub();
		}
		
	}
	
}

//初始化pos机
function initPos(){
	umsocx = document.getElementById("umsocxId");
}

//传统银行卡接口
function umsTransBasic()
{
	/*------------------------------------------------------------------------*/
	var result;
	umsocx.operId = document.getElementById("oper_no").value;
	umsocx.transtype = '02';
	var amount = $("#discount_totalCosts_input").val();
	amount = amount.replace(".", "");
	amount = lpad(amount,12-amount.length);
	umsocx.amount = amount;
	result = umsocx.umsbankBasic();
	if(0 == result)
	{
		document.getElementById("rspCode").value = umsocx.rspCode;;
		document.getElementById("bankCode").value = umsocx.bankCode;
		document.getElementById("cardNo").value = umsocx.cardNo;
		document.getElementById("expr").value = umsocx.expr;
		document.getElementById("batch").value = umsocx.batch;
		document.getElementById("trace").value = umsocx.trace;
		document.getElementById("rspAmount").value = umsocx.amount;
		document.getElementById("rspChin").value = umsocx.rspChin;
		document.getElementById("mchtId").value = umsocx.mchtId;
		document.getElementById("termId").value = umsocx.termId;
		document.getElementById("reference").value = umsocx.reference;
		document.getElementById("transDate").value = umsocx.transDate;
		document.getElementById("transTime").value = umsocx.transTime;
		document.getElementById("authNo").value = umsocx.authNo;
		document.getElementById("settleDate").value = umsocx.settleDate;
		document.getElementById("memo").value = umsocx.memo;
		if(umsocx.rspChin !="交易成功"){
			$.alert("设备初始化失败")
			return;
		}
	}else{
		document.getElementById("rspCode").value = umsocx.rspCode;
		document.getElementById("rspChin").value = umsocx.rspChin;
		$.alert("rspCode  "+umsocx.rspCode+"umsocx  "+umsocx.rspChin);
		return;
	}
	var data={
			"order_id":$("#order_id").text()
		  };
    	var URL = application.fullPath + "authority/orderFee/orderFeeJudge";

		$.ajax({
			type : "POST",
			url : URL,
			data : data,
			dataType:'json',
			waitMsg:"正在缴费，请稍等......",
			success:function(message){
				var jsonObj=eval(message);
				var payflag;
				if(jsonObj.type=='success'){
					payflag = message.args.pay_flag;
				}
				if(jsonObj.type=='error'){
					var dialog=$.dialog({
						   title:'提示',//提示title
						   content:'缴费失败，请稍后再试',//显示的内容，支持html格式。
						   buttons:[{id:'btn_ok',text:'确定',
								   onClick:function(){
									   dialog.close();
									   //window.location.href = application.fullPath + "authority/orderFee/orderFeeIndex";
								   }//点击时候回调函数
						   }]
					   });
				}
				if(jsonObj.type=='success'&&payflag=='Y'){
					var dialog=$.dialog({
						   title:'提示',//提示title
						   content:'订单已缴费，请进行确认。',//显示的内容，支持html格式。
						   buttons:[{id:'btn_ok',text:'确定',
								   onClick:function(){
									   dialog.close();
									   window.location.href = application.fullPath + "authority/orderFee/orderFeeIndex";
								   }//点击时候回调函数
						   }]
					   });
				}
				
				if(payflag=='P' && jsonObj.type=='success'){
						var data={
								"order_id":$("#order_id").text(),
								"oper_id":$("oper_no").val(),
								"trans_type":"02",
								"rspCode":document.getElementById("rspCode").value,
								"bank_code":document.getElementById("bankCode").value,
								"card_no":document.getElementById("cardNo").value,
								"expr":document.getElementById("expr").value,
								"batch":document.getElementById("batch").value,
								"trace":document.getElementById("trace").value,
								"rsp_amount":document.getElementById("rspAmount").value,
								"charge":$("#discount_totalCosts_input").val(),
								"rsp_chin":document.getElementById("rspChin").value,
								"mcht_id":document.getElementById("mchtId").value,
								"term_id":document.getElementById("termId").value,
								"reference":document.getElementById("reference").value,
								"trans_date":document.getElementById("transDate").value,
								"trans_time":document.getElementById("transTime").value,
								"auth_no":document.getElementById("authNo").value,
								"settle_date":document.getElementById("settleDate").value,
								"memo":document.getElementById("memo").value
							  };
						var URL = application.fullPath + "authority/orderFee/queryOrderFeeProsessingPos";
				
						$.ajax({
							type : "POST",
							async : false,
							url : URL,
							data : data,
							dataType:'json',
							contentType: "application/x-www-form-urlencoded; charset=utf-8",
							success:function(message){
								var jsonObj=eval(message);
								if(jsonObj.type=='success'){
									$("#pay_flag_hidden").val("1");
									$('#queding').removeClass('ok');
									$('#queding').attr('class','free');
									document.getElementById("pay_type").disable=false;
									$("#trace_compare").val(document.getElementById("reference").value);
									$('#PosPay').removeAttr("onclick");
									 var dialog=$.dialog({
										   title:'提示',//提示title
										   content:'缴费成功',//显示的内容，支持html格式。
										   buttons:[{id:'btn_ok',text:'确定',
												   onClick:function(){
													   dialog.close();
													   //window.location.href = application.fullPath + "authority/orderFee/orderFeeIndex";
												   }//点击时候回调函数
										   }]
									   });
								}
							}
						});
				}
			}
		});
}

function updateOrderFeeSub(){
	var data={
		"order_id":$("#order_id").text(),
		"start_time":$("#start_time").val(),
		"end_time":$("#end_time").val(),
		"cust_name":$("#cust_name").val(),
		"trade_no":$("#trace_compare").val(),
		"acc_nbr":$("#acc_nbr").text()
	};
    var URL = application.fullPath + "authority/orderFee/updateOrderFeeSub";
    $.ajax({
		type : "POST",
		url : URL,
		data : data,
		dataType:'json',
		waitMsg:"请稍等......",
		success:function(message){
			var jsonObj=eval(message);
			if(jsonObj.type=='success'){
				var start_time = $("#start_time").val();
				var end_time = $("#end_time").val();
				var cust_name = $("#cust_name").val();
				var btn_search_flag = "1";
				 var dialog=$.dialog({
					   title:'提示',//提示title
					   content:'恭喜您，缴费成功',//显示的内容，支持html格式。
					   buttons:[{id:'btn_ok',text:'确定',
							   onClick:function(){
								   dialog.close();
								   window.location.href = application.fullPath + "authority/orderFee/orderFeeIndex?start_time="+start_time+"&end_time="+end_time+"&cust_name="+cust_name+"&btn_search_flag="+btn_search_flag;
							   }//点击时候回调函数
					   }]
				   });
				
			}
		},
    	error:function(message){
    		var dialog=$.dialog({
				   title:'提示',//提示title
				   content:'非常抱歉，缴费失败',//显示的内容，支持html格式。
				   buttons:[{id:'btn_ok',text:'确定',
						   onClick:function(){
							   dialog.close();
//							   window.location.href = application.fullPath + "authority/orderFee/orderFeeIndex?start_time="+start_time+"&end_time="+end_time+"&cust_name="+cust_name+"&btn_search_flag="+btn_search_flag;
						   }//点击时候回调函数
				   }]
			   });
		}
    });
}

function lpad(str,length){
	for (var j=0;j<length;j++) {
		str = "0" + str;
	}
	return str;
}
