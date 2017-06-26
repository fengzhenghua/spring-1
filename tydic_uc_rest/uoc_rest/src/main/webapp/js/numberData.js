var intNumber=0;
var tele_type='';
$(document).ready(function() {
	$("#show2G").hide();
	$("#show3G").hide();

	$("#unlock_query").hide();
	$("#unlock").hide();
	$("#query").click(function() {
		selectPage();
	});
	$("#unlock").click(function() {
		resInfoCheckOfUnlock();
	});	
	$("#unlock_section").bind("change",function (){
		changeResourceInfoUnlock();	
	});
	/*$("#teleType2G").bind("click",function (){
		changeTeleType2G();			
	});
	$("#teleType3G").bind("click",function (){
		changeTeleType3G();	
	});
	$("#teleType4G").bind("click",function (){
		changeTeleType4G();
	});*/

	$("#mob_section").bind("change",function (){
		haoduanSelect();	
	});
	/*$("#mob_section_3G").bind("change",function (){
		haoduanSelect();	
	});
	$("#mob_section_4G").bind("change",function (){
		haoduanSelect();	
	});*/
	
});
function selectPage() {
	$("#dataLength").val("");
	var perrty_type = $.trim($("#perrty_type_pc").val());
	var good_type = $.trim($("#good_type").val());
	var fuzzy_query = $.trim($("#fuzzy_query").val());
	var mob_section;
	
	mob_section = $.trim($("#mob_section").val());
	if(mob_section=="185"||mob_section=="186"){
		tele_type = "3G4G";
	}else if(mob_section=="176" || mob_section=="175"){
		tele_type = "4G";
	}else{
		tele_type = "2G";
	}
	if(mob_section=="10000"){
		mob_section="";
		tele_type = "ALL";
		perrty_type="";
	}
	
	/*if(mob_section=="10000"){
		if(tele_type=="4G"){
		 mob_section="185";
		}else if(tele_type=="3G"){
		  mob_section="186";
		}else{
		  mob_section="";
		}
	}*/
	if(fuzzy_query=="请输入1-11位数字查询号码"){
		fuzzy_query="";
	}
	if(fuzzy_query=="请输入后1-8位数字查询号码"){
		fuzzy_query="";
	}
	
	if(fuzzy_query==""&&mob_section==""){
		mob_section="185";
	}
	
//	if(perrty_type=="1"){
//		perrty_type="";
//	}
	var GetURl= application.fullPath + 'authority/numberData/queryNumberData';
	$.ajax({
		url:GetURl,
		data:{
			"mob_section":mob_section,
			"perrty_type":perrty_type,
			"good_type":good_type,
			"fuzzy_query":fuzzy_query,
			"tele_type":tele_type,
			"page_info":"phonenumber.html"
		},
		dataType:'json',
		type:'post',
		waitMsg:"查询号码",
		success:function(page){
			$("#list").html('');
			var htmlNew = '';
			if(page.dataRows!=null){
			if(page.dataRows.length>0){
			 $("#dataLength").val(page.dataRows.length);
			 for(var i = 0;i < page.dataRows.length;i++){
				var numberBean = page.dataRows[i];
				/*if($("#tele_type").val()=="3G"||$("#tele_type").val()=="4G"){
					var html = '<div class="wrap_line"  id="'+i+'wrap\"  deviceNumber="'+numberBean.acc_nbr+'\,'+numberBean.first_prepay+'\,'+numberBean.mon_limit+'\">'
						+'<a href="###" onClick=\"jump(\''+numberBean.acc_nbr+'\',\''+numberBean.first_prepay+'\',\''+numberBean.mon_limit+'\')" class="code" id="'+numberBean.acc_nbr+'"> <span class="join" style="display:none;" id="'+numberBean.acc_nbr+'progress\">加入备选</span><dl><dt>'+numberBean.acc_nbr+'<dd>'
						+'首次预存'+numberBean.first_prepay+'元</dd>'
						+'  </dl></a></div>';
				}else{*/
					var html = '<div class="wrap_line"  id="'+i+'wrap\"  deviceNumber="'+numberBean.acc_nbr+'\,'+numberBean.first_prepay+'\,'+numberBean.mon_limit+'\">'
						+'<a href="###" onClick=\"jump(\''+numberBean.acc_nbr+'\',\''+numberBean.first_prepay+'\',\''+numberBean.mon_limit+'\')" class="code" id="'+numberBean.acc_nbr+'"> <span class="join" style="display:none;" id="'+numberBean.acc_nbr+'progress\">加入备选</span><dl><dt>'+numberBean.acc_nbr+'<dd>'
						//+'首次预存'+numberBean.first_prepay+','+'月消费'+numberBean.mon_limit+'元</dd>'
						+'首次预存'+numberBean.first_prepay+'元</dd>' 
						+'  </dl></a></div>';
				//}
				    htmlNew +=html;
				    
			 }
			 htmlNew +=' <div class="clear"></div>';
			 $("#list").append(htmlNew);
			 startTime();
			}
			}
		}
	});
	
}
function dealHelp(){
	var n=0;
	var m =  $("#dataLength").val();
	if(m==""){
		
	}else{
	var  c = m-n+1; 
	var d=Math.floor(Math.random() * c + n);
	var show=d+"wrap";
    var deviceNumberString=$("#"+show).attr("deviceNumber");	
    var strs= new Array(); //定义一数组 
    strs=deviceNumberString.split(","); //字符分割 
    var acc_nbr=strs[0];
    var first_prepay=strs[1];
    var mon_limit=strs[2];
    if($("#"+acc_nbr).hasClass("selected")){ 	
    }else{
		  if(parseInt(intNumber)>2){
			$.alert("选择号码框已满");	
			}else{
		    $("#"+acc_nbr).addClass("selected");	
		    var showId=acc_nbr+"progress";
		    $("#"+showId).hide();
		    if(tele_type=="3G"||tele_type=="4G"||tele_type=="3G4G"){
		    	var html = '<div class="compare_line" id="'+acc_nbr+'show\"><a href="###" onClick=\"shanChu(\''+acc_nbr+'\')" class="code code_current"><dl>'
		    		+'<dt>'+acc_nbr+'</dt><dd>首次预存'+first_prepay+'元</dd></dl><div class="close"></div></a></div>';
		    }else{
		    	var html = '<div class="compare_line" id="'+acc_nbr+'show\"><a href="###" onClick=\"shanChu(\''+acc_nbr+'\')" class="code code_current"><dl>'
	    		+'<dt>'+acc_nbr+'</dt><dd>首次预存'+first_prepay+',月消费'+mon_limit+'元</dd></dl><div class="close"></div></a></div>';
		    }
			   $("#compareInfo").append(html);
			   //$("#test1").css({'top':(document.documentElement.scrollTop-$("#test1").height())});
			   $("#test1").show();
			   intNumber++;
		  }
    }
 }
}
function jump(acc_nbr,first_prepay,mon_limit){
	  if($("#"+acc_nbr).hasClass("selected")){
			 $("#"+acc_nbr).removeClass("selected");	 
			 var showId=acc_nbr+"progress";
			 $("#"+showId).removeClass("cancel");
			 $("#"+showId).hide(); 
			 var showDiv=acc_nbr+"show";
			 $("#"+showDiv).remove(); 
			 intNumber--;
			 if(parseInt(intNumber)==0){
				 $("#test1").hide();	
			 }
		 }else{
		   if(parseInt(intNumber)>2){
					
			}else{
		    $("#"+acc_nbr).addClass("selected");	
		    var showId=acc_nbr+"progress";
		    $("#"+showId).hide();
		    /*if($("#tele_type").val()=="3G"||$("#tele_type").val()=="4G"){
		    	var html = '<div class="compare_line" id="'+acc_nbr+'show\"><a href="###" onClick=\"shanChu(\''+acc_nbr+'\')" class="code code_current"><dl>'
		    		+'<dt>'+acc_nbr+'</dt><dd>首次预存'+first_prepay+'元</dd></dl><div class="close"></div></a></div>';
		    }else{*/
		    	var html = '<div class="compare_line" id="'+acc_nbr+'show\"><a href="###" onClick=\"shanChu(\''+acc_nbr+'\')" class="code code_current"><dl>'
	    		//+'<dt>'+acc_nbr+'</dt><dd>首次预存'+first_prepay+',月消费'+mon_limit+'元</dd></dl><div class="close"></div></a></div>';
		    	+'<dt>'+acc_nbr+'</dt><dd>首次预存'+first_prepay+'元</dd></dl><div class="close"></div></a></div>';
		   // }
			   $("#compareInfo").append(html);
			   //$("#test1").css({'top':(document.documentElement.scrollTop-$("#test1").height())});
			   $("#test1").show();
			   intNumber++;
		  }
		 }
  }
