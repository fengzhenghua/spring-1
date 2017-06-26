var url1="";
var data_phonetrinal="";//保存选择的终端信息
var data_product="";//保存选择的产品信息
var product_tele_type="";//选择产品确定电信类型
var data_zbactivity="";//保存选择总部合约活动信息
var data_localnetactivity="";//保存选择本地合约活动信息
var data_ywb="";////保存选择服务包信息
var ywb_ofr_id="";//选择产品的ofr_id
var page_list=[];//已选择配置的所有列表
var page_select={};//单项选择配置打包
var phone_image_path="";//图片名称或者路径

$(document).ready(function() {
		
	 //限制图片上传类型
  $.jUploader.setDefaults({
            cancelable: true,
            allowedExtensions: ['jpeg','jpg', 'gif','png'],
            messages: {
                upload: '上传',
                cancel: '取消',
                emptyFile: "{file} 为空，请选择一个文件.",
                invalidExtension: "{file}上传格式错误,上传照片只支持{extensions}格式，核对一下图片格式再上传哈！",
                onLeave: "文件正在上传，如果你现在离开，上传将会被取消。"
            }
        });
	qryCodeLocalNet();//查询地市列表
	$("#add_tag").click(function(){
		showdiv('add_phonetrinal');
	});		
	$("#searchTariff").bind("click",function(){
		var input_text = $("#TariffInput").val()	
		queryPhonetri(input_text);
	});
	$("#searchProduct").bind("click",function(){
		var input_text = $("#ProductInput").val()	
		queryProduct(input_text);
	});
	$("#searchzbactivity").bind("click",function(){
		var input_text = $("#zbactivityInput").val()	
		queryZbActivity(input_text);
	});
	$("#searchlocalnetactivity").bind("click",function(){
		var input_text = $("#localnetactivityInput").val()	
		querylocalnetActivity(input_text);
	});
	$("#searchywb").bind("click",function(){
		var input_text = $("#ywbInput").val()	
		queryywb(input_text);
	});
	  url1=application.fullPath + "authority/productPage/uploadImg";
	 uploadPicFile();
	 
	
});

