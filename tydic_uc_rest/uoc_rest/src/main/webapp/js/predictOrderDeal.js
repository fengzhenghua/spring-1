//新的订单
var order_id = null;
var wcf_order_id = null;
//本地库沃创富ID
var local_wcf_order_id = null;
var order_sub_type = "10010";
//沃创富预订单详情
var data = null;
var fee_data = null;
var terminal_id = null;
var terminal_model = null;
var terminal_flag = null;
var oper_no = null;
//重新刷的证件
var id_number = null;
var returnFlag = true;
var load_rwcard_acx = false;
var writeCardFlag =0;
var cust_seq_id=null;
var posCardFlag=0;
var fee_info="";//费用信息
var is_shoufei = false;//是否收费标识
$(document).ready(function() {
		
	$("#card_con").hide();
	$("#readCard").bind("click",function (){
		readCardBtn();					
	});	
	$("#btn_ckeckback").bind("click",function (){//身份证校验返回事件
		$("#card_con").hide();
		$("#number").hide();
		$("#order_con").show();				
	});	
	$("#btn_getNumberback").bind("click",function (){//选号返回事件
		$("#card_con").hide();
		$("#number").hide();
		$("#order_con").show();				
	});	
	$("#show2G").hide();
	$("#show3G").hide();
	$("#mob_section").bind("change",function (){
		haoduanSelect();	
	});
	$("#query").click(function() {
		selectPage();
	});
	//生成订单
	createOrderId();	
	
});

function createOrderId(){	 
	 var data1={
			 "tele_type":"ALL",
			 "order_sub_type":order_sub_type
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
					getWcfOrder();
				}
			}
			
		});		  
};

//沃创富调接口获取订单
function getWcfOrder() {
	wcf_order_id = $("#wcf_order_id").val();
	if (wcf_order_id == "") {	
		$.alert("提货单为空");
		return;
	}	
		
	var URL = application.fullPath + "authority/restToController/getWcfOrderDeil";
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		data:{
			 "wcf_order_id" : wcf_order_id,
			 "order_id":order_id			
			},
		waitMsg:"加载订单信息..",
		success:function(message){
			if(message.type == "error"){
				var dialog=$.dialog({
					   title:'提示',//提示title
					   content:'订单查询失败:'+message.content,//显示的内容，支持html格式。
					   buttons:[{id:'btn_ok',text:'重新查询订单',
							   onClick:function(){									   
								   dialog.close();
								   getWcfOrder();
							   }								   
							}]
					});
			}else{
				data = message.args.order_list[0];
				fee_data = message.args.fee_info;				
				setOrderInfo(data,fee_data);				
			}
		}
		
	});	
	
};

function setOrderInfo(data,fee_data){
	/*if(data.status == "4"){
		$.alert("此订单已处理~");
		return;
	}	*/
	loahaoduan(data.tele_type);//根据电信类型显示号段
	document.getElementById("Order_number").innerHTML=data.order_number;
	document.getElementById("acc_nbr").innerHTML=data.device_number;
	document.getElementById("id_nbr").innerHTML=data.id_number;
	document.getElementById("first_month_fee_desc").innerHTML=data.first_month_fee_desc;
	document.getElementById("ofr_name").innerHTML=data.ofr_name;
	document.getElementById("custom_name_pc").innerHTML=data.custom_name;	
	local_wcf_order_id = data.wcf_order_id;

	var amount = 0;
    var tmp = 0;
    $("#feed_list").html('');
	var htmlNew = '';
    for(var i=0;i<fee_data.length;i++){
    	var html="";   	
	    if(i%2==0){
	    	html+='<div class="left"><div class="left_lable" >'+fee_data[i].feeName+'：</div><div class="left_data" ><span >'+fee_data[i].recAmount+'</span><span>.00元</span></div></div>';
	    }else{
	    	html+='<div class="right"><div class="left_lable">'+fee_data[i].feeName+'：</div> <div class="left_data" ><span >'+fee_data[i].recAmount+'</span><span>.00元</span></div></div> ';  
	    }
	    tmp = parseInt(fee_data[i].recAmount);
		amount += tmp;	
		htmlNew +=html;
    }
    $("#feed_list").append(htmlNew);    
	document.getElementById("fee_total").innerHTML=amount;
	if(data.pay_type == "02"){//沃创富已收费
	  document.getElementById("fee_flag").innerHTML="（已收费）";
	  if(data.device_number != null&&data.device_number!=''){//有手机号码
		  $("#haoma_btb").removeAttr("onclick");
		  $("#haoma_btb").removeClass("input_button");
		  $("#haoma_btb").addClass("input_button_disabled");	
	  }
	  
	}
    /*
	oper_no = data.oper_no;
	if(oper_no == ""){
		open_no = "DefaultOperNo";
	}
	loginOtherOper();*/
	
	if(data.terminal_model != ""){
		$("#terminal_code").val(data.terminal_id);
		terminal_flag = false;
		terminal_id = data.terminal_id;
		terminal_model = data.terminal_model;
		//document.getElementById("btn_submit").className ="ok";
	}else{
		//document.getElementById("btn_submit").className ="ok";		
		$("#terminal_btb").removeAttr("onclick");
		$("#terminal_btb").removeClass("input_button");
		$("#terminal_btb").addClass("input_button_disabled");	
	}		
	if(data.status == "0"){//已经写卡成功
		$("#terminal_btb").removeAttr("onclick");
		$("#terminal_btb").removeClass("input_button");
		$("#terminal_btb").addClass("input_button_disabled");
		
		$("#writeCard").removeAttr("onclick");
		$("#writeCard").removeClass("input_button");
		$("#writeCard").addClass("input_button_disabled");
		$("#readCard").removeAttr("onclick");
		$("#readCard").removeClass("input_button");
	    $("#readCard").addClass("input_button_disabled");
		
	}else{
		$("#writeCard").removeAttr("onclick");
		$("#writeCard").bind("click",function (){
			if(data.pay_type == "02"){//沃创富已收费
				submitWcfOrder();
			}else{
				writeCardBtn();
			}
			
		});
	}
		
	saveDataToAttr();//保存数据到订单属性表			
};


