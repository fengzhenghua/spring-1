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
var oper_no = '';
var dept_no = '';
var card_no ='';
var idType ="02";
var teleTypeChngFlag ="0";
var cust_id='';
var oper_id = '';
var area_id = '';
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

var num_infos = null;//获取费用返回的号码信息

var m_reference = ""; //交易流水
var broadBandInstallMode = "0"; // 0:宽带新装；1:宽带纳入
var broadBandYwNumber = "";//宽带为纳入时存储宽带业务号码
var broadBandproductId = "";//宽带编码
var broadBandexchCode = "";//纳入时存储局向编码
var finishFlag = true;//无写卡号码时为true 直接完成订单 要写卡时为false
var _spendLevel = "12";
var _cbssAccessType = "";

$(document).ready(function() {
	oper_id = $("#oper_id").val();
	area_id = $("#area_id").val();
	province_code = $("#province_code").val();
	write_way = $("#write_way").val();
	ms_flag = $("#ms_flag").val();
	
	// 0:模拟写卡;1:正式写卡
	if (write_way == "1") {
		$("#btn_load_test").hide();
	}
	
	$(".scroll_v").bind("mousewheel", function(event) {
		return false;
	});
	
	/* step1 ==============开始================== */
	$('#connect_phone').bind('keyup', function() {
		checkStep1Next();
	});
	
	$('#connect_addr').bind('keyup', function() {
		checkStep1Next();
	});
	
	$("#step1_next").bind("click",function(){
		if ($('#step1_next_div').hasClass('ok_disabled')) {
			return;
		}
		$('#step1').hide();
		$('#step2').show();
		window.location.href = "#tab_taocan";
	});
	/* step1 ==============结束================== */
	
	/* step2 ==============开始================== */
	$("#mob_section").bind("change", function() {
		haoduanSelect(tele_type);
	});
	/*$("#mob_section_3G").bind("change",function (){
		haoduanSelect(tele_type);	
	});
	$("#mob_section_4G").bind("change",function (){
		haoduanSelect(tele_type);	
	}); */
	
	$('#number_cancel').bind('click', function(){
		$('#new_phone_info').hide();
		$('#step2_info').show();
		window.location.href = "#tab_taocan";
	});
	
	$('#old_phone_info').on('click', '.list_old_number', function() {
		if ($(this).hasClass('num_selected')) {
			return;
		}
		$(this).find('input[type="radio"]').prop('checked', true);
		//23G号码添加校验
		var tele_type = $(this).attr("tele_type"); 
		if(tele_type == '2G' || tele_type == '3G'){
			var device_number = $(this).attr("device_number"); 
			check23To4(device_number);
		}
		
		checkOldPhoneConfirm();
	});
	
	$('#old_phone_confirm').bind('click', function() {
		if ($('#old_phone_confirm_div').hasClass('ok_disabled')) {
			return;
		}
		// 删除已选的老号码
		if (modifyOldPhoneNumber != '') {
			$('#number_' + curNumIndex).text('');
			$('#number_' + curNumIndex).attr('installMode', '');
			$('#number_' + curNumIndex).removeClass('num_old'); // 取消老号码标识
			var modifyOldPhone = $('#old_phone_info .list_old_number[device_number="' + modifyOldPhoneNumber + '"]');
			modifyOldPhone.removeClass('num_selected'); // 取消已选标识
			modifyOldPhone.find('input[type="radio"]').show(); // 显示单选按钮
			modifyOldPhoneNumber = '';
		}
		// 选中老号码
		var curRadio = $('[name="radio_old_phone"]:checked');
		var curOldPhone = curRadio.parents('.list_old_number');
		$('#number_' + curNumIndex).text(curOldPhone.attr('device_number'));
		$('#number_' + curNumIndex).attr('installMode', curOldPhone.attr('business_flag')); // 0:新装；1:纳入；2:迁转
		$('#number_' + curNumIndex).addClass('num_old'); // 增加老号码标识
		curOldPhone.addClass('num_selected'); // 增加已选标识
		curRadio.prop('checked', false); // 取消选中状态
		curRadio.hide(); // 隐藏单选按钮，不可再选
		$('#old_phone_confirm_div').removeClass('ok').addClass('ok_disabled');
		checkHasNumber();
		
		$('#old_phone_info').hide();
		$('#step2_info').show();
		window.location.href = "#tab_taocan";
	});
	
	$('#old_phone_cancel').bind('click', function(){
		modifyOldPhoneNumber = '';
		$('#old_phone_info').hide();
		$('#step2_info').show();
		window.location.href = "#tab_taocan";
	});
	
	$('#step2_next').bind('click', function() {
		if ($('#step2_next_div').hasClass('ok_disabled')) {
			return;
		}
		$('#step2').hide();
		$('#step3').show();
		window.location.href = "#tab_taocan";
		initstep3();
	});
	/* step2 ==============结束================== */
	
	/* step3 ==============开始================== */
	$('#step3_next').bind('click', function() {
		if ($('#step3_next_div').hasClass('ok_disabled')) {
			return;
		}
		$('#step3').hide();
		$('#step4').show();
		window.location.href = "#tab_taocan";
	});
	/* step3 ==============结束================== */
	
	/* step4 ==============开始================== */
	$('.lan_type_span').on('click', function() {
		$(this).find('input[type="radio"]').prop('checked', true);
		if ($('[name="radio_lan_type"]:first').is(':checked')) { // 宽带新装
			$('#old_lan_info').hide();
			$('#new_lan_info').show();
			window.location.href = "#tab_taocan";
			broadBandInstallMode = '0';
		}else if($('[name="radio_lan_type"]:last').is(':checked')){//宽带迁转
			$('#new_lan_info').hide();
			$('#old_lan_info').show();
			window.location.href = "#tab_taocan";
			broadBandInstallMode = '2';
		} 
		else { // 宽带纳入
			$('#new_lan_info').hide();
			$('#old_lan_info').show();
			window.location.href = "#tab_taocan";
			broadBandInstallMode = '1';
		}
	});
	
	$("#searchZjdz").bind("click", function() {
		var input_text = $("#zjdzInput").val()
		getZjdzName(input_text, false);
	});

	$("#searchKdzf").bind("click", function() {
		var input_text = $("#kdzfInput").val()
		getKdzfName(input_text, false);
	});
	
	$('#kdzf_connect_name').bind('keyup', function() {
		checkStep4Next();
	});
	
	$('#kdzf_connect_phone').bind('keyup', function() {
		checkStep4Next();
	});
	
	$('#old_lan_info').on('click', '.list_old_number', function() {
		$(this).find('input[type="radio"]').prop('checked', true);
		var liData = $(this);
		var device_number = liData.attr("device_number");
		if(broadBandInstallMode == "2"){//迁转先进行23转4校验
			$(this).attr("zb_product_id",device_number);
			_spendLevel = liData.attr("spendLevel");
			_cbssAccessType = liData.attr("cbssAccessType");
			
		}else{
			var productId = liData.attr("product_id");
			if(checkLanProductRelation(liData) == ""){
				return;
			}
		}
		checkOldLanConfirm();
		
	});
	
	$('#old_lan_confirm').bind('click', function() {
		if ($('#old_lan_confirm_div').hasClass('ok_disabled')) {
			return;
		}
		var curOldLan = $('[name="radio_old_lan"]:checked').parents('.list_old_number');
		curOldLan.siblings('.list_old_number').removeClass('num_selected'); // 取消已选标识
		curOldLan.addClass('num_selected'); // 增加已选标识
		$('#step4').hide();
		$('#step5').show();
		window.location.href = "#tab_taocan";
		initStep5();
	});
	
	$("#step4_next").bind("click",function(){
		if ($('#step4_next_div').hasClass('ok_disabled')) {
			return;
		}
		$('#step4').hide();
		$('#step5').show();
		window.location.href = "#tab_taocan";
		initStep5();
	});
	/* step4 ==============结束================== */
	
	/* step5 ==============开始================== */
	// 修改身份证信息
	$("#modify_idcard_info").bind("click",function(){
		$('#step1_next_div').hide();
		$('#step1_modify_div').show();
		$('#step1').show();
		$('#step5').hide();
		window.location.href = "#tab_taocan";
	});
	
	$("#step1_modify").bind("click",function(){
		if ($('#step1_modify_div').hasClass('ok_disabled')) {
			return;
		}
		setUserConfirmInfo(); // 用户信息
		$('#step1').hide();
		$('#step5').show();
		window.location.href = "#tab_taocan";
	});
	
	// 修改手机号码信息
	$("#modify_number_info").bind("click",function(){
		$('#step2_next_div').hide();
		$('#step2_modify_div').show();
		$('#step2').show();
		$('#step5').hide();
		window.location.href = "#tab_taocan";
	});
	
	$("#step2_modify").bind("click",function(){
		if ($('#step2_modify_div').hasClass('ok_disabled')) {
			return;
		}
		setPhoneConfirmInfo(); // 手机信息
		$('#step2').hide();
		$('#step5').show();
		window.location.href = "#tab_taocan";
	});
	
	// 修改宽带详情信息
	$("#modify_kdxq_info").bind("click",function(){
		if (broadBandInstallMode == '0') { // 宽带新装
			$('#step4_next_div').hide();
			$('#step4_modify_div').show();
		} else { // 宽带纳入
			$('#old_lan_confirm_div').hide();
			$('#old_lan_modify_div').show();
		}
		$('#step4').show();
		$('#step5').hide();
		window.location.href = "#tab_taocan";
	});
	
	// 修改客户联系方式信息
	$("#modify_contact_info").bind("click",function(){
		if (broadBandInstallMode == '0') { // 宽带新装
			$('#step4_next_div').hide();
			$('#step4_modify_div').show();
		} else { // 宽带纳入
			$('#old_lan_confirm_div').hide();
			$('#old_lan_modify_div').show();
		}
		$('#step4').show();
		$('#step5').hide();
		window.location.href = "#tab_taocan";
	});
	
	$("#step4_modify").bind("click",function(){
		if ($('#step4_modify_div').hasClass('ok_disabled')) {
			return;
		}
		setNewLanConfirmInfo(); // 新宽带信息
		$('#step4').hide();
		$('#step5').show();
		window.location.href = "#tab_taocan";
	});
	
	$("#old_lan_modify").bind("click",function(){
		if ($('#old_lan_modify_div').hasClass('ok_disabled')) {
			return;
		}
		setOldLanConfirmInfo(); // 老宽带信息
		var curOldLan = $('[name="radio_old_lan"]:checked').parents('.list_old_number');
		curOldLan.siblings('.list_old_number').removeClass('num_selected'); // 取消已选标识
		curOldLan.addClass('num_selected'); // 增加已选标识
		$('#step4').hide();
		$('#step5').show();
		window.location.href = "#tab_taocan";
	});
	
	// 修改智慧沃家套餐信息
	$("#modify_taocan_info").bind("click",function(){
		$('#step3_next_div').hide();
		$('#step3_modify_div').show();
		$('#step3').show();
		$('#step5').hide();
		window.location.href = "#tab_taocan";
	});
	
	$("#step3_modify").bind("click",function(){
		if ($('#step3_modify_div').hasClass('ok_disabled')) {
			return;
		}
		setSharePackageConfirmInfo(); // 智慧沃家套餐信息
		$('#step3').hide();
		$('#step5').show();
		window.location.href = "#tab_taocan";
	});
	
	$("#step5_next").bind("click",function(){
		if ($('#step5_next_div').hasClass('ok_disabled')) {
			return;
		}
		orderAffirm();
	});
	/* step5 ==============结束================== */
	
	/* step6 ==============开始================== */
	$("#step6_next").bind("click",function(){
		if ($('#step6_next_div').hasClass('ok_disabled')) {
			return;
		}
		feeAffirm();
	});
	/* step6 ==============结束================== */

	/* step7 ==============开始================== */
/*	$("#readCard").bind("click",function (){
		readCardBtn();					
	});
	$("#writeCard").bind("click",function (){
		writeCardBtn();					
	});*/
	/* step7 ==============结束================== */
	
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
	$("#btn_load").click("click", function() {
		var messageFlag = $("#messageFlag").val();
		if (messageFlag != '1') {
			$.alert("请选择读卡器!");
			return;
		}
		// 读卡未结束，不再次读卡
		if (strReadResult == null && !readError)
			return;
		strReadResult = null;
		// 调用空间的读卡方法
		try {
			strReadResult = CVR_IDCard.ReadCard();
		} catch (e) {
			$.alert("读卡错误,请检查您的控件或驱动是否正确安装最新版本");
			readError = true;
			return;
		}
		readError = false;
		// 读卡成功时，填充页面显示信息
		if (strReadResult == "0") {
			readCardSucc = "1"; // 读卡成功标示，传给上个页面
			fillForm();
			submit();
		} else {
			readCardSucc = "0"; // 读卡成功标示，传给上个页面
			$.alert("读卡错误,请移动身份证,进行读卡!");//错误原因描述strReadResult
		}
		checkStep1Next();
	});
	
	$("#btn_load_test").bind("click", function() {
		var test = {};
		test.Name = "某某某";
		test.Sex = "1";
		test.Nation = "汉";
		test.Born = "1990-01-01";
		test.Address = "测试地址重庆市渝中区石油路";
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
		$('#id_number').val(pCardNo);
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
	 

	var dialog = $.dialog({
		title : '提示',// 提示title
		content : '是否要生成新的订单',// 显示的内容，支持html格式。
		buttons : [ {
			id : 'btn_ok',
			text : '确定',
			onClick : function() {
				dialog.close();
				createOrderId();
			}
		}, {
			id : 'btn_cancel',
			text : '取消',
			onClick : function() {
				dialog.close();
				$("#step1").hide();
			}
		} ]
	});
	
});

//启用/禁用step1的【下一步】按钮
function checkStep1Next() {
	if ($('#id_number').val() != '' && $('#connect_phone').val() != '' && $('#connect_addr').val() != '') {
		$('#step1_next_div').removeClass('ok_disabled').addClass('ok');
		$('#step1_modify_div').removeClass('ok_disabled').addClass('ok');
	} else {
		$('#step1_next_div').removeClass('ok').addClass('ok_disabled');
		$('#step1_modify_div').removeClass('ok').addClass('ok_disabled');
	}
}

var curNumIndex = ''; // 当前正在操作的手机号码1~10
// 【选择新号】按钮事件
function numberSelect(numIndex) {
	curNumIndex = numIndex;
	$('#step2_info').hide();
	$('#new_phone_info').show();
}

//【纳入老号】按钮事件
function numberImport(numIndex) {
	curNumIndex = numIndex;
	$('#step2_info').hide();
	$('#old_phone_info').show();
}

// 【重选】按钮事件
var modifyOldPhoneNumber = '';
function numberModify(numIndex) {
	var num = $('#number_' + numIndex).text();
	var insMode = $('#number_' + numIndex).attr('installMode'); // 0:新装；1:纳入；2:迁转
	if (insMode == '0') {
		numberSelect(numIndex);
	} else if(insMode == '1' || insMode == '2') {
		modifyOldPhoneNumber = num;
		numberImport(numIndex);
	} else {
		$.alert('【' + num + '】不是新/老号码！');
	}
}

// 【删除】按钮事件
function numberDelete(numIndex) {
	var ope = numIndex == 1 ? '放弃主号' : '删除号码' + numIndex;
	var num = $('#number_' + numIndex).text();
	var insMode = $('#number_' + numIndex).attr('installMode'); // 0:新装；1:纳入；2:迁转
	var dialog = $.dialog({
		title : '提示', // 提示title
		content : '确认' + ope + '【' + num + '】？', // 显示的内容，支持html格式。
		buttons : [{
			id : 'btn_ok',
			text : '确定',
			onClick : function() {
				curNumIndex = numIndex;
				$('#number_' + numIndex).text('');
				$('#number_' + numIndex).attr('first_prepay', '');
				$('#number_' + numIndex).attr('mon_limit', '');
				$('#number_' + numIndex).attr('class_id', '');
				$('#number_' + numIndex).attr('installMode', '');
				if (insMode == '0') {
					$('#number_' + numIndex).removeClass('num_new'); // 取消新号码标识
				} else if(insMode == '1' || insMode == '2') {
					$('#number_' + numIndex).removeClass('num_old'); // 取消老号码标识
					var curOldPhone = $('#old_phone_info .list_old_number[device_number="' + num + '"]');
					curOldPhone.removeClass('num_selected'); // 取消已选标识
					curOldPhone.find('input[type="radio"]').show(); // 显示单选按钮
				}
				checkHasNumber();
				dialog.close();
			}
		}, {
			id : 'btn_ok',
			text : '取消',
			onClick : function() {
				dialog.close();
			}
		}]
	}); 
}

// 启用/禁用纳入老手机号码的【确定】按钮
function checkOldPhoneConfirm() {
	if ($('[name="radio_old_phone"]').is(':checked')) {
		$('#old_phone_confirm_div').removeClass('ok_disabled').addClass('ok');
	} else {
		$('#old_phone_confirm_div').removeClass('ok').addClass('ok_disabled');
	}
}

// 显示/隐藏【选择新号】【纳入老号】【重选】和【删除】这4个按钮
function checkHasNumber() {
	if ($('#number_' + curNumIndex).text() != '') {
		$('#number_select_' + curNumIndex).hide();
		$('#number_import_' + curNumIndex).hide();
		$('#number_modify_' + curNumIndex).show();
		$('#number_delete_' + curNumIndex).show();
	} else {
		$('#number_select_' + curNumIndex).show();
		$('#number_import_' + curNumIndex).show();
		$('#number_modify_' + curNumIndex).hide();
		$('#number_delete_' + curNumIndex).hide();
	}
	if (curNumIndex == 1) {
		checkStep2Next();
	}
}

// 启用/禁用step2的【下一步】按钮
function checkStep2Next() {
	if ($('#number_1').text() != '') {
		$('#step2_next_div').removeClass('ok_disabled').addClass('ok');
		$('#step2_modify_div').removeClass('ok_disabled').addClass('ok');
	} else {
		$('#step2_next_div').removeClass('ok').addClass('ok_disabled');
		$('#step2_modify_div').removeClass('ok').addClass('ok_disabled');
	}
}

// 初始化step3
function initstep3() {
	sendHttp(1); // 查询共享流量包
	sendHttp(2); // 共享语音/可视电话
	sendHttp(3); // 共享点对点短信包选择
	sendHttp(5); // 首月资费
	sendHttp(4); // 共享套餐预存分月返还
	sendHttp(6); // 重庆IPTV共享版
	sendHttp(7); // 重庆IPTV产品套餐
}

/* 1.共享流量包, 2.共享语音/电视电话, 3.共享点对点短信包, 4.共享套餐预存分月返还， 5.首月资费， 6.重庆IPTV共享版， 7.重庆IPTV产品套餐 */
function sendHttp(flag) {
	// 共享流量包选择
	if (flag == 1) {
		var ofr_name = "";
		var brand_code = "";
		if (province_code == 'gx') {// 广西根据名称模糊条件查找
			ofr_name = "智慧沃家";
			brand_code = "WOX1";
		} else if (province_code == 'cq') {// cq根据名称模糊条件查找
			ofr_name = "智慧沃家";
			brand_code = "WOX1";
		} else {// 默认模糊查询
			ofr_name = "智慧沃家";
			brand_code = "WOX1";
		}
		data = {
			"oper_no" : oper_no,
			"tele_type" : "LAN",
			"ofr_type" : "601",
			"brand_code" : brand_code,
			"ofr_name" : ofr_name
		};
		var URL = application.fullPath + 'authority/woJiaOpen/saleSelectedCode';
		$.ajax({
			url : URL,
			dataType : 'json',
			contentType : 'application/x-www-form-urlencoded; charset=utf-8',
			type : 'post',
			data : data,
			waitMsg: '流量包查询中..',
			success : function(message) {
				$('#liuliangbao_ul').show();
				$('#liuliangbao_list').html('');
				if (message.type == "error") {
					$.alert(message.content);
				} else {
					if (message.args.sale_selected_code_list.length == 0) {
						$.alert('暂无流量包产品');
					} else {
						$.each(message.args.sale_selected_code_list, function(i, item) {
							var data = JSON.stringify(item);
							var content = '';
							if (i % 2 == 0) {
								content += '<div class="left">'
										+ '<div class="left_lable width_35">' + item.product_name + '</div>'
										+ '<div class="left_radio">'
										+ '<input type="radio" name="liuliangbao" packageId="" elementId="" elementType="" id="' + item.product_id + '" is_mapped="true" onclick="productPapping(this, true)"/>'
										+ '</div>'
										+ '</div>';
							} else {
								content += '<div class="right">'
										+ '<div class="left_lable width_35">' + item.product_name + '</div>'
										+ '<div class="left_radio">'
										+ '<input type="radio" name="liuliangbao" packageId="" elementId="" elementType="" id="' + item.product_id + '" is_mapped="true" onclick="productPapping(this, true)"/>'
										+ '</div>'
										+ '</div>';
							}
							$('#liuliangbao_list').append(content);
						});
					}
				}
			}
		});
	}
	// 共享语音/可视电话选择
	else if (flag == 2) {
		var ofr_name = "";
		var brand_code = "";
		if (province_code == 'gx') {// 广西根据名称模糊条件查找
			ofr_name = "语音包";
			brand_code = "COMP";
		} else if (province_code == 'cq') {// 广西根据名称模糊条件查找
			ofr_name = "语音包";
			brand_code = "COMP";
		} else {// 默认模糊查询
			ofr_name = "语音包";
			brand_code = "COMP";
		}
		data = {
			"oper_no" : oper_no,
			"tele_type" : "LAN",
			"ofr_type" : "601",
			"brand_code" : brand_code,
			"ofr_name" : ofr_name
		};
		var URL = application.fullPath + 'authority/woJiaOpen/saleSelectedCode';
		$.ajax({
			url : URL,
			dataType : 'json',
			contentType : 'application/x-www-form-urlencoded; charset=utf-8',
			type : 'post',
			data : data,
			waitMsg: '语音/可视电话查询中..',
			success : function(message) {
				$('#yuyindianhua_ul').show();
				$('#yuyindianhua_list').html('');
				if (message.type == "error") {
					$.alert(message.content);
				} else {
					if (message.args.sale_selected_code_list.length == 0) {
						$.alert('暂无语音/可视电话产品');
					} else {
						$.each(message.args.sale_selected_code_list, function(i, item) {
							var data = JSON.stringify(item);
							var content = '';
							if (i % 2 == 0) {
								content += '<div class="left">'
										+ '<div class="left_lable width_35">' + item.product_name + '</div>'
										+ '<div class="left_radio">'
										+ '<input type="radio" name="yuyindianhua" packageId="" elementId="" elementType="" id="' + item.product_id + '" is_mapped="true" onclick="productPapping(this, false)"/>'
										+ '</div>'
										+ '</div>';
							} else {
								content += '<div class="right">'
										+ '<div class="left_lable width_35">' + item.product_name + '</div>'
										+ '<div class="left_radio">'
										+ '<input type="radio" name="yuyindianhua" packageId="" elementId="" elementType="" id="' + item.product_id + '" is_mapped="true" onclick="productPapping(this, false)"/>'
										+ '</div>'
										+ '</div>';
							}
							$('#yuyindianhua_list').append(content);
						});
					}
				}
			}
		});
	}
	// 共享点对点短信包选择
	else if (flag == 3) {
		var ofr_name = "";
		var brand_code = "";
		if (province_code == 'gx') {// 广西根据名称模糊条件查找
			ofr_name = "短彩信包";
			brand_code = "COMP";
		} else if (province_code == 'cq') {// 广西根据名称模糊条件查找
			ofr_name = "短彩信包";
			brand_code = "COMP";
		} else {// 默认模糊查询
			ofr_name = "短彩信包";
			brand_code = "COMP";
		}
		data = {
			"oper_no" : oper_no,
			"tele_type" : "LAN",
			"ofr_type" : "601",
			"brand_code" : brand_code,
			"ofr_name" : ofr_name
		};
		var URL = application.fullPath + 'authority/woJiaOpen/saleSelectedCode';
		$.ajax({
			url : URL,
			dataType : 'json',
			contentType : 'application/x-www-form-urlencoded; charset=utf-8',
			type : 'post',
			data : data,
			waitMsg: '点对点短信包查询中..',
			success : function(message) {
				$('#duanxinbao_ul').show();
				$('#duanxinbao_list').html('');
				if (message.type == "error") {
					$.alert(message.content);
				} else {
					if (message.args.sale_selected_code_list.length == 0) {
						$.alert('暂无点对点短信包产品');
					} else {
						$.each(message.args.sale_selected_code_list, function(i, item) {
							var data = JSON.stringify(item);
							var content = '';
							if (i % 2 == 0) {
								content += '<div class="left">'
										+ '<div class="left_lable width_35">' + item.product_name + '</div>'
										+ '<div class="left_radio">'
										+ '<input type="radio" name="duanxinbao"  packageId="" elementId="" elementType="" id="' + item.product_id + '" is_mapped="true" onclick="productPapping(this, false)"/>'
										+ '</div>'
										+ '</div>';
							} else {
								content += '<div class="right">'
										+ '<div class="left_lable width_35">' + item.product_name + '</div>'
										+ '<div class="left_radio">'
										+ '<input type="radio" name="duanxinbao"  packageId="" elementId="" elementType=""  id="' + item.product_id + '" is_mapped="true" onclick="productPapping(this, false)"/>'
										+ '</div>'
										+ '</div>';
							}
							$('#duanxinbao_list').append(content);
						});
					}
				}
			}
		});
	}
	// 共享套餐预存分月返还选择
	else if (flag == 4) {
		var ofr_name = "";
		if (province_code == 'gx') { // 广西根据名称模糊条件查找
			ofr_name = "预存300元分12月返还";
		} else if (province_code == 'cq') { // 重庆根据名称模糊条件查找
			ofr_name = "分12月返还";
		} else { // 默认模糊查询
			ofr_name = "分12月返还";
		}
		var data = {
			"oper_no" : oper_no,
			"tele_type" : "LAN",
			"ofr_name" : ofr_name
		};
		var URL = application.fullPath + 'authority/woJiaOpen/saleSelectedCode';
		$.ajax({
			url : URL,
			dataType : 'json',
			contentType : 'application/x-www-form-urlencoded; charset=utf-8',
			type : 'post',
			data : data,
			waitMsg: '套餐预存查询中..',
			success : function(message) {
				$('#taocanyucun_ul').show();
				$('#taocanyucun_list').html('');
				if (message.type == "error") {
					$.alert(message.content);
				} else {
					if (message.args.sale_selected_code_list.length == 0) {
						$.alert('暂无套餐预存产品');
					} else {
						$.each(message.args.sale_selected_code_list, function(i, item) {
							var data = JSON.stringify(item);
							var content = '';
							if (i % 2 == 0) {
								content += '<div class="left">'
										+ '<div class="left_lable width_35">' + item.product_name + '</div>'
										+ '<div class="left_radio">'
										+ '<input type="radio" name="taocanyucun" id="' + item.product_id + '" onclick="checkStep3Next()"/>'
										+ '</div>'
										+ '</div>';
							} else {
								content += '<div class="right">'
										+ '<div class="left_lable width_35">' + item.product_name + '</div>'
										+ '<div class="left_radio">'
										+ '<input type="radio" name="taocanyucun" id="' + item.product_id + '" onclick="checkStep3Next()"/>'
										+ '</div>'
										+ '</div>';
							}
							$('#taocanyucun_list').append(content);
						});
					}
				}
			}
		});
	}
	// 首月资费方式选择
	else if (flag == 5) {
		var data = {
			"code_type" : "first_month_fee"
		};
		var URL = application.fullPath + 'authority/woJiaOpen/codeList';
		$.ajax({
			url : URL,
			dataType : 'json',
			contentType : 'application/x-www-form-urlencoded; charset=utf-8',
			type : 'post',
			data : data,
			waitMsg: '首月资费查询中..',
			success : function(message) {
				$('#first_month_fee_list').html('');
				if (message.type == "error") {
					$.alert(message.content);
				} else {
					if (message.args.code_list_vos.length == 0) {
						$.alert('暂无首月资费产品');
					} else {
						$.each(message.args.code_list_vos, function(i, item) {
							var data = JSON.stringify(item);
							var content = '';
							if (i == 0) {
								content += '<a><div class="radio_button_checked" id="' + item.code_id + '" onclick="selectFirstMonthFee(this)" style="float:left;">'
										+ item.code_name
										+ '</div></a>';
							} else {
								content += '<a><div class="radio_button_unchecked" id="' + item.code_id + '" onclick="selectFirstMonthFee(this)" style="float:left;">'
										+ item.code_name
										+ '</div></a>';
							}
							$('#first_month_fee_list').append(content);
						});
					}
				}
			}
		});
	}
	// 重庆IPTV共享版
	else if (flag == 6) { // TODO
		data = {
			"oper_no" : oper_no,
			"tele_type" : "LAN",
			"ofr_name" : "IPTV融合折扣"
		};
		var URL = application.fullPath + 'authority/woJiaOpen/saleSelectedCode';
		$.ajax({
			url : URL,
			dataType : 'json',
			contentType : 'application/x-www-form-urlencoded; charset=utf-8',
			type : 'post',
			data : data,
			waitMsg: '重庆IPTV共享版查询中..',
			success : function(message) {
				$('#iptvgongxiang_ul').show();
				$('#iptvgongxiang_list').html('');
				if (message.type == "error") {
					$.alert(message.content);
				} else {
					if (message.args.sale_selected_code_list.length == 0) {
						$.alert('暂无重庆IPTV共享版产品');
					} else {
						$.each(message.args.sale_selected_code_list, function(i, item) {
							var data = JSON.stringify(item);
							var content = '';
							if (i % 2 == 0) {
								content += '<div class="left">'
										+ '<div class="left_lable width_35">' + item.product_name + '</div>'
										+ '<div class="left_radio">'
										+ '<input type="radio" name="iptvDiscnt"  packageId="" elementId="" elementType="" id="' + item.product_id + '" is_mapped="true" onclick=""/>'
										+ '</div>'
										+ '</div>';
							} else {
								content += '<div class="right">'
										+ '<div class="left_lable width_35">' + item.product_name + '</div>'
										+ '<div class="left_radio">'
										+ '<input type="radio" name="iptvDiscnt"  packageId="" elementId="" elementType=""  id="' + item.product_id + '" is_mapped="true" onclick=""/>'
										+ '</div>'
										+ '</div>';
							}
							$('#iptvgongxiang_list').append(content);
						});
					}
				}
			}
		});
	}
	// 重庆IPTV产品套餐
	else if (flag == 7) { // TODO
		data = {
				"oper_no" : oper_no,
				"tele_type" : "LAN",
				"ofr_type" : "604",
				"ofr_name" : 'IPTV'
			};
		var URL = application.fullPath + 'authority/woJiaOpen/saleSelectedCode';
		$.ajax({
			url : URL,
			dataType : 'json',
			contentType : 'application/x-www-form-urlencoded; charset=utf-8',
			type : 'post',
			data : data,
			waitMsg: '重庆IPTV产品套餐查询中..',
			success : function(message) {
				$('#iptvtaocan_ul').show();
				$('#iptvtaocan_list').html('');
				if (message.type == "error") {
					$.alert(message.content);
				} else {
					if (message.args.sale_selected_code_list.length == 0) {
						$.alert('暂无重庆IPTV产品套餐');
					} else {
						$.each(message.args.sale_selected_code_list, function(i, item) {
							var data = JSON.stringify(item);
							var content = '';
							if (i % 2 == 0) {
								content += '<div class="left">'
										+ '<div class="left_lable width_35">' + item.product_name + '</div>'
										+ '<div class="left_radio">'
										+ '<input type="radio" name="iptvradio"  packageId="" elementId="" elementType="" id="' + item.product_id + '" is_mapped="true" onclick="popIptvMode()"/>'
										+ '</div>'
										+ '</div>';
							} else {
								content += '<div class="right">'
										+ '<div class="left_lable width_35">' + item.product_name + '</div>'
										+ '<div class="left_radio">'
										+ '<input type="radio" name="iptvradio"  packageId="" elementId="" elementType=""  id="' + item.product_id + '" is_mapped="true" onclick="popIptvMode()"/>'
										+ '</div>'
										+ '</div>';
							}
							$('#iptvtaocan_list').append(content);
						});
					}
				}
			}
		});
	}
	// 套餐生效方式选择
	else if (flag == 8) {
		var data = {
			"code_type" : "effect_type"
		};
		var URL = application.fullPath + 'authority/woJiaOpen/saleSelectedCode';
		$.ajax({
			url : URL,
			dataType : 'json',
			contentType : 'application/x-www-form-urlencoded; charset=utf-8',
			type : 'post',
			data : data,
			success : function(message) {
				if (message.type == "error") {
					$.alert(message.content);
				} else {
					// 注意要看接口返回具体值
					$.each(message.args.sale_selected_code_list, function(i, item) {
						
					});
				}
			}
		});
	}
	else if (flag == 9) {
		data = {
			"oper_no" : oper_no,
			"tele_type" : "LAN",
			"tips" : "tips"
		};
		var URL = application.fullPath + 'authority/woJiaOpen/saleSelectedCode';
		$.ajax({
			url : URL,
			dataType : 'json',
			contentType : 'application/x-www-form-urlencoded; charset=utf-8',
			type : 'post',
			data : data,
			success : function(message) {
				if (message.type == "error") {
					$.alert(message.content);
				} else {
					// 注意要看接口返回具体值
					$.each(message.args.sale_selected_code_list, function(i, item) {
						
					});
				}
			}
		});
	}
}

// 更新产品id映射
function productPapping(obj, isCheck) {
	if ($(obj).attr('is_mapped')) {
		$(obj).attr('id', getElementInfos(obj));
	}
	if (isCheck) {
		checkStep3Next();
	}
}

// 弹出【重庆IPTV产品套餐】操作页面
function popIptvMode(mode) {
	alert('此处应有弹出窗，根据mode值弹出不同的页面');
	if (mode == '') {
		// TODO 弹出页面1
	} else if (mode == '') {
		// TODO 弹出页面2
	}
}

// 启用/禁用step3的【下一步】按钮
function checkStep3Next() {
	if ($('[name="liuliangbao"]').is(':checked') && $('[name="taocanyucun"]').is(':checked')) {
		$('#step3_next_div').removeClass('ok_disabled').addClass('ok');
		$('#step3_modify_div').removeClass('ok_disabled').addClass('ok');
	} else {
		$('#step3_next_div').removeClass('ok').addClass('ok_disabled');
		$('#step3_modify_div').removeClass('ok').addClass('ok_disabled');
	}
}

function selectFirstMonthFee(e) {
	var obj = $(e);
	if (obj.hasClass('radio_button_checked')) {
		return;
	}
	obj.removeClass('radio_button_unchecked');
	obj.addClass('radio_button_checked');
	obj.parent().siblings().children().removeClass('radio_button_checked');
	obj.parent().siblings().children().addClass('radio_button_unchecked');
}

// 查询装机地址
function getZjdzName(input_text, isPopup) {
	// 弹出查询窗口
	if (isPopup) {
		showdiv('zjdz_search');
	}
	// 必须输入关键字
	if ($.trim(input_text) == '') {
		return;
	}
	var province_code = $("#province_code").val();
	if(province_code == 'nx'){
		if(addressCode_fj == null || addressCode_fj == ""){
			$.alert("请选择分局");
			return;
		}
	}
//	var area_id = '0002'; // TODO 开发环境写死，原因：后台取到的数据0102，查询失败
	var data = {'province_code': province_code,
		        'oper_id': oper_id,
		        'area_id': area_id,
		        'addr_txt': input_text,
		        'hx_area_id':addressCode_fj
			   };
	if(province_code == 'nx'){
		var url = application.fullPath + "/authority/woJiaOpen/selectAddrOnLineNx";
	}else{
		var url = application.fullPath + "/authority/woJiaOpen/selectAddrOnLineCq";
	}
	
	$.ajax({
		url: url,
		dataType: 'json',
		contentType: 'application/x-www-form-urlencoded; charset=utf-8',
		type: 'post',
		data: data,
		waitMsg: '装机地址查询中..',
		success: function(message) {
			$('#zjdz_list').html('');
			if (message.type == 'error') {
				hidediv('zjdz_search');
				$.alert(message.content);
			} else {
				if (message.args.selectAddr.length == 0) {
					hidediv('zjdz_search');
					$.alert('暂无装机地址信息');
				} else {
					$.each(message.args.selectAddr, function(i, item) {
						var data = JSON.stringify(item);
						var desc = '';
						if (item.addr_txt) {
							desc = item.addr_txt;
						} else {
							desc = item.addr_name;
						}
						$("#zjdz_list").append('<li class="list"><div class="left"><div class="left_lable">'
								+ '<a class="tip" href="javascript:void(0)">'
								+ item.addr_name
								+ '<span class="tip_info width_32" style="z-index:999;">' + desc + '</span></a>'
								+ '</div><div class="right_data">'								
								+ '<input name="zjdz_search_check" onClick="zjdzSelect(this)" type="radio" data=\'' + data + '\' id="' + item.addr_code + '"/></div></div></li>');
					});
				}
				//解决最后一行资费提示文字叠加到浮选框头部，预留空白位置
				$("#zjdz_list").append('<div class="list"></div>');
				$("#zjdz_list").append('<div class="list"></div>');
				$("#zjdz_list").append('<div class="list"></div>');
			}
		}
	});
}

var data_zjdz = '';
var addressCode_zjdz = '';
function zjdzSelect(e){
	var div = $(e);
	var dataStr = div.attr('data');
	var selectData = JSON.parse(dataStr);
	//TODO 装机地址预判
	var data1={
			oper_no:oper_no,
			ofr_type:'601',
			area_id:selectData.area_id,
			addr_code:selectData.addr_code
			
	};
	var URL=application.fullPath + 'authority/woJiaOpen/checkAddressInfo';
	$.ajax({
		url: URL,
		dataType: 'json',
		contentType: 'application/x-www-form-urlencoded; charset=utf-8',
		type: 'post',
		data: data1,
		waitMsg: '地址预判中..',
		success: function(message) {
			$("#jrfs_select").html('');
			var accessList = message.args.returnTtext.accessList;
			if(accessList != null && accessList != ""){
				for(var i = 0; i < accessList.length; i++){
					var access = accessList[i];
					$("#jrfs_select").append("<option value='"+access.cb_access_type+"'>"+access.cb_access_type_name+"</option>");
				}
			}
		}
	});
	
	data_zjdz = JSON.parse(dataStr);
}

function zjdzConfirm() {
	hidediv('zjdz_search');
	$('#zjdz_select').val(data_zjdz.addr_name);
	addressCode_zjdz = data_zjdz.addr_code;
	checkStep4Next();
}

function zjdzDelete() {
	hidediv('zjdz_search');
	$('#zjdz_select').val('');
	addressCode_zjdz = '';
	checkStep4Next();
}

// 查询宽带资费
function getKdzfName(input_text, isPopup) {
	input_text = input_text == '' ? '重庆宽带' : input_text;
	var data = {
		"oper_no" : oper_no,
		"ofr_name" : input_text,
		"ofr_type" : '601',
		"tele_type" : '4G'
	};
	var URL=application.fullPath + 'authority/woJiaOpen/saleSelectedCode';
	$.ajax({
		url: URL,
		dataType: 'json',
		contentType: 'application/x-www-form-urlencoded; charset=utf-8',
		type: 'post',
		data: data,
		waitMsg: '宽带资费查询中..',
		success: function(message) {
			$('#kdzf_list').html('');
			// 弹出查询窗口
			if (isPopup) {
				showdiv('kdzf_search');
			}
			if (message.type == 'error') {
				hidediv('kdzf_search');
				$.alert(message.content);
			} else {
				if (message.args.sale_selected_code_list.length == 0) {
					hidediv('kdzf_search');
					$.alert('暂无宽带资费产品');
				} else {
					$.each(message.args.sale_selected_code_list, function(i, item) {
						var data = JSON.stringify(item);
						$("#kdzf_list").append('<li class="list"><div class="left"><div class="left_lable">'
								+ '<a class="tip" href="javascript:void(0)">'
								+ item.product_name
								+ '<span class="tip_info width_32" style="z-index:999;">' + item.product_desc + '</span></a>'
								+ '</div><div class="right_data">'								
								+ '<input name="kdzf_search_check" onClick="kdzfSelect(this)" type="radio" data=\'' + data + '\' id="' + item.product_id + '"/></div></div></li>');
					});
				}
				//解决最后一行资费提示文字叠加到浮选框头部，预留空白位置
				$("#kdzf_list").append('<div class="list"></div>');
				$("#kdzf_list").append('<div class="list"></div>');
				$("#kdzf_list").append('<div class="list"></div>');
			}
		}
	});
}

var data_kdzf = '';
function kdzfSelect(e){
	var div = $(e);
	var dataStr = div.attr('data');
	data_kdzf = JSON.parse(dataStr);
	//校验宽带是否打包
	var productId = data_kdzf.product_id;
	 var data = {
			 "element_id":productId
	 };
	 var URL = application.fullPath + "authority/woJiaOpen/queryZbOfr"; 
    $.ajax({
		type : "POST",
		url : URL,
		data : data,
		async:true,
		dataType:'json',
		waitMsg:"正在校验该宽带资费。。。",
		success:function(message){
			var zb_list = message.args.zb_list;
			if(zb_list != null && zb_list != "" && zb_list.length != 0){
				for(var i = 0;i < zb_list.length; i++){
					data_kdzf.product_id = zb_list[i].aop_product_id;
				}
			}else{
				$.alert("该资费没有约定好打包，请选择其他资费！");
				$(".xxDialog").css("z-index","9999");
				return "";
			}
		},error:function(message){
			var jsonObj=eval(message);
			var desc = jsonObj.content;
			$.alert(desc);
		}
    });
    return "ok";
	
}

function kdzfConfirm() {
	hidediv('kdzf_search');
	$('#kdzf_select').val(data_kdzf.product_name);
	checkStep4Next();
}

function kdzfDelete() {
	hidediv('kdzf_search');
	$('#kdzf_select').val('');
	checkStep4Next();
}

//启用/禁用纳入老手机号码的【确定】按钮
function checkOldLanConfirm() {
	if ($('[name="radio_old_lan"]').is(':checked')) {
		$('#old_lan_confirm_div').removeClass('ok_disabled').addClass('ok');
		$('#old_lan_modify_div').removeClass('ok_disabled').addClass('ok');
	} else {
		$('#old_lan_confirm_div').removeClass('ok').addClass('ok_disabled');
		$('#old_lan_modify_div').removeClass('ok').addClass('ok_disabled');
	}
}

// 启用/禁用step4的【下一步】按钮
function checkStep4Next() {
	var flag = true;
	if ($('#zjdz_select').val() == '') {
		flag = false;
	}
	if ($('#kdzf_select').val() == '') {
		flag = false;
	}
	if ($('#kdzf_connect_name').val() == '') {
		flag = false;
	}
	if ($('#kdzf_connect_phone').val() == '') {
		flag = false;
	}
	if (flag) {
		$('#step4_next_div').removeClass('ok_disabled').addClass('ok');
		$('#step4_modify_div').removeClass('ok_disabled').addClass('ok');
	} else {
		$('#step4_next_div').removeClass('ok').addClass('ok_disabled');
		$('#step4_modify_div').removeClass('ok').addClass('ok_disabled');
	}
}

// 初始化step5
function initStep5() {
	// 用户信息
	setUserConfirmInfo();
	// 手机信息
	setPhoneConfirmInfo();
	// 宽带信息
	if (broadBandInstallMode == '0') { // 宽带新装
		setNewLanConfirmInfo();
	} else { // 宽带纳入
		setOldLanConfirmInfo();
	}
	// 智慧沃家套餐信息
	setSharePackageConfirmInfo();
}

// 用户信息
function setUserConfirmInfo() {
	$('#customer_name_confirm').text($('#customer_name').text());
	$('#id_number_confirm').text($('#id_number').val());
}
// 手机信息
function setPhoneConfirmInfo() {
	$('#number_1_confirm').text($('#number_1').text());
	$('#number_2_confirm').text($('#number_2').text());
	$('#number_3_confirm').text($('#number_3').text());
	$('#number_4_confirm').text($('#number_4').text());
	$('#number_5_confirm').text($('#number_5').text());
	$('#number_6_confirm').text($('#number_6').text());
	$('#number_7_confirm').text($('#number_7').text());
	$('#number_8_confirm').text($('#number_8').text());
	$('#number_9_confirm').text($('#number_9').text());
	$('#number_10_confirm').text($('#number_10').text());
}
// 新宽带信息
function setNewLanConfirmInfo() {
	var tmpKdtcConfirm = omitDisplay($('#kdzf_select').val(), 0, 50);
	var tmpZjdzConfirm = omitDisplay($('#zjdz_select').val(), 0, 50);
	$('#kdtc_confirm').text(tmpKdtcConfirm).attr('title', $('#kdzf_select').val());
	$('#zjdz_confirm').text(tmpZjdzConfirm).attr('title', $('#zjdz_select').val());
	$('#jrfs_confirm').text($('#jrfs_select').find("option:selected").text());
	$('#zdtg_confirm').text($('#zdtg_select').find("option:selected").text());
	$('#zdlx_confirm').text($('#zdlx_select').find("option:selected").text());
	$('#connect_name_confirm').text($('#kdzf_connect_name').val());
	$('#connect_phone_confirm').text($('#kdzf_connect_phone').val());
}
// 老宽带信息
function setOldLanConfirmInfo() {
	var curOldLan = $('[name="radio_old_lan"]:checked').parents('.list_old_number');
	broadBandproductId = curOldLan.attr('zb_product_id');
	broadBandexchCode = curOldLan.attr('exch_code');
	broadBandYwNumber = curOldLan.attr('device_number');
	var tmpKdtcConfirm = omitDisplay(curOldLan.attr('devices_products'), 0, 50);
	$('#kdtc_confirm').text(tmpKdtcConfirm).attr('title', curOldLan.attr('devices_products'));
	$('#zjdz_confirm').text('无');
	$('#jrfs_confirm').text('无');
	$('#zdtg_confirm').text('无');
	$('#zdlx_confirm').text('无');
	$('#connect_name_confirm').text($('#customer_name').text());
	$('#connect_phone_confirm').text($('#connect_phone').val());
}
// 智慧沃家套餐信息
function setSharePackageConfirmInfo() {
	var tmpGxllbText = $('[name="liuliangbao"]:checked').parent().prev().text();
	var tmpGxllbConfirm = omitDisplay(tmpGxllbText, 0, 50);
	var tmpGxtcycText = $('[name="taocanyucun"]:checked').parent().prev().text();
	var tmpGxtcycConfirm = omitDisplay(tmpGxtcycText, 0, 50);
	$('#gxllb_confirm').text(tmpGxllbConfirm).attr('title', tmpGxllbText);
	$('#gxtcyc_confirm').text(tmpGxtcycConfirm).attr('title', tmpGxtcycText);
	if ($('[name="yuyindianhua"]:checked').length == 0) {
		$('#gxyydh_confirm').text('');
	} else {
		var tmpGxyydhText = $('[name="yuyindianhua"]:checked').parent().prev().text();
		var tmpGxyydhConfirm = omitDisplay(tmpGxyydhText, 0, 50);
		$('#gxyydh_confirm').text(tmpGxyydhConfirm).attr('title', tmpGxyydhText);
	}
	if ($('[name="duanxinbao"]:checked').length == 0) {
		$('#gxdxb_confirm').text('');
	} else {
		var tmpGxdxbText = $('[name="duanxinbao"]:checked').parent().prev().text();
		var tmpGxdxbConfirm = omitDisplay(tmpGxdxbText, 0, 50);
		$('#gxdxb_confirm').text(tmpGxdxbConfirm).attr('title', tmpGxdxbText);
	}
}

// 订单确认
function orderAffirm() {
	
	var broadBandProduct = $('#kdtc_confirm').text();
	var speedLevel = "12";
	if(broadBandProduct.indexOf("12M") >=0){
		speedLevel = "12"
	}
	if(broadBandProduct.indexOf("20M") >=0){
		speedLevel = "20"
	}
	if(broadBandProduct.indexOf("50M") >=0){
		speedLevel = "50"
	}
	
	if(broadBandInstallMode == "2"){
		speedLevel = _spendLevel;
	}
	
	var allbroadBandTemplate = [];
	
	var _custType = "0";
	if(cust_id != ""){
		var _custType = "1"
	}else{
		_custType = "0"
	}
	
	var broadBandCustInfo={
			"customerName":$('#customer_name').text(),
			"certAdress":$('#auth_adress').text(),
			"contactPhone":$('#connect_phone').val(),
			"certExpireDate":$("#end_date_val").val(),
			"certNum":$('#id_number').val(),
			"certType":$('#id_type').val(),
			"checkType":"03",
			"contactPerson":$('#kdzf_connect_name').val() == "" ? $('#customer_name').text():$('#kdzf_connect_name').val(),
			"contactAddress":$('#connect_addr').val(),
			"bandCustId":cust_id,//客户ID
			"custType":_custType//新老客户标志
			
	};
	
	

	var broadBandProduct = [];
	var machineInfo = {
			"machineProvide":$('#zdtg_select').val(),
			"machineType":$('#zdlx_select').val()
	};
	
	var tempBroadBandProductId = "";
	if(broadBandInstallMode == "0"){//新装从原位置选择宽带编号
		tempBroadBandProductId = data_kdzf.product_id;
	}else{//从列表数据中获取
		tempBroadBandProductId = broadBandproductId;
	}
	var tempProduct = {
			"payMode":"1",
			"machineInfo":machineInfo,
			"cityArea":"831", //后台获取
			"productMode":"1",
			"userProperty":"1",
			"serviceArea":"83a0964",//后台获取
			"cityMark":"1",
			"acceptMode":"1",
			"userType":"1",
			"productId":tempBroadBandProductId,
			"firstMonBillMode":$('#first_month_fee_list').find('.radio_button_checked').attr('id')
	};
	broadBandProduct.push(tempProduct);
	var exchInfo = {
			"key":"EXCH_CODE",
			"value":data_zjdz.branch_code
	};
	
	
	/**
	 * IPTV说明
	 * 选择了iptv产品必须要选择iptv资费
	 */
	
	var iptvInfos = [];
	var iptvInfoCheckId = $('[name="iptvradio"]:checked').attr('id');//iptv产品信息
	
	if(iptvInfoCheckId != null && iptvInfoCheckId != ""){
		
		var IptvServiceAttrs = [];
		var iptvServiceAttr1 = {
				"code":"123",
				"value":"2321"
		};
		IptvServiceAttrs.push(iptvServiceAttr1);
		
		var iptvDiscnt =  $('[name="iptvDiscnt"]:checked').attr('id');
		if(iptvDiscnt != "" && iptvDiscnt != null){
			var IptvDiscntInfos = [];
			var IptvDiscntInfo = {
					"IptvDiscntId":iptvDiscnt
			};
			IptvDiscntInfos.push(IptvDiscntInfo);
		}
		
		var iptvInfo = {
				"IptvService":iptvInfoCheckId,
				"IptvServiceAttr":IptvServiceAttrs
				"IptvDiscntInfo":IptvDiscntInfos
			};
		iptvInfos.push(iptvInfo);
	}
	
	
	
	
	 
	var broadBandTemplate = [];
	var tempbroadBandTemplate = {
			"bookingDateType":"0",
			"accountPayType":"10",
			"installMode":broadBandInstallMode,
			"speedLevel":speedLevel,
			"createOrExtendsAcct":"0",
			"broadBandType":"1",
			"exchCode":data_zjdz.branch_code == null ?broadBandexchCode: data_zjdz.branch_code,
			"addressCode":addressCode_zjdz == null ? "" : addressCode_zjdz,
			"addressName":$('#zjdz_select').val() == null ? "" :$('#zjdz_select').val(),
			"installAddress":$('#zjdz_select').val() == null ? "" :$('#zjdz_select').val(),
			"cbssAccessMode":$('#jrfs_select').val()== null ? "" :$('#jrfs_select').val() 
	};
	
	if(broadBandInstallMode == "2"){
		broadBandCustInfo.cbssAccessMode = _cbssAccessType;
	}
	
	broadBandTemplate.push(tempbroadBandTemplate);
	
	
	var allnumberList = [];
	var numberList = $('#number_list');
	$('[name="numbers"]').each(function(i, item) {
		if ($(this).text() != '') {
			var installMode = $(this).attr('installMode');
			var num = {
				'acc_nbr': $(this).text(),
				'acc_nbr_fee': $(this).attr('first_prepay'),
				'low_fee': $(this).attr('mon_limit'),
				'installMode': installMode
			};
			allnumberList.push(num);
			
			if(installMode == "0"){
				finishFlag = false;
				numberList.append($('<option>', {
					'value': $(this).text(),
					'text': $(this).text()
				}));
				
			}
			
		}
	});
	var masterNumber = {
		'acc_nbr': $('#number_1').text(),
		'acc_nbr_fee': $('#number_1').attr('first_prepay'),
		'low_fee': $('#number_1').attr('mon_limit'),
		'installMode': $('#number_1').attr('installMode')
	};
	
	var elementInfo = [];
	
	var temp = {
			'packageId':$('[name="liuliangbao"]:checked').attr('packageId'),
			'elementId':$('[name="liuliangbao"]:checked').attr('elementId'),
			'elementType':$('[name="liuliangbao"]:checked').attr('elementType')
	};
	elementInfo.push(temp);
	if($('[name="yuyindianhua"]:checked').attr('id') != null){
		temp = {
				'packageId':$('[name="yuyindianhua"]:checked').attr('packageId'),
				'elementId':$('[name="yuyindianhua"]:checked').attr('elementId'),
				'elementType':$('[name="yuyindianhua"]:checked').attr('elementType')
		};
		elementInfo.push(temp);
	}
	
	if($('[name="duanxinbao"]:checked').attr('id') != null){
		temp = {
				'packageId':$('[name="duanxinbao"]:checked').attr('packageId'),
				'elementId':$('[name="duanxinbao"]:checked').attr('elementId'),
				'elementType':$('[name="duanxinbao"]:checked').attr('elementType')
		};
		elementInfo.push(temp);
	}
	var data = {
		'order_id': $('#order_id').val(), // 订单号
		/* 手机号码信息 */
		'allnumberList': JSON.stringify(allnumberList), // 全部号码
		'masterNumber': JSON.stringify(masterNumber), // 主号码
		'oldNnumberList':JSON.stringify(allnumberList),
		/* 智慧沃家套餐信息 */
		'flow_package_id': $('[name="liuliangbao"]:checked').attr('id'), // 共享流量包id
		'flow_package': $('[name="liuliangbao"]:checked').parent().prev().text(), // 共享流量包
		'first_month_fee': $('#first_month_fee_list').find('.radio_button_checked').attr('id'), // 首月付费模式id
		'effect_type_id': $('#first_month_fee_list').find('.radio_button_checked').attr('id'), // 首月付费模式id
		'effect_type': $('#first_month_fee_list').find('.radio_button_checked').text(), // 首月付费模式
		'voice_videophone_id': $('[name="yuyindianhua"]:checked').attr('id'), // 共享语音/可视电话id
		'voice_videophone': $('[name="yuyindianhua"]:checked').parent().prev().text(), // 共享语音/可视电话
		'message_package_id': $('[name="duanxinbao"]:checked').attr('id'), // 共享点对点短信包id
		'message_package': $('[name="duanxinbao"]:checked').parent().prev().text(), // 共享点对点短信包
		'sub_productid': $('[name="taocanyucun"]:checked').attr('id'), // 套餐预存分月返现id
		'activity_name': $('[name="taocanyucun"]:checked').parent().prev().text(), // 套餐预存分月返现
		/* 宽带新装信息 */
		'addressCode': addressCode_zjdz, // 装机地址id
		'addressName': $('#zjdz_select').val(), // 装机地址
		'installAddress': $('#zjdz_select').val(), // 装机地址
		'taocanName': $('#kdzf_select').val(), // 宽带资费
		/*'jrfs': $('#jrfs_select').val(),
		'zdtg': $('#zdtg_select').val(),
		'zdlx': $('#zdlx_select').val(),*/
		'customerName': $('#kdzf_connect_name').val(), // 宽带联系人
		'phoneNumber': $('#kdzf_connect_phone').val(), // 宽带联系电话
		'phone_number': $('#kdzf_connect_phone').val(), // 宽带联系电话
		/* 用户资料信息 */
		'cust_id': cust_id == null ? '' : cust_id,
		'customer_id': cust_id == null ? '' : cust_id,
		'id_number': $('#id_number').val(), // 身份证号
		'id_type': $('#id_type').val(), // 身份证类型
		'customer_name': $('#customer_name').text(), // 证件名称
		'cust_sex': $('#customer_sex').val(), // 性别
		'born_date': $("#born_date_val").val(), // 出生日期
		'auth_adress': $('#auth_adress').text(), // 证件地址
		'auth_end_date': $("#end_date_val").val(), // 身份证实效时间
		'contact_phone': $('#connect_phone').val(), // 联系人
		'contact_adress': $('#connect_addr').val(), // 联系地址
		/* 其他信息 */
		'main_product_id': '89017299', // 智慧沃家主产品
		'lanProductMode': '0',
		'ordType': '1',
		'product_id': '89002922',
		'productMode': '0',
		'G_type':'4G',
		'acc_nbr':allnumberList[0].acc_nbr,
		'acc_nbr_fee':allnumberList[0].acc_nbr_fee,
		'low_fee':allnumberList[0].low_fee,
		'installMode':allnumberList[0].installMode,
		'class_id': $('#number_1').attr('class_id'), // 号码级别
		'bookingDateType':'0',
		'tele_type':'LAN',
		'serCode':'40',
		'wo_type':'0',
		'workDepId': dept_no,
		'workStaffId': oper_no,
		'yw_number': '[]',
		'writecar_success':'0',
		'areaExchId':data_zjdz.hx_area_code,
		'exchCode':data_zjdz.branch_code,
		'pointExchId':data_zjdz.branch_code,
		'elementInfos':JSON.stringify(elementInfo),
		'broadBandInfo':JSON.stringify(broadBandTemplate),
		'broadBandCustInfo':JSON.stringify(broadBandCustInfo),
		'broadBandProduct':JSON.stringify(broadBandProduct),
		'broadBandexchInfo':JSON.stringify(exchInfo)
	};
	var data_nx = {
			'order_id': $('#order_id').val(), // 订单号
			/* 手机号码信息 */
			'allnumberList': JSON.stringify(allnumberList), // 全部号码
			'masterNumber': JSON.stringify(masterNumber), // 主号码
			'oldNnumberList':JSON.stringify(allnumberList),
			/* 智慧沃家套餐信息 */
			'flow_package_id': $('[name="liuliangbao"]:checked').attr('id'), // 共享流量包id
			'flow_package': $('[name="liuliangbao"]:checked').parent().prev().text(), // 共享流量包
			'first_month_fee': $('#first_month_fee_list').find('.radio_button_checked').attr('id'), // 首月付费模式id
			'effect_type_id': $('#first_month_fee_list').find('.radio_button_checked').attr('id'), // 首月付费模式id
			'effect_type': $('#first_month_fee_list').find('.radio_button_checked').text(), // 首月付费模式
			'voice_videophone_id': $('[name="yuyindianhua"]:checked').attr('id'), // 共享语音/可视电话id
			'voice_videophone': $('[name="yuyindianhua"]:checked').parent().prev().text(), // 共享语音/可视电话
			'message_package_id': $('[name="duanxinbao"]:checked').attr('id'), // 共享点对点短信包id
			'message_package': $('[name="duanxinbao"]:checked').parent().prev().text(), // 共享点对点短信包
			'sub_productid': $('[name="taocanyucun"]:checked').attr('id'), // 套餐预存分月返现id
			'activity_name': $('[name="taocanyucun"]:checked').parent().prev().text(), // 套餐预存分月返现
			/* 宽带新装信息 */
			'addressCode': addressCode_zjdz, // 装机地址id
			'addressName': $('#zjdz_select').val(), // 装机地址
			'installAddress': $('#zjdz_select').val(), // 装机地址
			'taocanName': $('#kdzf_select').val(), // 宽带资费
			/*'jrfs': $('#jrfs_select').val(),
			'zdtg': $('#zdtg_select').val(),
			'zdlx': $('#zdlx_select').val(),*/
			'customerName': $('#kdzf_connect_name').val(), // 宽带联系人
			'phoneNumber': $('#kdzf_connect_phone').val(), // 宽带联系电话
			'phone_number': $('#kdzf_connect_phone').val(), // 宽带联系电话
			/* 用户资料信息 */
			'cust_id': cust_id == null ? '' : cust_id,
			'customer_id': cust_id == null ? '' : cust_id,
			'id_number': $('#id_number').val(), // 身份证号
			'id_type': $('#id_type').val(), // 身份证类型
			'customer_name': $('#customer_name').text(), // 证件名称
			'cust_sex': $('#customer_sex').val(), // 性别
			'born_date': $("#born_date_val").val(), // 出生日期
			'auth_adress': $('#auth_adress').text(), // 证件地址
			'auth_end_date': $("#end_date_val").val(), // 身份证实效时间
			'contact_phone': $('#connect_phone').val(), // 联系人
			'contact_adress': $('#connect_addr').val(), // 联系地址
			/* 其他信息 */
			'main_product_id': '89017299', // 智慧沃家主产品
			'lanProductMode': '0',
			'ordType': '1',
			'product_id': '89002922',
			'productMode': '0',
			'G_type':'4G',
			'acc_nbr':allnumberList[0].acc_nbr,
			'acc_nbr_fee':allnumberList[0].acc_nbr_fee,
			'low_fee':allnumberList[0].low_fee,
			'installMode':allnumberList[0].installMode,
			'class_id': $('#number_1').attr('class_id'), // 号码级别
			'bookingDateType':'0',
			'tele_type':'LAN',
			'serCode':'40',
			'wo_type':'0',
			'workDepId': dept_no,
			'workStaffId': oper_no,
			'yw_number': '[]',
			'writecar_success':'0',
			'areaExchId':data_zjdz.hx_area_code,
//			'exchCode':data_zjdz.branch_code,
//			'pointExchId':data_zjdz.branch_code,
			'elementInfos':JSON.stringify(elementInfo)
		};
	//TODO elementInfos 宽带元素，
	//TODO oldNnumberList：老号码（[{"acc_nbr":"18580136570","acc_nbr_fee":"0.00","low_fee":"0","installMode":"0"}]）
	//TODO recomDepartId，83a0876，recomPersonId	8305162412，recomPersonName	秦禹 发展人信息
	if(province_code == 'nx'){
		getLanNumber(data_nx);
	}else{
		if(broadBandInstallMode == "0"){
			getLanNumber(data);
		}else{
			data['lanywNumber'] = broadBandYwNumber;
			OrderUpdatedate(data);
		}
		
	}
}

/**
 * 获取宽带业务号码和上网账号
 */
function getLanNumber(jsonObj){
	var URl = application.fullPath + "authority/woJiaOpen/IsUseFixM165";
	var data = {
		'oper_no': oper_no,
		"tele_type": 'ALL',
		"exch_code": data_zjdz.branch_code,
		"addressCode": addressCode_zjdz,
		"addressName": $('#zjdz_select').val()
	};
	var data_nx = {
			'oper_no': oper_no,
			"tele_type": 'ALL',
			"exch_code": data_fjdz.hx_area_id, 
			"addressCode": addressCode_zjdz,
			"addressName": $('#zjdz_select').val()
		};
	if(province_code == 'nx'){
		$.ajax({
			type: "POST",
			url: URl,
			data: data_nx,
			dataType: 'json',
			waitMsg: "正在生成宽带业务号码和上网账号...",
			success: function(message) {
				var infos = message.args.lan_info;
				jsonObj['onlineNmber'] = infos.serial_number;
				jsonObj['lanywNumber'] = infos.auth_acct_id;
				OrderUpdatedate(jsonObj);
			},
			error: function(message) {
				var jsonObj = eval(message);
				var desc = jsonObj.content;
				$.alert(desc);
			}
		});
	}else{
		$.ajax({
			type: "POST",
			url: URl,
			data: data,
			dataType: 'json',
			waitMsg: "正在生成宽带业务号码和上网账号...",
			success: function(message) {
				var infos = message.args.lan_info;
				jsonObj['onlineNmber'] = infos.auth_acct_id;
				jsonObj['lanywNumber'] = infos.serial_number;
				OrderUpdatedate(jsonObj);
			},
			error: function(message) {
				var jsonObj = eval(message);
				var desc = jsonObj.content;
				$.alert(desc);
			}
		});
	}
	
}

function OrderUpdatedate(jsonObj) {
	jsonObj = JSON.stringify(jsonObj);
	var data = {
		"order_id": $("#order_id").val(),
		"page_code": "0",
		"order_json": jsonObj
	};
	var URL = application.fullPath + "authority/accountOpen/orderInfoAttrUpdate";
	$.ajax({
		url: URL,
		dataType: 'json',
		contentType: 'application/x-www-form-urlencoded; charset=utf-8',
		type: 'post',
		data: data,
		// waitMsg:order_all_json[i].hint,
		success: function(message) {
			if (message.type == "error") {
				$.alert(message.content);
				return;
			} else {
				$('#step5').hide();
				$('#step6').show();
				window.location.href = "#tab_taocan";
				initStep6();
			}
		}
	});
}

// 初始化step6
function initStep6() {
	getFee();
}

// 费用确认
function feeAffirm() {
	var feeItem = [];
	$.each(fee_info, function(i, item) {
		var obj = $('#charge_item' + i);
		var discount = (parseFloat(obj.attr('data')) - parseFloat(obj.val())) + '';
		var fee = {
			'fee_id': obj.attr('code'),
			'fee_id_type': '2',
			'orig_fee': obj.attr('data'),
			'discount_fee': discount,
			'real_fee': obj.val(),
			'fee_name': obj.attr('name')
		};
		feeItem.push(fee);
	});
	
	var data1 = {
		'totalFee': fee_all,
		'feeItem': JSON.stringify(feeItem),
		'orderId': $('#order_id').val(),
		'customerName': $('#customer_name').text(),
		'numberInfos':JSON.stringify(num_infos)
	};
	 var URL = application.fullPath + "authority/woJiaOpen/updateOrderStatus";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"正在提交费用",
			success:function(message){					 
				if(message.type == "error"){	
					$.alert('费用提交失败');
				}else{
					
					var attrs = message.args.attrMap;
					
					$("#payFlag").val(attrs.payFlag);
					$("#writeWay").val(attrs.writeWay);
					$("#wo_type").val(attrs.wo_type);
					$("#prepayFlag").val(attrs.prepayFlag);
					$("#cardType").val(attrs.cardType);
					$("#tele_type").val(attrs.teleType);
					$("#wo_type").val(attrs.wo_type);

					
					$('#step6').hide();
					$('#step7').show();
					window.location.href = "#tab_taocan";
					initStep7();
				}				
			}
		});	
}

