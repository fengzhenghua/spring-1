<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
<%@ page import="java.util.Date" %>
<%
	String nowDate = DateUtil.getCurrentDate(); 
	String beforeDate = DateUtil.getAfterMonth(new Date(), -1);
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>统一销售服务系统,稽核报表</title>
<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/audit.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>js/jquery-ligerui-2.25/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />

<script src="<%=basePath%>js/jquery-ligerui-2.25/js/core/base.js" type="text/javascript"></script> 
<script src="<%=basePath%>js/jquery-ligerui-2.25/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="<%=fullPath%>js/CustomersData.js" type="text/javascript"></script> 
<script type="text/javascript">
	var dateTime = {
			beforeDate:"<%=beforeDate%>",
			nowDate:"<%=nowDate%>"	
	};
</script>
<script type="text/javascript">
		var audit = {
				rep_list_array:["operating_receipt","income_list","business_list","terminal_list"],
				rep_list_manager:{"operating_receipt":null,"income_list":null,"business_list":null ,"terminal_list":null},
				cur_report_name:"",//当前报表类型
				report_manager :{}
		};
		$(document).ready(function (){

			audit.initRepListArray();
			//初始化需查询的范围
			audit.initQueryRange();
			audit.initReport();
            audit.initClick();
        });
		

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
				var select_urbon = $("#select_urbon").val();
				if(!select_urbon){
					$.alert("请选择城区!");
					return;
				}
				audit.dialog.showDialog("select_channel_dialog");
				audit.dialog.initChannelDialog();
			});
			
			$("#select_people").click(function(){
				var channel = $("#select_channel").val();
				if(!channel){
					$.alert("请选择营业厅!");
					return;
				}
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
	  		                { display: '营业厅编码', name: 'dept_no', align: 'left', width: 100, minWidth: 60 },
	  		                { display: '营业厅名称', name: 'dept_name', minWidth: 120 },
	  		                { display: '备注', name: 'comments', minWidth: 140 }
	  		                ], 
	  		                buttonType:"confirm",//右下角按钮类型 目前类型  confirm(确认),excel(导出excel按钮) 
	  		                buttonEvent:function(){
	  		                	var manager = audit.dialog.manager.channel;
	  		                	var row = manager.getSelectedRow();
	  		                	$("#select_channel").val(row.dept_name);
	  		                	$("#select_channel").attr("data",JSON.stringify(row));
	  		                	audit.clearGrid(audit.dialog.manager.staff);
	  		                	audit.dialog.hideDialog("select_channel_dialog");
	  		                },		
	  		                checkbox:true,
	  		                single:true,
	  		                delayLoad:true,
	  		                selectRowButtonOnly:true,
	  		                width:950,
	  		                url: application.fullPath + "authority/webUtil/queryInfoDeptByCons",
	  		                height:300,pageSize:30 ,rownumbers:false
	  		   });
			   $(".icon-close",select_channel_dialog).click(function(){
				   audit.dialog.hideDialog("select_channel_dialog");
			   });
			   $(".js_query",select_channel_dialog).click(function(){
				   var obj = $("#select_channel_dialog");
				   var params = {};
				   params.parent_dept_no  = $("#select_urbon").val();
				   params.dept_no = $(".branch-code", obj).val();
				   params.dept_name = $(".branch-name",obj).val();
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
	  		                { display: '员工工号', name: 'oper_no', align: 'left', width: 100, minWidth: 60 },
	  		                { display: '员工姓名', name: 'oper_name', minWidth: 120 }
	  		                ], 
	  		                buttonType:"confirm",//右下角按钮类型 目前类型  confirm(确认),excel(导出excel按钮) 
	  		                buttonEvent:function(){
	  		                	var manager = audit.dialog.manager.staff;
	  		                	var row = manager.getSelectedRow();
	  		              		$("#select_people").val(row.oper_name);
  		                		$("#select_people").attr("data",JSON.stringify(row));
	  		                	audit.dialog.hideDialog("select_staff_dialog");
	  		                },	
	  		                delayLoad:true,
	  		                checkbox:true,
	  		                single:true,
	  		                selectRowButtonOnly:true,
	  		                width:950,
	  		              	url: application.fullPath + "authority/webUtil/queryInfoOperByDeptOperNo",
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
				   params.dept_no = audit.getJsonVal("select_channel","dept_no");
				   params.oper_no = $(".branch-code",obj).val();
				   params.oper_name = $(".branch-name",obj).val();
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
			if(value == "operating_receipt"){//营业收入
				audit.operatingReceiptCss();
				if(manager == null){
					audit.operatingReceipt(value);
				}			
			}else if(value == "income_list"){//收入清单
				audit.incomeListCss();
				if(manager == null){
					audit.incomeList(value);
				}				
			}else if(value == "business_list"){//业务清单
				audit.businessListCss();
				if(manager == null){
					audit.businessList(value);
				}			
			}else if(value == "terminal_list"){//终端清单
				audit.terminalListCss();
				if(manager == null){
					audit.terminalList(value);
				}			
			}
		}
		audit.commmonCss = function(){
			$("#select_number_tr").hide();
			audit.empty.emptyInput("select_number");
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
			this.commmonCss();
		};
		//点击营业收入,处理函数
		audit.operatingReceipt = function(value){	
			 audit.rep_list_manager[value] = 
		            $("#rep_"+value).ligerGrid({
		                columns: [
		                          { display: 'pay_cs_id', name: 'pay_cs_id', width: 100 },
		                          { display: 'tele_type', name: 'tele_type', width: 120 },
		  		                { display: 'device_number', name: 'device_number', width: 200 }
		  		                /* { display: '费用类型', name: 'charge_code', width: 200 },
		  		                { display: '支付方式', name: 'pay_type', width: 100 },
		  		                { display: '应收金额', name: 'pay_charge', width: 120 },
		  		                { display: '优惠金额', name: 'derate_charge', width: 120 },
		  		                { display: '实收金额', name: 'discount_charge', width: 120 },
		  		                { display: '地市', name: 'oper_local_net' , width: 150 },
		  		                { display: '部门', name: 'oper_dept_no' , width: 300 },
		  		                { display: '受理人员', name: 'oper_no' , width: 150 } */
		  		                ],
		  		                delayLoad:true,
		  		                url: application.fullPath + "noauthority/ReportCs/queryLogPayCs",
		  		                height:300,pageSize:30 ,rownumbers:false
		            });
		};
		//点击收入清单,处理函数
	//	audit.incomeList = function(value){
	//		audit.rep_list_manager[value]  = 
	//	            $("#rep_"+value).ligerGrid({
	//	                columns: [
	//	                          { display: '电信类型', name: 'tele_type', width: 100 },
	//	                          { display: '业务号码', name: 'device_number', width: 150 },
	//	                          { display: '业务类型', name: 'serv_code', width: 200 },
	//	                          { display: '费用类型', name: 'charge_code', width: 200 },
	//	                          { display: '支付方式', name: 'pay_type', width: 100 },
	//	                          { display: '应收金额', name: 'pay_charge', width: 120 },
	//	                          { display: '优惠金额', name: 'derate_charge', width: 120 },
	//	                          { display: '实收金额', name: 'discount_charge', width: 120 },
	//	                          { display: '地市', name: 'oper_local_net' , width: 150 },
	//	                          { display: '部门', name: 'oper_dept_no' , width: 300 },
	//	                          { display: '受理人员', name: 'oper_no' , width: 150 },
	////	                          { display: '受理日期', name: 'operator_date', width: 120 }
	//	  		                ],
	//	  		                delayLoad:true,
	//	  		                url: application.fullPath + "authority/rptBusiFee/QueryRptBusiFeeDetail",
	//	  		                height:300,pageSize:30 ,rownumbers:false
	//	            });
	//	};
		//点击业务清单,处理函数
	//	audit.businessList = function(value){
	//		audit.rep_list_manager[value]  = 
	//	            $("#rep_"+value).ligerGrid({
	//	                columns: [
	//	  		                { display: '电信类型', name: 'tele_type',  width: 100, minWidth: 60 },
	//	  		                { display: '业务类型', name: 'serv_code', width: 250},
	//	  		                { display: '设备号码', name: 'device_number', width: 150 },
	//	  		                { display: '用户姓名', name: 'customer_name',width: 100 },
	//	  		                { display: '资费', name: 'product_name', width: 300,render:function(row){
	//	  		                	var product_name = row.product_name,temp_name = row.product_name;                
	//	  		               		if(temp_name&&temp_name.length>18){
	//	  		               			temp_name = temp_name.substr(0,17)+"....";
	//	  		               		}
	//	  		                	return "<a href='javascript:void(0);' title='"+product_name+"'>"+temp_name+"</a>";
	///	  		                }},
	//	  		                { display: '受理人员', name: 'oper_no',width: 100 },
	//	  		               /*  { display: '接待人员', name: 'City' },
	//	  		                { display: '受理人员', name: 'City' }, */
	//	  		                { display: '受理时间', name: 'oper_date',width: 100 },
	//	  		               /*  { display: '宽带竣工时间', name: 'City' }, */
	//	  		                { display: '营业厅名称', name: 'dept_no', width: 300 }
	//	  		                ], 
	//	  		                delayLoad:true,
	//	  		                url: application.fullPath + "noauthority/rptbusilist/QueryRptBusiList",
	//	  		                height:300,pageSize:30 ,rownumbers:false
	//	            });
	//	};
		//点击终端清单,处理函数
		audit.terminalList = function(value){
			audit.rep_list_manager[value]  = 
		            $("#rep_"+value).ligerGrid({
		                columns: [
		  		                { display: 'cs_order_id', name: 'cs_order_id', width: 100},
		  		                { display: 'ofr_id', name: 'ofr_id',  width: 150}, 
		  		                { display: 'customer_no', name: 'customer_no', width: 100,render:function(row){
									var customer_no = row.customer_no,temp_name = row.customer_no;                
			  		            	if(temp_name&&temp_name.length>4){
			  		            	temp_name = temp_name.substr(0,3)+"....";
			  		            	}
			  		            	return "<a href='javascript:void(0);' title='"+customer_no+"'>"+temp_name+"</a>";
		  		            	}},
		  		                { display: 'customer_name', name: 'customer_name',  width: 130 },
		  		                { display: 'id_type', name: 'id_type', width: 100},
		  		                { display: 'customer_id', name: 'customer_id',width: 100 },
		  		                { display: 'id_addr', name: 'id_addr', width: 150 },
		  		                { display: 'link_name', name: 'link_name', width: 150},
		  		                { display: 'link_phone1', name: 'link_phone1', minWidth: 120 },
		  		                { display: 'address_id', name: 'address_id', width: 100 },
		  		                { display: 'address_str', name: 'address_str', width: 100 },
		  		                { display: 'letv_flag', name: 'letv_flag', width: 300 },
		  		                { display: 'iptv_flag', name: 'iptv_flag', width: 120 } 
		  		                
		  		                ], 
		  		                delayLoad:true,
		  		                url: application.fullPath + "noauthority/ReportCs/queryOrderCs",
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
			 params.dept_no  = $("#select_urbon").val();
			 params.oper_dept_no  = audit.getJsonVal("select_channel","dept_no");*/
			 params.oper_no  = audit.getJsonVal("select_people","oper_no"); 
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
</script>
</head>
<body>
	<div class="show">
    <div class="show_title_bg">
        <div class="show_title"><!--<span class="crumb">当前位置：账号选择</span>-->稽核报表<!--<span class="red">选择完一项后，才能选择下面一项的内容，否则下面全部置灰无法选择；仅可以做单选。</span>-->
        </div>
    </div>
    
    <div class="box box_long" id=""auditCs_report_type_box>
    	<table>
    		<tr>
    		<td>
    		<div class="box box_margin_right" id="audit_report_type_box">
		        <div class="show_big_title"><strong>.</strong>请选择报表的类别：</div>
		        <div class="table_box">
		          <table width="446" border="0" align="center" cellpadding="0" cellspacing="0">
		            <tr>
		              <td width="24" height="22" align="center"><input type="radio" name="audit_report_type" value="operating_receipt" id="checkbox" checked="checked"/></td>
		              <td width="124">orderCs</td>
		              <!-- <td width="24" align="center"><input type="radio" name="audit_report_type" value="income_list" id="checkbox3" /></td>
		              <td width="124">收入清单</td>
		              <td width="25" align="center"><input type="radio" name="audit_report_type" value="business_list" id="checkbox5" /></td>
		              <td width="125">业务清单</td> -->
		            </tr>
		            <tr>
		              <td height="22" align="center"><input type="radio" name="audit_report_type" value="terminal_list" vaid="checkbox2" /></td>
		              <td>logPayCs</td>
		              <td align="center">&nbsp;</td>
		              <td>&nbsp;</td>
		              <td align="center">&nbsp;</td>
		              <td>&nbsp;</td>
		            </tr>
		          </table>
		        </div>
		        
		    </div>
		    </td>
    		<td>
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
			  </td>
    		</tr>
    	</table>
    
    </div>
    
	
	
 <!--  <div class="box" id="tele_type_box">
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
  
 <%--  <div class="box box_margin_right" id="select_rang_box">
        <div class="show_big_title"><strong>.</strong>请选择需查询的范围：</div>
        <div class="table_box_none">
          <table width="486" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td width="114" height="22" align="right"><strong>地  市：</strong></td>
              <td width="372" align="center"><select  ltype="init" name="select_city" id="select_city" url="<%=fullPath%>authority/webUtil/queryLocalNet" class="sel">
                <option ltype="default" value="">请选择地市</option>
              </select></td>
            </tr>
            <tr>
              <td height="22" align="right"><strong>城  区：</strong></td>
              <td align="center"><select name="select_urbon" id="select_urbon" url="<%=fullPath%>authority/webUtil/queryDeptByLocalNet"  class="sel">
                <option ltype="default" value="">请选择城区</option>
              </select></td>
            </tr>
			<tr>
              <td width="114"  height="22" align="right"><strong>营业厅：</strong></td>
              <td width="372" align="center"><div id="select_channel_delete" class="input_click_delete"></div>
              <a href="javascript:void(0);"><input type="text" name="select3" id="select_channel"  class="input_click"  readonly="readonly"/>
              </a></td>
            </tr>
            <tr>
              <td width="114"  height="22" align="right"><strong>接待/受理人员：</strong></td>
              <td width="372" align="center"><div id="select_people_delete"  class="input_click_delete"></div>
              <a href="javascript:void(0);"><input type="text" name="select3" id="select_people"  class="input_click"  readonly="readonly"/>
              </a></td>
            </tr>
            <tr id="select_number_tr">
              <td width="114"  height="22" align="right"><strong>号码选择：</strong></td>
              <td width="372" align="center"><div id="select_number_delete" class="input_click_delete"></div>
              <a href="javascript:void(0);"><input type="text" name="select3" id="select_number"  class="input_click" />
              </a></td>
            </tr>
          </table>
        </div>
        
    </div>  --%>
 
<!--   <div class="box" id="pay_type_box"> -->
<!--     <div class="show_big_title"><strong>.</strong>请决定你需要查看的支付方式:</div> -->
<!--         <div class="table_box_none"> -->
<!--           <table width="486" border="0" align="center" cellpadding="0" cellspacing="0"> -->
<!--             <tr> -->
<!--               <td width="114" height="22" align="right"><strong>支付方式：</strong></td> -->
<!--               <td width="372" align="center"><select name="select5" id="select_pay_type" class="sel"> -->
<!--                 <option value="">全部</option> -->
<!--               </select></td> -->
<!--             </tr> -->
<!--           </table> -->
<!--         </div> -->
<!--   </div>  -->
 

  
  <div class="dashed">
  	<a id="reset" href="javascript:void(0);">重 置</a> <a id="confirm" href="javascript:void(0);">确 定</a>
  </div>
  
  <div class="box box_long" style="padding:0 0 10px 0;height:360px;">
	    <div id="rep_report_list" class="show_big_title"><strong>.</strong>报表结果展示：</div>  
	     
	   
  </div>
  <div class="clear"></div>
  </div>

  <div id="divCoverIframe" class="cover" style="display:none;"><iframe id="coverIframe" name="left"  frameborder="0" height="100%" scrolling="no" width="100%" style="Z-INDEX: 1;WIDTH:100%; HEIGHT:100%;OVERFLOW: visible" src="<%=fullPath%>authority/reportForms/coverIframe"></iframe></div>
  <!-- 选择营业厅开始 -->
  <!-- <div id="select_channel_dialog" class="show-select" style="display:none;">
	<div class="icon-close"></div>
	<div class="select-title">查询条件：</div>
	<div class="search-input">
		<div class="leble">营业厅代码：</div>
		<div class="input"><input class="branch-code" type="text" value=""></div>
		<div class="leble">营业厅名称：</div>
		<div class="input"><input class="branch-name" type="text" value=""></div>
		<a class="js_query" href="javascript:void(0);">查 询</a>
		<a class="js_reset" href="javascript:void(0);">重 置</a>	</div>
	<div id="select_channel_table" class="select-table"></div>	
  </div> -->
    <!-- 选择员工开始 -->
  <!-- <div id="select_staff_dialog" class="show-select" style="display:none;">
	<div class="icon-close"></div>
	<div class="select-title">查询条件：</div>
	<div class="search-input">
		<div class="leble">员工代码：</div>
		<div class="input"><input class="branch-code" type="text" value=""></div>
		<div class="leble">员工名称：</div>
		<div class="input"><input class="branch-name" type="text" value=""></div>
		<a class="js_query" href="javascript:void(0);">查 询</a>
		<a class="js_reset" href="javascript:void(0);">重 置</a>	</div>
	<div id="select_staff_table" class="select-table"></div>	
  </div> -->
  <div class="copyright">Copyright © 2014 China unicom All Right Reserved</div>
		
</body>
</html>