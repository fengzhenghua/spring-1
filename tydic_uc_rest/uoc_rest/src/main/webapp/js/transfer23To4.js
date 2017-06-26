var imgDir = ''; // 图片文件夹路径
var operNo = '';
var provinceCode = '';
var inputIdNumber = ''; // 按证件编号查询
var inputDeviceNumber = ''; // 按设备号码查询
var devCount = 0; // 设备总数
var devCurrent = 0; // 当前选中设备
var details;
var dealArray;
var t;
var cust_info;
var order_id;
var requsetDescs = '';
var respOperTypes = '';

$(document).ready(function() {
	imgDir = $("#img_dir").val();
	operNo = $("#oper_no").val();
	provinceCode = $("#province_code").val();
	
	// 查询按钮
	$('#searchBtn').bind('click', function() {
		queryInfo();
	});
	// 置空按钮
	$('#resetBtn').bind('click', function() {
		clearAll();
	});
	// 一键取消
	$('#cancel').bind('click', function() {
		akeyToCancel();
	});
	//  免填单
	$('#fill_sheet').bind('click', function() {
		transfer23To4Wkp();
	});
	
	bindDeviceEvent();
});

// 绑定设备的相关事件
function bindDeviceEvent() {
	devCount = $('[name="info_detail"]').length; // 设备总个数
	devCurrent = 1; // 左边第一个设备的序号
	var displayCount = 2; // 页面上显示的设备个数
	// 隐藏第3个以后的设备
	for (var i = displayCount + 1; i <= devCount; i++) {
		$("#detail_" + i).hide();
	}
	// 禁用向左按钮
    $('#btn_left').removeClass('btn_left_more').addClass('btn_left');
    // 启用/禁用向右按钮
    if (devCount > displayCount) {
    	$('#btn_right').removeClass('btn_right').addClass('btn_right_more');
    } else {
    	$('#btn_right').removeClass('btn_right_more').addClass('btn_right');
    }
	// 点击向左按钮
    $('#btn_left').unbind('click');
	$('#btn_left').click(function() {
		if (devCurrent > 1) {
			devCurrent = devCurrent - 1;
			// 显示/隐藏设备信息
			$('#detail_' + (parseInt(devCurrent) + parseInt(displayCount))).hide();
			$('#detail_' + devCurrent).show();
			// 启用向右按钮
			if (devCurrent <= devCount - displayCount) {
				$('#btn_right').removeClass('btn_right').addClass('btn_right_more');
			}
			// 禁用向左按钮
			if (devCurrent <= 1) {
				$('#btn_left').removeClass('btn_left_more').addClass('btn_left');
			}
		}
	});
	// 点击向右按钮
	$("#btn_right").unbind('click');
	$("#btn_right").click(function() {
		if (devCurrent <= devCount - displayCount) {
			devCurrent = devCurrent + 1;
			// 显示/隐藏设备信息
			$('#detail_' + (parseInt(devCurrent) - 1)).hide();
			$('#detail_' + (parseInt(devCurrent) + parseInt(displayCount) - 1)).show();
			// 启用向左按钮
			if (devCurrent > 1) {
				$('#btn_left').removeClass('btn_left').addClass('btn_left_more');
			}
			// 禁用向右按钮
			if (devCurrent > devCount - displayCount) {
				$('#btn_right').removeClass('btn_right_more').addClass('btn_right');
			}
		}
	});
	// 选中设备
	$('[name="info_detail"]').click(function() {
		$(this).siblings().removeClass('info_user_cur');
		$(this).addClass('info_user_cur');
		queryLimitInfo($(this).attr('deviceNumber'));
	});
}

function queryInfo() {
	inputIdNumber = $('#inputIdNumber').val();
	inputDeviceNumber = $('#inputDeviceNumber').val();
	if (inputIdNumber.length == 0 && inputDeviceNumber.length == 0) {
		$.error('请输入身份证号码或设备号码进行查询!');
		return;
	}
	if (inputIdNumber.length > 0 && inputDeviceNumber.length > 0) {
		$.error('不允许同时输入身份证号码和设备号码进行查询!');
		return;
	}
	clearCustomerInfo();
	clearLimitInfo();
	queryUserInfo();
}

