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
        <input type="text" style="display:none" id="articlecount" value="${(count?c)!''}" />
        <div id="article-data-container"></div>
        <div id="article-pagination" class="pagination">
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