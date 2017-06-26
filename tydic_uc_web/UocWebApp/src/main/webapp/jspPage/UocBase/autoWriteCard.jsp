<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>自动写卡</title>
    <link rel="stylesheet" type="text/css" href="../../style/css/public.css" />
    <link rel="stylesheet" type="text/css" href="../../style/css/writeCard.css" />
    <script type="text/javascript" src="../../js/common/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../../js/common/WebUtil.js"></script>
    <script type="text/javascript" src="../../js/common/public.js"></script>
    <script type="text/javascript" src="../../js/UocBaseJs/autoWriteCard.js"></script>
    <script type="text/javascript" src="../../js/UocBaseJs/cardFunctions.js"></script>
    
    <!--[if lt IE 9]>
        <link type="text/css"href="../../style/css/PIE.htc" rel="stylesheet" />
        <script type="text/javascript" src="../../js/common/PIE_IE678.js"></script>
        <style>
            .writeCard_info,.writeCard_info_label,.text_input{position:relative;behavior: url(../../style/css/PIE.htc);z-index:100;}
        </style>
    <![endif]-->
    
</head>
<body>
	<div class="container">
		<div class="writeCard_content">
        	<ul class="writeCard_info">
            	<li class="text_left">
                	<div class="label">当前部门：</div>
                    <div class="text" id="currentDepart"></div>
                </li>
                <li class="text_mid" id="writedNum">已写卡订单：<span>0</span></li>
                <li class="text_mid" id="successNum">写卡成功：<span>0</span></li>
                <li class="text_mid" id="failNum">写卡失败：<span>0</span></li>
                <li class="text_right" id="leftTotalNum">待写卡订单：<span>0</span></li>
                
                <li class="text_left">
                	<div class="label">循环写卡：</div>
                    <div class="loop_check cursor" id="isCyclic"></div>
                </li>
                <li class="text_mid">
                	每次写卡数：<input type="text" class="text_input" id="writeCardNum" value="3"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  
    onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/>
                </li>
                <li class="text_mid">写卡间隔（秒）：<input type="text" class="text_input" id="writePeriod" value="20"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  
    onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/></li>
                <li class="text_mid">查询间隔（秒）：<input type="text" class="text_input" id="queryPeriod" value="60"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  
    onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/></li>
                <li class="text_right">产品组：
                <span class="input_box">
					<select id="product_group">
						<option value="">--请选择产品组--</option>
					</select>
				</span>
				</li>
            </ul>
            
            <div class="writeCard_info_label" id="startWriteButton">开始写卡</div>
        </div>
        
        <div class="inline_block">
        	<div class="writeCard_detail_img"></div>
        	<div class="left font14">写卡成功详情记录：</div>
        </div>
        <div class="writeCard_detail" id="successOrder">
        <!-- 	<div>订单3831170110000020002，卡号8986000288331742997，设备号13092123321，2017-01-16 19：26：12，王森</div>
            <div>订单3831170110000020003，卡号8986000288331742998，设备号13092123325，2017-01-16 19：27：24，肖虹</div>
            <div>订单3831170110000020002，卡号8986000288331742997，设备号13092123321，2017-01-16 19：26：12，王森</div>
            <div>订单3831170110000020002，卡号8986000288331742997，设备号13092123321，2017-01-16 19：26：12，王森</div>
            <div>订单3831170110000020002，卡号8986000288331742997，设备号13092123321，2017-01-16 19：26：12，王森</div>
            <div>订单3831170110000020002，卡号8986000288331742997，设备号13092123321，2017-01-16 19：26：12，王森</div> -->
        </div>
        
        <div class="inline_block">
        	<div class="writeCard_detail_img"></div>
        	<div class="left font14">写卡失败详情记录：</div>
        </div>
        <div class="writeCard_detail" id="failOrder">
        	<!-- <div>订单3831170110000020004，卡号8986000288331742999，设备号13092123324，2017-01-16 19：28：55，刘立新</div>
            <div>订单3831170110000020004，卡号8986000288331742999，设备号13092123324，2017-01-16 19：28：55，刘立新</div>
            <div>订单3831170110000020004，卡号8986000288331742999，设备号13092123324，2017-01-16 19：28：55，刘立新</div>
            <div>订单3831170110000020004，卡号8986000288331742999，设备号13092123324，2017-01-16 19：28：55，刘立新</div>
            <div>订单3831170110000020004，卡号8986000288331742999，设备号13092123324，2017-01-16 19：28：55，刘立新</div>
            <div>订单3831170110000020004，卡号8986000288331742999，设备号13092123324，2017-01-16 19：28：55，刘立新</div> -->
        </div>
        
	</div>
	
</body>
</html>

