$(document).ready(function(){
	
	var mcht_flag = "oppo";		//微信标志
	
	var validate_data = "";
	/*var jsessionid = CACHE_UTIL.getSessionItem("jsessionid");
	var order_id = CACHE_UTIL.getSessionItem("order_id");*/
	
	var appid = "";
	
	var video_local_id = "";
	var video_server_id = "";
	var video_file_name = "";
	
	/*DIALOG_UTIL.showTypeDialog("loading", "");*/
	var registClient = {
		onComplete: function (args) {
			var config = args.data;
			
			/*wx.config({  
		        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。  
		        appId: config.appId, // 必填，公众号的唯一标识  
		        timestamp: config.timestamp, // 必填，生成签名的时间戳  
		        nonceStr: config.nonceStr, // 必填，生成签名的随机串  
		        signature: config.signature,// 必填，签名，见附录1  
		        jsApiList: ['chooseVideo', 'uploadVideo'], // 必填，需要使用的JS接口列表，所有JS接口列表见附录2  
		    });*/
			
			appid = config.appId;
		},
		onError: function(error) {
		},
		onException: function(status, errorInfo, hint) {
		},
		/*onFinally: function () {
			DIALOG_UTIL.hideDialog("", "loading");
		}*/
	};
	
	/*WebUtil.doPost(URLS.URL_API_HOST + "rest/pay/getWXJSConfig",{"mcht_flag":mcht_flag, "url": window.location.href}
		, true,registClient);*/

	
	
	/*wx.ready(function(){
		
		getValidate();
		
		$("#next_flow_step").bind("click", function(){
			var sourceType = "camera";
		    var camera = "front";
		    var maxDuration = 6;
		    WeixinJSBridge.invoke('chooseVideo', {
		      sourceType: sourceType,
		      maxDuration: maxDuration,
		      camera: camera
		    }, function (res) {
		      alert(JSON.stringify(res));
		      video_local_id = res.localId.toString();
		      
		      
		      DIALOG_UTIL.showTypeDialog("confirm", "视频录制结束，是否确定并上传？",function(type){
					if(type=="1"){
						DIALOG_UTIL.showTypeDialog("loading", "");
						WeixinJSBridge.invoke('uploadVideo', {
					        appId: appid,
					        isShowProgressTips: 1,
					        localId: video_local_id
					    }, function (res) {
					    	DIALOG_UTIL.hideDialog("", "loading");
					        if (res.err_msg === "uploadVideo:ok") {
					        	video_server_id = res.serverId.toString();
					        	
					        	//下载好视频
					        	downloadFile(video_server_id, function(args){
					        		video_file_name = args.filename;
					        		
					        		idcardlivedetectfour();
					        	});
					        }
					    });
					}
				});
		      
		    });
		});
	});*/
	
	
	function getValidate(){
		/*DIALOG_UTIL.showTypeDialog("loading", "");*/
		var registClient = {
			onComplete: function (args) {
				var data = args.validate_data;
				
				$(".open_card_number_list").html("");
				
				var strHtml = "";
				for(var i=0;i<data.length;i++){
					strHtml += '<div class="open_card_number">'+data.charAt(i)+'</div>';
				}
				
				$(".open_card_number_list").html(strHtml);
				
				validate_data = args.validate_data;
			},
			onError: function(error) {
			},
			onException: function(status, errorInfo, hint) {
			},
			/*onFinally: function () {
				DIALOG_UTIL.hideDialog("", "loading");
			}*/
		};
		
		/*WebUtil.doPost(URLS.URL_API_HOST + "rest/oppocard/livegetfour",{"mcht_flag":mcht_flag, "url": window.location.href}
		, true,registClient);*/
	}
	
	//下载文件
	function downloadFile(server_id, callback){
		/*DIALOG_UTIL.showTypeDialog("loading", "");*/
		var registClient = {
				onComplete: function (args) {
					callback(args);
				},
				onError: function(error) {
				},
				onException: function(status, errorInfo, hint) {
				},
				onFinally: function () {
				}
		};
		var reg=new RegExp(":null,","g");
		/*WebUtil.doPost(URLS.URL_API_HOST + 'rest/pay/downloadWXFile', {
			"jsessionid": jsessionid,
			"order_id"  : order_id,	
			"mcht_flag" : mcht_flag,	
			"server_id": server_id

		}, true,registClient);*/
	}
	
	//活体校验
	function idcardlivedetectfour(){
		/*DIALOG_UTIL.showTypeDialog("loading", "");*/
		
		var jsondata = {
			"jsessionid":jsessionid,
			"order_id": order_id,
			"validate_data": validate_data,
			"video" : video_file_name,
			"video_server_id": video_server_id
		};
		
		var registClient = {
			onComplete: function (args) {
				updateSaleOrder();
			},
			onError: function(error) {
				var fail_times = error.compare_fail_times;
				//活体比对，连续比对三次失败的需要提示客户转人工激活
				if(fail_times >= 3){
					$("#pop_confirm .tip").text("激活失败：活动认证校验连续三次失败，请转人工激活方式进行卡激活。")
					updateSaleOrder();
				}
			},
			onException: function(status, errorInfo, hint) {
			},
			/*onFinally: function () {
				DIALOG_UTIL.hideDialog("", "loading");
			}*/
		};
		
		/*WebUtil.doPost(URLS.URL_API_HOST + "rest/oppocard/idcardlivedetectfour",jsondata
		, true,registClient);*/
	}
	
	$("#pop_btn_oK").bind("click", function(){
		WeixinJSBridge.call('closeWindow');
	});
	
	//在更新中台订单
	function updateSaleOrder() {
		var registClient = {
				/*onComplete: function (args) {
					DIALOG_UTIL.showDialog("pop_confirm", true);
				},*/
				onError: function(error) {
				},
				onException: function(status, errorInfo, hint) {
				},
				onFinally: function () {
				}
		};
		/*WebUtil.doPost(URLS.URL_API_HOST + 'rest/oppocard/updateJTOrder', {
		    "jsessionid":jsessionid,
		    "order_id"  : order_id
		}
		, true,registClient);*/
	};
});