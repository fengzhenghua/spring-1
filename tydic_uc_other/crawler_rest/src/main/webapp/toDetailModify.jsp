<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单处理详情</title>
<link rel="stylesheet" type="text/css" href="./css/common/common.css" />
<link rel="stylesheet" type="text/css" href="./css/todetail.css" />
<script type="text/javascript" src="./js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="./js/public.js"></script>
<%@ include file="include.jsp"%>
<script>
function CloseWin() // 这个不会提示是否关闭浏览器
{
  window.opener = null;
  window.open("", "_self");
  window.close();
}
		
    	//	var ciuuid = $UTIL.getUrlParam('ciuuid');
    	var username = 'MODIFY';
    	$(function(){
    		  $('#closeBtn').on('click',CloseWin);
    		  finditem();
    	});
    	
    	function finditembtn(){
    		orderid = $('#inporderid').val();
    		finditem();
    	}
    	
    	
    	//检查是否session正确，如果正确跳过登录，如果不正确显示登录
    	function finditem() {
    		$.ajax({
    			crossDomain : true == !(document.all),
    			url : BASEURL + '/rest/getInfoServiceRest/getOrderInfoItem',
    			type : 'POST', //GET
    			async : true, //或false,是否异步
    			data : {
    				json_info : '{"user":"' + username + '","ciuuid":"'
    						+ orderid + '","orderid":"'
    						+ orderid +'"}'
    			},
    			timeout : TIMEOUT, //超时时间
    			dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
    			beforeSend : function(xhr) {
    				console.log(xhr)
    				console.log('发送前')
    			},
    			success : function(data, textStatus, jqXHR) {
    				if (data.respCode == '0000' || data.respCode == '0') {
    					showdata(data);
    				} else {
    				}
    			},
    			error : function(xhr, textStatus) {
    				console.log(xhr);
    				console.log(textStatus);
    			},
    			complete : function() {
    				console.log('结束');
    			}
    		});
    	};
    	
    	function showdata(data){
    		$('#ordernum').text(data.args.item.order_sourceinfo.ordernum);
    		$('#order_createtime').text(data.args.item.order_createtime);
    		//0创建订单，410物理写卡，430同步写卡，450物流发货，530同步发货
    		$('#order_status').text(data.args.item.order_status);
    		$("#order_status_sel").val(data.args.item.order_status);
    		$('#customer_name').text(data.args.item.customer_name);
    		$('#cert_address').text(data.args.item.order_info.params.json_info.cert_address);
    		$('#serial_number').text(data.args.item.order_info.params.json_info.serial_number);
    		
    		$('#receive_name').text(data.args.item.order_info.params.json_info.receive_name);
    		$('#receive_phone').text(data.args.item.order_info.params.json_info.receive_phone);
    		
    		
    		$('#contact_address').text(data.args.item.order_info.params.json_info.contact_address);
    		$('#logistics_no').text(data.args.item.logistics_no);
    		
    		$('#sale_order_no').text(data.args.item.sale_order_no);
    		$('#serv_order_no_list').text(data.args.item.serv_order_no_list);
    		$('#cbssnum').text(data.args.item.cbssnum);
    		$('#usim').text(data.args.item.usim);
    		//$('#commodityname').text(data.args.item.order_sourceinfo.context.0.'商品');
    		$.each(data.args.item.order_sourceinfo.context,function(name,value) {
    			if(name=='0'){
    				$.each(value,function(name,value) {
    					if(name='商品'){
    	    				$('#commodityname').text(value);
    	    			}
    				});
    			}
    			//{"号码":"18581075094(重庆)","套餐":"日租卡","首月":"全月套餐","卡类型":"普卡"},"2":{"姓名":"刘娟","证件":"500382198810117469(18位身份证)","地址":"重庆市合川区大石街道高马村5组89号","推荐人":"","发展人渠道名称":"()"}
    			if(name=='1'){
    				console.log(value);
    				$.each(value,function(pname,pvalue) {
    					if(pname=='套餐'){
    	    				$('#product_cn').text(pvalue);
    	    			}
    					if(pname=='套餐'){
    	    				$('#product_cn').text(pvalue);
    	    			}
    				});
    			}
    		});
    	}
    		var orderid;
    	function modifyitemconfirm(){
    		//请输入密码
    		$.dialog.confirm('请输入密码：<input id="pwd" type="password"></intpu>','输入管理密码','','',modifyitem);
    	}	
    	
    	function modifyitem(){
    		$.loading.show('等待更新状态!');
    		var pwd = $('#pwd').val();
    		$.ajax({
    			crossDomain : true == !(document.all),
    			url : BASEURL + '/rest/writeBackServiceRest/updateOrderStatus',
    			type : 'POST', //GET
    			async : true, //或false,是否异步
    			data : {
    				json_info : '{"user":"' + username + '","pwd":"'
    						+ pwd + '","orderid":"'
    						+ orderid +'","orderstatus":"'
    						+ $("#order_status_sel").val() +'"}'
    			},
    			timeout : TIMEOUT, //超时时间
    			dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
    			beforeSend : function(xhr) {
    				console.log(xhr)
    				console.log('发送前')
    			},
    			success : function(data, textStatus, jqXHR) {
    				if (data.respCode == '0000' || data.respCode == '0') {
    					$.message.success(data.content);
    				} else {
    					$.message.warning(data.content);
    				}
    			},
    			error : function(xhr, textStatus) {
    				console.log(xhr);
    				console.log(textStatus);
    				$.loading.hide();
    			},
    			complete : function() {
    				console.log('结束');
    				$.loading.hide();
    			}
    		});
    	}
    	
    </script>
