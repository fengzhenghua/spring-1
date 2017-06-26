var regionList
var json_info;
var receive_info;
var select_flag = false;
//本页面需要展示的信息，在父页面的全局参数json_info中获取
$(document).ready(function() {
	json_info = selectTaskInfo.json_info;
	receive_info = selectTaskInfo.region_info;
	if(commonInfo.role_type !="2"){
		$("#forceFlowBtn").hide();
	}
	
	$("#forceFlowBtn").on('click', function(e) {
		$.dialog.confirm(
				"是否强制流转？",
			    "提示",
			    "确认",
			    "取消",
			    function() {
					forceFlow();
			    }
			);
	});
	
	// 确认
	$('#exaPassBtn').on('click', function(e) {
		//alert($(this).text());
		var change_flag = $('#exaRecUpdateBtn').html();
		if($('#exaRecUpdateBtn').html() == "保存"){
			$.message.info("请保存收货信息~");
			return;
		}else{
			$.dialog.confirm(
					"是否审核通过？",
				    "提示",
				    "确认",
				    "取消",
				    function() {
						changeServOrder("1");

				    }
				);
		}
	});
	$("#exaReceiveNameChange").hide();
	$("#exaPhoneChange").hide();

	getRegionInfo();
	
	var delivery_region =json_info.delivery_region;
	if(delivery_region ==null || delivery_region ==""){
		$("#delivery_region").hide();
	}else{
		$("#deliveryRegionList").append('<option value="'+delivery_region+'" selected="selected">'+delivery_region+'</option>');
		$("#exaDeliveryRegion").text(delivery_region).attr('op_val', delivery_region);
		getShellingInfo();
	}

	// 修改收货地址
	$('#exaRecUpdateBtn').on('click', function(e) {
		var $this = $(this);
		if ($this.text() == '更正') {
			$("#exaReceiveNameChange").change();
			getRegionInfo();
			$('#deliveryRegionList').val($('#exaDeliveryRegion').attr('op_val'));
			$('#deliveryDeptNoList').val($('#exaDeliveryDeptNo').attr('op_val'));
			$('#exaRecProvince').val($('#exaRecShowProvince').attr('op_val')); // 省份
			$('#exaRecCity').val($('#exaRecShowCity').attr('op_val')); // 市
			$('#exaRecDistrict').val($('#exaRecShowDistrict').attr('op_val'));//区县
			$('#exaRecDetail').val($('#exaRecShowDetail').text()); // 详细地址
			$('#exaRecAddr').addClass('hide');
			$('#exaDelivery').addClass('hide');
			$('#exaRecUpdate').removeClass('hide');
			$('#exaRecUpdate2').removeClass('hide');
			$("#exaReceiveName").hide();
			$("#exaPhone").hide();
			$("#exaReceiveNameChange").show();
			$("#exaPhoneChange").show();
			$("#exaReceiveNameChange").val($("#exaReceiveName").text());
			$("#exaPhoneChange").val($("#exaPhone").text());
			$this.text('保存');
		} else {
			var checkIsNumber=checkInput();
			if(!checkIsNumber){
				return checkIsNumber;
			}
			$('#exaDeliveryRegion').attr('op_val', $('#deliveryRegionList option:selected').val()).text($('#deliveryRegionList option:selected').text()); 
			$('#exaDeliveryDeptNo').attr('op_val', $('#deliveryDeptNoList option:selected').val()).text($('#deliveryDeptNoList option:selected').text());
			$('#exaRecShowProvince').attr('op_val', $('#exaRecProvince option:selected').val()).text($('#exaRecProvince option:selected').text()); // 省份
			$('#exaRecShowCity').attr('op_val', $('#exaRecCity option:selected').val()).text($('#exaRecCity option:selected').text()); // 市
			$('#exaRecShowDistrict').attr('op_val', $('#exaRecDistrict option:selected').val()).text($('#exaRecDistrict option:selected').text()); // 区县
			$('#exaRecShowDetail').text($('#exaRecDetail').val()); // 详细地址
			$('#exaRecAddr').removeClass('hide');
			$('#exaDelivery').removeClass('hide');
			$('#exaRecUpdate').addClass('hide');
			$('#exaRecUpdate2').addClass('hide');
			$("#exaReceiveNameChange").hide();
			$("#exaPhoneChange").hide();
			$("#exaReceiveName").show();
			$("#exaPhone").show();
			$("#exaReceiveName").text($("#exaReceiveNameChange").val());
			$("#exaPhone").text($("#exaPhoneChange").val());
			changeServOrder("0");
			$this.text('更正');
			select_flag = true;
			//var select_area = $('#exaRecDistrict').find ("option:selected").attr("data");
		}
	});

	$("#exaRecCity").change(function(e){
		dealRegionInfo("3",$("#exaRecCity").val(),regionList);
	});

	/*加载页面数据 - */
	$('#exaUserName').text(selectTaskInfo.cust_name==null?"":selectTaskInfo.cust_name);
	$('#exaIdNum').text(selectTaskInfo.cert_no==null?"":selectTaskInfo.cert_no);
	$('#exaReceiveName').text(json_info==null?"":json_info.receive_name);
	$('#exaPhone').text(json_info==null?"":json_info.receive_phone);

	$('#exaRecShowDetail').text(json_info==null?"":json_info.receive_address);
	
});

