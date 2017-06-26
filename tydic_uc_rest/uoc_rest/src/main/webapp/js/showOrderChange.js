var baseInfo = {
	prefix:""
};

var wt_flag = "";
var posCardFlag = 0;
var pay_flag = false;
$(document).ready(function() {	
	var payType = $("#payType").val();//支付方式
	var payFlag = $("#payFlag").val();//支付方式
	var change_order_type = $("#change_order_type").val();
	
	wt_flag = $("#wt_flag").val();
	
	if(wt_flag == "1"){
	   	 //支付方式仅支持“支付宝”
	   	$("#pay_type").html("");
	   	 $("#pay_type").append("<option value='19'>支付宝</option>");
   }
	
    var order_status = $("#order_status").val();//订单状态
    //YUN-778  bug 修正
    if(change_order_type == '1'){
    	baseInfo.prefix = "tarriff";
    }else if(change_order_type == '2'){
    	baseInfo.prefix = "callLevel";
    }else if(change_order_type == '3'){
    	baseInfo.prefix = "openOrClose";
    }else if(change_order_type == '4'){
    	baseInfo.prefix = "colseAccount";
    }else if(change_order_type=='5'){
    	baseInfo.prefix = "business";
    }else if(change_order_type=='6'){
    	baseInfo.prefix = "redeemPoints";
    }else if(change_order_type=='7'){
    	baseInfo.prefix = "remakeCard";
    }else if(change_order_type=='8'){
    	baseInfo.prefix = "transfer";
    }

    $("#" + baseInfo.prefix + "box2").show();
    
    if(payFlag=="Y"){
		$("#payTypePc").hide();
		$("#payTypeMobile").show();
	}else{
		$("#payTypeMobile").hide();
		$("#payTypePc").show();
	}
	if(order_status=='C10'){
		$("#btnDaYin").bind("click",function (){
			paymentBill();					
		});
		$("#btnGetFee").removeAttr("onclick");
		$("#btnGetFee").removeClass("view_btn");
		$("#btnGetFee").addClass("btn_disabled");
		$("#okSubmit").removeAttr("onclick");
		$("#okModule").removeClass("ok");
		$("#okModule").addClass("ok_disabled");	
		$("#pay_type_mobile").attr("disabled",true);  
		$("#pay_type_mobile option[value='"+payType+"']").attr('selected','selected'); 
		//$("#readCard").attr("onclick","readCardBtn();");		
		$("#getFee").text("(已收费)");
		$("#sytleProgress").removeAttr("src");
		$("#sytleProgress").attr("src",application.fullPath +"images/tow_2.png");
	};
	if(order_status=='C00'){
		$("#okSubmit").removeAttr("onclick");
		$("#okModule").removeClass("ok");
		$("#okModule").addClass("ok_disabled");
		
		$("#btnDaYin").removeClass("view_btn");
		$("#btnDaYin").addClass("btn_disabled");
		
		$("#pay_type option[value='"+payType+"']").attr('selected','selected'); 
		//$("#sytleProgress").addClass("progress_3");
		$("#sytleProgress").attr("src",application.fullPath +"images/tow_1.png");
	};
$("#shenSuoBianGeng").bind("click",function(){
	 var p_class = $("#shenSuoBianGeng").attr("class");
	 if(p_class=="down"){
		 $("#shenSuoBianGeng").attr("class", ""); 
	 }else{
		 $("#shenSuoBianGeng").attr("class", "down"); 
	 }
	 $("#box1").toggle();   
	});
$("#shenSuoYeWu").bind("click",function(){
	 var p_class = $("#shenSuoYeWu").attr("class");
	 if(p_class=="down"){
		 $("#shenSuoYeWu").attr("class", ""); 
	 }else{
		 $("#shenSuoYeWu").attr("class", "down"); 
	 }
	 $("#" + baseInfo.prefix + "box2").toggle();   
	});
$("#shenSuoTiaoKuan").bind("click",function(){
	 var p_class = $("#shenSuoTiaoKuan").attr("class");
	 if(p_class=="down"){
		 $("#shenSuoTiaoKuan").attr("class", ""); 
	 }else{
		 $("#shenSuoTiaoKuan").attr("class", "down"); 
	 }
	 $("#box3").toggle();   
	});
$("#shenSuoFeiYong").bind("click",function(){
	var p_class = $("#shenSuoFeiYong").attr("class");
	if(p_class=="down"){
		$("#shenSuoFeiYong").attr("class", ""); 
	}else{
		$("#shenSuoFeiYong").attr("class", "down"); 
	}
	$("#box4").toggle();   
	});
});
function pdfIsSign(){
	 var payType= $("#pay_type").val();
	 var data={
			"order_id":$("#order_id").val(),
			"trade_no":document.getElementById("reference").value,
			"acc_nbr":$("#acc_nbr").val(),
			"order_sub_type":$("#order_sub_type").val(),
			"pay_type":payType
	 };
   
    if(payType=='10'){	
    	dodealFee(payType);
	}else if(payType=='15'){
		//传统银行卡接口
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
				$.alert("设备初始化失败");
				return;
			}
		}else{
			document.getElementById("rspCode").value = umsocx.rspCode;
			document.getElementById("rspChin").value = umsocx.rspChin;
			$.alert("rspCode  "+umsocx.rspCode+"umsocx  "+umsocx.rspChin);
			return;
		}
			var data1={
					"order_id":$("#order_id").val(),
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
				data : data1,
				dataType:'json',
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				success:function(message){
					var jsonObj=eval(message);
					if(jsonObj.type=='success'){
						dodealFee(payType);
					}else{
						$.alert("收费失败");  
					}
				},
			  error:function(message){
				  $.alert("收费失败");  
			  }
			});
	}
	else if(payType == '19'){
		alipayScan($("#order_id").val(),
				$("#discount_totalCosts_input").val(),"广西联通电子流智能营业厅-变更",$("#tele_type").val(),
    			'19',$("#oper_no").val(),$("#acc_nbr").val());
	}
	
}
function okSumbit(){
    var content = '<div class="msgbox"><div class="serial">订单已完成</div><div class="intro">发展积分为：0.0 分<br />受理积分为：0.0 分</div><div class="msgbox_ok"><a href="###" id="okModou">确定</a></div></div>';
		$.dialog({
			width:400,
			draggable:false,
			content:content
		});
		$("#okModou").bind("click",function(){
            $(".xxDialog").remove();
            $(".dialogOverlay").remove();
            window.close();
			
		});
}
function apweb(){
	var order_id = $("#order_id").val();//订单号
    var apweb_url = $("#apweb_url").val();
	//修改bug 变更pdf协议显示
	var template_type_of_change =$("#template_type_of_change").val();
	if (template_type_of_change == '' || template_type_of_change == null || template_type_of_change == undefined) { 
		alert("亲，没有对应的协议，联系系统管理员添加对应的协议！！！");
		return;
	} else { 
		window.open(apweb_url+"form/formPdfDetail.action?folat=2&templateid="+template_type_of_change+"&formsn="+order_id);
	} 
}

