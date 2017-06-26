var oper_no;
var ap_id;
var order_id="";
var selected_number="";
var tele_type="4G";
var code_area_list;
var code_area_p="";
var code_area_c="";
var code_area_a="";
var pay_type="";
var dev_id="";
var dev_name="";
var serv_order_no="";
var sale_order_no="";
//保存到属性表,Y表示创建中台订单成功
var uoc_order_success="N";
var developer_list="";


//发货方式标志，2表示货到付款，直接在该页面进行中台订单创建，不进行支付
var send_id="1";
//从首页触点信息中取oper_code缓存
var oper_code="";
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
var feeInfo=[];
var total_fee=0;
var cod_charge=0;
//发货方式
var send_type0_flag = "";
var send_type1_flag = "";
var send_type2_flag = "";
var tariff_id="";
var tb_account="";
var accept_no="";
var receive_name="";
var receive_phone="";
var receive_address="";
var product_id="";
var activity_id="";
var receive_province="";
var receive_city="";
var receive_area="";
var market_price=0;
var callback_url="";
var accept_system="";
var ywb_id=[];
//关注公众号二维码，用于后续激活
var wxPublicQrCode="";
var ywb_info_list;

$(document).ready(function () {
	dealUrlPara();

	//根据URL中收货地址是否为空控制选址项
	$("#update_btn").unbind("click").bind("click",function(){
		updateAddr();
	});
	if(isNullUtil("0",receive_address)){
		$("#delivery_address").val("");
		$(".area_page").show();
		$("#delivery").hide();
		$("#addr_operate").hide();
	}else{
		$("#delivery_address").val(receive_address);
		$("#delivery").show();
		$("#addr_operate").show();
		$(".area_page").hide();
	}
	
	//获取省份编码
	queryProvinceCode();
	//取触点信息
	getApInfo();

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

	//选择地址弹出页面中的返回按钮
	$("#address_choose_exit").unbind("click").bind("click", function(){
		DIALOG_UTIL.hideDialog("address_choose_dialog");
	});

	//协议弹出框的返回按钮
	$("#protocol_back").unbind("click").bind("click", function () {

		$("#service_protocol_txt").hide();

		$("#main-page").show();
	});

});

/**
 * 处理url中参数
 */
function dealUrlPara(){
	oper_no = HTML_UTLS.getParam("oper_no");
	ap_id = HTML_UTLS.getParam("ap_id");
	dev_code = HTML_UTLS.getParam("dev_code");
	dev_name = HTML_UTLS.getParam("dev_name");
	reward_oper_no=HTML_UTLS.getParam("reward_oper_no");
	ap_type=HTML_UTLS.getParam("ap_type");
	tariff_id=HTML_UTLS.getParam("tariff_id");
	tb_account=HTML_UTLS.getParam("tb_account");
	accept_no=HTML_UTLS.getParam("accept_no");
	receive_name=HTML_UTLS.getParam("receive_name");
	receive_phone=HTML_UTLS.getParam("receive_phone");
	receive_address=HTML_UTLS.getParam("receive_address");
	$("#idName").val(isNullUtil("1",receive_name));
	$("#connect_phone").val(isNullUtil("1",receive_phone));
}

/**
 * 获取触点信息
 */
