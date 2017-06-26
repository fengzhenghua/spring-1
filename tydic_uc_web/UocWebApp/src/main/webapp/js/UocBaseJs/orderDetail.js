var jsession_id='';
var rest_host='';
var order_no='';
var finish_flag='';
var query_type='';
var order_type='';
var orderData ="";
var tache_code='';
var cert_no='';
var cust_name='';
var proc_code_list='';
var first_month_rent='';
var cert_type='';
var card_kind_code_list='';
var order_list_flag = false;
var cert_info='';
var pay_type_code_list='';
var region_info='';
var selfGetChannel='';
var modiOrdType='';
var modiOrdState='';
var acceptSystem='';
var sale_order_no='';
var logisticsHtml='';
var commonInfo = {
		rest_host:"",
		jsession_id:"",
		order_no:"",
		pageType:""
};

$(document).ready(function() {
	commonInfo.jsession_id = HTML_UTLS.getParam("jsession_id");
	commonInfo.order_no = HTML_UTLS.getParam("order_no");
	commonInfo.pageType = "orderDetail";
	commonInfo.rest_host = window.parent.operInfo.rest_host;

	order_no = commonInfo.order_no;
	jsession_id = commonInfo.jsession_id;
	rest_host = commonInfo.rest_host;
	finish_flag = HTML_UTLS.getParam("finish_flag");
	tache_code = HTML_UTLS.getParam("tache_code");

	//产品
	proc_code_list = window.parent.Constant.prodCode();

	//首月计费方式
	first_month_rent =window.parent.Constant.first_month_rent();

	//证件类型
	cert_type = window.parent.Constant.certType();

	//卡类型
	card_kind_code_list = window.parent.Constant.card_kind();

	//支付方式
	pay_type_code_list = window.parent.Constant.payType();

	selfGetChannel = window.parent.Constant.selfGetChannel();
	modiOrdType = window.parent.Constant.modiOrdType();
	modiOrdState = window.parent.Constant.modiOrdState();
	acceptSystem = window.parent.Constant.acceptSystem();

	getRegionInfo();

	if(order_no.substring(0,1) == "1"){
		order_type = "100";
		query_type = "100";
    	getOrderDetail();
    	getProcViewInfo();
	}else if(order_no.substring(0,1) == "3"){
		order_type = "101";
		query_type = "101";
    	getOrderDetail();
    	getProcViewInfo();
	}
	// 展开/折叠 隐藏域
	$('.flow_log_head [control="foldable"]').on('click', function(e) {
		$('.flow_log_body [on_off="off"]').toggleClass('hide');
	});

	// 展开/折叠 订单列表
	$('.order_level1').on('click', 'b', function(e) {
		var $this = $(this);
		if ($this.attr('on_off') == 'on') {
			$this.text('▼');
			$this.attr('on_off', 'off');
			$this.siblings('div').hide();
		} else {
			$this.text('▲');
			$this.attr('on_off', 'on');
			$this.siblings('div').show();
			var otherItem = $this.parent().siblings('div').children('b:first');
			if (otherItem.attr('on_off') == 'on') {
				otherItem.click();
			}
		}
	});

	// 打开 分配-弹出页面
	$('#sendGoodsAgainBtn').on('click', function(e) {
		$.stopPropagation(e);
		$('.pop_container').hide();
		$('#popSendGoods').show();
	});

	$("#logisticsInfo").hide();
	$("#live_check").hide();
	$("#select_order_no").html(order_no);
    $("#master_service").html("");
    $(".order_level1").on("click",'span',function(){
    	$("#logisticsInfo").hide();
		$("#live_check").hide();
    	order_no = $(this).text();
    	//alert(order_no);
    	if(order_no.substring(0,1) != "2"){
    		$("#select_order_no").html(order_no);
        	$(this).parents('.order_level1').find('span').removeClass('active');
        	$(this).addClass("active");
    	}
    	if(order_no.substring(0,1) == "1"){
    		query_type = "100";
    		order_type = "100";
        	getOrderDetail();
        	getProcViewInfo();
    	}else if(order_no.substring(0,1) == "3"){
    		query_type = "101";
    		order_type = "101";
        	getOrderDetail();
        	getProcViewInfo();
    	}
    });

  //点击图片事件
	$('#thumbnailList').on('click', 'li', function(e) {
		var $this = $(this);
		if ($this.attr('type') == 'video') {
			$('#photoBox').hide();
			$('#videoBox').show();
			//video动态添加样式
			$('.video-js .vjs-tech').css('position','relative');
			$('.vjs-poster').css('display','none');
		}else{
			$('#photoBox').show();
			$('#videoBox').hide();
			$('#photoBox img').attr("src", $this.find("img").attr("src"));
		}

	});

    getSFLogisticsRouterInfo();
});

