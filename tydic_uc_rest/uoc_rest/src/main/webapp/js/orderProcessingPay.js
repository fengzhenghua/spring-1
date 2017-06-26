	var pay_tpye = '';
	var order_id = '';
$(document).ready(function() {
	$("#check_cust_flag").val("false");
	$("#info_confirm_flag").val("false");
	//加载时支付方式隐藏字段赋值
	 pay_type = $("#pay_type") .val();
	$("#pay_type_hidden").val(pay_type);
	/*判断加载时是PC端传过来的订单还是PAD端*/
	var real_name = $("#real_name").text();
	//电信类型
	var tele_type = $("#tele_type").text();
	//卡类型
	var card_kind_type = $("#card_kind_type").val();
	//iccid
	var baseInfo_iccid = $("#baseInfo_iccid").text();
	if(baseInfo_iccid == "" || baseInfo_iccid == null){
		$("#card_type").attr("style","display:none");
		$("#baseInfo_card_kind").attr("style","display:none");
		$("#info_iccid").attr("style","display:none");
	}
	if(card_kind_type == "01"){
		$("#baseInfo_card_kind").text("成卡");
	}
	/*当请求为PAD端时，显示未认证，并显示实名信息输入表格*/
	if(real_name =='未认证'){
		$("#real_name_authentication").attr("style","display:block");
		if(card_kind_type == "01" && tele_type == "4G"){
			$("#info_card").removeAttr("style");
		}
		$("#id_number").val("");
		$("#auth_end_date").val("");
		$("#customer_name").val("");
		$("#auth_adress").val("");
		$("#contact_adress").val("");
		$("#born_date").val("");
		$("#cust_post").val("");
		$("#cust_email").val("");
		$("#contact_name").val("");
		$("#contact_phone").val("");
		$("#remark_desc").val("");
	}
	if(real_name =='已认证' && card_kind_type == "01" && tele_type == "4G" && (baseInfo_iccid == "" || baseInfo_iccid == null)){
		$("#real_name_authentication").attr("style","display:block");
		$("#info_custAndterminal").attr("style","display:none");
		$("#info_card").removeAttr("style");
	}
	var terminal_type = $("#terminal_type").val();
	if(terminal_type == null || terminal_type == ""){
		$("#terminal_type").val("无");
	}
	//是否有终端机型标记
	var terminal_seleted = $("#terminal_seleted").val();
	if(terminal_seleted =="1"){
		$("#terminal_product").removeAttr("style");
		$("#info_terminal").removeAttr("style");
		$("#terminal_id").removeAttr("style");
	}
	/*判断选择的支付方式，并出现相应的支付方式输入框*/
	$("#pay_type").change(function(){
		/*获取支付方式*/

		 pay_type = $("#pay_type").val();

		$("#pay_type_hidden").val(pay_type);
		/*当支付方式值为10时，表示现金支付，参考号，支票，转账等输入框均不显示*/
		if(pay_type == '10'){
			$("#reference_number_tr").attr("style","display:none");
			$("#posliushui").attr("style","display:none");
			$("#chequeInfo").attr("style","display:none");
			$("#transferInfo").attr("style","display:none");
			$('.posliushui').hide();
		}if(pay_type == '18'){/*当支付方式值为18时，表示POS刷卡，参考号显示，支票，转账等输入框不显示*/
			$("#reference_number_tr").show();
			$('.posliushui').show();
		}
		/*当支付方式值为11时，表示现金支票，参考号不显示，支票信息显示，转账信息输入框不显示*/
		if(pay_type == '11'){
			$("#reference_number_tr").attr("style","display:none");
			$("#chequeInfo").attr("style","display:block");
			$("#transferInfo").attr("style","display:none");
		}
		/*当支付方式值为12时，表示转账支票，参考号和支票信息不显示，转账信息输入框显示*/
		if(pay_type == '12'){
			$("#reference_number_tr").attr("style","display:none");
			$("#chequeInfo").attr("style","display:none");
			$("#transferInfo").attr("style","display:block");
		}
	});
	
	if(tele_type == '2G'){
		//$("#card_kind_name").attr("style","display:block");
		//$("#card_kind_select").attr("style","display:block");
		$("#card_kind_name").removeClass("none");
		$("#card_kind_select").removeClass("none");
		//加载时卡类型隐藏字段赋值
		var card_kind = $("#card_kind").val();
		$("#card_kind_hidden").val(card_kind);
	}
	$("#card_kind").change(function(){
		var card_kind = $("#card_kind").val();
		$("#card_kind_hidden").val(card_kind);
	});
	/*更新id_type_hidden隐藏标签的值*/
	$("#id_type").change(function(){
		var id_type = $("#id_type").val();
		$("#id_type_hidden").val(id_type);
	});
	
	/**
	 * 取消按钮
	 */
	$("#goBack").click(function(){
		var url = application.fullPath + "authority/order/orderProsessing";
		$.confirm('确认取消吗？',
		'确认提示',
		function cancel_ok() {
			window.location.href = url;
		},
		function cancel_cancel() {
			
		});
	});

	/**
	 * form 校验
	 */
	$("#orderProPaySub").validate({ 
	    rules: { 
			/*实名信息验证，如果是PAD端下的订单，则进行文本框校验
			validateRealNameInfo
			*/
//	    	id_number: {
//	    				required :true,   
//	    				pattern:/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/,
//	    				pattern:/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/
//	    				},
//	    	auth_end_date: "required" ,
//	    	customer_name: "required" ,
//	    	auth_adress:"required",
//			/*现金支票信息验证，进行文本框校验
//			validateChequeInfo
//			*/
//			cheque_number:"required",
//	    	cheque_bank_name: "required" ,
//	    	cheque_agent_name: "required" ,
//	    	cheque_agent_phone:{
//						    		required :true,   
//									pattern:/^(\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
//						       },
//			cheque_agent_id: "required"	,
//			/*转账信息验证，进行文本框校验
//			validateTransferInfo*/
//			transfer_number:"required",
//			transfer_bank_name: "required" ,
//			transfer_agent_name: "required" ,
//			transfer_agent_phone:{
//									required :true,   
//									pattern:/^(\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
//								},
//			transfer_agent_id: "required",	
//			
			/***提交时：费用参数统计*/
			invoice_code:"required"	

	    }
	});
	/**
	 * 国政通验证
	 */
	$("#machine_confirm").click(function(){
		$.alert("无法连续设备");
	});
	/*点击一键校验时进行身份证校验*/
	$("#id_number").change(function(){
		$("#terminal_checked_flag").val("false");
		var regIDCard1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
		var regIDCard2=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
		var id_number = $.trim($("#id_number").val());
		if(id_number == '' || id_number == null){
			$.alert("您输入的证件信息为空，请输入");
			$("#check_cust_flag").val("false");
			$("#id_number").focus();
			return false;
		}else{
			if(!regIDCard1.test(id_number) && !regIDCard2.test(id_number)){
				$.alert("证件号不合法，请重新输入!");
				return false;
			}
		}
		checkCustInfo();
	});
	/*------------------------------------------------------------------------*/
	//终端号校验
	$("#id_read").click(function(){
		var check_cust_flag = $("#check_cust_flag").val();
		if(check_cust_flag == 'false'){
			$.alert("客户资料未校验,请校验!")
			return;
		}
		var resources_code = $.trim($("#id_read_content").val());
		var URL = application.fullPath + "authority/order/CheckTerminal";
		if(id_read_content == null || id_read_content ==''){
			$.alert("终端号为空,请输入!");
			return;
		}
		var data={
			"resources_code":resources_code
		  };
		$.ajax({
			type : "POST",
			async : false,
			url : URL,
			data : data,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			waitMsg:"正在校验，请稍候......",
			success:function(message){
				var checkTermialFlag = message.args.checkTermialFlag;
				if(checkTermialFlag==false||checkTermialFlag=='false'){
					var error_detail = message.args.errorDetail;
					$.error("终端信息校验失败："+error_detail);
					$("#terminal_checked").text("不通过");
					return false;
				}
				var jsonObj=eval(message);
				if(jsonObj.type=='success'){
					var URL = application.fullPath + "authority/order/occupyTerminal";
					var resources_code = $.trim($("#id_read_content").val());
					var order_id = $("#order_id").text();
					var data={
						"resources_code":resources_code,
						"order_id":order_id
				    };
					$.ajax({
						type : "POST",
						async : false,
						url : URL,
						data : data,
						dataType:'json',
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
						success:function(message){
							var occupyTermialFlag = message.args.occupyTermialFlag;
							var addOrderAttrFalg = message.args.addOrderAttrFalg;
							if(occupyTermialFlag==false||occupyTermialFlag=='false'){
								var error_detail = message.args.errorDetail;
								$.error("终端信息预占失败："+error_detail);
								return false;
							}else if(addOrderAttrFalg==false||addOrderAttrFalg=='false'){
								$.error("保存终端号失败,请重新预占！");
								return false;
							}
							$("#terminal_checked").text("通过");
							$("#terminal_checked_flag").val("true");
						}
					});
				}
			}
		});
	});
	//读卡
	$("#read_card").click(function(){
		var URL = application.fullPath + "authority/order/cardVerif";
		//判断是否有终端
		var terminal_seleted = $("#terminal_seleted").val();
		var terminal_checked_flag = $("#terminal_checked_flag").val();
		if(terminal_seleted == "1" && (terminal_checked_flag =="false" || terminal_checked_flag ==false)){
			$.alert("终端信息未校验,请校验!");
			return;
		}
		var styleAttr = $("#info_custAndterminal").attr("style");
		var realNameFlag = true;
		if(styleAttr=="display:block"){
			var check_cust_flag = $("#check_cust_flag").val();		
			if(check_cust_flag == "true"){
				realNameFlag = validateRealNameInfo();
				if(realNameFlag==false || realNameFlag == "false"){
					return;
				}
			}else{
				$.alert("身份验证未通过,请确认证件号!");
				realNameFlag = false;
				return;
			}
		}
		var info_card_number = $("#info_card_number").val();
		if(info_card_number == "" || info_card_number == null){
			$.alert("ICCID卡号为空,请输入!");
			return;
		}
		var data={
			"sim_card_no":info_card_number
	    };
		if(realNameFlag){
			$.ajax({
				type : "POST",
				async : false,
				url : URL,
				data : data,
				dataType:'json',
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				waitMsg:"正在校验，请稍候......",
				success:function(message){
					//填充卡资料信息,读卡后返回的数据隐藏在JSP中,给信息确认调用开户申请接口作参数传
					var sim_card_no = message.args.CardVerifVo.sim_card_no;
					var imsi = message.args.CardVerifVo.imsi;
					var card_type = message.args.CardVerifVo.card_type;
					var rsp_desc = message.args.CardVerifVo.rsp_desc
					var rsp_code = message.args.CardVerifVo.rsp_code
					if(rsp_code == "00000"){
						$("#sim_card_no_hidden").val(sim_card_no);
						$("#imsi_hidden").val(imsi);
						$("#card_type_hidden").val(card_type);
						//将校验通过标志设置为true
						$("#info_card_flag").val("true");
						$.success("卡校验成功："+rsp_desc);
					}else{
						var errorMessage = message.args.errorMessage;
						$("#info_card_flag").val("false");
						$.error("卡校验失败："+errorMessage);
					}
					
				}
			});
		}	
	});
	/*点击确认信息，客户信息 和 费用信息保存*/
	$("#info_confirm").click(function(){
		/*-----------------------------*/
		//判断是否需要读卡
		var tele_type = $("#tele_type").text();
		var card_kind_type = $("#card_kind_type").val();
		if(tele_type =="4G" && card_kind_type == "01"){
			var info_card_flag = $("#info_card_flag").val();
			if(info_card_flag == "false" || info_card_flag == false){
				$.alert("未读卡,请读卡!");
				return;
			}
		}
		//判断是否要填写终端信息,如果不填写终端信息则直接进行下面的校验,如果填写则需要判断下面值是否为true,否则不能进行信息确认!
		var terminal_seleted = $("#terminal_seleted").val();
		var terminal_checked_flag = $("#terminal_checked_flag").val();
		if(terminal_seleted == "1" && (terminal_checked_flag =="false" || terminal_checked_flag ==false)){
			$.alert("终端信息未校验,请校验!");
			return false;
		}
		var styleAttr = $("#info_custAndterminal").attr("style");
		var realNameFlag = true;
		if(styleAttr=="display:block"){
			var check_cust_flag = $("#check_cust_flag").val();		
			if(check_cust_flag == "true"){
				realNameFlag = validateRealNameInfo();
			}else{
				$.alert("身份验证未通过,请确认证件号!");
				realNameFlag = false;
				return;
			}
		}
		if(realNameFlag){
			var URL = application.fullPath + "authority/order/saveCustAndFeeInfo";
			var tele_type = $("#tele_type").text();
			var id_type_hidden = $("#id_type_hidden").val();
			var id_number = $("#id_number").val();
			var auth_end_date = $("#auth_end_date").val();
			var customer_name = $("#customer_name").val();
			var customer_id = $("#customer_id").val();
			var auth_adress = $("#auth_adress").val();
			var contact_adress = $("#contact_adress").val();
			var cust_sex = $("#cust_sex").val();
			var born_date = $("#born_date").val();
			var cust_post = $("#cust_post").val();
			var cust_email = $("#cust_email").val();
			var contact_name = $("#contact_name").val();
			var contact_phone = $("#contact_phone").val();
			var remark_desc = $("#remark_desc").val();
			var order_id = $("#order_id").text();
			var sim_card_no = $("#sim_card_no_hidden").val();
			var imsi = $("#imsi_hidden").val();
			var card_type = $("#card_type_hidden").val();
			$.ajax({
				type : "POST",
				async : false,  //同步请求
				url : URL,
				data : {
					"tele_type":tele_type,
					"id_type":id_type_hidden,
					"id_number":id_number,
					"auth_end_date":auth_end_date,
					"customer_name":customer_name,
					"customer_id":customer_id,
					"auth_adress":auth_adress,
					"contact_adress":contact_adress,
					"cust_sex":cust_sex,
					"born_date":born_date,
					"cust_post":cust_post,
					"cust_email":cust_email,
					"contact_name":contact_name,
					"contact_phone":contact_phone,
					"remark_desc":remark_desc,
					"order_id":order_id,
					"sim_card_no":sim_card_no,
					"imsi":imsi,
					"card_type":card_type
				},
				dataType:'json',
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				waitMsg:"正在确认信息，请稍候......",
				success:function(message){
					var saveFlag=message.args.save_flag;
					if(saveFlag==false||saveFlag=='false'){
						var error_detail = message.args.error_detail;
						$.error("确认信息失败："+error_detail);
						return false;
					}
					var tele_type = $("#tele_type").text();
					if(tele_type=="3G" || tele_type=="4G"){
						var feeList = message.args.feeList;
						var feeVo = message.args.feeVo;
						var feeNum = message.args.feeVo.fee_num;
						var infoOrderBaseMessVo = message.args.infoOrderBaseMessVo;
						refresh(feeList,feeVo,infoOrderBaseMessVo);
						$("#discount_totalCosts").text("总计："+feeNum);
					}
					$("#info_confirm_flag").val("true");
					$("#real_name").text("已认证");
					//修改基本信息的内容
					var id_type_name = $("#id_type").find("option:selected").text();
					$("#customer_name_baseInfo").text(customer_name);
					$("#id_number_baseInfo").text(id_number);
					$("#id_type_baseInfo").text(id_type_name);
					$.alert("success");
				},
				error: function() {
					$("#info_confirm_flag").val("false");
					$("#real_name").text("未认证");
					$.error("确认信息失败!");
		        }
			});
		}
	});
	//初始化页面移除确定按钮的点击事件
	$('#queding').removeAttr("onclick");
	//免填单按钮点击事件
	$('#miantiandan').click(function(){
		var real_name = $("#real_name").text();
		if(real_name=="已认证"){
			order_id = $('#order_id').text();
			infoPrint();
		}else {
			$.alert("信息未确认,请一键确认信息!");
		}
	})
});

