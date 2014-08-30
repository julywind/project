<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
      content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp"/>
<link rel="alternate icon" type="image/png" href="${ctx}/assets/i/favicon.png">
<link rel="stylesheet" href="${ctx}/assets/css/amui.all.min.css"/>
<style>
    .head {
        margin-bottom: 0;
        width: 100%;
        position: fixed;
        z-index: 1;
        left: 0;
        top: 0;
    }
</style>

<header class="am-topbar head">
    <h1 class="am-topbar-brand">
        <a href="#">导航>></a>
    </h1>

    <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-secondary am-show-sm-only"
            data-am-collapse="{target: '#collapse-head'}"><span class="am-sr-only">导航切换</span> <span
            class="am-icon-bars"></span></button>

    <div class="am-collapse am-topbar-collapse" id="collapse-head">
        <ul class="am-nav am-nav-pills am-topbar-nav">
            <li ${param.active_item=='home'?'class="am-active"':''}><a href="${ctx}/">首页</a></li>
            <li ${param.active_item=='alarm'?'class="am-active"':''}><a href="${ctx}/alarm/list.html">警报</a></li>
            <li ${param.active_item=='user'?'class="am-active"':''}><a href="${ctx}/user/list.html">用户</a></li>
            <li ${param.active_item=='ftpuser'?'class="am-active"':''}><a href="${ctx}/ftp/list.html">FTP账号</a></li>
            <%--<li class="am-dropdown" data-am-dropdown>
                <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
                    下拉菜单 <span class="am-icon-caret-down"></span>
                </a>
                <ul class="am-dropdown-content">
                    <li class="am-dropdown-header">标题</li>
                    <li><a href="#">1. 默认样式</a></li>
                    <li><a href="#">2. 基础设置</a></li>
                    <li><a href="#">3. 文字排版</a></li>
                    <li><a href="#">4. 网格系统</a></li>
                </ul>
            </li>--%>
        </ul>
        <div class="am-topbar-right">
            <a href="${ctx}/user/logout.html" class="am-btn am-btn-link am-topbar-btn am-btn-sm"><span class="am-icon-sign-out"></span> 登出</a>
        </div>
        <div class="am-topbar-right">
            <a href="${ctx}/user/register.html" class="am-btn am-btn-link am-topbar-btn am-btn-sm" role="button"><span class="am-icon-user"></span> 添加用户</a>
        </div>
        <div class="am-topbar-right">
            <a data-am-modal="{target: '#modal-update-password'}" class="am-btn am-btn-link am-topbar-btn am-btn-sm" role="button"><span class="am-icon-pencil"></span> 修改密码</a>
        </div>
    </div>

    <%--<div class="am-popup" id="modal-update-password">
        <div class="am-popup-inner">
            <div class="col-md-8 col-sm-centered">
                <form class="am-form">
                    <fieldset class="am-form-set">
                        <input type="text" placeholder="取个名字">
                        <input type="text" placeholder="设个密码">
                        <input type="email" placeholder="填下邮箱">
                    </fieldset>
                    <button type="submit" class="am-btn am-btn-primary am-btn-block">注册个账号</button>
                </form>
            </div>
        </div>
    </div>--%>


</header>
<div class="am-modal am-modal-prompt" tabindex="-1" id="modal-update-password">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">修改密码</div>
        <div class="am-modal-bd">
            <form class="am-form" action="${ctx}/user/updatePasswd" method="post">
                <fieldset class="am-form-set">
                    <input type="hidden" name="id" value="${sessionScope.logged_user.id}"/>
                    <input type="text" class="am-form-field" name="userName" value="${sessionScope.logged_user.userName}" readonly/>
                    <input type="password" name="passWord" required="true" placeholder="原密码">
                    <input type="password" name="newPassword" required="true" placeholder="新密码">
                    <input type="password" name="rePassword" required="true" placeholder="重复新密码">
                </fieldset>
                <button type="submit" class="am-btn am-btn-primary am-btn-block" data-am-modal-cancel>提交</button>
            </form>
        </div>
        <%--<div class="am-modal-footer">
            <span class="am-modal-btn" data-am-modal-cancel>取消</span>
            <span class="am-modal-btn" data-am-modal-confirm>提交</span>
        </div>--%>

    </div>
</div>