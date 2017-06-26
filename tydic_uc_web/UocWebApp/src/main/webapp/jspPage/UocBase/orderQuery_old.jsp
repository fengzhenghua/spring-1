<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>订单查询</title>
    <script type="text/javascript" src="../../js/jquery-easyui-1.3.5/jquery.min.js"></script>
    <script type="text/javascript" src="../../js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../../js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="http://www.w3cschool.cc/try/jeasyui/datagrid-detailview.js"></script>
    <link rel="stylesheet" type="text/css" href="../../js/jquery-easyui-1.3.5/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="../../js/jquery-easyui-1.3.5/themes/icon.css" />
    
    <link rel="stylesheet" type="text/css" href="../../style/css/public.css" />
    <script type="text/javascript" src="../../js/common/WebUtil.js"></script>
    <script type="text/javascript" src="../../js/UocBaseJs/orderQuery_old.js"></script>
</head>
<body>
	<div class="nav">
		<b>当前位置：</b>
		<span class="bold"><a href="javascript:void(0);">订单查询</a></span>
		&nbsp;&nbsp;<a href="../UocBase/saleOrderDetail.jsp?sale_order_no=''&finish_flag=''">进入下一级页面</a>
		&nbsp;&nbsp;<a href="../UocBase/orderDetail.jsp">订单详情</a>
	</div>
	<div class="container">
		<table id="orderDatagrid"></table>
		<div class="grid_tool_bar" id="tb" style="padding:5px 0;">
			<table>
				<tr>
					<td nowrap>订单号 </td>
					<td><input id="sale_order_no" class="easyui-textbox" style="width:80px"></td>
					<td nowrap>证件号码</td>
					<td><input id="cert_no" class="easyui-textbox" style="width:80px"></td>
					<td nowrap>客户名称</td>
					<td><input id="cust_name" class="easyui-textbox" style="width:70px"></td>
					<td nowrap>业务号码</td>
					<td><input id="acc_nbr" class="easyui-textbox" style="width:70px"></td>
					<td nowrap>订单状态</td>
					<td><input id="order_state" class="easyui-textbox" style="width:70px"></td>
					<td nowrap>开始时间</td>
					<td><input id="accept_time_start" class="easyui-datetimebox" data-options="required:true" style="width:90px"></td>
					<td nowrap>结束时间</td>
					<td><input id="accept_time_end" class="easyui-datetimebox" data-options="required:true" style="width:90px"></td>
					<td nowrap>竣工标识</td>
					<td>
						<select id="finish_flag" class="easyui-combobox" name="finish_flag" style="width:70px;" editable="false">
							<option value="0">未竣工</option>
							<option value="1">已竣工</option>
							<option value="2">全部</option>
						</select>
					</td>
				</tr>
				<tr>
					<td nowrap>受理系统</td>
					<td><input id="accept_system" class="easyui-textbox" style="width:80px"></td>
					<td nowrap>受理流水</td>
					<td><input id="accept_no" class="easyui-textbox" style="width:80px"></td>
					<td nowrap>省份</td>
					<td><input id="province_code" class="easyui-textbox" style="width:70px"></td>
					<td nowrap>地域</td>
					<td><input id="area_code" class="easyui-textbox" style="width:70px"></td>
					<td nowrap>渠道编码</td>
					<td><input id="accept_depart_no" class="easyui-textbox" style="width:70px"></td>
					<td nowrap>订单类型</td>
					<td><input id="order_type" class="easyui-textbox" style="width:90px"></td>
					<td nowrap>受理员工</td>
					<td><input id="accept_oper_no" class="easyui-textbox" style="width:90px"></td>
					<td colspan="2" align="center"><a href="#" style="align:right" class="easyui-linkbutton" iconcls="icon-search" onclick="getData();">查询</a></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>