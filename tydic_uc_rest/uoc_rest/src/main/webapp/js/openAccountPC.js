var load_rwcard_acx = false;
var flag_select=false;
var pName = ""; // 姓名
var tele_type = "ALL";
var tele_type_tariff='';
var tele_type_num='';
var pSex = ""; // 性别
var pSexShow = "";
var pNation = ""; // 民族
var pBorn = ""; // 出生日期
var pAddress = ""; // 住址
var pCardNo = ""; // 身份证号
var pPolice = ""; // 签发机关
var pActivityLFrom = ""; // 起始日期
var pActivityLTo = ""; // 结束日期
var pDeviceNo = ""; //阅读器ID
var base64jpg = ""; // 照片
var readCardSucc = ""; // 是否读卡成功标示 1 成功 0 失败或者未读卡
var identityType = ''; // 认证方式
var identityFlag = false; // 认证成功标识
var defaultImagePath = '';
var id_card_image_path = '';
var current_channel_name = '';
var current_channel_id = '';
var terminals = '';
var pre_ting=''
var self_dev_code = '';
var model= '';
var brand='';
var terminal_brand ='';
var pay_flag = "1";
var ofr_id = '';
var combined_product_id='';
var property_code='';
var property_name='';
var mobile_no='';
var phone_flag='';
var select_acc_nbr='';
var select_first_prepay='';
var select_mon_limit='';
var select_class_id='';
var select_on_net='';
var phone_brand='';
var phone_model='';
var phone_color='';
var fee_all =0;
var fee_info=[];
var cust_seq_id = '';
var pos_sn ='';
var orderUpdateReturn =true;
var order_all_json=[];
var order_sub_type = "10010";
var oper_no='';
var card_no ='';
var idType ="02";
var teleTypeChngFlag ="0";
var cust_id='';
var province_code = '';
var write_way = '';
var select_property_code ='';
var select_terminal_model_code ='';
var select_ofr_type ='';
var scene_type ='';
//业务包类型
var ywb_type=null;
//2G业务包基套餐用到的资费ID
var ywb_ofr_id=null;
var activity_type_name ='';
var oper_dept_no ='';
//选中的发展人
var data_dev='';
//快速业务发展人标记
var dev_busi_flag="";
//场景模式选中的发展人
var dev_name_model ="";
//末梢代理商标志
var ms_flag = "";
//协同标志
var wt_flag = "";
//协同开户原始操作工号
var ori_oper_no ="";
//优惠金额
var discountAmount = 0.00;

//3G副卡    数据副卡功能支撑   主卡号码
var main_card_phonenumber = '';
var productName=null;
var ywb_product_id="";
//购机直降营销活动费用信息
var ac_fee="";//预存款初始化费用
var fee_code="";//费用项目编码
var fee_class="";//费用项目
var fee_name="";//费用项名称

var bind_dev = "";	//默认绑定的发展人
var dev_select_flag = "";		//为1时，可选择大代理商和绑定的发展人

var infoTerminalClearStoreVo;	//协同开户清库机信息
var logIdValidityVo;

var cur_step = "";

var page_cust_flag = "";

var step1_flag=false;

$(document).ready(function() {
	province_code= $("#province_code").val();
	write_way = $("#write_way").val();
	ms_flag = $("#ms_flag").val();
	
	//协同开户，广西的需求
	if(province_code == "gx"){
		wt_flag = $("#wt_flag").val();
		ori_oper_no = $("#ori_oper_no").val();
		var channel_run_type = "";
		if(wt_flag == "1"){
			//调用LoginOtherOperXT方法
			var URL = application.fullPath + "authority/restToController/LoginOtherOperXT";
			$.ajax({
				url:URL,
				type:'post',
				async: false,
				success:function(message){
					if(message.type == "error"){					
						$.alert(message.content);
					}
					else{
						channel_run_type = message.args.channel_run_type;
					}
				}
			});
		}
		
		if(channel_run_type == "02"){
			$(".show").hide();
			$.alert("此工号不能办理协同开户业务!");
			return;
		}
	}
	
	if(write_way =="0"){
		
	}else{
		$("#btn_load_test").hide();
	}
	if(province_code!=null){
		var tempHtml = $("#step1").html();
		document.getElementById("step1").innerHTML = $("#step2").html();
		document.getElementById("step2").innerHTML = tempHtml;
		document.getElementById("title1").innerHTML = "2";
		document.getElementById("title2").innerHTML = "1";
		
		var v_phone = document.getElementById("phone");
		var v_number = document.getElementById("number");
		var v_fee = document.getElementById("fee");
		v_phone.style.display  = "none";
		v_number.style.display  = "";
		v_fee.style.display  = "none";
		document.getElementById("tab_number").className ="current";
		document.getElementById("tab_fee").className ="";
		document.getElementById("tab_phone").className ="";
	}
	else if(province_code=="nx"){
		$("#ywb").html("可选附加包");
	}
	else{		
		$("#mode_select").hide();
	}
	
	var myDate = new Date();
	var text2 = '';
	var code_id = '';
	if(myDate.getDate()>=1&&myDate.getDate()<=24){
		text2 = "全月套餐";
		code_id ="02";
	}else if(myDate.getDate()>=25){
		text2 = "套餐包外资费";
		code_id ="01";
	}
	var text = "今天是"+(myDate.getMonth()+1)+"月"+myDate.getDate()+"日，建议选择"+text2;      
	$("#rwzf_hint").html(text);
	
	$("#first_month_fee_select option[value='"+code_id+"']").attr('selected','selected');
	$("#first_month_fee_select_group option[value='"+code_id+"']").attr('selected','selected');
	
	$("#step0").hide();
	$("#step2").hide();
	$("#step3").hide();
	$("#step4").hide();
	$("#step5").hide();
	$("#huodongywb").hide();
	$("#step2_next_div").hide();
	if(wt_flag == "1"){
		//普通办理隐藏
		$("#mode_quick").addClass('handle_btn_right_clicked');
		$("#mode_scene").removeClass('handle_btn_left_clicked');
		$("#mode_scene").hide();
		$("#mode_scene_detail").hide();
		$("#mode_quick_detail").show();
		
		$("#quick_detail_handle_type").css({visibility:'hidden'});
	}
	else{
		$("#mode_quick_detail").hide();
	}
	
	$("#pay_type_select").hide();
	$("#check_mobile2").hide();
	$("#check_mobile4").hide();
	
	
	$("#number_title1").hide();	
	$("#number_title2").hide();	
	
	$("#btnGetFee").hide();	
	$("#yixiang_phone").hide();
	$("#tab_phone").hide();
	$("#show2G").hide();
	$("#show3G").hide();
	
	$(".scroll_v").bind("mousewheel", function(event) {  
		return false;			
	});	
	
	$("#searchMobile").bind("click",function(){
		var input_text = $("#searchMobileInput").val()	
		getPhoneStock(input_text);
	});

	$("#searchTariff").bind("click",function(){
		var input_text = $("#TariffInput").val()	
		GetTariffName(input_text,"1");
	});
	
	$("#searchActivity").bind("click",function(){
		var input_text = $("#ActivityInput").val()	
		GetActivityName(input_text,"1");
	});
	
	$("#searchGiftActivity").bind("click",function(){
		var input_text = $("#GiftActivityInput").val()	
		GetGiftActivityName(input_text,"1");
	});
	
	$("#searchYwb").bind("click",function(){
		var input_text = $("#YwbInput").val()	
		GetYwbName(input_text,"1");
	});
	
	//发展人查询按钮绑定
	$("#searchDev").bind("click",function(){
		QueryDevName(dev_busi_flag);
	});
	
	$("#mode_scene").bind("click",function(){	
		if(mobile_no!=''){
			var dialog=$.dialog({
				   title:'提示',//提示title
				   content:'切换将清空已选数据,是否确定',//显示的内容，支持html格式。
				   buttons:[
						{id:'btn_ok',text:'确定',
							   onClick:function(){	
								   dialog.close();
								   $("#mode_quick").removeClass('handle_btn_right_clicked');
									$("#mode_scene").addClass('handle_btn_left_clicked');

									$("#mode_scene_detail").show();
									$("#mode_quick_detail").hide();
									groupDelete();
									CancelMobileNo();
					
							   }},  {id:'btn_ok',text:'取消',
								   onClick:function(){	
									   dialog.close();
						
								   }}
					   ]
			}); 		
		}else{
			$("#mode_quick").removeClass('handle_btn_right_clicked');
			$("#mode_scene").addClass('handle_btn_left_clicked');

			$("#mode_scene_detail").show();
			$("#mode_quick_detail").hide();
		}
		
	});
	$("#mode_quick").bind("click",function(){
		if(data_tariff!=''){
			var dialog=$.dialog({
				   title:'提示',//提示title
				   content:'切换将清空已选数据,是否确定',//显示的内容，支持html格式。
				   buttons:[
						{id:'btn_ok',text:'确定',
							   onClick:function(){	
								   dialog.close();
								   $("#mode_quick").addClass('handle_btn_right_clicked');
									$("#mode_scene").removeClass('handle_btn_left_clicked');

									$("#mode_quick_detail").show();
									$("#mode_scene_detail").hide();
									tariffDelete();
									ywbDelete();
									CancelMobileNo();
									activityDelete();
									giftActivityDelete();
					
							   }},  {id:'btn_ok',text:'取消',
								   onClick:function(){	
									   dialog.close();
						
								   }}
					   ]
			}); 		
		}else{
			$("#mode_quick").addClass('handle_btn_right_clicked');
			$("#mode_scene").removeClass('handle_btn_left_clicked');

			$("#mode_quick_detail").show();
			$("#mode_scene_detail").hide();
		}
		
	});
	
	$("#mode_scene_1").bind("click",function(){
		if(mobile_no!='')
		{
			var dialog=$.dialog({
				   title:'提示',//提示title
				   content:'切换将清空已选数据,是否确定',//显示的内容，支持html格式。
				   buttons:[
						{id:'btn_ok',text:'确定',
							   onClick:function(){	
								   dialog.close();
								   document.getElementById("mode_scene_1").className ="handle_type_title handle_type_title_clicked";
									document.getElementById("mode_scene_2").className ="handle_type_title";
									document.getElementById("mode_scene_3").className ="handle_type_title";
									document.getElementById("mode_scene_4").className ="handle_type_title";
									scene_type = "1";
									groupDelete();
									CancelMobileNo();
					
							   }},  {id:'btn_ok',text:'取消',
								   onClick:function(){	
									   dialog.close();
						
								   }}
					   ]
			}); 			
		}else{
			 document.getElementById("mode_scene_1").className ="handle_type_title handle_type_title_clicked";
				document.getElementById("mode_scene_2").className ="handle_type_title";
				document.getElementById("mode_scene_3").className ="handle_type_title";
				document.getElementById("mode_scene_4").className ="handle_type_title";
				scene_type = "1";
		}
	});
	
	$("#mode_scene_2").bind("click",function(){
		if(mobile_no!='')
		{
			var dialog=$.dialog({
				   title:'提示',//提示title
				   content:'切换将清空已选数据,是否确定',//显示的内容，支持html格式。
				   buttons:[
						{id:'btn_ok',text:'确定',
							   onClick:function(){	
								   dialog.close();
									document.getElementById("mode_scene_2").className ="handle_type_title handle_type_title_clicked";
									document.getElementById("mode_scene_1").className ="handle_type_title";
									document.getElementById("mode_scene_3").className ="handle_type_title";
									document.getElementById("mode_scene_4").className ="handle_type_title";
									scene_type = "2";
									groupDelete();
									CancelMobileNo();
					
							   }},  {id:'btn_ok',text:'取消',
								   onClick:function(){	
									   dialog.close();
						
								   }}
					   ]
			}); 		
		}else{
			document.getElementById("mode_scene_2").className ="handle_type_title handle_type_title_clicked";
			document.getElementById("mode_scene_1").className ="handle_type_title";
			document.getElementById("mode_scene_3").className ="handle_type_title";
			document.getElementById("mode_scene_4").className ="handle_type_title";
			scene_type = "2";
		}
	});
	
	$("#mode_scene_3").bind("click",function(){
		if(mobile_no!='')
		{
			var dialog=$.dialog({
				   title:'提示',//提示title
				   content:'切换将清空已选数据,是否确定',//显示的内容，支持html格式。
				   buttons:[
						{id:'btn_ok',text:'确定',
							   onClick:function(){	
								   dialog.close();
									document.getElementById("mode_scene_3").className ="handle_type_title handle_type_title_clicked";
									document.getElementById("mode_scene_1").className ="handle_type_title";
									document.getElementById("mode_scene_2").className ="handle_type_title";
									document.getElementById("mode_scene_4").className ="handle_type_title";
									scene_type = "3";
									groupDelete();
									CancelMobileNo();
					
							   }},  {id:'btn_ok',text:'取消',
								   onClick:function(){	
									   dialog.close();
						
								   }}
					   ]
			}); 	
		}else{
			document.getElementById("mode_scene_3").className ="handle_type_title handle_type_title_clicked";
			document.getElementById("mode_scene_1").className ="handle_type_title";
			document.getElementById("mode_scene_2").className ="handle_type_title";
			document.getElementById("mode_scene_4").className ="handle_type_title";
			scene_type = "3";
		}
	});
	
	$("#mode_scene_4").bind("click",function(){
		if(mobile_no!='')
		{
			var dialog=$.dialog({
				   title:'提示',//提示title
				   content:'切换将清空已选数据,是否确定',//显示的内容，支持html格式。
				   buttons:[
						{id:'btn_ok',text:'确定',
							   onClick:function(){	
								   dialog.close();
									document.getElementById("mode_scene_4").className ="handle_type_title handle_type_title_clicked";
									document.getElementById("mode_scene_3").className ="handle_type_title";
									document.getElementById("mode_scene_1").className ="handle_type_title";
									document.getElementById("mode_scene_2").className ="handle_type_title";
									scene_type = "5";
									groupDelete();
									CancelMobileNo();
					
							   }},  {id:'btn_ok',text:'取消',
								   onClick:function(){	
									   dialog.close();
						
								   }}
					   ]
			}); 	
		}else{
			document.getElementById("mode_scene_4").className ="handle_type_title handle_type_title_clicked";
			document.getElementById("mode_scene_3").className ="handle_type_title";
			document.getElementById("mode_scene_1").className ="handle_type_title";
			document.getElementById("mode_scene_2").className ="handle_type_title";
			scene_type = "5";
		}
	});
	
	$("#mode_scene_1").click();
	
	/*$("#teleType2G_sel").bind("click",function(){
		 if(teleTypeChngFlag=="0"){
			tele_type="2G";
			changeTeleType2G(tele_type);
			$("#step1").show();
			window.location.href = "#step1";
		 }else{
			 $.alert("订单生成后不能更改开户类型");
		 }
	});
	
	$("#teleType3G_sel").bind("click",function(){
		 if(teleTypeChngFlag=="0"){
			tele_type="3G";
			changeTeleType3G(tele_type);
			$("#step1").show();
			window.location.href = "#step1";
		 }else{
			 $.alert("订单生成后不能更改开户类型");
		 }
	});
	
	$("#teleType4G_sel").bind("click",function(){
		 if(teleTypeChngFlag=="0"){
			tele_type="4G";
			changeTeleType4G(tele_type);
			$("#step1").show();
			window.location.href = "#step1";
		 }else{
			 $.alert("订单生成后不能更改开户类型");
		 }
	});*/
	$("#mob_section").bind("change",function (){
		haoduanSelect(tele_type);	
	});
	/*$("#mob_section_3G").bind("change",function (){
		haoduanSelect(tele_type);	
	});
	$("#mob_section_4G").bind("change",function (){
		haoduanSelect(tele_type);	
	}); */
	//document.getElementById("step1_next_div").className ="ok";
	//document.getElementById("step2_next_div").className ="ok";
	/*$("#step1_next").bind("click",function(){
		var step1_next = document.getElementById("step1_next_div");
		if(step1_next.className=="ok"){	
			createOrderId(); 
			
			//$("#step2").show();	
		}
	});*/
	$("#group_next").bind("click",function(){
		var group_next = document.getElementById("group_next_div");
		if(group_next.className=="ok"){
			if(select_acc_nbr==''){
				$.alert("亲~你还没有选择号码~");
				return;
			}
			if(mobile_no==''){
				$.alert("串号未通过校验~");
				return;
			}
			if(combined_product_id==''){
				$.alert("未选择组合名称~");
				return;
			}	
			if(((province_code == "nx") || province_code == "gx") && (data_dev == "" || data_dev.dev_code == undefined)){
				$.alert("未选择发展人~");
				return;
			}
			stepNext2Click();
			
			pcRspTimeEnd($("#order_id").val());
			var scene_name = "";
			if(wt_flag == "1"){
				scene_name = "协同开户";
			}
			else if(scene_type == "1"){
				scene_name = "明星手机直降";
			}
			else if(scene_type == "2"){
				scene_name = "一年电话免费打";
			}
			else if(scene_type == "3"){
				scene_name = "99元本地存费送机合约";
			}
			else if(scene_type == "4"){
				scene_name = "购机直降合约";
			}
			
			if(province_code!=null){
				pcRspTimeStart($("#order_id").val(), "step2", "用户资料-"+scene_name);
				window.location.href = "#step2";
			}else{
				pcRspTimeStart($("#order_id").val(), "step3", "意向订单-"+scene_name);
				window.location.href = "#step3";
			}
		}
		
	});	
	
	$("#step2_next").bind("click",function(){
		if(productName=="手机上网数据共享功能包10元/月优惠套餐"&&province_code=="gx"){
			if(ywb_product_id==""){
				$.alert("亲~选了此资费要必选业务包哦")
				return;
			}
		}
		var step2_next = document.getElementById("step2_next_div");
		if(step2_next.className=="ok"){
						
			if(province_code!=null){
			
			}else{
				replaceCheck();
				var checkIsNumber=checkInput();
				if(!checkIsNumber){
					return checkIsNumber;
				}
			}
			
			if(mobile_no!=$("#mobile_no").val()){
				var dialog=$.dialog({
					   title:'提示',//提示title
					   content:'输入串号后未通过校验,视为放弃选择终端，是否继续',//显示的内容，支持html格式。
					   buttons:[/*{id:'btn_ok',text:'继续',
							   onClick:function(){									   
								   dialog.close();
								   stepNext2Click();
							   }
								   
							},*/{id:'btn_cancel',text:'重新操作',
								   onClick:function(){									   
									   dialog.close();									 
								   }
									   
								}]
					});
			}else if(mobile_no==''&&property_code!=''){
				var dialog=$.dialog({
					   title:'提示',//提示title
					   content:'已选择机型但是未输入串号进行校验,视为放弃选择终端，是否继续',//显示的内容，支持html格式。
					   buttons:[/*{id:'btn_ok',text:'继续',
							   onClick:function(){									   
								   dialog.close();
								   stepNext2Click();
							   }
								   
							},*/{id:'btn_cancel',text:'重新操作',
								   onClick:function(){									   
									   dialog.close();									 
								   }
									   
								}]
					});
			}else{
				//如果校验通过，活动类型和活动必选 YUN-727 
				if(($("#mobile_no").val()!=null) && ($("#mobile_no").val()!='')){
					if(((($("#activity_type_select").find("option:selected").text())!=null	&& ($("#activity_type_select").find("option:selected").text())!= '')) ){				

					}else{
						$.alert("亲~~校验串号后,活动类型必选");
						return false;
					}
				}
			}
			if(((province_code == "nx") || province_code == "gx") && (data_dev == "" || data_dev.dev_code == undefined)){
				$.alert("亲~~请选择发展人");
				return false;
			}
			stepNext2Click();
			pcRspTimeEnd($("#order_id").val());
			if(province_code!=null){
				pcRspTimeStart($("#order_id").val(), "step2", "用户资料");
				window.location.href = "#step2";
			}else{
				pcRspTimeStart($("#order_id").val(), "step3", "意向订单");
				window.location.href = "#step3";
			}
		}
	});
	$("#step3_next").bind("click",function(){
		var step3_next = document.getElementById("step3_next_div");
		if(step3_next.className=="ok"){
			pcRspTimeEnd($("#order_id").val());
			pcRspTimeStart($("#order_id").val(), "step4", "费用确认");
			getFee();			
			$("#step3").hide();
			$("#step4").show();	
		}
	});
	
	$("#step4_next").bind("click",function(){
		var step4_next = document.getElementById("step4_next_div");
		if(step4_next.className=="ok"){
			checkPosSn();
			
			pcRspTimeEnd($("#order_id").val());
		}
	});
	

	var v_phone = document.getElementById("phone");
	var v_number = document.getElementById("number");
	var v_fee = document.getElementById("fee");
	

	
	$("#tab_phone").click(function(){
	//$("tab_phone").bind("click",function(){
		v_phone.style.display  = "";
		v_number.style.display  = "none";
		v_fee.style.display  = "none";
		document.getElementById("tab_phone").className ="current";
		document.getElementById("tab_number").className ="";
		document.getElementById("tab_fee").className ="";
	});	
	$("#tab_number").click(function(){
	//$("tab_phone").bind("click",function(){
		v_phone.style.display  = "none";
		v_number.style.display  = "";
		v_fee.style.display  = "none";
		document.getElementById("tab_phone").className ="";
		document.getElementById("tab_number").className ="current";
		document.getElementById("tab_fee").className ="";
	});	
	$("#tab_fee").click(function(){
	//$("tab_phone").bind("click",function(){
		v_phone.style.display  = "none";
		v_number.style.display  = "none";
		v_fee.style.display  = "";
		document.getElementById("tab_phone").className ="";
		document.getElementById("tab_number").className ="";
		document.getElementById("tab_fee").className ="current";
	});	
	//默认选全月套餐
	//$("#first_month_fee_select option[value='02']").attr('selected','selected');
	
	/*
	 * 设置初始值
	 */
	defaultImagePath = $("#idCradImage").attr('src');
	id_card_image_path = $("#id_card_image_path").val();
	pName = ""; // 姓名
	pSex = ""; // 性别
	pSexShow = "";
	pNation = ""; // 民族
	pBorn = ""; // 出生日期
	pAddress = ""; // 住址
	pCardNo = ""; // 身份证号
	pPolice = ""; // 签发机关
	pActivityLFrom = ""; // 起始日期
	pActivityLTo = ""; // 结束日期
	pDeviceNo = ""; //阅读器ID
	base64jpg = ""; // 照片
	readCardSucc = ""; // 是否读卡成功标示 1 成功 0 失败或者未读卡
	/*
	 * 加载读卡器
	 */
	load_card_mech();
	
	// 读卡结果
	var strReadResult = "";
	var readError = false;
	$("#btn_load").click("click",function(){
		var messageFlag = $("#messageFlag").val();
		if(messageFlag !='1'){
			$.alert("请选择读卡器!");
			return;
		}
		// 读卡未结束，不再次读卡
		if (strReadResult == null && !readError) return;
		strReadResult = null;
		// 调用空间的读卡方法
		try {
			strReadResult = CVR_IDCard.ReadCard();
		}
		catch (e) {
			$.alert("读卡错误,请检查您的控件或驱动是否正确安装最新版本");
			readError = true;
			return;
		}
		readError = false;
		// 读卡成功时，填充页面显示信息
		if(strReadResult == "0") {
			readCardSucc = "1"; // 读卡成功标示，传给上个页面
			fillForm();  
			submit();
		} else {
			readCardSucc = "0"; // 读卡成功标示，传给上个页面
			$.alert("读卡错误,请移动身份证,进行读卡!");//错误原因描述strReadResult
		}
	});
	
	$("#btn_load_test").bind("click",function(){ 	
		var test = {};
		test.Name = "曹华";
		test.Sex = "1";
		test.Nation = "汉";
		test.Born = "1989-03-15";
		test.Address = "测试地址一二三四";
		test.CardNo = "500109198903151035";
		test.IssuedAt = "测试公安局";
		test.EffectedDate = "20120903";
		test.ExpiredDate = "20220903";
		test.Picture="/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDABALDA4MChAODQ4SERATGCgaGBYWGDEjJR0oOjM9PDkzODdASFxOQERXRTc4UG1RV19iZ2hnPk1xeXBkeFxlZ2P/2wBDARESEhgVGC8aGi9jQjhCY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2P/wAARCAB+AGYDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwDv6KKKACiis7WNUTTLYueXI+Uf40AWbi9gtv8AWOAfSufvfFP2feAV3BsDjtXIalrU00shDEBmz16VkSTSSsSxzn1piud0vjUggMFPuFrRtPFMEuNzA59sV5nkipEcjoaBXPY7W9gu1zE4JHVe4qxXk+n6vdWUqtFKRtPTsa9G0XV4dVtgykLKB8yZ6UhmlRRRQMKKKKACiiigCpqd4LGzaXjd2zXmes6rPqFw7u5wT0HSt3xvqe66FmjZVACcdjXLRwNLyRQFrlRk3c0mzaucVt2mmeYeRn0qW50sCPG3pS5ivZ6GAhB4NAAVqtvbeW2CKgkjwadyXFoaTg8Vq6HeywXqGGQI+cDJ4Psay1QkUpBQgjjFMR7JaTrc26SqQcjnHr3qauW8E6i09s1vJyV5U11NIYUUUUAFMncRQO5OAqk0+oL7/jym4z8poA8r1Em41CWR2ySx5Per1rAPKGB1rHnkZ73HUlq6q1twkab8CpbLgi7plsEUEjoKmuYEY8DqealgeJUA3rn608hXbhhUWOhNGFeaSJRuQc1izWbRkrIhBHQ4613QULVe4NswPmOmPQkU0TJI4byivQY9ahmUdB0rp7uxsZhmGQL/ALhzWHqdg9vHuU7l9aq5k49jS8FzeXqaRZOHPb6V6PXmngsk6zEcetel1RmFFFFABSOodGU9CMUtFAHl1/pn2TxN5GPk3gg47ZFb93CGYbiQoqx4nhC6rbTcZYY/Wp3gWdSGGc1nLc3gtDFk/s1MDz3VunAx+tXbNNrApIXHqTRc6SjoqleEOV9qt29uI13FdppMpbkkxYIc1iXUNormW4Ut7YzW3M+QBVS4sluI8MMg4zSTKaKUEtg6KsUWNw4yuKZq0CnTHAGAKvLZBVQY4QYHtUeor/oTrTe5NtCt8PYVaWaQjLJx9K7uua8DwiLTpjjkyf0rpa1OdqwUUUUCCiiigDA8VIojtpm6h9tJatkA1Z8UQ+bo8hC7jGQ2fQdzWbZS7raNx3FRI2pvSxpuQeTVSSfMhUHgcU/zN3BNQS20cgxvZfXFQbodNt2cEZ+tJay87WPNQtaRqpAkakijCOCGJI9aQGg+MVn3EZuHWFBksasPISKuaPEGleUr0GAaaV2RJ2Vy9p9oLK1EIxwSeKs0UVscoUUUUAFFFFAEV3CtzaywuMq6kEVy9tE1taqrH7pKnPsa62uH1a9T+3lto3zCobcAf4uKmWxcHZl/eCvB5qGQSZBErCoVl8p8NnFWQVkXg1kjpTK7rNjmfP0FOgDqcmRjTzCAc8U13VB15psG0yYPlgK6OxjEVqgHfk1w+o3Zt7cMDhyw2iu10u6jvNPhni+6y9PQ+lXAwqPoW6KKKsyCiiigAorN1PXLHTFIml3Sdo05b/61cnqPjK8uFKWqLbr69WP+FAHX6xqUWm2MkrOvmYwi55Jry8zu9y0xYly2S3rSTTy3Em+Z2d/Vjk00DFOwJnR2Vwl/blWbEi9RUM8t1bnbHkisWGZ4ZlkQ4IrpLe8huYlc43D7y1k42ZtGRnLf3ZbGDmrYkZY/OuGxgZxmpJ57dfmVf0rA1G5kuXKZwg7UkrjcrDLu8a+uC/IVeF+lbfhzxH/ZSvFOrSQtyMH7prnM7QAOgpkjVqlZGDd2egHx3p4nWPyJiD/GMYFblprOn3mBBdxliM7ScEV48vzTfQVaQkU7CPZFZWGVII9QaK8usNYv7FcWs5QYxggMPyNFFhlBpCxyab70h6UE9qYhD1pwNIelOA4zQIUY70GUxMCpw3akbgZqspMs/wAx+6KTKRan1CZtodQFxyQetNyGGe1MYbl59KjgJAK9gcUWE2PK5zUbLVnHWomHWmIiiX52PpVhKii+6T61MnSgaHg44oph5JooGf/Z";
		
		var valid_start='';
		var valid_end='';
		var year = '';
		var month = '';
		var day ='';
		pName = test.Name; // 姓名
		pSex = test.Sex; // 性别
		if(pSex != '1') {
			pSex = '0';
		}
		pSexShow = pSex == "1" ? "男" : "女";
		pNation = getNationStr(test.Nation); // 民族
		pBorn = test.Born; // 出生日期
		
		pAddress = test.Address; // 住址
		pCardNo = test.CardNo; // 身份证号
		pPolice = test.IssuedAt; // 签发机关
		pActivityLFrom = test.EffectedDate; // 起始日期
		if(pActivityLFrom.length == 8){
			var y = pActivityLFrom.substr(0, 4);
			var m = pActivityLFrom.substr(4, 2);
			var d = pActivityLFrom.substr(6, 2);
			pActivityLFrom = y + "-" + m + "-" + d; 
			valid_start = y + "." + m + "." + d;
		}
		pActivityLTo = test.ExpiredDate; // 结束日期
		if(pActivityLTo.length >= 8){
			var y = pActivityLTo.substr(0, 4);
			var m = pActivityLTo.substr(4, 2);
			var d = pActivityLTo.substr(6, 2);
			pActivityLTo = y + "-" + m + "-" + d;
			valid_end = y + "." + m + "." + d;
		}else{
			valid_end = pActivityLTo;
			pActivityLTo = "2099" + "-" + "01" + "-" + "01";
		}
		//pDeviceNo = test.CardReaderId; //阅读器终端ID
		base64jpg = test.Picture; // 照片编码 
		$('#customer_name').text(pName);
		$('#customer_sex').val(pSexShow);
		$('#nation_id').val(pNation);
		$('#born_date_val').val(pBorn);
		$('#auth_adress').text(pAddress);
		$('#idcard_addr').val(pPolice);
		$('#end_date_val').val(pActivityLTo);
		$('#id_number').html(pCardNo);
		$('#start_date_val').val(pActivityLFrom);
		$('#valid').val(valid_start+"―"+valid_end);
		$('#photo_buffer_val').val(base64jpg);
		//图片上的信息
		$('#bg_card_name').text(pName);
		$('#bg_card_sex').text(pSexShow);
		$('#bg_card_nation').text(pNation);
		$('#bg_card_born_year').text(year);
		$('#bg_card_born_month').text(month);
		$('#bg_card_born_day').text(day);
		$('#bg_card_born_addrss').text(pAddress);
		$('#bg_card_idcard_addr').text(pPolice);
		//身份证上的图片信息
		
		$('#bg_card_born_id_number').text(pCardNo);
		$('#bg_card_valid').text(valid_start+"―"+valid_end);
		
		submit();
	});
	
	
	
	$("#query").click(function() {
		selectPage();
	});
	/*$("#teleType2G").bind("click",function (){
		changeTeleType2G(tele_type);	
	});
	$("#teleType3G").bind("click",function (){
		changeTeleType3G(tele_type);	
	});
	$("#teleType4G").bind("click",function (){
		changeTeleType4G(tele_type);	
	});*/
	/*$("#connect_phone")[0].addEventListener("input", function(){
		var input_util = {
				checkInputInt: function (oInput) {
					oInput.value = oInput.value.replace(/\D/g, '');
				}
		} 
		input_util.checkInputInt(this);
	});*/
	
	 /*$("#connect_phone").keydown(function(event) {  
			var keyCode = event.which;
		   // $.alert('keyCode'+keyCode);
			if ((keyCode >= 48 && keyCode <=57) || (keyCode >= 96 && keyCode <=105) || keyCode == 8){
				return true; 
			}else{        	 
				return false;   
			}           
		}).focus(function() {  
			this.style.imeMode='disabled';
		});*/
	 
	if(province_code == "cq"){
		var dialog=$.dialog({
			   title:'提示',//提示title
			   content:'是否要生成新的订单',//显示的内容，支持html格式。
			   buttons:[{id:'btn_ok',text:'确定',
					   onClick:function(){
						   dialog.close();
						   createOrderId();
					   }
						   
					},{id:'btn_cancel',text:'取消',
						   onClick:function(){
							   dialog.close();
							   $("#step1").hide();
						   }
							   
						}]
			});
	}else{
		createOrderId();
	}
	
//	 createOrderId();
	
	 //初始加载发展人
	 GetDevName();
	 GetDevNameBusi();
	 
	 
	 
});

