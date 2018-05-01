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
import org.cloud.yblog.constant.CookieConstants;
import org.cloud.yblog.constant.UrlConstants;
import org.cloud.yblog.controller.base.FrontBaseController;
import org.cloud.yblog.controller.exception.ResourceNotFoundException;
import org.cloud.yblog.model.*;
import org.cloud.yblog.service.*;
import org.cloud.yblog.utils.CookieUtil;
import org.cloud.yblog.utils.PropertiesUtil;
import org.cloud.yblog.utils.QRCodeUtil;
import org.cloud.yblog.utils.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by d05660ddw on 2017/3/20.
 * novel handler
 */
@Controller
public class FrontPageController extends FrontBaseController {

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
     * 主页列表显示文章，按照创建时间降序排列
     *
     * @param options
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.HOMEPAGE)
    public ModelAndView homePageHandler(@ModelAttribute("options") HashMap<String, String> options, HttpServletRequest request,
                                        @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum) {
        ModelAndView result;
        if (1 == pageNum) {
            result = defaultFirstPage(UrlConstants.FRONT_HOME_FTL, request);
        } else {
            result = defaultModelAndView(UrlConstants.FRONT_HOME_FTL, request);
        }
        long count = articleService.getCount(new Article());
        result.addObject("count", count);
        return result;
    }

    /**
     * 按照Category列表显示文章
     *
     * @param options
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.CATEGORIES)
    public ModelAndView articleListByCategoriesHandler(@ModelAttribute("options") HashMap<String, String> options, HttpServletRequest request) {
        List<Article> articleList = new ArrayList<>();
        List<Category> categories = categoryService.getAll();
        ModelAndView result;
        if (categories.isEmpty()) {
            result = defaultModelAndView(UrlConstants.FRONT_NOTFOUND_FTL, request);
        } else {
            result = defaultModelAndView(UrlConstants.FRONT_CATEGORIES_FTL, request);
            result.addObject("categories", categories);
            for (Category sub : categories) {
                articleList.addAll(articleService.getArticlesByCategory(sub));
            }
            result.addObject("count", articleList.size());
        }
        return result;
    }

    /**
     * 按照Category二级列表显示文章
     *
     * @param options
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.CATEGORY)
    public ModelAndView articleListByCategory(@ModelAttribute("options") HashMap<String, String> options, HttpServletRequest request,
                                              @PathVariable String categoryName,
                                              @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                              @RequestParam(value = "size", required = false, defaultValue = "50") int pageSize) {
        ModelAndView result;
        Category category = categoryService.getCategoryByName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category", categoryName));
        if (null == category) {
            result = defaultModelAndView(UrlConstants.FRONT_NOTFOUND_FTL, request);
        } else {
            List<Article> articleList = articleService.getArticlesByCategory(new Category(categoryName, null), pageNum, pageSize);
            result = defaultModelAndView(UrlConstants.FRONT_CATEGORY_FTL, request);
            result.addObject("count", new PageInfo<>(articleList).getTotal());
            result.addObject("category", category);
        }
        return result;
    }

    /**
     * 显示page页面
     *
     * @param options
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.PAGE)

    public ModelAndView page(@ModelAttribute("options") HashMap<String, String> options, HttpServletRequest request,
                             @PathVariable String pageTitle) {
        Page page = pageService.getBySlug(pageTitle)
                .orElseThrow(() -> new ResourceNotFoundException("Page", pageTitle));
        ModelAndView result;
        if (page == null) {
            result = defaultModelAndView(UrlConstants.FRONT_NOTFOUND_FTL, request);
        } else {
            result = defaultModelAndView(UrlConstants.FRONT_PAGE_FTL, request);
            result.addObject("page", page);
        }
        return result;
    }

    /**
     * 显示标签页面
     *
     * @param options
     * @return
     */
    @GetMapping(value = UrlConstants.BIAO)
    public ModelAndView biao(@ModelAttribute("options") HashMap<String, String> options, HttpServletRequest request) {
        List<Tag> tagList = tagService.getAll();
        ModelAndView result = defaultModelAndView(UrlConstants.FRONT_BIAO_FTL, request);
        result.addObject("tags", tagList);
        return result;
    }

    /**
     * 按照Tag列表显示文章
     *
     * @param options
     * @param request
     * @param tagName
     * @return
     */
    @GetMapping(UrlConstants.TAG)
    public ModelAndView articleListByTagHandler(@ModelAttribute("options") HashMap<String, String> options, HttpServletRequest request,
                                                @PathVariable String tagName,
                                                @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        List<Article> articleList = articleService.getArticlesByTag(new Tag(tagName), pageNum, pageSize);
        ModelAndView result;
        if (articleList.isEmpty()) {
            result = defaultModelAndView(UrlConstants.FRONT_NOTFOUND_FTL, request);
        } else {
            result = defaultModelAndView(UrlConstants.FRONT_TAG_FTL, request);
            result.addObject("count", new PageInfo<>(articleList).getTotal());
            result.addObject("tag", new Tag(tagName));
        }
        return result;
    }

    /**
     * 显示详情页面
     *
     * @param options
     * @param articleId
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.ARTICLE)
    public ModelAndView articles(@ModelAttribute("options") HashMap<String, String> options,
                                 @PathVariable String articleId, HttpServletRequest request) {
        Long myArticleId = StringHelper.parseLongWithDefault(articleId, 0);
        Article article = articleService.getArticlesByWid(myArticleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", articleId));
        ModelAndView result;
        if (article == null) {
            result = defaultModelAndView(UrlConstants.FRONT_NOTFOUND_FTL, request);
        } else {
            article.setReadCount(article.getReadCount() + 1);
            articleService.updateArticleReadCount(article);
            result = defaultModelAndView(UrlConstants.FRONT_ARTICLE_FTL, request);
            result.addObject("article", article);
            result.addObject("relative_article", articleService.getRelativeArticlesByCategory(article));
        }
        return result;
    }

    @GetMapping(value = "/qrcode")
    public void qrcode(String content, @RequestParam(defaultValue = "256", required = false) int width,
                       @RequestParam(defaultValue = "256", required = false) int height, HttpServletResponse response) {
        ServletOutputStream outputStream = null;
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("image/png");
        try {
            outputStream = response.getOutputStream();
            QRCodeUtil.writeToStream(content, outputStream, width, height);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 显示404页面
     *
     * @return
     * @throws Exception
     */
    @GetMapping(UrlConstants.ERROR404)
    public ModelAndView handleRequest() throws Exception {
        return defaultModelAndView(UrlConstants.FRONT_NOTFOUND_FTL, null);
    }

    /**
     * 显示画廊
     *
     * @param request
     * @param albumId
     * @return
     */
    @GetMapping(UrlConstants.ALBUM)
    public ModelAndView gallery(HttpServletRequest request, @PathVariable String albumId) {
        Album album = albumService.getById(Long.parseLong(albumId))
                .orElseThrow(() -> new ResourceNotFoundException("Album", albumId));
        ModelAndView result;
        if (album == null) {
            result = defaultModelAndView(UrlConstants.FRONT_NOTFOUND_FTL, request);
        } else {
            List<Gallery> galleries = galleryService.getGalleryByAlbum(album);
            result = defaultModelAndView(UrlConstants.FRONT_ALBUM_FTL, request);
            result.addObject("galleries", galleries);
            result.addObject("album", album);
        }
        return result;
    }

    /**
     * 显示Albumset画廊
     *
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.ALBUMS)
    public ModelAndView galleriesListHandler(HttpServletRequest request,
                                             @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                             @RequestParam(value = "size", required = false, defaultValue = "50") int pageSize) {
        List<Album> albums = albumService.getAllAlbum(pageNum, pageSize);
        ModelAndView result;
        if (albums.isEmpty()) {
            result = defaultModelAndView(UrlConstants.FRONT_NOTFOUND_FTL, request);
        } else {
            result = defaultModelAndView(UrlConstants.FRONT_ALBUMS_FTL, request);
            result.addObject("count", new PageInfo<>(albums).getTotal());

        }
        return result;
    }

    /**
     * 显示appdownload
     *
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.APPDOWEN)
    public ModelAndView appDownload(HttpServletRequest request) {
        return defaultModelAndView(UrlConstants.FRONT_APPDOWEN_FTL, request);
    }

    /**
     * 显示novels
     *
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.NOVEL)
    public ModelAndView novelListHandler(HttpServletRequest request,
                                         @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                         @RequestParam(value = "size", required = false, defaultValue = "50") int pageSize) {
        List<NovelInfo> novelInfoList = novelInfoService.getAllNovel(pageNum, pageSize);
        if (novelInfoList.isEmpty()) {
            return defaultModelAndView(UrlConstants.FRONT_NOTFOUND_FTL, request);
        } else {
            ModelAndView result = defaultModelAndView(UrlConstants.FRONT_NOVEL_FTL, request);
            result.addObject("count", new PageInfo<>(novelInfoList).getTotal());
            return result;
        }
    }

    /**
     * 显示novelchanpter
     *
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.NOVELCHAPTER)
    public ModelAndView novelChanpterlistHandler(HttpServletRequest request, @PathVariable String novelId) {
        ModelAndView result;
        List<NovelChapter> lastNovelChapter = novelChapterService.getLastChaptersByNovelId(Long.parseLong(novelId));
        if (lastNovelChapter.isEmpty()) {
            result = defaultModelAndView(UrlConstants.FRONT_NOTFOUND_FTL, request);
        } else {
            NovelInfo novelInfo = novelInfoService.getById(Long.parseLong(novelId))
                    .orElseThrow(() -> new ResourceNotFoundException("Novel", novelId));
            long count = novelChapterService.getCountByNovelId(Long.parseLong(novelId));
            result = defaultModelAndView(UrlConstants.FRONT_NOVEL_CHPTER_FTL, request);
            result.addObject("novelId", novelId);
            result.addObject("novelInfo", novelInfo);
            result.addObject("novelCount", count);
            result.addObject("lastnovelchapters", lastNovelChapter);
        }
        return result;
    }

    /**
     * 显示noveldetails
     *
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.NOVELDETAIL)
    public ModelAndView novelDetailHandler(HttpServletRequest request, @PathVariable String novelId, @PathVariable String chapterId) {
        NovelChapter novelDetails = novelChapterService.getDetailsByNovelAndChapter(Long.parseLong(novelId), Long.parseLong(chapterId))
                .orElseThrow(() -> new ResourceNotFoundException("Novel", chapterId));
        ModelAndView result;
        if (novelDetails == null) {
            result = defaultModelAndView(UrlConstants.FRONT_NOTFOUND_FTL, request);
        } else {
            String content = StringHelper.getHtmlContentFromTxt(PropertiesUtil.getConfigBykey("uploadPath") + "/" + novelDetails.getLocation());
            novelDetails.setLocation(content != null ? content : "");
            result = defaultModelAndView(UrlConstants.FRONT_NOVELDETAIL_FTL, request);
            result.addObject("noveldetails", novelDetails);
            Map<String, NovelChapter> previousNext = novelChapterService.getPrevAndNextNovel(Long.parseLong(novelId), Long.parseLong(chapterId));
            String prevUrl, nextUrl;
            if (previousNext.get("prev_novel") == null) {
                prevUrl = novelDetails.getNovel().getId().toString();
            } else {
                prevUrl = String.format("%s/%s", novelDetails.getNovel().getId(), previousNext.get("prev_novel").getWid());
            }
            result.addObject("prevurl", prevUrl);
            if (previousNext.get("next_novel") == null) {
                nextUrl = novelDetails.getNovel().getId().toString();
            } else {
                nextUrl = String.format("%s/%s", novelDetails.getNovel().getId(), previousNext.get("next_novel").getWid());
            }
            result.addObject("nexturl", nextUrl);
        }
        return result;
    }
}
