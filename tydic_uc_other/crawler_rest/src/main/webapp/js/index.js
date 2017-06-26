$().ready(function(){
	//动态添加高度
	var allH = parseInt($(window).height());
    /*var titleH = parseInt($('.title').height());*/
    var itemH = parseInt($('.item').height());
    var mtH = parseInt($('.item').css('margin-top'));
    var H =( allH  - itemH - mtH - 80)+'px';
    var MT = ( allH  - itemH - mtH - 130)+'px';
    $('#griddiv').css('height',H);
    $('.page').css('margin-top',MT);
});