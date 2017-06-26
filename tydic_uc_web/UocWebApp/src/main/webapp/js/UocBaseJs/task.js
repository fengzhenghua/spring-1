//全局信息
var commonInfo={
		pageType:"",
		jsession_id:"",
		tache_code:"",
		role_type:"",
		rest_host:""
};

//选中任务参数信息
var selectTaskInfo = {
		order_no:"",
		order_type:"",
		cust_name:"",
		cert_no:"",
		acc_nbr:"",
		prod_name:"",
		iccid:"",
		tache_code:"",
		json_info:{},
		region_info:{}
};
var pushMsgFlag = "0";//1：推送消息到达后自动刷新；0:表示非推送消息到达后自动刷新

$(document).ready(function() {
	commonInfo.jsession_id = window.parent.operInfo.jsession_id;
	commonInfo.rest_host = window.parent.operInfo.rest_host;
	commonInfo.pageType = "task";
	//获取url参数
	commonInfo.tache_code = HTML_UTLS.getParam("tache_code");
	commonInfo.role_type = HTML_UTLS.getParam("role_type");

	// 任务包领取
	$('#taskPackageGetBtn').on('click', function(e) {
		window.location.href = 'taskPackageGet.jsp?tache_code='+commonInfo.tache_code+'&role_type='+commonInfo.role_type;
	});
	// 任务包设置
	if(commonInfo.role_type==1){
		$('#taskPackageSetBtn').hide();
	}else{
		$('#taskPackageSetBtn').show();
		$('#taskPackageSetBtn').on('click', function(e) {
			window.location.href = 'taskPackageSet.jsp?tache_code='+commonInfo.tache_code+'&role_type='+commonInfo.role_type;
		});
	}
	//全局刷新
	$('#refreshBtn').on('click', function(e) {
		clearQueryInfo();
		getTaskDataList(0,1,10,1);
	});

	// 部门任务 勾选 事件
	$('.card_item').on('click', '.card_body [issue="operate"]', function(e) {
		var $this = $(this);
		$this.toggleClass('active');
	});

	// 展开/折叠 隐藏域
	$('.flow_log_head [control="foldable"]').on('click', function(e) {
		$('.flow_log_body [on_off="off"]').toggleClass('hide');
	});

	$('.short_info_head[control="foldable"], .short_info_foot[control="foldable"]').on('click', function(e){
		var $this = $(this);
		if ($this.siblings('.short_info_body').attr('on_off') == 'off') {
			$this.siblings('.short_info_body').find('li').removeClass('hide');
			$this.siblings('.short_info_body').attr('on_off', 'on');
		} else {
			$this.siblings('.short_info_body').find('li').addClass('hide');
			$this.siblings('.short_info_body').attr('on_off', 'off');
		}
	});

	var limitTaskCount=1;
	var personalTaskCount=1;
	var departTaskCount=1;

	getTaskDataList(0,1,10,1);

	// 打开 任务-弹出页面
	$('.card_item').on('click', '.card_body [issue="title"]', function(e) {
		$.stopPropagation(e);
		var $this = $(this);
		selectTaskInfo.order_no = $this.find('p:eq(0) .text').text();
		selectTaskInfo.order_type = $this.attr('order_type');
		selectTaskInfo.cust_name = $this.attr('cust_name');
		selectTaskInfo.cert_no = $this.attr('cert_no');
		selectTaskInfo.acc_nbr = $this.attr('acc_nbr');

		//100-销售订单，101-服务订单
		getTaskDetail(selectTaskInfo.order_no,selectTaskInfo.order_type);
		if($this.parents('[class="card_item"]').attr('id')=='taskNotgrabList'){
			if ($('#taskBtnArea').hasClass('hide')) {
				$('#taskBtnArea').removeClass('hide');
			}

			$('#taskBtnArea .btn_primary').unbind().bind('click', function(e) {
				$.dialog.confirm(
					    "确认领取该订单",
					    "领取任务",
					    "确认",
					    "取消",
					    function() {
							assignTask($this.prev('[issue="operate"]'),selectTaskInfo.order_no,"100","","",window.parent.operInfo.depart_no);
					    }
					);
			});
		}else{
			if (!$('#taskBtnArea').hasClass('hide')) {
				$('#taskBtnArea').addClass('hide');
			}
		}

	});


	// 打开 筛选-弹出页面
	$('.card_item').on('click', '.card_head .search_icon', function(e) {
		$.stopPropagation(e);
		var $this = $(this);

		$('.pop_container').hide();
		$('#popSearch').show();
		getTacheCodes();
		getDepartments();
		getOperCodes();
		getProducts();

		//查询
		$('#popSearch .btn_primary').unbind().bind('click', function(e) {
			if($this.parents('[class="card_item"]').attr('id')=='taskOuttimeList'){
				getTaskDataList(0,1,10,0);
			}
			if($this.parents('[class="card_item"]').attr('id')=='taskIntimeList'){
				getTaskDataList(0,0,10,0);
			}
			if($this.parents('[class="card_item"]').attr('id')=='taskNotgrabList'){
				getTaskDataList(1,'',10,0);
			}
		});
	});

	//选中查询条件
	$('#popSearch').on('click', '.short_info_body .line', function(e) {
		var $line = $(this);
		if ($line.hasClass('active')) {
			$line.removeClass('active');
		} else {
			$line.siblings().removeClass('active');
			$line.addClass('active');
			$line.siblings().addClass('hide');
			$line.parent().attr('on_off', 'off');
		}
	});


	//刷新
	$('.card_item').on('click', '.card_head .refresh_icon', function(e) {
		var $this=$(this);
		clearQueryInfo();
		if($this.parents('[class="card_item"]').attr('id')=='taskOuttimeList'){
			getTaskDataList(0,1,10,0);
		}
		if($this.parents('[class="card_item"]').attr('id')=='taskIntimeList'){
			getTaskDataList(0,0,10,0);
		}
		if($this.parents('[class="card_item"]').attr('id')=='taskNotgrabList'){
			getTaskDataList(1,'',10,0);
		}
	});

	//查看更多
	$('.card_item').on('click', '.card_foot .more_issues', function(e) {
		var $this=$(this);
		if($this.parents('[class="card_item"]').attr('id')=='taskOuttimeList'){
			limitTaskCount++;
			getTaskDataList(0,1,limitTaskCount*10,0);
		}
		if($this.parents('[class="card_item"]').attr('id')=='taskIntimeList'){
			personalTaskCount++;
			getTaskDataList(0,0,personalTaskCount*10,0);
		}
		if($this.parents('[class="card_item"]').attr('id')=='taskNotgrabList'){
			departTaskCount++;
			getTaskDataList(1,'',departTaskCount*10,0);
		}
	});

	// 打开 分配-弹出页面
	if(commonInfo.role_type==1){
		$('#taskAllotBtn').hide();
	}else{
		$('#taskAllotBtn').show();
		$('#taskAllotBtn').on('click', function(e) {
			$.stopPropagation(e);
			var selectedCards = $('#taskNotgrabList .card_body .issue [issue="operate"].active');
			if(selectedCards.length>0){
				getSelectDepart("");
				getSelectOper("");
			}else{
				$.message.info('请勾选部门任务');
			}
		});
	}

	//查询工号部门
	$('#popAllot').on('click', '.short_info_body .search_icon', function(e) {
		var $this = $(this);
		var oper_type=$this.parent().parent().parent().attr('oper_type');
		var operNm = "";
		var departNm="";
		if(oper_type==300){
			operNm=$this.prev().val();
			getSelectOper(operNm);
		}else if(oper_type==400){
			departNm=$this.prev().val();
			getSelectDepart(departNm);
		}
	});

	//点击分配
	$('#popAllot').on('click', '.short_info_body .line', function(e) {
		var selectedCards = $('#taskNotgrabList .card_body .issue [issue="operate"].active');
		var $this = $(this);
		var oper_type=$this.parent().parent().attr('oper_type');
		var target_oper = "";
		var target_depart="";
		var target_oper_depart="";
		$.each(selectedCards, function(i, item) {
			var assign_order_no = $(item).next().find('p:eq(0) .text').text();
			var confirmMessage="确认分配订单"+assign_order_no+"给";
			if(oper_type==300){
				target_oper = $this.attr("code_id");
				target_oper_depart=$this.attr("target_oper_depart");
				confirmMessage=confirmMessage+"员工"+$this.find('span:eq(0)').text();
			}else if(oper_type==400){
				target_depart = $this.attr("code_id");
				confirmMessage=confirmMessage+"部门"+ $this.find('span:eq(0)').text();
			}
			$.dialog.confirm(
				    confirmMessage,
				    "分配任务",
				    "确认",
				    "取消",
				    function() {
				    	assignTask($(item),assign_order_no,oper_type,target_oper,target_depart,target_oper_depart);
				    }
				);
		});
	});

	//领取按钮
	$('#taskGetBtn').on('click', function(e) {
		var selectedCards = $('#taskNotgrabList .card_body .issue [issue="operate"].active');
		if(selectedCards.length==0){
			$.message.info('请勾选部门任务');
		}
		if(selectedCards.length>0){
			$.dialog.confirm(
				    "确认领取所选订单",
				    "领取任务",
				    "确认",
				    "取消",
				    function() {
				    	$.each(selectedCards, function(i, item) {
							var assign_order_no = $(item).next().find('p:eq(0) .text').text();
							assignTask($(item),assign_order_no,"100","","",window.parent.operInfo.depart_no);
						});
				    }
				);
		}
	});

	// 撤单模态框属性设置
	$.modal('#revokeOrderModal', {
        width: 500,
        height: 350
    });
	// 打开撤单模态框
	$('#taskDealArea').on('click', '#revokeOrderBtn', function(e) {
		if($('#reason_list>p').length == 0){
			//加载
			getCancelReasons();
			$('#revokeOrderModal').show();
		}else{
			$('#revokeOrderModal').show();
		}
	});
	// 打开撤单模态框
	$('#taskProcessArea').on('click', '#revokeOrderBtn', function(e) {
		if($('#reason_list>p').length == 0){
			//加载
			getCancelReasons();
			$('#revokeOrderModal').show();
		}else{
			$('#revokeOrderModal').show();
		}
	});
	// 撤单-选项选中事件
	$('.modal_layer').on('click', '.reason_option p', function(e) {
		var $this = $(this);
		$this.siblings().removeClass('active');
		$this.addClass('active');
	});
	// 撤单-确定事件
	$('#revokeOrderConfirmBtn').on('click', function(e) {
		var cancel_type=$("#reason_list>.active").attr("code_id");
		var reasonContent=$("#reason_list>.active span").html();
		if(reasonContent==null||reasonContent==""||reasonContent==undefined){
			cancel_type="other_reason";
			reasonContent=$("#other_reason").val();
		}
		if(reasonContent==null||reasonContent==""||reasonContent==undefined){
			$.message.info('请选择撤单原因或者手填其他原因');
			$(".message_box").css("z-index",10001);
			return;
		}
		$('#revokeOrderModal').hide();
		var json_info_ext={
				"tache_code":selectTaskInfo.tache_code,
				"cancel_type":cancel_type,
				"cancel_reason":reasonContent
		};
		cancelOrder(json_info_ext);
	});
	// 撤单-取消事件
	$('#revokeOrderCancelBtn').on('click', function(e) {
		$('#revokeOrderModal').hide();
	});

	getRegionInfo();
});

