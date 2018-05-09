/**
 *
 * Created by d05660ddw on 2017/3/27.
 *
 */

(function (factory) {
    if (typeof exports == 'object') {
        // CommonJS
        factory(require('jquery'), require('spin.js'))
    }
    else if (typeof define == 'function' && define.amd) {
        // AMD, register as anonymous module
        define(['jquery', 'spin'], factory)
    }
    else {
        // Browser globals
        if (!window.Spinner) throw new Error('Spin.js not present')
        factory(window.jQuery, window.Spinner)
    }

}(function ($, Spinner) {

    $.fn.spin = function (opts, color, modal) {

        return this.each(function () {
            var $this = $(this),
                data = $this.data();

            if (data.spinner) {
                data.spinner.stop();
                delete data.spinner;
            }
            if (opts !== false) {
                opts = $.extend(
                    {color: color || $this.css('color')},
                    $.fn.spin.presets[opts] || opts
                )

                data.spinner = new Spinner(opts).spin(this)
            }
        })
    };

    $.fn.spin.presets = {
        tiny: {lines: 8, length: 2, width: 2, radius: 3},
        small: {lines: 8, length: 4, width: 3, radius: 5},
        large: {lines: 10, length: 8, width: 4, radius: 8}
    };

    $.fn.spinModal = function (opts, color) {

        return this.each(function () {
            var $this = $(this),
                data = $this.data();

            if (data.spinner) {
                data.spinner.stop();
                data.overlay.remove();
                delete data.spinner;
                delete data.overlay;
            }
            if (opts !== false) {
                opts = $.extend(
                    {color: color || $this.css('color')},
                    $.fn.spin.presets[opts] || opts
                );
                var overlay = $("<div />", {"class": "overlay"})
                $this.append(overlay);
                data.spinner = new Spinner(opts).spin(this);
                data.overlay = overlay;
            }
        });
    }

}));

(function ($) {
    $.extend({
        loadAllObject: function (url, handleData) {
            $.ajax({
                type: "GET",
                url: url,
                dataType: "json",
                data: data,
                timeout: 1000,
                beforeSend: function () {
                    //$('.loading').showLoading();
                },
                success: handleData,
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });
            return false;
        },

        getTotalObject: function (url, data, handleData) {
            $.ajax({
                type: "GET",
                url: url,
                dataType: "json",
                data: data,
                success: handleData,
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });
            return false;
        },

        //curd operations
        loadObject: function (url, id, handleData) {
            $.ajax({
                type: "GET",
                url: url + "/" + id,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: handleData,
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });
            return false;
        },

        addNewObject: function (url, jsonData, handleData) {
            $.ajax({
                type: "POST",
                url: url,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data: jsonData,
                success: handleData,
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });
            return false;
        },
        addFormObject: function (url, formData, handleData) {
            $.ajax({
                type: "POST",
                url: url,
                processData: false,
                contentType: false,
                data: formData,
                success: handleData,
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });
            return false;
        },
        updateFormObject: function (url, id, formData, handleData) {
            $.ajax({
                type: "PUT",
                url: url + "/" + id,
                processData: false,
                contentType: false,
                data: formData,
                success: handleData,
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });
            return false;
        },
        updateObject: function (url, id, jsonData, handleData) {
            $.ajax({
                type: "PUT",
                url: url + "/" + id,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data: jsonData,
                success: handleData,
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });
            return false;
        },
        deleteObject: function (url, id, handleData) {
            $.ajax({
                type: "DELETE",
                url: url + "/" + id,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: handleData,
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });
            return false;
        },
        deleteMultiObject: function (url, jsonData, handleData) {
            $.ajax({
                type: "POST",
                url: url,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data: jsonData,
                success: handleData,
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });
            return false;
        },
        toastSuccess: function (msg) {
            toastr.options = {
                closeButton: true,
                debug: false,
                positionClass: "toast-top-right",
                onclick: null,
                showDuration: "300",
                hideDuration: "1000",
                timeOut: "2000",
                extendedTimeOut: "1000",
                showEasing: "swing",
                hideEasing: "linear",
                showMethod: "fadeIn",
                hideMethod: "fadeOut"
            };
            toastr.success(msg);
        },
        toastError: function (msg) {
            toastr.options = {
                closeButton: true,
                debug: false,
                positionClass: "toast-top-right",
                onclick: null,
                showDuration: "300",
                hideDuration: "1000",
                timeOut: "2000",
                extendedTimeOut: "1000",
                showEasing: "swing",
                hideEasing: "linear",
                showMethod: "fadeIn",
                hideMethod: "fadeOut"
            };
            toastr.error(msg);
        },
        table_ajax: function (wrapper, url, objectManage) {
            return function (data, callback, settings) {//ajax配置为function,手动调用异步查询
                //手动控制遮罩
                wrapper.spinModal();
                //封装请求参数
                var param = objectManage.getQueryCondition(data);
                $.ajax({
                    type: "GET",
                    url: url,
                    cache: false,	//禁用缓存
                    data: param,	//传入已封装的参数
                    dataType: "json",
                    success: function (result) {
                        //setTimeout仅为测试遮罩效果
                        setTimeout(function () {
                            //异常判断与处理
                            if (result.errorCode) {
                                $.dialog.alert("查询失败。错误码：" + result.errorCode);
                                return;
                            }
                            //封装返回数据，这里仅演示了修改属性名
                            var returnData = {};
                            returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                            returnData.recordsTotal = result.total;
                            returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                            returnData.data = result.pageData;
                            //关闭遮罩
                            wrapper.spinModal(false);
                            //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                            //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                            callback(returnData);
                        }, 200);
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        $.dialog.alert("查询失败");
                        wrapper.spinModal(false);
                    }
                });
            }
        },
        drawCallback: function (wrapper, table) {
            return function (settings) {
                //渲染完毕后的回调
                //清空全选状态
                $(":checkbox[name='cb-check-all']", wrapper).prop('checked', false);
                //默认选中第一行
                $("tbody tr", table).eq(0).click();
            }
        }
    });
})(jQuery);

/*常量*/
var CONSTANT = {
    DATA_TABLES: {
        DEFAULT_OPTION: { //DataTables初始化选项
            language: {
                "sProcessing": "处理中...",
                "sLengthMenu": "每页 _MENU_ 项",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
                "sInfoEmpty": "当前显示第 0 至 0 项，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "sEmptyTable": "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页",
                    "sJump": "跳转"
                },
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            },
            autoWidth: false,	//禁用自动调整列宽
            stripeClasses: ["odd", "even"],//为奇偶行加上样式，兼容不支持CSS伪类的场合
            order: [],	//取消默认排序查询,否则复选框一列会出现小箭头
            //processing: true,	//隐藏加载提示,自行处理
            serverSide: true,	//启用服务器端分页
            pageLength: 10,
            pagingType: "full_numbers",
            stateSave: true,
            lengthChange:   false,
            searching: false	//用原生搜索
        },
        COLUMN: {
            CHECKBOX: {	//复选框单元格
                className: "td-checkbox",
                orderable: false,
                width: "30px",
                data: null,
                render: function (data, type, row, meta) {
                    return '<input type="checkbox" class="iCheck">';
                }
            }
        },
        RENDER: {	//常用render可以抽取出来，如日期时间、头像等
            ELLIPSIS: function (data, type, row, meta) {
                data = data || "";
                return '<span title="' + data + '">' + data + '</span>';
            }
        }
    }
};

(function () {
    function Validator($element) {
        this.$container = $element;
        this.$bsCalloutInfo = this.$container.find('.bs-callout-info');
        this.$bsCalloutWarnin = this.$container.find('bs-callout-warnin');
    }

    Validator.prototype = {
        constructor: Validator,

        validate: function () {
            this.$container.parsley().validate();
            if (true === this.$container.parsley().isValid()) {
                this.$bsCalloutInfo.removeClass('hidden');
                this.$bsCalloutWarnin.addClass('hidden');
                return true;
            } else {
                this.$bsCalloutInfo.addClass('hidden');
                this.$bsCalloutWarnin.removeClass('hidden');
                return false;
            }
        }
    };
    $.fn.validate = function () {
        //创建Beautifier的实体
        var validator = new Validator(this);
        //调用其方法
        return validator.validate();
    };
})();

