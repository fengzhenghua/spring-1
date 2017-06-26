var num_flag = false;
var cust_belong = "";
var order_id = "";
var pay_no = "";
var user_info = "";
var tele_type="";
var province_code="";

$(document).ready(function() {
	province_code=$("#province_code").val();//省份标识
    $("#btn_submit").unbind().bind("click", confirmCharge);
    $("#fee_detail").click(function(){
    	getBalance();
    });
    $("#numCheck").click(function(){
    	unmCheck();
    });
    $("#charge_phone").focus();
    var paySelectList = $('#pay_type_select_list div[name=pay_type_select]');
    paySelectEvn(paySelectList);
   
});

function confirmCharge() {
	
	var charge_phone = $("#charge_phone").val();
	var charge_value = $("#charge_value").val();
	
	if (!num_flag) {
		$.alert("请先查询号码！");
		return;
	}
	
	if (charge_value=="") {
		$.alert("请输入缴费金额！");
		return;
	}

	
	var chargetip = "缴费号码：<span class=\"red\">"+charge_phone+"</span><br>缴费金额：<span class=\"red\">" +charge_value+ "元</span><br>是否继续？";
	$.confirm(chargetip,'请确认',function() {
			payTypeConfirm();
		},	function () {});	
};

function getBalance(){
	var charge_phone = $("#charge_phone").val();
	if(user_info == null || user_info == ""){
		$.alert("请先查询号码~");
		return;
	}
	var myDate = new Date()	
	var year=myDate.getFullYear()+"";    //获取完整的年份(4位,1970-????)
	var month=myDate.getMonth()+1+"";       //获取当前月份(0-11,0代表1月)
	var date=myDate.getDate()+"";        //获取当前日(1-31)
	if(month.length == 1){
		month =  "0"+month;
	}
	var YM = year+month;
	var YMD = year+month+date;

	var oweCharge= "";
	if(user_info.current_balance > 0){
		oweCharge = "0";
	}else{
		oweCharge = user_info.current_balance;
	}
	$.ajax({
		url:application.fullPath + "authority/sale/getBalanceDetails",
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		sync:false,
		waitMsg:"余额查询中...",
		data:{
			"serialNumber" : charge_phone,
			"device" : charge_phone,
			"device_guishu" : user_info.device_guishu,
			"tele_type":user_info.tele_type,
			"order_id":order_id,
			"payNo":user_info.pay_no,
			"userNo":user_info.user_no,			
			"payMonth":YM,					
			"acctMonth":YM,			
			"oweCharge":oweCharge,//欠费
			"payDate":YM
		},
		success:function(message){
			if(message.type=="success") {				
				var fee_info = message.args.fee_info;
				var fee_details = message.args.fee_details;
				var bal_info = message.args.bal_info;
				
				//4G
				if(fee_info != null){
					showdiv('fee_info');					
					$("#rt_fee").html(fee_info[0].usedMoney);
					$("#ava_fee").html(fee_info[1].acctCurntBalance);
					$("#pre_store").html(fee_info[1].prepayAvailable);
					$("#rt_gant").html(fee_info[1].promAvailable);
					$("#his_arr").html(fee_info[2].totalFee);
					$("#share_li").hide();										
					$("#dedit_li").hide();
				}
				//2G3G
				if(bal_info != null){
					showdiv('fee_info');
					$("#rt_fee").html(bal_info[0].realChg);
					$("#ava_fee").html(user_info.current_balance);
					$("#pre_store").html(bal_info[1].prepayBalance);
					$("#share_fee").html(bal_info[1].imprestReserve);
					$("#rt_gant").html(bal_info[1].promBalance);
					$("#his_arr").html(bal_info[1].oweCharge);
					$("#dedit").html(bal_info[1].sumFineCharge);
				}
				//2G3G4G
				if(fee_details != null){
					showdiv('fee_info');
					$("#rt_fee").html(fee_details.rtcalls);
					$("#ava_fee").html(fee_details.currentbalance);
					$("#pre_store").html(fee_details.advbalance);
					$("#share_fee").html(fee_details.availadv_balance);
					$("#rt_gant").html(fee_details.availgrant);
					$("#his_arr").html(fee_details.historyowe);
					$("#dedit").html(fee_details.latefee);
				}
			}else{
				$.alert("获取余额失败:"+message.content);
			}
		},
		error:function(){
			$.alert("获取余额失败");
		}		
	});
}