//TODO
//接收到后台推送信息后，刷新任务
function refreshTaskListAfterGetPushMsg(){
	pushMsgFlag = "1";
	clearQueryInfo();
	getTaskDataList(0,1,10,1);
};



//获取任务列表  person_flag:0-个人任务,1-部门任务,2-全部任务   late_flag:0-未逾期,1-逾期,2-全部   queryType:0-单独查询  1-回调部门 2-回调未逾期
function getTaskDataList(person_flag,late_flag,pageSize,queryType) {
	var accept_time_start=$('#accept_time_start').val();
	var accept_time_end=$('#accept_time_end').val();
	var query_order_no=$('#order_no').val();
	var accpet_depart_no = $('#accpet_depart_no .active').attr("code_id");
	var query_tache_code = $('#tache_code .active').attr("code_id");
	var oper_code = $('#oper_code .active').attr("code_id");
	var prod_code = $('#prod_code .active').attr("code_id");
//	var area_code=$('#area_code').find("option:selected").text();
	var acc_nbr=$('#acc_nbr').val();
	var cust_name=$('#cust_name').val();
	var cert_no=$('#cert_no').val();

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

	if(person_flag==0&&late_flag===1){
		$('#taskOuttimeList .card_body .issue').remove();
	}else if(person_flag==0&&late_flag===0){
		$('#taskIntimeList .card_body .issue').remove();
	}else if(person_flag==1){
		$('#taskNotgrabList .card_body .issue').remove();
	}

	var data = {
			jsession_id: commonInfo.jsession_id,
			accept_depart_no: INPUT_UTIL.isNull(accpet_depart_no)?window.parent.operInfo.depart_no:accpet_depart_no,
			accept_oper_no: person_flag==1?"":window.parent.operInfo.oper_no,
			order_no: query_order_no,
			accept_time_start: accept_time_start,
			accept_time_end: accept_time_end,
//			area_code: '',//area_code,					    //code未转译
			tache_code: (INPUT_UTIL.isNull(commonInfo.tache_code)?query_tache_code:commonInfo.tache_code),
			oper_code: oper_code,
			prod_code: prod_code,
			acc_nbr: acc_nbr,
			cust_name: cust_name,
			cert_no:cert_no,
			person_flag:person_flag,
			late_flag:late_flag,
			page_no: '1',
			page_size: pageSize
		};

	$.ajax({
		type: "post",
		url: commonInfo.rest_host+"rest/ArtificialTaskRest/getArtificialTaskList",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			if(pushMsgFlag == "0"){
				$.loading.show("正在加载");
			}
		},
		success: function(data) {
			if (data.respCode=="0000") {
				var limitTaskCount=0;
				var personalTaskCount=0;
				var departTaskCount=0;

				if (data.args.task_list.task_list != null && data.args.task_list.task_list.length>0) {
					$.each(data.args.task_list.task_list, function(i, item) {
						var currDate=new Date();
						//0个人任务,1部门任务
						if(item.person_flag=="0"&&item.late_flag=="1"){
							//逾期
							limitTaskCount++;
							var html =createTaskHtml(item,currDate,1);
							$('#taskOuttimeList .card_body').append(html);
							timeRunning(html); // 倒计时
						}else if(item.person_flag=="0"&&item.late_flag=="0"){
							//未逾期
							personalTaskCount++;
							var html =createTaskHtml(item,currDate,0);
					        $('#taskIntimeList .card_body').append(html);
					        timeRunning(html); // 倒计时
						}else if(item.person_flag=="1"){
							//部门
							departTaskCount++;
							var html =createTaskHtml(item,currDate,3);
							$('#taskNotgrabList .card_body').append(html);
						}
					});

					//任务总数,已加载,剩余
					if(limitTaskCount>0){
						updateTaskNum(1,data.args.task_list.total_num,limitTaskCount);
					}
					if(personalTaskCount>0){
						updateTaskNum(2,data.args.task_list.total_num,personalTaskCount);
					}
					if(departTaskCount>0){
						updateTaskNum(3,data.args.task_list.total_num,departTaskCount);
					}
				}else{
					if(person_flag==0&&late_flag===1){
						updateTaskNum(1,0,0);
					}else if(person_flag==0&&late_flag===0){
						updateTaskNum(2,0,0);
					}else if(person_flag==1){
						updateTaskNum(3,0,0);
					}
				}

			} else {
				if(pushMsgFlag == "0"){
					$.message.error('获取任务列表失败!'+data.content);
				}
			}
		},
		error: function(data) {
			if(pushMsgFlag == "0"){
				$.message.error('获取任务列表Ajax请求失败!');
			}
		},
		complete: function(){
				if(pushMsgFlag == "0"){
					$.loading.hide();
				}
				switch(queryType){
				case 1:
					getTaskDataList(1,'',10,2);
					break;
				case 2:
					getTaskDataList(0,0,10,0);
					pushMsgFlag = "0";
					break;
				default:
					pushMsgFlag = "0";
					break;
				}
		}
	});

}
// type: 0-未逾期,1-逾期,2-剩余时间小于10%逾期,3-部门
function createTaskHtml(item,curTime,type){
	var border = ['border_much','','border_less',''];
	var color = ['color_much','color_over','color_less',''];
	var tip = ['倒计时长','逾期时长','倒计时长',''];
	var diffTime = $UTIL.timeTool.getTimeDiff(curTime, stringToDate(item.limit_time));
	if (type == 0) {
		var residualTime = stringToDate(item.limit_time).getTime() - curTime.getTime();
		var totalTime = stringToDate(item.limit_time).getTime() - stringToDate(item.create_time).getTime();
		if (residualTime / totalTime < window.parent.Config.TASK_TIME_RATIO) {
			type=2;
		}
	}
	var diffContent='<div class="times" time_type="residual_time">'
					+ '<span class="label">'+tip[type]+'：</span><span class="text" color_attr="'+color[type]+'">'+diffTime+'</span>'
					+ '</div>';
	var html=$('<div class="issue" color_attr="' + border[type] + '">'
			+ '<div issue="flag">'
			+ ' <span class="label">'+item.task_name+'</span>'
			+ '</div>'
			+ (type==3?'<div issue="operate"></div>':'')
			+ '<div issue="title" class="card_hover" order_type="' + item.order_type + '" cust_name="' + item.cust_name + '" cert_no="' + item.cert_no + '" acc_nbr="' + item.acc_nbr + '">'
			+ '	<p><span class="label">订单ID：</span><span class="text">'+item.order_no+'</span></p>'
			+ '	<p><span class="label">业务号码：</span><span class="text">'+item.acc_nbr+'</span></p>'
			+ '	<p><span class="label">业务名称：</span><span class="text">'+(INPUT_UTIL.isNull(item.oper_code)?"":window.parent.Constant.operCode(item.oper_code))+'</span></p>'
			+ '</div>'
			+ '<div issue="sup_info" create_time="' + item.create_time + '" limit_time="' + item.limit_time + '">'
			+ '	<div class="times" time_type="start_time">'
			+ '	<span class="label">创建时间：</span><span class="text" color_attr="color_default">'+item.create_time+'</span>'
			+ '	</div>'
			+  (type==3?'':diffContent)
			+ '</div>'
			+ '</div>');
	return html;
}

