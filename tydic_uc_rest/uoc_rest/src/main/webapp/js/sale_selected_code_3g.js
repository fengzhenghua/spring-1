$(document).ready(function() {
	  /*全局变量－－－获取上一页传入的 电信类型和套餐制定类型*/
	  var full_path_value=$.trim($("#full_path_value").val());  //项目路径
	  var tele_type=$.trim($("#tele_type").val());				//电信类型
	  var device_number=$.trim($("#acc_nbr").val());			//设备号码
	  var pay_flag=$.trim($("#pay_flag").val());				//付费类型
	  /*选择月消费金额*/
	  $("#month_fee_dl li").click("click",function(){
		  $(this).parent().each(function () {
			 $('#month_fee_dl li').removeClass("over");
	      });
		  $(this).addClass("over");
		  $("#month_fee_a").text($(this).text()).append("<span>&nbsp</span>");
		  $("#month_fee_hidden").val($(this).val());
		  $("#month_fee_a").show();
		  $("#clear_condition_a").show();
		  $("#selected_conditions").show();
		  $("#selected_line").hide();
		  selectPage();
	  });
	  /*取消月消费金额*/
	   $(document).on("click",".selected_month_fee",function(){
		$("#month_fee_a").text("");
		$("#month_fee_hidden").val(null);
		$("#month_fee_a").hide();
		$("#month_fee_dl li").each(function () {
		   $('#month_fee_dl li').removeClass("over");
	    });
		var month_fee= $("#month_fee_hidden").val();
		if(month_fee==''){
			$("#clear_condition_a").hide();
			$("#selected_conditions").hide();
			$("#selected_line").show();
		}
		selectPage();
	}); 
	   /*清楚筛选条件*/
	   $(document).on("click",".clear_condition",function(){
			/*取消月消费金额*/
			$("#month_fee_a").text("");
			$("#month_fee_hidden").val(null);
			$("#month_fee_a").hide();
			$("#month_fee_dl li").each(function () {
			   $('#month_fee_dl li').removeClass("over");
		    });
			/*隐藏筛选条件*/
			$("#clear_condition_a").hide();
			$("#selected_conditions").hide();
			$("#selected_line").show();
			selectPage();
	   });
	  selectPage();//页面初始化调用
	  /*--------------------------------------------------加载套餐---------------------------------------*/
	  function selectPage() {
		   /*初次加载时，不显示过滤信息*/
		  var month_fee= $("#month_fee_hidden").val();
		  if(month_fee==''){
				$("#clear_condition_a").hide();
				$("#selected_conditions").hide();
				$("#selected_line").show();
		  }
		  
		  $("#sale_selected_code").html('');//先清空套餐显示标签
		   $("#layerPaging").pages({
			   url:"getSaleSelectedCode",
			   onLoad:function(codeOfrVo){
				   var html = '<a href="javascript:void(0);" title="'+
				   codeOfrVo.ofr_desc
				   +'" class="float"><dl><dt><img src="'
					   +full_path_value+'/images/pic_2.jpg" /></dt><dd><div style="display:none">'
					   +codeOfrVo.ofr_id+'</div></dd><dd><p>'
					   +codeOfrVo.ofr_name+'</p></dd></dl>'+
					   '<input name="ofr_id" 		type="hidden" value="'+codeOfrVo.ofr_id+'" >'
					   +'<input name="ofr_name" 	type="hidden" value="'+codeOfrVo.ofr_name+'" >'
					   +'<input name="ofr_desc" 	type="hidden" value="'+codeOfrVo.ofr_desc+'" >'
					   +'<input name="market_price" type="hidden" value="'+codeOfrVo.market_price+'" >'
					   +'<input name="ofr_status" 	type="hidden" value="'+codeOfrVo.ofr_status+'" >'
					   +'<input name="eff_flag" 	type="hidden" value="'+codeOfrVo.eff_flag+'" >'
					   +'<input name="eff_date" 	type="hidden" value="'+codeOfrVo.eff_date+'" >'
					   +'<input name="exp_date" 	type="hidden" value="'+codeOfrVo.exp_date+'" >'
					   +'<input name="tele_type" 	type="hidden" value="'+codeOfrVo.tele_type+'" >'
					   +'<input name="oper_date" 	type="hidden" value="'+codeOfrVo.oper_date+'" >'
					   +'<input name="exclude_code" type="hidden" value="'+codeOfrVo.exclude_code+'" >'
					   +'<input name="rule_id" 		type="hidden" value="'+codeOfrVo.rule_id+'" >'
					   +'<input name="ofr_type" 	type="hidden" value="'+codeOfrVo.ofr_type+'" >'
					   +'<input name="ofr_sub_type" type="hidden" value="'+codeOfrVo.ofr_sub_type+'" >'
					   +'<input name="bss_product" 	type="hidden" value="'+codeOfrVo.bss_product+'" >'
					   +'<input name="brand_code" 	type="hidden" value="'+codeOfrVo.brand_code+'" >'
					   +'<input name="pay_flag" 	type="hidden" value="'+codeOfrVo.pay_flag+'" >'
					   +'<input name="zb_product" 	type="hidden" value="'+codeOfrVo.zb_product+'" ></a>';
				   return html;
			   },
			   getData:function(){
				   return {"tele_type":tele_type,"pay_flag":pay_flag};
			   },
			   dataDiv:"sale_selected_code"
		   });
	  }
	  /*--------------------------------------------------选择套餐---------------------------------------*/
	  $(document).on("click",".warp_order a",function(){
		  /*选中时，有选中标志*/
		  $(this).parent().each(function () {
				 $('.warp_order a').removeClass("hover");
	      });
		  $(this).addClass("hover");
		  HideOfSelectPackage();		//初始化标签
		  /*初始化数据*/
		  ClearOfCheckTerminal();		//清空终端校验信息
		  ClearOfActivityDesc();		//清空活动描述信息
		  ClearOfActivitySelectedItem();//清空活动选中信息
		  ClearOfFormPackageInfo();		//清空表单中套餐信息
		  ClearOfFormActivityInfo();	//清空表单中活动信息
		  

		  /*获取套餐ID，套餐名称,套餐描述，套餐价格，套餐生效时间，套餐失效时间*/
		  var ofr_id=$(this).children("input:eq(0)").val();			//套餐ID
		  var ofr_name=$(this).children("input:eq(1)").val();		//套餐名称
		  var ofr_desc=$(this).children("input:eq(2)").val();		//套餐描述
		  var market_price=$(this).children("input:eq(3)").val();	//套餐价格
		  var ofr_status=$(this).children("input:eq(4)").val();		//套餐状态
		  var eff_flag=$(this).children("input:eq(5)").val();		//生效标志
		  var eff_date=$(this).children("input:eq(6)").val();		//生效时间
		  var exp_date=$(this).children("input:eq(7)").val();		//失效时间
		  var tele_type=$(this).children("input:eq(8)").val();		//电信类型
		  var oper_date=$(this).children("input:eq(9)").val();		//操作时间
		  var exclude_code=$(this).children("input:eq(10)").val();	//
		  var rule_id=$(this).children("input:eq(11)").val();		//销售品产品选择关系规则
		  var ofr_type=$(this).children("input:eq(12)").val();		//电信类型编码
		  var ofr_sub_type=$(this).children("input:eq(13)").val();	//套餐类型
		  var bss_product=$(this).children("input:eq(14)").val();	//产品编号
		  var brand_code=$(this).children("input:eq(15)").val();	//
		  var pay_flag=$(this).children("input:eq(16)").val();		//付费标志
		  var zb_product=$(this).children("input:eq(17)").val();	//ess套餐编码
		  /*将套餐ID，套餐名称,套餐描述，套餐价格，套餐生效时间，套餐失效时间赋值到表单中*/
		  $("#ofr_id_result").val(ofr_id);
		  $("#ofr_name_result").val(ofr_name);
		  $("#ofr_desc_result").val(ofr_desc);
		  $("#market_price_result").val(market_price);
		  $("#ofr_status_result").val(ofr_status);
		  $("#eff_flag_result").val(eff_flag);
		  $("#eff_date_result").val(eff_date);
		  $("#exp_date_result").val(exp_date);
		  $("#tele_type_result").val(tele_type);
		  $("#oper_date_result").val(oper_date);
		  $("#exclude_code_result").val(exclude_code);
		  $("#rule_id_result").val(rule_id);
		  $("#ofr_type_result").val(ofr_type);
		  $("#ofr_sub_type_result").val(ofr_sub_type);
		  $("#bss_product_result").val(bss_product);
		  $("#brand_code_result").val(brand_code);
		  $("#pay_flag_result").val(pay_flag);
		  $("#zb_product_result").val(zb_product);
		  var data={
				  "ofr_id":ofr_id  
		  }
		  $.ajax({
				type: "POST",
				url: "isCanJoinActivity",
				data:data,//需要传的数据
				dataType: "json",
				success: function(data){
					var jsonObj=eval(data);
					if(jsonObj.type=='success'){
						var flag=jsonObj.args.flag;//可参加该活动产品：0，不可参加该活动产品：1，默认参加该活动产品：2，绑定参加该活动产品：3
						$("#activity_plan").empty();
						if(flag=='1'){
							document.getElementById("activity_plan").innerHTML="<li><input name='agreement_plan'  type='radio' value='1' > 参加合约计划</li>"
								+"<li><input name='agreement_plan'  type='radio' value='0' checked='checked' > 不参加合约计划</li>";
							var input = $("#activity_plan").find("input[type='radio']");
							input.attr("disabled","disabled");
						}else if(flag=='3'){
							document.getElementById("activity_plan").innerHTML="<li><input name='agreement_plan' checked='checked' type='radio' value='1' > 参加合约计划</li>"
								+"<li><input name='agreement_plan'  type='radio' value='0'  > 不参加合约计划</li>";
							var input = $("#activity_plan").find("input[type='radio']");
							input.attr("disabled","disabled");
							loadActivity_Type();//加载活动类型的下拉列表
						}else{
							document.getElementById("activity_plan").innerHTML="<li><input name='agreement_plan'  type='radio' value='1' > 参加合约计划</li>"
								+"<li><input name='agreement_plan'  type='radio' value='0' checked='checked' > 不参加合约计划</li>";
						}
											
					}else{
						$.message({
								type:'error', //可以是3个，success.error.warn
								content:jsonObj.content//错误信息
						});
					}
				},
				waitMsg:'加载中...'
			});
	  });
	  /*加载活动类型*/
	  function loadActivity_Type(){
		  var data={
				   "ofr_id":$("#ofr_id_result").val(),
				   "ofr_type":$("#ofr_type_result").val(),
				   "ofr_sub_type":$("#ofr_sub_type_result").val(),
				   "tele_type":tele_type
		   }
		   $.ajax({
				type: "POST",
				url: "getActivityType",
				data:data,//需要传的数据
				dataType: "json",
				success: function(data){
					var jsonObj=eval(data);
					if(jsonObj.type=='success'){
						$("#activity_type").html(jsonObj.args.options);//向activity_type添加option
					}else{
						$.message({
								type:'error', //可以是3个，success.error.warn
								content:jsonObj.content//错误信息
						});
					}
				},
				waitMsg:'加载中...'
			});
		   $("#activity_information").show();
		   $("#show_activity").show();//显示活动类型
	  }
	   /*--------------------------------------------------选择合约计划---------------------------------------*/
	   $(document).on("click","#select_activity_plan ul li input",function(){
			   var agreement_plan=$('input:radio[name="agreement_plan"]:checked').val();
			   //不参加合约计划
			   if(agreement_plan=='0'){
				   HideOfSelectPackage();		//初始化标签
				  /*初始化数据*/
				  ClearOfCheckTerminal();		//清空终端校验信息
				  ClearOfActivityDesc();		//清空活动描述信息
				  ClearOfActivitySelectedItem();//清空活动选中信息
				  ClearOfFormActivityInfo();	//清空表单中活动信息
			   }
			   //参加合约计划
			   if(agreement_plan=='1'){
				   var data={
						   "ofr_id":$("#ofr_id_result").val(),
						   "ofr_type":$("#ofr_type_result").val(),
						   "ofr_sub_type":$("#ofr_sub_type_result").val(),
						   "tele_type":tele_type
				   }
				   $.ajax({
						type: "POST",
						url: "getActivityType",
						data:data,//需要传的数据
						dataType: "json",
						success: function(data){
							var jsonObj=eval(data);
							if(jsonObj.type=='success'){
								$("#activity_type").html(jsonObj.args.options);//向activity_type添加option
							}else{
								$.message({
										type:'error', //可以是3个，success.error.warn
										content:jsonObj.content//错误信息
								});
							}
						},
						waitMsg:'加载中...'
					});
				   $("#activity_information").show();
				   $("#show_activity").show();//显示活动类型
			   }
	   });
	   
	   /*--------------------------------------------------选择活动类型---------------------------------------*/
	   $("#activity_type").change(function(){
		   var activity_type=$("#activity_type").val();
		   var data={
				   "ofr_id":$("#ofr_id_result").val(),
				   "ofr_type":$("#ofr_type_result").val(),
				   "ofr_sub_type":$("#ofr_sub_type_result").val(),
				   "activity_type":activity_type,
				   "tele_type":tele_type
		   }
		   HideOfSelectActivityType();//当选中请选择时，需要隐藏的字段
		   /*选择，请选则的时候*/
		  /*初始化数据*/
		  ClearOfCheckTerminal();		//清空终端校验信息
		  ClearOfActivityDesc();		//清空活动描述信息
		  ClearOfActivitySelectedItem();//清空活动选中信息
		  ClearOfFormActivityInfo();	//清空表单中活动信息
		  if(activity_type==""){
			  return;
		  }
		   $.ajax({
				type: "POST",
				url: "getGuaranteeType",
				data:data,//需要传的数据
				dataType: "json",
				success: function(data){
					var jsonObj=eval(data);
					if(jsonObj.type=='success'){
						$("#guarantee_type_lbl").show();
						$("#guarantee_type").show();
						$("#guarantee_type").html(jsonObj.args.options);//向activity_type添加option
					}else{
						$.message({
								type:'error', //可以是3个，success.error.warn
								content:jsonObj.content//错误信息
						});
					}
				},
				waitMsg:'加载中...'
			});
		   
	   });
	   /*--------------------------------------------------选择担保类型---------------------------------------*/
	   $("#guarantee_type").change(function(){
		   var guarantee_type=$("#guarantee_type").val();
		   var data={
				   "ofr_id":$("#ofr_id_result").val(),
				   "ofr_type":$("#ofr_type_result").val(),
				   "ofr_sub_type":$("#ofr_sub_type_result").val(),
				   "activity_type":$("#activity_type").val(),
				   "guarantee_type":guarantee_type,
				   "tele_type":tele_type
		   }
		   HideOfSelectGuranteeType();//选择，请选则时，需要隐藏的标签
		   /*初始化数据*/
		  ClearOfCheckTerminal();		//清空终端校验信息
		  ClearOfActivityDesc();		//清空活动描述信息
		  ClearOfActivitySelectedItem();//清空活动选中信息
		  ClearOfFormActivityInfo();	//清空表单中活动信息
		  if(guarantee_type==''){
			  return;
		  }
		   $.ajax({
				type: "POST",
				url: "getActivityName",
				data:data,//需要传的数据
				dataType: "json",
				success: function(data){
					var jsonObj=eval(data);
					if(jsonObj.type=='success'){
						$("#activity_name_lbl").show();
						$("#activity_name").show();
						$("#activity_name").html(jsonObj.args.options);//向activity_type添加option
					}else{
						$.message({
								type:'error', //可以是3个，success.error.warn
								content:jsonObj.content//错误信息
						});
					}
				},
				waitMsg:'加载中...'
			});
		   
	   });
	   /*--------------------------------------------------选择可选活动---------------------------------------*/
	   $("#activity_name").change(function(){
		   var activity_name=$("#activity_name").val();//可选活动
		   var activity_type=$("#activity_type").val();//活动类型
		   /*初始化数据*/
		   
		   ClearOfCheckTerminal();		//清空终端校验信息
		   ClearOfActivityDesc();		//清空活动描述信息
		   ClearOfFormActivityInfo();	//清空表单中活动信息
		   //若可选活动“请选择”时
		   if(activity_name==''){
			   ClearOfActivitySelectedItem();		//清空活动选中信息
			   $("#terminal_info").hide();
			   $("#activity_selected_item").hide();	//隐藏选中活动信息
			   $("#activity_desc").hide();
			   return;
		   }
		   var data={
				   "ofr_id":$("#ofr_id_result").val(),
				   "ofr_type":$("#ofr_type_result").val(),
				   "ofr_sub_type":$("#ofr_sub_type_result").val(),
				   "activity_type":$("#activity_type").val(),
				   "guarantee_type":$("#guarantee_type").val(),
				   "activity_id":$("#activity_name").val(),
				   "tele_type":tele_type
		   }
		   $.ajax({
				type: "POST",
				url: "getActivityInfo",
				data:data,//需要传的数据
				dataType: "json",
				success: function(data){
					var jsonObj=eval(data);
					if(jsonObj.type=='success'){
						$("#activity_id_val").val(jsonObj.args.activity_info.activity_id);				//赋值活动ID
						$("#activity_name_val").val(jsonObj.args.activity_info.activity_name);			//活动名称
						$("#activity_desc_val").val(jsonObj.args.activity_info.activity_desc);			//活动描述
						$("#activity_fee_val").val(jsonObj.args.activity_info.market_price);			//活动费用
						$("#activity_type_val").val(jsonObj.args.activity_info.activity_type);		//活动类型
						$("#activity_guarantee_type_val").val(jsonObj.args.activity_info.activity_sub_type);//担保类型
						$("#activity_eff_date_val").val(jsonObj.args.activity_info.eff_date);			//生效时间
						$("#activity_exp_date_val").val(jsonObj.args.activity_info.exp_date);			//失效时间
						$("#activity_eff_flag_val").val(jsonObj.args.activity_info.eff_flag);			//生效标志
						/*根据活动类型（activity_type）判断是否有终端校验*/
						/*不进行终端校验*/
						if(activity_type=='00'){
							$("#activity_desc").show();
							$("#activity_actDescSpan").html(jsonObj.args.activity_info.activity_desc);	//显示活动描述
						}
						/*进行终端校验*/
						if(activity_type=='01'||activity_type=='02'||activity_type=='03'){
							$("#terminal_info").show();
						}
					}else{
						$.message({
								type:'error', 			//可以是3个，success.error.warn
								content:jsonObj.content	//错误信息
						});
					}
				},
				waitMsg:'加载中...'
			});
	   });
	   /*--------------------------------------------------确定选中活动信息---------------------------------------*/
	   $("#btn_selected_activity_item").click("click",function(){
		   var flag=$.trim($("#txt_activity_name_val").text());
		   /*显示在选中活动的表格中*/
		   var txt_activity_name=$("#activity_name option:selected").text();
		   var txt_activity_guarantee_type=$("#guarantee_type option:selected").text();
		   var txt_activity_type=$("#activity_type option:selected").text();
		   /*判断是否已经选中活动*/
		   if(flag!=""){
			   /*判断是否与之前选中的活动相同，相同则只提示不赋值，不相同则提示并赋值*/
			   if(flag==txt_activity_name){
				   $.alert('您所选的活动没有任何变化，无须再次添加','提示');
				   return;
			   }else{
				   var dialog=$.dialog({
					   title:'提示',//提示title
					   content:'是否替换现已选活动?',//显示的内容，支持html格式。
					   buttons:[{id:'btn_cancel',text:'取消', 
						   		onClick:function(){
								   dialog.close();
								   return;
							   	}//点击时候回调函数
						   },{id:'btn_ok',text:'确定',
							   onClick:function(){
								   $("#txt_activity_name_val").text(txt_activity_name);
								   $("#txt_guarantee_type_val").text(txt_activity_guarantee_type);
								   $("#txt_activity_type_val").text(txt_activity_type);
								   /*获取隐藏字段的数据*/
								   $("#activity_id_result").val($("#activity_id_val").val());							//活动ID
								   $("#activity_name_result").val($("#activity_name_val").val());						//活动名称
								   $("#activity_desc_result").val($("#activity_desc_val").val());						//活动描述
								   $("#activity_fee_result").val($("#activity_fee_val").val());							//活动费用
								   $("#activity_guarantee_type_result").val($("#activity_guarantee_type_val").val());	//担保类型
								   $("#activity_type_result").val($("#activity_type_val").val());						//活动类型
								   $("#activity_eff_date_result").val($("#activity_eff_date_val").val());				//生效时间
								   $("#activity_exp_date_result").val($("#activity_exp_date_val").val());				//失效时间
								   $("#activity_eff_flag_result").val($("#activity_eff_flag_val").val());				//生效标志
								   
								   dialog.close();
							   }//点击时候回调函数
					   }]
				   });
			   }
		   }else{
			   /*初次赋值的时候*/
			   $("#txt_activity_name_val").text(txt_activity_name);
			   $("#txt_guarantee_type_val").text(txt_activity_guarantee_type);
			   $("#txt_activity_type_val").text(txt_activity_type);
			   /*获取隐藏字段的数据*/
			   $("#activity_id_result").val($("#activity_id_val").val());							//活动ID
			   $("#activity_name_result").val($("#activity_name_val").val());						//活动名称
			   $("#activity_desc_result").val($("#activity_desc_val").val());						//活动描述
			   $("#activity_fee_result").val($("#activity_fee_val").val());							//活动费用
			   $("#activity_guarantee_type_result").val($("#activity_guarantee_type_val").val());	//担保类型
			   $("#activity_type_result").val($("#activity_type_val").val());						//活动类型
			   $("#activity_eff_date_result").val($("#activity_eff_date_val").val());				//生效时间
			   $("#activity_exp_date_result").val($("#activity_exp_date_val").val());				//失效时间
			   $("#activity_eff_flag_result").val($("#activity_eff_flag_val").val());				//生效标志
		   }
		   $("#activity_selected_item").show();//显示选中活动信息
		   
	   });
	   /*--------------------------------------------------确定删除活动信息---------------------------------------*/
	   $("#btn_delete_activity_item").click("click",function(){
		   ClearOfCheckTerminal();				//清空终端校验信息
		   ClearOfFormActivityInfo();			//清空表单中活动信息
		   ClearOfActivitySelectedItem();		//清空活动选中信息
		   $("#activity_selected_item").hide();	//隐藏选中活动信息
	   });
	   /*--------------------------------------------------提交表单---------------------------------------*/
	   $("#btn_submit").click("click",function(){
		   var ofr_id=$("#ofr_id_result").val();
		   var activity_name=$("#activity_name_result").val();
		   var agreement_plan=$('input:radio[name="agreement_plan"]:checked').val();//合约计划 参加合约计划1，不参加合约计划0
		   var select_self_phone=$('input:radio[name="select_self_phone"]:checked').val();//自备机 自备机入网1，非自备机入网0
		   
		   //将元转换成哩
		   var market_price_result=$("#market_price_result").val();//套餐费用 将元转换成哩
		   var activity_fee_result=$("#activity_fee_result").val();//活动费用 将元转换成哩
		   var acc_nbr_fee_result=$("#acc_nbr_fee_result").val();//号码预存 将元转换成哩
		   market_price_result=market_price_result!=null?market_price_result*1000:0;
		   activity_fee_result=activity_fee_result!=null?activity_fee_result*1000:0;
		   acc_nbr_fee_result=acc_nbr_fee_result!=null?acc_nbr_fee_result*1000:0;
		   $("#market_price_result").val(market_price_result);
		   $("#activity_fee_result").val(activity_fee_result);
		   $("#acc_nbr_fee_result").val(acc_nbr_fee_result);
		   
		   /*判断是否选中套餐*/
		   if(ofr_id==""){
			   $.alert("请选择套餐");
			   return;
		   }
		   var terminal_info_flag=$("#terminal_info").is(":hidden");//判断终端校验的标签是否隐藏  隐藏true，显示false
		   /*参加合约计划且活动没有选中*/
		   if(agreement_plan=='1'&&activity_name==""&&terminal_info_flag){
			   $.alert("请选择活动");
			   return;
		   }
		   //参加合约计划，没有终端的时候
		   if(agreement_plan=='1'&&activity_name!=""&&terminal_info_flag){
			   $("#saleOpenForm").submit();
		   }
		   /*参加合约计划且活动已经选中*/
		   if(agreement_plan=='0'){//不参加活动时可提交
			   $("#saleOpenForm").submit();
		   }
		   if(agreement_plan=='1'&&activity_name!=""&&!terminal_info_flag){//当参加合约，选中活动，有终端校验
			   var data={
					   "terminal_id":$("#terminal_id").val()
			   };
			   $.ajax({
					type: "POST",
					url: "occupyTerminal",
					data:data,//需要传的数据
					dataType: "json",
					success: function(data){
						var jsonObj=eval(data);
						if(jsonObj.type=='success'){
							$("#saleOpenForm").submit();
						}else{
							$.alert("终端预占不通过");
							return;
						}
					},
					waitMsg:'加载中...'
				});
		   }
		   if(agreement_plan=='1'&&activity_name==""&&!terminal_info_flag){//当参加合约，未选中活动，有终端校验
			   $.alert("终端校验未通过，不可以提交");
			   return;
		   }
	   });
	   /*--------------------------------------------------终端校验---------------------------------------*/
	   $("#btn_read_terminal_id").click("click",function(){
		   ClearOfFormActivityInfo();			//清空表单中活动信息
		   ClearOfActivitySelectedItem();		//清空活动选中信息
		   var terminal_id=$.trim($("#terminal_id").val());
		   //判断终端是否为空
		   if(!terminal_id){
			   $.alert("终端号不能为空");
			   return;
		   }
		   var data={
				   "terminal_id":terminal_id
		   };
		   $.ajax({
				type: "POST",
				url: "CheckTerminal",
				data:data,//需要传的数据
				dataType: "json",
				success: function(data){
					var jsonObj=eval(data);
					if(jsonObj.type=='success'){
					    /*获取隐藏字段的数据*/
					    $("#activity_id_result").val($("#activity_id_val").val());							//活动ID
					    $("#activity_name_result").val($("#activity_name_val").val());						//活动名称
					    $("#activity_desc_result").val($("#activity_desc_val").val());						//活动描述
					    $("#activity_fee_result").val($("#activity_fee_val").val());						//活动费用
					    $("#activity_guarantee_type_result").val($("#activity_guarantee_type_val").val());	//担保类型
					    $("#activity_type_result").val($("#activity_type_val").val());						//活动类型
					    $("#activity_eff_date_result").val($("#activity_eff_date_val").val());				//生效时间
					    $("#activity_exp_date_result").val($("#activity_exp_date_val").val());				//失效时间
					    $("#activity_eff_flag_result").val($("#activity_eff_flag_val").val());				//生效标志
					    $("#terminal_id_result").val(terminal_id);											//终端号
						$("#terminal_checked").text("通过");
					}else{
						$.message({
								type:'error', //可以是3个，success.error.warn
								content:jsonObj.content//错误信息
						});
						$("#terminal_checked").text("不通过");
					}
				},
				waitMsg:'加载中...'
			});

	   });
	   /**
		 * 订单取消
		 */
		$("#btn_cancel").click(function(){
			 var dialog=$.dialog({
				   title:'提示',//提示title
				   content:'是否确定取消订单?',//显示的内容，支持html格式。
				   buttons:[
				        {id:'btn_ok',text:'确定',
						   onClick:function(){
							   top.location.href = "../../authority/index/index";
							   dialog.close();
						   }//点击时候回调函数
				        },
				        {id:'btn_cancel',text:'取消', 
				   		onClick:function(){
							   dialog.close();
							   return;
						   	}//点击时候回调函数
					   }]
			   });
		});
	  /**
	   * 选择套餐时
	   * （1）需要显示部分标签，和需要部分隐藏标签
	   * （2）需要清空表单数据，和选中的活动信息。
	   */
	  function HideOfSelectPackage(){
		  /*显示活动*/
		  $("#box").show();
		  //显示“活动信息” 标签
		  $("#margin_top").show();
		  /*显示参加合约计划*/
		  $("#select_activity_plan").show();
		  //隐藏 活动类型 担保类型 可选活动
		  $("#show_activity").hide();
		  //隐藏 担保类型 标签
		  $("#guarantee_type_lbl").hide();
		  //隐藏 担保类型
		  $("#guarantee_type").hide();
		  //隐藏可选活动 标签
		  $("#activity_name_lbl").hide();
		  //隐藏可选活动
		  $("#activity_name").hide();
		  //隐藏终端校验
		  $("#terminal_info").hide();
		  //隐藏活动描述信息
		  $("#activity_desc").hide();
		  //隐藏活动选中信息
		  $("#activity_selected_item").hide();
		  //隐藏自备机入网
		  $("#self_phone_information").hide();
		  
		  $("#activity_information").hide();
	  }
	  /**
	   * 不参加合约计划是，需要隐藏的标签
	   */
	  function HideOfNoJoinActivity(){
		  /*显示活动*/
		  $("#box").show();
		  /*显示参加合约计划*/
		  $("#agreement_plan").show();
		  //隐藏“活动信息” 标签
		  $("#margin_top").hide();
		  //隐藏 活动类型 担保类型 可选活动
		  $("#show_activity").hide();
		  //隐藏 担保类型 标签
		  $("#guarantee_type_lbl").hide();
		  //隐藏 担保类型
		  $("#guarantee_type").hide();
		  //隐藏可选活动 标签
		  $("#activity_name_lbl").hide();
		  //隐藏可选活动
		  $("#activity_name").hide();
		  //隐藏终端校验
		  $("#terminal_info").hide();
		  //隐藏活动描述信息
		  $("#activity_desc").hide();
		  //隐藏活动选中信息
		  $("#activity_selected_item").hide();
	  }
	  /**
	   * 选择活动类型时，需要隐藏的标签
	   */
	  function HideOfSelectActivityType(){
		//隐藏 担保类型 标签
		  $("#guarantee_type_lbl").hide();
		  //隐藏 担保类型
		  $("#guarantee_type").hide();
		  //隐藏可选活动 标签
		  $("#activity_name_lbl").hide();
		  //隐藏可选活动
		  $("#activity_name").hide();
		  //隐藏终端校验
		  $("#terminal_info").hide();
		  //隐藏活动描述信息
		  $("#activity_desc").hide();
		  //隐藏活动选中信息
		  $("#activity_selected_item").hide();
	  }
	  /**
	   * 选择担保类型时，需要隐藏的标签
	   */
	  function HideOfSelectGuranteeType(){
		//隐藏可选活动 标签
		  $("#activity_name_lbl").hide();
		  //隐藏可选活动
		  $("#activity_name").hide();
		  //隐藏终端校验
		  $("#terminal_info").hide();
		  //隐藏活动描述信息
		  $("#activity_desc").hide();
		  //隐藏活动选中信息
		  $("#activity_selected_item").hide();
	  }
	  /**
	   * 选择可选活动时，需要隐藏的标签
	   */
	  function HideOfSelectActivityName(){
		  //隐藏终端校验
		  $("#terminal_info").hide();
		  //隐藏活动描述信息
		  $("#activity_desc").hide();
		  //隐藏活动选中信息
		  $("#activity_selected_item").hide();
	  }
	  /**
	   * 清空表单中活动信息
	   */
	  function ClearOfFormActivityInfo(){
		  /*表单中－－活动*/
		  $("#activity_id_result").val("");					//活动ID
		  $("#activity_name_result").val("");				//活动名称
		  $("#activity_type_result").val("");				//活动类型
		  $("#activity_guarantee_type_result").val("");		//担保类型
		  $("#activity_desc_result").val("");				//活动描述
		  $("#activity_fee_result").val("");				//活动预存费用
		  $("#activity_eff_date_result").val("");			//活动生效时间
		  $("#activity_exp_date_result").val("");			//活动失效时间
		  $("#activity_eff_flag_result").val("");			//活动生效标志
		  $("#terminal_brand_result").val("");				//终端品牌
		  $("#terminal_model_result").val("");				//终端机型
		  $("#terminal_fee_result").val("");				//终端价格
		  $("#terminal_id_result").val("");				    //终端串号
	  }
	  /**
	   * 清空表单中套餐信息
	   */
	  function ClearOfFormPackageInfo(){
		  /*表单中－－套餐*/
		  $("#ofr_id_result").val("");
		  $("#ofr_name_result").val("");
		  $("#ofr_desc_result").val("");
		  $("#market_price_result").val("");
		  $("#ofr_status_result").val("");
		  $("#eff_flag_result").val("");
		  $("#eff_date_result").val("");
		  $("#exp_date_result").val("");
		  $("#tele_type_result").val("");
		  $("#oper_date_result").val("");
		  $("#exclude_code_result").val("");
		  $("#rule_id_result").val("");
		  $("#ofr_type_result").val("");
		  $("#ofr_sub_type_result").val("");
		  $("#bss_product_result").val("");
		  $("#brand_code_result").val("");
		  $("#pay_flag_result").val("");
		  $("#zb_product_result").val("");
	  }
	  /**
	   * 清空活动选中的活动信息
	   */
	  function ClearOfActivitySelectedItem(){
		  $("#txt_activity_name_val").empty();			//
		  $("#txt_activity_type_val").empty();			//
		  $("#txt_guarantee_type_val").empty();		//
		  $("#activity_id_val").empty();				//
		  $("#activity_name_val").empty();				//
		  $("#activity_type_val").empty();				//
		  $("#activity_guarantee_type_val").empty();	//
		  $("#activity_desc_val").empty();				// 
		  $("#activity_fee_val").empty();				//
	  }
	  /**
	   * 清空活动描述信息
	   */
	  function ClearOfActivityDesc(){
		  $("#activity_actDescSpanFee").empty();		//显示 活动预存费用，协议期内次月开始每月返还,赠送,在网时间不少于月等
		  $("#activity_actDescSpan").empty();			//显示活动信息
	  }
	  /**
	   * 清空终端校验信息
	   */
	  function ClearOfCheckTerminal(){
		  $("#terminal_id").val("");					//终端ID
		  $("#terminal_checked").empty();				//是否通过终端校验
		  $("#activity_terminalBrandDesc").empty();	//终端品牌
		  $("#activity_terminalModelDesc").empty();	//终端型号
		  $("#activity_terminalFeeDesc").empty();		//终端价格
	  }
	  
	  
});