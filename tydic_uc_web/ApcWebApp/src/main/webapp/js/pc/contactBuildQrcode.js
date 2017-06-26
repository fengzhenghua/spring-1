var jsession_id = "";
var rest_host = "";

$(document).ready(function() {
	jsession_id = HTML_UTLS.getParam("jsession_id");
	rest_host = $('#apc_rest_host').val();

	queryApList();
	getSelectDevelopers();
	getSelectOpers();
	getSelectProduct();
	getSelectChnl();
	getOptionalRegion();
	getSelectType();
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
        width: 750,
        height: 350
    });
	// 弹出发展人模态框
	var inputDeveloperName = '';
	$('#searchDeveloper').on('click', function(e) {
		inputDeveloperName = $(this).attr('id');
		$('#developerModal').show();
	});
	// 发展人模态框-确定按钮事件
	$('#developerConfirmBtn').on('click', function(e) {
		var inputBox = $('#' + inputDeveloperName);
		inputBox.val('');
		inputBox.attr('no', '');
		var list = $('#developerList a.active');
		if (list.length > 0) {
			if(inputDeveloperName=='modalDeveloper'&&list.length>1){
				$.message.info('只能选择一个发展人！');
				return;
			}

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
		var selectChnlCode = $("#"+inputChannel).attr('no');
		if(!INPUT_UTIL.isNull(selectChnlCode)){
			getSelectDevelopers(selectChnlCode);
		}else{
			getSelectDevelopers();
		}
	});
	/* 发展人模态框 - end */

	/* 绑定发展人模态框 - start */
	// 绑定发展人模态框属性设置
	$.modal('#bindingDeveloperModal', {
        width: 750,
        height: 350
    });
	// 弹出绑定发展人模态框
	$('#modalDeveloper').on('click',function(e){
		var ap_id = $('#bindingContactModal').attr('modalapid');
		$('#bindingDeveloperModal').attr('ap_id',ap_id);
		$('#bindingDeveloperModal').show();
	});
	//查询即时激励发展人
	$('#modalJiLiDeveloper').on('click',function(e){
		if(INPUT_UTIL.isNull($('#modalDeveloper').attr('no'))){
			$.message.error('请先选择发展人!');
			return;
		}
		getJiLiDevelopers();
		//$('#bindingJiLiDeveloperModal').show();
	});
	//设定即时激励发展人弹框大小
	$.modal('#bindingJiLiDeveloperModal', {
        width: 750,
        height: 350
    });
	// 即时激励发展人模态框-确定按钮事件
	$('#bindingJiLiDeveloperConfirmBtn').on('click', function(e) {
		var inputBox = $('#modalJiLiDeveloper');
		inputBox.val('');
		inputBox.attr('no', '');
		var list = $('#bindingJiLiDeveloperList a.active');
		if (list.length > 0) {
			if(list.length>1){
				$.message.info('只能选择一个即时激励发展人！');
				return;
			}

			$.each(list, function(i, item) {
				inputBox.val(inputBox.val() + $(item).text());
				inputBox.attr('no', inputBox.attr('no') + $(item).attr('no'));
				if (i < list.length - 1) {
					inputBox.val(inputBox.val() + ', ');
					inputBox.attr('no', inputBox.attr('no') + ', ');
				}
			});
		}
		$('#bindingJiLiDeveloperModal').hide();
	});
	// 即时激励发展人模态框-取消按钮事件
	$('#bindingJiLiDeveloperCancelBtn').on('click', function(e) {
		$('#bindingJiLiDeveloperModal').hide();
	});
	// 绑定发展人模态框-确定按钮事件
	$('#bindingDeveloperConfirmBtn').on('click', function(e) {
		var inputBox = $('#modalDeveloper');
		inputBox.val('');
		inputBox.attr('no', '');
		var list = $('#bindingDeveloperList a.active');
		if (list.length > 0) {
			if(list.length>1){
				$.message.info('只能选择一个发展人！');
				return;
			}

			$.each(list, function(i, item) {
				inputBox.val(inputBox.val() + $(item).text());
				inputBox.attr('no', inputBox.attr('no') + $(item).attr('no'));
				if (i < list.length - 1) {
					inputBox.val(inputBox.val() + ', ');
					inputBox.attr('no', inputBox.attr('no') + ', ');
				}
			});
		}
		$('#bindingDeveloperModal').hide();
	});
	// 绑定发展人模态框-取消按钮事件
	$('#bindingDeveloperCancelBtn').on('click', function(e) {
		$('#bindingDeveloperModal').hide();
	});
	// 绑定发展人模态框-查询按钮事件
	$('#bindingDeveloperSearchBtn').on('click', function(e) {
		var ap_id=$('bindingDeveloperModal').attr('ap_id');
		getBindDevelopers(ap_id);
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
	$('#searchProduct').on('click', function(e) {
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

	/* 选择触点类型模态框 - start */
	// 选择触点类型模态框属性设置
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
	/* 选择触点类型模态框 - end */

	/* 可选地区模态框 - start */
	// 可选地区模态框属性设置
	$.modal('#areaModal', {
        width: 550,
        height: 350
    });
	// 弹出可选地区模态框
	var inputRegion = '';
	$('#searchArea').on('click', function(e) {
		inputRegion = $(this).attr('id');
		$('#areaModal').show();
	});
	//可选地区模态框-确定按钮事件
	$('#areaConfirmBtn').on('click', function(e) {
		var inputBox = $('#' + inputRegion);
		inputBox.val('');
		inputBox.attr('no', '');
		var areaList = $('#areaList a.active');
		if (areaList.length > 0) {
			inputBox.val(areaList.text());
			inputBox.attr('no', areaList.attr('no'));
		}
		$('#areaModal').hide();
	});
	// 可选地区模态框-取消按钮事件
	$('#areaCancelBtn').on('click', function(e) {
		$('#areaModal').hide();
	});
	// 可选地区模态框-查询按钮事件
	$('#areaSearchBtn').on('click', function(e) {
		getOptionalRegion();
	});
	/* 可选地区模态框 - end */

	/* 可选渠道模态框 - start */
	// 可选渠道模态框属性设置
	$.modal('#channelModal', {
        width: 750,
        height: 350
    });
	// 弹出可选渠道模态框
	var inputChannel = '';
	$('#searchChannel,#searchmodalChannel').on('click', function(e) {
		inputChannel = $(this).attr('id');
		var selectRegionId = $("#"+inputRegion).attr('no');
		if(!INPUT_UTIL.isNull(selectRegionId)){
			getSelectChnl(selectRegionId);
		}
		$('#channelModal').show();
	});
	//可选渠道模态框-确定按钮事件
	$('#channelConfirmBtn').on('click', function(e) {
		var inputBox = $('#' + inputChannel);
		inputBox.val('');
		inputBox.attr('no', '');
		var channelList = $('#channelList a.active');
		if (channelList.length > 0) {
			inputBox.val(channelList.text());
			inputBox.attr('no', channelList.attr('no'));
		}
		var selectChnlCode = $("#"+inputChannel).attr('no');
		if(!INPUT_UTIL.isNull(selectChnlCode)){
			getSelectDevelopers(selectChnlCode);
		}
		$('#channelModal').hide();
	});
	// 可选渠道模态框-取消按钮事件
	$('#channelCancelBtn').on('click', function(e) {
		$('#channelModal').hide();
	});
	// 可选渠道模态框-查询按钮事件
	$('#channelSearchBtn').on('click', function(e) {
		var selectRegionId = $("#"+inputRegion).attr('no');
		if(!INPUT_UTIL.isNull(selectRegionId)){
			getSelectChnl(selectRegionId);
		}else{
			getSelectChnl();
		}
	});

	/* 生成二维码模态框 - start */
	// 生成二维码模态框属性设置
	$.modal('#contactModal', {
        width: 650,
        height: 250
    });
	// 弹出生成页面模态框
	$('#contactList').on('click', 'td .btn_link[name="contactAddBtn"]', function(e) {
		var $this = $(this);
		var ap_id=$this.parent().parent().attr('ap_id');
		var chnl_code=$this.parent().parent().attr('chnl_code');
		var region_id=$this.parent().parent().attr('region_id');
		getBindDevelopers(ap_id,chnl_code,region_id);
		var apName=$this.parent().prevAll('td:eq(9)').text();
		var apUrl=$this.parent().prevAll('td:eq(6)').text();
		$('#contactModal').attr('modalApId',ap_id);
		$('#modalApName').text(apName);
		$('#modalApUrl').text(apUrl);
		$('#contactModal').show();
		reset(2);
	});
	// 生成二维码模态框-确定按钮事件
	$('#contactConfirmBtn').on('click', function(e) {
		createQrcode($('#modalApUrl').text(),$('#contactModal').attr('modalApId'),$('#modalDeveloper').attr('no'),$('#modalDeveloper').val()); // 生成二维码
		$('#qrcodeModal').show();
		$('#contactModal').hide();
	});

	// 生成二维码模态框-取消按钮事件
	$('#contactCancelBtn').on('click', function(e) {
		$('#contactModal').hide();
	});
	/* 生成二维码模态框 - end */

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
function createQrcode(ap_url,ap_id,dev_code,dev_name) {
	var txt = "";
	if(INPUT_UTIL.isNull(dev_code)){
		txt = ap_url + '?ap_id=' + ap_id;
	}else{
		txt = ap_url + '?ap_id=' + ap_id + '&dev_code=' + dev_code + '&dev_name=' + dev_name;
	}
	
	var reward_oper_no=$('#modalJiLiDeveloper').attr('no');
	if(!INPUT_UTIL.isNull(reward_oper_no)){
		txt = ap_url + '?ap_id=' + ap_id + '&dev_code=' + dev_code + '&dev_name=' + dev_name + '&reward_oper_no=' + reward_oper_no;
	}

	$('#qrCode').empty().qrcode({
		render:'table',	//渲染方式
	    width: 300, // 宽度
	    height: 300, // 高度
	    text: utf16to8(txt) // 二维码内容
	});
}

//转换为UTF-8
function utf16to8(str) {
    var out, i, len, c;
    out = "";
    len = str.length;
    for(i = 0; i < len; i++) {
    c = str.charCodeAt(i);
    if ((c >= 0x0001) && (c <= 0x007F)) {
        out += str.charAt(i);
    } else if (c > 0x07FF) {
        out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
        out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));
        out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));
    } else {
        out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));
        out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));
    }
    }
    return out;
}

