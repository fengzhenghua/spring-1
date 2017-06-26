var READ_WRITE_CARD=(function(){
	var jsessionid = window.parent.application.jsessionid+"PC";
	var fullPath=window.parent.application.fullPath;
	var writeCardFlag=0;//点击写卡按钮次数
	var writeWay=0;//模拟写卡还是正式写卡方式0代表模拟写卡1代表正式写卡
	var readCardInfo={
			card_info:{
				nextFlag:false,
				card_number:"",
				ICCID:"",
				IMSI:"",
				procId:"",
			},
			//读卡
			readCardBtn:function(){
				var sim_no=getCardId();
				$("#resourcesCode").val(sim_no);
			},
			//写卡
			writeCardBtn:function(){
				writeCardFlag ++;
				if(writeCardFlag>1){
					$.alert("请不要重复点击写卡按钮！");
					return;
				}
				var cardNo=$("#resourcesCode").val();
				if(writeWay=="0"){
					if(cardNo==""){
						writeCardFlag=0;
						$.alert("模拟写卡请先输入卡号！");
						return;
					}
				}else{
					if(cardNo==""){
						writeCardFlag=0;
						$.alert("正式写卡请先读卡！");
						return;
					}
				}
				var tele_type=CUSTOMER_INFO_SEARCH.customer_info.tele_type;
				var numId=CUSTOMER_INFO_SEARCH.customer_info.device_number;
				var userType="01";
				var product_name=CUSTOMER_INFO_SEARCH.customer_info.product_name;
				if(product_name.indexOf("OCS") != -1){
					if(tele_type == "2G"){
						userType = "00";
					}else{
						userType = "0";
					}
				}else{
					if(tele_type == "2G"){
						userType = "01";
					}else{
						userType = "1";
					}
				}
				$.ajax({
					url:fullPath+"rest/orderInfo/cardQryRemake",
					data:{
						"jsessionid":jsessionid,
						"iccid":cardNo,
						"numId":numId,
						"userType":userType,
						"tele_type":tele_type,
					},
					dataType:'json',
					type:'post',
					waitMsg:"正在写卡！",
					success:function(message){
						if(message.args.ruturnData=="OK"){
							var cardData="";
							var imsi="";
							var procId="";
							var activeId="";
							var capacityTypeCode="";
							var resKindCode="";
							if (tele_type=="2G") {	
								cardData=message.args.CardData;
								imsi=message.args.IMSI;
								procId=message.args.procId;
								activeId="";
								capacityTypeCode="";
								resKindCode="";
								}else{
									imsi=message.args.card_info.imsi;
									procId=message.args.card_info.procId;
									activeId=message.args.card_info.activeId;
									capacityTypeCode=message.args.card_info.capacityTypeCode;
									resKindCode=message.args.card_info.resKindCode;
									cardData=message.args.card_info.cardData;
								}
							var ret=true;
							if('0'==writeWay){  
							 ret=true;
							}else{
							 ret=UpdateImsi(imsi,tele_type);
							}
							if (ret==true){
								var reasonId="0";//写卡成功
								var errorComments="";
								$.ajax({
									url:fullPath+"rest/orderInfo/cardNotifyRemake",
									data:{
										"jsessionid":jsessionid,
										"imsi":imsi,
										"procId":procId,
										"activeId":activeId,
										"iccid":cardNo,
										"numId":numId,					
										"tele_type":tele_type,
										"reasonId":"0",
									},
									dataType:'json',
									type:'post',
									waitMsg:"正在写卡！",
									success:function(message){
										if(tele_type=="2G"){
											if(message.args.ruturnData=="OK"){
												$("#rw_next").attr("disabled",true);
								  				$("#rw_next").attr("class","ok left_lable");
								  				$("#write_class").attr("class","input_button_disabled");
								  				$("#read_class").attr("class","input_button_disabled");
												//2G最终写卡成功
												var info=READ_WRITE_CARD.card_info;
												info.nextFlag=true;
												info.card_number=cardData;
												info.ICCID=cardNo;
												info.IMSI=imsi;
												info.procId=procId;
											}else{
												writeCardFlag=0;
											}
										}else{
											if(message.args.ruturnData=="写卡成功"){
												$("#rw_next").attr("disabled",true);
								  				$("#rw_next").attr("class","ok left_lable");
								  				$("#write_class").attr("class","input_button_disabled");
								  				$("#read_class").attr("class","input_button_disabled");
												//3、4G最终写卡成功
												var info=READ_WRITE_CARD.card_info;
												info.nextFlag=true;
												info.card_number=cardData;
												info.ICCID=cardNo;
												info.IMSI=imsi;
												info.procId=procId;
											}else{
												writeCardFlag=0;
											}
										}
										$.alert(message.content);
									}
								});
							}else{
								var reasonId="9";//写卡失败
								var errorComments="写卡失败";//写卡失败
								$.ajax({
									url:"cardNotify",
									data:{
										"iccid":iccid,
										"imsi":imsi,
										"procId":procId,
										"activeId":activeId,
										"capacityTypeCode":capacityTypeCode,
										"resKindCode":resKindCode,
										"reasonId":reasonId,
										"errorComments":errorComments,
										"teleType":tele_type,
										"wt_flag":wt_flag
									},
									dataType:'json',
									type:'post',
									waitMsg:"正在校验！",
									success:function(message){
										writeCardFlag=0;
										$.alert("读卡器写卡失败");
									}
								});
							}
						}else{
							writeCardFlag=0;
							$.alert(message.content);
						}
					}
				});
				
			},
			//查询写卡方式
			writeWay:function(){
				$.ajax({
			  		type : "GET",
			  		url : window.parent.application.fullPath + "authority/index/writeWay",
			  		data:"",
			  		async:true,
			  		//waitMsg:"请稍等......",
			  		success:function(message){
			  			writeWay=message.writeWay;
			  		 }
			  		});
			},
			
	};
	return readCardInfo;
})();