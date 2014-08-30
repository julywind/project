<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <jsp:param name="active_item" value="user"/>
</jsp:include>

<div class="detail">
    <div class="am-g am-container">

        <div class="am-panel am-panel-default">
            <div class="am-panel-hd">
                <h3 class="am-panel-title">用户列表</h3>
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
                            <th>姓</th>
                            <th>名</th>
                            <th>年龄</th>
                            <th>电话号码</th>
                            <th>登陆权限</th>
                            <th>管理员权限</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="list" items="${result.data}" varStatus="status">
                            <tr>
                                <td align="center">${status.count}</td>
                                <td align="center">${list.userName}</td>
                                <td align="center">${list.firstName}kb/s</td>
                                <td align="center">${list.lastName}</td>
                                <td align="center">${list.age}</td>
                                <td align="center">${list.phoneNumber}</td>
                                <td align="center">
                                    ${fn:substring(list.authority,0,1)=="1"?"有":"无"}
                                </td>
                                <td align="center">
                                        ${fn:substring(list.authority,1,2)=="1"?"有":"无"}
                                </td>

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