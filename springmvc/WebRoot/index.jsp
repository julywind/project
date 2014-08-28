<%@ page language="java" pageEncoding="UTF-8"%>
<%
    response.setContentType("text/html; charset=utf-8");
    response.sendRedirect(request.getContextPath()+"/user/login.html");
%>