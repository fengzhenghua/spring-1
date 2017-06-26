
function jifenverifyFormToqueren(){
	if(window.parent.window.parent.isNullOrEmpty(ruleInfoScore.selecteditem)){
		$.alert("先选择积分兑换项目！");
		return false;
	}
	if(window.parent.window.parent.isNullOrEmpty(CUSTOMER_INFO_SEARCH.returndata[0].devices_brand)){
		$.alert("设备品牌：devices_brand为空！");
		return false;
	}
	if(window.parent.isNullOrEmpty(CUSTOMER_INFO_SEARCH.returndata[0].cust_name)){
		$.alert("原客户名称为空！");
		return false;
	}
	if(window.parent.isNullOrEmpty(CUSTOMER_INFO_SEARCH.returndata[0].tele_type)){
		$.alert("电信类型为空！");
		return false;
	}
	if(window.parent.isNullOrEmpty(ruleInfoScore.selecteditem.brand_code)){
		$.alert("积分兑换项目为空！");
		return false;
	}
	
	if(window.parent.isNullOrEmpty(ruleInfoScore.selecteditem.ofr_name)){
		$.alert("积分兑换产品为空！");
		return false;
	}
	
	if(window.parent.isNullOrEmpty(PC_IDREADER.pc_idreader_info.cust_seq_id )){
		$.alert("未读取身份证：证件序列号为空！");
		return false;
	}
	return true;
}
function jifenverifyForm(){
	var flag = true;
	var contact_phone = $.trim($("#contact_phone").val());
	
	var contact_adress= $.trim($("#contact_address").val());
	
	var errorMessage = "";
	
	var phoneRegex =  /^(13[0-9]|15[012356789]|18[02356789]|17[02356789]|14[57]|17[0])[0-9]{8}$/;//手机号验证	
	var phoneRegex2 = /^(0[0-9]{2,3})?([0-9]{7,8})$/;
	//var phoneRegex = /^\d{11}$/;//手机号验证
	
	var adressRegex = /[\u4E00-\u9FA5]/g; //判断中文
//	 var phoneRegex2=/^\d*(\d)\1{9,}\d*$/;//校验重复号码
	
	if(!phoneRegex.test(contact_phone) && !phoneRegex2.test(contact_phone)){
		errorMessage = "联系电话请输入固话或手机号码!";	
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
	 	
	if(window.parent.isNullOrEmpty(contact_adress)){
		errorMessage = "联系地址不能为空!";		
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
	}else{
		var addressMatch = contact_adress.match(adressRegex);
		console.info(addressMatch);
		if(addressMatch&&addressMatch.length){
			var chinaLength = addressMatch.length; //返回中文的个数   
			if(chinaLength < 7){		
				flag = false;		
				errorMessage = "联系地址至少输入7个汉字!";
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
			flag = false;		
			errorMessage = "联系地址至少输入7个汉字!";
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
		
	}	
	
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
	/*if(PC_IDREADER.pc_idreader_info.pCardNo != CUSTOMER_INFO_SEARCH.returndata[0].id_number){
		flag = false;
		console.info("老用户证件号码="+CUSTOMER_INFO_SEARCH.returndata[0].id_number);
		errorMessage="老用户证件号码与选择证件号码不一致~";
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
	}*/
	/*
		if(black_flag=="1"){
		return DIALOG_UTIL.showTypeDialog("warring","黑名单客户，不能进行开户操作!");
		}
		if(total_fee>0&&baseInfo.tele_type=="2G"){
			return DIALOG_UTIL.showTypeDialog("warring","客户历史欠款"+total_fee.toFixed(2)+"元,请清欠后来开户!");
		}
	*/
	return flag;
};