function initArticle() {
    if (typeof ($.fn.DataTable) === 'undefined') {
        return;
    }
    if (typeof (toastr) === 'undefined') {
        return;
    }
    if (typeof (moment) === 'undefined') {
        return;
    }

    var articleManage = {
        currentItem: null,
        fuzzySearch: true,
        getQueryCondition: function (data) {
            var param = {};
            //组装排序参数
            if (data.order && data.order.length && data.order[0]) {
                switch (data.order[0].column) {
                    case 2:
                        param.orderColumn = "title";
                        break;
                    case 3:
                        param.orderColumn = "author";
                        break;
                    case 4:
                        param.orderColumn = "category";
                        break;
                    case 5:
                        param.orderColumn = "articleStatus";
                        break;
                    case 6:
                        param.orderColumn = "createdDate";
                        break;
                    default:
                        param.orderColumn = "createdDate";
                        break;
                }
                param.orderDir = data.order[0].dir;
            }
            //组装分页参数
            param.startIndex = data.start;
            param.pageSize = data.length;

            param.draw = data.draw;

            return param;
        },
        deleteItem: function (selectedItems) {
            var message;
            if (selectedItems && selectedItems.length) {
                if (selectedItems.length === 1) {
                    message = "确定要删除 '" + selectedItems[0].title + "' 吗?";
                    $.dialog.confirmDanger(message, function () {
                        $.deleteObject("/backend/article", selectedItems[0].id, function (data) {
                            $.toastSuccess("删除" + data.result + "项纪录成功!!!");
                            _table.draw();
                        });
                    });
                }
            } else {
                $.dialog.tips('请先选中要操作的行');
            }
        }
    };

    var $wrapper = $("#div-table-container"),
        $table = $("#table-article");

    var _table = $table.DataTable($.extend(true, {}, CONSTANT.DATA_TABLES.DEFAULT_OPTION, {
        ajax: $.table_ajax($wrapper, '/backend/article', articleManage),
        columns: [
            CONSTANT.DATA_TABLES.COLUMN.CHECKBOX,
            {
                data: "id",
                visible: false
            },
            {
                className: "ellipsis",	//文字过长时用省略号显示，CSS实现
                data: "title",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS //会显示省略号的列，需要用title属性实现划过时显示全部文本的效果
            },
            {
                className: "ellipsis",
                data: "author",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                //固定列宽，但至少留下一个活动列不要固定宽度，让表格自行调整。不要将所有列都指定列宽，否则页面伸缩时所有列都会随之按比例伸缩。
                //切记设置table样式为table-layout:fixed; 否则列宽不会强制为指定宽度，也不会出现省略号。
                width: "50px"
            },
            {
                className: "ellipsis",
                data: "category.term",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                //固定列宽，但至少留下一个活动列不要固定宽度，让表格自行调整。不要将所有列都指定列宽，否则页面伸缩时所有列都会随之按比例伸缩。
                //切记设置table样式为table-layout:fixed; 否则列宽不会强制为指定宽度，也不会出现省略号。
                width: "80px"
            },
            {
                data: "articleStatus",
                width: "50px",
                //visible: false,
                render: function (data, type, row, meta) {
                    return '<span><i class="fa fa-male"></i>' + (data ? "上线" : "下线") + '</span>';
                }
            },
            {
                data: "createdDate",
                width: "60px",
                render: function (data, type, row, meta) {
                    return '<span>' + moment(data).format("YYYY-MM-DD") + '</span>';
                }
            },
            {
                className: "td-operation",
                data: null,
                defaultContent: "",
                orderable: false,
                width: "120px"
            }
        ],
        "createdRow": function (row, data, index) {
            //行渲染回调,在这里可以对该行dom元素进行任何操作
            //给当前行加样式
            if (data.role) {
                $(row).addClass("info");
            }
            //给当前行某列加样式
            $('td', row).eq(4).addClass(data.articleStatus ? "text-success" : "text-danger");
            //不使用render，改用jquery文档操作呈现单元格
            var $btnPrev = $('<span><button type="button" class="btn btn-xs btn-info btn-preview">预览</button></span>');
            var $btnEdit = $('<span><button type="button" class="btn btn-xs btn-primary btn-edit">修改</button></span>');
            var $btnDel = $('<span><button type="button" class="btn btn-xs btn-danger btn-del">删除</button></span>');
            $('td', row).eq(6).append($btnPrev).append($btnEdit).append($btnDel);
        },
        "drawCallback": $.drawCallback($wrapper, $table)
    }));

    $table.on("change", ":checkbox", function () {
        if ($(this).is("[name='cb-check-all']")) {
            //全选
            $(":checkbox", $table).prop("checked", $(this).prop("checked"));
        } else {
            //一般复选
            var checkbox = $("tbody :checkbox", $table);
            $(":checkbox[name='cb-check-all']", $table).prop('checked', checkbox.length === checkbox.filter(':checked').length);
        }
    }).on("click", ".td-checkbox", function (event) {
        //点击单元格即点击复选框
        !$(event.target).is(":checkbox") && $(":checkbox", this).trigger("click");
    }).on("click", ".btn-preview", function () {
        //点击预览按钮
        var item = _table.row($(this).closest('tr')).data();
        window.location.href = "/web/article/" + item.wid + ".html";
    }).on("click", ".btn-edit", function () {
        //点击编辑按钮
        var item = _table.row($(this).closest('tr')).data();
        window.location.href = "/backend/writeArticle?id=" + item.id;
    }).on("click", ".btn-del", function () {
        //点击删除按钮
        var item = _table.row($(this).closest('tr')).data();
        articleManage.deleteItem([item]);
    });

    $('#page').val(_table.page.len());

    $('#page').change(function() {
        var pageLength = $('#page').val();
        _table.page.len(pageLength).draw();
    });
}

function modifyArticle() {

    if (typeof (parsley) === 'undefined') {
        return;
    }
    if (typeof (toastr) === 'undefined') {
        return;
    }
    if (typeof (CKEDITOR) === 'undefined') {
        return;
    }

    CKEDITOR.replace("content", {
        extraPlugins: 'justify,font,showblocks,colorbutton,codesnippet',
        codeSnippet_theme: 'zenburn',
        language: "zh-cn",
        toolbar: 'Basic',
        toolbarCanCollapse: true,

        toolbar: [
            ['Source', 'Maximize', 'ShowBlocks', '-', 'Link', 'Unlink'],
            ['Bold', 'Italic', 'Strike'], ['JustifyLeft', 'JustifyCenter'],
            ['NumberedList', 'BulletedList', '-', 'Blockquote'],
            ['TextColor', 'BGColor', '-', 'Format', 'Image', 'Table', 'CodeSnippet']
        ],
        image_previewText: ' ',
        filebrowserUploadUrl: '/backend/upload?type=File',
        filebrowserImageUploadUrl: '/backend/upload?type=Image',
        removeButtons: 'Subscript,Superscript,Underline,JustifyRight,JustifyBlock'
    });

    var $addNewArticle = $('#submitAddForm'),
        $updateArticle = $('#submitUpdateForm');

    var $title = $("#title"),
        $author = $("#author"),
        $excerpt = $("#excerpt"),
        $istop = $("#istop"),
        $recommendation = $("#recommendation"),
        $original = $("#original"),
        $original_url = $("#original_url"),
        $category = $("#category"),
        $user = $("#user"),
        $image_thumb = $(".avatar-view img"),
        $tagname = $("#tagname"),
        $read_count = $("#read_count"),
        $like_count = $("#like_count"),
        $article_status = $("#article_status");

    var jsonData = {};

    var url = "/backend/article";

    $addNewArticle.on('click', function () {
        if (!$('#demo-form').validate()) {
            return false;
        }
        jsonData.title = $title.val();
        jsonData.excerpt = $excerpt.val();
        jsonData.content = CKEDITOR.instances.content.getData();
        jsonData.author = $author.val() ? $author.val() : $author.attr("placeholder");
        jsonData.istop = $istop.val();
        jsonData.recommendation = $recommendation.val();
        jsonData.article_status = $article_status.val();
        jsonData.original = $original.val() ? $original.val() : $original.attr("placeholder");
        jsonData.original_url = $original_url.val() ? $original_url.val() : $original_url.attr("placeholder");
        jsonData.category = $category.val();
        jsonData.user = $user.val();
        jsonData.image_thumb = $image_thumb.attr("src");
        jsonData.tagname = $tagname.val();
        jsonData.read_count = $read_count.val() ? $read_count.val() : $read_count.attr("placeholder");
        jsonData.like_count = $like_count.val() ? $like_count.val() : $like_count.attr("placeholder");

        $.addNewObject(url, JSON.stringify(jsonData), function (data) {
            if (data.result) {
                $.toastSuccess("添加文章成功!!!");
                $title.val("");
                $excerpt.val("");
                $author.val("");
                CKEDITOR.instances.content.setData("这里是默认值，修改文本的内容是放在这里。");
                $istop.bootstrapSwitch('setState', false);
                $recommendation.bootstrapSwitch('setState', false);
                $original.val("");
                $original_url.val("");
                $tagname.val("");
                $read_count.val("");
                $like_count.val("");
            } else {
                $.toastError("添加文章失败!!!");
            }
        });
        return false;
    });

    $updateArticle.on('click', function () {
        if (!$("#demo-form").validate()) {
            return false;
        }
        var $id = $("#articleId").val();
        var $created_date = $("#createdDate").val();
        jsonData.id = $id;
        jsonData.created_date = $created_date;
        jsonData.title = $title.val();
        jsonData.excerpt = $excerpt.val();
        jsonData.content = CKEDITOR.instances.content.getData();
        jsonData.author = $author.val() ? $author.val() : $author.attr("placeholder");
        jsonData.istop = $istop.val();
        jsonData.recommendation = $recommendation.val();
        jsonData.article_status = $article_status.val();
        jsonData.original = $original.val() ? $original.val() : $original.attr("placeholder");
        jsonData.original_url = $original_url.val() ? $original_url.val() : $original_url.attr("placeholder");
        jsonData.category = $category.val();
        jsonData.user = $user.val();
        jsonData.image_thumb = $image_thumb.attr("src");
        jsonData.tagname = $tagname.val();
        jsonData.read_count = $read_count.val() ? $read_count.val() : $read_count.attr("placeholder");
        jsonData.read_count = $read_count.val() ? $read_count.val() : $read_count.attr("placeholder");
        jsonData.like_count = $like_count.val() ? $like_count.val() : $like_count.attr("placeholder");
        $.updateObject(url, $id, JSON.stringify(jsonData), function (data) {
            if (data.result) {
                $.toastSuccess("更新文章成功!!!");
                window.location.href = "/backend/listArticle";
            } else {
                $.toastError("更新文章失败!!!");
            }
        });
        return false;
    });
}

