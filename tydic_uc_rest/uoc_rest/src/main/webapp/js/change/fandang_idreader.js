function verifyForm(){
	var flag = true;

	var errorMessage = "";
	var old_id_type=CUSTOMER_INFO_SEARCH.returndata[0].id_type;	
	var phoneRegex =  /^(13[0-9]|15[012356789]|18[02356789]|17[02356789]|14[57]|17[0])[0-9]{8}$/;//手机号验证	
	var phoneRegex2 = /^(0[0-9]{2,3})?([0-9]{7,8})$/;
	//var phoneRegex = /^\d{11}$/;//手机号验证
	
	var adressRegex = /[\u4E00-\u9FA5]/g; //判断中文	
	
	if(!PC_IDREADER.pc_idreader_info.pCardNo){
		errorMessage = "请选择证件号码!";		
		flag = false;
		var dialog=$.dialog({
			   title:'提示',//提示title
			   content:errorMessage,
			   buttons:[{id:'btn_ok',text:'确定',
				   onClick:function(){	
					   dialog.close();					   
				   }
			   }]
			});
		return flag;
	}
	//
	var zhengjianleixings = new Array("身份证","护照","港澳证","台湾通行证","其他");
	
	/*a、自有厅工号(部门权限限制crm_pub.rule_spec_role表新增配置，表中配置有的才可操作)，支持：护照-->护照，港澳台通行证-->港澳台通行证 
	b、自有厅工号，支持：身份证-->身份证，其他-->身份证 
	c、代理商工号，只支持:其他-->身份证。其他类型的限制不能办理。 */

	if (old_id_type=="身份证"){
		if(PC_IDREADER.pc_idreader_info.pCardNo != CUSTOMER_INFO_SEARCH.returndata[0].id_number){
			flag = false;
			errorMessage="用户证件号码与选择证件号码不一致~";
			var dialog=$.dialog({
				   title:'提示',//提示title
				   content:errorMessage,
				   buttons:[{id:'btn_ok',text:'确定',
					   onClick:function(){	
						   dialog.close();					   
					   }
				   }]
				});			
			return flag;		
		}
		
	}else{
		var zhengjianleixingmatchflag=false;
		for (var i=0;i<zhengjianleixings.length;i++){
			
			if(old_id_type==zhengjianleixings[i]){
				zhengjianleixingmatchflag=true;
				break;
			}
		}
		if(!zhengjianleixingmatchflag){
			errorMessage="用户原证件类型["+old_id_type+"],不能进行返档操作！";
			var dialog=$.dialog({
				   title:'提示',//提示title
				   content:errorMessage,
				   buttons:[{id:'btn_ok',text:'确定',
					   onClick:function(){	
						   dialog.close();					   
					   }
				   }]
				});
			return false;
		}
		var ruleSpecRoleofzhengjian=getRuleSpecRole({"jsessionid":window.parent.application.jsessionid+"PC","spec_role":"fd1","old_id_type":old_id_type},gztValid);
		return ruleSpecRoleofzhengjian;
	}
	//国政通校验
	flag=gztValid();
	return flag;
};
//国政通校验
function gztValid(gztValidParameter){
	if (!gztValidParameter){
		gztValidParameter={"jsessionid":window.parent.application.jsessionid+"PC","certId":PC_IDREADER.pc_idreader_info.pCardNo,"certName":PC_IDREADER.pc_idreader_info.pName};
	}
	var flag = false;
	if(!PC_IDREADER.pc_idreader_info.pCardNo){
		errorMessage = "请选择证件号码!";		
		var dialog=$.dialog({
			   title:'提示',//提示title
			   content:errorMessage,
			   buttons:[{id:'btn_ok',text:'确定',
				   onClick:function(){	
					   dialog.close();					   
				   }
			   }]
			});
		return flag;
	}
	/*只有身份证才走国证通校验
	身份证回调
	if(baseInfo.id_type.code_id != "02"){
		return true;
	}*/
	
	var registClient = {
			onComplete: function(message) {
				if(message.type=="success"){
					flag=true;
					console.info("国政通校验通过！");					
	  			}else{
	  				$.alert(message.content);
	  				flag=false;
	  				return false;
	  			}
			},
			onError: function(XMLHttpRequest, textStatus,errorThrown) {
				var status = XMLHttpRequest.status;//未显示
				var hint = "网络繁忙，请稍后再试!";//通用万金油提示，都懂得。         
				var dialog=$.dialog({
					   title:'提示',//提示title
					   content:'国政通校验:'+hint,//显示的内容，支持html格式。
					   buttons:[{id:'btn_ok',text:'确定',
						   onClick:function(){					   
							   dialog.close();
						   }//点击时候回调函数
					   }]
				});
				flag=false;
				return false;
			}
		};
    var postData={
    		"jsessionid":gztValidParameter.jsessionid,
       		//订单id
    		"certId":gztValidParameter.certId,
    		"certName":gztValidParameter.certName};
	$.ajax({
  		type:"POST",
  		url:application.fullPath + 'rest/customerVerify/gztValidCustomerInfo',
  		data:postData,  
  		//默认同步
  		async:false,
  		waitMsg:"请稍等......",
  		success:registClient.onComplete,				  		
  	    error:registClient.onError				  		
	});
	return flag;
}
//工号权限校验
function getRuleSpecRole(ruleSpecRoleParameter,callback){
	var flag = false;
	if (isNullOrEmpty(ruleSpecRoleParameter.spec_role)){
		$.alert("无法获取操作员返档权限！");
		return flag;
	}	
	var registClient = {
			onComplete: function(message) {
				if(message.type=="success"){
					
					if (isNullOrEmpty(message.args.ruleSpecRoles)){
						console.info("获取权限成功:无操作员返档权限！");
						flag=true;
						if((ruleSpecRoleParameter.old_id_type=="其他")||(ruleSpecRoleParameter.old_id_type=="身份证")){
							console.info("获取权限成功:old_id_type="+ruleSpecRoleParameter.old_id_type);
						}else{
							errorMessage="用户原证件类型["+ruleSpecRoleParameter.old_id_type+"],代理商无返档操作权限，请到自营厅办理！";
							var dialog=$.dialog({
								   title:'提示',//提示title
								   content:errorMessage,
								   buttons:[{id:'btn_ok',text:'确定',
									   onClick:function(){	
										   dialog.close();					   
									   }
								   }]
								});
							flag=false;
							return flag;
						}
					}else{
						console.info("操作员返档权限dept_no:"+message.args.ruleSpecRoles[0].dept_no);	
						console.info("操作员返档权限 spec_role:"+message.args.ruleSpecRoles[0].spec_role);	
						flag=true;
						
					}
					if(callback ){
						console.info("获取权限成功:callback");
						flag=callback();
						return flag;
					}else{
						return flag;
					}
	  			}else{
	  				console.info("获取权限失败！");
	  				return false;
	  			}
				
			},
			onError: function(XMLHttpRequest, textStatus,errorThrown) {
				var status = XMLHttpRequest.status;//未显示
				var hint = "网络繁忙，请稍后再试!";//通用万金油提示，都懂得。         
				var dialog=$.dialog({
					   title:'提示',//提示title
					   content:'返档权限:'+hint,//显示的内容，支持html格式。
					   buttons:[{id:'btn_ok',text:'确定',
						   onClick:function(){					   
							   dialog.close();
						   }//点击时候回调函数
					   }]
				});
			}
		};
    var postData={"ruleSpecRoleJsonPara":JSON.stringify({
    		"jsessionid":ruleSpecRoleParameter.jsessionid,
       		
    		"spec_role":ruleSpecRoleParameter.spec_role})};
	$.ajax({
  		type:"POST",
  		url:application.fullPath + 'rest/ruleSpecRole/getRuleSpecRole',
  		data:postData,  
  		//默认同步
  		async:false,
  		waitMsg:"请稍等......",
  		success:registClient.onComplete,				  		
  	    error:registClient.onError				  		
	});
	return flag;
};
function isNullOrEmpty(strVal) { 
 if (strVal == '' || strVal == null || strVal == undefined) { 
 return true; 
 } else { 
 return false; 
 } 
}