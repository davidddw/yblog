package org.cloud.yblog.service;

/**
 * Created by d05660ddw on 2017/3/19.
 */
public interface ISearchService {

    void addSource(String title, String content, int id);

    void delete(int id);
}
