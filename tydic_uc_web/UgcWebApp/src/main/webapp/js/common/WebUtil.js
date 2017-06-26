


var HTTP_ERROR_STATUES = {
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
};

var BASE_URLS = {
	HOST: "101.200.72.48:8093",
    HTTP: "http://",
    HTTPS: "https://",
    URL_SPLITTER: "/",
    PROJECT_NAME: "uoc_rest"
};
var URLS = {
    URL_API_BASE: BASE_URLS.HTTP + BASE_URLS.HOST,
    URL_API_HOST: BASE_URLS.HTTP + BASE_URLS.HOST + BASE_URLS.URL_SPLITTER + BASE_URLS.PROJECT_NAME + BASE_URLS.URL_SPLITTER,
    URL_API_BIGDATA_HOST: BASE_URLS.HTTP + BASE_URLS.BIGDATA_HOST + BASE_URLS.URL_SPLITTER + BASE_URLS.BIGDATA_NAME + BASE_URLS.URL_SPLITTER,
    //例子，登陆URL   URLS.getOpenLoginUrl();
    getOpenLoginUrl: function () {
        return this.URL_API_HOST + "rest/oper/login";
    },
    /**
     * 查询codeList接口
     */
    getCodeListUrl: function () {
        return this.URL_API_HOST + "rest/codeType/codeList";
    }
};


var HTML_UTLS = {
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
	    }
	};

	var INPUT_UTIL = {
	    /**判断是否为空
	     * 为空返回true
	     * 不为空返回false
	     */
	    isNull: function (str) {
	    	if (str==null || str=="" || str=="null" || str==undefined || str=="undefined"){
	    		return true;
	    	}else {
	    		return false;
	    	}
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
	    	if(s==null){
	    		return "";
	    	}else{
	    		return s.replace(/(^s*)|(s*$)/g, "");
	    	}
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

	                    if (elem._length === elem.value.length) {
							return;
						}
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
};





