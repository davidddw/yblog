<header id="header-web">
    <div class="header-main">
        <a href="${basePath}" class="logo" title="琅嬛福地" rel="home"><img src="${basePath}/static/gr/images/logo.png" alt="琅嬛福地"></a>
        <h1 class="logo2"><a href="${basePath}" rel="home"><img src="${basePath}/static/gr/images/logo.png" alt="琅嬛福地"></a></h1>
        <!--logo-->
        <nav class="header-nav">
            <div class="menu-%e9%a1%b6%e9%83%a8%e8%8f%9c%e5%8d%95-container">
                <ul id="menu-%e9%a1%b6%e9%83%a8%e8%8f%9c%e5%8d%95" class="menu">
                    <li id="menu-item-6"
                        class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item current_page_item menu-item-home menu-item-6">
                        <a href="${basePath}">首页</a></li>
                    <li id="menu-item-51"
                        class="menu-item menu-item-type-taxonomy menu-item-object-category menu-item-51">
                        <a href="${basePath}/web/category">分类</a></li>
                    <li id="menu-item-52"
                        class="menu-item menu-item-type-taxonomy menu-item-object-category menu-item-52">
                        <a href="${basePath}/web/albums">相册</a></li>
                    <li id="menu-item-53"
                        class="menu-item menu-item-type-taxonomy menu-item-object-category menu-item-53">
                        <a href="${basePath}/web/novels">小说</a></li>
                    <li id="menu-item-376" class="menu-item menu-item-type-post_type menu-item-object-page menu-item-376">
                        <a href="${basePath}/web/search">搜索</a></li>
            </ul>
            </div>
        </nav>
        <div id="soutab">
            <form method="get" class="search" action="${basePath}/web/search">
                <input class="text" type="text" name="s" placeholder="搜索一下">
                <input class="button" value="" type="submit">
            </form>
        </div>
    </div>
</header>