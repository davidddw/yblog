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

package org.cloud.yblog.utils;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.cloud.yblog.model.ImageData;
import org.cloud.yblog.model.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by d05660ddw on 2017/5/4.
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private static final String FILE_UPLOAD_DIR = "/uploads/";
    private static final String IMAGE = "images/";
    private static final String THUMBNAIL = "thumb/";
    private static final String FILE_DATE_DIR_FMT = "yyyy/MM";
    private static final String FILE_UPLOAD_SUB_IMG_DIR_FMT = "yyyyMMdd_HHmmss_SSS";
    private static final String ALLOWEXT = "jpg,gif,bmp,jpeg,png";
    //上传文件的最大文件大小
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 10;
    //系统默认建立和使用的以时间字符串作为文件名称的时间格式
    private static final String LOCALROOT = PropertiesUtil.getConfigBykey("uploadPath");
    private static final String URLROOT = PropertiesUtil.getConfigBykey("urlPath");

    /**
     * 创建目录
     *
     * @param finalPath 路径字符串
     * @return boolean
     */
    private static boolean makeDirs(String finalPath) {
        File fullFolder = new File(finalPath);
        if (!fullFolder.exists()) {
            if (!fullFolder.mkdirs()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取文件的存放相对路径
     *
     * @param
     * @return
     */
    private static String getTempRelativePath() {
        //从配置文件中获取文件存放路径
        String finalPath = LOCALROOT + FILE_UPLOAD_DIR + "temp";
        return makeDirs(finalPath) ? FILE_UPLOAD_DIR + "temp" : "";
    }

    /**
     * 获取图片的存放相对路径
     *
     * @param path
     * @return
     */
    private static String getImageRelativePath(String path) {
        //从配置文件中获取文件存放路径
        path = path.endsWith("/") ? path : path + "/";
        String finalPath = LOCALROOT + FILE_UPLOAD_DIR + path;
        return makeDirs(finalPath) ? FILE_UPLOAD_DIR + path : "";
    }

    /**
     * @param date localtime
     * @return
     */
    private static String getImageRelativePathByDate(LocalDateTime date) {
        //获取时间格式的路径名
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FILE_DATE_DIR_FMT);
        String datePath = IMAGE + date.format(formatter);
        return getImageRelativePath(datePath);
    }

    /**
     * @param date localtime
     * @return
     */
    private static String getThumbRelativePathByDate(LocalDateTime date) {
        //获取时间格式的路径名
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FILE_DATE_DIR_FMT);
        String datePath = THUMBNAIL + date.format(formatter);
        return getImageRelativePath(datePath);
    }

    private static String getDateTypeFileName(LocalDateTime date, String suffix) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FILE_UPLOAD_SUB_IMG_DIR_FMT);
        return date.format(formatter) + "." + suffix;
    }

    private static String getUUIDFileName(String suffix) {
        return UUID.randomUUID().toString() + "." + suffix;
    }

    private static String getWidFileName(String suffix) {
        return IdWorker.getSingletonId() + "." + suffix;
    }

    private static String getImageUrlPathByDate(LocalDateTime date) {
        return URLROOT + getImageRelativePathByDate(date);
    }

    private static String getThumbUrlPathByDate(LocalDateTime date) {
        return URLROOT + getThumbRelativePathByDate(date);
    }

    /**
     * Ckeditor的上传操作
     *
     * @param request
     * @return
     */
    public static String processUploadPostForCkeditor(HttpServletRequest request) {
        LocalDateTime now = LocalDateTime.now();
        String realFolder = LOCALROOT + getImageRelativePathByDate(now);
        String urlPath = getImageUrlPathByDate(now);

        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        diskFileItemFactory.setRepository(new File(realFolder));
        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
        servletFileUpload.setFileSizeMax(MAX_FILE_SIZE);

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Iterator<String> fileNameIter = multipartRequest.getFileNames();
        try {
            while (fileNameIter.hasNext()) {
                MultipartFile mr = multipartRequest.getFile(fileNameIter.next());
                String sourceFileName = mr.getOriginalFilename();
                if (StringUtils.isNotBlank(sourceFileName)) {
                    String fileType = sourceFileName.substring(sourceFileName.lastIndexOf(".") + 1);
                    if (ALLOWEXT.contains(fileType)) {
                        String filename = getDateTypeFileName(now, fileType);
                        File targetFile = new File(realFolder + File.separator + filename);
                        logger.info("Upload file path: " + targetFile.getAbsolutePath());
                        mr.transferTo(targetFile);
                        return urlPath + "/" + filename;
                    } else {
                        logger.error("上传文件的格式错误: " + fileType);
                        return "";
                    }
                }
            }
        } catch (IOException e) {
            logger.error("error", e);
        }
        return "";
    }

    /**
     * 缩放并裁剪
     *
     * @param avatar_file
     * @param request
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public static ResponseMessage processCropPost(MultipartFile avatar_file, HttpServletRequest request, float x, float y, float width, float height) {
        LocalDateTime now = LocalDateTime.now();
        String realFolder = LOCALROOT + getThumbRelativePathByDate(now);
        String urlPath = getThumbUrlPathByDate(now);
        //判断文件的MIMEtype
        String type = avatar_file.getContentType();
        if (type == null || !type.toLowerCase().startsWith("image/")) {
            return new ResponseMessage("不支持的文件类型，仅支持图片！", null);
        }

        String sourceFileName = avatar_file.getOriginalFilename();
        String fileType = sourceFileName.substring(sourceFileName.lastIndexOf(".") + 1);
        String fileName = getDateTypeFileName(now, fileType);
        String fullUrl = urlPath + "/" + fileName;

        //开始上传
        File targetFile = new File(realFolder, fileName);
        //保存
        try {
            if (!targetFile.exists()) {
                InputStream is = avatar_file.getInputStream();
                ImageUtil.cutAndScale(ImageIO.read(is), targetFile, (int) x, (int) y, (int) width, (int) height, 500, 300);
                is.close();
            }
        } catch (Exception e) {
            logger.error("error", e);
            return new ResponseMessage("上传失败，出现异常：" + e.getMessage(), null);
        }
        return new ResponseMessage("上传成功", fullUrl);
    }

    /**
     * 上传单个文件
     *
     * @param file
     * @return
     */
    public static ImageData uploadSingleFile(MultipartFile file, HttpServletRequest request, Boolean haveThumb) {
        if (!file.isEmpty()) {
            LocalDateTime now = LocalDateTime.now();
            String imageRelativeFolder = getImageRelativePathByDate(now);
            String thumbRelativeFolder = getThumbRelativePathByDate(now);

            String sourceFileName = file.getOriginalFilename();
            String fileType = sourceFileName.substring(sourceFileName.lastIndexOf(".") + 1);
            String fileName = getWidFileName(fileType);
            File targetFile = new File(LOCALROOT + imageRelativeFolder + File.separator + fileName);
            try {
                file.transferTo(targetFile);
                logger.info("Upload file path: " + targetFile.getAbsolutePath());
                if (haveThumb) {
                    File thumbFile = new File(LOCALROOT + thumbRelativeFolder + File.separator + fileName);
                    ImageUtil.zoomImage(targetFile, thumbFile, 300);
                }
                return new ImageData(fileName, file.getSize(), imageRelativeFolder, thumbRelativeFolder);
            } catch (Exception e) {
                logger.error("error", e);
            }
        }
        return null;
    }

    /**
     * 上传多个文件
     *
     * @param files
     * @return
     */
    public static List<ImageData> uploadMultipleFiles(MultipartFile[] files, HttpServletRequest request) {
        List<ImageData> arr = new ArrayList<>();
        for (MultipartFile file : files) {
            arr.add(uploadSingleFile(file, request, true));
        }
        return arr;
    }

    /**
     * 上传普通文件
     * @param file  文件名
     * @param request   http请求
     * @return
     */
    public static String uploadCommonFile(MultipartFile file, HttpServletRequest request) {
        if (!file.isEmpty()) {
            String commonRelativeFolder = getTempRelativePath();
            String sourceFileName = file.getOriginalFilename();
            String fileType = sourceFileName.substring(sourceFileName.lastIndexOf(".") + 1);
            String fileName = getWidFileName(fileType);
            File targetFile = new File(LOCALROOT + commonRelativeFolder + File.separator + fileName);
            try {
                file.transferTo(targetFile);
                logger.info("Upload file path: " + targetFile.getAbsolutePath());
                return targetFile.getAbsolutePath();
            } catch (Exception e) {
                logger.error("error", e);
            }
        }
        return null;
    }

    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName
     * @return
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            logger.error("删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName
     * @return
     */
    private static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                logger.warn("Delete " + fileName + " successful！");
                return true;
            } else {
                logger.error("Delete " + fileName + " fail！");
                return false;
            }
        } else {
            logger.error("Delete Single File：" + fileName + " fail!");
            return false;
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param dir
     * @return
     */
    private static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            logger.error("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        if (files != null) {
            for (File file : files) {
                // 删除子文件
                if (file.isFile()) {
                    flag = deleteFile(file.getAbsolutePath());
                    if (!flag)
                        break;
                }
                // 删除子目录
                else if (file.isDirectory()) {
                    flag = deleteDirectory(file.getAbsolutePath());
                    if (!flag)
                        break;
                }
            }
        }
        if (!flag) {
            logger.error("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            logger.warn("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }
}
