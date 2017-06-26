
var jsessionid;
var order_id;
var selected_number="";
var tele_type;

var code_area_list;
var code_area_p;
var code_area_c;
var code_area_a;
var apc_rest_host="http://localhost:8080/apc_rest/";

$(document).ready(function () {
	jsessionid = CACHE_UTIL.getSessionItem("jsessionid");
	order_id = CACHE_UTIL.getSessionItem("order_id");
	tele_type = CACHE_UTIL.getSessionItem("tele_type");
	
	$("#product_name").html(CACHE_UTIL.getSessionItem("product_name"));
	
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
	//选号
	$("#selectNum").unbind("click").bind("click", popNumber);
	$("#hideNumber").unbind("click").bind("click", hideNumber);
	$("#searchData").unbind("click").bind("click", selectNumberApc);
	$(".btn_ahead").unbind("click").bind("click", turnLeft);
	$(".btn_behind").unbind("click").bind("click", turnRight);
	
	
	$("#btn_service_protocol").click(function(){
		$("#service_protocol_txt").show();
		
		$("#main-page").hide();
		
	});
	
	//提交订单
	$("#next_flow_step").unbind("click").bind("click", confirmOrder);
	
	
	$("#areaCode").unbind("click").bind("click", showCodeAreaApc);
	
	//发展人弹出框的返回按钮
	$("#protocol_back").unbind("click").bind("click", function () {
		
		$("#service_protocol_txt").hide();
		
		$("#main-page").show();
	});
	
	
	//tab lgw 2016-5-19
	jQuery.jqtab = function(tabtit,tabcon) {
		$(tabtit+" li:first").hide();
		$("#tab1").hide();
		$(tabtit+" li:first").next().addClass("thistab")
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


function popNumber() {
	if (selected_number=="") {
		selectNumberApc();
	}
	else{
		DIALOG_UTIL.showDialog("number_pop", true);
	}
};

function hideNumber() {
	DIALOG_UTIL.hideDialog("number_pop");
};

/*function selectNumber() {
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
	var registClient = {
			onComplete: function (args) {
				list = args.number_list;
				if (list=="" || list==null || list.length == 0) {//args==null 
					$("#number_content").html("");
					DIALOG_UTIL.showTypeDialog("warring", "没有符合条件的号码！");
					return;
				}
				initPage();
				
				numberPageData();
				
				DIALOG_UTIL.showDialog("number_pop", true);
			},
			onError: function (error) {
				DIALOG_UTIL.showTypeDialog("warring", "没有符合条件的号码！");
				$("#number_content").html("");
			},
			onException: function (status, errorInfo, hint) {
				$("#number_content").html("");
			},
			onFinally: function () {
				DIALOG_UTIL.hideDialog("", "loading");
			}
	};
	DIALOG_UTIL.showTypeDialog("loading", "");
	WebUtil.doPost(apc_rest_host + "rest/oppocard/selectNumberData", {
		jsessionid  : jsessionid,
		fuzzy_query : inputText,
		page_info   : "autoTerminal",
		order_id    : order_id,
		tele_type   : tele_type,
		ser_type    : "1"
	}, true, registClient);

};*/

//分页显示号码
var list;
var pageNums = 9;
var page_current ,page_total;
var totalRows;
function initPage() {
	totalRows = list.length;
	page_total = parseInt(totalRows/pageNums)+(parseInt(totalRows%pageNums)>0?1:0);
	$("#page_total").html(page_total);
	
	page_current = 1;
};

function numberPageData() {
	$("#number_content").html("");
	
	var startRow = (page_current-1)*pageNums;
	var endRow = startRow+pageNums;
	var result = '';
	for (var i=startRow;i<endRow&&i<totalRows;i++) {
		var start ='';
		var end ='';
		if((i+1)%3==1){
			start='<div class="number_box">';
		}
		if((i+1)%3==0 || (i+1)==totalRows){
			end = '</div>';
		}
		result += start + '<div class="number_box_row" style="cursor:pointer;" id=\'' + list[i].acc_nbr + '\'>' + list[i].acc_nbr + '</div>' + end;
	}
	$("#number_content").html(result);
	
	$("#page_current").html(page_current);
	
	selectClick();
	
};

function turnLeft() {
	if (page_current<=1) {
		return;
	}
	page_current--;
	numberPageData();
};

function turnRight() {
	if (page_current>=page_total) {
		return;
	}
	page_current++;
	numberPageData();
};

function selectClick() {
	$("#number_content .number_box_row").unbind("click").bind("click", function (e) {
		hideNumber();
		numberOccupyApc($(this).attr("id"));
		
	});
};

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
	
	var connect_addr = $("#connect_addr").val();
	if(isNull(connect_addr)){
		DIALOG_UTIL.showTypeDialog("error","请输入配送地址！");
		return;
	}
	
	
	if(isNull(selected_number)){
		DIALOG_UTIL.showTypeDialog("error","请先选择一个号码");
		return;
	}
	
	//判断黑名单和一证五户
	$.when(custCheckApc()).done(function(){
		$.when(userNumberCheckApc()).done(function(){
			var attrdata = {
					"customer_name"  : customer_name,
					"id_type"        : "02",
					"id_number"      : id_number,
					"contact_phone"  : contact_phone,
					"contact_adress" : $("#areaCode").text()+connect_addr,
					"receive_name"   : customer_name,
					"receive_phone"  : contact_phone,
					"receive_address": $("#areaCode").text()+connect_addr,
					"acc_nbr"        : selected_number,
					"concard_flag": $("#concard_flag").attr("value"),
					"receive_province":code_area_p,
					"receive_city":code_area_c,
					"receive_area":code_area_a
			};
			
			//1.更新订单属性 －》 2.预占号码 －》 3.支付 －》 4.更新订单状态为已支付
			attrUpdateApc(attrdata);
		});
	});
};


/*function attrUpdate(attrdata) {
	
	DIALOG_UTIL.showTypeDialog("loading", "");
	var registClient = {
			onComplete: function (args) {
				// 2.预占号码
				//numberOccupy();
				
				//改成支付，然后支付完成再长预占
				pay();
			},
			onError: function(error) {
				DIALOG_UTIL.showTypeDialog("error", error.content);
			},
			onException: function(status, errorInfo, hint) {
				
				DIALOG_UTIL.showTypeDialog("error", errorInfo);
			},
			onFinally: function () {
				//DIALOG_UTIL.hideDialog("", "loading");
			}
	};
	var reg=new RegExp(":null,","g");
	WebUtil.doPost(apc_rest_host + 'rest/orderInfo/orderInfoAttrUpdate', {
		"jsessionid": jsessionid,
		"order_id"  : order_id,
		"page_code" : "0",
		"order_json": JSON.stringify(attrdata).replace(reg,':"",')

	}, true,registClient);
};*/

/*function numberOccupy(number) {
	
	DIALOG_UTIL.showTypeDialog("loading", "");
	var registClient = {
			onComplete: function (args) {
				if( args.state_code == "0000" ){
					// 3.支付
					//pay();
					selected_number = number;
					$("#selected_number").html(number);
					
				}
				else {
					//DIALOG_UTIL.hideDialog("", "loading");
					DIALOG_UTIL.showTypeDialog("error", "号码预占失败，请选择其它的号码");
				}
			},
			onError: function(error) {
				DIALOG_UTIL.showTypeDialog("error", "号码预占失败，请选择其它的号码重试");
			},
			onException: function(status, errorInfo, hint) {
				DIALOG_UTIL.showTypeDialog("error", errorInfo);
			},
			onFinally: function () {
				DIALOG_UTIL.hideDialog("", "loading");
			}
	};
	WebUtil.doPost(apc_rest_host + 'rest/oppocard/numberOccupy', {
		"jsessionid"   : jsessionid,
		"order_id"     : order_id,
		"acc_nbr"      : number,
		"ser_type"     : "1",
		"tele_type"    : tele_type,
		"occupiedFlag" : "1"  //号码状态标识：0 不预占；1 预占；2 预订（未付费）；3 预订（付费）；4 释放资源
	}, true,registClient);
};*/


//支付
function pay() {
	
	//跳转另一页面用扫码
	CACHE_UTIL.setSessionItem("selected_number", selected_number);
	window.location.href = "pay.html";
	
	//暂未实现
	//DIALOG_UTIL.showTypeDialog("loading", "");
	//var market_price = CACHE_UTIL.getSessionItem("market_price");
	
	
	//4.更新订单状态为已支付
	//orderInfoStatusUpdate();
};

function orderInfoStatusUpdate() {
	DIALOG_UTIL.showTypeDialog("loading", "");
    var registClient = {
        onComplete: function (args) {
        	alert("ok");
        },
        onError: function(error) {
			DIALOG_UTIL.showTypeDialog("error", error.content);
		},
		onException: function(status, errorInfo, hint) {
			DIALOG_UTIL.showTypeDialog("error", errorInfo);
		},
		onFinally: function () {
			//DIALOG_UTIL.hideDialog("", "loading");
		}
    };
    
    WebUtil.doPost(apc_rest_host + "rest/orderInfo/orderInfoStatusUpdate", {
        "jsessionid" : jsessionid,
        "order_id"   : order_id,
        "bill_type"  : '100'
    }, true, registClient);
};



//验证手动输入的身份证号
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
	if(area[parseInt(idcard.substr(0,2))]==null) 
		return false;
	//身份号码位数及格式检验 
	switch(idcard.length){ 
	case 15: 
	if ( (parseInt(idcard.substr(6,2))+1900) % 4 == 0 || ((parseInt(idcard.substr(6,2))+1900) % 100 == 0 && (parseInt(idcard.substr(6,2))+1900) % 4 == 0 )){ 
		ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//测试出生日期的合法性 
	} else { 
		ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//测试出生日期的合法性 
	} 
	if(ereg.test(idcard)) 
		return false; 
	else return false; 
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
		if(M == idcard_array[17]) 
			return true; //检测ID的校验位 
		else return false;
	}else 
		return false;
	break; 
	default: 
		return false; 
	break; 
	} 
} 


