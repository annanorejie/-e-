package com.company.dao.impl;

import com.company.bean.Company;
import com.company.dao.CompanyDao;
import com.express.util.DruidUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyImpl implements CompanyDao {
    //用于查询数据库中的所有快递信息
    public static final String SQL_FIND_ALL = "SELECT * FROM company";
    //通过快递公司名称查询公司信息
    public static final String SQL_FIND_BY_NAME = "SELECT * FROM company WHERE name=?";
    //通过快递公司编号查询公司信息
    public static final String SQL_FIND_BY_ID = "SELECT * FROM company WHERE id=?";
    //通过快递公司电话查询快递公司信息
    public static final String SQL_FIND_BY_PHONE = "SELECT * FROM company WHERE phone=?";
    //录入快递公司信息
    public static final String SQL_INSERT = "INSERT INTO company (id,name,address,phone,owner) VALUES(?,?,?,?,?)";
    //快递公司信息修改
    public static final String SQL_UPDATE = "UPDATE company SET name=?,address=?,phone=?,owner=? WHERE ID=?";
    //快递公司信息的删除
    public static final String SQL_DELETE = "DELETE FROM company WHERE ID=?";
    //用于分页查询数据库中的快递信息
    public static final String SQL_FIND_LIMIT = "SELECT * FROM company LIMIT ?,?";
    //用于查询数据库中的全部公司数
    public static final String SQL_CONSOLE = "SELECT COUNT(ID) data1_size FROM company";
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
    public List<Company> findAll(boolean limit, int offset, int pageNumber){
        Connection connection= DruidUtil.getConnection();
        PreparedStatement pre=null;
        ResultSet res=null;
        ArrayList<Company> list=new ArrayList<>();
        try {
            if(limit) {
                pre=connection.prepareStatement(SQL_FIND_LIMIT);
                pre.setInt(1,offset);
                pre.setInt(2,pageNumber);
            }else {
                pre=connection.prepareStatement(SQL_FIND_ALL);
            }
            res=pre.executeQuery();
            while (res.next())
            {
                Company company=new Company();
                String id=res.getString("id");
                String name = res.getString("name");
                String address = res.getString("address");
                String phone = res.getString("phone");
                String owner = res.getString("owner");
                company.setId(id);
                company.setName(name);
                company.setAddress(address);
                company.setPhone(phone);
                company.setOwner(owner);
                list.add(company);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            DruidUtil.close(connection,pre,res);
        }
        return list;
    }

    @Override
    public boolean delete(String id) {
        Connection connection= DruidUtil.getConnection();
        PreparedStatement pre=null;
        ResultSet res=null;
        try {
            pre=connection.prepareStatement(SQL_DELETE);
            pre.setString(1,id);
            return pre.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            DruidUtil.close(connection,pre,res);
        }
        return false;
    }

    @Override
    public boolean insert(Company company) {
        Connection connection= DruidUtil.getConnection();
        PreparedStatement pre=null;
        ResultSet res=null;
        try {
            String id=company.getId();
            String name = company.getName();
            String address = company.getAddress();
            String phone = company.getPhone();
            String owner = company.getOwner();
            pre=connection.prepareStatement(SQL_INSERT);
            pre.setString(1,id);
            pre.setString(2,name);
            pre.setString(3,address);
            pre.setString(4,phone);
            pre.setString(5,owner);
            return pre.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            DruidUtil.close(connection,pre,res);
        }
        return false;
    }

    @Override
    public Company selectByPhone(String phone) {
        Connection connection= DruidUtil.getConnection();
        PreparedStatement pre=null;
        ResultSet res=null;
        try {
            pre=connection.prepareStatement(SQL_FIND_BY_PHONE);
            pre.setString(1,phone);
            res = pre.executeQuery();
            if(res.next())
            {
                Company company=new Company();
                String id=res.getString("id");
                String name = res.getString("name");
                String address = res.getString("address");
                String owner = res.getString("owner");
                company.setId(id);
                company.setName(name);
                company.setAddress(address);
                company.setPhone(phone);
                company.setOwner(owner);
                return company;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            DruidUtil.close(connection,pre,res);
        }
        return null;
    }

    @Override
    public Company selectByName(String name) {
        Connection connection= DruidUtil.getConnection();
        PreparedStatement pre=null;
        ResultSet res=null;
        try {
            pre=connection.prepareStatement(SQL_FIND_BY_NAME);
            pre.setString(1,name);
            res = pre.executeQuery();
            while(res.next())
            {
                Company company=new Company();
                String id=res.getString("id");
                String address = res.getString("address");
                String phone=res.getString("phone");
                String owner = res.getString("owner");
                company.setId(id);
                company.setName(name);
                company.setAddress(address);
                company.setPhone(phone);
                company.setOwner(owner);
                System.out.println(company);
                return company;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            DruidUtil.close(connection,pre,res);
        }
        return null;
    }

    @Override
    public boolean updateInformation(String id,Company company) {
        Connection connection= DruidUtil.getConnection();
        PreparedStatement pre=null;
        ResultSet res=null;
        try {
            String name = company.getName();
            String address = company.getAddress();
            String phone = company.getPhone();
            String owner = company.getOwner();
            pre=connection.prepareStatement(SQL_UPDATE);
            pre.setString(1,name);
            pre.setString(2,address);
            pre.setString(3,phone);
            pre.setString(4,owner);
            pre.setString(5,id);
            return pre.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            DruidUtil.close(connection,pre,res);
        }
        return false;
    }

    @Override
    public Company findById(String id) {
        Connection connection= DruidUtil.getConnection();
        PreparedStatement pre=null;
        ResultSet res=null;
        try {
            pre=connection.prepareStatement(SQL_FIND_BY_ID);
            pre.setString(1,id);
            res = pre.executeQuery();
            if(res.next())
            {
                Company company=new Company();
                String name=res.getString("name");
                String address = res.getString("address");
                String phone=res.getString("phone");
                String owner = res.getString("owner");
                company.setId(id);
                company.setName(name);
                company.setAddress(address);
                company.setPhone(phone);
                company.setOwner(owner);
                return company;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            DruidUtil.close(connection,pre,res);
        }
        return null;
    }
}