//显示身份证校验
function checkeCarId(){	
	$("#card_con").show();
	$("#order_con").hide();
	$("#number").hide();
};
//显示选号
function showNumber(){	
	var idnumber=$('#id_nbr').text();
	 if(idnumber==null||idnumber==""){		
			$.alert("请选校验身份证!");
			return;
	}else if(data.terminal_model != "" && !terminal_flag){
			hidediv('loading');
			$.alert("请校验终端~");		
			return;
	}
	$("#card_con").hide();
	$("#order_con").hide();
	$("#number").show();
	$("#number_title1").hide();	
	$("#number_title2").hide();	
};

//校验终端
function getTerminalInfo(){	
	var idnumber=$('#id_nbr').text();
	 if(idnumber==null||idnumber==""){		
			$.alert("请选校验身份证!");
			return;
		}
	terminal_id=$("#terminal_code").val();//终端串号
	if(terminal_model == ""){
		$.alert("无终端信息~");
		return;
	}
	
	var URL = application.fullPath + "authority/restToController/getTerminalInfoByTerminalId";
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		data:{
			"order_id":order_id,
			"terminal_id":terminal_id
			},
		waitMsg:"终端串号校验中..",
		success:function(message){						
			if(message.type == "error"){
				$.alert(message.content);
			}else{
				document.getElementById("pinpai").innerHTML=message.args.terminal_info.terminal_brand_desc;
				document.getElementById("jixing").innerHTML=message.args.terminal_info.terminal_model_code;
				$("#terminal_btb").removeAttr("onclick");
				$("#terminal_btb").removeClass("input_button");
				$("#terminal_btb").addClass("input_button_disabled");
				terminal_flag=true;
				
				var dialog=$.dialog({
					   title:'提示',//提示title
					   content:'校验成功',//显示的内容，支持html格式。
					   buttons:[{id:'btn_ok',text:'确定',
							   onClick:function(){									   
								   dialog.close();								  										
							    if(data.device_number != null&&data.device_number!=''){//有手机号码
							    	 if(data.pay_type != "02"){//沃创富未收费
							    		 queryNumberAndYz();//查询号码信息中
							    	 }
								}	

							   }
								   
							}]
					});
			
			}
		}
		
	});
};

//更新属性表
function OrderUpdatedate(data,page){	
	 var data_json=JSON.stringify(data);
	 var data1={	
				"order_id":order_id,
				"page_code":page,	
				"order_json":data_json
			};
	 var URL = application.fullPath + "authority/accountOpen/orderInfoAttrUpdate";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			//waitMsg:order_all_json[i].hint,
			success:function(message){					 
				if(message.type == "error"){
					hidediv('loading');
					$.alert(message.content);
					return returnFlag = false;
				}else{
					if(page=='5'){//保存费用信息
					  dealFee();
					}
				}		
			}			
		});		
}


//提交订单
function submitWcfOrder(){
	document.getElementById("in_text").innerHTML="预提交数据请稍等...";
	showdiv('loading');
	
	if(!returnFlag){
		hidediv('loading');
		$.alert("更新订单失败~");
		return;
	}
	id_number = $("#id_nbr").text();
	if(id_number == ""){
		hidediv('loading');
		$.alert("请读取证件~");
		return;
	}	
	if(data.terminal_model != "" && !terminal_flag){
		hidediv('loading');
		$.alert("请校验终端~");		
		return;
	}
	var URL = application.fullPath + "authority/restToController/submitWcfOrder";
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		async: true,//使用同步的方式,true为异步方式
		data:{
			"order_id":order_id,
			"wcf_order_id":local_wcf_order_id,
			"id_number":id_number
			},
		//waitMsg:"订单提交中..",
		success:function(message){
			if(message.type == "success"){
				$("#writeCard").removeAttr("onclick");
				 $("#writeCard").bind("click",function (){//改变写卡绑定按钮事件
						writeCardBtn();
						//submitWcfOrder();
					});
				  writeCardBtn();//写卡
				 
			}else{
				hidediv('loading');
				var dialog=$.dialog({
					   title:'提示',//提示title
					   content:'订单提交失败:'+message.content,//显示的内容，支持html格式。
					   buttons:[{id:'btn_ok',text:'重新提交订单',
							   onClick:function(){									   
								   dialog.close();									
								   submitWcfOrder();
							   }								   
							}]
					});
				
				
			}
		}
		
	});
};


//读卡
function readCardBtn(){
	id_number = $("#id_nbr").text();
	if(id_number == ""){
		hidediv('loading');
		$.alert("请读取证件~");
		return;
	}	
	if(data.terminal_model != "" && !terminal_flag){
		hidediv('loading');
		$.alert("请校验终端~");		
		return;
	}
	if(!load_rwcard_acx){
		    document.body.insertAdjacentHTML("beforeEnd", " \
		          <object id=\"CardReader\" style=\"display:none;\" classid=\"clsid:43E4D4FC-3CD8-459A-AAA1-698C1288DE93\" width=\"0\" height=\"0\"> \
		        </object>");

		        load_rwcard_acx = true;
		  }	
	var writeWay=$("#writeWay").val();
	if('0'==writeWay){
	  $.alert("模拟白卡开户，请手动输入卡号，进行写卡");
	}else{
	var sim_no=getCardId();
	  $("#resourcesCode").val(sim_no);
	  $("#readCard").removeAttr("onclick");
	  $("#readCard").removeClass("input_button");
	  $("#readCard").addClass("input_button_disabled");
	}
			
	
}

