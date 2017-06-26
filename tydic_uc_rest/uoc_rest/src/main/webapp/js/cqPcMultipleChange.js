// 新的订单
var order_id = '';
var pre_order_id = '';
var device_number = '';
var id_number = '';
var tele_type = '';
var oper_no = '';
var jsessionid = '';
var ofr_type = '';
var ywb_type = '';
var s_ywbInfo = [];
var devNoo = '';
var ofr_id = '';
var mobileNo = '';
var model= '';
var brand='';
var terminal_brand ='';
var phone_brand='';
var phone_model='';
var phone_color='';
var select_property_code ='';
var select_terminal_model_code ='';
var property_code = '';
var flag_select=false;
var fee_info=[];
var res_rele='';
var terminal_type='';
var cost='';
var sale_price='';
var machine_type_code='';
var machine_type_name='';
var resources_src_code='';
var org_device_brand_code='';
var fee_all =0;
var fee_info_json = [];
var product_info_json=[];
var resource_info_json=[];
var activity_info_json = [];
// 身份证
var defaultImagePath = '';
var id_card_image_path = '';
var pName = ""; // 姓名
var pSex = ""; // 性别
var pSexShow = "";
var pNation = ""; // 民族
var pBorn = ""; // 出生日期
var pAddress = ""; // 住址
var pCardNo = ""; // 身份证号
var pPolice = ""; // 签发机关
var pActivityLFrom = ""; // 起始日期
var pActivityLTo = ""; // 结束日期
var pDeviceNo = ""; //阅读器ID
var base64jpg = ""; // 照片
var readCardSucc = ""; // 是否读卡成功标示 1 成功 0 失败或者未读卡
var preMark = "";
var preProductId = "";
$(document).ready(function() {
	
	//活动类型选择change事件
	$("#activityType").change(function(){
		var activityType = $('#activityType option:selected').val();
		if(activityType=="3"){
			$("#chuanhao").css('display','none');
			$("#pinpai").css('display','none');
			$("#xinghao").css('display','none');
			$("#yanse").css('display','none');
		} else {
			$("#chuanhao").css('display','block');
			$("#pinpai").css('display','block');
			$("#xinghao").css('display','block');
			$("#yanse").css('display','block');
		}
	});
	
	$("#qryInfo").bind("click",function(){
		queryInfo();
	});
	
	$("#clearInput").bind("click",function(){
		cleanInputText();
	});
	
	$("#commitOrder").bind("click",function(){
		orderSubmit();
	});
	
	// table选中事件
	$('#deviceInfos>tbody').on('click', 'tr', function(){
		var $this = $(this);
		if ($this.hasClass('active')) {
			// 取消选中的预订单
			if ($('#preOrderInfos>tbody>tr').hasClass('active')) {
				$('#preOrderInfos>tbody>tr').find('input[type="checkbox"]').prop('checked', false);
				$('#preOrderInfos>tbody>tr').removeClass('active');
			}
			// 取消选中当前行
			$this.find('input[type="checkbox"]').prop('checked', false);
			$this.removeClass('active');
		} else {
			$this.find('input[type="checkbox"]').prop('checked', false); // 禁止点击checkbox直接勾选
			if ($this.attr('device_number') == '') { // 判断是否是设备（即是否有设备号码）
				return;
			} else if ($this.attr('device_number').indexOf("comcis") > 0) { // 判断是否是宽带设备
				$.alert('暂不支持宽带设备变更业务！');
				return;
			}
			// 是否生成订单
			var dialog = $.dialog({
				title: '提示',// 提示title
				content: '是否操作此设备，生成新的订单',// 显示的内容，支持html格式。
				buttons: [ {
					id: 'btn_ok',
					text: '确定',
					onClick: function() {
						dialog.close();
						// 选中当前行
						$this.siblings().find('input[type="checkbox"]').prop('checked', false);
						$this.find('input[type="checkbox"]').prop('checked', true);
						$this.siblings().removeClass('active');
						$this.addClass('active');
						// 保存参数
						device_number = $this.attr('device_number');
						tele_type = $this.attr('tele_type');
						$("#teleType").val($this.attr('tele_type'));
						$("#productId").val($this.attr('product_code'));
						// 生成订单
						createOrderId();
					}
				}, {
					id: 'btn_cancel',
					text: '取消',
					onClick: function() {
						dialog.close();
					}
				} ]
			});
		}
	});
	$('#preOrderInfos>tbody').on('click', 'tr', function(){
		var $this = $(this);
		if ($this.hasClass('active')) {
			// 取消选中当前行
			$this.find('input[type="checkbox"]').prop('checked', false);
			$this.removeClass('active');
		} else {
			$this.find('input[type="checkbox"]').prop('checked', false); // 禁止点击checkbox直接勾选
			if ($this.attr('pre_order_id') == '') { // 判断是否是设备（即是否有设备号码）
				return;
			}
			if (!$('#deviceInfos>tbody>tr').hasClass('active')) { // 判断是否已经选择一个设备
				$.alert('请先选择一个设备！');
				return;
			}
			// 选中当前行
			$this.siblings().find('input[type="checkbox"]').prop('checked', false);
			$this.find('input[type="checkbox"]').prop('checked', true);
			$this.siblings().removeClass('active');
			$this.addClass('active');
			// 查询预订单的产品信息
			queryPreOrderProduct($this.attr('pre_order_id'));
		}
	});
	
	// tab点击事件，展开对应的内容
	$('#tabs>li').bind('click', function(){
		var $this = $(this);
		var tabName = $this.attr('tab_name');
		if (!tabName) {
			$.alert('功能尚未开放！');
			return;
		} else if (tabName == 'productChng') {
			productChngShow();
		} else if (tabName == 'dinnerChng') {
			dinnerChngShow();
		}
		// 展开tab页
		openTab($this);
	});
	
	//活动搜索
	$("#searchActivity").bind("click",function(){
		var input_text = $("#ActivityInput").val()	
		showActivityInfo(input_text,"1");
	});
	
	//产品搜索
	$("#searchProduct").bind("click",function(){
		var input_text = $("#ProductInput").val()	
		showProductInfo(input_text,"1");
	});
	
	//套餐  业务包搜索
	$("#searchPkg").bind("click",function(){
		var input_text = $("#PkgInput").val()	
		busiPkgSel(input_text,"1");
	});
	
	//产品变更  业务包搜索
	$("#searchProdPkg").bind("click",function(){
		var input_text = $("#ProdPkgInput").val()	
		prodBusiPkgSel(input_text,"1");
	});
	
	/*
	 * 读身份证，设置初始值
	 */
	defaultImagePath = "";
	id_card_image_path = "";
	pName = ""; // 姓名
	pSex = ""; // 性别
	pSexShow = "";
	pNation = ""; // 民族
	pBorn = ""; // 出生日期
	pAddress = ""; // 住址
	pCardNo = ""; // 身份证号
	pPolice = ""; // 签发机关
	pActivityLFrom = ""; // 起始日期
	pActivityLTo = ""; // 结束日期
	pDeviceNo = ""; //阅读器ID
	base64jpg = ""; // 照片
	readCardSucc = ""; // 是否读卡成功标示 1 成功 0 失败或者未读卡
	
	// 加载读卡器
	load_card_mech();
	
	// 读卡结果
	var strReadResult = "";
	var readError = false;
	$("#btn_load").click("click", function() {
		var messageFlag = $("#messageFlag").val();
		if (messageFlag != '1') {
			$.alert("请选择读卡器!");
			return;
		}
		// 读卡未结束，不再次读卡
		if (strReadResult == null && !readError)
			return;
		strReadResult = null;
		// 调用空间的读卡方法
		try {
			strReadResult = CVR_IDCard.ReadCard();
		} catch (e) {
			$.alert("读卡错误,请检查您的控件或驱动是否正确安装最新版本");
			readError = true;
			return;
		}
		readError = false;
		// 读卡成功时，填充页面显示信息
		if (strReadResult == "0") {
			readCardSucc = "1"; // 读卡成功标示，传给上个页面
			fillForm();
			queryInfo();
		} else {
			readCardSucc = "0"; // 读卡成功标示，传给上个页面
			$.alert("读卡错误,请移动身份证,进行读卡!");//错误原因描述strReadResult
		}
	});
	
	$("#btn_load_test").bind("click", function() {
		var test = {};
		test.Name = "某某某";
		test.Sex = "1";
		test.Nation = "汉";
		test.Born = "1990-01-01";
		test.Address = "测试地址重庆市渝中区石油路";
		test.CardNo = "500109198903151035";
		test.IssuedAt = "测试公安局";
		test.EffectedDate = "20120903";
		test.ExpiredDate = "20220903";
		test.Picture="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";
		
		var valid_start='';
		var valid_end='';
		var year = '';
		var month = '';
		var day ='';
		pName = test.Name; // 姓名
		pSex = test.Sex; // 性别
		if(pSex != '1') {
			pSex = '0';
		}
		pSexShow = pSex == "1" ? "男" : "女";
		pNation = getNationStr(test.Nation); // 民族
		pBorn = test.Born; // 出生日期
		
		pAddress = test.Address; // 住址
		pCardNo = test.CardNo; // 身份证号
		pPolice = test.IssuedAt; // 签发机关
		pActivityLFrom = test.EffectedDate; // 起始日期
		if(pActivityLFrom.length == 8){
			var y = pActivityLFrom.substr(0, 4);
			var m = pActivityLFrom.substr(4, 2);
			var d = pActivityLFrom.substr(6, 2);
			pActivityLFrom = y + "-" + m + "-" + d; 
			valid_start = y + "." + m + "." + d;
		}
		pActivityLTo = test.ExpiredDate; // 结束日期
		if(pActivityLTo.length >= 8){
			var y = pActivityLTo.substr(0, 4);
			var m = pActivityLTo.substr(4, 2);
			var d = pActivityLTo.substr(6, 2);
			pActivityLTo = y + "-" + m + "-" + d;
			valid_end = y + "." + m + "." + d;
		}else{
			valid_end = pActivityLTo;
			pActivityLTo = "2099" + "-" + "01" + "-" + "01";
		}
		//pDeviceNo = test.CardReaderId; //阅读器终端ID
		base64jpg = test.Picture; // 照片编码 
		$('#idNumber').val(pCardNo);
		
		queryInfo();
	});
});

