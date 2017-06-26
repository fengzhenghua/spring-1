var ap_id = "";
var apc_rest_host = "";
var oper_no = "";
var tele_type="4G";
var order_source = "300";
var order_id = "";
var tariffInfoList = [];
var developer_list = [];
var length = 0;
var idx = 0;
var reward_oper_no="";
var ap_type="";
var fee_info=[];
var activity_id = "";
var good_num_flag = "";
var receive_area_flag = "";
var total_fee=0;

$(document).ready(function () {
	apc_rest_host = $('#apc_rest_host').val();
	ap_id = $('#ap_id').val();
	oper_no = $('#oper_no').val();
	reward_oper_no=isNullUtil("1",$('#reward_oper_no').val());
	ap_type=isNullUtil("1",$('#ap_type').val());
	
	$("#next_flow_step").unbind().bind("click", createOrderInfo);

	//查询资费
	getTariffInfo();

	//初始化资费信息
	initTariffInfo(length);
	

});

//初始化资费信息
function initTariffInfo(length){//len表示传入的li的个数
	//left-left li移入移出特效
	$('#tariff_info').on('click','.l-li',function(){
		var $this=$(this);
		idx = $this.attr('idx')
		
		//替换资费信息和图片
		$('.lr-img').attr('src',tariffInfoList[idx].pic_url);
		$('#rent').text(tariffInfoList[idx].tariff_show_detail.rent);
		$('#province_flow_charge').text(tariffInfoList[idx].tariff_show_detail.province_flow_charge);
		$('#country_flow_charge').text(tariffInfoList[idx].tariff_show_detail.country_flow_charge);
		$('#province_minutes_charge').text(tariffInfoList[idx].tariff_show_detail.province_minutes_charge);
		$('#country_minutes_charge').text(tariffInfoList[idx].tariff_show_detail.country_minutes_charge);
		$('#province_free_minutes').text(tariffInfoList[idx].tariff_show_detail.province_free_minutes);
		$('#country_free_minutes').text(tariffInfoList[idx].tariff_show_detail.country_free_minutes);
		$('#province_free_flow').text(tariffInfoList[idx].tariff_show_detail.province_free_flow);
		$('#country_free_flow').text(tariffInfoList[idx].tariff_show_detail.country_free_flow);
		$('#other_info').html(tariffInfoList[idx].tariff_show_detail.other_info);
//		changeFeeInfo(idx);//替换资费详情类容
		$this.css('border','1px solid #f2594b');
		$this.css('cursor','default');
		$this.siblings().css('border','1px solid #eee');
		$this.find('.l-top i').css('background-image','url(../../images/pc/oppoCard/open_card_chip.png)');
		$this.siblings().find('.l-top i').css('background-image','url(../../images/pc/oppoCard/open_card_chip_un.png)');
		if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE6.0")
		{
			$this.find('.l-top i').css('filter','progid:DXImageTransform.Microsoft.AlphaImageLoader(src="../../images/pc/oppoCard/open_card_chip.png",sizingMethod="scale")');
			$this.siblings().find('.l-top i').css('filter','progid:DXImageTransform.Microsoft.AlphaImageLoader(src="../../images/pc/oppoCard/open_card_chip_un.png",sizingMethod="scale")');
		}
		if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE7.0")
		{
			$this.find('.l-top i').css('filter','progid:DXImageTransform.Microsoft.AlphaImageLoader(src="../../images/pc/oppoCard/open_card_chip.png",sizingMethod="scale")');
			$this.siblings().find('.l-top i').css('filter','progid:DXImageTransform.Microsoft.AlphaImageLoader(src="../../images/pc/oppoCard/open_card_chip_un.png",sizingMethod="scale")');
		}
		if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE8.0")
		{
			$this.find('.l-top i').css('filter','progid:DXImageTransform.Microsoft.AlphaImageLoader(src="../../images/pc/oppoCard/open_card_chip.png",sizingMethod="scale")');
			$this.siblings().find('.l-top i').css('filter','progid:DXImageTransform.Microsoft.AlphaImageLoader(src="../../images/pc/oppoCard/open_card_chip_un.png",sizingMethod="scale")');
		}
		if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE9.0")
		{
			$this.find('.l-top i').css('filter','progid:DXImageTransform.Microsoft.AlphaImageLoader(src="../../images/pc/oppoCard/open_card_chip.png",sizingMethod="scale")');
			$this.siblings().find('.l-top i').css('filter','progid:DXImageTransform.Microsoft.AlphaImageLoader(src="../../images/pc/oppoCard/open_card_chip_un.png",sizingMethod="scale")');
		}
		$this.find('.l-bottom').css('color','#d94032');
		$this.siblings().find('.l-bottom').css('color','#000');
		
	});

	//点击左移
	var count = 0;//计数器
	var liArr = $('.lt-ul li');
	var len = Math.floor(length / 5);	//length=7,len = 1每5个li翻一页
	var ml;
	if((length % 5) == 0){//当有5的倍数个的资费时
		$('.you').on('click',function(){
			if(parseInt($('.lt-ul').css('margin-left')) < parseInt($('.lt-ul').css('width'))){
				count++;//计数器
				if(count < len){//鼠标点击的次数要小于或者等于总的li的页数（此处的页数为1）
					ml = count * 545;//翻一页要左移-545px,93*5+16*5
				}
				if(count >= len){
					count = len - 1;
				};
				ml = count * 545;//翻一页要左移-545px,93*5+16*5
			    //ie7
			    if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE7.0")
			    {
			    	ml = count * 540;//90*5+16*5
			    }
				$('.lt-ul').css('margin-left','-'+ml+'px');
			};
		});
	}
	if((length % 5) != 0){//当没有5的倍数个的资费时
		$('.you').on('click',function(){
			if(parseInt($('.lt-ul').css('margin-left')) < parseInt($('.lt-ul').css('width'))){
				count++;//计数器
				if(count <= len){//鼠标点击的次数要小于或者等于总的li的页数（此处的页数为1）
					 ml = count * 545;//翻一页要左移-545px,93*5+16*5
					 //ie7
					 if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE7.0")
					 {
						 ml = count * 540;//90*5+16*5
					 }
				}else{
					count = len;
				};
				$('.lt-ul').css('margin-left','-'+ml+'px');
			};
		});
	}


	//点击右移
	$('.zuo').on('click',function(){
		if(count !=0 && parseInt($('.lt-ul').css('margin-left')) != 0){
			count--;
			if(count == 0){
				ml = 0;
				if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE7.0")
				 {
					ml = 0;
				 }
			}else{
				ml = parseInt($('.lt-ul').css('margin-left')) + count * 545;
				if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE7.0")
				 {
					ml = parseInt($('.lt-ul').css('margin-left')) + count * 540;
				 }
			}
			$('.lt-ul').css('margin-left',ml+'px');
		};
	});
	
};


