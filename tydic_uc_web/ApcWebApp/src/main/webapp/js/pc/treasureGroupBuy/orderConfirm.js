var apc_rest_host = "";
var accept_no = "";
var receive_name = "";
var receive_phone = "";
var receive_address = "";
var oper_no = "";//工号
var good_num_flag = "";//是否允许选靓号
var selected_number = "";//选择的号码
var tb_account = "";//淘宝账号
var ap_id = "";
var tariff_id = "";
var total_fee = 0;
var fee_info=[];//费用信息
var dev_list = [];//可选发展人
var devListIdx = 0;
var ap_type = "";
var reward_oper_no="";//即时激励发展人工号
var pay_type = "";//支付类型
var province_code = "";
var province_name = "";
var city_code = "";
var city_name = "";
var area_code = "";
var area_name = "";
var ap_order_id = "";//触点订单号
var advancePay = "0";//号码预存金额
var uoc_serv_order_no = "";//中台服务订单
var uoc_sale_order_no = "";//中台销售订单
var timersearch = null;
var callingSearch = false; //是否在执行轮询操作
var havePay = false;//是否已经支付
var needPayOnline = false;//是否需要在线支付
var ywb_id = [];//业务包
var accept_system = "";//受理系统
var callback_url = "";//回调地址
var tariff_info = {
		"tele_type":"4G",
		"good_num_flag":"",
		"tariff_id":"",
		"tariff_name":"",
		"tariff_desc":"",
		"product_id":"",
		"activity_id":""
};
var gztInfo = {
		"addr":"",
		"birthday":"",
		"certId":"",
		"certName":"",
		"nation":"",
		"sex":""
};
var wxPublicQrCode="";

$(document).ready(function () {
	//初始化页面参数
	initPage();
	
	/*$('#addressChoice').hide();
	$('#saveBtn').hide();
	$('#cancleBtn').hide();*/
	
	
	
	//修改收货人信息
	$('#modifyBtn').on('click',modifyReceiverInfo);
	
	//保存收货人信息
	$('#saveBtn').on('click',saveReceiverInfo);
	
	//取消修改收货人信息
	$('#cancleBtn').on('click',cancleChangeReceiverInfo);
	
	//设置城市下拉框信息
	$('#provinceid').unbind().bind("change",getCityInfo);
	
	//设置区域下拉框信息
	$('#cityid').unbind().bind("change",getAreaInfo);
	
	//选择号码
	$('#selectNum').unbind("click").on('click',selectNumber);
	
	//提交订单
	$("#next_flow_step").unbind().bind("click", confirmOrder);
	
	$.modal('#selectNumber', {
        width:1000,
        height: 300
    });
	
	//查询号码(输入号码后0-6位查询)
	$("#searchNumber").unbind("click").bind("click", searchNumber);
	
	//用户协议弹出框显示
	$("#btn_service_protocol").click(function(){
		$("#service_protocol_txt").show();
		$("#main-page").hide();
	});
	
	//协议弹出框的返回
	$("#protocol_back").unbind("click").bind("click", function () {
		$("#service_protocol_txt").hide();
		$("#main-page").show();
	});
	
	//设置微信支付弹框大小
	$.modal('#WXPay', {
        width:400,
        height:800
    });
	
	//微信支付弹框内返回按钮
	$("#wx_btn").unbind("click").bind("click", function (){
		clearInterval(timersearch);
		callingSearch =false;
		$("#WXPay").hide();
	});
	
	//获取触点信息
	getApInfo();
	//获取触点属性
	getApAttrInfo();

	//关注二维码下方关闭按钮
	$("#close_all_page").unbind("click").bind("click", function () {
		$("#mask").hide();
		$("#qr_code_pop").hide();
	});
});

//初始化页面参数
function initPage(){
	ap_id = $('#ap_id').val();
	tariff_id = $('#tariff_id').val();
	apc_rest_host = $('#apc_rest_host').val();
	tb_account = $('#tb_account').val();
	accept_no = $('#accept_no').val();
	receive_name = $('#receive_name').val();
	receive_phone = $('#receive_phone').val();
	receive_address = $('#receive_address').val();
	if(isNullUtil("0",receive_address)){
		$('#addressDefault').hide();
		$('#addressChoice').show();
		$('#modifyBtn').hide();
		$('#saveBtn').show();
		$('#cancleBtn').show();
		$('#receiveAddress').attr("disabled",false);
		getAllProvinceInfo();
	}else{
		$('#addressDefault').show();
		$('#addressChoice').hide();
		$('#modifyBtn').show();
		$('#saveBtn').hide();
		$('#cancleBtn').hide();
		$('#receiveAddress').attr("disabled",true);
	}
	
	$('#idName').val(receive_name);
	$('#connectPhone').val(receive_phone);
	$('#receiveAddress').val(receive_address);
	
};

