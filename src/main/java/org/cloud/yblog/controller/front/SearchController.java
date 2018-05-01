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

package org.cloud.yblog.controller.front;

import com.github.pagehelper.PageInfo;
import org.cloud.yblog.constant.UrlConstants;
import org.cloud.yblog.controller.base.FrontBaseController;
import org.cloud.yblog.model.Article;
import org.cloud.yblog.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by d05660ddw on 2017/3/19.
 */
@Controller
public class SearchController extends FrontBaseController {

    @Autowired
    private IArticleService articleService;

    /**
     * 显示搜索页面
     *
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.SEARCH)
    public ModelAndView searchPage(HttpServletRequest request,
                                   @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                   @RequestParam(value = "s", required = false) String searchValue) {
        List<Article> articleList;
        ModelAndView result = defaultModelAndView(UrlConstants.SEARCH, request);
        if(searchValue==null) {
            result.addObject("count", 0);
        } else {
            articleList = articleService.getAllByTitleOrContent(searchValue, pageNum, pageSize);
            result.addObject("searchValue", searchValue);
            result.addObject("count", new PageInfo<>(articleList).getTotal());
        }
        return result;
    }

    /**
     * 查询article
     *
     * @return json
     */
    @GetMapping(UrlConstants.GETSEARCH)
    public
    @ResponseBody
    Map<String, Object> articleGetByTagMethod(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                              @RequestParam(value = "size", required = false, defaultValue = "50") int pageSize,
                                              @RequestParam(value = "s", required = false) String searchValue) {
        Map<String, Object> items = new HashMap<>();
        List<Article> articleList = articleService.getAllByTitleOrContent(searchValue, pageNum, pageSize);
        items.put("items", new PageInfo<>(articleList));
        return items;
    }

//    @Autowired
//    private SearchService searchService;
//
//    public ModelAndView search(@RequestParam("query") String query) throws ScanException, PolicyException {
//        query = AntiSamyUtils.getCleanHtml(query);
//        //totally get data from lucene need to control if the file not in database.
//        List<SearchResult> searchResults = searchService.query(query);
//        List<QueryResultVo> resultVos = buildResultVo(searchResults);
//        ModelAndView s = new ModelAndView("searchPage");
//        boolean hasContent = false;
//        if (!CollectionUtils.isEmpty(resultVos)) {
//            hasContent = true;
//        }
//        s.addObject("hasContent", hasContent);
//        s.addObject("result", resultVos);
//        s.addObject("resultlen", resultVos.size());
//        return s;
//    }
//
//    private static List<QueryResultVo> buildResultVo(List<SearchResult> searchResults) {
//        List<QueryResultVo> resultVos = Lists.newArrayList();
//        for (SearchResult result : searchResults) {
//            resultVos.add(buildResultVo(result));
//        }
//        return resultVos;
//    }
//
//    private static QueryResultVo buildResultVo(SearchResult result) {
//        QueryResultVo vo = new QueryResultVo();
//        vo.setLink(buildLink(result.getId()));
//        vo.setMarktitle(result.getTitle());
//        vo.setMarkContent(result.getMarkText());
//        return vo;
//    }
//
//    private static String buildLink(int id) {
//        return "/getArticle/" + id;
//    }
}
