<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@include file="../common/common.jsp"%>
 <%@ page import="com.tydic.unicom.util.DateUtil" %>
 <%@page import="org.jasig.cas.client.util.AssertionHolder"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>号码仓库</title>
<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/store.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/jquery.floatDiv.js"></script>
<script type="text/javascript" src="<%=fullPath%>js/numberData.js"></script>
<script type="text/javascript">
$(function(){

	$("#test1").floatdiv({bottom:"0px",right:"0px",left:"0px"});
	$("#help").floatdiv({bottom:"50px",right:"20px"});
});
</script>

</head>
 <input type="hidden" id="tele_type" value="4G" />  
  <input type="hidden" id="dataLength" value="" />
<body>
<div class="select">
    <form id="form1" name="form1" method="post" action="">
	<div class="select_list">
    	<div class="div_float">
        	<!--  <a href="###" id="teleType2G" class="g" >2G</a><a href="###" id="teleType3G"  class="g">3G</a><a href="###" id="teleType4G" class="g g_current">4G</a>
        -->
        </div>
        <div class="div_float div_float_ie6" id="showResInfoUnlock"> 
           <t:select id="unlock_section" name = "unlock_section" codeType="resource_info_unlock" ></t:select>
        </div>
       <div id="unlock_section_qry">
        <div class="div_float div_float_ie6" id="showMob2G"> 
					           <select id="mob_section" name = "mob_section">
					           <option value="10000">号段选择</option>
					           <option value="185">185</option>
					           <option value="186">186</option>
					           <option value="145">145</option>
					           <option value="175">175</option>
					           <option value="176">176</option>
					           <option value="156">156</option>
					           <option value="155">155</option>
					           <option value="132">132</option>
					           <option value="131">131</option>
					           <option value="130">130</option>
					           </select>
					        </div>

    	
    	
         <div class="div_float div_float_ie6" id="show3G">
           <t:select id="perrty_type_pc" name = "perrty_type_pc" codeType="perrty_type_pc" ></t:select>
        </div>
        
        <div class="div_float div_float_ie6" id="show2G">
           <t:select id="good_type" name = "good_type" codeType="good_type" ></t:select>
        </div>  
        </div>  
        <div class="div_float div_float_none">
        	<dl>
           	  <dt>搜索：</dt>
              <dd>
              	<input type="text"  id="fuzzy_query" value="请输入1-11位数字查询号码" onfocus="this.value=''" onblur="haoduanOnblur();" class="tel" />
              	<input type="text"  id="unlock_query" value="请输入预占号码后解锁" onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入预占号码后解锁'}" class="tel" />
              </dd>
              <dd class="none">
              	<a href="#" id="query" class="search">查询</a>
              	<a href="#" id="unlock" class="search">解锁</a>
              </dd>
              <!--  //YUN-172
              
              <dd></dd>
              <dd class="none"></dd>-->
              <div class="clear"></div>
            </dl>
        </div>
        <div class="clear"></div>
	</div>
	</form>
</div>


<div class="wrap" id="list">
</div>




<div class="copyright">
Copyright © 2014 China unicom All Right Reserved
</div>
<div class="help" id="help">
	<a href="###" onclick="dealHelp();"></a>
</div>
<div class="compare" id="test1" style="display:none;"><!--这块请用JS做，否则在IE6上会没有作用。IE7及以上均可以使用。-->
	<div class="title">已选号码对比栏</div>
        <div class="compare_info" id="compareInfo">
       
         
           
         </div>
          
</div>


</body>
</html>
