<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" uri="http://tydic.com/jsp/core" %>
<%@include file="../common/common.jsp" %>
<html>
<head>
    <title>我的主页</title>

    <link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css"/>
    <link href="<%=fullPath%>css/home.css" rel="stylesheet" type="text/css"/>
    <link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<%=fullPath%>js/home_gx.js"></script>
    <!--[if lt IE 9]>
    <script type="text/javascript" src="<%=fullPath%>js/json2.js"></script>
    <![endif]-->

</head>
<body>
<div class="show" style="height:auto;">
    <div class="padding_blank10"></div>
    <div class="container">
        <div class="top_area">
            <div class="top_search">
                <input type="text" value="请输入用户号码，搜索常用菜单" placeholder="请输入用户号码，搜索常用菜单" id="input_number" onFocus="this.value='';"  onblur="if (this.value=='') this.value='请输入用户号码，搜索常用菜单';"/>
                <img src="<%=fullPath%>images/home_search.png" width="24" height="24" id="btn_search"/>
            </div>
            <div class="top_list">
                <div class="top_arrow float_left">
                    <i class="arrow_left" id="btn_left"></i>
                </div>
                <div class="top_options float_left" id="dtjzlb">
                    <!-- 动态加载 -->
                    <div class="top_option" id="top_detail_1" style="background:url(<%=fullPath%>images/home_3g_change.png) no-repeat left top;">
                        <div class="top_text">3G套餐变更</div>
                    </div>
                    <div class="top_option" id="top_detail_2" style="background:url(<%=fullPath%>images/home_supply_card.png) no-repeat left top;">
                        <div class="top_text">补卡</div>
                    </div>
                    <div class="top_option" id="top_detail_3" style="background:url(<%=fullPath%>images/home_replace_card.png) no-repeat left top;">
                        <div class="top_text">换卡</div>
                    </div>
                    <div class="top_option" id="top_detail_4" style="background:url(<%=fullPath%>images/home_roam_change.png) no-repeat left top;">
                        <div class="top_text">变更漫游</div>
                    </div>
                    <div class="top_option" id="top_detail_5" style="background:url(<%=fullPath%>images/home_product_change.png) no-repeat left top;">
                        <div class="top_text">产品变更</div>
                    </div>
                    <div class="top_option" id="top_detail_6" style="background:url(<%=fullPath%>images/home_product_change.png) no-repeat left top;">
                        <div class="top_text">test1（测试两行内容）</div>
                    </div>
                    <div class="top_option" id="top_detail_7" style="background:url(<%=fullPath%>images/home_product_change.png) no-repeat left top;">
                        <div class="top_text">test2</div>
                    </div>
                </div>
                <div class="top_arrow float_right">
                    <i class="arrow_right" id="btn_right"></i>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="quick_area">
            <div class="business_head">
                <i class="quick_accept_icon"></i>
                <span>快速受理通道</span>
            </div>
            <div class="business_body">
                <div class="quick_options" id="kssltd">
                    <!-- 动态加载 -->
                    <div class="quick_option" id="" style="background:url(<%=fullPath%>images/home_phone_treaty_down.png) no-repeat left top;">
                        <div class="hot_icon"><img src="<%=fullPath%>images/home_hot.png" width="42" height="26"/></div>
                        <div class="quick_text">明星手机合约直降</div>
                    </div>
                    <div class="quick_option" id="" style="background:url(<%=fullPath%>images/home_one_year_free.png) no-repeat left top;">
                        <div class="hot_icon"><img src="<%=fullPath%>images/home_hot.png" width="42" height="26"/></div>
                        <div class="quick_text">一年电话免费打</div>
                    </div>
                    <div class="quick_option" id="" style="background:url(<%=fullPath%>images/home_deposit_gift_treaty.png) no-repeat left top;">
                        <div class="hot_icon"></div>
                        <div class="quick_text">99元本地存费送机合约</div>
                    </div>
                    <div class="quick_option" id="" style="background:url(<%=fullPath%>images/home_buy_down.png) no-repeat left top;">
                        <div class="hot_icon"></div>
                        <div class="quick_text">购机直降</div>
                    </div>
                    <div class="quick_option" id="" style="background:url(<%=fullPath%>images/home_clear_treaty.png) no-repeat left top;">
                        <div class="hot_icon"></div>
                        <div class="quick_text">清库机合约</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="history_area float_left">
            <div class="business_head">
                <i class="business_history_icon"></i>
                <span>常用业务历史</span>
            </div>
            <div class="business_body">
                <div class="history_options" id="cywyls">
                    <!-- 动态加载 -->
                    <div class="history_option" id="">
                        <div class="history_text">统一开户</div>
                    </div>
                    <div class="history_option" id="">
                        <div class="history_text">统一订单处理</div>
                    </div>
                    <div class="history_option" id="">
                        <div class="history_text">改账户定制关系</div>
                    </div>
                    <div class="history_option" id="">
                        <div class="history_text">拆机</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="jump_area float_right">
            <div class="business_head">
                <i class="jump_page_icon"></i>
                <span>跳转首页</span>
            </div>
            <div class="business_body">
                <div class="jump_options">
                    <!-- 动态加载 -->
                    <div class="jump_option" id="">
                        <a href="#" class="jump_text"><i class="triangle_icon"></i>BSS</a>
                    </div>
                    <div class="jump_option" id="">
                        <a href="#" class="jump_text"><i class="triangle_icon"></i>CBSS</a>
                    </div>
                    <div class="jump_option" id="">
                        <a href="#" class="jump_text"><i class="triangle_icon"></i>ESS</a>
                    </div>
                    <div class="jump_option" id="">
                        <a href="#" class="jump_text"><i class="triangle_icon"></i>沃受理</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="padding_blank"></div>
</div>

<div class="bg_mask" id="bg_mask">
    <iframe class="bg_mask_iframe"></iframe>
</div>

<input type="hidden" id="img_dir" value="<%=fullPath%>images/"/>
<input type="hidden" id="oper_no" value="${oper_no}"/>

<div class="copyright"> Copyright © 2014 China unicom All Right Reserved</div>
</body>
</html>