//写卡按钮
function writeCardBtn(){
	id_number = $("#id_nbr").text();
	if(id_number == ""){
		hidediv('loading');
		$.alert("请读取证件~");
		return;
	}	
	if(data.terminal_model != "" && !terminal_flag){
		hidediv('loading');
		$.alert("请校验终端~");		
		return;
	}
	document.getElementById("in_text").innerHTML="正在读卡...";
	showdiv('loading');
	writeCardFlag ++;
	if(writeCardFlag>1){
		$.alert("请不要重复点击写卡按钮");
		  hidediv('loading');
		 return;
	}	
	var iccid=$("#resourcesCode").val();
	var writeWay=$("#writeWay").val();
	if(''==iccid||'请读卡'==iccid){
	   if('0'==writeWay){
		   $.alert("模拟白卡开户，请手动输入卡号，在进行写卡");
		   writeCardFlag=0;
		   hidediv('loading');
	     return;
		}else{
			$.alert("请先读卡在进行写卡");
			writeCardFlag=0;
			  hidediv('loading');
		 return;
	   }
	}else{
		if('0'==writeWay){
		}else{
		 var sim_no=getCardId();
		 if(sim_no==iccid){	 
		 }else{
		$.alert("此白卡与已经获取的卡号不一样，请插入之前的白卡再操作");
		  hidediv('loading');
		writeCardFlag=0;
		 return;
		 }
		}
		
	}
	
	var wt_flag="0";
	var numId=data.device_number;//手机号码
	var prepayFlag=data.prepay_flag;//付费方式
	var cardType="";//白卡业务类型
	var teleType = data.tele_type;//电信类型
	if(teleType=='2G'){
		if(prepayFlag=='1'){//后付费
			prepayFlag='01'
		}else if(prepayFlag=='2'){//预付费
			prepayFlag='00'
		}else{
			prepayFlag='00'
		}
		cardType="08";
	}else{//3,4G
		if(prepayFlag=='1'){//后付费
			prepayFlag='1'
		}else if(prepayFlag=='2'){//预付费
			prepayFlag='0'
		}else{
			prepayFlag='0'
		}
		cardType="04";
	}
	var GetURl= application.fullPath + "authority/checkResInfo/getCardData";
	$.ajax({
		url:GetURl,
		data:{
			"iccid":iccid,
			"numId":numId,
			"cardType":cardType,
			"userType":prepayFlag,
			"teleType":teleType,
			"orderId":order_id
		},
		dataType:'json',
		type:'post',
		//waitMsg:"正在写卡！",
		success:function(message){
			var returnData=message.args.ruturnData;
			if(returnData=="OK"){
				if ('2G' == teleType) {	
				var cardData=message.args.CardData;
				var imsi=message.args.IMSI;
				var procId=message.args.procId;
				//console.log(imsi);
				$("#cardData").val(cardData);
				$("#imsi").val(imsi);
				$("#procId").val(procId);//2G代表报文头流水 3G代表卡序列
				var activeId="";
				var capacityTypeCode="";
				var resKindCode="";
				}else{
				var imsi=message.args.card_info.imsi;
				var procId=message.args.card_info.procId;
				var activeId=message.args.card_info.activeId;
				var capacityTypeCode=message.args.card_info.capacityTypeCode;
				var resKindCode=message.args.card_info.resKindCode;
				var cardData=message.args.card_info.cardData;
				$("#imsi").val(imsi);
				$("#activeId").val(activeId);
				$("#cardData").val(cardData);
				$("#capacityTypeCode").val(capacityTypeCode);
				$("#resKindCode").val(resKindCode);
				}
				var ret=true;
				if('0'==writeWay){  
				 ret=true;
				}else{
				 ret=UpdateImsi(imsi,teleType);
				}
				if (ret==true){
					document.getElementById("in_text").innerHTML="正在校验...";
					var reasonId="0";//写卡成功
					var errorComments="";
					var NotifyURl= application.fullPath + "authority/checkResInfo/cardNotify";
					$.ajax({
						url:NotifyURl,
						data:{
							"iccid":iccid,
							"imsi":imsi,
							"procId":procId,
							"activeId":activeId,
							"capacityTypeCode":capacityTypeCode,
							"resKindCode":resKindCode,
							"reasonId":reasonId,
							"errorComments":errorComments,
							"teleType":teleType,
							"OrdersID":order_id,
							"wt_flag":wt_flag
							
						},
						dataType:'json',
						type:'post',
						//waitMsg:"正在校验！",
						success:function(message){
							var returnData=message.args.ruturnData;
							if(returnData=="OK"){								
								var iccid = $("#resourcesCode").val();//资源号
								var imsi = $("#imsi").val();
								var cardData = $("#cardData").val();
								var cardType = $("#cardType").val();
								var capacityTypeCode = $("#capacityTypeCode").val();
								var resKindCode = $("#resKindCode").val();
								var activeId = $("#activeId").val();
								var  procId = $("#procId").val();
								doAccountOpen(order_id,iccid,imsi,cardType,capacityTypeCode,resKindCode,cardData,activeId,procId);
								writeCardFlag=0;
							}else{
								hidediv('loading');
								writeCardFlag=0;
								$.alert(returnData);
								//支付宝退款
								/*if(pay_type!=""&& pay_type=="19"){
									alipayRefund(order_id);
								}
								else{
									var a = $("#payTypePc").find("select[style='display: inline-block;']");
									pay_type = typeof(a)!="undefined"?a.val():"";
									if(pay_type!=""&&pay_type=="19"){
										alipayRefund(order_id);
									}
								}*/
							}
						}
					});
				}else{
					  hidediv('loading');
					var reasonId="9";//写卡失败
				   var errorComments="写卡失败";//写卡失败
				$.ajax({
					url:"cardNotify",
					data:{
						"iccid":iccid,
						"imsi":imsi,
						"procId":procId,
						"activeId":activeId,
						"capacityTypeCode":capacityTypeCode,
						"resKindCode":resKindCode,
						"reasonId":reasonId,
						"errorComments":errorComments,
						"teleType":teleType,
						"wt_flag":wt_flag
					},
					dataType:'json',
					type:'post',
					waitMsg:"正在校验！",
					success:function(message){
						writeCardFlag=0;
						$.alert("读卡器写卡失败");
					}
				});
				}
			}else{
				  hidediv('loading');
				writeCardFlag=0;
				$.alert(returnData);
			}
		}
	});
};

