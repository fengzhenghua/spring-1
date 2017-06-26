/*
 * added by haolong
 * 获取费用，减免费用 ，收费模块
 * 支持减免：
 * */
var CHARGE = (function(){
		
	var charge_info = {
		chargeInfo : {
			chargeItems : [],
			originChargeItems:[],
			totalFee : 0,
			disChargeItems:[],
			totalRawFee:0,
			m_jessionid:"",
			serv_code:"",
			getChargeItemsOfUrl:""
		},
		init:function(){
			CHARGE.chargeInfo.m_jessionid=HTML_UTLS.getParam("jsessionid");
		},
		//
		trimNumber : function (txt) {
	        var rst = "";
	        for (var i = 0; i < txt.length; i++) {
	            var char = txt[i];
	            if (/[\d\.]/.test(char)) {
	                rst += char;
	            }
	        }
	        return rst;
	    },
		//
		parseNumber : function (item) {
	        item.rec_amount = this.trimNumber(item.rec_amount);	        
	        var floatReg = /^\d*(\.\d+)?$/;
	        if (item.rec_amount == ''
	            || item.rec_amount.endsWith(".")
	            || /\d+\.\d{1,2}$/.test(item.rec_amount)
	            || /^[0-9]+$/.test(item.rec_amount)) {
	            return;
	        }
	        if (!floatReg.test(item.rec_amount)) {
	            item.rec_amount = 0.00;
	        } else {
	            try {
	                item.rec_amount = parseFloat(item.rec_amount);
	            } catch (e) {
	                item.rec_amount = 0.00;
	            }
	        }

	        if (!item.rec_amount || item.rec_amount < 0) {
	            item.rec_amount = 0.00;
	        }
	        item.rec_amount = item.rec_amount.toFixed(2);
	        CHARGE.getTotalFee();
	    },
	    /**
	     * 获取总费用
	     * @returns {number}
	     */
	    getTotalFee : function () {
	        var amount = 0;
	        var tmp = 0;
	        for (var i = 0; i < CHARGE.chargeInfo.chargeItems.length; i++) {
	            if (!CHARGE.chargeInfo.chargeItems[i].rec_amount) {
	                tmp = 0;
	            } else {
	                tmp = CHARGE.chargeInfo.chargeItems[i].rec_amount;
	                tmp = parseFloat(CHARGE.chargeInfo.chargeItems[i].rec_amount);
	            }
	            amount += tmp;
	        }
	        CHARGE.chargeInfo.totalFee = amount.toFixed(2);
	        return CHARGE.chargeInfo.totalFee;
	    },
	    //初始化费用
	    getChargeItems:function () {
	    	
	    	var chargeInfo=CHARGE.chargeInfo;
	        var registClient = {
	        	
	            onComplete: function (message) {	            	
	            	if(message.type=="success"){
						console.info("获取费用成功");							
		  			}else{
		  				$.alert(message.content);
		  				return;
		  			}
	            	var args=message.args
	                //调用成功后服务器返回的数据,args是json格式,args具体参数,参考接口文档
	                //当数据为空的时候  返回
	                if (args == null) {
	                    return;
	                }
	                if (args.fee_info==null){
	                	chargeInfo.chargeItems = [];
	                	return;
	                }
	                var infos = args.fee_info;
	                chargeInfo.chargeItems = [];
	                chargeInfo.totalRawFee = 0;
                    for (var i = 0; i < infos.length; i++) {
                    	infos[i].rec_amount = parseFloat(infos[i].rec_amount);
                        infos[i].max_relief = parseFloat(infos[i].max_relief);
                        chargeInfo.totalRawFee += infos[i].rec_amount;
                        infos[i].raw_amount = infos[i].rec_amount;
                        chargeInfo.chargeItems.push(infos[i]);
                    }
                    chargeInfo.originChargeItems=chargeInfo.chargeItems.concat();
                   
                    //alert("fangdangchargeInfo.chargeItems"+JSON.stringify(fangdangchargeInfo.chargeItems));
                    //alert("CHARGE.chargeInfo"+JSON.stringify(CHARGE.chargeInfo));
	               
	                //$("#reget_fee").hide();
	                //$("#next_flow_step_1").show();
	            },
	            onError: function(XMLHttpRequest, textStatus,errorThrown) {
					var status = XMLHttpRequest.status;//未显示
					var hint = "网络繁忙，请稍后再试!";//通用万金油提示，都懂得。                
		            var error = hint + ",后台接口报错!";
					var dialog=$.dialog({
						   title:'提示',//提示title
						   content:'更新异常:'+error,//显示的内容，支持html格式。
						   buttons:[{id:'btn_ok',text:'确定',
							   onClick:function(){					   
								   dialog.close();
							   }//点击时候回调函数
						   }]
					});
		        	//$("#reget_fee").show();
	                //$("#next_flow_step_1").hide();
				}
	        };

	        /**
	         * 参数1：product_id
	         * 参数2：tele_type
	         * 参数3：acc_nbr_fee
	         */
	        console.info("scope.m_jessionid="+CHARGE.chargeInfo.m_jessionid);
	        var data= {};        
	        postData= {                     	
				jsessionid: CHARGE.chargeInfo.m_jessionid,
				serv_code:CHARGE.chargeInfo.serv_code
			}; 
	        $.ajax({
		  		type:"POST",
		  		url:application.fullPath + CHARGE.chargeInfo.getChargeItemsOfUrl,
		  		data:postData,  
		  		async:true,
		  		waitMsg:"请稍等:获取费用信息......",
		  		success:registClient.onComplete,				  		
		  	    error:registClient.onError				  		
			});
	    },
	    reset : function () {
	    	CHARGE.chargeInfo.chargeItems = CHARGE.chargeInfo.originChargeItems.concat();
	    }

		
};
return charge_info;
	   
	    
})();	    