function initCategory() {
    if (typeof ($.fn.DataTable) === 'undefined') {
        return;
    }
    if (typeof (toastr) === 'undefined') {
        return;
    }
    if (typeof (parsley) === 'undefined') {
        return;
    }

    var categoryManage = {
        currentItem: null,
        getQueryCondition: function (data) {
            var param = {};
            //组装排序参数
            if (data.order && data.order.length && data.order[0]) {
                switch (data.order[0].column) {
                    case 2:
                        param.orderColumn = "id";
                        break;
                    case 3:
                        param.orderColumn = "name";
                        break;
                    case 4:
                        param.orderColumn = "term";
                        break;
                    case 5:
                        param.orderColumn = "intro";
                        break;
                    default:
                        param.orderColumn = "id";
                        break;
                }
                param.orderDir = data.order[0].dir;
            }
            //组装分页参数
            param.startIndex = data.start;
            param.pageSize = data.length;

            param.draw = data.draw;

            return param;
        },
        editItemInit: function (item) {
            if (!item) {
                return;
            }
            $("#category_name").val(item.name);
            $("#category_id").val(item.id);
            $("#category_term").val(item.term);
            $("#category_intro").val(item.intro);
        },
        deleteItem: function (selectedItems) {
            var message;
            if (selectedItems && selectedItems.length) {
                if (selectedItems.length === 1) {
                    message = "确定要删除 '" + selectedItems[0].term + "' 吗?";
                    $.dialog.confirmDanger(message, function () {
                        if (selectedItems[0].id === 1) {
                            $.toastError("不能删除默认类别!!!");
                        } else {
                            $.deleteObject("/backend/category", selectedItems[0].id, function (data) {
                                $.toastSuccess("删除" + data.result + "项纪录成功!!!");
                                _table.draw();
                            });
                        }
                    });
                }
            } else {
                $.dialog.tips('请先选中要操作的行');
            }
        }
    };

    var $wrapper = $("#div-table-container"),
        $table = $("#table-category");

    var _table = $table.DataTable($.extend(true, {}, CONSTANT.DATA_TABLES.DEFAULT_OPTION, {
        ajax: $.table_ajax($wrapper, '/backend/category', categoryManage),
        columns: [
            CONSTANT.DATA_TABLES.COLUMN.CHECKBOX,
            {
                data: "id",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                width: "40px"
            },
            {
                className: "ellipsis",	//文字过长时用省略号显示，CSS实现
                data: "name",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                width: "120px"
            },
            {
                className: "ellipsis",
                data: "term",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                width: "120px"
            },
            {
                className: "ellipsis",
                data: "intro",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS
            },
            {
                className: "td-operation",
                data: null,
                defaultContent: "",
                orderable: false,
                width: "120px"
            }
        ],
        "createdRow": function (row, data, index) {
            //行渲染回调,在这里可以对该行dom元素进行任何操作
            //给当前行加样式
            if (data.role) {
                $(row).addClass("info");
            }
            //不使用render，改用jquery文档操作呈现单元格
            var $btnPrev = $('<span><button type="button" class="btn btn-xs btn-info btn-preview">预览</button></span>');
            var $btnEdit = $('<span><button type="button" class="btn btn-xs btn-success btn-edit" data-toggle="modal" data-target=".bs-example-modal-md">修改</button></span>');
            var $btnDel = $('<span><button type="button" class="btn btn-xs btn-danger btn-del">删除</button></span>');
            $('td', row).eq(5).append($btnPrev).append($btnEdit).append($btnDel);
        },
        "drawCallback": $.drawCallback($wrapper, $table)
    }));

    $table.on("change", ":checkbox", function () {
        if ($(this).is("[name='cb-check-all']")) {
            //全选
            $(":checkbox", $table).prop("checked", $(this).prop("checked"));
        } else {
            //一般复选
            var checkbox = $("tbody :checkbox", $table);
            $(":checkbox[name='cb-check-all']", $table).prop('checked', checkbox.length === checkbox.filter(':checked').length);
        }
    }).on("click", ".td-checkbox", function (event) {
        //点击单元格即点击复选框
        !$(event.target).is(":checkbox") && $(":checkbox", this).trigger("click");
    }).on("click", ".btn-preview", function () {
        //点击预览按钮
        var item = _table.row($(this).closest('tr')).data();
        window.location.href = "/web/category/" + item.name;
    }).on("click", ".btn-edit", function () {
        //点击编辑按钮
        var item = _table.row($(this).closest('tr')).data();
        categoryManage.currentItem = item;
        categoryManage.editItemInit(item);
    }).on("click", ".btn-del", function () {
        //点击删除按钮
        var item = _table.row($(this).closest('tr')).data();
        categoryManage.deleteItem([item]);
    });

    var $category_name = $('#category_name'),
        $category_id = $('#category_id'),
        $category_term = $('#category_term'),
        $category_intro = $('#category_intro'),
        $myModal = $('.bs-example-modal-md'),
        $submitButton = $('#submit_category');

    $myModal.on("hidden.bs.modal", function () {
        $category_id.val("");
        $category_name.val("");
        $category_term.val("");
        $category_intro.val("");
        $(this).find('form')[0].reset();
    });

    $submitButton.on("click", function () {
        if (!$('#form-add').validate()) {
            return false;
        }

        var jsonData = {};

        var url = "/backend/category";
        jsonData.name = $category_name.val();
        jsonData.term = $category_term.val();
        jsonData.intro = $category_intro.val();

        if ($category_id.val()) {
            var id = $category_id.val();
            jsonData.id = id;
            $.updateObject(url, id, JSON.stringify(jsonData), function (data) {
                if (data.result) {
                    $.toastSuccess("更新类别成功!!!");
                    $category_id.val("");
                    $category_name.val("");
                    $category_term.val("");
                    $category_intro.val("");
                } else {
                    $.toastError("更新类别失败!!!");
                }
                $myModal.modal('hide');
                _table.draw();
            });
        } else {
            $.addNewObject(url, JSON.stringify(jsonData), function (data) {
                if (data.result) {
                    $.toastSuccess("添加类别成功!!!");
                    $category_id.val("");
                    $category_name.val("");
                    $category_term.val("");
                    $category_intro.val("");
                } else {
                    $.toastError("添加类别失败!!!");
                }
                $myModal.modal('hide');
                _table.draw();
            });
        }
    });
}

function initTag() {
    if (typeof ($.fn.DataTable) === 'undefined') {
        return;
    }
    if (typeof (toastr) === 'undefined') {
        return;
    }
    if (typeof (parsley) === 'undefined') {
        return;
    }

    var tagManage = {
        currentItem: null,
        getQueryCondition: function (data) {
            var param = {};
            //组装排序参数
            if (data.order && data.order.length && data.order[0]) {
                switch (data.order[0].column) {
                    case 2:
                        param.orderColumn = "name";
                        break;
                    case 3:
                        param.orderColumn = "slug";
                        break;
                    case 4:
                        param.orderColumn = "intro";
                        break;
                    case 5:
                        param.orderColumn = "count";
                        break;
                    default:
                        param.orderColumn = "count";
                        break;
                }
                param.orderDir = data.order[0].dir;
            }
            //组装分页参数
            param.startIndex = data.start;
            param.pageSize = data.length;

            param.draw = data.draw;

            return param;
        },
        editItemInit: function (item) {
            if (!item) {
                return;
            }
            $('#tag_name').val(item.name);
            $('#tag_id').val(item.id);
            $('#tag_slug').val(item.slug);
            $('#tag_intro').val(item.intro);
        },
        deleteItem: function (selectedItems) {
            var message;
            if (selectedItems && selectedItems.length) {
                if (selectedItems.length === 1) {
                    message = "确定要删除 '" + selectedItems[0].name + "' 吗?";
                    $.dialog.confirmDanger(message, function () {
                        $.deleteObject("/backend/tag", selectedItems[0].id, function (data) {
                            $.toastSuccess("删除" + data.result + "项纪录成功!!!");
                            _table.draw();
                        });

                    });
                }
            } else {
                $.dialog.tips('请先选中要操作的行');
            }
        }
    };

    var $wrapper = $('#div-table-tag'),
        $table = $('#table-tag');

    var _table = $table.DataTable($.extend(true, {}, CONSTANT.DATA_TABLES.DEFAULT_OPTION, {
        ajax: $.table_ajax($wrapper, '/backend/tag', tagManage),
        columns: [
            CONSTANT.DATA_TABLES.COLUMN.CHECKBOX,
            {
                data: "id",
                visible: false
            },
            {
                className: "ellipsis",	//文字过长时用省略号显示，CSS实现
                data: "name",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                width: "100px"
            },
            {
                className: "ellipsis",	//文字过长时用省略号显示，CSS实现
                data: "slug",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                width: "100px"
            },
            {
                className: "ellipsis",
                data: "intro",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS
            },
            {
                className: "ellipsis",
                data: "count",
                render: function (data, type, row, meta) {
                    return data === 0 ? "0" : data;
                },
                width: "40px"
            },
            {
                className: "td-operation",
                data: null,
                defaultContent: "",
                orderable: false,
                width: "80px"
            }
        ],
        "createdRow": function (row, data, index) {
            //行渲染回调,在这里可以对该行dom元素进行任何操作
            //给当前行加样式
            if (data.role) {
                $(row).addClass("info");
            }
            //不使用render，改用jquery文档操作呈现单元格
            var $btnEdit = $('<span><button type="button" class="btn btn-xs btn-success btn-edit">修改</button></span>');
            var $btnDel = $('<span><button type="button" class="btn btn-xs btn-danger btn-del">删除</button></span>');
            $('td', row).eq(5).append($btnEdit).append($btnDel);
        },
        "drawCallback": $.drawCallback($wrapper, $table)
    }));

    $table.on("change", ":checkbox", function () {
        if ($(this).is("[name='cb-check-all']")) {
            //全选
            $(":checkbox", $table).prop("checked", $(this).prop("checked"));
        } else {
            //一般复选
            var checkbox = $("tbody :checkbox", $table);
            $(":checkbox[name='cb-check-all']", $table).prop('checked', checkbox.length === checkbox.filter(':checked').length);
        }
    }).on("click", ".td-checkbox", function (event) {
        //点击单元格即点击复选框
        !$(event.target).is(":checkbox") && $(":checkbox", this).trigger("click");
    }).on("click", ".btn-edit", function () {
        //点击编辑按钮
        var item = _table.row($(this).closest('tr')).data();
        tagManage.currentItem = item;
        tagManage.editItemInit(item);
    }).on("click", ".btn-del", function () {
        //点击删除按钮
        var item = _table.row($(this).closest('tr')).data();
        tagManage.deleteItem([item]);
    });

    var tags_a = $("#tags").find("span");
    tags_a.each(function () {
        var n = 0;
        var m = 6;
        var rand = parseInt(Math.random() * (m - n) + 1);
        $(this).addClass("size" + rand);
    });

    var $tag_name = $('#tag_name'),
        $tag_id = $('#tag_id'),
        $tag_slug = $('#tag_slug'),
        $tag_intro = $('#tag_intro'),
        $submitButton = $('#submitForm');

    $submitButton.on("click", function () {
        if (!$('#demo-form').validate()) {
            return false;
        }
        var jsonData = {};

        var url = "/backend/tag";
        jsonData.name = $tag_name.val();
        jsonData.slug = $tag_slug.val();
        jsonData.intro = $tag_intro.val();

        if ($tag_id.val()) {
            var id = $tag_id.val();
            jsonData.id = id;
            $.updateObject(url, id, JSON.stringify(jsonData), function (data) {
                if (data.result) {
                    $.toastSuccess("更新类别成功!!!");
                    $tag_id.val("");
                    $tag_name.val("");
                    $tag_slug.val("");
                    $tag_intro.val("");
                } else {
                    $.toastError("更新类别失败!!!");
                }
                _table.draw();
            });
        } else {
            $.addNewObject(url, JSON.stringify(jsonData), function (data) {
                if (data.result) {
                    $.toastSuccess("添加类别成功!!!");
                    $tag_id.val("");
                    $tag_name.val("");
                    $tag_slug.val("");
                    $tag_intro.val("");
                } else {
                    $.toastError("添加类别失败!!!");
                }
                _table.draw();
            });
        }
    });
}