// 展开tab页
function openTab(ele) {
	// 选中当前tab
	ele.siblings().removeClass('active');
	ele.addClass('active');
	// 当前tab的箭头向下
	ele.siblings().find('.tab_icon_down').removeClass("tab_icon_down").addClass("tab_icon_up");
	ele.find('.tab_icon_up').removeClass("tab_icon_up").addClass("tab_icon_down");
	// 显示当前tab对应的内容
	ele.siblings().each(function(i, item) {
		$('#' + $(item).attr('tab')).hide();
	});
	$('#' + ele.attr('tab')).show();
}

function cleanInputText(){
	cleanInputTextAll();
};
function queryInfo(){
	device_number = $("#deviceNumber").val();
	id_number = $("#idNumber").val();
	if((device_number == null || device_number == "") 
			&& (id_number == null || id_number == "")){
		$.error("请输入身份证号码或设备号码进行查询!");
	} else if(id_number != null && id_number != "" 
		&& device_number != null && device_number != ""){
		$.error("不允许同时输入身份证号码和设备号码进行查询!");
	} else {
		queryUserInfo();
		queryPreOrderInfo();
	}
};

// 查询预订单
function queryPreOrderInfo(){
	$('#preOrderInfos>tbody>tr').remove();
	device_number = $('#deviceNumber').val();
	id_number = $('#idNumber').val();
	var data = {
		'id_number': id_number,
		'device_number': device_number
	}
	var url = application.fullPath + '/authority/customer/queryPreOrdersCq';
	$.ajax({
		url: url,
		type: 'post',
		data: data,
		waitMsg: '正在查询...',
		success: function(message) {  
			if ('error' == message.type) {
				if (null != message.content) {
					$.error('预订单查询失败，' + message.content);
					return;
				} else {
					$.error('预订单查询失败，请重新查询！');
					return;
				}
			}
			var infoPreOrderVos = message.args.infoPreOrderVos;
			if (!infoPreOrderVos || null == infoPreOrderVos) {
				$('#preOrderDiv').hide();
			} else {
				$('#preOrderDiv').show();
				$.each(infoPreOrderVos, function(i, item) {
					var html = '<tr pre_order_id="' + item.pre_order_id + '">'
							+ '<td><input type="checkbox" name="chk_pre_order"/></td>'
							+ '<td class="text_center">' + item.pre_order_id + '</td>' // 订单号
							+ '<td class="text_center">' + item.id_number + '</td>' // 证件号
							+ '<td class="text_center">' + item.device_number + '</td>' // 设备号码
							+ '<td class="text_center">业务变更</td>' // 订单类型
							+ '<td class="text_center" id ="preMark"></td>' // 订单信息
							+ '</tr>';
					$('#preOrderInfos>tbody').append(html);
				});
			}
		},
		error: function() {
			$.error('预订单查询错误，请重新查询！');
		}
	});
};

function queryUserInfo(){
	device_number = $("#deviceNumber").val();
	id_number = $("#idNumber").val();
    operNo = $('#operNo').val();
	var card_type = "ID001";
	var data={
			  'id_number' : id_number,
			  'device_number' : device_number,
			  'id_type' : card_type,
			  'oper_no' : operNo
		  }
	var url = application.fullPath + "/authority/customer/queryCustomerCQMess";
	$.ajax({
		url:url,
		type:'post',
		data:data,
		waitMsg:"正在查询...",
		success:function(message){  
			if ("error" == message.type){
		    	if (null != message.content){
		    		$.error("查询失败 "+message.content);
		    		return;
		    	} else {
		    		$.error("查询失败 ,请重新查询！");
		    		return;
		    	}
		    }
		    var cust_info=message.args.cust_info[0];
		    $('#custName').text(cust_info.cust_name);
		    $('#idType').text('身份证');
		    $('#iddNumber').text(cust_info.id_number);
		    $('#cardType').text('');
		    queryBusiInfo();
		},
		error:function(){
			$.error("查询错误，请重新查询！ ");
		}
	});
};