// 初始化step7
function initStep7() {
	$('#totalFee').text($('#fee_all').text());
	// TODO
}

//读卡按钮
function readCardBtn() {
	// 读卡时查询PDF状态码，确认电子签名并保存（即状态码为是否000），不是则提示操作员"请确认电子签名并保存电子单!"
	if (province_code == "nx" || province_code == "cq") {
		AfterReadCard();
	} else {
		$.ajax({
			type : "POST",
			url : application.fullPath + "authority/dealShowOrder/pdfIsSign",
			data : {
				"order_id" : $("#order_id").val(),
				"template_id" : $("#termsInnetFlag").val() + ',' + $("#termsGoodFlag").val() + ',' + $("#termsPreferentialFlag").val()
			},
			dataType : 'json',
			waitMsg : "请稍等......",
			success : function(message) {
				if (message.type == "error") { // pdf状态码非000时
					$.alert(message.content.substr(0, message.content.length - 1));
				} else {
					AfterReadCard();
				}
			}
		})
	}
}

function AfterReadCard(){
	if (!load_rwcard_acx) {
		document.body.insertAdjacentHTML("beforeEnd", " \
		          <object id=\"CardReader\" style=\"display:none;\" classid=\"clsid:43E4D4FC-3CD8-459A-AAA1-698C1288DE93\" width=\"0\" height=\"0\"> \
		        </object>");

		load_rwcard_acx = true;
	}
	var payFlag = $("#payFlag").val();// 如果是Y则代表已收费，则隐藏收费的按钮
	if (payFlag == 'Y') {
	} else {
		$.warn("先收费成功在进行卡操作")
		return;
	}
	var writeWay = $("#writeWay").val();
	if ('0' == writeWay) {
		$.alert("模拟白卡开户，请手动输入卡号，进行写卡");
	} else {
		var sim_no = getCardId();
		$("#resourcesCode").val(sim_no);
	}
}