function listPage() {
    if (typeof ($.fn.DataTable) === 'undefined') {
        return;
    }
    if (typeof (toastr) === 'undefined') {
        return;
    }
    if (typeof (CKEDITOR) === 'undefined') {
        return;
    }

    var pageManager = {
        currentItem: null,
        getQueryCondition: function (data) {
            var param = {};
            //组装排序参数
            if (data.order && data.order.length && data.order[0]) {
                switch (data.order[0].column) {
                    case 1:
                        param.orderColumn = "id";
                        break;
                    case 2:
                        param.orderColumn = "title";
                        break;
                    case 3:
                        param.orderColumn = "slug";
                        break;
                    case 4:
                        param.orderColumn = "priority";
                        break;
                    default:
                        param.orderColumn = "priority";
                        break;
                }
                param.orderDir = data.order[0].dir;
            }
            //组装分页参数
            param.startIndex = data.start;
            param.pageSize = data.length;

            param.draw = data.draw;

            return param;
        },
        editItemInit: function (item) {
            if (!item) {
                return;
            }
            $("#page_title").val(item.title);
            $("#page_id").val(item.id);
            $("#page_slug").val(item.slug);
            CKEDITOR.instances.content.setData(item.content);
            //$("#page_content").val(item.content);
            $("#page_priority").val(item.priority);
        },
        deleteItem: function (selectedItems) {
            var message;
            if (selectedItems && selectedItems.length) {
                if (selectedItems.length === 1) {
                    message = "确定要删除 '" + selectedItems[0].title + "' 吗?";
                    $.dialog.confirmDanger(message, function () {
                        $.deleteObject("/backend/page", selectedItems[0].id, function (data) {
                            $.toastSuccess("删除" + data.result + "项纪录成功!!!");
                            _table.draw();
                        });
                    });
                } else {
                    message = "确定要删除选中的" + selectedItems.length + "项记录吗?";
                    $.dialog.confirmDanger(message, function () {
                        var saveDataAry=[];
                        for (var i = 0; i < selectedItems.length; i++) {
                            saveDataAry.push(selectedItems[i].id);
                        }
                        $.deleteMultiObject("/backend/page/delete", JSON.stringify(saveDataAry), function (data) {
                            $.toastSuccess("删除" + data.result + "项纪录成功!!!");
                            _table.draw();
                        });
                    });
                }

            } else {
                $.dialog.tips('请先选中要操作的行');
            }
        }
    };

    var $wrapper = $("#div-table-page");
    var $table = $("#table-page");

    var _table = $table.DataTable($.extend(true, {}, CONSTANT.DATA_TABLES.DEFAULT_OPTION, {
        ajax: $.table_ajax($wrapper, '/backend/page', pageManager),
        columns: [
            CONSTANT.DATA_TABLES.COLUMN.CHECKBOX,
            {
                data: "id",
                visible: false
            },
            {
                className: "ellipsis",	//文字过长时用省略号显示，CSS实现
                data: "title",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS
            },
            {
                className: "ellipsis",
                data: "slug",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                width: "160px"
            },
            {
                className: "ellipsis",
                data: "priority",
                render: function (data, type, row, meta) {
                    return data === 0 ? "0" : data;
                },
                width: "80px"
            },
            {
                className: "td-operation",
                data: null,
                defaultContent: "",
                orderable: false,
                width: "120px"
            }
        ],
        "createdRow": function (row, data, index) {
            //行渲染回调,在这里可以对该行dom元素进行任何操作
            //给当前行加样式
            if (data.role) {
                $(row).addClass("info");
            }
            //不使用render，改用jquery文档操作呈现单元格
            var $btnPrev = $('<span><button type="button" class="btn btn-xs btn-info btn-preview">预览</button></span>');
            var $btnEdit = $('<span><button type="button" class="btn btn-xs btn-success btn-edit" data-toggle="modal" data-target=".bs-example-modal-lg">修改</button></span>');
            var $btnDel = $('<span><button type="button" class="btn btn-xs btn-danger btn-del">删除</button></span>');
            $('td', row).eq(4).append($btnPrev).append($btnEdit).append($btnDel);
        },
        "drawCallback": $.drawCallback($wrapper, $table)
    }));

    $table.on("change", ":checkbox", function () {
        if ($(this).is("[name='cb-check-all']")) {
            //全选
            $(":checkbox", $table).prop("checked", $(this).prop("checked"));
        } else {
            //一般复选
            var checkbox = $("tbody :checkbox", $table);
            $(":checkbox[name='cb-check-all']", $table).prop('checked', checkbox.length === checkbox.filter(':checked').length);
        }
    }).on("click", ".td-checkbox", function (event) {
        //点击单元格即点击复选框
        !$(event.target).is(":checkbox") && $(":checkbox", this).trigger("click");
    }).on("click", ".btn-preview", function () {
        //点击预览按钮
        var item = _table.row($(this).closest('tr')).data();
        window.location.href = "/web/page/" + item.slug;
    }).on("click", ".btn-edit", function () {
        //点击编辑按钮
        var item = _table.row($(this).closest('tr')).data();
        pageManager.currentItem = item;
        pageManager.editItemInit(item);
    }).on("click", ".btn-del", function () {
        //点击删除按钮
        var item = _table.row($(this).closest('tr')).data();
        pageManager.deleteItem([item]);
    });

    $('#btn-delAll').on('click', function () {
        var checkbox = $("tbody :checkbox", $table);
        var items = [];
        checkbox.filter(':checked').each(function () {
            items.push(_table.row($(this).closest('tr')).data());
        });
        if(items.length > 0) {
            pageManager.deleteItem(items);
        }
    });

    $('#page').val(_table.page.len());

    $('#page').change(function() {
        var pageLength = $('#page').val();
        _table.page.len(pageLength).draw();
    });

    var $page_title = $('#page_title'),
        $page_id = $('#page_id'),
        $page_slug = $('#page_slug'),
        $page_priority = $('#page_priority'),
        $myModal = $('.bs-example-modal-lg'),
        $submitButton = $('#submit_page');

    $myModal.on("hidden.bs.modal", function () {
        $("#page_title").val("");
        $("#page_id").val("");
        $("#page_slug").val("");
        CKEDITOR.instances.content.setData("");
        $("#page_priority").val("");
        //$(this).removeData("bs.modal");
        $(this).find('form')[0].reset();
    });

    $submitButton.on("click", function () {
        if (!$('#form-add').validate()) {
            return false;
        }

        var jsonData = {};

        var url = "/backend/page";
        jsonData.title = $page_title.val();
        jsonData.slug = $page_slug.val();
        jsonData.content = CKEDITOR.instances.content.getData();
        jsonData.priority = $page_priority.val();

        if ($page_id.val()) {
            var id = $page_id.val();
            jsonData.id = id;
            $.updateObject(url, id, JSON.stringify(jsonData), function (data) {
                if (data.result) {
                    $.toastSuccess("更新类别成功!!!");
                    $page_id.val("");
                    $page_title.val("");
                    $page_slug.val("");
                    CKEDITOR.instances.content.setData("");
                    //$page_content.val("");
                    $page_priority.val("");
                } else {
                    $.toastError("更新类别失败!!!");
                }
                $myModal.modal('hide');
                _table.draw();
            });
        } else {
            $.addNewObject(url, JSON.stringify(jsonData), function (data) {
                if (data.result) {
                    $.toastSuccess("添加类别成功!!!");
                    $page_id.val("");
                    $page_title.val("");
                    $page_slug.val("");
                    CKEDITOR.instances.content.setData("");
                    //$page_content.val("");
                    $page_priority.val("");
                } else {
                    $.toastError("添加类别失败!!!");
                }
                $myModal.modal('hide');
                _table.draw();
            });
        }
    });
}

