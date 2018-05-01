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
        <div id="soutab2">
            <form method="get" class="search2" action="${basePath}/search" >
                <input class="text" type="text" name="s" placeholder=" 请输入关键词" value="${searchValue!''}">
                <input class="butto" value="" type="submit">
            </form>
        </div>
    <#if count==0>
        <div class="noarticle">抱歉,暂时还没有你想找的相关文章!</div>
    <#else>
        <input type="text" style="display:none" id="searchcount" value="${(count?c)!''}" />
        <input type="text" style="display:none" id="searchvalue" value="${(searchValue)!''}" />
        <div id="search-data-container"></div>
        <div id="search-pagination" class="pagination">
            <div id="pagination-container" class="pagination_container"></div>
        </div>
    </#if>
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