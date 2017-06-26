var oper_no = "";
var ap_id = "";
var order_id = "";
var tele_type = "";
var product_id = "";
var product_name = "";
var market_price = "";
var acc_nbr_fee = "";//靓号预存
var product_desc = "";
var fee_code = "";
var fee_class = "";
var fee_name = "";
var apc_rest_host = "";
var selected_number = "";
var province_code = "";
var province_name = "";
var city_code = "";
var city_name = "";
var area_code = "";
var area_name = "";
var serv_order_no="";
var sale_order_no="";
var dev_list = [];
var devListIdx = 0;
var gztInfo={
		"addr":"",
		"birthday":"",
		"certId":"",
		"certName":"",
		"nation":"",
		"sex":""
};
//号码预存金额
var advancePay = "";
//费用明细
var fee_info;
var reward_oper_no="";
var ap_type="";
var activity_id="";
var good_num_flag = "";
var receive_area_flag = "";
var dev_code="";
var dev_name="";
var developer_list;
var wxPublicQrCode="";
var cod_bank_no="";
var pay_flag="货到付款未支付";
var callingSearch = false; //是否在执行轮询操作

$(document).ready(function () {
	//初始化触点订单属性参数
	initApAttrInfo();
	
	//获取省份编码
	queryProvinceCode();

	//设置城市下拉框信息
	$('#provinceid').unbind().bind("change",selectCityInfo);
	//设置区域下拉框信息
	$('#cityid').unbind().bind("change",selectAreaInfo);

	$('body').on('click', '.modal_layer .modal_top .modal_close, .modal_layer .bg_cover', function(e) {
		var $this = $(this);
		$this.parents('.modal_layer').hide();
	});

	//选择号码
	$('#selectNum').unbind("click").on('click',selectNumber);
	
	//查询号码(输入号码后0-6位查询)
	$("#searchNumber").unbind("click").bind("click", searchNumber);

	$.modal('#selectNumber', {
        width:1000,
        height: 300
    });

	//选择发展人
	$('#selectRecomPerson').on('click',selectRecomPerson);

	$("#check_contract").click(function(){
		if ($("#check_contract").attr("class").indexOf("icon-add") > -1  ){ //本来是勾选状态的，变取消勾选
			$("#check_contract").removeClass("icon-add");
			$("#check_contract").addClass("icon-point");
			$("#next_flow_step").addClass("next_disabled");
		}
		else {
			$("#check_contract").removeClass("icon-point");
			$("#check_contract").addClass("icon-add");
			$("#next_flow_step").removeClass("next_disabled");
		}
	});

	$("#btn_service_protocol").click(function(){
		$("#service_protocol_txt").show();

		$("#main-page").hide();

	});

	//提交订单
	$("#next_flow_step").unbind().bind("click", confirmOrder);

	//协议弹出框的返回按钮
	$("#protocol_back").unbind("click").bind("click", function () {

		$("#service_protocol_txt").hide();

		$("#main-page").show();
	});
	
	//关注二维码下方关闭按钮
	$("#close_all_page").unbind("click").bind("click", function () {
		$("#mask").hide();
		$("#qr_code_pop").hide();
		window.location.href = "ofrs.jsp?ap_id="+ap_id;
	});

});

