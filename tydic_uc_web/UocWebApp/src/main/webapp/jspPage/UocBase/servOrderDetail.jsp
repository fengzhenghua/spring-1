<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>服务订单详情</title>
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
							<div id="serv_menu" class="easyui-panel" title="服务订单"  style="width:1020px;height:2390px;padding:3px;"  data-options="collapsible:true">
						    	<table border="0">						    	
						    	  <tr>
             		                  <td colspan=2>
             		                    <div id="serv_ord" class="easyui-panel" style="width:1004px;height:190px;padding:3px;"  title="订单信息"  data-options="collapsible:true" >
             		                    	   <table border="0" cellspacing="0" cellpadding="0">
             		                    	      <tr style="background:#fafafa;">  
             		                    	               <td style="height:30px;width:100px;text-align:right">订单号：</td><td id="serv_order_no" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">业务名称：</td><td id="oper_name" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">业务编码：</td><td id="oper_code" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">受理员工名称：</td><td id="accept_oper_name" style="width:150px"></td>
             		                    	     </tr>            		                    	      
             		                    	      <tr > 
             		                    	            <td style="height:30px;text-align:right">服务号码：</td><td id="acc_nbr" ></td>
             		                    	            <td style="text-align:right">撤单标志：</td><td id="cancle_flag"></td>
             		                    	            <td style="text-align:right">订单状态：</td><td id="order_state"></td>
             		                    	            <td style="text-align:right">创建时间：</td><td id="create_time"></td>
             		                    	      </tr>
             		                    	       <tr style="background:#fafafa;">             		                   	       		 
             		                    	            <td style="height:30px;text-align:right">受理渠道编码：</td><td id="accept_depart_no" ></td>
             		                    	            <td style="text-align:right">受理渠道名称：</td><td id="accept_depart_name"></td>
             		                    	            <td></td><td></td><td></td><td></td>
             		                    	      </tr>
             					       			</table>
             					          </div></td></tr>
						
             					 	  <tr>
             		                  <td>
             		                    <div id="serv_ord_fee" class="easyui-panel" style="width:500px;height:230px;padding:3px;"  title="费用详情" data-options="collapsed:true,collapsible:true"  >
             		                    	<span id="serv_ord_fee_res"></span>
             		                    	<table border="0" cellspacing="0" cellpadding="0">
             		                    			<tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">费用项名称：</td><td id="fee_item_name" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">费用项类型：</td><td id="fee_item_type" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">预收费用：</td><td id="total_fee" ></td>
             		                    	            <td style="text-align:right">实收费用：</td><td id="payed_fee"></td>
             		                    	      </tr>
             		                    	       <tr> 
             		                    	            <td style="height:30px;text-align:right">减免费用：</td><td id="discount_fee" ></td>  
             		                    	            <td style="height:30px;text-align:right">减免描述：</td><td id="discount_comments" ></td>              		                    	            
             		                    	      </tr> 
             		                    	</table>
             		                    </div>
             		                  </td>
             		                  <td>
             		                    <div id="serv_ord_pay" class="easyui-panel" style="width:500px;height:230px;padding:3px;" title="收费详情"  data-options="collapsed:true,collapsible:true" >
             		                          <table border="0" cellspacing="0" cellpadding="0">
             		                          		<tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">付费类型：</td><td id="pay_type" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">缴费流水：</td><td id="pay_sn" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">预收费用：</td><td id="pay_total_fee" ></td>
             		                    	            <td style="text-align:right">实收费用：</td><td id="pay_payed_fee"></td>
             		                    	      </tr>
             		                    	       <tr> 
             		                    	            <td style="height:30px;text-align:right">减免费用：</td><td id="pay_discount_fee" ></td>              		                    	                      		                    	            
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">收费员工工号：</td><td id="pay_oper_no" ></td>
             		                    	            <td style="text-align:right">收费员工名称：</td><td id="pay_oper_name"></td>
             		                    	      </tr>
             		                          </table>
             		                    </div>
             		                  </td>
             					      </tr>
             					        <tr>
             		                  <td> <div id="serv_ord_collection" class="easyui-panel" style="width:500px;height:230px;padding:3px;"  title="银行托收信息" data-options="collapsed:true,collapsible:true"  >
             		                    	<table border="0" cellspacing="0" cellpadding="0">
             		                          		<tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">账户ID：</td><td id="account_id" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">开户银行编码：</td><td id="bank_code" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">银行账号：</td><td id="bank_account" ></td>
             		                    	            <td style="text-align:right">开户应用名称：</td><td id="bank_name"></td>
             		                    	      </tr>
             		                    	       <tr> 
             		                    	            <td style="height:30px;text-align:right">开户行地市名称：</td><td id="bank_area_name" ></td>  
             		                    	            <td style="height:30px;text-align:right">开户行地市编码：</td><td id="bank_area_code" ></td>              		                    	            
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">托收方式：</td><td id="collection_type" ></td>
             		                    	            <td style="text-align:right">托收协议号：</td><td id="collection_sn"></td>
             		                    	      </tr>
             		                    	       <tr> 
             		                    	            <td style="height:30px;text-align:right">储值方式：</td><td id="save_type" ></td>  
             		                    	            <td style="height:30px;text-align:right">上级银行编码：</td><td id="upper_bank_code" ></td>              		                    	            
             		                    	      </tr>
             		                          </table>
             		                    </div>
             		                   
             		                  </td>
             		                  <td>
             		                     <div id="serv_ord_guarantor" class="easyui-panel" style="width:500px;height:230px;padding:3px;"  title="担保人信息" data-options="collapsed:true,collapsible:true"  >
             		                    	<table border="0" cellspacing="0" cellpadding="0">
             		                          		<tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">担保类型：</td><td id="guarant_type" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">担保项目：</td><td id="guarant_project" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">担保方式：</td><td id="guarant_method" ></td>
             		                    	            <td style="text-align:right">担保期限：</td><td id="guarant_eff_time"></td>
             		                    	      </tr>
             		                    	       <tr> 
             		                    	            <td style="height:30px;text-align:right">担保单号：</td><td id="guarant_sn" ></td>  
             		                    	            <td style="height:30px;text-align:right">担保单位编码：</td><td id="guarant_depart_code" ></td>              		                    	            
             		                    	      </tr>
             		                          </table>
             		                    </div>
             		                  </td>
             					      </tr>
             					       <tr>
             		                  <td>
             		                    <div id="serv_ord_ext" class="easyui-panel" style="width:500px;height:230px;padding:3px;"  title="拓展属性信息" data-options="collapsed:true,collapsible:true"  >
             		                    	<table border="0" cellspacing="0" cellpadding="0">
             		                          	
             		                          </table>
             		                    </div>
             		                  </td>
             		                  <td>
             		                     <div id="serv_ord_attr" class="easyui-panel" style="width:500px;height:230px;padding:3px;"  title="订单属性" data-options="collapsed:true,collapsible:true"  >
             		                    	<span id="ser_ord_attr_res"></span>
             		                    	<table border="0" cellspacing="0" cellpadding="0">
             		                          		<tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">属性编码：</td><td id="attr_code" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">属性类型：</td><td id="attr_type" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">属性值：</td><td id="attr_after_value" ></td>
             		                    	            <td style="text-align:right">属性原值：</td><td id="attr_before_value"></td>
             		                    	      </tr>
             		                    	       <tr> 
             		                    	            <td style="height:30px;text-align:right">本级序列：</td><td id="curr_seq" ></td>  
             		                    	            <td style="height:30px;text-align:right">上级序列：</td><td id="upper_seq" ></td>              		                    	            
             		                    	      </tr>
             		                          </table>
             		                    </div>
             		                  </td>
             					      </tr>
             					       <tr>
             		                  <td>
             		                    <div id="serv_ord_developer" class="easyui-panel" style="width:500px;height:230px;padding:3px;"  title="发展人信息" data-options="collapsed:true,collapsible:true"  >
             		                    	<table border="0" cellspacing="0" cellpadding="0">
             		                          		<tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">用户号码：</td><td id="acc_nbr_dev" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right"></td><td id="" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">发展人名称：</td><td id="developer_name" ></td>
             		                    	            <td style="text-align:right">发展人编码：</td><td id="developer_code"></td>
             		                    	      </tr>
             		                    	       <tr> 
             		                    	            <td style="height:30px;text-align:right">发展渠道名称：</td><td id="develop_depart_name" ></td>  
             		                    	            <td style="height:30px;text-align:right">发展渠道编码：</td><td id="develop_depart_id" ></td>              		                    	            
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">发展类型：</td><td id="develop_type" ></td>
             		                    	            <td style="text-align:right">发展对象编码：</td><td id="develop_target_code"></td>
             		                    	      </tr>
             		                    	      <tr> 
             		                    	            <td style="height:30px;text-align:right">发展时间：</td><td id="develop_time" ></td>  
             		                    	      </tr>
             		                          </table>
             		                    </div>
             		                  </td>
             		                  <td>
             		                     <div id="serv_ord_person" class="easyui-panel" style="width:500px;height:230px;padding:3px;"  title="个客信息" data-options="collapsed:true,collapsible:true"  >
             		                    	<table border="0" cellspacing="0" cellpadding="0">
             		                          	<tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">客户ID：</td><td id="cust_id" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">客户名称:</td><td id="cust_name" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">客户新老标志：</td><td id="new_flag" ></td>
             		                    	            <td style="text-align:right">客户联系电话：</td><td id="cust_phone"></td>
             		                    	      </tr>
             		                    	       <tr> 
             		                    	            <td style="height:30px;text-align:right">EMAIL：</td><td id="email" ></td>  
             		                    	            <td style="height:30px;text-align:right">客户地址：</td><td id="sex" ></td>              		                    	            
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">客户类型：</td><td id="cust_type" ></td>
             		                    	            <td style="text-align:right">证件类型：</td><td id="cert_type"></td>
             		                    	      </tr>
             		                    	       <tr> 
             		                    	            <td style="height:30px;text-align:right">证件号码：</td><td id="cert_no" ></td>  
             		                    	            <td style="height:30px;text-align:right">证件地址：</td><td id="cert_address" ></td>              		                    	            
             		                    	      </tr>
             		                    	       <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">证件有效期：</td><td id="cert_expire_date" ></td>
             		                    	            <td style="text-align:right"></td><td id=""></td>
             		                    	      </tr>
             		                    	       <tr> 
             		                    	            <td style="height:30px;text-align:right">证件照片：</td><td id="cert_link1" ></td>  
             		                    	            <td style="height:30px;text-align:right"></td><td id="cert_link2" ></td>              		                    	            
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">证件其他照片：</td><td id="cert_link3" ></td>
             		                    	            <td style="text-align:right"></td><td id=""></td>
             		                    	      </tr>
             		                          </table>
             		                    </div>
             		                  </td>
             					      </tr>
             					       <tr>
             		                  <td>
             		                    <div id="serv_ord_prod_map" class="easyui-panel" style="width:500px;height:230px;padding:3px;"  title="产品信息" data-options="collapsed:true,collapsible:true"  >
             		                    	<span id="serv_ord_prod_map_res"></span>
             		                    	<table border="0" cellspacing="0" cellpadding="0">
             		                          		<tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">产品名称：</td><td id="prod_name" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">产品编码：</td><td id="prod_code" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">首月计费方式：</td><td id="first_month_rent" ></td>
             		                    	            <td style="text-align:right"></td><td id=""></td>
             		                    	      </tr>
             		                    	       <tr> 
             		                    	            <td style="height:30px;text-align:right">漫游级别：</td><td id="roam_level" ></td>  
             		                    	            <td style="height:30px;text-align:right">呼叫级别：</td><td id="call_level" ></td>              		                    	            
             		                    	      </tr>
             		                          </table>
             		                    </div>
             		                  </td>
             		                  <td>
             		                     <div id="serv_ord_package" class="easyui-panel" style="width:500px;height:230px;padding:3px;"  title="套餐信息" data-options="collapsed:true,collapsible:true"  >
             		                    	<span id="serv_ord_package_res"></span>
             		                    	<table border="0" cellspacing="0" cellpadding="0">
             		                          		<tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">套餐名称：</td><td id="pack_name" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">套餐编码：</td><td id="pack_code" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">开始时间：</td><td id="begin_time" ></td>
             		                    	            <td style="text-align:right">结束时间：</td><td id="end_time"></td>
             		                    	      </tr>
             		                          </table>
             		                    </div>
             		                  </td>
             					      </tr>
             					       <tr>
             		                  <td>
             		                    <div id="serv_ord_terminal" class="easyui-panel" style="width:500px;height:230px;padding:3px;"  title="终端信息" data-options="collapsed:true,collapsible:true"  >
             		                    	<span id="serv_ord_terminal_res"></span>
             		                    	<table border="0" cellspacing="0" cellpadding="0">
             		                          	<tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">终端名称：</td><td id="terminal_name" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">终端编码：</td><td id="terminal_code" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">终端类型：</td><td id="terminal_type" ></td>
             		                    	            <td style="text-align:right">终端类型名称：</td><td id="terminal_type_name"></td>
             		                    	      </tr>
             		                    	      <tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">销售价格：</td><td id="sale_price" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">成本价格：</td><td id="cost_price" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">品牌：</td><td id="terminal_brand" ></td>
             		                    	            <td style="text-align:right">颜色：</td><td id="terminal_color"></td>
             		                    	      </tr>
             		                          </table>
             		                    </div>
             		                  </td>
             		                  <td>
             		                     <div id="serv_ord_activity" class="easyui-panel" style="width:500px;height:230px;padding:3px;"  title="优惠活动信息" data-options="collapsed:true,collapsible:true"  >
             		                    	<span id="serv_ord_activity_res"></span>
             		                    	<table border="0" cellspacing="0" cellpadding="0">
             		                          	<tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">活动ID：</td><td id="activity_id" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">活动名称：</td><td id="activity_name" style="width:150px"></td>
             		                    	      </tr>
             		                          </table>
             		                    </div>
             		                  </td>
             					      </tr>
             					       <tr>
             		                  <td>
             		                    <div id="serv_ord_agreement" class="easyui-panel" style="width:500px;height:230px;padding:3px;"  title="协议信息" data-options="collapsed:true,collapsible:true"  >
             		                    	<table border="0" cellspacing="0" cellpadding="0">
             		                          	<tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">协议ID：</td><td id="agreement_id" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right"></td><td id="" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">入网限定月份数：</td><td id="limit_months" ></td>
             		                    	            <td style="text-align:right">入网限制类型：</td><td id="limit_type"></td>
             		                    	      </tr>
             		                    	      <tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">协议结束方式：</td><td id="end_method" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">担保方式：</td><td id="guarant_type" style="width:150px"></td>
             		                    	      </tr>
             		                          </table>
             		                    </div>
             		                  </td>
             		                  <td>
             		                     <div id="serv_ord_sim" class="easyui-panel" style="width:500px;height:230px;padding:3px;"  title="SIM卡信息" data-options="collapsed:true,collapsible:true"  >
             		                    	<table border="0" cellspacing="0" cellpadding="0">
             		                          	<tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">SIMID：</td><td id="sim_id" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">IMSI：</td><td id="imsi" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">卡费：</td><td id="card_fee" ></td>
             		                    	            <td style="text-align:right">卡类型：</td><td id="card_type"></td>
             		                    	      </tr>
             		                    	      <tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">白卡数据：</td><td id="card_data" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">写卡业务流水：</td><td id="card_data_proc_id" style="width:150px"></td>
             		                    	      </tr>
             		                          </table>
             		                    </div>
             		                  </td>
             					      </tr>
             					       <tr>
             		                  <td>
             		                    <div id="serv_ord_fix" class="easyui-panel" style="width:500px;height:230px;padding:3px;"  title="固网信息" data-options="collapsed:true,collapsible:true"  >
             		                    	<table border="0" cellspacing="0" cellpadding="0">
             		                          	<tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">逻辑号码：</td><td id="acc_nbr_fix" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">物理号码：</td><td id="machine_nbr" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">安装地址：</td><td id="install_address" ></td>
             		                    	            <td style="text-align:right">标准地址编码：</td><td id="stardand_addr_id_fix"></td>
             		                    	      </tr>
             		                    	      <tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">号码地域：</td><td id="nbr_area_code_fix" style="width:150px"></td>
             		                    	      </tr>
             		                          </table>
             		                    </div>
             		                  </td>
             		                  <td>
             		                     <div id="serv_ord_m165" class="easyui-panel" style="width:500px;height:230px;padding:3px;"  title="宽带信息" data-options="collapsed:true,collapsible:true"  >
             		                    	<table border="0" cellspacing="0" cellpadding="0">
             		                          	<tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">宽带账号：</td><td id="acc_nbr" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">密码：</td><td id="passwd" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">宽带编码：</td><td id="line_nbr" ></td>
             		                    	            <td style="text-align:right">标准地址编码：</td><td id="stardand_addr_id"></td>
             		                    	      </tr>
             		                    	      <tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">安装地址：</td><td id="install_address_fix" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">号码地域：</td><td id="nbr_area_code" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">接入方式：</td><td id="accept_type" ></td>
             		                    	            <td style="text-align:right">速率：</td><td id="speed"></td>
             		                    	      </tr>
             		                          </table>
             		                    </div>
             		                  </td>
             					      </tr>
             					      <tr>
             		                  <td>
             		                    <div id="serv_ord_goodnbr" class="easyui-panel" style="width:500px;height:230px;padding:3px;"  title="靓号信息" data-options="collapsed:true,collapsible:true"  >
             		                    	<table border="0" cellspacing="0" cellpadding="0">
             		                          		<tr> 
             		                    	            <td style="height:30px;width:100px;text-align:right">靓号级别：</td><td id="nbr_level" style="width:150px"></td>
             		                    	            <td style="width:100px;text-align:right">协议时长：</td><td id="time_dur_pro" style="width:150px"></td>
             		                    	      </tr>
             		                    	      <tr style="background:#fafafa;"> 
             		                    	            <td style="height:30px;text-align:right">靓号预存：</td><td id="nbr_pre_save" ></td>
             		                    	            <td style="text-align:right">月承诺消费：</td><td id="month_consume"></td>
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
	serv_order_no = Request["serv_order_no"];
	sale_order_no = Request["sale_order_no"];
	ofr_order_no = Request["ofr_order_no"];
	finish_flag = Request["finish_flag"];
	getData();	
});  
  
