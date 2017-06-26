<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@include file="../common/common.jsp"%>
  <%@ page import="com.tydic.unicom.util.DateUtil" %>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>销售</title>
<script type="text/javascript" src="<%=fullPath%>js/allTake.js"></script>
<script type="text/javascript" src="<%=fullPath%>js/vpnDinner.js"></script>
<script type="text/javascript" src="<%=fullPath%>js/preOpen.js"></script>
<script  type="text/javascript"> 
$(document).ready(function(){ 
	var fullPath = '<%=fullPath%>';
	setInterval('search(true)',20000);
	setInterval('search2()',20100);
	setInterval('search3()',20200);
	$("#all_take_id").click(function(){		
		document.getElementById("all_take").style.display  = "block";
		document.getElementById("vpn").style.display  = "none";
		document.getElementById("open").style.display  = "none";
		$("#all_take_id").addClass("current");
		$("#vpn_id").removeClass("current");
		$("#open_id").removeClass("current");
		clear();
		clear2();
		clear3();
	});
	$("#vpn_id").click(function(){		
		document.getElementById("all_take").style.display  = "none";
		document.getElementById("vpn").style.display  = "block";
		document.getElementById("open").style.display  = "none";
		$("#vpn_id").addClass("current");
		$("#all_take_id").removeClass("current");
		$("#open_id").removeClass("current");
		clear();
		clear2();
		clear3();
	});
	$("#open_id").click(function(){		
		document.getElementById("all_take").style.display  = "none";
		document.getElementById("vpn").style.display  = "none";
		document.getElementById("open").style.display  = "block";
		$("#open_id").addClass("current");
		$("#all_take_id").removeClass("current");
		$("#vpn_id").removeClass("current");
		clear();
		clear2();
		clear3();
	});
	
});
</script>
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="show">
    
    <div class="info">
    	<div class="detail_white">
        	<div class="title"></div>
            
            <div class="title_tab">        
                <span class="text_large24"></span>
            
                <div class="tab_menu">
                    <a href="###" class="current" id="all_take_id">全拿走预受理</a>
                </div>
                <div class="tab_menu">
                    <a href="###" class="" id="vpn_id">VPN预受理</a>
                </div>
                <div class="tab_menu">
                    <a href="###" class="" id="open_id">开户预受理</a>
                </div>
            </div>   
            <div class="clear"></div> 
            <div class="line_red_top"></div>
          </div>              
        </div> 
       <!-- 全拿走 --> 	
		<div class="info" id="all_take">
		 <ul class="detail">
			<form id="searchfom" method="post">
                <div class="padding_blank10"></div>
                <li class="list_padding_zero">
                    <div class="left">
                        <div class="left_lable">预订单号：</div>
                        <div class="left_data_w130">
                            <input type="text" id="order_id" name="" value=""></input>
                        </div>
                        <div class="left_lable">手机号码：</div>
                        <div class="left_data_w130">
                            <input type="text" id="device_number" name="" value=""></input>
                        </div>
                    </div>
                    <div class="right">
                        <div class="left_lable">身份证号码：</div>
                        <div class="left_lable_quarter">
                            <div class="left_data_w130">
                                <input type="text" id="cust_id" name="" value=""></input>
                            </div>
                        </div>
                        <a href="javascript:void(0)" id="search"><div class="input_button">查  询</div></a>
                    </div>
                </li>
                <div class="padding_blank10"></div>
			</form>
				<li class="list" style="height: 300px;">
	         		<!-- jqGrid 数据 table -->
					<table id="rlist"></table>
					<!-- jqGrid 分页 div  -->
					<div id="gridPager"></div>
	         	</li>
		  </ul>
	 </div>
		 
	  <!-- vpn预受理 --> 	
	 <div class="info" id="vpn" style="display:none;" >
		<ul class="detail">
			<form id="searchfom" method="post">
                <div class="padding_blank10"></div>
                <li class="list_padding_zero">
                    <div class="left">
                        <div class="left_lable">群组名称：</div>
                        <div class="left_data_w130">
                            <input type="text" id="vpn_name" name="" value=""></input>
                        </div>
                        <div class="left_lable">手机号码：</div>
                        <div class="left_data_w130">
                            <input type="text" id="device_number2" name="" value=""></input>
                        </div>
                    </div>
                    <div class="right">
                        <a href="javascript:void(0)" id="search2"><div class="input_button">查  询</div></a>
                    </div>
                </li>
                <div class="padding_blank10"></div>
			</form>
				<li class="list" style="height: 350px;">
	         		<!-- jqGrid 数据 table -->
					<table id="rlist2"></table>
					<!-- jqGrid 分页 div  -->
					<div id="gridPager2"></div>
	         	</li>
			</ul>
		</div>
	   <!-- 开户预受理  --> 	
		<div class="info" id="open" style="display:none;">
		 <ul class="detail">
			<form id="searchfom" method="post">
                <div class="padding_blank10"></div>
                <li class="list_padding_zero">
                    <div class="left">
                        <div class="left_lable">手机号码：</div>
                        <div class="left_data_w130">
                            <input type="text" id="device_number3" name="" value=""></input>
                        </div>
                        <div class="left_lable">身份证号：</div>
                        <div class="left_data_w130">
                            <input type="text" id="cust_id3" name="" value=""></input>
                        </div>
                    </div>
                    <div class="right">
                        <a href="javascript:void(0)" id="search3"><div class="input_button">查  询</div></a>
                    </div>
                </li>
                <div class="padding_blank10"></div>
			</form>
				<li class="list" style="height: 350px;">
	         		<!-- jqGrid 数据 table -->
					<table id="rlist3"></table>
					<!-- jqGrid 分页 div  -->
					<div id="gridPager3"></div>
	         	</li>
			</ul>
		</div>
	</div>
	   
	   
</body>
</html>