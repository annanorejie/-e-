package com.user.dao;

import com.express.exception.DuplicateCodeException;
import com.user.bean.User;

import java.util.List;
import java.util.Map;

public interface BaseUserDao {
    /**
     * 用于查询数据库中的全部快递（总数+新增），以及待取快递（总数+新增）
     * @return [{size:总数,data:新增},{size:总数,data:新增}]
     */
    List<Map<String,Integer>> console();

    /**
     * 通过id查询用户
     * @param id 用户di
     * @return user对象
     */
    User findById(String id);

    /**
     * 用于查询所有快递
     * @param limit 是否分页的标记，true表示分页，false表示查询所有快递
     * @param offset SQL语句的起始索引
     * @param pageNumber 页的查询数量
     * @return 快递的集合
     */
    List<User> findAll(boolean limit, int offset, int pageNumber);

    /**
     * 根据快递单号查询快递信息
     * @param phone 电话
     * @return 查询的快递信息，单号不存在时，返回null
     */
    User findByPhone(String phone);

    /**
     * 快递的录入
     * @param c 要录入的快递对象
     * @return 录入的结果，true表示成功，false表示失败
     */
    boolean insert(User c) throws DuplicateCodeException;

    /**
     * 修改快递
     * @param id 要修改的快递的id
     * @param newUser 新的快递对象
     * @return 修改的结果，true表示成功，false表示失败
     */
    boolean update(int id,User newUser);

    /**
     * 根据id删除单个快递信息
     * @param id 要删除的快递id
     * @return
     */
    boolean delete(int id);

}