//正在加载二维码
function loadingQrcode() {
	$('#qrCode').html('<p>正在加载...</p>');
}

// 重置
function reset(type){
	if(type==1){
		$('#searchApId').val("");
		$('#searchApName').val("");
		$('#searchProduct').val("");
		$('#searchDeveloper').val("");
		$('#searchOperNo').val("");
		$('#searchProduct').attr("no","");
		$('#searchDeveloper').attr("no","");
		$('#searchOperNo').attr("no","");
		$('#searchModalOperNo').val("");
		$('#searchModalDeveloper').val("");
		$('#searchChannel').val("");
		$('#searchChannel').attr("no","");
		$('#searchArea').val("");
		$('#searchArea').attr("no","");
		$('#searchModalArea').val("");
		$('#searchModalArea').attr("no","");
		$('#searchModalChannel').val("");
		$('#searchModalChannel').attr("no","");
		$('#modalApType').val("");
		$('#modalApType').attr("no","");
	}else{
		$('#modalDeveloper').val("");
		$('#modalDeveloper').attr("no","");
		$('#modalJiLiDeveloper').val("");
		$('#modalJiLiDeveloper').attr("no","");
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
				"ap_id" : $('#searchApId').val(),
				"ap_name" : $('#searchApName').val(),
				"state" : "0",
				"bind_oper" : $('#searchOperNo').attr('no'),
				"region" : $('#searchArea').attr('no'),
				"chnl_code" : $('#searchChannel').attr('no'),
				"ap_type" : $('#modalApType').attr('no'),
			},
			//触点发展人表
			"ap_developer":{
				"developer_no" : $('#searchDeveloper').attr('no'),
			},
			//触点商品表
			"ap_goods":{
				"goods_id": $('#searchProduct').attr('no'),
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
						apHtml=apHtml+'<tr ap_id="'+item.ap_id+'" region_id="'+item.region_id+'" chnl_code="'+item.chnl_code+'">'
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
							+'<td class="text_center">'
							+'	<div class="btn btn_link" name="contactAddBtn">生成二维码</div>'
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

//获取可选发展人
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

//获取绑定发展人
function getBindDevelopers(selected_ap_id,selected_chnl_code,selected_region_id){
	var json_info = {
			"jsession_id": jsession_id,
			"selected_ap_id" : selected_ap_id,
			"developer_name" : $('#bindingSearchModalDeveloper').val(),
			"selected_chnl_code" : INPUT_UTIL.isNull(selected_chnl_code)?"":selected_chnl_code,
			"selected_region_id" : INPUT_UTIL.isNull(selected_region_id)?"":selected_region_id
		};

	var queryData={
		"jsession_id":jsession_id,
		"json_info":JSON.stringify(json_info)
		};

	$.ajax({
		type: "post",
		url: rest_host+"rest/ap/queryApBulidQrcodeDevelopers",
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
				var bindDeveloperHtml = "";
				if (data.args.developsInfo != null) {
					$('#bindingDeveloperList').empty();
					var apInfo=data.args.developsInfo;
					var developerNms=apInfo.developer_name.split(",");
					var developerNos=apInfo.developer_no.split(",");
					if(developerNos.length<30){
						$('#bindingDeveloperSearchBtn').parent().hide();
					}else{
						$('#bindingDeveloperSearchBtn').parent().show();
					}
					$.each(developerNos, function(i, item) {
						bindDeveloperHtml=bindDeveloperHtml+'<span class="one_third"><a no="'+item+'">'+developerNms[i]+'</a></span>';
					});
				}
				$('#bindingDeveloperList').append(bindDeveloperHtml);
			} else {
				$.message.error('获取触点发展人异常!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取触点发展人Ajax请求失败!');
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

//获取可选渠道
function getSelectChnl(selectRegionId){
	var par_region_id = "";
	if( selectRegionId!=null && selectRegionId != "null"){
		par_region_id = selectRegionId;
	}
	var requestData = {
		jsession_id: jsession_id,
		chnl_name: ($("#searchModalChannel").val() != null ? $("#searchModalChannel").val() : ""),
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
			$('#channelList').empty();
			if (data.respCode=="0000" && !INPUT_UTIL.isNull(data.args.json_info)) {
				var chnl_list = JSON.parse(data.args.json_info).chnl_list;
				var apChnlHtml = '';
				if (chnl_list != null && chnl_list.length>0) {
					$.each(chnl_list, function(i, item) {
						apChnlHtml = apChnlHtml+'<span class="one_third"><a no="'+item.chnl_code+'">'+item.chnl_name+'</a></span>';
					});
				}
				$('#channelList').append(apChnlHtml);
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

//获取可选地区
function getOptionalRegion(){
	var RegionData = {
		jsession_id: jsession_id,
		region_name: ($("#areaSearchModal").val() != null ? $("#areaSearchModal").val() : ""),
		region_id: "",
		parent_region_id: "",
		region_level: ""
	};

	$.ajax({
		type: "post",
		url: rest_host+"rest/optionalOper/getOptionalRegionInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: RegionData,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000" && !INPUT_UTIL.isNull(data.args.json_info)) {
				$('#areaList').empty();
				var regionHtml = '';
				var region_list = JSON.parse(data.args.json_info).region_list;
				if (region_list != null && region_list.length>0) {
					$.each(region_list, function(i, item) {
						regionHtml=regionHtml+'<span class="one_third"><a no="'+item.region_id+'">'+item.region_name+'</a></span>';
					});
				}
				$('#areaList').append(regionHtml);
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

//获取触点类型
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


//获取即时激励发展人
function getJiLiDevelopers(){
	var requestData = {
		jsession_id: jsession_id,
		developer_no: $('#modalDeveloper').attr('no')
	};
	$.ajax({
		type: "post",
		url: rest_host+"rest/optionalOper/getRewardDevelopInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: requestData,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				if(data.args.code=="200"){
					var reward_oper_list=JSON.parse(data.args.json_info).reward_oper_list;
					var jiLiDeveloperHtml = "";
					if (reward_oper_list != null && reward_oper_list.length>0) {
						$('#bindingJiLiDeveloperList').empty();
						$.each(reward_oper_list, function(i, item) {
							jiLiDeveloperHtml=jiLiDeveloperHtml+'<span class="one_third"><a no="'+item.reward_oper_no+'">'+item.reward_oper_name+'/'+item.reward_oper_no+'</a></span>';
						});
					}else{
						$.message.error('没有查到即时激励发展人!');
					}
					$('#bindingJiLiDeveloperList').append(jiLiDeveloperHtml);
					$('#bindingJiLiDeveloperModal').show();
				}else{
					$.message.error('获取即时激励发展人失败!');
				}
			} else {
				$.message.error('获取即时激励发展人异常!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('获取即时激励发展人Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
}
