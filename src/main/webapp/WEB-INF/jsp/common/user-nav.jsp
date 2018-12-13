<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="${pageContext.request.contextPath}/res/jquery-3.3.1.js"></script>
<ul class="layui-nav layui-nav-tree layui-inline" lay-filter="user">
    <li class="layui-nav-item">
        <a href="${pageContext.request.contextPath}/user/home/${userinfo.id}">
            <i class="layui-icon">&#xe609;</i>
            我的主页
        </a>
    </li>
    <li class="layui-nav-item  layui-this">
        <a href="${pageContext.request.contextPath}/user/index">
            <i class="layui-icon">&#xe612;</i>
            用户中心
        </a>
    </li>
    <li class="layui-nav-item">
        <a href="${pageContext.request.contextPath}/user/set">
            <i class="layui-icon">&#xe620;</i>
            基本设置
        </a>
    </li>
    <li class="layui-nav-item">
        <a href="${pageContext.request.contextPath}/user/message">
            <i class="layui-icon">&#xe611;</i>
            我的消息
        </a>
    </li>
</ul>