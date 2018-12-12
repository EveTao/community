

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>基于 layui 的极简社区页面模版</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="keywords" content="fly,layui,前端社区">
    <meta name="description" content="Fly社区是模块化前端UI框架Layui的官网社区，致力于为web开发提供强劲动力">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/layui/css/layui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/global.css">
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <script src="${pageContext.request.contextPath}/res/jquery-3.3.1.js"></script>

    </head>
<body>

<%@include file="../common/header.jsp"%>

<div class="fly-panel fly-column">
    <div class="layui-container">
        <ul class="layui-clear">
            <li class="layui-hide-xs"><a href="/">首页</a></li>
            <c:forEach items="${category}" var="cate">
                <c:choose>
                    <c:when test="${cate.id==categoryid }">
                        <li class="layui-this"><a href="${pageContext.request.contextPath}/jie/index/${cate.id }">${cate.name}
                                <c:if test="${cate.name=='分享'}">
                                    <span class="layui-badge-dot"></span>
                                </c:if>
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/jie/index/${cate.id }">${cate.name}
                            <c:if test="${cate.name=='分享'}">
                                <span class="layui-badge-dot"></span>
                            </c:if>
                        </a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <li class="layui-hide-xs layui-hide-sm layui-show-md-inline-block"><span class="fly-mid"></span></li>

            <c:if test="${!empty userinfo}">
                <li class="layui-hide-xs layui-hide-sm layui-show-md-inline-block"><a href="${pageContext.request.contextPath}/user/index.html">我发表的贴</a></li>
                <%--<li class="layui-hide-xs layui-hide-sm layui-show-md-inline-block"><a href="${pageContext.request.contextPath}/user/index.html#collection">我收藏的贴</a></li>--%>
            </c:if>
        </ul>

        <div class="fly-column-right layui-hide-xs">
            <span class="fly-search"><i class="layui-icon"></i></span>
            <a href="${pageContext.request.contextPath}/jie/add" class="layui-btn">发表新帖</a>
        </div>
        <div class="layui-hide-sm layui-show-xs-block" style="margin-top: -10px; padding-bottom: 10px; text-align: center;">
            <a href="${pageContext.request.contextPath}/jie/add" class="layui-btn">发表新帖</a>
        </div>
    </div>
</div>

