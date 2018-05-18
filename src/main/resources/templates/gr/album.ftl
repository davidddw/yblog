<!DOCTYPE html>
<html>
<head>
    <title>相册</title>
    <#include "defaulthead.ftl">
    <link rel='stylesheet' href="${basePath}/static/vender/lightgallery/1.3.9/css/lightgallery.min.css" media='screen'/>
    <link rel='stylesheet' href='${basePath}/static/vender/justifiedGallery/3.6.3/css/justifiedGallery.min.css' type='text/css' media='all'/>
</head>
<body>
<#include "header.ftl">
<!--header-web-->
<div id="mains">
    <div id="container-novel">
        <div class="crumbs"><span>当前位置：</span><a href="/">首页</a> > 相册 > ${album.title}</div>
    </div>
    <div id="container-novel">
        <article class="box2">
            <header class="pagetitle">
                <h1 class="p3">${album.title}</h1>
                <hr />
            </header>
            <div class="container-fluid">
                <div class="demo-gallery mrb50">
                    <div id="aniimated-thumbnials" class="justified-gallery">
                        <#list galleries as gallery>
                            <a href="${basePath + gallery.path + gallery.name}">
                                <img class="img-responsive"
                                     src="${basePath + gallery.thumb + gallery.name}">
                                <div class="demo-gallery-poster">
                                    <img src="${basePath}/static/gr/images/zoom.png">
                                </div>
                            </a>
                        </#list>
                    </div>
                </div>
            </div>
        </article>
        <!--Content-->
        <div class="clear"></div>
    </div><!--Container End-->
    <!--Container-->
</div>
<#include "defaultjs.ftl">
<script src='${basePath}/static/vender/justifiedGallery/3.6.3/js/jquery.justifiedGallery.min.js'></script>
<script src='${basePath}/static/vender/jquery-mousewheel/3.1.13/jquery.mousewheel.min.js'></script>
<script src='${basePath}/static/vender/lightgallery/1.3.9/js/lightgallery.min.js'></script>
<script src='${basePath}/static/vender/lightgallery/1.2.21/js/lg-thumbnail.min.js'></script>
<!--main-->
<#include "footer.ftl">
<!--dibu-->
</body>
</html>