var order_id ="";
$(document).ready(function() {
//	$("#btn_commit").bind("click",commitRealNameAudi);
	load_card_mech();
    $("#btn_clean").bind("click",cleanAll);
    //cleanAll();
    
    $("#numCheck").click(function(){
		var phone = $("#device_number").val();
		var flag = false;
		if( phone!="" ){
			var phoneRegex = /^[0|1]\d{10}$/;//手机号验证
			 if (!phoneRegex.test(phone) || phone.length !=11 ) {
				 $.alert("请输入完整并正确的手机号码!");
				 flag=false;
		     }
			 else{
				 flag=true;
			 }
		}
		if( flag ){
			$.post( application.fullPath + "rest/allTake/realNamePhoneNumCheck", {
			     jsessionid : $("#jsessionid").val(),
			     device_number : phone
				}, function(data) {
					var args = data.args;
					if( args.rsp_code!=null && args.rsp_code!="" ){
						if( args.rsp_code=="00002" ){
							$.alert("该号码未实名返档!");
							$("#ID_PhoneNumber").html(phone);
							num_flag = true;
							//验证成功后生成订单
							createOrderId();
						}
						else if( args.rsp_code=="00000" ){
							$.alert("该号码已经实名返档!");
							$("#device_number").val("");
						}
						else if( args.rsp_code=="00001" ){
							$.alert("该号码在黑名单中!");
							$("#device_number").val("");
						}
						else if( args.rsp_code=="00003" ){
							$.alert("该号码有多个客户!");
							$("#device_number").val("");
						}
						else if( args.rsp_code=="00004" ){
							$.alert("该号码不存在!");
							$("#device_number").val("");
						}
					}
					else{
						var src="";
						if(!args.rsp_code === undefined){
							src = "rsp_code="+args.rsp_code+",rsp_desc="+args.rsp_desc;
						}
						$.alert("号码验证不通过!"+src);
						
					}
				},"json" );
			
		}
	});
    $("#btn_load").bind("click",readIDCard);
    
});

function cleanAll() {
	
	$("#device_number").val("");
	$('#ID_PhoneNumber').html("");
	
	//$("#btn_check_phone").unbind().bind("click",checkRealName);
	//document.getElementById("btn_check_phone").className ="input_button";
	
	/*$("#IN_RandomCode").val("");
	$("#btn_check_code").unbind();
	document.getElementById("btn_check_code").className ="input_button_disabled";
	
	$("#btn_load").unbind();
	document.getElementById("btn_load").className ="input_button_disabled";
	
	$("#btn_commit").unbind();
	document.getElementById("btn_commit").className ="ok_disabled";*/
	
	setValueFromCard(true);
    fillForm();
}


//校验号码是否非实名
function checkRealName() {
	
	if (!checkPhoneNumber($("#IN_PhoneNumber").val())) {
		$.alert("请输入正确的手机号码！");
		return;
	}
	
	var data1={
			device_number:$("#IN_PhoneNumber").val()
	};
	var URL = application.fullPath + "authority/realNameCheck/WXAuditQueryNumber";
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		data:data1,
		waitMsg:"实名信息检查中..",
		success:function(message){
			$.alert(message.content);
			
			if(message.type=="success") {
				
				vOrderId = message.args.orderId;
				vPhoneNumber = $("#IN_PhoneNumber").val();
				$('#ID_PhoneNumber').html(vPhoneNumber);
				
				$("#btn_check_phone").unbind();
				document.getElementById("btn_check_phone").className ="input_button_disabled";
				
				$("#btn_check_code").unbind().bind("click",checkRandomCode);
				document.getElementById("btn_check_code").className ="input_button";
			}
		},
		error:function(){
			$.alert("系统失败，请稍后再试。");
		}
	});
}

//校验随机码
function checkRandomCode() {
	
	var data1={
			device_number: vPhoneNumber,
	    	order_id: vOrderId,
	    	random_code: $("#IN_RandomCode").val()
	};
	var URL = application.fullPath + "authority/realNameCheck/WXAuditVerifyCode";
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		data:data1,
		waitMsg:"随机码校验中..",
		success:function(message){
			
			if(message.type=="success") {
				
				$.alert("验证码校验成功，请刷身份证。");
				
				$("#btn_check_code").unbind();
				document.getElementById("btn_check_code").className ="input_button_disabled";
				
				document.getElementById("btn_load").className ="input_button";
				$("#btn_load").unbind().bind("click",readIDCard);
			}
			else {
				$.alert(message.content);
			}
		}
	});
	
	
}