//保存参数信息到触点订单属性表
function saveDataToApOrderAttr(){
	var data = {
			"order_id":order_id,
			"product_id":tariffInfoList[idx].product_id,
			"product_name":tariffInfoList[idx].tariff_name,
			"market_price":total_fee,
			"fee_code":tariffInfoList[idx].fee_item_code,
			"fee_class":tariffInfoList[idx].fee_item_type,
			"fee_name":tariffInfoList[idx].fee_item_name,
			"ap_id":ap_id,
			"fee_info":JSON.stringify(fee_info),
			"reward_oper_no":reward_oper_no,
			"ap_type":ap_type,
			"activity_id":activity_id,
			"good_num_flag":good_num_flag,
			"receive_area_flag":receive_area_flag,
			"developer_list":JSON.stringify(developer_list)
	};
	
	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoOrderServiceRest/updateOppoOrderInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				window.location.href = "base.jsp?order_id="+order_id;
			} else {
				$.message.error('创建触点订单失败');
			}
		},
		error: function(data) {
			$.message.error('创建订单Ajax请求失败!');
		},
		complete:function(){
			$.loading.hide();
		}
	});
};

/**
 * 创建订单
 */
function createOrderInfo(){
	//总费用以及费用明细
	var fee_list=tariffInfoList[idx].fee_list;
//	var total_fee=0;
//	var fee_info=[];
	for(var i=0;i<fee_list.length;i++){
		total_fee=total_fee+parseFloat(fee_list[i].total_fee);
		var fee_result={
				"fee_id":fee_list[i].fee_item_code,
				"fee_category":fee_list[i].fee_item_type,
				"orig_fee":parseFloat(fee_list[i].total_fee)*100+"",
				"relief_fee":"0.00",
				"relief_result":"无",
				"real_fee":parseFloat(fee_list[i].total_fee)*100+"",
				"fee_des":fee_list[i].fee_item_name
		};
		fee_info.push(fee_result);
	}
	activity_id=isNullUtil("1",tariffInfoList[idx].activity_id);
	good_num_flag=isNullUtil("1",tariffInfoList[idx].good_num_flag);
	receive_area_flag=isNullUtil("1",tariffInfoList[idx].receive_area_flag);
	
	var dataJson={
			oper_no      : oper_no,
			tele_type    : tele_type,
			order_source : order_source
	};

	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/oppoOrderServiceRest/createOppoOrderInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:dataJson,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				order_id=data.args.order_id;
				//保存参数信息到触点订单属性表
				saveDataToApOrderAttr();
//				window.location.href = "base.jsp?order_id="+order_id+"&oper_no="+oper_no+"&tele_type="+tele_type+"&product_id="
//				+tariffInfoList[idx].product_id+"&product_name="+tariffInfoList[idx].tariff_name+"&market_price="+total_fee
//				+"&fee_code="+tariffInfoList[idx].fee_item_code+"&fee_class="+tariffInfoList[idx].fee_item_type
//				+"&fee_name="+tariffInfoList[idx].fee_item_name+"&ap_id="+ap_id+"&fee_info="+encodeURIComponent(JSON.stringify(fee_info))
//				+"&reward_oper_no="+reward_oper_no+"&ap_type="+ap_type+"&activity_id="+activity_id+"&good_num_flag="+good_num_flag
//				+"&receive_area_flag="+receive_area_flag+"&developer_list="+encodeURIComponent(JSON.stringify(developer_list));
			} 
			else {
				$.message.error('创建订单失败!');//dj
			}
		},
		error: function(data) {
			$.message.error('创建订单Ajax请求失败!');//dj
		},
		complete:function(){
			$.loading.hide();//dj
		}
	});
};

