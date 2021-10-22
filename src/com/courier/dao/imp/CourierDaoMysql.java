package com.courier.dao.imp;

import com.courier.bean.Courier;
import com.courier.dao.BaseCourierDao;
import com.express.exception.DuplicateCodeException;
import com.express.util.DruidUtil;
import com.user.bean.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourierDaoMysql implements BaseCourierDao {
    //用于查询数据库中的全部用户人数
    public static final String SQL_CONSOLE = "SELECT COUNT(ID) data1_size FROM COURIER";
    //用于查询数据库中的所有快递信息
    public static final String SQL_FIND_ALL = "SELECT * FROM COURIER";
    //用于分页查询数据库中的快递信息
    public static final String SQL_FIND_LIMIT = "SELECT * FROM COURIER LIMIT ?,?";
    //通过手机号查询快递信息
    public static final String SQL_FIND_BY_PHONE = "SELECT * FROM COURIER WHERE PHONE=?";
    //录入快递
    public static final String SQL_INSERT = "INSERT INTO `courier` VALUES(?,?,?,?,?)";
    //快递修改
    public static final String SQL_UPDATE = "UPDATE COURIER SET NAME=?,express=?,SEX=?,PHONE=? WHERE ID=?";
    //快递的删除
    public static final String SQL_DELETE = "DELETE FROM COURIER WHERE ID=?";
    //通过id查询用户
    public static final String SQL_FIND_BY_ID="SELECT * FROM COURIER WHERE ID=?";

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
    public Courier findById(String id) {
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
                String express=resultSet.getString("express");
                String sex = resultSet.getString("sex");
                String phone = resultSet.getString("phone");
                Courier courier = new Courier(intId,name,express,sex,phone);
                return courier;
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
    public List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<Courier> data=new ArrayList<>();
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
                int intId= Integer.parseInt(resultSet.getString("id"));
                String name = resultSet.getString("name");
                String express=resultSet.getString("express");
                String sex = resultSet.getString("sex");
                String phone = resultSet.getString("phone");
                Courier courier = new Courier(intId,name,express,sex,phone);
                data.add(courier);
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
    public Courier findByPhone(String phone) {
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
                int intId= Integer.parseInt(resultSet.getString("id"));
                String name = resultSet.getString("name");
                String express=resultSet.getString("express");
                String sex = resultSet.getString("sex");
                Courier courier = new Courier(intId,name,express,sex,phone);
                return courier;
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
    public boolean insert(Courier c) throws DuplicateCodeException {
        //获取数据库的连接
        Connection connection = DruidUtil.getConnection();
        PreparedStatement sta=null;
        try {
            //预编译sql语句
            sta = connection.prepareStatement(SQL_INSERT);
            //填充参数
            sta.setInt(1,c.getId());
            sta.setString(2,c.getName());
            sta.setString(3,c.getExpress());
            sta.setString(4,c.getSex());
            sta.setString(5,c.getPhone());
            //执行sql语句+获取执行结果
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
    public boolean update(int id, Courier newCourier) {
        Connection connection = DruidUtil.getConnection();
        ResultSet resultSet=null;
        PreparedStatement sta=null;
        try {
            //预编译sql语句
            sta = connection.prepareStatement(SQL_UPDATE);
            sta.setString(1,newCourier.getName());
            sta.setString(2,newCourier.getExpress());
            sta.setString(3,newCourier.getSex());
            sta.setString(4, newCourier.getPhone());
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