//开户提交
function doAccountOpen( order_id, iccid,imsi,cardType,capacityTypeCode,resKindCode,cardData,activeId,procId){
	var accountOpenURL = application.fullPath + "authority/accountOpen/wcfdoAccountOpen";//开户url
	document.getElementById("in_text").innerHTML="卡数据同步...";
	$.ajax({
		type : "post",
		url : accountOpenURL,
		//waitMsg : "正在开户，请稍候！",
		data : {
			"order_id" : order_id,
			"SimID" : iccid,
			"imsi" : imsi,
			"cardType" : cardType,
			"capacityTypeCode" : capacityTypeCode,
			"resKindCode" : resKindCode,
			"cardData" : cardData,
			"activeId" : activeId,
			"procId":procId,
			"wt_flag":"0"
		},
		dataType : 'json',
		success : function(message) {
			var respCode = message.args.RespCode;
			var RespDesc = message.args.RespDesc;
			if (respCode == '0000') {//开户成功
				hidediv('loading');
				$.alert("开户成功");
				//updateWcfOrder();//更新沃创富订单
				$("#writeCard").removeAttr("onclick");
				$("#writeCard").removeClass("input_button");
				$("#writeCard").addClass("input_button_disabled");
				$("#readCard").removeAttr("onclick");
				$("#readCard").removeClass("input_button");
			    $("#readCard").addClass("input_button_disabled");
				//$("html,body").animate({scrollTop: $("#box4").offset().top}, 10)
			} else {//开户失败
				  hidediv('loading');
				var jsonObj=eval(message);
				var desc = jsonObj.content;
	    		$.alert(desc);
				//$("html,body").animate({scrollTop: $("#box4").offset().top}, 10)
			}
		},
		error : function(message) {//开户提交失败
			  hidediv('loading');
			var jsonObj=eval(message);
			var desc = jsonObj.content;
    		$.alert(desc);
			  // $("html,body").animate({scrollTop: $("#box3").offset().top}, 10);
		}
	});
}
function checkCustInfo(id_number){	 
	 var data1={
			  "id_number":id_number,
			  "tele_type":data.tele_type, 
			  "province_code":$("#province_code").text()			
		  }
	 var URL = application.fullPath + "authority/accountOpen/checkCustInfo";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"客户信息校验中..",
			success:function(message){
				if(message.type == "error"){
					$.alert("客户校验失败");
				}else{
					var checkCustInfo = message.args.cust_info;
					cust_seq_id = message.args.cust_seq_id;				
					var dialog=$.dialog({
						   title:'提示',//提示title
						   content:'校验成功',//显示的内容，支持html格式。
						   buttons:[{id:'btn_ok',text:'确定',
								   onClick:function(){									   
									   dialog.close();
									   $('#card_con').hide();
									   $("#number").hide();
									   $("#order_con").show();
									   var data1 = {
												"tele_type":data.tele_type,
												"id_type":"02",
												"id_number":($('#id_number').text()==null||$('#id_number').text()=="")?data.id_number:$('#id_number').text(),
												//"auth_address":($('#auth_adress').text()==null||$('#auth_adress').text()=="")?data.id_addr:$('#auth_adress').text(),
												"auth_end_date":$("#end_date_result").val(),
												"contact_adress":($('#auth_adress').text()==null||$('#auth_adress').text()=="")?data.id_addr:$('#auth_adress').text(),
												"contact_phone":data.device_number,
												"cust_seq_id": cust_seq_id,
												"customer_name":$('#custom_name_pc').text()
												
											
										}; 	
									    OrderUpdatedate(data1,"7");
									    if(data.terminal_model == ""||data.terminal_model ==null){//不需要校验串号											
										    if(data.device_number != null&&data.device_number!=''){//有手机号码
										    	 if(data.pay_type != "02"){//沃创富未收费
										    		 queryNumberAndYz();//查询号码信息中
										    	 }
											}	
									    }
								   }
									   
								}]
						});
						
					
				}
			}
			
		});	
	
	 
	
	  
};
//写卡完成更新沃创富订单状态
function updateWcfOrder(){	
	
	var data1={
		  "wcf_order_id" : $("#wcf_order_id").text()
		};
	 var URL = application.fullPath + "authority/restToController/updateWcfOrder";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			success:function(message){
				if(message.type == "error"){
					$.alert("更新沃创富订单失败");
				}
			}
			
		});	
	
	 
	
	  
};

function showdiv(popWinId) { 
	document.getElementById("bg_mask").style.display ="block";
	document.getElementById(popWinId).style.display ="block";
}
function hidediv(popWinId) {
	document.getElementById("bg_mask").style.display ='none';
	document.getElementById(popWinId).style.display ='none';
}

//加载号段显示
function loahaoduan(tele_type) {
	
	if(tele_type=="2G"){
		
		$("#pay_type_mobile_2G").show();
		$("#pay_type_mobile_3G").hide();
		$("#pay_type_mobile_4G").hide();		
		
		$("#mob_section option[value='185']").remove();
		$("#mob_section option[value='186']").remove();
		$("#mob_section option[value='145']").remove();
		$("#mob_section option[value='175']").remove();
		$("#mob_section option[value='176']").remove();		
		
	}else if(tele_type=="3G"){
		
		$("#pay_type_mobile_3G").show();
		$("#pay_type_mobile_2G").hide();
		$("#pay_type_mobile_4G").hide();
		$("#mob_section option[value='156']").remove();
		$("#mob_section option[value='155']").remove();
		$("#mob_section option[value='132']").remove();
		$("#mob_section option[value='131']").remove();
		$("#mob_section option[value='130']").remove();
		$("#mob_section option[value='145']").remove();
		$("#mob_section option[value='175']").remove();
		$("#mob_section option[value='176']").remove();	  
		
	}else if(tele_type=="4G"){
		
		$("#pay_type_mobile_4G").show();
		$("#pay_type_mobile_3G").hide();
		$("#pay_type_mobile_2G").hide();		
		$("#mob_section option[value='156']").remove();
		$("#mob_section option[value='155']").remove();
		$("#mob_section option[value='132']").remove();
		$("#mob_section option[value='131']").remove();
		$("#mob_section option[value='130']").remove();
		$("#mob_section option[value='145']").remove();
		
	}
	
	
}

function haoduanSelect(){
	var fuzzy_query = $.trim($("#fuzzy_query").val());
	var mob_section;
	 mob_section = $.trim($("#mob_section option:selected").val());
	 
	  if(mob_section=="185"||mob_section=="186"||mob_section=="176"){
			$("#show3G").show();
			$("#show2G").hide();
		}else{
			$("#show2G").show();
			$("#show3G").hide();
		}
		if(mob_section=="10000"){
			$("#show2G").hide();
			$("#show3G").hide();
		}
	 
	if(mob_section=="10000"){
		if(fuzzy_query=="请输入后1-8位数字查询号码"){
			$("#fuzzy_query").val("请输入1-11位数字查询号码");
		}
	}else{
		if(fuzzy_query=="请输入1-11位数字查询号码"){
			$("#fuzzy_query").val("请输入后1-8位数字查询号码");
		}
	} 
}

