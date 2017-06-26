var imgUrl=imgUrlPath;
var imgUrl2=fullPath+'images/delete_ql.png';//打叉图片的路径
var data_deptSelect="";
$(document).ready(function () {
	//商品上架点击事件
	$("#commodity_up").unbind().bind("click",function(){
		
		$("#commodity_up").addClass('handle_btn_left_clicked');
		$("#commodity_down").removeClass('handle_btn_right_clicked');
		
		$("#content_up").show();
		$("#goodSelect").show();
		$("#goodOk").hide();
		$("#content_down").hide();
		getProducts();//查询上架产品列表

	});
	
	//商品下架点击事件
	$("#commodity_down").unbind().bind("click",function(){
		
		$("#commodity_down").addClass('handle_btn_right_clicked');
		$("#commodity_up").removeClass('handle_btn_left_clicked');
		
		$("#content_down").show();
		$("#content_up").hide();		
		typeChangeDown();
		
		
	}); 
	
	//点击上架下一步事件
	$("#commit_up").unbind().bind("click",function(){
		var scene_type=$("#scene_type  option:selected");//场景类型
		var type_section=$("#type_section  option:selected");//类型
		var productList=$("input[name=productList]:checked");//已选手机
		if(scene_type.text()==""||scene_type.val()==""){
			$.alert("~请选择上架的场景~");
			return;
		}
		if(type_section.text==""||type_section.val==""){
			$.alert("~请选择类型~");
			return;
		}
		if(productList.length=="0"){
			$.alert("~请选择上架产品~");
			return;
		}
		
		document.getElementById("bg_mask").style.display ="block";
		document.getElementById("next1").style.display ="block";
		//跳转到第二步页面，商品确认
		$("#next1_ok").unbind().bind("click",function(){
			document.getElementById("bg_mask").style.display ="none";
			document.getElementById("next1").style.display ="none";
			document.getElementById("goodSelect").style.display ="none";
			document.getElementById("goodOk").style.display ="block";
			//初始化信息确认页面
			goodOkInit(type_section,scene_type,productList);
			
		});
	});
	//取消事件
	$("#next1_cancel").unbind().bind("click",function(){
		document.getElementById("bg_mask").style.display ="none";
		document.getElementById("next1").style.display ="none";
	});
	//商品上架完成事件
	$("#step2").unbind().bind("click",function(){
		 var array =[];
		 var phoneList="";
		 var SelectedVal=$("#type_section  option:selected").val();
		 var scene_type=$("#scene_type  option:selected").val();
		 var combined_product_name=$("#scene_type  option:selected").text();
		 $("#producted li").each(function(j,elemt){
			 var dataStr=$(this).attr("data");
			 phoneList=JSON.parse(dataStr);			
			 var combined_product=phoneList.combined_product;			 
			 data={
				"combined_product":combined_product,
				"scene_type":scene_type,	
				"selected_type":SelectedVal,
				"combined_product_name":combined_product_name,
				"dept_no":data_deptSelect.dept_no
			 };
			 array.push(data);
		 });
		 var arrayJson=JSON.stringify(array);
		 var URL = application.fullPath + "authority/productPage/insertTerCodeCombinedProduct";
		 $.ajax({
		 	url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:{dataArrayJson:arrayJson},
			waitMsg:"正在执行中...",
			success:function(message){
				if(message.type == "error"){
					$.alert(message.content);
				}else{
					$("#bg_mask").show();
					 $("#next2").show();
					 $("#up_ok").unbind().bind("click",function(){
						 $("#next2").hide();
						 $("#bg_mask").hide();
						 $("#goodOk").hide();
						 $("#goodSelect").show();
						 getProducts();//查询上架产品列表
					 });
					
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 console.info(XMLHttpRequest.status);
				 console.info(XMLHttpRequest.readyState);
				 console.info(textStatus);
			}
		 });
	});
	

	typeChange();//查询场景列表
	getProducts();//查询上架产品列表
	getDeptListTree('');//查询部门树形列表
	$("#searchdept_search").bind("click",function(){
		var input_text = $("#dept_searchInput").val();		
		getDeptList(input_text);
	
	});
});

//查询场景
function getScenetype(){	 
	 var URL = application.fullPath + "authority/productPage/queryPageScenetype";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			waitMsg:"加载数据中...",
			success:function(message){
				if(message.type == "error"){
					$.alert(message.content);
				}else{
					var codeTypeList=message.args.codeListSceneType;
					//加载上架活动数据
					$("#scene_type").html("");
					$("#scene_type").append('<option value="">--请选择--</option>');
					if(codeTypeList!=null&&codeTypeList!=""){
						
						for(var i=0;i<codeTypeList.length;i++){
							$("#scene_type").append('<option value="'+codeTypeList[i].code_id+'">'+codeTypeList[i].code_name+'</option></select>');
						}
					
					}
								
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 console.info(XMLHttpRequest.status);
				 console.info(XMLHttpRequest.readyState);
				 console.info(textStatus);
			}
			
		});
}

