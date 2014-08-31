<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <title>工地管理系统</title>

    <style>
        .head-fixed {
            padding-top: 50px;
        }
        .get {
            background: #1E5B94;
            color: #fff;
            text-align: center;
            padding: 100px 0;
        }

        .get-title {
            font-size: 200%;
            border: 2px solid #fff;
            padding: 20px;
            display: inline-block;
        }

        .get-btn {
            background: #fff;
        }

        .detail {
            background: #fff;
        }

        .detail-h2 {
            text-align: center;
            font-size: 150%;
            margin: 40px 0;
        }

        .detail-h3 {
            color: #1f8dd6;
        }

        .detail-p {
            color: #7f8c8d;
        }

        .detail-mb {
            margin-bottom: 30px;
        }

        .hope {
            background: #0bb59b;
            padding: 50px 0;
        }

        .hope-img {
            text-align: center;
        }

        .hope-hr {
            border-color: #149C88;
        }

        .hope-title {
            font-size: 140%;
        }

        .about {
            background: #fff;
            padding: 40px 0;
            color: #7f8c8d;
        }

        .about-color {
            color: #34495e;
        }

        .about-title {
            font-size: 180%;
            padding: 30px 0 50px 0;
            text-align: center;
        }

        .footer p {
            color: #7f8c8d;
            margin: 0;
            padding: 15px 0;
            text-align: center;
            background: #2d3e50;
        }
    </style>
</head>
<body class="head-fixed">

<jsp:include page="common-header.jsp">
    <jsp:param name="active_item" value="home"/>
</jsp:include>
<div class="get">
    <div class="am-g">
        <div class="col-lg-12">
            <h1 class="get-title">工地安全信息管理系统</h1>

            <p>
                期待你的参与，共同打造一个简单易用的安全警报系统
            </p>

            <p>
                <a href="#" class="am-btn am-btn-sm get-btn">下载Android移动终端√</a>
            </p>
        </div>
    </div>
</div>

<div class="detail">
    <div class="am-g am-container">
        <div class="col-lg-12">
            <h2 class="detail-h2">随时随地 知道一切顺利</h2>

            <div class="am-g">
                <div class="col-lg-3 col-md-6 col-sm-12 detail-mb">

                    <h3 class="detail-h3">
                        <i class="am-icon-mobile am-icon-sm"></i>
                        为移动而生
                    </h3>

                    <p class="detail-p">
                        工地安全系统为移动而生的理由
                    </p>
                </div>
                <div class="col-lg-3 col-md-6 col-sm-12 detail-mb">
                    <h3 class="detail-h3">
                        <i class="am-icon-cogs am-icon-sm"></i>
                        组件丰富，模块化
                    </h3>

                    <p class="detail-p">
                        模块化的描述
                    </p>
                </div>
                <div class="col-lg-3 col-md-6 col-sm-12 detail-mb">
                    <h3 class="detail-h3">
                        <i class="am-icon-check-square-o am-icon-sm"></i>
                        实时高效
                    </h3>

                    <p class="detail-p">
                        实时推送至移动终端，及时掌握第一手安全隐患
                    </p>
                </div>
                <div class="col-lg-3 col-md-6 col-sm-12 detail-mb">
                    <h3 class="detail-h3">
                        <i class="am-icon-send-o am-icon-sm"></i>
                        轻量级，高性能
                    </h3>

                    <p class="detail-p">
                        高性能的描述
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
<%--

<div class="hope">
    <div class="am-g am-container">
        <div class="col-lg-4 col-md-6 col-sm-12 hope-img">
            <img src="${ctx}/assets/i/examples/landing.png" alt="" data-am-scrollspy="{animation:'slide-left', repeat: false}">
            <hr class="am-article-divider am-show-sm-only hope-hr">
        </div>
        <div class="col-lg-8 col-md-6 col-sm-12">
            <h2 class="hope-title">同我们一起打造你的前端框架</h2>

            <p>
                在知识爆炸的年代，我们不愿成为知识的过客，拥抱开源文化，发挥社区的力量，参与到Amaze Ui开源项目能获得自我提升。
            </p>
        </div>
    </div>
</div>
--%>

<div class="about">
    <div class="am-g am-container">
        <div class="col-lg-12">
            <h2 class="about-title about-color">非常欢迎大家反馈意见和建议</h2>

            <div class="am-g">
                <div class="col-lg-6 col-md-4 col-sm-12">
                    <form class="am-form">
                        <label for="name" class="about-color">你的姓名</label>
                        <input id="name" type="text">
                        <br/>
                        <label for="email" class="about-color">你的邮箱</label>
                        <input id="email" type="email">
                        <br/>
                        <label for="message" class="about-color">你的留言</label>
                        <textarea id="message"></textarea>
                        <br/>
                        <button type="submit" class="am-btn am-btn-primary am-btn-sm"><i class="am-icon-check"></i> 提 交</button>
                    </form>
                    <hr class="am-article-divider am-show-sm-only">
                </div>

                <div class="col-lg-6 col-md-8 col-sm-12">
                    <h4 class="about-color">关于我们</h4>

                    <p>德康视讯
                        <i>这里放公司简介。</i></p>
                    <h4 class="about-color">团队介绍</h4>
                    <p><i>这里放团队介绍。</i></p>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="common-footer.jsp"/>
</body>
</html>