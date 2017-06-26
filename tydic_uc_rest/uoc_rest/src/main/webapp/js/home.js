var imgDir = ''; // 图片文件夹路径
var operNo = '';

$(document).ready(function () {
    imgDir = $("#img_dir").val();
    operNo = $("#oper_no").val();

    // 查询框
    $('#input_number').bind('keyup', function() {
		$('#device_type').css('visibility', 'hidden');
	});
    
    // 查询按钮
    $('#btn_search').bind('click', function () {
        queryNumber($('#input_number').val());
    });

    bindDeviceEvent();

    queryMenu("1", "", createTopMenu);
    queryMenu("2", "", createMidMenu);
    queryMenu("3", "", createBottonMenu);
});

// 绑定左右按钮事件
function bindDeviceEvent() {
    var devCount = $('.top_option').length; // 设备总个数
    var devCurrent = 1; // 左边第一个设备的序号
    var displayCount = 5; // 页面上显示的设备个数
    // 隐藏第6个以后的设备
    for (var i = displayCount + 1; i <= devCount; i++) {
        $("#top_detail_" + i).hide();
    }
    // 禁用向左按钮
    $('#btn_left').removeClass('arrow_left_active').addClass('arrow_left');
    // 启用/禁用向右按钮
    if (devCount > displayCount) {
        $('#btn_right').removeClass('arrow_right').addClass('arrow_right_active');
    } else {
    	$('#btn_right').removeClass('arrow_right_active').addClass('arrow_right');
    }
    // 点击向左按钮
    $('#btn_left').unbind('click');
    $('#btn_left').click(function () {
        if (devCurrent > 1) {
            devCurrent = devCurrent - 1;
            // 显示/隐藏设备信息
            $('#top_detail_' + (parseInt(devCurrent) + parseInt(displayCount))).hide();
            $('#top_detail_' + devCurrent).show();
            // 启用向右按钮
            if (devCurrent <= devCount - displayCount) {
                $('#btn_right').removeClass('arrow_right').addClass('arrow_right_active');
            }
            // 禁用向左按钮
            if (devCurrent <= 1) {
                $('#btn_left').removeClass('arrow_left_active').addClass('arrow_left');
            }
        }
    });
    // 点击向右按钮
    $("#btn_right").unbind('click');
    $("#btn_right").click(function () {
        if (devCurrent <= devCount - displayCount) {
            devCurrent = devCurrent + 1;
            // 显示/隐藏设备信息
            $('#top_detail_' + (parseInt(devCurrent) - 1)).hide();
            $('#top_detail_' + (parseInt(devCurrent) + parseInt(displayCount) - 1)).show();
            // 启用向左按钮
            if (devCurrent > 1) {
                $('#btn_left').removeClass('arrow_left').addClass('arrow_left_active');
            }
            // 禁用向右按钮
            if (devCurrent > devCount - displayCount) {
                $('#btn_right').removeClass('arrow_right_active').addClass('arrow_right');
            }
        }
    });
}

var queryMenu = function (commonType, appKey, callBack) {
    var URL = application.fullPath + 'rest/nav/menu';
    if (commonType != undefined) {
        URL = URL + "?commonType=" + commonType;
    }
    if (appKey != undefined) {
        URL = URL + "&appKey=" + appKey;
    }

    $.get(URL, function (data) {
        callBack(data)
    });
};

var queryNumber = function (number) {
    var URL = application.fullPath + 'rest/saleOpen/qryDeviceGuishu';

    var postData = {
        jsessionid: 111,
        device_number: number
    };
    $.ajax({
        url: URL,
        dataType: 'json',
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        type: 'post',
        data: postData,
        waitMsg: "读取中..",
        success: function (message) {
            if (message.type == 'error') {
            	if (message.args.message && message.args.message != '') {
            		$.alert(message.args.message);
            	} else {
            		$.alert(message.content);
            	}
            }
            else if (message.type == 'success') {
            	var deviceType = '';
            	if (message.args.device_guishu == 'BSS') { // 2G
            		deviceType = '2G';
            	} else if(message.args.device_guishu == 'ESS') { // 3G
            		deviceType = '3G';
            	} else if(message.args.device_guishu == 'CBSS') { // 4G
            		deviceType = '4G';
            	}
            	if (deviceType != '') {
            		$('#device_type').text(deviceType);
            		$('#device_type').css('visibility', 'visible');
            	}
                // 根据归属查询不同系统
                queryMenu('1', message.args.device_guishu, createTopMenu);
            }
        },
        error: function (message) {
            var dialog = $.dialog({
                title: '提示', // 提示title
                content: '查询业务号码失败', // 显示的内容，支持html格式。
                buttons: [{
                    id: 'btn_ok', text: '确定',
                    onClick: function () {
                        dialog.close();
                    }//点击时候回调函数
                }]
            });
        }

    });
};

/**
 * 生成顶部菜单
 * @param data
 */
