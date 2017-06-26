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
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.tydic.unicom.crm.listener.GlobalConfig"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
<script type="text/javascript">
	 $(function(){
		window.location.href = '<%=GlobalConfig.newInstance().get("casLoginErrorUrl")%>';
	}); 
</script>
<title>天源迪科统一登录平台</title>
<style type="text/css" media="screen">
   .center{
   text-align: center;
   padding-top: 250px;
  }
 </style>
</head>
<html>
<body>
<div class="center">
	<div style="padding-left:45%;width:200px;">
		<table>
			<tr>
				<td valign="middle">
					<img  src="images/loading.gif"/>
				</td>
				<td valign="middle">页面加载中...
				</td>
			</tr>
		</table>
	</div>
</div>
   
</body>
</html>
  

