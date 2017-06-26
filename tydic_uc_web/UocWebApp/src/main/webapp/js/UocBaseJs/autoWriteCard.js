var jsession_id = "";
var rest_host = "";
var iccid ="";
var tele_type ="4G";
var writeCircle;
var leftNum = 0;	   //剩余写卡数
var writeCount = 0;    //已写卡次数
var successCount = 0;  //写卡成功次数
var faliCount = 0;	   //写卡失败次数
var failDesc = "";
var order_list = [];

$(document).ready(function() {
	jsession_id = window.parent.operInfo.jsession_id;
	rest_host = window.parent.operInfo.rest_host;

	loadProductGroup();

	//循环写卡
	$('#isCyclic').click(function(e) {
		var $this = $(this);
		var change=$('#isCyclic').attr('class')=="loop_checked";
		$this.removeClass();
		if(change){
			$this.addClass('loop_check');
		}else{
			$this.addClass('loop_checked');
		}
	});

	//开始写卡
	$("#startWriteButton").on('click', function(e) {
		writeCardProcess();
    });

	//当前部门
	$("#currentDepart").text(window.parent.operInfo.depart_name);
});


function writeCardProcess(){
	//初始化
	writeCount = 0;
	leftNum = 0;
	successCount = 0;
	faliCount = 0;
	order_list = [];

	//从页面取值
	var isCyclic = $('#isCyclic').attr('class')=="loop_checked";
	var writeNum =  $('#writeCardNum').val();
	var writePeriod =  $('#writePeriod').val();
	var queryPeriod =  $('#queryPeriod').val();

	if(writeNum==0 || writePeriod<15 || queryPeriod<30){
		$.message.info("请重新设置自动写卡数量（大于0）、写卡间隔（大于15秒）以及查询间隔（大于30秒）！");
		return;
	}

	//不为空值
	if(writeNum && writePeriod && queryPeriod){
		//优先处理逾期任务 late_flag=1
		getAvailableOrder(isCyclic,writeNum,writePeriod,queryPeriod,"1");
	}else{
		$.message.info("请设置自动写卡数量、写卡间隔以及查询间隔！");
		return;
	}
}

//查询待写卡订单
function getAvailableOrder(isCyclic,writeNum,writePeriod,queryPeriod,late_flag){
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
	var accept_time_start = year + "-" + month + "-" + "01" + " 00:00:00";// 上个月的第一天
	var accept_time_end = new Date().format('yyyy-MM-dd hh:mm:ss');

	var data = {
			jsession_id: jsession_id,
			accept_time_start: accept_time_start,
			accept_time_end: accept_time_end,
			tache_code: "S00005",
			prod_group: $('#product_group').val(),
			person_flag:"0",
			late_flag:late_flag,
			page_no: "1",
			page_size: writeNum
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
			$.loading.show("正在写卡");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				if (data.args.task_list.task_list != null && data.args.task_list.task_list.length>0) {
					leftNum=leftNum+data.args.task_list.total_num;
					if(late_flag=="1"){
						order_list=data.args.task_list.task_list;
					}else if(late_flag=="0"){
						order_list=$.merge(order_list,data.args.task_list.task_list);
					}
				}
			} else {
				$.loading.hide();
				$.message.error("获取写卡订单失败!"+data.content);
				return;
			}
		},
		error: function(data) {
			$.message.error("获取写卡订单Ajax请求失败!");
		},
		complete: function(){
			if(order_list.length==0 && late_flag=="0"){
				$.loading.hide();
				$.dialog.confirm(
						"未查询到待写卡订单，"+queryPeriod+"秒后重新查询",
					    "提示",
					    "继续",
					    "停止",
					    function() {
							writeCardProcess();
						},
					    function() {
							$.loading.hide();
							return;
					    },
					    function() {
					    	$.loading.hide();
					    	return;
					    },
					    queryPeriod
					);
			}else if(order_list.length<writeNum && late_flag=="1"){
				//处理未逾期任务 late_flag=0
				$.loading.hide();
				getAvailableOrder(isCyclic,writeNum,writePeriod,queryPeriod,"0");
			}else if(order_list != null && order_list.length > 0){
				var isNextOrder=true;

				writeCircle = setInterval(function(){
					if(isNextOrder && writeCount<writeNum && writeCount<order_list.length){
						writeCard(order_list[writeCount],isCyclic,writeNum,writePeriod,leftNum);
					}else if(!isNextOrder){
						$.message.info("正在写卡");
					}else{
						clearInterval(writeCircle);
						faliCount = writeCount - successCount;
						if(isCyclic){
							$.dialog.confirm(
									"写卡完成<br>成功数："+successCount+"<br>失败数："+faliCount+"<br>"+writePeriod+"秒后继续",
								    "提示",
								    "继续",
								    "停止",
								    function() {
										$.loading.hide();
										writeCardProcess();
									},
								    function() {
										$.loading.hide();
										return;
								    },
								    function() {
								    	$.loading.hide();
								    	return;
								    },
								    writePeriod
								);
						}else{
							$.dialog.confirm(
									"写卡完成<br>成功数："+successCount+"<br>失败数："+faliCount,
								    "提示",
								    "继续",
								    "停止",
								    function() {
										$.loading.hide();
										writeCardProcess();
									},
								    function() {
										$.loading.hide();
										return;
								    },
								    function() {
								    	$.loading.hide();
								    	return;
								    }
								);
						}
					}
				}, writePeriod*1000);
			}
		}
	});
}

