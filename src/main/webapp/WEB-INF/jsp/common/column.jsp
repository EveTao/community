<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="fly-panel fly-column">
    <div class="layui-container">
        <ul class="layui-clear">
            <li class="layui-hide-xs"><a href="/">首页</a></li>
            <c:forEach items="${category}" var="cate">
                <c:choose>
                    <c:when test="${cate.id==categoryid }">
                        <li class="layui-this"><a href="${pageContext.request.contextPath}/jie/index/${cate.id }/0">${cate.name}
                            <c:if test="${cate.name=='分享'}">
                                <span class="layui-badge-dot"></span>
                            </c:if>
                        </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/jie/index/${cate.id }/0">${cate.name}
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