function shanChu(acc_nbr){
	 $("#"+acc_nbr).removeClass("selected");	 
	 var showId=acc_nbr+"progress";
	 $("#"+showId).removeClass("cancel");
	 $("#"+showId).hide(); 
	 var showDiv=acc_nbr+"show";
	 $("#"+showDiv).remove(); 
	 intNumber--;
	 if(parseInt(intNumber)==0){
		 $("#test1").hide();	
	 }
}
function startTime()
{   
	 var foo = $("a[class='code']");
	 $(foo).each(function() {
		 $(this).mouseenter(function (){	
				 if($(this).hasClass("selected")){
					 var fooId = $(this).attr("id");
					 var showId=fooId+"progress";
					 $("#"+showId).text("取消备选");
					 $("#"+showId).addClass("cancel");
					 $("#"+showId).show(); 
				 }else{
					 var fooId = $(this).attr("id");
					 var showId=fooId+"progress";
					 if(parseInt(intNumber)>2){
						 $(this).addClass("code_gray");
						 $("#"+showId).text("备选已满")
					  }else{
					   $("#"+showId).text("加入备选");
					  }
					 $("#"+showId).show();
				 }
			});
		 $(this).mouseleave(function (){
			 if(parseInt(intNumber)>2){
				 $(this).removeClass("code_gray"); 
			 }
			 var fooId = $(this).attr("id");
			 var showId=fooId+"progress";
			 $("#"+showId).hide();
			});
	 });	
}

