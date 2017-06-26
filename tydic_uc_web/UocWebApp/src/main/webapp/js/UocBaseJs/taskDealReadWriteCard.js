var load_rwcard_acx = false;
var writeCardFlag =0;

$(document).ready(function() {
	if(!load_rwcard_acx){
		document.body.insertAdjacentHTML("beforeEnd"," \<object id=\"CardReader\" style=\"display:none;\" classid=\"clsid:43E4D4FC-3CD8-459A-AAA1-698C1288DE93\" width=\"0\" height=\"0\"> \</object>");
		load_rwcard_acx = true;
	}

	// 读卡
	$('#readCardBtn').on('click', function(e) {
		readCardBtn(selectTaskInfo.order_no);
	});

	// 一键写卡
	$('#writeCardBtn').on('click', function(e) {
		$.dialog.confirm(
				"是否写卡？",
			    "提示",
			    "确认",
			    "取消",
			    function() {
					writeCardBtn(selectTaskInfo.order_no);
			    }
			);
	});
	if(commonInfo.role_type !="2"){
		$("#forceFlowBtn").hide();
	}

	$("#forceFlowBtn").on('click', function(e) {
		$.dialog.confirm(
				"是否强制流转？",
			    "提示",
			    "确认",
			    "取消",
			    function() {
					forceFlow();
			    }
			);
	});
});


//读卡按钮
function readCardBtn(order_no){
	var sim_no=getCardId();
	$('#card_no').val(sim_no);
};


