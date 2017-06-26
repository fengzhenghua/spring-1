$(function(){
	$("#rlist").jqGrid({
        url: "queryPreOpenOrders",
        postData:{"tele_type":"M165"},
        datatype:"json", 
        mtype:"POST",//提交方式
        loadonce: true,
        height:270,//高度，表格高度。可为数值、百分比或'auto'
        //width:1000,//这个宽度不能为百分比
        autowidth:true,//自动宽
        colNames:['订单号码','手机号码','证件号码', '工号','密码','订单操作'],
        colModel:[
            {name:'order_id',index:'order_id', width:'5%',align:'center', sortable:false,hidden:true},
            {name:'device_number',index:'device_number', width:'12%',align:'center', sortable:false},
            {name:'cust_id',index:'cust_id', width:'17%',align:'center', sortable:false},
            
            {name:'oper_no',index:'oper_no', width:'8%', align:"center"},
            {name:'pass_word',index:'pass_word', width:'8%', align:"center"},
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
	        	var operationBtn = "<div><a href='javascript:;' style='color:#f60' class='detailclass' id='"+ids[i]+"' >订单详情</a> |<a href='javascript:;' style='color:#f60' class='dealclass' id='"+ids[i]+"-P10' >处理</a> | <a href='javascript:;' style='color:#f60' class='dealclass' id='"+ids[i]+"-P20' >取消</a></div>";
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
		search();
	});
	
	$(document).on('click', '.dealclass', dealData);
	$(document).on('click', '.detailclass', detailData);
});

//setInterval('search()',20000);

function search(){
	var phone = $("#device_number").val();
	var identity = $("#cust_id").val();
	
	var searchParameter = new Object();
	var flag = false;
	
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
		
		$('#rlist').jqGrid('setGridParam',{
			datatype:'json',
			postData:searchParameter
		}).trigger('reloadGrid');
		
		/*$("#vpn_name").val("");$("#device_number").val("");$("#order_id").val("");*/
		var postData = $('#rlist').jqGrid("getGridParam", "postData");
		$.each(postData, function (k, v) {
            delete postData[k];
        });
	}
}

function clear(){
	$("#device_number").val("");
	$("#vpn_name").val("");
}

function detailData(){
	var ta = $(this);
	var id = ta.attr("id");
	$("#row_id").val(id);
	var audit = $('#rlist').jqGrid('getRowData',id);
	
	$.post("queryPreInfoOrderAttrs",{order_id:audit.order_id},function(data){
		if(data!=null && data.attr_list!=null){
			var list = data.attr_list;
			var content = "";
			for (var i = 0; i < list.length; i++) {
				if(list[i].attr_id=="customer_name"){
					content += "<div>客户名称："+list[i].attr_value+"</div>";
				}
				else if(list[i].attr_id=="id_number"){
					content += "<div>证件号码："+list[i].attr_value+"</div>";
				}
				else if(list[i].attr_id=="acc_nbr_GSM"){
					content += "<div>手机号码(GSM)："+list[i].attr_value+"</div>";
				}
				else if(list[i].attr_id=="acc_nbr_WCDMA"){
					content += "<div>手机号码(WCDMA)："+list[i].attr_value+"</div>";
				}
				else if(list[i].attr_id=="order_type"){
					content += "<div>订单类型："+list[i].attr_value+"</div>";
				}
				else if(list[i].attr_id=="product_type"){
					content += "<div>产品类型："+list[i].attr_value+"</div>";
				}
				else if(list[i].attr_id=="first_month_fee"){
					content += "<div>首月付费模式："+list[i].attr_value+"</div>";
				}
				else if(list[i].attr_id=="pay_no_165"){
					content += "<div>宽带号码："+list[i].attr_value+"</div>";
				}
				else if(list[i].attr_id=="account_code"){
					content += "<div>固网账号："+list[i].attr_value+"</div>";
				}
				else if(list[i].attr_id=="access_mode"){
					content += "<div>接入方式："+list[i].attr_value+"</div>";
				}
				else if(list[i].attr_id=="speed_level"){
					content += "<div>速率："+list[i].attr_value+"</div>";
				}
				else if(list[i].attr_id=="address_name"){
					content += "<div>地址名称："+list[i].attr_value+"</div>";
				}
				else if(list[i].attr_id=="activity_name"){
					content += "<div>活动名称："+list[i].attr_value+"</div>";
				}
				else if(list[i].attr_id=="fee_info"){
					content += "<div>费用信息："+list[i].attr_value+"</div>";
				}
				else if(list[i].attr_id=="share_product"){
					content += "<div>共享产品："+list[i].attr_value+"</div>";
				}
				else if(list[i].attr_id=="2G_add_package"){
					content += "<div>手机2G附加包："+list[i].attr_value+"</div>";
				}
				else if(list[i].attr_id=="3G_add_package"){
					content += "<div>手机3G附加包："+list[i].attr_value+"</div>";
				}
				else if(list[i].attr_id=="lan_add_package"){
					content += "<div>宽带附加包："+list[i].attr_value+"</div>";
				}
				else if(list[i].attr_id=="fixed_line_package"){
					content += "<div>固话附加包："+list[i].attr_value+"</div>";
				}
				else if(list[i].attr_id=="order_belong"){
					content += "<div>订单归属类型："+list[i].attr_value+"</div>";
				}
				else if(list[i].attr_id=="backup_info"){
					content += "<div>备注信息："+list[i].attr_value+"</div>";
				}
			}
			$.dialog({
				top:50,
				width:260,
				draggable:false,
				content:content
			});
		}
	},"json");
	
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
		url : "dealPreOrder",
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