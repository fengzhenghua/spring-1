//var jsession_id = "D2B3A6F54814225FA9A05AC82DBE647C";
var jsession_id = "";
var rest_host = "";
var sku_name = "";
var sku_id = "";

$().ready(function(){
	jsession_id = HTML_UTLS.getParam("jsession_id");
	sku_name = HTML_UTLS.getParam("sku_name");
	sku_name = decodeURI(sku_name);
	sku_id = HTML_UTLS.getParam("sku_id");
	rest_host = $('#ugc_rest_host').val();
	
	// 加载sku_attr数据
	getSkuAttrList();
	
	// 加载sku_attr可选键
	loadSkuAttrNameAndIdData();
	
	// 加载sku_attr_type可选类型
	loadSkuAttrTypeData();
	
	// 返回sku管理按钮
	$('#return_sku_btn').on('click', function(e) {
		window.location.href = 'skuManage.jsp?jsession_id='+jsession_id;
	});
	
	// 新增按钮-sku属性
	$('#sku-attr').on('click',function(){
		showOrHideSkuAttrEdit();
	});
	
	// sku管理编辑框-取消按钮
	$("#sku_attr_edit_cancel").click(function(){
		showOrHideSkuAttrEdit();
		resetSkuAttrEdit();
	});
	// sku管理编辑框-保存按钮
	$("#sku_attr_edit_save").click(function(){
		var oper_sku="sku_attr";
		var oper_type="100";
		var $this = $(this);
		var is_add = $this.parents().parents().attr('no');
		if(is_add != 1){
			oper_type = "101";
		}
		var id = $('#sku_attr_edit_id').val();
		var attr_type = $('#sku_attr_edit_attr_type').val();
		var attr_id = $('#sku_attr_edit_attr_key').val();
		var attr_name = $('#sku_attr_edit_attr_key').find("option:selected").text();
		var attr_value = $('#sku_attr_edit_attr_value').val();
		if(!id){
			$.message.error("sku属性的编号不能为空！");
			return;
		}
		if(!attr_id){
			$.message.error("sku属性键不能为空！");
			return;
		}
		if(!attr_value){
			$.message.error("sku属性值不能为空！");
			return;
		}
		var json_info = { "sku_id":sku_id,"id":id,"attr_type":attr_type,"attr_id":attr_id,"attr_value":attr_value,"attr_name":attr_name};
		skuAttrOper(oper_sku,oper_type,json_info);
		showOrHideSkuAttrEdit();
		resetSkuAttrEdit();
		$('#sku_attr_edit_tr').attr("no","1");
	});
	
	/* sku属性管理加一条按钮事件 */
	/*$(".container").on('click','#skuAdd',function() {
		var $this=$(this);
		skuAddTaskList($this);
		isChanged = true;
	});*/
	/* 删除按钮事件 */
	/*$('.container').on('click','tbody .del-btn-check',function() {
		var $this = $(this);
		if ($this.parent().parent().parent().children("tr").length == 1) {
			$.message.warning("不能再删了！！！");
			return;
		}
		$this.parent().parent().remove();
		isChanged = true;
	});*/
	//列表删除按钮
	$('.task-list').on('click','li .del-btn',function() {
		var $this = $(this);
		var oper_sku="sku_attr";
		var oper_type="102";
		var id=$this.parents().parents().attr('no');
		var json_info = {"id":id};
		$.dialog.confirm(
				"是否要删除该sku属性？",
			    "提示",
			    "确认",
			    "取消",
			    function() {
					skuAttrOper(oper_sku,oper_type,json_info);
			    }
			);
	});
	/*编辑按钮事件*/
	/*$(".task-package").on('click','.edit-btn',function(){
		var $this=$(this);
		$this.parent().parent().prev().show();
		$this.parent().parent().hide();
	});*/
	/*取消编辑按钮事件*/
	/*$(".task-package").on('click','.cancel',function() {
		var $this=$(this);
		$this.parent().parent().hide();
		$this.parent().parent().next().show();
		
		if(isChanged){
			isChanged = false;
			taskPackageQuery();
		}
	});*/
/*	$(".sku-attr-hidden").on('click','.cancel',function() {
		var $this=$(this);
		$this.parent().parent().hide();
		isChanged = false;
	});*/
});
//加一条
//sku加一条
/*function skuAddTaskList($this){
	var html ='<tr>'
			+'<td class="s-td">'
			+	'<input class="st-input br" type="text" value="">'
			+'</td>'
			+'<td class="s-td">'
			+	'<input class="st-input br" type="text" value="">'
			+'</td>'
			+'<td class="s-td">'
			+	'<select class="s-select br">'
			+		'<option value="">--请选择--</option>'
			+	'</select>'
			+'</td>'
			+'<td>'
			+	'<div class="btn btn_primary" id="searchBtn">选择</div>'
			+'</td>'
			+'<td>'
			+	'<i class="del-btn-check"></i>'
			+'</td>'
			+'</tr>';
	$this.parent().prev().find("table tbody").append(html);
}*/

