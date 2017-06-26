//var apc_rest_host="http://localhost:8080/apc_rest/";
var oper_no;
var ap_id;
var order_id;
var selected_number="";
var tele_type;
var oper_code="";
var code_area_list;
var code_area_p;
var code_area_c;
var code_area_a;
var pay_type="20";
var dev_id="";
var dev_name="";
var serv_order_no="";
var sale_order_no="";
//要缓存所选的发展人
var select_dev_id="";
var select_dev_name="";
//保存到属性表,Y表示创建中台订单成功
var uoc_order_success="N";
var developer_list="";


//发货方式标志，2表示货到付款，直接在该页面进行中台订单创建，不进行支付
var send_id="1";
//从首页触点信息中取oper_code缓存
var oper_code_apc="";
//号码
var list;
var pageNums = 9;
var page_current ,page_total;
var totalRows;
//发展人
var dev_list;
var dev_pageNums = 9;
var dev_page_current ,page_total;
var dev_totalRows;
var gztInfo={
		"addr":"",
		"birthday":"",
		"certId":"",
		"certName":"",
		"nation":"",
		"sex":""
};
var province_code="";
//父节点为城市
var addr_a;
var dev_info;
//可选靓号标识
var good_num_flag;
//号码预存金额
var advancePay = "";
//收货区域标识：0本省、1全国
var receive_area_flag="";
//即时激励发展人
var reward_oper_no="";
//触点类型
var ap_type="";
//费用数组
var fee_info;
var uoc_total_fee="";

var cod_bank_no="";
var wxPublicQrCode="";

$(document).ready(function () {
	oper_no = CACHE_UTIL.getSessionItem("oper_no");
	ap_id = CACHE_UTIL.getSessionItem("ap_id");
	tele_type = CACHE_UTIL.getSessionItem("tele_type");
	order_id = CACHE_UTIL.getSessionItem("order_id");
	developer_list = CACHE_UTIL.getSessionItem("developer_list");
	oper_code_apc=CACHE_UTIL.getSessionItem("oper_code");
	good_num_flag=CACHE_UTIL.getSessionItem("good_num_flag");
	receive_area_flag=CACHE_UTIL.getSessionItem("receive_area_flag");
	reward_oper_no=CACHE_UTIL.getSessionItem("reward_oper_no");
	ap_type=CACHE_UTIL.getSessionItem("ap_type");
	fee_info=CACHE_UTIL.getSessionItem("fee_info");
	uoc_total_fee=CACHE_UTIL.getSessionItem("uoc_total_fee");
	//刚加载页面就显示一个默认的已选发展人
	dev_list=JSON.parse(developer_list);
	select_dev_id=dev_list[0].developer_no;
	select_dev_name=dev_list[0].developer_name;
	$("#selected_developer").html(select_dev_name);

	//发货方式
	sendTypeShow();
	//获取省份编码
	queryProvinceCode();
	//根据触点id取属性
	queryApAttrByRedis();

	//勾选或者取消勾选入网服务协议
	$("#check_contract").click(function(){
		if ($("#check_contract").attr("class").indexOf("icon-add") > -1  ){ //本来是勾选状态的，变取消勾选
			$("#check_contract").removeClass("icon-add");
			$("#check_contract").addClass("icon-point");
			$("#next_flow_step").addClass("next_disabled");
		}
		else {
			$("#check_contract").removeClass("icon-point");
			$("#check_contract").addClass("icon-add");
			$("#next_flow_step").removeClass("next_disabled");
		}
	});

	//选择发货方式
	$(".sendType").unbind("click").bind("click",function(e){
		var $this = $(this);
		selectSendWay($this);
	});

	//选号
	$("#selectNum").unbind("click").bind("click", popNumber);
	//关闭号码选择框
	$("#hideNumber").unbind("click").bind("click", hideNumber);
	//查询号码
	$("#searchData").unbind("click").bind("click", selectNumber);
	//号码分页显示，左翻页
	$("#num_btn_ahead").unbind("click").bind("click", turnLeft);
	//号码分页显示，右翻页
	$("#num_btn_behind").unbind("click").bind("click", turnRight);

	/**
	 * 打开协议页面查看内容
	 */
	$("#btn_service_protocol").click(function(){
		$("#service_protocol_txt").show();

		$("#main-page").hide();

	});

	//点击完成按钮
	$("#next_flow_step").unbind("click").bind("click", confirmOrder);

	/**
	 * 点击所在地区进行选择具体地区
	 */
	$("#areaCode").unbind("click").bind("click", function(){
		if(receive_area_flag=="1"){
			showCodeArea("province","86");
		}else{
			code_area_p=province_code;
			showCodeArea("city",province_code);
		}
	});

	//选择地址弹出页面中的返回按钮
	$("#address_choose_exit").unbind("click").bind("click", function(){
		DIALOG_UTIL.hideDialog("address_choose_dialog");
	});

	//协议弹出框的返回按钮
	$("#protocol_back").unbind("click").bind("click", function () {

		$("#service_protocol_txt").hide();

		$("#main-page").show();
	});



	jQuery.jqtab = function(tabtit,tabcon) {
		if(receive_area_flag=="1"){
			$(tabtit+" li:first").addClass("thistab")
		}else{
			$(tabtit+" li:first").hide();
			$(tabtit+" li:first").next().addClass("thistab");
		}
		//$(tabtit+" li:first").hide();
		$("#tab1").hide();
		//$(tabtit+" li:first").next().addClass("thistab")
		$("#tab2").show();

		$(tabtit+" li").unbind("click").bind("click", function () {
			$(tabtit+" li").removeClass("thistab");
			$(this).addClass("thistab");

			$(tabcon).hide();
			var activeTab = $(this).find("a").attr("tab");
			$("#"+activeTab).show();
			return false;
		});

	};
	/*调用方法如下：*/
	$.jqtab("#tabs",".tab_con");






});