function dodealFee(payType){
	var data2;
	var URl;
	if ($("#change_order_type").val() == '8'){
		 data2={
			 "order_id":$("#order_id").val(),
			 "device_number":$("#acc_nbr").val(),
			 "oper_no":$("#oper_no").val(),
			 "old_customer_id":$("#old_customer_id").val(),
			 "old_id_type":$("#old_id_type").val(),
			 //"id_type":$("#id_type").val(),
			 "id_type":"id_card",
			 "customer_id":$("#customer_id").val(), 
			 "charge":$("#discount_totalCosts_input").val(),
			 "charge_code":$("#guo_hu_charge_code").val(),
			 "customer_name":$("#customer_name").val(),
			 "pay_flag":"Y",
			 "pay_type":payType
		 }
		URl = application.fullPath + "rest/zonghebianggen/guohu"; 			 
	}else if($("#change_order_type").val() == '9'){

		 data2={
				 "order_id":$("#order_id").val(),
				 "deviceNum":$("#acc_nbr").val(),
				 "customer_name":$("#customer_name").val(),
				 "id_type":"ID001",
				 "customer_id":$("#customer_id").val(), 
				 "remark":$("#auth_adress").val(),
				 "jsessionid":$("#jsessionid").val(),
				 "pay_flag":"Y",
				 "pay_type":payType
			 }
			URl = application.fullPath + "rest/customer/fandang"; 
		
	}else if($("#change_order_type").val() == '1'){
		 data2={
				 "order_id":$("#order_id").val(),
				 "platform_type": "pc"
		 }
		 URl = application.fullPath + "rest/sale/tarrifChangeOrderSubmit";			 			 
	 }
	else if($("#change_order_type").val() == "5"){//优惠购机
		if($("#mkt_activity").val() == "1"){//购机直降
			var mkt_typeStr="";				 
			var charge =parseInt( $("#discount_totalCosts_input").val());				
			data2={					
					"device_number":$("#acc_nbr").val(),	       		
					"mkt_code":$("#mkt_code").val(),						
       			 	"mkt_type":mkt_typeStr,
       			 	"mobile_str":$("#terminal_id").val(),
       			 	"charge":charge,	       			   
       			 	"change_flag":"1",
       			 	"order_id":$("#order_id").val()
			};
			URl = application.fullPath + "authority/mkt/dosubmitmkt" ;
		}else{
			data2={
					//订单编号
					"ordersId":$("#order_id" ).val(),//请求报文用
					"order_id":$("#order_id" ).val(),//更新订单状态用                              
					//号码类型
					// "tele_type": "4G",                                
					"acceptanceReqTag":"1" ,                             
					payInfo:JSON.stringify(
							{
								"payType":"10" ,
								"payOrg":"" ,
								"payNum":""
							}
					),
					"wt_flag":wt_flag
			};
			URl = application.fullPath + "authority/order/threeDiscountSubmit" ;
		}
					
	}
	else if($("#change_order_type").val() == "7"){//3G4G补换卡
		data2={
				"jsessionid":$("#jsessionid").val(),
				"order_id":$("#order_id" ).val(),      					
				"pay_type":"10"	
		};
		URl = application.fullPath + "rest/orderInfo/chg_re_card" ;
	}
	else{
		data2={
				"order_id":$("#order_id").val(),
				"trade_no":document.getElementById("reference").value,
				"acc_nbr":$("#acc_nbr").val(),
				"order_sub_type":$("#order_sub_type").val(),
				"pay_type":payType,
				"tele_type":"3G",
				"wt_flag":wt_flag
		};
		URl = application.fullPath + "authority/dealShowOrder/updateOrderFee";
	}
	$.ajax({
		type : "POST",
		url : URl,
		data : data2,
		dataType:'json',
		timeout:100000,
		waitMsg:"请稍等......",
		success:function(message){
			var jsonObj=eval(message);
			//$.alert(jsonObj.type);
			if(jsonObj.type=='success'){
				$.alert("缴费成功");
				$("#btnGetFee").removeAttr("onclick");
				$("#btnGetFee").removeClass("view_btn");
				$("#btnGetFee").addClass("btn_disabled");
				//$("#okSubmit").attr("onclick","okSumbitww();");
				$("#okModule").removeClass("ok_disabled"); 
				$("#okModule").addClass("ok");
				$("#btnDaYin").removeClass("btn_disabled");
				$("#btnDaYin").addClass("view_btn");
				$("#btnDaYin").bind("click",function (){
					paymentBill();					
				});
			    $("#getFee").text("(已收费)");
			    $("#payFlag").attr("value","Y");
				$("#okSubmit").bind("click",function (){
					okSumbit();
							
					
				});
			  // $("html,body").animate({scrollTop: $("#box3").offset().top}, 10);
			}else{
				var chg_re_car = $("#change_order_type").val();
				if(chg_re_car=="7"){
					 $.alert(JSON.stringify(jsonObj.content));
					 return;
				}
			    $.alert("收费失败");
			   //$("html,body").animate({scrollTop: $("#box3").offset().top}, 10);
			    
			    //支付宝失败发起退款
			    if(payType == '19'){
			    	alipayRefund($("#order_id").val());
			    }
			}
		},
    	error:function(message){
    		$.alert("收费失败");
    		//$("html,body").animate({scrollTop: $("#box3").offset().top}, 10);
    		
    		//支付宝失败发起退款
		    if(payType == '19'){
		    	alipayRefund($("#order_id").val());
		    }
		}
    });
	
	posCardFlag = 0;
}

