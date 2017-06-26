var url1="";
$(document).ready(function() {
	var province = $("#province_code").val();
	if(province=="gx"){
		$("#oper_role_id").hide();
	}
	$("#queryExistAuthority").hide();
	
	$("#query").click(function() {
		selectPage();
	});
	$("#query2").click(function() {
		selectPage2();
	});
	$("#addRule").click(function() {
		addRule();
	});
	$("#delRule").click(function() {
		delRule();
	});
	$("#queryRule").click(function() {
		queryRule();
	});
	$("#oper_role_id").click(function(){
		operAuthorityClick();
	});
	$("#query_oper_info").click(function(){
		queryOperInfo();
	});
	$("#query_role_info").click(function(){
		queryRoleInfo();
	});
	$("#add_del_role").click(function(){
		addOrDelOperRole();
	});
	$("#del_role").click(function(){
		delOperRole();
	});
	
//	$("#choosefile").click(function() {
//		var path=$("#realpath").val();
//		if(path!=''){
//			wirtefile(path);
//		}else{			
//		  $("#filepath").click();
//		 
//		}
//		
//	});
//	
	 url1=application.fullPath + "authority/ofr_code_config/uploadImg";
	 uploadPicFile();
	 
	
});

function uploadPicFile() {//图片原图上传临时文件
	$("#loading").show();//动态加载小图标	
    $.jUploader({
        button: 'uploadbutton', // 这里设置按钮id
        action: url1, // 这里设置上传处理接口，这个加了参数test_cancel=1来测试取消
        waitMsg:"正在读取文件...",
        onComplete: function (fileName, page) {
        	var htmlNew = '';
        	if(page.args.respose=='0'){//解析成功
				$("#filepath").val("");
			if(page.args.data.length>0){
				$("#writelist").html('');	
				var allList=[];			
				var datalentgh=page.args.data.length;
				if(datalentgh>50){//设置显示最多显示50条
					datalentgh=50;
				}
			  for(var i = 0;i < datalentgh;i++){
					var infoQryOrderVo = page.args.data[i];				
					allList.push(JSON.stringify(infoQryOrderVo));
					var str="";
					var strname="";
					if(infoQryOrderVo.ofr_desc.length>0){
						if(infoQryOrderVo.ofr_desc.length>10){
					     str=infoQryOrderVo.ofr_desc.substring(0,10)+"...";
						}else{
							 str=infoQryOrderVo.ofr_desc;	
						}
					}
					if(infoQryOrderVo.ofr_name.length>0){
						if(infoQryOrderVo.ofr_name.length>5){
							strname=infoQryOrderVo.ofr_name.substring(0,5)+"...";
						}else{
							strname=infoQryOrderVo.ofr_name;	
						}
					}
					
					var html = '<li class="list" style="*display:inline;">'
						+'<div class="order_row white_bgcolor">';			
					 if(infoQryOrderVo.eff_flag=='Y'){
				    	 html +='<div class="order_cell white_bgcolor" style="width:83px;" title="'+infoQryOrderVo.ofr_name+'">'+strname+'</div>'
				    	
							+'<div class="order_cell white_bgcolor" style="text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow: hidden;" title="'+infoQryOrderVo.ofr_desc+'">'+str+'</div>'				
						    +'<div class="order_cell white_bgcolor" style="width:83px;">生效</div>'
							+'<div class="order_cell white_bgcolor" style="width:83px;">'+infoQryOrderVo.ofr_type+'</div>'					  
						    +'<div class="order_cell white_bgcolor" style="width:83px;">'+infoQryOrderVo.tele_type+'</div>'					  
						    +'<div class="order_cell white_bgcolor" style="width:83px;">'+infoQryOrderVo.bss_product+'</div>'
				    	 
				    		
						    +'<div class="order_cell white_bgcolor" style="width:83px;">'+infoQryOrderVo.begin_date+'</div>'					  
						    +'<div class="order_cell white_bgcolor" style="width:83px;">'+infoQryOrderVo.end_date+'</div>'
				    	    +'<div class="order_cell white_bgcolor" style="width:83px;">'+infoQryOrderVo.product_id+'</div>';					  
						  
				    	 if(infoQryOrderVo.pay_flag=='1'){
				    	      html +='<div class="order_cell white_bgcolor" style="width:83px;">后付费</div>';
						    }else{
						    	 html +='<div class="order_cell white_bgcolor" style="width:83px;">预付费</div>';
						    }
						  
					    }else{
					    	 html +='<div class="order_cell white_bgcolor" style="width:83px;" title="'+infoQryOrderVo.ofr_name+'">'+strname+'</div>'
						    	
								+'<div class="order_cell white_bgcolor" style="text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow: hidden;" title="'+infoQryOrderVo.ofr_desc+'">'+str+'</div>'				
							    +'<div class="order_cell white_bgcolor" style="width:83px;">生效</div>'
								+'<div class="order_cell white_bgcolor" style="width:83px;">'+infoQryOrderVo.ofr_type+'</div>'					  
							    +'<div class="order_cell white_bgcolor" style="width:83px;">'+infoQryOrderVo.tele_type+'</div>'					  
							    +'<div class="order_cell white_bgcolor" style="width:83px;">'+infoQryOrderVo.bss_product+'</div>'
					    	 
					    		
							    +'<div class="order_cell white_bgcolor" style="width:83px;">'+infoQryOrderVo.begin_date+'</div>'					  
							    +'<div class="order_cell white_bgcolor" style="width:83px;">'+infoQryOrderVo.end_date+'</div>'
					    	    +'<div class="order_cell white_bgcolor" style="width:83px;">'+infoQryOrderVo.product_id+'</div>';					  
							  
							    if(infoQryOrderVo.pay_flag=='1'){
						    	      html +='<div class="order_cell white_bgcolor" style="width:83px;">后付费</div>';
								    }else{
								    	 html +='<div class="order_cell white_bgcolor" style="width:83px;">预付费</div>';
								    }
					    }
				    	 html +='</div></li>';  
					    htmlNew +=html;
				   
				   
				    
			 }
			$("#jsonlist").val("["+allList+"]");
			 $("#writelist").append(htmlNew);
			}
			}else{
					$.alert(page.args.back);
					return;	
			}
        }
    });  
}

