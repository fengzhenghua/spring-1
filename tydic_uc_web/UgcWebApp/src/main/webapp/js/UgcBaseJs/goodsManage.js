var rest_host = "";
var jsession_id="";
var total_pages = 0;
var goods_type = "";
var goods_state= "";
var goods_type_html = "";
var goods_state_html= "";
var sku_define_list = "";
var select_sku_type = "";
var select_sku_id = "";

$().ready(function(){
	rest_host = $('#ugc_rest_host').val();
	jsession_id = HTML_UTLS.getParam("jsession_id");

	// 多选卡-选中事件
	$('.option_box[type="checkbox"]').on('click', 'a', function(e) {
		var $this = $(this);
		if ($this.hasClass('active')) {
			$this.removeClass('active');
		} else {
			$this.addClass('active');
		}
	});

	//点击新增商品
	$('#add_goods').on('click',function(){
		$('#new_goods_id').val('');
        $('#new_goods_desc').val('');
        $('#new_goods_type').val('');
        $('#new_goods_name').val('');
        $('#new_goods_state').val('');
		select_sku_type = "";
		select_sku_id = "";
		$('.n-ul').slideToggle(500);
	});

	/* 删除按钮事件 */
	$('.task-package').on('click','.task-list li .del-btn',function() {
		var $this = $(this);
		var goods_id=$this.parents('[class="task-list"]').attr('goods_id');
		var json_info={
             "goods_define":{
                    "goods_id" : goods_id
               },
              "goods_sku": {
            	  "goods_id" : goods_id
              }
	    };

		$.dialog.confirm(
				"是否要删除该商品以及其sku信息",
			    "提示",
			    "确认",
			    "取消",
			    function() {
					goodsAndSkuOperate("102",json_info);
			    }
			);
	});
	/*编辑按钮事件*/
	$(".task-package").on('click','.sku-edit-btn',function(){
		var $this=$(this);
		$this.parent().parent().prev().show();
		$this.parent().parent().hide();
		select_sku_type = "";
		select_sku_id = "";
	});
	/*取消编辑按钮事件*/
	$(".task-package").on('click','.cancel',function() {
		var $this=$(this);
		$this.parent().parent().hide();
		$this.parent().parent().next().show();
	});
	$(".new-goods-hidden").on('click','.cancel',function() {
		$('.n-ul').slideToggle(500);
	});
	/*保存按钮事件*/
	$(".task-package").on('click','.save',function() {
		var $this = $(this);
		$this.parent().parent().hide();
		$this.parent().parent().next().show();

		var goods_id = $this.parents('[class="task-package"]').attr('goods_id');
		var new_goods_id = "";

		if(goods_id != $('#'+goods_id+'_id').val()){
			//goods_id已修改 删除原来的以后新增
			new_goods_id = $('#'+goods_id+'_id').val();
		}

		var json_info={
             "goods_define":{
                    "goods_id" : goods_id,
                    "goods_desc" : $('#'+goods_id+'_desc').val(),
                    "goods_type" : $('#'+goods_id+'_type').val(),
                    "goods_name" : $('#'+goods_id+'_name').val(),
                    "state" : $('#'+goods_id+'_state').val()
               },
              "goods_sku": ''
	    };

		if(!INPUT_UTIL.isNull(select_sku_id) || !INPUT_UTIL.isNull(new_goods_id)){
			json_info.goods_sku={
	          	  "goods_id" : goods_id,
	        	  "sku_type" : select_sku_type,
	        	  "sku_id" : select_sku_id
	          };
		}

		if(INPUT_UTIL.isNull(new_goods_id)){
			goodsAndSkuOperate("101",json_info);
		}else{
			$.dialog.confirm(
					"goods_id已修改，是否删除原商品以后新增该商品",
				    "提示",
				    "确认",
				    "取消",
				    function() {
						goodsAndSkuOperate("102",json_info,new_goods_id);
				    }
				);
		}
	});
	$(".new-goods-hidden").on('click','.save',function() {
		var json_info={
             "goods_define":{
                    "goods_id" : $('#new_goods_id').val(),
                    "goods_desc" : $('#new_goods_desc').val(),
                    "goods_type" : $('#new_goods_type').val(),
                    "goods_name" : $('#new_goods_name').val(),
                    "state" : $('#new_goods_state').val()
               },
              "goods_sku": ''
	    };

		if(!INPUT_UTIL.isNull(select_sku_id)){
			json_info.goods_sku={
	          	  "goods_id" : $('#new_goods_id').val(),
	        	  "sku_type" : select_sku_type,
	        	  "sku_id" : select_sku_id
	          };
		}
		//新增
		goodsAndSkuOperate("100",json_info);
		$('.n-ul').slideToggle(500);
	});

	// 跳到首页
	$('#pageFirst').on('click', function(e) {
		var page_no = "1";
		$("#pageNo").val(page_no);
		queryGoodsList();
	});
	// 跳到上一页
	$('#pageUp').on('click', function(e) {
		if($("#pageNo").val() == "1"){
			return;
		}else{
			var page_no = parseInt($("#pageNo").val()) - 1;
			$("#pageNo").val(page_no);
			queryGoodsList();
		}
	});
	// 跳到下一页
	$('#pageDown').on('click', function(e) {
		if($("#pageNo").val() == $("#totalPage").text()){
			return;
		}else{
			var page_no = parseInt($("#pageNo").val()) + 1;
			$("#pageNo").val(page_no);
			queryGoodsList();
		}
	});
	// 跳到尾页
	$('#pageLast').on('click', function(e) {
		var page_no = total_pages;
		$("#pageNo").val(page_no);
		queryGoodsList();
	});

	$("#pageSize").change(function(e){
		$("#pageNo").val("1");
		queryGoodsList();
	});

	$('#searchBtn').on('click', function(e) {
		queryGoodsList();
	});

	$('#resetBtn').on('click', function(e) {
		$('#goods_id').val('');
		$('#goods_desc').val('');
		$('#goods_name').val('');
		$('[name="goods_type"]').val('');
		$('[name="goods_state"]').val('');
	});

	// sku选择模态框属性设置
	$.modal('#skuSelectModal', {
        width: 550,
        height: 350
    });
	// sku选择模态框-确定按钮事件
	$('#skuSelectConfirmBtn').on('click', function(e) {
		var activeNo = $('#skuSelectList a.active');
		if (activeNo.length > 0) {
			$.each(activeNo, function(i, item) {
				select_sku_type += $(item).attr('type');
				select_sku_id += $(item).attr('no');
				if (i < activeNo.length - 1) {
					select_sku_type += ',';
					select_sku_id += ',';
				}
			});
		}
		$('#skuSelectModal').hide();
	});
	// sku选择模态框-取消按钮事件
	$('#skuSelectCancelBtn').on('click', function(e) {
		$('#skuSelectModal').hide();
	});
	// sku选择模态框-查询按钮事件
	$('#skuSearchBtn').on('click', function(e) {
		querySkuList();
	});

	$('#sku_manage').on('click', function(e) {
		window.location.href = 'skuManage.jsp?jsession_id=' + jsession_id;
	});

	queryGoodsCode("goods_type");
	queryGoodsCode("goods_state");
	queryGoodsList();
	querySkuList();
});