/**
 * 取资费
 */
function getTariffInfo(){

	var data = {
			"ap_id":ap_id
	};

	$.ajax({
		type: "post",
		url: apc_rest_host + "rest/ap/queryApMsg",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				tariffInfoList = data.args.tariff_info_list;
				length = tariffInfoList.length;
				
				if(length > 0){
					$('.lr-img').attr('src',tariffInfoList[idx].pic_url);
					$('#rent').text(tariffInfoList[idx].tariff_show_detail.rent);
					$('#province_flow_charge').text(tariffInfoList[idx].tariff_show_detail.province_flow_charge);
					$('#country_flow_charge').text(tariffInfoList[idx].tariff_show_detail.country_flow_charge);
					$('#province_minutes_charge').text(tariffInfoList[idx].tariff_show_detail.province_minutes_charge);
					$('#country_minutes_charge').text(tariffInfoList[idx].tariff_show_detail.country_minutes_charge);
					$('#province_free_minutes').text(tariffInfoList[idx].tariff_show_detail.province_free_minutes);
					$('#country_free_minutes').text(tariffInfoList[idx].tariff_show_detail.country_free_minutes);
					$('#province_free_flow').text(tariffInfoList[idx].tariff_show_detail.province_free_flow);
					$('#country_free_flow').text(tariffInfoList[idx].tariff_show_detail.country_free_flow);
					$('#other_info').html(tariffInfoList[idx].tariff_show_detail.other_info);
				}

				//动态添加套餐
				var li = "";
				var pic_url = "";
				for(var i=0;i<tariffInfoList.length;i++){
					if(tariffInfoList[i].pic_url == null || tariffInfoList[i].pic_url == ""){
						pic_url = "../../images/pc/oppoCard/del1.png";
					}
					else{
						pic_url = tariffInfoList[i].pic_url;
					}
					if(i==0){
						li = li +'<li class=" l l-li l-ie6 l-border"  title="'+tariffInfoList[i].tariff_name+'" idx="'+ i +'" data-src="'+pic_url+'">'
						+'<div class="l-top"><i class="i bg-i-r"></i></div>'
						+'<div class="l-bottom l-bottom-ie6">'+tariffInfoList[i].tariff_name+'</div></li>';
					}
					else{
						li = li +'<li class=" l l-li l-ie6"  title="'+tariffInfoList[i].tariff_name+'" idx="'+ i +'" data-src="'+pic_url+'">'
						+'<div class="l-top"><i class="i bg-i-h"></i></div>'
						+'<div class="l-bottom l-bottom-ie6">'+tariffInfoList[i].tariff_name+'</div></li>';
					}
				}
				$('#tariff_info').append(li);

				var str = $(".l-bottom").text().substr(0,5) + " ...";
			    $(".l-bottom").text(str);

			    //动态添加ul的宽度
				var length = $('#tariff_info li').length;
				var liWidth = $('.l-li').css('width');
				var marginRight = $('.l-li').css('margin-right');
				var ulWidth = ((parseInt(liWidth) + 2) + parseInt(marginRight)) * length;//5表示每5个显示一页
				$('#tariff_info').css('width',ulWidth+'px');

				//如果url中取不到工号，就取接口返回的绑定工号
				if(oper_no==null||oper_no==""||oper_no==undefined||oper_no=="null"||oper_no=="undefined"){
					oper_no=data.args.ap_info.bind_oper;
				}

				//可选发展人
				developer_list=data.args.developer_list;
			} else {
				/*alert("FAIL");*/
				$.message.error('FAIL');//dj
			}
		},
		error: function(data) {
			/*alert("ERROR");*/
			$.message.error('ERROR');//dj
		},
		complete:function(){
			$.loading.hide();//dj
		}
	});
};

