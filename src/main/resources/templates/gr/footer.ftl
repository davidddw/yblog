<script type='text/javascript' src='${basePath}/static/gr/js/custom.js'></script>
<footer id="dibu">
    <div class="header-main">
        <div class="bottomlist">
            <div class="xinlan"><a href="http://weibo.com/d05660/" target="_blank"><img
                    src="${basePath}/static/gr/images/xinlan.png" alt="琅嬛福地吧新浪微博" title="新浪微博"></a>
            </div>
            <div class="qq"><a href="http://user.qzone.qq.com/51551864/" target="_blank"><img
                    src="${basePath}/static/gr/images/qqzone.png" alt="琅嬛福地网QQ" title="QQ空间"></a>
            </div>
            <div class="weixin2">
                <div class="weixin"><img src="${basePath}/static/gr/images/weixin.png" alt="琅嬛福地吧微信公众号" title="微信公众号">
                    <img src="${basePath}/static/gr/images/qrcode.jpg" class="xixii" alt="琅嬛福地吧微信公众号" >
                </div>
            </div>
        </div>

        <div class="bleft">
            <ul id="menu-%e5%ba%95%e9%83%a8%e8%8f%9c%e5%8d%95" class="menu">
                <#list pages as page>
                <li id="menu-item-143" class="menu-item menu-item-type-post_type menu-item-object-page menu-item-143"><a
                        href="${basePath}/web/page/${page.slug}">${page.title}</a></li>
                </#list>
                <li id="menu-item-141" class="menu-item menu-item-type-post_type menu-item-object-page menu-item-141"><a
                        href="${basePath}/web/appdown">客户端下载</a></li>
                <li id="menu-item-142" class="menu-item menu-item-type-post_type menu-item-object-page menu-item-142"><a
                        href="${basePath}/web/page/biao">网站标签</a></li>
            </ul>
            <div class="bottom"> © 2017 <a href="${basePath}">琅嬛福地</a> | <a href="http://www.miitbeian.gov.cn/" rel="external nofollow" target="_blank"> 京ICP备16068408号 </a>
            </div>
        </div>

    </div>
    <div class="off">
        <div class="scroll" id="scroll" style="display:none;"> ︿</div>
    </div>
</footer>