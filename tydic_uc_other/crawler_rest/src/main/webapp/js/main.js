var operInfo = {
	rest_host: '',
	jsession_id: '',
	oper_no: '',
	oper_name: '',
	province_code: '',
	area_code: '',
	depart_no: '',
	depart_name: '',
	channel_id: '',
	channel_type: '',
	district: '',
	role_id: '',
	infoMenuList: []
};
//配置信息
var Config = {
	CARD_READER_FLAG:'',
	GZT_FLAG:'',
	TASK_TIME_RATIO:'',
	TAB_COUNT_LIMIT:'',
	CAS_LOGOUT:'',
	SF_FLAG:'',
	SMS_CODE:'',
	DEVICE_ADR:''
};
//监听的后台小心推送频道
var push_msg_channel = "/pushMsg/uoc";

$(document).ready(function() {
	operInfo.jsession_id = $('#jsession_id').val();
	operInfo.rest_host = $('#rest_host').val();

	Config.CARD_READER_FLAG = $('#card_reader_flag').val();
	Config.GZT_FLAG = $('#gzt_flag').val();
	Config.TASK_TIME_RATIO = $('#task_time_ratio').val();
	Config.TAB_COUNT_LIMIT = $('#tab_count_limit').val();
	Config.CAS_LOGOUT = $('#cas_logout').val();
	Config.SF_FLAG = $('#sf_flag').val();
	Config.SMS_CODE = $('#sms_code').val();
	Config.DEVICE_ADR = $('#device_adr').val();

	// 获取当前工号信息
	getOperInfo();

	// （签入）加入任务组按钮
	$('#taskJoinBtn').on('click', function(e) {
		$.dialog.confirm(
			"是否加入任务组？",
		    "提示",
		    "确认",
		    "取消",
		    function() {
				taskJoinOrExitRequestMethod(1,null);
		    }
		);
	});

	// （签出）退出任务组按钮（不支持解锁）
	$('#taskExitBtn').on('click', function(e) {
		$.dialog.alert(
			"是否解锁所有个人任务？",
			"提示",
			"不解锁",
			function() {
				$.dialog.confirm(
	    			"是否确认签出，不解锁所有个人任务？",
	    		    "提示",
	    		    "确认",
	    		    "取消",
	    		    function() {
	    				taskJoinOrExitRequestMethod(2,0);
	    		    }
	    		);
			});
	});

	// （签出）退出任务组按钮（支持解锁）
	/*
	$('#taskExitBtn').on('click', function(e) {
		$.dialog.confirm(
			"是否解锁所有个人任务？",
		    "提示",
		    "解锁",
		    "不解锁",
		    function() {
				$.dialog.confirm(
					"是否确认签出，并解锁所有个人任务？",
				    "提示",
				    "确认",
				    "取消",
				    function() {
						taskJoinOrExitRequestMethod(2,1);
				    }
				);
		    },
		    function() {
		    	$.dialog.confirm(
	    			"是否确认签出，不解锁所有个人任务？",
	    		    "提示",
	    		    "确认",
	    		    "取消",
	    		    function() {
	    				taskJoinOrExitRequestMethod(2,0);
	    		    }
	    		);
		    }
		);
	});
	*/

	// 退出登录
	$('#logoutBtn').on('click', function(e) {
		logoutBtn();
//		$.message.info('退出登录，功能未完成...');
	});

	// 点击一级菜单
	$('#menuList').on('click', '.menu_level1>li', function(e) {
		var target = $(e.target);
		if (target.closest('.menu_level2').length > 0) {
			return; // 是否点击的二级菜单
		}

		var $this = $(this);
		if (overTabLimit($this.attr('label'))) {
			return; // 检查页签数量
		}

		$this.siblings().find('li').removeClass('active');
		$this.siblings().removeClass('active');
		$this.addClass('active');

		// 打开iframe页面
		openMenu({
			menu_id: $this.attr('menu_id'),
			label: $this.attr('label'),
			url: $this.attr('url')
		});
	});
	// 点击二级菜单
	$('#menuList').on('click', '.menu_level2>li', function(e) {
		var $this = $(this);
		if($this.attr('label')=="缓存刷新"){
			//如果菜单是缓存刷新就调rest接口刷新，不做其他操作
			refreshRedisAll();
			return;
		}
		if (overTabLimit($this.attr('label'))) {
			return; // 检查页签数量
		}

		var liLevel1 = $this.parents('.menu_level1>li');
		liLevel1.siblings().find('li').removeClass('active');
		liLevel1.siblings().removeClass('active');
		liLevel1.addClass('active');
		$this.siblings().removeClass('active');
		$this.addClass('active');

		// 打开iframe页面
		openMenu({
			menu_id: $this.attr('menu_id'),
			label: $this.attr('label'),
			url: $this.attr('url')
		});
	});

	// 显示二级菜单
	$('#menuList').on('mouseenter', '.menu_level1>li', function(e) {
		var $this = $(this);
		var ml2 = $this.find('.menu_level2');
		if (ml2.length > 0) { // 重定位二级菜单的位置
			var ml1LiOffsetTop = parseInt($this.attr('offset_top')); // 一级菜单的offsetTop偏移数值
			var ml1ScrollTop = parseInt($this.parent().scrollTop()); // 整个菜单的scrollTop滚动数值
			var ml2MarginTop = parseInt(ml2.css('margin-top').substring(0, ml2.css('margin-top').indexOf('px'))); // 二级菜单的maring-top数值
			ml2.css('margin-top', ml2MarginTop - (ml1ScrollTop - ml1LiOffsetTop));
			$this.attr('offset_top', ml1ScrollTop);
		}
		$this.find('.menu_level2').removeClass('hide');
	});
	// 隐藏二级菜单
	$('#menuList').on('mouseleave', '.menu_level1>li', function(e) {
		var $this = $(this);
		$this.find('.menu_level2').addClass('hide');
	});

	// 页签栏点击标签
	$('#tabList').on('click', 'ul>li', function(e) {
		var target = $(e.target);
		if (target.closest('b').length > 0) {
			return; // 是否点击的关闭按钮
		}

		var $this = $(this);
		if ($this.hasClass('active')) {
			return; // 是否点击的是.active的页签
		}

		// 切换页签
		$this.siblings().removeClass('active');
		$this.addClass('active');

		// 切换iframe页面
		$('#pageFrame iframe').hide();
		$('#pageFrame iframe[label="' + $this.attr('label') + '"]').show();
	});

	// 页签栏点击关闭按钮
	$('#tabList').on('click', 'ul>li>b', function(e) {
		var $this = $(this);
		var parent = $this.parent();
		if (parent.hasClass('active')) {
			parent.prev().click();
		}
		// 销毁页签
		parent.remove();
		// 销毁iframe页面
		$('#pageFrame iframe[label="' + parent.attr('label') + '"]').remove();
	});

	// 重置iframe的高度（IE下iframe高度100%无效）
	if ($UTIL.browser.isIE) {
		var iframeHeight = $('#pageFrame').css('height');
		$('#pageFrame').css('height', iframeHeight);
	}

});