function selectPage() {
	var start_date = $.trim($("#date_begin").val());
	var end_date = $.trim($("#date_end").val());
	var product_id = $.trim($("#product_id").val());	
	var ofr_name = $.trim($("#ofr_name").val());
	
	if(ofr_name=="请输入产品名称"){
		ofr_name="";
	}
	if(product_id=="请输入产品ID"){
		product_id="";
	}
	var GetURl= application.fullPath + 'authority/ofr_code_config/queryCodeConfigOfr';
	
	$.ajax({
		url:GetURl,
		data:{
			"begin_date":start_date,
			"end_date":end_date,
			"product_id":product_id,
			"ofr_name":ofr_name
			
		},
		dataType:'json',
		type:'post',
		waitMsg:"查询产品",
		success:function(page){
			$("#list").html('');
			var htmlNew = '';
			if(page.args!=null){
			if(page.args.sale_selected_code_list.length>0){
			 for(var i = 0;i < page.args.sale_selected_code_list.length;i++){
				var infoQryOrderVo = page.args.sale_selected_code_list[i];			
				var str="";
				var strname="";
				
				if(infoQryOrderVo.ofr_desc.length>0){
					if(infoQryOrderVo.ofr_desc.length>10){
				     str=infoQryOrderVo.ofr_desc.substring(0,10)+"...";
					}else{
						 str=infoQryOrderVo.ofr_desc;	
					}
				}
				
				if(infoQryOrderVo.ofr_name.length>0){
					if(infoQryOrderVo.ofr_name.length>5){
						strname=infoQryOrderVo.ofr_name.substring(0,5)+"...";
					}else{
						strname=infoQryOrderVo.ofr_name;	
					}
				}
				
				var html = '<a href="#"><li class="list" style="*display:inline;" onClick="jump(\''+ infoQryOrderVo.ofr_id+'\')"><div class="order_row white_bgcolor">';			
				    if(infoQryOrderVo.eff_flag=='Y'){
			    	 html +='<div class="order_cell white_bgcolor" style="width:83px;text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow: hidden;" title="'+infoQryOrderVo.ofr_name+'">'+strname+'</div>'					  	
						+'<div class="order_cell white_bgcolor" style="text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow: hidden;" title="'+infoQryOrderVo.ofr_desc+'">'+str+'</div>'				
						+'<div class="order_cell white_bgcolor" style="width:83px;">生效</div>'
						+'<div class="order_cell white_bgcolor" style="width:83px;">'+infoQryOrderVo.ofr_type+'</div>'					  
					    +'<div class="order_cell white_bgcolor" style="width:83px;">'+infoQryOrderVo.tele_type+'</div>'					  
					    +'<div class="order_cell white_bgcolor" style="width:83px;">'+infoQryOrderVo.bss_product+'</div>'
					    +'<div class="order_cell white_bgcolor" style="width:83px;text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow: hidden;"  title="'+infoQryOrderVo.eff_date+'">'+infoQryOrderVo.eff_date+'</div>'					  
					    +'<div class="order_cell white_bgcolor" style="width:83px;text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow: hidden;"  title="'+infoQryOrderVo.exp_date+'">'+infoQryOrderVo.exp_date+'</div>'
			    	    +'<div class="order_cell white_bgcolor" style="width:83px;">'+infoQryOrderVo.product_id+'</div>';					  
					  
			    	 if(infoQryOrderVo.prepay_flag=='1'){
			    	      html +='<div class="order_cell white_bgcolor" style="width:83px;">后付费</div>';
					    }else{
					    	 html +='<div class="order_cell white_bgcolor" style="width:83px;">预付费</div>';
					    }
			    	  
				    }else{
				    	 html +='<div class="order_cell white_bgcolor" style="width:83px;text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow: hidden;" title="'+infoQryOrderVo.ofr_name+'">'+strname+'</div>'					  	
							+'<div class="order_cell white_bgcolor" style="text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow: hidden;" title="'+infoQryOrderVo.ofr_desc+'">'+str+'</div>'				
							+'<div class="order_cell white_bgcolor" style="width:83px;">生效</div>'
							+'<div class="order_cell white_bgcolor" style="width:83px;">'+infoQryOrderVo.ofr_type+'</div>'					  
						    +'<div class="order_cell white_bgcolor" style="width:83px;">'+infoQryOrderVo.tele_type+'</div>'					  
						    +'<div class="order_cell white_bgcolor" style="width:83px;">'+infoQryOrderVo.bss_product+'</div>'
						    +'<div class="order_cell white_bgcolor" style="width:83px;text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow: hidden;"  title="'+infoQryOrderVo.eff_date+'">'+infoQryOrderVo.eff_date+'</div>'					  
						    +'<div class="order_cell white_bgcolor" style="width:83px;text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow: hidden;"  title="'+infoQryOrderVo.exp_date+'">'+infoQryOrderVo.exp_date+'</div>'
				    	    +'<div class="order_cell white_bgcolor" style="width:83px;">'+infoQryOrderVo.product_id+'</div>';					  
						  
						    if(infoQryOrderVo.prepay_flag=='1'){
					    	      html +='<div class="order_cell white_bgcolor" style="width:83px;">后付费</div>';
							    }else{
							    	 html +='<div class="order_cell white_bgcolor" style="width:83px;">预付费</div>';
							    }
				    }
			    	 html +='</div></li></a>';  
					    htmlNew +=html;
				   
				   
				    
			 }
			 $("#list").append(htmlNew);
			
			}else{
				htmlNew='<li class="list" style="*display:inline;"><div class="order_row white_bgcolor"><div class="order_cell white_bgcolor"></div><div class="order_cell white_bgcolor"></div><div class="order_cell white_bgcolor"></div><div class="order_cell white_bgcolor"></div><div class="order_cell white_bgcolor"></div></div></li>';
				 $("#list").append(htmlNew);
			}
		   }else{
			   htmlNew='<li class="list" style="*display:inline;"><div class="order_row white_bgcolor"><div class="order_cell white_bgcolor"></div><div class="order_cell white_bgcolor"></div><div class="order_cell white_bgcolor"></div><div class="order_cell white_bgcolor"></div><div class="order_cell white_bgcolor"></div></div></li>';
			   $("#list").append(htmlNew);
		   }
			 
			 
			
			
		}
	});
	
}

