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

            <ul class="am-list am-list-static">
                <c:choose>
                <c:when test="${empty result.data}">
                    <div class="am-panel-bd">
                        无数据
                    </div>
                </c:when>

                <c:otherwise>
                    <c:forEach var="list" items="${result.data}" varStatus="status">
                        <li >${status.count}|${list.userId}|${list.homedirectory}</li>
                    </c:forEach>
                </c:otherwise>
                </c:choose>

            </ul>
            <div class="am-panel-footer">Footer写点儿啥呢...</div>
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