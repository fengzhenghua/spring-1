/**
 * 实名返档审核
 */
//var gjsonData;
$(function(){
	
	$("#rlist").jqGrid({
        url: "cqSearchResult",
        datatype:"local", 
        mtype:"POST",//提交方式
        loadonce: true,
        height:320,//高度，表格高度。可为数值、百分比或'auto'
        //width:1000,//这个宽度不能为百分比
        autowidth:true,//自动宽
        colNames:['姓名', '身份证号', '地址','手机号码','工单号','添加日期','补登渠道','操作','民族','性别','出生日期','有效期','联系地址','联系人','联系电话'],
        colModel:[
            //{name:'id',index:'id', width:'10%', align:'center' },
            {name:'cert_name',index:'cert_name', width:'6%',align:'center'},
            {name:'cert_id',index:'cert_id', width:'16%',align:'center'},
            {name:'cert_addr',index:'cert_addr', width:'25%', align:"center"},
            {name:'device_number',index:'device_number', width:'11%', align:"center", sortable:false},
            {name:'order_id',index:'order_id', width:'12%', align:"center"},
            {name:'oper_date',index:'oper_date', width:'8%', align:"center"},
            {name:'plat_type_name',index:'plat_type_name', width:'7%', align:"center"},
            {name:'select',index:'select', width:'7%',align:"center", sortable:false},
            {name:'nation',index:'nation',align:"center", hidden:true},
            {name:'sex',index:'sex',align:"center", hidden:true},
            {name:'birthday',index:'birthday',align:"center", hidden:true},
            {name:'exp_date',index:'exp_date',align:"center", hidden:true},
            {name:'cust_addr',index:'cust_addr',align:"center", hidden:true},
            {name:'link_name',index:'link_name',align:"center", hidden:true},
            {name:'link_phone',index:'link_phone',align:"center", hidden:true}
        ],
        //rownumbers:true,//添加左侧行号
       
        viewrecords: true,//是否在浏览导航栏显示记录总数
        rowNum:10,//每页显示记录数
        rowList:[5,10,15,20],//用于改变显示行数的下拉列表框的元素数组。
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
        	gjsonData = data;
        	
        	var ids = jQuery("#rlist").jqGrid('getDataIDs');
        	for (var i = 0; i < ids.length; i++) {
	        	//var id = ids[i];
	        	var selectBtn = "<a href='#card_front' style='color:#f60' onclick='selectRowData("+ids[i]+")' >选择</a>";
	        	jQuery("#rlist").jqGrid('setRowData', ids[i], { select: selectBtn });
        	}
        	if( result == "fail" ){
				clear();
				$.alert("没有与查询条件匹配的结果！");
			}
        }
    });
	
	//点击查询按钮
	$("#search").click(function(){
		var identity = $("#identity").val();
		var phone = $("#phone").val();
		var workid = $("#workid").val();
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		var searchParameter = new Object();
		var flag = false;
		
		if( identity!="" ){
			//身份证正则表达式 
			var IDCard=/^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;
			if( !IDCard.test(identity) ){
				$.alert("请输入完整并正确的身份证号码!");
				return;
			}
			else{
				flag=true;
			}
			
			searchParameter.cert_id = identity;
		}
		if( workid!="" ){
			searchParameter.order_id = workid;
			flag=true;
		}
		if( phone!="" ){
			var phoneRegex = /^[0|1]\d{10}$/;//手机号验证
			 if (!phoneRegex.test(phone) || phone.length !=11 ) {
				 $.alert("请输入完整并正确的手机号码!");
				 return
		     }
			 else{
				 flag=true;
			 }
			searchParameter.device_number = phone;
		}
		if( startDate!="" ){
			searchParameter.start_date = startDate + " 00:00:00";
			flag=true;
		}
		if( endDate!="" ){
			searchParameter.end_date = endDate + " 23:59:59";
			flag=true;
		}
		
		$('#rlist').jqGrid('setGridParam',{
			datatype:'json',
			postData:searchParameter
		}).trigger('reloadGrid');
		
		$("#identity").val("");
		$("#phone").val("");
		$("#workid").val("");
		$("#startDate").val("");
		$("#endDate").val("");
		var postData = $('#rlist').jqGrid("getGridParam", "postData");
		$.each(postData, function (k, v) {
            delete postData[k];
        });
	});
	
	//点击返档按钮
	$("#record").click(function(){
		var orderId = $("#order_id").val();
		var cert_addr = $("#cert_addr").val();
		if( orderId!="" ){
			
			$.ajax({
				type : "POST",
				url : "cqReturnRecord",
				dataType : 'json',
				data : {order_id:orderId,cert_addr:cert_addr},
				beforeSend : function(){
					addMask();
					var noHtml = '<div id="showLoadNotice" class="" style="display:block; position: absolute;z-index: 91000;padding: 10px 30px 10px 30px;top:40%;background: url(\''+application.basePath+'images/loading_bg.png\') repeat;border:1px solid #279DE5;border-top:10px solid #279DE5;"><table><tr height="25px"><td align="center" vertical-align="center" style="font-size:14px;font-weight:bold;">返档执行中...</td></tr><tr height="20px"><td align="center" vertical-align="center"><img src="'+application.basePath+'images/loading.gif"/></td></tr></table></div>';
					$('body').append(noHtml);
					var showLoad = $("#showLoadNotice");
					showLoad.css({"left":((($(window).width() - showLoad.width()) / 2 - 100)+ "px"),"top":((getScrollTop() + 220) +"px")});
				},
				success : function(data){
					if( data.msg=="success" ){
						$.alert("返档成功！");
						clear();
					}
					else{
						$.alert(data.res);
						//$.alert("返档失败，请稍后重试！");
					}
				}
			});
			
		}
	});
	
	//点击审核不通过按钮
	$("#veto").click(function(){
		if(confirm("确认不通过吗?") == true){
			var orderId = $("#order_id").val();
			var reason = $("#reason").val();
			var remark = $("#remark").val();
			if( orderId!="" ){
				$.ajax({
					type : "POST",
					url : "cqCheckVeto",
					dataType : 'json',
					data : {order_id:orderId,reason:reason,remark:remark},
					beforeSend : function(){
						addMask();
						var noHtml = '<div id="showLoadNotice" class="" style="display:block; position: absolute;z-index: 91000;padding: 10px 30px 10px 30px;top:40%;background: url(\''+application.basePath+'images/loading_bg.png\') repeat;border:1px solid #279DE5;border-top:10px solid #279DE5;"><table><tr height="25px"><td align="center" vertical-align="center" style="font-size:14px;font-weight:bold;">审核执行中...</td></tr><tr height="20px"><td align="center" vertical-align="center"><img src="'+application.basePath+'images/loading.gif"/></td></tr></table></div>';
						$('body').append(noHtml);
						var showLoad = $("#showLoadNotice");
						showLoad.css({"left":((($(window).width() - showLoad.width()) / 2 - 100)+ "px"),"top":((getScrollTop() + 220) +"px")});
					},
					success : function(data){
						if( data.msg=="success" ){
							$.alert("审核状态更改成功！");
							clear();
						}
						else{
							$.alert("状态更改失败，请稍后重试！");
						}
					}
				});
				
			}
		}
	});
});

