package com.user.dao.imp;

import com.express.exception.DuplicateCodeException;
import com.express.util.DruidUtil;
import com.user.bean.User;
import com.user.dao.BaseUserDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoMysql implements BaseUserDao {
    //用于查询数据库中的全部用户人数
    public static final String SQL_CONSOLE = "SELECT COUNT(ID) data1_size FROM USER";
    //用于查询数据库中的所有快递信息
    public static final String SQL_FIND_ALL = "SELECT * FROM USER";
    //用于分页查询数据库中的快递信息
    public static final String SQL_FIND_LIMIT = "SELECT * FROM USER LIMIT ?,?";
    //通过手机号查询快递信息
    public static final String SQL_FIND_BY_PHONE = "SELECT * FROM USER WHERE PHONE=?";
    //录入快递
    public static final String SQL_INSERT = "INSERT INTO USER (ID,NAME,PHONE,SEX,ADDRESS) VALUES(?,?,?,?,?)";
    //快递修改
    public static final String SQL_UPDATE = "UPDATE USER SET NAME=?,PHONE=?,SEX=?,ADDRESS=? WHERE ID=?";
    //快递的删除
    public static final String SQL_DELETE = "DELETE FROM USER WHERE ID=?";
    //通过id查询用户
    public static final String SQL_FIND_BY_ID="SELECT * FROM USER WHERE ID=?";

    @Override
    public List<Map<String, Integer>> console() {
        ArrayList<Map<String, Integer>> data=new ArrayList<>();
        //获取数据库的连接
        Connection connection = DruidUtil.getConnection();
        ResultSet resultSet=null;
        PreparedStatement sta=null;
        try {
            //预编译sql语句
            sta = connection.prepareStatement(SQL_CONSOLE);
            //填充参数
            //执行sql语句
            resultSet = sta.executeQuery();
            //获取执行结果
            if(resultSet.next())
            {
                int data1_size = resultSet.getInt("data1_size");
                Map data1 = new HashMap();
                data1.put("data1_size",data1_size);
                data.add(data1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //资源的释放
        finally {
            DruidUtil.close(connection,sta,resultSet);
        }
        return data;
    }

    @Override
    public User findById(String id) {
        //获取数据库的连接
        Connection connection = DruidUtil.getConnection();
        ResultSet resultSet=null;
        PreparedStatement sta=null;
        try {
            //预编译sql语句
            sta = connection.prepareStatement(SQL_FIND_BY_ID);
            //填充参数
            sta.setString(1,id);
            //执行sql语句
            resultSet = sta.executeQuery();
            //获取执行结果
            if(resultSet.next())
            {
                int intId= Integer.parseInt(id);
                String name = resultSet.getString("name");
                String phone=resultSet.getString("phone");
                String sex = resultSet.getString("sex");
                String address = resultSet.getString("address");
                User user = new User(intId,name,phone,sex,address);
                return user;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //资源的释放
        finally {
            DruidUtil.close(connection,sta,resultSet);
        }
        return null;
    }

    @Override
    public List<User> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<User> data=new ArrayList<>();
        //获取数据库的连接
        Connection connection = DruidUtil.getConnection();
        ResultSet resultSet=null;
        PreparedStatement sta=null;
        try {
            //预编译sql语句
            if(limit) {
                sta = connection.prepareStatement(SQL_FIND_LIMIT);//分页
                sta.setInt(1,offset);
                sta.setInt(2,pageNumber);
            }
            else {
                sta = connection.prepareStatement(SQL_FIND_ALL);
            }
            //填充参数
            //执行sql语句
             resultSet = sta.executeQuery();
            //获取执行结果
            while (resultSet.next())
            {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                String sex = resultSet.getString("sex");
                String address = resultSet.getString("address");
                User user = new User(id,name,phone,sex,address);
                data.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //资源的释放
        finally {
            DruidUtil.close(connection,sta,resultSet);
        }
        return  data;
    }

    @Override
    public User findByPhone(String phone) {
        //获取数据库的连接
        Connection connection = DruidUtil.getConnection();
        ResultSet resultSet=null;
        PreparedStatement sta=null;
        try {
            //预编译sql语句
            sta = connection.prepareStatement(SQL_FIND_BY_PHONE);
            //填充参数
            sta.setString(1,phone);
            //执行sql语句
            resultSet = sta.executeQuery();
            //获取执行结果
            if(resultSet.next())
            {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String sex = resultSet.getString("sex");
                String address = resultSet.getString("address");
                User user = new User(id,name,phone,sex,address);
                return user;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //资源的释放
        finally {
            DruidUtil.close(connection,sta,resultSet);
        }
        return null;
    }

    @Override
    public boolean insert(User c) throws DuplicateCodeException {
        //获取数据库的连接
        Connection connection = DruidUtil.getConnection();
        PreparedStatement sta=null;
        try {
            //预编译sql语句
            sta = connection.prepareStatement(SQL_INSERT);
            //填充参数
            sta.setInt(1,c.getId());
            sta.setString(2,c.getName());
            sta.setString(3,c.getPhone());
            sta.setString(4,c.getSex());
            sta.setString(5,c.getAddress());
            //执行sql语句+
            //获取执行结果
            return sta.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //资源的释放
        finally {
            DruidUtil.close(connection,sta,null);
        }
        return false;
    }

    @Override
    public boolean update(int id, User newUser) {
        Connection connection = DruidUtil.getConnection();
        PreparedStatement sta=null;
        try {
            //预编译sql语句
            sta = connection.prepareStatement(SQL_UPDATE);
            sta.setString(1,newUser.getName());
            sta.setString(2,newUser.getPhone());
            sta.setString(3,newUser.getSex());
            sta.setString(4,newUser.getAddress());
            sta.setInt(5,id);
            return sta.executeUpdate()>0?true:false;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //资源的释放
        finally {
            DruidUtil.close(connection,sta,null);
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        //获取数据库的连接
        Connection connection = DruidUtil.getConnection();
        PreparedStatement sta=null;
        try {
            //预编译sql语句
            sta = connection.prepareStatement(SQL_DELETE);
            sta.setInt(1,id);
            return sta.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //资源的释放
        finally {
            DruidUtil.close(connection,sta, null);
        }
        return false;
    }
}