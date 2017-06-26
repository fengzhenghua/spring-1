<%@page import="com.tydic.unicom.crm.web.uss.constants.UrlsMappings"%>
<%@page import="com.tydic.unicom.crm.web.uss.constants.ControllerMappings"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实名返档报表</title>
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="<%=fullPath%>css/share.css" >


<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/commCheck.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>js/jquery-ligerui-2.25/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />

<script src="<%=basePath%>js/jquery-ligerui-2.25/js/core/base.js" type="text/javascript"></script> 
<script src="<%=basePath%>js/jquery-ligerui-2.25/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="<%=basePath%>js/jquery-ligerui-2.25/js/plugins/ligerDialog.js" type="text/javascript"></script>
<script src="<%=fullPath%>js/json2.js" type="text/javascript"></script> 


<style type="text/css">
.list_nx{padding:4px 0px 0px 0px; width:874px; height:26px; margin:0 auto; vertical-align:central;}
.left_data_nx{float:left;}
.left_data_nx input {padding-left:4px; margin-right:4px; border:1px solid #e7e7e7; height:24px; line-height:24px; width: 148px;}
</style>
<script type="text/javascript">
var fullPath = '<%=fullPath%>';
$(function(){
	$("#rlist").jqGrid({
        url: "searchResult",
        datatype:"local", 
        mtype:"POST",//提交方式
        loadonce: true,
        height:258,//高度，表格高度。可为数值、百分比或'auto'
        //width:1000,//这个宽度不能为百分比
        autowidth:true,//自动宽
        colNames:['姓名', '身份证号', '地址','手机号码','工单号','添加日期','补登渠道','审核结果','性别','出生日期','有效期','联系地址','联系人','联系电话'],
        colModel:[
            //{name:'id',index:'id', width:'10%', align:'center' },
            {name:'cert_name',index:'cert_name', width:'6%',align:'center'},
            {name:'cert_id',index:'cert_id', width:'16%',align:'center'},
            {name:'cert_addr',index:'cert_addr', width:'25%', align:"center"},
            {name:'device_number',index:'device_number', width:'11%', align:"center", sortable:false},
            {name:'order_id',index:'order_id', width:'12%', align:"center"},
            {name:'oper_date',index:'oper_date', width:'8%', align:"center"},
            {name:'plat_type_name',index:'plat_type_name', width:'7%', align:"center"},
            {name:'audit_flag',index:'audit_flag',width:'7%',align:"center"},
            {name:'sex',index:'sex',align:"center", hidden:true},
            {name:'birthday',index:'birthday',align:"center", hidden:true},
            {name:'exp_date',index:'exp_date',align:"center", hidden:true},
            {name:'cust_addr',index:'cust_addr',align:"center", hidden:true},
            {name:'link_name',index:'link_name',align:"center", hidden:true},
            {name:'link_phone',index:'link_phone',align:"center", hidden:true}
        ],
        //rownumbers:true,//添加左侧行号
       
        viewrecords: true,//是否在浏览导航栏显示记录总数
        rowNum:10,//每页显示记录数
        rowList:[5,10,15,20],//用于改变显示行数的下拉列表框的元素数组。
        jsonReader:{
        	page: "page", 
            total: "total", 
            records: "records", 
        	root: "rows",
            repeatitems : false
        },
        pager:$('#gridPager'),
        loadComplete: function (data) {
        	var result = data.result;
        	if( result == "fail" ){
				clear();
				$.alert("没有与查询条件匹配的结果！");
			}
        }
    });
	
	//点击查询按钮
	$("#search").click(function(){
		var phone = $("#phone").val();
		var plattype = $("#plat_type").val();
		var auditflag = $("#audit_flag").val();
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		var searchParameter = new Object();
		var flag = false;
		if( phone!="" ){
			var phoneRegex = /^[0|1]\d{10}$/;//手机号验证
			 if (!phoneRegex.test(phone) || phone.length !=11 ) {
				 $.alert("请输入完整并正确的手机号码!");
				 return
		     }
			 else{
				 flag=true;
			 }
			searchParameter.device_number = phone;
		}
		if(plattype!=""){
			searchParameter.plat_type = plattype;
		}
		if(auditflag!=""){
			searchParameter.audit_flag = auditflag;
		}
		if( startDate!="" ){
			searchParameter.start_date = startDate + " 00:00:00";
			flag=true;
		}
		if( endDate!="" ){
			searchParameter.end_date = endDate + " 23:59:59";
			flag=true;
		}
		
		$('#rlist').jqGrid('setGridParam',{
			datatype:'json',
			postData:searchParameter
		}).trigger('reloadGrid');
		
		//$("#audit_flag").val("0");
		//$("#plat_type").val("1");
		//$("#phone").val("");
		//$("#startDate").val("");
		//$("#endDate").val("");
		var postData = $('#rlist').jqGrid("getGridParam", "postData");
		$.each(postData, function (k, v) {
            delete postData[k];
        });
	});
	
	$("#exportExcel").click(function(){
		exportExcel();
	});
});

function clear(){
	$("#audit_flag").val("0");
	$("#plat_type").val("1");
	$("#phone").val("");
	$("#startDate").val("");
	$("#endDate").val("");
	var selectedId = $("#row_id").val();
	if(selectedId != null && selectedId != ""){
		$("#rlist").jqGrid("delRowData", selectedId);
	}
}

/**
 *导出函数
 */
function exportExcel(){
    var colNames = "cert_name,cert_id,cert_addr,device_number,order_id,oper_date,plat_type_name,audit_flag";
    post(application.fullPath + "authority/realNameReport/qryRealNameReportExcel",colNames);
}

function post(url,cols){
    var params = {};
    params.device_number = $("#phone").val();
    params.plat_type = $("#plat_type").val();
    params.audit_flag = $("#audit_flag").val();
    var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	if( startDate!="" ){
		params.start_date = startDate + " 00:00:00";
	}
	if( endDate!="" ){
		params.end_date = endDate + " 23:59:59";
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
}
function formatDate(date){
	if(date&&date.match(/^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/)){
		return date.split("-").join("");
	}else{
		return "";
	}
}
</script>
</head>
<body>
	<div class="show">
        <div class="box box_long" >
        		<div class="table_box">
			        <table width="980" border="0" align="center" cellpadding="0" cellspacing="0">
			          <tr>
			            <td width="320" align="center">
			            	<div class="left_data_nx" style="padding-left: 12px;">
        					手机号码：<input type="text" id="phone" name="" value=""></input>
        					</div>
        				</td>
			            <td width="320"><div class="left_data_nx">
        					来源渠道：<t:select codeType="plat_type" id="plat_type"  style="width:155px"></t:select>
        					</div>
        				</td>
			            <td width="320"><div class="left_data_nx">
        					审核状态：<t:select codeType="audit_flag" id="audit_flag" style="width:155px"></t:select>
        					</div>
        				</td>
			          </tr>
			          <tr>
			            <td width="320">
			            	<div class="left_data_nx" style="padding-left: 12px;">
        					开始日期：<input type="text" id="startDate" readonly="readonly" onfocus="WdatePicker({maxDate: '#F{$dp.$D(\'endDate\')}'});"></input>
        					</div>
        				</td>
			            <td width="320" align="center">
			            	<div class="left_data_nx">
        					结束日期：<input type="text" id="endDate" readonly="readonly" onfocus="WdatePicker({minDate: '#F{$dp.$D(\'startDate\')}'});"></input>
        					</div>
        				</td>
			          </tr>
			        </table>
			    </div>
			</div>
			<div class="dashed">
			  	<a href="javascript:void(0)" id="search">查  询</a>
			  	<a href="javascript:void(0)" id="exportExcel">导  出</a>
			</div>
			<div class="box box_long" style="padding:0 0 10px 0;height:320px;">
				<input type="hidden" id="row_id" value=""/>
				<table id="rlist"></table>
				<div id="gridPager"></div>
			</div>
			<div class="clear"></div>
		</div>
</body>
</html>