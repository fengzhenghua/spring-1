var load_rwcard_acx = false;
var order_source ='';//订单来源
var writeCardFlag =0;
var posCardFlag=0;
var province_code='';
var dialog_scan;//支付宝二维码对象
var pay_flag = false;//订单支付状态
var pay_type = "";//支付方式
var pdf_resign_time ="";
var pdf_residue_time ="";
var reSignBtn="";

var wt_flag = "";
var big_agent_flag = "";
$(document).ready(function() {
	var payType = typeof($("#payType"))!="undefined"?$("#payType").val():"";//支付方式
	pay_type = payType;
    var order_status = $("#order_status").val();//订单状态
    province_code = $("#province_code").val();
    order_source = $("#order_source").val();
    wt_flag = $("#wt_flag").val();
    big_agent_flag = $("#big_agent_flag").val();
    pdf_resign_time = $("#pdf_resign_time").val();
    
    if(pdf_resign_time > 15){
    	pdf_residue_time = 100000000;
    }else{
    	pdf_residue_time = 15 - pdf_resign_time;
    }
    
    if(wt_flag == "1"){
    	 //支付方式仅支持“支付宝”
    	$("#pay_type_2G").html("");
    	$("#pay_type_3G").html("");
    	$("#pay_type_4G").html("");
    	 $("#pay_type_2G").append("<option value='19'>支付宝</option>");
    	 $("#pay_type_3G").append("<option value='19'>支付宝</option>");
    	 $("#pay_type_4G").append("<option value='19'>支付宝</option>");
    }
    
    if(province_code=="nx" && big_agent_flag == "1"){
    	$("#pay_type_2G").html("");
    	$("#pay_type_3G").html("");
    	$("#pay_type_4G").html("");
    	$("#pay_type_2G").append("<option value='19'>支付宝</option>");
    	$("#pay_type_3G").append("<option value='19'>支付宝</option>");
    	$("#pay_type_4G").append("<option value='19'>支付宝</option>");
    }
    
    if(province_code=="nx"){
    	$("#whiteOrAdult").show();
    	$("#tiaokuanConfirm").hide();    	
    	 if(order_source=="100"){
 	    	$("#xuhao2").html("5");
 	    	$("#xuhao3").html("6");
 	    	//xuhao4 只有23g才显示 tmd 建议不要序号"*"
 	    	$("#xuhaoCommission").html("8");
 	    	$("#xuhao4").html("7");
 	    	
 	    	//$("#xuhao5").html("8");
 	    	//$("#xuhao6").html("9");
 	    	$("#xuhao0").html("");
 	    	window.location.href = "#feiyong_xinxi";
 	    }else{
 	    	var index=2;
 	    	$("#xuhao2").html(++index);
 	    	$("#xuhao3").html(++index);
 	    	//xuhao4 只有23g才显示 tmd
 	    	$("#xuhaoCommission").html(++index);
 	    	$("#xuhao4").html(++index);
 	    	
 	    	//$("#xuhao5").html(++index);
 	    	//$("#xuhao6").html(++index);
 	    }
    	
    }else{
	    if(order_source=="100" && order_status!="A30"){
	    	var index=5;
	    	$("#xuhao1").html(index);
	    	$("#xuhao2").html(++index);
	    	$("#xuhao3").html(++index);
	    	//xuhao4 只有23g才显示 tmd
	    	$("#xuhaoCommission").html(++index);
	    	$("#xuhao4").html(++index);
	    	
	    	//$("#xuhao5").html(++index);
	    	//$("#xuhao6").html(++index);
	    	$("#xuhao0").html("");
	    	window.location.href = "#tiaokuan_info";
	    }else{
	    	var index=2;
	    	$("#xuhao1").html(index);
	    	$("#xuhao2").html(++index);
	    	$("#xuhao3").html(++index);
	    	//xuhao4 只有23g才显示 tmd
	    	$("#xuhaoCommission").html(++index);
	    	$("#xuhao4").html(++index);
	    	//$("#xuhao5").html(++index);
	    	//$("#xuhao6").html(++index);
	    }
    }
	var payFlag = $("#payFlag").val();//支付方式
	if(payFlag=="Y"){
		$("#payTypePc").hide();
		$("#payTypeMobile").show();
	}else{
		$("#payTypeMobile").hide();
		$("#payTypePc").show();
	}
	
	$("#pay_type_2G").hide();
	$("#pay_type_3G").hide();
	$("#pay_type_4G").hide();
	if($("#tele_type").val()=='2G'){
		operPayType('2G');
		$("#pay_type_2G").show();
	}else if($("#tele_type").val()=='3G'){
		operPayType('3G');
		$("#pay_type_3G").show();
	}else if($("#tele_type").val()=='4G'){
		operPayType('4G');
		$("#pay_type_4G").show();
	}
	
	if(order_status=='A20'||order_status=='A30'){
		$("#btnGetFee").removeAttr("onclick");
		$("#btnGetFee").removeClass("view_btn");
		$("#btnGetFee").addClass("btn_disabled");
		//$("#readCard").removeAttr("onclick");
		$("#readCard").removeClass("view_btn");
		$("#readCard").addClass("btn_disabled");
		$("#readAdultCard").removeClass("view_btn");
		$("#readAdultCard").addClass("btn_disabled");
		//$("#readCard").attr("onclick","readCardBtn();");
		//$("#writeCard").removeAttr("onclick");
		$("#writeCard").removeClass("view_btn");
		$("#writeCard").addClass("btn_disabled");
		$("#adultWriteCard").removeClass("view_btn");
		$("#adultWriteCard").addClass("btn_disabled");
		$("#okSubmit").removeAttr("onclick");
		$("#okModule").removeClass("ok");
		$("#okModule").addClass("ok_disabled");	
		$("#pay_type_mobile").attr("disabled",true);  
		$("#pay_type_mobile option[value='"+payType+"']").attr('selected','selected'); 
		$("#b").attr("disabled",true);  
		$("#b2").attr("disabled",true);  
		$("#b3").attr("disabled",true);
		$("#end_time").attr("disabled",true);
		$("#receiveAddress").attr("readonly","readonly");
		$("#receivePhone").attr("readonly","readonly");
		$("#getFee").text("(已收费)");
		$("#sytleProgress").attr("src",application.fullPath +"images/five_long_5.png");
	};
	if(order_status=='A10'&&(payFlag=='C'||payFlag=='P')){
		//$("#readCard").removeAttr("onclick");
		$("#readCard").removeClass("view_btn");
		$("#readCard").addClass("btn_disabled");
		$("#readAdultCard").removeClass("view_btn");
		$("#readAdultCard").addClass("btn_disabled");
		//$("#readCard").attr("onclick","readCardBtn();");
		//$("#writeCard").removeAttr("onclick");
		$("#writeCard").removeClass("view_btn");
		$("#writeCard").addClass("btn_disabled");
		$("#adultWriteCard").removeClass("view_btn");
		$("#adultWriteCard").addClass("btn_disabled");
		$("#pay_type option[value='"+payType+"']").attr('selected','selected'); 
		$("#okSubmit").removeAttr("onclick");
		$("#okModule").removeClass("ok");
		$("#okModule").addClass("ok_disabled");
		$("#sytleProgress").attr("src",application.fullPath +"images/five_long_3.png");
	};
	if(order_status=='A10'&&payFlag=='Y'){
		$("#readCard").bind("click",function (){
			readCardBtn();					
		});
		$("#readAdultCard").bind("click",function (){
			readCardBtn();					
		});
		$("#writeCard").bind("click",function (){
			writeCardBtn();					
		});
		$("#btnGetFee").removeAttr("onclick");
		$("#btnGetFee").removeClass("view_btn");
		$("#btnGetFee").addClass("btn_disabled");
		$("#pay_type_mobile").attr("disabled",true); 
		$("#pay_type_mobile option[value='"+payType+"']").attr('selected','selected'); 
		$("#getFee").text("(已收费)");
		$("#okSubmit").removeAttr("onclick");
		$("#okModule").removeClass("ok");
		$("#okModule").addClass("ok_disabled");	
		$("#sytleProgress").attr("src",application.fullPath +"images/five_long_4.png");
	
	};
$("#b").bind("click",function(){
	$("#b").attr("checked",true);
	$("#indent2").hide();
	$("#indent1").hide();
	$("#b2").attr("checked",false);
	$("#b3").attr("checked",false);
});
$("#b2").bind("click",function(){
	$("#b2").attr("checked",true);
	$("#indent1").show();
	$("#indent2").hide();
	$("#b").attr("checked",false);
	$("#b3").attr("checked",false);
});
$("#b3").bind("click",function(){
	$("#b3").attr("checked",true);
	$("#indent2").show();
	$("#indent1").hide();
	$("#b").attr("checked",false);
	$("#b2").attr("checked",false);
});
$("#shenSuoDingGou").bind("click",function(){
	 var p_class = $("#shenSuoDingGou").attr("class");
	 if(p_class=="down"){
		 $("#shenSuoDingGou").attr("class", ""); 
	 }else{
		 $("#shenSuoDingGou").attr("class", "down"); 
	 }
	 $("#box1").toggle();   
	});
$("#shenSuoTiaoKuan").bind("click",function(){
	 var p_class = $("#shenSuoTiaoKuan").attr("class");
	 if(p_class=="down"){
		 $("#shenSuoTiaoKuan").attr("class", ""); 
	 }else{
		 $("#shenSuoTiaoKuan").attr("class", "down"); 
	 }
	 $("#box2").toggle();   
	});
$("#shenSuoFeiYong").bind("click",function(){
	 var p_class = $("#shenSuoFeiYong").attr("class");
	 if(p_class=="down"){
		 $("#shenSuoFeiYong").attr("class", ""); 
	 }else{
		 $("#shenSuoFeiYong").attr("class", "down"); 
	 }
	 $("#box3").toggle();   
	});
$("#shenSuoKa").bind("click",function(){
	 var p_class = $("#shenSuoKa").attr("class");
	 if(p_class=="down"){
		 $("#shenSuoKa").attr("class", ""); 
	 }else{
		 $("#shenSuoKa").attr("class", "down"); 
	 }
	 $("#box4").toggle();   
	});
			//交付方式暂时屏蔽
//$("#shenSuoZhiFu").bind("click",function(){
//	 var p_class = $("#shenSuoZhiFu").attr("class");
//	 if(p_class=="down"){
//		 $("#shenSuoZhiFu").attr("class", ""); 
//	 }else{
//		 $("#shenSuoZhiFu").attr("class", "down"); 
//	 }
//	 $("#box5").toggle();   
//	});
$("#shenSuoDinner").bind("click",function(){
	 var p_class = $("#shenSuoDinner").attr("class");
	 if(p_class=="down"){
		 $("#shenSuoDinner").attr("class", ""); 
	 }else{
		 $("#shenSuoDinner").attr("class", "down"); 
	 }
	 $("#box6").toggle();   
	});
$("#shenSuoAc").bind("click",function(){
	 var p_class = $("#shenSuoAc").attr("class");
	 if(p_class=="down"){
		 $("#shenSuoAc").attr("class", ""); 
	 }else{
		 $("#shenSuoAc").attr("class", "down"); 
	 }
	 $("#box7").toggle();   
	});
/*<!--YUN-744   NX_代理商开户/缴费佣金功能 -->*/
$("#shenSuoCommission").bind("click",function(){
	 var p_class = $("#shenSuoCommission").attr("class");
	 if(p_class=="down"){
		 $("#shenSuoCommission").attr("class", ""); 
	 }else{
		 $("#shenSuoCommission").attr("class", "down"); 
	 }
	 $("#boxCommission").toggle();   
	});
	
if($("#tele_type").val()=='2G'||$("#tele_type").val()=='3G'){
	$("#backMKt").attr("checked",false);//去掉选择 
	$("#mkt").attr("checked",false);//去掉选择 
	$("#backTag").attr("style","display:none"); //隐藏
	$("#mktTag").attr("style","display:none"); //隐藏
	$("#backMKtS").css('visibility','hidden');
	$("#mktS").css('visibility','hidden');
	$("#backMKtS").val("");//清空选项
	$("#mktS").val("");//清空选项			
	 $("#doAc").attr("style","display:none");
	}
	$("#alipayrefunda").click(function(){
		var order_id = $("#order_id").val();
		alipayRefund('20160330100000026547');
	});
	//白卡、成卡开户切换
	$("input[name='cardRadio']").click(function(){
		if( $(this).attr("id")=="adultCard" ){
			$("#adultCardDiv").show();
			$("#whiteCardDiv").hide();
		}
		else{
			$("#adultCardDiv").hide();
			$("#whiteCardDiv").show();
		}
	});
	//成卡开户按钮
	$("#adultWriteCard").click(function(){
		writeAdultCar();
	});
	
	//支付宝订单号是否显示
    if(province_code=="gx"){
    	if(pay_type=='支付宝扫码支付'&& $("#getFee").text()=='(已收费)'){
    		$("#payCsId").show();
    	}
    }
    
    $("#reSign").click(function(){
    	$("#reSign").removeClass("input_button_long");
		$("#reSign").addClass("input_button_long_disabled");		
	});
    
    if(pdf_resign_time > 15){
    	$("#reSign").removeClass("input_button_long");
		$("#reSign").addClass("input_button_long_disabled");	
    }else{
    	window.setTimeout(function(){
    		$("#reSign").removeClass("input_button_long");
    		$("#reSign").addClass("input_button_long_disabled");
    	}, pdf_residue_time*60000); 
    }
    
});
//读卡按钮
function readCardBtn(){
//读卡时查询PDF状态码，确认电子签名并保存（即状态码为是否000），不是则提示操作员"请确认电子签名并保存电子单!"		
	if(province_code=="nx" || province_code == "cq"){
		AfterReadCard();
	}else{
	 $.ajax({
	  		type : "POST",
	  		url : application.fullPath + "authority/dealShowOrder/pdfIsSign",
	  		data : {"order_id":$("#order_id").val(),
	  			   "template_id":$("#termsInnetFlag").val()+','+$("#termsGoodFlag").val()+','+$("#termsPreferentialFlag").val()
	  			},
	  		dataType:'json',
	  		waitMsg:"请稍等......",
	  		success:function(message){
	  			if(message.type == "error"){			//pdf状态码非000时
	  				 $.alert(message.content.substr(0,message.content.length-1));
	  				 return;
	  			}else{
	  				AfterReadCard();
	  			}
	  		 }	
	  		})
 		
	}
};