function akeyToCancel() {
	inputDeviceNumber = $('#inputDeviceNumber').val();
	for(var i = 0;i < dealArray.length; i++){
		var item = dealArray[i]; 
		var requsetCoud = item.requset_code;
		var operType = $('input[name="radio_' + item.requset_code + '"]:checked ').val();
		var respOperTypeArray=item.resp_oper_type.split(";");
		if(respOperTypeArray.length > 0){
			requsetDescs += item.requset_desc+'\n';
			var respOperType="";
			var index=0;
			for(var j=0;j<respOperTypeArray.length;j++){
				if(respOperTypeArray[j] == operType){
					index=j;
					break;
				}
			}
			respOperType = respOperTypeArray[index+1];
		}
		respOperTypes += respOperType+'\n';
		var data = {
			'serial_number': inputDeviceNumber,//电话号码
			'operation_id': requsetCoud,//错误吗
			'oper_type':operType,//处理编码
			'oper_id': operNo//操作员工号
		}
		var url = application.fullPath + '/authority/transfer23To4/transfer23To4Oper';
		$('[code="'+item.requset_code+'"]>td:last').html('<img src="' + imgDir + 'deal_waiting.gif"/>');
		$.ajax({
			url: url,
			type: 'post',
			data: data,
			async : false, 
			waitMsg: '正在查询客户信息...',
			success: function(message) {
				if (message.type == 'error') {
					$('[code="'+item.requset_code+'"]>td:last').html('<img src="' + imgDir + 'deal_fail.png"/>');
					pending = Boolean("true");
					return;
				}
				$('[code="'+item.requset_code+'"]>td:last').html('<img src="' + imgDir + 'deal_success.png"/>');
				pending = Boolean("true");
				addInfoOrderAttrAndInfoOrder();
				
			},
			error: function() {
				$.error('客户信息查询错误，请重新查询！');
				$('[code="'+item.requset_code+'"]>td:last').html('<img src="' + imgDir + 'deal_fail.png"/>');
			}
		});
	}
}



function addInfoOrderAttrAndInfoOrder(){
	var data = {
		'device_number': cust_info.device_number,//电话号码
		'cust_name': cust_info.cust_name,//客户名称
		'tele_type':cust_info.tele_type,//电信类型
		'devices_products': cust_info.devices_products,//产品
		'id_number': cust_info.id_number,//证件号码
		'id_type': cust_info.id_type,//证件类型
		'device_status':cust_info.device_status,//用户状态
		'devices_brand': cust_info.devices_brand,//品牌
		'requset_desc':requsetDescs,//错误原因
		'resp_oper_type':respOperTypes//一键处理
	}
	var url = application.fullPath + '/authority/transfer23To4/addInfoOrderAttrAndInfoOrder';
	$.ajax({
		url: url,
		type: 'post',
		data: data,
		async : false, 
		waitMsg: '正在查询客户信息...',
		success: function(message) {
			if (message.type == "error") {
				var dialog=$.dialog({
					   title:'提示',//提示title
					   content:'订单生成失败',//显示的内容，支持html格式。
					   buttons:[{id:'btn_ok',text:'重新生成订单',
							   onClick:function() {
								   dialog.close();
								   addInfoOrderAttrAndInfoOrder();
							   }
								   
							}]
					});
			}
			order_id = message.args.order_id;
			$("#order_id").val(order_id);
		},
		error: function() {
			
		}
	});
}

function queryUserInfo() {
	var data = {
		'id_number': inputIdNumber,
		'device_number': inputDeviceNumber,
		'id_type': 'ID001',
		'oper_no': operNo
	}
	var url = application.fullPath + '/authority/customer/queryCustomerCQMess';
	$.ajax({
		url: url,
		type: 'post',
		data: data,
		waitMsg: '正在查询客户信息...',
		success: function(message) {
			if (message.type == 'error') {
				var errMessage = '客户信息查询失败，' + (message.content == null ? '请重新查询！' : message.content);
				$.error(errMessage);
				return;
			}
			cust_info = message.args.cust_info[0];
			$('#customerName').text(cust_info.cust_name);
			$('#idType').text('身份证');
			$('#idNumber').text(cust_info.id_number);
			$('#customerLevel').text('');
			queryBusiInfo();
		},
		error: function() {
			$.error('客户信息查询错误，请重新查询！');
		}
	});
}

function queryBusiInfo() {
	if (0 != inputDeviceNumber.length) {
		//设备号码查询业务信息
		queryBusiInfoByDeviceNumber();
	} else {
		//身份证号码查业务信息
		queryBusiInfoByIdNumber();
	}
}

