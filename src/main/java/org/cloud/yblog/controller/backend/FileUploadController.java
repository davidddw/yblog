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

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.cloud.yblog.constant.UrlConstants;
import org.cloud.yblog.controller.base.AdminBaseController;
import org.cloud.yblog.model.ResponseMessage;
import org.cloud.yblog.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 上传图片
 * <p>
 * 为CKEDITOR定制的图片上传功能，后续可以扩展上传其他格式的文件
 * 上传的文件的基础路径为: ${apache.home}/${project.name}/${project.name}/resources/static/upload/yyyy/MM/${'yyyyMMdd'}/
 * 每个文件夹下最多500个文件
 * </p>
 */
@Controller
public class FileUploadController extends AdminBaseController {
    protected final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @GetMapping(UrlConstants.UPLOADPIC)
    public void processUpload(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        processUploadPost(modelMap, request, response);
        return;
    }

    @PostMapping(UrlConstants.UPLOADPIC)
    public void processUploadPost(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        // 判断提交的请求是否包含文件
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            return;
        }
        String imageUrlPath = FileUtil.processUploadPostForCkeditor(request);

        try {
            response.setContentType("text/html; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter out = response.getWriter();

            if (!StringUtils.isEmpty(imageUrlPath)) {
                String callback = request.getParameter("CKEditorFuncNum");
                out.println("<script type=\"text/javascript\">");
                out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + imageUrlPath + "',''" + ")");
                out.println("</script>");
                out.flush();
                out.close();
            } else {
                out.println("<script type=\"text/javascript\">alert('格式错误，仅支持jpg|jpeg|bmp|gif|png格式');</script>");
                out.flush();
                out.close();
                return;
            }
        } catch (Exception e) {
            logger.error("error", e);
        }
    }

    @PostMapping(value = UrlConstants.CROPPIC)
    public @ResponseBody
    ResponseMessage processCropPost(@RequestParam String avatar_src, @RequestParam String avatar_data, @RequestParam MultipartFile avatar_file,
                                    MultipartHttpServletRequest request) {
        JSONObject joData = (JSONObject) JSONObject.parse(avatar_data);
        // 用户经过剪辑后的图片的大小
        float x = joData.getFloatValue("x");
        float y = joData.getFloatValue("y");
        float w = joData.getFloatValue("width");
        float h = joData.getFloatValue("height");
        return FileUtil.processCropPost(avatar_file, request, x, y, w, h);
    }

}