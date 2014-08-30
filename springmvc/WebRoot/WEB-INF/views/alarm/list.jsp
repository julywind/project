<%@ page import="java.security.Timestamp" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tags" prefix="date"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<jsp:useBean id="dataTime" class="java.util.Date" />


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
    <jsp:param name="active_item" value="alarm"/>
</jsp:include>

<div class="detail">
    <div class="am-g am-container">

        <div class="am-panel am-panel-default">
            <div class="am-panel-hd">
                <h3 class="am-panel-title">警报列表</h3>
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
                        <th>上传者</th>
                        <th>文件路径</th>
                        <th>上传时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="list" items="${result.data}" varStatus="status">
                        <tr>
                            <td align="center">${status.count}</td>
                            <td align="center">${list.userName}</td>
                            <td>${list.fileName}</td>
                            <td>
                                <date:date value="${list.genDate}"  parttern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
            </c:choose>

            <div class="am-panel-footer">
                <c:set var="limit" value="${param.limit==null?25:param.limit}"/>
                <c:set var="currentPage" value="${param.offset==null?1:(param.offset+limit-1)/limit}"/>
                <c:set var="totalPage" value="${(result.totalCount+limit-1)/limit}"/>
                <c:set var="prePage" value="${currentPage-1}"/>
                <c:set var="nextPage" value="${currentPage+1}"/>
                <ul class="am-pagination">
                    <li ${prePage<1?"class='am-disabled'":""}>
                        <a href="./list.html?limit=${limit}&offset=${(prePage-1)*limit}">&laquo; Prev</a></li>
                    <%--<li class="am-disabled"><a href="#">&laquo;</a></li>--%>
                    <%--<spring:message code="uri" />--%>
                    <c:forEach var="item" varStatus="status" begin="1" end="${totalPage}">
                        <li ${status.index==currentPage?'class="am-active"':''}>
                            <a href="./list.html?limit=${limit}&offset=${(status.index-1)*limit}">${status.index}</a></li>
                    </c:forEach>
                    <%--<li><a href="#">&raquo;</a></li>--%>
                    <li ${nextPage>totalPage?"class='am-disabled'":""}>
                        <a href="./list.html?limit=${limit}&offset=${(nextPage-1)*limit}">Next &raquo;</a>
                    </li>
                </ul>
            </div>
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