var intNumber=0;
function selectPage() {
	
	var perrty_type = $.trim($("#perrty_type_pc").val());
	var good_type = $.trim($("#good_type").val());
	var fuzzy_query = $.trim($("#fuzzy_query").val());
	var mob_section;
	
	mob_section = $.trim($("#mob_section").val());
	
	
	if(fuzzy_query=="请输入1-11位数字查询号码"){
		fuzzy_query="";
	}
	if(fuzzy_query=="请输入后1-8位数字查询号码"){
		fuzzy_query="";
	}
	
	if(fuzzy_query==""&&mob_section==""){
		
		$.alert("请选择号段或者输入号码查询!");
		return;
	}
  
	
	if(perrty_type=="1"){
		perrty_type="";
	}
	var GetURl= application.fullPath + "authority/numberData/queryWcfNumberData";
	$.ajax({
		url:GetURl,
		data:{
			"mob_section":mob_section,
			"perrty_type":perrty_type,
			"good_type":good_type,
			"fuzzy_query":fuzzy_query,
			"tele_type":data.tele_type,
			"page_info":"phonenumber.html",
			"order_id":order_id,
			"oper_no":data.oper_no
			
		},
		dataType:'json',
		type:'post',
		waitMsg:"查询号码",
		success:function(page){
			$("#haomalist").html('');
			var htmlNew = '';
			if(page.dataRows!=null){
			if(page.dataRows.length>0){
			 $("#dataLength").val(page.dataRows.length);
			 for(var i = 0;i < page.dataRows.length;i++){
				var numberBean = page.dataRows[i];
				if(data.tele_type=="3G"||data.tele_type=="4G"||data.tele_type=="3G4G"){
					var html = '<div class="wrap_line"  id="'+i+'wrap\"  deviceNumber="'+numberBean.acc_nbr+'\,'+numberBean.first_prepay+'\,'+numberBean.mon_limit+'\">'
						+'<a href="javascript:void(0);" onClick=\"jump(\''+numberBean.acc_nbr+'\',\''+numberBean.first_prepay+'\',\''+numberBean.mon_limit+'\',\''+numberBean.on_net+'\',0,\''+numberBean.class_id+'\')" class="code" id="'+numberBean.acc_nbr+'"> <span class="join" style="display:none;" id="'+numberBean.acc_nbr+'progress\">加入备选</span><dl><dt>'+numberBean.acc_nbr+'<dd>'
						+'首次预存'+numberBean.first_prepay+'元</dd>'
						+'  </dl></a></div>';
				}else{
					
						var html = '<div class="wrap_line"  id="'+i+'wrap\"  deviceNumber="'+numberBean.acc_nbr+'\,'+numberBean.first_prepay+'\,'+numberBean.mon_limit+'\">'
						+'<a href="javascript:void(0);" onClick=\"jump(\''+numberBean.acc_nbr+'\',\''+numberBean.first_prepay+'\',\''+numberBean.mon_limit+'\',\''+numberBean.on_net+'\',\''+numberBean.lock_flag+'\',\''+numberBean.class_id+'\')" class="code" id="'+numberBean.acc_nbr+'"> <span class="join" style="display:none;" id="'+numberBean.acc_nbr+'progress\">加入备选</span><dl><dt>'+numberBean.acc_nbr+'<dd>'
						//+'首次预存'+numberBean.first_prepay+','+'月消费'+numberBean.mon_limit+'元</dd>'
						+'首次预存'+numberBean.first_prepay+'元</dd>'
						+'  </dl></a></div>';
					
					
				}
					htmlNew +=html;
					
			 }
			 htmlNew +=' <div class="clear"></div>';
			 $("#haomalist").append(htmlNew);
			 startTime();
			}
			}
		}
	});
	
	
	
}

function jump(acc_nbr,first_prepay,mon_limit,on_net,lock_flag,class_id){

		if(data.tele_type=="2G"){
			var GetURl= application.fullPath + "authority/numberData/getMonLimitByNumber";
			var low_fee = '0';
			$.ajax({
				url:GetURl,
				data:{
					"device_number":acc_nbr,
					"order_id":order_id,
					"oper_no":data.oper_no
				},
				dataType:'json',
				type:'post',
				waitMsg:"号码信息获取中...",
				success:function(page){
					if(page.dataRows!=null){
						if(page.dataRows.length>0){
							low_fee = page.dataRows[0].mon_limit;
						}
					}		
//					if((tele_type=="3G"||tele_type=="4G"||tele_type=="3G4G")&&(low_fee != null && low_fee != '' && low_fee != '0')){
						mon_limit = low_fee;
//					}
					
					numberOccupyPre(acc_nbr,first_prepay,mon_limit,on_net,lock_flag,class_id);
					
				}
			});
		}else{
			numberOccupyPre(acc_nbr,first_prepay,mon_limit,on_net,lock_flag,class_id);
		}

  }

function numberOccupyPre(acc_nbr,first_prepay,mon_limit,on_net,lock_flag,class_id){
	if(mon_limit==null ||mon_limit==""){
		mon_limit = '0';
	}
	var content = '预占号：['+acc_nbr+']'+'<br>月消费：['+mon_limit+']元';
	if(data.tele_type=="2G"){
		content += '<br>首次预存：['+first_prepay+']元';
	}
	var dialog=$.dialog({
		   title:'提示',//提示title
		   content:content,//显示的内容，支持html格式。
		   buttons:[{id:'btn_ok',text:'确定',
			   onClick:function(){
				   if(lock_flag=='1'){//该号码已经被锁
					   $.alert("该号码已被预占,请重新选择号码!");
				   }else{
					 numberOccupy(acc_nbr,first_prepay,mon_limit,on_net,class_id);		
				   }
				   dialog.close();
			   }},
				{id:'btn_ok',text:'加入备选',
					   onClick:function(){	
							  if($("#"+acc_nbr).hasClass("selected")){
								 $("#"+acc_nbr).removeClass("selected");	 
								 var showId=acc_nbr+"progress";
								 $("#"+showId).removeClass("cancel");
								 $("#"+showId).hide(); 
								 var showDiv=acc_nbr+"show";
								 $("#"+showDiv).remove(); 
								 intNumber--;
								 if(parseInt(intNumber)==0){
									 $("#number_title1").hide();	
								 }
							 }else{
							   if(parseInt(intNumber)>2){
										
								}else{
								$("#number_title1").show();	
								$("#"+acc_nbr).addClass("selected");	
								var showId=acc_nbr+"progress";
								   $("#"+showId).hide();
								   /*if(tele_type=="3G"||tele_type=="4G"){
									   var html = '<div class="wrap_line border_red" id="'+acc_nbr+'show\"><a href="javascript:void(0);" onClick=\"shanChu(\''+acc_nbr+'\',\''+first_prepay+'\',\''+mon_limit+'\',\''+on_net+'\',0)" class="code code_current"><dl>'
											+'<dt>'+acc_nbr+'</dt><dd>首次预存'+first_prepay+'元</dd></dl><div class="close"></div></a></div>';
								   }else{*/
									   var html = '<div class="wrap_line border_red" id="'+acc_nbr+'show\"><a href="javascript:void(0);" onClick=\"shanChu(\''+acc_nbr+'\',\''+first_prepay+'\',\''+mon_limit+'\',\''+on_net+'\',\''+lock_flag+'\',\''+class_id+'\')" class="code code_current"><dl>'
										+'<dt>'+acc_nbr+'</dt><dd>首次预存'+first_prepay+',月消费'+mon_limit+'元</dd></dl><div class="close"></div></a></div>'; 
								   /*}*/
								   $("#compareInfo").append(html);
								   //$("#test1").css({'top':(document.documentElement.scrollTop-$("#test1").height())});
								   intNumber++;
							  }
							}
							 window.location.href = "#number_title1";
							 dialog.close();
					   }}
			   ]
	}); 
}

