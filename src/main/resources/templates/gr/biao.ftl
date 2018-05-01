<!DOCTYPE html>
<html>
<head>
    <title>免责声明</title>
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
                    <li id="menu-item-878" class="menu-item menu-item-type-post_type menu-item-object-page menu-item-878">
                    <a href="${basePath}/page/${pageitem.slug}">${pageitem.title}</a></li>
                </#list>
                <li id="menu-item-175" class="menu-item menu-item-type-post_type menu-item-object-page current-menu-item page_item page-item-25 current_page_item menu-item-175 navon">
                    <a href="${basePath}/page/biao">网站标签</a></li>
            </ul>
        </div>
    </aside>
    <div id="container">
        <article class="content">
            <header class="pagetitle1">
                <h1 class="mscctitle"><a href="${basePath}/page/biao">网站标签</a></h1>
            </header>
            <ul class='wp-tag-cloud'>
            <#list tags as tag>
                <li><a href='${basePath}/tag/${tag.name}' title='${tag.count}个话题' style='font-size: 10pt;'>${tag.name}</a></li>
            </#list>
            </ul>
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