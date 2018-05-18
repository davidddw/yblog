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
                <div class="clearfix"></div>
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>评论管理 </h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <div class="row-fluid">
                                    <div class="text-center text-center">
                                        <br/>
                                        <br/>
                                        <h1 class="error-number">404</h1>
                                        <h1>页面建设中...</h1>
                                        <br/>
                                        <br/>
                                        <br/>
                                        <br/>
                                    </div>
                                </div>
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
<script src="${basePath}/static/admin/js/pestle.js"></script></script>
</body>
</html>