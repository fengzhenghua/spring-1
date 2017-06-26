$(document).ready(function() {
	tiHuanText();
	selectPage();
	$("#query").click(function() {
		var ordersubtype = $.trim($("#order_sub_type_status").val());
		if(ordersubtype=='10080'){//沃创富订单			
			selectWCFPage();
		}else{
		 selectPage();
		}
	});
	$('#order_sub_type_status').bind('change', function(){
		var order_sub_type = $.trim($("#order_sub_type_status").val());
		if(order_sub_type=='10000' ||order_sub_type=='10010' ){
		  if($("#order_processing_status option[value='2']").val()=='2'){  
		  }else{
			  //因为append只能追加到最后，为了让位置属性保持一致，先把竣工的删除了在在后面加进去
			 $("#order_processing_status option[value='4']").remove();
		     $("#order_processing_status").append("<option value='2'>待写卡</option>");
		     $("#order_processing_status").append("<option value='4'>竣工</option>"); //为Select追加一个Option(下拉项) 
		  }
		 }
		if(order_sub_type=='10020' || order_sub_type == '10030'){
		   $("#order_processing_status option[value='2']").remove();
		}
		if(order_sub_type == '10030'){
			$("#order_processing_status option[value='3']").text('预订单生成');
		}
	 });
});
//分页

function tiHuanText() {
	var flag2 ='0';
	var flag4 ='0';
	if($("#order_processing_status option[value='3']").val()=='3'&&$("#order_processing_status option[value='3']").text()=='待缴费'){
		$("#order_processing_status option[value='3']").remove();
		if($("#order_processing_status option[value='2']").val()=='2'){
			flag2 = '1';
			$("#order_processing_status option[value='2']").remove();			
		}	
		if($("#order_processing_status option[value='4']").val()=='4'){
			flag4 = '1';
			$("#order_processing_status option[value='4']").remove();
		}
		$("#order_processing_status").append("<option value='3'>待收费</option>");
		if (flag2=='1'){
			$("#order_processing_status").append("<option value='2'>待写卡</option>");
		}		
		if (flag4=='1'){
			$("#order_processing_status").append("<option value='4'>竣工</option>");
		}
	}
	

}