//将yyyy-MM-dd hh:mm:ss格式字符串转换为Date
function stringToDate(s) {
	var d = new Date();
	d.setYear(parseInt(s.substring(0,4),10));
	d.setMonth(parseInt(s.substring(5,7)-1,10));
	d.setDate(parseInt(s.substring(8,10),10));
	d.setHours(parseInt(s.substring(11,13),10));
	d.setMinutes(parseInt(s.substring(14,16),10));
	d.setSeconds(parseInt(s.substring(17,19),10));
	return d;
}

// 倒计时
function timeRunning(html) {
	setInterval(function() {
		var curTime = new Date();
		var createTime = html.find('[issue="sup_info"]').attr('create_time');
		var limitTime = html.find('[issue="sup_info"]').attr('limit_time');
		html.find('[time_type="residual_time"] span.text').text($UTIL.timeTool.getTimeDiff(curTime, stringToDate(limitTime)));
		if (html.closest('#taskIntimeList').length > 0) {
			var residualTime = stringToDate(limitTime).getTime() - curTime.getTime(); // 剩余时间（毫秒）
			var totalTime = stringToDate(limitTime).getTime() - stringToDate(createTime).getTime(); // 总时间（毫秒）
			if (residualTime <= 0) {
				html.attr('color_attr', '');
				html.find('[time_type="residual_time"] span.label').text('逾期时长');
				html.find('[time_type="residual_time"] span.text').attr('color_attr', 'color_over');
				html.prependTo($('#taskOuttimeList .card_body'));
				$('#taskIntimeList .card_head [name="taskCount"]').text($('#taskIntimeList .card_head [name="taskCount"]').text() - 1);
				$('#taskIntimeList .card_foot [name="taskLoadCount"]').text($('#taskIntimeList .card_foot [name="taskLoadCount"]').text() - 1);
				$('#taskOuttimeList .card_head [name="taskCount"]').text(parseInt($('#taskOuttimeList .card_head [name="taskCount"]').text()) + 1);
				$('#taskOuttimeList .card_foot [name="taskLoadCount"]').text(parseInt($('#taskOuttimeList .card_foot [name="taskLoadCount"]').text()) + 1);
			} else if (residualTime / totalTime < window.parent.Config.TASK_TIME_RATIO
					&& html.attr('color_attr') == 'border_much') {
				html.attr('color_attr', 'border_less');
				html.find('[time_type="residual_time"] span.text').attr('color_attr', 'color_less');
			}
		}
	}, 1000);
}