//调用写卡接口
function writeCard(item,isCyclic,writeNum,writePeriod,leftNum){
	isNextOrder = false;
	var resultFlag = false;
	failDesc = "";
	var isDeviceOk=true;

	if(window.parent.Config.CARD_READER_FLAG){
		//物理读卡
		isDeviceOk=getCardData();
	}else{
		iccid='未获取到测试卡号';
	}

	if(!isDeviceOk){
		$.loading.hide();
		clearInterval(writeCircle);
		return;
	}

	if(isNextOrder){
		return;
	}

	var json_info='{"iccid":'+($.trim(iccid)==''?'""':'"'+iccid+'"')+'}';
//	console.log(json_info,item.order_no);

	//调用服务订单修改服务(UOC0009)传入卡信息
	var writeCardData = {
			jsession_id: jsession_id,
			serv_order_no: item.order_no,
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
						serv_order_no: item.order_no,
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
									ret=srCardIn();
									if(ret=='1'){
										ret = deviceWrite(imsi,tele_type,item.acc_nbr);
										if (ret=='1') {
//											console.log("物理写卡成功");
											isSuccess=true;
											break;
										}
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
									serv_order_no: item.order_no,
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
										writeCount++;
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
									serv_order_no: item.order_no,
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
													order_no: item.order_no,
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
															var ret = devicePush();
															if(ret=="1"){
																successCount++;
																resultFlag=true;
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
			//写卡失败
			if(!resultFlag){
				$.message.error(failDesc);
				deviceReclaim();
			}

			//记录写卡结果
			logResultNotify(item,iccid,resultFlag,failDesc);

			//更新页面内容
			updatePageContent(resultFlag,leftNum);

			if(resultFlag){
				var successDetail='<div>订单:'+item.order_no+'，卡号:'+iccid+'，设备号:'+item.acc_nbr+'，处理时间:'+new Date().format('yyyy-MM-dd hh:mm:ss')+'，姓名:'+item.cust_name+'</div>';
				$('#successOrder').append(successDetail);
			}else{
				var failDetail='<div>订单:'+item.order_no+'，卡号:'+iccid+'，设备号:'+item.acc_nbr+'，处理时间:'+new Date().format('yyyy-MM-dd hh:mm:ss')+'，姓名:'+item.cust_name+'</div>';
				$('#failOrder').append(failDetail);
			}

			$.loading.hide();
		}
	});
}


//记录写卡结果UOC0063
function logResultNotify(item,iccid,resultFlag,failDesc){
	var data = {
			jsession_id: jsession_id,
			serv_order_no: item.order_no,
			sim_id: iccid,
			cust_name: item.cust_name,
			acc_nbr: item.acc_nbr,
			write_card_result: resultFlag?"0":"1",
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
			$.loading.show("正在加载");
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
		}
	});
}

//更新页面显示数据
function updatePageContent(isSuccess,leftNum){
	writeCount++;
	isNextOrder = true;

	var writedNum = parseInt($('#writedNum span').text());
	var failNum = parseInt($('#failNum span').text());
	var successNum = parseInt($('#successNum span').text());
	writedNum++;
	$('#writedNum span').text(writedNum);
	if(isSuccess){
		successNum++;
		$('#successNum span').text(successNum);
		leftNum = leftNum - successNum;
	}else{
		failNum++;
		$('#failNum span').text(failNum);
	}
	$('#leftTotalNum span').text(leftNum);
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
				deviceReclaim();
				$.message.error("读卡失败:"+ret);
				isNextOrder = true;
				return true;
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
