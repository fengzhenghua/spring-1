(function($){
	
	/**定义一个值对象校验框架*/
	$.ValidateValueFrame = function(){
		this.settings = $.extend(true,{},$.ValidateValueFrame.defaults);
	};
	
	/**格式化提示信息输出*/
	$.ValidateValueFrame.format = function(name,source, params) {
		var arr = new Array(); 
		arr.push(name);
		if($.isArray(params)){
			$.merge(arr,params);
		}
		if (typeof params=="string" || typeof params=="number") {
			arr.push(params);
		} 
		$.each(arr, function(i, n) {
			source = source.replace(new RegExp("\\{" + i + "\\}", "g"), n);
		}); 
		return source;
	};
	
	/**为值校验框架扩展一些属性与方法*/
	$.extend($.ValidateValueFrame,{
		/**默认属性*/
		defaults: {
			elements: [], //存储所有等待校验的元素
			fieldErrors:[] //字段级校验失败信息
		},
		
		/*扩展一些方法*/
		prototype: {
			/**
			* 为指定元素的指定值进行规则绑定
			* @param element:绑定元素的校验规则
				var element = {
					labelName: labelName,
					fieldName: fieldName,
					fieldValue: fieldValue,
					fieldIndex: fieldIndex,
					jqObj: jqObj,
					fieldRules: {},
					ruleMessages: {
						required: "XXX"
					}
				}
			* @return this 当前校验对象，支持链式调用
			**/
			addRule: function(/*json*/element){
				if(!element)return this;
				/*1.优先判断是否存在必填，如果存在，则需要将必填放在第一位校验*/ 
				var rules = element.fieldRules;
				if(rules.required){ 
					var param = rules.required;
					delete rules.required;
					rules = $.extend({required: param}, rules);
					element.fieldRules = rules;
				}
				element.fieldValue = element.fieldValue?element.fieldValue:"";
				/*2.将对应的信息存储起来*/ 
				this.settings.elements.push(element);
				return this;
			},
			/*获取绑定的规则*/
			getRules: function(){
				return this.settings.elements;
			},
			/**
			* 根据指定字段获取一个元素规则
			* 如果能获取到则对象本身，否则返回false
			* @param options
			**/
			getRule: function(fieldName){
				var _this=this;
				var rules = _this.getRules();
				for(var i=0;i<rules.length;i++){
					var rule = rules[i];
					if(rule.fieldName==fieldName){
						return rule;
					}
				}
				return false;
			},
			/**
			* 为一个表单绑定值校验
			* @param options
			* options={
				title:"提示信息名称",
				formName: "表单中文名称，用于提示",	
				elements: [{}] //表单元素信息，包含校验规则
			}
			* return true/false:true表示校验通过
			**/
			validForm: function(/*JSON*/options){
				if(!options){
					alert("没有指定参数，无法绑定表单校验信息");
					return false;
				}
				var title = options.title?options.title:"校验信息提示";
				var formname = options.formName?options.formName:"";
				var elements = options.elements?options.elements:[];
				var _this=this;
				/*1.追加校验规则*/
				$.each(elements,function(index,element){
					_this.addRule(element);
				});
				/*2.执行校验*/
				_this.valid(); 
				/*3.获取校验失败信息*/
				var errors = _this.getErrors();
				/*4.封装成校验框架信息*/
				var validateFrame= new $.ValidateChannelFrame();
				var validateTab = new $.ValidateTab("1",formname); 
				var validateComponent = new $.ValidateComponent();
				validateComponent.addFormFields(errors);
				var componet = validateComponent.get();  
				validateTab.push(componet); 
				validateFrame.push(validateTab.get()); 
				/*5.执行框架级校验，如果校验不通过，则提示*/
				var pass=validateFrame.valid(); 
				if(pass)return true;
				validateFrame.info({
					title: title
				});
			},
			/**
			* 执行校验
			* 如果校验不通过，则返回false/成功true
			*/
			valid: function(){
				var _this=this;
				if(!_this.settings.elements.length)return true;
				$.each(_this.settings.elements,function(index,element){
					var valid = _this.check(element);
					if(!valid){
						_this.createError(element);
					}
				});
				return _this.settings.fieldErrors.length?false:true;
			},
			/*校验单个元素信息,返回当前元素对象*/
			validElement: function(element){
				var _this = this;
				var valid = _this.check(element);
				if(!valid){
					_this.createError(element);
				}
			},
			/*获取校验错误信息*/
			getErrors: function(){
				var errors = this.settings.fieldErrors;
				return errors;
			},
			/**
			* 生成单条错误信息，并进行存储
			* @param element 单个元素
			**/
			createError: function(element){
				if(!element)return;
				var _this=this;
				var validerrors = element.validerrors;
				$.each(validerrors,function(index,error){
					var field = {
						fieldName: element.fieldName,
						fieldType: element.fieldType,
						fieldIndex: element.fieldIndex,
						fieldInfo: error,
						jqObj: element.jqObj
					} ;
					_this.settings.fieldErrors.push(field);
				}) ;
			}, 
			/**
			* 校验单个元素的信息
			* @param element:单个元素校验对象
			* @return true/false 不通过为false
			**/
			check: function(/*json*/element){
				var _this=this;
				if(!element)return;
				/*存储该元素的所有校验错误信息*/
				element.validerrors = [];
				/*循环执行规则校验*/
				for(method in element.fieldRules){
					try{ 
						/*获取校验信息*/
						var rule = { method: method, parameters:element.fieldRules[method] };
						/*校验结果只允许返回true/false;或者是复杂的JSON = {VALID_FLAG: true/false; VALID_MESSAGE:'消息'}*/
						var validResult=$.ValidateValueFrame.methods[method].call(this,element.fieldValue,rule.parameters);
						/*执行校验规则，校验成功返回true*/
						var valid = true;
						var methodReturnMessage = "";
						if(typeof validResult == "boolean")valid= validResult; 
						else{
							/*如果后端返回的复杂格式，则根据校验标志位标志是否成功*/
							valid = validResult.VALID_FLAG;
							methodReturnMessage = validResult.VALID_MESSAGE?validResult.VALID_MESSAGE:"";
						}
						/*如果校验不成功，则格式化校验信息*/
						if(!valid){
							/*判断是否有自定义的消息*/
							var ruleMessages=element.ruleMessages?element.ruleMessages:{};
							var custMessage =  ruleMessages[method];
							/*如果有方法实现返回的消息，则以方法消息为准*/
							custMessage = methodReturnMessage && $.trim(methodReturnMessage)!=""?methodReturnMessage:custMessage;
							/*如果自定义消息存在，而且不为空，则显示自定义消息*/
							if(custMessage && $.trim(custMessage)!=""){
								element.validerrors.push(custMessage);
							}else{ 
								/*如果自定义消息不存在，则显示默认消息*/
								var message=_this.format(element.labelName,method,rule.parameters);
								element.validerrors.push(message);
							}
						} 
					}catch(ex){  
						alert("执行数据校验规则方法["+ method +"]出错，规则方法不存在或者执行报错");
						throw ex;
					}
				}
				/*如果存在校验不通过的信息，则标识当前元素校验不通过*/
				return element.validerrors.length?false:true;
			},
			/**
			* 格式化提示信息
			* @param name:元素名称
			* @param method：校验规则
			* @param parameters:对应的参数
			**/
			format: function(name,method,parameters) {  
				var source = $.ValidateValueFrame.messages[method];
				source=$.ValidateValueFrame.format(name,source,parameters); 
				return source;
			}
		},
		/*预定义信息提示模板*/
		messages: {
			required: "{0}不能为空", 
			email: "{0}不是有效的E-MAIL地址",
			url: "{0}不是有效的URL地址",
			date: "{0}不是有效的日期格式(yyyy-MM-dd)", 
			datetime: "{0}不是有效的时间格式(yyyy-MM-dd hh:mm:ss)", 
			lessdate: "{0}所选择的日期(时间)大于指定的日期[{1}]", 
			greaterdate:  "{0}所选择的日期(时间)小于指定的日期[{1}]", 
			number: "{0}不是有效的数字", 
			equalto: "{0}的值与{1}不相等",
			notequalto: "{0}的值与{1}相等",
			accept: "{0}文件后缀不是指定的({1})格式",
			maxlength: "{0}的长度或数量不能大于{1}",
			minlength: "{0}的长度或数量不能小于{1}",
			rangelength: "{0}的长度或数量必须在{1}到{2}之间",
			range: "{0}的值必须在{1}到{2}之间",
			max: "{0}的值不能大于{1}",
			min: "{0}的值不能小于{1}",
			postcode:"{0}不是有效的邮政编码", 
			regexp: "{0}的值与定义的格式不匹配",
			phonenumber:"{0}不是有效的电话号码",
			idcard: "{0}不是有效的身份证号码",
			decimal:"{0}与格式要求不匹配,如果是整数最大为{1}位;如果是小数则小数位最大为{2}位",
			rightexp:"{0}格式不正确",
			cnlength: "{0}输入的长度超过最大字节({1}个字节)限制(1个汉字占2字节)",
			cugroupacct: "{0}格式不正确，必须以@chinaunicom.cn结尾",
			remote: "{0}远程校验失败",
			remote2: "{0}远程校验失败",
			rcrequired: "{0}请选择一项",
			selectrequired: "{0}请选择一项",
			telenumber:"{0}不是有效的手机号码",
			notContainSpecChar:"{0}包含非法字符",
			onlyChineseChar:"{0}只能输入汉字",
			websiteRule:"{0}格式不正确",
			telOrPhone:"{0}无效的联系电话",
			rangeChlength: "{0}的长度或数量必须在{1}到{2}之间"
		},
		
		/*类方法集合，声明一些校验规则*/
		methods: {
		
			/**
			* 校验是否满足必填要求 
			* @param value:元素取值,单string或array
			* @param parameters:规则绑定设定的参数,必须为true
			* @return true/false:校验不通过为false
			**/
			required: function(value,paramters){
				if(!paramters)return true;
				var valid = $.isArray(value)? value.length > 0:$.trim(value).length > 0;
				return valid;
			},
			/**
			* 校验某组选最少必须选择的数量,如复选框至少要选择2个
			* @param value:元素取值,单string或array
			* @param parameters: 最小长度 数字
			* @return true/false:校验不通过为false
			**/
			minlength: function(value,paramters){
				var arr = $.isArray(value)?value:[];
				arr = typeof value == "string"?arr.push(arr):arr; 
				var valid = arr.length>=paramters;
				return valid;
			},
			/**
			* 校验某组选最多能选择的数量,如复选框最多只能选4个
			* @param value:元素取值,单string或array
			* @param parameters: 最大长度 数字
			* @return true/false:校验不通过为false
			**/
			maxlength: function(value,paramters){
				var arr = $.isArray(value)?value:[];
				arr = typeof value == "string"?arr.push(arr):arr; 
				var valid =arr.length<=paramters;
				return valid;
			},
			/**
			* 校验某组选的个数选择范围
			* @param value:元素取值,单string或array
			* @param parameters: 区间数组 [2,4]
			* @return true/false:校验不通过为false
			**/
			rangelength: function(value,paramters){
				/*当前输入的长度*/
				var vlength = $.trim(value).length;
				var arr = $.isArray(paramters)?paramters:[];
				var valid = vlength >= arr[0] && vlength<=arr[1];
				return valid;
			},
			/**
			* 校验某元素取值的最小值
			* @param value:元素取值,单string
			* @param parameters: 最小长度 数字
			* @return true/false:校验不通过为false
			**/
			min: function(value,paramters){ 
				/*如果值存在,则进行校验最小值校验*/ 
				var valid = $.trim(value).length?value >= paramters:true;  
				return valid;
			},
			/**
			* 校验某元素取值的最大值
			* @param value:元素取值,单string
			* @param parameters: 最大长度 数字
			* @return true/false:校验不通过为false
			**/
			max: function(value,paramters){
				/*如果值存在,则进行校验最大值校验*/ 
				var valid = $.trim(value).length?value <= paramters:true;  
				return valid;
			},
			/**
			* 校验某元素取值的是否在以个范围内
			* @param value:元素取值,单string
			* @param parameters: 最大长度 数字
			* @return true/false:校验不通过为false
			**/
			range: function(value,paramters){
				/*如果值存在，则进行校验*/ 
				var valid = $.trim(value).length?(value >= paramters[0] && value <= paramters[1]):true;  
				return valid;
			},
			/**
			* 校验元素的取值是否是email
			* @param value:元素取值,单string
			* @param parameters:true/false
			* @return true/false:校验不通过为false
			**/
			email: function(value,paramters){
				/*如果参数为false，表示不进行email格式校验*/ 
				if(paramters==false)return true;
				/*如果参数值存在，则进行校验*/
				var empty = $.trim(value).length?false:true;
				if(empty)return true;
				var valid = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test(value);
				return valid;
			},
			/**
			* 校验元素的取值是否是url
			* @param value:元素取值,单string
			* @param parameters:true/false
			* @return true/false:校验不通过为false
			**/
			url: function(value,paramters){
				/*如果参数为false，表示不进行email格式校验*/ 
				if(paramters==false)return true;
				/*如果参数值存在，则进行校验*/
				var empty = $.trim(value).length?false:true;
				if(empty)return true;
				var strRegex = "^(https|http|ftp|rtsp|mms)://" 
    				+ "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?"  
          			+ "(([0-9]{1,3}\.){3}[0-9]{1,3}(([\w_!~*'()-]+\.)*)?"   
          			+ "|" 
          			+ "([0-9a-z_!~*'()-]+\.)*" 
          			+ "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." 
          			+ "[a-z]{2,6})" 
          			+ "(:[0-9]{1,4})?" 
          			+ "((/?)|" // 
          			+ "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$"; 
          		var re=new RegExp(strRegex); 
				var valid =  (re.test(value))?true:false;				
				return valid;
			},
			/**
			* 校验元素的取值是符合日期格式YYYY-MM-DD
			* @param value:元素取值,单string
			* @param parameters:true/false
			* @return true/false:校验不通过为false
			**/
			date: function(value,paramters){
				if(paramters==false)return true;
				/*如果参数值存在，则进行校验*/
				var empty = $.trim(value).length?false:true;
				if(empty)return true;
				var re =/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/i;
				var valid =  (re.test(value))?true:false;		
				return valid;
			},
			/**
			* 校验元素的取值是符合日期格式YYYY-MM-DD hh:MM:ss
			* @param value:元素取值,单string
			* @param parameters:true/false
			* @return true/false:校验不通过为false
			**/
			datetime: function(value,paramters){
				if(paramters==false)return true;
				/*如果参数值存在，则进行校验*/
				var empty = $.trim(value).length?false:true;
				if(empty)return true;
				var re =/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\d):[0-5]?\d:[0-5]?\d$/i;
				var valid =  (re.test(value))?true:false;		
				return valid ;
			},
			/**
			* 校验指定日期值是否小于或等于目标参数日期,如小于或等于返回true,否则返回false
			* @param value:元素取值,单string，日期格式 yyyy-mm-dd 或者yyyy-mm-dd hh:mm:ss
			* @param parameters:目标时间 单string，日期格式 yyyy-mm-dd 或者yyyy-mm-dd hh:mm:ss
			* @return true/false:校验不通过为false
			**/
			lessdate: function(value,paramters){
				/*如果参数值存在，则进行校验*/
				var empty = $.trim(value).length?false:true;
				if(empty)return true;
				empty = $.trim(paramters).length?false:true;
				if(empty)return true;
				var d1 = new Date(value.replace(/-/g,"/"));  
				var d2 = new Date(paramters.replace(/-/g,"/"));  
				return Date.parse(d1)<=Date.parse(d2);
			},
			/**
			* 校验指定日期值是否大于或等于目标参数日期,如大于或等于返回true,否则返回false
			* @param value:元素取值,单string，日期格式 yyyy-mm-dd 或者yyyy-mm-dd hh:mm:ss
			* @param parameters:目标时间 单string，日期格式 yyyy-mm-dd 或者yyyy-mm-dd hh:mm:ss
			* @return true/false:校验不通过为false
			**/
			greaterdate: function(value,paramters){
				/*如果参数值存在，则进行校验*/
				var empty = $.trim(value).length?false:true;
				if(empty)return true;
				empty = $.trim(paramters).length?false:true;
				if(empty)return true;
				var d1 = new Date(value.replace(/-/g,"/"));  
				var d2 = new Date(paramters.replace(/-/g,"/"));  
				return Date.parse(d1)>=Date.parse(d2);
			},
			/**
			* 校验元素的取值是数字,包含负数
			* @param value:元素取值,单string
			* @param parameters:true/false
			* @return true/false:校验不通过为false
			**/
			number: function(value,paramters){
				if(paramters==false)return true;
				/*如果参数值存在，则进行校验*/
				var empty = $.trim(value).length?false:true;
				if(empty)return true;
				var re = /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/;
				var valid =  (re.test(value))?true:false;		
				return valid ;
			},
			/**
			* 校验元素的取值是否为指定的值
			* @param value:元素取值,单string
			* @param parameters:待比较的值
			* @return true/false:校验不通过为false
			**/
			equalto: function(value,paramters){
				/*如果参数值存在，则进行校验*/
				var empty = $.trim(value).length?false:true;
				if(empty)return true;
				return $.trim(value)==$.trim(paramters);
			},
			/**
			* 校验元素的取值是否不为指定的值
			* @param value:元素取值,单string
			* @param parameters:待比较的值
			* @return true/false:校验不通过为false
			**/
			notequalto: function(value,paramters){
				/*如果参数值存在，则进行校验*/
				var empty = $.trim(value).length?false:true;
				if(empty)return true;
				return $.trim(value)!=$.trim(paramters);
			},
			/**
			* 校验元素的取值是否为邮政编码
			* @param value:元素取值,单string
			* @param parameters:true/false
			* @return true/false:校验不通过为false
			**/
			postcode: function(value,paramters){
				if(paramters==false)return true;
				/*如果参数值存在，则进行校验*/
				var empty = $.trim(value).length?false:true;
				if(empty)return true; 
				var re = /^[0-9]\d{5}(?!\d)?$/;
				var valid =  (re.test(value))?true:false;		
				return valid ;
			},
			/**
			* 校验元素的取值是否为匹配正则表达式
			* @param value:元素取值,单string
			* @param parameters:待比较的值
			* @return true/false:校验不通过为false
			**/
			regexp: function(value,paramters){
				/*如果参数值存在，则进行校验*/
				var empty = $.trim(value).length?false:true;
				if(empty)return true;
				try{ 
					var re=new RegExp(paramters); 
					var valid =  (re.test(value))?true:false;				
					return valid;
				}catch(ex){}
				return true;
			},
			/**
			* 校验元素的取值是否为电话号码
			* @param value:元素取值,单string
			* @param parameters:true/false
			* @return true/false:校验不通过为false
			**/
			phonenumber: function(value,paramters){
				if(paramters==false)return true;  
				/*如果参数值存在，则进行校验*/
				var empty = $.trim(value).length?false:true;
				if(empty)return true;
				var re = /^((\d{11}|\d{12})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)$/i;
				var valid =  (re.test(value))?true:false;		
				return valid ;
			},
			/**
			* 附件上传可接收到文件类型
			* @param value:元素取值
			* @param parameters: 可接受的后缀参数 如:doc|xls|gif|jpg
			* @return true/false:校验不通过为false
			**/
			accept: function(value, paramters) {
				/*如果参数值存在，则进行校验*/
				var empty = $.trim(value).length?false:true;
				if(empty)return true;
				paramters = typeof paramters == "string" ? paramters.replace(/,/g, '|') :"png|jpg|gif|doc|xls|pdf|xlsx|docx";
				var result = value.match(new RegExp(".(" + paramters + ")$", "i"));  
				valid = result?true:false;   
				return valid;
			},
			/**
			* 对身份证号码的校验
			* @param value:元素取值
			* @param parameters: true/false
			* @return true/false:校验不通过为false
			**/
			idcard: function(value, paramters){
				if(paramters==false)return true;
				/*如果参数值存在，则进行校验*/
				var empty = $.trim(value).length?false:true;
				if(empty)return true; 
				/*实例化身份证校验对象*/
				var idcardVerify = new $.IdentityCardVerify(value);
				return idcardVerify.check();
			},
			/**
			* 解决appframe对Number(如： Number(8,2)处理出现的问题;)
			* @param value:元素取值
			* @param parameters: 整数位和小数位（如: 6/2,6为整数位，2为小数位）
			* @return true/false:校验不通过为false
			**/
			decimal:function(value,parameters){	
				if(parameters == false||parameters ==null){
					return true;
				}
				var empty = $.trim(value).length?false:true;
				if(empty){
				   return true; 
				}
				
				var valuearray = parameters;
				if(valuearray != null && valuearray.length == 2){
					ivalue = parseInt(valuearray[0]) + parseInt(valuearray[1]);
					dvalue = valuearray[1];
					realvalue = value;
					//验证整数位
					var reg = '/^\\d{1,'+ ivalue +'}$/';
					if(!eval(reg).test(value)){
						//如果有小数点，要求最多只能有2位
						var regxp='/^-?\\d+[.]?\\d{1,'+ dvalue +'}$/';
						if(!eval(regxp).test(value))return false;
					} 
					if(realvalue.indexOf('.') != -1){
	    				var regularExpression = '/^(-)?(\\d){0,' + parseFloat(ivalue-dvalue) + '}(\\.)(\\d){0,' +dvalue+'}$/';
	    				return eval(regularExpression).test(realvalue);
	    			}else{
	    				var regularExpression = '/^(-)?(\\d){0,' + parseInt(ivalue-dvalue) + '}$/';
	                    var result =  eval(regularExpression).test(realvalue);
	                    return result;
	    			}		
				}else{
					return false;
				}				
			},
			/**
			* 校验是否符合表达式规则。
			* @param value:元素取值
			* @param parameters: 正则表达式
			* @return true/false:校验不通过为false
			**/
			rightexp:function(value,parameters){	
				if(parameters == false||parameters ==null){
					return true;
				}
				var empty = $.trim(value).length?false:true;
				if(empty){
				   return true; 
				}
				realvalue = value;
				return eval(parameters).test(value);						
			},
			/**
			* 校验输入的字符长度是否超过数据库的限制。
			* 1个中文在ORACLE占2个字节
			* @param value:元素取值
			* @param parameters: ORACLE要求的最大长度
			* @return true/false:校验不通过为false
			**/
			cnlength: function(value,parameters){
				/*当前输入的长度*/
				var vlength = $.trim(value).length;  
				/*判断数据库要求的长度是多少*/
				var dlength = parameters?parameters:0;
				if(!dlength)return true;
				/*正则表达式判断输入文本中包含的中文长度*/
				var reg=/[\u4E00-\u9FA5]/g ;
				var cnArray = value.match(reg);
				var cnNum =cnArray?cnArray.length:0;
				/*按字节计算实际输入的长度*/
				var nCount = parseInt(vlength) + cnNum;
				/*判断实际的长度是否超出数据库的长度*/
				return nCount <= parseInt(dlength);
			},
			/**
			* 校验联通集团账号后缀是否是@chinaunicom.cn
			* @param value:元素取值,单string
			* @param parameters:true/false
			* @return true/false:校验不通过为false
			**/
			cugroupacct: function(value,paramters){
				/*如果参数为false，表示不进行校验*/ 
				if(paramters==false)return true;
				/*如果参数值存在，则进行校验*/
				var empty = $.trim(value).length?false:true;
				if(empty)return true;
				/*转换为小写*/
				var groupacct=$.trim(value).toLowerCase();
				if(groupacct.indexOf("@chinaunicom.cn")==-1){
					return false;
				}
				return true;
			},
			
			/**
			* 远程AJAX校验
			* @param value:元素取值,单string
			* @param parameters: json配置对象 
			* @return json: {VALID_FLAG: true/false; VALID_MESSAGE:'消息'}
			**/
			remote: function(value,paramters){
				/*如果参数值存在，则进行校验*/
				var empty = $.trim(value).length?false:true;
				if(empty)return true;
				/* 校验标志位*/
				var valid = true;
				var remoteMessage = "";
				try{
					/*判断参数*/
					var options = paramters?paramters:{};
					if(!options.url)return true;
					var valid_value = options.data.valid_value ;
					var value = options.data.value ;
					if(typeof(options.data.valid_value) != "undefined"){
						if(valid_value==value){
							return true ;
						}
					}
					/*执行AJAX同步校验*/
					$.ajax({
					   async: false, //false为同步
					   type: options.type,
					   url: options.url,
					   data: options.data?options.data:{},
					   success: function(transport){
							var data = transport?transport:{};
							var json=eval('('+data+')');
							if(data == '{}'){
								valid = true;
							}else{
								valid = false;
								remoteMessage = json.error_desc;
							}
						}
					});
				}catch(ex){throw ex}
				var data = {
					VALID_FLAG: valid,
					VALID_MESSAGE: remoteMessage
				} ;
				return data;
			},
			
			/**
			* 远程AJAX校验
			* @param value:元素取值,单string
			* @param parameters: json配置对象 
			* @return json: {VALID_FLAG: true/false; VALID_MESSAGE:'消息'}
			**/
			remote2: function(value,paramters){
				/*如果参数值存在，则进行校验*/
				var empty = $.trim(value).length?false:true;
				if(empty)return true;
				/* 校验标志位*/
				var valid = true;
				var remoteMessage = "";
				try{
					/*判断参数*/
					var options = paramters?paramters:{};
					if(!options.url)return true;
					var valid_value = options.data.valid_value ;
					var value = options.data.value ;
					if(typeof(options.data.valid_value) != "undefined"){
						if(valid_value==value){
							return true ;
						}
					}
					/*执行AJAX同步校验*/
					$.ajax({
					   async: false, //false为同步
					   type: options.type,
					   url: options.url,
					   data: options.data?options.data:{},
					   success: function(transport){
							var data = transport?transport:{};
							var json=eval('('+data+')');
							if(data == '{}'){
								valid = true;
							}else{
								valid = false;
								remoteMessage = json.error_desc;
							}
						}
					});
				}catch(ex){throw ex}
				var data = {
					VALID_FLAG: valid,
					VALID_MESSAGE: remoteMessage
				} ;
				return data;
			},

			
			/**
			* 判断选框是否被选择
			* @param value:元素选择的值
			* @param parameters: true/false
			* @return 
			**/
			rcrequired: function(value,paramters){
				if(value>0){ 
					return true ;
				} else {
					return false ;
				}
			},
			/**
			* 判断下拉框是否被选择
			* @param value:元素选择的值
			* @param parameters: 
			* @return 
			**/
			selectrequired: function(value,paramters){
				/*如果参数为false，表示不进行校验*/ 
				if(paramters==false)return true;
				if(value==""){
					return false ;
				}
				return true ;
			},
			/**
			* 校验元素的取值是否为手机号码
			* @param value:元素取值,单string
			* @param parameters:true/false
			* @return true/false:校验不通过为false
			**/
			telenumber: function(value,paramters){
				if(paramters==false)return true;  
				/*如果参数值存在，则进行校验*/
				var empty = $.trim(value).length?false:true;
				if(empty)return true;
				var re = /^0*(13|15|18)\d{9}$/;
				var valid =  (re.test(value))?true:false;
				return valid ;
			},
			/**
			* 校验输入框不能包含参数中指定的特殊字符
			* @param value:元素取值,单string
			* @param parameters:true/false
			* @return true/false:校验不通过为false
			**/
			notContainSpecChar: function(value,paramters){
				/*如果参数值存在，则进行校验*/
				var empty = $.trim(value).length?false:true;
				if(empty)return true;
				for (var ind=0;ind<paramters.length;ind++)
				{	var re;
					if(paramters[ind]=="\\"){
						if(value.indexOf("\\")!=-1){
							return false;
						}
					}else{
						re = "/^[^"+paramters[ind]+"]+$/";
						if(!(eval(re).test(value))){	
							return false;
						}
					}
				}
				return true;
			},
			/**
			* 校验输入框只能输入汉字
			* @param value:元素取值,单string
			* @param parameters:true/false
			* @return true/false:校验不通过为false
			**/
			onlyChineseChar: function(value,paramters){
				if(paramters==false)return true;  
				/*如果参数值存在，则进行校验*/
				var empty = $.trim(value).length?false:true;
				if(empty)return true;
				var re = /^[\u0391-\uFFE5]+$/;
				var valid =  (re.test(value))?true:false;
				return valid ;
			},
			/**
			* 校验网站地址格式
			* @param value:元素取值,单string
			* @param parameters:true/false
			* @return true/false:校验不通过为false
			**/
			websiteRule: function(value,paramters){
				if(paramters==false)return true;  
				/*如果参数值存在，则进行校验*/
				var empty = $.trim(value).length?false:true;
				if(empty)return true;
				var re = /http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
				var valid =  (re.test(value))?true:false;
				return valid ;
			},
			/**
			* 校验公司联系人电话格式（可为手机号码或者固话）
			* @param value:元素取值,单string
			* @param parameters:true/false
			* @return true/false:校验不通过为false
			**/
			telOrPhone: function(value,paramters){
				if(paramters==false)return true;  
				/*如果参数值存在，则进行校验*/
				var empty = $.trim(value).length?false:true;
				if(empty)return true;
				var re1 = /^((\d{11}|\d{12})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)$/i;
				var valid1 =  (re1.test(value))?true:false;
				var re2 = /^0*(13|15|18)\d{9}$/;
				var valid2 =  (re2.test(value))?true:false;
				return (valid1 || valid2);
			},
			/**
			* 校验某组选的个数选择范围(包含中文英文等字符)
			* @param value:元素取值,单string或array
			* @param parameters: 区间数组 [2,4]
			* @return true/false:校验不通过为false
			**/
			rangeChlength: function(value,paramters){
				/*当前输入的长度*/
				var vlength = 0;
				var arraystr = $.trim(value).split("");
				for (var i=0;i<arraystr.length;i++) {
				 if (arraystr[i].charCodeAt(0)<299) {
				  vlength++;
				 } else {//中文
				  vlength+=2;
				 }
				}
				var arr = $.isArray(paramters)?paramters:[];
				var valid = vlength >= arr[0] && vlength<=arr[1];
				return valid;
			}
		}
	}); 
})(jQuery);