function listUser() {
    if (typeof ($.fn.DataTable) === 'undefined') {
        return;
    }
    if (typeof (toastr) === 'undefined') {
        return;
    }
    if (typeof (parsley) === 'undefined') {
        return;
    }

    var userManage = {
        currentItem: null,
        getQueryCondition: function (data) {
            var param = {};
            //组装排序参数
            if (data.order && data.order.length && data.order[0]) {
                switch (data.order[0].column) {
                    case 1:
                        param.orderColumn = "id";
                        break;
                    case 2:
                        param.orderColumn = "name";
                        break;
                    case 3:
                        param.orderColumn = "phone";
                        break;
                    case 4:
                        param.orderColumn = "email";
                        break;
                    case 5:
                        param.orderColumn = "enabled";
                        break;
                    default:
                        param.orderColumn = "id";
                        break;
                }
                param.orderDir = data.order[0].dir;
            }
            //组装分页参数
            param.startIndex = data.start;
            param.pageSize = data.length;

            param.draw = data.draw;

            return param;
        },
        editItemInit: function (item) {
            if (!item) {
                return;
            }
            $("#user_name").val(item.name);
            $("#user_id").val(item.id);
            $("#user_phone").val(item.phone);
            $("#user_email").val(item.email);
            $("#user_enabled").val(item.enabled);
            $("#user_password").val("");
            $("#user_intro").val(item.intro);
        },
        deleteItem: function (selectedItems) {
            var message;
            if (selectedItems && selectedItems.length) {
                if (selectedItems.length === 1) {
                    message = "确定要删除 '" + selectedItems[0].name + "' 吗?";
                    $.dialog.confirmDanger(message, function () {
                        if (selectedItems[0].id === 1) {
                            $.toastError("不能删除默认类别!!!");
                        } else {
                            $.deleteObject("/backend/user", selectedItems[0].id, function (data) {
                                $.toastSuccess("删除" + data.result + "项纪录成功!!!");
                                _table.draw();
                            });
                        }
                    });
                }
            } else {
                $.dialog.tips('请先选中要操作的行');
            }
        }
    };

    var $wrapper = $("#div-table-user"),
        $table = $("#table-user");

    var _table = $table.DataTable($.extend(true, {}, CONSTANT.DATA_TABLES.DEFAULT_OPTION, {
        ajax: $.table_ajax($wrapper, '/backend/user', userManage),
        columns: [
            CONSTANT.DATA_TABLES.COLUMN.CHECKBOX,
            {
                data: "id",
                visible: false
            },
            {
                className: "ellipsis",	//文字过长时用省略号显示，CSS实现
                data: "name",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                width: "120px"
            },
            {
                className: "ellipsis",
                data: "phone",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                width: "120px"
            },
            {
                className: "ellipsis",
                data: "email",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                width: "120px"
            },
            {
                className: "ellipsis",
                data: "enabled",
                render: function (data, type, row, meta) {
                    return '<span><i class="fa fa-male"></i>' + (data ? "激活" : "未激活") + '</span>';
                },
                width: "60px"
            },
            {
                className: "ellipsis",
                data: "roles",
                render: function (data, type, row, meta) {
                    var sum = "";
                    data.forEach(function (val, index, arr) {
                        sum += val.name + " ";
                    });
                    return '<span>' + sum + '</span>';
                }
            },
            {
                className: "td-operation",
                data: null,
                defaultContent: "",
                orderable: false,
                width: "120px"
            }
        ],
        "createdRow": function (row, data, index) {
            //行渲染回调,在这里可以对该行dom元素进行任何操作
            //给当前行加样式
            // if (data.role) {
            //     $(row).addClass("info");
            // }
            $('td', row).eq(4).addClass(data.enabled ? "text-success" : "text-danger");
            //不使用render，改用jquery文档操作呈现单元格
            var $btnSetting = $('<span><button type="button" class="btn btn-xs btn-info btn-preview">设置</button></span>');
            var $btnEdit = $('<span><button type="button" class="btn btn-xs btn-success btn-edit" data-toggle="modal" data-target=".bs-example-modal-md">修改</button></span>');
            var $btnDel = $('<span><button type="button" class="btn btn-xs btn-danger btn-del">删除</button></span>');
            $('td', row).eq(6).append($btnSetting).append($btnEdit).append($btnDel);
        },
        "drawCallback": $.drawCallback($wrapper, $table)
    }));

    $table.on("change", ":checkbox", function () {
        if ($(this).is("[name='cb-check-all']")) {
            //全选
            $(":checkbox", $table).prop("checked", $(this).prop("checked"));
        } else {
            //一般复选
            var checkbox = $("tbody :checkbox", $table);
            $(":checkbox[name='cb-check-all']", $table).prop('checked', checkbox.length === checkbox.filter(':checked').length);
        }
    }).on("click", ".td-checkbox", function (event) {
        //点击单元格即点击复选框
        !$(event.target).is(":checkbox") && $(":checkbox", this).trigger("click");
    }).on("click", ".btn-edit", function () {
        //点击编辑按钮
        var item = _table.row($(this).closest('tr')).data();
        userManage.currentItem = item;
        userManage.editItemInit(item);
    }).on("click", ".btn-del", function () {
        //点击删除按钮
        var item = _table.row($(this).closest('tr')).data();
        userManage.deleteItem([item]);
    });

    var $user_name = $('#user_name'),
        $user_password = $('#user_password'),
        $user_id = $('#user_id'),
        $user_phone = $('#user_phone'),
        $user_email = $('#user_email'),
        $user_enabled = $('#user_enabled'),
        $myModal = $('.bs-example-modal-md'),
        $submitButton = $('#submit_user');

    $myModal.on("hidden.bs.modal", function () {
        $user_id.val("");
        $user_name.val("");
        $user_password.val("");
        $user_phone.val("");
        $user_email.val("");
        $user_enabled.val("");
        $(this).find('form')[0].reset();
    });

    $submitButton.on("click", function () {
        if (!$('#form-add').validate()) {
            return false;
        }

        var jsonData = {};

        var url = "/backend/user";
        jsonData.name = $user_name.val();
        jsonData.password = $user_password.val();
        jsonData.enabled = $user_enabled.val();
        jsonData.phone = $user_phone.val();
        jsonData.email = $user_email.val();

        if ($user_id.val()) {
            var id = $user_id.val();
            jsonData.id = id;
            $.updateObject(url, id, JSON.stringify(jsonData), function (data) {
                if (data.result) {
                    $.toastSuccess("更新类别成功!!!");
                    $user_id.val("");
                    $user_name.val("");
                    $user_password.val("");
                    $user_phone.val("");
                    $user_email.val("");
                } else {
                    $.toastError("更新类别失败!!!");
                }
                $myModal.modal('hide');
                _table.draw();
            });
        } else {
            $.addNewObject(url, JSON.stringify(jsonData), function (data) {
                if (data.result) {
                    $.toastSuccess("添加类别成功!!!");
                    $user_id.val("");
                    $user_name.val("");
                    $user_password.val("");
                    $user_phone.val("");
                    $user_email.val("");
                } else {
                    $.toastError("添加类别失败!!!");
                }
                $myModal.modal('hide');
                _table.draw();
            });
        }
    });
}

