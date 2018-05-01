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
import org.cloud.yblog.mapper.NovelChapterMapper;
import org.cloud.yblog.mapper.NovelInfoMapper;
import org.cloud.yblog.model.NovelChapter;
import org.cloud.yblog.model.NovelInfo;
import org.cloud.yblog.service.INovelInfoService;
import org.cloud.yblog.utils.FileUtil;
import org.cloud.yblog.utils.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by d05660ddw on 2017/3/6.
 */
@Service
public class NovelInfoServiceImpl extends ServiceImpl<NovelInfoMapper, NovelInfo> implements INovelInfoService {

    @Autowired
    private NovelInfoMapper novelInfoMapper;

    @Autowired
    private NovelChapterMapper novelChapterMapper;

    @Override
    public long saveOrUpdateNovelInfo(NovelInfo novelInfo) {
        Long id = novelInfo.getId();
        if (id != null) {
            return novelInfoMapper.updateByPrimaryKey(novelInfo);
        } else {
            return novelInfoMapper.insert(novelInfo);
        }
    }

    @Override
    public List<NovelInfo> getAllByOrder(String condition, String orderColumn, String orderDir, int startIndex, int pageSize) {
        int pageNum = startIndex / pageSize + 1;
        PageHelper.startPage(pageNum, pageSize);
        return novelInfoMapper.selectAllOrderBy(condition, orderColumn, orderDir);
    }

    @Override
    public int deleteSafetyById(Long id) {
        List<Long> longList = new ArrayList<>();
        for(NovelChapter novelChapter : novelChapterMapper.selectChaptersByNovelId(id)) {
            longList.add(novelChapter.getId());
        }
        //批量删除数据库
        novelChapterMapper.deleteByBatch(longList);
        String uploadPath = PropertiesUtil.getConfigBykey("uploadPath");
        FileUtil.delete(uploadPath + "/novel/" + id);
        return novelInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<NovelInfo> getAllNovel(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return novelInfoMapper.selectAll();
    }

    @Override
    public long getCountByCondition(String condition, NovelInfo novelInfo) {
        return novelInfoMapper.selectCountByCondition(condition, novelInfo);
    }

}
