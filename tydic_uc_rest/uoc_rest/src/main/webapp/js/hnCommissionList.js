var ruleId ="";
var operNo = "";
var deptNo = "";
var topPageNo = "";
var previousPageNo = "";
var nextPageNo = "";
var totalPages = "";
var bottomPageNo = "";
var pageNo = 1 ;
var pageSize = 10 ;
$(document).ready(function() {
	operNo = $("#operNo").val();
	deptNo = $("#deptNo").val();
	selectDataNum();
	$("#query").click(function() {
		selectData();
	});
	rule_method_add();
	rule_method_delete();
	rule_method_update();
});

function selectDataNum() {
	var ruleFlag = "";
	var chnlType = "";
	var data= {
		"create_oper_id":operNo,
		"pageNo":pageNo,
		"pageSize":pageSize
	};
	var GetURl = application.fullPath + "/authority/ruleCommission/queryRuleCommissionListPage";
	$.ajax({
		url: GetURl,
		type: 'post',
		//waitMsg: "查询订单",
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
			var oper_type="";
			var comm_typ="";
			var deptType = "";
			var effDate = "";
			var expDate = "";
			if(pageModelVo != null || pageModelVo.length != 0){
				for (var i = 0; i <pageModelVo.length; i++) {
					rule = pageModelVo[i];					
					
					if(rule.oper_type=="1"){
						oper_type="员工";
					}
					else if(rule.oper_type=="1"){
						oper_type="主管";
					}
					else{
						oper_type="请选择";
					}
					
					if(rule.comm_type=="102"){
						comm_type="省公司规则";
					}
					else if(rule.comm_type=="103"){
						comm_type="分公司规则";
					}
					else if(rule.comm_type=="104"){
						comm_type="网格规则";
					}
					else{
						comm_type="";
					}
					
					if("1"==rule.flag){
						ruleFlag="有效";
					} else {
						ruleFlag="无效";
					}
					if("01"==rule.chnl_type){
						chnlType="中小渠道";
					} else if("02"==rule.chnl_type){
						chnlType="自由厅";
					} else if ("03"==rule.chnl_type){
						chnlType="自有渠道";
					} else {
						chnlType = "";
					}
					
					if("01"==rule.dept_type){
						deptType = "省公司";
					} else if ("02"==rule.dept_type){
						deptType = "分公司";
					} else if ("04"==rule.dept_type){
						deptType = "网格";
					} else {
						deptType = "";
					}
					 effDate = rule.eff_date;
					 expDate = rule.exp_date;
					var html ='<tr>'
					    +'<td class="text_center"><input type="checkbox" name="checkInfo" id="checkInfo" onclick="check(this)"></td>'
					    +'<td style="display:none">'+rule.rule_id+'</td>'
					    +'<td class="text_center">'+deptType+'</td>'
			            +'<td class="text_center">'+rule.dept_name+'</td>'
						+'<td class="text_center">'+chnlType+'</td>'
			            +'<td class="text_center">'+rule.busi_type+'</td>'
			            +'<td class="text_center">'+rule.product_name+'</td>'
			            +'<td class="text_center">'+rule.ternimal_name+'</td>'
			            +'<td class="text_center">'+rule.base_value_1+'.00￥</td>'
			            +'<td class="text_center">'+rule.base_value_2+'.00￥</td>'
			            +'<td class="text_center">'+rule.base_value_3+'.00￥</td>'
			            +'<td class="text_center">'+rule.base_value_4+'.00￥</td>'
			            +'<td class="text_center">'+effDate.substr(0, effDate.indexOf('00:00:00'))+'</td>'
			            +'<td class="text_center">'+expDate.substr(0, expDate.indexOf('00:00:00'))+'</td>'
			            +'<td class="text_center">'+ruleFlag+'</td>'
			            //+'<td class="text_center">'+rule.create_date+'</td>'
			            +'</tr>';
						htmlNew += html;
				}
			}
			
			$('#paylogTable>tbody').append(htmlNew);
			document.getElementById("totalPages").innerHTML = totalPages;
		},
		error:function(message){
			$.error("系统失败，请稍后再试。");
		}
	});
	 $("#previousPage").attr("disabled", true); 
	 document.getElementById("pageNo").innerHTML = pageNo;
}

