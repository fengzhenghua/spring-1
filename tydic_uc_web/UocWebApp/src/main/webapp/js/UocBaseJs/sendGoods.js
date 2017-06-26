var jsession_id="";
var rest_host="";
var resend_flag = "";
var cod_account = "";
var cod_charge = "";
var insure_charge = "";
var need_return_tracking_no = "";
var sendGoodsInfo={
		serv_order_no:""
};

$(document).ready(function() {
	jsession_id=window.parent.operInfo.jsession_id;
	rest_host=window.parent.operInfo.rest_host;
	//一加载该页面就让sim卡号输入框获得焦点
	$("#sim_id").focus();
	//需要保价复选框
	$('#isInsure').on('click', function(e) {
		var $this = $(this);
		if($this.is(':checked') == true){
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
	//点击查询按钮
	$("#query_info_sf").unbind("click").bind("click",function(){
		var serv_order_no = strTrim($("#serv_order_no").val());
		var sim_id=strTrim($("#sim_id").val());
		var acc_nbr=strTrim($("#acc_nbr").val());
		if(!serv_order_no){
			//没有输入服务订单号，那就必须输入sim卡号和设备号进行查询
			if(!sim_id&&!acc_nbr){
				$.message.info('请输入SIM卡号、设备号或者服务订单号进行查询');
				return;
			}
			//如果没有输入服务订单号，则先要根据sim卡号和设备号查出订单服务号
			getServOrderNo(sim_id,acc_nbr);
		}else{
			//如果输入服务订单号不为空，直接根据订单服务号获取顺丰纸质单信息
			getSendGoodsInfoSF(serv_order_no);
		}
	});

	//点击发货按钮
	$("#sendGoodsBtn").unbind("click").bind("click",function(){
		$.dialog.confirm(
				"是否发货？",
			    "提示",
			    "确认",
			    "取消",
			    function() {
					sendGoodsSF();
			    }
			);
	});

	// 快递单预览
	$('#billPreviewBtn').on('click', function(e) {
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

});

/**
 * 获得焦点时，校验sim卡号输入框，符合sim卡号即可自动根据sim卡号查询服务订单号
 * @param value
 */
function chcekInput(value){
	if(value&&value.length==19){
		getServOrderNo(value,"");
	}
}
/**
 * 快递单预览
 */
function billPreviewBtn(){
	window.open("waybill.jsp?jsession_id="+jsession_id+"&order_no="+sendGoodsInfo.serv_order_no+"&rest_host="+rest_host+"&cod_charge="+cod_charge+"&need_return_tracking_no="+need_return_tracking_no);
}

/**
 * 根据卡号和设备号查询服务订单号
 */
function getServOrderNo(sim_no,acc_nbr){
	var data = {
			"jsession_id":jsession_id,
			"sim_id":sim_no,
			"acc_nbr":acc_nbr
	};

	$.ajax({
		type: "post",
		url: rest_host+"rest/ordModServiceRest/getServOrderNo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		crossDomain: true == !(document.all),
		data: data,
		beforeSend:function(){
			$.loading.show("正在加载...");
		},
		dataType: "json",
		crossDomain: true == !(document.all),
		success: function(data) {
			if (data.respCode=="0000") {
				if(data.args!=null){
					if(data.args.serv_order_list!=null && data.args.serv_order_list.length>0){
						$.each(data.args.serv_order_list, function(i, item) {
							var serv_order_no=item.serv_order_no;
							$("#serv_order_no").val(serv_order_no);
							getSendGoodsInfoSF(serv_order_no);
						});
					}else{
						$.message.error("没有查到相应的服务订单号");
						return;
					}
				}else{
					$.message.error("没有查到相应的服务订单号");
					return;
				}
			} else {
				$.message.error(data.content);
				return;
			}
		},
		complete:function(){
			$.loading.hide();
		},
		error: function(data) {
			$.message.error("获取服务订单号Ajax请求失败");
			return;
		}
	});
}

/**
 * 根据订单服务号获取顺丰纸质单信息
 */
function getSendGoodsInfoSF(serv_order_no){
	var data = {
			"jsession_id":jsession_id,
			"serv_order_no":serv_order_no
	};

	$.ajax({
		type: "post",
		url: rest_host+"rest/expressService/getInfoForSF",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: data,
		beforeSend:function(){
			$.loading.show("正在加载...");
		},
		dataType: "json",
		crossDomain: true == !(document.all),
		success: function(data) {
			if (data.respCode=="0000") {
				$('#contact_name').val(data.args.contact_name);
				$('#contact_tel').val(data.args.contact_tel);
				$('#contact_address').val(data.args.contact_address);
				$('#j_contact').val(data.args.send_name);
				$('#goods_name').val(INPUT_UTIL.isNull(data.args.goods_name)?"号卡":data.args.goods_name);
				$('#remarks').val(data.args.note);

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
					$('#sendGoodsBtn').text("发货");
					$('#logistics_no').text("");
					getDefaultSendInfo();
					resend_flag = "send";
				}else{
					$('#j_contact').val(data.args.send_name);
					$('#j_tel').val(data.args.real_phone);
					$('#j_address').val(data.args.send_addr);
					$('#goods_name').val(INPUT_UTIL.isNull(data.args.goods_name)?"号卡":data.args.goods_name);
					$('#remarks').val(data.args.note);
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
				sendGoodsInfo.serv_order_no=serv_order_no;
			} else {
				//获取默认发货信息
				getDefaultSendInfo();
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

}

/**
 * 顺丰发货
 */
function sendGoodsSF(){
//	var sendGoodsBtnValue = $('#sendGoodsBtn').text();
//	if(sendGoodsBtnValue == "重发货"){
//		$('#sendGoodsBtn').text("发货");
//		return;
//	}

	var send_name = $('#j_contact').val();
	var send_addr = $('#j_address').val();
	var send_tel = $('#j_tel').val();
	var goods_name = $('#goods_name').val();
	var remarks = $('#remarks').val();
	var receive_name = $('#contact_name').val();
	var receive_addr = $('#contact_address').val();
	var receive_tel = $('#contact_tel').val();
	insure_charge = $('#insure_charge').val()*100;

//	var flow_type = "0";//传0
	if(resend_flag == "send"){
		flow_type = "0";
	}
	else{
		flow_type = "2";
	}
	//校验数据
	if(sendGoodsInfo.serv_order_no == null || sendGoodsInfo.serv_order_no == ""){
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

	var data = {
			jsession_id : jsession_id,
			serv_order_no : sendGoodsInfo.serv_order_no,
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
	var url = "";
	//发货
	if(resend_flag == "send"){
		if(sf_flag =="1"){//走分支
			url =rest_host+"rest/logisticsService/sendSFLogisticsInfo";
		}
		else{
			url =rest_host+"rest/logisticsService/sendSFLogisticsInfoCq";
		}
	}
	//重发货
	else{
		if(sf_flag =="1"){//走分支
			url =rest_host+"rest/logisticsService/sendSFLogisticsInfoNoProcess";
		}
		else{
			url =rest_host+"rest/logisticsService/sendSFLogisticsInfoNoProcessCq";
		}
	}
	
//	var url =rest_host+"rest/logisticsService/sendSFLogisticsInfoCq";
//	if(sf_flag =="1"){//走分支
//		url =rest_host+"rest/logisticsService/sendSFLogisticsInfo";
//	}
	$.ajax({
		type: "post",
		url: url,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: data,
		beforeSend:function(){
			$.loading.show("正在处理...");
		},
		dataType: "json",
		crossDomain: true == !(document.all),
		success: function(data) {
			if (data.respCode=="0000") {
				if(data.args.filterResult == "2"){
//					$('#saveSendInfoBtn').remove();
//					$('#sendGoodsBtn').remove();
//					$('#billPreviewBtn').remove();
					
					$('#logistics_no').text(data.args.mailNo);
					if(!INPUT_UTIL.isNull(data.args.returnTrackingNo)){
						$('#return_tracking_no').text(data.args.returnTrackingNo);
						$('#return_tracking_no').parent().show();
					}else{
						$('#return_tracking_no').parent().hide();
					}
					$.message.success("发货成功");
					var sendGoodsBtnValue = $('#sendGoodsBtn').text();
					if(sendGoodsBtnValue == "发货"){
						$('#sendGoodsBtn').text("重发货");
						return;
					}
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

}

/**
 * 保存默认发货信息
 */
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

/**
 * 获取默认寄件信息
 */
function getDefaultSendInfo(){
	var data = {
		"jsession_id":jsession_id,
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
				$('#j_contact').val(data.args.send_name);
				$('#j_address').val(data.args.send_addr);
				$('#j_tel').val(data.args.real_phone);
			} else {
				//$.message.error("获取默认寄件信息失败:"+data.content);
			}
		},
		error: function(data) {
			$.message.error("获取默认寄件信息失败");
		}
	});
}

/**
 * 去除两端空格
 * @param str
 * @returns
 */
function strTrim(str){
	if(str!=null) {
		return str.replace(/^(\s|\u00A0)+/,'').replace(/(\s|\u00A0)+$/,'');
	} else {
		return "";
	}
}