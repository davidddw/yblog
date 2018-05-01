package org.cloud.yblog.service;

import org.cloud.yblog.model.Tag;

import java.util.List;

/**
 * Created by d05660ddw on 2017/3/6.
 */
public interface ITagService extends IService<Tag> {
    List<Tag> getTop10();

    List<Tag> getAllByOrder(String column, String orderDir, int pageNum, int pageSize);

    long saveOrUpdateTag(Tag tag);

    int deleteSafetyById(Long id);

    void setTagCount();
}
