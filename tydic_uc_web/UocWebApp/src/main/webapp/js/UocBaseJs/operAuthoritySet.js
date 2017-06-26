$(document).ready(function() {
	// 新增权限模态框属性设置
	$.modal('#authorityAddModal', {
        width: 540,
        height: 170
    });

	// 查询 按钮事件
	$('#searchBtn').on('click', function(e) {
		alert($(this).text());
	});

	// 重置 按钮事件
	$('#resetBtn').on('click', function(e) {
		reset();
	});

	// 新增权限 按钮事件
	$('#authorityAddBtn').on('click', function(e) {
		$('#authorityAddModal').show();
	});
	
	// 新增权限模态框 保存按钮事件
	$('#authorityAddConfirmBtn').on('click', function(e) {
		alert($(this).text());
	});
	
	// 新增权限模态框 取消按钮事件
	$('#authorityAddCancelBtn').on('click', function(e) {
		$('#authorityAddModal').hide();
	});

	// 删除 按钮事件
	$('#authorityList').on('click', 'td .btn_link[name="delBtn"]', function(e) {
		var $this = $(this);
		$.dialog.confirm('确定删除此行数据吗？', '删除', '确定', '取消',
		    function() {
				$this.parent().parent().remove();
			}
		);
	});

	// 跳到首页
	$('#pageFirst').on('click', function(e) {
		var page_no = "1";
		$("#pageNo").val(page_no);
	});
	// 跳到上一页
	$('#pageUp').on('click', function(e) {
		if($("#pageNo").val() == "1"){
			return;
		}else{
			var page_no = parseInt($("#pageNo").val()) - 1;
			$("#pageNo").val(page_no);
		}
	});
	// 跳到下一页
	$('#pageDown').on('click', function(e) {
		if($("#pageNo").val() == $("#totalPage").text()){
			return;
		}else{
			var page_no = parseInt($("#pageNo").val()) + 1;
			$("#pageNo").val(page_no);
		}
	});
	// 跳到尾页
	$('#pageLast').on('click', function(e) {
		var page_no = total_pages;
		$("#pageNo").val(page_no);
	});

	$('#pageSize').change(function(e){
		$("#pageNo").val("1");
	});
});

//重置
function reset(){
	alert('重置');
}
