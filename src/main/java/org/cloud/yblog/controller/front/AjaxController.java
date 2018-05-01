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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cloud.yblog.constant.CookieConstants;
import org.cloud.yblog.constant.UrlConstants;
import org.cloud.yblog.controller.base.FrontBaseController;
import org.cloud.yblog.controller.exception.ResourceNotFoundException;
import org.cloud.yblog.model.Album;
import org.cloud.yblog.model.Article;
import org.cloud.yblog.model.Category;
import org.cloud.yblog.model.Gallery;
import org.cloud.yblog.model.NovelChapter;
import org.cloud.yblog.model.NovelInfo;
import org.cloud.yblog.model.Tag;
import org.cloud.yblog.service.IAlbumService;
import org.cloud.yblog.service.IArticleService;
import org.cloud.yblog.service.ICategoryService;
import org.cloud.yblog.service.IGalleryService;
import org.cloud.yblog.service.INovelChapterService;
import org.cloud.yblog.service.INovelInfoService;
import org.cloud.yblog.service.IPageService;
import org.cloud.yblog.service.ITagService;
import org.cloud.yblog.utils.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

/**
 * Created by d05660ddw on 2017/3/20.
 * novel handler
 */
@Controller
public class AjaxController extends FrontBaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IPageService pageService;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private ITagService tagService;

    @Autowired
    private IAlbumService albumService;

    @Autowired
    private IGalleryService galleryService;

    @Autowired
    private INovelInfoService novelInfoService;

    @Autowired
    private INovelChapterService novelChapterService;

    /**
     * Ajax查询article
     *
     * @return json
     */
    @GetMapping(UrlConstants.GETARTICLE)
    public
    @ResponseBody
    Map<String, Object> articleGetMethod(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                         @RequestParam(value = "size", required = false, defaultValue = "50") int pageSize) {
        Map<String, Object> items = new HashMap<>();
        List<Article> articleList = articleService.getAllByDateDesc(pageNum, pageSize);
        items.put("items", new PageInfo<>(articleList));
        return items;
    }

    /**
     * 查询article
     *
     * @return json
     */
    @GetMapping(UrlConstants.GETCATEGORY)
    public
    @ResponseBody
    Map<String, Object> articleGetByCategoryMethod(@PathVariable String categoryName,
                                                   @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                                   @RequestParam(value = "size", required = false, defaultValue = "50") int pageSize) {
        Map<String, Object> items = new HashMap<>();
        List<Article> articleList = articleService.getArticlesByCategory(new Category(categoryName, null), pageNum, pageSize);
        items.put("items", new PageInfo<>(articleList));
        return items;
    }

    /**
     * 查询article
     *
     * @param tagName
     * @param pageNum
     * @param pageSize
     * @return json
     */
    @GetMapping(UrlConstants.GETTAG)
    public @ResponseBody
    Map<String, Object> articleGetByTagMethod(@PathVariable String tagName,
                                              @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                              @RequestParam(value = "size", required = false, defaultValue = "50") int pageSize) {
        Map<String, Object> items = new HashMap<>();
        List<Article> articleList = articleService.getArticlesByTag(new Tag(tagName), pageNum, pageSize);
        items.put("items", new PageInfo<>(articleList));
        return items;
    }

    /**
     * 点赞操作
     *
     * @param request
     * @param response
     * @param um_id
     * @return
     */
    @PostMapping(value = UrlConstants.PATH_LOVE)
    public
    @ResponseBody
    String love(HttpServletRequest request, HttpServletResponse response, @RequestParam String um_id) {
        Cookie cookie = CookieUtil.getCookieByName(request, CookieConstants.ARTICLEID + um_id);
        Article article = articleService.getArticlesByWid(Long.valueOf(um_id))
                .orElseThrow(() -> new ResourceNotFoundException("Article", um_id));;
        // 判断cookie是否为空
        if (cookie == null) {
            article.setLikeCount(article.getLikeCount() + 1);
            // 数据库操作，点赞个数加
            articleService.love(article);
            CookieUtil.addCookie(response, CookieConstants.ARTICLEID + um_id, "", CookieConstants.MAXAGE);
        }
        return String.valueOf(article.getLikeCount());
    }

    /**
     * 查询album
     *
     * @return json
     */
    @GetMapping(UrlConstants.GETALBUMS)
    public
    @ResponseBody
    Map<String, Object> albumGetMethod(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                       @RequestParam(value = "size", required = false, defaultValue = "50") int pageSize) {
        Map<String, Object> items = new HashMap<>();
        List<Album> albums = albumService.getAllAlbum(pageNum, pageSize);
        for (Album album : albums) {
            List<Gallery> galleries = galleryService.getGalleryByAlbum(album);
            if (galleries.size() > 0) {
                album.setCoverpic(galleries.get(0).getThumbRelativePath());
            }
        }
        items.put("items", new PageInfo<>(albums));
        return items;
    }

    /**
     * 查询album
     *
     * @return json
     */
    @GetMapping(UrlConstants.GETNOVEL)
    public
    @ResponseBody
    Map<String, Object> novelGetMethod(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                       @RequestParam(value = "size", required = false, defaultValue = "50") int pageSize) {
        Map<String, Object> items = new HashMap<>();
        List<NovelInfo> novelInfoList = novelInfoService.getAllNovel(pageNum, pageSize);
        items.put("items", new PageInfo<>(novelInfoList));
        return items;
    }

    /**
     * Ajax方式查询小说章节
     *
     * @return json
     */
    @GetMapping(UrlConstants.GETNOVELCHAPTER)
    public
    @ResponseBody
    Map<String, Object> novelChapterGetMethod(@PathVariable String novelId,
                                              @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                              @RequestParam(value = "size", required = false, defaultValue = "50") int pageSize) {
        Map<String, Object> novelChapters = new HashMap<>();
        List<NovelChapter> novelChapter = novelChapterService.getChaptersByNovelId(Long.parseLong(novelId), pageNum, pageSize);
        novelChapters.put("items", new PageInfo<>(novelChapter));
        return novelChapters;
    }
}
