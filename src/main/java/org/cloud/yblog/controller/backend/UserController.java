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

import org.cloud.yblog.constant.UrlConstants;
import org.cloud.yblog.controller.base.AdminBaseController;
import org.cloud.yblog.controller.exception.ResourceNotFoundException;
import org.cloud.yblog.model.Article;
import org.cloud.yblog.model.ResponseMessage;
import org.cloud.yblog.model.User;
import org.cloud.yblog.service.IArticleService;
import org.cloud.yblog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by d05660ddw on 2017/3/20.
 * 用户管理
 */
@RestController
public class UserController extends AdminBaseController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IUserService userService;

    /**
     * 新增
     */
    @PostMapping("/user")
    public @ResponseBody
    ResponseMessage addUser(@RequestBody User user) {
        long number = userService.saveOrUpdateUser(user);
        return new ResponseMessage(String.valueOf(number));
    }

    /**
     * 更新
     */
    @PutMapping("/user/{id}")
    public @ResponseBody
    ResponseMessage updateUser(@PathVariable String id, @RequestBody User user) {
        user.setId(Long.parseLong(id));
        long number = userService.saveOrUpdateUser(user);
        return new ResponseMessage(String.valueOf(number));
    }

    /**
     * 查询
     */
    @GetMapping("/user")
    public @ResponseBody
    Map<String, Object> listUser(@RequestParam String draw,
                                 @RequestParam int startIndex,
                                 @RequestParam int pageSize,
                                 @RequestParam(value = "orderColumn", required = false, defaultValue = "id") String orderColumn,
                                 @RequestParam(value = "orderDir", required = false, defaultValue = "asc") String orderDir) {
        Map<String, Object> info = new HashMap<>();
        info.put("pageData", userService.getAllByOrder(orderColumn, orderDir, startIndex, pageSize));
        info.put("total", userService.getCount(new User()));
        info.put("draw", draw);
        return info;
    }

    /**
     * 删除，除了第一个其他允许删除
     */
    @DeleteMapping("/user/{id}")
    public @ResponseBody
    ResponseMessage removeUser(@PathVariable String id) {
        User defaultUser = userService.getById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        if(!id.equals("1")) {
            User user = userService.getById(Long.parseLong(id))
                    .orElseThrow(() -> new ResourceNotFoundException("Novel", id));
            List<Article> articleList = articleService.getarticlesByUser(user);
            for(Article article : articleList) {
                article.setUser(defaultUser);
                articleService.save(article);
            }
            userService.deleteSafetyById(user.getId());
            new ResponseMessage(String.valueOf(userService.deleteById(user.getId())));
        }
        return new ResponseMessage("Cannot Delete", "0");
    }
}