function initGallery() {
    if (typeof ($.fn.fileinput) === 'undefined') {
        return;
    }

    if (typeof ($.fn.DataTable) === 'undefined') {
        return;
    }
    if (typeof (toastr) === 'undefined') {
        return;
    }
    if (typeof (parsley) === 'undefined') {
        return;
    }

    var albumManager = {
        currentItem: null,
        getQueryCondition: function (data) {
            var param = {};
            //组装排序参数
            if (data.order && data.order.length && data.order[0]) {
                switch (data.order[0].column) {
                    case 1:
                        param.orderColumn = "id";
                        break;
                    case 2:
                        param.orderColumn = "title";
                        break;
                    case 3:
                        param.orderColumn = "description";
                        break;
                    case 4:
                        param.orderColumn = "coverpic";
                        break;
                    default:
                        param.orderColumn = "id";
                        break;
                }
                param.orderDir = data.order[0].dir;
            }
            //组装分页参数
            param.startIndex = data.start;
            param.pageSize = data.length;

            param.draw = data.draw;

            return param;
        },
        editItemInit: function (item) {
            if (!item) {
                return;
            }
            $("#album_title").val(item.title);
            $("#album_id").val(item.id);
            $("#album_description").val(item.description);
            $("#album_coverpic").val(item.coverpic);
        },
        deleteItem: function (selectedItems) {
            var message;
            if (selectedItems && selectedItems.length) {
                if (selectedItems.length === 1) {
                    message = "确定要删除 '" + selectedItems[0].title + "' 吗?";
                    $.dialog.confirmDanger(message, function () {
                        if (selectedItems[0].id === 1) {
                            $.toastError("不能删除默认类别!!!");
                        } else {
                            $.deleteObject("/backend/album", selectedItems[0].id, function (data) {
                                $.toastSuccess("删除" + data.result + "项纪录成功!!!");
                                _table.draw();
                            });
                        }
                    });
                } else {
                    message = "确定要删除选中的" + selectedItems.length + "项记录吗?";
                    $.dialog.confirmDanger(message, function () {
                        var saveDataAry=[];
                        for (var i = 0; i < selectedItems.length; i++) {
                            if (selectedItems[i].id === 1) {
                                $.toastError("不能删除默认类别!!!");
                            } else {
                                saveDataAry.push(selectedItems[i].id);
                            }
                        }
                        $.deleteMultiObject("/backend/album/delete", JSON.stringify(saveDataAry), function (data) {
                            $.toastSuccess("删除" + data.result + "项纪录成功!!!");
                            _table.draw();
                        });
                    });
                }
            } else {
                $.dialog.tips('请先选中要操作的行');
            }
        }
    };

    var $wrapper = $("#div-table-album"),
        $table = $("#table-album");

    var _table = $table.DataTable($.extend(true, {}, CONSTANT.DATA_TABLES.DEFAULT_OPTION, {
        ajax: $.table_ajax($wrapper, '/backend/album', albumManager),
        columns: [
            CONSTANT.DATA_TABLES.COLUMN.CHECKBOX,
            {
                data: "id",
                visible: false
            },
            {
                className: "ellipsis",	//文字过长时用省略号显示，CSS实现
                data: "title",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                width: "120px"
            },
            {
                className: "ellipsis",
                data: "description",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS
            },
            {
                className: "ellipsis",
                data: "coverpic",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                width: "120px"
            },
            {
                className: "td-operation",
                data: null,
                defaultContent: "",
                orderable: false,
                width: "120px"
            }
        ],
        "createdRow": function (row, data, index) {
            $('td', row).eq(4).addClass(data.enabled ? "text-success" : "text-danger");
            //不使用render，改用jquery文档操作呈现单元格
            var $btnGallery = $('<span><button type="button" class="btn btn-xs btn-info btn-gallery" data-toggle="modal" data-target=".bs-example1-modal-lg">图片</button></span>');
            var $btnEdit = $('<span><button type="button" class="btn btn-xs btn-success btn-edit" data-toggle="modal" data-target=".bs-example-modal-md">修改</button></span>');
            var $btnDel = $('<span><button type="button" class="btn btn-xs btn-danger btn-del">删除</button></span>');
            $('td', row).eq(4).append($btnGallery).append($btnEdit).append($btnDel);
        },
        "drawCallback": $.drawCallback($wrapper, $table)
    }));

    var initFileInput = function () {
        var btnCust = '<button type="button" class="btn btn-default" title="Add picture tags" id="btnCust"><i class="glyphicon glyphicon-tag"></i></button>';
        $("#avatar").fileinput({
            overwriteInitial: true,
            maxFileCount: 1,
            showClose: false,
            showCaption: false,
            showBrowse: false,
            browseOnZoneClick: true,
            uploadAsync: true,
            removeLabel: '',
            removeIcon: '<i class="glyphicon glyphicon-remove"></i>',
            removeTitle: 'Cancel or reset changes',
            elErrorContainer: '#kv-avatar-errors-1',
            msgErrorClass: 'alert alert-block alert-danger',
            defaultPreviewContent: '<img src="/static/admin/images/img.png" alt="Your Avatar" style="width:160px">',
            layoutTemplates: {main2: '{preview} ' + ' {browse}'},
            allowedFileExtensions: ["jpg", "png", "gif"]
        }).on('filebatchpreupload', function (event, data, jqXHR) {
        }).on('filepreupload', function (event, data, previewId, index, jqXHR) {
        }).on('fileuploaded', function (e, params) {
            console.log('File uploaded params', params);
        });
    };

    $table.on("change", ":checkbox", function () {
        if ($(this).is("[name='cb-check-all']")) {
            //全选
            $(":checkbox", $table).prop("checked", $(this).prop("checked"));
        } else {
            //一般复选
            var checkbox = $("tbody :checkbox", $table);
            $(":checkbox[name='cb-check-all']", $table).prop('checked', checkbox.length === checkbox.filter(':checked').length);
        }
    }).on("click", ".td-checkbox", function (event) {
        //点击单元格即点击复选框
        !$(event.target).is(":checkbox") && $(":checkbox", this).trigger("click");
    }).on("click", ".btn-gallery", function () {
        //点击预览按钮
        var item = _table.row($(this).closest('tr')).data();
        var $myModal1 = $('.bs-example1-modal-lg');
        $myModal1.on('hidden.bs.modal', function () {
            $("#file-0a").fileinput('destroy');
        });
        $.loadObject('/backend/gallery', item.id, function (data) {
            $("#file-0a").fileinput({
                theme: "explorer",
                language: 'zh',
                uploadUrl: "uploadMultipleFile",
                uploadAsync: true,
                browseOnZoneClick: true,
                overwriteInitial: false,
                uploadExtraData: {album: item.id},
                initialPreview: data.initialPreview,
                initialPreviewAsData: true, // defaults markup
                initialPreviewFileType: 'image', // image is the default and can be overridden in config below
                initialPreviewConfig: data.initialPreviewConfig
            }).on('filebatchpreupload', function (event, data, jqXHR) {
                //data.form = {"videoname": "your_video_name_here"};
            }).on('filepreupload', function (event, data, previewId, index, jqXHR) {
                //data.form.append("album", item.id);
            }).on('fileuploaded', function (e, params) {
                console.log('File uploaded params', params);
            });
        });
    }).on("click", ".btn-edit", function () {
        //点击编辑按钮
        var item = _table.row($(this).closest('tr')).data();
        albumManager.currentItem = item;
        albumManager.editItemInit(item);
        initFileInput();
    }).on("click", ".btn-del", function () {
        //点击删除按钮
        var item = _table.row($(this).closest('tr')).data();
        albumManager.deleteItem([item]);
    });

    $('#btn-add').on('click', function () {
        initFileInput();
    });

    $('#btn-delAll').on('click', function () {
        var checkbox = $("tbody :checkbox", $table);
        var items = [];
        checkbox.filter(':checked').each(function () {
            items.push(_table.row($(this).closest('tr')).data());
        });
        if(items.length > 0) {
            albumManager.deleteItem(items);
        }
    });

    $('#pagealbum').val(_table.page.len());

    $('#pagealbum').change(function() {
        var pageLength = $('#pagealbum').val();
        _table.page.len(pageLength).draw();
    });

    var $album_title = $('#album_title'),
        $album_description = $('#album_description'),
        $album_id = $('#album_id'),
        $album_coverpic = $('#album_coverpic'),
        $myModal = $('.bs-example-modal-md'),
        $submitButton = $('#submit_album');

    $myModal.on("hidden.bs.modal", function () {
        $album_id.val("");
        $album_title.val("");
        $album_description.val("");
        $album_coverpic.val("");
        $(this).find('form')[0].reset();
        $("#avatar").fileinput('destroy');
    });

    $submitButton.on("click", function () {
        if (!$('#form-add').validate()) {
            return false;
        }

        var jsonData = {};

        var url = "/backend/album";
        jsonData.title = $album_title.val();
        jsonData.description = $album_description.val();
        jsonData.coverpic = "/uploads/xxx.gif";
        if ($album_id.val()) {
            var id = $album_id.val();
            jsonData.id = id;
            $.updateObject(url, id, JSON.stringify(jsonData), function (data) {
                if (data.result) {
                    $.toastSuccess("更新类别成功!!!");
                    $album_id.val("");
                    $album_title.val("");
                    $album_description.val("");
                } else {
                    $.toastError("更新类别失败!!!");
                }
                $myModal.modal('hide');
                _table.draw();
            });
        } else {
            $.addNewObject(url, JSON.stringify(jsonData), function (data) {
                if (data.result) {
                    $.toastSuccess("添加类别成功!!!");
                    $album_id.val("");
                    $album_title.val("");
                    $album_description.val("");
                } else {
                    $.toastError("添加类别失败!!!");
                }
                $myModal.modal('hide');
                _table.draw();
            });
        }
    });
}

