$(document).ready(function(){
	
	var teleType = $("#tele_type").val();//电信类型
	
	if(teleType=="3G" || teleType=="4G"){
		$("#first_month_fee").show();
		$("#first_month_fee_title").show();
	//	$("#card_kind_title").show();
		$("#card_kind").show();
	}else if(teleType=="2G"){
		$("#first_month_fee").hide();
		$("#first_month_fee_title").hide();
	//	$("#card_kind_title").hide();
		$("#card_kind").hide();
	}
	/*
	var billSend = $("#bill_send").val();
	alert(billSend);
	
	if(billSend=="1"){
		$("#logistics_type option:first").prop("selected", "selected");
		$("#send_content").attr("disabled","false");
	}else if(billSend=="2"){
		$("#logistics_type").val("");
		$("#send_content").val("");
		}
*/
	$("#bill_send").change(function(){
		var billSend = $("#bill_send").val();
		//alert(billSend);
		
		if(billSend=="1"){
			$("#logistics_type").show();
			$("#send_content").show();
			$("#logistics_type_text").show();
			$("#send_content_text").show();
			$("#logistics_type option:first").prop("selected", "selected");
			$("#send_content option:first").prop("selected", "selected");
		}else if(billSend=="2"){
			$("#logistics_type").hide();
			$("#send_content").hide();
			$("#logistics_type_text").hide();
			$("#send_content_text").hide();
		}
	})
	/**
	 * 国政通验证
	 */
	$("#machine_confirm").click(function(){
		$.alert("无法连续设备");
	});
	
	/**
	 * 订单取消(暂时取消，order_cancel改为ordercancel)
	 */
	$("#order_cancel").click(function(){
		$.confirm('是否确定取消订单','取消订单',cancel_ok,onCancel);
	});
	function onSubmit(){
		var URL = application.fullPath + 'authority/sale/cancelOrder';
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			waitMsg:"正在取消订单！",
			success:function(message){
//			$.alert("111"+message.args.nextUrl);
			window.location.href= application.fullPath + message.args.nextUrl;
		}
		})
	}
	
	function cancel_ok() {
		var cancelurl = application.fullPath + "authority/index/saleIndex";
		window.location.href = cancelurl;
	}
	function onCancel(){
		
	}
	
	
	$("#form_check").validate({
	    rules: {
		    	id_number:{
		    		required:true,
		    		pattern:/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}[(0-9)|(A-z)]$/
		    	},
		    	customer_name: {
		    		required:true,
		    		pattern:/[^x00-xff]|[a-zA-Z]/
		    	},
		    	cust_sex: "required",
		    	devolop_name: "required",
		    	devolop_post: "required",
		    	auth_end_date:"required",
		    	devolop_channel_id: "required",
		    	devolop_channel_name: "required",
		    	devolop_phone: "required",
		    	handler_name: {
		    		pattern:/[^x00-xff]|[a-zA-Z]/
		    	},
		    	handler_id_number: {
		    		pattern:/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}[(0-9)|(A-z)]$/
		    	},
		    	handler_contact_address: {
		    		pattern:/[^x00-xff]|[a-zA-Z]/
		    	},
		    	credit_first: "required",
		    	contactPerson: {
		    		required:true,
		    		pattern:/[^x00-xff]|[a-zA-Z]/
		    	},
		    	contactPhone:{ 
		    		required:true,
		    		pattern:/((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/
		    		
		    	},
		    	contactAddress: {
		    		required:true,
		    		pattern:/[^x00-xff]|[a-zA-Z]/
		    	},
		    	//resource_code:"required",//  ^(?!\d*$)
		    	auth_adress:{
		    		required:true,
		    		pattern:/[^x00-xff]|[a-zA-Z]/
		    	}
			}
	});
	//新客户 解析身份证号，性别
	function sexByIdCard(idCard){  
//		console.log(idCard);
	    idCard = $.trim(idCard.replace(/ /g, ""));// 对身份证号码做处理。包括字符间有空格。  
	    if(idCard.length==15){  
	        if(idCard.substring(14,15)%2==0){  
	            return '0';  
	        }else{  
	            return '1';  
	        }  
	    }else if(idCard.length ==18){  
	        if(idCard.substring(16,17)%2==0){  
	            return '0';  
	        }else{  
	            return '1';  
	        }  
	    }else{  
	        return null;  
	    }   
	}
	//$(".developTable input[type=radio] ").first().attr("checked",true); 
	//默认选中操作员的发展人
	$(".developTable td[name=dev_post]").each(function(){
//		alert("devCode : " + $("#devCode").val() );
//		alert( $(this).text() );
		if( $.trim($("#devCode").val()) == $.trim( $(this).text() ) ){
			$(this).prev().children().first().attr("checked",true); 
		}else{
			$(".developTable input[type=radio] ").first().attr("checked",true); 
		}
	});
	
	//获得选中的发展人信息
	function getDeveloper(){
		$(".developTable input[type=radio]").each(function(){
			 if(this.checked){
				 $(this).parent().nextAll().each(function(){
					// alert("name:"+$(this).attr("name"));
	 			 		if($(this).attr("name") == "dev_name"){
	 			 			$("#devolop_name").val( $.trim( $(this).text() ));
	 			 		}
	 			 		if($(this).attr("name") == "dev_post"){
	 			 			$("#devolop_post").val($.trim($(this).text()));
	 			 		}
	 			 		if($(this).attr("name") == "dev_phone"){
	 			 			$("#devolop_phone").val($.trim($(this).text()));
	 			 		}
	 			 		if($(this).attr("name") == "dev_channel_id"){
	 			 			$("#devolop_channel_id").val($.trim($(this).text()));
	 			 		}
	 			 		if($(this).attr("name") == "dev_channel_name"){
	 			 			$("#devolop_channel_name").val($.trim($(this).text()));
	 			 		}
	 			 });
				 
			 }
		}); 
	}
	
	
	
	//订单提交按钮点击事件,只在进入页面并未进行客户资料校验时有效;在进行客户信息校验时绑定新的事件函数
	$("#order_submit").bind("click",function(){
		$.error("请先进行客户信息校验！");
		return;
	});
	//客户页面发展人查询按钮点击事件,只在进入页面并未进行客户资料校验时有效;在进行客户信息校验时绑定新的事件函数
	$("#to_developer_qry").bind("click",function(){
		$.error("请先进行客户信息校验！");
		return;
	});
	/**
	 * 身份证号码校验
	 */
	$("#id_number").change(function(){
		
		//解绑客户页面订单提交按钮的click事件
		$("#order_submit").unbind("click");
		$("#order_submit").unbind("dbclick");
		$("#order_submit").removeAttr("disabled");
		//解绑客户页面发展人查询按钮点击事件
		$("#to_developer_qry").unbind("click");
		
		var teleType = $("#tele_type").val();
		
		//页面发展人查询按钮点击时执行的函数
		var to_developer_qryFun=function(){
			//$.alert("to_developer_qry");
			
			//点击客户页发展人查询按钮时进行异步请求的函数
			var onShowFun=function(){
				$("tr[name='tr_dev_info']").remove();
				$.ajax({
					url : "devQry",
					data:{
						"developerName":$("#dev_name_qry").val(),
						"developerId":$("#dev_code_qry").val(),
						"developerNumber":$("#dev_phone_qry").val()
					},
					dataType:'json',
					type : 'post',
					waitMsg : "正在查询！",
					success : function(message) {
						var code=message.args.code;
						var detail=message.args.detail;
						if("200"==code){
							$("#dev_info_head").after(detail);
							$(".developTable td[name=dev_post]").each(function(){
								if( $.trim($("#devCode").val()) == $.trim( $(this).text() ) ){
									$(this).prev().children().first().attr("checked",true); 
								}else{
									$(".developTable input[type=radio] ").first().attr("checked",true); 
								}
							});
						}else{
							$("#dev_info_head").after(detail);
						}
					},
					error : function() {
						$("#dev_info_head").after("<tr name='tr_dev_info'> <td width='24' height='32' align='right' bgcolor='#FBFBFB'></td><td width='110' align='left' bgcolor='#FBFBFB'  colspan='5'>操作失败，请重试！</td></tr>");
					}
				});
			};
			
			//点击发展人查询弹窗内的查询按钮、异步请求的函数
			var btn_dev_qry_clickFun=onShowFun;
			
			//发展人查询弹窗，弹出后进行弹窗内的按钮点击事件绑定
			$.dialog({
					top:20,
					width:800,
					title:'发展人查询',//提示title
					content:'<div style="width:800px; margin:0 auto;" id="box_dev_qry">		<h2 class="title margin_top">发展人信息查询</h2>	  <div class="box">	    <table width="800" border="0" align="center" cellpadding="0" cellspacing="0" class="custom">				  <tr>					<td width="92" height="32" align="right"><span class="star"></span> 发展人名称：</td>					<td width="175"><input type="text" name="textfield3" id="dev_name_qry" class="id" /></td>					<td width="92" align="right">发展人编码：</td>					<td width="173"><input type="text" name="textfield5" class="id" id="dev_code_qry" /></td>					<td width="103" align="right"><span class="star"></span>手机号码：</td>					<td width="165"><input type="text" name="textfield6" id="dev_phone_qry" class="id" /></td>				  </tr>				  <tr>				    <td height="40" colspan="6" align="center" valign="bottom"><a href="#" class="id_read" id="btn_dev_qry">查询</a> <a href="#" class="id_read" id="btn_dev_qry_clean">重置</a></td>	      </tr>	    </table>      </div>		<h2 class="title margin_top">发展人信息</h2>	  <div class="box box_none">		<table width="800" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#e7e7e7" class="developTable">              <tr id="dev_info_head">                <td height="32" colspan="2" align="center" bgcolor="#F5F4F4"><strong>　　发展人编码</strong></td>                <td width="207" align="center" bgcolor="#F5F4F4"><strong>发展人名称</strong></td>                <td width="124" align="center" bgcolor="#F5F4F4"><strong>发展人手机号</strong></td>                <td width="119" align="center" bgcolor="#F5F4F4"><strong>发展渠道编码</strong></td>                <td width="209" align="center" bgcolor="#F5F4F4"><strong>发展渠道名称</strong></td>              </tr>   </table>		      </div>		<div class="open_submit"><a href="#" class="ok" id="btn_dev_qry_ok">确 定</a><a href="#" class="cancel" id="btn_dev_qry_cancel">取 消</a></div>		</div>',//$("#box_dev_qry").html(),//'发展人查询',//显示的内容，支持html格式。
					onShow:onShowFun
				});
			//btn_dev_qry//发展人查询弹窗内查询按钮绑定点击事件
			$("#btn_dev_qry").bind("click",btn_dev_qry_clickFun);
			//btn_dev_qry_clean//发展人查询弹窗内重置按钮绑定点击事件
			$("#btn_dev_qry_clean").bind("click",function(){
				$("#dev_name_qry").val("");
				$("#dev_code_qry").val("");
				$("#dev_phone_qry").val("");
			});
			//btn_dev_qry_ok//发展人查询弹窗内确定按钮绑定点击事件、将选中结果回显到客户页、移除发展人查询弹窗
			$("#btn_dev_qry_ok").bind("click",function(){
				getDeveloper();
				$(".xxDialog").remove();
				$(".dialogOverlay").remove();
			});
			//btn_dev_qry_cancel//发展人查询弹窗内取消按钮绑定点击事件
			$("#btn_dev_qry_cancel").bind("click",function(){
				$(".xxDialog").remove();
				$(".dialogOverlay").remove();
			});
		};
		
		//客户页面发展人查询按钮点击事件
		$("#to_developer_qry").click(to_developer_qryFun);
		
		function pass(code) { 
				
				//input=top.document.getElementsByName("cardid");
				//var code = input[0].value;
				
				//alert("code="+code);

			    var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
			    var tip = "";
			    var pass= true;
			    //!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)
			    if (!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)) {
			        tip = "身份证号格式错误";
			        pass = false;
			    }
			    
			   else if(!city[code.substr(0,2)]){
			        tip = "地址编码错误";
			        pass = false;
			    }
			    else{
			        //18位身份证需要验证最后一位校验位
			        if(code.length == 18){
			            code = code.split('');
			            //∑(ai×Wi)(mod 11)
			            //加权因子
			            var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
			            //校验位
			            var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
			            var sum = 0;
			            var ai = 0;
			            var wi = 0;
			            for (var i = 0; i < 17; i++)
			            {
			                ai = code[i];
			                wi = factor[i];
			                sum += ai * wi;
			            }
			            var last = parity[sum % 11];
			            if(parity[sum % 11] != code[17]){
			                tip = "校验位错误";
			                pass =false;
			            }
			        }
			    }
			    //if(!pass) {alert(tip);}
			    //else{alert("身份证可用");};
			    return pass;
			}
		
		////订单提交按钮,进行客户资料校验时绑定
		var order_submit_clickFun=function(){
			var id_number=$("#id_number").val().replace(/ /g,"");
			if(false==pass(id_number)){
				$("#id_number").focus();
				$.error("证件号码有误，请检查！");
				return;
			}
			var auth_end_date=$("#auth_end_date").val().replace(/ /g,"");
			if(""==auth_end_date){
				$("#auth_end_date").focus();
				$.error("证件到期时间有误，请检查！");
				return;
			}
			var auth_adress=$("#auth_adress").val().replace(/ /g,"");
			if(""==auth_adress||null==auth_adress.match(/[^x00-xff]|[a-zA-Z]/)){
				$("#auth_adress").focus();
				$.error("证件地址有误，请检查！");
				return;
			}
			var customer_name=$("#customer_name").val().replace(/ /g,"");
			if(""==customer_name||null==customer_name.match(/[^x00-xff]|[a-zA-Z]/)){
				$("#customer_name").focus();
				$.error("客户名请检查，请检查！");
				return;
			}
			var contactPerson=$("#contactPerson").val().replace(/ /g,"");
			if(""==contactPerson||null==contactPerson.match(/[^x00-xff]|[a-zA-Z]/)){
				$("#contactPerson").focus();
				$.error("联系人姓名有误，请检查！");
				return;
			}
			var contactPhone=$("#contactPhone").val().replace(/ /g,"");
			if(""==contactPhone||null==contactPhone.match(/((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/)){
				$("#contactPhone").focus();
				$.error("联系人电话有误，请检查！");
				return;
			}
			var contactAddress=$("#contactAddress").val().replace(/ /g,"");
			if(""==contactAddress||null==contactAddress.match(/[^x00-xff]|[a-zA-Z]/)){
				$("#contactAddress").focus();
				$.error("联系人地址有误，请检查！");
				return;
			}
			
			var devolop_name=$("#devolop_name").val().replace(/ /g,"");
			var devolop_post=$("#devolop_post").val().replace(/ /g,"");
			var devolop_phone=$("#devolop_phone").val().replace(/ /g,"");
			var devolop_channel_id=$("#devolop_channel_id").val().replace(/ /g,"");
			var devolop_channel_name=$("#devolop_channel_name").val().replace(/ /g,"");
			if(""==devolop_name||""==devolop_post||""==devolop_phone||null==devolop_phone.match(/\d{11}/)||""==devolop_channel_id||""==devolop_channel_name){
				$.error("发展人信息有误，请选择发展人！",function(){
					to_developer_qryFun.apply();
				});
				return;
			}
			
			var handler_name=$("#handler_name").val().replace(/ /g,"");
			if(""!=handler_name&&null==handler_name.match(/[^x00-xff]|[a-zA-Z]/)){
				$("#handler_name").focus();
				$.error("经办人姓名有误，请检查！");
				return;
			}
			var handler_id_number=$("#handler_id_number").val().replace(/ /g,"");
			if(""!=handler_id_number&&null==handler_id_number.match(/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}[(0-9)|(A-z)]$/)){
				$("#handler_id_number").focus();
				$.error("经办人证件号码有误，请检查！");
				return;
			}
			var handler_contact_address=$("#handler_contact_address").val().replace(/ /g,"");
			if(""!=handler_contact_address&&null==handler_contact_address.match(/[^x00-xff]|[a-zA-Z]/)){
				$("#handler_contact_address").focus();
				$.error("经办人地址有误，请检查！");
				return;
			}
			//校验是否选择卡类型
//			var ck=false;
//			if("4G"==teleType || "3G"==teleType){
//				$("input[name='radio_card']").each(function(){
//					if(this.checked){
//						ck=true;
//					}
////					if(this.checked && this.val() == "01"){
////						
////					}
//				});
//					 
//				if(!ck){
//					$.error("3/4G开户，请选择卡类型！");
//					return;
//				}
//			}
			var okBtnEnable=$("#okBtnEnable").val();
			var okBtnEnableInfo=$("#okBtnEnableInfo").val();
			if("0"==okBtnEnable){//不允许提交订单
				$.error(okBtnEnableInfo||"表单校验未通过，请检查!");
				return;
			}else{//提交订单
				$("#order_submit").unbind("click");
				$("#order_submit").unbind("dbclick");
				$("#order_submit").attr("disabled","true");
				getDeveloper();
				$("#form_check").submit();
			}
		};
		//为订单提交按钮绑定点击、双击事件
		$("#order_submit").bind("click",order_submit_clickFun);
		$("#order_submit").bind("dbclick",order_submit_clickFun);
		var idType = $("#id_type").val();
		var idNumber = $("#id_number").val().replace(/ /g,"");//证件号码
		var len=idNumber.replace(/ /g,"").length;//str.match(/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/)
		if(false==pass(idNumber)){
			$("#id_number").focus();
			$("#okBtnEnable").val("0");
			$("#okBtnEnableInfo").val("证件号码有误，请检查！");
			$.error("证件号码有误，请检查！");
			return;
		}
		
		
		var essKey = $("#essKey").val();
		var accNbr = $("#acc_nbr").val();
		var prepay_flag = $("#prepay_flag").val();
		var ofrSubType3g = $("#ofr_sub_type_3g").val();
		var URL = "checkCustInfo";
		//alert(essKey);
		//进行校验之前先将输入框中资料清空
		$("#customer_name").val("");
		$("#customer_id").val("");
		$("#auth_end_date").val("");
		$("#auth_end_date_hidden").val("");
		$("#auth_adress").val("");
		$("#cust_sex").val("");
		$("#input_customer_sex").val("");
		$("#born_date").val("");
		$("#born_date_hidden").val("");
		$("#remark_desc").val("");
		$("#handler_name").val("");
		$("#handler_id_type").val("");
		$("#handler_id_number").val("");
		$("#handler_contact_address").val("");
		$("#handler_remark_desc").val("");
		$("#contactPerson").val("");
		$("#contactPhone").val("");
		$("#contactAddress").val("");
		
		/*$("#devolop_name").val("");
		$("#devolop_post").val("");
		$("#devolop_phone").val("");
		$("#devolop_channel_id").val("");
		$("#devolop_channel_name").val("");*/
		
		//进行校验之前先将输入框 class还原为页面初始值
		$("#id_number").removeClass("valid fieldError");
		$("#auth_end_date").removeClass("valid fieldError");
		$("#auth_adress").removeClass("valid fieldError");
		$("#customer_name").removeClass("valid fieldError");
		$("#cust_sex").removeClass("valid fieldError");
		$("#born_date").removeClass("valid fieldError");
		$("#contactPerson").removeClass("valid fieldError");
		$("#contactPhone").removeClass("valid fieldError");
		$("#contactAddress").removeClass("valid fieldError");
		$("#devolop_name").removeClass("valid fieldError");
		$("#devolop_post").removeClass("valid fieldError");
		$("#devolop_phone").removeClass("valid fieldError");
		$("#devolop_channel_id").removeClass("valid fieldError");
		$("#devolop_channel_name").removeClass("valid fieldError");
		
		$.ajax({
			url:URL,
			data:{
				"id_type":idType,
				"id_number":idNumber,
				"tele_type":teleType,
				"acc_nbr":accNbr,
				"essKey":essKey,
				"ofr_sub_type_3g":ofrSubType3g,
				"prepay_flag":prepay_flag
			},
			dataType:'json',
			type:'post',
			waitMsg:"正在校验！",
			success:function(message){
					var code=message.args.code;
					var detail=message.args.detail;
					if("200"==code||"0000"==code){//老客户
						var customer_name="";
						var auth_end_date="";
						var auth_adress="";
						var cust_sex="";
						var born_date="";
						var customer_id="";
						var contactPerson="";
						var contactPhone="";
						var contactAddress="";
						var arrearageFlag="";
						var blackListFlag="";
						var maxUserFlag="";
						//2G,3G,4G都走bss
//						if("2G"==teleType){
							customer_name=message.args.cust_info.customerName;
							auth_end_date=message.args.cust_info.certExpireDate;
							auth_adress=message.args.cust_info.certAddr;//CertAddr
							cust_sex=message.args.cust_info.customerSex;
							born_date=message.args.cust_info.customerBirth;
							customer_id=message.args.cust_info.customerID;
							contactPerson=message.args.cust_info.contactPerson;
							contactPhone=message.args.cust_info.contactPhone;
							contactAddress=message.args.cust_info.contactAddr;
							arrearageFlag=message.args.cust_info.arrearageFlag;
							blackListFlag=message.args.cust_info.blackListFlag;
							maxUserFlag=message.args.cust_info.maxUserFlag;
//						}else{
//							customer_name=message.args.cust_info.customerName;
//							auth_end_date=message.args.cust_info.certExpireDate;
//							auth_adress=message.args.cust_info.certAdress;
//							//cust_sex=message.args.cust_info.CustomerSex;
//							var bornDate = idNumber.substring(6, 10)+"-"+idNumber.substring(10,12)+"-"+idNumber.substring(12,14);
//							born_date=bornDate;
//							//customer_id=message.args.cust_info.CustomerID;
//							contactPerson=message.args.cust_info.contactPerson;
//							contactPhone=message.args.cust_info.contactPhone;
//							arrearageFlag=message.args.cust_info.arrearageFlag;
//						}
						
						//$.alert("arrearageFlag=="+arrearageFlag);
						if("1"==blackListFlag){//黑名单客户
							$("#okBtnEnableInfo").val("黑名单客户!");
							$("#okBtnEnable").val("0");
							$.error("黑名单客户!");
							return;
						}else if("1"==maxUserFlag){//已达到最大用户数
							$("#okBtnEnableInfo").val("已达到最大用户数!");
							$("#okBtnEnable").val("0");
							$.error("已达到最大用户数!");
							return;
						}else if("1"==arrearageFlag){//有欠费信息
							$("#okBtnEnableInfo").val("有欠费信息!");
							$("#okBtnEnable").val("0");
							$.error("有欠费信息!");
							return;
						}else{
							$("#okBtnEnableInfo").val(detail);
							$("#okBtnEnable").val("1");
							//$.alert(message.args.cust_info.certExpireDate);
							$("#customer_name").val(customer_name);
							$("#customer_name").attr("readonly","true");
							if(null!=auth_end_date&&""!=auth_end_date){ 
								$("#auth_end_date").val(auth_end_date);
								$("#auth_end_date_hidden").removeAttr("disabled");
								$("#auth_end_date_hidden").val(auth_end_date);
								$("#auth_end_date").attr("disabled","true");
							}else{
								$("#auth_end_date").removeAttr("disabled");
								$("#auth_end_date_hidden").attr("disabled","true");
							}
							$("#auth_adress").val(auth_adress);
							$("#auth_adress").attr("readonly","true");
							
							$("#cust_sex").val(cust_sex);
							$("#cust_sex").attr("readonly","true");
							
							$("#born_date").val(born_date);
							$("#born_date_hidden").removeAttr("disabled");
							$("#born_date_hidden").val(born_date);							
							$("#born_date").attr("disabled","true");
							
							$("#customer_id").val(customer_id);
							
							$("#contactPerson").val(contactPerson);
							$("#contactPhone").val(contactPhone);
							$("#contactAddress").val(contactAddress);
							$("#contactPerson").attr("readonly","true");
							$("#contactPhone").attr("readonly","true");
							//$("#contactAddress").attr("readonly","true");
							$.success(detail);
							return;
						}
					}else if("0001"==code){
						$("#customer_name").removeAttr("readonly");
						$("#auth_end_date").removeAttr("disabled");
						$("#auth_end_date_hidden").attr("disabled","true");
						$("#auth_adress").removeAttr("readonly");
						$("#cust_sex").removeAttr("readonly");
//						$("#born_date").removeAttr("disabled");
//						$("#born_date_hidden").attr("disabled","true");
						$("#contactPhone").removeAttr("readonly");
						$("#contactPerson").removeAttr("readonly");
						var bornDate = idNumber.substring(6, 10)+"-"+idNumber.substring(10,12)+"-"+idNumber.substring(12,14);
						$("#born_date_hidden").val(bornDate);
						$("#born_date").val(bornDate);
						$("#born_date").attr("disabled","true");
						var cust_sex = sexByIdCard(idNumber);
						if(cust_sex == "1"){
							$("#customer_sex option:first").prop("selected", "selected");
						}else{
							$("#customer_sex option:last").prop("selected", "selected");
						}
//						console.log("cust_sex"+cust_sex);
						$("#input_customer_sex").val(cust_sex);
						//$("input[name='born_date']").val(bornDate);
						$("#okBtnEnableInfo").val("新客户，请手动录入客户信息！");
						$("#okBtnEnable").val("1");
						$.success("新客户，请手动录入客户信息！");
						return;
					}else{
						$("#okBtnEnableInfo").val(detail);
						$("#okBtnEnable").val("0");
						$.error(detail);
						return;
					}
					
				},
			error:function(){
					var detail="客户资料校验失败，请稍后重试！";
					$("#okBtnEnableInfo").val(detail);
					$("#okBtnEnable").val("0");
					$.error(detail);
					return;
				}
			});
	});
	
});
