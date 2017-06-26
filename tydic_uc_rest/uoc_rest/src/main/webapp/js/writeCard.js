//申明全局变量readerName,用来储存读卡器的名称
var readerName = "";

// 列出写卡器
function doListCard() {
	var obj = document.getElementById("CardReader");
	var str = obj.ListCard();
	if (str == null || str == "") {
		alert("没有取到读卡器，请检查控件是否注册或者驱动是否安装或者连接是否正常！");
		return;
	}

	if (str != null && str != "") {
		var strArray = str.split(";");
	}

	// 经局方王河同意，只返回列表中的第一个写卡器信息
	readerName = strArray[0];
	return true;
}

// 连接写卡器
function doConnectCard() {
	/*
	 * 功能：连接写卡器或者智能卡硬件。 返回类型：整型类型。 参数类型：字符串类型。传入通过ListCard函数列出的写卡器的名称。
	 * 函数返回值说明：0表示成功。不等于0表示失败 失败原因可以调用函数GetErrMsg获取错误信息。
	 */
	var obj = document.getElementById("CardReader");
	if ("" == readerName) {
		var result = doListCard();
		if (result != true) {
			return false;
		}
	}
	var ret = obj.ConnectCard(readerName);
	if (ret != "0") {
		alert("连接写卡设备失败，请重新插入白卡！");
		return false;
	}

	return true;
}
function getCardId() {
	/* 连接读卡器 */
	ret = doConnectCard();

	if (ret == false) {
		ret = doDisConnectCard();
		return false;
	}
	var ximNumber = getXimNo();
	if (ximNumber == null) {
		ret = doDisConnectCard();
		return false;
	}
	// 新增读IMSI函数
	/*ret = getImsi();
	if (ret == false) {
		ret = doDisConnectCard();
		alert("该卡已写入IMSI，请换卡后重试！");
		return false;
	}*/
	ret = doDisConnectCard();
	if (ret == false) {
		return false;
	}

	// 调试用
	return ximNumber;
}
/* 得到读卡机中实体卡的卡号 */
function getXimNo() {
	var str = "";
	// alert(str);
	str = doSendCard("A0A40000023F00");
	if (str == "-1") {
		alert("获取卡数据失败！");
		// return false;
		return null;
	}
	str = doSendCard("A0A40000022FE2");
	if (str == "-1") {
		alert("获取卡数据失败！");
		// return false;
		return null;
	}
	str = doSendCard("A0B000000A");
	if (str == "-1") {
		alert("获取卡数据失败！");
		// return false;
		return null;
	}	
	var strArray = str.substring(0, 20);
	var xim_no = '';
	for (var i = 0; i < 20; i++) {
		var j = -1 * ((i % 2));
		if (j == 0)
			j = 1;
		j += i;
		xim_no += strArray.charAt(j);
	}	
	return xim_no.substring(0, 20);

}
function getImsi() {
	var str = "";
	str = doSendCard("A0A40000023F00");
	if (str == "-1") {
		alert("获取卡数据失败！");
		return false;
	}
	str = doSendCard("A0A40000027F20");
	if (str == "-1") {
		alert("获取卡数据失败！");
		return false;
	}
	str = doSendCard("A0A40000026F07");
	if (str == "-1") {
		alert("获取卡数据失败！");
		return false;
	}
	str = doSendCard("A0B0000009");
	if (str == "-1") {
		alert("获取卡IMSI数据失败！");
		return false;
	}
	var imsi_no = "";
	var strArray = str.split(" ");
	// alert(strArray.length);
	for (var i = 0; i < strArray.length; i++) {
		for (var j = strArray[i].length - 1; j >= 0; j--) {
			imsi_no = imsi_no + strArray[i].charAt(j);
		}
	}
	chkImsiNo = imsi_no.substr(0, 18);
	if (chkImsiNo.toUpperCase() != "FFFFFFFFFFFFFFFFFF") {
		return false;
	}
	// alert(strArray.length);
	return true;
}
// 发送指令
function doSendCard(str) {
	var obj = document.getElementById("CardReader");
	if ("" == readerName) {
		var result = doListCard();
		if (result != true) {
			return '-1';
		}
	}
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
// 断开连接
function doDisConnectCard() {
	/*
	 * 功能：断开写卡器的连接。 返回类型：整型类型。 参数类型：字符串类型。传入通过ListCard函数列出的写卡器的名称。
	 * 函数返回值说明：0表示成功。不等于0表示失败，失败原因可以调用函数GetErrMsg获取错误信息。如果传入的写卡器的名称没有连接该函数直接返回成功0值。
	 */
	var obj = document.getElementById("CardReader");
	// alert("doDisConnectCard str="+str);
	if ("" == readerName) {
		var result = doListCard();
		if (result != true) {
			return false;
		}
	}
	var ret = obj.DisconnectCard(readerName);
	if (ret != "0") {
		return false;
	}
	return true;
}
//更新白卡信息，发送指令
function UpdateImsi(imsi,teleType){
	var str="";
	//alert("in UpdateImsi...");
	//alert("imsi="+imsi);
	//alert("cardType="+cardType);

	/*连接读卡器*/
	ret=doConnectCard();
	if(ret==false){
		//断开读卡器连接
		ret=doDisConnectCard();
		return false;
	}
	
	var IMSI= "809"+imsi;
	//alert("IMSI="+IMSI);
	var sendCode="";
	for(var i=0;i<IMSI.length;i++){
      	sendCode=sendCode+IMSI.charAt(i+1)+IMSI.charAt(i);
      	i++;
	}
	str=doSendCard("A0A40000023F00");
	if(str=="-1"){
		ret=doDisConnectCard();
		alert("写卡失败1,请换卡重试");
		return false;
	}
	//alert("end ScriptStr[1]="+ScriptStr[1]);
	str=doSendCard("A0A40000027F10");
	if(str=="-1"){
		ret=doDisConnectCard();
		alert("写卡失败2,请换卡重试");
		return false;
	}
	//alert("end ScriptStr[2]="+ScriptStr[2]);
	str=doSendCard("A0A40000026F42");
	if(str=="-1"){
		ret=doDisConnectCard();
		alert("写卡失败3,请换卡重试");
		return false;
	}
	//alert("end ScriptStr[3]="+ScriptStr[3]);
	str=doSendCard("A0DC010428FFFFFFFFFFFFFFFFFFFFFFFFFDFFFFFFFFFFFFFFFFFFFFFFFF0891683110501905F0FFFFFFFFFFFF");
	if(str=="-1"){
		ret=doDisConnectCard();
		alert("写卡失败4,请换卡重试");
		return false;
	}
	//alert("end ScriptStr[4]="+ScriptStr[4]);
	str=doSendCard("A0A40000023F00");
	if(str=="-1"){
		ret=doDisConnectCard();
		alert("写卡失败5,请换卡重试");
		return false;
	}
	str=doSendCard("A0A40000027F20");
	if(str=="-1"){
		ret=doDisConnectCard();
		alert("写卡失败6,请换卡重试");
		return false;
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
			ret=doDisConnectCard();
			alert("写卡失败7,请换卡重试");
			return false;
		}
		str=doSendCard("A0F4000012"+sendCode+sendCode);
		//alert("in cardType=4 str="+str);
	}else{
		str=doSendCard("A0A40000026F42");
		if(str=="-1"){
			ret=doDisConnectCard();
			alert("写卡失败8,请换卡重试");
			return false;
		}
		str=doSendCard("A0F4000012"+sendCode+sendCode);
		//alert("A0F4000012"+sendCode+sendCode);
		//alert("in cardType != 4 str="+str);
	}
	
	if(str.length>=5){
		//alert("in str.length>=5");
	    var imsiReses=str.split(" ");
	    str=imsiReses[0]+imsiReses[1];
	}
	
	//alert("last str ="+str);
	if(str!="9000"){
		ret=doDisConnectCard();
		alert("写卡失败:"+str);
		return false;
	}
	
	//断开读卡器
	ret=doDisConnectCard();
	if(ret==false){
		return false;
	}
    return true;

}