package com.express.controller;

import com.express.bean.Message;
import com.express.mvc.ResponseBody;
import com.express.service.AdminService;
import com.express.util.JSONUtil;

import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class AdminController {
    @ResponseBody("/admin/login.do")
    public String login(HttpServletRequest request, HttpServletResponse response)
    {
        //接收参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //调用service传递参数，并获取结果
        boolean result = AdminService.login(username, password);
        //根据结果返回不同的数据
        Message msg=null;
        if(result)
        {
            msg=new Message(0,"登录成功");
            //登陆时间和ip的更新
            Date date=new Date();
            String ip=request.getRemoteAddr();//获取IP地址
            AdminService.updateLoginTimeAndIP(username,date,ip);
        }
        else
        {
            msg=new Message(-1,"登录失败");
        }
        //将数据转换为json字符串
        String json = JSONUtil.toJSON(msg);
        //将json字符串返回给ajax
        return json;
    }
}