function stepNext2Click (){
	var checkGetActivityNameNew=checkGetActivityName();
	if(!checkGetActivityNameNew){
		return checkGetActivityNameNew;
	}
	if(data_tariff.activity_flag == "1" && (activity_id == null || activity_id =="")){
		$.alert("亲~此套餐必须选择活动类型哦~");
		return false;
	}	
	
	if(province_code!=null){
		$("#step2").show();	
		$("#step2_next_div").hide();
	}else{
		$("#step0").hide();
		$("#step1").hide();
		$("#step3").show();	
		$("#step2").hide();
		$("#c_customer_name").val(pName);
		$("#c_id_number").html(pCardNo);
	}
					
	$("#c_number").val(select_acc_nbr);
	/*if(tele_type=="3G"||tele_type=="4G"||tele_type=="4G"){
		$("#c_number_detail").html("号码预存话费"+select_first_prepay+"元");
	}else{*/
		$("#c_number_detail").html("号码预存话费"+select_first_prepay+"元，月承诺消费"+select_mon_limit+"元");
	//}
	var selectIndex = ''
	if(combined_product_id==''){
		$("#c_tariff").val(data_tariff.product_name);
		selectIndex = document.getElementById("first_month_fee_select").selectedIndex;
		var first_month_fee_select;
//		if( typeof( document.getElementById("first_month_fee_select")=="undefined" ) ){
		if( selectIndex == -1 ){
			first_month_fee_select = "全月套餐";
		}
		else{
			first_month_fee_select = document.getElementById("first_month_fee_select").options[selectIndex].text;
		}

		$("#c_first_month_fee").val(document.getElementById("first_month_fee_select").options[selectIndex].text);
		$("#c_activity").val(data_activity.activity_name);
	}else{
		$("#c_tariff").val(data_group.ofr_name);
		selectIndex = document.getElementById("first_month_fee_select_group").selectedIndex;
		$("#c_first_month_fee").val(document.getElementById("first_month_fee_select_group").options[selectIndex].text);
		$("#c_activity").val(data_group.activity_name);
	}	

	
	$("#c_discntName").val(data_giftActivity.discnt_name);
	if(mobile_no!=null&&mobile_no!=''){
		$("#yixiang_phone").show();
		$("#c_phone").val(phone_brand+"("+phone_model+")"+phone_color);
		$("#c_mobile_no").val(mobile_no);	
	}else{
		$("#yixiang_phone").hide();
	}

	if(step1_flag){	
		$("#step0").hide();
		$("#step1").hide();
		$("#step2").hide();	
		$("#step3").show();		
	}
	step1_flag = true;

};


function addMask (){
	var w = $(window).width();
	var h = $(document).height();
	var maskDivHtml = "<div id='maskDivNotice'  style='cursor: pointer;position:fixed; _position:absolute; top:0px;left:0px;width:"+w+"px;height:"+h+"px;opacity:0.45;background:none repeat scroll 0 0 #000000;z-index: 90009;filter: progid:DXImageTransform.Microsoft.Alpha(opacity=45)'></div>";
	var maskDivNotice = $("#maskDivNotice");
	if(maskDivNotice.length > 0){
		maskDivNotice.remove();
	}
	$('body').append(maskDivHtml);
	
};

function getScrollTop(){
	var D = document; 
	return Math.max(D.body.scrollTop, D.documentElement.scrollTop)
}

function payTypeChange(e) { 
	if(e.value =='16'){
		document.getElementById("pos_info").style.display  = "";	
	}else{
		document.getElementById("pos_info").style.display  = "none";	
	}
}

if (!window.XMLHttpRequest) { 
	var selects = document.getElementsByTagName("select");
	var selects_display = new Array;
}
function showdiv(popWinId) { 
//	document.getElementById("bg_mask").style.display ="block";
//	document.getElementById(popWinId).style.display ="block";
	if (!window.XMLHttpRequest) { 
		for(var i=0;i<selects.length;i++){
			if(selects[i].style.display!='none'){
				selects_display.push(selects[i]);
				selects[i].style.display = "none";
			};
		}
	}
	document.getElementById("bg_mask").style.display ='block';
	document.getElementById(popWinId).style.display ='block';

}
function hidediv(popWinId) {
	if (!window.XMLHttpRequest) { 
		for(var i=0;i<selects_display.length;i++){
			selects_display[i].style.display = "";
		}
	}
	document.getElementById("bg_mask").style.display ='none';
	document.getElementById(popWinId).style.display ='none';
}

function showErrMsg(error) {
	showdiv('pop_win_msg');
	document.getElementById("err_msg").innerHTML = error;//错误原因描述strReadResult

}

//加载身份证读卡器
function load_card_mech(){
	var id_card_mech = $("#id_card_mech").val();
	if(id_card_mech === "crvu"){
		load_model(id_card_mech);
	}
	$("#id_card_mech").change(function(){
		id_card_mech=$("#id_card_mech").val();
		if(id_card_mech === "crvu"){
			load_model(id_card_mech);
		}
//		else if(id_card_mech === "ICR"){
//			load_model("ICR");
//		}
	});
}

