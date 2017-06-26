/*
 * added by haolong
 * 独立模块：读取身份证 华视设备
 * 依赖模块：CVR_IDCard
 * 依赖的变量：application.fullPath (一开始漏掉了 。。。 *)
 * 
 */
var PC_IDREADER = (function(){
	var pc_idreader = {
			/*里面的元素按照约定增减,可以不用在这里显示定义*/
		pc_idreader_info:{
			idreadbackCall:"",
			next_flag:false,//下一步
			order_id:"",
			cust_seq_id:"",
			readerDeviceTypeFlag:"0",//默认为0 表示 读卡器种类
			id_card_mech:"crvu",//默认值？？
			defaultImagePath: "images/photo.png",
			id_card_image_path : "/uss_web/picture/idcard/idCardImag",
			applicationfullPath:"",
			pName : "", // 姓名
			pSex : "", // 性别
			pSexShow : "",
			pNation : "", // 民族
			pBorn : "", // 出生日期
			pAddress : "", // 住址
			pCardNo : "", // 身份证号
			pPolice : "", // 签发机关
			pActivityLFrom : "", // 起始日期
			pActivityLTo : "", // 结束日期
			pDeviceNo : "", //阅读器ID
			base64jpg : "", // 照片
			readCardSucc:"", // 是否读卡成功标示 1 成功 0 失败或者未读卡
			identityType : '', // 认证方式
			identityFlag : false, // 认证成功标识
			defaultImagePath : '',
			valid_start:'',
		    valid_end:'',
			year:'',
		    month:'',
		    day:'',
		    contact_phone:'',//联系电话
		    contact_address:'',//联系地址
		},
		load_model:function (){
			PC_IDREADER.pc_idreader_info.readerDeviceTypeFlag='1';
		},
		//加载身份证读卡器
		load_card_mech:function (){
			//var id_card_mech = $("#id_card_mech").val();
			var id_card_mech =PC_IDREADER.pc_idreader_info.id_card_mech;
			if(id_card_mech === "crvu"){
				PC_IDREADER.load_model(id_card_mech);
			}
		},
		
		readCard:function(applicationfullPath,idreadbackCall){
			// 读卡结果
			var strReadResult="";
			var	readError=false;
			PC_IDREADER.pc_idreader_info.applicationfullPath=applicationfullPath;
			PC_IDREADER.pc_idreader_info.idreadbackCall=idreadbackCall;
			var messageFlag = PC_IDREADER.pc_idreader_info.readerDeviceTypeFlag;
			if(messageFlag !='1'){
				$.alert('请选择读卡器!');
				return;
			}
			// 读卡未结束，不再次读卡
			if (strReadResult == null && !readError) return;
			strReadResult = null;
			// 调用空间的读卡方法
			try {
				strReadResult = CVR_IDCard.ReadCard();
			}
			catch (e) {
				$.alert("读卡错误,请检查您的控件或驱动是否正确安装最新版本");
				readError = true;
				return;
			}
			readError = false;//不懂要干嘛
			// 读卡成功时，填充页面显示信息
			if(strReadResult == "0") {
				//readCardSucc = "1";  读卡成功标示，传给上个页面
				//fillLocal 拷贝原来的代码  (以后可以省掉 ，慢慢改啦) 
				PC_IDREADER.fillLocal();
				PC_IDREADER.submitToDb();				
			} else {
				 //readCardSucc = "0"; 读卡成功标示，传给上个页面
				$.alert("读卡错误,请移动身份证,进行读卡!");//错误原因描述strReadResult
			}
			return true;
		},
		
		// 将读取到的证件信息持久化本地，？为啥名称都不一样 哈哈
		fillLocal:function() {
			var info=PC_IDREADER.pc_idreader_info;
			var year = '';
			var month = '';
			var day ='';
			info.pName = CVR_IDCard.Name; // 姓名
			info.pSex = CVR_IDCard.Sex; // 性别
			if(info.pSex != '1') {
				info.pSex = '0';
			}
			info.pSexShow = info.pSex == "1" ? "男" : "女";
			info.pNation = PC_IDREADER.getNationStr(CVR_IDCard.Nation); // 民族
			info.pBorn = CVR_IDCard.Born; // 出生日期
			// 截取生日日期，目前有些环境读卡器读出日期为 ‘yyyy-dd-mm’
			if(info.pBorn.length == 8){
				var year = info.pBorn.substr(0, 4);
				var month = info.pBorn.substr(4, 2);
				var day = info.pBorn.substr(6, 2);
				info.pBorn = year + "-" + month + "-" + day;
				info.year = year;
				info.month = month;
				info.day = day;
			}
			info.pAddress = CVR_IDCard.Address; // 住址
			info.pCardNo = CVR_IDCard.CardNo; // 身份证号
			info.pPolice = CVR_IDCard.IssuedAt; // 签发机关
			info.pActivityLFrom = CVR_IDCard.EffectedDate; // 起始日期
			if(info.pActivityLFrom.length == 8){
				var y = info.pActivityLFrom.substr(0, 4);
				var m = info.pActivityLFrom.substr(4, 2);
				var d = info.pActivityLFrom.substr(6, 2);
				info.pActivityLFrom = y + "-" + m + "-" + d; 
				info.valid_start = y + "." + m + "." + d;
			}
			info.pActivityLTo = CVR_IDCard.ExpiredDate; // 结束日期
			if(info.pActivityLTo.length >= 8){
				var y = info.pActivityLTo.substr(0, 4);
				var m = info.pActivityLTo.substr(4, 2);
				var d = info.pActivityLTo.substr(6, 2);
				info.pActivityLTo = y + "-" + m + "-" + d;
				info.valid_end = y + "." + m + "." + d;
			}else{
				info.valid_end = info.pActivityLTo;
				info.pActivityLTo = "2099" + "-" + "01" + "-" + "01";
			}
			info.pDeviceNo = CVR_IDCard.CardReaderId; //阅读器终端ID
			info.base64jpg = CVR_IDCard.Picture; // 照片编码 

			return true;
		},		
		
			
		/*持久化身份数据到表中*/
		submitToDb:function () {
			var data1={
				/*with(PC_IDREADER.pc_idreader_info){
					"id_number":pCardNo,
					"id_addr":pAddress,
					"id_police":pPolice,
					"custom_name":pName,
					"custom_sex":pSex,
					"custom_birth":pBorn,
					"custom_nation":pNation,
					"start_date":pActivityLFrom,
					"end_date":pActivityLTo,
					"photo_code":base64jpg,
					"order_id":$("#order_id_result").text()==null ? "" : $("#order_id_result").text()
				};*/
					"id_number":PC_IDREADER.pc_idreader_info.pCardNo,
					"id_addr":PC_IDREADER.pc_idreader_info.pAddress,
					"id_police":PC_IDREADER.pc_idreader_info.pPolice,
					"custom_name":PC_IDREADER.pc_idreader_info.pName,
					"custom_sex":PC_IDREADER.pc_idreader_info.pSex,
					"custom_birth":PC_IDREADER.pc_idreader_info.pBorn,
					"custom_nation":PC_IDREADER.pc_idreader_info.pNation,
					"start_date":PC_IDREADER.pc_idreader_info.pActivityLFrom,
					"end_date":PC_IDREADER.pc_idreader_info.pActivityLTo,
					"photo_code":PC_IDREADER.pc_idreader_info.base64jpg,
					"order_id": PC_IDREADER.pc_idreader_info.order_id
			}
			var URL = PC_IDREADER.pc_idreader_info.applicationfullPath + 'authority/idcard/addIdCard';
			$.ajax({
				url:URL,
				dataType:'json',
				contentType: "application/x-www-form-urlencoded; charset=utf-8", 
				type:'post',
				data:data1,
				waitMsg:"读取中..",
				success:function(message){	
					//用户自定义 读取成功后
					if(PC_IDREADER.pc_idreader_info.idreadbackCall){
						PC_IDREADER.pc_idreader_info.idreadbackCall();
					}
					//$.alert ("message="+JSON.stringify(message));
					if (message.type=="success"){
						PC_IDREADER.pc_idreader_info.next_flag=true;
						$("#jiaoyan_next_class").attr("class","ok left_lable");
						PC_IDREADER.pc_idreader_info.cust_seq_id=message.args.seq_id;
					}
					var dialog=$.dialog({
					   title:'提示',//提示title
					   content:'资料上传成功',//显示的内容，支持html格式。
					   buttons:[{id:'btn_ok',text:'确定',
						   onClick:function(){					   
							   dialog.close();
							   window.setTimeout("PC_IDREADER.ClearForm()",2000);
						   }//点击时候回调函数
					   }]
					});
					setTimeout("PC_IDREADER.ClearForm()",3000);
					/*if($('#id_nbr').text() != ""){							
						$('#card_con').hide();
						$("#order_con").show();				
					}*/
				},
				error:function(message){
					$("#jiaoyan_next_class").attr("class","ok_disabled left_lable");
					var dialog=$.dialog({
						   title:'提示',//提示title
						   content:'资料上传失败',//显示的内容，支持html格式。
						   buttons:[{id:'btn_ok',text:'确定',
							   onClick:function(){					   
								   dialog.close();
							   }//点击时候回调函数
						   }]
					});
				}
				
			});
		},
		//清空页面显示信息
		ClearForm:function () {		
			return true;
		},
		getNationStr:function (iNation) {
			if(isNaN(iNation)){
				return iNation;
			}
			var temp = parseInt(iNation);
			if (temp<1 || temp>56) {
				return iNation;
			}
			var arrNations = new Array("汉", "蒙古", "回", "藏", "维吾尔", "苗", "彝", "壮", "布依", "朝鲜", "满", "侗", "瑶", "白", "土家", "哈尼", "哈萨克", "傣", "黎", "傈僳", "佤", "畲", "高山", "拉祜", "水", "东乡", "纳西", "景颇", "克尔克孜", "土", "达斡尔", "仫佬", "羌", "布朗", "撒拉", "毛南", "仡佬", "锡伯", "阿昌", "普米", "塔吉克", "怒", "乌兹别克", "俄罗斯", "鄂温克", "德昂", "保安", "裕固", "京", "塔塔尔", "独龙", "鄂伦春", "赫哲", "门巴", "珞巴", "基诺");
			return arrNations[temp-1];
		}
		
	};

	return pc_idreader;
})();
