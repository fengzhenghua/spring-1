$().ready(function(){
	//返回
	$('#back').on('click',function(){
		window.location.href = 'index.html';
	});
	//点击立即申请，跳转到申请页面
	$('#sq').on('click',function(){
		window.location.href="../oppocard/base.html";
	});
});