function selectPage2() {
//	var start_date = $.trim($("#start_date").val());
//	var end_date = $.trim($("#end_date").val());
	var product_id = $.trim($("#product_id2").val());	
	var ofr_name = $.trim($("#ofr_name2").val());
	
	if(ofr_name=="产品名称"){
		ofr_name="";
	}
	if(product_id=="产品ID"){
		product_id="";
	}
	var GetURl= application.fullPath + 'authority/ofr_code_config/queryCodeConfigOfr';
	
	$.ajax({
		url:GetURl,
		data:{
			"product_id":product_id,
			"ofr_name":ofr_name
			
		},
		dataType:'json',
		type:'post',
		waitMsg:"查询产品",
		success:function(page){
			$("#list2").html('');
			var htmlNew = '';
			if(page.args!=null){
			if(page.args.sale_selected_code_list.length>0){
			 for(var i = 0;i < page.args.sale_selected_code_list.length;i++){
				var infoQryOrderVo = page.args.sale_selected_code_list[i];			
				
				var html = ' <li class="list" style="*display:inline;">'+
                           '<div class="order_row white_bgcolor" style="width:535px;">'+
                           '<div class="order_cell gray_bgcolor" style="width:33px;">'+
                           '<input type="checkbox" name="product_record" style="height:28px;" value="'+infoQryOrderVo.bss_product+'"/> </div>';
               			
				   
			    	
				 html  +='<div class="order_cell gray_bgcolor_f8" style="width:99px;">'+infoQryOrderVo.bss_product+'</div>'				
					    +'<div class="order_cell gray_bgcolor_f8" style="text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow: hidden;" title="'+infoQryOrderVo.ofr_name+'">'+infoQryOrderVo.ofr_name+'</div>'					  
					    +'<div class="order_cell gray_bgcolor_f8" style="width:266px;text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow: hidden;" title="'+infoQryOrderVo.ofr_desc+'">'+infoQryOrderVo.ofr_desc+'</div>';					  
			    	  
				   
			    	 html +='</div></li>';  
					    htmlNew +=html;
				   
				   
				    
			 }
			 $("#list2").append(htmlNew);
			
			}else{
				htmlNew='<li class="list" style="*display:inline;"><div class="order_row white_bgcolor"><div class="order_cell white_bgcolor"></div><div class="order_cell white_bgcolor"></div><div class="order_cell white_bgcolor"></div><div class="order_cell white_bgcolor"></div><div class="order_cell white_bgcolor"></div></div></li>';
				 $("#list").append(htmlNew);
			}
		   }else{
			   htmlNew='<li class="list" style="*display:inline;"><div class="order_row white_bgcolor"><div class="order_cell white_bgcolor"></div><div class="order_cell white_bgcolor"></div><div class="order_cell white_bgcolor"></div><div class="order_cell white_bgcolor"></div><div class="order_cell white_bgcolor"></div></div></li>';
			   $("#list").append(htmlNew);
		   }
			 
			 
			
			
		}
	});
	
}