//获取推送信息(pushlet回调函数)
function onData(event) {
    var data=event.get(operInfo.oper_no);
    if( typeof(data) == undefined || data== null){
//    	var msgContentNull = "您有1条未处理的人工审核任务！！您有1条未处理的人工审核任务！！您有1条未处理的人工审核任务！！";
//    	showPushMsg(msgContentNull);
    }
    else{
    	var dataJson = JSON.parse(data);
    	var msgContent = "";
    	if(dataJson.S00020 != null && dataJson.S00020 !=""){
    		msgContent = msgContent + "您有" + dataJson.S00020 + "条未处理的人工审核任务！！";
    	}

    	// frame10001的10001是菜单的menu_id，指任务台task.jsp页面，从数据库取得
		if (window.frames['frame10001']) {
			window.frames['frame10001'].window.refreshTaskListAfterGetPushMsg();
		}
		if (window.frames['frame10006']) {
			window.frames['frame10006'].window.refreshTaskListAfterGetPushMsg();
		}
		if (window.frames['frame10007']) {
			window.frames['frame10007'].window.refreshTaskListAfterGetPushMsg();
		}
		if (window.frames['frame10008']) {
			window.frames['frame10008'].window.refreshTaskListAfterGetPushMsg();
		}
		if (window.frames['frame10009']) {
			window.frames['frame10009'].window.refreshTaskListAfterGetPushMsg();
		}
		if (window.frames['frame10019']) {
			window.frames['frame10019'].window.refreshTaskListAfterGetPushMsg();
		}
		if (window.frames['frame10022']) {
			window.frames['frame10022'].window.refreshTaskListAfterGetPushMsg();
		}
		if (window.frames['frame10024']) {
			window.frames['frame10024'].window.refreshTaskListAfterGetPushMsg();
		}

    	showPushMsg(msgContent);
    }
};
//弹出后台消息框
function showPushMsg(msgContent){
	if(window.Notification){
		if (Notification.permission == "granted") {
	        popNotice(msgContent);
	    } else if (Notification.permission != "denied") {
	        Notification.requestPermission(function (permission) {
	          popNotice(msgContent);
	        });
	    }
	}
	else{
		popup.show(msgContent);
	}
};

