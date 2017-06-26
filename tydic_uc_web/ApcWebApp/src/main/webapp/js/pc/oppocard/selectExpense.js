$().ready(function(){
	/*资费移入移出*/
	$('.t-li').on('mouseover',function(){
		$this = $(this);
		$this.find('.tc').css('color','#e6061e');
		$this.find('.t-bottom').css('color','#e6061e');
	});
	$('.t-li').on('mouseout',function(){
		$this.find('.fs16').css('color','#808080');
		$this.find('.fs14').css('color','#9a9a9a');
		$this.find('.t-bottom').css('color','#ee6c00');
	});
});