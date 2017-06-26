var ruleId ="";
var operNo = "";
var operKind = "";
var comm_dept_id = "";
var start_time = "";
var end_time = "";
var operKind = "";
var deptNo = "";
var chnlCode = "";
$(document).ready(function() {
	deptNo = $("#deptNo").val();
	operNo = $("#operNo").val();
	chnlCode = $("#chnlCode").val();
	operKind = $("#operKind").val();
	selectDataNum();
	$("#query").click(function() {
			selectData();
	});
});


/**
 * 查询佣金部门
 */
function getCommDept(){
	var comm_type = document.getElementById("comm_type").value;
	if('-1'==comm_type){
	} else {
		if ('102'==comm_type){
			getBranchByProvinceNo();
		} else if ('103'==comm_type){
			getGridByDeptNo();
		} else if('104'==comm_type) {
			getChnlCodeListByGrid();
		}
	}	
}
function getGridByDeptNo(){
	data = {
			dept_no : deptNo	
	};
	var GetURl = application.fullPath + "/authority/hnCommissionList/qryInfoGridListByDeptNo";
	$.ajax({
		url: GetURl,
		type: 'post',
		waitMsg: "正在处理",
		data : data,
		success: function(message) {
			$("#tariff_list").html('');
			var infoGridVoList = message.args.infoGridVoList;
			$.each(infoGridVoList, function(i, item) {
				var data = JSON.stringify(item);
				showdiv('fee_search');
				//YUN-773
				$("#tariff_list").append('<li class="list"><div class="left"><div class="left_lable">'
						+'<a class="tip" href="javascript:void(0)" >'
						+item.dept_name
						+'<span class="tip_info width_32" style="z-index:999;">'+item.dept_name+'</span></a>'
						+'</div><div class="right_data">'								
					  +' <input name="fee_search_check" onClick="tariffSelect(this)"  type="radio"  data=\''+data+'\' id=\''+item.dept_no+'\'></input></div></div></li>');
	
			
			});
			//解决最后一行资费提示文字叠加到浮选框头部，预留空白位置
			/*$("#tariff_list").append('<div class="list"></div>');
			$("#tariff_list").append('<div class="list"></div>');
			$("#tariff_list").append('<div class="list"></div>');*/
		},
		error:function(message){
			$.error("系统失败，请稍后再试！");
		}
	});


}
function showdiv(popWinId) { 
	if (!window.XMLHttpRequest) { 
		for(var i=0;i<selects.length;i++){
			if(selects[i].style.display!='none'){
				selects_display.push(selects[i]);
				selects[i].style.display = "none";
			};
		}
	}
	document.getElementById("bg_mask").style.display ='block';
	document.getElementById(popWinId).style.display ='block';
}

function hidediv(popWinId) {
	if (!window.XMLHttpRequest) { 
		for(var i=0;i<selects_display.length;i++){
			selects_display[i].style.display = "";
		}
	}
	document.getElementById("bg_mask").style.display ='none';
	document.getElementById(popWinId).style.display ='none';
}

function tariffSelect(e){
	var div = $(e);
	var dataStr = div.attr("data");	
	data_tariff = JSON.parse(dataStr);
	comm_dept_id = data_tariff.dept_no;//业务类型id
}

function tariffConfirm(){
	if(comm_dept_id==null||comm_dept_id==''){
		$.error("请选择终端类型");
		return;
	}
	hidediv('fee_search');
	document.getElementById("comm_dept").value =  data_tariff.dept_name;
}

function tariffDelete(){
	hidediv('fee_search');
	//busi_type_id = '';
	data_tariff='';
	document.getElementById("comm_dept").value =  '';
}

function getBranchByProvinceNo(){
	data = {
			//comm_type:comm_type,
	};
	var GetURl = application.fullPath + "/authority/hnCommissionList/qryInfoBranchListByProvinceNo";
	$.ajax({
		url: GetURl,
		type: 'post',
		waitMsg: "正在处理",
		data : data,
		success: function(message) {
			$("#tariff_list_baranch").html('');
			var infoBranchVoList = message.args.infoBranchVoList;
			$.each(infoBranchVoList, function(i, item) {
				var data = JSON.stringify(item);
				showdiv('fee_search_branch');
				//YUN-773
				$("#tariff_list_branch").append('<li class="list"><div class="left"><div class="left_lable">'
						+'<a class="tip" href="javascript:void(0)" >'
						+item.dept_name
						+'<span class="tip_info width_32" style="z-index:999;">'+item.dept_name+'</span></a>'
						+'</div><div class="right_data">'								
					  +' <input name="fee_search_check" onClick="tariffSelectBranch(this)"  type="radio"  data=\''+data+'\' id=\''+item.dept_no+'\'></input></div></div></li>');
	
			
			});
			//解决最后一行资费提示文字叠加到浮选框头部，预留空白位置
			/*$("#tariff_list").append('<div class="list"></div>');
			$("#tariff_list").append('<div class="list"></div>');
			$("#tariff_list").append('<div class="list"></div>');*/
		},
		error:function(message){
			$.error("系统失败，请稍后再试！");
		}
	});


}
function showdiv(popWinId) { 
	if (!window.XMLHttpRequest) { 
		for(var i=0;i<selects.length;i++){
			if(selects[i].style.display!='none'){
				selects_display.push(selects[i]);
				selects[i].style.display = "none";
			};
		}
	}
	document.getElementById("bg_mask").style.display ='block';
	document.getElementById(popWinId).style.display ='block';
}

function hidediv(popWinId) {
	if (!window.XMLHttpRequest) { 
		for(var i=0;i<selects_display.length;i++){
			selects_display[i].style.display = "";
		}
	}
	document.getElementById("bg_mask").style.display ='none';
	document.getElementById(popWinId).style.display ='none';
}

function tariffSelectBranch(e){
	var div = $(e);
	var dataStr = div.attr("data");	
	data_tariff = JSON.parse(dataStr);
	comm_dept_id = data_tariff.dept_no;//业务类型id
}

function tariffConfirmBranch(){
	if(comm_dept_id==null||comm_dept_id==''){
		$.alert("请选择终端类型");
		return;
	}
	hidediv('fee_search_branch');
	document.getElementById("comm_dept").value =  data_tariff.dept_name;
}

