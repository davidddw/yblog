package org.cloud.yblog.service;

import java.util.List;
import java.util.Optional;

/**
 * Created by d05660ddw on 2017/3/6.
 */
public interface IService<T> {

    Long getCount(T entity);

    Optional<T> getById(Long id);

    List<T> getAll();

    List<T> getAll(int pageNum, int pageSize);

    void save(T entity);

    int deleteById(Long id);
}