//查询商品
function queryGoodsList(sku_goods_id){
	var json_info = {
		//goods_define表
		"goods_define":{
			"goods_id" : $('#goods_id').val(),
			"goods_desc" : $('#goods_desc').val(),
			"goods_type" : $('#goods_type').val(),
			"goods_name" : $('#goods_name').val(),
			"state" : $('#goods_state').val()
		},
		//goods_sku表
        "goods_sku": {
        	"goods_id" : sku_goods_id
        },
		"page_no": $('#pageNo').val(),
		"page_size": $('#pageSize').val()
	};

	if(INPUT_UTIL.isNull(sku_goods_id)){
		json_info.goods_sku = '';
	}else{
		json_info.goods_define = '';
	}

	var goodsData = {
		jsession_id: jsession_id,
		json_info: JSON.stringify(json_info)
	};

	$.ajax({
		type: "post",
		url: rest_host+"rest/skuServiceRest/queryGoodsDefineList",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: goodsData,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				var goodsHtml = '';

				if (data.args.goods_define_list != null && data.args.goods_define_list.length>0) {
					$.each(data.args.goods_define_list, function(i, item) {
						var goods_state_nm = getSelectGoodsHtml("goods_state",item.state);
						var goods_type_nm = getSelectGoodsHtml("goods_type",item.goods_type);

						var hideHtml = '<ul class="task-package" style="display:none;" goods_id="'+item.goods_id+'">'
									   +' <li class="f-li">'
									   +'	<b>商品名称：</b>'
									   +'	<input class="task-package-name color" value="'+item.goods_name+'" placeholder="点我输入商品名称哦" id="'+item.goods_id+'_name"/>'
									   +'</li>'
									   +'<li class="n-li">'
									   +'	<table class="n-table" border="0" cellpadding="0" cellspacing="0">'
									   +'		<thead>'
									   +'			<tr>'
									   +'				<th class="n-th n-w">ID</th>'
									   +'				<th class="n-th n-w">描述</th>'
									   +'				<th class="n-th n-w">状态</th>'
									   +'				<th class="n-th n-w">类型</th>'
									   +'				<th ></th>'
									   +'				<th ></th>'
									   +'			</tr>'
									   +'		</thead>'
									   +'		<tbody>'
									   +'			<tr>'
									   +'				<td class="n-td td-w">'
									   +'					<input class="n-input br" type="text" value="'+item.goods_id+'" id="'+item.goods_id+'_id">'
									   +'				</td>'
									   +'				<td class="n-td td-w">'
									   +'					<input class="n-input br" type="text" value="'+item.goods_desc+'" id="'+item.goods_id+'_desc">'
									   +'				</td>'
									   +'				<td class="n-td td-w">'
									   +'					<select class="n-select br" id="'+item.goods_id+'_state">'
									   +						goods_state_html
									   +'					</select>'
									   +'				</td>'
									   +'				<td class="n-td td-w">'
									   +'					<select class="n-select br" id="'+item.goods_id+'_type">'
									   +						 goods_type_html
									   +'					</select>'
									   +'				</td>'
									   +'				<td>'
									   +				'<div class="btn btn_primary" name="search_btn">选择sku</div>'
									   +'				</td>'
									   +'			</tr>'
									   +'		</tbody>'
									   +'	</table>'
									   +'</li>'
									   +'<li class="last-item">'
									   +'	<span class="save">保存</span>'
									   +'	<span  class="cancel sku-cancel">取消</span>'
									   +'</li>'
									   +'</ul>';

						var showHtml = '<ul class="task-list" goods_id='+item.goods_id+'>'
									   +'	<li class="rf-li">'
									   +'		<i class="edit-btn sku-edit-btn"></i>'
									   +'		<span>'+item.goods_name+'</span>'
									   +'		<i class="del-btn"></i>'
									   +'	</li>'
									   +'	<li class="r-li">'
									   +'		<table class="s-table" cellpadding="0" cellspacing="0">'
									   +'			<thead>'
									   +'				<tr>'
									   +'					<th class="r-th">ID</th>'
									   +'					<th class="r-th">描述</th>'
									   +'					<th class="r-th">状态</th>'
									   +'					<th class="r-th">类型</th>'
									   +'					<th class="r-th">创建时间</th>'
									   +'					<th class="r-th">创建人</th>'
									   +'				</tr>'
									   +'			</thead>'
									   +'			<tbody>'
									   +'				<tr>'
									   +'					<td class="r-td">'+item.goods_id+'</td>'
									   +'					<td class="r-td">'+item.goods_desc+'</td>'
									   +'					<td class="r-td">'+goods_state_nm+'</td>'
									   +'					<td class="r-td">'+goods_type_nm+'</td>'
									   +'					<td class="r-td">'+item.create_time+'</td>'
									   +'					<td class="r-td">'+item.create_staff+'</td>'
									   +'				</tr>'
									   +'			</tbody>'
									   +'		</table>'
									   +'	</li>'
									   +'</ul>';

						goodsHtml = goodsHtml+hideHtml+showHtml;
					});

				}

				//单独查询商品列表
				if(INPUT_UTIL.isNull(sku_goods_id)){
					$('#goods_define_list').empty();

					$("#totalCount").html(data.args.total_num);
					$("#totalPage").html(data.args.total_page);
					$('#goods_define_list').prepend(goodsHtml);
				}

				if (data.args.goods_sku_list != null && data.args.goods_sku_list.length>0) {
					$('#skuSelectList').empty();
					var selectHtml = '';

					$.each(sku_define_list, function(j, sku_define) {
						var isSelect = false;
						$.each(data.args.goods_sku_list, function(i, goods_sku) {
							if(goods_sku.sku_id==sku_define.sku_id){
								isSelect = true;
								return false;
							}
						});
						selectHtml += '<span class="width50per"><a class="'+(isSelect?"active":"")+'" no="'+sku_define.sku_id+'" type="'+sku_define.sku_type+'" >'+sku_define.sku_name+'</a></span>';
					});

					$('#skuSelectList').append(selectHtml);
				}
			} else {
				$.message.error('商品查询异常!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('商品查询Ajax请求失败!');
		},
		complete:function(){
			// 弹出sku选择模态框
			$('[name="search_btn"]').unbind().bind('click', function(e) {
				var $this = $(this);
				var sku_goods_id = $this.parents('[class="task-package"]').attr('goods_id');

				var list=$('.option_box a.active');
				if (list.length > 0) {
					$.each(list, function(i, item) {
						$(item).removeClass('active');
					});
				}

				if(!INPUT_UTIL.isNull(sku_goods_id)){
					queryGoodsList(sku_goods_id);
				}
				$('#skuSelectModal').show();
			});
			$.loading.hide();
		}
	});
}

