var mac_name="";//自助机名称
var mac="";//自助机mac地址
var device_status="";//发卡机状态
var blank_box_status="";//空白卡箱状态。0－无卡 1－少卡 2－卡充足
var recycle_box_status="";//废卡箱状态。0－未满 1－已满
var log_date="";//日志记录时间
var printer_status="";//打印机状态
$(document).ready(function () {
	//初始化页面
	getAutoStatusList(mac_name);
	//自助机终端名称模糊查询
	$("#name_search").click(function () {
		if($("#mac_name").val()==""){
			mac_name="";
		}else{
			mac_name="%"+$("#mac_name").val()+"%";
		}
		getAutoStatusList(mac_name);
	});
	//重置
	$("#name_reset").click(function () {
		$("#mac_name").val("");
		
	});
});

//初始化页面
function getAutoStatusList(mac_name){	 
	 var URL = application.fullPath + "authority/autoStatusList/queryAutoStatus";
		$.ajax({
			url:URL,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type:'post',
			data:{
				hall_name:mac_name
			},
			waitMsg:"加载数据中...",
			success:function(message){
				var lists=message.args.lists;
				var str="";
				str+='<tr>'
		        	+'<td width="290" height="40" align="center" bgcolor="#ececec" style="display:none">mac地址</td>'
		        	+'<td width="150" height="40" align="center" bgcolor="#ececec">自助终端名称</td>'
		        	+'<td width="100" align="center" bgcolor="#ececec">维护员姓名</td>'
		        	+'<td width="150" align="center" bgcolor="#ececec">发卡机状态</td>'
		        	+'<td width="118" align="center" bgcolor="#ececec">空白箱状态</td>'
		        	+'<td width="118" align="center" bgcolor="#ececec">废卡箱状态</td>'
		        	+'<td width="150" align="center" bgcolor="#ececec">打印机状态</td>'
		        	+'<td align="center" bgcolor="#ececec">记录时间</td></tr>';
				if(lists==null||lists==""){
					str+='<tr><td align="center" colspan="7">暂无监控数据</td></tr>';
				}else{
					for(var i=0;i<lists.length;i++){
						str+='<tr>';
						str+='<td width="290" height="40" align="center" bgcolor="#ececec" style="display:none">'+lists[i].mac+'</td>';
						str+='<td width="150" height="40" align="center" bgcolor="#ececec" style="word-break:break-all">'+lists[i].hall_name+'</td>';
						str+='<td width="100" align="center" bgcolor="#ececec" style="word-break:break-all">'+lists[i].maintenance_man+'</td>';//维护员姓名
						
						var autoStatusLog=lists[i].autoStatusLogVo;
						//发卡机状态
						if(autoStatusLog!=null&&autoStatusLog!=""){
							
							if(autoStatusLog.device_status!="1" || autoStatusLog.device_status==null){
								if(autoStatusLog.device_status==null){
									device_status=" — — ";
								}else{
									device_status=autoStatusLog.device_status;
								}
								blank_box_status=" — — ";
								recycle_box_status=" — — ";
							}else{
								device_status="发卡机正常";
								//空白卡箱状态
								if(autoStatusLog.blank_box_status==0){
									blank_box_status="空白箱无卡";
								}else if(autoStatusLog.blank_box_status==1){
									blank_box_status="空白箱少卡";
								}else if(autoStatusLog.blank_box_status==2){
									blank_box_status="空白箱卡充足";
								}else{
									blank_box_status=" — — ";
								}
								//废卡箱状态
								if(autoStatusLog.recycle_box_status==0){
									recycle_box_status="废卡箱未满";
								}else if(autoStatusLog.recycle_box_status==1){
									recycle_box_status="废卡箱已满";
								}else{
									recycle_box_status=" — — ";
								}
							}
							//打印机状态
							if(autoStatusLog.printer_status==null){
								printer_status = "— —";
							}else{
								switch (autoStatusLog.printer_status)
								{		
								case "0": printer_status = "打印机正常"; break;
								case "1": printer_status = "打印机未连接或未上电"; break;
								case "2": printer_status = "打印机和调用库不匹配"; break;
								case "3": printer_status = "打印头打开"; break;
								case "4": printer_status = "切刀未复位"; break;
								case "5": printer_status = "打印头过热"; break;
								case "6": printer_status = "黑标错误"; break;
								case "7": printer_status = "纸尽"; break;
								case "8": printer_status = "纸将尽"; break;
								}
							}
							//记录时间
							log_date=autoStatusLog.log_date;
							
							str+='<td width="100" align="center" bgcolor="#ececec" style="word-break:break-all">'+device_status+'</td>';//发卡机状态
							str+='<td width="118" align="center" bgcolor="#ececec" style="word-break:break-all">'+blank_box_status+'</td>';//空白卡箱状态
							str+='<td width="118" align="center" bgcolor="#ececec" style="word-break:break-all">'+recycle_box_status+'</td>';//废卡箱状态
							str+='<td width="150" align="center" bgcolor="#ececec" style="word-break:break-all">'+printer_status+'</td>';//打印机状态
							str+='<td align="center" bgcolor="#ececec" style="word-break:break-all">'+log_date+'</td>';//记录时间
						}else{
							str+='<td width="100" align="center" bgcolor="#ececec"> — — </td>';//发卡机状态
							str+='<td width="100" align="center" bgcolor="#ececec"> — — </td>';//空白卡箱状态
							str+='<td width="100" align="center" bgcolor="#ececec"> — — </td>';//废卡箱状态
							str+='<td width="100" align="center" bgcolor="#ececec"> — — </td>';//打印机状态
							str+='<td align="center" bgcolor="#ececec"> — — </td>';//记录时间
						}
						str+='</tr>';
					}
					str+='<tr><td colspan="4" font-size="8"> — —：表示暂无此项数据。</td></tr>';
				}
				$("#log").html(str);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 console.info(XMLHttpRequest.status);
				 console.info(XMLHttpRequest.readyState);
				 console.info(textStatus);
			}
			
		});
}