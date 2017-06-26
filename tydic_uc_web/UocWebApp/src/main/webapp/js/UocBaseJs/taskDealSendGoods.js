var order_no= "";
var send_name = "";
var send_addr = "";
var send_tel = "";
var receive_name = "";
var receive_addr = "";
var receive_tel = "";
var goods_name = "";
var remarks = "";
var jsession_id = "";
var resend_flag = "";
var parentPageType = "";
var rest_host = "";
var flow_type = "";
var cod_account = "";
var cod_charge = "";
var insure_charge = "";
var need_return_tracking_no = "";
var breakFlag = false;
$(document).ready(function() {
	//需要保价复选框
	$('#isInsure').on('click', function(e) {
		if($('#isInsure').is(':checked') == true){
			$('#insure_charge').parent().parent().show();
		}else{
			$('#insure_charge').parent().parent().hide();
			$('#insure_charge').val("");
		}
	});
	//需要回单复选框
	$('#isReturn').on('click', function(e) {
		if($('#isReturn').is(':checked')){
			need_return_tracking_no = "1";
		}else{
			need_return_tracking_no = "0";
		}
	});

	parentPageType = commonInfo.pageType;
	if(parentPageType == "task"){
		jsession_id = commonInfo.jsession_id;
		order_no = selectTaskInfo.order_no;
		rest_host = commonInfo.rest_host;
	}
	if(parentPageType == "orderDetail"){
		jsession_id = commonInfo.jsession_id;
		order_no = commonInfo.order_no;
		rest_host = commonInfo.rest_host;
		$('#revokeOrderBtn').hide();
	}
	// 发货
	$('#sendGoodsBtn').on('click', function(e) {
		$.dialog.confirm(
				"是否发货？",
			    "提示",
			    "确认",
			    "取消",
			    function() {
					sendGoodsBtn();
			    }
			);
	});

	// 快递单预览
	$('#billPreviewBtn').on('click', function(e) {
		//$('#logistics_no').text(444825186052);
		if( !$('#logistics_no').text() ){
			$.dialog.alert(
				"请先发货成功后，再查看快递单预览！",
			    "提示",
			    "确认"
			);
			return;
		}
		billPreviewBtn();
	});

	//保存默认发货信息
	$('#saveSendInfoBtn').hide();		//暂时屏蔽
	$('#saveSendInfoBtn').on('click', function(e) {
		$.dialog.confirm(
				"是否保存？",
			    "提示",
			    "确认",
			    "取消",
			    function() {
					saveSendInfoBtn();
			    }
			);
	});

	getOriginalSendInfo();

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

//快递单预览
function billPreviewBtn(){
	window.open("waybill.jsp?jsession_id="+jsession_id+"&order_no="+order_no+"&rest_host="+rest_host+"&cod_charge="+cod_charge+"&need_return_tracking_no="+need_return_tracking_no);
	if(parentPageType == "task"){
		getTaskDataList(0,1,10,1);
		$('#popTask').hide();
	}
};

//自动打印
function autoPrint(){
	var page = 'waybillAuto.jsp';
	var param ='jsession_id='+jsession_id+'&order_no='+order_no+'&rest_host='+rest_host+'&cod_charge='+cod_charge+'&need_return_tracking_no='+need_return_tracking_no+'&prod_name='+selectTaskInfo.prod_name+'&acc_nbr='+selectTaskInfo.acc_nbr+'&iccid='+selectTaskInfo.iccid;

	$.printer.autoPrint({page:page, param:param}, function(args) {
		$.message.info(args.content); // 输出打印结果
		if(args.type == true){
			$.dialog.alert(
					"快递单打印成功",
					"任务",
					"返回",
					function() {
						if(parentPageType == "task"){
							window.location.href = "task.jsp?tache_code="+commonInfo.tache_code+"&role_type="+commonInfo.role_type;
						}
					}
			);
		}else{
			$.dialog.confirm(
					"打印失败,是否浏览快递单重新打印",
					"任务",
					"浏览",
					"返回",
					function() {
						billPreviewBtn();
					},
					function() {
						if(parentPageType == "task"){
							window.location.href = "task.jsp?tache_code="+commonInfo.tache_code+"&role_type="+commonInfo.role_type;
						}
					},
					function() {
						if(parentPageType == "task"){
							window.location.href = "task.jsp?tache_code="+commonInfo.tache_code+"&role_type="+commonInfo.role_type;
						}
					}
			);
		}
	});
}

//获取原发货信息
function getOriginalSendInfo(){
	var data = {
			jsession_id:jsession_id,
			serv_order_no:order_no
	};
	$.ajax({
		type: "post",
		url: rest_host+"rest/expressService/getInfoForSF",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: data,
		beforeSend:function(){
			$.loading.show("正在处理");
		},
		dataType: "json",
		crossDomain: true == !(document.all),
		success: function(data) {
			if (data.respCode=="0000") {
				$('#contact_name').text(data.args.contact_name);
				$('#contact_tel').text(data.args.contact_tel);
				$('#contact_address').text(data.args.contact_address);
				$('#goods_name').val(INPUT_UTIL.isNull(data.args.goods_name)?"号卡":data.args.goods_name);
				$('#remarks').val(INPUT_UTIL.isNull(data.args.note)?"无":data.args.note);
				if(INPUT_UTIL.isNull(data.args.cod_charge)){
					$('#cod_charge').parent().parent().hide();
				}else{
					cod_charge=data.args.cod_charge;
					cod_account=data.args.cod_account;
					$('#cod_charge').text(data.args.cod_charge/100);
					$('#cod_account').text(data.args.cod_account);
					$('#cod_charge').parent().parent().show();
				}

				if(INPUT_UTIL.isNull(data.args.logistics_no)){
					getSendInfo();
					resend_flag = "send";
				}else{
					$('#j_contact').text(data.args.send_name);
					$('#j_tel').text(data.args.real_phone);
					$('#j_address').text(data.args.send_addr);
					$('#goods_name').val(INPUT_UTIL.isNull(data.args.goods_name)?"号卡":data.args.goods_name);
					$('#remarks').val(INPUT_UTIL.isNull(data.args.note)?"无":data.args.note);
					$('#logistics_no').text(data.args.logistics_no);

					if(!INPUT_UTIL.isNull(data.args.insure_charge)){
						$('#isInsure').attr('checked','true');
						$('#insure_charge').parent().parent().show();
						$('#insure_charge').val(data.args.insure_charge/100);
					}

					if(data.args.need_return_tracking_no=="1"){
						need_return_tracking_no = "1";
						$('#isReturn').attr('checked','true');
						$('#return_tracking_no').parent().show();
						$('#return_tracking_no').text(data.args.return_tracking_no);
					}

					$('#sendGoodsBtn').text("重发货");

					resend_flag = "resend";
				}
			} else {
				//获取默认发货信息
				getSendInfo();
				resend_flag = "send";
			}
		},
		complete:function(){
			$.loading.hide();
		},
		error: function(data) {
			$.message.error("获取原发货信息失败");
		}
	});
};

//发货按钮
function sendGoodsBtn(){
	var sendGoodsBtnValue = $('#sendGoodsBtn').text();
	if(sendGoodsBtnValue == "重发货"){
		$('#sendGoodsBtn').text("发货");
		$('#j_contact').attr("disabled",false);
		$('#j_tel').attr("disabled",false);
		$('#j_address').attr("disabled",false);
		$('#goods_name').attr("disabled",false);
		$('#remarks').attr("disabled",false);
		return;
	}
	send_name = $('#j_contact').text();
	send_addr = $('#j_address').text();
	send_tel = $('#j_tel').text();
	goods_name = $('#goods_name').val();
	remarks = $('#remarks').val();
	receive_name = $('#contact_name').text();
	receive_addr = $('#contact_address').text();
	receive_tel = $('#contact_tel').text();
	insure_charge = $('#insure_charge').val()*100;

	if(resend_flag == "send"){
		flow_type = "0";
	}
	else{
		flow_type = "2";
	}
	//校验数据
	if(order_no == null || order_no == ""){
		$.message.info('订单号不能为空');
		return;
	}
	if(receive_name == null || receive_name == ""){
		$.message.info("收货人姓名不能为空");
		return;
	}
	if(receive_tel == null || receive_tel == ""){
		$.message.info("收货人电话不能为空");
		return;
	}
	if(receive_addr == null || receive_addr == ""){
		$.message.info("收货人地址不能为空");
		return;
	}
	if(send_name == null || send_name == ""){
		$.message.info("发货人姓名不能为空");
		return;
	}
	if(send_addr == null || send_addr == ""){
		$.message.info("发货人地址不能为空");
		return;
	}
	if(send_tel == null || send_tel == ""){
		$.message.info("发货人电话不能为空");
		return;
	}
	if(goods_name == null || goods_name == ""){
		$.message.info("货物名称不能为空");
		return;
	}
	if($('#isInsure').is(':checked') == true && INPUT_UTIL.isNull(insure_charge) && insure_charge==0){
		$.message.info("保价金额不能为空");
		return;
	}


	if(flow_type == "0"){
		sendSFHaveProcess();
	}
	else{
		sendSFNoProcess();
	}
};
//顺丰发货(无流程)
function sendSFNoProcess(){

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
			flow_type : flow_type,
			remark : remarks,
			cod_account : cod_account,
			cod_charge  : cod_charge,
			insure_charge : insure_charge==0?"":insure_charge,
			need_return_tracking_no : need_return_tracking_no
		};
	//判断分支
	var sf_flag =window.parent.Config.SF_FLAG;
	var url =rest_host+"rest/logisticsService/sendSFLogisticsInfoNoProcessCq";
	if(sf_flag =="1"){//走分支
		url =rest_host+"rest/logisticsService/sendSFLogisticsInfoNoProcess";
	}

	$.ajax({
		type: "post",
		url: url,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: data,
		beforeSend:function(){
			$.loading.show("正在处理");
		},
		dataType: "json",
		crossDomain: true == !(document.all),
		success: function(data) {
			if (data.respCode=="0000") {
				if(data.args.filterResult == "2"){
					$('#logistics_no').text(data.args.mailNo);
					if(!INPUT_UTIL.isNull(data.args.returnTrackingNo)){
						$('#return_tracking_no').text(data.args.returnTrackingNo);
						$('#return_tracking_no').parent().show();
					}else{
						$('#return_tracking_no').parent().hide();
					}

					//自动打印
					autoPrint();
				}
				if(data.args.filterResult == "1"){
					$.message.info("需人工确实");
				}
				if(data.args.filterResult == "3"){
					$.message.info("不可派送");
				}
			} else {
				$.message.error("发货失败:"+data.content);
			}
		},
		complete:function(){
			$.loading.hide();
		},
		error: function(data) {
			$.message.error("发货失败");
		}
	});
};

//顺丰发货（有流程）
function sendSFHaveProcess(){
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
			flow_type : flow_type,
			remark : remarks,
			cod_account : cod_account,
			cod_charge  : cod_charge,
			insure_charge : insure_charge==0?"":insure_charge,
			need_return_tracking_no : need_return_tracking_no
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
		async: true,
		data: data,
		beforeSend:function(){
			$.loading.show("正在处理");
		},
		dataType: "json",
		crossDomain: true == !(document.all),
		success: function(data) {
			if (data.respCode=="0000") {
				if(data.args.filterResult == "2"){
					$('#logistics_no').text(data.args.mailNo);
					if(!INPUT_UTIL.isNull(data.args.returnTrackingNo)){
						$('#return_tracking_no').text(data.args.returnTrackingNo);
						$('#return_tracking_no').parent().show();
					}else{
						$('#return_tracking_no').parent().hide();
					}

					//自动打印
					autoPrint();
				}
				if(data.args.filterResult == "1"){
					$.message.info("需人工确实");
				}
				if(data.args.filterResult == "3"){
					$.message.info("不可派送");
				}
			} else {
				$.message.error("发货失败:"+data.content);
			}
		},
		complete:function(){
			$.loading.hide();
		},
		error: function(data) {
			$.message.error("发货失败");
		}
	});
};

