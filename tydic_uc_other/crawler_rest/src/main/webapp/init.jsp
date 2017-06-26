<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Init system</title>
<script type="text/javascript" src="./js/jquery.min.js"></script>
<%@ include file="include.jsp"%>
<script>
	//1、判断是否登录，如果登录了就不需要显示 logindiv ，如果没有登录就显示
	$(document)
			.ready(
					function() {
						$
								.ajax({
									url : BASEURL
											+ '/rest/getInfoServiceRest/crawlerinitmethod',
									crossDomain : true == !(document.all),
									type : 'POST', //GET
									async : true, //或false,是否异步
									data : {
										json_info : "{}"
									},
									timeout : TIMEOUT, //超时时间
									dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
									beforeSend : function(xhr) {
										console.log(xhr);
										console.log('发送前');
										//等待查询信息
									},
									success : function(data, textStatus, jqXHR) {
										if (data.content != null) {
											console.log('检查用户');
											$('#logindiv').show();
											$('#login_user').val(data.content);
										} else {
											showerr(data);
										}

									},
									error : function(xhr, textStatus) {
										console.log('错误')
										console.log(xhr);
									},
									complete : function() {
										console.log('结束')
									}
								});
						//hidelogin();
						//showmain();
					});
</script>
</head>
<body>
	<input id='json' name='json'></input>
</body>
</html>