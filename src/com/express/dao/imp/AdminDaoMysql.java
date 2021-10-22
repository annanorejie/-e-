package com.express.dao.imp;

import com.express.dao.BaseAdminDao;
import com.express.util.DruidUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class AdminDaoMysql implements BaseAdminDao {
    private static final String SQL_UPDATE_LOGIN_TIME = "UPDATE EADMIN SET LOGINTIME=?,LOGINIP=? WHERE USERNAME=? ";
    private static final String SQL_LOGIN = "SELECT ID FROM EADMIN WHERE USERNAME=? AND PASSWORD=?";

    @Override
    public void updateLoginTime(String username, Date date, String ip) {
        //1.获取连接
        Connection conn= DruidUtil.getConnection();
        //2.预编译SQL语句
        PreparedStatement state=null;
        try {
            state = conn.prepareStatement(SQL_UPDATE_LOGIN_TIME);
            //3.填充参数
            state.setDate(1,new java.sql.Date(date.getTime()));//封装转换过程
            state.setString(2,ip);
            state.setString(3,username);
            //4.执行语句
            state.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            //5.释放资源
            DruidUtil.close(conn,state,null);
        }

    }

    @Override
    public boolean login(String username, String password) {
        Connection conn= DruidUtil.getConnection();
        //2.预编译SQL语句
        PreparedStatement state=null;
        ResultSet rs=null;
        try {
            state = conn.prepareStatement(SQL_LOGIN);
            //3.填充参数
            state.setString(1,username);
            state.setString(2,password);
            //4.执行语句
            rs = state.executeQuery();
            //5.根据查询结果返回true或者false
            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            //6.释放资源
            DruidUtil.close(conn,state,rs);
        }
        return false;
    }
}
