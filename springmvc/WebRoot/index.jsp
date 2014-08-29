<%@ page language="java" pageEncoding="UTF-8"%>
<%
    response.setContentType("text/html; charset=utf-8");
    System.out.println(request.getContextPath()+"/user/login.html");
    response.sendRedirect(request.getContextPath()+"/user/login.html");
%>