//写卡按钮
function AfterReadCard(){
	if(!load_rwcard_acx){
		    document.body.insertAdjacentHTML("beforeEnd", " \
		          <object id=\"CardReader\" style=\"display:none;\" classid=\"clsid:43E4D4FC-3CD8-459A-AAA1-698C1288DE93\" width=\"0\" height=\"0\"> \
		        </object>");

		        load_rwcard_acx = true;
		  }
		var payFlag = $("#payFlag").val();//如果是Y则代表已收费，则隐藏收费的按钮
		if(payFlag=='Y'){
		}else{
		 $.warn("先收费成功在进行卡操作")
		  return;
		}
		var writeWay=$("#writeWay").val();
		if('0'==writeWay){
		  $.alert("模拟白卡开户，请手动输入卡号，进行写卡");
		}else{
		var sim_no=getCardId();
			var cardRadio = $('input:radio[name="cardRadio"]:checked');
			if( cardRadio.attr("id")=="whiteCard"){
				$("#resourcesCode").val(sim_no);	
			}
			else{
				$("#adultCardCode").val(sim_no);
			}
		}
}

//写卡按钮
function writeCardBtn(){
	writeCardFlag ++;
	readCardBtn();
	if(writeCardFlag>1){
		$.alert("请不要重复点击写卡按钮");
		 return;
	}
	var payFlag = $("#payFlag").val();//如果是Y则代表已收费，则隐藏收费的按钮
	if(payFlag=='Y'){
	}else{
		writeCardFlag=0;
		$.alert("先收费成功在进行卡操作");
	  return;
	}
	var iccid=$("#resourcesCode").val();
	var writeWay=$("#writeWay").val();
	if(''==iccid||'请读卡'==iccid){
	   if('0'==writeWay){
		   $.alert("模拟白卡开户，请手动输入卡号，在进行写卡");
		   writeCardFlag=0;
	     return;
		}else{
			$.alert("请先读卡在进行写卡");
			writeCardFlag=0;
		 return;
	   }
	}else{
		if('0'==writeWay){
		}else{
		 var sim_no=getCardId();
		 if(sim_no==iccid){	 
		 }else{
		$.alert("此白卡与已经获取的卡号不一样，请插入之前的白卡再操作");
		writeCardFlag=0;
		 return;
		 }
		}
		
	}
	var numId=$("#acc_nbr").val();
	var prepayFlag=$("#prepayFlag").val();
	var cardType=$("#cardType").val();
	var teleType = $("#tele_type").val();//电信类型
	var GetURl= application.fullPath + "authority/checkResInfo/getCardData";
	$.ajax({
		url:GetURl,
		data:{
			"iccid":iccid,
			"numId":numId,
			"cardType":cardType,
			"userType":prepayFlag,
			"teleType":teleType,
			"orderId":$("#order_id").val()
		},
		dataType:'json',
		type:'post',
		waitMsg:"正在写卡！",
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
							"OrdersID":$("#order_id").val(),
							"wt_flag":wt_flag
							
						},
						dataType:'json',
						type:'post',
						waitMsg:"正在校验！",
						success:function(message){
							var returnData=message.args.ruturnData;
							if(returnData=="OK"){
								var order_id = $("#order_id").val();//订单号
								var iccid = $("#resourcesCode").val();//资源号
								var imsi = $("#imsi").val();
								var cardData = $("#cardData").val();
								var cardType = $("#cardType").val();
								var capacityTypeCode = $("#capacityTypeCode").val();
								var resKindCode = $("#resKindCode").val();
								var activeId = $("#activeId").val();
								var  procId = $("#procId").val();
								doAccountOpen(order_id,iccid,imsi,cardType,capacityTypeCode,resKindCode,cardData,activeId,procId,pay_type);
								writeCardFlag=0;
								if(province_code=="nx"){
									makeElectronInvoiceOpen(order_id);
								}
							}else{
								writeCardFlag=0;
								$.alert(returnData);
								//支付宝退款
								if(pay_type!=""&& pay_type=="19"){
									alipayRefund(order_id);
								}
								else if(pay_type!=""&& pay_type=="20"){
									wxpayRefund(order_id);
								}
								else{
									var a = $("#payTypePc").find("select[style='display: inline-block;']");
									pay_type = typeof(a)!="undefined"?a.val():"";
									if(pay_type!=""&&pay_type=="19"){
										alipayRefund(order_id);
									}
									else if(pay_type!=""&&pay_type=="20"){
										wxpayRefund(order_id);
									}
								}
							}
						}
					});
				}else{
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
				writeCardFlag=0;
				$.alert(returnData);
			}
		}
	});
};
//成卡写卡
function writeAdultCar(){
	var iccid=$("#adultCardCode").val();
	var teleType = $("#tele_type").val();
	if(''==iccid || '请输入成卡卡号'==iccid){
		$.alert("请先读卡或输入卡号后再进行写卡");
		return;
	}
	var NotifyURl= application.fullPath + "authority/checkResInfo/cardNotify";
	$.ajax({
		url:NotifyURl,
		data:{
			"iccid":iccid,
			"OrdersID":$("#order_id").val(),
			"teleType":teleType
		},
		dataType:'json',
		type:'post',
		waitMsg:"正在校验！",
		success:function(message){
			var returnData=message.args.ruturnData;
			if(returnData=="OK"){
				$.alert("开户成功");
			}else{
				writeCardFlag=0;
				$.alert(returnData);
				//支付宝退款
				if(pay_type!=""&& pay_type=="19"){
					alipayRefund(order_id);
				}
				//微信退款
				else if(pay_type!=""&& pay_type=="20"){
					wxpayRefund(order_id);
				}
				else{
					var a = $("#payTypePc").find("select[style='display: inline-block;']");
					pay_type = typeof(a)!="undefined"?a.val():"";
					if(pay_type!=""&&pay_type=="19"){
						alipayRefund(order_id);
					}
					else if(pay_type!=""&&pay_type=="20"){
						wxpayRefund(order_id);
					}
				}
			}
			if(province_code=="nx"){
				makeElectronInvoiceOpen(order_id);
			}
		}
	});
}



