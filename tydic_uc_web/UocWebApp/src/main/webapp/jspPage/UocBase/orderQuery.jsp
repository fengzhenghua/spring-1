<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>订单查询</title>
	
    <link rel="stylesheet" type="text/css" href="../../style/css/public.css" />
    <link rel="stylesheet" type="text/css" href="../../style/css/orderQuery.css" />    
    <script type="text/javascript" src="../../js/common/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../../js/common/WebUtil.js"></script>
    <script type="text/javascript" src="../../js/common/public.js"></script>
    <script type="text/javascript" src="../../js/UocBaseJs/orderQuery.js"></script>
	<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
	<div class="container">
		<div class="detail_item">
			<ul class="detail_info_body mtop15">
				<li class="line">
					<div class="width100per">
						<span class="bold mright5">受理系统</span>
						<span class="option_box" id="accept_system">
							<!-- <a>BSS</a><a>CBSS</a><a>云销售</a><div class="btn btn_link vertical_baseline" name="more">更多</div>
							<div class="more_option hide">
								<a>测试1</a><a>测试2</a><a>测试3</a>
							</div> -->
						</span>
						<span class="bold mleft10 mright5">受理时间</span>
						<span class="input_box width150"><input type="text" id="accept_time_start" onclick="WdatePicker();"/></span>						
						<span class="mleft5 mright5">TO</span>
						<span class="input_box width150"><input type="text" id="accept_time_end" onclick="WdatePicker();"/></span>
						
						<span class="bold mleft10 mright5">区域</span>
						<span class="input_box mleft5">
							<select id="province_code">
								<!-- <option value="cq">重庆</option> -->
							</select>
						</span>
						<span class="input_box mleft5">
							<select id="">
								<option value="all">全部</option>
							</select>
						</span>
						<span class="bold mleft10">竣工状态</span>
						<span class="radio_box mleft5"><input type="radio" name="finishStatus" value="0" id="check_doing" checked="checked"/>在途</span>
						<span class="radio_box mleft5"><input type="radio" name="finishStatus" value="1" id="check_finish" />竣工</span>
					</div>
				</li>
				<li class="line mtop5">
					<div class="width100per">
						<span class="bold mleft24 mright5">环节</span>
						<span class="option_box" id="tache_code">
							<!-- <a>审核</a><a>写卡</a><a>发货</a><a>激活</a><div class="btn btn_link vertical_baseline" name="more">更多</div>
							<div class="more_option hide">
								<a>审核1</a><a>写卡1</a><a>发货1</a>
							</div> -->
						</span>
						<span class="bold mleft10 mright5">订单状态</span>
						<span class="option_box" id="order_state">
							<!-- <a>待审核</a><a>待写卡</a><a>待激活</a><div class="btn btn_link vertical_baseline" name="more">更多</div>
							<div class="more_option hide">
								<a>已审核</a><a>已写卡</a><a>已激活</a>
							</div> -->
						</span>
						<span class="bold mleft10 mright5">订单ID</span>
						<span class="input_box width150"><input type="text" id="order_no"/></span>
						<div class="btn btn_primary mleft10" id="searchBtn">查询</div>
						<div class="btn btn_default mleft5" id="resetBtn">重置</div>
						<span class="bold mleft10">时间排序</span>
						<span class="radio_box mleft5"><input type="radio" name="timeSort" value="02" id="time_desc" checked="checked"/>最新</span>
						<span class="radio_box mleft5"><input type="radio" name="timeSort" value="01" id="time_asc" />最早</span>
					</div>
				</li>
				<li class="line mtop5 hide" control="foldable">
					<div class="width100per">
						<span class="bold mright5">订单编号</span>
						<span class="input_box width150"><input type="text" id="order_code"/></span>
						<span class="bold mleft10 mright5">客户名称</span>
						<span class="input_box width150"><input type="text" id="cust_name"/></span>
						<span class="bold mleft10 mright5">证件号码</span>
						<span class="input_box width150"><input type="text" id="cert_no"/></span>
					</div>
				</li>
				<li class="line mtop5 hide" control="foldable">
					<div class="width100per">
						<span class="bold mright5">业务号码</span>
						<span class="input_box width150"><input type="text" id="acc_nbr"/></span>
						<span class="bold mleft10 mright5">业务类型</span>
						<span class="input_box">
							<select id="oper_code">
								<!-- <option value="4G开户">4G开户</option> -->
							</select>
						</span>
						<span class="bold mleft10 mright5">产品</span>
						<span class="input_box width150"><input type="text" class="cs_pointer" id="prod_code" no="" placeholder="请选择产品" readonly/></span>
