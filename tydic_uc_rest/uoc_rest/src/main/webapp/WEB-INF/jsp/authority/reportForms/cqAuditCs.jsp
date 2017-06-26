<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tydic.unicom.crm.ussM165.vo.pub.RuleAreaHxVo" %>
<%@ page import="java.util.Date" %>

<%
	String nowDate = DateUtil.getCurrentDate(); 
	String beforeDate = DateUtil.getAfterMonth(new Date(), -1);
	String info_oper_flag=(String)request.getAttribute("info_oper_flag");
	RuleAreaHxVo ruleOperRoleVo =(RuleAreaHxVo)request.getAttribute("ruleAreaHxVo");
	String area_code_limit=ruleOperRoleVo.getArea_code();
	String area_code_limit_name=ruleOperRoleVo.getArea_name();
%>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>统一销售服务系统,稽核报表</title>
<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/audit.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>js/jquery-ligerui-2.25/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />

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
				rep_list_array:["operating_receipt","operating_receipt_all","commsion_list","commsion_list_all","terminal_list","terminal_list_all"],
				rep_list_manager:{"operating_receipt":null,"operating_receipt_all":null,"commsion_list":null ,"commsion_list_all":null,"terminal_list":null,"terminal_list_all":null},
				cur_report_name:"",//当前报表类型
				report_manager :{}
		};
		$(document).ready(function (){

			audit.initRepListArray();
			//初始化需查询的范围
			audit.initQueryRange();
			audit.initReport();
            audit.initClick();
            audit.initArea();
        });
		
		 audit.initArea = function(){
			/* var params = {};
            var temp_form =document.createElement("form");
            temp_form.action = application.fullPath + "authority/ReportCs/queryReportCsInitArea";
            temp_form.method = "post";
            temp_form.submit(); */
			<% if("all".equals(info_oper_flag)){%>
				
			<%}else{%>
				$("#select_people").val("<%=area_code_limit_name%>");
				$('#select_people').attr("disabled", "disabled");
				$('#select_people_delete').attr("disabled", "disabled");
			
			<%}%>
			 
		 }
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
			//选择地市改变
			$("#select_city").change(function(){
				var select = $("#select_urbon");
				var url = select.attr("url");
				var value = $(this).val();
				//清空操作
				audit.empty.emptySelect("select_urbon");
				audit.empty.emptyInput("select_channel");
				audit.empty.emptyInput("select_people");
				
				url += "?local_net="+value;
				audit.clearGrid(audit.dialog.manager.channel);
				audit.clearGrid(audit.dialog.manager.staff);
				audit.createSelect(select,url);
			});
			//选择城区改变
			$("#select_urbon").change(function(){
				//清空操作
				audit.empty.emptyInput("select_channel");
				audit.empty.emptyInput("select_people");	
				audit.clearGrid(audit.dialog.manager.channel);
				audit.clearGrid(audit.dialog.manager.staff);
			});
			//
			$("#select_channel").click(function(){
				/* var select_urbon = $("#select_urbon").val();
				if(!select_urbon){
					$.alert("请选择城区!");
					return;
				} */
				audit.dialog.showDialog("select_channel_dialog");
				audit.dialog.initChannelDialog();
			});
			
			$("#select_people").click(function(){
				/* var channel = $("#select_channel").val();
				if(!channel){
					$.alert("请选择营业厅!");
					return;
				} */
				audit.dialog.showDialog("select_staff_dialog");
				audit.dialog.initStaffDialog();
			});			
		};
		
		audit.dialog = {
			manager:{channel:null,staff:null},
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
				 //  params.parent_dept_no  = $("#select_urbon").val();
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
	  		              	url: application.fullPath + "authority/webUtilUssM165/queryRuleAreaHx",
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
		/**
		*生成下拉框
		*/
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
			$("#select_number_delete").click(function(){
				audit.empty.emptyInput("select_number");
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
			if(value == "operating_receipt"){//APP收费报表明细
				audit.incomeListCss();
				if(manager == null){
					audit.operatingReceipt(value);
				}			
			}else if(value == "operating_receipt_all"){//APP收费汇总报表
				audit.incomeListCss();
				if(manager == null){
					audit.operatingReceiptAll(value);
				}				
			}else if(value == "commsion_list"){//APP佣金报表明细
				audit.incomeListCss();
				if(manager == null){
					audit.commsionList(value);
				}			
			}else if(value == "commsion_list_all"){//APP佣金汇总报表
				audit.incomeListCss();
				if(manager == null){
					audit.commsionListAll(value);
				}			
			}
			else if(value == "terminal_list"){//APP受理明细清单
				audit.incomeListCss();
				if(manager == null){
					audit.terminalList(value);
				}			
			}
			else if(value == "terminal_list_all"){//APP受理清单汇总

				audit.incomeListCss();
				if(manager == null){
					audit.terminalListAll(value);
				}			
			}
		}
		audit.commmonCss = function(){
			//$("#select_people_tr").hide();
			//audit.empty.emptyInput("select_people");
			$("#select_channel_tr").hide();

		};
		audit.operatingReceiptCss = function(){
			this.commmonCss();
		};
		audit.incomeListCss = function(){
			this.commmonCss();
		};
		audit.businessListCss = function(){
			$("#select_number_tr").show();
		};
		audit.terminalListCss = function(){
			$("#select_people_tr").show();		
		};
		//APP收费报表明细,处理函数
		audit.operatingReceipt = function(value){	
			 audit.rep_list_manager[value] = 
		            $("#rep_"+value).ligerGrid({
		                columns: [
		                            { display: '缴费流水号', name: 'pay_cs_id', width: 100 },
		                            { display: '电信类型', name: 'tele_type', width: 120 },
			  		                { display: '设备号码', name: 'device_number', width: 200 },
			  		                { display: '缴费类型', name: 'pay_type', width: 200 },
			  		                { display: '订单号', name: 'cs_order_id', width: 200 },
			  		                { display: '应缴金额', name: 'total_fee', width: 200 },
			  		                { display: '减免金额', name: 'discount_fee', width: 200 },
			  		                { display: '缴纳金额', name: 'payed_fee', width: 200 },
			  		              //  { display: '票据标识', name: 'invoice_flag', width: 200 },
			  		                { display: '缴费状态', name: 'flag', width: 200 },
			  		                { display: '操作工号', name: 'oper_id', width: 200 },
			  		                { display: '操作工号名称', name: 'oper_name', width: 200 },
			  		                { display: 'AB类工号', name: 'oper_id_ab', width: 100 },
			  		                { display: '缴纳时间', name: 'create_date', width: 200 },
			  		                { display: '返佣类型', name: 'pay_commission_fee_type', width: 200 },
			  		                { display: '返佣金额', name: 'commission_fee', width: 200 },
			  		                { display: '代支付金额', name: 'agent_pay_fee', width: 200 },
			  		                { display: '前台支付金额', name: 'app_pay_fee', width: 200 },
			  		                { display: '分公司区域', name: 'area_id', width: 200 },
			  		                { display: '分公司名称', name: 'area_name', width: 200 }
			  		               
		  		                ],
		  		                delayLoad:true,
		  		                url: application.fullPath + "noauthority/ReportCs/queryLogPayCs",
		  		                height:300,pageSize:30 ,rownumbers:false
		            });
		};
		
		
		
		///APP收费汇总报表,处理函数
		audit.operatingReceiptAll = function(value){	
			 audit.rep_list_manager[value] = 
		            $("#rep_"+value).ligerGrid({
		                columns: [
		                            { display: '分公司区域', name: 'area_id', width: 200 },
		                            { display: '分公司名称', name: 'area_name', width: 200 },
		                            { display: '应缴金额汇总', name: 'total_fee', width: 200 },
			  		                { display: '减免金额汇总', name: 'discount_fee', width: 200 },
			  		                { display: '缴纳金额汇总', name: 'payed_fee', width: 200 } 
			  		               
		  		                ],
		  		                delayLoad:true,
		  		                url: application.fullPath + "noauthority/ReportCs/queryLogPayCsAll",
		  		                height:300,pageSize:30 ,rownumbers:false
		            });
		};
		
		//APP受理明细清单,处理函数
		audit.terminalList = function(value){
			audit.rep_list_manager[value]  = 
		            $("#rep_"+value).ligerGrid({
		                columns: [	                
		  		             	{ display: '订单号', name: 'cs_order_id', width: 100 },
	                            { display: '佣金金额', name: 'comm_fee', width: 120 },
	                            { display: '销售品编号', name: 'ofr_id', width: 120 },
		  		                { display: '客户ID号与BSS关联', name: 'customer_no', width: 180 },
		  		                { display: '客户名称', name: 'customer_name', width: 150 },
		  		                { display: '证件类型', name: 'id_type', width: 100 },
		  		                { display: '身份证号', name: 'customer_id', width: 200 },
			  		              { display: '证件地址', name: 'id_addr', width: 200,render:function(row){
										var id_addr = row.id_addr,temp_name = row.id_addr;                
				  		            	if(temp_name&&temp_name.length>20){
				  		            	temp_name = temp_name.substr(0,19)+"....";
				  		            	}
				  		            	return "<a href='javascript:void(0);' title='"+id_addr+"'>"+temp_name+"</a>";
			  		            	}},
		  		                { display: '联系人姓名', name: 'link_name', width: 150 },
		  		                { display: '身份证正面照图片链接地址编码', name: 'pic_link0', width: 200 },
		  		                { display: '身份证反面照图片链接地址编码', name: 'pic_link1', width: 200 },
		  		                { display: '证件到期时间', name: 'id_exp_date', width: 150 },
		  		                { display: '号线标准地址ID', name: 'address_id', width: 100 },
		  		             	 { display: '实际装机地址', name: 'address_str', width: 200,render:function(row){
									var id_addr = row.address_str,temp_name = row.address_str;                
			  		            	if(temp_name&&temp_name.length>20){
			  		            	temp_name = temp_name.substr(0,19)+"....";
			  		            	}
			  		            	return "<a href='javascript:void(0);' title='"+id_addr+"'>"+temp_name+"</a>";
		  		            	}},
		  		                { display: '号线局站地址', name: 'exch_id', width: 150 },
		  		                { display: '乐视tv安装标志', name: 'letv_flag', width: 200 },
		  		                { display: '乐视tv安装套餐', name: 'letv_service_dinner', width: 100 },
		  		              	{ display: '备注', name: 'note', width: 200,render:function(row){
									var id_addr = row.note,temp_name = row.note;                
			  		            	if(temp_name&&temp_name.length>20){
			  		            	temp_name = temp_name.substr(0,19)+"....";
			  		            	}
			  		            	return "<a href='javascript:void(0);' title='"+id_addr+"'>"+temp_name+"</a>";
		  		            	}},
		  		                { display: '分公司区域', name: 'area_id', width: 150 },
		  		                { display: '分公司名称', name: 'area_name', width: 150 },
		  		                { display: '订单状态', name: 'flag', width: 100 },
		  		                { display: '操作工号', name: 'oper_id', width: 100 },
		  		                { display: '操作工号名称', name: 'oper_name', width: 100 },
		  		                { display: 'AB类工号', name: 'oper_id_ab', width: 100 },
		  		                { display: '生成订单时间', name: 'create_date', width: 200 },
		  		                { display: '最近操作时间', name: 'update_date', width: 200 }
		  		                
		  		                ], 
		  		                delayLoad:true,
		  		                url: application.fullPath + "noauthority/ReportCs/queryOrderCs",
		  		                height:300,pageSize:30 ,rownumbers:false
		            });
		};
		
		//APP受理汇总清单,处理函数
		audit.terminalListAll = function(value){
			audit.rep_list_manager[value]  = 
		            $("#rep_"+value).ligerGrid({
		                columns: [	                
		                        { display: '分公司区域', name: 'area_id', width: 160 },
		                        { display: '分公司名称', name: 'area_name', width: 200 },
		  		                { display: '在途汇总', name: 'ang_dog1', width: 160 },
		  		                { display: '撤单汇总', name: 'ang_dog2', width: 160 },
		  		                { display: '竣工汇总', name: 'ang_dog3', width: 160 },
		  		                { display: '受理工单汇总', name: 'ang_dog4', width: 160 }
		  		                ], 
		  		                delayLoad:true,
		  		                url: application.fullPath + "noauthority/ReportCs/queryOrderCsAll",
		  		                height:300,pageSize:30 ,rownumbers:false
		            });
		};
		//APP收费报表明细,处理函数
		audit.commsionList = function(value){	
			 audit.rep_list_manager[value] = 
		            $("#rep_"+value).ligerGrid({
		                columns: [
		                            { display: '佣金流水号', name: 'comm_sn', width: 100 },
		                            { display: '佣金规则编号', name: 'rule_id', width: 120 },
			  		                { display: '订单号', name: 'cs_order_id', width: 200 },
			  		                { display: '代理商工号', name: 'oper_id', width: 200 },
			  		                { display: '代理商工号名称', name: 'oper_name', width: 200 },
			  		                { display: '付佣金代理商工号', name: 'pay_oper_id', width: 200 },
			  		                { display: '付佣金代理商工号名称', name: 'pay_oper_name', width: 200 },
			  		                { display: '流水类型', name: 'comm_type', width: 200 },
			  		                { display: '状态', name: 'comm_status', width: 200 },
			  		                { display: '佣金金额', name: 'comm_fee', width: 200 },
			  		                { display: '客户名称', name: 'customer_name', width: 200 },
			  		                { display: '销售品名称', name: 'ofr_name', width: 200 },
			  		                { display: '生成日期', name: 'comm_date', width: 100 },
			  		                { display: '是否可提现', name: 'get_cash_flag', width: 200 },
			  		                { display: '提现状态', name: 'get_cash_status', width: 200 },
			  		                { display: '提现金额', name: 'cash_money', width: 200 },
			  		                { display: '账务月', name: 'acct_month', width: 200 },
			  		                { display: '提现流水号', name: 'get_cash_cs_id', width: 200 },
			  		                { display: '分公司区域', name: 'area_id', width: 200 },
			  		                { display: '分公司名称', name: 'area_name', width: 200 }
			  		               
		  		                ],
		  		                delayLoad:true,
		  		                url: application.fullPath + "noauthority/ReportCs/queryRptCommissionList",
		  		                height:300,pageSize:30 ,rownumbers:false
		            });
		};
		
		
		
		///APP收费汇总报表,处理函数
		audit.commsionListAll = function(value){	
			 audit.rep_list_manager[value] = 
		            $("#rep_"+value).ligerGrid({
		                columns: [
		                            { display: '分公司区域', name: 'area_id', width: 300 },
		                            { display: '分公司名称', name: 'area_name', width: 300 },
		                            { display: '佣金汇总', name: 'comm_fee', width: 400 }			  		               
		  		                ],
		  		                delayLoad:true,
		  		                url: application.fullPath + "noauthority/ReportCs/queryRptCommissionListAll",
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
			/*  params.local_net  = $("#select_city").val();
			 params.dept_no  = $("#select_urbon").val();*/
			 params.oper_no  = audit.getJsonVal("select_channel","oper_id");
			 <% if("all".equals(info_oper_flag)){%>
				params.area_code  = audit.getJsonVal("select_people","area_code"); 

			<%}else{%>
				params.area_code=<%=area_code_limit%>; 

			<%}%>			 
			 
			 params.begin_date = audit.formatDate($("#date_begin").val());
			 params.end_date= audit.formatDate($("#date_end").val());
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
			 $("#select_city option:first").attr('selected','selected'); 
			 $("#select_urbon option:first").attr('selected','selected'); 
			 audit.empty.emptyInput("select_channel");
			 audit.empty.emptyInput("select_people");
			 audit.empty.emptyInput("select_number");

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
                var colNames = "pay_cs_id,tele_type,device_number,pay_type,cs_order_id,total_fee,discount_fee,payed_fee,flag,oper_id,oper_name,oper_id_ab,create_date,pay_commission_fee_type,commission_fee,agent_pay_fee,app_pay_fee,area_id,area_name";
                post(application.fullPath + "noauthority/ReportCs/queryLogPayCsExcel",colNames);
            }
            else if (audit_report_type == "operating_receipt_all"){
            	var colNames = "area_id,area_name,total_fee,discount_fee,payed_fee";
                post(application.fullPath + "noauthority/ReportCs/queryLogPayCsAllExcel",colNames);
            }
            else if (audit_report_type == "terminal_list"){
            	var colNames = "cs_order_id,comm_fee,ofr_id,customer_no,customer_name,id_type,customer_id,link_name,id_addr,pic_link0,pic_link1,id_exp_date,address_id,address_str,exch_id,letv_flag,letv_service_dinner,note,area_id,area_name,flag,oper_id_ab,oper_id,oper_name,create_date,update_date";
                post(application.fullPath + "noauthority/ReportCs/queryOrderCsExcel",colNames);
            }
            else if (audit_report_type == "terminal_list_all"){
            	var colNames = "area_id,area_name,ang_dog1,ang_dog2,ang_dog3,ang_dog4";
                post(application.fullPath + "noauthority/ReportCs/queryOrderCsAllExcel",colNames);
            }
            else if (audit_report_type == "commsion_list"){
            	var colNames = "comm_sn,rule_id,cs_order_id,oper_id,oper_name,pay_oper_id,pay_oper_name,comm_type,comm_status,comm_fee,customer_name,ofr_name,comm_date,get_cash_flag,get_cash_status,cash_money,acct_month,get_cash_cs_id,area_id,area_name";
                post(application.fullPath + "noauthority/ReportCs/queryRptCommissionListExcel",colNames);
            }
            else if (audit_report_type == "commsion_list_all"){
            	var colNames = "area_id,area_name,comm_fee";
                post(application.fullPath + "noauthority/ReportCs/queryRptCommissionListAllExcel",colNames);
            }
        };
        
        function post(url,cols){
            var params = {};
            //params.tele_type = $("input[name='tele_type']:checked").val();
            //params.local_net = $("#select_city").val();
            // params.dept_no = $("#select_urbon").val();
            //params.oper_dept_no = audit.getJsonVal("select_channel", "dept_no");
            // params.oper_no = audit.getJsonVal("select_people", "oper_no");
            params.begin_date = audit.formatDate($("#date_begin").val());
            params.end_date = audit.formatDate($("#date_end").val());
            <% if("all".equals(info_oper_flag)){%>
				params.area_code  = audit.getJsonVal("select_people","area_code"); 
	
			<%}else{%>
				params.area_code=<%=area_code_limit%>; 
	
			<%}%>	
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
        <div class="show_title"><!--<span class="crumb">当前位置：账号选择</span>-->稽核报表<!--<span class="red">选择完一项后，才能选择下面一项的内容，否则下面全部置灰无法选择；仅可以做单选。</span>-->
        </div>
    </div>
	<div class="box box_margin_right" id="audit_report_type_box">
        <div class="show_big_title"><strong>.</strong>请选择报表的类别：</div>
        <div class="table_box">
          <table width="446" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td width="24" height="22" align="center"><input type="radio" name="audit_report_type" value="operating_receipt" id="checkbox" checked="checked"/></td>
              <td width="124">APP收费明细清单</td>
              <td width="24" height="22" align="center"><input type="radio" name="audit_report_type" value="terminal_list" id="checkbox2" /></td>
              <td width="124">APP受理明细清单</td> 
              <td width="24" height="22" align="center"><input type="radio" name="audit_report_type" value="commsion_list" id="checkbox3" /></td>
              <td width="124">APP佣金明细报表</td>
              
 			</tr>
             <tr>
              <td width="25" height="22" align="center"><input type="radio" name="audit_report_type" value="operating_receipt_all" id="checkbox5" /></td>
              <td width="125">APP收费汇总报表</td>
			  <td width="24" height="22" align="center"><input type="radio" name="audit_report_type" value="terminal_list_all" id="checkbox4" /></td>
              <td width="124">APP受理汇总报表</td>			  
			  <td width="25" height="22" align="center"><input type="radio" name="audit_report_type" value="commsion_list_all" id="checkbox6" /></td>
              <td width="125">APP佣金汇总报表</td>    
            </tr>
          </table>
        </div>
        
    </div>
	
  <!-- <div class="box" id="tele_type_box">
        <div class="show_big_title"><strong>.</strong>是否需要选择电信类型？</div>
        <div class="table_box">
          <table width="446" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td width="24" height="22" align="center"><input type="radio" value="" name="tele_type" id="checkbox" checked="checked"/></td>
              <td width="124">全选</td>
              <td width="24" align="center"><input type="radio" value="2G" name="tele_type" id="checkbox3" /></td>
              <td width="123">2G</td>
              <td width="26" align="center"><input type="radio" value="3G" name="tele_type" id="checkbox5" /></td>
              <td width="125">3G</td>
            </tr>
            <tr>
              <td height="22" align="center"><input type="radio" value="4G" name="tele_type" id="checkbox2" /></td>
              <td>              4G</td>
              <td align="center"><input type="radio" value="M165" name="tele_type" id="checkbox4" /></td>
              <td>              宽带</td>
              <td align="center">&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
          </table>
        </div>
    </div> -->

		<!-- <div class="box box_margin_right" id="pay_type_box">
			<div class="show_big_title"><strong>.</strong>请选择支付方式的类别：</div>
			<div class="table_box">
				<table width="446" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<td width="24" height="22" align="center"><input type="radio" name="pay_type" value="" checked="checked"/></td>
						<td width="124">全部</td>
						<td width="24" align="center"><input type="radio" name="pay_type" value="现金" /></td>
						<td width="124">现金</td>
						<td width="25" align="center"><input type="radio" name="pay_type" value="银行托收" /></td>
						<td width="125">银行托收</td>
					</tr>
					<tr>
						<td height="22" align="center"><input type="radio" name="pay_type" value="支票收费" /></td>
						<td>支票收费</td>
						<td align="center"><input type="radio" name="pay_type" value="POS刷卡" /></td>
						<td>POS刷卡</td>
						<td align="center">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table>
			</div>
		</div> -->

		<div class="box" id="select_rang_box">
        <div class="show_big_title"><strong>.</strong>请选择需查询的范围：</div>
        <div class="table_box_none">
          <table width="486" border="0" align="center" cellpadding="0" cellspacing="0">
           <%--  <tr>
              <td width="114" height="22" align="right"><strong>地  市：</strong></td>
              <td width="372" align="center"><select  ltype="init" name="select_city" id="select_city" url="<%=fullPath%>authority/webUtil/queryLocalNet" class="sel">
              </select></td>
            </tr>
            <tr>
              <td height="22" align="right"><strong>城  区：</strong></td>
              <td align="center"><select name="select_urbon" id="select_urbon" url="<%=fullPath%>authority/webUtil/queryDeptByLocalNet"  class="sel">
              </select></td>
            </tr> --%>
			<tr id="select_channel_tr">
              <td width="50"  height="22" align="right"><strong>操作员：</strong></td>
              <td width="372" align="center"><div id="select_channel_delete" class="input_click_delete"></div>
              <a href="javascript:void(0);"><input type="text" name="select3" id="select_channel"  class="input_click"  readonly="readonly"/>
              </a></td>
            </tr>
            <tr id="select_people_tr">
              <td width="50"  height="22" align="right"><strong>分公司：</strong></td>
              <td width="372" align="center"><div id="select_people_delete"  class="input_click_delete"></div>
              <a href="javascript:void(0);"><input type="text" name="select3" id="select_people"  class="input_click"  readonly="readonly"/>
              </a></td>
            </tr>
           <!--  <tr id="select_number_tr">
              <td width="114"  height="22" align="right"><strong>号码选择：</strong></td>
              <td width="372" align="center"><div id="select_number_delete" class="input_click_delete"></div>
              <a href="javascript:void(0);"><input type="text" name="select3" id="select_number"  class="input_click" />
              </a></td>
            </tr>
			<tr id="select_audit_flag_tr">			  
			  <td width="114" height="22" align="right"><strong>是否稽核：</strong></td>
              <td width="372" align="center">
			  <select  name="select_audit_flag" id="select_audit_flag" class="sel">
			  <option value="1">是</option>
			  <option value="0">否</option>			  
              </select></td>
            </tr>
			<tr id="select_audit_status_tr">			  
			  <td width="114" height="22" align="right"><strong>是否稽核通过：</strong></td>
              <td width="372" align="center"><select  name="select_audit_status" id="select_audit_status" class="sel">
			  <option value="11">通过</option>
			  <option value="12">不通过</option>
			  <option value="13">重新复核</option>			  
			 <option>重新</option>
              </select></td>
            </tr> -->
          </table>
        </div>
    </div>
 
<div class="box box_long" id="report_date_box">
        <div class="show_big_title"><strong>.</strong>请选择统计报表的时间：</div>
        <div class="table_box">
        <table width="980" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="210"> <t:date id="date_begin" name="date_begin"  maxDateElId="date_end"  value="<%=beforeDate%>"  ></t:date></td>
            <td width="24" align="center">到</td>
            <td width="746"> <t:date id="date_end" name="date_end"   minDateElId="date_begin"  value="<%=nowDate%>"  maxDate="2050-01-01"  ></t:date></td>
          </tr>
        </table>
		</div>
        
  </div>
  
  <div class="dashed">
  	<a id="reset" href="javascript:void(0);">重 置</a> <a id="confirm" href="javascript:void(0);">确 定</a> <a id="export" href="javascript:void(0);">导 出</a>
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
		<div class="leble">分公司编号：</div>
		<div class="input"><input class="branch-code" type="text" value=""></div>
		<div class="leble">分公司名称：</div>
		<div class="input"><input class="branch-name" type="text" value=""></div>
		<a class="js_query" href="javascript:void(0);">查 询</a>
		<a class="js_reset" href="javascript:void(0);">重 置</a>	</div>
	<div id="select_staff_table" class="select-table"></div>	
  </div>
    <!--稽核界面 -->
  <div id="sampling_audit_dialog" class="show-select" style="display:none;">
	<div class="icon-close"></div>
	<div class="select-title">协议展示：</div>	
	<table style="margin:0 auto">
	<tr>
		<td><a class="audit_leble" href="javascript:get_pic_pre();"><</a></td>
		<td><Iframe height='200' width='836' src="" id="identitypic" scrolling="auto" ></iframe></td>
		<td><Iframe height='200' width='836' src="" id="getPdf"  scrolling="auto" style="display:none;"></iframe></td>
		<td><a class="audit_leble" href="javascript:get_pic_next();">></a></td>
	</tr>	
	</table>		
	<div id="audit_table" >	
	<div id ="audit_note_div">
	<div id="audit_note_title" class="select-title">稽核处理意见：</div>	
	<div style="height:50px" id ="audit_note_text">
	<input type="hidden"  id="audit_order_id"  value="">
	<input  id="audit_note" type="text" value=""></div>	
	</div>
	<div class="audit_a">
  	<a id="audit_pass" style="margin:0px 5px" href="javascript:sampling_audit(11);">审核通过</a><a id="audit_no_pass" style="margin:0px 5px" href="javascript:sampling_audit(12);">审核不通过</a><a id="audit_again" style="margin:0px 5px" href="javascript:sampling_audit(13);">重新复核</a>
	</div>
	
  </div>		
</body>
</html>