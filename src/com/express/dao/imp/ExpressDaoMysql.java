package com.express.dao.imp;

import com.company.service.CompanyService;
import com.express.bean.Express;
import com.express.dao.BaseExpressDao;
import com.express.exception.DuplicateCodeException;
import com.express.util.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressDaoMysql implements BaseExpressDao {
    //用于查询数据库中的全部快递（总数+新增），待取件快递（总数+新增）
    public static final String SQL_CONSOLE = "SELECT " +
            "COUNT(ID) data1_size," +
            "COUNT(TO_DAYS(INTIME)=TO_DAYS(NOW()) OR NULL) data1_day," +
            "COUNT(STATUS=0 OR NULL) data2_size," +
            "COUNT(TO_DAYS(INTIME)=TO_DAYS(NOW()) AND STATUS=0 OR NULL) data2_day" +
            " FROM EXPRESS";
    //用于查询数据库中的所有快递信息
    public static final String SQL_FIND_ALL = "SELECT * FROM express";
    //用于分页查询数据库中的快递信息
    public static final String SQL_FIND_LIMIT = "SELECT * FROM express LIMIT ?,?";
    //通过取件码查询快递信息
    public static final String SQL_FIND_BY_CODE = "SELECT * FROM express WHERE CODE=?";
    //通过快递单号查询快递信息
    public static final String SQL_FIND_BY_NUMBER = "SELECT * FROM express WHERE NUMBER=?";
    //通过录入人手机号查询快递信息
    public static final String SQL_FIND_BY_SYSPHONE = "SELECT * FROM express WHERE SYSPHONE=?";
    //通过用户手机号查询用户所有快递
    public static final String SQL_FIND_BY_USERPHONE = "SELECT * FROM express WHERE USERPHONE=?";
    //录入快递
    public static final String SQL_INSERT = "INSERT INTO express (NUMBER,USERNAME,USERPHONE,COMPANY,CODE,INTIME,STATUS,SYSPHONE) VALUES(?,?,?,?,?,NOW(),0,?)";
    //快递修改
    public static final String SQL_UPDATE = "UPDATE express SET NUMBER=?,USERNAME=?,COMPANY=?,STATUS=? WHERE ID=?";
    //快递的状态码改变（取件）
    public static final String SQL_UPDATE_STATUS = "UPDATE express SET STATUS=1,OUTTIME=NOW(),CODE=NULL WHERE CODE=?";
    //快递的删除
    public static final String SQL_DELETE = "DELETE FROM express WHERE ID=?";
    //通过用户手机号查询用户所有快递
    public static final String SQL_FIND_BY_USERPHONE_AND_STATUS = "SELECT * FROM EXPRESS WHERE USERPHONE=? AND STATUS=?";


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
                int data1_day = resultSet.getInt("data1_day");
                int data2_size = resultSet.getInt("data2_size");
                int data2_day = resultSet.getInt("data2_day");
                Map data1 = new HashMap();
                data1.put("data1_size",data1_size);
                data1.put("data1_day",data1_day);
                Map data2 = new HashMap();
                data2.put("data2_size",data2_size);
                data2.put("data2_day",data2_day);
                data.add(data1);
                data.add(data2);
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
    public List<Express> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<Express> data=new ArrayList<>();
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
                String number = resultSet.getString("number");
                String username = resultSet.getString("username");
                String userPhone = resultSet.getString("userPhone");
                String company = resultSet.getString("company");
                String code = resultSet.getString("code");
                Timestamp inTime = resultSet.getTimestamp("inTime");
                Timestamp outTime = resultSet.getTimestamp("outTime");
                int status = resultSet.getInt("status");
                String sysPhone = resultSet.getString("sysPhone");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                data.add(e);
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
    public Express findByNumber(String number) {
        //获取数据库的连接
        Connection connection = DruidUtil.getConnection();
        ResultSet resultSet=null;
        PreparedStatement sta=null;
        try {
            //预编译sql语句
            sta = connection.prepareStatement(SQL_FIND_BY_NUMBER);
            //填充参数
            sta.setString(1,number);
            //执行sql语句
            resultSet = sta.executeQuery();
            //获取执行结果
            if(resultSet.next())
            {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String userPhone = resultSet.getString("userPhone");
                String company = resultSet.getString("company");
                String code = resultSet.getString("code");
                Timestamp inTime = resultSet.getTimestamp("inTime");
                Timestamp outTime = resultSet.getTimestamp("outTime");
                int status = resultSet.getInt("status");
                String sysPhone = resultSet.getString("sysPhone");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                return e;
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
    public Express findByCode(String code) {
        //获取数据库的连接
        Connection connection = DruidUtil.getConnection();
        ResultSet resultSet=null;
        PreparedStatement sta=null;
        try {
            //预编译sql语句
            sta = connection.prepareStatement(SQL_FIND_BY_CODE);
            //填充参数

            sta.setString(1,code);
            //执行sql语句
            resultSet = sta.executeQuery();
            //获取执行结果

            if(resultSet.next())
            {
                int id = resultSet.getInt("id");
                String number = resultSet.getString("number");
                String username = resultSet.getString("username");
                String userPhone = resultSet.getString("userPhone");
                String company = resultSet.getString("company");
                Timestamp inTime = resultSet.getTimestamp("inTime");
                Timestamp outTime = resultSet.getTimestamp("outTime");
                int status = resultSet.getInt("status");
                String sysPhone = resultSet.getString("sysPhone");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                return e;
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
    public List<Express> findByUserPhone(String phoneNumber) {
        ArrayList<Express> data = new ArrayList<>();
        //获取数据库的连接
        Connection connection = DruidUtil.getConnection();
        ResultSet resultSet=null;
        PreparedStatement sta=null;
        try {
            //预编译sql语句
            sta = connection.prepareStatement(SQL_FIND_BY_USERPHONE);
            //填充参数
            //执行sql语句
            sta.setString(1,phoneNumber);
            resultSet = sta.executeQuery();
            //获取执行结果
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String number = resultSet.getString("number");
                String username = resultSet.getString("username");
                String userPhone = resultSet.getString("userPhone");
                String company = resultSet.getString("company");
                String code = resultSet.getString("code");
                Timestamp inTime = resultSet.getTimestamp("inTime");
                Timestamp outTime = resultSet.getTimestamp("outTime");
                int status = resultSet.getInt("status");
                String sysPhone = resultSet.getString("sysPhone");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                data.add(e);
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
    public List<Express> findBySysPhone(String sysPhone) {
        ArrayList<Express> data = new ArrayList<>();
        //获取数据库的连接
        Connection connection = DruidUtil.getConnection();
        ResultSet resultSet=null;
        PreparedStatement sta=null;
        try {
            //预编译sql语句
            sta = connection.prepareStatement(SQL_FIND_BY_SYSPHONE);
            //填充参数
            sta.setString(1,sysPhone);
            //执行sql语句
            resultSet = sta.executeQuery();
            //获取执行结果
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String number = resultSet.getString("number");
                String username = resultSet.getString("username");
                String userPhone = resultSet.getString("userPhone");
                String company = resultSet.getString("company");
                String code = resultSet.getString("code");
                Timestamp inTime = resultSet.getTimestamp("inTime");
                Timestamp outTime = resultSet.getTimestamp("outTime");
                int status = resultSet.getInt("status");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                data.add(e);
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
    public boolean insert(Express e) throws DuplicateCodeException {
        //获取数据库的连接
        CompanyService companyService=new CompanyService();
        String companyName=e.getCompany();
        System.out.println("xinwei "+companyService.findByName(companyName));
        if(companyService.findByName(companyName)==null){
            System.out.println("未查询到该快公司");
            return false;
        }
        else {
            Connection connection = DruidUtil.getConnection();
            PreparedStatement sta=null;
            try {
                //预编译sql语句
                sta = connection.prepareStatement(SQL_INSERT);
                //填充参数
                sta.setString(1,e.getNumber());
                sta.setString(2,e.getUsername());
                sta.setString(3,e.getUserPhone());
                sta.setString(4,e.getCompany());
                sta.setString(5,e.getCode());
                sta.setString(6,e.getSysPhone());
                //执行sql语句+
                //获取执行结果
                return sta.executeUpdate()>0?true:false;
            } catch (SQLException e1) {
                if(e1.getMessage().endsWith("for key 'code'")){
                    //是因为取件码重复,而出现了异常
                    DuplicateCodeException e2 = new DuplicateCodeException(e1.getMessage());
                    throw e2;
                }else{
                    e1.printStackTrace();
                }
            }
            //资源的释放
            finally {
                DruidUtil.close(connection,sta,null);
            }
            return false;
        }
    }

    @Override
    public boolean update(int id, Express newExpress) {
        //获取数据库的连接
        Connection connection = DruidUtil.getConnection();
        PreparedStatement sta=null;
        try {
            //预编译sql语句
            sta = connection.prepareStatement(SQL_UPDATE);
            sta.setString(1,newExpress.getNumber());
            sta.setString(2,newExpress.getUsername());
            sta.setString(3,newExpress.getCompany());
            sta.setInt(4,newExpress.getStatus());
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
    public boolean updateStatus(String code) {
        //获取数据库的连接
        Connection connection = DruidUtil.getConnection();
        ResultSet resultSet=null;
        PreparedStatement sta=null;
        try {
            //预编译sql语句
            sta = connection.prepareStatement(SQL_UPDATE_STATUS);
            sta.setString(1,code);
            return sta.executeUpdate()>0?true:false;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //资源的释放
        finally {
            DruidUtil.close(connection,sta,resultSet);
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        //获取数据库的连接
        Connection connection = DruidUtil.getConnection();
        ResultSet resultSet=null;
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
            DruidUtil.close(connection,sta,resultSet);
        }
        return false;
    }
    @Override
    public List<Express> findByUserPhoneAndStatus(String userPhone, int status) {
        ArrayList<Express> data = new ArrayList<>();
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        //2.    预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_FIND_BY_USERPHONE_AND_STATUS);
            //3.    填充参数(可选)
            state.setString(1,userPhone);
            state.setInt(2,status);
            //4.    执行SQL语句
            result = state.executeQuery();
            //5.    获取执行的结果
            while(result.next()){
                int id = result.getInt("id");
                String number = result.getString("number");
                String username = result.getString("username");
                String company = result.getString("company");
                String code = result.getString("code");
                Timestamp inTime = result.getTimestamp("inTime");
                Timestamp outTime = result.getTimestamp("outTime");
                String sysPhone = result.getString("sysPhone");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                data.add(e);
            }
            //6.    资源的释放
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,result);
        }
        return data;
    }
}