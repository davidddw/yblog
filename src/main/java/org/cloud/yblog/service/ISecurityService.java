package org.cloud.yblog.service;

/**
 * Created by d05660ddw on 2017/4/8.
 */
public interface ISecurityService {

    String findLoggedInUsername();

    void autologin(String username, String password);
}
