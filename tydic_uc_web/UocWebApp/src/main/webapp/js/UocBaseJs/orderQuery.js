var jsession_id;
var rest_host;
var total_pages;
var area_code;
var province_code;
var proc_code_list;
var system_code_list;
var order_state_code_list;
$(document).ready(function() {

	// 单选卡-关闭更多事件
	$('body').on('click', function(e) {
		var target = $(e.target);
		if (target.closest('.option_box').length == 0
				&& $('.option_box .more_option:visible').length > 0) {
			$('.option_box .more_option').addClass('hide');
		}
	});

	// 单选卡-更多事件
	$('.option_box').on('click', 'div[name="more"]', function(e) {
		var $this = $(this);
		if ($this.next().hasClass('hide')) {
			$('.option_box .more_option').addClass('hide');
			$this.next().removeClass('hide');
		} else {
			$this.next().addClass('hide');
		}
	});

	// 单选卡-选中事件
	$('.option_box').on('click', 'a', function(e) {
		var $this = $(this);
		if ($this.hasClass('active')) {
			$this.removeClass('active');
		} else {
			$this.parents('.option_box').find('a').removeClass('active');
			$this.addClass('active');
		}
	});

	// 竣工状态-选中事件
	$('.radio_box').on('click', function(e) {
		var $this = $(this);
		$this.find('[type="radio"]').prop('checked', true);
	});

	// 更多查询条件-展开/收起事件
	$('.more_condition_btn').on('click', function(e) {
		var $this = $(this);
		if ($this.attr('on_off') == 'off') {
			$this.attr('on_off', 'on');
			$this.find('span').text('收起');
			$this.find('i').text('▲');
			$this.parent().siblings('[control="foldable"]').removeClass('hide');
		} else {
			$this.attr('on_off', 'off');
			$this.find('span').text('更多选项');
			$this.find('i').text('▼');
			$this.parent().siblings('[control="foldable"]').addClass('hide');
		}
	});

	jsession_id = window.parent.operInfo.jsession_id;
	rest_host = window.parent.operInfo.rest_host;

	// 查询
	$('#searchBtn').on('click', function(e) {
		page_no = $("#pageNo").val("1");
		getData();
	});
	// 跳到首页
	$('#pageFirst').on('click', function(e) {
		page_no = "1";
		$("#pageNo").val(page_no);
		getData();

	});
	// 跳到上一页
	$('#pageUp').on('click', function(e) {
		if($("#pageNo").val() == "1"){
			return;
		}else{
			page_no = parseInt($("#pageNo").val()) - 1;
			$("#pageNo").val(page_no);
			getData();
		}
	});
	// 跳到下一页
	$('#pageDown').on('click', function(e) {
		if($("#pageNo").val() == $("#totalPage").text()){
			return;
		}else{
			page_no = parseInt($("#pageNo").val()) + 1;
			$("#pageNo").val(page_no);
			getData();
		}
	});
	// 跳到尾页
	$('#pageLast').on('click', function(e) {
		page_no = total_pages;
		$("#pageNo").val(page_no);
		getData();
	});

	// 重置
	$('#resetBtn').on('click', function(e) {
		//alert($(this).text());
		getTime();
		$("#pageNo").val("1");
		$("#order_code").val("");
		$("#cust_name").val("");
		$("#cert_no").val("");
		$("#order_no").val("");
		$("#acc_nbr").val("");
		$("#accept_oper_no").val("");
		$("#accept_oper_no").attr("no", "");
		$("#accept_depart_no").val("");
		$("#accept_depart_no").attr("no", "");
		$("#prod_code").val("");
		$("#prod_code").attr("no","");
		$("#oper_code").val("");
		$("#sim_id").val("");
//		if($("input[name='finishStatus']:checked").val() == "1"){
//			$("#check_doing").attr("checked",true);
//			//$("#check_finish").attr("checked",false);
//		}
		$("#accept_system a").each(function(){
			if($(this).attr('class') == "active" ){
				$(this).removeClass("active");
			}
		});
		$("#tache_code a").each(function(){
			if($(this).attr('class') == "active" ){
				$(this).removeClass("active");
			}
		});
//		$("#prod_code a").each(function(){
//			if($(this).attr('class') == "active" ){
//				$(this).removeClass("active");
//			}
//		});
		$("#pay_type a").each(function(){
			if($(this).attr('class') == "active" ){
				$(this).removeClass("active");
			}
		});
		$("#order_state a").each(function(){
			if($(this).attr('class') == "active" ){
				$(this).removeClass("active");
			}
		});
	});

	// 导出
	$('#exportBtn').on('click', function(e) {
		//alert($(this).text());
	});

	// 跳转到订单详情
	$('#orderList').on('click', 'tbody .link', function(e) {
		var $this = $(this);
		var orderId = $this.attr('order_id');
		var tache_code = $this.attr('tache_code');
		tache_code = tache_code==null?"":tache_code;
		window.parent.openMenu({
			menu_id: 'frameOrderDetail',
			label: '订单详情',
			//url: 'jspPage/UocBase/orderDetail.jsp?jsession_id='+jsession_id+'&order_no=' + orderId+'&finish_flag='+$("input[name='finishStatus']:checked").val()+"&tache_code="+tache_code
			url: 'jspPage/UocBase/orderDetail.jsp?jsession_id='+jsession_id+'&order_no=' + orderId+'&finish_flag='+ $this.attr('finish_flag') +"&tache_code="+tache_code
			
		}, true);
	});

	// 跳转页面
	$('#pageJump').on('keyup', function(e) {
		var $this = $(this);
		var reg = /^\d+(?=\.{0,1}\d+$|$)/;
		if (e.keyCode == '13') { // 回车
			if ($this.val().trim() == '') {
				//alert('页数不能为空');
				$.message.info('页数不能为空~');
				return;
			} else if (!reg.test($this.val())) {
				//alert('请输入正整数');
				$.message.info('请输入正整数~');
				return;
			}
			//alert('跳转到' + $this.val() + '页');
			$.message.info('跳转到' + $this.val() + '页');
		}
	});

	// 受理工号模态框属性设置
	$.modal('#acceptOperNoModal', {
        width: 550,
        height: 350
    });
	// 弹出受理工号模态框
	$('#accept_oper_no').on('click', function(e) {
		$('#acceptOperNoModal').show();
	});
	// 受理工号模态框-确定按钮事件
	$('#acceptOperNoConfirmBtn').on('click', function(e) {
		var activeNo = $('#acceptOperNoList a.active');
		if (activeNo.length > 0) {
			$('#accept_oper_no').val(activeNo.text());
			$('#accept_oper_no').attr('no', activeNo.attr('no'));
		} else {
			$('#accept_oper_no').val('');
			$('#accept_oper_no').attr('no', '');
		}
		$('#acceptOperNoModal').hide();
	});
	// 受理工号模态框-取消按钮事件
	$('#acceptOperNoCancelBtn').on('click', function(e) {
		$('#acceptOperNoModal').hide();
	});
	// 受理工号模态框-查询按钮事件
	$('#acceptOperNoSearchBtn').on('click', function(e) {
		page_no = $("#pageNo").val("1");
		getData();
		$('#acceptOperNoModal').hide();
		$("#accept_oper_no").attr("no","");
	});

	// 受理部门模态框属性设置
	$.modal('#acceptDepartNoModal', {
        width: 550,
        height: 350
    });
	// 弹出受理部门模态框
	$('#accept_depart_no').on('click', function(e) {
		$('#acceptDepartNoModal').show();
	});
	// 受理部门模态框-确定按钮事件
	$('#acceptDepartNoConfirmBtn').on('click', function(e) {
		var activeNo = $('#acceptDepartNoList a.active');
		if (activeNo.length > 0) {
			$('#accept_depart_no').val(activeNo.text());
			$('#accept_depart_no').attr('no', activeNo.attr('no'));
		} else {
			$('#accept_depart_no').val('');
			$('#accept_depart_no').attr('no', '');
		}
		$('#acceptDepartNoModal').hide();
	});
	// 受理部门模态框-取消按钮事件
	$('#acceptDepartNoCancelBtn').on('click', function(e) {
		$('#acceptDepartNoModal').hide();
	});
	// 受理部门模态框-查询按钮事件
	$('#acceptDepartNoSearchBtn').on('click', function(e) {
		getDepartments($('#depart_no_search').val());
	});

	// 产品模态框属性设置
	$.modal('#prodCodeModal', {
        width: 820,
        height: 350
    });
	// 弹出受理部门模态框
	$('#prod_code').on('click', function(e) {
		$('#prodCodeModal').show();
	});
	// 受理部门模态框-确定按钮事件
	$('#prodCodeConfirmBtn').on('click', function(e) {
		var activeNo = $('#prodCodeList a.active');
		if (activeNo.length > 0) {
			$('#prod_code').val(activeNo.text());
			$('#prod_code').attr('no', activeNo.attr('no'));
		} else {
			$('#prod_code').val('');
			$('#prod_code').attr('no', '');
		}
		$('#prodCodeModal').hide();
	});
	// 受理部门模态框-取消按钮事件
	$('#prodCodeCancelBtn').on('click', function(e) {
		$('#prodCodeModal').hide();
	});
	// 受理部门模态框-查询按钮事件
	$('#prodCodeSearchBtn').on('click', function(e) {
		page_no = $("#pageNo").val("1");
		getData();
		$('#prodCodeModal').hide();
		$("#prod_code").attr("no","");
	});

	$("#check_doing").bind("click",function(){
		$("#check_doing").attr("checked","checked");
	});
	$("#check_finish").bind("click",function(){
		$("#check_finish").attr("checked","checked");
	});


	//受理工号
	getSelectOper();

	//受理系统
	system_code_list = window.parent.Constant.acceptSystem();
	dealCodeList(system_code_list,"acceptSystem");
	//环节
	getTacheCodes();

	//部门
	getDepartments("");

	//订单状态
	order_state_code_list = window.parent.Constant.orderState();
	dealCodeList(order_state_code_list,"orderState");

	//省份/区域
	var province_code_list = window.parent.Constant.provinceCode();
	dealCodeList(province_code_list,"provinceCode");

	//业务类型
	var oper_code_list = window.parent.Constant.operCode();
	dealCodeList(oper_code_list,"operCode");

	//产品
	proc_code_list = window.parent.Constant.prodCode();
	dealCodeList(proc_code_list,"prodCode");

	//支付方式
	var pry_type_code_list = window.parent.Constant.payType();
	dealCodeList(pry_type_code_list,"payType");

	getTime();
	getData();

	$("#pageSize").change(function(e){
		$("#pageNo").val("1");
		getData();
	});
});

