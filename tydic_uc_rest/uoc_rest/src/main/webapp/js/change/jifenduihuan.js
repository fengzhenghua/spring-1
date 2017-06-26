/** ----------------------页面初始化begin------------------------------*/
/**
 * 页面显示数据查询
 * @param cust_seq_id String
 */
var ruleInfoScore={};
console.log("积分兑换！");
$("#scoreList li").bind("click",clickExchangeScoreItem);
var getDataparam={"jsessionid":window.parent.application.jsessionid+"PC","ofr_type":"300"};
getData(getDataparam);
jifenshow();

//显示信息
function jifenshow(){
	$("#exchangeScore_customer_name").html(CUSTOMER_INFO_SEARCH.returndata[0].cust_name);
	$("#current_balance").html(CUSTOMER_INFO_SEARCH.returndata[0].current_balance);
	$("#phone_number").html(CUSTOMER_INFO_SEARCH.customer_info.device_number);
}
function getData(myparam,getDatacallBack) {
	console.log("积分兑换：获取数据");
	var ofr_type="300";
	$("#scoreList").html("");
	var flag = false;
	if (window.parent.isNullOrEmpty(myparam.jsessionid)){
		$.alert("未登录");
		return flag;
	}	
	var registClient = {
			onComplete: function(message) {

				if(message.type=="success"){
					
					if (window.parent.isNullOrEmpty(message.args.exchange_info_score)){
						console.info("获取积分及其兑换项目数据：无数据！");
					}else{
						console.info("获取积分吧兑换数据成功！");
						ruleInfoScore = message.args.exchange_info_score;
						createScoreList(ruleInfoScore,"score");
						flag=true;						
					}
					if(!window.parent.isNullOrEmpty(getDatacallBack) ){
						console.info("获取积分及其兑换项目:callback");
						flag=getDatacallBack();
						return flag;
					}else{
						return flag;
					}
	  			}else{
	  				console.info("获取积分及其兑换项目数据失败！");
	  				return false;
	  			}
			},
			onError: function(XMLHttpRequest, textStatus,errorThrown) {
				var status = XMLHttpRequest.status;//未显示
				var hint = "网络繁忙，请稍后再试!";//通用万金油提示，都懂得。         
				var dialog=$.dialog({
					   title:'提示',//提示title
					   content:'返档权限:'+hint,//显示的内容，支持html格式。
					   buttons:[{id:'btn_ok',text:'确定',
						   onClick:function(){					   
							   dialog.close();
						   }//点击时候回调函数
					   }]
				});
			}
	};
    var postData={
    		"jsessionid":myparam.jsessionid,       		
    		"ofr_type":myparam.ofr_type};

	$.ajax({
  		type:"POST",
  		url:application.fullPath + 'rest/sale/getExchangeInfoScore',
  		data:postData,  
  		//默认同步
  		async:false,
  		waitMsg:"请稍等......",
  		success:registClient.onComplete,				  		
  	    error:registClient.onError				  		
	});
	return flag;
};


/*创建积分页面显示数据*/
function createScoreList(infoScoreList,page_id){
	var id = "";
	var bindFunction = null;
	if(page_id == "score"){
		id = "scoreList";
		bindFunction = clickExchangeScoreItem;
	}
	var listUl = $("#"+ id);
	listUl.html('');
	/*
	 * <div class="padding_blank10"></div>
            <li class="list">
                <div class="left"><div class="left_lable bold">可兑换项目</div></div>
            </li>
            <li class="list">
                <div class="left">
                	<span class="red">注：兑换项目次月生效</span>
                </div>
            </li>
            */
	var li_1 = '<div class="padding_blank10"></div><li class="list">';
	li_1 = li_1 +'<div class="left"><div class="left_lable bold">可兑换项目</div></div>';
	li_1 = li_1 + '</li>';
	listUl.append(li_1);
	var length = infoScoreList.length;
	/*            
	 * <li class="list">
                <div class="left width_20">
                	<div class="left_lable">500MB省内闲时流量</div>
                </div>
                <div class="left width_15">
                    <div class="left_data width_8">486积分</div>
                    <div class="left_data"><div class="radio_checked"></div></div>
                </div>
            </li>
            */
	for (var i = 0; i < length; i++) {
		var infoScore = infoScoreList[i];
		var data = JSON.stringify(infoScore);
		var li = "";
		var score = infoScore.ofr_name + "("+ infoScore.market_price +")";
		li  =     '<li class="list"  data=\''+data+'\'>';
		li = li	+				'<div class="left width_20">' ;
		li = li +					'<div class="left_lable">'+infoScore.ofr_name + '</div>';
		li = li +				'</div>';
		li = li +				'<div class="left width_15">';
		li = li +					'<div class="left_data width_8">'+infoScore.market_price+'积分</div>';
		li = li +					'<div class="left_data"><div name="jifenduihuantiaomu" class="radio"></div></div>';
		li = li +				'</div>';
		li = li +'</li>';
		listUl.append(li);
		//console.info("data="+data);
	}
	listUl.append('<div class="padding_blank10"></div>');

	//绑定点击时间
	$("#scoreList li").bind("click",bindFunction);
	//console.info("execute");
};

