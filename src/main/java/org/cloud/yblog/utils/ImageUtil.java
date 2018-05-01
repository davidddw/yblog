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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.*;
import java.util.Iterator;

/**
 * 纯JAVA实现的图片处理工具类
 */
public class ImageUtil {

    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 图像切割(按指定起点坐标和宽高切割)
     *
     * @param bi     源图像
     * @param result 切片后的图像地址
     * @param x      目标切片起点坐标X
     * @param y      目标切片起点坐标Y
     * @param width  目标切片宽度
     * @param height 目标切片高度
     */
    public static void cutAndScale(BufferedImage bi, File result, int x, int y, int width, int height, int destWidth, int destHeight) {
        try {
            // 读取源图像
            //BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcWidth = bi.getWidth(); // 源图宽度
            int srcHeight = bi.getHeight(); // 源图高度
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageOutputStream ios = ImageIO.createImageOutputStream(outputStream);
            if (srcWidth > 0 && srcHeight > 0) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                // 四个参数分别为图像起点坐标和宽高
                // 即: CropImageFilter(int x,int y,int width,int height)
                ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
                Image img = Toolkit.getDefaultToolkit().createImage(
                        new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图

                g.dispose();
                ImageIO.write(tag, "JPEG", ios);
            }
            byte[] data = outputStream.toByteArray();
            BufferedImage src = ImageIO.read(new ByteArrayInputStream(data));
            Image image = src.getScaledInstance(destWidth, destHeight, Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图

            g.dispose();
            ImageIO.write(tag, "JPEG", result);// 输出到文件流

        } catch (Exception e) {
            logger.error("error", e);
        }
    }

    /**
     * 通用图片缩放
     *
     * @param src
     * @param dest
     * @param w
     * @param h
     * @throws Exception
     */
    public static void zoomImage(File src, File dest, int w, int h) throws Exception {
        BufferedImage bufImg = ImageIO.read(src);
        String destString = dest.getName();
        double wr = w * 1.0 / bufImg.getWidth();
        double hr = h * 1.0 / bufImg.getHeight();
        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
        Image Itemp = ato.filter(bufImg, null);
        try {
            ImageIO.write((BufferedImage) Itemp, destString.substring(destString.lastIndexOf(".") + 1), dest);
        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }

    /**
     * 等比例图片缩放
     *
     * @param src
     * @param dest
     * @param h
     * @throws Exception
     */
    public static void zoomImage(File src, File dest, int h) throws Exception {
        BufferedImage bufImg = ImageIO.read(src);
        String destString = dest.getName();
        double scale = h * 1.0 / bufImg.getHeight();
        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(scale, scale), null);
        Image Itemp = ato.filter(bufImg, null);
        try {
            ImageIO.write((BufferedImage) Itemp, destString.substring(destString.lastIndexOf(".") + 1), dest);
        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }

    /*
     * 图片裁剪通用接口
     */

    public static void cutImage(String src, String dest, int x, int y, int w, int h) throws IOException {
        Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = (ImageReader) iterator.next();
        InputStream in = new FileInputStream(src);
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        Rectangle rect = new Rectangle(x, y, w, h);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);
        ImageIO.write(bi, "jpg", new File(dest));
    }

    /**
     * 对图片裁剪，并把裁剪新图片保存
     *
     * @param srcPath          读取源图片路径
     * @param toPath           写入图片路径
     * @param x                剪切起始点x坐标
     * @param y                剪切起始点y坐标
     * @param width            剪切宽度
     * @param height           剪切高度
     * @param readImageFormat  读取图片格式
     * @param writeImageFormat 写入图片格式
     * @throws IOException
     */
    public static void cutOutImage(String srcPath, String toPath, int x, int y, int width, int height,
                                   String readImageFormat, String writeImageFormat) throws IOException {
        FileInputStream fis = null;
        ImageInputStream iis = null;
        try {
            //读取图片文件
            fis = new FileInputStream(srcPath);
            Iterator it = ImageIO.getImageReadersByFormatName(readImageFormat);
            ImageReader reader = (ImageReader) it.next();
            //获取图片流
            iis = ImageIO.createImageInputStream(fis);
            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();
            //定义一个矩形
            Rectangle rect = new Rectangle(x, y, width, height);
            //提供一个 BufferedImage，将其用作解码像素数据的目标。
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            //保存新图片
            ImageIO.write(bi, writeImageFormat, new File(toPath));
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (iis != null) {
                iis.close();
            }
        }
    }

    /**
     * 横向拼接图片（两张）
     *
     * @param firstSrcImagePath  第一张图片的路径
     * @param secondSrcImagePath 第二张图片的路径
     * @param imageFormat        拼接生成图片的格式
     * @param toPath             拼接生成图片的路径
     */
    public static void joinImagesHorizontal(String firstSrcImagePath, String secondSrcImagePath, String imageFormat, String toPath) {
        //可以改进为将两张图缩放到同等高度之后进行拼接，等后续改进吧
        try {
            //读取第一张图片
            File firstFile = new File(firstSrcImagePath);
            BufferedImage firstImg = ImageIO.read(firstFile);
            int firstWidth = firstImg.getWidth();
            int firstHeight = firstImg.getHeight();
            //从图片中读取RGB
            int[] firstImgAry = new int[firstWidth * firstHeight];
            firstImgAry = firstImg.getRGB(0, 0, firstWidth, firstHeight, firstImgAry, 0, firstWidth);
            //对第二张图片做相同的处理
            File secondFile = new File(secondSrcImagePath);
            BufferedImage secondImg = ImageIO.read(secondFile);
            int secondWidth = secondImg.getWidth();
            int secondHeight = secondImg.getHeight();
            //从图片中读取RGB
            int[] secondImgAry = new int[secondWidth * secondHeight];
            //secondImgAry  =  secondImg.getRGB(0,0,firstWidth,firstHeight,secondImgAry,0,firstWidth);
            secondImgAry = secondImg.getRGB(0, 0, secondWidth, secondHeight, secondImgAry, 0, secondWidth);

            //生成新图片
            int thirdHeight = (firstHeight > secondHeight || firstHeight == secondHeight) ? firstHeight : secondHeight;
            //BufferedImage imageNew = new BufferedImage(firstWidth*2,firstHeight,BufferedImage.TYPE_INT_RGB);
            BufferedImage imageNew = new BufferedImage(firstWidth + secondWidth, thirdHeight, BufferedImage.TYPE_INT_RGB);
            imageNew.setRGB(0, 0, firstWidth, firstHeight, firstImgAry, 0, firstWidth);//设置左半部分的RGB
            //imageNew.setRGB(firstWidth,0,firstWidth,firstHeight,secondImgAry,0,firstWidth);//设置右半部分的RGB
            imageNew.setRGB(firstWidth, 0, secondWidth, secondHeight, secondImgAry, 0, secondWidth);//设置右半部分的RGB

            File outFile = new File(toPath);
            ImageIO.write(imageNew, imageFormat, outFile);//写图片
        } catch (Exception e) {
            logger.error("error", e);
        }
    }

    /**
     * 横向拼接一组（多张）图像
     *
     * @param pics    将要拼接的图像
     * @param type    图像写入格式
     * @param dst_pic 图像写入路径
     * @return
     */
    public static boolean joinImageListHorizontal(String[] pics, String type, String dst_pic) {
        //可以改进为将该组图缩放到同等高度之后进行拼接，等后续改进吧
        try {
            int len = pics.length;
            if (len < 1) {
                System.out.println("pics len < 1");
                return false;
            }
            File[] src = new File[len];
            BufferedImage[] images = new BufferedImage[len];
            int[][] imageArrays = new int[len][];
            for (int i = 0; i < len; i++) {
                src[i] = new File(pics[i]);
                images[i] = ImageIO.read(src[i]);
                int width = images[i].getWidth();
                int height = images[i].getHeight();
                imageArrays[i] = new int[width * height];// 从图片中读取RGB
                imageArrays[i] = images[i].getRGB(0, 0, width, height, imageArrays[i], 0, width);
            }
            int dst_width = 0;
            int dst_height = images[0].getHeight();
            for (int i = 0; i < images.length; i++) {
                dst_height = dst_height > images[i].getHeight() ? dst_height : images[i].getHeight();
                dst_width += images[i].getWidth();
            }
            //System.out.println(dst_width);
            //System.out.println(dst_height);
            if (dst_height < 1) {
                System.out.println("dst_height < 1");
                return false;
            }
            //生成新图片
            BufferedImage ImageNew = new BufferedImage(dst_width, dst_height, BufferedImage.TYPE_INT_RGB);
            int width_i = 0;
            for (int i = 0; i < images.length; i++) {
                ImageNew.setRGB(width_i, 0, images[i].getWidth(), dst_height, imageArrays[i], 0, images[i].getWidth());
                width_i += images[i].getWidth();
            }
            File outFile = new File(dst_pic);
            ImageIO.write(ImageNew, type, outFile);// 写图片
        } catch (Exception e) {
            logger.error("error", e);
            return false;
        }
        return true;
    }

    /**
     * 纵向拼接图片（两张）
     *
     * @param firstSrcImagePath  读取的第一张图片
     * @param secondSrcImagePath 读取的第二张图片
     * @param imageFormat        图片写入格式
     * @param toPath             图片写入路径
     */
    public static void joinImagesVertical(String firstSrcImagePath, String secondSrcImagePath, String imageFormat, String toPath) {
        //可以改进为将两张图缩放到同等宽度之后进行拼接，等后续改进吧
        try {
            //读取第一张图片
            File fileOne = new File(firstSrcImagePath);
            BufferedImage imageOne = ImageIO.read(fileOne);
            int width = imageOne.getWidth();//图片宽度
            int height = imageOne.getHeight();//图片高度
            //从图片中读取RGB
            int[] imageArrayOne = new int[width * height];
            imageArrayOne = imageOne.getRGB(0, 0, width, height, imageArrayOne, 0, width);

            //对第二张图片做相同的处理
            File fileTwo = new File(secondSrcImagePath);
            BufferedImage imageTwo = ImageIO.read(fileTwo);
            int width2 = imageTwo.getWidth();
            int height2 = imageTwo.getHeight();
            int[] ImageArrayTwo = new int[width2 * height2];
            ImageArrayTwo = imageTwo.getRGB(0, 0, width, height, ImageArrayTwo, 0, width);
            //ImageArrayTwo = imageTwo.getRGB(0,0,width2,height2,ImageArrayTwo,0,width2);

            //生成新图片
            //int width3 = (width>width2 || width==width2)?width:width2;
            BufferedImage imageNew = new BufferedImage(width, height * 2, BufferedImage.TYPE_INT_RGB);
            //BufferedImage  imageNew  =  new  BufferedImage(width3,height+height2,BufferedImage.TYPE_INT_RGB);
            imageNew.setRGB(0, 0, width, height, imageArrayOne, 0, width);//设置上半部分的RGB
            imageNew.setRGB(0, height, width, height, ImageArrayTwo, 0, width);//设置下半部分的RGB
            //imageNew.setRGB(0,height,width2,height2,ImageArrayTwo,0,width2);//设置下半部分的RGB

            File outFile = new File(toPath);
            ImageIO.write(imageNew, imageFormat, outFile);//写图片
        } catch (Exception e) {
            logger.error("error", e);
        }
    }
}