//验证表单
function checkInput(){
	var connect_phone = $("#exaPhoneChange").val();
	//var contact_adress = $("#exaReceiveNameChange").val();
	var flag = true;
	var phoneRegex = /^(13[0-9]|15[012356789]|18[02356789]|17[02356789]|14[57]|17[0])[0-9]{8}$/;
	var phoneRegex2 = /^(0[0-9]{2,3})?([0-9]{7,8})$/;
	if(!phoneRegex.test(connect_phone) && !phoneRegex2.test(connect_phone)){
		flag = false;
		$.message.error("收货人电话请输入固话或手机号码~");
	}else{
		if($("#exaReceiveNameChange").val() == "" || $("#exaReceiveNameChange").val() == null){
			flag = false;
			$.message.error("请填写收货人姓名~");
		}else if($('#exaRecCity option:selected').text() == "" || $('#exaRecCity option:selected').text() == null ){
			flag = false;
			$.message.error("请选择收货人所在城市~");
		}else if($('#exaRecDistrict option:selected').text() == "" || $('#exaRecDistrict option:selected').text() == null){
			flag = false;
			$.message.error("请选择收货人所在区域~");
		}else if($('#exaRecDetail').val() == "" || $('#exaRecDetail').val() == null){
			flag = false;
			$.message.error("请填写收货人所在详细地址~");
		}
	}
	var delivery_region =json_info.delivery_region;
	if(delivery_region !=null && delivery_region !=""){
		if($('#deliveryDeptNoList').val() == "" || $('#deliveryDeptNoList').val() == null){
			flag = false;
			$.message.error("请选择自提营业厅~");
		}
	}
		return flag;
}

