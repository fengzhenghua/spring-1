$(document).ready(function() {
	  /*全局变量－－－获取上一页传入的 电信类型和套餐制定类型*/
	  var tele_type=$.trim($("#tele_type").val());				//电信类型
	  var acc_nbr=$.trim($("#acc_nbr").val());					//设备号码
	  var pay_flag=$.trim($("#pay_flag").val());				//付费类型
	   /*查询条件*/
	   $("#txt_search").focus(function() {
		  var txt_search= $("#txt_search").val();
		   if(txt_search=='套餐名称'){
			   $("#txt_search").val("");
		   }
	   });
	   $("#txt_search").blur(function() {
	   		if(this.value == ""){
	   			this.value = "套餐名称";
	   		}
	   });
	   /*模糊查询按钮*/
	   $("#search_btn").click(function(){
		   selectPage();
	   });
	   /*返回上一页按钮*/
	   $("#back_selected_btn").click("click",function(){
		   window.history.back();
	   });
	   /*选择套餐类型*/
	   $("#ofr_sub_type_dl li").click("click",function(){
			  $(this).parent().each(function () {
				  $('#ofr_sub_type_dl li').removeClass("over");
		      });
			  $(this).addClass("over");
			  $("#ofr_sub_type_a").text($(this).text()).append("<span>&nbsp</span>");
			  $("#ofr_sub_type_hidden").val($(this).val());
			  $("#ofr_sub_type_a").show();
			  $("#clear_condition_a").show();
			  $("#selected_conditions").show();
			  $("#selected_line").hide();
			  selectPage();
		 });
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
		/*选择月通话时长*/
	    $("#month_call_duration_dl li").click("click",function(){
			 $(this).parent().each(function () {
				 $('#month_call_duration_dl li').removeClass("over");
		      });
			  $(this).addClass("over");
			  $("#month_call_duration_a").text($(this).text()).append("<span>&nbsp</span>");
			  $("#month_call_duration_hidden").val($(this).val());
			  $("#month_call_duration_a").show();
			  $("#clear_condition_a").show();
			  $("#selected_conditions").show();
			  $("#selected_line").hide();
			  selectPage();
	    });
	    /*月上网流量*/
	    $("#month_net_duration_dl li").click("click",function(){
			 $(this).parent().each(function () {
			   $('#month_net_duration_dl li').removeClass("over");
	         });
			 $(this).addClass("over");
			 $("#month_net_duration_a").text($(this).text()).append("<span>&nbsp</span>");
			 $("#month_net_duration_hidden").val($(this).val());
			 $("#month_net_duration_a").show();
			 $("#clear_condition_a").show();
			 $("#selected_conditions").show();
			  $("#selected_line").hide();
			 selectPage();
		});
	    /*取消套餐类型*/
		   $(document).on("click",".selected_ofr_type",function(){
	        $("#ofr_sub_type_a").text("");
	        $("#ofr_sub_type_hidden").val(null);
	        $("#ofr_sub_type_a").hide();
			$("#ofr_sub_type_dl li").each(function () {
			   $('#ofr_sub_type_dl li').removeClass("over");
		    });	
			
			var ofr_sub_type= $("#ofr_sub_type_hidden").val();
			var month_fee= $("#month_fee_hidden").val();
			var month_call_duration= $("#month_call_duration_hidden").val();
			var month_net_duration= $("#month_net_duration_hidden").val();
			if(ofr_sub_type==''&&month_fee==''&&month_call_duration==''&&month_net_duration==''){
				$("#clear_condition_a").hide();
				$("#selected_conditions").hide();
				$("#selected_line").show();
			}
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
			var ofr_sub_type= $("#ofr_sub_type_hidden").val();
			var month_fee= $("#month_fee_hidden").val();
			var month_call_duration= $("#month_call_duration_hidden").val();
			var month_net_duration= $("#month_net_duration_hidden").val();
			if(ofr_sub_type==''&&month_fee==''&&month_call_duration==''&&month_net_duration==''){
				$("#clear_condition_a").hide();
				$("#selected_conditions").hide();
				$("#selected_line").show();
			}
			selectPage();
		}); 
	   /*取消通话时长*/
	   $(document).on("click",".selected_month_call_duration",function(){
		    $("#month_call_duration_a").text("");
		    $("#month_call_duration_hidden").val(null);
		    $("#month_call_duration_a").hide();
			$("#month_call_duration_dl li").each(function () {
			   $('#month_call_duration_dl li').removeClass("over");
		    });
			
			var ofr_sub_type= $("#ofr_sub_type_hidden").val();
			var month_fee= $("#month_fee_hidden").val();
			var month_call_duration= $("#month_call_duration_hidden").val();
			var month_net_duration= $("#month_net_duration_hidden").val();
			if(ofr_sub_type==''&&month_fee==''&&month_call_duration==''&&month_net_duration==''){
				$("#clear_condition_a").hide();
				$("#selected_conditions").hide();
				$("#selected_line").show();
			}
			selectPage();
		}); 
	   /*取消上网流量*/
	   $(document).on("click",".selected_month_net_duration",function(){
		    $("#month_net_duration_a").text("");
		    $("#month_net_duration_hidden").val(null);
		    $("#month_net_duration_a").hide();
			$("#month_net_duration_dl li").each(function () {
				$('#month_net_duration_dl li').removeClass("over");
			});

		   var ofr_sub_type= $("#ofr_sub_type_hidden").val();
		   var month_fee= $("#month_fee_hidden").val();
		   var month_call_duration= $("#month_call_duration_hidden").val();
		   var month_net_duration= $("#month_net_duration_hidden").val();
		   if(ofr_sub_type==''&&month_fee==''&&month_call_duration==''&&month_net_duration==''){
			   $("#clear_condition_a").hide();
				$("#selected_conditions").hide();
				$("#selected_line").show();
		   }
		   selectPage();
		}); 
	   /*清楚筛选条件*/
	   $(document).on("click",".clear_condition",function(){
		   /*取消套餐类型*/
		    $("#ofr_sub_type_a").text("");
	        $("#ofr_sub_type_hidden").val(null);
	        $("#ofr_sub_type_a").hide();
			$("#ofr_sub_type_dl li").each(function () {
			   $('#ofr_sub_type_dl li').removeClass("over");
		    });	
			/*取消月消费金额*/
			$("#month_fee_a").text("");
			$("#month_fee_hidden").val(null);
			$("#month_fee_a").hide();
			$("#month_fee_dl li").each(function () {
			   $('#month_fee_dl li').removeClass("over");
		    });
			 /*取消通话时长*/
			$("#month_call_duration_a").text("");
		    $("#month_call_duration_hidden").val(null);
		    $("#month_call_duration_a").hide();
			$("#month_call_duration_dl li").each(function () {
			   $('#month_call_duration_dl li').removeClass("over");
		    });
			/*取消上网流量*/
			$("#month_net_duration_a").text("");
		    $("#month_net_duration_hidden").val(null);
		    $("#month_net_duration_a").hide();
			$("#month_net_duration_dl li").each(function () {
				$('#month_net_duration_dl li').removeClass("over");
			});
			/*隐藏筛选条件*/
			$("#clear_condition_a").hide();
			$("#selected_conditions").hide();
			$("#selected_line").show();
			selectPage();
	   });
	   /*选择套餐*/
	   $(document).on("click",".warp_order a",function(){
		  /*选中时，有选中标志*/
		  $(this).parent().each(function () {
				 $('.warp_order a').removeClass("hover");
	      });
		  $(this).addClass("hover");
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
		});
	   selectPage();//初始化时加载的套餐
	   //调用套餐服务
	   function selectPage(){
		  /*地址*/
		  var full_path_value=$.trim($("#full_path_value").val());
		  /*初次加载时，不显示过滤信息*/
		  var ofr_sub_type= $("#ofr_sub_type_hidden").val();
		  var month_fee= $("#month_fee_hidden").val();
		  var month_call_duration= $("#month_call_duration_hidden").val();
		  var month_net_duration= $("#month_net_duration_hidden").val();
		  if(ofr_sub_type==''&&month_fee==''&&month_call_duration==''&&month_net_duration==''){
				$("#clear_condition_a").hide();
				$("#selected_conditions").hide();
				$("#selected_line").show();
		  }
		   /*套餐类型*/
		   var ofr_sub_type= $.trim($("#ofr_sub_type_hidden").val());
		   /*月消费金额*/
		   var month_fee= $.trim($("#month_fee_hidden").val());
		   /*月通话时长*/
		   var month_call_duration=$.trim( $("#month_call_duration_hidden").val());
		   /*月上网流量*/
		   var month_net_duration=$.trim( $("#month_net_duration_hidden").val());
		   /*模糊查询*/
		   var ofr_name=$.trim($("#txt_search").val());
		   if(ofr_name=='套餐名称'){
			   ofr_name='';
		   }
		   /*清空显示套餐标签*/
		   $("#sale_selected_code").html('');
		   $("#layerPaging").pages({
			   url:"getSaleSelectedCode",
			   onLoad:function(codeOfrVo){
				   var html = '<a href="javascript:void(0);" title="'+
				   codeOfrVo.ofr_desc
				   +'" class="float"><dl><dt><img src="'
					   +full_path_value+'/images/pic_2.jpg" /></dt><dd><div style="display:none">'
					   +codeOfrVo.ofr_id+'</div></dd><dd><p>'
					   +codeOfrVo.ofr_name+'</p></dd><dd><span class="price_b">￥'
					   +codeOfrVo.market_price+'</span> <img src="'
					   +full_path_value+'/images/good.jpg" /></dd></dl>'+
					   '<input name="ofr_id" 		type="hidden" value="'+codeOfrVo.bss_product+'" >'
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
					   +'<input name="pay_flag" 	type="hidden" value="'+codeOfrVo.pay_flag+'" ></a>';
				   return html;
			   },
			   getData:function(){
				   return {"tele_type":tele_type,"ofr_sub_type":ofr_sub_type ,"month_fee":month_fee,"month_call_duration":month_call_duration,"month_net_duration":month_net_duration,"ofr_name":ofr_name};
			   },
			   dataDiv:"sale_selected_code"
		   });
	   }
	   /*提交表单*/
	   $("#btn_submit").click(function(){
		   var ofr_id=$("#ofr_id_result").val();
		   /*判断是否选中套餐*/
		   if(ofr_id){
			   $("#saleOpenForm").submit();
		   }else{
			   $.alert("请选择套餐");
		   }
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
});

