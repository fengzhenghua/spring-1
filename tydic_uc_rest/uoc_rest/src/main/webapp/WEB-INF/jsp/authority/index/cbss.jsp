<%@ page import="java.util.Map" %>
<%@ page import="org.jasig.cas.client.authentication.AttributePrincipal" %>
<%--
  Created by IntelliJ IDEA.
  User: Chunwei
  Date: 2016/4/30
  Time: 16:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>CBSS</title>
    <meta http-equiv="X-UA-Compatible" content="IE=7"/>
    <script>
        <%
            String operNo = "CE0744";
            String departId = "83a0881";
            String eparchyCode = "0023";

            AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
            if (principal != null) {
                Map<String, Object> attributes = principal.getAttributes();
                if (attributes != null) {
                    operNo = attributes.get("CBS").toString();
                    departId = attributes.get("departId").toString();
                    eparchyCode = attributes.get("eparchyCode").toString();
                }
            }
            String url = request.getQueryString();
            String redurl = url.substring(url.indexOf("redurl=") + 7);
            if(redurl.indexOf("http:")<0 && redurl.indexOf("https:")<0){
                redurl ="https://135.24.252.38:8099/cbss"+redurl +
                "&_specCasServer=CQCU&subSysCode=CBS&_fromSys=UNP"+
                "&staffId=" + operNo + "&departId=" + departId + "&eparchyCode=" + eparchyCode + "&LOGIN_PROVINCE_CODE=83";
                //FIXME 改从配置读取 cbss 代理地址
            }
        %>
    </script>
</head>
<frameset id="fset" cols="100,0,0">
    <frame id="icontentframe" name="icontentframe" scrolling="auto" noresize="noresize"
           src="<%=redurl%>"/>
    <frame id="printframe" name="printframe" scrolling="no"/>
    <frame id="customframe" name="customframe" scrolling="no"/>
</frameset>
<noframes>
    <body>
    </body>
</noframes>
<body>
</body>
</html>
