<!DOCTYPE html>
<html>
<head>
    <title>小说</title>
<#include "defaulthead.ftl">
</head>
<body>
<#include "header.ftl">
<!--header-web-->
<div id="mains">
    <div id="container-novel">
        <div id="lim-cate-list" class="filter">
            <a id="sort1" href="#" target="_self">玄幻奇幻</a>
            <a id="sort2" href="#" target="_self">武侠仙侠</a>
            <a id="sort3" href="#" target="_self">都市言情</a>
            <a id="sort4" href="#" target="_self">历史军事</a>
            <a id="sort5" href="#" target="_self">游戏竞技</a>
            <a id="sort6" href="#" target="_self">科幻灵异</a>
            <a id="sort7" href="#" target="_self">其他类型</a>
        </div>
        <div id="hotcontent">
            <input type="text" style="display:none" id="novelscount" value="${(count?c)!''}" />
            <div id="novels-data-container"></div>
            <div id="novels-pagination" class="pagination">
                <div id="pagination-container" class="pagination_container"></div>
            </div>
            <div class="clear"></div>
        </div>
    </div>
    <!--Content-->
    <div class="clear"></div>
    <!--Container-->
</div>
<#include "defaultjs.ftl">
<script src='${basePath}/static/vender/jquery.imagesloaded/3.0.4/jquery.imagesloaded.min.js'></script>
<script src='${basePath}/static/vender/jquery.wookmark/1.3.1/jquery.wookmark.min.js'></script>
<!--main-->
<#include "footer.ftl">
<!--dibu-->
</body>
</html>