<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%--<base href="<%=basePath%>">--%>
    <title>Title</title>
        <style type="text/css">
            .h3{
                color : #806537;
                background-color: #0e8035;
            }
            #ja{
                width: 700px;
            }
            #ajax{
                width: 700px;
                height: 300px;
                background-color: #807c3e;
            }
        </style>
    <script type="text/javascript" src="js/BaseJs/jquery-2.1.4.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            getData();
        });

        function getData() {
            var mydata = {
                name : 'qq'
            };
            $.ajax({
                type : 'post',
                url  : 'http://localhost:8000/ssm/user/getUsers',
                contentType : "application/x-www-form-urlencoded; charset=utf-8",
                async : true,
                data  : mydata,
                dataType : "json",
                crossDomain : true == !(document.all),
                success : function (data) {
                    if(data.respCode == "0000"){
                        var users = data.args.users;

                        $.each(users,function (i,item) {
                            var id = item.id;
                            var name = item.name;
                            var dept = item.dept;
                            var phone = item.phone;
                            var website = item.website;
                            var html = '<h3 class="h3"> id=' + id + 'name=' +name + 'phone=' + phone + 'dept=' + dept + 'website=' + website + '<h3>';
                            $("#ja").append(html);
                        })
                    }
                }
            });
        }
    </script>
</head>
<body>
    <div id="ajax">
        <div id="ja"></div>
    </div>
</body>
</html>
