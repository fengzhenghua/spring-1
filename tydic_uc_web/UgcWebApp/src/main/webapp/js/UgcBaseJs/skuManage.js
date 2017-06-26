var jsession_id = "";
var rest_host = "";

$().ready(function(){
	jsession_id = HTML_UTLS.getParam("jsession_id");
	rest_host = $('#ugc_rest_host').val();

	// 加载sku数据
	getSkuList();

	// 加载sku类型
	loadSkuTypeData();

	// 返回商品管理按钮
	$('#return_goods_btn').on('click', function(e) {
		window.location.href = 'goodsManage.jsp?jsession_id='+jsession_id;
	});

	// sku新增按钮
	$('#sku-attr').on('click',function(){
		showOrHideSkuEdit();
	});
	// 列表删除按钮
	$('.task-package').on('click','.task-list li .del-btn',function() {
		var $this = $(this);
		var oper_sku="sku_define";
		var oper_type="102";
		var sku_id=$this.parents().attr('no');
		$.dialog.confirm(
				"是否要删除该sku？",
			    "提示",
			    "确认",
			    "取消",
			    function() {
					var json_info = { "sku_id":sku_id};
					skuOper(oper_sku,oper_type,json_info);
			    }
			);
	});
	// 列表编辑按钮
	$(".task-package").on('click','.edit-btn',function(){
		resetSkuEdit();
		$('#sku_edit_tr').attr("no","0");

		var $this = $(this);
		var sku_id=$this.parents().attr('no');
		var sku_name=$this.parents().attr('sku_name');
		var sku_desc=$this.parents().attr('sku_desc');

		$('#sku_edit_sku_id').val(sku_id);
		$('#sku_edit_sku_id').attr("readonly","true");
		$('#sku_edit_sku_name').val(sku_name);
		$('#sku_edit_sku_desc').val(sku_desc);

		showOrHideSkuEdit();
	});
	// sku管理编辑框-取消按钮
	$(".sku-attr-hidden").on('click','.cancel',function() {
		showOrHideSkuEdit();
		resetSkuEdit();
	});
	// sku管理编辑框-保存按钮
	$("#sku_edit_save").click(function(){
		var oper_sku="sku_define";
		var oper_type="100";
		var $this = $(this);
		var is_add = $this.parents().parents().attr('no');
		if(is_add != 1){
			oper_type = "101";
		}
		var sku_id = $('#sku_edit_sku_id').val();
		var sku_name = $('#sku_edit_sku_name').val();
		var sku_desc = $('#sku_edit_sku_desc').val();
		var sku_type = $('#sku_edit_sku_type').val();
		if(!sku_id){
			$.message.error("sku编号不能为空！");
			return;
		}
		if(!sku_name){
			$.message.error("sku名称不能为空！");
			return;
		}
		var json_info = { "sku_id":sku_id,"sku_name":sku_name,"sku_type":sku_type,"sku_desc":sku_desc};
		skuOper(oper_sku,oper_type,json_info);
		showOrHideSkuEdit();
		resetSkuEdit();
		$('#sku_edit_tr').attr("no","1");
	});

	// 顶部菜单栏查询按钮
	$('#sku_search_btn').click(function(){
		getSkuList();
	});

	// 顶部菜单栏重置按钮
	$('#sku_reset_btn').click(function(){
		resetQuery();
	});

	// 配置属性-按钮
	$('.task-package').on('click', 'td .btn_link[name="sku_attr_btn"]',function(e) {
		var $this = $(this);
		var sku_id = $this.parent().parent().attr('sku_id');
		var sku_name = $this.parent().parent().attr('sku_name');
		window.location.href = 'skuAttrManage.jsp?sku_id='+sku_id+'&sku_name='+encodeURI(sku_name)
			+'&jsession_id='+jsession_id;
	});
});
//加一条
//sku加一条
function skuAddTaskList($this){
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
}
function resetQuery(){
	$('#sku_id').val('');
	$('#sku_name').val('');
}
function resetSkuEdit(){
	$('#sku_edit_sku_id').val('');
	$('#sku_edit_sku_name').val('');
	$('#sku_edit_sku_desc').val('');
	$("#sku_edit_sku_id").removeAttr("readonly");
}
function showOrHideSkuEdit(){
	if($('.n-ul').css('display') != 'none'){
		$('.n-ul').css('display','none');
	}
	$('.st-ul').slideToggle(500);
}

function getSkuList(){
	var sku_id = $('#sku_id').val();
	var sku_name = $('#sku_name').val();
	var json_info = { "sku_id":sku_id,"sku_name":sku_name};
	var request_data = {
		"jsession_id":jsession_id,
		"oper_sku":"sku_define",
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

				$('#sku_list').empty();

				if (null != data.args) {
					var skuDefineList = data.args.sku_define_list;
					var skuDefineHtml = "";
					$.each(skuDefineList, function(i, item) {
						skuDefineHtml += createSkuHtml(item);
					});
					$('#sku_list').append(skuDefineHtml);
				}

				$.message.info(data.content);
			} else {
				$.message.error(data.content);
			}
		},
		error: function(data) {
			$.message.error('获取sku失败Ajax请求失败!');
		},
		complete: function(){

			$.loading.hide();
		}
	});
}
function skuOper(oper_sku,oper_type,json_info){
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
				getSkuList();
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
function createSkuHtml(item){
	if(item.sku_type == 1){
		item.sku_type = "默认类型";
	}

	var html = '<ul class="task-list">'
		+'<li class="rf-li" no="'+item.sku_id+'" sku_name="'+item.sku_name+'" sku_desc="'+item.sku_desc+'">'
		+'<i class="edit-btn"></i>'
		+'<span>'+item.sku_name+'</span>'
		+'<i class="del-btn"></i>'
		+'</li>'
		+'<li class="r-li">'
		+'<table class="s-table" cellpadding="0" cellspacing="0">'
		+'<thead>'
		+'<tr>'
		+'<th class="r-th">编号</th>'
		+'<th class="r-th">类型</th>'
		+'<th class="r-th">描述</th>'
		+'<th class="r-th">操作</th>'
		+'</tr>'
		+'</thead>'
		+'<tbody>'
		+'<tr sku_id="'+item.sku_id+'" sku_name="'+item.sku_name+'" >'
		+'<td class="r-td">'+item.sku_id+'</td>'
		+'<td class="r-td">'+item.sku_type+'</td>'
		+'<td class="r-td">'+item.sku_desc+'</td>'
		+'<td class="r-td"><div class="btn btn_link" name="sku_attr_btn">属性配置</div></td>'
		+'</tr>'
		+'</tbody>'
		+'</table>'
		+'</li>'
		+'</ul>';
	return html;
}
function loadSkuTypeData(){
	var request_data = {
		"jsession_id":jsession_id,
		"type_code":"sku_type"
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
					$("#sku_edit_sku_type").append("<option value='"+item.code_id+"'>"+item.code_name+"</option>");
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