var jsession_id = "";
var rest_host = "";
var ap_id = "";
var ap_name = "";

$(document).ready(function() {

	rest_host=$('#apc_rest_host').val();
	jsession_id = HTML_UTLS.getParam("jsession_id");
	ap_id = HTML_UTLS.getParam("ap_id");
	ap_name = HTML_UTLS.getParam("ap_name");

	getApAttrList();

	// 返回按钮
	$('#returnBtn').on('click', function(e) {
		window.location.href = 'contactCreate.jsp?jsession_id='+jsession_id;
	});

	// 刷新缓存按钮
	$('#refreshApAttrBtn').on('click', function(e) {
		refreshApAttrinfo();
	});

	// 触点属性配置-模态框属性设置
	$.modal('#apAttrModal', {
        width: 360,
        height: 280
    });
	// 新增触点属性按钮-弹出触点属性配置-模态框
	$('#addApAttrBtn').on('click', function(e) {
		emptyApAttrModal();
		$('#apAttrModal').show();
	});

	// 修改按钮
	$('#apAttrList').on('click', 'td .btn_link[name="editBtn"]', function(e) {
		var $this = $(this);
		$('#modalId').val($this.parent().parent().attr('id'));
		$('#modalApAttrType').val($this.parent().parent().attr('attr_type_name'));
		$('#modalApAttrType').attr("no",$this.parent().parent().attr('attr_type'));
		$('#modalApAttrName').val($this.parent().parent().attr('attr_name'));
		$('#modalApAttrId').val($this.parent().parent().attr('attr_id'));
		$('#modalApAttrValue').val($this.parent().parent().attr('attr_value'));
		$('#apAttrModal').show();
	});

	// 触点属性配置-模态框-确定按钮事件
	$('#apAttrConfirmBtn').on('click', function(e) {
		if( !$('#modalApAttrName').val()){
			$.message.error('触点属性名称不能为空!');
			return false;
		}
		if( !$('#modalApAttrId').val()){
			$.message.error('触点属性的键不能为空!');
			return false;
		}
		if( !$('#modalApAttrValue').val()){
			$.message.error('触点属性的值不能为空!');
			return false;
		}
		var operType = "101";
		if( !$('#modalId').val()){
			operType = "100";
		}
		modifyApAttr(operType);
		$('#apAttrModal').hide();
	});

	// 触点属性配置-模态框-取消按钮事件
	$('#apAttrCancelBtn').on('click', function(e) {
		$('#apAttrModal').hide();
	});

	// 删除按钮
	$('#apAttrList').on('click', 'td .btn_link[name="delBtn"]', function(e) {
		var $this = $(this);
		$.dialog.confirm('确定删除此行数据吗？', '删除', '确定', '取消',
		    function() {
				var id = $this.parent().parent().attr('id');
				$('#modalId').val(id);
				modifyApAttr("102");
		    }
		);
	});

	// 修改每页显示记录条数事件
	$('#pageSize').change(function(e){
		showListInfo(1);
	});

	// 跳到首页
	$('#pageFirst').on('click', function(e) {
		showListInfo(1);
	});
	// 跳到上一页
	$('#pageUp').on('click', function(e) {
		if($("#pageNo").val() == "1"){
			return;
		}else{
			var page_no = parseInt($("#pageNo").val()) - 1;
			showListInfo(page_no);
		}
	});
	// 跳到下一页
	$('#pageDown').on('click', function(e) {
		if($("#pageNo").val() == $("#totalPage").text()){
			return;
		}else{
			var page_no = parseInt($("#pageNo").val()) + 1;
			showListInfo(page_no);
		}
	});
	// 跳到尾页
	$('#pageLast').on('click', function(e) {
		var page_no = $("#totalPage").text();
		showListInfo(page_no);
	});

});

// 分页组件功能
var totalCount = 0;
var pageSize = 0;
var pageNo = 1;
var totalPage = 1;
var totalList;
function showPageInfo(){
	pageSize = $("#pageSize").val();
	$("#totalCount").html(totalCount);
	totalPage = Math.ceil(totalCount/pageSize);
	$("#totalPage").html(totalPage);
}
function showListInfo(pageNo){
//	$.message.error(" pageNo : "+pageNo);
	$("#pageNo").val(pageNo);
	showPageInfo();
	$('#apAttrList').empty();
	var startCount = 0;
	if( pageNo == 1){
		startCount = 0;
	}else{
		startCount = (pageNo-1) * pageSize;
	}
	var endCount = parseInt(startCount) + parseInt(pageSize);
	var apAttrHtml='';
//	$.message.error(startCount+" - "+endCount);
	$.each(totalList, function(i, item) {
		if( i < parseInt(startCount)){
			return true;
		}
		if( i < parseInt(endCount)){
			apAttrHtml += createApAttrHtml(item);
		}else{
			return false;
		}
	});
	$('#apAttrList').append(apAttrHtml);
}

