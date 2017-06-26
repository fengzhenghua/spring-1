<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>商品订单详情</title>
    <script type="text/javascript" src="../js/jquery-easyui-1.3.5/jquery.min.js"></script>   
    <script type="text/javascript" src="../js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>   
    <script type="text/javascript" src="../js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../js/common/WebUtil.js"></script>  
       
    <link rel="stylesheet" type="text/css" href="../js/jquery-easyui-1.3.5/themes/default/easyui.css" />   
    <link rel="stylesheet" type="text/css" href="../js/jquery-easyui-1.3.5/themes/icon.css" />
</head>
<body >
   <table border="0">
             	<tr>
             		<td colspan=2>
							<div id="ofr_menu" class="easyui-panel" title="商品订单"  style="width:1020px;height:1390px;padding:3px;"  data-options="collapsible:true">
						    	<table border="0">
						    	 <tr>
             		                  <td colspan=2>
             		                     <div id="serv_ord_list" class="easyui-panel" style="width:1004px;height:200px;padding:3px;"  title="服务订单列表"  data-options="collapsible:true" >
             		                     <table id="servOrderDataGrid"></table></div>
             		                  </td>
             		             </tr>
						    	  <tr>
             		                  <td colspan=2>
             		                    <div id="ofr_ord" class="easyui-panel" style="width:1004px;height:190px;padding:3px;"  title="订单信息"  data-options="collapsible:true" >
             		                    	  <span id="ofr_ord_res"></span>
             		                    	   <table border="0" cellspacing="0" cellpadding="0">             		              
             		                    	      <tr >  
             		                    	            <td style="height:30px;width:100px;text-align:right">商品订单号：</td><td id="ofr_order_no" style="width:150px"></td>             		                    	            
             		                    	            <td style="width:100px;text-align:right">省份：</td><td id="province_code" style="width:150px"></td>  
             		                    	            <td style="width:100px;text-align:right"></td><td id="" style="width:150px"></td>         		                    	          
             		                    	      </tr>            		                    	      
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">商品ID：</td><td id="ofr_id" ></td>
             		                    	            <td style="text-align:right">商品名称：</td><td id="ofr_name"></td>
             		                    	            <td style="text-align:right">撤单标志：</td><td id="cancle_flag"></td>
             		                    	            <td style="text-align:right">订单状态：</td><td id="order_state"></td>
             		                    	      </tr>
             		                    	       <tr > 
             		                    	            <td style="height:30px;width:100px;text-align:right">创建时间：</td><td id="create_time" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">完工时间：</td><td id="finish_time" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">订单模板编码：</td><td id="ord_mod_code" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right"></td><td id="" style="width:150px"></td>
             		                    	      </tr>
             		                    	       <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">受理工号：</td><td id="accept_oper_no" ></td>
             		                    	            <td style="text-align:right">受理员工名称：</td><td id="accept_oper_name"></td>
             		                    	            <td style="text-align:right">受理渠道编码：</td><td id="accept_depart_no"></td>
             		                    	            <td style="text-align:right">受理渠道名称：</td><td id="accept_depart_name"></td>
             		                    	      </tr>
             					       </table>  
             					       </div></td></tr>
						
             					 
             					 	  <tr>
             		                  <td>
             		                     <div id="ofr_ord_fee" class="easyui-panel" style="width:500px;height:230px;padding:3px;"  title="费用详情" data-options="collapsed:true,collapsible:true"  >
             		                    	 <span id="ofr_ord_fee_res"></span>
             		                    	 <table border="0" cellspacing="0" cellpadding="0">
             		                    	      <tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">费用项编码：</td><td id="fee_item_code" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">费用项名称：</td><td id="fee_item_name" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">预收费用：</td><td id="total_fee" ></td>
             		                    	            <td style="text-align:right">减免费用：</td><td id="discount_fee"></td>
             		                    	      </tr>
             		                    	       <tr> 
             		                    	            <td style="height:30px;text-align:right">实收费用：</td><td id="payed_fee" ></td>             		                    	            
             		                    	      </tr>             		                    	     
             					           </table> 
             		                    </div>
             		                  </td>
             		                  <td>
             		                    <div id="ofr_ord_pay" class="easyui-panel" style="width:500px;height:230px;padding:3px;" title="收费详情"  data-options="collapsed:true,collapsible:true" >
             		                           <span id="ofr_ord_pay_res"></span>
             		                           <table border="0" cellspacing="0" cellpadding="0">
             		                    	      <tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">费用项编码：</td><td id="pay_item_code" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">付费类型：</td><td id="pay_type" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">缴费流水：</td><td id="pay_sn" ></td>
             		                    	            <td style="text-align:right">减免流水：</td><td id="discount_sn"></td>
             		                    	      </tr>
             		                    	       <tr> 
             		                    	            <td style="height:30px;text-align:right">收费员工工号：</td><td id="pay_oper_no" ></td> 
             		                    	            <td style="width:100px;text-align:right">收费员工名称：</td><td id="pay_oper_name" style="width:150px"></td>            		                    	            
             		                    	      </tr>             		                    	     
             					           		</table>
             		                    </div>
             		                  </td>
             					      </tr>
             					        <tr>
             		                  <td>
             		                   <div id="ofr_ord_invoice" class="easyui-panel" style="width:500px;height:230px;padding:3px;"  title="一次性费用发票" data-options="collapsed:true,collapsible:true"  >
             		                    	 <span id="ofr_ord_invoice_res"></span>
             		                    	 <table border="0" cellspacing="0" cellpadding="0">
             		                    	      <tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">发票号：</td><td id="invoice_sn" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">发票类型：</td><td id="invoice_type" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">发票抬头：</td><td id="invoice_head" ></td>
             		                    	            <td style="text-align:right">本票号：</td><td id="note_sn"></td>
             		                    	      </tr>
             		                    	       <tr> 
             		                    	            <td style="height:30px;text-align:right">打印金额：</td><td id="print_fee" ></td> 
             		                    	            <td style="width:100px;text-align:right">发票状态：</td><td id="invoice_flag" style="width:150px"></td>            		                    	            
             		                    	      </tr> 
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">是否需要发票：</td><td id="need_invoice" ></td>
             		                    	            <td style="text-align:right">发票寄送标志：</td><td id="send_flag"></td>
             		                    	      </tr> 
             		                    	       <tr> 
             		                    	            <td style="height:30px;text-align:right">打印次数：</td><td id="print_num" ></td>              		                    	                        		                    	            
             		                    	      </tr>           		                    	     
             					           		</table>
             		                    </div>
             		                  
             		                  </td>
             		                  <td>
             		                  </td>
             					      </tr>
             					        
						    	</table>
						    </div>
             		</td>
             	</tr>
             </table>         
