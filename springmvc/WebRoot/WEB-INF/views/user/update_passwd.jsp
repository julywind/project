<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <title>修改密码</title>
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
            <form class="am-form">
                <fieldset>
                    <legend>表单标题</legend>

                    <div class="am-form-group">
                        <label for="doc-ipt-email-1">邮件</label>
                        <input type="email" class="" id="doc-ipt-email-1" placeholder="输入电子邮件">
                    </div>

                    <div class="am-form-group">
                        <label for="doc-ipt-pwd-1">密码</label>
                        <input type="password" class="" id="doc-ipt-pwd-1" placeholder="设置个密码吧">
                    </div>

                    <div class="am-form-group">
                        <label for="doc-ipt-file-1">文件上传域</label>
                        <input type="file" id="doc-ipt-file-1">
                        <p class="am-form-help">请选择要上传的文件...</p>
                    </div>

                    <div class="am-checkbox">
                        <label>
                            <input type="checkbox"> 复选框，选我选我选我
                        </label>
                    </div>

                    <div class="am-radio">
                        <label>
                            <input type="radio" name="doc-radio-1" value="option1" checked>
                            单选框 - 选项1
                        </label>
                    </div>

                    <div class="am-radio">
                        <label>
                            <input type="radio" name="doc-radio-1" value="option2">
                            单选框 - 选项2
                        </label>
                    </div>

                    <div class="am-form-group">
                        <label class="am-checkbox-inline">
                            <input type="checkbox" value="option1"> 选我
                        </label>
                        <label class="am-checkbox-inline">
                            <input type="checkbox" value="option2"> 同时可以选我
                        </label>
                        <label class="am-checkbox-inline">
                            <input type="checkbox" value="option3"> 还可以选我
                        </label>
                    </div>

                    <div class="am-form-group">
                        <label class="am-radio-inline">
                            <input type="radio"  value="" name="docInlineRadio"> 每一分
                        </label>
                        <label class="am-radio-inline">
                            <input type="radio" name="docInlineRadio"> 每一秒
                        </label>
                        <label class="am-radio-inline">
                            <input type="radio" name="docInlineRadio"> 多好
                        </label>
                    </div>

                    <div class="am-form-group">
                        <label for="doc-select-1">下拉多选框</label>
                        <select id="doc-select-1">
                            <option value="option1">选项一...</option>
                            <option value="option2">选项二.....</option>
                            <option value="option3">选项三........</option>
                        </select>
                        <span class="am-form-caret"></span>
                    </div>

                    <div class="am-form-group">
                        <label for="doc-select-2">多选框</label>
                        <select multiple class="" id="doc-select-2">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                        </select>
                    </div>

                    <div class="am-form-group">
                        <label for="doc-ta-1">文本域</label>
                        <textarea class="" rows="5" id="doc-ta-1"></textarea>
                    </div>

                    <p><button type="submit" class="am-btn am-btn-default">提交</button></p>
                </fieldset>
            </form>
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