function jumpTo(){
	var jumpTo = $("#jumpTo").val();
	if ("" ==jumpTo){
		jumpTo = 1;
	} 
	var data= {
	    "create_oper_id":operNo,
		"pageNo":jumpTo,
		"pageSize":pageSize	
	};
	var GetURl = application.fullPath + "/authority/ruleCommission/queryRuleCommissionListPage";
	$.ajax({
		url: GetURl,
		type: 'post',
		//waitMsg: "查询订单",
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
			var oper_type="";
			var comm_typ="";
			var deptType = "";
			var effDate = "";
			var expDate = "";
			if(pageModelVo != null || pageModelVo.length != 0){
				for (var i = 0; i <pageModelVo.length; i++) {
					rule = pageModelVo[i];		
					
					if(rule.oper_type=="1"){
						oper_type="员工";
					}
					else if(rule.oper_type=="1"){
						oper_type="主管";
					}
					else{
						oper_type="请选择";
					}
					
					if(rule.comm_type=="102"){
						comm_type="省公司规则";
					}
					else if(rule.comm_type=="103"){
						comm_type="分公司规则";
					}
					else if(rule.comm_type=="104"){
						comm_type="网格规则";
					}
					else{
						comm_type="";
					}
					
					if("1"==rule.flag){
						ruleFlag="有效";
					} else {
						ruleFlag="无效";
					}
					if("01"==rule.chnl_type){
						chnlType="中小渠道";
					} else if("02"==rule.chnl_type){
						chnlType="自由厅";
					} else if ("03"==rule.chnl_type){
						chnlType="自有渠道";
					} else {
						chnlType = "";
					}
					
					if("01"==rule.dept_type){
						deptType = "省公司";
					} else if ("02"==rule.dept_type){
						deptType = "分公司";
					} else if ("04"==rule.dept_type){
						deptType = "网格";
					} else {
						deptType = "";
					}
					 effDate = rule.eff_date;
					 expDate = rule.exp_date;
					var html ='<tr>'
					    +'<td class="text_center"><input type="checkbox" name="checkInfo" id="checkInfo" onclick="check(this)"></td>'
					    +'<td style="display:none">'+rule.rule_id+'</td>'
					    +'<td class="text_center">'+deptType+'</td>'
			            +'<td class="text_center">'+rule.dept_name+'</td>'
						+'<td class="text_center">'+chnlType+'</td>'
			            +'<td class="text_center">'+rule.busi_type+'</td>'
			            +'<td class="text_center">'+rule.product_name+'</td>'
			            +'<td class="text_center">'+rule.ternimal_name+'</td>'
			            +'<td class="text_center">'+rule.base_value_1+'.00￥</td>'
			            +'<td class="text_center">'+rule.base_value_2+'.00￥</td>'
			            +'<td class="text_center">'+rule.base_value_3+'.00￥</td>'
			            +'<td class="text_center">'+rule.base_value_4+'.00￥</td>'
			            +'<td class="text_center">'+effDate.substr(0, effDate.indexOf('00:00:00'))+'</td>'
			            +'<td class="text_center">'+expDate.substr(0, expDate.indexOf('00:00:00'))+'</td>'
			            +'<td class="text_center">'+ruleFlag+'</td>'
			            //+'<td class="text_center">'+rule.create_date+'</td>'
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

	var data= {
	    "create_oper_id":operNo,
		"pageNo":"1",
		"pageSize":pageSize	
	};
	var GetURl = application.fullPath + "/authority/ruleCommission/queryRuleCommissionListPage";
	$.ajax({
		url: GetURl,
		type: 'post',
		//waitMsg: "查询订单",
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
			var oper_type="";
			var comm_typ="";
			var deptType = "";
			var effDate = "";
			var expDate = "";
			if(pageModelVo != null || pageModelVo.length != 0){
				for (var i = 0; i <pageModelVo.length; i++) {
					rule = pageModelVo[i];					
					
					if(rule.oper_type=="1"){
						oper_type="员工";
					}
					else if(rule.oper_type=="1"){
						oper_type="主管";
					}
					else{
						oper_type="请选择";
					}
					
					if(rule.comm_type=="102"){
						comm_type="省公司规则";
					}
					else if(rule.comm_type=="103"){
						comm_type="分公司规则";
					}
					else if(rule.comm_type=="104"){
						comm_type="网格规则";
					}
					else{
						comm_type="";
					}
					
					if("1"==rule.flag){
						ruleFlag="有效";
					} else {
						ruleFlag="无效";
					}
					if("01"==rule.chnl_type){
						chnlType="中小渠道";
					} else if("02"==rule.chnl_type){
						chnlType="自由厅";
					} else if ("03"==rule.chnl_type){
						chnlType="自有渠道";
					} else {
						chnlType = "";
					}
					
					if("01"==rule.dept_type){
						deptType = "省公司";
					} else if ("02"==rule.dept_type){
						deptType = "分公司";
					} else if ("04"==rule.dept_type){
						deptType = "网格";
					} else {
						deptType = "";
					}
					 effDate = rule.eff_date;
					 expDate = rule.exp_date;
					var html ='<tr>'
					    +'<td class="text_center"><input type="checkbox" name="checkInfo" id="checkInfo" onclick="check(this)"></td>'
					    +'<td style="display:none">'+rule.rule_id+'</td>'
					    +'<td class="text_center">'+deptType+'</td>'
			            +'<td class="text_center">'+rule.dept_name+'</td>'
						+'<td class="text_center">'+chnlType+'</td>'
			            +'<td class="text_center">'+rule.busi_type+'</td>'
			            +'<td class="text_center">'+rule.product_name+'</td>'
			            +'<td class="text_center">'+rule.ternimal_name+'</td>'
			            +'<td class="text_center">'+rule.base_value_1+'.00￥</td>'
			            +'<td class="text_center">'+rule.base_value_2+'.00￥</td>'
			            +'<td class="text_center">'+rule.base_value_3+'.00￥</td>'
			            +'<td class="text_center">'+rule.base_value_4+'.00￥</td>'
			            +'<td class="text_center">'+effDate.substr(0, effDate.indexOf('00:00:00'))+'</td>'
			            +'<td class="text_center">'+expDate.substr(0, expDate.indexOf('00:00:00'))+'</td>'
			            +'<td class="text_center">'+ruleFlag+'</td>'
			            //+'<td class="text_center">'+rule.create_date+'</td>'
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

	var data= {
		"create_oper_id":operNo,
		"pageNo":previousPageNo,
		"pageSize":pageSize
	};
	var GetURl = application.fullPath + "/authority/ruleCommission/queryRuleCommissionListPage";
	$.ajax({
		url: GetURl,
		type: 'post',
		//waitMsg: "查询订单",
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
			var oper_type="";
			var comm_typ="";
			var deptType = "";
			var effDate = "";
			var expDate = "";
			if(pageModelVo != null || pageModelVo.length != 0){
				for (var i = 0; i <pageModelVo.length; i++) {
					rule = pageModelVo[i];					
					
					if(rule.oper_type=="1"){
						oper_type="员工";
					}
					else if(rule.oper_type=="1"){
						oper_type="主管";
					}
					else{
						oper_type="请选择";
					}
					
					if(rule.comm_type=="102"){
						comm_type="省公司规则";
					}
					else if(rule.comm_type=="103"){
						comm_type="分公司规则";
					}
					else if(rule.comm_type=="104"){
						comm_type="网格规则";
					}
					else{
						comm_type="";
					}
					
					if("1"==rule.flag){
						ruleFlag="有效";
					} else {
						ruleFlag="无效";
					}
					if("01"==rule.chnl_type){
						chnlType="中小渠道";
					} else if("02"==rule.chnl_type){
						chnlType="自由厅";
					} else if ("03"==rule.chnl_type){
						chnlType="自有渠道";
					} else {
						chnlType = "";
					}
					
					if("01"==rule.dept_type){
						deptType = "省公司";
					} else if ("02"==rule.dept_type){
						deptType = "分公司";
					} else if ("04"==rule.dept_type){
						deptType = "网格";
					} else {
						deptType = "";
					}
					 effDate = rule.eff_date;
					 expDate = rule.exp_date;
					var html ='<tr>'
					    +'<td class="text_center"><input type="checkbox" name="checkInfo" id="checkInfo" onclick="check(this)"></td>'
					    +'<td style="display:none">'+rule.rule_id+'</td>'
					    +'<td class="text_center">'+deptType+'</td>'
			            +'<td class="text_center">'+rule.dept_name+'</td>'
						+'<td class="text_center">'+chnlType+'</td>'
			            +'<td class="text_center">'+rule.busi_type+'</td>'
			            +'<td class="text_center">'+rule.product_name+'</td>'
			            +'<td class="text_center">'+rule.ternimal_name+'</td>'
			            +'<td class="text_center">'+rule.base_value_1+'.00￥</td>'
			            +'<td class="text_center">'+rule.base_value_2+'.00￥</td>'
			            +'<td class="text_center">'+rule.base_value_3+'.00￥</td>'
			            +'<td class="text_center">'+rule.base_value_4+'.00￥</td>'
			            +'<td class="text_center">'+effDate.substr(0, effDate.indexOf('00:00:00'))+'</td>'
			            +'<td class="text_center">'+expDate.substr(0, expDate.indexOf('00:00:00'))+'</td>'
			            +'<td class="text_center">'+ruleFlag+'</td>'
			            //+'<td class="text_center">'+rule.create_date+'</td>'
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

function bottomPage() {
    
	var data= {
		"create_oper_id":operNo,
		"pageNo":bottomPageNo,
		"pageSize":pageSize	
	};
	var GetURl = application.fullPath + "/authority/ruleCommission/queryRuleCommissionListPage";
	$.ajax({
		url: GetURl,
		type: 'post',
		//waitMsg: "查询订单",
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
			var oper_type="";
			var comm_typ="";
			var deptType = "";
			var effDate = "";
			var expDate = "";
			if(pageModelVo != null || pageModelVo.length != 0){
				for (var i = 0; i <pageModelVo.length; i++) {
					rule = pageModelVo[i];						
					
					if(rule.oper_type=="1"){
						oper_type="员工";
					}
					else if(rule.oper_type=="1"){
						oper_type="主管";
					}
					else{
						oper_type="请选择";
					}
					
					if(rule.comm_type=="102"){
						comm_type="省公司规则";
					}
					else if(rule.comm_type=="103"){
						comm_type="分公司规则";
					}
					else if(rule.comm_type=="104"){
						comm_type="网格规则";
					}
					else{
						comm_type="";
					}
					
					if("1"==rule.flag){
						ruleFlag="有效";
					} else {
						ruleFlag="无效";
					}
					if("01"==rule.chnl_type){
						chnlType="中小渠道";
					} else if("02"==rule.chnl_type){
						chnlType="自由厅";
					} else if ("03"==rule.chnl_type){
						chnlType="自有渠道";
					} else {
						chnlType = "";
					}
					
					if("01"==rule.dept_type){
						deptType = "省公司";
					} else if ("02"==rule.dept_type){
						deptType = "分公司";
					} else if ("04"==rule.dept_type){
						deptType = "网格";
					} else {
						deptType = "";
					}
					 effDate = rule.eff_date;
					 expDate = rule.exp_date;
					var html ='<tr>'
					    +'<td class="text_center"><input type="checkbox" name="checkInfo" id="checkInfo" onclick="check(this)"></td>'
					    +'<td style="display:none">'+rule.rule_id+'</td>'
					    +'<td class="text_center">'+deptType+'</td>'
			            +'<td class="text_center">'+rule.dept_name+'</td>'
						+'<td class="text_center">'+chnlType+'</td>'
			            +'<td class="text_center">'+rule.busi_type+'</td>'
			            +'<td class="text_center">'+rule.product_name+'</td>'
			            +'<td class="text_center">'+rule.ternimal_name+'</td>'
			            +'<td class="text_center">'+rule.base_value_1+'.00￥</td>'
			            +'<td class="text_center">'+rule.base_value_2+'.00￥</td>'
			            +'<td class="text_center">'+rule.base_value_3+'.00￥</td>'
			            +'<td class="text_center">'+rule.base_value_4+'.00￥</td>'
			            +'<td class="text_center">'+effDate.substr(0, effDate.indexOf('00:00:00'))+'</td>'
			            +'<td class="text_center">'+expDate.substr(0, expDate.indexOf('00:00:00'))+'</td>'
			            +'<td class="text_center">'+ruleFlag+'</td>'
			            //+'<td class="text_center">'+rule.create_date+'</td>'
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

function nextPage() {
	  
	var data= {
	    "create_oper_id":operNo,
		"pageNo":nextPageNo,
		"pageSize":pageSize	
	};
	var GetURl = application.fullPath + "/authority/ruleCommission/queryRuleCommissionListPage";
	$.ajax({
		url: GetURl,
		type: 'post',
		//waitMsg: "查询订单",
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
			var oper_type="";
			var comm_typ="";
			var deptType = "";
			var effDate = "";
			var expDate = "";
			if(pageModelVo != null || pageModelVo.length != 0){
				for (var i = 0; i <pageModelVo.length; i++) {
					rule = pageModelVo[i];					
					
					if(rule.oper_type=="1"){
						oper_type="员工";
					}
					else if(rule.oper_type=="1"){
						oper_type="主管";
					}
					else{
						oper_type="请选择";
					}
					
					if(rule.comm_type=="102"){
						comm_type="省公司规则";
					}
					else if(rule.comm_type=="103"){
						comm_type="分公司规则";
					}
					else if(rule.comm_type=="104"){
						comm_type="网格规则";
					}
					else{
						comm_type="";
					}
					
					if("1"==rule.flag){
						ruleFlag="有效";
					} else {
						ruleFlag="无效";
					}
					if("01"==rule.chnl_type){
						chnlType="中小渠道";
					} else if("02"==rule.chnl_type){
						chnlType="自由厅";
					} else if ("03"==rule.chnl_type){
						chnlType="自有渠道";
					} else {
						chnlType = "";
					}
					
					if("01"==rule.dept_type){
						deptType = "省公司";
					} else if ("02"==rule.dept_type){
						deptType = "分公司";
					} else if ("04"==rule.dept_type){
						deptType = "网格";
					} else {
						deptType = "";
					}
					 effDate = rule.eff_date;
					 expDate = rule.exp_date;
					var html ='<tr>'
					    +'<td class="text_center"><input type="checkbox" name="checkInfo" id="checkInfo" onclick="check(this)"></td>'
					    +'<td style="display:none">'+rule.rule_id+'</td>'
					    +'<td class="text_center">'+deptType+'</td>'
			            +'<td class="text_center">'+rule.dept_name+'</td>'
						+'<td class="text_center">'+chnlType+'</td>'
			            +'<td class="text_center">'+rule.busi_type+'</td>'
			            +'<td class="text_center">'+rule.product_name+'</td>'
			            +'<td class="text_center">'+rule.ternimal_name+'</td>'
			            +'<td class="text_center">'+rule.base_value_1+'.00￥</td>'
			            +'<td class="text_center">'+rule.base_value_2+'.00￥</td>'
			            +'<td class="text_center">'+rule.base_value_3+'.00￥</td>'
			            +'<td class="text_center">'+rule.base_value_4+'.00￥</td>'
			            +'<td class="text_center">'+effDate.substr(0, effDate.indexOf('00:00:00'))+'</td>'
			            +'<td class="text_center">'+expDate.substr(0, expDate.indexOf('00:00:00'))+'</td>'
			            +'<td class="text_center">'+ruleFlag+'</td>'
			            //+'<td class="text_center">'+rule.create_date+'</td>'
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


function selectData() {
	//var operId = $('#operId').val();
	//var start_time = $.trim($("#date_begin").val());
	//var end_time = $.trim($("#date_end").val());
	var comm_type = $.trim($("#comm_type").val());
	var effect_flag = $.trim($("#effect_flag").val());
	var ruleFlag = "";
	var chnlType = "";
	var data= {
		"create_oper_id":operNo,
		//"create_date":start_time,
		//"end_date":end_time,
		"comm_type":comm_type,
		"flag":effect_flag
	};
	var GetURl = application.fullPath + "/authority/ruleCommission/queryRuleCommissionListByOperId";
	$.ajax({
		url: GetURl,
		type: 'post',
		waitMsg: "查询订单",
		data : data,
		success: function(message) {
			$('#paylogTable>tbody>tr').remove();
			var ruleCommissionList= message.args.infoRuleCommissionVoList;
			var htmlNew = '';
			var oper_type="";
			var comm_typ="";
			var deptType = "";
			var effDate = "";
			var expDate = "";
			var order_sub_type="";
			if(ruleCommissionList != null || ruleCommissionList.length != 0){
				for (var i = 0; i <ruleCommissionList.length; i++) {
					rule = ruleCommissionList[i];
					
					if(rule.oper_type=="1"){
						oper_type="员工";
					}
					else if(rule.oper_type=="1"){
						oper_type="主管";
					}
					else{
						oper_type="请选择";
					}
					
					if(rule.comm_type=="102"){
						comm_type="省公司规则";
					}
					else if(rule.comm_type=="103"){
						comm_type="分公司规则";
					}
					else if(rule.comm_type=="104"){
						comm_type="网格规则";
					}
					else{
						comm_type="";
					}
					
					if("1"==rule.flag){
						ruleFlag="有效"
					} else {
						ruleFlag="无效"
					}
					if("01"==rule.chnl_type){
						chnlType="中小渠道";
					} else if("02"==rule.chnl_type){
						chnlType="自有厅";
					} else if ("03"==rule.chnl_type){
						chnlType="自有渠道";
					} else{
						chnlType="";
					}
					if("1"==rule.order_sub_type){
						order_sub_type="开户";
					} else if("2"==rule.order_sub_type){
						order_sub_type="活动变更";
					} else if ("3"==rule.order_sub_type){
						order_sub_type="产品变更";
					} else{
						order_sub_type="";
					}				
					
					if("01"==rule.dept_type){
						deptType = "省公司";
					} else if ("02"==rule.dept_type){
						deptType = "分公司";
					} else if ("04"==rule.dept_type){
						deptType = "网格";
					} else {
						deptType = "";
					}
					 effDate = rule.eff_date;
					 expDate = rule.exp_date;
					var html ='<tr>'
					    +'<td class="text_center"><input type="checkbox" name="checkInfo" id="checkInfo" onclick="check(this)"></td>'
					    +'<td style="display:none">'+rule.rule_id+'</td>'
					    +'<td class="text_center">'+deptType+'</td>'
			            +'<td class="text_center">'+rule.dept_name+'</td>'
						+'<td class="text_center">'+chnlType+'</td>'
						+'<td class="text_center">'+order_sub_type+'</td>'
			            +'<td class="text_center">'+rule.busi_type+'</td>'
			            +'<td class="text_center">'+rule.product_name+'</td>'
			            +'<td class="text_center">'+rule.ternimal_name+'</td>'
			            +'<td class="text_center">'+rule.base_value_1+'.00￥</td>'
			            +'<td class="text_center">'+rule.base_value_2+'.00￥</td>'
			            +'<td class="text_center">'+rule.base_value_3+'.00￥</td>'
			            +'<td class="text_center">'+rule.base_value_4+'.00￥</td>'
			            +'<td class="text_center">'+effDate.substr(0, effDate.indexOf('00:00:00'))+'</td>'
			            +'<td class="text_center">'+expDate.substr(0, expDate.indexOf('00:00:00'))+'</td>'
			            +'<td class="text_center">'+ruleFlag+'</td>'
			            //+'<td class="text_center">'+rule.create_date+'</td>'
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

function rule_method_add(){
	
	$("#addRuleComm").bind("click",function(e){
		window.location.href = application.fullPath + "/authority/index/hnCommissionEdit?create_oper_id="+operNo+"&cost_dept="+deptNo;
	});

}

function rule_method_update(){
	
	$("#updateRuleComm").bind("click",function(e){
		
		var checkboxs = document.getElementsByName('checkInfo');
		var j = 0; //
		for (var i = 0; i < checkboxs.length; i++) {
			 if(checkboxs[i].checked){
		          j++;
		      }
		}
		if (j>1){
			$.alert("请选择一条数据进行操作");
		} else if (j<1){
			$.alert("请选择一条数据进行操作！");
		} else {
			window.location.href = application.fullPath + "/authority/index/hnCommissionModify?rule_id="+ruleId;
		}
	});

}

function rule_method_delete(){
	
	$("#delRuleComm").bind("click",function(e){

		
		var checkboxs = document.getElementsByName('checkInfo');
		var j = 0; //
		for (var i = 0; i < checkboxs.length; i++) {
			 if(checkboxs[i].checked){
		          j++;
		      }
		}
		if (j>1){
			$.alert("请选择一条数据进行操作");
		} else if (j<1){
			$.alert("请选择一条数据进行操作！");
		} else {
			if(window.confirm('请确认是否要删除该数据？')){
				rule_method_delete_f();
				return true;
		     }else{
		        return false;
		    }
		}
	});

}

function rule_method_delete_f(){
	var data= {
		"oper_id":operNo,
		"rule_id":ruleId
	};
	var GetURl = application.fullPath + "/authority/ruleCommission/removeRuleCommission";
	$.ajax({
		url: GetURl,
		type: 'post',
		waitMsg: "正在处理",
		data : data,
		success: function(message) {
			$.success("该佣金规则删除成功！");
			selectData();
		},
		error:function(message){
			$.error("系统失败，请稍后再试！");
		}
	});

}

function check(o){
	if (!o.checked) {
		return;
	}
	var tr = o.parentNode.parentNode;
	var tds = tr.cells;
	for (var i = 0; i < tds.length; i++) {
		if (i < 2) {
			ruleId = tds[i].innerHTML
		}
	}
}
