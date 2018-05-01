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

import java.io.Serializable;

/**
 * Created by d05660ddw on 2017/5/1.
 */
public class NovelChapter implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long wid;

    private String title;

    private String location;

    private String novelId;

    private NovelInfo novel;

    public NovelChapter() {
    }

    public NovelChapter(Long wid, String title, String location, NovelInfo novel) {
        this.wid = wid;
        this.title = title;
        this.location = location;
        this.novel = novel;
    }

    public String getNovelId() {
        return String.valueOf(novel.getId());
    }

    public void setNovelId(String novelId) {
        this.novelId = novelId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWid() {
        return wid;
    }

    public void setWid(Long wid) {
        this.wid = wid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public NovelInfo getNovel() {
        return novel;
    }

    public void setNovel(NovelInfo novel) {
        this.novel = novel;
    }

    @Override
    public String toString() {
        return title;
    }
}