//服务订单修改服务(UOC0009)
function changeServOrder(taskHandleFlag){
	if(taskHandleFlag=="1"){
		if(json_info.delivery_region !=null && json_info.delivery_region !=""){
			if($("#exaDeliveryDeptNo").text()=="" || $("#exaDeliveryDeptNo").text()==null){
				$.message.error("请选择自提营业厅~");
				return;
			}
		}
	}
	var json_info_in = {
			"receive_province":$('#exaRecShowProvince').attr('op_val'),
			"receive_city":$('#exaRecShowCity').attr('op_val'),
			"receive_area":$('#exaRecShowDistrict').attr('op_val'),
			"receive_address":$('#exaRecShowDetail').text(),
			"receive_name":$('#exaReceiveName').text(),
			"receive_phone":$('#exaPhone').text(),
			"receive_province_name":$('#exaRecShowProvince').text(),
			"receive_city_name":$('#exaRecShowCity').text(),
			"receive_area_name":$('#exaRecShowDistrict').text(),
			"delivery_dept_no":$('#deliveryDeptNoList').val(),
			"delivery_region":json_info.delivery_region
	};
	data = {
			"jsession_id" :commonInfo.jsession_id,
			"serv_order_no" :selectTaskInfo.order_no==null?"":selectTaskInfo.order_no,
			"json_info" :JSON.stringify(json_info_in),
			"oper_type" :selectTaskInfo.oper_type==null?"101":selectTaskInfo.oper_type,
			"flow_type" :"2",
			"manual_flag" :"0"	//人工操作标志：0人工，1非人工
	};
	$.ajax({
		type: "post",
		url: commonInfo.rest_host+"rest/ordModServiceRest/serviceOrderChange",
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
				if(taskHandleFlag=="1"){
					taskHandle();
				}else{
					$.message.info("保存成功");
				}
			}else{
				$.message.info(data.content);
			}
		},
		error: function(data){
			$.message.error("服务订单修改失败");
		},
		complete:function(){
			$.loading.hide();
		}
	});
}

//人工任务处理服务(UOC0015)
function taskHandle(){
	var data = {
			jsession_id: commonInfo.jsession_id,
			order_no: selectTaskInfo.order_no,
			oper_type:"101",
			order_type:"101",
			tache_code:"S00011",
			call_type:"1"
		};

	$.ajax({
		type: "post",
		url: commonInfo.rest_host+"rest/ArtificialTaskRest/createHandingArtificialTask",
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
				if(INPUT_UTIL.isNull(commonInfo.tache_code)){
					$.dialog.confirm(
						    "审核成功,是否进入下一环节",
						    "任务",
						    "下一步",
						    "返回",
						    function() {
						    	getTaskDataList(0,1,10,1);
						    	getTaskDetail(selectTaskInfo.order_no,selectTaskInfo.order_type);
						    },
						    function() {
						    	window.location.href = "task.jsp?role_type="+commonInfo.role_type;
						    },
						    function() {
						    	window.location.href = "task.jsp?role_type="+commonInfo.role_type;
						    }
						);
				}else{
					$.dialog.alert(
							"审核成功",
							"任务",
							"返回",
							function() {
						    	window.location.href = "task.jsp?tache_code="+commonInfo.tache_code+"&role_type="+commonInfo.role_type;
						    }
					);
				}
			} else {
				$.message.error("人工任务处理失败:"+data.content);
			}
		},
		error: function(data) {
			$.message.error('人工任务处理失败!');

		},
		complete:function(){
			$.loading.hide();
		}
	});
};

//获取地区信息(UOC0044)
function getRegionInfo(){
	//初始化收货信息
	if(null != receive_info){
		$("#exaRecShowProvince").text(receive_info.provinceName).attr('op_val', receive_info.provinceCode);
		if(!select_flag){
			$.each(receive_info.cityInfo,function(i,item){
				if(null == json_info){
					$('#exaRecShowCity').attr('op_val', item.cityRegionCode).text(item.cityRegionName);
				}else if(json_info.receive_city == item.cityRegionCode){
					$('#exaRecShowCity').attr('op_val', item.cityRegionCode).text(item.cityRegionName); // 市
				}
				$.each(item.areaInfo,function(i,item){
					if(null == json_info){
						$('#exaRecShowDistrict').attr('op_val', item.areaRegionCode).text(item.areaRegionName); // 区县
					}else if(json_info.receive_area == item.areaRegionCode){
						$('#exaRecShowDistrict').attr('op_val', item.areaRegionCode).text(item.areaRegionName); // 区县
					}
				});
			});
		}

		$("#exaRecProvince").html("");
		$("#exaRecProvince").append('<option value="'+receive_info.provinceCode+'" selected="selected">'+receive_info.provinceName+'</option>');
		regionList=receive_info.cityInfo;
		dealRegionInfo("2","",regionList);
		if(!select_flag){
			$("#exaRecCity").change();
		}else{
			$("#exaRecCity").change(function(e){
				dealRegionInfo("3",$("#exaRecCity").val(),regionList);
			});
		}
	}
}

