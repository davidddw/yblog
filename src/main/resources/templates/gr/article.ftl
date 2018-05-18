<!DOCTYPE html>
<html>
<head>
    <title>${article.title} | 琅嬛福地</title>
    <#include "defaulthead.ftl">
    <link rel='stylesheet' href='${basePath}/static/vender/ckeditor/4.6.0/plugins/codesnippet/lib/highlight/styles/zenburn.css' type='text/css' media='all'/>
</head>
<body>
<#include "header.ftl">
<!--header-web-->
<div id="main">
    <div id="container">
    <#include "content.ftl">
    </div>
    <!--Container-->
    <#include "sidebar.ftl">
</div>
<#include "defaultjs.ftl">
<script type='text/javascript' src='${basePath}/static/vender/jquery.qrcode/1.0/jquery.qrcode.min.js'></script>
<script type='text/javascript' src='${basePath}/static/gr/js/jquery.popup.min.js'></script>
<script>hljs.initHighlightingOnLoad();</script>
<!--main-->
<#include "footer.ftl">
<!--dibu-->
</body>
</html>