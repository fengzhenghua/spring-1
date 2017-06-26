<%@ page import="org.jasig.cas.client.authentication.AttributePrincipal" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: Chunwei
  Date: 2016/5/13
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ESS</title>
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
                redurl ="https://135.24.252.38:8099/ess/pages/sys/frame/iframe-unp?url="+redurl+"&staffId=83"+operNo+
                "&departId=83"+departId+"&eparchyCode="+ eparchyCode + "&provinceId=83&subSysCode=BSS&_fromSys=UNP&_specCasServer=CQCU";
                //FIXME 改从配置读取 cbss 代理地址
            }
            response.sendRedirect(redurl);
        %>
    </script>
</head>
<body>
<iframe width="100%" height="100%">

</iframe>
</body>
</html>