function getOrderDetail(){
	$.ajax({
        type: "post",
        //TODO fengzhenghua
        url: rest_host+"rest/ordModServiceRest/getOrderDetail",
        //url: rest_host+"rest/systemServiceRest/getOrderDetailES",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        async:true,
        data: {
        	"jsession_id":jsession_id,
			"order_no":order_no,
			"finish_flag":finish_flag,
			"query_type":query_type
        },
        dataType: "json",
        crossDomain: true == !(document.all),
        beforeSend: function() {
			$.loading.show("正在加载");
		},
        success: function(data){
        	if("0000" == data.respCode){
        		 if(data.args != null){
                	 orderData = data.args.json_info;
                	 getOrderList();
                	 getOrderInfo(query_type);
                	 //国政通加载身份证图片
                	 gztValid();
                	 //加载相片
                	 getCertInfo();
                 }
        	}
        },
        error:function(message){
			$.message.error("订单查询失败!");
        },
		complete: function(){
			$.loading.hide();
		}
	});
}
function getProcViewInfo(){
	$('.detail_area div.flow_title').remove();
	if(order_type=="101"&&(tache_code=="B00009"||INPUT_UTIL.isNull(tache_code))){
		tache_code="S00009";
	}else if(order_type=="100"&&(tache_code=="S00009"||INPUT_UTIL.isNull(tache_code))){
		tache_code="B00009";
	}

	var data = {
			jsession_id: jsession_id,
			order_no: order_no,
			tache_code: tache_code,
			finish_flag: finish_flag,
			order_type: order_type
		};

	$.ajax({
		type: "post",
		url: rest_host+"rest/procModService/getProcViewInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				var flow_title='',flow_chart='',flow_log_body='';

				flow_log_body='';

				if (data.args.proc_log_list != null&& data.args.proc_log_list.length>0) {
					$.each(data.args.proc_log_list, function(i, item) {
						if(i+1<data.args.proc_log_list.length){
							flow_log_body=flow_log_body+createProcLogHtml(item,i);
						}else if(i+1==data.args.proc_log_list.length){
							flow_log_body=flow_log_body+createProcLogHtml(item,i);
						}
						if(item.tache_code == "S00013"){
							if(item.deal_time != null && item.deal_time != ""){
								$("#logisticsInfo").show();
							}
						}else if(item.tache_code == "S00010" || item.tache_code == "S00012" || item.tache_code == "S00020"  || item.tache_code == "S00021" || item.tache_code == "S00022"){
							if(item.deal_time != null && item.deal_time != ""){
								$("#live_check").show();
							}
						}

					});
				}

				var currSeq_no='';
				if (data.args.proc_view_list!= null&& data.args.proc_view_list.length>0) {
					var width=200*data.args.proc_view_list.length-124;
					flow_chart='<div class="flow_chart">'
						+ '<ul class="flow_list grab" style="width:'+width+'px;">';

					if(order_type=="101"){
						$.each(data.args.proc_view_list, function(i, item) {
							if(item.tache_code==tache_code){
								currSeq_no=parseInt(item.seq_no);
								if(finish_flag!=1&&data.args.proc_view_list.length>=2&&currSeq_no>1&&!INPUT_UTIL.isNull(data.args.proc_view_list[currSeq_no-2].deal_time)){
									flow_title='<div class="flow_title">当前订单['+order_no+']已经进入'+item.tache_name+'处理'+$UTIL.timeTool.getTimeDiff(new Date(), stringToDate(data.args.proc_view_list[currSeq_no-2].deal_time))+'，请尽快处理！</div>';
								}else if(finish_flag!=1&&data.args.proc_view_list.length>=2&&currSeq_no>1&&!INPUT_UTIL.isNull(data.args.proc_view_list[currSeq_no-1].deal_time)){
									flow_title='<div class="flow_title">当前订单['+order_no+']已经进入'+item.tache_name+'处理'+$UTIL.timeTool.getTimeDiff(new Date(), stringToDate(data.args.proc_view_list[currSeq_no-1].deal_time))+'，请尽快处理！</div>';
								}
							}
						});

						var nextSeqNo="";
						$.each(data.args.proc_view_list, function(i, item) {
							var lastProcFlag=false;
							var dealFlag=false;
							var lastLogFlag=false;
							if(i+1==data.args.proc_view_list.length){
								lastProcFlag=true;
								if(finish_flag==1||data.args.proc_view_list[i].seq_no==currSeq_no){
									flow_title='<div class="flow_title">当前订单['+order_no+']已经处理完成</div>';
								}
							}
							if(item.seq_no == currSeq_no && tache_code != "S00009"){
								lastLogFlag=true;
							}
							if(item.seq_no <= currSeq_no){
								dealFlag=true;
							}
							if(item.tache_code == "S00009" && tache_code == "S00009"){
								dealFlag=true;
							}
							if(item.seq_no == currSeq_no && !lastLogFlag&&item.tache_code != "S00009"){
								return true;
							}

							if(i+1 < data.args.proc_view_list.length){
								nextSeqNo=parseInt(data.args.proc_view_list[i+1].seq_no);
							}
							if(item.seq_no == nextSeqNo&&dealFlag && INPUT_UTIL.isNull(item.deal_time) && item.tache_code != "S00009"){
								return true;
							}

							flow_chart=flow_chart+createProcHtml(item,i+1,dealFlag,lastLogFlag,lastProcFlag);
						});
					}else{
						$.each(data.args.proc_view_list, function(i, item) {
							var lastProcFlag=false;
							var dealFlag=false;
							var lastLogFlag=false;

							if(item.deal_time!=""){
								dealFlag=true;
							}
							if(i+1==data.args.proc_view_list.length){
								lastProcFlag=true;
							}
							if(item.deal_time==""&&data.args.proc_view_list[i-1].deal_time!=""&&tache_code!="B00009"){
								lastLogFlag=true;
								flow_title='<div class="flow_title">当前订单['+order_no+']已经进入'+item.tache_name+'处理'+$UTIL.timeTool.getTimeDiff(new Date(), stringToDate(data.args.proc_view_list[i-1].deal_time))+'，请尽快处理！</div>';
							}
							if(item.tache_code=="B00009"&&tache_code=="B00009"){
								dealFlag=true;
								flow_title='<div class="flow_title">当前订单['+order_no+']已经处理完成</div>';
							}

							flow_chart=flow_chart+createProcHtml(item,i+1,dealFlag,lastLogFlag,lastProcFlag);
						});
					}
				}

				if(flow_title==''){
					flow_title='<div class="flow_title">当前订单['+order_no+']还未处理，请尽快处理！</div>';
				}
				$('#proc_view_list').empty();
				$('#proc_log_list').empty();
				$('#proc_view_list').prepend(flow_title);
				$('#proc_view_list').append(flow_chart);
				$('#proc_log_list').append(flow_log_body);
			} else {
				$.message.error('获取流程图信息失败!'+data.content);
				$('#proc_view_list').empty();
				$('#proc_log_list').empty();
			}
		},
		error: function(data) {
			$.message.error('获取流程图信息Ajax请求失败!');
		},
		complete: function(){
			$.loading.hide();
			// 流程图左右拖拽事件
			var box = $('.flow_chart');
			var grab = $('.flow_chart .flow_list');
			var store = {move: false};
			grab.on('mousedown', function(e) {
				grab.removeClass('grab').addClass('grabbing');
				store.move = true;
				store.startX = e.pageX + box.scrollLeft();
				e.preventDefault();
			});
			grab.on('mousemove', function(e) {
				if (store.move == true){box.scrollLeft(store.startX - e.pageX);}
				e.preventDefault();
			});
			$(document).on('mouseup', function(e) {
				grab.removeClass('grabbing').addClass('grab');
				store.move = false;
				e.preventDefault();
			});
		}
	});
}

function createProcHtml(item,seq_no,dealFlag,lastLogFlag,lastProcFlag){
	var state=dealFlag?"done":"wait";

	var html= '<li class="flow_node '+(lastLogFlag?"doing":state)+'">'
			  + '<b>'+seq_no+'</b>'
			  + '<div>'
			  + '<p>'+item.tache_name+'</p>'
			  + ((dealFlag&&item.deal_time!="")?'<p>'+item.deal_time.substring(0,10)+'<br/>'+item.deal_time.substring(11)+'</p>':'')
			  + '</div>'
			  + '</li>'
			  + (lastProcFlag?'</ul></div>':'<li class="flow_arrow '+(lastLogFlag?"wait":state)+'"></li>');
	return html;
}

function createProcLogHtml(item,seqNo){
	var html='<li class="line'+(seqNo<3?'':' hide')+'" on_off='+(seqNo<3?'"on"':'"off"')+'>'
		+'	<div class="width20per">'+item.deal_time+'</div><div class="width15per">'+item.tache_name+'</div><div class="width50per">'+(item.deal_desc==null?"":item.deal_desc)+'</div><div class="width15per">'+item.oper_no+'</div>'
		+'</li>';
	return html;
}