//写卡按钮
function writeCardBtn(order_no){
	writeCardFlag ++;
	if(writeCardFlag>1){
		writeCardFlag=0;
		$.message.info("请不要重复点击写卡按钮");
		return;
	}
	var iccid=$("#card_no").val();

	if(''==iccid||'请读卡'==iccid){
		writeCardFlag=0;
		$.message.info("请先读卡在进行写卡");
		return;
	}else{
		if(window.parent.Config.CARD_READER_FLAG){
			try{
				var sim_no=getCardId();
				if(sim_no!=iccid){
				   $.message.info("此白卡与已经获取的卡号不一样，请插入之前的白卡再操作");
				   writeCardFlag=0;
				   return;
				}
			}catch(e){
				writeCardFlag=0;
				$.message.error("物理读卡失败");
				return;
			}
		}
	}

	var json_info='{"iccid":'+($.trim(iccid)==''?'""':'"'+iccid+'"')+'}';

	//调用服务订单修改服务(UOC0009)传入卡信息
	var writeCardData = {
			jsession_id: commonInfo.jsession_id,
			serv_order_no: order_no,
			oper_type:'100',//服务订单
			flow_type:"2",
			manual_flag:"0",
			json_info:json_info
		};

	$.ajax({
		type: "post",
		url: commonInfo.rest_host+"rest/ordModServiceRest/serviceOrderChange",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: writeCardData,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend:function(){
			$.loading.show("正在处理");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				//获取卡数据
				var getCardData = {
						jsession_id: commonInfo.jsession_id,
						serv_order_no: order_no,
						tache_code:"S00001"
					};

				$.ajax({
					type: "post",
					url: commonInfo.rest_host+"rest/ordModServiceRest/getOrderTacheCall",
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					async: true,
					data: getCardData,
					dataType: "json",
					crossDomain: true == !(document.all),
					beforeSend:function(){
						$.loading.show("正在处理");
					},
					success: function(data) {
						if (data.respCode=="0000") {
							var cardInfo = JSON.parse(data.args.abilityPlatJson);
							var imsi = cardInfo.IMSI;

							if(INPUT_UTIL.isNull(imsi)){
								imsi = cardInfo.imsi;
							}
							var teleType = "4G";
							var isSuccess = false;
							if(window.parent.Config.CARD_READER_FLAG){
								//写卡
								try{
									isSuccess = UpdateImsi(imsi, teleType);
								}catch(e){
									$.message.error("物理写卡失败");
									return;
								}
							}else{
								isSuccess=true;
							}

							//传入写卡结果，成功时reason_id=0，失败时reason_id=9
							var json_info='{"reason_id":'+(isSuccess?'"0"':'"9"')+'}';
							var notify = {
									jsession_id: commonInfo.jsession_id,
									serv_order_no: order_no,
									oper_type:'100',//服务订单
									flow_type:"2",
									manual_flag:"0",
									json_info:json_info
								};

							$.ajax({
								type: "post",
								url: commonInfo.rest_host+"rest/ordModServiceRest/serviceOrderChange",
								contentType: "application/x-www-form-urlencoded; charset=utf-8",
								async: true,
								data: notify,
								dataType: "json",
								crossDomain: true == !(document.all),
								beforeSend:function(){
									$.loading.show("正在处理");
								},
								success: function(data) {
									if (data.respCode!="0000") {
										$.dialog.alert(
												"传入写卡结果失败："+data.content,
											    "任务",
											    "确认"
											);
									}
								},
								error: function(data) {
									$.message.error('服务订单修改Ajax请求失败!');
								},
								complete:function(){
									$.loading.hide();
								}
							});


							//写卡结果通知
							var successData = {
									jsession_id: commonInfo.jsession_id,
									serv_order_no: order_no,
									tache_code:"S00003"
								};

							$.ajax({
								type: "post",
								url: commonInfo.rest_host+"rest/ordModServiceRest/getOrderTacheCall",
								contentType: "application/x-www-form-urlencoded; charset=utf-8",
								async: true,
								data: successData,
								dataType: "json",
								crossDomain: true == !(document.all),
								beforeSend:function(){
									$.loading.show("正在处理");
								},
								success: function(data) {
									if (data.respCode=="0000") {
										if(isSuccess){
											//调用人工任务处理服务(UOC0015)
											var taskProcess = {
													jsession_id: commonInfo.jsession_id,
													order_no: order_no,
													oper_type:"101",//服务订单
													order_type:"101",
													tache_code:"S00005",
													call_type:"1"
											};

											$.ajax({
												type: "post",
												url: commonInfo.rest_host+"rest/ArtificialTaskRest/createHandingArtificialTask",
												contentType: "application/x-www-form-urlencoded; charset=utf-8",
												async: true,
												data: taskProcess,
												dataType: "json",
												crossDomain: true == !(document.all),
												beforeSend: function() {
													$.loading.show("正在处理");
												},
												success: function(data) {
													if (data.respCode=="0000") {
														if(INPUT_UTIL.isNull(commonInfo.tache_code)){
															$.dialog.confirm(
																    "写卡成功,是否进入下一环节",
																    "任务",
																    "下一步",
																    "返回",
																    function() {
																    	getTaskDataList(0,1,10,1);
																    	getTaskDetail(selectTaskInfo.order_no,selectTaskInfo.order_type);
																    },
																    function() {
																    	window.location.href = "task.jsp?role_type="+commonInfo.role_type;
																    },
																    function() {
																    	window.location.href = "task.jsp?role_type="+commonInfo.role_type;
																    }
																);
														}else{
															$.dialog.alert(
																	"写卡成功",
																	"任务",
																	"返回",
																	function() {
																    	window.location.href = "task.jsp?tache_code="+commonInfo.tache_code+"&role_type="+commonInfo.role_type;
																    }
															);
														}
													} else {
														$.dialog.alert(
																"写卡任务处理失败:" + data.content,
																"任务",
																"确认"
														);
													}
												},
												error: function(data) {
													$.message.error('写卡任务处理Ajax请求失败!'+ data.content);
												},
												complete:function(){
													$.loading.hide();
												}
											});
										}else{
											$.dialog.alert(
													"物理写卡失败",
												    "任务",
												    "确认"
												);
										}
									}else{
										$.dialog.alert(
												"写卡结果通知失败:" + data.content,
											    "任务",
											    "确认"
											);
									}
								},
								error: function(data) {
									$.message.error('环节接口服务Ajax请求失败!');
								},
								complete:function(){
									$.loading.hide();
								}
							});
						}else{
							$.dialog.alert(
									"获取卡数据失败：" + data.content,
								    "任务",
								    "确认"
								);
						}
					},
					error: function(data) {
						$.message.error('环节接口服务Ajax请求失败!');
					},
					complete:function(){
						$.loading.hide();
					}
				});

			}else{
				$.dialog.alert(
						"传入卡信息失败："+data.content,
					    "任务",
					    "确认"
					);
			}
		},
		error: function(data) {
			$.message.error('服务订单修改Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
			writeCardFlag=0;
		}
	});

};


