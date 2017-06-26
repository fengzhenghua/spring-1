
var CUSTOMER_INFO_SEARCH=(function(){
var customer={
		customer_info:{
			next_flag:false,
			customer_name:"",
			id_number:"",
			device_number:"",
			product_name:"",
			product_detail:"",
			product_id:"",
			tele_type:"",
			guishu_city:"",
			sysType:"",
			device_guishu:""
		},
		returndata:{},

		//查询用户号码
		queryCustomerInfo:function(callback){
			var customerInfo=CUSTOMER_INFO_SEARCH.customer_info;
			var jsessionid = window.parent.application.jsessionid+"PC";
			var input_text = $("#input_phone").val().trim();
			if(input_text==""){
				$.alert("请输入号码");
				return;
			}
				$.ajax({
			  		type : "POST",
			  		url : window.parent.application.fullPath + "rest/saleOpen/getUserList",
			  		data : {
			  			 "input_text": $("#input_phone").val(),
			             "search_type":"2",				//2为变更业务
			             "jsessionid": jsessionid
			  			},
			  		//dataType:'json',
			  		//async:false,
			  		async:true,
			  		//contentType: "application/x-www-form-urlencoded; charset=utf-8",
			  		waitMsg:"请稍等......",
			  		success:function(message){
			  			if(message.type=="success"){
			  				var data=message.args.cust_info;
			  				CUSTOMER_INFO_SEARCH.returndata=data;
			  				//console.info(CUSTOMER_INFO_SEARCH.returndata);
			  				var devices_products=data[0].devices_products.split("[");
			  				if(devices_products[1]==undefined){
			  					devices_products[1]=devices_products[0];
			  				}
			  				var html=devices_products[0]
			  						+"<span class='tip_info' style=' position:absolute; width:387px; left:0px; top:26px;'>"
			  						+"<span class='bold'>"+devices_products[0]+"</span>"
			  						+"<span class='line_dashed'></span>"
			  						+"<span>"+devices_products[1].split("]")[0]+"</span>"
			  						+"</span>";
			  				$("#customer_name").html(data[0].cust_name);
			  				$("#product_name").html(html);
			  				$("#customer_phone").html(data[0].device_number);
			  				$("#available_money").html(data[0].current_balance);
			  				$("#guishudi").html(data[0].guishu_city);
			  				customerInfo.customer_name=data[0].cust_name;
			  				customerInfo.id_number=data[0].id_number;
			  				customerInfo.device_number=data[0].device_number;
			  				customerInfo.product_name=data[0].devices_products;
			  				customerInfo.product_detail=data[0].devices_products;
			  				customerInfo.product_id=data[0].product_id;
			  				customerInfo.tele_type=data[0].tele_type;
			  				customerInfo.guishu_city=data[0].guishu_city;
			  				customerInfo.guishu_city=message.args.sysType;
			  				customerInfo.device_guishu=data[0].device_guishu;
			  				customerInfo.next_flag=true;
			  				$("#next_class").attr("disabled",false);
			  				$("#serachnext").attr("disabled",false);
			  				$("#next_class").attr("class","ok");
			  				if(callback){
			  					callback();
			  				}
			  			}else{
			  				$.alert(message.content);
			  				return;
			  			}
			  		 },
			  		error:function(message){
						var dialog=$.dialog({
							   title:'提示',//提示title
							   content:'查询异常',//显示的内容，支持html格式。
							   buttons:[{id:'btn_ok',text:'确定',
								   onClick:function(){					   
									   dialog.close();
								   }//点击时候回调函数
							   }]
						});
					}
			  		});
		},
		//资费详情
		feeDetail:function(){
			var customer_info=CUSTOMER_INFO_SEARCH.customer_info;
			var device_number=customer_info.device_number;
			var device_guishu=customer_info.device_guishu;
			var content="";
			$.ajax({
		  		type : "POST",
		  		url : window.parent.application.fullPath + "rest/reason_discount/getFeeDetails",
		  		data : {
		  			device: device_number,
	                device_guishu: device_guishu
		  			},
		  		waitMsg:"请稍等......",
		  		success:function(message){
		  			if(message.type=="success"){
		  				var data=message.args.fee_details;
		  				content="当月实时话费："
		  						+data.rtcalls+"元<br>当前可用余额："
		  						+data.currentbalance+"<br>普通预存款："
		  						+data.advbalance+"元<br>当月可用分摊款："
		  						+data.availadv_balance+"元<br>当月赠款："
		  						+data.availgrant+"元<br>历史欠费："
		  						+data.historyowe+"元<br>违约金："
		  						+data.latefee+"元";
		  				var dialog=$.dialog({
		 				   title:'费用详情',
		 				   content:content,
		 				   buttons:[
		 						{id:'btn_ok',text:'确定',
		 							   onClick:function(){	
		 								   dialog.close();
		 							   }}
		 					   ]
		 				}); 
		  			}else{
		  				$.alert(message.content);
		  				return;
		  			}
		  		 },
		  		error:function(message){
					var dialog=$.dialog({
						   title:'提示',//提示title
						   content:'查询异常',//显示的内容，支持html格式。
						   buttons:[{id:'btn_ok',text:'确定',
							   onClick:function(){					   
								   dialog.close();
							   }//点击时候回调函数
						   }]
					});
				}
		  		});
		},
		//更新订单
		OrderUpdate:function(data,flag,orderId,msg,callBack){
			data=JSON.stringify(data);
			var data1={	
					"order_id":orderId,
					"page_code":flag,	
					"order_json":data
				};
			
			$.ajax({
				url:window.parent.application.fullPath + "authority/accountOpen/orderInfoAttrUpdate",
				dataType:'json',
				contentType: "application/x-www-form-urlencoded; charset=utf-8", 
				type:'post',
				data:data1,
				waitMsg:msg,
				success:function(message){					 
					if(message.type == "error"){
						callBack(false);
						$.alert(message.content);
					}else{
						callBack(true);
					}			
				}			
			});
		},
};
return customer;
})();
