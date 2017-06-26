$(document).ready(function() {
	//进入页面时默认成卡,隐藏readCard、writeCard连个按钮
	var teleType = $("#tele_type").val();//电信类型
	var nextUrl=application.fullPath + "authority/order/orderProsessing";
	//确定按钮
	$(".ok").click( function() {// 开户
//		$.alert('开户');
		var checkResURL = application.fullPath + "authority/checkResInfo/doCheckResInfo"; //资源验证url
		var accountOpenURL = application.fullPath + "authority/accountOpen/doAccountOpen";//开户url
		var resourcesType = $("#resourcesType").val();//资源类型 01成卡，02白卡
		var resourcesCode = $("#resourcesCode").val();//资源号
		var order_id = $("#order_id").val();//订单号
		var tele_type = $("#tele_type").val();//电信类型
		var imsi = $("#imsi").val();
		var cardData = $("#cardData").val();
		var cardType = $("#cardType").val();
		var capacityTypeCode = $("#capacityTypeCode").val();
		var resKindCode = $("#resKindCode").val();
		var activeId = $("#activeId").val();
		var  procId = $("#procId").val();
		resourcesCode = resourcesCode.replace(/(^\s*)|(\s*$)/g, "");
		var submitFlag = $("#submitFlag").val();
		//$.alert("resourcesCode=="+resourcesCode.length);
		if('3G' == tele_type||'4G' == tele_type){
		 if ('' == resourcesCode&&'01'==resourcesType) {
			//$.alert('请填写卡号！');
			$.warn("请填写卡号！");
			return;
		 }
		}else{
		 if ('' == resourcesCode) {
					//$.alert('请填写卡号！');
					$.warn("请填写卡号！");
					return;
				 }
		 if('0' == submitFlag)  {
			$.warn("请先写完卡后在提交");
			return;
		}
		}
//		$.alert("resourcesType=="+resourcesType);
		//开户----开始-------------------------------
		doAccountOpen(order_id,resourcesType,resourcesCode,accountOpenURL,imsi,cardType,capacityTypeCode,resKindCode,cardData,activeId,procId);
		//开户----结束-------------------------------
	});
	// 取消按钮
	$("#cancel").click(function() {
		$.confirm('是否确定取消配卡?',
			'取消配卡',
			function cancel_ok() {
				window.location.href = nextUrl;
			},
			function cancel_cancel() {
				
			});
	});
	//读卡按钮
	$("#readCard").click(function() {
		var writeWay=$("#writeWay").val();
		if('0'==writeWay){
		  $.alert("模拟白卡开户，请手动输入卡号，进行写卡");
		}else{
		var sim_no=getCardId();
		$("#resourcesCode").val(sim_no);	
		}
	    $("#submitFlag").val(0);
	});
	//写卡按钮
	$("#writeCard").click(function() {
		var iccid=$("#resourcesCode").val();
		var writeWay=$("#writeWay").val();
		if(''==iccid){
		   if('0'==writeWay){
		     $.alert("模拟白卡开户，请手动输入卡号，在进行写卡");
		     return;
			}else{
			 $.warn("请先读卡在进行写卡");
			 return;
		   }
		}else{
			if('0'==writeWay){
			}else{
			 var sim_no=getCardId();
			 if(sim_no==iccid){	 
			 }else{
			 $.warn("此白卡与已经获取的卡号不一样，请插入之前的白卡再操作");	 
			 return;
			 }
			}
			
		}
		var numId=$("#acc_nbr").val();
		var prepayFlag=$("#prepayFlag").val();
		var cardType=$("#cardType").val();
		var teleType = $("#tele_type").val();//电信类型
		$.ajax({
			url:"getCardData",
			data:{
				"iccid":iccid,
				"numId":numId,
				"cardType":cardType,
				"userType":prepayFlag,
				"teleType":teleType
			},
			dataType:'json',
			type:'post',
			waitMsg:"正在写卡！",
			success:function(message){
				var returnData=message.args.ruturnData;
				if(returnData=="OK"){
					if ('2G' == teleType) {	
					var cardData=message.args.CardData;
					var imsi=message.args.IMSI;
					var procId=message.args.procId;
					//console.log(imsi);
					$("#cardData").val(cardData);
					$("#imsi").val(imsi);
					$("#procId").val(procId);//2G代表报文头流水 3G代表卡序列
					var activeId="";
					var capacityTypeCode="";
					var resKindCode="";
					}else{
					var imsi=message.args.card_info.imsi;
					var procId=message.args.card_info.procId;
					var activeId=message.args.card_info.activeId;
					var capacityTypeCode=message.args.card_info.capacityTypeCode;
					var resKindCode=message.args.card_info.resKindCode;
					var cardData=message.args.card_info.cardData;
					$("#imsi").val(imsi);
					$("#activeId").val(activeId);
					$("#cardData").val(cardData);
					$("#capacityTypeCode").val(capacityTypeCode);
					$("#resKindCode").val(resKindCode);
					}
					var ret=true;
					if('0'==writeWay){  
					 ret=true;
					}else{
					 ret=UpdateImsi(imsi,teleType);
					}
					if (ret==true){
						var reasonId="0";//写卡成功
						var errorComments="";
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
								"teleType":teleType
								
							},
							dataType:'json',
							type:'post',
							waitMsg:"正在校验！",
							success:function(message){
								var returnData=message.args.ruturnData;
								if(returnData=="OK"){
								  $.success("写卡成功");
								  $("#submitFlag").val(1);
								}else{
								  $.error(returnData);
								}
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
							"teleType":teleType
						},
						dataType:'json',
						type:'post',
						waitMsg:"正在校验！",
						success:function(message){
							$.error("读卡器写卡失败");
						}
					});
					}
				}else{
					$.error(returnData);
				}
			}
		});

	});
	//选择卡类型
	$("#resourcesType").change(function(){
		var resourcesType=$("#resourcesType").val();
		var resourcesCode = $("#resourcesCode").val();//资源号
		var teleType = $("#tele_type").val();//电信类型
//		$.alert("resourcesType=="+resourcesType);
			if ("2G" == teleType) {
				if ("01" == resourcesType) {// 成卡
					$("#readCard").hide();
					$("#writeCard").hide();
					$("#resourcesCode").val();
					$("#submitFlag").val(1);
				} else if ("02" == resourcesType) {// 白卡
					$("#readCard").show();
					$("#writeCard").show();
					$("#resourcesCode").val();
					$("#submitFlag").val(0);
				}
			} else {
				if ("01" == resourcesType) {// 成卡
					$('#idcardtype_word').attr('style','visibility:visible;');
					$('#idcardtype_select').attr('style','visibility:visible;');
					
				} else if ("02" == resourcesType) {// 白卡
					$('#idcardtype_word').attr('style','visibility:hidden;');
					$('#idcardtype_select').attr('style','visibility:hidden;');
					
				}

			}
	});
	$("#resourcesCode").change(function(){
//		if(null!=resourcesCode&&""!=resourcesCode){
//			$("#writeCard").show();
//		}else{
//			$("#writeCard").hide();
//		}
	});
	//开户提交
	function doAccountOpen( order_id,resourcesType, resourcesCode, accountOpenURL,imsi,cardType,capacityTypeCode,resKindCode,cardData,activeId,procId){		
		$.ajax({
			type : "post",
			url : accountOpenURL,
			waitMsg : "正在开户，请稍候！",
			data : {
				"order_id" : order_id,
				"card_kind" : resourcesType,
				"SimID" : resourcesCode,
				"imsi" : imsi,
				"cardType" : cardType,
				"capacityTypeCode" : capacityTypeCode,
				"resKindCode" : resKindCode,
				"cardData" : cardData,
				"activeId" : activeId,
				"procId":procId
			},
			dataType : 'json',
			success : function(message) {
				var respCode = message.args.RespCode;
				var RespDesc = message.args.RespDesc;
				if (respCode == '0000') {//开户成功
					$("#accountOpen").unbind("click");
					/*$.dialog({
							title : message.content,
							content : message.content,
							buttons : [ {
								id : "",
								text : "确定",
								onClick : function() {
									window.location.href = application.fullPath + "authority/order/orderProsessing";
								}
							} ]
						});*/
					/*$.message({
						type:"success",
						content:message.content,
						callback:function(){
							window.location.href = application.fullPath + "authority/order/orderProsessing";
						}
					});*/
					$.success("恭喜你，"+RespDesc,function(){
						window.location.href = application.fullPath + "authority/order/orderProsessing";
					});
				} else {//开户失败
					$.error(message.content + ":[" + message.args.RespDesc + "]");
					//$.alert(message.content + ":[" + message.args.RespDesc + "]");
//					$.message({
//						type:"warn",
//						content:message.content + ":[" + message.args.RespDesc + "]"
//					});
				}
			},
			error : function() {//开户提交失败
				$.warn("开户提交失败，请稍后再试！");
				//$.alert("开户提交失败，请稍后再试！");
//				$.message({
//					type:"error",
//					content:"开户提交失败，请稍后再试！"
//				});
			}
		});
	}
	//资源校验
	function doCheckResInfoThenAccountOpen( order_id, resourcesCode, accountOpenURL){
		$.ajax({
			type : "post",
			url : accountOpenURL,
			waitMsg : "正在开户，请稍候！",
			data : {
				"order_id":order_id,
				"resourcesCode":resourcesCode
			},
			dataType : 'json',
			success : function(message) {
				var respCode = message.args.RespCode;
//				$.success("ceshi",function(){
//					$.warn("respCode=="+respCode);
//					//window.location.href = application.fullPath + "authority/order/orderProsessing";
//				});
				if (respCode == '0000') {//
					$("#accountOpen").unbind("click");
					$.success("恭喜你，"+message.args.RespDesc,function(){
						window.location.href = application.fullPath + "authority/order/orderProsessing";
					});
				} else {//
					$.error(message.content + ":[" + message.args.RespDesc + "]");
					$("#accountOpen").bind("click");
				}
			},
			error : function() {//
				$.warn("开户提交失败，请稍后再试！");
				$("#accountOpen").bind("click");
			}
		});
	}
});