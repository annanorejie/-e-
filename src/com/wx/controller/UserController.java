package com.wx.controller;

import com.courier.bean.Courier;
import com.courier.service.CourierService;
import com.express.bean.Message;
import com.express.bean.*;
import com.express.mvc.ResponseBody;
import com.express.util.JSONUtil;
import com.express.util.UserUtil;
import com.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserController {
    @ResponseBody("/wx/loginSms.do")
    public String sendSms(HttpServletRequest request, HttpServletResponse response) {
        String userPhone = request.getParameter("userPhone");
        String code = "123456";
        Message msg = new Message();
        //短信发送成功
        msg.setStatus(0);
        msg.setResult("验证码已发送,请查收!");
        UserUtil.setLoginSms(request.getSession(), userPhone, code);

        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/wx/login.do")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        String userPhone = request.getParameter("userPhone");
        String userCode = request.getParameter("code");
        String sysCode = UserUtil.getLoginSms(request.getSession(), userPhone);
        Message msg = new Message();
        if (sysCode == null) {
            msg.setStatus(-1);
            msg.setResult("手机号码未获取短信");
        } else if (sysCode.equals(userCode)) {
            User user = new User();
            Courier byPhone = CourierService.findByPhone(userPhone);
            com.user.bean.User byPhone1 = UserService.findByPhone(userPhone);
            if (byPhone != null) {
                //快递员
                msg.setStatus(1);
                user.setUser(false);
            } else if (byPhone1 != null) {
                //用户
                msg.setStatus(0);
                user.setUser(true);
            } else {
                msg.setStatus(-3);
                msg.setResult("目前所录入的信息中没有查询到该用户");
            }
            user.setUserPhone(userPhone);
            UserUtil.setWxUser(request.getSession(), user);
        } else {
            //这里是验证码不一致 , 登陆失败
            msg.setStatus(-2);
            msg.setResult("验证码不一致,请检查");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/wx/userInfo.do")
    public String userInfo(HttpServletRequest request, HttpServletResponse response) {
        User user = UserUtil.getWxUser(request.getSession());
        boolean isUser = user.isUser();
        Message msg = new Message();
        if (isUser)
            msg.setStatus(0);
        else
            msg.setStatus(1);
        msg.setResult(user.getUserPhone());
        String json = JSONUtil.toJSON(msg);
        return json;
    }


    @ResponseBody("/wx/logout.do")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        //1.销毁session
        request.getSession().invalidate();
        //2.给客户端回复消息
        Message msg = new Message(0);
        return JSONUtil.toJSON(msg);
    }
}