// 设备号码查询业务信息
function queryBusiInfoByDeviceNumber() {
	$('[name="info_detail"]').remove();
	var data = {
		'id_number': inputIdNumber,
		'device_number': inputDeviceNumber,
		'id_type': 'ID001',
		'oper_no': operNo
	}
	var url = application.fullPath + "/authority/customer/queryCustomerCQMess";
	$.ajax({
		url: url,
		type: 'post',
		data: data,
		waitMsg: '正在查询设备信息...',
		success: function(message) {
			if (message.type == 'error') {
				var errMessage = '设备信息查询失败，' + (message.content == null ? '请重新查询！' : message.content);
				$.error(errMessage);
				return;
			}
			if (inputDeviceNumber.indexOf("comcis") > 0) {
				var broad_info = message.args.broad_info[0];
				var cust_info = message.args.cust_info[0];
				var tmpDeviceAddress = getByteLen(cust_info.devices_address) > 26 ? getByteSubstring(cust_info.devices_address, 0, 26) + '...' : cust_info.devices_address;
				var tmpProductComments = getByteLen(broad_info.product_comments) > 26 ? getByteSubstring(broad_info.product_comments, 0, 26) + '...' : broad_info.product_comments;
				var html = '<div class="info_user" name="info_detail" id="detail_1" deviceNumber="' + inputDeviceNumber + '">'
						+ '<div class="info_user_detail">'
						+ '<div class="left_lable">设备号码:</div>'
						+ '<div class="left_data_quarter">' + inputDeviceNumber + '</div>'
						+ '</div>'
						+ '<div class="info_user_detail">'
						+ '<div class="left_lable">用户状态：</div>'
						+ '<div class="left_data_quarter"></div>'
						+ '</div>'
						+ '<div class="info_user_detail">'
						+ '<div class="left_lable">装机地址：</div>'
						+ '<div class="left_data_quarter" title="' + cust_info.devices_address + '">' + tmpDeviceAddress + '</div>'
						+ '</div>'
						+ '<div class="info_user_detail">'
						+ '<div class="left_lable">产<span class="space24"></span>品：</div>'
						+ '<div class="left_data_quarter" title="' + broad_info.product_comments + '">' + tmpProductComments + '</div>'
						+ '</div>'
						+ '<div class="info_user_detail">'
						+ '<div class="left_lable">归属系统：</div>'
						+ '<div class="left_data_quarter"></div>'
						+ '</div>'
						+ '</div>';
				$('#btn_right').before(html);
				// TODO: 删除↓
//				document.getElementById("deviceeNumber").innerHTML = deviceNumber.value;
//				document.getElementById("deviceSatus").innerHTML = "";
//				document.getElementById("deviceProduct").innerHTML = broad_info.product_comments;
//				document.getElementById("deviceAddress").innerHTML = cust_info.devices_address;
//				document.getElementById("deviceName").innerHTML = broad_info.product;
//				document.getElementById("deviceGuishu").innerHTML = "";
			} else {
				var cust_info = message.args.cust_info[0];
				var tmpDeviceNumber = cust_info.device_number == null ? inputDeviceNumber : cust_info.device_number;
				var tmpDeviceGuishu = cust_info.device_guishu == null ? 'CBSS' : cust_info.device_guishu;
				var tmpDeviceProducts = getByteLen(cust_info.devices_products) > 26 ? getByteSubstring(cust_info.devices_products, 0, 26) + '...' : cust_info.devices_products;
				var html = '<div class="info_user" name="info_detail" id="detail_1" deviceNumber="' + tmpDeviceNumber + '">'
						+ '<div class="info_user_detail">'
						+ '<div class="left_lable">设备号码:</div>'
						+ '<div class="left_lable">' + tmpDeviceNumber + '</div>'
						+ '</div>'
						+ '<div class="info_user_detail">'
						+ '<div class="left_lable">用户状态：</div>'
						+ '<div class="left_data_quarter">' + cust_info.device_status + '</div>'
						+ '</div>'
						+ '<div class="info_user_detail">'
						+ '<div class="left_lable">品<span class="space24"></span>牌：</div>'
						+ '<div class="left_data_quarter">' + cust_info.devices_brand + '</div>'
						+ '</div>'
						+ '<div class="info_user_detail">'
						+ '<div class="left_lable">产<span class="space24"></span>品：</div>'
						+ '<div class="left_data_quarter" title="' + cust_info.devices_products + '">' + tmpDeviceProducts + '</div>'
						+ '</div>'
						+ '<div class="info_user_detail">'
						+ '<div class="left_lable">归属系统：</div>'
						+ '<div class="left_data_quarter">' + tmpDeviceGuishu + '</div>'
						+ '</div>'
						+ '</div>';
				$('#btn_right').before(html);
				// TODO: 删除↓
//				if (null == cust_info.device_number) {
//					document.getElementById("deviceeNumber").innerHTML = m_deviceNumber;
//				} else {
//					document.getElementById("deviceeNumber").innerHTML = cust_info.device_number;
//				}
//				document.getElementById("deviceSatus").innerHTML = cust_info.device_status;
//				document.getElementById("deviceProduct").innerHTML = cust_info.devices_products;
//				document.getElementById("deviceName").innerHTML = cust_info.devices_brand;
//				if (null == cust_info.device_guishu) {
//					document.getElementById("deviceGuishu").innerHTML = "CBSS";
//				} else {
//					document.getElementById("deviceGuishu").innerHTML = cust_info.device_guishu;
//				}
			}
			bindDeviceEvent();
			
			// 选中第一个设备，并查询限制条件
			var firstObj = $('#detail_1');
			if (firstObj.length > 0) {
				firstObj.addClass('info_user_cur');
				queryLimitInfo(firstObj.attr('deviceNumber'));
			}
		},
		error: function() {
			$.error('设备信息查询错误，请重新查询！');
		}
	});
}