//查询终端信息
function queryPhonetri(input_text){
	$("#phone_check_text").html("");
	 var data1 = {			
			"terminal_info":input_text
			
		};
	
	 var URL = application.fullPath + "authority/productPage/queryPhoneTerminal";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"终端信息查询中..",
			success:function(message){		
				$("#phoneTri_list").html('');
				if(message.type == "error"){					
					$.alert(message.content);
				}else{	
					
					showdiv('fee_search');
					$("#phoneTri_list").append('<li class="list" style="*display:inline;"><div class="order_row"><div class="order_cell" style="width:100px;">机型代码</div><div class="order_cell" style="width:160px;">机型名称</div><div class="order_cell" style="width:100px;">颜色</div><div class="order_cell" style="width:100px;"></div></div></li>');
					$.each(message.args.phone_list, function(i, item) {
						var data = JSON.stringify(item);
						//YUN-773
						$("#phoneTri_list").append('<li class="list" style="*display:inline;"> <div class="order_row white_bgcolor">'
								+'<div class="order_cell white_bgcolor" style="width:100px;">'+item.terminal_model+'</div> '
								+'<div class="order_cell white_bgcolor" style="width:160px;">'+item.terminal_info+'</div> '
								+'<div class="order_cell white_bgcolor" style="width:100px;">'+item.terminal_color+'</div> '
								+' <div  class="order_cell white_bgcolor" style="width:100px;"><input name="phoneTrinal_check" id="phoneTrinal_check" onClick="phoneTrinalSelect(this)" value="'+item.phone_terminal_ID+'" data=\''+data+'\'  type="radio"></input></div> '								
								+'</div></li>');
			
					
					});
					//解决最后一行资费提示文字叠加到浮选框头部，预留空白位置
					$("#phoneTri_list").append('<div class="list"></div>');
					
				}				
			}
			
		});	
	
}
//添加终端信息
function addTrinalConfirm(){
	var terminal_color=$("#terminal_color").val();//机型颜色
	var terminal_info=$("#terminal_info").val();//机型名称
	var terminal_model=$("#terminal_model").val();//机型代码
		if(terminal_color=='请输入机型颜色'){						
			$("#check_text").html("机型颜色不能为空！");
			$("#check_tag").css("visibility","visible");		
			return;
		}else if(terminal_info=='请输入机型名称'){
			$("#check_text").html("机型名称不能为空！");
			$("#check_tag").css("visibility","visible");		
			return;
		}else if(terminal_model=='请输入机型代码'){
			$("#check_text").html("机型代码不能为空！");
			$("#check_tag").css("visibility","visible");		
			return;
		}
	 var data1 = {			
				"terminal_info":terminal_info,
				"terminal_color":terminal_color,
				"terminal_model":terminal_model
			};
		
		 var URL = application.fullPath + "authority/productPage/insertPhoneTerminal";
			$.ajax({
				url:URL,
				dataType:'json',
				contentType: "application/x-www-form-urlencoded; charset=utf-8", 
				type:'post',
				data:data1,
				waitMsg:"终端信息查询中..",
				success:function(message){		
					if(message.type == "error"){
						hidediv('add_phonetrinal');
						$.alert(message.content);
					}else{
						$.alert("添加成功！");
						hidediv('add_phonetrinal');
						
					}				
				}
				
			});	
		
}
//查询产品
function queryProduct(inputText){
	var ofr_type="500600";//查询3,4G产品
	var tele_type="3G4G";
	var data1 = {			
			"jsessionid":$("#jsessionid").val(),
			"ofr_type":ofr_type,
			"product_name":inputText,
			"tele_type":tele_type
			
		};
	
	 var URL = application.fullPath + "rest/sale/saleSelectedCode";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"产品信息查询中..",
			success:function(message){		
				$("#product_list").html('');
				if(message.type == "error"){					
					$.alert(message.content);
				}else{	
					
					showdiv('product_search');
					$("#product_list").append('<li class="list" style="*display:inline;"><div class="order_row"><div class="order_cell" style="width:100px;">产品编码</div><div class="order_cell" style="width:200px;">产品名称</div><div class="order_cell" style="width:80px;"></div></div></li>');
					$.each(message.args.sale_selected_code_list, function(i, item) {
						var data = JSON.stringify(item);
						//YUN-773
						$("#product_list").append('<li class="list" style="*display:inline;"> <div class="order_row white_bgcolor">'
								+'<div class="order_cell white_bgcolor" style="width:100px;">'+item.product_id+'</div> '
								+'<div class="order_cell white_bgcolor" style="width:200px;" title="'+item.product_name+'">'+item.product_name+'</div> '							
								+' <div  class="order_cell white_bgcolor" style="width:80px;"><input  onClick="productSelect(this)" data=\''+data+'\'  type="radio"></input></div> '								
								+'</div></li>');
			
					
					});
					//解决最后一行资费提示文字叠加到浮选框头部，预留空白位置
					$("#product_list").append('<div class="list"></div>');
					$("#product_list").append('<div class="list"></div>');
					$("#product_list").append('<div class="list"></div>');
				}				
			}
			
		});	
	
	
}
//查询总部合约活动
function queryZbActivity(inputText){
	if(data_phonetrinal==null||data_phonetrinal==''){
		$.alert("请先选择终端信息");
		return;
	}else if(data_product==null||data_product==''){
		$.alert("请先选择产品信息");
		return;
	}
	var data1 = {					
			"input_text":inputText,
			"product_id":data_product.product_id,
			"tele_type":product_tele_type,
			"model":data_phonetrinal.terminal_model,
			"mobile_no":data_phonetrinal.terminal_model
			
		};
	
	 var URL = application.fullPath + "authority/productPage/queryZbActivity";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"活动信息查询中..",
			success:function(message){		
				$("#zbactivity_list").html('');
				if(message.type == "error"){					
					$.alert(message.content);
				}else{	
					
					showdiv('zbactivity_search');
					$("#zbactivity_list").append('<li class="list" style="*display:inline;"><div class="order_row"><div class="order_cell" style="width:150px;">总部活动代码</div><div class="order_cell" style="width:200px;">总部活动名称</div><div class="order_cell" style="width:50px;"></div></div></li>');
					$.each(message.args.chose_activity_list, function(i, item) {
						var data = JSON.stringify(item);
						
						$("#zbactivity_list").append('<li class="list" style="*display:inline;"> <div class="order_row white_bgcolor">'
								+'<div class="order_cell white_bgcolor" style="width:150px;">'+item.activity_id+'</div> '
								+'<div class="order_cell white_bgcolor" style="width:200px;" title="'+ item.activity_name +'">'+ item.activity_name +'</div> '															
								+' <div  class="order_cell white_bgcolor" style="width:50px;"><input  onClick="zbactivitySelect(this)" data=\''+data+'\'  type="radio"></input></div> '								
								+'</div></li>');
			
					
					});
					//解决最后一行资费提示文字叠加到浮选框头部，预留空白位置
					$("#zbactivity_list").append('<div class="list"></div>');
					$("#zbactivity_list").append('<div class="list"></div>');
					$("#zbactivity_list").append('<div class="list"></div>');
				}				
			}
			
		});	
	
	
}
//查询本地合约活动
function querylocalnetActivity(inputText){

	var data1 = {					
			"activity_name":inputText
			
		};
	
	 var URL = application.fullPath + "authority/productPage/queryLocalActivity";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"活动信息查询中..",
			success:function(message){		
				$("#localnetactivity_list").html('');
				if(message.type == "error"){					
					$.alert(message.content);
				}else{	
					
					showdiv('localnetactivity_search');
					$("#localnetactivity_list").append('<li class="list" style="*display:inline;"><div class="order_row"><div class="order_cell" style="width:150px;">本地活动代码</div><div class="order_cell" style="width:200px;">本地活动名称</div><div class="order_cell" style="width:50px;"></div></div></li>');
					$.each(message.args.chose_activity_list, function(i, item) {
						var data = JSON.stringify(item);
						
						$("#localnetactivity_list").append('<li class="list" style="*display:inline;"> <div class="order_row white_bgcolor">'
								+'<div class="order_cell white_bgcolor" style="width:150px;">'+item.activity_code+'</div> '
								+'<div class="order_cell white_bgcolor" style="width:200px;" title="'+ item.activity_name +'">'+ item.activity_name +'</div> '															
								+' <div  class="order_cell white_bgcolor" style="width:50px;"><input  onClick="localnetactivitySelect(this)" data=\''+data+'\'  type="radio"></input></div> '								
								+'</div></li>');
			
					
					});
					//解决最后一行资费提示文字叠加到浮选框头部，预留空白位置
					$("#localnetactivity_list").append('<div class="list"></div>');
					$("#localnetactivity_list").append('<div class="list"></div>');
					$("#localnetactivity_list").append('<div class="list"></div>');
				}				
			}
			
		});	
	
	
}
//查询服务部套餐
function queryywb(inputText){
	var ofr_type="";
	var ywb_type="";
	if(product_tele_type==null||product_tele_type==''){
		$.alert("请先选择产品！");
		return;
	}
	if(product_tele_type == "3G"){
		ofr_type = "802";
		ywb_type="902";
	}else if(product_tele_type == "4G"){
		ofr_type = "803";
		ywb_type="903";
	}
	var data1 = {					
			"jsessionid":$("#jsessionid").val(),
			"ofr_type":ofr_type,
			"input_text":inputText,
			"ywb_ofr_id":ywb_ofr_id,
			"ywb_type":ywb_type
			
		};
	
	 var URL = application.fullPath + "rest/sale/getProductBag";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"服务套餐信息查询中..",
			success:function(message){		
				$("#ywb_list").html('');
				if(message.type == "error"){					
					$.alert(message.content);
				}else{	
					
					showdiv('ywb_search');
					$("#ywb_list").append('<li class="list" style="*display:inline;"><div class="order_row"><div class="order_cell" style="width:150px;">服务套餐编码</div><div class="order_cell" style="width:200px;">服务套餐名称</div><div class="order_cell" style="width:50px;"></div></div></li>');
					$.each(message.args.chk_product_info, function(i, item) {
						var data = JSON.stringify(item);
						
						$("#ywb_list").append('<li class="list" style="*display:inline;"> <div class="order_row white_bgcolor">'
								+'<div class="order_cell white_bgcolor" style="width:150px;">'+item.chk_product_id+'</div> '
								+'<div class="order_cell white_bgcolor" style="width:200px;" title="'+ item.chk_product_name +'">'+ item.chk_product_name +'</div> '															
								+' <div  class="order_cell white_bgcolor" style="width:50px;"><input  onClick="ywbSelect(this)" data=\''+data+'\'  type="radio"></input></div> '								
								+'</div></li>');
			
					
					});
					//解决最后一行资费提示文字叠加到浮选框头部，预留空白位置
					$("#ywb_list").append('<div class="list"></div>');
					$("#ywb_list").append('<div class="list"></div>');
					$("#ywb_list").append('<div class="list"></div>');
				}				
			}
			
		});	
	
	
}

