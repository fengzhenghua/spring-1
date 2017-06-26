/*var pay_order_id = null;
var order_id = null;
var pay_type = null;
var callingSearch = false; //是否在执行轮询操作
var timersearch = null;
var maxtimes = 40;
var curtimes = 0;
var pertime = 1000;*/
$(document).ready(function () {
	order_id = $("#order_id").val();
	pay_type = $("#pay_type").val();
	getQrCode();
	//setTimeout(createQrcode, 2000); // 测试生成二维码	
	//返回按钮事件
	$("#clickBack").on('click',function(){
		var id = getUrlParam("id");
		var key = getUrlParam("key");
		var req_way = "APP";
		window.location.href = application.fullPath + "authority/busiReq/pay?id="+id+"&key="+key + "&req_way=" + req_way;
		
		//window.history.go(-1);
	});
	//重新获取按钮
	$("#retrieve").on('click',function(){
		$("#hint2").hide();	
		$("#hint1").show();
		getQrCode();
	});
});

function getQrCode(){
	loadingQrcode();
	
	var registClient = {
			onComplete: function (args) {
				pay_order_id = args.pay_order_id;
				createQrcode(args.qrData);
	        },
	        onError: function(error){
	        	$.error("二维码获取失败，请重新刷新获取");
	        	$('.xxDialog').css('margin-left', '-141px');
	        }
	     };

	 WebUtil.doPostAsync(application.fullPath + "authority/pay/wxQrCodePay",
			 {
	    	 	"order_id":order_id,
	    	 	"pay_type":pay_type,
		 		"key":getUrlParam("key")
			 }, 
			 true, registClient, "生成二维码"
	     );
}
// 生成二维码
function createQrcode(content) {
	$('#qrCode').empty().qrcode({ 
	    width: 200, // 宽度 
	    height: 200, // 高度 
	    text: content // 二维码内容
	});
	//timer($("#expiryDate"));//倒计时失效时间
	startSearchResult();
}

// 正在加载二维码
function loadingQrcode() {
	$('#qrCode').html('<p>正在加载...</p>');
}

function startSearchResult(){
	callingSearch = false; //是否在执行轮询操作
	curtimes = 115;
	timersearch = setInterval(intervalSearch, pertime);
}
function endSearchResult(){
	clearInterval(timersearch);
	callingSearch = true;
	hideLoadingMask();
}

function intervalSearch(){
	
	curtimes--;
	if(curtimes  == 0 || curtimes < 0){
		callingSearch = true;
	}
	$("#expiryDate").html(curtimes);
	if(callingSearch){
		endSearchResult();
		$("#hint1").hide();
		$("#hint2").show();
		$.error("支付结果等待超时，请重新进行支付!");
		$('.xxDialog').css('margin-left', '-141px');
		loadingQrcode();
		//getQrCode();
		return;
	}
	
	var registClient = {
			onComplete: function (args) {
				if(args.result == "SUCCESS"){
					endSearchResult();
					tojump(1);
				}
				else if(args.result == "FAIL"){
					endSearchResult();
					errMsg = args.msg;
					tojump(0);
				}
	        },
	        onError: function(error){
	        	$.error("支付检测结果失败");
	        	$('.xxDialog').css('margin-left', '-141px');
	        }
	     };

	WebUtil.doPostAsync(application.fullPath + "authority/pay/queryPayOrder",
			 {
	    	 	"order_id":order_id,
		 		"key":getUrlParam("key"),
		 		"pay_order_id":pay_order_id
			 },
			 true, registClient
	     );
	
}

/*//倒计时失效时间
var id = null;
var wait=115;
function timer(o) {
	if (wait == 0) {
		$("#hint1").hide();
		$("#hint2").show();
		loadingQrcode();
		wait = 115;
		o.text(wait);
	} else {
		o.text(wait);
		wait--;
		id = setTimeout(function() {
			timer(o)
		},
		1000)
	}
	
	var registClient = {
			onComplete: function (args) {
				if(args.result == "SUCCESS"){
					clearTimeout(id);
					tojump(1);
				}
				else if(args.result == "FAIL"){
					clearTimeout(id);
					tojump(0);
				}
	        },
	        onError: function(error){
	        	$u.dialog_util.showTypeDialog("error","检测支付结果失败");
	        }
	     };

	WebUtil.doPostAsync(application.fullPath + "authority/pay/queryPayOrder",
			 {
	    	 	"order_id":order_id,
		 		"key":getUrlParam("key"),
		 		"pay_order_id":pay_order_id
			 },
			 true, registClient
	     );
}*/

/*//倒计时
//主要选择跳转时候的 界面显示
function tojump(flage){
	if(flage == "1"){// 成功时候跳转
		
		$(".pay-succ").show();
		$(".step1").hide();
		var num1=$("#num1").text();
		jump(num1,'succ');
	}else{
		
		$(".pay-false").show();
		$(".step1").hide();
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
        	sendMessage(result,pay_type);
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
