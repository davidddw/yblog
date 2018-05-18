<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>后台管理页面</title>
    <link rel="shortcut icon" href="${basePath}/static/favicon.ico"/>
    <!-- CDN start -->
    <link rel="stylesheet" href="${basePath}/static/vender/bootstrap/3.3.7/css/bootstrap.min.css" media="screen">
    <link rel="stylesheet" href="${basePath}/static/vender/font-awesome/4.6.3/css/font-awesome.min.css">
    <link rel="stylesheet" href="${basePath}/static/vender/cropperjs/1.0.0-rc/cropper.min.css">
    <link rel="stylesheet" href="${basePath}/static/vender/bootstrap-switch/3.3.4/css/bootstrap3/bootstrap-switch.min.css">
    <link rel="stylesheet" href="${basePath}/static/vender/nprogress/0.2.0/nprogress.min.css">
    <link rel="stylesheet" href="${basePath}/static/vender/toastr.js/2.1.3/toastr.min.css">
    <!-- CDN end-->
    <!-- Custom Theme Style -->
    <link rel="stylesheet" href="${basePath}/static/admin/css/custom.css">
    <link rel="stylesheet" href="${basePath}/static/admin/css/user-manage.css">
</head>
<body class="nav-md">
<div class="container body" id="crop-avatar">
    <div class="main_container">
        <#include "sidebar.ftl">
        <#include "navigation.ftl">
        <!-- page content -->
        <div class="right_col" role="main">
            <div class="">
                <div class="clearfix"></div>
                <form id="demo-form" data-parsley-validate>
                <div class="row">

                    <div class="col-md-9 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>撰写文章</h2>
                                <input type="text" style="display:none" id="articleId" value="${(article.id)!''}" />
                                <input type="text" style="display:none" id="createdDate" value="${(article.createdDate)!''}" />
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <div class="row">
                                    <div class="col-md-12 col-sm-12 col-xs-12 form-group">
                                        <input id="title" class="form-control col-md-7 col-xs-12" name="name" placeholder="输入文章标题" required type="text" value="${(article.title)!''}">
                                    </div>
                                    <div class="col-md-12 col-sm-12 col-xs-12 form-group">
                                        <textarea id="excerpt" class="resizable_textarea form-control" placeholder="摘要" required>${(article.excerpt)!''}</textarea>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12 col-sm-12 col-xs-12">
                                        <textarea id="content" class="form-control" name="editor1" rows="6">${(article.content)!''}</textarea>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="x_panel">
                            <div class="x_title">
                                <h2>属性设置</h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <div class="" role="tabpanel" data-example-id="togglable-tabs">
                                    <ul id="myTab" class="nav nav-tabs bar_tabs" role="tablist">
                                        <li role="presentation" class="active">
                                            <a href="#tab_content1" id="home-tab" role="tab" data-toggle="tab" aria-expanded="true">常用</a>
                                        </li>
                                        <li role="presentation" class="">
                                            <a href="#tab_content2" role="tab" id="profile-tab" data-toggle="tab" aria-expanded="false">文章属性</a>
                                        </li>
                                        <li role="presentation" class="">
                                            <a href="#tab_content3" role="tab" id="profile-tab2" data-toggle="tab" aria-expanded="false">评论状态</a>
                                        </li>
                                    </ul>
                                    <div id="myTabContent" class="tab-content">
                                        <div role="tabpanel" class="tab-pane fade active in" id="tab_content1" aria-labelledby="home-tab">
                                            <form class="form-horizontal form-label-left">
                                                <div class="col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">作者</label>
                                                    <div class="col-md-9 col-sm-9 col-xs-12">
                                                        <input type="text" class="form-control" name="author" id="author" placeholder="佚名" value="${(article.author)!''}">
                                                    </div>
                                                </div>
                                                <div class="col-md-6 col-sm-6 col-xs-12 form-group">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">文章状态</label>
                                                    <div class="col-md-9 col-sm-9 col-xs-12">
                                                        <select id="article_status" class="form-control">
                                                            <option value="0" <#if (article.articleStatus)??><#if article.articleStatus==0>selected</#if></#if>>下线</option>
                                                            <option value="1" <#if (article.articleStatus)??><#if article.articleStatus==1>selected</#if></#if>>在线</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-6 col-sm-6 col-xs-12 form-group">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">是否置顶</label>
                                                    <div class="col-md-9 col-sm-9 col-xs-12">
                                                        <select id="istop" class="form-control">
                                                            <option value="0" <#if (article.isTop)??><#if article.isTop==0>selected</#if></#if>>不置顶</option>
                                                            <option value="1" <#if (article.isTop)??><#if article.isTop==1>selected</#if></#if>>置顶</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-6 col-sm-6 col-xs-12 form-group">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">是否推荐</label>
                                                    <div class="col-md-9 col-sm-9 col-xs-12">
                                                        <select id="recommendation"class="form-control">
                                                            <option value="0" <#if (article.recommendation)??><#if article.recommendation==0>selected</#if></#if>>不推荐</option>
                                                            <option value="1" <#if (article.recommendation)??><#if article.recommendation==1>selected</#if></#if>>推荐</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-12 col-sm-12 col-xs-12 form-group">
                                                    <label for="source">文章来源:</label>
                                                    <input type="text" id="original" class="form-control" name="original" placeholder="琅嬛福地" value="${(article.original)!''}"/>
                                                </div>
                                                <div class="col-md-12 col-sm-12 col-xs-12 form-group">
                                                    <label for="source_url">文章来源:</label>
                                                    <input type="text" id="original_url" class="form-control" name="original_url" placeholder="http://d05660.top" value="${(article.originalUrl)!''}"/>
                                                </div>
                                            </form>
                                        </div>
                                        <div role="tabpanel" class="tab-pane fade" id="tab_content2" aria-labelledby="profile-tab">
                                            <form class="form-horizontal form-label-left form-inline">
                                                <div class="form-group">
                                                    <label class="control-label col-md-6 col-sm-6 col-xs-12">点击数</label>
                                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                                        <input type="text" id="read_count" class="form-control" placeholder="0" value="${(article.readCount?c)!''}" >
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-6 col-sm-6 col-xs-12">点赞数</label>
                                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                                        <input type="text" id="like_count" class="form-control" placeholder="0" value="${(article.likeCount?c)!''}">
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                        <div role="tabpanel" class="tab-pane fade" id="tab_content3" aria-labelledby="profile-tab">
                                            <p>开发中</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>发布文章</h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div class="row">
                                        <div class="col-lg-6">
                                        <#if (article)??>
                                            <button type="button" class="btn btn-success" id="submitUpdateForm"><span class="glyphicon glyphicon-ok"> 更新 </span></button>
                                        <#else>
                                            <button type="button" class="btn btn-success" id="submitAddForm"><span class="glyphicon glyphicon-ok"> 发布 </span></button>
                                        </#if>
                                        </div>
                                        <div class="col-lg-6">
                                            <button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-remove"> 撤销 </span></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>文章分类</h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <label for="category">类别:</label>
                                <select id="category" class="form-control" required>
                                <#if (article.category)??><option value="${article.category.id}">${article.category.term}</option><#else><option value="">请选择...</option></#if>
                                <#list categories as category><option value="${category.id}">${category.term}</option></#list>
                                </select>
                                <br/>
                                <label for="user">用户:</label>
                                <select id="user" class="form-control" required>
                                    <#if (article.user)??><option value="${article.user.id}">${article.user.name}</option><#else><option value="">请选择...</option></#if>
                                <#list users as user><option value="${user.id}">${user.name}</option></#list>
                                </select>
                                <br/>
                                <label for="fullname">标签 :</label>
                                <input type="text" id="tagname" class="form-control" name="tagname" placeholder='多个标签请用英文","分隔' value="${(article.tagStrings)!''}"/>
                            </div>
                        </div>
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>缩略图</h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <div class="avatar-view img-responsive" title="Change the avatar">
                                <#if (article.imageThumb)??><img src="${article.imageThumb}" alt="Avatar"><#else><img src="${basePath}/static/gr/xxx.gif" alt="Avatar"></#if>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
                </form>
            </div>

            <!-- Cropping modal -->
            <div class="modal fade" id="avatar-modal" aria-hidden="true" aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1">
                <div class="modal-dialog modal-md">
                    <div class="modal-content">
                        <form class="avatar-form" action="cropscale" enctype="multipart/form-data" method="post">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times</button>
                                <h4 class="modal-title" id="avatar-modal-label">上传图片</h4>
                            </div>
                            <div class="modal-body">
                                <div class="avatar-body">
                                    <!-- Upload image and data -->
                                    <div class="avatar-upload">
                                        <input type="hidden" class="avatar-src" name="avatar_src">
                                        <input type="hidden" class="avatar-data" name="avatar_data">
                                        <label for="avatarInput">图片上传</label>
                                        <input type="file" class="avatar-input" id="avatarInput" name="avatar_file">
                                    </div>

                                    <!-- Crop and preview -->
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="avatar-wrapper"></div>
                                        </div>
                                    </div>

                                    <div class="row avatar-btns">
                                        <div class="col-md-12">
                                            <button type="button" class="btn btn-primary btn-block avatar-save">保存修改</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div><!-- /.modal -->

            <!-- Loading state -->
            <div class="loading" aria-label="Loading" role="img" tabindex="-1"></div>

        </div>
        <!-- /page content -->
        <#include "footer.ftl">
        <!-- footer content -->
    </div>
