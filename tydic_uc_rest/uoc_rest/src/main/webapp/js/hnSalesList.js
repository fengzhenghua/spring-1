
var operNo = "";
var topPageNo = "";
var previousPageNo = "";
var nextPageNo = "";
var totalPages = "";
var bottomPageNo = "";
var pageNo = 1 ;
var pageSize = 10 ;
$(document).ready(function() {
	operNo = $("#operNo").val();
	selectDataNum();
	$("#query").click(function() {
		selectData();
	});
	
});

/**
 *分页
 */
function selectDataNum(){
	
	var data= {
		"oper_id":operNo,
		"pageNo":pageNo,
		"pageSize":pageSize	
	};
	var GetURl = application.fullPath + "/authority/saleRecord/querySaleRecordPage";
	$.ajax({
		url: GetURl,
		type: 'post',
		//waitMsg: "查询...",
		data : data,
		success: function(message) {
			$('#paylogTable>tbody>tr').remove();
			var pageModelVo= message.args.pageModelVo.list;
			topPageNo = message.args.pageModelVo.topPageNo;
			previousPageNo = message.args.pageModelVo.previousPageNo;
			nextPageNo = message.args.pageModelVo.nextPageNo;
			totalPages = message.args.pageModelVo.totalPages;
			bottomPageNo = message.args.pageModelVo.bottomPageNo;
			pageNo = message.args.pageModelVo.pageNo;
			var htmlNew = '';
			if(pageModelVo != null || pageModelVo.length != 0){
				for (var i = 0; i <pageModelVo.length; i++) {
					sale = pageModelVo[i];
					var busiType = sale.busi_type;
					if (null == busiType ){
						busiType = "";
					}
					var html ='<tr>'
					    +'<td class="text_center"><input type="checkbox" name="checkInfo" onclick="check(this)"></td>'
			            +'<td class="text_center">'+sale.order_number+'</td>'
			            +'<td class="text_center">'+busiType+'</td>'
			            +'<td class="text_center">'+sale.oper_name+'</td>'
						+'<td class="text_center">'+sale.oper_dept+'</td>'
			            +'<td class="text_center">'+sale.order_date+'</td>'
			            +'</tr>';
						htmlNew += html;
				}
			}
			
			$('#paylogTable>tbody').append(htmlNew);
			document.getElementById("totalPages").innerHTML = totalPages;
		},
		error:function(message){
			$.alert("系统失败，请稍后再试。");
		}
	});
	 $("#previousPage").attr("disabled", true); 
	document.getElementById("pageNo").innerHTML = pageNo;
	
}
function jumpTo(){
	var jumpTo = $("#jumpTo").val();
	if ("" ==jumpTo){
		jumpTo = 1;
	} 
	var data= {
		"oper_id":operNo,
		"pageNo":jumpTo,
		"pageSize":pageSize	
	};
	var GetURl = application.fullPath + "/authority/saleRecord/querySaleRecordPage";
	$.ajax({
		url: GetURl,
		type: 'post',
		//waitMsg: "查询...",
		data : data,
		success: function(message) {
			$('#paylogTable>tbody>tr').remove();
			var pageModelVo= message.args.pageModelVo.list;
			topPageNo = message.args.pageModelVo.topPageNo;
			previousPageNo = message.args.pageModelVo.previousPageNo;
			nextPageNo = message.args.pageModelVo.nextPageNo;
			totalPages = message.args.pageModelVo.totalPages;
			bottomPageNo = message.args.pageModelVo.bottomPageNo;
			pageNo = message.args.pageModelVo.pageNo;
			var htmlNew = '';
			if(pageModelVo != null || pageModelVo.length != 0){
				for (var i = 0; i <pageModelVo.length; i++) {
					sale = pageModelVo[i];
					var busiType = sale.busi_type;
					if (null == busiType ){
						busiType = "";
					}
					var html ='<tr>'
					    +'<td class="text_center"><input type="checkbox" name="checkInfo" onclick="check(this)"></td>'
			            +'<td class="text_center">'+sale.order_number+'</td>'
			            +'<td class="text_center">'+busiType+'</td>'
			            +'<td class="text_center">'+sale.oper_name+'</td>'
						+'<td class="text_center">'+sale.oper_dept+'</td>'
			            +'<td class="text_center">'+sale.order_date+'</td>'
			            +'</tr>';
						htmlNew += html;
				}
			}
			
			$('#paylogTable>tbody').append(htmlNew);
		},
		error:function(message){
			$.alert("系统失败，请稍后再试。");
		}
	});
	if (1>=pageNo){
		   $("#previousPage").attr("disabled", false); 
	   } else {
		   $("#previousPage").attr("disabled", true); 
	   }
	if (totalPages<=jumpTo){
		   $("#nextPage").attr("disabled", true); 
	   } else {
		   $("#nextPage").attr("disabled", false); 
	   }
	document.getElementById("pageNo").innerHTML = jumpTo;

}
function topPage() {

	var data= {
		"oper_id":operNo,
		"pageNo":"1",
		"pageSize":pageSize	
	};
	var GetURl = application.fullPath + "/authority/saleRecord/querySaleRecordPage";
	$.ajax({
		url: GetURl,
		type: 'post',
		//waitMsg: "查询...",
		data : data,
		success: function(message) {
			$('#paylogTable>tbody>tr').remove();
			var pageModelVo= message.args.pageModelVo.list;
			topPageNo = message.args.pageModelVo.topPageNo;
			previousPageNo = message.args.pageModelVo.previousPageNo;
			nextPageNo = message.args.pageModelVo.nextPageNo;
			totalPages = message.args.pageModelVo.totalPages;
			bottomPageNo = message.args.pageModelVo.bottomPageNo;
			pageNo = message.args.pageModelVo.pageNo;
			var htmlNew = '';
			if(pageModelVo != null || pageModelVo.length != 0){
				for (var i = 0; i <pageModelVo.length; i++) {
					sale = pageModelVo[i];
					var busiType = sale.busi_type;
					if (null == busiType ){
						busiType = "";
					}
					var html ='<tr>'
					    +'<td class="text_center"><input type="checkbox" name="checkInfo" onclick="check(this)"></td>'
			            +'<td class="text_center">'+sale.order_number+'</td>'
			            +'<td class="text_center">'+busiType+'</td>'
			            +'<td class="text_center">'+sale.oper_name+'</td>'
						+'<td class="text_center">'+sale.oper_dept+'</td>'
			            +'<td class="text_center">'+sale.order_date+'</td>'
			            +'</tr>';
						htmlNew += html;
				}
			}
			
			$('#paylogTable>tbody').append(htmlNew);
		},
		error:function(message){
			$.alert("系统失败，请稍后再试。");
		}
	});
    document.getElementById("pageNo").innerHTML = 1;
	$("#previousPage").attr("disabled", true);
	$("#nextPage").attr("disabled", false);
}