function dealFee(){
	var messageTmp="";
    var payType= "";//付费方式标标识
    posCardFlag ++;
	if(posCardFlag>1){
		$.alert("请不要重复点击收费按钮");
		 return;
	}
    if($("#tele_type").val()=='2G'){
	    payType= $("#pay_type_2G").val();
	}else if($("#tele_type").val()=='3G'){
		payType= $("#pay_type_3G").val();
	}else if($("#tele_type").val()=='4G'){
		payType= $("#pay_type_4G").val();
	}
    if(payType=='15'){   //传统银行卡接口
    	/*-----------------------调用pos刷卡操作接口开始-------------------------------------------------*/
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
					"memo":document.getElementById("memo").value,
					"wt_flag":wt_flag
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
		}else if(payType == '19'){
			alipayScan($("#order_id").val(),
					$("#discount_totalCosts_input").val(),"广西联通电子流智能营业厅-开户",$("#tele_type").val(),
	    			'19',$("#oper_no").val(),$("#acc_nbr").val());
		}else if(payType == '20'){
			wxpay($("#order_id").val(),
					$("#discount_totalCosts_input").val(),"广西联通电子流智能营业厅-开户",$("#tele_type").val(),
					payType,$("#oper_no").val(),$("#acc_nbr").val(), "gx");
		}else{//不是传统银行卡接口
			dodealFee(payType);
			posCardFlag=0;
	   }
   }

  //调用接口收费部分
  function dodealFee(payType){  
	  posCardFlag = 0;
	  //协同开户，3G的支付宝PayType要传10
	  if($("#tele_type").val()=='3G' && (wt_flag == "1" || big_agent_flag == "1") && payType == "19"){
		  payType = "10";
	  }
	  //微信4G转成15
	  if($("#tele_type").val()=='4G' && payType == "20"){
		  payType = "15";
	  }
	  
	  /*-----------------------调用付费接口开始-------------------------------------------------*/
		var data={
			"order_id":$("#order_id").val(),
			"trade_no":document.getElementById("reference").value,
			"acc_nbr":$("#acc_nbr").val(),
			"pay_type":payType,
			"order_sub_type":$("#order_sub_type").val(),
			"wt_flag":wt_flag
		};
	    //收费接口url
      var URL = application.fullPath + "authority/dealShowOrder/submitOrder"; 
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
							"order_id":$("#order_id").val(),
							"trade_no":document.getElementById("reference").value,
							"acc_nbr":$("#acc_nbr").val(),
							"pay_type":payType,
							"order_sub_type":$("#order_sub_type").val(),
							"wt_flag":wt_flag
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
								$.alert("收费成功");
								$("#btnGetFee").removeAttr("onClick");
								$("#btnGetFee").removeClass("view_btn");
								$("#btnGetFee").addClass("btn_disabled");
								$("#readCard").removeClass("btn_disabled");
								$("#readCard").addClass("view_btn");
								$("#readAdultCard").removeClass("btn_disabled");
								$("#readAdultCard").addClass("view_btn");
								//$("#readCard").attr("onclick","readCardBtn();");
								$("#writeCard").removeClass("btn_disabled");
								$("#writeCard").addClass("view_btn");
								$("#adultWriteCard").removeClass("btn_disabled");
								$("#adultWriteCard").addClass("view_btn");
								$("#readCard").bind("click",function (){
									readCardBtn();					
								});
								$("#writeCard").bind("click",function (){
									writeCardBtn();					
								});
							    $("#getFee").text("(已收费)");
							    $("#payFlag").attr("value","Y");
							    $( ".out_div" ).hide();
							  // $("html,body").animate({scrollTop: $("#box3").offset().top}, 10);
						}else{
							var desc = jsonObj.content;
				    		$.alert(desc);
						   //$("html,body").animate({scrollTop: $("#box3").offset().top}, 10);
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

				//$("html,body").animate({scrollTop: $("#box3").offset().top}, 10);
				
			}
		},
  	error:function(message){
  		var jsonObj=eval(message);
  		var desc = jsonObj.content;
  		$.alert(desc);
  		//$("html,body").animate({scrollTop: $("#box3").offset().top}, 10)
		}
    });
	  
  }
	
	//开户提交
	function doAccountOpen( order_id, iccid,imsi,cardType,capacityTypeCode,resKindCode,cardData,activeId,procId,pay_type){
		var accountOpenURL = application.fullPath + "authority/accountOpen/doAccountOpen";//开户url
		$.ajax({
			type : "post",
			url : accountOpenURL,
			waitMsg : "正在开户，请稍候！",
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
				"wt_flag":wt_flag,
				"pay_type":pay_type
			},
			dataType : 'json',
			success : function(message) {
				var respCode = message.args.RespCode;
				var RespDesc = message.args.RespDesc;
				if (respCode == '0000') {//开户成功
					$("#readCard").removeAttr("onClick");
					$("#readCard").removeClass("view_btn");
					$("#readCard").addClass("btn_disabled");
					//$("#readCard").attr("onclick","readCardBtn();");
					$("#writeCard").removeAttr("onClick");
					$("#writeCard").removeClass("view_btn");
					$("#writeCard").addClass("btn_disabled");
					//$("#okSubmit").attr("onclick","okSumbit();");
					$("#okModule").removeClass("ok_disabled"); 
					$("#okModule").addClass("ok");
					$("#okSubmit").bind("click",function (){
						okSumbit();					
					});
					 $("#readCard").unbind("click");
					 $("#writeCard").unbind("click");
					//$("#order_status").val("A30");//复制订单状态为A30竣工
					$.alert("开户成功");
					
					//$("html,body").animate({scrollTop: $("#box4").offset().top}, 10)
				} else {//开户失败
					var jsonObj=eval(message);
					var desc = jsonObj.content;
		    		$.alert(desc);
					//$("html,body").animate({scrollTop: $("#box4").offset().top}, 10)
				}
			},
			error : function(message) {//开户提交失败
				var jsonObj=eval(message);
				var desc = jsonObj.content;
	    		$.alert(desc);
				  // $("html,body").animate({scrollTop: $("#box3").offset().top}, 10);
			}
		});
	}
	function apweb(data){
		var again = "";
		again = data;
		var order_id = $("#order_id").val();//订单号
	    var apweb_url = $("#apweb_url").val();
		var good_templateid =$("#good_templateid").val();
 	   //var order_status = $("#order_status").val();//订单状态 如果订单完成了 则不能修改签名
		var preferential_templateid =$("#preferential_templateid").val();
		var local_templateid =$("#local_templateid").val();
		var pass_templateid = $("#pass_templateid").val();
		var radio_tiaokuan_info=$("input[name='radio_tiaokuan_info']:checked").val();
//		var apweb_value = $("#apweb_value").val();
//		if(apweb_value=='2'){
//			window.open(apweb_url+"form/formPdfDetail.action?folat=2&templateid="+good_templateid+"&formsn="+order_id);
//		}else if(apweb_value=='4'){
//			window.open(apweb_url+"form/formPdfDetail.action?folat=2&templateid="+preferential_templateid+"&formsn="+order_id);
//		}else{
//			window.open(apweb_url+"form/formPdfDetail.action?folat=2&templateid=1&formsn="+order_id);
//		}

		if(again == "1"){
			if(radio_tiaokuan_info=='good'){
				window.open(apweb_url+"form/formPdfDetail.action?folat=2&templateid="+good_templateid+"&formsn="+order_id);
			}else if(radio_tiaokuan_info=='preferential'){
				window.open(apweb_url+"form/formPdfDetail.action?folat=2&templateid="+preferential_templateid+"&formsn="+order_id);
			}else if(radio_tiaokuan_info=='pass_innet'){
				window.open(apweb_url+"form/formPdfDetail.action?folat=2&templateid="+pass_templateid+"&formsn="+order_id);
			}else if(radio_tiaokuan_info=='innet'){
				window.open(apweb_url+"form/formPdfDetail.action?folat=2&templateid=1&formsn="+order_id);
			}else{
				window.open(apweb_url+"form/formPdfDetail.action?folat=2&templateid="+local_templateid+"&formsn="+order_id);
//				alert("亲，没有对应的协议，联系系统管理员添加对应的协议！！！");
			}
		}
		else if (again = "2"){
			if(pdf_resign_time > 15){
		    	$("#reSign").removeClass("input_button_long");
				$("#reSign").addClass("input_button_long_disabled");	
		    }
			
			reSignBtn = $("#reSign").attr("class")
			if(reSignBtn == "input_button_long_disabled"){
				return;
			}
			var URL = application.fullPath + "authority/accountOpen/uploadFormPdfElectric";//uploadFormPdfElectric
			$.ajax({
				type : "POST",
				url : URL,
				waitMsg : "",
				data : {
					"order_id":	order_id,
					"time_limit":"15"
				},
				dataType : 'json',
				success : function(message) {
					var jsonObj=eval(message);
					if(jsonObj.type=='success'){
						if(radio_tiaokuan_info=='good'){
							window.open(apweb_url+"form/formPdfDetail.action?folat=2&templateid="+good_templateid+"&formsn="+order_id);
						}else if(radio_tiaokuan_info=='preferential'){
							window.open(apweb_url+"form/formPdfDetail.action?folat=2&templateid="+preferential_templateid+"&formsn="+order_id);
						}else if(radio_tiaokuan_info=='innet'){
							window.open(apweb_url+"form/formPdfDetail.action?folat=2&templateid=1&formsn="+order_id);
						}else if(radio_tiaokuan_info=='pass_innet'){
							window.open(apweb_url+"form/formPdfDetail.action?folat=2&templateid="+pass_templateid+"&formsn="+order_id);
						}else{
							window.open(apweb_url+"form/formPdfDetail.action?folat=2&templateid="+local_templateid+"&formsn="+order_id);
//							alert("亲，没有对应的协议，联系系统管理员添加对应的协议！！！");
						}
					}else{
						$.alert(jsonObj.content);
					}					
				}
			});
		}
		
	}
	function okSumbit(){
		
		if($("#tele_type").val()=='2G'||$("#tele_type").val()=='3G'){
			var radios = document.getElementsByName("noAc");
			 var backMKt=document.getElementsByName("backMKt");  
			 var mkt=document.getElementsByName("mkt");
			 var x99=document.getElementsByName("x99");
			 var backMKtS=$("#backMKtS").val();
			 var mktS=$("#mktS").val();
			 var x99levels =$("#x99levels").val();
			 var priceSelect =$("#x99levels").val();
			 if(!radios[0].checked&&!mkt[0].checked&&!backMKt[0].checked&&!x99[0].checked){//未选择
				 $.alert("请选择是否参与活动!");
				 return;
			 }
			 else if(mkt[0].checked&&mktS==''){//没有选择营销活动
				 $.alert("请选择营销活动!");
				 return;
			}else if(backMKt[0].checked&&backMKtS==''){
				$.alert("请选择活动退费!");
				 return;
			}else if(x99[0].checked&&(x99levels==''||priceSelect=='')){
				$.alert("请选档位和价格!");
				 return;
			}
		}
		/*对于已经竣工的单子不能修改交付方式*/
	   var order_status = $("#order_status").val();//订单状态
	   if(order_status=='A20'||order_status=='A30'){
	   }else{
        var b = document.getElementById("b").checked;
		var b2= document.getElementById("b2").checked;
		var b3 = document.getElementById("b3").checked;
		var give_type="";
		var receivePhone="";
		var receiveAddress="";
		var end_time="";
		if(b){
			give_type="1";
        }
		if(b2){
			give_type="2";
			 var receivePhone= $("#receivePhone").val();
			 var receiveAddress= $("#receiveAddress").val();
			 if(receivePhone=="请输入收货人号码"){
				 $.alert("请输入收货人号码");
				 return;
			 }
			 if(receiveAddress=="请输入收货人地址"){
				 $.alert("请输入收货人地址");
				 return;
			 }
			
		}
        if(b3){
        	give_type="3";
        	 var end_time= $("#end_time").val();
		}
        var data={
        		"order_id":$("#order_id").val(),
        		"give_type":give_type,
        		"receivePhone":receivePhone,
        		"receiveAddress":receiveAddress,
        		"end_time":end_time
        	};
        			//提交
    				 var URL = application.fullPath + "authority/dealShowOrder/dealGive"; 
 		            $.ajax({
 		        		type : "POST",
 		        		url : URL,
 		        		data : data,
 		        		async:true,
 		        		dataType:'json',
 		        		waitMsg:"请稍等......",
 		        		success:function(message){
 		        			 var content = '<div class="msgbox"><div class="serial">订单已完成</div><div class="intro">发展积分为：0.0 分<br />受理积分为：0.0 分</div><div class="msgbox_ok"><a href="###" id="okModou">确定</a></div></div>';
 		 		  			$.dialog({
 		 		  				width:400,
 		 		  				draggable:false,
 		 		  				content:content
 		 		  			});

 		 		  			$("html,body").animate({scrollTop: $("#box1").offset().top}, 10);
 		 		  			$("#okModou").bind("click",function(){
 		 		                   $(".xxDialog").remove();
 		 		                   $(".dialogOverlay").remove();
 		 		                   
 		 		                   if(order_source=="100"){
 		 		                  	 window.open(application.fullPath+"authority/index/openAccountPC","_self");
 		 		                   }else{
 		 		                  	 window.close();
 		 		                   }
 		 		  			});
 		        		}
 		            });
    		
	   }
	   
	 
	}
	
 //选择活动单选框复选框事件
 function docheckdBox(name){
	 var eles=document.getElementsByName(name);  
	 $("#doAc").attr("style","display:''");
	 $("#backMKtS").css('visibility','visible');
	 $("#mktS").css('visibility','visible');
	 $("#imei").css('visibility','visible');
	 $("#imeiBtn").css('visibility','visible');
//	 $("#x99levels").css('visibility','visible');
//	 $("#priceSelect").css('visibility','visible');
	 if(name=='mkt'){//营销活动
		 $("#noAc").attr("checked",false);//去掉单选框的选择 
		 if(eles[0].checked){//选中复选框
			  doappadAc("mktS"); //加载营销活动
		      $("#mktTag").attr("style","display:''");
		      document.getElementById("mktS").style.width ="300";
		 }else{//取消选中
			 $("#mktTag").attr("style","display:none");	   				
			 $("#mktS").val("");//清空选项
			 $("#mktS").css('visibility','hidden');
		 }
	 }else if(name=='backMKt'){//活动退费
		 $("#noAc").attr("checked",false);//去掉单选框的选择 
		 if(eles[0].checked){//选中复选框
			 doappadAc("backMKtS"); //加载退费活动数据
		     $("#backTag").attr("style","display:''");
		     document.getElementById("backMKtS").style.width ="300";

		 }else{//取消选中
			 $("#backTag").attr("style","display:none");     				
			 $("#backMKtS").val("");//清空选项
			 $("#backMKtS").css('visibility','hidden');

			
		 }
		
	 }
	 else if(name=='noAc'){//不参与单选框
			$("#backMKt").attr("checked",false);//去掉选择 
			$("#mkt").attr("checked",false);//去掉选择 
			$("#x99").attr("checked",false);//去掉选择 
			$("#backTag").attr("style","display:none"); //隐藏
			$("#mktTag").attr("style","display:none"); //隐藏
			$("#imeiTag").attr("style","display:none");
			$("#dangweiTag").attr("style","display:none");
		    $("#priceSelectTag").attr("style","display:none");
	
			$("#backMKtS").css('visibility','hidden');
			$("#mktS").css('visibility','hidden');
			$("#imei").css('visibility','hidden');
		    $("#imeiBtn").css('visibility','hidden');
		    $("#x99levels").css('visibility','hidden');
			$("#priceSelect").css('visibility','hidden');
			
			$("#backMKtS").val("");//清空选项
			$("#mktS").val("");//清空选项
			$("#imei").val("");//清空选项
			$("#x99levels").val("");//清空选项
			$("#priceSelect").val("");//清空选项
			 $("#doAc").attr("style","display:none");
	}else if(name ='x99'){//x99 3G合约
		 $("#noAc").attr("checked",false);//去掉单选框的选择 
		 if(eles[0].checked){//选中复选框
		     $("#imeiTag").attr("style","display:''");
//		     $("#dangweiTag").attr("style","display:''");
//		     $("#priceSelectTag").attr("style","display:''");
		     document.getElementById("imei").style.width ="300";
		     document.getElementById("imeiBtn").style.width ="30";
		     document.getElementById("x99levels").style.width ="300";
		     document.getElementById("priceSelect").style.width ="300";
		 }else{//取消选中
			 $("#imeiTag").attr("style","display:none");
			 $("#dangweiTag").attr("style","display:none");
			 $("#priceSelectTag").attr("style","display:none");
			 $("#imei").val("");//清空选项
			 $("#x99levels").val("");//清空选项
			 $("#priceSelect").val("");//清空选项
			 $("#imei").css('visibility','hidden');
			 $("#imeiBtn").css('visibility','hidden');
			 $("#x99levels").css('visibility','hidden');
			 $("#priceSelect").css('visibility','hidden');
			
		 }
		 
	 }
	 
 }
 //加载营销活动数据
 function doappadAc(type){
	 $("#doAc").attr("style","display:''");
	 var URL = application.fullPath + "authority/mkt/querymkt";
	 if(type=='mktS'){//营销活动
		var obj = $("#mktS");
		obj.empty();
		var data={
				"mkt_type":"code_mkt",
				"device_number":$("#acc_nbr").val(),
				"wt_flag":wt_flag
				//"device_number":"18677724565"
			};	      
		    $.ajax({
				type : "POST",
				url : URL,
				data : data,
				dataType:'json',
				waitMsg:"正在加载数据...",
				success:function(msg){
					var jsonObj=eval(msg.args);
					 obj.append("<option value=''>请选择--</option>");
					 
					if(jsonObj.code=='00000'){						 
						 
						  if(jsonObj.info!=null&&jsonObj.info!=''){
							  var datesize=jsonObj.info.mktinfoList.length;	
						    for(var i=0;i<datesize;i++){								
								obj.append("<option value='"+jsonObj.info.mktinfoList[i].code_mkt+"'>"+jsonObj.info.mktinfoList[i].code_name+"</option>");
							}
						  }
					     }else{
					    	 var desc = jsonObj.content;
					    		$.alert(desc);
					    		// $("#doAc").attr("style","display:none");
					     }
			},
	  	   error:function(message){
	    	 $.alert("加载数据异常");	
	  		 obj.append("<option value=''>无信息</option>");	   	
			}
	    });
	 }else if(type=='backMKtS'){//退费活动
		 var obj = $("#backMKtS");
		  obj.empty();
		 var data={
					"mkt_type":"back_mkt",
					"device_number":$("#acc_nbr").val(),
					"wt_flag":wt_flag
					//"device_number":"18677724565"
				};	      
			    $.ajax({
					type : "POST",
					url : URL,
					data : data,
					dataType:'json',
					waitMsg:"正在加载数据...",
					success:function(msg){
						var jsonObj=eval(msg.args);
						  obj.append("<option value=''>请选择--</option>");
						if(jsonObj.code=='00000'){						 
						
						  if(jsonObj.info!=null&&jsonObj.info!=''){
							  var datesize=jsonObj.info.mktinfoList.length;	
							  for(var i=0;i<datesize;i++){								
									obj.append("<option value='"+jsonObj.info.mktinfoList[i].code_mkt+"'>"+jsonObj.info.mktinfoList[i].code_name+"</option>");
								}
						  }
					     }else{
					    	 var desc = jsonObj.content;
					    		$.alert(desc);
					    		// $("#doAc").attr("style","display:none");
					     }
				},
		  	   error:function(message){
		  		 $.alert("加载数据异常");
		  		 obj.append("<option value=''>无信息</option>");	  	
				}
		    });
	}else if(type=='x99levels'){
		 var obj = $("#x99levels");
		  obj.empty();
		  URL = application.fullPath + "authority/mkt/queryX99levels";
		 var data={
					"para_type":"x99levels",
					"type":type
				};	      
			    $.ajax({
					type : "POST",
					url : URL,
					data : data,
					dataType:'json',
					waitMsg:"正在加载数据...",
					success:function(msg){
						var jsonObj=eval(msg.args);
//						console.info(jsonObj);
						  obj.append("<option value=''>请选择--</option>");
						if(jsonObj.code=='success'){						 
						
						  if(jsonObj.info!=null&&jsonObj.info!=''){
							  var datesize=jsonObj.info.length;	
							  for(var i=0;i<datesize;i++){								
									obj.append("<option value='"+jsonObj.info[i].para_code+"'>"+jsonObj.info[i].para_name+"</option>");
								}
						  }
					     }else{
					    	 var desc = jsonObj.content;
					    		$.alert(desc);
					     }
				},
		  	   error:function(message){
		  		 $.alert("加载数据异常");
		  		 obj.append("<option value=''>无信息</option>");	  	
				}
		    }); 
		                                                                                                                    
	 }else if(type=='priceSelect'){
		 var obj = $("#priceSelect");
		  obj.empty();
		  URL = application.fullPath + "authority/mkt/queryX99levels";
//		 var para_code = $("#x99levels").val(); 
		 var para_code =document.getElementById("x99levels").value;
		 var data={
					"para_type":"x99levels",
					"para_code":para_code,
					"type":type
				};	      
			    $.ajax({
					type : "POST",
					url : URL,
					data : data,
					dataType:'json',
					waitMsg:"正在加载数据...",
					success:function(msg){
						var jsonObj=eval(msg.args);
//						console.info(jsonObj);
						  obj.append("<option value=''>请选择--</option>");
						if(jsonObj.code=='success'){						 
						
						  if(jsonObj.info!=null&&jsonObj.info!=''){
							  var datesize=jsonObj.info.length;	
							  for(var i=0;i<datesize;i++){								
									obj.append("<option value='"+jsonObj.info[i].purch_mode+"'>"+jsonObj.info[i].purch_mode_name+"</option>");
								}
						  }
					     }else{
					    	 var desc = jsonObj.content;
					    		$.alert(desc);
					     }
				},
		  	   error:function(message){
		  		 $.alert("加载数据异常");
		  		 obj.append("<option value=''>无信息</option>");	  	
				}
		    }); 

	 }

 }
 //营销活动改变选项事件验证活动是否可做
 function change(type){
	 
	 var values=$("#"+type).val();
	 if(values!=''){
		 var URL = application.fullPath + "authority/mkt/checkmkt";
		 if(type=='mktS'){//营销活动
			var code_name_markets = $("#mktS").find("option:selected").text();//fhc
			var code_mkt_markets = $("#mktS").val().toString();//fhc
			var data={
					"mkt_type":"code_mkt",
					"device_number":$("#acc_nbr").val(),
					"code_mkt":$("#mktS").val().toString(),
					"wt_flag":wt_flag
				};	      
			    $.ajax({
					type : "POST",
					url : URL,
					data : data,
					dataType:'json',
					waitMsg:"正在校验活动...",
					success:function(msg){
						var jsonObj=eval(msg.args);
						var desc = jsonObj.content;
						if(jsonObj.code!='00000'){
							$.alert(desc);	
							$("#doAc").attr("style","display:none");
							
						 }else{
							 $("#doAc").attr("style","display:''");
							//$("#mktS").attr("disabled","disabled");//设下拉框只读 fhc
								var order_json={
										"code_mkt_markets":code_mkt_markets,	
										"code_name_markets":code_name_markets	
									}
									updateOrderAttr(order_json);//保存数据到订单属性表
						 }
				},
		  	   error:function(message){
		  		 $.alert("校验异常");
				}
		    });
		 }else if(type=='backMKtS'){//退费活动	
			 var code_name_feeback = $("#backMKtS").find("option:selected").text();//fhc
			 var code_mkt_feeback = $("#backMKtS").val().toString();//fhc
			 var data={
						"mkt_type":"back_mkt",
						"device_number":$("#acc_nbr").val(),
						"code_mkt":$("#backMKtS").val().toString(),
						"wt_flag":wt_flag
					};	      
				    $.ajax({
						type : "POST",
						url : URL,
						data : data,
						dataType:'json',
						waitMsg:"正在校验活动...",
						success:function(msg){
							//var jsonObj=eval('('+msg.args+')');
							var jsonObj = msg.args;
							var desc = jsonObj.content;
							if(jsonObj.code!='00000'){
								$.alert(desc);
								$("#doAc").attr("style","display:none");
								
						     }else{
						    	 $("#doAc").attr("style","display:''");
						    	//$("#backMKtS").attr("disabled","disabled");//设下拉框只读 fhc
									var order_json={
											"code_mkt_feeback":code_mkt_feeback,	
											"code_name_feeback":code_name_feeback	
										}
										updateOrderAttr(order_json);//保存数据到订单属性表
						     }
					},
			  	   error:function(message){
			  		 $.alert("校验异常");
					}
			    });
			 
		 }else if(type=='x99levels'){
			 var para_code =$("#"+type).val();
//			 console.info(para_code);
			 doappadAc("priceSelect")			//加载x99价格选择
		 }
	 }
 }
