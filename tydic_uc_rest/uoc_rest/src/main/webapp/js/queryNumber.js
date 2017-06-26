$(document).ready(function() {
	$("#card_generation").change(function(){
		var card_generation = $("#card_generation").val();
		if(card_generation=="3G"){
			$("#ofr_sub_type_3g").show();
		}else{
			$("#ofr_sub_type_3g").hide();
		}
		
    });
	$("#query_num").focus(function() {  //得到焦点
		var num = $("#query_num").val(); //获取数字
		$("#query_num_tishi").html("");
		if (num == '请输入后8位中2~4位数字查询') {
			$("#query_num").val(''); //获取焦点之后清空input框的value
		}
	});
	
	$("#query_num").blur(function() { //失去焦点
		var num = $("#query_num").val(); //获取数字
		if (num == '') { //如果是空 给input框value赋值
			$("#query_num").val('请输入后8位中2~4位数字查询');
		}
	});
	
	$("#reset").click(function() { //点击重置按钮函数
		//$("select:first option:first").attr("selected", true);//获取select框的值
		$('#card_generation')[0].selectedIndex = 0; 
		//$("#card_generation").change()没起效
		var card_generation = $("#card_generation").val();
		if(card_generation=="3G"){
			$("#ofr_sub_type_3g").show();
		}else{
			$("#ofr_sub_type_3g").hide();
		}
		$("#query_num").val('请输入后8位中2~4位数字查询'); //给input的value框赋值
	});
	$("#submit").click(function() { //点击提交按钮
		var parofOfr_sub_type_3g="";
		parofOfr_sub_type_3g=$("input[name='ofr_sub_type_3g']:checked").val();
		if(parofOfr_sub_type_3g==""){}else{
			parofOfr_sub_type_3g="&ofr_sub_type_3g="+parofOfr_sub_type_3g;
		}
		var card_generation = $("#card_generation").val(); //得到select框选中的值
		var query_num = $("#query_num").val(); //得到input框输入的值
		if (query_num == "请输入后8位中2~4位数字查询") {
			query_num = null;
		}
		var flag = queryNumValida();  //得到方法返回值  true /  false
		if (!flag) { //如果flag == false
			return false; //返回false
		}
		
		//flag == true 做页面跳转
		// &ofr_sub_type_3g=50001
		var ofr_sub_type_3g = parofOfr_sub_type_3g.substr(17,21);
		console.log("客户类型"+card_generation)
		parent.location.href=application.fullPath + 'authority/selectNumber/selectNumber?tele_type='+card_generation+"&fuzzy_query="+query_num+"&ofr_sub_type_3g="+ofr_sub_type_3g;
	});

});
function queryNumValida() {
	var num = $("#query_num").val(); //获取数字
	var reg = /^\d{2,4}$/; //数字位数的正则  2-4位
	if (num == "请输入后8位中2~4位数字查询") {
		return true;
	}
	//如果input 框中的值不是空值  做验证
	if (!reg.test(num)) { //匹配正则表达式    如果不匹配
		$("#query_num_tishi").html("请输入后8位中2~4位数字查询");
		$("#query_num").val('请输入后8位中2~4位数字查询');
		return false;
	}
	return true;

}