//获取任务详情
function getTaskDetail(order_no,order_type){
	$('#task_detail ul.detail_info_head').remove();
	$('#task_detail ul.detail_info_body').remove();
	$('#order_detail ul.detail_info_head').remove();
	$('#order_detail ul.detail_info_body').remove();

	var data = {
		jsession_id: commonInfo.jsession_id,
		order_no: order_no
	};

	$.ajax({
		type: "post",
		url: commonInfo.rest_host+"rest/ArtificialTaskRest/getTaskDetailInfo",
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
				if (data.args.task_detail != null) {
					var item=data.args.task_detail;
					var task_detail='	<ul class="detail_info_head">'
								    +'<li class="line">'
								    +'			<span>任务信息</span>'
									+'		</li>'
									+'	</ul>'
									+'	<ul class="detail_info_body">'
									+'		<li class="line">'
									+'			<div class="one_third">任务名称：'+item.task_name+'</div><div class="one_third">业务号码：'+item.acc_nbr+'</div><div class="one_third">业务名称：'+window.parent.Constant.operCode(item.oper_code)+'</div>'
									+'		</li>'
									+'		<li class="line">'
									+'			<div class="one_third">创建时间：'+item.create_time+'</div><div class="one_third">逾期时间：'+item.limit_time+'</div>'
									+'		</li>'
									+'	</ul>';
					$('#task_detail').append(task_detail);

					var prodNm="";
					if(INPUT_UTIL.isNull(item.prod_name)){
						prodNm=item.prod_code==''?"":window.parent.Constant.prodCode(item.prod_code);
					}else{
						prodNm=item.prod_name;
					}
					if(prodNm=='未定义'){
						prodNm=item.prod_code+'(产品编码)';
					}
					selectTaskInfo.prod_name = prodNm;
					selectTaskInfo.iccid = item.iccid;

					var order_detail='<ul class="detail_info_head">'
									+'	<li class="line">'
									+'		<span>订单信息</span>'
									+'	</li>'
									+'</ul>'
									+'	<ul class="detail_info_body">'
									+'	<li class="line">'
									+'			<div class="one_third">订单ID：'+item.order_no+'</div><div class="one_third">客户名称：'+item.cust_name+'</div><div class="one_third">产品名称：'+prodNm+'</div>'
									+'	</li>'
									+'	<li class="line">'
									+'		<div class="one_third">首月付费方式：'+(item.first_month_rent==''?"":window.parent.Constant.first_month_rent("02"))+'</div><div class="one_third">创建时间：'+item.create_time+'</div>'
									+'		</li>'
									+'	</ul>';
					$('#order_detail').append(order_detail);

					if(item.task_json!=""){
						selectTaskInfo.json_info=JSON.parse(item.task_json);
					}
					selectTaskInfo.tache_code=item.tache_code;

					//根据环节加载不同页面
					if(!INPUT_UTIL.isNull(item.task_url) && $('#taskBtnArea').hasClass('hide')){
						if (!$('#taskProcessArea').hasClass('hide')) {
							$('#taskProcessArea').addClass('hide');
						}

						$("#taskDealArea").show();
						$("#taskDealArea").load(item.task_url);
					}else{
						$("#taskDealArea").hide();

						if($('#taskBtnArea').hasClass('hide')){
							if ($('#taskProcessArea').hasClass('hide')) {
								$('#taskProcessArea').removeClass('hide');
							}

							$('#taskProcessArea .btn_primary').unbind().bind('click', function(e) {
								$.dialog.confirm(
										"确认处理该订单到下一环节？",
										"任务处理",
										"确认",
										"取消",
										function() {
											taskProcess();
										}
								);
							});
						}else{
							if (!$('#taskProcessArea').hasClass('hide')) {
								$('#taskProcessArea').addClass('hide');
							}
						}
					}
				}

			} else {
				$.message.error('获取任务详情失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取任务详情Ajax请求失败!');
		},
		complete: function(){
			$.loading.hide();
			getProcViewInfo(order_no,order_type);
		}
	});
}