function jump(ofr_id){	
   window.open(application.fullPath+"authority/ofr_code_config/queryCodeOfrByOfrId?ofr_id="+ofr_id,"","width=1050,height=465,top=80,left=80,toolbar=no, menubar=no, scrollbars=no, resizable=no");
			
}

function addRule(){
//	 console.info(11111);
	 var product_record =[];
	 
	 $("input[name='product_record']:checked").each(function(){
		 product_record.push($(this).val());
	 });
//	 console.info(product_record.length);
	 if(product_record.length<=0){
		 $.alert("请先选择产品");
			return;
	 }
	 
	 
	 product_record =product_record.join(",");
//	 console.info(product_record);
	 var authority_record=[];
 
	 $("input[name='authority_record']:checked").each(function(){
		 authority_record.push($(this).val());
	 });
	 
	 if(authority_record.length<=0){
		 $.alert("请选择角色");
			return;
	 }
	 
	 authority_record =authority_record.join(",");
//	 console.info(authority_record);
	
	 var url =application.fullPath +'authority/ofr_code_config/addRuleByRoleAndProductId';
	 
	 $.ajax({
			url:url,
			data:{
				"productIds":product_record,
				"roleCodes":authority_record	
			},
			dataType:'json',
			type:'post',
			waitMsg:"添加产品权限",
			success:function(message){
//				 console.info(message);
				 var type =message.args.type;
				if(type =="success"){
					
					$.alert("权限添加成功");
					
				}else{
					
					$.alert("权限添加失败");
				}
				
				
				
			
			 }
			});
}

