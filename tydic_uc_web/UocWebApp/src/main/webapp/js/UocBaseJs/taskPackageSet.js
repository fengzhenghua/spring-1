var tache_code = "";
var jsession_id = "";
var rest_host = "";
var role_type = "";
var operHtml = "";
var procHtml = "";
var tacheHtml = "";
var roleShowHtml = "";
var isChanged = false;
var total_pages = 0;

$(document).ready(function() {
	tache_code = HTML_UTLS.getParam("tache_code");
	role_type = HTML_UTLS.getParam("role_type");
	jsession_id = window.parent.operInfo.jsession_id;
	rest_host = window.parent.operInfo.rest_host;

	$('body').on('click', function(e) {
		var target = $(e.target);
		if (target.closest('.caret').length == 0
				&& $('.caret').next(':visible').length > 0
				&& target.closest('.select1').length == 0) {
			$('.caret').next().hide();
			$('.caret').removeClass("caret-up").addClass("caret-down");
		}
	});

	/* 下拉菜单的显示与隐藏 */
	$(".container").on('click','.caret',function() {
		var $this = $(this);
		if ($this.hasClass("caret-down")) {
			$('.caret').next().hide();
			$('.caret').removeClass("caret-up").addClass("caret-down");

			var query_type= $this.attr("query_type");
			if(query_type == "oper"){
				$this.next().empty();
				$this.next().append(operHtml);
			}else if(query_type == "prod"){
				$this.next().empty();
				$this.next().append(procHtml);
			}else if(query_type == "tache"){
				$this.next().empty();
				$this.next().append(tacheHtml);
			}

			$this.next().show();
			$this.removeClass("caret-down");
			$this.addClass("caret-up");
		} else {
			$this.next().hide();
			$this.removeClass("caret-up");
			$this.addClass("caret-down");
		}
	});

	/* 选择下拉菜单项 */
	$(".container").on('click', '.select ul li', function() {
		var $this = $(this);
		var val = $this.text();
		var code = $this.attr('code_id');
		$this.parent().prevAll("input").val(val);
		$this.parent().prevAll("input").attr('code_id',code);
		$this.parent().hide();
		$this.parent().prevAll(".caret").removeClass("caret-up");
		$this.parent().prevAll(".caret").addClass("caret-down");
		isChanged = true;
	});

	/* 加减任务数量按钮事件 */
	$(".container").on('click','.number .add-btn', function() {
		var $this = $(this);
		var num = $this.prev().val();
		num++;
		$this.prev().val(num);
		isChanged = true;
	});
	$(".container").on('click','.number .minus-btn', function() {
		var $this = $(this);
		var num = $this.next().val();
		if (num >= 1) {
			num--;
		} else {
			$.message.info("数量不能小于0");
			return;
		}
		$this.next().val(num);
		isChanged = true;
	});

	/* 新增任务包按钮事件 */
	$(".add span").click(function() {
		var $this = $(this);
		newTaskPackage();
		$this.parent().next().slideToggle();
	});

	/* 删除按钮事件 */
	$('.container').on('click','tbody .del-btn',function() {
		var $this = $(this);
		if ($this.parent().parent().parent().children("tr").length == 1) {
			$.message.warning("不能再删了！！！");
			return;
		}
		$this.parent().parent().remove();
		isChanged = true;
	});
	$('.task-package').on('click','.task-list li .del-btn',function() {
		var $this = $(this);
		var oper_type="102";
		var pkg_id=$this.parents('[class="task-list"]').attr('package_id');
		var json_info={"codeTaskPkgDefine":[{"pkg_id":pkg_id}],"codeTaskPkgDesign":[{"pkg_id":pkg_id}],"codeTaskPkgApp":[{"pkg_id":pkg_id}]};
		$.dialog.confirm(
				"是否要删除该任务包",
			    "提示",
			    "确认",
			    "取消",
			    function() {
					taskPackageOperate(oper_type,json_info,$this);
			    }
			);
	});

	/* 选择角色 */
	$('.container').on('click','.role1 .rl-li',function(){
		var $this = $(this);
		if($this.hasClass('blue')){
			$this.removeClass('blue');
		}else{
			$this.addClass('blue');
		}
		isChanged = true;
	});

	/* 加一条按钮事件 */
	$(".container").on('click','.add-list',function() {
		var $this=$(this);
		addTaskList($this);
		isChanged = true;
	});

	/* 保存按钮事件 */
	$(".new-task-package").on('click','.save',function() {
		$this=$(this);
		var pkg_id=createPkgId();
		var oper_type="100";
		createPackageHtml($this.parent().parent(),pkg_id,"","",oper_type);
		isChanged = false;
	});
	$(".task-package").on('click','.save',function() {
		$this=$(this);
		var pkg_id=$this.parent().parent().next().attr('package_id');

		var design_ids=new Array();;
		$.each($this.parent().parent().find('[name="taskPackageList"] tbody tr'), function(i, item) {
			design_ids[i]=$(item).attr('design_id');
		});
		var app_ids=new Array();;
		$.each($this.parent().parent().find('.role1 span .selected'),function(i,item){
			app_ids[i]=$(item).attr('app_id');
		});

		var oper_type="101";
		createPackageHtml($this.parent().parent(),pkg_id,design_ids,app_ids,oper_type);
		isChanged = false;
	});

	/* 取消按钮事件 */
	$(".new-task-package").on('click','.cancel',function() {
		var $this = $(this);
		$this.parent().parent().remove();
		isChanged = false;
	});
	$(".task-package").on('click','.cancel',function() {
		var $this=$(this);
		$this.parent().parent().hide();
		$this.parent().parent().next().show();

		if(isChanged){
			isChanged = false;
			taskPackageQuery();
		}
	});

	/*编辑按钮事件*/
	$(".task-package").on('click','.edit-btn',function(){
		var $this=$(this);
		$this.parent().parent().prev().show();
		$this.parent().parent().hide();
	});

	/*重置按钮事件*/
	$('#resetBtn').on('click', function(e) {
		$('.search .select input').val('');
		$('.search .select input').attr('code_id', '');
	});

	/*查询按钮事件*/
	$("#searchBtn").on('click',function(){
		taskPackageQuery();
	});
	$(".search-btn").on('click',function(){
		taskPackageQuery();
	});


	// 跳到首页
	$('#pageFirst').on('click', function(e) {
		var page_no = "1";
		$("#pageNo").val(page_no);
		taskPackageQuery();
	});
	// 跳到上一页
	$('#pageUp').on('click', function(e) {
		if($("#pageNo").val() == "1"){
			return;
		}else{
			var page_no = parseInt($("#pageNo").val()) - 1;
			$("#pageNo").val(page_no);
			taskPackageQuery();
		}
	});
	// 跳到下一页
	$('#pageDown').on('click', function(e) {
		if($("#pageNo").val() == $("#totalPage").text()){
			return;
		}else{
			var page_no = parseInt($("#pageNo").val()) + 1;
			$("#pageNo").val(page_no);
			taskPackageQuery();
		}
	});
	// 跳到尾页
	$('#pageLast').on('click', function(e) {
		var page_no = total_pages;
		$("#pageNo").val(page_no);
		taskPackageQuery();
	});

	$("#pageSize").change(function(e){
		$("#pageNo").val("1");
		taskPackageQuery();
	});

	// 返回
	$('#close_task_package').on('click', function(e) {
		window.location.href = 'task.jsp?tache_code=' + tache_code+'&role_type='+role_type;
	});

	taskPackageQuery();
	getOperCodes();
	getProdCodes();
	getTacheCodes();

});


