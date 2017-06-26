$(document).ready(function() {
	/*初始化头*/
	$("a[index_list=1]",parent.document).each(function(){
		if($(this).attr("class")=="hover"){
			$(this).removeClass();
			$(this).addClass('sale_enter sale_enter_none');
		}
		var hrefurl = $(this).attr("hrefUrl");
		var hrefurlEnd = hrefurl.substring(hrefurl.lastIndexOf("/")+1,hrefurl.length);
		if(hrefurlEnd=="orderProsessing"){
			$(this).removeClass();
			$(this).addClass('hover');
		}
	});
	selectPage();
	$("#query").click(function() {
		selectPage();
	});
	
});

//订单查询
function queryOrderProsessing(){
	var start_time = $.trim($("#start_time").val());
	var end_time = $.trim($("#end_time").val());
	var order_status = $.trim($("#order_status").val());
	var cust_name = $("#cust_name").val();
	
//	//一年的毫秒数
//	var mils=(1000*60*60*24*365);
//	var first_time = new Date(start_time).getTime();//开始时间的毫秒数
//	var second_time = new Date(end_time).getTime();//结束时间的毫秒数
//	var temp_time =second_time - first_time;//获得时间段的毫秒数
//	
//	var now = new Date().getTime(); //1411523198698  当前时间的毫秒数
//	var first_time_to_now = Math.abs(first_time - now); 
//	var second_time_to_now = Math.abs(second_time - now);
//	
//	if(first_time_to_now>mils){
//		$.alert("操作有误：您能查询一年内的订单！");
//		return false;
//	}
//	if(second_time_to_now>mils){
//		$.alert("操作有误：您能查询一年内的订单！");
//		return false;
//	}
//	if(temp_time>mils){
//		$.alert("操作有误：两时间间距不能超过一年");
//		return false;
//	}
	
//	var postData = $("#grid").jqGrid("getGridParam", "postData");
//	$.extend(postData, {start_time : start_time,end_time:end_time,order_status:order_status,cust_name:cust_name});
//	$("#grid").jqGrid("setGridParam", {search : false,datatype: 'json'}).trigger("reloadGrid", [ {page : 1} ]);
}

function toDaishoufei(order_id){
	window.location.href = application.fullPath + "authority/orderFee/orderProcessingPayFee?orderFlag=1&order_id="+order_id;
}
function toDaibeika(order_id,that){
	$(that).removeAttr("onclick");
	window.location.href = application.fullPath + "authority/checkResInfo/toCheckResInfo?order_id="+order_id;
}



//分页
function selectPage() {
	var start_time = $.trim($("#start_time").val());
	var end_time = $.trim($("#end_time").val());
	var order_status = $.trim($("#order_processing_status").val());
	var order_sub_type = $.trim($("#order_sub_type_status").val());
	var cust_name = $.trim($("#cust_name").val());
	$("#showData").html('');
	$("#pager").pages({
		url : application.fullPath + 'authority/order/queryOrderProsessing',
		onLoad:function(infoQryOrderVo,i){
			var cc = "odd";
			if(i % 2 == 1){
				cc = "even";
			}
			var html = '<table id="grid" class="box_list" width="808" cellspacing="1" cellpadding="0" border="0">'
				+'<tbody>'
				+'<tr class="'+cc+'">'
				+'<td width="180" height="32" onClick="jump(\''+infoQryOrderVo.order_id+'\')";>'+infoQryOrderVo.order_id+'</td>'
				+'<td width="110">'+infoQryOrderVo.update_time_str+'</td>'
				+'<td width="80">'+infoQryOrderVo.tele_type+'</td>'
				+'<td width="100">'+infoQryOrderVo.order_source_name+'</td>'
				+'<td width="110">'+infoQryOrderVo.cust_name+'</td>'
				+'<td width="120">'+infoQryOrderVo.order_status_name+'</td>'
				+'<td id = "order_sub_type" style="display:none;">'+infoQryOrderVo.order_sub_type+'</td>'
				+'<td width="108">';
				if(infoQryOrderVo.order_status=="A10"&&infoQryOrderVo.pay_flag=="C"){
					html += '<a href="javascript:void(0);" onclick=\"toDaishoufei(\''+ infoQryOrderVo.order_id+ '\');\">缴费</a>';
				}else if(infoQryOrderVo.order_status=="A10"&&infoQryOrderVo.pay_flag=="Y"){
					html += '<a href="javascript:void(0)"  onclick=\"toDaibeika(\'' + infoQryOrderVo.order_id+ '\',this);\">配卡</a>';
				}else if(infoQryOrderVo.order_status=="A10"&&infoQryOrderVo.pay_flag=="P"){
					html += '<a href="javascript:void(0);" onclick=\"toDaishoufei(\''+ infoQryOrderVo.order_id+ '\');\">缴费</a>';
				}else if(infoQryOrderVo.order_status=="A30"){
					
				}else if(infoQryOrderVo.order_status=="A20"){
					
				}else if(infoQryOrderVo.order_status=="B00"&&infoQryOrderVo.pay_flag=="P"){
					html += '<a href="javascript:void(0);" onclick=\"toDaishoufei(\''+ infoQryOrderVo.order_id+ '\');\">缴费</a>';
				}
				html += '</td>'
				+'<td style="display:none">'+infoQryOrderVo.order_status+'</td>'
				+'</tr>'
				+'</tbody>'
				+'</table>';
			return html;
		},
		getData:function(){
			return {"start_time":start_time,"end_time": end_time,"order_status":order_status ,"cust_name":cust_name,"order_sub_type":order_sub_type};
		},
		dataDiv:"showData"
	});
}