function shanChu(acc_nbr,first_prepay,mon_limit,on_net,lock_flag,class_id){
	var dialog=$.dialog({
		   title:'提示',//提示title
		   content:'预占号码：['+acc_nbr+']',//显示的内容，支持html格式。
		   buttons:[{id:'btn_ok',text:'确定',
			   onClick:function(){
				   if(lock_flag=='1'){//该号码已经被锁
					   $.alert("该号码已被预占,请重新选择号码!");
				   }else{
					numberOccupy(acc_nbr,first_prepay,mon_limit,on_net,class_id);
				   }
				   dialog.close();
			   }},
				{id:'btn_ok',text:'删除备选',
					   onClick:function(){	
						   $("#"+acc_nbr).removeClass("selected");	 
							 var showId=acc_nbr+"progress";
							 $("#"+showId).removeClass("cancel");
							 $("#"+showId).hide(); 
							 var showDiv=acc_nbr+"show";
							 $("#"+showDiv).remove(); 
							 intNumber--;
							 if(parseInt(intNumber)==0){	
								 $("#number_title1").hide();
							 }
						   dialog.close();
					   }}
			   ]
	}); 
	 
	 
}

function numberOccupy(acc_nbr,first_prepay,mon_limit,on_net,class_id){	

	var temp =  acc_nbr.substr(0,3);
	 
	 if( on_net==null || on_net=="" )	{
		 on_net = "1";
	 }	 		 
	 var data1={
			    "tele_type": data.tele_type,
				//传入号码
				"acc_nbr": acc_nbr,				
				"ser_type": on_net,
				"order_id":order_id,
				"oper_no":data.oper_no
		  }
	 var URL = application.fullPath + "authority/accountOpen/numberWcfOccupy";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"号码预占中..",
			success:function(message){					 
				if(message.type == "error"){					
					$.alert(message.content);
				}else{	
					
					$("#number_title2").show();
					window.location.href = "#number_title2";
					
						$("#lockInfo").html('<div class="wrap_line border_red" id="'+acc_nbr+'show\"><a href="javascript:void(0);" class="code code_current"><dl>'
								+'<dt>'+acc_nbr+'</dt><dd>首次预存'+first_prepay+',月消费'+mon_limit+'元</dd></dl><div class="close"></div></a></div>');
							
					var order_json={
						"acc_nbr":acc_nbr,
						"tele_type": data.tele_type,
						"acc_nbr_fee": first_prepay,
						"low_fee": mon_limit,
						"class_id": class_id
					};
					OrderUpdatedate(order_json,"2");//更新订单属性表
					if($("#connect_phone").val()==''||$("#connect_phone").val()==null){
						$("#connect_phone").val(acc_nbr);
					}
					
					var dialog=$.dialog({
						   title:'提示',//提示title
						   content:'号码：['+acc_nbr+']预占成功',//显示的内容，支持html格式。
						   buttons:[
								{id:'btn_ok',text:'确定',
									   onClick:function(){	
										   dialog.close();
										   document.getElementById("acc_nbr").innerHTML=acc_nbr;
										   data.device_number=acc_nbr;
										   if(data.pay_type == "02"){//沃创富已经收费
											    $("#card_con").hide();
												$("#number").hide();
												$("#order_con").show();		
										   }else{
												getFee();//获取费用 
										   }
										   
									   }}
							   ]
					}); 														
				}				
			}
			
		});	
	
	 
	
	  
};

function startTime()
{   
	 var foo = $("a[class='code']");
	 $(foo).each(function() {
		 $(this).mouseenter(function (){	
				 if($(this).hasClass("selected")){
					 var fooId = $(this).attr("id");
					 var showId=fooId+"progress";
					 $("#"+showId).text("取消备选");
					 $("#"+showId).addClass("cancel");
					 $("#"+showId).show(); 
				 }else{
					 var fooId = $(this).attr("id");
					 var showId=fooId+"progress";
					 if(parseInt(intNumber)>2){
						 $(this).addClass("code_gray");
						 $("#"+showId).text("备选已满")
					  }else{
					   $("#"+showId).text("加入备选");
					  }
					 $("#"+showId).show();
				 }
			});
		 $(this).mouseleave(function (){
			 if(parseInt(intNumber)>2){
				 $(this).removeClass("code_gray"); 
			 }
			 var fooId = $(this).attr("id");
			 var showId=fooId+"progress";
			 $("#"+showId).hide();
			});
	 });	
}

