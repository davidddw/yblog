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

import org.cloud.yblog.constant.UrlConstants;
import org.cloud.yblog.controller.base.AdminBaseController;
import org.cloud.yblog.dto.ArticleData;
import org.cloud.yblog.model.Article;
import org.cloud.yblog.model.ResponseMessage;
import org.cloud.yblog.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by d05660ddw on 2017/3/20.
 */
@RestController
public class ArticleController extends AdminBaseController {

    @Autowired
    private IArticleService articleService;

    /**
     * 新增
     */
    @PostMapping("/article")
    public
    @ResponseBody
    ResponseMessage addArticle(@RequestBody ArticleData articleData) {
        long number = articleService.saveOrUpdateArticleFromData(articleData);
        return new ResponseMessage(String.valueOf(number));
    }

    /**
     * 更新
     */
    @PutMapping("/article/{id}")
    public
    @ResponseBody
    ResponseMessage updateArticle(@PathVariable String id, @RequestBody ArticleData articleData) {
        articleData.setId(id);
        long number = articleService.saveOrUpdateArticleFromData(articleData);
        return new ResponseMessage(String.valueOf(number));
    }

    /**
     * 查询
     */
    @GetMapping("/article")
    public
    @ResponseBody
    Map<String, Object> listArticle(@RequestParam String draw,
                                    @RequestParam int startIndex,
                                    @RequestParam int pageSize,
                                    @RequestParam (value = "orderColumn", required = false, defaultValue = "createdDate") String orderColumn,
                                    @RequestParam (value = "orderDir", required = false, defaultValue = "desc") String orderDir) {
        Map<String, Object> info = new HashMap<>();
        info.put("pageData", articleService.getAllByOrder(orderColumn, orderDir, startIndex, pageSize));
        info.put("total", articleService.getCount(new Article()));
        info.put("draw", draw);
        return info;
    }

    /** 删除 */
    @DeleteMapping("/article/{id}")
    public
    @ResponseBody
    ResponseMessage removeArticle(@PathVariable String id) {
        return new ResponseMessage(String.valueOf(articleService.deleteSafetyById(Long.parseLong(id))));
    }
}