function getData(){
	 var data = {
       	 jsession_id:"555555555555555555",
       	 order_no:serv_order_no,
       	 query_type:"101",
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
            	if(data.infoServiceOrderList != null){
            		var infoServiceOrder = data.infoServiceOrderList[0];
            		$('#serv_order_no').html(infoServiceOrder.serv_order_no);
            		$('#oper_name').html(infoServiceOrder.oper_name);
            		$('#oper_code').html(infoServiceOrder.oper_code);
            		$('#accept_oper_name').html(infoServiceOrder.accept_oper_name);
            		$('#acc_nbr').html(infoServiceOrder.acc_nbr);
            		$('#cancle_flag').html(infoServiceOrder.cancle_flag);
            		$('#order_state').html(infoServiceOrder.order_state);
            		$('#create_time').html(infoServiceOrder.create_time);
            		$('#accept_depart_no').html(infoServiceOrder.accept_depart_no);
            		$('#accept_depart_name').html(infoServiceOrder.accept_depart_name);
            		
            	}
            	
            	//服务订单优惠活动
            	if(data.infoServiceOrderActivityList != null){
            		var infoServiceOrderActivity = data.infoServiceOrderActivityList[0];
            		$('#activity_id').html(infoServiceOrderActivity.activity_id);
            		$('#activity_name').html(infoServiceOrderActivity.activity_name);
            		
            		/* var infoServiceOrderActivity = data.infoServiceOrderActivityList;
            		var servActivityDetail = '<table border="0" cellspacing="0" cellpadding="0">';
            		for(var i = 0;i < infoServiceOrderActivity.length;i++){
            			servActivityDetail +='<tr>'
         	            +'<td style="height:30px;width:100px;text-align:right">活动ID：</td><td id="activity_id_"'+i+' style="width:150px">'+infoServiceOrderActivity[i].activity_id+'</td>'
         	           	+'<td style="width:100px;text-align:right">活动名称：</td><td id="activity_name_"'+i+' style="width:150px">'+infoServiceOrderActivity[i].activity_name+'</td>'
         	          	+'</tr>';
              
            		}
            		servActivityDetail +=' </table>';
            		$("#serv_ord_activity_res").append(servActivityDetail); */
            	}
            	//服务订单费用
            	if(data.infoServiceOrderFeeList != null){
            		var infoServiceOrderFee = data.infoServiceOrderFeeList[0];
            		$('#fee_item_name').html(infoServiceOrderFee.fee_item_name);
            		$('#fee_item_type').html(infoServiceOrderFee.fee_item_type);
            		$('#total_fee').html(infoServiceOrderFee.total_fee);
            		$('#payed_fee').html(infoServiceOrderFee.payed_fee);
            		$('#discount_fee').html(infoServiceOrderFee.discount_fee);
            		$('#discount_comments').html(infoServiceOrderFee.discount_comments);
            	}
            	//服务订单收费
            	if(data.infoServiceOrderPayList != null){
            		var infoServiceOrderPay = data.infoServiceOrderPayList[0];
            		$('#pay_type').html(infoServiceOrderPay.pay_type);
            		$('#pay_sn').html(infoServiceOrderPay.pay_sn);
            		$('#pay_total_fee').html(infoServiceOrderPay.total_fee);
            		$('#pay_payed_fee').html(infoServiceOrderPay.payed_fee);
            		$('#pay_discount_fee').html(infoServiceOrderPay.discount_fee);
            		$('#pay_oper_no').html(infoServiceOrderPay.pay_oper_no);
            		$('#pay_oper_name').html(infoServiceOrderPay.pay_oper_name);
            		
            	}
            	
            	//服务订单银行托收
            	if(data.infoServiceOrderCollectionList != null){
            		var infoServiceOrderCollection = data.infoServiceOrderCollectionList[0];
            		$('#account_id').html(infoServiceOrderCollection.account_id);
            		$('#bank_code').html(infoServiceOrderCollection.bank_code);
            		$('#bank_account').html(infoServiceOrderCollection.bank_account);
            		$('#bank_name').html(infoServiceOrderCollection.bank_name);
            		$('#bank_area_name').html(infoServiceOrderCollection.bank_area_name);
            		$('#bank_area_code').html(infoServiceOrderCollection.bank_area_code);
            		$('#collection_type').html(infoServiceOrderCollection.collection_type);
            		$('#collection_sn').html(infoServiceOrderCollection.collection_sn);
            		$('#save_type').html(infoServiceOrderCollection.save_type);
            		$('#upper_bank_code').html(infoServiceOrderCollection.upper_bank_code);
            		
            	}
            	
            	//服务订单担保人
            	if(data.infoServiceOrderGuarantorList != null){
            		var infoServiceOrderGuarantor = data.infoServiceOrderGuarantorList[0];
            		$('#guarant_type').html(infoServiceOrderGuarantor.guarant_type);
            		$('#guarant_project').html(infoServiceOrderGuarantor.guarant_project);
            		$('#guarant_method').html(infoServiceOrderGuarantor.guarant_method);
            		$('#guarant_eff_time').html(infoServiceOrderGuarantor.guarant_eff_time);
            		$('#guarant_sn').html(infoServiceOrderGuarantor.guarant_sn);
            		$('#guarant_depart_code').html(infoServiceOrderGuarantor.guarant_depart_code);
            		
            	}
            	
            	//服务订单属性
            	if(data.infoServiceOrderAttrList != null){
            		var infoServiceOrderAttr = data.infoServiceOrderAttrList[0];
            		$('#attr_code').html(infoServiceOrderAttr.attr_code);
            		$('#attr_type').html(infoServiceOrderAttr.attr_type);
            		$('#attr_after_value').html(infoServiceOrderAttr.attr_after_value);
            		$('#attr_before_value').html(infoServiceOrderAttr.attr_before_value);
            		$('#curr_seq').html(infoServiceOrderAttr.curr_seq);
            		$('#upper_seq').html(infoServiceOrderAttr.upper_seq);
            		/* var infoServiceOrderAttrList = data.infoServiceOrderAttrList;
            		var servAttrDetail = '<table border="0" cellspacing="0" cellpadding="0">';
            		for(var i = 0;i<infoServiceOrderAttrList.length;i++){
            			servAttrDetail +='<tr>' 
            				+'<td style="height:30px;width:100px;text-align:right">属性编码：</td><td id="attr_code" style="width:150px"></td>' 
            				+'<td style="width:100px;text-align:right">属性类型：</td><td id="attr_type" style="width:150px"></td>' 
            				+'</tr>' 
            				+' <tr style="background:#fafafa;">'  
            				+'<td style="height:30px;text-align:right">属性值：</td><td id="attr_after_value" ></td>' 
            				+'<td style="text-align:right">属性原值：</td><td id="attr_before_value"></td>' 
            				+'</tr>' 
            				+'<tr>'  
            				+' <td style="height:30px;text-align:right">本级序列：</td><td id="curr_seq" ></td>'   
            				+' <td style="height:30px;text-align:right">上级序列：</td><td id="upper_seq" ></td>'               		                    	            
            				+' </tr>';
            		}
            		servAttrDetail +='</table>';
            		$('#ser_ord_attr_res').append(servAttrDetail); */
            	}
            	
            	//服务订单发展人
            	if(data.infoServiceOrderDeveloperList != null){
            		var infoServiceOrderDeveloper = data.infoServiceOrderDeveloperList[0];
            		$('#acc_nbr_dev').html(infoServiceOrderDeveloper.acc_nbr);
            		$('#developer_name').html(infoServiceOrderDeveloper.developer_name);
            		$('#developer_code').html(infoServiceOrderDeveloper.developer_code);
            		$('#develop_depart_name').html(infoServiceOrderDeveloper.develop_depart_name);
            		$('#develop_depart_id').html(infoServiceOrderDeveloper.develop_depart_id);
            		$('#develop_type').html(infoServiceOrderDeveloper.develop_type);
            		$('#develop_target_code').html(infoServiceOrderDeveloper.develop_target_code);
            		$('#develop_time').html(infoServiceOrderDeveloper.develop_time);
            		
            	}
            	
            	//服务订单产品列表
            	if(data.infoServiceOrderProdMapList != null){
            		var infoServiceOrderProdMap = data.infoServiceOrderProdMapList[0];
            		$('#prod_name').html(infoServiceOrderProdMap.prod_name);
            		$('#prod_code').html(infoServiceOrderProdMap.prod_code);
            		$('#first_month_rent').html(infoServiceOrderProdMap.first_month_rent);
            		$('#roam_level').html(infoServiceOrderProdMap.roam_level);
            		$('#call_level').html(infoServiceOrderProdMap.call_level);
            		
            		
            		/* var infoServiceOrderProdMapList = data.infoServiceOrderProdMapList;
            		var servProcMapDetail = '<table border="0" cellspacing="0" cellpadding="0">';
            		for(var i = 0;i < infoServiceOrderProdMapList.length;i++){
            			servProcMapDetail +='<tr>' 
            				+'<td style="height:30px;width:100px;text-align:right">产品名称：</td><td id="prod_name_"'+i+' style="width:150px">'+infoServiceOrderProdMapList[i].prod_name+'</td>' 
            				+' <td style="width:100px;text-align:right">产品编码：</td><td id="prod_code_"'+i+' style="width:150px">'+infoServiceOrderProdMapList[i].prod_code+'</td>' 
            				+'</tr>' 
            				+'<tr style="background:#fafafa;">'  
            				+' <td style="height:30px;text-align:right">首月计费方式：</td><td id="first_month_rent_"'+i+' >'+infoServiceOrderProdMapList[i].first_month_rent+'</td>' 
            				+'<td style="text-align:right"></td><td id=""></td>' 
            				+' </tr>' 
            				+'<tr> ' 
            				+' <td style="height:30px;text-align:right">漫游级别：</td><td id="roam_level_"'+i+' >'+infoServiceOrderProdMapList[i].roam_level+'</td>'  
            				+'<td style="height:30px;text-align:right">呼叫级别：</td><td id="call_level_"'+i+' >'+infoServiceOrderProdMapList[i].call_level+'</td>'             		                    	            
            				+' </tr>';
            			
            		}
            		servProcMapDetail +=' </table>'; 
            		$("#serv_ord_prod_map_res").append(servProcMapDetail); */
            	}
            	
            	//服务订单套餐
            	if(data.infoServiceOrderPackageList != null){
            		var infoServiceOrderPackage = data.infoServiceOrderPackageList[0];
            		$('#pack_name').html(infoServiceOrderPackage.pack_name);
            		$('#pack_code').html(infoServiceOrderPackage.pack_code);
            		$('#begin_time').html(infoServiceOrderPackage.begin_time);
            		$('#end_time').html(infoServiceOrderPackage.end_time);
            		
            		/* var infoServiceOrderPackageList = data.infoServiceOrderPackageList;
            		var servPackageDetail = '<table border="0" cellspacing="0" cellpadding="0">';
            		for(var i = 0;i<infoServiceOrderPackageList.length;i++){
            			servPackageDetail +='<tr>'
            				+'<td style="height:30px;width:100px;text-align:right">套餐名称：</td><td id="pack_name_"'+i+' style="width:150px">'+infoServiceOrderPackageList[i].pack_name+'</td>'
            				+' <td style="width:100px;text-align:right">套餐编码：</td><td id="pack_code_"'+i+' style="width:150px">'+infoServiceOrderPackageList[i].pack_code+'</td>'
            				+'</tr>'
            				+'<tr style="background:#fafafa;"> '
            				+'<td style="height:30px;text-align:right">开始时间：</td><td id="begin_time_"'+i+' >'+infoServiceOrderPackageList[i].begin_time+'</td>'
            				+' <td style="text-align:right">结束时间：</td><td id="end_time_"'+i+'>'+infoServiceOrderPackageList[i].end_time+'</td>'
            				+'</tr>';
            			
            		}
            		servPackageDetail +='</table>';
            		$('#serv_ord_package_res').append(servPackageDetail); */
            	}
            	
            	//服务订单终端信息
            	if(data.infoServiceOrderTerminalList != null){
            		var infoServiceOrderTerminal = data.infoServiceOrderTerminalList[0];            		
            		$('#terminal_name').html(infoServiceOrderTerminal.terminal_name);
            		$('#terminal_code').html(infoServiceOrderTerminal.terminal_code);
            		$('#terminal_type').html(infoServiceOrderTerminal.terminal_type);
            		$('terminal_type_name#').html(infoServiceOrderTerminal.terminal_type_name);
            		$('#sale_price').html(infoServiceOrderTerminal.sale_price);
            		$('#terminal_type_name').html(infoServiceOrderTerminal.terminal_type_name);
            		$('#sale_price').html(infoServiceOrderTerminal.sale_price);
            		$('#cost_price').html(infoServiceOrderTerminal.cost_price);
            		$('#terminal_brand').html(infoServiceOrderTerminal.terminal_brand);
            		$('#terminal_color').html(infoServiceOrderTerminal.terminal_color);
            		/* var infoServiceOrderTerminalList = data.infoServiceOrderTerminalList;
            		var servTerminalDetail = '<table border="0" cellspacing="0" cellpadding="0">';
            		for(var i = 0;i<infoServiceOrderTerminalList.length;i++){
            			servTerminalDetail +='<tr>'
            				+'<td style="height:30px;width:100px;text-align:right">终端名称：</td><td id="terminal_name_"'+i+' style="width:150px">'+infoServiceOrderTerminalList[i].terminal_name+'</td>'
            				+'<td style="width:100px;text-align:right">终端编码：</td><td id="terminal_code_"'+i+' style="width:150px">'+infoServiceOrderTerminalList[i].terminal_code+'</td>'
            				+'</tr>'
            				+'<tr style="background:#fafafa;"> '
            				+'<td style="height:30px;text-align:right">终端类型：</td><td id="terminal_type_"'+i+' >'+infoServiceOrderTerminalList[i].terminal_type+'</td>'
            				+'<td style="text-align:right">终端类型名称：</td><td id="terminal_type_name_"'+i+'>'+infoServiceOrderTerminalList[i].terminal_type_name+'</td>'
            				+'</tr>'
            				+'<tr> '
            				+' <td style="height:30px;width:100px;text-align:right">销售价格：</td><td id="sale_price_"'+i+' style="width:150px">'+infoServiceOrderTerminalList[i].sale_price+'</td>'
            				+' <td style="width:100px;text-align:right">成本价格：</td><td id="cost_price_"'+i+' style="width:150px">'+infoServiceOrderTerminalList[i].cost_price+'</td>'
            				+' </tr>'
            				+' <tr style="background:#fafafa;"> '
            				+' <td style="height:30px;text-align:right">品牌：</td><td id="terminal_brand_"'+i+' >'+infoServiceOrderTerminalList[i].terminal_brand+'</td>'
            				+'<td style="text-align:right">颜色：</td><td id="terminal_color_"'+i+'>'+infoServiceOrderTerminalList[i].terminal_color+'</td>'
            				+'</tr>';
            			
            		}
            		servTerminalDetail +='</table>';
            		$("#serv_ord_terminal_res").append(servTerminalDetail); */
            	}
            	
            	//服务订单协议
            	if(data.infoServiceOrderAgreementList != null){
            		var infoServiceOrderAgreement = data.infoServiceOrderAgreementList[0];
            		$('#agreement_id').html(infoServiceOrderAgreement.agreement_id);
            		$('#limit_months').html(infoServiceOrderAgreement.limit_months);
            		$('#limit_type').html(infoServiceOrderAgreement.limit_type);
            		$('#end_method').html(infoServiceOrderAgreement.end_method);
            		$('#guarant_type').html(infoServiceOrderAgreement.guarant_type);
            		
            	}
            	
            	//服务订单SIM卡
            	if(data.InfoServiceOrderSimCardList != null){
            		var InfoServiceOrderSimCard = data.InfoServiceOrderSimCardList[0];
            		$('#sim_id').html(InfoServiceOrderSimCard.sim_id);
            		$('#imsi').html(InfoServiceOrderSimCard.imsi);
            		$('#card_fee').html(InfoServiceOrderSimCard.card_fee);
            		$('#card_type').html(InfoServiceOrderSimCard.card_type);
            		$('#card_data').html(InfoServiceOrderSimCard.card_data);
            		$('#card_data_proc_id').html(InfoServiceOrderSimCard.card_data_proc_id);
            		
            	}
            	
            	//服务订单固网
            	if(data.infoServiceOrderFixList != null){
            		var infoServiceOrderFix = data.infoServiceOrderFixList[0];
            		$('#acc_nbr_fix').html(infoServiceOrderFix.acc_nbr);
            		$('#machine_nbr').html(infoServiceOrderFix.machine_nbr);
            		$('#install_address_fix').html(infoServiceOrderFix.install_address);
            		$('#stardand_addr_id_fix').html(infoServiceOrderFix.stardand_addr_id);
            		$('#nbr_area_code_fix').html(infoServiceOrderFix.nbr_area_code);
            		
            	}
            	
            	//服务订单宽带
            	if(data.infoServiceOrderM165List != null){
            		var infoServiceOrderM165 = data.infoServiceOrderM165List[0];
            		$('#acc_nbr_m165').html(infoServiceOrderM165.acc_nbr_m165);
            		$('#passwd').html(infoServiceOrderM165.passwd);
            		$('#line_nbr').html(infoServiceOrderM165.line_nbr);
            		$('#stardand_addr_id').html(infoServiceOrderM165.stardand_addr_id);
            		$('#install_address').html(infoServiceOrderM165.install_address);
            		$('#nbr_area_code').html(infoServiceOrderM165.nbr_area_code);
            		$('#accept_type').html(infoServiceOrderM165.accept_type);
            		$('#speed').html(infoServiceOrderM165.speed);
            		
            	}
            	
            	//服务订单靓号
            	if(data.infoServiceOrderGoodNbrList != null){
            		var infoServiceOrderGoodNbr = data.infoServiceOrderGoodNbrList[0];
            		$('#nbr_level').html(infoServiceOrderM165.nbr_level);
            		$('#time_dur_pro').html(infoServiceOrderM165.time_dur_pro);
            		$('#nbr_pre_save').html(infoServiceOrderM165.nbr_pre_save);
            		$('#month_consume').html(infoServiceOrderM165.month_consume);
            	}
            	
            	//服务订单拓展属性横表
            	if(data.infoServiceOrderExtList != null){
            		var infoServiceOrderExtList = data.infoServiceOrderExtList;
            		
            		/* var servExtDetail = '<table border="0" cellspacing="0" cellpadding="0">';
            		for(var i = 0;i<infoServiceOrderExtList.length;i++){
            			servExtDetail +='<tr>';
            			
            		} */
            	}
            	
            	//客户个人信息表
            	if(data.infoServiceOrderPersionList != null){
            		var infoServiceOrderPersion = data.infoServiceOrderPersionList[0];
            		$('#cust_id').html(infoServiceOrderPersion.cust_id);
            		$('#cust_name').html(infoServiceOrderPersion.cust_name);
            		$('#new_flag').html(infoServiceOrderPersion.new_flag);
            		$('#cust_phone').html(infoServiceOrderPersion.cust_phone);
            		$('#email').html(infoServiceOrderPersion.email);
            		$('#cust_address').html(infoServiceOrderPersion.cust_address);
            		$('#sex').html(infoServiceOrderPersion.sex);
            		$('#cust_type').html(infoServiceOrderPersion.cust_type);
            		$('#cert_type').html(infoServiceOrderPersion.cert_type);
            		$('#cert_no').html(infoServiceOrderPersion.cert_no);
            		$('#cert_address').html(infoServiceOrderPersion.cert_address);
            		$('#cert_expire_date').html(infoServiceOrderPersion.cert_expire_date);
            		$('#cert_link1').html(infoServiceOrderPersion.cert_link1);
            		$('#cert_link2').html(infoServiceOrderPersion.cert_link2);         		
            		$('#cert_link3').html(infoServiceOrderPersion.cert_link3);
            		
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