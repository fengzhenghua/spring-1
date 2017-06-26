//var apc_rest_host="http://localhost:8080/apc_rest/";

$(document).ready(function(){
	/*var order_id="2017032902575680643717";
	updateSaleOrder();
	return;*/
	var mcht_flag = "oppo";		//微信标志
	
	var validate_data = "";
	var order_id = CACHE_UTIL.getSessionItem("order_id");
	
	var appid = "";
	
	var video_local_id = "";
	var video_server_id = "";
	var video_file_name = "";
	var video_local_file_name = "";
	
	
	var data_Json={
			"mcht_flag":mcht_flag,
			"url": window.location.href
	};
	DIALOG_UTIL.showTypeDialog("loading", "");
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoExamineServiceRest/getWXJSConfig",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:data_Json,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			//$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				var config = data.args.data;
				
				wx.config({
					beta:true,
			        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。  
			        appId: config.appId, // 必填，公众号的唯一标识  
			        timestamp: config.timestamp, // 必填，生成签名的时间戳  
			        nonceStr: config.nonceStr, // 必填，生成签名的随机串  
			        signature: config.signature,// 必填，签名，见附录1  
			        jsApiList: ['chooseVideo', 'uploadVideo'], // 必填，需要使用的JS接口列表，所有JS接口列表见附录2  
			    });
				
				appid = config.appId;
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "获取配置文件Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});

	
	
	wx.ready(function(){
		
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
					    	//DIALOG_UTIL.hideDialog("", "loading");
					        if (res.err_msg === "uploadVideo:ok") {
					        	video_server_id = res.serverId.toString();
					        	
					        	//下载好视频
					        	downloadFile(video_server_id);
					        	/*downloadFile(video_server_id, function(args){
					        		video_file_name = args.filename;
					        		video_local_file_name = args.fileLocalName;
					        		
					        		idcardlivedetectfour();
					        	});*/
					        }
					    });
					}
				});
		      
		    });
		});
	});
	
	
	$("#pop_btn_oK").bind("click", function(){
		WeixinJSBridge.call('closeWindow');
	});
	
	
	/***************************************移代码后修改的js*****************************************/
	
	/**
	 * 获取唇语数据
	 */
	function getValidate(){
		var dataJson={
				"mcht_flag":mcht_flag,
				"url": window.location.href
		};
		DIALOG_UTIL.showTypeDialog("loading", "");
		
		$.ajax({
			type: "post",
			url: apc_rest_host + "rest/oppoExamineServiceRest/getLipLanguageInfo",
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			async: true,
			data:dataJson,
			dataType: "json",
			crossDomain: true == !(document.all),
			beforeSend: function() {
				//$.loading.show("正在加载");
			},
			success: function(data) {
				if (data.respCode=="0000") {
					var data1 = data.args.validate_data;
					
					$(".open_card_number_list").html("");
					
					var strHtml = "";
					for(var i=0;i<data1.length;i++){
						strHtml += '<div class="open_card_number">'+data1.charAt(i)+'</div>';
					}
					
					$(".open_card_number_list").html(strHtml);
					
					validate_data = data.args.validate_data;
				} else {
					DIALOG_UTIL.showTypeDialog("error", data.content);
				}
			},
			error: function(data) {
				DIALOG_UTIL.showTypeDialog("error", "获取唇语数据Ajax请求失败!");
			},
			complete:function(){
				DIALOG_UTIL.hideDialog("", "loading");
			}
		});
	}
	
	/**
	 * 下载文件
	 */
	function downloadFile(server_id){
		var reg=new RegExp(":null,","g");
		var dataJson={
				"order_id"  : order_id,	
				"mcht_flag" : mcht_flag,	
				"server_id": server_id,
				"validate_data": validate_data,
				"video_server_id": video_server_id
		};
		DIALOG_UTIL.showTypeDialog("loading", "");
		
		$.ajax({
			type: "post",
			url: apc_rest_host + "rest/oppoExamineServiceRest/downloadWXFile",
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			async: true,
			data:dataJson,
			dataType: "json",
			crossDomain: true == !(document.all),
			beforeSend: function() {
				//$.loading.show("正在加载");
			},
			success: function(data) {
				/*if (data.respCode=="0000") {
					callback(data.args);
				} else {
					DIALOG_UTIL.showTypeDialog("error", data.content);
					DIALOG_UTIL.hideDialog("", "loading");
				}*/
				
				if (data.respCode=="0000") {
					updateSaleOrder();
				} else {
					var fail_times = data.args.compare_fail_times;
					//活体比对，连续比对三次失败的需要提示客户转人工激活
					if(fail_times >= 3){
						$("#pop_confirm .tip").text("激活失败：活动认证校验连续三次失败，请转人工激活方式进行卡激活。")
						updateSaleOrder();
					}else{
						DIALOG_UTIL.showTypeDialog("error", data.content);
						DIALOG_UTIL.hideDialog("", "loading");
					}
				}
			},
			error: function(data) {
				DIALOG_UTIL.showTypeDialog("error", "下载文件Ajax请求失败!");
				DIALOG_UTIL.hideDialog("", "loading");
			},
			complete:function(){
				//DIALOG_UTIL.hideDialog("", "loading");
			}
		});
	}
	
	/**
	 * 活体校验
	 */
	function idcardlivedetectfour(){
		var dataJson={
				"order_id": order_id,
				"validate_data": validate_data,
				"video" : video_local_file_name,
				"video_server_id": video_server_id,
				"video_file_name":video_file_name
		};
		DIALOG_UTIL.showTypeDialog("loading", "");
		
		$.ajax({
			type: "post",
			url: apc_rest_host + "rest/oppoExamineServiceRest/oppoLivingExamine",
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			async: true,
			data:dataJson,
			dataType: "json",
			crossDomain: true == !(document.all),
			beforeSend: function() {
				//$.loading.show("正在加载");
			},
			success: function(data) {
				if (data.respCode=="0000") {
					updateSaleOrder();
				} else {
					var fail_times = data.args.compare_fail_times;
					//活体比对，连续比对三次失败的需要提示客户转人工激活
					if(fail_times >= 3){
						$("#pop_confirm .tip").text("激活失败：活动认证校验连续三次失败，请转人工激活方式进行卡激活。")
						updateSaleOrder();
					}else{
						DIALOG_UTIL.showTypeDialog("error", data.content);
						DIALOG_UTIL.hideDialog("", "loading");
					}
				}
			},
			error: function(data) {
				DIALOG_UTIL.showTypeDialog("error", "活体校验Ajax请求失败!");
				DIALOG_UTIL.hideDialog("", "loading");
			},
			complete:function(){
				//DIALOG_UTIL.hideDialog("", "loading");
			}
		});
	}
	
	/**
	 * 更新中台订单
	 */
	function updateSaleOrder() {
		var dataJson={
			    "order_id"  : order_id
		};
		DIALOG_UTIL.showTypeDialog("loading", "");
		
		$.ajax({
			type: "post",
			url: apc_rest_host + "rest/oppoOrderServiceRest/updateOrderInfoFromUoc",
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			async: true,
			data:dataJson,
			dataType: "json",
			crossDomain: true == !(document.all),
			beforeSend: function() {
				//$.loading.show("正在加载");
			},
			success: function(data) {
				if (data.respCode=="0000") {
					if(data.args.code=="200"){
						DIALOG_UTIL.showDialog("pop_confirm", true);
					}else{
						DIALOG_UTIL.showTypeDialog("error", "更新中台订单失败！！！");
					}
				} else {
					DIALOG_UTIL.showTypeDialog("error", data.content);
				}
			},
			error: function(data) {
				DIALOG_UTIL.showTypeDialog("error", "更新中台订单Ajax请求失败!");
			},
			complete:function(){
				DIALOG_UTIL.hideDialog("", "loading");
			}
		});
	}
	
});
