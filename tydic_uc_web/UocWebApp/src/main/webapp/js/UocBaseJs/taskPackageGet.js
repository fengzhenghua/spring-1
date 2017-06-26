var tache_code = "";
var role_type = "";
var rest_host = "";

$(document).ready(function() {
	tache_code = HTML_UTLS.getParam("tache_code");
	role_type = HTML_UTLS.getParam("role_type");
	rest_host = window.parent.operInfo.rest_host;

	getTaskPackageData();

	//领取任务包
	$('#get_task_package').on('click', function(e) {
		var pkg_id=$('#package_title .task-package-item i.selected').parent().attr('package_id');
		var pkg_num=$('#pkg_num').val();
		$.dialog.confirm(
			    "是否领取任务包",
			    "提示",
			    "确定",
			    "取消",
			    function() {
			    	getTaskPackage(pkg_id,pkg_num);
			    }
			);
	});

	var curIndex = 0, //当前index
	taskLen = $("#package_title li").length; //任务包总数
	//左箭头点击事件
	$("#prev").click(function(){
		if(curIndex==0){
			$.message.warning("到头了，不能再点了");
			return;
		}
		//根据curIndex进行上一个任务处理
		curIndex = (curIndex > 0) ? (--curIndex) : (taskLen - 1);
		changeTo(curIndex);
	});

	//右箭头点击事件
	$("#next").click(function(){
		if(curIndex==taskLen-5){
		 	$.message.warning("到头了，不能再点了");
			return;
		}
		curIndex = (curIndex < taskLen - 1) ? (++curIndex) : 0;
		changeTo(curIndex);
	 });

	//返回
	$('#close_task_package').on('click', function(e) {
		window.location.href = 'task.jsp?tache_code='+tache_code+'&role_type='+role_type;
	});
});

function changeTo(num){
    var goLeft = num * 203;
    $("#package_title").animate({left: "-" + goLeft + "px"},300);
}

function getTaskPackageData(){
	$('#package_title li').remove();
	$('#package_detail .package-details').remove();

	var data = {
			jsession_id: window.parent.operInfo.jsession_id,
			page_no: "1",
			page_size: "100"
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
				var titleHtml='';
				var detailHead='';
				var detailHtml='';

				if (data.args.task_pkg_list != null && data.args.task_pkg_list.length>0) {
					$.each(data.args.task_pkg_list, function(i, item) {
						titleHtml=titleHtml+createPackageTitleHtml(i,item);

						detailHead='<table class="package-details"' + (i==0?'style="display:table;"':'style="display:none;"') + 'package_id="'+item.pkg_id+'" >'
							+'<thead class="bg-graye6">'
							+'<tr class="border-gray">'
							+'<th><i class="icon icon-firm"></i><span class="tab-tip">业务</span></th>'
							+'<th><i class="icon icon-pro"></i><span class="tab-tip">产品</span></th>'
							+'<th><i class="icon icon-link"></i><span class="tab-tip">环节</span></th>'
							+'<th><i class="icon icon-num"></i><span class="tab-tip">数量</span></th>'
							+'</tr>'
							+'</thead>'
							+'<tbody class="bg-grayf7">';
						var detailInfo='';
						$.each(item.design_list,function(i,detailItem){
							detailInfo=detailInfo+createPackageDetailHtml(detailItem);
						});
						detailHtml = detailHtml+detailHead+detailInfo+'</tbody></table>';
					});
				}

				$('#package_title').append(titleHtml);
				$('#package_detail').append(detailHtml);
			} else {
				$.message.error('获取可选任务包异常：'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取可选任务包Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
			//选择任务包
			$(".task-package-item").on('click','p',function() {
				var $this = $(this);
				$.each($(".task-package-item p i"),function(i,item){
					if($(item).hasClass("selected")){
						$(item).removeClass("selected");
						$(item).parent().removeClass("border-blue");
						$(item).parent().addClass("border-gray");
						$(".package-list").find('table').hide();
					}
				});
				$this.find("i").addClass("selected");
				$this.removeClass("border-gray");
				$this.addClass("border-blue");
				if($this.children("i.selected")){
					var packageId=$this.attr("package_id");
					$(".package-list").find('table[package_id="'+packageId+'"]').show();
				}
			});
		}
	});
}

function createPackageTitleHtml(i,item){
	var html='<li class="task-package-item">'
			+'<p class="'+(i==0?"border-blue":"border-gray")+'" package_id="'+item.pkg_id+'">'
			+'<span>任务包'+(i+1)+'</span><u class="valign-middle"></u>'
			+'<txt class="pkg_name">'+item.pkg_name+'</txt>'
			+(i==0?'<i class="selected"></i>':'<i></i>')
			+'</p>'
			+'</li>';
	return html;
}

function createPackageDetailHtml(item){
	var html='	<tr class="border-gray-dashed">'
			+'		<td>'+(INPUT_UTIL.isNull(item.oper_code)?"":window.parent.Constant.operCode(item.oper_code))+'</td>'
			+'		<td>'+(INPUT_UTIL.isNull(item.product_id)?"":window.parent.Constant.prodCode(item.product_id))+'</td>'
			+'		<td>'+item.tache_name+'</td>'
			+'		<td>'+item.num+'</td>'
			+'	</tr>';
	return html;
}


function getTaskPackage(pkg_id,pkg_num){
	var data = {
			jsession_id: window.parent.operInfo.jsession_id,
			pkg_id: pkg_id,
			pkg_num:pkg_num
		};

	$.ajax({
		type: "post",
		url: rest_host+"rest/ArtificialTaskRest/getTaskPackage",
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
				$.dialog.confirm(
					    "成功领取"+data.args.taskCount+"个任务,是否返回任务列表",
					    "提示",
					    "确定",
					    "取消",
					    function() {
					    	window.location.href = "task.jsp?tache_code="+tache_code+"&role_type="+role_type;
					    }
					);
			} else {
				$.message.error('领取任务包失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('领取任务包Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
}
