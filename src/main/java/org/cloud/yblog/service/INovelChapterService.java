package org.cloud.yblog.service;

import org.cloud.yblog.model.NovelChapter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by d05660ddw on 2017/3/6.
 */
public interface INovelChapterService extends IService<NovelChapter> {

    List<NovelChapter> getAllByOrder(String condition, String orderColumn, String orderDir, int startIndex, int pageSize);

    int deleteSafetyById(Long id);

    long getCountByNovelId(Long id);

    List<NovelChapter> getChaptersByNovelId(long novelId);

    List<NovelChapter> getChaptersByNovelId(long novelId, int pageNum, int pageSize);

    Optional<NovelChapter> getDetailsByNovelAndChapter(long novelId, long chapterId);

    Map<String, NovelChapter> getPrevAndNextNovel(long novelId, long chapterId);

    List<NovelChapter> getLastChaptersByNovelId(long novelId);

    List<NovelChapter> getNovelChapterFromFile(long novelId, String filePath);

    long saveNovelChapterByBatch(List<NovelChapter> novelChapters);

    long getCountByCondition(String condition, NovelChapter novelChapter);
}
