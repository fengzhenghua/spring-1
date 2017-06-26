$(document).ready(function(){
	FastClick.attach(document.body);
	$(".main-page,.pop-page").css("min-height",$(window).height()+1);
	$("input,textarea").on("focus",function(){
		$(".bottom").hide();
	});
	$("input,textarea").on("blur",function(){
		$(".bottom").show();
	});
});



var URLS = {
    getHostUrl:function(){
    	var prefix;
    	$.ajaxSettings.async = false;
    	$.getJSON('../../../../js/common/URL.json', function(data){
			prefix = data.HTTP+data.HOST_CQ+data.URL_SPLITTER+data.PROJECT_NAME+data.URL_SPLITTER;
		});
		return prefix;
	},
    getMenuUrl:function(){
    	var menuUrl;
    	$.ajaxSettings.async = false;
    	$.getJSON('../../../../js/common/URL.json', function(data){
			menuUrl =  data.HOST_MENU;
		});
		return menuUrl;
	}
};


/**
 * author:chenliangyu V2.0
 */
var $u = {
		
   webutil : {
		
	/**
	 * otherParam默认值配置
	 */	
	defaultOtherParam:{ignoreDefaultErrorHandler:false},	
    /**
     * 发送GET请求
     */
    doGet: function (url, data, async, registClient, otherParam) {
        this.checkParam("GET", url, data, async, registClient, otherParam);
    },
    /**
     * 发送POST请求
     */
    doPost: function (url, data, async, registClient, otherParam) {
        this.checkParam("POST", url, data, async, registClient, otherParam);
    },

    /**
     * 检查参数
     */
    checkParam: function (type, url, data, async, registClient, otherParam) {
        if (!url) {
            return;
        }
        if (!data) {
            return;
        }
        if ((!registClient) || (!registClient.onComplete) || (!registClient.onError) || (!registClient.onException)) {
            return;
        }
        //处理otherParam
       otherParam = $.extend({},this.defaultOtherParam,otherParam||{});
       this.sendUrl(type, url, data, async, registClient, otherParam);
    },
    
   
    /**
     * url:发送到服务器的url
     * type:发送方式是POST还是GET
     * data:传递到服务器的数据json格式,如果不需要传参传入{}
     * async:true异步
     * registClient:对象必须包含onComplete(args) API调用成功后返回值以json ,
     *                           onError(error)  出现业务错误时通知监听器错误码及字错误码等信息
     *                           onException(data) 出现网络问题等未知异常时会回调此方法,
     *                           onFinally()不管成功还是失败都会回调此方法
     *otherParam中ajaxParam ajaxParam额外扩充ajax参数
     */
    sendUrl: function (type, url, data, async, registClient, otherParam) {
    	var prefix = URLS.getHostUrl();
        console.info("请求方式为:" + type + ",请求接口的url为:" + prefix + url);
        console.info("请求报文--入参--如下");
        console.info(data);
        var jsonStr=JSON.stringify(data);
		var key = '12345678';  
		jsonStr=encryptByDES(jsonStr, key);  
		var dataEncrypt={"encrypt":jsonStr};
		console.info("请求报文--加密串--如下");
        console.info(dataEncrypt);
        var that = this;
        var ajaxParam = {
            url: prefix+url,
            async: async,
            data: dataEncrypt,
            timeout: 20000,//超时设置一分钟
            type: type,
            dataType: "json",
            beforeSend: function () {
                //开启等待窗口
            },
            complete: function () {
                //
                // 关闭等待窗口
                if (registClient.onFinally) {
                    registClient.onFinally();
                }
            },
            success: function (data, textStatus, jqXHR) {
                console.info("请求接口的url为:" + url);
                console.info("请求报文--出参--如下");
                console.info(data);
                var error = that.dealErrorInfo(data);
                var args = that.getArgs(data);
                if (error != null) {
                    registClient.onError(error,args);
                    //如果配置了不调用默认错误处理器
                    if (!otherParam.ignoreDefaultErrorHandler) {
                        that.hintErrorInfo(error, args);
                    }
                } else {
                    registClient.onComplete(args);
                }
            },
            /**
             * 400系列码 400范围的状态码是客户端错误码
             * 500系列码 500级状态码表示的是服务器错误
             */
            error: function (XMLHttpRequest, textStatus) {
                var status = XMLHttpRequest.status;
                var errorInfo = $u._http_error_statues[status];
                var hint = "网络繁忙，请稍后再试!";//通用万金油提示，都懂得。
                var error = errorInfo;
                if (!error) {
                    error = hint + ",后台接口报错!";
                }
                $u.dialog_util.showTypeDialog("error", error);
                registClient.onException(status, errorInfo, hint);
            }
        };
        if (otherParam) {
            if (otherParam.ajaxParam) {
                $.extend(ajaxParam, otherParam.ajaxParam);
            }
        }
        $.ajax(ajaxParam);
    },

    /**
     * 判断是否有错误数据
     * @param data
     * @returns
     */
    dealErrorInfo: function (data) {
        if (data) {
            var type = data.type;
            var content = data.content;
            var args = data.args;
            console.info(args);
            if ("success" == type) {
                //目前接口可能返回的异常数据args
                if (args == "null") {
                    type = "intfdatanull";
                    content = "接口数据返回异常,接口返回数据的args为" + args == "" ? "\"\"" : args;
                } else {
                    return null;
                }
            }
            var error = {};
            error.type = type;
            error.content = content;
            return error;
        }
    },
    /**
     * 获取args的json数据
     * @param data
     * @returns
     */
    getArgs: function (data) {
        var args = data.args;
        if (args) {
            return args;
        }
        return data;
    },
    hintErrorInfo: function (error, args) {
        var type = error.type;
        var content = error.content;
        var resp_code = args.resp_code;
        var err_content = args.content;
        if (resp_code == "rest.need.reLogin") {//需要登陆
        	  $u.dialog_util.showTypeDialog("rest.need.reLogin");
        } else if (resp_code == "dele.memchached.error") {

        } else if (resp_code == "memchached.error") {

        } else {
    		if(err_content==undefined){
    			$u.dialog_util.showTypeDialog("error", content);
    		}else{
        		$u.dialog_util.showTypeDialog("error", content+"["+err_content+"]");
        	}
        }
    }
},

_http_error_statues :{
    "0": "您当前网络状况不好,请稍后再试!",
    "400": "程序请求出现语法错误。",
    "401": "程序试图未经授权访问受密码保护的页面。应答中会包含一个WWW-Authenticate头，浏览器据此显示用户名字/密码对话框，然后在填 写合适的Authorization头后再次发出请求。",
    "403": "资源不可用。服务器理解程序的请求，但拒绝处理它。通常由于服务器上文件或目录的权限设置导致。",
    "404": "无法找到指定位置的资源。",
    "405": "请求方法（GET、POST、HEAD、DELETE、PUT、TRACE等）对指定的资源不适用。（HTTP 1.1新）",
    "406": "指定的资源已经找到，但它的MIME类型和程序在Accpet头中所指定的不兼容（HTTP 1.1新）。",
    "407": "类似于401，表示程序必须先经过代理服务器的授权。（HTTP 1.1新）",
    "408": "在服务器许可的等待时间内，程序一直没有发出任何请求。程序可以在以后重复同一请求。（HTTP 1.1新）",
    "409": "请求和资源的当前状态相冲突，请求不能成功。（HTTP 1.1新）",
    "410": "所请求的文档已经不再可用，而且服务器不知道应该重定向到哪一个地址。它和404的不同在于，返回407表示文档永久地离开了指定的位置，而 404表示由于未知的原因文档不可用。（HTTP 1.1新）",
    "411": "服务器不能处理请求，除非程序发送一个Content-Length头。（HTTP 1.1新）",
    "412": "请求头中指定的一些前提条件失败（HTTP 1.1新）。",
    "413": "目标文档的大小超过服务器当前愿意处理的大小。如果服务器认为自己能够稍后再处理该请求，则应该提供一个Retry-After头（HTTP 1.1新）。",
    "414": "请求过长（HTTP 1.1新）。",
    "416": "服务器不能满足程序在请求中指定的Range头。（HTTP 1.1新）",
    "500": "服务器遇到了意料不到的情况，不能完成程序的请求。",
    "501": "服务器不支持实现请求所需要的功能。例如，程序发出了一个服务器不支持的PUT请求。",
    "502": "服务器作为网关或者代理时，为了完成请求访问下一个服务器，但该服务器返回了非法的应答。",
    "503": "服务器由于维护或者负载过重未能应答。例如，Servlet可能在数据库连接池已满的情况下返回503。服务器返回503时可以提供一个 Retry-After头。",
    "504": "由作为代理或网关的服务器使用，表示不能及时地从远程服务器获得应答。（HTTP 1.1新）",
    "505": "服务器不支持请求中所指明的HTTP版本。（HTTP 1.1新）"
},

html_util : {
    requestParams: null,
    /**
     * 获取html后面字符串所传参数的
     * @param key
     * @returns
     */
    getParam: function (key) {
        if (this.requestParams == null) {
            this.requestParams = this.getRequestParam();
        }

        if ("all" == key) {
            return this.requestParams;
        } else {
            return this.requestParams[key];
        }
    },
    /**
     * 直接获取字符串
     */
    getParamsStr: function () {
        var url = window.location.search;
        if (url.indexOf("?") != -1) {
            return url.substr(1);
        } else {
            return "";
        }
    },
    getParamsDataStr: function (data) {
        var requestParam = this.getRequestParam();
        $.extend(requestParam, data);
        return $.param(requestParam);
    },
    //去除一些不需的后续参数
    getParamsDataDelStr: function (data, dels) {
        var requestParam = this.getRequestParam();
        $.extend(requestParam, data);
        for (var i = 0; i < dels.length; i++) {
            delete requestParam[dels[i]];
        }
        return $.param(requestParam);
    },
    getRequestParam: function () {
        var url = decodeURI(window.location.search); //获取

        var theRequest = new Object();
        if (url.indexOf("?") != -1) {
            var str = url.substr(1);
            strs = str.split("&");
            for (var i = 0; i < strs.length; i++) {
                var args = strs[i].split("=");
                theRequest[args[0]] = args[1];
            }
        }
        return theRequest;
    },
    
    /**
     * 停止冒泡
     * 用于父子元素上均存在事件时，不希望触发子元素事件时同时触发父元素事件
     * @param {Object} e
     */
	stopBubble:function(e){
		// 如果提供了事件对象，则这是一个非IE浏览器
		if (e && e.stopPropagation) {
			// 因此它支持W3C的stopPropagation()方法 
			e.stopPropagation();
		} else {
			// 否则，我们需要使用IE的方式来取消事件冒泡
			window.event.cancelBubble = true;
		}
	}
},

input_util : {
    /**判断是否为空
     * 为空返回true
     * 不为空返回false
     */
    isNull: function (str) {
        if (str == "") return true;
        var regu = "^[ ]+$";
        var re = new RegExp(regu);
        return re.test(str);
    },
    /**
     *用途：检查输入字符串是否符合正整数格式
     */
    isNumber: function (s) {
        var regu = "^[0-9]+$";
        var re = new RegExp(regu);
        if (s.search(re) != -1) {
            return true;
        } else {
            return false;
        }
    },
    /**
     * 去除左右两边空格
     */
    trim: function (s) {
        return s.replace(/(^s*)|(s*$)/g, "");
    },
    /**
     * 自动替换input输入只允许输入整数和浮点数
     * @param oInput
     */
    checkInputFloat: function (oInput) {
        if ('' != oInput.value.replace(/\d{1,}\.{0,1}\d{0,}/, '')) {
            oInput.value = oInput.value.match(/\d{1,}\.{0,1}\d{0,}/) == null ? '' : oInput.value.match(/\d{1,}\.{0,1}\d{0,}/);
        }
    },
    /**
     * 自动替换input输入只允许输入整数
     * @param oInput
     */
    checkInputInt: function (oInput) {
        oInput.value = oInput.value.replace(/\D/g, '');
    },
    /**
     * 自动替换input输入只允许数字和字母
     * @param oInput
     */
    checkInputIntChar: function (oInput) {
        oInput.value = oInput.value.replace(/[^\w]/ig, '');
    },
    /**
     * 自动替换input值,只允许输入两位小数,大多数用于金钱验证
     */
    checkInputMoney: function (oInput) {
        oInput.value = oInput.value.replace(/[^\d.]/g, "");  //清除“数字”和“.”以外的字符
        oInput.value = oInput.value.replace(/^\./g, "");  //验证第一个字符是数字而不是.
        oInput.value = oInput.value.replace(/\.{2,}/g, "."); //只保留第一个. 清除多余的
        oInput.value = oInput.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
        oInput.value = oInput.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');//只能输入两个小数
    },
    /**
     * 文本框根据输入内容自适应高度
     * @param                {HTMLElement}        输入框元素,必选
     * @param                {Number}                设置光标与输入框保持的距离(默认0),可选
     * @param                {Number}                设置最大高度(可选),可选
     */
    autoTextarea : function (elemId, extra, maxHeight) {
    		var elem = document.getElementById(elemId);
            extra = extra || 0;
            var isFirefox = !!document.getBoxObjectFor || 'mozInnerScreenX' in window,
            isOpera = !!window.opera && !!window.opera.toString().indexOf('Opera'),
                    addEvent = function (type, callback) {
                            elem.addEventListener ?
                                    elem.addEventListener(type, callback, false) :
                                    elem.attachEvent('on' + type, callback);
                    },
                    getStyle = elem.currentStyle ? function (name) {
                            var val = elem.currentStyle[name];
     
                            if (name === 'height' && val.search(/px/i) !== 1) {
                                    var rect = elem.getBoundingClientRect();
                                    return rect.bottom - rect.top -
                                            parseFloat(getStyle('paddingTop')) -
                                            parseFloat(getStyle('paddingBottom')) + 'px';        
                            };
     
                            return val;
                    } : function (name) {
                                    return getComputedStyle(elem, null)[name];
                    },
                    minHeight = parseFloat(getStyle('height'));
     
            elem.style.resize = 'none';
     
            var change = function () {
                    var scrollTop, height,
                            padding = 0,
                            style = elem.style;
     
                    if (elem._length === elem.value.length) return;
                    elem._length = elem.value.length;
     
                    if (!isFirefox && !isOpera) {
                            padding = parseInt(getStyle('paddingTop')) + parseInt(getStyle('paddingBottom'));
                    };
                    scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
     
                    elem.style.height = minHeight + 'px';
                    if (elem.scrollHeight > minHeight) {
                            if (maxHeight && elem.scrollHeight > maxHeight) {
                                    height = maxHeight - padding;
                                    style.overflowY = 'auto';
                            } else {
                                    height = elem.scrollHeight - padding;
                                    style.overflowY = 'hidden';
                            };
                            style.height = height + extra + 'px';
                            scrollTop += parseInt(style.height) - elem.currHeight;
                            document.body.scrollTop = scrollTop;
                            document.documentElement.scrollTop = scrollTop;
                            elem.currHeight = parseInt(style.height);
                    };
            };
     
            addEvent('propertychange', change);
            addEvent('input', change);
            addEvent('focus', change);
            change();
    }
},

//加载数据几种情况1.页面加载 2.触发事件

/**
 * 公共接口文档
 * 以前接口试common_rest
 */
common_rest_intf :{
    /**
     * code_type:接口需要入参，必须传入
     * parent_code_id:接口需要入参,非必须参数，如果不需要传入请传入""空字符串即可
     * async:true:异步 ,false 同步
     * registClient:对象必须包含onComplete(args) API调用成功后返回值以json ,
     *                            onError(error)  出现业务错误时通知监听器错误码及字错误码等信息
     *                            onException(data) 出现网络问题等未知异常时会回调此方法
     */
    getCodeList: function (code_type, parent_code_id, async, registClient) {
//      var url = URLS.getCodeListUrl();
		var url = "rest/codeType/codeList";
        var param = {};
        if ($u.input_util.isNull(code_type)) {
            return;
        }
        param.code_type = code_type;
        if (parent_code_id) {
            param.arent_code_id = arent_code_id;
        }
        $u.webutil.doGet(url, param, async, registClient);
    }

},


/**
 * html5自带缓存系统
 */
 cache_util : {
    prefix: [],
    setItem: function (type, key, value) {
        if (type == "session") {
            this.setSessionItem(key, value);
        } else if (type == "local") {
            this.setLocalItem(key, value);
        }
    },
    getItem: function (type, key) {
        if (type == "session") {
            return this.getSessionItem(key);
        } else if (type == "local") {
            return this.getLocalItem(key);
        }
    },
    setJsonItem: function (type, key, json) {
        var value = JSON.stringify(json);
        this.setItem(type, key, value);
    },
    getJsonItem: function (type, key) {
        var value = this.getItem(type, key);
        if (value) {
            return JSON.parse(value);
        } else {
            return "";
        }
    },
    setLocalItem: function (key, value) {
        window.localStorage.setItem(key, value);
    },
    setSessionItem: function (key, value) {
        window.sessionStorage.setItem(key, value);
    },
    getLocalItem: function (key) {
        return window.localStorage.getItem(key);
    },
    getSessionItem: function (key) {
        return window.sessionStorage.getItem(key);
    },
    removeLocalItem: function (key) {
        window.localStorage.removeItem(key);
    },
    removeSessionItem: function (key) {
        window.sessionStorage.removeItem(key);
    },
    clearLocalData: function () {
        window.localStorage.clear();
    },
    clearSessionData: function () {
        window.sessionStorage.clear();
    }
},

/**
 * 将页面里的需要复现的值存储到一起,整合整个业务流程值
 */
pv_util : {
    _busObj: undefined,
    _pageObj: undefined,
    //业务唯一标识，开户，变更，还是其它
    _busFlagList: ["open_gsm","open_account", "pay_cost"],
    _busFlag: "",
    //每个页面的标识
    _pageFlag: "",
    /**
     * @param busFlag  业务标识，是开户open_account，变更，还是缴费pay_cost
     * @param pageFlag    例:开户中的phonenumber.html页面此值就传入 phonenumbe
     */
    init: function (busFlag, pageFlag) {
        this._inBusFlagList(busFlag);
        this._busFlag = busFlag;
        this._pageFlag = pageFlag;
        this._initData();
    },
    /**
     * 默认在当前业务流程，当前页面存储值,依据初始化init方法声明
     * 设置单独的key和value
     * @param key
     * @param value
     */
    setPageVal: function (key, value) {
        var pageObj = this._getPageObj();
        if (!key) {
            return;
        }
        pageObj[key] = value;
        this._savePageVal(this._pageFlag, pageObj);
    },
    /**
     * 默认在当前业务流程，当前页面存储值,依据初始化init方法声明
     * 提取整个jsonData的key
     * 录入为json格式,json中不要嵌套另一层json,标准格式{key:value,key,value}
     * deleteKeys删除多余的keys
     * @param jsonData
     */
    setJsonPageVal: function (key, jsonData, deleteKeys) {
        var pageObj = this._getPageObj(), saveJsonObj = {};

        if (!jsonData) {
            return;
        }
        if (deleteKeys) {
            for (var i = 0; i < deleteKeys.length; i++) {
                delete jsonData[deleteKeys[i]];
            }
        }
        saveJsonObj[key] = jsonData;
        $.extend(pageObj, saveJsonObj);
        this._savePageVal(this._pageFlag, pageObj);
    },
    _savePageVal: function (pageFlag, pageObj) {
        this._busObj[pageFlag] = pageObj;
        console.info("存储的paegObj为");
        console.info(pageObj);
        console.info("总体的busObj为");
        console.info(this._busObj);
        $u.cache_util.setJsonItem("session", this._busFlag, this._busObj);
        /**
         * pageFlag和当前页面的pageFlag相等的时候才存储
         */
        if (pageFlag == this._pageFlag) {
            this._pageObj = pageObj;
        }
    },
    /**
     * 获取html页面的key值
     * @param page 需要哪个页面存储的值
     * @param key
     * @returns
     */
    getPageVal: function (page, key) {
        var busObj = this._getBusObj(), pageObj;
        if (!busObj) {
            return "";
        }
        pageObj = busObj[page];
        if (key && pageObj) {
            return pageObj[key] ? pageObj[key] : "";
        } else {
            return "";
        }
    },
    /**
     *允许跨流程来取值
     *通过业务流程获取值,哪个页面来提取值
     *当需要干扰其它页面值的时候使用
     */
    getBusPageVal: function (busFlag, pageFlag, key) {
        this._inBusFlagList(busFlag);
        var busObj = this._getInitBusObj(busFlag);
        var pageObj = this._getInitPageObj(busObj, pageFlag);
        if (key && pageObj) {
            return pageObj[key];
        } else {
            return "";
        }
    },
    /**
     * 允许跨页面赋值
     * 没有进行初始化，直接赋值
     */
    setBusPageVal: function (busFlag, pageFlag, key, jsonData) {
        this._inBusFlagList(busFlag);
        var saveJsonObj = {};
        var busObj = this._getInitBusObj(busFlag);
        var pageObj = this._getInitPageObj(busObj, pageFlag);
        saveJsonObj[key] = jsonData;
        $.extend(pageObj, saveJsonObj);
        busObj[pageFlag] = pageObj;
        console.info("跨业务,页面存储的paegObj为");
        console.info(pageObj);
        console.info("跨业务,页面,总体的busObj为");
        console.info(busObj);
        $u.cache_util.setJsonItem("session", busFlag, busObj);
    },
    /**
     *  page 需要哪个页面存储的值
     * 批量获取 keys为字符串数组
     */
    getPageVals: function (pageFlag, keys) {
        var returnObj = {};
        for (var i = 0; i < keys.length; i++) {
            returnObj[keys[i]] = this.getPageVal(pageFlag, keys[i]);
        }
        return returnObj;
    },
    /**
     * 更新页面里的某个页面值
     */
    updatePageVal: function (pageFlag, key, jsonData) {
        var busObj = this._getBusObj(), pageObj;
        if (!busObj) {
            return "";
        }
        pageObj = busObj[pageFlag];
        if (pageObj && pageObj[key]) {
            $.extend(pageObj[key], jsonData);
            this._savePageVal(pageFlag, pageObj);
        }
    },
    /**
     * 删除当前业务流程浩总,删除指定的key
     * @param page
     * @param key
     */
    deletePageVal: function (pageFlag, key) {
        var busObj = this._getBusObj(), pageObj;
        if (!busObj) {
            console.log("deletePageVal删除失败,没有初始化PV_UTIL");
            return;
        }
        pageObj = busObj[pageFlag];
        if (key && pageObj) {
            delete pageObj[key];
        }
        this._savePageVal(pageFlag, pageObj);
    },
    /**
     *将统一的一个key
     */
    doMemCache: function () {
        var busObjStr = JSON.stringify(this._busObj);
        return MEMCACHE_UTIL.insertCache(this._busFlag, busObjStr, false);
    },
    _inBusFlagList: function (busFlag) {
        var flag = false;
        console.info(busFlag);
        for (var i = 0; i < this._busFlagList.length; i++) {
            console.info(this._busFlagList[i]);
            if (this._busFlagList[i] == busFlag) {
                flag = true;
            }
        }
        if (!flag) {
            throw new Error("PV_UIIL中Flag参数设置错误,请参照_busFlagList中的值传参");
        }
    },
    _getInitBusObj: function (busFlag) {
        var busObj = $u.cache_util.getJsonItem("session", busFlag);
        if (busObj == "") {
            busObj = {};
        }
        return busObj;
    },
    _getInitPageObj: function (busObj, pageFlag) {
        var pageObj = busObj[pageFlag];
        if (!pageObj) {
            pageObj = {};
        }
        return pageObj;
    },
    _initData: function () {
        var pageObj, busObj;
        busObj = this._getInitBusObj(this._busFlag);
        this._busObj = busObj;
        pageObj = this._getInitPageObj(busObj, this._pageFlag);//busObj[this._pageFlag];
        this._pageObj = pageObj;
        this._busObj[this._pageFlag] = pageObj;
    },
    _getPageObj: function () {
        return this._pageObj;
    },
    _getBusObj: function () {
        return this._busObj;
    },
    clearBusFlag: function () {
        for (var i = 0; i < this._busFlagList.length; i++) {
            var busFlag = this._busFlagList[i];
            $u.cache_util.removeSessionItem(busFlag);
        }
    }
},
/**
 * 所传参数说明
 * key:传入的key
 * value:传入的value
 * async：同步还是异步，true 同步,false 异步 异步如果获取值的时候需要回调函数
 * callBack: 回调函数,入参flag(true,false),value获取的value值
 * 同步的时候调用方法可以直接得到返回值
 */
memcache_util : {
        jsessionid: "",
        url: "rest/memcachedinfo/mem",//memcache缓存接口地址,
        insertCache: function (key, value, async, callBack) {
            var flag = "1";
            var key_value = {key: key, value: value, add: true};
            return this.prepareCache(flag, key_value, async, callBack);
        },
        getCache: function (key, async, callBack) {
            //如果为异步切回调函数为空不存在，不执行方法
            if (async && !callBack) {
                return;
            }
            var flag = "2";
            var key_value = {key: key, value: "", add: true};
            return this.prepareCache(flag, key_value, async, callBack);
        },
        deleteCache: function (key, async, callBack) {
            var flag = "3";
            var key_value = {key: key, value: "", add: true};
            return this.prepareCache(flag, key_value, async, callBack);
        },
        deleteBatchCache: function (arrays, async, callBack) {
        	this._initJsessionId();
            var keys = [];
            var flag = "3";
            for (var i = 0; i < arrays.length; i++) {
                keys.push(arrays[i] + "_" + this.jsessionid);
            }
            var key_value = {key: keys.join(","), value: "", add: false};
            return this.prepareCache(flag, key_value, async, callBack);
        },
        _initJsessionId:function(){
        	 this.jsessionid = $u.html_util.getParam("jsessionid");
        },
        /**flag 进行那种操作
         * key_value:json格式:{key:"",value:""}
         * async:同步还是异步
         * callBack回调函数.call(flag,value) flag:success,fail     value获取的key值
         */
        prepareCache: function (flag, key_value, async, callBack) {    
        	this._initJsessionId();
            var add = key_value.add;
            var cache_key = "";
            if (add) {
                cache_key = key_value.key + "_" + this.jsessionid;
            } else {
                cache_key = key_value.key;
            }
            var mem_value = key_value.value;
            var data = {
                op_type: flag,
                jsessionid: this.jsessionid,
                key_id: cache_key,
                json_str: mem_value
            };
            if (flag != "1") {
                delete data["json_str"];
            }
            //当其为同步调用的时候可以直接返回value
            var returnValue = "";
            var registClient = {
                onComplete: function (args) {
                    var mem_info = args.mem_info;
                    var value = "";
                    if (mem_info) {
                        value = mem_info.json_str;
                    }
                    //为删除和添加准备的标识位
                    if (flag == "1" || flag == "3") {
                        value = true;
                    }
                    if (callBack) {
                        callBack(true, value);
                    } else {
                        //async为false时为同步的ajax 可以返回值
                        if (!async) {
                            returnValue = value;
                        }
                    }
                },
                onError: function (error) {
                    this.failOperate();
                },
                onException: function (status, errorInfo, hint) {
                    this.failOperate();
                },
                failOperate: function () {
                    if (callBack) {
                        callBack(false);
                    } else {
                        if (!async) {
                            returnValue = false;
                        }
                    }
                }
            };
            $u.webutil.doPost(this.url, data, async, registClient);
            if (!async) {
                return returnValue;
            }
        }
 },

input_focus : {
    objBlur: function (obj, time) {
        time = time || 10;
        docTouchend = function (event) {
            if (event.target != obj) {
                setTimeout(function () {
                    obj.blur();
                    document.removeEventListener('touchstart', docTouchend, false);
                }, time);
            }
        };
        if (obj) {
            document.addEventListener('touchstart', docTouchend, false);
        }
    }
},
//
dialog_util : {
    /**
     * @param type   弹出框类型
     * @param msg   弹出框提示信息
     * @param callBack(type)   弹出框回调函数(有确认和取消按钮的时候)type为1时候确认 type为0 的时候 取消
     */

    dialog_ids: [],
    dialog_id: "public_dialog",
    /**
     * 模仿android吐司
     */
    showToastDialog:function(msg){
    	var id = this.dialog_id+"_toast";
    	var htmlcode = "<div   class='dialog-toast' id='" + id + "'><span>"+msg+"</span></div>";
    	$("body").append(htmlcode);
    	setTimeout(function(){
    		$("#"+id).remove();
    	},1500);
    	  
    },
    /**
     * 这个是居中带有遮罩层的弹出框
     */
    showTypeDialog: function (type, msg, callBack) {
        //var dialog_type="";
        var id = this.dialog_id;
        var self = false;
        //处理self值
        if (type == "confirm" ||type == "warning" || type == "loading" || type == "rest.need.reLogin") {
            self = true;
        }
        //处理ID,注意ID不能有.
        if (type == "rest.need.reLogin") {
            id = id + "reLogin";
        }
        //处理生成弹出框html begin
        var htmlcode = "";
        /**
         * info,error,config,loading  中type类型,其它未定义统一other
         */
        if (type == "loading") {
            htmlcode = "<div  id='" + id + "' l_type='" + type + "' class='x-loading-spinner'>"
            + "<span class='x-loading-top'></span>"
            + "<span class='x-loading-right'></span>"
            + "<span class='x-loading-bottom'></span>"
            + "<span class='x-loading-left'></span>"
            + "</div>";
        } else if (type == "rest.need.reLogin") {
            htmlcode = "<div l_type='" + type + "'  class='dialog' id='" + id + "'><div class='tip'>" + "需要登陆" + "</div></div>";
        } else {
            htmlcode = "<div l_type='" + type + "'  class='dialog' id='" + id + "'><div class='tip'>" + msg + "</div></div>";
        }
        //判重
        var index = this.getRepeatObjIndex(id);
        var flag = true;
        if (index == -1) {
            flag = false;
        }
        if (flag) {
            //移除
            $("#" + id).remove();
            $("body").append(htmlcode);
            this.dialog_ids[index].self = self;
            this.bindDialogClick(id, self);
            this.setDialogCss(id);
        } else {
            $("body").append(htmlcode);
            //处理生成弹出框html end
            this.showDialog(id, self);
        }

        if (type == "confirm") {
            this.createConfirmWindow(id, callBack);
        } else if(type == "warning"){
        	this.createWarningWindow(id, callBack);
        } else if (type == "rest.need.reLogin") {
            this.createNeedLogin(id);
        } 

    },
    createNeedLogin: function (id) {
        var htmlcode = "<div class='btn'><a href='javascript:void(0);' id='button" + id + "'>确 定</a></div>";
        $("#" + id).append(htmlcode);
        $("#button" + id).click(function () {
            if (window.WebViewJavascriptBridge) {
                window.WebViewJavascriptBridge.callMotion("gologinpage", "");
            }
        });
    },
    createConfirmWindow: function (id, callBack) {
        var that = this;
        var htmlcode = "<div class='btn'><a href='javascript:void(0);' id='button" + id + "'>确 定</a> <a href='javascript:void(0)'  class='cancel' id='cnacle" + id + "'>取 消</a></div>";
        $("#" + id).append(htmlcode);
        $("#button" + id).bind("click", function () {
            that.hideDialog(id);
            if (callBack) {
                callBack("1");
            }
        });
        //注册点击取消按钮
        $("#cnacle" + id).bind("click", function () {
            that.hideDialog(id);
            if (callBack) {
                callBack("0");
            }

        });
    },
    createWarningWindow: function (id, callBack) {
        var that = this;
        var htmlcode = "<div class='btn'><a href='javascript:void(0);' id='button" + id + "'>确 定</a></div>";
        $("#" + id).append(htmlcode);
        $("#button" + id).bind("click", function () {
            that.hideDialog(id);
            if (callBack) {
                callBack("1");
            }
        });
    },
    /**
     * 创建或显示遮罩层,只未显示的时候dialogId传入“”空字符串
     * @param dialogId
     * @param flag
     */
    createMaskWindow: function (dialogId, flag) {
        var html = "<div  class='cover'></div>";
        var that = this;
        //遮罩层不存在,才会创建
        if ($(".cover").length == 0) {
            $("body").append(html);
            $(".cover").show();
        }
        //点击遮罩层的时候,删除self为false的弹出框
        $(".cover").unbind("click").bind("click", function () {
            //公共的dialog才需要移除
            var flag = that.removeDialogs(that);
            if (flag) {
                that.removeMaskWindow();
            }
        });
    },
    bindDialogClick: function (dialogId, flag) {
        var that = this;
        $("#" + dialogId).unbind("click").bind("click", function () {
            var flag = that.removeDialog(that, dialogId);
            if (flag) {
                that.removeMaskWindow();
            }
        });
    },
    removeDialog: function (that, dialogId) {
        return that._dealDialogs(that, [dialogId]);
    },
    _dealDialogs: function (that, dialog_ids) {
        var tempArr = [];
        for (var i = 0; i < that.dialog_ids.length; i++) {
            var dialogId = that.dialog_ids[i].id;
            var self = that.dialog_ids[i].self;
            var flag = true;
            //slef为true代表自己控制,不对其进行删除和隐藏操作
            if (self) {
                tempArr.push(that.dialog_ids[i]);
                continue;
            }
            //dialog_ids需要清除的dialog的id的集合
            for (var j = 0; j < dialog_ids.length; j++) {
                if (dialog_ids[j] == dialogId) {
                	flag = false;
                    if (that.dialog_id == dialogId) {
                        $("#" + dialogId).remove();
                    } else {
                        $("#" + dialogId).hide();
                    }
                    //如果是公用弹出框则隐藏其它remove,self为false时候代表自动控制
                    break;
                }
            }
            if(flag){
            	 tempArr.push(that.dialog_ids[i]);
            }
        }
        that.dialog_ids = tempArr;
        return tempArr.length == 0;
    },
    /**
     * 点击遮罩层的时候,移除弹出框
     * @param that
     */
    removeDialogs: function (that) {
        var dialog_ids = [];
        for (var i = 0; i < that.dialog_ids.length; i++) {
            dialog_ids.push(that.dialog_ids[i].id);
        }
        return that._dealDialogs(that, dialog_ids);
    },
    /**
     * 删除遮罩层
     */
    removeMaskWindow: function () {
        $(".cover").remove();
    },
    /**
     *判断重复元素的数组下标
     */
    getRepeatObjIndex: function (id) {
        var index = -1;
        for (var i = 0; i < this.dialog_ids.length; i++) {
            if (this.dialog_ids[i].id == id) {
                index = i;
                break;
            }
        }
        return index;
    },
    /**
     * 添加obj
     * @param obj
     * @returns {Boolean}
     */
    addDialogIds: function (obj) {
        var index = this.getRepeatObjIndex(obj.id);
        var flag = false;
        if (index == -1) {//没有重复的
            flag = true;
        }

        if (flag) {
            this.dialog_ids.push(obj);
        }
        return flag;
    },
    /**
     * 传入弹出框ID
     * @param id
     * @param self 自己控制弹出框点击边框 false自动控制遮罩和弹出框隐藏,true自己需要回调hideDialog
     */
    showDialog: function (id, self) {

        var flag = this.addDialogIds({id: id, self: !!self});
        //不添加重复的值
        if (!flag) {
            return;
        }
        //生成并显示遮罩层 begin
        this.createMaskWindow(id, flag);
        //end
        //绑定弹出框事件begin
        this.bindDialogClick(id, flag);
        //设置弹出框的css
        this.setDialogCss(id);

    },
    /**
     * 设置弹出框的css,计算居中
     * @param id
     */
    setDialogCss: function (id) {
        $("#" + id).show();

        var div_height = $("#" + id).height();
        var div_width = $("#" + id).width();

        var right = Math.round((document.body.clientWidth - div_width) / 2);


        var scrollTop = document.body.scrollTop;
        var top = Math.round((document.documentElement.clientHeight - div_height) / 2) + scrollTop;

        //弹出框
        $("#" + id).css({
            'position': 'absolute', 'top': top, 'right': right
        });
    },
    /**
     * 传入弹出框ID
     * @param id
     */
    hideDialog: function (id, type) {
        //如果id没传则默认为公共弹出框ID
        if (!id) {
            id = this.dialog_id;
        }
        var tempArr = [];
        for (var i = 0; i < this.dialog_ids.length; i++) {
            var dialogId = this.dialog_ids[i].id;
            if (id != dialogId) {
                tempArr.push(this.dialog_ids[i]);
            }
        }

        var target = $("#" + id);
        var flag = false;//flag置为true的时候证明确实已经删除或者隐藏
        if (this.dialog_id == id) {
            var l_type = target.attr("l_type");
            //如果传入type
            if (type) {
                //如果传入的type和 当前type相等
                if (type == l_type) {
                    flag = true;
                    target.remove();
                }
            } else {
                flag = true;
                target.remove();
            }
        } else {
            flag = true;
            target.hide();
        }
        if (flag) {
            this.dialog_ids = tempArr;
            if (this.dialog_ids.length == 0) {
                this.removeMaskWindow();
            }
        }

    }
},

/**
 * 判断方法,BRVE.versions.android  值为true，false
 *
 */

brve : {
    versions: function () {
        var u = navigator.userAgent; //app = navigator.appVersion;
        return {//移动终端浏览器版本信息
            mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/), //是否为移动终端
            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
            iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
            iPad: u.indexOf('iPad') > -1, //是否iPad
            webApp: u.indexOf('Safari') == -1
        };
    }(),
    language: (navigator.browserLanguage || navigator.language).toLowerCase()
},
/**
 * 调用后台方法统一入口
 */