function getTime(){
    var curr_time = new Date();
    var startDate = curr_time.getFullYear() + "-";
    //startDate += curr_time.getMonth() + 1 + "-01";
    var month = curr_time.getMonth();
    if(month==0){
        month=12;
        year=year-1;
    }
    if (month < 10) {
        month = "0" + month;
    }
    startDate += month + "-01";
    var endDate = curr_time.format('yyyy-MM-dd');
    $("#accept_time_start").val(startDate);
    $("#accept_time_end").val(endDate);
}

function getData() {
	var accept_time_start = $('#accept_time_start').val()+" 00:00:00";
	var accept_time_end = $('#accept_time_end').val()+" 23:59:59";
	var start_month = accept_time_start.substring(5,7);
	var end_month = accept_time_end.substring(5,7);
	if(start_month.substring(0,1) == "0"){
		start_month = start_month.substring(1,2);
	}
	if(end_month.substring(0,1) == "0"){
		end_month = end_month.substring(1,2);
	}
	var curr_time = new Date();
	var d1 = new Date(accept_time_start.replace(/\-/g, "\/"));
	var d2 = new Date(accept_time_end.replace(/\-/g, "\/"));
//	if(start_month == end_month || (Number(start_month) + 1) == (Number(end_month)) || (start_month =="12" && end_month == "1")){
//	}else{
//		$.message.info( '开始时间和结束时间跨度不能超过两个月~' );
//	    return;
//	}
	if (accept_time_start == null ) {
		$.message.info('请重新选择开始时间~');
		return;
	}
	if (accept_time_end == null) {
		$.message.info('请重新选择结束时间~');
		return;
	}

	if(d1 > d2){
		$.message.info('请重新选择时间~');
		return;
	}
	var data = {
		jsession_id: jsession_id,
		serv_order_no: $('#order_no').val(),
		cert_no: $('#cert_no').val(),
		cust_name: $('#cust_name').val(),
		acc_nbr: $('#acc_nbr').val(),
		accept_time_start: accept_time_start,
		accept_time_end: accept_time_end,
		area_code: area_code,
		province_code: province_code,
		accept_depart_no: $('#accept_depart_no').attr('no'),
		order_state: $("#order_state a.active").attr('code_id'),
		accept_oper_no: $('#accept_oper_no').attr('no'),
		//finish_flag: $("input[name='finishStatus']:checked").val(),
		prod_code:$("#prod_code").attr('no'),
		oper_code:$("#oper_code").val(),
		tache_code:$("#tache_code a.active").attr('tache_code'),
		pay_type:$("#pay_type a.active").attr('code_id'),
		accept_system:$("#accept_system a.active").attr('code_id'),
		sim_id:$("#sim_id").val(),
		sort_type: $("input[name='timeSort']:checked").val(),
		page_no: $("#pageNo").val(),
		page_size: $("#pageSize").val()
	};
	var str = JSON.stringify(data);
//	console.info("str in=" + str);
	$.ajax({
		type: "post",
		//TODO fengzhenghua
		//url: rest_host+"rest/saleOrder/queryServOrderList",
		url: rest_host+"rest/systemServiceRest/getServOrderListES",
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
				$("#totalCount").html("0");
				$("#totalPage").html("0");
				if(data.args.info_serv_order_list.info_serv_order != null && data.args.info_serv_order_list.info_serv_order.length > 0){
					var serOrderInfo = data.args.info_serv_order_list;
					var servOrderList = data.args.info_serv_order_list.info_serv_order;
					$("#totalCount").html(serOrderInfo.total_num);
					//int totalPages = (totalNume + (pageSize -1)) / pageSize;
					total_pages = parseInt((serOrderInfo.total_num + (serOrderInfo.page_size - 1)) / serOrderInfo.page_size) ;
					$("#totalPage").html(total_pages);
					var servOrderHtml = "";
					var accept_no;
					var pay_fee;
					var pay_type;
					var prod_code;
					var accept_system;
					var order_state;
					var finish_flag;
					$.each(servOrderList,function(i,item){
						accept_no = item.accept_no==null?"":item.accept_no;
						pay_fee = item.pay_fee==null?"":item.pay_fee;
						pay_type = item.pay_type==null?"":item.pay_type;
						prod_code = item.prod_code==null?"":item.prod_code;
						accept_system = item.accept_system==null?"":item.accept_system;
						order_state = item.order_state==null?"":item.order_state;
						finish_flag = order_state=="211" || order_state=="302"?"1":"0";
						$.each(system_code_list,function(i,item){
							if(accept_system == item.code_id){
								accept_system = item.code_name;
							}
						});
						$.each(proc_code_list,function(i,item){
							if(prod_code == item.code_id){
								prod_code = item.code_name;
							}
						});
						$.each(order_state_code_list,function(i,item){
							if(order_state == item.code_id){
								order_state = item.code_name;
							}
						});
						servOrderHtml +='<tr>'
									  +'<td class="link" title="点击查看详情" order_id='+item.serv_order_no+' tache_code='+item.tache_code+' finish_flag='+finish_flag+' cert_no='+item.cert_no+' cust_name='+item.cust_name+'>'
									  +'<p>订单ID：'+item.serv_order_no+'</p>'
									  +'<p>订单编码：'+accept_no+'</p>'
									  +'<p>受理系统：'+accept_system+'</p>'
									  //+'<p>受理时间：'+item.accept_time+'</p>'
									  +'<p>受理时间：'+item.create_time+'</p>'
									  +'</td>'
									  +'<td>'
									 /* +'<p>'+prod_code+'</p>'*/
									  +'<p>'+prod_code+'</p>'
									  +'<p>'+item.acc_nbr+'</p>'
									  +'</td>'
									  +'<td class="text_center">'
									  +'<p>'+pay_fee+'</p>'
									  +'<p>'+pay_type+'</p>'
									  +'</td>'
									  +'<td>'
									  +'<p>客户名称：'+item.cust_name+'</p>'
									  +'<p>证件号码：'+item.cert_no+'</p>'
									  +'</td>'
									  +'</tr>';
					});
					$("#serv_order_list").empty();
					$("#serv_order_list").append(servOrderHtml);
				}else{
					$.message.info('无服务订单~');
					$("#serv_order_list").empty();
				}
			} else {
				$.message.error('查询失败!');
				$("#serv_order_list").empty();
				$("#pageNo").val("1");
			}

		},
		error: function(data) {
			$.message.error("查询失败!");
			$("#serv_order_list").empty();
		},
		complete: function(){
			$.loading.hide();
		}
	});
}