// 身份证号码查询业务信息
function queryBusiInfoByIdNumber() {
	$('[name="info_detail"]').remove();
	var data = {
		'id_number': inputIdNumber,
		'device_number': inputDeviceNumber,
		'id_type': 'ID001',
		'oper_no': operNo
	}
	var url = application.fullPath + "/authority/customer/queryBussInfoCQMess";
	$.ajax({
		url: url,
		type: 'post',
		data: data,
		waitMsg: '正在查询设备信息...',
		success: function(message) {
			if (message.type == 'error') {
				var errMessage = '设备信息查询失败，' + (message.content == null ? '请重新查询！' : message.content);
				$.error(errMessage);
				return;
			}
			$.each(message.args.deviceList, function(i, item) {
				var index = i + 1;
				var tmpDeviceAddress = getByteLen(item.devices_address) > 26 ? getByteSubstring(item.devices_address, 0, 26) + '...' : item.devices_address;
				var tmpDeviceProducts = getByteLen(item.devices_products) > 26 ? getByteSubstring(item.devices_products, 0, 26) + '...' : item.devices_products;
				if (item.devices_number.indexOf("comcis") > 0) {
					var html = '<div class="info_user" name="info_detail" id="detail_' + index + '" deviceNumber="' + item.devices_number + '">'
							+ '<div class="info_user_detail">'
							+ '<div class="left_lable">' + item.devices_number + '</div>'
							+ '</div>'
							+ '<div class="info_user_detail">'
							+ '<div class="left_lable">用户状态：</div>'
							+ '<div class="left_data_quarter">' + item.devices_status + '</div>'
							+ '</div>'
							+ '<div class="info_user_detail">'
							+ '<div class="left_lable">装机地址：</div>'
							+ '<div class="left_data_quarter" title="' + item.devices_address + '">' + tmpDeviceAddress + '</div>'
							+ '</div>'
							+ '<div class="info_user_detail">'
							+ '<div class="left_lable">产<span class="space24"></span>品：</div>'
							+ '<div class="left_data_quarter" title="' + item.devices_products + '">' + tmpDeviceProducts + '</div>'
							+ '</div>'
							+ '<div class="info_user_detail">'
							+ '<div class="left_lable">归属系统：</div>'
							+ '<div class="left_data_quarter"></div>'
							+ '</div>'
							+ '</div>';
					$('#btn_right').before(html);
				} else {
					var html = '<div class="info_user" name="info_detail" id="detail_' + index + '" deviceNumber="' + item.devices_number + '">'
							+ '<div class="info_user_detail">'
							+ '<div class="left_lable">' + item.devices_number + '</div>'
							+ '</div>'
							+ '<div class="info_user_detail">'
							+ '<div class="left_lable">用户状态：</div>'
							+ '<div class="left_data_quarter">' + item.devices_status + '</div>'
							+ '</div>'
							+ '<div class="info_user_detail">'
							+ '<div class="left_lable">品<span class="space24"></span>牌：</div>'
							+ '<div class="left_data_quarter">' + item.devices_brand + '</div>'
							+ '</div>'
							+ '<div class="info_user_detail">'
							+ '<div class="left_lable">产<span class="space24"></span>品：</div>'
							+ '<div class="left_data_quarter" title="' + item.devices_products + '">' + tmpDeviceProducts + '</div>'
							+ '</div>'
							+ '<div class="info_user_detail">'
							+ '<div class="left_lable">归属系统：</div>'
							+ '<div class="left_data_quarter"></div>'
							+ '</div>'
							+ '</div>';
					$('#btn_right').before(html);
				}
			});
			bindDeviceEvent();
			
			// 选中第一个设备，并查询限制条件
			var firstObj = $('#detail_1');
			if (firstObj.length > 0) {
				firstObj.addClass('info_user_cur');
				queryLimitInfo(firstObj.attr('deviceNumber'));
			}
		},
		error: function() {
			$.error('设备信息查询错误，请重新查询！');
		}
	});
}

