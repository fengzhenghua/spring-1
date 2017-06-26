$(document).ready(function() {
	/*初始化头*/
//	$("a[index_list=1]",parent.document).each(function(){
//		if($(this).attr("class")=="hover"){
//			$(this).removeClass();
//			$(this).addClass('sale_enter sale_enter_none');
//		}
//		var hrefurl = $(this).attr("hrefUrl");
//		console.log(hrefurl);
//		var hrefurlEnd = hrefurl.substring(hrefurl.lastIndexOf("/")+1,hrefurl.length);
//		if(hrefurlEnd=="orderProsessing"){
//			$(this).removeClass();
//			$(this).addClass('hover');
//		}
//	});
	var btn_search_flag = $("#btn_search_flag").val();
	if(btn_search_flag == "1"){
		$("#start_time").val($("#hidden_start_time").val());
		$("#end_time").val($("#hidden_end_time").val());
		$("#cust_name").val($("#hidden_cust_name").val());
		selectPage();
	}
	else{
		selectPage();
		$("#query").click(function() {
			selectPage();
		});
	}
});

//订单查询
function queryOrderProsessing(){
	var start_time = $.trim($("#start_time").val());
	var end_time = $.trim($("#end_time").val());
	//var order_status = "A10";
//	var order_status = $.trim($("#order_status").val());
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

function toDaishou(order_id){
	var start_time = $.trim($("#start_time").val());
	var end_time = $.trim($("#end_time").val());
	var cust_name = $.trim($("#cust_name").val());
	var data={
			"order_id":order_id,
			"start_time":start_time,
			"end_time":end_time,
			"cust_name":cust_name
		  };
    	var URL = application.fullPath + "authority/orderFee/orderFeeJudge";

		$.ajax({
			type : "POST",
			url : URL,
			data : data,
			dataType:'json',
			waitMsg:"正在校验，请稍候......",
			success:function(message){
				var jsonObj=eval(message);
				var payflag;
				if(jsonObj.type=='success'){
					payflag = message.args.pay_flag;
				}
				if(payflag=='P'||payflag=='C'){
					window.location.href = application.fullPath + "authority/orderFee/orderProcessingPayFee?order_id="+order_id+"&start_time="+start_time+"&end_time="+end_time+"&cust_name="+cust_name;
				}else{
					var dialog=$.dialog({
						   title:'提示',//提示title
						   content:'订单已缴费，请进行确认。',//显示的内容，支持html格式。
						   buttons:[{id:'btn_ok',text:'确定',
								   onClick:function(){
									   dialog.close();
									   window.location.href = application.fullPath + "authority/orderFee/orderFeeIndex";
								   }//点击时候回调函数
						   }]
					   });
				}
			},
			error: function() {
				$.alert("error");
	        }
		});
	
	
	
	
	
}
function toDaibeika(order_id){
	window.location.href = application.fullPath + "authority/checkResInfo/toCheckResInfo?order_id="+order_id;
}


//分页
function selectPage() {
	var start_time = $.trim($("#start_time").val());
	var end_time = $.trim($("#end_time").val());	
	var cust_name = $.trim($("#cust_name").val());
	var rand_id = $.trim($("#rand_id").val());
	if(rand_id == null || rand_id == ""){
		rand_id = "0";
	}
	$("#showData").html('');
	$("#pager").pages({
		url : application.fullPath + 'authority/orderFee/queryOrderFeeProsessing',
		onLoad:function(infoQryOrderVo,i){
			var cc = "odd";
			if(i % 2 == 1){
				cc = "even";
			}
			var html = '<table id="grid" class="box_list" width="808" cellspacing="1" cellpadding="0" border="0">'
				+'<tbody>'
				+'<tr class="'+cc+'">'
				+'<td width="130" height="32" onClick="jump(\''+infoQryOrderVo.order_id+'\')";>'+infoQryOrderVo.order_id+'</td>'
				+'<td width="130">'+infoQryOrderVo.update_time_str+'</td>'
				+'<td width="150">'+infoQryOrderVo.tele_type+'</td>'
				+'<td width="130">'+infoQryOrderVo.order_source_name+'</td>'
				+'<td width="140">'+infoQryOrderVo.cust_name+'</td>'
//				+'<td width="120">'+infoQryOrderVo.order_status_name+'</td>'
				+'<td>';
				html += '<a href="javascript:void(0);" onclick=\"toDaishou(\''+ infoQryOrderVo.order_id+ '\');\">缴费</a>';
				html += '</td>'
				+'<td style="display:none">'+infoQryOrderVo.order_status+'</td>'
				+'</tr>'
				+'</tbody>'
				+'</table>';
			return html;
		},
		getData:function(){
			return {"start_time":start_time,"end_time": end_time,"cust_name":cust_name,"rand_id":rand_id};//"order_status":order_status ,
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
			console.log("查询成功");
			var dingdanhao = message.args.order_id
			console.log("订单号  "+dingdanhao)
			$('#dingdanhao').text(dingdanhao)
			
			var zhongduanjixing = message.args.terminal_type
			console.log("终端机型  "+zhongduanjixing)
			$('#zhongduanjixing').text(zhongduanjixing)
			
			var xiaoshoupin = message.args.product_name
			console.log("销售品  "+xiaoshoupin)
			$('#xiaoshoupin').text(xiaoshoupin)
			
			var shebeihaoma = message.args.acc_nbr
			console.log("设备号码  "+shebeihaoma)
			$('#shebeihaoma').text(shebeihaoma)
			

			var xiadanshijian = message.args.create_date
			console.log("下单时间  "+ xiadanshijian)
			$('#xiadanshijian').text(xiadanshijian)
			
			var zhengjianleixing = message.args.id_type
			console.log("证件类型  "+ zhengjianleixing)
			$('#zhengjianleixing').text(xiadanshijian)
		
			var zhengjianhaoma = message.args.id_number
			console.log("证件号码  "+ zhengjianhaoma)
			$('#zhengjianhaoma').text(zhengjianhaoma)
			
			var daoqi = message.args.auth_end_date
			console.log("证件到期时间  "+ daoqi)
			$('#daoqi').text(daoqi)
			
			var zhengjiandizhi = message.args.auth_adress
			console.log("证件地址  "+ zhengjiandizhi)
			$('#zhengjiandizhi').text(zhengjiandizhi)
			
			var kehumingcheng = message.args.customer_name
			console.log("客户名称  "+ kehumingcheng)
			$('#kehumingcheng').text(kehumingcheng)
			
			var lianxirendizhi = message.args.contact_adress
			console.log("联系人地址  "+ lianxirendizhi)
			$('#lianxirendizhi').text(lianxirendizhi)
			
			var xingbie = message.args.cust_sex
			console.log("客户性别  "+ xingbie)
			if(xingbie==1){
				$('#xingbie').text("男")
			}else {
				$('#xingbie').text("女")
			}
			var chushengriqi = message.args.born_date
			console.log("出生日期  "+ chushengriqi)
			$('#chushengriqi').text(chushengriqi)
			
			var kehuyoubian = message.args.cust_post
			console.log("客户邮编  "+ kehuyoubian)
			$('#cust_post').text(kehuyoubian)
			
			var email = message.args.cust_email
			console.log("客户Email  "+ email)
			$('#email').text(email)
			
			var lianxiren = message.args.contact_name
			console.log("联系人  "+ lianxiren)
			$('#lianxiren').text(lianxiren)
			
			var lianxirendianhua = message.args.contact_phone
			console.log("联系人电话  "+ lianxirendianhua)
			$('#lianxirendianhua').text(lianxirendianhua)
			
			var zhongduanpinpai = message.args.terminal_brand
			console.log("终端品牌  "+ zhongduanpinpai)
			$('#zhongduanpinpai').text(zhongduanpinpai)
			
			var zhongduanxinghao = message.args.machine_code
			console.log("终端型号  "+ zhongduanxinghao)
			$('#zhongduanxinghao').text(zhongduanxinghao)
			
			var zhongduanchuanhao = message.args.terminal_id
			console.log("终端串号  "+ zhongduanchuanhao)
			$('#zhongduanchuanhao').text(zhongduanchuanhao)
			
			var kaleixing = message.args.card_kind
			console.log("卡类型  "+ kaleixing)
			$('#kaleixing').text(kaleixing)
			
			var iccid = message.args.card_number
			console.log("ICCID  "+ iccid)
			$('#iccid').text(iccid)
			
			var beizhu = message.args.remark_desc
			console.log("备注  "+ beizhu)
			$('#beizhu').text(beizhu)
			
		},
		error : function() {
			//失败操作的DIALOG
			$("#dev_info_head").after("<tr name='tr_dev_info'> <td width='24' height='32' align='right' bgcolor='#FBFBFB'></td><td width='110' align='left' bgcolor='#FBFBFB'  colspan='5'>操作失败，请重试！</td></tr>");
		}
	});
};


function jump(order_id){
	$.dialog({
		top:10,
		width:800,
		draggable:false,
		content:'<div class="sale_content"><div class="content"><table width="100" border="0" cellspacing="0" cellpadding="0"><tr><td valign="top" class="c_right"><div class="wrap_right"><div class="right_box"><div class="sub_title"><span class="ico">◎</span> 基本信息、客户信息</div><table width="778" border="0" align="center" cellpadding="0" cellspacing="0"> <tr><td width="66" height="25" align="right">订 单 号：</td><td width="128" id="dingdanhao"></td><td width="66" align="right" >客户名称：</td><td width="104" id="kehumingcheng"></td><td width="66" align="right" >证件号码：</td><td width="164" id="zhengjianhaoma"></td></tr><tr><td height="25" align="right">终端机型：</td><td height="25" id="zhongduanjixing"></td><td align="right">销售品：</td><td id="xiaoshoupin"></td> </tr> <tr><td height="25" align="right">设备号码：</td><td height="25" id="shebeihaoma"></td> <td align="right" >下单时间：</td><td id="xiadanshijian"></td> </tr><table width="788" border="0" align="center" cellpadding="0" cellspacing="0" class="custom"><tr> <td height="27" align="right"> 联系人地址：</td> <td colspan="3" id="lianxirendizhi"></td> </tr><tr> <td width="104" height="27" align="right">客户性别：</td><td width="253" id="xingbie"></td><td width="90" align="right">出生日期：</td><td width="341" id="chushengriqi"></td></tr> <tr><td height="27" align="right"> 客户邮编：</td><td id="kehuyoubian"></td><td align="right">客户Email：</td> <td id="email"></td></tr> <tr><td width="104" height="27" align="right"> 联系人姓名：</td> <td width="253" id="lianxiren"></td><td width="90" align="right"> 联系人电话：</td><td id="lianxirendianhua"></td> </tr><tr><td height="27" align="right">备注：</td><td colspan="3" id="beizhu"></td></tr></table><div class="sub_title"><span class="ico">◎</span> 终端信息、卡信息</div> <table width="788" border="0" align="center" cellpadding="0" cellspacing="0" class="custom"> <tr><td height="27" align="right"> 终端品牌：</td> <td width="253" id="zhongduanpinpai"></td><td width="90" align="right">终端型号：</td> <td width="341" id="zhongduanxinghao"></td> </tr><tr> <td width="104" height="27" align="right">终端串号：</td><td colspan="3" id="zhongduanchuanhao"></td></tr> </table><table width="788" border="0" align="center" cellpadding="0" cellspacing="0" class="custom"><tr><td width="104" height="27" align="right">卡类型：</td><td width="253" id="kaleixing"></td><td width="90" align="right" >ICCID：</td><td width="341" id="iccid"></td> </tr> </table></div></div></td></tr></table></div></div>',
		onShow:showDialog(order_id)
	})
	}
