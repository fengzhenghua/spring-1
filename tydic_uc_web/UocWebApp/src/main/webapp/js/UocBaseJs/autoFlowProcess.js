var jsession_id = "";
var rest_host = "";
var isDeviceOk = true;
var order_list = [];
var total_count = 0;
var successCount = 0;  //处理成功次数
var failDesc = ""; //处理结果
var cbOrderNo = ""; //CB单号

//写卡信息
var writeCardResult = false;
var acc_nbr= "";
var iccid = "";
var prod_name = "";
var tele_type = "4G";
var writeCount = 0;    //已写卡次数


//发货信息
var sendGoodsResult = false;
var order_no= "";
var send_name = "";
var send_addr = "";
var send_tel = "";
var receive_name = "";
var receive_addr = "";
var receive_tel = "";
var goods_name = "";
var remarks = "";
var cod_account = "";
var cod_charge = "";
var logistics_no = "";

$(document).ready(function() {
	jsession_id = window.parent.operInfo.jsession_id;
	rest_host = window.parent.operInfo.rest_host;

	loadProductGroup();
	loadSendInfo();

	//查询订单
	$("#search_btn").on('click', function(e) {
		searchProcessOrder();
    });

	//开始处理
	$("#start_btn").on('click', function(e) {
		startAutoProcess();
    });

	//ctrl+点击数据事件
    $('#order_list').on('click', 'tr', function(e) {
		var $this = $(this);
		if(e.ctrlKey){
			jumpOrderDetail($this.attr('order_no'),$this.attr('tache_code'),$this.attr('finish_flag'));
		}
	});

    //查询已处理订单
    $('#process_query').on('click', function(e) {
		window.parent.openMenu({
			menu_id: 'frameWriteQuery',
			label: '处理结果查询',
			url: 'jspPage/UocBase/writeCardQuery.jsp'
		}, true);
	});


    // 重发货模态框属性设置
	$.modal('#scanResendModal', {
        width: 500,
        height: 210
    });
	// 打开重发货模态框
	$('#scan_resend').on('click', function(e) {
		$('#scanResendModal').show();

		//sim卡号输入框获得焦点
		$("#sim_id").focus();
		$('#send_result').empty();
	});
	// 重发货确定事件
	$('#scanResendConfirmBtn').on('click', function(e) {
		$('#scanResendModal').hide();
	});
});

//开始自动处理
function startAutoProcess(){
	//初始化
	writeCount = 0;
	successCount = 0;

	if(window.parent.Config.CARD_READER_FLAG){
		isDeviceOk = checkDeviceStatus();
		if(!isDeviceOk){
			$.message.error("写卡器、打印机未正确设置，请检查，然后再次操作！");
			return;
		}
	}

	if(order_list.length>0){
		total_count = order_list.length;
		cardWriteProcess(order_list[0]);
	}else{
		$.message.info("待写卡订单已处理完毕，请重新查询！");
		return;
	}
}

//查询待写卡订单
function searchProcessOrder(){
	$('#order_list').empty();

	var query_order_no = "";
	var cust_name = "";
	var search_info = $('#search_info').val();
	//判断是否为中文
	if(search_info.match(/^[\u4e00-\u9fa5]+$/)){
		cust_name = search_info;
	}else{
		query_order_no = search_info;
	}

	var nowdays = new Date();
	var year = nowdays.getFullYear();
	var month = nowdays.getMonth();
	if (month == 0) {
		month = 12;
		year = year - 1;
	}
	if (month < 10) {
		month = "0" + month;
	}
	var accept_time_start = year + "-" + month + "-" + "01" + " 00:00:00";// 上个月的第一天`
	var accept_time_end = new Date().format('yyyy-MM-dd hh:mm:ss');

	var data = {
			jsession_id: jsession_id,
			accept_time_start: accept_time_start,
			accept_time_end: accept_time_end,
			tache_code: "S00005",
			prod_group: $('#product_group').val(),
			person_flag: "0",
			order_no: query_order_no,
			deal_system_no: "CRAWLER",//TODO 订单来源
			cust_name: cust_name,
			page_no: "1",
			page_size: "99"
		};

	$.ajax({
		type: "post",
		url: rest_host+"rest/ArtificialTaskRest/getArtificialTaskList",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在查询");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				var html = "";
				if (data.args.task_list.task_list != null && data.args.task_list.task_list.length>0) {
					order_list = data.args.task_list.task_list;

					$.each(data.args.task_list.task_list, function(i, item) {
						html += '<tr order_no="'+item.order_no+'" tache_code="'+item.tache_code+'" finish_flag="0">'
						     +  '<td>'+item.order_no+'</td>'
						     +  '<td>'+item.acc_nbr+'</td>'
						     +  '<td>'+item.cust_name+'</td>'
						     +  '<td>'+item.create_time+'</td>'
						     +  '<td></td>'
						     +  '<td></td>'
						     +  '<td></td>'
						     +  '<td></td>'
						     +  '</tr>';
					});

					$('#order_list').append(html);
				}else{
					$.message.info("未查询到代写卡订单");
				}
			} else {
				$.message.error("获取写卡订单失败!"+data.content);
			}
		},
		error: function(data) {
			$.message.error("获取写卡订单Ajax请求失败!");
		},
		complete: function(){
			$.loading.hide();
		}
	});
}

