<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<%--引入头部文件--%>
<%@include file="/WEB-INF/include-head.jsp"%>
<%--引入nav文件--%>
<%@include file="/WEB-INF/include-nav.jsp"%>
<%--引入添加菜单项的模态框--%>
<%@include file="/WEB-INF/menu-add.jsp"%>
<body>
<link rel="stylesheet" href="${pageContext.request.contextPath}/ztree/zTreeStyle.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/fundraising/my-menu.js"></script>
<script type="text/javascript">
    $(function () {

        // 调用专门封装好的初始化树的函数
        generateTree();

        // 添加菜单项显示模态框
        $("#treeDemo").on("click",".addBtn",function () {

            // 获取当前id并且设置为全局变量
            window.pid = this.id;

            // 显示模态框
            $("#addMenuModel").modal("show");

            // 禁止跳转
            return false;

        });

        // 保存添加菜单项修改
        $("#saveMenuBtn").click(function () {

            var pid = window.pid;
            var url = $("#inputUrl").val();
            var name = $("#inputName").val();

             $.ajax({
                 url: "menu/save.json",
                 type: "POST",
                 data:{
                     pid: pid,
                     name: name,
                     url: url,
                 },
                 dataType:"json",
                 success:function (result) {
                     console.log(result);
                 },
                 error:function (result) {
                    console.log(result);
                 }
             })
        })
    })
</script>
<div class="container-fluid">
    <div class="row">
        <%--        引入侧边栏文件--%>
        <%@include file="/WEB-INF/include-sidebar.jsp"%>
            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

                <div class="panel panel-default">
                    <div class="panel-heading">权限菜单列表
                        <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"></div>
                    </div>
                    <div class="panel-body">
<%--                        用来给动态数据显型的静态结构--%>
                        <ul id="treeDemo" class="ztree" style="user-select: none;"></ul>
                    </div>
                </div>
            </div>
    </div>
</div>
</body>
</html>

