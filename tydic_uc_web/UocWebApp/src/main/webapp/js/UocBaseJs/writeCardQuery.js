var jsession_id = "";
var rest_host = "";
var total_pages = 0;

$(document).ready(function() {
	jsession_id = window.parent.operInfo.jsession_id;
	rest_host = window.parent.operInfo.rest_host;

	//当前部门
	$('#accept_depart_no').text(window.parent.operInfo.depart_name);

	$('#searchBtn').on('click', function(e) {
		getResultList();
    });

	//ctrl+点击数据事件
    $('#serv_order_list').on('click', 'tr', function(e) {
		var $this = $(this);
		if(e.ctrlKey){
			jumpOrderDetail($this.attr('order_no'),$this.attr('tache_code'),$this.attr('finish_flag'));
		}
	});

	// 跳到首页
	$('#pageFirst').on('click', function(e) {
		var page_no = "1";
		$("#pageNo").val(page_no);
		getResultList();
	});
	// 跳到上一页
	$('#pageUp').on('click', function(e) {
		if($("#pageNo").val() == "1"){
			return;
		}else{
			var page_no = parseInt($("#pageNo").val()) - 1;
			$("#pageNo").val(page_no);
			getResultList();
		}
	});
	// 跳到下一页
	$('#pageDown').on('click', function(e) {
		if($("#pageNo").val() == $("#totalPage").text()){
			return;
		}else{
			var page_no = parseInt($("#pageNo").val()) + 1;
			$("#pageNo").val(page_no);
			getResultList();
		}
	});
	// 跳到尾页
	$('#pageLast').on('click', function(e) {
		var page_no = total_pages;
		$("#pageNo").val(page_no);
		getResultList();
	});

	$("#pageSize").change(function(e){
		$("#pageNo").val("1");
		getResultList();
	});

	getResultList();
});


function getResultList(){
	var accept_time_start=$('#time_start').val();
	var accept_time_end=$('#time_end').val();

	if (accept_time_start == '') {
		var nowdays = new Date();
	    var year = nowdays.getFullYear();
	    var month = nowdays.getMonth();
	    if(month==0){
	        month=12;
	        year=year-1;
	    }
	    if (month < 10) {
	        month = "0" + month;
	    }
	    accept_time_start = year + "-" + month + "-" + "01"+" 00:00:00";//上个月的第一天
	}else{
		accept_time_start = accept_time_start+" 00:00:00";
	}

	if (accept_time_end == '') {
		accept_time_end=new Date().format('yyyy-MM-dd hh:mm:ss');
	}else{
		accept_time_end = accept_time_end+" 23:59:59";
	}

	var sta_str = accept_time_start.replace(/-/g,"/");
	var sta_date = new Date(sta_str);
	var end_str = accept_time_end.replace(/-/g,"/");
	var end_date = new Date(end_str);

	var num = (end_date-sta_date)/(1000*3600*24);//求出两个时间的时间差,天数
	var days = parseInt(Math.ceil(num));//转化为整天

	if (days>62) {
		$.message.info('开始时间和结束时间不能跨两个月');
		return;
	}

	$('#serv_order_list').empty();
	var data = {
			jsession_id: jsession_id,
			serv_order_no: $('#order_no').val(),
			sim_id_begin: $('#start_no').val(),
			sim_id_end: $('#end_no').val(),
			acc_nbr: $('#dev_no').val(),
			cust_name: $('#cust_name').val(),
			accept_time_start: accept_time_start,
			accept_time_end: accept_time_end,
			write_card_result: $('#query_type').val(),//查询类型0-处理成功  1-写卡失败  2-发货失败
			page_no: $("#pageNo").val(),
			page_size: $("#pageSize").val()
		};

	$.ajax({
		type: "post",
		url: rest_host+"rest/writeCardResult/getWriteCardResult",
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
				var logHtml="";

				if (data.args.write_card_log_list.write_card_log_list != null && data.args.write_card_log_list.write_card_log_list.length>0) {
					var logList = data.args.write_card_log_list.write_card_log_list;
					$.each(logList, function(i, item) {
						logHtml+=createLogHtml(item);
					});

					total_pages = parseInt((data.args.write_card_log_list.total_num + (data.args.write_card_log_list.page_size - 1)) / data.args.write_card_log_list.page_size) ;
					$("#totalCount").html(data.args.write_card_log_list.total_num);
				}

				$("#totalPage").html(total_pages);
				$('#serv_order_list').append(logHtml);
			} else {
				$.message.error('获取写卡结果失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取写卡结果Ajax请求失败!');
		},
		complete: function(){
			$.loading.hide();
		}
	});
}

function createLogHtml(item){
	var resultType = "";
	var tache_code = "";
	if(item.write_card_result=="0"){
		resultType = "写卡发货成功";
		tache_code = "S00017";
	}else if(item.write_card_result=="1"){
		resultType = "写卡失败,未发货";
		tache_code = "S00005";
	}else if(item.write_card_result=="2"){
		resultType = "写卡成功,发货失败";
		tache_code = "S00013";
	}

	var html='<tr order_no="'+item.serv_order_no+'" tache_code="'+tache_code+'" finish_flag="0">'
			+'	<td class="width210 text-left">'+item.serv_order_no+'</td>'
			+'	<td class="width100 text-left">'+item.sim_id+'</td>'
			+'	<td>'+item.cust_name+'</td>'
			+'	<td>'+item.acc_nbr+'</td>'
			+'	<td class="width140 text-left">'+item.create_time+'</td>'
			+'	<td>'+item.accept_oper_no+'</td>'
			+'	<td>'+resultType+'</td>'
			+'	<td class="width300 text-left">'+(INPUT_UTIL.isNull(item.failed_desc)?"":item.failed_desc)+'</td>'
			+'</tr>';
	return html;
}

//查看订单详情
function jumpOrderDetail(order_no,tache_code,finish_flag){
	window.parent.openMenu({
		menu_id: 'frameQueryOrderDetail',
		label: '订单详情',
		url: 'jspPage/UocBase/orderDetail.jsp?jsession_id='+jsession_id+'&order_no=' + order_no+"&tache_code="+tache_code+"&finish_flag="+finish_flag
	}, true);
}