function goCharge(charge_phone,charge_value) {
	
	//插入属性表
	var attrdata = {
			"tele_type":"ALL",
			"device_guishu":user_info.device_guishu,
			"order_sub_type":"10020",
			"acc_nbr":charge_phone,
			"customer_id":user_info.cust_name,
			"id_type":user_info.id_type,
			"id_number":user_info.id_number,
			"pay_type":"10",
			"real_fee":charge_value,
			"pay_no":pay_no,
			"share_flag":"1",
			"login_oper_no":$("#oper_no").val()
	};
	$.ajax({
		url:application.fullPath + "rest/orderInfo/orderInfoAttrUpdate",
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		sync:false,
		data:{
			jsessionid: $("#jsessionid").val(),
			"page_code":"0",
			"order_id":order_id,	
			"order_json":JSON.stringify(attrdata)
		},
		success:function(message){
			
		}
	});

	
	
	var URL = application.fullPath + "authority/sale/recvFee";
	var data1={
			order_id:order_id,
			device_number:charge_phone,
			pay_charge:charge_value,
			pay_no:user_info.pay_no
	};
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		data:data1,
		waitMsg:"缴费中，请稍候...",
		success:function(message){
			
			if(message.type=="success") {
				$.alert("恭喜，缴费成功！");
				
				num_flag = false;
				
				//记录FeeDetail
				callServiceCharge(charge_phone,charge_value);
				
				$("#charge_phone").val("");
				$("#charge_value").val("");
				$("#balance").html("");	
				$("#custname").html("");
				user_info = "";
				
			}
			else {
				$.alert("缴费失败！\n"+message.content);
			}
			
		},
		error:function(){
			$.alert("系统失败，请稍后再试。");
		}
	});
};
//查询号码姓名和余额
function unmCheck(){
	var phone = $("#charge_phone").val();
	var customer_name = "";
	var PhoneBalance = "";
	var rsp_code = "";
	var rsp_desc = "";
	var flag = false;
	if( phone!="" ){
		var phoneRegex = /^[0|1]\d{10}$/;//手机号验证
		 if (!phoneRegex.test(phone) || phone.length !=11 ) {
			 $.alert("请输入完整并正确的手机号码!");
			 flag=false;
	     }
		 else{
			 flag=true;
		 }
	}
	if( flag ){
		var data1 = {
			     jsessionid : $("#jsessionid").val(),
			     input_text : phone
		};
		
		$.ajax({
			url:application.fullPath + 'rest/saleOpen/getUserList',
			dataType:'json',
			type:'post',
			data:data1,
			waitMsg:"查询中..",
			success:function(message){	
				if(message.type == "error"){
					$.alert(message.content);
				}else{
					var cust_info_list = message.args.cust_info;
					if(cust_info_list == undefined || cust_info_list.length == 0){
						$.alert("查不到相应的用户信息");
					}
					else{
						$.each(cust_info_list, function(i, item) {
							if(item.id_type == "身份证"){
								item.id_type = "02";
							}
						});
						
						if(cust_info_list.length == 1){
							user_info = cust_info_list[0];
							if(province_code=='gx'){// 广西4G暂时限制
								if(user_info.device_guishu=='CBSS'){
									$.alert("4G号码目前不支持缴费");
									return;
								}
							}
							selectedNumber();
						}
						else{
							$("#tariff_list").html('');
							showdiv('cust_search');	
							$.each(cust_info_list, function(i, item) {
								var data = JSON.stringify(item);
								//YUN-773
								$("#tariff_list").append('<li class="list"><div class="left"><div class="left_lable">'
										+'<a class="tip" href="javascript:void(0)" >'
										+item.device_number + " - "+item.cust_name
										+'</div><div class="right_data">'								
									  +' <input name="cust_search_check" type="radio"  data=\''+data+'\'></input></div></div></li>');
							});
						}
					}
				}
			}
		});
	}
}

