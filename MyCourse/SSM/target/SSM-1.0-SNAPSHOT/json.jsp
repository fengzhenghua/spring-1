<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>json</title>
    <script type="text/javascript">
        window.onload = function () {
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if(xhr.status==200 && xhr.readyState==4){
                    var jsonText = xhr.responseText;
                    var obj = JSON.parse(jsonText);
                    var id = obj.args.users[0].id;
                    var name = obj.args.users[0].name;
                    document.getElementById("json").innerHTML = id;
                    document.getElementById("ss").innerHTML = name;
                }
            }
            xhr.open("POST","/ssm/user/getUsers",true);
            xhr.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
            xhr.send("id=1");
        }

    </script>
</head>
<body>
    <div id="json"></div><span id="ss"></span>

</body>
</html>