//获取触点属性
function getApAttrInfo(){
	var data = {
			"ap_id":ap_id
	};
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/operateApAttrRest/queryApAttrByRedis",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if(data.respCode=="0000"){
				pay_type = data.args.apAttrInfo.pay_type;
				callback_url = data.args.apAttrInfo.callback_url;
				accept_system = data.args.apAttrInfo.accept_system;
				wxPublicQrCode=isNullUtil("1",data.args.apAttrInfo.wx_public_qr_code);
			}
			else{
				$("#next_flow_step").unbind("click");
				$("#next_flow_step").attr("class","next_disabled next_blank");
				$.message.error(data.content);
				return;
			}
		},
		error: function(data) {
			$.message.error('校验Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
};

//获取微信支付二维码
function GetWxQr() {
	
	var get_wx_code_total_fee = total_fee + parseFloat(advancePay);
	var dataJson={
			oper_no:oper_no,
			order_id :ap_order_id,
			tele_type:tariff_info.tele_type,
			body:"微信开户（1元流量王）",
			totalFee:get_wx_code_total_fee, //费用的单位为元
			deviceInfo:selected_number,
			mcht_flag:ap_id
	};
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoPayServiceRest/getWxQrCode",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:dataJson,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				$("#PIC_QR").attr('src',("data:image/jpeg;base64,"+data.content)); //二维码图片
				$('#fee_all').html(get_wx_code_total_fee);
				$('#wx_btn').text("重新下单");
				$('#WXPay').show();
				//启动支付结果轮询
				timersearch = setInterval(searchStatus, 1000);
			} else {
				if (data.content=="该订单已经支付！" || data.content=="生成二维码失败！该订单已支付") {
					//订单已支付，直接创建中台订单
					createUocOrder();
				}else{
					$.dialog.alert(data.content,"","确定");
				}
			}
		},
		error: function(data) {
			$.message.error('校验Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
};

//支付结果轮询
function searchStatus() {
	
	if (callingSearch) {
		return;
	}
	
	callingSearch = true;
	
	var dataJson={
			oper_no:oper_no,           	
			order_id:ap_order_id
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
				var have_pay_total = total_fee + parseFloat(advancePay);
				$("#tip1").html("支付成功！");
	        	$("#tip2").html("");
	        	$('#fee_all').html(have_pay_total);
	        	havePay = true;
				pay_flag="Y";
				clearInterval(timersearch);
				$("#PIC_QR").attr('src','../../images/mobile/oppoCard/payment_ok.png');
				$('#wx_btn').text("返回");
				//创建中台订单
				createUocOrder();
			}
		},
		error: function(data) {
//			DIALOG_UTIL.showTypeDialog("error", "支付结果查询Ajax请求失败!");
		},
		complete:function(){
			callingSearch = false;
			//DIALOG_UTIL.hideDialog("", "loading");
		}
	});
};

