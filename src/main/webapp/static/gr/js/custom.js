/**
 * Created by d05660ddw on 2017/3/8.
 */

jQuery.fn.pagination = function (maxentries, opts) {
    opts = jQuery.extend({
        items_per_page: 10,
        num_display_entries: 10,
        current_page: 0,
        num_edge_entries: 0,
        link_to: "#",
        prev_text: "Prev",
        next_text: "Next",
        first_text: "First",
        last_text: "Last",
        ellipse_text: "...",
        prev_show_always: true,
        next_show_always: true,
        first_show_always: true,
        last_show_always: true,
        callback: function () {
            return false;
        }
    }, opts || {});

    return this.each(function () {
        /**
         * Calculate the maximum number of pages
         */
        function numPages() {
            return Math.ceil(maxentries / opts.items_per_page);
        }

        /**
         * Calculate start and end point of pagination links depending on
         * current_page and num_display_entries.
         * @return {Array}
         */
        function getInterval() {
            var ne_half = Math.ceil(opts.num_display_entries / 2);
            var np = numPages();
            var upper_limit = np - opts.num_display_entries;
            var start = current_page > ne_half ? Math.max(Math.min(current_page - ne_half, upper_limit), 0) : 0;
            var end = current_page > ne_half ? Math.min(current_page + ne_half, np) : Math.min(opts.num_display_entries, np);
            return [start, end];
        }

        /**
         * This is the event handling function for the pagination links.
         * @param {int} page_id The new page number
         */
        function pageSelected(page_id, evt) {
            current_page = page_id;
            drawLinks();
            var continuePropagation = opts.callback(page_id, panel);
            if (!continuePropagation) {
                if (typeof(evt) !== "undefined") {
                    if (typeof evt.stopPropagation === "function") {
                        evt.stopPropagation();
                    } else {
                        evt.cancelBubble = true;
                    }
                }
            }
            return continuePropagation;
        }

        /**
         * This function inserts the pagination links into the container element
         */
        function drawLinks() {
            panel.empty();
            var interval = getInterval();
            var np = numPages();
            // This helper function returns a handler function that calls pageSelected with the right page_id
            var getClickHandler = function (page_id) {
                return function (evt) {
                    return pageSelected(page_id, evt);
                }
            };
            // Helper function for generating a single link (or a span tag if it's the current page)
            var appendItem = function (page_id, appendopts) {
                page_id = page_id < 0 ? 0 : (page_id < np ? page_id : np - 1); // Normalize page id to sane value
                appendopts = jQuery.extend({text: page_id + 1, classes: "standard"}, appendopts || {});
                if (page_id === current_page) {
                    var lnk = jQuery("<span class='current'>" + (appendopts.text) + "</span>");
                }
                else {
                    var lnk = jQuery("<a class=''>" + (appendopts.text) + "</a>")
                        .bind("click", getClickHandler(page_id))
                        .attr('href', opts.link_to.replace(/__id__/, page_id));
                }
                if (appendopts.classes) {
                    lnk.addClass(appendopts.classes);
                }
                panel.append(lnk);
            };
            var appendOption = function (page_num) {
                var selectT = jQuery("<select class='simple'>");
                for (var i = 1; i < page_num + 1; i++) {
                    if (i === current_page + 1) {
                        var lnk = jQuery("<option value ='" + i + "' selected >" + i + "/" + (page_num) + "</option>");
                    }
                    else {
                        var lnk = jQuery("<option value ='" + i + "'>" + i + "</option>");
                    }
                    lnk.appendTo(selectT);
                }
                selectT.bind("change", function () {
                    var selectId = $(this).children('option:selected').val();
                    pageSelected(selectId - 1);
                    selectT.val(10);
                    return false;
                });
                panel.append(selectT);
            };
            // Generate "First"-Link
            if (opts.first_text && (opts.first_show_always)) {
                appendItem(0, {text: opts.first_text, classes: "first"});
            }
            // Generate "Previous"-Link
            if (opts.prev_text && (current_page > 0 || opts.prev_show_always)) {
                appendItem(current_page - 1, {text: opts.prev_text, classes: "prev"});
            }
            //select menu
            appendOption(np);
            // Generate starting points
            if (interval[0] > 0 && opts.num_edge_entries > 0) {
                var end = Math.min(opts.num_edge_entries, interval[0]);
                for (var i = 0; i < end; i++) {
                    appendItem(i);
                }
                if (opts.num_edge_entries < interval[0] && opts.ellipse_text) {
                    jQuery("<span>" + opts.ellipse_text + "</span>").appendTo(panel);
                }
            }
            // Generate interval links
            for (var i = interval[0]; i < interval[1]; i++) {
                appendItem(i);
            }
            // Generate ending points
            if (interval[1] < np && opts.num_edge_entries > 0) {
                if (np - opts.num_edge_entries > interval[1] && opts.ellipse_text) {
                    jQuery("<span>" + opts.ellipse_text + "</span>").appendTo(panel);
                }
                var begin = Math.max(np - opts.num_edge_entries, interval[1]);
                for (var i = begin; i < np; i++) {
                    appendItem(i);
                }

            }
            // Generate "Next"-Link
            if (opts.next_text && (current_page < np - 1 || opts.next_show_always)) {
                appendItem(current_page + 1, {text: opts.next_text, classes: "next"});
            }
            // Generate "Last"-Link
            if (opts.last_text && (current_page < np - 1 || opts.last_show_always)) {
                appendItem(np - 1, {text: opts.last_text, classes: "last"});
            }
        }

        // Extract current_page from options
        var current_page = opts.current_page;
        // Create a sane value for maxentries and items_per_page
        maxentries = (!maxentries || maxentries < 0) ? 1 : maxentries;
        opts.items_per_page = (!opts.items_per_page || opts.items_per_page < 0) ? 1 : opts.items_per_page;
        // Store DOM element for easy access from all inner functions
        var panel = jQuery(this);
        // Attach control functions to the DOM element
        this.selectPage = function (page_id) {
            pageSelected(page_id);
        };
        this.prevPage = function () {
            if (current_page > 0) {
                pageSelected(current_page - 1);
                return true;
            }
            else {
                return false;
            }
        };
        this.nextPage = function () {
            if (current_page < numPages() - 1) {
                pageSelected(current_page + 1);
                return true;
            }
            else {
                return false;
            }
        };
        // When all initialisation is done, draw the links
        if (numPages() > 1) {
            drawLinks();
        }
        // call callback function
        opts.callback(current_page, this);
    });
};

