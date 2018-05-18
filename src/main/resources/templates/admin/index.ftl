<!DOCTYPE html>
<html lang="en">
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
    <link rel="stylesheet" href="${basePath}/static/vender/datatables/1.10.13/css/dataTables.bootstrap.min.css">
    <link rel="stylesheet" href="${basePath}/static/vender/nprogress/0.2.0/nprogress.min.css">
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
                <div class="row top_tiles">
                    <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
                        <div class="tile-stats">
                            <div class="icon"><i class="fa fa-caret-square-o-right"></i></div>
                            <div class="count">17</div>
                            <h3>文章总数</h3>
                        </div>
                    </div>
                    <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
                        <div class="tile-stats">
                            <div class="icon"><i class="fa fa-comments-o"></i></div>
                            <div class="count">4</div>
                            <h3>页面统计</h3>
                        </div>
                    </div>
                    <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
                        <div class="tile-stats">
                            <div class="icon"><i class="fa fa-sort-amount-desc"></i></div>
                            <div class="count">9</div>
                            <h3>用户统计</h3>
                        </div>
                    </div>
                    <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
                        <div class="tile-stats">
                            <div class="icon"><i class="fa fa-check-square-o"></i></div>
                            <div class="count">0</div>
                            <h3>评论数</h3>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>最新文章</h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <article class="media event">
                                    <a class="pull-left date">
                                        <p class="month">April</p>
                                        <p class="day">23</p>
                                    </a>
                                    <div class="media-body">
                                        <a class="title" href="#">Item One Title</a>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                                    </div>
                                </article>
                                <article class="media event">
                                    <a class="pull-left date">
                                        <p class="month">April</p>
                                        <p class="day">23</p>
                                    </a>
                                    <div class="media-body">
                                        <a class="title" href="#">Item Two Title</a>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                                    </div>
                                </article>
                                <article class="media event">
                                    <a class="pull-left date">
                                        <p class="month">April</p>
                                        <p class="day">23</p>
                                    </a>
                                    <div class="media-body">
                                        <a class="title" href="#">Item Two Title</a>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                                    </div>
                                </article>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>最新评论</h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <article class="media event">
                                    <a class="pull-left date">
                                        <p class="month">April</p>
                                        <p class="day">23</p>
                                    </a>
                                    <div class="media-body">
                                        <a class="title" href="#">Item One Title</a>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                                    </div>
                                </article>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>最新页面</h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <article class="media event">
                                    <a class="pull-left date">
                                        <p class="month">April</p>
                                        <p class="day">23</p>
                                    </a>
                                    <div class="media-body">
                                        <a class="title" href="#">Item One Title</a>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                                    </div>
                                </article>
                                <article class="media event">
                                    <a class="pull-left date">
                                        <p class="month">April</p>
                                        <p class="day">23</p>
                                    </a>
                                    <div class="media-body">
                                        <a class="title" href="#">Item Two Title</a>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                                    </div>
                                </article>
                                <article class="media event">
                                    <a class="pull-left date">
                                        <p class="month">April</p>
                                        <p class="day">23</p>
                                    </a>
                                    <div class="media-body">
                                        <a class="title" href="#">Item Three Title</a>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                                    </div>
                                </article>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /page content -->
    <#include "footer.ftl">
        <!-- footer content -->
    </div>
</div>
<!-- CDN start -->
<script src="${basePath}/static/vender/jquery/1.12.4/jquery.min.js"></script>
<script src="${basePath}/static/vender/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="${basePath}/static/vender/spin.js/2.3.2/spin.min.js"></script>
<script src="${basePath}/static/vender/parsley.js/2.7.0/parsley.min.js"></script>
<script src="${basePath}/static/vender/datatables/1.10.13/js/jquery.dataTables.min.js"></script>
<script src="${basePath}/static/vender/datatables/1.10.13/js/dataTables.bootstrap.min.js"></script>
<script src="${basePath}/static/vender/toastr.js/2.1.3/toastr.min.js"></script>
<script src="${basePath}/static/vender/iCheck/1.0.2/icheck.min.js"></script>
<script src="${basePath}/static/vender/nprogress/0.2.0/nprogress.min.js"></script>
<!-- CDN end-->
<!-- lhgdialog -->
<script src="${basePath}/static/admin/js/lhgdialog/lhgdialog.js"></script>
<!-- Custom Theme Scripts -->
<#--<script src="${basePath}/static/admin/js/custom.js"></script>-->
<script src="${basePath}/static/admin/js/pestle.js"></script>
<!-- Google Analytics -->
</body>
</html>