//写卡按钮
var writeCardFlag = 0;
function writeCardBtn() {
	writeCardFlag ++;
	if (writeCardFlag > 1) {
		$.alert("请不要重复点击写卡按钮");
		return;
	}
	var payFlag = $("#payFlag").val();//如果是Y则代表已收费，则隐藏收费的按钮
	if (payFlag == 'Y') {
	} else {
		writeCardFlag = 0;
		$.alert("先收费成功在进行卡操作");
		return;
	}
	var iccid = $("#resourcesCode").val();
	var writeWay = $("#writeWay").val();
	if ('' == iccid || '请读卡' == iccid) {
		if ('0' == writeWay) {
			$.alert("模拟白卡开户，请手动输入卡号，在进行写卡");
			writeCardFlag = 0;
			return;
		} else {
			$.alert("请先读卡在进行写卡");
			writeCardFlag = 0;
			return;
		}
	} else {
		if ('0' == writeWay) {
		} else {
			var sim_no = getCardId();
			if (sim_no == iccid) {
			} else {
				$.alert("此白卡与已经获取的卡号不一样，请插入之前的白卡再操作");
				writeCardFlag = 0;
				return;
			}
		}
	}
	
	var numId = "";
	var type = $("#wo_type").val();
	if (type == '3') {// 沃家庭
		numId = $("#acc_nbr").val().toString();
	} else {// 智慧沃家
		numId = $("#number_list").val().toString();
	}
	var prepayFlag = '1';
	var cardType = $("#cardType").val();
	var teleType = $("#tele_type").val();// 电信类型
	
	var GetURl = application.fullPath + "authority/checkResInfo/getCardData";
	$.ajax({
		url: GetURl,
		data: {
			"iccid": iccid,
			"numId": numId,
			"cardType": cardType,
			"userType": prepayFlag,
			"teleType": teleType
		},
		dataType: 'json',
		type: 'post',
		waitMsg: "正在写卡！",
		success: function(message) {
			var returnData = message.args.ruturnData;
			if (returnData == "OK") {
				if ('2G' == teleType) {
					var cardData = message.args.CardData;
					var imsi = message.args.IMSI;
					var procId = message.args.procId;
					// console.log(imsi);
					$("#cardData").val(cardData);
					$("#imsi").val(imsi);
					$("#procId").val(procId);// 2G代表报文头流水 3G代表卡序列
					var activeId = "";
					var capacityTypeCode = "";
					var resKindCode = "";
				} else {
					var imsi = message.args.card_info.imsi;
					var procId = message.args.card_info.procId;
					var activeId = message.args.card_info.activeId;
					var capacityTypeCode = message.args.card_info.capacityTypeCode;
					var resKindCode = message.args.card_info.resKindCode;
					var cardData = message.args.card_info.cardData;
					$("#imsi").val(imsi);
					$("#activeId").val(activeId);
					$("#cardData").val(cardData);
					$("#capacityTypeCode").val(capacityTypeCode);
					$("#resKindCode").val(resKindCode);
				}
				var ret = true;
				if ('0' == writeWay) {
					ret = true;
				} else {
					ret = UpdateImsi(imsi, teleType);
				}
				if (ret == true) {
					var reasonId = "0";// 写卡成功
					var errorComments = "";
					var NotifyURl = application.fullPath + "authority/checkResInfo/cardNotify";
					$.ajax({
						url: NotifyURl,
						data: {
							"iccid": iccid,
							"imsi": imsi,
							"procId": procId,
							"activeId": activeId,
							"capacityTypeCode": capacityTypeCode,
							"resKindCode": resKindCode,
							"reasonId": reasonId,
							"errorComments": errorComments,
							"teleType": teleType
						},
						dataType: 'json',
						type: 'post',
						waitMsg: "正在校验！",
						success: function(message) {
							var returnData = message.args.ruturnData;
							if (returnData == "OK") {
								var order_id = $("#order_id").val();// 订单号
								var iccid = $("#resourcesCode").val();// 资源号
								var imsi = $("#imsi").val();
								var cardData = $("#cardData").val();
								var cardType = $("#cardType").val();
								var capacityTypeCode = $("#capacityTypeCode").val();
								var resKindCode = $("#resKindCode").val();
								var activeId = $("#activeId").val();
								var procId = $("#procId").val();
								doAccountOpen(order_id, iccid, imsi, cardType, capacityTypeCode, resKindCode, cardData, activeId, procId, numId);
								writeCardFlag = 0;
							} else {
								writeCardFlag = 0;
								$.alert(returnData);
							}
						}
					});
				} else {
					var reasonId = "9";// 写卡失败
					var errorComments = "写卡失败";// 写卡失败
					$.ajax({
						url: "cardNotify",
						data: {
							"iccid": iccid,
							"imsi": imsi,
							"procId": procId,
							"activeId": activeId,
							"capacityTypeCode": capacityTypeCode,
							"resKindCode": resKindCode,
							"reasonId": reasonId,
							"errorComments": errorComments,
							"teleType": teleType
						},
						dataType: 'json',
						type: 'post',
						waitMsg: "正在校验！",
						success: function(message) {
							writeCardFlag = 0;
							$.alert("读卡器写卡失败");
						}
					});
				}
			} else {
				writeCardFlag = 0;
				$.alert(returnData);
			}
		}
	});
}