// 获取触点属性
function getApAttrList(){
	$('#apAttrList').empty();
	var json_info = { "ap_id": ap_id};
	var request_data = {
		"jsession_id":jsession_id,
		"json_info":JSON.stringify(json_info)
	};
	var request_url = "rest/operateApAttrRest/getApAttrInfo";

	$.ajax({
		type: "post",
		url: rest_host+request_url,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: request_data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if ("0000" == data.respCode) {
				if (null != data.args) {
					var apAttrList = data.args.apattr_list;
					totalList = apAttrList;
					totalCount = 0;
					$.each(apAttrList, function(i, item) {
						totalCount += 1;
					});
				}
				showListInfo(1);
			} else {
				$.message.info(data.content);
			}
		},
		error: function(data) {
			$.message.error('获取触点属性失败Ajax请求失败!');
		},
		complete: function(){
			$.loading.hide();
		}
	});
}
function createApAttrHtml(item){
	var attr_type_name = item.attr_type_name;
	if( 1 == item.attr_type){
		attr_type_name = "默认类型";
	}
	var html = '<tr id="'+item.id+'" attr_type="'+item.attr_type+'" attr_type_name="'+attr_type_name+'" attr_name="'+item.attr_name+'" attr_id="'+item.attr_id+'" attr_value="'+item.attr_value+'">'
		 +'<td class="text_center">'+ap_name+'</td>'
		 +'<td class="text_center">'+attr_type_name+'</td>'
		 +'<td class="text_center">'+item.attr_name+'</td>'
		 +'<td class="text_center">'+item.attr_id+'</td>'
		 +'<td class="text_center">'+item.attr_value+'</td>'
		 +'<td class="text_center">'
		 +'	<div class="btn btn_link" name="editBtn">修改</div>'
		 +'	<div class="btn btn_link" name="delBtn">删除</div>'
		 +'</td>'
		 +'</tr>';
	return html;
}

function emptyApAttrModal(){
	// 属性类型 预留
//	$('#modalApAttrType').val('');
//	$('#modalApAttrType').attr('no', '');

	$('#modalId').val('');
	$('#modalApAttrId').val('');
	$('#modalApAttrName').val('');
	$('#modalApAttrValue').val('');
}

// 触点属性维护：operType：100-新增，101-修改，102-删除
function modifyApAttr(operType){
	var operTypeName = "新增";
	if(operType == 101 ){
		operTypeName = "修改";
	}
	if(operType == 102){
		operTypeName = "删除";
	}
	var id = $('#modalId').val();
	if( null == id ){
		id = "";
	}
	var attr_type =  $('#modalApAttrType').attr("no");
	var attr_name =  $('#modalApAttrName').val();
	var attr_id =  $('#modalApAttrId').val();
	var attr_value =  $('#modalApAttrValue').val();
	var json_info = {
		"ap_id": ap_id,
		"id":id,
		"attr_type":attr_type,
		"attr_name":attr_name,
		"attr_id":attr_id,
		"attr_value":attr_value
	};
	var request_data = {
		"jsession_id":jsession_id,
		"oper_type":operType,
		"json_info":JSON.stringify(json_info)
	};
	var request_url = "rest/operateApAttrRest/saveApAttrInfo";
	$.ajax({
		type: "post",
		url: rest_host+request_url,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: request_data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if ("0000" == data.respCode) {
				getApAttrList();
				$.message.info(operTypeName+'触点属性成功!');
			} else {
				$.message.error(operTypeName+'触点属性失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error(operTypeName+'触点属性失败Ajax请求失败!');
		},
		complete: function(){
			$.loading.hide();
		}
	});
}

// 刷新缓存
function refreshApAttrinfo(){
	var request_data = {
		"jsession_id":jsession_id,
		"ap_id":ap_id
	};
	var request_url = "rest/operateApAttrRest/refreshApAttrRedis";
	$.ajax({
		type: "post",
		url: rest_host+request_url,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: request_data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if ("0000" == data.respCode) {
				$.message.info('刷新缓存成功!');
			} else {
				$.message.error('刷新缓存失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('刷新缓存失败Ajax请求失败!');
		},
		complete: function(){
			$.loading.hide();
		}
	});
}