//获取触点信息
function getApInfo(){
	var data = {
			"ap_id":ap_id
	};
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/ap/queryApMsg",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				tariffInfoList = data.args.tariff_info_list;
				//业务包处理
				var ywb_info_list=data.args.ywb_info_list
				var ywb_id_string = isNullUtil("1",$('#ywb_id').val());
				//不为空
				if(!isNullUtil("0",ywb_id_string)){
					var ywbInfo=JSON.parse(ywb_id_string);
					//是否是数组
					if(jQuery.isArray(ywbInfo)&&ywbInfo.length>0){
						for(var i=0;i<ywbInfo.length;i++){
							for(var j=0;j<ywb_info_list.length;j++){
								if(ywbInfo[i]==ywb_info_list[j].ywb_code){
									var pack_cod={
											"pack_code":ywb_info_list[j].ywb_code,
											"element_id":ywb_info_list[j].element_id,
											"element_type":ywb_info_list[j].element_type
									};
									ywb_id.push(pack_cod);
								}
							}
						}
					}
				}
				
				var isExist = "no";
				for(var i=0;i<tariffInfoList.length;i++){
					if(tariffInfoList[i].tariff_id == tariff_id){
						isExist = "yes";
						tariff_info.good_num_flag = tariffInfoList[i].good_num_flag;
						tariff_info.tariff_id = tariffInfoList[i].tariff_id;
						tariff_info.tariff_name = tariffInfoList[i].tariff_name;
						tariff_info.tariff_desc = tariffInfoList[i].tariff_desc;
						tariff_info.product_id = tariffInfoList[i].product_id;
						tariff_info.activity_id = tariffInfoList[i].activity_id;
						oper_no = data.args.ap_info.bind_oper;
						dev_list = data.args.developer_list;
						ap_type = data.args.ap_info.ap_type;
						
						var fee_list=tariffInfoList[i].fee_list;
						for(var i=0;i<fee_list.length;i++){
							if(fee_list[i].fee_item_type!="apc"){
								total_fee=total_fee+parseFloat(fee_list[i].total_fee);
								var fee_result={
										"fee_id":fee_list[i].fee_item_code,
										"fee_category":fee_list[i].fee_item_type,
										"orig_fee":parseFloat(fee_list[i].total_fee)*100+"",
										"relief_fee":"0.00",
										"relief_result":"无",
										"real_fee":parseFloat(fee_list[i].total_fee)*100+"",
										"fee_des":fee_list[i].fee_item_name
								};
								fee_info.push(fee_result);
							}
						}
						break;
					}
				}
				if(isExist == "yes"){
				}
				else{
					$("#next_flow_step").unbind("click");
					$("#next_flow_step").attr("class","next_disabled next_blank");
					$.message.error('传入的资费id与配置不匹配');
					return;
				}
			} else {
				$("#next_flow_step").unbind("click");
				$("#next_flow_step").attr("class","next_disabled next_blank");
				$.message.error(data.content);
				return;
			}
		},
		error: function(data) {
			$.message.error('ERROR');
		},
		complete:function(){
			$.loading.hide();
		}
	});
};

//选择号码
function selectNumber(){
	$("#searchNumberCondition").val("");
	searchNumber();
};

//查询号码
function searchNumber(){
	var fuzzyQuery = $("#searchNumberCondition").val();

	var dataJson={
			"oper_no"     : oper_no,
			"fuzzy_query" : fuzzyQuery,
			"page_info"   : "autoTerminal",
			"tele_type"   : tariff_info.tele_type,
			"ser_type"    : "1",
			"good_num_flag":tariff_info.good_num_flag
	};

	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoNumberServiceRest/oppoSelectNumber",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:dataJson,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				var list = data.args.number_list;
				if (list=="" || list==null || list.length == 0) {
					$.dialog.alert("没有符合条件的号码！","","确定");
					return;
				}
				else{
					var numStr = "";
					$('#number').empty();
					for(var i = 0; i < list.length ;i++){
						if(list[i].class_id == "9"){
							numStr += '<li class="num-list l bh num-lh"><span class="num-span1">'+list[i].acc_nbr+'</span></li>';
						}
						else{
							numStr += '<li class="num-list l bh" style="position:relative;"><i style="display:inline-block;width:22px;height:22px;position:absolute;top:0;left:0;background:url(../../images/mobile/oppoCard/liang.png) no-repeat;background-size:22px 22px;"></i><span class="num-span1">'+list[i].acc_nbr+'</span><br/><span>靓号预存：<span class="num-span2">'+list[i].acc_nbr_fee+'</span></span></li>';
						}
					}
					$('#number').append(numStr);

					$('.num-list').on('click',function(){
						$this = $(this);
						$this.removeClass('bh').addClass('br');
						$this.siblings().removeClass('br').addClass('bh');

						if(confirm("确定预占该号码吗？"))
						 {
							$('#selectNumber').hide();
							var numberTemp = $this.find('.num-span1').html();
							var accNbrFeeTemp = $this.find('.num-span2').html();
							if(accNbrFeeTemp == undefined){
								accNbrFeeTemp = "0";
							}
							numberOccupy(numberTemp,accNbrFeeTemp);
						 }
					});
					$('#selectNumber').show();
				}
			}
			else {
				$.message.error(data.content);
			}
		},
		error: function(data) {
		},
		complete: function(){
			$.loading.hide();
		}
	});

};