//写卡处理
function cardWriteProcess(item){
	//初始化
	writeCardResult = false;
	sendGoodsResult = false;
	cbOrderNo = "";
	failDesc = "";
	logistics_no = "";
	prod_name = window.parent.Constant.prodCode(item.prod_code)=='未定义'?item.prod_code+"(产品编码)":window.parent.Constant.prodCode(item.prod_code);
	acc_nbr = item.acc_nbr;
	order_no = item.order_no;

	if(window.parent.Config.CARD_READER_FLAG){
		//物理读卡
		isDeviceOk = getCardData();
	}else{
		//模拟读卡
		iccid = '898600028833178384'+(7+writeCount);
	}

	if(!isDeviceOk){
		$.message.error('设备不能读卡，请检查设备!');
		return;
	}

	var json_info='{"iccid":"'+iccid+'"}';
//	console.log(json_info,order_no);

	writeCount++;
	//调用服务订单修改服务(UOC0009)传入卡信息
	var writeCardData = {
			jsession_id: jsession_id,
			serv_order_no: order_no,
			oper_type:'100',//服务订单
			flow_type:"2",
			manual_flag:"0",
			json_info:json_info
		};

	$.ajax({
		type: "post",
		url: rest_host+"rest/ordModServiceRest/serviceOrderChange",
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
				var cardDataRequest = {
						jsession_id: jsession_id,
						serv_order_no: order_no,
						tache_code:"S00001"
					};

				$.ajax({
					type: "post",
					url: rest_host+"rest/ordModServiceRest/getOrderTacheCall",
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					async: false,
					data: cardDataRequest,
					dataType: "json",
					crossDomain: true == !(document.all),
					beforeSend:function(){
						$.loading.show("正在处理");
					},
					success: function(data) {
						if (data.respCode=="0000"&&!INPUT_UTIL.isNull(data.args)) {
							var cardInfo = JSON.parse(data.args.abilityPlatJson);
							var imsi = cardInfo.IMSI;

							if(INPUT_UTIL.isNull(imsi)){
								imsi = cardInfo.imsi;
							}
							var isSuccess = false;
//							console.log("imsi:"+imsi);

							if(window.parent.Config.CARD_READER_FLAG){
								var ret = '';
								var writeTimes = 3; //循环写3次
								for (var i=0; i<writeTimes; i++) {
									//物理写卡
									ret = deviceWrite(imsi,tele_type,acc_nbr);
									if (ret=='1') {
//										console.log("物理写卡成功");
										isSuccess=true;
										break;
									}
								}

								if(ret=='1'){
									isSuccess=true;
								}else{
									failDesc = "物理写卡失败"+ret;
								}
							}else{
								isSuccess=true;
							}

							//传入写卡结果，成功时reason_id=0，失败时reason_id=9
							var json_info='{"reason_id":'+(isSuccess?'"0"':'"9"')+'}';
							var notify = {
									jsession_id: jsession_id,
									serv_order_no: order_no,
									oper_type:'100',//服务订单
									flow_type:"2",
									manual_flag:"0",
									json_info:json_info
								};

							$.ajax({
								type: "post",
								url: rest_host+"rest/ordModServiceRest/serviceOrderChange",
								contentType: "application/x-www-form-urlencoded; charset=utf-8",
								async: false,
								data: notify,
								dataType: "json",
								crossDomain: true == !(document.all),
								beforeSend:function(){
									$.loading.show("正在处理");
								},
								success: function(data) {
									if (data.respCode!="0000") {
										failDesc="传入写卡结果失败："+data.content;
										$.message.error(failDesc);
										return;
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
									jsession_id: jsession_id,
									serv_order_no: order_no,
									tache_code:"S00003"
								};

							$.ajax({
								type: "post",
								url: rest_host+"rest/ordModServiceRest/getOrderTacheCall",
								contentType: "application/x-www-form-urlencoded; charset=utf-8",
								async: false,
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
													jsession_id: jsession_id,
													order_no: order_no,
													oper_type:"101",//服务订单
													order_type:"101",
													tache_code:"S00005",
													call_type:"1"
											};

											$.ajax({
												type: "post",
												url: rest_host+"rest/ArtificialTaskRest/createHandingArtificialTask",
												contentType: "application/x-www-form-urlencoded; charset=utf-8",
												async: false,
												data: taskProcess,
												dataType: "json",
												crossDomain: true == !(document.all),
												beforeSend: function() {
													$.loading.show("正在处理");
												},
												success: function(data) {
													if(data.respCode=="0000"){
														//成功后出卡
														try{
															if(!INPUT_UTIL.isNull(data.args.cb_order_no)){
																cbOrderNo = data.args.cb_order_no;
															}
															var ret = "1";
															if(window.parent.Config.CARD_READER_FLAG){
																ret = devicePush();
															}
															if(ret=="1"){
																writeCardResult=true;

																//TODO 写卡成功，写入cb订单号   获取发货信息
																getOriginalSendInfo(false);
															}else{
																failDesc="出卡失败";
															}
														}catch(e){
															failDesc="出卡失败";
														}
													}else{
														failDesc="写卡任务处理失败："+data.content;
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
									}else{
										failDesc = "写卡结果通知失败:" + data.content;
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
							failDesc="获取卡数据失败：" + data.content;
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
				failDesc = "传入卡信息失败："+data.content;
			}
		},
		error: function(data) {
			$.message.error('服务订单修改Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();

			//写卡失败
			if(!writeCardResult){
				if(window.parent.Config.CARD_READER_FLAG){
					deviceReclaim();
				}

				processComlete(false);
			}
		}
	});
}


//记录写卡结果UOC0063 resultType 0-写卡发货成功，1-写卡失败 未发货，2-写卡成功 发货失败
function logResultNotify(item,iccid,resultType,failDesc){

	var data = {
			jsession_id: jsession_id,
			serv_order_no: order_no,
			sim_id: iccid,
			cust_name: item.cust_name,
			acc_nbr: acc_nbr,
			write_card_result: resultType,
			failed_desc: failDesc
		};

	$.ajax({
		type: "post",
		url: rest_host+"rest/writeCardResult/createWriteCardResult",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在处理");
		},
		success: function(data) {
			if (data.respCode!="0000") {
				$.message.error('记录写卡结果失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('记录写卡结果Ajax请求失败!');
		},
		complete: function(){
			$.loading.hide();

			if(resultType!="1"){
				order_list.splice(writeCount-1,1);//删除写卡成功订单
				writeCount--;
			}
		}
	});
}

//检查发卡机状态
function checkTeleCard() {
	try{
		//检查发卡机状态
		var ret = deviceStatus();
		if(ret =='1'){
			//空白卡卡箱状态，’0’：无卡，’1’：少卡，’2’：卡充足
			if (checkDeviceCardBox()=='0') {
				$.message.error("发卡机卡槽无卡，请联系管理员！");
				return false;
			}
			//废卡回收箱状态，’0’：未满，’1’：已满
			else if (checkDeviceRecycleBox()=='1') {
				$.message.error("发卡机废卡槽已满，请联系管理员！");
				return false;
			}
		}
		else {
			$.message.error("检查发卡机失败，请联系管理员！");
			return false;
		}

	}catch(e){
		$.message.error("发卡机异常，请联系管理员！");
		return false;
	}
	return true;
}

//获取卡数据
function getCardData(){
	var checkResult=checkTeleCard();
	if(checkResult){
		try{
			var ret = '';
			var readTimes = 3; //读卡次数
			for (var i=0; i<readTimes; i++) {
				ret = deviceRead();
				if (ret=='1') {
					return true;
				}
			}

			if(ret!='1'){
				$.message.error("读卡失败:"+ret+",回收卡以后再次读卡");
				deviceReclaim();
				return getCardData();
			}

		}catch(e){
			$.message.error("读卡失败");
			return false;
		}
	}
	return checkResult;
}

//获取产品组
function loadProductGroup(){
	var groupData = {
		jsession_id: jsession_id,
		type_code: "product_group"
	};

	$.ajax({
		type: "post",
		url: rest_host+"rest/ArtificialTaskRest/getTaskQueryInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: groupData,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				var productGroupHtml='';
				if (data.args.code_list != null && data.args.code_list.length>0) {
					$.each(data.args.code_list, function(i, item) {
						productGroupHtml=productGroupHtml+'<option value="'+item.code_id+'">'+item.code_name+'</option>';
					});
				}
				$('#product_group').append(productGroupHtml);
			} else {
				$.message.error('获取产品组失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取产品组Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
}


//获取默认寄件信息
function loadSendInfo(){
	var data = {
		jsession_id: jsession_id,
	};

	$.ajax({
		type: "post",
		url: rest_host+"rest/expressService/getSendInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: data,
		dataType: "json",
		crossDomain: true == !(document.all),
		success: function(data) {
			if (data.respCode=="0000") {
				send_name=data.args.send_name;
				send_addr=data.args.send_addr;
				send_tel=data.args.real_phone;
			}else{
				$.message.error("获取默认寄件信息异常"+data.content);
			}
		},
		error: function(data) {
			$.message.error("获取默认寄件信息Ajax请求失败");
		}
	});
}

//获取原发货信息
function getOriginalSendInfo(isResend){
	var data = {
		jsession_id:jsession_id,
		serv_order_no:order_no
	};

	$.ajax({
		type: "post",
		url: rest_host+"rest/expressService/getInfoForSF",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: false,
		data: data,
		beforeSend:function(){
			$.loading.show("正在处理");
		},
		dataType: "json",
		crossDomain: true == !(document.all),
		success: function(data) {
			if (data.respCode=="0000") {
				receive_name=data.args.contact_name;
				receive_tel=data.args.contact_tel;
				receive_addr=data.args.contact_address;
				goods_name=(INPUT_UTIL.isNull(data.args.goods_name)?"号卡":data.args.goods_name);
				remarks=(INPUT_UTIL.isNull(data.args.note)?"无":data.args.note);
				if(INPUT_UTIL.isNull(data.args.cod_charge)){
					cod_charge="";
					cod_account="";
				}else{
					cod_charge=data.args.cod_charge;
					cod_account=data.args.cod_account;
				}

				//是否发货成功
				if(INPUT_UTIL.isNull(data.args.logistics_no)){
					//发货
					sendSFHaveProcess(isResend);
				}else{
					//直接打印
					autoPrint(isResend);
				}

			}else{
				failDesc = "获取原发货信息异常"+data.content;
			}
		},
		complete:function(){
			$.loading.hide();
		},
		error: function(data) {
			$.message.error("获取原发货信息Ajax请求失败");
		}
	});
}

//顺丰发货（有流程）
function sendSFHaveProcess(isResend){
	var data = {
			jsession_id : jsession_id,
			serv_order_no : order_no,
			d_contact : receive_name,
			d_tel : receive_tel,
			d_address : receive_addr,
			j_tel : send_tel,
			j_contact : send_name,
			j_address : send_addr,
			name : goods_name,
			remark : remarks,
			cod_account : cod_account,
			cod_charge  : cod_charge,
			flow_type : "0",
			insure_charge : "",
			need_return_tracking_no : "0"
		};

	//判断分支
	var sf_flag =window.parent.Config.SF_FLAG;
	var url =rest_host+"rest/logisticsService/sendSFLogisticsInfoCq";
	if(sf_flag =="1"){//走分支
		url =rest_host+"rest/logisticsService/sendSFLogisticsInfo";
	}
	$.ajax({
		type: "post",
		url: url,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: false,
		data: data,
		beforeSend:function(){
			$.loading.show("正在处理");
		},
		dataType: "json",
		crossDomain: true == !(document.all),
		success: function(data) {
			if (data.respCode=="0000") {
				if(data.args.filterResult == "2"){
					logistics_no = data.args.mailNo;
					//自动打印
					autoPrint(isResend);
				}
				if(data.args.filterResult == "1"){
					failDesc = "顺丰发货失败，需人工确实";
					processComlete(isResend);
				}
				if(data.args.filterResult == "3"){
					failDesc = "顺丰发货失败，不可派送";
					processComlete(isResend);
				}
			} else {
				failDesc = "顺丰发货异常:"+data.content;
				processComlete(isResend);
			}
		},
		complete:function(){
			$.loading.hide();
		},
		error: function(data) {
			$.message.error("顺丰发货Ajax请求失败");
		}
	});
}

//自动打印
function autoPrint(isResend){
	var page = 'waybillAuto.jsp';
	var param ='jsession_id='+jsession_id+'&order_no='+order_no+'&rest_host='+rest_host+'&cod_charge='+cod_charge+'&need_return_tracking_no=0&prod_name='+prod_name+'&acc_nbr='+acc_nbr+'&iccid='+iccid;

	$.printer.autoPrint({page:page, param:param}, function(args) {
//		console.log(args.type+' '+args.content);
		if(args.type == true){
			//打印成功
			sendGoodsResult = true;
			successCount++;
			failDesc = "";
			processComlete(isResend);
		}else{
			failDesc = "顺丰已发货成功，物流单打印失败，请检查打印机设置然后重新打印";
			processComlete(isResend);
		}
	});
}

//检查发卡机、打印设备状态
function checkDeviceStatus() {
	var checkResult=checkTeleCard();
	if(checkResult){
		var printer=jatoolsPrinter.getDefaultPrinter();
		if(printer=='ZDesigner GK888d'){
			checkResult=true;
		}else{
			checkResult=false;
		}
	}

	return checkResult;
}

//查看订单详情
function jumpOrderDetail(order_no,tache_code,finish_flag){
	window.parent.openMenu({
		menu_id: 'frameOrderDetail',
		label: '订单详情',
		url: 'jspPage/UocBase/orderDetail.jsp?jsession_id='+jsession_id+'&order_no=' + order_no+"&tache_code="+tache_code+"&finish_flag="+finish_flag
	}, true);
}

//更新页面内容
function processComlete(isResend){
	//补发货处理
	if(isResend){
		if(INPUT_UTIL.isNull(failDesc)){
			$('#send_result').text("扫码发货成功，请继续扫描");
		}else{
			$('#send_result').text(failDesc);
			$('#send_result').css('color','#d94032');
		}
	}else{
		var item=$('#order_list').find('tr[order_no="'+order_list[writeCount-1].order_no+'"]');
		if(INPUT_UTIL.isNull(failDesc)){
			$(item).find('td:eq(7)').text("写卡发货成功");//处理结果
		}else{
			$.message.error(failDesc);
			$(item).find('td:eq(7)').text(failDesc);//处理结果
			$(item).find('td:eq(7)').css('color','#d94032');
		}

		$(item).find('td:eq(4)').text(cbOrderNo);//CB单号
		$(item).find('td:eq(5)').text(iccid);//ICCID
		$(item).find('td:eq(6)').text(logistics_no);//物流单号

		//处理下一个订单
		if(writeCount < order_list.length){
			$.dialog.confirm(
					"订单"+order_no+"处理完成<br>15秒后开始处理下一个订单<br>是否继续",
				    "提示",
				    "继续",
				    "停止",
				    function() {
						cardWriteProcess(order_list[writeCount]);
					},
				    function() {
				    },
				    function() {
				    },
				    15
				);
		}else{
			$.dialog.alert(
				"自动流程处理完成<br>成功数："+successCount+"<br>失败数："+(total_count-successCount),
			    "提示",
			    "确定"
			);
		}

		//记录写卡结果
		var resultType = "";
		if(writeCardResult && sendGoodsResult){
			resultType = "0";//写卡发货成功
		}else if(!writeCardResult){
			resultType = "1";//写卡失败,未发货
		}else if(writeCardResult && !sendGoodsResult){
			resultType = "2";//写卡成功,发货失败
		}
		logResultNotify(order_list[writeCount-1],iccid,resultType,failDesc);
	}
}

/**
 * 获得焦点时，校验sim卡号输入框，符合sim卡号即可自动根据sim卡号查询服务订单号
 * @param value
 */
function chcekInput(value){
	if(value&&value.length==19){
		var data = {
			"jsession_id" : jsession_id,
			"sim_id" : value,
			"acc_nbr" : ""
		};

		$.ajax({
			type: "post",
			url: rest_host+"rest/ordModServiceRest/getServOrderNo",
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			async: true,
			crossDomain: true == !(document.all),
			data: data,
			beforeSend:function(){
				$.loading.show("正在加载");
			},
			dataType: "json",
			crossDomain: true == !(document.all),
			success: function(data) {
				if (data.respCode=="0000") {
					if(data.args!=null){
						if(data.args.serv_order_list!=null && data.args.serv_order_list.length==1){
							$.each(data.args.serv_order_list, function(i, item) {
								prod_name = window.parent.Constant.prodCode(item.prod_code)=='未定义'?item.prod_code+"(产品编码)":window.parent.Constant.prodCode(item.prod_code);
								order_no = item.serv_order_no;
								acc_nbr = item.acc_nbr;
								iccid = item.sim_id;

								//获取收货信息
								getOriginalSendInfo(true);
							});
						}else{
							$.message.info("没有查到相应的服务订单号");
							return;
						}
					}else{
						$.message.info("没有查到相应的服务订单号");
						return;
					}
				} else {
					$.message.error(data.content);
					return;
				}
			},
			complete:function(){
				$('#sim_id').val("");
				$.loading.hide();
			},
			error: function(data) {
				$.message.error("获取服务订单号Ajax请求失败");
				return;
			}
		});
	}
}
