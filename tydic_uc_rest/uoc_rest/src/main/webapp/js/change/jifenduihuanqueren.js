/*依赖：\uss_web\src\main\webapp\js\change\chargeItemCommon.js */
/********************费用相关信息***********************************************************************************/
var exchangeScorechargeInfo ={
    	m_jessionid:window.parent.application.jsessionid+"PC",
		chargeItems : [],
		originChargeItems:[],
		totalFee : 0,
		disChargeItems:[],
		totalRawFee:0,
		serv_code:"exchangeScore",//公用一下，费用都是1000块可减免到0 而且必须减免都0
		getChargeItemsOfUrl:"rest/getFee/zonghebiangeng"
	};
//初始化费用信息
CHARGE.chargeInfo=exchangeScorechargeInfo;
//获取费用
CHARGE.getChargeItems();

/********************费用相关信息end***********************************************************************************/
/********************返档相关信息***********************************************************************************/
function jifendingdangtijiao() {   
	
	
    //订单更新接口开始
    var returnFlag = false;
    var jsonObj = {  
			"contact_address":$("#contact_address").val(),
			"contact_phone":$("#contact_phone").val(),
			"brand":CUSTOMER_INFO_SEARCH.returndata[0].devices_brand,
			"tele_type":CUSTOMER_INFO_SEARCH.returndata[0].tele_type,
			"brand_code":ruleInfoScore.selecteditem.brand_code,
			"ofr_name":ruleInfoScore.selecteditem.ofr_name,
			"change_order_type":"6",
			"acc_nbr":CUSTOMER_INFO_SEARCH.returndata[0].device_number,
			"device_number":CUSTOMER_INFO_SEARCH.returndata[0].device_number,
			"fee_info": [] ,
			"cust_seq_id":PC_IDREADER.pc_idreader_info.cust_seq_id                   
    };
    var fee_info_new = [];
    //for循环为订单更新添加内容
    for (var i = 0; i < exchangeScorechargeInfo.chargeItems.length; i++) {
        //服务器费用只支持字符
        var fee_result = {
            "fee_code": exchangeScorechargeInfo.chargeItems[i].fee_code,
            "fee_class": exchangeScorechargeInfo.chargeItems[i].fee_class,
            "fee_name": exchangeScorechargeInfo.chargeItems[i].fee_name,
            "rec_amount": exchangeScorechargeInfo.chargeItems[i].raw_amount + "",
            "discount_flag":"1",
            "rel_amount": "0"//全部减免
        };
        jsonObj.fee_info.push(fee_result);
    }
    //订单更新
    ORDER_UPDATE.doDirUpdateOrder(jsonObj, false, function () {
	     	returnFlag = sendHttpSq({"jsessionid":window.parent.application.jsessionid+"PC","order_id":ORDER_UPDATE.order_id,"change_order_type":"6"});
    });

}; 
/**********************************************************************/
//显示订单信息
console.log("积分兑换！queren");
	jifenshowOrderqueren();
	$("#jifenquerendingdanpre").click(jifenquerendingdanpre);
	$("#jifenquerendingdannext").click(jifenquerendingdannext);
	//上一步  
	function jifenquerendingdanpre(){
		$("#jifenduihuanpage").show();		
		$("#jifenquerendingdanpage").hide();
	}	
	function jifenquerendingdannext(){
		//更新订单
	     	/*var post_data = {"jsessionid":this.jsessionid,	
							"order_id":this.order_id,							
							"order_json":order_json	
				};*/
	    jifendingdangtijiao();
	       
	}
	//读写卡页面
	function showReadAndwriteCardPage(){
		$.ajax({
			url:window.parent.application.fullPath+"html/yxs_pctongyi/biangengfangdang/buka/readAndWriteCard.html",
			//global: false,
		    type: "GET",
		    dataType: "html",
		    async:false,
		    success: function(data,textStatus){			    	
		    	$("#xiaodipaihanban").append(data);	
		    	$("#gonggongquerendingdanpage").hide();
		    	//console.log($("#xiaodipaihanban").html())
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
	
	//显示订单信息
	function jifenshowOrderqueren(){
		var order_info=CUSTOMER_INFO_SEARCH.customer_info;
		$("#customer_name_order").html(order_info.customer_name);
		$("#id_number_order").html(order_info.id_number);
		$("#phone_number_order").html(order_info.device_number);
		$("#busi_type_order").html("积分兑换");
		$("#duihuanxianmu").html(ruleInfoScore.selecteditem.ofr_name);
	}
	/********************************************************/
	/*提交兑换积分*/
	function submitExchangescore(){
		if (baseInfo.deviceNumber=='' || baseInfo.deviceNumber==null 
			||baseInfo.brand_code=='' || baseInfo.brand_code == null){
			 alert("请先选择兑换项目");
			 return;
			}
		DIALOG_BASE.showDialog('loading');
		 WebUtil.doPost(URLS.URL_API_HOST + 'rest/integratedChange/ConfirmExchangeInfoScore', {
			 	jsessionid: baseInfo.jsessionid,
			 	device_number: baseInfo.deviceNumber,
		        product_id:baseInfo.brand_code,//实际上需要传的值是baseInfo.brand_code
		        ofr_name:baseInfo.ofr_name //dyh add 20151015
		    }, true, $.extend(baseInfo.registClient, {
		        onComplete: function (args) {
		        	 console.log("兑换积分成功");
		        	 DIALOG_BASE.hideDialog('loading');
		        	 var exchangeInfoScore = args.score_res;
		        	 DIALOG_UTIL.showTypeDialog("info","积分兑换成功");
		        },
		        onError: function (error) {
		        	DIALOG_BASE.hideDialog('loading');
		        	console.log(error.content);
		            console.log(error.type);
		            DIALOG_UTIL.showTypeDialog("info"," 错误: " + error.content);
		            /**
		             * 出现业务错误的时候 error为json对象包含error.type成功失败标志(error)  error.content失败信息
		             */
		        },
		        onException: function (status, errorInfo, hint) {
		        	DIALOG_BASE.hideDialog('loading');
		        	 DIALOG_UTIL.showTypeDialog("info","积分兑换失败");
		            console.log(status);
		            console.log(errorInfo);
		            /**
		             * 出现异常时候调用
		             * status:状态码:404 500等
		             * errorInfo:状态码对应的具体信息
		             * hint:提示信息
		             */
		        }
		    
		    }));
		
	}
	/********************************************************///