var createTopMenu = function (data) {
    $('#dtjzlb').children().remove();
    var i = 1;
    $.map(data, function (menu) {
        var menuImageUrl = application.fullPath + menu.imageDis;
        $('#dtjzlb').append("<div class='top_option' id='top_detail_" + i + "' style='background:url(" + menuImageUrl + ") no-repeat left top;' onmouseover=hover(this,'home_quick_cur'); onmouseout=unhover(this,'"+menuImageUrl+"');>" +
        	"<a href=" + menu.menuUrl + ">" +
            "<div class='top_text'>" + menu.menuName + "</div></a></div>");
        i++;
    });
    bindDeviceEvent();
};

/**
 * 生成快速受理菜单
 * @param data
 */
var createMidMenu = function (data) {
    $('#kssltd').children().remove();
    var i = 1;
    $.map(data, function (menu) {
        var menuImageUrl = application.fullPath + menu.imageDis;
        var hotIconUrl = application.fullPath + "images/home_hot.png";
        var hotImg = "";
        if (menu.isHot == 1) {
        	hotImg = "<img src='" + hotIconUrl + "' width='42' height='26'/>";
        }
		
        $('#kssltd').append("<a href=" + menu.menuUrl + ">" +
        	//"<div class='quick_option' id='mid_detail_" + i + "' style='background:url(" + menuImageUrl + ") no-repeat left top;'>" +
			//2016-6-28 lgw
			"<div class='quick_option' id='mid_detail_" + i + "' style='background:url(" + menuImageUrl + ") no-repeat left top;'  onmouseover=hover(this,'home_quick_cur'); onmouseout=unhover(this,'"+menuImageUrl+"');>" +
			"<div class='hot_icon'>" + hotImg + "</div>" +
			"<div class='quick_text'>" + menu.menuName + "</div></div></a>");
			
        i++;
    });
};

//2016-6-28 lgw
function hover(element,img_name) {
	element.style.cssText = "backgroundimage:url(../images/"+img_name+".png) no-repeat left top;";
}
function unhover(element,src) {
	element.style.cssText = "background:url("+src+") no-repeat left top;";
}


/**
 * 生成常用业务历史
 * @param data
 */
var createBottonMenu = function (data) {
    $('#cywyls').children().remove();
    var i = 1;
    $.map(data, function (menu) {
        var menuImageUrl = application.fullPath + menu.imageDis;
        $('#cywyls').append("<a href=" + menu.menuUrl + "><div class='history_option' id='botton_detail_" + i + "'>" +
            "<div class='history_text'>" + menu.menuName + "</div></div></a>");
        i++;
    })
};

/**
 * ULP打开菜单用
 * 用法onClick="newUlpPannel('9900577','GetInfoDept.do?next_page=INNET.screen','1');"
 * 参数一 当前菜单ID
 * 参数二 需要打开菜单的URL
 * 参数三 所属子系统
 */
function newUlpPannel(menuCode, menuUrl, subSysCode) {
    /*parent是子系系统所在iframe内,parent.parent才是ulp对应菜单所在dom(以上前提是本函数所在地未嵌入子系统自定义iframe)*/
    //alert(window.parent.location.href); http://10.77.45.22:7003/mainwindow/bodyFrameTab.htm
    //alert(window.parent.parent.location.href); //http://10.77.45.22:7003/mainwindow/mainwindow.screen 
    //alert(window.parent.frames.length);
    //alert(window.parent.frames["bottomTabFrame"]);
    /*增加tab的按钮在子系统内*/
    var subSysWindow = getSubSysWindow();
    //subSysWindow.frames["bottomTabFrame"].document.getElementById("newTabButton").click();
    /*菜单模拟点击事件,菜单不在子系统内,在ulp内*/
    var ulpSysWindow = getUlpSysWindow();
    //window.parent.parent.clickLeftItem('lzy_tblMenu_P9906103_td','M165Enter.screen','sys_1','9906103');
    ulpSysWindow.clickLeftItem("lzy_tblMenu_P" + menuCode + "_td", menuUrl, "sys_" + subSysCode, menuCode);
    //"lzy_tblMenu_P9917106_td""http://135.24.252.38:8080/uss_web/authority/index/openAccountPC?menu_id=16""sys_8800118""9917106"
}

function getSubSysWindow() {
    var subSysWindow = window.parent;
    while (true) {//mainwindow/bodyFrameTab.htm或mainwindow/bodyFrameTab.jsp
        if (subSysWindow.location.href.indexOf("mainwindow/bodyFrameTab") > -1) {
            return subSysWindow;
        }
        else {
            subSysWindow = subSysWindow.parent;
        }
    }
}

function getUlpSysWindow() {
    var ulpSysWindow = window.parent.parent;
    while (true) {
        if (ulpSysWindow.location.href.indexOf("mainwindow/mainwindow.screen") > -1) {
            return ulpSysWindow;
        }
        else {
            ulpSysWindow = ulpSysWindow.parent;
        }
    }
}

