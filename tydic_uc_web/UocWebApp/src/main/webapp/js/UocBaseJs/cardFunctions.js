/*****************************
 * 文件名：cardFunctions.js
 * For UOC自动写卡页面
 * 作者：yangzhifan
 * 日期：2017.1.20
 *
 * 使用说明：
 * 1. device_type由参数传进来。sr（不传时的默认值）,传chika为驰卡设备，传kaer时为卡尔的设备，传halfauto时为PC端使用的写卡器
 * 2. sms_center 根据使用地的短信中心来设置
 * 3. 以device开头的函数，是操作发卡机活动的相关函数。调用成功时返回值都是 '1'，否则为具体的错误原因
 * 4. 设备类型为chika时，需要传com_port进来，不传的话默认是3。也可以修改此js更改其默认值
 */


var device_type = HTML_UTLS.getParam("device_type");
if (INPUT_UTIL.isNull(device_type)) {
	device_type = "sr";//森锐
}

var sms_center = "0891683110501905F0";//重庆
//sms_center = 0891683110501905F0; 广西短信中心：+8613010591500
//sms_center = 0891683110304105F0; 上海短信中心：+8613010314500
//sms_center = 0891683110501005F0; 海南短信中心：+8613010501500

var com_port = window.parent.Config.DEVICE_ADR; //COM端口 chika设备时使用
var iccid = "";

$(document).ready(function(){
	loadOcx();
});

function loadOcx() {

	if (device_type=="chika") { //驰卡
		if (INPUT_UTIL.isNull(document.getElementById("pdo"))) {
			document.body.insertAdjacentHTML("beforeEnd", '<div style="height:0px;"><object classid="CLSID:99A30CEB-DE74-4E99-9315-364750BA13D7" width="0" height="0" id="pdo"></object></div>');
		}
		if (INPUT_UTIL.isNull(document.getElementById("obj"))) {
			document.body.insertAdjacentHTML("beforeEnd", '<div style="height:0px;"><object classid="clsid:43E4D4FC-3CD8-459A-AAA1-698C1288DE93" width="0" height="0" id="obj"></object></div>');
		}
	}
	else if (device_type=="halfauto") {//pc
		if (INPUT_UTIL.isNull(document.getElementById("obj"))) {
			document.body.insertAdjacentHTML("beforeEnd", '<div style="height:0px;"><object classid="clsid:43E4D4FC-3CD8-459A-AAA1-698C1288DE93" width="0" height="0" id="obj"></object></div>');
		}
	}
	else if (device_type=="kaer") { //卡尔
		if (INPUT_UTIL.isNull(document.getElementById("myocx"))) {
			document.body.insertAdjacentHTML("beforeEnd", '<div style="height:0px;"><object classid="clsid:B8DC93A3-8805-43C7-8A0B-436BCE3A878F"  width="0" height="0" id="myocx"></object></div>');
		}
	}

};

//卡操作的函数。设备分支在这里实现

//以device开头的函数，调用成功时返回值都是 '1'，否则为具体的错误原因

//检查发卡器是否连接
function deviceStatus() {
//	console.info("deviceStatus device_type="+device_type);
	if (device_type=="kaer") {
		return myocx.CIM_State();
	}
	else if (device_type=="chika") {
		var ret  = deviceConnectPusher();
		deviceDisconnectPusher();
		return ret;
	}
	else if (device_type=="halfauto") {
		var ret = deviceConnectWriter();
		deviceDisconnectWriter();
		return ret;
	}
	else if (device_type=="sr"){
		var ret=srMachineInit();
		return ret;
	}
};

//读取白卡数据，成功则返回'1'，同时把白卡的iccid放在全局变量中的iccid中
function deviceRead() {
	iccid = "";
	if (device_type=="kaer") {
		var ret = myocx.CIM_Read_ICCID();
		if (ret == '1'){
			iccid = exchangeByte(myocx.CIM_Card_ICCID);
		}
		return ret;
	}
	else if (device_type=="chika") {
		//return
		var ret = devicePutCard();
		if (ret!='1') {
			return ret;
		}

		var waitmilseconds = 10000; //等待多少毫秒
		var start=new Date().getTime();
		while(true)  {
			if(new Date().getTime()-start>waitmilseconds) { //霸气的延时。没有办法，JS是单线程的，这两个设备又不能同步
				break;
			}
			else {
				ret = getXimNo();
				if (ret=='1') {
					return ret;
				}
			}
		}
		return ret; //这个函数内部已设好iccid
	}
	else if (device_type=="halfauto") {
		return getXimNo();
	}
	else if(device_type=="sr"){
		var ret=srCardIn();
		if(ret==1){
			ret=srReadCard();
		}
		return ret;
	}
};