function tariffDeleteBranch(){
	hidediv('fee_search_branch');
	//busi_type_id = '';
	data_tariff='';
	document.getElementById("comm_dept").value =  '';
}

function getChnlCodeListByGrid(){
	data = {
		    dept_no : deptNo
	};
	var GetURl = application.fullPath + "/authority/hnCommissionList/qryInfoChnlCodeListByGridId";
	$.ajax({
		url: GetURl,
		type: 'post',
		waitMsg: "正在处理",
		data : data,
		success: function(message) {
			$("#tariff_list_chnl").html('');
			var infoBranchVoList = message.args.infoBranchVoList;
			$.each(infoBranchVoList, function(i, item) {
				var data = JSON.stringify(item);
				showdiv('fee_search_chnl');
				//YUN-773
				$("#tariff_list_chnl").append('<li class="list"><div class="left"><div class="left_lable">'
						+'<a class="tip" href="javascript:void(0)" >'
						+item.dept_name
						+'<span class="tip_info width_32" style="z-index:999;">'+item.dept_name+'</span></a>'
						+'</div><div class="right_data">'								
					  +' <input name="fee_search_check" onClick="tariffSelectChnl(this)"  type="radio"  data=\''+data+'\' id=\''+item.dept_no+'\'></input></div></div></li>');
	
			
			});
			//解决最后一行资费提示文字叠加到浮选框头部，预留空白位置
			/*$("#tariff_list").append('<div class="list"></div>');
			$("#tariff_list").append('<div class="list"></div>');
			$("#tariff_list").append('<div class="list"></div>');*/
		},
		error:function(message){
			$.error("系统失败，请稍后再试！");
		}
	});


}
function showdiv(popWinId) { 
	if (!window.XMLHttpRequest) { 
		for(var i=0;i<selects.length;i++){
			if(selects[i].style.display!='none'){
				selects_display.push(selects[i]);
				selects[i].style.display = "none";
			};
		}
	}
	document.getElementById("bg_mask").style.display ='block';
	document.getElementById(popWinId).style.display ='block';
}

function hidediv(popWinId) {
	if (!window.XMLHttpRequest) { 
		for(var i=0;i<selects_display.length;i++){
			selects_display[i].style.display = "";
		}
	}
	document.getElementById("bg_mask").style.display ='none';
	document.getElementById(popWinId).style.display ='none';
}

function tariffSelectChnl(e){
	var div = $(e);
	var dataStr = div.attr("data");	
	data_tariff = JSON.parse(dataStr);
	comm_dept_id = data_tariff.dept_no;//业务类型id
}

function tariffConfirmChnl(){
	if(comm_dept_id==null||comm_dept_id==''){
		$.alert("请选择终端类型");
		return;
	}
	hidediv('fee_search_chnl');
	document.getElementById("comm_dept").value =  data_tariff.dept_name;
}

function tariffDeleteChnl(){
	hidediv('fee_search_chnl');
	//busi_type_id = '';
	data_tariff='';
	document.getElementById("comm_dept").value =  '';
}

function selectData(){
	if("5" == operKind){
		if (""==comm_dept_id){
			$.alert("请选择省公司成本类型，进行查询！");
		} else {
			selectHnCommissionListByBranch();
		}
	} else if("4" == operKind) {
		if (""==comm_dept_id){
			$.alert("请选择分公司成本类型，进行查询！");
		} else {
			selectHnCommissionListByDeptNo();
		}
	} else if ("3" == operKind){
	  //
	} else if ("2" == operKind){
		selectHnCommissionListByChnlCode();
	} else if ("1" == operKind){
		selectHnCommissionListByOperId();
	}
}

/**
 * 根据operid佣金查询
 */
function selectHnCommissionListByOperId() {
	
	var comm_type = $.trim($("#comm_type").val());
	var start_time = $.trim($("#smeeting").val());
	var end_time = $.trim($("#emeeting").val());
	var data= {
		"oper_id":operNo,
		"start_date":start_time,
		"end_date":end_time,
		"comm_type":""
	};
	var GetURl = application.fullPath + "/authority/hnCommissionList/querySelfHelpCommissionListByOperId";
	$.ajax({
		url: GetURl,
		type: 'post',
		waitMsg: "查询...",
		data : data,
		success: function(message) {
			$('#paylogTable>tbody>tr').remove();
			var infoCommissionVoList= message.args.infoCommissionVoList;
			var htmlNew = '';
			var comm_type_show ='';
			var commType = '';
			var orderSource = '';
			if(infoCommissionVoList != null || infoCommissionVoList.length != 0){
				for (var i = 0; i <infoCommissionVoList.length; i++) {
					common = infoCommissionVoList[i];
					if(common.comm_type == "102"){
						comm_type_show = "省公司规则";
					}
					else if(common.comm_type == "103"){
						comm_type_show = "分公司规则";
					}
					else if(common.comm_type == "104"){
						comm_type_show = "网格规则";
					}
					else{
						comm_type_show = "";
					}
					
					if("1"==common.comm_type){
						commType = "开户";
					} else if ("2"==common.comm_type){
						commType = "变更";
					} else if ("3"==common.comm_type){
						commType = "缴费";
					} else if ("4"==common.comm_type){
						commType = "终端销售";
					} else if ("99"==common.comm_type){
						commType = "返销";
					}
					
					if("1"==common.order_source){
						orderSource = "即时激励录入";
					} else if ("2"==common.order_source){
						orderSource ="沃营销系统";
					}
					
					if("01"==common.cost_type){
						deptType = "省公司";
					} else if ("02"==common.cost_type){
						deptType = "分公司";
					} else if ("04"==common.cost_type){
						deptType = "网格";
					} else {
						deptType = "";
					}
					
					var html ='<tr>'
					    +'<td class="text_center" nowrap>'+common.rule_id+'</td>'
			            +'<td class="text_center"nowrap>'+commType+'</td>'
			            +'<td class="text_center"nowrap>'+common.comm_fee+'.00￥</td>'
			            +'<td class="text_center"nowrap>'+common.comm_date+'</td>'
			            +'<td class="text_center"nowrap>'+common.cs_order_id+'</td>'
			            +'<td class="text_center"nowrap>'+common.device_number+'</td>'
			            +'<td class="text_center"nowrap>'+common.product_name+'</td>'
					    +'<td class="text_center"nowrap>'+common.oper_name+'</td>'
						+'<td class="text_center"nowrap>'+common.province_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.branch_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.grid_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.agent_name+'</td>'
			            +'<td class="text_center"nowrap>'+deptType+'</td>'
			            +'<td class="text_center"nowrap>'+orderSource+'</td>'
			            +'</tr>';
						htmlNew += html;
				}
			}
			
			$('#paylogTable>tbody').append(htmlNew);
		},
		error:function(message){
			$.error("系统失败，请稍后再试。");
		}
	});
}


