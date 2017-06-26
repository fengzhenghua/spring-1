<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>销售订单详情</title>
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
							<div id="sale_menu" class="easyui-panel" title="销售订单"  style="width:1020px;height:1390px;padding:3px;"  data-options="collapsible:true">
						    	<table border="0">
						    	 <tr>
             		                  <td colspan=2>
             		                     <div id="ofr_ord_list" class="easyui-panel" style="width:1004px;height:200px;padding:3px;"  title="商品订单列表"  data-options="collapsible:true" >
             		                     <table id="ofrOrderDataGrid"></table></div>
             		                  </td>
             		             </tr>
						    	  <tr>
             		                  <td colspan=2>
             		                    <div id="sale_ord" class="easyui-panel" style="width:1004px;height:190px;padding:3px;"  title="订单信息"  data-options="collapsible:true" >
             		                    	   <table border="0" cellspacing="0" cellpadding="0">
             		                    	      <tr > 
             		                    	            <td style="height:30px;width:100px;text-align:right">销售订单号：</td><td id="sale_order_no" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">省份：</td><td id="province_code" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">受理流水：</td><td id="accept_no" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">受理员工：</td><td id="accept_oper_no" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;">  
             		                    	            <td style="height:30px;text-align:right">受理时间：</td><td id="accept_time" ></td>
             		                    	            <td style="text-align:right">订单类型：</td><td id="order_type"></td>
             		                    	            <td style="text-align:right">受理类型：</td><td id="accept_type"></td>
             		                    	            <td style="text-align:right">受理员工名称：</td><td id="accept_oper_name"></td>
             		                    	      </tr>
             		                    	       <tr> 
             		                    	            <td style="height:30px;text-align:right">受理渠道编码：</td><td id="accept_depart_no" ></td>
             		                    	            <td style="text-align:right">受理渠道名称：</td><td id="accept_depart_name"></td>
             		                    	            <td style="text-align:right">付费标志：</td><td id="pay_flag"></td>
             		                    	            <td style="text-align:right">支付方式：</td><td id="pay_type"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">配送标志：</td><td id="express_flag" ></td>
             		                    	            <td style="text-align:right">撤单标志：</td><td id="cancle_flag"></td>
             		                    	            <td style="text-align:right">订单状态：</td><td id="order_state"></td>
             		                    	            <td style="text-align:right">创建时间：</td><td id="create_time"></td>
             		                    	      </tr>
             		                    	      <tr> 
             		                    	            <td style="height:30px;text-align:right">完成时间：</td><td id="finish_time" ></td>
             		                    	            <td style="text-align:right">订单模板编码：</td><td id="ord_mod_code"></td>
             		                    	            <td style="text-align:right">流程实例：</td><td id="proc_inst_id"></td>
             		                    	            <td style="text-align:right">预订单流程：</td><td id="pre_proc_inst_id"></td>
             		                    	      </tr>
             					       </table>   </div></td></tr>
						    	  <tr>
             		                  <td>
             		                    <div id="sale_person" class="easyui-panel" style="width:500px;height:230px;padding:3px;"  title="个客信息" data-options="collapsed:true,collapsible:true"  >
             		                    	 <table border="0" cellspacing="0" cellpadding="0">
             		                    	      <tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">客户ID：</td><td id="cust_id" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">客户名称：</td><td id="cust_name" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">客户新老标志：</td><td id="new_flag" ></td>
             		                    	            <td style="text-align:right">客户联系电话：</td><td id="cust_phone"></td>
             		                    	      </tr>
             		                    	       <tr> 
             		                    	            <td style="height:30px;text-align:right">EMAIL：</td><td id="email" ></td>
             		                    	            <td style="text-align:right">客户地址：</td><td id="cust_address"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">客户性别：</td><td id="sex" ></td>
             		                    	            <td style="text-align:right">客户类型：</td><td id="cust_type"></td>
             		                    	      </tr>
             		                    	      <tr> 
             		                    	            <td style="height:30px;text-align:right">证件类型：</td><td id="cert_type" ></td>
             		                    	            <td style="text-align:right">证件号码：</td><td id="cert_no"></td>
           		                    	      	</tr>
           		                    	       <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">证件地址：</td><td id="cert_address" ></td>
             		                    	            <td style="text-align:right">证件有效期：</td><td id="cert_expire_date"></td>
             		                    	      </tr>
             					       </table> 
             		                    </div>
             		                  </td>
             		                  <td>
             		                    <div id="sale_fee" class="easyui-panel" style="width:500px;height:230px;padding:3px;" title="费用信息"  data-options="collapsed:true,collapsible:true" >
             		                           <table border="0" cellspacing="0" cellpadding="0">
             		                    	      <tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">预收总费用：</td><td id="total_fee" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">减免总费用：</td><td id="discount_fee" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">实收总费用：</td><td id="payed_fee" ></td>
             		                    	            <td style="text-align:right">收费标志：</td><td id="pay_flag"></td>
             		                    	      </tr>
             		                    	      <tr> 
             		                    	            <td style="height:30px;text-align:right">收费时间：</td><td id="pay_time" ></td>
             		                    	            <td style="text-align:right">创建时间：</td><td id="create_time"></td>
             		                    	      </tr>
             		                    	      <!-- <tr style="background:#fafafa;height:30px"> 
             		                    	            <td colspan=4></td>
             		                    	       </tr>
             		                    	        <tr style="height:30px"> 
             		                    	       </tr>
             		                    	       <tr style="background:#fafafa;height:30px"> 
             		                    	            <td colspan=4></td> 
             		                    	       </tr> -->
             		                    	  

             					       </table> 
             		                    </div>
             		                  </td>
             					 </tr>
             					 
             					 	  <tr>
             		                  <td>
             		                    <div id="sale_distribution" class="easyui-panel" style="width:500px;height:230px;padding:3px;"  title="收件人信息" data-options="collapsed:true,collapsible:true"  >
             		                    	<table border="0" cellspacing="0" cellpadding="0">
             		                    	      <tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">收件人姓名：</td><td id="contact_name" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">收件人电话：</td><td id="contact_tel" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">收件人省分：</td><td id="post_province" ></td>
             		                    	            <td style="text-align:right">收件人地市：</td><td id="post_city"></td>
             		                    	      </tr>
             		                    	      <tr> 
             		                    	            <td style="height:30px;text-align:right">收件人区县：</td><td id="post_area" ></td>
             		                    	            <td style="text-align:right">收件人详细地址：</td><td id="address"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;">              		                    	           
             		                    	            <td style="height:30px;text-align:right">邮政编码：</td><td id="post_code" ></td>
             		                    	            <td style="text-align:right">配送方式：</td><td id="logistics_type"></td>
             		                    	       </tr>
             		                    	       <tr >              		                    	           
             		                    	            <td style="height:30px;text-align:right">配送开始时间：</td><td id="distribution_begin_time" ></td>
             		                    	            <td style="text-align:right">配送结束时间：</td><td id="distribution_end_time"></td>
             		                    	       </tr>
             		                    	       <tr style="background:#fafafa;height:30px">              		                    	           
             		                    	            <td style="height:30px;text-align:right">自提渠道ID：</td><td id="self_chl_id" ></td>
             		                    	            <td style="text-align:right">自提渠道名称：</td><td id="self_chl_name"></td>
             		                    	       </tr>
             		                    	       <tr >              		                    	           
             		                    	            <td style="height:30px;text-align:right">支付方式：</td><td id="pay_type" ></td>             		                    	            
             		                    	       </tr>
             		                    	        <!-- <tr style="height:30px"> 
             		                    	       </tr>
             		                    	       <tr style="background:#fafafa;height:30px"> 
             		                    	            <td colspan=4></td> 
             		                    	       </tr> -->             		                    	  
             					       		</table> 
             		                    </div>
             		                  </td>
             		                  <td>
             		                    <div id="sale_distr_detail" class="easyui-panel" style="width:500px;height:230px;padding:3px;" title="包裹信息"  data-options="collapsed:true,collapsible:true" >
             		                          	<span id="sale_distr_detail_res"></span>
             		                          	<table border="0" cellspacing="0" cellpadding="0">
             		                          	<tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">物品编码：</td><td id="article_code" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">设备串码：</td><td id="article_imsi" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">物品名称：</td><td id="article_name" ></td>
             		                    	            <td style="text-align:right">物品数量：</td><td id="article_num"></td>
             		                    	      </tr>
             		                    	      <tr> 
             		                    	            <td style="height:30px;text-align:right">物品描述：</td><td id="article_desc" ></td>             		                    	            
             		                    	      </tr>
             		                    	      </table>
             		                      </div>
             		                  </td>
             					      </tr>
             					        <tr>
             		                  <td>
             		                    <div id="sale_edit" class="easyui-panel" style="width:500px;height:230px;padding:3px;"  title="修订信息" data-options="collapsed:true,collapsible:true"  >
             		                    	<span id="sale_edit_res"></span>
             		                    	<table border="0" cellspacing="0" cellpadding="0">
             		                    		<tr>
            		 								 <td style="height:30px;width:100px;text-align:right">修订类型：</td><td id="edit_type" style="width:150px"></td>
            		 								 <td style="width:100px;text-align:right">状态：</td><td id="state"style="width:150px"></td>
              	      							</tr>
              	      							 <tr style="background:#fafafa;">
              	       								<td style="height:30px;text-align:right">修订系统：</td><td id="edit_system"></td>
              	      							 	<td style="text-align:right">修订工号：</td><td id="oper_no"></td>
              	      							</tr>
              	      							 <tr>
              	     								 <td style="height:30px;text-align:right">修订时间：</td><td id="edit_time"></td>            
              	      								 <td style="width:100px;text-align:right">修订描述：</td><td id="edit_desc" style="width:150px"></td>        	       
              	       							</tr>
             		                    	</table>
             		                    </div>
             		                  </td>
             		                  <td>
             		                    <div id="sale_ext" class="easyui-panel" style="width:500px;height:230px;padding:3px;" title="拓展属性横向信息"  data-options="collapsed:true,collapsible:true" >
             		                          <table border="0" cellspacing="0" cellpadding="0">
             		                    		<tr>
            		 								 <td style="height:30px;width:100px;text-align:right">ext_field_：</td><td id="ext_field_" style="width:150px"></td>
            		 								 <td style="width:100px;text-align:right">ext_field_：</td><td id="ext_field_" style="width:150px"></td>
              	      							</tr>
              	      							 <tr style="background:#fafafa;">
              	       								<td style="height:30px;text-align:right">ext_field_：</td><td id="ext_field_"></td>
              	      							 	<td style="text-align:right">ext_field_：</td><td id="ext_field_"></td>
              	      							</tr>
              	      							<tr>
            		 								 <td style="height:30px;width:100px;text-align:right">ext_field_：</td><td id="ext_field_" style="width:150px"></td>
            		 								 <td style="width:100px;text-align:right">ext_field_：</td><td id="ext_field_" style="width:150px"></td>
              	      							</tr>
              	      							 <tr style="background:#fafafa;">
              	       								<td style="height:30px;text-align:right">ext_field_：</td><td id="ext_field_"></td>
              	      							 	<td style="text-align:right">ext_field_：</td><td id="ext_field_"></td>
              	      							</tr>
              	      							<tr>
            		 								 <td style="height:30px;width:100px;text-align:right">ext_field_：</td><td id="ext_field_" style="width:150px"></td>
            		 								 <td style="width:100px;text-align:right">ext_field_：</td><td id="ext_field_" style="width:150px"></td>
              	      							</tr>
              	      							 <tr style="background:#fafafa;">
              	       								<td style="height:30px;text-align:right">ext_field_：</td><td id="ext_field_"></td>
              	      							 	<td style="text-align:right">ext_field_：</td><td id="ext_field_"></td>
              	      							</tr>
              	      							<tr>
            		 								 <td style="height:30px;width:100px;text-align:right">ext_field_：</td><td id="ext_field_" style="width:150px"></td>
            		 								 <td style="width:100px;text-align:right">ext_field_：</td><td id="ext_field_" style="width:150px"></td>
              	      							</tr>
              	      							 <tr style="background:#fafafa;">
              	       								<td style="height:30px;text-align:right">ext_field_：</td><td id="ext_field_"></td>
              	      							 	<td style="text-align:right">ext_field_：</td><td id="ext_field_"></td>
              	      							</tr>
              	      							<tr>
            		 								 <td style="height:30px;width:100px;text-align:right">ext_field_：</td><td id="ext_field_" style="width:150px"></td>
            		 								 <td style="width:100px;text-align:right">ext_field_：</td><td id="ext_field_" style="width:150px"></td>
              	      							</tr>
              	      							 <tr style="background:#fafafa;">
              	       								<td style="height:30px;text-align:right">ext_field_：</td><td id="ext_field_"></td>
              	      							 	<td style="text-align:right">ext_field_：</td><td id="ext_field_"></td>
              	      							</tr>
             		                    	</table>
             		                    </div>
             		                  </td>
             					      </tr>
             					        <tr>
             		                  <td>
             		                    <div id="sale_attr" class="easyui-panel" style="width:500px;height:230px;padding:3px;"  title="属性集" data-options="collapsed:true,collapsible:true"  >
             		                    	<span id="sale_attr_res"></span>
             		                    	<table border="0" cellspacing="0" cellpadding="0">
             		                    		<tr>
            		 								 <td style="height:30px;width:100px;text-align:right">属性编码：</td><td id="attr_code" style="width:150px"></td>
            		 								 <td style="width:100px;text-align:right">属性类型：</td><td id="attr_type" style="width:150px"></td>
              	      							</tr>
              	      							 <tr style="background:#fafafa;">
              	       								<td style="height:30px;text-align:right">属性值：</td><td id="attr_after_value"></td>
              	      							 	<td style="text-align:right">属性原值：</td><td id="attr_before_value"></td>'
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
var sale_order_no;
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
	$.messager.progress({ 
		title:'', 
		msg:'数据加载中...'
	 }); 
	var Request = new Object();
	Request = GetRequest();
	sale_order_no = Request["sale_order_no"];
	finish_flag = Request["finish_flag"];
	$('#ofrOrderDataGrid').datagrid({
			width:980,
			height:160,
			fitColumns:true,
			rownumbers:false,
			singleSelect:true,
			columns:[[
						/*{field:'action',title:'',width:5,align:'center'},*/
			         	{field:'ofr_order_no',title:'订单号',sortable:"true",width:20,editor:'text'},
			         	{field:'ofr_id',title:'商品ID',width:10,editor:'text'},
						{field:'ofr_name',title:'商品名称',width:10,editor:'text'},
						{field:'accept_time',title:'受理时间',sortable:"true",width:20,editor:'text'},
						{field:'accept_system',title:'受理系统',width:10,editor:'text'},
						{field:'order_state',title:'订单状态',width:10,editor:'text'},
						{field:'oper_type',title:'业务类型',width:10,editor:'text'},
						{field:'accept_oper_name',title:'受理员工',width:10,editor:'text'},
						{field:'accpet_depart_name',title:'受理渠道',width:10,editor:'text'}
						
						
			]],
			onSelect:function(index,row){
				var content = '<iframe scrolling="yes" frameborder="0" src="../jspPage/ofrOrderDetail.jsp?ofr_order_no='+row['ofr_order_no']+'&sale_order_no='+sale_order_no+'&finish_flag='+finish_flag+'" style="width:100%;height:100%;"></iframe>';				
				top.jQuery('#tTabs').tabs('add',{
					cache:true,
				    title:"商品订单详情",
				    id:row['ofr_order_no'],
				    closable:true,
				    content:content
				});
		    }
		});
		//$('#ofrOrderDataGrid').datagrid('loadData', [{ofr_order_no:"111",ofr_id:"3G001",ofr_name:"3G 186A套餐",accept_time:"2016-11-17 11:51:13",ofr_name:"3G 186A套餐",accept_system:"BSS",order_state:"流程完结",oper_type:"3G开户",accept_oper_name:"韦小宝",accpet_depart_name:"重庆联通"}]);	
		
	getData();	
	
});  
  
