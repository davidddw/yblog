package org.cloud.yblog.utils;

import java.util.List;

/**
 *
 * Created by d05660ddw on 2017/3/12.
 */
public interface BaseMapper<T> {

    Long selectCount(T entity);

    T selectByPrimaryKey(Long id);

    List<T> selectAll();

    int deleteByPrimaryKey(Long id);

    Long insert(T entity);

    Long insertSelective(T entity);

    Long updateByPrimaryKey(T entity);
}
