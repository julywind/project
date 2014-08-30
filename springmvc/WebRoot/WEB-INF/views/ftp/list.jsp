<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <title>Landing Page | Amaze UI Example</title>
    <style>
        .head-fixed {
            padding-top: 50px;
        }

        .detail {
            padding: 15px 0;
            background: #fff;
            vertical-align: 50%;
        }

        .footer p {
            color: #7f8c8d;
            margin: 0;
            bottom: 0;
            position: fixed;
            padding: 15px 0;
            width: 100%;
            text-align: center;
            background: #2d3e50;
        }
    </style>
</head>
<body class="head-fixed">

<jsp:include page="../common-header.jsp">
    <jsp:param name="active_item" value="ftpuser"/>
</jsp:include>

<div class="detail">
    <div class="am-g am-container">

        <div class="am-panel am-panel-default">
            <div class="am-panel-hd">
                <h3 class="am-panel-title">FTP用户列表</h3>
            </div>

            <c:choose>
                <c:when test="${empty result.data}">
                    <ul class="am-list am-list-static">
                        <div class="am-panel-bd">
                            无数据
                        </div>
                    </ul>
                </c:when>

                <c:otherwise>
                    <table class="am-table am-table-bd am-table-bdrs am-table-striped am-table-hover">
                        <thead align="center">
                        <tr>
                            <th>序号</th>
                            <th>用户名</th>
                            <th>个人目录</th>
                            <th>状态</th>
                            <th>写权限</th>
                            <th>等待时间</th>
                            <th>上传限制</th>
                            <th>同时登陆限制</th>
                            <th>单机登陆限制</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="list" items="${result.data}" varStatus="status">
                            <tr>
                                <td align="center">${status.count}</td>
                                <td align="center">${list.userId}</td>
                                <td>${list.homedirectory}</td>
                                <td align="center">${list.enableflag?"启用":"停用"}</td>
                                <td align="center">${list.writePermission?"有":"无"}</td>
                                <td align="center">${list.idletime}秒</td>
                                <td align="center">${list.uploadrate}kb/s</td>
                                <td align="center">${list.maxloginnumber}</td>
                                <td align="center">${list.maxloginperip}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
            <jsp:include page="../pager-footer.jsp"/>
        </div>
    </div>
</div>

<footer class="footer">
    <p>© 2014 <a href="http://www.yunshipei.com" target="_blank">AllMobilize, Inc.</a> Licensed under <a
            href="http://opensource.org/licenses/MIT" target="_blank">MIT license</a>. by the AmazeUI Team.</p>
</footer>
<script src="${ctx}/assets/js/zepto.min.js"></script>
<script src="${ctx}/assets/js/amui.min.js"></script>
</body>
</html>