//初始化触点订单属性参数
function initApAttrInfo(){
	apc_rest_host = $('#apc_rest_host').val();
	order_id = $('#order_id').val();
	
	var data = {
			"ap_order_id":order_id
	};
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoOrderServiceRest/getApOrderAttrInfo",
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
				ap_id = data.args.ap_id;
				oper_no = data.args.oper_no;
				tele_type = data.args.tele_type;
				product_id = data.args.product_id;
				product_name = data.args.product_name;
				market_price = data.args.market_price;
				fee_code = data.args.fee_code;
				fee_class = data.args.fee_class;
				fee_name = data.args.fee_name;
				fee_info = data.args.fee_info;
				developer_list = data.args.developer_list;
				reward_oper_no = data.args.reward_oper_no;
				ap_type = data.args.ap_type;
				activity_id = data.args.activity_id;
				good_num_flag = data.args.good_num_flag;
				receive_area_flag = data.args.receive_area_flag;
				//发展人
				var devInfoList=JSON.parse(developer_list);
				dev_code=devInfoList[0].developer_no;
				dev_name=devInfoList[0].developer_name;
				for(var i=0;i<devInfoList.length;i++){
					$('#dev_option').append('<option value="'+devInfoList[i].developer_no+'">'+devInfoList[i].developer_name+'</option>');
					//默认选中
					if(dev_code==devInfoList[i].developer_no){
						$("#dev_option   option[value='"+dev_code+"']").attr("selected",true);
					}
				}
				
				
				queryApAttrByRedis();
							
			} else {
				$.message.error('获取触点订单属性值');
			}
		},
		error: function(data) {
			$.message.error('获取触点订单属性值Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
};
/**
 * 在中台创建订单
 */
function createUocOrder() {

	var cod_charge="";
	var connect_addr = $("#connect_addr").val();
	var goodNumFee=parseFloat(advancePay)*100;
	var total_fee=goodNumFee;
	var feeInfo=[];
	//先剔除apc类型的费用
	var fee_info_all=JSON.parse(fee_info);
	for(var i=0;i<fee_info_all.length;i++){
		cod_charge=cod_charge+parseFloat(fee_info_all[i].real_fee);
		if(fee_info_all[i].fee_category!="apc"){
			feeInfo.push(fee_info_all[i]);
			total_fee=total_fee+parseFloat(fee_info_all[i].real_fee)
		}
	}
	
	var send_type=$("#send_type option:selected").val();
	if(send_type!="1"){
		//不是货到付款的
		cod_charge="";
		cod_bank_no="";
	}
	
	if(goodNumFee>0){
		for(var i=0;i<feeInfo.length;i++){
			if(feeInfo[i].fee_id=="100000"){
				feeInfo[i].orig_fee=parseFloat(feeInfo[i].orig_fee)+goodNumFee+"";
				feeInfo[i].real_fee=parseFloat(feeInfo[i].real_fee)+goodNumFee+"";
			}else{
				if(i==feeInfo.length-1){
					var fee_result={
							"fee_id":"100000",
							"fee_category":"2",
							"orig_fee":goodNumFee+"",
							"relief_fee":"0.00",
							"relief_result":"无",
							"real_fee":goodNumFee+"",
							"fee_des":"营业普通预存款"
					};
					feeInfo.push(fee_result);
				}
			}
		}
	}
	
	var all_fee_info=JSON.stringify(feeInfo);
	//发展人
	dev_code=$("#dev_option option:selected").val();
	dev_name=$("#dev_option option:selected").text();
	
	var dataJson={
			"oper_no":oper_no,
		    "order_id":order_id,
		    "pay_type":"20",//支付类型
			"serial_number":selected_number,
			"recom_person_id":dev_code,
			"recom_person_name":dev_name,
			"first_mon_bill_mode":"01",
			"product_id":product_id,
			"cert_address":gztInfo.addr,
			"cert_expire_date":"",
			"customer_name":$("#idName").val(),
			"customer_sex":gztInfo.sex,
			"cert_num":$("#id_number").val(),
			"cert_type":"02",
			"contact_address":province_name+city_name+area_name+connect_addr,
			"contact_phone":$("#connect_phone").val(),
			"receive_name":$("#idName").val(),
			"receive_phone":$("#connect_phone").val(),
			"receive_province":province_code,
			"receive_city":city_code,
			"receive_area":area_code,
			"receive_address":connect_addr,
			"total_fee":total_fee+"",
			"oper_code":"openHn01",
			"cod_charge":cod_charge,
			"activity_id":activity_id,
			"reward_oper_no":reward_oper_no,
			"ap_type":ap_type,
			"fee_info":all_fee_info,
			"cod_bank_no":cod_bank_no
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
			$.loading.show("正在加载");//dj
		},
		success: function(data) {
			if (data.respCode=="0000") {
				serv_order_no=data.args.serv_order_no;
				sale_order_no=data.args.sale_order_no;
	        	//更新订单属性表
	        	updateOrderAttr();
			} else {
				$.message.error('创建订单失败');//dj
			}
		},
		error: function(data) {
			$.message.error('创建订单Ajax请求失败!');//dj
		},
		complete:function(){
			$.loading.hide();//dj
		}
	});

};

/**
 * 更新订单属性表
 */