<!-- 						<span class="option_box" id="prod_code"> -->
							<!-- <a>腾讯王卡</a><a>滴滴王卡</a><div class="btn btn_link vertical_baseline" name="more">更多</div>
							<div class="more_option hide">
								<a>测试1</a><a>测试2</a><a>测试3</a>
							</div> -->
<!-- 						</span> -->
						<span class="bold mleft10 mright5">支付方式</span>
						<span class="option_box" id="pay_type">
							<!-- <a>在线支付</a><a>现金支付</a><div class="btn btn_link vertical_baseline" name="more">更多</div>
							<div class="more_option hide">
								<a>测试1</a><a>测试2</a><a>测试3</a>
							</div> -->
						</span>
					</div>
				</li>
				<li class="line mtop5 hide" control="foldable">
					<div class="width100per">
						<span class="bold mright5">受理工号</span>
						<span class="input_box width150"><input type="text" class="cs_pointer" id="accept_oper_no" no="" placeholder="请选择受理工号" readonly/></span>
						<span class="bold mleft10 mright5">受理部门</span>
						<span class="input_box width150"><input type="text" class="cs_pointer" id="accept_depart_no" no="" placeholder="请选择受理部门" readonly/></span>
						<span class="bold mleft10 mright5"">SIM卡号</span>
						<span class="input_box width150"><input type="text" id="sim_id"/></span>
					</div>
				</li>
				<li class="more_condition">
					<div class="more_condition_btn" on_off="off"><span>更多选项</span><i>▼</i></div>
				</li>
			</ul>
		</div>
		<table class="info_list" id="orderList">
			<thead>
				<tr>
					<th width="30%">订单标识</th>
					<th width="40%">产品信息</th>
					<th width="10%">订单费用</th>
					<th>客户信息</th>
				</tr>
			</thead>
			<tbody id="serv_order_list">
				<!--  <tr>
					<td class="link" title="点击查看详情" order_id="E8312017010711541797493168">
						<p>订单ID：E8312017010711541797493168</p>
						<p>订单编码：762833000022</p>
						<p>受理系统：网上商城</p>
						<p>受理时间：2016-12-23 00:00:00</p>
					</td>
					<td>
						<p>4G全国套餐老用户全加存费送费/业务【网厅专享】【校园专享】</p>
						<p>套餐：4G全国76元套餐</p>
						<p>业务号码：13222222222</p>
					</td>
					<td class="text_center">
						<p>128.00</p>
						<p>在线支付</p>
					</td>
					<td>
						<p>客户名称：张*三</p>
						<p>证件号码：5****************5</p>
					</td>
				</tr> -->
				<!--<tr>
					<td class="link" title="点击查看详情" order_id="E2016121312345622222">
						<p>订单ID：E2016121312345622222</p>
						<p>订单编码：762833000022</p>
						<p>受理系统：网上商城</p>
						<p>受理时间：2016-12-23 00:00:00</p>
					</td>
					<td>
						<p>4G全国套餐老用户全加存费送费/业务【网厅专享】【校园专享】</p>
						<p>套餐：4G全国76元套餐</p>
						<p>业务号码：13222222222</p>
					</td>
					<td class="text_center">
						<p>128.00</p>
						<p>在线支付</p>
					</td>
					<td>
						<p>客户名称：张*三</p>
						<p>证件号码：5****************5</p>
					</td>
				</tr>--> 
			</tbody>
		</table>
		<div class="pagination">
			<span class="mright10">共<b class="mleft5 mright5" id="totalCount">0</b>条</span>			
			<span class="input_box width40" >
				<select id="pageSize" value="">
					<option value="5">5</option>
					<option value="10">10</option>
					<option value="20">20</option>
				</select>				
			</span>
			<span class="page_turn" id="pageFirst">&lt;&lt;</span>
			<span class="page_turn" id="pageUp">&lt;</span>
			<span class="input_box width40"><input type="text" class="text_center" id="pageNo" value="1"/></span>
			<span class="page_turn" id="pageDown">&gt;</span>
			<span class="page_turn" id="pageLast">&gt;&gt;</span>
			<span class="mright10">共<b class="mleft5 mright5" id="totalPage">0</b>页</span>	
			<div class="btn btn_default mleft25" id="exportBtn">导出</div>
		</div>
	</div>
	<!-- 受理工号-模态框 -->
	<div class="modal_layer" belong="mask" id="acceptOperNoModal">
		<div class="bg_cover"></div>
		<div class="modal_box">
			<div class="modal_top">
				<span class="modal_title">受理工号</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle" style="margin-top:0;">
				<div class="detail_last_item">
					<ul class="detail_info_body">
						<li class="line">
							<div class="width100per text_center">
								<span class="input_box width150"><input type="text" id="oper_no_search" placeholder="请输入工号进行搜索"/></span>
								<div class="btn btn_primary mleft10" id="acceptOperNoSearchBtn">查询</div>
							</div>
						</li>
						<li class="line">
							<div class="option_box" id="acceptOperNoList">
								<!-- <span class="one_third"><a no="OPER_NO_01">OPER_NO_01</a></span><span class="one_third"><a no="OPER_NO_02">OPER_NO_02</a></span><span class="one_third"><a no="OPER_NO_03">OPER_NO_03</a></span><span class="one_third"><a no="OPER_NO_04">OPER_NO_04</a></span> -->
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" id="acceptOperNoConfirmBtn">确 定</div>
				<div class="btn btn_default" id="acceptOperNoCancelBtn">取 消</div>
			</div>
		</div>
	</div>
	<!-- 受理部门-模态框 -->
	<div class="modal_layer" belong="mask" id="acceptDepartNoModal">
		<div class="bg_cover"></div>
		<div class="modal_box">
			<div class="modal_top">
				<span class="modal_title">受理部门</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle" style="margin-top:0;">
				<div class="detail_last_item">
					<ul class="detail_info_body">
						<li class="line">
							<div class="width100per text_center">
								<span class="input_box width150"><input type="text" id="depart_no_search" placeholder="请输入部门进行搜索"/></span>
								<div class="btn btn_primary mleft10" id="acceptDepartNoSearchBtn">查询</div>
							</div>
						</li>
						<li class="line">
							<div class="option_box" id="acceptDepartNoList">
								<!-- <span class="width50per"><a no="JB">江北红旗河沟营业厅</a></span><span class="width50per"><a no="AA26">志强合作营业厅</a></span><span class="width50per"><a no="AC121">江北中信国美合作营业厅</a></span> -->
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" id="acceptDepartNoConfirmBtn">确 定</div>
				<div class="btn btn_default" id="acceptDepartNoCancelBtn">取 消</div>
			</div>
		</div>
	</div>
	<!-- 产品-模态框 -->
	<div class="modal_layer" belong="mask" id="prodCodeModal">
		<div class="bg_cover"></div>
		<div class="modal_box">
			<div class="modal_top">
				<span class="modal_title">产 品</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle" style="margin-top:0;">
				<div class="detail_last_item">
					<ul class="detail_info_body">
						<li class="line">
							<div class="width100per text_center">
								<span class="input_box width150"><input type="text" id="prod_code_search" placeholder="请输入产品进行搜索"/></span>
								<div class="btn btn_primary mleft10" id="prodCodeSearchBtn">查询</div>
							</div>
						</li>
						<li class="line">
							<div class="option_box" id="prodCodeList">
							
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" id="prodCodeConfirmBtn">确 定</div>
				<div class="btn btn_default" id="prodCodeCancelBtn">取 消</div>
			</div>
		</div>
	</div>
</body>
</html>