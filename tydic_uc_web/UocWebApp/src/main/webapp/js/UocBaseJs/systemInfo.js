$().ready(function(){
	//动态添加
	var html='';
	html +='<div class="title">标题</div>'
		 +'<div class="dear">亲爱的xxx</div>'
		 +'<div class="content">你有一条系统消息，请及时查看</div>'
		 +'<div class="content">你有一条系统消息，请及时查看</div>'
		 +'<div class="content">你有一条系统消息，请及时查看</div>'
		 +'<div class="content">你有一条系统消息，请及时查看</div>'
		 +'<div class="content">你有一条系统消息，请及时查看</div>'
		 +'<div class="content">你有一条系统消息，请及时查看</div>'
		 ;
	$('#middle').append(html);
	//关闭
	$('#guanbi').on('click',function(){
		$('.box').hide();
	});
});