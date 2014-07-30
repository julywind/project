<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>提交表单</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  <body style="text-align: center">
	<form action="${ctx}/user/register" method="post">
	   userName：<input type="text" name="userName"><br/>
	   password：<input type="text" name="passWord"><br/>
	   firstName：<input type="text" name="firstName"><br/>
	   lastName：<input type="text" name="lastName"><br/>
	   age：<input type="text" name="age"><br/>
	   birthday：<input type="text" name="birthday"><br/>
	   <input type="submit" value="提交">
    </form>
  </body>
</html>
