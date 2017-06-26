var jsession_id="";
var rest_host="";

$(document).ready(function() {
	var operInfo = window.parent.operInfo;
	jsession_id = operInfo.jsession_id;
	rest_host = operInfo.rest_host;
	init();

	//查询订单明细报表
    queryOrderDetailData();

	//点击查询按钮
	$("#searchBtn").unbind("click").bind("click",function(){
		$("#pageNo").val("1");
		queryOrderDetailData();
	});

	//点击重置按钮
	$("#resetBtn").unbind("click").bind("click",function(){
		init();
	});

	// 跳到首页
	$('#pageFirst').on('click', function(e) {
		var page_no = "1";
		$("#pageNo").val(page_no);
		queryOrderDetailData();

	});
	// 跳到上一页
	$('#pageUp').on('click', function(e) {
		if($("#pageNo").val() == "1"){
			return;
		}else{
			var page_no = parseInt($("#pageNo").val()) - 1;
			$("#pageNo").val(page_no);
			queryOrderDetailData();
		}
	});
	// 跳到下一页
	$('#pageDown').on('click', function(e) {
		if($("#pageNo").val() == $("#totalPage").text()){
			return;
		}else{
			var page_no = parseInt($("#pageNo").val()) + 1;
			$("#pageNo").val(page_no);
			queryOrderDetailData();
		}
	});
	// 跳到尾页
	$('#pageLast').on('click', function(e) {
		var page_no = $("#totalPage").text();
		$("#pageNo").val(page_no);
		queryOrderDetailData();
	});

});

/**
 * 初始化条件
 */
