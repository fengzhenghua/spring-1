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
  		async:(sendHttpSqParameter.myasync==null?true:sendHttpSqParameter.myasync),
  		waitMsg:"请稍等......",
  		success:registClient.onComplete,				  		
  	    error:registClient.onError				  		
	});
   };