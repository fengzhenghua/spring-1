var callingSearch = false; //是否在执行轮询操作
var timersearch = null;
var maxtimes = 20;
var curtimes = 0;
var pertime = 2000;		//2秒钟执行一次
var pay_order_id = null;

$(document).ready(function () {
	
	$("#scanBtn").unbind("click").bind("click", function(){
		var auth_code = $.trim($("#authCode").val());
		if(auth_code == ""){
			$.alert("请输入条码串号!");
			$("#authCode").focus();
			return;
		}
		
		var registClient = {
			onComplete: function (args) {
				pay_order_id = args.pay_order_id;
				loadingMask("等待支付结果...");
				startSearchResult();
            },
            onError: function(error){
            }
	     };

	     WebUtil.doPostAsync(application.fullPath + "authority/pay/scanPay",
    		 {
    	 		"order_id":$("#order_id").val(),
    	 		"auth_code":auth_code,
    	 		"pay_type":$("#pay_type").val(),
    	 		"key":getUrlParam("key")
    		 }, 
    		 true, registClient, "发起支付..."
	     );
		
	});
	
	
	//返回收银台
	$(".payment_a").unbind("click").bind("click", function(){
		
		var id = getUrlParam("id");
		var key = getUrlParam("key");
		
		window.location.href = application.fullPath + "authority/busiReq/pay?id="+id+"&key="+key;
		
	});
	
	
	$("#authCode").focus();
});


function startSearchResult(){
	callingSearch = false; //是否在执行轮询操作
	curtimes = 0;
	timersearch = setInterval(intervalSearch, pertime);
}
function endSearchResult(){
	clearInterval(timersearch);
	callingSearch = true;
	
	hideLoadingMask();
}

function intervalSearch(){
	
	curtimes++;
	if(curtimes > maxtimes){
		callingSearch = true;
	}
	
	if(callingSearch){
		endSearchResult();
		
		$.error("支付结果等待超时，请重新进行支付!");
		
		return;
	}
	
	var registClient = {
		onComplete: function (args) {
/*			if(args.result == "SUCCESS"){
				endSearchResult();
				
				$.success("恭喜，支付成功!", function(){
					if(window.parent){
						window.parent.postMessage({ 
				            result: 'SUCCESS',
				            args:{
				            	"pay_type":$("#pay_type").val()
				            }
				        }, '*');
					}
					window.close();
				});
			}
			else if(args.result == "FAIL"){
				
				endSearchResult();
				
				$.error("支付失败!"+args.msg);
			}*/
		if(args.result == "FAIL"||args.result == "SUCCESS"){
			endSearchResult();
			var pay_type = $("#pay_type").val();
			closeWindow(args.result,pay_type,args.msg);
			}
        },
        onError: function(error){
        	endSearchResult();
        }
     };

     WebUtil.doPostAsync(application.fullPath + "authority/pay/queryPayOrder",
		 {
	 		"order_id":$("#order_id").val(),
	 		"key":getUrlParam("key"),
	 		"pay_order_id":pay_order_id
		 },
		 true, registClient
     );
}

function closeWindow(result,type,erMsg){
	if(result == "success"|| result=="SUCCESS"){
		// 写订单成功需要向前端传值
		$.success("恭喜，支付成功!", function(){
			if(window.parent){
				window.parent.postMessage({ 
		            result: 'SUCCESS',
		            args:{
		            	"pay_type":type
		            }
		        }, '*');
			}
			window.close();
		});
	}else{
		$.success("支付异常!"  + erMsg, function(){
			if(window.parent){
				window.parent.postMessage({ 
		            result: 'FAIL',
		            args:{
		            	"pay_type":type
		            }
		        }, '*');
			}
			window.close();
		});
	}
}
