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
import org.cloud.yblog.mapper.AlbumMapper;
import org.cloud.yblog.model.Album;
import org.cloud.yblog.service.IAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by d05660ddw on 2017/3/6.
 */
@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements IAlbumService {

    @Autowired
    private AlbumMapper albumMapper;

    @Override
    public long saveOrUpdateAlbum(Album album) {
        Long id = album.getId();
        if (id != null) {
            return albumMapper.updateByPrimaryKey(album);
        } else {
            return albumMapper.insert(album);
        }
    }

    @Override
    public List<Album> getAllAlbum(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return albumMapper.selectAll();
    }

    @Override
    public List<Album> getAllByOrder(String orderColumn, String orderDir, int startIndex, int pageSize) {
        int pageNum = startIndex / pageSize + 1;
        PageHelper.startPage(pageNum, pageSize);
        return albumMapper.selectAllOrderBy(orderColumn, orderDir);
    }
}