//获取订单列表
function getOrderList(){
	if(order_list_flag){
		return;
	} else {
		$("#master_service").empty();
		 var ofrOrderServOrderHtml="";
		 if(orderData.infoSaleOrder != null){
			// $("#sale_order_no").html(orderData.infoSaleOrder.sale_order_no);
			 ofrOrderServOrderHtml +='<b on_off="on">▲</b><i class="icon_sale"></i><span id="sale_order_no">'+orderData.infoSaleOrder.sale_order_no+'</span>';
			  sale_order_no =orderData.infoSaleOrder.sale_order_no;
		 }
		 if(orderData.ofrOrderAndServiceOrderList != null && orderData.ofrOrderAndServiceOrderList.length > 0){
			 $.each(orderData.ofrOrderAndServiceOrderList,function(i,item){
				 if(item.serv_order_no != null && order_no == item.serv_order_no){
					 ofrOrderServOrderHtml +='<div class="order_level2" is_parent="true" is_loaded="true">'
	    				 +'<b on_off="on">▲</b><i class="icon_goods"></i><span id="'+item.ofr_order_no+'">'+item.ofr_order_no+'</span>'
	    				 +'<div class="order_level3" is_parent="false" is_loaded="true">'
	    				 +'<i class="icon_service"></i><span class="active" id="'+item.serv_order_no+'">'+item.serv_order_no+'</span></div>'
						 +'</div>';
				 }else{
					 ofrOrderServOrderHtml +='<div class="order_level2" is_parent="true" is_loaded="false">'
	    				 +'<b on_off="off">▼</b><i class="icon_goods"></i><span id="'+item.ofr_order_no+'">'+item.ofr_order_no+'</span>';
	    			 if(item.serv_order_no != null){
	    				 ofrOrderServOrderHtml +='<div class="order_level3" is_parent="false" is_loaded="false">'
	    					 +'<i class="icon_service"></i><span id="'+item.serv_order_no+'">'+item.serv_order_no+'</span></div>'
	    					 +'</div>';
	    			 }else{
	    				 ofrOrderServOrderHtml +='</div>';
	    			 }
				 }
			 });
		 }
		 $("#master_service").append(ofrOrderServOrderHtml);
		 order_list_flag = true;
	}
}

