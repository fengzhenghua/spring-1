/**
 * common.js是公共函数文件。只要引入了common.jsp的文件都可以使用这里的公共函数。
 * 也可以单独引用。如：<script type="text/javascript" src="<%=fullPath%>js/common.js"></script>
 */

//获取民族
function getNationStr(iNation) {
	if(isNaN(iNation)){
		return iNation;
	}
	var temp = parseInt(iNation,10);
	if (temp<0 || temp>56) {
		return iNation;
	}
	var arrNations = new Array("汉", "蒙古", "回", "藏", "维吾尔", "苗", "彝", "壮", "布依", "朝鲜", "满", "侗", "瑶", "白", "土家", "哈尼", "哈萨克", "傣", "黎", "傈僳", "佤", "畲", "高山", "拉祜", "水", "东乡", "纳西", "景颇", "克尔克孜", "土", "达斡尔", "仫佬", "羌", "布朗", "撒拉", "毛南", "仡佬", "锡伯", "阿昌", "普米", "塔吉克", "怒", "乌兹别克", "俄罗斯", "鄂温克", "德昂", "保安", "裕固", "京", "塔塔尔", "独龙", "鄂伦春", "赫哲", "门巴", "珞巴", "基诺");
	return arrNations[temp-1];
}

//日期格式化  convert: 20150923 to: 2015.09.23
function formatDateStr(strDate,strSeparator) {
	if (strSeparator==undefined)
		strSeparator = ".";
	
	if (strDate.length==8)  //yyyymmdd
		return strDate.substr(0, 4) + strSeparator + strDate.substr(4, 2) + strSeparator + strDate.substr(6, 2);
	else if (strDate.length==10) //yyyy-mm-dd
		return strDate.substr(0, 4) + strSeparator + strDate.substr(5, 2) + strSeparator + strDate.substr(8, 2);
	else
		return strDate;
}

//校验手机号码
function checkPhoneNumber(strPhone) {
	var phoneRegex = /^[0|1]\d{10}$/;//手机号正则表达式
	if (!phoneRegex.test(strPhone) || strPhone.length != 11) {
		return false;
	}
	else {
		return true;
	}
}
function addMask(){
	var w = $(window).width();
	var h = $(document).height();
	var maskDivHtml = "<div id='maskDivNotice'  style='cursor: pointer;position:fixed; _position:absolute; top:0px;left:0px;width:"+w+"px;height:"+h+"px;opacity:0.45;background:none repeat scroll 0 0 #000000;z-index: 90009;filter: progid:DXImageTransform.Microsoft.Alpha(opacity=45)'></div>";
	var maskDivNotice = $("#maskDivNotice");
	if(maskDivNotice.length > 0){
		maskDivNotice.remove();
	}
	$('body').append(maskDivHtml);
	
};

function loadingMask(str){
	
	addMask();
	var noHtml = '<div id="showLoadNotice" class="" style="display:block; position: absolute;z-index: 91000;padding: 10px 30px 10px 30px;top:40%;background: url(\''+application.basePath+'images/loading_bg.png\') repeat;border:1px solid #279DE5;border-top:10px solid #279DE5;"><table><tr height="25px"><td align="center" vertical-align="center" style="font-size:14px;font-weight:bold;">'+str+'</td></tr><tr height="20px"><td align="center" vertical-align="center"><img src="'+application.basePath+'images/loading.gif"/></td></tr></table></div>';
	var showLoad = $("#showLoadNotice");
	if(showLoad.length > 0){
		showLoad.remove();
	}
	$('body').append(noHtml);
	var showLoad2 = $("#showLoadNotice");
	showLoad2.css({"left":((($(window).width() - showLoad.width()) / 2 - 100)+ "px"),"top":((getScrollTop() + 220) +"px")});
}

function hideLoadingMask(){
	var showLoad = $("#showLoadNotice");
	if(showLoad.length > 0){
		showLoad.remove();
	}
	
	var maskDivNotice = $("#maskDivNotice");
	if(maskDivNotice.length > 0){
		maskDivNotice.remove();
	}
}