/*********************************号码相关 start****************************************/
/**
 * 号码查询
 */
function selectNumber() {
	if ($("#number_content").html()=="请稍侯。。。") {
		return;
	}

	var inputText = $("#searchNumberformat").val();
	if (inputText!=null&&inputText.length>6) {
		DIALOG_UTIL.showTypeDialog("warring", "最多输入6位！");
		return;
	}
	$("#number_content").html("请稍侯。。。");
	$("#page_current").html("0");
	$("#page_total").html("0");

	var dataJson={
			oper_no     : oper_no,
			fuzzy_query : inputText,
			page_info   : "autoTerminal",
			tele_type   : tele_type,
			ser_type    : "1",
			good_num_flag:good_num_flag
	};

	DIALOG_UTIL.showTypeDialog("loading", "");

	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoNumberServiceRest/oppoSelectNumber",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:dataJson,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			//$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				list = data.args.number_list;
				if (list=="" || list==null || list.length == 0) {
					$("#number_content").html("");
					DIALOG_UTIL.showTypeDialog("warring", "没有符合条件的号码！");
					return;
				}
				initPage();
				numberPageData();

				DIALOG_UTIL.showDialog("number_pop", true);
			} else {
				$("#number_content").html("");
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			$("#number_content").html("");
			DIALOG_UTIL.showTypeDialog("error", "查询号码Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}


/**
 * 号码预占
 * @param number
 */
function numberOccupy(number) {
	var dataJson={
			"oper_no"      : oper_no,
			"acc_nbr"      : number,
			"ser_type"     : "1",
			"tele_type"    : tele_type,
			"occupiedFlag" : "1"  //号码状态标识：0 不预占；1 预占；2 预订（未付费）；3 预订（付费）；4 释放资源
	};
	DIALOG_UTIL.showTypeDialog("loading", "");

	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoNumberServiceRest/oppoOccupyNumber",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:dataJson,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			//$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				selected_number = number;
				$("#selected_number").html(number);
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "预占号码Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}

/**
 * 初始化支持号码分页，总页数、总条数
 */
function initPage() {
	totalRows = list.length;
	page_total = parseInt(totalRows/pageNums)+(parseInt(totalRows%pageNums)>0?1:0);
	$("#page_total").html(page_total);

	page_current = 1;
}

/**
 * 号码分页显示
 */
function numberPageData() {
	$("#number_content").html("");

	var startRow = (page_current-1)*pageNums;
	var endRow = startRow+pageNums;
	var result = '';
	for (var i=startRow;i<endRow&&i<totalRows;i++) {
		var start ='';
		var end ='';
		var middle='';
		if((i+1)%3==1){
			start='<div class="number_box">';
		}
		if((i+1)%3==0 || (i+1)==totalRows){
			end = '</div>';
		}
		//靓号样式
		if(list[i].class_id!="9"){
			middle += '<div style="position:relative;" class="number_box_row" style="cursor:pointer;" advancePay=\'' + list[i].acc_nbr_fee + '\' id=\'' + list[i].acc_nbr + '\'><i style="display:inline-block;position:absolute;top:0;left:0;width:22px;height:22px;"><img width="100%" height="100%" src="../../images/mobile/oppoCard/liang.png" style="margin:0 auto;"></i><div style="line-height:30px;font-size:14px;">' + list[i].acc_nbr + '</div><div style="width:100%;font-size:12px;line-height:11px;text-align:left;color:#959595;">预存'+list[i].acc_nbr_fee+'元,月消费'+list[i].low_fee+'元</div></div>';
		}else{
			middle +=  '<div class="number_box_row" style="cursor:pointer;" advancePay=\'' + list[i].acc_nbr_fee + '\' id=\'' + list[i].acc_nbr + '\'>' + list[i].acc_nbr + '</div>';
		}
		result += start + middle + end;
		//result += start + '<div class="number_box_row" style="cursor:pointer;" id=\'' + list[i].acc_nbr + '\'>' + list[i].acc_nbr + '</div>' + end;
	}
	/*靓号样式
	 * for (var i=startRow;i<endRow&&i<totalRows;i++) {
		var start ='';
		var end ='';
		var middle = '';
		if((i+1)%3==1){
			start='<div class="number_box">';
		}
		if((i+1)%3==0 || (i+1)==totalRows){
			end = '</div>';
		}
		if((i+1) <= 3){
			middle += '<div style="position:relative;" class="number_box_row" style="cursor:pointer;" id=\'' + list[i].acc_nbr + '\'><i style="display:inline-block;position:absolute;top:0;left:0;width:22px;height:22px;"><img width="100%" height="100%" src="../../images/mobile/oppoCard/liang.png" style="margin:0 auto;"></i><div style="line-height:30px;font-size:14px;">' + list[i].acc_nbr + '</div><div style="width:100%;font-size:12px;line-height:11px;text-align:left;color:#959595;">预存xxx元,月消费xxx元</div></div>';
		}else{
			middle +=  '<div class="number_box_row" style="cursor:pointer;" id=\'' + list[i].acc_nbr + '\'>' + list[i].acc_nbr + '</div>';
		}
		result += start + middle + end;
	}*/
	$("#number_content").html(result);

	$("#page_current").html(page_current);

	selectClick();

}


/**
 * 号码左翻页
 */
function turnLeft() {
	if (page_current<=1) {
		return;
	}
	page_current--;
	numberPageData();
}


/**
 * 号码右翻页
 */
function turnRight() {
	if (page_current>=page_total) {
		return;
	}
	page_current++;
	numberPageData();
}


/**
 * 点击一个号码，进行预占
 */
function selectClick() {
	$("#number_content .number_box_row").unbind("click").bind("click", function (e) {
		hideNumber();
		numberOccupy($(this).attr("id"));
		advancePay = $(this).attr("advancePay");
	});
}


/**
 * 打开号码选择页面框
 */
function popNumber() {
	$("#searchNumberformat").val("");
	if (selected_number=="") {
		selectNumber();
	}
	else{
		DIALOG_UTIL.showDialog("number_pop", true);
	}
};

/**
 * 关闭号码选择页面框
 */
function hideNumber() {
	DIALOG_UTIL.hideDialog("number_pop");
};

/**************************************号码相关 end**************************************/


/**
 * 国政通校验
 */
function checkGZT(){
	var certId = $("#id_number").val();
	var certName = $("#idName").val();
	var flag="1";
	var dataJson={
			"certId":certId,
			"certName":certName,
			"flag":flag//是否需要校验标志，1  需要   0不需要
	};
	DIALOG_UTIL.showTypeDialog("loading", "");

	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoCheckServiceRest/checkGZT",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:dataJson,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			//$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				if(flag=="0"){
					//货到付款直接先创建中台订单，后面不需要支付
					if(send_id=="2"){
						createUocOrder();
					}else{
						//更新订单属性表
						updateOrderAttr();
					}
				}else if(flag=="1"){
					var custInfo=JSON.parse(data.args.json_info).json_info.rspmsg;
					if(custInfo.code!=null){
						DIALOG_UTIL.showTypeDialog("error", custInfo.detail);
					}else{
						gztInfo.addr=custInfo.addr;
						gztInfo.birthday=custInfo.birthday;
						gztInfo.certId=custInfo.certId;
						gztInfo.certName=custInfo.certName;
						gztInfo.nation=custInfo.nation;
						gztInfo.sex=custInfo.sex;
						//货到付款直接先创建中台订单，后面不需要支付
						if(send_id=="2"){
							createUocOrder();
						}else{
							//国政通校验通过，取到客户身份证相关信息缓存
							CACHE_UTIL.setSessionItem("addr", custInfo.addr);
							CACHE_UTIL.setSessionItem("birthday", custInfo.birthday);
							CACHE_UTIL.setSessionItem("certId", custInfo.certId);
							CACHE_UTIL.setSessionItem("certName", custInfo.certName);
							CACHE_UTIL.setSessionItem("nation", custInfo.nation);
							CACHE_UTIL.setSessionItem("sex", custInfo.sex);
							//更新订单属性表
							updateOrderAttr();
						}

					}
				}
			} else {
				DIALOG_UTIL.hideDialog("", "loading");
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.hideDialog("", "loading");
			DIALOG_UTIL.showTypeDialog("error", "国政通校验Ajax请求失败!");
		},
		complete:function(){
			//DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}

/**
 * 黑名单一证五户校验
 */
function custCheck(){
	var certId = $("#id_number").val();
	var certName = $("#idName").val();
	var flag="1";
	var dataJson={
			"oper_no" : oper_no,
			"tele_type":tele_type,
			"id_number":certId,
			"id_type":"02",
			"customer_name":certName,
			"device_number":"",
			"checkType":"0",
			"flag":flag//是否需要校验的标志 1需要校验  0不需要校验
	};
	DIALOG_UTIL.showTypeDialog("loading", "");

	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoCheckServiceRest/checkCustInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:dataJson,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			//$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				if(flag=="0"){
					//进行国政通校验
					checkGZT();
				}else if(flag=="1"){
					var rsp_info=JSON.parse(data.args.json_info);
					if(rsp_info.code!="200"){
						if(rsp_info.code=="0001"){
							//没有客户资料，新客户，通过
							//进行国政通校验
							checkGZT();
						}else{
							DIALOG_UTIL.showTypeDialog("error", rsp_info.json_info.detail);
						}
					}else{
						//进行国政通校验
						checkGZT();
					}
				}

			} else {
				DIALOG_UTIL.hideDialog("", "loading");
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.hideDialog("", "loading");
			DIALOG_UTIL.showTypeDialog("error", "校验Ajax请求失败!");
		},
		complete:function(){
			//DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}




function confirmOrder() {

	if ($("#next_flow_step").attr("class").indexOf("next_disabled") > -1  ){
		//DIALOG_UTIL.showTypeDialog("error","请先阅读并同意《客户入网服务协议》！");
		return;
	}

	var customer_name=$("#idName").val();
	if(isNull(customer_name)){
		DIALOG_UTIL.showTypeDialog("error","请输入姓名！");
		return;
	}

	var id_number=$("#id_number").val();
	if(!checkIdcard(id_number)){
		DIALOG_UTIL.showTypeDialog("error","请输入正确的身份证号码！");
		return;
	}

	var contact_phone=$("#connect_phone").val();
	if(isNull(contact_phone)){
		DIALOG_UTIL.showTypeDialog("error","请输入联系电话！");
		return;
	}

	var areaCode = $("#areaCode").html();
	if(isNull(areaCode)){
		DIALOG_UTIL.showTypeDialog("error","请选择所在地区！");
		return;
	}

	if(isNull(code_area_a)||code_area_a==undefined){
		DIALOG_UTIL.showTypeDialog("error","请选择城区！！！");
		return;
	}

	var connect_addr = $("#connect_addr").val();
	if(isNull(connect_addr)){
		DIALOG_UTIL.showTypeDialog("error","请输入配送地址！");
		return;
	}else{
		if(connect_addr.length<8){
			DIALOG_UTIL.showTypeDialog("error","详细地址至少要输入8个字！");
			return;
		}
	}


	if(isNull(selected_number)){
		DIALOG_UTIL.showTypeDialog("error","请先选择一个号码");
		return;
	}

	if(isNull(send_id)){
		DIALOG_UTIL.showTypeDialog("error","请先选择发货方式");
		return;
	}

	//缓存数据
	CACHE_UTIL.setSessionItem("customer_name", customer_name);
	CACHE_UTIL.setSessionItem("id_type", "02");
	CACHE_UTIL.setSessionItem("id_number", id_number);
	CACHE_UTIL.setSessionItem("contact_phone", contact_phone);
	CACHE_UTIL.setSessionItem("contact_adress", $("#areaCode").text()+connect_addr);
	CACHE_UTIL.setSessionItem("receive_name", customer_name);
	CACHE_UTIL.setSessionItem("receive_phone", contact_phone);
	CACHE_UTIL.setSessionItem("receive_address", connect_addr);
	CACHE_UTIL.setSessionItem("acc_nbr", selected_number);
	CACHE_UTIL.setSessionItem("selected_number", selected_number);
	CACHE_UTIL.setSessionItem("concard_flag", $("#concard_flag").attr("value"));
	CACHE_UTIL.setSessionItem("receive_province", code_area_p);
	CACHE_UTIL.setSessionItem("receive_city", code_area_c);
	CACHE_UTIL.setSessionItem("receive_area", code_area_a);
	//CACHE_UTIL.setSessionItem("oper_code", oper_code);
	CACHE_UTIL.setSessionItem("dev_id", select_dev_id);
	CACHE_UTIL.setSessionItem("dev_name", select_dev_name);
	CACHE_UTIL.setSessionItem("advancePay", advancePay);

	//进行客户资料校验
	checkCertNoNums();
	//queryUserNumber();

}

/**
 * 选择发货方式
 * @param e
 */
function selectSendWay(e){
	oper_code=e.attr("code_id");
	if(oper_code=="openHn01"){
		e.siblings().removeClass('icon-add');
		e.addClass('icon-add');
		$(".area_page").show();
	}else{
		//目前暂时不支持自提
		return;
		$(".area_page").hide();
	}

	send_id=e.attr("send_id");
	//决定支付方式
	if(send_id=="1"){
		pay_type="20";
	}else if(send_id=="2"){
		pay_type="10";
	}
}


/**
 * 获取省份
 */
function queryProvinceCode(){
	DIALOG_UTIL.showTypeDialog("loading", "");

	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoCommonServiceRest/getProvinceCode",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:{},
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			//$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				province_code=data.args.province_code_value;//编码
				var province=data.args.province_code;//英文缩写
				if(province=="cq"){
					$("#gui_shu").html("重庆 重庆市");
					$("#addr_name_p").val("重庆");
					genAddr();
				}else if(province=="hn"){
					$("#gui_shu").html("海南 海口市");
					$("#addr_name_p").val("海南省");
					genAddr();
				}
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "查询省份编码Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}


/**
 * 地区选择
 */
function showCodeArea(flag,parent_id){
	if(receive_area_flag=="1"){
		$(".tabs li").removeClass("thistab");
		$(".tabs li:first").addClass("thistab");
	}else{
		$(".tabs li").removeClass("thistab");
		$(".tabs li:first").next().addClass("thistab");
	}
	var dataJson={
			"parent_area_code": parent_id
	};

	DIALOG_UTIL.showTypeDialog("loading", "");

	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoCommonServiceRest/getAreaInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:dataJson,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			//$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				code_area_list = data.args.area_info;

				if(flag=="city"){
					//查询城市
					var pstr = "";
					for(var i=0; i<code_area_list.length; i++){
						var codeArea = code_area_list[i];
						pstr += '{"code_id":"'+code_area_list[i].area_code+'","code_name":"'+code_area_list[i].area_name+'","parent_code":"'+code_area_list[i].parent_area_code+'"},';
					}
					pstr = pstr.substring(0,pstr.length-1);
					pstr = '{"addr_list":['+pstr+']}';
					console.info(pstr);
					addr_c = JSON.parse(pstr);

					genData(parent_id,addr_c,"tab2");

					if(receive_area_flag=="1"){
						//选中
						$(".tabs li").removeClass("thistab");
						$(".tabs li:first").next().addClass("thistab");
					}
					
					//记录名称 2016-12-21
					$("#tab2 .tabs_data").unbind("click").bind("click", function () {
						$("#addr_name_c").val($(this).html());
						code_area_c = JSON.parse($(this).attr("data")).code_id;
						genAddr();
					});
					
					$(".tab_con").hide();
					$("#tab2").show();

				}else if(flag=="area"){
					//清空
					$("#tab3").html("");
					//查询城区
					var pstr = "";
					for(var i=0; i<code_area_list.length; i++){
						var codeArea = code_area_list[i];
						pstr += '{"code_id":"'+code_area_list[i].area_code+'","code_name":"'+code_area_list[i].area_name+'","parent_code":"'+code_area_list[i].parent_area_code+'"},';
					}
					pstr = pstr.substring(0,pstr.length-1);
					pstr = '{"addr_list":['+pstr+']}';
					console.info(pstr);
					addr_a = JSON.parse(pstr);

					genData(parent_id,addr_a,"tab3");

					//选中
					$(".tabs li").removeClass("thistab");
					$(".tabs li:last").addClass("thistab");

					//记录名称
					$("#tab3 .tabs_data").unbind("click").bind("click", function () {
						$("#addr_name_a").val($(this).html());
						code_area_a = JSON.parse($(this).attr("data")).code_id;
						genAddr();
						//返回
						returnBack();
					});

					$(".tab_con").hide();
					$("#tab3").show();
				}else if(flag=="province"){
					//查询省份
					var pstr = "";
					for(var i=0; i<code_area_list.length; i++){
						var codeArea = code_area_list[i];
						pstr += '{"code_id":"'+code_area_list[i].area_code+'","code_name":"'+code_area_list[i].area_name+'","parent_code":"'+code_area_list[i].parent_area_code+'"},';
					}
					pstr = pstr.substring(0,pstr.length-1);
					pstr = '{"addr_list":['+pstr+']}';
					console.info(pstr);
					var addr_p = JSON.parse(pstr);

					genData(parent_id,addr_p,"tab1");
					
					//记录名称 2016-12-21
					$("#tab1 .tabs_data").unbind("click").bind("click", function () {
						$("#addr_name_p").val($(this).html());
						code_area_p = JSON.parse($(this).attr("data")).code_id;
						genAddr();
					});
					
					$(".tab_con").hide();
					$("#tab1").show();
				}

				$("#addr_choice").css("line-height","30px");


				DIALOG_UTIL.showDialog("address_choose_dialog", true);
				//dj
				$('#address_choose_dialog').css('position', 'fixed');
				$('#address_choose_dialog').css('top', '0px');
				$('#address_choose_dialog').css('right', '0px');
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "查询区域Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}


/**
 * 拼成地址串
 */
function genAddr() {
	$("#addr_choice").html("");
	$("#addr_choice").html($("#addr_name_p").val() + $("#addr_name_c").val() + $("#addr_name_a").val());
	$("#areaCode").html($("#addr_choice").html());//结果写回页面
}


/**
 *  生成数据
 */
function genData(ParentId,dataJson,targetId) {
	if ($("#"+targetId).length <= 0) {
		console.info("genData无效入参targetId=="+targetId);
	}

	//清空
	$("#"+targetId).html("");

	//拼数据
	var tab_list="<div class='tab_list'>";
	var tabs_data="";
	for (i = 0; i < dataJson.addr_list.length; i++) {
		if(ParentId == dataJson.addr_list[i].parent_code) {
			tabs_data = tabs_data + "<div class='tabs_data' data=" + JSON.stringify(dataJson.addr_list[i]);

			//加点击事件
			var str_click="";
			if(targetId == "tab1") {
				//str_click = " onclick=genDataCity('" + dataJson.addr_list[i].code_id + "')";
				str_click = " onclick=showCodeArea('city','" + dataJson.addr_list[i].code_id + "')";
			}
			if(targetId == "tab2") {
				str_click = " onclick=showCodeArea('area','" + dataJson.addr_list[i].code_id + "')";
			}

			tabs_data = tabs_data  + str_click;
			tabs_data = tabs_data + ">" +  dataJson.addr_list[i].code_name + "</div>";
		}
	}
	/*tab_list = tab_list + tabs_data + "</div>";*/
	var html ='<div class="tabs_data" data="" style="visibility:hidden;"></div>';
	tab_list = tab_list + tabs_data + html + "</div>";

	//插入
	$("#"+targetId).append(tab_list);
}



/**
 * 选择城市区域后返回
 */
function returnBack() {
	DIALOG_UTIL.hideDialog("address_choose_dialog");
}




/**
 * 验证手动输入的身份证号
 * @param idcard
 * @returns {Boolean}
 */
function checkIdcard(idcard){
	if (isNull(idcard)) {
		return false;
	}

	var area={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}

	var idcard,Y,JYM;
	var S,M;
	var idcard_array = new Array();
	idcard_array = idcard.split("");
	//地区检验
	if(area[parseInt(idcard.substr(0,2))]==null) {
		return false;
	}
	//身份号码位数及格式检验
	switch(idcard.length){
	case 15:
	if ( (parseInt(idcard.substr(6,2))+1900) % 4 == 0 || ((parseInt(idcard.substr(6,2))+1900) % 100 == 0 && (parseInt(idcard.substr(6,2))+1900) % 4 == 0 )){
		ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//测试出生日期的合法性
	} else {
		ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//测试出生日期的合法性
	}
	if(ereg.test(idcard)) {
		return false;
	} else {
		return false;
	}
	break;
	case 18:
	//18位身份号码检测
	//出生日期的合法性检查
	//闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
	//平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
	if ( parseInt(idcard.substr(6,4)) % 4 == 0 || (parseInt(idcard.substr(6,4)) % 100 == 0 && parseInt(idcard.substr(6,4))%4 == 0 )){
		ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//闰年出生日期的合法性正则表达式
	} else {
		ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//平年出生日期的合法性正则表达式
	}
	if(ereg.test(idcard)){//测试出生日期的合法性
		//计算校验位
		S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
		+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9
		+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10
		+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5
		+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8
		+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4
		+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2
		+ parseInt(idcard_array[7]) * 1
		+ parseInt(idcard_array[8]) * 6
		+ parseInt(idcard_array[9]) * 3 ;
		Y = S % 11;
		M = "F";
		JYM = "10X98765432";
		M = JYM.substr(Y,1);//判断校验位
		if(M == idcard_array[17]) {
			return true; //检测ID的校验位
		} else {
			return false;
		}
	} else {
		return false;
	}
	break;
	default:
		return false;
	break;
	}
}




/**
 * 更新订单属性表
 */
function updateOrderAttr(){
	DIALOG_UTIL.showTypeDialog("loading", "");
	//资费id
	var product_id=CACHE_UTIL.getSessionItem("product_id");
	//资费名称
	var product_name=CACHE_UTIL.getSessionItem("product_name");
	//费用
	var market_price=CACHE_UTIL.getSessionItem("market_price");
	var product_desc=CACHE_UTIL.getSessionItem("product_desc");
	var fee_code=CACHE_UTIL.getSessionItem("fee_code");
	var fee_class=CACHE_UTIL.getSessionItem("fee_class");
	var fee_name=CACHE_UTIL.getSessionItem("fee_name");
	var customer_name=$("#idName").val();
	var id_number=$("#id_number").val();
	var contact_phone=$("#connect_phone").val();
	var connect_addr = $("#connect_addr").val();
	var dataJson={
			"order_id":order_id,
			"rel_amount":market_price,
			"rec_amount":market_price,
			"fee_code":fee_code,
			"fee_class":fee_class,
			"fee_name":fee_name,
			"product_id":product_id,
			"product_name":product_name,
			"prepay_flag":"1",
			"tele_type":tele_type,
			"product_desc":product_desc,
			"first_month_fee":"01",//套外
			"market_price":market_price,
			"fee_all":market_price,
			"pay_type":pay_type,
			"customer_name":customer_name,
			"id_number":id_number,
			"id_type":"02",
			"contact_phone":contact_phone,
			"contact_adress":$("#areaCode").text()+connect_addr,
			"receive_name":customer_name,
			"receive_phone":contact_phone,
			"receive_address":$("#areaCode").text()+connect_addr,
			"acc_nbr":selected_number,
			"receive_province":code_area_p,
			"receive_city":code_area_c,
			"receive_area":code_area_a,
			"pay_flag":"货到付款未支付",
			"serv_order_no":serv_order_no,
			"sale_order_no":sale_order_no,
			"uoc_order_success":uoc_order_success,
			"sim_id":"N"
	};
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoOrderServiceRest/updateOppoOrderInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:dataJson,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			//$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				//属于货到付款的申请流程至此完成，不需要在触点支付
				if(send_id=="2"){
					$("#next_flow_step").unbind("click");
					$("#next_flow_step").attr("class","next_disabled next_blank");
					$("#next_flow_step").hide();
					//显示关注的公众号二维码
					$("#order_success_tip").html("下单成功，电话卡将在订单信息审核完成后寄出。");
					$("#PUBLIC_QR").attr('src',(wxPublicQrCode)); //二维码图片
					DIALOG_UTIL.showDialog("qr_code_pop", true);
					$("#qr_code_pop").css("top","15px");
					//DIALOG_UTIL.showTypeDialog("info", "电话卡将在订单信息审核完成后寄出，感谢使用！");
				}else{
					//更新后跳转到支付页面
					window.location.href = "pay.html";
				}
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "更新订单属性表Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}


/**
 * 在中台创建订单
 */
function createUocOrder() {
	var product_id=CACHE_UTIL.getSessionItem("product_id");
	var market_price=CACHE_UTIL.getSessionItem("market_price");
	var connect_addr = $("#connect_addr").val();
	var cod_charge=parseFloat(market_price)*100+parseFloat(advancePay)*100;
	var activity_id=CACHE_UTIL.getSessionItem("activity_id");
	
	var goodNumFee=parseFloat(advancePay)*100;
	var feeInfo=JSON.parse(fee_info);
	var total_fee=parseFloat(uoc_total_fee)*100;
	if(goodNumFee>0){
		total_fee=total_fee+goodNumFee;
		for(var i=0;i<feeInfo.length;i++){
			if(feeInfo[i].fee_id=="100000"){
				feeInfo[i].orig_fee=parseFloat(feeInfo[i].orig_fee)+goodNumFee+"";
				feeInfo[i].real_fee=parseFloat(feeInfo[i].real_fee)+goodNumFee+"";
			}else{
				if(i==feeInfo.length-1){
					var fee_result={
							"fee_id":"100000",
							"fee_category":"2",
							"orig_fee":goodNumFee,
							"relief_fee":0,
							"relief_result":"无",
							"real_fee":goodNumFee,
							"fee_des":"营业普通预存款"
					};
					feeInfo.push(fee_result);
				}
			}
		}
	}
	
	var all_fee_info=JSON.stringify(feeInfo);
	
	var dataJson={
			"oper_no":oper_no,
		    "order_id":order_id,
		    "pay_type":pay_type,
			"serial_number":selected_number,
			"recom_person_id":select_dev_id,
			"recom_person_name":select_dev_name,
			"first_mon_bill_mode":"01",
			"product_id":product_id,
			"cert_address":gztInfo.addr,
			"cert_expire_date":"",
			"customer_name":$("#idName").val(),
			"customer_sex":gztInfo.sex,
			"cert_num":$("#id_number").val(),
			"cert_type":"02",
			"contact_address":$("#areaCode").text()+connect_addr,
			"contact_phone":$("#connect_phone").val(),
			"receive_name":$("#idName").val(),
			"receive_phone":$("#connect_phone").val(),
			"receive_province":code_area_p,
			"receive_city":code_area_c,
			"receive_area":code_area_a,
			"receive_address":connect_addr,
			"total_fee":total_fee,
			"oper_code":oper_code_apc,
			"cod_charge":cod_charge,
			"activity_id":isNullUtil("1",activity_id),
			"ap_id":ap_id,
			"reward_oper_no":isNullUtil("1",reward_oper_no),
			"ap_type":isNullUtil("1",ap_type),
			"fee_info":all_fee_info,
			"cod_bank_no":cod_bank_no
	};
	DIALOG_UTIL.showTypeDialog("loading", "");

	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoUocOrderServiceRest/createUocOrder",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:dataJson,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			//$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				serv_order_no=data.args.serv_order_no;
				sale_order_no=data.args.sale_order_no;
				uoc_order_success="Y";
	        	//更新订单属性表
	        	updateOrderAttr();
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "创建中台订单Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});

}

/**
 * 一证五户校验
 */
function queryUserNumber(){
	var certId = $("#id_number").val();
	var certName = $("#idName").val();
	var dataJson={
			"oper_no" : oper_no,
			"certId":certId,
			"certName":certName
	};
	DIALOG_UTIL.showTypeDialog("loading", "");

	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoCheckServiceRest/queryUserNumber",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:dataJson,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			//$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				if(data.args.USER_AMOUNT>=5){
					DIALOG_UTIL.showTypeDialog("warning","抱歉，此身份证已经超过五个用户，不允许再受理！");
				}else{
					custCheck();
				}
			} else {
				DIALOG_UTIL.hideDialog("", "loading");
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.hideDialog("", "loading");
			DIALOG_UTIL.showTypeDialog("error", "校验Ajax请求失败!");
		},
		complete:function(){
			//DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}

/**
 * 身份证开卡数量检验
 */
function checkCertNoNums(){
	var certId = $("#id_number").val();
	var dataJson={
			"oper_no" : oper_no,
			"ap_id": ap_id,
			"cert_no": certId
	};
	DIALOG_UTIL.showTypeDialog("loading", "");

	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoCheckServiceRest/checkCertNumsForAp",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:dataJson,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
		},
		success: function(data) {
			if (data.respCode=="0000") {
				if(data.args.checkFlag!=0){
					DIALOG_UTIL.showTypeDialog("warning","抱歉，此身份证开卡数量超过限制，不允许再受理！");
				}else{
					queryUserNumber();
				}
			} else {
				DIALOG_UTIL.hideDialog("", "loading");
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.hideDialog("", "loading");
			DIALOG_UTIL.showTypeDialog("error", "校验Ajax请求失败!");
		},
		complete:function(){
		}
	});
}

/*dj*/
$().ready(function(){
	//点击
	$('#tab2').on('click','.tabs_data',function(){
		$this = $(this);
		$this.css('color','#e60017');
		$this.siblings().css('color','#595959');

		/*$this.addClass('red');
		$this.siblings().removeClass('red').css('color','gray');*/
	});
	$('#tab3').on('click','.tabs_data',function(){
		$this = $(this);
		$this.css('color','#e60017');
		$this.siblings().css('color','#595959');
		/*$this.addClass('red');
		$this.siblings().removeClass('red').css('color','gray');*/
	});


	//动态添加选择地区样式
	$('#tab2').css('display','none');
	$('#tab3').css('display','none');
	$('#address_choose_dialog').css('max-height',$(window).height());
	$('#tab2').css('max-height',$(window).height());
	$('#tab3').css('max-height',$(window).height());
	console.log($(window).height());
});


/**
 * 显示要支持的发货方式
 */

function sendTypeShow(){
	var send_type0_flag = CACHE_UTIL.getSessionItem("send_type0_flag");
	var send_type1_flag = CACHE_UTIL.getSessionItem("send_type1_flag");
	var send_type2_flag = CACHE_UTIL.getSessionItem("send_type2_flag");
	
	if(send_type0_flag=="1"){
		$("#send_type0_flag").show();
		$(".send_type0_flag").show();
	}else{
		$("#send_type0_flag").hide();
		$(".send_type0_flag").hide();
	}
	
	if(send_type1_flag=="1"){
		$("#send_type1_flag").show();
		$(".send_type1_flag").show();
	}else{
		$("#send_type1_flag").hide();
		$(".send_type1_flag").hide();
	}
	
	if(send_type2_flag=="1"){
		$("#send_type2_flag").show();
		$(".send_type2_flag").show();
	}else{
		$("#send_type2_flag").hide();
		$(".send_type2_flag").hide();
	}
		
}


/**
 * 根据触点id从缓存中获取触点属性
 */
function queryApAttrByRedis(){
	var certId = $("#id_number").val();
	var dataJson={
			"ap_id": ap_id
	};
	DIALOG_UTIL.showTypeDialog("loading", "");
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/operateApAttrRest/queryApAttrByRedis",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:dataJson,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
		},
		success: function(data) {
			if (data.respCode=="0000") {
				cod_bank_no=isNullUtil("1",data.args.apAttrInfo.cod_bank_no);
				wxPublicQrCode=isNullUtil("1",data.args.apAttrInfo.wx_public_qr_code);
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "根据触点id获取触点属性Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}