function selectPage() {
	var start_time = $.trim($("#end_time").val());
	var end_time = $.trim($("#end_time").val());
	var order_status = $.trim($("#order_processing_status").val());
	var order_sub_type = $.trim($("#order_sub_type_status").val());
	var device_number = $.trim($("#device_number").val());
	var cust_name = $.trim($("#cust_name").val());
	var receive = $("#receive").find("option:selected").text();
	var create_operator_id = "";
	if (receive == "接待人员") {
		 create_operator_id = "";
	} else {
	    create_operator_id = receive.split(/\s+/)[1];
	}
	if (cust_name == "姓名") {
		 cust_name = "";
	}
	if (device_number == "请输入手机号码") {
		 device_number = "";
	}
	
	var order_id = $.trim($("#order_ids").val());
	if (order_id == "云销售订单编号/沃创富提货单/身份证号") {
		 order_id = "";
	}
	var GetURl = application.fullPath + 'authority/order/queryOrderProsessing';
	$.ajax({
		url: GetURl,
		data: {
			"start_time":start_time,
			"end_time":end_time,
			"order_status":order_status,
			"order_sub_type":order_sub_type,
			"device_number":device_number,
			"cust_name":cust_name,
			"order_id":order_id,
			"create_operator_id":create_operator_id
		},
		dataType: 'json',
		type: 'post',
		waitMsg: "查询订单",
		success: function(page) {
			$("#list").html('');
			var htmlNew = '';
			if (page.dataRows != null) {
				if (page.dataRows.length > 0) {
					for (var i = 0; i < page.dataRows.length; i++) {
						var infoQryOrderVo = page.dataRows[i];
						var html = '<div class="list_table"><table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">'
							+ '<tr>';
						if(infoQryOrderVo.order_sub_type == "10010") {
							html +='<td width="90" height="89" align="left"><div class="open"><a href="javascript:;" onClick="jump(\''+ infoQryOrderVo.order_id+'\',\''+infoQryOrderVo.order_sub_type+'\',\''+infoQryOrderVo.tele_type+'\')">开户</a></div></td>';
						} else if(infoQryOrderVo.order_sub_type == "10020") {
							html +='<td width="90" height="89" align="left"><div class="charge"><a href="javascript:;" onClick="jump(\''+ infoQryOrderVo.order_id+'\',\''+infoQryOrderVo.order_sub_type+'\')">收费</a></div></td>';
						} else if(infoQryOrderVo.order_sub_type == "10030") {
							html +='<td width="90" height="89" align="left"><div class="change"><a href="javascript:;" onClick="jump(\''+ infoQryOrderVo.order_id+'\',\''+infoQryOrderVo.order_sub_type+'\')">变更</a></div></td>';
						} else if(infoQryOrderVo.order_sub_type == "10060") {
							html +='<td width="90" height="89" align="left"><div class="archives"><a href="javascript:;" onClick="jump(\''+ infoQryOrderVo.order_id+'\',\''+infoQryOrderVo.order_sub_type+'\')">返档</a></div></td>';
						}
						if (infoQryOrderVo.wo_type == "2"||infoQryOrderVo.wo_type == "4") { //宽带单装、沃TV
					    	var lanywNumber = infoQryOrderVo.lanywNumber == null ? '无' : infoQryOrderVo.lanywNumber;
					    	var custName = infoQryOrderVo.cust_name == 0 ? '无' : infoQryOrderVo.cust_name;
						    html += '<td width="270" class="dashed" id=' + infoQryOrderVo.order_id + '>'
								+ '<div class="no_">'
							    + '<a href="javascript:;" onClick="jump(\'' + infoQryOrderVo.order_id + '\',\'' + infoQryOrderVo.order_sub_type + '\',\'' + infoQryOrderVo.tele_type + '\')">'
							    + '订单编号:' + infoQryOrderVo.order_id + ' <br />'
							    + '客户号码：' + lanywNumber + '</a>'
								+ '</div> </td> <td width="254" class="dashed"> <div class="no_">'
								+ '<a href="javascript:;" onClick="jump(\'' + infoQryOrderVo.order_id + '\',\'' + infoQryOrderVo.order_sub_type + '\',\'' + infoQryOrderVo.tele_type + '\')">'
								+ '订单时间:' + infoQryOrderVo.creat_time_str + ' <br />'
								+ '客户姓名：' + custName + '</a>'
								+ '</div> </td> <td width="254" class="dashed"> <div class="no_">'
								+ '<a href="javascript:;" onClick="jump(\'' + infoQryOrderVo.order_id + '\',\'' + infoQryOrderVo.order_sub_type + '\',\'' + infoQryOrderVo.tele_type + '\')">'
								+ '接待人员:' + infoQryOrderVo.oper_name + infoQryOrderVo.oper_no + '<br />';
						} else {
							var deviceNumber = infoQryOrderVo.device_number == null ? '无' : infoQryOrderVo.device_number;
							var custName = infoQryOrderVo.cust_name == 0 ? '无' : infoQryOrderVo.cust_name;
							html += '<td width="270" class="dashed" id=' + infoQryOrderVo.order_id + '>'
								+ '<div class="no_">'
							    + '<a href="javascript:;" onClick="jump(\'' + infoQryOrderVo.order_id + '\',\'' + infoQryOrderVo.order_sub_type + '\',\'' + infoQryOrderVo.tele_type + '\')">'
							    + '订单编号:' + infoQryOrderVo.order_id + ' <br />'
							    + '客户号码：' + deviceNumber + '</a>'
								+ '</div> </td> <td width="254" class="dashed"> <div class="no_">'
								+ '<a href="javascript:;" onClick="jump(\'' + infoQryOrderVo.order_id + '\',\'' + infoQryOrderVo.order_sub_type + '\',\'' + infoQryOrderVo.tele_type + '\')">'
								+ '订单时间:' + infoQryOrderVo.creat_time_str + ' <br />'
								+ '客户姓名：' + custName + '</a>'
								+ '</div> </td> <td width="254" class="dashed"> <div class="no_">'
								+ '<a href="javascript:;" onClick="jump(\'' + infoQryOrderVo.order_id + '\',\'' + infoQryOrderVo.order_sub_type + '\',\'' + infoQryOrderVo.tele_type + '\')">'
								+ '接待人员:' + infoQryOrderVo.oper_name + infoQryOrderVo.oper_no + '<br />';
						}
						if (infoQryOrderVo.tele_type != 'LAN' && infoQryOrderVo.tele_type != 'M165'&& infoQryOrderVo.tele_type != 'IPTV'&& infoQryOrderVo.tele_type !='WO_FA') { //手机开户
							if (infoQryOrderVo.order_status == "A10" && infoQryOrderVo.pay_flag == "C") {
								html += '订单状态：<a href="###" class="arrow" onClick=\"jiaofei(\''+ infoQryOrderVo.order_id + '\',\'' + infoQryOrderVo.order_sub_type + '\',\'' + infoQryOrderVo.tele_type + '\')">待收费';
							} else if (infoQryOrderVo.order_status == "A10" && infoQryOrderVo.pay_flag == "Y") {
								html += '订单状态：<a href="###" class="arrow" onClick=\"peika(\'' + infoQryOrderVo.order_id + '\')">待写卡';
							} else if (infoQryOrderVo.order_status == "A10" && infoQryOrderVo.pay_flag == "P") {
								html += '订单状态：<a href="###" class="arrow" onClick=\"jiaofei(\'' + infoQryOrderVo.order_id + '\',\'' + infoQryOrderVo.order_sub_type + '\',\'' + infoQryOrderVo.tele_type + '\')">待收费';
							} else if (infoQryOrderVo.order_status == "A30") {
								html += '订单状态：<a href="###" class="arrow" onClick=\"jungong(\'' + infoQryOrderVo.order_id + '\',\'' + infoQryOrderVo.order_sub_type + '\',\'' + infoQryOrderVo.tele_type + '\')">竣工';
							} else if (infoQryOrderVo.order_status == "A20") {
								
							} else if (infoQryOrderVo.order_status== "B00" && (infoQryOrderVo.pay_flag == "P" || infoQryOrderVo.pay_flag == "C")) {
								html += '订单状态：<a href="###" class="arrow" onClick=\"jiaofei(\'' + infoQryOrderVo.order_id + '\',\'' + infoQryOrderVo.order_sub_type + '\',\'' + infoQryOrderVo.tele_type + '\')">待收费';
							} else if (infoQryOrderVo.order_status == "B10" && infoQryOrderVo.pay_flag == "Y") {
								html += '订单状态：<a href="###" class="arrow" onClick=\"jungong(\'' + infoQryOrderVo.order_id + '\',\'' + infoQryOrderVo.order_sub_type + '\',\'' + infoQryOrderVo.tele_type + '\')">竣工';
							} else if (infoQryOrderVo.order_status == "C00") {
								html += '订单状态：<a href="###" class="arrow" onClick=\"jiaofei(\'' + infoQryOrderVo.order_id + '\',\'' + infoQryOrderVo.order_sub_type + '\',\'' + infoQryOrderVo.tele_type + '\')">待收费';
							} else if (infoQryOrderVo.order_status == "C10") {
								html += '订单状态：<a href="###" class="arrow" onClick=\"jungong(\'' + infoQryOrderVo.order_id + '\',\'' + infoQryOrderVo.order_sub_type + '\',\'' + infoQryOrderVo.tele_type + '\')">竣工';
							}
							html += '<div class="tip_box tip_none" id="tip_box' + infoQryOrderVo.order_id + '\"> <div class="tip_box_top"></div> <div class="tip_box_bg"> <div class="close"></div>';
							if (infoQryOrderVo.order_sub_type == "10010") {
								html += '<table width="325" border="0" cellspacing="0" cellpadding="0"> <tr> <td colspan="6"><div class="pro_five_1" ><img width="320" height="20" id="progress' + infoQryOrderVo.order_id + '\" src="' + application.fullPath + 'images/five_1.png" /></div></td> </tr>'
									+ '<td align="left" width="78" height="20">预订单生成</td>'
									+ '<td align="left" width="76">终端调拨</td>'
									+ '<td align="left" width="64">待收费</td>'
									+ '<td align="left" width="75">待写卡</td>'
									+ '<td align="left">竣工</td>';
							}
							if (infoQryOrderVo.order_sub_type == "10020") {
								html += '<table width="325" border="0" cellspacing="0" cellpadding="0"> <tr> <td colspan="6"><div class="progress_1" ><img width="320" height="20" id="progress' + infoQryOrderVo.order_id + '\" src="' + application.fullPath + 'images/five_1.png" /></div></td> </tr>'
									+ '<td align="left" width="78" height="20">预订单生成</td>'
									+ '<td align="left" width="76">&nbsp;</td>'
									+ '<td align="left" width="64">待收费</td>'
									+ '<td align="left" width="75">&nbsp;</td>'
									+ '<td align="left">竣工</td>';
							}
							if (infoQryOrderVo.order_sub_type == "10030") {
								html += '<table width="325" border="0" cellspacing="0" cellpadding="0"> <tr> <td colspan="4"><div class="" ><img id="progress' + infoQryOrderVo.order_id + '\" src="' + application.fullPath + 'images/tow_1.png" /></div></td> </tr>'
									+ '<tr> <td height="22" width="130" align="left">&nbsp;</td>'
									+ '<td width="140" align="left">预订单生成</td>'
									+ '<td width="61" align="right">竣工</td>';
							}
							if (infoQryOrderVo.order_sub_type == "10060") {
								html += '<table width="325" border="0" cellspacing="0" cellpadding="0"> <tr> <td colspan="4"><div class="" ><img id="progress' + infoQryOrderVo.order_id + '\" src="' + application.fullPath + 'images/tow_2.png" /></div></td> </tr>'
									+ '<tr> <td height="22" width="130" align="left">&nbsp;</td>'
									+ '<td width="140" align="left">返档单生成</td>'
									+ '<td width="61" align="right">竣工</td>';
							}
						} else { //宽带开户订单
							if ('2' == infoQryOrderVo.wo_type || ('3' == infoQryOrderVo.wo_type && infoQryOrderVo.wo_fa_phone_number != null && infoQryOrderVo.wo_fa_phone_number != '')||infoQryOrderVo.is_writecar=='0'||infoQryOrderVo.wo_type == "4") { //宽带单装  、沃家庭旧手机号、智慧沃家无写卡号码
								if (infoQryOrderVo.order_status == "A10" && infoQryOrderVo.pay_flag == "C") {
									html += '订单状态：<a href="###" class="arrow" onClick=\"wojiaofei(\'' + infoQryOrderVo.order_id + '\',\'' + infoQryOrderVo.order_sub_type + '\',\'' + infoQryOrderVo.tele_type + '\',\'' + infoQryOrderVo.wo_type + '\',\'' + infoQryOrderVo.wo_fa_phone_number + '\',\'' + infoQryOrderVo.is_writecar + '\')">待收费';
								} else if (infoQryOrderVo.order_status == "A10" && infoQryOrderVo.pay_flag == "P") {
									html += '订单状态：<a href="###" class="arrow" onClick=\"wojiaofei(\'' + infoQryOrderVo.order_id + '\',\'' + infoQryOrderVo.order_sub_type + '\',\'' + infoQryOrderVo.tele_type + '\',\'' + infoQryOrderVo.wo_type + '\',\'' + infoQryOrderVo.wo_fa_phone_number + '\',\'' + infoQryOrderVo.is_writecar + '\')">待收费';
								} else if (infoQryOrderVo.order_status == "A30" && infoQryOrderVo.pay_flag == "Y") {
									html += '订单状态：<a href="###" class="arrow" onClick=\"woend(\'' + infoQryOrderVo.order_id + '\',\'' + infoQryOrderVo.wo_type + '\',\'' + infoQryOrderVo.wo_fa_phone_number + '\')">开户完成';
								}
								html += '<div class="tip_box tip_none" id="tip_box' + infoQryOrderVo.order_id + '\"> <div class="tip_box_top"></div> <div class="tip_box_bg"> <div class="close"></div>';
								if (infoQryOrderVo.order_sub_type == "10010") {
									html += '<table width="325" border="0" cellspacing="0" cellpadding="0"> <tr> <td colspan="6"><div class="pro_five_1" ><img width="320" height="20" id="progress' + infoQryOrderVo.order_id + '\" src="' + application.fullPath + 'images/four_1.jpg" /></div></td> </tr>'
										+ '<td align="left" width="65">&nbsp;</td>'
										+ '<td align="left" width="63">开户</td>'
										+ '<td align="left" width="62" >待收费</td>'
										+ '<td align="left" width="103" >开户完成</td>'
										+ '<td align="right">竣工</td>'
								}
							} else { //智慧沃家宽带
								if (infoQryOrderVo.order_status == "A10" && infoQryOrderVo.pay_flag == "C") {
									html += '订单状态：<a href="###" class="arrow" onClick=\"wojiaofei(\'' + infoQryOrderVo.order_id + '\',\'' + infoQryOrderVo.order_sub_type + '\',\'' + infoQryOrderVo.tele_type + '\',\'1' + '\',\'' + '\',\'' + infoQryOrderVo.is_writecar + '\')">待收费';
								} else if (infoQryOrderVo.order_status == "A10" && infoQryOrderVo.pay_flag == "P") {
									html += '订单状态：<a href="###" class="arrow" onClick=\"wojiaofei(\'' + infoQryOrderVo.order_id + '\',\'' + infoQryOrderVo.order_sub_type + '\',\'' + infoQryOrderVo.tele_type + '\',\'1' + '\',\'' + '\',\'' + infoQryOrderVo.is_writecar + '\')">待收费';
								} else if (infoQryOrderVo.order_status == "A10" && infoQryOrderVo.pay_flag == "Y") {
									html += '订单状态：<a href="###" class="arrow" onClick=\"wopeika(\'' + infoQryOrderVo.order_id + '\')">待写卡';
								} else if (infoQryOrderVo.order_status == "A30" && infoQryOrderVo.pay_flag == "Y") {
									html += '订单状态：<a href="###" class="arrow" onClick=\"woend(\'' + infoQryOrderVo.order_id + '\',\'1' + '\',\'' + '\')">开户完成';
								}
								html += '<div class="tip_box tip_none" id="tip_box' + infoQryOrderVo.order_id + '\"> <div class="tip_box_top"></div> <div class="tip_box_bg"> <div class="close"></div>';
								if (infoQryOrderVo.order_sub_type == "10010") {
									html += '<table width="325" border="0" cellspacing="0" cellpadding="0"> <tr> <td colspan="6"><div class="pro_five_1" ><img width="320" height="20" id="progress' + infoQryOrderVo.order_id + '\" src="' + application.fullPath + 'images/five_1.png" /></div></td> </tr>'
										+ '<tr> <td align="left" width="38" height="22">&nbsp;</td>'
										+ '<td align="left" width="50">开户</td>'
										+ '<td align="left" width="65">待收费</td>'
										+ '<td align="left" width="50">待写卡</td>'
										+ '<td align="left" width="90">开户完成</td>'
										+ '<td align="left">竣工</td>';
								}
							}
						}
						html += '</tr></table></div><div class="tip_box_bottom"></div></div>'
							+ '</a></div> </td> <td> <div class="no_"> 剩余时间<br /><span class="red" status="' + infoQryOrderVo.order_status + '\"  timeStyle="' + infoQryOrderVo.create_time_add_str + '\"  id="time' + infoQryOrderVo.order_id + '\">00:07:00</span></div> </td></tr></table></div>';  
						htmlNew +=html;
					}
					$("#list").append(htmlNew);
					startTime();
				} else {
					htmlNew = '<div class="list_table" style="height:380px;">  <table width="1000" border="0" align="center" cellpadding="0" cellspacing="0"><tr>没有符合条件的订单</tr>';
					$("#list").append(htmlNew);
				}
			} else {
				htmlNew = '<div class="list_table" style="height:380px;">  <table width="1000" border="0" align="center" cellpadding="0" cellspacing="0"><tr>没有符合条件的订单</tr>';
				$("#list").append(htmlNew);
			}
		}
	});
}