function updateOrderAttr(){

	var customer_name=$("#idName").val();
	var id_number=$("#id_number").val();
	var contact_phone=$("#connect_phone").val();
	var connect_addr = $("#connect_addr").val();

	var dataJson={
			"order_id":order_id,
			"rel_amount":market_price,
			"rec_amount":market_price,
			"fee_code":fee_code,
			"fee_class":fee_class,
			"fee_name":fee_name,
			"product_id":product_id,
			"product_name":product_name,
			"prepay_flag":"1",
			"tele_type":tele_type,
			"first_month_fee":"01",//套外
			"market_price":market_price,
			"fee_all":market_price,
			"pay_type":"20",
			"customer_name":customer_name,
			"id_number":id_number,
			"id_type":"02",
			"contact_phone":contact_phone,
			"contact_adress":province_name+city_name+area_name+connect_addr,
			"receive_name":customer_name,
			"receive_phone":contact_phone,
			"receive_address":province_name+city_name+area_name+connect_addr,
			"acc_nbr":selected_number,
			"receive_province":province_code,
			"receive_city":city_code,
			"receive_area":area_code,
			"pay_flag":pay_flag,
			"serv_order_no":serv_order_no,
			"sale_order_no":sale_order_no
	};
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoOrderServiceRest/updateOppoOrderInfo",
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
				//属于货到付款的申请流程至此完成，不需要在触点支付
					$("#next_flow_step").unbind("click");
					$("#next_flow_step").attr("class","next_disabled next_blank");
					
					$("#mask").css("height",$(document).height());     
				    $("#mask").css("width",$(document).width());     
				    $("#mask").show();
					$("#order_success_tip").html("下单成功，订单号为："+serv_order_no);
					$("#PUBLIC_QR").attr('src',(wxPublicQrCode)); //二维码图片
					$("#qr_code_pop").show();
					$("#qr_code_pop").css("top","15px");
					/*$.dialog.alert(
							"电话卡将在订单信息审核完成寄出，感谢使用！",
							"",
							"确定",
							function() {
								window.location.href = "ofrs.jsp?ap_id="+ap_id;
						    }
							);*/
			} else {
				/*alert(data.content);*/
				$.message.error(data.content);//dj
			}
		},
		error: function(data) {
			/*alert("更新订单属性表Ajax请求失败!");*/
			$.message.error('更新订单属性表Ajax请求失败!');//dj
		},
		complete:function(){
			$.loading.hide();//dj
		}
	});
};

/**
 * 选择可选发展人
 * */
function selectRecomPerson(){
	var dataJson={
			"jsessionId":oper_no,
			"developerNo":"",
			"developerName":""
	};

	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/optionalOper/getOptionalDeveInfo",
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
				if(data.args.code=="200"){
					dev_list=JSON.parse(data.args.json_info).developer_list;
					if (dev_list=="" || dev_list==null || dev_list.length == 0) {
						alert("没有查询到发展人信息！");
						return;
					}
					else{
						var personStr = "";
						$('#recommendPerson').empty();
						for(var i = 0 ;i<dev_list.length;i++){
							personStr += '<li class="person-list l bh" idx="'+ i +'">'+dev_list[i].dev_name+'</li>';
						}
						$('#recommendPerson').append(personStr);

						$('.person-list').on('click',function(){
							$this = $(this);
							$this.removeClass('bh').addClass('br');
							$this.siblings().removeClass('br').addClass('bh');

							if(confirm("确定选择该发展人吗？"))
							 {
								$('#selectRecommendPerson').hide();
								devListIdx = $this.attr('idx');
								$('#recom_person').text(dev_list[devListIdx].dev_name);
							 }
						});

						$('#selectRecommendPerson').show();
					}
				}
				else{

				}
			}
			else {
				/*alert("查询发展人信息失败");*/
				$.message.error('查询发展人信息失败!');//dj
			}
		},
		error: function(data) {
			$.message.error('查询发展人信息失败Ajax请求失败!');//dj
		},
		complete:function(){
			$.loading.hide();//dj
		}
	});
};

/**
 * 选择号码
 * */
function selectNumber(){
	$("#searchNumberCondition").val("");
	searchNumber();
};


/**
 * 查询号码
 * */
function searchNumber(){
	var fuzzyQuery = $("#searchNumberCondition").val();

	var dataJson={
			oper_no     : oper_no,
			fuzzy_query : fuzzyQuery,
			page_info   : "autoTerminal",
			tele_type   : tele_type,
			ser_type    : "1",
			good_num_flag:good_num_flag
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
							numStr += '<li class="num-list l bh"><span class="num-span1">'+list[i].acc_nbr+'</span><br/><span>靓号预存：<span class="num-span2">'+list[i].acc_nbr_fee+'</span></span></li>';
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

/**
 * 号码预占
 */
function numberOccupy(number,accNbrFee) {
	var dataJson={
			"oper_no"      : oper_no,
			"acc_nbr"      : number,
			"ser_type"     : "1",
			"tele_type"    : tele_type,
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
				$.message.error(data.content);//dj
			}
		},
		error: function(data) {
			$.message.error('号码预占Ajax请求失败!');//dj
		},
		complete:function(){
			$.loading.hide();
		}
	});
}

/**
 * 获取区域信息
 * */
function selectAreaInfo(){
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
				var area_info_list = data.args.area_info;
				for(var i=0;i<area_info_list.length;i++){
					$('#countyid').append('<option value="'+area_info_list[i].area_code+'">'+area_info_list[i].area_name+'</option>');
				}
			}
			else {
				$.message.error('获取区域信息失败！');//dj
			}
		},
		error: function(data) {
			$.message.error('获取区域信息Ajax请求失败!');//dj
		},
		complete:function(){
			$.loading.hide();
		}
	});

};


