<article class="content">
    <header class="contenttitle">
        <h1 class="mscctitle"><a href="${basePath}/web/article/${article.wid?c}.html">${article.title}</a></h1>
        <address class="msccaddress ">
            <time>${article.createdDate?datetime?string("yyyy.MM.dd")}</time> - ${article.author} - 阅 ${article.readCount}
        </address>
    </header>
    <div class="content-text">${article.content}</div>
    <!--content_text-->
    <div class="meta-bottom">
        <div class="post-like">
            <a href="javascript:" data-id="${article.wid?c}" class="favorite"><span class="count">${article.likeCount}</span></a>
        </div>
        <div class="share-group">
            <div class="weixin" id="weixin"><img src="${basePath}/static/gr/images/weixin.png" alt="琅嬛福地吧微信公众号" title="微信公众号">
            </div>
            <div class="xinlang"><a href="http://weibo.com/d05660/" target="_blank"><img
                    src="${basePath}/static/gr/images/xinlan.png" alt="琅嬛福地吧新浪微博" title="新浪微博"></a>
            </div>
            <div class="qq"><a href="http://user.qzone.qq.com/51551864/" target="_blank"><img
                    src="${basePath}/static/gr/images/qqzone.png" alt="琅嬛福地网QQ" title="QQ空间"></a>
            </div>
        </div>
    </div>
    <div class="zhuan">
        <p>本文转载 "${article.original}"</br></br>原文地址 "${article.originalUrl}"</p>
    </div>
</article>
<div id="code"></div>
<!--相关文章-->
<div class="xianguan">
    <div class="xianguantitle">相关文章！</div>
    <ul class="pic">
    <#list relative_article as article><li>
        <#assign image=article.imageThumb>
        <#assign imageShortName=image?substring(image?last_index_of('/')+1)>
        <li><a href="${basePath}/web/article/${article.wid?c}.html" target="_blank">
        <#if imageShortName!="xxx.gif"><img width="1000" height="600" src="${image}"
                            class="attachment-medium wp-post-image" alt="${imageShortName}" />
        <#else><img width="1000" height="600" src="${basePath}/static/gr/xxx.gif"
                    class="attachment-medium wp-post-image" alt="xxx" /></#if>
        </a><a rel="bookmark" href="${basePath}/web/article/${article.wid?c}.html" title="${article.title}"
               class="link" target="_blank">${article.title}</a><address class="xianaddress">
            <time>${article.createdDate?datetime?string("MM")}.${article.createdDate?datetime?string("dd")}</time> - 阅 ${article.readCount}</address>
            <p>${article.excerpt}</p>
        </li>
    </#list>
    </ul>
</div>