//dialog  调用函数 。
function showDialog(order_id){
	$.ajax({
		url :application.fullPath + 'authority/order/showdialog',
		type : 'post',
		data:{
			"order_id" : order_id
		},
		waitMsg : "正在查询！",
		success : function(message) {
			var dingdanhao = message.args.order_id
			$('#dingdanhao').text(dingdanhao)
			
//			var zhongduanjixing = message.args.terminal_type
//			$('#zhongduanjixing').text(zhongduanjixing)
//			<td style="display:none">终端机型：</td><td id="zhongduanjixing"></td>
			var xiaoshoupin = message.args.product_name
			$('#xiaoshoupin').text(xiaoshoupin)
			
			var shebeihaoma = message.args.acc_nbr
			$('#shebeihaoma').text(shebeihaoma)
			

			var xiadanshijian = message.args.create_date
			$('#xiadanshijian').text(xiadanshijian)
			
			var zhengjianleixing = message.args.id_type
			$('#zhengjianleixing').text(xiadanshijian)
		
			var zhengjianhaoma = message.args.id_number
			$('#zhengjianhaoma').text(zhengjianhaoma)
			
			var daoqi = message.args.auth_end_date
			$('#daoqi').text(daoqi)
			
			var zhengjiandizhi = message.args.auth_adress
			$('#zhengjiandizhi').text(zhengjiandizhi)
			
			var kehumingcheng = message.args.customer_name
			$('#kehumingcheng').text(kehumingcheng)
			
			var lianxirendizhi = message.args.contact_adress
			$('#lianxirendizhi').text(lianxirendizhi)
			
			var xingbie = message.args.cust_sex
			if(xingbie==1){
				$('#xingbie').text("男")
			}else {
				$('#xingbie').text("女")
			}
			var chushengriqi = message.args.born_date
			$('#chushengriqi').text(chushengriqi)
			
			var kehuyoubian = message.args.cust_post
			$('#cust_post').text(kehuyoubian)
			
			var email = message.args.cust_email
			$('#email').text(email)
			
			var lianxiren = message.args.contact_name
			$('#lianxiren').text(lianxiren)
			
			var lianxirendianhua = message.args.contact_phone
			$('#lianxirendianhua').text(lianxirendianhua)
			
			var zhongduanpinpai = message.args.terminal_brand
			$('#zhongduanpinpai').text(zhongduanpinpai)
			
			var zhongduanxinghao = message.args.machine_code
			$('#zhongduanxinghao').text(zhongduanxinghao)
			
			var zhongduanchuanhao = message.args.terminal_id
			$('#zhongduanchuanhao').text(zhongduanchuanhao)
			
			var kaleixing = message.args.card_kind
			$('#kaleixing').text(kaleixing)
			
			var iccid = message.args.card_number
			$('#iccid').text(iccid)
			
			var beizhu = message.args.remark_desc
			$('#beizhu').text(beizhu)
			
			var dianxinleixing = message.args.tele_type
			$('#dianxinleixing').text(dianxinleixing);
			
			var fee_name = message.args.fee_name;
			$('#fee_name').text(fee_name)
			var real_fee = message.args.real_fee;
			$('#real_fee').text(real_fee)
			var orl_fee = message.args.orl_fee;
			$('#orl_fee').text(orl_fee)
			var discount_fee = message.args.discount_fee;
			$('#discount_fee').val(discount_fee)
			var discount_reason = message.args.discount_reason;
			$('#discount_reason').val(discount_reason)
		},
		error : function() {
			//失败操作的DIALOG
			$("#dev_info_head").after("<tr name='tr_dev_info'> <td width='24' height='32' align='right' bgcolor='#FBFBFB'></td><td width='110' align='left' bgcolor='#FBFBFB'  colspan='5'>操作失败，请重试！</td></tr>");
		}
	});
};