function refresh(feeList,feeVo,infoOrderBaseMessVo){
	$("#table_feeList").empty();
	$("#order_info").empty();
	var html = "";
	for(var i=0;i<feeList.length;i++){
		orderFee = feeList[i];
		html +='<tr>'
			+'		<td width="178" height="32">'+orderFee.fee_name+'</td>'
			+'		<td id="old_fee_'+i+'" width="136">'+parseFloat(orderFee.orig_fee).toFixed(2)+'</td>'
			+'		<td id="new_fee_'+i+'" width="136">'+parseFloat(orderFee.orig_fee).toFixed(2)+'</td>'
			+'		<td width="136"><input name="discountList['+i+'].discount_fee" key="'+i+'" id="discount_fee_'+i+'" onblur="countFee(this)" onfocus="cleanDefaultValue(this)"  type="text" value="0"/></td>'
			+'		<td  width="172"><input name="discountList['+i+'].discount_reason" id="discount_reason_'+i+'" type="text" value=""/></td>'
			+'</tr>'
			+'<tr style="display:none">'
			+'		<td><input name="discountList['+i+'].fee_detail_id" width="172"value="'+orderFee.fee_detail_id+'"/></td>'
			+'		<td><input name="discountList['+i+'].order_id" width="172"value="'+infoOrderBaseMessVo.order_id+'"/></td>'
			+'		<td><input name="discountList['+i+'].discount_type" width="172" value="400"/></td>'
			+'		<td><input name="discountList['+i+'].fee_id" width="172" value="'+orderFee.fee_id+'"/></td>'
			+'		<td><input name="discountList['+i+'].fee_id_type" width="172" value="'+orderFee.fee_id_type+'"/></td>'
			+'		<td><input name="discountList['+i+'].fee_name" value="'+orderFee.fee_name+'"/></td>'
			+'		<td><input name="discountList['+i+'].orig_fee" value="'+parseFloat(orderFee.orig_fee).toFixed(2)+'"/></td>'
			+'		<td><input id="new_fee_none_'+i+'" name="discountList['+i+'].real_fee" value="'+parseFloat(orderFee.orig_fee).toFixed(2)+'"/></td>'
			+'</tr>';
	}
	$("#table_feeList").html(html);
	$("#order_info").append(
		'<tr style="display:none">'
		+'<td><input name="payed_fee" id="discount_totalCosts_input" value="'+feeVo.fee_num+'"/></td>'
		+'<td><input name="total_fee" id="total_fee" value="'+feeVo.fee_num+'"></input></td>'
		+'<td><input name="discount_fee" id="discount_fee" value="0"></input></td>'
		+'<td><input name="order_id"  value="'+infoOrderBaseMessVo.order_id+'"></input></td>'
		+'<td><input name="order_source" id="order_source" value="'+infoOrderBaseMessVo.order_source+'"></input></td>'
		+'<td><input name="invoice_flag"  value="0"></input></td>'
		+'</tr>'
	);
}

