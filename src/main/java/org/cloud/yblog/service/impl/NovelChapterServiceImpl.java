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

package org.cloud.yblog.service.impl;

import com.github.pagehelper.PageHelper;
import org.cloud.yblog.mapper.NovelChapterMapper;
import org.cloud.yblog.model.NovelChapter;
import org.cloud.yblog.model.NovelInfo;
import org.cloud.yblog.service.INovelChapterService;
import org.cloud.yblog.utils.FileUtil;
import org.cloud.yblog.utils.IdWorker;
import org.cloud.yblog.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by d05660ddw on 2017/3/6.
 */
@Service
public class NovelChapterServiceImpl extends ServiceImpl<NovelChapterMapper, NovelChapter> implements INovelChapterService {

    private final Logger logger = LoggerFactory.getLogger(NovelChapterServiceImpl.class);

    @Autowired
    private NovelChapterMapper novelChapterMapper;

    @Override
    public List<NovelChapter> getAllByOrder(String condition, String orderColumn, String orderDir, int startIndex, int pageSize) {
        int pageNum = startIndex / pageSize + 1;
        PageHelper.startPage(pageNum, pageSize);
        return novelChapterMapper.selectAllOrderBy(condition, orderColumn, orderDir);
    }

    @Override
    public int deleteSafetyById(Long id) {
        return novelChapterMapper.deleteByPrimaryKey(id);
    }

    @Override
    public long getCountByNovelId(Long id) {
        return novelChapterMapper.selectCountByNovelId(id);
    }

    @Override
    public List<NovelChapter> getChaptersByNovelId(long novelId) {
        return novelChapterMapper.selectChaptersByNovelId(novelId);
    }

    @Override
    public List<NovelChapter> getChaptersByNovelId(long novelId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return novelChapterMapper.selectChaptersByNovelId(novelId);
    }

    @Override
    public Optional<NovelChapter> getDetailsByNovelAndChapter(long novelId, long chapterId) {
        return Optional.ofNullable(novelChapterMapper.selectDetailsByChaptersAndNovelId(novelId, chapterId));
    }

    @Override
    public List<NovelChapter> getLastChaptersByNovelId(long novelId) {
        PageHelper.startPage(1, 8);
        return novelChapterMapper.selectAllByNovelIdOrderBy(novelId, "id", "desc");
    }

    @Override
    public List<NovelChapter> getNovelChapterFromFile(long novelId, String srcFilePath) {
        BufferedReader br = null;
        BufferedWriter bw = null;
        List<NovelChapter> novelChapters = new ArrayList<>();
        Pattern pp = Pattern.compile("(第[0-9]+章)[\\s]*([\\S\\u4e00-\\u9fa5]+)");
        Pattern pp2 = Pattern.compile("[^\\r\\n]+[\\r\\n]+[\\S]*");
        boolean success = false;
        try {
            br = new BufferedReader(new FileReader(srcFilePath));
            //fw = new FileWriter(String.format("%s/%s.txt", PATH, "sql"),false);
            String tempString;
            StringBuffer sb = new StringBuffer();
            int index;
            while ((tempString = br.readLine()) != null) {
                tempString += "\r\n";
                sb.append(tempString);
            }
            Matcher m = pp.matcher(sb);
            while (m.find()) {
                long wid = IdWorker.getSingletonId();
                String title = m.group(0);
                Matcher m2 = pp2.matcher(title);
                if (m2.find()) {
                	title = m.group(1) + " 匿名";
                } else {
                	title = m.group(1) + " " + m.group(2);
                }
                // 相对路径
                String relativePath = "novel/" + novelId;
                // uploadPath根目录
                String uploadPath = PropertiesUtil.getConfigBykey("uploadPath");
                // 判断目录是否存在，不存在则创建
                File targetPath = new File(uploadPath + "/" + relativePath);
                if (!targetPath.exists()) {
                    targetPath.mkdirs();
                }
                // 存放数据库中的路径
                String location = String.format("%s/%s.txt", relativePath, wid);
                int titleLength = title.length();
                // txt的完整路径
                File targetFile = new File(uploadPath + "/" + location);
                //logger.info("Novel file location: " + targetFile);
                bw = new BufferedWriter(new FileWriter(targetFile));
                int start = m.start();
                if (m.find()) {
                    index = m.start();
                    bw.write(sb.toString(), start + titleLength, index - start - titleLength);
                    bw.flush();
                    m.region(index, sb.length());
                } else {
                    bw.write(sb.toString(), start + titleLength, sb.length() - start - titleLength);
                }
                NovelChapter novelChapter = new NovelChapter(wid, title, location, new NovelInfo(novelId));
                novelChapters.add(novelChapter);
                success = true;
            }
        } catch (IOException e) {
            success = false;
            logger.error("Exception", e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (bw != null) {
                    bw.close();
                }
                FileUtil.delete(srcFilePath);
            } catch (IOException e) {
                logger.error("Exception", e);
            }
            if(success) {
                logger.info("Novel file upload successful!");
            } else {
                logger.warn("Novel file upload fail!");
            }
        }
        return novelChapters;
    }

    @Override
    public long saveNovelChapterByBatch(List<NovelChapter> novelChapters) {
        return novelChapterMapper.insertByBatch(novelChapters);
    }

    @Override
    public long getCountByCondition(String condition, NovelChapter novelChapter) {
        return novelChapterMapper.selectCountByCondition(condition, novelChapter);
    }

    @Override
    public Map<String, NovelChapter> getPrevAndNextNovel(long novelId, long chapterId) {
        HashMap<String, NovelChapter> prevNextArticle = new HashMap<>();
        List<NovelChapter> prevArticle = novelChapterMapper.selectPrevNovels(novelId, chapterId);
        List<NovelChapter> nextArticle = novelChapterMapper.selectNextNovels(novelId, chapterId);
        prevNextArticle.put("prev_novel", prevArticle.size() > 0 ? prevArticle.get(0) : null);
        prevNextArticle.put("next_novel", nextArticle.size() > 0 ? nextArticle.get(0) : null);
        return prevNextArticle;
    }

}
