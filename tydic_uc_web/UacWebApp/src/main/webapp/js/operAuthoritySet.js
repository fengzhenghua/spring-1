var rest_host = "";
var jsession_id = "";
var oper_no = "";

$(document).ready(function() {
	jsession_id = HTML_UTLS.getParam("jsession_id");
	oper_no = HTML_UTLS.getParam("oper_no");
	rest_host = $('#uac_rest_host').val();

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

	// 新增权限模态框属性设置
	$.modal('#authorityAddModal', {
        width: 540,
        height: 170
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
		getOperNos($('#accept_oper_no_search').val());
	});

	// 角色模态框属性设置
	$.modal('#ruleModal', {
        width: 550,
        height: 350
    });
	// 弹出角色模态框
	var ruleId = '';
	$('#roleId, #modalRoleId').on('click', function(e) {
		ruleId = $(this).attr('id');
		$('#ruleModal').show();
	});
	// 角色模态框-确定按钮事件
	$('#ruleModalConfirmBtn').on('click', function(e) {
		var inputBox = $('#' + ruleId);
		inputBox.val('');
		inputBox.attr('no', '');
		var activeNo = $('#modalRoleList a.active');
		if (activeNo.length > 0) {
			inputBox.val(activeNo.text());
			inputBox.attr('no', activeNo.attr('no'));
		}
		$('#ruleModal').hide();
	});
	// 角色模态框-取消按钮事件
	$('#ruleModalCancelBtn').on('click', function(e) {
		$('#ruleModal').hide();
	});

	// 查询 按钮事件
	$('#searchBtn').on('click', function(e) {
		getOperRoleList();
	});

	// 重置 按钮事件
	$('#resetBtn').on('click', function(e) {
		reset();
	});

	// 新增权限 按钮事件
	$('#authorityAddBtn').on('click', function(e) {
		getOperNos("");
		$('#modal_accept_oper_no').val('');
		$('#modal_accept_oper_no').attr('no','');
		$('#modalRoleId').val('');
		$('#modalRoleId').attr('no','');
		$('#accept_oper_no_search').val('');
		var list=$('.option_box a.active');
		if (list.length > 0) {
			$.each(list, function(i, item) {
				$(item).removeClass('active');
			});
		}
		$('#authorityAddModal').show();
	});

	// 新增权限模态框 保存按钮事件
	$('#authorityAddConfirmBtn').on('click', function(e) {
		operRoleOperate(100);
	});

	// 新增权限模态框 取消按钮事件
	$('#authorityAddCancelBtn').on('click', function(e) {
		$('#authorityAddModal').hide();
	});

	// 删除 按钮事件
	$('#authorityList').on('click', 'td .btn_link[name="delBtn"]', function(e) {
		var $this = $(this);
		$.dialog.confirm('确定删除此行数据吗？', '删除', '确定', '取消',
		    function() {
				var selectItem=$this.parent().parent();
				$('#modal_accept_oper_no').val(selectItem.find('td:eq(0)').text());
				$('#modal_accept_oper_no').attr('no',selectItem.find('td:eq(0)').text());
				$('#modalRoleId').val(selectItem.find('td:eq(2)').text());
				$('#modalRoleId').attr('no',selectItem.find('td:eq(2)').text());
				operRoleOperate(102);
			}
		);
	});

/*	// 跳到首页
	$('#pageFirst').on('click', function(e) {
		var page_no = "1";
		$("#pageNo").val(page_no);
		getOperRoleList();
	});
	// 跳到上一页
	$('#pageUp').on('click', function(e) {
		if($("#pageNo").val() == "1"){
			return;
		}else{
			var page_no = parseInt($("#pageNo").val()) - 1;
			$("#pageNo").val(page_no);
			getOperRoleList();
		}
	});
	// 跳到下一页
	$('#pageDown').on('click', function(e) {
		if($("#pageNo").val() == $("#totalPage").text()){
			return;
		}else{
			var page_no = parseInt($("#pageNo").val()) + 1;
			$("#pageNo").val(page_no);
			getOperRoleList();
		}
	});
	// 跳到尾页
	$('#pageLast').on('click', function(e) {
		var page_no = total_pages;
		$("#pageNo").val(page_no);
		getOperRoleList();
	});

	$('#pageSize').change(function(e){
		$("#pageNo").val("1");
		getOperRoleList();
	});*/

	getOperRoleList();
	getRoleIds();
	getOperNos("");
});

