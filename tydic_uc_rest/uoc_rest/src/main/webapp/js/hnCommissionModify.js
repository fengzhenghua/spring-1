
var rule_id = "";
$(document).ready(function() {
    rule_id = $("#rule_id").val();
	rule_method_save();
	selectData();
	rule_method_cancel();
});

function selectData(){
	var rule_flag = "";
	var data={
			"rule_id":rule_id
	}
	var GetURl = application.fullPath + "/authority/ruleCommission/queryRuleCommissionListByRuleId";
	$.ajax({
		url: GetURl,
		type: 'post',
		waitMsg: "修改订单",
		data : data,
		success: function(message) {
			var ruleCommissionList= message.args.infoRuleCommissionVoList;
			var comm_type="";
			var chnlType = "";
			if(ruleCommissionList !=null || ruleCommissionList.length != 0){
				rule = ruleCommissionList[0];
				
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
					comm_type="请选择";
				}
				
				if ("1"==rule.flag){
					rule_flag ="有效"
				} else if ("0"==rule.flag) {
					rule_flag ="无效"
				}
				if("01"==rule.chnl_type){
					chnlType="中小渠道";
				} else if("02"==rule.chnl_type){
					chnlType="自由厅";
				} else if ("03"==rule.chnl_type){
					chnlType="自有渠道";
				}
				$("#order_type").val(rule.busi_type);
				$("#comm_type").val(comm_type);
				$("#chnl_type").val(chnlType);
				$("#product_type").val(rule.product_name);
				$("#terminal_type").val(rule.ternimal_name);
				$("#flag").val(rule_flag);
				$("#base1").val(rule.base_value_1);
				$("#base2").val(rule.base_value_2);
				$("#base3").val(rule.base_value_3);
				$("#base4").val(rule.base_value_4);
			}
			
		},
		error:function(message){
			$.error("系统失败，请稍后再试。");
		}
	});
}

function rule_method_save(){
	$("#saveRuleComm").bind("click",function(e){
		//var oper_id = $.trim($("#oper_id").val());
		//var oper_type = document.getElementById("oper_type").value;
		//var seq_no = $.trim($("#seq_no").val());
		var flag = $.trim($("#flag").val());
		//var quotiety = $.trim($("#quotiety").val());
		var base1 = $.trim($("#base1").val());
		var base2 = $.trim($("#base2").val());
		var base3 = $.trim($("#base3").val());
		var base4 = $.trim($("#base4").val());
		//var comm_type = document.getElementById("comm_type").value;
		var data= {
			//"oper_id":oper_id,
			//"oper_type":oper_type,
			//"order_type":"1",
			//"order_sub_type":"1",
			"rule_id":rule_id,
			//"ofr_id":"*",
			//"create_oper_id":"CF0540",
			//"quotiety":quotiety,
			"base_value_1":base1,
			"base_value_2":base2,
			"base_value_3":base3,
			"base_value_4":base4,
			//"seq_no":seq_no,
			"flag":"1"
		};
		var GetURl = application.fullPath + "/authority/ruleCommission/modifyRuleCommission";
		$.ajax({
			url: GetURl,
			type: 'post',
			waitMsg: "正在处理",
			data : data,
			success: function(message) {
				$.success("该佣金规则修改成功！");
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
//document.getElementById("entityId_id").value;