//号码预占
function numberOccupy(number,accNbrFee) {
	var dataJson={
			"oper_no"      : oper_no,
			"acc_nbr"      : number,
			"ser_type"     : "1",
			"tele_type"    : tariff_info.tele_type,
			"occupiedFlag" : "1"  //号码状态标识：0 不预占；1 预占；2 预订（未付费）；3 预订（付费）；4 释放资源
	};

	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoNumberServiceRest/oppoOccupyNumber",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:dataJson,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				selected_number = number;
				advancePay = accNbrFee;
				$('#selected_number').html(number);
			} else {
				$.message.error(data.content);
			}
		},
		error: function(data) {
			$.message.error('号码预占Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
};

//修改收货人信息
function modifyReceiverInfo(){

	$('#addressDefault').hide();
	$('#addressChoice').show();
	$('#modifyBtn').hide();
	$('#saveBtn').show();
	$('#cancleBtn').show();
	
	$('#receiveAddress').attr("disabled",false);
	
	getAllProvinceInfo();
};

//保存收货人信息
function saveReceiverInfo(){
	var province_options =$("#provinceid option:selected");
	var city_options =$("#cityid option:selected");
	var area_options =$("#countyid option:selected");
	
	province_name = province_options.text();
	province_code = province_options.val();
	city_name = city_options.text();
	city_code = city_options.val();
	area_name = area_options.text();
	area_code = area_options.val();
	var input_address = $('#connect_addr').val();
	
	if(province_name == "请选择"){
		$.dialog.alert("请选择省份！","","确定");
		return;
	}
	if(city_name == "请选择"){
		$.dialog.alert("请选择城市！","","确定");
		return;
	}
	if(area_name == "请选择"){
		$.dialog.alert("请选择区域！","","确定");
		return;
	}
	if(input_address == ""){
		$.dialog.alert("请输入具体配送地址！","","确定");
		return;
	}
	
	//dj
	if($('.i-hide').html() !== ''){
		$('.i-hide').html(province_name+city_name+area_name+input_address);
		var w = $('.i-hide').css('width');
		$('#receiveAddress').css('width',w);
		//$('#receiveAddress').val(province_name+city_name+area_name+input_address);
	}
	//djend
	
	$('#receiveAddress').val(province_name+city_name+area_name+input_address);
	$('#addressDefault').show();
	$('#addressChoice').hide();
	
	$('#modifyBtn').show();
	$('#saveBtn').hide();
	$('#cancleBtn').hide();
	
	$('#receiveAddress').attr("disabled",true);
};

//获取全国省份信息
function getAllProvinceInfo(){
	var data = {};
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoCommonServiceRest/getAllProvinceInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				var province_info_list = data.args.provinceInfoList;
				for(var i=0;i<province_info_list.length;i++){
					$('#provinceid').append('<option value="'+province_info_list[i].area_code+'">'+province_info_list[i].area_name+'</option>');
				}
			}
			else {
				$.message.error('获取省份信息失败!');
			}
		},
		error: function(data) {
			$.message.error('获取省份信息Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
};

//获取城市信息
function getCityInfo(){
	var options =$("#provinceid option:selected");
	province_code = options.val();
	province_name = options.text();

	var data = {
		"parent_area_code":province_code
	};

	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoCommonServiceRest/getAreaInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				$("#cityid").empty();
				$('#cityid').append('<option value="">请选择</option>');
				$("#countyid").empty();
				$('#countyid').append('<option value="">请选择</option>');
				var area_info_list = data.args.area_info;
				for(var i=0;i<area_info_list.length;i++){
					$('#cityid').append('<option value="'+area_info_list[i].area_code+'">'+area_info_list[i].area_name+'</option>');
				}
			}
			else {
				$.message.error('获取城市信息失败!');
			}
		},
		error: function(data) {
			$.message.error('获取城市信息Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
};

//获取区域信息
function getAreaInfo(){
	var options =$("#cityid option:selected");
	city_code = options.val();
	city_name = options.text();

	var data = {
			"parent_area_code":city_code
		};

	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoCommonServiceRest/getAreaInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				$("#countyid").empty();
				$('#countyid').append('<option value="">请选择</option>');
				var area_info_list = data.args.area_info;
				for(var i=0;i<area_info_list.length;i++){
					$('#countyid').append('<option value="'+area_info_list[i].area_code+'">'+area_info_list[i].area_name+'</option>');
				}
			}
			else {
				$.message.error('获取区域信息失败！');
			}
		},
		error: function(data) {
			$.message.error('获取区域信息Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
};

//取消修改收货人信息
function cancleChangeReceiverInfo(){
	receive_address = $('#receive_address').val();
	
	$('#receiveAddress').val(receive_address);
	
	$('#modifyBtn').show();
	$('#saveBtn').hide();
	$('#cancleBtn').hide();
	
	$('#addressDefault').show();
	$('#addressChoice').hide();
	
	$('#receiveName').attr("disabled",true);
	$('#receivePhone').attr("disabled",true);
	$('#receiveAddress').attr("disabled",true);
};

//提交订单
function confirmOrder() {

	var customer_name=$("#idName").val();
	if(customer_name == null || customer_name == ""){
		$.dialog.alert("请输入姓名！","","确定");
		return;
	}

	var id_number=$("#idNumber").val();
	if(!checkIdcard(id_number)){
		$.dialog.alert("请输入正确的身份证号码！","","确定");
		return;
	}

	var contact_phone=$("#connectPhone").val();
	if(contact_phone == null || contact_phone == ""){
		$.dialog.alert("请输入联系电话！","","确定");
		return;
	}

	var connect_addr = $("#receiveAddress").val();
	if(connect_addr == null || connect_addr == ""){
		$.dialog.alert("请输入配送地址！","","确定");
		return;
	}

	if(selected_number == null || selected_number == ""){
		$.dialog.alert("请先选择一个号码！","","确定");
		return;
	}

	//进行身份证开卡数量检验
	checkCertNoNums();
};

//身份证开卡数量检验
function checkCertNoNums(){
	var certId = $("#idNumber").val();
	var dataJson={
			"oper_no" : oper_no,
			"ap_id": ap_id,
			"cert_no": certId
	};

	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoCheckServiceRest/checkCertNumsForAp",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:dataJson,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在处理");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				if(data.args.checkFlag!=0){
					$.message.error("抱歉，此身份证开卡数量超过限制，不允许再受理！");
				}else{
					//黑名单一证五户校验
					custCheck();
				}
			} else {
				$.message.error(data.content);
			}
		},
		error: function(data) {
			$.message.error("校验Ajax请求失败!");
		},
		complete:function(){
			$.loading.hide();
		}
	});
};