//IE消息弹窗
var popup = {
		isPopupSupported: 'createPopup' in window,
		show: function(msgContent) {
			if (!this.isPopupSupported) {
				alert('当前浏览器不支持Popup API');
				return;
			}

			var pop = window.createPopup();
			pop.document.body.innerHTML = '<div class="box" style="width:100%;height:100%;">'+
					'<div style="float:left;width:20%;height:100%;padding:10px"><img src="style/images/icon/logo.png" width="100%" height="100%"></div>'+
					'<div style="width:100%;height:50%;font-size:16px;">系统消息</div>'+
					'<div style="width:100%;height:50%;font-size:12px;color:#6d6d6d">'+msgContent+'</div></div>';
			pop.document.body.style.background = "#eee";
			pop.document.body.style.border = "3px solid #999";
			pop.document.body.style.padding = "10px";
			pop.show(window.screen.width - 290, window.screen.height - 120, 290, 80);
		}
	};

//非IE消息弹窗
function popNotice(msgContent) {
    if (Notification.permission == "granted") {
        var notification = new Notification("系统消息", {
        	tag:' ',
            body: msgContent,
            renotify:true,//表示覆盖消息，而不是一个一个往上叠加,需和tag合并使用
            icon: 'style/images/icon/logo.png'
        });
		notification.close();
    }
};

//退出登录
function logoutBtn(){
	var data = {
		jsession_id:operInfo.jsession_id
	};
	$.ajax({
		type: 'post',
		url: operInfo.rest_host + 'rest/systemServiceRest/loginOut',
		contentType: 'application/x-www-form-urlencoded; charset=utf-8',
		async: true,
		data: data,
		dataType: 'json',
		crossDomain: true == !(document.all),
		success: function(data) {
			window.location.href= Config.CAS_LOGOUT;
		},
		error: function(data) {
			$.message.error('退出失败');
		}
	});
}

/**
 * （签入）加入任务组方法  或
 * （签出）退出任务组方法
 */