function initGallery() {

    if (typeof ($.fn.justifiedGallery) === 'undefined') {
        return;
    }
    $('#aniimated-thumbnials').justifiedGallery({
        rowHeight: 120,
        maxRowHeight: null,
        margins: 6,
        border: 0,
        rel: 'liveDemo',
        lastRow: 'nojustify',
        captions: true,
        randomize: false
    }).on('jg.complete', function () {
        $(this).lightGallery({
            loop: true,
            thumbnail:true,
            animateThumb: true,
            showThumbByDefault: false,
            download: false
        });
    });
}

function initQRCode() {
    if (typeof ($.fn.qrcode) === 'undefined') {
        return;
    }
    if (typeof ($.fn.popup) === 'undefined') {
        return;
    }

    var $weixin = $('#weixin');

    function generateQRCode(rendermethod, picwidth, picheight, url) {
        $("#qrcode").qrcode({
            render: rendermethod, // 渲染方式有table方式（IE兼容）和canvas方式
            width: picwidth, //宽度
            height: picheight, //高度
            text: url, //内容
            typeNumber: -1,//计算模式
            correctLevel: 2,//二维码纠错级别
            background: "#ffffff",//背景颜色
            foreground: "#000000"  //二维码颜色
        });
    }

    $weixin.popup({
        content: function () {
            return $('#qrcode');
        },
        type: 'html'
    });

    $weixin.on('click', function () {
        $('#code').append('<div id="qrcode"></div>');
        generateQRCode("canvas", 256, 256, window.location.href);
    });
}

Date.prototype.mmdd = function () {
    var mm = this.getMonth() + 1; // getMonth() is zero-based
    var dd = this.getDate();

    return [(mm > 9 ? '' : '0') + mm,
        (dd > 9 ? '' : '0') + dd
    ].join('.');
};

