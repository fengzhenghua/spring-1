var errMsg = "";
var pay_type = null;
var order_id = null;
var pay_order_id = null;
var callingSearch = false; //是否在执行轮询操作
var timersearch = null;
var maxtimes = 20;// 条码支付的最大等待时间
var curtimes = 0;
var pertime = 1000;	// 二维码支付 扫结果的间隔时间
var barpertime = 2000;// 条码支付扫结果的间隔时间
$(document).ready(function () {//立即跳转按钮	
	$("#jump1,#jump2").on('click',function(){
		// 直接关闭窗口  并且需要向前端传值 
		if($(this).attr("id") == "jump1"){
			sendMessage("SUCCESS");
		}else{
			sendMessage("FAIL");
		}
	});	
});
//选着显示的页面
function tojump(flage){
	if(flage == "1"){// 成功时候跳转
		
		$(".pay-succ").show();
		$(".main-page").hide();
		var num1=$("#num1").text();
		jump(num1,'succ');
	}else{
		
		$(".pay-false").show();
		$(".main-page").hide();
		$("#PayMsgText").html(errMsg);
		var num2=$("#num2").text();
		jump(num2,"false");
	}
}
var result = null;
//倒计时跳转
function jump(count,type) {
	//var pay_type = "";
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
        	sendMessage(result);
        }
    }, 1000);
}

function appDilogMsg(){
	$("#PayMsgText").html(errMsg);
	$("#appPayMsg").show();
}

function closePayMsg(){
	$("#appPayMsg").hide();
}
//向前端发送消息
	function sendMessage(result){
		
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