//处理提交营销活动选择
 function doXyAction(){
	 var mkt_typeStr="";
	 var code_mktStr=";"
	 var radios = document.getElementsByName("noAc");
	 var backMKt=document.getElementsByName("backMKt");  
	 var mkt=document.getElementsByName("mkt");
	 var x99=document.getElementsByName("x99");
	 var backMKtS=$("#backMKtS").val();
	 var mktS=$("#mktS").val();
	 var x99levels =$("#x99levels").val();
	 var priceSelect =$("#priceSelect").val();
	 if(!radios[0].checked&&!mkt[0].checked&&!backMKt[0].checked&&!x99[0].checked){//未选择
		 $.alert("请选择是否参与活动!");
	 }else if(mkt[0].checked&&mktS==''){
		 $.alert("请选择营销活动!");
	 }else if(backMKt[0].checked&&backMKtS==''){
		 $.alert("请选择退费活动!");
	 }else if(x99[0].checked&&(x99levels==''||priceSelect=='')){
		 $.alert("请选择档位和价格!");
	 }
	 else if(mkt[0].checked || backMKt[0].checked){
		 code_mktStr=backMKtS==''?"":backMKtS+",";
		 code_mktStr+=mktS==''?"":mktS;
		 mkt_typeStr=backMKtS==''?"":"back_mkt,";
		 mkt_typeStr+=mktS==''?"":"code_mkt";
		 var URL = application.fullPath + "authority/mkt/dosubmitmkt";		
			var data={
					"mkt_type":mkt_typeStr,
					"device_number":$("#acc_nbr").val(),
					"code_mkt":code_mktStr,
					"wt_flag":wt_flag
				};	      
			    $.ajax({
					type : "POST",
					url : URL,
					data : data,
					dataType:'json',
					waitMsg:"正在提交数据...",
					success:function(msg){
						var jsonObj=eval(msg.args);
						var desc = jsonObj.content;
						if(jsonObj.code=='00000'){
							
							//设置完成按钮、复选框、单选框、下拉框失效
							 
							//$("#backMKtS").attr("disabled","disabled");
							//$("#mktS").attr("disabled","disabled");
							//$("#backMKt").attr("disabled","disabled");
							//$("#mkt").attr("disabled","disabled");
							//$("#noAc").attr("disabled","disabled");
							$("#doAc").attr("style","display:none");
						 }
						$.alert(desc);	
				},
		  	   error:function(message){
		  		 $.alert("提交数据异常");
				}
		    });
		
	 }else if(x99[0].checked){
		 var acc_nbr =$("#acc_nbr").val();
		 var mobile_series =$("#imei").val();
//		 var purch_mode_code=$("#priceSelect").val();
		 var purch_mode_code = document.getElementById("priceSelect").value;
		 var URL = application.fullPath + "authority/mkt/openContract";		
			var data={
					"acc_nbr":acc_nbr,
					"mobile_series":mobile_series,
					"purch_mode_code":purch_mode_code,
					"purch_type":"1",
					"wt_flag":wt_flag
				};	      
			    $.ajax({
					type : "POST",
					url : URL,
					data : data,
					dataType:'json',
					waitMsg:"正在提交数据...",
					success:function(msg){
						var jsonObj=eval(msg.args);
						var desc = jsonObj.content;
//						console.info(msg);
//						console.info(jsonObj);
						if(jsonObj.err_code =='000'){
							var activity_name = $("#priceSelect").find("option:selected").text();
							var order_json={
								"imei":mobile_series,	
								"x99":purch_mode_code,
								"activity_name_x99":activity_name
							}
							updateOrderAttr(order_json);
							
						 }else{
							 $.alert(desc);
						 }

				},
		  	   error:function(message){
		  		 $.alert("提交数据异常");
				}
		    });
		 
	 }
 }

 function tarrifChangeOrderSubmit(order_status){
//	console.info("--in----tarrifChangeOrderSubmit");
	/*
	 * 无默认功能包时退出
	 */
	
	if(order_status != "A30"){	//	开户未成功
		$.alert("开户未成功不能增加功能包");
		return;
	}
	var dinner_service=$("#pkg_name").val();
	if(dinner_service ==null && dinner_service ==""){	//所选套餐没有默认功能包
		return;
	}
	
	 var order_id=$("#order_id").val();
	 var id_number=$("#id_number").val();
	 var product_name=$("#product_name").val();
	 var device_number=$("#acc_nbr").val();
	 var pkg_code=$("#pkg_code").val();
	 var dinner_info_json=[{
		 	 pkg_code:pkg_code,
		 	 pkg_name:dinner_service,
			 change_type:"0"
	 }];
	 var URL = application.fullPath + "authority/accountOpen/tarrifChangeOrderSubmit";		
		var data={
				"device_number":device_number,
				"order_id":order_id,
				"id_number":id_number,
				"product_name":product_name,
				"dinner_service":dinner_service,
				"dinner_info_json":JSON.stringify(dinner_info_json)
			};	      
		    $.ajax({
				type : "POST",
				url : URL,
				data : data,
				dataType:'json',
				waitMsg:"正在提交数据...",
				success:function(msg){
					var jsonObj=eval(msg.args);
					var desc = jsonObj.content;			
					if(jsonObj.rsp_code=='00000'){
//						console.info(jsonObj.rsp_desc);
						$("#dinnerConfirm").addClass("btn_disabled");
						$("#dinnerConfirm").removeAttr("onclick");
					 }
					
			},		
	  	   error:function(message){
	  		 $.alert("提交数据异常");
			}
	    });
	 
 }
 