//查询上架产品列表
function getProducts(){	 
	 var URL = application.fullPath + "authority/productPage/queryPageProduct";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			waitMsg:"加载数据中...",
			success:function(message){
				if(message.type == "error"){
					$.alert(message.content);
				}else{
					var productList=message.args.productList;
					var strPhone="";
					for(var i=0;i<productList.length;i++){
						var data=JSON.stringify(productList[i]);
						
						strPhone+='<li class="list" style="font-size:15px;color:#cccccc;" title="'+productList[i].memo+'"><input type="checkbox" name="productList" data=\''+data+'\' value="'+productList[i].combined_product+'"'
						+' id="'+productList[i].combined_product+'">'+productList[i].combined_product_name+'</input></li>';
				
					}
			         $("#phoneLis_select").html(strPhone);
									
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 console.info(XMLHttpRequest.status);
				 console.info(XMLHttpRequest.readyState);
				 console.info(textStatus);
			}
			
		});
}


//查询下架场景列表
function getScenetypeDown(){	
	 var URL = application.fullPath + "authority/productPage/queryPageScenetype";
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		type:'post',
		waitMsg:"加载数据中...",
		success:function(message){
			
			if(message.type == "error"){
				$.alert(message.content);
				$("#phoneedDown").html("");
				$("#phoneImgDownShow").html("");
				
			}else{
				var codeTypeList=message.args.codeListSceneType;
				var strActivity='';
				if(codeTypeList!=null&&codeTypeList!=""){
					for(var i=0;i<codeTypeList.length;i++){
						if(i=="0"){
								strActivity+='<li class="list" style="font-size:15px;"><input name="downActed" type="radio" value="'+codeTypeList[i].code_id+'" checked="checked" onclick="actTerRadio();">'+codeTypeList[i].code_name+'</input></li>';
						}else{
								strActivity+='<li class="list" style="font-size:15px;"><input name="downActed" type="radio" value="'+codeTypeList[i].code_id+'" onclick="actTerRadio();">'+codeTypeList[i].code_name+'</input></li>';
						}
					}
					$("#scenetypeDown").html(strActivity);
				}
				 var scene_type=$("input[name=downActed]:checked").val();
				queryCodeCombinedProduct(scene_type);//查询已上线列表数据
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			 console.info(XMLHttpRequest.status);
			 console.info(XMLHttpRequest.readyState);
			 console.info(textStatus);
		}
	});
}
//查询组合表在线产品列表
function queryCodeCombinedProduct(scene_type){
	// var scene_type=$("input[name=downActed]:checked").val();
	 var URL = application.fullPath + "authority/productPage/queryCodeCombinedProduct";
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		type:'post',
		data:{
			"scene_type":scene_type			
		},
		waitMsg:"加载数据中...",
		success:function(message){
			$("#phoneedDown").html("");
			$("#phoneImgDownShow").html("");
			if(message.type == "error"){
				$.alert(message.content);
				$("#phoneedDown").html("");
				$("#phoneImgDownShow").html("");
				
			}else{				
				//加载已上架的产品
				var listCombinedProduct=message.args.productList;
				if(listCombinedProduct!=null&&listCombinedProduct!=""){
					var strPho='';
					var phoneImgDownShowStr='';
					for(var i=0;i<listCombinedProduct.length;i++){
						if(listCombinedProduct[i].phone_image_path==null||listCombinedProduct[i].phone_image_path==''){
							img_name="images/product_defunt.jpg";//设置默认图片
						}else{
							img_name="uploadImg/"+listCombinedProduct[i].phone_image_path;
						}
							var data=JSON.stringify(listCombinedProduct[i]);
								
									strPho+='<li class="list" title="'+listCombinedProduct[i].memo+'"><input type="checkbox" name="downPhoed" data=\''+data+'\''
									+' id="'+listCombinedProduct[i].combined_product+'" value="'+listCombinedProduct[i].combined_product+'" onclick="phoneedSelect(this);" >'+listCombinedProduct[i].combined_product_name+'</input></li>';
									//加载机型展示
								phoneImgDownShowStr+='<td style="width:410px;"><div style="float: left;border-right: 1px dashed #080808;margin:1px 3px 1px 5px;width:410px;height:240px;position: relative;"><div class="img" style="float:left;height:200px;width:100px;">'
									+'<div><img id="img" src="'+fullPath+img_name+'" style="height:180px;width:100px;"/></div><div style="float:left;text-align:center;width:100px;"><span>'+listCombinedProduct[i].combined_product_name+'</span></div></div>';//图片信息;element.text机型名称
						
								phoneImgDownShowStr+='<div class="content" style="float: left;margin:0 10px 0 30px;"><div style="font-size:14px;color:#080808;font-weight:bold;">产品描述：</div><div class="padding_blank10"></div>';
								phoneImgDownShowStr+='<div id="desc" style="height:90px;width:200px;" name="'+listCombinedProduct[i].combined_product+'">'+listCombinedProduct[i].memo +'</div>';//产品描述信息
								phoneImgDownShowStr+='<div class="padding_blank10"></div></div><div class="clear"></div>';
								phoneImgDownShowStr+='<img id="coverImg'+listCombinedProduct[i].combined_product+'" src="'+imgUrl2+'" style="z-index: 3; position: absolute; top: 0px;display: none;height:240px;width:400px;"/></div></td>';
					
					}
					$("#phoneedDown").html(strPho);
					$("#phoneImgDownShow").html(phoneImgDownShowStr);
				}
		
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			 console.info(XMLHttpRequest.status);
			 console.info(XMLHttpRequest.readyState);
			 console.info(textStatus);
		}
	});
}

