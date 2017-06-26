var month = "";
var today = "";
var jsession_id = "";
var order_no = "";
var rest_host = "";
var cod_charge = "";
var need_return_tracking_no = "";

$(document).ready(function() {
	jsession_id = HTML_UTLS.getParam("jsession_id");
	order_no = HTML_UTLS.getParam("order_no");
	rest_host = HTML_UTLS.getParam("rest_host");
	cod_charge = HTML_UTLS.getParam("cod_charge");
	if(!INPUT_UTIL.isNull(cod_charge)){
		$('#cod').show();
	}
	need_return_tracking_no = HTML_UTLS.getParam("need_return_tracking_no");
	if(need_return_tracking_no=="1"){
		$('#waybill2').show();
	}
	getData();
});

function getDate(){
	var date=new Date;
	month = date.getMonth()+1;
	today = date.getDate();
	month =(month<10 ? "0"+month:month);
	today =(today<10 ? "0"+today:today);
}


function getData(){
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
			if(data.respCode=="0000"){
				$('#barCode1').barcode(data.args.logistics_no, "code128",{barWidth:2,barHeight:30,showHRI:false});
				$('#barCode2').barcode(data.args.logistics_no, "code128",{barWidth:2,barHeight:30,showHRI:false});
				$('#barCodeNumber1').text(data.args.logistics_no);
				$('#barCodeNumber2').text(data.args.logistics_no);
				$('#destination').text(data.args.send_target_addr);
				$('#contactName1').text(data.args.contact_name);
				$('#contactTel1').text(data.args.contact_tel);
				$('#contactAddress1').text(data.args.contact_address);
				$('#contactName2').text(data.args.contact_name);
				$('#contactTel2').text(data.args.contact_tel);
				$('#contactAddress2').text(data.args.contact_address);
				$('#sendName1').text(data.args.send_name);
				$('#sendTel1').text(data.args.real_phone);
				$('#sendAddr1').text(data.args.send_addr);
				$('#sendName2').text(data.args.send_name);
				$('#sendTel2').text(data.args.real_phone);
				$('#sendAddr2').text(data.args.send_addr);

				$('#monthlyAccount').text(data.args.month_account);
				$('#declaredValue').text(INPUT_UTIL.isNull(data.args.insure_charge)?"":(data.args.insure_charge/100+"元"));
				$('#mailGoods1').text(data.args.goods_name);
				//获取当前月份和号数
				getDate();
				$('#sendDate').text(month+"月"+today+"日");
				//添加自定义区信息
				var addli = "";
				if(data.args.goods_name != null && data.args.goods_name != ""){
					addli = addli + "<li><span>托寄物:</span><span id='itemType'>"+data.args.goods_name+"</span></li>";
				}
				if(data.args.order_no != null && data.args.order_no != ""){
					addli = addli + "<li><span>订单号:</span><span id='orderNum'>"+data.args.order_no+"</span></li>";
				}
				if(data.args.note != null && data.args.note != ""){
					addli = addli + "<li><span>备注:</span><span id='note'>"+data.args.note+"</span></li>";
				}
				$('#customArea').append(addli);

				if(INPUT_UTIL.isNull(data.args.cod_charge)){
					$('#collectionOnDelivery').hide();
				}else{
					$('#creditCardNumber').text(data.args.cod_account);
					$('#collectionAmount').text(data.args.cod_charge/100);
					$('#collectionOnDelivery').show();
					$('#cod').show();
				}

				if(data.args.need_return_tracking_no=="1"){
					$('#returnLogisNo').text(data.args.return_tracking_no);
					$('#reBarCode1').barcode(data.args.return_tracking_no, "code128",{barWidth:2,barHeight:30,showHRI:false});
					$('#reBarCode2').barcode(data.args.return_tracking_no, "code128",{barWidth:2,barHeight:30,showHRI:false});
					$('#reBarCodeNumber1').text(data.args.return_tracking_no);
					$('#reBarCodeNumber2').text(data.args.return_tracking_no);
					$('#parentLogisNo').text(data.args.logistics_no);
					$('#reDestination').text(data.args.send_origin_addr);
					$('#reContactName1').text(data.args.send_name);
					$('#reContactTel1').text(data.args.real_phone);
					$('#reContactAddress1').text(data.args.send_addr);
					$('#reContactName2').text(data.args.send_name);
					$('#reContactTel2').text(data.args.real_phone);
					$('#reContactAddress2').text(data.args.send_addr);
					$('#reSendName1').text(data.args.contact_name);
					$('#reSendTel1').text(data.args.contact_tel);
					$('#reSendAddr1').text(data.args.contact_address);
					$('#reSendName2').text(data.args.contact_name);
					$('#reSendTel2').text(data.args.contact_tel);
					$('#reSendAddr2').text(data.args.contact_address);
					$('#waybill2').show();
				}
			}
			else {
				alert("查询免填单信息失败!");
			}
		},
		complete:function(){
			$.loading.hide();
		},
		error: function(data) {
			alert("查询免填单信息失败!");
		}
	});
}