//将卡片移到回收槽
function deviceReclaim() {
	if (device_type=="kaer") {
		return myocx.CIM_Reclaim_SIM();
	}
	else if (device_type=="chika") {
		var ret = deviceConnectPusher();
		if (ret!='1') {
			deviceDisconnectPusher();
			return ret;
		}
		try {
			pdo.MoveCard(3);
		} catch (err) {
			deviceDisconnectPusher();
			return err.message;
		}
		deviceDisconnectPusher();
		return '1';
	}
	else if (device_type=="halfauto") {
		return '1';
	}
	else if(device_type=="sr"){
		var ret=srCardBin();
		return ret;
	}
};

//将卡送到出卡口
function devicePush() {
	if (device_type=="kaer") {
		return myocx.CIM_Push_SIM();
	}
	else if (device_type=="chika") {
		var ret = deviceConnectPusher();
		if (ret!='1') {
			deviceDisconnectPusher();
			return ret;
		}
		var errs="";
		for (var i=0;i<3;i++) {
			try {
				pdo.MoveCard(1);
				deviceDisconnectPusher();
				return '1';
			} catch (err) {
				deviceDisconnectPusher();
				errs = err.message;
				var nsleep = 1000; //延时1秒
				var start=new Date().getTime();
				while(true) {
					if(new Date().getTime()-start>nsleep) {
						break;
					}
				}
			}
		}
		return errs;

	}
	else if (device_type=="halfauto") {
		return '1';
	}
	else if(device_type=="sr"){
		var ret=cardOut();
		return ret;
	}
};

//写卡
function deviceWrite(imsi,tele_type,acc_nbr) {

	if (device_type=="kaer") {
		/*写卡
		 * 第一个参数 ：ADM密码数据，8字节；
		 * 第二个参数：IMSI数据，2G IMSI数据+3G IMSI数据共18字节；
		 * 第三个参数：短消息服务中心号码，12字节；
		 *
		 * 第一跟第三参数不知道如何填，待调试时再说
		 * */
		ret = myocx.CIM_Write_SIM(""
				, imsi+""+imsi  //15位的IMSI写两遍
				, sms_center);
		return ret;
	}
	else if (device_type=="chika") {
		return UpdateImsi(imsi,tele_type);
	}
	else if (device_type=="halfauto") {
		return UpdateImsi(imsi,tele_type);
	}
	else if (device_type=="sr") {
		//森锐设备需要再次进卡
		ret=srCardIn();
		return ret=="1"?srWriteCard(iccid,imsi,acc_nbr):ret;
	}
};

//－－－－－－－－－－检查函数－－－－－－－－－－－

//检查出卡口是否有卡
//0-无卡 1-有卡
function checkDeviceCardSlot() {
	if (device_type=="kaer") {
		myocx.CIM_State();
		return myocx.CIM_CardSlot_State;
	}
	else if (device_type=="chika") {
		var ret = deviceConnectPusher();
		if (ret!='1') {
			deviceDisconnectPusher();
			return ret;
		}
		try {
			var oStatus = pdo.GetStatus();
			deviceDisconnectPusher();
			if (oStatus.CardPosition=='2') {
				return '1';
			}
			else {
				return '0';
			}
		} catch (err) {
			deviceDisconnectPusher();
			return err.message;
		}

	}
	else if (device_type=="halfauto") {
		return '0';
	}
};

//检查机内空白卡箱是否有卡
//CIM_BlankCardBox_State	BStr	空白卡卡箱状态，’0’：无卡，’1’：少卡，’2’：卡充足
function checkDeviceCardBox() {
	if (device_type=="kaer") {
		myocx.CIM_State();
		return myocx.CIM_BlankCardBox_State;
	}
	else if (device_type=="chika") {
		var ret = deviceConnectPusher();
		if (ret!='1') {
			deviceDisconnectPusher();
			return ret;
		}
		try {
			var oStatus = pdo.GetStatus();
			deviceDisconnectPusher();
			if (oStatus.SupplyBinState=='1') {
				return '0';
			}
			else if (oStatus.SupplyBinState=='2') {
				return '1';
			}
			else if (oStatus.SupplyBinState=='3') {
				return '2';
			}
			else {
				return "other";
			}
		} catch (err) {
			deviceDisconnectPusher();
			return err.message;
		}

	}
	else if (device_type=="halfauto") {
		return '2';
	}
	else if(device_type=="sr"){
		var status=srStatusCheck();
		return status.St1-48;
	}
};