// 查询限制条件
function queryLimitInfo(deviceNumber) {
	$('#limitInfos>tbody>tr').remove();
	var data = {
		'serial_number': deviceNumber,
		'oper_id': operNo
	}
	var url = application.fullPath + "/authority/transfer23To4/getDeviceNumber";
	$.ajax({
		url: url,
		type: 'post',
		data: data,
		waitMsg: '正在查询限制条件...',
		success: function(message) {
			if (message.type == 'error') {
				var errMessage = '限制条件查询失败，' + (message.content == null ? '请重新查询！' : message.content);
				$.error(errMessage);
				return;
			}
			details = message.args.num;
			dealArray = new Array();
			$.each(message.args.num, function(i, item) {
				var msg;
				var index = i + 1;
				var rads = '';
				var imgs ='';
				if(item.resp_oper_type != null){
					dealArray.push(item);
					var operType = item.resp_oper_type;
					var operTypeArray = operType.split(";");
					for (var j=0 ;j<(operTypeArray.length-1)/2;j++){
						if(j==0){
							rads += '<div><input type="radio" name="radio_' + item.requset_code + '" value="' + operTypeArray[2*j] + '" checked/>' + operTypeArray[2*j+1] + '</div>';
						}else{
							rads += '<div><input type="radio" name="radio_' + item.requset_code + '" value="' + operTypeArray[2*j] + '"/>' + operTypeArray[2*j+1] + '</div>';
						}
						
					}
				}
				if( item.resp_msg==null ){
					item.resp_msg="人工操作：报信息化部取消";
				}else{
					imgs = '<img id="img_'+index+'" src="' + imgDir + 'deal_detail.png" width="17" height="17" onclick="showDetail(' + index + ')"/>';
				}
				
				var html = '<tr code="' + item.requset_code + '" radioName="radio_' + index + '">'
						+ '<td class="text_center">' + item.requset_code + '</td>'
						+ '<td>' + item.requset_desc + '</td>'
						+ '<td class="text_center">'
						+ imgs
						+ '</td>'
						+ '<td>'+item.resp_msg+'</td>'
						+ '<td>'+rads+'</td>'
						+ '<td class="text_center" id="result_'+index+'">待处理</td>'
						+ '</tr>';
				$('#limitInfos>tbody').append(html);
			});
		},
		error: function() {
			$.error('限制条件查询错误，请重新查询！');
		}
	});
}

// 清空页面信息
function clearAll() {
	clearSearchInfo();
	clearCustomerInfo();
	clearLimitInfo();
}
// 置空查询信息
function clearSearchInfo() {
	$('#inputIdNumber').val('');
	$('#inputDeviceNumber').val('');
}
// 置空用户信息
function clearCustomerInfo() {
	$('#customerName').text('');
	$('#idType').text('');
	$('#idNumber').text('');
	$('#customerLevel').text('');
	$('[name="info_detail"]').remove();
	devCount = 0; // 设备总数
	devCurrent = 0; // 当前选中设备
	$("#btn_left").removeClass('btn_left_more').addClass('btn_left');
	$("#btn_right").removeClass('btn_right_more').addClass('btn_right');
}
// 置空限制条件
function clearLimitInfo() {
	$('#limitInfos>tbody>tr').remove();
}

