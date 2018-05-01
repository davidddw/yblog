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

package org.cloud.yblog.constant;

/**
 * Created by d05660ddw on 2017/3/20.
 */
public class UrlConstants {
    //URLS
    public static final String HOMEPAGE = "/";
    public static final String GETARTICLE = "/article/get";
    public static final String CATEGORIES = "/category";
    public static final String CATEGORY = "/category/{categoryName:[a-zA-Z0-9-]+}";
    public static final String GETCATEGORY = "/category/get/{categoryName:[a-zA-Z0-9-]+}";
    public static final String TAG = "/tag/{tagName}";
    public static final String GETTAG = "/tag/get/{tagName}";
    public static final String ARTICLE = "/article/{articleId}";
    public static final String SEARCH = "/search";
    public static final String GETSEARCH = "/search/get";
    public static final String PAGE = "/page/{pageTitle:[a-zA-Z0-9-]+}";
    public static final String BIAO = "/page/biao";
    public static final String ALBUM = "/album/{albumId}";
    public static final String ALBUMS = "/albums";
    public static final String GETALBUMS = "/albums/get";
    public static final String NOVEL = "/novels";
    public static final String GETNOVEL = "/novels/get";
    public static final String APPDOWEN = "/appdown";
    public static final String NOVELCHAPTER = "/novel/{novelId}";
    public static final String GETNOVELCHAPTER = "/novel/get/{novelId}";
    public static final String NOVELDETAIL = "/novel/{novelId}/{chapterId}";

    public static final String VALIDATE = "/validateCode";
    public static final String ERROR404 = "/errorpage";
    public static final String PATH_LOVE = "/admin-ajax";


    public static final String WEB = "/web";
    public static final String API = "/api/v1";
    public static final String OAUTHAPI = "/api/v2";
    public static final String ADMIN = "/backend";

    //admin模板
    public static final String ADMININDEX =  "/index";
    public static final String WRITEARTICLE = "/writeArticle";
    public static final String LISTARTICLE = "/listArticle";
    public static final String LISTCATEGORY = "/listCategory";
    public static final String LISTTAG = "/listTag";
    public static final String LISTPAGE= "/listPage";
    public static final String LISTGALLERY= "/listGallery";
    public static final String LISTNOVEL= "/listNovel";
    public static final String LISTNOVELCHAPTER= "/listNovelChapter";
    public static final String LISTCOMMENT= "/listComment";
    public static final String LISTUSER= "/listUser";
    public static final String LISTSETTINGS = "/listSettings";
    public static final String UPLOADPIC = "/upload";
    public static final String CROPPIC = "/cropscale";
    public static final String DEVELOPING = "/developing";

    //模板名称
    public static final String THEME = "gr";
    public static final String ADMINTHEME = "admin";

    //front模板
    public static final String FRONT_HOME_FTL = "/home";
    public static final String FRONT_CATEGORIES_FTL = "/categories";
    public static final String FRONT_CATEGORY_FTL = "/category";
    public static final String FRONT_TAG_FTL = "/tag";
    public static final String FRONT_ARTICLE_FTL = "/article";
    public static final String FRONT_PAGE_FTL = "/page";
    public static final String FRONT_BIAO_FTL = "/biao";
    public static final String FRONT_NOTFOUND_FTL = "/404";
    public static final String FRONT_APPDOWEN_FTL = "/appdown";
    public static final String FRONT_ALBUM_FTL = "/album";
    public static final String FRONT_ALBUMS_FTL = "/albums";
    public static final String FRONT_NOVEL_FTL = "/novels";
    public static final String FRONT_NOVEL_CHPTER_FTL = "/novelchapter";
    public static final String FRONT_NOVELDETAIL_FTL = "/noveldetails";

}
