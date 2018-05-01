package org.cloud.yblog.service;

import org.cloud.yblog.model.Page;

import java.util.List;
import java.util.Optional;

/**
 * Created by d05660ddw on 2017/3/6.
 */
public interface IPageService extends IService<Page> {

    List<Page> getNameAndSlug();

    long saveOrUpdateCategory(Page page);

    Optional<Page> getBySlug(String slug);

    List<Page> getAllByOrder(String column, String orderDir, int pageNum, int pageSize);
}