/* 地址数据 2016-12-19 */
var addr_p;

var addr_c;

//父节点为省份+城市
var addr_a;

/*function showCodeArea(){
	
	DIALOG_UTIL.showTypeDialog("loading", "");
	var registClient = {
			onComplete: function (args) {
				code_area_list = args.list;
				
				var pstr = "";
				for(var i=0; i<code_area_list.length; i++){
					var codeArea = code_area_list[i];
					if(codeArea.area_code.length <= 3){
						pstr += '{"code_id":"'+code_area_list[i].area_code+'","code_name":"'+code_area_list[i].area_name+'","parent_code":"'+code_area_list[i].parent_area_code+'"},';
					}
				}
				pstr = pstr.substring(0,pstr.length-1);
				pstr = '{"addr_list":['+pstr+']}';
				console.info(pstr);
				addr_p = JSON.parse(pstr);
				
				
				pstr = "";
				for(var i=0; i<code_area_list.length; i++){
					var codeArea = code_area_list[i];
					if(codeArea.area_code.length == 4){
						pstr += '{"code_id":"'+code_area_list[i].area_code+'","code_name":"'+code_area_list[i].area_name+'","parent_code":"'+code_area_list[i].parent_area_code+'"},';
					}
				}
				pstr = pstr.substring(0,pstr.length-1);
				pstr = '{"addr_list":['+pstr+']}';
				console.info(pstr);
				addr_c = JSON.parse(pstr);
				
				pstr = "";
				for(var i=0; i<code_area_list.length; i++){
					var codeArea = code_area_list[i];
					if(codeArea.area_code.length > 4){
						pstr += '{"code_id":"'+code_area_list[i].area_code+'","code_name":"'+code_area_list[i].area_name+'","parent_code":"'+code_area_list[i].parent_area_code+'"},';
					}
				}
				pstr = pstr.substring(0,pstr.length-1);
				pstr = '{"addr_list":['+pstr+']}';
				console.info(pstr);
				addr_a = JSON.parse(pstr);
				
				var default_province = "50";
				
				
				//生成省份，城市、城区关联生成
				//genData("00",addr_p,"tab1");
				//默认海南 2016-12-21
				var addr_default_p = "";
				for(var i=0; i<addr_p.addr_list.length; i++){
					if(addr_p.addr_list[i].code_id == default_province){
						addr_default_p = addr_p.addr_list[i].code_name;
						break;
					}
				}
				
				
				$("#addr_name_p").val(addr_default_p);
				code_area_p = default_province;
				
				$("#addr_name_c").val("");
				$("#addr_name_a").val("");
				genAddr();
				$("#addr_choice").css("line-height","30px");
				genData(default_province,addr_c,"tab2");
				
				//记录名称 2016-12-21
				$("#tab2 .tabs_data").unbind("click").bind("click", function () {
					$("#addr_name_c").val($(this).html());
					code_area_c = JSON.parse($(this).attr("data")).code_id;
					genAddr();
				});
				
				//记录名称
				$("#tab1 .tabs_data").unbind("click").bind("click", function () {
					$("#addr_name_p").val("");
					$("#addr_name_c").val("");
					$("#addr_name_a").val("");
					$("#local_addr").html("");
					$("#addr_name_p").val($(this).html());
					genAddr();
				});
				
				DIALOG_UTIL.showDialog("address_choose_dialog", true);
				
				$("#areaCode").unbind().bind("click", function(){
					DIALOG_UTIL.showDialog("address_choose_dialog", true);
				});
			},
			onError: function(error) {
			},
			onException: function(status, errorInfo, hint) {
			},
			onFinally: function () {
				DIALOG_UTIL.hideDialog("", "loading");
			}
	};
	WebUtil.doPost(apc_rest_host + 'rest/oppocard/queryCodeArea', {
		"jsessionid": CACHE_UTIL.getSessionItem("jsessionid")
	
	}, true,registClient);
}*/