</body> 

<script type="text/javascript">
var ofr_order_no;
var finish_flag;
function GetRequest() {
	  
	  var url = location.search; //获取url中"?"符后的字串
	   var theRequest = new Object();
	   if (url.indexOf("?") != -1) {
	      var str = url.substr(1);
	      strs = str.split("&");
	      for(var i = 0; i < strs.length; i ++) {
	         theRequest[strs[i].split("=")[0]]=(strs[i].split("=")[1]);
	      }
	   }
	   return theRequest;
	}
$(document).ready(function(){ 
	var Request = new Object();
	Request = GetRequest();
	ofr_order_no = Request["ofr_order_no"];
	sale_order_no = Request["sale_order_no"];
	finish_flag = Request["finish_flag"];
	getData();	
	$('#servOrderDataGrid').datagrid({
		width:980,
		height:160,
		fitColumns:true,
		rownumbers:false,
		singleSelect:true,
		columns:[[
					/*{field:'action',title:'',width:5,align:'center'},*/
		         	{field:'serv_order_no',title:'订单号',sortable:"true",width:20,editor:'text'},
		         	{field:'oper_name',title:'业务名称',width:10,editor:'text'},
					{field:'acc_nbr',title:'服务号码',width:10,editor:'text'},
					{field:'order_state',title:'订单状态',sortable:"true",width:20,editor:'text'},
					{field:'create_time',title:'创建时间',width:10,editor:'text'},
					{field:'finish_time',title:'完工时间',width:10,editor:'text'},
					{field:'accept_oper_no',title:'受理工号',width:10,editor:'text'},
					{field:'accept_oper_name',title:'受理员工',width:10,editor:'text'},
					{field:'accpet_depart_name',title:'受理渠道',width:10,editor:'text'}
					
					
		]],
		onSelect:function(index,row){
			var content = '<iframe scrolling="yes" frameborder="0" src="../jspPage/servOrderDetail.jsp?ofr_order_no='+row['ofr_order_no']+'&sale_order_no='+sale_order_no+'&finish_flag='+finish_flag+'&serv_order_no='+row['serv_order_no']+'" style="width:100%;height:100%;"></iframe>';				
			top.jQuery('#tTabs').tabs('add',{
				cache:true,
			    title:"服务订单详情",
			    id:row['ofr_order_no'],
			    closable:true,
			    content:content
			});
	    }
	});
	//$('#servOrderDataGrid').datagrid('loadData', [{ofr_order_no:"111",ofr_id:"3G001",ofr_name:"3G 186A套餐",accept_time:"2016-11-17 11:51:13",ofr_name:"3G 186A套餐",accept_system:"BSS",order_state:"流程完结",oper_type:"3G开户",accept_oper_name:"韦小宝",accpet_depart_name:"重庆联通"}]);	
	
});  
  
