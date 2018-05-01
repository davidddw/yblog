<!DOCTYPE html>
<html>
<head>
    <title>关于我们</title>
    <#include "defaulthead.ftl">
</head>
<body>
<#include "header2.ftl">
<!--header-web-->
<div id="mainss">
    <aside id="sitebar-page">
        <div id="menu">
            <ul id="menu-%e5%8f%b3%e4%be%a7%e5%85%b3%e4%ba%8e" class="menu">
                <li id="menu-item-341"
                    class="menu-item menu-item-type-custom menu-item-object-custom menu-item-home menu-item-341"><a
                        href="${basePath}">返回首页</a></li>
                <#list pages as pageitem>
                    <#if pageitem.slug==page.slug>
                    <li id="menu-item-133"
                        class="menu-item menu-item-type-post_type menu-item-object-page current-menu-item page_item page-item-2 current_page_item menu-item-133 navon">
                    <#else>
                    <li id="menu-item-878" class="menu-item menu-item-type-post_type menu-item-object-page menu-item-878">
                    </#if>
                    <a href="${basePath}/web/page/${pageitem.slug}">${pageitem.title}</a></li>
                </#list>
                <li id="menu-item-175" class="menu-item menu-item-type-post_type menu-item-object-page menu-item-175">
                    <a href="${basePath}/web/page/biao">网站标签</a></li>
            </ul>
        </div>
    </aside>
    <div id="container">
        <article class="content">
            <header class="pagetitle1">
                <h1 class="mscctitle"><a href="${basePath}/web/page/${page.slug}">${page.title}</a></h1>
            </header>
            <div class="content-text">
                ${page.content}
            </div>
            <!--content_text-->
        </article>
        <!--content-->
    </div>
    <!--Container End-->
</div>
<#include "defaultjs.ftl">
<!--main-->
<#include "footer.ftl">
<!--dibu-->
</body>
</html>