function createOrderId(){
	 var data1={
			jsessionid:$("#jsessionid").val(),
			device_guishu:user_info.device_guishu,
			tele_type: "ALL",
			phone_no: $("#charge_phone").val(),
			pay_no_165: user_info.pay_no_165,
			order_sub_type:"10020",
			customer_name:user_info.cust_name,
			trade_no:"",
		    product_id: "0", 
		    activity_id: "0",
		    id_type: user_info.id_type,
			id_number:user_info.id_number
		  }
	 var URL = application.fullPath + "rest/paymentinfo/create_order";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"订单生成中..",
			success:function(message){
				if(message.type == "error"){
					var dialog=$.dialog({
						   title:'提示',//提示title
						   content:'订单生成失败',//显示的内容，支持html格式。
						   buttons:[{id:'btn_ok',text:'重新生成订单',
								   onClick:function(){									   
									   dialog.close();
									   createOrderId();
								   }
									   
								}]
						});
				}else{
										
					$.each(message.args.nomal_payment_info, function(i, item) {
						var data =JSON.stringify(item);	
						order_id       = item.order_id;
					});
				}
			}
			
		});		  
};

function payTypeConfirm(){
	var payTypeRadio = $('#pay_type_select_list .radio_checked');
	
	if(payTypeRadio == undefined){
		$.alert("请选择支付方式!");
		return;
	}
	
	var charge_phone = $("#charge_phone").val();
	var charge_value = $("#charge_value").val();
	
	//现金
	if(payTypeRadio.attr("value") == "10"){
		if(province_code=='gx'&&tele_type=='4G'){// 广西4G走aop缴费接口
			 goAopCharge(charge_phone,charge_value);//aop缴费接口
		}else{
			if(province_code=='gx'&&tele_type!='4G'&&$("#iscolse").val()=='0'){
				goCharge(charge_phone,charge_value);//云化缴费接口
			
			}else{
				//记录FeeDetail
				callServiceCharge($("#charge_phone").val(),$("#charge_value").val());
				callPayCharge();//bss侧缴费
			}
		}
	}
	
}

function subEvn(event){
	if(event.keyCode==13) 
	{
		$("#btn_submit").click();
		return false;
	}
}

function paySelectEvn(paySelectList){
	var selectedIndex;
	paySelectList.each(
		function(){
	        $(this).unbind().bind("click",function(){
	        	selectedIndex = $(this);
	        	if(!num_flag)
	        	{
	        		$.alert("请先查询号码！");
	        		return;
	        	}
	        	paySelectList.each(
	        			function(){
	        				var a = selectedIndex.attr("value");
	        				var b = $(this).attr("value");
	        				if(a == b)
	        				{
	        					$(this).attr("class", "radio_checked");
	        				}else{
	        					$(this).attr("class", "radio");
	        				}
	        			}
	        	);
	        })
		}
	);

}


function callServiceCharge(charge_phone,charge_value) {
	$.post(application.fullPath + "rest/paymentinfo/create_order_insert_fee",{
		jsessionid: $("#jsessionid").val(),
		"pay_type":"10",
		"discount_fee":"0.00",
		"phone_no":charge_phone,
		"pay_no_165":user_info.pay_no_165,
		"orig_fee":charge_value,
		"real_fee":charge_value,
		"fee_type":"100",
		"trade_no":"",
		"real_fee_m165": 0, 
		"order_id":order_id
	},function(attrArgs){
		console.info("callServiceCharge更新成功");
	},"json");
	
}

function selectedNumber(){
	$("#balance").html(user_info.current_balance);	
	$("#custname").html(user_info.cust_name);
	tele_type=user_info.tele_type;
	num_flag = true;
	$("#charge_value").focus();
	//查询成功后生成订单id
	createOrderId();
}

function showdiv(popWinId) { 
	document.getElementById("bg_mask").style.display ='block';
	document.getElementById(popWinId).style.display ='block';

}
function hidediv(popWinId) {
	document.getElementById("bg_mask").style.display ='none';
	document.getElementById(popWinId).style.display ='none';
}