function dealRegionInfo(region_level,region_code,regionList){
	var cityHtml = '';
	var districtHtml = '';
	if(region_level == "2"){
		$.each(regionList,function(i,item){
			cityHtml +='<option value="'+item.cityRegionCode+'" data=\'' + JSON.stringify(item) + '\'>'+item.cityRegionName+'</option>';
		});
		$("#exaRecCity").html("");
		$("#exaRecCity").append(cityHtml);
	}else if(region_level == "3"){
		$.each(regionList,function(i,item){
			if(region_code == item.cityRegionCode){
				$.each(item.areaInfo,function(i,item){
					districtHtml +='<option value="'+item.areaRegionCode+'" data=\'' + JSON.stringify(item) + '\'>'+item.areaRegionName+'</option>';
				});
			}
		});
		$("#exaRecDistrict").html("");
		$("#exaRecDistrict").append(districtHtml);
	}
}

function getShellingInfo(){
	var delivery_dept_no =json_info.delivery_dept_no;
	data={
			jsession_id:commonInfo.jsession_id,
			region_id:"",
			dept_no:"",
			dept_name:""
	}
	
	$.ajax({
		type: "post",
		url: commonInfo.rest_host+"rest/getInfoService/getShellingInfo",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data:data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
//			$.loading.show("正在加载");
		},
		success: function(data) {
			if (data.respCode=="0000") {
				var obj =JSON.parse(data.args.json_info);
				var dept_list =obj.dept_list;
				var deptHtml ="";
				console.info(dept_list);
				$.each(dept_list,function(i,item){
					if(delivery_dept_no ==item.dept_no){
						deptHtml +='<option value="'+item.dept_no+'" selected="selected" data=\'' + JSON.stringify(item) + '\'>'+item.dept_name+'</option>';
						$("#exaDeliveryDeptNo").text(item.dept_name).attr('op_val', item.dept_name);
					}else{
						deptHtml +='<option value="'+item.dept_no+'" data=\'' + JSON.stringify(item) + '\'>'+item.dept_name+'</option>';
					}			
				});
				$("#deliveryDeptNoList").append(deptHtml);
			}else{
				$.message.info(data.content);
			}
		},
		error: function(data){
		},
		complete:function(){
			$.loading.hide();
		}
	});
}

//强制流转 UOC0015，flow_skip填1
function forceFlow(){
	var data = {
			jsession_id: commonInfo.jsession_id,
			order_no: selectTaskInfo.order_no,
			oper_type:"101",
			order_type:"101",
			flow_skip:"1",
			tache_code:"S00011",
			call_type:"1"
		};

	$.ajax({
		type: "post",
		url: commonInfo.rest_host+"rest/ArtificialTaskRest/createHandingArtificialTask",
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
				if(INPUT_UTIL.isNull(commonInfo.tache_code)){
					$.dialog.confirm(
						    "审核成功,是否进入下一环节",
						    "任务",
						    "下一步",
						    "返回",
						    function() {
						    	getTaskDataList(0,1,10,1);
						    	getTaskDetail(selectTaskInfo.order_no,selectTaskInfo.order_type);
						    },
						    function() {
						    	window.location.href = "task.jsp?role_type="+commonInfo.role_type;
						    },
						    function() {
						    	window.location.href = "task.jsp?role_type="+commonInfo.role_type;
						    }
						);
				}else{
					$.dialog.alert(
							"审核成功",
							"任务",
							"返回",
							function() {
						    	window.location.href = "task.jsp?tache_code="+commonInfo.tache_code+"&role_type="+commonInfo.role_type;
						    }
					);
				}
			} else {
				$.message.error("人工任务处理失败:"+data.content);
			}
		},
		error: function(data) {
			$.message.error('人工任务处理失败!');

		},
		complete:function(){
			$.loading.hide();
		}
	});
}