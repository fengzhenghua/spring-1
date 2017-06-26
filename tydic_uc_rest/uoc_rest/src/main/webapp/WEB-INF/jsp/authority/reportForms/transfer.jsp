<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
<%@ page import="java.util.Date" %>
<%
	String nowDate = DateUtil.getCurrentDate(); 
	String beforeDate = DateUtil.getAfterMonth(new Date(), -1);
	String load_oper_no=(String)request.getAttribute("load_oper_no");
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>统一销售服务系统,稽核报表</title>
<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/commCheck.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>js/jquery-ligerui-2.25/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />

<script src="<%=basePath%>js/jquery-ligerui-2.25/js/core/base.js" type="text/javascript"></script> 
<script src="<%=basePath%>js/jquery-ligerui-2.25/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="<%=basePath%>js/jquery-ligerui-2.25/js/plugins/ligerDialog.js" type="text/javascript"></script>
<script src="<%=fullPath%>js/json2.js" type="text/javascript"></script> 
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
				//var select = $("#select_urbon");
				//var url = select.attr("url");
				//var value = $(this).val();
				//清空操作
				//audit.empty.emptySelect("select_urbon");
				//audit.empty.emptyInput("select_channel");
				//audit.empty.emptyInput("select_people");
				
				//url += "?local_net="+value;
				//audit.clearGrid(audit.dialog.manager.channel);
				//audit.clearGrid(audit.dialog.manager.staff);
				//audit.createSelect(select,url);
			});
			//选择城区改变
			$("#select_urbon").change(function(){
				//清空操作
				//audit.empty.emptyInput("select_channel");
				//audit.empty.emptyInput("select_people");	
				//audit.clearGrid(audit.dialog.manager.channel);
				//audit.clearGrid(audit.dialog.manager.staff);
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
		
		 function f_onCheckAllRow(checked)
	        {
	            for (var rowid in this.records)
	            {
	                if(checked)
	                    addCheckedCustomer(this.records[rowid]['get_cash_cs_id']);
	                else
	                    removeCheckedCustomer(this.records[rowid]['get_cash_cs_id']);
	            }
	        }
	 
	        /*
	        该例子实现 表单分页多选
	        即利用onCheckRow将选中的行记忆下来，并利用isChecked将记忆下来的行初始化选中
	        */
	        var checkedCustomer = [];
	        function findCheckedCustomer(get_cash_cs_id)
	        {
	            for(var i =0;i<checkedCustomer.length;i++)
	            {
	                if(checkedCustomer[i] == get_cash_cs_id) return i;
	            }
	            return -1;
	        }
	        function addCheckedCustomer(get_cash_cs_id)
	        {
	            if(findCheckedCustomer(get_cash_cs_id) == -1)
	                checkedCustomer.push(get_cash_cs_id);
	        }
	        function removeCheckedCustomer(get_cash_cs_id)
	        {
	            var i = findCheckedCustomer(get_cash_cs_id);
	            if(i==-1) return;
	            checkedCustomer.splice(i,1);
	        }
	        function f_isChecked(rowdata)
	        {
	            if (findCheckedCustomer(rowdata.get_cash_cs_id) == -1)
	                return false;
	            return true;
	        }
	        function f_onCheckRow(checked, data)
	        {
	            if (checked) addCheckedCustomer(data.get_cash_cs_id);
	            else removeCheckedCustomer(data.get_cash_cs_id);
	        }
	        function f_getChecked()
	        {


	           // alert(checkedCustomer.join(','));
	            var data=checkedCustomer.join(',');
	            if(data==null||data==""){
	            	alert('至少选着一行数据!');
	            	return false;
	            }
	            var dataParam={"get_cash_cs_id_list":data,"oper_id":"<%=load_oper_no%>;"};
		           /*  for (var i = 0, l = grid.rows.length; i < l; i++) {  
		          
		                if (grid.rows[i].Permit) {  
		                    data += grid.rows[i].comm_sn + "&";  
		                }  
		          
		            }   */
		          	//alert(data); 
		            $.ligerDialog.waitting('正在保存中,请稍候...'); 

		            $.ajax({  
		                loading: '正在加载中...',  
		                type: "POST",
			            dataType: "json",
		                url: application.fullPath + "csrest/pay/alipay_batch_trans",  
		                data: dataParam,  
		                success: function (msg,status) {  
		                	//alert(msg.args.alipay_html);
		                	//alert(msg.type);
		                	var respCode = msg.type;
		                	var content=msg.content;
							if(respCode == 'success'){
								$("body").append(msg.args.alipay_html);
								
							}else{
								if(content.length>0){
									$.ligerDialog.closeWaitting();
									$.alert(content);
								}else{
									$.ligerDialog.closeWaitting();
									$.alert("查询失败");
								}
							}		                	
		                   // $.showSuccess("保存成功!");  
		                },  
  
		                error: function (msg,status) {  
		                	var respCode = msg.type;
		                	var content=msg.content;
								if(content.length>0){
									$.ligerDialog.closeWaitting();
									$.alert(content);
								}else{
									$.ligerDialog.closeWaitting();
									$.alert("查询成功");
								}
		                }  
		            }); 
	           
	    	}
	        
	       /*  function f_open()
	        {
	            $.ligerDialog.open({
	                height:600,
	                width: 800,
	                title : '打开窗口提示',
	                url: 'dialogContent.htm', 
	                showMax: false,
	                showToggle: true,
	                showMin: false,
	                isResize: true,
	                slide: false,
	                data: {
	                    name: $("#txtValue").val()
	                },
	                //自定义参数
	                myDataName: $("#txtValue").val()
	            });
	        }  */


		/**
		*注册click事件
		*/
		audit.initClick = function(){
			$("input[name='audit_report_type']").click(audit.clickReportType);
			$("#reset").click(audit.reset);
			$("#confirm").click(audit.confirm);
			$("#tixian").click(audit.f_getChecked);
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
			$("#select_number_tr").show();
		};
		audit.terminalListCss = function(){
			$("#select_people_tr").show();		};
		//点击营业收入,处理函数
		audit.operatingReceipt = function(value){	
			 audit.rep_list_manager[value] = 
		            $("#rep_"+value).ligerGrid({
		                columns: [
									{ display: '提现流水号', name: 'get_cash_cs_id', width: 100 },
									{ display: '提现描述', name: 'cash_memo', width: 120 },
									{ display: '提现金额', name: 'cash_fee', width: 120 },
									{ display: '提现类型', name: 'get_cash_type', width: 120 },
									{ display: '转账状态', name: 'trans_status', width: 120 },
								    { display: '操作工号', name: 'oper_id', width: 180 },
								    { display: '创建时间', name: 'create_date', width: 150 },
								    { display: '转账时间', name: 'cash_date', width: 100 }
									], 
		  		                checkbox:true,
		  		                delayLoad:true,
		  		                url: application.fullPath + "authority/transfer/transfer",
		  		                height:300,pageSize:30 ,rownumbers:false,
		  		                isChecked: f_isChecked, onCheckRow: f_onCheckRow, onCheckAllRow: f_onCheckAllRow
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
		  		             	{ display: '提现流水号', name: 'get_cash_cs_id', width: 100 },
	                            { display: '提现描述', name: 'cash_memo', width: 120 },
	                            { display: '提现金额', name: 'cash_fee', width: 120 },
	                            { display: '提现类型', name: 'get_cash_type', width: 120 },
	                            { display: '转账状态', name: 'trans_status', width: 120 },
		  		                { display: '操作工号', name: 'oper_id', width: 180 },
		  		                { display: '创建时间', name: 'create_date', width: 150 },
		  		                { display: '转账时间', name: 'cash_date', width: 100 }
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
			 /* params.audit_report_type = $("input[name='audit_report_type']:checked").val();
			 params.tele_type = $("input[name='tele_type']:checked").val();
			 params.local_net  = $("#select_city").val();
			 params.dept_no  = $("#select_urbon").val();*/
			 params.oper_id  = audit.getJsonVal("select_channel","oper_id");
			 params.begin_date = audit.formatDate($("#date_begin").val());
			 params.end_date= audit.formatDate($("#date_end").val());
			 params.get_cash_status = $("#select_city").val();		 
			 params.get_cash_flag = $("#select_urbon").val();
			 
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
			// $("input[name='audit_report_type']:checked").attr("checked",false);
			// $("input[name='audit_report_type']:first").get(0).checked = true;
			// $("input[name='tele_type']:checked").attr("checked",false);			 
			// $("input[name='tele_type']:first").get(0).checked = true;
			 
			 //select默认选中第一个
			 $("#select_city option:first").attr('selected','selected'); 
			 $("#select_urbon option:first").attr('selected','selected'); 
			 $("#select_channel").val("");
			 
			 $("#date_begin").val(dateTime.beforeDate);
			 $("#date_end").val(dateTime.nowDate);
			 
			// $("#select_pay_type option:first").attr('selected','selected'); 
		}
		
		
</script>
</head>
<body>
	<div class="show">
    <div class="show_title_bg">
        <div class="show_title"><!--<span class="crumb">当前位置：账号选择</span>-->提现审核<!--<span class="red">选择完一项后，才能选择下面一项的内容，否则下面全部置灰无法选择；仅可以做单选。</span>-->
        </div>
    </div>
	<div  id="audit_report_type_box" style="display:none;" >
        <div class="show_big_title"><strong>.</strong>请选择报表的类别：</div>
        <div class="table_box">
          <table width="446" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td width="24" height="22" align="center"><input type="radio" name="audit_report_type" value="operating_receipt" id="checkbox" checked="checked"/></td>
              <td width="124">订单收费报表</td>
             <!--  <td width="24" align="center"><input type="radio" name="audit_report_type" value="income_list" id="checkbox3" /></td>
              <td width="124">收入清单</td>
              <td width="25" align="center"><input type="radio" name="audit_report_type" value="business_list" id="checkbox5" /></td>
              <td width="125">业务清单</td> -->
              
           <!--  </tr>
             <tr> -->
             
              <!-- <td width="24" height="22" align="center"><input type="radio" name="audit_report_type" value="terminal_list" id="checkbox2" /></td>
              <td width="124">订单订购报表</td>   -->
			  <!-- <td width="24" height="22" align="center"><input type="radio" name="audit_report_type" value="mobile_number_list" id="checkbox4" /></td>
              <td width="124">靓号清单</td>			  
			  <td width="25" height="22" align="center"><input type="radio" name="audit_report_type" value="sampling_audit_list" id="checkbox6" /></td>
              <td width="125">抽样稽核</td>      -->         
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
           <!-- <tr>
              <td width="114" height="22" align="right"><strong>提现状态：</strong></td>
              <td width="372" align="center">
              <select  ltype="init" name="select_city" id="select_city" class="sel">
	                <option value="0">未提现</option>
	                <option value="1">已提现</option>
	                <option value="">全部</option>
              </select></td>
            </tr>
            <tr>
              <td height="22" align="right"><strong>是否可以提现：</strong></td>
              <td align="center">
              <select name="select_urbon" id="select_urbon" class="sel">
              		<option value="1">可以</option>
	                <option value="0">否</option>
	                <option value="">全部</option>
	                
              </select>
              </td>
            </tr> -->
			<tr>
              <td   height="22" align="right"><strong>工号：</strong></td>
              <td   align="left"><div id="select_channel_delete"  class="input_click_delete"></div>
	              <a href="javascript:void(0);"><input type="text" name="select3" id="select_channel"  class="input_click"  readonly="readonly"/>
	              </a>
              </td> 
             <!--  <td width="10%" ></td>
              <td width="40%"  align="right">
              	<div class="dashed">
				  	<a id="reset" href="javascript:void(0);">重 置</a> 
				  	<a id="confirm" href="javascript:void(0);">查 询</a>
				  	<a id="commission" href="javascript:void(0);">提 现</a>
				  </div>
              </td> -->
            </tr>
           <!--  <tr id="select_people_tr">
              <td width="114"  height="22" align="right"><strong>区域：</strong></td>
              <td width="372" align="center"><div id="select_people_delete"  class="input_click_delete"></div>
              <a href="javascript:void(0);"><input type="text" name="select3" id="select_people"  class="input_click"  readonly="readonly"/>
              </a></td>
            </tr> -->
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
        <div class="show_big_title"><strong>.</strong>提现申请时间：</div>
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
  	<a id="reset" href="javascript:void(0);">重 置</a>
  	<a id="confirm" href="javascript:void(0);">查 询</a>
  	<a id="tixian" onclick="javascript:f_getChecked();">提 现</a>
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
  <div id="jiazaizhong" style="display: none" class="center">
	<div style="padding-left:45%;width:200px;">
		<table>
			<tr>
				<td valign="middle">
					<img  src="images/loading.gif"/>
				</td>
				<td valign="middle">页面加载中...
				</td>
			</tr>
		</table>
	</div>
</div>	
</body>
</html>