/* 生成数据 */
function genData(ParentId,dataJson,targetId) {
	if ($("#"+targetId).length <= 0) {
		console.info("genData无效入参targetId=="+targetId);
	}
	
	//清空
	$("#"+targetId).html("");
	
	//console.info("dataJson.addr_list=="+dataJson.addr_list[0].code_name);
	
	//拼数据
	var tab_list="<div class='tab_list'>";
	var tabs_data="";
	for (i = 0; i < dataJson.addr_list.length; i++) {
		if(ParentId == dataJson.addr_list[i].parent_code) {
			tabs_data = tabs_data + "<div class='tabs_data' data=" + JSON.stringify(dataJson.addr_list[i]);
			
			//加点击事件
			var str_click="";
			if(targetId == "tab1") {
				str_click = " onclick=genDataCity('" + dataJson.addr_list[i].code_id + "')";
			}
			if(targetId == "tab2") {
				str_click = " onclick=genDataArea('" + dataJson.addr_list[i].code_id + "')";
			}
			
			tabs_data = tabs_data  + str_click;
			tabs_data = tabs_data + ">" +  dataJson.addr_list[i].code_name + "</div>";
		}
	}
	tab_list = tab_list + tabs_data + "</div>";
	
	//插入
	$("#"+targetId).append(tab_list);
};