;(function ($) {
    $.extend({
        loadAllObject: function (url, page_index, page_size, handleData) {
            $.ajax({
                type: "GET",
                url: url,
                dataType: "json",
                data: "page=" + page_index + "&size=" + page_size,
                beforeSend: function () {
                    NProgress.start();
                },
                complete: function () {
                    NProgress.done();
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
                beforeSend: function () {
                    NProgress.start();
                },
                complete: function () {
                    NProgress.done();
                },
                success: handleData,
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });
            return false;
        }
    });
})(jQuery);

var article_template = function (data) {
    var html = "";
    $.each(data, function (index, item) {
        html += '<section class="list">';
        var image = item.imageThumb;
        var imageShortName = image.substring(image.lastIndexOf('/') + 1);
        if (imageShortName !== 'xxx.gif') {
            html += '<div class=" mecc">';
        } else {
            html += '<div class=" mucc">';
        }
        html += '<a href="/web/article/' + item.wid + '.html" target="_blank" class="iu">' + item.likeCount + '</a>';
        html += '<h2 class="mecctitle"><a href="/web/article/' + item.wid + '.html" target="_blank">' + item.title + '</a></h2>';
        html += '<address class="meccaddress">';
        html += '<time>' + new Date(item.createdDate).mmdd() + '</time> - <a href="/web/category/' + item.category.name + '" rel="category tag">' + item.category.term + '</a> - 阅 ' + item.readCount.toLocaleString();
        if (item.recommendation === 1) {
            html += '&nbsp;&nbsp;<span class="zan">推荐</span>';
        }
        html += '</address></div>';
        if (imageShortName !== 'xxx.gif') {
            html += '<span class="titleimg"><a href="/web/article/' + item.wid + '.html" target="_blank">';
            html += '<img width="500" height="300" src="' + image + '" class="attachment-thumbnail wp-post-image" alt="' + imageShortName + '"/></a></span>';
            html += '<div class=" abstract">';
        } else {
            html += '<div class=" abstractwithoutpic">';
        }
        html += '<p>' + item.excerpt + '</p></div><div class="clear"></div></section>';
    });
    return html;
};

var novel_template = function (data) {
    var html = '<div class="inner details"><dl class="chapterlist">';
    $.each(data, function (index, item) {
        html += '<dd><a href="' + item.novelId + '/' + item.wid + '.html">' + item.title + '<\/a><\/dd>';
    });
    html += '<div class="clr"><\/div><\/dl><\/div>';
    return html;
};

var album_template = function (data) {
    var html = '<div id="mainpic"><ul class="post_list">';
    $.each(data, function (index, item) {
        html += '<li><div class="main"><a href="/web/album/' + item.id + '" title="' + item.title + '">';
        html += '<img class="lim-icon" src="' + item.coverpic + '" alt="' + item.title + '">';
        html += '<div class="info"><h3>' + item.description + '</h3></div></a></div></li>';
    });
    html += '</ul></div>';
    return html;
};

var novels_template = function (data) {
    var html = '<div class="l">';
    $.each(data, function (index, item) {
        html += '<div class="item"><div class="image"><a href="/web/novel/' + item.id + '.html" title="' + item.name + '">';
        html += '<img src="' + item.cover + '" alt="' + item.name + '"></a></div>';
        html += '<dl><dt><span>' + item.author + '</span><a href="/web/novel/' + item.id + '.html" title="' + item.name + '">' + item.name + '</a></dt>';
        html += '<dd>' + item.description + '</dd></dl><div class="clear"></div></div>';
    });
    html += '</div>';
    return html;
};

jQuery.fn.page = function (opts) {
    opts = jQuery.extend({
        num_entries: 10,
        pagesize: 40,
        current_page: 0,
        show_edit: true,
        show_delete: true,
        show_more: true,
        select_show_always: true,
        get_all_url: "/web/novel/list/",
        callback: function () {
            return false;
        },
        container: '',
        data_container: '',
        template: function () {
            return false;
        }
    }, opts || {});

    return this.each(function () {
        var initPagination = function (container, page_index, num_entries, pagesize, pageselectCallback) {
            // 创建分页
            container.pagination(num_entries, {
                num_edge_entries: 0,
                num_display_entries: 8,
                callback: pageselectCallback,
                items_per_page: pagesize,
                current_page: page_index,
                first_text: "首页",
                last_text: "尾页",
                prev_text: "前一页",
                next_text: "后一页"
            });
        };

        var callback = function (template) {
            return function (page_index, jq) {
                var url = opts.get_all_url;
                $.loadAllObject(url, page_index + 1, opts.pagesize, function (data) {
                    var html = template(data.items.list);
                    opts.data_container.html(html);
                });
                return false;
            }
        }(opts.template);

        initPagination(opts.container, 0, opts.num_entries, opts.pagesize, callback);
    });
};

// NProgress
if (typeof NProgress != 'undefined') {
    $(document).ready(function () {
        NProgress.start();
    });

    $(window).load(function () {
        NProgress.done();
    });
}

$(document).ready(function () {
    initGallery();
    initQRCode();

    $.fn.postLike = function () {
        if ($(this).hasClass('done')) {
            return false;
        } else {
            $(this).addClass('done');
            var id = $(this).data("id"),
                rateHolder = $(this).children('.count');
            $.ajax({
                type: "post",
                url: "/web/admin-ajax",
                data: {
                    "um_id": id
                },
                success: function (data) {
                    $(rateHolder).html(data);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });
            return false;
        }
    };
    $(document).on("click", ".favorite", function () {
        $(this).postLike();
    });

    if ((screen.width >= 1250)) {
        var elm = $('.sitebar_list');
        if ($(elm).length > 0) {
            var startPos = $(elm).offset().top;
            $.event.add(window, "scroll", function () {
                var p = $(window).scrollTop();
                $(elm).css('position', ((p) > startPos) ? 'fixed' : 'static');
                $(elm).css('top', ((p) > startPos) ? '20px' : '');
            });
        }
    }

    $.fn.showScroll = function () {
        $(window).scroll(function () {
            var scrollValue = $(window).scrollTop();
            scrollValue > 500 ? $('div[class=scroll]').fadeIn() : $('div[class=scroll]').fadeOut();
        });
        $('#scroll').click(function () {
            $("html,body").animate({scrollTop: 0}, 200);
        });
    };
    $(this).showScroll();

    var prevpage = $("li.prev a").attr("href");
    var nextpage = $("li.next a").attr("href");
    var indexpage = $("li.index a").attr("href");
    $("body").keydown(function (event) {
        if (event.keyCode == 37 && prevpage != undefined) location = prevpage;
        if (event.keyCode == 39 && nextpage != undefined) location = nextpage;
        if (event.keyCode == 13 && nextpage != undefined) location = indexpage;
    });

    $('#novel-pagination').page({
        num_entries: $("#novelcount").val(),
        get_all_url: "/web/novel/get/" + $("#novelId").val(),
        pagesize: 60,
        container: $('#pagination-container'),
        data_container: $('#novel-data-container'),
        template: novel_template
    });

    $('#article-pagination').page({
        num_entries: $("#articlecount").val(),
        get_all_url: "/web/article/get",
        pagesize: 10,
        container: $('#pagination-container'),
        data_container: $('#article-data-container'),
        template: article_template
    });

    $('#categories-pagination').page({
        num_entries: $("#categoriescount").val(),
        get_all_url: "/web/article/get",
        pagesize: 10,
        container: $('#pagination-container'),
        data_container: $('#categories-data-container'),
        template: article_template
    });

    $('#category-pagination').page({
        num_entries: $("#categorycount").val(),
        get_all_url: "/web/category/get/" + $("#categoryId").val(),
        pagesize: 10,
        container: $('#pagination-container'),
        data_container: $('#category-data-container'),
        template: article_template
    });

    $('#tag-pagination').page({
        num_entries: $("#tagcount").val(),
        get_all_url: "/web/tag/get/" + $("#tagId").val(),
        pagesize: 10,
        container: $('#pagination-container'),
        data_container: $('#tag-data-container'),
        template: article_template
    });

    $('#search-pagination').page({
        num_entries: $("#searchcount").val(),
        get_all_url: "/web/search/get?s=" + $("#searchvalue").val(),
        pagesize: 10,
        container: $('#pagination-container'),
        data_container: $('#search-data-container'),
        template: article_template
    });

    $('#album-pagination').page({
        num_entries: $("#albumcount").val(),
        get_all_url: "/web/albums/get",
        pagesize: 10,
        container: $('#pagination-container'),
        data_container: $('#album-data-container'),
        template: album_template
    });

    $('#novels-pagination').page({
        num_entries: $("#novelscount").val(),
        get_all_url: "/web/novels/get",
        pagesize: 6,
        container: $('#pagination-container'),
        data_container: $('#novels-data-container'),
        template: novels_template
    });
});
