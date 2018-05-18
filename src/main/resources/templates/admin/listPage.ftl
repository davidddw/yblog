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
                                <h2>页面管理 </h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <div class="pull-right">
                                    <div class="btn-group">
                                        <button type="button" class="btn btn-primary btn-sm" id="btn-add"
                                                data-toggle="modal" data-target=".bs-example-modal-lg">
                                            <i class="fa fa-plus"></i> 添加页面
                                        </button>
                                        <button type="button" class="btn btn-primary btn-sm" id="btn-delAll">
                                            <i class="fa fa-remove"></i> 批量删除
                                        </button>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="table_page_length">
                                        <label>每页
                                            <select id="page" name="page" class="form-control input-sm">
                                                <option value="10">10</option>
                                                <option value="25">25</option>
                                                <option value="50">50</option>
                                                <option value="100">100</option>
                                            </select> 项
                                        </label>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span12" id="div-table-page">
                                        <table class="table table-striped table-bordered table-hover dt-responsive nowrap" id="table-page"
                                               cellspacing="0" width="100%">
                                            <thead>
                                            <tr>
                                                <th>
                                                    <input type="checkbox" name="cb-check-all" class="iCheck">
                                                </th>
                                                <th>编号</th>
                                                <th>标题</th>
                                                <th>Slug</th>
                                                <th>优先级</th>
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
                <!-- modals -->
                <div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
                                </button>
                                <h4 class="modal-title" id="myModalLabel">页面信息</h4>
                            </div>
                            <div class="modal-body1 info-content">
                                <div class="block-content ">
                                    <form id="form-add">
                                        <input type="text" style="display:none" id="page_id"  />
                                        <div class="control-group">
                                            <label class="control-label" for="extn-add">
                                                <span class="red-asterisk">*</span>标题:</label>
                                            <div class="controls">
                                                <input type="text" id="page_title" name="extn-add" placeholder="静态页面标题" required>
                                            </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="control-label" for="name-add">
                                                <span class="red-asterisk">*</span>Slug:</label>
                                            <div class="controls">
                                                <input type="text" id="page_slug" name="name-add" required placeholder="slug">
                                            </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="control-label" for="name-add">
                                                <span class="red-asterisk">*</span>内容:</label>
                                            <div class="controls">
                                                <textarea id="content" class="ckeditor form-control" name="editor1" rows="6">内容</textarea>
                                            </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="control-label" for="office-add">优先级:</label>
                                            <div class="controls">
                                                <input type="text" id="page_priority" name="office-add" required placeholder="0">
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-close"></i> 关闭</button>
                                            <button type="button" class="btn btn-primary" id="submit_page"><i class="fa fa-check"></i> 保存</button>
                                        </div>
                                    </form>
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
<script src="${basePath}/static/vender/ckeditor/4.6.2/ckeditor.js"></script>
<!-- lhgdialog -->
<script src="${basePath}/static/admin/js/lhgdialog/lhgdialog.js"></script>
<!-- Custom Theme Scripts -->
<#--<script src="${basePath}/static/admin/js/custom.js"></script>-->
<script src="${basePath}/static/admin/js/pestle.js"></script>
</body>
</html>