function delRule(){
//	 console.info(22222);
	 
	 var authority_rule_record =[];
	 
	 $("input[name='authority_rule_record']:checked").each(function(){
		 authority_rule_record.push($(this).val());
//		 console.info(authority_rule_record);

	 });
//	    console.info(authority_rule_record);
	 if(authority_rule_record.length <=0){
		 $.alert("请先查询已存在产品权限再删除");
		 return;
	 }
	
	
	 authority_rule_record =JSON.stringify(authority_rule_record);
//	 console.info(authority_rule_record);
	
	 
	 var url =application.fullPath +'authority/ofr_code_config/deleteRuleByRoleAndProductId';
	 
	 $.ajax({
			url:url,
			data:{
				"ruleRoleProducts":authority_rule_record
			},
			dataType:'json',
			type:'post',
			waitMsg:"删除产品权限",
			success:function(message){
//				 console.info(message);
				 var type =message.args.type;
				if(type =="success"){
					$("input[name='authority_rule_record']:checked").parents("li").remove();
				}else{
					
					$.alert("权限删除失败");
				}
				
				
				
			
			 }
			});
}


function queryRule(){
//	console.info(333333);	
	
	var product_id_select =$("input[name='product_record']:checked");
	var product_id =product_id_select.val();
//	console.info(product_id);
	if(!product_id ){
		$.alert("请先选择产品");
		return;
	}
//	console.info(product_id_select.length);
	if(product_id_select.length>1 ){
		$.alert("查询请单选产品");
		return;
	}
	

	 var url =application.fullPath +'authority/ofr_code_config/queryRuleByProductId';
	 
	 $.ajax({
			url:url,
			data:{
				"product_id":product_id
			},
			dataType:'json',
			type:'post',
			waitMsg:"查询已存在产品权限",
			success:function(message){
//				 console.info(message);
				 var type =message.args.type;
//				 console.info(message.args.ruleRoleProducts);
				 $("#list3").html('');
				 $("#queryExistAuthority").show();
				 
					var htmlNew = '';
				 
				if(message.args.ruleRoleProducts.length>0){
//					 console.info("forforforfor");
					for(var i = 0;i < message.args.ruleRoleProducts.length;i++){
						var ruleRoleProduct = message.args.ruleRoleProducts[i];	
//						 console.info(ruleRoleProduct);
						var role_name ="";
						if(ruleRoleProduct.role_code =="1"){
							role_name ="系统管理员";
						}else if(ruleRoleProduct.role_code =="2"){
							role_name ="稽核人员";
						}else if(ruleRoleProduct.role_code =="3"){
							role_name ="营业厅经理";
						}
						
						var html = ' <li class="list" style="width:436px;*display:inline;">'+
		                           '<div class="order_row" style="width:436px;">'+
		                           '<div class="order_cell gray_bgcolor" style="width:33px;">'+
		                           '<input type="checkbox" name="authority_rule_record" value='+JSON.stringify(ruleRoleProduct)+' style="height:28px;"/></div>';
		               			
						   
					    	
						 html  +='<div class="order_cell gray_bgcolor_f8">'+ruleRoleProduct.product_id+'</div>'				
							    +'<div class="order_cell gray_bgcolor_f8">'+ruleRoleProduct.role_code+'</div>'					 
							    +'<div class="order_cell gray_bgcolor_f8">'+role_name+'</div>';					  
 
						 html +='</div></li>';  
						    htmlNew +=html;   
						    
					 }
					 $("#list3").append(htmlNew);
//					$("#authority_rule_record"+i).data("ruleRoleProduct",ruleRoleProduct);
					
				}else{
					
				}
				
				
				
			
			 }
			});
	
	
}

