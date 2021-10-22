package com.express.util;

import com.express.bean.User;

import javax.servlet.http.HttpSession;

public class UserUtil {
    public static String getUserName(HttpSession session){
        return (String) session.getAttribute("adminUserName");
    }
    public static String getUserPhone(HttpSession session){
        // TODO : 还没有编写柜子端,未存储任何的录入人信息
        return "18888888888";
    }
    public static String getLoginSms(HttpSession session,String userPhone){
        return (String) session.getAttribute(userPhone);
    }
    public static void setLoginSms(HttpSession session,String userPhone,String code){
        session.setAttribute(userPhone,code);
    }
    public static void setWxUser(HttpSession session, User user){
        session.setAttribute("wxUser",user);
    }
    public static User getWxUser(HttpSession session){
        return (User) session.getAttribute("wxUser");
    }
}
