var load_rwcard_acx = false;
var order_source ='';//订单来源
var writeCardFlag =0;
var posCardFlag=0;
var writedNumArray = [];
$(document).ready(function() {
	
	var payType = $("#payType").val();//支付方式
    var order_status = $("#order_status").val();//订单状态
    var end_open=$("#end_open").val();//用于判断是否点击完成按钮
	var payFlag = $("#payFlag").val();//支付方式
	var wo_type= $("#wo_type").val();
	var wo_fa_phone_number=$("#wo_fa_phone_number").val();//沃家庭旧手机号码
	var is_writecar=$("#is_writecar").val();//智慧沃家是否有写卡号码标识
	  if(wo_type== "0"||wo_type== "1"){//属于智慧沃家的
		    apnumber();//加载号码列表
		   // $("#sumbwjt").css('display','none');
		  }else if(wo_type=="3"){//沃家庭	 
			  document.getElementById("number_list_tag").style.display ='none'; //隐藏写卡列表 	 	
			 // $("#sumbwjt").removeClass("view_btn");
			//  $("#sumbwjt").addClass("btn_disabled");
			//  $("#sumbwjt").removeAttr("onclick");

		  }
	if(payFlag=="Y"){
		$("#payTypePc").hide();
		$("#payTypeMobile").show();
	}else{
		$("#payTypeMobile").hide();
		$("#payTypePc").show();
	}
	
	if(wo_type == "2"||(wo_type == "3"&&wo_fa_phone_number!=null&&wo_fa_phone_number!="")||is_writecar=="0"||wo_type == "4"){//单宽或者沃家庭旧手机号码或者智慧沃家无写卡号码或沃TV  隐藏写卡部分
		$("#box4").hide();
		$("#usim_title").hide();
		
	}
		
	$("#okSubmit").removeAttr("onclick");
	$("#okModule").removeClass("ok");
	$("#okModule").addClass("ok_disabled");	
	 if(wo_type == "2"||(wo_type == "3"&&wo_fa_phone_number!=null&&wo_fa_phone_number!="")||is_writecar=="0"||wo_type == "4"){
		
			if(order_status=='A10'&&(payFlag=='C'||payFlag=='P'||payFlag=='Y')){
				
				$("#pay_type option[value='"+payType+"']").attr('selected','selected');
				$("#readCard").removeClass("view_btn");
				$("#readCard").addClass("btn_disabled");
				//$("#readCard").attr("onclick","readCardBtn();");
				//$("#writeCard").removeAttr("onclick");
				$("#writeCard").removeClass("view_btn");
				$("#writeCard").addClass("btn_disabled");
				$("#okSubmit").removeAttr("onclick");
				$("#okModule").removeClass("ok");
				$("#okModule").addClass("ok_disabled");
				$("#sytleProgress").attr("src",application.fullPath +"images/four_long_2.jpg");
			};
			if(order_status=='A30'&&payFlag=='Y'){
				$("#readCard").removeClass("view_btn");
				$("#readCard").addClass("btn_disabled");
				$("#readCard").removeAttr("onclick");
				$("#writeCard").removeAttr("onclick");
				$("#writeCard").removeClass("view_btn");
				$("#writeCard").addClass("btn_disabled");
				$("#btnGetFee").removeAttr("onclick");
				$("#btnGetFee").removeClass("view_btn");
				$("#btnGetFee").addClass("btn_disabled");
				$("#pay_type_mobile").attr("disabled",true); 
				$("#pay_type_mobile option[value='"+payType+"']").attr('selected','selected'); 
				$("#getFee").text("(已收费)");
				if(end_open!='1'){//没点击完成按钮
					$("#okModule").removeClass("ok_disabled"); 
					$("#okModule").addClass("ok");
					$("#okSubmit").bind("click",function (){
						okSumbit();					
					});
					
				}else if(end_open=='1'){//已点击完成按钮	
					$("#okSubmit").removeAttr("onclick");
					$("#okModule").removeClass("ok");
					$("#okModule").addClass("ok_disabled");	
				  
		         }
				  $("#sytleProgress").attr("src",application.fullPath +"images/four_long_3.jpg");
			};
	 }else{
		  if(order_status=='A10'&&payFlag=='Y'){
			$("#btnGetFee").removeAttr("onclick");
			$("#btnGetFee").removeClass("view_btn");
			$("#btnGetFee").addClass("btn_disabled");	
			$("#readCard").removeClass("btn_disabled");
			$("#readCard").addClass("view_btn");
			$("#writeCard").removeClass("btn_disabled");
			$("#writeCard").addClass("view_btn");
			$("#readCard").bind("click",function (){
				readCardBtn();					
			});
			$("#writeCard").bind("click",function (){
				writeCardBtn();					
			});
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
			$("#sytleProgress").attr("src",application.fullPath +"images/five_long_3.png");
		};
		if(order_status=='A10'&&(payFlag=='C'||payFlag=='P')){
			
			$("#pay_type option[value='"+payType+"']").attr('selected','selected');
			$("#readCard").removeClass("view_btn");
			$("#readCard").addClass("btn_disabled");
			//$("#readCard").attr("onclick","readCardBtn();");
			//$("#writeCard").removeAttr("onclick");
			$("#writeCard").removeClass("view_btn");
			$("#writeCard").addClass("btn_disabled");
			$("#okSubmit").removeAttr("onclick");
			$("#okModule").removeClass("ok");
			$("#okModule").addClass("ok_disabled");
			$("#sytleProgress").attr("src",application.fullPath +"images/five_long_2.png");
		};
		if(order_status=='A30'&&payFlag=='Y'){
			$("#readCard").removeClass("view_btn");
			$("#readCard").addClass("btn_disabled");
			$("#readCard").removeAttr("onclick");
			$("#writeCard").removeAttr("onclick");
			$("#writeCard").removeClass("view_btn");
			$("#writeCard").addClass("btn_disabled");
			$("#btnGetFee").removeAttr("onclick");
			$("#btnGetFee").removeClass("view_btn");
			$("#btnGetFee").addClass("btn_disabled");
			$("#pay_type_mobile").attr("disabled",true); 
			$("#pay_type_mobile option[value='"+payType+"']").attr('selected','selected'); 
			$("#getFee").text("(已收费)");
			if(end_open!='1'){//没点击完成按钮
				if(wo_type=="3"){//沃家庭
					var dialog=$.dialog({
						   title:'提示',//提示title
						   content:'请组沃家庭',
						   closed: false,
						   cache: false,
						   buttons:[{id:'btn_ok',text:'确定',
								   onClick:function(){									   
									   dialog.close();
									   sumbwjt();
								   }								   
								}]
						});
					
				 }
				$("#okModule").removeClass("ok_disabled"); 
				$("#okModule").addClass("ok");
				$("#okSubmit").bind("click",function (){
					okSumbit();					
				});
				
			}else if(end_open=='1'){//已点击完成按钮	
				$("#okSubmit").removeAttr("onclick");
				$("#okModule").removeClass("ok");
				$("#okModule").addClass("ok_disabled");	
				/*if(wo_type=="3"){//沃家庭
					$("#sumbwjt").removeClass("view_btn");
					$("#sumbwjt").addClass("btn_disabled");
					$("#sumbwjt").removeAttr("onclick");
				}*/
	         }
			  $("#sytleProgress").attr("src",application.fullPath +"images/five_long_4.png");
		};
 }
	 var writedList = $("#wo_writed_number").val();
	 writedList=eval(writedList);
	 if(writedList.length > 0){
	 	for(var i = 0;i < writedList.length;i++){
	 		var numId = "";
	 		var type=$("#wo_type").val();
	 		if(type=='3'){//沃家庭
	 			numId=$("#acc_nbr").val();		
	 		}else{//智慧沃家
	 			numId=$("#number_list").val();		
	 		}
	 		if(writedList[i].acc == numId){
	 			$("#readCard").removeClass("view_btn");
	 			$("#writeCard").removeClass("view_btn");
	 			$("#readCard").addClass("btn_disabled");								
	 			$("#writeCard").addClass("btn_disabled");
	 		}
	 	}
	 }
	
$("#number_list").bind("click",function(){						
	writedNum();	 
});
			 
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
$("#shenSuoZhiFu").bind("click",function(){
	 var p_class = $("#shenSuoZhiFu").attr("class");
	 if(p_class=="down"){
		 $("#shenSuoZhiFu").attr("class", ""); 
	 }else{
		 $("#shenSuoZhiFu").attr("class", "down"); 
	 }
	 $("#box5").toggle();   
	});
$("#shenSuoAc").bind("click",function(){
	 var p_class = $("#shenSuoAc").attr("class");
	 if(p_class=="down"){
		 $("#shenSuoAc").attr("class", ""); 
	 }else{
		 $("#shenSuoAc").attr("class", "down"); 
	 }
	 $("#box6").toggle();   
	});

});