//任务包操作  oper_type:100-新增，101-修改，102-删除
function goodsAndSkuOperate(oper_type,json_info,new_goods_id){
	var operateData = {
	         "jsession_id":jsession_id,
	         "oper_type":oper_type,
	         "json_info":JSON.stringify(json_info)
	     };

		$.ajax({
		     type: "post",
		     url: rest_host+"rest/skuServiceRest/operateGooodsDefine",
		     contentType: "application/x-www-form-urlencoded; charset=utf-8",
		     async: true,
		     data: operateData,
		     dataType: "json",
		     crossDomain: true == !(document.all),
		     beforeSend: function() {
		         $.loading.show("正在处理");
		     },
		     success: function(data) {
		         if (data.respCode=="0000" ) {
	                  if(oper_type=="100"){
	                	  $.message.success("商品添加成功！");
	                  }else if(oper_type=="101"){
	                	  $.message.success("商品修改成功！");
	                  }else if(oper_type=="102"){
	                	  $.message.success("商品删除成功！");
	                  }

	                  if(INPUT_UTIL.isNull(new_goods_id)){
	                	  queryGoodsList();
	  	              }else{
	  	            	//goods_id修改为新增商品，回调
	  	            	json_info.goods_define.goods_id = new_goods_id;
	  	            	json_info.goods_sku.goods_id = new_goods_id;

	  	            	goodsAndSkuOperate("100",json_info);
	  	              }
	             } else {
	                   $.message.error('商品维护失败!' +data.content);
	             }
		     },
		     error: function(data) {
	            $.message.error('商品维护Ajax请求失败!' );
		     },
		     complete: function(){
	            $.loading.hide();
		     }
		});
}