//生成城市
function genDataCity(ParentId) {
	genData(ParentId,addr_c,"tab2");
	
	//选中
	$(".tabs li").removeClass("thistab");
	$(".tabs li:first").next().addClass("thistab");
	
	//记录名称
	$("#tab2 .tabs_data").unbind("click").bind("click", function () {
		$("#addr_name_c").val($(this).html());
		code_area_c = JSON.parse($(this).attr("data")).code_id;
		code_area_a = "";
		$("#addr_name_a").val("");
		genAddr();
		
		//返回
		//returnBack();
	});
	
	$(".tab_con").hide();
	$("#tab2").show();
};
//生成城区
function genDataArea(ParentId) {
	genData(ParentId,addr_a,"tab3");
	
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
};

//拼成地址串
function genAddr() {
	$("#addr_choice").html("");
	$("#addr_choice").html($("#addr_name_p").val() + " " + $("#addr_name_c").val() + " " + $("#addr_name_a").val());
	$("#areaCode").html($("#addr_choice").html());//结果写回页面
}

//返回
function returnBack() {
	DIALOG_UTIL.hideDialog("address_choose_dialog");
}


/*function custCheck(){
	var certId = $("#id_number").val();
	var certName = $("#idName").val();
	
	return WebUtil.doPostAsync(apc_rest_host + 'rest/oppocard/checkCustInfo', {
		"jsessionid" : jsessionid,
		"tele_type":tele_type,
		"province_code":"hn",
		"id_number":certId,
		"id_type":"02",
		"customer_name":certName,
		"device_number":"",
		"checkType":"0"
    }, true, {
        onComplete: function (args) {        	
        }
    });
}*/

