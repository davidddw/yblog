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
import org.cloud.yblog.model.ImageData;
import org.cloud.yblog.model.NovelInfo;
import org.cloud.yblog.model.ResponseMessage;
import org.cloud.yblog.service.INovelInfoService;
import org.cloud.yblog.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
public class NovelController extends AdminBaseController {
    @Autowired
    private INovelInfoService novelInfoService;

    /**
     * 新增
     */
    @PostMapping(value = "/novel", consumes = "multipart/form-data", produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseMessage addNovel(NovelInfo novelInfo, @RequestParam("avatar") MultipartFile file, HttpServletRequest request) {
        ImageData imageData = FileUtil.uploadSingleFile(file, request, false);
        novelInfo.setCover(imageData.getFullName());
        long number = novelInfoService.saveOrUpdateNovelInfo(novelInfo);
        return new ResponseMessage(String.valueOf(number));
    }

    /**
     * 更新
     */
    @PutMapping(value = "/novel/{novelId}", consumes = "multipart/form-data", produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseMessage updateNovel(@PathVariable String novelId, NovelInfo novelInfo, @RequestParam(value="avatar" ,required =false ) MultipartFile file, HttpServletRequest request) {
        novelInfo.setId(Long.parseLong(novelId));
        if(file!=null) {
            ImageData imageData = FileUtil.uploadSingleFile(file, request, false);
            novelInfo.setCover(imageData.getFullName());
        }
        long number = novelInfoService.saveOrUpdateNovelInfo(novelInfo);
        return new ResponseMessage(String.valueOf(number));
    }

    /**
     * 查询
     */
    @GetMapping("/novel")
    public
    @ResponseBody
    Map<String, Object> listNovel(@RequestParam String draw,
                                  @RequestParam int startIndex,
                                  @RequestParam int pageSize,
                                  @RequestParam(value = "condition", required = false) String condition,
                                  @RequestParam(value = "orderColumn", required = false, defaultValue = "id") String orderColumn,
                                  @RequestParam(value = "orderDir", required = false, defaultValue = "desc") String orderDir) {
        Map<String, Object> info = new HashMap<>();
        info.put("pageData", novelInfoService.getAllByOrder(condition, orderColumn, orderDir, startIndex, pageSize));
        info.put("total", novelInfoService.getCountByCondition(condition, new NovelInfo()));
        info.put("draw", draw);
        return info;
    }

    /**
     * 删除
     */
    @DeleteMapping("/novel/{id}")
    public
    @ResponseBody
    ResponseMessage removeNovel(@PathVariable String id) {
        NovelInfo novelInfo = novelInfoService.getById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Novel", id));
        return new ResponseMessage(String.valueOf(novelInfoService.deleteSafetyById(novelInfo.getId())));
    }

    /**
     * 批量删除
     */
    @PostMapping("/novel/delete")
    public
    @ResponseBody
    ResponseMessage removeNovels(@RequestBody List<String> ids) {
        int sum = 0;
        for(String id : ids) {
            NovelInfo novelInfo = novelInfoService.getById(Long.parseLong(id))
                    .orElseThrow(() -> new ResourceNotFoundException("Novel", id));
            sum += novelInfoService.deleteById(novelInfo.getId());
        }
        return new ResponseMessage(String.valueOf(sum));
    }

    /**
     * Upload single file using Spring Controller
     */
    @RequestMapping(value = "/uploadNovelPic", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public boolean uploadFileHandler(@RequestParam("avatar") MultipartFile file, HttpServletRequest request) {
        ImageData imageData = FileUtil.uploadSingleFile(file, request, false);
        if (imageData !=null) {
        	return true;
        }
        return false;
    }
}
