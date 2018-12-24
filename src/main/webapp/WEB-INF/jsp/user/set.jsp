<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>帐号设置</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="keywords" content="fly,layui,前端社区">
    <meta name="description" content="Fly社区是模块化前端UI框架Layui的官网社区，致力于为web开发提供强劲动力">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/layui/css/layui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/global.css">
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <script src="${pageContext.request.contextPath}/res/jquery-3.3.1.js"></script>
    <script>
        $(function () {
            $('#L_email').blur(function () {
                var email=$(this).serialize();
                $.post({
                    url:'${pageContext.request.contextPath}/user/checkEmail/',
                    data:email,
                    dataType:'json',
                    success:function (res) {
                        // alert(res.msg);
                        $('#L_email_msg').text(res.msg);
                        if(res.status!=0){
                            $('#L_email').val('');
                        }
                    },
                    error:function (err) {
                        console.log(err)
                    }
                })
            });
            $('#L_nowpass').blur(function () {
                var passwd=$(this).val();
                $.get({
                    url:'${pageContext.request.contextPath}/user/surePasswd/'+passwd,
                    dataType:"json",
                    success:function (res) {
                        // alert(res)
                        $('#sure_passwd').text(res.msg);
                        if(res.status!=0){
                            $('#L_nowpass').val('');
                        }
                    },
                    error:function (err) {
                        console.log(err);
                    }
                })
            });
            $('#L_repass').blur(function () {
                var pwd=$('#L_pass').val();
                if($(this).val()!=pwd){
                    // alert('密码确认不正确，请重新输入');
                    $(this).val('');
                    $('#L_repass_msg').text('密码确认不正确，请重新输入');
                }else {
                    $('#L_repass_msg').text('密码确认正确');
                }
            });
            $('#req').hover(function () {
                var pwd1=$('#L_pass').val();
                var pwd2=$('#L_repass').val();
                if(pwd1!=pwd2){
                    // $('#L_repass').val('');
                    $('#L_repass_msg').text('密码确认不正确，请重新输入');
                    $("button[class*=layui-btn]").addClass("layui-btn-disabled");
                }else {
                    $("button[class*=layui-btn]").removeClass("layui-btn-disabled");
                }
            })
        })
    </script>

</head>
<body>
<%@include file="../common/header.jsp"%>