function taskJoinOrExitRequestMethod(oper_type,quit_type){
	var showMessage ="网络连接异常";
	if(oper_type != 1 && oper_type !=2 ){
		$.message.info("未知的操作");
		return;
	}
	if(oper_type == 1){
		showMessage="签入";
	}
	if(oper_type == 2){
		showMessage="签出";
	}
	var joinOrExitData = {
		jsession_id: operInfo.jsession_id,
		oper_type: oper_type,
		quit_type: quit_type
	};
	$.ajax({
		type: "post",
		url: operInfo.rest_host+"rest/ordModServiceRest/joinOrExitDepartTaskForce",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: joinOrExitData,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在执行");
		},
		success: function(data) {

			if (data.respCode=="0000") {

				if( oper_type == 1 ){
					$("#taskJoinBtnDiv").hide();
					$("#taskJoiningDiv").show();
					$("#taskExitBtnDiv").show();
				}
				if( oper_type == 2 ){
					$("#taskJoinBtnDiv").show();
					$("#taskJoiningDiv").hide();
					$("#taskExitBtnDiv").hide();
				}
				$.message.info(showMessage+'成功!');
			} else {
				$.message.error(showMessage+'失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error(showMessage+'Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
}

//获取当前工号信息
function getOperInfo(){
	if(operInfo.jsession_id == null || operInfo.jsession_id == ""){
		$.message.info("jsession_id不能为空");
		return;
	}
	var data = {
		jsession_id : operInfo.jsession_id
	};
	$.ajax({
		type: 'post',
		url: operInfo.rest_host + 'rest/checkInfoRest/getOperInfo',//待访问地址
		contentType: 'application/x-www-form-urlencoded; charset=utf-8',
		async: true,
		data: data,
		dataType: 'json',
		crossDomain: true == !(document.all),
		success: function(data) {
			if (data.respCode == '0000') {
				var args = data.args;
				if (args.oper_info && args.oper_info != null) {
					operInfo.oper_no = args.oper_info.oper_no;
					operInfo.oper_name = args.oper_info.oper_name;
					operInfo.province_code = args.oper_info.province_code;
					operInfo.area_code = args.oper_info.area_code;
					operInfo.depart_no = args.oper_info.depart_no;
					operInfo.depart_name = args.oper_info.depart_name;
					operInfo.channel_id = args.oper_info.channel_id;
					operInfo.channel_type = args.oper_info.channel_type;
					operInfo.district = args.oper_info.district;
					operInfo.role_id = args.oper_info.role_id;
					$('#operInfo').text(operInfo.depart_name+' '+operInfo.oper_name);
					//注册后台消息推送事件
					PL.userId = operInfo.oper_no;
					PL.joinListen(push_msg_channel);
				} else {
					$.message.info('当前工号信息为空');
				}

				if (args.infoMenus && args.infoMenus != null) {
					operInfo.infoMenuList = args.infoMenus;
					loadMenuList(); // 加载菜单
				} else {
					$.message.info('菜单列表信息为空');
				}

				if(args.operJoinState=="1"){
					$("#taskJoinBtnDiv").show();
				}else if(args.operJoinState=="0"){
					$("#taskJoiningDiv").show();
					$("#taskExitBtnDiv").show();
				}else if(INPUT_UTIL.isNull(args.operJoinState)){
					$.message.info('查询签入签出状态异常');
				}
			} else {
				$.message.error('获取当前工号信息失败:' + data.content);
			}
		},
		error: function(data) {
			$.message.error('获取当前工号信息失败');
		}
	});

}

// 加载菜单
function loadMenuList() {
	$('#menuList>ul').remove();
	$('#menuList').append('<ul class="menu_level1"></ul>');
	$.each(operInfo.infoMenuList, function(i, item) {
		// menu_url参数设置
		var menuUrl = item.menu_url;
		if (menuUrl.indexOf('http') > -1 || menuUrl.indexOf('https') > -1) {
			if (menuUrl.indexOf('?') > -1) {
				menuUrl += '&jsession_id=' + operInfo.jsession_id + '&oper_no='+operInfo.oper_no;
			} else {
				menuUrl += '?jsession_id=' + operInfo.jsession_id + '&oper_no='+operInfo.oper_no;
			}
		}
		// 菜单
		if (item.up_menu_id == '0') { // 一级菜单
			var menu1 = $('<li label="' + item.menu_name + '" url="' + menuUrl + '" menu_id="' + item.menu_id + '" offset_top="0">'
						+ '<i class="' + item.menu_comment + '"></i>'
						+ '<span>' + item.menu_name + '</span>'
						+ '</li>');
			$('#menuList>ul').append(menu1);
		} else { // 二级菜单
			var menu1 = $('#menuList>ul>li[menu_id="' + item.up_menu_id + '"]');
			if (menu1.length > 0) { // 如果有对应的一级菜单
				if (menu1.find('ul.menu_level2').length == 0) {
					menu1.append('<ul class="menu_level2 hide"></ul>');
				}
				var menu2 = $('<li label="' + item.menu_name + '" url="' + menuUrl + '" menu_id="' + item.menu_id + '">'
							+ '<span>' + item.menu_name + '</span>'
							+ '</li>');
				menu1.find('ul.menu_level2').append(menu2);
			}
		}
	});
}

// 是否超过页签数量上限
function overTabLimit(label) {
	if ($('#tabList ul li').length >= Config.TAB_COUNT_LIMIT && $('#tabList ul li[label="' + label + '"]').length == 0) {
		$.message.info('已超过最大页签数：' + Config.TAB_COUNT_LIMIT);
		return true;
	}
	return false;
}

/*
 * 打开菜单对应的iframe页面
 * data: {
 * 	menu_id: [string] 菜单ID
 * 	label: [string] 页签名称
 * 	url: [string] iframe加载的页面路径
 * }
 * refresh: [bool] 是否刷新iframe页面
 */
function openMenu(data, refresh) {
	if (!data.url || data.url == null || data.url == '') {
		return;
	}
	var existTab = $('#tabList ul li[label="' + data.label + '"]');
	if (existTab.length > 0) { // 页面已打开
		existTab.click();
	} else { // 页面未打开
		// 添加标签
		var newTab = $('<li menu_id="' + data.menu_id + '" label="' + data.label + '"><span>' + data.label + '</span><b>X</b></li>');
		$('#tabList ul').append(newTab);
		// 加载页面
		var iframe = '<iframe id="frame' + data.menu_id + '" name="frame' + data.menu_id + '" label="' + data.label + '" src="' + data.url + '" scrolling="yes" frameborder="0" style="width:100%; height:100%;"></iframe>';
		$('#pageFrame').append(iframe);
		newTab.click();
	}
	if (refresh) {
		$('#pageFrame iframe[label="' + data.label + '"]').attr('src', data.url);
	}
}

/**
 * 缓存刷新
 */
function refreshRedisAll(){
	$.ajax({
		type: "post",
		url: operInfo.rest_host+"rest/systemServiceRest/refreshRedisAll",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: "",
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在刷新");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				$.message.info('缓存刷新成功!');
			} else {
				$.message.error('缓存刷新失败!'+data.content);
			}
		},
		error: function(data) {
			$.message.error('缓存刷新Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
}
