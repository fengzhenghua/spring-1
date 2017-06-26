<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tydic.unicom.util.DateUtil"%>
<%@ taglib prefix="t" uri="http://tydic.com/jsp/core"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>2/3G转4G处理</title>

<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/card.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/showOrder.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/pre_accept.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/posPayFee.js"></script>
<script type="text/javascript" src="<%=fullPath%>js/transfer23To4.js"></script>
<!--[if lt IE 9]>
    <script type="text/javascript" src="<%=fullPath%>js/json2.js"></script>
<![endif]-->

</head>
<body>
	<object classid="clsid:10946843-7507-44FE-ACE8-2B3483D179B7" id="CVR_IDCard" name="CVR_IDCard" width="0" height="0"></object>
	<div class="show" style="height:auto;">
		<div class="info">
			<div class="title text_big">
				<span class="text_large24">1</span>查询
			</div>
			<ul class="detail">
				<div class="padding_blank10"></div>
				<li class="list_padding_zero" style="height:auto;">
					<div class="left">
						<div class="left_lable">证件类型：</div>
						<div class="left_lable_quarter">
							<select id="" class="select_down">
								<option value="ID001">身份证</option>
								<!-- <option value="">护照</option>
								<option value="">警察证</option>
								<option value="">军官证</option> -->
							</select>
						</div>
						<div class="left_lable">证件编号：</div>
						<div class="left_data_quarter">
							<input type="text" id="inputIdNumber" name="inputIdNumber" placeholder=""></input>
						</div>
					</div>
					<div class="right">
						<div class="left_lable">设备号码：</div>
						<div class="left_data_quarter">
							<input type="text" id="inputDeviceNumber" name="inputDeviceNumber" placeholder=""></input>
						</div>
						<div class="left_lable">
							<div class="left_data">
								<span class=" space12"></span>
							</div>
							<div class="left_data">
								<a href="javascript:void(0);" class="search" id="searchBtn">查询</a>
							</div>
							<div class="left_data">
								<span class=" space12"></span>
							</div>
							<div class="left_data">
								<a href="javascript:void(0);" class="search_no_circle" id="resetBtn">置 空</a>
							</div>
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
				<li class="list_padding_zero" style="height:auto;">
					<div class="charge_info_user">
						<div class="info_user" name="detail" id="detail_0">
							<div class="info_user_detail">
								<div class="left_lable">客户名称：</div>
								<div class="left_data_quarter" id="customerName"><!-- 邓** --></div>
							</div>
							<div class="info_user_detail">
								<div class="left_lable">证件类型：</div>
								<div class="left_data_quarter" id="idType"><!-- 身份证 --></div>
							</div>
							<div class="info_user_detail">
								<div class="left_lable">证件编号：</div>
								<div class="left_data_quarter" id="idNumber"><!-- 5102222222222222 --></div>
							</div>
							<div class="info_user_detail">
								<div class="left_lable">客户级别：</div>
								<div class="left_data_quarter" id="customerLevel"><!-- 金卡 --></div>
							</div>
						</div>

						<div class="btn_left" id="btn_left"></div>

						<!-- 动态加载 -->
						<!-- <div class="info_user" name="info_detail" id="detail_1" deviceNumber="18600000000">
							<div class="info_user_detail">
								<div class="left_lable">18600000000</div>
							</div>
							<div class="info_user_detail">
								<div class="left_lable">用户状态：</div>
								<div class="left_data_quarter">正常在用</div>
							</div>
							<div class="info_user_detail">
								<div class="left_lable">品<span class="space24"></span>牌：</div>
								<div class="left_data_quarter">沃</div>
							</div>
							<div class="info_user_detail">
								<div class="left_lable">产<span class="space24"></span>品：</div>
								<div class="left_data_quarter" title="186套餐">186套餐</div>
							</div>
							<div class="info_user_detail">
								<div class="left_lable">归属系统：</div>
								<div class="left_data_quarter">CBSS</div>
							</div>
						</div>
						<div class="info_user" name="info_detail" id="detail_2" deviceNumber="dsl1307111223@comcis">
							<div class="info_user_detail">
								<div class="left_lable">dsl1307111223@comcis</div>
							</div>
							<div class="info_user_detail">
								<div class="left_lable">用户状态：</div>
								<div class="left_data_quarter">正常在用</div>
							</div>
							<div class="info_user_detail">
								<div class="left_lable">装机地址：</div>
								<div class="left_data_quarter" title="沃">沃</div>
							</div>
							<div class="info_user_detail">
								<div class="left_lable">
									产<span class="space24"></span>品：
								</div>
								<div class="left_data_quarter" title="[SA8245]636元8M包年组合包">[SA8245]636元8M包年组合包</div>
							</div>
							<div class="info_user_detail">
								<div class="left_lable">归属系统：</div>
								<div class="left_data_quarter">BSS</div>
							</div>
						</div>
						<div class="info_user" name="info_detail" id="detail_3" deviceNumber="ttttttttteeeeee">
							<div class="info_user_detail">
								<div class="left_lable">ttttttttteeeeee</div>
							</div>
							<div class="info_user_detail">
								<div class="left_lable">用户状态：</div>
								<div class="left_data_quarter">正常在用</div>
							</div>
							<div class="info_user_detail">
								<div class="left_lable">装机地址：</div>
								<div class="left_data_quarter" title="沃">沃</div>
							</div>
							<div class="info_user_detail">
								<div class="left_lable">
									产<span class="space24"></span>品：
								</div>
								<div class="left_data_quarter" title="636元8M包年组合包（测试内容过长时的显示问题）">636元8M包年组合包（测试内容...</div>
							</div>
							<div class="info_user_detail">
								<div class="left_lable">归属系统：</div>
								<div class="left_data_quarter">BSS</div>
							</div>
						</div> -->

						<!-- 选中 -->
						<div class="btn_right" id="btn_right"></div>
					</div>
				</li>
				<div class="padding_blank10"></div>
			</ul>
			<div class="padding_blank10"></div>
		</div>
		<div class="info">
			<div class="title text_big">
				<span class="text_large24">3</span>限制条件
			</div>
			<ul class="detail">
				<div class="padding_blank10"></div>
				<table class="table_infos" id="limitInfos">
					<thead>
						<tr>
							<th width="10%">错误编码</th>
							<th width="20%">错误原因</th>
							<th width="10%">查看详情</th>
							<th width="30%">操作提示</th>
							<th width="20%">一键处理</th>
							<th>处理结果</th>
						</tr>
					</thead>
					<tbody>
						<!-- 动态加载 -->
					<%-- <tr code="1307" radioName="radio_1">
							<td class="text_center">1307</td>
							<td>用户在协议期内</td>
							<td class="text_center">
								<img src="<%=fullPath%>images/deal_detail.png" width="17" height="17" onclick="showDetail()" />
							</td>
							<td>人工操作：报信息化部取消</td>
							<td></td>
							<td class="text_center">待处理</td>
						</tr>
						<tr code="1303" radioName="radio_2">
							<td class="text_center">1303</td>
							<td>用户为集团成员</td>
							<td class="text_center">
								<img src="<%=fullPath%>images/deal_detail.png" width="17" height="17"/>
							</td>
							<td>系统操作：可选择一键处理</td>
							<td>
								<div><input type="radio" name="radio_2" />取消集团</div>
							</td>
							<td class="text_center">
								<img src="<%=fullPath%>images/deal_success.png"/>
							</td>
						</tr>
						<tr code="1603" radioName="radio_3">
							<td class="text_center">1603</td>
							<td>当前用户合约不能转4G</td>
							<td class="text_center">
								<img src="<%=fullPath%>images/deal_detail.png" width="17" height="17"/>
							</td>
							<td>人工操作：报信息化部取消</td>
							<td></td>
							<td class="text_center">
								<img src="<%=fullPath%>images/deal_fail.png"/>
							</td>
						</tr>
						<tr code="1332" radioName="radio_4">
							<td class="text_center">1332</td>
							<td>主副卡业务未退订</td>
							<td class="text_center">
								<img src="<%=fullPath%>images/deal_detail.png" width="17" height="17"/>
							</td>
							<td>系统操作：可选择一键处理</td>
							<td>
								<div><input type="radio" name="radio_4" />自动将副卡转为标准资费</div>
								<div><input type="radio" name="radio_4" />副卡销户</div>
								<div><input type="radio" name="radio_4" />副卡转其他资费</div>
							</td>
							<td class="text_center">
								<img src="<%=fullPath%>images/deal_waiting.gif"/>
							</td>
						</tr>
						<tr code="1203" radioName="radio_5">
							<td class="text_center">1203</td>
							<td>该用户是黑名单用户</td>
							<td class="text_center">
								<img src="<%=fullPath%>images/deal_detail.png" width="17" height="17"/>
							</td>
							<td>人工操作：欠费黑名单，请到缴费页面进行缴费（测试显示内容过长）</td>
							<td></td>
							<td class="text_center">待处理</td>
						</tr>
						<tr code="1204" radioName="radio_6">
							<td class="text_center">1204</td>
							<td>该用户是黑名单用户</td>
							<td class="text_center">
								<img src="<%=fullPath%>images/deal_detail.png" width="17" height="17"/>
							</td>
							<td>人工操作：普通黑名单，分公司做取消操作</td>
							<td></td>
							<td class="text_center">待处理</td>
						</tr> --%>
					</tbody>
				</table>
				<div class="padding_blank10"></div>
			</ul>
			<div class="padding_blank10"></div>
		</div>
		<div class="padding_blank"></div>
		<div class="text_large text_center">
			<div class="ok" style="display:inline;">
				<a href="javascript:void(0);" id="cancel">一键取消</a>
				</div>
			<div class="ok" style="display:inline;">
				<a href="javascript:void(0);" id="fill_sheet">免填单</a>
			</div>
		</div>
		<div class="padding_blank"></div>
	</div>

	<div class="bg_mask" id="bg_mask">
		<iframe class="bg_mask_iframe"> </iframe>
	</div>
	
		<!-- 弹出窗口 装机地址选择： -->
	<div class="pop_win_23to4" id="zjdz_search" style="display: none;">
		<div class="msgbox">
			<a href="javascript:hidediv('zjdz_search');">
				<div class="msgbox_close"></div>
			</a>
			<ul class="text_big">
				<li><span class="bold">查看详情</span></li>
			</ul>
			<div class="band_phone_small_23to4">	
			</div>
			<table class="table_infos_paylog" id="paylogTable">
				<thead>
					<tr>
						<th width="10%">集团代码</th>
						<th width="12%">集团名称</th>
						<th width="12%">集团标志</th>
						<th width="8%">集团类型</th>
						<th width="11%">成员状态</th>
						<th width="12%">生效时间</th>
						<th width="12%">失效时间</th>
						<th width="12%">操作时间</th>
						<th >操作员</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>
	
	
	<input type="hidden" id="img_dir" value="<%=fullPath%>images/"/>
	<input type="hidden" id="oper_no" value="${oper_no}"/>
	<input type="hidden" id="province_code" value="${province_code}"/>
	<input type="hidden" id="order_id" value="0">
	
	<div class="copyright"> Copyright © 2014 China unicom All Right Reserved</div>
</body>
</html>