//任务包查询
function taskPackageQuery(oper_code,prod_code,tache_code,pkg_name){
	$('#taskpackageList').empty();
	var oper_code=$('#oper').attr('code_id');
	var prod_code=$('#prod').attr('code_id');
	var tache_code=$('#tache').attr('code_id');
	var pkg_name=$('#pkg_name').val();

	var data = {
			jsession_id : jsession_id,
			oper_code : oper_code,
			prod_code : prod_code,
			tache_code : tache_code,
			pkg_name : pkg_name,
			page_no: $("#pageNo").val(),
			page_size: $("#pageSize").val()
	};

	$.ajax({
		type: "post",
		url: rest_host+"rest/ArtificialTaskRest/getOptionalTaskPkgList",
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
				var detailHtml='';
				if (data.args.task_pkg_list != null && data.args.task_pkg_list.length>0) {
					$.each(data.args.task_pkg_list, function(i, item) {
						var hideHtml='<ul style="display: none;">'
							+'<li>'
							+'<b><i></i>任务包名称：</b>'
							+'<input class="task-package-name" placeholder="点我输入任务包名称哦" name="taskPackageName" value="'+item.pkg_name+'"/>'
							+'</li>'
							+'<li class="task">'
							+'<table class="add-task" name="taskPackageList">'
							+'<thead>'
							+'<tr>'
							+'<th><i></i>业务</th>'
							+'<th><i></i>产品</th>'
							+'<th><i></i>环节</th>'
							+'<th><i></i>数量</th>'
							+'<th></th>'
							+'</tr>'
							+'</thead>'
							+'<tbody>';

						var detailInfo='';
						$.each(item.design_list,function(i,detailItem){
							detailInfo=detailInfo+createPackageDetailHtml(detailItem);

							hideHtml+='<tr design_id="'+detailItem.id+'">'
								+'<td>'
								+'<div class="select">'
								+'<input placeholder="— 请选择 —" readonly code_id="'+detailItem.oper_code+'" value="'+window.parent.Constant.operCode(detailItem.oper_code)+'"/>'
								+'<b class="caret caret-down" query_type="oper"></b>'
								+'<ul>'
								+ operHtml
								+'</ul>'
								+'</div>'
								+'</td>'
								+'<td>'
								+'<div class="select">'
								+'<input placeholder="— 请选择 —" readonly code_id="'+detailItem.product_id+'" value="'+window.parent.Constant.prodCode(detailItem.product_id)+'"/>'
								+'<b class="caret caret-down" query_type="prod"></b>'
								+'<ul>'
								+ procHtml
								+'</ul>'
								+'</div>'
								+'</td>'
								+'<td>'
								+'<div class="select">'
								+'<input placeholder="— 请选择 —" readonly code_id="'+detailItem.tache_code+'" value="'+detailItem.tache_name+'"/>'
								+'<b class="caret caret-down" query_type="tache"></b>'
								+'<ul>'
								+ tacheHtml
								+'</ul>'
								+'</div>'
								+'</td>'
								+'<td>'
								+'<div class="number">'
								+'<span class="minus-btn">-</span>'
								+'<input class="num" value="'+detailItem.num+'"/>'
								+'<span class="add-btn">+</span>'
								+'</div>'
								+'</td>'
								+'<td>'
								+'<i class="del-btn"></i>'
								+'</td>'
								+'</tr>';
						});

						hideHtml+='</tbody>'
							+'</table>';

						getHideRoleHtml(item.app_list);

						hideHtml+='<div class="role role1 clearfix">'
						+'<div class="left rl">'
						+'<ul class="rl-ul">'
						+ roleShowHtml
						+'</ul>'
						+'</div>'
						+'<div class="right rr">'
						+'<div class="rr-top"></div>'
						+'<div class="rr-bottom"></div>'
						+'</div>'
						+'</li>'
						+'<li>'
						+'<span class="add-list"><i></i></span>'
						+'</li>'
						+'<li class="last-item">'
						+'<span class="save">保存</span>'
						+'<span  class="cancel">取消</span>'
						+'</li>'
						+'</ul>';

						var roleHtml='';
						$.each(item.app_list,function(i,app){
							roleHtml=roleHtml+'<li class="rl-li" app_id="'+app.id+'">'+app.role_id+'</li>';
						});


						detailHtml=detailHtml+hideHtml+'<ul class="task-list" package_id="'+item.pkg_id+'" >'
							+'<li>'
							+'<i class="edit-btn"></i>'
							+'<span>'+item.pkg_name+'</span>'
							+'<i class="del-btn"></i>'
							+'</li>'
							+'<li class="task">'
							+'<table class="add-task">'
							+'	<thead>'
							+'		<tr>'
							+'			<th><i></i>业务</th>'
							+'			<th><i></i>产品</th>'
							+'			<th><i></i>环节</th>'
							+'			<th><i></i>数量</th>'
							+'			<th></th>'
							+'		</tr>'
							+'	</thead>'
							+'	<tbody>'
							+detailInfo
							+'	</tbody>'
							+'</table>'
							+'<div class="role clearfix">'
							+'<div class="left rl">'
							+'<ul class="rl-ul">'
							+roleHtml
							+'</ul>'
							+'</div>'
							+'<div class="right rr">'
							+'<div class="rr-top"></div>'
							+'<div class="rr-bottom"></div>'
							+'</div>'
							+'</div>'
							+'</li>'
							+'</ul>';

					});

					total_pages = parseInt((data.args.total_num + (data.args.page_size - 1)) / data.args.page_size) ;
					$("#totalCount").html(data.args.total_num);
				}
				$("#totalPage").html(total_pages);
				$('#taskpackageList').prepend(detailHtml);
			} else {
				$.message.error('获取可选任务包异常：'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取可选任务包Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
			getRoles();
		}
	});

}