//检查废卡槽
//废卡回收箱状态，’0’：未满，’1’：已满
function checkDeviceRecycleBox() {
	if (device_type=="kaer") {
		myocx.CIM_State();
		return myocx.CIM_RecycleBox_State;
	}
	else if (device_type=="chika") {
		var ret = deviceConnectPusher();
		if (ret!='1') {
			deviceDisconnectPusher();
			return ret;
		}
		try {
			var oStatus = pdo.GetStatus();
			deviceDisconnectPusher();
			if (oStatus.RetainBinState=='1') {
				return '0';
			}
			else if (oStatus.RetainBinState=='2') {
				return '1';
			}
			else {
				return "other";
			}
		} catch (err) {
			deviceDisconnectPusher();
			return err.message;
		}

	}
	else if (device_type=="halfauto") {
		return '0';
	}
	else if(device_type=="sr"){
		var status=srStatusCheck();
		return status.St2-48;
	}
};

//以下都是--------------非kaer---------------分支调用到的函数。

/*---------------chika------------begin-------------------*/
var readerName = "";

//断开发卡机
function deviceDisconnectPusher() {
	try {
		pdo.Disconnect();
	} catch (err) {
	}
};
//连上发卡机
function deviceConnectPusher() {
	//控件比较操蛋，最好先断开再连接
	deviceDisconnectPusher();
	var err_msg="";
	for (var i=0;i<3;i++) {
		try {
			var com_port1 = HTML_UTLS.getParam("com_port");
			if (!INPUT_UTIL.isNull(com_port1)) {
				com_port = com_port1;
			}
			pdo.Connect(com_port, 0);
			return '1';
		} catch (err) {
			//有异常就是失败
			err_msg = "port["+com_port+"]"+err.message;
//			console.info("deviceConnectPusher -> "+err_msg);
			deviceDisconnectPusher();
			var nsleep = 1000; //延时1秒
			var start=new Date().getTime();
			while(true) {
				if(new Date().getTime()-start>nsleep) {
					break;
				}
			}
		}
	}

	return err_msg;

};
//发卡机送到读卡位
function devicePutCard() {
	var ret = deviceConnectPusher();
	if (ret!='1') {
		deviceDisconnectPusher();
		return ret;
	}
	try {
		pdo.MoveCard(2);
	} catch (err) {
		deviceDisconnectPusher();
		return err.message;
	}
	deviceDisconnectPusher();
	return '1';
};
/*---------------chika------------end-------------------*/


//连上写卡机
function deviceConnectWriter() {
	var str = obj.ListCard();
	if (str == null || str == "") {
		return "没有取到写卡机，请检查控件是否注册或者驱动是否安装或者连接是否正常！";
	}
	var strArray = str.split(";");
	readerName = strArray[0];

	var ret=obj.ConnectCard(readerName);
	if (ret != "0") {
		return "连接写卡设备失败，请重新插入白卡！";
	}
	return '1';
};

//断开写卡机
function deviceDisconnectWriter() {
	try {
		obj.DisconnectCard(readerName);
	} catch (err) {
	}
};

//发送指令
function doSendCard(str) {
	var ret = obj.TransmitCard(str, readerName);
	var ErrMsg = obj.GetErrMsg();
	if (ErrMsg != "" && ErrMsg != "0") {
		return "-1";
	}
	var imsiReses = ret.split(" ");
	var tmp = "";
	for (var i = 0; i < imsiReses.length; i++) {
		tmp = tmp + imsiReses[i];
	}
	ret = tmp;
	return ret;
}

/* 得到读卡机中实体卡的卡号 */
function getXimNo() {
	deviceDisconnectPusher();
//	var nsleep = 15000; //延时15秒
//	var start=new Date().getTime();
//	while(true) {
//		if(new Date().getTime()-start>nsleep) {
//			break;
//		}
//	}

	var ret = deviceConnectWriter();
	if (ret!='1') {
		return ret;
	}
	var str = "";
	// alert(str);
	str = doSendCard("A0A40000023F00");
	if (str == "-1") {
		return "1获取卡数据失败！";
	}
	str = doSendCard("A0A40000022FE2");
	if (str == "-1") {
		return "2获取卡数据失败！";
	}
	str = doSendCard("A0B000000A");
	if (str == "-1") {
		return "3获取卡数据失败！";
	}
	var strArray = str.substring(0, 20);
	var xim_no = '';
	for (var i = 0; i < 20; i++) {
		var j = -1 * ((i % 2));
		if (j == 0){j = 1;}
		j += i;
		xim_no += strArray.charAt(j);
	}
	iccid = xim_no.substring(0, 20);
	//读取错误的情况
	if(iccid.charAt(0)=='e') {
		return iccid;
	}
	return '1';
}