function load_model(model_name){
//	$("#message").val('成功加载:' + model_name);
//		$.get("/uss_web/cvr/"+model_name+"/content.html",function(rst){$('#reader_context').html(rst);});
//		$.getScript("/uss_web/cvr/"+model_name+"/script.js",function(rst){
//			$("#message").val('成功加载:' + model_name);
//		});
	$("#messageFlag").val('1');
};
// 将读取到的证件信息填充到页面上
function fillForm() {
	var valid_start='';
	var valid_end='';
	var year = '';
	var month = '';
	var day ='';
	pName = CVR_IDCard.Name; // 姓名
	pSex = CVR_IDCard.Sex; // 性别
	if(pSex=='0'||pSex=='2') {
		pSexShow = '女';
	}else{
		pSexShow = '男';
	}
	//pSexShow = pSex == "1" ? "男" : "女";
	pNation = getNationStr(CVR_IDCard.Nation); // 民族
	pBorn = CVR_IDCard.Born; // 出生日期
	
	// 截取生日日期，目前有些环境读卡器读出日期为 ‘yyyy-dd-mm’
	if(pBorn.length == 8){
		var year = pBorn.substr(0, 4);
		var month = pBorn.substr(4, 2);
		var day = pBorn.substr(6, 2);
		pBorn = year + "-" + month + "-" + day;
	}
	pAddress = CVR_IDCard.Address; // 住址
	pCardNo = CVR_IDCard.CardNo; // 身份证号
	pPolice = CVR_IDCard.IssuedAt; // 签发机关
	pActivityLFrom = CVR_IDCard.EffectedDate; // 起始日期
	if(pActivityLFrom.length == 8){
		var y = pActivityLFrom.substr(0, 4);
		var m = pActivityLFrom.substr(4, 2);
		var d = pActivityLFrom.substr(6, 2);
		pActivityLFrom = y + "-" + m + "-" + d; 
		valid_start = y + "." + m + "." + d;
	}
	pActivityLTo = CVR_IDCard.ExpiredDate; // 结束日期
	if(pActivityLTo.length >= 8){
		var y = pActivityLTo.substr(0, 4);
		var m = pActivityLTo.substr(4, 2);
		var d = pActivityLTo.substr(6, 2);
		pActivityLTo = y + "-" + m + "-" + d;
		valid_end = y + "." + m + "." + d;
	}else{
		valid_end = pActivityLTo;
		pActivityLTo = "2099" + "-" + "01" + "-" + "01";
	}
	pDeviceNo = CVR_IDCard.CardReaderId; //阅读器终端ID
	base64jpg = CVR_IDCard.Picture; // 照片编码 
	$('#customer_name').text(pName);
	$('#customer_sex').val(pSexShow);
	$('#nation_id').val(pNation);
	$('#born_date_val').val(pBorn);
	$('#auth_adress').text(pAddress);
	$('#idcard_addr').val(pPolice);
	$('#end_date_val').val(pActivityLTo);
	$('#id_number').html(pCardNo);
	$('#start_date_val').val(pActivityLFrom);
	$('#valid').val(valid_start+"―"+valid_end);
	$('#photo_buffer_val').val(base64jpg);
	//图片上的信息
	$('#bg_card_name').text(pName);
	$('#bg_card_sex').text(pSexShow);
	$('#bg_card_nation').text(pNation);
	$('#bg_card_born_year').text(year);
	$('#bg_card_born_month').text(month);
	$('#bg_card_born_day').text(day);
	$('#bg_card_born_addrss').text(pAddress);
	$('#bg_card_idcard_addr').text(pPolice);
	//身份证上的图片信息
	
	$('#bg_card_born_id_number').text(pCardNo);
	$('#bg_card_valid').text(valid_start+"―"+valid_end);
		
	return true;
}

//清空Object中存放的读取结果
function ClearIDCard() {
	CVR_IDCard.Name = ""; // 姓名
	CVR_IDCard.Sex = ""; // 性别
	CVR_IDCard.Nation = ""; // 民族
	CVR_IDCard.Born = ""; // 出生日期
	CVR_IDCard.Address = ""; // 住址
	CVR_IDCard.CardNo = ""; // 身份证号
	CVR_IDCard.IssuedAt = ""; // 签发机关
	CVR_IDCard.EffectedDate = ""; // 起始日期
	CVR_IDCard.ExpiredDate = ""; // 结束日期
	return true;
}

//清空页面显示信息
function ClearForm() {
	readCardSucc = "";
//	$("#id_card_mech").val('0');
	$('#valid').text('');
//	$('#messageFlag').val('1');
	$("#idCradImage").attr('src',defaultImagePath);//"<%=fullPath%>images/photo.png"
	$('#customer_name').text('');
	$('#customer_sex').text('');
	$('#nation_id').text('');
	$('#born_date_val').text('');
	$('#auth_adress').text('');
	$('#idcard_addr').text('');
	$('#end_date_val').val('');
	$('#id_number').text('');
	$('#start_date_val').val('');
	$('#photo_buffer_val').val('');
	//图片上的信息
	$('#bg_card_name').text('');
	$('#bg_card_sex').text('');
	$('#bg_card_nation').text('');
	$('#bg_card_born_year').text('');
	$('#bg_card_born_month').text('');
	$('#bg_card_born_day').text('');
	$('#bg_card_born_addrss').text('');
	$('#bg_card_idcard_addr').text('');
	$('#bg_card_born_id_number').text('');
	$('#bg_card_valid').text('');
	return true;
}

function submit() {
   var id_number=$("#id_number").text();
   if(id_number==""){
	   $.alert("请读取身份证!");
	   return;
   }
   var sex = $("#customer_sex").val();
   if(sex=="男"){
	   sex = "1";
   }else if(sex=="女"){
	   sex = "0";
   }
   $("#id_number_result").val($("#id_number").text());						//身份证
   $("#id_addr_result").val($("#auth_adress").text());						//证件地址
   $("#id_police_result").val($("#idcard_addr").val());						//签发地址
   $("#custom_name_result").val($("#customer_name").text());						//客户姓名
   $("#custom_sex_result").val(sex);						//客户性别
   $("#custom_birth_result").val($("#born_date_val").val());						//出生日期
   $("#custom_nation_result").val($("#nation_id").val());						//民族
   $("#start_date_result").val($("#start_date_val").val());						//生效时间
   $("#end_date_result").val($("#end_date_val").val());						//实效时间
   $("#photo_code_result").val($("#photo_buffer_val").val());						//照片编码
   var endDate = $("#end_date_result").val();
   endDate = endDate.replace(".","-").replace(".","-");//有效结束日期
   var data1={
		  "id_number":$("#id_number_result").val(),
		  "id_addr":trim($("#id_addr_result").val()),
		  "id_police":$("#id_police_result").val(),
		  "custom_name":$("#custom_name_result").val(),
		  "custom_sex":$("#custom_sex_result").val(),
		  "custom_birth":$("#custom_birth_result").val(),
		  "custom_nation":$("#custom_nation_result").val(),
		  "start_date":$("#start_date_result").val(),
		  "end_date":endDate,//$("#end_date_result").val(),
		  "photo_code":$("#photo_code_result").val(),
		  "wt_flag":wt_flag
	  }
   var URL = application.fullPath + "authority/idcard/addIdCard";
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		data:data1,
		waitMsg:"读取中..",
		success:function(message){	
			$("#idCradImage").attr('src',id_card_image_path+$("#id_number").text()+".png");
			if(message.type == "error"){
				var dialog=$.dialog({
					   title:'提示',//提示title
					   content:message.content,//显示的内容，支持html格式。
					   buttons:[{id:'btn_ok',text:'确定',
						   onClick:function(){					   
							   dialog.close();
						   }//点击时候回调函数
					   }]
				});
			}else{ 
				checkCustInfo(pCardNo); 
			}
			
			/*var dialog=$.dialog({
				   title:'提示',//提示title
				   content:'身份证读取成功,点击确定开始身份校验',//显示的内容，支持html格式。
				   buttons:[{id:'btn_ok',text:'确定',
					   onClick:function(){	
						   checkCustInfo(CVR_IDCard.CardNo); 
						   dialog.close();
					   }//点击时候回调函数
				   }]
				});*/
			
		}
	
	});
}

function checkCustInfo(id_number){	 
	 var data1={
			  "id_number":id_number,
//			  tele_type:"ALL",
			  tele_type:"2G", 
			  province_code:province_code,
			  //页面信息
			  page_info:"base.html",
			  wt_flag:wt_flag
		  }
	 var URL = application.fullPath + "authority/accountOpen/checkCustInfo";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"客户信息校验中..",
			success:function(message){
				if(message.type == "error"){
					$("#cust_check_info").html("<font color='red'>"+message.content+"</font>");
					emptyUserInfo();
					document.getElementById("step1_next_div").className ="ok_disabled";
				}else{
					var checkCustInfo = message.args.cust_info;
					logIdValidityVo =checkCustInfo;
					oper_dept_no =message.args.oper_dept_no;
					cust_seq_id = message.args.cust_seq_id;
					dealCustInfo(checkCustInfo);
					if(total_fee>0&&tele_type=="2G"){
						$.alert("该客户历史欠费"+total_fee.toFixed(2)+"元，请清欠后来开户");
						document.getElementById("step1_next_div").className ="ok_disabled";
					}else{
						
						//增加校验国政通实名认证
						if(province_code == "gx" && idType == "02"){
							$.ajax({
								url:application.fullPath + "rest/customerVerify/gztValidCustomerInfo",
								dataType:'json',
								contentType: "application/x-www-form-urlencoded; charset=utf-8", 
								type:'post',
								data:{
									jsessionid:$("#jsessionid").val(),
									certId:pCardNo,
									certName:pName
								},
								waitMsg:"客户信息校验中..",
								success:function(message){
									if(message.type == "error"){
										$("#cust_check_info").html("<font color='red'>"+message.content+"</font>");
										emptyUserInfo();
										document.getElementById("step1_next_div").className ="ok_disabled";
									}else{
										var dialog=$.dialog({
											   title:'提示',//提示title
											   content:'校验成功',//显示的内容，支持html格式。
											   buttons:[{id:'btn_ok',text:'下一步',
													   onClick:function(){									   
														   dialog.close();
														   $("#step1_next").click();
													   }
														   
													}]
											});
									}
								}
							});
						}
						else{
							if(message.type == "error"){
								$("#cust_check_info").html("<font color='red'>"+message.content+"</font>");
								emptyUserInfo();
								document.getElementById("step1_next_div").className ="ok_disabled";
							}else{
								var dialog=$.dialog({
									   title:'提示',//提示title
									   content:'校验成功',//显示的内容，支持html格式。
									   buttons:[{id:'btn_ok',text:'下一步',
											   onClick:function(){									   
												   dialog.close();
												   $("#step1_next").click();
											   }
												   
											}]
									});
							}
						}
					}
				}
			}
			
		});	
	
	 
	
	  
};

function createOrderId(){	 
	 var data1={
			 tele_type:"ALL",
			 order_sub_type:order_sub_type,
			 wt_flag:wt_flag
		  }
	 var URL = application.fullPath + "authority/accountOpen/orderInfoSubmit";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"订单生成中..",
			success:function(message){
				if(message.type == "error"){
					var dialog=$.dialog({
						   title:'提示',//提示title
						   content:'订单生成失败',//显示的内容，支持html格式。
						   buttons:[{id:'btn_ok',text:'重新生成订单',
								   onClick:function(){									   
									   dialog.close();
									   createOrderId();
								   }
									   
								}]
						});
				}else{
					teleTypeChngFlag="1";
					var order_id = message.args.order_info.order_id;
					phone_flag =  message.args.phone_flag;
					oper_no =  message.args.oper_no;
					$("#order_id").val(order_id);
					$("#step1_next").unbind("click").bind("click",function(){
						var step1_next = document.getElementById("step1_next_div");
						if(step1_next.className=="ok"){	
						replaceCheck();
						var checkIsNumber=checkInput();
						if(!checkIsNumber){
							return checkIsNumber;
						}
						//客户校验增加BSS信息录入接口
						addLogIdValidity();
						if(tele_type=="ALL" ||tele_type=="2G" || tele_type=="3G" || tele_type=="4G" || phone_flag=="0"){

							if(province_code!=null){
								$("#step0").hide();
								$("#step1").hide();
								$("#step2").hide();	
								$("#step3").show();	
								$("#c_customer_name").val(pName);
								$("#c_id_number").html(pCardNo);
								window.location.href = "#step3";
								
								pcRspTimeEnd($("#order_id").val());
								pcRspTimeStart($("#order_id").val(), "step3", "意向订单");
							}else{
								$("#step2").show();	
								$("#step2_next_div").hide();
								window.location.href = "#step2";
								var v_phone = document.getElementById("phone");
								var v_number = document.getElementById("number");
								var v_fee = document.getElementById("fee");
								v_phone.style.display  = "none";
								v_number.style.display  = "";
								v_fee.style.display  = "none";
								document.getElementById("tab_number").className ="current";
								document.getElementById("tab_fee").className ="";
								document.getElementById("tab_phone").className ="";
								pcRspTimeEnd($("#order_id").val());
								pcRspTimeStart($("#order_id").val(), "step2", "号码资费"	);
							}															
						}else{							
							$("#tab_phone").show();
							//getPhoneStock("");
							if(province_code!=null){
								$("#step0").hide();
								$("#step1").hide();
								$("#step2").hide();	
								$("#step3").show();	
								$("#c_customer_name").val(pName);
								$("#c_id_number").html(pCardNo);
								window.location.href = "#step3";
								pcRspTimeEnd($("#order_id").val());
								pcRspTimeStart($("#order_id").val(), "step3", "意向订单");
							}else{
								$("#step2").show();	
								$("#step2_next_div").hide();
								window.location.href = "#step2";
								pcRspTimeEnd($("#order_id").val());
								pcRspTimeStart($("#order_id").val(), "step2", "号码资费"	);
							}	
							
						}	 
						}
					});
					
					if(wt_flag == "1"){
						pcRspTimeStart($("#order_id").val(), "step1", "号码资费-协同开户"	);
					}
					else{
						pcRspTimeStart($("#order_id").val(), "step1", "号码资费"	);
					}
				}
			}
			
		});		  
};

function getPhoneStock(input_text){	 
	 var data1={
			 tele_type:tele_type,
			 input_text:input_text,
			 wt_flag:wt_flag
		  }
	 var URL = application.fullPath + "authority/accountOpen/qryTerminalStock";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"终端查询中..",
			success:function(message){
				if(message.type == "error"){
					$.alert(message.content);
				}else{
					current_channel_name = message.args.current_channel_name;
					terminals = message.args.terminal_info;
					//添加元素
					var result = "";
					//员工号
					code = message.args.current_channel_id;
					current_channel_id = message.args.current_channel_id;
					for(var i = 0; i < terminals.length; i++){
									if (i >= 20){
										break;
									}
									//JSON.stringify(obj)将JSON转为字符串。JSON.parse(string)将字符串转为JSON格式；
									//这里将数据封装到data中,data同时也是一个标记
									id = terminals[i].channel_id + "_" + terminals[i].property_code;
									terminals[i].unique_id = id;//手动添加唯一标识
									var data = JSON.stringify(terminals[i]);
									var benting = '';
									//判断是否显示本厅两个字
									self_dev_code = '0';
									if(code == terminals[i].channel_id){
										benting = "本厅";
										self_dev_code = '1';
									}
									result += 
										'<li  class="list_white">'+
											'<div class="left">'+
												'<input name="radio" type="radio"  id=\''+ id +'\' onclick="selectPhone(this,'+self_dev_code+')" id="num" data=\''+data+'\'>'+terminals[i].channel_name+'</input>'+
												'</li><li class="list"><div class="left"> <div class="left_lable bold">'+terminals[i].property_name+'</div>'+
												'</div></li><li class="list"><div class="left"> <div class="left_lable">' + benting +'数量:' +terminals[i].stock+'台'+'</div>'+
											'</div>'+
										'</li><div class="line_dashed_h"></div>';
					}
					//替换数据
					$("#phone_content").html(result);					
				}
			}
			
		});	
	
	 
	
	  
};

function selectPhone(e,self_dev_code){
		var parent = $(e);	
		var data = parent.attr("data"); 
		var objData = JSON.parse(data);
		flag = "1"; 
		//获取本厅剩余终端数量
		var stock = objData.stock;
		
		if(self_dev_code=='1'){
			property_name = objData.property_name;
			property_code = objData.property_code;
			
			var dialog=$.dialog({
				   title:'提示',//提示title
				   content:'已选终端型号['+objData.property_name+']',//显示的内容，支持html格式。
				   buttons:[{id:'btn_ok',text:'下一步',
						   onClick:function(){									   
							   dialog.close();
							   $("#tab_number").click();
						   }
							   
						}]
				});
			
			return;
		}
		
		//这里是得到员工号,员工号如果与channel_id相同,则/显示购买,如果不相同则需要调拨
		channel_id  = objData.channel_id;
		
		//记录滚动条的位置
		//window_scrollTop = document.body.scrollTop;
		//进入详情的时候将document.body.scrollTop设置为0
		//document.body.scrollTop=0;
		
		//判断显示哪个div,显示=======选择div
		$("#phone_from").html("<span class='bold'>从：</span>"+objData.channel_name);
		$("#phone_to").html("<span class='bold'>至：</span>"+current_channel_name);
		$("#phone_model").html("机型："+property_name);
		
		showdiv('phone_apply');
		$("#diaobo").unbind("click").bind("click",function(){	
			hidediv('phone_apply');
			pre_ting = objData.channel_name;
			property_code = objData.property_code;
			property_name = objData.property_name;
			DiaoBoShenQing(objData.channel_id,objData.property_code);

		});
}


function DiaoBoShenQing(channel_id,property_code){	 
	 var data1={
			//目标营业厅渠道编码
			channel_id:channel_id,
			//机器型号编码
			property_code:property_code,
			//数量
			count:"1",
			//订单号
			order_id:$("#order_id").val(),
			wt_flag:wt_flag
		  }
	 var URL = application.fullPath + "authority/accountOpen/changeForTerminalAllot";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"调拨申请中..",
			success:function(message){					 
				if(message.type == "error"){					
					//$.alert(message.content);
				}else{					
					//$.alert("调拨申请成功");
				}		
				
				var dialog=$.dialog({
					   title:'提示',//提示title
					   content:'调拨成功',//显示的内容，支持html格式。
					   buttons:[{id:'btn_ok',text:'下一步',
							   onClick:function(){									   
								   dialog.close();
								   $("#tab_number").click();
							   }
								   
							}]
					});
			}
			
		});	
	
	 
	
	  
};

/**
 * 清除用户信息
 */
function emptyUserInfo(){	
	
	//$("#id_number").html("");//身份证号码

	$("#customer_name").html("");//姓名
	$("#auth_adress").html("");//地址
	$("#cust_pic").hide().removeAttr("src");
	$("#connect_phone").val("");
	$("#connect_addr").val("");
	//baseInfo.agent_id_card_info = {};
	//baseInfo.id_card_info = {};
}
var black_flag="";//黑名单标示 1是黑名单
var total_fee=0;//历史欠费总金额
/**
 * 处理客户信息
 */
