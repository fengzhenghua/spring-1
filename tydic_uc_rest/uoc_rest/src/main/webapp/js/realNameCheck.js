/**
 * 实名返档审核
 */
//var gjsonData;
$(function(){
	
	$("#rlist").jqGrid({
        url: "searchResult",
        datatype:"local", 
        mtype:"POST",//提交方式
        loadonce: true,
        height:165,//高度，表格高度。可为数值、百分比或'auto'
        //width:1000,//这个宽度不能为百分比
        autowidth:true,//自动宽
        colNames:['姓名', '身份证号', '地址','手机号码','工单号','添加日期','审核结果','操作','民族','性别','出生日期','有效期'],
        colModel:[
            //{name:'id',index:'id', width:'10%', align:'center' },
            {name:'cert_name',index:'cert_name', width:'6%',align:'center'},
            {name:'cert_id',index:'cert_id', width:'16%',align:'center'},
            {name:'cert_addr',index:'cert_addr', width:'37%', align:"center"},
            {name:'device_number',index:'device_number', width:'13%', align:"center", sortable:false},
            {name:'order_id',index:'order_id', width:'12%', align:"center"},
            {name:'oper_date',index:'oper_date', width:'10%', align:"center"},
            {name:'nopass_reason',index:'nopass_reason', width:'10%', align:"center"},
            {name:'select',index:'select', width:'7%',align:"center", sortable:false},
            {name:'nation',index:'nation',align:"center", hidden:true},
            {name:'sex',index:'sex',align:"center", hidden:true},
            {name:'birthday',index:'birthday',align:"center", hidden:true},
            {name:'exp_date',index:'exp_date',align:"center", hidden:true}
        ],
        //rownumbers:true,//添加左侧行号
       
        viewrecords: true,//是否在浏览导航栏显示记录总数
        rowNum:5,//每页显示记录数
        rowList:[5,10,15,20],//用于改变显示行数的下拉列表框的元素数组。
        jsonReader:{
        	page: "page", 
            total: "total", 
            records: "records", 
        	root: "rows",
            repeatitems : false
        },
        pager:$('#gridPager'),
        loadComplete: function (data) {
        	
        	if( data == null || data.records == 0 ){
        		return;
        	}
        	var result = data.result;
        	gjsonData = data;
        	var ids = jQuery("#rlist").jqGrid('getDataIDs');
        	var audit_flag = $('input:radio[name=audit_flag]:checked').val();
        	for (var i = 0; i < ids.length; i++) {
	        	//var id = ids[i];
	        	var selectBtn = "<a href='#card_front' style='color:#f60' onclick='selectRowData("+ids[i]+")' >选择</a>";
	        	if( audit_flag == "0" ){
	        		jQuery("#rlist").jqGrid('setRowData', ids[i], { select: selectBtn });
	        		jQuery("#rlist").jqGrid('setRowData', ids[i], { nopass_reason: "未审核" });
	        	}
	        	else if (audit_flag == "1") {
	        		jQuery("#rlist").jqGrid('setRowData', ids[i], { nopass_reason: "审核通过" });
				}
        	}
        	if( result == "fail" ){
				clear();
				$.alert("没有与查询条件匹配的结果！");
			}
        }
    });
	
	//点击查询按钮
	$("#search").click(function(){
		var identity = $("#identity").val();
		var phone = $("#phone").val();
		var workid = $("#workid").val();
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		var auditFlag = $('input:radio[name=audit_flag]:checked').val();
		var searchParameter = new Object();
		var flag = false;
		
		if( identity!="" ){
			 
			//身份证正则表达式 
			var IDCard=/^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;
			
			if( !IDCard.test(identity) ){
				$.alert("请输入完整并正确的身份证号码!");
				flag=false;
			}
			else{
				flag=true;
			}
			
			searchParameter.cert_id = identity;
		}
		if( workid!="" ){
			searchParameter.order_id = workid;
			flag=true;
		}
		if( phone!="" ){
			var phoneRegex = /^[0|1]\d{10}$/;//手机号验证
			 if (!phoneRegex.test(phone) || phone.length !=11 ) {
		           
				 $.alert("请输入完整并正确的手机号码!");
				 flag=false;
		     }
			 else{
				 flag=true;
			 }
			searchParameter.device_number = phone;
		}
		if( startDate!="" ){
			searchParameter.start_date = startDate + " 00:00:00";
			flag=true;
		}
		if( endDate!="" ){
			searchParameter.end_date = endDate + " 23:59:59";
			flag=true;
		}
		if( auditFlag!="" ){
			if( auditFlag=="1" ){
				searchParameter.file_status = "3";
			}
			else if( auditFlag=="2" ){
				searchParameter.file_status = "1";
			}
			else{
				searchParameter.file_status = "1";
			}
			searchParameter.audit_flag = auditFlag;
			flag=true;
		}
		
		if( flag ){
			
			$('#rlist').jqGrid('setGridParam',{
				datatype:'json',
				postData:searchParameter
			}).trigger('reloadGrid');
			
			$("#identity").val("");$("#phone").val("");$("#workid").val("");$("#startDate").val("");$("#endDate").val("");
			var postData = $('#rlist').jqGrid("getGridParam", "postData");
			$.each(postData, function (k, v) {
                delete postData[k];
            });
		}
	});
	
	//点击返档按钮
	$("#record").click(function(){
		var cert_name = $("#cert_name").val();
		var nation = $("#nation").val();   
		var sex = $("#sex").val();   
		var birthday = $("#birthday").val();   
		var cert_id = $("#cert_id").val();   
		var exp_date = $("#exp_date").val();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
		var cert_addr = $("#cert_addr").val();
		var device_number = $("#device_number").val();
		var orderId = $("#order_id").val();
		
		if( orderId!="" ){
			var data1={
					order_id:orderId,
					cert_name:cert_name,
					nation:nation,
					sex:sex,
					birthday:birthday,   
					cert_id:cert_id,
					exp_date:exp_date,
					cert_addr:cert_addr,
					device_number:device_number
			};
			$.ajax({
				type : "POST",
				url : "returnRecord",
				dataType : 'json',
				data : data1,
				beforeSend : function(){
					addMask();
					var noHtml = '<div id="showLoadNotice" class="" style="display:block; position: absolute;z-index: 91000;padding: 10px 30px 10px 30px;top:40%;background: url(\''+application.basePath+'images/loading_bg.png\') repeat;border:1px solid #279DE5;border-top:10px solid #279DE5;"><table><tr height="25px"><td align="center" vertical-align="center" style="font-size:14px;font-weight:bold;">返档执行中...</td></tr><tr height="20px"><td align="center" vertical-align="center"><img src="'+application.basePath+'images/loading.gif"/></td></tr></table></div>';
					$('body').append(noHtml);
					var showLoad = $("#showLoadNotice");
					showLoad.css({"left":((($(window).width() - showLoad.width()) / 2 - 100)+ "px"),"top":((getScrollTop() + 220) +"px")});
				},
				success : function(data){
					
					if( data.msg=="success" ){
						$.alert("返档成功！");
						clear();
					}
					else{
						$.alert(data.res+"返档失败，请稍后重试！");
					}
				}
			});
			
		}
	});
	
	//点击审核不通过按钮
	$("#veto").click(function(){
		$("tr[name='reason_tr']").show();
		location.href = "#nopass";
		/*if(confirm("确认不通过吗?") == true){
		}*/
	});
	$("#nopass").click(function(){
		var orderId = $("#order_id").val();
		var nopass_reason = $("#nopass_reason").val(); 
		if( nopass_reason == "" ){
			$.alert("请输入审核不通过的原因！");
			return;
		}
		if( orderId!="" ){
			
			$.ajax({
				type : "POST",
				url : "checkVeto",
				dataType : 'json',
				data : {order_id:orderId,nopass_reason:nopass_reason},
				beforeSend : function(){
					addMask();
					var noHtml = '<div id="showLoadNotice" class="" style="display:block; position: absolute;z-index: 91000;padding: 10px 30px 10px 30px;top:40%;background: url(\''+application.basePath+'images/loading_bg.png\') repeat;border:1px solid #279DE5;border-top:10px solid #279DE5;"><table><tr height="25px"><td align="center" vertical-align="center" style="font-size:14px;font-weight:bold;">审核执行中...</td></tr><tr height="20px"><td align="center" vertical-align="center"><img src="'+application.basePath+'images/loading.gif"/></td></tr></table></div>';
					$('body').append(noHtml);
					var showLoad = $("#showLoadNotice");
					showLoad.css({"left":((($(window).width() - showLoad.width()) / 2 - 100)+ "px"),"top":((getScrollTop() + 220) +"px")});
				},
				success : function(data){
					
					if( data.msg=="success" ){
						$.alert("审核状态更改成功！");
					}
					else{
						$.alert("状态更改失败，请稍后重试！");
					}
					clear();
					$("tr[name='reason_tr']").hide();
				}
			});
			
		}
	});
	$("#nopass_cancel").click(function(){
		$("tr[name='reason_tr']").hide();
	});
	
	var value = 0;
	 $( "#dialog" ).dialog({
		 autoOpen: false,
		 height:550,
		 width:750,
		 buttons: {
			 "旋转90°": function() {
				 value +=90;
				 $("#dialog").find("img").rotate({ animateTo:value});

		        },
	        "旋转180°": function() {
				 value +=180;
				 $("#dialog").find("img").rotate({ animateTo:value});

		        }
		 }
	 });
	 $("#cBacka,#cFronta").click(function(){
		 value = 0;
		 imgTodialog(this);
	 });
	 $(document).on("click",'#aCIH',function(){
		 value = 0;
		 imgTodialog(this);
	 });
	 
	//点击纠错
	$("#update").click(function(){
		$("#info_table input[id!=device_number]").removeAttr("readonly");
		$("#info_table input[id!=device_number]").css("backgroundColor", "#ffffff");
	});
	
});

