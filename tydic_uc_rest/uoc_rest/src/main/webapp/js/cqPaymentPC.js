/******************************************************************
** 文件名: cqPcCharge.js
** 创建人: gaowei
** 日　期: 2016/05/13
** 描　述: 用户列表查询接口
** 修改人:
** 日　期:
** 描　述:
** 版　本: 1.0
********************************************************************/
var m_deviceNumber = null;
var m_idNumber = null;
var devNoo = null;
var tele_typee = null;
$(document).ready(function(){
	var operNo = $('#operNo').val();
	var m_business_flag = null;
	var index =null;
	$("#broandInfo").hide();
	$("#business_flag").hide();
	$("#tele_type").hide();
	//1、用户信息的显示初始化
	//注意给每个框赋一个ID，以detail_开头，加序号结束，如detail_1
	////左右按钮不可移
	$("#btn_left").removeClass('btn_left_more');
	$("#btn_left").addClass('btn_left');
	$("#btn_right").removeClass('btn_right_more');
	$("#btn_right").addClass('btn_right');
	//设备号码查询业务信息
	$("#detail").click(function(){
		//加载余额信息
		m_deviceNumber = $('#deviceNumber').val();
		if(m_deviceNumber.indexOf("comcis")>0){
	   			$("#broandInfo").show();
	   			$("#phoneInfo").hide();
	   		 } else {
	   			$("#phoneInfo").show();
	   			$("#broandInfo").hide();
	   			document.getElementById("payMoney").value = "";//缴费金额置为空
			    document.getElementById('payMoney').readOnly = false ;//宽带续费input恢复
	   		 }
		qryBalance();
	});
	
	$("#deviceNumber").keyup(function() {
		var devNo = $('#deviceNumber').val();
		if(devNo.indexOf("comcis")>0){
			$("#broandInfo").show();
			$("#phoneInfo").hide();
		} else {
			$("#phoneInfo").show();
			$("#broandInfo").hide();
			document.getElementById("payMoney").value = "";//缴费金额置为空
			document.getElementById('payMoney').readOnly = false ;//宽带续费input恢复
		}
	});
});
function queryInfo() {
	m_deviceNumber = $("#deviceNumber").val();
	m_idNumber = $("#idNumber").val();
	if(0 == m_idNumber&& 0 == m_deviceNumber){
		$.error("请输入身份证号码或设备号码进行查询!");
	} else if(0 < idNumber.value && 0 < deviceNumber.value){
		$.error("不允许同时输入身份证号码和设备号码进行查询!");
	} else {
		queryUserInfo();
	}
}
function queryUserInfo(){
	m_deviceNumber = $("#deviceNumber").val();
	m_idNumber = $("#idNumber").val();
    var operNo = $('#operNo').val();
	var card_type = "ID001";
	var data={
			  'id_number' : idNumber.value,
			  'device_number' : m_deviceNumber,
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
			    	} else {
			    		$.error("查询失败 ,请重新查询！");
			    	}
			    }
			    var cust_info=message.args.cust_info[0];
			    document.getElementById("custName").innerHTML = cust_info.cust_name;
			    document.getElementById("idType").innerHTML = "身份证";
			    document.getElementById("iddNumber").innerHTML = cust_info.id_number;
			    document.getElementById("cardType").innerHTML = "";
			    queryBusiInfo();
			},
			error:function(){
				$.error("查询错误，请重新查询！ ");
			}
			
		});
}
function queryBusiInfo(){
	if(0 != m_deviceNumber ){
		//设备号码查询业务信息
		queryBusiInfoByDeviceNumber();
	} else {
		//身份证号码查业务信息
		queryBusiInfoByIdNumber();
	}
}
function queryBusiInfoByDeviceNumber(){
	$("#detail").show();
	$("[name='info_detail']").remove();
	var card_type = "ID001";
	var operNo = $('#operNo').val();
	var data={
			  'id_number' : m_idNumber,
			  'device_number' : m_deviceNumber,
			  'id_type' : card_type,
			  'oper_no' : operNo
		  }
	if(deviceNumber.value.indexOf("comcis")>0){
		$("#info_address").show();
		var url = application.fullPath + "/authority/customer/queryCustomerCQMess";
		$.ajax({
			url:url,
			type:'post',
			data:data,
			waitMsg:"正在查询...",
			success:function(message){   
			    var broad_info=message.args.broad_info[0];
			    var cust_info=message.args.cust_info[0];
			    document.getElementById("deviceeNumber").innerHTML = deviceNumber.value;
			    document.getElementById("deviceSatus").innerHTML = "";
			    document.getElementById("deviceProduct").innerHTML = broad_info.product_comments;
			    document.getElementById("deviceAddress").innerHTML = cust_info.devices_address;
			    document.getElementById("deviceName").innerHTML = broad_info.product;
			    document.getElementById("deviceGuishu").innerHTML = "";
			    document.getElementById("business_flag").innerHTML =broad_info.business_flag;
			    document.getElementById("tele_type").innerHTML =broad_info.tele_type;
			},
			error:function(){
				$.error("查询错误，请重新查询！ ");
			}
			
		});
	} else {
		$("#info_address").hide();
		var url = application.fullPath + "/authority/customer/queryCustomerCQMess";
		$.ajax({
			url:url,
			type:'post',
			data:data,
			waitMsg:"正在查询...",
			success:function(message){   
			    var cust_info=message.args.cust_info[0];
			    var deviceNumbers;
			    var deviceGuishus;
			    if (null == cust_info.device_number ){
			    	deviceNumbers= m_deviceNumber;
			    } else {
			    	deviceNumbers = cust_info.device_number;
			    }
			    if (null == cust_info.device_guishu){
			    	 deviceGuishus = "CBSS";
			    } else {
			    	 deviceGuishus = cust_info.device_guishu;
			    }
				var tmpDeviceProducts = getByteLen(cust_info.devices_products) > 26 ? getByteSubstring(cust_info.devices_products, 0, 26) + '...' : cust_info.devices_products;
			    var html = "";
			    var htmll = '<div class="info_user" name="info_detail" id="detail" tabindex="2" style="cursor:hand">';
			    htmll+='<div class="info_user_detail" id="info_user_detail">';
            	htmll+='<div class="left_lable">设备号码：</div>';
            	htmll+='<div class="left_data_quarter" id="deviceeNumber">'+deviceNumbers+'</div>';
            	htmll+='</div>';
            	htmll+='<div class="info_user_detail">';
            	htmll+='<div class="left_lable">用户状态：</div>';
            	if (null==cust_info.devices_status){
            		htmll+='<div class="left_data_quarter" id="deviceSatus"></div>';
            	} else {
            		htmll+='<div class="left_data_quarter" id="deviceSatus">'+cust_info.devices_status+'</div>';
            	}
            	htmll+='</div>';
            	htmll+='<div class="info_user_detail">';
            	htmll+='<div class="left_lable">品<span class="space24"></span>牌：</div>';
            	htmll+='<div class="left_data_quarter" id="deviceName">'+cust_info.devices_brand+'</div>';
            	htmll+='</div>';
            	htmll+='<div class="info_user_detail" id = "info_address">';
            	htmll+='<div class="left_lable">装机地址:</div>';
            	if(null==cust_info.devices_address){
            		htmll+='<div class="left_data_quarter"></div>';
            	} else {
            		htmll+='<div class="left_data_quarter">' + cust_info.devices_address + '</div>';
            	}
            	
            	htmll+='</div>';
            	htmll+='<div class="info_user_detail">';
            	htmll+='<div class="left_lable">产<span class="space24"></span>品：</div>';
            	htmll+='<div class="left_data_quarter" title="' + cust_info.devices_products + '">' + tmpDeviceProducts + '</div>';
            	htmll+='</div>';
            	htmll+='<div class="info_user_detail">';
            	htmll+='<div class="left_lable">归属系统：</div>';
            	htmll+='<div class="left_data_quarter" id="deviceGuishu">'+deviceGuishus+'</div>';
            	htmll+='</div>';
                htmll+='</div>';
                html +=htmll;   	
                $("#btn_left").after(html);
                ready();
                
                $("#detail").siblings().removeClass('info_user_cur');
        		$("#detail").addClass('info_user_cur');
        		//加载余额信息
        		m_deviceNumber = $('#deviceNumber').val();
        		if(m_deviceNumber.indexOf("comcis")>0){
        	   			$("#broandInfo").show();
        	   			$("#phoneInfo").hide();
        	   		 } else {
        	   			$("#phoneInfo").show();
        	   			$("#broandInfo").hide();
        	   			document.getElementById("payMoney").value = "";//缴费金额置为空
        			    document.getElementById('payMoney').readOnly = false ;//宽带续费input恢复
        	   		 }
        		qryBalance();
//			    if (null == cust_info.device_number ){
//			    	document.getElementById("deviceeNumber").innerHTML = m_deviceNumber;
//			    } else {
//			    	document.getElementById("deviceeNumber").innerHTML = cust_info.device_number;
//			    }
//			    document.getElementById("deviceSatus").innerHTML = cust_info.device_status;
//			    document.getElementById("deviceProduct").innerHTML = cust_info.devices_products;
//			    document.getElementById("deviceName").innerHTML = cust_info.devices_brand;
//			    if (null == cust_info.device_guishu){
//			    	 document.getElementById("deviceGuishu").innerHTML = "CBSS";
//			    } else {
//			    	 document.getElementById("deviceGuishu").innerHTML = cust_info.device_guishu;
//			    }
//			   
			    document.getElementById("business_flag").innerHTML =cust_info.business_flag;
			    document.getElementById("tele_type").innerHTML =cust_info.tele_type;
			},
			error:function(){
				$.error("查询错误，请重新查询！ ");
			}
			
		});
	}
	 
}
function queryBusiInfoByIdNumber(){
	//身份证号码查询业务信息
    var operNo = $('#operNo').val();
	var card_type = "ID001";
	var data={
			  'id_number' : m_idNumber,
			  'device_number' : m_deviceNumber,
			  'id_type' : card_type,
			  'oper_no' : operNo
		  }
	 var url = application.fullPath + "/authority/customer/queryBussInfoCQMess";
		$.ajax({
			url:url,
			type:'post',
			data:data,
			waitMsg:"正在查询...",
			success:function(message){   
				$("#detail").hide();
				$("[name='info_detail']").remove();
			    var deviceList=message.args.deviceList;
			    var html = "";
				for(var i=0;i<deviceList.length;i++){			    
			    	device = deviceList[i];
			        index = i+1;
			        var htmll = '<div class="info_user" name="info_detail" id="detail_'+index+'" tabindex="2" style="cursor:hand">';
                    if(device.devices_number.indexOf("comcis")>0){
                    	htmll+='<div class="info_user_detail" id="info_user_detail">';
                    	htmll+='<div class="left_lable">设备号码：</div>';
                    	htmll+='<div class="left_data_quarter" id="deviceeNumber">'+device.devices_number+'</div>';
                    	htmll+='</div>';
                    	htmll+='<div class="info_user_detail">';
                    	htmll+='<div class="left_lable">用户状态：</div>';
                    	htmll+='<div class="left_data_quarter" id="deviceSatus">'+device.devices_status+'</div>';
                    	htmll+='</div>';
                    	htmll+='<div class="info_user_detail">';
                    	htmll+='<div class="left_lable">装机地址:</div>';
                    	htmll+='<div class="left_data_quarter" id="deviceAddress">'+device.devices_address+'</div>';
                    	htmll+='</div>';
                    	htmll+='<div class="info_user_detail">';
                    	htmll+='<div class="left_lable">产<span class="space24"></span>品：</div>';
                    	htmll+='<div class="left_data_quarter" id="deviceProduct">'+device.devices_products+'</div>';
                    	htmll+='</div>';
                    	htmll+='<div class="info_user_detail">';
                    	htmll+='<div class="left_lable">归属系统：</div>';
                    	htmll+='<div class="left_data_quarter" id="deviceGuishu"></div>';
                    	htmll+='</div>';
                    } else {
                    	htmll+='<div class="info_user_detail" id="info_user_detail">';
                    	htmll+='<div class="left_lable">设备号码：</div>';
                    	htmll+='<div class="left_data_quarter" id="deviceeNumber">'+device.devices_number+'</div>';
                    	htmll+='</div>';
                    	htmll+='<div class="info_user_detail">';
                    	htmll+='<div class="left_lable">用户状态：</div>';
                    	htmll+='<div class="left_data_quarter" id="deviceSatus">'+device.devices_status+'</div>';
                    	htmll+='</div>';
                    	htmll+='<div class="info_user_detail">';
                    	htmll+='<div class="left_lable">品<span class="space24"></span>牌：</div>';
                    	htmll+='<div class="left_data_quarter" id="deviceName">'+device.devices_brand+'</div>';
                    	htmll+='</div>';
                    	htmll+='<div class="info_user_detail">';
                    	htmll+='<div class="left_lable">产<span class="space24"></span>品：</div>';
                    	htmll+='<div class="left_data_quarter" id="deviceProduct">'+device.devices_products+'</div>';
                    	htmll+='</div>';
                    	htmll+='<div class="info_user_detail">';
                    	htmll+='<div class="left_lable">归属系统：</div>';
                    	htmll+='<div class="left_data_quarter" id="deviceGuishu"></div>';
                    	htmll+='</div>';
                    	htmll+='<div class="info_user_detail" display="none";>';
                    	htmll+='<div class="left_lable">归属系统：</div>';
                    	htmll+='<div class="left_data_quarter" id="tele_typee">'+device.flag234+'</div>';
                    	htmll+='</div>';
                    } 
                    htmll+='</div>';
                    html +=htmll;
				}
				$("#btn_left").after(html);
				ready();
				 $("#detail_1").siblings().removeClass('info_user_cur');
				 $("#detail_1").addClass('info_user_cur');
				 showBalance();
			},
			error:function(){
				$.error("查询错误，请重新查询！ ");
			}
			
		});
}
function ready(){
////取总个数
	var user_cnts = $(".info_user").length;
	var user_cur = 0;//当前选中
	var user_befor = 0;//前一个
	var user_after = 0;//后一个
	var left_right = "";//是左还是右	
	for(var i=1; i<=user_cnts; ++i) {
		if (i > 2)
			$("#detail_" + i).hide();
		//$("#detail_" + i).removeClass('info_user_cur');
		//$("#detail_" + i).addClass('info_user');
	}
	
	////显示选中
	if (user_cnts >=3) {
		//$("#detail_2").addClass('info_user_cur');
		$("#btn_right").addClass('btn_right_more');
		user_befor = 1;
		user_cur = 2;
		user_after = 2;
		
		left_right = "right";
	}else{
		$('#btn_left').removeClass('btn_left_more').addClass('btn_left');
		$('#btn_right').removeClass('btn_right_more').addClass('btn_right');
	}
	
	//2、用户信息的滚动
	////点击向右
	$("#btn_right").click(function(){
		clearBalanceInfo();
		if (left_right == "left" && user_cur < (user_cnts -1)) {
			//$("#detail_" + user_cur).removeClass('info_user_cur');
			user_cur = user_cur + 1;//前一个
			//$("#detail_" + user_cur).addClass('info_user_cur');
			left_right = "right";
		}
		else {
			if (user_cur < (user_cnts -1)) {
				//$("#btn_right").addClass('btn_right_more');
				//$("#btn_right").removeClass('btn_right');
				$("#btn_right").attr("disabled", false); 
				
				user_befor = user_cur - 1;//前一个
				//不显示最左边的
				$("#detail_" + user_befor).hide();
				
				//原来的不选中
				//$("#detail_" + user_cur).removeClass('info_user_cur');
				
				user_cur = user_cur + 1;//下一个
				left_right = "right";
				
				//选中
				$("#detail_" + user_cur).show();
				//$("#detail_" + user_cur).addClass('info_user_cur');
				
				if (user_cur >=3) {//左按钮可按
					$("#btn_left").addClass('btn_left_more');
					$("#btn_left").removeClass('btn_left');
				}
				
			}
			if (user_cur == (user_cnts -1)) {//最后一个不能变
				$("#btn_right").addClass('btn_right');
				$("#btn_right").removeClass('btn_right_more');
				$("#btn_right").attr("disabled", true); 
			}
		}
	});	
	
	////点击向左
	$("#btn_left").click(function(){
		clearBalanceInfo();
		if (left_right == "right" && user_cur >= 2) {
			//$("#detail_" + user_cur).removeClass('info_user_cur');
			user_cur = user_cur - 1;//前一个
			//$("#detail_" + user_cur).addClass('info_user_cur');
			left_right = "left";
		}
		else if (left_right == "left" && user_cur >= 2) {
			if (user_cur > 1) {
				$("#btn_left").addClass('btn_left_more');
				//$("#btn_left").removeClass('btn_left');
				$("#btn_left").attr("disabled", false); 
			}
			
			//不显示最右边的
			user_after = user_cur + 1;//后一个
			if (user_after >= 3)
				$("#detail_" + user_after).hide();
			
			//$("#detail_" + user_cur).addClass('info_user_cur');
			
			//选中
			user_befor = user_cur - 1;//前一个
			$("#detail_" + user_befor).show();
			
			if (user_cur >= 2)
				left_right = "right";
			else {
				left_right = "left";
				user_cur = 1;//前一个
			}
			
			if (user_cur >=2) {//右按钮可按
				$("#btn_right").addClass('btn_right_more');
				$("#btn_right").removeClass('btn_right');
			}
		}	
			
		if (user_cur == 1 && left_right == "left") {//最后一个不能变
			$("#btn_left").addClass('btn_left');
			$("#btn_left").removeClass('btn_left_more');
			$("#btn_left").attr("disabled", true); 
		}

	});	
	
	//设备号码查询业务信息
	$('[name="detail"]').click(function(){
		$(this).siblings().removeClass('info_user_cur');
		$(this).addClass('info_user_cur');
		//加载余额信息
		m_deviceNumber = $('#deviceNumber').val();
		if(m_deviceNumber.indexOf("comcis")>0){
	   			$("#broandInfo").show();
	   			$("#phoneInfo").hide();
	   		 } else {
	   			$("#phoneInfo").show();
	   			$("#broandInfo").hide();
	   			document.getElementById("payMoney").value = "";//缴费金额置为空
			    document.getElementById('payMoney').readOnly = false ;//宽带续费input恢复
	   		 }
		qryBalance();
	});
	
	//身份证号码查业务信息
	$('[name="info_detail"]').click(function() {
		$(this).siblings().removeClass('info_user_cur');
		$(this).addClass('info_user_cur');
		var info = {};
		$(this).children().each(function(index, ele) {
			switch(index) {
			case 0:
				info.number = $(ele).find('.left_data_quarter').text();
				break;
			case 1:
				info.status = $(ele).find('.left_data_quarter').text();
				break;
			case 2:
				info.brand = $(ele).find('.left_data_quarter').text();
				break;
			case 3:
				info.product = $(ele).find('.left_data_quarter').text();
				break;
			case 4:
				info.system = $(ele).find('.left_data_quarter').text();
				break;
			case 5:
				info.flag = $(ele).find('.left_data_quarter').text();
				break;
			}
		});
		devNoo = info.number;
		tele_typee = info.flag;
		if(devNoo.indexOf("comcis")>0){
   			$("#broandInfo").show();
   			$("#phoneInfo").hide();
   		 } else {
   			$("#phoneInfo").show();
   			$("#broandInfo").hide();
   			document.getElementById("payMoney").value = "";//缴费金额置为空
		    document.getElementById('payMoney').readOnly = false ;//宽带续费input恢复
   		 }
		 if(devNoo.indexOf("comcis")>0){
			 document.getElementById("business_flag").innerHTML ="broadBand";
		 } else {
			 document.getElementById("business_flag").innerHTML ="23G";
		 }
		qryBalance();
	});
}
function showBalance(){
	var info = {};
	$("#detail_1").children().each(function(index, ele) {
		switch(index) {
		case 0:
			info.number = $(ele).find('.left_data_quarter').text();
			break;
		case 1:
			info.status = $(ele).find('.left_data_quarter').text();
			break;
		case 2:
			info.brand = $(ele).find('.left_data_quarter').text();
			break;
		case 3:
			info.product = $(ele).find('.left_data_quarter').text();
			break;
		case 4:
			info.system = $(ele).find('.left_data_quarter').text();
			break;
		case 5:
			info.flag = $(ele).find('.left_data_quarter').text();
			break;
		}
	});
	devNoo = info.number;
	tele_typee = info.flag;
	if(devNoo.indexOf("comcis")>0){
			$("#broandInfo").show();
			$("#phoneInfo").hide();
		 } else {
			$("#phoneInfo").show();
			$("#broandInfo").hide();
			document.getElementById("payMoney").value = "";//缴费金额置为空
	    document.getElementById('payMoney').readOnly = false ;//宽带续费input恢复
		 }
	 if(devNoo.indexOf("comcis")>0){
		 document.getElementById("business_flag").innerHTML ="broadBand";
	 } else {
		 document.getElementById("business_flag").innerHTML ="23G";
	 }
	qryBalance();
}
function qryBalance(){
	var devNo = null;
	if (0 == m_idNumber){
		devNo = m_deviceNumber;
	} else {
		devNo = devNoo;
	}
	var card_type = "ID001";
	if(devNo.indexOf("comcis")>0){
		var data={
				  'device_number' : devNo
			  }
		 var url = application.fullPath + "/authority/customer/queryBroadbandInfo";
			$.ajax({
				url:url,
				type:'post',
				data:data,
				waitMsg:"正在查询...",
				success:function(message){
					var broand_info = message.args.broand_info[0];
					if(null == broand_info){
						$.error(message.content);
						document.getElementById("payMoney").value = "";//缴费金额置为空
					    document.getElementById('payMoney').readOnly = true ;//宽带续费input恢复
					}
					if (null == broand_info.total_fee){
						 document.getElementById("total_fee").innerHTML = "0.00";//当前可用余额
					} else {
						 document.getElementById("total_fee").innerHTML = broand_info.total_fee;//当前可用余额
					}
				    document.getElementById("pre_charge").innerHTML = broand_info.pre_charge;//当前实时话费
				    document.getElementById("dis_charge").innerHTML = broand_info.dis_charge;//普通预存款
				    document.getElementById("discount_rate").innerHTML = broand_info.discount_rate;//当前可用分摊款
				    if (null == broand_info.extra_charge){
				    	 document.getElementById("extra_charge").innerHTML = "0.00";//当月赠款
				    } else {
				    	 document.getElementById("extra_charge").innerHTML = broand_info.extra_charge;//当月赠款
				    }
				    if(null == broand_info.owe_charge){
				    	document.getElementById("owe_charge").innerHTML = "0.00";//历史欠费
				    } else {
				    	document.getElementById("owe_charge").innerHTML = broand_info.owe_charge;//历史欠费
				    }
				    document.getElementById("liquidated_charge").innerHTML = "0.00";//违约金
				    document.getElementById("available_credit").innerHTML = "0.00";//信息用额度
				    document.getElementById("payMoney").value = broand_info.pre_charge;//缴费金额
				    document.getElementById('payMoney').readOnly = true ;//宽带续费input置为不可编辑
				},
				error:function(){
					$.error("查询错误，请重新查询！ ");
				}
				
			});
	} else {
		var data={
				  'deviceNum' : devNo,
				  'oper_no' : "CF0540"
			  }
		 var url = application.fullPath + "/authority/customer/queryBalanceCQ";
			$.ajax({
				url:url,
				type:'post',
				data:data,
				waitMsg:"正在查询...",
				success:function(message){
					var balance_info = message.args.balance_info[0];
				    document.getElementById("sumBalance").innerHTML = balance_info.current_balance;//当前可用余额
				    document.getElementById("sumCharge").innerHTML = balance_info.rt_calls;//当前实时话费
				    document.getElementById("prepayBalance").innerHTML = "0.00";//普通预存款
				    document.getElementById("apportionCharge").innerHTML = "0.00";//当前可用分摊款
				    document.getElementById("grantsCharge").innerHTML = balance_info.avail_grant;//当月赠款
				    if (0==balance_info.owe_charge){
				    	document.getElementById("oweCharge").innerHTML = "0.00";//历史欠费
				    } else {
				    	document.getElementById("oweCharge").innerHTML = balance_info.owe_charge;//历史欠费	
				    }
				    document.getElementById("liquidatedCharge").innerHTML = "0.00";//违约金
				    document.getElementById("availableCredit").innerHTML = "0.00";//信息用额度
				},
				error:function(){
					$.error("查询错误，请重新查询！ ");
				}
				
			});
	}
}
function next(){
	var payMoney = $.trim($('#payMoney').val()); 
	if (0 == payMoney){
		$.error("请输入缴费金额！ ");
	} else {
		if (5<payMoney&&5000>payMoney){
			if(window.confirm('本次缴费共计'+payMoney+'元!请您确认?')){
				callPayCharge();
				return true;
		     }else{
		        return false;
		    }
		} else {
			$.error("充值金额大于 5 小于5000,请重新输入缴费金额！");
			document.getElementById('payMoney').value = "";
		}
		
	}
}
function callPayCharge() { 
	var tele_type = null;
	m_tele_type = document.getElementById("tele_type").innerHTML;
	if (0 == m_idNumber){
		tele_type = m_tele_type;
	} else {
		tele_type = tele_typee;
	}
	 m_business_flag = document.getElementById("business_flag").innerHTML
     devNo = document.getElementById("deviceeNumber").innerHTML;
    var operNo = $('#operNo').val();
	var data={
			  'oper_no' : operNo,
			  'phone_no' : devNo,
			  'business_flag': m_business_flag,
			  'tele_type': tele_type,
			  'real_fee' : $.trim($('#payMoney').val()),
			  'pre_charge' : $.trim($('#pre_charge').val()),
			  'dis_charge' : $.trim($('#dis_charge').val()),
			  'extra_charge' : $.trim($('#extra_charge').val()),
			  'current_balance' : $.trim($('#owe_charge').val())
		  }
	 var url = application.fullPath + "/authority/customer/paymentInfoCQ";
		$.ajax({
			url:url,
			type:'post',
			data:data,
			waitMsg:"缴费中...",
			success:function(message){   
				var pement_info = message.args.pement_info;
				if("00000"==pement_info.resp_code){	
					$.error("恭喜您，缴费成功！ ");
					setTimeout('qryBalance2()',1000); 
					document.getElementById('payMoney').value = "";
				} else {
					 if(null!=pement_info.resp_desc){
						 $.error("抱歉，缴费失败！ "+pement_info.resp_desc);
					 } else {
						 $.error("抱歉，缴费失败！ ");
					 }
					document.getElementById('payMoney').value = "";
				}
			
			},
			error:function(){
				;
			}
			
		});
}

