/* add by haolong
 * 页签 控制 事件脚本
 * */
$(document).ready(function(){	
	
		//tab菜单 页签控制
		$(".tab_menu").click(function(){
			//选中
			$(".tab_menu").children("a").removeClass("current");
			$(this).children("a").addClass("current");
			
			var sub_div = "";
			sub_div = $(this).children("a").attr("id");
			
					
			/*控制子页签 的显示*/	
			var sub_cnt = $(".tab_menu").length;
			
			for (i=1;i<=sub_cnt;i++) {
				$("#tab_menu_"+ i + "_sub").hide();
			}
			$("#"+sub_div + "_sub").show();				
			//所有的动态资源（如工号相关信息 ） 和静态资源（对应的html页面） 全部动态加载			
			myclickmenu = $(this).children("a").attr("name");
			loadmypage(myclickmenu);
			
		});
		
		//点击变更子菜单
		$(".change").click(function(){
			var pre_id=$(this).attr("id");
			$(".change_menu_list").children("a").removeClass("btn_white_cur").addClass("btn_white");
			$("#change_seoncd_menus_biangeng").removeClass("btn_white_long_cur").addClass("btn_white_long");
			$("#change_seoncd_menus_biangeng").removeClass("btn_white");
			if(pre_id=="change_seoncd_menus_biangeng"){
				$("#"+pre_id).removeClass("btn_white_long").addClass("btn_white_long_cur");
			}else{
				$("#"+pre_id).removeClass("btn_white").addClass("btn_white_cur");
			}
			myclickmenu = pre_id;
			yewufengzhi = pre_id;
		});

		/*缺少获取公共资源的rest：
		 * 如工号相关信息 
		 * */
	});
jQuery.ajaxSetup({
	cache:false
});
//默认加载页面
var myclickmenu = "biangenghuozefangdang";
//初始化订单id，属于全局变量，将在进入身份证读取的页面开始创建订单并赋值
var order_id="";
//控制变更业务逻辑代码分支
var  yewufengzhi="";
var application={basePath:window.parent.application.basePath,fullPath:window.parent.application.fullPath};
function loadmypage(myclickmenu) { 	 
	 switch (myclickmenu) {
	     case ("test"):
	         alert("test");
	         break;
	     case ("biangenghuozefangdang"):
	    	//只加载业务入口
	        //alert("biangenghuozefangdang");
	        showBiangenghuozefangdang()
	        break;
	     case ("string3"):      
	         break;
	     default:
	    	  alert("tmd no match");
	 }

}
//变更或者返档
function showBiangenghuozefangdang(){
	 $.ajax({
			url:window.parent.application.fullPath+"html/yxs_pctongyi/biangengfangdang/common/search.html",
			//global: false,
		    type: "GET",
		    dataType: "html",
		    async:false,
		    success: function(data,textStatus){		    	
		    	$("#xiaodipaihanban").html(data);	    	
		    },
		    error: function (XMLHttpRequest, textStatus,errorThrown) {
               var status = XMLHttpRequest.status;
               //var errorInfo = HTTP_ERROR_STATUES[status];
               var hint = "网络繁忙，请稍后再试!";//通用万金油提示，都懂得。                
               var error = hint + ",后台接口报错!";
               $.alert(error);
           }
		});
};
	/*
	function showdiv(popWinId) { 
		document.getElementById("bg_mask").style.display ="block";
		document.getElementById(popWinId).style.display ="block";
	}
	function hidediv(popWinId) {
		document.getElementById("bg_mask").style.display ='none';
		document.getElementById(popWinId).style.display ='none';
	}
	
	function lgwtest() {
			var dialog=$.dialog({
			   title:'提示',
			   content:'该号码未返档，请进行返档操作。',
			   buttons:[
					{id:'btn_ok',text:'确定',
						   onClick:function(){	
							   dialog.close();
				
						   }},  {id:'btn_ok',text:'取消',
							   onClick:function(){	
								   dialog.close();
					
							   }}
				   ]
			}); 
		};
	
	function lgwtestquery() {
			var dialog=$.dialog({
			   title:'费用详情',
			   content:'当月实时话费：56.58元<br>当前可用余额：12.34元<br>普通预存款：2.34元<br>当月可用分摊款：10.00元<br>当月赠款：0.00元<br>历史欠费：0.00元<br>违约金：0.00元',
			   buttons:[
					{id:'btn_ok',text:'确定',
						   onClick:function(){	
							   dialog.close();
				
						   }}
				   ]
			}); 
		};
	
	*/