function queryBusiInfo(){
	if(device_number != null && device_number != ""){
		//设备号码查询业务信息
		queryBusiInfoByDeviceNumber();
	} else {
		//身份证号码查业务信息
		queryBusiInfoByIdNumber();
	}
	
};
//根据设备号码进行查询
function queryBusiInfoByDeviceNumber() {
	$('#deviceInfos>tbody>tr').remove();
	var card_type = "ID001";
	operNo = $('#operNo').val();
	var data={
			  'id_number' : id_number,
			  'device_number' : device_number,
			  'id_type' : card_type,
			  'oper_no' : operNo
		  }
	if(deviceNumber.value.indexOf("comcis") > 0) {
		var url = application.fullPath + "/authority/customer/queryCustomerCQMess";
		$.ajax({
			url: url,
			type: 'post',
			data: data,
			waitMsg: "正在查询...",
			success: function(message) {   
			    var broad_info=message.args.broad_info[0];
			    var cust_info=message.args.cust_info[0];
			    var html = '<tr device_number="' + deviceNumber.value + '" tele_type="' + cust_info.tele_type + '" product_code="' + cust_info.product_id + '">'
				    	+ '<td><input type="checkbox" name="chk_device"/></td>'
				    	+ '<td class="text_center">' + deviceNumber.value + '</td>' // 设备号码
				    	+ '<td class="text_center"></td>' // 用户状态
				    	+ '<td class="text_center">' + broad_info.product + '</td>' // 品牌
				    	+ '<td class="text_center">' + broad_info.product_comments + '</td>' // 产品
				    	+ '<td class="text_center">' + cust_info.devices_address + '</td>' // 装机地址
				    	+ '<td class="text_center"></td>' // 归属系统
				    	+ '</tr>';
			    $('#deviceInfos>tbody').append(html);
			},
			error: function() {
				$.error("查询错误，请重新查询！ ");
			}
			
		});
	} else {
		var url = application.fullPath + "/authority/customer/queryCustomerCQMess";
		$.ajax({
			url: url,
			type: 'post',
			data: data,
			waitMsg: "正在查询...",
			success: function(message) {   
			    var cust_info=message.args.cust_info[0];
			    var html = '<tr device_number="' + (null == cust_info.device_number ? device_number : cust_info.device_number) + '" tele_type="' + cust_info.tele_type + '" product_code="' + cust_info.product_id + '">'
				    	+ '<td><input type="checkbox" name="chk_device"/></td>'
				    	+ '<td class="text_center">' + (null == cust_info.device_number ? device_number : cust_info.device_number) + '</td>' // 设备号码
				    	+ '<td class="text_center">' + cust_info.device_status + '</td>' // 用户状态
				    	+ '<td class="text_center">' + cust_info.devices_brand + '</td>' // 品牌
				    	+ '<td class="text_center">' + cust_info.devices_products + '</td>' // 产品
				    	+ '<td class="text_center"></td>' // 装机地址
				    	+ '<td class="text_center">' + (null == cust_info.device_guishu ? 'CBSS' : cust_info.device_guishu) + '</td>' // 归属系统
				    	+ '</tr>';
			    $('#deviceInfos>tbody').append(html);
			},
			error:function(){
				$.error("查询错误，请重新查询！ ");
			}
			
		});
	}
};
//根据身份证号码进行查询
function queryBusiInfoByIdNumber() {
	$('#deviceInfos>tbody>tr').remove();
	//身份证号码查询业务信息
    operNo = $('#operNo').val();
	var card_type = "ID001";
	var data={
			  'id_number' : id_number,
			  'device_number' : device_number,
			  'id_type' : card_type,
			  'oper_no' : operNo
		  }
	var url = application.fullPath + "/authority/customer/queryBussInfoCQMess";
	$.ajax({
		url: url,
		type: 'post',
		data: data,
		waitMsg: "正在查询...",
		success: function(message) {
			var deviceList = message.args.deviceList;
			var html = '';
			for (var i = 0; i < deviceList.length; i++) {
		    	device = deviceList[i];
                if (device.devices_number.indexOf("comcis") > 0) {
                	html += '<tr device_number="' + device.devices_number + '" tele_type="" product_code="">'
				    	+ '<td><input type="checkbox" name="chk_device"/></td>'
				    	+ '<td class="text_center">' + device.devices_number + '</td>' // 设备号码
				    	+ '<td class="text_center">' + device.devices_status + '</td>' // 用户状态
				    	+ '<td class="text_center"></td>' // 品牌
				    	+ '<td class="text_center">' + device.devices_products + '</td>' // 产品
				    	+ '<td class="text_center">' + device.devices_address + '</td>' // 装机地址
				    	+ '<td class="text_center"></td>' // 归属系统
				    	+ '</tr>';
                } else {
                	html = '<tr device_number="' + device.devices_number + '" tele_type="' + device.flag234 + '" product_code="' + device.product_code + '">'
				    	+ '<td><input type="checkbox" name="chk_device"/></td>'
				    	+ '<td class="text_center">' + device.devices_number + '</td>' // 设备号码
				    	+ '<td class="text_center">' + device.devices_status + '</td>' // 用户状态
				    	+ '<td class="text_center">' + device.devices_brand + '</td>' // 品牌
				    	+ '<td class="text_center">' + device.devices_products + '</td>' // 产品
				    	+ '<td class="text_center"></td>' // 装机地址
				    	+ '<td class="text_center">' + device.devices_guishu + '</td>' // 归属系统
				    	+ '</tr>';
                }
			}
			$('#deviceInfos>tbody').append(html);
		},
		error:function(){
			$.error("查询错误，请重新查询！ ");
		}
		
	});
};

function createOrderId(){
	tele_type = $("#teleType").val();
	jsessionid = $('#jsessionid').val();
	var data1={
			 "jsessionid":jsessionid,
			 "tele_type":tele_type,
			 "order_sub_type":"10030"
		  };
	var URL = application.fullPath + "authority/accountOpen/orderInfoSubmit";
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
				order_id = message.args.order_info.order_id;
			}
		}
		
	});		  
};