function update(){
	var eff_flag = $.trim($("#eff_flag").val());
	var ofr_desc = $.trim($("#ofr_desc").val());
	var bss_product = $.trim($("#bss_product").val());	
	var ofr_id = $.trim($("#ofr_id").val());
	var ofr_name = $.trim($("#ofr_name").val());	
	var oldeff_flag = $.trim($("#oldeff_flag").val());//修改之前的失效标识	
	var ofr_type = $.trim($("#ofr_type").val());
	var tele_type = $.trim($("#tele_type").val());
	var product_id=$.trim($("#product_id").val());	
	var pay_flag=$.trim($("#pay_flag").val());	
	
	if(eff_flag==""){
		$.alert("请选产品是否失效");
		return;
	}	
	else if(ofr_desc==""){
		$.alert("产品描述不能为空");
		return;
	}		
	else if(ofr_name==""){
		$.alert("产品名称不能为空");
		return;
	}	
	else if(tele_type==""){
	 alert("电信类型不能为空");
		return;
	}	
	else if(ofr_type==""){
		$.alert("产品类型不能为空");
		return;
	}		
	var GetURl= application.fullPath + 'authority/ofr_code_config/updateCodeConfigOfr';
	
	$.ajax({
		url:GetURl,
		data:{
			"ofr_id":ofr_id,
			"ofr_desc":ofr_desc,
			"bss_product":bss_product,
			"ofr_name":ofr_name,			
			"tele_type":tele_type,
			"ofr_type":ofr_type,
			"eff_flag":eff_flag,
			"oldeff_flag":oldeff_flag,
			"product_id":product_id,
			"pay_flag":pay_flag
			
		},
		dataType:'json',
		type:'post',
		waitMsg:"正在更新",
		success:function(page){
			if(confirm(page.args.back)){
				window.close();
			}

		}
	});
	
}

function insert(){
	var jsonlist = $("#jsonlist").val();
	
	 if(jsonlist==""){
		$.alert("数据为空或者已经处理过该数据,请重新读取数据!");
		return;
	}	
		
	var GetURl= application.fullPath + 'authority/ofr_code_config/insertCodeConfigOfr';
	
	$.ajax({
		url:GetURl,
		data:{
			"jsonList":jsonlist			
		},
		dataType:'json',
		type:'post',
		waitMsg:"正在处理...",
		success:function(page){
			
			 $.alert(page.args.back);
			 $("#jsonlist").val("");
			//	return;		
		
		//	$.alert(page.args.content);
			
		}
	});
	
}

//点击工号权限菜单选项
function operAuthorityClick(){
	document.getElementById("oper_authority").style.display  = "block";
	document.getElementById("authority").style.display  = "none";
	document.getElementById("product").style.display  = "none";
	document.getElementById("product2").style.display  = "none";
	$("#oper_role_id").addClass("current");
	$("#roe_id").removeClass("current");
	$("#ofr_id").removeClass("current");
}

//工号权限配置——>点击查询进行模糊查询
function queryOperInfo() {
	var oper_no = $.trim($("#oper_id").val());	
	var oper_name = $.trim($("#oper_name").val());
	
	if(oper_no=="请输入工号编码"){
		oper_no="";
	}
	if(oper_name=="请输入工号名称"){
		oper_name="";
	}
	if(oper_no==""&&oper_name==""){
		$.alert("请输入工号编码或编号名称再查询");
		return;
	}
	var GetURl= application.fullPath + 'authority/authority_configure/queryOperInfo';
	
	$.ajax({
		url:GetURl,
		data:{
			"oper_no":oper_no,
			"oper_name":oper_name
			
		},
		dataType:'json',
		type:'post',
		waitMsg:"正在查询工号信息...",
		success:function(message){
			console.info(JSON.stringify(message));
			pre_oper_id ="";
			$("input[name='authority_record_oper']:checked").each(function(){
				$(this).attr("checked",false);//取消默认选中的
			});
			$("#oper_info_list").html('');
			$("#role_info_list").html('');
			var htmlNew = '';
			//$("#check_flag_1").attr("checked",true);
			if(message.args.operInfoList != null){
				if(message.args.operInfoList.length>0){
					for(var i = 0;i < message.args.operInfoList.length;i++){
						var operInfo = message.args.operInfoList[i];
						var data = JSON.stringify(operInfo);
						var effFlag = "";
						if(operInfo.flag == 0){
							effFlag = "是";
						}else{
							effFlag = "否";
						}
						var html='<li class="list" style="*display:inline;">'+
									'<div class="order_row white_bgcolor" style="width:600px;">'+
								 	'<div class="order_cell gray_bgcolor" style="width:33px;">'+
								 		'<input type="checkbox" class="oper_info" id="oper_info_id_'+i+'" data='+data+' name="oper_record" style="height:28px;" value="'+operInfo.oper_no+'"/></div>'
						
						html  +='<div class="order_cell white_bgcolor" style="width:99px;">'+operInfo.oper_no+'</div>'				
								+'<div class="order_cell white_bgcolor">'+operInfo.oper_name+'</div>'					  
								+'<div class="order_cell white_bgcolor" style="width:263px;">'+operInfo.dept_name+'</div>'
								+'<div class="order_cell white_bgcolor" style="width:67px;">'+effFlag+'</div>';					  
							    	  
								   
						html +='</div></li>';  
						htmlNew +=html;
					}
					
					if(message.args.operInfoList.length==1){
						//如果子工号信息数据只有一条，那么就将其相对应的权限角色默认打钩
						if(message.args.operRoleList != null){
							if(message.args.operRoleList.length>0){
								for(var i = 0;i <message.args.operRoleList.length;i++){
									var role_id = message.args.operRoleList[i].role_id;
									//alert("kkkkkk   "+role_id);
									$("input[name='authority_record_oper']").each(function(){
										var roleId = $(this).val();
										 if(role_id == roleId){
											 var check_id = $(this).attr("id")
											 var id = "";
											 if(roleId==1){
												 id="test1";
											 }else if(roleId==2){
												 id="test2";
											 }else if(roleId==3){
												 id="test3";
											 }
											 $("#"+id).html('');
											 $("#"+id).html('<input type="checkbox" name="authority_record_oper" value="'+roleId+'" id ="'+check_id+'" checked="checked" style="height:28px;"/>')
										 }
										 
									 });
								}
							}
						}
						
					}
					
					$("#oper_info_list").append(htmlNew);
				}
				$(".oper_info").click(selectOperNo);
			}
			
			 }
	});
	
}