function init(){
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
 * 订单明细报表查询
 */
function queryOrderDetailData(){

	var data={
			"jsession_id":jsession_id,
			"accept_time_start":$("#start_time").val()+" 00:00:00",
			"accept_time_end":$("#end_time").val()+" 23:59:59",
			"page_no": $("#pageNo").val(),
			"page_size": $("#pageSize").val(),
	};

	$.ajax({
		type: "post",
		url: rest_host+"rest/systemServiceRest/getOrderDetailReport",
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
				$("#report_list").empty();
				var orderList=data.args.json_info.servOrderList;
				if(orderList!=null&&orderList.length>0){
					var html="";
					$.each(orderList, function(i, orderData) {
						var servOrderNo = "";
						var servOrder_oper_code = "";
			    		var servOrder_oper_name = "";
			    		var servOrder_acc_nbr = "";
			    		var servOrder_cancle_flag = "";
			    		var servOrder_order_state = "";
			    		var servOrder_create_time = "";
			    		var servOrder_finish_time = "";
			    		var servOrder_proc_inst_id = "";
			    		var servOrder_accept_oper_no = "";
			    		var servOrder_accept_oper_name = "";
			    		var servOrder_accept_depart_no = "";
			    		var servOrder_accept_depart_name = "";
			    		var servOrder_accept_depart_type = "";

			    		var proc_name = "";
			    		var roam_level = "";
			    		var call_level = "";
			    		var proc_code = "";
			    		var first_month_rent = "";

						var cust_name = "";
						var cust_phone = "";
						var cust_address = "";
						var cert_no = "";
						var cert_address = "";
						var cert_type_name = "";
						var cert_expire_date = "";

						var develop_type = "";
			    		var develop_depart_name = "";
			    		var develop_depart_id = "";
			    		var develop_target_code = "";
			    		var developer_name = "";
			    		var developer_code = "";
			    		
//			    		if(orderData.serv_order_no != null){
//			    			servOrderNo=orderData.serv_order_no;
//			    			
//			    		}
						//服务订单发展人
				    	if(orderData.infoServiceOrderDeveloperList != null){
				    		var infoServiceOrderDeveloper = orderData.infoServiceOrderDeveloperList[0];
				    		develop_type = infoServiceOrderDeveloper.develop_type==null?"":infoServiceOrderDeveloper.develop_type;
				    		develop_depart_name = infoServiceOrderDeveloper.develop_depart_name==null?"":infoServiceOrderDeveloper.develop_depart_name;
				    		develop_depart_id = infoServiceOrderDeveloper.develop_depart_id==null?"":infoServiceOrderDeveloper.develop_depart_id;
				    		develop_target_code = infoServiceOrderDeveloper.develop_target_code==null?"":infoServiceOrderDeveloper.develop_target_code;
				    		developer_name = infoServiceOrderDeveloper.developer_name==null?"":infoServiceOrderDeveloper.developer_name;
				    		developer_code = infoServiceOrderDeveloper.developer_code==null?"":infoServiceOrderDeveloper.developer_code;
				    	}

				    	//服务订单基本信息
				    	if(orderData.infoServiceOrderList != null){
				    		var servOrder_all = orderData.infoServiceOrderList[0];
				    		servOrderNo = servOrder_all.serv_order_no;
				    		servOrder_oper_code = servOrder_all.oper_code == null ? "" : servOrder_all.oper_code;
				    		servOrder_oper_code = window.parent.Constant.operCode(servOrder_oper_code);
				    		servOrder_oper_name = servOrder_all.oper_name == null ? "" : servOrder_all.oper_name;
				    		servOrder_oper_name = servOrder_oper_code;
				    		servOrder_acc_nbr = servOrder_all.acc_nbr == null ? "" : servOrder_all.acc_nbr;

				    		servOrder_cancle_flag = servOrder_all.cancle_flag == null ? "" : servOrder_all.cancle_flag;
				    		servOrder_cancle_flag = servOrder_cancle_flag == "0" ? "已撤单" :"正常单";
				    		servOrder_order_state = servOrder_all.order_state == null ? "" : servOrder_all.order_state;
				    		servOrder_order_state = window.parent.Constant.orderState(servOrder_order_state);
				    		servOrder_create_time = servOrder_all.create_time == null ? "" : servOrder_all.create_time;

				    		servOrder_finish_time = servOrder_all.finish_time == null ? "" : servOrder_all.finish_time;
				    		servOrder_proc_inst_id = servOrder_all.proc_inst_id == null ? "" : servOrder_all.proc_inst_id;

				    		servOrder_accept_oper_no = servOrder_all.accept_oper_no == null ? "" : servOrder_all.accept_oper_no;
				    		servOrder_accept_oper_name = servOrder_all.accept_oper_name == null ? "" : servOrder_all.accept_oper_name;
				    		servOrder_accept_depart_no = servOrder_all.accept_depart_no == null ? "" : servOrder_all.accept_depart_no;

				    		servOrder_accept_depart_name = servOrder_all.accept_depart_name == null ? "" : servOrder_all.accept_depart_name;
				    		servOrder_accept_depart_type = servOrder_all.accept_depart_type == null ? "" : servOrder_all.accept_depart_type;
				    	}

				    	//服务订单产品列表
				    	if(orderData.infoServiceOrderProdMapList != null){
				    		var infoServiceOrderProdMap = orderData.infoServiceOrderProdMapList[0];
				    		proc_name = infoServiceOrderProdMap.prod_name==null?"":infoServiceOrderProdMap.prod_name;
				    		roam_level = infoServiceOrderProdMap.roam_level==null?"":infoServiceOrderProdMap.roam_level;
				    		call_level = infoServiceOrderProdMap.call_level==null?"":infoServiceOrderProdMap.call_level;
				    		proc_code = infoServiceOrderProdMap.prod_code==null?"":infoServiceOrderProdMap.prod_code;
				    		first_month_rent = infoServiceOrderProdMap.first_month_rent==null?"":infoServiceOrderProdMap.first_month_rent;
				    		first_month_rent=window.parent.Constant.first_month_rent(first_month_rent);
				    		$.each(window.parent.Constant.prodCode(),function(i,item){
				    			if(infoServiceOrderProdMap.prod_code == item.code_id){
				    				proc_name = item.code_name;
				    			}
				    		});
				    	}

				    	//客户个人信息表
				    	if(orderData.infoServiceOrderPersionList != null && orderData.infoServiceOrderPersionList.length>0){
				    		var infoServiceOrderPersion = orderData.infoServiceOrderPersionList[0];
				    		cust_name = infoServiceOrderPersion.cust_name;
							cust_phone = infoServiceOrderPersion.cust_phone;
							cust_address = infoServiceOrderPersion.cust_address;
							cert_no = infoServiceOrderPersion.cert_no;
							cert_address = infoServiceOrderPersion.cert_address;
				    		cert_expire_date = infoServiceOrderPersion.cert_expire_date==null?"":infoServiceOrderPersion.cert_expire_date;
				    		$.each(window.parent.Constant.certType(),function(i,item){
				    			if(infoServiceOrderPersion.cert_type == item.code_id){
				    				cert_type_name = item.code_name;
				    			}
				    		});
				    	}

						html +='<tr>'
							 +'<td width="8%">'+servOrderNo+'</td>'
							 +'<td width="8%">'+servOrder_oper_name+'</td>'
							 +'<td width="5%">'+servOrder_acc_nbr+'</td>'
							 +'<td width="3%">'+servOrder_cancle_flag+'</td>'
							 +'<td width="3%">'+servOrder_order_state+'</td>'
							 +'<td width="6%">'+servOrder_create_time+'</td>'
							 +'<td width="6%">'+servOrder_finish_time+'</td>'
						 	 +'<td width="3%">'+servOrder_accept_oper_no+'</td>'
							 +'<td width="3%">'+servOrder_accept_oper_name+'</td>'
							 +'<td width="6%">'+servOrder_accept_depart_no+'</td>'
							 +'<td width="3%">'+servOrder_accept_depart_name+'</td>'
							 +'<td width="4%">'+servOrder_accept_depart_type+'</td>'
							 +'<td width="3%">'+servOrder_proc_inst_id+'</td>'
							 +'<td width="5%">'+proc_name+'</td>'
							 +'<td width="5%">'+proc_code+'</td>'
							 +'<td width="8%">'+first_month_rent+'</td>'
						 	 +'<td width="3%">'+roam_level+'</td>'
							 +'<td width="3%">'+call_level+'</td>'
							 +'<td width="3%">'+cust_name+'</td>'
							 +'<td width="5%">'+cust_phone+'</td>'
							 +'<td width="15%">'+cust_address+'</td>'
							 +'<td width="5%">'+cert_type_name+'</td>'
							 +'<td width="7%">'+cert_no+'</td>'
							 +'<td width="10%">'+cert_address+'</td>'
							 +'<td width="5%">'+cert_expire_date+'</td>'
							 +'<td width="3%">'+developer_name+'</td>'
							 +'<td width="5%">'+developer_code+'</td>'
							 +'<td width="3%">'+develop_type+'</td>'
							 +'<td width="5%">'+develop_depart_name+'</td>'
							 +'<td width="5%">'+develop_depart_id+'</td>'
							 +'<td width="5%">'+develop_target_code+'</td>'
							 +'<tr>';
					});

					$("#totalCount").html(data.args.json_info.total_num);
					$("#totalPage").html(data.args.json_info.page_total);
					$("#report_list").append(html);
				}else{
					$("#totalCount").html("0");
					$("#totalPage").html("0");
				}
			} else {
				$.message.error('订单明细报表查询失败!');
			}

		},
		error: function(data) {
			$.message.error("订单明细报表查询Ajax请求失败!");
		},
		complete: function(){
			$.loading.hide();
		}
	});
}