//任务包操作  oper_type:100-新增，101-修改，102-删除
function taskPackageOperate(oper_type,json_info,$this,html){
	var taskPackageData = {
	             "jsession_id":jsession_id,
	             "oper_type":oper_type,
	             "json_info":JSON.stringify(json_info)
	     };
	$.ajax({
	     type: "post",
	     url: rest_host+"rest/ordModServiceRest/codeTaskPkgMainten",
	     contentType: "application/x-www-form-urlencoded; charset=utf-8",
	     async: true,
	     data: taskPackageData,
	     dataType: "json",
	     crossDomain: true == !(document.all),
	     beforeSend: function() {
	         $.loading.show("正在处理");
	     },
	     success: function(data) {
	         if (data.respCode=="0000" ) {
                  if(oper_type=="100"){
                	  $('#taskpackageList').prepend(html);
                	  $this.parent().parent().prependTo('#taskpackageList').hide();
                	  taskPackageQuery();
                	  $.message.success("任务包添加成功！");
                  }else if(oper_type=="101"){
                	  $this.parent().parent().next().remove();
	              	  $this.parent().parent().hide();
	              	  $this.parent().parent().after(html);
                	  $.message.success("任务包修改成功！");
                  }else if(oper_type=="102"){
                	  $this.parent().parent().prev().remove();
              		  $this.parent().parent().remove();
                	  $.message.success("任务包删除成功！");
                  }
             } else {
                   $.message.error('任务包维护失败!' +data.content);
             }
	     },
	     error: function(data) {
            $.message.error('任务包维护Ajax请求失败!' );
	     },
	     complete: function(){
            $.loading.hide();
	     }
	});
}


