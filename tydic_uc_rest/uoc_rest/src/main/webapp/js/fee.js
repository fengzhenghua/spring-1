var COST_CHANGE=(function(){
	var jsessionid = window.parent.application.jsessionid+"PC";
	var tele_type=CUSTOMER_INFO_SEARCH.customer_info.tele_type;
	var certNum=CUSTOMER_INFO_SEARCH.customer_info.id_number;
	var serialNumber=CUSTOMER_INFO_SEARCH.customer_info.device_number;
	var ordersID=order_id;
	var fee_info=[];
	var essSubscribeId="";
	var fee={
		fee_info:{
			next_flag:false,
		},
		//初始化获取费用
		getFee:function(){
			var fee_all=0;
			$("#fee_list").html("");
			$.ajax({
		  		type : "POST",
		  		url : window.parent.application.fullPath + "rest/orderInfo/cardDataApply",
		  		data : {
		  			"jsessionid":jsessionid,
    	    		"ordersID":ordersID,
    	    		"tele_type":tele_type,
    	    		"serialNumber":serialNumber,    	    		    	    		
    	    		"expiryTime":"",
    	    		"cardData": "无",
    	    		"certType":"02",    	    		    	    			
    	    		"certNum":certNum, 
		  			},
		  		//dataType:'json',
		  		//async:true,
		  		//contentType: "application/x-www-form-urlencoded; charset=utf-8",
		  		waitMsg:"请稍等......",
		  		success:function(message){
		  			if(message.type=="success"){
		  				 //费用项与边框上间距
		  				 $("#fee_list").append("<div class='padding_blank10'></div>");
		  				 fee_info = message.args.fee_info;
		  				 if(tele_type=="3G"||tele_type=="4G"){
		  					essSubscribeId=message.args.fee_data.essSubscribeId;
		  				 }
		  				 $.each(message.args.fee_info, function(i, item) {
		  					var data = JSON.stringify(item);
		  					//追加各项费用
		  					$("#fee_list").append("<li class='list'>"
		  							+"<div class='left' style=' background-color:#FFF; border-left:1px solid #CCC;border-top:1px solid #CCC;border-right:1px solid #CCC;'>"
		  							+"<div class='left_lable bold'><span class=' space12'></span>"+item.fee_name+"：</div></div></li>"
		  							+"<li class='list' style=' padding:0px 0px 0px 0px;'>"
		  							+"<div class='left' style=' background-color:#FFF; padding:0px 0px 5px 0px; border-left:1px solid #CCC;border-bottom:1px solid #CCC;border-right:1px solid #CCC;'>"
		  							+"<div class='left_lable'>"
		  							+"<span class=' space24'></span>应收：<span class='bold'>"+item.max_relief+"</span>&nbsp;元</div>"
		  							+"<div class='right_data'>"
		  							+"实收：<input type='text' placeholder='0.00' value='"+item.rec_amount+"' id='charge_item"+i+"' onChange='COST_CHANGE.CheckChargeItem(this,1);' data='"+item.rec_amount+"' class='input_text width_8 text_normal_b'>&nbsp;元&nbsp;</div></div></li>");
		  					//加分隔线
		  					$("#fee_list").append("<div class='line_dashed_h'></div>");
		  					//加上总金额项
		  					fee_all = parseFloat(item.rec_amount) + parseFloat(fee_all);
		  					$("#fee_list").append("<li class='list'><div class='left'><div class='left_lable bold'>总金额：<span class='red' id='fee_all'>"+fee_all+"</span>&nbsp;元</div></div></li>");
		  					//内边框下间距
		  					$("#fee_list").append("<div class='padding_blank10'></div>");
		  				 });
		  				COST_CHANGE.fee_info.next_flag=true;
		  				$("#fee_next").attr("disabled",false);
		  				$("#fee_next").attr("class","ok left_lable");
		  			}else{
		  				$("#fee_next").attr("disabled",true);
		  				$("#fee_next").attr("class","ok_disabled left_lable");
		  				$.alert(message.content);
		  				return;
		  			}
		  		 },
		  		error:function(message){
					var dialog=$.dialog({
						   title:'提示',//提示title
						   content:'查询费用异常',//显示的内容，支持html格式。
						   buttons:[{id:'btn_ok',text:'确定',
							   onClick:function(){					   
								   dialog.close();
							   }//点击时候回调函数
						   }]
					});
				}
		  		});
		},
		//校验费用
		CheckChargeItem:function(e,flag){
			var div = $(e);
			var fee_pre = div.attr("data");	
			
			if(flag==1){
				if(parseFloat(e.value)>parseFloat(fee_pre)){
					$.alert("实收不能大于应收")
					e.value = parseFloat(fee_pre);	
				}	
			}
			
			var re = /^[0-9]+.?[0-9]*$/;   //判断字符串是否为数字     //判断正整数 /^[1-9]+[0-9]*]*$/  
			
			 if (!re.test(e.value))
			{
				 $.alert("请输入数字");
				 e.value = 0;
			 }
			 
			 COST_CHANGE.getTotalFee();
		},
		//计算总费用
		getTotalFee:function(){
			fee_all =0;
			$.each(fee_info, function(i, item) {
				var test = "#charge_item"+i;
				fee_all =  parseFloat($("#charge_item"+i).val()) + parseFloat(fee_all);
			});
			$("#fee_all").html(fee_all);
		},
		//更新费用信息
		updateFeeDetail:function(callBack){
			var data = {
					"pay_type": "10",
					"pay_type_code": "10",
					"fee_info": [],
					"charge":$("#fee_all").html(),
					"essSubscribeId":essSubscribeId,
			};
			//for循环为订单更新添加内容
			for (var i = 0; i < fee_info.length; i++) {
				//服务器费用只支持字符
				var fee_result = {
					"fee_code": fee_info[i].fee_code,
					"fee_class":fee_info[i].fee_class,
					"fee_name": fee_info[i].fee_name,
					"rec_amount":fee_info[i].rec_amount + "",
					"rel_amount":parseFloat($("#charge_item"+i).val())+ "" ,
					"discount_flag": fee_info[i].discount_flag
				};
				data.fee_info.push(fee_result);
			}
			
			CUSTOMER_INFO_SEARCH.OrderUpdate(data,"5",order_id,"费用信息更新中...",function(flag){
				if(flag){
					callBack(true);
				}else{
					callBack(false);
				}
			});
		},
		//PDF
		uploadFormPdf:function(callBack){
			 var data={
					jsessionid:jsessionid,
				   	order_id:order_id,
				   	change_order_type:"7"
					};
			 var URL = window.parent.application.fullPath + "rest/paperless/uploadFormPdf";
				$.ajax({
					url:URL,
					dataType:'json',
					contentType: "application/x-www-form-urlencoded; charset=utf-8", 
					type:'post',
					data:data,
					waitMsg:"免填单生成处理中...",
					success:function(message){					 
						if(message.type == "error"){
							callBack(false);
							$.alert(message.content);
						}else{	
							callBack(true);
						}				
					}			
				});	
		}
	};
	return fee;
})();