//上架类型中事件
function typeChange(){
	
	var SelectedVal=$("#type_section  option:selected").val();
	
	if(SelectedVal=="0"){//快速业务办理
		getScenetype();//加载快速办理场景
	}else if(SelectedVal=="1"){//热销
		getProductHot();//加载热销场景
	}
	
};
function getProductHot(){
	  //加载热销场景
	   $("#scene_type").html('');
	   $("#scene_type").append('<option value="">--请选择--</option>');
		for(var i=1;i<11;i++){
			$("#scene_type").append('<option value="h'+i+'">热销TOP'+i+'</option>');
		}
		
}

//下架热销
function getProductHotDown(){
	var strscenetype="";
	//加载热销场景
	for(var i=1;i<11;i++){
		if(i=="1"){
			strscenetype+='<li class="list" style="font-size:15px;"><input name="downActed" type="radio" value="h'+i+'" checked="checked" onclick="actTerRadio();">热销TOP'+i+'</input></li>';
		}else{
			strscenetype+='<li class="list" style="font-size:15px;"><input name="downActed" type="radio" value="h'+i+'" onclick="actTerRadio();">热销TOP'+i+'</input></li>';
		}
	}
	$("#scenetypeDown").html(strscenetype);
	 var scene_type=$("input[name=downActed]:checked").val();
	queryCodeCombinedProduct(scene_type);//查询已上线列表数据
	
}


//下架类型中事件
function typeChangeDown(){
	
	var SelectedVal=$("#down_type_section  option:selected").val();
	
	if(SelectedVal=="0"){//快速业务办理
		getScenetypeDown();//加载快速办理场景
	}else if(SelectedVal=="1"){//热销
		getProductHotDown();//加载热销场景
	}
	
};