// 查询预订单的产品信息
function queryPreOrderProduct(preOrderId) {
	pre_order_id = preOrderId;
	var data = {
		'pre_order_id' : preOrderId
	}
	var url = application.fullPath + "/authority/customer/queryPreOrderAttrCq"; // TODO 修改url
	$.ajax({
		url: url,
		type: 'post',
		data: data,
		waitMsg: '正在查询...',
		success: function(message) {  
			if ('error' == message.type){
		    	if (null != message.content){
		    		$.error('查询失败，' + message.content);
		    		return;
		    	} else {
		    		$.error('查询失败，请重新查询！');
		    		return;
		    	}
		    }
			var infoPreOrderAttrVos=message.args.infoPreOrderAttrVos[0];
			 ofr_id = infoPreOrderAttrVos.product_id;
			 bss_product = infoPreOrderAttrVos.product_id;
			 preMark = infoPreOrderAttrVos.mark;
			 $('#preMark').text(preMark);
			 document.getElementById("product_select").value = preMark;
			 // 展开【产品变更】tab页
			 openTab($('#tabs>[tab_name="productChng"]'));
			
			// TODO 把查询到预订单的产品信息显示到页面上
		},
		error: function(){
			$.error('查询错误，请重新查询！');
		}
	});
}

function productChngShow(){
	tele_type = $("#teleType").val();
	if(tele_type == "4G"){
		document.getElementById("prodValidType").innerHTML = "立即生效";
		$("#activity_show").css('display','none');
		$("#chuanhao").css('display','none');
		$("#pinpai").css('display','none');
		$("#xinghao").css('display','none');
		$("#yanse").css('display','none');
	} else {
		document.getElementById("prodValidType").innerHTML = "次月生效";
		$("#activity_show").css('display','block');
		$("#chuanhao").css('display','block');
		$("#pinpai").css('display','block');
		$("#xinghao").css('display','block');
		$("#yanse").css('display','block');
	}
};

function dinnerChngShow(){
	$("#busiPgkOperType option:selected").val("");
	document.getElementById("pkg_select").value =  '';
	s_ywbInfo = [];
	$("#validType option:selected").val("");
};
//业务包弹出选择框
function busiPkgSel(input_text,flag){
	$("#PkgInput").val("");
	var chngType = $("#busiPgkOperType option:selected").val();
	if(chngType == "" || chngType == null){
		$.error("请选择订购类型!");
		return;
	}
	tele_type = $("#teleType").val();
	if(tele_type == "2G"){
		ofr_type = "801";
		ywb_type = "901";
	}else if(tele_type == "3G"){
		ofr_type = "802";
		ywb_type = "902";
	}else if(tele_type == "4G"){
		ofr_type = "803";
		ywb_type = "903";
	}
	operNo = $('#operNo').val();
	jsessionid = $('#jsessionid').val();
	var productId = $('#productId').val();
	var data={
		'jsessionid' : jsessionid,
		'device_number' : device_number,
		'oper_no' : operNo,
		'chng_type' : chngType,
		'ofr_type' : ofr_type,
		'ywb_type' : ywb_type,
		'input_text' : input_text,
		'ywb_ofr_id' : productId
	}
	var url = "";
	if(chngType == "0"){
		url = application.fullPath + "/authority/index/getProductBagCq";
	} else if(chngType == "1"){
		url = application.fullPath + "/authority/index/queryUserProductCq";
	}
	$.ajax({
		url:url,
		type:'post',
		data:data,
		waitMsg:"正在查询...",
		success:function(message){
			if(chngType == "1"){
				$("#ywb_list").html('');
				if(message.type == "error"){					
					$.alert(message.content);
					flag_select=true;
				}else{					
					if(flag=="0"){
						showdiv('busi_package');
					}
					$.each(message.args.product.pkg_list, function(i, item) {
						var data = JSON.stringify(item);
						$("#ywb_list").append('<li class="list"><div class="left"><div class="left_lable">'+item.pkg_name+'</div><div class="right_data">'
							  +' <input name="pkg_search_check" type="checkbox" data=\''+data+'\' id=\''+item.pkg_code+'\'></input></div></div></li>');
					});
				}
			} else if(chngType == "0"){
				$("#ywb_list").html('');
				if(message.type == "error"){					
					$.alert(message.content);
					flag_select=true;
				}else{					
					if(flag=="0"){
						showdiv('busi_package');
					}
					$.each(message.args.chk_product_info, function(i, item) {
						var data = JSON.stringify(item);
						$("#ywb_list").append('<li class="list"><div class="left"><div class="left_lable">'+item.chk_product_name+'</div><div class="right_data">'
							  +' <input name="pkg_search_check" type="checkbox" data=\''+data+'\' id=\''+item.chk_product_id+'\'></input></div></div></li>');
					});
				}
			}
		},
		error:function(){
			$.error("查询错误，请重新查询！ ");
		}
	});
};
//选择业务包信息
function selBusiPkg(){
	s_ywbInfo = [];
	var chngType = $("#busiPgkOperType option:selected").val();
	var pkg_name = '';
	$("#ywb_list li input[type=checkbox]").each(function(){
		if(this.checked){
			var dataStr = $(this).attr("data");	
			var data_pkg = JSON.parse(dataStr);
			var tempJsonItem = {};
			if(chngType == "1"){
				pkg_name = pkg_name + data_pkg.pkg_name + ";";
				tempJsonItem.pkg_code = data_pkg.pkg_code;
				tempJsonItem.change_type = chngType;
			} else if(chngType == "0"){
				pkg_name = pkg_name + data_pkg.chk_product_name + ";";
				tempJsonItem.pkg_code = data_pkg.chk_product_id;
				tempJsonItem.change_type = chngType;
			}
			s_ywbInfo.push(tempJsonItem);
	    }
	});
	hidediv('busi_package');
	document.getElementById("pkg_select").value =  pkg_name;
};
function BusiPkgDelete(){
	hidediv('busi_package');
	document.getElementById("pkg_select").value =  '';
	s_ywbInfo = [];
};
//选择或者取消业务包
function selectYwbInfo(e){
	var obj = $(e);
	if(obj.hasClass("busi_bag_checked")){
		obj.removeClass("busi_bag_checked").addClass("busi_bag_check");
	} else {
		obj.removeClass("busi_bag_check").addClass("busi_bag_checked");
	}
};
//点击确认 提交订单
function orderSubmit(){
	var orderId = $("div.tab_icon_down").attr('orderId');
	if(orderId == "" || orderId == null){
		$.alert("请进行业务操作！");
		return;
	}
	if(orderId == "1"){
		//产品变更
		productChngSubmit();
	} else if(orderId == "2"){
		//套餐变更
		dinnerChngSubmit();
	}
};

//产品变更
function productChngSubmit(){
	var changeDate = $("#prodValidType").text();
	tele_type = $("#teleType").val();
	if(tele_type == "" || tele_type == null){
		$.alert("请先查询用户信息！");
		return;
	}
	var prodSel = $("#product_select").val();
	if(prodSel == "" || prodSel == null){
		$.alert("请选择产品信息！");
		return;
	}
	if(tele_type == "4G"){
		orderSubmitFor4G();
	} else if(tele_type == "3G"){
		var actType = $("#activityType").val();
		if(actType != null && actType != "" && actType != "0"){
			//换产品同时参加活动
			orderSubmitFor3G();
		} else {
			//换产品不参加活动
			orderSubmitFor3GNoActivity();
		}
	}
};