//黑名单一证五户校验
function custCheck(){
	var certId = $("#idNumber").val();
	var certName = $("#idName").val();
	var flag="0";
	var dataJson={
			"oper_no" : oper_no,
			"tele_type":tariff_info.tele_type,
			"id_number":certId,
			"id_type":"02",
			"customer_name":certName,
			"device_number":"",
			"checkType":"0",
			"flag":flag//是否需要校验的标志 1需要校验  0不需要校验
	};

	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoCheckServiceRest/checkCustInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:dataJson,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				if(flag=="0"){
					//进行国政通校验
					checkGZT();
				}else if(flag=="1"){
					var rsp_info=JSON.parse(data.args.json_info);
					if(rsp_info.code!="200"){
						DIALOG_UTIL.showTypeDialog("error", rsp_info.json_info.detail);
					}else{
						//进行国政通校验
						checkGZT();
					}
				}
			} else {
				$.message.error('黑名单一证五户校验失败!');
			}
		},
		error: function(data) {
			$.message.error('黑名单一证五户校验Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
};

//国政通校验
function checkGZT(){
	var certId = $("#idNumber").val();
	var certName = $("#idName").val();
	var flag="1";
	var dataJson={
			"certId":certId,
			"certName":certName,
			"flag":flag//是否需要校验标志，1  需要   0不需要
	};

	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoCheckServiceRest/checkGZT",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:dataJson,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				if(flag=="0"){
					//是否需要在线支付
					whetherNeedPayOnline();
				}else if(flag=="1"){
					var custInfo=JSON.parse(data.args.json_info).json_info.rspmsg;
					if(custInfo.code!=null){
						$.dialog.alert(custInfo.detail,"","确定");
					}else{
							gztInfo.addr=custInfo.addr;
							gztInfo.birthday=custInfo.birthday;
							gztInfo.certId=custInfo.certId;
							gztInfo.certName=custInfo.certName;
							gztInfo.nation=custInfo.nation;
							gztInfo.sex=custInfo.sex;
							//是否需要在线支付
							whetherNeedPayOnline();
					}
				}
			} else {
				$.message.error(data.content);
			}
		},
		error: function(data) {
			$.message.error('国政通校验Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
};

//是否需要在线支付
function whetherNeedPayOnline(){
	var needPayTotal = total_fee + parseFloat(advancePay);
	//需要在线付款
	if(needPayTotal>0){
		needPayOnline = true;
	}
	else{
		needPayOnline = false;
	}
	createApOrder();
};

//创建触点订单
function createApOrder(){
	var connect_addr = $("#receiveAddress").val();
	
	var data = {
			"oper_no":oper_no,
			"tele_type":tariff_info.tele_type,
			"pay_type":pay_type,
			"acc_nbr":selected_number,
			"recom_person_id":dev_list[devListIdx].dev_code,
			"recom_person_name":dev_list[devListIdx].dev_name,
			"product_id":tariff_info.product_id,
			"cert_address":gztInfo.addr,
			"customer_name":$("#idName").val(),
			"customer_sex":gztInfo.sex,
			"cert_num":$("#idNumber").val(),
			"cert_type":"02",
			"receive_name":$("#idName").val(),
			"receive_phone":$("#connectPhone").val(),
			"receive_province":province_code,
			"receive_city":city_code,
			"receive_area":area_code,
			"receive_address":connect_addr,
			"tb_account":tb_account,
			"accept_system":accept_system,
			"callback_url":callback_url
	};
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoOrderServiceRest/createApOrder",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				ap_order_id = data.args.ap_order_id;
				if(needPayOnline == true && havePay == false && pay_type == "20"){
					GetWxQr();
				}
				else{
					createUocOrder();
				}
			} else {
				$.message.error('创建触点订单失败');
			}
		},
		error: function(data) {
			$.message.error('创建订单Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
};

//在中台创建订单
function createUocOrder() {

	var connect_addr = $("#receiveAddress").val();
	var goodNumFee = parseFloat(advancePay)*100;
	var cod_charge = "";
	if(needPayOnline == true && havePay == false && pay_type == "20"){
		cod_charge = total_fee*100+goodNumFee;
	}
	var total_fee_uoc=goodNumFee;
	if(goodNumFee>0){
		for(var i=0;i<fee_info.length;i++){
			total_fee_uoc=total_fee_uoc+parseFloat(fee_info[i].real_fee);
			if(fee_info[i].orig_fee=="100000"){
				fee_info[i].orig_fee=parseFloat(fee_info[i].orig_fee)+goodNumFee+"";
				fee_info[i].real_fee=parseFloat(fee_info[i].real_fee)+goodNumFee+"";
			}
			else{
				if(i==fee_info.length-1){
					var fee_result={
							"fee_id":"100000",
							"fee_category":"2",
							"orig_fee":goodNumFee.toString(),
							"relief_fee":"0.00",
							"relief_result":"无",
							"real_fee":goodNumFee.toString(),
							"fee_des":"营业普通预存款"
					};
					fee_info.push(fee_result);
				}
			}
		}
	}
	
	var all_fee_info=JSON.stringify(fee_info);
	
	if(accept_no == ""){
		accept_no = ap_order_id;
	}
	var dataJson={
			"oper_no":oper_no,
		    "order_id":ap_order_id,
		    "accept_no":accept_no,
		    "pay_type":pay_type,
			"serial_number":selected_number,
			"recom_person_id":dev_list[devListIdx].dev_code,//可选发展人id
			"recom_person_name":dev_list[devListIdx].dev_name,//可选发展人名字
			"first_mon_bill_mode":"01",
			"product_id":tariff_info.product_id,
			"cert_address":gztInfo.addr,
			"cert_expire_date":"",
			"customer_name":$("#idName").val(),
			"customer_sex":gztInfo.sex,
			"cert_num":$("#idNumber").val(),
			"cert_type":"02",
			"contact_address":connect_addr,
			"contact_phone":$("#connectPhone").val(),
			"receive_name":$("#idName").val(),
			"receive_phone":$("#connectPhone").val(),
			"receive_province":province_code,
			"receive_city":city_code,
			"receive_area":area_code,
			"receive_address":connect_addr,
			"total_fee":total_fee_uoc,
			"oper_code":"openHn01",
			"cod_charge":cod_charge,
			"activity_id":tariff_info.activity_id,
			"reward_oper_no":reward_oper_no,
			"ap_type":ap_type,
			"fee_info":all_fee_info,
			"tb_account":tb_account,
			"ywb_id":JSON.stringify(ywb_id),
			"callback_url":callback_url,
			"accept_system":accept_system
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
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				uoc_serv_order_no=data.args.serv_order_no;
				uoc_sale_order_no=data.args.sale_order_no;
				$('#WXPay').hide();
				$.message.error('创建订单成功');
				$("#next_flow_step").unbind("click");
				$("#next_flow_step").attr("class","next_disabled next_blank");
	        	//更新触点订单
				updateApOrder();
			} else {
				$('#WXPay').hide();
				$("#next_flow_step").html("重新下单");
				$('#idName').attr("disabled",true);
				$('#idNumber').attr("disabled",true);
				$('#connectPhone').attr("disabled",true);
				$('#selectNum').unbind("click");
				$('#modifyBtn').unbind("click");
				$.message.error('创建订单失败');
			}
		},
		error: function(data) {
			$('#WXPay').hide();
			$("#next_flow_step").html("重新下单");
			$.message.error('创建订单Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});

};

//更新触点订单
function updateApOrder(){
	var data = {
			"serv_order_no":uoc_serv_order_no,
			"sale_order_no":uoc_sale_order_no,
			"order_id":ap_order_id
	};
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoOrderServiceRest/updateOppoOrderInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
					//$.dialog.alert("电话卡将在订单信息审核完成寄出，感谢使用！","","确定");
				$("#mask").css("height",$(document).height());     
			    $("#mask").css("width",$(document).width());     
			    $("#mask").show();
				$("#order_success_tip").html("下单成功，订单号为："+uoc_serv_order_no);
				$("#PUBLIC_QR").attr('src',(wxPublicQrCode)); //二维码图片
				$("#qr_code_pop").show();
				$("#qr_code_pop").css("top","15px");
			} else {
				$.message.error(data.content);
			}
		},
		error: function(data) {
			$.message.error('更新订单属性表Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
};

//验证手动输入的身份证号
function checkIdcard(idcard){
	if (idcard == "") {
		return false;
	}

	var area={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}

	var idcard,Y,JYM;
	var S,M;
	var idcard_array = new Array();
	idcard_array = idcard.split("");
	//地区检验
	if(area[parseInt(idcard.substr(0,2))]==null) {
		return false;
	}
	//身份号码位数及格式检验
	switch(idcard.length){
	case 15:
	if ( (parseInt(idcard.substr(6,2))+1900) % 4 == 0 || ((parseInt(idcard.substr(6,2))+1900) % 100 == 0 && (parseInt(idcard.substr(6,2))+1900) % 4 == 0 )){
		ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//测试出生日期的合法性
	} else {
		ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//测试出生日期的合法性
	}
	if(ereg.test(idcard)) {
		return false;
	} else {
		return false;
	}
	break;
	case 18:
	//18位身份号码检测
	//出生日期的合法性检查
	//闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
	//平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
	if ( parseInt(idcard.substr(6,4)) % 4 == 0 || (parseInt(idcard.substr(6,4)) % 100 == 0 && parseInt(idcard.substr(6,4))%4 == 0 )){
		ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//闰年出生日期的合法性正则表达式
	} else {
		ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//平年出生日期的合法性正则表达式
	}
	if(ereg.test(idcard)){//测试出生日期的合法性
		//计算校验位
		S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
		+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9
		+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10
		+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5
		+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8
		+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4
		+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2
		+ parseInt(idcard_array[7]) * 1
		+ parseInt(idcard_array[8]) * 6
		+ parseInt(idcard_array[9]) * 3 ;
		Y = S % 11;
		M = "F";
		JYM = "10X98765432";
		M = JYM.substr(Y,1);//判断校验位
		if(M == idcard_array[17]) {
			return true; //检测ID的校验位
		} else {
			return false;
		}
	} else {
		return false;
	}
	break;
	default:
		return false;
	break;
	}
};

