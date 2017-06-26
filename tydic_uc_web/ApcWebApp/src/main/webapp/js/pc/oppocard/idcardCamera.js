$(document).ready(function(){
	
	var front_server_id = "";
	var front_local_id = "";
	var back_server_id = "";
	var back_local_id = "";
	
	var front_file_name = "";
	var back_file_name = "";
	
	var mcht_flag = "";		//微信标志
	
	/*var jsessionid = CACHE_UTIL.getSessionItem("jsessionid");
	if(jsessionid == null || jsessionid == ""){
		DIALOG_UTIL.showTypeDialog("error","数据校验异常，请返回上一步重新校验！");
		return;
	}
	var order_id = CACHE_UTIL.getSessionItem("order_id");
	if(order_id == null || order_id == ""){
		DIALOG_UTIL.showTypeDialog("error","数据校验异常，请返回上一步重新校验！");
		return;
	}*/
	
	/*DIALOG_UTIL.showTypeDialog("loading", "");*/
	var registClient = {
			onComplete: function (args) {
				var config = args.data;
				
				wx.config({  
			        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。  
			        appId: config.appId, // 必填，公众号的唯一标识  
			        timestamp: config.timestamp, // 必填，生成签名的时间戳  
			        nonceStr: config.nonceStr, // 必填，生成签名的随机串  
			        signature: config.signature,// 必填，签名，见附录1  
			        jsApiList: ['chooseImage', 'uploadImage'], // 必填，需要使用的JS接口列表，所有JS接口列表见附录2  
			    });  

			    wx.ready(function(){
			    	
			    	function cameraFront(){
			    		wx.chooseImage({  
			                count: 1,
			                sizeType: ['original', 'compressed'],//可以指定是原图还是压缩图，默认二者都有  
			                sourceType: ['camera'],//可以指定来源是相册还是相机，默认二者都有  
			                success: function(res) {  
			                    var localIds = res.localIds;//返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片  
			                    $("#front_img").attr("src", localIds);
			                    $("#front_text").text("正面重拍");
			                    front_local_id = localIds.toString();
			                },
			            });
			    	}
			    	
			    	function cameraBack(){
			    		wx.chooseImage({  
			                count: 1,
			                sizeType: ['original', 'compressed'],//可以指定是原图还是压缩图，默认二者都有  
			                sourceType: ['camera'],//可以指定来源是相册还是相机，默认二者都有  
			                success: function(res) {  
			                    var localIds = res.localIds;//返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片  
			                    $("#back_img").attr("src", localIds);
			                    $("#back_text").text("背面重拍");
			                    
			                    back_local_id = localIds.toString();
			                },
			            });
			    	}
			    	
			        $("#front_text").bind('click', cameraFront);
			        $("#front_img").bind('click', cameraFront);
			        
			        $("#back_text").bind('click', cameraBack);
			        $("#back_img").bind('click', cameraBack);
			        
			        $("#next_flow_step").bind('click', function() {
			        	
			        	
			        	/*if(front_local_id == ""){
			        		DIALOG_UTIL.showTypeDialog("error","身份证正面需要照片！");
			    			return;
			        	}*/
			        	/*if(back_local_id == ""){
			        		DIALOG_UTIL.showTypeDialog("error","身份证背面需要照片！");
			    			return;
			        	}*/
			        	
			        	//先正面图片
			        	wx.uploadImage({
			        	    localId: front_local_id, // 需要上传的图片的本地ID，由chooseImage接口获得
			        	    isShowProgressTips: 1, // 默认为1，显示进度提示
			        	    success: function (res) {
			        	    	front_server_id = res.serverId; // 返回图片的服务器端ID
			        	        
			        	    	//下载文件
			        	    	downloadFile(front_server_id, function(args){
			        	    		
			        	    		front_file_name = args.filename;
			        	    		
			        	    		//上传背面
				        	        wx.uploadImage({
				    	        	    localId: back_local_id, // 需要上传的图片的本地ID，由chooseImage接口获得
				    	        	    isShowProgressTips: 1, // 默认为1，显示进度提示
				    	        	    success: function (res) {
				    	        	    	back_server_id = res.serverId; // 返回图片的服务器端ID
				    	        	        
				    	        	    	
				    	        	    	//下载文件
						        	    	downloadFile(back_server_id, function(args){
						        	    		
						        	    		back_file_name = args.filename;
						        	    		
						        	    		//更新attr表
					    	        	    	attrUpdate();
						        	    	});
				    	        	    	
				    	        	    }
				    	        	});
			        	    		
			        	    	});
			        	    }
			        	});
			        });
			    }); 
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
	
	
	//身份证对比
	function idcardcompare(){
		/*DIALOG_UTIL.showTypeDialog("loading", "");*/
		var registClient = {
				onComplete: function (args) {
					//跳转选号
					window.location.href = "openCardActiveVideo.html";
				},
				onError: function(error) {
				},
				onException: function(status, errorInfo, hint) {
				},
				/*onFinally: function () {
					DIALOG_UTIL.hideDialog("", "loading");
				}*/
		};
		/*WebUtil.doPost(URLS.URL_API_HOST + 'rest/oppocard/idcardcompare', {
			"jsessionid": jsessionid,
			"order_id"  : order_id,	
			"front_server_id" : front_server_id

		}, true,registClient);*/
	}

	
	//保存attr
	function attrUpdate() {
		var attrdata = {
				"photo_front_server_id" : front_server_id,
				"photo_back_server_id" : back_server_id,
				"front_file_name" : front_file_name,
				"back_file_name" : back_file_name
		};
		
		/*DIALOG_UTIL.showTypeDialog("loading", "");*/
		var registClient = {
				onComplete: function (args) {
					//跳转
					window.location.href = "openCardActiveVideo.html";
				},
				onError: function(error) {
				},
				onException: function(status, errorInfo, hint) {
				},
				/*onFinally: function () {
					DIALOG_UTIL.hideDialog("", "loading");
				}*/
		};
		var reg=new RegExp(":null,","g");
		/*WebUtil.doPost(URLS.URL_API_HOST + 'rest/orderInfo/orderInfoAttrUpdate', {
			"jsessionid": jsessionid,
			"order_id"  : order_id,	
			"page_code" : "0",	
			"order_json": JSON.stringify(attrdata).replace(reg,':"",')

		}, true,registClient);*/
	};
	
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
				/*onFinally: function () {
					DIALOG_UTIL.hideDialog("", "loading");
				}*/
		};
		var reg=new RegExp(":null,","g");
		/*WebUtil.doPost(URLS.URL_API_HOST + 'rest/pay/downloadWXFile', {
			"jsessionid": jsessionid,
			"order_id"  : order_id,	
			"mcht_flag" : mcht_flag,	
			"server_id": server_id

		}, true,registClient);*/
	}
});



