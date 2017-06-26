(function($) {
	$.fn.extend({
		pages: function(options) {
			var _this=$(this);
			if(typeof(options) !=  "object"){
				$.alert("分页参数必须是Object类型");
				return;
			}
			if(!options.url){
				$.alert("分页参数url必须输入");
				return;
			}	
			if(!_this.hasClass("tydic_pages")){
				_this.addClass("tydic_pages");
			}
			_this.removeClass("pages");
			/*if($("#tydic_pages_loading_"+_this.attr("id")).length <= 0){
				var w = $(window).width();
				var h = $(window).height();
				var maskDivHtml = "<div id='maskDivNotice_"+_this.attr("id")+"'  style='cursor: pointer;position:absolute;top:0px;left:0px;width:"+w+"px;height:"+h+"px;opacity:0.15;background:none repeat scroll 0 0 #000000;z-index: 90009;filter: progid:DXImageTransform.Microsoft.Alpha(opacity=0.15)'></div>";
				$('body').append(maskDivHtml);
				_this.after('<div id="tydic_pages_loading_'+_this.attr("id")+'" class="tydic_pages_loading" style="display:none;"><img src="'+application.basePath+'images/loading.gif"/><br/> 正在读取...</div>');
				$("#tydic_pages_loading_"+_this.attr("id")).css({"top":_this.offset().top+10});
			}*/
			
			var s=options.getData();
			s.pageNum=0;/*默认查询第1页*/
			if(options.pageSize){
				s.pageSize = options.pageSize;
			}
			//$("#tydic_pages_loading_"+_this.attr("id")).show();
			//$("#maskDivNotice_"+_this.attr("id")).show();
			$.ajax({
				url:options.url,
				type:"post",
				dataType:"json",
				data:s,
				waitMsg:"读取中...",
				success:function(page){
//					
//					var totalSize = page.dataRows.length;
//					$("#totalSize").text(totalSize);
//					$("#total_page").text(page.totalPages);
//					$("#this_page").text(page.curPage);
					//$("#tydic_pages_loading_"+_this.attr("id")).hide();
					//$("#maskDivNotice_"+_this.attr("id")).hide();
					if(page.dataRows == null || page.dataRows  == ""){
						if($("#showSelectData").length>0){
							$("#showSelectData").remove();
						}
						$("#"+options.dataDiv).append("没有符合条件的数据！"); 
						_this.text("");
						//$.alert("没有符合条件的数据！");
						return;
					}
					if(page.dataRows.length <= 0 ){
						if($("#showSelectData").length>0){
							$("#showSelectData").remove();
						}
						$("#"+options.dataDiv).append("没有符合条件的数据！"); 
						_this.text("");
						//$.alert("没有符合条件的数据！");
						return;
					}
					if(page.dataRows.length>page.pageSize){
						    
						    var totalPageNew=Math.ceil(page.dataRows.length/page.pageSize);
		                    var forPage=0;
		                    if($("#showSelectData").length>0){
								$("#showSelectData").remove();
							}
		                    var html = '<div id="showSelectData">';
							for(var j = 0;j< totalPageNew;j++){
								var fortotalPage=forPage+page.pageSize;
						        html += '<div  style="display:none;" id="showSelectData_'+j+'">';	
							    if(fortotalPage>page.dataRows.length){
							    	fortotalPage=page.dataRows.length;
							    }
							    for(var i = forPage;i < fortotalPage;i++){
								    var vo = page.dataRows[i];
								    html += options.onLoad(vo,i);						  
								   }
							    forPage +=page.pageSize;
							    html += '</div>';
						 
							
							}
							  html += '</div>';
							  $("#"+options.dataDiv).append(html);
							_this.pagination(page.dataRows.length, {
								num_edge_entries: 2, //边缘页数
								callback: pageselectCallbackOnly,//代表一次性查询很多页在分页
								items_per_page:page.pageSize //每页显示1项
							});
						}else{
							if($("#showSelectData").length>0){
								$("#showSelectData").remove();
							}
							/*if($("#showSelectData_1").length>0){
								$("#showSelectData_1").remove();
							}*/
							var html='<div id="showSelectData">';
							 html += '<div  id="showSelectData_0">';	
							  for(var i = 0; i <page.dataRows.length;i++){
									var vo = page.dataRows[i];
									html += options.onLoad(vo,i);	
									
							  }
							  html += '</div></div>';
						     $("#"+options.dataDiv).append(html);
						_this.pagination(page.totalRecords, {
							num_edge_entries: 2, //边缘页数
							callback: pageselectCallback,
							items_per_page:page.pageSize //每页显示1项
						});
					   }
				}
			});
			function pageselectCallbackOnly(page_index,jq){
				$("#showSelectData div").each(function(){
				    $(this).hide();
				});
               $("#showSelectData_"+page_index).show();
					return;
			}
			function pageselectCallback(page_index,jq){
				var s=options.getData();
				s.pageNum=page_index;
				if(options.pageSize){
					s.pageSize = options.pageSize;
				}
				if(page_index==0&&$("#showSelectData_0").length>0){
					return;
				}
				if($("#showSelectData_0").length>0){
					$("#showSelectData_0").remove();
				}
				if($("#showSelectData_1").length>0){
					$("#showSelectData_1").remove();
				}
				//$("#tydic_pages_loading_"+_this.attr("id")).show();
				//$("#maskDivNotice_"+_this.attr("id")).show();
				$.ajax({
					url:options.url,
					type:"post",
					dataType:"json",
					data:s,
					waitMsg:"读取中...",
					success:function(page){
						//$("#tydic_pages_loading_"+_this.attr("id")).hide();
						//$("#maskDivNotice_"+_this.attr("id")).hide();
						var html="";
						 html += '<div  id="showSelectData_1">';	
						  for(var i = 0; i <page.dataRows.length;i++){
								var vo = page.dataRows[i];
								html += options.onLoad(vo,i);	
								
						  }
						  html += '</div>';
					     $("#"+options.dataDiv).append(html);
					     return;
					}
				});
			}
		}
	});
})(jQuery);