/**
 * 获取城市信息
 * */
function selectCityInfo(){
	var options =$("#provinceid option:selected");
	var province_code = options.val();
	var province_name = options.text();

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
				var area_info_list = data.args.area_info;
				for(var i=0;i<area_info_list.length;i++){
					$('#cityid').append('<option value="'+area_info_list[i].area_code+'">'+area_info_list[i].area_name+'</option>');
				}
			}
			else {
				$.message.error('获取城市信息失败!');//dj
			}
		},
		error: function(data) {
			$.message.error('获取城市信息Ajax请求失败!');//dj
		},
		complete:function(){
			$.loading.hide();//dj
		}
	});
};


/**
 * 获取省份
 */
function queryProvinceCode(){

	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoCommonServiceRest/getProvinceCode",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:{},
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				province_code=data.args.province_code_value;//编码
				var province=data.args.province_code;//英文缩写
				if(province=="cq"){
					$("#numAssignValue").html("重庆 重庆市");
					province_name = "重庆";
				}else if(province=="hn"){
					$("#numAssignValue").html("海南 海口市");
					province_name = "海南";
				}

				if(receive_area_flag=="1"){
					queryAllProvinceCode();
				}else{
					$('#provinceid').append('<option value="'+province_code+'">'+province_name+'</option>');
				}
			}
			else {
				$.message.error('获取省份失败!');//dj
			}
		},
		error: function(data) {
			$.message.error('获取省份信息Ajax请求失败!');//dj
		},
		complete:function(){
			$.loading.hide();//dj
		}
	});
};


/**
 * 获取全国省份
 * */
function queryAllProvinceCode(){
	var options =$("#provinceid option:selected");
	
	var data = {
		"parent_area_code":"86"
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
				var area_info_list = data.args.area_info;
				for(var i=0;i<area_info_list.length;i++){
					$('#provinceid').append('<option value="'+area_info_list[i].area_code+'">'+area_info_list[i].area_name+'</option>');
				}
			}
			else {
				$.message.error('获取省份信息失败!');//dj
			}
		},
		error: function(data) {
			$.message.error('获取省份信息Ajax请求失败!');//dj
		},
		complete:function(){
			$.loading.hide();//dj
		}
	});
};

/**
 * 黑名单一证五户校验
 */
function custCheck(){
	var certId = $("#id_number").val();
	var certName = $("#idName").val();
	var flag="0";
	var dataJson={
			"oper_no" : oper_no,
			"tele_type":tele_type,
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
				$.message.error('黑名单一证五户校验失败!');//dj
			}
		},
		error: function(data) {
			$.message.error('黑名单一证五户校验Ajax请求失败!');//dj
		},
		complete:function(){
			$.loading.hide();//dj
		}
	});
};

/**
 * 国政通校验
 */
function checkGZT(){
	var certId = $("#id_number").val();
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
					//货到付款直接先创建中台订单，后面不需要支付
					createUocOrder();
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
							//判断，根据发货方式走
							var send_type=$("#send_type option:selected").val();
							if(send_type=="1"){
								//货到付款
								createUocOrder();
							}else{
								//否则要先支付
								GetWxQr();
							}
					}
				}
			} else {
				$.message.error(data.content);//dj
			}
		},
		error: function(data) {
			$.message.error('国政通校验Ajax请求失败!');//dj
		},
		complete:function(){
			$.loading.hide();//dj
		}
	});
};

/**
 * 身份证开卡数量检验
 */
function checkCertNoNums(){
	var certId = $("#id_number").val();
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
					queryUserNumber();
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


/**
 * 一证五户校验
 */
function queryUserNumber(){
	var certId = $("#id_number").val();
	var certName = $("#idName").val();
	var dataJson={
			"oper_no" : oper_no,
			"certId":certId,
			"certName":certName
	};

	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoCheckServiceRest/queryUserNumber",
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
				if(data.args.USER_AMOUNT>=5){
					$.message.error("抱歉，此身份证已经超过五个用户，不允许再受理");
				}else{
					custCheck();
				}
			} else {
				$.message.error(data.content);
			}
		},
		error: function(data) {
			$.message.error("一证五户校验Ajax请求失败!");
		},
		complete:function(){
			$.loading.hide();
		}
	});
}


