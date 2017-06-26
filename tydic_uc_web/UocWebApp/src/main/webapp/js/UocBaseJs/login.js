$(document).ready(function(){
	/*清空按钮事件*/
	$(".close").on('mousedown',function(){
		var $this=$(this);
		$this.prev().val("");
	});
	/*清空按钮事件的显示或隐藏*/
	$("#account").focus(function(){
		var $this=$(this);
		$this.next().show();
	});
	$("#account").blur(function(){
		var $this=$(this);
		$this.next().hide();
	});
	$("#pwd").focus(function(){
		var $this=$(this);
		$this.next().show();
	});
	$("#pwd").blur(function(){
		var $this=$(this);
		$this.next().hide();
	});
	
	/*登录按钮事件*/
	$("#loginBtn").on('click',function(){
		if($("#account").val().trim()==""){
			$('#info-bar').fadeIn();
			$('.info').text('您还未输入您的帐号');
		}else if($("#pwd").val().trim()==""){
			$('#info-bar').fadeIn();
			$('.info').text('请输入密码');
		}else{			
			$.message.info('登录成功!');
			$('#info-bar').fadeOut();
		}
	});
	
});
