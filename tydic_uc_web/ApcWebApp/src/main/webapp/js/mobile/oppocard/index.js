$().ready(function(){
	//动态写入ulli
	var html = '';
	html +='<li class="z-li l" title="1元套餐">'
		 +	'<div class="z-top"><img src="../../images/mobile/oppoCard/taocan.png"></div>'
		 +	'<div class="z-bottom">1元套餐</div>'
		 +'</li>'
		 +'<li class="z-li l" title="2元套餐">'
		 +	'<div class="z-top"><img src="../../images/mobile/oppoCard/taocan.png"></div>'
		 +	'<div class="z-bottom">2元套餐</div>'
		 +'</li>'
		 +'<li class="z-li l" title="3元套餐">'
		 +	'<div class="z-top"><img src="../../images/mobile/oppoCard/taocan.png"></div>'
		 +	'<div class="z-bottom">3元套餐</div>'
		 +'</li>'
		 +'<li class="z-li l" title="4元套餐">'
		 +	'<div class="z-top"><img src="../../images/mobile/oppoCard/taocan.png"></div>'
		 +	'<div class="z-bottom">4元套餐</div>'
		 +'</li>'
		 +'<li class="z-li l" title="5元套餐">'
		 +	'<div class="z-top"><img src="../../images/mobile/oppoCard/taocan.png"></div>'
		 +	'<div class="z-bottom">5元套餐</div>'
		 +'</li>'
		 +'<li class="z-li l" title="6元套餐">'
		 +	'<div class="z-top"><img src="../../images/mobile/oppoCard/taocan.png"></div>'
		 +	'<div class="z-bottom">6元套餐</div>'
		 +'</li>'
		 +'<li class="z-li l" title="7元套餐">'
		 +	'<div class="z-top"><img src="../../images/mobile/oppoCard/taocan.png"></div>'
		 +	'<div class="z-bottom">7元套餐</div>'
		 +'</li>'
		 +'<li class="z-li l" title="7元套餐">'
		 +	'<div class="z-top"><img src="../../images/mobile/oppoCard/taocan.png"></div>'
		 +	'<div class="z-bottom">7元套餐</div>'
		 +'</li>';
		$('#z-ul').append(html);
		
		//动态添加ul得宽度
		var li = $('#z-ul li');
		var liWidth = $('.z-li').css('width');
		var liMr = $('.z-li').css('margin-right');
		var ulWidth = (li.length * parseInt(liWidth)+li.length * parseInt(liMr))+'px';
		/*alert(ulWidth);*/
		
		//点击左移
		var count = 0;//计数器
		var liArr = $('#z-ul li');
		var len;
		var num = liArr.length % 3;
		if( num == 0){
			len = (liArr.length / 3) -1;
		}else{
			len = Math.floor(liArr.length / 3);
		}
		var page = 3 * (parseInt(liWidth)+parseInt(liMr)) + 1;
		var ml;
		$('.you').on('click',function(){
			if(parseInt($('#z-ul').css('margin-left')) < parseInt($('#z-ul').css('width'))){
				count++;//计数器
				if(count <= len){//鼠标点击的次数要小于或者等于总的li的页数（此处的页数为1）
					 ml = count * page;//翻一页要左移-545px
				}else{
					count = len;
				};
				$('#z-ul').css('margin-left','-'+ml+'px');
			};
		});
		
		//点击右移
		$('.zuo').on('click',function(){
			if(count !=0 && parseInt($('#z-ul').css('margin-left')) != 0){
				count--;
				if(count == 0){
					ml = 0;
				}else{
					ml = parseInt($('#z-ul').css('margin-left')) + count * page;
				}
				$('#z-ul').css('margin-left',ml+'px');
			};
		}); 
		
		//点击更多产品
		$('#more').on('click',function(){
			window.location.href = 'allProduce.html';
		});
		//点击几元套餐
		$('#taocan').on('click',function(){
			/*alert(1);*/
			window.location.href = 'packageTariff.html';
		});
		//点击立即申请，跳转到申请页面
		$('#sq').on('click',function(){
			window.location.href="../oppocard/base.html";
		});
});