function getApInfo(){
	
	DIALOG_UTIL.showTypeDialog("loading", "");
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/ap/queryApMsg",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:{"ap_id":ap_id},
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			//$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				//判断传入资费id与配置id是否匹配
				var isExist = "no";
				//取到资费信息
				var tariffInfoList = data.args.tariff_info_list;
				//业务包处理
				ywb_info_list=data.args.ywb_info_list
				var ywbIds=HTML_UTLS.getParam("ywb_id")
				//不为空
				if(!isNullUtil("0",ywbIds)){
					var ywbInfo=JSON.parse(ywbIds);
					//是否是数组
					if(jQuery.isArray(ywbInfo)&&ywbInfo.length>0){
						for(var i=0;i<ywbInfo.length;i++){
							for(var j=0;j<ywb_info_list.length;j++){
								if(ywbInfo[i]==ywb_info_list[j].ywb_code){
									var pack_cod={
											"pack_code":ywb_info_list[j].ywb_code,
											"element_id":ywb_info_list[j].element_id,
											"element_type":ywb_info_list[j].element_type
									};
									ywb_id.push(pack_cod);
								}
							}
							
						}
					}
				}
				
				for(var i=0;i<tariffInfoList.length;i++){
					if(tariffInfoList[i].tariff_id == tariff_id){
						isExist="yes";
						//发货方式标志
						send_type0_flag = tariffInfoList[i].send_type0_flag;
						send_type1_flag = tariffInfoList[i].send_type1_flag;
						send_type2_flag = tariffInfoList[i].send_type2_flag;
						sendTypeShow();
						
						oper_code=tariffInfoList[i].oper_code;
						good_num_flag=tariffInfoList[i].good_num_flag;
						receive_area_flag=tariffInfoList[i].receive_area_flag;
						product_id=tariffInfoList[i].product_id;
						activity_id=tariffInfoList[i].activity_id;
						
						//费用相关
						var fee_list=tariffInfoList[i].fee_list;
						for(var i=0;i<fee_list.length;i++){
							market_price=market_price+parseFloat(fee_list[i].total_fee);
							cod_charge=cod_charge+parseFloat(fee_list[i].total_fee)*100;
							if(fee_list[i].fee_item_type!="apc"){
								total_fee=total_fee+parseFloat(fee_list[i].total_fee)*100;
								var fee_result={
										"fee_id":fee_list[i].fee_item_code,
										"fee_category":fee_list[i].fee_item_type,
										"orig_fee":parseFloat(fee_list[i].total_fee)*100+"",
										"relief_fee":"0.00",
										"relief_result":"无",
										"real_fee":parseFloat(fee_list[i].total_fee)*100+"",
										"fee_des":fee_list[i].fee_item_name
								};
								feeInfo.push(fee_result);
							}
						}
					}
				}
				if(isExist=="no"){
					DIALOG_UTIL.showTypeDialog("error", "传入的资费id与配置的不匹配！");
					return;
				}
				
				//如果url中取不到工号，就取接口返回的绑定工号
				if(isNullUtil("0",oper_no)){
					oper_no=data.args.ap_info.bind_oper;
					if(isNullUtil("0",oper_no)){
						DIALOG_UTIL.showTypeDialog("error", "触点信息没有配置相应工号信息！！！");
						return;
					}
				}
				
				//取到触点信息返回的可选发展人
				developer_list=data.args.developer_list;
				//判断如果触点信息返回没有发展人就页面报错
				if(developer_list!=null&&developer_list.length>0){
					dev_id=developer_list[0].developer_no;
					dev_name=developer_list[0].developer_name;
				}else{
					DIALOG_UTIL.showTypeDialog("error", "触点信息没有配置相应发展人！！！");
					return;
				}
				
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
				
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "获取触点信息Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}


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
				var payFee=total_fee+parseFloat(advancePay);
				if(flag=="0"){
					//总费用大于0并且支付类型为20的才微信支付
					if(pay_type=="20"&&payFee>0){
						updateOrderAttr();
					}else{
						createUocOrder();
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
						//总费用大于0并且支付类型为20的才微信支付
						if(pay_type=="20"&&payFee>0){
							//国政通校验通过，取到客户身份证相关信息缓存
							CACHE_UTIL.setSessionItem("addr", custInfo.addr);
							CACHE_UTIL.setSessionItem("birthday", custInfo.birthday);
							CACHE_UTIL.setSessionItem("certId", custInfo.certId);
							CACHE_UTIL.setSessionItem("certName", custInfo.certName);
							CACHE_UTIL.setSessionItem("nation", custInfo.nation);
							CACHE_UTIL.setSessionItem("sex", custInfo.sex);
							//更新订单属性表
							updateOrderAttr();
						}else{
							createUocOrder();
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
	
	var connect_addr ="";
	var connect_area ="";
	if(isNullUtil("0",$("#delivery_address").val())){
		connect_area=$("#areaCode").text();
		var areaCode = $("#areaCode").html();
		if(isNull(areaCode)){
			DIALOG_UTIL.showTypeDialog("error","请选择所在地区！");
			return;
		}

		if(isNull(code_area_a)||code_area_a==undefined){
			DIALOG_UTIL.showTypeDialog("error","请选择城区！！！");
			return;
		}

		connect_addr = $("#connect_addr").val();
		if(isNull(connect_addr)){
			DIALOG_UTIL.showTypeDialog("error","请输入配送地址！");
			return;
		}else{
			if(connect_addr.length<8){
				DIALOG_UTIL.showTypeDialog("error","详细地址至少要输入8个字！");
				return;
			}
		}
	}else{
		connect_area="";
		connect_addr=$("#delivery_address").val();
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
	CACHE_UTIL.setSessionItem("contact_adress", connect_area+connect_addr);
	CACHE_UTIL.setSessionItem("receive_name", customer_name);
	CACHE_UTIL.setSessionItem("receive_phone", contact_phone);
	CACHE_UTIL.setSessionItem("receive_address", connect_addr);
	CACHE_UTIL.setSessionItem("acc_nbr", selected_number);
	CACHE_UTIL.setSessionItem("selected_number", selected_number);
	CACHE_UTIL.setSessionItem("receive_province", receive_province);
	CACHE_UTIL.setSessionItem("receive_city", receive_city);
	CACHE_UTIL.setSessionItem("receive_area", receive_area);
	CACHE_UTIL.setSessionItem("dev_id", dev_id);
	CACHE_UTIL.setSessionItem("dev_name", dev_name);
	CACHE_UTIL.setSessionItem("advancePay", advancePay);
	CACHE_UTIL.setSessionItem("market_price", market_price);
	CACHE_UTIL.setSessionItem("tele_type", tele_type);
	CACHE_UTIL.setSessionItem("oper_no", oper_no);
	CACHE_UTIL.setSessionItem("oper_code", oper_code);
	CACHE_UTIL.setSessionItem("ap_id", ap_id);
	CACHE_UTIL.setSessionItem("product_id", product_id);
	CACHE_UTIL.setSessionItem("total_fee", total_fee);
	CACHE_UTIL.setSessionItem("accept_no", accept_no);
	CACHE_UTIL.setSessionItem("fee_info", JSON.stringify(feeInfo));
	CACHE_UTIL.setSessionItem("tb_account", tb_account);
	CACHE_UTIL.setSessionItem("accept_system", accept_system);
	CACHE_UTIL.setSessionItem("callback_url", callback_url);
	CACHE_UTIL.setSessionItem("ywb_id", JSON.stringify(ywb_id));

	//根据触点id从缓存中获取触点属性
	queryApAttrByRedis();

}

/**
 * 选择发货方式
 * @param e
 */
function selectSendWay(e){
	var code_id=e.attr("code_id");
	if(code_id=="openHn01"){
		e.siblings().removeClass('icon-add');
		e.addClass('icon-add');
		$(".area_page").show();
	}else{
		//目前暂时不支持自提
		return;
		$(".area_page").hide();
	}

	send_id=e.attr("send_id");
	
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
					//genAddr();
				}else if(province=="hn"){
					$("#gui_shu").html("海南 海口市");
					$("#addr_name_p").val("海南省");
					//genAddr();
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
	var customer_name=$("#idName").val();
	var id_number=$("#id_number").val();
	var contact_phone=$("#connect_phone").val();
	
	var contact_address="";
	var receiveAddress="";
	if(isNullUtil("0",$("#delivery_address").val())){
		contact_address=$("#areaCode").text()+$("#connect_addr").val();
		receiveAddress=$("#connect_addr").val();
	}else{
		contact_address=$("#delivery_address").val();
		receiveAddress=$("#delivery_address").val();
	}
	
	var dataJson={
			"order_id":order_id,
			"product_id":product_id,
			"prepay_flag":"1",
			"tele_type":tele_type,
			"first_month_fee":"01",//套外
			"pay_type":pay_type,
			"customer_name":customer_name,
			"id_number":id_number,
			"id_type":"02",
			"contact_phone":contact_phone,
			"contact_adress":contact_address,
			"receive_name":customer_name,
			"receive_phone":contact_phone,
			"receive_address":receiveAddress,
			"acc_nbr":selected_number,
			"receive_province":receive_province,
			"receive_city":receive_city,
			"receive_area":receive_area,
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
				var payFee=total_fee+parseFloat(advancePay);
				if(pay_type=="20"&&payFee>0){
					//更新后跳转到支付页面
					window.location.href = "babyPay.html";
				}else{
					$("#next_flow_step").unbind("click");
					$("#next_flow_step").attr("class","next_disabled next_blank");
					$("#next_flow_step").hide();
					//显示关注的公众号二维码
					$("#order_success_tip").html("下单成功，电话卡将在订单信息审核完成后寄出。");
					$("#PUBLIC_QR").attr('src',(wxPublicQrCode)); //二维码图片
					DIALOG_UTIL.showDialog("qr_code_pop", true);
					$("#qr_code_pop").css("top","15px");
					//DIALOG_UTIL.showTypeDialog("info", "电话卡将在订单信息审核完成后寄出，感谢使用！");
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
	var contact_address="";
	var receiveAddress="";
	if(isNullUtil("0",$("#delivery_address").val())){
		contact_address=$("#areaCode").text()+$("#connect_addr").val();
		receiveAddress=$("#connect_addr").val();
	}else{
		contact_address=$("#delivery_address").val();
		receiveAddress=$("#delivery_address").val();
	}
	
	cod_charge=cod_charge+parseFloat(advancePay)*100;
	total_fee=total_fee+parseFloat(advancePay)*100;
	var goodNumFee=parseFloat(advancePay)*100;
	
	if(goodNumFee>0){
		for(var i=0;i<feeInfo.length;i++){
			if(feeInfo[i].fee_id=="100000"){
				feeInfo[i].orig_fee=parseFloat(feeInfo[i].orig_fee)+goodNumFee+"";
				feeInfo[i].real_fee=parseFloat(feeInfo[i].real_fee)+goodNumFee+"";
			}else{
				if(i==feeInfo.length-1){
					var fee_result={
							"fee_id":"100000",
							"fee_category":"2",
							"orig_fee":goodNumFee+"",
							"relief_fee":"0.00",
							"relief_result":"无",
							"real_fee":goodNumFee+"",
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
		    "accept_no":accept_no,
		    "pay_type":pay_type,
			"serial_number":selected_number,
			"recom_person_id":dev_id,
			"recom_person_name":dev_name,
			"first_mon_bill_mode":"01",
			"product_id":product_id,
			"cert_address":gztInfo.addr,
			"cert_expire_date":"",
			"customer_name":$("#idName").val(),
			"customer_sex":gztInfo.sex,
			"cert_num":$("#id_number").val(),
			"cert_type":"02",
			"contact_address":contact_address,
			"contact_phone":$("#connect_phone").val(),
			"receive_name":$("#idName").val(),
			"receive_phone":$("#connect_phone").val(),
			"receive_province":receive_province,
			"receive_city":receive_city,
			"receive_area":receive_area,
			"receive_address":receiveAddress,
			"total_fee":total_fee,
			"oper_code":oper_code,
			"activity_id":activity_id,
			"ap_id":ap_id,
			"reward_oper_no":reward_oper_no,
			"ap_type":ap_type,
			"fee_info":all_fee_info,
			"tb_account":tb_account,
			"callback_url":callback_url,
			"accept_system":accept_system,
			"ywb_id":JSON.stringify(ywb_id)
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
				//取支付类型，20时才微信支付
				pay_type=data.args.apAttrInfo.pay_type;
				accept_system=isNullUtil("1",data.args.apAttrInfo.accept_system);
				callback_url=isNullUtil("1",data.args.apAttrInfo.callback_url);
				wxPublicQrCode=isNullUtil("1",data.args.apAttrInfo.wx_public_qr_code);
				if(isNullUtil("0",pay_type)){
					pay_type="10";
				}
				
				//创建本地触点订单
				createOrderInfo();
				
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
});


/**
 * 显示要支持的发货方式
 */

function sendTypeShow(){
	
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
 * 点击修改按钮后
 */
function updateAddr(){
	$("#delivery").hide();
	$("#update_btn").hide();
	$("#cancel_btn").show();
	$(".area_page").show();
	$("#save_btn").attr("class","a_use");
	//取消按钮
	$("#cancel_btn").unbind("click").bind("click",function(){
		$("#delivery").show();
		$("#update_btn").show();
		$("#cancel_btn").hide();
		$(".area_page").hide();
		$("#save_btn").attr("class","a_un_use");
		$("#save_btn").unbind("click");
	});
	$("#save_btn").unbind("click").bind("click",function(){
		receive_province=code_area_p;
		receive_city=code_area_c;
		receive_area=code_area_a;
		$("#delivery_address").val($("#areaCode").text()+$("#connect_addr").val());
		$("#delivery").show();
		$("#update_btn").show();
		$("#cancel_btn").hide();
		$(".area_page").hide();
		$("#save_btn").attr("class","a_un_use");
		$("#save_btn").unbind("click");
	});
}


/**
 * 创建触点本地订单
 */
function createOrderInfo(){
	var dataJson={
			oper_no      : oper_no,
			tele_type    : tele_type,
			order_source : "300"//订单来源
	};
	
	DIALOG_UTIL.showTypeDialog("loading", "");
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoOrderServiceRest/createOppoOrderInfo",
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
				//创建订单成功返回order_id
				order_id=data.args.order_id;
				CACHE_UTIL.setSessionItem("order_id", order_id);
				//客户的校验一系列操作
				checkCertNoNums();
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "创建订单Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}
//dj
$().ready(function(){
	if($('#delivery_address').val()==''){
		$('#textarea').html('请输入配送地址（不得少于8个字）');
		$('#textarea').css('color','#c8c8c8');
		$('#textarea').css('font-weight','100');
	}else{
		$('#textarea').html($('#delivery_address').val());
		$('#textarea').css('color','#333');
	}
});



