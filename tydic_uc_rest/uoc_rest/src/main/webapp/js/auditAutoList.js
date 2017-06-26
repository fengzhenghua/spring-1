$(document).ready(function () {
	//查询点击事件
	$("#confirm").unbind().bind("click",function(){
		autoTerminalList();
	 	$("#result_place").show();
	 	$("#dashed_excel").show();
	});
	
	//导出事件
	$("#export").click(rpt_export);
	
	//重置事件
	$("#reset").unbind().bind("click",function(){
		$("#result_place").hide();
		$("#dashed_excel").hide();
	    $("#date_begin").val(dateTime.nowDate);
	    $("#date_end").val(dateTime.nowDate);
	});
	$("#result_place").hide();
	$("#dashed_excel").hide();
});

//点击 查询 处理函数
function autoTerminalList(){
	var begin_date=formatDate($("#date_begin").val());
	var end_date=formatDate($("#date_end").val());
		 var grid=$("#rep_report_list").ligerGrid({
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
	             parms:[{name:"begin_date",value:begin_date},
	                    {name:"end_date",value:end_date}],
	             url: application.fullPath + "authority/rptSelfService/queryRptSelfServiceList",
	             height: 450, 
	             pageSize: 20,
	             dataAction:"local",
	             rownumbers: false  	
	      });
		 grid.loadData(true);
};

//对时间的处理
function formatDate(date) {
    if (date && date.match(/^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/)) {
        return date.split("-").join("");
    } else {
        return "";
    }
};

//导出函数
function rpt_export() {
    var colNames = "order_id,order_status,hall_name,oper_date,busi_name,cust_name,acc_nbr,tele_type,product_name,first_month_fee,activity_type_name,discnt_name,fee_all,pay_type";
    post(application.fullPath + "authority/rptSelfService/queryRptSelfServiceListExcel", colNames);
};
function post(url, cols) {
    var params = {};
    params.begin_date = formatDate($("#date_begin").val());
    params.end_date = formatDate($("#date_end").val());
    params.name_list=cols;
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