function qryBalance2(){
	//var idNumber = $.trim($('#idNumber').val());  
	//devNo = document.getElementById("deviceeNumber").innerHTML;
	var devNo = null;
	var devNoo = null;
	if (0 == idNumber.value){
		devNo = deviceNumber.value;
	} else {
		devNo = devNoo;
	}
	var card_type = "ID001";
	var data={
			  'deviceNum' : devNo,
			  'oper_no' : operNo
		  }
	 var url = application.fullPath + "/authority/customer/queryBalanceCQ";
		$.ajax({
			url:url,
			type:'post',
			data:data,
			success:function(message){
				var balance_info = message.args.balance_info[0];
			    document.getElementById("sumBalance").innerHTML = balance_info.current_balance;//当前可用余额
			    document.getElementById("sumCharge").innerHTML = balance_info.rt_calls;//当前实时话费
			    document.getElementById("prepayBalance").innerHTML = balance_info.current_balance;//普通预存款
			    document.getElementById("apportionCharge").innerHTML = "0.00";//当前可用分摊款
			    document.getElementById("grantsCharge").innerHTML = balance_info.avail_grant;//当月赠款
			    if (0==balance_info.owe_charge){
			    	document.getElementById("oweCharge").innerHTML = "0.00";//历史欠费
			    } else {
			    	document.getElementById("oweCharge").innerHTML = balance_info.owe_charge;//历史欠费	
			    }
			    document.getElementById("liquidatedCharge").innerHTML = "0.00";//违约金
			    document.getElementById("availableCredit").innerHTML = "0.00";//信息用额度
			},
			error:function(){
				$.error("查询错误，请重新查询！ ");
			}
			
		});
}
//判断字符串长度（汉字算两个字符，字母数字算一个字符）
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