function previousPage() {

	var data= {
		"oper_id":operNo,
		"pageNo":previousPageNo,
		"pageSize":pageSize
	};
	var GetURl = application.fullPath + "/authority/saleRecord/querySaleRecordPage";
	$.ajax({
		url: GetURl,
		type: 'post',
		//waitMsg: "查询...",
		data : data,
		success: function(message) {
			$('#paylogTable>tbody>tr').remove();
			var pageModelVo= message.args.pageModelVo.list;
			topPageNo = message.args.pageModelVo.topPageNo;
			previousPageNo = message.args.pageModelVo.previousPageNo;
			nextPageNo = message.args.pageModelVo.nextPageNo;
			totalPages = message.args.pageModelVo.totalPages;
			bottomPageNo = message.args.pageModelVo.bottomPageNo;
			pageNo = message.args.pageModelVo.pageNo;
			var htmlNew = '';
			if(pageModelVo != null || pageModelVo.length != 0){
				for (var i = 0; i <pageModelVo.length; i++) {
					sale = pageModelVo[i];
					var busiType = sale.busi_type;
					if (null == busiType ){
						busiType = "";
					}
					var html ='<tr>'
					    +'<td class="text_center"><input type="checkbox" name="checkInfo" onclick="check(this)"></td>'
			            +'<td class="text_center">'+sale.order_number+'</td>'
			            +'<td class="text_center">'+busiType+'</td>'
			            +'<td class="text_center">'+sale.oper_name+'</td>'
						+'<td class="text_center">'+sale.oper_dept+'</td>'
			            +'<td class="text_center">'+sale.order_date+'</td>'
			            +'</tr>';
						htmlNew += html;
				}
			}
			
			$('#paylogTable>tbody').append(htmlNew);
		},
		error:function(message){
			$.alert("系统失败，请稍后再试。");
		}
	});
   if ("2"==pageNo){
	   $("#previousPage").attr("disabled", true); 
	   $("#nextPage").attr("disabled", false);
   } else {
	   $("#previousPage").attr("disabled", false); 
	   $("#nextPage").attr("disabled", false);
   }
   document.getElementById("pageNo").innerHTML = pageNo-1;
}    

