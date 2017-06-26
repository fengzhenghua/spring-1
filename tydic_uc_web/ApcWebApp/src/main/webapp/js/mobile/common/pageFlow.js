var PAGE_FLOW = {
		flow:"",
		step:"",
		href:"",
		flag:false,
		pageFlowReady:undefined,
		//通常用于进行一些点击下一步之前的一些检查,如果设置需要返回true和false,回调函数  入参传入flow,step
		beforeClick:undefined,
		//自己触发按钮事件,只进行js内部处理，不调用android程序,传入flow,step,href
		doSelfJsClick:undefined,
		init:function(options){
			this.flow = options.flow;
			this.step = options.step;
			this.href = options.href;
		},
		setPageFlowReady:function(readyClick){
			if(typeof readyClick == "function" ){
				this.pageFlowReady = readyClick;
			}			
		},
		setBeforeClick:function(beforeClick){
			if(typeof beforeClick == "function" ){
				this.beforeClick = beforeClick;
			}			
		},
		setSelfDoJsClick:function(doSelfJsClick){
			if(typeof doSelfJsClick == "function" ){
				this.doSelfJsClick = doSelfJsClick;
			}			
		},
		initHeadButton:function(pre_fix){
			var message = "";
			if(pre_fix == "oc"){
				message = "是否要退出开户?";
			}			
			$("#head_buttons .top_left").unbind("click").bind("click", function () {
				var pre_url = HTML_UTLS.getParam("pre_url");
				if(pre_url==undefined||pre_url==0){				
					DIALOG_UTIL.showTypeDialog("confirm",message,function(type){
						/*if(type == "1"){
							window.location.href =  "opening.html?" + HTML_UTLS.getParamsDataStr({});
						}*/
						if(window.WebViewJavascriptBridge){
							if(window.WebViewJavascriptBridge.callMotion){
								window.WebViewJavascriptBridge.callMotion("gohome","");
							}
						}
					});
				}else{
					 var next_step = {
								"step":(HTML_UTLS.getParam("step")-1)==0?1:HTML_UTLS.getParam("step")-1,
								"pre_url":HTML_UTLS.getParam("step")-2>0?flowData[HTML_UTLS.getParam("step")-2]["cur_url"]:0
					};
					 //console.info("flowData="+JSON.stringify(flowData));
					 var href = pre_url+"?" + HTML_UTLS.getParamsDataStr(next_step);
						try{
							console.info("tele_type_new="+tele_type_new); 
							if(tele_type_new!=null&&tele_type_new!=''){
								var tele_type =  HTML_UTLS.getParam("tele_type");
								href=href.replace("&tele_type="+tele_type,"");
								href+="&tele_type="+tele_type_new;
							}
						}catch(e){
							
						} 
					 
					window.location.href = href;
				}
			});			
			$("#head_buttons .top_right").unbind("click").bind("click", function () {
				DIALOG_UTIL.showTypeDialog("confirm",message,function(type){
					if(type == "1"){
						if(window.WebViewJavascriptBridge){
							if(window.WebViewJavascriptBridge.callMotion){
								window.WebViewJavascriptBridge.callMotion("gerenxinxi","");
							}
						}
					}
				});	
			});
		}
};

var flowData = '';
//html后面需要拼传参例子  open.html?flow=phone&step=1
$(document).ready(function(){
	pageFlowInit();
});