function UpdateImsi(imsi,teleType){
	var ret = deviceConnectWriter();
	if (ret!='1') {
		return ret;
	}

	var str="";

	var IMSI= "809"+imsi;
	//alert("IMSI="+IMSI);
	var sendCode="";
	for(var i=0;i<IMSI.length;i++){
      	sendCode=sendCode+IMSI.charAt(i+1)+IMSI.charAt(i);
      	i++;
	}
	str=doSendCard("A0A40000023F00");
	if(str=="-1"){
		deviceDisconnectWriter();
		return "写卡失败1,请换卡重试";
	}
	//alert("end ScriptStr[1]="+ScriptStr[1]);
	str=doSendCard("A0A40000027F10");
	if(str=="-1"){
		deviceDisconnectWriter();
		return "写卡失败2,请换卡重试";
	}
	//alert("end ScriptStr[2]="+ScriptStr[2]);
	str=doSendCard("A0A40000026F42");
	if(str=="-1"){
		deviceDisconnectWriter();
		return "写卡失败3,请换卡重试";
	}
	//alert("end ScriptStr[3]="+ScriptStr[3]);
	str=doSendCard(window.parent.Config.SMS_CODE);
	if(str=="-1"){
		deviceDisconnectWriter();
		return "写卡失败4,请换卡重试";
	}
	//alert("end ScriptStr[4]="+ScriptStr[4]);
	str=doSendCard("A0A40000023F00");
	if(str=="-1"){
		deviceDisconnectWriter();
		return "写卡失败5,请换卡重试";
	}
	str=doSendCard("A0A40000027F20");
	if(str=="-1"){
		deviceDisconnectWriter();
		return "写卡失败6,请换卡重试";
	}

	/*str=doSendCard("A0A40000026F07");
	if(str=="-1"){
		alert("写卡失败！");
		return false;
	}

	str=doSendCard("A0F4000012"+sendCode+sendCode);*/
	//alert("end A0A40000023F00 A0A40000027F20");
	if("3G" == teleType||"4G" == teleType){
		str=doSendCard("A0A40000026F07");
		if(str=="-1"){
			deviceDisconnectWriter();
			return "写卡失败7,请换卡重试";
		}
		str=doSendCard("A0F4000012"+sendCode+sendCode);
		//alert("in cardType=4 str="+str);
	}else{
		str=doSendCard("A0A40000026F42");
		if(str=="-1"){
			deviceDisconnectWriter();
			return "写卡失败8,请换卡重试";
		}
		str=doSendCard("A0F4000012"+sendCode+sendCode);
	}

	if(str.length>=5){
	    var imsiReses=str.split(" ");
	    str=imsiReses[0]+imsiReses[1];
	}

	if(str!="9000"){
		deviceDisconnectWriter();
		return "写卡失败:"+str;
	}

	//断开读卡器
	deviceDisconnectWriter();
	return '1';
};

/*---------------sr------------start-------------------*/
//初始化
function srMachineInit(){
	iccid = "";
	var respData=srDeviceOperate("MachineInit");
	return respData.ReType==80?"1":"0";
}

//状态检查
function srStatusCheck(){
	var respData=srDeviceOperate("StatusCheck");
	return respData;
}

//进卡
function srCardIn(){
	var respData=srDeviceOperate("CardIn");
	return respData.ReType==80?"1":"0";
}

//读卡
function srReadCard(){
	var respData=srDeviceOperate("ReadCard");
	if(respData.ReType==80 && respData.ErrorCode==0){
		iccid = respData.ReData;
		return "1";
	}else{
		return respData.ErrorMessage;
	}
}

function srWriteCard(simCardNo,imsi,custNum){
	var cardData={
		"SimCardNo":simCardNo,
		"Imsi":imsi,
		"CustNum":custNum,
		"Option":"!A0A40000023F00,S,,9FXX!A0A40000027F10,S,,9FXX!A0A40000026F42,S,,9FXX!"+window.parent.Config.SMS_CODE+",S,,9000"
	};

	var respData=srDeviceOperate("WriteCard",cardData);
	return respData.ReType==80?"1":"0";
}

//吐卡
function srCardOut(){
	var respData=srDeviceOperate("CardOut");
	return respData.ReType==80?"1":"0";
}

//回收卡
function srCardBin(){
	var respData=srDeviceOperate("CardBin");
	return respData.ReType==80?"1":"0";
}

//machineNo为初始化时候的唯一编码
function srDeviceOperate(command,params){
	var resp="";

	$.ajax({
		type: "get",
		url: "http://localhost:1208/_crt571/"+command+"?adr="+window.parent.Config.DEVICE_ADR+"&machineNo=uocAutoProcess",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: false,
		data: params,
		dataType: "json",
		crossDomain: true,
		beforeSend: function() {
		},
		success: function(data) {
			resp=data;
		},
		error: function(data) {
			resp=data;
		},
		complete:function(){
		}
	});

	return resp;
}
/*---------------sr------------end-------------------*/
