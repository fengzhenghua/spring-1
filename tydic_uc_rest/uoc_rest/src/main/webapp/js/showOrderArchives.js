var baseInfo = {
	prefix:""
};
$(document).ready(function() {
	$("#shenSuoFanDang").bind("click",function(){
		var p_class = $("#shenSuoFanDang").attr("class");
		if(p_class=="down"){
			$("#shenSuoFanDang").attr("class", ""); 
		}else{
			$("#shenSuoFanDang").attr("class", "down"); 
		}
		$("#box1").toggle();
	});
	$("#shenSuoYeWu").bind("click",function(){
		var p_class = $("#shenSuoYeWu").attr("class");
		if(p_class=="down"){
			$("#shenSuoYeWu").attr("class", ""); 
		}else{
			$("#shenSuoYeWu").attr("class", "down"); 
		}
		$("#box2").toggle();
	});
	$("#shenSuoTiaoKuan").bind("click",function(){
		var p_class = $("#shenSuoTiaoKuan").attr("class");
		if(p_class=="down"){
			$("#shenSuoTiaoKuan").attr("class", ""); 
		}else{
			$("#shenSuoTiaoKuan").attr("class", "down"); 
		}
		$("#box3").toggle();
	});
});
	