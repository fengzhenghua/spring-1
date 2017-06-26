var imgDir = ''; // 图片文件夹路径
var operNo = '';

$(document).ready(function () {
    imgDir = $("#img_dir").val();
    operNo = $("#oper_no").val();

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
    devCount = $('.top_option').length; // 功能总数
    devCurrent = 1; // 左边第一个功能的序号
    // 隐藏第6个以后的功能
    for (var i = 6; i <= devCount; i++) {
        $("#top_detail_" + i).hide();
    }
    // 禁用向左按钮
    $('#btn_left').removeClass('arrow_left_active').addClass('arrow_left');
    // 启用向右按钮
    if (devCount > 2) {
        $('#btn_right').removeClass('arrow_right').addClass('arrow_right_active');
    }
    // 点击向左按钮
    $('#btn_left').click(function () {
        if (devCurrent > 1) {
            devCurrent = devCurrent - 1;
            // 显示/隐藏设备信息
            $('#top_detail_' + (parseInt(devCurrent) + 5)).hide();
            $('#top_detail_' + devCurrent).show();
            // 启用向右按钮
            if (devCurrent <= devCount - 5) {
                $('#btn_right').removeClass('arrow_right').addClass('arrow_right_active');
            }
            // 禁用向左按钮
            if (devCurrent <= 1) {
                $('#btn_left').removeClass('arrow_left_active').addClass('arrow_left');
            }
        }
    });
    // 点击向右按钮
    $("#btn_right").click(function () {
        if (devCurrent < devCount - 4) {
            devCurrent = devCurrent + 1;
            // 显示/隐藏设备信息
            $('#top_detail_' + (parseInt(devCurrent) - 1)).hide();
            $('#top_detail_' + (parseInt(devCurrent) + 4)).show();
            // 启用向左按钮
            if (devCurrent >= 2) {
                $('#btn_left').removeClass('arrow_left').addClass('arrow_left_active');
            }
            // 禁用向右按钮
            if (devCurrent >= devCount - 4) {
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
    var URL = application.fullPath + 'rest/sale/checkCustNew';

    var postData = {
        oper_no: $('#oper_no').val(),
        mobile_no: number
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
                alert(message.content);
            }
            else if (message.type == 'success') {
                var sysType = message.args.cust_acc_info.sysType;
                var flag3G = message.args.cust_acc_info.userInfo.flag3G;

                var guishu = "BSS";
                if (sysType == '2'){
                    guishu = "CBSS";
                } else if (sysType == '1' && flag3G == '1'){
                    guishu = "ESS";
                } else if (sysType == '1' && flag3G == '2'){
                    guishu = "BSS";
                }
                queryMenu("1", guishu, createTopMenu);
            }
        },
        error: function (message) {
            var dialog = $.dialog({
                title: '提示',//提示title
                content: '无法查询到号码归属系统',//显示的内容，支持html格式。
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
        $('#dtjzlb').append("<div class='top_option' id='top_detail_" + i + "' onclick=" + menu.menuUrl + " style='background:url(" + menuImageUrl + ") no-repeat left top;' onmouseover=hover(this,'home_quick_cur'); onmouseout=unhover(this,'"+menuImageUrl+"');>" +
            "<div class='top_text'>" + menu.menuName + "</div></div>");
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
    var subSysWindow = getSubSysWindow();
    var ulpSysWindow = getUlpSysWindow();
    ulpSysWindow.clickLeftItem("lzy_tblMenu_P" + menuCode + "_td", menuUrl, "sys_" + subSysCode, menuCode);
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

