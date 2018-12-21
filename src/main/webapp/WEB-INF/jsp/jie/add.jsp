
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>发表问题 编辑问题 公用</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="keywords" content="fly,layui,前端社区">
    <meta name="description" content="Fly社区是模块化前端UI框架Layui的官网社区，致力于为web开发提供强劲动力">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/layui/css/layui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/global.css">
    <%@page contentType="text/html;charset=UTF-8" language="java" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <script src="${pageContext.request.contextPath}/res/jquery-3.3.1.js"></script>
    <script>
        $(function () {
            var param1=Math.ceil(Math.random()*20);
            var param2=Math.ceil(Math.random()*20);
            $('#check_people').text(param1+'+'+param2+'=?');
            var count=param1+param2;
            $('#L_vercode').blur(function () {
                if($(this).val()!=count){
                    $(this).val('');
                    $('#check_msg').text('答案错误，请重新输入');
                }else {
                    $('#check_msg').text('验证通过，请点击登录');
                }
            })
        })
    </script>
</head>

<body>

<%@include file="../common/header.jsp"%>

<div class="layui-container fly-marginTop">
    <div class="fly-panel" pad20 style="padding-top: 5px;">
        <!--<div class="fly-none">没有权限</div>-->
        <div class="layui-form layui-form-pane">
            <div class="layui-tab layui-tab-brief" lay-filter="user">
                <ul class="layui-tab-title">
                    <li class="layui-this">发表新帖<!-- 编辑帖子 --></li>
                </ul>
                <div class="layui-form layui-tab-content" id="LAY_ucm" style="padding: 20px 0;">
                    <div class="layui-tab-item layui-show">
                        <form action="${pageContext.request.contextPath}/jie/doadd" method="post">
                            <div class="layui-row layui-col-space15 layui-form-item">
                                <div class="layui-col-md3">
                                    <label class="layui-form-label">所在专栏</label>
                                    <div class="layui-input-block">
                                        <select lay-verify="required" name="topicCategoryId" lay-filter="column"
                                                <c:if test="${topic.id>0}">
                                                    disabled
                                                </c:if>
                                                >
                                            <c:forEach items="${categoryinfo}" var="cate">
                                                <c:choose>
                                                    <c:when test="${cate.id==topic.topicCategoryId} ">
                                                        <option value="${cate.id}" selected>${cate.name}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${cate.id}">${cate.name}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="layui-col-md9">
                                    <label for="L_title" class="layui-form-label">标题</label>
                                    <div class="layui-input-block">
                                        <input type="text" id="L_title" name="title" required lay-verify="required" autocomplete="off" class="layui-input" value="${topic.title}">
                                            <input type="hidden" name="id" value="${topic.id}">
                                    </div>
                                </div>
                            </div>
                            <div class="layui-form-item layui-form-text">
                                <div class="layui-input-block">
                                    <textarea id="L_content" name="content" required lay-verify="required" placeholder="详细描述" class="layui-textarea fly-editor" style="height: 260px;" >${topic.content}</textarea>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <label class="layui-form-label">悬赏飞吻</label>
                                    <div class="layui-input-inline" style="width: 190px;">
                                        <c:choose>
                                            <c:when test="${empty topic}">
                                            <select name="kissNum" id="kisnum">
                                                <option value="20">20</option>
                                                <option value="30">30</option>
                                                <option value="50">50</option>
                                                <option value="60">60</option>
                                                <option value="80">80</option>
                                            </select>
                                            </c:when>
                                            <c:otherwise>
                                                <select name="kissNum" id="kisnum" disabled>
                                                    <option value="${topic.kissNum}">${topic.kissNum}</option>
                                                </select>
                                            </c:otherwise>
                                        </c:choose>


                                    </div>
                                    <div class="layui-form-mid layui-word-aux">发表后无法更改飞吻</div>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label for="L_vercode" class="layui-form-label">人类验证</label>
                                <div class="layui-input-inline">
                                    <input type="text" id="L_vercode" name="vercode" required lay-verify="required" placeholder="请回答后面的问题" autocomplete="off" class="layui-input">
                                </div>
                                <div class="layui-form-mid">
                                    <span style="color: #c00;" id="check_people"></span>
                                </div>
                                <div class="layui-form-mid">
                                    <span style="color: #c00;" id="check_msg"></span>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <button class="layui-btn" lay-filter="*" lay-submit>立即发布</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="fly-footer">
    <p><a href="http://fly.layui.com/" target="_blank">Fly社区</a> 2017 &copy; <a href="http://www.layui.com/" target="_blank">layui.com 出品</a></p>
    <p>
        <a href="http://fly.layui.com/jie/3147/" target="_blank">付费计划</a>
        <a href="http://www.layui.com/template/fly/" target="_blank">获取Fly社区模版</a>
        <a href="http://fly.layui.com/jie/2461/" target="_blank">微信公众号</a>
    </p>
</div>

<script src="${pageContext.request.contextPath}/res/layui/layui.js"></script>
<script>
    layui.cache.page = 'jie';
    <%@include file="../common/cache-user.jsp"%>
    layui.config({
        version: "3.0.0"
        ,base: '${pageContext.request.contextPath}/res/mods/'
    }).extend({
        fly: 'index'
    }).use('fly',function () {
        
    });
</script>

</body>
</html>