//套餐变更
function dinnerChngSubmit(){
	var cust_name = $("#custName").text();
	if(cust_name == "" || cust_name == null){
		$.alert("请先查询用户信息！");
		return;
	}
	//生效方式
	var validType = $("#validType option:selected").val();
	if(validType == "" || validType == null){
		$.error("请选择生效方式!");
		return;
	}
	var data={
			'jsessionid' : jsessionid,
			'device_number' : device_number,
			'oper_no' : operNo,
			'dinner_info_json' : JSON.stringify(s_ywbInfo),
			'change_date' : validType,
			'order_id' : order_id,
			'cust_name' : cust_name
		};
	var url = application.fullPath + "/authority/index/tarrifChangeOrderSubmitCq";
	$.ajax({
		url:url,
		type:'post',
		data:data,
		waitMsg:"正在提交...",
		success:function(message){
			if (message.type == "error") {
				$.alert(message.content);
			} else {
				$.alert(message.content);
			}
		},
		error:function(){
			$.error("订单提交失败！ ");
		}
		
	});
};
//产品变更产品弹出框
function showProductInfo(input_text,flag){
	tele_type = $("#teleType").val();
	if(tele_type == "4G"){
		ofr_type = "600";
	}
	jsessionid = $('#jsessionid').val();
	var data={
		'jsessionid' : jsessionid,
		'tele_type' : tele_type,
		'pay_flag' : "1",
		'ofr_type' : ofr_type,
		'product_name' : input_text
	}
	var url = application.fullPath + "/authority/index/saleSelectedCodeCq";
	$.ajax({
		url:url,
		type:'post',
		data:data,
		waitMsg:"正在查询...",
		success:function(message){
			$("#product_list").html('');
			if(message.type == "error"){					
				$.alert(message.content);
				flag_select=true;
			}else{					
				if(flag=="0"){
					showdiv('product_info');
				}
				$.each(message.args.sale_selected_code_list, function(i, item) {
					var data = JSON.stringify(item);
					$("#product_list").append('<li class="list"><div class="left"><div class="left_lable">'+item.product_name+'</div><div class="right_data">'
						  +' <input name="prod_search_check" onClick="productSelect(this)" type="radio" data=\''+data+'\' id=\''+item.product_id+'\'></input></div></div></li>');
				});
			}
		},
		error:function(){
			$.error("查询错误，请重新查询！ ");
		}
		
	});
};
var data_product='';
var ofr_name='';
var bss_product='';
function productSelect(e){
	var div = $(e);
	var dataStr = div.attr("data");	
	data_product = JSON.parse(dataStr);	
};
function productConfirm(){
	ofr_id = data_product.product_id;
	ofr_name = data_product.product_name;
	bss_product = data_product.bss_product;
	if(ofr_id==null||ofr_id==''){
		return;
	}
	hidediv('product_info');
	document.getElementById("product_select").value =  data_product.product_name;
	if(tele_type == "3G"){
		GetActivityType();
	}
};

function productDelete(){
	hidediv('product_info');
	ofr_id = '';
	ofr_name = '';
	document.getElementById("product_select").value =  '';
	data_product='';
};
//产品单选事件
function chngProdInfo(e){
	$('.radio_checked').each(function(index, item) {
		$(this).removeClass("radio_checked").addClass("radio");
	});
	var obj = $(e);
	if(obj.hasClass("radio_checked")){
		obj.removeClass("radio_checked").addClass("radio");
	} else {
		obj.removeClass("radio").addClass("radio_checked");
	}
};
//查询活动类型
function GetActivityType(){
	tele_type = $("#teleType").val();
	$("#activityType").html("<option value='0'></option>");
	var data1={
		"product_id":ofr_id,
		"tele_type":tele_type
	};
	var URL = application.fullPath + "authority/accountOpen/getActivityType";
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		data:data1,
		waitMsg:"活动类型加载中..",
		success:function(message){					 
			if(message.type == "error"){
				if(message.content.indexOf("没有查询到可用的活动类型")>=0){
					
				}else{
					$.alert(message.content);
				}
			}else{						 
				$.each(message.args.activity_list, function(i, item) {
					var data = JSON.stringify(item);
					$("#activityType").append('<option value=\''+item.activity_type+'\'>'+item.activity_type_name+'</option>');
				});					
			}				
		}
	});
}
//产品变更业务包弹出框
function showBusiPkgInfo(){
	
};
//查询担保类型
function GetGuaranteeType(){
	activityDelete();
	var activity_type = $("#activityType").val();
	if(activity_type==null||activity_type==""||activity_type=="0"){
		$("#guarantee_type_select").html("<option value='0'></option>");
		return;
	}
	$("#guarantee_type_select").html("<option value='0'></option>"); 
	if(activity_type == "CFSF001" || activity_type =="ZSYW001"){
		$("#guarantee_type_select").attr("disabled",true);
	}else{
		var data1={
				"tele_type":tele_type,
				"product_id":ofr_id,
				"activity_type": activity_type
			};
		var URL = application.fullPath + "authority/accountOpen/getGuaranteeType";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"担保类型加载中..",
			success:function(message){					 
				if(message.type == "error"){					
					$.alert(message.content);
				}else{		
					$("#guarantee_type_select").removeAttr("disabled");
					$.each(message.args.guarantee_list, function(i, item) {
						var data = JSON.stringify(item);
						$("#guarantee_type_select").append('<option value=\''+item.guarantee_type+'\'>'+item.guarantee_type_name+'</option>');
					});
					if($("#guarantee_type_select option[value='04']").val()=='04'){
						$("#guarantee_type_select").val("04");
					}
				}				
			}
		});	
	}
};
//查询可选活动
function showActivityInfo(input_text,flag){
	tele_type = $("#teleType").val();
	if(ofr_id == null||ofr_id ==""){
		$.alert("选择的资费不能为空");
		return;
	}
	if(mobileNo!=$("#mobileNo").val()){
		$.alert("请校验终端串号!");
		return;
	}else if(mobileNo==''&&property_code!=''){
		$.alert("请校验终端串号!");
		return;
		}
	if((((($("#activityType").find("option:selected").text())!="存费送费" ) &&
			(($("#activityType").find("option:selected").text())!="存费送业务") &&
			(($("#activityType").find("option:selected").text())!="自备机入网送话费")&&
			(($("#activityType").find("option:selected").text())!=""))									
			&& ($("#mobileNo").val()==null||$("#mobileNo").val()=='') ) ){				
			$.alert("请校验终端串号!");
			return ;	
	}
	var guarantee_type = $("#guarantee_type_select").val();
	if(guarantee_type == null||guarantee_type ==""||guarantee_type =="0"){
		if(tele_type=="3G"){
			guarantee_type = "04";
		}else if(tele_type=="4G"){
			guarantee_type = "4";  
		}
	}	
	 
	if(guarantee_type =="04"&& tele_type=="4G"){
		guarantee_type = "4";  
	}
	 
	 
	var activity_type = $("#activityType").val();
	if(activity_type == null||activity_type ==""||activity_type =="0"){
		$.alert("选择活动类型不能为空");
		return;
	}
	 
	 if(activity_type == "CFSF001" || activity_type =="ZSYW001"){
		guarantee_type = '';
	 }	 
	 
	 var data1 = {
				"product_id":ofr_id,
				"tele_type":tele_type,
				"activity_type":activity_type,
				"guarantee_type":guarantee_type,
				"brand":brand,
				"model":model,
				"mobile_no":mobileNo,
				"input_text":input_text
		};
	var URL = application.fullPath + "authority/accountOpen/getActivityName";
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		data:data1,
		waitMsg:"可选活动查询中..",
		success:function(message){			
			$("#activity_list").html('');
			if(message.type == "error"){					
				$.alert(message.content);
				flag_select=true;
			}else{					
				if(flag=="0"){
					showdiv('fee_active_option');
				}
				$.each(message.args.chose_activity_list, function(i, item) {
					var data = JSON.stringify(item);
					$("#activity_list").append('<li class="list"><div class="left"><div class="left_lable">'+item.activity_name+'</div><div class="right_data">'
						  +' <input name="fee_search_check" onClick="activitySelect(this)" type="radio" data=\''+data+'\' id=\''+item.activity_id+'\'></input></div></div></li>');
				
				});
				if($("#activity_select").val()!=null && $("#activity_select").val()!=''){
					flag_select=true;
				}
			}				
		}
	});	
};
var data_activity='';
var activity_id='';
function activitySelect(e){
	var div = $(e);
	var dataStr = div.attr("data");	
	data_activity = JSON.parse(dataStr);	
};
function activityConfirm(){
	activity_id = data_activity.activity_id;
	if(activity_id==null||activity_id==''){
		return;
	}
	hidediv('fee_active_option');
	document.getElementById("activity_select").value =  data_activity.activity_name;
	//调用费用计算，即老用户优惠购机处理申请接口
	getFeeData();
};