//调用服务订单修改服务(UOC0009)
function servOrderChange(order_no,json_info){
	var result={"processFlag":false,"data":""};

	var data = {
			jsession_id: commonInfo.jsession_id,
			serv_order_no: order_no,
			oper_type:'100',//服务订单
			flow_type:"2",
			manual_flag:"0",
			json_info:json_info
		};

	$.ajax({
		type: "post",
		url: commonInfo.rest_host+"rest/ordModServiceRest/serviceOrderChange",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend:function(){
			$.loading.show("正在处理");
		},
		success: function(data) {
			result.data=data;
			if (data.respCode=="0000") {
				result.processFlag=true;
			}
		},
		error: function(data) {
			result.data=data;
			$.message.error('服务订单修改Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});

	return result;
};


//调用环节调用接口服务(UOC0052)
function tacheHandle(order_no,tache_code){
	var result={"processFlag":false,"data":""};

	var data = {
			jsession_id: commonInfo.jsession_id,
			serv_order_no: order_no,
			tache_code:tache_code
		};

	$.ajax({
		type: "post",
		url: commonInfo.rest_host+"rest/ordModServiceRest/getOrderTacheCall",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend:function(){
			$.loading.show("正在处理");
		},
		success: function(data) {
			result.data=data;
			if (data.respCode=="0000") {
				result.processFlag=true;
			}
		},
		error: function(data) {
			result.data=data;
			$.message.error('环节接口服务Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});

	return result;
};


//强制流转 UOC0015，flow_skip填1
function forceFlow(){
	//调用人工任务处理服务(UOC0015)
	var taskProcess = {
			jsession_id: commonInfo.jsession_id,
			order_no: selectTaskInfo.order_no,
			oper_type:"101",//服务订单
			order_type:"101",
			flow_skip:"1",
			tache_code:"S00005",
			call_type:"1"
	};

	$.ajax({
		type: "post",
		url: commonInfo.rest_host+"rest/ArtificialTaskRest/createHandingArtificialTask",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: taskProcess,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在处理");
		},
		success: function(data) {
			console.info(1111111111111);
			console.info(data);
			if (data.respCode=="0000") {
				if(INPUT_UTIL.isNull(commonInfo.tache_code)){
					$.dialog.confirm(
						    "写卡成功,是否进入下一环节",
						    "任务",
						    "下一步",
						    "返回",
						    function() {
						    	getTaskDataList(0,1,10,1);
						    	getTaskDetail(selectTaskInfo.order_no,selectTaskInfo.order_type);
						    },
						    function() {
						    	window.location.href = "task.jsp?role_type="+commonInfo.role_type;
						    },
						    function() {
						    	window.location.href = "task.jsp?role_type="+commonInfo.role_type;
						    }
						);
				}else{
					$.dialog.alert(
							"审核成功",
							"任务",
							"返回",
							function() {
						    	window.location.href = "task.jsp?tache_code="+commonInfo.tache_code+"&role_type="+commonInfo.role_type;
						    }
					);
				}
			} else {
				$.dialog.alert(
						"写卡任务处理失败:" + data.content,
						"任务",
						"确认"
				);
			}
		},
		error: function(data) {
			$.message.error('写卡任务处理Ajax请求失败!'+ data.content);
		},
		complete:function(){
			$.loading.hide();
		}
	});
}