function confirmOrder() {

	var customer_name=$("#idName").val();
	if(customer_name == null || customer_name == ""){
		$.dialog.alert("请输入姓名！","","确定");
		return;
	}

	var id_number=$("#id_number").val();
	if(!checkIdcard(id_number)){
		$.dialog.alert("请输入正确的身份证号码！","","确定");
		return;
	}

	var contact_phone=$("#connect_phone").val();
	if(contact_phone == null || contact_phone == ""){
		$.dialog.alert("请输入联系电话！","","确定");
		return;
	}

	var province_option =$("#provinceid option:selected");
	province_code = province_option.val();
	province_name =  province_option.text();
	var city_option =$("#cityid option:selected");
	city_code = city_option.val();
	city_name = city_option.text();
	var area_option =$("#countyid option:selected");
	area_code = area_option.val();
	area_name = area_option.text();
	if(province_code == null || province_code == "" || city_code == null || city_code == "" || area_code == null || area_code == ""){
		$.dialog.alert("请选择所在地区！","","确定");
		return;
	}

	var connect_addr = $("#connect_addr").val();
	if(connect_addr == null || connect_addr == ""){
		$.dialog.alert("请输入配送地址！","","确定");
		return;
	}else{
		if(connect_addr.length<8){
			$.dialog.alert("配送详细地址至少要输入8个字！","","确定");
			return;
		}
	}

	if(selected_number == null || selected_number == ""){
		$.dialog.alert("请先选择一个号码！","","确定");
		return;
	}
	var send_type=$("#send_type option:selected").val();
	if(send_type==""){
		$.dialog.alert("请选择发货方式！","","确定");
		return;
	}

	var devCode=$("#dev_option option:selected").val();
	if(devCode==""){
		$.dialog.alert("请选择发展人！","","确定");
		return;
	}
	//进行客户资料校验
	checkCertNoNums();
};





//验证手动输入的身份证号
function checkIdcard(idcard){
	/*if (isNull(idcard)) {
		return false;
	}*/

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
}