/**
* 定义一个符合大陆身份证号码规则的校验类
* author: wangyong
**/
(function($){
	
	/**
	*定义一个值对象校验框架
	*@param idcardNo：身份证号码
	*/
	$.IdentityCardVerify  = function(/*String*/idcardNo){
		this.settings = $.extend(true,{},$.IdentityCardVerify.defaults);
		this.idcardNo = idcardNo;
	};
	
	/**扩展一些方法与属性*/
	$.extend($.IdentityCardVerify,{
		/**扩展属性*/
		defaults: {
			/*身份证全国省份编码*/
			VCITIES: { 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",
	        	21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",
	        	33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",
	        	42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",
	        	51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",
	        	63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"
           }
		},
		/**扩展方法*/
		prototype: {
			/**校验入口方法,校验成功返回true,否则为false*/
			check: function(){
				var pass = this.isCardNo() &&  this.checkProvince() && this.checkBirthday();
				return pass;
			},
			/**
			* 校验号码是否符合规范
			* 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
			**/
			isCardNo: function(){ 
				var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/; 
				return reg.test(this.idcardNo)?true:false;
			},
			
			/**
			* 取身份证前两位,校验省份
			**/
			checkProvince: function(){
				var province = this.idcardNo.substr(0,2); 
				var pass = this.settings.VCITIES[province]?true:false;
				return pass;
			},
			
			/**
			* 检查生日是否正确
			*/
			checkBirthday: function(){
				var card = this.idcardNo;
				var len = card.length;
				//身份证15位时，次序为省（3位）市（3位）年（2位）月（2位）日（2位）校验位（3位），皆为数字
				if(len == '15'){
					var re_fifteen = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/; 
					var arr_data = card.match(re_fifteen);
					var year = arr_data[2];
					var month = arr_data[3];
					var day = arr_data[4];
					var birthday = new Date('19'+year+'/'+month+'/'+day);
					return this.verifyBirthday('19'+year,month,day,birthday);
				}
				//身份证18位时，次序为省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位），校验位末尾可能为X
				if(len == '18'){
					var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
					var arr_data = card.match(re_eighteen);
					var year = arr_data[2];
					var month = arr_data[3];
					var day = arr_data[4];
					var birthday = new Date(year+'/'+month+'/'+day);
					return this.verifyBirthday(year,month,day,birthday);
				}
				return false;
			},
			/**
			* 检查生日的日期是否正确
			* @param year:年
			* @param month：月
			* @param day：日
			* @param birthday：生日
			*/
			verifyBirthday: function(year,month,day,birthday){
				var now = new Date();
				var now_year = now.getFullYear();
				//年月日是否合理
				if(birthday.getFullYear() == year && (birthday.getMonth() + 1) == month && birthday.getDate() == day){
					//判断年份的范围（3岁到100岁之间)
					var time = now_year - year;
					if(time >= 3 && time <= 100){
						return true;
					}
					return false;
				}
				return false;
			}, 
			/*校验位的检测*/
			checkParity: function(){
				var card = this.idcardNo;
				//15位转18位
				card = this.changeFivteenToEighteen(card);
				var len = card.length;
				if(len == '18'){
					var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); 
					var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'); 
					var cardTemp = 0, i, valnum; 
					for(i = 0; i < 17; i ++) { 
						cardTemp += card.substr(i, 1) * arrInt[i]; 
					} 
					valnum = arrCh[cardTemp % 11]; 
					if (valnum == card.substr(17, 1)) {
						return true;
					}
					return false;
				}
				return false;
			},
			/**15位转18位身份证号*/
			changeFivteenToEighteen: function(){
				var card = this.idcardNo;
				if(card.length == '15'){
					var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); 
					var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'); 
					var cardTemp = 0, i;   
					card = card.substr(0, 6) + '19' + card.substr(6, card.length - 6);
					for(i = 0; i < 17; i ++){ 
						cardTemp += card.substr(i, 1) * arrInt[i]; 
					} 
					card += arrCh[cardTemp % 11]; 
					return card;
				}
				return card;
			}
		}
	});
})(jQuery);

