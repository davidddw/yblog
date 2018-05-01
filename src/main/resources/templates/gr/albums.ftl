<!DOCTYPE html>
<html>
<head>
    <title>相册</title>
<#include "defaulthead.ftl">
</head>
<body>
<#include "header.ftl">
<!--header-web-->
<div id="main">
    <div id="container-page">
        <div class="wrap">
            <input type="text" style="display:none" id="albumcount" value="${(count?c)!''}" />
            <div id="album-data-container"></div>
            <div id="album-pagination" class="pagination">
                <div id="pagination-container" class="pagination_container"></div>
            </div>
        </div>
    </div>
    <!--Content-->
    <div class="clear"></div>
    <!--Container-->
</div>
<#include "defaultjs.ftl">
<!--main-->
<#include "footer.ftl">
<!--dibu-->
</body>
</html>