function activityDelete(){
	hidediv('fee_active_option');
	activity_id = '';
	document.getElementById("activity_select").value =  '';
	data_activity='';
};
//终端串号校验
function terminalCheck(){
	mobileNo =$("#mobileNo").val();
	if(mobileNo==null||mobileNo==''){
		$.alert("输入串号不能为空");
		return;
	}
	if(ofr_id==null||ofr_id==''){
		$.alert("亲~你没有选择资费哦~");
		return;
	}
	checkMobileNoById();
};
function checkMobileNoById(){
	var data1={
			"order_id":order_id,
			"terminal_id":mobileNo
		};
	var URL = application.fullPath + "authority/accountOpen/getTerminalInfoByTerminalId";
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		data:data1,
		waitMsg:"串号校验中..",
		success:function(message){					 
			if(message.type == "error"){	
				CancelMobileNo();
				$.alert(message.content);
			}else{	
				var data = message.args.terminal_info;  
				if(property_code!=null&&property_code!=''&&data.property_code!=property_code){
					CancelMobileNo();						
					$("#mobileNo").val("");
					$.alert("选择的手机型号与检验的不一致，请检查是否是同一型号");		
					mobile_no = '';
					return;
				}  
				
				select_property_code = data.property_code;
				select_terminal_model_code = data.terminal_model_code;
				
				model = data.terminal_model_code;
				brand = data.terminal_brand_code;
				terminal_brand = data.terminal_brand;
				phone_brand = data.terminal_brand_desc;
				phone_model = data.terminal_model_desc;
				phone_color = data.terminal_color;
				res_rele = data.res_rele;
				terminal_type = data.terminal_type;
				cost = data.cost;
				sale_price = data.sale_price;
				machine_type_code = data.property_code;
				machine_type_name = data.property_name;
				resources_src_code = data.channel_id;
				org_device_brand_code = data.org_device_brand_code;
				if(phone_brand != null){
					document.getElementById("brand").value = phone_brand;
				}
				if(phone_model != null){
					document.getElementById("model").value = phone_model;
				}
				if(phone_color != null){
					document.getElementById("color").value = phone_color;
				}
				mobileNo = $("#mobileNo").val();
				document.getElementById("mobileNo").readOnly ="true";	
			}				
		}
	});
};
function CancelMobileNo(){
	mobileNo ='';
	$("#mobileNo").val('');
	document.getElementById("brand").value = '';
	document.getElementById("model").value = '';
	document.getElementById("color").value = '';
	document.getElementById("mobileNo").readOnly ='';
	res_rele = '';
	terminal_type = '';
	cost = '';
	sale_price = '';
	machine_type_code = '';
	machine_type_name = '';
	resources_src_code = '';
};
function showdiv(popWinId) {
	if (!window.XMLHttpRequest) { 
		for(var i=0;i<selects.length;i++){
			if(selects[i].style.display!='none'){
				selects_display.push(selects[i]);
				selects[i].style.display = "none";
			};
		}
	}
	document.getElementById("bg_mask").style.display ='block';
	document.getElementById(popWinId).style.display ='block';

};
function hidediv(popWinId) {
	if (!window.XMLHttpRequest) { 
		for(var i=0;i<selects_display.length;i++){
			selects_display[i].style.display = "";
		}
	}
	document.getElementById("bg_mask").style.display ='none';
	document.getElementById(popWinId).style.display ='none';
};
//产品变更   业务包弹出选择框
function prodBusiPkgSel(input_text,flag){
	tele_type = $("#teleType").val();
	if(tele_type == "2G"){
		ofr_type = "801";
		ywb_type = "901";
	}else if(tele_type == "3G"){
		ofr_type = "802";
		ywb_type = "902";
	}else if(tele_type == "4G"){
		ofr_type = "803";
		ywb_type = "903";
	}
	if(ofr_id == null || ofr_id == ""){
		$.alert("产品为空，请先选择产品！");
		return;
	}
	operNo = $('#operNo').val();
	jsessionid = $('#jsessionid').val();
	var productId = $('#productId').val();
	var data={
		'jsessionid' : jsessionid,
		'device_number' : device_number,
		'oper_no' : operNo,
		'chng_type' : "0",
		'ofr_type' : ofr_type,
		'ywb_type' : ywb_type,
		'input_text' : input_text,
		'ywb_ofr_id' : ofr_id
	}
	var url = application.fullPath + "/authority/index/getProductBagCq";
	$.ajax({
		url:url,
		type:'post',
		data:data,
		waitMsg:"正在查询...",
		success:function(message){
			$("#prod_ywb_list").html('');
			if(message.type == "error"){					
				$.alert(message.content);
				flag_select=true;
			}else{					
				if(flag=="0"){
					showdiv('prod_busi_package');
				}
				$("#ProdPkgInput").val("");
				$.each(message.args.chk_product_info, function(i, item) {
					var data = JSON.stringify(item);
					$("#prod_ywb_list").append('<li class="list"><div class="left"><div class="left_lable">'+item.chk_product_name+'</div><div class="right_data">'
						  +' <input name="prod_pkg_search_check" type="checkbox" data=\''+data+'\' id=\''+item.chk_product_id+'\'></input></div></div></li>');
				});
			}
		},
		error:function(){
			$.error("查询错误，请重新查询！ ");
		}
	});
};
//产品变更  选择业务包信息
function selProdBusiPkg(){
	s_ywbInfo = [];
	var pkg_name = '';
	$("#prod_ywb_list li input[type=checkbox]").each(function(){
		if(this.checked){
			var dataStr = $(this).attr("data");	
			var data_pkg = JSON.parse(dataStr);
			var tempJsonItem = {};
			pkg_name = pkg_name + data_pkg.chk_product_name + ";";
			tempJsonItem.pkg_code = data_pkg.chk_product_id;
			tempJsonItem.change_type = "0";
			s_ywbInfo.push(tempJsonItem);
	    }
	});
	hidediv('prod_busi_package');
	document.getElementById("prod_pkg_sel").value =  pkg_name;
};
function ProdBusiPkgDelete(){
	hidediv('prod_busi_package');
	document.getElementById("prod_pkg_sel").value =  '';
	s_ywbInfo = [];
};

