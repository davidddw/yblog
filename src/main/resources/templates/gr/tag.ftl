<!DOCTYPE html>
<html>
<head>
    <title>琅嬛福地</title>
    <meta charset="utf-8"/>
    <meta http-equiv="Cache-Control" content="no-transform "/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <meta name="description" content="互联网的运营规划，为企业提供有效的运营解决方案"/>
    <meta name="keywords" content="互联网运营，行业运营，网站运营， APP运营"/>
    <#include "defaulthead.ftl">
</head>
<body>
<#include "header.ftl">
<!--header-web-->
<div id="main">
    <div id="container">
        <div class="decr"> <span class="title"> 标签: </span><p>${tag.name}</p>
        </div>
        <input type="text" style="display:none" id="tagcount" value="${(count?c)!''}" />
        <input type="text" style="display:none" id="tagId" value="${(tag.name)!''}" />
        <div id="tag-data-container"></div>
        <div id="tag-pagination" class="pagination">
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