//获取XX订单信息
function getOrderInfo(query_type){
	if(orderData != null){
		var servOrderDetailHtml = "";
		var saleOrderDetailHtml = "";
		var ofrOrderDetailHtml = "";
		var headFirstHtml = '<ul class="detail_info_head">'
						+'<li class="line">'
						+'<span>';
		var headLatterHtml = '</span>'
						+'</li>'
						+'</ul>';
		var bodyFirstHtml = '<ul class="detail_info_body">'
							+'<li class="line">'
							+'<div class="one_third">';

		//服务订单优惠活动
		if(orderData.infoServiceOrderActivity != null){
    		var infoServiceOrderActivity = orderData.infoServiceOrderActivity;
    		servOrderDetailHtml += headFirstHtml + '优惠活动' + headLatterHtml + bodyFirstHtml + ''
    							+'活动ID：'+infoServiceOrderActivity.activity_id+'</div><div class="one_third">活动名称：'+(INPUT_UTIL.isNull(infoServiceOrderActivity.activity_name)?infoServiceOrderActivity.activity_id+"(活动ID)":infoServiceOrderActivity.activity_name)+'</div></li></ul>';
    	}
    	//服务订单费用
    	if(orderData.infoServiceOrderFeeList != null){
    		var infoServiceOrderFee = orderData.infoServiceOrderFeeList[0];
    		servOrderDetailHtml += headFirstHtml + '费用' + headLatterHtml + bodyFirstHtml + ''
    							+'费用项名称：'+infoServiceOrderFee.fee_item_name+'</div><div class="one_third">费用类型：'+infoServiceOrderFee.fee_item_type+'</div><div class="one_third">减免费用：'+infoServiceOrderFee.discount_fee+'</div></li>'
    							+'<li class="line">'
    							+'<div class="one_third">预收：'+infoServiceOrderFee.total_fee+'</div><div class="one_third">实收：'+infoServiceOrderFee.payed_fee+'</div><div class="one_third">减免描述：'+infoServiceOrderFee.discount_comments+'</div></li></ul>';
    	}
    	//服务订单收费
    	if(orderData.infoServiceOrderPayList != null){
    		var infoServiceOrderPay = orderData.infoServiceOrderPayList[0];
    		servOrderDetailHtml += headFirstHtml + '收费' + headLatterHtml + bodyFirstHtml + ''
    			+'付费类型：'+infoServiceOrderPay.pay_type+'</div><div class="one_third">缴费流水：'+infoServiceOrderPay.pay_sn+'</div><div class="one_third">收费员工工号：'+infoServiceOrderPay.pay_oper_no+'</div></li>'
    			+'<li class="line">'
    			+'<div class="one_third">预收：'+infoServiceOrderPay.total_fee+'</div><div class="one_third">减免：'+infoServiceOrderPay.discount_fee+'</div><div class="one_third">实收：'+infoServiceOrderPay.payed_fee+'</div></li></ul>';


    	}

    	//服务订单银行托收
    	if(orderData.infoServiceOrderCollectionList != null){
    		var infoServiceOrderCollection = orderData.infoServiceOrderCollectionList[0];
    		servOrderDetailHtml += headFirstHtml + '银行托收' + headLatterHtml + bodyFirstHtml + ''
    		+'账户ID：'+infoServiceOrderCollection.account_id+'</div><div class="one_third">开户银行编码：'+infoServiceOrderCollection.bank_code+'</div><div class="one_third">银行账号：'+infoServiceOrderCollection.bank_account+'</div></li>'
			+'<li class="line">'
			+'<div class="one_third">托收方式：'+infoServiceOrderCollection.collection_type+'</div><div class="one_third">托收协议号：'+infoServiceOrderCollection.collection_sn+'</div><div class="one_third">储值方式：'+infoServiceOrderCollection.save_type+'</div></li></ul>';


    	}

    	//服务订单担保人
    	if(orderData.infoServiceOrderGuarantorList != null){
    		var infoServiceOrderGuarantor = orderData.infoServiceOrderGuarantorList[0];
    		servOrderDetailHtml += headFirstHtml + '担保人' + headLatterHtml + bodyFirstHtml + ''
    							+'担保类型：'+infoServiceOrderGuarantor.guarant_type+'</div><div class="one_third">担保项目：'+infoServiceOrderGuarantor.guarant_project+'</div><div class="one_third">担保方式：'+infoServiceOrderGuarantor.guarant_method+'</div></li>'
    							+'<li class="line">'
    							+'<div class="one_third">担保期限：'+infoServiceOrderGuarantor.guarant_eff_time+'</div><div class="one_third">担保单号：'+infoServiceOrderGuarantor.guarant_sn+'</div><div class="one_third">担保单位编码：'+infoServiceOrderGuarantor.guarant_depart_code+'</div></li></ul>';

    	}

    	//服务订单属性
    	if(orderData.infoServiceOrderAttrList != null){
    		var infoServiceOrderAttr = orderData.infoServiceOrderAttrList[0];
    		//servOrderDetailHtml += headFirstHtml + '服务订单属性' + headLatterHtml + bodyFirstHtml + ''

    	}

    	//服务订单发展人
    	if(orderData.infoServiceOrderDeveloperList != null){
    		var infoServiceOrderDeveloper = orderData.infoServiceOrderDeveloperList[0];
    		var develop_type = infoServiceOrderDeveloper.develop_type==null?"":infoServiceOrderDeveloper.develop_type;
    		var develop_depart_name = infoServiceOrderDeveloper.develop_depart_name==null?"":infoServiceOrderDeveloper.develop_depart_name;
    		var develop_depart_id = infoServiceOrderDeveloper.develop_depart_id==null?"":infoServiceOrderDeveloper.develop_depart_id;
    		var develop_target_code = infoServiceOrderDeveloper.develop_target_code==null?"":infoServiceOrderDeveloper.develop_target_code;
    		servOrderDetailHtml += headFirstHtml + '发展人' + headLatterHtml + bodyFirstHtml + ''
    							+'发展人名称：'+infoServiceOrderDeveloper.developer_name+'</div><div class="one_third">发展人编码：'+infoServiceOrderDeveloper.developer_code+'</div><div class="one_third">发展类型：'+develop_type+'</div></li>'
    							+'<li class="line">'
    							+'<div class="one_third">发展渠道名称：'+develop_depart_name+'</div><div class="one_third">发展渠道编码：'+develop_depart_id+'</div><div class="one_third">发展对象编码：'+develop_target_code+'</div></li></ul>';

    	}

    	//服务订单基本信息
    	if(orderData.infoServiceOrderList != null){

    		var servOrder_all = orderData.infoServiceOrderList[0];

    		var servOrder_oper_code = servOrder_all.oper_code == null ? "" : servOrder_all.oper_code;
    		servOrder_oper_code = window.parent.Constant.operCode(servOrder_oper_code);
    		var servOrder_oper_name = servOrder_all.oper_name == null ? "" : servOrder_all.oper_name;
    		servOrder_oper_name = servOrder_oper_code;
    		var servOrder_acc_nbr = servOrder_all.acc_nbr == null ? "" : servOrder_all.acc_nbr;

    		var servOrder_cancle_flag = servOrder_all.cancle_flag == null ? "" : servOrder_all.cancle_flag;
    		servOrder_cancle_flag = servOrder_cancle_flag == "0" ? "已撤单" :"正常单";
    		var servOrder_order_state = servOrder_all.order_state == null ? "" : servOrder_all.order_state;
    		servOrder_order_state = window.parent.Constant.orderState(servOrder_order_state);
    		var servOrder_create_time = servOrder_all.create_time == null ? "" : servOrder_all.create_time;

    		var servOrder_finish_time = servOrder_all.finish_time == null ? "" : servOrder_all.finish_time;
    		var servOrder_proc_inst_id = servOrder_all.proc_inst_id == null ? "" : servOrder_all.proc_inst_id;
    		var servOrder_ord_mod_code = servOrder_all.ord_mod_code == null ? "" : servOrder_all.ord_mod_code;

    		var servOrder_accept_oper_no = servOrder_all.accept_oper_no == null ? "" : servOrder_all.accept_oper_no;
    		var servOrder_accept_oper_name = servOrder_all.accept_oper_name == null ? "" : servOrder_all.accept_oper_name;
    		var servOrder_accept_depart_no = servOrder_all.accept_depart_no == null ? "" : servOrder_all.accept_depart_no;

    		var servOrder_accept_depart_name = servOrder_all.accept_depart_name == null ? "" : servOrder_all.accept_depart_name;
    		var servOrder_accept_depart_type = servOrder_all.accept_depart_type == null ? "" : servOrder_all.accept_depart_type;
    		//服务订单基本信息
    		servOrderDetailHtml += headFirstHtml + '基本信息' + headLatterHtml + bodyFirstHtml + ''
								+'业务名称：'+servOrder_oper_name+'</div><div class="one_third">服务号码：'+servOrder_acc_nbr+'</div><div class="one_third">撤单标记：'+servOrder_cancle_flag+'</div></li>'
								+'<li class="line">'
								+'<div class="one_third">订购状态：'+servOrder_order_state+'</div><div class="one_third">创建时间：'+servOrder_create_time+'</div><div class="one_third">完工时间：'+servOrder_finish_time+'</div></li>'
								+'<li class="line">'
								+'<div class="one_third">受理工号：'+servOrder_accept_oper_no+'</div><div class="one_third">受理员工名称：'+servOrder_accept_oper_name+'</div></li>'
								+'<li class="line">'
								+'<div class="one_third">受理渠道编码：'+servOrder_accept_depart_no+'</div><div class="one_third">受理渠道名称：'+servOrder_accept_depart_name+'</div><div class="one_third">受理渠道类型：'+servOrder_accept_depart_type+'</div></li></ul>';

    	}

    	//服务订单产品列表
    	if(orderData.infoServiceOrderProdMapList != null){
    		var infoServiceOrderProdMap = orderData.infoServiceOrderProdMapList[0];
    		var proc_name = infoServiceOrderProdMap.prod_name==null?"":infoServiceOrderProdMap.prod_name;
    		var roam_level = infoServiceOrderProdMap.roam_level==null?"":infoServiceOrderProdMap.roam_level;
    		var call_level = infoServiceOrderProdMap.call_level==null?"":infoServiceOrderProdMap.call_level;
    		var proc_code = infoServiceOrderProdMap.prod_code==null?"":infoServiceOrderProdMap.prod_code;
    		var first_month_rent = infoServiceOrderProdMap.first_month_rent==null?"":infoServiceOrderProdMap.first_month_rent;
    		first_month_rent=window.parent.Constant.first_month_rent(first_month_rent);
    		$.each(proc_code_list,function(i,item){
    			if(infoServiceOrderProdMap.prod_code == item.code_id){
    				proc_name = item.code_name;
    			}
    		});
//    		$.each(first_month_rent,function(i,item){
//    			if(infoServiceOrderProdMap.first_month_rent == item.code_id){
//    				first_month_rent = item.code_name;
//    			}
//    		});

    		servOrderDetailHtml += headFirstHtml + '产品列表' + headLatterHtml + bodyFirstHtml + ''
    							+'产品名称：'+proc_name+'</div><div class="one_third">产品编码：'+proc_code+'</div><div class="one_third">首月计费方式：'+first_month_rent+'</div></li>'
    							+'<li class="line">'
    							+'<div class="one_third">漫游级别：'+roam_level+'</div><div class="one_third">呼叫级别：'+call_level+'</div></li></ul>';

    	}

    	//服务订单套餐
    	if(orderData.infoServiceOrderPackageList != null){
    		var infoServiceOrderPackage = orderData.infoServiceOrderPackageList[0];
    		servOrderDetailHtml += headFirstHtml + '套餐' + headLatterHtml + bodyFirstHtml + ''
    							+'套餐名称：'+infoServiceOrderPackage.pack_name+'</div><div class="one_third">套餐编码：'+infoServiceOrderPackage.pack_code+'</div></li>'
    							+'<li class="line">'
    							+'<div class="one_third">开始时间：'+infoServiceOrderPackage.begin_time+'</div><div class="one_third">结束时间：'+infoServiceOrderPackage.end_time+'</div></li></ul>';

    	}

    	//服务订单终端信息
    	if(orderData.infoServiceOrderTerminalList != null){
    		var infoServiceOrderTerminal = orderData.infoServiceOrderTerminalList[0];
    		servOrderDetailHtml += headFirstHtml + '终端信息' + headLatterHtml + bodyFirstHtml + ''
    							+'终端名称：'+infoServiceOrderTerminal.terminal_name+'</div><div class="one_third">终端编码：'+infoServiceOrderTerminal.terminal_code+'</div><div class="one_third">终端类型名称：'+infoServiceOrderTerminal.terminal_type_name+'</div></li>'
    							+'<li class="line">'
    							+'<div class="one_third">销售价：'+infoServiceOrderTerminal.sale_price+'</div><div class="one_third">成本价：'+infoServiceOrderTerminal.cost_price+'</div></li>';
    							+'<li class="line">'
    							+'<div class="one_third">品牌：'+infoServiceOrderTerminal.terminal_brand+'</div><div class="one_third">颜色：'+infoServiceOrderTerminal.terminal_color+'</div></li></ul>';

    	}

    	//服务订单协议
    	if(orderData.infoServiceOrderAgreementList != null){
    		var infoServiceOrderAgreement = orderData.infoServiceOrderAgreementList[0];
    		servOrderDetailHtml += headFirstHtml + '协议' + headLatterHtml + bodyFirstHtml + ''
    							+'协议ID：'+infoServiceOrderAgreement.agreement_id+'</div><div class="one_third">入网限定月份数：'+infoServiceOrderAgreement.limit_months+'</div><div class="one_third">入网限制类型：'+infoServiceOrderAgreement.limit_type+'</div></li>'
    							+'<li class="line">'
    							+'<div class="one_third">协议结束方式：'+infoServiceOrderAgreement.end_method+'</div><div class="one_third">担保方式：'+infoServiceOrderAgreement.guarant_type+'</div></li></ul>';

    	}

    	//服务订单SIM卡
    	if(orderData.InfoServiceOrderSimCardList != null){
    		var InfoServiceOrderSimCard = orderData.InfoServiceOrderSimCardList[0];
    		var card_fee = InfoServiceOrderSimCard.card_fee==null?"":InfoServiceOrderSimCard.card_fee;
    		var card_kind = InfoServiceOrderSimCard.card_type==null?"":InfoServiceOrderSimCard.card_type;
    		$.each(card_kind_code_list,function(i,item){
    			if(card_kind == item.code_id){
    				card_kind = item.code_name;
    			}
    		});
    		servOrderDetailHtml += headFirstHtml + 'SIM卡' + headLatterHtml + bodyFirstHtml + ''
    							+'SIMID：'+InfoServiceOrderSimCard.sim_id+'</div><div class="one_third">IMSI：'+InfoServiceOrderSimCard.imsi+'</div><div class="one_third">卡数据：</div></li>'
    							+'<li class="line">'
    							+'<div class="one_third">卡费：'+card_fee+'</div><div class="one_third">卡类型：'+card_kind+'</div><div class="one_third">写卡业务流水：'+InfoServiceOrderSimCard.card_data_proc_id+'</div></li></ul>';

    	}

    	//服务订单固网
    	if(orderData.infoServiceOrderFixList != null){
    		var infoServiceOrderFix = orderData.infoServiceOrderFixList[0];
    		servOrderDetailHtml += headFirstHtml + '固网' + headLatterHtml + bodyFirstHtml + ''
    							+'逻辑号码：'+infoServiceOrderFix.acc_nbr+'</div><div class="one_third">物理号码：'+infoServiceOrderFix.machine_nbr+'</div><div class="one_third">号码地域：'+infoServiceOrderFix.nbr_area_code+'</div></li>'
    							+'<li class="line">'
    							+'<div class="one_third">安装地址：'+infoServiceOrderFix.install_address+'</div><div class="one_third">标准地址编码：'+infoServiceOrderFix.stardand_addr_id+'</div></li></ul>';

    	}

    	//服务订单宽带
    	if(orderData.infoServiceOrderM165List != null){
    		var infoServiceOrderM165 = orderData.infoServiceOrderM165List[0];
    		servOrderDetailHtml += headFirstHtml + '宽带' + headLatterHtml + bodyFirstHtml + ''
    							+'宽带账号：'+infoServiceOrderM165.acc_nbr+'</div><div class="one_third">密码：'+infoServiceOrderM165.passwd+'</div><div class="one_third">宽带编码：'+infoServiceOrderM165.line_nbr+'</div></li>'
    							+'<li class="line">'
    							+'<div class="one_third">安装地址：'+infoServiceOrderM165.install_address+'</div><div class="one_third">接入方式：'+infoServiceOrderM165.accept_type+'</div><div class="one_third">速率：'+infoServiceOrderM165.speed+'</div></li></ul>';

    	}

    	//服务订单靓号
    	if(orderData.infoServiceOrderGoodNbrList != null){
    		var infoServiceOrderGoodNbr = orderData.infoServiceOrderGoodNbrList[0];
    		servOrderDetailHtml += headFirstHtml + '靓号' + headLatterHtml + bodyFirstHtml + ''
    							+'靓号级别：'+infoServiceOrderGoodNbr.nbr_level+'</div><div class="one_third">协议时长：'+infoServiceOrderGoodNbr.time_dur_pro+'</div></li>'
    							+'<li class="line">'
    							+'<div class="one_third">月承诺消费：'+infoServiceOrderGoodNbr.month_consume+'</div><div class="one_third">靓号预存：'+infoServiceOrderGoodNbrnbr_pre_save+'</div></li></ul>';

    	}

    	//服务订单拓展属性横表
    	if(orderData.infoServiceOrderExt != null){
    		var infoServiceOrderExt = orderData.infoServiceOrderExt;
    		if(!INPUT_UTIL.isNull(infoServiceOrderExt.ext_field_5)){
    			servOrderDetailHtml += headFirstHtml + '服务订单拓展属性横表' + headLatterHtml + bodyFirstHtml + ''
    			+'CB订单号：'+infoServiceOrderExt.ext_field_5+'</div></li></ul>';
    		}
    	}

    	//客户个人信息表
    	if(orderData.infoServiceOrderPersionList != null && orderData.infoServiceOrderPersionList.length>0){
    		var infoServiceOrderPersion = orderData.infoServiceOrderPersionList[0];
    		cert_info = infoServiceOrderPersion;
    		var cert_expire_date = infoServiceOrderPersion.cert_expire_date==null?"":infoServiceOrderPersion.cert_expire_date;
    		var cert_type_name = "";
    		$.each(cert_type,function(i,item){
    			if(infoServiceOrderPersion.cert_type == item.code_id){
    				cert_type_name = item.code_name;
    			}
    		});
    		servOrderDetailHtml += headFirstHtml + '客户信息' + headLatterHtml + bodyFirstHtml + ''
    							+'客户名称：'+infoServiceOrderPersion.cust_name+'</div><div class="one_third">联系电话：'+infoServiceOrderPersion.cust_phone+'</div><div class="one_third">地址：'+infoServiceOrderPersion.cust_address+'</div></li>'
    							+'<li class="line">'
    							+'<div class="one_third">证件类型：'+cert_type_name+'</div><div class="one_third">证件号码：'+infoServiceOrderPersion.cert_no+'</div><div class="one_third">证件地址：'+infoServiceOrderPersion.cert_address+'</div></li>'
    							+'<li class="line">'
    							+'<div class="one_third">证件有效期：'+cert_expire_date+'</div></li></ul>';
    	}

    	//审核状态
    	if(orderData.auditCondition != null){
    		var auditCondition = orderData.auditCondition;
    		servOrderDetailHtml += headFirstHtml + '审核状态' + headLatterHtml + bodyFirstHtml + ''
    							+'审核状态：'+auditCondition+'</div></li></ul>';
    	}

    	/*
    	 * 销售订单XXX
    	 */
    	if(orderData.infoSaleOrderPersion != null){
        	var infoSaleOrderPersion = orderData.infoSaleOrderPersion;
        	cert_info = infoSaleOrderPersion;
        	var cert_type_name = "";
        	var cert_expire_date = infoSaleOrderPersion.cert_expire_date==null?"":infoSaleOrderPersion.cert_expire_date;
    		$.each(cert_type,function(i,item){
    			if(infoSaleOrderPersion.cert_type == item.code_id){
    				cert_type_name = item.code_name;
    			}
    		});
        	saleOrderDetailHtml += headFirstHtml + '客户信息' + headLatterHtml + bodyFirstHtml + ''
        						+'客户名称：'+infoSaleOrderPersion.cust_name+'</div><div class="one_third">证件类型：'+cert_type_name+'</div><div class="one_third">证件号码：'+infoSaleOrderPersion.cert_no+'</div></li>'
        						+'<li class="line">'
        						+'<div class="one_third">联系电话：'+infoSaleOrderPersion.cust_phone+'</div><div class="one_third">地址：'+infoSaleOrderPersion.cust_address+'</div><div class="one_third">证件有效期：'+cert_expire_date+'</div></li>'
        						+'<li class="line">'
        						+'<div class="one_third">证件地址：'+infoSaleOrderPersion.cert_address+'</div></li></ul>';

        }

        if(orderData.infoSaleOrderFee != null){
        	var infoSaleOrderFee = orderData.infoSaleOrderFee;
        	saleOrderDetailHtml += headFirstHtml + '费用信息表' + headLatterHtml + bodyFirstHtml + ''
        						+'预收：'+infoSaleOrderFee.total_fee+'</div><div class="one_third">减免：'+infoSaleOrderFee.discount_fee+'</div><div class="one_third">实收：'+infoSaleOrderFee.payed_fee+'</div></li>'
        						+'<li class="line">'
        						+'<div class="one_third">收费时间：'+infoSaleOrderFee.pay_time+'</div><div class="one_third">收费标志：'+infoSaleOrderFee.pay_flag+'</div></li></ul>';

        }

       if(orderData.infoSaleOrderDistribution != null){
    	   var infoSaleOrderDistribution = orderData.infoSaleOrderDistribution;
    	   var pay_type_name = "";
    	   var post_province = "";
    	   var post_city = "";
    	   var post_area = "";
    	   var post_code = infoSaleOrderDistribution.post_code==null?"":infoSaleOrderDistribution.post_code;
    	   var self_chl_name = infoSaleOrderDistribution.self_chl_name==null?"":infoSaleOrderDistribution.self_chl_name;
    	   var distribution_begin_time = infoSaleOrderDistribution.distribution_begin_time==null?"":infoSaleOrderDistribution.distribution_begin_time;
    	   var distribution_end_time = infoSaleOrderDistribution.distribution_end_time==null?"":infoSaleOrderDistribution.distribution_end_time;
    	   $.each(pay_type_code_list,function(i,item){
    		   if(infoSaleOrderDistribution.pay_type == item.code_id){
    			   pay_type_name = item.code_name;
    		   }
    	   });
    	   if(infoSaleOrderDistribution.post_province == region_info.provinceCode){
    		   post_province = region_info.provinceName;
    	   }
    	   $.each(region_info.cityInfo,function(i,item){
    		   if(infoSaleOrderDistribution.post_city == item.cityRegionCode){
    			   post_city = item.cityRegionName;
    			   $.each(item.areaInfo,function(j,jtem){
    				  if(infoSaleOrderDistribution.post_area == jtem.areaRegionCode){
    					  post_area = jtem.areaRegionName;
    				  }
    			   });
    		   }
    	   });
    	   $.each(selfGetChannel,function(i,item){
    		  if(infoSaleOrderDistribution.self_chl_name == item.code_id){
    			  self_chl_name == item.code_name;
    		  }
    	   });
    	   saleOrderDetailHtml += headFirstHtml + '收件人信息' + headLatterHtml + bodyFirstHtml + ''
    	   					   +'收件人姓名：'+infoSaleOrderDistribution.contact_name+'</div><div class="one_third">收件人电话：'+infoSaleOrderDistribution.contact_tel+'</div><div class="one_third">支付方式：'+pay_type_name+'</div></li>'
    	   					   +'<li class="line">'
    	   					   +'<div class="one_third">省份：'+post_province+'</div><div class="one_third">地市：'+post_city+'</div><div class="one_third">区县：'+post_area+'</div></li>'
    	   					   +'<li class="line">'
    	   					   +'<div class="one_third">详细地址：'+infoSaleOrderDistribution.address+'</div><div class="one_third">邮政编码：'+post_code+'</div><div class="one_third">自提渠道：'+self_chl_name+'</div></li>'
							   +'<li class="line">'
							   +'<div class="one_third">配送开始时间：'+distribution_begin_time+'</div><div class="one_third">配送结束时间：'+distribution_end_time+'</div></li></ul>';

       }

       if(orderData.infoSaleOrderDistrDetailList != null){
    	   var infoSaleOrderDistrDetail = orderData.infoSaleOrderDistrDetailList[0];
    	   saleOrderDetailHtml += headFirstHtml + '包裹详细' + headLatterHtml + bodyFirstHtml + ''
    	   					   +'物品编码：'+infoSaleOrderDistrDetail.article_code+'</div><div class="one_third">设备串码：'+infoSaleOrderDistrDetail.article_imsi+'</div><div class="one_third">物品描述：'+infoSaleOrderDistrDetail.article_desc+'</div></li>'
    	   					   +'<li class="line">'
    	   					   +'<div class="one_third">物品名称：'+infoSaleOrderDistrDetail.article_name+'</div><div class="one_third">数量：'+infoSaleOrderDistrDetail.article_num+'</div></li></ul>';

       }

       if(orderData.infoSaleOrderEditList != null){
    	   var infoSaleOrderEdit = orderData.infoSaleOrderEditList[0];
    	   var edit_type = "";
    	   var edit_system = "";
    	   var state = "";
    	   var edit_desc = infoSaleOrderEdit.edit_desc==null?"":infoSaleOrderEdit.edit_desc;
    	   $.each(modiOrdType,function(i,item){
    		  if(infoSaleOrderEdit.edit_type == item.code_id ){
    			  edit_type = item.code_name;
    		  }
    	   });
    	   $.each(acceptSystem,function(i,item){
     		  if(infoSaleOrderEdit.edit_system == item.code_id ){
     			 edit_system = item.code_name;
     		  }
     	   });
    	   $.each(modiOrdState,function(i,item){
      		  if(infoSaleOrderEdit.state == item.code_id ){
      			state = item.code_name;
      		  }
      	   });
    	   saleOrderDetailHtml += headFirstHtml + '修订信息' + headLatterHtml + bodyFirstHtml + ''
    	   					   +'修订类型：'+edit_type+'</div><div class="one_third">状态：'+state+'</div><div class="one_third">修订系统：'+edit_system+'</div></li>'
    	   					   +'<li class="line">'
    	   					   +'<div class="one_third">修订工号：'+infoSaleOrderEdit.oper_no+'</div><div class="one_third">修订时间：'+infoSaleOrderEdit.edit_time+'</div><div class="one_third">描述：'+edit_desc+'</div></li></ul>';

       }

       //销售订单交付信息
       if(orderData.infoDeliverOrderList != null){

    	   var infoDeliverOrderAll = orderData.infoDeliverOrderList[0];

    	   var infoDeliverOrderAll_deliver_state = infoDeliverOrderAll.deliver_state == null ? "":infoDeliverOrderAll.deliver_state;
    	   if(infoDeliverOrderAll_deliver_state == 100){
    		   infoDeliverOrderAll_deliver_state='待交付';
    	   }else if(infoDeliverOrderAll_deliver_state == 101){
    		   infoDeliverOrderAll_deliver_state='交付中';
    	   }else if(infoDeliverOrderAll_deliver_state == 102){
    		   infoDeliverOrderAll_deliver_state='交付完成';
    	   }
    	   var infoDeliverOrderAll_deliver_time = infoDeliverOrderAll.deliver_time == null ? "":infoDeliverOrderAll.deliver_time;
    	   var infoDeliverOrderAll_logistics_no = infoDeliverOrderAll.logistics_no == null ? "":infoDeliverOrderAll.logistics_no;

    	   var infoDeliverOrderAll_send_target_addr = infoDeliverOrderAll.send_target_addr == null ? "":infoDeliverOrderAll.send_target_addr;
    	   var infoDeliverOrderAll_create_time = infoDeliverOrderAll.create_time == null ? "":infoDeliverOrderAll.create_time;
    	   var infoDeliverOrderAll_finish_time = infoDeliverOrderAll.finish_time == null ? "":infoDeliverOrderAll.finish_time;

    	   var infoDeliverOrderAll_accept_oper_no = infoDeliverOrderAll.accept_oper_no == null ? "":infoDeliverOrderAll.accept_oper_no;
    	   var infoDeliverOrderAll_accept_oper_name = infoDeliverOrderAll.accept_oper_name == null ? "":infoDeliverOrderAll.accept_oper_name;
    	   var infoDeliverOrderAll_accept_depart_no = infoDeliverOrderAll.accept_depart_no == null ? "":infoDeliverOrderAll.accept_depart_no;

    	   var infoDeliverOrderAll_accept_depart_name = infoDeliverOrderAll.accept_depart_name == null ? "":infoDeliverOrderAll.accept_depart_name;
    	   var infoDeliverOrderAll_goods_name = infoDeliverOrderAll.goods_name == null ? "":infoDeliverOrderAll.goods_name;
    	   var infoDeliverOrderAll_note = infoDeliverOrderAll.note == null ? "":infoDeliverOrderAll.note;

    	   var infoDeliverOrderAll_send_name = infoDeliverOrderAll.send_name == null ? "":infoDeliverOrderAll.send_name;
    	   var infoDeliverOrderAll_real_phone = infoDeliverOrderAll.real_phone == null ? "":infoDeliverOrderAll.real_phone;
    	   var infoDeliverOrderAll_send_addr = infoDeliverOrderAll.send_addr == null ? "":infoDeliverOrderAll.send_addr;

    	   var infoDeliverOrderAll_contact_name = infoDeliverOrderAll.contact_name == null ? "":infoDeliverOrderAll.contact_name;
    	   var infoDeliverOrderAll_contact_tel = infoDeliverOrderAll.contact_tel == null ? "":infoDeliverOrderAll.contact_tel;
    	   var infoDeliverOrderAll_post_province_name = infoDeliverOrderAll.post_province_name == null ? "":infoDeliverOrderAll.post_province_name;

    	   var infoDeliverOrderAll_post_city_name = infoDeliverOrderAll.post_city_name == null ? "":infoDeliverOrderAll.post_city_name;
    	   var infoDeliverOrderAll_post_area_name = infoDeliverOrderAll.post_area_name == null ? "":infoDeliverOrderAll.post_area_name;
    	   var infoDeliverOrderAll_address = infoDeliverOrderAll.address == null ? "":infoDeliverOrderAll.address;

    	   var infoDeliverOrderAll_cod_charge = infoDeliverOrderAll.cod_charge == null ? "":infoDeliverOrderAll.cod_charge;
    	   var infoDeliverOrderAll_insure_charge = infoDeliverOrderAll.insure_charge == null ? "":infoDeliverOrderAll.insure_charge;
    	   var infoDeliverOrderAll_return_tracking_no = infoDeliverOrderAll.return_tracking_no == null ? "":infoDeliverOrderAll.return_tracking_no;

    	   var infoDeliverOrderAll_send_origin_addr = infoDeliverOrderAll.send_origin_addr == null ? "":infoDeliverOrderAll.send_origin_addr;
    	   var infoDeliverOrderAll_delivery_region = infoDeliverOrderAll.delivery_region == null ? "":infoDeliverOrderAll.delivery_region;
    	   var infoDeliverOrderAll_delivery_dept_no = infoDeliverOrderAll.delivery_dept_no == null ? "":infoDeliverOrderAll.delivery_dept_no;

    	   saleOrderDetailHtml += headFirstHtml + '交付信息' + headLatterHtml + bodyFirstHtml + ''
							+'交付状态：'+infoDeliverOrderAll_deliver_state+'</div><div class="one_third">交付时间：'+infoDeliverOrderAll_deliver_time+'</div><div class="one_third">物流单号：'+infoDeliverOrderAll_logistics_no+'</div></li>'
							+'<li class="line">'
							+'<div class="one_third">目的地：'+infoDeliverOrderAll_send_target_addr+'</div><div class="one_third">创建时间：'+infoDeliverOrderAll_create_time+'</div><div class="one_third">完成时间：'+infoDeliverOrderAll_finish_time+'</div></li>'
							+'<li class="line">'
							+'<div class="one_third">受理工号：'+infoDeliverOrderAll_accept_oper_no+'</div><div class="one_third">受理员工名称：'+infoDeliverOrderAll_accept_oper_name+'</div><div class="one_third">受理渠道编码：'+infoDeliverOrderAll_accept_depart_no+'</div></li>'
							+'<li class="line">'
							+'<div class="one_third">受理渠道名称：'+infoDeliverOrderAll_accept_depart_name+'</div><div class="one_third">货物名称：'+infoDeliverOrderAll_goods_name+'</div><div class="one_third">备注：'+infoDeliverOrderAll_note+'</div></li>'
							+'<li class="line">'
							+'<div class="one_third">发货人：'+infoDeliverOrderAll_send_name+'</div><div class="one_third">联系电话：'+infoDeliverOrderAll_real_phone+'</div><div class="one_third">发货地址：'+infoDeliverOrderAll_send_addr+'</div></li>'
							+'<li class="line">'
							+'<div class="one_third">收货人：'+infoDeliverOrderAll_contact_name+'</div><div class="one_third">联系电话：'+infoDeliverOrderAll_contact_tel+'</div><div class="one_third">收货省份：'+infoDeliverOrderAll_post_province_name+'</div></li>'
							+'<li class="line">'
							+'<div class="one_third">收货城市：'+infoDeliverOrderAll_post_city_name+'</div><div class="one_third">收货地区：'+infoDeliverOrderAll_post_area_name+'</div><div class="one_third">收货详细地址：'+infoDeliverOrderAll_address+'</div></li>'
							+'<li class="line">'
							+'<div class="one_third">代收货款：'+infoDeliverOrderAll_cod_charge+'</div><div class="one_third">保价金额：'+infoDeliverOrderAll_insure_charge+'</div><div class="one_third">回单服务单号：'+infoDeliverOrderAll_return_tracking_no+'</div></li>'
							+'<li class="line">'
							+'<div class="one_third">原寄地区域代码：'+infoDeliverOrderAll_send_origin_addr+'</div><div class="one_third">自提区域：'+infoDeliverOrderAll_delivery_region+'</div><div class="one_third">自提营业厅：'+infoDeliverOrderAll_delivery_dept_no+'</div></li></ul>';

       }

       if(cert_info!=null){
    	   $("#idcardName").text(cert_info.cust_name);
    	   $("#idcardNum").text(cert_info.cert_no);
    	   $("#idcardAddr").text(cert_info.cert_address);
       }
       $("#order_detail").empty();
       $("#logisticsList").empty();
       if(query_type == "100"){
    	   $("#order_detail").append(saleOrderDetailHtml);
       }else if(query_type == "101"){
    	   $("#order_detail").append(servOrderDetailHtml);
    	   //显示物流信息
    	   $("#logisticsList").prepend('<div class="one_third">物流单号：'+$('#logistics_no').text()+'</div>');
    	   $("#logisticsList").append(logisticsHtml);
       }
	}else{
		$.message.info('无订单详情信息~');
	}
}

