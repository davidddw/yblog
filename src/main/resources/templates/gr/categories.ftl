<!DOCTYPE html>
<html>
<head>
    <title>琅嬛福地</title>
    <#include "defaulthead.ftl">
</head>
<body>
<#include "header.ftl">
<!--header-web-->
<div id="main">
    <div id="container">
        <nav id="mbx">当前位置：<a href="${basePath}">首页</a> &gt;   分类</nav>
        <!--调用栏目菜单-->
        <div class="muluf">
            <div class="mulu"> <span>分&nbsp&nbsp类</span>
                <div class="onefour">
                    <div class="menu-%e7%bb%bc%e5%90%88-container">
                        <ul id="menu-%e7%bb%bc%e5%90%88" class="menu">
                            <#list categories as category>
                                <li class="menu-item menu-item-type-taxonomy menu-item-object-category"><a href="${basePath}/web/category/${category.name}">${category.term}</a></li>
                            </#list>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <input type="text" style="display:none" id="categoriescount" value="${(count?c)!''}" />
        <div id="categories-data-container"></div>
        <div id="categories-pagination" class="pagination">
            <div id="pagination-container" class="pagination_container"></div>
        </div>
    </div>
    <!--Container-->
    <#include "sidebar.ftl">
</div>
<#include "defaultjs.ftl">
<!--main-->
<#include "footer.ftl">
<!--dibu-->
</body>
</html>