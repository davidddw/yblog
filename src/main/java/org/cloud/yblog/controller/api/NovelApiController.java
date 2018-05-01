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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloud.yblog.controller.base.APIBaseController;
import org.cloud.yblog.controller.exception.APINotFoundException;
import org.cloud.yblog.model.NovelChapter;
import org.cloud.yblog.model.NovelInfo;
import org.cloud.yblog.service.INovelChapterService;
import org.cloud.yblog.service.INovelInfoService;
import org.cloud.yblog.utils.PropertiesUtil;
import org.cloud.yblog.utils.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by d05660ddw on 2017/6/4.
 * novel api handler
 */

@RestController
public class NovelApiController extends APIBaseController {

    @Autowired
    private INovelInfoService novelInfoService;

    @Autowired
    private INovelChapterService novelChapterService;

    /**
     * 查询小说列表
     *
     * @param pageNum
     * @param pageSize
     * @return json
     */
    @GetMapping("/novel")
    public @ResponseBody
    Map<String, Object> getNovelList(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                     @RequestParam(value = "size", required = false, defaultValue = "50") int pageSize) {
        List<NovelInfo> novelInfoList = novelInfoService.getAllNovel(pageNum, pageSize);
        Map<String, Object> info = new HashMap<>();
        info.put("page", pageNum);
        info.put("size", pageSize);
        info.put("items", novelInfoList);
        info.put("total", novelInfoService.getCount(new NovelInfo()));
        return info;
    }

    /**
     * 查询小说列表
     *
     * @param novelId
     * @return
     */
    @GetMapping("/novel/{novelId}")
    public @ResponseBody
    NovelInfo getNovelInfoDetails(@PathVariable Long novelId) {
        NovelInfo novelInfo = novelInfoService.getById(novelId)
                .orElseThrow(APINotFoundException::new);
        List<NovelChapter> novelChapter = novelChapterService.getLastChaptersByNovelId(novelId);
        novelInfo.setLatestChapter(novelChapter);
        return novelInfo;
    }

    /**
     * 查询
     */
    @GetMapping("/novel/{novelId}/chapter")
    public @ResponseBody
    Map<String, Object> getNovelChapterList(@PathVariable Long novelId,
                                            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                            @RequestParam(value = "size", required = false, defaultValue = "50") int pageSize) {
        List<NovelChapter> novelChapter = novelChapterService.getChaptersByNovelId(novelId, pageNum, pageSize);
        long count = novelChapterService.getCountByNovelId(novelId);
        NovelInfo novelInfo = novelInfoService.getById(novelId)
                .orElseThrow(APINotFoundException::new);
        Map<String, Object> info = new HashMap<>();
        info.put("info", novelInfo);
        info.put("page", pageNum);
        info.put("size", pageSize);
        info.put("items", novelChapter);
        info.put("total", count);
        return info;
    }

    /**
     * 查询
     */
    @GetMapping("/novel/{novelId}/{chapterId}")
    public @ResponseBody
    Map<String, Object> getNovelDetails(@PathVariable Long novelId, @PathVariable Long chapterId) {
        Map<String, Object> info = new HashMap<>();
        NovelChapter novelDetails = novelChapterService.getDetailsByNovelAndChapter(novelId, chapterId)
                .orElseThrow(APINotFoundException::new);
        if (novelDetails == null) {

        } else {
            String content = StringHelper.getContentFromTxt(PropertiesUtil.getConfigBykey("uploadPath") + "/" + novelDetails.getLocation());
            novelDetails.setLocation(content != null ? content : "");
            info.put("noveldetails", novelDetails);
            Map<String, NovelChapter> previousNext = novelChapterService.getPrevAndNextNovel(novelId, chapterId);
            String prevUrl, nextUrl;
            if (previousNext.get("prev_novel") == null) {
                prevUrl = novelDetails.getNovel().getId().toString();
            } else {
                prevUrl = String.format("%s/%s", novelDetails.getNovel().getId(), previousNext.get("prev_novel").getWid());
            }
            info.put("prevurl", prevUrl);
            if (previousNext.get("next_novel") == null) {
                nextUrl = novelDetails.getNovel().getId().toString();
            } else {
                nextUrl = String.format("%s/%s", novelDetails.getNovel().getId(), previousNext.get("next_novel").getWid());
            }
            info.put("nexturl", nextUrl);
        }
        return info;
    }
}
