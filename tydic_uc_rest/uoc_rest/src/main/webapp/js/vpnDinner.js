$(function(){
	$("#rlist2").jqGrid({
        url: application.fullPath+"authority/realNameCheck/queryInfoVpnOrders",
        datatype:"json", 
        mtype:"POST",//提交方式
        loadonce: true,
        height:270,//高度，表格高度。可为数值、百分比或'auto'
        width:874,//这个宽度不能为百分比
//        autowidth:true,//自动宽
        colNames:['订单编号','群组名称', 'VPN套餐信息', '手机号码','工号','密码','订单操作'],
        colModel:[
            {name:'order_id',index:'order_id', width:'10%',align:'center', sortable:false},
            {name:'vpn_name',index:'vpn_name', width:'15%',align:'center', sortable:false},
            {name:'vpn_dinner',index:'vpn_dinner', width:'25%',align:'center'},
            {name:'device_number',index:'device_number', width:'15%', align:"center"},
            {name:'oper_no',index:'oper_no', width:'15%', align:"center"},
            {name:'pass_word',index:'pass_word', width:'10%', align:"center"},
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
        pager:$('#gridPager2'),
        loadComplete: function (data) {
        	
        	if( data == null || data.records == 0 ){
        		return;
        	}
        	var result = data.result;
        	
        	var ids = jQuery("#rlist2").jqGrid('getDataIDs');
        	for (var i = 0; i < ids.length; i++) {
	        	//var id = ids[i];
	        	var operationBtn = "<div><a href='javascript:;' style='color:#f60' class='dealclass' id='"+ids[i]+"-P10' >处理</a> | <a href='javascript:;' style='color:#f60' class='dealclass' id='"+ids[i]+"-P20' >取消</a></div>";
	        	jQuery("#rlist").jqGrid('setRowData', ids[i], { operation: operationBtn });
        	}
        	if( result == "fail" ){
				clear2();
				$.alert("没有与查询条件匹配的结果！");
			}
        }
    });
	
	//点击查询按钮
	$("#search2").click(function(){
		search2();
	});
	
	$(document).on('click', '.dealclass', dealData2);
});

//setInterval('search2()',20100);

function search2(){
	var phone = $("#device_number2").val();
	var vpn_name = $("#vpn_name").val();
//	var vpn_dinner = $("#vpn_dinner").val();
	
	var searchParameter = new Object();
	var flag = true;
	
	
	if( vpn_name!="" ){
		searchParameter.vpn_name = vpn_name;
		flag=true;
	}
//	if( vpn_dinner!="" ){
//		searchParameter.vpn_dinner = vpn_dinner;
//		flag=true;
//	}
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
	
	
	if( flag ){
		
		$('#rlist2').jqGrid('setGridParam',{
			datatype:'json',
			postData:searchParameter
		}).trigger('reloadGrid');
		
		/*$("#vpn_name").val("");$("#device_number").val("");$("#order_id").val("");*/
		var postData = $('#rlist2').jqGrid("getGridParam", "postData");
		$.each(postData, function (k, v) {
            delete postData[k];
        });
	}
}

function clear2(){
	$("#device_number2").val("");
	$("#vpn_name").val("");
}

function dealData2(){
	var ta = $(this);
	var vals = ta.attr("id");
	vals = vals.split("-");
	var id= vals[0];
	var order_status = vals[1];
	$("#row_id").val(id);
	var audit = $('#rlist2').jqGrid('getRowData',id);
	$.ajax({
		type : "POST",
		url : application.fullPath+"authority/realNameCheck/dealInfoVpnOrder",
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