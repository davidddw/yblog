<!DOCTYPE html>
<html>
<head>
    <title>小说</title>
<#include "defaulthead.ftl">
    <link rel='stylesheet' id='pc-css' href='${basePath}/static/vender/paginationjs/2.0.8/pagination.css' type='text/css' media='all'/>
</head>
<body>
<#include "header.ftl">
<!--header-web-->
<div id="mains">
    <div id="container-novel">
        <div class="crumbs"><span>当前位置：</span><a href="/">首页</a> > 玄幻修真 > ${novelInfo.name} 章节列表</div>
        <div class="lastnovel">
            <div class="noveltitle" id="alllist"><span class="novellabel"></span><em>《${novelInfo.name}》最新8章列表</em></div>
            <div class="inner details">
                <dl class="chapterlist">
                    <#if lastnovelchapters??><#list lastnovelchapters as novelchapter>
                        <dd><a href="${basePath}/web/novel/${novelInfo.id}/${novelchapter.wid?c}.html" title="${novelchapter.title}">${novelchapter.title}</a></dd>
                    </#list></#if>
                </dl>
            </div>
        </div>
        <div class="noveltitle" id="alllist1"><span class="novellabel"></span><em>《${novelInfo.name}》 全部章节列表</em></div>
        <input style="display:none" id="novelcount" value="${(novelCount?c)!''}" />
        <input style="display:none" id="novelId" value="${(novelId)!''}" />
        <div id="novel-data-container"></div>
        <div id="novel-pagination" class="pagination">
            <div id="pagination-container" class="pagination_container"></div>
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