/*支付宝扫码支付*/
function alipayScan(order_id,total_amount,subject,tele_type,pay_type,oper_id,device_number){
//	 console.log("order_id="+order_id+";total_amount="+total_amount+";subject="+subject+";tele_type="+tele_type+";pay_type="+pay_type+";oper_id="+oper_id+";device_number="+device_number);
	
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
		success:function(message){
			if(message.type == "error"){
				if(message.content == "该订单已经支付！"){
					$.alert("支付成功！");
					dodealFee('19');
				}
				else{
					$.alert(message.content);
					posCardFlag = 0;
				}
			}else{
				//将二维码输出到页面
				var htmlStr = message.content;
				//$(".out_div").html("<img class=\"pic-idPic\" src='"+application.fullPath+"authority/alipay/ie6twocode?date="+(new Date()).getTime()+"' /><div style='width:100%;text-align:center'><span style='color:#F00;font-size:20px'>支付完成前不要关闭本窗口！</span></div>");
				$(".pic-idPic").attr( "src", application.fullPath+"authority/alipay/ie6twocode?date="+(new Date()).getTime() );
				
				$( ".out_div" ).show();
				$(document).on('click', '#closetwocode', function() {
					$( ".out_div" ).hide();
					posCardFlag = 0;
				});
				
				//轮询支付状态，支付成功则停止
				searchStatusInterval(order_id);
			}
		},
		error:function(){
			alert("ajax error");
			posCardFlag = 0;
		}
	});
}
/*
 * 轮询订单状态
 * 
 */