//置灰读写卡按钮
function writedNum(){
	var numId = "";
	var type=$("#wo_type").val();
	if(type=='3'){//沃家庭
		numId=$("#acc_nbr").val();		
	}else{//智慧沃家
		numId=$("#number_list").val();		
	}

	if(writedNumArray.length != 0){
		for(var i = 0 ;i < writedNumArray.length;i++){
			if(numId == writedNumArray[i]){
				$("#readCard").removeClass("view_btn");
				$("#writeCard").removeClass("view_btn");
				$("#readCard").addClass("btn_disabled");								
				$("#writeCard").addClass("btn_disabled");
			}
		}
	}
}

//读卡按钮
function readCardBtn(){
	var province_code = $("#province_code").val();
	 var wojtype=$("#wo_type").val();
//读卡时查询PDF状态码，确认电子签名并保存（即状态码为是否000），不是则提示操作员"请确认电子签名并保存电子单!"	
	if(province_code=="nx" || province_code=="cq"||wojtype=="3"){
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
		  $("#resourcesCode").val(sim_no);	
		}
}

//写卡按钮
function writeCardBtn(){
			
	writeCardFlag ++;
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
	
	var numId="";
	 var type=$("#wo_type").val();
	if(type=='3'){//沃家庭
		numId=$("#acc_nbr").val().toString();
		
	}else{//智慧沃家
		numId=$("#number_list").val().toString();
	}
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
			"teleType":teleType
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
					var num=[];
                    writedNumArray.push(numId);
                    for(var i=0;i<writedNumArray.length;i++){
                           var numObj = {
                                         "acc":writedNumArray[i]
                                 };
                          num.push(numObj);
                   }      
                    var jsonObj = {
                                  "number":num
                   }
                    var order_json = {
                                  "writedNum":JSON.stringify(jsonObj.number)
                   };     
                    updateOrderAttr(order_json);
                    $( "#readCard").removeClass("view_btn" );
                    $( "#writeCard").removeClass("view_btn" );
                    $( "#readCard").addClass("btn_disabled" );                                                      
                    $( "#writeCard").addClass("btn_disabled" );
                    $.alert( "写卡成功！" );

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
							"teleType":teleType
							
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
								doAccountOpen(order_id,iccid,imsi,cardType,capacityTypeCode,resKindCode,cardData,activeId,procId,numId);
								writeCardFlag=0;
							}else{
								writeCardFlag=0;
								$.alert(returnData);
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
						"teleType":teleType
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

function dealFee(){
	var messageTmp="";
    var payType=$("#pay_type_LAN").val();//付费方式标标识
   // var type=$("#wo_type").val();
	  if(payType!='10'){
		  $.alert("目前只支持现金收费!");
			 return;  
	  }
    posCardFlag ++;
	if(posCardFlag>1){
		$.alert("请不要重复点击收费按钮");
		 return;
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
	  /*-----------------------调用付费接口开始-------------------------------------------------*/
		var data={
			"order_id":$("#order_id").val(),
			"trade_no":document.getElementById("reference").value,
			"acc_nbr":$("#acc_nbr").val(),
			"payType":payType,
			"order_sub_type":$("#order_sub_type").val()
		};
	    //收费接口url
	  var URL="";
	  var type=$("#wo_type").val();
		  if(type=='2'){//宽带单装
	        URL =application.fullPath + "authority/accountOpen/orderSubpc";//宽带单装开户url
		  }else if(type=='3'){//沃家庭  手机单卡收费
			var fa_phone_number=$("#wo_fa_phone_number").val();//沃家庭旧手机号码
			var wo_fa_lan_number=$("#wo_fa_lan_number").val();//沃家庭旧宽带业务号码
			
			if(fa_phone_number!=null&&fa_phone_number!=""){//旧手机号码直接沃家庭提交
				if(wo_fa_lan_number!=null&&wo_fa_lan_number!=""){//同装
					 URL =application.fullPath + "authority/wjt/sumbWoOldJiaTing"; 
				}else{//新装
				    URL =application.fullPath + "authority/wjt/sumbWoJt"; 
				}
			}else{//新手机号码收单卡费用
			   URL =application.fullPath + "authority/dealShowOrder/submitWjtOrder"; 
			}
		  }else if(type=='4'){//沃TV
			  var wo_fa_lan_number=$("#wo_fa_lan_number").val();//沃家庭旧宽带业务号码				
			  if(wo_fa_lan_number!=null&&wo_fa_lan_number!=""){//同装
					 URL =application.fullPath + "authority/wjt/sumbWoOldJiaTing"; 
				}else{//新装
				    URL =application.fullPath + "authority/wjt/sumbWoJt"; 
				}
		  }else{//智慧沃家
			  URL =application.fullPath + "authority/accountOpen/doLanAccountOpen";//智慧沃家开户url  
		  }
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
							"order_sub_type":$("#order_sub_type").val()					
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
								//$("#readCard").attr("onclick","readCardBtn();");
								$("#writeCard").removeClass("btn_disabled");
								//$("#writeCard").attr("onclick","writeCardBtn();");
								$("#writeCard").addClass("view_btn");
								$("#readCard").bind("click",function (){
									readCardBtn();					
								});
								$("#writeCard").bind("click",function (){
									writeCardBtn();					
								});
							    $("#getFee").text("(已收费)");
							    $("#payFlag").attr("value","Y");
							    var type= $("#wo_type").val();
							    if(type=='2'||(type=='3'&&fa_phone_number!=null&&fa_phone_number!="")){//宽带单装、沃家庭旧手机号码
								    $("#okModule").removeClass("ok_disabled"); 
									$("#okModule").addClass("ok");
									$("#okSubmit").bind("click",function (){
										okSumbit();					
									});
							    }
								
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
	
	//开户卡数据同步提交
	function doAccountOpen( order_id, iccid,imsi,cardType,capacityTypeCode,resKindCode,cardData,activeId,procId,numId){
		var type=$("#wo_type").val();
		var accountOpenURL ="";
		var data=null;
		if(type=='3'){//沃家庭 走单卡开户
			accountOpenURL = application.fullPath + "authority/accountOpen/doAccountOpen";//开户url
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
					"procId":procId				
				},
				dataType : 'json',
				success:function(message){
					var respCode = message.args.RespCode;
					var RespDesc = message.args.RespDesc;
					if (respCode == '0000') {//写卡完成部分开户成功		
						$("#readCard").removeAttr("onClick");
						$("#readCard").removeClass("view_btn");
						$("#readCard").addClass("btn_disabled");
						
						$("#writeCard").removeAttr("onClick");
						$("#writeCard").removeClass("view_btn");
						$("#writeCard").addClass("btn_disabled");
						var dialog=$.dialog({
							   title:'提示',//提示title
							   content:'写卡成功!请组沃家庭',
							   closed: false,
							   cache: false,
							   buttons:[{id:'btn_ok',text:'确定',
									   onClick:function(){									   
										   dialog.close();
										   sumbwjt();
									   }								   
									}]
							});						
						/*$("#sumbwjt").removeClass("btn_disabled");
						$("#sumbwjt").addClass("view_btn");
						$("#sumbwjt").bind("click",function (){
							sumbwjt();					
						});*/
					
										
					 }else {//开户失败
						var jsonObj=eval(message);
						var desc = jsonObj.content;
			    		$.alert(desc);
						//$("html,body").animate({scrollTop: $("#box4").offset().top}, 10)
					}
				},
				error : function(message) {//开户提交失败
					var jsonObj=eval(message);
					var desc = jsonObj.content;
				    if (typeof(desc) == "undefined") { 
				    	$("#readCard").removeAttr("onClick");
						$("#readCard").removeClass("view_btn");
						$("#readCard").addClass("btn_disabled");
						
						$("#writeCard").removeAttr("onClick");
						$("#writeCard").removeClass("view_btn");
						$("#writeCard").addClass("btn_disabled");
				    	var dialog=$.dialog({
				 		   title:'提示',//提示title
				 		   content:'写卡成功!请组沃家庭',
				 		   closed: false,
				 		   cache: false,
				 		   buttons:[{id:'btn_ok',text:'确定',
				 				   onClick:function(){									   
				 					   dialog.close();
				 					   sumbwjt();
				 				   }								   
				 				}]
				 		});
				 	
						/*$("#sumbwjt").removeClass("btn_disabled");
						$("#sumbwjt").addClass("view_btn");
						$("#sumbwjt").bind("click",function (){
							sumbwjt();					
						});*/
					}else{
						$.alert(desc);	
					}
		    		
					 
				}
			});
		}else{//智慧沃家
		  accountOpenURL = application.fullPath + "authority/accountOpen/doWoAccountOpen";//开户url
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
					"acc_nbr":numId
				},
				dataType : 'json',
				success : function(message) {
					var respCode = message.args.RespCode;
					var RespDesc = message.args.RespDesc;
					if (respCode == '0000') {//写卡完成部分开户成功
						
						$.alert("开户成功");
																
					 }else  if(respCode == '1111'){//所有卡写卡 完成标记
						
						$("#readCard").removeAttr("onClick");
						$("#readCard").removeClass("view_btn");
						$("#readCard").addClass("btn_disabled");
						
						$("#writeCard").removeAttr("onClick");
						$("#writeCard").removeClass("view_btn");
						$("#writeCard").addClass("btn_disabled");
						
						$("#okModule").removeClass("ok_disabled"); 
						$("#okModule").addClass("ok");
						$("#okSubmit").bind("click",function (){
							okSumbit();					
						});					
						$.alert("开户成功");
					}else {//开户失败
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
	
	}
	function apweb(){
		var order_id = $("#order_id").val();//订单号
	    var apweb_url = $("#apweb_url").val();
		var good_templateid =$("#good_templateid").val();
 	   //var order_status = $("#order_status").val();//订单状态 如果订单完成了 则不能修改签名
		var preferential_templateid =$("#preferential_templateid").val();
	
		var radio_tiaokuan_info=$("input[name='radio_tiaokuan_info']:checked").val();
//		var apweb_value = $("#apweb_value").val();
//		if(apweb_value=='2'){
//			window.open(apweb_url+"form/formPdfDetail.action?folat=2&templateid="+good_templateid+"&formsn="+order_id);
//		}else if(apweb_value=='4'){
//			window.open(apweb_url+"form/formPdfDetail.action?folat=2&templateid="+preferential_templateid+"&formsn="+order_id);
//		}else{
//			window.open(apweb_url+"form/formPdfDetail.action?folat=2&templateid=1&formsn="+order_id);
//		}

		if(radio_tiaokuan_info=='good'){
			window.open(apweb_url+"form/formPdfDetail.action?folat=2&templateid="+good_templateid+"&formsn="+order_id);
		}else if(radio_tiaokuan_info=='preferential'){
			window.open(apweb_url+"form/formPdfDetail.action?folat=2&templateid="+preferential_templateid+"&formsn="+order_id);
		}else if(radio_tiaokuan_info=='innet'){
			window.open(apweb_url+"form/formPdfDetail.action?folat=2&templateid=1&formsn="+order_id);
		}else{
			alert("亲，没有对应的协议，联系系统管理员添加对应的协议！！！");
		}
	}
	function okSumbit(){
				
		/*对于已经竣工的单子不能修改交付方式*/
	   var order_status = $("#order_status").val();//订单状态
	   if(end_open=='1'&&order_status=='A30'){//已点击完成按钮
		   $.alert("订单已经完成!");
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
        		"end_time":end_time,
        		"end_open":"1"
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
 		        			 var content = '<div class="msgbox"><div class="serial">订单开户完成</div><div class="msgbox_ok"><a href="###" id="okModou">确定</a></div></div>';
 		 		  			$.dialog({
 		 		  				width:400,
 		 		  				draggable:false,
 		 		  				content:content
 		 		  			});

 		 		  			$("html,body").animate({scrollTop: $("#box1").offset().top}, 10);
 		 		  			$("#okModou").bind("click",function(){
 		 		                   $(".xxDialog").remove();
 		 		                   $(".dialogOverlay").remove();
 		 		                   
 		 		                  
 		 		  			});
 		 		  		$("#okSubmit").removeAttr("onclick");
 		 				$("#okModule").removeClass("ok");
 		 				$("#okModule").addClass("ok_disabled");	
 		        		}
 		            });
    		
	}
	   
	 
	}
	function apnumber(){
		var obj = $("#number_list");		
		obj.empty();
		var jsonObj=$('#write_nubmer').val();
		jsonObj=eval(jsonObj);
	    for(var i=0;i<jsonObj.length;i++){
	    	if (jsonObj[i].installMode == '0') { // 0:新装；1:纳入；2:签转
	    		obj.append("<option value='"+jsonObj[i].acc_nbr+"'>"+jsonObj[i].acc_nbr+"</option>");
	    	}
		}
	}
	
	  //沃家庭组合接口
	  function sumbwjt(){  
		  var wo_fa_lan_number=$("#wo_fa_lan_number").val();//沃家庭旧宽带业务号码	
		  var fa_phone_number=$("#wo_fa_phone_number").val();//沃家庭旧手机号码
			var data={
				"order_id":$("#order_id").val(),
				"trade_no":document.getElementById("reference").value,
				"acc_nbr":$("#acc_nbr").val(),
				"order_sub_type":$("#order_sub_type").val()
			};
		    //收费接口url
		  var URL="";		 		
			if(wo_fa_lan_number!=null&&wo_fa_lan_number!=""){//同装
				 URL =application.fullPath + "authority/wjt/sumbWoOldJiaTing"; 
			}else{//新装
			     URL =application.fullPath + "authority/wjt/sumbWoJt"; 
			}
		
		    $.ajax({
				type : "POST",
				url : URL,
				data : data,
				dataType:'json',
				waitMsg:"请稍等......",
				success:function(message){
					var jsonObj=eval(message);
				if(jsonObj.type=='success'){						    							
					  $.alert("提交成功");					 					
				  if(fa_phone_number!=null&&fa_phone_number!=""){//旧手机号码
				    	$("#getFee").text("(已收费)");
						$("#payFlag").attr("value","Y");
					   
				    }
				   $("#okModule").removeClass("ok_disabled"); 
					$("#okModule").addClass("ok");
					$("#okSubmit").bind("click",function (){
						okSumbit();					
					});			
					uploadFormPdf();
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
	  
	  //生成PDF
	  function uploadFormPdf(){
			 var data1={	
						"order_id":$("#order_id").val()
					};
			 var URL = application.fullPath + "authority/accountOpen/uploadFormPdf";
				$.ajax({
					url:URL,
					dataType:'json',
					contentType: "application/x-www-form-urlencoded; charset=utf-8", 
					type:'post',
					data:data1,
					waitMsg:"免填单生成处理中...",
					success:function(message){					 
						if(message.type == "error"){	
							$.alert(message.content);
						}else{	
							$.alert("免填单生成失败");
							return;
						}				
					}			
				});	
		}
	  
	  function updateOrderAttr(order_json){
			 var order_json_string = JSON.stringify(order_json);			
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
						}
					}
							
				});		
			
		}