/*function userNumberCheck(){
	if(1 == 1){
		return;
	}
	var certId = $("#id_number").val();
	var certName = $("#idName").val();
	return WebUtil.doPostAsync(apc_rest_host + 'rest/oppocard/queryUserNumber', {
		"jsessionid" : jsessionid,
		"certId":certId,
		"certName":certName
    }, true, {
        onComplete: function (args) {
        	if(parseInt(args.user_amount) >= 5){
        		DIALOG_UTIL.showTypeDialog("warning","抱歉，此身份证已经超过五个用户，不允许再受理！");
      		  	return false;
        	}
        }
    });
}*/


/*********************************迁移代码后修改js****************************************/

function selectNumberApc() {
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
			jsessionid  : jsessionid,
			fuzzy_query : inputText,
			page_info   : "autoTerminal",
			order_id    : order_id,
			tele_type   : tele_type,
			ser_type    : "1"
	};
	
	DIALOG_UTIL.showTypeDialog("loading", "");
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppocard/selectNumberData",
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
				if (list=="" || list==null || list.length == 0) {//args==null 
					$("#number_content").html("");
					DIALOG_UTIL.showTypeDialog("warring", "没有符合条件的号码！");
					return;
				}
				initPage();
				
				numberPageData();
				
				DIALOG_UTIL.showDialog("number_pop", true);
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "查询号码Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}

/**
 * 地区选择
 */