function jump(order_id){
	var order_sub_type = $('#order_sub_type').text();
	if("10010"==order_sub_type){ //开户订单详情
		var content = '<div class="sale_content"><div class="content"><table width="100" border="0" cellspacing="0" cellpadding="0"><tr><td valign="top" class="c_right"><div class="wrap_right"><div class="right_box"><div class="sub_title"><span class="ico">◎</span> 基本信息、客户信息</div><table width="788" border="0" align="center" cellpadding="0"cellspacing="0"><tr><td width="104" height="27" align="right">订 单 号：</td><td width="253" id="dingdanhao"></td><td width="90" align="right">证件号码：</td><td width="341" id="zhengjianhaoma"></td></tr><tr><td height="27" align="right">客户名称：</td><td id="kehumingcheng"></td><td align="right">销售品：</td><td id="xiaoshoupin"></td></tr><tr><td width="104" height="27" align="right">设备号码：</td><td width="253" id="shebeihaoma"></td><td width="90" align="right">下单时间：</td><td id="xiadanshijian"></td></tr></table><table width="788" border="0" align="center" cellpadding="0" cellspacing="0" class="custom"><tr><td width="104" height="27" align="right">客户性别：</td><td width="253" id="xingbie"></td><td width="90" align="right">出生日期：</td><td width="341" id="chushengriqi"></td></tr><tr><td height="27" align="right">客户邮编：</td><td id="kehuyoubian"></td><td align="right">客户Email：</td><td id="email"></td></tr><tr><td width="104" height="27" align="right">联系人姓名：</td><td width="253" id="lianxiren"></td><td width="90" align="right">联系人电话：</td><td id="lianxirendianhua"></td></tr><tr><td height="27" align="right">联系人地址：</td><td colspan="3" id="lianxirendizhi"></td></tr><tr><td height="27" align="right">备注：</td><td colspan="3" id="beizhu"></td></tr></table><div class="sub_title"><span class="ico">◎</span> 终端信息、卡信息</div><table width="788" border="0" align="center" cellpadding="0"cellspacing="0" class="custom"><tr><td height="27" align="right">终端品牌：</td><td width="253" id="zhongduanpinpai"></td><td width="90" align="right">终端型号：</td><td width="341" id="zhongduanxinghao"></td></tr><tr><td width="104" height="27" align="right">终端串号：</td><td colspan="3" id="zhongduanchuanhao"></td></tr></table><table width="788" border="0" align="center" cellpadding="0"cellspacing="0" class="custom"><tr><td width="104" height="27" align="right">卡类型：</td><td width="253" id="kaleixing"></td><td width="90" align="right">ICCID：</td><td width="341" id="iccid"></td></tr></table></div></div></td></tr></table></div></div>';
	}
	if('10020'==order_sub_type){
		var content = '<div class="sale_content"><div class="content"><table width="100" border="0" cellspacing="0" cellpadding="0"><tr><td valign="top" class="c_right"><div class="wrap_right"><h2 class="title margin_top">订单信息</h2><div class="right_box"><table width="778" border="0" align="center" cellpadding="0"cellspacing="0"><tr><td width="66" height="25" align="right">订 单 号：</td><td width="128" id="dingdanhao"></td><td width="66" align="right">订单类型：</td><td width="104" id="kehumingcheng">缴费订单</td>	</tr><tr><td width="66" align="right">设备号码：</td><td width="164" id="shebeihaoma"></td><td width="66" align="right">电信类型：</td><td width="164" id="dianxinleixing">3G</td></tr></table></div><h2 class="title margin_top">费用信息</h2><div class="right_box"><div class="box"><table width="760" border="0" align="center" cellpadding="0"	cellspacing="1" bgcolor="#d1d7df" class="box_list"><tr class="not_bg"><th width="178" height="32" bgcolor="#eeeeee">费用名称</th>	<th width="135" bgcolor="#eeeeee">费用</th><th width="135" bgcolor="#eeeeee">实收金额</th>	<th width="135" bgcolor="#eeeeee">减免金额</th>	<th width="174" bgcolor="#eeeeee">减免原因</th></tr>	<tr><td height="32" colspan="5" bgcolor="#FFFFFF"><table width="758" border="0" cellspacing="0" cellpadding="0"><tr><td width="178" height="32" id="fee_name"></td><td width="136" id="real_fee"></td><td width="136" id="orl_fee"></td><td width="136"><input type="text" "class="edit" id="discount_fee"/></td><td width="172"><input type="text" class="edit edit_minus" id="discount_reason"/></td>	</tr>	</table>	</td>	</tr></table></div></div></div></td></tr></table></div></div>';
	}
		$.dialog({
			top:10,
			width:800,
			draggable:false,
			content:content,
			onShow:showDialog(order_id)
		})
	
}
