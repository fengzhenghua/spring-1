<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
<%@ page import="java.util.Date" %>
<%
	String nowDate = DateUtil.getCurrentDate();
	String beforeDate = DateUtil.getAfterMonth(new Date(), -1);
	String load_oper_no=(String)request.getAttribute("load_oper_no");
	String load_area_id=(String)request.getAttribute("load_area_id");
	String oper_type=(String)request.getAttribute("oper_type");
%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>终端店奖</title>
	<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
	<link href="<%=fullPath%>css/groupRcomm.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>js/jquery-ligerui-2.25/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<script src="<%=basePath%>js/jquery-ligerui-2.25/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/jquery-ligerui-2.25/js/core/base.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/jquery-ligerui-2.25/js/plugins/ligerGrid.js" type="text/javascript"></script>
	<script src="<%=fullPath%>js/json2.js" type="text/javascript"></script>
	<script type="text/javascript">
		var sNowDate="<%=beforeDate%>";
		if (sNowDate.length==10) //yyyy-mm-dd
			sNowDate = sNowDate.substr(0, 4) + sNowDate.substr(5, 2);
		else
			sNowDate = sNowDate;

		var dateTime = {
			beforeDate:"<%=beforeDate%>",
			nowDate:sNowDate
		};
	</script>
	<script type="text/javascript">
		var audit = {
			rep_list_array:["operating_receipt","terminal_list","business_list"],
			rep_list_manager:{"operating_receipt":null,"terminal_list":null,"business_list":null},
			rep_detail_array:["operating_receipt_detail"],
			rep_detail_manager:{"operating_receipt_detail":null},
			cur_report_name:"",//当前报表类型
			report_manager :{},
			areaIdSelected:"",
			detailTotalRender:null,
			detailSelectedRow:{gridObj:null,selectedRowData:null,selectedRowId:null,selectedRowObj:null}
		};
		var month = '';
		$(document).ready(function (){
			//$("#date_end").val(dateTime.nowDate);

			if($("#opType").val()=="GridConfirm"){
				document.getElementById("showTitle").innerText = "终端店奖(经理确认)";
				document.getElementById("showSubTitle").innerText = "终端店奖(经理确认)";
			}else if($("#opType").val()=="AgencyCharge"){
				document.getElementById("showTitle").innerText = "终端店奖(国代商确认)";
				document.getElementById("showSubTitle").innerText = "终端店奖(国代商确认)";
			}else if($("#opType").val()=="AgencyPay"){
				document.getElementById("showTitle").innerText = "终端店奖(国代商付款确认)";
				document.getElementById("showSubTitle").innerText = "终端店奖(国代商付款确认)";
			}else if($("#opType").val()=="GridPay"){
				document.getElementById("showTitle").innerText = "终端店奖(经理付款确认)";
				document.getElementById("showSubTitle").innerText = "终端店奖(经理付款确认)";
			}
			audit.initRepListArray();
			//初始化需查询的范围
			audit.initQueryRange();
			audit.initReport();
			audit.initClick();
		});
		var registClient = {
			onComplete: function(args) {
				alert(4)
			},
			onError: function(error) {
				console.info(error);
			},
			onException: function(status, errorInfo, hint) {
				console.info(errorInfo);
			},
			onFinally: function() {
				$u.dialog_util.hideDialog("", "loading");
			}
		};
		var addrChangeSubmitParam = {};
		audit.addr_change_submit=function(){

			var dataParam = {};
			dataParam.id = "11111";
			dataParam.agency_no = addrChangeSubmitParam.agency_no;
			dataParam.dept_no = addrChangeSubmitParam.dept_no;
			dataParam.device_type = addrChangeSubmitParam.device_type;
			dataParam.statistics_month = addrChangeSubmitParam.statistics_month;

			if($("#opType").val()=="GridConfirm"){
				if(!confirm("确认修改为 经理确认 状态？")){
					return false;
				}
				dataParam.grid_chief_confirm_flag="1";
			}else if($("#opType").val()=="AgencyCharge"){
				if(!confirm("确认修改为 国代商确认 状态？")){
					return false;
				}
				dataParam.agency_confirm_flag="1";
				dataParam.agency_adjust_charge=addrChangeSubmitParam.agency_adjust_charge;
			}else if($("#opType").val()=="AgencyPay"){
				if(!confirm("确认修改为 国代商已付款 状态？")){
					return false;
				}
				dataParam.agency_pay_flag="1";
			}else if($("#opType").val()=="GridPay"){
				if(!confirm("确认修改为 经理已付款 状态？")){
					return false;
				}
				dataParam.grid_chief_pay_flag="1";
			}
			/* var dataParam={"flag":flag,"jsessionid":"","id":id}; */
			$.ajax({
				loading: '正在加载中...',
				type: "POST",
				dataType: "json",
				url: application.fullPath + "authority/terminalReward/update_terminal_reward",
				data: dataParam,
				success: function (msg,status) {
					var respCode = msg.type;
					var content=msg.content;
					if(respCode == 'success'){
						alert(content);
						audit.dialog.hideDialog("select_addr_dialog");
						audit.setParams();
						audit.rep_list_manager[audit.cur_report_name].loadData(true);
					}else{
						if(content.length>0){
							$.alert(content);
							audit.dialog.hideDialog("select_addr_dialog");
							$.ligerDialog.closeWaitting();

						}else{
							audit.dialog.hideDialog("select_addr_dialog");
							$.ligerDialog.closeWaitting();
							$.alert("更改状态失败");
						}
					}
					// $.showSuccess("保存成功!");
				},

				error: function (msg,status) {
					var respCode = msg.type;
					var content=msg.content;
					if(content.length>0){
						audit.dialog.hideDialog("select_addr_dialog");
						$.ligerDialog.closeWaitting();
						$.alert(content);
					}else{
						audit.dialog.hideDialog("select_addr_dialog");
						$.ligerDialog.closeWaitting();
					}
				}
			});
		};
		audit.charge_fill_in_submit = function() {
			var oper_no = $("#detail_add_oper_no").val();
			var oper_name = $("#detail_add_oper_name").val();
			var imei = $("#detail_add_imei").val();
			var sale_date=audit.formatDate($("#detail_add_sale_date").val());
			if (oper_no == null || oper_no == "") {
				alert("店员工号不能为空!");
				return;
			}
			if (oper_name == null || oper_name == "") {
				alert("店员名称不能为空!");
				return;
			}
			if (imei == null || imei == "") {
				alert("串号不能为空!");
				return;
			}
			var totalRender = audit.detailTotalRender;
			if (totalRender == null) {
				alert("原始数据不足!");
				return;
			}
			var rowdata = totalRender.Rows[0];
			var dataParam = {};
			// 明细表
			dataParam.shop_reward_province_code = rowdata.province_code;
			dataParam.shop_reward_local_net = rowdata.local_net;
			dataParam.shop_reward_area_id = rowdata.area_id;
			dataParam.shop_reward_dept_no = rowdata.dept_no;
			dataParam.shop_reward_dept_name = rowdata.dept_name;
			dataParam.shop_reward_oper_no = oper_no;
			dataParam.shop_reward_oper_name = oper_name;
			dataParam.shop_reward_agency_no = rowdata.agency_no;
			dataParam.shop_reward_agency_name = rowdata.agency_name;
			dataParam.shop_reward_device_brand = rowdata.device_brand;
			dataParam.shop_reward_device_type = rowdata.device_type;
			dataParam.shop_reward_device_name = rowdata.device_name;
			dataParam.shop_reward_imei = imei;
			dataParam.shop_reward_sale_date = sale_date;
			dataParam.shop_reward_shop_charge = rowdata.shop_charge;
			dataParam.shop_reward_flag = rowdata.flag;
			dataParam.shop_reward_compute_date = rowdata.compute_date;
			dataParam.shop_reward_receive_charge = rowdata.shop_charge; // 初始化：实得金额=应得金额
			// 总表
			dataParam.standing_book_id = "11111";
			dataParam.standing_book_agency_no = addrChangeSubmitParam.agency_no;
			dataParam.standing_book_dept_no = addrChangeSubmitParam.dept_no;
			dataParam.standing_book_oper_no = oper_no;
			dataParam.standing_book_device_type = addrChangeSubmitParam.device_type;
			//dataParam.standing_book_sale_date = sale_date;
			dataParam.standing_book_shop_charge = rowdata.shop_charge; // 店奖金额统计
			dataParam.standing_book_deal_status = "plus";
			$.ajax({
				loading: '正在加载中...',
				type: "POST",
				dataType: "json",
				url: application.fullPath + "authority/terminalReward/add_terminal_shop_reward",
				data: dataParam,
				success: function(msg, status) {
					var respCode = msg.type;
					var content = msg.content;
					if (respCode == 'success') {
						alert(content);
						rowdata.statistics_month = month;
						audit.setDetailSearchParams(rowdata);
						audit.rep_detail_manager["operating_receipt_detail"].loadData(true);
						audit.setParams();
						audit.rep_list_manager[audit.cur_report_name].loadData(true);
						$("#detail_add_oper_no").val('');
						$("#detail_add_oper_name").val('');
						$("#detail_add_imei").val('');
					}else{
						if (content.length > 0) {
							alert(content);
						} else {
							alert("补录操作失败");
						}
					}
				},
				error: function(msg, status) {
					var respCode = msg.type;
					var content = msg.content;
					if (content.length > 0) {
						alert(content);
					} else {
						alert("补录操作失败");
					}
				}
			});
		};
		audit.charge_clear_submit = function() {
			var g = audit.detailSelectedRow.gridObj;
			if (g == null) {
				alert("请选择店奖金额清零的记录!");
				return;
			}
			var isSelected = false;
			for (var rid in g.records) {
				if (g.isSelected(g.records[rid]))
					isSelected = true;
			}
			if (!isSelected) {
				alert("请选择店奖金额清零的记录!");
				return;
			}
			var rowdata = audit.detailSelectedRow.selectedRowData;
			var rowid = audit.detailSelectedRow.selectedRowId;
			var rowobj = audit.detailSelectedRow.selectedRowObj;
			if (rowdata.receive_charge == null || rowdata.receive_charge == "") {
				alert("该记录没有店奖金额!");
				return;
			}
			if (rowdata.receive_charge == 0) {
				alert("该记录的店奖金额已经清零!");
				return;
			}
			if (!confirm("【" + rowdata.oper_name + "(" + rowdata.oper_no + ")  串号:" + rowdata.imei + "】确认清零吗？")) {
				return;
			}
			var dataParam = {};
			// 明细表
			dataParam.shop_reward_id = rowdata.id;
			dataParam.shop_reward_receive_charge = 0; // 店奖金额清零
			// 总表
			dataParam.standing_book_id = "11111";
			dataParam.standing_book_agency_no = addrChangeSubmitParam.agency_no;
			dataParam.standing_book_dept_no = addrChangeSubmitParam.dept_no;
			dataParam.standing_book_oper_no = rowdata.oper_no;
			dataParam.standing_book_device_type = addrChangeSubmitParam.device_type;
			dataParam.standing_book_statistics_month = addrChangeSubmitParam.statistics_month;
			dataParam.standing_book_shop_charge = rowdata.receive_charge; // 需要扣除的店奖金额
			dataParam.standing_book_deal_status = "minus";
			dataParam.standing_book_begin_date = rowdata.sale_date;
			$.ajax({
				loading: '正在加载中...',
				type: "POST",
				dataType: "json",
				url: application.fullPath + "authority/terminalReward/update_terminal_shop_reward",
				data: dataParam,
				success: function(msg, status) {
					var respCode = msg.type;
					var content = msg.content;
					if (respCode == 'success') {
						alert(content);
						rowdata.statistics_month = addrChangeSubmitParam.statistics_month;
						audit.setDetailSearchParams(rowdata);
						audit.rep_detail_manager["operating_receipt_detail"].loadData(true);
						audit.setParams();
						audit.rep_list_manager[audit.cur_report_name].loadData(true);
					}else{
						if (content.length > 0) {
							alert(content);
						} else {
							alert("店奖金额清零失败");
						}
					}
				},
				error: function(msg, status) {
					var respCode = msg.type;
					var content = msg.content;
					if (content.length > 0) {
						alert(content);
					} else {
						alert("店奖金额清零失败");
					}
				}
			});
		};
		/**
		 *	初始化select
		 */
		audit.initQueryRange = function(){
			var selectList = $("select[ltype='init']");
			for(var i=0;i<selectList.length;i++){
				var select = $(selectList[i]);
				var url = select.attr("url");
				this.createSelect(select,url);
			}
			$("#select_channel").click(function(){
				audit.dialog.showDialog("select_channel_dialog");
				audit.dialog.initChannelDialog();
			});

			$("#select_people").click(function(){
				audit.dialog.showDialog("select_staff_dialog");
				audit.dialog.initStaffDialog();
			});
			$("#addr_change_submit_2").click(function(){
				audit.addr_change_submit();
			});
			$("#charge_fill_in_submit").click(function(){
				audit.charge_fill_in_submit();
			});
			$("#charge_clear_submit").click(function(){
				audit.charge_clear_submit();
			});
		};

		audit.dialog = {
			manager:{channel:null,staff:null,selectAddr:null},
			initChannelDialog:function(){
				if(this.manager.channel != null){
					return;
				}
				var  select_channel_dialog = $("#select_channel_dialog");
				this.manager.channel =  $("#select_channel_table").ligerGrid({
					columns: [
						{ display: '工号编码', name: 'oper_id', align: 'left', width: 100, minWidth: 60 },
						{ display: '工号名字', name: 'oper_name', minWidth: 120 },
						{ display: '备注', name: 'dept_no', minWidth: 140 }
					],
					buttonType:"confirm",//右下角按钮类型 目前类型  confirm(确认),excel(导出excel按钮)
					buttonEvent:function(){
						var manager = audit.dialog.manager.channel;
						var row = manager.getSelectedRow();
						$("#select_channel").val(row.oper_name);
						$("#select_channel").attr("data",JSON.stringify(row));
						audit.clearGrid(audit.dialog.manager.staff);
						audit.dialog.hideDialog("select_channel_dialog");
					},
					checkbox:true,
					single:true,
					delayLoad:true,
					selectRowButtonOnly:true,
					width:950,
					url: application.fullPath + "authority/webUtilUssM165/queryInfoOper",
					height:300,pageSize:30 ,rownumbers:false
				});
				$(".icon-close",select_channel_dialog).click(function(){
					audit.dialog.hideDialog("select_channel_dialog");
				});
				$(".js_query",select_channel_dialog).click(function(){
					var obj = $("#select_channel_dialog");
					var params = {};
					params.oper_id = $(".branch-code", obj).val();
					params.oper_name = $(".branch-name",obj).val();
					var manager = audit.dialog.manager.channel;
					audit.dialog.loadQuery(manager,params);
				});
				$(".js_reset",select_channel_dialog).click(function(){
					var obj = $("#select_channel_dialog");
					$(".branch-code", obj).val("");
					$(".branch-name",obj).val("");
				});

			},
			initStaffDialog:function(){
				if(this.manager.staff != null){
					return;
				}
				var select_staff_dialog = $("#select_staff_dialog");
				this.manager.staff = $("#select_staff_table").ligerGrid({
					columns: [
						{ display: '区域编号', name: 'area_code', align: 'left', width: 150, minWidth: 60 },
						{ display: '区域名称', name: 'area_name', minWidth: 120 }
					],
					buttonType:"confirm",//右下角按钮类型 目前类型  confirm(确认),excel(导出excel按钮)
					buttonEvent:function(){
						var manager = audit.dialog.manager.staff;
						var row = manager.getSelectedRow();
						$("#select_people").val(row.area_name);
						$("#select_people").attr("data",JSON.stringify(row));
						audit.dialog.hideDialog("select_staff_dialog");
					},
					delayLoad:true,
					checkbox:true,
					single:true,
					selectRowButtonOnly:true,
					width:950,
					url: application.fullPath + "authority/webUtil/queryRuleAreaHx",
					height:300,pageSize:30 ,rownumbers:false
				});
				//员工信息关闭
				$(".icon-close",select_staff_dialog).click(function(){
					audit.dialog.hideDialog("select_staff_dialog");
				});
				//员工信息查询
				$(".js_query",select_staff_dialog).click(function(){
					var obj = $("#select_staff_dialog");
					var params = {};
					// params.dept_no = audit.getJsonVal("select_channel","dept_no");
					params.area_code = $(".branch-code",obj).val();
					params.area_name = $(".branch-name",obj).val();
					var manager = audit.dialog.manager.staff;
					audit.dialog.loadQuery(manager,params);
				});
				//员工信息重置
				$(".js_reset",select_staff_dialog).click(function(){
					var obj = $("#select_staff_dialog");
					$(".branch-code",obj).val("");
					$(".branch-name",obj).val("");
				});
			},
			initAddrDialog:function(rowdata){
				addrChangeSubmitParam = {};
				addrChangeSubmitParam.id = rowdata.id;
				addrChangeSubmitParam.dept_no = rowdata.dept_no;
				addrChangeSubmitParam.dept_name = rowdata.dept_name;
				addrChangeSubmitParam.oper_name = rowdata.oper_name;
				addrChangeSubmitParam.agency_no = rowdata.agency_no;
				addrChangeSubmitParam.agency_name = rowdata.agency_name;
				addrChangeSubmitParam.device_name = rowdata.device_name;
				addrChangeSubmitParam.device_type = rowdata.device_type;
				addrChangeSubmitParam.statistics_month = rowdata.statistics_month;
				addrChangeSubmitParam.shop_charge = rowdata.shop_charge;
				if (rowdata.agency_adjust_charge == "0") {
					addrChangeSubmitParam.agency_adjust_charge = rowdata.shop_charge;
				} else {
					addrChangeSubmitParam.agency_adjust_charge = rowdata.agency_adjust_charge;
				}


				/*$("#area_id").val("0102");
				 $("#oper_id").val("CJF092");*/
				//var select = $("#select_area");
				//var url = select.attr("url");
				//audit.createAreaSelect(select,url);
				var select_addr_dialog = $("#select_addr_dialog");
				//关闭窗口
				$(".icon-close",select_addr_dialog).click(function(){
					audit.dialog.hideDialog("select_addr_dialog");
				});
			},

			showDialog:function(id){
				$("#divCoverIframe").show();
				$("#"+id).show();
				$("#coverIframe").contents().find("#cover").click(function(){
					audit.dialog.hideDialog(id);
				});

			},
			hideDialog:function(id){
				$("#divCoverIframe").hide();
				$("#"+id).hide();
			},
			loadQuery:function(manager,params){
				if(!manager){
					return;
				}
				if(params){
					for(var key in params){
						if(params[key] != "" && params[key] != undefined){
							//ligerU设置后台传递参数
							manager.setParm(key,params[key]);
						}else{
							manager.removeParm(key);
						}
					}
				}
				manager.loadData(true);
			}
		};

		audit.empty = {
			emptySelect:function(selectId){
				$("option[ltype != 'default']",$("#"+selectId)).remove();
			},
			emptyInput:function(inputId){
				var input = $("#"+inputId);
				input.val("");
				input.attr("data","");
			}
		};
		audit.getOperAreaId=function(operId){
			var dataParam={"oper_no":operId};
			$.ajax({
				loading: '正在加载中...',
				type: "POST",
				dataType: "json",
				url: application.fullPath + "rest/oper/getInfoOper",
				async: false,
				data: dataParam,
				success: function (msg,status) {
					var respCode = msg.type;
					var content=msg.content;
					if(respCode == 'success'){

						audit.areaIdSelected=msg.args.getInfoOper.area_id;

						return msg.args.getInfoOper.area_id;

					}else{
						if(content.length>0){
							$.alert(content);
							$.ligerDialog.closeWaitting();

						}else{
							$.ligerDialog.closeWaitting();
							$.alert("查询失败");
						}
					}
					// $.showSuccess("保存成功!");
				},

				error: function (msg,status) {
					alert(msg.getInfoOper)
					var respCode = msg.type;
					var content=msg.content;
					if(content.length>0){
						$.ligerDialog.closeWaitting();
						$.alert(content);
					}else{
						$.ligerDialog.closeWaitting();
					}
				}
			});
		};
		/**
		 *生成下拉框
		 */
		audit.createAreaSelect = function(select,url){
			select.empty();
			//var oper_id=$("#oper_id").val();
			var oper_id='<%=load_oper_no%>';
			audit.getOperAreaId(oper_id);/*赋值全局变量*/
			var area_id=audit.areaIdSelected;
			var dataParam={"oper_id":oper_id,"area_id":area_id,"page":"1","pagesize":"10000"};

			$.ajax({url:url,
				async: true,
				type: "POST",
				dataType: "json",
				data:dataParam,
				success: function (msg, textStatus, jqXHR) {
					var dataLists=msg.Rows;

					for(var j = 0;j < dataLists.length;j++){
						var data = dataLists[j];
						var code_value = data.hx_area_id;
						var code_name = data.area_name;
						var option = "<option value='"+code_value+"'>" + code_name + "</option>";
						select.append(option);
					}
				},
				error: function (XMLHttpRequest, textStatus) {

				}
			});
		};
		audit.createSelect = function(select,url){
			$.ajax({url:url,
				async: true,
				type: "POST",
				dataType: "json",
				success: function (dataLists, textStatus, jqXHR) {
					for(var j = 0;j < dataLists.length;j++){
						var data = dataLists[j];
						var code_value = data.code_value;
						var code_name = data.code_name;
						var option = "<option value='"+code_value+"'>" + code_name + "</option>";
						select.append(option);
					}
				},
				error: function (XMLHttpRequest, textStatus) {

				}
			});
		};
		audit.initRepListArray = function(){
			for(var i=0;i<this.rep_list_array.length;i++){
				var temp_id = "rep_" + this.rep_list_array[i]
				$("#rep_report_list").append('<div id="'+temp_id+'" class="list" style="height:340px;"></div>');
			}
		};
		audit.initReport = function(){
			var value = $("input[name='audit_report_type']:checked").val();
			audit.createReport(value);
			audit.createReportDetail("operating_receipt_detail");
		};
		/**
		 *注册click事件
		 */
		audit.initClick = function(){
			$("input[name='audit_report_type']").click(audit.clickReportType);
			$("#reset").click(audit.reset);
			$("#confirm").click(audit.confirm);
			$("#export").click(audit.rpt_export);
			$("#select_channel_delete").click(function(){
				audit.empty.emptyInput("select_channel");
			});
			$("#select_people_delete").click(function(){
				audit.empty.emptyInput("select_people");
			});
		};

		audit.clickReportType = function(){
			if($("input[name='audit_report_type']:checked").val()=="operating_receipt"){
				$("#select_oper_id").hide();

			}else{
				$("#select_oper_id").show();

			}
			var value = $(this).val();

			if(value != audit.cur_report_name){
				audit.createReport(value);
				audit.cur_report_name = value;
			}

		};

		audit.createReport = function(value){
			if(value != audit.cur_report_name){
				audit.cur_report_name = value;
			}else{
				return;
			}
			$("#rep_"+value).show();
			for(var i=0;i< audit.rep_list_array.length;i++){
				var temp_id = "rep_" + audit.rep_list_array[i]
				if(value != audit.rep_list_array[i]){
					$("#rep_"+audit.rep_list_array[i]).hide();
				}
			}
			var manager = audit.rep_list_manager[value];
			if(value == "operating_receipt"){//业务发展清单
				audit.operatingReceiptCss();
				if(manager == null){
					audit.operatingReceipt(value);
				}
			}else if(value == "terminal_list"){//客响工单处理情况清单
				audit.terminalListCss();
				if(manager == null){
					audit.terminalList(value);
				}
			}else if(value == "business_list"){//月度报表清单
				audit.businessListCss();
				if(manager == null){
					audit.businessList(value);
				}
			}
		};
		audit.commmonCss = function(){
			$("#select_people_tr").hide();

			audit.empty.emptyInput("select_people");
		};
		audit.operatingReceiptCss = function(){
			this.commmonCss();
		};
		audit.incomeListCss = function(){
			this.commmonCss();
		};
		audit.businessListCss = function(){
			this.commmonCss();
		};
		audit.terminalListCss = function(){
			this.commmonCss();
		};
		audit.createReportDetail = function(value){
			var manager = audit.rep_detail_manager[value];
			if(value == "operating_receipt_detail"){ // 终端店奖详情
				if(manager == null){
					audit.operatingReceiptDetail(value);
				}
			}
		};
		audit.unDisplayAllCheckbox = function() {
			$(".l-grid-hd-cell-text.l-grid-hd-cell-btn-checkbox").css("display", "none");
		}
		//点击营业收入,处理函数
		audit.operatingReceipt = function(value){
			audit.rep_list_manager[value] =
					$("#rep_"+value).ligerGrid({
						columns: [

							//{ display: '销售店员工号', name: 'oper_no', width: 120 },
							//{ display: '销售店员名称', name: 'oper_name', width: 120 },
							{ display: '代理商名称', name: 'agency_name', width: 120 },
							//{ display: '品牌', name: 'device_brand', width: 120 },
							{ display: '手机型号', name: 'device_type', width: 120 },
							{ display: '手机名称', name: 'device_name', width: 120 },
							{ display: '店奖总金额', name: 'shop_charge', width: 120 },
							{ display: '台数', name: 'num_count', width: 100 },
							{ display: '销售部门', name: 'dept_name', width: 120 },
							//{ display: '国代商调整金额', name: 'agency_adjust_charge', width: 180 },
							{ display: '经理确认', name: 'grid_chief_confirm_flag', width: 120 },
							{ display: '国代商确认', name: 'agency_confirm_flag', width: 120 },
							{ display: '国代商付款确认', name: 'agency_pay_flag', width: 120 },
							{ display: '经理付款确认', name: 'grid_chief_pay_flag', width: 120 },
							{ display: '生成日期', name: 'statistics_month', width: 120 }


						],
						delayLoad:true,
						checkbox:true,
						onSelectRow:function(rowdata, rowid, rowobj){
							if($("#opType").val()=="AgencyCharge"){
								if(rowdata.agency_confirm_flag=="已确认"){
									alert("该状态不允许修改!");
								}
								else{
									audit.dialog.showDialog("select_addr_dialog");
									audit.hiddenElement();
									audit.dialog.initAddrDialog(rowdata);
									audit.showDetailList(rowdata);
								}
							}
							if($("#opType").val()=="GridConfirm"){
								if(rowdata.grid_chief_confirm_flag=="已确认"){
									alert("该状态不允许修改!");
								}
								else{
									month = rowdata.statistics_month;
									audit.dialog.showDialog("select_addr_dialog");
									audit.showElement();
									audit.dialog.initAddrDialog(rowdata);
									audit.showDetailList(rowdata);
								}
							}
							if($("#opType").val()=="AgencyPay"){
								if(rowdata.agency_pay_flag=="已确认"){
									alert("该状态不允许修改!");
								}
								else{
									audit.dialog.showDialog("select_addr_dialog");
									audit.hiddenElement();
									audit.dialog.initAddrDialog(rowdata);
									audit.showDetailList(rowdata);
								}
							}
							if($("#opType").val()=="GridPay"){
								if(rowdata.grid_chief_pay_flag=="已确认"){
									alert("该状态不允许修改!");
								}
								else{
									audit.dialog.showDialog("select_addr_dialog");
									audit.hiddenElement();
									audit.dialog.initAddrDialog(rowdata);
									audit.showDetailList(rowdata);
								}
							}

						},

						//onSelectRow:function(rowdata, rowid, rowobj){
						//audit.dialog.showDialog("select_addr_dialog");
						//audit.dialog.initAddrDialog(rowdata);

						//},
						totalRender: function(data, currentPageData) {
							return "共 " + data.Total + " 条数据";
						},
						delayLoad:true,
						url: application.fullPath + "authority/terminalReward/qry_terminal_reward",
						height:280,pageSize:30 ,rownumbers:false
					});
			audit.unDisplayAllCheckbox();
		};
		//点击终端清单,处理函数
		audit.terminalList = function(value){
			audit.rep_list_manager[value]  =
					$("#rep_"+value).ligerGrid({
						columns: [
							{ display: '部门编码', name: 'dept_no', width: 100 },
							{ display: '销售部门', name: 'dept_name', width: 100 },
							{ display: '销售店员工号', name: 'oper_no', width: 120 },
							{ display: '销售店员名称', name: 'oper_name', width: 120 },
							{ display: '代理商名称', name: 'agency_name', width: 120 },
							{ display: '品牌', name: 'device_brand', width: 120 },
							{ display: '型号', name: 'device_name', width: 240 },
							{ display: '串号', name: 'imei', width: 180 },
							{ display: '店奖应发金额', name: 'shop_charge', width: 120 },
							{ display: '店奖实得金额', name: 'receive_charge', width: 120 },
							// { display: '说明', name: 'receive_charge_comment', width: 180 }, // 2016.4后优化时使用
							{ display: '来源', name: 'flag', width: 100 },
							{ display: '销售日期', name: 'sale_date', width: 100 }
						],
						totalRender: function(data, currentPageData) {
							return "共 " + data.Total + " 条数据";
						},
						delayLoad:true,
						url: application.fullPath + "authority/terminalReward/qry_terminal_shop_reward",
						height:280,pageSize:30 ,rownumbers:false
					});
		};
		//点击终端清单,处理函数
		audit.businessList = function(value){
			audit.rep_list_manager[value]  =
					$("#rep_"+value).ligerGrid({
						columns: [
							{ display: '受理工单汇总', name: 'ang_dog4', width: 160 }

						],
						totalRender: function(data, currentPageData) {
							return "共 " + data.Total + " 条数据";
						},
						delayLoad:true,
						url: application.fullPath + "authority/ReportCs/queryInfoCustCareOrderMonthly",
						height:280,pageSize:30 ,rownumbers:false
					});
		};
		// 终端店奖详情（店奖详情修改），处理函数
		audit.operatingReceiptDetail = function(value) {
			audit.rep_detail_manager[value] =
					$("#rep_"+value).ligerGrid({
						columns: [
							{ display: '部门编码', name: 'dept_no', width: 100 },
							{ display: '销售部门', name: 'dept_name', width: 100 },
							{ display: '销售店员工号', name: 'oper_no', width: 120 },
							{ display: '销售店员名称', name: 'oper_name', width: 120 },
							{ display: '代理商名称', name: 'agency_name', width: 120 },
							{ display: '品牌', name: 'device_brand', width: 120 },
							{ display: '型号', name: 'device_name', width: 240 },
							{ display: '串号', name: 'imei', width: 180 },
							{ display: '店奖应发金额', name: 'shop_charge', width: 120 },
							{ display: '店奖实得金额', name: 'receive_charge', width: 120 },
							// { display: '说明', name: 'receive_charge_comment', width: 180 }, // 2016.4后优化时使用
							{ display: '来源', name: 'flag', width: 100 },
							{ display: '销售日期', name: 'sale_date', width: 100 }
						],
						checkbox: true,
						onSelectRow: function(rowdata, rowid, rowobj) {
							var g = this;
							// 设置成单选框模式
							for (var rid in g.records) {
								if (rid != rowid)
									g.unselect(g.records[rid]);
							}
							audit.detailSelectedRow.gridObj = g;
							audit.detailSelectedRow.selectedRowData = rowdata;
							audit.detailSelectedRow.selectedRowId = rowid;
							audit.detailSelectedRow.selectedRowObj = rowobj;
						},
						totalRender: function(data, currentPageData) {
							audit.detailTotalRender = data;
							return "共 " + data.Total + " 条数据";
						},
						delayLoad: true,
						url: application.fullPath + "authority/terminalReward/qry_terminal_shop_reward",
						height:250,pageSize:30,width:"100%",rownumbers:false
					});
			audit.unDisplayAllCheckbox();
		};



		/**
		 *confirm 页面提交事件
		 */
		audit.confirm = function(){


			audit.setParams();
			audit.rep_list_manager[audit.cur_report_name].loadData(true);
		};
		/**
		 *获取页面中所有参数同时去除掉参数中的空字符串和undefined
		 */
		audit.setParams = function(){
			var params = {};
			params.audit_report_type = $("input[name='audit_report_type']:checked").val();

			params.area_id  = audit.getJsonVal("select_people","area_id");
			params.begin_date = audit.formatDate($("#date_begin").val());
			params.end_date= audit.formatDate($("#date_end").val());
			//var date1=$("#date_end").val();
			if($("input[name='audit_report_type']:checked").val()=="operating_receipt"){

				if($("#opType").val()=="GridConfirm"){
					params.agency_confirm_flag="0";
					params.agency_pay_flag="0";
					params.grid_chief_pay_flag="0";

				}else if($("#opType").val()=="AgencyCharge"){
					params.grid_chief_confirm_flag="1";
					params.agency_pay_flag="0";
					params.grid_chief_pay_flag="0";

				}else if($("#opType").val()=="AgencyPay"){
					params.grid_chief_confirm_flag="1";
					params.agency_confirm_flag="1";
					params.grid_chief_pay_flag="0";

				}else if($("#opType").val()=="GridPay"){
					params.grid_chief_confirm_flag="1";
					params.agency_confirm_flag="1";
					params.agency_pay_flag="1";

				}
			}else{
				params.oper_no  = audit.getJsonVal("select_channel","oper_id");
			}
			if($("#opType").val()=="GridConfirm"||$("#opType").val()=="GridPay"){
				params.oper_dept_no="<%=load_oper_no%>";
			}
			params.dept_no = "";
			params.device_type=$("#device_name_input").val();
			params.agency_no=$("#select_agency_no").val();
			// params.device_number = $("#select_number").val();
			for(var key in params){
				if(params[key] != "" && params[key] != undefined){
					/**
					 *ligerU设置后台传递参数
					 */
					audit.rep_list_manager[audit.cur_report_name].setParm(key,params[key]);
				}else{
					audit.rep_list_manager[audit.cur_report_name].removeParm(key);
				}
			}
		};
		audit.showDetailList = function(data) {
			audit.detailTotalRender = null;
			audit.setDetailSearchParams(data);
			audit.rep_detail_manager["operating_receipt_detail"].loadData(true);
		};
		audit.setDetailSearchParams = function(data) {
			var params = {};
			params.area_id = audit.getJsonVal("select_people", "area_id");

			params.oper_no = audit.getJsonVal("select_channel", "oper_id");
			if($("#opType").val() == "GridConfirm" || $("#opType").val() == "GridPay") {
				params.oper_dept_no = "<%=load_oper_no%>";
			}
			params.dept_no = data.dept_no;
			params.device_type = data.device_type;
			params.agency_no = $("#select_agency_no").val();
			params.sale_date = data.statistics_month;
			for(var key in params){
				if(params[key] != "" && params[key] != undefined){
					// ligerU设置后台传递参数
					audit.rep_list_manager[audit.cur_report_name].setParm(key,params[key]);
				}else{
					audit.rep_list_manager[audit.cur_report_name].removeParm(key);
				}
			}
		};

		audit.formatDate = function(date){
			if(date&&date.match(/^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/)){
				return date.split("-").join("");
			}else{
				return "";
			}
		};

		audit.getJsonVal = function(id,key){
			var data = $("#"+id).attr("data");
			if(data){
				var jsonData = JSON.parse(data);
				return jsonData[key]||"";
			}else{
				return "";
			}
		};

		audit.clearGrid = function(manager){
			if(manager){
				manager.clearGrid();
			}
		};
		
		//隐藏补录和清零的部分输入框
		audit.hiddenElement = function(){
			$("#bulu_area").css('display','none');
			$("#charge_clear_submit").css('display','none');
		};
		
		//显示补录和清零的部分输入框
		audit.showElement = function(){
			$("#bulu_area").css('display','block');
			$("#charge_clear_submit").css('display','block');
		};
		
		/**
		 *重置所有函数
		 */
		audit.reset = function(){

			$("input[name='audit_report_type']:checked").attr("checked",false);
			$("input[name='audit_report_type']:first").get(0).checked = true;
			$("input[name='tele_type']:checked").attr("checked",false);
			$("input[name='tele_type']:first").get(0).checked = true;

			//select默认选中第一个
			audit.empty.emptyInput("select_channel");
			audit.empty.emptyInput("select_people");

			$("#date_begin").val(dateTime.beforeDate);
			$("#date_end").val(dateTime.nowDate);

			$("#select_pay_type option:first").attr('selected','selected');
		}

		/**
		 *导出函数
		 */
		audit.rpt_export = function () {
			var audit_report_type = $("input[name='audit_report_type']:checked").val();
			if (audit_report_type == "operating_receipt"){
				var colNames = "shop_charge,device_type,device_name,agency_no,agency_name,statistics_month,dept_no,dept_name";
				post(application.fullPath + "authority/terminalReward/qry_terminal_reward_excel",colNames);
			}
			if (audit_report_type == "terminal_list"){
				var colNames = "dept_name,oper_no,oper_name,agency_name,device_brand,device_name,shop_charge,flag,sale_date";
				post(application.fullPath + "authority/terminalReward/qry_terminal_shop_reward_excel",colNames);
			}

		};

		function post(url,cols){
			var audit_report_type = $("input[name='audit_report_type']:checked").val();
			var params = {};
			//params.tele_type = $("input[name='tele_type']:checked").val();
			//params.local_net = $("#select_city").val();
			//params.dept_no = $("#select_urbon").val();
			//params.oper_dept_no = audit.getJsonVal("select_channel", "dept_no");
			//params.oper_no = audit.getJsonVal("select_people", "oper_no");
			//params.begin_date = audit.formatDate($("#date_begin").val());
			//params.begin_date = audit.formatDate($("#date_end").val());
			params.device_type=$("#device_name_input").val();
			//params.oper_no  = audit.getJsonVal("select_channel","oper_id");
			params.area_id  = audit.getJsonVal("select_people","area_id");
			var agencyNo=$("#select_agency_no").val();
			if($("input[name='audit_report_type']:checked").val()=="operating_receipt"){
				if($("#opType").val()=="GridConfirm"){
					params.grid_chief_confirm_flag="0";
					params.agency_pay_flag="0";
					params.grid_chief_pay_flag="0";
				}else if($("#opType").val()=="AgencyCharge"){
					params.agency_confirm_flag="1";
					params.agency_pay_flag="0";
					params.grid_chief_pay_flag="0";

				}else if($("#opType").val()=="AgencyPay"){
					params.agency_confirm_flag="1";
					params.grid_chief_confirm_flag="1";
					params.grid_chief_pay_flag="0";

				}else if($("#opType").val()=="GridPay"){
					params.agency_confirm_flag="1";
					params.grid_chief_confirm_flag="1";
					params.agency_pay_flag="1";

				}
			}else{
				params.oper_no  = audit.getJsonVal("select_channel","oper_id");
			}
			if($("#opType").val()=="GridConfirm"||$("#opType").val()=="GridPay"){
				params.oper_dept_no="<%=load_oper_no%>";
			}
			params.agency_no=agencyNo;

			if (audit_report_type == "operating_receipt"){
				params.audit_flag="operating_receipt";
			}
			else if (audit_report_type == "terminal_list"){
				params.audit_flag="terminal_list";

			}
			params.name_list = cols;

			var temp_form = document.createElement("form");
			temp_form.action = url;
			temp_form.target = "_blank";
			temp_form.method = "post";
			temp_form.style.display = "none";
			for (var x in params) {
				if (params[x] != "" && params[x] != undefined) {
					var opt = document.createElement("input");
					opt.type = "hidden";
					opt.name = x;
					opt.value = params[x];
					temp_form.appendChild(opt);
				}
			}
			document.body.appendChild(temp_form);
			temp_form.submit();
		};
	</script>
