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

package org.cloud.yblog.service.impl;

import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.cloud.yblog.constant.GlobalContants;
import org.cloud.yblog.dto.ArticleData;
import org.cloud.yblog.mapper.*;
import org.cloud.yblog.model.*;
import org.cloud.yblog.service.IArticleService;
import org.cloud.yblog.utils.IdWorker;
import org.cloud.yblog.utils.SetOpt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.*;

/**
 * Created by d05660ddw on 2017/3/6.
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Article> getAllByDateDesc(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return articleMapper.selectAll();
    }

    @Override
    public List<Article> getAllByOrder(String column, String orderDir, int startIndex, int pageSize) {
        int pageNum = startIndex / pageSize + 1;
        PageHelper.startPage(pageNum, pageSize);
        return articleMapper.selectAllOrderBy(column, orderDir);
    }

    @Override
    public List<Article> getAllByTitleOrContent(String value, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return articleMapper.selectByTitleOrContent(value);
    }

    @Override
    public List<Article> getTopByDate() {
        PageHelper.startPage(1, GlobalContants.TOPLIMIT);
        return articleMapper.selectAllOrderBy("created_date", "desc");
    }

    @Override
    public List<Article> getTopByReadCount() {
        PageHelper.startPage(1, GlobalContants.TOPLIMIT);
        return articleMapper.selectArticlesByReadCount();
    }

    @Override
    public List<Article> getArticlesByCategory(Category category, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return articleMapper.selectArticlesByCategory(category.getName());
    }

    @Override
    public List<Article> getArticlesByCategory(Category category) {
        return articleMapper.selectArticlesByCategory(category.getName());
    }

    @Override
    public Optional<Article> getArticlesByWid(long wid) {
        return  Optional.ofNullable(articleMapper.selectByWid(wid));
    }

    @Override
    public List<Article> getArticlesByTag(Tag tag, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return articleMapper.selectArticlesByTag(tag.getName());
    }

    @Override
    public List<Article> getArticlesByTag(Tag tag) {
        return articleMapper.selectArticlesByTag(tag.getName());
    }

    @Override
    public Map<String, Article> getPrevAndNextArticle(Article article) {
        HashMap<String, Article> prevNextArticle = new HashMap<>();
        List<Article> prevArticle = articleMapper.selectPrevArticles(article.getCreatedDate());
        List<Article> nextArticle = articleMapper.selectNextArticles(article.getCreatedDate());
        prevNextArticle.put("prev_article", prevArticle.size() > 0 ? prevArticle.get(0) : null);
        prevNextArticle.put("next_article", nextArticle.size() > 0 ? nextArticle.get(0) : null);
        return prevNextArticle;
    }

    @Override
    public List<Map<String, Object>> getRelativeArticlesByTag(Article article) {
        List<Map<String, Object>> relate_tags = new ArrayList<>();
        for (Article m : articleMapper.selectRelativeArticlesByTag(article.getId())) {
            HashMap<String, Object> temp = new HashMap<>();
            temp.put("id", m.getId());
            temp.put("title", m.getTitle());
            relate_tags.add(temp);
        }
        return relate_tags;
    }

    @Override
    public List<Article> getRelativeArticlesByCategory(Article article) {
        return articleMapper.selectRelativeArticlesByCategory(article.getId());
    }

    @Override
    public void updateArticleReadCount(Article article) {
        articleMapper.updateReadCountById(article);
    }

    @Override
    public List<Article> getTopByComment() {
        return null;
    }

    @Override
    public void love(Article article) {
        articleMapper.updateLiveCountById(article);
    }

    @Override
    public long getArticleCountByCategory(Category category) {
        Category category1 = categoryMapper.selectByName(category.getName());
        return articleMapper.selectArticleCountByCategoryId(category1.getId());
    }

    @Override
    public long saveOrUpdateArticleFromData(ArticleData articleData) {
        long inputUserId = Long.parseLong(articleData.getUser());
        long inputCategoryId = Long.parseLong(articleData.getCategory());
        String articleId = articleData.getId();

        Article article = new Article(HtmlUtils.htmlEscape(articleData.getTitle()), articleData.getAuthor(),
                articleData.getExcerpt(), articleData.getContent(), articleData.getIstop(),
                articleData.getRecommendation(), articleData.getOriginal(), articleData.getOriginal_url(),
                articleData.getImage_thumb(), articleData.getRead_count(),
                articleData.getLike_count(), articleData.getArticle_status());

        //tag操作
        String originString = articleData.getTagname();
        Set<Tag> tags = new HashSet<>();
        if(!StringUtils.isEmpty(originString)) {
            List<Tag> list = tagMapper.selectAll();
            String[] newStrings = originString.split(",");

            for (int i = 0; i < newStrings.length; i++) {
                String escapeString = HtmlUtils.htmlEscape(newStrings[i]);
                Tag t;
                if (SetOpt.exist(list, escapeString, Tag.class)) {
                    t = tagMapper.selectByName(escapeString);
                } else {
                    t = new Tag(escapeString);
                    tagMapper.insert(t);
                }
                tags.add(t);
            }
            article.getTags().addAll(tags);
            String finalString = StringUtils.join(tags.toArray(), ",");
            article.setTagStrings(finalString);
        } else {
            article.setTagStrings(originString);
        }
        article.setUser(userMapper.selectByPrimaryKey(inputUserId));
        article.setCategory(categoryMapper.selectByPrimaryKey(inputCategoryId));
        article.setModifiedDate(new Date());

        // operate database
        long resultValue;
        if (articleId != null) {
            //update
            long id = Long.parseLong(articleId);
            article.setId(id);
            articleTagMapper.deleteFromArticle(id);
            resultValue = articleMapper.updateByPrimaryKey(article);
        } else {
            //insert
            article.setCreatedDate(new Date());
            article.setWid(IdWorker.getSingletonId());
            resultValue = articleMapper.insert(article);
        }
        for(Tag t: tags) {
            articleTagMapper.saveArticleTagRelativity(new ArticleTagLink(article, t));
        }
        return resultValue;
    }

    @Override
    public int deleteSafetyById(Long id) {
        articleTagMapper.deleteFromArticle(id);
        return articleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Article> getarticlesByUser(User user) {
        return articleMapper.selectArticlesByUser(user.getId());
    }
}
