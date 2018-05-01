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

import freemarker.core.Environment;
import freemarker.template.*;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by d05660ddw on 2017/3/6.
 */
public class DateFormatTag implements TemplateDirectiveModel {

    private static Logger logger = LoggerFactory.getLogger(DateFormatTag.class);
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        String text = "";
        // 标签上的text属性上的值
        if (params.get("date") != null) {
            text = ((SimpleScalar) params.get("date")).getAsString();
        }
        Writer out = env.getOut();

        out.append(getOffsetTime(text));
        if (body != null) {
            body.render(env.getOut());
        }
    }

    private String getOffsetTime(String newTime) {
        try {
            Date lastPost = DateUtils.parseDate(newTime, "yyyy-MM-dd HH:mm:ss.SSS");
            Date now = new Date();
            long duration = (now.getTime() - lastPost.getTime()) / 1000;
            if (duration < 60) {
                return duration + "秒前";
            } else if (duration < 3600) {
                return duration / 60 + "分钟前";
            } else if (duration < 86400) {
                return duration / 3600 + "小时前";
            } else {
                Date zeroTime = DateUtils.truncate(now, Calendar.DATE);
                long offset_day = (zeroTime.getTime() - lastPost.getTime()) / 1000 / 3600 / 24;
                if (offset_day < 1) {
                    return "昨天";
                } else if (offset_day < 2) {
                    return "前天";
                } else if (offset_day < 365) {
                    return offset_day + "天前";
                } else {
                    return offset_day / 365 + "年前";
                }
            }
        } catch (ParseException e) {
            logger.error("error", e);
        }
        return "";
    }
}