function showOrHideSkuAttrEdit(){
	if($('.n-ul').css('display') != 'none'){
		$('.n-ul').css('display','none');
	}
	$('.st-ul').slideToggle(500);
}
function resetSkuAttrEdit(){
	$('#sku_attr_edit_id').val('');
	$('#sku_attr_edit_attr_id').val('');
	$('#sku_attr_edit_attr_value').val('');
//	$("#sku_edit_sku_id").removeAttr("readonly");
}
function getSkuAttrList(){
	
	$('#sku_name_span').html(sku_name);
	var json_info = { "sku_id":sku_id};
	var request_data = {
		"jsession_id":jsession_id,
		"oper_sku":"sku_attr",
		"json_info":JSON.stringify(json_info)
	};
	var request_url = "rest/skuServiceRest/querySkuDefineAndSkuAttrNew";

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
				
				$('#sku_attr_list').empty();
				
				if (null != data.args) {
					var skuAttrList = data.args.sku_attr_list;
					var skuAttrHtml = "";
					skuAttrHtml += createSkuHtmlStart();
					$.each(skuAttrList, function(i, item) {
						skuAttrHtml += createSkuHtmlItem(item);
					});
					skuAttrHtml += createSkuHtmlEnd();
					$('#sku_attr_list').append(skuAttrHtml);
				}
				
				$.message.info(data.content);
			} else {
				$('#sku_attr_list').empty();
				var skuAttrHtml = "";
				skuAttrHtml += createSkuHtmlStart();
				skuAttrHtml += createSkuHtmlEnd();
				$('#sku_attr_list').append(skuAttrHtml);
				$.message.error(data.content);
			}
		},
		error: function(data) {
			$.message.error('获取sku属性失败Ajax请求失败!');
		},
		complete: function(){
			
			$.loading.hide();
		}
	});
}

function skuAttrOper(oper_sku,oper_type,json_info){
	var request_data = {
		"jsession_id":jsession_id,
		"oper_sku":oper_sku,
		"oper_type":oper_type,
		"json_info":JSON.stringify(json_info)
	};
	var request_url = "rest/skuServiceRest/operateSkuNew";

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
				$.message.info(data.content);
				getSkuAttrList();
			} else {
				$.message.error(data.content);
			}
		},
		error: function(data) {
			$.message.error('sku维护失败Ajax请求失败!');
		},
		complete: function(){
			$.loading.hide();
		}
	});
}
function createSkuHtmlItem(item){
	if(item.attr_type == 1){
		item.attr_type = "默认类型";
	}
	if(!item.attr_name){
		item.attr_name = "";
	}
	var html
		='<tr no="'+item.id+'" sku_id="'+item.sku_id+'">'
		+'<td class="r-td">'+item.id+'</td>'
		+'<td class="r-td">'+item.attr_type+'</td>'
		+'<td class="r-td">'+item.attr_name+'</td>'
		+'<td class="r-td">'+item.attr_id+'</td>'
		+'<td class="r-td">'+item.attr_value+'</td>'
		+'<td class="r-td">'
//			+'<i class="del-btn"></i>'
			+'<div class="del-btn" name="sku_attr_remove_btn"></div>'
		+'</td>'
		+'</tr>';
	return html;
}
function createSkuHtmlStart(){
	var html = '<ul class="task-list">'
		+'<li class="rf-li">'
		+'<i class="edit-btn"></i>'		
		+'<span>'+sku_name+'</span>'
//		+'<i class="del-btn"></i>'
		+'</li>'
		+'<li class="r-li">'
		+'<table class="s-table" cellpadding="0" cellspacing="0">'
		+'<thead>'
		+'<tr>'
		+'<th class="r-th">编号</th>'
		+'<th class="r-th">类型</th>'
		+'<th class="r-th">属性名</th>'
		+'<th class="r-th">属性键</th>'
		+'<th class="r-th">属性值</th>'
		+'<th class="r-th">操作</th>'
		+'</tr>'
		+'</thead>'
		+'<tbody>';
	return html;
}
function createSkuHtmlEnd(){
	var html 						
		='</tbody>'
		+'</table>'
		+'</li>'
		+'</ul>';
	return html;
}
function loadSkuAttrNameAndIdData(){
	var request_data = {
		"jsession_id":jsession_id,
		"type_code":"sku_attr"
	};
	var request_url = "rest/CodeList/getCodeListByType";

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
				var code_list = data.args.code_list;
				$.each(code_list, function(i, item) {
					$("#sku_attr_edit_attr_key").append("<option value='"+item.code_id+"'>"+item.code_name+"</option>");
				});
//				$.message.info(data.content);
			} else {
				$.message.error(data.content);
			}
		},
		error: function(data) {
			$.message.error('sku属性名称失败Ajax请求失败!');
		},
		complete: function(){
			$.loading.hide();
		}
	});
}
function loadSkuAttrTypeData(){
	var request_data = {
		"jsession_id":jsession_id,
		"type_code":"sku_attr_type"
	};
	var request_url = "rest/CodeList/getCodeListByType";

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
				var code_list = data.args.code_list;
				$.each(code_list, function(i, item) {
					$("#sku_attr_edit_attr_type").append("<option value='"+item.code_id+"'>"+item.code_name+"</option>");
				});
//				$.message.info(data.content);
			} else {
				$.message.error(data.content);
			}
		},
		error: function(data) {
			$.message.error('加载sku属性类型失败Ajax请求失败!');
		},
		complete: function(){
			$.loading.hide();
		}
	});
}