//获取流程图信息 order_type:100-销售订单，101-服务订单
function getProcViewInfo(order_no,order_type){
	$('#proc_view div.flow_title').remove();
	$('#proc_view div.flow_chart').remove();
	$('#proc_log ul.flow_log_body').remove();

	var data = {
			jsession_id: commonInfo.jsession_id,
			order_no: order_no,
			tache_code: selectTaskInfo.tache_code,
			order_type: order_type
		};

	$.ajax({
		type: "post",
		url: commonInfo.rest_host+"rest/procModService/getProcViewInfo",
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
				var flow_title='',flow_chart='',flow_log_body='';

				flow_log_body='<ul class="flow_log_body">';

				if (data.args.proc_log_list != null&& data.args.proc_log_list.length>0) {
					$.each(data.args.proc_log_list, function(i, item) {
						if(i+1<data.args.proc_log_list.length){
							flow_log_body=flow_log_body+createProcLogHtml(item,i);
						}else if(i+1==data.args.proc_log_list.length){
							flow_log_body=flow_log_body+createProcLogHtml(item,i) +'</ul>';;
						}
					});
				}

				var currSeq_no='';
				if (data.args.proc_view_list!= null&& data.args.proc_view_list.length>0) {
					var width=200*data.args.proc_view_list.length-124;
					flow_chart='<div class="flow_chart">'
						+ '<ul class="flow_list grab" style="width:'+width+'px;">';

					$.each(data.args.proc_view_list, function(i, item) {
						if(item.tache_code==selectTaskInfo.tache_code){
							currSeq_no=parseInt(item.seq_no);
							if(currSeq_no>1&&data.args.proc_view_list[currSeq_no-2].deal_time!=""){
								flow_title='<div class="flow_title">当前订单[<span id="go_order_detail" onclick="JumpOrderDetail()">'+order_no+'</span>]已经进入'+item.tache_name+'处理'+$UTIL.timeTool.getTimeDiff(new Date(), stringToDate(data.args.proc_view_list[currSeq_no-2].deal_time))+'，请尽快处理！</div>';
							}
						}
					});
					var nextSeqNo="";
					$.each(data.args.proc_view_list, function(i, item) {
						var lastProcFlag=false;
						var dealFlag=false;
						var lastLogFlag=false;
						if(i+1==data.args.proc_view_list.length){
							lastProcFlag=true;
							if(data.args.proc_view_list[i].seq_no == currSeq_no){
								flow_title='<div class="flow_title">当前订单[<span id="go_order_detail" onclick="JumpOrderDetail()">'+order_no+'</span>]已经处理完成</div>';
							}
						}
						if(item.seq_no == currSeq_no){
							lastLogFlag=true;
						}
						if(item.seq_no<=currSeq_no){
							dealFlag=true;
						}
						if(item.seq_no==currSeq_no && !lastLogFlag){
							return true;
						}

						if(i+1<data.args.proc_view_list.length){
							nextSeqNo=parseInt(data.args.proc_view_list[i+1].seq_no);
						}
						if(item.seq_no==nextSeqNo&&dealFlag&&INPUT_UTIL.isNull(item.deal_time)&&item.tache_code!="S00009"){
							return true;
						}
						flow_chart=flow_chart+createProcHtml(item,i+1,dealFlag,lastLogFlag,lastProcFlag);

					});
				}

				if(flow_title==''){
					flow_title='<div class="flow_title">当前订单[<span id="go_order_detail" onclick="JumpOrderDetail()">'+order_no+'</span>]还未处理，请尽快处理！</div>';
				}

				$('#proc_view').append(flow_title);
				$('#proc_view').append(flow_chart);
				$('#proc_log').append(flow_log_body);
			} else {
				$.message.error('获取流程图信息失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取流程图信息Ajax请求失败!');
		},
		complete: function(){
			$.loading.hide();
			$('.pop_container').hide();
			$('#popTask').show();

			// 流程图左右拖拽事件
			var box = $('.flow_chart');
			var grab = $('.flow_chart .flow_list');
			var store = {move: false};
			grab.on('mousedown', function(e) {
				grab.removeClass('grab').addClass('grabbing');
				store.move = true;
				store.startX = e.pageX + box.scrollLeft();
				e.preventDefault();
			});
			grab.on('mousemove', function(e) {
				if (store.move == true){box.scrollLeft(store.startX - e.pageX);}
				e.preventDefault();
			});
			$(document).on('mouseup', function(e) {
				grab.removeClass('grabbing').addClass('grab');
				store.move = false;
				e.preventDefault();
			});
		}
	});
}


function JumpOrderDetail(){
	var jsession_id=commonInfo.jsession_id;
	var order_no=selectTaskInfo.order_no;
	var tache_code=selectTaskInfo.tache_code;
	window.parent.openMenu({
		menu_id: 'frameOrderDetail',
		label: '订单详情',
		url: 'jspPage/UocBase/orderDetail.jsp?jsession_id='+jsession_id+'&order_no=' + order_no+"&tache_code="+tache_code
	}, true);
}