/**
 * 根据网格佣金查询
 */
function selectHnCommissionListByDeptNo() {
	var comm_type = $.trim($("#comm_type").val());
	var data= {
		"grid_dept":comm_dept_id,
		//"create_date":start_time,
		//"end_date":end_time,
		"comm_type":""
	};
	var GetURl = application.fullPath + "/authority/hnCommissionList/querySelfHelpCommissionListByDeptNo";
	$.ajax({
		url: GetURl,
		type: 'post',
		waitMsg: "查询...",
		data : data,
		success: function(message) {
			$('#paylogTable>tbody>tr').remove();
			var infoCommissionVoList= message.args.infoCommissionVoList;
			var htmlNew = '';
			var comm_type_show ='';
			var commType = '';
			var orderSource = '';
			if(infoCommissionVoList != null || infoCommissionVoList.length != 0){
				for (var i = 0; i <infoCommissionVoList.length; i++) {
					common = infoCommissionVoList[i];
					if(common.comm_type == "102"){
						comm_type_show = "省公司规则";
					}
					else if(common.comm_type == "103"){
						comm_type_show = "分公司规则";
					}
					else if(common.comm_type == "104"){
						comm_type_show = "网格规则";
					}
					else{
						comm_type_show = "";
					}
					
					if("1"==common.comm_type){
						commType = "开户";
					} else if ("2"==common.comm_type){
						commType = "变更";
					} else if ("3"==common.comm_type){
						commType = "缴费";
					} else if ("4"==common.comm_type){
						commType = "终端销售";
					} else if ("99"==common.comm_type){
						commType = "返销";
					}
					
					if("1"==common.order_source){
						orderSource = "即时激励录入";
					} else if ("2"==common.order_source){
						orderSource ="沃营销系统";
					}
					
					if("01"==common.cost_type){
						deptType = "省公司";
					} else if ("02"==common.cost_type){
						deptType = "分公司";
					} else if ("04"==common.cost_type){
						deptType = "网格";
					} else {
						deptType = "";
					}
					
					var html ='<tr>'
					    +'<td class="text_center" nowrap>'+common.rule_id+'</td>'
			            +'<td class="text_center"nowrap>'+commType+'</td>'
			            +'<td class="text_center"nowrap>'+common.comm_fee+'.00￥</td>'
			            +'<td class="text_center"nowrap>'+common.comm_date+'</td>'
			            +'<td class="text_center"nowrap>'+common.cs_order_id+'</td>'
			            +'<td class="text_center"nowrap>'+common.device_number+'</td>'
			            +'<td class="text_center"nowrap>'+common.product_name+'</td>'
					    +'<td class="text_center"nowrap>'+common.oper_name+'</td>'
						+'<td class="text_center"nowrap>'+common.province_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.branch_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.grid_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.agent_name+'</td>'
			            +'<td class="text_center"nowrap>'+deptType+'</td>'
			            +'<td class="text_center"nowrap>'+orderSource+'</td>'
			            +'</tr>';
						htmlNew += html;
				}
			}
			
			$('#paylogTable>tbody').append(htmlNew);
		},
		error:function(message){
			$.error("系统失败，请稍后再试。");
		}
	});
}
/**
* 根据分公司佣金查询
*/
function selectHnCommissionListByBranch() {
	var comm_type = $.trim($("#comm_type").val());
	var data= {
		"branch_dept":comm_dept_id
		//"create_date":start_time,
		//"end_date":end_time,
		//"comm_type":""
	};
	var GetURl = application.fullPath + "/authority/hnCommissionList/querySelfHelpCommissionListByBranchId";
	$.ajax({
		url: GetURl,
		type: 'post',
		waitMsg: "查询...",
		data : data,
		success: function(message) {
			$('#paylogTable>tbody>tr').remove();
			var infoCommissionVoList= message.args.infoCommissionVoList;
			var htmlNew = '';
			var comm_type_show ='';
			var commType = '';
			var orderSource = '';
			if(infoCommissionVoList != null || infoCommissionVoList.length != 0){
				for (var i = 0; i <infoCommissionVoList.length; i++) {
					common = infoCommissionVoList[i];
					if(common.comm_type == "102"){
						comm_type_show = "省公司规则";
					}
					else if(common.comm_type == "103"){
						comm_type_show = "分公司规则";
					}
					else if(common.comm_type == "104"){
						comm_type_show = "网格规则";
					}
					else{
						comm_type_show = "";
					}
					
					if("1"==common.comm_type){
						commType = "开户";
					} else if ("2"==common.comm_type){
						commType = "变更";
					} else if ("3"==common.comm_type){
						commType = "缴费";
					} else if ("4"==common.comm_type){
						commType = "终端销售";
					} else if ("99"==common.comm_type){
						commType = "返销";
					}
					
					if("1"==common.order_source){
						orderSource = "即时激励录入";
					} else if ("2"==common.order_source){
						orderSource ="沃营销系统";
					}
					
					if("01"==common.cost_type){
						deptType = "省公司";
					} else if ("02"==common.cost_type){
						deptType = "分公司";
					} else if ("04"==common.cost_type){
						deptType = "网格";
					} else {
						deptType = "";
					}
					
					var html ='<tr>'
					    +'<td class="text_center" nowrap>'+common.rule_id+'</td>'
			            +'<td class="text_center"nowrap>'+commType+'</td>'
			            +'<td class="text_center"nowrap>'+common.comm_fee+'.00￥</td>'
			            +'<td class="text_center"nowrap>'+common.comm_date+'</td>'
			            +'<td class="text_center"nowrap>'+common.cs_order_id+'</td>'
			            +'<td class="text_center"nowrap>'+common.device_number+'</td>'
			            +'<td class="text_center"nowrap>'+common.product_name+'</td>'
					    +'<td class="text_center"nowrap>'+common.oper_name+'</td>'
						+'<td class="text_center"nowrap>'+common.province_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.branch_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.grid_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.agent_name+'</td>'
			            +'<td class="text_center"nowrap>'+deptType+'</td>'
			            +'<td class="text_center"nowrap>'+orderSource+'</td>'
			            +'</tr>';
						htmlNew += html;
				}
			}
			
			$('#paylogTable>tbody').append(htmlNew);
		},
		error:function(message){
			$.error("系统失败，请稍后再试。");
		}
	});
}