function nextPage() {
  
	var data= {
		"oper_id":operNo,
		"pageNo":nextPageNo,
		"pageSize":pageSize	
	};
	var GetURl = application.fullPath + "/authority/saleRecord/querySaleRecordPage";
	$.ajax({
		url: GetURl,
		type: 'post',
		//waitMsg: "查询...",
		data : data,
		success: function(message) {
			$('#paylogTable>tbody>tr').remove();
			var pageModelVo= message.args.pageModelVo.list;
			topPageNo = message.args.pageModelVo.topPageNo;
			previousPageNo = message.args.pageModelVo.previousPageNo;
			nextPageNo = message.args.pageModelVo.nextPageNo;
			totalPages = message.args.pageModelVo.totalPages;
			bottomPageNo = message.args.pageModelVo.bottomPageNo;
			pageNo = message.args.pageModelVo.pageNo;
			var htmlNew = '';
			if(pageModelVo != null || pageModelVo.length != 0){
				for (var i = 0; i <pageModelVo.length; i++) {
					sale = pageModelVo[i];
					var busiType = sale.busi_type;
					if (null == busiType ){
						busiType = "";
					}
					var html ='<tr>'
					    +'<td class="text_center"><input type="checkbox" name="checkInfo" onclick="check(this)"></td>'
			            +'<td class="text_center">'+sale.order_number+'</td>'
			            +'<td class="text_center">'+busiType+'</td>'
			            +'<td class="text_center">'+sale.oper_name+'</td>'
						+'<td class="text_center">'+sale.oper_dept+'</td>'
			            +'<td class="text_center">'+sale.order_date+'</td>'
			            +'</tr>';
						htmlNew += html;
				}
			}
			
			$('#paylogTable>tbody').append(htmlNew);
		},
		error:function(message){
			$.alert("系统失败，请稍后再试。");
		}
	});
	  if (bottomPageNo-1==pageNo){
		 $("#previousPage").attr("disabled", false); 
		 $("#nextPage").attr("disabled", true);
	   } else {
		   $("#previousPage").attr("disabled", false);  
		   $("#nextPage").attr("disabled", false);
	   }
	 document.getElementById("pageNo").innerHTML = pageNo+1;
}

function bottomPage() {
    
	var data= {
		"oper_id":operNo,
		"pageNo":bottomPageNo,
		"pageSize":pageSize	
	};
	var GetURl = application.fullPath + "/authority/saleRecord/querySaleRecordPage";
	$.ajax({
		url: GetURl,
		type: 'post',
		//waitMsg: "查询...",
		data : data,
		success: function(message) {
			$('#paylogTable>tbody>tr').remove();
			var pageModelVo= message.args.pageModelVo.list;
			topPageNo = message.args.pageModelVo.topPageNo;
			previousPageNo = message.args.pageModelVo.previousPageNo;
			nextPageNo = message.args.pageModelVo.nextPageNo;
			totalPages = message.args.pageModelVo.totalPages;
			bottomPageNo = message.args.pageModelVo.bottomPageNo;
			pageNo = message.args.pageModelVo.pageNo;
			var htmlNew = '';
			if(pageModelVo != null || pageModelVo.length != 0){
				for (var i = 0; i <pageModelVo.length; i++) {
					sale = pageModelVo[i];
					var busiType = sale.busi_type;
					if (null == busiType ){
						busiType = "";
					}
					var html ='<tr>'
					    +'<td class="text_center"><input type="checkbox" name="checkInfo" onclick="check(this)"></td>'
			            +'<td class="text_center">'+sale.order_number+'</td>'
			            +'<td class="text_center">'+busiType+'</td>'
			            +'<td class="text_center">'+sale.oper_name+'</td>'
						+'<td class="text_center">'+sale.oper_dept+'</td>'
			            +'<td class="text_center">'+sale.order_date+'</td>'
			            +'</tr>';
						htmlNew += html;
				}
			}
			
			$('#paylogTable>tbody').append(htmlNew);
		},
		error:function(message){
			$.alert("系统失败，请稍后再试。");
		}
	});
	$("#nextPage").attr("disabled", true);
	$("#previousPage").attr("disabled", false);
	document.getElementById("pageNo").innerHTML = bottomPageNo;

}
function selectData() {
	var start_time = $.trim($("#smeeting").val());
	var end_time = $.trim($("#emeeting").val());
	var order_number = $.trim($("#order_number").val());
	if (""!=order_number){
		start_time ="";
		end_time = "";
	}
	var chnl_code = $.trim($("#chnl_code").val());
	if (order_number == "请输入手机号码") {
		order_number = "";
	}
	var data= {
		"oper_id":operNo,
		"start_date":start_time,
		"end_date":end_time,
		"order_number":order_number,
		"chnl_code":chnl_code
	};
	var GetURl = application.fullPath + "/authority/saleRecord/querySaleRecordByOperId";
	$.ajax({
		url: GetURl,
		type: 'post',
		waitMsg: "查询...",
		data : data,
		success: function(message) {
			$('#paylogTable>tbody>tr').remove();
			var saleList= message.args.saleRecordVos;
			var htmlNew = '';
			if(saleList != null || saleRecordVos.length != 0){
				for (var i = 0; i <saleList.length; i++) {
					sale = saleList[i];
					var busiType = sale.busi_type;
					if (null == busiType ){
						busiType = "";
					}
					var html ='<tr>'
					    +'<td class="text_center"><input type="checkbox" name="checkInfo" onclick="check(this)"></td>'
			            +'<td class="text_center">'+sale.order_number+'</td>'
			            +'<td class="text_center">'+busiType+'</td>'
			            +'<td class="text_center">'+sale.oper_name+'</td>'
						+'<td class="text_center">'+sale.oper_dept+'</td>'
			            +'<td class="text_center">'+sale.order_date+'</td>'
			            +'</tr>';
						htmlNew += html;
				}
			}
			
			$('#paylogTable>tbody').append(htmlNew);
		},
		error:function(message){
			$.alert("系统失败，请稍后再试。");
		}
	});

}