function createProcHtml(item,seq_no,dealFlag,lastLogFlag,lastProcFlag){
	var state=dealFlag?"done":"wait";

	var html= '<li class="flow_node '+(lastLogFlag?"doing":state)+'">'
			  + '<b>'+seq_no+'</b>'
			  + '<div>'
			  + '<p>'+item.tache_name+'</p>'
			  + ((dealFlag&& !INPUT_UTIL.isNull(item.deal_time))?'<p>'+item.deal_time.substring(0,10)+'<br/>'+item.deal_time.substring(11)+'</p>':'')
			  + '</div>'
			  + '</li>'
			  + (lastProcFlag?'</ul></div>':'<li class="flow_arrow '+(lastLogFlag?"wait":state)+'"></li>');
	return html;
}

function createProcLogHtml(item,seqNo){
	var html='<li class="line'+(seqNo<3?'':' hide')+'" on_off='+(seqNo<3?'"on"':'"off"')+'>'
		+'	<div class="width20per">'+item.deal_time+'</div><div class="width15per">'+item.tache_name+'</div><div class="width50per">'+(item.deal_desc==null?"":item.deal_desc)+'</div><div class="width15per">'+item.oper_no+'</div>'
		+'</li>';
	return html;
}


//任务分配  oper_type:100-部门共享任务锁定,200-部门共享任务解锁,300-分配任务给目标工号,400-分配任务给目标部门
function assignTask(item,order_no,oper_type,target_oper,target_depart,target_oper_depart){

	var data = {
			jsession_id: commonInfo.jsession_id,
			order_no: order_no,
			oper_type:oper_type,
			oper_no:target_oper,
			depart_no:target_depart,
			target_oper_depart:target_oper_depart
		};

	$.ajax({
		type: "post",
		url: commonInfo.rest_host+"rest/ArtificialTaskRest/createTaskAssignment",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在分配");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				$.message.success('分配任务成功!');
				if(oper_type=="100"){
					var issue = $(item).parent();
					var type = 0;
					var curTime = new Date();
					var createTime = issue.find('[issue="sup_info"]').attr('create_time');
					var limitTime = issue.find('[issue="sup_info"]').attr('limit_time');
					var residualTime = stringToDate(limitTime).getTime() - curTime.getTime(); // 剩余时间（毫秒）
					var totalTime = stringToDate(limitTime).getTime() - stringToDate(createTime).getTime(); // 总时间（毫秒）
					if (residualTime <= 0) {
						type = 1;
						issue.prependTo($('#taskOuttimeList .card_body'));
						$('#taskNotgrabList .card_foot [name="taskLoadCount"]').text($('#taskNotgrabList .card_foot [name="taskLoadCount"]').text() - 1);
						$('#taskNotgrabList .card_head [name="taskCount"]').text($('#taskNotgrabList .card_head [name="taskCount"]').text() - 1);
						$('#taskOuttimeList .card_head [name="taskCount"]').text(parseInt($('#taskOuttimeList .card_head [name="taskCount"]').text()) + 1);
						$('#taskOuttimeList .card_foot [name="taskLoadCount"]').text(parseInt($('#taskOuttimeList .card_foot [name="taskLoadCount"]').text()) + 1);
					} else {
						 if (residualTime>0&&residualTime / totalTime < window.parent.Config.TASK_TIME_RATIO) {
							type = 2;
						}
						issue.prependTo($('#taskIntimeList .card_body'));
						$('#taskNotgrabList .card_foot [name="taskLoadCount"]').text($('#taskNotgrabList .card_foot [name="taskLoadCount"]').text() - 1);
						$('#taskNotgrabList .card_head [name="taskCount"]').text($('#taskNotgrabList .card_head [name="taskCount"]').text() - 1);
						$('#taskIntimeList .card_foot [name="taskLoadCount"]').text(parseInt($('#taskIntimeList .card_foot [name="taskLoadCount"]').text()) + 1);
						$('#taskIntimeList .card_head [name="taskCount"]').text(parseInt($('#taskIntimeList .card_head [name="taskCount"]').text()) + 1);
					}

					var border = ['border_much','','border_less',''];
					var color = ['color_much','color_over','color_less',''];
					var tip = ['倒计时长','逾期时长','倒计时长',''];
					var diffTime = $UTIL.timeTool.getTimeDiff(curTime, stringToDate(limitTime));
					var diffContent='<div class="times" time_type="residual_time">'
									+ '<span class="label">'+tip[type]+'：</span><span class="text" color_attr="'+color[type]+'">'+diffTime+'</span>'
									+ '</div>';
					issue.find('[issue="sup_info"]').append(diffContent);
					issue.attr('color_attr', border[type]);
					$(item).remove('[issue="operate"]');
					timeRunning(issue);
				}

				if(oper_type=="300"){
					 $(item).parent().remove();
					 $('#taskNotgrabList .card_foot [name="taskLoadCount"]').text($('#taskNotgrabList .card_foot [name="taskLoadCount"]').text()-1);
					 $('#taskNotgrabList .card_head [name="taskCount"]').text($('#taskNotgrabList .card_head [name="taskCount"]').text()-1);
					 $('#taskIntimeList .card_foot [name="taskNotLoadCount"]').text(parseInt($('#taskIntimeList .card_foot [name="taskNotLoadCount"]').text())+1);
					 $('#taskIntimeList .card_head [name="taskCount"]').text(parseInt($('#taskIntimeList .card_head [name="taskCount"]').text())+1);
				}

				reload();
			} else {
				$.message.error('分配任务失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('分配任务Ajax请求失败!');
		},
		complete:function(){
			$('#popTask').hide();
			$.loading.hide();
		}
	});
}


