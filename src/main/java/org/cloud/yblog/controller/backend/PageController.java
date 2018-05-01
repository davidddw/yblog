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

import org.cloud.yblog.controller.base.AdminBaseController;
import org.cloud.yblog.controller.exception.ResourceNotFoundException;
import org.cloud.yblog.model.Page;
import org.cloud.yblog.model.ResponseMessage;
import org.cloud.yblog.service.IPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * Created by d05660ddw on 2017/3/20.
 */
@RestController
public class PageController extends AdminBaseController {

    @Autowired
    private IPageService pageService;

    /**
     * 新增
     */
    @PostMapping("/page")
    public @ResponseBody
    ResponseMessage addPage(@RequestBody Page page) {
        long number = pageService.saveOrUpdateCategory(page);
        return new ResponseMessage(String.valueOf(number));
    }

    /**
     * 更新
     */
    @PutMapping("/page/{id}")
    public @ResponseBody
    ResponseMessage updatePage(@PathVariable String id, @RequestBody Page page) {
        page.setId(Long.parseLong(id));
        long number = pageService.saveOrUpdateCategory(page);
        return new ResponseMessage(String.valueOf(number));
    }

    /**
     * 查询
     */
    @GetMapping("/page")
    public @ResponseBody
    Map<String, Object> listPage(@RequestParam String draw,
                                 @RequestParam int startIndex,
                                 @RequestParam int pageSize,
                                 @RequestParam(value = "orderColumn", required = false, defaultValue = "id") String orderColumn,
                                 @RequestParam(value = "orderDir", required = false, defaultValue = "asc") String orderDir) {
        Map<String, Object> info = new HashMap<>();
        info.put("pageData", pageService.getAllByOrder(orderColumn, orderDir, startIndex, pageSize));
        info.put("total", pageService.getCount(new Page()));
        info.put("draw", draw);
        return info;
    }

    /**
     * 删除，除了第一个其他允许删除
     */
    @DeleteMapping("/page/{id}")
    public @ResponseBody
    ResponseMessage removePage(@PathVariable String id) {
        Page page = pageService.getById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Page", id));
        return new ResponseMessage(String.valueOf(pageService.deleteById(page.getId())));
    }

    /**
     * 批量删除，除了第一个其他允许删除
     */
    @PostMapping("/page/delete")
    public @ResponseBody
    ResponseMessage removePages(@RequestBody List<String> ids) {
        int sum = 0;
        for(String id : ids) {
            Page page = pageService.getById(Long.parseLong(id))
                    .orElseThrow(() -> new ResourceNotFoundException("Page", id));
            sum += pageService.deleteById(page.getId());
        }
        return new ResponseMessage(String.valueOf(sum));
    }

}
