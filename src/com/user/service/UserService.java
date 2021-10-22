package com.user.service;

import com.express.exception.DuplicateCodeException;
import com.user.bean.User;
import com.user.dao.BaseUserDao;
import com.user.dao.imp.UserDaoMysql;

import java.util.List;
import java.util.Map;

public class UserService {
    private static BaseUserDao dao = new UserDaoMysql();
    public static List<Map<String, Integer>> console() {
        return dao.console();
    }

    public static List<User> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit,offset,pageNumber);
    }

    public static User findByPhone(String phone) {
        return dao.findByPhone(phone);
    }

    public static boolean insert(User e) {
        try{
            boolean flag= dao.insert(e);
            return flag;
        }
        catch (DuplicateCodeException duplicateCodeException)
        {
            return insert(e);
        }

    }

    public static boolean update(int id, User newUser) {
        if(newUser.getPhone()!=null)
        {
            dao.delete(id);
            return insert(newUser);
        }
        else {
            boolean update = dao.update(id, newUser);
            return update;
        }
    }

    public static boolean delete(int id) {
        return dao.delete(id);
    }

    public static User findById(String id) {
        User user = dao.findById(id);
        return user;
    }
}
