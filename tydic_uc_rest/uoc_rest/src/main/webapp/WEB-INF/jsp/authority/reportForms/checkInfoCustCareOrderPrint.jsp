<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
<%@ page import="java.util.Date" %>
<%
	String nowDate = DateUtil.getCurrentDate(); 
	String beforeDate = DateUtil.getAfterMonth(new Date(), -1);
	String load_oper_no=(String)request.getAttribute("load_oper_no");
	String load_area_id=(String)request.getAttribute("load_area_id");
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>统一销售服务系统,稽核报表</title>
<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/checkInfoCustCareOrder.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>js/jquery-ligerui-2.25/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery-ligerui-2.25/js/plugins/ligerDialog.js" type="text/javascript"></script>
<script src="<%=basePath%>js/jquery-ligerui-2.25/js/core/base.js" type="text/javascript"></script> 
<script src="<%=basePath%>js/jquery-ligerui-2.25/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="<%=fullPath%>js/json2.js" type="text/javascript"></script> 
<script type="text/javascript">

	var dateTime = {
			beforeDate:"<%=beforeDate%>",
			nowDate:"<%=nowDate%>"	
	};
</script>
<script type="text/javascript">
		var audit = {
				rep_list_array:["operating_receipt","terminal_list","business_list"],
				rep_list_manager:{"operating_receipt":null,"terminal_list":null,"business_list":null},
				cur_report_name:"",//当前报表类型
				report_manager :{},
				areaIdSelected:""
		};
		$(document).ready(function (){

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
		audit.addr_change_submit=function(opType){
			alert();
			 
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
			$("#addr_memo").click(function(){
				audit.dialog.showDialog("select_addr_search_dialog");
				audit.dialog.initSelectAddrDialog();
			});		
			$("#addr_change_submit").click(function(){
				audit.addr_change_submit();
			});		
			$("#addr_change_cancel_submit").click(function(){
				audit.addr_change_submit("N");
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
				$("#cust_care_id").val(rowdata.cust_care_id);
				$("#cust_name").val(rowdata.cust_name);
				$("#id_number").val(rowdata.id_number);
				$("#self_date").val(rowdata.self_date);
				$("#cust_addr").val(rowdata.cust_addr);
				$("#ofr_code").val(rowdata.ofr_code);
				$("#ofr_name").val(rowdata.ofr_name);
				$("#cust_name").val(rowdata.cust_name);
				
				$("#link_name").val(rowdata.link_name);
				$("#link_phone").val(rowdata.link_phone);
				$("#oper_id").val(rowdata.oper_id);
				$("#oper_name").val(rowdata.oper_name);
				$("#oper_phone").val(rowdata.oper_phone);
				$("#oper_date").val(rowdata.oper_date);
				$("#addr_desc").val(rowdata.addr_desc);
				$("#addr_memo").val(rowdata.addr_memo);
				$("#area_id").val(rowdata.area_id);
				
				$("#kxmemo").val(rowdata.kxmemo);
				$("#memo").val(rowdata.memo);
				
				
				/*$("#area_id").val("0102");
				$("#oper_id").val("CJF092");*/
				var select = $("#select_area");
				var url = select.attr("url");
				audit.createAreaSelect(select,url);
				
			},
			initSelectAddrDialog:function(){	
				if(this.manager.selectAddr != null){
					return;
				}
				var select_addr_search_dialog = $("#select_addr_search_dialog");
				this.manager.selectAddr = $("#select_addr_search_table").ligerGrid({
	                columns: [
	  		                { display: '标准地址编号', name: 'addr_code', align: 'left', width: 150, minWidth: 60 },
	  		                { display: '标准地址', name: 'addr_name', minWidth: 120 }
	  		                ], 
	  		                buttonType:"confirm",//右下角按钮类型 目前类型  confirm(确认),excel(导出excel按钮) 
	  		                buttonEvent:function(){
	  		                	var manager = audit.dialog.manager.selectAddr;
	  		                	var row = manager.getSelectedRow();
	  		                	/*不知道这里为什么不生效,换到onSelectRow函数或onCheckRow函数*/
	  		              		/*$("#addr_id").val(row.addr_code);
	  		              		$("#addr_memo").val(row.addr_memo);
  		                		$("#addr_id").attr("data",JSON.stringify(row));*/
	  		                	audit.dialog.hideDialog("select_addr_search_dialog");
	  		                },	
	  		                delayLoad:true,
	  		                checkbox:true,
	  		                single:true,
	  		                selectRowButtonOnly:true,
	  		                width:950,
	  		              	url: application.fullPath + "csrest/haoxian/selectAddrOnLine",
	  		              	onSelectRow:function(rowdata, rowid, rowobj){
	  		              		$("#addr_memo").val(rowdata.addr_name);
	  		              		$("#addr_id").val(rowdata.addr_code);
	  		              	},
	  		                height:300,pageSize:30 ,rownumbers:false
	  		   });
				//信息关闭
			   $(".icon-close",select_addr_search_dialog).click(function(){
					   audit.dialog.hideDialog("select_addr_search_dialog");
			   });
			   //信息查询
			   $(".js_query",select_addr_search_dialog).click(function(){
				   var obj = $("#select_addr_search_dialog");
				   var params = {"area_id":"","hx_area_id":"","oper_id":"","addr_txt":""};
				   var oper_id='<%=load_oper_no%>';
					// params.dept_no = audit.getJsonVal("select_channel","dept_no");
					//var hxAreaId=$("#select_area").val();
					var hxAreaId=$("#select_area").val()+"";
					var area_id='<%=load_area_id%>';
					//alert(area_id);
					params.hx_area_id = hxAreaId;
					
				   params.area_id = area_id;
				   params.oper_id = oper_id;
				   params.addr_txt = $(".addr_txt",obj).val();
				   var manager = audit.dialog.manager.selectAddr;
				   audit.dialog.loadQuery(manager,params);			   
			   });
			   //信息重置
			   $(".js_reset",select_addr_search_dialog).click(function(){
				   var obj = $("#select_addr_search_dialog");
				   $(".addr_txt",obj).val("");
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
		}
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
		//点击营业收入,处理函数
		audit.operatingReceipt = function(value){	
			 audit.rep_list_manager[value] = 
		            $("#rep_"+value).ligerGrid({
		                columns: [
		                            { display: '客响工单ID', name: 'cust_care_id', width: 100 },
		                            { display: '标准地址编码', name: 'addr_id', width: 120 },
		                            { display: '标准地址', name: 'addr_memo', width: 200 },
			  		                { display: '装机地址', name: 'addr_desc', width: 200 },
			  		               // { display: '号码', name: 'device_number', width: 200 },
			  		               // { display: '是否有资源', name: 'is_addr', width: 200 },
			  		               // { display: '资源类型', name: 'res_type', width: 200 },
			  		                { display: '工单状态', name: 'order_state', width: 200 },
			  		                { display: '联系人姓名', name: 'link_name', width: 200 },
			  		                { display: '联系人电话', name: 'link_phone', width: 200 },
			  		                { display: '操作员', name: 'oper_id', width: 200 },
			  		                { display: '操作员名称', name: 'oper_name', width: 200 },
			  		                { display: '操作员电话', name: 'oper_phone', width: 200 },
			  		                { display: '操作时间', name: 'oper_date', width: 200 },
			  		                //{ display: '确认时间', name: 'finish_date', width: 200 },
			  		                //{ display: '完成时间', name: 'is_over', width: 200 },
			  		                //{ display: '操作员部门', name: 'dept_name', width: 200 },
			  		                //{ display: '客响人员号码', name: 'cust_care_num', width: 100 },
			  		                //{ display: '客响人员名称', name: 'cust_care_name', width: 200 },
			  		                { display: '产品编号', name: 'ofr_code', width: 200 },
			  		                { display: '产品名称', name: 'ofr_name', width: 200 },
			  		                { display: '证件姓名', name: 'cust_name', width: 200 },
			  		                { display: '证件号码', name: 'id_number', width: 200 },
			  		                { display: '证件失效时间', name: 'self_date', width: 200 },
			  		                { display: '证件地址', name: 'cust_addr', width: 200 },
			  		              	{ display: '备注', name: 'memo', width: 200 },
			  		              	{ display: '客响员工号', name: 'kx_oper_id', width: 200 },
			  		              	{ display: '客响员备注', name: 'kxmemo', width: 200 }
			  		             
 
		  		                ],
		  		              	checkbox:true,
		  		              	onSelectRow:function(rowdata, rowid, rowobj){
		  		              		if(rowdata.order_state!="待核查"){
		  		              			alert("该状态不允许修改!");	
		  		              		}
		  		              		else{
			  		              		//audit.dialog.showDialog("select_addr_dialog");
			  		              		//audit.dialog.initAddrDialog(rowdata);
		  		              		window.open ('/uss_web/indexPrint.jsp?cust_care_id='+rowdata.cust_care_id, 'newwindow', 'height=600, width=1000, top=0, left=0, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no');
		  		              		}
		  		              	},
		  		                delayLoad:true,
		  		                url: application.fullPath + "authority/ReportCs/queryInfoCustCareOrder",
		  		                height:300,pageSize:30 ,rownumbers:false
		            });
		};
		
		//点击终端清单,处理函数
		audit.terminalList = function(value){
			audit.rep_list_manager[value]  = 
		            $("#rep_"+value).ligerGrid({
		                columns: [	                
		  		             	    { display: '客响工单ID', name: 'cust_care_id', width: 100 },
		                            { display: '标准地址编码', name: 'addr_id', width: 120 },
			  		                //{ display: '地址描述', name: 'addr_desc', width: 200 },
			  		                //{ display: '地址说明', name: 'addr_memo', width: 200 },
			  		                //{ display: '号码', name: 'device_number', width: 200 },
			  		                { display: '是否有资源', name: 'is_addr', width: 200 },
			  		                { display: '资源类型', name: 'res_type', width: 200 },
			  		                { display: '工单状态', name: 'order_state', width: 200 },
			  		                { display: '操作员', name: 'oper_id', width: 200 },
			  		                { display: '操作员名称', name: 'oper_name', width: 200 },
			  		                { display: '操作员电话', name: 'oper_phone', width: 200 },
			  		                { display: '操作时间', name: 'oper_date', width: 200 },
			  		                { display: '确认时间', name: 'finish_date', width: 200 },
			  		                { display: '完成时间', name: 'is_over', width: 200 },
			  		                { display: '操作员部门', name: 'dept_name', width: 200 },
			  		                //{ display: '客户名称', name: 'cust_name', width: 200 },
			  		                //{ display: '客户联系电话', name: 'link_phone', width: 200 },
			  		                { display: '开户订单ID', name: 'order_id', width: 200 },
			  		              	{ display: '备注', name: 'kxmemo', width: 200 }
		  		                
		  		                ], 
		  		                delayLoad:true,
		  		                url: application.fullPath + "authority/ReportCs/queryInfoCustCareOrder",
		  		                height:300,pageSize:30 ,rownumbers:false
		            });
		};
		//点击终端清单,处理函数
		audit.businessList = function(value){
			audit.rep_list_manager[value]  = 
		            $("#rep_"+value).ligerGrid({
		                columns: [	                
		  		             	    { display: '客响工单ID', name: 'cust_care_id', width: 100 },
		                            { display: '操作员', name: 'oper_id', width: 120 },
			  		                { display: '操作员名字', name: 'oper_name', width: 200 },
			  		                { display: '部门编号', name: 'dept_no', width: 200 },
			  		                { display: '部门名称', name: 'dept_name', width: 200 },
			  		                { display: '佣金', name: 'comm_fee', width: 200 },
			  		                { display: '在途汇总', name: 'ang_dog1', width: 160 },
			  		                { display: '撤单汇总', name: 'ang_dog2', width: 160 },
			  		                { display: '竣工汇总', name: 'ang_dog3', width: 160 },
			  		                { display: '受理工单汇总', name: 'ang_dog4', width: 160 }
		  		                
		  		                ], 
		  		                delayLoad:true,
		  		                url: application.fullPath + "authority/ReportCs/queryInfoCustCareOrderMonthly",
		  		                height:300,pageSize:30 ,rownumbers:false
		            });
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
			 params.tele_type = $("input[name='tele_type']:checked").val();
			 /*params.dept_no  = $("#select_urbon").val();*/
			 params.oper_no  = audit.getJsonVal("select_channel","oper_id");
			 params.area_code  = audit.getJsonVal("select_people","area_code"); 
			 params.begin_date = audit.formatDate($("#date_begin").val());
			 params.end_date= audit.formatDate($("#date_end").val());
			 var orderState=$("#select_order_state").val();
			 params.order_state=orderState;
			/*  params.pay_type = $("#select_pay_type").val();		 
			 params.device_number = $("#select_number").val(); */
			 
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
                var colNames = "cust_care_id,addr_id,addr_desc,addr_memo,device_number,is_addr,res_type,oper_id,oper_name,oper_phone,oper_date,cust_name,link_phone,order_id";
                post(application.fullPath + "authority/ReportCs/queryInfoCustCareOrderExcel",colNames);
            }
            else if (audit_report_type == "terminal_list"){
            	var colNames = "cust_care_id,addr_id,is_addr,res_type,order_state,oper_id,oper_name,oper_phone,oper_date,finish_date,is_over,dept_name,order_id";
                post(application.fullPath + "authority/ReportCs/queryInfoCustCareOrderExcel",colNames);
            }
            else if (audit_report_type == "business_list"){
            	var colNames = "cust_care_id,oper_id,oper_name,dept_no,dept_name,comm_fee,ang_dog1,ang_dog2,ang_dog3,ang_dog4";
                post(application.fullPath + "authority/ReportCs/queryInfoCustCareOrderMonthlyExcel",colNames);
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
            params.begin_date = audit.formatDate($("#date_begin").val());
            params.end_date = audit.formatDate($("#date_end").val());
            
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
        <div class="show_title"><!--<span class="crumb">当前位置：账号选择</span>-->客响工单<!--<span class="red">选择完一项后，才能选择下面一项的内容，否则下面全部置灰无法选择；仅可以做单选。</span>-->
        </div>
    </div>
	<div class="box box_margin_right" id="audit_report_type_box">
        <div class="show_big_title"><strong>.</strong>请选择清单的类别：</div>
        <div class="table_box">
          <table width="446" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td width="24" height="22" align="center"><input type="radio" name="audit_report_type" value="operating_receipt" id="checkbox" checked="checked"/></td>
              <td width="124">客响核查清单</td>
     <!--  </tr>
           <tr> -->
             <!--  <td width="24" height="22" align="center"><input type="radio" name="audit_report_type" value="terminal_list" id="checkbox2" /></td>
              <td width="124">工单处理清单</td>  
              <td width="25" align="center"><input type="radio" name="audit_report_type" value="business_list" id="checkbox5" /></td>
              <td width="125">月度报表清单</td>    -->
            </tr>
          </table>
        </div>
        
    </div>


		<div class="box" id="select_rang_box">
        <div class="show_big_title"><strong>.</strong>请选择需查询的范围：</div>
        <div class="table_box_none">
          <table width="486" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
              <td width="50"  height="22" align="right"><strong>操作员：</strong></td>
              <td width="372" align="center"><div id="select_channel_delete" class="input_click_delete"></div>
              <a href="javascript:void(0);"><input type="text" name="select3" id="select_channel"  class="input_click"  readonly="readonly"/>
              </a></td>
            </tr>
            <tr id="select_people_tr">
              <td width="50"  height="22" align="right"><strong>区域：</strong></td>
              <td width="372" align="center"><div id="select_people_delete"  class="input_click_delete"></div>
              <a href="javascript:void(0);"><input type="text" name="select3" id="select_people"  class="input_click"  readonly="readonly"/>
              </a></td>
            </tr>
          </table>
        </div>
    </div>
 <div class="box box_margin_right" id="report_date_box">
        <div class="show_big_title"><strong>.</strong>请选择统计报表的时间：</div>
        <div class="table_box">
         <table width="446" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="210"> <t:date id="date_begin" name="date_begin"  maxDateElId="date_end"  value="<%=beforeDate%>"  ></t:date></td>
            <td width="24" align="center">到</td>
            <td > <t:date id="date_end" name="date_end"   minDateElId="date_begin"  value="<%=nowDate%>"  maxDate="2050-01-01"  ></t:date></td>
          </tr>
          
        </table>
        </div>
        
    </div>


	<div class="box" id="report_state_box">
        <div class="show_big_title"><strong>.</strong>请选择统客响订单状态：</div>
        <div class="table_box_none">
         <table  border="0" align="center" cellpadding="0" cellspacing="0">
        
          <tr>
            <td ><select id='select_order_state' name='select_order_state'><option value ="">全部</option> <option value ="1" selected>待核查</option> <option value ="2">核查有资源</option> <option value ="-2">核查无资源</option> </select></td>
           
          </tr>
        </table>
        </div>
    </div>

  
  <div class="dashed">
  	<a id="reset" href="javascript:void(0);">重 置</a> <a id="confirm" href="javascript:void(0);">确 定</a>  <!-- <a id="export" href="javascript:void(0);">导 出</a> -->
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
	<div class="select-title">客响确认</div>
	<div class="select-head-kh">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;温馨提示：如果有资源就点击有资源按钮提交，如果没有资源请在备注栏上写上是否选择有线通资源再进行提交。</div>
<br>
	<div class="search-input">
          <table  border="0" align="center" cellpadding="0" cellspacing="0">
          	<tr>
              <td width="100"  height="22" align="right"><strong>订单编号：</strong></td>
              <td width="372" align="center"><input type="text" name="cust_care_id" id="cust_care_id"  class="input_click"  readonly="readonly"/></td>
            </tr>
            <tr>
              <td  width="100"  height="22" align="right"><strong>联系人名称：</strong></td>
              <td  align="center"><input type="text" name="link_name" id="link_name"  class="input_click"  readonly="readonly"/></td>
            </tr>
            <tr>
              <td width="100"  height="22" align="right"><strong>联系人电话：</strong></td>
              <td  align="center"><input type="text" name="link_phone" id="link_phone"  class="input_click"  readonly="readonly"/></td>
            </tr>
              <tr>
              <td   style="display:none" width="100"  height="22" align="right"><strong>客户名称：</strong></td>
              <td   style="display:none" align="center"><input type="text" name="cust_name" id="cust_name"  class="input_click"  readonly="readonly"/></td>
            </tr>
              <tr>
              <td style="display:none" width="100"  height="22" align="right"><strong>证件号码：</strong></td>
              <td style="display:none"  align="center"><input type="text" name="id_number" id="id_number"  class="input_click"  readonly="readonly"/></td>
            </tr>
              <tr>
              <td style="display:none" width="100"  height="22" align="right"><strong>证件失效时间：</strong></td>
              <td  style="display:none" align=""><input type="text" name="self_date" id="self_date"  class="input_click"  readonly="readonly"/></td>
            </tr>
              <tr>
              <td style="display:none"  width="100"  height="22" align="right"><strong>证件地址：</strong></td>
              <td style="display:none"  align="center"><input type="text" name="cust_addr" id="cust_addr"  class="input_click"  readonly="readonly"/></td>
            </tr>
              <tr>
              <td style="display:none" width="100"  height="22" align="right"><strong>产品编号：</strong></td>
              <td  style="display:none" align="center"><input type="text" name="ofr_code" id="ofr_code"  class="input_click"  readonly="readonly"/></td>
            </tr>
              <tr>
              <td style="display:none" width="100"  height="22" align="right"><strong>产品名称：</strong></td>
              <td style="display:none"  align="center"><input type="text" name="ofr_name" id="ofr_name"  class="input_click"  readonly="readonly"/></td>
            </tr>
         
              <tr>
              <td width="100"  height="22" align="right"><strong>操作员工号：</strong></td>
              <td  align="center"><input type="text" name="oper_id" id="oper_id"  class="input_click"  readonly="readonly"/></td>
            </tr>
            
			<tr>
              <td width="100"  height="22" align="right"><strong>操作员名称：</strong></td>
              <td  align="center"><input type="text" name="oper_name" id="oper_name"  class="input_click"  readonly="readonly"/></td>
            </tr>
            <tr>
              <td width="100"  height="22" align="right"><strong>操作员电话：</strong></td>
              <td  align="center"><input type="text" name="oper_phone" id="oper_phone"  class="input_click"  readonly="readonly"/></td>
            </tr>
           	<tr>
              <td width="100"  height="22" align="right"><strong>操作时间：</strong></td>
              <td  align="center"><input type="text" name="oper_date" id="oper_date"  class="input_click"  readonly="readonly"/></td>
            </tr>
            <tr>
          
            <tr>
              <td width="100"  height="22" align="right"><strong>装机地址：</strong></td>
              <td  align="center"><input type="text" name="addr_desc" id="addr_desc"  class="input_click"  readonly="readonly"/></td>
            </tr>
            <tr>
              <td width="100"  height="22" align="right"><strong>局向地址：</strong></td>
              <td  align="center"><select  ltype="init" name="select_area" id="select_area" url="<%=fullPath%>authority/webUtil/queryRuleAreaHx" class="sel"></td>
            </tr>
            <tr>
              <td width="100"  height="22" align="right"><strong>标准地址：</strong></td>
              <td  align="center"><input type="text" name="addr_memo" id="addr_memo"  class="input_click"  readonly="readonly"/></td>
            </tr>
             <tr>
              <td width="100"  height="22" align="right"><strong>备注：</strong></td>
              <td  align="center"><input type="text" name="kxmemo" id="kxmemo"  class="input_click"  /></td>
            </tr>
             <tr>
              <td  style="display:none"  width="100"  height="22" align="right"><strong>备注：</strong></td>
              <td  style="display:none"  align="center"><input type="text" name="memo" id="memo"  class="input_click"  /></td>
            </tr>
            <input type="hidden" name="area_id" id="area_id" />
            <input type="hidden" name="addr_id" id="addr_id" />
        </table>
        <a id="addr_change_submit" href="javascript:void(0);" class="js_query">有资源</a> <a id="addr_change_cancel_submit" href="javascript:void(0);" class="js_query">无资源</a> 
        </div>
  </div>
  

   <div id="select_addr_search_dialog" class="show-select" style="display:none;">
	<div class="icon-close"></div>
	<div class="select-title">标准地址：</div>
	<div class="search-input">
		<div class="leble">请输入地址：</div>
		<div class="input"><input class="addr_txt" type="text" value=""></div>
		<a class="js_query" href="javascript:void(0);">查 询</a>
		<a class="js_reset" href="javascript:void(0);">重 置</a>	</div>
	<div id="select_addr_search_table" class="select-table"></div>	
  </div>
  
  
  
 </div>		
</body>
</html>