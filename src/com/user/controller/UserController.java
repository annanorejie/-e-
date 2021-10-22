package com.user.controller;

import com.express.bean.BootStrapTableExpress;
import com.express.bean.Express;
import com.express.bean.Message;
import com.express.bean.ResultData;
import com.express.mvc.ResponseBody;
import com.express.service.ExpressService;
import com.express.util.JSONUtil;
import com.user.bean.User;
import com.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class UserController {
    @ResponseBody("/user/console.do")
    public String console(HttpServletRequest request, HttpServletResponse response)
    {
        List<Map<String, Integer>> data = ExpressService.console();
        Message msg=new Message();
        msg.setData(data);
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/user/list.do")
    public String list(HttpServletRequest request, HttpServletResponse response)
    {
        //获取起始索引 从第几个开始查
        int offset = Integer.parseInt(request.getParameter("offset"));
        //获取当前页要查询的数据量 显示几个
        int pangNumber= Integer.parseInt(request.getParameter("pageNumber"));
        //进行查询
        List<User> list = UserService.findAll(true, offset, pangNumber);
        List<Map<String, Integer>> console = UserService.console();
        Integer total=console.get(0).get("data1_size");
        //将集合封装为bootstrap-table识别的格式
        ResultData<User> data=new ResultData<>();
        data.setRows(list);
        data.setTotal(total);

        String json=JSONUtil.toJSON(data);
        System.out.println(json);
        return json;
    }
    @ResponseBody("/user/insert.do")
    public String insert(HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String sex = request.getParameter("sex");
        String address=request.getParameter("address");
        User e = new User(id,name,phone,sex,address);
        boolean flag = UserService.insert(e);
        System.out.println(id+"用户录入成功!");
        String json = JSONUtil.toJSON(flag);
        System.out.println(json);
        return json;
    }
    @ResponseBody("/user/find.do")
    public String find(HttpServletRequest request,HttpServletResponse response){
        String id = request.getParameter("id");
        System.out.println(id);
        User e = UserService.findById(id);
        if(e == null){
            System.out.println(e);
            System.out.println("用户不存在");
        }else{
            System.out.println(e);
            System.out.println("用户存在");
        }
        String json = JSONUtil.toJSON(e);
        return json;
    }
    @ResponseBody("/user/update.do")
    public String update(HttpServletRequest request,HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String sex = request.getParameter("sex");
        String address=request.getParameter("address");
        User e = new User(id,name,phone,sex,address);
        boolean flag = UserService.update(id, e);
        if(flag){
            System.out.println("修改成功");
        }else{
            System.out.println("修改失败");
        }
        String json = JSONUtil.toJSON(flag);
        return json;
    }

    @ResponseBody("/user/delete.do")
    public String delete(HttpServletRequest request,HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        boolean flag = UserService.delete(id);
        String json = JSONUtil.toJSON(flag);
        System.out.println(json);
        return json;
    }
}
