<aside id="sitebar">
    <div class="tagg">
        <span class="tagg_title">文章分类</span>
        <div>
            <ul id="menu-%e5%8f%b3%e4%be%a7%e8%8f%9c%e5%8d%95" class="menu">
            <#list categories as category>
                <#assign name=category.name />
                <#assign title=category.term />
                    <li class="menu-item menu-item-type-taxonomy menu-item-object-category"><a
                            href="${basePath}/web/category/${name}">${title}</a></li>
            </#list>
            </ul>
        </div>
        <span class="tagg_title">热门标签</span>
        <div>
            <ul id="menu-%e8%90%a5%e9%94%80%e6%8e%a8%e5%b9%bf" class="menu">
            <#list tags as tag>
                <li class="menu-item menu-item-type-taxonomy menu-item-object-category"><a
                        href="${basePath}/web/tag/${tag}">${tag}</a></li>
            </#list>
            </ul>
        </div>
    </div>
    <div class="sitebar_list">
        <h4 class="sitebar_title">随机热文</h4>
        <ul class="sitebar_list_ul">
        <#list mostArticles as mostArticle>
            <li><a href="${basePath}/web/article/${mostArticle.wid?c}.html" target="_blank">${mostArticle.title} </a></li>
        </#list>
        </ul>
    </div>
</aside>
<div class="clear"></div>