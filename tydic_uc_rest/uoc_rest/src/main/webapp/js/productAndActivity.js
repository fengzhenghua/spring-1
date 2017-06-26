var imgUrl=imgUrlPath;
var imgUrl2=fullPath+'images/delete_ql.png';//打叉图片的路径
$(document).ready(function () {
	//商品上架点击事件
	$("#commodity_up").unbind().bind("click",function(){
		
		$("#commodity_up").addClass('handle_btn_left_clicked');
		$("#commodity_down").removeClass('handle_btn_right_clicked');
		
		$("#content_up").show();
		$("#goodSelect").show();
		$("#goodOk").hide();
		$("#content_down").hide();

		getProductAndActivity();
	});
	
	//商品下架点击事件
	$("#commodity_down").unbind().bind("click",function(){
		
		$("#commodity_down").addClass('handle_btn_right_clicked');
		$("#commodity_up").removeClass('handle_btn_left_clicked');
		
		$("#content_down").show();
		$("#content_up").hide();
		var terminal_scene=$("input[name=downTer]:checked").val();
		var oper_no=""
		if(terminal_scene=="1"){
			oper_no=$("input[name=downAuto]:checked").val()
		}
		getProductAndActivityDown(0,terminal_scene,"",oper_no);
		
		
	}); 
	
	//商品上架下一步点击事件
	$("#step2_next").unbind().bind("click",function(){
		var act=$("#activity_section  option:selected");
		var ter=$("#terminal_section  option:selected");
		var auto=$("#auto_section  option:selected");
		var pho=$("input[name=phoneList]:checked");//已选手机
		if(act.text()==""||act.val()==""){
			$.alert("~请选择上架的活动~");
			return;
		}
		if(ter.text==""||ter.val==""){
			$.alert("~请选择上架的终端~");
			return;
		}
		if(pho.length=="0"){
			$.alert("~请选择活动配置的手机终端~");
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
			goodOkInit(act,ter,auto,pho);
			
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
		 $("#phoneed li").each(function(j,elemt){
			 var dataStr=$(this).attr("data");
			 phoneList=JSON.parse(dataStr);
			 var terminal_model_code=phoneList.terminal_model;
			 var property_code=phoneList.property_code;
			 var combined_product=phoneList.productId;
			 var terminal_scene=$("#terminaled").attr("name");
			 var scene_type=$("#activitied").attr("name");
			 var oper_no="";
			 if(terminal_scene=="1"){
				 oper_no=$("#autoed").attr("name");
			 }
			 
			 data={
					 terminal_model_code:terminal_model_code,
					 property_code:property_code,
					 terminal_scene:terminal_scene,
					 combined_product:combined_product,
					 scene_type:scene_type,
					 oper_no:oper_no,
					 flag:"0"
			 };
			 array.push(data);
		 });
		 var arrayJson=JSON.stringify(array);
		 var URL = application.fullPath + "authority/productAndActivity/insertTerComRelation";
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
						 getProductAndActivity();
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
	
	getProductAndActivity();
});

//初始化加载页面数据
function getProductAndActivity(){	 
	 var URL = application.fullPath + "authority/productAndActivity/activitySceneType";
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
					var phoneList=message.args.phoneList;
					//加载上架活动数据
					var strActivity='<select id="activity_section" name="activity_section"><option value="">--请选择--</option>';//<option value="">--请选择--</option>
					if(codeTypeList!=null&&codeTypeList!=""){
						for(var i=0;i<codeTypeList.length;i++){
							strActivity+='<option value="'+codeTypeList[i].code_id+'">'+codeTypeList[i].code_name+'</option></select>';
						}
						$("#activity_section").html(strActivity);
					}
					//加载手机终端数据
					if(phoneList.length>0){
						var strPhone='';
						for(var i=0;i<phoneList.length;i++){
							var phoneColorArrt="";
							if(phoneList[i].term_color!=null && phoneList[i].term_color!=""){
								phoneColorArrt=phoneList[i].term_color.split(/[，.。,;|-、]/);//颜色数组
							}
							var phonePropertyCodeArrt=phoneList[i].property_code.split(/[，.。,;|-、]/);//property_code数组
							for(var j=0;j<phonePropertyCodeArrt.length;j++){
								var color=phoneColorArrt.length ? phoneColorArrt[j] : phoneColorArrt;
								var dataUp ={
									term_id:phoneList[i].term_id,
									terminal_model:phoneList[i].terminal_model,
									term_name:phoneList[i].term_name,
									orint_fee:phoneList[i].orint_fee,
									term_fee:phoneList[i].term_fee,
									term_pic:phoneList[i].term_pic,
									term_color: color,//不同颜色
									term_hot:phoneList[i].term_hot,
									property_code:phonePropertyCodeArrt[j]//不同property_code
								};
								var data=JSON.stringify(dataUp);
								if(color==""||color==null){
									strPhone+='<li class="list" style="font-size:15px;color:#cccccc;" id="phone_'+phonePropertyCodeArrt[j]+'"><input type="checkbox" name="phoneList" data=\''+data+'\' value="'+phoneList[i].term_name+"_"+j+'" disabled="disabled" '
									+' id="'+phonePropertyCodeArrt[j]+'">'+phoneList[i].term_name+"_"+j+'</input></li>';
								}else{
									strPhone+='<li class="list" style="font-size:15px;color:#cccccc;" id="phone_'+phonePropertyCodeArrt[j]+'"><input type="checkbox" name="phoneList" data=\''+data+'\' value="'+phoneList[i].term_name+"_"+phoneColorArrt[j]+'" disabled="disabled" '
									+' id="'+phonePropertyCodeArrt[j]+'">'+phoneList[i].term_name+"_"+phoneColorArrt[j]+'</input></li>';
								}
								
							}
						}
						$("#phoneLis_select").html(strPhone);
					}
					
					//自助机上架范围
					var oper_no=message.args.oper_no;
					var strOper='<option value="'+oper_no+'" >本厅自助机</option><option value="*">省份自助机</option>';
					$("#auto_section").html(strOper);
					
					//自助机下架范围
					$("#downAutoLocal").attr("value",oper_no);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 console.info(XMLHttpRequest.status);
				 console.info(XMLHttpRequest.readyState);
				 console.info(textStatus);
			}
			
		});
}

