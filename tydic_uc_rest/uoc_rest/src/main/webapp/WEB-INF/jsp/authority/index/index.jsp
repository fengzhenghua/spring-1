<%@page import="com.tydic.unicom.crm.web.uss.constants.UrlsMappings"%>
<%@page import="com.tydic.unicom.crm.web.uss.constants.ControllerMappings"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<%@ page import="com.tydic.unicom.webUtil.SpringBean" %>
<%@ page import="com.tydic.unicom.crm.uss.service.pub.interfaces.CodeListServDu" %>
 <% 
 CodeListServDu codeListServDu = SpringBean.getBean("CodeListServDu", CodeListServDu.class);
 String province = codeListServDu.getCodeListByTypeCode("province_code");
 String jsessionid =(String)request.getAttribute("jsessionid");
 String uniform_info_oper =(String)request.getAttribute("uniform_info_oper");
 %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>统一销售服务系统</title>
<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/list.css" rel="stylesheet" type="text/css" />

<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/pre_accept.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/pcIdReader.js"></script>

<script type="text/javascript" src="<%=fullPath%>js/url_helper.js"></script>
<script  type="text/javascript">
application.jsessionid = '<%=jsessionid%>';
application.uniform_info_oper = '<%=uniform_info_oper%>';
var province ="<%=province%>";
$(document).ready(function(){ 	
	if($("#shoujihaomabdwh").length>0){
		$("#shoujihaomabdwh").click(showBindNumExplainAjax);
	}
	
	

	//$("#index_menu").trigger("click");
	//$("#uss_iframe_main").load(function(){ 
	      //$(this).height(0); //用于每次刷新时控制IFRAME高度初始化 
	     // var height = $(this).contents().height() + 10; 
	      //$("#uss_iframe_main").width($(window).width()-17); //ie6这样计算，ie11以上这注掉 下面设置width="100%"
	      //$("#uss_iframe_main").height($(window).height()-132 + 200); 
		  
		  $("#uss_iframe_main").height($(window).height()-132); //2016-4-6 lgw
		  
	      //console.info("fullPath+infoMenuList[0].menu_url="+"<%=fullPath%>${infoMenuList[0].menu_url}");	      
	      refreshIframe('<%=fullPath%>${infoMenuList[0].menu_url}', $("#index_menu_0"));
  //  });
	//$(".sale_mail").hover(function(){
    //    $(".notice_show").show();
   // },function(){
   //     $(".notice_show").hide();
   // });

	var myurl = new objURL();
	var redUrl = myurl.get('redurl');
	if(redUrl != undefined){
		window.location.href = decodeURIComponent(redUrl);
	}
});
function showBindNumExplain(){
	 $("#bangdingweihu").load(application.fullPath+"html/agentVerifyRelation/phoneNumberList.html",
			 bangdingweihucallback
	 );
};
function showBindNumExplainAjax(){
	 $.ajax({
			url:application.fullPath+"html/agentVerifyRelation/phoneNumberList.html",
			//global: false,
		    type: "GET",
		    dataType: "html",
		    async:false,
		    success: function(data,textStatus){
		    	$("#bangdingweihu").html(data);
		    	bangdingweihucallback();
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

function bangdingweihucallback(){
	 showdiv("fun_explain");
	 PC_IDREADER.pc_idreader_info.defaultImagePath=application.fullPath+PC_IDREADER.pc_idreader_info.defaultImagePath;
	 agentVerifyRelationBindinfo.uniform_info_oper="<%=uniform_info_oper%>";
	 agentVerifyRelationBindinfo.jsessionid="<%=jsessionid%>";
};

/**
 * 点击菜单刷新
 */
function refreshIframe(url,obj){
	if(obj!=null||obj!=''){
		$("a[index_list=1]").each(function(){
			if($(this).attr("class")=="current"){
				$(this).removeClass();
				$(this).addClass('sale_enter sale_enter_none');
			}
		});
		$(obj).removeClass();
		$(obj).addClass('current');
		if($(obj).attr("content")!=''){
			 $("#index_div_id").html($(obj).attr("content"));
			return false;
		}
	}
	if(url==null||url=='') return false;
	 if($(obj).attr("menu_id")!=undefined){
		url = url+"?menu_id="+$(obj).attr("menu_id");
	} 
	 $("#crumb").text("当前位置："+$(obj).attr("menu_name"));
	$("#uss_iframe_main").attr('src', url);
	
	
	/* $.ajax({
		url:url,
		global: false,
	    type: "GET",
	    dataType: "html",
	    async:false,
	    success: function(msg){
	    	if(obj!=null||obj!='')
	    	$(obj).attr("content",msg);
	         $("#index_div_id").html(msg);
	    },
	    error:function(msg){
	    	 $("#index_div_id").html(msg);
	    }
	}); */
}
/**
 * 刷新菜单下的页面内容
 */
 function refreshContent(html){
	 $("#index_div_id").html(html);
}
//菜单操作权限校验
function menuAuthorityJudgement(url,obj,value){
	//校验权限目前只针对广西的
	if(province!="gx"){
		refreshIframe(url,obj);
		return;
	}
	var pre_menu_id=$(obj).attr("menu_id");
	if(pre_menu_id!="16"&&pre_menu_id!="33"){
		refreshIframe(url,obj);
		return;
	}
	var jsessionidPC="<%=jsessionid%>"+"PC";
	$.ajax({
  		type : "POST",
  		url : application.fullPath + "rest/saleOpen/menuAuthorityJudgement",
  		data : {
  			"jsessionid":jsessionidPC,
			"menu_element":pre_menu_id,
			"belong_source":"PC"
  			},
  		dataType:'json',
  		waitMsg:"请稍等......",
  		success:function(message){
  			if(message.args.rsp_code == "99999"){
  				 $.alert("对不起，你暂时没有操作该菜单的权限！");
  				 return;
  			}else{
  				//点击菜单显示页面
				refreshIframe(url,obj);
  			}
  		 }	
  		});
}

$(document).ready(function(){ 
	//菜单移动 2016-7-7 lgw
	function moveMenu() {
		//初始化
		var menu_cnt=$("a[index_list=1]").length;//总数
		var left_id=0;//显示的第1个
		var right_id=9;//显示的最后1个
		
		//显示
		if (menu_cnt <= 10) {
			//not thing
		} else {
			//不显示第10个及以后的菜单
			for (i=9;i<menu_cnt;i++) {
				$("#index_menu_"+i).hide();
			}
			
			$("#menu_btn_left").show();
			$("#menu_btn_right").show();
			$("#menu_btn_left").attr("disabled", true);
			$("#menu_btn_left").unbind("click");
			
			//右点击
			$("#menu_btn_right").click(function(){
				if (right_id < (menu_cnt - 1)) {//右移
					$("#index_menu_"+left_id).hide();
					left_id = left_id + 1;
					
					right_id = right_id + 1;
					$("#index_menu_"+right_id).show();
					
					$("#menu_btn_left").addClass('btn_left_red');
					$("#menu_btn_left").removeClass('btn_left_grey');
					$("#menu_btn_left").attr("disabled", false);
				}
				
				if (right_id == (menu_cnt - 1)) {//最后1个
					$("#menu_btn_right").addClass('btn_right_grey');
					$("#menu_btn_right").removeClass('btn_right_red');
					$("#menu_btn_right").attr("disabled", true); 
				}
			});
			
			//左点击
			$("#menu_btn_left").click(function(){
				if (left_id > 0) {//左移
					$("#index_menu_"+right_id).hide();
					right_id = right_id - 1;
					
					left_id = left_id - 1;
					$("#index_menu_"+left_id).show();
					
					$("#menu_btn_right").addClass('btn_right_red');
					$("#menu_btn_right").removeClass('btn_right_grey');
					$("#menu_btn_right").attr("disabled", false);
				}
				
				if (left_id == 0) {//最后1个
					$("#menu_btn_left").addClass('btn_left_grey');
					$("#menu_btn_left").removeClass('btn_left_red');
					$("#menu_btn_left").attr("disabled", true); 
				}
				
			});
		
		}
	}
	
	moveMenu();

});


</script>
</head>
<body>
<object classid="clsid:10946843-7507-44FE-ACE8-2B3483D179B7" id="CVR_IDCard" name="CVR_IDCard" width="0" height="0"></object>
<%@ include file="/WEB-INF/jsp/authority/index/indexHeadNew.jsp"%>
<div id="bangdingweihu"></div>
<div class="tab" id="caiDanYe">
   <div class="tab_list">
    <ul>
    	
        <!-- lgw 2016-7-12 -->
    	<div class="btn_right_red" id="menu_btn_right"></div>
   		<div class="btn_left_grey" id="menu_btn_left"></div>
        
      <!--   <div class="sale_mail">
            <div class="notice_show" style="display:none;">
                <h4><span class="notice_name">公告</span><span class="notice_num">5</span><div class="clear"></div></h4>
                <div class="notice_list"><a href="###">· 关于2G/3G用户转自由组合套餐的公告</a></div>
                <div class="notice_list"><a href="###">· 关于2G/3G用户转自由组合套餐的公告</a></div>
                <div class="notice_list"><a href="###">· 关于2G/3G用户转自由组合套餐的公告</a></div>
                <div class="notice_list"><a href="###">· 关于2G/3G用户转自由组合套餐的公告</a></div>
                <h4><span class="notice_name">待处理订单</span><span class="notice_num">1</span><div class="clear"></div></h4>
                <div class="notice_go"><a href="###">关于2G/3G用户转自由组合套餐的公告</a></div>
                <div class="notice_arrow"></div>
            </div>
        </div> -->
<%--    		<li><a content="" hrefUrl="<%=fullPath%><%=ControllerMappings.USS_WEB_INDEX %>/<%=UrlsMappings.SALE_INDEX_NEW%>" index_list=1 id="index_menu" onclick="refreshIframe('<%=fullPath%><%=ControllerMappings.USS_WEB_INDEX %>/<%=UrlsMappings.SALE_INDEX_NEW%>',this)" href="javascript:void(0)" class="current">订单处理</a></li>
 --%>   
   <c:forEach var="infoMenu" items="${infoMenuList}" varStatus="i">
          <c:if test="${i.index=='0'}">
        	<li>
           		<a content="" hrefUrl="<%=fullPath%>${infoMenu.menu_url}" index_list=1 href="javascript:void(0)" id="index_menu_${i.index }" onClick="menuAuthorityJudgement('<%=fullPath%>${infoMenu.menu_url}',this)"  menu_name="${infoMenu.menu_name }" menu_id="${infoMenu.menu_id }" class="current">${infoMenu.menu_name }</a><!-- sale_enter sale_enter_none -->
         	</li>
          </c:if>
          <c:if test="${i.index!='0'}">
        	<li>
           		<a content="" hrefUrl="<%=fullPath%>${infoMenu.menu_url}" index_list=1 href="javascript:void(0)" id="index_menu_${i.index }" onClick="menuAuthorityJudgement('<%=fullPath%>${infoMenu.menu_url}',this)"  menu_name="${infoMenu.menu_name }" menu_id="${infoMenu.menu_id }" >${infoMenu.menu_name }</a><!-- sale_enter sale_enter_none -->
         	</li>
          </c:if>
        </c:forEach>
  </ul>
    <div class="tip"></div><!-- 营业报表栏目标题只在营业坐席显示，共享坐席不显示 --> 
        <div id="crumb" class="crumb">当前位置：订单处理</div>
        <div class="clear"></div>
    </div>
</div>
<iframe padding-top="0"  frameborder="0"  name="uss_iframe_main" width ="100%" id="uss_iframe_main"  src="<%=fullPath%><%=ControllerMappings.USS_WEB_INDEX %>/<%=UrlsMappings.SALE_INDEX_NEW%>"></iframe>
<div class="bg_mask" id="bg_mask">
	<iframe class="bg_mask_iframe"> </iframe>
</div>
</body>
</html>