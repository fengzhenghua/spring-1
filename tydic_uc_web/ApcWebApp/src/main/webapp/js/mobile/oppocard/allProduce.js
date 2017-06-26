$().ready(function(){
	//鼠标抓住上下移动特效
	/*if (window.addEventListener) {
	    var box = document.querySelector(".main"), grab = document.querySelector(".main ul");
	    
	    var store = { move: false };//用于判断鼠标是否拖动了图片
	    
	    grab.addEventListener("mousedown", function (event) {
	        this.className = "grabbing";//当鼠标按下时，将grab变成grabbing
	        store.move = true;
	        store.startY = event.pageY + box.scrollTop;
	        event.preventDefault();//规定阻止哪个事件的默认动作
	    });//鼠标按下改变cursor的值
	    
	    document.addEventListener("mousemove", function(event) {
	        store.y = event.pageY;
	        if (store.move == true) box.scrollTop =  store.startY - store.y;
	        event.preventDefault();
	    });//鼠标按下并上下拖动
	    
	    document.addEventListener("mouseup", function(event) {
	        grab.className = "grab";//将鼠标抬起时，将grabbing变成grabbing
	        store.move = false;        
	        event.preventDefault();
	    });//鼠标放开停止拖动，同时鼠标恢复原状
	}*/
	//返回
	$('#back').on('click',function(){
		window.location.href = 'index.html';
	});
	//点击更多产品跳转到套餐详情页
	$('.m-li').on('click',function(){
		window.location.href = 'packageTariff.html';
	});
	/*
	 * //图片轮播
	 * */
	var length,
	currentIndex = 0,
	interval,
	hasStarted = false, //是否已经开始轮播
	t = 3000; //轮播时间间隔
	length = $('.slider-panel').length;
	
	//将除了第一张图片隐藏
	$('.slider-panel:not(:first)').hide();
	//将第一个slider-item设为激活状态
	$('.slider-item:first').addClass('slider-item-selected');
	
	$('.slider-item').hover(function(e) {
		stop();
		var preIndex = $(".slider-item").filter(".slider-item-selected").index();
		currentIndex = $(this).index();
		play(preIndex, currentIndex);
	}, function() {
		start();
	});
	
	/**
	 * 向前翻页
	 */
	function pre() {
		var preIndex = currentIndex;
		currentIndex = (--currentIndex + length) % length;
		play(preIndex, currentIndex);
	}
	/**
	 * 向后翻页
	 */
	function next() {
		var preIndex = currentIndex;
		currentIndex = ++currentIndex % length;
		play(preIndex, currentIndex);
	}
	/**
	 * 从preIndex页翻到currentIndex页
	 * preIndex 整数，翻页的起始页
	 * currentIndex 整数，翻到的那页
	 */
	function play(preIndex, currentIndex) {
		$('.slider-panel').eq(preIndex).fadeOut(500)
			.parent().children().eq(currentIndex).fadeIn(1000);
		$('.slider-item').removeClass('slider-item-selected');
		$('.slider-item').eq(currentIndex).addClass('slider-item-selected');
	}
	
	/**
	 * 开始轮播
	 */
	function start() {
		if(!hasStarted) {
			hasStarted = true;
			interval = setInterval(next, t);
		}
	}
	/**
	 * 停止轮播
	 */
	function stop() {
		clearInterval(interval);
		hasStarted = false;
	}
	
	//开始轮播
	start();
	/*//图片轮播end*/
});