function changeTeleType(tele_type) {
	
	if(tele_type=="2G"){
		$("#show2G").show();
		$("#show3G").hide();
		$("#tele_type").val("2G");
	}else if(tele_type=="3G"){
		$("#show2G").hide();
		$("#show3G").show();
		$("#tele_type").val("3G");
	}else if(tele_type=="4G"){
		$("#show2G").hide();
		$("#show3G").show();
		$("#tele_type").val("4G");
	}

	//haoduanSelect();
	
}

/*function changeTeleType2G() {
	
	$("#teleType2G").addClass("g_current");
	$("#teleType3G").removeClass("g_current");
	$("#teleType4G").removeClass("g_current");
	$("#show2G").show();
	$("#show3G").hide();
	$("#showMob2G").show();
	$("#showMob4G").hide();
	$("#showMob3G").hide();
	$("#tele_type").val("2G");
	haoduanSelect();
	
}
function changeTeleType3G() {
	$("#teleType3G").addClass("g_current");
	$("#teleType2G").removeClass("g_current");
	$("#teleType4G").removeClass("g_current");
	$("#show2G").hide();
	$("#show3G").show();
	$("#showMob3G").show();
	$("#showMob4G").hide();
	$("#showMob2G").hide();
	$("#tele_type").val("3G");
	$("#mob_section_3G").val("185");
	haoduanSelect();
}
function changeTeleType4G() {
	$("#show2G").hide();
	$("#show3G").show();
	$("#teleType4G").addClass("g_current");
	$("#teleType2G").removeClass("g_current");
	$("#teleType3G").removeClass("g_current");
	$("#showMob4G").show();
	$("#showMob3G").hide();
	$("#showMob2G").hide();
	$("#tele_type").val("4G");
	$("#mob_section_4G").val("185");
	haoduanSelect();
}*/

