var json_info;
//本页面需要展示的信息，在父页面的全局参数json_info中获取
$(document).ready(function() {
	json_info = selectTaskInfo.json_info;
	if(!INPUT_UTIL.isNull(json_info)){
		$("#revokeType").text(json_info.cancel_type==null?"":window.parent.Constant.cancelType(json_info.cancel_type));
		$("#revokeReason").text(json_info.cancel_reason==null?"":json_info.cancel_reason);
		$("#revokeTache").text(json_info.cancel_tache_code==null?"":window.parent.Constant.tacheCode(json_info.cancel_tache_code));
		$("#operNo").text(json_info.cancel_oper_no==null?"":json_info.cancel_oper_no);
		$("#operTime").text(json_info.cancel_time==null?"":json_info.cancel_time);
	}

	// 审核通过
	$('#revokePassBtn').on('click', function(e) {
		$.dialog.confirm(
				"是否审核通过？",
			    "提示",
			    "确认",
			    "取消",
			    function() {
					checkOrderCancel("1");
			    }
		);
	});

	// 审核不通过
	$('#revokeRejectBtn').on('click', function(e) {
		$.dialog.confirm(
				"是否审核不通过？",
			    "提示",
			    "确认",
			    "取消",
			    function() {
					checkOrderCancel("2");
			    }
		);
	});

});

function checkOrderCancel(audit_flag){
	var data = {
			"jsession_id": commonInfo.jsession_id,
			"order_no": selectTaskInfo.order_no==null?"":selectTaskInfo.order_no,
			"order_type": selectTaskInfo.order_type==null?"":selectTaskInfo.order_type,
			"audit_flag": audit_flag,
			"audit_desc":$("#remarks").val(),
			"deal_code":"",
			"deal_desc":"",
			"deal_system_no":""
			};
	$.ajax({
		type: "post",
		url: commonInfo.rest_host+"rest/orderCancelApplyRest/checkInfoOrderCancel",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: data,
		crossDomain: true == !(document.all),
		dataType: "json",
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				$.dialog.alert(
						"处理成功",
						"任务",
						"返回",
						function() {
					    	window.location.href = "task.jsp?tache_code="+commonInfo.tache_code+"&role_type="+commonInfo.role_type;
					    }
				);
			} else {
				$.message.error('撤单审核失败'+data.content);
			}
		},
		error: function(data) {
			$.message.error('撤单审核Ajax请求失败');
		},
		complete: function(){
			$.loading.hide();
		}
	});
}