<div class="layui-container">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md8">
            <div class="fly-panel" style="margin-bottom: 0;">

                <div class="fly-panel-title fly-filter">
                    <a href="" class="layui-this">综合</a>
                    <span class="fly-mid"></span>
                    <a href="">未结</a>
                    <span class="fly-mid"></span>
                    <a href="">已结</a>
                    <span class="fly-mid"></span>
                    <a href="">精华</a>
                    <span class="fly-filter-right layui-hide-xs">
            <a href="" class="layui-this">按最新</a>
            <span class="fly-mid"></span>
            <a href="">按热议</a>
          </span>
                </div>

                <ul class="fly-list">
                    <div id="data">
                    </div>

                    <%--<script id="demo" type="text/html">
                            {{#  layui.each(d.datas, function(index, item){ }}
                            <li>
                                &lt;%&ndash;{{item.nickname}}&ndash;%&gt;
                                <a href="${pageContext.request.contextPath}/user/home/{{item.userid}}" class="fly-avatar">
                                    <c:choose>
                                        <c:when test="{{item.pic_path != ''}}">
                                            <img src="${pageContext.request.contextPath}/res/uploadImg/{{item.pic_path}}" alt="{{item.userid}}">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg">
                                        </c:otherwise>
                                    </c:choose>
                                </a>
                                <h2>
                                    <a class="layui-badge">{{item.name}}</a>
                                    <a href="{{pageContext.request.contextPath}/jie/detail/{{item.id}}">{{item.title}}</a>
                                </h2>
                                <div class="fly-list-info">
                                    <a href="${pageContext.request.contextPath}/user/home/{{item.userid}}" link>
                                        <cite>{{item.nickname}}</cite>
                                        <!--
                                        <i class="iconfont icon-renzheng" title="认证信息：XXX"></i>
                                        <i class="layui-badge fly-badge-vip">VIP3</i>
                                        -->
                                    </a>
                                    <span>{{item.create_time}}</span>
                                    <span class="fly-list-kiss layui-hide-xs" title="悬赏飞吻"><i class="iconfont icon-kiss"></i> {{item.kiss_num}}</span>
                                    <!--<span class="layui-badge fly-badge-accept layui-hide-xs">已结</span>-->
                                    <span class="fly-list-nums">
                <i class="iconfont icon-pinglun1" title="回答"></i> {{item.count}}
              </span>
                                </div>
                            </li>
                            {{#  }); }}
                    </script>--%>
                </ul>

                <!-- <div class="fly-none">没有相关数据</div> -->

                <div style="text-align: center">
                    <div id="page">
                    </div>

                </div>

            </div>
        </div>
        <div class="layui-col-md4">
            <dl class="fly-panel fly-list-one">
                <dt class="fly-panel-title">本周热议</dt>
                <dd>
                    <a href="">基于 layui 的极简社区页面模版</a>
                    <span><i class="iconfont icon-pinglun1"></i> 16</span>
                </dd>
                <dd>
                    <a href="">基于 layui 的极简社区页面模版</a>
                    <span><i class="iconfont icon-pinglun1"></i> 16</span>
                </dd>
                <dd>
                    <a href="">基于 layui 的极简社区页面模版</a>
                    <span><i class="iconfont icon-pinglun1"></i> 16</span>
                </dd>
                <dd>
                    <a href="">基于 layui 的极简社区页面模版</a>
                    <span><i class="iconfont icon-pinglun1"></i> 16</span>
                </dd>
                <dd>
                    <a href="">基于 layui 的极简社区页面模版</a>
                    <span><i class="iconfont icon-pinglun1"></i> 16</span>
                </dd>
                <dd>
                    <a href="">基于 layui 的极简社区页面模版</a>
                    <span><i class="iconfont icon-pinglun1"></i> 16</span>
                </dd>
                <dd>
                    <a href="">基于 layui 的极简社区页面模版</a>
                    <span><i class="iconfont icon-pinglun1"></i> 16</span>
                </dd>
                <dd>
                    <a href="">基于 layui 的极简社区页面模版</a>
                    <span><i class="iconfont icon-pinglun1"></i> 16</span>
                </dd>
                <dd>
                    <a href="">基于 layui 的极简社区页面模版</a>
                    <span><i class="iconfont icon-pinglun1"></i> 16</span>
                </dd>
                <dd>
                    <a href="">基于 layui 的极简社区页面模版</a>
                    <span><i class="iconfont icon-pinglun1"></i> 16</span>
                </dd>

                <!-- 无数据时 -->
                <!--
                <div class="fly-none">没有相关数据</div>
                -->
            </dl>

            <div class="fly-panel">
                <div class="fly-panel-title">
                    这里可作为广告区域
                </div>
                <div class="fly-panel-main">
                    <a href="" target="_blank" class="fly-zanzhu" style="background-color: #393D49;">虚席以待</a>
                </div>
            </div>

            <div class="fly-panel fly-link">
                <h3 class="fly-panel-title">友情链接</h3>
                <dl class="fly-panel-main">
                    <dd><a href="http://www.layui.com/" target="_blank">layui</a><dd>
                    <dd><a href="http://layim.layui.com/" target="_blank">WebIM</a><dd>
                    <dd><a href="http://layer.layui.com/" target="_blank">layer</a><dd>
                    <dd><a href="http://www.layui.com/laydate/" target="_blank">layDate</a><dd>
                    <dd><a href="mailto:xianxin@layui-inc.com?subject=%E7%94%B3%E8%AF%B7Fly%E7%A4%BE%E5%8C%BA%E5%8F%8B%E9%93%BE" class="fly-link">申请友链</a><dd>
                </dl>
            </div>

        </div>
    </div>
</div>

<div class="fly-footer">
    <p><a href="http://fly.layui.com/" target="_blank">Fly社区</a> 2017 &copy; <a href="http://www.layui.com/" target="_blank">layui.com 出品</a></p>
    <p>
        <a href="http://fly.layui.com/${pageContext.request.contextPath}/jie/3147/" target="_blank">付费计划</a>
        <a href="http://www.layui.com/template/fly/" target="_blank">获取Fly社区模版</a>
        <a href="http://fly.layui.com/${pageContext.request.contextPath}/jie/2461/" target="_blank">微信公众号</a>
    </p>
</div>

<script src="${pageContext.request.contextPath}/res/layui/layui.js"></script>
<script>
    layui.cache.page = 'jie';
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
    }).use(['fly','laypage','laytpl'],function () {
        var $ = layui.jquery;
        function fillCurrentPageData(jsonObj) {
            var getTpl = demo.innerHTML;
            var view = document.getElementById('data');
            layui.laytpl(getTpl).render(jsonObj, function(html){
                view.innerHTML = html;
            });
        }
        function getPageData(pageInfo) {
            if(!pageInfo)
            {
                pageInfo = {};
                pageInfo.pageSize = 5;
                pageInfo.pageIndex = 1;
            }
            $.ajax({
                url:'${pageContext.request.contextPath}/getTopicPage/${categoryid}/',
                type:'post',
                dataType:'json',
                data:pageInfo,
                success:function (jsonObj) {
                    //执行一个laypage实例,渲染分頁代碼
                    var laypage = layui.laypage;
                    laypage.render({
                        elem: 'page' //注意，这里的 test1 是 ID，不用加 # 号
                        , limit: pageInfo.pageSize
                        , curr: pageInfo.pageIndex
                        , count: jsonObj.total//数据总数，从服务端得到
                        , first: "首页"
                        , last: "尾页"
                        , jump: function (obj, first) {
                            //obj包含了当前分页的所有参数，比如：
//                        console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
//                        console.log(obj.limit); //得到每页显示的条数
                            if (!first) {
                                pageInfo.pageIndex = obj.curr;
                                pageInfo.pageSize = obj.limit;
                                getPageData(pageInfo);
                            }
                        }
                    });
                    //替換帖子內容
                    fillCurrentPageData(jsonObj);
                }
            })
        }
        $(function () {
            getPageData();
        })

    });
</script>
<script id="demo" type="text/html">
    {{#  layui.each(d.datas, function(index, topic){ }}
    <li>
        <a href="${pageContext.request.contextPath}/user/home/{{topic.userid}}" class="fly-avatar">
            {{#  if(topic.pic_path!=''){ }}
            <img src="${pageContext.request.contextPath}/res/uploadImg/{{topic.pic_path}}" alt="{{topic.userid}}">
            {{#  } else { }}
            <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg">
            {{#  } }}
        </a>
        <h2>
            <a class="layui-badge">{{topic.name}}</a>
            <a href="${pageContext.request.contextPath}/jie/detail/{{topic.id}}">{{topic.title}}</a>
        </h2>
        <div class="fly-list-info">
            <a href="${pageContext.request.contextPath}/user/home/{{topic.userid}}" link>
                <cite>{{topic.nickname}}</cite>
                <!--
                <i class="iconfont icon-renzheng" title="认证信息：XXX"></i>
                <i class="layui-badge fly-badge-vip">VIP3</i>
                -->
            </a>
            <span>{{topic.create_time}}</span>
            <span class="fly-list-kiss layui-hide-xs" title="悬赏飞吻"><i class="iconfont icon-kiss"></i> {{topic.kiss_num}}</span>
            <!--<span class="layui-badge fly-badge-accept layui-hide-xs">已结</span>-->
            <span class="fly-list-nums">
                <i class="iconfont icon-pinglun1" title="回答"></i> {{topic.count}}
              </span>
        </div>
        <div class="fly-list-badge">
            <!--<span class="layui-badge layui-bg-red">精帖</span>-->
        </div>
    </li>
    {{#  }); }}
</script>

</body>
</html>