package com.express.service;

import com.express.bean.Express;
import com.express.dao.BaseExpressDao;
import com.express.dao.imp.ExpressDaoMysql;
import com.express.exception.DuplicateCodeException;
import com.express.util.RandomUtil;

import java.util.List;
import java.util.Map;

public class ExpressService {
    private static BaseExpressDao dao = new ExpressDaoMysql();
    public static List<Map<String, Integer>> console() {
        return dao.console();
    }

    public static List<Express> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit,offset,pageNumber);
    }

    public static Express findByNumber(String number) {
        return dao.findByNumber(number);
    }

    public static Express findByCode(String code) {
        return dao.findByCode(code);
    }

    public static List<Express> findByUserPhone(String userPhone) {
        return dao.findByUserPhone(userPhone);
    }

    public static List<Express> findBySysPhone(String sysPhone) {
        return dao.findBySysPhone(sysPhone);
    }

    public static boolean insert(Express e) {
        e.setCode(RandomUtil.getCode() + "");
        try{
            boolean flag= dao.insert(e);
            return flag;
        }
        catch (DuplicateCodeException duplicateCodeException)
        {
            return insert(e);
        }

    }

    public static boolean update(int id, Express newExpress) {
        if(newExpress.getUserPhone()!=null)
        {
            dao.delete(id);
            return insert(newExpress);
        }
        else {
            boolean update = dao.update(id, newExpress);
            Express e = dao.findByNumber(newExpress.getNumber());
            if(newExpress.getStatus()==1)
            {
                updateStatus(e.getCode());
            }
            return update;
        }
    }

    public static boolean updateStatus(String code) {
        return dao.updateStatus(code);
    }

    public static boolean delete(int id) {
        return dao.delete(id);
    }
    /**
     * 根据用户手机号码，查询他所有的快递信息
     *
     * @param userPhone 手机号码
     * @param status 状态码
     * @return 查询的快递信息列表
     */
    public static List<Express> findByUserPhoneAndStatus(String userPhone,int status) {
        return dao.findByUserPhoneAndStatus(userPhone,status);
    }

}
