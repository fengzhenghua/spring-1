var jsession_id = "";
var rest_host = "";
var isUpdate = false;
var selectItem = null;

$(document).ready(function() {
	jsession_id = window.parent.operInfo.jsession_id;
	rest_host = window.parent.operInfo.rest_host;

	getSpecRuleList();
	loadOperNo("");
	loadDepartNo("");
	loadOperNoGroups("");

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

	// 工号组模态框属性设置
	$.modal('#operNoGroupModal', {
        width: 550,
        height: 350
    });
	// 弹出工号组模态框
	var operNo = '';
	$('#ruleId,#modalRuleId').on('click', function(e) {
		operNo = $(this).attr('id');
		if(operNo=='ruleId'){
			$('#operNoGroupAddBtn').hide();
		}else{
			$('#operNoGroupAddBtn').show();
		}
		$('#operNoGroupModal').show();
	});
	// 工号组模态框-确定按钮事件
	$('#operNoGroupConfirmBtn').on('click', function(e) {
		var inputBox = $('#' + operNo);
		inputBox.val('');
		inputBox.attr('no', '');
		var activeNo = $('#operNoGroupList a.active');
		if (activeNo.length > 0) {
			//截取group_以后的字符
			var roleId=activeNo.text().substring(6,activeNo.text().length);
			inputBox.val(roleId);
			inputBox.attr('no', roleId);
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

	// 新增工号组模态框属性设置
	$.modal('#specialRuleModal', {
        width: 580,
        height: 320
    });
	$('#operNoGroupAddBtn').on('click', function(e) {
		if(INPUT_UTIL.isNull($('#oper_no_group_search').val())){
			$.message.info('请输入新增工号组名称');
		}else{
			$('#modalRuleId').val($('#oper_no_group_search').val());
			$('#modalRuleId').attr('no', $('#oper_no_group_search').val());
			$('#operNoGroupModal').hide();
		}
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
		var str = '';
		var inputBox = $('#' + targetOperNo);
		inputBox.val('');
		inputBox.attr('no', '');
		var activeNo = $('#targetOperNoList a.active');
		if (activeNo.length > 0) {
			if(isUpdate&&targetOperNo=='modal_target_oper_no'&&activeNo.length>1){
				$.message.info('只能选择一个工号');
				return;
			}

			$.each(activeNo, function(i, item) {
				inputBox.val(inputBox.val() + $(item).text());
				inputBox.attr('no', inputBox.attr('no') + $(item).attr('no'));
				if(isUpdate){
					str='<span class="input_box width200" id="updateInput"><input type="text" id="'+$(item).attr('no')+'"/></span>';
				}else{
					str +='<span class="input_box bold" style="padding:2px;width:18%;margin-bottom:10px;margin-top:6px;margin-left:'+(i%5==0?'0':'10')+'px;" id="addInput'+i+'">'+$(item).text()+'<input type="text" value="10" id="'+$(item).attr('no')+'"/></span>';
				}
				if (i < activeNo.length - 1) {
					inputBox.val(inputBox.val() + ', ');
					inputBox.attr('no', inputBox.attr('no') + ', ');
				}
			});
		}
		$('#addInput').empty();
		$('#addInput').append(str);
		$('#targetOperNoModal').hide();
	});
	// 目标工号模态框-取消按钮事件
	$('#targetOperNoCancelBtn').on('click', function(e) {
		$('#targetOperNoModal').hide();
	});
	// 目标工号模态框-查询按钮事件
	$('#targetOperNoSearchBtn').on('click', function(e) {
		loadOperNo($('#target_oper_no_search').val());
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
			$('#modalDepartProportion').val('10');
			$('#modalDepartProportion').parent().parent().parent().show();
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

	// 查询 按钮事件
	$('#searchBtn').on('click', function(e) {
		getSpecRuleList();
	});

	// 重置 按钮事件
	$('#resetBtn').on('click', function(e) {
		$('#ruleId').val("");
		$('#ruleId').attr("no","");
		$('#target_oper_no').val("");
		$('#target_oper_no').attr("no","");
		$('#accept_depart_no').val("");
		$('#accept_depart_no').attr("no","");
		$('#accept_depart_no_search').val("");
		$('#oper_no_group_search').val("");
		var list=$('.option_box a.active');
		if (list.length > 0) {
			$.each(list, function(i, item) {
				$(item).removeClass('active');
			});
		}
	});

	// 返回 按钮事件
	$('#returnBtn').on('click', function(e) {
		window.location.href = 'taskAllotRule.jsp';
	});

	// 新增工号组规则 按钮事件
	$('#specialRuleAddBtn').on('click', function(e) {
		isUpdate = false;
		loadOperNoGroups("");
		$('#addInput').empty();
		$('#modalRuleId').val("");
		$('#modalRuleId').attr("no","");
		$('#oper_no_group_search').val("");
		$('#modal_target_oper_no').val("");
		$('#modal_target_oper_no').attr("no","");
		$('#modal_accept_depart_no').val("");
		$('#modal_accept_depart_no').attr("no","");
		$('#modalDepartProportion').val('');
		$('#modalDepartProportion').parent().parent().parent().hide();

		var list=$('.option_box a.active');
		if (list.length > 0) {
			$.each(list, function(i, item) {
				$(item).removeClass('active');
			});
		}
		$('#specialRuleModal').show();
	});
	$('#specialRuleCancelBtn').on('click', function(e) {
		$('#specialRuleModal').hide();
	});

	// 修改 按钮事件
	$('#specialRuleList').on('click', 'td .btn_link[name="editBtn"]', function(e) {
		var list=$('.option_box a.active');
		if (list.length > 0) {
			$.each(list, function(i, item) {
				$(item).removeClass('active');
			});
		}

		var $this = $(this);
		selectItem = $this.parent().parent();
		isUpdate = true;
		$('#modalRuleId').val(selectItem.find('td:eq(0)').text());
		$('#modalRuleId').attr('no',selectItem.find('td:eq(0)').text());
		$('#modal_target_oper_no').attr('no',selectItem.find('td:eq(1)').text());
		$('#modal_target_oper_no').val(selectItem.find('td:eq(1)').text());
		$('#addInput').empty();
		var str='<span class="input_box width200" id="updateInput"><input type="text" id="'+ selectItem.find('td:eq(1)').text()+'"/></span>';
		$('#addInput').append(str);
		$('#'+$('#modal_target_oper_no'). attr('no')+'').val(selectItem.find('td:eq(2)').text());

		if(!INPUT_UTIL.isNull(selectItem.find('td:eq(3)').text())){
			$('#modal_accept_depart_no').val(selectItem.find('td:eq(3)').text());
			$('#modal_accept_depart_no').attr('no',selectItem.find('td:eq(3)').text());
			$('#modalDepartProportion').val(selectItem.find('td:eq(4)').text());
			$('#modalDepartProportion').parent().parent().parent().show();
		}else{
			$('#modal_accept_depart_no').val('');
			$('#modal_accept_depart_no').attr('no','');
			$('#modalDepartProportion').parent().parent().parent().hide();
		}

		$('#specialRuleModal').show();
	});

	$('#specialRuleConfirmBtn').on('click', function(e) {
		if(isUpdate){
			$.dialog.confirm('确定修改该工号组吗？', '确定', '确定', '取消',
			    function() {
					specRuleOperate("101");
			    }
			);
		}else{
			$.dialog.confirm('确定新增该工号组吗？', '确定', '确定', '取消',
			    function() {
					specRuleOperate("100");
			    }
			);
		}
	});

	// 删除 按钮事件
	$('#specialRuleList').on('click', 'td .btn_link[name="delBtn"]', function(e) {
		var $this = $(this);
		selectItem = $this.parent().parent();
		$.dialog.confirm('确定删除此行数据吗？', '删除', '确定', '取消',
			    function() {
					selectItem = $this.parent().parent();
					$('#modalRuleId').val(selectItem.find('td:eq(0)').text());
					$('#modalRuleId').attr('no',selectItem.find('td:eq(0)').text());
					$('#modal_target_oper_no').attr('no',selectItem.find('td:eq(1)').text());
					$('#modal_target_oper_no').val(selectItem.find('td:eq(1)').text());
					$('#addInput').empty();
					var str='<span class="input_box width200" style="margin-bottom:10px;margin-top:6px;margin-left:57px" id="updateInput"><input type="text" id="'+ selectItem.find('td:eq(1)').text()+'"/></span>';
					$('#addInput').append(str);
					$('#'+$('#modal_target_oper_no'). attr('no')+'').val(selectItem.find('td:eq(2)').text());

					if(!INPUT_UTIL.isNull(selectItem.find('td:eq(3)').text())){
						$('#modal_accept_depart_no').val(selectItem.find('td:eq(3)').text());
						$('#modal_accept_depart_no').attr('no',selectItem.find('td:eq(3)').text());
						$('#modalDepartProportion').val(selectItem.find('td:eq(4)').text());
						$('#modalDepartProportion').parent().parent().parent().show();
					}else{
						$('#modal_accept_depart_no').val('');
						$('#modal_accept_depart_no').attr('no','');
						$('#modalDepartProportion').parent().parent().parent().hide();
					}

					specRuleOperate("102");
			    }
			);
	});

});

//获取特殊分配规则表
function getSpecRuleList(){
	var json_info = {
			//任务特殊分配规则表
			"procTaskRuleSpec":{
				"id":"",
				"rule_id":$('#ruleId').attr('no'),
				"target_oper_no" :$('#target_oper_no').attr('no'),
				"proportion":"",
				"flag":"0",
				"belong_depart_no":$('#accept_depart_no').attr('no')
			},
			"pageNo":"1",
			"pageSize":"10"

	};

	var data={
		"jsession_id":jsession_id,
		"json_info":JSON.stringify(json_info)
	};

	$('#specialRuleList').empty();
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
				var specRuleHtml='';

				if (data.args != null) {
					var ruleList = data.args.json_info.procTaskRuleSpec;
					$.each(ruleList, function(i, item) {
						specRuleHtml += createRuleHtml(item);
					});

					var departList = data.args.json_info.procTaskRuleDepart;
					$.each(departList, function(i, item) {
						specRuleHtml += '<tr code="'+item.rule_id+'">'
										 +'<td>'+item.rule_id+'</td>'
										 +'<td></td>'
										 +'<td></td>'
										 +'<td>'+item.depart_no+'</td>'
										 +'<td>'+item.proportion+'</td>'
										 +'<td class="text_center">'
										 +'	<div class="btn btn_link" name="editBtn">修改</div>'
										 +'	<div class="btn btn_link" name="delBtn">删除</div>'
										 +'</td>'
										 +'</tr>';
					});

					$("#totalCount").html(data.args.json_info.totalNumeProcTaskRuleSpec);
					$("#totalPage").html(data.args.json_info.totalPagesProcTaskRuleSpec);
				}else{
					$("#totalCount").html(0);
					$("#totalPage").html(0);
				}

				$('#specialRuleList').append(specRuleHtml);
			} else {
				$.message.error('获取任务特殊分配规则失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取任务特殊分配规则Ajax请求失败!');
		},
		complete: function(){
			$('#specialRuleModal').hide();
			$.loading.hide();
		}
	});
}

function createRuleHtml(item){
	var html = '<tr code="'+item.id+'">'
			 +'<td>'+item.rule_id+'</td>'
			 +'<td>'+item.target_oper_no+'</td>'
			 +'<td>'+item.proportion+'</td>'
			 +'<td>'+item.belong_depart_no+'</td>'
			 +'<td>'+item.departProportion+'</td>'
			 +'<td class="text_center">'
			 +'	<div class="btn btn_link" name="editBtn">修改</div>'
			 +'	<div class="btn btn_link" name="delBtn">删除</div>'
			 +'</td>'
			 +'</tr>';
	return html;
}

//增、删、改  oper_type:100新增，101修改，102删除
function specRuleOperate(oper_type){
	var proportion="";
	if(oper_type==100){
		var list=$('#modal_target_oper_no').attr('no').toString().split(",");
		$.each(list, function(i, item) {
			proportion+=$('#'+item.trim()+'').val();
			if(i<list.length-1){
				proportion+=",";
			}
		});
	}else{
		proportion=$('#'+$('#modal_target_oper_no').attr('no')+'').val();
	}

	var isAvailable=true;
	if(!INPUT_UTIL.isNull($('#modal_target_oper_no').attr('no'))&&INPUT_UTIL.isNull(proportion)){
		isAvailable=false;
	}else if(!INPUT_UTIL.isNull($('#modal_accept_depart_no').attr('no'))&&INPUT_UTIL.isNull($('#modalDepartProportion').val())){
		isAvailable=false;
	}else if(INPUT_UTIL.isNull($('#modal_target_oper_no').attr('no'))&&INPUT_UTIL.isNull($('#modal_accept_depart_no').attr('no'))){
		isAvailable=false;
	}

	if(INPUT_UTIL.isNull($('#modalRuleId').attr('no'))||!isAvailable) {
		$.message.info("规则信息填写不完整，请检查！");
		return;
	}

	var json_info = {
			//任务特殊分配规则表
			"procTaskRuleSpec":{
				"id":oper_type!="100"?selectItem.attr('code'):"",
				"rule_id":$('#modalRuleId').attr('no'),
				"target_oper_no":$('#modal_target_oper_no').attr('no'),
				"proportion":proportion,
				"flag":"0",
				"belong_depart_no":$('#modal_accept_depart_no').attr('no'),
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
				getSpecRuleList();
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

//获取工号
function loadOperNo(operName){
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
				var operHtml="";
				if(data.args.json_info!=''){
					var operInfo=JSON.parse(data.args.json_info).OperInfo;

					if (operInfo != null && operInfo.length>0) {
						$.each(operInfo, function(i, item) {
							operHtml=operHtml+'<span class="one_third"><a no="'+item.oper_no+'">'+item.oper_no +' / '+item.oper_name+'</a></span>';
						});
					}
					$('#targetOperNoList').empty();
					$('#targetOperNoList').append(operHtml);
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

//获取部门
function loadDepartNo(departName){
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

