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
        <div class="decr"> <span class="title"> ${category.term}: </span><p>${category.intro}</p>
        </div>
        <input type="text" style="display:none" id="categorycount" value="${(count?c)!''}" />
        <input type="text" style="display:none" id="categoryId" value="${(category.name)!''}" />
        <div id="category-data-container"></div>
        <div id="category-pagination" class="pagination">
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