//权限查询
function queryRoleInfo() {
	var role_id = $.trim($("#role_id").val());	
	var role_name = $.trim($("#role_name").val());
	
	if(role_id=="请输入角色编码"){
		role_id="";
	}
	if(role_name=="请输入角色名称"){
		role_name="";
	}

	var GetURl= application.fullPath + 'authority/authority_configure/queryRoleInfo';
	
	$.ajax({
		url:GetURl,
		data:{
			"oper_no":role_id,
			"oper_name":role_name
			
		},
		dataType:'json',
		type:'post',
		waitMsg:"正在查询角色信息...",
		success:function(message){
			console.info(JSON.stringify(message));
			pre_oper_id ="";
			$("input[name='authority_record_oper']:checked").each(function(){
				$(this).attr("checked",false);//取消默认选中的
			});
			$("#role_info_list").html('');
			var htmlNew = '';
			//$("#check_flag_1").attr("checked",true);
			if(message.args.roleInfoList != null){
				if(message.args.roleInfoList.length>0){
					for(var i = 0;i < message.args.roleInfoList.length;i++){
						var roleInfo = message.args.roleInfoList[i];
						var data = JSON.stringify(roleInfo);
						var effFlag = "";
						if(roleInfo.flag == 0){
							effFlag = "是";
						}else{
							effFlag = "否";
						}
						var html='<li class="list" style="*display:inline;">'+
									'<div class="order_row white_bgcolor" style="width:268px;">'+
								 	'<div class="order_cell gray_bgcolor" style="width:33px;" id="infoRoleList_'+roleInfo.role_id+'">'+
								 		'<input type="checkbox" class="role_info"  data='+data+' name="authority_record_oper" style="height:28px;" value="'+roleInfo.role_id+'"/></div>'
						
						html  +='<div class="order_cell white_bgcolor" style="width:99px;">'+roleInfo.role_id+'</div>'				
								+'<div class="order_cell white_bgcolor">'+roleInfo.role_name+'</div>';					  
							    	  
								   
						html +='</div></li>';  
						htmlNew +=html;
					}
					
					
					$("#role_info_list").append(htmlNew);
				}
			}
			
			 }
	});
	
}


//选择左边工号，右边列出该工号对应角色
var pre_oper_id ="";
function selectOperNo(e){
	var div = $(this);
	var dataStr = div.attr("data");
	var data = JSON.parse(dataStr);

	$("input[name='oper_record']:checked").each(function(){
		$(this).attr("checked",false);//取消默认选中的
	});
	
	$(this).prop('checked',true)
	
	var oper_id = data.oper_no;
	
	queryOperRole(oper_id);
	
}

