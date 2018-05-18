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
    <link rel="stylesheet" href="${basePath}/static/vender/x-editable/1.5.1/bootstrap-editable/css/bootstrap-editable.css">
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
                                <h2>配置管理 </h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <div class="row-fluid">
                                    <div style="float: right; margin-bottom: 10px">
                                        <button id="enable" class="btn btn-default">enable / disable</button>
                                    </div>
                                    <table id="user" class="table table-bordered table-striped">
                                        <thead>
                                        <tr>
                                            <th>字段名</th>
                                            <th>字段值</th>
                                            <th>描述</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td><span class="text-muted">Simple text field</span></td>
                                            <td><a href="#" id="username" data-type="text" data-pk="1" data-title="Enter username"><span class="text-muted">superuser</span></a></td>
                                            <td><span class="text-muted">Simple text field </span></td>
                                        </tr>
                                        <tr>
                                            <td>Empty text field, required</td>
                                            <td><a href="#" id="firstname" data-type="text" data-pk="1" data-placement="right" data-placeholder="Required"
                                                   data-title="Enter your firstname"></a></td>
                                            <td><span class="text-muted">Simple text field </span></td>
                                        </tr>
                                        </tbody>
                                    </table>
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
<script src="${basePath}/static/vender/x-editable/1.5.1/bootstrap-editable/js/bootstrap-editable.js"></script>
<script src="${basePath}/static/vender/moment.js/2.18.1/moment.min.js"></script>
<script src="${basePath}/static/vender/nprogress/0.2.0/nprogress.min.js"></script>
<!-- CDN end-->
<!-- Custom Theme Scripts -->
<#--<script src="${basePath}/static/admin/js/custom.js"></script>-->
<script src="${basePath}/static/admin/js/pestle.js"></script>
</body>
</html>