//YUN-172 
function changeResourceInfoUnlock() {
			//获取“是否解锁下拉框”选项值				
			var unlockItem = $("#unlock_section option:selected").val();
			//分支1查询
			if(unlockItem=="qry"){
				$("#fuzzy_query").show();
				$("#query").show();
				$("#unlock_query").hide();
				$("#unlock").hide();
				//$("#perrty_type_pc").show();
				//$("#good_type").show();
				$("#unlock_section_qry").show();
				
				/*$("#teleType3G").show();
				$("#teleType4G").show();
				$("#teleType4G").click();*/
				
			}
			//分支2解锁
			if(unlockItem=="GB002"){
				$("#fuzzy_query").hide();
				$("#query").hide();
				$("#unlock_query").show();
				$("#unlock").show();
				//$("#perrty_type_pc").hide();
				//$("#good_type").hide();
				$("#unlock_section_qry").hide();
				
				/*$("#teleType3G").hide();
				$("#teleType4G").hide();
				$("#teleType2G").click();*/
							
			}
}
function getMonLimitByNumber(acc_nbr){
	var myData = {
			tele_type: tele_type,
	        //传入号码
			device_number: acc_nbr
	}
	var URL = application.fullPath + "authority/selectNumber/getMonLimitByNumber";
	$.ajax({
		url: URL,
		dataType: 'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		type:'post',
		data:myData,
		success: function(message){
			if(message.dataRows!=null && message.dataRows.length>0){
				var numberBean = message.dataRows[0];
				consumption = numberBean.mon_limit;
			}else{
				alter("获取数据失败");
			}
		}
	});
}
function resInfoCheckOfUnlock() {	

	var device_number = $.trim($("#unlock_query").val());

	if(device_number=="请输入预占号码后解锁"){
		alert(device_number);
		return ;
	}
	
	var unlockURl= application.fullPath + 'authority/numberData/numberUnlock';
	$.ajax({
		url:unlockURl,
		data:{
			"device_number":device_number,
			"tele_type":"2G"
		},
		dataType:'json',
		type:'post',
		waitMsg:"正在解锁号码",
		success:function(message){
			if("selectNumber.numberUnlock.success" == message.type){
				$.alert(message.content);
				//转查询页面 
				$("#unlock_section option:first").attr('selected','selected');
				$("#unlock_section").trigger("change");
			 }else{
				 $.alert(message.args.state_desc);
			 }
		}
	});
	
}
function haoduanSelect(){
	var fuzzy_query = $.trim($("#fuzzy_query").val());
	var mob_section;
	mob_section = $.trim($("#mob_section option:selected").val());
	
	 
   if(mob_section=="185"||mob_section=="186"){
	  tele_type ="3G4G";
	  changeTeleType("3G");
	}else if(mob_section=="176" || mob_section=="175" ){
		  tele_type ="4G";
		  changeTeleType("4G");
	}else{
		tele_type ="2G";
		changeTeleType("2G");
	}
	if(mob_section=="10000"){
		$("#show2G").hide();
		$("#show3G").hide();
	}
	
	if(mob_section=="10000"||mob_section=="175"||mob_section=="176"||mob_section=="185"||mob_section=="186"){
		if(fuzzy_query=="请输入后1-8位数字查询号码"){
			$("#fuzzy_query").val("请输入1-11位数字查询号码");
		}
	}else{
		if(fuzzy_query=="请输入1-11位数字查询号码"){
			$("#fuzzy_query").val("请输入后1-8位数字查询号码");
		}
	} 
}

function haoduanOnblur(){
	if($("#fuzzy_query").val()==null||$("#fuzzy_query").val()==""){
		var mob_section;
		 mob_section = $.trim($("#mob_section option:selected").val());
		if(mob_section=="10000"||mob_section=="175"||mob_section=="176"||mob_section=="185"||mob_section=="186"){
			$("#fuzzy_query").val("请输入1-11位数字查询号码");
		}else{
			$("#fuzzy_query").val("请输入后1-8位数字查询号码");
		} 
	}
}
