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
import org.cloud.yblog.constant.GlobalContants;
import org.cloud.yblog.mapper.CommentMapper;
import org.cloud.yblog.model.Article;
import org.cloud.yblog.model.Comment;
import org.cloud.yblog.service.ICommentService;
import org.cloud.yblog.dto.CommentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by d05660ddw on 2017/3/6.
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getAllByArticle(Article article, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return commentMapper.selectAllByArticle(article.getId());
    }

    @Override
    public List<Comment> getTopByDateDesc() {
        PageHelper.startPage(1, GlobalContants.TOPLIMIT);
        return commentMapper.selectCommentByDateDesc();
    }

    @Override
    public Long addNewComment(CommentInfo commentInfo, Article article) {
        Comment com = new Comment(HtmlUtils.htmlEscape(commentInfo.getSubmitter()),
                HtmlUtils.htmlEscape(commentInfo.getEmail()), HtmlUtils.htmlEscape(commentInfo.getContent()));
        com.setArticle(article);
        article.setCommentCount(article.getCommentCount()+1);
        return commentMapper.insert(com);
    }
}
