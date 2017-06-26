//rem
$().ready(function(){
	//rem
	var $h = document.getElementsByTagName('html')[0];
	/*var pageWidth = $h.getBoundingClientRect().width; */
	var pageWidth = screen.width; 
	/*alert(pageWidth);*/
	$h.style.fontSize = pageWidth / 16 + "px";
	
	/* 
	alert(pageWidth);
	alert($(window).width());
	alert(screen.width); */
	//rem end
});



//判断是否为空 （null、undefined、""、"null"、"undefined"）,或者空的话就返回""
function isNullUtil(flagValue,paraValue){
	if(flagValue=="0"){
		if(paraValue==null||paraValue==""||paraValue==undefined||paraValue=="null"||paraValue=="undefined"){
			return true;
		}else{
			return false;
		}
	}else if(flagValue=="1"){
		if(paraValue==null||paraValue==""||paraValue==undefined||paraValue=="null"||paraValue=="undefined"){
			return "";
		}else{
			return paraValue;
		}
	}
}