var jsession_id ="";
var rest_host="";
var depart_no ="";
var oper_no ="";
var oper_code ="";
var prod_code ="";
var start_time ="";
var end_time ="";
var role_type ="";
var state = "0";

$(document).ready(function() {
	//获取url参数
	role_type = HTML_UTLS.getParam("role_type");
	//初始化数据
	init();

	// 显示/隐藏列表
	$('.short_info_head[control="foldable"]').on('click', function(e) {
		var $this = $(this);
		//$this.next().toggleClass('hide');
		$this.next().children('.active').toggleClass('hide');
		$this.next().children().toggleClass('hide');
	});
	$('.short_info_foot[control="foldable"]').on('click', function(e) {
		var $this = $(this);
		$this.prev().children('.active').toggleClass('hide');
		$this.prev().children().toggleClass('hide');
	});

	// 打开 筛选-弹出页面
	$('[name="searchBtns"]').on('click', function(e) {
		$.stopPropagation(e);
		var $this = $(this);
		var idx = $this.attr('idx');
		$('.pop_container').hide();
		$('[name="popSearchParts"][idx="' + idx + '"]').show();
		init();
	});

	getStatisticalTaskOverdueRateInfo(); // 加载逾期占比
	taskStatistics();					 // 加载环节占比
	getServiceOrderCancelRate();		 // 加载撤单占比
	getServiceOrderCompletionRate();	 // 加载竣工占比

//	loadChartPart1(); // 加载逾期占比
//	loadChartPart2(); // 加载环节占比
//	loadChartPart3(); // 加载竣工占比
//	loadChartPart4(); // 加载撤单占比

	//撤单筛选点击
	$("#cancel_rate_search").click(function(){
		depart_no =$("[name='sel_depart_code']").attr("id")==undefined?depart_no:$("[name='sel_depart_code']").attr("id");
		oper_no =$("[name='sel_oper_no']").attr("id")==undefined?oper_no:$("[name='sel_oper_no']").attr("id");
//		oper_code =$("[name='sel_oper_code']").attr("id");
//		prod_code =$("[name='sel_prod_code']").attr("id");
		oper_code=$("li.active[name='operCode']>span").attr("id")==undefined?oper_code:$("li.active[name='operCode']>span").attr("id");
		prod_code=$("li.active[name='prodCode']>span").attr("id")==undefined?prod_code:$("li.active[name='prodCode']>span").attr("id");
		start_time =$("#start_time").val();
		end_time =$("#end_time").val();

		if(start_time!=null&&start_time!=""&&start_time!=undefined){
			start_time=start_time+" 00:00:00";
		}
		if(end_time!=null&&end_time!=""&&end_time!=undefined){
			end_time=end_time+" 23:59:59";
		}

		getServiceOrderCancelRate();
		//重置筛选参数
		//reset();
	});

	//竣工筛选点击
	$("#complete_rate_search").click(function(){
		depart_no =$("[name='sel_depart_code2']").attr("id")==undefined?depart_no:$("[name='sel_depart_code2']").attr("id");
		oper_no =$("[name='sel_oper_no2']").attr("id")==undefined?oper_no:$("[name='sel_oper_no2']").attr("id");
//		oper_code =$("[name='sel_oper_code2']").attr("id");
//		prod_code =$("[name='sel_prod_code2']").attr("id");
		oper_code=$("li.active[name='operCode']>span").attr("id")==undefined?oper_code:$("li.active[name='operCode']>span").attr("id");
		prod_code=$("li.active[name='prodCode']>span").attr("id")==undefined?prod_code:$("li.active[name='prodCode']>span").attr("id");
		start_time =$("#start_time2").val();
		end_time =$("#end_time2").val();

		if(start_time!=null&&start_time!=""&&start_time!=undefined){
			start_time=start_time+" 00:00:00";
		}
		if(end_time!=null&&end_time!=""&&end_time!=undefined){
			end_time=end_time+" 23:59:59";
		}

		getServiceOrderCompletionRate();
		//重置筛选参数
		//reset();
	});

	//环节筛选点击
	$("#task_statistics_search").click(function(){
		depart_no =$("[name='sel_depart_code3']").attr("id")==undefined?depart_no:$("[name='sel_depart_code3']").attr("id");
		oper_no =$("[name='sel_oper_no3']").attr("id")==undefined?oper_no:$("[name='sel_oper_no3']").attr("id");
//		oper_code =$("[name='sel_oper_code3']").attr("id");
//		prod_code =$("[name='sel_prod_code3']").attr("id");
		oper_code=$("li.active[name='operCode']>span").attr("id")==undefined?oper_code:$("li.active[name='operCode']>span").attr("id");
		prod_code=$("li.active[name='prodCode']>span").attr("id")==undefined?prod_code:$("li.active[name='prodCode']>span").attr("id");
		start_time =$("#start_time3").val();
		end_time =$("#end_time3").val();
		if(start_time!=null&&start_time!=""&&start_time!=undefined){
			start_time=start_time+" 00:00:00";
		}
		if(end_time!=null&&end_time!=""&&end_time!=undefined){
			end_time=end_time+" 23:59:59";
		}

		//state=$("[name='sel_state_code1']").attr("id");
		state=$("li.active[name='state']>span").attr("id")==undefined?state:$("li.active[name='state']>span").attr("id");

		taskStatistics();
		//重置筛选参数
		//reset();
	});

	//逾期筛选点击
	$("#overdue_rate_search").click(function(){
		depart_no =$("[name='sel_depart_code4']").attr("id")==undefined?depart_no:$("[name='sel_depart_code4']").attr("id");
		oper_no =$("[name='sel_oper_no4']").attr("id")==undefined?oper_no:$("[name='sel_oper_no4']").attr("id");
//		oper_code =$("[name='sel_oper_code4']").attr("id");
//		prod_code =$("[name='sel_prod_code4']").attr("id");
		oper_code=$("li.active[name='operCode']>span").attr("id")==undefined?oper_code:$("li.active[name='operCode']>span").attr("id");
		prod_code=$("li.active[name='prodCode']>span").attr("id")==undefined?prod_code:$("li.active[name='prodCode']>span").attr("id");
		start_time =$("#start_time4").val();
		end_time =$("#end_time4").val();
		//state=$("[name='sel_state_code1']").attr("id");
		state=$("li.active[name='state']>span").attr("id")==undefined?state:$("li.active[name='state']>span").attr("id");
		getStatisticalTaskOverdueRateInfo();
		//重置筛选参数
		//reset();
	});
});


