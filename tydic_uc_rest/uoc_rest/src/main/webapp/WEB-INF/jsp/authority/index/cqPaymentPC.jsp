<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
 <%@page import="org.jasig.cas.client.util.AssertionHolder"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>云销售-收费</title>
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/pre_accept.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/cqPaymentPC.js"></script>
</head>
<body>
<input type="hidden" id = "operNo" value="${operNo}" />
<div class="show">
    <div class="info">
        <div class="title text_big">
            <span class="text_large24">1</span>证件查询
        </div>
        
        <ul class="detail">
        	<div class="padding_blank10"></div>
            <li class="list_padding_zero">
            	<div class="left">
                	<div class="left_lable">证件类型：</div>
                    <div class="left_lable_quarter">
                    	<select id="" class="select_down">
                            <option>身份证</option>
                        </select>
                    </div>
                    <div class="left_lable">证件编号：</div>
                    <div class="left_data_quarter"><input type="text" id="idNumber" name="idNumber" placeholder=""></input></div>
                </div>
                <div class="right">
                	<div class="left_lable">设备号码：</div>
                    <div class="left_lable_quarter">
                    	<div class="left_data_quarter"><input type="text" id="deviceNumber" name="deviceNumber" placeholder=""></input></div>
                    </div>
                   
                    <div class="left_lable">
                        <div class="left_data"><span class=" space12"></span></div>
                    	<div class="left_data"><a onClick="queryInfo()" class="search">查询</a></div>
                        <div class="left_data"><span class=" space12"></span></div>
                        <div class="left_data"><a onClick="clearInput()" class="search_no_circle">置 空</a></div>
                    </div>
                </div>
            </li>
            <div class="padding_blank10"></div>
        </ul>
        <div class="padding_blank10"></div>
    </div> 
    
    <div class="info">
        <div class="title text_big">
            <span class="text_large24">2</span>用户信息
        </div>
        
        <ul class="detail">
        	<div class="padding_blank10"></div>
            <li class="list_padding_zero" style=" height:124px;">
                <div class="charge_info_user">
                	<div class="info_user" id="detail_00" name="detail"></>
                    	<div class="info_user_detail">
                            <div class="left_lable">客户名称：</div>
                            <div class="left_data_quarter" id="custName"></div>
                        </div>
                        <div class="info_user_detail">
                            <div class="left_lable">证件类型：</div>
                            <div class="left_data_quarter" id="idType"></div>
                        </div>
                        <div class="info_user_detail">
                            <div class="left_lable">证件编号：</div>
                            <div class="left_data_quarter" id="iddNumber"></div>
                        </div>
                        <div class="info_user_detail">
                            <div class="left_lable">客户级别：</div>
                            <div class="left_data_quarter" id="cardType"></div>
                        </div>
                    </div>
                    
                    <div class="btn_left" id="btn_left"></div> 
                    <!-- <div class="info_user" id="detail" tabindex="2" onmouseover="this.style.cursor='hand'" onmouseout="this.style.cursor='default'">
                    	<div class="info_user_detail" id="info_user_detail">
                    	    <div class="left_lable">设备号码：</div>
                            <div class="left_data_quarter" id="deviceeNumber"></div>
                        </div>
                        <div class="info_user_detail">
                            <div class="left_lable">用户状态：</div>
                            <div class="left_data_quarter" id="deviceSatus"></div>
                        </div>
                        <div class="info_user_detail">
                            <div class="left_lable">品<span class="space24"></span>牌：</div>
                            <div class="left_data_quarter" id="deviceName"></div>
                        </div>
                        <div class="info_user_detail" id = "info_address">
                            <div class="left_lable">装机地址:</div>
                            <div class="left_data_quarter" id="deviceAddress"></div>
                        </div>
                        <div class="info_user_detail">
                            <div class="left_lable">产<span class="space24"></span>品：</div>
                            <div class="left_data_quarter" id="deviceProduct"></div>
                        </div>
                        <div class="info_user_detail">
                            <div class="left_lable">归属系统：</div>
                            <div class="left_data_quarter" id="deviceGuishu"></div>
                        </div>
                    </div> -->
                    
                    <!-- 选中 -->
                    <div class="btn_right_more" id="btn_right"></div>
                </div>
            </li>
            
            <div class="padding_blank10"></div>   
        </ul>
        
        <div class="padding_blank10"></div>
    </div> 
    
    <div class="info" id="broandInfo">
        <div class="title text_big">
            <span class="text_large24">3</span>详情展示
        </div>
        
        <ul class="detail">
        	<div class="padding_blank10"></div>
            <li class="list_padding_zero" style="*display:inline;">
                <div class="order_row">
                    <div class="order_cell_charge">包年费</div>
                    <div class="order_cell_charge">宽带应缴费</div>
                    <div class="order_cell_charge">优惠费用</div>
                    <div class="order_cell_charge">折扣率</div>
                    <div class="order_cell_charge">附加套餐金额</div>
                    <div class="order_cell_charge">历史欠费</div>
                    <div class="order_cell_charge">违约金</div>
                    <div class="order_cell_charge">信用额度</div>
                </div>
            </li>
            
            <li class="list" style="*display:inline;">
                <div class="order_row white_bgcolor">
                    <div class="order_cell_charge red" id="total_fee"></div>
                    <div class="order_cell_charge red" id="pre_charge"></div>
                    <div class="order_cell_charge red" id="dis_charge"></div>
                    <div class="order_cell_charge red" id="discount_rate"></div>
                    <div class="order_cell_charge red" id="extra_charge"></div>
                    <div class="order_cell_charge red" id="owe_charge"></div>
                    <div class="order_cell_charge red" id="liquidated_charge"></div>
                    <div class="order_cell_charge red" id="available_credit"></div>
                    <div class="order_cell_charge red" id="business_flag"></div>
                    <div class="order_cell_charge red" id="tele_type"></div>
                </div>
            </li>
            <div class="padding_blank10"></div>
		</ul>
        <div class="padding_blank10"></div>
    </div> 
	
	<div class="info" id="phoneInfo">
        <div class="title text_big">
            <span class="text_large24">3</span>余额展示
        </div>
        
        <ul class="detail">
        	<div class="padding_blank10"></div>
            <li class="list_padding_zero" style="*display:inline;">
                <div class="order_row">
                    <div class="order_cell_charge">当前可用余额</div>
                    <div class="order_cell_charge">当前实时话费</div>
                    <div class="order_cell_charge">普通预存款</div>
                    <div class="order_cell_charge">当前可用分摊款</div>
                    <div class="order_cell_charge">当月赠款</div>
                    <div class="order_cell_charge">历史欠费</div>
                    <div class="order_cell_charge">违约金</div>
                    <div class="order_cell_charge">信用额度</div>
                </div>
            </li>
            
            <li class="list" style="*display:inline;">
                <div class="order_row white_bgcolor">
                    <div class="order_cell_charge red" id="sumBalance"></div>
                    <div class="order_cell_charge red" id="sumCharge"></div>
                    <div class="order_cell_charge red" id="prepayBalance"></div>
                    <div class="order_cell_charge red" id="apportionCharge"></div>
                    <div class="order_cell_charge red" id="grantsCharge"></div>
                    <div class="order_cell_charge red" id="oweCharge"></div>
                    <div class="order_cell_charge red" id="liquidatedCharge"></div>
                    <div class="order_cell_charge red" id="availableCredit"></div>
                </div>
            </li>
            <div class="padding_blank10"></div>
		</ul>
        <div class="padding_blank10"></div>
    </div> 
	
 	<div class="info">
        <div class="title text_big">
            <span class="text_large24">4</span>缴费
        </div>
        
        <ul class="detail">
        	<div class="padding_blank10"></div>
            <li class="list_padding_zero">
            	<div class="left">
                	<div class="left_lable">交费方式：</div>
                    <div class="left_lable_quarter">
                    	<select id="" class="select_down">
                            <option>坐席收费</option>
                        </select>
                    </div>
                    <div class="left_lable">交费：</div>
                    <div class="left_data_quarter"><input type="text" id="payMoney" placeholder="" class="red"></input></div>
                </div>
            </li>
            <div class="padding_blank10"></div>
		</ul>
        <div class="padding_blank10"></div>
    </div> 
        
    <div class="padding_blank"></div> 
    <div class="padding_blank"></div>
    <li class="text_large">
        <div class="ok" id="submit"><a onClick="next()">马上缴费</a></div>
        
    </li> 
    <div class="padding_blank"></div>
  
</div>

</body>
</html>