//保存默认发货信息
function saveSendInfoBtn(){
	send_name = $('#j_contact').val();
	send_addr = $('#j_address').val();
	send_tel = $('#j_tel').val();

	var data = {
		jsession_id : jsession_id,
		send_name : send_name,
		send_addr : send_addr,
		real_phone : send_tel
	};

	$.ajax({
		type: "post",
		url: rest_host+"rest/expressService/saveSendInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: data,
		dataType: "json",
		crossDomain: true == !(document.all),
		success: function(data) {
			if (data.respCode=="0000") {
				$.message.success("保存默认寄件信息成功");
			} else {
				$.message.error("保存默认寄件信息失败:"+data.content);
			}
		},
		error: function(data) {
			$.message.error("保存默认寄件信息失败");
		}
	});
}

//获取默认寄件信息
function getSendInfo(){
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
				$('#j_contact').text(data.args.send_name);
				$('#j_address').text(data.args.send_addr);
				$('#j_tel').text(data.args.real_phone);
			} else {
				//$.message.error("获取默认寄件信息失败:"+data.content);
			}
		},
		error: function(data) {
			$.message.error("获取默认寄件信息失败");
		}
	});
};

//强制流转 UOC0015，flow_skip填1
function forceFlow(){
	var data = {
			jsession_id: commonInfo.jsession_id,
			order_no: selectTaskInfo.order_no,
			oper_type:"101",
			order_type:"101",
			flow_skip:"1",
			tache_code:"S00013",
			call_type:"1"
		};

	$.ajax({
		type: "post",
		url: commonInfo.rest_host+"rest/ArtificialTaskRest/createHandingArtificialTask",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				$.dialog.confirm(
					    "流转成功",
					    "下一步",
					    "返回",
					    function() {
					    	window.location.href = "task.jsp?role_type="+commonInfo.role_type;
					    },
					    function() {
					    	window.location.href = "task.jsp?role_type="+commonInfo.role_type;
					    }
					);
			} else {
				$.message.error("人工任务处理失败:"+data.content);
			}
		},
		error: function(data) {
			$.message.error('人工任务处理失败!');

		},
		complete:function(){
			$.loading.hide();
		}
	});
}