</head>
<body>
	<div class="width900 contentBtm01">
  <!--============订单状态 begin==============-->
  <h2 class="detailTitle">订单详情</h2>
  <div>
  	<input id='inporderid'></input> <button id='btn_findorder' onclick='finditembtn();'>查询</button>
  </div>
  <div class="infoBoxTop">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><span class="fontStyle">订单编号：</span></td>
        <td class="tdWidthOne"><span id="ordernum"></span></td>
        <td><span class="fontStyle">订单日期：</span></td>
        <td class="tdWidthTwo"><span id="order_createtime"></span></td>
        <td><span class="fontStyle">订单状态：</span></td>
        <td><span id="order_status"></span></td>
      </tr>
      <tr>
        <td><span class="fontStyle">销售编号：</span></td>
        <td class="tdWidthOne"><span id="sale_order_no"></span></td>
        <td><span class="fontStyle">服务编号：</span></td>
        <td class="tdWidthTwo"><span id="serv_order_no_list"></span></td>
        <td><span class="fontStyle">订单状态：</span></td>
        <td><select id="order_status_sel">
        	<option value='0'>爬取成功-等待创单</option>
        	<option value='410'>创单成功-等待写卡</option>
        	<option value='430'>写卡成功-等待同步卡</option>
        	<option value='450'>同步写卡-等待物流</option>
        	<option value='530'>物流成功-等待同步物</option>
        	<option value='550'>同步物流</option>
        	<option value='990'>退单</option>
        	</select>
        <div id='modifystatus' onclick='modifyitemconfirm();'>修改</div></td>
      </tr>
    </table>
  </div>
  <div class="infoBox">
    <h2 class="fontStyle">入网信息：</h2>
    <table width="100%" border="0" cellspacing="2" cellpadding="0">
      <tr>
        <td>客户名称：</td>
        <td><span id="customer_name"></span></td>
      </tr>
      <tr>
        <td>证件地址：</td>
        <td colspan="5"><span id="cert_address"></span></td>
      </tr>
    </table>
  </div>
  <div class="infoBox">
    <h2 class="fontStyle">商品信息：</h2>
    <table widtd="100%" border="0" cellspacing="4" cellpadding="0">
      <tr>
        <td>商品名称：</td> 
        <td colspan="3"><span id="commodityname"></span></td>
        <td></td>
        <td></td>
      </tr>
      <tr>
        <td>订购号码：</td>
        <td><span id="serial_number"></span></td>
        <td>套餐名称：</td>
        <td><span id="product_cn"></span></td>
      </tr>
      <tr>
        <td>CBSS单号：</td>
        <td><span id="cbssnum"></span></td>
        <td>ICCID：</td>
        <td><span id="usim"></span></td>
      </tr>
    </table>
  </div>
  <div class="infoBox">
    <h2 class="fontStyle">物流信息：</h2>
    <table width="100%" border="0" cellspacing="4" cellpadding="0">
      <tr>
        <td>联&nbsp;&nbsp;系&nbsp;&nbsp;人：</td>
        <td><span id='receive_name'></span></td>
        <td>联系电话：</td>
        <td><span id='receive_phone'></span>
        </td>
      </tr>
      <tr>
        <td>配送地址：</td>
        <td colspan='3'><span id='contact_address'></span></td>
      </tr>
      <tr>
            <td>物流公司：</td>
            <td></td>
            <td>物流单号：</td>
            <td><span id='logistics_no'></span></td>
        <td></td>
        <td></td>
      </tr>
    </table>
  </div>
  <div class="closeBtn">
    <input type="button" name="" id="closeBtn" value="关&nbsp;&nbsp;闭" class="blueBtn" />
  </div>
</div>

</body>
</html>