function showCodeAreaApc(){
	
	var dataJson={
			"jsessionid": CACHE_UTIL.getSessionItem("jsessionid")
	};
	
	DIALOG_UTIL.showTypeDialog("loading", "");
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppocard/queryCodeArea",
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
				code_area_list = data.args.list;
				
				var pstr = "";
				for(var i=0; i<code_area_list.length; i++){
					var codeArea = code_area_list[i];
					if(codeArea.area_code.length <= 3){
						pstr += '{"code_id":"'+code_area_list[i].area_code+'","code_name":"'+code_area_list[i].area_name+'","parent_code":"'+code_area_list[i].parent_area_code+'"},';
					}
				}
				pstr = pstr.substring(0,pstr.length-1);
				pstr = '{"addr_list":['+pstr+']}';
				console.info(pstr);
				addr_p = JSON.parse(pstr);
				
				
				pstr = "";
				for(var i=0; i<code_area_list.length; i++){
					var codeArea = code_area_list[i];
					if(codeArea.area_code.length == 4){
						pstr += '{"code_id":"'+code_area_list[i].area_code+'","code_name":"'+code_area_list[i].area_name+'","parent_code":"'+code_area_list[i].parent_area_code+'"},';
					}
				}
				pstr = pstr.substring(0,pstr.length-1);
				pstr = '{"addr_list":['+pstr+']}';
				console.info(pstr);
				addr_c = JSON.parse(pstr);
				
				pstr = "";
				for(var i=0; i<code_area_list.length; i++){
					var codeArea = code_area_list[i];
					if(codeArea.area_code.length > 4){
						pstr += '{"code_id":"'+code_area_list[i].area_code+'","code_name":"'+code_area_list[i].area_name+'","parent_code":"'+code_area_list[i].parent_area_code+'"},';
					}
				}
				pstr = pstr.substring(0,pstr.length-1);
				pstr = '{"addr_list":['+pstr+']}';
				console.info(pstr);
				addr_a = JSON.parse(pstr);
				
				var default_province = "50";
				
				
				//生成省份，城市、城区关联生成
				//genData("00",addr_p,"tab1");
				//默认海南 2016-12-21
				var addr_default_p = "";
				for(var i=0; i<addr_p.addr_list.length; i++){
					if(addr_p.addr_list[i].code_id == default_province){
						addr_default_p = addr_p.addr_list[i].code_name;
						break;
					}
				}
				
				
				$("#addr_name_p").val(addr_default_p);
				code_area_p = default_province;
				
				$("#addr_name_c").val("");
				$("#addr_name_a").val("");
				genAddr();
				$("#addr_choice").css("line-height","30px");
				genData(default_province,addr_c,"tab2");
				
				//记录名称 2016-12-21
				$("#tab2 .tabs_data").unbind("click").bind("click", function () {
					$("#addr_name_c").val($(this).html());
					code_area_c = JSON.parse($(this).attr("data")).code_id;
					genAddr();
				});
				
				//记录名称
				$("#tab1 .tabs_data").unbind("click").bind("click", function () {
					$("#addr_name_p").val("");
					$("#addr_name_c").val("");
					$("#addr_name_a").val("");
					$("#local_addr").html("");
					$("#addr_name_p").val($(this).html());
					genAddr();
				});
				
				DIALOG_UTIL.showDialog("address_choose_dialog", true);
				
				$("#areaCode").unbind().bind("click", function(){
					DIALOG_UTIL.showDialog("address_choose_dialog", true);
				});
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
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
function numberOccupyApc(number) {
	var dataJson={
			"jsessionid"   : jsessionid,
			"order_id"     : order_id,
			"acc_nbr"      : number,
			"ser_type"     : "1",
			"tele_type"    : tele_type,
			"occupiedFlag" : "1"  //号码状态标识：0 不预占；1 预占；2 预订（未付费）；3 预订（付费）；4 释放资源
	};
	DIALOG_UTIL.showTypeDialog("loading", "");
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppocard/numberOccupy",
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
				if( data.args.state_code == "0000" ){
					// 3.支付
					//pay();
					selected_number = number;
					$("#selected_number").html(number);
					
				}
				else {
					//DIALOG_UTIL.hideDialog("", "loading");
					DIALOG_UTIL.showTypeDialog("error", "号码预占失败，请选择其它的号码");
				}
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
 * 五户校验
 */
function userNumberCheckApc() {
	
	if(1 == 1){
		return;
	}
	var certId = $("#id_number").val();
	var certName = $("#idName").val();
	
	var dataJson={
			"jsessionid" : jsessionid,
			"certId":certId,
			"certName":certName
	};
	DIALOG_UTIL.showTypeDialog("loading", "");
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppocard/queryUserNumber",
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
				if(parseInt(args.user_amount) >= 5){
	        		DIALOG_UTIL.showTypeDialog("warning","抱歉，此身份证已经超过五个用户，不允许再受理！");
	      		  	return false;
	        	}
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "校验Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}


function custCheckApc(){
	var certId = $("#id_number").val();
	var certName = $("#idName").val();
	
	var dataJson={
			"jsessionid" : jsessionid,
			"tele_type":tele_type,
			"id_number":certId,
			"id_type":"02",
			"customer_name":certName,
			"device_number":"",
			"checkType":"0"
	};
	DIALOG_UTIL.showTypeDialog("loading", "");
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppocard/checkCustInfo",
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
				
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "校验Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}

/**
 * 更新订单属性表
 * @param attrdata
 */
function attrUpdateApc(attrdata) {
	var reg=new RegExp(":null,","g");
	var dataJson={
			"jsessionid": jsessionid,
			"order_id"  : order_id,
			"page_code" : "0",
			"order_json": JSON.stringify(attrdata).replace(reg,':"",')
	};
	DIALOG_UTIL.showTypeDialog("loading", "");
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/orderInfo/orderInfoAttrUpdate",
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
				//改成支付，然后支付完成再长预占
				pay();
			} else {
				DIALOG_UTIL.showTypeDialog("error", data.content);
			}
		},
		error: function(data) {
			DIALOG_UTIL.showTypeDialog("error", "更新订单Ajax请求失败!");
		},
		complete:function(){
			DIALOG_UTIL.hideDialog("", "loading");
		}
	});
}