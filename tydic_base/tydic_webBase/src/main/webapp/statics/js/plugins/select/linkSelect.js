$(document).ready(function(){
	$("select").each(function(index) {
		var el = $(this)
		if(el.attr("init")=="true") {
			var dataUrl = $(this).attr("url");
			if(dataUrl){
				$.ajax({
					url: dataUrl,
					async: false,
					dataType: "json",
					success: function(data) {
						el.empty();  
						el.prepend("<option value='' selected>请选择</option>");
						for(var i in data){
							el.append("<option value='"+data[i].value+"'>"+data[i].text+"</option>");
						}
					}
				});
			}
		}
	});
});
/* 级联下拉 */
function nextSelect(obj, nextBoxId) {
	var parentValue=$(obj).val();
	var nextSelect = $("#" + nextBoxId);
	if(!parentValue || parentValue == ""){
		nextSelect.empty();
		nextSelect.change();
		nextSelect.attr("disabled",true);
		return;
	}
	var dataUrl = nextSelect.attr("url");
	if(dataUrl){
		$.ajax({
			type: "post",
			cache: false,
			dataType: "json",
			url: dataUrl,
			data: {parentValue: parentValue},
			success : function(data) {
				nextSelect.empty();
				nextSelect.prepend("<option value='' selected>请选择</option>");
				for(var i in data){
					nextSelect.append("<option value='"+data[i].value+"'>"+data[i].text+"</option>");
				}
				nextSelect.attr("disabled",false);
			}
		});
	}
}