function getData(){
	 var data = {
       	 jsession_id:"555555555555555555",
       	 order_no:sale_order_no,
       	 query_type:"104",
       	 finish_flag:finish_flag
        }
	 var str = JSON.stringify(data);
   	console.info("str in="+str);
	 $.ajax({
        type: "post",
        url: URLS.URL_API_HOST+"rest/ordModServiceRest/getOrderDetail",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        async:true,
        data: data,
        dataType: "json",
        success: function(data){
       	 if(data.type=="success"){
       		var str = JSON.stringify(data);
            console.info("str out="+str);            
            if(data.args != null){
            	var data = data.args.json_info;
            	
            	if(data.infoServiceOrderList != null && data.infoServiceOrderList.length>0){
            		$('#servOrderDataGrid').datagrid("loaded");
                	$('#servOrderDataGrid').datagrid("loadData",data.infoServiceOrderList);	
                	$('#servOrderDataGrid').datagrid("loaded");
            	}
            	
            	if(data.infoOfrOrderList != null){
            		var infoOfrOrder = data.infoOfrOrderList[0];
            		$('#ofr_order_no').html(infoOfrOrder.ofr_order_no);
            		$('#province_code').html(infoOfrOrder.province_code);
            		$('#ofr_id').html(infoOfrOrder.ofr_id);
            		$('#ofr_name').html(infoOfrOrder.ofr_name);
            		$('#cancle_flag').html(infoOfrOrder.cancle_flag);
            		$('#order_state').html(infoOfrOrder.order_state);
            		$('#create_time').html(infoOfrOrder.create_time);
            		$('#finish_time').html(infoOfrOrder.finish_time);
            		$('#ord_mod_code').html(infoOfrOrder.ord_mod_code);
            		$('#accept_oper_no').html(infoOfrOrder.accept_oper_no);
            		$('#accept_oper_name').html(infoOfrOrder.accept_oper_name);
            		$('#accept_depart_no').html(infoOfrOrder.accept_depart_no);
            		$('#accept_depart_name').html(infoOfrOrder.accept_depart_name);
            		//$('#').html(infoOfrOrder.);
            		
            		
            		/* var infoOfrOrderList = data.infoOfrOrderList;
            		var ofrOrderDetail = ' <table border="0" cellspacing="0" cellpadding="0">' ;
            		for(var i = 0;i<infoOfrOrderList.length;i++){
            			ofrOrderDetail +=           		              
                   	+'<tr >'  
                   	+'<td style="height:30px;width:100px;text-align:right">商品订单号：</td><td id="ofr_order_no_"'+i+' style="width:150px">'+infoOfrOrderList[i].ofr_order_no+'</td>'
                   	+'<td style="width:100px;text-align:right">销售订单号：</td><td id="sale_order_no_"'+i+' style="width:150px">'+infoOfrOrderList[i].sale_order_no+'</td>'
                   	+'<td style="width:100px;text-align:right">省份：</td><td id="province_code_"'+i+' style="width:150px">'+infoOfrOrderList[i].province_code+'</td>'  
                   	+'<td style="width:100px;text-align:right"></td><td id="" style="width:150px"></td> '        		                    	          
                   	+' </tr>'            		                    	      
                   	+'<tr style="background:#fafafa;"> '
                   	+' <td style="height:30px;text-align:right">商品ID：</td><td id="ofr_id_"'+i+' >'+infoOfrOrderList[i].ofr_id+'</td>'
                   	+' <td style="text-align:right">商品名称：</td><td id="ofr_name_"'+i+'>'+infoOfrOrderList[i].ofr_name+'</td>'
                   	+'<td style="text-align:right">撤单标志：</td><td id="cancle_flag_"'+i+'>'+infoOfrOrderList[i].cancle_flag+'</td>'
                   	+'  <td style="text-align:right">订单状态：</td><td id="order_state_"'+i+'>'+infoOfrOrderList[i].order_state+'</td>'
                   	+'</tr>'
                   	+'<tr > '
                   	+'<td style="height:30px;width:100px;text-align:right">创建时间：</td><td id="create_time_"'+i+' style="width:150px">'+infoOfrOrderList[i].create_time+'</td>'
                   	+'<td style="width:100px;text-align:right">完工时间：</td><td id="finish_time_"'+i+' style="width:150px">'+infoOfrOrderList[i].finish_time+'</td>'
                   	+' <td style="width:100px;text-align:right">订单模板编码：</td><td id="ord_mod_code_"'+i+' style="width:150px">'+infoOfrOrderList[i].ord_mod_code+'</td>'
                   	+' <td style="width:100px;text-align:right"></td><td id="" style="width:150px"></td>'
                   	+'</tr>'
                   	+'<tr style="background:#fafafa;"> '
                   	+'  <td style="height:30px;text-align:right">受理工号：</td><td id="accept_oper_no_"'+i+' >'+infoOfrOrderList[i].accept_oper_no+'</td>'
                   	+'   <td style="text-align:right">受理员工名称：</td><td id="accept_oper_name_"'+i+'>'+infoOfrOrderList[i].accept_oper_name+'</td>'
                   	+'    <td style="text-align:right">受理渠道编码：</td><td id="accept_depart_no_"'+i+'>'+infoOfrOrderList[i].accept_depart_no+'</td>'
                   	+'    <td style="text-align:right">受理渠道名称：</td><td id="accept_depart_name_"'+i+'>'+infoOfrOrderList[i].accept_depart_name+'</td>'
                   	+' </tr>';
                	
            		}
            		ofrOrderDetail +=  ' </table>'; 
            		$("#ofr_ord_res").append(ofrOrderDetail); */
            	} 
            	
            	if(data.infoOfrOrderFeeList != null){
            		var infoOfrOrderFee = data.infoOfrOrderFeeList[0];
            		$('#fee_item_code').html(infoOfrOrderFee.fee_item_code);
            		$('#fee_item_name').html(infoOfrOrderFee.fee_item_name);
            		$('#total_fee').html(infoOfrOrderFee.total_fee);
            		$('#discount_fee').html(infoOfrOrderFee.discount_fee);
            		$('#payed_fee').html(infoOfrOrderFee.payed_fee);
            		
            		/* var infoOfrOrderFeeList = data.infoOfrOrderFeeList;
            		var ofrFeeDetail = '<table border="0" cellspacing="0" cellpadding="0">';
            		for(var i = 0;i < infoOfrOrderFeeList.length;i++){
            			ofrFeeDetail += 
            				+' <tr> '
            				+'<td style="height:30px;width:100px;text-align:right">费用项编码：</td><td id="fee_item_code_"'+i+' style="width:150px">'+infoOfrOrderFeeList[i].fee_item_code+'</td>'
            				+'<td style="width:100px;text-align:right">费用项名称：</td><td id="fee_item_name_"'+i+' style="width:150px">'+infoOfrOrderFeeList[i].fee_item_name+'</td>'
            				+' </tr>'
            				+' <tr style="background:#fafafa;">' 
            				+'<td style="height:30px;text-align:right">预收费用：</td><td id="total_fee_"'+i+' >'+infoOfrOrderFeeList[i].total_fee+'</td>'
            				+' <td style="text-align:right">减免费用：</td><td id="discount_fee_"'+i+'>'+infoOfrOrderFeeList[i].discount_fee+'</td>'
            				+'</tr>'
            				+'<tr> '
            				+' <td style="height:30px;text-align:right">实收费用：</td><td id="payed_fee_"'+i+' >'+infoOfrOrderFeeList[i].payed_fee+'</td>'             		                    	            
            				+'</tr>';             		                    	     
            				
            		}
            		ofrFeeDetail += ' </table>';
            		$("#ofr_ord_fee_res").append(ofrFeeDetail); */
            	}
            	
            	if(data.infoOfrOrderPayList != null){
            		var infoOfrOrderPay = data.infoOfrOrderPayList[0];
            		$('#pay_item_code').html(infoOfrOrderPay.fee_item_code);
            		$('#pay_type').html(infoOfrOrderPay.pay_type);
            		$('#pay_sn').html(infoOfrOrderPay.pay_sn);
            		$('#discount_sn').html(infoOfrOrderPay.discount_sn);
            		$('#pay_oper_no').html(infoOfrOrderPay.pay_oper_no);
            		$('#pay_oper_name').html(infoOfrOrderPay.pay_oper_name);
            		/* var infoOfrOrderPayList = data.infoOfrOrderPayList;
            		var ofrPayDetail = '<table border="0" cellspacing="0" cellpadding="0">';
            		for(var i = 0;i<infoOfrOrderPayList.length;i++){
            			ofrPayDetail +=
            				+'<tr> '
            				+'<td style="height:30px;width:100px;text-align:right">费用项编码：</td><td id="pay_item_code_"'+i+' style="width:150px">'+infoOfrOrderPayList[i].fee_item_code+'</td>'
            				+'<td style="width:100px;text-align:right">付费类型：</td><td id="pay_type_"'+i+' style="width:150px">'+infoOfrOrderPayList[i].pay_type+'</td>'
            				+' </tr>'
            				+'<tr style="background:#fafafa;"> '
            				+' <td style="height:30px;text-align:right">缴费流水：</td><td id="pay_sn_"'+i+' >'+infoOfrOrderPayList[i].pay_sn+'</td>'
            				+'<td style="text-align:right">减免流水：</td><td id="discount_sn_"'+i+'>'+infoOfrOrderPayList[i].discount_sn+'</td>'
            				+' </tr>'
            				+' <tr>' 
            				+' <td style="height:30px;text-align:right">收费员工工号：</td><td id="pay_oper_no_"'+i+' >'+infoOfrOrderPayList[i].pay_oper_no+'</td> '
            				+'<td style="width:100px;text-align:right">收费员工名称：</td><td id="pay_oper_name_"'+i+' style="width:150px">'+infoOfrOrderPayList[i].pay_oper_name+'</td> '           		                    	            
            				+'</tr> '  ;          		                    	     
            				
            		}
            		ofrPayDetail +='</table>';
            		$('#ofr_ord_pay_res').append(ofrPayDetail); */
            	}
            	if(data.infoOfrOrderInvoiceList != null){
            		var infoOfrOrderInvoice = data.infoOfrOrderInvoiceList[0];
            		$('#invoice_sn').html(infoOfrOrderInvoice.invoice_sn);
            		$('#invoice_type').html(infoOfrOrderInvoice.invoice_type);
            		$('#invoice_head').html(infoOfrOrderInvoice.invoice_head);
            		$('#note_sn').html(infoOfrOrderInvoice.note_sn);
            		$('#print_fee').html(infoOfrOrderInvoice.print_fee);
            		$('#invoice_flag').html(infoOfrOrderInvoice.invoice_flag);
            		$('#need_invoice').html(infoOfrOrderInvoice.need_invoice);
            		$('#send_flag').html(infoOfrOrderInvoice.send_flag);
            		$('#print_num').html(infoOfrOrderInvoice.print_num);
            		/* var infoOfrOrderInvoiceList = data.infoOfrOrderInvoiceList;
            		var ofrInvoiceDetail = '<table border="0" cellspacing="0" cellpadding="0">';
            		for(var i = 0;i<infoOfrOrderInvoiceList.length;i++){
            			ofrInvoiceDetail += 
            				+'<tr> '
            				+' <td style="height:30px;width:100px;text-align:right">发票号：</td><td id="invoice_sn_"'+i+' style="width:150px">'+infoOfrOrderInvoiceList[i].invoice_sn+'</td>'
            				+'<td style="width:100px;text-align:right">发票类型：</td><td id="invoice_type_"'+i+' style="width:150px">'+infoOfrOrderInvoiceList[i].invoice_type+'</td>'
            				+' </tr>'
            				+' <tr style="background:#fafafa;">' 
            				+'<td style="height:30px;text-align:right">发票抬头：</td><td id="invoice_head_"'+i+' >'+infoOfrOrderInvoiceList[i].invoice_head+'</td>'
            				+' <td style="text-align:right">本票号：</td><td id="note_sn_"'+i+'>'+infoOfrOrderInvoiceList[i].note_sn+'</td>'
            				+'</tr>'
            				+'<tr>' 
            				+' <td style="height:30px;text-align:right">打印金额：</td><td id="print_fee_"'+i+' >'+infoOfrOrderInvoiceList[i].print_fee+'</td>' 
            				+'<td style="width:100px;text-align:right">发票状态：</td><td id="invoice_flag_"'+i+' style="width:150px">'+infoOfrOrderInvoiceList[i].invoice_flag+'</td>'            		                    	            
            				+'</tr> '
            				+' <tr style="background:#fafafa;"> '
            				+' <td style="height:30px;text-align:right">是否需要发票：</td><td id="need_invoice_"'+i+' >'+infoOfrOrderInvoiceList[i].need_invoice+'</td>'
            				+'<td style="text-align:right">发票寄送标志：</td><td id="send_flag_"'+i+'>'+infoOfrOrderInvoiceList[i].send_flag+'</td>'
            				+'</tr> '
            				+'<tr>' 
            				+' <td style="height:30px;text-align:right">打印次数：</td><td id="print_num_"'+i+' >'+infoOfrOrderInvoiceList[i].print_num+'</td> '             		                    	                        		                    	            
            				+'</tr>'  ;         		                    	     
            				
            		}
            		ofrInvoiceDetail += '</table>';
            		$("#ofr_ord_invoice_res").append(ofrInvoiceDetail); */
            	}
            }  	
            	
       	 }
       	 else if(data.type=="error"){
       		$.messager.alert('',data.content,'info');
       	 }},
        error:function(data)
        {
        	$.messager.alert('','查询失败!','info');
        }
    });	
}  

</script>
</html>