function init(){
	var operInfo =window.parent.operInfo;
	jsession_id=operInfo.jsession_id;
	rest_host= operInfo.rest_host;
	depart_no =operInfo.depart_no;
	if(role_type==1){
		oper_no =operInfo.oper_no;
	}

	$("[name='taskState']").empty();
	var stateCodes=window.parent.Constant.taskState();
	$.each(stateCodes, function(i,n){
		$("[name='taskState']").append(
				'<li class="line hide" name="state">'+
				'<i class="icon_business"></i><span id="'+stateCodes[i].code_id+'">'+stateCodes[i].code_name+'</span>'+
				'</li>');
		});

	$("[name='state']").click(function(){
		if($(this).hasClass("active")){
			$(this).removeClass("active");
			state = "0";
		}else{
			$(this).siblings().removeClass('active');
			$(this).addClass("active");
			$(this).removeClass("hide");
			$(this).siblings().removeClass("hide").addClass("hide");
		}

//		$(this).parent().next().children().text($(this).children("span").text());
//		$(this).parent().next().children().attr("id",$(this).children("span").attr('id'));
//		$(this).parent().toggleClass('hide');
	});


	$("[name='operCodes']").empty();
	var operCodes=window.parent.Constant.operCode();
	$.each(operCodes, function(i,n){
		$("[name='operCodes']").append(
				'<li class="line hide" name="operCode">'+
				'<i class="icon_business"></i><span id="'+operCodes[i].code_id+'">'+operCodes[i].code_name+'</span>'+
				'</li>');
		});

	$("[name='operCode']").click(function(){
		if($(this).hasClass("active")){
			$(this).removeClass("active");
			oper_code ="";
		}else{
			$(this).siblings().removeClass('active');
			$(this).addClass("active");
			$(this).removeClass("hide");
			$(this).siblings().removeClass("hide").addClass("hide");
		}
//		$(this).parent().next().children().text($(this).children("span").text());
//		$(this).parent().next().children().attr("id",$(this).children("span").attr('id'));
//		$(this).parent().toggleClass('hide');
	});

	$("[name='prodCodes']").empty();
	var prodCodes=window.parent.Constant.prodCode();
	$.each(prodCodes, function(i,n){
		$("[name='prodCodes']").append(
				'<li class="line hide" name="prodCode">'+
				'<i class="icon_business"></i><span id="'+prodCodes[i].code_id+'">'+prodCodes[i].code_name+'</span>'+
				'</li>');
		});
	//点击事件处理
	$("[name='prodCode']").click(function(){
		if($(this).hasClass("active")){
			$(this).removeClass("active");
			prod_code ="";
		}else{
			$(this).siblings().removeClass('active');
			$(this).addClass("active");
			$(this).removeClass("hide");
			$(this).siblings().removeClass("hide").addClass("hide");
		}
//		$(this).parent().next().children().text($(this).children("span").text());
//		$(this).parent().next().children().attr("id",$(this).children("span").attr('id'));
//		$(this).parent().toggleClass('hide');
	});

	//加载获取部门、工号信息
	getDepartments();
	getOperaters();
}