function getScrollTop(){
	var D = document; 
	return Math.max(D.body.scrollTop, D.documentElement.scrollTop)
}

function clear(){
	$("#order_id").val("");
	$("#identity").val("");
	$("#phone").val("");
	$("#workid").val("");
	$("#cert_name").html("");
	$("#nation").html("");
	$("#sex").html("");
	$("#birthday").html("");
	$("#cert_addr").val("");
	$("#cert_id").html("");
	$("#exp_date").html("");
	$("#device_number").html("");
	$("#cust_addr").html("");
	$("#link_name").html("");
	$("#link_phone").html("");
	$("#startDate").val("");
	$("#endDate").val("");
	$("#remark").text("");
	$("#card_front").attr( "src", fullPath+"images/card_front.png" );
	$("#card_back").attr( "src", fullPath+"images/card_reverse.png" );
	$("#card_in_hand_td").html("<h2>手持身份证</h2>");
	var selectedId = $("#row_id").val();
	if(selectedId != null && selectedId != ""){
		$("#rlist").jqGrid("delRowData", selectedId);
	}
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

function selectRowData(id){
	//var a = gjsonData;
	//var audit = gjsonData.rows[id-1];
	$("#row_id").val(id);
	var audit = $('#rlist').jqGrid('getRowData',id);
	$("#cert_name").html(audit.cert_name);
	$("#nation").html(audit.nation);
	$("#sex").html(audit.sex);
	$("#birthday").html(audit.birthday);
	$("#cert_addr").val(audit.cert_addr);
	$("#cert_id").html(audit.cert_id);
	$("#exp_date").html(audit.exp_date);
	$("#device_number").html(audit.device_number);
	$("#order_id").val(audit.order_id);
	$("#link_name").html(audit.link_name);
	$("#link_phone").html(audit.link_phone);
	$("#cust_addr").html(audit.cust_addr);
	
	$("#card_front").attr( "src", fullPath+"authority/CqRealNameCheck/cqGetCardPhoto/card_front/"+audit.order_id );

	$("#card_back").attr( "src", fullPath+"authority/CqRealNameCheck/cqGetCardPhoto/card_back/"+audit.order_id );
	
	$("#card_in_hand_td").html( '<img src="'+fullPath+'authority/CqRealNameCheck/cqGetCardPhoto/card_in_hand/'+audit.order_id+'" width="340" height="216" ondblclick="enlargeImage1(this)" onclick="smallImage(this)"/>' );
}
function enlargeImage1(obj){
	obj.height="400";
	obj.width="600";
}
function smallImage(obj){
	obj.height="216";
	obj.width="340";
}
