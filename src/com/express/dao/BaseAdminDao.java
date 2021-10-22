package com.express.dao;

import java.util.Date;

/***
 * 用于admin表格的操作规范
 */
public interface BaseAdminDao {
    /**
     * 根据用户名，更新登录时间和登录ip
     * @param username
     */
    void updateLoginTime(String username, Date date, String ip);

    /**
     * 管理员根据账号和密码进行登录
     * @param username 账号
     * @param password 密码
     * @return 登陆是否成功，true表示登陆成功，false表示登录失败
     */
    boolean login(String username,String password);
}
