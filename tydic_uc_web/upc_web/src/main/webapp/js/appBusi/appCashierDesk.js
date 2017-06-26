var pay_type = null;
var order_id = null;
var req_way = null;
$(document).ready(function () {
	
	pay_type = "30";// 主页可以直接进行缴费 所以直接将pay_type 写死
	order_id = $("#order_id").html();
	// 跳转支付页面 是通过后天转发实现的
	 req_way = "APP";
	//微信扫码支付
	$("#payWeixinQrCode").unbind("click").bind("click", function(){
		
		var id = getUrlParam("id");
		var key = getUrlParam("key");
		 
		var type = "11";
		
		window.location.href = application.fullPath + "authority/cashier/wxQrPay?id="+id+"&key="+key+"&pay_type="+type + "&req_way=" + req_way;
		
	});
	
	// 微信条形码
	$("#payWeixinBarCode").unbind("click").bind("click", function(){
		
		var id = getUrlParam("id");
		var key = getUrlParam("key");
		var type = "10";
		
		window.location.href = application.fullPath + "authority/cashier/wxScanPay?id="+id+"&key="+key+"&pay_type="+type + "&req_way=" + req_way;
		
	});
	
	//支付宝扫码支付
	$("#payZhifubaoQrCode").unbind("click").bind("click", function(){
		
		var id = getUrlParam("id");
		var key = getUrlParam("key");
		var type = "21";
		
		window.location.href = application.fullPath + "authority/cashier/aliQrPay?id="+id+"&key="+key+"&pay_type="+type + "&req_way=" + req_way;
		
	});
	
	//支付宝条形码
	$("#payZhifubaoBarCode").unbind("click").bind("click", function(){
		
		var id = getUrlParam("id");
		var key = getUrlParam("key");
		var type = "20";
		
		window.location.href = application.fullPath + "authority/cashier/aliScanPay?id="+id+"&key="+key+"&pay_type="+type + "&req_way=" + req_way;
		
	});
	//返回按钮事件
	$("#clickBack").on('click',function(){
		// 表示为做任何操作返回
		pay_type = "0";
		sendMessage("FAIL" , "0");
	});
	//现金支付
	$("#payInCash").on('click',function(){
		//var pay_type = "30";
		
		var payMoney=$("#real_fee").html();
		$.confirm('是否已收到现金'+payMoney+'元？','',cashPay,null);
		$('.xxDialog').css('margin-left', '-141px');
		
		/*$.dialog({
			ok: message('admin.dialog.ok'),
			cancel: message('admin.dialog.cancel'),
			content:'<div style="font-size:14px;font-weight:bold;">' + '是否已收到现金'+payMoney+'元？'+'</div>',
			onOk: cashPay,
			});*/
	});
	
	// ******  现金收费写订单 *******
	function cashPay(){
		
		var registClient = {
				onComplete: function (args){
					if(args.type == "success"){
						// 写订单成功需要向前端传值
						tojump(1);
					}else{
						// 写订单失败
						tojump(0);
						}
				},
				 onError: function(error){
					 
			     }
		};
		WebUtil.doPostAsync(application.fullPath + "authority/pay/cashPay",
			{
	    	 	"order_id":order_id,
	    	 	"pay_type":pay_type,
		 		"key":getUrlParam("key")
			 }, 
			 true, registClient,"正在支付");
	}
});

//跳转到支付结果提示按钮 
/**
 * falge :1 表示跳转成功页面
 * count :向前端窗口提示
 * type : 支付类型
 */
/*function toAppResrult(falge,count,type){
	//直接跳转页面
	window.location.href = "../html/appResult.html?falge="+falge+"&count="+count+"&type="+type;
}

/*
	$("#jump1,#jump2").on('click',function(){
		// 直接关闭窗口  并且需要向前端传值 
		if($(this).attr("id") == "jump1"){
			sendMessage("SUCCESS",30);
		}else{
			sendMessage("FAIL",30);
		}
	});	
	 
 // 倒计时
// 主要选择跳转时候的 界面显示
function tojump(flage){
	if(flage == "1"){// 成功时候跳转
		
		$(".pay-succ").show();
		$(".main-page").hide();
		var num1=$("#num1").text();
		jump(num1,'succ');
	}else{
		
		$(".pay-false").show();
		$(".main-page").hide();
		var num2=$("#num2").text();
		jump(num2,"false");
	}
}
//倒计时跳转
function jump(count,type) {
	var result = "";
	var pay_type = "";
	window.setTimeout(function(){
        count--;
        if(count > 0) {
        	if(type=="succ"){        		
        		$('#num1').text(count);
        		result = "SUCCESS";
        	}else if(type=="false"){
        		$('#num2').text(count);
        		result = "FAIL";
        	}
            jump(count,type);
        } else {
        	// 页面跳转时想前端发送小时 ifarme
        	//window.location.href='cashierDesk.html';
        	sendMessage(result,30);
        }
    }, 1000);
}

//向前端发送消息
function sendMessage(result , pay_type){
	
	if(window.parent){
		window.parent.postMessage({ 
	        result: result,
	        args:{
	        	"pay_type":pay_type
	        }
	    }, '*');
	}
	window.close();
}
*/