function getScrollTop(){
	var D = document; 
	return Math.max(D.body.scrollTop, D.documentElement.scrollTop)
}

function clear(){
	$("#order_id").val("");
	$("#identity").val("");
	$("#phone").val("");
	$("#workid").val("");
	$("#cert_name").val("");
	$("#nation").val("");
	$("#sex").val("");
	$("#birthday").val("");
	$("#cert_addr").val("");
	$("#cert_id").val("");
	$("#exp_date").val("");
	$("#device_number").val("");
	$("#nopass_reason").val("");
	$("#card_front").attr( "src", fullPath+"images/card_front.png" );
	$("#card_back").attr( "src", fullPath+"images/card_reverse.png" );
	//$("#card_in_hand").attr( "src", fullPath+"images/photo.png" );
	$("#card_in_hand_td").html("<h2>手持身份证</h2>");
	var selectedId = $("#row_id").val();
	 $("#rlist").jqGrid("delRowData", selectedId);
}

function addMask (){
	var w = $(window).width();
	var h = $(document).height();
	var maskDivHtml = "<div id='maskDivNotice'  style='cursor: pointer;position:fixed; _position:absolute; top:0px;left:0px;width:"+w+"px;height:"+h+"px;opacity:0.45;background:none repeat scroll 0 0 #000000;z-index: 90009;filter: progid:DXImageTransform.Microsoft.Alpha(opacity=45)'></div>";
	var maskDivNotice = $("#maskDivNotice");
	if(maskDivNotice.length > 0){
		maskDivNotice.remove();
	}
	$('body').append(maskDivHtml);
	
};