function clearInput() {
	ready();
	document.getElementById("idNumber").value = "";
	document.getElementById("deviceNumber").value = "";
	document.getElementById('payMoney').value = "";
	$("[name='info_detail']").remove();
	$("#detail").show();
	clearUserInfo();
	clearBusiInfo();
	clearBalanceInfo();
}
function clearBusiInfo(){
	document.getElementById("deviceeNumber").innerHTML = "";
    document.getElementById("deviceSatus").innerHTML = "";
    document.getElementById("deviceProduct").innerHTML = "";
    document.getElementById("deviceName").innerHTML = "";
    document.getElementById("deviceGuishu").innerHTML = "";
    document.getElementById("deviceAddress").innerHTML = "";
}
function clearUserInfo(){
	document.getElementById("custName").innerHTML = "";
    document.getElementById("idType").innerHTML = "";
    document.getElementById("iddNumber").innerHTML = "";
    document.getElementById("cardType").innerHTML = "";
}
function clearBalanceInfo() {
	document.getElementById("sumBalance").innerHTML = "";
    document.getElementById("sumCharge").innerHTML = "";
    document.getElementById("prepayBalance").innerHTML = "";
    document.getElementById("apportionCharge").innerHTML = "";
    document.getElementById("grantsCharge").innerHTML = "";
    document.getElementById("oweCharge").innerHTML = "";
    document.getElementById("liquidatedCharge").innerHTML = "";
    document.getElementById("availableCredit").innerHTML = "";
    document.getElementById("payMoney").value = "";//缴费金额置为空
    document.getElementById('payMoney').readOnly = true ;//宽带续费input恢复

	document.getElementById("total_fee").innerHTML = ""//当前可用余额
	document.getElementById("pre_charge").innerHTML = "";//当前实时话费
    document.getElementById("dis_charge").innerHTML = "";//普通预存款
    document.getElementById("discount_rate").innerHTML = "";//当前可用分摊款
    document.getElementById("extra_charge").innerHTML = "";//当月赠款
    document.getElementById("owe_charge").innerHTML = "";//历史欠费
    document.getElementById("liquidated_charge").innerHTML = "";//违约金
    document.getElementById("available_credit").innerHTML = "";//信息用额度
}