//查询地市列表
function qryCodeLocalNet(){

	var data1 =null;
	
	 var URL = application.fullPath + "authority/productPage/qryCodeLocalNet";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			success:function(message){		
				
				if(message.type == "error"){					
					//$.alert(message.content);
				}else{	
					var obj = $("#city_list");
					obj.empty();
					obj.append("<option value='*' selected='selected'>全区</option>");					 
					$.each(message.args.city_list, function(i, item) {					
						obj.append("<option value='"+item.local_net+"'>"+item.alias_name+"</option>");
					
					});
					
				}				
			}
			
		});	
	
	
}
function phoneTrinalSelect(e){
	var div = $(e);
	var dataStr = div.attr("data");	
	data_phonetrinal = JSON.parse(dataStr);	
	
}

function phoneTrinalConfirm(){
	if(data_phonetrinal!=null&&data_phonetrinal!=''){
	   $("#list_1").html('');	
		var data = JSON.stringify(data_phonetrinal);
		$("#list_1").append('<li class="list" style="*display:inline;"> <div class="order_row white_bgcolor">'
				+'<div class="order_cell white_bgcolor" style="width:250px;">'+data_phonetrinal.terminal_model+'</div>'
				+'<div class="order_cell white_bgcolor" style="width:250px;">'+data_phonetrinal.terminal_info+'</div>'
				+'<div class="order_cell white_bgcolor" style="width:250px;">'+data_phonetrinal.terminal_color+'</div>'			
				+'</div> </li>');
	}
	   hidediv('fee_search');	
	
}
function productSelect(e){
	var div = $(e);
	var dataStr = div.attr("data");	
	data_product = JSON.parse(dataStr);	
	
}
function productConfirm(){	
	if(data_product!=null&&data_product!=''){
	   $("#list_2").html('');	
	   var strdesc="";
		if(data_product.product_desc.length>0){
			if(data_product.product_desc.length>22){
				strdesc=data_product.product_desc.substring(0,22)+"...";
			}else{
				strdesc=data_product.product_desc;	
			}
		}
		product_tele_type=data_product.tele_type;
		ywb_ofr_id=data_product.ofr_id;
		$("#list_2").append('<li class="list" style="*display:inline;"> <div class="order_row white_bgcolor">'
				+'<div class="order_cell white_bgcolor" style="width:250px;">'+data_product.product_id+'</div>'
				+'<div class="order_cell white_bgcolor" style="width:250px;">'+data_product.product_name+'</div>'
				+'<div class="order_cell white_bgcolor" style="width:250px;" title="'+data_product.product_desc+'">'+strdesc+'</div>'			
				+'</div> </li>');	
	}
	   hidediv('product_search');	
	
}
function zbactivitySelect(e){
	var div = $(e);
	var dataStr = div.attr("data");	
	data_zbactivity = JSON.parse(dataStr);	
	
}
function zbactivityConfirm(){
	if(data_zbactivity!=null&&data_zbactivity!=''){
	   $("#list_3").html('');		  
		$("#list_3").append('<li class="list" style="*display:inline;"> <div class="order_row white_bgcolor">'
				+'<div class="order_cell white_bgcolor" style="width:300px;">'+data_zbactivity.activity_id+'</div>'
				+'<div class="order_cell white_bgcolor" style="width:300px;">'+data_zbactivity.activity_name+'</div>'			
				+'</div> </li>');	
	}
	   hidediv('zbactivity_search');	
	
}
function localnetactivitySelect(e){
	var div = $(e);
	var dataStr = div.attr("data");	
	data_localnetactivity = JSON.parse(dataStr);	
	
}
function localnetactivityConfirm(){
	if(data_localnetactivity!=null&&data_localnetactivity!=''){
	   $("#list_4").html('');		  
		$("#list_4").append('<li class="list" style="*display:inline;"> <div class="order_row white_bgcolor">'
				+'<div class="order_cell white_bgcolor" style="width:300px;">'+data_localnetactivity.activity_code+'</div>'
				+'<div class="order_cell white_bgcolor" style="width:300px;">'+data_localnetactivity.activity_name+'</div>'			
				+'</div> </li>');	
	}
	   hidediv('localnetactivity_search');	
	
}
function ywbSelect(e){
	var div = $(e);
	var dataStr = div.attr("data");	
	data_ywb = JSON.parse(dataStr);	
	
}
function ywbConfirm(){
	if(data_ywb!=null&&data_ywb!=''){
	   $("#list_5").html('');		  
		$("#list_5").append('<li class="list" style="*display:inline;"> <div class="order_row white_bgcolor">'
				+'<div class="order_cell white_bgcolor" style="width:300px;">'+data_ywb.chk_product_id+'</div>'
				+'<div class="order_cell white_bgcolor" style="width:300px;">'+data_ywb.chk_product_name+'</div>'			
				+'</div> </li>');	
	}
	   hidediv('ywb_search');	
	
}
//返回
function phoneTrinalBack(hid_tag){
	hidediv(hid_tag);	
}
//隐藏公共方法
function showdiv(popWinId) { 
	if (!window.XMLHttpRequest) { 
		for(var i=0;i<selects.length;i++){
			if(selects[i].style.display!='none'){
				selects_display.push(selects[i]);
				selects[i].style.display = "none";
			};
		}
	}
	document.getElementById("bg_mask").style.display ='block';
	document.getElementById(popWinId).style.display ='block';

}
//公共隐藏方法
function hidediv(popWinId) {
	if (!window.XMLHttpRequest) { 
		for(var i=0;i<selects_display.length;i++){
			selects_display[i].style.display = "";
		}
	}
	document.getElementById("bg_mask").style.display ='none';
	document.getElementById(popWinId).style.display ='none';
}