function selectRowData(id){
	//var a = gjsonData;
	//var audit = gjsonData.rows[id-1];
	$("#row_id").val(id);
	var audit = $('#rlist').jqGrid('getRowData',id);
	$("#cert_name").val(audit.cert_name);
	$("#nation").val(audit.nation);
	$("#sex").val(audit.sex);
	$("#birthday").val(audit.birthday);
	$("#cert_addr").val(audit.cert_addr);
	$("#cert_id").val(audit.cert_id);
	$("#exp_date").val(audit.exp_date);
	$("#device_number").val(audit.device_number);
	$("#order_id").val(audit.order_id);
	
	$("#card_front").attr( "src", fullPath+"authority/realNameCheck/getCardPhoto/card_front/"+audit.order_id );

	$("#card_back").attr( "src", fullPath+"authority/realNameCheck/getCardPhoto/card_back/"+audit.order_id );
	 
	$("#card_in_hand_td").html( '<a id="aCIH" rel="group" href="javascript:;"><img src="'+fullPath+'authority/realNameCheck/getCardPhoto/card_in_hand/'+audit.order_id+'" width="340" height="216"/></a>' );
	$.ajax({
		type : "GET",
		url : fullPath+'authority/realNameCheck/getCardPhoto/card_in_hand/'+audit.order_id,
		dataType : 'html',
		beforeSend : function(){
			addMask();
			var noHtml = '<div id="showLoadNotice" class="" style="display:block; position: absolute;z-index: 91000;padding: 10px 30px 10px 30px;top:40%;background: url(\''+application.basePath+'images/loading_bg.png\') repeat;border:1px solid #279DE5;border-top:10px solid #279DE5;"><table><tr height="25px"><td align="center" vertical-align="center" style="font-size:14px;font-weight:bold;">身份证图片加载中...</td></tr><tr height="20px"><td align="center" vertical-align="center"><img src="'+application.basePath+'images/loading.gif"/></td></tr></table></div>';
			$('body').append(noHtml);
			var showLoad = $("#showLoadNotice");
			showLoad.css({"left":((($(window).width() - showLoad.width()) / 2 - 100)+ "px"),"top":((getScrollTop() + 220) +"px")});
		},
		success : function(data){
			//do nothing
		}
	});
	$("#info_table input").css("backgroundColor", "#d9d9d9");
	$("#info_table input").attr("readonly","readonly");
}
//将图片路径传入jqueryUI的弹出框中
function imgTodialog(obj){
	 var diaimg = '<img>';
	 var imgsrc = $(obj).children("img").attr("src");
	 var d = $("#dialog");
	 var a = $(diaimg);
	 a.attr("src", imgsrc);
	 d.empty();
	 d.append(a[0]);
	 d.dialog( "open" );
}
// VERSION: 2.3 LAST UPDATE: 11.07.2013
/* 
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 * 
 * Made by Wilq32, wilq32@gmail.com, Wroclaw, Poland, 01.2009
 * Website: http://code.google.com/p/jqueryrotate/ 
 */