function checkIMEI(){
	var mobile_series =$("#imei").val();
	
	if(mobile_series ==null || mobile_series ==""){
		return;
	}
	 var URL = application.fullPath +"authority/mkt/checkIMEI";
	 var data ={
		"mobile_series":mobile_series,
		"purch_type":"1"
	 }
	   $.ajax({
			type : "POST",
			url : URL,
			data : data,
			dataType:'json',
			waitMsg:"正在校验...",
			success:function(msg){
				var jsonObj=eval(msg.args);
				var desc = jsonObj.content;		
//				console.info(jsonObj.err_code);
			    if("000" == jsonObj.err_code){
			   	 $("#x99levels").css('visibility','visible');
			   	 $("#priceSelect").css('visibility','visible');
			     $("#dangweiTag").attr("style","display:''");
			     $("#priceSelectTag").attr("style","display:''");
			     doappadAc("x99levels");
			     
			    }else{
			    	 $.alert("不符合条件");
			    }	
				
				
		},		
 	   error:function(message){
 		 $.alert("校验数据异常");
		}
   });

	
}

function updateOrderAttr(order_json){
	 var order_json_string = JSON.stringify(order_json);
	 //$.alert(order_json_string);
	 var data1={	
			 	"order_id":$("#order_id").val(),
				"page_code":"0",	
				"order_json":order_json_string
			};
	 var URL = application.fullPath + "authority/accountOpen/orderInfoAttrUpdate";
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
					uploadFormPdfPreferential();
			    }
			}
					
		});		
	
}