function selectWCFPage() {//查询沃创富订单
	var end_time = $.trim($("#end_time").val());
	var order_status = $.trim($("#order_processing_status").val());
	var order_sub_type = $.trim($("#order_sub_type").val());
	var device_number = $.trim($("#device_number").val());
	var cust_name = $.trim($("#cust_name").val());
	var receive = $("#receive").find("option:selected").text();
	var create_operator_id = "";	
	var order_id = $.trim($("#order_ids").val());
	if (receive == "接待人员") {
		 create_operator_id = "";
	} else {
	    create_operator_id = receive.split(/\s+/)[1];
	}
	if (cust_name == "姓名") {
		 cust_name = "";
	}
	if (device_number == "请输入手机号码") {
		 device_number = "";
	}

	if (order_id == "云销售订单编号/沃创富提货单/身份证号"||order_id=="") {
		 order_id = "";
		
	}else{
		 end_time="";//订单不为空就把时间条件去掉
	}
	var GetURl = application.fullPath + 'authority/restToController/getNewWcfOrder';
	var isIDcard=/^(\d{15}|\d{17}[x0-9])$/i;
	if (isIDcard.test(order_id))//输入的是身份证号
	{    
		GetURl = application.fullPath + 'authority/restToController/getWcfOrderByNumberId';
	}	
	
	$.ajax({
		url: GetURl,
		data: {			
			"create_date":end_time,
			"order_status":order_status,
			"order_sub_type":order_sub_type,
			"device_number":device_number,
			"cust_name":cust_name,
			"wcf_order_id":order_id,
			"id_number":order_id,
			"create_operator_id":create_operator_id
		},
		dataType: 'json',
		type: 'post',
		waitMsg: "查询订单",
		success: function(page) {
			$("#list").html('');
			if(page.type=="error"){
				$.alert(page.content);
				return;
			}
			var htmlNew = '';
			if (page.args!= null) {
			if (page.args.order_list != null) {
				if (page.args.order_list.length > 0) {
					for (var i = 0; i < page.args.order_list.length; i++) {
						var infoQryOrderVo = page.args.order_list[i];
						var html = '<div class="list_table"><table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">'
							+ '<tr>';
							html +='<td width="90" height="89" align="left"><div class="wo"><a href="javascript:;" onClick="jumpWcf(\''+ infoQryOrderVo.wcf_order_id+'\')">沃创富订单</a></div></td>';
						
					    html += '<td width="270" class="dashed" id=' + infoQryOrderVo.wcf_order_id + '>'
							+ '<div class="no_">'
						    + '<a href="javascript:;" onClick="jumpWcf(\'' + infoQryOrderVo.wcf_order_id + '\')">'
						    + '订单编号:' + infoQryOrderVo.order_number + ' <br />'
						    + '客户号码：' + infoQryOrderVo.device_number + '</a>'
							+ '</div> </td> <td width="254" class="dashed"> <div class="no_">'
							+ '<a href="javascript:;" onClick="jumpWcf(\'' + infoQryOrderVo.wcf_order_id + '\')">'
							+ '订单时间:' + infoQryOrderVo.create_date + ' <br />';
					    if ('5'!= infoQryOrderVo.status){
					       html += '订单状态：<font color="red">待写卡</font></a>';
					    }else{
					       html += '订单状态：<font color="red">竣工</font></a>';
					    }
					    
					    html += '</div> </td> <td width="254" class="dashed"> <div class="no_">'
							+ '<a href="javascript:;" >';
											
						html += '<div class="tip_box tip_none" id="tip_box' + infoQryOrderVo.wcf_order_id + '\"> <div class="tip_box_top"></div> <div class="tip_box_bg"> <div class="close"></div>';
						
							
					
						html += '</tr></table></div><div class="tip_box_bottom"></div></div>'
							+ '</a></div> </td> <td> </td></tr></table></div>';  
						htmlNew +=html;
					}
					$("#list").html(htmlNew);
					//startTime();
				} else {
					htmlNew = '<div class="list_table" style="height:380px;">  <table width="1000" border="0" align="center" cellpadding="0" cellspacing="0"><tr>没有符合条件的订单</tr>';
					$("#list").html(htmlNew);
				}
			} else {
				htmlNew = '<div class="list_table" style="height:380px;">  <table width="1000" border="0" align="center" cellpadding="0" cellspacing="0"><tr>没有符合条件的订单</tr>';
				$("#list").html(htmlNew);
			}
		}else {
			htmlNew = '<div class="list_table" style="height:380px;">  <table width="1000" border="0" align="center" cellpadding="0" cellspacing="0"><tr>没有符合条件的订单</tr>';
			$("#list").html(htmlNew);
		}
		}
	});
}
function startTime()
{   
 var foo = $("span[class='red']");
 $(foo).each(
	function() {
		var fooId = $(this).attr("id");
		$("#" + fooId).html('');
		var orderStatus = $("#" + fooId).attr("status");// 竣工的默认都是0
		if (orderStatus == 'B10' || orderStatus == 'A30') {
			var htmlTime = "00:00:00";
			$("#" + fooId).append(htmlTime)
		} else {
			var strTime = $("#" + fooId).attr("timeStyle");
			//var date = new Date().toLocaleString().replace(/[年月]/g, '-').replace(/[日上下午]/g, '');
			var result = comptime(strTime);
			if (result == '0') {
				var timearr = strTime.replace(" ", ":").replace(/\:/g,"-").split("-");
				var s1 = timearr[0];
				var s2 = timearr[1];
				var s3 = timearr[2];
				var s4 = timearr[3];
				var s5 = timearr[4];
				var s6 = timearr[5];
				var ts = (new Date(s1, s2, s3, s4, s5, s6))- (new Date());
				var mm = parseInt(ts / 1000 / 60 % 60, 10);
				var ss = parseInt(ts / 1000 % 60, 10);
				if (mm == 0 && ss == 0) {
					mm = "0" + mm;
					ss = "0" + ss;
					var htmlTime = "00" + ":" + mm + ":" + ss;
					$("#" + fooId).append(htmlTime);
				} else {
					if (mm < 10) {
						mm = "0" + mm;
					}
					if (ss < 10) {
						ss = "0" + ss;
					}
					var htmlTime = "00" + ":" + mm + ":" + ss;
					$("#" + fooId).append(htmlTime);
					t = setTimeout('startTime()', 1000);
					//t=setTimeout(startTime(''+order_id+''),1000);
				}
			} else {
				var htmlTime = "00:00:00";
				$("#" + fooId).append(htmlTime);
			}
		}

	})
}
function comptime(beginTime){   
	/*var beginTimes=beginTime.substring(0,10).split('-');  
	var endTimes=endTime.substring(0,10).split('-');  
	beginTime=beginTimes[1]+'-'+beginTimes[2]+'-'+beginTimes[0]+' '+beginTime.substring(10,19);  
	endTime=endTimes[1]+'-'+endTimes[2]+'-'+endTimes[0]+' '+endTime.substring(10,19);     
	var a =(Date.parse(endTime)-Date.parse(beginTime))/3600/1000;  
	if(a<0){  
		return "0"; //endTime小
	}else if (a>0){  
		return "1"; //endTime大 
	}else if (a==0){  
	   alert("2");  //时间相等
	}else{  
	return "";  
	}  */
	
	var str = beginTime.replace(/-/g,"/");
	var date = new Date(str);
	  if(date>new Date())  
	  { 
		  return "0"; 
	  }else{
		  return "1"; 
	  }
}