function dealCustInfo(checkCustInfo){
	if(!checkCustInfo){
		emptyUserInfo();
		$.alert("客户资料校验没有任何信息!");
		return;
	}
	 black_flag = checkCustInfo.black_flag;

		var cust_flag  =  checkCustInfo.cust_flag;
		page_cust_flag = cust_flag;
		cust_id = checkCustInfo.cust_id;
		var max_user_flag = checkCustInfo.max_user_flag;
		var connect_person = checkCustInfo.connect_person;
		var connect_phone = checkCustInfo.connect_phone;
		var connect_addr = trim(checkCustInfo.connect_addr);
			if (connect_addr == '' || connect_addr == null){
				connect_addr = trim(checkCustInfo.auth_adress);
			}
		var id_type = checkCustInfo.id_type;
		var id_number = checkCustInfo.id_number;
		var customer_name = checkCustInfo.customer_name;
		var cust_sex = checkCustInfo.cust_sex;
		var nation = checkCustInfo.nation; 
		var born_date = checkCustInfo.born_date;
		var auth_adress = checkCustInfo.auth_adress;
		var office = checkCustInfo.office;
		var auth_begin_date = checkCustInfo.auth_begin_date;
		var auth_end_date = checkCustInfo.auth_end_date;
		
		var user_info = checkCustInfo.user_info;
		//离网欠费owe_fee
		var owe_fee = checkCustInfo.owe_fee;

		var acc_nbr="";
		var arr_fee="";
		 Array.prototype.contains = function(item){
			  return RegExp("\\b"+item+"\\b").test(this);
			};
			total_fee=0;
		
		 if(owe_fee!=''){
			 total_fee+=parseFloat(owe_fee);
		 } else if(user_info!=null){
			for(var i=0;i<user_info.length;i++){
				var shares = new Array();
				
				if(user_info[i].pay_no!=''){
					 if(!shares.contains(user_info[i].pay_no)){
							total_fee+=	parseFloat(user_info[i].arr_fee);
						}
					 shares.push(user_info[i].pay_no);
				}else{
					total_fee+=	parseFloat(user_info[i].arr_fee);
				}
				
			}
		
		}
		
		 $("#customer_name").html(customer_name);
		$("#auth_adress").html(trim(auth_adress));//地址
		if(connect_phone){
			$("#connect_phone").val(trim(connect_phone));//联系电话
		}else{
			$("#connect_phone").val(select_acc_nbr);//联系电话			
		}
		
		if(connect_addr){
			$("#connect_addr").height("20px");
			$("#connect_addr").val(trim(connect_addr));//联系地址
		}else{
			$("#connect_addr").val(trim(auth_adress));//如果为空,默认回填地址
		}	
		if(customer_name==null){
			emptyUserInfo();
		}else{
			document.getElementById("step1_next_div").className ="ok";
			/**
			 * 回显信息
			 */
			 if(black_flag == "1"){
				$("#cust_check_info").html("<font color='red'>该客户为（黑名单）</font>");//姓名

			}
			 else if(total_fee>0&&tele_type=="2G"){
				/*if(tele_type=="3G"||tele_type=="4G"){
					$("#cust_check_info").html("（该客户历史欠费"+total_fee.toFixed(2)+"元，可点“确定”继续办理，清欠可在原系统操作）");//姓名				
				}else{*/
					$("#step2").hide();
					$("#step3").hide();
					$("#step4").hide();
					$("#step5").hide();
					document.getElementById("step1_next_div").className ="ok_disabled";
					$("#cust_check_info").html("<font color='red'>（该客户历史欠费"+total_fee.toFixed(2)+"元，请清欠后来开户）</font>");//姓名
				//}	
			}
			else{
				$("#cust_check_info").html("<font color='red'>"+"校验成功"+"</font>");
			}
		}
		
		
}


function trim(str){  
	if(str!=null)
		return str.replace(/^(\s|\u00A0)+/,'').replace(/(\s|\u00A0)+$/,'');  
	else
		return "";
} 


var intNumber=0;
function selectPage() {
	$("#dataLength").val("");
	var perrty_type = $.trim($("#perrty_type_pc").val());
	var good_type = $.trim($("#good_type").val());
	var fuzzy_query = $.trim($("#fuzzy_query").val());
	var mob_section;
	
	mob_section = $.trim($("#mob_section").val());
	if(tele_type_tariff==null||tele_type_tariff==''){
		if(mob_section=="185"||mob_section=="186"){
			tele_type = "3G4G";
		}else if(mob_section=="176" || mob_section=="175"){
			tele_type = "3G4G";
		}else{
			tele_type = "2G";
		}
		if(mob_section=="10000"){
			if(province_code == "cq"){
				tele_type = "4G";
			}else{
				tele_type = "ALL";
			}

		}
	}

	if(mob_section=="10000"){
		if(select_ofr_type=="501"){
			mob_section="145";
		}else{
			mob_section="";
		}
		perrty_type="";
	}
	
	/*if(mob_section=="10000"){
		if(tele_type=="4G"){
		 mob_section="185";
		}else if(tele_type=="3G"){
		  mob_section="186";
		}else{
		  mob_section="";
		}
	}*/
	if(fuzzy_query=="请输入1-11位数字查询号码"){
		fuzzy_query="";
	}
	if(fuzzy_query=="请输入后1-8位数字查询号码"){
		fuzzy_query="";
	}
	
	if(fuzzy_query==""&&mob_section==""){
		if(province_code == "cq"){
			mob_section="";
		}else{
			mob_section="185";
		}
		//mob_section="185";
	}
	
	if(perrty_type=="1"){
		perrty_type="";
	}
	var GetURl= application.fullPath + "authority/numberData/queryNumberData";
	$.ajax({
		url:GetURl,
		data:{
			"mob_section":mob_section,
			"perrty_type":perrty_type,
			"good_type":good_type,
			"fuzzy_query":fuzzy_query,
			"tele_type":tele_type,
			"page_info":"phonenumber.html",
			"wt_flag":wt_flag
		},
		dataType:'json',
		type:'post',
		waitMsg:"查询号码",
		success:function(page){
			$("#list").html('');
			var htmlNew = '';
			if(page.dataRows!=null){
			if(page.dataRows.length>0){
			 $("#dataLength").val(page.dataRows.length);
			 for(var i = 0;i < page.dataRows.length;i++){
				var numberBean = page.dataRows[i];
				if(tele_type=="3G"||tele_type=="4G"||tele_type=="3G4G"){
					var html = '<div class="wrap_line"  id="'+i+'wrap\"  deviceNumber="'+numberBean.acc_nbr+'\,'+numberBean.first_prepay+'\,'+numberBean.mon_limit+'\">'
						+'<a href="javascript:void(0);" onClick=\"jump(\''+numberBean.acc_nbr+'\',\''+numberBean.first_prepay+'\',\''+numberBean.mon_limit+'\',\''+numberBean.on_net+'\',0,\''+numberBean.class_id+'\')" class="code" id="'+numberBean.acc_nbr+'"> <span class="join" style="display:none;" id="'+numberBean.acc_nbr+'progress\">加入备选</span><dl><dt>'+numberBean.acc_nbr+'<dd>'
						+'首次预存'+numberBean.first_prepay+'元</dd>'
						+'  </dl></a></div>';
				}else{
					if( province_code=="nx" ){
						var str = "";
						if( numberBean.num_info=="16" ){
							str = "预付费号码";
						}
						else{
							str = "后付费号码";
						}
						var html = '<div class="wrap_line"  id="'+i+'wrap\"  deviceNumber="'+numberBean.acc_nbr+'\,'+numberBean.first_prepay+'\,'+numberBean.mon_limit+'\">'
						+'<a href="javascript:void(0);" onClick=\"jump(\''+numberBean.acc_nbr+'\',\''+numberBean.first_prepay+'\',\''+numberBean.mon_limit+'\',\''+numberBean.num_info+'\',\''+numberBean.lock_flag+'\',\''+numberBean.class_id+'\')" class="code" id="'+numberBean.acc_nbr+'"> <span class="join" style="display:none;" id="'+numberBean.acc_nbr+'progress\">加入备选</span><dl><dt>'+numberBean.acc_nbr+'<dd>'
						+str
						+'  </dl></a></div>';
					}
					else{
						var html = '<div class="wrap_line"  id="'+i+'wrap\"  deviceNumber="'+numberBean.acc_nbr+'\,'+numberBean.first_prepay+'\,'+numberBean.mon_limit+'\">'
						+'<a href="javascript:void(0);" onClick=\"jump(\''+numberBean.acc_nbr+'\',\''+numberBean.first_prepay+'\',\''+numberBean.mon_limit+'\',\''+numberBean.on_net+'\',\''+numberBean.lock_flag+'\',\''+numberBean.class_id+'\')" class="code" id="'+numberBean.acc_nbr+'"> <span class="join" style="display:none;" id="'+numberBean.acc_nbr+'progress\">加入备选</span><dl><dt>'+numberBean.acc_nbr+'<dd>'
						//+'首次预存'+numberBean.first_prepay+','+'月消费'+numberBean.mon_limit+'元</dd>'
						+'首次预存'+numberBean.first_prepay+'元</dd>'
						+'  </dl></a></div>';
					}
					
				}
					htmlNew +=html;
					
			 }
			 htmlNew +=' <div class="clear"></div>';
			 $("#list").append(htmlNew);
			 startTime();
			}
			}
		}
	});
	
	
	
}

function jump(acc_nbr,first_prepay,mon_limit,on_net,lock_flag,class_id){
	if(province_code == "cq"){
		numberOccupyPre(acc_nbr,first_prepay,mon_limit,on_net,lock_flag,class_id);
	}else{
		if(province_code != "nx"&&tele_type=="2G"){
			var GetURl= application.fullPath + "authority/numberData/getMonLimitByNumber";
			var low_fee = '0';
			$.ajax({
				url:GetURl,
				data:{
					"device_number":acc_nbr,
					"wt_flag":wt_flag
				},
				dataType:'json',
				type:'post',
				waitMsg:"号码信息获取中...",
				success:function(page){
					if(page.dataRows!=null){
						if(page.dataRows.length>0){
							low_fee = page.dataRows[0].mon_limit;
						}
					}		
//					if((tele_type=="3G"||tele_type=="4G"||tele_type=="3G4G")&&(low_fee != null && low_fee != '' && low_fee != '0')){
						mon_limit = low_fee;
//					}
					
					numberOccupyPre(acc_nbr,first_prepay,mon_limit,on_net,lock_flag,class_id);
					
				}
			});
		}
		else if(province_code == "nx"&&tele_type=="2G"){
			var GetURl= application.fullPath + "authority/numberData/nxGetMonLimitByNumber";
			var low_fee = '0';
			$.ajax({
				url:GetURl,
				data:{
					"device_number":acc_nbr,
					//"device_number":"13209555555",
					"nettype":on_net,
					"wt_flag":wt_flag
				},
				dataType:'json',
				type:'post',
				waitMsg:"号码信息获取中...",
				success:function(message){
					if(message.type == "success"){
						mon_limit = message.args.numberBean.mon_limit;
						first_prepay = message.args.numberBean.first_prepay;
					}
					
					numberOccupyPre(acc_nbr,first_prepay,mon_limit,on_net,lock_flag,class_id);
				}
			});
		}
		else{
			numberOccupyPre(acc_nbr,first_prepay,mon_limit,on_net,lock_flag,class_id);
		}
	}
  }

function numberOccupyPre(acc_nbr,first_prepay,mon_limit,on_net,lock_flag,class_id){
	if(mon_limit==null ||mon_limit==""){
		mon_limit = '0';
	}
	var content = '预占号：['+acc_nbr+']'+'<br>月消费：['+mon_limit+']元';
	if(province_code == "nx" && tele_type=="2G"){
		content += '<br>首次预存：['+first_prepay+']元';
	}
	var dialog=$.dialog({
		   title:'提示',//提示title
		   content:content,//显示的内容，支持html格式。
		   buttons:[{id:'btn_ok',text:'确定',
			   onClick:function(){
				   if(lock_flag=='1'){//该号码已经被锁
					   $.alert("该号码已被预占,请重新选择号码!");
				   }else{
					 numberOccupy(acc_nbr,first_prepay,mon_limit,on_net,class_id);		
				   }
				   dialog.close();
			   }},
				{id:'btn_ok',text:'加入备选',
					   onClick:function(){	
							  if($("#"+acc_nbr).hasClass("selected")){
								 $("#"+acc_nbr).removeClass("selected");	 
								 var showId=acc_nbr+"progress";
								 $("#"+showId).removeClass("cancel");
								 $("#"+showId).hide(); 
								 var showDiv=acc_nbr+"show";
								 $("#"+showDiv).remove(); 
								 intNumber--;
								 if(parseInt(intNumber)==0){
									 $("#number_title1").hide();	
								 }
							 }else{
							   if(parseInt(intNumber)>2){
										
								}else{
								$("#number_title1").show();	
								$("#"+acc_nbr).addClass("selected");	
								var showId=acc_nbr+"progress";
								   $("#"+showId).hide();
								   /*if(tele_type=="3G"||tele_type=="4G"){
									   var html = '<div class="wrap_line border_red" id="'+acc_nbr+'show\"><a href="javascript:void(0);" onClick=\"shanChu(\''+acc_nbr+'\',\''+first_prepay+'\',\''+mon_limit+'\',\''+on_net+'\',0)" class="code code_current"><dl>'
											+'<dt>'+acc_nbr+'</dt><dd>首次预存'+first_prepay+'元</dd></dl><div class="close"></div></a></div>';
								   }else{*/
									   var html = '<div class="wrap_line border_red" id="'+acc_nbr+'show\"><a href="javascript:void(0);" onClick=\"shanChu(\''+acc_nbr+'\',\''+first_prepay+'\',\''+mon_limit+'\',\''+on_net+'\',\''+lock_flag+'\',\''+class_id+'\')" class="code code_current"><dl>'
										+'<dt>'+acc_nbr+'</dt><dd>首次预存'+first_prepay+',月消费'+mon_limit+'元</dd></dl><div class="close"></div></a></div>'; 
								   /*}*/
								   $("#compareInfo").append(html);
								   //$("#test1").css({'top':(document.documentElement.scrollTop-$("#test1").height())});
								   intNumber++;
							  }
							}
							 window.location.href = "#number_title1";
							 dialog.close();
					   }}
			   ]
	}); 
}
function shanChu(acc_nbr,first_prepay,mon_limit,on_net,lock_flag,class_id){
	var dialog=$.dialog({
		   title:'提示',//提示title
		   content:'预占号码：['+acc_nbr+']',//显示的内容，支持html格式。
		   buttons:[{id:'btn_ok',text:'确定',
			   onClick:function(){
				   if(lock_flag=='1'){//该号码已经被锁
					   $.alert("该号码已被预占,请重新选择号码!");
				   }else{
					numberOccupy(acc_nbr,first_prepay,mon_limit,on_net,class_id);
				   }
				   dialog.close();
			   }},
				{id:'btn_ok',text:'删除备选',
					   onClick:function(){	
						   $("#"+acc_nbr).removeClass("selected");	 
							 var showId=acc_nbr+"progress";
							 $("#"+showId).removeClass("cancel");
							 $("#"+showId).hide(); 
							 var showDiv=acc_nbr+"show";
							 $("#"+showDiv).remove(); 
							 intNumber--;
							 if(parseInt(intNumber)==0){	
								 $("#number_title1").hide();
							 }
						   dialog.close();
					   }}
			   ]
	}); 
	 
	 
}
function numberOccupy(acc_nbr,first_prepay,mon_limit,on_net,class_id){	 
	var temp =  acc_nbr.substr(0,3);
	if(temp=="185"||temp=="186"){
		tele_type_num = "3G4G";
	}else if(temp=="176" || temp=="175"){
		tele_type_num = "3G4G";
	}else{
		tele_type_num = "2G";
	}		 
		 
	// tele_type = tele_type_num;	 
	 if( on_net==null || on_net=="" )	{
		 on_net = "1";
	 }
	 
	 if(temp=="145"){
		tele_type_num = "3G";
	 }
	 
	 
	 var data1={
			  tele_type: tele_type_num,
				//传入号码
				acc_nbr: acc_nbr,
				order_id:$("#order_id").val(),
				ser_type: on_net,
				wt_flag:wt_flag
		  }
	 var URL = application.fullPath + "authority/accountOpen/numberOccupy";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"号码预占中..",
			success:function(message){					 
				if(message.type == "error"){					
					$.alert(message.content);
				}else{	
					
					$("#number_title2").show();
					window.location.href = "#number_title2";
					/*if((tele_type=="3G"||tele_type=="4G")&&(low_fee != null && low_fee != '' && low_fee != '0')){
						$("#lockInfo").html('<div class="wrap_line border_red" id="'+acc_nbr+'show\"><a href="javascript:void(0);" class="code code_current"><dl>'
								+'<dt>'+acc_nbr+'</dt><dd>首次预存'+first_prepay+'元</dd></dl><div class="close"></div></a></div>');
					}else{*/
						$("#lockInfo").html('<div class="wrap_line border_red" id="'+acc_nbr+'show\"><a href="javascript:void(0);" class="code code_current"><dl>'
								+'<dt>'+acc_nbr+'</dt><dd>首次预存'+first_prepay+',月消费'+mon_limit+'元</dd></dl><div class="close"></div></a></div>');
								//+'<dt>'+acc_nbr+'</dt><dd>首次预存'+first_prepay+'元</dd></dl><div class="close"></div></a></div>');
					/*}*/
					select_acc_nbr = acc_nbr;
					select_first_prepay = first_prepay;
					select_mon_limit = mon_limit;
					select_on_net = on_net;
					select_class_id = class_id;
					if($("#connect_phone").val()==''||$("#connect_phone").val()==null){
						$("#connect_phone").val(acc_nbr);
					}
					checkStep2Complete();
					
					var dialog=$.dialog({
						   title:'提示',//提示title
						   content:'号码：['+acc_nbr+']预占成功',//显示的内容，支持html格式。
						   buttons:[
								{id:'btn_ok',text:'下一步',
									   onClick:function(){	
										   dialog.close();
										   if(province_code!=null){
											   window.location.href = "#step1"; 
										   }else{
											   window.location.href = "#step2";
										   }
										   $("#tab_fee").click();
									   }}
							   ]
					}); 														
				}				
			}
			
		});	
	
	 
	
	  
};