</div>
<!-- CDN start -->
<script src="${basePath}/static/vender/jquery/1.12.4/jquery.min.js"></script>
<script src="${basePath}/static/vender/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="${basePath}/static/vender/datatables/1.10.13/js/jquery.dataTables.min.js"></script>
<script src="${basePath}/static/vender/datatables/1.10.13/js/dataTables.bootstrap.min.js"></script>
<script src="${basePath}/static/vender/moment.js/2.18.1/moment.min.js"></script>
<script src="${basePath}/static/vender/spin.js/2.3.2/spin.min.js"></script>
<script src="${basePath}/static/vender/parsley.js/2.7.0/parsley.min.js"></script>
<script src="${basePath}/static/vender/ckeditor/4.6.2/ckeditor.js"></script>
<script src="${basePath}/static/vender/cropper/2.3.4/cropper.min.js"></script>
<script src="${basePath}/static/vender/html2canvas/0.4.1/html2canvas.min.js"></script>
<script src="${basePath}/static/vender/toastr.js/2.1.3/toastr.min.js"></script>
<script src="${basePath}/static/vender/nprogress/0.2.0/nprogress.min.js"></script>
<!-- CDN end-->
<!-- cropperjs -->
<script src="${basePath}/static/admin/js/cropper.config.js"></script>
<!-- Custom Theme Scripts -->
<#--<script src="${basePath}/static/admin/js/custom.js"></script>-->
<script src="${basePath}/static/admin/js/pestle.js"></script>
</body>
</html>