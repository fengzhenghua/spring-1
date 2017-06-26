/*定义全局页面配置信息*/
var PagConfig = new Object();

//Ajax后台返回日志开关 true:开;false:关
PagConfig.debug = false;


/***
* 定义系统信息处理类,用来输入消息
* @version 1.0
* based on easyUI
**/
(function($){
	
	/*定义信息处理类*/
	$.AilkMessageManager = function(){
		this.settings = $.extend(true,{},$.AilkMessageManager.defaults);
	}
	
	/*为信息处理类扩展属性,并 继承方法*/
	$.extend($.AilkMessageManager,{
		/*扩展属性*/
		defaults: {
			AI_WAIT_DIALOG: "x_aiwait_dialog",//处理进度对话框的DIV ID
			SELECTOR_AI_WAIT_DIALOG: "#x_aiwait_dialog", // 处理进度对话框选择器
			AI_DEBUG_WINDOW : "x_aidebug_window",//调试信息窗口DIV 标识
			SELECTOR_AI_DEBUG_WINDOW: "#x_aidebug_window",//调试信息窗口选择器
			AI_DEBUG_TEXTAREA: "x_aidebug_textarea",//调试信息输出的textarea
			SELECTOR_AI_DEBUG_TEXTAREA: "#x_aidebug_textarea", //调试信息输出的textarea选择器
			AI_EXCEPTION_WINDOW: "x_ai_exception_window",//异常信息窗口的div
			SELECTOR_AI_EXCEPTION_WINDOW: "#x_ai_exception_window", //异常信息窗口的选择器
			
			KEY_EXCEP_TYPE : "EXCEP_TYPE",//AJAX请求返回回来的异常类型标识码
			KEY_EXCEP_MESSAGE : "EXCEP_MESSAGE",//AJAX请求返回回来的异常信息标示码
			KEY_EXCEP_DETAIL : "EXCEP_DETAIL",//AJAX请返回的详细信息标识码
			KEY_EXCEP_NAME : "EXCEP_NAME",//AJAX请求返回回来的异常名称标识码
			KEY_EXCEP_PRINTEX : "EXCEP_PRINTEX",//AJAX请求返回的堆栈错误标识码
			KEY_OPERATOR : "OPERATOR"//AJAX请求返回的错误信息的测试人员标识
		},
		
		/*继承原型的方法*/
		prototype: {
			
			/**
			* 显示处理进度条
			* @param:options 全部入参为
				{ message:'正在处理中，请稍候..',//进度显示消息
				  modal:true/false,//是否遮罩
				  shadow:true/false,//是否有阴影边框
				  width: 250, //消息栏宽度，数字
				  height: 60 //消息栏高度，数字
				}
			**/
			showWait: function(options){
				
				/*内部函数，渲染对话框信息*/
				function renderAiWait(options){
					/*1.从入参对象中获取属性信息*/ 
					var width =  options && options.width ? options.width:300;
					var height = options && options.height ? options.height:80;
					var modal = options && options.modal==true ? true:false;
					var shadow = options && options.shadow==true ? true:false;
					
					/*2.执行渲染处理*/
					var _this=this;
					$(_this.settings.SELECTOR_AI_WAIT_DIALOG).dialog({
						title: "",
						width: width,
						height: height,
						modal: modal, 
						shadow: shadow
					});
				}
				
				/*1.获取wait对话框对象*/
				var aiwaitDialog = $(this.settings.SELECTOR_AI_WAIT_DIALOG);
				var message =  options && options.message ? options.message:"正在处理中，请稍候...";
				/*2.如果当前页面不存在，则创建一个*/
				if(!aiwaitDialog.length){
					/*2.1 拼装HTML*/
					var html = "<div id=\""+ this.settings.AI_WAIT_DIALOG +"\">";
					html+="<table width=\"100%\" height=\"100%\" border=0>";
					html+="<tr>"; 
					html+="<td width=100 align=center><span  class=\"AIAPPFRAME_WAIT_LOAD_CSS\"></span></td>";
					html+="<td id='wait_message_td'>"+ message +"</td>";
					html+="</tr>";
					html+="</table>"; 
					html+="</div>";
					/*2.2 载入到当前页面*/
					$(document.body).append(html);
				}else{
					/*2.3 如果存在，则需要更新消息栏上的提示信息*/
					$(this.settings.SELECTOR_AI_WAIT_DIALOG).find("#wait_message_td").html(message);
				} 
				
				/*3.渲染效果*/
				renderAiWait.call(this,options);
			},
			/**
			* 在调试窗口中输出指定的消息
			* @param:message-需要显示的信息
			* @options:配置参数
			**/
			showLogInfo: function(/*String*/message,/*json*/options){
				/**内部方法：渲染信息调试窗口*/
				function renderAiDebugWindow(message,options){
					var _this=this;
					/*1.渲染调试信息窗口*/
					$(_this.settings.SELECTOR_AI_DEBUG_WINDOW).window({
						title: options.title?options.title:'输出信息',
						width: options.width?options.width:720,
						modal: false,
						shadow: false,
						closed: true,
						minimizable:false,
						maximizable:false,
						resizable: false,
						height: options.height?options.height:370
					});
					/*2.渲染窗口中的关闭按钮*/
					$('#x-debug-closebtn').linkbutton({
						iconCls: 'icon-ok'
					}).bind("click",function(){
						$(_this.settings.SELECTOR_AI_DEBUG_WINDOW).window("close");
					});
					
					/*3.输出错误信息*/
					var debuTextarea = $(_this.settings.SELECTOR_AI_DEBUG_TEXTAREA);
					if(debuTextarea.length){
						/*如果需要先清除调试信息，则清空*/
						if(options.clear)debuTextarea.val("");
						var msg = debuTextarea.val();
						msg+=new Date().toLocaleString()+" 输出信息:\r\n";
						msg+=message;
						msg+="\r\n";//回车
						msg+="-----------------------------------------------------";
						msg+="\r\n";
						debuTextarea.val(msg);
					}
					/*4.默认开启窗口*/
					$(_this.settings.SELECTOR_AI_DEBUG_WINDOW).window("open");
				}
				options = options?options:{};
				var cols = options.cols?options.cols:100;
				var rows = options.rows?options.rows:20;
				/*1.获取当前界面上的调试窗口*/
				var xAiDebugWindow = $(this.settings.SELECTOR_AI_DEBUG_WINDOW);
				/*2.如果不存在，则默认构建一个*/
				if(!xAiDebugWindow.length){
					var windowHTML = "";
					windowHTML+="<div id=\""+ this.settings.AI_DEBUG_WINDOW +"\"  class=\"easyui-window\"  icon=\"icon-help\" >";
					windowHTML+="<div class=\"easyui-layout\">";
					windowHTML+="<div region=\"center\" border=\"false\" fit=\"true\" style=\"padding:1px;background:#fff;border:1px solid #ccc;\">";
					windowHTML+="<textarea cols=\""+cols+"\" rows=\""+rows+"\" id=\""+ this.settings.AI_DEBUG_TEXTAREA +"\"></textarea>";
					windowHTML+="</div>";
					windowHTML+="<div region=\"south\" border=\"false\" style=\"text-align:right;height:30px;line-height:30px;\">";
					windowHTML+="<a id=\"x-debug-closebtn\" class=\"easyui-linkbutton\" iconCls=\"icon-ok\">关闭</a>";
					windowHTML+="</div>";
					windowHTML+="</div>";
					windowHTML+="</div>";
					$(document.body).append(windowHTML);
				} 
				options = options?options:{};
				/*3.渲染调试信息*/
				renderAiDebugWindow.call(this,message,options);
			},
			/**隐藏处理消息框*/
			hideWait: function(){
				$(this.settings.SELECTOR_AI_WAIT_DIALOG).dialog('close');
			},
			
			/**
			* 普通提示
			* @param message:提示信息
			* @param callFunc:回调函数
			* @param args:回调函数参数数组
			*/
			alert: function(message,/*Function*/callFunc,/*Array*/args){
				var top = document.documentElement.scrollTop+'px' ;
				/*转移焦点*/
				this.transferFocus();
				$.messager.defaults={ok:"确定",cancel:"取消"};
				$.messager.alert('提示',message,'alert',function(){
					if(!args || args.constructor!=window.Array){
						args = arguments; 
					}
					callFunc && callFunc.apply(this,args); 
					$('html,body').animate({scrollTop: top}, 100);
				});

			},
			/**
			* 错误提示
			* @param message:提示信息
			* @param callFunc:回调函数
			* @param args:回调函数参数数组
			*/
			error: function(message,/*Function*/callFunc,/*Array*/args){
				var top = document.documentElement.scrollTop+'px' ;
				/*转移焦点*/
				this.transferFocus();
				$.messager.defaults={ok:"确定",cancel:"取消"};
				$.messager.alert('错误提示',message,"error",function(){
					if(!args || args.constructor!=window.Array){
						args = arguments; 
					}
					callFunc && callFunc.apply(this,args); 
					$('html,body').animate({scrollTop: top}, 100);
				});
			},
			/**
			* 信息提示
			* @param message:提示信息
			* @param callFunc:调用函数
			* @param args:回调函数参数数组
			*/
			info: function(message,/*Function*/callFunc,/*Array*/args){
				var top = document.documentElement.scrollTop+'px' ;
				/*转移焦点*/
				this.transferFocus();
				$.messager.defaults={ok:"确定",cancel:"取消",closed:false};
				var flag = '0';
				$.messager.alert('提示',message,"info",function(){ 
					if(!args || args.constructor!=window.Array){
						args = arguments; 
					}
					callFunc && callFunc.apply(this,args);
					$('html,body').animate({scrollTop: top}, 100);
				});
			},
			/**
			* 问题提示
			* @param message:提示信息
			* @param callFunc:调用函数
			* @param args:回调函数参数数组
			*/
			question: function(message,/*Function*/callFunc,/*Array*/args){
				var top = document.documentElement.scrollTop+'px' ;
				/*转移焦点*/
				this.transferFocus();
				$.messager.defaults={ok:"确定",cancel:"取消"};
				$.messager.alert('确认',message,"question",function(){ 
					if(!args || args.constructor!=window.Array){
						args = arguments; 
					}
					callFunc && callFunc.apply(this,args); 
					$('html,body').animate({scrollTop: top}, 100);
				});
			},
			/**
			* 警告提示
			* @param message:提示信息
			* @param callFunc:调用函数
			* @param args:回调函数参数数组
			*/
			warning: function(message,/*Function*/callFunc,/*Array*/args){
				var top = document.documentElement.scrollTop+'px' ;
				/*转移焦点*/
				this.transferFocus();
				$.messager.defaults={ok:"确定",cancel:"取消"};
				$.messager.alert('警告',message,"warning",function(){ 
					if(!args || args.constructor!=window.Array){
						args = arguments; 
					}
					callFunc && callFunc.apply(this,args);
					$('html,body').animate({scrollTop: top}, 100);
				});
			},
			/**
			* 确认信息
			* @param message:提示信息
			* @param callFunc:调用函数
			* @param args:函数参数列表
			**/
			confirm : function(/*String*/message,/*Function*/callFunc,/*Array*/args){
				var top = document.documentElement.scrollTop+'px' ;
				/*转移焦点*/
				this.transferFocus(); 
				$.messager.defaults={ok:"确认",cancel:"取消"}; 
				$.messager.confirm('确认', message, function(r){
					if (r){
						if(!args || args.constructor!=window.Array){
							args = arguments; 
						}  
						callFunc && callFunc.apply(this,args); 
					}
				});
				$('html,body').animate({scrollTop: top}, 100);
			},
			/**
			* 确认信息
			* @param message:提示信息
			* @param options: 配置参数
			   {
			     okFunc: function(){},//确定按钮回调函数
			     okArgs: [],//确定按钮回调函数参数组
			   	 cancelFunc: function(){},//取消按钮回调函数
			   	 cancelArgs: [],//取消按钮回调函数参数组
			   }
			**/
			confirmNew: function(/*String*/message,/*JSON*/options){
				var top = document.documentElement.scrollTop+'px' ;
				options = options ? options:{};
				/*转移焦点*/
				var activeElementObj=document.activeElement;
				this.transferFocus();
				/*获取参数*/
				var args =  options.okArgs;
				var callFunc = options.okFunc;
				var cancelArgs= options.cancelArgs;
				var cancelFunc= options.cancelFunc;
				/*设置提示框信息*/
				$.messager.defaults={ok:"确认",cancel:"取消"}; 
				$.messager.confirm('确认', message, function(r){
					if (r){
						if(!args || args.constructor!=window.Array){
							args = arguments; 
						}  
						if($(activeElementObj).length)$(activeElementObj).focus();
						callFunc && callFunc.apply(this,args);
						$('html,body').animate({scrollTop: top}, 100);
					}else{
						if(!cancelArgs || cancelArgs.constructor!=window.Array){
							cancelArgs = arguments; 
						}  
						if($(activeElementObj).length)$(activeElementObj).focus();
						cancelFunc && cancelFunc.apply(this,cancelArgs);
						$('html,body').animate({scrollTop: top}, 100);
					}
				});
			},
			/**转移焦点*/
			transferFocus: function(){
				/*1.创建一个DIV*/
				var div="<div id='_x_div_focus_id' tabindex='-1'></div>";
				if(!$("#_x_div_focus_id").length){
					$(document.body).append(div);
				}
				/*2.使DIV获取到焦点*/
				if($("#_x_div_focus_id").length){
					$("#_x_div_focus_id").focus();
				} 
			}
		
		} 
	})
	
})(jQuery)

