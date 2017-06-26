/**
 * common.js是公共函数文件。只要引入了common.jsp的文件都可以使用这里的公共函数。
 * 也可以单独引用。如：<script type="text/javascript" src="<%=fullPath%>js/common.js"></script>
 */

//获取民族
function getNationStr(iNation) {
	if(isNaN(iNation)){
		return iNation;
	}
	var temp = parseInt(iNation,10);
	if (temp<0 || temp>56) {
		return iNation;
	}
	var arrNations = new Array("汉", "蒙古", "回", "藏", "维吾尔", "苗", "彝", "壮", "布依", "朝鲜", "满", "侗", "瑶", "白", "土家", "哈尼", "哈萨克", "傣", "黎", "傈僳", "佤", "畲", "高山", "拉祜", "水", "东乡", "纳西", "景颇", "克尔克孜", "土", "达斡尔", "仫佬", "羌", "布朗", "撒拉", "毛南", "仡佬", "锡伯", "阿昌", "普米", "塔吉克", "怒", "乌兹别克", "俄罗斯", "鄂温克", "德昂", "保安", "裕固", "京", "塔塔尔", "独龙", "鄂伦春", "赫哲", "门巴", "珞巴", "基诺");
	return arrNations[temp-1];
}

//日期格式化  convert: 20150923 to: 2015.09.23
function formatDateStr(strDate,strSeparator) {
	if (strSeparator==undefined)
		strSeparator = ".";
	
	if (strDate.length==8)  //yyyymmdd
		return strDate.substr(0, 4) + strSeparator + strDate.substr(4, 2) + strSeparator + strDate.substr(6, 2);
	else if (strDate.length==10) //yyyy-mm-dd
		return strDate.substr(0, 4) + strSeparator + strDate.substr(5, 2) + strSeparator + strDate.substr(8, 2);
	else
		return strDate;
}

//校验手机号码
function checkPhoneNumber(strPhone) {
	var phoneRegex = /^[0|1]\d{10}$/;//手机号正则表达式
	if (!phoneRegex.test(strPhone) || strPhone.length != 11) {
		return false;
	}
	else {
		return true;
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