function startTime()
{   
	 var foo = $("a[class='code']");
	 $(foo).each(function() {
		 $(this).mouseenter(function (){	
				 if($(this).hasClass("selected")){
					 var fooId = $(this).attr("id");
					 var showId=fooId+"progress";
					 $("#"+showId).text("取消备选");
					 $("#"+showId).addClass("cancel");
					 $("#"+showId).show(); 
				 }else{
					 var fooId = $(this).attr("id");
					 var showId=fooId+"progress";
					 if(parseInt(intNumber)>2){
						 $(this).addClass("code_gray");
						 $("#"+showId).text("备选已满")
					  }else{
					   $("#"+showId).text("加入备选");
					  }
					 $("#"+showId).show();
				 }
			});
		 $(this).mouseleave(function (){
			 if(parseInt(intNumber)>2){
				 $(this).removeClass("code_gray"); 
			 }
			 var fooId = $(this).attr("id");
			 var showId=fooId+"progress";
			 $("#"+showId).hide();
			});
	 });	
}
function changeTeleType(tele_type) {
	/*if(tele_type!="2G"){
		$.alert("此处不能切换");
		return;
	}*/
	
	/*$("#teleType2G").addClass("g_current");
	$("#teleType3G").removeClass("g_current");
	$("#teleType4G").removeClass("g_current");
	$("#teleType3G").hide();
	$("#teleType4G").hide();
	
	$("#teleType2G_sel").addClass("g_current");
	$("#teleType3G_sel").removeClass("g_current");
	$("#teleType4G_sel").removeClass("g_current");*/
	
	/*$("#show2G").show();
	$("#show3G").hide();*/
	/*$("#showMob2G").show();
	$("#showMob4G").hide();
	$("#showMob3G").hide();*/
	if(tele_type=="2G"){
		$("#tele_type").val("2G");
		
		$("#pay_type_mobile_2G").show();
		$("#pay_type_mobile_3G").hide();
		$("#pay_type_mobile_4G").hide();		
		$("#first_month_fee").hide();				
		
		
		$("#first_month_fee_select option[value='01']").remove();
		$("#first_month_fee_select option[value='02']").remove();
		$("#first_month_fee_select option[value='03']").remove();
		
		$("#mob_section option[value='185']").remove();
		$("#mob_section option[value='186']").remove();
		$("#mob_section option[value='145']").remove();
		$("#mob_section option[value='175']").remove();
		$("#mob_section option[value='176']").remove();		
		if($("#mob_section option[value='156']").text()=='156'){
		}else{
			$("#mob_section").append("<option value='156'>156</option>");
		}
		if($("#mob_section option[value='155']").text()=='155'){
		}else{
			$("#mob_section").append("<option value='155'>155</option>");
		}
		if($("#mob_section option[value='132']").text()=='132'){
		}else{
			$("#mob_section").append("<option value='132'>132</option>");
		}
		if($("#mob_section option[value='131']").text()=='131'){
		}else{
			$("#mob_section").append("<option value='131'>131</option>");
		}
		if($("#mob_section option[value='130']").text()=='130'){
		}else{
			$("#mob_section").append("<option value='130'>130</option>");
		}
		
	}else if(tele_type=="3G"){
		$("#tele_type").val("3G");
		
		$("#pay_type_mobile_3G").show();
		$("#pay_type_mobile_2G").hide();
		$("#pay_type_mobile_4G").hide();
		
		if($("#first_month_fee_select option[value='01']").text()=='套餐包外资费'){
		}else{
			$("#first_month_fee_select").append("<option value='01'>套餐包外资费</option>");
		}
		if($("#first_month_fee_select option[value='03']").text()=='套餐减半'){
		}else{
			$("#first_month_fee_select").append("<option value='03'>套餐减半</option>");
		}
		

		if($("#mob_section option[value='186']").text()=='186'){
		}else{
			$("#mob_section").append("<option value='186'>186</option>");
		}
		if($("#mob_section option[value='185']").text()=='185'){
		}else{
			$("#mob_section").append("<option value='185'>185</option>");
		}
		if($("#mob_section option[value='145']").text()=='145'){
		}else{
			$("#mob_section").append("<option value='145'>145</option>");
		}
		if($("#mob_section option[value='156']").text()=='156'){
		}else{
			$("#mob_section").append("<option value='156'>156</option>");
		}
		if($("#mob_section option[value='155']").text()=='155'){
		}else{
			$("#mob_section").append("<option value='155'>155</option>");
		}
		if($("#mob_section option[value='132']").text()=='132'){
		}else{
			$("#mob_section").append("<option value='132'>132</option>");
		}
		if($("#mob_section option[value='131']").text()=='131'){
		}else{
			$("#mob_section").append("<option value='131'>131</option>");
		}
		if($("#mob_section option[value='130']").text()=='130'){
		}else{
			$("#mob_section").append("<option value='130'>130</option>");
		}
		
		
	}else if(tele_type=="4G"){
		$("#tele_type").val("4G");
		
		$("#pay_type_mobile_4G").show();
		$("#pay_type_mobile_3G").hide();
		$("#pay_type_mobile_2G").hide();
		
		if($("#first_month_fee_select option[value='01']").text()=='套餐包外资费'){
		}else{
			$("#first_month_fee_select").append("<option value='01'>套餐包外资费</option>");
		}
		if($("#first_month_fee_select option[value='03']").text()=='套餐减半'){
		}else{
			$("#first_month_fee_select").append("<option value='03'>套餐减半</option>");
		}
		
		$("#mob_section option[value='156']").remove();
		$("#mob_section option[value='155']").remove();
		$("#mob_section option[value='132']").remove();
		$("#mob_section option[value='131']").remove();
		$("#mob_section option[value='130']").remove();
		$("#mob_section option[value='145']").remove();
		if($("#mob_section option[value='186']").text()=='186'){
		}else{
			$("#mob_section").append("<option value='186'>186</option>");
		}
		if($("#mob_section option[value='185']").text()=='185'){
		}else{
			$("#mob_section").append("<option value='185'>185</option>");
		}
		if($("#mob_section option[value='176']").text()=='176'){
		}else{
			$("#mob_section").append("<option value='176'>176</option>");
		}
	}
	
	if(select_ofr_type=="501"){
		$("#mob_section option[value='156']").remove();
		$("#mob_section option[value='155']").remove();
		$("#mob_section option[value='132']").remove();
		$("#mob_section option[value='131']").remove();
		$("#mob_section option[value='130']").remove();
		$("#mob_section option[value='185']").remove();
		$("#mob_section option[value='186']").remove();
		$("#mob_section option[value='176']").remove();
		if($("#mob_section option[value='145']").text()=='145'){
		}else{
			$("#mob_section").append("<option value='145'>145</option>");
		}
	}else{
		$("#mob_section option[value='145']").remove();
	}
	//haoduanSelect(tele_type);
	
}
function changeTeleType3G(tele_type) {
	/*if(tele_type!="3G"){
		$.alert("此处不能切换");
		return;
	}*/
	/*$("#teleType3G").addClass("g_current");
	$("#teleType2G").removeClass("g_current");
	$("#teleType4G").removeClass("g_current");
	$("#teleType2G").hide();
	$("#teleType4G").hide();
	$("#teleType3G_sel").addClass("g_current");
	$("#teleType2G_sel").removeClass("g_current");
	$("#teleType4G_sel").removeClass("g_current");*/
	//$("#show2G").hide();
	//$("#show3G").show();
	/*$("#showMob3G").show();
	$("#showMob4G").hide();
	$("#showMob2G").hide();*/
	$("#tele_type").val("3G");
	
	$("#pay_type_mobile_3G").show();
	$("#pay_type_mobile_2G").hide();
	$("#pay_type_mobile_4G").hide();
	
	if($("#first_month_fee_select option[value='01']").text()=='套餐包外资费'){
	}else{
		$("#first_month_fee_select").append("<option value='01'>套餐包外资费</option>");
	}
	if($("#first_month_fee_select option[value='03']").text()=='套餐减半'){
	}else{
		$("#first_month_fee_select").append("<option value='03'>套餐减半</option>");
	}
	
	//$("#mob_section_3G").val("185");
	//haoduanSelect(tele_type);
}
function changeTeleType4G(tele_type) {
	/*if(tele_type!="4G"){
		$.alert("此处不能切换");
		return;
	}
	$("#show2G").hide();
	$("#show3G").show();*/
	/*$("#teleType4G").addClass("g_current");
	$("#teleType2G").removeClass("g_current");
	$("#teleType3G").removeClass("g_current");
	$("#teleType2G").hide();
	$("#teleType3G").hide();
	$("#teleType4G_sel").addClass("g_current");
	$("#teleType2G_sel").removeClass("g_current");
	$("#teleType3G_sel").removeClass("g_current");
	$("#showMob4G").show();
	$("#showMob3G").hide();
	$("#showMob2G").hide();*/
	$("#tele_type").val("4G");
	
	$("#pay_type_mobile_4G").show();
	$("#pay_type_mobile_3G").hide();
	$("#pay_type_mobile_2G").hide();
	
	if($("#first_month_fee_select option[value='01']").text()=='套餐包外资费'){
	}else{
		$("#first_month_fee_select").append("<option value='01'>套餐包外资费</option>");
	}
	if($("#first_month_fee_select option[value='03']").text()=='套餐减半'){
	}else{
		$("#first_month_fee_select").append("<option value='03'>套餐减半</option>");
	}
	
	//$("#mob_section_4G").val("185");
	//haoduanSelect(tele_type);
}

/*资费相关JS ==============开始==================*/

//查询组合
function GetGroupName(input_text,flag){
	 if(mobile_no == null||mobile_no ==""){
		$.alert("请先输入串号,校验通过后再选择组合~");
		return;
	}
	 var data1=null;
	  if(scene_type=='5'){//购机急降合约
		 data1 = {
					"tele_type":tele_type,
					"activity_flag":wt_flag == "1" ? "55" : scene_type,
					"wt_flag":wt_flag
				};
		}else{
		  data1 = {
				"tele_type":tele_type,
				"property_code":select_property_code,
				"terminal_model_code":select_terminal_model_code,
				"activity_flag":wt_flag == "1" ? "55" : scene_type,
				"wt_flag":wt_flag
			};
		}
	 var URL = application.fullPath + "authority/accountOpen/getGroupSaleCode";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"可选组合查询中..",
			success:function(message){		
				$("#group_list").html('');
				if(message.type == "error"){					
					$.alert(message.content);
				}else{	
					if(flag=="0"){
						showdiv('group_search');	
					}
					$.each(message.args.sale_selected_code_list, function(i, item) {
						var data = JSON.stringify(item);
						$("#group_list").append('<li class="list"><div class="left"><div class="left_lable">"'+item.product_name+'</div><div class="right_data">'
							  +' <input name="group_search_check" onClick="groupSelect(this)"  type="radio"  data=\''+data+'\' id=\''+item.product_id+'\'></input></div></div></li>');
					
					});
				}				
			}
			
		});	
	
}

var data_group='';
function groupSelect(e){
	var div = $(e);
	var dataStr = div.attr("data");	
	data_group = JSON.parse(dataStr);	
}

function groupConfirm(){
	combined_product_id = data_group.product_id;
	if(combined_product_id==null||combined_product_id==''){
		return;
	}
	tele_type_tariff=data_group.tele_type;
	select_ofr_type = data_group.ofr_type;
	if(select_ofr_type=="501"){
		tele_type="2G";
	}else{
		tele_type=data_group.tele_type;
	}
	changeTeleType(tele_type);
	pay_flag = data_group.prepay_flag;
	hidediv('group_search');	
	document.getElementById("group_product_select").value =   data_group.product_name;
	document.getElementById("group_product_detail").innerHTML =  data_group.product_desc;
	
	document.getElementById("group_next_div").className ="ok";
	 ac_fee=data_group.ac_fee;//预存款初始化费用
	 fee_code=data_group.fee_code;//费用项目编码
	 fee_class=data_group.fee_class;//费用项目
	 fee_name=data_group.fee_name;//费用项名称
	 if(scene_type=='5'){//快速办理购机直降合约
			
		 var data1={					
				 "ac_fee":ac_fee,//预存款初始化费用
				 "fee_code":fee_code,//费用项目编码
				 "fee_class":fee_class,//费用项目
				 "fee_name":fee_name//费用项名称	
				};
	    OrderUpdatedate(data1);
	}
	
	//GetActivityType();
	//checkStep2Complete();
}

function groupDelete(){
	hidediv('group_search');
	combined_product_id = '';
	data_group='';
	document.getElementById("group_product_select").value =  '';

	//checkStep2Complete();
	tele_type_tariff='';
	select_ofr_type='';
	if(tele_type_num!=null&&tele_type_num!=''){
		tele_type = tele_type_num;
	}else{
		tele_type = "ALL";
	}
	changeTeleType(tele_type);
	document.getElementById("group_product_detail").innerHTML = "";
	
	document.getElementById("group_next_div").className ="ok_disabled";
}

//查询资费
function GetTariffName(input_text,flag){
	if(province_code == "nx" && ms_flag == "1" && data_dev.dev_name == undefined){
		$.alert('请先选择一个发展人，再选择资费');
		return;
	}
	 var data1={};
	 var ofr_type;
	 
	 var tele_type_tmp="";
	 if(tele_type_num!=null&&tele_type_num!=''){
		 tele_type_tmp = tele_type_num;
	 }else{
		 tele_type_tmp = "ALL";
	 }
	 
	 if(tele_type_tmp == "2G"){
		ofr_type = "200500";
	}else if(tele_type_tmp == "3G"){
		ofr_type = "500";
	}else if(tele_type_tmp == "4G"){
		ofr_type = "600";
	}else if(tele_type_tmp == "3G4G"){
		ofr_type = "500600";
	}
	else if(tele_type_tmp == "ALL"){
		ofr_type = "000";
	}
	 
	var number_section ='';
	if(select_acc_nbr!=''){
		number_section = select_acc_nbr.substr(0,3);
	}
	if(number_section=="145"){
		ofr_type = "501";
	}
	 
	if(model == "" || brand == ""){
		data1 = {
			"tele_type":tele_type_tmp,
			"ofr_type":ofr_type,
			"product_name":input_text,
			order_id:$("#order_id").val(),
			"dev_code":(province_code == "nx" && (ms_flag == "1" || data_dev.big_agent_flag == "1")) ? (data_dev.dev_code == undefined ? "" : data_dev.dev_code) : "",
			"wt_flag":wt_flag
		};
	}else{
		data1 = {
			"tele_type":tele_type_tmp,
			"model_code":model,
			"brand_code":brand,
			"ofr_type":ofr_type,
			"product_name":input_text,
			order_id:$("#order_id").val(),
			"dev_code":(province_code == "nx" && (ms_flag == "1" || data_dev.big_agent_flag == "1")) ? (data_dev.dev_code == undefined ? "" : data_dev.dev_code) : "",
			"wt_flag":wt_flag
		};
	}
	 var URL = application.fullPath + "authority/accountOpen/saleSelectedCode";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"可选资费查询中..",
			success:function(message){		
				$("#tariff_list").html('');
				if(message.type == "error"){					
					$.alert(message.content);
				}else{	
					if(flag=="0"){
						showdiv('fee_search');	
					}
					$.each(message.args.sale_selected_code_list, function(i, item) {
						var data = JSON.stringify(item);
						//YUN-773
						$("#tariff_list").append('<li class="list"><div class="left"><div class="left_lable">'
								+'<a class="tip" href="javascript:void(0)" >'
								+item.product_name
								+'<span class="tip_info width_32" style="z-index:999;">'+item.product_desc+'</span></a>'
								+'</div><div class="right_data">'								
						//$("#tariff_list").append('<li class="list"><div class="left"><div class="left_lable">"'+item.product_name+'</div><div class="right_data">'
							  +' <input name="fee_search_check" onClick="tariffSelect(this)"  type="radio"  data=\''+data+'\' id=\''+item.product_id+'\'></input></div></div></li>');
			
					
					});
					//解决最后一行资费提示文字叠加到浮选框头部，预留空白位置
					$("#tariff_list").append('<div class="list"></div>');
					$("#tariff_list").append('<div class="list"></div>');
					$("#tariff_list").append('<div class="list"></div>');
				}				
			}
			
		});	
	
}

var data_tariff='';
function tariffSelect(e){
	var div = $(e);
	var dataStr = div.attr("data");	
	data_tariff = JSON.parse(dataStr);	
	ywb_ofr_id=data_tariff.ofr_id;//选择的资费ID
	productName=data_tariff.product_name;//3G副卡用到
}

function tariffConfirm(){
	ofr_id = data_tariff.product_id;
	if(ofr_id==null||ofr_id==''){
		return;
	}
	tele_type_tariff=data_tariff.tele_type;
	var number_section ='';
	if(select_acc_nbr!=''){
		number_section = select_acc_nbr.substr(0,3);
	}

	select_ofr_type = data_tariff.ofr_type;
	if(select_ofr_type=="501"){
		tele_type="2G";
	}else{
		tele_type=data_tariff.tele_type;
	}
	changeTeleType(tele_type);
	pay_flag = data_tariff.prepay_flag;
	hidediv('fee_search');	
	document.getElementById("tariff_select").value =  data_tariff.product_name;
	GetActivityType();
	checkStep2Complete();
}

function tariffDelete(){
	hidediv('fee_search');
	ofr_id = '';
	data_tariff='';
	document.getElementById("tariff_select").value =  '';
	$("#activity_type_select").html("<option value='0'></option>");
	$("#guarantee_type_select").html("<option value='0'></option>");
	checkStep2Complete();
	tele_type_tariff='';
	select_ofr_type='';
	if(tele_type_num!=null&&tele_type_num!=''){
		tele_type = tele_type_num;
	}else{
		tele_type = "ALL";
	}
	changeTeleType(tele_type);
}

