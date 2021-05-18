<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<div id="addMenuModel" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加菜单项</h4>
            </div>
            <div class="modal-body">
                <form class="form-signin" role="form">
                    <div class="form-group has-success has-feedback">
                        <input  type="text" class="form-control" name="url" id="inputUrl" placeholder="请输入菜单项地址" autofocus>
                        <span class=" form-control-feedback"></span>
                    </div>
                    <div class="form-group has-success has-feedback">
                        <input  type="text" class="form-control" name="name" id="inputName" placeholder="请输入菜单项名字" autofocus>
                        <span class=" form-control-feedback"></span>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button id="saveMenuBtn" type="submit" class="btn btn-primary">保存</button>
                <button type="reset" id="resetMenuBtn" class="btn btn-danger">重置</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>

