package com.courier.controller;

import com.courier.bean.Courier;
import com.courier.service.CourierService;
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

public class CourierController {
    @ResponseBody("/courier/console.do")
    public String console(HttpServletRequest request, HttpServletResponse response)
    {
        List<Map<String, Integer>> data = ExpressService.console();
        Message msg=new Message();
        msg.setData(data);
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/courier/list.do")
    public String list(HttpServletRequest request, HttpServletResponse response)
    {
        //获取起始索引 从第几个开始查
        int offset = Integer.parseInt(request.getParameter("offset"));
        //获取当前页要查询的数据量 显示几个
        int pangNumber= Integer.parseInt(request.getParameter("pageNumber"));
        //进行查询
        List<Courier> list = CourierService.findAll(true, offset, pangNumber);
        List<Map<String, Integer>> console = CourierService.console();
        Integer total=console.get(0).get("data1_size");
        //将集合封装为bootstrap-table识别的格式
        ResultData<Courier> data=new ResultData<>();
        data.setRows(list);
        data.setTotal(total);
        String json=JSONUtil.toJSON(data);
        System.out.println(json);
        return json;
    }
    @ResponseBody("/courier/insert.do")
    public String insert(HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String sex = request.getParameter("sex");
        String express=request.getParameter("express");
        Courier courier = new Courier(id,name,express,sex,phone);
        boolean flag = CourierService.insert(courier);
        if(flag)
        {
            System.out.println("用户录入成功!");
        }
        else
        {
            System.out.println("用户录入失败");
        }
        String json = JSONUtil.toJSON(courier);
        return json;
    }
    @ResponseBody("/courier/find.do")
    public String find(HttpServletRequest request,HttpServletResponse response){
        String id = request.getParameter("id");
        System.out.println(id);
        Courier courier = CourierService.findById(id);
        if(courier == null){
            System.out.println(courier);
            System.out.println("用户不存在");
        }else{
            System.out.println(courier);
            System.out.println("用户存在");
        }
        String json = JSONUtil.toJSON(courier);
        return json;
    }
    @ResponseBody("/courier/update.do")
    public String update(HttpServletRequest request,HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String sex = request.getParameter("sex");
        String express=request.getParameter("express");
        Courier courier = new Courier(id,name,express,sex,phone);
        boolean flag = CourierService.update(id, courier);
        if(flag){
            System.out.println("修改成功");
        }else{
            System.out.println("修改失败");
        }
        String json = JSONUtil.toJSON(flag);
        return json;
    }

    @ResponseBody("/courier/delete.do")
    public String delete(HttpServletRequest request,HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        boolean flag = CourierService.delete(id);
        String json = JSONUtil.toJSON(flag);
        System.out.println(json);
        return json;
    }
}
