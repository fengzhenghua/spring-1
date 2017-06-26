$(function(){
	$("#rlist").jqGrid({
        url: application.fullPath+"authority/realNameCheck/searchAllTake",
        datatype:"json", 
        mtype:"POST",//提交方式
        loadonce: true,
        height:240,//高度，表格高度。可为数值、百分比或'auto'
        //width:1000,//这个宽度不能为百分比
        autowidth:true,//自动宽
        colNames:['预订单号', '手机号', '身份证号','串号','手机信息','供应商','最低消费','销售价格','成本价格','活动号','工号','密码','订单操作'],
        colModel:[
            //{name:'id',index:'id', width:'10%', align:'center' },
            {name:'order_id',index:'order_id', width:'12%',align:'center', sortable:false},
            {name:'device_number',index:'device_number', width:'10%',align:'center'},
            {name:'cust_id',index:'cust_id', width:'10%', align:"center"},
            {name:'terminal_number',index:'terminal_number', width:'10%', align:"center", sortable:false},
            {name:'terminal_model',index:'terminal_model', width:'10%', align:"center"},
            {name:'terminal_brand',index:'terminal_brand', width:'10%', align:"center",hidden:true},
            {name:'low_fee',index:'low_fee', width:'5%',align:"center", sortable:false,hidden:true},
            {name:'sale_price',index:'sale_price', width:'10%',align:"center",hidden:true},
            {name:'contract_price',index:'contract_price', width:'10%',align:"center",hidden:true},
            {name:'activity_id',index:'activity_id', width:'10%',align:"center"},
            {name:'oper_no',index:'oper_no', width:'10%',align:"center"},
            {name:'pass_word',index:'pass_word', width:'10%',align:"center"},
            {name:'operation',index:'operation', width:'10%',align:"center", sortable:false}
        ],
        //rownumbers:true,//添加左侧行号
       
        viewrecords: true,//是否在浏览导航栏显示记录总数
        rowNum:30,//每页显示记录数
        rowList:[40,50,60,70],//用于改变显示行数的下拉列表框的元素数组。
        jsonReader:{
        	page: "page", 
            total: "total", 
            records: "records", 
        	root: "rows",
            repeatitems : false
        },
        pager:$('#gridPager'),
        loadComplete: function (data) {
        	
        	if( data == null || data.records == 0 ){
        		return;
        	}
        	var result = data.result;
        	
        	var ids = jQuery("#rlist").jqGrid('getDataIDs');
        	for (var i = 0; i < ids.length; i++) {
	        	//var id = ids[i];
	        	var operationBtn = "<div><a href='javascript:;' style='color:#f60' class='dealclass' id='"+ids[i]+"-P10-allTake' >处理</a> | <a href='javascript:;' style='color:#f60' class='dealclass' id='"+ids[i]+"-P20-allTake' >取消</a></div>";
	        	jQuery("#rlist").jqGrid('setRowData', ids[i], { operation: operationBtn });
        	}
        	if( result == "fail" ){
				clear();
				$.alert("没有与查询条件匹配的结果！");
			}
        }
    });
	
	//点击查询按钮
	$("#search").click(function(){
		search(true);
	});
	
	$(document).on('click', '.dealclass', dealData);
});


function search(fl){
	var identity = $("#cust_id").val();
	var phone = $("#device_number").val();
	var workid = $("#order_id").val();
	/*var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();*/
	var searchParameter = new Object();
	var flag = fl;
	
	if( identity!="" ){
		 
		//身份证正则表达式 
		var IDCard=/^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;
		
		if( !IDCard.test(identity) ){
			$.alert("请输入完整并正确的身份证号码!");
			flag=false;
		}
		else{
			flag=true;
		}
		
		searchParameter.cust_id = identity;
	}
	if( workid!="" ){
		searchParameter.order_id = workid;
		flag=true;
	}
	if( phone!="" ){
		var phoneRegex = /^[0|1]\d{10}$/;//手机号验证
		 if (!phoneRegex.test(phone) || phone.length !=11 ) {
	           
			 $.alert("请输入完整并正确的手机号码!");
			 flag=false;
	     }
		 else{
			 flag=true;
		 }
		searchParameter.device_number = phone;
	}
	/*if( startDate!="" ){
		searchParameter.start_date = startDate + " 00:00:00";
		flag=true;
	}
	if( endDate!="" ){
		searchParameter.end_date = endDate + " 23:59:59";
		flag=true;
	}*/
	
	if( flag ){
		
		$('#rlist').jqGrid('setGridParam',{
			datatype:'json',
			postData:searchParameter
		}).trigger('reloadGrid');
		
		$("#cust_id").val("");$("#device_number").val("");$("#order_id").val("");
		var postData = $('#rlist').jqGrid("getGridParam", "postData");
		$.each(postData, function (k, v) {
            delete postData[k];
        });
	}
}

function clear(){
	$("#cust_id").val("");
	$("#device_number").val("");
	$("#order_id").val("");
}

function dealData(){
	var ta = $(this);
	var vals = ta.attr("id");
	vals = vals.split("-");
	var id= vals[0];
	var order_status = vals[1];
	$("#row_id").val(id);
	var audit = $('#rlist').jqGrid('getRowData',id);
	$.ajax({
		type : "POST",
		url : application.fullPath+"authority/realNameCheck/dealPreOrder",
		dataType : 'json',
		data : {order_id:audit.order_id,order_status:order_status},
		beforeSend : function(){
			addMask();
			var noHtml = '<div id="showLoadNotice" class="" style="display:block; position: absolute;z-index: 91000;padding: 10px 30px 10px 30px;top:40%;background: url(\''+application.basePath+'images/loading_bg.png\') repeat;border:1px solid #279DE5;border-top:10px solid #279DE5;"><table><tr height="25px"><td align="center" vertical-align="center" style="font-size:14px;font-weight:bold;">执行中...</td></tr><tr height="20px"><td align="center" vertical-align="center"><img src="'+application.basePath+'images/loading.gif"/></td></tr></table></div>';
			$('body').append(noHtml);
			var showLoad = $("#showLoadNotice");
			showLoad.css({"left":((($(window).width() - showLoad.width()) / 2 - 100)+ "px"),"top":((getScrollTop() + 220) +"px")});
		},
		success : function(data){
			
			if( data.flag ){
				$.alert("订单状态更改成功！");
				ta.parent().hide();
				ta.parent().hide();
			}
			else{
				$.alert("订单状态更改失败，请稍后重试！");
			}
			
		}
	});
}
function addMask (){
	var w = $(window).width();
	var h = $(document).height();
	var maskDivHtml = "<div id='maskDivNotice'  style='cursor: pointer;position:fixed; _position:absolute; top:0px;left:0px;width:"+w+"px;height:"+h+"px;opacity:0.45;background:none repeat scroll 0 0 #000000;z-index: 90009;filter: progid:DXImageTransform.Microsoft.Alpha(opacity=45)'></div>";
	var maskDivNotice = $("#maskDivNotice");
	if(maskDivNotice.length > 0){
		maskDivNotice.remove();
	}
	$('body').append(maskDivHtml);
	
}
function getScrollTop(){
	var D = document; 
	return Math.max(D.body.scrollTop, D.documentElement.scrollTop)
}