//实例化一个信息处理对象
var messageManager= new $.AilkMessageManager();

/***
* 定义页面交互信息调试类,根据配置输出调试信息 
* @version 1.0
**/
(function($){
	/*声明一个页面调试类*/
	$.PagDebug = function(){
		this.settings = $.extend(true,{},$.PagDebug.defaults);
	};
	/*扩展一些属性与方法*/
	$.extend($.PagDebug,{
		defaults : {
			
		},
		prototype : {
			
			/**
			* 输出调试信息
			* @parent message:调试日志信息 
			**/
			debug : function(/**String*/message){
				if(PagConfig.debug === false){
					return;
				}  
				messageManager.showLogInfo(message);
			}
		}
	});
})(jQuery)

//实例化一个页面日志调试类
var pagDebug = new $.PagDebug();

/***
* 定义页面控制处理类,用来控制页面交互处理
* @version 1.0
**/
(function($){
	
	/*定义页面交互处理类*/
	$.AilkPageInteractionManager = function(){
		this.settings = $.extend(true,{},$.AilkPageInteractionManager.defaults);
	}
	
	/*为页面交互处理类扩展属性,并 继承方法*/
	$.extend($.AilkPageInteractionManager,{
		/*扩展属性*/
		defaults: {
			AI_WAIT_DIALOG: "x_aiwait_dialog",//处理进度对话框的DIV ID
			SELECTOR_AI_WAIT_DIALOG: "#x_aiwait_dialog", // 处理进度对话框选择器
			AI_DEBUG_WINDOW : "x_aidebug_window",//调试信息窗口DIV 标识
			SELECTOR_AI_DEBUG_WINDOW: "#x_aidebug_window",//调试信息窗口选择器
			AI_DEBUG_TEXTAREA: "x_aidebug_textarea",//调试信息输出的textarea
			SELECTOR_AI_DEBUG_TEXTAREA: "#x_aidebug_textarea", //调试信息输出的textarea选择器
			AJAX_SUBMIT_CONTAINER: "_X_AJAX_SUBMIT_CONTAINER_DIV",//内部提交的AJAX容器DIV标识
			SELECTOR_AJAX_SUBMIT_CONTAINER: "#_X_AJAX_SUBMIT_CONTAINER_DIV",//内部提交的AJAX容器选择器
			AJAX_STATUS_SUCCESS: "1",//AJAX请求成功状态
			AJAX_STATUS_FAILURE: "0",//AJAX请求失败状态
			EXCEP_PRINTEX: "EXCEP_PRINTEX",//异常信息堆栈KEY
			SHOW_DETAIL: "SHOW_DETAIL",//错误提示中，是否有查看详细功能
			STATUS_CODE: "STATUS_CODE",//对应于appframe5.5封装的自定义消息CustomProperty的状态key值
			STATUS_INFO: "STATUS_INFO"//对应于appframe5.5封装的自定义消息CustomProperty的状态描述信息key值
		},
		
		/*继承原型的方法*/
		prototype: {
			
			/**
			* 载入iframe页面信息
			* @param:iframe --iframe选择器
			* @param:url --在iframe内加载的url
			* @param:options -json格式，额外的参数键值对.<br>
			  完整的参数：{
			  	showBusi： true/false, //是否显示载入进度条
			  	modal: true/false,//如果存在遮罩，是否模态形式遮罩
			  	message: "xx"//进度信息
			  } 
			**/
			loadIframe: function(/*selector*/iframe,/*String*/url,/*json*/options){
				/*1.获取是否显示载入进度条,进度消息，是否遮罩*/
				var showBusi = options && options.showBusi?true:false;
				var modal = options && options.modal?true:false;
				var message = options && options.message ? options.message: "页面正在加载，请稍候..";
				/*2.如果不显示处理进度条，则不显示*/
				if(showBusi)messageManager.showWait({ message:message,modal: modal});
				/*3.载入URL*/
				$(iframe).attr("src",url);
				/*4.侦听页面是否加载完毕，如果完毕，则根据配置隐藏进度信息栏*/
				$(iframe).load(function(){
					if(showBusi)messageManager.hideWait();
				})
			},
			
			/**
			* Ajax请求方法
			* @param options:参数信息
			* based on jquery.form.js
			**/
			goAjax: function(/*JSON*/options){
				var _this = this;
				/*1.转换各种回调函数*/
				var callbacks = {};
				if(typeof options.before == 'function'){
					callbacks["before"] = options.before;
					delete options.before;
				}
				if(typeof options.success=='function'){
					callbacks["success"] = options.success;
					delete options.success;
				}
				if(typeof options.failure=='function'){
					callbacks["failure"] = options.failure;
					delete options.failure;
				}
				if(typeof options.error=='function'){
					callbacks["error"] = options.error;
					delete options.error;
				}  
				var target = options.target;
				delete options.target;
				/*2.获取提交方式,如果没有指定，默认是request*/
				var postmode = options.postmode?options.postmode:"request";
				/*3.获取处理消息相关的信息*/
				//获取是否显示处理进度 如果没设置，则不显示
				var showBusi = options && options.showBusi==true?true:false;
				//获取消息处理进度框是否遮罩,如果不设置，默认为不遮罩
				var modal = options && options.modal==true?true:false;
				//获取消息处理进度框中显示的提示信息
				var message = options && options.message ? options.message: "正在处理中，请稍候..";
				//定义一个消息对象
				var msgManager = options.showArea=='parent'?parent.messageManager : messageManager;
				/*4.定义一个新的参数值对信息，继承入参相关参数*/
				var settings = {}; $.extend(settings,options); 
				
				/*5.实现一些jquery.form.js原生的回调函数*/
				/**
				* 实现ajax后端处理成功后的回调函数
				* @param transport:指ajax捕捉的服务端返回的信息
				**/
				settings["success"] = function(transport){
					//关闭进度条
					if(showBusi)msgManager.hideWait();
					
					/*5.1 首先判断返回值是否是JSON格式*/
					var returnObj = dataProcesser.parseJSON(options.dataType,transport);
					if(returnObj){
						/*5.1.1 获取特定的处理状态值*/
						var status = returnObj[_this.settings.STATUS_CODE];
						var statusInfo = returnObj[_this.settings.STATUS_INFO];
						/*5.1.2 根据状态码进行处理*/
						if(status && status == _this.settings.AJAX_STATUS_FAILURE){
							/*如果状态码为失败，则回调failure函数*/ 
							var sessionInvalid = returnObj["_SESSION_INVALID_"]?returnObj["_SESSION_INVALID_"]:"1";
							var redirectURL = returnObj["_REDIRECT_URL_"];
							if(sessionInvalid == "0"){ 
								window.location=redirectURL;
								return false;
							} 
							/*封装显示的详细信息*/
							var randTime = new Date().getTime();
							var showDetail = returnObj[_this.settings.SHOW_DETAIL];
							var info = showDetail?"处理失败(<a href='#x_alert_message_anchor' id='_frame_href_alert_message_"+ randTime +"' title='点击查看详细'><b>详情</b></a>)："
								:"处理失败，";
							info = statusInfo? info+statusInfo:info+"请查看详细，并联系管理员解决";
							msgManager.warning(info,function(){
								callbacks["failure"] && callbacks["failure"].call(this);  
							});  
							/*绑定查看详细事件*/
							$("#_frame_href_alert_message_"+ randTime +"").bind("click",function(){
								var excepPrintex = returnObj[_this.settings.EXCEP_PRINTEX];
								msgManager.showLogInfo(excepPrintex,{
									title: "详细错误信息",
									width: 750,
									height: 400,
									clear: true
								});
							})
						}else{
							/*如果状态码为成功，则回调success函数*/
							callbacks["success"] && callbacks["success"].call(this,returnObj);
						}
						pagDebug.debug("【返回数据格式】JSON  \r\n【数据内容】\r\n"+JSON.stringify(returnObj));
						return false;
					}
					/*5.2 判断返回值是否是XML格式*/
					var returnObj = dataProcesser.parseXML(options.dataType,transport);
					if(returnObj){
						/*5.2.1 调用appframe5.5 公有 api获取用户节点信息*/
						var xmlNode = transport.documentElement;
						var ud = createUserDataClass(xmlNode,true); 
						/*5.2.2 获取处理状态*/
						var status = ud.getValueByName(_this.settings.STATUS_CODE);
						var statusInfo = ud.getValueByName(_this.settings.STATUS_INFO);
						if(status && status == _this.settings.AJAX_STATUS_FAILURE){
							/*如果状态码为失败，则回调failure函数*/
							var info = statusInfo?"处理失败,错误信息:"+statusInfo+"":"处理失败，请联系管理员解决";
							msgManager.warning(info,function(){
								callbacks["failure"] && callbacks["failure"].call(this);  
							});   
						}else{
							/*如果状态码为成功，则回调success函数*/
							callbacks["success"] && callbacks["success"].call(this,transport);
						}
						pagDebug.debug("【返回数据格式】\r\nxml \r\n【数据内容】\r\n"+xmlNode.xml);
						return false;
					}
					
					/*5.3 其它数据格式*/
					pagDebug.debug("【返回数据格式】\r\ntext \r\n【数据内容】\r\n"+transport);
					if(postmode=="update")$(target).html(transport);
					callbacks["success"] && callbacks["success"].call(this,transport);
				};
				/**
				* 实现ajax处理前的句柄操作。如果返回为false,则终止操作 
				**/
				settings["beforeSubmit"] = function(){ 
					return callbacks["before"] && callbacks["before"].call(this);  
				};
				/**
				* 请求失败后的回调函数。如404错误等
				* 一般指服务器的异常，不能完成一次完整的请求
				**/
				settings["error"] = function(transport){  
					/*1.关闭处理进度信息*/
					if(showBusi)msgManager.hideWait();
					/*2.点击确定后执行回调函数*/ 
					msgManager.error("请求错误,错误码:"+transport.status+"",function(){
						callbacks["error"] && callbacks["error"].call(this,transport);
					});  
				};
				
				/*6.其它处理*/
				//生成遮罩效果
				if(showBusi)msgManager.showWait({ message:message,modal: modal,width: options.width,height: options.height});
				//附带数据部分的处理
				settings.data=options.data?options.data:{};
				settings.data["url_source"]="AilkPageInteractionManager"; 
				
				/**对提交区域的容器进行处理*/
				if(options.postselectors && options.postselectors.length==1){ 
					/*如果待提交区域容器存在，而且仅仅只有一个，则不做任何处理，直接进行提交*/
					settings.semantic=true; 
					var postContainerSelector=options.postselectors[0]; 
					if($(postContainerSelector).length){
						$(postContainerSelector).ajaxSubmit(settings);
					}else{
						_this.processCombineParamContainer(options.postselectors);
						$(this.settings.SELECTOR_AJAX_SUBMIT_CONTAINER).ajaxSubmit(settings);
					}
					
				}else{
					/*如果待提交区域容器不存在，或者存在而且存在多个，则创建一个虚拟的提交区域作为提交区域*/
					settings.semantic=true;
					/**处理参数容器*/
					_this.processCombineParamContainer(options.postselectors);
					$(this.settings.SELECTOR_AJAX_SUBMIT_CONTAINER).ajaxSubmit(settings);
				}  
			}, 
			/**
			* 合并多区域提交的参数容器
			* @param postContainerSelectors: 需要批量提交的容器选择器数组
			**/
			processCombineParamContainer: function(/**Array*/postContainerSelectors){
				/**1.创建一个虚拟容器*/
				this.createSubmitContainer();
				var submitContainer = $(this.settings.SELECTOR_AJAX_SUBMIT_CONTAINER);
				/**3.处理批量提交的容器数据*/
				if(postContainerSelectors && $.isArray(postContainerSelectors)){
					$(postContainerSelectors).each(function(index,selector){
						if($(selector).length){
							$(selector).clone().prependTo(submitContainer);
						} 
					}); 
				}  
			},
			
			/**创建一个虚拟容器**/
			createSubmitContainer: function(){ 
				var xSubmitContainer = $(this.settings.SELECTOR_AJAX_SUBMIT_CONTAINER);
				if(!xSubmitContainer.length){
					$(document.body).append("<div id='"+ this.settings.AJAX_SUBMIT_CONTAINER +"' style='display:none'></div>");
				}else{
					xSubmitContainer.html("");
				}
			}
			
		}
	})
})(jQuery)