/**
* 根据渠道佣金查询
*/
function selectHnCommissionListByChnlCode() {
	var data= {
		"chnl_code":chnlCode
	};
	var GetURl = application.fullPath + "/authority/hnCommissionList/querySelfHelpCommissionListByDeptNo";
	$.ajax({
		url: GetURl,
		type: 'post',
		waitMsg: "查询...",
		data : data,
		success: function(message) {
			$('#paylogTable>tbody>tr').remove();
			var infoCommissionVoList= message.args.infoCommissionVoList;
			var htmlNew = '';
			var comm_type_show ='';
			var commType = '';
			var orderSource = '';
			if(infoCommissionVoList != null || infoCommissionVoList.length != 0){
				for (var i = 0; i <infoCommissionVoList.length; i++) {
					common = infoCommissionVoList[i];
					if(common.comm_type == "102"){
						comm_type_show = "省公司规则";
					}
					else if(common.comm_type == "103"){
						comm_type_show = "分公司规则";
					}
					else if(common.comm_type == "104"){
						comm_type_show = "网格规则";
					}
					else{
						comm_type_show = "";
					}
					
					if("1"==common.comm_type){
						commType = "开户";
					} else if ("2"==common.comm_type){
						commType = "变更";
					} else if ("3"==common.comm_type){
						commType = "缴费";
					} else if ("4"==common.comm_type){
						commType = "终端销售";
					} else if ("99"==common.comm_type){
						commType = "返销";
					}
					
					if("1"==common.order_source){
						orderSource = "即时激励录入";
					} else if ("2"==common.order_source){
						orderSource ="沃营销系统";
					}
					
					if("01"==common.cost_type){
						deptType = "省公司";
					} else if ("02"==common.cost_type){
						deptType = "分公司";
					} else if ("04"==common.cost_type){
						deptType = "网格";
					} else {
						deptType = "";
					}
					
					var html ='<tr>'
					    +'<td class="text_center" nowrap>'+common.rule_id+'</td>'
			            +'<td class="text_center"nowrap>'+commType+'</td>'
			            +'<td class="text_center"nowrap>'+common.comm_fee+'.00￥</td>'
			            +'<td class="text_center"nowrap>'+common.comm_date+'</td>'
			            +'<td class="text_center"nowrap>'+common.cs_order_id+'</td>'
			            +'<td class="text_center"nowrap>'+common.device_number+'</td>'
			            +'<td class="text_center"nowrap>'+common.product_name+'</td>'
					    +'<td class="text_center"nowrap>'+common.oper_name+'</td>'
						+'<td class="text_center"nowrap>'+common.province_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.branch_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.grid_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.agent_name+'</td>'
			            +'<td class="text_center"nowrap>'+deptType+'</td>'
			            +'<td class="text_center"nowrap>'+orderSource+'</td>'
			            +'</tr>';
						htmlNew += html;
				}
			}
			
			$('#paylogTable>tbody').append(htmlNew);
		},
		error:function(message){
			$.error("系统失败，请稍后再试。");
		}
	});
}
/**
 * ---------------------------分页-------------
 */
function selectDataNum(){
	if("5" == operKind){
		if (""==comm_dept_id){
			$.alert("请选择省公司成本类型，进行查询！");
		} else {
			selectHnCommissionListByBranchPage();
		}
	} else if("4" == operKind) {
		if (""==comm_dept_id){
			$.alert("请选择分公司成本类型，进行查询！");
		} else {
			selectHnCommissionListByDeptNoPage();
		}
	} else if ("3" == operKind){
	  //
	} else if ("2" == operKind){
		selectHnCommissionListByChnlCodePage();
	} else if ("1" == operKind){
		selectHnCommissionListByOperIdPage();
	}
}
/**
 * 根据operid佣金查询--分页
 */
function selectHnCommissionListByOperId() {
	
	var data= {
		"oper_id":operNo,
		"pageNo":pageNo,
		"pageSize":pageSize
	};
	var GetURl = application.fullPath + "/authority/hnCommissionList/querySelfHelpCommissionListByOperIdPage";
	$.ajax({
		url: GetURl,
		type: 'post',
		waitMsg: "查询...",
		data : data,
		success: function(message) {
			$('#paylogTable>tbody>tr').remove();
			var pageModelVo= message.args.pageModelVo.list;
			topPageNo = message.args.pageModelVo.topPageNo;
			previousPageNo = message.args.pageModelVo.previousPageNo;
			nextPageNo = message.args.pageModelVo.nextPageNo;
			totalPages = message.args.pageModelVo.totalPages;
			bottomPageNo = message.args.pageModelVo.bottomPageNo;
			pageNo = message.args.pageModelVo.pageNo;
			var htmlNew = '';
			var comm_type_show ='';
			var commType = '';
			var orderSource = '';
			if(pageModelVo != null || pageModelVo.length != 0){
				for (var i = 0; i <pageModelVo.length; i++) {
					common = pageModelVo[i];
					if(common.comm_type == "102"){
						comm_type_show = "省公司规则";
					}
					else if(common.comm_type == "103"){
						comm_type_show = "分公司规则";
					}
					else if(common.comm_type == "104"){
						comm_type_show = "网格规则";
					}
					else{
						comm_type_show = "";
					}
					
					if("1"==common.comm_type){
						commType = "开户";
					} else if ("2"==common.comm_type){
						commType = "变更";
					} else if ("3"==common.comm_type){
						commType = "缴费";
					} else if ("4"==common.comm_type){
						commType = "终端销售";
					} else if ("99"==common.comm_type){
						commType = "返销";
					}
					
					if("1"==common.order_source){
						orderSource = "即时激励录入";
					} else if ("2"==common.order_source){
						orderSource ="沃营销系统";
					}
					
					if("01"==common.cost_type){
						deptType = "省公司";
					} else if ("02"==common.cost_type){
						deptType = "分公司";
					} else if ("04"==common.cost_type){
						deptType = "网格";
					} else {
						deptType = "";
					}
					
					var html ='<tr>'
					    +'<td class="text_center" nowrap>'+common.rule_id+'</td>'
			            +'<td class="text_center"nowrap>'+commType+'</td>'
			            +'<td class="text_center"nowrap>'+common.comm_fee+'.00￥</td>'
			            +'<td class="text_center"nowrap>'+common.comm_date+'</td>'
			            +'<td class="text_center"nowrap>'+common.cs_order_id+'</td>'
			            +'<td class="text_center"nowrap>'+common.device_number+'</td>'
			            +'<td class="text_center"nowrap>'+common.product_name+'</td>'
					    +'<td class="text_center"nowrap>'+common.oper_name+'</td>'
						+'<td class="text_center"nowrap>'+common.province_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.branch_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.grid_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.agent_name+'</td>'
			            +'<td class="text_center"nowrap>'+deptType+'</td>'
			            +'<td class="text_center"nowrap>'+orderSource+'</td>'
			            +'</tr>';
						htmlNew += html;
				}
			}
			
			$('#paylogTable>tbody').append(htmlNew);
		},
		error:function(message){
			$.error("系统失败，请稍后再试。");
		}
	});
}

