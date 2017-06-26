/*var pay_type = null;
var order_id = null;
var pay_order_id = null;
var callingSearch = false; //是否在执行轮询操作
var timersearch = null;
var maxtimes = 20;
var curtimes = 0;
var pertime = 2000;	*/
$(document).ready(function () {
	pay_type = $("#pay_type").val();
	order_id = $("#order_id").val();
	//返回按钮事件
	$("#clickBack").on('click',function(){
		var id = getUrlParam("id");
		var key = getUrlParam("key");
		var req_way = "APP";
		window.location.href = application.fullPath + "authority/busiReq/pay?id="+id+"&key="+key + "&req_way=" + req_way;
	});
	//确认支付按钮
	$("#payBtn").on('click',function(){
		var auth_code = $.trim($("#authCode").val());
		if(auth_code == ""){
			$.alert("请输入条码串号!");
			$('.xxDialog').css('margin-left', '-141px');
			//$.alert("请输入条码串号!");
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
    	 		"order_id":order_id,
    	 		"auth_code":auth_code,
    	 		"pay_type":pay_type,
    	 		"key":getUrlParam("key")
    		 }, 
    		 true, registClient, "发起支付..."
	     );
	});
	
	$("#authCode").focus();
		
});

function startSearchResult(){
	callingSearch = false; //是否在执行轮询操作
	curtimes = 0;
	timersearch = setInterval(intervalSearch, barpertime);
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
		$('.xxDialog').css('margin-left', '-141px');
		
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
					errMsg=args.msg;
					tojump(0);
				}
	        },
	        onError: function(error){
	        	endSearchResult();
	        	//$.error("支付结果异常，请重新进行支付!");
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
	
/*var id = null;
var wait=20;
function residue() {
	if (wait == 0) {
		//$.error("支付结果等待超时，请重新进行支付!");
		$u.dialog_util.showTypeDialog("error","支付结果等待超时，请重新进行支付！");
		wait=20;
		return;
	} else {
		wait--;
		id = setTimeout(function() {
			residue()
		},
		1000)
	}
	
	var registClient = {
			onComplete: function (args) {
				if(args.result == "SUCCESS"){
					endSearchResult();
					tojump(1);
				}
				else if(args.result == "FAIL"){
					endSearchResult();
					tojump(0);
				}
	        },
	        onError: function(error){
	        	endSearchResult();
	        	//$.error("支付结果异常，请重新进行支付!");
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