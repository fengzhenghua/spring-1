/*
 * added by haolong
 * 独立模块：创建订单及其订单更新
 * 依赖模块：WebUtil.js 
 * 费用信息 fee_info{} 
 * 身份信息与订单关系编码 cust_seq_id*/
var ORDER_UPDATE = (function(){
	var order_update = {
			//订单更新接口
			updateOrder:{},
			//是否启用订单更新接口
			flagUpdateOrder:false,
			jsessionid:"",
			order_id:"",
			rand_id:"",
			getOrderId:function(){
				return this.order_id;
			},
			getRandId:function(){
				return this.rand_id;
			},
			//设置key,value
			setUpdateOrder:function(key,value){
				if(key&&value){
					this.updateOrder[key] = value;
				}
			},
			//直接传过来json格式
			extendUpdateOrder:function(json){
				$.extend(this.updateOrder,json);
			},
			jsonIsEmpty:function(jsonData){
				for(var i in jsonData){
					return false;
				}
				return true;
			},
			clearUpdateOrder:function(){
				this.updateOrder = {};
			},
			//传递过来json对象，直接调用订单更新接口
			doDirUpdateOrder:function(json,async,callBack,callBackParameters){
				this.extendUpdateOrder(json);
				return this.doUpdateOrder( async,callBack,callBackParameters);
			},
			//调用订单更新接口,将updateOrder保存的json格式传递过去
			doUpdateOrder:function(myasync,callBack,callBackParameters){
				var returnValue = false;
				var that = this;
				var registClient = {
						onComplete: function(message) {
							console.info("订单更新成功");
							if(message.type=="success"){
								console.info("订单更新成功");
				  			}else{
				  				$.alert(message.content);
				  				return;
				  			}							
							that.clearUpdateOrder();
							if(callBack){
								callBack(callBackParameters);
							}else{
								if(!myasync){
									console.info("设置返回值为 正!!!");
									returnValue = true;
								}
							}
						},	
						onError: function(XMLHttpRequest, textStatus,errorThrown){
							var status = XMLHttpRequest.status;//未显示
							var hint = "网络繁忙，请稍后再试!";//通用万金油提示，都懂得。                
				            var error = hint + ",后台接口报错!";
							var dialog=$.dialog({
								   title:'提示',//提示title
								   content:'更新异常:'+error,//显示的内容，支持html格式。
								   buttons:[{id:'btn_ok',text:'确定',
									   onClick:function(){					   
										   dialog.close();
									   }//点击时候回调函数
								   }]
							});
							this.failOperate();
						},
						failOperate:function(){
							if(callBack){
								callBack(false);
							}else{
								if(!async){
									returnValue = false;
								}
							}
						}
					};
				var order_json = JSON.stringify(this.updateOrder);
				console.info("订单更新入餐--");
				var post_data = {"jsessionid":this.jsessionid,	
							"order_id":this.order_id,							
							"order_json":order_json	
				};
				$.ajax({
				  		type:"POST",
				  		url:application.fullPath + "rest/orderInfo/updateOrderbyOrderJson",
				  		data:post_data,  
				  		async:myasync,
				  		waitMsg:"请稍等......",
				  		success:registClient.onComplete,				  		
				  	    error:registClient.onError				  		
				});
				//console.info(post_data);
				if(!myasync){
					return returnValue;
				}
			},
			doCreateOrder:function(postData,doCreateOrdercallback,myasync){
				ORDER_UPDATE.jsessionid = postData.jsessionid;
				var order=this;
				var registClient = {
					onComplete: function(message) {						
						if(message.type=="success"){
							console.info("订单创建成功");
							if(doCreateOrdercallback){
								doCreateOrdercallback();
							}
			  			}else{
							var dialog=$.dialog({
								   title:'提示',//提示title
								   content:'订单生成失败',//显示的内容，支持html格式。
								   buttons:[{id:'btn_ok',text:'重新生成订单',
										   onClick:function(){									   
											   dialog.close();
											   ORDER_UPDATE.doCreateOrder(postData,myasync);
										   }
											   
										}]
								});
			  				//$.alert(message.content);
			  				//return;
			  			}
						var args=message.args
						var order_info = args.order_info;	
						order.order_id = order_info.order_id;
						order.rand_id = order_info.rand_id;
						//当前订单信息
						//PV_UTIL.setJsonPageVal("order_id",order_info);						
					},
					onError: function(XMLHttpRequest, textStatus,errorThrown) {
						var status = XMLHttpRequest.status;//未显示
						var hint = "网络繁忙，请稍后再试!";//通用万金油提示，都懂得。                
			            var error = hint + ",后台接口报错!";
						var dialog=$.dialog({
							   title:'提示',//提示title
							   content:'更新异常:'+error,//显示的内容，支持html格式。
							   buttons:[{id:'btn_ok',text:'确定',
								   onClick:function(){					   
									   dialog.close();
								   }//点击时候回调函数
							   }]
						});
					},
					onException: function(status, errorInfo, hint) {
						console.info(errorInfo);
					}
				};

				$.ajax({
			  		type:"POST",
			  		url:application.fullPath + "rest/orderInfo/orderInfoSubmit",
			  		data:postData,  
			  		async:(myasync==null?true:myasync),
			  		waitMsg:"请稍等......",
			  		success:registClient.onComplete,				  		
			  	    error:registClient.onError				  		
				});
				/*WebUtil.doPost(URLS.URL_API_HOST + 'rest/orderInfo/orderInfoSubmit', {
						 jsessionid: HTML_UTLS.getParam("jsessionid"),
						 tele_type:HTML_UTLS.getParam("tele_type")==null?data.tele_type:HTML_UTLS.getParam("tele_type"),
						 order_sub_type:HTML_UTLS.getParam("order_sub_type")==null?data.order_sub_type:HTML_UTLS.getParam("order_sub_type")
				}, false,registClient);	 */
			}
		};
	//order_update.jsessionid = HTML_UTLS.getParam("jsessionid");	
return 	order_update;
})();
