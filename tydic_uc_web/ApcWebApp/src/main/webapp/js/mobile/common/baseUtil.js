
var DIALOG_BASE = {
		/**
		 * 传入弹出框ID
		 * @param id
		 */
		/*遮罩div */
		/*hidediv:"<div id='hide_div' style='background:#ADADAD;width:100%;height:100%;z-index:199;opacity:0.5;position:fixed;top:0;bottom:0;left:0;right:0;'><\/div>",*/
		
		showDialog : function(id) {
		/*动态生成遮罩div*/
		/*	$(document.body).append(this.hidediv);*/ 
		/*显示遮罩*/	
						
			$("#"+id).show();
			
			//设置width为%90
			//var width = Math.round(document.body.clientWidth*0.94);
			//$("#"+id).width(width);
			
			var div_height=$("#" + id ).height();
			var div_width=$("#" + id).width();
						
			var right = Math.round((document.body.clientWidth -div_width) / 2);
			//var top =Math.round((document.documentElement.clientHeight-div_height)/2);
			
			var top = Math.round((document.documentElement.clientHeight-div_height)/2) + document.body.scrollTop;
			
			//弹出框
			$("#"+id).css({
				'position':'absolute','top': top,'right':right
			});
		},
		/**
		 * 传入弹出框ID
		 * @param id
		 */
		hideDialog:function(id){
		/*	$("#hide_div").remove();*/
			$("#" + id).hide();
		}
};

