<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>OPPO卡触点接入</title>
    <link rel="stylesheet" type="text/css" href="../../style/css/oppoCard.css" />
    <script type="text/javascript" src="../../js/common/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../../js/common/zepto.min.js"></script>
    <script type="text/javascript" src="../../js/common/WebUtil.js"></script>
</head>

<body>
<div class="main-page" style="display:block;">
	<!-- 
	<div id="head_buttons" class="top black-bgcolor">
		<div id="allt_left" class="top_left"><span class="top_left_icon"></span><span class="white"></span></div>
		<div id="allt_right" class=""></div>
		<div class="top_title white">1元流量王</div>
	</div>
	<div class="top_blank"></div>
     -->
	<ul class="content_open_card">
    	<div class="clear"></div>
        <div class="open_card_title">请选择产品</div>
        <div class="clear"></div>
        <div class="open_card_choose">
        	<div class="open_card_ed open_card_clicked" name="ofr_div" data="0">
        		<div class="open_card_ed_vip">用终端免月租</div>
            	<div class="open_card_chip"></div>
                <div class="text-vsmall" id="ofr_name0">1元流量王</div>
            </div>
            <div class="open_card_un" name="ofr_div" data="1">
            	<div class="open_card_chip_un"></div>
                <div class="text-vsmall" id="ofr_name1">1元流量王</div>
            </div>
        	<div class="open_card_info" id="ofr_desc">
            	<div class=""><span class="red">1</span>元=<span class="red">500</span>M，不用不花钱</div>
                <div class="text-vsmall gray999">（免三年月费，双卡手机必备）</div>
            </div>
            
            <div class="padding_blank10"></div>
           	<div class="text-small red text-right" style=" text-decoration:underline;" id="show_detail">查看资费详情</div>
            
        </div>
		<div class="padding_blank"></div>
        <li class="clear"></li>
        <li class="bottom_blank"></li>
            
	</ul>
	<div class="bottom">
		<div id="next_flow_step" class="next next_blank">立即申请</div>
	</div>
</div>

<div  id="expenses_deatil" class="dialog" style="height:auto;">
	<div class="tip">套餐资费</div>
    <div class="holder " style=" padding-left:2%; line-height:20px;">
        <table style=" width:98%;"  style="border-right:1px solid #FFF;">
            <tr style="border:1px solid #FFF;">           
                <td width="30%" align="left" valign="middle" style="border-right:1px solid #FFF;vertical-align:middle;">月租</td>
                <td width="60%" align="left" valign="middle" style=" padding-left:5px; word-break:break-all;">16元</td>
            </tr>
            <tr style="border:1px solid #FFF;">           
                <td width="30%" align="left" valign="middle" style="border-right:1px solid #FFF;vertical-align:middle;">省内流量（1元流量包）</td>
                <td width="60%" align="left" valign="middle" style=" padding-left:5px; word-break:break-all;">
                	<div><span>&radic;</span><span class="space6"></span>1元包500M省内流量；</div>
					<div><span>&radic;</span><span class="space6"></span>当天超过500M还按上述规则自动叠加；</div>
					<div><span>&radic;</span><span class="space6"></span>流量当日有效，不结转次日使用。</div>
				</td>
            </tr>
            <tr style="border:1px solid #FFF;">           
                <td width="30%" align="left" valign="middle" style="border-right:1px solid #FFF;vertical-align:middle;">国内流量</td>
                <td width="60%" align="left" valign="middle" style=" padding-left:5px; word-break:break-all;">0.27元/MB（执行流量放心用功能）</td>
            </tr>
            <tr style="border:1px solid #FFF;">           
                <td width="30%" align="left" valign="middle" style="border-right:1px solid #FFF;vertical-align:middle;">省内主叫长市话</td>
                <td width="60%" align="left" valign="middle" style=" padding-left:5px; word-break:break-all;">0.3元/分钟</td>
            </tr>
            <tr style="border:1px solid #FFF;">           
                <td width="30%" align="left" valign="middle" style="border-right:1px solid #FFF;vertical-align:middle;">省内被叫</td>
                <td width="60%" align="left" valign="middle" style=" padding-left:5px; word-break:break-all;">省内免费</td>
            </tr>
            <tr style="border:1px solid #FFF;">           
                <td width="30%" align="left" valign="middle" style="border-right:1px solid #FFF;vertical-align:middle;">国内通话主被叫</td>
                <td width="60%" align="left" valign="middle" style=" padding-left:5px; word-break:break-all;">0.3元/分钟</td>
            </tr>
        </table>
		<table style=" width:98%;">
            <tr style="border-left:1px solid #FFF;border-right:1px solid #FFF;">      
                <td width="90%" align="left" valign="middle" style=" padding-left:5px; word-break:break-all;">1.月租费16元/月，入网首月免月租，次月起1日统一扣费</td>
            </tr>
            <tr style="border-left:1px solid #FFF;border-right:1px solid #FFF;">      
                <td width="90%" align="left" valign="middle" style=" padding-left:5px; word-break:break-all;">2.来电显示不默认开通，用户可选择“来显及炫铃”可选功能包（6元/月）</td>
            </tr>
            <tr style="border-left:1px solid #FFF;border-right:1px solid #FFF;border-bottom:1px solid #FFF;">      
                <td width="90%" align="left" valign="middle" style=" padding-left:5px; word-break:break-all;">3.短彩信0.1元/条，其他业务按标准资费</td>
            </tr>
            
        </table>
        <div class="clear"></div>
    </div>
    
    <div class="btn">
        <a href="javascript:void(0);" id="hide_detail">关 闭</a>
    </div>

</div>
</body>
</html>