//使用方法：参考 WebContent/jspTest/DevelopAPI.jsp
$.extend({
	// 消息框
	message: {
		/*
		 * 创建
		 * msg: 消息div对象
		 */
		_create: function(msg) {
			var msgBox = $('.message_box');
			if (msgBox.length > 0) {
				msgBox.append(msg);
			} else {
				var newMsgBox = $('<div class="message_box" belong="mask"></div>');
				newMsgBox.append(msg);
				$('body').append(newMsgBox);
			}
			this._destroy(msg);
		},
		/*
		 * 销毁
		 * msg: 消息div对象
		 */
		_destroy: function(msg) {
			var msgBox = $('.message_box');
			msg.delay(3000).animate({opacity: 'hide'}, 500, function() {
				msg.remove();
				if (msgBox.children('.message').length == 0) {
					msgBox.remove();
				}
			});
		},
		/*
		 * 信息消息框
		 * content: 显示的文本内容
		 */
		info: function(content) {
			if (!content || content == null || content == ''){content = 'Info...';}
			var msg = $('<div class="message message_info">' + content + '</div>');
			this._create(msg);
		},
		/*
		 * 成功消息框
		 * content: 显示的文本内容
		 */
		success: function(content) {
			if (!content || content == null || content == ''){content = 'Success...';}
			var msg = $('<div class="message message_success">' + content + '</div>');
			this._create(msg);
		},
		/*
		 * 警告消息框
		 * content: 显示的文本内容
		 */
		warning: function(content) {
			if (!content || content == null || content == ''){content = 'Warning...';}
			var msg = $('<div class="message message_warning">' + content + '</div>');
			this._create(msg);
		},
		/*
		 * 错误消息框
		 * content: 显示的文本内容
		 */
		error: function(content) {
			if (!content || content == null || content == ''){content = 'Error...';}
			var msg = $('<div class="message message_error">' + content + '</div>');
			this._create(msg);
		}
	},
	// 加载框
	loading: {
		// 同时调用加载框的数量
		_loadCount: 0,
		/*
		 * 显示
		 * msg: 可选，显示的文本内容
		 * timer: 可选(传此参数时第1个参数必须传值或者null)，毫秒，指定时间后自动关闭加载框
		 */
		show: function(msg, timer) {
			this._loadCount++;
			if ($('.loading_layer').length > 0) {
				return;
			}
			if (!msg){msg = '';}
			var ldgLayer = $('<div class="loading_layer" belong="mask">'
							+ '<div class="bg_cover"></div>'
							+ '<div class="loading_box">'
							+ '<span class="loading_align_bar"></span><i class="loading_img"></i><span class="loading_msg">' + msg + '</span>'
							+ '</div>'
							+ '</div>');
			$('body').append(ldgLayer);
			var reg = /^\d+(?=\.{0,1}\d+$|$)/; // 正数
			if (timer && reg.test(timer)) {
				setTimeout(this.hide, timer);
			}
		},
		/*
		 * 销毁
		 */
		hide: function() {
			this._loadCount--;
			if (this._loadCount > 0) {
				return;
			} else {this._loadCount = 0;}
			$('.loading_layer').remove();
		}
	},
	// 对话框
	dialog: {
		// 层叠顺序z-index
		_zIdx: 3000,
		/*
		 * 提示框
		 * content: 可选，显示的文本内容
		 * title: 可选(传此参数时第1个参数必须传值或者null)，显示的标题内容
		 * cnfBtnText: 可选(传此参数时前2个参数必须传值或者null)，改变[确定]按钮的文本内容
		 * cnfCallback: 可选(传此参数时前3个参数必须传值或者null)，[确定]按钮的回调函数
		 * xCallback: 可选(传此参数时前4个参数必须传值或者null)，[X]按钮的回调函数
		 */
		alert: function(content, title, cnfBtnText, cnfCallback, xCallback) {
			if (!content){content = '';}
			if (!title){title = '提示';}
			if (!cnfBtnText){cnfBtnText = '确 定';}
			var dlgLayer = $('<div class="dialog_layer" belong="mask">'
						+ '<div class="bg_cover" style="z-index:' + this._zIdx++ + '"></div>'
						+ '<div class="dialog_box" style="z-index:' + this._zIdx++ + '">'
						+ '<div class="dialog_top"><span class="dialog_title">' + title + '</span><span class="dialog_close">x</span></div>'
						+ '<div class="dialog_middle"><span class="middle_align_bar"></span><span class="dialog_content">' + content + '</span></div>'
						+ '<div class="dialog_bottom"><div class="btn btn_primary" name="cfBtn">' + cnfBtnText + '</div></div>'
						+ '</div>'
						+ '</div>');
			$('body').append(dlgLayer);
			// [确定]按钮绑定点击事件
			dlgLayer.find('[name="cfBtn"]').bind('click', function(e) {
				if (cnfCallback && cnfCallback != null) {
					cnfCallback();
				}
				dlgLayer.remove();
			});
			// [X]按钮绑定点击事件
			dlgLayer.find('.dialog_close').bind('click', function(e) {
				if (xCallback && xCallback != null) {
					xCallback();
				}
				dlgLayer.remove();
			});
		},
		/*
		 * 确认框
		 * content: 可选，显示的文本内容
		 * title: 可选(传此参数时第1个参数必须传值或者null)，显示的标题内容
		 * cnfBtnText: 可选(传此参数时前2个参数必须传值或者null)，改变[确定]按钮的文本内容
		 * cncBtnText: 可选(传此参数时前3个参数必须传值或者null)，改变[取消]按钮的文本内容
		 * cnfCallback: 可选(传此参数时前4个参数必须传值或者null)，[确定]按钮的回调函数
		 * cncCallback: 可选(传此参数时前5个参数必须传值或者null)，[取消]按钮的回调函数
		 * xCallback: 可选(传此参数时前6个参数必须传值或者null)，[X]按钮的回调函数
		 */
		confirm: function(content, title, cnfBtnText, cncBtnText, cnfCallback, cncCallback, xCallback, interval) {
			if (!content){content = '';}
			if (!title){title = '提示';}
			if (!cnfBtnText){cnfBtnText = '确 定';}
			if (!cncBtnText){cncBtnText = '取 消';}
			var dlgLayer = $('<div class="dialog_layer" belong="mask">'
						+ '<div class="bg_cover" style="z-index:' + this._zIdx++ + '"></div>'
						+ '<div class="dialog_box" style="z-index:' + this._zIdx++ + '">'
						+ '<div class="dialog_top"><span class="dialog_title">' + title + '</span><span class="dialog_close">x</span></div>'
						+ '<div class="dialog_middle"><span class="middle_align_bar"></span><span class="dialog_content">' + content + '</span></div>'
						+ '<div class="dialog_bottom"><div class="btn btn_primary" name="cfBtn">' + cnfBtnText + '</div><div class="btn btn_default" name="ccBtn">' + cncBtnText + '</div></div>'
						+ '</div>'
						+ '</div>');
			$('body').append(dlgLayer);
			// [确定]按钮绑定点击事件
			dlgLayer.find('[name="cfBtn"]').bind('click', function(e) {
				if (cnfCallback && cnfCallback != null) {
					cnfCallback();
				}
				dlgLayer.remove();
			});
			// [取消]按钮绑定点击事件
			dlgLayer.find('[name="ccBtn"]').bind('click', function(e) {
				if (cncCallback && cncCallback != null) {
					cncCallback();
				}
				dlgLayer.remove();
			});
			// [X]按钮绑定点击事件
			dlgLayer.find('.dialog_close').bind('click', function(e) {
				if (xCallback && xCallback != null) {
					xCallback();
				}
				dlgLayer.remove();
			});
			//interval不为空时模拟点击[确定]按钮绑定点击事件
			if (interval && interval != null) {
				setTimeout(function(){
					if(cnfCallback && cnfCallback != null){
						cnfCallback();
					}
					dlgLayer.remove();
				}, interval*1000);
			}
		}
	},
	// 模态框
	modal: function(id, option) {
		var $ele = $(id);
		if ($ele.length == 0) {
			$.message.error('没有找到DOM对象：' + id);
			return;
		}
		if (option && option != null) {
			if (option.width && $UTIL.dataType.isNumber(option.width)) {
				$ele.find('.modal_box').css('width', option.width + 'px');
				$ele.find('.modal_box').css('margin-left', '-' + (option.width / 2) + 'px');
			}
			if (option.height && $UTIL.dataType.isNumber(option.height)) {
				$ele.find('.modal_box').css('height', option.height + 'px');
				$ele.find('.modal_box').css('margin-top', '-' + (option.height / 2) + 'px');
			}
		}
	},
	//停止冒泡
	stopPropagation: function(e) {
		window.event? window.event.cancelBubble = true : e.stopPropagation();
	},
	// 阻止默认行为
	preventDefault: function(e) {
		window.event? window.event.returnValue = false : e.preventDefault();
	}
});