//获取code_list表
function getCodeList(type_code){
	var departData = {
			jsession_id: jsession_id,
			type_code: type_code
		};
	$.ajax({
		type: "post",
		url: rest_host+"rest/ArtificialTaskRest/getTaskQueryInfo",
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
				if (data.args.code_list != null && data.args.code_list.length>0) {
					code_list = data.args.code_list;
					dealCodeList(code_list,type_code);
				}
			} else {
				$.message.error('获取产品失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取产品Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
}

function dealCodeList(code_list,type_code){
	if (code_list != null && code_list.length>0) {
		if(type_code == "acceptSystem"){
			$.each(code_list,function(i,item){
				var html = '<a code_id="' + item.code_id + '">' + item.code_name + '</a>';
				if (i == 3) {
					var more = '<div class="btn btn_link vertical_baseline" name="more">更多</div><div class="more_option hide"></div>';
					$("#accept_system").append(more);
				}
				if (i < 3) {
					$("#accept_system").append(html);
				} else {
					$("#accept_system .more_option").append(html);
				}
			});
			system_code_list = code_list;
		}
		else if(type_code == "operCode"){
			var operCodeHtml = '';
			operCodeHtml ='<option value="" ></option>';
			$.each(code_list,function(i,item){
				operCodeHtml +='<option value="'+item.code_id+'" data=\'' + JSON.stringify(item) + '\'>'+item.code_name+'</option>';
			});
			$("#oper_code").append(operCodeHtml);
		}
		else if(type_code == "orderState"){
			$.each(code_list,function(i,item){
				var html = '<a code_id="' + item.code_id + '">' + item.code_name + '</a>';
				if (i == 3) {
					var more = '<div class="btn btn_link vertical_baseline" name="more">更多</div><div class="more_option hide"></div>';
					$("#order_state").append(more);
				}
				if (i < 3) {
					$("#order_state").append(html);
				} else {
					$("#order_state .more_option").append(html);
				}
			});
		}
		else if(type_code == "provinceCode"){
			var provinceCodeHtml = '';
			$.each(code_list,function(i,item){
				provinceCodeHtml +='<option value="'+item.code_id+'" data=\'' + JSON.stringify(item) + '\'>'+item.code_name+'</option>';
			});
			$("#province_code").append(provinceCodeHtml);
		}
		else if(type_code == "prodCode"){
//			$.each(code_list,function(i,item){
//				var html = '<a code_id="' + item.code_id + '">' + item.code_name + '</a>';
//				if (i == 3) {
//					var more = '<div class="btn btn_link vertical_baseline" name="more">更多</div><div class="more_option hide"></div>';
//					$("#prod_code").append(more);
//				}
//				if (i < 3) {
//					$("#prod_code").append(html);
//				} else {
//					$("#prod_code .more_option").append(html);
//				}
//			});
			$('#prodCodeList').empty();
			var prodCodeHtml='';
		        proc_code_list = code_list;
			$.each(proc_code_list,function(i,item){
				prodCodeHtml += '<span class="one_third" id="'+item.code_id+'" name="'+item.code_name+'"><a no="' + item.code_id + '">' + item.code_name + '</a></span>';
			});
			$("#prodCodeList").append(prodCodeHtml);
			$.each(proc_code_list,function(i,item) {
				$("#"+item.code_id).click(function(){
					$("#prod_code_search").val($(this).attr("name"));
					$('#prod_code').attr('no', this.id);
				});
			});
		}
		else if(type_code == "payType"){
			$.each(code_list,function(i,item){
				var html = '<a code_id="' + item.code_id + '">' + item.code_name + '</a>';
				if (i == 3) {
					var more = '<div class="btn btn_link vertical_baseline" name="more">更多</div><div class="more_option hide"></div>';
					$("#pay_type").append(more);
				}
				if (i < 3) {
					$("#pay_type").append(html);
				} else {
					$("#pay_type .more_option").append(html);
				}
			});
		}
	}
}

//获取环节
function getTacheCodes(){
	var tachetData = {
			jsession_id:jsession_id
		};
	$.ajax({
		type: "post",
		url: rest_host+"rest/getInfoService/getOptionalTache",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: tachetData,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				if (data.args.TacheList != null && data.args.TacheList.length>0) {
					$.each(data.args.TacheList,function(i,item){
						var html = '<a tache_code="' + item.tache_code + '">' + item.tache_name + '</a>';
						if (i == 3) {
							var more = '<div class="btn btn_link vertical_baseline" name="more">更多</div><div class="more_option hide"></div>';
							$("#tache_code").append(more);
						}
						if (i < 3) {
							$("#tache_code").append(html);
						} else {
							$("#tache_code .more_option").append(html);
						}
					});
				}
//				$('#tache_code .short_info_body').append(tacheHtml);
			} else {
				$.message.error('获取环节失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取环节Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
}

//获取部门
function getDepartments(departName){
	var departData = {
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
		data: departData,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				if(data.args.json_info!=''){
					$('#acceptDepartNoList').empty();
					var departHtml='';
					var depart_list = JSON.parse(data.args.json_info);
					$.each(depart_list.depart_list,function(i,item){
						departHtml += '<span class="width50per" id="'+item.dept_no+'" name="'+item.dept_name+'"><a no="' + item.dept_no + '">'+item.dept_no+' / ' + item.dept_name + '</a></span>';
					});
					$("#acceptDepartNoList").append(departHtml);
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

//获取可选工号
function getSelectOper(){
	var operData = {
			jsessionId: jsession_id,
			operNo: "",
			operName:""
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
			if (data.respCode=="0000" && data.args.code=="200") {
				$('#acceptOperNoList').empty();
				var oper_list = JSON.parse(data.args.json_info);
				var operHtml='';
				if (oper_list.OperInfo != null && oper_list.OperInfo.length>0) {
					$.each(oper_list.OperInfo, function(i, item) {
						operHtml += '<span class="one_third" id="'+item.oper_no+'"><a no="' + item.oper_no + '">' + item.oper_no + ' / '+ item.oper_name+'</a></span>';
					});
					$('#acceptOperNoList').append(operHtml);
					$.each(oper_list.OperInfo, function(i, item) {
						$("#"+item.oper_no).click(function(){
							$("#oper_no_search").val(this.id+' / '+item.oper_name);
							$('#accept_oper_no').attr('no', this.id);
						});
					});

				}else{
					$.message.info('无工号信息~'+data.content);
				}
			} else {
				$.message.error('获取可选工号失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取可选工号Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
}

