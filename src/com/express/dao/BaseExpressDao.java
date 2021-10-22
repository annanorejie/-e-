package com.express.dao;

import com.express.bean.Express;
import com.express.exception.DuplicateCodeException;
import java.util.List;
import java.util.Map;

public interface BaseExpressDao {
    /**
     * 用于查询数据库中的全部快递（总数+新增），以及待取快递（总数+新增）
     * @return [{size:总数,data:新增},{size:总数,data:新增}]
     */
    List<Map<String,Integer>> console();

    /**
     * 用于查询所有快递
     * @param limit 是否分页的标记，true表示分页，false表示查询所有快递
     * @param offset SQL语句的起始索引
     * @param pageNumber 页的查询数量
     * @return 快递的集合
     */
    List<Express> findAll(boolean limit,int offset,int pageNumber);

    /**
     * 根据快递单号查询快递信息
     * @param number 单号
     * @return 查询的快递信息，单号不存在时，返回null
     */
    Express findByNumber(String number);

    /**
     * 根据取件码查询快递信息
     * @param code 取件码
     * @return 查询的快递信息，取件码不存在时，返回null
     */
    Express findByCode(String code);

    /**
     * 根据手机号码查询快递信息
     * @param userPhone 手机号码
     * @return 查询的快递信息，取件码不存在时，返回null
     */
    List<Express> findByUserPhone(String userPhone);

    /**
     * 根据录入的手机号码查询所有录入记录
     * @param sysPhone 手机号码
     * @return 查询的快递信息，取件码不存在时，返回null
     */
    List<Express> findBySysPhone(String sysPhone);

    /**
     * 快递的录入
     * @param e 要录入的快递对象
     * @return 录入的结果，true表示成功，false表示失败
     */
    boolean insert(Express e) throws DuplicateCodeException;

    /**
     * 修改快递
     * @param id 要修改的快递的id
     * @param newExpress 新的快递对象
     * @return 修改的结果，true表示成功，false表示失败
     */
    boolean update(int id,Express newExpress);

    /**
     * 更改快递的状态为1，表示取件完成
     * @param code 要修改的快递取件码
     * @return
     */
    boolean updateStatus(String code);

    /**
     * 根据id删除单个快递信息
     * @param id 要删除的快递id
     * @return
     */
    boolean delete(int id);
    /**
     * 根据用户手机号码，查询他所有的快递信息
     * @param userPhone 手机号码
     * @return 查询的快递信息列表
     */
    List<Express> findByUserPhoneAndStatus(String userPhone,int status);

}