/**
* 实时校验信息控制类
**/
(function($){
	/*类的定义*/
	$.ValidateController =  function(options){
		this.settings = $.extend( true, {}, $.ValidateController.defaults,options?options:{});
	} ;
	/*扩展一些属性*/
	$.extend($.ValidateController,{
		/*默认属性*/
		defaults: {
			SELECTOR_VALIDFORM_CHECKTIP : ".Validform_checktip",//校验结果信息显示区域选择器
			MESSAGE_SPAN_NAME: "_message_span_",//提示信息对应的SPAN名称
			CLASS_VALIDFORM_CHECKTIP : "Validform_checktip",//校验信息区域样式
			CLASS_VALIDFORM_WRONG : "Validform_wrong Validform_checktip", //校验失败信息样式
			CLASS_VALIDFORM_RIGHT: "Validform_right Validform_checktip" //校验成功信息样式
		},
		/*继承一些属性方法*/
		prototype: {
			/**
			* 从部件校验信息中获取指定的字段的校验信息
			* 如果存在校验失败信息，则显示出来，如果校验成功，则显示成功
			* @param valiValueFrame: 绑定了校验规则的对象
			* @param fieldName: 对应的字段名称 
			**/
			processField: function(valiValueFrame,fieldName,status){
				/*1.获取所有规则*/
				var element = valiValueFrame.getRule(fieldName);
				/*2.如果元素不存在，则不处理*/
				if(!element)return;
				/*3.执行单个元素校验，获取校验信息*/
				valiValueFrame.check(element);
				var validerrors = element.validerrors;
				/*4.判断校验信息是否存在，如果不存在，则表示校验成功*/
				var valid = true;
				var error = "";
				var jqObj = element.jqObj;
				if(validerrors.length){
					valid = false;
					error = validerrors.join(",");
				}
				/*5.进行信息处理*/
				if(valid){
					/*5.1 如果校验成功，则显示成功*/
					if(status==null||status=='1'){
						this.showRight(jqObj);
					} else if(status=='2'){
						this.showRightNothing(jqObj);
					}
				}else{
					/*5.2 如果校验失败，则显示失败信息*/ 
					this.showError(jqObj,error);
				}
			},
			/**
			* 实时校验错误信息
			* @param obj: 表单控件对象
			* @param error:表单校验错误信息
			**/
			showError: function(obj,error){
				var messageObj = this.getMessageObj(obj); 
				if(messageObj){
					messageObj.removeClass().addClass(this.settings.CLASS_VALIDFORM_WRONG).text(error.toString().split(",")[0]);
				}
			},
			/**
			* 校验成功-显对号图片
			* @param obj: 表单控件对象 
			**/
			showRight: function(obj){
				var messageObj = this.getMessageObj(obj);
				if(messageObj)messageObj.removeClass().addClass(this.settings.CLASS_VALIDFORM_RIGHT).text("");
			},
			/**
			* 校验成功-不显示效果
			* @param obj: 表单控件对象 
			**/
			showRightNothing: function(obj){
				var messageObj = this.getMessageObj(obj);
				if(messageObj)messageObj.removeClass().text("");
			}, 
			/**
			* 在指定的表单对象后面创建一个校验信息样式
			* @param obj:对应的文本框对象
			**/
			getMessageObj: function(obj){
				/*1.判断当前对象是否存在，如果不存在则返回*/
				var jqObj = $(obj);
				if(!jqObj.length)return; 
				/*2.判断当前对象的下一节点是否存在，如果不存在，返回*/
				var nextObj = jqObj.nextAll("[name='"+ this.settings.MESSAGE_SPAN_NAME +"']");
				if(!nextObj.length){
					jqObj.after("<span name='"+ this.settings.MESSAGE_SPAN_NAME +"' class='"+ this.settings.CLASS_VALIDFORM_CHECKTIP +"'></span>");
					nextObj = jqObj.next();
				}
				return nextObj;
			}
		}
	});
	
})(jQuery);
