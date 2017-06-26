var jsession_id;
var rest_host;
var total_pages;
var finish_flag;
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
		getArtificialTaskList();
	});
	// 跳到首页
	$('#pageFirst').on('click', function(e) {
		page_no = "1";
		$("#pageNo").val(page_no);
		getArtificialTaskList();

	});
	// 跳到上一页
	$('#pageUp').on('click', function(e) {
		if($("#pageNo").val() == "1"){
			return;
		}else{
			page_no = parseInt($("#pageNo").val()) - 1;
			$("#pageNo").val(page_no);
			getArtificialTaskList();
		}
	});
	// 跳到下一页
	$('#pageDown').on('click', function(e) {
		if($("#pageNo").val() == $("#totalPage").text()){
			return;
		}else{
			page_no = parseInt($("#pageNo").val()) + 1;
			$("#pageNo").val(page_no);
			getArtificialTaskList();
		}
	});
	// 跳到尾页
	$('#pageLast').on('click', function(e) {
		page_no = total_pages;
		$("#pageNo").val(page_no);
		getArtificialTaskList();
	});

	// 重置
	$('#resetBtn').on('click', function(e) {
		getTime();
		$("#pageNo").val("1");
		$("#order_code").val("");
		$("#cust_name").val("");
		$("#cert_no").val("");
		$("#order_no").val("");
		$("#acc_nbr").val("");
		$("#accept_oper_no").val("");
		$("#accept_depart_no").val("");
		$("#oper_code").val("");
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
		$("#prod_code a").each(function(){
			if($(this).attr('class') == "active" ){
				$(this).removeClass("active");
			}
		});
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

	});

	// 跳转到订单详情
	$('#taskList').on('click', 'tbody .link', function(e) {
		var $this = $(this);
		var orderId = $this.attr('order_id');
		var tache_code = $this.attr('tache_code');
		var late_flag = $this.attr("late_flag");
		tache_code = tache_code==null?"":tache_code;
		window.parent.openMenu({
			menu_id: 'frameOrderDetail',
			label: '订单详情',
			url: 'jspPage/UocBase/orderDetail.jsp?jsession_id='+jsession_id+'&order_no=' + orderId+'&finish_flag='+late_flag+"&tache_code="+tache_code
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

	$("#check_doing").bind("click",function(){
		$("#check_doing").attr("checked","checked");
	});
	$("#check_finish").bind("click",function(){
		$("#check_finish").attr("checked","checked");
	});

	//受理工号
	getSelectOper("0");
	//环节
	getTacheCodes();
//	var tache_code_list = window.parent.Constant.tacheCode();
//	dealCodeList(tache_code_list,"tacheCode");

	//部门
	getDepartments("0");

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

	getTime();
	getArtificialTaskList();

	$("#pageSize").change(function(e){
		$("#pageNo").val("1");
		getArtificialTaskList();
	});

	//全选中或者取消
	$('#taskList').on('click', '.all_select', function(e) {
		var $this = $(this);
		if($this.attr("class")=="all_select select_check"){
			$("#task_list .select_check").removeClass('select_check').addClass('select_checked');
			$this.removeClass('select_check').addClass('select_checked');
		}else{
			$("#task_list .select_checked").removeClass('select_checked').addClass('select_check');
			$this.removeClass('select_checked').addClass('select_check');
		}

	});

	//单个选中
	$('#task_list').on('click', '.single_select', function(e) {
		var $this = $(this);
		if($this.attr("class")=="single_select select_check"){
			$this.removeClass('select_check').addClass('select_checked');
		}else{
			$this.removeClass('select_checked').addClass('select_check');
		}
	});

	//点击解锁按钮
	$("#unlockeBtn").unbind("click").bind("click",function(e){
		unlockeAllotBtn("200",e);
	});

	//点击指派按钮
	$("#allotBtn").unbind("click").bind("click",function(e){
		$.stopPropagation(e);
		$('.pop_container').hide();
		$('#popAllot').show();
		getDepartments("1");
		getSelectOper("1");

		//确定按钮，解锁或者指派任务
		$("#unlocke_allot_btn").unbind("click").bind("click",function(e){
			unlockeAllotBtn("3400",e);
		});
	});

	//展开折叠隐藏域
	$('.short_info_foot[control="foldable"]').on('click', function(e){
		var $this = $(this);
		$this.prev().find('[on_off="off"]').toggleClass('hide');
	});

	//右边弹出的隐藏域选中部门
	$("#depart_list").on("click","li",function(e){
		var $this = $(this);
		$this.siblings().removeClass('active');
		$this.addClass('active');
	});

	//右边弹出的隐藏域选中部门
	$("#oper_list").on("click","li",function(e){
		var $this = $(this);
		$this.siblings().removeClass('active');
		$this.addClass('active');
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

function getArtificialTaskList() {
	$('#taskList .all_select').removeClass('select_checked').addClass('select_check');
	var accept_time_start = $('#accept_time_start').val()+" 00:00:00";
	var accept_time_end = $('#accept_time_end').val()+" 23:59:59";
	var start_date = new Date(accept_time_start);
	var end_date = new Date(accept_time_end);
	var start_month = accept_time_start.substring(5,7);
	var end_month = accept_time_end.substring(5,7);
	if(start_month.substring(0,1) == "0"){
		start_month = start_month.substring(1,2);
	}
	if(end_month.substring(0,1) == "0"){
		end_month = end_month.substring(1,2);
	}
	var curr_time = new Date();
	var curr_date_str = curr_time.format('yyyy-MM-dd hh:mm:ss');
	var d1 = new Date(accept_time_start.replace(/\-/g, "\/"));
	var d2 = new Date(accept_time_end.replace(/\-/g, "\/"));
	var d3 = new Date(curr_date_str.replace(/\-/g, "\/"));
	if(start_month == end_month || (Number(start_month) + 1) == (Number(end_month)) || (start_month =="12" && end_month == "1")){
	}else{
		$.message.info( '开始时间和结束时间跨度不能超过两个月~' );
	    return;
	}
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
		"jsession_id": jsession_id,
		"accept_oper_no": $('#accept_oper_no').val(),
		"accept_depart_no": $('#accept_depart_no').val(),
		"order_no": $('#order_no').val(),
		"order_type":"",
		"accept_time_start": accept_time_start,
		"accept_time_end": accept_time_end,
		"area_code": area_code,
		"tache_code":$("#tache_code a.active").attr('tache_code'),
		"oper_code":$("#oper_code").val(),
		"prod_code":$("#prod_code a.active").attr('code_id'),
		"acc_nbr": $('#acc_nbr').val(),
		"cust_name": $('#cust_name').val(),
		"cert_no": $('#cert_no').val(),
		"accept_no":"",
		"sim_id":$('#sim_id').val(),
		"person_flag":$("#person_flag a.active").attr('code_id')==undefined?"0":$("#person_flag a.active").attr('code_id'),
		"late_flag":"",
		"page_no": $("#pageNo").val(),
		"page_size": $("#pageSize").val(),
		"sort_type":""
	};

	$.ajax({
		type: "post",
		url: rest_host+"rest/ArtificialTaskRest/getArtificialTaskList",
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
				if(data.args.task_list.task_list!= null && data.args.task_list.task_list.length > 0){
					var pageInfo = data.args.task_list;
					var taskList=data.args.task_list.task_list;
					$("#totalCount").html(pageInfo.total_num);

					total_pages = parseInt((pageInfo.total_num + (pageInfo.page_size - 1)) / pageInfo.page_size) ;
					$("#totalPage").html(total_pages);

					var taskHtml = "";
					var prod_code;
					var simId="";
					$.each(taskList,function(i,item){
						var person_flag=item.person_flag=="0"?"个人任务":"部门任务";
						var accept_no=item.person_flag=="0"?item.accept_oper_no:item.accpet_depart_no;
						var accept_name=item.person_flag=="0"?"受理工号":"受理部门";
						prod_code = item.prod_code==null?"":item.prod_code;
						simId = item.sim_id==null||undefined?"":item.sim_id;
						$.each(proc_code_list,function(i,item){
							if(prod_code == item.code_id){
								prod_code = item.code_name;
							}
						});
						taskHtml +='<tr>'
									  +'<td style="width:3%;">'
									  +'<div class="single_select select_check" order_id='+item.order_no+' person_flag='+item.person_flag+'></div>'
									  +'</td>'
									  +'<td class="width30per link" title="点击查看详情" order_id='+item.order_no+' tache_code='+item.tache_code+' cert_no='+item.cert_no+' cust_name='+item.cust_name+' late_flag='+item.late_flag+'>'
									  +'<p>订单ID：'+item.order_no+'<span style="margin-left:30px;font-size:12px;;font-weight: bold;">'+person_flag+'</span><span style="margin-left:30px;font-size:12px;;font-weight: bold;">'+item.task_name+'</span></p>'
									  +'<p>受理时间：'+item.create_time+'</p>'
									  +'<p>'+accept_name+'：'+accept_no+'</p>'
									  +'</td>'
									  +'<td class="width40per">'
									  +'<p>'+simId+'</p>'
									  +'<p>'+item.acc_nbr+'</p>'
									  +'<p>'+prod_code+'</p>'
									  +'</td>'
									  +'<td>'
									  +'<p>客户名称：'+item.cust_name+'</p>'
									  +'<p>证件号码：'+item.cert_no+'</p>'
									  +'</td>'
									  +'</tr>';
					});
					$("#task_list").empty();
					$("#task_list").append(taskHtml);
				}else{
					$.message.info('无人工任务~');
					$("#task_list").empty();
				}
			} else {
				$.message.error('查询失败!');
				$("#task_list").empty();
				$("#pageNo").val("1");
			}

		},
		error: function(data) {
			$.message.error("查询失败!");
			$("#task_list").empty();
		},
		complete: function(){
			$.loading.hide();
		}
	});
}

/**
 * 点击解锁或者指派按钮后调该函数
 */
function unlockeAllotBtn(value,e){
	var targetOper="";
	var targetOperDepart="";
	var targetDepart="";
	var operType=value;
	if(value=="3400"){
		var departSelect=$("#depart_list>li.active");
		var operSelect=$("#oper_list>li.active");
		if(departSelect.length<=0&&operSelect.length<=0){
			$.message.error("请选择一个工号或者一个部门");
			return;
		}
		//指派任务的只能选部门或者工号其中一者
		if(departSelect.length>0&&operSelect.length>0){
			$.message.error("工号和部门两者只能其一");
			return;
		}
		if(departSelect.length==1){
			operType="400";
			targetDepart=departSelect.attr("dept_no");
		}else if(operSelect.length==1){
			operType="300";
			targetOper=operSelect.attr("oper_no");
			targetOperDepart=operSelect.attr("dept_no");
		}
	}

	var domObject=$("#task_list .select_checked");
	if(domObject.length<=0){
		$.message.error("请选择任务");
		return;
	}
	for(var i=0;i<domObject.length;i++){
		//这里要将dom对象转成jquery对象后取到订单号存到数组中
		var orderNo=$(domObject[i]).attr("order_id");
		var personFlag=$(domObject[i]).attr("person_flag");
		if(value=="200"&&personFlag!="0"){
			$.message.error("订单【"+orderNo+"】不是个人任务");
			continue;
		}
		unlockeAllot(orderNo,operType,targetOper,targetOperDepart,targetDepart);
	}
}

/**
 * 正式开始任务解锁、任务指派
 */
function unlockeAllot(orderNo,operType,targetOper,targetOperDepart,targetDepart){
	var data = {
			"jsession_id":jsession_id,
			"order_no":orderNo,
			"oper_type":operType,
			"oper_no":targetOper,
			"target_oper_depart":targetOperDepart,
			"depart_no":targetDepart,
			"express_flag":"1"
		};
	$.ajax({
		type: "post",
		url: rest_host+"rest/ArtificialTaskRest/createTaskAssignment",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在处理...");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				$.message.error('【'+orderNo+'】'+data.content);
			} else {
				$.message.error('【'+orderNo+'】'+data.content);
			}
		},
		error: function(data) {
			$.message.error('【'+orderNo+'】任务解锁指派Ajax请求失败!');
		},
		complete:function(){
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
			$.each(code_list,function(i,item){
				var html = '<a code_id="' + item.code_id + '">' + item.code_name + '</a>';
				if (i == 3) {
					var more = '<div class="btn btn_link vertical_baseline" name="more">更多</div><div class="more_option hide"></div>';
					$("#prod_code").append(more);
				}
				if (i < 3) {
					$("#prod_code").append(html);
				} else {
					$("#prod_code .more_option").append(html);
				}
			});
			proc_code_list = code_list;
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
				var tacheHtml='';
				//console.info("环节》》》"+JSON.stringify(data.args.TacheList));
				if (data.args.TacheList != null && data.args.TacheList.length>0) {
					var tacheHtml = '';
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

/**
 * 获取部门
 */
function getDepartments(flag){
	var departData = {
		jsession_id: jsession_id,
		depart_no: "",
		depart_name: "",
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
			if (data.respCode=="0000"&&data.args.code=="200") {
				var depart_list = JSON.parse(data.args.json_info);
				if(flag=="0"){
					var departHtml='';
					departHtml +='<option value="">--请选择部门--</option>';
					$.each(depart_list.depart_list,function(i,item){
						departHtml +='<option value="'+item.dept_no+'">'+item.dept_name+'</option>';
					});
					$("#accept_depart_no").append(departHtml);
				}else{
					$("#depart_list").html("");
					var departHtml='';
					$.each(depart_list.depart_list,function(i,item){
						//先显示5条
						departHtml+='<li class="line'+(i<3?'':' hide')+'" on_off='+(i<3?'"on"':'"off"')+' dept_no="' + item.dept_no + '">'
						 +'<i class="icon_department"></i><span>'+item.dept_name+'</span>'
						 +'</li>';
					});
					$("#depart_list").append(departHtml);
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
function getSelectOper(flag){
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
				var oper_list = JSON.parse(data.args.json_info);
				//console.info("工号："+JSON.stringify(oper_list));
				if (oper_list.OperInfo != null && oper_list.OperInfo.length>0) {
					if(flag=="0"){
						var operHtml='';
						operHtml +='<option value="">--请选择工号--</option>'
						$.each(oper_list.OperInfo, function(i, item) {
							operHtml +='<option value="'+item.oper_no+'">'+item.oper_no+'</option>';
						});
						$('#accept_oper_no').append(operHtml);
					}else{
						$('#oper_list').html("");
						var operHtml='';
						$.each(oper_list.OperInfo, function(i, item) {
							operHtml+='<li class="line'+(i<3?'':' hide')+'" on_off='+(i<3?'"on"':'"off"')+' oper_no="' + item.oper_no + '" dept_no="' + item.dept_no + '">'
							 +'<i class="icon_staff"></i><span>'+item.oper_no+'</span>'
							 +'</li>';
						});
						$('#oper_list').append(operHtml);
					}

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