callback:{
	ios_init_flag:false,
	/**
	 * 初始化IOS和JS之间的接口
	 */
	_initIosFlag:function(){	
		/**
		 * 在JS调用IOS时候如果需要回调函数,ios直接调用的时候需要调用
		 */
		if($u.brve.versions.ios&&!this.ios_init_flag){
			window.WebViewJavascriptBridge.init(function(message, responseCallback) {	
				var data = { 'Javascript Responds':'Wee!' };//这里只是个例子数据
				responseCallback(data);
			});	
			this.ios_init_flag = true;
		}
		
	},
	/**
	 * android和ios简单的调用后台,不需要任何回到函数
	 * 
	 * motion:调用哪个事件,必传参数,例如saoyisao
	 * str:向后台传递的参数,字符串类型,非必传
	 * callBack:当需要后台返回数据的时候,传递函数,如果不需要后台返回数据,可以不传该参数,例子function(response){
	 * 		response为IOS回调后的入参
	 * }
	 */
	simpleCall:function(motion,str,callBack){
		 var bridge = window.WebViewJavascriptBridge;
		 str = str || "";
		 this._initIosFlag();
	     if (bridge) {
	    	 if(bridge.callMotion){
	    		 if(callBack){
	    			 if($u.brve.versions.ios){
		    			 bridge.callMotion(motion,str,callBack); 
		    		 }else if($u.brve.versions.android){
		    			 var responseData = bridge.callMotion(motion,str);		    			
		    			 //android需要手动调用
		    			 if(callBack){
		    				 callBack(responseData);
		    			 }
		    		 }		 
	    		 }else{
	    			 bridge.callMotion(motion, str);
	    		 }	
	    	 }
         }
	}
},
/**
 * 权限控制
 */
auth:{
	getAuthority:function(pageId){
		$u.dialog_util.showTypeDialog("loading","");
		var registClient = {
			onComplete : function(args) {
				var auth = args.get_info_pages_mobile_list_vo;
				$.each(auth,function(i,v){
					if(v.page_id==pageId){
						var cls = v.element_name;
						console.info("#"+pageId+" ."+cls+" 元素显示");
						$("#"+pageId+" ."+cls).show();
					}
				})
			},
			onError : function(error) {
				console.info(error);
			},
			onException : function(status, errorInfo, hint) {
				console.info(errorInfo);
			},
			onFinally:function(){
        			$u.dialog_util.hideDialog("","loading");
       		}
		};
		
		var operInfo = $.parseJSON($u.cache_util.getLocalItem("operInfo"));
		var strSessionId = operInfo.jsessionid;
		var operId = operInfo.oper_no;

		console.info(pageId+"页面开启权限控制");

		$u.webutil.doPost("rest/oper/getPagesAuthority",
				{
					jsessionid : strSessionId,
					oper_no:operId,
					page_id:pageId
				}, true, registClient);
	}
}

};