//重置
function reset(){
	$('#accept_oper_no').val("");
	$('#accept_oper_no').attr("no","");
	$('#roleId').val("");
	$('#roleId').attr("no","");

	var list=$('.option_box a.active');
	if (list.length > 0) {
		$.each(list, function(i, item) {
			$(item).removeClass('active');
		});
	}
}

//获取工号角色表
function getOperRoleList(){
	$('#authorityList').empty();
	var data = {
			jsession_id: jsession_id,
			oper_no: INPUT_UTIL.isNull($('#accept_oper_no').attr('no'))?oper_no:$('#accept_oper_no').attr('no'),
			role_id: $('#roleId').attr('no'),
			page_no: "1",
			page_size: "10"
		};

	$.ajax({
		type: "post",
		url: rest_host+"rest/operRest/queryRuleOperRole",
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
				var operInfoHtml="";
//				var totalPages=1;

				if (data.args.oper_relation_list != null && data.args.oper_relation_list.length>0) {
					var operList = data.args.oper_relation_list;

					$.each(operList, function(i, item) {
						operInfoHtml+=createOperInfoHtml(item);
					});

					$("#totalCount").html(operList.length);
				}

//				$("#totalPage").html(totalPages);
				$('#authorityList').append(operInfoHtml);
			} else {
				$.message.error('获取工号角色表失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取工号角色表Ajax请求失败!');
		},
		complete: function(){
			$('#authorityAddModal').hide();
			$.loading.hide();
		}
	});
}

function createOperInfoHtml(item){
	var html='<tr>'
		+'<td>'+item.oper_no+'</td>'
		+'<td>'+item.oper_name+'</td>'
		+'<td>'+item.role_id+'</td>'
		+'<td>'+item.role_name+'</td>'
		+'<td class="text_center">'
		+'<div class="btn btn_link" name="delBtn">删除</div>'
		+'</td>'
		+'</tr>';
	return html;
}

//增、删、改  oper_type:100新增，101修改，102删除
function operRoleOperate(oper_type){
	if(INPUT_UTIL.isNull($('#modal_accept_oper_no').attr('no'))||INPUT_UTIL.isNull($('#modalRoleId').attr('no'))) {
		$.message.info("信息填写不完整，请检查！");
		return;
	}

	var json_info = {
		"oper_id": $('#modal_accept_oper_no').attr('no'),
		"role_id": $('#modalRoleId').attr('no')
	};

	var data={
		"jsession_id":jsession_id,
		"oper_type":oper_type,
		"json_info":JSON.stringify(json_info)
	};

	$.ajax({
		type: "post",
		url: rest_host+"rest/operRest/operateRuleOperRole",
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
				getOperRoleList();
			}else{
				$.message.error('工号权限护失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('工号权限维护Ajax请求失败!');
		},
		complete: function(){
			$.loading.hide();
		}
	});
}

//获取工号
function getOperNos(operName){
	var operData = {
		jsessionId: jsession_id,
		operNo: '',
		operName: operName
	};

	$.ajax({
		type: "post",
		url: rest_host+"rest/baseOperRest/getOperInfoByDepartNo",
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
				if(data.args.oper_list!= null && data.args.oper_list.length>0){
					$.each(data.args.oper_list, function(i, item) {
						operHtml+='<span class="one_third"><a no="'+item.oper_no+'">'+item.oper_no+' / '+item.oper_name+'</a></span>';
					});
				}
				$('#acceptOperNoList').empty();
				$('#acceptOperNoList').append(operHtml);
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

//获取角色
function getRoleIds(){
	var operData = {
		jsessionId: jsession_id
	};

	$.ajax({
		type: "post",
		url: rest_host+"rest/baseOperRest/getBaseOperInfoByJsessionId",
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
				var roleHtml="";
				if(!INPUT_UTIL.isNull(data.args.oper_info.role_id)){
					var roleIds=data.args.oper_info.role_id.split(",");
					var roleNms=data.args.oper_info.role_name.split(",");
					$.each(roleIds, function(i, item) {
						roleHtml+='<span class="width100per"><a no="'+item+'">'+item+' / '+roleNms[i]+'</a></span>';
					});
				}
				$('#modalRoleList').empty();
				$('#modalRoleList').append(roleHtml);
			} else {
				$.message.error('获取工号信息失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取工号信息Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
}