function uploadFormPdfPreferential(){
	 var data1={	
			 	"order_id":$("#order_id").val()
			};
	 var URL = application.fullPath + "authority/accountOpen/uploadFormPdfPreferential";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"补充协议生成处理中...",
			success:function(message){					 
				if(message.type == "error"){	
					$.alert(message.content);
				}else{	
					window.open(application.fullPath+"authority/dealShowOrder/showOrder?order_id="+$("#order_id").val()+"&pcFlag=1","_self");
				}				
			}			
		});	
	
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
				$("#PayTips").text("手机支付宝扫描二维码支付");
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
					$.alert("支付成功！");
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
//按角色显示支付方式
function operPayType(tele_type){
	if( province_code=="nx" ){
		$.post(application.fullPath + "rest/oper/queryOperPay",{tele_type:tele_type,jsessionid:$("#jsessionid").val()},function(data){
			if(data!=null){
				var plist = data.args.code_list_vos;
				var seltag = $("#pay_type_"+tele_type);
				seltag.empty();
				if( plist.length>0 ){
					for (var i = 0; i < plist.length; i++) {
						if( i==0){
							seltag.append("<option value="+plist[i].pay_type+" selected='selected'>"+plist[i].pay_type_name+"</option>");
						}
						else{
							seltag.append("<option value="+plist[i].pay_type+">"+plist[i].pay_type_name+"</option>");
						}
					}
				}
			}
		},"json");
	}
}
//宁夏开户电子发票打印
function makeElectronInvoiceOpen(order_id){
	
	$.ajax({
		url:application.fullPath + "rest/allTake/makeElectronInvoiceOpen",
		data:{
		     "jsessionid": $("#jsessionid").val(),
			 "order_id":order_id,
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
				$.alert("电子发票生成失败!"+message.electinvoice);
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			$.alert("电子发票生成失败!");
		}
	});
}

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
	
	$.ajax({
		url:application.fullPath + "rest/wxpay/wxQrCode",
		data:jsonData,
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		dataType:'json',
		type:'post',
		waitMsg:"二维码生成中，请稍候...",
		success:function(message){
			if(message.type == "error"){
				if(message.content == "该订单已经支付！"){
					$.alert("支付成功！");
					dodealFee(pay_type);
				}
				else{
					$.alert(message.content);
					posCardFlag = 0;
				}
			}else{
				//将二维码输出到页面
				$("#PayTips").text("手机微信扫描二维码支付");
				var htmlStr = message.content;
				$(".pic-idPic").attr( "src", ("data:image/jpeg;base64,"+message.content));
				
				$( ".out_div" ).show();
				$(document).on('click', '#closetwocode', function() {
					$( ".out_div" ).hide();
					posCardFlag = 0;
				});
				
				//轮询支付状态，支付成功则停止
				wxsearchStatusInterval(order_id, pay_type);
			}
		},
		error:function(){
			alert("ajax error");
			posCardFlag = 0;
		}
	});
}

function wxsearchStatusInterval(order_id,pay_type){
	if($(".out_div").css('display') == "block"){
		$.ajax({
			url:application.fullPath + "rest/payCommon/searchStatus",
			data:{jsessionid:$("#jsessionid").val(),"order_id":order_id},
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			dataType:'json',
			timeout:22000,
			type:'post',
			success:function(message){
				if(message.type == "success"){
					$( ".out_div" ).hide();
					$.alert("支付成功！");
					dodealFee(pay_type);
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
	                	wxsearchStatusInterval(order_id,pay_type); // 递归调用
	                }
	            }
		});
	}
}

//微信退款
function wxpayRefund(order_id){
	$.post(application.fullPath + "rest/wxpay/wxpayRefund",{cs_order_id:order_id,jsessionid:$("#jsessionid").val()},function(data){
		if(data!=null){
			$.alert(data.content);
		}
		else{
			$.alert("无微信支付记录");
		}
	},"json");
}