//保存费用信息
function saveFee(){
	
	var idnumber=$('#id_nbr').text();
	var acc_nbrs=$('#acc_nbr').text();
	 if(idnumber==null||idnumber==""){		
			$.alert("请选校验身份证!");
			return;
		}
	 else if(acc_nbrs==null||acc_nbrs==""){		
			$.alert("请选选择号码!");
			return;
	}	
	else if(data.terminal_model != "" && !terminal_flag){
			hidediv('loading');
			$.alert("请校验终端~");		
			return;
		}
	var pay_type=$("#pay_type").val();
	var order_json_tmp = {
			"pay_type": pay_type,
			"pay_type_code": pay_type,
			"fee_info": []
	};
	//for循环为订单更新添加内容
	for (var i = 0; i < fee_info.length; i++) {
			//服务器费用只支持字符
			var fee_result = {
				"fee_code": fee_info[i].fee_code,
				"fee_class":fee_info[i].fee_class,
				"fee_name": fee_info[i].fee_name,
				"rec_amount":fee_info[i].rec_amount + "",
				"rel_amount":parseFloat($("#charge_item"+i).val())+ "" ,
				"discount_flag": fee_info[i].discount_flag
			};
			order_json_tmp.fee_info.push(fee_result);
	
	
	}
	OrderUpdatedate(order_json_tmp,"5");
	
}

function dealFee(){
	id_number = $("#id_nbr").text();
	if(id_number == ""){
		hidediv('loading');
		$.alert("请读取证件~");
		return;
	}	
	if(data.terminal_model != "" && !terminal_flag){
		hidediv('loading');
		$.alert("请校验终端~");		
		return;
	}
	var messageTmp="";
    var payType=$("#pay_type").val();//付费方式标标识
    var perNo=data.oper_no==null?document.getElementById("oper_no").value:data.oper_no;
    posCardFlag ++;
	if(posCardFlag>1){
		$.alert("请不要重复点击收费按钮");
		 return;
	}
    
    if(payType=='15'){   //传统银行卡接口
    	/*-----------------------调用pos刷卡操作接口开始-------------------------------------------------*/
	    	var result;
			umsocx.operId = perNo;
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
					$.alert("设备初始化失败");
					posCardFlag=0;
					return;
				}
			}else{
				document.getElementById("rspCode").value = umsocx.rspCode;
				document.getElementById("rspChin").value = umsocx.rspChin;
				$.alert("rspCode  "+umsocx.rspCode+"umsocx  "+umsocx.rspChin);
				posCardFlag=0;
				return;
			}
    	   
    	    //post机收费请求URL
		    var URL = application.fullPath + "authority/orderFee/queryOrderFeeProsessingPos";
		    var data1={
					"order_id":order_id,
					"oper_id":perNo,
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
			
				$.ajax({
					type : "POST",
					url : URL,
					data : data1,
					dataType:'json',
					waitMsg:"请稍等......",
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					success:function(message){
						var jsonObj=eval(message);	
						if(jsonObj.type=='success'){
							dodealFee(payType);
							posCardFlag=0;
						}else{
							 messageTmp = message;
							  var jsonObj=eval(messageTmp);
							  var desc = jsonObj.content;
							  posCardFlag=0;
							  $.alert(desc);
						}
						
					},
				  error:function(message){
					 
					   messageTmp = message;
					  var jsonObj=eval(messageTmp);
					  var desc = jsonObj.content;
					  posCardFlag=0;
					  $.alert(desc);
				  }
				});
		}else{//不是传统银行卡接口
			dodealFee(payType);
			posCardFlag=0;
	   }
   }
//调用接口收费部分
function dodealFee(payType){  
	  posCardFlag = 0;	 
	  /*-----------------------调用付费接口开始-------------------------------------------------*/
		var data={
			"order_id":order_id,
			"trade_no":document.getElementById("reference").value,			
			"pay_type":payType
			
		};
	    //收费接口url
    var URL = application.fullPath + "authority/dealShowOrder/toWcfOrderSubmit"; 
	    $.ajax({
			type : "POST",
			url : URL,
			data : data,
			dataType:'json',
			waitMsg:"请稍等......",
			success:function(message){
				var jsonObj=eval(message);
				if(jsonObj.type=='success'){
					    							
					/*-----------------------调用更新订单接口开始-------------------------------------------------*/
					var data2={
							"order_id":order_id,
							"trade_no":document.getElementById("reference").value,
							"acc_nbr":data.device_number,
							"pay_type":payType
						};
				    var URl = application.fullPath + "authority/dealShowOrder/updateOrderFee";
			       $.ajax({
						type : "POST",
						url : URl,
						data : data2,
						dataType:'json',
						waitMsg:"请稍等......",
						success:function(message){
							var jsonObj=eval(message);
							if(jsonObj.type=='success'){
								//$.alert("收费成功");
								$("#btnGetFee").removeAttr("onClick");
								$("#btnGetFee").removeClass("input_button");
								$("#btnGetFee").addClass("input_button_disabled");
								/*
								$("#readCard").removeClass("input_button_disabled");
								$("#readCard").addClass("input_button");
								$("#readCard").removeAttr("onclick");
								
								$("#writeCard").removeClass("input_button_disabled");
								$("#writeCard").addClass("input_button");
								 $("#writeCard").removeAttr("onclick");
								 
								$("#readCard").bind("click",function (){
									readCardBtn();					
								});
								$("#writeCard").bind("click",function (){
									writeCardBtn();					
								});
								*/
							    $("#fee_flag").text("(已收费)");
							    is_shoufei=true;
							    var dialog=$.dialog({
									   title:'提示',//提示title
									   content:'收费成功',//显示的内容，支持html格式。
									   buttons:[{id:'btn_ok',text:'确定',
											   onClick:function(){									   
												   dialog.close();								  										
												   wcfuploadFormPdf();//生产协议

											   }
												   
											}]
									});
							   
						}else{
							var desc = jsonObj.content;
				    		$.alert(desc);
						  
						}
					},
			    	error:function(message){
			    		var jsonObj=eval(message);
						var desc = jsonObj.content;
			    		$.alert(desc);
			    		//$("html,body").animate({scrollTop: $("#box3").offset().top}, 10);
					}
			    });
			 
			}else{				
				var jsonObj=eval(message);
				var desc = jsonObj.content;
	    		$.alert(desc);

				
			}
		},
	error:function(message){
		var jsonObj=eval(message);
		var desc = jsonObj.content;
		$.alert(desc);
		
		}
  });
	  
}
function paymentOrderBill(){
	if(!is_shoufei){
		$.alert("请先收费再打印!");
		return;
	}
	if(window.confirm('该功能只打收据~请勿打发票!')){
		var payType= $("#pay_type").val();
		var wt_flag="2";
		window.open(application.fullPath+"authority/wkprint/paymentorderbill?order_id="+order_id+"&wt_flag="+wt_flag+"&pay_type="+payType,"order_idNew"+order_id);
	}
	
}

