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

package org.cloud.yblog.model;

import org.cloud.yblog.utils.IdWorker;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by d05660ddw on 2017/3/6.
 */
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long wid;

    private User user;

    private Category category;

    private Date createdDate;

    private String title;

    private String author;

    private String excerpt;

    private String imageThumb;

    private String content;

    private String tagStrings;

    private int articleStatus;

    private CommentStatusType commentStatus;

    private Date modifiedDate;

    private long commentCount;

    private long likeCount;

    private long readCount;

    private int isTop;

    private int recommendation;

    private String original;

    private String originalUrl;

    private Set<Comment> comments = new HashSet<>();

    private Set<Tag> tags = new HashSet<>();

    public enum CommentStatusType {
        OPEN, CLOSED, REGISTER
    }

    public Article() {}

    public Article(String title, String content){
        this.title = title;
        this.content = content;
        this.author = "";
        this.commentCount = 0;
        this.excerpt = "";
        this.createdDate = new Date();
        this.articleStatus = 1;
        this.commentStatus = CommentStatusType.OPEN;
        this.imageThumb = "";
        this.readCount = 0;
        this.likeCount = 0;
        this.original = "";
        this.originalUrl = "";
    }

    public Article(String title, String author, String excerpt, String content, String istop,
                   String recommendation, String original, String originalUrl, String image_thumb,
                   String read_count, String like_count, String article_status){
        this.title = title;
        this.content = content;
        this.author = author;
        this.commentCount = 0;
        this.excerpt = excerpt;
        this.articleStatus = 1;
        this.commentStatus = CommentStatusType.OPEN;
        this.imageThumb = image_thumb;
        this.readCount = 0;
        this.likeCount = 0;
        this.original = original;
        this.isTop = Integer.parseInt(istop);
        this.recommendation = Integer.parseInt(recommendation);
        this.originalUrl = originalUrl;
        this.readCount = Integer.parseInt(read_count);
        this.likeCount = Integer.parseInt(like_count);
        this.articleStatus = Integer.parseInt(article_status);
    }

    public Article(String title, User user, String excerpt, String imageThumb, long readCount, String content){
        this.title = title;
        this.user = user;
        this.author = "";
        this.content = content;
        this.commentCount = 0;
        this.excerpt = excerpt;
        this.createdDate = new Date();
        this.wid = IdWorker.getSingletonId();
        this.articleStatus = 1;
        this.commentStatus = CommentStatusType.OPEN;
        this.imageThumb = imageThumb;
        this.readCount = readCount;
        this.likeCount = 0;
        this.original = "";
        this.originalUrl = "";
    }

    public Long getWid() {
        return wid;
    }

    public void setWid(Long wid) {
        this.wid = wid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(int recommendation) {
        this.recommendation = recommendation;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getImageThumb() {
        return imageThumb;
    }

    public void setImageThumb(String imageThumb) {
        this.imageThumb = imageThumb;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public long getReadCount() {
        return readCount;
    }

    public void setReadCount(long readCount) {
        this.readCount = readCount;
    }

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public int getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(int articleStatus) {
        this.articleStatus = articleStatus;
    }

    public CommentStatusType getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(CommentStatusType commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getTagStrings() {
        return tagStrings;
    }

    public void setTagStrings(String tagStrings) {
        this.tagStrings = tagStrings;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
