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

package org.cloud.yblog.controller.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.cloud.yblog.controller.base.AdminBaseController;
import org.cloud.yblog.controller.exception.ResourceNotFoundException;
import org.cloud.yblog.dto.NovelData;
import org.cloud.yblog.model.NovelChapter;
import org.cloud.yblog.model.ResponseMessage;
import org.cloud.yblog.service.INovelChapterService;
import org.cloud.yblog.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * Created by d05660ddw on 2017/5/3.
 */
@RestController
public class NovelChapterController extends AdminBaseController {
    @Autowired
    private INovelChapterService novelChapterService;

    /**
     * 新增
     * 1. 将上传的文件按章节分割，生成novelchapter
     * 2. 将生成的list批量更新数据库。
     */
    @PostMapping(value = "/novelchapter", produces = "application/json;charset=utf8")
    public
    @ResponseBody
    ResponseMessage addNovelChapter(@RequestBody NovelData novelData) {
        List<NovelChapter> novelChapters = novelChapterService.getNovelChapterFromFile(Long.parseLong(novelData.getId()), novelData.getLocation());
        long number = 0;
        if(!novelChapters.isEmpty()) {
            number = novelChapterService.saveNovelChapterByBatch(novelChapters);
        }
        return new ResponseMessage(String.valueOf(number));
    }

    /**
     * 查询
     */
    @GetMapping("/novelchapter")
    public
    @ResponseBody
    Map<String, Object> listNovelChapter(@RequestParam String draw,
                                         @RequestParam int startIndex,
                                         @RequestParam int pageSize,
                                         @RequestParam(value = "condition", required = false) String condition,
                                         @RequestParam(value = "orderColumn", required = false, defaultValue = "id") String orderColumn,
                                         @RequestParam(value = "orderDir", required = false, defaultValue = "desc") String orderDir) {
        Map<String, Object> info = new HashMap<>();
        info.put("pageData", novelChapterService.getAllByOrder(condition, orderColumn, orderDir, startIndex, pageSize));
        info.put("total", novelChapterService.getCountByCondition(condition, new NovelChapter()));
        info.put("draw", draw);
        return info;
    }

    /**
     * 先删除文件，再删除数据库
     */
    @DeleteMapping("/novelchapter/{id}")
    public
    @ResponseBody
    ResponseMessage removeNovelChapter(@PathVariable String id) {
        NovelChapter novelChapter = novelChapterService.getById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Novel", id));
        FileUtil.delete(getUploadPath() + "/" + novelChapter.getLocation());
        return new ResponseMessage(String.valueOf(novelChapterService.deleteById(novelChapter.getId())));
    }

    /**
     * 批量删除：先删除文件，再删除数据库
     */
    @PostMapping("/novelchapter/delete")
    public
    @ResponseBody
    ResponseMessage removeNovelChapters(@RequestBody List<String> ids) {
        int sum = 0;
        for(String id : ids) {
            NovelChapter novelChapter = novelChapterService.getById(Long.parseLong(id))
                    .orElseThrow(() -> new ResourceNotFoundException("Novel", id));
            FileUtil.delete(getUploadPath() + "/" + novelChapter.getLocation());
            sum += novelChapterService.deleteById(novelChapter.getId());
        }
        return new ResponseMessage(String.valueOf(sum));
    }

    /**
     * Upload single file using Spring Controller
     */
    @RequestMapping(value = "/uploadCommonFile", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public ResponseMessage uploadFileHandler(@RequestParam("avatar1") MultipartFile file, HttpServletRequest request) {
        String uploadPath = FileUtil.uploadCommonFile(file, request);
        return new ResponseMessage(uploadPath);
    }

}