//获取业务
function getOperCodes(){
	var operData = {
			jsession_id: jsession_id,
			type_code: "operCode"
		};

	$.ajax({
		type: "post",
		url: rest_host+"rest/ArtificialTaskRest/getTaskQueryInfo",
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
				if (data.args.code_list != null && data.args.code_list.length>0) {
					$.each(data.args.code_list, function(i, item) {
						operHtml=operHtml+createQueryInfoHtml(item,0);
					});
				}
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
function getProdCodes(){
	var departData = {
			jsession_id: jsession_id,
			type_code: "prodCode"
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
					$.each(data.args.code_list, function(i, item) {
						procHtml=procHtml+createQueryInfoHtml(item,1);
					});
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


//获取环节
function getTacheCodes(){
	var tachetData = {
			jsession_id: jsession_id
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
					$.each(data.args.TacheList, function(i, item) {
						tacheHtml=tacheHtml+createQueryInfoHtml(item,2);
					});
				}
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


function createPackageDetailHtml(detailItem){
	var html='<tr>'
			+'<td>'+window.parent.Constant.operCode(detailItem.oper_code)+'</td>'
			+'<td>'+window.parent.Constant.prodCode(detailItem.product_id)+'</td>'
			+'<td>'+detailItem.tache_name+'</td>'
			+'<td>'+detailItem.num+'</td>'
			+'</tr>';
	return html;
}


function getRoles(){
	roleShowHtml='';
	var roles=window.parent.operInfo.role_id.split(",");
//	var roleNms=window.parent.operInfo.role_name.split(",");  +roleNms[i]+' / '
	$.each(roles, function(i, item) {
		roleShowHtml=roleShowHtml+'<li class="rl-li">'+item+'</li>';
	});
}

function getHideRoleHtml(app_list){
	var role_ids=new Array();;
	$.each(app_list, function(i, item) {
		role_ids[i]=(item.role_id);
	});
	roleShowHtml="";
	var roles=window.parent.operInfo.role_id.split(",");

	$.each(roles, function(i, item) {
		var index=$.inArray(item, role_ids);
		var isSelect=($.inArray(item, role_ids)==-1)?false:true;
		roleShowHtml=roleShowHtml+'<li class="rl-li '+(isSelect?'blue" app_id="'+app_list[index].id+'"':'"')+'>'+item+'</li>';
	});
}

//type:0-业务，1-产品,2-环节
function createQueryInfoHtml(item,type){
	var content="";
	var code="";
	switch(type){
		case 0:
			content=item.code_name;
			code=item.code_id;
			break;
		case 1:
			content=item.code_name;
			code=item.code_id;
			break;
		case 2:
			content=item.tache_name;
			code=item.tache_code;
			break;
		default:
			break;
	}

	var html='<li code_id="'+code+'">'+content+'</li>';
	return html;
}


//生成pkg_id
function createPkgId(){
	var time=new Date().getTime();
	return time;
}

//点击新增任务包
function newTaskPackage(){
	var html = '<ul>'
		+'<li>'
		+'<b><i></i>任务包名称：</b>'
		+'<input class="task-package-name" placeholder="点我输入任务包名称哦" name="taskPackageName"/>'
		+'</li>'
		+'<li class="task">'
		+'<table class="add-task" name="taskPackageList">'
		+'<thead>'
		+'<tr>'
		+'<th><i></i>业务</th>'
		+'<th><i></i>产品</th>'
		+'<th><i></i>环节</th>'
		+'<th><i></i>数量</th>'
		+'<th></th>'
		+'</tr>'
		+'</thead>'
		+'<tbody>'
		+'<tr>'
		+'<td>'
		+'<div class="select">'
		+'<input placeholder="— 请选择 —" readonly/>'
		+'<b class="caret caret-down"></b>'
		+'<ul>'
		+operHtml
		+'</ul>'
		+'</div>'
		+'</td>'
		+'<td>'
		+'<div class="select">'
		+'<input placeholder="— 请选择 —" readonly/>'
		+'<b class="caret caret-down"></b>'
		+'<ul>'
		+ procHtml
		+'</ul>'
		+'</div>'
		+'</td>'
		+'<td>'
		+'<div class="select">'
		+'<input placeholder="— 请选择 —" readonly/>'
		+'<b class="caret caret-down"></b>'
		+'<ul>'
		+ tacheHtml
		+'</ul>'
		+'</div>'
		+'</td>'
		+'<td>'
		+'<div class="number">'
		+'<span class="minus-btn">-</span>'
		+'<input class="num" value="1"/>'
		+'<span class="add-btn">+</span>'
		+'</div>'
		+'</td>'
		+'<td>'
		+'<i class="del-btn"></i>'
		+'</td>'
		+'</tr>'
		+'</tbody>'
		+'</table>'
		+'<div class="role role1 clearfix">'
		+'<div class="left rl">'
		+'<ul class="rl-ul">'
		+ roleShowHtml
		+'</ul>'
		+'</div>'
		+'<div class="right rr">'
		+'<div class="rr-top"></div>'
		+'<div class="rr-bottom"></div>'
		+'</div>'
		+'</li>'
		+'<li>'
		+'<span class="add-list"><i></i></span>'
		+'</li>'
		+'<li class="last-item">'
		+'<span class="save">保存</span>'
		+'<span  class="cancel">取消</span>'
		+'</li>'
		+'</ul>';
	$('.new-task-package').append(html);
}
function addTaskList($this){
	var html ='<tr><td>'
		+'<div class="select">'
		+'<input placeholder="— 请选择 —" readonly/>'
		+'<b class="caret caret-down" query_type="oper"></b>'
		+'<ul>'
		+ operHtml
		+'</ul>'
		+'</div>'
		+'</td>'
		+'<td>'
		+'<div class="select">'
		+'<input placeholder="— 请选择 —" readonly/>'
		+'<b class="caret caret-down" query_type="prod"></b>'
		+'<ul>'
		+ procHtml
		+'</ul>'
		+'</div>'
		+'</td>'
		+'<td>'
		+'<div class="select">'
		+'<input placeholder="— 请选择 —" readonly/>'
		+'<b class="caret caret-down" query_type="tache"></b>'
		+'<ul>'
		+ tacheHtml
		+'</ul>'
		+'</div>'
		+'</td>'
		+'<td>'
		+'<div class="number">'
		+'<span class="minus-btn">-</span>'
		+'<input class="num" value="1"/>'
		+'<span class="add-btn">+</span>'
		+'</div>'
		+'</td>'
		+'<td>'
		+'<i class="del-btn"></i>'
		+'</td></tr>';
	$this.parent().prev().find("table tbody").append(html);
}
function createPackageHtml($this,pkg_id,design_ids,app_ids,oper_type) {
	var html = '<ul class="task-list" package_id="' + pkg_id + '">'
			+ '<li>'
			+ '<i class="edit-btn"></i>'
			+ '<span>' + $this.find('[name="taskPackageName"]').val() + '</span>'
			+ '<i class="del-btn"></i>'
			+ '</li>'
			+ '<li class="task">'
			+ '<table class="add-task">'
			+ '<thead>'
			+ '<tr>'
			+ '<th><i></i>业务</th>'
			+ '<th><i></i>产品</th>'
			+ '<th><i></i>环节</th>'
			+ '<th><i></i>数量</th>'
			+ '<th></th>'
			+ '</tr>'
			+ '</thead>'
			+ '<tbody>';

	var codeTaskPkgDesign=new Array();
	var checkFlag = true;
	$.each($this.find('[name="taskPackageList"] tbody tr'), function(i, item) {
		html += '<tr design_id="'+design_ids[i]+'">'
			+ '<td>' + $(item).find('td:eq(0) input').val() + '</td>'
			+ '<td>' + $(item).find('td:eq(1) input').val() + '</td>'
			+ '<td>' + $(item).find('td:eq(2) input').val() + '</td>'
			+ '<td>' + $(item).find('td:eq(3) input').val() + '</td>'
			+ '</tr>';

		var oper_code=$(item).find('td:eq(0) input').attr('code_id');
		var prod_code=$(item).find('td:eq(1) input').attr('code_id');
		var tache_code=$(item).find('td:eq(2) input').attr('code_id');
		if(INPUT_UTIL.isNull(oper_code)&&INPUT_UTIL.isNull(prod_code)&&INPUT_UTIL.isNull(tache_code)){
			checkFlag = false;
		}

		var num=$(item).find('td:eq(3) input').val();
		codeTaskPkgDesign[i]={
                "id":design_ids[i],
                "pkg_id":pkg_id,
                "oper_code":oper_code,
                "product_id":prod_code,
                "tache_code":tache_code,
                "num":num
        };
	});

	if(!checkFlag){
		$.message.info("任务包条件不能为空");
		return false;
	}

	var codeTaskPkgApp=new Array();

	html += '</tbody>'
		 + '</table>'
		 + '<div class="role clearfix">'
		 +'<div class="left rl">'
		 +'<ul class="rl-ul">';
	var isSelectRole=false;
	$.each($this.find('.role1 .blue'),function(i,item){
		isSelectRole=true;
		html+='<li class="rl-li" app_id="'+app_ids[i]+'">'+ $(item).text() +'</li>';
		codeTaskPkgApp[i]={
                "id":app_ids[i],
                "pkg_id":pkg_id,
                "role_id":$(item).text()
        };
	});
	html+='</ul>'
		+'</div>'
		+'<div class="right rr">'
		+'<div class="rr-top"></div>'
		+'<div class="rr-bottom"></div>'
		+'</div>'
		+'</div>'
		+'</li>'
		+'</ul>';

	var pkg_name=$this.find('[name="taskPackageName"]').val();

	if(INPUT_UTIL.isNull(pkg_name)|| !isSelectRole){
		$.message.info("任务包名称和角色不能为空");
		return;
	}
	var task_num=$this.find('[name="taskPackageList"] tbody tr').length;

	var json_info={
             "codeTaskPkgDefine":[{
                                "pkg_id":pkg_id,
                                "pkg_name":pkg_name,
                                "task_num" :task_num,
                                "state":"0",
                                "create_oper_no":window.parent.operInfo.oper_no,
                                "create_time":new Date().format('yyyy-MM-dd hh:mm:ss')
                                 }],
              "codeTaskPkgDesign":codeTaskPkgDesign,
              "codeTaskPkgApp":codeTaskPkgApp
     };

	taskPackageOperate(oper_type,json_info,$this.children().children(),html);

	return html;
}


/*dengjiao*/

$(document).ready(function(){
	//任务包右侧翻页特效
	var count = 0;//计数器
	//新增任务包点击上按钮(动态添加的事件)
	$('.new-task-package').on('click','.rr-top',function(){
		$this = $(this);
		var rl_ul = $this.parent('.rr').prev().find('.rl-ul');
		var liLen = rl_ul.find('li').length;//li的个数
//		var rl_ul_height = rl_ul.css('height',liLen * 30 +'px');//动态书写rl-ul的高度
//		var num = liLen/4;
		var page;//总页数
		var num = liLen % 4;
		if(num == 0){
			page = (liLen/4)-1;
		}else{
			page = Math.floor(liLen/4);//总页数
		}
		var top = rl_ul.css('margin-top');//获取ul的margin-top
		var height = rl_ul.css('height');//获取ul的height
		if(parseInt(top) < parseInt(height)){//120表示一次翻页翻4个li，一个li加上margin-bottom总高度为30，4个为120
			count++;
			if(count <= page){
				top = count * 120;
			}else{
				count = page;
			}
			rl_ul.css('margin-top','-'+top+'px');
		};
	});
	//新增任务包点击下按钮(动态添加的事件)
	$('.new-task-package').on('click','.rr-bottom',function(){
		$this = $(this);
		var rl_ul = $this.parent('.rr').prev().find('.rl-ul');
		var top = rl_ul.css('margin-top');//获取ul的margin-top
		if(count != 0 && parseInt(top ) != 0){
			count--;
			if(count == 0){
				top = 0;
			}else{
				top = count * 120;
			}
			rl_ul.css('margin-top','-'+top+'px');
		}
	});
	//任务包列表上按钮
	$('.task-package').on('click','.rr-top',function(){
		$this = $(this);
		var rl_ul = $this.parent('.rr').prev().find('.rl-ul');
		var liLen = rl_ul.find('li').length;//li的个数
		var page = Math.floor(liLen/4);//总页数
		var top = rl_ul.css('margin-top');//获取ul的margin-top
		var height = rl_ul.css('height');//获取ul的height
		if(parseInt(top) < parseInt(height)){
			count++;
			if(count < page){
				top = count * 120;
			}else{
				count = page;
				top = count * 120;
			}
			if(count = page){
				count = page - 1;
				top = count * 120;
			}
			rl_ul.css('margin-top','-'+top+'px');
		};
	});
	//任务包列表下按钮
	$('.task-package').on('click','.rr-bottom',function(){
		$this = $(this);
		var rl_ul = $this.parent('.rr').prev().find('.rl-ul');
//		var liLen = rl_ul.find('li').length;//li的个数
//		var page = Math.floor(liLen/4);//总页数
		var top = rl_ul.css('margin-top');//获取ul的margin-top
//		var height = rl_ul.css('height');//获取ul的height
		if(count != 0 && parseInt(top ) != 0){
			count--;
			top = count * 120;
			rl_ul.css('margin-top','-'+top+'px');
		}
	});

});