//4G用户产品变更 订单提交方法
function orderSubmitFor4G(){
	var old_product_id = $("#productId").val();
	tele_type = $("#teleType").val();
	jsessionid = $('#jsessionid').val();
	var data1={
		 "jsessionid":jsessionid,
		 "tele_type":tele_type,
		 "serialNumber":device_number,
		 "productId":ofr_id,
		 "old_productId":old_product_id,
		 "order_id" : order_id
	};
	var URL = application.fullPath + "/authority/index/tariffChangeCq";
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		data:data1,
		waitMsg:"订单生成中..",
		success:function(message){
			if(message.type == "error"){
				$.alert(message.content);
			}else{
				updatePreOrderInfo();
				$.alert(message.content);
			}
		}
	});
};
String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
};
//费用计算
function getFeeData(){
	var old_product_id = $("#productId").val();
	var resourceCode = $("#mobileNo").val();
	product_info_json=[];
	var tempJsonItems = {};
	tempJsonItems.optType = "00"
	tempJsonItems.productMode = "1";
	tempJsonItems.productId = ofr_id;
	product_info_json.push(tempJsonItems);
	
	var data1={
			 "jsessionid":jsessionid,
			 "serialNumber":device_number,
			 "productId":ofr_id,
			 "product_info_json":JSON.stringify(product_info_json),
			 "actPlanId":activity_id,
			 "resourcesCode":resourceCode,
			 "resourcesFee":"",
			 "resourcesModelCode":model,
			 "resourcesBrandCode":brand,
			 "resourcesBrandName":phone_brand,
			 "resRele":res_rele,
			 "terminalType":terminal_type,
			 "cost":cost,
			 "resourcesModelName":phone_model,
			 "machineTypeCode":machine_type_code,
			 "machineTypeName":machine_type_name,
			 "resourcesSrcCode":resources_src_code,
			 "orgDeviceBrandCode":org_device_brand_code,
			 "resourcesColor":phone_color,
			 "salePrice":sale_price,
			 "ordersId" : order_id,
		};
	fee_all =0;
	var URL = application.fullPath + "/authority/index/perPurchase";
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		data:data1,
		waitMsg:"订单处理申请中..",
		success:function(message){
			if(message.args.code == "200"){
				fee_info = message.args.perPurchase.feeInfo;
				$.each(message.args.perPurchase.feeInfo, function(i, item) {
					var data = JSON.stringify(item);
					data = data.replaceAll("\"", "\'");
					var strstr = '<li class="list bold">'+item.feeDes+'</li><li class="list"><div class="left"><div class="left_lable">应收：<span class="bold">'+	    											
					item.origFee/1000+'</span>&nbsp;元</div><div class="right_data">实收：<input type="text" id="charge_item'+i+'" name="'+item.feeDes+'" onChange="CheckChargeItem(this,1);" data="'+data+'" class="input_text width_8 text_normal_b" value="'+		    						    				
					item.origFee/1000+'">&nbsp;元</div></div></li><div class="line_dashed_h"></div>';
					$("#fee_list").append(strstr);
					fee_all = parseFloat(item.origFee/1000) + parseFloat(fee_all);
				});
				 
				$("#fee_all").html("&nbsp;&nbsp;&nbsp;"+fee_all);
			}else{
				$.alert(message.args.detail);
			}
		}
	});
};
function CheckChargeItem(e,flag){
	var div = $(e);
	var feess = div.attr("data");
	feess = feess.replaceAll("\'", "\"");
	var feeitem = JSON.parse(feess);
	var fe1 = feeitem.origFee;
	if(flag==1){
		if(parseFloat(e.value)>parseFloat(parseFloat(feeitem.origFee)/1000)){
			$.alert("实收不能大于应收");
			e.value = parseFloat(feeitem.origFee/1000);
		} else {
			if(feeitem.maxRelief > 0){
				if(parseFloat(e.value) < parseFloat((feeitem.origFee-feeitem.maxRelief)/1000)){
					$.alert("最大减免金额"+parseFloat(feeitem.maxRelief/1000)+"元,实收不能小于应收与最大减免金额相减)");
					e.value = parseFloat(feeitem.origFee/1000);
				}
			} else {
				if(parseFloat(e.value)<parseFloat(parseFloat(feeitem.origFee)/1000)){
					$.alert("该项费用不能减免");
					e.value = parseFloat(feeitem.origFee/1000);
				}
			}
		}	
	}
	if(parseFloat(e.value)<0){
		e.value = 0;	
	}
	var re = /^[0-9]+.?[0-9]*$/;   //判断字符串是否为数字     //判断正整数 /^[1-9]+[0-9]*]*$/  
	if (!re.test(e.value))
	{
		 $.alert("请输入数字");
		 e.value = 0;
	}
	getTotalFee();
};
function getTotalFee(){
	fee_all =0;
	$.each(fee_info, function(i, item) {
		var test = "#charge_item"+i;
		fee_all =  parseFloat($("#charge_item"+i).val()) + parseFloat(fee_all);
	});
	$("#fee_all").html("&nbsp;&nbsp;&nbsp;"+fee_all);
};
//3G订单提交
function orderSubmitFor3G(){
	if(((($("#activityType").find("option:selected").text())!=null 
			&& ($("#activityType").find("option:selected").text())!= '')) 
			&& ($('#activity_select').val()==null 
					|| $('#activity_select').val()=='') && !flag_select){				
		$.alert("亲~选择了活动类型必须选择可选活动~");
		return;
	}
	
	getJsonForCommit();
	var order_json_str = {
		"product_info": product_info_json,
		"activity_info": activity_info_json,
		"resource_info": resource_info_json,
		"fee_info_json" : fee_info_json,
		"origTotalFee":fee_all*1000,
		"pay_info":{"payType":"10"}
	};
	var cust_name = $("#custName").text();
	var data1={
		 "jsessionid":jsessionid,
		 "fee_info_json":JSON.stringify(fee_info_json),
		 "origTotalFee":fee_all*1000,
		 "ordersId" : order_id,
		 "device_number" : device_number,
		 "cust_name" : cust_name,
		 "order_json" : JSON.stringify(order_json_str)
	};
	var URL = application.fullPath + "/authority/index/perPurchaseSubmit";
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		data:data1,
		waitMsg:"订单提交中..",
		success:function(message){
			if(message.args.code == "200"){
				updatePreOrderInfo();
				$.alert(message.content);
				cleanInputTextAll();
			}else{
				$.alert(message.args.detail);
			}
		}
	});
};
//获取json
function getJsonForCommit(){
	//费用信息
	fee_info_json = [];
	for (var i = 0; i < fee_info.length; i++) {
		//服务器费用只支持字符
		var fee_result = {
			"feeCategory": fee_info[i].feeCategory,
			"feeDes":fee_info[i].feeDes,
			"feeId": fee_info[i].feeId,
			"origFee":fee_info[i].origFee + "",
			"realFee":parseFloat($("#charge_item"+i).val())*1000+ "" ,
			"reliefFee": fee_info[i].maxRelief,
			"reliefResult": "无"
		};
		fee_info_json.push(fee_result);
	}
	//资源信息
	resource_info_json = [];
	var resource_result = {
			"resourcesModelCode": model,
            "resourcesBrandName": phone_brand,
            "resourcesBrandCode": brand,
            "resRele": res_rele,
            "terminalType": terminal_type,
            "cost": cost,
            "resourcesModelName": phone_model,
            "machineTypeName": machine_type_name,
            "resourcesSrcCode": resources_src_code,
            "orgDeviceBrandCode": org_device_brand_code,
            "machineTypeCode": machine_type_code,
            "resourcesColor": phone_color,
            "salePrice": sale_price
	};
	resource_info_json.push(resource_result);
	//活动信息
	activity_info_json = [];
	var activity_result = {
			"actPlanId": activity_id,
            "resourcesType": terminal_type,
            "isTest": "0",
            "resourcesCode": $("#mobileNo").val(),
            "resourcesFee": ""
	};
	activity_info_json.push(activity_result);
};
//3G换产品不参加活动
function orderSubmitFor3GNoActivity(){
	var cust_name = $("#custName").text();
	var data1="";
	if(s_ywbInfo.length == 0){
		s_ywbInfo=[];
	}
	data1={
				 "jsessionid":jsessionid,
				 "order_id" : order_id,
				 "dinner_info_json":JSON.stringify(s_ywbInfo),
				 "product_id" : bss_product,
				 "customer_name":cust_name,
				 "device_number":device_number
		};
	var URL = application.fullPath + "/authority/index/tarrifChangeOrderSubmitCq";
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		data:data1,
		waitMsg:"订单提交中..",
		success:function(message){
			if(message.args.rsp_code == "00000"){
				updatePreOrderInfo();
				$.alert(message.content);
				cleanInputTextAll();
			}else{
				$.alert(message.args.rsp_desc);
			}
		}
	});
};
//预订单状态更新
function updatePreOrderInfo(){
	var data = {
		'pre_order_id': pre_order_id,
		'order_status': "0"
	}
	var url = application.fullPath + '/authority/index/updatePreOrderStatusCq';
	$.ajax({
		url: url,
		type: 'post',
		data: data,
		success: function(message) {  
		},
		error: function() {
			$.error('更新预订单状态失败!');
		}
	});
};