//获取可选部门
function getSelectDepart(departNm){
	$('#popAllot .pop_detail [oper_type="400"] .short_info_body li').remove();
	var departHtml='<li class="input_box">'
		   +'<input type="text" class="search_input" placeholder="输入部门名称..." />'
		   +'<i class="search_icon"></i>'
		   +'</li>';

	var departData = {
		jsession_id: commonInfo.jsession_id,
		depart_no: "",
		depart_name: departNm,
		region_id: window.parent.operInfo.area_code,
		query_type: "0"
	};

	$.ajax({
		type: "post",
		url: commonInfo.rest_host+"rest/getInfoService/getDepartInfo",
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

					if (departInfo != null && departInfo.length>0) {
						$.each(departInfo, function(i, item) {
							departHtml=departHtml+createQueryInfoHtml(i,item,1);
						});
					}
				}else{
					$.message.info('未查询到部门，请重新输入');
				}

				$('#popAllot .pop_detail [oper_type="400"] .short_info_body').append(departHtml);
			} else {
				$.message.error('获取可选部门失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取可选部门Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
			$('.pop_container').hide();
			$('#popAllot').show();
		}
	});
}
//获取可选工号
function getSelectOper(operName){
	$('#popAllot .pop_detail [oper_type="300"] .short_info_body li').remove();
	var operHtml='<li class="input_box">'
		 +'<input type="text" class="search_input" placeholder="输入员工名称..." />'
		 +'<i class="search_icon"></i>'
		 +'</li>';

	var operData = {
			jsessionId: commonInfo.jsession_id,
			operNo: '',
			operName:operName//以名字查询
		};

	$.ajax({
		type: "post",
		url: commonInfo.rest_host+"rest/getInfoService/getOperInfo",
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
				if(data.args.json_info!=''){
					var operInfo=JSON.parse(data.args.json_info).OperInfo;

					if (operInfo != null && operInfo.length>0) {
						$.each(operInfo, function(i, item) {
							operHtml=operHtml+createQueryInfoHtml(i,item,0);
						});
					}
				}else{
					$.message.info('未查询到工号，请重新输入');
				}

				$('#popAllot .pop_detail [oper_type="300"] .short_info_body').append(operHtml);
			} else {
				$.message.error('获取可选工号失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取可选工号Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
			$('.pop_container').hide();
			$('#popAllot').show();
		}
	});
}
//type:0-工号，1-部门，2-业务，3-产品,4-环节
function createQueryInfoHtml(seqNo,item,type){
	var content='';
	var icon='';
	var code='';
	var target_oper_depart='';
	switch(type){
		case 0:
			icon='icon_staff';
			content=item.oper_name;
			code=item.oper_no;
			target_oper_depart=item.dept_no;
			break;
		case 1:
			icon='icon_department';
			content=item.dept_name;
			code=item.dept_no;
			break;
		case 2:
			icon='icon_business';
			content=item.code_name;
			code=item.code_id;
			break;
		case 3:
			icon='icon_product';
			content=item.code_name;
			code=item.code_id;
			break;
		case 4:
			icon='icon_link';
			content=item.tache_name;
			code=item.tache_code;
			break;
		default:
			break;
	}
	var html='<li class="line'+(seqNo<3?'':' hide')+'" code_id="' + code + '"'+' target_oper_depart="'+target_oper_depart+'">'
			 +'<i class="'+icon+'"></i><span>'+content+'</span>'
			 +'</li>';
	return html;
}


