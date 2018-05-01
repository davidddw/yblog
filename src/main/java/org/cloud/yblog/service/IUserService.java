package org.cloud.yblog.service;

import org.cloud.yblog.model.User;

import java.util.List;

/**
 * Created by d05660ddw on 2017/3/6.
 */
public interface IUserService extends IService<User> {
    long saveOrUpdateUser(User user);

    List<User> getAllByOrder(String orderColumn, String orderDir, int startIndex, int pageSize);

    int deleteSafetyById(Long id);
}
