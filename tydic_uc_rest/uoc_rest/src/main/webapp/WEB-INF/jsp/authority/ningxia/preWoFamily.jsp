<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>智慧沃家预受理</title>
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/pre_accept.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/nxPreWoFamily.js"></script>
<script type="text/javascript" src="<%=fullPath%>js/json2.js"></script>
</head>
<body>
<div class="show" style="*height:512px;">
    <div class="info">
    	
       <div class="padding_blank10"></div>
       <div class="padding_blank"></div>

        
       <div class="charge_area" style="height:984px;">
        	<ul style="padding-left: 10px;">
        		<li class="list left" style=" width:454px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">客<span class="space4"></span>户<span class="space4"></span>名<span class="space4"></span>称：</div>
                        <div class="left_data">
                            <input type="text" class="width_25 text_large" id="customer_name" placeholder="请输入客户名称。" value="" ></input>
                        </div>
                    </div>
                </li>
            	<li class="list left" style=" width:454px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">证<span class="space4"></span>件<span class="space4"></span>号<span class="space4"></span>码：</div>
                        <div class="left_data">
                            <input type="text" class="width_25 text_large" id="id_number" placeholder="请输入证件号码。" value="" ></input>
                        </div>
                    </div>
                </li>
				<li class="list left" style=" width:454px; height:36px;" id="">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">订单归属类型：</div>
                        <div name="orderbelong_type_select" class="radio_checked" value="本地"></div>
                        <div class="left_data text_large">
                        	本地
                        </div>
                        <span class="space4"></span>
                        <div name="orderbelong_type_select" class="radio" value="总部" id=""></div>
                        <div class="left_data text_large" id="">
                        	总部
                        </div>
                    </div>
                </li>
                <li class="list left" style=" width:590px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">手<span class="space4"></span>机<span class="space4"></span>号<span class="space4"></span>码(GSM)：</div>
                        <div class="left_data" id="">
                        	<input type="text" class="width_35 text_large" id="acc_nbr_GSM" placeholder="请输入手机号码(GSM)。多个号码用逗号隔开" value="" ></input> 
                        </div>
                    </div>
                </li>
            	<li class="list left" style=" width:454px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">手机号码(WCDMA)：</div>
                        <div class="left_data" id="">
                        	<input type="text" class="width_26 text_large" id="acc_nbr_WCDMA" placeholder="请输入手机号码(WCDMA)。" value="" ></input>
                        </div>
                    </div>
                </li>
                <li class="list left" style=" width:454px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">订<span class="space4"></span>单<span class="space4"></span>类<span class="space4"></span>型：</div>
                        <div name="dingdan_type_select" class="radio_checked" value="新装"></div>
                        <div class="left_data text_large">
                        	新装
                        </div>
                        <span class="space4"></span>
                        <div name="dingdan_type_select" class="radio" value="纳入" id=""></div>
                        <div class="left_data text_large" id="">
                        	纳入
                        </div>
                        <span class="space4"></span>
                        <div name="dingdan_type_select" class="radio" value="迁转" id=""></div>
                        <div class="left_data text_large" id="">
                        	迁转
                        </div>
                        <span class="space4"></span>
                        <div name="dingdan_type_select" class="radio" value="拆机" id=""></div>
                        <div class="left_data text_large" id="">
                        	拆机
                        </div>
                        <span class="space4"></span>
                        <div name="dingdan_type_select" class="radio" value="拆分" id=""></div>
                        <div class="left_data text_large" id="">
                        	拆分
                        </div>
                    </div>
                </li>
            	
                <li class="list left" style=" width:590px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">产<span class="space4"></span>品<span class="space4"></span>类<span class="space4"></span>型：</div>
                        <div name="chanpin_type_select" class="radio_checked" value="组合产品"></div>
                        <div class="left_data text_large">
                        	组合产品
                        </div>
                        <span class="space4"></span>
                        <div name="chanpin_type_select" class="radio" value="共享产品" id=""></div>
                        <div class="left_data text_large" id="">
                        	共享产品
                        </div>
                        <span class="space4"></span>
                        <div name="chanpin_type_select" class="radio" value="手机产品" id=""></div>
                        <div class="left_data text_large" id="">
                        	手机产品
                        </div>
                        <span class="space4"></span>
                        <div name="chanpin_type_select" class="radio" value="宽带产品" id=""></div>
                        <div class="left_data text_large" id="">
                        	宽带产品
                        </div>
                        <span class="space4"></span>
                        <div name="chanpin_type_select" class="radio" value="融合产品" id=""></div>
                        <div class="left_data text_large" id="">
                        	融合产品
                        </div>
                    </div>
                </li>
                
	            <li class="list left" style=" width:454px; height:36px;" id="">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">首<span class="space4"></span>月<span class="space4"></span>付<span class="space4"></span>费：</div>
                        <div name="first_type_select" class="radio_checked" value="标准资费"></div>
                        <div class="left_data text_large">
                        	标准资费
                        </div>
                        <span class="space4"></span>
                        <div name="first_type_select" class="radio" value="全月套餐" id=""></div>
                        <div class="left_data text_large" id="">
                        	全月套餐
                        </div>
                        <span class="space4"></span>
                        <div name="first_type_select" class="radio" value="套餐减半" id=""></div>
                        <div class="left_data text_large" id="">
                        	套餐减半
                        </div>
                    </div>
                </li>
                <li class="list left" style=" width:454px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">宽<span class="space4"></span>带<span class="space4"></span>号<span class="space4"></span>码：</div>
                        <div class="left_data" id="">
                        	<input type="text" class="width_25 text_large" id="pay_no_165" placeholder="请输入宽带号码。" value="" ></input>
                        </div>
                    </div>
                </li>
                <li class="list left" style=" width:454px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">固<span class="space4"></span>网<span class="space4"></span>账<span class="space4"></span>号：</div>
                        <div class="left_data" id="">
                        	<input type="text" class="width_25 text_large" id="account_code" placeholder="请输入固网账号。" value="" ></input>
                        </div>
                    </div>
                </li>
                <li class="list left" style=" width:454px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">接<span class="space4"></span>入<span class="space4"></span>方<span class="space4"></span>式：</div>
                        <div class="left_data" id="">
                        	<input type="text" class="width_25 text_large" id="access_mode" placeholder="请输入接入方式。" value="" ></input>
                        </div>
                    </div>
                </li>
                <li class="list left" style=" width:454px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">速<span class="space36"></span><span class="space9"></span>率：</div>
                        <div class="left_data text_large" id="">
                        	<input type="text" class="width_25 text_large" id="speed_level" placeholder="请输速率。" value="" ></input>
                        </div>
                    </div>
                </li>
                <li class="list left" style=" width:454px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">标<span class="space4"></span>准<span class="space4"></span>地<span class="space4"></span>址：</div>
                        <div class="left_data" id="">
                        	<input type="text" class="width_25 text_large" id="address_name" placeholder="请输入地址名称。" value="" ></input>
                        </div>
                    </div>
                </li>
                <li class="list left" style=" width:454px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">共<span class="space4"></span>享<span class="space4"></span>产<span class="space4"></span>品：</div>
                        <div class="left_data" id="">
                        	<input type="text" class="width_25 text_large" id="share_product" placeholder="请输入共享产品。" value="" ></input>
                        </div>
                    </div>
                </li>
                <li class="list left" style=" width:454px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">手机2G附加包：</div>
                        <div class="left_data" id="">
                        	<input type="text" class="width_32 text_large" id="2G_add_package" placeholder="请输入手机2G附加包,多个附加包用逗号隔开" value="" ></input>
                        </div>
                    </div>
                </li>
                <li class="list left" style=" width:454px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">手机3G附加包：</div>
                        <div class="left_data" id="">
                        	<input type="text" class="width_32 text_large" id="3G_add_package" placeholder="请输入手机3G附加包。" value="" ></input>
                        </div>
                    </div>
                </li>
                <li class="list left" style=" width:454px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">宽带附加包：</div>
                        <div class="left_data" id="">
                        	<input type="text" class="width_32 text_large" id="lan_add_package" placeholder="请输入宽带附加包。" value="" ></input>
                        </div>
                    </div>
                </li>
                <li class="list left" style=" width:454px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">固话附加包：</div>
                        <div class="left_data" id="">
                        	<input type="text" class="width_32 text_large" id="fixed_line_package" placeholder="请输入固话附加包。" value="" ></input>
                        </div>
                    </div>
                </li>
                <li class="list left" style=" width:454px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">费<span class="space4"></span>用<span class="space4"></span>信<span class="space4"></span>息：</div>
                        <div class="left_data" id="">
                        	<input type="text" class="width_25 text_large" id="fee_info" placeholder="请输入费用信息。" value="" ></input>
                        </div>
                    </div>
                </li>
                <li class="list left" style=" width:454px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">工<span class="space36"></span><span class="space9"></span>号：</div>
                        <div class="left_data" id="">
                        	<input type="text" class="width_25 text_large" id="oper_no" placeholder="请输入工号。" value="" ></input>
                        </div>
                    </div>
                </li>
                <li class="list left" style=" width:454px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">密<span class="space36"></span><span class="space9"></span>码：</div>
                        <div class="left_data" id="">
                        	<input type="text" class="width_25 text_large" id="pass_word" placeholder="请输入密码。" value="" ></input>
                        </div>
                    </div>
                </li>
                <li class="list left" style=" width:454px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">活<span class="space4"></span>动<span class="space4"></span>名<span class="space4"></span>称：</div>
                        <div class="left_data" id="">
                        	<input type="text" class="width_25 text_large" id="activity_name" placeholder="请输入活动名称。" value="" ></input>
                        </div>
                    </div>
                </li>
                
                <li class="list left" style=" width:454px; height:36px;">
                    <div class="left_lable">
                        <div class="left_lable text_large bold">备<span class="space4"></span>注<span class="space4"></span>信<span class="space4"></span>息：</div>
                        <div class="left_data" id="">
                        	<input type="text" class="width_25 text_large" id="backup_info" placeholder="请输入备注信息。" value="" ></input>
                        </div>
                    </div>
                </li>
            </ul>
            <div class="clear"></div>
            <div class="charge_line"></div>
            
            <div class="padding_blank"></div>
            <div class="text_large">
                <div class="text_center">
                    <span class="ok" id="btn_submit"><a href="javascript:void(0)" id="btn_submit">提  交</a></span>
                </div>
            </div>
        </div>

    </div>
  <div class="padding_blank"></div>
  <div class="bottom_blank"></div>
  
</div>
<div class="bg_mask" id="bg_mask">
	<iframe class="bg_mask_iframe"> </iframe>
</div>
<input type="hidden" value="${jsessionid}" id="jsessionid" />
</body>
</html>