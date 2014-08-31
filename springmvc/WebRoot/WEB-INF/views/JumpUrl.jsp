<%@ page import="com.aokunsang.JsonResultBean" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
    response.setContentType("text/html; charset=utf-8");
    String RedirectUrl = ((JsonResultBean)request.getAttribute("result")).getRedirectUrl();
    if(RedirectUrl!=null && !RedirectUrl.startsWith("/"))
    {
        RedirectUrl = "/"+RedirectUrl;
    }
    response.sendRedirect(request.getContextPath()+RedirectUrl);
%>