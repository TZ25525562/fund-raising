<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<!DOCTYPE html>
<head>
    <title>测试</title>
<%--    全路径提取到前面--%>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
    <script type="text/javascript">
       $(function () {
           $("#btn").click(function () {
                layer.alert("lay弹窗");
           })
       })
    </script>
</head>
<body>
<a href="test1.html">Test</a><br>
<button id="btn">layer测试</button>
</body>
</html>