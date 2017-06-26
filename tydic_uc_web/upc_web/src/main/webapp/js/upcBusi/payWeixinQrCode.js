var callingSearch = false; //是否在执行轮询操作
var timersearch = null;
var pertime = 1000;		//执行间隔
var curtimes = 115;		//总次数

var pay_order_id = null;

$(document).ready(function () {
	getWxQrCode();
	
	//返回收银台
	$(".payment_a").unbind("click").bind("click", function(){
		
		var id = getUrlParam("id");
		var key = getUrlParam("key");
		
		window.location.href = application.fullPath + "authority/busiReq/pay?id="+id+"&key="+key;
		
	});
});


function getWxQrCode(){
	
	loadingQrcode();
	
	var registClient = {
		onComplete: function (args) {
			createQrcode(args.qrData);
			
			pay_order_id = args.pay_order_id;
			startSearchResult();
        },
        onError: function(error){
        }
     };

     WebUtil.doPostAsync(application.fullPath + "authority/pay/wxQrCodePay",
		 {
	 		"order_id":$("#order_id").val(),
	 		"pay_type":$("#pay_type").val(),
	 		"key":getUrlParam("key")
		 }, 
		 true, registClient, "生成二维码"
     );
}

// 生成二维码
function createQrcode(content) {
	$('#qrCode').empty().qrcode({ 
	    width: 300, // 宽度 
	    height: 300, // 高度 
	    correctLevel:0,
	    render: "table",
	    text: content // 二维码内容
	});
}

// 正在加载二维码
function loadingQrcode() {
	$('#qrCode').html('<p>正在加载...</p>');
}


function startSearchResult(){
	
	callingSearch = false; //是否在执行轮询操作
	timersearch = setInterval(intervalSearch, pertime);
}
function endSearchResult(){
	clearInterval(timersearch);
	callingSearch = true;
}

function intervalSearch(){
	
	$('#curtimes').html(curtimes);
	
	curtimes--;
	if(curtimes < 0){
		callingSearch = true;
	}
	
	if(callingSearch){
		endSearchResult();
		
		$.error("支付结果等待超时，请重新刷新页面!");
		loadingQrcode();
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

