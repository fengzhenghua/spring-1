<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
<%@ page import="java.util.Date" %>
<%
	String nowDate = DateUtil.getCurrentDate(); 
	String beforeDate = DateUtil.getAfterMonth(new Date(), -1);
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>批量赠款</title>
	<link href="<%=fullPath%>css/presentShareNew.css" rel="stylesheet" type="text/css" /> 
	<link href="<%=fullPath%>css/presentAudit.css" rel="stylesheet" type="text/css" />
	<%-- <script src="<%=fullPath%>js/present.js" type="text/javascript"></script>  --%>
	<%-- <script src="<%=fullPath%>js/module/angular/angular.min.js" type="text/javascript"></script>   --%>
	<%-- <script src="<%=fullPath%>js/module/jquery/jquery-1.11.3.js" type="text/javascript"></script>
	<link href="<%=basePath%>js/jquery-ligerui-2.25/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<script src="<%=basePath%>js/jquery-ligerui-2.25/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/jquery-ligerui-2.25/js/core/base.js" type="text/javascript"></script> 
	<script src="<%=basePath%>js/jquery-ligerui-2.25/js/plugins/ligerGrid.js" type="text/javascript"></script>
	<script src="<%=fullPath%>js/json2.js" type="text/javascript"></script>  --%>
	<script src="<%=fullPath%>js/jquery.js" type="text/javascript"></script>
	<script src="<%=fullPath%>js/jquery-form.js" type="text/javascript"></script>  
	<script type="text/javascript">
		var dateTime = {
				beforeDate:"<%=beforeDate%>",
				nowDate:"<%=nowDate%>"	
		};
	</script>
	<script type="text/javascript">
		//var presentApp = angular.module("presentApp", []);
		var loadData="";
		$(document).ready(function (){
			$("#fileload").bind("click",function(){    
				var excelPath = $("#file").val();   
				if(loadData.length>0){
					alert("文件已经导入"); 
					return;
				}
				if(excelPath == null || excelPath == ''){
					alert("请选择要上传的txt文件");
					return;    
				}else{    
					var fileExtend = excelPath.substring(excelPath.lastIndexOf('.')).toLowerCase(); 
					if(fileExtend == '.txt'){
						$("#uploadForm").ajaxSubmit({      
						url:application.fullPath +"authority/bacthPresent/loadFile", 
						cache:false,      
						dataType:'json',
						clearForm: true,
						success: function(data) {
							if(data!=null){ 
								var arr=eval(data);
								if(arr.length<1){
									alert("文件中没有找到用户数据");      
								}else{
									var trdef=$("<tr><td class='td'>设备号码</td><td class='td'>赠款金额</td></tr>"); 
									$("#load_table").append(trdef);
								}
								loadData=arr;
								for(var i=0;i<arr.length;i++){ 
									  var tr = $("<tr><td class='td'>"+arr[i].device_number+"</td><td class='td'>"+arr[i].payfee_type+"</td></tr>"); 
									  $("#load_table").append(tr); 
									  
								}
							  }
							} ,      
						error:function(){  
								alert("文件预览失败");      
						}   
						});
						}else{
							alert("文件格式需为'.txt'格式");   
							return;
						}  
					}});

					
			
	    });
		
			/* scope.isShowDetail = function(type){
				var type= type;
				if("1"==type){
					document.getElementById("error_message").style.display="inline";
					document.getElementById("warn_message").style.display="inline";
				}else{
					document.getElementById("error_message").style.display="none";
					document.getElementById("warn_message").style.display="none";
				}
					
			}; */
			
		
			function go_confirm(){
				var present_type=$("#present_type").val();
				var cycle=$("#cycle").val();
				var times=$("#times").val();
				var start_cycle=$("#start_cycle").val();
				var sent_reseon_code=$("#sent_reseon_code").val();
				var remark=$("#remark").val();
		        alert("present_typ"+present_type);
		        alert("cycle"+cycle);
		        alert("times"+times);
		        alert("start_cycle"+start_cycle);
		        alert("sent_reseon_code"+sent_reseon_code);
		        alert("remark"+remark);
		        alert(loadData);
				var dataParam="";
		        $.ajax({  
	                loading: '正在加载中...',  
	                type: "POST",
		            dataType: "json",
	                url: application.fullPath +"authority/bacthPresent/loadFile",
	                data: dataParam,  
	                success: function (msg,status) {  
	                	//alert(msg.args.alipay_html);
	                	//alert(msg.type);
	                	var respCode = msg.type;
	                	var content=msg.content;
						if(respCode == 'success'){
							$("body").append(msg.args.alipay_html);
							
						}else{
							if(content.length>0){
								$.ligerDialog.closeWaitting();
								$.alert(content);
							}else{
								$.ligerDialog.closeWaitting();
								$.alert("查询失败");
							}
						}		                	
	                   // $.showSuccess("保存成功!");  
	                },  

	                error: function () {  
	                	alert('error');
	                }  
	            }); 

			}
			
		
	</script>


