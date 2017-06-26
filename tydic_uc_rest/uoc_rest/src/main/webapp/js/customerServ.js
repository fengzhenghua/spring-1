
	
$(document).ready(function(){
	//132077182724
	var service_sn;
	$('#query').click(function(){
		var card_no = $.trim($('#card_no').val());  
	    var server_no = $.trim($('#server_no').val());
		var card_type = $.trim($('#card_type').val());
		if(card_no==null||card_no==''){
			card_type='';
		}
		if(card_no==''||card_no==null){//空值判断
			if(server_no == ''||server_no == null){
				$.error('至少输入一个号码，或身份证号，或业务号码！',function(){	$('#card_no').focus();});
				return false;
			}
		}
		if(server_no==''|| server_no == null){//空值判断
			if(card_no == ''|| card_no == null){
				$.error('至少输入一个号码，或身份证号，或业务号码！',function(){	$('#card_no').focus();});
				return false;
			}
		}
		if(card_no!=''&&server_no!=''){
			$.error('两个号码不能输入！',function(){	$('#card_no').focus();});
			$('#card_no').val(''); 
			$('#server_no').val('');
			return false;
		}
		if(card_no!=''){ //   身份证号不为空时
			//判断身份证号的合法性
			var regIDCard1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
			var regIDCard2=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
			if(regIDCard1.test(card_no)===false && regIDCard2.test(card_no)===false){
				$.error("您输入的身份证号有误！ ",function(){	$('#card_no').focus();}); 
				$('#card_no').val(''); 
				return false;
			}
		}
		if(server_no!=''){ //  手机号码不为空时
			//判断手机号输入的合法性
			var reg_phoneno =  /^(\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;//手机号码
			if(reg_phoneno.test(server_no)===false){
				$.error("您输入的手机号码有误！ ",function(){	$('#server_no').focus();}); 
				$('#server_no').val('');
				return false;
			}
		}
		var url = application.fullPath + "authority/customer/queryCustomerMess";
		$.ajax({
			url:url,
			type:'post',
			data:{
				'id_number' : card_no,
				'deviceNum' : server_no,
				'id_type' : card_type
			},
			waitMsg:"正在查询...",
			success:function(message){ 
				$('#custom_info_div').show();
				$("#sub_context_div").show();
				var rsp_code = message.args.rsp_code;
				if('00000'!=rsp_code){
					var rsp_desc = message.args.rsp_desc;
					$.error(rsp_desc);
					$('#custom_info_div').hide();
					$("#sub_context_div").hide();
					$('#customer_name').text("");
					$('#cardtype').text("");
					$('#cardno').text("");
					$('#leve').text("");
					$('#cust_man').text("");
					return false;
				}
				var devList=message.args.dev_list;
				if(devList==null||devList==''){
					$.error("查询错误，请重新查询！ ");
					$('#custom_info_div').hide();
					$("#sub_context_div").hide();
					$('#customer_name').text("");
					$('#cardtype').text("");
					$('#cardno').text("");
					$('#leve').text("");
					$('#cust_man').text("");
					return false;
				}
				var dataLength = devList.length;
				$("#custom_info_div").width(354*dataLength);
				if(dataLength > 2){
					$("#arrow_l").removeClass("no");
				}
				var html = '';
				var j=0;
				var deviceFirst="";
				for(var i=0;i<dataLength;i++){
					var device = devList[i];
					var product = device.devices_products;
					//产品
					var proSub = product;
					console.log("   +++++"+product.indexOf("["));
					console.log("   +++++"+product.indexOf("["));
					console.log("   +++++"+product.indexOf("["));
					console.log("   +++++"+product.indexOf("["));
					if(product.indexOf("[")>0){
						var proSub= product.substring(0, product.indexOf("["));
					}
					//电信标记
					var teleType = device.flag234;
					if(i==0){
						deviceFirst = device;
						var divStyle="pre selected ";
						//手机号码标记 title
						var titleStyle="title title_m";
						if(teleType!='2'&&teleType!='3'&&teleType!='4'){
							divStyle+=" selected_tel";
							titleStyle="title";
						}
						html+='<li teleType="'+teleType+'" devices_guishu="'+device.devices_guishu+'" devices_number="'+device.devices_number+'" id="customer_info_'+i+'" class="'+divStyle+'" devices_balance="'+device.devices_balance+'"  key="'+i+'" style="float:left;" onclick="selectedCustomer(this);">';  
						html+='	<h3 class="'+titleStyle+'">'; 
						/* 选中   selected selected_tel    电话被选中样式
 								  selected  			   手机被选中样式
						 */
						
						
					
					}else{
						var titleStyle="title title_m";
						var divStyle="pre ";
						if(teleType!='2'&&teleType!='3'&&teleType!='4'){
							titleStyle="title";
						}
						html+='<li teleType="'+teleType+'" devices_guishu="'+device.devices_guishu+'" devices_number="'+device.devices_number+'"  id="customer_info_'+i+'" class="'+divStyle+'" devices_balance="'+device.devices_balance+'" key="'+i+'" style="float:left;"  onclick="selectedCustomer(this);">'; 
						html+='	<h3 class="'+titleStyle+'">'; 
					}
						/**
						 * 		  1  有 title_m  手机样式
						 * 		  0  无          电话样式  
						 * **/
						
						html+=' 	<span>'+device.devices_status+'</span><font id="device">'+device.devices_number+'</font>';
						html+=' </h3>';
						html+='	<table width="340" border="0" cellspacing="0" cellpadding="0">';
						html+='		<tr>';
//						html+='			<td width="80" height="25" align="right" class="td_l">用户状态：</td>';
//						html+='			<td width="100">'+device.devices_status+'</td>';
						html+='			<td width="40"  height="25" align="right" class="td_l">产品：</td>';
						html+='			<td>'+proSub+'</td>';
						html+='		</tr>';	
						if(teleType!='2'&&teleType!='3'&&teleType!='4'){
							html+='		<tr>';
							html+='			<td height="25" align="right" class="td_l">装机地址：</td>';
							html+='			<td colspan="3">'+device.devices_address+'</td>';
							html+='		</tr>';	
							
						}else{
							html+='		<tr>';
							html+='			<td width="40" height="25" align="right" class="td_l">品牌：</td>';
							html+='			<td colspan="3">'+device.devices_brand+'</td>';
							html+='		</tr>';	
						}
						html+='	</table>';
						html+='</li>';
				}
			 var  cust_info=message.args.cust_info;
				$('#cardno').text(cust_info.customer_number);
				$('#customer_name').text(cust_info.customer_name);
				$('#cust_man').text(cust_info.customer_manager);
				$('#leve').text(cust_info.customer_level);
				$("#cardtype").text(cust_info.id_type)
				html+='<input type = "hidden" id="customer_selected_key" value="0"/>';
				$("#custom_info_div").html(html);
				feeManager(deviceFirst);
			},
			error:function(){
				$.error("查询错误，请重新查询！ ");
			}
			
		})
	});
	$('#reset').click(function(){
		$('#card_no').val(''); 
		$('#server_no').val('');
		$("#card_type option:first").prop("selected", 'selected');
	})
	/**
	 * 初始化收费管理模块
	 */
	
	//滑动效果
    $('#arrow_r').click(function(){
    	if($(this).hasClass("no")){
    		return;
    	}
    	var custom_info_div_left = $("#custom_info_div").css("margin-left");
    	custom_info_div_left = custom_info_div_left.substring(0,custom_info_div_left.length-2);
    	var custom_info_div_width = $("#custom_info_div").width();
    	if(0 == custom_info_div_left/1){
    		$(this).addClass("no");
    		return;
    	}else{
    		$("#custom_info_div").animate({"margin-left":custom_info_div_left/1+354});
    	}
    	var custom_info_div_left2 = custom_info_div_left/1+354;
    	if(0 == custom_info_div_left2/1){
    		$(this).addClass("no");
    	}else{
    		$(this).removeClass("no");
    	}
    	if((custom_info_div_left2/1 + custom_info_div_width/1) != 354*2){
    		$("#arrow_l").removeClass("no");
    	}
    });
    //滑动效果
    $('#arrow_l').click(function(i){
    	if($(this).hasClass("no")){
    		return;
    	}
    	var custom_info_div_left = $("#custom_info_div").css("margin-left");
    	custom_info_div_left = custom_info_div_left.substring(0,custom_info_div_left.length-2);
    	var custom_info_div_width = $("#custom_info_div").width();
    	if((custom_info_div_left/1 + custom_info_div_width/1) == 354*2){
    		$(this).addClass("no");
    	}else{
    		$("#custom_info_div").animate({"margin-left":custom_info_div_left/1+("-"+354)/1});
    	}
    	var custom_info_div_left2 = custom_info_div_left/1+("-"+354)/1;
    	if((custom_info_div_left2/1 + custom_info_div_width/1) == 354*2){
    		$(this).addClass("no");
    	}else{
    		$(this).removeClass("no");
    	}
    	if(custom_info_div_left2/1 < 0){
    		$("#arrow_r").removeClass("no");
    	}
    });
})
	/**
	 * 选中用户操作
	 */
    function selectedCustomer(obj){
		//取消上次的选中样式
		var customer_selected_key= $("#customer_selected_key").attr("value");
		var liKey = "#customer_info_"+customer_selected_key;
		$(liKey).removeClass();
		
		$(liKey).addClass("pre");
		
		//添加新选中的样式
		var teleType = $(obj).attr("teleType");
		var clickedKey = $(obj).attr("key");
		var clickedLiKey = "#customer_info_"+clickedKey;
		if(teleType==2||teleType==3||teleType==4){
			$(clickedLiKey).addClass("selected");
			
		}else{
			$(clickedLiKey).addClass("selected selected_tel");
		}
		$("#customer_selected_key").attr("value",clickedKey);
		//更新新选中的信息展示
		feeManager(obj);
    }
	/**
	 * 生成订单
	 * 
	 */
	function submit(){
		var id_type = $('#card_type').val(); //证件类型
		var id_number = $('#card_no').val();  //证件号码
		var customer_selected_key= $("#customer_selected_key").attr("value");
		var liKey = "#customer_info_"+customer_selected_key;
		//获取选中号码
		var number = $(liKey).attr("devices_number");
		var devices_guishu =  $(liKey).attr("devices_guishu");
		console.log(devices_guishu);
		console.log(devices_guishu);
		console.log(devices_guishu);
		console.log(devices_guishu);
		console.log(devices_guishu);
		var customer_name = $('#customer_name').text(); //客户名称
		var balance =  $('#balance').text(); //欠费金额
	    var payment = $('#payment').val(); //本次支付
		var rsp_code ;
		//支付金额空值判断
		if(payment==''||payment==null){
			$.error("付费金额为空，不能缴费");
			return false;
		}
		$.ajax({
			url : application.fullPath + "authority/customer/orderdo",
			type: 'post',
			data:{
				 'id_type':id_type,
				 'id_number':id_number, 
				 'server_no':number,
				 'customer_name':customer_name,
				 'balance':balance,
				 'payment':payment,
				 'devices_guishu':devices_guishu
			},
			waitMsg:"正在缴费...",
			success:function(message){
			rsp_code = message.args.rsp_code;
				if('00000'!=rsp_code){
					var rsp_desc = message.args.rsp_desc;
					$.error(rsp_desc);
					return false;
				}
					$.message({type:"success",content:"缴费成功"});
					var currentbalance = message.args.currentbalance;
				    service_sn = message.args.service_sn;
					$('#balance').html("账户余额："+currentbalance+"元");
					var customer_selected_key= $("#customer_selected_key").attr("value");
					var liKey = "#customer_info_"+customer_selected_key;
					//更新余额
					$(liKey).attr("devices_balance",currentbalance);
			},
			error:function(){
				console.log('ajax提交请求失败')
			}
		})
	
	}
	
	
	function paymentBill(){
		//打印票据方法
		var card_no = $.trim($('#card_no').val());  
	    var server_no = $.trim($('#device').text());
		var card_type = $.trim($('#card_type').val());
		var payment = $('#payment').val(); //本次支付
		var customer_name  = $('#customer_name').text();
//		console.log("全局变量service_sn"+service_sn);
//		console.log("全局变量service_sn"+service_sn);
//		console.log("全局变量service_sn"+service_sn);
		window.open(application.fullPath+"authority/wkprint/paymentbill?id_number="+card_no+"&server_no="+server_no+"&id_type="+card_type+"&payment="+payment+"&customer_name="+customer_name+"&service_sn="+service_sn,"window","toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=850,height=650,left=120,top=100");
	}

	/**
	 * 收费管理初始化
	 * @param obj
	 */
	function feeManager(obj){
		//初始化 收费管理div
		var html='';
		html+='<div class="sub_menu">';
		html+='	  <a href="#" class="hover">缴费</a>';
		html+='</div>';
		html+='<div class="sub_menu_show" id="balance">账户余额：'+$(obj).attr("devices_balance")+'元</div>';
		html+='<table width="974" border="0" align="center" cellpadding="0" cellspacing="0" class="customer_search customer_margin">';
		html+='		<tr>';
		html+='			<td width="71" height="30" align="center">付费类型：</td>';
		html+='			<td width="138">';
		html+='				<label>';
		html+=$("#pay_type_html").html();
//		html+='					<t:select id="pay_type" codeType="pay_type" clazz="sel"></t:select>';
		html+='				</label>';
		html+='			</td>';
		html+='			<td width="71" align="center">本次付费：</td>';
		html+='			<td width="240">';
		html+='				<label>';
		html+='					<input type="text" name="textfield3" class="txt_input" id="payment"/>';
		html+='				</label>';
		html+='			</td>';
		html+='			<td width="96" align="center">打印类型：</td>';
		html+='			<td width="358">';
		html+='				<select name="select3" class="sel">';
		html+='					<option value="0">收据</option>';
		html+='				</select>';
		html+='			</td>';
		html+='		</tr>';
		html+='	</table>';
		html+='<div class="ok">';
		html+='		<a id = "submit" onclick="javascript:submit();">确定</a>';
		html+='		<a id = "bill" onclick="javascript:paymentBill();">打印</a>';
		html+='</div>';
		$("#sub_context_div").html(html);
	}