//读卡
function readIDCard() {
	
	// 读卡结果
    var strReadResult = "";
    var readError = false;
    
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
    
    if(strReadResult != "0") {
    	$.alert("读卡错误,请移动身份证,进行读卡!"+strReadResult);//错误原因描述strReadResult
    	return;
    }
    
    // 读卡成功时，填充页面显示信息
    setValueFromCard();
    fillForm();
    submitInfo();
    
    /*$("#btn_check_code").unbind();
	document.getElementById("btn_check_code").className ="input_button_disabled";*/
	
    $("#btn_commit").bind("click",commitRealNameAudi);
    document.getElementById("btn_commit").className ="ok";

}

//身份证信息变量
var vName = "";
var vNation = "";
var vSex = "";
var vBorn = "";
var vAddress = "";
var vCardNo = "";
var vEffectedDate = "";
var vExpiredDate = "";
var vIssuedAt = "";
var vPicture = "";
var vValid = "";
var vPhoneNumber = "";

var vOrderId = "";

function setValueFromCard(bClean) {
	if (bClean) { //清空
		vName = "";
		vNation = "";
		vSex = "";
		vBorn = "";
		vAddress = "";
		vCardNo = "";
		vEffectedDate = "";
		vExpiredDate = "";
		vIssuedAt = "";
		vPicture = "";
		vValid = "";
		vPhoneNumber = "";
		
		vOrderId = "";
		
		return;
	}
	
	vName = CVR_IDCard.Name;
	vNation = getNationStr(CVR_IDCard.Nation);
	vSex = CVR_IDCard.Sex;
	vBorn = formatDateStr(CVR_IDCard.Born, "-");
	vAddress = CVR_IDCard.Address;
	vCardNo = CVR_IDCard.CardNo;
	vEffectedDate = formatDateStr(CVR_IDCard.EffectedDate, "-");
	vExpiredDate = formatDateStr(CVR_IDCard.ExpiredDate, "-");
	vIssuedAt = CVR_IDCard.IssuedAt;
	vPicture = CVR_IDCard.Picture;
	vValid = formatDateStr(CVR_IDCard.EffectedDate) + "-" + formatDateStr(CVR_IDCard.ExpiredDate);
	$("#auth_end_date").val(formatDateStr(CVR_IDCard.ExpiredDate,"-"));
	$("#auth_begin_date").val(formatDateStr(CVR_IDCard.EffectedDate,"-"));
}

//将读取到的证件信息填充到页面上
function fillForm() {
	
	//底部的信息
	$('#ID_Name').html(vName);
	$('#ID_Nation').html(vNation);
	$('#ID_Sex').html(vSex==""?"":(vSex=="1"?"男":"女"));
	$('#ID_Born').html(formatDateStr(vBorn));
	$('#ID_Address').html(vAddress);
	$('#ID_CardNo').html(vCardNo);
	$('#ID_valid').html(vValid);
	
	
	//图片背景的信息
	$('#PIC_Name').text(vName);
	$('#PIC_Nation').text(vNation);
	$('#PIC_Sex').text(vSex==""?"":(vSex=="1"?"男":"女"));
	$('#PIC_Address').text(vAddress);
	$('#PIC_CardNo').text(vCardNo);
	$('#PIC_IssuedAt').text(vIssuedAt);
	$('#PIC_valid').text(vValid);
	
	if(vBorn.length == 8){ //yyyymmdd
		$('#PIC_y').text(vBorn.substr(0, 4));
		$('#PIC_m').text(vBorn.substr(4, 2));
		$('#PIC_d').text(vBorn.substr(6, 2));
	}
	else if(vBorn.length == 10){ //yyyy-mm-dd
		$('#PIC_y').text(vBorn.substr(0, 4));
		$('#PIC_m').text(vBorn.substr(5, 2));
		$('#PIC_d').text(vBorn.substr(8, 2));
	}
	else {
		$('#PIC_y').text("");
		$('#PIC_m').text("");
		$('#PIC_d').text("");
	}
	
	//$("#PIC_Photo").attr('src',("data:image/jpeg;base64,"+vPicture)); //照片
	$("#PIC_Photo").attr('src', $("#id_card_image_path_blank").val());
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
	});
}

function load_model(model_name){
	$("#messageFlag").val('1');
}


//提交身份证信息
function submitInfo() {
	var data1={
			"id_number":vCardNo,
			"id_addr":vAddress,
			"id_police":vIssuedAt,
			"custom_name":vName,
			"custom_sex":vSex,
			"custom_birth":vBorn,
			"custom_nation":vNation,
			"start_date":vEffectedDate,
			"end_date":vExpiredDate,
			"photo_code":vPicture
	};
	
	var URL = application.fullPath + "authority/idcard/addIdCard";
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		data:data1,
		waitMsg:"处理中..",
		success:function(message){
			$("#PIC_Photo").attr('src', $("#id_card_image_path").val() + vCardNo + ".png");
		}
	
	});
}

