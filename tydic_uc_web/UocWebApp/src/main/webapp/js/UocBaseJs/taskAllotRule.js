var jsession_id = "";
var rest_host = "";
var isUpdate = false;
var selectItem = null;
var role_type = "";

$(document).ready(function() {
	jsession_id = window.parent.operInfo.jsession_id;
	rest_host = window.parent.operInfo.rest_host;
	role_type = HTML_UTLS.getParam("role_type");

	//TODO 超级管理员权限
	if(role_type!=1){
		$('#target_depart_no').hide();
		$('#target_depart_no').parent().prev().hide();
	}

	getAssignRuleList();
	loadTacheCodes();
	loadOperCodes();
	loadOperNo("",4);
	loadDepartNo("",4);
	loadOperNoGroups("");

	$('#isOperNo').on("click", function() {
		if($('#isOperNo').is(':checked') == true){
			$('#modal_oper_no_group').parent().hide();
			$('#modal_oper_no_group').parent().prev().hide();
			$('#modal_oper_no_group').val("");
			$('#modal_oper_no_group').attr("no","");
			$('#modal_target_oper_no').parent().show();
			$('#modal_target_oper_no').parent().prev().show();
		}else{
			$('#modal_target_oper_no').parent().hide();
			$('#modal_target_oper_no').parent().prev().hide();
			$('#modal_target_oper_no').val("");
			$('#modal_target_oper_no').attr("no","");
		}
	});
	$('#isOperNoGroup').on("click", function() {
		if($('#isOperNoGroup').is(':checked') == true){
			$('#modal_target_oper_no').parent().hide();
			$('#modal_target_oper_no').parent().prev().hide();
			$('#modal_target_oper_no').val("");
			$('#modal_target_oper_no').attr("no","");
			$('#modal_oper_no_group').parent().show();
			$('#modal_oper_no_group').parent().prev().show();
		}else{
			$('#modal_oper_no_group').parent().hide();
			$('#modal_oper_no_group').parent().prev().hide();
			$('#modal_oper_no_group').val("");
			$('#modal_oper_no_group').attr("no","");
		}
	});

	// 单选卡-关闭更多事件
	$('body').on('click', function(e) {
		var target = $(e.target);
		if (target.closest('.option_box').length == 0
				&& $('.option_box .more_option:visible').length > 0) {
			$('.option_box .more_option').addClass('hide');
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

	// 多选卡-选中事件
	$('.option_box[type="checkbox"]').on('click', 'a', function(e) {
		var $this = $(this);
		if ($this.hasClass('active')) {
			$this.removeClass('active');
		} else {
			$this.addClass('active');
		}
	});

	// 默认分配规则模态框属性设置
	$.modal('#allotRuleModal', {
        width: 630,
        height: 320
    });

	// 查询 按钮事件
	$('#searchBtn').on('click', function(e) {
		getAssignRuleList();
	});

	// 重置 按钮事件
	$('#resetBtn').on('click', function(e) {
		reset(1);
	});

	// 工号组设置 按钮事件
	$('#specialRuleSetBtn').on('click', function(e) {
		window.location.href = 'taskSpecialRule.jsp';
	});

	// 新增默认分配规则 按钮事件
	$('#allotRuleAddBtn').on('click', function(e) {
		isUpdate = false;
		reset(2);
		$('#allotRuleModal').show();
		$('#modal_target_depart_no').attr('no',window.parent.operInfo.depart_no);
		$('#modal_target_depart_no').val(window.parent.operInfo.depart_no+' / '+window.parent.operInfo.depart_name);
	});
	$('#allotRuleCancelBtn').on('click', function(e) {
		$('#allotRuleModal').hide();
	});

	// 修改 按钮事件
	$('#allotRuleList').on('click', 'td .btn_link[name="editBtn"]', function(e) {
		var $this = $(this);
		isUpdate = true;
		reset(2);
		selectItem = $this.parent().parent();
		var list=$('.option_box a.active');
		if (list.length > 0) {
			$.each(list, function(i, item) {
				$(item).removeClass('active');
			});
		}

		if(selectItem.find('td:eq(5)').attr('code').toString().indexOf("group_")>-1){
			$('#isOperNoGroup').trigger("click");
			$('#modal_oper_no_group').parent().show();
			$('#modal_oper_no_group').parent().prev().show();
			$('#modal_oper_no_group').attr('no',selectItem.find('td:eq(5)').attr('code'));
			$('#modal_oper_no_group').val(selectItem.find('td:eq(5)').text());
			$('#modal_target_oper_no').parent().hide();
			$('#modal_target_oper_no').parent().prev().hide();
			$('#modal_target_oper_no').val("");
			$('#modal_target_oper_no').attr("no","");
		}else{
			$('#isOperNo').trigger("click");
			$('#modal_target_oper_no').parent().show();
			$('#modal_target_oper_no').parent().prev().show();
			$('#modal_target_oper_no').attr('no',selectItem.find('td:eq(5)').attr('code'));
			$('#modal_target_oper_no').val(selectItem.find('td:eq(5)').text());
			$('#modal_oper_no_group').parent().hide();
			$('#modal_oper_no_group').parent().prev().hide();
			$('#modal_oper_no_group').val("");
			$('#modal_oper_no_group').attr("no","");
		}
		$('#modalTacheCode').val(selectItem.find('td:eq(0)').attr('code'));
		$('#modalOperCode').val(selectItem.find('td:eq(1)').attr('code'));
		$('#modal_target_depart_no').attr('no',selectItem.find('td:eq(4)').attr('code'));
		$('#modal_target_depart_no').val(selectItem.find('td:eq(4)').text());
		$('#modal_accept_depart_no').attr('no',selectItem.find('td:eq(2)').attr('code'));
		$('#modal_accept_depart_no').val(selectItem.find('td:eq(2)').text());
		$('#modal_accept_oper_no').attr('no',selectItem.find('td:eq(3)').attr('code'));
		$('#modal_accept_oper_no').val(selectItem.find('td:eq(3)').text());
		$('#allotRuleModal').show();
	});
	$('#allotRuleConfirmBtn').on('click', function(e) {
		if(isUpdate){
			assignRuleOperate("101");
		}else{
			assignRuleOperate("100");
		}
	});

	// 删除 按钮事件
	$('#allotRuleList').on('click', 'td .btn_link[name="delBtn"]', function(e) {
		var $this = $(this);
		$.dialog.confirm('确定删除此行数据吗？', '删除', '确定', '取消',
		    function() {
				selectItem = $this.parent().parent();
				if(selectItem.find('td:eq(5)').attr('code').toString().indexOf("group_")>-1){
					$('#isOperNoGroup').trigger("click");
					$('#modal_oper_no_group').parent().show();
					$('#modal_oper_no_group').parent().prev().show();
					$('#modal_oper_no_group').attr('no',selectItem.find('td:eq(5)').attr('code'));
					$('#modal_oper_no_group').val(selectItem.find('td:eq(5)').text());
					$('#modal_target_oper_no').parent().hide();
					$('#modal_target_oper_no').parent().prev().hide();
					$('#modal_target_oper_no').val("");
					$('#modal_target_oper_no').attr("no","");
				}else{
					$('#isOperNo').trigger("click");
					$('#modal_target_oper_no').parent().show();
					$('#modal_target_oper_no').parent().prev().show();
					$('#modal_target_oper_no').attr('no',selectItem.find('td:eq(5)').attr('code'));
					$('#modal_target_oper_no').val(selectItem.find('td:eq(5)').text());
					$('#modal_oper_no_group').parent().hide();
					$('#modal_oper_no_group').parent().prev().hide();
					$('#modal_oper_no_group').val("");
					$('#modal_oper_no_group').attr("no","");
				}
				$('#modalTacheCode').val(selectItem.find('td:eq(0)').attr('code'));
				$('#modalOperCode').val(selectItem.find('td:eq(1)').attr('code'));
				$('#modal_target_depart_no').attr('no',selectItem.find('td:eq(4)').attr('code'));
				$('#modal_target_depart_no').val(selectItem.find('td:eq(4)').text());
				$('#modal_accept_depart_no').attr('no',selectItem.find('td:eq(2)').attr('code'));
				$('#modal_accept_depart_no').val(selectItem.find('td:eq(2)').text());
				$('#modal_accept_oper_no').attr('no',selectItem.find('td:eq(3)').attr('code'));
				$('#modal_accept_oper_no').val(selectItem.find('td:eq(3)').text());
				assignRuleOperate("102");
		    }
		);
	});

	// 受理部门模态框属性设置
	$.modal('#acceptDepartNoModal', {
        width: 550,
        height: 350
    });
	// 弹出受理部门模态框
	var acceptDepartNo = '';
	$('#accept_depart_no, #modal_accept_depart_no').on('click', function(e) {
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
		loadDepartNo($('#accept_depart_no_search').val(),1);
	});

	// 目标部门模态框属性设置
	$.modal('#targetDepartNoModal', {
        width: 550,
        height: 350
    });
	// 弹出目标部门模态框
	var targetDepartNo = '';
	//TODO 超级管理员权限
	if(role_type == 1){
		$('#target_depart_no ,#modal_target_depart_no').on('click', function(e) {
			targetDepartNo = $(this).attr('id');
			$('#targetDepartNoModal').show();
		});
	}
	// 目标部门模态框-确定按钮事件
	$('#targetDepartNoConfirmBtn').on('click', function(e) {
		var inputBox = $('#' + targetDepartNo);
		inputBox.val('');
		inputBox.attr('no', '');
		var activeNo = $('#targetDepartNoList a.active');
		if (activeNo.length > 0) {
			inputBox.val(activeNo.text());
			inputBox.attr('no', activeNo.attr('no'));
		}
		$('#targetDepartNoModal').hide();
	});
	// 目标部门模态框-取消按钮事件
	$('#targetDepartNoCancelBtn').on('click', function(e) {
		$('#targetDepartNoModal').hide();
	});
	// 目标部门模态框-查询按钮事件
	$('#targetDepartNoSearchBtn').on('click', function(e) {
		loadDepartNo($('#target_depart_no_search').val(),2);
	});


	// 受理工号模态框属性设置
	$.modal('#acceptOperNoModal', {
        width: 550,
        height: 350
    });
	// 弹出受理工号模态框
	var acceptOperNo = '';
	$('#accept_oper_no, #modal_accept_oper_no').on('click', function(e) {
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
		loadOperNo($('#accept_oper_no_search').val(),1);
	});

	// 目标工号模态框属性设置
	$.modal('#targetOperNoModal', {
        width: 550,
        height: 350
    });
	// 弹出目标工号模态框
	var targetOperNo = '';
	$('#target_oper_no ,#modal_target_oper_no').on('click', function(e) {
		targetOperNo = $(this).attr('id');
		$('#targetOperNoModal').show();
	});
	// 目标工号模态框-确定按钮事件
	$('#targetOperNoConfirmBtn').on('click', function(e) {
		var inputBox = $('#' + targetOperNo);
		inputBox.val('');
		inputBox.attr('no', '');
		var activeNo = $('#targetOperNoList a.active');
		if (activeNo.length > 0) {
			inputBox.val(activeNo.text());
			inputBox.attr('no', activeNo.attr('no'));
		}
		$('#targetOperNoModal').hide();
	});
	// 目标工号模态框-取消按钮事件
	$('#targetOperNoCancelBtn').on('click', function(e) {
		$('#targetOperNoModal').hide();
	});
	// 目标工号模态框-查询按钮事件
	$('#targetOperNoSearchBtn').on('click', function(e) {
		loadOperNo($('#target_oper_no_search').val(),2);
	});


	// 工号组模态框属性设置
	$.modal('#operNoGroupModal', {
        width: 550,
        height: 350
    });
	// 弹出工号组模态框
	var operNo = '';
	$('#oper_no_group, #modal_oper_no_group').on('click', function(e) {
		operNo = $(this).attr('id');
		$('#operNoGroupModal').show();
	});
	// 工号组模态框-确定按钮事件
	$('#operNoGroupConfirmBtn').on('click', function(e) {
		var inputBox = $('#' + operNo);
		inputBox.val('');
		inputBox.attr('no', '');
		var activeNo = $('#operNoGroupList a.active');
		if (activeNo.length > 0) {
			inputBox.val(activeNo.text());
			inputBox.attr('no', activeNo.attr('no'));
		}
		$('#operNoGroupModal').hide();
	});
	// 工号组模态框-取消按钮事件
	$('#operNoGroupCancelBtn').on('click', function(e) {
		$('#operNoGroupModal').hide();
	});
	// 工号组模态框-查询按钮事件
	$('#operNoGroupSearchBtn').on('click', function(e) {
		loadOperNoGroups($('#oper_no_group_search').val());
	});

	// 工号组模态框属性设置
	$.modal('#specialRuleModal', {
        width: 580,
        height: 320
    });
	$('#operNoGroupAddBtn').on('click', function(e) {
		$('#addInput').empty();
		$('#modalRuleId').val("");
		$('#modal_target_oper_nos').val("");
		$('#modal_target_oper_nos').attr("no","");

		var list=$('.option_box a.active');
		if (list.length > 0) {
			$.each(list, function(i, item) {
				$(item).removeClass('active');
			});
		}
		$('#operNoGroupModal').hide();
		$('#specialRuleModal').show();
	});
	$('#specialRuleCancelBtn').on('click', function(e) {
		$('#specialRuleModal').hide();
	});
	$('#specialRuleConfirmBtn').on('click', function(e) {
		$.dialog.confirm('确定新增该工号组吗？', '确定', '确定', '取消',
		    function() {
				specRuleOperate("100");
		    }
		);
	});

	// 目标工号(多选)模态框属性设置
	$.modal('#targetOperNosModal', {
        width: 550,
        height: 350
    });
	// 弹出目标工号(多选)模态框
	var targetOperNos = '';
	$('#modal_target_oper_nos').on('click', function(e) {
		targetOperNos = $(this).attr('id');
		$('#targetOperNosModal').show();
	});
	// 目标工号(多选)模态框-确定按钮事件
	$('#targetOperNosConfirmBtn').on('click', function(e) {
		var str = '';
		var inputBox = $('#' + targetOperNos);
		inputBox.val('');
		inputBox.attr('no', '');
		var activeNo = $('#targetOperNosList a.active');
		if (activeNo.length > 0) {
			$.each(activeNo, function(i, item) {
				inputBox.val(inputBox.val() + $(item).text());
				inputBox.attr('no', inputBox.attr('no') + $(item).attr('no'));

				str +='<span class="input_box bold" style="padding:2px;width:25%;margin-bottom:10px;margin-top:6px;margin-left:57px" id="addInput'+i+'">'+$(item).text()+'<input type="text" value="10" id="'+$(item).attr('no')+'"/></span>';
				if (i < activeNo.length - 1) {
					inputBox.val(inputBox.val() + ', ');
					inputBox.attr('no', inputBox.attr('no') + ', ');
				}
			});
		}
		$('#addInput').empty();
		$('#addInput').append(str);
		$('#targetOperNosModal').hide();
	});
	// 目标工号(多选)模态框-取消按钮事件
	$('#targetOperNosCancelBtn').on('click', function(e) {
		$('#targetOperNosModal').hide();
	});
	// 目标工号(多选)模态框-查询按钮事件
	$('#targetOperNosSearchBtn').on('click', function(e) {
		loadOperNo($('#target_oper_nos_search').val(),3);
	});

	// 归属部门模态框属性设置
	$.modal('#belongDepartNoModal', {
        width: 550,
        height: 350
    });
	// 弹出归属部门模态框
	var belongDepartNo = '';
	$('#modal_belong_depart_no').on('click', function(e) {
		belongDepartNo = $(this).attr('id');
		$('#belongDepartNoModal').show();
	});
	// 归属部门模态框-确定按钮事件
	$('#belongDepartNoConfirmBtn').on('click', function(e) {
		var inputBox = $('#' + belongDepartNo);
		inputBox.val('');
		inputBox.attr('no', '');
		var activeNo = $('#belongDepartNoList a.active');
		if (activeNo.length > 0) {
			inputBox.val(activeNo.text());
			inputBox.attr('no', activeNo.attr('no'));
			$('#modalDepartProportion').val('10');
			$('#modalDepartProportion').parent().parent().parent().show();
		}
		$('#belongDepartNoModal').hide();
	});
	// 归属部门模态框-取消按钮事件
	$('#belongDepartNoCancelBtn').on('click', function(e) {
		$('#belongDepartNoModal').hide();
	});
	// 归属部门模态框-查询按钮事件
	$('#belongDepartNoSearchBtn').on('click', function(e) {
		loadDepartNo($('#belong_depart_no_search').val(),3);
	});


	// 跳到首页
	$('#pageFirst').on('click', function(e) {
		var page_no = "1";
		$("#pageNo").val(page_no);
		getAssignRuleList();
	});
	// 跳到上一页
	$('#pageUp').on('click', function(e) {
		if($("#pageNo").val() == "1"){
			return;
		}else{
			var page_no = parseInt($("#pageNo").val()) - 1;
			$("#pageNo").val(page_no);
			getAssignRuleList();
		}
	});
	// 跳到下一页
	$('#pageDown').on('click', function(e) {
		if($("#pageNo").val() == $("#totalPage").text()){
			return;
		}else{
			var page_no = parseInt($("#pageNo").val()) + 1;
			$("#pageNo").val(page_no);
			getAssignRuleList();
		}
	});
	// 跳到尾页
	$('#pageLast').on('click', function(e) {
		var page_no = total_pages;
		$("#pageNo").val(page_no);
		getAssignRuleList();
	});

	$('#pageSize').change(function(e){
		$("#pageNo").val("1");
		getAssignRuleList();
	});
});

//重置
function reset(type){
	if(type==1){
		$('#tacheCode').val("");
		$('#operCode').val("");
		$('#accept_oper_no').val("");
		$('#target_oper_no').val("");
		$('#accept_oper_no').attr("no","");
		$('#target_oper_no').attr("no","");
		$('#accept_depart_no').val("");
		$('#target_depart_no').val("");
		$('#accept_depart_no').attr("no","");
		$('#target_depart_no').attr("no","");
		$('#oper_no_group').val("");
		$('#oper_no_group').attr("no","");
	}else{
		$('#modal_belong_depart_no').val("");
		$('#modal_accept_depart_no').val("");
		$('#modal_target_depart_no').val("");
		$('#modal_belong_depart_no').attr("no","");
		$('#modal_accept_depart_no').attr("no","");
		$('#modal_target_depart_no').attr("no","");
		$('#modal_accept_oper_no').val("");
		$('#modal_target_oper_no').val("");
		$('#modal_target_oper_nos').val("");
		$('#modal_accept_oper_no').attr("no","");
		$('#modal_target_oper_no').attr("no","");
		$('#modal_target_oper_nos').attr("no","");
		$('#modalTacheCode').val("");
		$('#modalOperCode').val("");
		$('#modal_oper_no_group').val("");
		$('#modal_oper_no_group').attr("no","");
		$('#modalDepartProportion').val('');
		$('#modalDepartProportion').parent().parent().parent().hide();

		loadOperNo("",4);
		loadOperNoGroups("");
	}

	var list=$('.option_box a.active');
	if (list.length > 0) {
		$.each(list, function(i, item) {
			$(item).removeClass('active');
		});
	}
}


//获取规则分配表
function getAssignRuleList(){

	var json_info = {
		//任务分配规则表
		"procTaskAssignRule":{
			"target_depart_no":INPUT_UTIL.isNull($('#target_depart_no').attr('no'))?window.parent.operInfo.depart_no:$('#target_depart_no').attr('no'),
			"target_oper_no":INPUT_UTIL.isNull($('#oper_no_group').attr('no'))?$('#target_oper_no').attr('no'):$('#oper_no_group').attr('no'),
			"tache_code":$('#tacheCode').val(),
			"oper_code":$('#operCode').val(),
			"accept_oper_no":$('#accept_oper_no').attr('no'),
			"accept_depart_no":$('#accept_depart_no').attr('no'),
			"ext_cond1":"",
			"ext_cond2" :"",
			"ext_cond3":""
		},
		"pageNo":$("#pageNo").val(),
		"pageSize":$("#pageSize").val()
	};

	var data={
		"jsession_id":jsession_id,
		"json_info":JSON.stringify(json_info)
	};

	$('#allotRuleList').empty();
	$.ajax({
		type: "post",
		url: rest_host+"rest/ordModServiceRest/getTaskAssignRule",
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
				var assignRuleHtml='';

				if (data.args.json_info.procTaskAssignRule != null) {
					var ruleList=data.args.json_info.procTaskAssignRule;
					$.each(ruleList, function(i, item) {
						assignRuleHtml+=createRuleHtml(item);
					});

					$("#totalCount").html(data.args.json_info.totalNumeProcTaskAssignRule);
					$("#totalPage").html(data.args.json_info.totalPagesProcTaskAssignRule);
				}else{
					$("#totalCount").html(0);
					$("#totalPage").html(0);
				}

				$('#allotRuleList').append(assignRuleHtml);
			} else {
				$.message.error('获取任务分配规则失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取任务分配规则Ajax请求失败!');
		},
		complete: function(){
			$('#allotRuleModal').hide();
			$.loading.hide();
		}
	});
}

function createRuleHtml(item){
	var ext_condition = "";
	if(!INPUT_UTIL.isNull(item.ext_cond1)){
		ext_condition = item.ext_cond1;
	}else if(!INPUT_UTIL.isNull(item.ext_cond2)){
		ext_condition = item.ext_cond2;
	}else if(!INPUT_UTIL.isNull(item.ext_cond3)){
		ext_condition = item.ext_cond3;
	}
	var html = '<tr code="'+item.id+'">'
			 +'<td code="'+item.tache_code+'">'+window.parent.Constant.tacheCode(item.tache_code)+'</td>'
			 +'<td code="'+item.oper_code+'">'+window.parent.Constant.operCode(item.oper_code)+'</td>'
			 +'<td code="'+item.accept_depart_no+'">'+item.accept_depart_no+'</td>'
			 +'<td code="'+item.accept_oper_no+'">'+item.accept_oper_no+'</td>'
			 +'<td code="'+item.target_depart_no+'">'+item.target_depart_no+'</td>'
			 +'<td code="'+item.target_oper_no+'">'+(INPUT_UTIL.isNull(item.target_oper_no)?ext_condition:item.target_oper_no)+'</td>'
			 +'<td class="text_center">'
			 +'	<div class="btn btn_link" name="editBtn">修改</div>'
			 +'	<div class="btn btn_link" name="delBtn">删除</div>'
			 +'</td>'
			 +'</tr>';
	return html;
}

//增、删、改  oper_type:100新增，101修改，102删除
function assignRuleOperate(oper_type){
	var target_oper_no="";
	if(INPUT_UTIL.isNull($('#modal_target_oper_no').attr('no'))&&!INPUT_UTIL.isNull($('#modal_oper_no_group').attr('no'))){
		target_oper_no=$('#modal_oper_no_group').attr('no');
	}else if(!INPUT_UTIL.isNull($('#modal_target_oper_no').attr('no'))&&INPUT_UTIL.isNull($('#modal_oper_no_group').attr('no'))){
		target_oper_no=$('#modal_target_oper_no').attr('no');
	}

	if(INPUT_UTIL.isNull($('#modal_target_depart_no').attr('no'))||INPUT_UTIL.isNull($('#modalTacheCode').val())||INPUT_UTIL.isNull($('#modalOperCode').val())
			||INPUT_UTIL.isNull($('#modal_accept_depart_no').attr('no'))||INPUT_UTIL.isNull($('#modal_accept_oper_no').attr('no'))) {
		$.message.info("规则信息填写不完整，请检查！");
		return;
	}

	var json_info = {
		//任务分配规则表
		"procTaskAssignRule":{
			"id":oper_type!="100"?selectItem.attr('code'):"",
			"target_depart_no":$('#modal_target_depart_no').attr('no'),
			"target_oper_no":target_oper_no,
			"tache_code":$('#modalTacheCode').val(),
			"oper_code" :$('#modalOperCode').val(),
			"accept_oper_no" :$('#modal_accept_oper_no').attr('no'),
			"accept_depart_no":$('#modal_accept_depart_no').attr('no'),
			"ext_cond1":"",
			"ext_cond2" :"",
			"ext_cond3":""
		}
	};

	var data={
		"jsession_id":jsession_id,
		"oper_type":oper_type,
		"json_info":JSON.stringify(json_info)
	};

	$.ajax({
		type: "post",
		url: rest_host+"rest/ordModServiceRest/taskAssignRuleMainten",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在处理");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				getAssignRuleList();
			}else{
				$.message.error('任务分配规则维护失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('任务分配规则维护Ajax请求失败!');
		},
		complete: function(){
			$.loading.hide();
		}
	});
}

//获取工号
function loadOperNo(operName,type){
	var operData = {
		jsessionId: jsession_id,
		operNo: '',
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
				var operHtml='<span class="one_third"><a no="*">* / 任意工号</a></span>';
				if(data.args.json_info!=''){
					var operInfo=JSON.parse(data.args.json_info).OperInfo;

					if (operInfo != null && operInfo.length>0) {
						$.each(operInfo, function(i, item) {
							operHtml=operHtml+createQueryInfoHtml(item,0);
						});
					}
					if(type==1){
						$('#acceptOperNoList').empty();
						$('#acceptOperNoList').append(operHtml);
					}else if(type==2){
						$('#targetOperNoList').empty();
						$('#targetOperNoList').append(operHtml);
					}else if(type==3){
						$('#targetOperNosList').empty();
						$('#targetOperNosList').append(operHtml);
					}else{
						$('#acceptOperNoList').empty();
						$('#targetOperNoList').empty();
						$('#targetOperNosList').empty();

						$('#targetOperNosList').append(operHtml);
						$('#acceptOperNoList').append(operHtml);
						$('#targetOperNoList').append(operHtml);
					}
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

//获取部门 type 1-单独查询受理部门，2-单独查询目标部门,3-单独查询归属部门,4-全部查询
function loadDepartNo(departName,type){
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
					var departInfo=JSON.parse(data.args.json_info).depart_list;
					var departHtml='<span class="width50per"><a no="*">* / 任意部门</a></span>';
					if (departInfo != null && departInfo.length>0) {
						$.each(departInfo, function(i, item) {
							departHtml=departHtml+createQueryInfoHtml(item,1);
						});
					}
					if(type==1){
						$('#acceptDepartNoList').empty();
						$('#acceptDepartNoList').append(departHtml);
					}else if(type==2){
						$('#targetDepartNoList').empty();
						$('#targetDepartNoList').append(departHtml);
					}else if(type==3){
						$('#belongDepartNoList').empty();
						$('#belongDepartNoList').append(departHtml);
					}else{
						$('#acceptDepartNoList').empty();
						$('#targetDepartNoList').empty();
						$('#belongDepartNoList').empty();
						$('#belongDepartNoList').append(departHtml);
						$('#acceptDepartNoList').append(departHtml);
						$('#targetDepartNoList').append(departHtml);
					}
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
function loadOperCodes(){
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
				var operHtml='';
				if (data.args.code_list != null && data.args.code_list.length>0) {
					$.each(data.args.code_list, function(i, item) {
						operHtml=operHtml+createQueryInfoHtml(item,2);
					});
				}
				$('#operCode').append(operHtml);
				$('#modalOperCode').append(operHtml);
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

//获取环节
function loadTacheCodes(){
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
				var tacheHtml='';
				if (data.args.TacheList != null && data.args.TacheList.length>0) {
					$.each(data.args.TacheList, function(i, item) {
						tacheHtml=tacheHtml+createQueryInfoHtml(item,3);
					});
				}
				$('#tacheCode').append(tacheHtml);
				$('#modalTacheCode').append(tacheHtml);
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

//获取工作组
function loadOperNoGroups(rule_id){
	var groupData = {
		rule_id: rule_id
	};

	$.ajax({
		type: "post",
		url: rest_host+"rest/ordModServiceRest/queryOperNoGroup",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: groupData,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				$('#operNoGroupList').empty();
				var groupHtml='';
				if (data.args.resultList != null && data.args.resultList.length>0) {
					$.each(data.args.resultList, function(i, item) {
						groupHtml=groupHtml+'<span class="one_third"><a no="'+item.rule_id+'">'+item.rule_id+'</a></span>';
					});
				}
				$('#operNoGroupList').append(groupHtml);
			} else {
				$.message.error('查询工号组失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('查询工号组Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
}

function specRuleOperate(oper_type){
	var proportion="";
	var list=$('#modal_target_oper_nos').attr('no').toString().split(",");
	$.each(list, function(i, item) {
		proportion+=$('#'+item.trim()+'').val();
		if(i<list.length-1){
			proportion+=",";
		}
	});

	if(INPUT_UTIL.isNull($('#modalRuleId').val())||INPUT_UTIL.isNull($('#modal_target_oper_nos').attr('no'))||INPUT_UTIL.isNull(proportion)||(!INPUT_UTIL.isNull($('#modal_belong_depart_no').attr('no'))&&INPUT_UTIL.isNull($('#modalDepartProportion').val()))) {
		$.message.info("规则信息填写不完整，请检查！");
		return;
	}

	var json_info = {
			//任务特殊分配规则表
			"procTaskRuleSpec":{
				"id":oper_type!="100"?selectItem.attr('code'):"",
				"rule_id":$('#modalRuleId').val(),
				"target_oper_no":$('#modal_target_oper_nos').attr('no'),
				"proportion":proportion,
				"flag":"0",
				"belong_depart_no":$('#modal_belong_depart_no').attr('no'),
				"departProportion":$('#modalDepartProportion').val()
			}
	};

	var data={
			"jsession_id":jsession_id,
			"oper_type":oper_type,
			"json_info":JSON.stringify(json_info)
	};

	$.ajax({
		type: "post",
		url: rest_host+"rest/ordModServiceRest/taskAssignRuleMainten",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在处理");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				$('#modal_oper_no_group').val("group_"+$('#modalRuleId').val());
				$('#modal_oper_no_group').attr("no","group_"+$('#modalRuleId').val());
				$('#specialRuleModal').hide();
			}else{
				$.message.error('任务特殊分配规则维护失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('任务特殊分配规则维护Ajax请求失败!');
		},
		complete: function(){
			$.loading.hide();
		}
	});
}

//type:0-工号，1-部门，2-业务，3-环节
function createQueryInfoHtml(item,type){
	var content="";
	var code="";
	var html="";
	switch(type){
		case 0:
			html='<span class="one_third"><a no="'+item.oper_no+'">'+item.oper_no +' / '+item.oper_name+'</a></span>';
			return html;
		case 1:
			html='<span class="width50per"><a no="'+item.dept_no+'">'+item.dept_no+' / '+item.dept_name+'</a></span>';
			return html;
		case 2:
			content=item.code_name;
			code=item.code_id;
			break;
		case 3:
			content=item.tache_name;
			code=item.tache_code;
			break;
		default:
			break;
	}

	html='<option value="'+code+'">'+content+'</option>';
	return html;
}