function searchStatusInterval( order_id ){
//	console.log("窗体是否是显示状态--"+$(".xxDialog").css('display') == "block");
	//二维码对话框没有关闭的情况下进行轮询
	if($(".out_div").css('display') == "block"){
		$.ajax({
			url:application.fullPath + "authority/alipay/searchStatus",
			data:{"order_id":order_id},
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			dataType:'json',
			timeout:22000,
			type:'post',
			success:function(message){
				if(message.type == "success"){
					$( ".out_div" ).hide();
					$("#showLoadNotice").hide();
					
					pay_flag = true;
					//$.alert("支付成功！");
					dodealFee('19');
				}else{
					searchStatusInterval(order_id); // 递归调用
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown) {
	            if (textStatus == "timeout") { // 请求超时
	            	//console.log("重复请求！");
	            	searchStatusInterval(order_id); // 递归调用
	                // 其他错误，如网络错误等
	                } else { 
	                	searchStatusInterval(order_id);
	                }
	            }
		});
	}//如果二维码窗口关闭，但是支付状态 pay_flag = false 那么向支付宝查询下该订单是否支付
	else{
		if(pay_flag == false){
			$.ajax({
				url:application.fullPath + "authority/alipay/aliSearchStatus",
				data:{"order_id":order_id},
				contentType: "application/x-www-form-urlencoded; charset=utf-8", 
				dataType:'json',
				type:'post',
				success:function(message){
					if(message.type == "success"){
						if(message.content == "TRADE_SUCCESS" || message.content == "TRADE_FINISHED"){
							dodealFee('19');
						}else{
							//do nothing
						}
					}else{
						 //do nothing
					}
					
					posCardFlag = 0;
				},
				error:function(XMLHttpRequest, textStatus, errorThrown) {
		            $.alert('网络错误！');
		            posCardFlag = 0;
		            }
			});
		}
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

//查询协议是否已签
function dealFee(){
	posCardFlag ++;
	if(posCardFlag>1){
		$.alert("请不要重复点击收费按钮");
		 return;
	}
	//补卡要先确认签名才能收费
	if($("#change_order_type").val() == "7"){
		$.ajax({
			type : "POST",
			url :application.fullPath + "rest/paperless/pdfStatusCode",
			data :{
		         "order_id":$("#order_id" ).val(),//"20160823100000036494",
		         "jsessionid":$("#jsessionid").val()
				},
			dataType:'json',
			waitMsg:"请稍等......",
			success:function(message){
				if(message.type=="success"){
					if(message.args.pdfStatusCodes.变更模版=="000"){
						pdfIsSign();
					}else if(message.args.pdfStatusCodes.变更模版=="001"){
						posCardFlag=0;
						$.alert("工单不存在！");
						return;
					}else if(message.args.pdfStatusCodes.变更模版=="002"){
						posCardFlag=0;
						$.alert("文档不存在！");
						return;
					}else if(message.args.pdfStatusCodes.变更模版=="003"){
						posCardFlag=0;
						$.alert("未签名，请先签名再收费！");
						return;
					}else if(message.args.pdfStatusCodes.变更模版=="004"){
						posCardFlag=0;
						$.alert("工单生成失败！");
						return;
					}
				}else{
					posCardFlag=0;
					$.alert("未签名，请先签名再收费！");
				}
			}
	    });
	}else{
		pdfIsSign();
	}
	
}
	