//开户卡数据同步提交
function doAccountOpen(order_id, iccid, imsi, cardType, capacityTypeCode, resKindCode, cardData, activeId, procId, numId) {
	var type = $("#wo_type").val();
	var accountOpenURL = "";
	var data = null;
	if (type == '3') {// 沃家庭 走单卡开户
		accountOpenURL = application.fullPath + "authority/accountOpen/doAccountOpen";//开户url
		$.ajax({
			type: "post",
			url: accountOpenURL,
			waitMsg: "正在开户，请稍候！",
			data: {
				"order_id": order_id,
				"SimID": iccid,
				"imsi": imsi,
				"cardType": cardType,
				"capacityTypeCode": capacityTypeCode,
				"resKindCode": resKindCode,
				"cardData": cardData,
				"activeId": activeId,
				"procId": procId
			},
			dataType: 'json',
			success: function(message) {
				var respCode = message.args.RespCode;
				var RespDesc = message.args.RespDesc;
				if (respCode == '0000') {// 写卡完成部分开户成功
					$("#readCard").removeAttr("onClick");
					$("#readCard").removeClass("view_btn");
					$("#readCard").addClass("btn_disabled");

					$("#writeCard").removeAttr("onClick");
					$("#writeCard").removeClass("view_btn");
					$("#writeCard").addClass("btn_disabled");
					var dialog = $.dialog({
						title: '提示',// 提示title
						content: '写卡成功!请组智慧沃家',
						closed: false,
						cache: false,
						buttons: [ {
							id: 'btn_ok',
							text: '确定',
							onClick: function() {
								dialog.close();
								sumbwjt();
							}
						} ]
					});
					/*
					 * $("#sumbwjt").removeClass("btn_disabled");
					 * $("#sumbwjt").addClass("view_btn");
					 * $("#sumbwjt").bind("click",function (){ sumbwjt(); });
					 */
				} else {// 开户失败
					var jsonObj = eval(message);
					var desc = jsonObj.content;
					$.alert(desc);
					// $("html,body").animate({scrollTop:
					// $("#box4").offset().top}, 10)
				}
			},
			error: function(message) {// 开户提交失败
				var jsonObj = eval(message);
				var desc = jsonObj.content;
				if (typeof (desc) == "undefined") {
					$("#readCard").removeAttr("onClick");
					$("#readCard").removeClass("view_btn");
					$("#readCard").addClass("btn_disabled");

					$("#writeCard").removeAttr("onClick");
					$("#writeCard").removeClass("view_btn");
					$("#writeCard").addClass("btn_disabled");
					var dialog = $.dialog({
						title: '提示',// 提示title
						content: '写卡成功!请组智慧沃家',
						closed: false,
						cache: false,
						buttons: [ {
							id: 'btn_ok',
							text: '确定',
							onClick: function() {
								dialog.close();
								sumbwjt();
							}
						} ]
					});
					/*
					 * $("#sumbwjt").removeClass("btn_disabled");
					 * $("#sumbwjt").addClass("view_btn");
					 * $("#sumbwjt").bind("click",function (){ sumbwjt(); });
					 */
				} else {
					$.alert(desc);
				}

			}
		});
	} else {// 智慧沃家
		accountOpenURL = application.fullPath + "authority/accountOpen/doWoAccountOpen";// 开户url
		$.ajax({
			type: "post",
			url: accountOpenURL,
			waitMsg: "正在开户，请稍候！",
			data: {
				"order_id": order_id,
				"SimID": iccid,
				"imsi": imsi,
				"cardType": cardType,
				"capacityTypeCode": capacityTypeCode,
				"resKindCode": resKindCode,
				"cardData": cardData,
				"activeId": activeId,
				"procId": procId,
				"acc_nbr": numId
			},
			dataType: 'json',
			success: function(message) {
				var respCode = message.args.RespCode;
				var RespDesc = message.args.RespDesc;
				if (respCode == '0000') {// 写卡完成部分开户成功
					$.alert("开户成功");
				} else if (respCode == '1111') {// 所有卡写卡 完成标记
					$("#readCard").removeAttr("onClick");
					$("#readCard").removeClass("view_btn");
					$("#readCard").addClass("btn_disabled");

					$("#writeCard").removeAttr("onClick");
					$("#writeCard").removeClass("view_btn");
					$("#writeCard").addClass("btn_disabled");

					$("#okModule").removeClass("ok_disabled");
					$("#okModule").addClass("ok");
					$("#okSubmit").bind("click", function() {
						okSumbit();
					});
					$.alert("开户成功");
				} else {// 开户失败
					var jsonObj = eval(message);
					var desc = jsonObj.content;
					$.alert(desc);
					// $("html,body").animate({scrollTop:
					// $("#box4").offset().top}, 10)
				}
			},
			error: function(message) {// 开户提交失败
				var jsonObj = eval(message);
				var desc = jsonObj.content;
				$.alert(desc);
				// $("html,body").animate({scrollTop: $("#box3").offset().top}, 10);
			}
		});
	}
}

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
function load_card_mech() {
	var id_card_mech = $("#id_card_mech").val();
	if (id_card_mech === "crvu") {
		load_model(id_card_mech);
	}
	$("#id_card_mech").change(function() {
		id_card_mech = $("#id_card_mech").val();
		if (id_card_mech === "crvu") {
			load_model(id_card_mech);
		}
// 		else if(id_card_mech === "ICR"){
// 			load_model("ICR");
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
	if (pSex != '1') {
		pSex = '0';
	}
	pSexShow = pSex == "1" ? "男" : "女";
	pNation = getNationStr(CVR_IDCard.Nation); // 民族
	pBorn = CVR_IDCard.Born; // 出生日期
	
	// 截取生日日期，目前有些环境读卡器读出日期为 ‘yyyy-dd-mm’
	if (pBorn.length == 8) {
		var year = pBorn.substr(0, 4);
		var month = pBorn.substr(4, 2);
		var day = pBorn.substr(6, 2);
		pBorn = year + "-" + month + "-" + day;
	}
	pAddress = CVR_IDCard.Address; // 住址
	pCardNo = CVR_IDCard.CardNo; // 身份证号
	pPolice = CVR_IDCard.IssuedAt; // 签发机关
	pActivityLFrom = CVR_IDCard.EffectedDate; // 起始日期
	if (pActivityLFrom.length == 8) {
		var y = pActivityLFrom.substr(0, 4);
		var m = pActivityLFrom.substr(4, 2);
		var d = pActivityLFrom.substr(6, 2);
		pActivityLFrom = y + "-" + m + "-" + d; 
		valid_start = y + "." + m + "." + d;
	}
	pActivityLTo = CVR_IDCard.ExpiredDate; // 结束日期
	if (pActivityLTo.length >= 8) {
		var y = pActivityLTo.substr(0, 4);
		var m = pActivityLTo.substr(4, 2);
		var d = pActivityLTo.substr(6, 2);
		pActivityLTo = y + "-" + m + "-" + d;
		valid_end = y + "." + m + "." + d;
	} else {
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
	$('#id_number').val(pCardNo);
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
	$('#id_number').val('');
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
	var id_number = $("#id_number").val();
	if (id_number == "") {
		$.alert("请读取身份证!");
		return;
	}
	var sex = $("#customer_sex").val();
	if (sex == "男") {
		sex = "1";
	} else if (sex == "女") {
		sex = "0";
	}
	$("#id_number_result").val($("#id_number").val()); // 身份证
	$("#id_addr_result").val($("#auth_adress").text()); // 证件地址
	$("#id_police_result").val($("#idcard_addr").val()); // 签发地址
	$("#custom_name_result").val($("#customer_name").text()); // 客户姓名
	$("#custom_sex_result").val(sex); // 客户性别
	$("#custom_birth_result").val($("#born_date_val").val()); // 出生日期
	$("#custom_nation_result").val($("#nation_id").val()); // 民族
	$("#start_date_result").val($("#start_date_val").val()); // 生效时间
	$("#end_date_result").val($("#end_date_val").val()); // 实效时间
	$("#photo_code_result").val($("#photo_buffer_val").val()); // 照片编码
	var data1 = {
		"id_number" : $("#id_number_result").val(),
		"id_addr" : trim($("#id_addr_result").val()),
		"id_police" : $("#id_police_result").val(),
		"custom_name" : $("#custom_name_result").val(),
		"custom_sex" : $("#custom_sex_result").val(),
		"custom_birth" : $("#custom_birth_result").val(),
		"custom_nation" : $("#custom_nation_result").val(),
		"start_date" : $("#start_date_result").val(),
		"end_date" : $("#end_date_result").val(),
		"photo_code" : $("#photo_code_result").val(),
		"wt_flag" : wt_flag
	}
	var URL = application.fullPath + "authority/idcard/addIdCard";
	$.ajax({
		url : URL,
		dataType : 'json',
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : 'post',
		data : data1,
		waitMsg : "读取中..",
		success : function(message) {
			$("#idCradImage").attr('src', id_card_image_path + $("#id_number").val() + ".png");
			if (message.type == "error") {
				var dialog = $.dialog({
					title : '提示',// 提示title
					content : '身份证读取失败',// 显示的内容，支持html格式。
					buttons : [ {
						id : 'btn_ok',
						text : '确定',
						onClick : function() {
							dialog.close();
						} // 点击时候回调函数
					} ]
				});
			} else {
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
	 var data1 = {
		"id_number" : id_number,
//		tele_type:"ALL",
		tele_type : "LAN",
		province_code : province_code,
		// 页面信息
		page_info : "base.html",
		wt_flag : wt_flag
	}
	var URL = application.fullPath + "authority/accountOpen/checkCustInfo";
	$.ajax({
		url : URL,
		dataType : 'json',
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : 'post',
		data : data1,
		waitMsg : "客户信息校验中..",
		success : function(message) {
			if (message.type == "error") {
				// $("#cust_check_info").html("<font color='red'>" + message.content + "</font>");
				emptyUserInfo();
			} else {
				var checkCustInfo = message.args.cust_info;
				cust_seq_id = message.args.cust_seq_id;
				dealCustInfo(checkCustInfo);
				if (total_fee > 0 && tele_type == "2G") {
					$.alert("该客户历史欠费" + total_fee.toFixed(2) + "元，请清欠后来开户");
				} else {
					loadOldNumberList();
				}
			}
			checkStep1Next();
		}
	});
};

// 获取客户老号码信息
function loadOldNumberList() {
	var data1 = {
		'oper_id' : oper_id, // 操作员工号
		'customer_id' : $('#id_number').val() // 身份证号码
	}
	var URL = application.fullPath + 'authority/woJiaOpen/qryAllNumberList';
	$.ajax({
		url : URL,
		dataType : 'json',
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : 'post',
		data : data1,
		waitMsg : "获取客户老号码信息..",
		success : function(message) {
			// 清空手机和宽带老号码列表
			$('#old_phone_info>ul>li').remove();
			$('#old_lan_info>ul>li').remove();
			
			if (message.type == "error") {
				$.alert(message.content);
			} else {
				// 加载手机和宽带老号码列表
				$.each(message.args.numberList, function(i, item) {
					if (item.tele_type == 'LAN' || item.tele_type == 'M165') {
						var html = '<li class="list_old_number" cbssAccessCode="'+ item.cbssAccessType +'" spendLevel="'+  item.spendLevel +'" zb_product_id="" product_id="' + item.product_id + '" device_number="' + item.device_number + '" business_flag="' + item.business_flag + '" tele_type="' + item.tele_type + '" devices_products="' + item.devices_products + '" exch_code="'+item.broadBandExchCode+'">'
								+ '<div>'
								+ '<div class="inline_block">业务号码：' + item.device_number + '</div>'
								+ '<input type="radio" class="float_right" name="radio_old_lan" />'
								+ '</div>'
								+ '<div>'
								+ '<div class="inline_block">资<span class="space24"></span>费：' + item.devices_products + '</div>'
								+ '</div>'
								+ '</li>';
						$('#old_lan_info>ul').append(html);
					} else {
						var imgPathTeleType = application.fullPath + 'images/tele_type_' + item.tele_type + '.png';
						var html = '<li class="list_old_number" product_id="' + item.product_id + '" device_number="' + item.device_number + '" business_flag="' + item.business_flag + '" tele_type="' + item.tele_type + '">'
								+ '<div>'
								+ '<div class="inline_block">'
								+ '<span>手机号码：' + item.device_number + '</span>'
								+ '<img src="' + imgPathTeleType + '" width="22px" height="22px"/>'
								+ '</div>'
								+ '<input type="radio" class="float_right" name="radio_old_phone" />'
								+ '</div>'
								+ '<div>'
								+ '<div class="inline_block">资<span class="space24"></span>费：' + item.devices_products + '</div>'
								+ '</div>'
								+ '</li>';
						$('#old_phone_info>ul').append(html);
					}
				});
				// 提示框
				var dialog = $.dialog({
					title : '提示',// 提示title
					content : '校验成功',// 显示的内容，支持html格式。 TODO 成功文字改成校验成功
					buttons : [ {
						id : 'btn_ok',
						text : '确定',
						onClick : function() {
							dialog.close();
						}
					} ]
				});
			}
		}
	});
}

function createOrderId(){
	 var data1 = {
		tele_type : "LAN",
		order_sub_type : order_sub_type,
		wt_flag : wt_flag
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
			if (message.type == "error") {
				var dialog=$.dialog({
					   title:'提示',//提示title
					   content:'订单生成失败',//显示的内容，支持html格式。
					   buttons:[{id:'btn_ok',text:'重新生成订单',
							   onClick:function() {
								   dialog.close();
								   createOrderId();
							   }
								   
							}]
					});
			} else {
				teleTypeChngFlag = "1";
				var order_id = message.args.order_info.order_id;
				phone_flag =  message.args.phone_flag;
				oper_no =  message.args.oper_no;
				dept_no = message.args.dept_no;
				$("#order_id").val(order_id);
			}
		}
	});
};

/**
 * 清除用户信息
 */
function emptyUserInfo(){
	//$("#id_number").val("");//身份证号码
	$("#id_type").val("");
	$("#cust_flag").val("");

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
function dealCustInfo(checkCustInfo) {
	if (!checkCustInfo) {
		emptyUserInfo();
		$.alert("客户资料校验没有任何信息!");
		return;
	}
	black_flag = checkCustInfo.black_flag;

	var cust_flag = checkCustInfo.cust_flag;
	cust_id = checkCustInfo.cust_id;
	var max_user_flag = checkCustInfo.max_user_flag;
	var connect_person = checkCustInfo.connect_person;
	var connect_phone = checkCustInfo.connect_phone;
	var connect_addr = trim(checkCustInfo.connect_addr);
	if (connect_addr == '' || connect_addr == null) {
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
	// 离网欠费owe_fee
	var owe_fee = checkCustInfo.owe_fee;

	var acc_nbr = "";
	var arr_fee = "";
	Array.prototype.contains = function(item) {
		return RegExp("\\b" + item + "\\b").test(this);
	};
	total_fee = 0;

	if (owe_fee != '') {
		total_fee += parseFloat(owe_fee);
	} else if (user_info != null) {
		for (var i = 0; i < user_info.length; i++) {
			var shares = new Array();

			if (user_info[i].pay_no != '') {
				if (!shares.contains(user_info[i].pay_no)) {
					total_fee += parseFloat(user_info[i].arr_fee);
				}
				shares.push(user_info[i].pay_no);
			} else {
				total_fee += parseFloat(user_info[i].arr_fee);
			}
		}
	}
		
	$("#id_type").val(id_type);
	$("#cust_flag").val(cust_flag);
	$("#customer_name").html(customer_name);
	$("#auth_adress").html(trim(auth_adress));// 地址
	if (connect_phone) {
		$("#connect_phone").val(trim(connect_phone));// 联系电话
	} else {
		$("#connect_phone").val(select_acc_nbr);// 联系电话
	}
		
	if (connect_addr) {
		$("#connect_addr").height("20px");
		$("#connect_addr").val(trim(connect_addr));// 联系地址
	} else {
		$("#connect_addr").val(trim(auth_adress));// 如果为空,默认回填地址
	}
	if (customer_name == null) {
		emptyUserInfo();
	} else {
		$('#step1_next_div').removeClass('ok_disabled').addClass('ok');
		$('#step1_modify_div').removeClass('ok_disabled').addClass('ok');
		/**
		 * 回显信息
		 */
		if (black_flag == "1") {
			$("#cust_check_info").html("<font color='red'>该客户为（黑名单）</font>");// 姓名
		} else if (total_fee > 0 && tele_type == "2G") {
			/*
			 * if(tele_type=="3G"||tele_type=="4G"){
			 * $("#cust_check_info").html("（该客户历史欠费"+total_fee.toFixed(2)+"元，可点“确定”继续办理，清欠可在原系统操作）");//姓名
			 * }else{
			 */
			$('#step1_next_div').removeClass('ok').addClass('ok_disabled');
			$('#step1_modify_div').removeClass('ok').addClass('ok_disabled');
			$("#cust_check_info").html("<font color='red'>（该客户历史欠费" + total_fee.toFixed(2) + "元，请清欠后来开户）</font>");// 姓名
			// }
		} else {
			$("#cust_check_info").html("<font color='red'>" + "校验成功" + "</font>");
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
			tele_type = "4G";
		}else{
			tele_type = "2G";
		}
		if(mob_section=="10000"){			
//			tele_type = "ALL";
			tele_type = "4G";
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
//		mob_section="185";
		mob_section = "";
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
					var html = '<div class="wrap_line"  id="'+i+'wrap\"  deviceNumber="'+numberBean.acc_nbr+'\,'+numberBean.first_prepay+'\,'+numberBean.mon_limit+'\">'
					+'<a href="javascript:void(0);" onClick=\"jump(\''+numberBean.acc_nbr+'\',\''+numberBean.first_prepay+'\',\''+numberBean.mon_limit+'\',\''+numberBean.on_net+'\',\''+numberBean.lock_flag+'\',\''+numberBean.class_id+'\')" class="code" id="'+numberBean.acc_nbr+'"> <span class="join" style="display:none;" id="'+numberBean.acc_nbr+'progress\">加入备选</span><dl><dt>'+numberBean.acc_nbr+'<dd>'
					//+'首次预存'+numberBean.first_prepay+','+'月消费'+numberBean.mon_limit+'元</dd>'
					+'首次预存'+numberBean.first_prepay+'元</dd>'
					+'  </dl></a></div>';
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
	if (province_code == "cq") {
		numberOccupyPre(acc_nbr,first_prepay,mon_limit,on_net,lock_flag,class_id);
	} else {
		if (province_code != "nx" && tele_type == "2G") {
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
		} else if (province_code == "nx"&&tele_type=="2G") {
			var GetURl= application.fullPath + "authority/numberData/nxGetMonLimitByNumber";
			var low_fee = '0';
			$.ajax({
				url:GetURl,
				data:{
					"device_number":acc_nbr,
					//"device_number":"13209555555",
					"wt_flag":wt_flag
				},
				dataType:'json',
				type:'post',
				waitMsg:"号码信息获取中...",
				success:function(message){
					if(message.type == "success"){
						mon_limit = message.args.numberBean.mon_limit;
						numberOccupyPre(acc_nbr,first_prepay,mon_limit,on_net,lock_flag,class_id);
					}
					else{
						$.alert(message.content);
					}
				}
			});
		} else {
			numberOccupyPre(acc_nbr,first_prepay,mon_limit,on_net,lock_flag,class_id);
		}
	}
 }

function numberOccupyPre(acc_nbr,first_prepay,mon_limit,on_net,lock_flag,class_id){
	if(mon_limit==null ||mon_limit==""){
		mon_limit = '0';
	}
	var dialog=$.dialog({
		   title:'提示',//提示title
		   content:'预占号：['+acc_nbr+']'+'<br>月消费：['+mon_limit+']元',//显示的内容，支持html格式。
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
		tele_type_num = "4G";
	}else{
		tele_type_num = "2G";
	}		 
	//tele_type = tele_type_num;	 
	if(temp=="145"){
		tele_type_num = "3G";
	}
	 
	var data1={
			tele_type: tele_type_num,
			//传入号码
			acc_nbr: acc_nbr,
			ser_type: "1",
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
//					if($("#connect_phone").val()==''||$("#connect_phone").val()==null){
//						$("#connect_phone").val(acc_nbr);
//					}
//					checkStep2Complete();
					
					var dialog=$.dialog({
						   title:'提示',//提示title
						   content:'号码：['+acc_nbr+']预占成功',//显示的内容，支持html格式。
						   buttons:[
						            {id:'btn_ok',text:'下一步',
						             onClick:function(){
						            	 dialog.close();
						            	 $('#number_' + curNumIndex).text(acc_nbr);
						            	 $('#number_' + curNumIndex).attr('first_prepay', first_prepay);
						            	 $('#number_' + curNumIndex).attr('mon_limit', mon_limit);
						            	 $('#number_' + curNumIndex).attr('class_id', class_id);
						            	 $('#number_' + curNumIndex).attr('installMode', "0"); // 0:新装；1:纳入；2:迁转
						            	 $('#number_' + curNumIndex).addClass('num_new'); // 增加老号码标识
						            	 $('#new_phone_info').hide();
						            	 $('#step2_info').show();
						            	 window.location.href = "#tab_taocan";
						            	 checkHasNumber();
						             }}
						           ]
					}); 														
				}				
			}
		});	
};

function startTime(){
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

//查询活动类型
function GetActivityType(){
	activityDelete();
	ywbDelete();
	$("#activity_type_select").html("<option value='0'></option>");
	giftActivityDelete();
	$("#guarantee_type_select").html("<option value='0'></option>");
	 var data1={
				"product_id":ofr_id,
				"tele_type":tele_type,
				"terminal_model_code":model
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

//function checkStep2Complete(){
//	if(select_acc_nbr==''||$("#tariff_select").val()==''){
//		$('#step2_next_div').removeClass('ok').addClass('ok_disabled');
//		$('#step2_modify_div').removeClass('ok').addClass('ok_disabled');
//		$("#step2_next_div").hide();
//	}else{
//		var activity_type = $("#activity_type_select").val();
//		if(activity_type=="ZSYW001"&&(discnt_code==null||discnt_code=='')){
//			$('#step2_next_div').removeClass('ok').addClass('ok_disabled');
//			$('#step2_modify_div').removeClass('ok').addClass('ok_disabled');
//			$("#step2_next_div").hide();
//		}else{
//			$('#step2_next_div').removeClass('ok_disabled').addClass('ok');
//			$('#step2_modify_div').removeClass('ok_disabled').addClass('ok');
//			$("#step2_next_div").show();
//		}
//
//	}
//}
/*资费相关JS ==============结束=====================*/

/*费用 相关JS  =====================开始======================*/
function getFee(){
	 var first_month_fee = '';
	 var product_id_tmp = '';
	 var activity_id_tmp = '';
	 var activity_type_tmp = '';
		 
//	if(combined_product_id==''){
		first_month_fee = $('#first_month_fee_list').find('.radio_button_checked').attr('id');
		product_id_tmp = $('[name="liuliangbao"]:checked').attr('id') // data_tariff.product_id; // TODO: 产品id是指哪个
		activity_id_tmp = ''; // data_activity.activity_id; // TODO: 没有活动id
		activity_type_tmp = ''; // data_activity.activity_type;	// TODO: 没有活动类型
//	}else{
//		first_month_fee = $("#first_month_fee_select_group").val();
//		product_id_tmp = data_group.ofr_id;
//		activity_id_tmp =  data_group.activity_id;
//		activity_type_tmp =  data_group.activity_type;	
//	}	

	var data1 = {
		'order_id': $('#order_id').val(), // Test Data: "20160530100000023553"
		// 产品编号
		'product_id': "",
		// 号码类型
		'tele_type': "LAN",
		// 号码计费
		'acc_nbr_fee': "",
		// 这个参数 暂时写死 ,1-后付费 2-预付费
		'prepay_flag': "1",
		'oper_no': oper_no, // Test Data: "CF0540"
		'cust_flag': $("#cust_flag").val() // Test Data: "1"
	};
	var URL = application.fullPath + "authority/woJiaOpen/orderBusiFeeQry";
	fee_all = 0;
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		data:data1,
		waitMsg:"获取费用信息中..",
		success:function(message){
			if(message.type == "error"){
				$("#fee_all").html("0");
				$('#step6_next_div').removeClass('ok').addClass('ok_disabled');
				$("#btnGetFeeAgain").show();	
				$.alert(message.content);
			}else{
				$('#step6_next_div').removeClass('ok_disabled').addClass('ok');
				$("#btnGetFeeAgain").hide();
				$("#fee_list").find('.dynamic').remove();
				//初始化流水交易号
				m_reference = message.args.trade_no;
				fee_info = message.args.fee_info;
				num_infos = message.args.number_infos;
				//为清库终端时，限制不能调整合约款和合约预存款，并且在费用项上新增显示清库优惠价
//                if(infoTerminalClearStoreVo != undefined && infoTerminalClearStoreVo != null){
//                    var h1 = "100005";		//合约款的fee_code
//                    var h2 = "4310";		//合约预存款的fee_code
//                    $.each(message.args.fee_info, function(i, item) {
//                    	if(item.fee_code == h1 || item.fee_code == h2){
//                    		item.discount_flag = "1";
//                    	}
//                    });
//                    
//                    var yh = {"fee_code":"清库优惠价CODE","fee_name":"清库优惠价", "raw_amount":-infoTerminalClearStoreVo.discount_charge, "rec_amount":-infoTerminalClearStoreVo.discount_charge, 
//                    			"discount_flag":"1"};
//                    message.args.fee_info.push(yh);
//                    
//                    discountAmount = -parseFloat(infoTerminalClearStoreVo.discount_charge);
//                }
				
				var content = '';
				var feeCount = message.args.fee_info.length;
                $.each(message.args.fee_info, function(i, item) {
					//加载费用信息
                	var data = JSON.stringify(item);
					if(item.fee_name=="[预存]" && tele_type=="4G" && parseInt(select_mon_limit)>0){
						item.fee_name ="[靓号预存]";
					}
					if (i % 2 == 0) {
						content += '<div class="padding_blank10 dynamic"></div>'
								+ '<li class="list dynamic" style="height:auto;">';
					}
					if (i % 2 == 0) {
						if (item.discount_flag == '1') { // 不可减免
							content += '<div class="left fee_confirm_div">'
								+ '<div class="bold">' + item.fee_name + '</div>'
								+ '<div class="left_lable margin_left_1">'
								+ '应收：<span id="">' + item.rec_amount + '</span>&nbsp;元'
								+ '</div>'
								+ '<div class="right_data">'
								+ '实收：<input type="text" placeholder="0.00" readonly="true" id="charge_item' + i + '" code="' + item.fee_code + '" name="' + item.fee_name + '" data="' + item.rec_amount + '" value="' + item.rec_amount + '" onChange="CheckChargeItem(this,1);" class="input_text width_8 text_normal_b red">&nbsp;元' 
								+ '</div>'
								+ '</div>';
						} else if (item.discount_flag == '2') { // 不可小于该值
							content += '<div class="left fee_confirm_div">'
								+ '<div class="bold">' + item.fee_name + '</div>'
								+ '<div class="left_lable margin_left_1">'
								+ '应收：<span id="">' + item.rec_amount + '</span>&nbsp;元'
								+ '</div>'
								+ '<div class="right_data">'
								+ '实收：<input type="text" placeholder="0.00" id="charge_item' + i + '" code="' + item.fee_code + '" name="' + item.fee_name + '" data="' + item.rec_amount + '" value="' + item.rec_amount + '" onChange="CheckChargeItem(this,2);" class="input_text width_8 text_normal_b red">&nbsp;元' 
								+ '</div>'
								+ '</div>';
						} else {
							content += '<div class="left fee_confirm_div">'
								+ '<div class="bold">' + item.fee_name + '</div>'
								+ '<div class="left_lable margin_left_1">'
								+ '应收：<span id="">' + item.rec_amount + '</span>&nbsp;元'
								+ '</div>'
								+ '<div class="right_data">'
								+ '实收：<input type="text" placeholder="0.00" id="charge_item' + i + '" code="' + item.fee_code + '" name="' + item.fee_name + '" data="' + item.rec_amount + '" value="' + item.rec_amount + '" onChange="CheckChargeItem(this,1);" class="input_text width_8 text_normal_b red">&nbsp;元' 
								+ '</div>'
								+ '</div>';
						}
					} else {
						if (item.discount_flag == '1') { // 不可减免
							content += '<div class="right fee_confirm_div">'
								+ '<div class="bold">' + item.fee_name + '</div>'
								+ '<div class="left_lable margin_left_1">'
								+ '应收：<span id="">' + item.rec_amount + '</span>&nbsp;元'
								+ '</div>'
								+ '<div class="right_data">'
								+ '实收：<input type="text" placeholder="0.00" readonly="true" id="charge_item' + i + '" code="' + item.fee_code + '" name="' + item.fee_name + '" data="' + item.rec_amount + '" value="' + item.rec_amount + '" onChange="CheckChargeItem(this,1);" class="input_text width_8 text_normal_b red">&nbsp;元' 
								+ '</div>'
								+ '</div>';
						} else if (item.discount_flag == '2') { // 不可小于该值
							content += '<div class="right fee_confirm_div">'
								+ '<div class="bold">' + item.fee_name + '</div>'
								+ '<div class="left_lable margin_left_1">'
								+ '应收：<span id="">' + item.rec_amount + '</span>&nbsp;元'
								+ '</div>'
								+ '<div class="right_data">'
								+ '实收：<input type="text" placeholder="0.00" id="charge_item' + i + '" code="' + item.fee_code + '" name="' + item.fee_name + '" data="' + item.rec_amount + '" value="' + item.rec_amount + '" onChange="CheckChargeItem(this,2);" class="input_text width_8 text_normal_b red">&nbsp;元' 
								+ '</div>'
								+ '</div>';
						} else {
							content += '<div class="right fee_confirm_div">'
								+ '<div class="bold">' + item.fee_name + '</div>'
								+ '<div class="left_lable margin_left_1">'
								+ '应收：<span id="">' + item.rec_amount + '</span>&nbsp;元'
								+ '</div>'
								+ '<div class="right_data">'
								+ '实收：<input type="text" placeholder="0.00" id="charge_item' + i + '" code="' + item.fee_code + '" name="' + item.fee_name + '" data="' + item.rec_amount + '" value="' + item.rec_amount + '" onChange="CheckChargeItem(this,1);" class="input_text width_8 text_normal_b red">&nbsp;元' 
								+ '</div>'
								+ '</div>';
						}
					}
					if ((i % 2 != 0) || (i + 1 == feeCount)) {
						content += '</li>';
					}
					fee_all = parseFloat(item.rec_amount) + parseFloat(fee_all);
                });
                $("#fee_list").prepend(content);
                $("#fee_all").html(fee_all);
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
	$("#fee_all").html(fee_all);
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

//订单更新接口
function nextFlowStepClick(type) {
	$scope.next_type = type;
    //memcache存合计金额
    //var str = {"total_id": $scope.getTotalFee(), "pay_type": $scope.payType.code_name};
   // var value_str = JSON.stringify(str);
    //存的code_id
   // var code_type_id = {"type_code_id": $scope.payType.code_id};
    //console.info("要存的数据是"+value_str);
    //sesssion存数据
    //电子签名用的数据
   // PV_UTIL.setPageVal("total_id_value", str);
    //PV_UTIL.setPageVal("code_type_id", code_type_id);
    //PV_UTIL.setPageVal("tele_type", $scope.m_tele_type);
    //console.info("存到数据了"+value_str);
    //订单更新接口开始
    var returnFlag = false;
    var jsonObj = {
        "pay_type": $scope.payType.code_id,
        "pay_type_code": $scope.payType.code_id,
        "totalFee": $scope.totalFee,
        "writecar_success":"0",//写卡成功个数
        "fee_info": [],
        "nubm_info":[]
    };
        //for循环为订单更新添加内容
        for (var i = 0; i < $scope.chargeItems.length; i++) {
            //服务器费用只支持字符
            var fee_result = {
                "fee_code": $scope.chargeItems[i].fee_code,
                "fee_class": $scope.chargeItems[i].fee_class,
                "fee_name": $scope.chargeItems[i].fee_name,
                "rec_amount": $scope.chargeItems[i].raw_amount+ "",
                "rel_amount": $scope.chargeItems[i].rec_amount+ ""
            };
            jsonObj.fee_info.push(fee_result);      
        }
        //for循环为订单更新添加内容
        for (var j = 0; j < $scope.numberItems.length; j++) {
        	
            var nubm_result = {
                "number": $scope.numberItems[j].number,
                "subOrderId": $scope.numberItems[j].subOrderId                 
            };
         
        jsonObj.nubm_info.push(nubm_result);          

     }
      jsonObj.nubm_info=JSON.stringify(jsonObj.nubm_info) ;     
         
    PV_UTIL.setPageVal("fee_info_list", JSON.stringify(jsonObj.fee_info));//费用存入缓存
    //console.info(jsonObj);
    //订单更新
    ORDER_UPDATE.doDirUpdateOrder(jsonObj, 5, false, function (flag) {
        if (flag) {
        	returnFlag = jiaofu();
        } else {
        	return false;
        }
    });
    //原来交付方式页面的处理放在jiaofu里面
   
    return returnFlag;
};

//原来交付页面需要做的处理
function jiaofu(){
		var send_type="前台";	
		var send_type_value=send_type;
		var jsonData =PV_UTIL.getPageVal("woChargeItem","total_id_value");    
	   var str={"send_type":send_type_value};
	   //var value=PV_UTIL.getPageVal("wbChargeItem","total_id_value");
	   var pay_type=jsonData.pay_type;
	   var	returnFlag1="";
	   $.extend(jsonData,str);	   		
	   PV_UTIL.updatePageVal("woIntentOrder","order_info",jsonData);
	    PV_UTIL.deletePageVal("woInfo","taocan_list");
	    PV_UTIL.deletePageVal("woInfo","address_list");  	  
	       var flag_PV = PV_UTIL.doMemCache();

	    	var returnFlag = false;
	    	var result = {"bill_send":'',
	    	              "logistics_type":send_type,
	    	              "send_content":'',
	    	              "invoice_code":'',
	    	              "essKey":'',
	    	              "ofr_sub_type_3g":'',
	    	              "pro_order_id":''};
	    //订单更新事件  上面是传的参数  大部分都没有
	    	if(flag_PV){
 	        ORDER_UPDATE.doDirUpdateOrder(result,6,false,function(flag){
				if(flag){
					//var operInfo = $.parseJSON(CACHE_UTIL.getLocalItem("operInfo"));
					var _proviceCode = HTML_UTLS.getParam("province_code");
					//if(operInfo.province_code == "cq"){//重庆无PDF接口修改
					if(_proviceCode== "cq"||_proviceCode== "nx"||_proviceCode== "hn"){	
						tijiaodingdan($scope.m_jessionid,$scope.order_id);
					}		  					
				}else{
					return false;
				}
			    });	
	    	}else{
	    		return false;
	    	}
	     	    
			return returnFlagFinal;
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
	var phoneRegex = /^(13[0-9]|15[012356789]|18[02356789]|17[02356789]|14[57]|17[0])[0-9]{8}$/;
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

var posCardFlag = 0;
function dealFee() {
	posCardFlag++;
	if (posCardFlag > 1) {
		$.alert("请不要重复点击收费按钮");
		return;
	}
	// 目前只支持现金缴费
	dodealFee();
}

  // 调用接口收费部分
  function dodealFee(){  
	  /*-----------------------调用付费接口开始-------------------------------------------------*/
		var data={
			"order_id":$("#order_id").val(),
			"trade_no": document.getElementById("reference").value,
			"acc_nbr":fee_all,
			"payType":"201",
			"order_sub_type":$("#order_sub_type").val()
		};
	    //收费接口url
	  var URL="";
	  var type=$("#wo_type").val();
      //智慧沃家
	   URL =application.fullPath + "authority/accountOpen/doLanAccountOpen";//智慧沃家开户url  
	    $.ajax({
			type : "POST",
			url : URL,
			data : data,
			dataType:'json',
			waitMsg:"请稍等......",
			success:function(message){
				var jsonObj=eval(message);
				if(jsonObj.type=='success'){
					    							
					/*-----------------------调用更新订单接口开始-------------------------------------------------*/
					var data2={
							"order_id":$("#order_id").val(),
							"trade_no": m_reference,
							"acc_nbr":"300",
							"pay_type":"201",
							"tele_type":"LAN",
							"order_sub_type":$("#order_sub_type").val()					
						};
				    var URl = application.fullPath + "authority/dealShowOrder/updateOrderFee";
			       $.ajax({
						type : "POST",
						url : URl,
						data : data2,
						dataType:'json',
						waitMsg:"请稍等......",
						success:function(message){
							var jsonObj=eval(message);
							if(jsonObj.type=='success'){								
								
								$("#btnGetFee").removeAttr("onClick");
								$("#btnGetFee").removeClass("view_btn");
								$("#btnGetFee").addClass("btn_disabled");															
								$("#readCard").removeClass("btn_disabled");
								$("#readCard").addClass("view_btn");
								//$("#readCard").attr("onclick","readCardBtn();");
								$("#writeCard").removeClass("btn_disabled");
								//$("#writeCard").attr("onclick","writeCardBtn();");
								$("#writeCard").addClass("view_btn");
								$("#readCard").bind("click",function (){
									readCardBtn();					
								});
								$("#writeCard").bind("click",function (){
									writeCardBtn();					
								});
							    $("#getFee").text("(已收费)");
							    $("#payFlag").attr("value","Y");
							    var type= $("#wo_type").val();
							    if(type=='2'||(type=='3'&&fa_phone_number!=null&&fa_phone_number!="")){//宽带单装、沃家庭旧手机号码
								    $("#okModule").removeClass("ok_disabled"); 
									$("#okModule").addClass("ok");
									$("#okSubmit").bind("click",function (){
										okSumbit();					
									});
							    }
							    if(finishFlag){//无写卡号码直接掉用接口更新订单为完工状态
							    	finishOrder();
							    }else{
							    	 $.alert("收费成功");
							    }
							   
						}else{
							posCardFlag  = 0;
							var desc = jsonObj.content;
				    		$.alert(desc);
						   //$("html,body").animate({scrollTop: $("#box3").offset().top}, 10);
						}
					},
			    	error:function(message){
			    		posCardFlag  = 0;
			    		var jsonObj=eval(message);
						var desc = jsonObj.content;
			    		$.alert(desc);
			    		//$("html,body").animate({scrollTop: $("#box3").offset().top}, 10);
					}
			    });
			 
			}else{	
				posCardFlag  = 0;
				var jsonObj=eval(message);
				var desc = jsonObj.content;
	    		$.alert(desc);				
			}
		},
  	error:function(message){
  		posCardFlag  = 0;
  		var jsonObj=eval(message);
  		var desc = jsonObj.content;
  		$.alert(desc);
		}
    });
	  
  }

//产品映射查询
function getElementInfos(obj) {
	var URl = application.fullPath + "authority/woJiaOpen/queryZbOfr";
	$.ajax({
		type: "POST",
		url: URl,
		data: {'element_id':$(obj).attr('id')},
		dataType: 'json',
		async:false,
		waitMsg: "正在查询映射关系...",
		success: function(message) {
			var aop_product_id = "";
			var infos = message.args.zb_list;
			if(infos!=''&&infos!=null){
				for(var t = 0;t < infos.length; t++){
					aop_product_id = infos[t].aop_product_id;//传aop定义的产品ID
					if(aop_product_id==null||''==aop_product_id){
						$.alert("该资费没有约定好打包，请选择其他资费！");
					} else {
						$(obj).attr('id', aop_product_id);
						$(obj).attr('packageId', infos[t].package_id);
						$(obj).attr('elementId', infos[t].element_id);
						$(obj).attr('elementType', infos[t].element_type);
						$(obj).attr('is_mapped', 'false');
					}
				}
			}else{
				$.alert("该资费没有约定好打包，请选择其他资费！");
			}
		},
		error:function(message){
			var jsonObj=eval(message);
			var desc = jsonObj.content;
			$.alert(desc);
		}
	});
}

function okSumbit(){
	
	/*对于已经竣工的单子不能修改交付方式*/
   var order_status = $("#order_status").val();//订单状态
   if(end_open=='1'&&order_status=='A30'){//已点击完成按钮
	   $.alert("订单已经完成!");
   }else{
    var b = document.getElementById("b").checked;
	var b2= document.getElementById("b2").checked;
	var b3 = document.getElementById("b3").checked;
	var give_type="";
	var receivePhone="";
	var receiveAddress="";
	var end_time="";
	if(b){
		give_type="1";
    }
	if(b2){
		give_type="2";
		 var receivePhone= $("#receivePhone").val();
		 var receiveAddress= $("#receiveAddress").val();
		 if(receivePhone=="请输入收货人号码"){
			 $.alert("请输入收货人号码");
			 return;
		 }
		 if(receiveAddress=="请输入收货人地址"){
			 $.alert("请输入收货人地址");
			 return;
		 }
		
	}
    if(b3){
    	give_type="3";
    	 var end_time= $("#end_time").val();
	}
    var data={
    		"order_id":$("#order_id").val(),
    		"give_type":give_type,
    		"receivePhone":receivePhone,
    		"receiveAddress":receiveAddress,
    		"end_time":end_time,
    		"end_open":"1"
    	};
    			//提交
				 var URL = application.fullPath + "authority/dealShowOrder/dealGive"; 
		            $.ajax({
		        		type : "POST",
		        		url : URL,
		        		data : data,
		        		async:true,
		        		dataType:'json',
		        		waitMsg:"请稍等......",
		        		success:function(message){	
		        			 var content = '<div class="msgbox"><div class="serial">订单开户完成</div><div class="msgbox_ok"><a href="###" id="okModou">确定</a></div></div>';
		 		  			$.dialog({
		 		  				width:400,
		 		  				draggable:false,
		 		  				content:content
		 		  			});

		 		  			$("html,body").animate({scrollTop: $("#box1").offset().top}, 10);
		 		  			$("#okModou").bind("click",function(){
		 		                   $(".xxDialog").remove();
		 		                   $(".dialogOverlay").remove();
		 		                   
		 		                  
		 		  			});
		 		  		$("#okSubmit").removeAttr("onclick");
		 				$("#okModule").removeClass("ok");
		 				$("#okModule").addClass("ok_disabled");	
		        		}
		            });
		
}
}

   /**
    * 宁夏用，查询分局地址
    */
 function getfjName(){
	 var data = {};
	 var URL = application.fullPath + "authority/woJiaOpen/selectRuleAreaHx"; 
     $.ajax({
 		type : "POST",
 		url : URL,
 		data : data,
 		async:true,
 		dataType:'json',
 		waitMsg:"请稍等......",
 		success:function(message){
 			var data = message.args.fenjuList;
 			if(data =="" || data == null){
 				$.alert("没有查询到分局信息！");
 			}else{
 				$("#fj_search").show();
 				//整理列表显示
 				$("#fj_list").html("");
 				//+ '<input name="zjdz_search_check" onClick="zjdzSelect(this)" type="radio" data=\'' + data + '\' id="' + item.addr_code + '"/></div></div></li>');

 				$.each(data,function(i,item){
 					var dataTemp = item;
 					
 					$("#fj_list").append('<li class="list"><div class="left"><div class="left_lable">'
							+ '<a class="tip" href="javascript:void(0)">'
							+ dataTemp.area_name
							+ '</a>'
							+ '</div><div class="right_data">'								
							+ '<input name="fj_seach_check" type="radio" onclick="fjSelect(this)" data=\'' + JSON.stringify(dataTemp) + '\' id="' + dataTemp.addr_code + '"/></div></div></li>');
 					/*
 					$("#fj_list").append("<li class='list fenju_list' id='lan_fenju_" + i + "' data='" + dataTemp + "'><div class='icon-right icon-point'></div><div class='rightonly'><div class='holder'>" + dataTemp.area_name + "</div></div>"
 					+"<input type='radio' name='fj_seach_checl' data='" + dataTemp +"' id='" + dataTemp.area_code +"'/>"
 					+"</li>");*/
 				});
 				$("#fj_list").append("<li class='clear'></li>");
				$("#fj_list").append("<li class='bottom_blank'></li>");
 			}
 		}
     });
 }
/*宁夏分局相关*/
 var data_fjdz = "";
 var addressCode_fj = "";
 function fjSelect(_this){
		var div = $(_this);
		var dataStr = div.attr('data');	
		data_fjdz = JSON.parse(dataStr);	
	}

	function fjConfirm() {
		hidediv('fj_search');
		$('#fj_select').val(data_fjdz.area_name);
		addressCode_fj = data_fjdz.hx_area_id;
	}

	function fjDelete() {
		hidediv('fj_search');
		$('#fj_select').val('');
		addressCode_fj = '';
	}
/**
 * 校验老宽带映射编码
 * @param _this
 */
function checkLanProductRelation(_this){
	var productId = _this.attr("product_id");
	 var data = {
			 "product_id":productId
	 };
	 var URL = application.fullPath + "authority/woJiaOpen/queryZbOfr"; 
    $.ajax({
		type : "POST",
		url : URL,
		data : data,
		async:true,
		dataType:'json',
		waitMsg:"正在校验该宽带资费。。。",
		success:function(message){
			var zb_list = message.args.zb_list;
			if(zb_list != null && zb_list != "" && zb_list.length != 0){
				for(var i = 0;i < zb_list.length; i++){
					_this.attr("zb_product_id",zb_list[i].aop_product_id);
				}
			}else{
				$.alert("该资费没有约定好打包，请选择其他资费！");
				return "";
			}
		},error:function(message){
			var jsonObj=eval(message);
			var desc = jsonObj.content;
			$.alert(desc);
		}
    });
    return "ok";

}

function check23To4(_device_number){
	 var data = {
			 "oper_id":oper_id,
			 "deviceNumber":_device_number
	 };
	 var URL = application.fullPath + "authority/woJiaOpen/aopCheck23to4"; 
    $.ajax({
		type : "POST",
		url : URL,
		data : data,
		async: true,
		dataType:'json',
		waitMsg:"正在对该号码进行23转4校验。。。",
		success:function(message){
			var errorList = message.args.returnInfo.responseInfo;
			if(errorList != null && errorList.length != 0){
				 
				var content = '<div class="msgbox" style="max-height:260px; overflow-y:auto;"><ul>';
		  			
				for(var i = 0 ; i < errorList.length; i++){
					content += '<li style="height:auto;">' + (i+1) + '.'  +errorList[i].respDesc+'</li>';
				}
				content+='</ul></div>';
				var dialog = $.dialog({
					width : 400,
					top : 40,
					title : '提示消息',// 提示title
					content : content,// 显示的内容，支持html格式。
					buttons : [ {
						id : 'btn_ok',
						text : '确定',
						onClick : function() {
							dialog.close();
						}
					} ]
				});
				$('#old_phone_confirm_div').removeClass('ok').addClass('ok_disabled');
			}
		},
		error:function(message){
			var jsonObj=eval(message);
			var desc = jsonObj.content;
			$.alert(desc);
			$('#old_phone_confirm_div').removeClass('ok').addClass('ok_disabled');
		}
    });
}

// 省略显示字符串
function omitDisplay(str, begin, end) {
	return getByteLen(str) > 50 ? getByteSubstring(str, begin, end) + '...' : str;
}

// 判断字符串长度（汉字算两个字符，字母数字算一个字符）
function getByteLen(str) {
	var len = 0;
	for (var i = 0; i < str.length; i++) {
		var c = str.charCodeAt(i);
		if (c >= 0 && c <= 128) {
			len += 1;
		} else {
			len += 2;
		}
	}
	return len;
}

// 截取字符串（汉字算两个字符，字母数字算一个字符）
function getByteSubstring(str, begin, end) {
	var result = '';
	var index = -1;
	for (var i = 0; i < str.length; i++) {
		var c = str.charCodeAt(i);
		if (c >= 0 && c <= 128) {
			index += 1;
		} else {
			index += 2;
		}
		if (index >= begin && index <= end) {
			result += str.substring(i, i + 1);
		}
	}
	return result;
}
/**
 * 不需要写卡的时候直接更新订单状态为完成
 */
function finishOrder(){

	 var data = {
			 orderId:$("#order_id").val()
	 };
	 var URL = application.fullPath + "authority/woJiaOpen/finishOrder"; 
   $.ajax({
		type : "POST",
		url : URL,
		data : data,
		async: true,
		dataType:'json',
		waitMsg:"最后更新订单。。。",
		success:function(message){
			$.alert("开户成功!");
		},
		error:function(message){
			$.alert(message.args.RespDesc);
		}
   });

}
/**
 * 23转4固网校验
 * @param _device_number
 */
function check23To4M165(_device_number){
	 var data = {
			 "oper_id":oper_id,
			 "deviceNumber":_device_number,
			 "tele_type":"M165"
	 };
	 var URL = application.fullPath + "authority/woJiaOpen/aopCheck23to4"; 
   $.ajax({
		type : "POST",
		url : URL,
		data : data,
		async: true,
		dataType:'json',
		waitMsg:"正在对该号码进行23转4固网校验。。。",
		success:function(message){
			var errorList = message.args.returnInfo.responseInfo;
			if(errorList != null && errorList.length != 0){
				 
				var content = '<div class="msgbox" style="max-height:260px; overflow-y:auto;"><ul>';
		  			
				for(var i = 0 ; i < errorList.length; i++){
					content += '<li style="height:auto;">' + (i+1) + '.'  +errorList[i].respDesc+'</li>';
				}
				content+='</ul></div>';
				var dialog = $.dialog({
					width : 400,
					top : 40,
					title : '提示消息',// 提示title
					content : content,// 显示的内容，支持html格式。
					buttons : [ {
						id : 'btn_ok',
						text : '确定',
						onClick : function() {
							dialog.close();
						}
					} ]
				});
				$('#old_phone_confirm_div').removeClass('ok').addClass('ok_disabled');
			}
		},
		error:function(message){
			var jsonObj=eval(message);
			var desc = jsonObj.content;
			$.alert(desc);
			$('#old_phone_confirm_div').removeClass('ok').addClass('ok_disabled');
		}
   });
}