function loadChartPart1(message) {
	$('#chartPie1').highcharts({
		chart: {
			plotBackgroundColor: null,
			plotBorderWidth: null,
			plotShadow: false,
			type: 'pie'
		},
		title: {
			text: '逾期占比'
		},
		tooltip: {
			pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		},
		plotOptions: {
			pie: {
				allowPointSelect: false,
				cursor: 'pointer',
				dataLabels: {
					enabled: true,
					format: '<b>{point.name}</b>: {point.percentage:.1f} %',
					style: {
						color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
					}
				}
			}
		},
		series: [{
			name: '比例',
			colorByPoint: true,
			data: [
		       {name:'未逾期', y:message.args.other_num},
		       {name:'&lt;1小时', y:message.args.late_num1},
		       {name:'&lt;2小时', y:message.args.late_num2},
		       {name:'&lt;12小时', y:message.args.late_num3},
		       {name:'&lt;24小时', y:message.args.late_num4},
		       {name:'&gt;24小时及其他', y:message.args.late_num5}
	       ]
        }]
    });
	$("#not_late").text(message.args.other_num);
	$("#late_num1").text(message.args.late_num1);
	$("#late_num2").text(message.args.late_num2);
	$("#late_num3").text(message.args.late_num3);
	$("#late_num4").text(message.args.late_num4);
	$("#late_num5").text(message.args.late_num5);
}

function loadChartPart2(message) {
	$("#tacheRate").empty();
	var total_num =message.args.total_num;
	var tache_num_list =message.args.tache_num_list;
	var data = [];
	if(tache_num_list == null){
		$.message.error('环节占比统计无数据!');
	}else{
		$.each(tache_num_list, function(i,n){
			var v={name:n.task_name, y:n.tache_count*1};
			$("#tacheRate").append("<li>"+n.task_name+"："+n.tache_count+"</li>");
			data.push(v);
		});
	}

	$('#chartPie2').highcharts({
		chart: {
			plotBackgroundColor: null,
			plotBorderWidth: null,
			plotShadow: false,
			type: 'pie'
		},
		title: {
			text: '环节占比'
		},
		tooltip: {
			pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		},
		plotOptions: {
			pie: {
				allowPointSelect: false,
				cursor: 'pointer',
				dataLabels: {
					enabled: true,
					format: '<b>{point.name}</b>: {point.percentage:.1f} %',
					style: {
						color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
					}
				}
			}
		},
		series: [{
			name: '比例',
			colorByPoint: true,
			data:data
//			data: [
//		       {name:'预订单创建', y:40},
//		       {name:'预订单信息录入', y:40},
//		       {name:'资源确认', y:20}
//	       ]
        }]
    });
}

function loadChartPart3(message) {
	$('#chartPie3').highcharts({
		chart: {
			plotBackgroundColor: null,
			plotBorderWidth: null,
			plotShadow: false,
			type: 'pie'
		},
		title: {
			text: '竣工占比'
		},
		tooltip: {
			pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		},
		plotOptions: {
			pie: {
				allowPointSelect: false,
				cursor: 'pointer',
				dataLabels: {
					enabled: true,
					format: '<b>{point.name}</b>: {point.percentage:.1f} %',
					style: {
						color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
					}
				}
			}
		},
		series: [{
			name: '比例',
			colorByPoint: true,
			data: [
		       {name:'未竣工数', y:message.args.other_num},
		       {name:'竣工数', y:message.args.finish_num}
	       ]
        }]
    });

	$("#compelete_num").text(message.args.finish_num);
	$("#uncompelete_num").text(message.args.other_num);
}