function checkCustInfo(){
	var id_type = $("#id_type").val()
	var id_number = $("#id_number").val();
	var order_id = $("#order_id").text();
	var tele_type = $("#tele_type").text();
	var URL = application.fullPath + "authority/order/checkCustInfo";
	$.ajax({
		type : "POST",
		async : false,  //同步请求
		url : URL,
		data : {
			"id_number":id_number,
			"id_type":id_type,
			"order_id":order_id,
			"tele_type":tele_type
		},
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		waitMsg:"正在验证，请稍候......",
		/*校验成功，自动填充证件地址信息*/
		success:function(message){
			var returnData=message.args.returnData;
			var tele=message.args.teleType;
			if(returnData=="老客户"){
				var address = "";
				var authEndTime = "";
				var customerId="";
				var customerName = "";
				var contact_addr = "";
				var contact_person = "";
				var contact_phone = "";
				var cust_sex = "";
				var customer_birth = "";
				var customer_zip = "";
				var customer_email = "";
				var arrearage_flag = "";
				var blackListFlag="";
				var maxUserFlag="";
				var id_number = $("#id_number").val();
				if("2G"==tele){
					customerName=message.args.cust_info.customerName;
					authEndTime=message.args.cust_info.certExpireDate;
					address=message.args.cust_info.certAddr;//CertAddr
					cust_sex=message.args.cust_info.customerSex;
					customer_birth=message.args.cust_info.customerBirth;
					customerId=message.args.cust_info.customerID;
					contact_person=message.args.cust_info.contactPerson;
					contact_phone=message.args.cust_info.contactPhone;
					contact_addr=message.args.cust_info.contactAddr;
					arrearage_flag=message.args.cust_info.arrearageFlag;
					blackListFlag=message.args.cust_info.blackListFlag;
					maxUserFlag=message.args.cust_info.maxUserFlag;
				}else{
					address = message.args.cust_info.certAdress;
					authEndTime = message.args.cust_info.certExpireDate;
					customerName = message.args.cust_info.customerName;
					contact_addr = message.args.cust_info.contactAddr;
					contact_person = message.args.cust_info.contactPerson;
					contact_phone = message.args.cust_info.contactPhone;
					customer_birth = id_number.substring(6, 10)+"-"+id_number.substring(10,12)+"-"+id_number.substring(12,14);
					cust_sex = message.args.cust_info.customerSex;
					arrearage_flag=message.args.cust_info.arrearageFlag;
				}
				if("1"==blackListFlag){//黑名单客户
					$.error("黑名单客户!");
					return;
				}else if("1"==maxUserFlag){//已达到最大用户数
					$.error("已达到最大用户数!");
					return;
				}else if("1"==arrearage_flag){//有欠费信息
					$.error("有欠费信息!");
					return;
				}else{
					if(customerName != null){
						$("#customer_name").val(customerName);
						$("#customer_name").attr("readonly","true");
					}
					if(customerId != null){
						$("#customer_id").val(customerId);
					}
					if(authEndTime != null){
						$("#auth_end_date").val(authEndTime);
						$("#auth_end_date").attr("readonly","true");
					}
					if(address != null){
						$("#auth_adress").val(address);
						$("#auth_adress").attr("readonly","true");
					}
					if(contact_addr != null){
						$("#contact_adress").val(contact_addr);
						$("#contact_adress").attr("readonly","true");
					}
					if(cust_sex != null){
						$("#cust_sex").val(cust_sex);//..........
						$("#cust_sex").attr("readonly","true");
					}else{
						var sexNumber = id_number.substring(16,17);
						if((sexNumber%2)==0){
							cust_sex = '0'
						}else{
							cust_sex = '1'
						}
						$("#cust_sex").val(cust_sex);//..........
						$("#cust_sex").attr("readonly","true");
					}
					if(customer_birth != null){
						$("#born_date").val(customer_birth);
						$("#born_date").attr("readonly","true");
					}
					if(contact_person != null){
						$("#contact_name").val(contact_person);
						$("#contact_name").attr("readonly","true");
					}
					if(contact_phone != null){
						$("#contact_phone").val(contact_phone);
						$("#contact_phone").attr("readonly","true");
					}
					var id_number = $("#id_number").val();
					$("#check_cust_flag").val("true");
					$.success(""+returnData+"");
				}
			}else if(returnData=="新客户"||returnData=="此前无此客户信息"||returnData=="无此客户信息"){
				$("#customer_name").removeAttr("readonly");
				$("#auth_end_date").removeAttr("readonly");
				$("#auth_adress").removeAttr("readonly");
				$("#contact_adress").removeAttr("readonly");
				$("#cust_sex").removeAttr("readonly");
				$("#born_date").removeAttr("readonly");
				var id_number = $("#id_number").val();
				var bornDate = id_number.substring(6, 10)+"-"+id_number.substring(10,12)+"-"+id_number.substring(12,14);
				$("#born_date").val(bornDate);
				$("#cust_post").removeAttr("readonly");
				$("#cust_email").removeAttr("readonly");
				$("#contact_name").removeAttr("readonly");
				$("#contact_phone").removeAttr("readonly");
				$("#auth_end_date").val("");
				$("#customer_name").val("");
				$("#auth_adress").val("");
				$("#contact_adress").val("");
				var sexNumber = id_number.substring(16,17);
				if((sexNumber%2)==0){
					cust_sex = '0'
				}else{
					cust_sex = '1'
				}
				$("#cust_sex").val(cust_sex);//..........
				$("#cust_post").val("");
				$("#cust_email").val("");
				$("#contact_name").val("");
				$("#contact_phone").val("");
				$("#remark_desc").val("");
				$("#check_cust_flag").val("true");
				$.success("新客户，请手动录入客户信息！");
			}else{
				$("#check_cust_flag").val("false");
				$.error(returnData);
			}
		},
		/*校验失败，清空证件号码框，并得到光标*/
		error: function() {
			$("#check_cust_flag").val("false");
			$("#id_number").val('');
			var returnData="校验失败，请稍后重试！";
			$.error(returnData);
			$("#id_number").focus();
        }
	});
}
var phone = /^(\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;
var regIDCard1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
var regIDCard2=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
var email=/^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$/;
var notAllNumber =/[^x00-xff]|[a-zA-Z]/;
/*实名信息验证，如果是PAD端下的订单，则进行文本框校验*/
function validateRealNameInfo(){
	var id_number = $.trim($("#id_number").val());
	var auth_end_date = $("#auth_end_date").val();
	var customer_name = $("#customer_name").val();
	var auth_adress = $("#auth_adress").val();
	var contact_adress = $("#contact_adress").val();
	var cust_sex = $("#cust_sex").val();//..........
	var born_date = $("#born_date").val();
	var cust_post = $("#cust_post").val();
	var cust_email = $("#cust_email").val();
	var contact_name = $("#contact_name").val();
	var contact_phone = $("#contact_phone").val();

	if(null == id_number || id_number == ''){
		$.alert("请输入证件号!");
		return false;
	}else{
		if(!regIDCard1.test(id_number) && !regIDCard2.test(id_number)){
			$.alert("证件号不合法，请重新输入!");
			return false;
		}
	}
	if(null == auth_end_date || auth_end_date == ''){
		$.alert("请输入证件到期日期!");
		return false;
	}
	if(null == customer_name || customer_name == ''){
		$.alert("请输入客户名称!");
		return false;
	}else{
		if(!notAllNumber.test(customer_name)){
			$.alert("客户名称不合法，请重新输入!");
			return false;
		}
	}
	if(null == cust_sex || cust_sex == ''){
		$.alert("请选择客户性别!");
		return false;
	}
	if(null == auth_adress || auth_adress == ''){
		$.alert("请输入证件地址!");
		return false;
	}
	if(null == born_date || born_date == ''){
		$.alert("请填写出生日期!");
		return false;
	}
	if(null == contact_adress || contact_adress == ''){
		$.alert("请输入联系人地址!");
		return false;
	}
	if(null == contact_name || contact_name == ''){
		$.alert("请输入联系人名称!");
		return false;
	}else{
		if(!notAllNumber.test(contact_name)){
			$.alert("联系人名称不合法，请重新输入!");
			return false;
		}
	}
	if(null == contact_phone || contact_phone == ''){
		$.alert("请输入联系人电话!");
		return false;
	}else{
		if(!phone.test(contact_phone) && !phone.test(contact_phone)){
			$.alert("联系人电话不对，请重新输入!");
			return false;
		}
	}
	return true;
}

/*现金支票信息验证，进行文本框校验*/
function validateChequeInfo(){
	var cheque_number = $("#cheque_number").val();
	var cheque_bank_name = $("#cheque_bank_name").val();
	var cheque_agent_name = $("#cheque_agent_name").val();
	var cheque_agent_phone = $("#cheque_agent_phone").val();
	var cheque_agent_id = $("#cheque_agent_id").val();
	if(null == cheque_number || cheque_number == ''){
		$.alert("支票号码为空，请重新输入!");
		return false;
	}
	if(null == cheque_bank_name || cheque_bank_name == ''){
		$.alert("银行名称为空，请重新输入!");
		return false;
	}
	if(null == cheque_agent_name || cheque_agent_name == ''){
		$.alert("经办人姓名为空，请重新输入!");
		return false;
	}
	if(null == cheque_agent_phone || cheque_agent_phone == ''){
		$.alert("经办人电话为空，请重新输入!");
		return false;
	}else{
		if(!phone.test(cheque_agent_phone)){
			$.alert("您输入的电话不正确，请重新输入!");
			return false;
		}
	}
	if(null == cheque_agent_id || cheque_agent_id == ''){
		$.alert("经办人证件为空，请重新输入!");
		return false;
	}
}

/*转账信息验证，进行文本框校验*/
function validateTransferInfo(){
	
	var transfer_number = $("#transfer_number").val();
	var transfer_bank_name = $("#transfer_bank_name").val();
	var transfer_agent_name = $("#transfer_agent_name").val();
	var transfer_agent_phone = $("#transfer_agent_phone").val();
	var transfer_agent_id = $("#transfer_agent_id").val();
	
	if(null == transfer_number || transfer_number == ''){
		$.alert("转账凭证号为空，请重新输入!");
		return false;
	}
	if(null == transfer_bank_name || transfer_bank_name == ''){
		$.alert("银行名称为空，请重新输入!");
		return false;
	}
	if(null == cheque_agent_name || cheque_agent_name == ''){
		$.alert("经办人姓名为空，请重新输入!");
	}
	if(null == transfer_agent_phone || transfer_agent_phone == ''){
		$.alert("经办人电话为空，请重新输入!");
	}else{
		if(!phone.test(transfer_agent_phone)){
			$.alert("您输入的电话不正确，请重新输入!");
			return false;
		}
	}
	if(null == transfer_agent_id || transfer_agent_id == ''){
		$.alert("经办人证件为空，请重新输入!");
		return false;
	}
}

function cleanDefaultValue(obj){
	var id= $(obj).attr("id");
	if($("#"+id).val() == 0){
		$("#"+id).val('');
	}
}
/**
*减免费用计算
*/
function countFee(obj){
	console.log(obj);
	var id= $(obj).attr("id");
	if($("#"+id).val()==null||$("#"+id).val()==''){
		$("#"+id).val('0')
		return false;
	}
	//当前减免费用  如果新的减免费用和原来的减免费用一致，不处理
	var cur_dis_fee = parseFloat($(obj).attr("value"));
	//新的减免费用
	var new_dis_fee = $("#"+id).val();
	var reg = /^\d+(\.\d{1,2})?$/;
	if(!reg.test(new_dis_fee)){  
		$("#"+id).val(cur_dis_fee);
		$("#"+id).focus();
		$.alert("请输入最多两位小数的数字!");  
		return false;
    }  
	var current_dis_fee = '';
	if(isNaN(cur_dis_fee)){
		current_dis_fee=0;
	}else{
		current_dis_fee = cur_dis_fee;
	}
	
	if(new_dis_fee==current_dis_fee){
		return false;
	}
	
	//
	var order_attr_id = $(obj).attr("key");
	//新费用Id
	var new_fee_id="new_fee_"+order_attr_id;
	var new_fee_none_id="new_fee_none_"+order_attr_id;
	
	//原来费用Id
	var old_fee_id="old_fee_"+order_attr_id;
	
	//单个总费用
	var old_fee =  parseFloat($("#"+old_fee_id).text());
	
	if(isNaN(old_fee)){
		return false;
	}
	if(new_dis_fee>old_fee){
		$("#"+id).val(current_dis_fee);
		
		$.alert("减免费用不能大于本总费用！");
		return false;
	}
	//单个优惠后的费用
	var start_new_fee =  $("#"+new_fee_id).text();
	if(start_new_fee==null||start_new_fee==''){
		return false;
	}
	//优惠后的总费用
	var new_fee =old_fee-new_dis_fee;
	
	var discount_totalCosts = $("#discount_totalCosts_input").val();
	if(new_fee<start_new_fee){
		discount_totalCosts = discount_totalCosts-(start_new_fee-new_fee);
	}else if(new_fee>start_new_fee){
		discount_totalCosts= parseFloat(discount_totalCosts) + parseFloat(new_fee-start_new_fee);
	}
	
	$("#"+new_fee_id).text((parseFloat(new_fee)).toFixed(2));
	$("#"+new_fee_none_id).attr("value",(parseFloat(new_fee)).toFixed(2));
	//把最新的减免费用保存
	$("#"+id).attr("value",(parseFloat(new_dis_fee)).toFixed(2));
	
	//计算总费用
	$("#discount_totalCosts_input").attr("value",(parseFloat(discount_totalCosts)).toFixed(2));
	$("#discount_totalCosts").html("总计："+(parseFloat(discount_totalCosts)).toFixed(2));
	
	//总减免金额 discount_fee
	$("#discount_fee").attr("value",(parseFloat($("#total_fee").val()-discount_totalCosts)).toFixed(2));
}
/* modified 更改校验方式：*/

/**
*提交时：费用参数统计
*/
function orderSub(){
	var reaDisplayValue = $("#real_name_authentication").css("display");
	var ciDisplayValue = $("#chequeInfo").css("display");
	var tiDisplayValue = $("#transferInfo").css("display");
//	var check_cust_flag = $("#check_cust_flag").val();
//	var info_confirm_flag = $("#info_confirm_flag").val();
//	if(check_cust_flag=="true" && info_confirm_flag == "true"){
//		$("#orderProPaySub").submit();
//	}else if(check_cust_flag=="false"){
//		$.alert("身份验证未通过,请一键验证身份!");
//	}else if(check_cust_flag=="true" && info_confirm_flag == "false"){
//		$.alert("信息未确认,请一键确认信息!");
//	}
	
	$("#orderProPaySub").submit();
	
	
}
function infoPrint(){
	//改变按钮样式
	$('#queding').removeClass('ok');
	$('#queding').attr('class','free');
	$('#miantiandan').removeClass('free');
	$('#miantiandan').attr('class','ok');
	$('#miantiandan').removeAttr("onclick"); //移除免填单点击功能
	$('#queding').click(function(){
		orderSub();
	})
	window.open(application.fullPath+"authority/wkprint/openAccWkprint?order_id="+order_id+"&pay_type="+pay_type,"window","toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=650,height=450,left=120,top=100");
}