//清空所有赋值
function cleanInputTextAll(){
	$("#teleType").val('');
	$("#productId").val('');
	$("#idNumber").val('');
	$("#deviceNumber").val('');
	$("#custName").html('');
	$("#idType").html('');
	$("#iddNumber").html('');
	$("#cardType").html('');
	$("#product_select").val('');
	$("#prod_pkg_sel").val('');
	$("#activityType").html('');
	$("#guarantee_type_select").html('');
	$("#activity_select").val('');
	$("#mobileNo").val('');
	$("#brand").val('');
	$("#model").val('');
	$("#color").val('');
	$("#pkg_select").val('');
	$("#fee_list").html('');
	$("#fee_all").html("&nbsp;&nbsp;&nbsp;"+0);
	ofr_type='';
	ywb_type = '';
	s_ywbInfo = [];
	ofr_id = '';
	mobileNo = '';
	model= '';
	brand='';
	terminal_brand ='';
	phone_brand='';
	phone_model='';
	phone_color='';
	select_property_code ='';
	select_terminal_model_code ='';
	property_code = '';
	flag_select=false;
	fee_info=[];
	res_rele='';
	terminal_type='';
	cost='';
	sale_price='';
	machine_type_code='';
	machine_type_name='';
	resources_src_code='';
	org_device_brand_code='';
	fee_all =0;
	fee_info_json = [];
	product_info_json=[];
	resource_info_json=[];
	activity_info_json = [];
	$('#deviceInfos>tbody>tr').remove();
	var deviceHtml = '<tr device_number="" tele_type="" product_code="">'
					+ '<td></td>'
					+ '<td class="text_center"></td>'
					+ '<td class="text_center"></td>'
					+ '<td class="text_center"></td>'
					+ '<td class="text_center"></td>'
					+ '<td class="text_center"></td>'
					+ '<td class="text_center"></td>'
					+ '</tr>';
	$('#deviceInfos>tbody').append(deviceHtml);
	$('#preOrderInfos>tbody>tr').remove();
	var preOrderHtml = '<tr pre_order_id="">'
					+ '<td></td>'
					+ '<td class="text_center"></td>'
					+ '<td class="text_center"></td>'
					+ '<td class="text_center"></td>'
					+ '<td class="text_center"></td>'
					+ '<td class="text_center"></td>'
					+ '</tr>';
	$('#preOrderInfos>tbody').append(preOrderHtml);
};

//加载身份证读卡器
function load_card_mech() {
	var id_card_mech = $("#id_card_mech").val();
	if (id_card_mech === "crvu") {
		load_model(id_card_mech);
	}
	$("#id_card_mech").change(function() {
		id_card_mech = $("#id_card_mech").val();
		if (id_card_mech === "crvu") {
			load_model(id_card_mech);
		}
// 		else if(id_card_mech === "ICR"){
// 			load_model("ICR");
//		}
	});
}

function load_model(model_name){
//	$("#message").val('成功加载:' + model_name);
//		$.get("/uss_web/cvr/"+model_name+"/content.html",function(rst){$('#reader_context').html(rst);});
//		$.getScript("/uss_web/cvr/"+model_name+"/script.js",function(rst){
//			$("#message").val('成功加载:' + model_name);
//		});
	$("#messageFlag").val('1');
};

//将读取到的证件信息填充到页面上
function fillForm() {
	pCardNo = CVR_IDCard.CardNo; // 身份证号
	$('#idNumber').val(pCardNo);
		
	return true;
}
