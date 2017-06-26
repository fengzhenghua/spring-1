(function($){
	//备份jquery的ajax方法
	var _ajax=$.ajax;
	
	//重写jquery的ajax方法
	$.ajax=function(opt){
		
		//begin lgw 2015-12-29
		if (!window.XMLHttpRequest) { //是IE6
			var selects = document.getElementsByTagName("select");
			var selects_display = new Array;
		}
		//end lgw 2015-12-29
		
		var addMask = function(){
			var w = $(window).width();
			var h = $(document).height();
			var maskDivHtml = "<div id='maskDivNotice'  style='cursor: pointer;position:fixed; _position:absolute; top:0px;left:0px;width:"+w+"px;height:"+h+"px;opacity:0.45;background:none repeat scroll 0 0 #000000;z-index: 90009;filter: progid:DXImageTransform.Microsoft.Alpha(opacity=45)'></div>";
			var maskDivNotice = $("#maskDivNotice");
			if(maskDivNotice.length > 0){
				maskDivNotice.remove();
			}
		
			$('body').append(maskDivHtml);
			
			//begin lgw 2015-12-29
			if (!window.XMLHttpRequest) { //是IE6
				$("#maskDivNotice").append('<iframe class="mask_iframe_ie6"><\/iframe>');
				
				for(var i=0;i<selects.length;i++){
					if(selects[i].style.display!='none'){
						selects_display.push(selects[i]);
						selects[i].style.display = "none";
					};
				}
				selects = null;
			}
			//end lgw 2015-12-29
		};
		var deleteMaskDivNotice = function(){
			//begin lgw 2015-12-29
			if (!window.XMLHttpRequest) { //是IE6
				for(var i=0;i<selects_display.length;i++){
					selects_display[i].style.display = "";
				}
			}
			selects_display = null;
			//end lgw 2015-12-29
			
			var maskDivNotice = $("#maskDivNotice");
			if(maskDivNotice.length > 0){
				maskDivNotice.remove();
			}
			var showLoadNotice = $("#showLoadNotice");
			if(showLoadNotice.length > 0){
				showLoadNotice.remove();
			}
		};
		var getScrollTop = function(){
			var D = document; 
			return Math.max(D.body.scrollTop, D.documentElement.scrollTop)
		};
		if(opt.waitMsg){
			addMask();
			var noHtml = '<div id="showLoadNotice" class="" style="display:block; position: absolute;z-index: 91000;padding: 10px 30px 10px 30px;top:40%;background: url(\''+application.basePath+'images/loading_bg.png\') repeat;border:1px solid #279DE5;border-top:10px solid #279DE5;"><table><tr height="25px"><td align="center" vertical-align="center" style="font-size:14px;font-weight:bold;">'+opt.waitMsg+'</td></tr><tr height="20px"><td align="center" vertical-align="center"><img src="'+application.basePath+'images/loading.gif"/></td></tr></table></div>';
			var showLoad = $("#showLoadNotice");
			if(showLoad.length > 0){
				showLoad.remove();
			}
			//else{
				$('body').append(noHtml);
				var showLoad2 = $("#showLoadNotice");
				showLoad2.css({"left":((($(window).width() - showLoad.width()) / 2 - 100)+ "px"),"top":((getScrollTop() + 220) +"px")});
			//}
		}
		//备份opt中error和success方法
		var fn = {
			error:function(XMLHttpRequest, textStatus, errorThrown){},
			success:function(data, textStatus){}
		};
		if(opt.error){
			fn.error=opt.error;
		}
		if(opt.success){
			fn.success=opt.success;
		}
		
		//扩展增强处理
		var _opt = $.extend(opt,{
			error:function(XMLHttpRequest, textStatus, errorThrown){
				//错误方法增强处理
				deleteMaskDivNotice();
				fn.error(XMLHttpRequest, textStatus, errorThrown);
			},
			success:function(data, textStatus){
				//成功回调方法增强处理
				deleteMaskDivNotice();
				fn.success(data, textStatus);
			}
		});
		return _ajax(_opt);
	};
	//---------------------------------身份证读卡器专用ajax调用----------------------------------
	//备份jquery的ajax方法
	//var _ajaxIdCard=$.ajaxIdCard;
	//重写jquery的ajax方法
	$.ajaxIdCard=function(opt){
		
		//begin lgw 2015-12-29
		if (!window.XMLHttpRequest) { //是IE6
			var selects = document.getElementsByTagName("select");
			var selects_display = new Array;
		}
		//end lgw 2015-12-29
		
		var addMask = function(){
			var w = $(window).width();
			var h = $(document).height();
			var maskDivHtml = "<div id='maskDivNotice1'  style='cursor: pointer;position:absolute;top:0px;left:0px;width:"+w+"px;height:"+h+"px;opacity:0.35;background:none repeat scroll 0 0 #000000;z-index: 90009;filter: progid:DXImageTransform.Microsoft.Alpha(opacity=35)'></div>";
			var maskDivNotice = $("#maskDivNotice1");
			if(maskDivNotice.length > 0){
				maskDivNotice.remove();
			}
			$('body').append(maskDivHtml);
			
			//begin lgw 2015-12-29
			if (!window.XMLHttpRequest) { //是IE6
				$("#maskDivNotice").append('<iframe class="mask_iframe_ie6"><\/iframe>');
				for(var i=0;i<selects.length;i++){
					if(selects[i].style.display!='none'){
						selects_display.push(selects[i]);
						selects[i].style.display = "none";
					};
				}
				selects = null;
			}
			//end lgw 2015-12-29
			
		};
		var deleteMaskDivNotice = function(){
			
			//begin lgw 2015-12-29
			if (!window.XMLHttpRequest) { //是IE6
				for(var i=0;i<selects_display.length;i++){
					selects_display[i].style.display = "";
				}
			}
			selects_display = null;
			//end lgw 2015-12-29
			
			var maskDivNotice = $("#maskDivNotice1");
			maskDivNotice.remove();
			/*if(maskDivNotice.length > 0){
				maskDivNotice.hide();
			}*/
			var showLoadNotice = $("#showLoadNotice1");
			showLoadNotice.remove();
			/*if(showLoadNotice.length > 0){
				showLoadNotice.hide();
			}*/
		};
		if(opt.waitMsg){
			addMask();
			//var noHtml = '<div id="showLoadNotice" class="" style="display:block; position: absolute;z-index: 91000;padding: 10px 30px 10px 30px;top:40%;background: url(\''+application.basePath+'images/loading_bg.png\') repeat;border:1px solid #d7000f;border-top:10px solid #d7000f;"><table><tr height="25px"><td align="center" vertical-align="center" style="font-size:14px;font-weight:bold;">'+opt.waitMsg+'</td></tr><tr height="20px"><td align="center" vertical-align="center"><img src="'+application.basePath+'images/loading.gif"/></td></tr></table></div>';
			var noHtml = '<div id="showLoadNotice1" class="tydic_pages_loading" style="display:block;"><img src="'+application.basePath+'images/loading.gif"/><br/> '+opt.waitMsg+'</div>';
			var showLoad = $("#showLoadNotice1");
			if(showLoad.length > 0){
				showLoad.show();
			}else{
				$('body').append(noHtml);
				var showLoad = $("#showLoadNotice1");
				showLoad.css("left",(($(window).width() - showLoad.width()) / 2 - 20 )+ "px");
			}
		}
		//备份opt中error和success方法
		var fn = {
			error:function(XMLHttpRequest, textStatus, errorThrown){},
			success:function(data, textStatus){}
		};
		if(opt.error){
			fn.error=opt.error;
		}
		if(opt.success){
			fn.success=opt.success;
		}
		
		//扩展增强处理
		var _opt = $.extend(opt,{
			error:function(XMLHttpRequest, textStatus, errorThrown){
				//错误方法增强处理
				deleteMaskDivNotice();
				fn.error(XMLHttpRequest, textStatus, errorThrown);
			},
			success:function(data, textStatus){
				//成功回调方法增强处理
				deleteMaskDivNotice();
				fn.success(data, textStatus);
			}
		});
		return _ajax(_opt);
	};
})(jQuery);