//商品下架页面初始化;查询终端组合业务关系表
function getProductAndActivityDown(first_start,terminal_sceneVal,scene_typeVal,oper_noVal){
	 var data={
			 first_start:first_start,
			 terminal_scene:terminal_sceneVal,
			 scene_type:scene_typeVal,
			 oper_no:oper_noVal,
			 flag:"0"
	 };
	 var URL = application.fullPath + "authority/productAndActivity/queryTerComRelation";
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		type:'post',
		data:data,
		waitMsg:"加载数据中...",
		success:function(message){
			var sceneTypeList=message.args.sceneTypeList;
			var terComRelationList=message.args.terComRelationList;
			var codeTypeList=message.args.codeListSceneType;
			if(message.type == "error"){
				$.alert(message.content);
				$("#phoneedDown").html("");
				$("#phoneImgDownShow").html("");
				
				if(sceneTypeList==null||sceneTypeList==""){
					$("#activityDown").html("");
					$("#downTerm").hide();
					$("#downAuto").hide();
				}
				if(first_start=="0"){
					var strActi="";
					if(codeTypeList!=null&&codeTypeList!=""){
						for(var i=0;i<codeTypeList.length;i++){
							if(first_start=="0"&&i=="0"){
								strActi +='<li class="list" style="font-size:15px;"><input name="downActed" type="radio" value="'+codeTypeList[i].code_id+'" checked="checked" onclick="actTerRadio();">'+codeTypeList[i].code_name+'</input></li>';
							}else{
								strActi +='<li class="list" style="font-size:15px;"><input name="downActed" type="radio" value="'+codeTypeList[i].code_id+'" onclick="actTerRadio();">'+codeTypeList[i].code_name+'</input></li>';
							}
						}
						$("#activityDown").html(strActi);
					}else{
						$("#activityDown").html("");
						$("#downTerm").hide();
						$("#downAuto").hide();
					}
				}
				
			}else{
				//加载已上架活动数据
				var strActivity='';
				if(codeTypeList!=null&&codeTypeList!=""){
					for(var i=0;i<codeTypeList.length;i++){
						if(first_start=="0"&&i=="0"){
								strActivity+='<li class="list" style="font-size:15px;"><input name="downActed" type="radio" value="'+codeTypeList[i].code_id+'" checked="checked" onclick="actTerRadio();">'+codeTypeList[i].code_name+'</input></li>';
						}else{
								strActivity+='<li class="list" style="font-size:15px;"><input name="downActed" type="radio" value="'+codeTypeList[i].code_id+'" onclick="actTerRadio();">'+codeTypeList[i].code_name+'</input></li>';
						}
					}
					$("#activityDown").html(strActivity);
				}
				//加载已上架的手机终端
				var phoneList=message.args.phoneList;
				var listCombinedProduct=message.args.listCombinedProduct;
				if(phoneList!=null&&phoneList!=""){
					var strPho='';
					var phoneImgDownShowStr='';
					for(var i=0;i<phoneList.length;i++){
						var phoneColorArrt="";
						if(phoneList[i].term_color!=null && phoneList[i].term_color!=""){
							phoneColorArrt=phoneList[i].term_color.split(/[，.。,;|-、]/);//颜色数组
						}
						var phonePropertyCodeArrt=phoneList[i].property_code.split(/[，.。,;|-、]/);//property_code数组
						for(var j=0;j<phonePropertyCodeArrt.length;j++){
							if(phonePropertyCodeArrt[j]==terComRelationList[i].property_code){
								var color=phoneColorArrt.length ? phoneColorArrt[j] : phoneColorArrt;
								var dataDown={
										term_id:phoneList[i].term_id,
										terminal_model:phoneList[i].terminal_model,
										term_name:phoneList[i].term_name,
										orint_fee:phoneList[i].orint_fee,
										term_fee:phoneList[i].term_fee,
										term_pic:phoneList[i].term_pic,
										term_color:color,//不同颜色
										term_hot:phoneList[i].term_hot,
										property_code:phonePropertyCodeArrt[j]//不同property_code
									};
								var data=JSON.stringify(dataDown);
								if(color==""){
									strPho+='<li class="list"><input type="checkbox" name="downPhoed" data=\''+data+'\''
										+' id="'+phonePropertyCodeArrt[j]+'" value="'+listCombinedProduct[i].product_id+'" onclick="phoneedSelect(this);">'+phoneList[i].term_name+"_"+j+'</input></li>';
									
									phoneImgDownShowStr+='<td style="width:410px;"><div style="float: left;border-right: 1px dashed #080808;margin:1px 3px 1px 5px;width:410px;height:240px;position: relative;"><div class="img" style="float:left;height:200px;width:100px;">'
										+'<div><img id="img" src="'+imgUrl+phoneList[i].term_pic+'" style="height:180px;width:100px;"/></div><div style="float:left;text-align:center;width:100px;"><span>'+phoneList[i].term_name+"_"+j+'</span></div></div>';//图片信息;element.text机型名称
								}else{
									strPho+='<li class="list"><input type="checkbox" name="downPhoed" data=\''+data+'\''
									+' id="'+phonePropertyCodeArrt[j]+'" value="'+listCombinedProduct[i].product_id+'" onclick="phoneedSelect(this);">'+phoneList[i].term_name+"_"+phoneColorArrt[j]+'</input></li>';
									//加载机型展示
									phoneImgDownShowStr+='<td style="width:410px;"><div style="float: left;border-right: 1px dashed #080808;margin:1px 3px 1px 5px;width:410px;height:240px;position: relative;"><div class="img" style="float:left;height:200px;width:100px;">'
										+'<div><img id="img" src="'+imgUrl+phoneList[i].term_pic+'" style="height:180px;width:100px;"/></div><div style="float:left;text-align:center;width:100px;"><span>'+phoneList[i].term_name+"_"+phoneColorArrt[j]+'</span></div></div>';//图片信息;element.text机型名称
								}
								
								phoneImgDownShowStr+='<div class="content" style="float: left;margin:0 10px 0 30px;"><div style="font-size:14px;color:#080808;font-weight:bold;">活动描述：</div><div class="padding_blank10"></div>';
								phoneImgDownShowStr+='<div id="desc" style="height:90px;width:200px;" name="'+listCombinedProduct[i].product_id+'">'+listCombinedProduct[i].product_desc +'</div>';//活动描述信息
								phoneImgDownShowStr+='<div class="padding_blank10"></div><div style="font-size:14px;color:#080808;font-weight:bold;">添加个性说明：</div><textarea name="personDesc" style="width:200px;height:60px;"></textarea></div><div class="clear"></div>';
								phoneImgDownShowStr+='<img id="coverImg'+phonePropertyCodeArrt[j]+'" src="'+imgUrl2+'" style="z-index: 3; position: absolute; top: 0px;display: none;height:240px;width:400px;"/></div></td>';
							}
						}
					}
					$("#phoneedDown").html(strPho);
					$("#phoneImgDownShow").html(phoneImgDownShowStr);
				}
				//显示终端
				$("#downTerm").show();
				var terminal_sceneVal=$("input[name=downTer]:checked").val();
				if(first_start=="0"&&terminal_sceneVal=="1"){
					$("#app0").attr("checked",false);
					$("#app1").attr("checked",true);
					
				}
				if(terminal_sceneVal=="1"){
					$("#downAuto").show();
				}else{
					$("#downAuto").hide();
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

//上架活动选中事件
function activityChange(){
	var array =[];
	var arrayId=[];
	var obj="";//用于保存下拉框的值
	var dataStrBegin="";//用于保存手机端一开始的data属性的值
	var actSelectedVal=$("#activity_section  option:selected").val();
	var terminalVal=$("#terminal_section  option:selected").val();//已选终端
	var oper_no="";
	if(terminalVal=="1"){
		$("#auto_section").attr("disabled",false);
		oper_no=$("#auto_section option:selected").val();
	}else{
		$("#auto_section").attr("disabled",true);
	}
	obj=$("input[name=phoneList]:checkbox");
	if(actSelectedVal!=""&&actSelectedVal!=null){
		obj.each(function(j,element){
			var dataStr=$(this).attr("data");
			var phoneList=JSON.parse(dataStr);
			var phoneTerminal=phoneList.terminal_model;
			var id=element.id;
			arrayId.push(id);
			var data={
						activity_flag:actSelectedVal,
						terminal_model_code:phoneTerminal,
						property_code:id//property_codeVal
						
				};
				array.push(data);
		});
		 var arrayJson=JSON.stringify(array);
		$.ajax({
			url:application.fullPath + "authority/productAndActivity/activitySelected",
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:{
				dataArrayJson:arrayJson,
				terminal_scene:terminalVal,
				oper_no:oper_no,
				scene_type:actSelectedVal
				},
			waitMsg:"加载数据中...",
			success:function(message){
				if(message.type == "error"){
					$.alert(message.content);
//					getProductAndActivity();
					obj.each(function(j,element){
						var id=element.id;
						$("#"+id).attr("disabled",true);
						$("#"+id).prop("checked",false);
						$("#phone_"+id).css("color","#cccccc");
					});
				}else{
					var terminalModelList=message.args.terminalModelList;
					for(var j=0;j<arrayId.length;j++){
						var dataStrBegin=$("#"+arrayId[j]).attr("data");
						for(var i=0;i<terminalModelList.length;i++){
							if(arrayId[j]== terminalModelList[i].property_code 
									|| arrayId[j]== terminalModelList[i].terminal_model_code){
								
								var phoneListResult=JSON.parse(dataStrBegin);
								var product_desc=html_encode(terminalModelList[i].product_desc);
								phoneListResult.productId=terminalModelList[i].product_id;//组合编码combined_product 
								phoneListResult.productInfo=product_desc;//组合的具体信息，即活动描述
								var dataStrEnd=JSON.stringify(phoneListResult);
								
								$("#"+arrayId[j]).attr("disabled",false);
								$("#"+arrayId[j]).prop("checked",true);
								$("#"+arrayId[j]).attr("data",dataStrEnd);
								$("#phone_"+arrayId[j]).css("color","#080808");
								break;
							}else{
								//去掉样式
								$("#"+arrayId[j]).attr("disabled",true);
								$("#"+arrayId[j]).prop("checked",false);
								$("#"+arrayId[j]).attr("data",dataStrBegin);
								$("#phone_"+arrayId[j]).css("color","#cccccc");
							}
						}
					}
					document.getElementById("step2_next").className="ok";
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 console.info(XMLHttpRequest.status);
				 console.info(XMLHttpRequest.readyState);
				 console.info(textStatus);
			}
		});	
	}else{
		document.getElementById("step2_next").className="ok_disabled";
		obj.each(function(j,element){
			var id=element.id;
			$("#"+id).attr("disabled",true);
			$("#"+id).prop("checked",false);
			$("#phone_"+id).css("color","#cccccc");
		});
	}
};

//选择上架的终端 
//function terminalChange(){
//	var terminalVal=$("#terminal_section  option:selected").val();//已选终端
//	if(terminalVal=='1'){
//		$("#auto_section").attr("disabled",false);
//	}else{
//		$("#auto_section").attr("disabled",true);
//	}
//};

//初始化信息确认页面
function goodOkInit(act,ter,auto,pho){
	$("#activitied").val(act.text());
	$("#activitied").attr("name",act.val());//组合场景 scene_type 
	
	$("#terminaled").val(ter.text());
	$("#terminaled").attr("name",ter.val());// 终端使用场景0:app\pc端，1:自助机  terminal_scene 
	if(ter.val()=="1"){
		$("#autoRang").show();
		$("#autoed").val(auto.text());
		$("#autoed").attr("name",auto.val());//自助机适用范围 oper_no:本厅自助机; *:省份自助机
	}else{
		$("#autoRang").hide();
	}
	//手机信息
	var phoStr='';
	var phoneImgShowStr='';//机型展示
	pho.each(function(j,element){
		var dataStr=$(this).attr("data");
		var phoneList=JSON.parse(dataStr);
		var productInfo=html_decode(phoneList.productInfo);
		
		var id=element.id;
		var name=element.value;
		phoStr+='<li id="'+element.id+'" data=\''+dataStr+'\' value="">'+(j+1)+'.'+name+'</li>';
		phoneImgShowStr+='<td style="width:380px;"><div style="float:left;border-right: 1px dashed #080808;margin:5px 5px 5px 10px;width:380px;height:240px;"><div class="img" style="float:left;height:200px;width:100px;">'
			+'<div><img id="img" src="'+imgUrl+phoneList.term_pic+'" style="height:180px;width:100px;"/></div><div style="float:left;text-align:center;width:100px;"><span>'+name+'</span></div></div>';//图片信息;element.text机型名称
		phoneImgShowStr+='<div class="content" style="float: left;margin:0 10px 0 30px;"><div style="font-size:14px;color:#080808;font-weight:bold;">活动描述：</div><div class="padding_blank10"></div>';
		phoneImgShowStr+='<div id="desc" style="height:90px;width:200px;">'+productInfo +'</div>';//活动描述信息
		phoneImgShowStr+='<div class="padding_blank10"></div><div style="font-size:14px;color:#080808;font-weight:bold;">添加个性说明：</div><textarea name="personDesc" style="width:200px;height:60px;"></textarea></div><div class="clear" /></div></div></td>';
		
	}); 
	$("#phoneed").html(phoStr);
	$("#phoneImgShow").html(phoneImgShowStr);

};

//商品下架完成处理事件,即更新flag
function downDeal(){
	var scene_type=$("input[name=downActed]:checked").val();
	var terminal_scene=$("input[name=downTer]:checked").val();
	var oper_no="";
	if(terminal_scene=="1"){
		oper_no=$("input[name=downAuto]:checked").val();
	}
//	var obj=$("input[name=downPhoed]:checkbox");
	var obj=$("input[name=downPhoed]:checked");
	var array=[];
	obj.each(function(j,elemt){
		var dataStr=$(this).attr("data");
		var phoneList=JSON.parse(dataStr);
		var terminal_model=phoneList.terminal_model;
		var property_code=phoneList.property_code;
//		console.info(obj[j].checked);
//		if(!obj[j].checked){
//			return true;
//		}else{}
			var data={
					flag:"1",
					terminal_model_code:terminal_model,
					terminal_scene:terminal_scene,
					scene_type:scene_type,
					property_code:property_code,
					oper_no:oper_no
			};
			array.push(data);
	});
	var arrayJson=JSON.stringify(array);
	var URL=application.fullPath + "authority/productAndActivity/updateTerComRelation";
	$.ajax({
		url:URL,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		type:'post',
		data:{dataArrayJson:arrayJson},
		waitMsg:"加载数据中...",
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
					
					getProductAndActivityDown(1,terminal_scene,scene_type,oper_no);
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
	var terminal_sceneVal=$("input[name=downTer]:checked").val();
	var oper_noVal=$("input[name=downAuto]:checked").val();
	if(terminal_sceneVal=="1"){
		$("#downAuto").show();
	}else{
		$("#downAuto").hide();
		oper_noVal="";
	}
	getProductAndActivityDown(1,terminal_sceneVal,scene_typeVal,oper_noVal);
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