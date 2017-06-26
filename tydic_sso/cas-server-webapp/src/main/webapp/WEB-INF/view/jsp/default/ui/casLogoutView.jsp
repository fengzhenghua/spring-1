<%--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License.  You may obtain a
    copy of the License at the following location:

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
<link href="css/exit.css" rel="stylesheet" type="text/css" />
<script>
	x = 5;
	function countSecond()
	{　
	   x = x-1;
	　 document.fm.displayBox.value=x;
	　 timeOutId=setTimeout("countSecond()", 1000);
	  if( x==0 )
	  {
		  clearTimeout(timeOutId);
		  window.open('','_self');
		  window.opener=null;
		  window.location="login.jsp";
 	  }
	}
</script>

<title>统一销售服务系统</title>
</head>
<html>
<body>
	<div class="logo">
		<img src="images/exit_logo.png" />
	</div>
	<div class="content">
		
		<form name=fm>
         统一销售服务系统退出成功，将在  <input type="text" name="displayBox"value="5" size=1  > 秒后返回首页 
        </form> 
	</div>
 	<tr>
	  <div class="content">
		如果不能返回首页，请点击此处返回首页<a href="window.open('','_self');window.opener=null;login.jsp">返回首页</a>
	  </div>
	</tr>
<script>
countSecond();
</script>
	
</body>
</html>