function uploadPicFile() {//图片上传
	
    $.jUploader({
        button: 'uploadbutton', // 这里设置按钮id
        action: url1, // 这里设置上传处理接口，这个加了参数test_cancel=1来测试取消
        waitMsg:"正在上传图片...",
        onComplete: function (fileName, page) {
        	var htmlNew = '';
        	if(page.args.respose=='0'){//解析成功	
        		 $("#upload_ImgTag").attr("src","/uss_web/uploadImg/"+page.args.picName+"?random="+Math.random());
        		 phone_image_path=page.args.picName;
			}else{
					$.alert(page.args.back);
					return;	
			}
        }
    });  
}
//打包添加列表
function pageSelect(){
	var city_code=$("#city_list").val();
	var city_name=$("#city_list").find("option:selected").text();
	var img_desc=$("#img_desc").val();
	if(data_phonetrinal==null||data_phonetrinal==''){
		$.alert("请先选择终端信息");
		return;
	}else if(data_product==null||data_product==''){
		$.alert("请先选择产品信息");
		return;
	}/*else if(phone_image_path==null||phone_image_path==''){
		$.alert("请上传图片");
		return;
	}*/
	else if(img_desc==null||img_desc==''){
		$.alert("请填产品打包说明");
		return;
	}
	var ids= Math.random();
	page_select='{"ids":"'+ids+'","combined_product_name":"'+img_desc+'","memo":"'+img_desc+'","tele_type":"'+product_tele_type+'","product_id":"'+data_product.product_id+'","product_name":"'+data_product.product_name+'","activity_id":"'+data_zbactivity.activity_id+'","activity_name":"'+data_zbactivity.activity_name+'","terminal_model_code":"'+data_phonetrinal.terminal_model+'","mkt_code":"'+data_localnetactivity.activity_code+'","mkt_name":"'+data_localnetactivity.activity_name+'","terminal_info":"'+data_phonetrinal.terminal_info+'","terminal_color":"'+data_phonetrinal.terminal_color+'","mkt_type":"'+data_localnetactivity.activity_type+'","ywb_code":"'+data_ywb.chk_product_id+'","ywb_name":"'+data_ywb.chk_product_name+'","phone_image_path":"'+phone_image_path+'","local_net":"'+city_code+'","local_name":"'+city_name+'"}';	
	page_select=page_select.replace(/\undefined/g, "");//一次性全部替换
	page_select=JSON.parse(page_select);
	page_list.push(page_select);
	  $("#list_img").html('');
	  var count=0;
	$.each(page_list, function(i, item) {		  
		   count=count+1;
		   var path=application.fullPath+"/uploadImg/"+item.phone_image_path+"?random="+Math.random();
		   var product_name="";
		   var activity_name="";
		   var mkt_name="";
		   var ywb_name="";
		   var terminal_info="";
		   if(item.product_name.length>0){
				if(item.product_name.length>5){
					product_name=item.product_name.substring(0,5)+"...";
				}else{
					product_name=item.product_name;	
				}
			}
		   if(item.activity_name.length>0){
				if(item.activity_name.length>5){
					activity_name=item.activity_name.substring(0,5)+"...";
				}else{
					activity_name=item.activity_name;	
				}
			}
		   if(item.mkt_name.length>0){
				if(item.mkt_name.length>5){
					mkt_name=item.mkt_name.substring(0,5)+"...";
				}else{
					mkt_name=item.mkt_name;	
				}
			}
		   if(item.ywb_name.length>0){
				if(item.ywb_name.length>5){
					ywb_name=item.ywb_name.substring(0,5)+"...";
				}else{
					ywb_name=item.ywb_name;	
				}
			}
		   if(item.terminal_info.length>0){
				if(item.terminal_info.length>5){
					terminal_info=item.terminal_info.substring(0,5)+"...";
				}else{
					terminal_info=item.terminal_info;	
				}
			}
		   
			$("#list_img").append('<li class="list" style="*display:inline;"><div class="order_row white_bgcolor" style=" width:2000px;">'
					+'<div class="order_cell" style="width:83px;" >'+count+'&nbsp;&nbsp;<a  href="javascript:void(0);"id="'+item.ids+'" onClick="deletePageselect(this);">删除</a></div>'
					+'<div class="order_cell" style="width:100px;" title="查看大图片请点击"><a  href="javascript:void(0);" onClick="maxImg(this);" data=\''+path+'\'><img  src="'+path+'" width="50" height="30"/></a></div>'			
					+'<div class="order_cell" style="width:100px;">'+item.terminal_model_code+'</div>'
					+'<div class="order_cell" style="width:150px;" title="'+item.terminal_info+'">'+terminal_info+'</div>'
					+'<div class="order_cell" style="width:100px;">'+item.terminal_color+'</div>'
					+'<div class="order_cell" style="width:100px;">'+item.product_id+'</div>'
					+'<div class="order_cell" style="width:150px;" title="'+item.product_name+'">'+product_name+'</div>'
					+'<div class="order_cell" style="width:150px;">'+item.activity_id+'</div>'
					+'<div class="order_cell" style="width:150px;" title="'+item.activity_name+'">'+activity_name+'</div>'
					+'<div class="order_cell" style="width:150px;">'+item.mkt_code+'</div>'
					+'<div class="order_cell" style="width:150px;" title="'+item.mkt_name+'">'+mkt_name+'</div>'
					+'<div class="order_cell" style="width:100px;">'+item.ywb_code+'</div>'
					+'<div class="order_cell" style="width:150px;" title="'+item.ywb_name+'">'+ywb_name+'</div>'
					+'<div class="order_cell" style="width:100px;">'+item.local_name+'</div>'
					+'</div> </li>');
		      to_maxImg();
	});
	
	/**********清空以下数据*************/
	  $("#list_1").html('');	
	  $("#list_2").html('');	
	  $("#list_3").html('');	
	  $("#list_4").html('');	
	  $("#list_5").html('');	
	  $("#list_6").html('');
	  $("#img_desc").val('');
	  $("#upload_ImgTag").attr("src","/uss_web/images/product_defunt.jpg?random="+Math.random());
	 data_phonetrinal="";//保存选择的终端信息
	 data_product="";//保存选择的产品信息
	 product_tele_type="";//选择产品确定电信类型
	 data_zbactivity="";//保存选择总部合约活动信息
	 data_localnetactivity="";//保存选择本地合约活动信息
	 data_ywb="";////保存选择服务包信息
	 ywb_ofr_id="";//选择产品的ofr_id	
	 phone_image_path="";//图片名称或者路径
	
}
//删除选择列表项
function deletePageselect(e){
	 $("#list_img").html('');
	 var div = $(e);
	 var dataStr = div.attr("id");	
	var count=0;
	for(var n=0;n<page_list.length;n++){
		 if(dataStr===page_list[n].ids){
			    page_list.splice(n,1);//删除选项
		}
	}
	
	$.each(page_list,function(i, item) {
		  
		   count=count+1; 
		   var path=application.fullPath+"/uploadImg/"+item.phone_image_path+"?random="+Math.random();
		   var product_name="";
		   var activity_name="";
		   var mkt_name="";
		   var ywb_name="";
		   var terminal_info="";
		   if(item.product_name.length>0){
				if(item.product_name.length>5){
					product_name=item.product_name.substring(0,5)+"...";
				}else{
					product_name=item.product_name;	
				}
			}
		   if(item.activity_name.length>0){
				if(item.activity_name.length>5){
					activity_name=item.activity_name.substring(0,5)+"...";
				}else{
					activity_name=item.activity_name;	
				}
			}
		   if(item.mkt_name.length>0){
				if(item.mkt_name.length>5){
					mkt_name=item.mkt_name.substring(0,5)+"...";
				}else{
					mkt_name=item.mkt_name;	
				}
			}
		   if(item.ywb_name.length>0){
				if(item.ywb_name.length>5){
					ywb_name=item.ywb_name.substring(0,5)+"...";
				}else{
					ywb_name=item.ywb_name;	
				}
			}
		   if(item.terminal_info.length>0){
				if(item.terminal_info.length>5){
					terminal_info=item.terminal_info.substring(0,5)+"...";
				}else{
					terminal_info=item.terminal_info;	
				}
			}
		   
			$("#list_img").append('<li class="list" style="*display:inline;"><div class="order_row white_bgcolor" style=" width:2000px;">'
					+'<div class="order_cell" style="width:83px;">'+count+'&nbsp;&nbsp;<a  href="javascript:void(0); "id="'+item.ids+'" onClick="deletePageselect(this);">删除</a></div>'
					+'<div class="order_cell" style="width:100px;" title="查看大图片请点击"><a  href="javascript:void(0);" onClick="maxImg(this);" data=\''+path+'\'><img  src="'+path+'" width="50" height="30"/></a></div>'			
					+'<div class="order_cell" style="width:100px;">'+item.terminal_model_code+'</div>'
					+'<div class="order_cell" style="width:150px;" title="'+item.terminal_info+'">'+terminal_info+'</div>'
					+'<div class="order_cell" style="width:100px;">'+item.terminal_color+'</div>'
					+'<div class="order_cell" style="width:100px;">'+item.product_id+'</div>'
					+'<div class="order_cell" style="width:150px;" title="'+item.product_name+'">'+product_name+'</div>'
					+'<div class="order_cell" style="width:150px;">'+item.activity_id+'</div>'
					+'<div class="order_cell" style="width:150px;" title="'+item.activity_name+'">'+activity_name+'</div>'
					+'<div class="order_cell" style="width:150px;">'+item.mkt_code+'</div>'
					+'<div class="order_cell" style="width:150px;" title="'+item.mkt_name+'">'+mkt_name+'</div>'
					+'<div class="order_cell" style="width:100px;">'+item.ywb_code+'</div>'
					+'<div class="order_cell" style="width:150px;" title="'+item.ywb_name+'">'+ywb_name+'</div>'
					+'<div class="order_cell" style="width:100px;">'+item.local_name+'</div>'
					+'</div> </li>');
		      to_maxImg();
	});
}
function maxImg(e){	
	var div = $(e);
	var dataStr = div.attr("data");	
	$("#max_ImgTag").attr("src",dataStr);
	showdiv('max_img');
}
function to_maxImg(){ 
	
	var size=4*$("#list_img li img").width(); 
	$("#list_img li img").mouseover(function(event) { 
		var $target=$(event.target); 
		if($target.is('img')) { 
			$("<img id='tip_img' src='"+$target.attr("src")+"' style='position:absolute;'>").css({ 
				"height":"auto","width":size,"top":event.pageX+20,"left":event.pageY+0
		}).appendTo($("#list_img")); 
	} 
	}).mouseout(function() { 
		$("#tip_img").remove(); 
	}).mousemove(function(event) { 
		$("#tip_img").css({"left":event.pageX+20,"top":event.pageY+0}); 
	}); 
}
//删除终端信息
function deleteTrinalConfirm(){
	var phone_terminal_ID=$("input[name='phoneTrinal_check']:checked").val();  
	 if(phone_terminal_ID==null||phone_terminal_ID==""){
			$("#phone_check_text").html("请选择删除项！");
			$("#phone_check_tag").css("visibility","visible");		
			return;
	}			
	var GetURl= application.fullPath + 'authority/productPage/deletePhoneTerminal';
	
	$.ajax({
		url:GetURl,
		data:{
			"phone_terminal_ID":phone_terminal_ID			
		},
		dataType:'json',
		type:'post',
		waitMsg:"删除中...",
		success:function(page){
			if(page.type!= "error"){
				queryPhonetri('');
				$("#phone_check_text").html("");
			}else{
				$("#phone_check_text").html("");
				 hidediv('fee_search');	
			}
			//$.alert(page.content);
			
		}
	});
	
}

//批量保存
function allInsert(){
	 page_list=JSON.stringify(page_list);
	 if(page_list==null||page_list==""||page_list=="[]"){
		$.alert("请先添加数据!");
		return;
	}			
	var GetURl= application.fullPath + 'authority/productPage/insertPlanCodeCombinedProduct';
	
	$.ajax({
		url:GetURl,
		data:{
			"page_list":page_list			
		},
		dataType:'json',
		type:'post',
		waitMsg:"正在提交...",
		success:function(page){
			if(page.type!= "error"){
				page_list=[];
				$("#list_img").html('');
			}
			$.alert(page.content);
			
		}
	});
	
}
