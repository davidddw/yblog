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
package org.cloud.yblog.mapper;

import org.apache.ibatis.annotations.Param;
import org.cloud.yblog.model.Article;
import org.cloud.yblog.utils.BaseMapper;

import java.util.Date;
import java.util.List;

/**
 * Created by d05660ddw on 2017/3/6.
 */
public interface ArticleMapper extends BaseMapper<Article> {

    List<Article> selectArticlesByTag(@Param("tagValue") String tagName);

    List<Article> selectArticlesByCategory(@Param("enName") String categoryName);

    List<Article> selectArticlesByCategoryId(@Param("id") Long categoryId);

    List<Article> selectAllOrderBy(@Param("column") String column, @Param("orderDir") String orderDir);

    List<Article> selectArticlesByReadCount();

    Article selectByWid(@Param("wid") Long wid);

    List<Article> selectPrevArticles(@Param("now") Date currentTime);

    List<Article> selectNextArticles(@Param("now") Date currentTime);

    List<Article> selectRelativeArticlesByTag(@Param("myid") Long myId);

    List<Article> selectRelativeArticlesByCategory(@Param("myid") Long myId);

    Long selectArticleCountByCategoryId(@Param("categoryId") Long categoryId);

    void updateReadCountById(Article article);

    void updateLiveCountById(Article article);

    List<Article> selectByTitleOrContent(String value);

    List<Article> selectArticlesByUser(@Param("id") Long id);
}
