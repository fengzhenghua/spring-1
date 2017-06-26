<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../common/common.jsp" %>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.tydic.unicom.service.cache.service.interfaces.MemQueryServ" %>
<%@ page import="com.tydic.unicom.service.cache.po.CodeType" %>
<%@ page import="com.tydic.unicom.webUtil.SpringBean" %>
<%@ page import="com.tydic.unicom.service.cache.po.CodeList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.tydic.unicom.crm.uss.service.pub.interfaces.CodeListServDu" %>

<%
    String nowDate = DateUtil.getCurrentDate();
    String beforeDate = DateUtil.getAfterMonth(new Date(), -1);
    //用于动态获取serv_code 每三个一行显示，目前还没有通用需求，且原来的样式每项宽度一样
    MemQueryServ memQueryServ = SpringBean.getBean("MemQueryServ", MemQueryServ.class);
    CodeListServDu codeListServDu = SpringBean.getBean("CodeListServDu", CodeListServDu.class);
    CodeType code = memQueryServ.queryCodeType("code_type_rpt_business_list_serv_code");
    List<CodeList> serv_code_list = new ArrayList<CodeList>();
    List<ArrayList<CodeList>> serv_code_line_list = new ArrayList<ArrayList<CodeList>>();
    String province = codeListServDu.getCodeListByTypeCode("province_code");
    if (code != null) {
        serv_code_list = code.getCodeList();
        int len = serv_code_list.size();
        for (int i = 0; i < len; i++) {
            CodeList serv_code;
            ArrayList<CodeList> line = new ArrayList<CodeList>();
            serv_code = serv_code_list.get(i);
            line.add(serv_code);
            if (i + 1 < len) {
                i = i + 1;
                serv_code = serv_code_list.get(i);
                line.add(serv_code);
            } else {
                serv_code_line_list.add(line);
                break;
            }
            if (i + 1 < len) {
                i = i + 1;
                serv_code = serv_code_list.get(i);
                line.add(serv_code);
            } else {
                serv_code_line_list.add(line);
                break;
            }
            serv_code_line_list.add(line);
        }
    }
    request.setAttribute("serv_code_line_list", serv_code_line_list);
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>统一销售服务系统,稽核报表</title>
    <link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css"/>
    <link href="<%=fullPath%>css/audit.css" rel="stylesheet" type="text/css"/>
    <link href="<%=basePath%>js/jquery-ligerui-2.25/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css"/>
    <script src="<%=basePath%>js/jquery-ligerui-2.25/js/core/base.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/jquery-ligerui-2.25/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="<%=fullPath%>js/json2.js" type="text/javascript"></script>
    <script type="text/javascript">
        var dateTime = {
            beforeDate: "<%=beforeDate%>",
            nowDate: "<%=nowDate%>"
        };
        var province ="<%=province%>";
    </script>
    <script type="text/javascript">
        var audit = {
            rep_list_array: ["operating_receipt", "income_list", "business_list", "terminal_list", "mobile_number_list", "sampling_audit_list","business_statistics_list","auto_terminal_list"],
            rep_list_manager: {
                "operating_receipt": null,
                "income_list": null,
                "business_list": null,
                "terminal_list": null,
                "mobile_number_list": null,
                "sampling_audit_list": null,
                "business_statistics_list":null,
                "auto_terminal_list":null
            },
            cur_report_name: "",//当前报表类型
            report_manager: {}
        };
        $(document).ready(function () {
            audit.initRepListArray('540px','560px');
            //初始化需查询的范围
            audit.initQueryRange();
            audit.initReport();
            audit.initClick();
       		$("#result_place").hide();
        });


        /*
         *	初始化select
         */
        audit.initQueryRange = function () {
            var selectList = $("select[ltype='init']");
            for (var i = 0; i < selectList.length; i++) {
                var select = $(selectList[i]);
                var url = select.attr("url");
                this.createSelect(select, url);
            }
            //选择地市改变
            $("#select_city").change(function () {
                var select = $("#select_urbon");
                var url = select.attr("url");
                var value = $(this).val();
                //清空操作
                audit.empty.emptySelect("select_urbon");
                audit.empty.emptyInput("select_channel");
                audit.empty.emptyInput("select_people");
                url += "?local_net=" + value;
                audit.clearGrid(audit.dialog.manager.channel);
                audit.clearGrid(audit.dialog.manager.staff);
                audit.createSelect(select, url);
            });
            //选择城区改变
            $("#select_urbon").change(function () {
                //清空操作
                audit.empty.emptyInput("select_channel");
                audit.empty.emptyInput("select_people");
                audit.clearGrid(audit.dialog.manager.channel);
                audit.clearGrid(audit.dialog.manager.staff);
            });
            //
            $("#select_channel").click(function () {
                var select_urbon = $("#select_urbon").val();
                if (!select_urbon) {
                    $.alert("请选择城区!");
                    return;
                }
                audit.dialog.showDialog("select_channel_dialog");
                audit.dialog.initChannelDialog();
            });
            $("#select_people").click(function () {
                var channel = $("#select_channel").val();
                if (!channel) {
                    $.alert("请选择营业厅!");
                    return;
                }
                audit.dialog.showDialog("select_staff_dialog");
                audit.dialog.initStaffDialog();
            });
            //选择是否稽核改变
            $("#select_audit_flag").change(function () {
                //获取“是否稽核下拉框”选项值
                var audit_flag = $("#select_audit_flag option:selected").val();
                //分支1 未稽核
                if (audit_flag == "0") {
                    //清空审核状态 隐藏审核状态下拉框标签
                    $("#select_audit_status_tr").hide();
                }
                //分支2 已稽核
                if (audit_flag == "1") {
                    //显示审核状态标签，并提供选项
                    $("#select_audit_status_tr").show();
                }
                //分支3 全部
                if (audit_flag == "") {
                    //清空审核状态 隐藏审核状态下拉框标签
                    $("#select_audit_status_tr").hide();
                }

            });

            //选择'是否云销售'改变
            $("#select_sys_code").change(function () {
                //获取'是否云销售'下拉框选项值
                var sys_code = $("#select_sys_code option:selected").val();
                //分支1 云销售
                if (sys_code == "YXS") {
                    //数据显示列表 显示电子协议列
                    business_list_show_order_id();
                }
                //分支2 其它
                if (sys_code == "OTHER") {
                    //数据显示列表 隐藏电子协议列
                    business_list_hide_order_id();
                }
                //分支3 全部
                if (sys_code == "ALL") {
                //数据显示列表 隐藏电子协议列
                business_list_show_order_id();
            }
            });
            //$("#select_audit_flag").trigger("change");
        };

        audit.dialog = {
            manager: {channel: null, staff: null},
            initChannelDialog: function () {
                if (this.manager.channel != null) {
                    return;
                }
                var select_channel_dialog = $("#select_channel_dialog");
                this.manager.channel = $("#select_channel_table").ligerGrid({
                    columns: [
                        {display: '营业厅编码', name: 'dept_no', align: 'left', width: 100, minWidth: 60},
                        {display: '营业厅名称', name: 'dept_name', minWidth: 120},
                        {display: '备注', name: 'comments', minWidth: 140}
                    ],
                    buttonType: "confirm",//右下角按钮类型 目前类型  confirm(确认),excel(导出excel按钮)
                    buttonEvent: function () {
                        var manager = audit.dialog.manager.channel;
                        var row = manager.getSelectedRow();
                        $("#select_channel").val(row.dept_name);
                        $("#select_channel").attr("data", JSON.stringify(row));
                        audit.clearGrid(audit.dialog.manager.staff);
                        audit.dialog.hideDialog("select_channel_dialog");
                    },
                    checkbox: true,
                    single: true,
                    delayLoad: true,
                    selectRowButtonOnly: true,
                    width: 950,
                    url: application.fullPath + "authority/webUtil/queryInfoDeptByCons",
                    height: 300, pageSize: 30, rownumbers: false
                });
                $(".icon-close", select_channel_dialog).click(function () {
                    audit.dialog.hideDialog("select_channel_dialog");
                });
                $(".js_query", select_channel_dialog).click(function () {
                    var obj = $("#select_channel_dialog");
                    var params = {};
                    params.parent_dept_no = $("#select_urbon").val();
                    params.dept_no = $(".branch-code", obj).val();
                    params.dept_name = $(".branch-name", obj).val();
                    var manager = audit.dialog.manager.channel;
                    audit.dialog.loadQuery(manager, params);
                });
                $(".js_reset", select_channel_dialog).click(function () {
                    var obj = $("#select_channel_dialog");
                    $(".branch-code", obj).val("");
                    $(".branch-name", obj).val("");
                });

            },
            initStaffDialog: function () {
                if (this.manager.staff != null) {
                    return;
                }
                var select_staff_dialog = $("#select_staff_dialog");
                this.manager.staff = $("#select_staff_table").ligerGrid({
                    columns: [
                        {display: '员工工号', name: 'oper_no', align: 'left', width: 100, minWidth: 60},
                        {display: '员工姓名', name: 'oper_name', minWidth: 120}
                    ],
                    buttonType: "confirm",//右下角按钮类型 目前类型  confirm(确认),excel(导出excel按钮)
                    buttonEvent: function () {
                        var manager = audit.dialog.manager.staff;
                        var row = manager.getSelectedRow();
                        $("#select_people").val(row.oper_name);
                        $("#select_people").attr("data", JSON.stringify(row));
                        audit.dialog.hideDialog("select_staff_dialog");
                    },
                    delayLoad: true,
                    checkbox: true,
                    single: true,
                    selectRowButtonOnly: true,
                    width: 950,
                    url: application.fullPath + "authority/webUtil/queryInfoOperByDeptOperNo",
                    height: 300, pageSize: 30, rownumbers: false
                });
                //员工信息关闭
                $(".icon-close", select_staff_dialog).click(function () {
                    audit.dialog.hideDialog("select_staff_dialog");
                });
                //员工信息查询
                $(".js_query", select_staff_dialog).click(function () {
                    var obj = $("#select_staff_dialog");
                    var params = {};
                    params.dept_no = audit.getJsonVal("select_channel", "dept_no");
                    params.oper_no = $(".branch-code", obj).val();
                    params.oper_name = $(".branch-name", obj).val();
                    var manager = audit.dialog.manager.staff;
                    audit.dialog.loadQuery(manager, params);
                });
                //员工信息重置
                $(".js_reset", select_staff_dialog).click(function () {
                    var obj = $("#select_staff_dialog");
                    $(".branch-code", obj).val("");
                    $(".branch-name", obj).val("");
                });
            },
            showDialog: function (id) {
                $("#divCoverIframe").show();
                $("#" + id).show();
                $("#coverIframe").contents().find("#cover").click(function () {
                    audit.dialog.hideDialog(id);
                });

            },
            initAuditDialog: function () {
                //稽核关闭绑定事件
                if (this.manager.staff != null) {
                    return;
                }
                var sampling_audit_dialog = $("#sampling_audit_dialog");
                $(".icon-close", sampling_audit_dialog).click(function () {
                    audit.dialog.hideDialog("sampling_audit_dialog");
                });
            },
            hideDialog: function (id) {
                $("#divCoverIframe").hide();
                $("#" + id).hide();
            },
            loadQuery: function (manager, params) {
                if (!manager) {
                    return;
                }
                if (params) {
                    for (var key in params) {
                        if (params[key] != "" && params[key] != undefined) {
                            //ligerU设置后台传递参数
                            manager.setParm(key, params[key]);
                        } else {
                            manager.removeParm(key);
                        }
                    }
                }
                manager.loadData(true);
            }
        };

        audit.empty = {
            emptySelect: function (selectId) {
                $("option[ltype != 'default']", $("#" + selectId)).remove();
            },
            emptyInput: function (inputId) {
                var input = $("#" + inputId);
                input.val("");
                input.attr("data", "");
            }
        };
        /*
         *生成下拉框
         */
        audit.createSelect = function (select, url) {
		$.ajax({url:url,
			async: true,
                type: "POST",
                dataType: "json",
                success: function (dataLists, textStatus, jqXHR) {
                    for (var j = 0; j < dataLists.length; j++) {
                        var data = dataLists[j];
                        var code_value = data.code_value;
                        var code_name = data.code_name;
                        var option = "<option value='" + code_value + "'>" + code_name + "</option>";
                        select.append(option);
                        if (j == 0 && code_value != "") {
                            select.trigger("change");
                        }
                    }

                },
                error: function (XMLHttpRequest, textStatus) {
                }
            });
        };
        audit.initRepListArray = function (height1,height2) {
            for (var i = 0; i < this.rep_list_array.length; i++) {
                var temp_id = "rep_" + this.rep_list_array[i]
                $("#rep_report_list").append('<div id="' + temp_id + '" class="list" style="height:'+height1+';"></div>');
            }
            document.getElementById('result_list').style.height = height2;
            
        };
        audit.initReport = function () {
            var value = $("input[name='audit_report_type']:checked").val();
            audit.createReport(value);
        };
        /*
         *注册click事件
         */
        audit.initClick = function () {
            $("input[name='audit_report_type']").click(audit.clickReportType);
            $("#reset").click(audit.reset);
            $("#confirm").click(audit.confirm);
            $("#return").click(function () {
            	 $("#query_place").show();
            	 $("#result_place").hide();
            });
            $("#export").click(audit.rpt_export);
            $("#select_channel_delete").click(function () {
                audit.empty.emptyInput("select_channel");
            });
            $("#tele_type_all").click(audit.tele_type_all);
            $("#select_people_delete").click(function () {
                audit.empty.emptyInput("select_people");
            });
            $("#select_number_delete").click(function () {
                audit.empty.emptyInput("select_number");
            });
        };

        audit.clickReportType = function () {
            var value = $(this).val();
            if (value != audit.cur_report_name) {
                audit.createReport(value);
                audit.cur_report_name = value;
            }
        };

        audit.createReport = function (value) {
            if (value != audit.cur_report_name) {
                audit.cur_report_name = value;
            } else {
                return;
            }
            $("#rep_" + value).show();
            for (var i = 0; i < audit.rep_list_array.length; i++) {
                var temp_id = "rep_" + audit.rep_list_array[i]
                if (value != audit.rep_list_array[i]) {
                    $("#rep_" + audit.rep_list_array[i]).hide();
                }
            }
            var manager = audit.rep_list_manager[value];
            if (value == "operating_receipt") {//营业收入
            	$("#wo_lan").hide();
                $('#pay_type_box').show();
                audit.operatingReceiptCss();
                if (manager == null) {
                    audit.operatingReceipt(value);
                }
            } else if (value == "income_list") {//收入清单
            	$("#wo_lan").hide();
                $('#pay_type_box').show();
                audit.incomeListCss();
                if (manager == null) {
                    audit.incomeList(value);
                }
            } else if (value == "business_list") {//业务清单
            	if(province=="gx"){
            		$("#wo_lan").show();
            	}
                $('#pay_type_box').hide();
                audit.businessListCss();
                if (manager == null) {
                    audit.businessList(value);
                }
            } else if (value == "terminal_list") {//终端清单
            	$("#wo_lan").hide();
                $('#pay_type_box').hide();
                audit.terminalListCss();
                if (manager == null) {
                    audit.terminalList(value);
                }
            } else if (value == "mobile_number_list") {//靓号清单
            	$("#wo_lan").hide();
                $('#pay_type_box').hide();
                audit.mobileNumberListCss();
                if (manager == null) {
                    audit.mobileNumberList(value);
                }
            } else if (value == "sampling_audit_list") {//抽样稽核
            	$("#wo_lan").hide();
                $('#pay_type_box').hide();
                audit.samplingAuditListCss();
                if (manager == null) {
                    audit.samplingAuditList(value);
                }
            }else if (value == "business_statistics_list") {//业务统计清单
            	$("#wo_lan").hide();
                $('#pay_type_box').hide();
                audit.businessStatisticsListCss();
                if (manager == null) {
                    audit.businessStatisticsList(value);
                }
            }else if (value == "auto_terminal_list") {//自助终端报表
            	$("#wo_lan").hide();
                $('#pay_type_box').hide();
                audit.autoTerminalListCss();
                if (manager == null) {
                    audit.autoTerminalList(value);
                }
            }
        }
        audit.commmonCss = function () {
            $("#select_number_tr").hide();
            $("#select_sys_code_tr").hide();
            $("#select_audit_flag_tr").hide();
            $("#select_sample_flag_tr").hide();
            $("#select_audit_status_tr").hide();
            $("#select_chnl_run_type_tr").hide();
            $("#serv_code_box").hide();
            audit.empty.emptyInput("select_number");
            $("#tele_type_box").show();
        };
        audit.operatingReceiptCss = function () {
            this.commmonCss();
        };
        audit.incomeListCss = function () {
            this.commmonCss();
        };
        audit.businessListCss = function () {
            $("#select_number_tr").show();
            $("#select_audit_flag_tr").hide();
            $("#select_sample_flag_tr").hide();
            $("#select_audit_status_tr").hide();
            $("#select_sys_code_tr").show();
            $("#select_chnl_run_type_tr").show();
            $("#serv_code_box").show();
        };
        audit.terminalListCss = function () {
            this.commmonCss();
        };
        audit.mobileNumberListCss = function () {
            this.commmonCss();
        };
        audit.samplingAuditListCss = function () {
            $("#select_audit_flag_tr").show();
            $("#select_audit_status_tr").show();
            $("#select_sample_flag_tr").show();
            $("#select_audit_flag").trigger("change");
            $("#select_number_tr").show();
            $("#serv_code_box").hide();
            $("#select_sys_code_tr").hide();
            $("#select_chnl_run_type_tr").hide();
           // audit.empty.emptyInput("select_number");
        };
        audit.businessStatisticsListCss = function(){
        	this.commmonCss();
            $("#select_people_tr").hide();
        };
        audit.autoTerminalListCss = function(){
        	this.commmonCss();
            $("#select_people_tr").hide();
            $("#tele_type_box").hide();
        };
        //点击营业收入,处理函数
        audit.operatingReceipt = function (value) {
            audit.rep_list_manager[value] =
                    $("#rep_" + value).ligerGrid({
                        columns: [
                            {display: '电信类型', name: 'tele_type', width: 100},
                            {display: '受理日期', name: 'stat_date', width: 120},
                            {display: '业务类型', name: 'serv_code', width: 200},
                            {display: '费用类型', name: 'charge_code', width: 200},
                            {display: '支付方式', name: 'pay_type', width: 200},
                            {display: '应收金额', name: 'pay_charge', width: 120},
                            {display: '优惠金额', name: 'derate_charge', width: 120},
                            {display: '实收金额', name: 'discount_charge', width: 120},
                            {display: '地市', name: 'oper_local_net', width: 150},
                            {display: '部门', name: 'oper_dept_no', width: 300},
                            {display: '受理人员', name: 'oper_no', width: 150}
                        ],
                        delayLoad: true,
                        url: application.fullPath + "authority/rptBusiFee/QueryRptBusiFeeSum",
                        height: 500, pageSize: 30, rownumbers: false
                    });
        };
        //点击收入清单,处理函数
        audit.incomeList = function (value) {
            audit.rep_list_manager[value] =
                    $("#rep_" + value).ligerGrid({
                        columns: [
                            {display: '电信类型', name: 'tele_type', width: 100},
                            {display: '业务号码', name: 'device_number', width: 150},
                            {display: '业务类型', name: 'serv_code', width: 200},
                            {display: '费用类型', name: 'charge_code', width: 200},
                            {display: '支付方式', name: 'pay_type', width: 100},
                            {display: '应收金额', name: 'pay_charge', width: 120},
                            {display: '优惠金额', name: 'derate_charge', width: 120},
                            {display: '实收金额', name: 'discount_charge', width: 120},
                            {display: '地市', name: 'oper_local_net', width: 150},
                            {display: '部门', name: 'oper_dept_no', width: 300},
                            {display: '受理人员', name: 'oper_no', width: 150},
                            {display: '受理日期', name: 'oper_date', width: 120}
                        ],
                        delayLoad: true,
                        url: application.fullPath + "authority/rptBusiFee/QueryRptBusiFeeDetail",
                        height: 500, pageSize: 30, rownumbers: false
                    });
        };
        //点击业务清单,处理函数
        audit.businessList = function (value) {
            audit.rep_list_manager[value] =
                    $("#rep_" + value).ligerGrid({
                        columns: [
                            {display: '电信类型', name: 'tele_type', width: 100, minWidth: 60},
                            {display: '业务类型', name: 'serv_code_name', width: 250},
                            {display: '设备号码', name: 'device_number', width: 150},
                            {display: '用户姓名', name: 'customer_name', width: 100},
							{ display: '资费', name: 'product_name', width: 300,render:function(row){
                                var product_name = row.product_name, temp_name = row.product_name;
                                if (temp_name && temp_name.length > 18) {
                                    temp_name = temp_name.substr(0, 17) + "....";
                                }
                                return "<a href='javascript:void(0);' title='" + product_name + "'>" + temp_name + "</a>";
                            }
                            },
                            {display: '受理人员', name: 'oper_no', width: 300},
                            /*  { display: '接待人员', name: 'City' },
                             { display: '受理人员', name: 'City' }, */
                            {display: '受理时间', name: 'oper_date', width: 100},
                            /*  { display: '宽带竣工时间', name: 'City' }, */
                            {display: '营业厅名称', name: 'dept_no', width: 300},
                            {display: '渠道编码', name: 'chnl_code', width: 300},
                            {display: '可选活动', name: 'activity_name', width: 300},
							{ display: '电子协议查询', isAllowHide:'true', name: 'order_id', id:'order_id', width: 300 ,render: function(rowdata, rowindex, value){	
								var rucan="";
								if(rowdata.order_id){
								 rucan="<a style='text-decoration:underline' href=\"javascript:showpdf("+"'"+rowdata.order_id+"'"+",'"+1+"'"+");\">开户协议</a>&nbsp"+    //增加参数判断协议类型 1为开户协议2为靓号协议3为补充协议
										  "<a style='text-decoration:underline' href=\"javascript:showpdf("+"'"+rowdata.order_id+"'"+",'"+2+"'"+");\">靓号协议</a>&nbsp"+
										  "<a style='text-decoration:underline' href=\"javascript:showpdf("+"'"+rowdata.order_id+"'"+",'"+3+"'"+");\">补充协议</a>&nbsp";
								}
                                    return rucan;
							}},
                            {display: '是否云销售', name: 'is_cloudSale', width: 100,render: function(row){
                            	if(row.order_id){
                            		return "是";
                            	}else{
                            		return "";
                            	}           
                            }}
                        ],
							onSelectRow: function ()
							{

                            var item = this.getSelectedRow();
                            sampling_audit_order_id = item.order_id;

                        },
                        onUnSelectRow: function (data, rowindex, rowobj) {
                            sampling_audit_order_id = data.order_id;
                        },
                        allowUnSelectRow: true,
                        delayLoad: true,
                        url: application.fullPath + "authority/rptbusilist/QueryRptBusiList",
                        height: 500, pageSize: 30, rownumbers: false
                    });
        };
        function business_list_hide_order_id() {
            audit.rep_list_manager['business_list'].toggleCol('order_id', false);
        }
        function business_list_show_order_id() {
            audit.rep_list_manager['business_list'].toggleCol('order_id', true);
        }
        //点击终端清单,处理函数
        audit.terminalList = function (value) {
            audit.rep_list_manager[value] =
                    $("#rep_" + value).ligerGrid({
                        columns: [
                            {display: '终端品牌', name: 'terminal_brand_desc', width: 100},
                            {display: '终端型号', name: 'property_name', width: 150},
                            {display: '终端串号', name: 'terminal_id', width: 150},
                            {display: '终端状态', name: 'terminal_state', width: 130},
                            {display: '电信类型', name: 'tele_type', width: 100},
                            {display: '业务类型', name: 'order_sub_type', width: 100},
                            {display: '业务号码', name: 'device_number', width: 150},
                            {display: '用户姓名', name: 'cust_name', width: 150},
                            // { display: '参加活动', name: 'CompanyName', minWidth: 120 },
                            {display: '合约价', name: 'contract_price', width: 100},
                            {display: '成本价', name: 'sale_price', width: 100},
                            {display: '营业厅名称', name: 'dept_no', width: 300},
                            {display: '受理时间', name: 'operator_date', width: 120}

                        ],
                        delayLoad: true,
                        url: application.fullPath + "authority/rptTerminalDetail/QueryRptTerminalDetail",
                        height: 500, pageSize: 30, rownumbers: false
                    });
        };
        audit.mobileNumberList = function (value) {
            audit.rep_list_manager[value] =
                    $("#rep_" + value).ligerGrid({
                        columns: [
                            {display: '终端品牌', name: 'terminal_brand_desc', width: 100},
                            {display: '终端型号', name: 'property_name', width: 150},
                            {display: '终端串号', name: 'terminal_id', width: 150},
                            {display: '终端状态', name: 'terminal_state', width: 130},
                            {display: '电信类型', name: 'tele_type', width: 100},
                            {display: '业务类型', name: 'order_sub_type', width: 100},
                            {display: '业务号码', name: 'device_number', width: 150},
                            {display: '用户姓名', name: 'cust_name', width: 150},
                            // { display: '参加活动', name: 'CompanyName', minWidth: 120 },
                            {display: '合约价', name: 'contract_price', width: 100},
                            {display: '成本价', name: 'sale_price', width: 100},
                            {display: '营业厅名称', name: 'dept_no', width: 300},
                            {display: '受理时间', name: 'operator_date', width: 120}

                        ],
                        delayLoad: true,
                        url: application.fullPath + "",
                        height: 500, pageSize: 30, rownumbers: false
                    });
        };
        audit.samplingAuditList = function (value) {
            audit.rep_list_manager[value] =
                    $("#rep_" + value).ligerGrid({
                        //业务号码 用户编号 流水号 电信类型 业务类型 用户姓名 用户状态 资费
                        //营业厅名称 渠道编码  接待人员 受理时间 是否已稽核 稽核是否通过 处理意见
                        columns: [
                            {display: '业务号码', name: 'acc_nbr', width: 150},
                            {display: '用户编号', name: 'user_no', width: 150},
                            {display: '流水号', name: 'order_id', width: 150},
                            {display: '电信类型', name: 'tele_type', width: 150},
                            {display: '业务类型', name: 'order_sub_type', width: 150},
                            {display: '用户姓名', name: 'customer_name', width: 150},
                            {display: '用户状态', name: 'user_statu_code', width: 150},
                            {display: '资费', name: 'product_name', width: 250},
                            {display: '营业厅', name: 'dept_name', width: 200},
                            {display: '渠道编码', name: 'chnl_code', width: 150},
                            {display: '接待人员', name: 'operator_id', width: 150},
                            {display: '受理人员', name: 'login_oper_no', width: 150},
                            {display: '受理时间', name: 'create_date', width: 150},
                            {display: '可选活动', name: 'activity_name', width: 300},
                            {display: '是否已稽核', name: 'audit_flag', width: 200},
                            {display: '稽核是否通过', name: 'audit_status', width: 200},
                            {display: '开户归属系统', name: 'system_type', width: 150},
                            {display: '处理意见', name: 'audit_note', width: 150},
							{ display: '电子协议查询', name: 'id_seq' ,render: function(rowdata, rowindex, value){
								if(rowdata.order_id!=null&&rowdata.order_id!=''&&rowdata.order_id!="null"){//有订单id 云销售订单
	                                if(rowdata.any_dog2 !=null)
									var rucan = "<a href=\"javascript:showAuditPic2(" + "'" + rowdata.id_seq + "','" + rowdata.order_id + "','" + rowdata.customer_name + "','" + rowdata.any_dog1 + "','" + rowdata.audit_flag + "','" + rowdata.audit_status + "','" + rowdata.audit_note + "','" + rowdata.tele_type +"','" + rowdata.any_dog2 + "','" + rowdata.any_dog3 + "','" +rowdata.dept_no+ "');\">稽核</a>";
									else
									var rucan = "<a href=\"javascript:showAuditPic2(" + "'" + rowdata.id_seq + "','" + rowdata.order_id + "','" + rowdata.customer_name + "','" + rowdata.any_dog1 + "','" + rowdata.audit_flag + "','" + rowdata.audit_status + "','" + rowdata.audit_note + "','" + rowdata.tele_type +"','" + "','" + "','" + rowdata.dept_no+"');\">稽核</a>";	
									//alert(rucan);                             
	                                return rucan;
								}
							},width: 200}/*,
                             { display: 'any_dog1', name: 'any_dog1',hide:true}*/
                        ],
                        
                        async:true,
                        allowAdjustColWidth: true,
                        delayLoad: true,
                        url: application.fullPath + "authority/rptSamplingAudit/QueryRptSamplingAudit",
                        height: 500, pageSize: 30, rownumbers: false
                    });
        };
		//点击业务统计 处理函数
		audit.businessStatisticsList = function (value) {
			 audit.rep_list_manager[value] =
                 $("#rep_" + value).ligerGrid({
                	 columns:[
						{display: '地市', name: 'local_net', width: 100},
						{display: '局别代码', name: 'dept_no', width: 100},
						{display: '局别名称', name: 'dept_name', width: 300},
						{display: '云销售开户量', name: 'clound_count', width: 150},
						{display: '非云销售开户量', name: 'not_clound_count', width: 150},
						{display: '抽样稽核数量', name: 'sample_number', width: 150},
						{display: '抽样完成稽核数量', name: 'sample_finish_number', width: 150},
						{display: '抽样完成率 X%', name: 'sample_rate', width: 150},
						{display: '非抽样完成稽核数量', name: 'not_sample_number', width: 150},
						{display: '重新稽核数量', name: 'again_audit_number', width: 150} ,
						{display: '统计时间', name: 'stat_date', width: 200}
                	          ],
                	 async:true,
                     allowAdjustColWidth: true,
                     delayLoad: true,
                     url: application.fullPath + "authority/rptbusilist/QueryRptBusiStatistics",
                     height: 500, pageSize: 30, rownumbers: false  	
                 });
			
			
		};
		//点击自助终端统计 处理函数
		audit.autoTerminalList = function (value) {
			 audit.rep_list_manager[value] =
                 $("#rep_" + value).ligerGrid({
                	 columns:[
						{display: '流水号', name: 'order_id', width: 150},
						{display: '订单状态', name: 'order_status', width: 80},
						{display: '自助终端名称', name: 'hall_name', width: 110},
						{display: '受理时间', name: 'oper_date', width: 100},
						{display: '办理业务', name: 'busi_name', width: 100},
						{display: '客户姓名', name: 'cust_name', width: 80},
						{display: '号码', name: 'acc_nbr', width: 80},
						{display: '电信类型', name: 'tele_type', width: 60},
						{display: '资费', name: 'product_name', width: 180},
						{display: '当月资费', name: 'first_month_fee', width: 90},
						{display: '参与活动', name: 'activity_type_name', width: 180},
						{display: '业务包', name: 'discnt_name', width: 180},
						{display: '支付费用', name: 'fee_all', width: 80},
						{display: '支付方式', name: 'pay_type', width: 100}
                	          ],
                	 async:true,
                     allowAdjustColWidth: true,
                     delayLoad: true,
                     url: application.fullPath + "authority/rptSelfService/queryRptSelfServiceList",
                     height: 500, pageSize: 30, rownumbers: false  	
                 });
			
			
		};

        /*
         *confirm 页面提交事件
         */
        audit.confirm = function () {
            audit.setParams();
            var rpt_list = audit.cur_report_name;
            audit.clearGrid(audit.rep_list_manager[rpt_list]);
            audit.rep_list_manager[audit.cur_report_name].loadData(true);
            $("#query_place").hide();
       	 	$("#result_place").show();
        };
        /*
         *获取页面中所有参数同时去除掉参数中的空字符串和undefined
         */
        audit.setParams = function () {
            var params = {};
            params.audit_report_type = $("input[name='audit_report_type']:checked").val();

            //params.tele_type = $("input[name='tele_type']:checked").val();
            var is_first_in = true;
            var arrChk = "";
            $("input[name='tele_type']").each(function () {
                if ($(this).is(":checked")) {
                    if(is_first_in){
                        arrChk="'";
                        is_first_in = false;
                    }else{
                        arrChk+="','";
                    }
                    arrChk += $(this).val();
                }
            })
            if(!is_first_in){
                arrChk +="'";
            }
            params.tele_type = arrChk;
            params.local_net = $("#select_city").val();
            params.dept_no = $("#select_urbon").val();
            params.oper_dept_no = audit.getJsonVal("select_channel", "dept_no");
            params.oper_no = audit.getJsonVal("select_people", "oper_no");
            params.begin_date = audit.formatDate($("#date_begin").val());
            params.end_date = audit.formatDate($("#date_end").val());
            params.pay_type = $("#select_pay_type").val();
            params.device_number = $("#select_number").val();
            params.acc_nbr = $("#select_number").val();
            params.sample_flag = $("#select_sample_flag option:selected").val();
            params.audit_flag = $("#select_audit_flag option:selected").val();
            if (params.audit_flag == "1") {
                params.audit_status = $("#select_audit_status option:selected").val();
            } else {
                params.audit_status = "";
            }
            params.pay_type = $("input[name='pay_type']:checked").val();
            params.sys_code = $("#select_sys_code option:selected").val();
            params.serv_code = $("input[name='serv_code']:checked").val();
            params.chnl_run_type = $("#select_chnl_run_type option:selected").val();//select_chnl_run_type
            for (var key in params) {
                if (params[key] != "" && params[key] != undefined) {
                    /*
                     *ligerU设置后台传递参数
                     */
                    audit.rep_list_manager[audit.cur_report_name].setParm(key, params[key]);
                } else {
                    audit.rep_list_manager[audit.cur_report_name].removeParm(key);
                }
            }
        };

        audit.formatDate = function (date) {
            if (date && date.match(/^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/)) {
                return date.split("-").join("");
            } else {
                return "";
            }
        };

        audit.getJsonVal = function (id, key) {
            var data = $("#" + id).attr("data");
            if (data) {
                var jsonData = JSON.parse(data);
                return jsonData[key] || "";
            } else {
                return "";
            }
        };

        audit.clearGrid = function (manager) {
            if (manager) {
                manager.clearGrid();
            }
        };
        audit.tele_type_all = function () {
            $("input[name='tele_type']").attr("checked", $("#tele_type_all").is(":checked"));
        };
        /*
         *重置所有函数
         */
        audit.reset = function () {
            $("input[name='audit_report_type']:checked").attr("checked", false);
            $("input[name='audit_report_type']:first").get(0).checked = true;
            //电信类型全选
            $("input[name='tele_type_all']").attr("checked", true);
            $("input[name='tele_type']").attr("checked", true);

            //$("input[name='tele_type']:checked").attr("checked", false);
            //$("input[name='tele_type']:first").get(0).checked = true;

            //select默认选中第一个
            $("#select_city option:first").attr('selected', 'selected');
            $("#select_urbon option:first").attr('selected', 'selected');
            audit.empty.emptyInput("select_channel");
            audit.empty.emptyInput("select_people");
            audit.empty.emptyInput("select_number");

            $("#date_begin").val(dateTime.nowDate);
            $("#date_end").val(dateTime.nowDate);

            $("#select_pay_type option:first").attr('selected', 'selected');
            $("#select_sample_flag option:first").attr('selected', 'selected');
            $("#select_audit_flag option:first").attr('selected', 'selected');
            $("#select_audit_stauts option:first").attr('selected', 'selected');
            //$("input[name='audit_report_type']").trigger("click");
            audit.initReport();

        }

        /*
         *导出函数
         */
        audit.rpt_export = function () {
            var audit_report_type = $("input[name='audit_report_type']:checked").val();
            if (audit_report_type == "operating_receipt") {
                var colNames = "tele_type,stat_date,serv_code,charge_code,pay_type,pay_charge,derate_charge,discount_charge,oper_local_net,oper_dept_no,oper_no";
                post(application.fullPath + "authority/rptBusiFee/QueryRptBusiFeeSumExcel", colNames);
            }
            else if (audit_report_type == "income_list") {
                var colNames = "tele_type,device_number,serv_code,charge_code,pay_type,pay_charge,derate_charge,discount_charge,oper_local_net,oper_dept_no,oper_no,oper_date";
                post(application.fullPath + "authority/rptBusiFee/QueryRptBusiFeeDetailExcel", colNames);
            }
            else if (audit_report_type == "business_list") {
                var colNames = "tele_type,serv_code_name,device_number,customer_name,product_name,oper_no,oper_date,dept_no,chnl_code,service_sn,order_id";
                post(application.fullPath + "authority/rptbusilist/QueryRptBusiListExcel", colNames);
            }
            else if (audit_report_type == "terminal_list") {
                var colNames = "terminal_brand_desc,property_name,terminal_id,terminal_state,tele_type,order_sub_type,device_number,cust_name,contract_price,sale_price,dept_no,operator_date";
                post(application.fullPath + "authority/rptTerminalDetail/QueryRptTerminalDetailExcel", colNames);
            }
            else if (audit_report_type == "mobile_number_list") {
                alert("开发中......");
            }
            else if (audit_report_type == "sampling_audit_list") {
              var colNames = "order_id,acc_nbr,user_no,tele_type,order_sub_type,customer_id,customer_name,user_statu_code,product_name,product_id,id_type,id_number,operator_id,operator_name,login_oper_no,login_oper_name,id_seq,audit_status,audit_flag,any_dog1,audit_note,dept_no,chnl_code,create_date,local_net,system_type";               
              post(application.fullPath + "authority/rptSamplingAudit/QueryRptAuditListExcel", colNames);
            }
            else if (audit_report_type == "business_statistics_list") {
                var colNames = "local_net,dept_no,dept_name,clound_count,not_clound_count,sample_number,sample_finish_number,sample_rate,not_sample_number,again_audit_number,stat_date";
                post(application.fullPath + "authority/rptbusilist/QueryRptBusiStatisticsExcel", colNames);
            }
            else if (audit_report_type == "auto_terminal_list") {
            	var colNames = "order_id,order_status,hall_name,oper_date,busi_name,cust_name,acc_nbr,tele_type,product_name,first_month_fee,activity_type_name,discnt_name,fee_all,pay_type";
                post(application.fullPath + "authority/rptSelfService/queryRptSelfServiceListExcel", colNames);
            }
            
        };

        function post(url, cols) {
            var params = {};
            //params.tele_type = $("input[name='tele_type']:checked").val();
            var is_first_in = true;
            var arrChk = "";
            $("input[name='tele_type']").each(function () {
                if ($(this).is(":checked")) {
                    if(is_first_in){
                        arrChk="'";
                        is_first_in = false;
                    }else{
                        arrChk+="','";
                    }
                    arrChk += $(this).val();
                }
            })
            if(!is_first_in){
                arrChk +="'";
            }
            params.tele_type = arrChk;

            params.local_net = $("#select_city").val();
            params.dept_no = $("#select_urbon").val();
            params.oper_dept_no = audit.getJsonVal("select_channel", "dept_no");
            params.oper_no = audit.getJsonVal("select_people", "oper_no");
            params.begin_date = audit.formatDate($("#date_begin").val());
            params.end_date = audit.formatDate($("#date_end").val());
            params.device_number = $("#select_number").val();
            params.acc_nbr = $("#select_number").val();
            params.audit_flag = $("#select_audit_flag option:selected").val();
            if (params.audit_flag == "1") {
                params.audit_status = $("#select_audit_status option:selected").val();
            } else {
                params.audit_status = "";
            }
            params.sample_flag = $("#select_sample_flag option:selected").val();

            params.pay_type = $("input[name='pay_type']:checked").val();
            params.name_list = cols;
            params.sys_code = $("#select_sys_code option:selected").val();
            params.serv_code = $("input[name='serv_code']:checked").val();
            params.chnl_run_type = $("#select_chnl_run_type option:selected").val();
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


        var sampling_audit_order_id = null;
        var sampling_audit_id_seq = null;
        var sampling_audit_any_dog1 = null;
        var sampling_audit_customer_name = null;
        var sampling_audit_flag = null;
	function showpdf(order_id,type){		
            var order_id = order_id;
			var type=type;
            if (!order_id) {
                alert("显示电子协议：订单号为空！");
                return;
            }
            var apweb_url =$("#apweb_url").val(); //无纸化地址
            var openAcc_templateid =$("#openAcc_templateid").val() //开户协议templateid
			var good_templateid =$("#good_templateid").val();//靓号协议templateid
			var preferential_templateid =$("#preferential_templateid").val();//优惠协议templateid
			var pdfUrl = apweb_url+"form/formPdfDetail.action?folat=2&templateid="+openAcc_templateid+"&formsn=";
			//靓号协议
			var pdfUrl2 = apweb_url+"form/formPdfDetail.action?folat=2&templateid="+good_templateid+"&formsn=";
			//补充协议
			var pdfUrl3 = apweb_url+"form/formPdfDetail.action?folat=2&templateid="+preferential_templateid+"&formsn=";
            
		if(type=="1"){     //开户协议
		window.open(pdfUrl+order_id);
		}
		if(type=="2"){    //靓号协议 
		window.open(pdfUrl2+order_id);
		}
		if(type=="3"){    //补充协议
		window.open(pdfUrl3+order_id);
		}

	}		
        //获取身份证图片
        function showAuditPic() {
            alert("showAuditPic:sampling_audit_customer_name=" + sampling_audit_customer_name);
            alert("showAuditPic:id_seq=" + id_seq);
            var id_seq = sampling_audit_id_seq;
            var order_id = sampling_audit_order_id;
            $.ajax({
                url: application.fullPath + 'authority/rptSamplingAudit/getIDPic',
                type: 'post',
                data: {
                    "cust_seq_id": id_seq
                },
                waitMsg: "正在查询！",
                success: function (message) {
                    var dingdanhao = message.args.pic_path;
                    var apweb_url =$("#apweb_url").val(); //无纸化地址
                    var openAcc_templateid =$("#openAcc_templateid").val() //开户协议templateid
                    var pdfUrl =  apweb_url+"form/formPdfDetail.action?folat=2&templateid="+openAcc_templateid+"&formsn=";
// 						//靓号协议
// 						var pdfUrl2 = "http://133.0.90.137:9080/APWeb/form/formPdfDetail.action?folat=2&templateid=ff8080814dfb7f0c014dfb848b1d0007&formsn=";
// 						//补充协议
// 						var pdfUrl3 = "http://133.0.90.137:9080/APWeb/form/formPdfDetail.action?folat=2&templateid=ff8080814dfb7f0c014dfb83d7a60004&formsn=";
// 						//宽带协议 templateid暂无
// 						var pdfUrl4 = "#";

                    pdfUrl = pdfUrl + order_id;
// 						pdfUrl2=pdfUrl2+order_id;
// 						pdfUrl3=pdfUrl3+order_id;
// 						pdfUrl4=pdfUrl4+order_id;
                    var sampling_audit_dialog = $("#sampling_audit_dialog");
                    var idsrc = dingdanhao.substring(dingdanhao.indexOf("uss_web") - 1, dingdanhao.length);
                    $("#identitypic").attr("src", idsrc);
                    $("#getPdf").attr("src", pdfUrl);
                    $("#show_audit").attr("href", pdfUrl);
// 						$("#show_audit2").attr("href",pdfUrl2);
// 						$("#show_audit3").attr("href",pdfUrl3);
// 						$("#show_audit4").attr("href",pdfUrl4);
                    $("#audit_order_id").val(order_id);
                    audit.dialog.showDialog("sampling_audit_dialog");
                    audit.dialog.initAuditDialog();
                    if (sampling_audit_any_dog1 == "eq") {
                        if (sampling_audit_flag == "已稽核") {
                            //全部按钮 隐藏
                            $("#audit_pass").hide();
                            $("#audit_no_pass").hide();
                            $("#audit_again").hide();
                            $("#audit_note_div").hide();

                        } else {
                            //显示稽核按钮
                            $("#audit_pass").show();
                            $("#audit_no_pass").show();
                            $("#audit_again").hide();
                            $("#audit_note_div").show();
                        }
                    } else {
                        if (sampling_audit_flag == "已稽核") {
                            //显示复核按钮
                            $("#audit_pass").hide();
                            $("#audit_no_pass").hide();
                            $("#audit_again").show();
                            $("#audit_note_div").show();
                        } else {
                            //显示稽核按钮
                            $("#audit_pass").show();
                            $("#audit_no_pass").show();
                            $("#audit_again").hide();
                            $("#audit_note_div").show();
                        }
                    }

                },
                error: function () {
                    //console.log("操作失败，请重试！");
                    alert("操作失败，请重试！");
                    //$("#dev_info_head").after("<tr name='tr_dev_info'> <td width='24' height='32' align='right' bgcolor='#FBFBFB'></td><td width='110' align='left' bgcolor='#FBFBFB'  colspan='5'>操作失败，请重试！</td></tr>");
                }
            });
	};
		var checkClickFlags =new Array();
        //获取身份证图片
        function showAuditPic2(id_seq, order_id, customer_name, any_dog1, audit_flag, audit_status, audit_note,tele_type,any_dog2,any_dog3,dept_no) {
            var m_id_seq = id_seq;
            var m_order_id = order_id;
            var m_dept_no = dept_no;
            var any_dog2=any_dog2;
            var any_dog3=any_dog3;	
            if (m_id_seq == '' || m_id_seq == null || m_id_seq == undefined) {
                alert("客户信息未校验：无身份证图片");
                return;
            }

            if (m_order_id == '' || m_order_id == null || m_order_id == undefined) {
                alert("无客户订单号：无电子协议");
            }
            if(tele_type == 'M165'){
            	 $("#show_audit4").show();
            	 $("#show_audit2").hide();
            	 $("#show_audit3").hide();
            }else{
            	 $("#show_audit4").hide();
            }
            
            $("#show_audit").css("color","white");
            $("#show_audit2").css("color","white");
            $("#show_audit3").css("color","white");
            $("#show_audit4").css("color","white");
            $("#show_audit").attr("name",order_id+"a");
            $("#show_audit2").attr("name",order_id+"b");
            $("#show_audit3").attr("name",order_id+"c");
            $("#show_audit4").attr("name",order_id+"d");
            for(var i=0;i<checkClickFlags.length;i++){
            	if(checkClickFlags[i] == (order_id+"a")){
            	  $("#show_audit").css("color","green");
            	}
				if(checkClickFlags[i] == (order_id+"b")){
				  $("#show_audit2").css("color","green");
            	}
				if(checkClickFlags[i] == (order_id+"c")){
				  $("#show_audit3").css("color","green");
            	}
				if(checkClickFlags[i] == (order_id+"d")){
				  $("#show_audit4").css("color","green");
            	}
				
            }
            
            $.ajax({
                url: application.fullPath + 'authority/rptSamplingAudit/getIDPic',
                type: 'post',
                data: {
                    "cust_seq_id": m_id_seq
                },
                waitMsg: "正在查询！",
                success: function (message) {
                   /* if (message.args.resp_code == "customerVerify.getIDPic.error") {
                        alert("获得身份证图片失败:查询身份信息错误");
                        return;
                    }*/
                    var dingdanhao = message.args.pic_path;
                    var apweb_url =$("#apweb_url").val(); //无纸化地址
                    var openAcc_templateid =$("#openAcc_templateid").val() //开户协议templateid
                    var pdfUrl =  apweb_url+"form/formPdfDetail.action?folat=2&templateid="+openAcc_templateid+"&formsn=";
					pdfUrl=pdfUrl+order_id;
                    var sampling_audit_dialog = $("#sampling_audit_dialog");
                    var idsrc ="";
                    if(dingdanhao!=null){
                    	 dingdanhao.substring(dingdanhao.indexOf("uss_web") - 1, dingdanhao.length);
                    }                   
                    $("#identitypic").attr("src", idsrc);
                    $("#getPdf").attr("src", pdfUrl);
                    if(audit_note!=null&&audit_note!=''&&audit_note!="null"){
                     $("#audit_note").val(audit_note);
                    }
                    $("#audit_order_id").val(m_order_id); 
                    $("#audit_dept_no").val(m_dept_no);
	                $("#any_dog2").val(any_dog2);
                    $("#any_dog3").val(any_dog3);

                    audit.dialog.showDialog("sampling_audit_dialog");
                    audit.dialog.initAuditDialog();
                    //新需求，只要能进来，都能看到3个按钮
                    $("#audit_pass").show();
                    $("#audit_no_pass").show();
                    $("#audit_again").show();
                    $("#audit_note").show();
                    //本部门工号审核，有次数和时间限制
                    if (any_dog1 == "eq") {
                        if (audit_flag == "已稽核"/*&&audit_status=="通过")*/) {
                    		
            				if(any_dog3>0){
            					  //只能点 重新稽核
                                $("#audit_pass").attr("href", "javascript:audit_show_warning(5);");
                                $("#audit_no_pass").attr("href", "javascript:audit_show_warning(5);");
                                $("#audit_again").attr("href", "javascript:f_audit_again();");
                                $("#audit_note").show();		
            				}else if(any_dog3==0) {
                            	//全部按钮 都不能点 只能看
                           		 $("#audit_pass").attr("href", "javascript:audit_show_warning(6);");
                           		 $("#audit_no_pass").attr("href", "javascript:audit_show_warning(6);");
                           		 $("#audit_again").attr("href", "javascript:audit_show_warning(6);");
                           		 $("#audit_note").show();
            				}else if(any_dog3==-1){
                            	//全部按钮 都不能点 只能看
                          		 $("#audit_pass").attr("href", "javascript:audit_show_warning(7);");
                          		 $("#audit_no_pass").attr("href", "javascript:audit_show_warning(7);");
                          		 $("#audit_again").attr("href", "javascript:audit_show_warning(7);");
                          		 $("#audit_note").show();
           					}  
                        } else {
                            //可以点通过或者不通过
                            $("#audit_pass").attr("href", "javascript:sampling_audit(11);");
                            $("#audit_no_pass").attr("href", "javascript:sampling_audit(12);");
                            $("#audit_again").attr("href", "javascript:audit_show_warning(2);");
                            $("#audit_note").show();
                        }
                    } else if (any_dog1 == "parentDept") {
                        //上级部门 无次数和时间限制
                        if (audit_flag == "已稽核") {
                            //只能点 重新稽核
                            $("#audit_pass").attr("href", "javascript:audit_show_warning(3);");
                            $("#audit_no_pass").attr("href", "javascript:audit_show_warning(3);");
                            $("#audit_again").attr("href", "javascript:f_audit_again();");
                            $("#audit_note").show();
                        } else {
                            //可以点通过或者不通过
                            $("#audit_pass").attr("href", "javascript:sampling_audit(11);");
                            $("#audit_no_pass").attr("href", "javascript:sampling_audit(12);");
                            $("#audit_again").attr("href", "#");
                            $("#audit_note").show();
                        }
                    } else {
                        if (audit_flag == "已稽核") {
                            //显示复核按钮
                            $("#audit_pass").attr("href", "javascript:audit_show_warning(4);");
                            $("#audit_no_pass").attr("href", "javascript:audit_show_warning(4);");
                            $("#audit_again").attr("href", "javascript:audit_show_warning(4);");
                            $("#audit_note").show();
                        } else {
                            //显示稽核按钮
                            $("#audit_pass").attr("href", "javascript:audit_show_warning(4);");
                            $("#audit_no_pass").attr("href", "javascript:audit_show_warning(4);");
                            $("#audit_again").attr("href", "javascript:audit_show_warning(4);");
                            $("#audit_note").show();
                        }
                    }

				queryPdfStatusCode(order_id);
                },
                error: function () {
                    alert("操作失败，请重试！");
                }
            });
	};
	//获取pdf状态码
	function queryPdfStatusCode(order_id){
		var order_id=order_id;	
		$.ajax({	
			url :application.fullPath + 'authority/rptSamplingAudit/pdfStatusCode',
			type : 'post',
			data:{
				"order_id": order_id
			},
			waitMsg : "正在查询！",
			success : function(message) {
				var apweb_url =$("#apweb_url").val(); //无纸化地址
				var openAcc_templateid =$("#openAcc_templateid").val() //开户协议templateid
				var good_templateid =$("#good_templateid").val();//靓号协议templateid
				var preferential_templateid =$("#preferential_templateid").val();//优惠协议templateid
				var pdfUrl = apweb_url+"form/formPdfDetail.action?folat=2&templateid="+openAcc_templateid+"&formsn=";
				//靓号协议
				var pdfUrl2 = apweb_url+"form/formPdfDetail.action?folat=2&templateid="+good_templateid+"&formsn=";
				//补充协议
				var pdfUrl3 = apweb_url+"form/formPdfDetail.action?folat=2&templateid="+preferential_templateid+"&formsn=";
				//宽带协议 templateid暂无
				var pdfUrl4 = "#";

				pdfUrl=pdfUrl+order_id;
				pdfUrl2=pdfUrl2+order_id;
				pdfUrl3=pdfUrl3+order_id;
				pdfUrl4=pdfUrl4+order_id;
				
				if(message.args.pdfStatusCodes){
					   var pdfStatusCodes =message.args.pdfStatusCodes;
					   if(pdfStatusCodes.开户模版 =="000" || pdfStatusCodes.开户模版 =="003"){
						   $("#show_audit").attr("href",pdfUrl);					   
					   }else{
						   $("#show_audit").removeAttr("href",pdfUrl);
					   }
					   if(pdfStatusCodes.靓号PDF模板 =="000" || pdfStatusCodes.靓号PDF模板 =="003"){
						   $("#show_audit2").attr("href",pdfUrl2);  						   
					   }else{
						   $("#show_audit2").removeAttr("href",pdfUrl2);					   
					   }
					   if(pdfStatusCodes.优惠方案PDF模板 =="000" || pdfStatusCodes.优惠方案PDF模板 =="003"){
						   $("#show_audit3").attr("href",pdfUrl3);						   
					   }else{						   
						   $("#show_audit3").removeAttr("href",pdfUrl3);
					   }
                   	   if(pdfStatusCodes.宽带PDF模板 =="000" || pdfStatusCodes.宽带PDF模板 =="003"){ 		
                 	   $("#show_audit4").attr("href",pdfUrl4);		
					   }else{	
						  $("#show_audit4").removeAttr("href",pdfUrl4);
					   }
						
					}
			}
				
			});
		
		
	}
	
        //获取电子协议
        function get_pic_next() {
        	   $("#identitypic").show();
               $("#getPdf").hide();
        }
        function get_pic_pre() {
         
            $("#getPdf").show();
            $("#identitypic").hide();

        }
        function f_audit_again() {
            $("#audit_pass").attr("href", "javascript:sampling_audit(11);");
            $("#audit_no_pass").attr("href", "javascript:sampling_audit(12);");
            $("#audit_again").attr("href", "#");
            $("#audit_note").show();
        }
        function audit_show_warning(warning_type) {
            if (warning_type == 1) {
                alert("操作员所属部门已经稽核，本部门工号不能再进行稽核");
            } else if (warning_type == 2) {
                alert("未稽核状态，无须点击重新复核按钮");
            } else if (warning_type == 3) {
                alert("下级部门已稽核，请点击重新稽核按钮");
            } else if (warning_type == 4) {
                alert("上级部门已稽核");
            }  else if (warning_type == 5) {
                alert("当月3日内有三次稽核机会，请点击重新稽核按钮");
            } else if (warning_type == 6) {
                alert("三次稽核机会用完");
            } else if (warning_type == 7) {
                alert("超期：限当月，距首次稽核3日内");
            }
        }

        function sampling_audit(audit_status) {
            //获取被选中的行 audit_status
            var audit_note = $("#audit_note").val();
            var any_dog2 =$("#any_dog2").val();
            var any_dog3 =$("#any_dog3").val();
              var data1 = {
                "order_id": $("#audit_order_id").val(),
                "dept_no": $("#audit_dept_no").val(),
                "audit_note": $("#audit_note").val(),
                "any_dog2":any_dog2,
                "any_dog3":any_dog3,
                "audit_status": audit_status,
                "audit_flag": "1"
              };
            
            var URL = application.fullPath + 'authority/rptSamplingAudit/auditRptSamplingAudit';
            $.ajax({
                url: URL,
                type: 'post',
                data: data1,
                waitMsg: "读取中..",
                success: function (message) {
                    if ("success" == message.type) {
                        $.alert("稽核成功！");
                        audit.dialog.hideDialog("sampling_audit_dialog");
                        audit.rep_list_manager["sampling_audit_list"].loadData(true);	
//                         audit.confirm();
                    } else {
                        $.alert("稽核操作失败：数据提交失败！");
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
                }
            });
        }
     
	function chg_color(e) {	
		var v =$(e);
		var obj_name =$(e)[0].name;
// 		document.getElementsByName(obj_name)[0].style.color ="green";
		$("a[name="+obj_name+"]").css("color","green");
		checkClickFlags.push(obj_name);
		
	}

    </script>
</head>
<body>
<input type="hidden"  id="apweb_url"  value="${apweb_url}"/><!--无纸化地址   -->
<input type="hidden"  id="openAcc_templateid"  value="${openAcc_templateid}"/><!--开户模板ID -->
<input type="hidden"  id="good_templateid"  value="${good_templateid}"/><!--靓号模板ID -->
<input type="hidden"  id="preferential_templateid"  value="${preferential_templateid}"/><!--优惠协议模版ID   -->
<div class="show">
    <div class="show_title_bg">
        <div class="show_title">
			<!--<span class="crumb">当前位置：账号选择</span>-->稽核报表<!--<span class="red">选择完一项后，才能选择下面一项的内容，否则下面全部置灰无法选择；仅可以做单选。</span>-->
        </div>
    </div>
    <ul id="query_place">
    <div class="box box_margin_right" id="audit_report_type_box">
        <div class="show_big_title"><strong>.</strong>请选择报表的类别：</div>
        <div class="table_box">
            <table width="446" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
				  <td width="24" height="22" align="center"><input type="radio" name="audit_report_type" value="operating_receipt" id="checkbox" checked="checked"></td>
				  <td width="124">营业收入</td>
				  <td width="24" align="center"><input type="radio" name="audit_report_type" value="income_list" id="checkbox3" ></td>
				  <td width="124">收入清单</td>
				  <td width="25" align="center"><input type="radio" name="audit_report_type" value="business_list" id="checkbox5" ></td>
				  <td width="125">业务清单</td>
				</tr>
				 <tr>
				  <td width="24" height="22" align="center"><input type="radio" name="audit_report_type" value="terminal_list" id="checkbox2" ></td>
				  <td width="124">终端清单</td>  
				  <td width="24" height="22" align="center"><input type="radio" name="audit_report_type" value="mobile_number_list" id="checkbox4" ></td>
				  <td width="124">靓号清单</td>			  
				  <td width="25" height="22" align="center"><input type="radio" name="audit_report_type" value="sampling_audit_list" id="checkbox6" ></td>
				  <td width="125">抽样稽核</td>              
                </tr>
                <tr>
                 <td width="24" height="22" align="center"><input type="radio" name="audit_report_type" value="business_statistics_list" id="checkbox7" ></td>
				 <td width="124">业务统计</td>
				 <!-- <td width="24" height="22" align="center"><input type="radio" name="audit_report_type" value="auto_terminal_list" id="checkbox8" ></td>
				 <td width="124">自助终端</td> -->
                </tr>
            </table>
        </div>

    </div>

    <div class="box" id="tele_type_box">
        <div class="show_big_title"><strong>.</strong>电信类型：</div>
        <div class="table_box">
            <table width="446" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="24" height="22" align="center"><input type="checkbox"  name="tele_type_all"
                                                                     id="tele_type_all" checked="checked"></td>
                    <td width="124">全选</td>
                    <td width="24" align="center"><input type="checkbox" value="2G" name="tele_type" checked="checked"></td>
                    <td width="123">2G</td>
                    <td width="26" align="center"><input type="checkbox" value="3G" name="tele_type" checked="checked"></td>
                    <td width="125">3G</td>
                </tr>
                <tr>
                    <td height="22" align="center"><input type="checkbox" value="4G" name="tele_type" checked="checked"></td>
                    <td> 4G</td>
                    <td align="center"><input type="checkbox" value="M165" name="tele_type" checked="checked"></td>
                    <td> 宽带</td>
                    <td align="center">&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr id="wo_lan" style="display:none;">
                	<td align="center"><input type="checkbox" value="LAN" name="tele_type" checked="checked"></td>
                    <td> 智慧沃家</td>
                    <td align="center"><input type="checkbox" value="WO_FA" name="tele_type" checked="checked"></td>
                    <td> 沃家庭</td>
                </tr>
            </table>
        </div>
    </div>
    <div class="box box_margin_right" id="select_rang_box">
        <div class="show_big_title"><strong>.</strong>请选择需查询的范围：</div>
        <div class="table_box_none">
            <table width="486" border="3" align="center" cellpadding="3" cellspacing="3">
                <tr>
                    <td width="114" height="22" align="right"><strong>地 市：</strong></td>
                    <td width="372" align="center">
					  <select  ltype="init" name="select_city" id="select_city" url="<%=fullPath%>authority/webUtil/queryLocalNet" class="sel">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td height="22" align="right"><strong>城 区：</strong></td>
                    <td align="center">
					  <select name="select_urbon" id="select_urbon" url="<%=fullPath%>authority/webUtil/queryDeptByLocalNet"  class="sel">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td width="114" height="22" align="right"><strong>营业厅：</strong></td>
                    <td width="372" align="center">
                        <div id="select_channel_delete" class="input_click_delete"></div>
                        <a href="javascript:void(0);">
						<input type="text" name="select3" id="select_channel"  class="input_click"  readonly="readonly">
                        </a>
                    </td>
                </tr>
                <tr id="select_people_tr">
                    <td width="114" height="22" align="right"><strong>接待/受理人员：</strong></td>
				  <td width="372" align="center"><div id="select_people_delete"  class="input_click_delete"></div>
                        <a href="javascript:void(0);">
					<input type="text" name="select3" id="select_people"  class="input_click"  readonly="readonly">
                        </a>
                    </td>
                </tr>
                <tr id="select_number_tr">
                    <td width="114" height="22" align="right"><strong>号码选择：</strong></td>
				  <td width="372" align="center"><div id="select_number_delete" class="input_click_delete"></div>
				  <a href="javascript:void(0);"><input type="text" name="select3" id="select_number"  class="input_click" >
                        </a></td>
                </tr>
                <tr id="select_sample_flag_tr">
                    <td width="114" height="22" align="right"><strong>是否抽样：</strong></td>
                    <td width="372" align="center">
                        <select name="select_sample_flag" id="select_sample_flag" class="sel">
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                    </td>
                </tr>

                <tr id="select_audit_flag_tr">
                    <td width="114" height="22" align="right"><strong>是否稽核：</strong></td>
                    <td width="372" align="center">
                        <select name="select_audit_flag" id="select_audit_flag" class="sel">
                            <option value="">全部</option>
                            <option value="0">否</option>
                            <option value="1">是</option>
                        </select>
                    </td>
                </tr>
                <tr id="select_chnl_run_type_tr">
                    <td width="114" height="22" align="right"><strong>渠道类型：</strong></td>
                    <td width="372" align="center">
                        <select name="select_chnl_run_type" id="select_chnl_run_type" class="sel">
                            <option value="">全渠道</option>
                            <option value="01">自有营业厅</option><!-- 01  -->
                            <option value="02">代理商</option> <!-- 02 -->                    
                        </select>
                    </td>
                </tr>
                <tr id="select_sys_code_tr">
                    <td width="114" height="22" align="right"><strong>是否云销售：</strong></td>
                    <td width="372" align="center">
                        <select name="select_sys_code" id="select_sys_code" class="sel">
                            <option value="ALL">全部</option>
                            <option value="YXS">是</option>
                            <option value="OTHER">否</option>                     
                        </select>
                    </td>
                </tr>
                <tr id="select_audit_status_tr">
                    <td width="114" height="22" align="right"><strong>是否稽核通过：</strong></td>
                    <td width="372" align="center">
                        <select name="select_audit_status" id="select_audit_status" class="sel">
                            <option value="11">通过</option>
                            <option value="12">不通过</option>
                            <option value="">全部</option>
                        </select>
                    </td>
                </tr>


            </table>
        </div>
    </div>

    <div class="box" id="pay_type_box">
        <div class="show_big_title"><strong>.</strong>请选择支付方式的类别：</div>
        <div class="table_box">
            <table width="446" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="24" height="22" align="center"><input type="radio" name="pay_type" value=""
                                                                     checked="checked"></td>
                    <td width="124">全部</td>
                    <td width="24" align="center"><input type="radio" name="pay_type" value="现金"></td>
                    <td width="124">现金</td>
                    <td width="25" align="center"><input type="radio" name="pay_type" value="银行托收"></td>
                    <td width="125">银行托收</td>
                </tr>
                <tr>
                    <td height="22" align="center"><input type="radio" name="pay_type" value="支票收费"></td>
                    <td>支票收费</td>
                    <td align="center"><input type="radio" name="pay_type" value="POS刷卡"></td>
                    <td>POS刷卡</td>
                    <td align="center">&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
            </table>
        </div>
    </div>

    <div class="box" id="serv_code_box">
        <div class="show_big_title"><strong>.</strong>业务类型：</div>
        <div class="table_box">
            <table width="446" border="0" align="center" cellpadding="0" cellspacing="0">
                <c:forEach items="${serv_code_line_list}" var="servline">
                    <tr>
			    <c:choose><c:when test="${not empty servline[0].code_id}"><td width="24" height="22" align="center"><input type="radio"  name="serv_code" <c:choose><c:when test="${servline[0].code_id=='ALL'}">checked="checked" value="" </c:when><c:otherwise> value="${servline[0].code_id}" </c:otherwise></c:choose> ></td><td width="124">${servline[0].code_name}</td></c:when></c:choose>
				<c:choose><c:when test="${not empty servline[1].code_id}"><td width="24" align="center"><input type="radio" value="${servline[1].code_id}" name="serv_code"  ></td><td width="123">${servline[1].code_name}</td></c:when><c:otherwise><td align="center">&nbsp;</td><td>&nbsp;</td></c:otherwise></c:choose>
				<c:choose><c:when test="${not empty servline[2].code_id}"><td width="24" align="center"><input type="radio" value="${servline[2].code_id}" name="serv_code"  ></td><td width="123">${servline[2].code_name}</td></c:when><c:otherwise><td align="center">&nbsp;</td><td>&nbsp;</td></c:otherwise></c:choose>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
    <span class="clear"></span>
    
    <div class="box box_long" id="report_date_box">
        <div class="show_big_title"><strong>.</strong>请选择统计报表的时间：</div>
        <div class="table_box">
            <table width="980" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
					<td width="210"> <t:date id="date_begin" name="date_begin"  maxDateElId="date_end"  value="<%=nowDate%>"  ></t:date></td>
					<td width="24" align="center">到</td>
					<td width="746"> <t:date id="date_end" name="date_end"   minDateElId="date_begin"  value="<%=nowDate%>"  maxDate="<%=nowDate%>"  ></t:date></td>
                </tr>
            </table>
        </div>
    </div>

    <div class="dashed">
			<div><a id="reset" href="javascript:void(0);">重 置</a> <a id="confirm" href="javascript:void(0);">查 询</a> </div>
    </div>
	</ul>
	
	<ul id="result_place">
    <div id="result_list" class="box box_long" style="padding:0 0 10px 0;height:360px;">
        <div id="rep_report_list" class="show_big_title"><strong>.</strong>报表结果展示：</div>
    </div>
      <div class="dashed">
			<div><a id="export" href="javascript:void(0);">导 出</a> <a id="return" href="javascript:void(0);">返 回</a></div>
    </div>
    </ul>
    <div class="clear"></div>
</div>

<div id="divCoverIframe" class="cover" style="display:none;">
	  <iframe id="coverIframe" name="left"  frameborder="0" height="100%" scrolling="no" width="100%" style="Z-INDEX: 1;WIDTH:100%; HEIGHT:100%;OVERFLOW: visible" src="<%=fullPath%>authority/reportForms/coverIframe">
    </iframe>
</div>
<!-- 选择营业厅开始 -->
<div id="select_channel_dialog" class="show-select" style="display:none;">
    <div class="icon-close"></div>
    <div class="select-title">查询条件：</div>
    <div class="search-input">
        <div class="leble">营业厅代码：</div>
        <div class="input"><input class="branch-code" type="text" value=""/></div>
        <div class="leble">营业厅名称：</div>
        <div class="input"><input class="branch-name" type="text" value=""/></div>
        <a class="js_query" href="javascript:void(0);">查 询</a>
        <a class="js_reset" href="javascript:void(0);">重 置</a>
    </div>
    <div id="select_channel_table" class="select-table"></div>
</div>
<!-- 选择员工开始 -->
<div id="select_staff_dialog" class="show-select" style="display:none;">
    <div class="icon-close"></div>
    <div class="select-title">查询条件：</div>
    <div class="search-input">
        <div class="leble">员工代码：</div>
        <div class="input"><input class="branch-code" type="text" value=""/></div>
        <div class="leble">员工名称：</div>
        <div class="input"><input class="branch-name" type="text" value=""/></div>
        <a class="js_query" href="javascript:void(0);">查 询</a>
        <a class="js_reset" href="javascript:void(0);">重 置</a>
    </div>
    <div id="select_staff_table" class="select-table"></div>
</div>
<!--稽核界面 -->
<div id="sampling_audit_dialog" class="show-select" style="display:none;">
    <div class="icon-close"></div>
    <div class="select-title">协议展示：</div>
    <table style="margin:0 auto">
        <tr>
            <td><a class="audit_leble" href="javascript:get_pic_pre();"><</a></td>
					<td><Iframe height='226' width='836' src="" id="identitypic" scrolling="auto" style="display:none;"></iframe></td>
					<td><Iframe height='226' width='836' src="" id="getPdf"  scrolling="auto" ></iframe></td>
            <td><a class="audit_leble" href="javascript:get_pic_next();">></a></td>
        </tr>
    </table>
    <div id="audit_table">
        <div id="audit_note_div">
            <div class="select">
                <div id="audit_note_title" class="title">稽核处理意见：</div>
						<div class="btn">
							<a id="show_audit4"  href="" target="_blank" onClick="javascript:chg_color(this);">宽带协议 </a>
						</div>
					    <div class="btn">
							<a id="show_audit2"  href="" target="_blank" onClick="javascript:chg_color(this);">靓号协议</a>
                       </div>
                       	<div class="btn">
							<a id="show_audit3"  href="" target="_blank" onClick="javascript:chg_color(this);">补充协议</a>
						</div>
					    <div class="btn">
							<a id="show_audit"  href="" target="_blank"  onClick="javascript:chg_color(this);">入网协议</a>
                       </div>
					</div>

            <div style="height: 60px" id="audit_note_text">
                <input type="hidden" id="audit_order_id" value=""></input>
                <input type="hidden" id="audit_dept_no" value=""></input>
                <input type="hidden" id="any_dog2" value=""></input>
                <input type="hidden" id="any_dog3" value=""></input>
						<textarea   id="audit_note"  value="" style="width:735px; _width:755px; height:40px;margin-left:32px; _margin-left:20px;" ></textarea >
            </div>
        </div>
        <div class="select_left">
            <div class="btn_left">
                <a id="audit_pass" href="javascript:sampling_audit(11);">审核通过</a>
            </div>
            <div class="btn_left">
                <a id="audit_no_pass" href="javascript:sampling_audit(12);">审核不通过</a>
            </div>
            <div class="btn_left">
                <a id="audit_again" href="javascript:f_audit_again();">重新审核</a>
            </div>
        </div>
        <!--
        <div class="audit_a">
            <a id="audit_pass" style="margin:0px 5px" href="javascript:sampling_audit(11);">审核通过</a>
            <a id="audit_no_pass" style="margin:0px 5px" href="javascript:sampling_audit(12);">审核不通过</a>
            <a id="audit_again" style="margin:0px 5px" href="javascript:sampling_audit(13);">重新稽核</a>
        </div>-->
    </div>

</div>
</body>
</html>