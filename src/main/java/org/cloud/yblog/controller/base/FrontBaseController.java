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

package org.cloud.yblog.controller.base;

import org.cloud.yblog.constant.UrlConstants;
import org.cloud.yblog.controller.base.BaseController;
import org.cloud.yblog.model.*;
import org.cloud.yblog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by d05660ddw on 2017/3/6.
 */
@RequestMapping(UrlConstants.WEB)
public class FrontBaseController extends BaseController {
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private ICommentService commentService;

    @ModelAttribute("latestArticles")
    public List<Article> getLatestArticles() {
        return articleService.getTopByDate();
    }

    @ModelAttribute("mostArticles")
    public List<Article> getMostPopArticles() {
        return articleService.getTopByReadCount();
    }

    @ModelAttribute("mostComments")
    public List<Comment> getMostComments() {
        return commentService.getTopByDateDesc();
    }

    @Override
    protected ModelAndView defaultModelAndView(String modelTemplate, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView(UrlConstants.THEME + modelTemplate);
        modelAndView.addObject("contextPath", request.getRequestURL());
        modelAndView.addObject("isHomePage", false);
        modelAndView.addObject("categories", categoryService.getAll());
        return modelAndView;
    }

    protected ModelAndView defaultFirstPage(String modelTemplate, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView(UrlConstants.THEME + modelTemplate);
        modelAndView.addObject("contextPath", request.getRequestURL());
        modelAndView.addObject("isHomePage", true);
        modelAndView.addObject("categories", categoryService.getAll());
        return modelAndView;
    }
}