var $UTIL = {
	// 判断浏览器
	browser: {
		isIE: (/(msie\s|trident.*rv:)([\w.]+)/.exec(navigator.userAgent.toLowerCase()) ? true : false),
		isFirefox: (/(firefox)\/([\w.]+)/.exec(navigator.userAgent.toLowerCase()) ? true : false),
		isOpera: (/(opera).+version\/([\w.]+)/.exec(navigator.userAgent.toLowerCase()) ? true : false),
		isChrome: (/(chrome)\/([\w.]+)/.exec(navigator.userAgent.toLowerCase()) ? true : false),
		isSafari: (/version\/([\w.]+).*(safari)/.exec(navigator.userAgent.toLowerCase()) ? true : false)
	},
	// 判断数据类型
	dataType: {
		_type: Object.prototype.toString,
		isString: function(o) {
			return this._type.call(o) == '[object String]';
		},
		isNumber: function(o) {
			return this._type.call(o) == '[object Number]';
		},
		isBoolean: function(o) {
			return this._type.call(o) == '[object Boolean]';
		},
		isUndefined: function(o) {
			return this._type.call(o) == '[object Undefined]';
		},
		isNull: function(o) {
			return this._type.call(o) == '[object Null]';
		},
		isObject: function(o) {
			return this._type.call(o) == '[object Object]';
		},
		isArray: function(o) {
			return this._type.call(o) == '[object Array]';
		},
		isFunction: function(o) {
			return this._type.call(o) == '[object Function]';
		},
		test: function() {
			var obj = 'text';
			console.log('[String: ' + obj + '] >>   isString:' + this.isString(obj) + ', isNumber:' + this.isNumber(obj) + ', isBoolean:' + this.isBoolean(obj) + ', isUndefined:' + this.isUndefined(obj) + ', isNull:' + this.isNull(obj) + ', isObject:' + this.isObject(obj) + ', isArray:' + this.isArray(obj) + ', isFunction:' + this.isFunction(obj));
			obj = 123;
			console.log('[Number: ' + obj + '] >>   isString:' + this.isString(obj) + ', isNumber:' + this.isNumber(obj) + ', isBoolean:' + this.isBoolean(obj) + ', isUndefined:' + this.isUndefined(obj) + ', isNull:' + this.isNull(obj) + ', isObject:' + this.isObject(obj) + ', isArray:' + this.isArray(obj) + ', isFunction:' + this.isFunction(obj));
			obj = true;
			console.log('[Boolean: ' + obj + '] >>   isString:' + this.isString(obj) + ', isNumber:' + this.isNumber(obj) + ', isBoolean:' + this.isBoolean(obj) + ', isUndefined:' + this.isUndefined(obj) + ', isNull:' + this.isNull(obj) + ', isObject:' + this.isObject(obj) + ', isArray:' + this.isArray(obj) + ', isFunction:' + this.isFunction(obj));
			obj = undefined;
			console.log('[Undefined: ' + obj + '] >>   isString:' + this.isString(obj) + ', isNumber:' + this.isNumber(obj) + ', isBoolean:' + this.isBoolean(obj) + ', isUndefined:' + this.isUndefined(obj) + ', isNull:' + this.isNull(obj) + ', isobject:' + this.isObject(obj) + ', isArray:' + this.isArray(obj) + ', isFunction:' + this.isFunction(obj));
			obj = null;
			console.log('[Null: ' + obj + '] >>   isString:' + this.isString(obj) + ', isNumber:' + this.isNumber(obj) + ', isBoolean:' + this.isBoolean(obj) + ', isUndefined:' + this.isUndefined(obj) + ', isNull:' + this.isNull(obj) + ', isObject:' + this.isObject(obj) + ', isArray:' + this.isArray(obj) + ', isFunction:' + this.isFunction(obj));
			obj = {key:'value'};
			console.log('[Object: ' + obj + '] >>   isString:' + this.isString(obj) + ', isNumber:' + this.isNumber(obj) + ', isBoolean:' + this.isBoolean(obj) + ', isUndefined:' + this.isUndefined(obj) + ', isNull:' + this.isNull(obj) + ', isObject:' + this.isObject(obj) + ', isArray:' + this.isArray(obj) + ', isFunction:' + this.isFunction(obj));
			obj = [11, 22, 33];
			console.log('[Array: ' + obj + '] >>   isString:' + this.isString(obj) + ', isNumber:' + this.isNumber(obj) + ', isBoolean:' + this.isBoolean(obj) + ', isUndefined:' + this.isUndefined(obj) + ', isNull:' + this.isNull(obj) + ', isObject:' + this.isObject(obj) + ', isArray:' + this.isArray(obj) + ', isFunction:' + this.isFunction(obj));
			obj = function(){};
			console.log('[Function: ' + obj + '] >>   isString:' + this.isString(obj) + ', isNumber:' + this.isNumber(obj) + ', isBoolean:' + this.isBoolean(obj) + ', isUndefined:' + this.isUndefined(obj) + ', isNull:' + this.isNull(obj) + ', isObject:' + this.isObject(obj) + ', isArray:' + this.isArray(obj) + ', isFunction:' + this.isFunction(obj));
		}
	},
	// 时间工具
	timeTool: {
		// 获取时间差（格式：xx天 xx:xx:xx）
		getTimeDiff: function(start, end) {
			var time = '';
			var diff = parseInt((end.getTime() - start.getTime()) / 1000); // 相差x秒
			if (diff < 0) {
				diff = -1 * diff;
			}
			var day = parseInt(diff / (24 * 60 * 60)); // 天数
			time += day + '天 ';
			var hour = parseInt((diff % (24 * 60 * 60)) / (60 * 60)); // 时
			if (hour < 10) {
				time += '0' + hour + ':';
			} else {
				time += hour + ':';
			}
			var minute = parseInt((diff % (60 * 60)) / (60)); // 分
			if (minute < 10) {
				time += '0' + minute + ':';
			} else {
				time += minute + ':';
			}
			var second = parseInt(diff % 60); // 秒
			if (second < 10) {
				time += '0' + second;
			} else {
				time += second;
			}
			return time;
		}
	}
};
 

