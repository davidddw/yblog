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

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by d05660ddw on 2017/3/6.
 */
public class StringHelper {

    private static final Logger logger = LoggerFactory.getLogger(StringHelper.class);

    private StringHelper() {}

    public static String getHtmlContentFromTxt(String filename) {
        String content = "";
        File targetFile = new File(filename);
        try {
            content = FileUtils.readFileToString(targetFile);
            content = "<p>" + content + "</p>";
            content = content.replaceAll("(\n+|\r\n)", "</p><p>");
        } catch (FileNotFoundException e) {
            logger.error(targetFile.getAbsolutePath() + " does not exist");
        } catch (IOException e) {
            logger.error("error", e);
        }
        return content;
    }

    public static String getContentFromTxt(String filename) {
        String content = "";
        File targetFile = new File(filename);
        try {
            content = FileUtils.readFileToString(targetFile);
            content = content.replaceAll("\\n\\s\\s\\s\\s", "\n");
        } catch (FileNotFoundException e) {
            logger.error(targetFile.getAbsolutePath() + " does not exist");
        } catch (IOException e) {
            logger.error("error", e);
        }
        return content;
    }

    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static Date stringToDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            logger.error("error", e);
        }
        return new Date();
    }

    public static String join(String[] array) {
        return join(array, ";");
    }

    public static String join(String[] array, String delimiter) {
        // Cache the length of the delimiter
        // has the side effect of throwing a NullPointerException if
        // the delimiter is null.
        int delimiterLength = delimiter.length();
        // Nothing in the array return empty string
        // has the side effect of throwing a NullPointerException if
        // the array is null.
        if (array.length == 0)
            return "";

        // Only one thing in the array, return it.
        if (array.length == 1) {
            if (array[0] == null)
                return "";
            return array[0];
        }

        // Make a pass through and determine the size
        // of the resulting string.
        int length = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null)
                length += array[i].length();
            if (i < array.length - 1)
                length += delimiterLength;
        }

        // Make a second pass through and concatenate everything
        // into a string buffer.
        StringBuffer result = new StringBuffer(length);
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null)
                result.append(array[i]);
            if (i < array.length - 1)
                result.append(delimiter);
        }
        return result.toString();
    }

    public static String encrypt(String plainText) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(plainText);
    }

    public static boolean checkPassword(String plainText, String encryptedText) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(plainText, encryptedText);
    }

    public static int parseWithDefault(String number, int defaultVal) {
        try {
            return Integer.parseInt(number)<0? 0 :Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    public static Long parseLongWithDefault(String number, long defaultVal) {
        try {
            return Long.parseLong(number)<0? 0 :Long.parseLong(number);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }
}