<div class="layui-container fly-marginTop fly-user-main">
    <%@include file="../common/user-nav.jsp"%>

    <div class="site-tree-mobile layui-hide">
        <i class="layui-icon">&#xe602;</i>
    </div>
    <div class="site-mobile-shade"></div>

    <div class="site-tree-mobile layui-hide">
        <i class="layui-icon">&#xe602;</i>
    </div>
    <div class="site-mobile-shade"></div>


    <div class="fly-panel fly-panel-user" pad20>
        <div class="layui-tab layui-tab-brief" lay-filter="user">
            <ul class="layui-tab-title" id="LAY_mine">
                <li class="layui-this" lay-id="info">我的资料</li>
                <li lay-id="avatar">头像</li>
                <li lay-id="pass">密码</li>
                <li lay-id="bind">帐号绑定</li>
            </ul>
            <div class="layui-tab-content" style="padding: 20px 0;">
                <div class="layui-form layui-form-pane layui-tab-item layui-show">
                    <form method="post" action="/user/changeInfo">
                        <div class="layui-form-item">
                            <label for="L_email" class="layui-form-label">邮箱</label>
                            <div class="layui-input-inline">
                                <input type="text" id="L_email" name="email" required lay-verify="email" autocomplete="off" value="" class="layui-input">
                            </div>
                            <div class="layui-form-mid layui-word-aux">如果您在邮箱已激活的情况下，变更了邮箱，需<a href="${pageContext.request.contextPath}/user/active" style="font-size: 12px; color: #4f99cf;">重新验证邮箱</a>。</div>
                        </div>
                        <div class="layui-form-item">
                            <label for="L_username" class="layui-form-label">昵称</label>

                            <div class="layui-form-mid layui-word-aux" id="L_email_msg"></div>
                        </div>
                        <div class="layui-form-item">
                            <label for="L_username" class="layui-form-label">昵称</label>
                            <div class="layui-input-inline">
                                <input type="text" id="L_username" name="nickname" required lay-verify="required" autocomplete="off" value="" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <div class="layui-input-inline">
                                    <input type="radio" name="sex" value="0" checked title="男">
                                    <input type="radio" name="sex" value="1" title="女">
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label for="L_city" class="layui-form-label">城市</label>
                            <div class="layui-input-inline">
                                <input type="text" id="L_city" name="city" autocomplete="off" value="" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item layui-form-text">
                            <label for="L_sign" class="layui-form-label">签名</label>
                            <div class="layui-input-block">
                                <textarea placeholder="随便写些什么刷下存在感" id="L_sign"  name="sign" autocomplete="off" class="layui-textarea" style="height: 80px;"></textarea>
                            </div>
                        </div>
                        <div class="layui-input-inline" style="display:none;">
                            <input type="text" name="id" value="${userinfo.id}" >
                        </div>
                        <div class="layui-form-item" >
                            <button class="layui-btn" key="set-mine" lay-filter="*" lay-submit>确认修改</button>
                        </div>
                    </form>
                </div>


                <div class="layui-form layui-form-pane layui-tab-item">
                    <div class="layui-form-item">
                        <div class="avatar-add">
                            <p>建议尺寸168*168，支持jpg、png、gif，最大不能超过50KB</p>
                            <button type="button" class="layui-btn upload-img">
                                <i class="layui-icon">&#xe67c;</i>上传头像
                            </button>
                            <c:choose>
                                <c:when test="${userinfo.picPath==''}">
                                    <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg">
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/res/uploadImg/${userinfo.picPath}">
                                </c:otherwise>
                            </c:choose>


                            <span class="loading"></span>
                        </div>
                    </div>
                </div>

                <div class="layui-form layui-form-pane layui-tab-item">
                    <form action="/user/repass" method="post">
                        <div class="layui-form-item">
                            <label for="L_nowpass" class="layui-form-label">当前密码</label>
                            <div class="layui-input-inline">
                                <input type="password" id="L_nowpass" name="nowpass" required lay-verify="required" autocomplete="off" class="layui-input">
                            </div>
                            <div class="layui-form-mid layui-word-aux" id="sure_passwd"></div>
                            <div class="layui-form-item" style="display: none" >
                                <input type="text" name="id" value="${userinfo.id}">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label for="L_pass" class="layui-form-label">新密码</label>
                            <div class="layui-input-inline">
                                <input type="password" id="L_pass" name="passwd" required lay-verify="required" autocomplete="off" class="layui-input">
                            </div>
                            <div class="layui-form-mid layui-word-aux">6到16个字符</div>

                        </div>
                        <div class="layui-form-item">
                            <label for="L_repass" class="layui-form-label">确认密码</label>
                            <div class="layui-input-inline">
                                <input type="password" id="L_repass" name="repass" required lay-verify="required" autocomplete="off" class="layui-input">
                            </div>
                            <div class="layui-form-mid layui-word-aux" id="L_repass_msg"></div>
                        </div>
                        <div class="layui-form-item">
                            <button class="layui-btn" key="set-mine" lay-filter="*" lay-submit id="req">确认修改</button>
                        </div>
                    </form>
                </div>

                <div class="layui-form layui-form-pane layui-tab-item">
                    <ul class="app-bind">
                        <li class="fly-msg app-havebind">
                            <i class="iconfont icon-qq"></i>
                            <span>已成功绑定，您可以使用QQ帐号直接登录Fly社区，当然，您也可以</span>
                            <a href="javascript:;" class="acc-unbind" type="qq_id">解除绑定</a>

                            <!-- <a href="" onclick="layer.msg('正在绑定微博QQ', {icon:16, shade: 0.1, time:0})" class="acc-bind" type="qq_id">立即绑定</a>
                            <span>，即可使用QQ帐号登录Fly社区</span> -->
                        </li>
                        <li class="fly-msg">
                            <i class="iconfont icon-weibo"></i>
                            <!-- <span>已成功绑定，您可以使用微博直接登录Fly社区，当然，您也可以</span>
                            <a href="javascript:;" class="acc-unbind" type="weibo_id">解除绑定</a> -->

                            <a href="" class="acc-weibo" type="weibo_id"  onclick="layer.msg('正在绑定微博', {icon:16, shade: 0.1, time:0})" >立即绑定</a>
                            <span>，即可使用微博帐号登录Fly社区</span>
                        </li>
                    </ul>
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
    layui.cache.page = 'user';
    <%@include file="../common/cache-user.jsp"%>
    layui.config({
        version: "2.0.0"
        ,base: '${pageContext.request.contextPath}/res/mods/'
    }).extend({
        fly: 'index'
    }).use('fly',function () {
        var $=layui.jquery;
        $('.neu-nav-item').eq(2).addClass('layui-this').siblings().removeClass('layui-this');
    });
</script>

</body>
</html>