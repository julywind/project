<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="am-panel-footer">
    <c:set var="limit" value="${param.limit==null?25:param.limit}"/>
    <c:set var="currentPagef" value="${param.offset==null?1:(param.offset+limit-1)}"/>
    <%-- 这里因为算术除法的执行结果是浮点型,所以不能直接算出CurrentPage --%>
    <c:set var="currentPage" value="${(currentPagef-currentPagef%limit)/limit}"/>
    <c:set var="totalPage" value="${(result.totalCount+limit-1)/limit}"/>
    <c:set var="prePage" value="${currentPage-1}"/>
    <c:set var="nextPage" value="${currentPage+1}"/>
    <ul class="am-pagination">
        <li ${prePage<1?"class='am-disabled'":""}>
            <a href="./list.html?limit=${limit}&offset=${(prePage-1)*limit}">&laquo; Prev</a></li>
        <%--<li class="am-disabled"><a href="#">&laquo;</a></li>--%>
        <%--<spring:message code="uri" />--%>
        <c:forEach var="item" varStatus="status" begin="1" end="${totalPage}">
            <li ${status.index==(currentPage<1?1:currentPage)?'class="am-active"':''}>
                <a href="./list.html?limit=${limit}&offset=${(status.index-1)*limit}">${status.index}</a></li>
        </c:forEach>
        <%--<li><a href="#">&raquo;</a></li>--%>
        <li ${nextPage>totalPage?"class='am-disabled'":""}>
            <a href="./list.html?limit=${limit}&offset=${(nextPage-1)*limit}">Next &raquo;</a>
        </li>
    </ul>
</div>