function peika(order_id){
	 $("#progress"+order_id).attr("src",application.fullPath +"images/five_4.png");
	// $("#progress"+order_id).addClass("pro_five_4"); 
	 $("#tip_box"+order_id).toggle(); 
}
function wopeika(order_id){
	 $("#progress"+order_id).attr("src",application.fullPath +"images/five_3.png");
	// $("#progress"+order_id).addClass("pro_five_4"); 
	 $("#tip_box"+order_id).toggle(); 
}
function woend(order_id,wo_type,wo_fa_phone_number){
	if('2'==wo_type||(wo_type=='3'&&wo_fa_phone_number!=null&&wo_fa_phone_number!='')){//宽带单装
		 $("#progress"+order_id).attr("src",application.fullPath +"images/four_3.png");
			// $("#progress"+order_id).addClass("pro_five_4"); 
			 $("#tip_box"+order_id).toggle(); 
	}else{
	 $("#progress"+order_id).attr("src",application.fullPath +"images/five_4.png");
	// $("#progress"+order_id).addClass("pro_five_4"); 
	 $("#tip_box"+order_id).toggle(); 
	}
}
function wojiaofei(order_id,order_sub_type,tele_type,wo_type,wo_fa_phone_number,is_writecar){ 
	if('2'==wo_type||(wo_type=='3'&&wo_fa_phone_number!=null&&wo_fa_phone_number!='')||is_writecar=='0'||'4'==wo_type){//宽带单装、沃家庭无写卡手机号、智慧沃家无写卡手机号、沃TV
		if(order_sub_type=='10010'){
			   $("#progress"+order_id).attr("src",application.fullPath +"images/four_2.png");
			   //$("#progress"+order_id).addClass("pro_five_3"); 
			   $("#tip_box"+order_id).toggle();
		      }
	}else{
	 if(order_sub_type=='10010'){
	   $("#progress"+order_id).attr("src",application.fullPath +"images/five_2.png");
	   //$("#progress"+order_id).addClass("pro_five_3"); 
	   $("#tip_box"+order_id).toggle();
      }
	}
	
}
function jiaofei(order_id,order_sub_type){ 
	if(order_sub_type=='10010'){
	   $("#progress"+order_id).attr("src",application.fullPath +"images/five_3.png");
	   //$("#progress"+order_id).addClass("pro_five_3"); 
	   $("#tip_box"+order_id).toggle();
	}
	if(order_sub_type=='10020'){
		$("#progress"+order_id).attr("src",application.fullPath +"images/three_2.png");
		//$("#progress"+order_id).addClass("progress_3");
		$("#tip_box"+order_id).toggle(); 
	}
	if(order_sub_type=='10030'){
		$("#progress"+order_id).attr("src",application.fullPath +"images/tow_1.png");
		//$("#progress"+order_id).addClass("progress_3");
		$("#tip_box"+order_id).toggle(); 
	}
}

