<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<%--引入头部文件--%>
<%@include file="/WEB-INF/include-head.jsp"%>
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript">
    $(function () {
        //调用初始化函数
        initPagination();
    });

    //初始化Pagination
    function initPagination() {
        //获取总记录数
        var totalRecord = ${requestScope.pageInfo.total};

        //声明Pagination设置属性的Json对象
        var properties = {
            num_edge_entries: 3, //边缘页数
            num_display_entries: 5, //主体页数
            callback: pageSelectCallback, //用户点击“翻页”按钮之后执行翻页操作的回调函数
            items_per_page:${requestScope.pageInfo.pageSize}, //每页显示项
            current_page: ${requestScope.pageInfo.pageNum -1} , //当前选中的页面,Pagination下标从0开始，必须减一
            prev_text: "上一页", //上一页按钮显示的文字
            next_text: "下一页", //下一页按钮显示的文字
        };

        //创建分页
        $("#Pagination").pagination(totalRecord,properties);

    }

    // 翻页过程中执行的回调函数
    // 点击“上一页”、“下一页”或“数字页码”都会触发翻页动作，从而导致当前函数被调用
    // pageIndex 是用户在页面上点击的页码数值
    function pageSelectCallback(pageIndex, jQuery) {

        //页面加一
        var pageNum = pageIndex + 1;

        //跳转页面
        window.location.href = "admin/to/page.html?pageNum=" + pageNum + "&keyword=${param.keyword}";

        //由于每一个页码按钮都是超链接，所以在这个函数最后取消超链接的默认行为
        return false;
    }
</script>
<body>
<%--引入nav文件--%>
<%@include file="/WEB-INF/include-nav.jsp"%>
<div class="container-fluid">
    <div class="row">
        <%--        引入侧边栏文件--%>
        <%@include file="/WEB-INF/include-sidebar.jsp"%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">数据列表</h3>
                </div>
                <div class="panel-body">
                    <form action="admin/to/page.html" method="post" class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input  name="keyword" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-warning">查询</button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"> 删除</button>
                    <a class="btn btn-primary" style="float:right;" href="admin/to/add.html">新增</a>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${empty requestScope.pageInfo.list}">
                                <tr>
<%--                                    跟标题保持一致，占6个字符--%>
                                    <td colspan="6" style="text-align: center">抱歉!没有查询到符合条件的相关数据</td>
                                </tr>
                            </c:if>
                            <c:if test="${!empty requestScope.pageInfo.list}">
                                <c:forEach items="${requestScope.pageInfo.list}" var="admin" varStatus="myStatus">
                                    <tr>
                                        <td>${myStatus.count}</td>
                                        <td><input type="checkbox"></td>
                                        <td>${admin.loginAcct}</td>
                                        <td>${admin.userName}</td>
                                        <td>${admin.email}</td>
                                        <td>
                                            <button type="button" class="btn btn-success btn-sm">分配</button>
                                            <a href="admin/to/edit/page.html?adminId=${admin.id}&keyword=${param.keyword}&pageNum=${requestScope.pageInfo.pageNum}" class="btn btn-primary btn-sm ">更新</a>
                                            <a href="admin/remove/${admin.id}/${requestScope.pageInfo.pageNum}/${param.keyword}.html" type="button" class="btn btn-danger btn-sm ">删除</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
                            <tfoot>
                            <tr>
                            <td colspan="6" align="center">
                                <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                            </td>
                        </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