/**
 * 根据触点id从缓存中获取触点属性
 */
function queryApAttrByRedis(){
	var dataJson={
			"ap_id": ap_id
	};

	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/operateApAttrRest/queryApAttrByRedis",
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
				cod_bank_no=isNullUtil("1",data.args.apAttrInfo.cod_bank_no);
				wxPublicQrCode=isNullUtil("1",data.args.apAttrInfo.wx_public_qr_code);
			} else {
				$.message.error(data.content);
			}
		},
		error: function(data) {
			$.message.error("根据触点id获取触点属性Ajax请求失败!");
		},
		complete:function(){
			$.loading.hide();
		}
	});
}


/**
 * 获取微信支付二维码
 */
function GetWxQr() {
	var pay_fee_info=JSON.parse(fee_info);
	var pay_fee_total=parseFloat(advancePay);
	for(var i=0;i<pay_fee_info.length;i++){
		pay_fee_total=pay_fee_total+parseFloat(pay_fee_info[i].real_fee)/100
	}
	var dataJson={
			oper_no:oper_no,
			order_id :order_id,
			tele_type:tele_type,
			body:"微信开户",
			totalFee:pay_fee_total, //费用的单位为元
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
				$("#mask").css("height",$(document).height());     
			    $("#mask").css("width",$(document).width());     
			    $("#mask").show();
				$("#pay_qr").attr('src',(("data:image/jpeg;base64,"+data.content))); //二维码图片
				$('#pay_tips').html("您需要支付"+pay_fee_total+"元");
				$("#pay_qr_code_pop").show();
				$("#pay_qr_code_pop").css("top","15px");
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

/**
 * 支付结果轮询
 */
function searchStatus() {
	
	if (callingSearch) {
		return;
	}
	
	callingSearch = true;
	
	var dataJson={
			oper_no:oper_no,           	
			order_id:order_id
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
				pay_flag="Y";
				clearInterval(timersearch);
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