</head>
<body>
	<div class="show" >
    <div class="show_title_bg">
        <div class="show_title">批量赠款</div>
    </div>
 <form id="uploadForm" action="<%=fullPath%>authority/bacthPresent/loadFile" method="post" enctype="multipart/form-data">
	<input type="file" name="file" id="file" value="" /><input type="button" id="fileload" value="文件预览">
	
</form> 
    <div class="box box_long" id="error_message" style="display:none;">
        <div class="table_box">
            <div class="error"><strong>错误: {{ errorMessage }}</strong></div>
        </div>
    </div>

    <div class="box box_long" id="warn_message" style="display:none;">
        <div class="table_box">
            <div class="warn"><strong>警告: {{ warnMessage }}</strong></div>
        </div>
    </div>

   <div class="box box_long">
        <div class="show_big_title"><strong>.</strong>CBSS信息</div>
        <div class="table_box">
            <table width="980" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="100" align="middle" height="22"><strong>工号:</strong></td>
                    <td width="100"><input ng-model="presentAttr.user_name" type="text" placeholder="ȫˤɫԃ»§Ļ"></td>
                    <td width="100" align="middle"><strong>密码:</strong></td>
                    <td width="100"><input ng-model="presentAttr.password" type="text" placeholder="ȫˤɫĜë"></td>
                    <td></td>
                </tr>
            </table>
        </div>
    </div>

    <div class="box box_long">
        <div class="show_big_title"><strong>.</strong>批量赠款信息：</div>
        <div class="table_box">
            <table width="980" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="80" height="22">
                        <strong>赠送类型</strong>
                    </td>
                    <td width="250">
                        <select class="sel" id="present_type">
                            <option value="100301" selected="selected">退费</option>
                            <option value="100302">赠送话费-限本人使用</option>
                            <option value="100304">赠送话费-同一账户共享</option>
                            <option value="100303">SP赔付</option>
                        </select>
                    </td>
                    <td width="80" height="22">
                        <strong>赠送周期</strong>
                    </td>
                    <td width="250">
                        <select class="sel" id="cycle">
                            <option value="1" selected="selected">1个月</option>
                            <option value="2">2个月</option>
                            <option value="3">3个月</option>
                            <option value="6">6个月</option>
                            <option value="12">12个月</option>
                            <option value="18">18个月</option>
                            <option value="24">24个月</option>
                        </select>
                    </td>
                    <td width="80">
                        <strong>单次次数</strong>
                    </td>
                    <td width="250">
                        <select class="sel" id="times">
                            <option value="1" selected="selected">1次</option>
                            <option value="2">2次</option>
                            <option value="3">3次</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td width="80">
                        <strong>生效开始时间</strong>
                    </td>
                    <td>
                        <input class="branch-code" type="datetime" id="start_cycle"/>
                    </td>
                    <td width="80">
                        <strong>赠送原因</strong>
                    </td>
                    <td width="250">
                        <select class="sel" id="sent_reseon_code">
                            <option value="1" selected="selected">充值奖励</option>
                            <option value="2">维系挽留</option>
                            <option value="3">老客户赠送</option>
                            <option value="4">其他</option>
                        </select>
                    </td>
                    <td width="80">
                        <strong >备注</strong>
                    </td>
                    <td>
                        <input class="branch-code" type="text" id="remark" />
                    </td>
                </tr>
            </table>
        </div>
    </div>


   <div class="dashed">
  	<input type="button" id="confirm" onclick="go_confirm()" value="确认">
  </div>
</div>
<div class="show" >
    <div class="box box_long" >
        <div class="show_big_title"><strong>.</strong>批量用户：</div>
        <div class="table_box" >
          <table id="load_table"  width="1000" border="1" align="center" cellpadding="0" cellspacing="0">
          </table>
        </div>
    </div>
</div>	
</body>
</html>