</head>
<body>
<div class="show">
	<div class="show_title_bg">
		<div class="show_title"><span id="showTitle">终端店奖</span><!--<span class="red">选择完一项后，才能选择下面一项的内容，否则下面全部置灰无法选择；仅可以做单选。</span>-->
		</div>
	</div>
	<div class="box box_margin_right" id="audit_report_type_box" >
		<div class="show_big_title"><strong>.</strong>请选择清单的类别：</div>
		<div class="table_box">
			<table width="446" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="24" height="22" align="center"><input type="radio" name="audit_report_type" value="operating_receipt" id="checkbox" checked="checked"/></td>
					<td width="124"><span id="showSubTitle">终端店奖</span></td>
					<td width="24" height="22" align="center"><input type="radio" name="audit_report_type" value="terminal_list" id="checkbox1"/></td>
					<td width="124">终端销售日志</td>
				</tr>
			</table>
		</div>

	</div>


	<div class="box" id="select_rang_box">
		<div class="show_big_title"><strong>.</strong>请选择需查询的范围：</div>
		<div class="table_box_none">
			<table width="486" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr id="select_oper_id"  style="display: none">
					<td width="90"  height="22" align="left"><strong>操作员：</strong></td>
					<td width="300" align="center"><div id="select_channel_delete" class="input_click_delete"></div>
						<a href="javascript:void(0);"><input type="text" name="select3" id="select_channel"  class="input_click"  readonly="readonly"/>
						</a></td>
				</tr>
				<tr id="select_people_tr">
					<td width="50"  height="22" align="right"><strong>区域：</strong></td>
					<td width="372" align="center"><div id="select_people_delete"  class="input_click_delete"></div>
						<a href="javascript:void(0);"><input type="text" name="select3" id="select_people"  class="input_click"  readonly="readonly"/>
						</a></td>
				</tr>
				<tr id="select_order_tr">
					<td width="90"  height="22" align="left">国代商：</td >
					<td width="300"  height="22" align="left"><select id='select_agency_no'  class="input_click" name='select_agency_no'><option value ="" selected>全部</option> <option value ="A1" >重庆国代一</option> <option value ="A2">重庆国代二</option> <option value ="A3">重庆国代三</option> <option value ="4">处理失败</option> </select></td>
				</tr>
				<tr id="select_type_tr">
					<td width="90"  height="22" align="left">手机型号：</td>
					<td width="300"  height="22" align="left"><input type="text" name="select3" id="device_name_input"  class="input_click" />
						<input type="hidden"  id="opType"  value="${opType}"/><!--开户模板ID -->
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="box box_margin_right" id="report_date_box">
		<div class="show_big_title"><strong>.</strong>请选择录入的时间：</div>
		<div class="table_box">
			<table width="446" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="210"> <t:date id="date_begin" name="date_begin"  maxDateElId="date_end"  value="<%=beforeDate%>"  ></t:date></td>
					<td width="24" align="center">到</td>
					<td width="746"> <t:date id="date_end" name="date_end"   minDateElId="date_begin"  value="<%=nowDate%>"  maxDate="2050-01-01"  ></t:date></td>
				</tr>

			</table>
		</div>

	</div>





	<div class="dashed">
		<a id="reset" href="javascript:void(0);">重 置</a> <a id="confirm" href="javascript:void(0);">查 询</a>  <a id="export" href="javascript:void(0);">导 出</a>
	</div>

	<div class="box box_long" style="padding:0 0 10px 0;height:360px;">
		<div id="rep_report_list" class="show_big_title"><strong>.</strong>报表结果展示：</div>


	</div>
	<div class="clear"></div>