function jungong(order_id,order_sub_type){ 
	if(order_sub_type=='10010'){
		$("#progress"+order_id).attr("src",application.fullPath +"images/five_5.png");
	  // $("#progress"+order_id).addClass("pro_five_5");
	   $("#tip_box"+order_id).toggle(); 
	}
	if(order_sub_type=='10020'){
		$("#progress"+order_id).attr("src",application.fullPath +"images/three_3.png");
		//$("#progress"+order_id).addClass("progress_5");
		$("#tip_box"+order_id).toggle(); 
	 }
	if(order_sub_type=='10030'){
		$("#progress"+order_id).attr("src",application.fullPath +"images/tow_2.png");
		//$("#progress"+order_id).addClass("progress_5");
		$("#tip_box"+order_id).toggle(); 
	}
	if(order_sub_type=='10060'){
		$("#progress"+order_id).attr("src",application.fullPath +"images/tow_2.png");
		//$("#progress"+order_id).addClass("progress_5");
		$("#tip_box"+order_id).toggle(); 
	}
}
function jump(order_id, order_sub_type, tele_type) {
	var province_code = $("#province_code").val();
	var role_type = $("#role_type").val();
	if (province_code == 'cq') {
		if (order_sub_type == '10010') {
			if (tele_type == 'LAN' || tele_type == 'M165' || tele_type == 'WO_FA'||tele_type == 'IPTV') { //智慧沃家、宽带开户、沃家庭、沃TV
				window.open(application.fullPath + "authority/dealShowOrder/woOrderDetail?order_id=" + order_id, "order_id" + order_id);	
			} else {
				window.open(application.fullPath + "authority/dealShowOrder/showOrder?order_id=" + order_id , "order_id" + order_id);	
			}
		} else if (order_sub_type == '10020') {
			window.open(application.fullPath + "authority/dealShowOrder/showOrderFee?order_id=" + order_id, "order_id" + order_id);
		} else if (order_sub_type == '10030') {
			//YUN-778
			//window.open(application.fullPath+"authority/dealShowOrder/showOrderChange?order_id="+order_id,"order_id"+order_id);
			window.open(application.fullPath + "authority/dealShowOrder/showOrderChange?order_id=" + order_id);
		} else if (order_sub_type == '10060') {
			window.open(application.fullPath + "authority/dealShowOrder/showOrderArchives?order_id=" + order_id, "order_id" + order_id);
		}
	} else {
		if (role_type == '1') {
			if (order_sub_type == '10010') {
				if (tele_type == 'LAN' || tele_type == 'M165' || tele_type == 'WO_FA'||tele_type == 'IPTV') { //智慧沃家、宽带开户、沃家庭、沃TV
					window.open(application.fullPath + "authority/dealShowOrder/woOrderDetail?order_id=" + order_id, "order_id" + order_id);	
				} else {
					window.open(application.fullPath + "authority/dealShowOrder/showOrder?order_id=" + order_id , "order_id" + order_id);	
				}
			} else if (order_sub_type == '10020') {
				window.open(application.fullPath + "authority/dealShowOrder/showOrderFee?order_id=" + order_id, "order_id" + order_id);
			} else if (order_sub_type == '10030') {
				//YUN-778
				//window.open(application.fullPath+"authority/dealShowOrder/showOrderChange?order_id="+order_id,"order_id"+order_id);
				window.open(application.fullPath + "authority/dealShowOrder/showOrderChange?order_id=" + order_id);
			} else if (order_sub_type == '10060') {
				window.open(application.fullPath + "authority/dealShowOrder/showOrderArchives?order_id=" + order_id, "order_id" + order_id);
			}
		} else {
			var content = '<div class="msgbox"><div class="serial" id="orderId">订单编号：' + order_id + '</div><dl><dt>请输入订单验证码：</dt> <dd><input type="text" id="tattedCode" /></dd></dl> <div class="intro">如果遗忘，请从手机沃受理“个人中心 - 订单”查询</div> <div class="msgbox_ok"><a href="###" id="btn_dev_qry_ok">确定</a></div></div>';
			$.dialog({
				top: 10,
				width: 400,
				draggable: false,
				content: content
			});
			$("#btn_dev_qry_ok").bind("click", function() {
				var tattedCode = $("#tattedCode").val();
				if ('' == tattedCode) {
					$.alert("请填写订单验证码！");
					return;
				}
				$.ajax({
					url: application.fullPath + 'authority/dealShowOrder/getDevOrderRel',
					type: 'post',
					data: {
						"order_id": order_id
					},
					waitMsg: "正在查询！",
					success: function(message) {
						var rand_id = message.args.rand_id;
						if (tattedCode == rand_id) {
							if (order_sub_type == '10010') {
								if (tele_type == 'LAN' || tele_type == 'M165'||tele_type == 'IPTV') { //宽带开户
									window.open(application.fullPath + "authority/dealShowOrder/woOrderDetail?order_id=" + order_id, "order_id" + order_id);	
								} else {
									window.open(application.fullPath + "authority/dealShowOrder/showOrder?order_id=" + order_id, "order_id" + order_id);	
								}
							} else if (order_sub_type == '10020') {
								window.open(application.fullPath + "authority/dealShowOrder/showOrderFee?order_id=" + order_id, "order_id" + order_id);
							}
							$(".xxDialog").remove();
							$(".dialogOverlay").remove();
						}else{
							$.alert("订单验证码错误请重新输入");
							return; 
						}
					},
					error: function(message) {
						$.alert("查询订单验证码失败");
					}
					
				});	
			});
		}
	}
}

function jumpWcf(order_id) {
	window.open(application.fullPath + "/authority/index/predictOrderDeal?wcf_order_id=" + order_id, "wcf_order_id" + order_id);	
}
