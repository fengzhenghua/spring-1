//var apc_rest_host="http://localhost:8080/apc_rest/";
var listOfr = null;
var oper_no;
var ap_id;
var developer_list=[];
var ofr_idx = 0;//下标
var tele_type="4G";
var order_id="";
//url所传发展人，若传就去url中发展人，若不传，就取触点信息返回的发展人
var dev_code="";
var dev_name="";
//自助服务标志
var selfServFlag="";
//背景图片
var pic_url="";
//即时激励发展人
var reward_oper_no="";
//触点类型
var ap_type="";
//费用数组
var fee_info=[];
var uocTotalFee="";

$(document).ready(function () {
	//从url中取参数
	oper_no = HTML_UTLS.getParam("oper_no");
	ap_id = HTML_UTLS.getParam("ap_id");
	dev_code = HTML_UTLS.getParam("dev_code");
	dev_name = HTML_UTLS.getParam("dev_name");
	selfServFlag=HTML_UTLS.getParam("selfServFlag");
	reward_oper_no=HTML_UTLS.getParam("reward_oper_no");
	ap_type=HTML_UTLS.getParam("ap_type");
	
	//查询资费
	getTariffInfo();
	
	//点击选择产品
	$("div[name='ofr_div']").click(function(){
		$("div[name='ofr_div']").each(function() {
			$(this).removeClass("open_card_clicked");
		});
		
		$(this).addClass("open_card_clicked");
		ofr_idx = parseInt($(this).attr("data"));
		$("#ofr_desc").html(listOfr[ofr_idx].tariff_desc);
	});
	
	//详情展示
	$("#show_detail").unbind("click").bind("click", showTariffDetail);
	//详情关闭
	$("#hide_detail").unbind("click").bind("click", function(){
		DIALOG_UTIL.hideDialog("expenses_deatil");
	});
	//点击立即申请按钮
	$("#next_flow_step").unbind("click").bind("click", apply);
	
	
});


/**
 * 取资费
 */
