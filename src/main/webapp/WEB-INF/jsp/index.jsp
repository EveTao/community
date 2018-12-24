

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
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>


</head>
<body>
<%@include file="common/header.jsp"%>


<%@include file="common/column.jsp"%>

<div class="layui-container">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md8">
            <div class="fly-panel">
                <div class="fly-panel-title fly-filter">
                    <a>置顶</a>
                    <a href="#signin" class="layui-hide-sm layui-show-xs-block fly-right" id="LAY_goSignin" style="color: #FF5722;">去签到</a>
                </div>
                <ul class="fly-list">
                    <c:forEach items="${toptopics}" var="topic">
                        <li>
                            <a href="${pageContext.request.contextPath}/user/home/${topic.userid}" class="fly-avatar">
                                <c:choose>
                                    <c:when test="${topic.pic_path != ''}">
                                        <img src="${pageContext.request.contextPath}/res/uploadImg/${topic.pic_path}" alt="${topic.userid}">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg">
                                    </c:otherwise>
                                </c:choose>
                            </a>
                            <h2>
                                <a class="layui-badge">${topic.name}</a>
                                <a href="${pageContext.request.contextPath}/jie/detail/${topic.id}">${topic.title}</a>
                            </h2>
                            <div class="fly-list-info">
                                <a href="${pageContext.request.contextPath}/user/home/${topic.userid}" link>
                                    <cite>${topic.nickname}</cite>
                                    <!--
                                    <i class="iconfont icon-renzheng" title="认证信息：XXX"></i>
                                    <i class="layui-badge fly-badge-vip">VIP3</i>
                                    -->
                                </a>
                                <span>${topic.create_time}</span>
                                <span class="fly-list-kiss layui-hide-xs" title="悬赏飞吻"><i class="iconfont icon-kiss"></i> ${topic.kiss_num}</span>
                                <!--<span class="layui-badge fly-badge-accept layui-hide-xs">已结</span>-->
                                <span class="fly-list-nums">
                <i class="iconfont icon-pinglun1" title="回答"></i> ${topic.count}
              </span>
                            </div>
                            <div class="fly-list-badge">
                                <!--<span class="layui-badge layui-bg-red">精帖</span>-->
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>

            <div class="fly-panel" style="margin-bottom: 0;">

                <div class="fly-panel-title fly-filter" id="layui-this">
                    <a href="${pageContext.request.contextPath}/jie/index/0/0" class="layui-type">综合</a>
                    <span class="fly-mid"></span>
                    <a href="${pageContext.request.contextPath}/jie/index/0/1">未结</a>
                    <span class="fly-mid"></span>
                    <a href="${pageContext.request.contextPath}/jie/index/0/2">已结</a>
                    <span class="fly-mid"></span>
                    <a href="${pageContext.request.contextPath}/jie/index/0/3">精华</a>
                    <span class="fly-filter-right layui-hide-xs">
          </span>
                </div>

                <ul class="fly-list">
                    <div id="data">
                    </div>
                    <div id="page">
                    </div>
                </ul>
                <%--<div style="text-align: center">
                    <div class="laypage-main">
                        <a href="${pageContext.request.contextPath}/jie/index/1" class="laypage-next">更多求解</a>
                    </div>
                </div>--%>

            </div>
        </div>
        <div class="layui-col-md4">

            <div class="fly-panel">
                <h3 class="fly-panel-title">温馨通道</h3>
                <ul class="fly-panel-main fly-list-res">
                    <li>
                        <a href="http://fly.layui.com/jie/4281/" target="_blank">layui 的 GitHub 及 Gitee (码云) 仓库，欢迎Star</a>
                    </li>
                    <li>
                        <a href="http://fly.layui.com/jie/5366/" target="_blank">
                            layui 常见问题的处理和实用干货集锦
                        </a>
                    </li>
                    <li>
                        <a href="http://fly.layui.com/jie/4281/" target="_blank">layui 的 GitHub 及 Gitee (码云) 仓库，欢迎Star</a>
                    </li>
                    <li>
                        <a href="http://fly.layui.com/jie/5366/" target="_blank">
                            layui 常见问题的处理和实用干货集锦
                        </a>
                    </li>
                    <li>
                        <a href="http://fly.layui.com/jie/4281/" target="_blank">layui 的 GitHub 及 Gitee (码云) 仓库，欢迎Star</a>
                    </li>
                </ul>
            </div>


            <div class="fly-panel fly-signin">
                <div class="fly-panel-title">
                    签到
                    <i class="fly-mid"></i>
                    <a href="javascript:;" class="fly-link" id="LAY_signinHelp">说明</a>
                    <i class="fly-mid"></i>
                    <a href="javascript:;" class="fly-link" id="LAY_signinTop">活跃榜<span class="layui-badge-dot"></span></a>
                    <c:if test="${!empty userinfo}">
                        <span class="fly-signin-days">已连续签到<cite>${qiandaoDay}</cite>天</span>
                    </c:if>
                </div>
                <div class="fly-panel-main fly-signin-main">
                    <c:choose>
                        <c:when test="${countToday==0}">
                            <button class="layui-btn layui-btn-danger" id="LAY_signin">今日签到</button>
                            <c:if test="${!empty userinfo}">
                                <span>可获得<cite>${qiandaoKiss}</cite>飞吻</span>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <button class="layui-btn layui-btn-disabled">今日已签到</button>
                            <span>获得了<cite>${qiandaoKiss}</cite>飞吻</span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="fly-panel fly-rank fly-rank-reply" id="LAY_replyRank">
                <h3 class="fly-panel-title">回贴周榜</h3>
                <dl>
                    <!--<i class="layui-icon fly-loading">&#xe63d;</i>-->
                    <c:forEach items="${tops}" var="top">
                        <dd>
                            <a href="${pageContext.request.contextPath}/user/home/${top.user_id}">
                                <c:choose>
                                    <c:when test="${top.pic_path!=''}">
                                        <img src="${pageContext.request.contextPath}/res/uploadImg/${top.pic_path}">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg">
                                    </c:otherwise>
                                </c:choose>
                                <cite>${top.nickname}</cite>
                                <i>${top.count}次回答</i>
                            </a>
                        </dd>
                    </c:forEach>
                </dl>
            </div>

            <dl class="fly-panel fly-list-one">
                <dt class="fly-panel-title">本周热议</dt>
                <c:forEach items="${TopicsHot}" var="hot">
                    <dd>
                        <a href="${pageContext.request.contextPath}/jie/detail/${hot.id}">${hot.title}</a>
                        <span><i class="iconfont icon-pinglun1"></i> ${hot.comment_num}</span>
                    </dd>
                </c:forEach>
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
                    <a href="http://layim.layui.com/?from=fly" target="_blank" class="fly-zanzhu" time-limit="2017.09.25-2099.01.01" style="background-color: #5FB878;">LayIM 3.0 - layui 旗舰之作</a>
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
        <a href="http://fly.layui.com/jie/3147/" target="_blank">付费计划</a>
        <a href="http://www.layui.com/template/fly/" target="_blank">获取Fly社区模版</a>
        <a href="http://fly.layui.com/jie/2461/" target="_blank">微信公众号</a>
    </p>
</div>

<script src="${pageContext.request.contextPath}/res/layui/layui.js"></script>
<script>
    layui.cache.page = '';
    <%@include file="common/cache-user.jsp"%>
    layui.config({
        version: "3.0.0"
        ,base: '${pageContext.request.contextPath}/res/mods/' //这里实际使用时，建议改成绝对路径
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
                pageInfo.categor=0;
                pageInfo.type=${typeid};
            }
            $.ajax({
                url:'${pageContext.request.contextPath}/getTopicPage',
                type:'post',
                dataType:'json',
                data:pageInfo,
                success:function (jsonObj) {
                    //执行一个laypage实例
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
                    $('#layui-type').find('a').eq(${typeid}).addClass('layui-this').siblings().removeClass('layui-this');

                }
            })
        }
        $(function () {
            getPageData();
            $('#neu-hide-xs').addClass('layui-this');
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

<%--<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");--%>
<%--document.write(unescape("%3Cspan id='cnzz_stat_icon_30088308'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "w.cnzz.com/c.php%3Fid%3D30088308' type='text/javascript'%3E%3C/script%3E"));--%>
<%--</script>--%>



</body>
</html>