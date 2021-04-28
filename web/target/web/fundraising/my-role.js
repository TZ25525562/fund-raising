// 显示删除确认模态框
function showConfirmModal(roleArray) {

    // 显示模态框
    $("#confirmModel").modal("show");

    // 清空之前所有角色用户名数据
    $("#deleteArray").empty();

    // 定义全局的用户Id数组
    window.roleIdArray = [];

    // 取出所有用户名并显示在模态框中
    for (var i = 0; i < roleArray.length; i++ ){
        var role = roleArray[i];
        var roleId = role.roleId;
        // 往全局变量中写入数据
        window.roleIdArray.push(roleId);
        var roleName = role.roleName;
        $("#deleteArray").append(roleName + "<br>");
    };

}

// 执行分页，生成页面效果，任何时候调用函数都会重新加载页面
function generatePage() {

//    1. 获取分页数据
    let pageInfo = getPageInfo();

//    2. 填充表格
    fillTableBody(pageInfo);
}

// 从后端获取pageInfo数据
function getPageInfo() {
    // 发送Ajax请求，获取数据
    var ajaxResult = $.ajax({
        url: "role/get/page/info.json",
        type: "POST",
        data:{
            "pageNum": window.pageNum,
            "pageSize": window.pageSize,
            "keyword" : window.keyword,
        },
        async: false,
        dataType:"json",
    });
    // console.log(ajaxResult);
    // 获取PageInfo数据
    let responseJSON = ajaxResult.responseJSON;
    //获取状态码
    var status = ajaxResult.status;
    // 如果状态码不是200 ，显示报错原因
    if(status != 200){
        layer.msg("失败！原因为：" + ajaxResult.statusText);
        return null;
    }
    // 服务器段没有数据传入，返回错误信息
    if(responseJSON.result == "fail"){
        layer.msg("失败！原因为：" + responseJSON.message);
        return null;
    }

    // 返回PageInfo对象
    return responseJSON.data;

}

//填充表格
function fillTableBody(pageInfo) {

    // 清除tbody 中的旧的内容
    $("#rolePageBody").empty();

// 这里清空是为了让没有搜索结果时不显示页码导航条
    $("#Pagination").empty();

// 判断pageInfo 对象是否有效
    if(pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
        $("#rolePageBody").append("<tr><td colspan='4' align='center'>抱歉！没有查询到您搜索的数据！</td></tr>");
        return ;
    }
    // 使用pageInfo 的list 属性填充tbody
    for(var i = 0; i < pageInfo.list.length; i++) {
        var role = pageInfo.list[i];
        var roleId = role.id;
        var roleName = role.name;
        var numberTd = "<td>"+(i+1)+"</td>";
        var checkboxTd = "<td><input  class='checkboxTd' type='checkbox'></td>";
        var roleNameTd = "<td>"+roleName+"</td>";
        var checkBtn = "<button type='button' class='btn btn-success btn-sm'>分配</button>";
        // 点击回传id属性
        var pencilBtn = "<button type='button' id='"+roleId+"' class='btn btn-primary btn-sm pencilBtn'>更新</button>";
        // 点击回传id属性
        var removeBtn = "<button id='"+roleId+"' type='button' class='btn btn-danger btn-sm removeBtn'>删除</button>";
        var buttonTd = "<td>"+checkBtn+" "+pencilBtn+" "+removeBtn+"</td>";
        var tr = "<tr>"+numberTd+checkboxTd+roleNameTd+buttonTd+"</tr>";
        $("#rolePageBody").append(tr);
    }

    // 生成分页导航条
    generateNavigator(pageInfo);
}

// 生成分页页码条
function generateNavigator(pageInfo) {
    // 获取总记录数
    var totalRecord = pageInfo.total;
// 声明相关属性
    var properties = {
        "num_edge_entries": 3,
        "num_display_entries": 5,
        "callback": paginationCallback,
        "items_per_page": pageInfo.pageSize,
        "current_page": pageInfo.pageNum - 1,
        "prev_text": "上一页",
        "next_text": "下一页",
    }

    // 调用pagination()函数
    $("#Pagination").pagination(totalRecord, properties);
}

// 翻页时的回调函数
function paginationCallback(pageIndex,jQuery) {

    // 修改window 对象的pageNum 属性
    window.pageNum = pageIndex + 1;

    // 调用分页函数
    generatePage();

// 取消页码超链接的默认行为
    return false;
}