function initNovel() {
    if (typeof ($.fn.fileinput) === 'undefined') {
        return;
    }

    if (typeof ($.fn.DataTable) === 'undefined') {
        return;
    }
    if (typeof (toastr) === 'undefined') {
        return;
    }
    if (typeof (parsley) === 'undefined') {
        return;
    }

    var novelManage = {
        currentItem: null,
        getQueryCondition: function (data) {
            var param = {};
            //组装排序参数
            if (data.order && data.order.length && data.order[0]) {
                switch (data.order[0].column) {
                    case 1:
                        param.orderColumn = "id";
                        break;
                    case 2:
                        param.orderColumn = "name";
                        break;
                    case 3:
                        param.orderColumn = "author";
                        break;
                    case 4:
                        param.orderColumn = "description";
                        break;
                    case 5:
                        param.orderColumn = "type";
                        break;
                    default:
                        param.orderColumn = "id";
                        break;
                }
                param.orderDir = data.order[0].dir;
            }
            //组装分页参数
            param.startIndex = data.start;
            param.pageSize = data.length;
            param.condition = $('#keyword').val();
            param.draw = data.draw;

            return param;
        },
        editItemInit: function (item) {
            if (!item) {
                return;
            }
            $("#avatar_url").val(item.cover);
            $("#novel_name").val(item.name);
            $("#novel_id").val(item.id);
            $("#novel_author").val(item.author);
            $("#novel_description").val(item.description);
            $("#novel_type").val(item.type);

            $("#novel_cover").val(item.cover);
        },
        deleteItem: function (selectedItems) {
            var message;
            if (selectedItems && selectedItems.length) {
                if (selectedItems.length === 1) {
                    message = "确定要删除 '" + selectedItems[0].name + "' 吗?";
                    $.dialog.confirmDanger(message, function () {
                        $.deleteObject("/backend/novel", selectedItems[0].id, function (data) {
                            $.toastSuccess("删除" + data.result + "项纪录成功!!!");
                            _table.draw();
                        });
                    });
                } else {
                    message = "确定要删除选中的" + selectedItems.length + "项记录吗?";
                    $.dialog.confirmDanger(message, function () {
                        var saveDataAry=[];
                        for (var i = 0; i < selectedItems.length; i++) {
                            saveDataAry.push(selectedItems[i].id);
                        }
                        $.deleteMultiObject("/backend/novel/delete", JSON.stringify(saveDataAry), function (data) {
                            $.toastSuccess("删除" + data.result + "项纪录成功!!!");
                            _table.draw();
                        });
                    });
                }

            } else {
                $.dialog.tips('请先选中要操作的行');
            }
        }
    };

    var $wrapper = $("#div-table-novel"),
        $table = $("#table-novel");

    var _table = $table.DataTable($.extend(true, {}, CONSTANT.DATA_TABLES.DEFAULT_OPTION, {
        ajax: $.table_ajax($wrapper, '/backend/novel', novelManage),
        columns: [
            CONSTANT.DATA_TABLES.COLUMN.CHECKBOX,
            {
                data: "id",
                visible: false
            },
            {
                className: "ellipsis",	//文字过长时用省略号显示，CSS实现
                data: "name",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                width: "100px"
            },
            {
                className: "ellipsis",	//文字过长时用省略号显示，CSS实现
                data: "author",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                width: "100px"
            },
            {
                className: "ellipsis",	//文字过长时用省略号显示，CSS实现
                data: "type",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                width: "100px"
            },
            {
                className: "ellipsis",
                data: "description",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS
            },
            {
                className: "td-operation",
                data: null,
                defaultContent: "",
                orderable: false,
                width: "80px"
            }
        ],
        "createdRow": function (row, data, index) {
            //不使用render，改用jquery文档操作呈现单元格
            var $btnEdit = $('<span><button type="button" class="btn btn-xs btn-success btn-edit" data-toggle="modal" data-target=".bs-example-modal-md">修改</button></span>');
            var $btnDel = $('<span><button type="button" class="btn btn-xs btn-danger btn-del">删除</button></span>');
            $('td', row).eq(5).append($btnEdit).append($btnDel);
        },
        "drawCallback": $.drawCallback($wrapper, $table)
    }));

    var initFileInput = function (imagefile) {
        var btnCust = '<button type="button" class="btn btn-default" title="Add picture tags" id="btnCust"><i class="glyphicon glyphicon-tag"></i></button>';
        $("#avatar").fileinput({
            overwriteInitial: true,
            maxFileCount: 1,
            showClose: false,
            showCaption: false,
            showBrowse: false,
            browseOnZoneClick: true,
            uploadAsync: true,
            removeLabel: '',
            removeIcon: '<i class="glyphicon glyphicon-remove"></i>',
            removeTitle: 'Cancel or reset changes',
            elErrorContainer: '#kv-avatar-errors-1',
            msgErrorClass: 'alert alert-block alert-danger',
            defaultPreviewContent: '<img src="' + imagefile+ '" alt="Your Avatar" style="width:160px">',
            layoutTemplates: {main2: '{preview} {remove} {browse}'},
            allowedFileExtensions: ["jpg", "png", "gif", "jpeg"]
        }).on('filebatchpreupload', function (event, data, jqXHR) {
        }).on('filepreupload', function (event, data, previewId, index, jqXHR) {
        }).on('fileuploaded', function (e, params) {
            console.log('File uploaded params', params);
        });
    };

    $table.on("change", ":checkbox", function () {
        if ($(this).is("[name='cb-check-all']")) {
            //全选
            $(":checkbox", $table).prop("checked", $(this).prop("checked"));
        } else {
            //一般复选
            var checkbox = $("tbody :checkbox", $table);
            $(":checkbox[name='cb-check-all']", $table).prop('checked', checkbox.length === checkbox.filter(':checked').length);
        }
    }).on("click", ".td-checkbox", function (event) {
        //点击单元格即点击复选框
        !$(event.target).is(":checkbox") && $(":checkbox", this).trigger("click");
    }).on("click", ".btn-edit", function () {
        //点击编辑按钮
        var item = _table.row($(this).closest('tr')).data();
        novelManage.currentItem = item;
        novelManage.editItemInit(item);
        initFileInput(item.cover);
    }).on("click", ".btn-del", function () {
        //点击删除按钮
        var item = _table.row($(this).closest('tr')).data();
        novelManage.deleteItem([item]);
    });

    $("#btn-query").on("click", function () {
        _table.draw();//查询后不需要保持分页状态，回首页
    });

    $('#btn-add').on('click', function () {
        initFileInput();
    });

    $('#btn-delAll').on('click', function () {
        var checkbox = $("tbody :checkbox", $table);
        var items = [];
        checkbox.filter(':checked').each(function () {
            items.push(_table.row($(this).closest('tr')).data());
        });
        if(items.length > 0) {
            novelManage.deleteItem(items);
        }
    });

    var $novel_name = $('#novel_name'),
        $novel_author = $('#novel_author'),
        $novel_description = $('#novel_description'),
        $novel_id = $('#novel_id'),
        $novel_type = $('#novel_type'),
        $novel_cover = $('#novel_cover'),
        $myModal = $('.bs-example-modal-md'),
        $submitButton = $('#submit_novel');

    $myModal.on("hidden.bs.modal", function () {
        $novel_id.val("");
        $novel_name.val("");
        $novel_author.val("");
        $novel_description.val("");
        $novel_cover.val("");
        $(this).find('form')[0].reset();
        $("#avatar").fileinput('destroy');
    });

    $submitButton.on("click", function () {
        if (!$('#form_add').validate()) {
            return false;
        }

        var url = "/backend/novel";
        var formData = new FormData();
        formData.append('cover', $('#avatar_url').val());
        formData.append('avatar', $('#avatar')[0].files[0]);
        formData.append('name', $novel_name.val());
        formData.append('author', $novel_author.val());
        formData.append('description', $novel_description.val());
        formData.append('type', $novel_type.val());
        if ($novel_id.val()) {
            var id = $novel_id.val();
            formData.append('id', id);
            $.updateFormObject(url, id, formData, function (data) {
                if (data.result) {
                    $.toastSuccess("更新类别成功!!!");
                    $novel_id.val("");
                    $novel_name.val("");
                    $novel_author.val("");
                    $novel_description.val("");
                } else {
                    $.toastError("更新类别失败!!!");
                }
                $myModal.modal('hide');
                _table.draw();
            });
        } else {
            $.addFormObject(url, formData, function (data) {
                if (data.result) {
                    $.toastSuccess("添加类别成功!!!");
                    $novel_id.val("");
                    $novel_name.val("");
                    $novel_author.val("");
                    $novel_description.val("");
                } else {
                    $.toastError("添加类别失败!!!");
                }
                $myModal.modal('hide');
                _table.draw();
            });
        }
    });
}

function initNovelChapter() {
    if (typeof ($.fn.fileinput) === 'undefined') {
        return;
    }
    if (typeof ($.fn.DataTable) === 'undefined') {
        return;
    }
    if (typeof (toastr) === 'undefined') {
        return;
    }
    if (typeof (parsley) === 'undefined') {
        return;
    }

    var novelManage = {
        currentItem: null,
        getQueryCondition: function (data, condition) {
            var param = {};
            //组装排序参数
            if (data.order && data.order.length && data.order[0]) {
                switch (data.order[0].column) {
                    case 1:
                        param.orderColumn = "id";
                        break;
                    case 2:
                        param.orderColumn = "wid";
                        break;
                    case 3:
                        param.orderColumn = "title";
                        break;
                    case 4:
                        param.orderColumn = "novel";
                        break;
                    default:
                        param.orderColumn = "id";
                        break;
                }
                param.orderDir = data.order[0].dir;
            }
            //组装分页参数
            param.startIndex = data.start;
            param.pageSize = data.length;
            param.condition = $('#keyword').val();
            param.draw = data.draw;

            return param;
        },
        deleteItem: function (selectedItems) {
            var message;
            if (selectedItems && selectedItems.length) {
                if (selectedItems.length === 1) {
                    message = "确定要删除 '" + selectedItems[0].title + "' 吗?";
                    $.dialog.confirmDanger(message, function () {
                        $.deleteObject("/backend/novelchapter", selectedItems[0].id, function (data) {
                            $.toastSuccess("删除" + data.result + "项纪录成功!!!");
                            _table.draw();
                        });

                    });
                } else {
                    message = "确定要删除选中的" + selectedItems.length + "项记录吗?";
                    $.dialog.confirmDanger(message, function () {
                        var saveDataAry=[];
                        for (var i = 0; i < selectedItems.length; i++) {
                            saveDataAry.push(selectedItems[i].id);
                        }
                        $.deleteMultiObject("/backend/novelchapter/delete", JSON.stringify(saveDataAry), function (data) {
                            $.toastSuccess("删除" + data.result + "项纪录成功!!!");
                            _table.draw();
                        });
                    });
                }
            } else {
                $.dialog.tips('请先选中要操作的行');
            }
        }
    };

    var $wrapper = $("#div-table-novel-chapter"),
        $table = $('#table-novel-chapter');

    var _table = $table.DataTable($.extend(true, {}, CONSTANT.DATA_TABLES.DEFAULT_OPTION, {
        ajax: $.table_ajax($wrapper, '/backend/novelchapter', novelManage),
        columns: [
            CONSTANT.DATA_TABLES.COLUMN.CHECKBOX,
            {
                data: "id",
                visible: false
            },
            {
                className: "ellipsis",	//文字过长时用省略号显示，CSS实现
                data: "wid",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                width: "100px"
            },
            {
                className: "ellipsis",	//文字过长时用省略号显示，CSS实现
                data: "title",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
            },
            {
                className: "ellipsis",
                data: "novel.name",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                width: "100px"
            },
            {
                className: "td-operation",
                data: null,
                defaultContent: "",
                orderable: false,
                width: "80px"
            }
        ],
        "createdRow": function (row, data, index) {
            //不使用render，改用jquery文档操作呈现单元格
            var $btnPrev = $('<span><button type="button" class="btn btn-xs btn-info btn-preview">预览</button></span>');
            var $btnDel = $('<span><button type="button" class="btn btn-xs btn-danger btn-del">删除</button></span>');
            $('td', row).eq(4).append($btnPrev).append($btnDel);
        },
        "drawCallback": $.drawCallback($wrapper, $table)
    }));

    $table.on("change", ":checkbox", function () {
        if ($(this).is("[name='cb-check-all']")) {
            //全选
            $(":checkbox", $table).prop("checked", $(this).prop("checked"));
        } else {
            //一般复选
            var checkbox = $("tbody :checkbox", $table);
            $(":checkbox[name='cb-check-all']", $table).prop('checked', checkbox.length === checkbox.filter(':checked').length);
        }
    }).on("click", ".td-checkbox", function (event) {
        //点击单元格即点击复选框
        !$(event.target).is(":checkbox") && $(":checkbox", this).trigger("click");
    }).on("click", ".btn-preview", function () {
        //点击预览按钮
        var item = _table.row($(this).closest('tr')).data();
        window.location.href = "/web/novel/" + item.novel.id + "/" + item.wid + ".html";
    }).on("click", ".btn-del", function () {
        //点击删除按钮
        var item = _table.row($(this).closest('tr')).data();
        novelManage.deleteItem([item]);
    });

    $("#btn-query").on("click", function () {
        _table.draw();//查询后不需要保持分页状态，回首页
    });

    $('#btn-add').on('click', function () {
        initFileInput();
    });

    $('#btn-delAll').on('click', function () {
        var checkbox = $("tbody :checkbox", $table);
        var items = [];
        checkbox.filter(':checked').each(function () {
            items.push(_table.row($(this).closest('tr')).data());
        });
        if(items.length > 0) {
            novelManage.deleteItem(items);
        }
    });

    $('#page').val(_table.page.len());

    $('#page').change(function() {
        var pageLength = $('#page').val();
        _table.page.len(pageLength).draw();
    });

    var $novel_name = $('#novel_name'),
        $novel_location = $('#novel_filename'),
        $myModal = $('.bs-example-modal-md'),
        $submitButton = $('#submit_novel_chapter');

    var initFileInput = function (imagefile) {
        $("#avatar1").fileinput({
            language: 'zh',
            maxFileSize: 0,
            overwriteInitial: true,
            showCaption: false,
            allowedPreviewTypes: null,
            browseOnZoneClick: true,
            maxFileCount: 1,
            uploadUrl: "uploadCommonFile",
            previewFileIcon: '<i class="fa fa-file"></i>',
            allowedFileExtensions: ["txt"],
            uploadAsync: true,
            previewFileExtSettings: {
                'txt': function(ext) {
                    return ext.match(/(txt|ini)$/i);
                }
            }
        }).on('filebatchpreupload', function (event, data, jqXHR) {
            //data.form = {"videoname": "your_video_name_here"};
        }).on('filepreupload', function (event, data, previewId, index, jqXHR) {
            //data.form.append("album", item.id);
        }).on('fileuploaded', function (e, params) {
            $novel_location.val(params.response.result);
        });
    }

    $submitButton.on("click", function () {
        if (!$('#form_add').validate()) {
            return false;
        }
        var jsonData = {};
        var url = "/backend/novelchapter";
        jsonData.id = $novel_name.val();
        jsonData.location = $novel_location.val();

        $.addNewObject(url, JSON.stringify(jsonData), function (data) {
            if (data.result > 0) {
                $.toastSuccess("添加类别成功!!!");
                $novel_name.val("");
                $novel_location.val("");
            } else {
                $.toastError("添加类别失败!!!");
            }
            $myModal.modal('hide');
            _table.draw();
        });
    });
}

