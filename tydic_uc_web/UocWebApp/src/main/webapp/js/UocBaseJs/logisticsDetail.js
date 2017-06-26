var jsession_id="";
var rest_host="";
var login_depart_no="";
var total_pages;

$(document).ready(function() {
	var operInfo = window.parent.operInfo;
	jsession_id = operInfo.jsession_id;
	rest_host = operInfo.rest_host;
	login_depart_no = operInfo.depart_no;

	init();

	//查询物流报表
//    queryLogisticsDetailData();

	//点击查询按钮
	$("#searchBtn").unbind("click").bind("click",function(){
		$("#pageNo").val("1");
		queryLogisticsDetailData();
	});

	//点击重置按钮
	$("#resetBtn").unbind("click").bind("click",function(){
		init();
	});

	// 跳到首页
	$('#pageFirst').on('click', function(e) {
		var page_no = "1";
		$("#pageNo").val(page_no);
		queryLogisticsDetailData();

	});
	// 跳到上一页
	$('#pageUp').on('click', function(e) {
		if($("#pageNo").val() == "1"){
			return;
		}else{
			var page_no = parseInt($("#pageNo").val()) - 1;
			$("#pageNo").val(page_no);
			queryLogisticsDetailData();
		}
	});
	// 跳到下一页
	$('#pageDown').on('click', function(e) {
		if($("#pageNo").val() == $("#totalPage").text()){
			return;
		}else{
			var page_no = parseInt($("#pageNo").val()) + 1;
			$("#pageNo").val(page_no);
			queryLogisticsDetailData();
		}
	});
	// 跳到尾页
	$('#pageLast').on('click', function(e) {
		var page_no = total_pages;
		$("#pageNo").val(page_no);
		queryLogisticsDetailData();
	});
});

/**
 * 初始化条件
 */
function init(){
	//取部门
//	getDepartInfo();
	//设置初始时间
	var curr_time = new Date();
    var year = curr_time.getFullYear();
    var month = curr_time.getMonth();
	var new_year = year;    //取当前的年份
    var new_month = month+1;//取下一个月的第一天，方便计算（最后一天不固定）
    if(month>11) {
      new_month -=12;
      new_year++;
    }
    var new_date = new Date(new_year,new_month,1);//取当年下个月中的第一天
    var last=(new Date(new_date.getTime()-1000*60*60*24)).getDate();//获取当月最后一天日期

    $("#start_time").val(new Date(year,month,1).format('yyyy-MM-dd'));
    $("#end_time").val(new Date(year,month,last).format('yyyy-MM-dd'));

}
/**
 * 获取部门信息
 */
function getDepartInfo(departName){
	$("#accept_depart_no").append('');
	var data={
			jsession_id: jsession_id,
			depart_no: "",
			depart_name: INPUT_UTIL.isNull(departName)?"":departName,
			region_id: window.parent.operInfo.area_code,
			query_type: "0"
	};

	$.ajax({
		type: "post",
		url: rest_host+"rest/getInfoService/getDepartInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				var code=data.args.code;
				if(code=="200"){
					var departList=JSON.parse(data.args.json_info).depart_list;
					$.each(departList, function(i, item) {
						if(login_depart_no==item.dept_no){
							$("#accept_depart_no").append('<option value="'+item.dept_no+'" selected = "selected">'+item.dept_name+'</option>');
						}else{
							$("#accept_depart_no").append('<option value="'+item.dept_no+'">'+item.dept_name+'</option>');
						}
					});
				}else{
					$.message.error('无部门数据!');
				}
			} else {
				$.message.error('获取部门失败!');
			}

		},
		error: function(data) {
			$.message.error("获取部门失败!");
		},
		complete: function(){
			$.loading.hide();
		}
	});
}

/**
 * 物流报表查询
 */
function queryLogisticsDetailData(){
	$("#totalCount").html("0");
	$("#totalPage").html("0");
	/*$("#report_list").html("");*/
	var data={
			"accept_depart_no":$("#accept_depart_no").val()==null||""||undefined?login_depart_no:$("#accept_depart_no").val(),
			"create_time_start":$("#start_time").val()+" 00:00:00",
			"create_time_end":$("#end_time").val()+" 23:59:59",
			"page_no": $("#pageNo").val(),
			"page_size": $("#pageSize").val(),
	};

	$.ajax({
		type: "post",
		url: rest_host+"rest/systemServiceRest/queryLogisticsReportData",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				var dataRsp=data.args.info_deliver_order_list;
				if(dataRsp!=null&&dataRsp.length>0){
					$("#totalCount").html(data.args.total_num);
					total_pages = parseInt((data.args.total_num + (data.args.page_size - 1)) / data.args.page_size) ;
					$("#totalPage").html(total_pages);
					$.each(dataRsp, function(i, item) {
						var province_code_report=item.province_code==null||undefined?"":item.province_code;
						var area_code_report=item.area_code==null||undefined?"":item.area_code;
						var sale_order_no_report=item.sale_order_no==null||undefined?"":item.sale_order_no;
						var deliver_state_report=item.deliver_state==null||undefined?"":item.deliver_state;
						var deliver_time_report=item.deliver_time==null||undefined?"":item.deliver_time;
						var logistics_no_report=item.logistics_no==null||undefined?"":item.logistics_no;
						var create_time_report=item.create_time==null||undefined?"":item.create_time;
						var accept_oper_no_report=item.accept_oper_no==null||undefined?"":item.accept_oper_no;
						var accept_depart_no_report=item.accept_depart_no==null||undefined?"":item.accept_depart_no;
						var goods_name_report=item.goods_name==null||undefined?"":item.goods_name;
						var contact_name_report=item.contact_name==null||undefined?"":item.contact_name;
						var address_report=item.address==null||undefined?"":item.address;
						var contact_tel_report=item.contact_tel==null||undefined?"":item.contact_tel;
						var send_name_report=item.send_name==null||undefined?"":item.send_name;
						var send_target_addr_report=item.send_target_addr==null||undefined?"":item.send_target_addr;
						var note_report=item.province_code==null||undefined?"":item.note;
						$("#report_list").append('<tr>'
								+'<td>'+province_code_report+'</td>'
								+'<td>'+area_code_report+'</td>'
								+'<td>'+sale_order_no_report+'</td>'
								+'<td>'+deliver_state_report+'</td>'
								+'<td>'+deliver_time_report+'</td>'
								+'<td>'+logistics_no_report+'</td>'
								+'<td>'+create_time_report+'</td>'
								+'<td>'+accept_oper_no_report+'</td>'
								+'<td>'+accept_depart_no_report+'</td>'
								+'<td>'+goods_name_report+'</td>'
								+'<td>'+contact_name_report+'</td>'
								+'<td>'+address_report+'</td>'
								+'<td>'+contact_tel_report+'</td>'
								+'<td>'+send_name_report+'</td>'
								+'<td>'+send_target_addr_report+'</td>'
								+'<td>'+note_report+'</td>'
								+'<tr>');
					});
				}else{
					$.message.error('没有查询到数据!');
				}
			} else {
				$.message.error('物流报表查询失败!');
			}

		},
		error: function(data) {
			$.message.error("物流报表查询失败!");
		},
		complete: function(){
			$.loading.hide();
		}
	});
}