//物流信息
function getSFLogisticsRouterInfo(){
	//判断分支
	var sf_flag =window.parent.Config.SF_FLAG;
	var url =rest_host+"rest/logisticsService/getSFLogisticsRouterInfoCq";
	if(sf_flag =="1"){//走分支
		url =rest_host+"rest/logisticsService/getSFLogisticsRouterInfo";
	}
	$.ajax({
		type: "post",
		url: url,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async:true,
		data: {
			"jsession_id":jsession_id,
			"order_no":order_no,
			"order_no_type":"2",
			"query_type":"1"

		},
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data){
			var str = JSON.stringify(data);
			if("0000" == data.respCode){
				if(null !=data.args.RouterInfo && data.args.RouterInfo.length >0){
					var routerInfo = data.args.RouterInfo;

//					var jsonData = {
//						    "RouterInfo": [
//						                   {
//						                       "acceptTime": "2017-01-06 12:12:15",
//						                       "remark": "快递员已揽货",
//						                       "acceptAddress": "重庆"
//						                   },
//						                   {
//						                       "acceptTime": "2017-01-06 16:45:15",
//						                       "remark": "快递正在派送中",
//						                       "acceptAddress": "重庆"
//						                   }
//						               ]
//						           };
//					var routerInfo = jsonData.RouterInfo;

				    for(var i = routerInfo.length-1; i > -1; i--){
				    	var item=routerInfo[i];
				    	if(i == routerInfo.length-1){
				    		logisticsHtml +='<li class="line first">'
				  			  +'<div class="width20per">'+item.acceptTime+'</div><div class="width80per">'+item.remark+'</div>'
				  			  +'</li>';
				    	}else{
				    		logisticsHtml +='<li class="line">'
				  			  +'<div class="width20per">'+item.acceptTime+'</div><div class="width80per">'+item.remark+'</div>'
				  			  +'</li>';
				    	}
				    }
				}
			}
		},
		error: function(data){
			$.message.error("获取物流信息失败");
		},
		complete: function(){
			$.loading.hide();
		}

	});
}
//获取地区信息(UOC0044)
function getRegionInfo(){
	$.ajax({
		type: "post",
		url: rest_host+"rest/getInfoService/getDefaultProvinceCityAreaInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: {"jsession_id": jsession_id},
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				if(null != data.args){
					region_info = data.args.default_region_info;
				}
			} else {
				$.message.info('无地区信息');
			}
		},
		error: function(data) {
			$.message.error('获取地区信息失败!');
		},
		complete: function(){
			$.loading.hide();
		}
	});
}