//实例化一个页面交互实例对象
var formPag = new $.AilkPageInteractionManager();

/**
* 定义一些javascript数据处理类
* 用于对xml，json,text等格式的数据进行处理
**/
(function($){
	/*声明一个数据处理类*/
	$.DataProcesser = function(){
		this.settings = $.extend(true,{},$.DataProcesser.defaults);
	}
	/*扩展一些属性与方法**/
	$.extend($.DataProcesser,{
		defaults: {
		},
		
		prototype: {
			
			/**
			* 判断是否是JSON格式,并将data解析成json对象
			* 如果不是json对象，返回false,如果是json对象，则直接返回
			* @param dataType: $.ajax能定义的返回数据类型
			* @param data:未知的数据类型
			*/
			parseJSON: function(dataType,data){
				if(dataType=="json"){
					var isJSON = this.isJson(data);
					return isJSON?data:false;
				};
				try{
					var d=$.parseJSON(data);
					return d?d:false; 
				}catch(ex){ 
					return false;
				}
			},
			/**
			* 判断是否是JSON格式
			* 如果不是json对象，返回false,如果是json对象，则true
			* @param obj:未知的数据类型
			*/
			isJson: function(obj){
				var isjson = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length;    
				return isjson;
			},
			/**
			* 判断是否是xml格式,并将data解析成xmldocument对象
			* 如果是返回xmldocument， 否则返回false
			* @param dataType: $.ajax能定义的返回数据类型
			* @param data:未知的数据类型
			*/
			parseXML: function(dataType,data){ 
				var isXml = this.isXMLDoc(data);
				return isXml?data:false; 
			},
			/**
			* 判断是否是xml格式
			* 如果是返回true， 否则返回false
			* @param obj:未知的数据类型
			*/
			isXMLDoc: function(obj){
				return $.isXMLDoc(obj);
			}
		}
	})

})(jQuery)

//声明一个数据处理实例
var dataProcesser = new $.DataProcesser();