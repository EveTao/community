
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>用户主页</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="keywords" content="fly,layui,前端社区">
    <meta name="description" content="Fly社区是模块化前端UI框架Layui的官网社区，致力于为web开发提供强劲动力">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/layui/css/layui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/global.css">
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <script src="${pageContext.request.contextPath}/res/jquery-3.3.1.js"></script>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/layui/css/layui.css" media="all">
    <script src="${pageContext.request.contextPath}/res/layui/layui.all.js"></script>
    <script>
        var isOk = false;
        function getPageData(pageIndex) {
            var req = new XMLHttpRequest();
            req.open('get','${pageContext.request.contextPath}/getpagedata/'+pageIndex,true);
            req.send();
            req.onload = function () {
//                alert(req.responseText)
                var jsonObj = JSON.parse(req.responseText);
                var laypage = layui.laypage;
                //执行一个laypage实例
                if(isOk == false)
                {
                    laypage.render({
                        elem: 'page' //注意，这里的 test1 是 ID，不用加 # 号
                        ,limit:2
                        ,count:jsonObj.total//数据总数，从服务端得到
                        ,jump: function(obj, first){
                            //obj包含了当前分页的所有参数，比如：
//                        console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
//                        console.log(obj.limit); //得到每页显示的条数
                            if(!first)
                            {
                                getPageData(obj.curr);
                            }
                        }
                    });
                    isOk = true;
                }
                var getTpl = demo.innerHTML;
                var view = document.getElementById('data');
                layui.laytpl(getTpl).render(jsonObj, function(html){
                    view.innerHTML = html;
                });
            }
        }
        window.onload = function () {
            getPageData(1);
        }
    </script>
</head>
<body style="margin-top: 65px;">

<%@include file="../common/header.jsp"%>

<div class="fly-home fly-panel" style="background-image: url();">
    <c:choose>
        <c:when test="${user.picPath==''}">
            <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg" alt="贤心">
        </c:when>
        <c:otherwise>
            <img src="${pageContext.request.contextPath}/res/uploadImg/${user.picPath}" alt="贤心">
        </c:otherwise>
    </c:choose>

    <i class="iconfont icon-renzheng" title="Fly社区认证"></i>
    <h1>
        ${user.nickname}
            <c:choose>
                <c:when test="${user.sex==0}">
                    <i class="iconfont icon-nan"></i>
                </c:when>
                <c:otherwise>
                    <i class="iconfont icon-nv"></i>
                </c:otherwise>
            </c:choose>
        <i class="layui-badge fly-badge-vip">VIP${user.vipGrade}</i>
        <!--
        <span style="color:#c00;">（管理员）</span>
        <span style="color:#5FB878;">（社区之光）</span>
        <span>（该号已被封）</span>
        -->
    </h1>

    <p style="padding: 10px 0; color: #5FB878;">认证信息：layui 作者</p>

    <p class="fly-home-info">
        <i class="iconfont icon-kiss" title="飞吻"></i><span style="color: #FF7200;">${user.kissNum}</span>
        <i class="iconfont icon-shijian"></i><span>${joinTime}&nbsp;加入</span>
        <c:choose>
            <c:when test="${user.city==''}">
                <i class="iconfont icon-chengshi"></i><span>来自地球</span>
            </c:when>
            <c:otherwise>
                <i class="iconfont icon-chengshi"></i><span>${user.city}</span>
            </c:otherwise>
        </c:choose>

    </p>
    <c:choose>
        <c:when test="${user.sign==''}">
            <p class="fly-home-sign">（人生仿若一场修行）</p>
        </c:when>
        <c:otherwise>
            <p class="fly-home-sign">${user.sign}</p>
        </c:otherwise>
    </c:choose>


    <div class="fly-sns" data-user="">
        <a href="javascript:;" class="layui-btn layui-btn-primary fly-imActive" data-type="addFriend">加为好友</a>
        <a href="javascript:;" class="layui-btn layui-btn-normal fly-imActive" data-type="chat">发起会话</a>
    </div>

</div>

<div class="layui-container">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md6 fly-home-jie">
            <div class="fly-panel">
                <h3 class="fly-panel-title">${user.nickname} 最近的提问</h3>
                <ul class="jie-row">
                    <c:forEach var="t" items="${topic}">
                        <li>
                            <c:if test="${t.isGood==1}">
                                <span class="fly-jing">精</span>
                            </c:if>
                            <a href="${pageContext.request.contextPath}/jie/detail/${t.id}" class="jie-title"> ${t.title}</a>
                            <i>${t.createTime}</i>
                            <em class="layui-hide-xs">1136阅/27答</em>
                        </li>
                    </c:forEach>
                    <!-- <div class="fly-none" style="min-height: 50px; padding:30px 0; height:auto;"><i style="font-size:14px;">没有发表任何求解</i></div> -->
                </ul>
            </div>
        </div>

        <div class="layui-col-md6 fly-home-da">
            <div class="fly-panel">
                <h3 class="fly-panel-title">${user.nickname} 最近的回答</h3>
                <ul class="home-jieda">
                    <c:forEach items="${comment}" var="com">
                    <li>
                        <p>
                            <span>${com.comment_time}</span>
                            在<a href="${pageContext.request.contextPath}/jie/detail/${com.topic_id }" target="_blank">${com.title}</a>中回答：
                        </p>
                        <div class="home-dacontent">
                            ${com.comment_content}
                        </div>
                    </li>
                    </c:forEach>
                    <!-- <div class="fly-none" style="min-height: 50px; padding:30px 0; height:auto;"><span>没有回答任何问题</span></div> -->
                </ul>
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
    layui.cache.page = 'user';
    layui.cache.user = {
        username: '游客'
        ,uid: -1
        ,avatar: '${pageContext.request.contextPath}/res/images/avatar/00.jpg'
        ,experience: 83
        ,sex: '男'
    };
    layui.config({
        version: "3.0.0"
        ,base: '${pageContext.request.contextPath}/res/mods/'
    }).extend({
        fly: 'index'
    }).use(['fly', 'face'], function(){
        var $ = layui.$
            ,fly = layui.fly;
        //如果你是采用模版自带的编辑器，你需要开启以下语句来解析。

        $('.home-dacontent').each(function(){
            var othis = $(this), html = othis.html();
            othis.html(fly.content(html));
        });

    });

</script>
<%--{{#  layui.each(d.datas, function(index, item){ }}
<li>
    {{item.nickname}}
    <p>
        <span>{{item.comment_time}}</span>
        在<a href="${pageContext.request.contextPath}/jie/detail/{{item.topic_id}}" target="_blank">{{item.nickname}}</a>中回答：
    </p>
    <div class="home-dacontent">
        {{item.nickname}}
    </div>

</li>
{{#  }); }}--%>


</body>
</html>