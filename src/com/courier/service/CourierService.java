package com.courier.service;

import com.courier.bean.Courier;
import com.courier.dao.BaseCourierDao;
import com.courier.dao.imp.CourierDaoMysql;
import com.express.exception.DuplicateCodeException;
import com.user.bean.User;
import com.user.dao.BaseUserDao;
import com.user.dao.imp.UserDaoMysql;

import java.util.List;
import java.util.Map;

public class CourierService {
    private static BaseCourierDao dao = new CourierDaoMysql();
    public static List<Map<String, Integer>> console() {
        return dao.console();
    }

    public static List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit,offset,pageNumber);
    }

    public static Courier findByPhone(String phone) {
        return dao.findByPhone(phone);
    }

    public static boolean insert(Courier c) {
        try{
            boolean flag= dao.insert(c);
            return flag;
        }
        catch (DuplicateCodeException duplicateCodeException)
        {
            return insert(c);
        }

    }

    public static boolean update(int id, Courier newCourier) {
        if(newCourier.getPhone()!=null)
        {
            dao.delete(id);
            return insert(newCourier);
        }
        else {
            boolean update = dao.update(id, newCourier);
            return update;
        }
    }

    public static boolean delete(int id) {
        return dao.delete(id);
    }

    public static Courier findById(String id) {
        Courier courier = dao.findById(id);
        return courier;
    }
}
