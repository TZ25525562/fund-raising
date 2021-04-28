<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
    <title>系统错误页面</title>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#btn").click(function () {
                //相当于浏览器的后退按钮
                window.history.back();
            })
        })
    </script>
</head>
<body>
<h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in" style="text-align: center"></i>系统错误消息</h2>
<h3 style="text-align: center">${requestScope.exception.message}</h3>
<button id="btn" style="width: 150px;margin-left: auto;margin-right: auto" class="btn btn-lg btn-success btn-block">
    返回上一个</button>

</body>
</html>