/**
* 根据分公司佣金查询--分页查询
*/
function selectHnCommissionListByBranchPage() {
	var data= {
		"branch_dept":comm_dept_id
		"pageNo":pageNo,
		"pageSize":pageSize
	};
	var GetURl = application.fullPath + "/authority/hnCommissionList/querySelfHelpCommissionListByBranchIdPage";
	$.ajax({
		url: GetURl,
		type: 'post',
		waitMsg: "查询...",
		data : data,
		success: function(message) {
			$('#paylogTable>tbody>tr').remove();
			var pageModelVo= message.args.pageModelVo.list;
			topPageNo = message.args.pageModelVo.topPageNo;
			previousPageNo = message.args.pageModelVo.previousPageNo;
			nextPageNo = message.args.pageModelVo.nextPageNo;
			totalPages = message.args.pageModelVo.totalPages;
			bottomPageNo = message.args.pageModelVo.bottomPageNo;
			pageNo = message.args.pageModelVo.pageNo;
			var htmlNew = '';
			var comm_type_show ='';
			var commType = '';
			var orderSource = '';
			if(pageModelVo != null || pageModelVo.length != 0){
				for (var i = 0; i <pageModelVo.length; i++) {
					common = pageModelVo[i];
					if(common.comm_type == "102"){
						comm_type_show = "省公司规则";
					}
					else if(common.comm_type == "103"){
						comm_type_show = "分公司规则";
					}
					else if(common.comm_type == "104"){
						comm_type_show = "网格规则";
					}
					else{
						comm_type_show = "";
					}
					
					if("1"==common.comm_type){
						commType = "开户";
					} else if ("2"==common.comm_type){
						commType = "变更";
					} else if ("3"==common.comm_type){
						commType = "缴费";
					} else if ("4"==common.comm_type){
						commType = "终端销售";
					} else if ("99"==common.comm_type){
						commType = "返销";
					}
					
					if("1"==common.order_source){
						orderSource = "即时激励录入";
					} else if ("2"==common.order_source){
						orderSource ="沃营销系统";
					}
					
					if("01"==common.cost_type){
						deptType = "省公司";
					} else if ("02"==common.cost_type){
						deptType = "分公司";
					} else if ("04"==common.cost_type){
						deptType = "网格";
					} else {
						deptType = "";
					}
					
					var html ='<tr>'
					    +'<td class="text_center" nowrap>'+common.rule_id+'</td>'
			            +'<td class="text_center"nowrap>'+commType+'</td>'
			            +'<td class="text_center"nowrap>'+common.comm_fee+'.00￥</td>'
			            +'<td class="text_center"nowrap>'+common.comm_date+'</td>'
			            +'<td class="text_center"nowrap>'+common.cs_order_id+'</td>'
			            +'<td class="text_center"nowrap>'+common.device_number+'</td>'
			            +'<td class="text_center"nowrap>'+common.product_name+'</td>'
					    +'<td class="text_center"nowrap>'+common.oper_name+'</td>'
						+'<td class="text_center"nowrap>'+common.province_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.branch_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.grid_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.agent_name+'</td>'
			            +'<td class="text_center"nowrap>'+deptType+'</td>'
			            +'<td class="text_center"nowrap>'+orderSource+'</td>'
			            +'</tr>';
						htmlNew += html;
				}
			}
			
			$('#paylogTable>tbody').append(htmlNew);
		},
		error:function(message){
			$.error("系统失败，请稍后再试。");
		}
	});
	 $("#previousPage").attr("disabled", true); 
		document.getElementById("pageNo").innerHTML = pageNo;
}
/**
 * 
 *   ---------------------首页，上一页，下一页，尾页，跳转-----------------------
 * 
 */