//查询可选包
function GetYwbName(input_text,flag){
	//3G副卡
	var bssProduct=null;
	if(productName=="手机上网数据共享功能包10元/月优惠套餐"&&province_code=="gx"){
		bssProduct="SJB014";
	}
	 if(ofr_id == null||ofr_id ==""){
			$.alert("选择的资费不能为空");
			return;
		}
	 var data1={};
	 var ofr_type;
	 if(tele_type == "2G"){
			ofr_type = "801";
			ywb_type="901";
		}else if(tele_type == "3G"){
			ofr_type = "802";
			ywb_type="902";
		}else if(tele_type == "4G"){
			ofr_type = "803";
			ywb_type="903";
		}
		data1 = {
			"ofr_type":ofr_type,
			"input_text":input_text,
			"pay_flag":pay_flag,
			"ywb_type":ywb_type,
			"ywb_ofr_id":ywb_ofr_id,
			"bss_product":bssProduct,//3G副卡--fhc
			"wt_flag":wt_flag
		};
	 var URL = application.fullPath + "authority/accountOpen/getProductBag";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"可选业务包查询中..",
			success:function(message){	
				$("#ywb_list").html('');
				//+++
				if(data_tariff.activity_flag == "2"){
				 var URL2 = application.fullPath + "authority/accountOpen/getBusiSystemPara";
				 $.ajax({
					url:URL2,
					dataType:'json',
					contentType: "application/x-www-form-urlencoded; charset=utf-8", 
					type:'post',
					data:{"para_type":"mktActivityRelation",
						  "para_code":data_tariff.bss_product},
					waitMsg:"可选业务包查询中..",
					success:function(message){	
						if(message.type == "error"){					
							$.alert(message.content);
						}else{					
							if(flag=="0"){
							  showdiv('fee_option_busi');	
							}
								var data = JSON.stringify(message.args.busiSystemPara);
								$("#ywb_list").append('<li class="list"><div class="left"><div class="left_lable">"'+message.args.busiSystemPara.comments+'</div><div class="right_data">'
									  +' <input name="fee_search_check" onClick="ywbSelect(this)" type="radio" data=\''+data+'\' id=\''+message.args.busiSystemPara.para_value1+'\'></input></div></div></li>');
								console.info(data);
							
						}				
					}
					
				});	
			   }	
				if(message.type == "error"){					
					$.alert(message.content);
				}else{					
					if(flag=="0"){
					  showdiv('fee_option_busi');	
					}
					$.each(message.args.chk_product_info, function(i, item) {
						var data = JSON.stringify(item);
						$("#ywb_list").append('<li class="list"><div class="left"><div class="left_lable">"'+item.chk_product_name+'</div><div class="right_data">'
							  +' <input name="fee_search_check" onClick="ywbSelect(this)" type="radio" data=\''+data+'\' id=\''+item.chk_product_id+'\'></input></div></div></li>');
					
					});
					//3G副卡--fhc
					if(productName=="手机上网数据共享功能包10元/月优惠套餐"&&province_code=="gx"){
						$('#ywb_sure').removeClass('input_button').addClass('input_button_disabled');
						$("#ywb_list").append("<li class='list'><div class='left'><div class='left_lable'>主卡号码：<input type='text' id='main_card_number' oninput='checkNumber(value);' placeholder='请输入11位手机号码' class='input_text'></input></div></div></li>");
					}
				}				
			}
			
		});	
	
}


var data_ywb='';
var chk_product_id='';
function ywbSelect(e){
	var div = $(e);
	var dataStr = div.attr("data");	
	data_ywb = JSON.parse(dataStr);	
}

function ywbConfirm(){
	//3G副卡
	if(productName=="手机上网数据共享功能包10元/月优惠套餐"&&province_code=="gx"){
		ywb_product_id=data_ywb.ywb_product_id;
		main_card_phonenumber = $("#main_card_number").val();//3G副卡 主卡号
		var phoneRegex =  /^(13[0-9]|15[012356789]|18[02356789]|14[57]|17[0])[0-9]{8}$/;//手机号验证
		if(main_card_phonenumber==""||!phoneRegex.test(main_card_phonenumber)){
			//$.alert("请输入主卡号");
			return;
		}
	}
	if(data_ywb.para_value1 !=null && data_ywb.para_value1 !=""){
		data_ywb.dinner_service_flag="0";
		hidediv('fee_option_busi');
		document.getElementById("ywb_select").value =  data_ywb.comments;
		return;
	}
	
	chk_product_id = data_ywb.chk_product_id;
	if(chk_product_id==null||chk_product_id==''){
		return;
	}
	hidediv('fee_option_busi');
	document.getElementById("ywb_select").value =  data_ywb.chk_product_name;
}

function ywbDelete(){
	hidediv('fee_option_busi');
	chk_product_id = '';
	document.getElementById("ywb_select").value =  '';
	chk_product_id='';
}

function checkGetActivityName(){
	var flag=true;
	if(((($("#activity_type_select").find("option:selected").text())!=null 
			&& ($("#activity_type_select").find("option:selected").text())!= '')) 
			&& ($('#activity_select').val()==null 
					|| $('#activity_select').val()=='') && !flag_select){				
		$.alert("亲~选择了活动类型必须选择可选活动~");
		flag=false;
	}
	$("#activity_type_select").change(function(){
		flag_select=false;
	});
	return flag;
}

//查询可选活动
function GetActivityName(input_text,flag){				
	if(mobile_no!=$("#mobile_no").val()){
		$.alert("请校验终端串号!");
		return;
	}else if(mobile_no==''&&property_code!=''){
		$.alert("请校验终端串号!");
		return;
	}
	if((((($("#activity_type_select").find("option:selected").text())!="存费送费" ) &&
			(($("#activity_type_select").find("option:selected").text())!="存费送业务") &&
			(($("#activity_type_select").find("option:selected").text())!="自备机入网送话费")&&
			(($("#activity_type_select").find("option:selected").text())!=""))									
			&& ($("#mobile_no").val()==null||$("#mobile_no").val()=='') ) ){				
			$.alert("请校验终端串号!");
			return ;	
	}
	
	if(ofr_id == null||ofr_id ==""){
			$.alert("选择的资费和活动类型不能为空");
			return;
		}
	 var guarantee_type = $("#guarantee_type_select").val();
	 if(guarantee_type == null||guarantee_type ==""||guarantee_type =="0"){
		  if(tele_type=="3G"){
			guarantee_type = "04";
		  }else if(tele_type=="4G"){
			guarantee_type = "4";  
		  }
		}	
	 
	 if(guarantee_type =="04"&& tele_type=="4G"){
		 guarantee_type = "4";  
	 }
	 
	 
	 var activity_type = $("#activity_type_select").val();
	 if(activity_type == null||activity_type ==""||activity_type =="0"){
			$.alert("选择的资费和活动类型不能为空");
			return;
		}
	 
	 if(activity_type == "CFSF001" || activity_type =="ZSYW001"){
		guarantee_type = '';
	 }	 
	 
	 var data1 = {
				"product_id":ofr_id,
				"tele_type":tele_type,
				"activity_type":activity_type,
				"guarantee_type":guarantee_type,
				"brand":brand,
				"model":model,
				"mobile_no":mobile_no,
				"input_text":input_text,
				"wt_flag":wt_flag
		};
	 var URL = application.fullPath + "authority/accountOpen/getActivityName";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"可选活动查询中..",
			success:function(message){			
				$("#activity_list").html('');
				if(message.type == "error"){					
					$.alert(message.content);
					flag_select=true;
				}else{					
					if(flag=="0"){
						showdiv('fee_active_option');
					}
					$.each(message.args.chose_activity_list, function(i, item) {
						var data = JSON.stringify(item);
						$("#activity_list").append('<li class="list"><div class="left"><div class="left_lable">'+item.activity_name+'</div><div class="right_data">'
							  +' <input name="fee_search_check" onClick="activitySelect(this)" type="radio" data=\''+data+'\' id=\''+item.activity_id+'\'></input></div></div></li>');
					
					});
					if($("#activity_select").val()!=null && $("#activity_select").val()!=''){
						flag_select=true;
					}
					
				}				
			}
			
		});	
	
}


var data_activity='';
var activity_id='';
function activitySelect(e){
	var div = $(e);
	var dataStr = div.attr("data");	
	data_activity = JSON.parse(dataStr);	
}

function activityConfirm(){
	activity_id = data_activity.activity_id;
	if(activity_id==null||activity_id==''){
		return;
	}
	hidediv('fee_active_option');
	document.getElementById("activity_select").value =  data_activity.activity_name;
}

function activityDelete(){
	hidediv('fee_active_option');
	activity_id = '';
	document.getElementById("activity_select").value =  '';
	data_activity='';
	activity_type_name = '';
}



//查询活动业务包
function GetGiftActivityName(input_text,flag){
	 var activity_type = $("#activity_type_select").val();
	 if(activity_type !="ZSYW001"){
			$.alert("当前活动类型不支持活动业务包选择");
			return;
		}
	 if(activity_id == null||activity_id ==""){
			$.alert("选择的活动不能为空");
			return;
		}
	 var data1 = {
			 "activity_id":activity_id
		};
	 var URL = application.fullPath + "authority/accountOpen/get_Gift_activity";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"活动业务包查询中..",
			success:function(message){			
				$("#gift_activity_list").html('');
				if(message.type == "error"){					
					$.alert(message.content);
				}else{					
					if(flag=="0"){
						showdiv('gift_active_option');
					}
					$.each(message.args.gift_activity, function(i, item) {
						var data = JSON.stringify(item);
						$("#gift_activity_list").append('<li class="list"><div class="left"><div class="left_lable">'+item.discnt_name+'</div><div class="right_data">'
							  +' <input name="fee_search_check" onClick="giftActivitySelect(this)" type="radio" data=\''+data+'\' id=\''+item.discnt_code+'\'></input></div></div></li>');
					
					});
				}				
			}
			
		});	
	
}


var data_giftActivity='';
var discnt_code='';
function giftActivitySelect(e){
	var div = $(e);
	var dataStr = div.attr("data");	
	data_giftActivity = JSON.parse(dataStr);	
}

function giftActivityConfirm(){
	discnt_code = data_giftActivity.discnt_code;
	if(discnt_code==null||discnt_code==''){
		return;
	}
	hidediv('gift_active_option');
	document.getElementById("gift_activity_select").value =  data_giftActivity.discnt_name;
	checkStep2Complete();
}

function giftActivityDelete(){
	data_giftActivity='';
	hidediv('gift_active_option');
	discnt_code = '';
	document.getElementById("gift_activity_select").value =  '';
	checkStep2Complete();
}



//查询活动类型
function GetActivityType(){
	
	
	activityDelete();
	ywbDelete();
	$("#activity_type_select").html("<option value='0'></option>");
	giftActivityDelete();
	$("#guarantee_type_select").html("<option value='0'></option>");
	 var data1={
				"product_id":ofr_id,
				"tele_type":tele_type//,
				//"terminal_model_code":model
			};
	 var URL = application.fullPath + "authority/accountOpen/getActivityType";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"活动类型加载中..",
			success:function(message){					 
				if(message.type == "error"){
					if(message.content.indexOf("没有查询到可用的活动类型")>=0){
						
					}else{
						$.alert(message.content);
					}
				}else{						 
					 $.each(message.args.activity_list, function(i, item) {
							var data = JSON.stringify(item);
							$("#activity_type_select").append('<option value=\''+item.activity_type+'\'>'+item.activity_type_name+'</option>');
						});					
				}				
			}
			
		});	
	
}


//查询担保类型
function GetGuaranteeType(){
	activityDelete();
	giftActivityDelete();
	 $("#huodongywb").hide();
	 var activity_type = $("#activity_type_select").val();
	 if(activity_type==null||activity_type==""||activity_type=="0"){
		 $("#guarantee_type_select").html("<option value='0'></option>");
		 return;
	 }
	 activity_type_name = $("#activity_type_select option[value='"+$("#activity_type_select").val()+"']").text();
	 if(activity_type=="ZSYW001"){
		 $("#huodongywb").show();
	 }
	 $("#guarantee_type_select").html("<option value='0'></option>");
	 
	 if(activity_type == "CFSF001" || activity_type =="ZSYW001"){
		 $("#guarantee_type_select").attr("disabled",true);
	 }else{
		 var data1={
					"tele_type":tele_type,
					"product_id":ofr_id,
					"activity_type": activity_type
				};
		 var URL = application.fullPath + "authority/accountOpen/getGuaranteeType";
			$.ajax({
				url:URL,
				dataType:'json',
				contentType: "application/x-www-form-urlencoded; charset=utf-8", 
				type:'post',
				data:data1,
				waitMsg:"担保类型加载中..",
				success:function(message){					 
					if(message.type == "error"){					
						$.alert(message.content);
					}else{		
						 $("#guarantee_type_select").removeAttr("disabled");
						 $.each(message.args.guarantee_list, function(i, item) {
								var data = JSON.stringify(item);
								$("#guarantee_type_select").append('<option value=\''+item.guarantee_type+'\'>'+item.guarantee_type_name+'</option>');
							});
						 if($("#guarantee_type_select option[value='04']").val()=='04'){
							 $("#guarantee_type_select").val("04");
						 }
					}				
				}
				
			});	
	 }
}

//校验终端串号
function CheckMobileNo(flag){		
	if(data_activity!=''){
		 $.alert("请先清空已选活动");
			return;
	 }
	
	 var mobileNo ='';
		 
	 if(flag=="1"){
		 mobileNo =$("#mobile_no_group").val();
	 }else{
		 mobileNo =$("#mobile_no").val(); 
		 if($("#tariff_select").val()==null||$("#tariff_select").val()==''){
			$.alert("亲~你没有选择资费名称哦~");
				return;
		 }
	 }
	 
	 if(mobileNo==null||mobileNo==''){
		 $.alert("输入串号不能为空");
			return;
	 }
	 
	 //协同开户先查询清库是否存在这个终端
	 if(wt_flag == "1"){
		 var data1={
				"mac_number":mobileNo,
				"wt_flag":wt_flag
		};
		 var URL = application.fullPath + "authority/accountOpen/getTerminalClearStore";
			$.ajax({
				url:URL,
				dataType:'json',
				contentType: "application/x-www-form-urlencoded; charset=utf-8", 
				type:'post',
				data:data1,
				waitMsg:"清库信息查询中..",
				success:function(message){	
					if(message.type == "success"){
						infoTerminalClearStoreVo = message.args.infoTerminalClearStoreVo;
						CheckMobileNoById(mobileNo, flag);
					}
					else{					
						$.alert(message.content);
					}
					
				}});
	 }
	 else{
		if(scene_type=='5'){//购机急降合约
			 checkIMEI(mobileNo);//串号校验
		 }else{
		    CheckMobileNoById(mobileNo,flag);		
		 }
		
	 }

}
function CheckMobileNoById(mobileNo,flag){
	 var data1={
			 "order_id":$("#order_id").val(),
				"terminal_id":mobileNo,
				"wt_flag":wt_flag
			};
	 var URL = application.fullPath + "authority/accountOpen/getTerminalInfoByTerminalId";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"串号校验中..",
			success:function(message){					 
				if(message.type == "error"){	
					CancelMobileNo();
					$.alert(message.content);
				}else{	
					var data = message.args.terminal_info;  
					if(property_code!=null&&property_code!=''&&data.property_code!=property_code){
						CancelMobileNo();						
						$("#mobile_no").val("");
						$("#mobile_no_group").val("");
						 $.alert("选择的手机型号与检验的不一致，请检查是否是同一型号");		
						 mobile_no = '';
						 return;
					}  
					
					select_property_code = data.property_code;
					select_terminal_model_code = data.terminal_model_code;
					
					model = data.terminal_model_code;
					brand = data.terminal_brand_code;
					terminal_brand = data.terminal_brand;
					phone_brand = data.terminal_brand_desc;
					phone_model = data.terminal_model_desc;
					phone_color = data.terminal_color;
				
					if(flag=="1"){
						if(phone_brand != null){
							$("#group_brand").html(phone_brand);
						}
						if(phone_model != null){
							$("#group_model").html(phone_model);
						}
						if(phone_color != null){
							$("#group_color").html(phone_color);
						}
						mobile_no = $("#mobile_no_group").val();
						$("#check_mobile4").show();
						$("#check_mobile3").hide();		
					}else{
						if(phone_brand != null){
							document.getElementById("brand").value = phone_brand;
						}
						if(phone_model != null){
							document.getElementById("model").value = phone_model;
						}
						if(phone_color != null){
							document.getElementById("color").value = phone_color;
						}
						mobile_no = $("#mobile_no").val();
						document.getElementById("mobile_no").readOnly ="true";
						$("#check_mobile2").show();
						$("#check_mobile1").hide();		
					}
				
					
					
					if(flag=="1"){
						AutoSelectTariff();
					}
				}				
			}
			
		});	
}


function AutoSelectTariff(){
	if(scene_type=='5'){//购机急降合约
		data = {
				"tele_type":tele_type,
				"activity_flag":wt_flag == "1" ? "55" : scene_type,
				"wt_flag":wt_flag
			};
	}else{
		data = {
				"tele_type":tele_type,
				"property_code":select_property_code,
				"terminal_model_code":select_terminal_model_code,
				"activity_flag":wt_flag == "1" ? "55" : scene_type,
				"wt_flag":wt_flag
			};
	}
	 var URL = application.fullPath + "authority/accountOpen/getGroupSaleCode";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data,
			waitMsg:"串号对应组合查询中..",
			success:function(message){					 
				if(message.type == "error"){	
					$.alert(message.content);
				}else{	
					if(message.args.sale_selected_code_list.length == 0){
					}else{
						var data = message.args.sale_selected_code_list[0];

						data_group = data;
						//ofr_id = data.ofr_id;
						tele_type_tariff=data.tele_type;
						select_ofr_type = data.ofr_type;
						if(select_ofr_type=="501"){
							tele_type="2G";
						}else{
							tele_type=data.tele_type;
						}
						changeTeleType(tele_type);
						pay_flag = data.prepay_flag;
						combined_product_id = data_group.product_id;
						//document.getElementById("tariff_select").value =  data_tariff.product_name;
						//GetActivityType();
						//checkStep2Complete();
						document.getElementById("group_next_div").className= "ok";
						document.getElementById("group_product_select").value =   data.product_name;
						document.getElementById("group_product_detail").innerHTML =  data.product_desc;
						 ac_fee=data_group.ac_fee;//预存款初始化费用
						 fee_code=data_group.fee_code;//费用项目编码
						 fee_class=data_group.fee_class;//费用项目
						 fee_name=data_group.fee_name;//费用项名称
						 if(scene_type=='5'){//快速办理购机直降合约
								
							 var data1={											
									 "ac_fee":ac_fee,//预存款初始化费用
									 "fee_code":fee_code,//费用项目编码
									 "fee_class":fee_class,//费用项目
									 "fee_name":fee_name//费用项名称	
									};
						    OrderUpdatedate(data1);
						}
					}											
				}				
			}
			
		});	
}

function CancelMobileNo(){
	mobile_no ='';
	$("#mobile_no").val('');
	$("#mobile_no_group").val('');
	$("#check_mobile1").show();
	$("#check_mobile2").hide();
	$("#check_mobile3").show();
	$("#check_mobile4").hide();
	document.getElementById("brand").value = '';
	document.getElementById("model").value = '';
	document.getElementById("color").value = '';
	$("#group_brand").html("");
	$("#group_model").html("");
	$("#group_color").html("");
	document.getElementById("mobile_no").readOnly ="";
	phone_brand = '';
	phone_model = '';
	phone_color = '';
	model = '';
	brand = '';
	terminal_brand = '';	
	document.getElementById("group_next_div").className ="ok_disabled";
}

function checkStep2Complete(){
	if(select_acc_nbr==''||$("#tariff_select").val()==''){
		document.getElementById("step2_next_div").className.className= "ok_disabled";
		$("#step2_next_div").hide();
	}else{
		var activity_type = $("#activity_type_select").val();
		if(activity_type=="ZSYW001"&&(discnt_code==null||discnt_code=='')){
			document.getElementById("step2_next_div").className.className= "ok_disabled";
			$("#step2_next_div").hide();
		}else{
			document.getElementById("step2_next_div").className= "ok";
			$("#step2_next_div").show();
		}

	}
}

/*资费相关JS ==============结束=====================*/