//初始化信息确认页面
function goodOkInit(type_section,scene_type,productList){
	$("#type_ed").val(type_section.text());
	$("#type_ed").attr("name",type_section.val());//类型
	
	$("#scene_type_ed").val(scene_type.text());
	$("#scene_type_ed").attr("name",scene_type.val());// 场景
	$("#depted").html($("#dept_select").val());// 部门
	$("#depted").attr("title",$("#dept_select").val());// 部门
	
	//产品信息
	var phoStr='';
	var phoneImgShowStr='';//产品展示
	productList.each(function(j,element){
		var dataStr=$(this).attr("data");
		var phoneList=JSON.parse(dataStr);
		if(phoneList.phone_image_path==null||phoneList.phone_image_path==''){
			img_name="images/product_defunt.jpg";//设置默认图片
		}else{
			img_name="uploadImg/"+phoneList.phone_image_path;
		}
		
		var productInfo=html_decode(phoneList.memo);
		
		var id=element.id;
		var name=phoneList.combined_product_name;
		phoStr+='<li id="'+element.id+'" data=\''+dataStr+'\' value="">'+(j+1)+'.'+name+'</li>';
		phoneImgShowStr+='<td style="width:380px;"><div style="float:left;border-right: 1px dashed #080808;margin:5px 5px 5px 10px;width:380px;height:240px;"><div class="img" style="float:left;height:200px;width:100px;">'
			+'<div><img id="img" src="'+fullPath+img_name+'" style="height:180px;width:100px;"/></div><div style="float:left;text-align:center;width:100px;"><span></span></div></div>';//图片信息;element.text机型名称
		phoneImgShowStr+='<div class="content" style="float: left;margin:0 10px 0 30px;"><div style="font-size:14px;color:#080808;font-weight:bold;">产品描述：</div><div class="padding_blank10"></div>';
		phoneImgShowStr+='<div id="desc" style="height:90px;width:200px;">'+productInfo +'</div>';//活动描述信息
		phoneImgShowStr+='<div class="padding_blank10"></div></div><div class="clear" /></div></div></td>';
		
	}); 
	$("#producted").html(phoStr);
	$("#phoneImgShow").html(phoneImgShowStr);

};

//商品下架完成处理事件,即更新flag
function downDeal(){
	var scene_type=$("input[name=downActed]:checked").val();
	var obj=$("input[name=downPhoed]:checked");
	if(obj.length<=0){
		$.alert("请选择下架产品!");
		return;
	}
	var array=[];
	obj.each(function(j,elemt){
		var dataStr=$(this).attr("data");
		var phoneList=JSON.parse(dataStr);
		var combined_product=phoneList.combined_product;	
			var data={					
					"combined_product":combined_product
			};
			array.push(data);
	});
	var arrayJson=JSON.stringify(array);
	var URL=application.fullPath + "authority/productPage/updateCodeCombinedProduct";
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		type:'post',
		data:{dataArrayJson:arrayJson},
		waitMsg:"执行中...",
		success:function(message){
			if(message.type == "error"){
				$.alert(message.content);
			}else{
				$("#bg_mask").show();
				$("#next_down").show();
				$("#down_ok").unbind().bind("click",function(){
					$("#next_down").hide();
					$("#bg_mask").hide();
					$("#content_down").show();
					queryCodeCombinedProduct(scene_type);//查询已上线列表数据
				});
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			 console.info(XMLHttpRequest.status);
			 console.info(XMLHttpRequest.readyState);
			 console.info(textStatus);
		}
	});
};

//改变多选框选中事件，是否显示打叉图片
function phoneedSelect(obj){
	var id=obj.id;
	if(obj.checked){
		$("#coverImg"+id).show();
	}else{
		$("#coverImg"+id).hide();
	}
	
}

//下架活动-终端单选按钮事件
function actTerRadio(){
	var scene_typeVal=$("input[name=downActed]:checked").val();	
	queryCodeCombinedProduct(scene_typeVal);//查询已上线列表数据
}

