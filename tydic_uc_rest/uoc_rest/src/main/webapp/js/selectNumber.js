$(document).ready(function(){
	var tele_type = "";
	var perrty_type = "";
	var price_range = "";
	var mob_section = "";
	var good_type = "";
	var ser_type = "";
	var fuzzy_query= $.trim($("#fuzzyQueryValue").val());
	init();
	selectPage();

	//筛选
	//电信类型
	$("#teleType li").click("click",function(){
		 price_range = "";
		 mob_section = "";
		 perrty_type = "";
	 $(this).parent().each(function () {
		$("#13").text("");
   		$(".selectedShowNumber").hide();
   		$("#14").text("");
   		$(".selectedShowMoney").hide();
   		$("#15").text("");
   		$(".selectedShowType").hide();
	   $('#teleType li').removeClass("over");
        });
//	  if( $.trim($(this).text() == "2G") ){
		 
//	  }
	  $(this).addClass("over");
//	   $("#l2").text($(this).text()).append("<span>&nbsp</span>");
	   $("#l2").text($(this).text());
	   $("#l2").show();
	   tele_type = $(this).text();
	   selectPage();
    });
	//号码类型2G 0普通，1靓号
	$("#goodType li").click("click",function(){
//	 $(".selected").hide();
	 $("#mobSection li").parent().each(function () {
		   $('#mobSection li').removeClass("over");
	        });	
	 $("#priceRange li").parent().each(function () {
		   $('#mobSection li').removeClass("over");
     });	
	 mob_section = "";
     $("#l3").text("");
     $("#l3").hide();
		$("#mobSection li").each(function () {
		   $('#mobSection li').removeClass("over");
	        });	
	 $(this).parent().each(function () {
	   $('#goodType li').removeClass("over");
        });
	  $(this).addClass("over");
	  price_range = "";
	   $("#l4").text("");
	   $("#l4").hide();
	  
	  //普通不显示预存话费
	   if($(this).index() == "0"){
			$(".priceRangeDiv").hide();
		}else{
			$(".priceRangeDiv").show();
		}
//	   $("#l6").text($(this).text()).append("<span>&nbsp</span>");
	   $("#l6").text($.trim($(this).text()));
	   $("#l6").show();
	   good_type = $.trim($(this).val());
//	   alert("2G mobsec:" + mob_section);
	   selectPage();
    });
	//号段（2G）
	$("#mobSection li").click("click",function(){
//	 $(".selected").show();
	 $(this).parent().each(function () {
	   $('#mobSection li').removeClass("over");
        });
	  $(this).addClass("over");
	   $("#l3").text($(this).text()).append("<span>&nbsp</span>");
	   $("#l3").show();
	   mob_section = $.trim($(this).text());
//	   alert("2G mobsec:" + mob_section);
	   selectPage();
    });
	$("#mobSection3G li").click("click",function(){
//		$(".selected").show();
		 $(this).parent().each(function () {
		   $('#mobSection3G li').removeClass("over");
	        });
		  $(this).addClass("over");
		   $("#l3").text($(this).text()).append("<span>&nbsp</span>");
		   $("#l3").show();
		   mob_section = $.trim($(this).text());
//		   alert("3G mob_sect:" + mob_section);
		   selectPage();
	    });
	//受理类型
	$("#serType li").click("click",function(){
	 $(this).parent().each(function () {
	   $('#serType li').removeClass("over");
        });
	  $(this).addClass("over");
	   $("#l7").text($(this).text());
	   $("#l7").show();
	   ser_type = $.trim($(this).val());
	   selectPage();
    });
	//预存话费2G
   $("#priceRange li").click("click",function(){
//	   $(".selected").show();
	 $(this).parent().each(function () {
	   $('#priceRange li').removeClass("over");
        });
	  $(this).addClass("over");
	  $("#l4").text($(this).text()).append("<span>&nbsp</span>");
	   $("#l4").show();
//	   alert("this text:"+$(this).text());
	   price_range = $.trim($(this).text());
	   selectPage();
    });
   //靓号类型3G
   $("#numberKind li").click("click",function(){
//	   $(".selected").show();
		 $(this).parent().each(function () {
		   $('#numberKind li').removeClass("over");
	        });
		  $(this).addClass("over");
		  $("#l5").text($(this).text()).append("<span>&nbsp</span>");
		   $("#l5").show();
		  perrty_type = $.trim( $(this).val() );
//		  alert("perrty_type:"+perrty_type);
		   selectPage();
	    });
   //预存档次3G
//    $("#priceRange3G li").click("click",function(){
//	 $(this).parent().each(function () {
//	   $('#priceRange3G li').removeClass("over");
//        });
//	  $(this).addClass("over");
//	  $("#l4").text($(this).text()).append("<span>&nbsp</span>");
//	   $("#l4").show();
//	   selectPage();
//    });
//    $("#numberKind li").click("click",function(){
//	 $(this).parent().each(function () {
//	   $('#perrtyType li').removeClass("over");
//        });
//	  $(this).addClass("over");
//	  $("#l5").text($(this).text()).append("<span>&nbsp</span>");
//	  $("#l5").show();
//	  selectPage();
//    });
   //预存档次3G
    $("#frepayLevel li").click("click",function(){
//     $(".selected").show();
   	 $(this).parent().each(function () {
   	   $('#frepayLevel li').removeClass("over");
           });
   	  $(this).addClass("over");
   	  $("#l4").text($(this).text()).append("<span>&nbsp</span>");
   	  $("#l4").show();
   	  price_range = $.trim( $(this).val() );
   	  selectPage();
     });
    //清除筛选条件
  //    $(".selectedTeleType").live("click",function(){
	 //        $("#l2").text("");
	 //        $("#l2").hide();
		// 	$("#teleType a").each(function () {
		// 	   $('#teleType a').removeClass("over");
		//         });	
		// 	selectPage();
		// }); 
   $(document).on("click",".selectedShowNumber",function(){
   //$(".selectedShowNumber").live("click",function(){
	   		mob_section = "";
	        $("#l3").text("");
	        $("#l3").hide();
			$("#mobSection li").each(function () {
			   $('#mobSection li').removeClass("over");
		        });	
			selectPage();
		}); 
	 $(document).on("click",".selectedShowNumber",function(){
		   //$(".selectedShowNumber").live("click",function(){
		 			mob_section = "";
			        $("#l3").text("");
			        $("#l3").hide();
					$("#mobSection3G li").each(function () {
					   $('#mobSection3G li').removeClass("over");
				        });	
					selectPage();
				});
   $(document).on("click",".selectedShowMoney",function(){
   //$(".selectedShowMoney").live("click",function(){
	   price_range = "";
	   $("#l4").text("");
	    $("#l4").hide();
		$("#priceRange li").parent().each(function () {
		   $('#priceRange li').removeClass("over");
        });
		$("#frepayLevel li").parent().each(function () {
			   $('#frepayLevel li').removeClass("over");
	     });
			selectPage();
		}); 
//   $(document).on("click",".selectedShowMoney",function(){
//   //$(".selectedShowMoney").live("click",function(){
//	   price_range = "";
//	   $("#l4").text("");
//	    $("#l4").hide();
//			$("#priceRange3G li").each(function () {
//			   $('#priceRange3G li').removeClass("over");
//		        });
//			selectPage();
//		}); 
   $(document).on("click",".selectedShowType",function(){
   //$(".selectedShowType").live("click",function(){
	    perrty_type = "";
	    $("#l5").text("");
	    $("#l5").hide();
	    $("#numberKind li").parent().each(function () {
			$('#numberKind li').removeClass("over");
	    });
			selectPage();
		}); 
  
   //清除所有筛选条件
   $(document).on("click",".clear_condition",function(){
  // $(".clear_condition").live("click", function() {
	    perrty_type = "";
		price_range = "";
		mob_section = "";
   		$("#12").text("");
   		$(".teleType").hide();
   		$("#13").text("");
   		$(".selectedShowNumber").hide();
   		$("#14").text("");
   		$(".selectedShowMoney").hide();
   		$("#15").text("");
   		$(".selectedShowType").hide();
   		$("#teleType li").each( function() {
	   			 $('#teleType li').removeClass("over");
	   			});
   		$("#mobSection li").each(function () {
			   $('#mobSection li').removeClass("over");
		        });	
   		$("#priceRange li").each(function () {
			   $('#priceRange li').removeClass("over");
		        });
   		$("#perrtyType li").each(function () {
			   $('#perrtyType li').removeClass("over");
		        });
   		$("#frepayLevel li").parent().each(function () {
			   $('#frepayLevel li').removeClass("over");
	     });
   		$("#numberKind li").parent().each(function () {
			$('#numberKind li').removeClass("over");
	    });
//   		$(".selected").hide();
   		selectPage();
   });
   //默认选中2G，普通
//   if($(".selectedTeleType").text() == ""){
//	   tele_type = "2G";
//	   $(this).text("2G");
//   }
  
   //模糊查询
   $(document).on("click","#search_num",function(){
   //$(".s_btn").live("click",function(){
	   if(true){
	   		 fuzzy_query= $.trim($("#fuzzyQueryValue").val());
	   		if(fuzzy_query == ""||fuzzy_query == "生日/纪念日/车牌/门牌(2-4位)"){
	   			fuzzy_query = "";
	   		}
	   }else{
	   		 $("#fuzzyQueryValue").val("生日/纪念日/车牌/门牌(2-4位)");
	   		 $.alert("请输入2-4位数字");
	   		 return;
	   }
   		selectPage();
   });
   $("#fuzzyQueryValue").focus(function() {
   		$("#fuzzyQueryValue").val("");
   });
   $("#fuzzyQueryValue").blur(function() {
   		if(this.value == ""){
   			this.value = "生日/纪念日/车牌/门牌(2-4位)";
   		}
   		
   });
   //取下拉框value
//   $("select").change(function(){
//	   var selectText = $(this).parent().prev().text();
////	   alert("selecttext:"+selectText);
//	   if($.trim(selectText) == "靓号类型："){
//		   var perrtyValue = $(this).find("option:selected").val();
////		   alert("qian:" + perrty_type);
//		   perrty_type = perrtyValue;
////		   alert("hou:" + perrty_type);
//	   }
//	   if($.trim(selectText) == "预存档次："){
//		   var priceRangeValue = $(this).find("option:selected").val();
//		   price_range = priceRangeValue;
//	   }
//	   selectPage();
//   });

//   $("#tele2G").click(function(){
//   		$(".3G").hide();
//   		$(".2G").show();
//   		$("#show3GData").hide();
//   		$("#showData").show();
//   });
//
//   $("#tele3G").click(function(){
//   		tele_type = "3G";
//   		$(".2G").hide();
//   		$(".3G").show();
//   		$("#showData").hide();
//   		$("#show3GData").show();
//   		
//   		// selectPage();
//   });

   //初始化
   function init(){
	    tele_type = $.trim($("#teleTypeId").val());
	    good_type = $.trim($("#goodTypeId").val() );
	    ser_type = $.trim($("#serTypeId").val());
//	    fuzzy_query= $.trim($("#fuzzyQueryValue").val());
//	    alert("tele_type::"+tele_type);
   		// var tele_type = "2G";
//		var url_tele_type = '${tele_type}';
//		var url_fuzzy_query = '${fuzzy_query}';
		
		// alert("url_tele_type:"+url_tele_type);
		// var tele_type_3G = $("#12").text().trim();
//		alert("fuzzy:"+url_fuzzy_query+"urlteletype:"+url_tele_type);
//		 $("#fuzzyQueryValue").val(url_fuzzy_query);
		if("3G"==tele_type || "4G"==tele_type){
//			$(".div3G").show();
//			$(".div2G").hide();
			$('#teleType li').parent().each(function () {
	   		 	$('#teleType li').removeClass("over");
	        });
			$("li[value=3G]").addClass("over");
//			$("#tele3G").addClass("over");
//		    $("#l2").text(tele_type).append("<span>&nbsp</span>");
			$("#l2").text(tele_type);
		    $("#l2").show();
		    $("#l6").hide();
		}else if("2G"==tele_type){
			$("li[value=2G]").addClass("over");
		}
		// alert("tele_type : "+tele_type);
		
   }
   //校验
   function validate(){
   		var inputValue = $("#fuzzyQueryValue").val();
   		//alert("inputValue : "+inputValue);
   		var reg=/^([0-9]){2,4}$/i;
   		var bool = reg.test(inputValue);
   		if(inputValue == ""||inputValue == "生日/纪念日/车牌/门牌(2-4位)"){
   			bool=true;
   		}
		return bool;
   }
   function selectPage() {
   	   // var tele_type = $("#12").text().trim();
//	   alert("teletype:" + $.trim($("#teleTypeId").text()) );
	   if("3G" == $.trim($("#teleTypeId").val()) || "4G"==$.trim($("#teleTypeId").val()) ){
		   //alert("ssss:"+$("#lastNumBox").is(':checked'));
		   $(".div3G").show();
		   $(".priceRangeDiv").hide();
		   $(".div2G").hide();
	   }else{
		  // alert("ssss:"+$("#lastNumBox").val());
		   $(".div3G").hide();
//		   $(".priceRangeDiv").show();
		   $(".div2G").show();
//		   price_range = $.trim($("#l4").text());
	   }
//	   mob_section= $.trim($("#l3").text());
//	   var price_range= $.trim($("#l4").text());
//	   var perrty_type= $.trim($("#l5").text());
//	   var last_numBox = "";
//	   if($.trim($("#lastNumBox").is(':checked')) == "true"){
//		   last_numBox = "on";
//	   }
//	   if("3G" == $.trim($(".selectedTeleType").text())){
//		 // var perrty_type = $("select").get(0).val();
//		  var obj_pretty = $("select").get(0).val();
//		  alert("........."+obj_pretty);
//	   }
	   
//	   alert("checked:"+$("#lastNumBox").is(':checked'));
//	   alert("last_numBox:"+last_numBox);
//	   alert(mob_section + " " + price_range + " " + perrty_type + " " + last_numBox);
//	   alert("mob:"+mob_section);
	   //获取URL参数
	   // var url_tele_type = '${tele_type}';
	   // var url_fuzzy_query = '${fuzzy_query}';
	   // alert("tele_type:"+url_tele_type+"url_fuzzy_query:"+url_fuzzy_query);
	   //$("#fuzzyQueryValue").val(url_fuzzy_query);
	  
	   
	    var dataDivId = "showData";
//	    alert("tele_type::"+tele_type);
	    if("3G" == tele_type){
	    	dataDivId = "show3GData";
//	    	alert(dataDivId);
	    } 
	    var ofrSubType3G = $("#ofr_sub_type_3g").val();
	    $("#showData").text("");
	    $("#show3GData").text("");//清掉“无数据”提示
		$("#layerPaging").pages({
			url:application.fullPath + 'authority/selectNumber/selectNumberData',
			onLoad:function(numberBean){
					//var html = '<ul class="order"> <li class="tel">'+numberBean.acc_nbr+'</li> <li class="price">'+numberBean.fee+'元</li>	<li class="buy"><a href= "'+application.fullPath+'authority/sale/saleSelectedCode?acc_nbr='+numberBean.acc_nbr+'&fee='+numberBean.fee+'&tele_type='+tele_type+'"></a></li> </ul>';
				var preFee = parseFloat(numberBean.fee) / 100;
				var params = numberBean.acc_nbr+"@"+preFee+"@"+tele_type+"@"+ofrSubType3G+"@"+good_type+"@"+ser_type;
				var html = '<ul class="order"> <li class="tel">'+numberBean.acc_nbr+'</li> <li class="price">'+preFee+'元</li>	<li class="buy"><a  href="#"  onclick="numOccupy(\''+params+'\')"></a></li> </ul>';	
				return html;
			},
			getData:function(){
				return {"fuzzy_query":fuzzy_query,"tele_type": tele_type,"mob_section":mob_section ,"price_range":price_range,"perrty_type":perrty_type,"good_type":good_type,"ser_type":ser_type};
				
			},
			dataDiv:dataDivId,
			pageSize:50
		});
	} 
   
   
   
 
   
});
