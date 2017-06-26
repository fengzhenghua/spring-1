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
		
    	var ciuuid = $UTIL.getUrlParam('ciuuid');
    	var username = $UTIL.getUrlParam('username');
    	if(ciuuid == null){
    		//$.dialog.alert('未找到订单【'+orderid+'】信息',null,null,CloseWin,CloseWin);
    	}else{
    		
    	}
    	$(function(){
    		  $('#closeBtn').on('click',CloseWin);
    		  finditem(ciuuid);
    	});
    	//检查是否session正确，如果正确跳过登录，如果不正确显示登录
    	function finditem() {
    		$.ajax({
    			crossDomain : true == !(document.all),
    			url : BASEURL + '/rest/getInfoServiceRest/getOrderInfoItem',
    			type : 'POST', //GET
    			async : true, //或false,是否异步
    			data : {
    				json_info : '{"user":"' + username + '","ciuuid":"'
    						+ ciuuid + '"}'
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
    		
    		
    </script>
</head>
<body>
	<div class="width900 contentBtm01">
  <!--============订单状态 begin==============-->
  <h2 class="title"><i></i>&nbsp;订单详情</h2>
  <!-- 流程 -->
  <div class="br" style="display:none;width:100%;padding:10px;background:#f2f2f2;margin-top:10px;"></div>
  <!-- 流程end -->
  <!-- box -->
  <div class="box br" style="padding:0 20px;">
  <div class="infoBoxTop">
  	<h2 class="fontStyle">基本信息：</h2>
    <table border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="fs14 color">订单编号：</td>
        <td class="tdWidthOne"><span id="ordernum"></span></td>
        <td class="fs14 color">订单日期：</td>
        <td class="tdWidthTwo"><span id="order_createtime"></span></td>
        <td class="fs14 color">订单状态：</td>
        <td><span id="order_status"></span></td>
      </tr>
      <tr>
        <td class="fs14 color">销售编号：</td>
        <td class="tdWidthOne"><span id="sale_order_no"></span></td>
        <td class="fs14 color">服务编号：</td>
        <td class="tdWidthTwo"><span id="serv_order_no_list"></span></td>
        <td class="fs14 color"></td>
        <td></td>
      </tr>
    </table>
  </div>
  <div class="infoBoxTop">
  	<h2 class="fontStyle">入网信息：</h2>
    <table border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="fs14 color">客户名称：</td>
        <td class="tdWidthOne" colspan="5"><span id="customer_name"></span></td>
      </tr>
      <tr>
        <td class="fs14 color">证件地址：</td>
        <td class="tdWidthOne" colspan="5"><span id="cert_address"></span></td>
      </tr>
    </table>
  </div>
  <!-- 商品信息 -->
  <div class="infoBoxTop">
  	<h2 class="fontStyle">商品信息：</h2>
    <table border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="fs14 color">商品名称：</td> 
        <td colspan="3"><span id="commodityname"></span></td>
        <td class="fs14 color"></td>
        <td></td>
      </tr>
      <tr>
        <td class="fs14 color">订购号码：</td>
        <td class="tdWidthOne"><span id="serial_number"></span></td>
        <td class="fs14 color">套餐名称：</td>
        <td class="tdWidthTwo"><span id="product_cn"></span></td>
      </tr>
      <tr>
        <td class="fs14 color">CBSS单号：</td>
        <td class="tdWidthOne"><span id="cbssnum"></span></td>
        <td class="fs14 color">CCID：</td>
        <td class="tdWidthTwo"><span id="usim"></span></td>
      </tr>
    </table>
  </div>
  <!-- 商品信息 end -->
  <!-- 物流信息 -->
  <div class="infoBoxTop" style="border-bottom:none">
  	<h2 class="fontStyle">物流信息：</h2>
    <table border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="fs14 color">联&nbsp;&nbsp;系&nbsp;&nbsp;人：</td>
        <td class="tdWidthOne"><span id="receive_name"></span></td>
        <td class="fs14 color">联系电话：</td>
        <td class="tdWidthTwo"><span id="receive_phone"></span></td>
      </tr>
      <tr>
      	<td class="fs14 color">配送地址：</td>
        <td colspan="5"><span id="contact_address"></span></td>
      </tr>
      <tr>
        <td class="fs14 color">物流公司：</td>
        <td class="tdWidthOne"></td>
        <td class="fs14 color">物流单号：</td>
        <td class="tdWidthTwo"><span id="logistics_no"></span></td>
      </tr>
    </table>
  </div>
  <!-- 物流信息 end-->
  </div>
  
  <div class="closeBtn">
    <input class="br closeInput cursor" type="button" name="" id="closeBtn" value="关&nbsp;&nbsp;闭" class="blueBtn" />
  </div>
  <!-- box end -->
</div>

</body>
</html>