function getData(){
	var data = {
       	 jsession_id:"555555555555555555",
       	 order_no:sale_order_no,
       	 query_type:"100",
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
        if (data.respCode=="0000") {
       		$.messager.progress('close');	       		
       		var str = JSON.stringify(data);
            console.info("str out="+str);
            if(data.args != null){
            	var data = data.args.json_info; 
                
                //商品订单表
                if(data.infoOfrOrder != null && data.infoOfrOrder.length>0){
                	//$('#ofrOrderDataGrid').datagrid("loaded");
                	$('#ofrOrderDataGrid').datagrid("loadData",data.infoOfrOrder);	
                	$('#ofrOrderDataGrid').datagrid("loaded");
                }
                
                           
                if(data.infoSaleOrder != null){
                	var infoSaleOrder = data.infoSaleOrder;
                	$('#sale_order_no').html(infoSaleOrder.sale_order_no);
                	$("#province_code").html(infoSaleOrder.province_code);
                	$("#accept_no").html(infoSaleOrder.accept_no);
                	$("#accept_oper_no").html(infoSaleOrder.accept_oper_no);
                	$("#accept_time").html(infoSaleOrder.accept_time);
                	$("#order_type").html(infoSaleOrder.order_type);
                	$("#accept_type").html(infoSaleOrder.accept_type);
                	$("#accept_oper_name").html(infoSaleOrder.accept_oper_name);
                	$("#accept_depart_no").html(infoSaleOrder.accept_depart_no);
                	$("#accept_depart_name").html(infoSaleOrder.accept_depart_name);
                	$("#pay_flag").html(infoSaleOrder.pay_flag);
                	$("#pay_type").html(infoSaleOrder.pay_type);
                	$("#express_flag").html(infoSaleOrder.express_flag);
                	$("#cancle_flag").html(infoSaleOrder.cancle_flag);
                	$("#order_state").html(infoSaleOrder.order_state);
                	$("#create_time").html(infoSaleOrder.create_time);
                	$("#finish_time").html(infoSaleOrder.finish_time);
                	$("#ord_mod_code").html(infoSaleOrder.ord_mod_code);
                	$("#proc_inst_id").html(infoSaleOrder.proc_inst_id);
                	$("#pre_proc_inst_id").html(infoSaleOrder.pre_proc_inst_id);
                	
                }
                
                if(data.infoSaleOrderPersion != null){
                	var infoSaleOrderPersion = data.infoSaleOrderPersion;
                	$('#cust_id').html(infoSaleOrderPersion.cust_id);
                	$('#cust_name').html(infoSaleOrderPersion.cust_name);
                	$('#new_flag').html(infoSaleOrderPersion.new_flag);
                	$('#cust_phone').html(infoSaleOrderPersion.cust_phone);
                	$('#email').html(infoSaleOrderPersion.email);
                	$('#cust_address').html(infoSaleOrderPersion.cust_address);
                	$('#sex').html(infoSaleOrderPersion.sex);
                	$('#cust_type').html(infoSaleOrderPersion.cust_type);
                	$('#cert_type').html(infoSaleOrderPersion.cert_type);
                	$('#cert_no').html(infoSaleOrderPersion.cert_no);
                	$('#cert_address').html(infoSaleOrderPersion.cert_address);
                	$('#cert_expire_date').html(infoSaleOrderPersion.cert_expire_date);
                }
           	 	
                if(data.infoSaleOrderFee != null){
                	var infoSaleOrderFee = data.infoSaleOrderFee;
                	$('#total_fee').html(infoSaleOrderFee.total_fee);
                	$('#discount_fee').html(infoSaleOrderFee.discount_fee);
                	$('#payed_fee').html(infoSaleOrderFee.payed_fee);
                	$('#pay_flag').html(infoSaleOrderFee.pay_flag);
                	$('#pay_time').html(infoSaleOrderFee.pay_time);
                	$('#create_time').html(infoSaleOrderFee.create_time);
                }
                
               if(data.infoSaleOrderDistribution != null){
            	   var infoSaleOrderDistribution = data.infoSaleOrderDistribution;
            	   $('#contact_name').html(infoSaleOrderDistribution.contact_name);
            	   $('#contact_tel').html(infoSaleOrderDistribution.contact_tel);
            	   $('#post_province').html(infoSaleOrderDistribution.post_province);
            	   $('#post_city').html(infoSaleOrderDistribution.post_city);
            	   $('#post_area').html(infoSaleOrderDistribution.post_area);
            	   $('#address').html(infoSaleOrderDistribution.address);
            	   $('#post_code').html(infoSaleOrderDistribution.post_code);
            	   $('#distribution_begin_time').html(infoSaleOrderDistribution.distribution_begin_time);
            	   $('#distribution_end_time').html(infoSaleOrderDistribution.distribution_end_time);
            	   $('#self_chl_id').html(infoSaleOrderDistribution.self_chl_id);
            	   $('#self_chl_name').html(infoSaleOrderDistribution.self_chl_name);
            	   $('#pay_type').html(infoSaleOrderDistribution.pay_type);
               }
               
               if(data.infoSaleOrderDistrDetailList != null){
            	   var infoSaleOrderDistrDetail = data.infoSaleOrderDistrDetailList[0];
            	   $('#article_code').html(infoSaleOrderDistrDetail.article_code);
            	   $('#article_imsi').html(infoSaleOrderDistrDetail.article_imsi);
            	   $('#article_name').html(infoSaleOrderDistrDetail.article_name);
            	   $('#article_num').html(infoSaleOrderDistrDetail.article_num);
            	   $('#article_desc').html(infoSaleOrderDistrDetail.article_desc);
            	  /*  $('#').html(infoSaleOrderDistrDetail.);
            	   $('#').html(infoSaleOrderDistrDetail.); */
            	   
            	   /* var infoSaleOrderDistrDetailList = data.infoSaleOrderDistrDetailList;
            	   var distrDetail = '<table border="0" cellspacing="0" cellpadding="0">';
            	   for(var i = 0;i < infoSaleOrderDistrDetailList.length;i++){
            		   distrDetail += '<tr>'
            		   +'<td style="height:30px;width:100px;text-align:right">物品编码：</td><td id="article_code_"'+i+' style="width:150px">'+infoSaleOrderDistrDetailList[i].article_code+'</td>'
            		   +'<td style="width:100px;text-align:right">设备串码：</td><td id="article_imsi_"'+i+' style="width:150px">'+infoSaleOrderDistrDetailList[i].article_imsi+'</td>'
              	       +'</tr>'
              	       +' <tr style="background:#fafafa;">'
              	       +'<td style="height:30px;text-align:right">物品名称：</td><td id="article_name_"'+i+'>'+infoSaleOrderDistrDetailList[i].article_name+'</td>'
              	       +'<td style="text-align:right">物品数量：</td><td id="article_num_"'+i+'>'+infoSaleOrderDistrDetailList[i].article_num+'</td>'
              	       +'</tr>'
              	       +'<tr>'
              	       +'<td style="height:30px;text-align:right">物品描述：</td><td id="article_desc_"'+i+'>'+infoSaleOrderDistrDetailList[i].article_desc+'</td>'	            
              	       + '</tr>';
            	   }
            	   distrDetail += '</table>';
            	   $('#sale_distr_detail_res').append(distrDetail); */
               }
               
               if(data.infoSaleOrderEditList != null){
            	   var infoSaleOrderEdit = data.infoSaleOrderEditList[0];
            	   $('#edit_type').html(infoSaleOrderEdit.edit_type);
            	   $('#state').html(infoSaleOrderEdit.state);
            	   $('#edit_system').html(infoSaleOrderEdit.edit_system);
            	   $('#oper_no').html(infoSaleOrderEdit.oper_no);
            	   $('#edit_time').html(infoSaleOrderEdit.edit_time);
            	   $('#edit_desc').html(infoSaleOrderEdit.edit_desc);
            	  /*  var infoSaleOrderEditList = data.infoSaleOrderEditList;
            	   var editDetail = '<table border="0" cellspacing="0" cellpadding="0">';
            	   for(var i = 0;i < infoSaleOrderEditList.length;i++){
            		   editDetail += '<tr>'
            		   +'<td style="height:30px;width:100px;text-align:right">修订类型：</td><td id="edit_type_"'+i+' style="width:150px">'+infoSaleOrderEditList[i].edit_type+'</td>'
            		   +'<td style="width:100px;text-align:right">状态：</td><td id="state_"'+i+' style="width:150px">'+infoSaleOrderEditList[i].state+'</td>'
              	       +'</tr>'
              	       +' <tr style="background:#fafafa;">'
              	       +'<td style="height:30px;text-align:right">修订系统：</td><td id="edit_system_"'+i+'>'+infoSaleOrderEditList[i].edit_system+'</td>'
              	       +'<td style="text-align:right">修订工号：</td><td id="oper_no_"'+i+'>'+infoSaleOrderEditList[i].oper_no+'</td>'
              	       +'</tr>'
              	       +'<tr> '
              	       +'<td style="height:30px;text-align:right">修订时间：</td><td id="edit_time_"'+i+'>'+infoSaleOrderEditList[i].edit_time+'</td>'	            
              	       +'<td style="width:100px;text-align:right">修订描述：</td><td id="edit_desc_"'+i+' style="width:150px">'+infoSaleOrderEditList[i].edit_desc+'</td>'        	       
              	       + '</tr>';
            	   }
            	   editDetail += '</table>';
            	   $('#sale_edit_res').append(editDetail); */
               }
               if(data.infoSaleOrderExt != null){
            	   var infoSaleOrderExt = data.infoSaleOrderExt;
            	   
               }
               if(data.infoSaleOrderAttrList != null){
            	   var infoSaleOrderAttrList = data.infoSaleOrderAttrList[0];
            	   $('#attr_code').html(infoSaleOrderAttrList.attr_code);
            	   $('#attr_type').html(infoSaleOrderAttrList.attr_type);
            	   $('#attr_after_value').html(infoSaleOrderAttrList.attr_after_value);
            	   $('#attr_before_value').html(infoSaleOrderAttrList.attr_before_value);
            	   /* var infoSaleOrderAttrList = data.infoSaleOrderAttrList;
            	   var attrDetail = '<table border="0" cellspacing="0" cellpadding="0">';
            	   for(var i = 0;i < infoSaleOrderAttrList.length;i++){
            		   attrDetail += '<tr>'
            		   +'<td style="height:30px;width:100px;text-align:right">属性编码：</td><td id="attr_code_"'+i+' style="width:150px">'+infoSaleOrderAttrList[i].attr_code+'</td>'
            		   +'<td style="width:100px;text-align:right">属性类型：</td><td id="attr_type_"'+i+' style="width:150px">'+infoSaleOrderAttrList[i].attr_type+'</td>'
              	       +'</tr>'
              	       +' <tr style="background:#fafafa;">'
              	       +'<td style="height:30px;text-align:right">属性值：</td><td id="attr_after_value_"'+i+'>'+infoSaleOrderAttrList[i].attr_after_value+'</td>'
              	       +'<td style="text-align:right">属性原值：</td><td id="attr_before_value_"'+i+'>'+infoSaleOrderAttrList[i].attr_before_value+'</td>'
              	       +'</tr>';
            	   }
            	   attrDetail += '</table>';
            	   $('#sale_attr_res').append(attrDetail); */
               }
            }         
       	 }
       	 else if(data.type=="error"){
       		$.messager.progress('close');
       		$.messager.alert('','查询失败!','info');
       	 }},
        error:function(data)
        {
        	$.messager.progress('close');
        	$.messager.alert('','查询失败!','info');
        }
    });	
}  

</script>
</html>