</div>

<div id="divCoverIframe" class="cover" style="display:none;"><iframe id="coverIframe" name="left"  frameborder="0" height="100%" scrolling="no" width="100%" style="Z-INDEX: 1;WIDTH:100%; HEIGHT:100%;OVERFLOW: visible" src="<%=fullPath%>authority/reportForms/coverIframe"></iframe></div>
<!-- 选择营业厅开始 -->
<div id="select_channel_dialog" class="show-select" style="display:none;">
	<div class="icon-close"></div>
	<div class="select-title">查询条件：</div>
	<div class="search-input">
		<div class="leble">操作员编号：</div>
		<div class="input"><input class="branch-code" type="text" value=""></div>
		<div class="leble">操作员名称：</div>
		<div class="input"><input class="branch-name" type="text" value=""></div>
		<a class="js_query" href="javascript:void(0);">查 询</a>
		<a class="js_reset" href="javascript:void(0);">重 置</a>	</div>
	<div id="select_channel_table" class="select-table"></div>
</div>
<!-- 选择员工开始 -->
<div id="select_staff_dialog" class="show-select" style="display:none;">
	<div class="icon-close"></div>
	<div class="select-title">查询条件：</div>
	<div class="search-input">
		<div class="leble">区域编号：</div>
		<div class="input"><input class="branch-code" type="text" value=""></div>
		<div class="leble">区域名称：</div>
		<div class="input"><input class="branch-name" type="text" value=""></div>
		<a class="js_query" href="javascript:void(0);">查 询</a>
		<a class="js_reset" href="javascript:void(0);">重 置</a>	</div>
	<div id="select_staff_table" class="select-table"></div>
