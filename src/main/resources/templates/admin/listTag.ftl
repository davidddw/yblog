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
    <link rel="stylesheet" href="${basePath}/static/vender/toastr.js/2.1.3/toastr.min.css">
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
                                <h2>标签管理</h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <div class="col-md-4 col-sm-12 col-xs-12">
                                    <div class="row-fluid">
                                        <h4>热门标签</h4>
                                        <div class="taglist" id="tags">
                                        <#list tags as tag>
                                            <span>${tag.name}</span>
                                        </#list>
                                        </div>
                                        <form id="demo-form" data-parsley-validate>
                                            <input type="text" style="display:none" id="tag_id"  />
                                            <label for="tag_name">标签:</label>
                                            <input type="text" id="tag_name" class="form-control" name="tag_name" required />
                                            <p></p>
                                            <label for="tag_slug">Slug:</label>
                                            <input type="text" id="tag_slug" class="form-control" name="tag_slug" required />
                                            <p></p>
                                            <label for="tag_intro">介绍:</label>
                                            <textarea id="tag_intro" required="required" class="form-control" name="message"></textarea>
                                            <br/>
                                            <span class="btn btn-primary glyphicon glyphicon-ok" id="submitForm">新增&更新</span>
                                        </form>
                                        <!-- end form for validations -->
                                    </div>
                                </div>
                                <div class="col-md-8 col-sm-12 col-xs-12">
                                    <div class="row-fluid">
                                        <div class="span12" id="div-table-tag">
                                            <table class="table table-striped table-bordered table-hover table-condensed" id="table-tag" cellspacing="0" width="100%">
                                                <thead>
                                                <tr>
                                                    <th>
                                                        <input type="checkbox" name="cb-check-all" class="iCheck">
                                                    </th>
                                                    <th>编号</th>
                                                    <th>名称</th>
                                                    <th>Slug</th>
                                                    <th>介绍</th>
                                                    <th>文章数</th>
                                                    <th>操作</th>
                                                </tr>
                                                </thead>
                                            </table>
                                        </div>
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
<script src="${basePath}/static/admin/js/pestle.js"></script>
</body>
</html>