/*意向订单 相关JS  =====================开始======================*/
function GoToStep(step){	
	$("#step3").hide();	
	if(step==4){
		if(combined_product_id==''){
			$("#tab_phone").click();
		}else{
			$("#tab_fee").click();
		}
		
	}if(step==2){
		$("#tab_number").click();
	}if(step==3){
		$("#tab_fee").click();
		$("#step2_next_div").show();
	}
	if(province_code!=null){
		if(step==1){
			$("#step2").show();
		}else {
			$("#step1").show();
		}
		window.location.href = "#step1";
	}else{
		if(step==1){
			$("#step1").show();
		}else {
			$("#step2").show();
		}
		window.location.href = "#step2";
	}	
}
/*意向订单 相关JS ==============结束=====================*/

/*费用 相关JS  =====================开始======================*/
function getFee(){
	
	 var first_month_fee = '';
	 var product_id_tmp = '';
	 var activity_id_tmp = '';
	 var activity_type_tmp = '';
		 
	if(combined_product_id==''){
		first_month_fee = $("#first_month_fee_select").val();
		product_id_tmp = data_tariff.product_id;
		activity_id_tmp =  data_activity.activity_id;
		activity_type_tmp =  data_activity.activity_type;	
	}else{
		first_month_fee = $("#first_month_fee_select_group").val();
		product_id_tmp = data_group.ofr_id;
		activity_id_tmp =  data_group.activity_id;
		activity_type_tmp =  data_group.activity_type;	
	}	

	
	 var data1={
				//订单编号
				"order_id":$("#order_id").val(),
				//产品编号
				"product_id": product_id_tmp,
				
				"activity_id": activity_id_tmp,
				"acc_nbr": select_acc_nbr,
				"id_number": pCardNo,
				"id_type": idType,
				"auth_adress": pAddress,
				"auth_end_date": pActivityLTo,
				"customer_name": pName,
				"contact_phone": $("#connect_phone").val(),
				"contact_adress": $("#connect_addr").val(),
				"first_month_fee":first_month_fee==null ? "" :first_month_fee,
				"activity_type":activity_type_tmp==null ? "" :activity_type_tmp,
				"terminal_id":mobile_no==null ? "" :mobile_no,
						
				"packageId":data_giftActivity.package_id==null ? "" :data_giftActivity.package_id,
				"elementId":discnt_code==null ? "" :discnt_code,
				"elementType":data_giftActivity.element_type==null ? "" :data_giftActivity.element_type,
						
				//号码类型
				"tele_type": tele_type,
				//号码计费
				"acc_nbr_fee": select_first_prepay,
				"class_id": select_class_id==null ? "" :select_class_id,
				//这个参数 暂时写死 ,1-后付费 2-预付费
				"prepay_flag": pay_flag==null ? "1" :pay_flag,
				//业务包
				"chk_product_id": data_ywb.chk_product_id,
				//页面信息
				"page_info":"cost.html",
				"dev_code" : data_dev.dev_code == null ? "" :data_dev.dev_code,	//发展人代号
				"dev_name" : data_dev.dev_name == null ? "" :data_dev.dev_name,	//发展人名字
				"chnl_code" : data_dev.chnl_code == null ? "" :data_dev.chnl_code,	//渠道类型
				"wt_flag":wt_flag
			};
	 var URL = application.fullPath + "authority/accountOpen/getFeeData";
	 fee_all =0;
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			beforeSend:function(){
				$("#step4_next_div").removeClass("ok").addClass("ok_disabled");
			},
			waitMsg:"获取费用信息中..",
			success:function(message){					 
				if(message.type == "error"){
					$("#fee_all").html("&nbsp;&nbsp;&nbsp;0");
					document.getElementById("step4_next_div").className ="ok_disabled";
					$("#btnGetFee").show();	
					$.alert(message.content);
				}else{	
					$("#step4_next_div").removeClass("ok_disabled").addClass("ok");
					document.getElementById("step4_next_div").className ="ok";
					$("#btnGetFee").hide();
					 $("#fee_list").html('');
					 fee_info = message.args.fee_info;
					 
					//为清库终端时，限制不能调整合约款和合约预存款，并且在费用项上新增显示清库优惠价
                    if(infoTerminalClearStoreVo != undefined && infoTerminalClearStoreVo != null){
	                    var h1 = "100005";		//合约款的fee_code
	                    var h2 = "4310";		//合约预存款的fee_code
	                    $.each(message.args.fee_info, function(i, item) {
	                    	if(item.fee_code == h1 || item.fee_code == h2){
	                    		item.discount_flag = "1";
	                    	}
	                    });
	                    
	                    var yh = {"fee_code":"清库优惠价CODE","fee_name":"清库优惠价", "raw_amount":-infoTerminalClearStoreVo.discount_charge, "rec_amount":-infoTerminalClearStoreVo.discount_charge, 
	                    			"discount_flag":"1"};
	                    message.args.fee_info.push(yh);
	                    
	                    discountAmount = -parseFloat(infoTerminalClearStoreVo.discount_charge);
                    }
					 
					 $.each(message.args.fee_info, function(i, item) {
						var data = JSON.stringify(item);
						if(item.fee_name=="[预存]" && tele_type=="4G" && parseInt(select_mon_limit)>0	){
								 item.fee_name ="[靓号预存]";
							 }
						if(item.discount_flag=='1'){//不可减免	
						 $("#fee_list").append('<li class="list bold">'+item.fee_name+'</li><li class="list"><div class="left"><div class="left_lable">应收：<span class="bold">'+	    				
						 item.rec_amount+'</span>&nbsp;元</div><div class="right_data">实收：<input type="text" readonly="true" id="charge_item'+i+'" name="'+item.fee_name+'" onChange="CheckChargeItem(this,1);" data="'+item.rec_amount+'" class="input_text width_8 text_normal_b" value="'+	    				
						 item.rec_amount+'">&nbsp;元</div></div></li><div class="line_dashed_h"></div>');
						}else if(item.discount_flag=='2'){//不可小于该值	
							 $("#fee_list").append('<li class="list bold">'+item.fee_name+'</li><li class="list"><div class="left"><div class="left_lable">应收：<span class="bold">'+	    											
							 item.rec_amount+'</span>&nbsp;元</div><div class="right_data">实收：<input type="text" id="charge_item'+i+'" name="'+item.fee_name+'" onChange="CheckChargeItem(this,2);" data="'+item.rec_amount+'" class="input_text width_8 text_normal_b" value="'+		    						    				
							 item.rec_amount+'">&nbsp;元</div></div></li><div class="line_dashed_h"></div>');
						}else{
							 $("#fee_list").append('<li class="list bold">'+item.fee_name+'</li><li class="list"><div class="left"><div class="left_lable">应收：<span class="bold">'+	    											
							 item.rec_amount+'</span>&nbsp;元</div><div class="right_data">实收：<input type="text" id="charge_item'+i+'" name="'+item.fee_name+'" onChange="CheckChargeItem(this,1);" data="'+item.rec_amount+'" class="input_text width_8 text_normal_b" value="'+		    						    				
							 item.rec_amount+'">&nbsp;元</div></div></li><div class="line_dashed_h"></div>');
						}
							fee_all = parseFloat(item.rec_amount) + parseFloat(fee_all);
						
					 });
					 
					 $("#fee_all").html("&nbsp;&nbsp;&nbsp;"+fee_all);
				}				
			}
			
		});	
}

function getTotalFee(){
	fee_all =0;
	$.each(fee_info, function(i, item) {
		var test = "#charge_item"+i;
		fee_all =  parseFloat($("#charge_item"+i).val()) + parseFloat(fee_all);
	});
	$("#fee_all").html("&nbsp;&nbsp;&nbsp;"+fee_all);
}

function CheckChargeItem(e,flag){
	var div = $(e);
	var fee_pre = div.attr("data");	
	
	if(flag==1){
		if(parseFloat(e.value)>parseFloat(fee_pre)){
			$.alert("实收不能大于应收")
			e.value = parseFloat(fee_pre);	
		}	
	}else if(flag==2){
		if(parseFloat(e.value)<parseFloat(fee_pre)){
			$.alert("实收不能小于应收")
			e.value = parseFloat(fee_pre);	
		}
	}
	if(parseFloat(e.value)<0){
		e.value = 0;	
	}
	
	 var re = /^[0-9]+.?[0-9]*$/;   //判断字符串是否为数字     //判断正整数 /^[1-9]+[0-9]*]*$/  
		
	 if (!re.test(e.value))
	{
		 $.alert("请输入数字");
		 e.value = 0;
	 }
	
	getTotalFee();

}


/*费用 相关JS ==============结束=====================*/

function OrderUpdate(order_all_json,i){
 
	 var order_json_string = JSON.stringify(order_all_json[i].order_json);
	 //$.alert(order_json_string);
	 var data1={	
				"order_id":$("#order_id").val(),
				"page_code":order_all_json[i].page_code,	
				"order_json":order_json_string
			};
	 var URL = application.fullPath + "authority/accountOpen/orderInfoAttrUpdate";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:order_all_json[i].hint,
			success:function(message){					 
				if(message.type == "error"){	
					$.alert(message.content);
				}else{						
					if(i<order_all_json.length-2){
						i++;
						OrderUpdate(order_all_json,i);
					}else{
						if(province_code=="nx" || province_code == "cq"){
							orderCommit();
						}else{
							uploadFormPdf();
						}
					}
				}				
			}			
		});		
}

function orderCommit(){
	 var data1={	
				"order_id":$("#order_id").val(),
				"bill_type":"100"
			};
	 var URL = application.fullPath + "authority/accountOpen/orderInfoStatusUpdate";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"订单提交中...",
			success:function(message){					 
				if(message.type == "error"){	
					$.alert(message.content);
				}else{	
					addMask();
					var noHtml = '<div id="showLoadNotice" class="" style="display:block; position: absolute;z-index: 91000;padding: 10px 30px 10px 30px;top:40%;background: url(\''+application.basePath+'images/loading_bg.png\') repeat;border:1px solid #279DE5;border-top:10px solid #279DE5;"><table><tr height="25px"><td align="center" vertical-align="center" style="font-size:14px;font-weight:bold;">页面加载中...</td></tr><tr height="20px"><td align="center" vertical-align="center"><img src="'+application.basePath+'images/loading.gif"/></td></tr></table></div>';
					var showLoad = $("#showLoadNotice");
					if(showLoad.length > 0){
						showLoad.remove();
					}
					$('body').append(noHtml);
					var showLoad2 = $("#showLoadNotice");
					showLoad2.css({"left":((($(window).width() - showLoad.width()) / 2 - 100)+ "px"),"top":((getScrollTop() + 220) +"px")});
					window.open(application.fullPath+"authority/dealShowOrder/showOrder?order_id="+$("#order_id").val()+"&pcFlag=1","_self");
				}				
			}			
		});	
}


function getAllOrderJson(){
	
	var pay_type ='';
	if(tele_type=="2G"){
		pay_type=$("#pay_type_mobile_2G").val();
	}else if(tele_type=="3G"){
		pay_type=$("#pay_type_mobile_3G").val();
	}else if(tele_type=="4G"){
		pay_type=$("#pay_type_mobile_4G").val();
	}
	
	var order_json_tmp = {
			"pos_sn": pos_sn,
			"pay_type": "10",
			"pay_type_code": "10",
			"fee_info": []
	};
	
	//for循环为订单更新添加内容
	for (var i = 0; i < fee_info.length; i++) {
		if(fee_info[i].fee_code != "清库优惠价CODE"){
			//服务器费用只支持字符
			var fee_result = {
				"fee_code": fee_info[i].fee_code,
				"fee_class":fee_info[i].fee_class,
				"fee_name": fee_info[i].fee_name,
				"rec_amount":fee_info[i].rec_amount + "",
				"rel_amount":parseFloat($("#charge_item"+i).val())+ "" ,
				"discount_flag": fee_info[i].discount_flag
			};
			order_json_tmp.fee_info.push(fee_result);
		}
	
	}
	
	var dataTariff = {};
	if(combined_product_id!=''){
		dataTariff = {"order_json":{
			"deal_flag":data_group.deal_flag==null ? "" :data_group.deal_flag,
			"combined_product":data_group.product_id==null ? "" :data_group.product_id,
			"product_id":data_group.ofr_id==null ? "" :data_group.ofr_id,
			"product_name":data_group.ofr_name==null ? "" :data_group.ofr_name,
			"prepay_flag":pay_flag==null ? "1" :pay_flag,
			"terminal_brand":terminal_brand==null ? "" :terminal_brand,
			"terminal_id":mobile_no==null ? "" :mobile_no,
			"terminal_brand_code":brand==null ? "" :brand,
			"terminal_model_code":model==null ? "" :model,					
			"first_month_fee":$("#first_month_fee_select_group").val()==null ? "" :$("#first_month_fee_select_group").val(),
			"activity_name":data_group.activity_name==null ? "" :data_group.activity_name,
			"activity_id":data_group.activity_id==null ? "" :data_group.activity_id,

			"activity_type":data_group.activity_type==null ? "" :data_group.activity_type,
			"activity_desc":data_group.activity_desc==null ? "" :data_group.activity_desc,
			"activity_type_name":data_group.activity_type_name==null ? "" :data_group.activity_type_name,
			"dev_code" : data_dev.dev_code == null ? "" :data_dev.dev_code,	//发展人代号
			"dev_name" : data_dev.dev_name == null ? "" :data_dev.dev_name,	//发展人名字
			"chnl_code" : data_dev.chnl_code == null ? "" :data_dev.chnl_code,	//渠道类型
			"wt_flag": wt_flag,
			"ori_oper_no": ori_oper_no,
			"clear_discount_amount":discountAmount.toFixed(2),
			"big_agent_flag" : data_dev.big_agent_flag == null ? "" :data_dev.big_agent_flag
		},"page_code":0,"hint":"资费信息更新中..."}
	}else{
		dataTariff = {"order_json":{
			"product_id":data_tariff.product_id==null ? "" :data_tariff.product_id,
			"product_name":data_tariff.product_name==null ? "" :data_tariff.product_name,
			"market_price":data_tariff.market_price==null ? "" :data_tariff.market_price,
			"prepay_flag":pay_flag==null ? "1" :pay_flag,
			"terminal_brand":terminal_brand==null ? "" :terminal_brand,
			"terminal_id":mobile_no==null ? "" :mobile_no,
			"terminal_brand_code":brand==null ? "" :brand,
			"terminal_model_code":model==null ? "" :model,
			"chk_product_id": data_ywb.chk_product_id==null ? "" : data_ywb.chk_product_id,
			"chk_product_name": data_ywb.chk_product_name==null ? "" :data_ywb.chk_product_name,
			"chk_market_price":data_ywb.chk_market_price==null ? "" :data_ywb.chk_market_price,								
			"first_month_fee":$("#first_month_fee_select").val()==null ? "" :$("#first_month_fee_select").val(),
			"activity_name":data_activity.activity_name==null ? "" :data_activity.activity_name,
			"activity_id":data_activity.activity_id==null ? "" :data_activity.activity_id,

			"activity_type":data_activity.activity_type==null ? "" :data_activity.activity_type,
			"activity_desc":data_activity.activity_desc==null ? "" :data_activity.activity_desc,
			"activity_type_name":activity_type_name==null ? "" :activity_type_name,	
			"guarantee_type":$("#guarantee_type_select").val()==null ? "" :$("#guarantee_type_select").val(),
			"product_desc":data_tariff.product_desc==null ? "" :data_tariff.product_desc,
			"packageId":data_giftActivity.package_id==null ? "" :data_giftActivity.package_id,
			"elementId":discnt_code==null ? "" :discnt_code,
			"elementType":data_giftActivity.element_type==null ? "" :data_giftActivity.element_type,
			"ywb_product_id":data_ywb.ywb_product_id==null ? "" : data_ywb.ywb_product_id,		
			"discnt_name":data_giftActivity.discnt_name==null ? "" :data_giftActivity.discnt_name,
			"dinner_service":data_ywb.para_value1==null ? "" :data_ywb.para_value1,
			"dinner_service_flag":data_ywb.dinner_service_flag==null ? "" :data_ywb.dinner_service_flag,
			"dev_code" : data_dev.dev_code == null ? "" :data_dev.dev_code,	//发展人代号
			"dev_name" : data_dev.dev_name == null ? "" :data_dev.dev_name,	//发展人名字
			"chnl_code" : data_dev.chnl_code == null ? "" :data_dev.chnl_code,	//渠道类型				
			"main_card_phonenumber":main_card_phonenumber,//3G副卡  主卡号
			"wt_flag": wt_flag,
			"ori_oper_no": ori_oper_no,
			"clear_discount_amount":discountAmount.toFixed(2),
			"big_agent_flag" : data_dev.big_agent_flag == null ? "" :data_dev.big_agent_flag	//大代理商
		},"page_code":0,"hint":"资费信息更新中..."}
	}
	//新客户写订单属性
	var newCustInfo = {};
	if(cust_id == "" || cust_id == null){
		newCustInfo = {"CertType":"02",
				"CertNum":$('#id_number').text,
				"CustomerName":$('#customer_name').text
					}
	}
	var FeeInfo = [];
	$.each(fee_info, function(i, item) {
		var obj = $('#charge_item' + i);
		var discount = (parseFloat(obj.attr('data')) - parseFloat(obj.val()));
		var fee = {
			'FeeID': fee_info[i].fee_code,
			'OrigFee': (parseFloat(obj.attr('data')) * 100) + "",
			'ReliefFee': (discount * 100) + "",
			'RealFee': (parseFloat(obj.val()) * 100) +"",
			'fee_name': obj.attr('name')
		};
		FeeInfo.push(fee);
	});
	
	if(province_code == "cq"){
		order_all_json=[
						 dataTariff,
					 {"order_json":{"terminal_brand_code":brand,
							"terminal_model_code":model,
							"channel_name":current_channel_name, //本厅
							"property_name":property_name,   //机型
							"chnl_code":current_channel_id,
							"property_code":property_code,
							"pre_ting":pre_ting//从哪个厅
							},"page_code":0,"hint":"终端信息更新中...."},
					
					 {"order_json":{
							"acc_nbr": select_acc_nbr,
							"tele_type": tele_type,
							"acc_nbr_fee": select_first_prepay,
							"low_fee": select_mon_limit,
							"class_id": select_class_id
						},"page_code":2,"hint":"号码信息更新中..."},		
						
						{"order_json": {
							OrigTotalFee:(fee_all*100)+"",
							  NewCustomerInfo:newCustInfo == null ? "":JSON.stringify(newCustInfo),	
									  FeeInfo:FeeInfo == null ? "" : JSON.stringify(FeeInfo),
							  cust_seq_id: cust_seq_id,
							  contact_adress:trim($("#connect_addr").val()),
							  contact_phone: $("#connect_phone").val(),
							  cust_id : cust_id==null ? "" :cust_id
						  },"page_code":7,"hint":"资料信息更新中..."},	
						  
						  {"order_json": order_json_tmp
							  ,"page_code":5,"hint":"费用信息更新中..."},
							  
							  {"order_json": {"bill_send":'',
								  "logistics_type":"前台",
								  "send_content":'',
								  "invoice_code":'',
								  "essKey":'',
								  "ofr_sub_type_3g":'',
								  "pro_order_id":''},"page_code":6,"hint":"交付信息更新中..."},	
							];
	}else{
		order_all_json=[
						 dataTariff,
						
					 {"order_json":{"terminal_brand_code":brand,
							"terminal_model_code":model,
							"channel_name":current_channel_name, //本厅
							"property_name":property_name,   //机型
							"chnl_code":current_channel_id,
							"property_code":property_code,
							"pre_ting":pre_ting//从哪个厅
							},"page_code":0,"hint":"终端信息更新中...."},
					
					 {"order_json":{
							"acc_nbr": select_acc_nbr,
							"tele_type": tele_type,
							"acc_nbr_fee": select_first_prepay,
							"low_fee": select_mon_limit,
							"class_id": select_class_id
						},"page_code":2,"hint":"号码信息更新中..."},		
						
						{"order_json": {
							  cust_seq_id: cust_seq_id,
							  contact_adress:trim($("#connect_addr").val()),
							  contact_phone: $("#connect_phone").val(),
							  cust_id : cust_id==null ? "" :cust_id
						  },"page_code":7,"hint":"资料信息更新中..."},	
						  
						  {"order_json": order_json_tmp
							  ,"page_code":5,"hint":"费用信息更新中..."},
							  
							  {"order_json": {"bill_send":'',
								  "logistics_type":"前台",
								  "send_content":'',
								  "invoice_code":'',
								  "essKey":'',
								  "ofr_sub_type_3g":'',
								  "pro_order_id":''},"page_code":6,"hint":"交付信息更新中..."},	
							];
	}

}

