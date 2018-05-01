<div class="col-md-3 left_col">
    <div class="left_col scroll-view">
        <div class="navbar nav_title" style="border: 0;">
            <a href="${basePath}" class="site_title"><i class="fa fa-paw"></i> <span>琅 嬛 福 地</span></a>
        </div>
        <div class="clearfix"></div>
        <!-- menu profile quick info -->
        <div class="profile clearfix">
            <div class="profile_pic">
                <img src="${basePath}/static/admin/images/img.png" alt="..." class="img-circle profile_img">
            </div>
            <div class="profile_info">
                <span>欢迎,</span>
                <h2>${loginName!''}</h2>
            </div>
            <div class="clearfix"></div>
        </div>
        <!-- /menu profile quick info -->
        <br/>
        <!-- sidebar menu -->
        <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
            <div class="menu_section">
                <ul class="nav side-menu">
                    <li><a href="${basePath}/backend"><i class="fa fa-home"></i> 面板 </a></li>
                    <li><a><i class="fa fa-clone"></i> 文章 <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu">
                            <li><a href="${basePath}/backend/listArticle">所有文章</a></li>
                            <li><a href="${basePath}/backend/writeArticle">撰写文章</a></li>
                            <li><a href="${basePath}/backend/listCategory">分类</a></li>
                            <li><a href="${basePath}/backend/listTag">标签</a></li>
                            <li><a href="${basePath}/backend/listComment">评论</a></li>
                        </ul>
                    </li>
                    <li><a><i class="fa fa-edit"></i> 页面 <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu">
                            <li><a href="${basePath}/backend/listPage">静态页面</a></li>
                            <li><a href="${basePath}/backend/listGallery">图片管理</a></li>
                        </ul>
                    </li>
                    <li><a><i class="fa fa-edit"></i> 小说 <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu">
                            <li><a href="${basePath}/backend/listNovel">小说管理</a></li>
                            <li><a href="${basePath}/backend/listNovelChapter">章节管理</a></li>
                        </ul>
                    </li>
                    <li><a><i class="fa fa-user"></i> 用户 <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu">
                            <li><a href="${basePath}/backend/listUser">用户列表</a></li>
                        </ul>
                    </li>
                    <li><a><i class="fa fa-cog"></i> 设置 <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu">
                            <li><a href="${basePath}/backend/listSettings">全局设置</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
        <!-- /sidebar menu -->
        <!-- /menu footer buttons -->
        <div class="sidebar-footer hidden-small">
            <a data-toggle="tooltip" data-placement="top" title="Settings" href="${basePath}/backend/listSettings">
                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
            </a>
            <a data-toggle="tooltip" data-placement="top" title="FullScreen">
                <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
            </a>
            <a data-toggle="tooltip" data-placement="top" title="Lock">
                <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
            </a>
            <a data-toggle="tooltip" data-placement="top" title="Logout" href="${basePath}/web/logout">
                <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
            </a>
        </div>
        <!-- /menu footer buttons -->
    </div>
</div>