function pageFlowInit(){
	var flow =HTML_UTLS.getParam("flow");
	if(!flow){
		return;
	}
	var arrays = flow.split("_");
	var pre_fix = arrays[0];
	PAGE_FLOW.initHeadButton(pre_fix);
	var step = HTML_UTLS.getParam("step");
	//YUN-415 GX_20150110_统一版增加相关权限控制功能_杨枝蕃
	var pre_fix_province = pre_fix;
	var province_code = HTML_UTLS.getParam("province_code");
	//如果是开户页面 且为广西，统计菜单操作记录
	if(pre_fix == "oc" && province_code == "gx" && step =="1"){	
		//统计菜单操作记录
		operateRecord(flow);
		}
	
	//根据省份代码确定下标
	if (province_code!=undefined && province_code!="undefined" && province_code!="") {
		pre_fix_province = pre_fix + "_" + province_code;
	}
	
	$.ajax({
		url:"../../../../js/common/json/pageFlowUrl.json",
		async:true,
		type:"GET",
		dataType:"json",
		success:function(data, textStatus, jqXHR){
			var url = data[pre_fix_province];
			initPageFlow(url);
		}
	});	
	//初始化
	function initPageFlow(post_url){
		var flow =HTML_UTLS.getParam("flow");
		var step = HTML_UTLS.getParam("step");
		//下一步next_step_id用于注册下一步id
		var next_step_id = "next_flow_step";//"phone_next";
		//提示信息
		var hint_id = "hint_desc";
		
		$.ajax({
			url:post_url,
			async:true,
			type:"GET",
			dataType:"json",
			success:function(data, textStatus, jqXHR){
				var hints = data.hints;;
				if(flow){
					flowData = data[flow];
					if(step){
						console.info(step);
						var stepData = flowData[step];	
						console.info(stepData);
						var next_url = stepData["next_url"];//页面中的下一步url
						var cur_url = stepData["cur_url"];
						var hint = hints[cur_url];//页面中的提示
						var pre_step = stepData["pre_step"];//上一步
						var next_step = stepData["next_step"];//下一步
						var call_background = stepData["call_background"];
						var win_obj = stepData["win_obj"];
						var func_name = stepData["func_name"];
						var handler = stepData["handler"];//当做参数传入function中
						//添加尾部提示信息
						var weiba = '<div class="arrow"></div>';
						$("#msg_box").html(hint + weiba);
						
						$("#pop_message").unbind("click").bind("click", function () {
							var display = $("#msg_box").css("display"); 
							if(display == "block"){
								$("#msg_box").hide();
							}else{
								$("#msg_box").show();	
							}

						});
						
						//如果下一步为空,不进行下一步逻辑						
						var next_step_json = {
											"flow":flow,
											"step":next_step,
											"pre_url":next_step-1>0?flowData[next_step-1]["cur_url"]:0
										};
						var href = next_url + "?"+ HTML_UTLS.getParamsDataStr(next_step_json);
						try{
							console.info("tele_type_new="+tele_type_new); 
							if(tele_type_new!=null&&tele_type_new!=''){
								var tele_type =  HTML_UTLS.getParam("tele_type");
								href=href.replace("&tele_type="+tele_type,"");
								href+="&tele_type="+tele_type_new;
							}
						}catch(e){
							
						}
						//初始化PAGE_FLOW
						PAGE_FLOW.init({"flow":flow,"step":step,"href":href});
						//注册下一步点击事件
						$("#"+next_step_id).unbind("click").bind("click", function () {
							
							var beforeClickFlag = false;
							DIALOG_UTIL.showTypeDialog("loading","");
							
							if(PAGE_FLOW.beforeClick){						
								beforeClickFlag = PAGE_FLOW.beforeClick(flow,step);
							}else{
								//如果未设置则进行下一步,默认通过
								beforeClickFlag = true;
							}
							if(beforeClickFlag){	
								//如果设置了进行自己的click事件则传入三个参数flow,step,href
								if(PAGE_FLOW.doSelfJsClick){
									if(call_background == "true"){
										PAGE_FLOW.doSelfJsClick(flow,step,href,win_obj,func_name,handler);
									}else{
										PAGE_FLOW.doSelfJsClick(flow,step,href);
									}								
								}else{
									//没有设置自己调用函数则直接跳转

									if(call_background == "true"){
										if(window[win_obj]){
											if(window[win_obj][func_name]){			
												var funcData = {
													flow:flow,
													step:step,
													href:href
												};
												window[win_obj][func_name](handler,JSON.stringify(funcData));
											}
										}
									}else{
										window.location.href = href;
									}					
								}
							}else{
								 DIALOG_UTIL.hideDialog("","loading");
							}
						});
						//回调加载完成函数如果设置了的话
						if(PAGE_FLOW.pageFlowReady){
							PAGE_FLOW.pageFlowReady(flow,step,href);
						}
						
					}
				}
				
			},
			error:function(data){
				console.info(data);
			}
		});	
		}
	}

function operateRecord(args){
	var jsessionid =HTML_UTLS.getParam("jsessionid");
	var date =getNowFormatDate();
	var myData ={
		jsessionid:jsessionid,
		page_element:args,
		oper_date:date
	}
	
	$.ajax({
		url:URLS.URL_API_HOST +"rest/operateRecord/menuOperateRecord",
		type:"post",
		data:myData,
		dataType:"json",
		success:function(data){
			console.info("统计:"+data.type);
			console.info(data.args);
		}
	});	
	
}

function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}