//获取证件信息
function getCertInfo(){

	$.ajax({
		type: "post",
		url: rest_host+"rest/checkInfoRest/getCertInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async:true,
		data: {
			"order_no":sale_order_no,
			"finish_flag":finish_flag
		},
		dataType: "json",
		crossDomain: true == !(document.all),
		success: function(data){
			if("0000" != data.respCode){
				return;
			}
			//加载图片
			var json_info=data.args.infoSaleOrderPersionCertVo;
			if(!INPUT_UTIL.isNull(json_info)){
				if(!INPUT_UTIL.isNull(json_info.cert_video_url)){
					$('#video_source1').attr("src",json_info.cert_video_url);
				}else{
					$("#showVideo").parent().hide();
				}
				if(!INPUT_UTIL.isNull(json_info.pic_from_guardian_url)){
					$("#showPic7").attr("src",json_info.pic_from_guardian_url);
					$('#photoBox img').attr("src",json_info.pic_from_guardian_url);
				}else{
					$("#showPic7").parent().hide();
				}
				if(!INPUT_UTIL.isNull(json_info.pic_from_person_url)){
					$("#showPic6").attr("src",json_info.pic_from_person_url);
					$('#photoBox img').attr("src",json_info.pic_from_person_url);
				}else{
					$("#showPic6").parent().hide();
				}
				if(!INPUT_UTIL.isNull(json_info.pic_guardian_url)){
					$("#showPic5").attr("src",json_info.pic_guardian_url);
					$('#photoBox img').attr("src",json_info.pic_guardian_url);
				}else{
					$("#showPic5").parent().hide();
				}
				if(!INPUT_UTIL.isNull(json_info.pic_person_url)){
					$("#showPic4").attr("src",json_info.pic_person_url);
					$('#photoBox img').attr("src",json_info.pic_person_url);
				}else{
					$("#showPic4").parent().hide();
				}
				if(!INPUT_UTIL.isNull(json_info.cert_hand_url)){
					$("#showPic3").attr("src",json_info.cert_hand_url);
					$('#photoBox img').attr("src",json_info.cert_hand_url);
				}else{
					$("#showPic3").parent().hide();
				}
				if(!INPUT_UTIL.isNull(json_info.cert_back_url)){
					$("#showPic2").attr("src",json_info.cert_back_url);
					$('#photoBox img').attr("src",json_info.cert_back_url);
				}else{
					$("#showPic2").parent().hide();
				}
				if(!INPUT_UTIL.isNull(json_info.cert_face_url)){
					$("#showPic1").attr("src",json_info.cert_face_url);
					$('#photoBox img').attr("src",json_info.cert_face_url);
				}else{
					$("#showPic1").parent().hide();
				}
			}

			 //点击图片事件
			$('#thumbnailList').on('click', 'li', function(e) {
				var $this = $(this);
				if ($this.attr('type') == 'video') {
					$('#photoBox').hide();
					$('#videoBox').show();
				}else{
					$('#photoBox').show();
					$('#videoBox').hide();
					$('#photoBox img').attr("src", $this.find("img").attr("src"));
				}

			});
		},
		error: function(data){
			$.message.error("用户证件信息Ajax请求失败");
		},
		complete: function(){
			$.loading.hide();
		}

	});
}
//加载图片显示
function gztValid(){

	   var  data ={
			   jsession_id:jsession_id,
			   cert_no:cert_info==null?"":cert_info.cert_no,//过后自己改
			   cust_name:cert_info==null?"":cert_info.cust_name,
			   serv_order_no:order_no
	    	 };
		$.ajax({
			url:rest_host + "rest/checkInfoRest/gztValid",
			dataType:'json',
			crossDomain: true == !(document.all),
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			type:'post',
			data:data,
			success:function(message){
				if(message.respCode !="0000"){
					$.message.error("国政通校验失败:"+message.content);
					return;
				}

//				var pic_path = commonInfo.rest_host + message.args.pic_path.substring(message.args.pic_path.indexOf("picture"));
				$(".idcard_area img").attr("src",message.args.pic_path);

				// 通过
				$('#livingExaPassBtn').on('click', function(e) {
					orderAct();
				});

			}
		});

}

//将yyyy-MM-dd hh:mm:ss格式字符串转换为Date
function stringToDate(s) {
	var d = new Date();
	d.setYear(parseInt(s.substring(0,4),10));
	d.setMonth(parseInt(s.substring(5,7)-1,10));
	d.setDate(parseInt(s.substring(8,10),10));
	d.setHours(parseInt(s.substring(11,13),10));
	d.setMinutes(parseInt(s.substring(14,16),10));
	d.setSeconds(parseInt(s.substring(17,19),10));
	return d;
}