function queryOperRole(oper_id){
	$("input[name='authority_record_oper']:checked").each(function(){
		$(this).attr("checked",false);//取消选中的
	});
	
	var GetURl= application.fullPath + 'authority/authority_configure/queryOperRole';
	$.ajax({
		url:GetURl,
		data:{
			"oper_no":oper_id
			
		},
		dataType:'json',
		type:'post',
		waitMsg:"正在查询工号角色...",
		success:function(message){
			console.info(JSON.stringify(message));
			if(message.args.operRoleList != null){
				if(message.args.operRoleList.length>0){
					var htmlNew ='';
					for(var i=0;i<message.args.operRoleList.length;i++){
						var operRole = message.args.operRoleList[i];
						var role_id = operRole.role_id;
						var same_flag = 0;
						$("input[name='authority_record_oper']").each(function(){
						
							 var roleId = $(this).val();
							 if(role_id == roleId){
								 var check_id = $(this).attr("id")
								 var id = 'infoRoleList_'+roleId;
								 $("#"+id).html('');
								 $("#"+id).html('<input type="checkbox" name="authority_record_oper" value="'+roleId+'" id ="'+check_id+'" checked="checked" style="height:28px;"/>')
								 same_flag = 1;
							 }
						 });
						
						if(same_flag==0){
							var roleInfo = operRole;
							var data = JSON.stringify(roleInfo);
							var effFlag = "";
							if(roleInfo.flag == 0){
								effFlag = "是";
							}else{
								effFlag = "否";
							}
							var html='<li class="list" style="*display:inline;">'+
										'<div class="order_row white_bgcolor" style="width:268px;">'+
									 	'<div class="order_cell gray_bgcolor" style="width:33px;" id="infoRoleList_'+roleInfo.role_id+'">'+
									 		'<input type="checkbox"  checked="checked"  class="role_info"  data='+data+' name="authority_record_oper" style="height:28px;" value="'+roleInfo.role_id+'"/></div>'
							
							html  +='<div class="order_cell white_bgcolor" style="width:99px;">'+roleInfo.role_id+'</div>'				
									+'<div class="order_cell white_bgcolor">'+roleInfo.role_name+'</div>';					  
								    	  
									   
							html +='</div></li>';  
							htmlNew +=html;
						}
						
//						$("input[name='authority_record_oper']:checked").each(function(){
//							alert("有吗     "+$(this).attr("checked")+"    "+$(this).val());
//						});
						
					}
					$("#role_info_list").append(htmlNew);
				}
			}
		}
	});
}

//给工号分配角色(包括新增和删除)
function addOrDelOperRole(){
	var oper_no = "";
	var authority_record = [];
	$("input[name='oper_record']:checked").each(function(){
		oper_no = $(this).val();
		//alert($(this).val());
	});
	
	$("input[name='authority_record_oper']:checked").each(function(){
		authority_record.push($(this).val());
		//alert($(this).val());
	});
	
	authority_record =authority_record.join(",");
	
	if(oper_no==""){
		$.alert("请先选择工号！");
		return;
	}
	
	if(authority_record.length<=0){
		$.alert("请先选择角色！");
		return;
	}
	
	
	var url =application.fullPath +'authority/authority_configure/addOrDleOperRole';
	 
	 $.ajax({
			url:url,
			data:{
				"oper_no":oper_no,
				"roleCodes":authority_record	
			},
			dataType:'json',
			type:'post',
			waitMsg:"分配工号角色...",
			success:function(message){
				console.info(JSON.stringify(message));
				var type =message.args.flag;
				if(type =="true"){
					$.alert("权限分配成功");
				}else{
					$.alert("权限分配失败");
				}
			 }
			});
	
}

//删除工号角色
function delOperRole(){
	var oper_no = "";
	var authority_record = [];
	$("input[name='oper_record']:checked").each(function(){
		oper_no = $(this).val();
		//alert($(this).val());
	});
	
	$("input[name='authority_record_oper']:checked").each(function(){
		authority_record.push($(this).val());
		//alert($(this).val());
	});
	
	authority_record =authority_record.join(",");
	
	if(oper_no==""){
		$.alert("请先选择工号！");
		return;
	}
	
	if(authority_record.length<=0){
		$.alert("请先选择角色！");
		return;
	}
	
	
	var url =application.fullPath +'authority/authority_configure/dleOperRole';
	 
	 $.ajax({
			url:url,
			data:{
				"oper_no":oper_no,
				"roleCodes":authority_record	
			},
			dataType:'json',
			type:'post',
			waitMsg:"删除工号角色...",
			success:function(message){
				console.info(JSON.stringify(message));
				var type =message.args.flag;
				if(type =="true"){
					$.alert("权限删除成功");
				}else{
					$.alert("权限删除失败");
				}
			 }
			});
}
