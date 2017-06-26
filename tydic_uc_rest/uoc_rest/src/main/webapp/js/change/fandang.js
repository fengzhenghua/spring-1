/*依赖：\uss_web\src\main\webapp\js\change\chargeItemCommon.js */
/********************费用相关信息***********************************************************************************/
var fangdangchargeInfo ={
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
CHARGE.chargeInfo=fangdangchargeInfo;
//获取费用
CHARGE.getChargeItems();
$("#busi_type_order").html("返档");
/********************费用相关信息end***********************************************************************************/
/********************返档相关信息***********************************************************************************/
var fangdang_info = {"fangdang":{
   	            jsessionid: window.parent.application.jsessionid+"PC",
   	            deviceNum:CUSTOMER_INFO_SEARCH.returndata[0].device_number,
   	            //customer_name: $scope.cust_info.cust_name,
   	            customer_name: PC_IDREADER.pc_idreader_info.pName,
   	            id_type: 'ID001',
   	            customer_id: PC_IDREADER.pc_idreader_info.pCardNo,
   	            remark: PC_IDREADER.pc_idreader_info.pAddress,
   	            order_id:ORDER_UPDATE.order_id
}};
function fandangdingdangtijiao() {    

    //订单更新接口开始
    var returnFlag = false;
    var jsonObj = {  
			"contact_address":$("#contact_address").val(),
			"contact_phone":$("#contact_phone").val(),
			"usim_number":CUSTOMER_INFO_SEARCH.returndata[0].usim_info.usim_number,
			"old_id_type": CUSTOMER_INFO_SEARCH.returndata[0].id_type,
			"old_id_number":CUSTOMER_INFO_SEARCH.returndata[0].id_number,
			"old_id_address":"yuan zhengjian dizhi",
			"old_cust_name":CUSTOMER_INFO_SEARCH.returndata[0].cust_name,
			//"old_isfandang":$scope.cust_info.isfandang,
			//"id_number":$scope.id_card_info.id_number 
			"brand":CUSTOMER_INFO_SEARCH.returndata[0].devices_brand,
			"tele_type":CUSTOMER_INFO_SEARCH.returndata[0].tele_type,
			"change_order_type":"9",
			"acc_nbr":CUSTOMER_INFO_SEARCH.returndata[0].device_number,
			"device_number":CUSTOMER_INFO_SEARCH.returndata[0].device_number,
			"fee_info": [] ,
			"cust_seq_id":PC_IDREADER.pc_idreader_info.cust_seq_id                   
    };
    var fee_info_new = [];
    //for循环为订单更新添加内容
    for (var i = 0; i < fangdangchargeInfo.chargeItems.length; i++) {
        //服务器费用只支持字符
        var fee_result = {
            "fee_code": fangdangchargeInfo.chargeItems[i].fee_code,
            "fee_class": fangdangchargeInfo.chargeItems[i].fee_class,
            "fee_name": fangdangchargeInfo.chargeItems[i].fee_name,
            "rec_amount": fangdangchargeInfo.chargeItems[i].raw_amount + "",
            "discount_flag":"1",
            "rel_amount": "0"//全部减免
        };
        jsonObj.fee_info.push(fee_result);
    }
    //订单更新
    ORDER_UPDATE.doDirUpdateOrder(jsonObj, false, function () {
	     	returnFlag = sendHttpSq({"jsessionid":window.parent.application.jsessionid+"PC","order_id":ORDER_UPDATE.order_id,"change_order_type":"9"});
    });

};  

/*生成变更类pdf：
 * sendHttpSqParameter、{
 * jsessionid order_id change_order_type
 * }*/
function sendHttpSq(sendHttpSqParameter){ 		
	var registClient = {
		onComplete: function(message) {
			if(message.type=="success"){
				console.info("pdf协议已经生成！");
				//window.parent.addMask();
				var noHtml = '<div id="showLoadNotice" class="" style="display:block; position: absolute;z-index: 91000;padding: 10px 30px 10px 30px;top:40%;background: url(\''+application.basePath+'images/loading_bg.png\') repeat;border:1px solid #279DE5;border-top:10px solid #279DE5;"><table><tr height="25px"><td align="center" vertical-align="center" style="font-size:14px;font-weight:bold;">页面加载中...</td></tr><tr height="20px"><td align="center" vertical-align="center"><img src="'+application.basePath+'images/loading.gif"/></td></tr></table></div>';
				var showLoad = $("#showLoadNotice");
				if(showLoad.length > 0){
					showLoad.remove();
				}
				$('body').append(noHtml);
				var showLoad2 = $("#showLoadNotice");
				showLoad2.css({"left":((($(window).width() - showLoad.width()) / 2 - 100)+ "px"),"top":((window.parent.getScrollTop() + 220) +"px")});
				window.open(application.fullPath+"authority/dealShowOrder/showOrderChange?order_id="+ORDER_UPDATE.order_id+"&pcFlag=1","_self");
  			}else{
  				$.alert(message.content);
  				return;
  			}
		},
		onError: function(XMLHttpRequest, textStatus,errorThrown) {
			var status = XMLHttpRequest.status;//未显示
			var hint = "网络繁忙，请稍后再试!";//通用万金油提示，都懂得。                
            var error = hint + ",后台接口报错!";
			var dialog=$.dialog({
				   title:'提示',//提示title
				   content:'更新异常:'+error,//显示的内容，支持html格式。
				   buttons:[{id:'btn_ok',text:'确定111',
					   onClick:function(){					   
						   dialog.close();
					   }//点击时候回调函数
				   }]
			});
		}
	};
    var postData={
    		jsessionid:sendHttpSqParameter.jsessionid,
       		//订单id
       		order_id:sendHttpSqParameter.order_id,
       		change_order_type:sendHttpSqParameter.change_order_type};
	$.ajax({
  		type:"POST",
  		url:application.fullPath + "rest/paperless/uploadFormPdfofBiangeng",
  		data:postData,  
  		async:(sendHttpSqParameter.myasync==null?sendHttpSqParameter.myasync:false),
  		waitMsg:"请稍等......",
  		success:registClient.onComplete,				  		
  	    error:registClient.onError				  		
	});
   };

/********************返档相关信息end***********************************************************************************/