var infoMsg = "";

//提交
function commitRealNameAudi() {
	/*infoMsg = "";
	if (submit1() && submit2()) {
		$.alert("恭喜！号码："+vPhoneNumber+" 返档成功！");
		cleanAll();
	}
	else {
		$.alert("返档失败，请联系管理员！\n"+infoMsg);
	}*/
	
	var device_number = $("#ID_PhoneNumber").html();
	var id_number = $("#ID_CardNo").html();
	if( device_number=="" ){
		$.alert("请验证电话号码!");
		return;
	}
	if( id_number=="" ){
		$.alert("请读取身份证!");
		return;
	}
	
	var customer_name = $("#ID_Name").html();
	var auth_adress = $("#ID_Address").html();
	
	$.ajax({
		type : "POST",
		url : application.fullPath + "rest/allTake/returnRecord",
		async : false,
		dataType : 'json',
		data : {
			jsessionid: $("#jsessionid").val(),
			device_number : device_number,
		    cert_id : id_number,
		    cert_addr : auth_adress,
		    cert_name : customer_name,
		    exp_date : $("#auth_end_date").val(),
		    birthday : $("#ID_Born").html(),
		    sex : $("#ID_Sex").html(),
		    order_id:order_id
		},
		beforeSend : function(){
			addMask();
			var noHtml = '<div id="showLoadNotice" class="" style="display:block; position: absolute;z-index: 91000;padding: 10px 30px 10px 30px;top:40%;background: url(\''+application.basePath+'images/loading_bg.png\') repeat;border:1px solid #279DE5;border-top:10px solid #279DE5;"><table><tr height="25px"><td align="center" vertical-align="center" style="font-size:14px;font-weight:bold;">返档执行中...</td></tr><tr height="20px"><td align="center" vertical-align="center"><img src="'+application.basePath+'images/loading.gif"/></td></tr></table></div>';
			$('body').append(noHtml);
			var showLoad = $("#showLoadNotice");
			showLoad.css({"left":((($(window).width() - showLoad.width()) / 2 - 100)+ "px"),"top":((getScrollTop() + 220) +"px")});
		},
		success : function(data){
			if( data.args.rsp_code!="0000" ){
				$.alert("返档失败!rsp_code="+data.args.rsp_code);
				return;
			}
			else{
						var attrdata = {
								"acc_nbr":device_number,
								"contact_phone":device_number,
								"id_number":id_number,
								"customer_name":customer_name,
								"id_type":"02",
								"auth_adress":auth_adress,
								"contact_adress":auth_adress,
								"cust_sex":$("#ID_Sex").html(),
								"born_date":$("#ID_Born").html(),
								"auth_end_date":$("#auth_end_date").val(),
								"auth_begin_date":$("#auth_begin_date").val(),
								"nation":$("#ID_Nation").html()
						};
						$.post(application.fullPath + "rest/orderInfo/orderInfoAttrUpdate",{
							jsessionid: $("#jsessionid").val(),
							"order_id":order_id,	
							"page_code":"0",	
							"order_json":JSON.stringify(attrdata)
						},function(attrArgs){
							$.alert("返档成功!");
							cleanAll();
						},"json");
				
			}
		}
	});
	
}

//提交的第一步，相当于这个页面的提交：YUN-423 (宁夏版)新增实名返档页面(微信端)_郭振丰
function submit1() {
	var data1={
			device_number: vPhoneNumber,
	    	order_id: vOrderId
	};
	var sub_result = false;
	var URL = application.fullPath + "authority/realNameCheck/WXAuditSubmit";
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		data:data1,
		async:false,
		waitMsg:"处理中..",
		success:function(message){
			sub_result = true;
		}
	
	});
	infoMsg = "1";
	return sub_result;
}

//提交的第二步，相当于这个页面的提交：YUN-424   (宁夏版)实名返档审核页面(PC端)_覃健
function submit2() {
	var data1={
	    	order_id: vOrderId
	};
	var sub_result = false;
	infoMsg = "2";
	
	var URL = application.fullPath + "authority/realNameCheck/returnRecord";
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		data:data1,
		async:false,
		waitMsg:"处理中..",
		success:function(message){
			if (message.msg=="success" ){
				sub_result = true;
			}
			else {
				infoMsg = message.res;
			}
		}
	
	});
	
	return sub_result;
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
	
}

function createOrderId(){	 
	 var data1={
			 tele_type:"ALL",
			 order_sub_type:"10060"
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
					order_id = message.args.order_info.order_id;
				}
			}
			
		});		  
};