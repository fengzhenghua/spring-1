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
<link href="<%=fullPath%>css/checkChargeList.css" rel="stylesheet" type="text/css" />
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
				rep_list_array:["operating_receipt","terminal_list","business_list"],
				rep_list_manager:{"operating_receipt":null,"terminal_list":null,"business_list":null},
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
			$("#select_channel").click(function(){
				audit.dialog.showDialog("select_channel_dialog");
				audit.dialog.initChannelDialog();
			});
			
			$("#select_people").click(function(){
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
		                            { display: '云销售流水号', name: 'pay_id', width: 100 },
		                            { display: '账户余额', name: 'balance', width: 120 },
			  		                { display: '创建时间', name: 'create_date', width: 200 },
			  		                { display: '流水号', name: 'service_sn', width: 200 },
			  		                { display: '支付宝交易号', name: 'alipay_order_no', width: 200 },
			  		                { display: '交易对方EMAIL', name: 'owner_logon_id', width: 200 },
			  		                { display: '交易对方名称', name: 'owner_name', width: 200 },
			  		                { display: '用户编号', name: 'owner_user_id', width: 200 },
			  		                { display: '收入（元）', name: 'in_amount', width: 200 },
			  		                { display: '支出（元）', name: 'out_amount', width: 200 },
			  		                { display: '交易场所', name: 'order_from', width: 200 },
			  		                { display: '商品名称', name: 'order_title', width: 200 },
			  		                { display: '类型', name: 'memo', width: 200 },
			  		                { display: '说明', name: 'message', width: 200 },
			  		                { display: '账期', name: 'account_date', width: 200 },
			  		                { display: '对账时间', name: 'oper_date', width: 200 }
			  		               
		  		                ],
		  		                delayLoad:true,
		  		                url: application.fullPath + "noauthority/ReportCs/queryAlipayRecord",
		  		                height:300,pageSize:30 ,rownumbers:false
		            });
		};
		
		//点击终端清单,处理函数
		audit.terminalList = function(value){
			audit.rep_list_manager[value]  = 
		            $("#rep_"+value).ligerGrid({
		                columns: [	                
		  		             	    { display: '沃受理系统流水号', name: 'pay_id', width: 100 },
		                            { display: '订单号', name: 'cs_order_id', width: 120 },
			  		                { display: '支付宝流水', name: 'oper_sn', width: 200 },
			  		                { display: '代理商缴费金额', name: 'payed_fee', width: 200 },
			  		                { display: '提现金额', name: 'cash_fee', width: 200 },
			  		                { display: '支付宝总金额', name: 'total_zfb_fee', width: 200 },
			  		                { display: 'BSS总金额', name: 'total_bss_fee', width: 200 },
			  		                { display: '支付宝数量', name: 'count_zfb_group', width: 200 },
			  		                { display: 'BSS数量', name: 'count_bss_group', width: 200 },
			  		                { display: '标识', name: 'flag', width: 200 },
			  		                { display: '操作时间', name: 'create_date', width: 200 },
			  		                { display: '对账标识', name: 'cmp_flag', width: 200 },
			  		                { display: '对账描述', name: 'cmp_memo', width: 200 },
			  		                { display: '对账时间', name: 'charge_date', width: 200 }
		  		                
		  		                ], 
		  		                delayLoad:true,
		  		                url: application.fullPath + "noauthority/ReportCs/queryChargeCheck",
		  		                height:300,pageSize:30 ,rownumbers:false
		            });
		};
		//点击终端清单,处理函数
		audit.businessList = function(value){
			audit.rep_list_manager[value]  = 
		            $("#rep_"+value).ligerGrid({
		                columns: [	                
			  		                { display: '支付宝金额', name: 'balance', width: 160 },
			  		                { display: '工号支付金额', name: 'app_pay_fee', width: 160 },
			  		                { display: 'DIC支付金额', name: 'agent_pay_fee', width: 160 },
			  		                { display: 'BSS费用', name: 'bss_charge', width: 160 },
			  		                { display: '资金归集金额', name: 'contract_charge', width: 160 },
			  		                { display: '操作时间', name: 'oper_date', width: 160 },
			  		                { display: '账务时间', name: 'account_date', width: 160 }
		  		                
		  		                ], 
		  		                delayLoad:true,
		  		                url: application.fullPath + "noauthority/ReportCs/queryMoneyFlowsCheck",
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
                var colNames = "pay_id,balance,create_date,service_sn,alipay_order_no,owner_logon_id,owner_name,owner_user_id,in_amount,out_amount,order_from,order_title,memo,message,oper_date,account_date";
                post(application.fullPath + "noauthority/ReportCs/queryAlipayRecordExcel",colNames);
            }
            else if (audit_report_type == "terminal_list"){
            	var colNames = "pay_id,cs_order_id,oper_sn,payed_fee,cash_fee,total_zfb_fee,total_bss_fee,count_zfb_group,count_bss_group,flag,create_date,cmp_flag,cmp_memo,charge_date";
                post(application.fullPath + "noauthority/ReportCs/queryChargeCheckExcel",colNames);
            }
            else if (audit_report_type == "business_list"){
            	var colNames = "balance,app_pay_fee,agent_pay_fee,bss_charge,contract_charge,oper_date,account_date";
                post(application.fullPath + "noauthority/ReportCs/queryMoneyFlowsCheckExcel",colNames);
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
        <div class="show_title"><!--<span class="crumb">当前位置：账号选择</span>-->对账报表<!--<span class="red">选择完一项后，才能选择下面一项的内容，否则下面全部置灰无法选择；仅可以做单选。</span>-->
        </div>
    </div>
	<div class="box box_margin_right" id="audit_report_type_box">
        <div class="show_big_title"><strong>.</strong>请选择报表的类别：</div>
        <div class="table_box">
          <table width="446" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td width="24" height="22" align="center"><input type="radio" name="audit_report_type" value="operating_receipt" id="checkbox" checked="checked"/></td>
              <td width="124">支付宝清单报表</td>
     <!--  </tr>
           <tr> -->
              <td width="24" height="22" align="center"><input type="radio" name="audit_report_type" value="terminal_list" id="checkbox2" /></td>
              <td width="124">对账结果报表</td>  
              <td width="25" align="center"><input type="radio" name="audit_report_type" value="business_list" id="checkbox5" /></td>
              <td width="125">资金流比对清单</td>   
            </tr>
          </table>
        </div>
        
    </div>


		<!-- <div class="box" id="select_rang_box">
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
    </div> -->
 
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
  	<a id="reset" href="javascript:void(0);">重 置</a> <a id="confirm" href="javascript:void(0);">确 定</a>  <a id="export" href="javascript:void(0);">导 出</a>
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
 </div>		
</body>
</html>