//查询商品
function querySkuList(){
	$('#skuSelectList').empty();

	var json_info = {
		"sku_name" : $('#sku_search').val(),
		"sku_type" : ""
	};

	var goodsData = {
		jsession_id: jsession_id,
		oper_sku: "sku_define",
		json_info: JSON.stringify(json_info)
	};

	$.ajax({
		type: "post",
		url: rest_host+"rest/skuServiceRest/querySkuDefineAndSkuAttrNew",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: goodsData,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				var skuListHtml = '';
				if (data.args.sku_define_list != null && data.args.sku_define_list.length>0) {
					sku_define_list = data.args.sku_define_list;
					$.each(sku_define_list, function(i, item) {
						skuListHtml = skuListHtml+'<span class="width50per"><a no="'+item.sku_id+'" type="'+item.sku_type+'" >'+item.sku_name+'</a></span>';
					});
				}
				$('#skuSelectList').append(skuListHtml);
			}else{
				$.message.error('sku查询异常!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('sku查询Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
}

//查询商品类型、状态
function queryGoodsCode(type_code){

	var goodsData = {
		jsession_id: jsession_id,
		type_code: type_code
	};

	$.ajax({
		type: "post",
		url: rest_host+"rest/CodeList/getCodeListByType",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: false,
		data: goodsData,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				var html = '';
				if (data.args.code_list != null && data.args.code_list.length>0) {
					$.each(data.args.code_list, function(i, item) {
						html = html+'<option value="'+item.code_id+'">'+item.code_name+'</option>';
					});

					if(type_code=="goods_type"){
						goods_type_html = html;
						goods_type = data.args.code_list;
						$('[name="goods_type"]').append(html);
					}
					if(type_code=="goods_state"){
						goods_state_html = html;
						goods_state = data.args.code_list;
						$('[name="goods_state"]').append(html);
					}
				}
			}else{
				$.message.error('code_list查询异常!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('code_list查询Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
}

//翻译商品类型、状态
function getSelectGoodsHtml(type_code,code_id){
	var code_nm = "";
	var html = '';
	if(type_code=="goods_state"){
		$.each(goods_state, function(i, item) {
			if(item.code_id==code_id){
				code_nm = item.code_name;
				html = html+'<option value="'+item.code_id+'" selected>'+item.code_name+'</option>';
			}else{
				html = html+'<option value="'+item.code_id+'">'+item.code_name+'</option>';
			}
		});

		goods_state_html = html;
	}

	if(type_code=="goods_type"){
		$.each(goods_type, function(i, item) {
			if(item.code_id==code_id){
				code_nm = item.code_name;
				html = html+'<option value="'+item.code_id+'" selected>'+item.code_name+'</option>';
			}else{
				html = html+'<option value="'+item.code_id+'">'+item.code_name+'</option>';
			}
		});

		goods_type_html = html;
	}

	if(INPUT_UTIL.isNull(code_nm)){
		code_nm = code_id+"(编码)";
	}

	return code_nm;
}