function getTariffInfo(){
	
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
				//如果url中取不到工号，就取接口返回的绑定工号
				if(oper_no==null||oper_no==""||oper_no==undefined){
					oper_no=data.args.ap_info.bind_oper;
					if(oper_no==""||oper_no==null||oper_no==undefined){
						DIALOG_UTIL.showTypeDialog("error", "触点信息没有配置相应工号信息！！！");
						return;
					}
				}
				
				//取到资费信息
				listOfr = data.args.tariff_info_list;
				//不同的触点显示不同的背景图片,默认取第一条资费配的
				pic_url=listOfr[0].pic_url;
				
				//pic_url="../../images/mobile/oppoCard/open_card_choose.jpg";
				$("#pic_url").css("background","url("+pic_url+") no-repeat center center ");
				$("#pic_url").css("background-size","100% 616px");
				//url所传发展人为空
				if(dev_code==""||dev_code==null||dev_code==undefined){
					//取到触点信息返回的可选发展人
					developer_list=data.args.developer_list;
					//判断如果触点信息返回没有发展人就页面报错
					if(developer_list!=null&&developer_list.length>0){
						developer_list=JSON.stringify(developer_list);
						
						if (listOfr!=null&&listOfr!=undefined&&listOfr.length>=2) {
							$("#ofr_name0").html(listOfr[0].tariff_name);
							$("#ofr_name1").html(listOfr[1].tariff_name);
							$("div[name='ofr_div']").first().click();
						}
					}else{
						DIALOG_UTIL.showTypeDialog("error", "触点信息没有配置相应发展人！！！");
						return;
					}
				}else{
					var devDefault={
							"developer_no":dev_code,
							"developer_name":dev_name
					};
					developer_list[0]=devDefault;
					developer_list=JSON.stringify(developer_list);
					
					if (listOfr!=null&&listOfr!=undefined&&listOfr.length>=2) {
						$("#ofr_name0").html(listOfr[0].tariff_name);
						$("#ofr_name1").html(listOfr[1].tariff_name);
						$("div[name='ofr_div']").first().click();
					}
				}
				
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "查询资费Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}

/**
 * 立即申请，缓存后跳转到base.html页面
 */
function apply(){
	//取选中的资费
	var ofrVo = listOfr[ofr_idx];
	if(ofrVo.oper_code==null||ofrVo.oper_code==""||ofrVo.oper_code==undefined){
		DIALOG_UTIL.showTypeDialog("error", "该资费没有配置对应的oper_code!!!");
		return;
		
	}
	
	//先创建订单
	createOrderInfo();
}


/**
 * 页面缓存
 */

function cacheData(){
	//不选资费不能进行申请
	if (ofr_idx == null) {
		return;
	}
	
	//取选中的资费
	var ofrVo = listOfr[ofr_idx];
	
	//总费用
	var fee_list=ofrVo.fee_list;
	var total_fee=0;
	var uoc_total_fee=0;
	for(var i=0;i<fee_list.length;i++){
		total_fee=total_fee+parseFloat(fee_list[i].total_fee);
		if(fee_list[i].fee_item_type!="apc"){
			uoc_total_fee=uoc_total_fee+parseFloat(fee_list[i].total_fee);
			var fee_result={
					"fee_id":fee_list[i].fee_item_code,
					"fee_category":fee_list[i].fee_item_type,
					"orig_fee":parseFloat(fee_list[i].total_fee)*100+"",
					"relief_fee":"0.00",
					"relief_result":"无",
					"real_fee":parseFloat(fee_list[i].total_fee)*100+"",
					"fee_des":fee_list[i].fee_item_name
			};
			fee_info.push(fee_result);
		}
	}
	
	
	//首页先清除下缓存，后面的页面不要清除喔
	CACHE_UTIL.clearSessionData();
	//缓存工号
	CACHE_UTIL.setSessionItem("oper_no", oper_no);
	//缓存可选发展人
	CACHE_UTIL.setSessionItem("developer_list", developer_list);
	//缓存电信类型，现在暂时写死4G
	CACHE_UTIL.setSessionItem("tele_type", "4G");
	//资费id
	CACHE_UTIL.setSessionItem("product_id", ofrVo.tariff_id);
	//资费名称
	CACHE_UTIL.setSessionItem("product_name", ofrVo.tariff_name);
	//合约id
	CACHE_UTIL.setSessionItem("activity_id", ofrVo.activity_id);
	//缓存费用，暂时写死0.1
	CACHE_UTIL.setSessionItem("market_price", total_fee);
	//缓存订单id
	CACHE_UTIL.setSessionItem("order_id", order_id);
	
	CACHE_UTIL.setSessionItem("fee_code", ofrVo.fee_item_code);
	CACHE_UTIL.setSessionItem("fee_class", ofrVo.fee_item_type);
	CACHE_UTIL.setSessionItem("fee_name", ofrVo.fee_item_name);
	CACHE_UTIL.setSessionItem("product_desc", ofrVo.tariff_desc);
	CACHE_UTIL.setSessionItem("oper_code", ofrVo.oper_code);
	//缓存自助标志
	CACHE_UTIL.setSessionItem("selfServFlag", selfServFlag);
	//缓存触点id
	CACHE_UTIL.setSessionItem("ap_id", ap_id);
	//缓存可选靓号标识
	CACHE_UTIL.setSessionItem("good_num_flag", ofrVo.good_num_flag);
	//缓存收货区域标识
	CACHE_UTIL.setSessionItem("receive_area_flag", ofrVo.receive_area_flag);
	//缓存普通快递发货标识
	CACHE_UTIL.setSessionItem("send_type0_flag", ofrVo.send_type0_flag);
	//缓存货到付款发货标
	CACHE_UTIL.setSessionItem("send_type1_flag", ofrVo.send_type1_flag);
	//缓存自提发货标识
	CACHE_UTIL.setSessionItem("send_type2_flag", ofrVo.send_type2_flag);
	//缓存即时激励发展人
	CACHE_UTIL.setSessionItem("reward_oper_no", reward_oper_no);
	//缓存触点类型
	CACHE_UTIL.setSessionItem("ap_type", ap_type);
	//缓存费用数组字符串
	CACHE_UTIL.setSessionItem("fee_info", JSON.stringify(fee_info));
	//缓存传中台的总费用
	CACHE_UTIL.setSessionItem("uoc_total_fee", uoc_total_fee);
}

/**
 * 创建订单
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
				//创建订单成功返回order_id并缓存给前台传参做更新数据用
				order_id=data.args.order_id;
				//缓存
				cacheData();
				//跳转选号
				window.location.href = "base.html";
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


/**
 * 查询发展人
 */
function getDeveloperInfo(){
	
	var dataJson={
			"jsessionId":oper_no,
			"developerNo":dev_code,
			"developerName":""
	};
	
	DIALOG_UTIL.showTypeDialog("loading", "");
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/optionalOper/getOptionalDeveInfo",
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
				if(data.args.code=="200"){
					var dev_list=JSON.parse(data.args.json_info).developer_list;
					//没有查到发展人页面弹出提示
					if(dev_list!=null&&dev_list.length>0){
						if(dev_list.length==1){
							dev_code=dev_list[0].dev_code;
							dev_name=dev_list[0].dev_name;
							var devDefault={
									"developer_no":dev_code,
									"developer_name":dev_name
							};
							developer_list[0]=devDefault;
							developer_list=JSON.stringify(developer_list);
							
							if (listOfr!=null&&listOfr!=undefined&&listOfr.length>=2) {
								$("#ofr_name0").html(listOfr[0].tariff_name);
								$("#ofr_name1").html(listOfr[1].tariff_name);
								$("div[name='ofr_div']").first().click();
							}
						}else{
							DIALOG_UTIL.showTypeDialog("error", "查询到多个发展人，请检查发展人配置是否有误！！！");
						}
					}else{
						DIALOG_UTIL.showTypeDialog("error", "没有查询到相应的发展人！！！");
					}
				}else{
					DIALOG_UTIL.showTypeDialog("error", "查询发展人失败");
				}
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "可选发展人Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}


/**
 * 选择资费时，显示详情内容
 */
function showTariffDetail(){
	//取选中的资费详情
	var detailInfo = listOfr[ofr_idx].tariff_show_detail;
	$("#rent").html(replaceEmpty(detailInfo.rent));
	$("#province_flow_charge").html(replaceEmpty(detailInfo.province_flow_charge));
	$("#country_flow_charge").html(replaceEmpty(detailInfo.country_flow_charge));
	$("#province_minutes_charge").html(replaceEmpty(detailInfo.province_minutes_charge));
	$("#country_minutes_charge").html(replaceEmpty(detailInfo.country_minutes_charge));
	$("#province_free_minutes").html(replaceEmpty(detailInfo.province_free_minutes));
	$("#country_free_minutes").html(replaceEmpty(detailInfo.country_free_minutes));
	$("#province_free_flow").html(replaceEmpty(detailInfo.province_free_flow));
	$("#country_free_flow").html(replaceEmpty(detailInfo.country_free_flow));
	$("#other_content").html(replaceEmpty(detailInfo.other_info));
	
	DIALOG_UTIL.showDialog("expenses_deatil", true);
}

/**
 * 用空字符串代替null和undefined
 * @param value
 */
function replaceEmpty(value){
	if(value==null||value==undefined){
		value="";
	}
	return value;
}



