var pName = ""; // 姓名
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
$(document).ready(function() {
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
    		$.alert('请选择读卡器!');
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
    //重置功能
//  $("#btn_reset").click("click",function(){
//	  ClearIDCard();
//	  ClearForm();
//  });
   
})

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
   // pSexShow = pSex == "1" ? "男" : "女";
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
	$('#custom_name_pc').text(pName);	
	$('#customer_sex').text(pSexShow);
	$('#nation_id').text(pNation);
	$('#born_date_val').text(pBorn);
	$('#auth_adress').text(pAddress);
	$('#idcard_addr').text(pPolice);
	$('#end_date_val').val(pActivityLTo);
	$('#id_number').text(pCardNo);
	$('#id_nbr').text(pCardNo);
	$('#start_date_val').val(pActivityLFrom);
	$('#valid').text(valid_start+"―"+valid_end);
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
   var sex = $("#customer_sex").text();
   if(sex=="男"){
	   sex = "1";
   }else if(sex=="女"){
	   sex = "0";
   }
   $("#id_number_result").val($("#id_number").text());						//身份证
   $("#id_addr_result").val($("#auth_adress").text());						//证件地址
   $("#id_police_result").val($("#idcard_addr").text());						//签发地址
   $("#custom_name_result").val($("#customer_name").text());						//客户姓名
   $("#custom_sex_result").val(sex);						//客户性别
   $("#custom_birth_result").val($("#born_date_val").text());						//出生日期
   $("#custom_nation_result").val($("#nation_id").text());						//民族
   $("#start_date_result").val($("#start_date_val").val());						//生效时间
   $("#end_date_result").val($("#end_date_val").val());						//实效时间
   $("#photo_code_result").val($("#photo_buffer_val").val());						//照片编码
  
   var data1={
		  "id_number":$("#id_number_result").val(),
		  "id_addr":$("#id_addr_result").val(),
		  "id_police":$("#id_police_result").val(),
		  "custom_name":$("#custom_name_result").val(),
		  "custom_sex":$("#custom_sex_result").val(),
		  "custom_birth":$("#custom_birth_result").val(),
		  "custom_nation":$("#custom_nation_result").val(),
		  "start_date":$("#start_date_result").val(),
		  "end_date":$("#end_date_result").val(),
		  "photo_code":$("#photo_code_result").val(),
		  "order_id":$("#order_id_result").text()==null ? "" : $("#order_id_result").text()
	  }
   var URL = application.fullPath + 'authority/idcard/addIdCard';
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		data:data1,
		waitMsg:"读取中..",
		success:function(message){
			$("#idCradImage").attr('src',id_card_image_path+$("#id_number_result").val()+".png");
			var dialog=$.dialog({
			   title:'提示',//提示title
			   content:'资料上传成功',//显示的内容，支持html格式。
			   buttons:[{id:'btn_ok',text:'确定',
				   onClick:function(){					   
					   dialog.close();
					  // window.setTimeout("ClearForm()",2000);
					   checkCustInfo($("#id_number_result").val());//校验身份证
				   }//点击时候回调函数
			   }]
			});
			//setTimeout("ClearForm()",3000);
			
		},error:function(message){
			var dialog=$.dialog({
				   title:'提示',//提示title
				   content:'资料上传失败',//显示的内容，支持html格式。
				   buttons:[{id:'btn_ok',text:'确定',
					   onClick:function(){					   
						   dialog.close();
					   }//点击时候回调函数
				   }]
			});
		}
		
	});
}

//YUN-386 GX_PC端开户，读取身份证时写customer_info_verify表时民族存入有误_杨枝蕃
function getNationStr(iNation) {
	if(isNaN(iNation)){
		return iNation;
	}
	var temp = parseInt(iNation);
	if (temp<1 || temp>56) {
		return iNation;
	}
	var arrNations = new Array("汉", "蒙古", "回", "藏", "维吾尔", "苗", "彝", "壮", "布依", "朝鲜", "满", "侗", "瑶", "白", "土家", "哈尼", "哈萨克", "傣", "黎", "傈僳", "佤", "畲", "高山", "拉祜", "水", "东乡", "纳西", "景颇", "克尔克孜", "土", "达斡尔", "仫佬", "羌", "布朗", "撒拉", "毛南", "仡佬", "锡伯", "阿昌", "普米", "塔吉克", "怒", "乌兹别克", "俄罗斯", "鄂温克", "德昂", "保安", "裕固", "京", "塔塔尔", "独龙", "鄂伦春", "赫哲", "门巴", "珞巴", "基诺");
	return arrNations[temp-1];
}