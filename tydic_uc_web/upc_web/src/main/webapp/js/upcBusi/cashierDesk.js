

$(document).ready(function () {
	
	//微信条码支付
	$("#wxScanPayChoice").unbind("click").bind("click", function(){
		var id = getUrlParam("id");
		var key = getUrlParam("key");
		
		var pay_type = "10";
		
		window.location.href = application.fullPath + "authority/cashier/wxScanPay?id="+id+"&key="+key+"&pay_type="+pay_type;
	});
	
	//微信扫码支付
	$("#wxQrPayChoice").unbind("click").bind("click", function(){
		
		var id = getUrlParam("id");
		var key = getUrlParam("key");
		
		var pay_type = "11";
		
		window.location.href = application.fullPath + "authority/cashier/wxQrPay?id="+id+"&key="+key+"&pay_type="+pay_type;
		
	});
	
	//支付宝条码
	$("#payZhifubaoBarCode").unbind("click").bind("click", function(){
		
		var id = getUrlParam("id");
		var key = getUrlParam("key");
		
		var pay_type = "20";
		
		window.location.href = application.fullPath + "authority/cashier/aliScanPay?id="+id+"&key="+key+"&pay_type="+pay_type;
		
	});
	
	//支付宝条码
	$("#payZhifubaoQrCode").unbind("click").bind("click", function(){
		
		var id = getUrlParam("id");
		var key = getUrlParam("key");
		
		var pay_type = "21";
		
		window.location.href = application.fullPath + "authority/cashier/aliQrPay?id="+id+"&key="+key+"&pay_type="+pay_type;
		
	});
	
	$("#cashPay").unbind("click").bind("click",function(){
		
		var payMoney=$("#real_fee").html();
		$.confirm('是否已收到现金'+payMoney+'元？','',cashPay,null);
	});
	function cashPay(){
		var pay_type = "30";
		var registClient = {
				onComplete: function (args){
					closeWindow(args.type,pay_type,args.msg);
				},
				 onError: function(error){
			     }
		};
		WebUtil.doPostAsync(application.fullPath + "authority/pay/cashPay",
			{
	    	 	"order_id":$("#order_id").val(),
	    	 	"pay_type":pay_type,
		 		"key":getUrlParam("key")
			 }, 
			 true, registClient,"正在支付");
	}

	
	$("#order_more_btn").unbind("click").bind("click", function(){
		
		$("#goods_detail_list").html('');
		
		var registClient = {
			onComplete: function (args) {
				
				$.each(args.list, function(i, item) {
					var data = JSON.stringify(item);
					
					$("#goods_detail_list").append('<li class="list"><div class="left"><div class="left_lable">'+item.goods_name+'</div><div class="right_data">'+item.goods_price+'元</div></div></li>');
				});
				
				showdiv("goods_detail_div");
            },
            onError: function(error){
            }
	     };

	     WebUtil.doPostAsync(application.fullPath + "authority/cashier/queryGoodsDetail",
    		 {
    	 		"id":$("#order_id").val(),
    	 		"key":getUrlParam("key")
    		 }, 
    		 true, registClient, "正在查询..."
	     );
		
		
		
	});
	
});

function closeWindow(result,type,erMsg){
	if(result == "success"){
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

function showdiv(popWinId) { 
	if (!window.XMLHttpRequest) { 
		for(var i=0;i<selects.length;i++){
			if(selects[i].style.display!='none'){
				selects_display.push(selects[i]);
				selects[i].style.display = "none";
			};
		}
	}
	document.getElementById("bg_mask").style.display ='block';
	document.getElementById(popWinId).style.display ='block';

}
function hidediv(popWinId) {
	if (!window.XMLHttpRequest) { 
		for(var i=0;i<selects_display.length;i++){
			selects_display[i].style.display = "";
		}
	}
	document.getElementById("bg_mask").style.display ='none';
	document.getElementById(popWinId).style.display ='none';
}