/*费用 相关JS  =====================开始======================*/
function getFee(){
	
	 var first_month_fee = '';
	 var product_id_tmp = '';
	 var activity_id_tmp = '';
	 var activity_type_tmp = '';
		 	
	 var data1={
				//订单编号
				"wcf_order_id":wcf_order_id,
				"order_id":order_id
			};
	 var URL = application.fullPath + "authority/restToController/getWcfFee";
	 fee_all =0;
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			beforeSend:function(){
				$("#step4_next_div").removeClass("ok").addClass("ok_disabled");
			},
			waitMsg:"获取费用信息中..",
			success:function(message){					 
				if(message.type == "error"){
					$.alert(message.content);
				}else{	
					
					 $("#fee_list").html('');
					 fee_info = message.args.fee_info;			
					 
					 $.each(fee_info, function(i, item) {
						var data = JSON.stringify(item);
						if(item.fee_name=="[预存]" && tele_type=="4G" && parseInt(select_mon_limit)>0	){
								 item.fee_name ="[靓号预存]";
							 }
						if(item.discount_flag=='1'){//不可减免	
						 $("#fee_list").append('<li class="list">'+item.fee_name+'</li><li class="list"><div class="left"><div class="left_lable">应收：<span class="bold">'+	    				
						 item.rec_amount+'</span>&nbsp;元</div><div class="right_data">实收：<input type="text" readonly="true" id="charge_item'+i+'" name="'+item.fee_name+'" onChange="CheckChargeItem(this,1);" data="'+item.rec_amount+'" class="input_text width_8 text_normal_b" value="'+	    				
						 item.rec_amount+'">&nbsp;元</div></div></li>');
						}else if(item.discount_flag=='2'){//不可小于该值	
							 $("#fee_list").append('<li class="list">'+item.fee_name+'</li><li class="list"><div class="left"><div class="left_lable">应收：<span class="bold">'+	    											
							 item.rec_amount+'</span>&nbsp;元</div><div class="right_data">实收：<input type="text" id="charge_item'+i+'" name="'+item.fee_name+'" onChange="CheckChargeItem(this,2);" data="'+item.rec_amount+'" class="input_text width_8 text_normal_b" value="'+		    						    				
							 item.rec_amount+'">&nbsp;元</div></div></li>');
						}else{
							 $("#fee_list").append('<li class="list">'+item.fee_name+'</li><li class="list"><div class="left"><div class="left_lable">应收：<span class="bold">'+	    											
							 item.rec_amount+'</span>&nbsp;元</div><div class="right_data">实收：<input type="text" id="charge_item'+i+'" name="'+item.fee_name+'" onChange="CheckChargeItem(this,1);" data="'+item.rec_amount+'" class="input_text width_8 text_normal_b" value="'+		    						    				
							 item.rec_amount+'">&nbsp;元</div></div></li>');
						}
							fee_all = parseFloat(item.rec_amount) + parseFloat(fee_all);
						
					 });					 
					 $("#fee_total").html("&nbsp;&nbsp;&nbsp;"+fee_all);
					   $("#card_con").hide();
					   $("#number").hide();
					   $("#order_con").show();	
				}				
			}
			
		});	
}

function getTotalFee(){
	fee_all =0;
	$.each(fee_info, function(i, item) {
		var test = "#charge_item"+i;
		fee_all =  parseFloat($("#charge_item"+i).val()) + parseFloat(fee_all);
	});
	$("#fee_total").html("&nbsp;&nbsp;&nbsp;"+fee_all);
}

function CheckChargeItem(e,flag){
	var div = $(e);
	var fee_pre = div.attr("data");	
	
	if(flag==1){
		if(parseFloat(e.value)>parseFloat(fee_pre)){
			$.alert("实收不能大于应收")
			e.value = parseFloat(fee_pre);	
		}	
	}else if(flag==2){
		if(parseFloat(e.value)<parseFloat(fee_pre)){
			$.alert("实收不能小于应收")
			e.value = parseFloat(fee_pre);	
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

}

/*页面加班后自动保存沃创富订单表数据到属性表*/
function saveDataToAttr(){
	 var data1={
				//订单编号
				"wcf_order_id":wcf_order_id,
				"order_id":order_id,
				"jsessionid":$("#jsessionid").val()
				
			};
	 var URL = application.fullPath + "rest/wcf/saveWcfOrderToAttr";
	 fee_all =0;
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,			
			success:function(message){					 
				if(message.type == "error"){
					$.alert(message.content);
				}else{
					if(data.pay_type == "02"){//沃创富已收费
						$("#btnGetFee").removeAttr("onClick");
						$("#btnGetFee").removeClass("input_button");
						$("#btnGetFee").addClass("input_button_disabled");
						
						$("#btnDaYinBillData").removeAttr("onClick");
						$("#btnDaYinBillData").removeClass("input_button");
						$("#btnDaYinBillData").addClass("input_button_disabled");
					}
					
				}		
			}
			
		});	
}

/*对沃创富传的号码进行查询预占*/
function queryNumberAndYz(){
	
	document.getElementById("in_text").innerHTML="查询号码信息...";
	showdiv('loading');
			 	
	 var data1={
				//订单编号
				"wcf_order_id":wcf_order_id,
				"order_id":order_id
			};
	 var URL = application.fullPath + "rest/wcf/queryNumberAndYz";
	 fee_all =0;
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,			
			success:function(message){	
				hidediv('loading');
				if(message.type == "error"){
					$.alert(message.content);
				}else{					
					getFee();//获取费用信息
				}				
			}
			
		});	
}

/*沃创富生产pdf协议*/
function wcfuploadFormPdf(){

	 var data1={
				//订单编号
				"order_id":order_id,
				"jsessionid":$("#jsessionid").val()
			};
	 var URL = application.fullPath + "rest/paperless/uploadFormPdf";
	 fee_all =0;
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,	
			waitMsg:"生成协议中..",
			success:function(message){	

				if(message.type == "error"){
					$.alert(message.content);
				}else{					
					$.alert("协议生成成功!");
					return;
				}				
			}
			
		});	
}

