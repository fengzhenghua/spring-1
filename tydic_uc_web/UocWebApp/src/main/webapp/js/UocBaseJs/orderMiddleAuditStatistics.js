var jsession_id="";
var rest_host="";

$(document).ready(function() {
	var operInfo = window.parent.operInfo;
	jsession_id = operInfo.jsession_id;
	rest_host = operInfo.rest_host;
	init();
	loadOperNo();
    loadDepartNo();

    //ctrl+点击数据事件
    $('#report_list').on('click', 'tr', function(e) {
		var $this = $(this);
		if(e.ctrlKey){
			JumpOrderDetail($this.attr('order_no'),$this.attr('tache_code'),$this.attr('finish_flag'));
		}
	});

	// 单选卡-选中事件
	$('.option_box[type="radio"]').on('click', 'a', function(e) {
		var $this = $(this);
		if ($this.hasClass('active')) {
			$this.removeClass('active');
		} else {
			$this.parents('.option_box').find('a').removeClass('active');
			$this.addClass('active');
		}
	});

	// 受理工号模态框属性设置
	$.modal('#acceptOperNoModal', {
        width: 550,
        height: 350
    });
	// 弹出受理工号模态框
	var acceptOperNo = '';
	$('#accept_oper_no,#deal_oper_no').on('click', function(e) {
		acceptOperNo = $(this).attr('id');
		$('#acceptOperNoModal').show();
	});
	// 受理工号模态框-确定按钮事件
	$('#acceptOperNoConfirmBtn').on('click', function(e) {
		var inputBox = $('#' + acceptOperNo);
		inputBox.val('');
		inputBox.attr('no', '');
		var activeNo = $('#acceptOperNoList a.active');
		if (activeNo.length > 0) {
			inputBox.val(activeNo.text());
			inputBox.attr('no', activeNo.attr('no'));
		}
		$('#acceptOperNoModal').hide();
	});
	// 受理工号模态框-取消按钮事件
	$('#acceptOperNoCancelBtn').on('click', function(e) {
		$('#acceptOperNoModal').hide();
	});
	// 受理工号模态框-查询按钮事件
	$('#acceptOperNoSearchBtn').on('click', function(e) {
		loadOperNo($('#accept_oper_no_search').val());
	});


	// 受理部门模态框属性设置
	$.modal('#acceptDepartNoModal', {
        width: 550,
        height: 350
    });
	// 弹出受理部门模态框
	var acceptDepartNo = '';
	$('#accept_depart_no,#deal_depart_no').on('click', function(e) {
		acceptDepartNo = $(this).attr('id');
		$('#acceptDepartNoModal').show();
	});
	// 受理部门模态框-确定按钮事件
	$('#acceptDepartNoConfirmBtn').on('click', function(e) {
		var inputBox = $('#' + acceptDepartNo);
		inputBox.val('');
		inputBox.attr('no', '');
		var activeNo = $('#acceptDepartNoList a.active');
		if (activeNo.length > 0) {
			inputBox.val(activeNo.text());
			inputBox.attr('no', activeNo.attr('no'));
		}
		$('#acceptDepartNoModal').hide();
	});
	// 受理部门模态框-取消按钮事件
	$('#acceptDepartNoCancelBtn').on('click', function(e) {
		$('#acceptDepartNoModal').hide();
	});
	// 受理部门模态框-查询按钮事件
	$('#acceptDepartNoSearchBtn').on('click', function(e) {
		loadDepartNo($('#accept_depart_no_search').val());
	});

	// 导出
	$('#exportBtn').on('click', function(e) {
		exprotData();
	});
	//查询订单明细报表
    queryOrderAuditData();

	//点击查询按钮
	$("#searchBtn").unbind("click").bind("click",function(){
		$("#pageNo").val("1");
		queryOrderAuditData();
	});

	//点击重置按钮
	$("#resetBtn").unbind("click").bind("click",function(){
		init();
	});

	// 跳到首页
	$('#pageFirst').on('click', function(e) {
		var page_no = "1";
		$("#pageNo").val(page_no);
		queryOrderAuditData();
	});
	// 跳到上一页
	$('#pageUp').on('click', function(e) {
		if($("#pageNo").val() == "1"){
			return;
		}else{
			var page_no = parseInt($("#pageNo").val()) - 1;
			$("#pageNo").val(page_no);
			queryOrderAuditData();
		}
	});
	// 跳到下一页
	$('#pageDown').on('click', function(e) {
		if($("#pageNo").val() == $("#totalPage").text()){
			return;
		}else{
			var page_no = parseInt($("#pageNo").val()) + 1;
			$("#pageNo").val(page_no);
			queryOrderAuditData();
		}
	});
	// 跳到尾页
	$('#pageLast').on('click', function(e) {
		var page_no = $("#totalPage").text();
		$("#pageNo").val(page_no);
		queryOrderAuditData();
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

    $('#accept_oper_no').val("");
    $('#accept_oper_no').attr("no","");
    $('#accept_depart_no').val("");
    $('#accept_depart_no').attr("no","");
    $('#deal_oper_no').val("");
    $('#deal_oper_no').attr("no","");
    $('#deal_depart_no').val("");
    $('#deal_depart_no').attr("no","");

    var list=$('.option_box a.active');
	if (list.length > 0) {
		$.each(list, function(i, item) {
			$(item).removeClass('active');
		});
	}
}

function exprotData(){
	var paramStr="?jsession_id="+jsession_id+"&accept_time_start="+$("#start_time").val()+" 00:00:00"+"&accept_time_end="+$("#end_time").val()+" 23:59:59"+"&oper_code=open01,open02"
				+"&accept_oper_no="+$('#accept_oper_no').attr('no')+"&accept_depart_no="+$('#accept_depart_no').attr('no')+"&deal_oper_no="+$('#deal_oper_no').attr('no')+"&deal_depart_no="+$('#deal_depart_no').attr('no')+"&page_no=1&page_size="+$("#totalCount").text();

	var export_path = rest_host+"rest/exprotOrderRest/exportAuditOrderInfo"+paramStr;
	window.open(export_path);
}

/**
 * 订单审核报表查询
 */
function queryOrderAuditData(){

	var data={
			"jsession_id":jsession_id,
			"accept_time_start":$("#start_time").val()+" 00:00:00",
			"accept_time_end":$("#end_time").val()+" 23:59:59",
			"oper_code":"open01,open02",
			"accept_oper_no":$('#accept_oper_no').attr('no'),
			"accept_depart_no": INPUT_UTIL.isNull($('#accept_depart_no').attr('no'))?window.parent.operInfo.depart_no:$('#accept_depart_no').attr('no'),
			"deal_oper_no":$('#deal_oper_no').attr('no'),
			"deal_depart_no":$('#deal_depart_no').attr('no'),
			"page_no": $("#pageNo").val(),
			"page_size": $("#pageSize").val(),
	};

	$.ajax({
		type: "post",
		url: rest_host+"rest/exprotOrderRest/queryAuditOrderInfo",
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
				var orderList=data.args.info_serv_order_list.info_serv_order;
				if(orderList!=null&&orderList.length>0){
					var html="";
					$.each(orderList, function(i, orderData) {
//						var oper_code = orderData.oper_code == null ?"":orderData.oper_code;
				    	var serv_order_no =  orderData.serv_order_no==null?"":orderData.serv_order_no;
				    	var acc_nbr = orderData.acc_nbr == null ?"":orderData.acc_nbr;
				    	var create_time=orderData.create_time;
				    	var accept_date =orderData.create_time ==null?"":create_time.substring(0,10);
				    	var accept_time = orderData.create_time ==null ?"":create_time.substring(11,create_time.length);
				    	var accept_depart_name = orderData.accpet_depart_name==null?"":orderData.accpet_depart_name;
				    	var accept_oper_no =orderData.accept_oper_no ==null?"":orderData.accept_oper_no;
				    	var cust_name = orderData.cust_name==null?"":orderData.cust_name;
				    	var cust_phone = orderData.cust_phone==null?"":orderData.cust_phone;
				    	var cust_address = orderData.cust_address==null?"":orderData.cust_address;
				    	var cert_no =orderData.cert_no ==null ?"":orderData.cert_no;
				    	var deal_oper_no = orderData.deal_oper_no==null?"":orderData.deal_oper_no;
				    	var deal_date = orderData.deal_date==null?"":orderData.deal_date;
				    	var deal_time = orderData.deal_time==null?"":orderData.deal_time;
				    	var audit_state = orderData.audit_state==null?"":orderData.audit_state;

				    	var finish_flag=orderData.order_state=="211"?"1":"0";
						html +='<tr order_no="'+serv_order_no+'" tache_code="'+orderData.tache_code+'" finish_flag="'+finish_flag+'">'
//							 +'<td width="8%">'+(INPUT_UTIL.isNull(oper_code)?"":window.parent.Constant.operCode(oper_code))+'</td>'
							 +'<td width="8%">'+serv_order_no+'</td>'
							 +'<td width="5%">'+acc_nbr+'</td>'
							 +'<td width="5%">'+accept_date+'</td>'
							 +'<td width="3%">'+accept_time+'</td>'
							 +'<td width="6%">'+accept_depart_name+'</td>'
							 +'<td width="6%">'+accept_oper_no+'</td>'
						 	 +'<td width="3%">'+cust_name+'</td>'
							 +'<td width="3%">'+cust_phone+'</td>'
							 +'<td width="6%">'+cust_address+'</td>'
							 +'<td width="3%">'+cert_no+'</td>'
							 +'<td width="5%">'+(deal_oper_no=="Auto"?"Auto(系统自动处理)":deal_oper_no)+'</td>'
							 +'<td width="5%">'+deal_date+'</td>'
							 +'<td width="5%">'+deal_time+'</td>'
							 +'<td width="5%">'+audit_state+'</td>'
							 +'<tr>';
					});
					$("#totalCount").html(data.args.info_serv_order_list.total_num);
					$("#totalPage").html(parseInt((data.args.info_serv_order_list.total_num + (data.args.info_serv_order_list.page_size -1)) / data.args.info_serv_order_list.page_size));
					$("#report_list").append(html);
				}else{
					$("#totalCount").html(0);
					$("#totalPage").html(0);
				}
			} else {
				$.message.error('订单审核报表查询失败!');
			}

		},
		error: function(data) {
			$.message.error("订单报表查询Ajax请求失败!");
		},
		complete: function(){
			$.loading.hide();
		}
	});
}

//获取部门
function loadDepartNo(search_info){
	var depart_no = '';
	var depart_name = '';

	if(INPUT_UTIL.isNull(search_info)){
		search_info = '';
	}
	//判断是否为中文
	if(INPUT_UTIL.isChinese(search_info)){
		depart_name = search_info;
	}else{
		depart_no = search_info.toUpperCase();
	}

	var departData = {
		jsession_id: jsession_id,
		depart_no: depart_no,
		depart_name: depart_name,
		region_id: window.parent.operInfo.area_code,
		query_type: "0"
	};

	$.ajax({
		type: "post",
		url: rest_host+"rest/getInfoService/getDepartInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: departData,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				if(data.args.json_info!=''){
					var departInfo=JSON.parse(data.args.json_info).depart_list;
					var departHtml='<span class="width50per"><a no="System"> System / 系统自动处理</a></span>';
					if (departInfo != null && departInfo.length>0) {
						$.each(departInfo, function(i, item) {
							departHtml=departHtml+'<span class="width50per"><a no="'+item.dept_no+'">'+item.dept_no+' / '+item.dept_name+'</a></span>';
						});
					}
					$('#acceptDepartNoList').empty();
					$('#acceptDepartNoList').append(departHtml);
				}
			} else {
				$.message.error('获取部门失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取部门Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
}

//获取工号
function loadOperNo(search_info){
	var operNo = '';
	var operName = '';

	if(INPUT_UTIL.isNull(search_info)){
		search_info = '';
	}
	//判断是否为中文
	if(INPUT_UTIL.isChinese(search_info)){
		operName = search_info;
	}else{
		operNo = search_info.toUpperCase();
	}

	var operData = {
			jsessionId: jsession_id,
			operNo: operNo,
			operName: operName
		};

	$.ajax({
		type: "post",
		url: rest_host+"rest/getInfoService/getOperInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: operData,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				var operHtml='<span class="one_third"><a no="Auto"> Auto / 系统自动处理</a></span>';
				if(data.args.json_info!=''){
					var operInfo=JSON.parse(data.args.json_info).OperInfo;
					if (operInfo != null && operInfo.length>0) {
						$.each(operInfo, function(i, item) {
							operHtml=operHtml+'<span class="one_third"><a no="'+item.oper_no+'">'+item.oper_no +' / '+item.oper_name+'</a></span>';
						});
					}
					$('#acceptOperNoList').empty();
					$('#acceptOperNoList').append(operHtml);
				}
			} else {
				$.message.error('获取工号失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取工号Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
}

function JumpOrderDetail(order_no,tache_code,finish_flag){
	window.parent.openMenu({
		menu_id: 'frameOrderDetail',
		label: '订单详情',
		url: 'jspPage/UocBase/orderDetail.jsp?jsession_id='+jsession_id+'&order_no=' + order_no+"&tache_code="+tache_code+"&finish_flag="+finish_flag
	}, true);
}
