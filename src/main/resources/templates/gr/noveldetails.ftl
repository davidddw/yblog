<!DOCTYPE html>
<html>
<head>
    <title>${noveldetails.novel.name}_${noveldetails.title}</title>
<#include "defaulthead.ftl">
</head>
<body>
<header id="header-web">
    <div class="header-main-novel">
        <a href="${basePath}" class="logo" title="琅嬛福地" rel="home"><img src="${basePath}/static/gr/images/logo.png" alt="琅嬛福地"></a>
        <h1 class="logo2"><a href="${basePath}" rel="home"><img src="${basePath}/static/gr/images/logo.png" alt="琅嬛福地"></a></h1>
        <!--logo-->
        <nav class="header-nav">
            <div class="menu-%e9%a1%b6%e9%83%a8%e8%8f%9c%e5%8d%95-container">
                <ul id="menu-%e9%a1%b6%e9%83%a8%e8%8f%9c%e5%8d%95" class="menu">
                    <li id="menu-item-6"
                        class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item current_page_item menu-item-home menu-item-6">
                        <a href="${basePath}">首页</a></li>
                    <li id="menu-item-51"
                        class="menu-item menu-item-type-taxonomy menu-item-object-category menu-item-51">
                        <a href="${basePath}/web/category">分类</a></li>
                    <li id="menu-item-52"
                        class="menu-item menu-item-type-taxonomy menu-item-object-category menu-item-52">
                        <a href="${basePath}/web/albums">相册</a></li>
                    <li id="menu-item-53"
                        class="menu-item menu-item-type-taxonomy menu-item-object-category menu-item-53">
                        <a href="${basePath}/web/novels">小说</a></li>
                    <li id="menu-item-376" class="menu-item menu-item-type-post_type menu-item-object-page menu-item-376">
                        <a href="${basePath}/web/search">搜索</a></li>
                </ul>
            </div>
        </nav>
        <div id="soutab">
            <form method="get" class="search" action="${basePath}/web/search">
                <input class="text" type="text" name="s" placeholder="搜索一下">
                <input class="button" value="" type="submit">
            </form>
        </div>
    </div>
</header>
<!--header-web-->
<div id="mains">
    <div id="container-novel">
        <div class="crumbs"><span>当前位置：</span><a href="/">首页</a> > 玄幻修真 > <a href="${basePath}/web/novel/${noveldetails.novel.id}.html">${noveldetails.novel.name}</a> > ${noveldetails.title}</div>
        <div class="article">
            <div class="inner" id="BookCon">
                <div class="title">
                    <div class="novel_info" >
                        <h1 class="novel_name">${noveldetails.novel.name}</h1><h1 class="novel_title">${noveldetails.title}</h1>
                    </div>
                    <div class="info">
                        <span>小说：<a href="${basePath}/web/novel/${noveldetails.novel.id}.html" title="${noveldetails.novel.name}">${noveldetails.novel.name}</a></span>
                        <span>作者：${noveldetails.novel.author}</a></span>
                        <span>更新时间：2016-09-15 02:59:15</span>
                    </div>
                </div>
                <div class="operatetop">
                    <ul>
                        <li class="prev"><a href="${basePath}/web/novel/${prevurl}.html"><i class="pticon pticon-chevron-left"></i> 上一章</a></li>
                        <li class="index"><a href="${basePath}/web/novel/${noveldetails.novel.id}.html" title="index"><i class="pticon pticon-list"></i> 目录</a></li>
                        <li class="next"><a href="${basePath}/web/novel/${nexturl}.html">下一章 <i class="pticon pticon-chevron-right"></i></a></li>
                    </ul>
                </div>
                <div id="BookText">
                    ${noveldetails.location}
                </div>
                <div class="operate">
                    <ul>
                        <li class="prev"><a href="${basePath}/web/novel/${prevurl}.html"><i class="pticon pticon-chevron-left"></i> 上一章</a></li>
                        <li class="index"><a href="${basePath}/web/novel/${noveldetails.novel.id}.html" title="index"><i class="pticon pticon-list"></i> 目录</a></li>
                        <li class="next"><a href="${basePath}/web/novel/${nexturl}.html">下一章 <i class="pticon pticon-chevron-right"></i></a></li>
                    </ul>
                </div>
                <div class="readingTips">
                    可以使用回车、←→快捷键阅读
                    <div class="readingSwitch none"><span class="readingSwitch_Btn active"></span>开启瀑布流阅读
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!--Content-->
    <div class="clear"></div>
    <!--Container-->
</div>
<#include "defaultjs.ftl">
<script src='${basePath}/static/vender/jquery.imagesloaded/3.0.4/jquery.imagesloaded.min.js'></script>
<script src='${basePath}/static/vender/jquery.wookmark/1.3.1/jquery.wookmark.min.js'></script>
<script src='${basePath}/static/gr/js/custom.js'></script>
<!--main-->
<footer id="dibu">
    <div class="header-main-novel">
        <div class="bottomlist">
            <div class="xinlan"><a href="http://weibo.com/d05660/" target="_blank"><img
                    src="${basePath}/static/gr/images/xinlan.png" alt="琅嬛福地吧新浪微博" title="新浪微博"></a>
            </div>
            <div class="qq"><a href="http://user.qzone.qq.com/51551864/" target="_blank"><img
                    src="${basePath}/static/gr/images/qqzone.png" alt="琅嬛福地网QQ" title="QQ空间"></a>
            </div>
            <div class="weixin2">
                <div class="weixin"><img src="${basePath}/static/gr/images/weixin.png" alt="琅嬛福地吧微信公众号" title="微信公众号">
                    <img src="${basePath}/static/gr/images/qrcode.jpg" class="xixii" alt="琅嬛福地吧微信公众号" >
                </div>
            </div>
        </div>

        <div class="bleft">
            <ul id="menu-%e5%ba%95%e9%83%a8%e8%8f%9c%e5%8d%95" class="menu">
            <#list pages as page>
                <li id="menu-item-143" class="menu-item menu-item-type-post_type menu-item-object-page menu-item-143"><a
                        href="${basePath}/web/page/${page.slug}">${page.title}</a></li>
            </#list>
                <li id="menu-item-141" class="menu-item menu-item-type-post_type menu-item-object-page menu-item-141"><a
                        href="${basePath}/web/appdown">客户端下载</a></li>
                <li id="menu-item-142" class="menu-item menu-item-type-post_type menu-item-object-page menu-item-142"><a
                        href="${basePath}/web/page/biao">网站标签</a></li>
            </ul>
            <div class="bottom"> © 2017 <a href="${basePath}">琅嬛福地</a> | <a href="http://www.miitbeian.gov.cn/" rel="external nofollow" target="_blank"> 京ICP备16068408号 </a>
            </div>
        </div>

    </div>
    <div class="off">
        <div class="scroll" id="scroll" style="display:none;"> ︿</div>
    </div>
</footer>
<!--dibu-->
</body>
</html>