//转义'<>'
function html_encode(str){
	var s = "";   
	if (str.length == 0)
		return "";   
	s = str.replace(/</g, "&lt;");
	s = s.replace(/>/g, "&gt;");   
	return s;   
};
//反转义
function html_decode(str)   
{   
  var s = "";   
  if (str.length == 0) 
	  return "";   
  s = str.replace(/&lt;/g, "<");   
  s = s.replace(/&gt;/g, ">");   
  return s;   
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
//查询部门列表
function getDeptList(inputText){

	var data1 = {					
			"dept_name":inputText
			
		};
	
	 var URL = application.fullPath + "authority/productPage/queryDeptList";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:data1,
			waitMsg:"部门信息查询中..",
			success:function(message){		
				$("#dept_list").html('');
				if(message.type == "error"){					
					$.alert(message.content);
				}else{	
					
					showdiv('dept_search');
					$("#dept_list").append('<li class="list" style="*display:inline;"><div class="order_row"><div class="order_cell" style="width:150px;">部门名称</div><div class="order_cell" style="width:200px;">部门编码</div><div class="order_cell" style="width:50px;"></div></div></li>');
					$.each(message.args.deptList, function(i, item) {
						var data = JSON.stringify(item);
						
						$("#dept_list").append('<li class="list" style="*display:inline;"> <div class="order_row white_bgcolor">'
								+'<div class="order_cell white_bgcolor" style="width:150px;" title="'+ item.dept_name +'">'+item.dept_name+'</div> '
								+'<div class="order_cell white_bgcolor" style="width:200px;" >'+ item.dept_no +'</div> '															
								+' <div  class="order_cell white_bgcolor" style="width:50px;"><input  onClick="deptSelect(this)" data=\''+data+'\'  type="radio"></input></div> '								
								+'</div></li>');
			
					
					});
					//解决最后一行资费提示文字叠加到浮选框头部，预留空白位置
					$("#dept_list").append('<div class="list"></div>');
					$("#dept_list").append('<div class="list"></div>');
					$("#dept_list").append('<div class="list"></div>');
				}				
			}
			
		});	
	
	
}
//查询部门树形列表
function getDeptListTree(dept_no){
	$.ajax({
		url: application.fullPath + 'authority/productPage/getDeptInfoList',
		dataType: 'json',
		contentType: 'application/x-www-form-urlencoded; charset=utf-8',
		type: 'post',
		data: {
			'dept_no': dept_no
		},
		success: function(message) {
			var curNodes = toNodeJsonData(message.args.infoDeptLevel);
			initZTree(curNodes);
		},
		error: function(message){
			$.alert('获取部门列表失败！');
		}
	});
	
}

/* ---------- zTree start ---------- */
var zTreeObj; // 树对象
var setting = {}; // 树参数配置
// 初始化树
function initZTree(zNodes) {
	setting = {
		async: {
			contentType: 'application/x-www-form-urlencoded; charset=utf-8',
			enable: true,
			type: 'post',
			url: application.fullPath + 'authority/productPage/getDeptInfoList',
			autoParam: ['dept_no=dept_no'], // 异步加载时需要自动提交当前节点属性的参数
			dataFilter: ajaxDataFilter
		},
		callback: {
			onAsyncSuccess: zTreeOnAsyncSuccess,
			onAsyncError: zTreeOnAsyncError,
			onClick: zTreeOnClick
		}
	};
	zTreeObj = $.fn.zTree.init($("#ztree_dept_div"), setting, zNodes);
}

// 处理树节点参数和格式
function toNodeJsonData(data) {
	var curNodes = [];		
	if (data.length > 0) {
		$.each(data, function(i, item) {
			var curNode = {
				'id': 'deptno_' + item.dept_no,
				'name': item.dept_name,
				'dept_no': item.dept_no,
				'dept_name': item.dept_name,
				'dept_type': item.dept_type,
				'isParent': item.has_sub_dept > 0 ? true : false,
				'icon': application.fullPath + 'js/zTree/css/zTreeStyle/img/diy/dept_icon.png'
			};
			curNodes.push(curNode);
		});
	}
	
	return curNodes;
}

// 预处理Ajax返回的数据
function ajaxDataFilter(treeId, parentNode, responseData) {
	if (!responseData.args.infoDeptLevel) {
		return null;
	}
	return toNodeJsonData(responseData.args.infoDeptLevel);
}

// 用于捕获异步加载正常结束的事件回调函数
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
//	$.alert(msg);
}

// 用于捕获异步加载出现异常错误的事件回调函数
function zTreeOnAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
	$.alert('获取部门列表失败！');
}

//用于捕获节点被点击的事件回调函数
function zTreeOnClick(event, treeId, treeNode) {
	// 部门信息
	data_deptSelect = treeNode;
	$("#dept_select").val(data_deptSelect.dept_name);
	$("#dept_select").attr("title",data_deptSelect.dept_name)
	hidediv('dept_Tree');	
	
};


function deptSelect(e){
	var div = $(e);
	var dataStr = div.attr("data");	
	data_deptSelect = JSON.parse(dataStr);	
	
}
function deptConfirm(){
	if(data_deptSelect!=null&&data_deptSelect!=''){
	   $("#dept_select").val(data_deptSelect.dept_name);		  
		
	}
	hidediv('dept_search');	
	
}
//返回
function deptBack(hid_tag){
	hidediv(hid_tag);	
}
