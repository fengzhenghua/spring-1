var data_tariff='';
var busi_type_id='';
var busi_sub_type_id='';
var product_type_id='';
var terminal_type_id='';
var create_oper_id = '';
var comm_dept_id = '';
var comm_dept_type = '';
var deptNo = '';
var quota_id = '';
var activity_group_id ='';
$(document).ready(function() {
	$("#base2").val("0");
	create_oper_id = $("#create_oper_id").val();
	deptNo = $("#dept_no").val();
	rule_method_save();
	rule_method_cancel();
});


/**
 * 查询佣金部门
 */
function getCommDept(){
	var comm_type = document.getElementById("comm_type").value;
	if('-1'==comm_type){
		$.alert("请先选择成本类型！");
	} else {
		 if ('102'==comm_type){
		  document.getElementById("comm_dept").value="海南省";
		  comm_dept_id = "root";
		  comm_dept_type ="01"
		 } else if ('103'==comm_type){
			getBranchByProvinceNo()
			comm_dept_type ='02';
		} else if('104'==comm_type) {
			getGridByDeptNo();
			comm_dept_type = '04';
		}
	}	
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
		$.alert("请选择成本部门");
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
			$("#tariff_list_grid").html('');
			var infoGridVoList = message.args.infoGridVoList;
			$.each(infoGridVoList, function(i, item) {
				var data = JSON.stringify(item);
				showdiv('fee_search_grid');
				//YUN-773
				$("#tariff_list_grid").append('<li class="list"><div class="left"><div class="left_lable">'
						+'<a class="tip" href="javascript:void(0)" >'
						+item.dept_name
						+'<span class="tip_info width_32" style="z-index:999;">'+item.dept_name+'</span></a>'
						+'</div><div class="right_data">'								
					  +' <input name="fee_search_check" onClick="tariffSelectGrid(this)"  type="radio"  data=\''+data+'\' id=\''+item.dept_no+'\'></input></div></div></li>');
	
			
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

function tariffSelectGrid(e){
	var div = $(e);
	var dataStr = div.attr("data");	
	data_tariff = JSON.parse(dataStr);
	comm_dept_id = data_tariff.dept_no;//业务类型id
}

function tariffConfirmGrid(){
	if(comm_dept_id==null||comm_dept_id==''){
		$.error("请选择终端类型");
		return;
	}
	hidediv('fee_search_grid');
	document.getElementById("comm_dept").value =  data_tariff.dept_name;
}

function tariffDeletGrid(){
	hidediv('fee_search_grid');
	//busi_type_id = '';
	data_tariff='';
	document.getElementById("comm_dept").value =  '';
}

function rule_method_save(){
	$("#saveRuleComm").bind("click",function(e){
		var chnl_type = document.getElementById("chnl_type").value;
		if ('-1'==chnl_type){
			chnl_type = "";
		}
		var order_sub_type = document.getElementById("order_sub_type").value;
		if ('-1'==order_sub_type){
			order_sub_type = "";
		}
		var base1 = $.trim($("#base1").val());
		var base2 = $.trim($("#base2").val());
		var base3 = $.trim($("#base3").val());
		var base4 = $.trim($("#base4").val());
		var rule_name = $.trim($("#rule_name").val());
		var settle_type = document.getElementById("settle_type").value;
		var comm_type = document.getElementById("comm_type").value;
		if ('-1'==comm_type){
			comm_type = "";
		}
		var smeeting = $("#smeeting").val().trim();
		var emeeting = $("#emeeting").val().trim();
		if( checkRuleInfo(base1,base2,comm_dept_id) == false ){
			return;
		}
		var data= {
			//"oper_id":oper_id,
		    "chnl_type":chnl_type,
		    "rule_name":rule_name,
		    "settle_type":settle_type,
		    "activity_type":activity_group_id,
		    "policy_type":quota_id,
		    "product_type":product_type_id,
		    "terminal_type":terminal_type_id,
			"order_type":busi_type_id,
			"order_sub_type":order_sub_type,
			"ofr_id":"*",
			"create_oper_id":create_oper_id,
			"base_value_1":base1,
			"base_value_2":base2,
			"base_value_3":base3,
			"base_value_4":base4,
			"comm_type":comm_type,
			"cost_type":comm_dept_type,
			"cost_dept":comm_dept_id,	
		    "eff_date":smeeting,
		    "exp_date":emeeting,
		    "flag":"1"
		};
		var GetURl = application.fullPath + "/authority/ruleCommission/createRuleCommission";
		$.ajax({
			url: GetURl,
			type: 'post',
			waitMsg: "正在处理",
			data : data,
			success: function(message) {
				$.success("该佣金规则新增成功！");
				setTimeout('backQry()',2000); 
			},
			error:function(message){
				$.error("系统失败，请稍后再试！");
			}
		});


	});
}
//返回查询页面
function backQry(){
	window.location.href = application.fullPath + "/authority/index/hnCommissionList";
}

function rule_method_cancel(){
	$("#cancelRuleComm").bind("click",function(e){
		window.location.href = application.fullPath + "/authority/index/hnCommissionList";
	});
}


function getActivityGroup(){
	var groupName = $("#TariffActivityInput").val().trim();
	data = {
			group_name:groupName,
			flag:"1"
	};
	var GetURl = application.fullPath + "/authority/activityVirtualGroup/qryActivityVirtualGroup";
	$.ajax({
		url: GetURl,
		type: 'post',
		waitMsg: "正在处理",
		data : data,
		success: function(message) {
			$("#activity_list").html('');
			var ActivityVirtualGroupList = message.args.CodeActivityVirtualGroup;
			$.each(ActivityVirtualGroupList, function(i, item) {
				var data = JSON.stringify(item);
				showdiv('activity_search');
				//YUN-773
				$("#activity_list").append('<li class="list"><div class="left"><div class="left_lable">'
						+'<a class="tip" href="javascript:void(0)" >'
						+item.group_name
						+'<span class="tip_info width_32" style="z-index:999;">'+item.group_name+'</span></a>'
						+'</div><div class="right_data">'								
					  +' <input name="activity_search_check" onClick="activitySelect(this)"  type="radio"  data=\''+data+'\' id=\''+item.group_id+'\'></input></div></div></li>');
	
			
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


function getRuleQuota(){
	var quotaName = $("#TariffRuleQuotaInput").val().trim();
	data = {
			quota_name:quotaName,
			flag:"1"
	};
	var GetURl = application.fullPath + "/authority/ruleQuota/qryRuleQuota";
	$.ajax({
		url: GetURl,
		type: 'post',
		waitMsg: "正在处理",
		data : data,
		success: function(message) {
			$("#ruleQuota_list").html('');
			var ruleQuotaList = message.args.rule_quota;
			$.each(ruleQuotaList, function(i, item) {
				var data = JSON.stringify(item);
				showdiv('rule_search');
				//YUN-773
				$("#ruleQuota_list").append('<li class="list"><div class="left"><div class="left_lable">'
						+'<a class="tip" href="javascript:void(0)" >'
						+item.quota_name
						+'<span class="tip_info width_32" style="z-index:999;">'+item.quota_name+'</span></a>'
						+'</div><div class="right_data">'								
					  +' <input name="rule_search_check" onClick="ruleQuotaSelect(this)"  type="radio"  data=\''+data+'\' id=\''+item.quota_id+'\'></input></div></div></li>');
	
			
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
/**
 * 查询活动类型
 */
function getOrderType(){
	data = {
			type_code:"busi_type"
	};
	var GetURl = application.fullPath + "/authority/ruleCommission/qryOrderTypeList";
	$.ajax({
		url: GetURl,
		type: 'post',
		waitMsg: "正在处理",
		data : data,
		success: function(message) {
			$("#tariff_list").html('');
			var orderTypeList = message.args.orderTypeList;
			$.each(orderTypeList, function(i, item) {
				var data = JSON.stringify(item);
				showdiv('fee_search');
				//YUN-773
				$("#tariff_list").append('<li class="list"><div class="left"><div class="left_lable">'
						+'<a class="tip" href="javascript:void(0)" >'
						+item.code_name
						+'<span class="tip_info width_32" style="z-index:999;">'+item.code_name+'</span></a>'
						+'</div><div class="right_data">'								
					  +' <input name="fee_search_check" onClick="tariffSelect(this)"  type="radio"  data=\''+data+'\' id=\''+item.code_id+'\'></input></div></div></li>');
	
			
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


function ruleQuotaSelect(e){
	var div = $(e);
	var dataStr = div.attr("data");
	data_tariff = JSON.parse(dataStr);
	quota_id = data_tariff.quota_id;//规则政策id
}

function activitySelect(e){
	var div = $(e);
	var dataStr = div.attr("data");
	data_tariff = JSON.parse(dataStr);
	activity_group_id = data_tariff.group_id;
}

function tariffSelect(e){
	var div = $(e);
	var dataStr = div.attr("data");	
	data_tariff = JSON.parse(dataStr);
	busi_type_id = data_tariff.code_id;//业务类型id
}

function tariffConfirmRuleQuota(){
	if(data_tariff.quota_id==null||data_tariff.quota_id==''){
		hidediv('rule_search');
		$.alert("请选择规则政策");
		return;
	}
	hidediv('rule_search');
	document.getElementById("rule_quota").value =  data_tariff.quota_name;
}

function tariffConfirmActivity(){
	if(data_tariff.group_id==null||data_tariff.group_id==''){
		hidediv('activity_search');
		$.alert("请选择活动虚分组");
		return;
	}
	hidediv('activity_search');
	document.getElementById("activity_group").value =  data_tariff.group_name;
}

function tariffDeleteActivity(){
	hidediv('activity_search');
	activity_group_id = '';
	data_tariff='';
	document.getElementById("activity_group").value =  '';
}

function tariffDeleteRuleQuota(){
	hidediv('rule_search');
	quota_id = '';
	data_tariff='';
	document.getElementById("rule_quota").value =  '';
}


function tariffConfirm(){
	if(data_tariff.code_id==null||data_tariff.code_id==''){
		$.alert("请选择活动类型");
		return;
	}
	hidediv('fee_search');
	document.getElementById("busi_type").value =  data_tariff.code_name;
}

function tariffDelete(){
	hidediv('fee_search');
	busi_type_id = '';
	busi_sub_type_id='';
	data_tariff='';
	document.getElementById("busi_type").value =  '';
}


/**
 * 查询终端类型
 */
function getTerminalModel(){
	data = {
			terminal_name:""
	};
	var GetURl = application.fullPath + "/authority/ruleCommission/qryTerminalModelList";
	$.ajax({
		url: GetURl,
		type: 'post',
		waitMsg: "正在处理",
		data : data,
		success: function(message) {
			$("#tariff_list1").html('');
			var termianlModelList = message.args.termianlModelList;
			$.each(termianlModelList, function(i, item) {
				var data = JSON.stringify(item);
				showdiv('fee_search1');
				//YUN-773
				$("#tariff_list1").append('<li class="list"><div class="left"><div class="left_lable">'
						+'<a class="tip" href="javascript:void(0)" >'
						+item.group_name
						+'<span class="tip_info width_32" style="z-index:999;">'+item.group_name+'</span></a>'
						+'</div><div class="right_data">'								
					  +' <input name="fee_search_check" onClick="tariffTerminalSelect(this)"  type="radio"  data=\''+data+'\' id=\''+item.group_id+'\'></input></div></div></li>');
	
			
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

function tariffTerminalSelect(e){
	var div = $(e);
	var dataStr = div.attr("data");	
	data_tariff = JSON.parse(dataStr);
	terminal_type_id = data_tariff.group_id;//业务类型id
}

function tariffConfirmTerminal(){
	if(terminal_type_id==null||terminal_type_id==''){
		$.alert("请选择终端类型");
		return;
	}
	hidediv('fee_search1');
	document.getElementById("terminal_type").value =  data_tariff.group_name;
}

function tariffDeleteTerminal(){
	hidediv('fee_search1');
	//busi_type_id = '';
	data_tariff='';
	document.getElementById("terminal_type").value =  '';
}


/**
 * 查询产品类型
 */
function getProductModel(){
	data = {
			product_name:""
	};
	var GetURl = application.fullPath + "/authority/ruleCommission/qryProductModelList";
	$.ajax({
		url: GetURl,
		type: 'post',
		waitMsg: "正在处理",
		data : data,
		success: function(message) {
			$("#tariff_list2").html('');
			var productModelList = message.args.productModelList;
			$.each(productModelList, function(i, item) {
				var data = JSON.stringify(item);
				showdiv('fee_search2');
				//YUN-773
				$("#tariff_list2").append('<li class="list"><div class="left"><div class="left_lable">'
						+'<a class="tip" href="javascript:void(0)" >'
						+item.group_name
						+'<span class="tip_info width_32" style="z-index:999;">'+item.group_name+'</span></a>'
						+'</div><div class="right_data">'								
					  +' <input name="fee_search_check" onClick="tariffProductSelect(this)"  type="radio"  data=\''+data+'\' id=\''+item.group_id+'\'></input></div></div></li>');
	
			
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
function tariffProductSelect(e){
	var div = $(e);
	var dataStr = div.attr("data");	
	data_tariff = JSON.parse(dataStr);
	product_type_id = data_tariff.group_id;//业务类型id
}

function tariffConfirmProduct(){
	if(product_type_id==null||product_type_id==''){
		$.alert("请选择产品类型");
		return;
	}
	hidediv('fee_search2');
	document.getElementById("product_type").value =  data_tariff.group_name;
}

function tariffDeleteProduct(){
	hidediv('fee_search2');
	product_id = '';
	data_tariff='';
	document.getElementById("product_type").value =  '';
}

function checkRuleInfo (base1,base2,comm_dept_id){
	if( base1.length == 0){
		$.alert("员工奖励不能为空！");
		return false;
	}
	if( base2.length == 0){
		$.alert("主管奖励不能为空！");
		return false;
	}
	if( comm_dept_id.length == 0){
		$.alert("成本部门不能为空！");
		return false;
	}
	return true;
}