<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<%--引入头部文件--%>
<%@include file="/WEB-INF/include-head.jsp"%>
<%--添加角色添加弹窗组件--%>
<%@include file="/WEB-INF/main-add-role.jsp"%>
<%--添加角色更新弹窗组件--%>
<%@include file="/WEB-INF/main-edit-role.jsp"%>
<%--添加角色删除确认弹窗组件--%>
<%@include file="/WEB-INF/main-delete-confirm.jsp"%>
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/fundraising/my-role.js"></script>
<script>
    $(function () {
        // 1. 准备初始数据
        window.pageNum = 1;
        window.pageSize = 5;
        window.keyword = "";

    //    2. 调用分页显示函数
        generatePage();

        // 3. 查询按钮点击事件
        $("#searchBtn").click(function () {
            //    ①. 获取关键词
            var searchWord = $("#keywordInput").val();

            //    ②. 设置keyword，并且调用分页显示函数刷新页面
            window.keyword = searchWord;
            generatePage();
        });

//    4. 新增角色按钮点击事件
        $("#addRoleBtn").click(function(){
            //显示模态框
            $("#addModel").modal("show");
        });

    //    5. 保存角色按钮的点击事件
        $("#saveRoleBtn").click(function () {
            // 获取角色姓名
            var roleName = $("#inputRoleName").val();
            // 发送Ajax请求
            $.ajax({
                url:"role/save.json",
                type:"POST",
                data:{
                    name: roleName,
                },
                dataType:"json",
                success:function (response) {
                    //获取结果
                    var result =response.result;
                    if(result == "success"){

                        layer.msg("操作成功！");
                        //跳转到最后一页
                        window.pageSize = 99999999;

                        //重新刷新页面生成数据
                        generatePage();
                    }
                    if (result == "fail"){
                        layer.msg("操作失败！" + response.message);
                    }
                },
                error:function (response) {
                    layer.msg(response.status + ":" +response.statusText);
                }
            });

            // 关闭模态框
            $('#addModel').modal('hide');

            //将输入框内容清空
            $("#inputRoleName").val("");
        });

        // 6. 添加按钮回显角色姓名
        // 首先找到所有“动态生成”的元素所附着的“静态”元素
        // on()函数的第一个参数是事件类型,第二个参数是找到真正要绑定事件的元素的选择器,第三个参数是事件的响应函数
        $("#rolePageBody").on("click",".pencilBtn",function () {

            // 显示模态框
            $("#editModel").modal("show");

            // 获取角色名称
            var roleName = $(this).parent().prev().text();

            // 获取角色Id，设置成为全局变量，传给后端保存
            window.roleId = this.id;

            // 将角色姓名显示在模态框上
            $("#editModel [name = roleName]").val(roleName);

        });

        // 7. 更新按钮修改角色姓名
        $("#updateRoleBtn").click(function () {

             // 获取角色名称
             var roleName = $("#editModel [name = roleName]").val();

             // 发送Ajax请求更新数据
             $.ajax({
                 url: "role/update.json",
                 type: "POST",
                 data:{
                     id: window.roleId,
                     name: roleName,
                 },
                 dataType: "json",
                 success:function (response) {
                     //获取结果
                     var result =response.result;
                     if(result == "success"){

                         layer.msg("操作成功！");

                         //重新刷新页面生成数据
                         generatePage();
                     }
                     if (result == "fail"){
                         layer.msg("操作失败！" + response.message);
                     }
                 },
                 error:function (response) {
                     layer.msg(response.status + ":" +response.statusText);
                 }
             });

            // 关闭模态框
            $('#editModel').modal('hide');
        });

        // 测试数据
        // var roleArray = [{roleId:5,roleName:"aaa"},{roleId:8,roleName:"bbbb"}];
        // showConfirmModal(roleArray);

    //    8. 点击确认删除按钮删除数据
        $("#removeRoleBtn").click(function () {
            $.ajax({
                url:"role/remove.json",
                type: "POST",
                data:JSON.stringify(window.roleIdArray),
                contentType:"application/json;charset=UTF-8",
                dataType:"json",
                success:function (response) {
                    //获取结果
                    var result =response.result;
                    if(result == "success"){

                        layer.msg("操作成功！");

                        //重新刷新页面生成数据
                        generatePage();
                    }
                    if (result == "fail"){
                        layer.msg("操作失败！" + response.message);
                    }
                },
                error:function (response) {
                    layer.msg(response.status + ":" +response.statusText);
                }
            });

            // 关闭模态框
            $('#confirmModel').modal('hide');
        });

    //    9. 点击单个删除按钮删除用户数据
        $("#rolePageBody").on("click",".removeBtn",function () {

            // 获取角色名称
            var roleName = $(this).parent().prev().text();

            // 设置角色数组
            var roleArray = [ { roleId:this.id , roleName:roleName}];

            // 显示模态框，回显数据
            showConfirmModal(roleArray);
        });

    //     10. 全选框实现
        $("#selectAllBtn").click(function () {

            // 获取当前全选框的状态
            var currentStatus = this.checked;

            // 将全选框的状态填充到每个用户的状态中
            $(".checkboxTd").prop("checked",currentStatus);
        });

    //     11. 全选，全不选的反向实现
        $("#rolePageBody").on("click",".checkboxTd",function () {

            // 获取当前选中框的个数
            var currentCount = $(".checkboxTd:checked").length;

            // 获取选择框的总个数
            var totalCount = $(".checkboxTd").length;

            // 给全选框赋予结果
            $("#selectAllBtn").prop("checked",currentCount == totalCount);

        });

    //    12. 多项选中删除绑定点击函数
        $("#deleteAllBtn").click(function () {

            //设置用户数组保存数据
            var roleArray = [];

            //循环获取选中框的id和用户姓名并写入数组
            $(".checkboxTd:checked").each(function () {
                var roleId =  this.id;
                var roleName = $(this).parent().next().text();
                roleArray.push({
                    roleId: roleId,
                    roleName: roleName,
                });
            });

            // 判断数组是否为0
            if(roleArray.length == 0){
                layer.msg("请选择一个删除！");
                return;
            }

            // 调用函数显示模态框
            showConfirmModal(roleArray);
        });

    });
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
                    <h3 class="panel-title"><i class="g"></i>数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keywordInput" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i class=""></i>查询</button>
                    </form>
                    <button  id="deleteAllBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;">删除</button>
                    <button id="addRoleBtn" type="button" class="btn btn-primary" style="float:right;"><i class=""></i>新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="selectAllBtn" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody">
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

