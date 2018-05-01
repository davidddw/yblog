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

package org.cloud.yblog.controller.front;

import org.cloud.yblog.constant.UrlConstants;
import org.cloud.yblog.controller.base.FrontBaseController;
import org.cloud.yblog.controller.exception.ResourceNotFoundException;
import org.cloud.yblog.model.Article;
import org.cloud.yblog.service.*;
import org.cloud.yblog.utils.ValidateCode;
import org.cloud.yblog.dto.CommentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by d05660ddw on 2017/3/11.
 */
@Controller
public class PostCommentController extends FrontBaseController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private ICommentService commentService;
    /**
     *
     * 提交评论，返回json数据
     *
     * @param commentInfo
     * @return json
     */
    @RequestMapping(value = "/node/postComment", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> addComment(CommentInfo commentInfo) {
        Map<String, Object> map = new HashMap<>();
        String validateCode = commentInfo.getValidateCode();
        //boolean checkCodeValidate = validateCode.equals(request.getSession().getAttribute("validateCode"));
        boolean checkCodeValidate = true;
        String returnMessage;
        if (checkCodeValidate) {
            Long postId = commentInfo.getPostId();
            Article article = articleService.getById(postId)
                    .orElseThrow(() -> new ResourceNotFoundException("Album", String.valueOf(postId)));
            if (article != null) {
                Long commentId = commentService.addNewComment(commentInfo, article);
                if (commentId > 0) {
                    returnMessage = "发表评论成功";
                } else {
                    returnMessage = "发表评论失败";
                }
            } else {
                returnMessage = "发表评论失败";
            }
        } else {
            returnMessage = "校验码错误或已经过期";
        }
        map.put("msg", returnMessage);
        map.put("checkCodeValidate", checkCodeValidate);
        return map;
    }

    /**
     * 生成验证码
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = UrlConstants.VALIDATE)
    public void validateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置浏览器不缓存本页
        response.setHeader("Cache-Control", "no-cache");
        // 生成验证码，写入用户session
        String verifyCode = ValidateCode.generateTextCode(ValidateCode.TYPE_NUM_ONLY, 4, null);
        request.getSession().setAttribute("validateCode", verifyCode);
        // 输出验证码给客户端
        response.setContentType("image/jpeg");
        BufferedImage bim = ValidateCode.generateImageCode(verifyCode, 90, 30, 3, true, null, null, null, request.getServletContext());
        ImageIO.write(bim, "JPEG", response.getOutputStream());
    }
}