function uploadFormPdf(){
	 var data1={	
				"order_id":$("#order_id").val()
			};
	 var URL = application.fullPath + "authority/accountOpen/uploadFormPdf";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"免填单生成处理中...",
			success:function(message){					 
				if(message.type == "error"){	
					$.alert(message.content);
				}else{	
					orderCommit();
				}				
			}			
		});	
}

function checkPosSn(){	
	var pay_type ='';
	if(tele_type=="2G"){
		pay_type=$("#pay_type_mobile_2G").val();
	}else if(tele_type=="3G"){
		pay_type=$("#pay_type_mobile_3G").val();
	}else if(tele_type=="4G"){
		pay_type=$("#pay_type_mobile_4G").val();
	}
	 if(pay_type=="16"){
		pos_sn = $("#pos_sn").val();
		if(pos_sn ==""){
			 $.alert("支付方式为旧POS刷卡时输入的流水号不能为空!");
			 return;
		 }
	 }else{
		 getAllOrderJson();
		 OrderUpdate(order_all_json,0);
		 return;
	 }	 
	 
	 var data1={	
			 pos_sn: pos_sn,	
			 "order_id":$("#order_id").val()	
			};
	 var URL = application.fullPath + "authority/accountOpen/get_pospudateinfo";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"流水号校验中...",
			success:function(message){					 
				if(message.type == "error"){	
					$.alert(message.content);
				}else{	
					getAllOrderJson();
					OrderUpdate(order_all_json,0);
				}				
			}			
		});	
}


/**
 * 开户资料 验证表单
 */
function checkInput(){	 
	var connect_phone = $("#connect_phone").val();
	var contact_adress = $("#connect_addr").val();	
	var flag = true;	
	var phoneRegex = /^(13[0-9]|15[012356789]|18[023456789]|17[02356789]|14[57]|17[0])[0-9]{8}$/;
	var phoneRegex2 = /^(0[0-9]{2,3})?([0-9]{7,8})$/;
	var adressRegex= /[\u4E00-\u9FA5]/g;		 
		
	if(!phoneRegex.test(connect_phone) && !phoneRegex2.test(connect_phone)){
		flag = false;
		$.alert("联系电话请输入固话或手机号码!");				
	}else{
		if($("#connect_addr").val()== "" || $("#connect_addr").val()== null){
			$.alert("联系地址不能为空!");
			flag = false;
		}else{
			var addressMatch = contact_adress.match(adressRegex);
			if(addressMatch&&addressMatch.length){
				var chinaLength = addressMatch.length; //返回中文的个数   
				if(chinaLength < 7){												
					$.alert("联系地址至少输入7个汉字!");
					flag = false;
				}
			}else{
				flag = false;		
				$.alert("联系地址至少输入7个汉字!");
			}
		}
	}			
		return flag;
};	

function replaceCheck(){
	var contact_adr = $.trim($("#connect_addr").val());	


	var reg=new RegExp("#","g");
	var newstr=contact_adr.replace(reg,"-");
	document.getElementById("connect_addr").value=newstr;
	
}
function haoduanSelect(tele_type){
	var fuzzy_query = $.trim($("#fuzzy_query").val());
	var mob_section;
	 mob_section = $.trim($("#mob_section option:selected").val());
	 
	  if(mob_section=="185"||mob_section=="186"||mob_section=="176"){
			$("#show3G").show();
			$("#show2G").hide();
		}else{
			$("#show2G").show();
			$("#show3G").hide();
		}
		if(mob_section=="10000"){
			$("#show2G").hide();
			$("#show3G").hide();
		}
	 
	if(mob_section=="10000"){
		if(fuzzy_query=="请输入后1-8位数字查询号码"){
			$("#fuzzy_query").val("请输入1-11位数字查询号码");
		}
	}else{
		if(fuzzy_query=="请输入1-11位数字查询号码"){
			$("#fuzzy_query").val("请输入后1-8位数字查询号码");
		}
	} 
}

function haoduanOnblur(){
	if($("#fuzzy_query").val()==null||$("#fuzzy_query").val()==""){
		 mob_section = $.trim($("#mob_section option:selected").val());
		if(mob_section=="10000"){
			$("#fuzzy_query").val("请输入1-11位数字查询号码");
		}else{
			$("#fuzzy_query").val("请输入后1-8位数字查询号码");
		} 
	}
}

//3G副卡 主卡号输入框
var mainNumber="";
function checkNumber(value){
	var mainPhonenumber="";
	mainNumber=value;
	if(value != ""){
		mainPhonenumber=value;
		//mainNumber=$("#main_card_number").val();
		var phoneRegex =  /^(13[0-9]|15[012356789]|18[02356789]|14[57]|17[0])[0-9]{8}$/;//手机号验证
		if(mainPhonenumber!==""&&phoneRegex.test(mainPhonenumber)){
			$('#ywb_sure').removeClass('input_button_disabled').addClass('input_button');
		}else{
			$('#ywb_sure').removeClass('input_button').addClass('input_button_disabled');
		} 
	}
}

//默认发展人
function GetDevName(){


	//广西发展人设置为空
//	if(province_code == "gx"){
//	data_dev = "";
//	}
	//宁夏,广西默认发展人
	if(province_code == "gx"){
		var URL = application.fullPath + "authority/accountOpen/getAgentDeveloper";
		$.ajax({
			url:URL,
			type:'post',
			//waitMsg:"发展人查询中..",
			success:function(message){			
				$("#dev_list").html('');
				if(message.type == "error"){					
					$.alert(message.content);
				}else{
					if(message.args.infoAgentDeveloper != null && message.args.infoAgentDeveloper != undefined){
						data_dev = message.args.infoAgentDeveloper; 
					}
				}
				
				if(message.args.infoAgentDeveloper != null && message.args.infoAgentDeveloper != undefined){		
					data_dev = message.args.infoAgentDeveloper
					var agent_flag = message.args.dev_info;				
					if(agent_flag.channel_run_type == "01"){
						dev_name_model = data_dev.dev_name;
		       	 		$("#dev_div").hide();   
		       	 	    $("#mode_scene_4").hide(); 
		       	 	}else{
		       	 		data_dev="";		       	 	  
		       	 	}
				}
				
			}
	
		});
	}
	else if(province_code == "nx" && ms_flag == "1"){		//宁夏
		var URL = application.fullPath + "authority/accountOpen/getDefaultAgentDeveloperBySceneType";
		var data1 = {
				 "scene_type":"tariff"
			};
		$.ajax({
			url:URL,
			type:'post',
			//waitMsg:"发展人查询中..",
			data:data1,
			success:function(message){
				$("#dev_list").html('');
				if(message.type == "error"){					
					$.alert(message.content);
				}else{
					if(message.args.infoAgentDeveloper != null && message.args.infoAgentDeveloper != undefined){
						data_dev = message.args.infoAgentDeveloper; 
						data_dev.big_agent_flag = "1";
						dev_name_model = data_dev.dev_name;
						
						document.getElementById("dev_name").value =  data_dev.dev_name + '('+data_dev.chnl_name+')';
					}
	       	 	}
				
			}
	
		});
	}
	else if(province_code == "nx" && ms_flag != "1"){
		var URL = application.fullPath + "authority/accountOpen/getAgentDeveloper";
		$.ajax({
			url:URL,
			type:'post',
			//waitMsg:"发展人查询中..",
			success:function(message){			
				$("#dev_list").html('');
				if(message.type == "error"){					
					$.alert(message.content);
				}else{
					if(message.args.infoAgentDeveloper != null && message.args.infoAgentDeveloper != undefined){
						data_dev = message.args.infoAgentDeveloper;
						bind_dev = message.args.infoAgentDeveloper;
						
						document.getElementById("dev_name").value =  data_dev.dev_name + '('+data_dev.chnl_name+')';
					}
					var dev_info = message.args.dev_info;			
					if(dev_info.channel_run_type == "02" || ms_flag == "2"){
						dev_select_flag = "1";
					}
					
				}
			}
	
		});
	}
	else{
		$("#dev_div").hide();
	}
}


//快速业务办理默认发展人
function GetDevNameBusi(){
	if(province_code == "gx"){
		var URL = application.fullPath + "authority/accountOpen/getAgentDeveloper";
		$.ajax({
			url:URL,
			type:'post',
			//waitMsg:"发展人查询中..",
			success:function(message){			
				if(message.type == "error"){					
					$.alert(message.content);
				}else{
					if(message.args.infoAgentDeveloper != null && message.args.infoAgentDeveloper != undefined){
						data_dev = message.args.infoAgentDeveloper
						var agent_flag = message.args.dev_info;				
						if(agent_flag.channel_run_type == "01"){
							dev_name_model = data_dev.dev_name;
		           	 		$("#agent_flag").hide();        		
		           	 	}else{
		           	 		data_dev="";
		           	 	}
					}
				}
			}
		});
	}
	else if(province_code == "nx" && ms_flag == "1"){		//宁夏
		var URL = application.fullPath + "authority/accountOpen/getDefaultAgentDeveloperBySceneType";
		var data1 = {
				 "scene_type":"tariff"
			};
		$.ajax({
			url:URL,
			type:'post',
			//waitMsg:"发展人查询中..",
			data:data1,
			success:function(message){			
				$("#dev_list").html('');
				if(message.type == "error"){					
					$.alert(message.content);
				}else{
					if(message.args.infoAgentDeveloper != null && message.args.infoAgentDeveloper != undefined){
						data_dev = message.args.infoAgentDeveloper; 
								
						dev_name_model = data_dev.dev_name;
						
						document.getElementById("dev_name_busi").value =  data_dev.dev_name + '('+data_dev.chnl_name+')';
					}
	       	 	}
				
			}
	
		});
	}
	else if(province_code == "nx" && ms_flag == "0"){
		var URL = application.fullPath + "authority/accountOpen/getAgentDeveloper";
		$.ajax({
			url:URL,
			type:'post',
			//waitMsg:"发展人查询中..",
			success:function(message){			
				if(message.type == "error"){					
					$.alert(message.content);
				}else{
					if(message.args.infoAgentDeveloper != null && message.args.infoAgentDeveloper != undefined){
						data_dev = message.args.infoAgentDeveloper;
					}
				}
			}
		});
	}
	else{
		$("#agent_flag").hide();
	}
}

//查询发展人
function QueryDevName(flag){
	dev_busi_flag = flag;
	//广西可以选择发展人
	if(province_code == "gx" || (province_code == "nx" && ms_flag == "0" && dev_select_flag != "1")){
	 var dev_name = $("#dev_name_input").val();
	 
	 
	 var data1 = {

			 "dev_name":dev_name
		};
	 var URL = application.fullPath + "authority/accountOpen/queryAgentDevelopers";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"发展人查询中..",
			success:function(message){			
				$("#dev_list").html('');
				if(message.type == "error"){					
					$.alert(message.content);
				}else{					
					showdiv('dev_option');
					$.each(message.args.devList, function(i, item) {
						var data = JSON.stringify(item);
						$("#dev_list").append('<li class="list"><div class="left"><div class="left_lable">'+item.dev_name+'('+item.chnl_name+')</div><div class="right_data">'
							  +' <input name="dev_search_check" onClick="devSelect(this)" type="radio" data=\''+data+'\' id=\''+item.dev_code+'\'></input></div></div></li>');
					
					});
				}
			}
			

		});
	}
	else if((province_code == "nx" && ms_flag == "1") || dev_select_flag == "1"){		//宁夏
		var dev_name = $("#dev_name_input").val();
		 
		 
		 var data1 = {
				 "dev_name":dev_name,
				 "scene_type":"tariff"
			};
		 var URL = application.fullPath + "authority/accountOpen/queryAgentDevelopersBySceneType";
			$.ajax({
				url:URL,
				dataType:'json',
				contentType: "application/x-www-form-urlencoded; charset=utf-8", 
				type:'post',
				data:data1,
				waitMsg:"发展人查询中..",
				success:function(message){			
					$("#dev_list").html('');
					if(message.type == "error"){					
						$.alert(message.content);
					}else{					
						showdiv('dev_option');
						
						if(dev_select_flag == "1" && bind_dev != ""){
			        		bind_dev.big_agent_flag = "";
			        		var data = JSON.stringify(bind_dev);
			        		$("#dev_list").append('<li class="list"><div class="left"><div class="left_lable">'+bind_dev.dev_name+'('+bind_dev.chnl_name+')</div><div class="right_data">'
									  +' <input name="dev_search_check" onClick="devSelect(this)" type="radio" data=\''+data+'\' id=\''+bind_dev.dev_code+'\'></input></div></div></li>');
			        	}
						
						$.each(message.args.devList, function(i, item) {
							item.big_agent_flag = "1";
							var data = JSON.stringify(item);
							$("#dev_list").append('<li class="list"><div class="left"><div class="left_lable">'+item.dev_name+'('+item.chnl_name+')</div><div class="right_data">'
								  +' <input name="dev_search_check" onClick="devSelect(this)" type="radio" data=\''+data+'\' id=\''+item.dev_code+'\'></input></div></div></li>');
						
						});
					}	
				}
				

			});
	}
	
}

function devSelect(e){
	var div = $(e);
	var dataStr = div.attr("data");	
	data_dev = JSON.parse(dataStr);	
}

function devConfirm(){
	if(data_dev.dev_name == undefined){
		$.alert('请选择一个发展人');
	}
	else{
		hidediv('dev_option');
		if(dev_busi_flag == "1"){
			document.getElementById("dev_name_busi").value =  data_dev.dev_name + '('+data_dev.chnl_name+')';
			dev_name_model =  data_dev.dev_name;
		}else{
			document.getElementById("dev_name").value =  data_dev.dev_name + '('+data_dev.chnl_name+')';	
			dev_name_model =  data_dev.dev_name;
			
			if(province_code == "nx" && (ms_flag == "1" || data_dev.big_agent_flag == "1")){
				tariffDelete();
			}
		}		
	}	
}

function devDelete(){
	hidediv('dev_option');
}


function OrderUpdatedate(data){
	data=JSON.stringify(data);
	 var data1={	
				"order_id":$("#order_id").val(),
				"page_code":"0",	
				"order_json":data
			};
	 var URL = application.fullPath + "authority/accountOpen/orderInfoAttrUpdate";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			//waitMsg:order_all_json[i].hint,
			success:function(message){					 
				if(message.type == "error"){	
					$.alert(message.content);
					return;
				}			
			}			
		});		
}
function checkIMEI(mobileNo){//购机直降终端串号校验

	if(mobileNo ==null || mobileNo ==""){
		 $.alert("串号不能为空");
		return;
	}
	 var URL = application.fullPath +"authority/mkt/checkIMEI";
	 var data ={
		"mobile_series":mobileNo,
		"purch_type":"3"
	 }
	   $.ajax({
			type : "POST",
			url : URL,
			data : data,
			dataType:'json',
			waitMsg:"正在校验串号...",
			success:function(msg){
				var jsonObj=eval(msg.args);
				var desc = jsonObj.rsp_desc;		
			    if("000" == jsonObj.err_code){
			    	
			    	mobile_no=mobileNo;
			    	var dialog=$.dialog({
						   title:'提示',//提示title
						   content:'串号可用',
						   closed: false,
						   cache: false,
						   buttons:[{id:'btn_ok',text:'确定',
								   onClick:function(){									   
									   dialog.close();
									 AutoSelectTariff();//组合套餐查询
								   }								   
								}]
						});
			    
			    	
			    }else if("001" == jsonObj.err_code){
			    	 $.alert("串号未入库");
			    		return;
			    }else{
			    	 $.alert(desc);
			    		return;
			    }	
				
				
		},		
 	   error:function(message){
 		 $.alert("校验失败:"+message.args.rsp_desc);
 		return;
		}
   });

	
}
//客户校验增加BSS信息录入
function addLogIdValidity(){
	if(!logIdValidityVo){
		return;	
	}	
	var reg=new RegExp("-","g");    //日期格式为YYYYMMDD
	var data1 ={
			"id_type":logIdValidityVo.id_type,
			"id":logIdValidityVo.id_number,
			"name":logIdValidityVo.customer_name,
			"sex":logIdValidityVo.cust_sex,
			"nation":logIdValidityVo.nation,
			"birthday":logIdValidityVo.born_date.replace(reg,""),
			"address":logIdValidityVo.auth_adress,
			"auth_dept":logIdValidityVo.office,
			"valid_period":logIdValidityVo.auth_end_date.replace(reg,""),
			"oper_no":oper_no,
			"oper_dept_no":oper_dept_no
	} 
	
	 var URL =application.fullPath +"rest/customerVerify/addLogIdValidity";
	 $.ajax({
			type : "POST",
			url : URL,
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			dataType:'json',
			data : data1,
			waitMsg:"...",
			success:function(msg){
				console.info("身份验证信息录入接口返回");
				console.info(msg);
				}
			});
}

function pcRspTimeStart(order_id, page_sub_type, page_name){
	if(province_code == "gx"){
		var data = {
			"order_id":order_id,
			"page_url":window.location.pathname,
			"page_name":page_name,
			"page_sub_type":page_sub_type,
			"program_source":"PC"
		};
		$.post(application.fullPath + "authority/pageRspTime/rspTimeStart", data);
		
		cur_step = page_sub_type;
	}
}

function pcRspTimeEnd(order_id){
	if(province_code == "gx"){
		var data = {
			"order_id":order_id,
			"page_url":window.location.pathname,
			"page_sub_type":cur_step,
			"program_source":"PC"
		};
		$.post(application.fullPath + "authority/pageRspTime/rspTimeEnd", data);
	}
}
