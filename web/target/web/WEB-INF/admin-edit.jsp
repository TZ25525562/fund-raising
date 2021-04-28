<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<%--引入头部文件--%>
<%@include file="/WEB-INF/include-head.jsp" %>
<body>
<%--引入nav文件--%>
<%@include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%--        引入侧边栏文件--%>
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="/admin/to/main.html">首页</a></li>
                <li><a href="/admin/to/page.html">数据列表</a></li>
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"></div>
                </div>
                <div class="panel-body">
                    <form role="form" action="admin/to/update.html" method="post">
                        <input type="hidden" name="pageNum" value="${param.pageNum}">
                        <input type="hidden" name="keyword" value="${param.keyword}">
                        <input type="hidden" name="id" value="${requestScope.admin.id}">
                        <div class="form-group">
                            <label for="exampleInputPassword1">登录账号</label>
                            <input name="loginAcct" type="text" class="form-control" id="exampleInputPassword1"
                                   placeholder="请输入登录账号" value="${requestScope.admin.loginAcct}">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">用户昵称</label>
                            <input name="userName" type="text" class="form-control" id="exampleInputPassword1"
                                   placeholder="请输入用户昵称" value="${requestScope.admin.userName}">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">邮箱地址</label>
                            <input name="email" type="email" class="form-control" id="exampleInputEmail1"
                                   placeholder="请输入邮箱地址" value="${requestScope.admin.email}">
                            <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                        </div>
                        <button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i>更新
                        </button>
                        <button type="button" class="btn btn-danger">重置</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