function jumpTo(){
	var jumpTo = $("#jumpTo").val();
	if ("" ==jumpTo){
		jumpTo = 1;
	} 
	var data= {
		"oper_id":operNo,
		"pageNo":jumpTo,
		"pageSize":pageSize	
	};
	var GetURl = "";
	if("5" == operKind){
		 GetURl = application.fullPath + "/authority/hnCommissionList/querySelfHelpCommissionListByBranchIdPage";
	} else if("4" == operKind) {
	  //	
	} else if ("3" == operKind){
	  //
	} else if ("2" == operKind){
	  //
	} else if ("1" == operKind){
		var GetURl = application.fullPath + "/authority/hnCommissionList/querySelfHelpCommissionListByOperIdPage";
	}
	$.ajax({
		url: GetURl,
		type: 'post',
		waitMsg: "查询...",
		data : data,
		success: function(message) {
			$('#paylogTable>tbody>tr').remove();
			var pageModelVo= message.args.pageModelVo.list;
			topPageNo = message.args.pageModelVo.topPageNo;
			previousPageNo = message.args.pageModelVo.previousPageNo;
			nextPageNo = message.args.pageModelVo.nextPageNo;
			totalPages = message.args.pageModelVo.totalPages;
			bottomPageNo = message.args.pageModelVo.bottomPageNo;
			pageNo = message.args.pageModelVo.pageNo;
			var htmlNew = '';
			var comm_type_show ='';
			var commType = '';
			var orderSource = '';
			if(pageModelVo != null || pageModelVo.length != 0){
				for (var i = 0; i <pageModelVo.length; i++) {
					common = pageModelVo[i];
					if(common.comm_type == "102"){
						comm_type_show = "省公司规则";
					}
					else if(common.comm_type == "103"){
						comm_type_show = "分公司规则";
					}
					else if(common.comm_type == "104"){
						comm_type_show = "网格规则";
					}
					else{
						comm_type_show = "";
					}
					
					if("1"==common.comm_type){
						commType = "开户";
					} else if ("2"==common.comm_type){
						commType = "变更";
					} else if ("3"==common.comm_type){
						commType = "缴费";
					} else if ("4"==common.comm_type){
						commType = "终端销售";
					} else if ("99"==common.comm_type){
						commType = "返销";
					}
					
					if("1"==common.order_source){
						orderSource = "即时激励录入";
					} else if ("2"==common.order_source){
						orderSource ="沃营销系统";
					}
					
					if("01"==common.cost_type){
						deptType = "省公司";
					} else if ("02"==common.cost_type){
						deptType = "分公司";
					} else if ("04"==common.cost_type){
						deptType = "网格";
					} else {
						deptType = "";
					}
					
					var html ='<tr>'
					    +'<td class="text_center" nowrap>'+common.rule_id+'</td>'
			            +'<td class="text_center"nowrap>'+commType+'</td>'
			            +'<td class="text_center"nowrap>'+common.comm_fee+'.00￥</td>'
			            +'<td class="text_center"nowrap>'+common.comm_date+'</td>'
			            +'<td class="text_center"nowrap>'+common.cs_order_id+'</td>'
			            +'<td class="text_center"nowrap>'+common.device_number+'</td>'
			            +'<td class="text_center"nowrap>'+common.product_name+'</td>'
					    +'<td class="text_center"nowrap>'+common.oper_name+'</td>'
						+'<td class="text_center"nowrap>'+common.province_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.branch_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.grid_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.agent_name+'</td>'
			            +'<td class="text_center"nowrap>'+deptType+'</td>'
			            +'<td class="text_center"nowrap>'+orderSource+'</td>'
			            +'</tr>';
						htmlNew += html;
				}
			}
			
			$('#paylogTable>tbody').append(htmlNew);
		},
		error:function(message){
			$.alert("系统失败，请稍后再试。");
		}
	});
	if (1>=pageNo){
		   $("#previousPage").attr("disabled", false); 
	   } else {
		   $("#previousPage").attr("disabled", true); 
	   }
	if (totalPages<=jumpTo){
		   $("#nextPage").attr("disabled", true); 
	   } else {
		   $("#nextPage").attr("disabled", false); 
	   }
	document.getElementById("pageNo").innerHTML = jumpTo;

}

function topPage() {

	var data="";
	var GetURl = "";
	if("5" == operKind){
		data= {
				"pageNo":"1",
				"pageSize":pageSize	
			}
		 GetURl = application.fullPath + "/authority/hnCommissionList/querySelfHelpCommissionListByBranchIdPage";
	} else if("4" == operKind) {
	  //	
	} else if ("3" == operKind){
	  //
	} else if ("2" == operKind){
	  //
	} else if ("1" == operKind){
		data= {
			"oper_id":operNo,
			"pageNo":"1",
			"pageSize":pageSize	
		}
		   GetURl = application.fullPath + "/authority/hnCommissionList/querySelfHelpCommissionListByOperIdPage";
	}
	$.ajax({
		url: GetURl,
		type: 'post',
		waitMsg: "查询...",
		data : data,
		success: function(message) {
			$('#paylogTable>tbody>tr').remove();
			var pageModelVo= message.args.pageModelVo.list;
			topPageNo = message.args.pageModelVo.topPageNo;
			previousPageNo = message.args.pageModelVo.previousPageNo;
			nextPageNo = message.args.pageModelVo.nextPageNo;
			totalPages = message.args.pageModelVo.totalPages;
			bottomPageNo = message.args.pageModelVo.bottomPageNo;
			pageNo = message.args.pageModelVo.pageNo;
			var htmlNew = '';
			var comm_type_show ='';
			var commType = '';
			var orderSource = '';
			if(pageModelVo != null || pageModelVo.length != 0){
				for (var i = 0; i <pageModelVo.length; i++) {
					common = pageModelVo[i];
					if(common.comm_type == "102"){
						comm_type_show = "省公司规则";
					}
					else if(common.comm_type == "103"){
						comm_type_show = "分公司规则";
					}
					else if(common.comm_type == "104"){
						comm_type_show = "网格规则";
					}
					else{
						comm_type_show = "";
					}
					
					if("1"==common.comm_type){
						commType = "开户";
					} else if ("2"==common.comm_type){
						commType = "变更";
					} else if ("3"==common.comm_type){
						commType = "缴费";
					} else if ("4"==common.comm_type){
						commType = "终端销售";
					} else if ("99"==common.comm_type){
						commType = "返销";
					}
					
					if("1"==common.order_source){
						orderSource = "即时激励录入";
					} else if ("2"==common.order_source){
						orderSource ="沃营销系统";
					}
					
					if("01"==common.cost_type){
						deptType = "省公司";
					} else if ("02"==common.cost_type){
						deptType = "分公司";
					} else if ("04"==common.cost_type){
						deptType = "网格";
					} else {
						deptType = "";
					}
					
					var html ='<tr>'
					    +'<td class="text_center" nowrap>'+common.rule_id+'</td>'
			            +'<td class="text_center"nowrap>'+commType+'</td>'
			            +'<td class="text_center"nowrap>'+common.comm_fee+'.00￥</td>'
			            +'<td class="text_center"nowrap>'+common.comm_date+'</td>'
			            +'<td class="text_center"nowrap>'+common.cs_order_id+'</td>'
			            +'<td class="text_center"nowrap>'+common.device_number+'</td>'
			            +'<td class="text_center"nowrap>'+common.product_name+'</td>'
					    +'<td class="text_center"nowrap>'+common.oper_name+'</td>'
						+'<td class="text_center"nowrap>'+common.province_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.branch_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.grid_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.agent_name+'</td>'
			            +'<td class="text_center"nowrap>'+deptType+'</td>'
			            +'<td class="text_center"nowrap>'+orderSource+'</td>'
			            +'</tr>';
						htmlNew += html;
				}
			}
			
			$('#paylogTable>tbody').append(htmlNew);
		},
		error:function(message){
			$.alert("系统失败，请稍后再试。");
		}
	});
    document.getElementById("pageNo").innerHTML = 1;
	$("#previousPage").attr("disabled", true);
	$("#nextPage").attr("disabled", false);
}