function loadChartPart4(message) {
	$('#chartPie4').highcharts({
		chart: {
			plotBackgroundColor: null,
			plotBorderWidth: null,
			plotShadow: false,
			type: 'pie'
		},
		title: {
			text: '撤单占比'
		},
		tooltip: {
			pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		},
		plotOptions: {
			pie: {
				allowPointSelect: false,
				cursor: 'pointer',
				dataLabels: {
					enabled: true,
					format: '<b>{point.name}</b>: {point.percentage:.1f} %',
					style: {
						color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
					}
				}
			}
		},
		series: [{
			name: '比例',
			colorByPoint: true,
			data: [
		       {name:'撤单数', y:message.args.cancel_num},
		       {name:'有效订单数', y:message.args.other_num}
	       ]
        }]
    });
	$("#cancel_num").text(message.args.cancel_num);
	$("#valid_num").text(message.args.other_num);
}


//订单撤单率统计   接口编号UOC0053
function getServiceOrderCancelRate(){

	var data1 ={
			jsession_id :jsession_id,
			depart_no :depart_no,
			oper_no :oper_no,
			oper_code:oper_code,
			prod_code:prod_code,
			start_time:start_time,
			end_time:end_time
	};

	$.ajax({
		url:rest_host + "rest/StatisticalService/getServiceOrderCancelRate",
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		type:'post',
		crossDomain: true == !(document.all),
		data:data1,
		beforeSend: function() {
			$.loading.show();
		},
		success:function(message){
			if(message.respCode=="0000"){
				loadChartPart4(message);
			}else{
				$.message.error('订单竣工率统计失败'+message.content);
			}
		},
		complete:function(){
			$.loading.hide();
		}
	});

}

//订单竣工率统计   接口编号UOC0038
function getServiceOrderCompletionRate(){

	var data1 ={
			jsession_id :jsession_id,
			depart_no :depart_no,
			oper_no :oper_no,
			oper_code:oper_code,
			prod_code:prod_code,
			start_time:start_time,
			end_time:end_time
	};

	$.ajax({
		url:rest_host + "rest/StatisticalService/getServiceOrderCompletionRate",
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		type:'post',
		crossDomain: true == !(document.all),
		data:data1,
		beforeSend: function() {
			$.loading.show();
		},
		success:function(message){
			if(message.respCode=="0000"){
				loadChartPart3(message);
			}else{
				$.message.error('订单竣工率统计失败'+message.content);
			}

		},
		complete:function(){
			$.loading.hide();
		}
	});

}

//任务逾期率统计   接口编号UOC0054
function getStatisticalTaskOverdueRateInfo(){
	var data1 ={
			jsession_id :jsession_id,
			depart_no :depart_no,
			oper_no :oper_no,
			oper_code:oper_code,
			prod_code:prod_code,
			tache_code:"",
			start_time:start_time,
			end_time:end_time,
			state:state
	};

	$.ajax({
		url:rest_host + "rest/StatisticalService/getStatisticalTaskOverdueRateInfo",
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		type:'post',
		crossDomain: true == !(document.all),
		data:data1,
		beforeSend: function() {
			$.loading.show();
		},
		success:function(message){

				if(message.respCode=="0000"){
					loadChartPart1(message);
				}else{
					$.message.error('订单竣工率统计失败'+message.content);
				}
		},
		complete:function(){
			$.loading.hide();
		}
	});

}

//任务环节统计计   接口编号UOC0055
function taskStatistics(){
	var data1 ={
			jsession_id :jsession_id,
			depart_no :depart_no,
			oper_no :oper_no,
			oper_code:oper_code,
			prod_code:prod_code,
			tache_code:"",
			start_time:start_time,
			end_time:end_time,
			state:state
	};

	$.ajax({
		url:rest_host + "rest/ArtificialTaskRest/taskStatistical",
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		type:'post',
		crossDomain: true == !(document.all),
		data:data1,
		beforeSend: function() {
			$.loading.show();
		},
		success:function(message){
			if(message.respCode=="0000"){
				loadChartPart2(message);
			}else{
				$.message.error('订单竣工率统计失败'+message.content);
			}
		},
		complete:function(){
			$.loading.hide();
		}
	});

}

