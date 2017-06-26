var jsession_id = "";
var rest_host = "";
var state = "";

$(document).ready(function() {
	jsession_id = HTML_UTLS.getParam("jsession_id");
	rest_host=$('#apc_rest_host').val();

	queryApList();
	getSelectOpers();
	getSelectProduct();
	getSelectType();
	getSelectRegion();
	getSelectChnl();
	getSelectDevelopers();

	// 查询 按钮事件
	$('#searchBtn').on('click', function(e) {
		$("#pageNo").val("1");
		queryApList();
	});

	// 重置 按钮事件
	$('#resetBtn').on('click', function(e) {
		reset(1);
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

	/* 发展人模态框 - start */
	// 发展人模态框属性设置
	$.modal('#developerModal', {
        width: 550,
        height: 350
    });
	// 弹出发展人模态框
	var inputDeveloperName = '';
	$('#searchDeveloper, #modalDeveloper').on('click', function(e) {
		inputDeveloperName = $(this).attr('id');

		var selectChnlCode = $("#"+inputApChannel).attr('no');
		if(!INPUT_UTIL.isNull(selectChnlCode)){
			getSelectDevelopers(selectChnlCode);
		}

		$('#developerModal').show();
	});
	// 发展人模态框-确定按钮事件
	$('#developerConfirmBtn').on('click', function(e) {
		var inputBox = $('#' + inputDeveloperName);
		inputBox.val('');
		inputBox.attr('no', '');
		var list = $('#developerList a.active');
		if (list.length > 0) {
			$.each(list, function(i, item) {
				inputBox.val(inputBox.val() + $(item).text());
				inputBox.attr('no', inputBox.attr('no') + $(item).attr('no'));
				if (i < list.length - 1) {
					inputBox.val(inputBox.val() + ', ');
					inputBox.attr('no', inputBox.attr('no') + ', ');
				}
			});
		}
		$('#developerModal').hide();
	});
	// 发展人模态框-取消按钮事件
	$('#developerCancelBtn').on('click', function(e) {
		$('#developerModal').hide();
	});
	// 发展人模态框-查询按钮事件
	$('#developerSearchBtn').on('click', function(e) {
		var selectChnlCode = $("#"+inputApChannel).attr('no');
		if(!INPUT_UTIL.isNull(selectChnlCode)){
			getSelectDevelopers(selectChnlCode);
		}else{
			getSelectDevelopers();
		}
	});
	/* 发展人模态框 - end */

	/* 绑定工号模态框 - start */
	// 绑定工号模态框属性设置
	$.modal('#operNoModal', {
        width: 550,
        height: 350
    });
	// 弹出绑定工号模态框
	var inputOperNo = '';
	$('#searchOperNo, #modalOperNo').on('click', function(e) {
		inputOperNo = $(this).attr('id');
		$('#operNoModal').show();
	});
	// 绑定工号模态框-确定按钮事件
	$('#operNoConfirmBtn').on('click', function(e) {
		var inputBox = $('#' + inputOperNo);
		inputBox.val('');
		inputBox.attr('no', '');
		var operNo = $('#operNoList a.active');
		if (operNo.length > 0) {
			inputBox.val(operNo.text());
			inputBox.attr('no', operNo.attr('no'));
		}
		$('#operNoModal').hide();
	});
	// 绑定工号模态框-取消按钮事件
	$('#operNoCancelBtn').on('click', function(e) {
		$('#operNoModal').hide();
	});
	// 绑定工号模态框-查询按钮事件
	$('#operNoSearchBtn').on('click', function(e) {
		getSelectOpers();
	});
	/* 绑定工号模态框 - end */

	/* 选择商品模态框 - start */
	// 选择商品模态框属性设置
	$.modal('#prodModal', {
        width: 550,
        height: 350
    });
	// 弹出选择商品模态框
	var inputProcduct = '';
	$('#searchProduct, #modalProduct').on('click', function(e) {
		inputProcduct = $(this).attr('id');
		$('#prodModal').show();
	});
	// 选择商品模态框-确定按钮事件
	$('#prodConfirmBtn').on('click', function(e) {
		var inputBox = $('#' + inputProcduct);
		inputBox.val('');
		inputBox.attr('no', '');
		var list = $('#prodList a.active');
		if (list.length > 0) {
			$.each(list, function(i, item) {
				inputBox.val(inputBox.val() + $(item).text());
				inputBox.attr('no', inputBox.attr('no') + $(item).attr('no'));
				if (i < list.length - 1) {
					inputBox.val(inputBox.val() + ', ');
					inputBox.attr('no', inputBox.attr('no') + ', ');
				}
			});
		}
		$('#prodModal').hide();
	});
	// 选择商品模态框-取消按钮事件
	$('#prodCancelBtn').on('click', function(e) {
		$('#prodModal').hide();
	});
	/* 选择商品模态框 - end */


	/* 新增触点模态框 - start */
	// 新增触点模态框属性设置
	$.modal('#contactModal', {
        width: 820,
        height:270
    });
	// 弹出新增触点模态框
	$('#contactAddBtn').on('click', function(e) {
		$('#contactModal').show();
		reset(2);
	});
	// 新增触点模态框-确定按钮事件
	$('#contactConfirmBtn').on('click', function(e) {
		operateApInfo(100,"","");
	});
	// 新增触点模态框-取消按钮事件
	$('#contactCancelBtn').on('click', function(e) {
		$('#contactModal').hide();
	});
	/* 新增触点模态框 - end */

	/* 二维码模态框 - start */
	// 二维码模态框属性设置
	$.modal('#qrcodeModal', {
        width: 550,
        height: 460
    });
	// 二维码模态框-另存为按钮事件
	/*$('#qrcodeSaveAsBtn').on('click', function(e) {
		alert($(this).text());
	});*/
	// 二维码模态框-关闭按钮事件
	$('#qrcodeCloseBtn').on('click', function(e) {
		loadingQrcode(); // 清除二维码
		$('#qrcodeModal').hide();
	});
	/* 二维码模态框 - end */

/*==========>选择地区模态框<==========*/
	// 选择地区模态框属性设置
	$.modal('#apRegionModal', {
        width: 550,
        height: 350
    });
	// 弹出选择地区模态框
	var inputRegion = '';
	$('#searchRegion, #modalApRegion').on('click', function(e) {
		inputRegion = $(this).attr('id');
		$('#apRegionModal').show();
	});
	// 选择地区模态框-确定按钮事件
	$('#apRegionConfirmBtn').on('click', function(e) {
		var inputBox = $('#' + inputRegion);
		inputBox.val('');
		inputBox.attr('no', '');
		var operNo = $('#apRegionList a.active');
		if (operNo.length > 0) {
			inputBox.val(operNo.text());
			inputBox.attr('no', operNo.attr('no'));
		}
		$('#modalApChannel').attr('no','');
		$('#modalApChannel').val('');
		$('#modalDeveloper').attr('no','');
		$('#modalDeveloper').val('');
		$('#apRegionModal').hide();
	});
	// 选择地区模态框-取消按钮事件
	$('#apRegionCancelBtn').on('click', function(e) {
		$('#apRegionModal').hide();
	});
	// 选择地区模态框-查询按钮事件
	$('#apRegionSearchBtn').on('click', function(e) {
		getSelectRegion();
	});
	/* 选择地区模态框 - end */

/*==========>选择渠道模态框-start<==========*/
	// 选择地区模态框属性设置
	$.modal('#apChnlModal', {
        width: 800,
        height: 400
    });
	// 弹出选择渠道模态框
	var inputApChannel = '';
	$('#searchChannel, #modalApChannel').on('click', function(e) {
		inputApChannel = $(this).attr('id');

		var selectRegionId = $("#"+inputRegion).attr('no');
		if(!INPUT_UTIL.isNull(selectRegionId)){
			getSelectChnl(selectRegionId);
		}

		$('#apChnlModal').show();
	});
	// 选择渠道模态框-确定按钮事件
	$('#apChnlConfirmBtn').on('click', function(e) {
		var inputBox = $('#' + inputApChannel);
		inputBox.val('');
		inputBox.attr('no', '');
		var operNo = $('#apChnlList a.active');
		if (operNo.length > 0) {
			inputBox.val(operNo.text());
			inputBox.attr('no', operNo.attr('no'));
		}
		$('#modalDeveloper').attr('no','');
		$('#modalDeveloper').val('');
		$('#apChnlModal').hide();
	});
	// 选择渠道模态框-取消按钮事件
	$('#apChnlCancelBtn').on('click', function(e) {
		$('#apChnlModal').hide();
	});
	// 选择渠道模态框-查询按钮事件
	$('#apChnlSearchBtn').on('click', function(e) {
		var selectRegionId = $("#"+inputRegion).attr('no');
		if(!INPUT_UTIL.isNull(selectRegionId)){
			getSelectChnl(selectRegionId);
		}else{
			getSelectChnl();
		}
	});
	/* 选择渠道模态框 - end */

/*==========>选择类型模态框-start<==========*/
	// 选择地区模态框属性设置
	$.modal('#apTypeModal', {
        width: 550,
        height: 350
    });
	// 弹出选择类型模态框
	var inputApType = '';
	$('#searchType, #modalApType').on('click', function(e) {
		inputApType = $(this).attr('id');
		$('#apTypeModal').show();
	});
	// 选择类型模态框-确定按钮事件
	$('#apTypeConfirmBtn').on('click', function(e) {
		var inputBox = $('#' + inputApType);
		inputBox.val('');
		inputBox.attr('no', '');
		var operNo = $('#apTypeList a.active');
		if (operNo.length > 0) {
			inputBox.val(operNo.text());
			inputBox.attr('no', operNo.attr('no'));
		}
		$('#apTypeModal').hide();
	});
	// 选择类型模态框-取消按钮事件
	$('#apTypeCancelBtn').on('click', function(e) {
		$('#apTypeModal').hide();
	});
	// 选择类型模态框-查询按钮事件
	$('#apTypeSearchBtn').on('click', function(e) {
		getSelectType();
	});
	/*==========>选择类型模态框-end<==========*/

	// 查看二维码 按钮事件
	$('#contactList').on('click', 'td .btn_link[name="viewQrcodeBtn"]', function(e) {
		var $this = $(this);
		createQrcode($this.parent().parent().attr('ap_url'),$this.parent().parent().attr('ap_id')); // 生成二维码
		$('#qrcodeModal').show();
	});

	// 属性配置 按钮
	$('#contactList').on('click', 'td .btn_link[name="apAttrSetBtn"]',function(e) {
		var $this = $(this);
		var ap_id_temp = $this.parent().parent().attr('ap_id');
		var ap_name_temp = $this.parent().parent().attr('ap_name');
		window.location.href = 'contactCreateAttr.jsp?ap_id='+ap_id_temp
			+'&jsession_id='+jsession_id+'&ap_name='+encodeURIComponent(ap_name_temp);
	});

	// 生效 按钮事件
	$('#contactList').on('click', 'td .btn_link[name="enableBtn"]', function(e) {
		var $this = $(this);
		$.dialog.confirm('确定生效吗？', null, '确定', '取消',
		    function() {
				operateApInfo(101,$this.parent().parent(),0);
			}
		);
	});

	// 失效 按钮事件
	$('#contactList').on('click', 'td .btn_link[name="disableBtn"]', function(e) {
		var $this = $(this);
		$.dialog.confirm('确定失效吗？', null, '确定', '取消',
		    function() {
				operateApInfo(101,$this.parent().parent(),1);
			}
		);
	});

	// 跳到首页
	$('#pageFirst').on('click', function(e) {
		var page_no = "1";
		$("#pageNo").val(page_no);
		queryApList();
	});
	// 跳到上一页
	$('#pageUp').on('click', function(e) {
		if($("#pageNo").val() == "1"){
			return;
		}else{
			var page_no = parseInt($("#pageNo").val()) - 1;
			$("#pageNo").val(page_no);
			queryApList();
		}
	});
	// 跳到下一页
	$('#pageDown').on('click', function(e) {
		if($("#pageNo").val() == $("#totalPage").text()){
			return;
		}else{
			var page_no = parseInt($("#pageNo").val()) + 1;
			$("#pageNo").val(page_no);
			queryApList();
		}
	});
	// 跳到尾页
	$('#pageLast').on('click', function(e) {
		var page_no = $("#totalPage").text();
		$("#pageNo").val(page_no);
		queryApList();
	});

	$('#pageSize').change(function(e){
		$("#pageNo").val("1");
		queryApList();
	});

});

//生成二维码
function createQrcode(ap_url,ap_id) {
	var txt = ap_url+'?ap_id=' + ap_id;
	$('#qrCode').empty().qrcode({
		render:'table',	//渲染方式
	    width: 300, // 宽度
	    height: 300, // 高度
	    text: txt // 二维码内容
	});
}

//正在加载二维码
function loadingQrcode() {
	$('#qrCode').html('<p>正在加载...</p>');
}

// 重置
function reset(type){
	if(type==1){
		$('#searchStatus').val("0");
		$('#searchApName').val("");
		$('#searchProduct').val("");
		$('#searchDeveloper').val("");
		$('#searchOperNo').val("");
		$('#searchProduct').attr("no","");
		$('#searchDeveloper').attr("no","");
		$('#searchOperNo').attr("no","");
		$('#searchModalOperNo').val("");
		$('#searchModalDeveloper').val("");

		$('#searchRegion').val("");
		$('#searchChannel').val("");
		$('#searchRegion').attr("no","");
		$('#searchChannel').attr("no","");
	}else{
		$('#modalApName').val("");
		$('#modalApDesc').val("");
		$('#modalApUrl').val("");
		$('#modalOperNo').val("");
		$('#modalProduct').val("");
		$('#modalDeveloper').val("");
		$('#modalOperNo').attr("no","");
		$('#modalProduct').attr("no","");
		$('#modalDeveloper').attr("no","");

		$('#modalApStartTime').val("");
		$('#modalApEndTime').val("");
		$('#modalApType').val("");
		$('#modalApRegion').val("");
		$('#modalApChannel').val("");
		$('#modalApType').attr("no","");
		$('#modalApRegion').attr("no","");
		$('#modalApChannel').attr("no","");
	}

	var list=$('.option_box a.active');
	if (list.length > 0) {
		$.each(list, function(i, item) {
			$(item).removeClass('active');
		});
	}
}

//触点查询
function queryApList(){
	var json_info = {
			//触点定义表
			"ap_define":{
				"ap_name" : $('#searchApName').val(),
				"state" : $('#searchStatus').val(),
				"bind_oper" : $('#searchOperNo').attr('no'),
				"region" : $('#searchRegion').attr('no'),
				"chnl_code" : $('#searchChannel').attr('no'),
				"ap_type" : $('#searchType').attr('no')
			},
			//触点发展人表
			"ap_developer":{
				"developer_no" : $('#searchDeveloper').attr('no')
			},
			//触点商品表
			"ap_goods":{
				"goods_id": $('#searchProduct').attr('no')
			},
			"pageNo": $('#pageNo').val(),
			"pageSize": $('#pageSize').val()
		};

	var queryData={
		"jsession_id":jsession_id,
		"json_info":JSON.stringify(json_info)
		};

	$.ajax({
		type: "post",
		url: rest_host+"rest/ap/queryApList",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: queryData,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				$('#contactList').empty();
				$("#totalCount").html(data.args.total_num);
				$("#totalPage").html(data.args.total_page);
				var apHtml='';
				if (data.args.ap_list != null && data.args.ap_list.length>0) {
					$.each(data.args.ap_list, function(i, item) {
						apHtml=apHtml+'<tr ap_id="'+item.ap_id+'" ap_name="'+item.ap_name+'" ap_url="'+item.ap_url+'">'
							+'<td>'+item.ap_name+'</td>'
							+'<td>'+(INPUT_UTIL.isNull(item.ap_desc)?"":item.ap_desc)+'</td>'
							+'<td>'+(INPUT_UTIL.isNull(item.goods_name)?"":item.goods_name)+'</td>'
							+'<td>'+item.ap_url+'</td>'
							+'<td class="text_center">'+(item.region_name==null?"":item.region_name) +'</td>'
							+'<td class="text_center">'+(item.chnl_name==null?"":item.chnl_name) + '</td>'
							+'<td class="text_center">'+item.developer_name+'</td>'
							+'<td class="text_center">'+(INPUT_UTIL.isNull(item.bind_oper)?"":item.bind_oper)+'</td>'
							+'<td class="text_center">'+item.create_time+'</td>'
							+'<td class="text_center">'+item.create_staff+'</td>'
							+'<td class="text_center">'+(item.eff_date==null?"":item.eff_date) + '</td>'
							+'<td class="text_center">'+(item.exp_date==null?"":item.exp_date) + '</td>'
							+'<td class="text_center">'+(item.state==0?'有效':'无效')+'</td>'
							+'<td class="text_center">'
							+'	<div class="btn btn_link" name="viewQrcodeBtn">查看二维码</div>'
							+'	<div class="btn btn_link" name="apAttrSetBtn">属性配置</div>'
							+'	<div class="btn btn_link" name="'+(item.state==0?'disableBtn':'enableBtn')+'">'+(item.state==0?'失效':'生效')+'</div>'
							+'</td>'
							+'</tr>';
					});
				}
				$('#contactList').append(apHtml);
			} else {
				$.message.error('获取触点信息异常!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取触点信息Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
}

//触点维护 oper_type:100新增，101修改，102删除
function operateApInfo(oper_type,selectItem,state){
	var ap_id=new Date().getTime();

	if(oper_type==100&&(INPUT_UTIL.isNull($('#modalApName').val())||
		INPUT_UTIL.isNull($('#modalApUrl').val())||INPUT_UTIL.isNull($('#modalOperNo').val())||
		INPUT_UTIL.isNull($('#modalProduct').val()) || INPUT_UTIL.isNull($('#modalApDesc').val()))){
		$.message.error('所需触点信息填写不完整！');
		return;
	}

	var modalApStartTime = "";
	var modalApEndTime = "";
	if( INPUT_UTIL.isNull($('#modalApStartTime').val()) ){
		modalApStartTime = "2017-01-01 00:00:01";
	}else{
		modalApStartTime = $('#modalApStartTime').val()+" 00:00:01";
	}
	if( INPUT_UTIL.isNull($('#modalApEndTime').val()) ){
		modalApEndTime = "2099-12-31 23:59:59";
	}else{
		modalApEndTime = $('#modalApEndTime').val()+" 23:59:59";
	}

	var json_info = {
			//触点定义表
			"ap_define":{
				"ap_id" : oper_type!=100?selectItem.attr('ap_id'):ap_id,
				"state" : oper_type!=100?state:"",//0-有效1-失效
				"ap_name" : oper_type!=100?"":$('#modalApName').val(),
				"ap_desc" : oper_type!=100?"":$('#modalApDesc').val(),
				"ap_url" : oper_type!=100?"":$('#modalApUrl').val(),
				"bind_oper" : oper_type!=100?"":$('#modalOperNo').attr('no'),
				"ap_type" : oper_type!=100?"":$('#modalApType').attr('no'),
//				"eff_date" : oper_type!=100?"":$('#modalApStartTime').val(),
//				"exp_date" : oper_type!=100?"":$('#modalApEndTime').val(),
				"eff_date" : oper_type!=100?"":modalApStartTime,
				"exp_date" : oper_type!=100?"":modalApEndTime,
				"region" : oper_type!=100?"":$('#modalApRegion').attr('no'),
				"chnl_code" : oper_type!=100?"":$('#modalApChannel').attr('no')
			},
			//触点发展人表
			"ap_developer":{
				"ap_id" :  oper_type!=100?selectItem.attr('ap_id'):ap_id,
				"developer_no" : oper_type!=100?"":$('#modalDeveloper').attr('no'),
				"developer_name" : oper_type!=100?"":$('#modalDeveloper').val()
			},
			//触点商品表
			"ap_goods":{
				"ap_id" :  oper_type!=100?selectItem.attr('ap_id'):ap_id,
				"goods_id": oper_type!=100?"":$('#modalProduct').attr('no')
			}
		};

	var operateData={
			"jsession_id":jsession_id,
			"oper_type":oper_type,
			"json_info":JSON.stringify(json_info)
			};

	$.ajax({
		type: "post",
		url: rest_host+"rest/ap/operateAp",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: operateData,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在处理");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				if(oper_type==100){
					createQrcode($('#modalApUrl').val(),ap_id); // 生成二维码
					$('#contactModal').hide();
					$('#qrcodeModal').show();
				}
			}else{
				$.message.error('触点维护异常!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('触点维护Ajax请求失败!');
		},
		complete:function(){
			queryApList();
			$.loading.hide();
		}
	});
}

//获取可选工号
function getSelectOpers(){
	var operData = {
			jsessionId: jsession_id,
			operNo: "",
			operName: $('#searchModalOperNo').val()
		};

	$.ajax({
		type: "post",
		url: rest_host+"rest/optionalOper/getOptionalOperInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: operData,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000" && !INPUT_UTIL.isNull(data.args.json_info)) {
				$('#operNoList').empty();
				var operHtml = '';
				var oper_list = JSON.parse(data.args.json_info).oper_list;
				if (oper_list != null && oper_list.length>0) {
					$.each(oper_list, function(i, item) {
						operHtml=operHtml+'<span class="one_third"><a no="'+item.oper_no+'">'+item.oper_no+' / '+item.oper_name+'</a></span>';
					});
				}
				$('#operNoList').append(operHtml);
			} else {
				$.message.error('获取可选工号异常!'+data.content);
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

//获取可选发展人，可传入渠道编码
function getSelectDevelopers(selected_chnl_code){
	var developerData = {
			jsessionId: jsession_id,
			developerNo: "",
			developerName: $('#searchModalDeveloper').val(),
			region:"",
			chnl_code: selected_chnl_code==null?"":selected_chnl_code
		};

	$.ajax({
		type: "post",
		url: rest_host+"rest/optionalOper/getOptionalDeveInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: developerData,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000" && !INPUT_UTIL.isNull(data.args.json_info)) {
				$('#developerList').empty();
				var developerHtml = '';
				var developer_list = JSON.parse(data.args.json_info).developer_list;
				if (developer_list != null && developer_list.length>0) {
					$.each(developer_list, function(i, item) {
						developerHtml=developerHtml+'<span class="one_third"><a no="'+item.dev_code+'">'+item.dev_name+'</a></span>';
					});
				}
				$('#developerList').append(developerHtml);
			} else {
				$.message.error('获取可选发展人异常!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取可选发展人Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
}

//获取可选商品
function getSelectProduct(){
	var productData = {
			jsession_id: jsession_id
		};

	$.ajax({
		type: "post",
		url: rest_host+"rest/optionalOper/getOptionalGoodsInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: productData,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000" && !INPUT_UTIL.isNull(data.args.json_info)) {
				$('#prodList').empty();
				var productHtml = '';
				var goods_list = JSON.parse(data.args.json_info).goods_list;
				if (goods_list != null && goods_list.length>0) {
					$.each(goods_list, function(i, item) {
						productHtml=productHtml+'<span class="one_third"><a no="'+item.goods_id+'">'+item.goods_name+'</a></span>';
					});
				}
				$('#prodList').append(productHtml);
			} else {
				$.message.error('获取可选商品异常!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取可选商品Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
}

/**
 * 获取可选地区
 */
function getSelectRegion(){
	var regionData = {
			jsession_id: jsession_id,
			region_name: ($("#apRegionSearchModal").val() != null ? $("#apRegionSearchModal").val() : ""),
			region_id: "",
			parent_region_id: "",
			region_level: ""
		};

	$.ajax({
		type: "post",
		url: rest_host+"rest/optionalOper/getOptionalRegionInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: regionData,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000" && !INPUT_UTIL.isNull(data.args.json_info)) {
				var region_list = JSON.parse(data.args.json_info).region_list;
				$('#apRegionList').empty();
				var apRegionHtml = '';
				if (region_list != null && region_list.length>0) {
					$.each(region_list, function(i, item) {
						apRegionHtml = apRegionHtml+'<span class="one_third"><a no="'+item.region_id+'">'+item.region_name+'</a></span>';
					});
				}
				$('#apRegionList').append(apRegionHtml);
			} else {
				$.message.error('获取可选地区异常!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取可选地区Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
}
/**
 * 获取可选渠道
 * @param selectRegionId
 */
function getSelectChnl(selectRegionId){
	var par_region_id = "";
	if( !INPUT_UTIL.isNull(selectRegionId) ){
		par_region_id = selectRegionId;
	}
	var requestData = {
		jsession_id: jsession_id,
		chnl_name: ($("#apChnlSearchModal").val() != null ? $("#apChnlSearchModal").val() : ""),
		chnl_code: "",
		region_id: par_region_id
	};
	$.ajax({
		type: "post",
		url: rest_host+"rest/optionalOper/getOptionalAgentInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: requestData,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			$('#apChnlList').empty();
			if (data.respCode=="0000" && !INPUT_UTIL.isNull(data.args.json_info)) {
				var chnl_list = JSON.parse(data.args.json_info).chnl_list;
				var apChnlHtml = '';
				if (chnl_list != null && chnl_list.length>0) {
					$.each(chnl_list, function(i, item) {
						apChnlHtml = apChnlHtml+'<span class="one_third"><a no="'+item.chnl_code+'">'+item.chnl_name+'</a></span>';
					});
				}
				$('#apChnlList').append(apChnlHtml);
			} else {
				$.message.error('获取可选渠道异常!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取可选渠道Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
}
/**
 * 获取触点类型
 * @param selectRegionId
 */
function getSelectType(){
	var requestData = {
		jsession_id: jsession_id,
		type_code: "1000"
	};
	$.ajax({
		type: "post",
		url: rest_host+"rest/codeList/getCodeListByTypeCode",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: requestData,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			$('#apTypeList').empty();
			if (data.respCode=="0000" && !INPUT_UTIL.isNull(data.args.redisData.code_list)) {
				var code_list = data.args.redisData.code_list;
				var apTypeHtml = '';
				if (code_list != null && code_list.length>0) {
					$.each(code_list, function(i, item) {
						apTypeHtml = apTypeHtml+'<span class="one_third"><a no="'+item.code_id+'">'+item.code_name+'</a></span>';
					});
				}
				$('#apTypeList').append(apTypeHtml);
			} else {
				$.message.error('获取触点类型异常!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取触点类型Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
}