//获取环节
function getTacheCodes(){
	$('#tache_code .short_info_body li').remove();
	if(!INPUT_UTIL.isNull(commonInfo.tache_code)){
		$('#tache_code').hide();
	}else{
		var tachetData = {
				jsession_id: commonInfo.jsession_id
		};

		$.ajax({
			type: "post",
			url: commonInfo.rest_host+"rest/getInfoService/getOptionalTache",
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
					if (data.args.TacheList != null && data.args.TacheList.length>0) {
						$.each(data.args.TacheList, function(i, item) {
							tacheHtml=tacheHtml+createQueryInfoHtml(i,item,4);
						});
					}
					$('#tache_code .short_info_body').append(tacheHtml);
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
}


//获取部门
function getDepartments(){
	$('#accpet_depart_no .short_info_body li').remove();
	var departData = {
		jsession_id: commonInfo.jsession_id,
		depart_no: "",
		depart_name: "",
		region_id: window.parent.operInfo.area_code,
		query_type: "0"
	};

	$.ajax({
		type: "post",
		url: commonInfo.rest_host+"rest/getInfoService/getDepartInfo",
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
					var departHtml='';
					if (departInfo != null && departInfo.length>0) {
						$.each(departInfo, function(i, item) {
							departHtml=departHtml+createQueryInfoHtml(i,item,1);
						});
					}
					$('#accpet_depart_no .short_info_body').append(departHtml);
				}else{
					$.message.info('获取部门为空');
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


//获取业务
function getOperCodes(){
	$('#oper_code .short_info_body li').remove();
	var operData = {
			jsession_id: commonInfo.jsession_id,
			type_code: "operCode"
		};

	$.ajax({
		type: "post",
		url: commonInfo.rest_host+"rest/ArtificialTaskRest/getTaskQueryInfo",
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
				var operHtml='';
				if (data.args.code_list != null && data.args.code_list.length>0) {
					$.each(data.args.code_list, function(i, item) {
						operHtml=operHtml+createQueryInfoHtml(i,item,2);
					});
				}
				$('#oper_code .short_info_body').append(operHtml);
			} else {
				$.message.error('获取业务失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取业务Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
}


//获取产品
function getProducts(){
	$('#prod_code .short_info_body li').remove();
	var departData = {
			jsession_id: commonInfo.jsession_id,
			type_code: "prodCode"
		};

	$.ajax({
		type: "post",
		url: commonInfo.rest_host+"rest/ArtificialTaskRest/getTaskQueryInfo",
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
				var productHtml='';
				if (data.args.code_list != null && data.args.code_list.length>0) {
					$.each(data.args.code_list, function(i, item) {
						productHtml=productHtml+createQueryInfoHtml(i,item,3);
					});
				}
				$('#prod_code .short_info_body').append(productHtml);
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


//更新任务数，加载数，未加载数    type:1-逾期，2-未逾期，3-部门
function updateTaskNum(type,taskNum,loadNum){
	switch(type){
		case 1:
			$('#taskOuttimeList .card_head [name="taskCount"]').text(taskNum);
			$('#taskOuttimeList .card_foot [name="taskLoadCount"]').text(loadNum);
			$('#taskOuttimeList .card_foot [name="taskNotLoadCount"]').text(taskNum-loadNum);
			if(taskNum==loadNum){
				$('#taskOuttimeList .card_foot .more_issues').hide();
			}else{
				$('#taskOuttimeList .card_foot .more_issues').show();
			}
			break;
		case 2:
			$('#taskIntimeList .card_head [name="taskCount"]').text(taskNum);
			$('#taskIntimeList .card_foot [name="taskLoadCount"]').text(loadNum);
			$('#taskIntimeList .card_foot [name="taskNotLoadCount"]').text(taskNum-loadNum);
			if(taskNum==loadNum){
				$('#taskIntimeList .card_foot .more_issues').hide();
			}else{
				$('#taskIntimeList .card_foot .more_issues').show();
			}
			break;
		case 3:
			$('#taskNotgrabList .card_head [name="taskCount"]').text(taskNum);
			$('#taskNotgrabList .card_foot [name="taskLoadCount"]').text(loadNum);
			$('#taskNotgrabList .card_foot [name="taskNotLoadCount"]').text(taskNum-loadNum);
			if(taskNum==loadNum){
				$('#taskNotgrabList .card_foot .more_issues').hide();
			}else{
				$('#taskNotgrabList .card_foot .more_issues').show();
			}
			break;
		default:
			break;
	}
}


//重新加载事件
function reload(){
	//隐藏查看更多
	if($('#taskOuttimeList .card_foot [name="taskNotLoadCount"]').text()==0){
		$('#taskOuttimeList .card_foot .more_issues').hide();
	}
	if($('#taskIntimeList .card_foot [name="taskNotLoadCount"]').text()==0){
		$('#taskIntimeList .card_foot .more_issues').hide();
	}
	if($('#taskNotgrabList .card_foot [name="taskNotLoadCount"]').text()==0){
		$('#taskNotgrabList .card_foot .more_issues').hide();
	}
}


function clearQueryInfo() {
	$('#accept_time_start').val('');
	$('#accept_time_end').val('');
	$('#order_no').val('');
	$('#accpet_depart_no .active').removeClass('active');
	$('#tache_code .active').removeClass('active');
	$('#oper_code .active').removeClass('active');
	$('#prod_code .active').removeClass('active');
	$('#acc_nbr').val('');
	$('#cust_name').val('');
	$('#cert_no').val('');
}

//获取地区信息(UOC0044)
function getRegionInfo(){
	$.ajax({
		type: "post",
		url: commonInfo.rest_host+"rest/getInfoService/getDefaultProvinceCityAreaInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: {"jsession_id": commonInfo.jsession_id},
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				if(null != data.args){
					selectTaskInfo.region_info = data.args.default_region_info;
				}
			} else {
				$.message.error('无地区信息~');
			}
		},
		error: function(data) {
			$.message.error('获取地区信息失败!');
		},
		complete: function(){
			$.loading.hide();
		}
	});
}


/**
 * 获取撤单原因
 */
function getCancelReasons(){
	var cancleReasons=window.parent.Constant.cancelType();
	var reasonHtml='';
	if (cancleReasons != null && cancleReasons.length>0) {
		$.each(cancleReasons, function(i, item) {
			var appendHtml="<p code_id='"+item.code_id+"' type_code='"+item.type_code+"'><i></i><span>"+item.code_name+"</span></p>";
			reasonHtml=reasonHtml+appendHtml;
		});
	}else{
		$.message.error('查询撤单原因为空!');
	}
	$('#reason_list').append(reasonHtml);
	$('#oper_code .short_info_body li').remove();
}
/**
 * 撤单
 * @param json_info_ext
 */
function cancelOrder(data_ext){

	var data={
			"jsession_id":commonInfo.jsession_id,
			"order_no":selectTaskInfo.order_no,
			"oper_type":selectTaskInfo.order_type,
			"order_type":selectTaskInfo.order_type,
			"deal_code":"E00001",
			"deal_desc":"",
			"deal_system_no":"",
			"call_type":"1",
			"tache_code":selectTaskInfo.tache_code,
			"flow_skip":"1",
			"json_info_ext":JSON.stringify(data_ext)
	};
	$.ajax({
		type: "post",
		url: commonInfo.rest_host+"rest/ArtificialTaskRest/createHandingArtificialTask",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在撤单，请稍等...");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				$.dialog.alert(
						"撤单成功",
						"任务",
						"返回",
						function() {
					    	window.location.href = "task.jsp?tache_code="+commonInfo.tache_code+"&role_type="+commonInfo.role_type;
					    }
				);
			} else {
				$.message.error('撤单失败：'+data.content);
			}
		},
		error: function(data) {
			$.message.error('撤单Ajax请求失败!');
		},
		complete: function(){
			$.loading.hide();
		}
	});
}

//人工任务处理服务(UOC0015)
function taskProcess(){
	var data = {
			jsession_id: commonInfo.jsession_id,
			order_no: selectTaskInfo.order_no,
			oper_type:"101",
			order_type:"101",
			tache_code: selectTaskInfo.tache_code,
			call_type:"1"
		};

	$.ajax({
		type: "post",
		url: commonInfo.rest_host+"rest/ArtificialTaskRest/createHandingArtificialTask",
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
				if(INPUT_UTIL.isNull(commonInfo.tache_code)){
					$.dialog.confirm(
						    "处理成功,是否进入下一环节",
						    "任务",
						    "下一步",
						    "返回",
						    function() {
						    	getTaskDataList(0,1,10,1);
						    	getTaskDetail(selectTaskInfo.order_no,selectTaskInfo.order_type);
						    },
						    function() {
						    	window.location.href = "task.jsp?role_type="+commonInfo.role_type;
						    },
						    function() {
						    	window.location.href = "task.jsp?role_type="+commonInfo.role_type;
						    }
						);
				}else{
					$.dialog.alert(
							"处理成功",
							"任务",
							"返回",
							function() {
						    	window.location.href = "task.jsp?tache_code="+commonInfo.tache_code+"&role_type="+commonInfo.role_type;
						    }
					);
				}
			} else {
				$.message.error("人工任务处理异常:"+data.content);
			}
		},
		error: function(data) {
			$.message.error('人工任务处理Ajax请求失败!');

		},
		complete:function(){
			$.loading.hide();
		}
	});
};
