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
import org.cloud.yblog.mapper.ArticleTagMapper;
import org.cloud.yblog.mapper.TagMapper;
import org.cloud.yblog.model.Tag;
import org.cloud.yblog.model.EntityCount;
import org.cloud.yblog.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by d05660ddw on 2017/3/6.
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public List<Tag> getTop10() {
        PageHelper.startPage(1, GlobalContants.PAGESIZE);
        return tagMapper.selectAll();
    }

    @Override
    public List<Tag> getAllByOrder(String column, String orderDir, int startIndex, int pageSize) {
        int pageNum = startIndex / pageSize + 1;
        PageHelper.startPage(pageNum, pageSize);
        return tagMapper.selectAllOrderBy(column, orderDir);
    }

    @Override
    public long saveOrUpdateTag(Tag tag) {
        Long id = tag.getId();
        if (id != null) {
            return tagMapper.updateByPrimaryKey(tag);
        } else {
            tagMapper.insert(tag);
            return tag.getId();
        }
    }

    @Override
    public int deleteSafetyById(Long id) {
        articleTagMapper.deleteFromTag(id);
        return tagMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void setTagCount() {
        List<EntityCount> list = articleTagMapper.selectArticlesCountByTag();
        for(EntityCount map : list) {
            Tag tag = tagMapper.selectByPrimaryKey(map.getId());
            tag.setCount(map.getCount());
            tagMapper.updateCountByPrimaryKey(tag);
        }
    }
}