(function(k){for(var d,f,l=document.getElementsByTagName("head")[0].style,h=["transformProperty","WebkitTransform","OTransform","msTransform","MozTransform"],g=0;g<h.length;g++)void 0!==l[h[g]]&&(d=h[g]);d&&(f=d.replace(/[tT]ransform/,"TransformOrigin"),"T"==f[0]&&(f[0]="t"));eval('IE = "v"=="\v"');jQuery.fn.extend({rotate:function(a){if(0!==this.length&&"undefined"!=typeof a){"number"==typeof a&&(a={angle:a});for(var b=[],c=0,d=this.length;c<d;c++){var e=this.get(c);if(e.Wilq32&&e.Wilq32.PhotoEffect)e.Wilq32.PhotoEffect._handleRotation(a);
else{var f=k.extend(!0,{},a),e=(new Wilq32.PhotoEffect(e,f))._rootObj;b.push(k(e))}}return b}},getRotateAngle:function(){for(var a=[],b=0,c=this.length;b<c;b++){var d=this.get(b);d.Wilq32&&d.Wilq32.PhotoEffect&&(a[b]=d.Wilq32.PhotoEffect._angle)}return a},stopRotate:function(){for(var a=0,b=this.length;a<b;a++){var c=this.get(a);c.Wilq32&&c.Wilq32.PhotoEffect&&clearTimeout(c.Wilq32.PhotoEffect._timer)}}});Wilq32=window.Wilq32||{};Wilq32.PhotoEffect=function(){return d?function(a,b){a.Wilq32={PhotoEffect:this};
this._img=this._rootObj=this._eventObj=a;this._handleRotation(b)}:function(a,b){this._img=a;this._onLoadDelegate=[b];this._rootObj=document.createElement("span");this._rootObj.style.display="inline-block";this._rootObj.Wilq32={PhotoEffect:this};a.parentNode.insertBefore(this._rootObj,a);if(a.complete)this._Loader();else{var c=this;jQuery(this._img).bind("load",function(){c._Loader()})}}}();Wilq32.PhotoEffect.prototype={_setupParameters:function(a){this._parameters=this._parameters||{};"number"!==
typeof this._angle&&(this._angle=0);"number"===typeof a.angle&&(this._angle=a.angle);this._parameters.animateTo="number"===typeof a.animateTo?a.animateTo:this._angle;this._parameters.step=a.step||this._parameters.step||null;this._parameters.easing=a.easing||this._parameters.easing||this._defaultEasing;this._parameters.duration=a.duration||this._parameters.duration||1E3;this._parameters.callback=a.callback||this._parameters.callback||this._emptyFunction;this._parameters.center=a.center||this._parameters.center||
["50%","50%"];this._rotationCenterX="string"==typeof this._parameters.center[0]?parseInt(this._parameters.center[0],10)/100*this._imgWidth*this._aspectW:this._parameters.center[0];this._rotationCenterY="string"==typeof this._parameters.center[1]?parseInt(this._parameters.center[1],10)/100*this._imgHeight*this._aspectH:this._parameters.center[1];a.bind&&a.bind!=this._parameters.bind&&this._BindEvents(a.bind)},_emptyFunction:function(){},_defaultEasing:function(a,b,c,d,e){return-d*((b=b/e-1)*b*b*b-
1)+c},_handleRotation:function(a,b){d||this._img.complete||b?(this._setupParameters(a),this._angle==this._parameters.animateTo?this._rotate(this._angle):this._animateStart()):this._onLoadDelegate.push(a)},_BindEvents:function(a){if(a&&this._eventObj){if(this._parameters.bind){var b=this._parameters.bind,c;for(c in b)b.hasOwnProperty(c)&&jQuery(this._eventObj).unbind(c,b[c])}this._parameters.bind=a;for(c in a)a.hasOwnProperty(c)&&jQuery(this._eventObj).bind(c,a[c])}},_Loader:function(){return IE?function(){var a=
this._img.width,b=this._img.height;this._imgWidth=a;this._imgHeight=b;this._img.parentNode.removeChild(this._img);this._vimage=this.createVMLNode("image");this._vimage.src=this._img.src;this._vimage.style.height=b+"px";this._vimage.style.width=a+"px";this._vimage.style.position="absolute";this._vimage.style.top="0px";this._vimage.style.left="0px";this._aspectW=this._aspectH=1;this._container=this.createVMLNode("group");this._container.style.width=a;this._container.style.height=b;this._container.style.position=
"absolute";this._container.style.top="0px";this._container.style.left="0px";this._container.setAttribute("coordsize",a-1+","+(b-1));this._container.appendChild(this._vimage);this._rootObj.appendChild(this._container);this._rootObj.style.position="relative";this._rootObj.style.width=a+"px";this._rootObj.style.height=b+"px";this._rootObj.setAttribute("id",this._img.getAttribute("id"));this._rootObj.className=this._img.className;for(this._eventObj=this._rootObj;a=this._onLoadDelegate.shift();)this._handleRotation(a,
!0)}:function(){this._rootObj.setAttribute("id",this._img.getAttribute("id"));this._rootObj.className=this._img.className;this._imgWidth=this._img.naturalWidth;this._imgHeight=this._img.naturalHeight;var a=Math.sqrt(this._imgHeight*this._imgHeight+this._imgWidth*this._imgWidth);this._width=3*a;this._height=3*a;this._aspectW=this._img.offsetWidth/this._img.naturalWidth;this._aspectH=this._img.offsetHeight/this._img.naturalHeight;this._img.parentNode.removeChild(this._img);this._canvas=document.createElement("canvas");
this._canvas.setAttribute("width",this._width);this._canvas.style.position="relative";this._canvas.style.left=-this._img.height*this._aspectW+"px";this._canvas.style.top=-this._img.width*this._aspectH+"px";this._canvas.Wilq32=this._rootObj.Wilq32;this._rootObj.appendChild(this._canvas);this._rootObj.style.width=this._img.width*this._aspectW+"px";this._rootObj.style.height=this._img.height*this._aspectH+"px";this._eventObj=this._canvas;for(this._cnv=this._canvas.getContext("2d");a=this._onLoadDelegate.shift();)this._handleRotation(a,
!0)}}(),_animateStart:function(){this._timer&&clearTimeout(this._timer);this._animateStartTime=+new Date;this._animateStartAngle=this._angle;this._animate()},_animate:function(){var a=+new Date,b=a-this._animateStartTime>this._parameters.duration;if(b&&!this._parameters.animatedGif)clearTimeout(this._timer);else{if(this._canvas||this._vimage||this._img)a=this._parameters.easing(0,a-this._animateStartTime,this._animateStartAngle,this._parameters.animateTo-this._animateStartAngle,this._parameters.duration),
this._rotate(~~(10*a)/10);this._parameters.step&&this._parameters.step(this._angle);var c=this;this._timer=setTimeout(function(){c._animate.call(c)},10)}this._parameters.callback&&b&&(this._angle=this._parameters.animateTo,this._rotate(this._angle),this._parameters.callback.call(this._rootObj))},_rotate:function(){var a=Math.PI/180;return IE?function(a){this._angle=a;this._container.style.rotation=a%360+"deg";this._vimage.style.top=-(this._rotationCenterY-this._imgHeight/2)+"px";this._vimage.style.left=
-(this._rotationCenterX-this._imgWidth/2)+"px";this._container.style.top=this._rotationCenterY-this._imgHeight/2+"px";this._container.style.left=this._rotationCenterX-this._imgWidth/2+"px"}:d?function(a){this._angle=a;this._img.style[d]="rotate("+a%360+"deg)";this._img.style[f]=this._parameters.center.join(" ")}:function(b){this._angle=b;b=b%360*a;this._canvas.width=this._width;this._canvas.height=this._height;this._cnv.translate(this._imgWidth*this._aspectW,this._imgHeight*this._aspectH);this._cnv.translate(this._rotationCenterX,
this._rotationCenterY);this._cnv.rotate(b);this._cnv.translate(-this._rotationCenterX,-this._rotationCenterY);this._cnv.scale(this._aspectW,this._aspectH);this._cnv.drawImage(this._img,0,0)}}()};IE&&(Wilq32.PhotoEffect.prototype.createVMLNode=function(){document.createStyleSheet().addRule(".rvml","behavior:url(#default#VML)");try{return!document.namespaces.rvml&&document.namespaces.add("rvml","urn:schemas-microsoft-com:vml"),function(a){return document.createElement("<rvml:"+a+' class="rvml">')}}catch(a){return function(a){return document.createElement("<"+
a+' xmlns="urn:schemas-microsoft.com:vml" class="rvml">')}}}())})(jQuery);