// 判断字符串长度（汉字算两个字符，字母数字算一个字符）
function getByteLen(str) {
	var len = 0;
	for (var i = 0; i < str.length; i++) {
		var c = str.charCodeAt(i);
		if (c >= 0 && c <= 128) {
			len += 1;
		} else {
			len += 2;
		}
	}
	return len;
}

// 截取字符串（汉字算两个字符，字母数字算一个字符）
function getByteSubstring(str, begin, end) {
	var result = '';
	var index = -1;
	for (var i = 0; i < str.length; i++) {
		var c = str.charCodeAt(i);
		if (c >= 0 && c <= 128) {
			index += 1;
		} else {
			index += 2;
		}
		if (index >= begin && index <= end) {
			result += str.substring(i, i + 1);
		}
	}
	return result;
}
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
}
function hidediv(popWinId) {
	if (!window.XMLHttpRequest) { 
		for(var i=0;i<selects_display.length;i++){
			selects_display[i].style.display = "";
		}
	}
	document.getElementById("bg_mask").style.display ='none';
	document.getElementById(popWinId).style.display ='none';
}

function showDetail(index){
	var htmlNew = '';
	var html ='<tr>'
		+'<td class="text_center">'+details[index-1].resp_desc1+'</td>'
        +'<td class="text_center">'+details[index-1].resp_desc2+'</td>'
        +'<td class="text_center">'+getGroupLogoForCoredID(details[index-1].resp_desc3)+'</td>'
        +'<td class="text_center">'+getGroupTypeForCoredID(details[index-1].resp_desc4)+'</td>'
        +'<td class="text_center">'+getStatusForCoredID(details[index-1].resp_desc5)+'</td>'
		+'<td class="text_center">'+timeFormat(details[index-1].resp_desc6)+'</td>'
        +'<td class="text_center">'+timeFormat(details[index-1].resp_desc7)+'</td>'
        +'<td class="text_center">'+timeFormat(details[index-1].resp_desc8)+'</td>'
        +'<td class="text_center">'+operNo+'</td>'
        +'</tr>';
		htmlNew += html;
		$('#paylogTable>tbody').html(htmlNew);
		showdiv('zjdz_search');
}

function timeFormat(date){
	if(date != "{}"){
		var year = date.slice(0,4);
		var moth = date.slice(4,6);
		var day =  date.slice(6,8);
		var dateTime=year+"－"+moth+"－"+day;
		return  dateTime;
	}else{
		return  "";
	}
	
}

function getGroupLogoForCoredID(codeid){
	if(codeid == 0){
		return '主集团';
	}else if(codeid == 1){
		return '非主集团';
	}
}
function getGroupTypeForCoredID(codeid){
	if(codeid == 1){
		return '全国';
	}else if(codeid == 2){
		return '大区';
	}if(codeid == 3){
		return '省级';
	}else if(codeid == 4){
		return '地市级';
	}if(codeid == 5){
		return '区县';
	}else if(codeid == 6){
		return '个人自由组级';
	}else if(codeid == 7){
		return '未知';
	}
}
function getStatusForCoredID(codeid){
	if(codeid == 001){
		return '预开户';
	}else if(codeid == 101){
		return '正常在用';
	}if(codeid == 201){
		return '限制呼出';
	}else if(codeid == 202){
		return '报亭';
	}if(codeid == 203){
		return '强停';
	}else if(codeid == 204){
		return '欠费停机';
	}else if(codeid ==206 ){
		return '封锁';
	}else if(codeid == 207){
		return '死锁';
	}if(codeid == 208){
		return '高额停机';
	}else if(codeid == 209){
		return '中行停机保护';
	}if(codeid == 210){
		return '挂失';
	}else if(codeid == 214){
		return '长期欠费停机';
	}else if(codeid == 223){
		return '违约停机';
	}else if(codeid == 301){
		return '过户预约';
	}if(codeid == 302){
		return '退机预约';
	}else if(codeid == 303){
		return '正式退机';
	}if(codeid == 304){
		return '正式过户';
	}else if(codeid == 401){
		return '拆机预约';
	}else if(codeid == 402){
		return '正式拆机';
	}else if(codeid == 403){
		return '欠费拆机';
	}if(codeid == 404){
		return '报亭批量拆机';
	}else if(codeid == 408){
		return '3G转4G拆机';
	}if(codeid == 501){
		return '预配死卡未激活';
	}
}

