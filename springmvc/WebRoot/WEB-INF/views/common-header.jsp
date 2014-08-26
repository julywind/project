<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<header class="am-topbar head">
    <%--<h1 class="am-topbar-brand">
        <a href="#">工地安全信息管理系统</a>
    </h1>--%>

    <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-secondary am-show-sm-only"
            data-am-collapse="{target: '#collapse-head'}"><span class="am-sr-only">导航切换</span> <span
            class="am-icon-bars"></span></button>

    <div class="am-collapse am-topbar-collapse" id="collapse-head">
        <ul class="am-nav am-nav-pills am-topbar-nav">
            <li class="am-active"><a href="${ctx}/">首页</a></li>
            <li><a href="#">警报</a></li>
            <li><a href="#">用户</a></li>
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
            <a href="${ctx}/user/register.html" class="am-btn am-btn-link am-topbar-btn am-btn-sm" role="button"><span class="am-icon-pencil"></span> 修改密码</a>
        </div>
    </div>
</header>