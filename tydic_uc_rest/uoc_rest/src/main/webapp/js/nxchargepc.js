var num_flag = false;
var cust_belong = "";
var order_id = "";
var channel_run_type = "";
var customer_name = "";

$(document).ready(function() {
	
	operPayType();
    $("#btn_submit").unbind().bind("click", confirmCharge);
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

function goCharge(charge_phone,charge_value,pay_type) {
	var URL = application.fullPath + "authority/nx/nxcharge";
	var data1={
			charge_phone:charge_phone,
			charge_value:charge_value,
			order_id:order_id,
			pay_type:pay_type
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
				$.alert("恭喜，缴费成功！\n"+message.content);
				
				$("#charge_phone").val("");
				$("#charge_value").val("");
				
				//打印电子发票
				makeElectronInvoice(charge_phone, charge_value);
				
				
				num_flag = false;
				channel_run_type = "";
				//插入属性表 加入customer_name
				var attrdata = {
						"customer_name":$("#custname").val()
				};
				$.post(application.fullPath + "rest/orderInfo/orderInfoAttrUpdate",{
					jsessionid: $("#jsessionid").val(),
					"order_id":order_id,	
					"page_code":"0",	
					"order_json":JSON.stringify(attrdata)
				},function(attrArgs){
					console.info("属性表更新成功");
				},"json");
				
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
//查询号码姓名和余额
function unmCheck(){
	var phone = $("#charge_phone").val();
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
		  var balance = "";
		  var custname = "";
		  $("#custname").html("请稍候......");
		  $("#balance").html("");	
			$.post(application.fullPath + 'rest/allTake/realNamePhoneNumCheck', {
				     jsessionid : $("#jsessionid").val(),
				     device_number : phone
			},  function(data){
				rsp_code = data.args.rsp_code;
				rsp_desc = data.args.rsp_desc;
				if("00000" != rsp_code)
				{
					$("#custname").html(rsp_desc);
					return ;
				}
				customer_name = data.args.customer_name;
				cust_belong = data.args.cust_belong;
				channel_run_type = data.args.channel_run_type;

				$.post(application.fullPath + 'rest/allTake/queryNumberBalance', {
					     jsessionid : $("#jsessionid").val(),
					     device_number : phone
				}, function(data){
					rsp_code = data.args.rsp_code;
					rsp_desc = data.args.rsp_desc;
					if(customer_name!=null&&customer_name!=''){
						custname = customer_name;
						if("??" == custname.substring(custname.length-2,custname.length))
						{
							custname = custname.substring(0,custname.length-2) + "*";
						}
						else
						{
							custname = custname.substring(0,custname.length-1) + "*";
						}
					}
					if("00000" != rsp_code)
					{
						$("#balance").html(rsp_desc);
						$("#custname").html(custname);
						return ;
					}
					
					PhoneBalance = data.args.PhoneBalance;
					if(PhoneBalance!=""){
						balance = PhoneBalance/100 + "元";
					}
					else{
						balance = "实时话费查询失败";
					}
					
					$("#balance").html(balance);	
					$("#custname").html(custname);	
					num_flag = true;
					$("#charge_value").focus();
					//查询成功后生成订单id
					createOrderId();					
				},"json");
			},"json");
	}
	
}

/*支付宝扫码支付*/
function alipayScan(order_id,total_amount,subject,tele_type,pay_type,oper_id,device_number){
	
	//预下单请求到后台，后台处理后向支付宝发起请求预下单，支付宝返回二维码到后台系统，后台组装数据返回前端生成二维码
	$.ajax({
		url:application.fullPath + "authority/alipay/alipayScan",
		data:{"order_id":order_id,"total_amount":total_amount,
			"subject":subject,"tele_type":tele_type,
			"pay_type":pay_type,
			"oper_id":oper_id,
			"device_number":device_number
				},
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		dataType:'json',
		type:'post',
		waitMsg:"二维码生成中，请稍候...",
		success:function(message){
			if(message.type == "error"){
				$.alert(message.content);
			}else{
				//将二维码输出到页面
				var htmlStr = message.content;
				$("#pic-idPic").attr( "src", application.fullPath+"authority/alipay/ie6twocode?date="+(new Date()).getTime() );
				
				showdiv("dialog_charge_pop");
				$(document).on('click', '#qr_btn_cancel', function() {
					hidediv("dialog_charge_pop");
				});
				
				//轮询支付状态，支付成功则停止
				searchStatusInterval(order_id,pay_type);
			}
		},
		error:function(){
			alert("ajax error");
		}
	});
}
/*
 * 轮询订单状态
 * 
 */
function searchStatusInterval( order_id,pay_type ){
//	console.log("窗体是否是显示状态--"+$(".xxDialog").css('display') == "block");
	//二维码对话框没有关闭的情况下进行轮询
	if($("#dialog_charge_pop").css('display') == "block"){
		$.ajax({
			url:application.fullPath + "authority/alipay/searchStatus",
			data:{"order_id":order_id},
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			dataType:'json',
			timeout:22000,
			type:'post',
			success:function(message){
				if(message.type == "success"){
					hidediv("dialog_charge_pop" );
					var charge_phone = $("#charge_phone").val();
					var charge_value = $("#charge_value").val();
					goCharge(charge_phone,charge_value,pay_type);
				}else{
					searchStatusInterval(order_id,pay_type); // 递归调用
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown) {
	            if (textStatus == "timeout") { // 请求超时
	            	//console.log("重复请求！");
	            	searchStatusInterval(order_id,pay_type); // 递归调用
	                // 其他错误，如网络错误等
	                } else { 
	                	searchStatusInterval(order_id,pay_type);
	                }
	            }
		});
	}
}
//支付宝退款
function alipayRefund(order_id){
	$.post(application.fullPath + "rest/alipay/alipayTradeRefund",{order_id:order_id,jsessionid:$("#jsessionid").val()},function(data){
		if(data!=null){
			$.alert(data.content);
		}
		else{
			$.alert("无支付宝支付记录");
		}
	},"json");
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

function payTypeConfirm(){
	var payTypeRadio = $('#pay_type_select_list .radio_checked');
	
	if(payTypeRadio == undefined){
		$.alert("请选择支付方式!");
		return;
	}
	
//	//营业厅支持在线支付，代理商暂不支持
	if("01" != channel_run_type && "10" != payTypeRadio.attr("value"))
	{
		$.alert("代理商暂不支持在线支付方式！");
		return;
	}

	var charge_phone = $("#charge_phone").val();
	var charge_value = $("#charge_value").val();
	
	//现金
	if(payTypeRadio.attr("value") == "10"){
		goCharge(charge_phone,charge_value,"10");
	}
	
	//支付宝
	else if(payTypeRadio.attr("value") == "19"){
		alipayScan(order_id,
				charge_value,"缴费",'ALL',
    			'19',$("#oper_no").val(),charge_phone);
	}
	//微信（迪科）
	else if(payTypeRadio.attr("value") == "20"){
		wxpay(order_id,
				charge_value,"缴费",'ALL',
    			'20',$("#oper_no").val(),charge_phone);
	}
	//微信（联通）
	else if(payTypeRadio.attr("value") == "20_nx"){
		wxpay(order_id,charge_value,"缴费",'ALL',
    			'20',$("#oper_no").val(),charge_phone,"nx");
	}
	
}

function createOrderId(){	 
	 var data1={
			 tele_type:"ALL"
		  }
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

function wxpay(order_id,total_amount,subject,tele_type,pay_type,oper_id,device_number, mcht_flag){
	var jsonData = {
    		jsessionid:$("#jsessionid").val(),           	
			order_id : order_id,
			tele_type:tele_type,
			body:subject,
			totalFee:total_amount, //费用的单位为元
			deviceInfo:device_number,
			mcht_flag:mcht_flag == undefined ? "" : mcht_flag
	};
	
	//预下单请求到后台，后台处理后向支付宝发起请求预下单，支付宝返回二维码到后台系统，后台组装数据返回前端生成二维码
	$.ajax({
		url:application.fullPath + "rest/wxpay/wxQrCode",
		data:jsonData,
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		dataType:'json',
		type:'post',
		waitMsg:"二维码生成中，请稍候...",
		success:function(message){
			if(message.type == "error"){
				$.alert(message.content);
			}else{
				//将二维码输出到页面
				$("#pic-idPic").attr('src',("data:image/jpeg;base64,"+message.content)); //二维码图片
				
				showdiv("dialog_charge_pop");
				$(document).on('click', '#qr_btn_cancel', function() {
					hidediv("dialog_charge_pop");
				});
				
				//轮询支付状态，支付成功则停止
				wxsearchStatusInterval(order_id,pay_type);
			}
		},
		error:function(){
			alert("ajax error");
		}
	});
}

function wxsearchStatusInterval(order_id,pay_type){
	if($("#dialog_charge_pop").css('display') == "block"){
		$.ajax({
			url:application.fullPath + "rest/payCommon/searchStatus",
			data:{jsessionid:$("#jsessionid").val(),"order_id":order_id},
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			dataType:'json',
			timeout:22000,
			type:'post',
			success:function(message){
				if(message.type == "success"){
					hidediv("dialog_charge_pop" );
					var charge_phone = $("#charge_phone").val();
					var charge_value = $("#charge_value").val();
					goCharge(charge_phone,charge_value,pay_type);
				}else{
					wxsearchStatusInterval(order_id,pay_type); // 递归调用
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown) {
	            if (textStatus == "timeout") { // 请求超时
	            	//console.log("重复请求！");
	            	wxsearchStatusInterval(order_id,pay_type); // 递归调用
	                // 其他错误，如网络错误等
	                } else { 
	                	wxsearchStatusInterval(order_id,pay_type);
	                }
	            }
		});
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
	        	if("" == channel_run_type && !num_flag)
	        	{
	        		$.alert("请先查询号码！");
	        		return;
	        	}
	        	//营业厅支持在线支付，代理商暂不支持
				if("01" != channel_run_type && "10" != selectedIndex.attr("value"))
				{
					$.alert("代理商暂不支持在线支付方式！");
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

//生成电子发票
function makeElectronInvoice(charge_phone, charge_value){
	
	//4G用户没有打发票的功能
	if(cust_belong == "CBSS"){
		$.alert("4G用户暂不支持发票打印");
		return;
	}
	
	$.ajax({
		url:application.fullPath + "rest/allTake/makeElectronInvoice",
		data:{
		     "jsessionid": $("#jsessionid").val(),
			 "order_id":order_id,
			 "charge_phone":charge_phone,
			 "charge_value":charge_value,
			 "customer_name":customer_name
				
		},
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		dataType:'json',
		timeout:22000,
		type:'post',
		waitMsg:"电子发票生成中，请稍候...",
		async:false,
		success:function(message){
			if(message.type == "success"){
				$.alert("已自动生成电子发票！\n");
			}
			else{
				$.alert("电子发票生成失败!"+message.content);
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			$.alert("电子发票生成失败!");
		}
	});
	
}

// 按角色显示支付方式
function operPayType() {
	$.post(application.fullPath + "rest/oper/queryOperPay", {
		tele_type : "ALL",
		jsessionid : $("#jsessionid").val()
	}, function(data) {
		if (data != null) {
			var plist = data.args.code_list_vos;
			var result = '';
			if (plist.length > 0) {
				for (var i = 0; i < plist.length; i++) {
					var start = '';
					var end = '';
					if(i%2 == 0){
						if(i == 0){
							start = '<li class="list left" style=" width:454px; height:36px;" id="pay_type_select_list"><div class="left_lable">'+
							'<div class="left_lable text_large bold">支<span class="space4"></span>付<span class="space4"></span>方<span class="space4"></span>式：</div>';
						}else{
							start = '<li class="list left" style=" width:454px; height:36px;" id="pay_type_select_list"><div class="left_lable">'+
							'<div class="left_lable text_large bold"><span class="space36"></span><span class="space36"></span><span class="space18"></span><span class="space2"></span></div>';
						}
					}
					if(i%2 == 1 || (i+1) == plist.length ){
						end = '</div></li>';
					}
					if(i == 0){
						result += start +
						  '<div name="pay_type_select" class="radio_checked" value='+plist[i].pay_type+'></div>'+
						  '<div class="left_data text_large">'+plist[i].pay_type_name+
						  '</div>'+
						  end;
					}else{
						result += start +
						 ' <span class="space4"></span>'+
						  '<div name="pay_type_select" class="radio" value='+plist[i].pay_type+'></div>'+
						  '<div class="left_data text_large">'+plist[i].pay_type_name+
						  '</div>'+
						  end;
					}
				}
			}else{
				result += '<li class="list left" style=" width:454px; height:36px;" id="pay_type_select_list">'+
						  '<div class="left_lable">'+
						  '<div class="left_lable text_large bold">支<span class="space4"></span>付<span class="space4"></span>方<span class="space4"></span>式：</div>'+
						  '<div class="left_data text_large">'+'无可用支付方式，请联系管理员！'+
						  '</div>'+
						  '</li>';
			}
			
			$("#pay_way").append(result);
		}
	}, "json");
}