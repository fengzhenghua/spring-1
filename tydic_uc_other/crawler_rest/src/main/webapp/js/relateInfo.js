$().ready(function(){
	//动态添加HTML
	addList('#info_list tbody');
	//td
	$('#info_list tbody tr').each(function(index,element){
		if(index%2==1)
		 $(this).css('background','rgba(192,192,192,0.2)');
	}); 

	$('#info_list').on('mousemove','tbody tr',function(){
		$this = $(this);
		$this.css('background','#e1e1e1');
	});

	$('#info_list').on('mouseover','tbody tr',function(){
		$('.info_list tbody tr').each(function(index,element){
			if(index%2==1){
			  $(this).css('background','rgba(192,192,192,0.2)');
			}
			if(index % 2 == 0){
			  $(this).css('background','#fff');
			}
		}); 
	});
	/*//删除按钮事件
	$('#info_list').on('click','tbody tr .del_btn',function(){
		var $this = $(this);
		$.dialog.confirm(
				"是否要删除该条数据",
			    "提示",
			    "确认",
			    "取消"
		);
	});*/

	//点击新增
	$('#add_list').on('click',function(){
		addList('#info_list tbody');
	});

	//点击编辑
	$('.edit').on('click',function(){
		$this = $(this);
		$('.detail_info_body').hide();
		$('.edit_item').show();
		/*var td1 = $this.parent().siblings('.td1').html();
		var td2 = $this.parent().siblings('.td2').html();
		var td3 = $this.parent().siblings('.td3').html();
		var td4 = $this.parent().siblings('.td4').html();
		var td5 = $this.parent().siblings('.td5').html();
		var td6 = $this.parent().siblings('.td6').html();
		var td7 = $this.parent().siblings('.td7').html();
		var edit_input1 = $('.edit_input1 input').val(td1);
		var edit_input2 = $('.edit_input2 input').val(td2);
		var edit_input3 = $('.edit_input3 input').val(td3);
		var edit_input4 = $('.edit_input4 input').val(td4);
		var edit_input5 = $('.edit_input5 input').val(td5);
		var edit_input6 = $('.edit_input6 input').val(td6);
		var edit_input7 = $('.edit_input7 input').val(td7);*/

	});
	//点击取消
	$('.cancel').on('click',function(){
		$('.detail_info_body').show();
		$('.edit_item').hide();
	});
	
	//动态添加高度
	var allH = parseInt($(window).height());
    var titleH = parseInt($('.title').height());
    var itemH = parseInt($('.detail_info_body').height());
    var mtH = parseInt($('.detail_info_body').css('margin-top'));
    var H =( allH - titleH - itemH - mtH - 80 )+'px';
    var MT = ( allH - titleH - itemH - mtH - 100)+'px';
    $('.box').css('height',H);
    $('.page').css('margin-top',MT);
});
function addList(className){
	var html = '';
	html += '<tr>'
			+ '<td class="td1">小电视卡通小电视卡通1</td>'
			+ '<td class="td2">小电视卡通小电视卡通2</td>'
			+ '<td class="td3">4G</td>'
			+ '<td class="td4">90157799</td>'
			+ '<td class="td5">小电视卡通小电视卡通</td>'
			+ '<td class="td6">7</td>'
			+ '<td class="td7">8</td>'
			+ '<td><i title="编辑" class="cursor edit"></i></td>'
			+'</tr>';
	$(className).append(html);	
}