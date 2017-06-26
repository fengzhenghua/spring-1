var img_dir = '';
var oper_no = '';
var dept_no = '';
var province_code = '';

$(document).ready(function() {
	img_dir = $("#img_dir").val();
	oper_no = $("#oper_no").val();
	dept_no = $("#dept_no").val();
	province_code = $("#province_code").val();

	$.ajax({
		url: application.fullPath + 'authority/departmentManage/getDeptInfoList',
		dataType: 'json',
		contentType: 'application/x-www-form-urlencoded; charset=utf-8',
		type: 'get',
		data: {
			'dept_no': dept_no
		},
		success: function(message) {
			var curNodes = toNodeJsonData(message);
			initZTree(curNodes);
		},
		error: function(message){
			$.alert('获取部门列表失败！');
		}
	});
	
});

/* ---------- zTree start ---------- */
var zTreeObj; // 树对象
var setting = {}; // 树参数配置
// 初始化树
function initZTree(zNodes) {
	setting = {
		async: {
			contentType: 'application/x-www-form-urlencoded; charset=utf-8',
			enable: true,
			type: 'get',
			url: application.fullPath + 'authority/departmentManage/getDeptInfoList',
			autoParam: ['dept_no=parent_dept_no'], // 异步加载时需要自动提交当前节点属性的参数
			dataFilter: ajaxDataFilter
		},
		callback: {
			onAsyncSuccess: zTreeOnAsyncSuccess,
			onAsyncError: zTreeOnAsyncError,
			onClick: zTreeOnClick
		}
	};
	zTreeObj = $.fn.zTree.init($("#ztree_dept_div"), setting, zNodes);
}

// 处理树节点参数和格式
function toNodeJsonData(data) {
	var curNodes = [];
	if (data.args.infoDeptLevel.length > 0) {
		$.each(data.args.infoDeptLevel, function(i, item) {
			var curNode = {
				'id': 'deptno_' + item.dept_no,
				'name': item.dept_name,
				'dept_no': item.dept_no,
				'dept_name': item.dept_name,
				'dept_type': item.dept_type,
				'isParent': item.has_sub_dept > 0 ? true : false,
				'icon': application.fullPath + 'js/zTree/css/zTreeStyle/img/diy/dept_icon.png'
			};
			curNodes.push(curNode);
		});
	}
	return curNodes;
}

// 预处理Ajax返回的数据
function ajaxDataFilter(treeId, parentNode, responseData) {
	if (!responseData) {
		return null;
	}
	return toNodeJsonData(responseData);
}

// 用于捕获异步加载正常结束的事件回调函数
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
//	$.alert(msg);
}

// 用于捕获异步加载出现异常错误的事件回调函数
function zTreeOnAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
	$.alert('获取部门列表失败！');
}

// 用于捕获节点被点击的事件回调函数
function zTreeOnClick(event, treeId, treeNode) {
	// 部门信息
	$('#dept_no_label').text(treeNode.dept_no);
	$('#dept_name_label').text(treeNode.dept_name);
	$('#dept_type_label').text(treeNode.dept_type);
	if (treeNode.getParentNode()) {
		$('#parent_dept_name_label').text(treeNode.getParentNode().dept_name);
	} else {
		$('#parent_dept_name_label').text('无');
	}

	// 工号信息
	$('#oper_info_list>tbody>tr').remove();
	$.ajax({
		url: application.fullPath + 'authority/departmentManage/getOperInfoList',
		dataType: 'json',
		contentType: 'application/x-www-form-urlencoded; charset=utf-8',
		type: 'get',
		data: {
			'dept_no': treeNode.dept_no
		},
		waitMsg: '工号信息查询中..',
		success: function(message) {
			if (message.args.deptOperInfo && message.args.deptOperInfo.length > 0) {
				$.each(message.args.deptOperInfo, function(i, item) {
					var html = '<tr oper_no="' + item.oper_id + '">'
//							+ '<td class="text_center"><input type="checkbox" name="chk_opers" /></td>'
							+ '<td class="text_center">' + item.oper_id + '</td>'
							+ '<td class="text_center">' + item.oper_name + '</td>'
							+ '<td class="text_center">' + (item.mobile_no == null ? '无' : item.mobile_no) + '</td>'
							+ '</tr>';
					$('#oper_info_list>tbody').append(html);
				});
			} else {
				var html = '<tr><td class="text_center red" colspan="' + $('#oper_info_list>thead>tr>th').length + '">没有数据！</td></tr>';
				$('#oper_info_list>tbody').append(html);
			}
		},
		error: function(message){
			$.alert('获取工号信息列表失败！');
		}
	});
};
/* ---------- zTree end ---------- */