function previousPage() {
	var data="";
	var GetURl = "";
	if("5" == operKind){
		data= {
				"pageNo":previousPageNo,
				"pageSize":pageSize	
			}
		 GetURl = application.fullPath + "/authority/hnCommissionList/querySelfHelpCommissionListByBranchIdPage";
	} else if("4" == operKind) {
	  //	
	} else if ("3" == operKind){
	  //
	} else if ("2" == operKind){
	  //
	} else if ("1" == operKind){
		data= {
			"oper_id":operNo,
			"pageNo":previousPageNo,
			"pageSize":pageSize	
		}
		   GetURl = application.fullPath + "/authority/hnCommissionList/querySelfHelpCommissionListByOperIdPage";
	}
	$.ajax({
		url: GetURl,
		type: 'post',
		waitMsg: "查询...",
		data : data,
		success: function(message) {
			$('#paylogTable>tbody>tr').remove();
			var pageModelVo= message.args.pageModelVo.list;
			topPageNo = message.args.pageModelVo.topPageNo;
			previousPageNo = message.args.pageModelVo.previousPageNo;
			nextPageNo = message.args.pageModelVo.nextPageNo;
			totalPages = message.args.pageModelVo.totalPages;
			bottomPageNo = message.args.pageModelVo.bottomPageNo;
			pageNo = message.args.pageModelVo.pageNo;
			var htmlNew = '';
			var comm_type_show ='';
			var commType = '';
			var orderSource = '';
			if(pageModelVo != null || pageModelVo.length != 0){
				for (var i = 0; i <pageModelVo.length; i++) {
					common = pageModelVo[i];
					if(common.comm_type == "102"){
						comm_type_show = "省公司规则";
					}
					else if(common.comm_type == "103"){
						comm_type_show = "分公司规则";
					}
					else if(common.comm_type == "104"){
						comm_type_show = "网格规则";
					}
					else{
						comm_type_show = "";
					}
					
					if("1"==common.comm_type){
						commType = "开户";
					} else if ("2"==common.comm_type){
						commType = "变更";
					} else if ("3"==common.comm_type){
						commType = "缴费";
					} else if ("4"==common.comm_type){
						commType = "终端销售";
					} else if ("99"==common.comm_type){
						commType = "返销";
					}
					
					if("1"==common.order_source){
						orderSource = "即时激励录入";
					} else if ("2"==common.order_source){
						orderSource ="沃营销系统";
					}
					
					if("01"==common.cost_type){
						deptType = "省公司";
					} else if ("02"==common.cost_type){
						deptType = "分公司";
					} else if ("04"==common.cost_type){
						deptType = "网格";
					} else {
						deptType = "";
					}
					
					var html ='<tr>'
					    +'<td class="text_center" nowrap>'+common.rule_id+'</td>'
			            +'<td class="text_center"nowrap>'+commType+'</td>'
			            +'<td class="text_center"nowrap>'+common.comm_fee+'.00￥</td>'
			            +'<td class="text_center"nowrap>'+common.comm_date+'</td>'
			            +'<td class="text_center"nowrap>'+common.cs_order_id+'</td>'
			            +'<td class="text_center"nowrap>'+common.device_number+'</td>'
			            +'<td class="text_center"nowrap>'+common.product_name+'</td>'
					    +'<td class="text_center"nowrap>'+common.oper_name+'</td>'
						+'<td class="text_center"nowrap>'+common.province_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.branch_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.grid_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.agent_name+'</td>'
			            +'<td class="text_center"nowrap>'+deptType+'</td>'
			            +'<td class="text_center"nowrap>'+orderSource+'</td>'
			            +'</tr>';
						htmlNew += html;
				}
			}
			
			$('#paylogTable>tbody').append(htmlNew);
		},
		error:function(message){
			$.alert("系统失败，请稍后再试。");
		}
	});
   if ("2"==pageNo){
	   $("#previousPage").attr("disabled", true); 
	   $("#nextPage").attr("disabled", false);
   } else {
	   $("#previousPage").attr("disabled", false); 
	   $("#nextPage").attr("disabled", false);
   }
   document.getElementById("pageNo").innerHTML = pageNo-1;
}    

