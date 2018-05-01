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

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by d05660ddw on 2017/3/25.
 */
public class ImageHelper {
    public static String getImageRealPath(HttpServletRequest request, String path) {
        return request.getSession().getServletContext().getRealPath("/") + path;
    }

    public static String getImageUrlPath(HttpServletRequest request, String path) {
        return request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath() + "/"
                + path;
    }

    public static String getImageFormat(String filename) throws IOException {
        // get image format in a file
        File file = new File(filename);
        // create an image input stream from the specified file
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        // get all currently registered readers that recognize the image format
        Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
        if (!iter.hasNext()) {
            throw new RuntimeException("No readers found!");
        }
        // get the first reader
        ImageReader reader = iter.next();
        String returnValue = reader.getFormatName();
        // close stream
        iis.close();
        return returnValue;
    }

    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return System.currentTimeMillis() + "_" + sb.toString();
    }

    public static String rename(String fileName, String newName) {
        String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        return newName + suffix;
    }

    public static Map<String, Object> uploadOriginalImage(MultipartFile file, String realPath, String urlPath)
            throws IOException {
        Map<String, Object> returnValues = new HashMap<>();
        if (file.getSize() > 0) {
            InputStream inputStream = file.getInputStream();
            String newFileName = rename(file.getOriginalFilename(),
                    getRandomString(4));
            String categoryFolder = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            String fullDirectory = realPath + categoryFolder;
            if (!new File(fullDirectory).isDirectory()) {
                new File(fullDirectory).mkdirs();
            }
            String fileName = fullDirectory + "/" + newFileName;
            OutputStream outputStream = new FileOutputStream(fileName);
            int readBytes = 0;
            byte[] buffer = new byte[10000];
            while ((readBytes = inputStream.read(buffer, 0, 10000)) != -1) {
                outputStream.write(buffer, 0, readBytes);
            }
            outputStream.close();
            inputStream.close();
            returnValues.put("error", 0);
            returnValues.put("msg", "ok");
            returnValues.put("url", urlPath + categoryFolder + "/" + newFileName);
            return returnValues;
        } else {
            returnValues.put("error", 1);
            returnValues.put("msg", "fail");
            return returnValues;
        }
    }

    public static Map<String, Object> uploadResizeImage(MultipartFile file, String realPath, String imageFormat, String urlPath)
            throws IOException {
        Map<String, Object> returnValues = new HashMap<>();
        if (file.getSize() > 0) {
            BufferedImage src = ImageIO.read(file.getInputStream());
            int width = src.getWidth(); // 得到源图宽
            int height = src.getHeight(); // 得到源图长
            int newWidth, newHeight;
            if (width > 570) {
                newWidth = 570;
                newHeight = (newWidth * height) / width;
            } else {
                newWidth = width;
                newHeight = height;
            }
            // 获取一个宽、长是原来1/n的图像实例
            Image image = src.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);

            BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB); // 缩放图像
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(tag, imageFormat, bos);// 输出到bos
            String newFileName = rename(file.getOriginalFilename(), getRandomString(4));
            String fileName = realPath + newFileName;
            OutputStream outputStream = new FileOutputStream(fileName);
            outputStream.write(bos.toByteArray());
            outputStream.close();
            returnValues.put("error", 0);
            returnValues.put("msg", "ok");
            returnValues.put("url", urlPath + newFileName);
            return returnValues;
        } else {
            returnValues.put("error", 1);
            returnValues.put("msg", "fail");
            return returnValues;
        }
    }
}