function getScrollTop(){
	var D = document; 
	return Math.max(D.body.scrollTop, D.documentElement.scrollTop)
}

function isEmpty(strVal) {
	if (strVal == '' || strVal == null || strVal == undefined) {
		return true;
	} else {
		return false;
	}
}

function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}


var WebUtil = {
		
		/**
		 * otherParam默认值配置
		 */	
		defaultOtherParam:{ignoreDefaultErrorHandler:false},	
		/**
	     * 发送GET请求
	     */
	    doGetAsync: function (url, data, async, registClient, waitMsg, otherParam) {
	        return this.checkParamAsync("GET", url, data, async, registClient, waitMsg, otherParam);
	    },
	    /**
	     * 发送POST请求
	     */
	    doPostAsync: function (url, data, async, registClient,waitMsg, otherParam) {
	        return this.checkParamAsync("POST", url, data, async, registClient, waitMsg, otherParam);
	    },

	    /**
	     * 检查参数
	     */
	    checkParamAsync: function (type, url, data, async, registClient,waitMsg, otherParam) {
	        if (!url) {
	            return;
	        }
	        if (!data) {
	            return;
	        }
	        if ((!registClient) || (!registClient.onComplete) ) {
	            return;
	        }
	        //处理otherParam
	       otherParam = $.extend({},this.defaultOtherParam,otherParam||{});
	       return this.sendUrlAsync(type, url, data, async, registClient, waitMsg, otherParam);
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
	    sendUrlAsync: function (type, url, data, async, registClient, waitMsg, otherParam) {
	        var timeout_value=60000;//默认超时设置一分钟
	        if (otherParam.timeout_value!=undefined) {
	        	timeout_value = otherParam.timeout_value;
	        }
	        var dtd = null;
	        if(typeof jQuery == 'undefined'){
	        	
	        }
	        else if(async){
	        	dtd = $.Deferred();
	        }
	        var that = this;
	        var ajaxParam = {
	            url: url,
	            async: async,
	            data: data,
	            timeout: timeout_value,
	            type: type,
	            dataType: "json",
	            waitMsg:waitMsg,
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
	                var error = that.dealErrorInfo(data);
	                var args = that.getArgs(data);
	                if (error != null) {
	                	if(registClient.onError){
	                		registClient.onError(error);
	                	}
	                    //如果配置了不调用默认错误处理器
	                    if (!otherParam.ignoreDefaultErrorHandler) {
	                        that.hintErrorInfo(error, args);
	                    }
	                    if(dtd != null){
	                    	dtd.reject();
	                    }
	                } else {
	                    var completeflag = registClient.onComplete(args)
	                    if(dtd != null){
	                    	if(completeflag == undefined || completeflag){
	                    		dtd.resolve();
	                    	}else{
	                    		dtd.reject();
	                    	}
	                    }
	                }
	            },
	            /**
	             * 400系列码 400范围的状态码是客户端错误码
	             * 500系列码 500级状态码表示的是服务器错误
	             */
	            error: function (XMLHttpRequest, textStatus) {
	                var status = XMLHttpRequest.status;
	                var errorInfo = HTTP_ERROR_STATUES[status];
	                var hint = "网络繁忙，请稍后再试!";//通用万金油提示，都懂得。
	                var error = errorInfo;
	                if (!error) {
	                    error = hint + ",后台接口报错!";
	                }
	                $.error(error);
	                if(registClient.onException){
	                	registClient.onException(status, errorInfo, hint);
	                }
	                if(dtd != null){
	                	dtd.reject();
	                }
	            }
	        };
	        if (otherParam) {
	            if (otherParam.ajaxParam) {
	                $.extend(ajaxParam, otherParam.ajaxParam);
	            }
	        }
	        $.ajax(ajaxParam);
	        if(dtd != null){
	        	return dtd.promise();
	        }
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
	        if (resp_code == "rest.need.reLogin") {//需要登陆
	        } else if (resp_code == "dele.memchached.error") {

	        } else if (resp_code == "memchached.error") {

	        } else {
	        	$.error(content);
	        }
	    }
};
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