function nextPage() {
  
	var data="";
	var GetURl = "";
	if("5" == operKind){
		data= {
				"pageNo":nextPageNo,
				"pageSize":pageSize	
			}
		 GetURl = application.fullPath + "/authority/hnCommissionList/querySelfHelpCommissionListByBranchIdPage";
	} else if("4" == operKind) {
	  //	
	} else if ("3" == operKind){
	  //
	} else if ("2" == operKind){
	  //
	} else if ("1" == operKind){
		data= {
			"oper_id":operNo,
			"pageNo":nextPageNo,
			"pageSize":pageSize	
		}
		   GetURl = application.fullPath + "/authority/hnCommissionList/querySelfHelpCommissionListByOperIdPage";
	}
	$.ajax({
		url: GetURl,
		type: 'post',
		waitMsg: "查询...",
		data : data,
		success: function(message) {
			$('#paylogTable>tbody>tr').remove();
			var pageModelVo= message.args.pageModelVo.list;
			topPageNo = message.args.pageModelVo.topPageNo;
			previousPageNo = message.args.pageModelVo.previousPageNo;
			nextPageNo = message.args.pageModelVo.nextPageNo;
			totalPages = message.args.pageModelVo.totalPages;
			bottomPageNo = message.args.pageModelVo.bottomPageNo;
			pageNo = message.args.pageModelVo.pageNo;
			var htmlNew = '';
			var comm_type_show ='';
			var commType = '';
			var orderSource = '';
			if(pageModelVo != null || pageModelVo.length != 0){
				for (var i = 0; i <pageModelVo.length; i++) {
					common = pageModelVo[i];
					if(common.comm_type == "102"){
						comm_type_show = "省公司规则";
					}
					else if(common.comm_type == "103"){
						comm_type_show = "分公司规则";
					}
					else if(common.comm_type == "104"){
						comm_type_show = "网格规则";
					}
					else{
						comm_type_show = "";
					}
					
					if("1"==common.comm_type){
						commType = "开户";
					} else if ("2"==common.comm_type){
						commType = "变更";
					} else if ("3"==common.comm_type){
						commType = "缴费";
					} else if ("4"==common.comm_type){
						commType = "终端销售";
					} else if ("99"==common.comm_type){
						commType = "返销";
					}
					
					if("1"==common.order_source){
						orderSource = "即时激励录入";
					} else if ("2"==common.order_source){
						orderSource ="沃营销系统";
					}
					
					if("01"==common.cost_type){
						deptType = "省公司";
					} else if ("02"==common.cost_type){
						deptType = "分公司";
					} else if ("04"==common.cost_type){
						deptType = "网格";
					} else {
						deptType = "";
					}
					
					var html ='<tr>'
					    +'<td class="text_center" nowrap>'+common.rule_id+'</td>'
			            +'<td class="text_center"nowrap>'+commType+'</td>'
			            +'<td class="text_center"nowrap>'+common.comm_fee+'.00￥</td>'
			            +'<td class="text_center"nowrap>'+common.comm_date+'</td>'
			            +'<td class="text_center"nowrap>'+common.cs_order_id+'</td>'
			            +'<td class="text_center"nowrap>'+common.device_number+'</td>'
			            +'<td class="text_center"nowrap>'+common.product_name+'</td>'
					    +'<td class="text_center"nowrap>'+common.oper_name+'</td>'
						+'<td class="text_center"nowrap>'+common.province_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.branch_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.grid_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.agent_name+'</td>'
			            +'<td class="text_center"nowrap>'+deptType+'</td>'
			            +'<td class="text_center"nowrap>'+orderSource+'</td>'
			            +'</tr>';
						htmlNew += html;
				}
			}
			
			$('#paylogTable>tbody').append(htmlNew);
		},
		error:function(message){
			$.alert("系统失败，请稍后再试。");
		}
	});
	  if (bottomPageNo-1==pageNo){
		 $("#previousPage").attr("disabled", false); 
		 $("#nextPage").attr("disabled", true);
	   } else {
		   $("#previousPage").attr("disabled", false);  
		   $("#nextPage").attr("disabled", false);
	   }
	 document.getElementById("pageNo").innerHTML = pageNo+1;
}

function bottomPage() {
	var data="";
	var GetURl = "";
	if("5" == operKind){
		data= {
				"pageNo":bottomPageNo,
				"pageSize":pageSize	
			}
		 GetURl = application.fullPath + "/authority/hnCommissionList/querySelfHelpCommissionListByBranchIdPage";
	} else if("4" == operKind) {
	  //	
	} else if ("3" == operKind){
	  //
	} else if ("2" == operKind){
	  //
	} else if ("1" == operKind){
		data= {
			"oper_id":operNo,
			"pageNo":bottomPageNo,
			"pageSize":pageSize	
		}
		   GetURl = application.fullPath + "/authority/hnCommissionList/querySelfHelpCommissionListByOperIdPage";
	}
	$.ajax({
		url: GetURl,
		type: 'post',
		waitMsg: "查询...",
		data : data,
		success: function(message) {
			$('#paylogTable>tbody>tr').remove();
			var pageModelVo= message.args.pageModelVo.list;
			topPageNo = message.args.pageModelVo.topPageNo;
			previousPageNo = message.args.pageModelVo.previousPageNo;
			nextPageNo = message.args.pageModelVo.nextPageNo;
			totalPages = message.args.pageModelVo.totalPages;
			bottomPageNo = message.args.pageModelVo.bottomPageNo;
			pageNo = message.args.pageModelVo.pageNo;
			var htmlNew = '';
			var comm_type_show ='';
			var commType = '';
			var orderSource = '';
			if(pageModelVo != null || pageModelVo.length != 0){
				for (var i = 0; i <pageModelVo.length; i++) {
					common = pageModelVo[i];
					if(common.comm_type == "102"){
						comm_type_show = "省公司规则";
					}
					else if(common.comm_type == "103"){
						comm_type_show = "分公司规则";
					}
					else if(common.comm_type == "104"){
						comm_type_show = "网格规则";
					}
					else{
						comm_type_show = "";
					}
					
					if("1"==common.comm_type){
						commType = "开户";
					} else if ("2"==common.comm_type){
						commType = "变更";
					} else if ("3"==common.comm_type){
						commType = "缴费";
					} else if ("4"==common.comm_type){
						commType = "终端销售";
					} else if ("99"==common.comm_type){
						commType = "返销";
					}
					
					if("1"==common.order_source){
						orderSource = "即时激励录入";
					} else if ("2"==common.order_source){
						orderSource ="沃营销系统";
					}
					
					if("01"==common.cost_type){
						deptType = "省公司";
					} else if ("02"==common.cost_type){
						deptType = "分公司";
					} else if ("04"==common.cost_type){
						deptType = "网格";
					} else {
						deptType = "";
					}
					
					var html ='<tr>'
					    +'<td class="text_center" nowrap>'+common.rule_id+'</td>'
			            +'<td class="text_center"nowrap>'+commType+'</td>'
			            +'<td class="text_center"nowrap>'+common.comm_fee+'.00￥</td>'
			            +'<td class="text_center"nowrap>'+common.comm_date+'</td>'
			            +'<td class="text_center"nowrap>'+common.cs_order_id+'</td>'
			            +'<td class="text_center"nowrap>'+common.device_number+'</td>'
			            +'<td class="text_center"nowrap>'+common.product_name+'</td>'
					    +'<td class="text_center"nowrap>'+common.oper_name+'</td>'
						+'<td class="text_center"nowrap>'+common.province_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.branch_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.grid_name+'</td>'
			            +'<td class="text_center"nowrap>'+common.agent_name+'</td>'
			            +'<td class="text_center"nowrap>'+deptType+'</td>'
			            +'<td class="text_center"nowrap>'+orderSource+'</td>'
			            +'</tr>';
						htmlNew += html;
				}
			}
			
			$('#paylogTable>tbody').append(htmlNew);
		},
		error:function(message){
			$.alert("系统失败，请稍后再试。");
		}
	});
	$("#nextPage").attr("disabled", true);
	$("#previousPage").attr("disabled", false);
	document.getElementById("pageNo").innerHTML = bottomPageNo;

}
