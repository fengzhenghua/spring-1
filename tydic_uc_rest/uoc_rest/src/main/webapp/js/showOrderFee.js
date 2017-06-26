$(document).ready(function() {	
	var payType = $("#payType").val();//支付方式
	var payFlag = $("#payFlag").val();//支付方式
    var order_status = $("#order_status").val();//订单状态
    if(payFlag=="Y"){
		$("#payTypePc").hide();
		$("#payTypeMobile").show();
	}else{
		$("#payTypeMobile").hide();
		$("#payTypePc").show();
	}
	if(order_status=='B10'){
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
		$("#sytleProgress").attr("src",application.fullPath +"images/three_long_3.png");
	};
	if(order_status=='B00'&&(payFlag=='C'||payFlag=='P')){
		$("#okSubmit").removeAttr("onclick");
		$("#okModule").removeClass("ok");
		$("#okModule").addClass("ok_disabled");
		
		$("#btnDaYin").removeClass("view_btn");
		$("#btnDaYin").addClass("btn_disabled");
		
		$("#pay_type option[value='"+payType+"']").attr('selected','selected'); 
		//$("#sytleProgress").addClass("progress_3");
		$("#sytleProgress").attr("src",application.fullPath +"images/three_long_2.png");
	};
$("#shenSuoDingGou").bind("click",function(){
	 var p_class = $("#shenSuoDingGou").attr("class");
	 if(p_class=="down"){
		 $("#shenSuoDingGou").attr("class", ""); 
	 }else{
		 $("#shenSuoDingGou").attr("class", "down"); 
	 }
	 $("#box1").toggle();   
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
});
function dealFee(){
	 var payType= $("#pay_type").val();
  var data={
			"order_id":$("#order_id").val(),
			"trade_no":document.getElementById("reference").value,
			"acc_nbr":$("#acc_nbr").val(),
			"order_sub_type":$("#order_sub_type").val(),
			"pay_type":payType
	};
   
	var ruturndate='1';
    if(payType=='10'){	
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
						ruturndate ="1";
					}else{
						ruturndate ="0";
					}
				},
			  error:function(message){
				  ruturndate ="0";
			  }
			});
	}
	 if(ruturndate=='1'){
		  var data2={
					"order_id":$("#order_id").val(),
					"trade_no":document.getElementById("reference").value,
					"acc_nbr":$("#acc_nbr").val(),
					"order_sub_type":$("#order_sub_type").val(),
					"pay_type":payType
			};
		var URl = application.fullPath + "authority/dealShowOrder/updateOrderFee";
	    $.ajax({
			type : "POST",
			url : URl,
			data : data2,
			dataType:'json',
			timeout:100000,
			waitMsg:"请稍等......",
			success:function(message){
				var jsonObj=eval(message);
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
				   $.alert("收费失败");
				   //$("html,body").animate({scrollTop: $("#box3").offset().top}, 10);
				}
			},
	    	error:function(message){
	    		
	    		$.alert("收费失败");
	    		//$("html,body").animate({scrollTop: $("#box3").offset().top}, 10);
			}
	    });
	  }else{
		  $.alert("收费失败");  
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
	