function custConfirm(){
	var radioChecked = $("#tariff_list input[name='cust_search_check']:checked");
	
	if(radioChecked.attr("data") == undefined){
		return;
	}
	
	user_info = JSON.parse(radioChecked.attr("data"));
	if(province_code=='gx'){// 广西4G暂时限制
		if(user_info.device_guishu=='CBSS'){
			$.alert("4G号码目前不支持缴费");
			return;
		}
	}
	selectedNumber();
	
	hidediv("cust_search");
}

//aop4G缴费接口
function goAopCharge(charge_phone,charge_value) {
	
	//插入属性表
	var attrdata = {
			"tele_type":"ALL",
			"device_guishu":user_info.device_guishu,
			"order_sub_type":"10020",
			"acc_nbr":charge_phone,
			"customer_id":user_info.cust_name,
			"id_type":user_info.id_type,
			"id_number":user_info.id_number,
			"pay_type":"10",
			"real_fee":charge_value,
			"pay_no":pay_no,
			"share_flag":"1",
			"login_oper_no":$("#oper_no").val()
	};
	$.ajax({
		url:application.fullPath + "rest/orderInfo/orderInfoAttrUpdate",
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		sync:false,
		data:{
			jsessionid: $("#jsessionid").val(),
			"page_code":"0",
			"order_id":order_id,	
			"order_json":JSON.stringify(attrdata)
		},
		success:function(message){
			
		}
	});

	
	
	var URL = application.fullPath + "authority/sale/doPayment";
	var data1={
			"order_id":order_id,
			"serialNumber":charge_phone,
			"fee":charge_value,
			"pay_type": "800"//现金收费
	};
	
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		data:data1,
		waitMsg:"缴费中，请稍候...",
		success:function(message){
			
			if(message.type=="success") {
				$.alert("恭喜，缴费成功！");
				
				num_flag = false;
				
				//记录FeeDetail
				callServiceCharge(charge_phone,charge_value);
				
				$("#charge_phone").val("");
				$("#charge_value").val("");
				$("#balance").html("");	
				$("#custname").html("");
				user_info = "";
				
			}
			else {
				$.alert("缴费失败！\n"+message.content);
			}
			
		},
		error:function(){
			alert("系统失败，请稍后再试。");
		}
	});
};

//bss侧缴费接口调用
function callPayCharge() {

	var URL = application.fullPath + "rest/paymentinfo/payment";
	var data1={
			"jsessionid":$("#jsessionid").val(),
			"device_guishu":user_info.device_guishu,
			"tele_type": tele_type,
			"pay_type": "10",			
			"phone_no": $("#charge_phone").val(),
			"real_fee": $("#charge_value").val(),			
			"order_sub_type":"10020",
			"customer_name":user_info.cust_name,			 	
		    "cust_email":"0",
		    "product_id":"0",
		    "order_id":order_id//订单号
		    
	};
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		data:data1,
		waitMsg:"请稍候...",
		success:function(message){
			
			if(message.type=="success") {
				updateOrderFee(message);				
			}
			else {
				$.alert("缴费失败！\n"+message.content);
			}
			
		},
		error:function(){
			alert("系统失败，请稍后再试。");
		}
	});
		
	
}

function updateOrderFee(data){
		
	var URL = application.fullPath + "rest/orderInfo/updateOrderFee";
	var data1={
			"jsessionid":$("#jsessionid").val(),           	
			"order_id":order_id,
			"pay_type":"10",
			"order_sub_type":"10020",
			"acc_nbr":$("#charge_phone").val()
	};
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		data:data1,
		waitMsg:"缴费中，请稍候...",
		success:function(message){
			if(message.type=="success") {
				$.alert("恭喜，缴费成功！");
				
				num_flag = false;
				$("#charge_phone").val("");
				$("#charge_value").val("");
				$("#balance").html("");	
				$("#custname").html("");
				user_info = "";
				
			}
			else {
				$.alert("缴费失败！\n"+message.content);
			}
			
			
		},
		error:function(){
			alert("系统失败，请稍后再试。");
		}
	});
	
		
	
}