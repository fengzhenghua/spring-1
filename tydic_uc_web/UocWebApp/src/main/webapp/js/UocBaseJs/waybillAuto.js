/*
 * 自动打印说明文档
 * 注：IE支持无打印页面和打印预览弹出、自动打印、返回打印结果；其他浏览器支持无打印页面弹出、仅弹出打印预览手动点击打印
 *
 * 第一步：引入打印JS文件
 * 		<script type="text/javascript" src="../../js/common/printer.js"></script>
 *
 * 第二步：调用方法
 * 		// 测试数据
 * 		// 自动打印页面
 * 		var page = 'waybillAuto.jsp';
 * 		// 一页顺丰单
 * 		var param = 'jsession_id=1969534A6138FA84E113E1EEA100624D&order_no=3831170401000124282&rest_host=http://localhost:8080/uoc_rest/&cod_charge=&need_return_tracking_no=';
 * 		// 两页顺丰单
 * 		// var param = 'jsession_id=6A6FD450C6EC4A29FCE28D286CDFFC16&order_no=3831170405000124291&rest_host=http://localhost:8080/uoc_rest/&cod_charge=&need_return_tracking_no=1';
 *
 * 		// 调用方法
 * 		$.printer.autoPrint({page:page, param:param}, function(args) {
 * 			alert((args.type == true) + '  ' + args.content); // 输出打印结果
 * 		});
 */

var month = "";
var today = "";
var jsession_id = "";
var order_no = "";
var rest_host = "";
var cod_charge = "";
var need_return_tracking_no = "";
var prod_name = "";
var acc_nbr = "";
var iccid = "";

$(document).ready(function() {
	if ($UTIL.browser.isIE) {
		$('#monthlyAccount, #returnLogisNo').css('font-size', '7pt');
	}

	jsession_id = HTML_UTLS.getParam("jsession_id");
	order_no = HTML_UTLS.getParam("order_no");
	rest_host = HTML_UTLS.getParam("rest_host");
	cod_charge = HTML_UTLS.getParam("cod_charge");
	prod_name = HTML_UTLS.getParam("prod_name");
	acc_nbr = HTML_UTLS.getParam("acc_nbr");
	iccid = HTML_UTLS.getParam("iccid");

	if(prod_name.length>9){
		prod_name=prod_name.substring(0, 9);
	}
	$('#prodInfo').text(prod_name+':'+acc_nbr+",ICCID:"+iccid);
	$('#page1ProdInfo').text(prod_name+':'+acc_nbr+",ICCID:"+iccid);
	if (!INPUT_UTIL.isNull(cod_charge)) {
		$('#cod').show();
	}
	need_return_tracking_no = HTML_UTLS.getParam("need_return_tracking_no");
	if (need_return_tracking_no == "1") {
		$('#page2ProdInfo').text(prod_name+':'+acc_nbr+",ICCID:"+iccid);
		$('#page2').show();
	}
	getData();
});

// 获取时间
function getDate() {
	var date = new Date;
	month = date.getMonth() + 1;
	today = date.getDate();
	month =(month < 10 ? "0" + month : month);
	today =(today < 10 ? "0" + today : today);
}

// 获取数据
function getData() {
	var data = {
		jsession_id: jsession_id,
		serv_order_no: order_no
	};
	$.ajax({
		type: "post",
		url: rest_host + "rest/expressService/getInfoForSF",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: false,
		data: data,
		beforeSend: function() {
			$.loading.show("正在处理");
		},
		dataType: "json",
		crossDomain: true == !(document.all),
		success: function(data) {
			if (data.respCode == "0000") {
				$('#barCode1').barcode(data.args.logistics_no, "code128", {barWidth:2, barHeight:30, showHRI:false});
				$('#barCode2').barcode(data.args.logistics_no, "code128", {barWidth:2, barHeight:30, showHRI:false});
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
				$('#declaredValue').text(INPUT_UTIL.isNull(data.args.insure_charge) ? "" : (data.args.insure_charge / 100 + "元"));
				$('#mailGoods1').text(data.args.goods_name);
				//获取当前月份和号数
				getDate();
				$('#sendDate').text(month + "月" + today + "日");
				//添加自定义区信息
				var addli = "";
				if (data.args.goods_name != null && data.args.goods_name != "") {
					addli = addli + "<li><span>托寄物:</span><span id='itemType'>" + data.args.goods_name + "</span></li>";
				}
				if (data.args.order_no != null && data.args.order_no != "") {
					addli = addli + "<li><span>订单号:</span><span id='orderNum'>" + data.args.order_no + "</span></li>";
				}
				if (data.args.note != null && data.args.note != "") {
					addli = addli + "<li><span>备注:</span><span id='note'>" + data.args.note + "</span></li>";
				}
				$('#customArea').append(addli);

				if (INPUT_UTIL.isNull(data.args.cod_charge)) {
					$('#collectionOnDelivery').hide();
				} else {
					$('#creditCardNumber').text(data.args.cod_account);
					$('#collectionAmount').text(data.args.cod_charge / 100);
					$('#collectionOnDelivery').show();
					$('#cod').show();
				}

				if (data.args.need_return_tracking_no == "1") {
					$('#returnLogisNo').text(data.args.return_tracking_no);
					$('#reBarCode1').barcode(data.args.return_tracking_no, "code128",{barWidth:2, barHeight:30, showHRI:false});
					$('#reBarCode2').barcode(data.args.return_tracking_no, "code128",{barWidth:2, barHeight:30, showHRI:false});
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
					$('#page2').show();
				}

				// 启动打印功能
				autoPrint();
			} else {
				if ($UTIL.browser.isIE) {
					// 返回打印结果
					window.parent.$.printer.autoPrintCallback({type:false, content:'查询免填单信息失败!'});
				} else {
					alert("查询免填单信息失败!");
				}
			}
		},
		complete: function() {
			$.loading.hide();
		},
		error: function(data) {
			if ($UTIL.browser.isIE) {
				// 返回打印结果
				window.parent.$.printer.autoPrintCallback({type:false, content:'查询免填单信息失败!'});
			} else {
				alert("查询免填单信息失败!");
			}
		}
	});
}

// 自动打印/打印预览
function autoPrint() {
	if ($UTIL.browser.isIE) {
		var isSuccess = false;
		// 直接打印
		var myDoc = {
			onPagePrinted: function(i, size) { // i当前打印页 size打印总页数
				if (i + 1 == size) {
					isSuccess = true;
				}else{
					isSuccess = false;
				}
			},
			documents: document, // 要打印的div对象在当前文档document中
			settings:{
				paperWidth: 1000, // 指定纸张的高宽以十分之一毫米为单位
				paperHeight: 1500,
				topMargin: 0,
				leftMargin: 0,
				rightMargin: 0,
				bottomMargin: 0
			},
			copyrights: '杰创软件拥有版权  www.jatools.com' // 版权声明,必须
		};

		isSuccess = jatoolsPrinter.print(myDoc, false);

		// 返回打印结果
		window.parent.$.printer.autoPrintCallback({type:isSuccess==true, content:'打印完成!'});
	} else {
		// 打印预览
		$('#print_area').printArea();
	}
}
