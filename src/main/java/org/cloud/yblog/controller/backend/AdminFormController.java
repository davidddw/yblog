/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 d05660@163.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.cloud.yblog.controller.backend;

import javax.servlet.http.HttpServletRequest;

import org.cloud.yblog.constant.UrlConstants;
import org.cloud.yblog.controller.base.AdminBaseController;
import org.cloud.yblog.controller.exception.ResourceNotFoundException;
import org.cloud.yblog.model.Article;
import org.cloud.yblog.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by d05660ddw on 2017/3/25.
 * Admin后台管理界面，主要用于处理ModelAndView
 */
@Controller
public class AdminFormController extends AdminBaseController {

    @Autowired
    private IArticleService articleService;

    /**
     * 显示首页页面
     *
     * @param request
     * @return
     */
    @GetMapping
    public ModelAndView homePageHandler(HttpServletRequest request) {
        return defaultModelAndView(UrlConstants.ADMININDEX, request);
    }

    /**
     * 显示文章编辑页面
     *
     * @param request
     * @param id
     * @return
     */
    @GetMapping(UrlConstants.WRITEARTICLE)
    public ModelAndView newArticleHandler(HttpServletRequest request,
                                          @RequestParam(value = "id", required = false) String id) {
        ModelAndView result = defaultModelAndView(UrlConstants.WRITEARTICLE, request);
        if (null != id) {
            Article article = articleService.getById(Long.parseLong(id))
                    .orElseThrow(() -> new ResourceNotFoundException("Article", id));
            result.addObject("article", article);
        }
        return result;
    }

    /**
     * 显示文章列表页面
     *
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.LISTARTICLE)
    public ModelAndView listArticleHandler(HttpServletRequest request) {
        return defaultModelAndView(UrlConstants.LISTARTICLE, request);
    }

    /**
     * 显示类别页面
     *
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.LISTCATEGORY)
    public ModelAndView listCategoryHandler(HttpServletRequest request) {
        return defaultModelAndView(UrlConstants.LISTCATEGORY, request);
    }

    /**
     * 显示标签页面
     *
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.LISTTAG)
    public ModelAndView listTagHandler(HttpServletRequest request) {
        return defaultModelAndView(UrlConstants.LISTTAG, request);
    }

    /**
     * 显示静态页面
     *
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.LISTPAGE)
    public ModelAndView listPageHandler(HttpServletRequest request) {
        return defaultModelAndView(UrlConstants.LISTPAGE, request);
    }

    /**
     * 显示图片页面
     *
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.LISTGALLERY)
    public ModelAndView listGalleryHandler(HttpServletRequest request) {
        return defaultModelAndView(UrlConstants.LISTGALLERY, request);
    }

    /**
     * 显示小说页面
     *
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.LISTNOVEL)
    public ModelAndView listNovelHandler(HttpServletRequest request) {
        return defaultModelAndView(UrlConstants.LISTNOVEL, request);
    }

    /**
     * 显示小说章节页面
     *
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.LISTNOVELCHAPTER)
    public ModelAndView listNovelChapterHandler(HttpServletRequest request) {
        return defaultModelAndView(UrlConstants.LISTNOVELCHAPTER, request);
    }

    /**
     * 显示评论页面
     *
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.LISTCOMMENT)
    public ModelAndView listCommentHandler(HttpServletRequest request) {
        return defaultModelAndView(UrlConstants.DEVELOPING, request);
    }

    /**
     * 显示用户页面
     *
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.LISTUSER)
    public ModelAndView listUserHandler(HttpServletRequest request) {
        return defaultModelAndView(UrlConstants.LISTUSER, request);
    }

    /**
     * 显示设置页面
     *
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.LISTSETTINGS)
    public ModelAndView listSettingsHandler(HttpServletRequest request) {
        return defaultModelAndView(UrlConstants.LISTSETTINGS, request);
    }
}