</div>

<div id="select_addr_dialog" class="show-select" style="display:none;">
	<div class="icon-close"></div>
	<div class="select-title">店奖状态修改</div>
	<!-- 	<div class="select-head-kh">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;温馨提示：如果有资源就点击有资源按钮提交，如果没有资源请在备注栏上写上是否选择有线通资源再进行提交。</div>
     --><br>
	<div class="search-input">
		<div id="bulu_area">
			<a id="charge_fill_in_submit" href="javascript:void(0);" class="js_query detail-add">补录</a>
			<label>店员工号：</label>
			<span><input type="text" id="detail_add_oper_no" style="width:100px;" /></span>
			<label>店员名称：</label>
			<span><input type="text" id="detail_add_oper_name" style="width:100px;" /></span>
			<label>串号：</label>
			<span><input type="text" id="detail_add_imei" style="width:180px;" /></span>
			<label>销售日期：</label>
			<span><t:date id="detail_add_sale_date" name="detail_add_sale_date" value="<%=nowDate%>" style="width:100px;"  maxDate="2050-01-01"  ></t:date></span>
		</div>
		<div>
			<a id="charge_clear_submit" href="javascript:void(0);" class="js_query detail-delete">清零</a>
		</div>
		<div id="rep_operating_receipt_detail" class="list"></div>
		<a id="addr_change_submit_2" href="javascript:void(0);" class="js_query">确认</a>
	</div>
</div>

</div>
</body>
</html>