//获取部门
function getDepartments(){
	$("[name='departCodes']").empty();
	if(role_type==1){
		$("[name='departCodes']").parent().hide();
	}else if(role_type==2){
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
			crossDomain: true == !(document.all),
			dataType: "json",
			beforeSend: function() {
				$.loading.show("正在加载");
			},
			success: function(data) {
				if (data.respCode=="0000") {
					if(data.args.json_info!=''){
						var departInfo=JSON.parse(data.args.json_info).depart_list;
						if (departInfo != null && departInfo.length>0) {
							$.each(departInfo, function(i, item) {
								$("[name='departCodes']").append(
									'<li class="line hide" name="department">'+
										'<i class="icon_department"></i><span id="'+item.dept_no+'">'+item.dept_name+'</span>'+
									'</li>'
								);
							});

							$("[name='department']").click(function(){
								$(this).parent().next().children().text($(this).children("span").text());
								$(this).parent().next().children().attr("id",$(this).children("span").attr('id'));
								$(this).parent().toggleClass('hide');
							});
						}
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
}


//获取工号
function getOperaters(){
	$("[name='operNo']").empty();
	if(role_type==1){
		$("[name='operNo']").parent().hide();
	}else if(role_type==2){
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
				if (data.respCode=="0000") {
					if(data.args.json_info!=''){
						var operInfo=JSON.parse(data.args.json_info).OperInfo;

						if (operInfo != null && operInfo.length>0) {
							$.each(operInfo, function(i, item) {
								$("[name='operNo']").append(
										'<li class="line hide" name="operName">'+
										'<i class="icon_staff"></i><span id="'+item.oper_no+'">'+item.oper_name+'</span>'+
										'</li>'
								);
							});
						}
					}

					$("[name='operName']").click(function(){
						$(this).parent().next().children().text($(this).children("span").text());
						$(this).parent().next().children().attr("id",$(this).children("span").attr('id'));
						$(this).parent().toggleClass('hide');
					});
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

}

function reset(){
	$("[name='sel_depart_code']").attr("id","");
	$("[name='sel_oper_no']").attr("id","");
    $("[name='sel_oper_code']").attr("id","");
	$("[name='sel_prod_code']").attr("id","");
	$("[name='sel_depart_code']").text("...所有部门");
	$("[name='sel_oper_no']").text("...所有员工");
    $("[name='sel_oper_code']").text("...所有业务");
	$("[name='sel_prod_code']").text("...所有产品");
	$("#start_time").val("");
	$("#end_time").val("");

	$("[name='sel_depart_code2']").attr("id","");
	$("[name='sel_oper_no2']").attr("id","");
    $("[name='sel_oper_code2']").attr("id","");
	$("[name='sel_prod_code2']").attr("id","");
	$("[name='sel_depart_code2']").text("所有部门");
	$("[name='sel_oper_no2']").text("...所有员工");
    $("[name='sel_oper_code2']").text("所有业务");
	$("[name='sel_prod_code2']").text("所有产品");
	$("#start_time2").val("");
	$("#end_time2").val("");

	$("[name='sel_depart_code3']").attr("id","");
	$("[name='sel_oper_no3']").attr("id","");
    $("[name='sel_oper_code3']").attr("id","");
	$("[name='sel_prod_code3']").attr("id","");
	$("[name='sel_depart_code3']").text("...所有部门");
	$("[name='sel_oper_no3']").text("...所有员工");
    $("[name='sel_oper_code3']").text("...所有业务");
	$("[name='sel_prod_code3']").text("...所有产品");
	$("#start_time3").val("");
	$("#end_time3").val("");

	$("[name='sel_depart_code4']").attr("id","");
	$("[name='sel_oper_no4']").attr("id","");
    $("[name='sel_oper_code4']").attr("id","");
	$("[name='sel_prod_code4']").attr("id","");
	$("[name='sel_depart_code4']").text("...所有部门");
	$("[name='sel_oper_no4']").text("...所有员工");
    $("[name='sel_oper_code4']").text("...所有业务");
	$("[name='sel_prod_code4']").text("...所有产品");
	$("#start_time4").val("");
	$("#end_time4").val("");

	$("[name='sel_state_code1']").attr("id","0");
	$("[name='sel_state_code1']").text("...未完成任务");
}