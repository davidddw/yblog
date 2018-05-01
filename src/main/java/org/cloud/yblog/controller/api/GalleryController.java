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

package org.cloud.yblog.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloud.yblog.controller.base.APIBaseController;
import org.cloud.yblog.dto.GalleryData;
import org.cloud.yblog.model.Gallery;
import org.cloud.yblog.service.IGalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by d05660ddw on 2017/6/4.
 * novel api handler
 */

@RestController
public class GalleryController extends APIBaseController {

    @Autowired
    private IGalleryService galleryService;

    /**
     * 查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/gallery")
    public
    @ResponseBody
    Map<String, Object> getGallery(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                   @RequestParam(value = "size", required = false, defaultValue = "50") int pageSize) {
        List<GalleryData> galleryDataList = new ArrayList<>();
        List<Gallery> galleryList = galleryService.getAllGallery(pageNum, pageSize);
        for (Gallery gallery : galleryList) {
            String url = getBasePath() + gallery.getPath() + gallery.getName();
            GalleryData galleryData = new GalleryData(gallery.getId(), url, gallery.getAlbum().getDescription());
            galleryDataList.add(galleryData);
        }
        Map<String, Object> info = new HashMap<>();
        info.put("page", pageNum);
        info.put("size", pageSize);
        info.put("items", galleryDataList);
        info.put("total", galleryService.getCount(new Gallery()));
        return info;
    }

}