/*积分点击事件*/
function clickExchangeScoreItem(){
	if ($(this).index() ==0)
	{
		return false;
	}
	$("[name='jifenduihuantiaomu']").removeClass("radio_checked");
	$("[name='jifenduihuantiaomu']").addClass("radio");
	$(this).find("[name='jifenduihuantiaomu']").removeClass("radio");
	$(this).find("[name='jifenduihuantiaomu']").addClass("radio_checked");
	var data= $(this).attr("data");
	var jsonData = JSON.parse(data);
	ruleInfoScore.selecteditem = jsonData;
	console.info("jsonData="+jsonData);
	console.info("jsonData="+jsonData.brand_code);
	$("#jifenduihuanpagenext").attr("class","ok left_lable");
};
/** ----------------------页面初始化end------------------------------*/
/** ----------------------页面跳转begin------------------------------*/

$("#jifenduihuanpagepre").click(jifenduihuanpagepre);
//上一步   jifenduihuanpagenext jifenquerendingdanpage
function jifenduihuanpagepre(){
	console.info("jifenduihuanpagepre");
	$("#biangengreaderpage").show();		
	$("#jifenduihuanpage").hide();
}
$("#jifenduihuanpagenext").click(jifenduihuanpagenext);
//下一步  订单确认 
function jifenduihuanpagenext(){
	//身份证校验不通过不能下一步
	/*if(PC_IDREADER.pc_idreader_info.next_flag==false){
		return;
	}*/
	//先一步执行前 业务逻辑 
	if(!jifenduihuanpageNextBefor()){
		
		return;
	}
	jifenduihuannextpageload(jifenduihuanpageNextAfter);
	
	$("#jifenduihuanpage").hide();
};
function jifenduihuannextpageload(pageLoadcallBack){
	//缺省跳转到订单确认页面
	         if($("#jifenquerendingdanpage").length > 0){
		 			$("#jifenquerendingdanpage").show();
		 	 }else{
		    	 $.ajax({
						url:window.parent.application.fullPath+"html/yxs_pctongyi/biangengfangdang/exchangeScore/jifenquerendingdan.html",
						//global: false,
					    type: "GET",
					    dataType: "html",
					    async:false,
					    success: function(data,textStatus){			    	
					    	$("#xiaodipaihanban").append(data);	
					    	//console.log($("#xiaodipaihanban").html())
					    	if(pageLoadcallBack){
					    		pageLoadcallBack();
					    	}
					    },
					    error: function (XMLHttpRequest, textStatus,errorThrown) {
			               var status = XMLHttpRequest.status;
			               //var errorInfo = HTTP_ERROR_STATUES[status];
			               var hint = "网络繁忙，请稍后再试!";//通用万金油提示，都懂得。                
			               var error = hint + ",后台接口报错!";
			               $.alert(errorThrown);
			           }
					});
		 	 }	
};
//处理我业务校验等问题：
function jifenduihuanpageNextBefor(){ 	
	
	 return jifenverifyFormToqueren(); 
	//return true;
};
function jifenduihuanpageNextAfter(){ 	
	     	loadjavascript(window.parent.application.fullPath+"js/change/chargeItemCommon.js",
	 	 			function (){loadjavascript(window.parent.application.fullPath+"js/change/jifenduihuanqueren.js")}
	 	 	 ); 	       
};
/** ----------------------页面跳转end------------------------------*/