function listSettings() {
    if (typeof ($.fn.editable) === 'undefined') {
        return;
    }
    if (typeof (moment) === 'undefined') {
        return;
    }
    $.fn.editable.defaults.mode = 'inline';
    //defaults
    $.fn.editable.defaults.url = '/post';

    //enable / disable
    $('#enable').click(function () {
        $('#user .editable').editable('toggleDisabled');
    });

    //editables
    $('#username').editable({
        url: '/post',
        type: 'text',
        pk: 1,
        name: 'username',
        title: 'Enter username'
    });

    $('#firstname').editable({
        validate: function (value) {
            if ($.trim(value) == '') return 'This field is required';
        }
    });

    $('#sex').editable({
        prepend: "not selected",
        source: [
            {value: 1, text: 'Male'},
            {value: 2, text: 'Female'}
        ],
        display: function (value, sourceData) {
            var colors = {"": "gray", 1: "green", 2: "blue"},
                elem = $.grep(sourceData, function (o) {
                    return o.value == value;
                });

            if (elem.length) {
                $(this).text(elem[0].text).css("color", colors[value]);
            } else {
                $(this).empty();
            }
        }
    });

    $('#status').editable();

    $('#group').editable({
        showbuttons: false
    });

    $('#vacation').editable({
        datepicker: {
            todayBtn: 'linked'
        }
    });

    $('#dob').editable();

    $('#event').editable({
        placement: 'right',
        combodate: {
            firstItem: 'name'
        }
    });

    $('#meeting_start').editable({
        format: 'yyyy-mm-dd hh:ii',
        viewformat: 'dd/mm/yyyy hh:ii',
        validate: function (v) {
            if (v && v.getDate() == 10) return 'Day cant be 10!';
        },
        datetimepicker: {
            todayBtn: 'linked',
            weekStart: 1
        }
    });

    $('#comments').editable({
        showbuttons: 'bottom'
    });

    $('#note').editable();
    $('#pencil').click(function (e) {
        e.stopPropagation();
        e.preventDefault();
        $('#note').editable('toggle');
    });
}

/**
 * Resize function without multiple trigger
 *
 * Usage:
 * $(window).smartresize(function(){
 *     // code here
 * });
 */
(function ($, sr) {
    // debouncing function from John Hann
    // http://unscriptable.com/index.php/2009/03/20/debouncing-javascript-methods/
    var debounce = function (func, threshold, execAsap) {
        var timeout;

        return function debounced() {
            var obj = this, args = arguments;

            function delayed() {
                if (!execAsap)
                    func.apply(obj, args);
                timeout = null;
            }

            if (timeout)
                clearTimeout(timeout);
            else if (execAsap)
                func.apply(obj, args);

            timeout = setTimeout(delayed, threshold || 100);
        };
    };

    // smartresize
    jQuery.fn[sr] = function (fn) {
        return fn ? this.bind('resize', debounce(fn)) : this.trigger(sr);
    };

})(jQuery, 'smartresize');

var CURRENT_URL = window.location.href.split('#')[0].split('?')[0],
    $BODY = $('body'),
    $MENU_TOGGLE = $('#menu_toggle'),
    $SIDEBAR_MENU = $('#sidebar-menu'),
    $SIDEBAR_FOOTER = $('.sidebar-footer'),
    $LEFT_COL = $('.left_col'),
    $RIGHT_COL = $('.right_col'),
    $NAV_MENU = $('.nav_menu'),
    $FOOTER = $('footer');

// Sidebar
function init_sidebar() {
// TODO: This is some kind of easy fix, maybe we can improve this
    var setContentHeight = function () {
        // reset height
        $RIGHT_COL.css('min-height', $(window).height());

        var bodyHeight = $BODY.outerHeight(),
            footerHeight = $BODY.hasClass('footer_fixed') ? -10 : $FOOTER.height(),
            leftColHeight = $LEFT_COL.eq(1).height() + $SIDEBAR_FOOTER.height(),
            contentHeight = bodyHeight < leftColHeight ? leftColHeight : bodyHeight;

        // normalize content
        contentHeight -= $NAV_MENU.height() + footerHeight;

        $RIGHT_COL.css('min-height', contentHeight);
    };

    $SIDEBAR_MENU.find('a').on('click', function (ev) {
        console.log('clicked - sidebar_menu');
        var $li = $(this).parent();

        if ($li.is('.active')) {
            $li.removeClass('active active-sm');
            $('ul:first', $li).slideUp(function () {
                setContentHeight();
            });
        } else {
            // prevent closing menu if we are on child menu
            if (!$li.parent().is('.child_menu')) {
                $SIDEBAR_MENU.find('li').removeClass('active active-sm');
                $SIDEBAR_MENU.find('li ul').slideUp();
            } else {
                if ($BODY.is(".nav-sm")) {
                    $SIDEBAR_MENU.find("li").removeClass("active active-sm");
                    $SIDEBAR_MENU.find("li ul").slideUp();
                }
            }
            $li.addClass('active');

            $('ul:first', $li).slideDown(function () {
                setContentHeight();
            });
        }
    });

    // toggle small or large menu
    $MENU_TOGGLE.on('click', function () {
        console.log('clicked - menu toggle');

        if ($BODY.hasClass('nav-md')) {
            $SIDEBAR_MENU.find('li.active ul').hide();
            $SIDEBAR_MENU.find('li.active').addClass('active-sm').removeClass('active');
        } else {
            $SIDEBAR_MENU.find('li.active-sm ul').show();
            $SIDEBAR_MENU.find('li.active-sm').addClass('active').removeClass('active-sm');
        }

        $BODY.toggleClass('nav-md nav-sm');

        setContentHeight();
    });

    // check active menu
    $SIDEBAR_MENU.find('a[href="' + CURRENT_URL + '"]').parent('li').addClass('current-page');

    $SIDEBAR_MENU.find('a').filter(function () {
        return this.href == CURRENT_URL;
    }).parent('li').addClass('current-page').parents('ul').slideDown(function () {
        setContentHeight();
    }).parent().addClass('active');

    // recompute content when resizing
    $(window).smartresize(function () {
        setContentHeight();
    });

    setContentHeight();

    // fixed sidebar
    if ($.fn.mCustomScrollbar) {
        $('.menu_fixed').mCustomScrollbar({
            autoHideScrollbar: true,
            theme: 'minimal',
            mouseWheel: {preventDefault: true}
        });
    }
}
// /Sidebar

$(document).ready(function () {
    initArticle();
    modifyArticle();
    initCategory();
    initTag();
    initGallery();
    initNovel();
    initNovelChapter();
    listPage();
    listUser();
    listSettings();
    init_sidebar();
});

