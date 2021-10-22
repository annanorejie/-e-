package com.wx.controller;

import com.express.bean.BootStrapTableExpress;
import com.express.bean.Express;
import com.express.bean.Message;
import com.express.bean.User;
import com.express.mvc.ResponseBody;
import com.express.service.ExpressService;
import com.express.util.DateFormatUtil;
import com.express.util.JSONUtil;
import com.express.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ExpressController {
    @ResponseBody("/wx/findExpressByUserPhone.do")
    public String findByUserPhone(HttpServletRequest request, HttpServletResponse response) {
        User wxUser = UserUtil.getWxUser(request.getSession());
        String userPhone = wxUser.getUserPhone();
        List<Express> list = ExpressService.findByUserPhone(userPhone);
        List<BootStrapTableExpress> list2 = new ArrayList<>();
        for (Express e : list) {
            String inTime = DateFormatUtil.format(e.getInTime());
            String outTime = e.getOutTime() == null ? "未出库" : DateFormatUtil.format(e.getOutTime());
            String status = e.getStatus() == 0 ? "待取件" : "已取件";
            String code = e.getCode() == null ? "已取件" : e.getCode();
            BootStrapTableExpress e2 = new BootStrapTableExpress(e.getId(), e.getNumber(), e.getUsername(), e.getUserPhone(), e.getCompany(), code, inTime, outTime, status, e.getSysPhone());
            list2.add(e2);
        }
        Message msg = new Message();
        if (list.size() == 0) {
            msg.setStatus(-1);
        } else {
            msg.setStatus(0);
            Stream<BootStrapTableExpress> status0Express = list2.stream().filter(express -> {//用流的方式遍历数组，同时添加过滤器
                if (express.getStatus().equals("待取件")) {
                    return true;
                } else {
                    return false;
                }
            }).sorted((o1, o2) -> {//对时间进行排序
                long o1Time = DateFormatUtil.toTime(o1.getInTime());
                long o2Time = DateFormatUtil.toTime(o2.getInTime());
                return (int) (o1Time - o2Time);
            });
            Stream<BootStrapTableExpress> status1Express = list2.stream().filter(express -> {
                if (express.getStatus().equals("已取件")) {
                    return true;
                } else {
                    return false;
                }
            }).sorted((o1, o2) -> {
                long o1Time = DateFormatUtil.toTime(o1.getInTime());
                long o2Time = DateFormatUtil.toTime(o2.getInTime());
                return (int) (o1Time - o2Time);
            });
            Object[] s0 = status0Express.toArray();
            Object[] s1 = status1Express.toArray();
            Map data = new HashMap<>();
            data.put("status0", s0);
            data.put("status1", s1);
            msg.setData(data);
        }
        String json = JSONUtil.toJSON(msg.getData());
        return json;
    }

    @ResponseBody("/wx/findExpressByExpressNum.do")
    public String findByEXpressNum(HttpServletRequest request, HttpServletResponse response) {
        Message msg = new Message();
        String ExpressNum=request.getParameter("ExpressNum");
        System.out.println(ExpressNum);
        Express e=ExpressService.findByNumber(ExpressNum);
        System.out.println(e);
        if(e==null)
        {
            msg.setStatus(-1);
            msg.setResult("未查询到快递");
        } else {
            String inTime = DateFormatUtil.format(e.getInTime());
            String outTime = e.getOutTime() == null ? "未出库" : DateFormatUtil.format(e.getOutTime());
            String status = e.getStatus() == 0 ? "待取件" : "已取件";
            String code = e.getCode() == null ? "已取件" : e.getCode();
            BootStrapTableExpress e2 = new BootStrapTableExpress(e.getId(), e.getNumber(), e.getUsername(), e.getUserPhone(), e.getCompany(), code, inTime, outTime, status, e.getSysPhone());
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(e2);
        }
        System.out.println(msg.getData());
        return JSONUtil.toJSON(msg);
    }
    @ResponseBody("/wx/userExpressList.do")
    public String expressList(HttpServletRequest request, HttpServletResponse response) {
        String userPhone = request.getParameter("userPhone");
        List<Express> list = ExpressService.findByUserPhoneAndStatus(userPhone, 0);
        List<BootStrapTableExpress> list2 = new ArrayList<>();
        for (Express e : list) {
            String inTime = DateFormatUtil.format(e.getInTime());
            String outTime = e.getOutTime() == null ? "未出库" : DateFormatUtil.format(e.getOutTime());
            String status = e.getStatus() == 0 ? "待取件" : "已取件";
            String code = e.getCode() == null ? "已取件" : e.getCode();
            BootStrapTableExpress e2 = new BootStrapTableExpress(e.getId(), e.getNumber(), e.getUsername(), e.getUserPhone(), e.getCompany(), code, inTime, outTime, status, e.getSysPhone());
            list2.add(e2);
        }
        Message msg = new Message();
        if (list.size() == 0) {
            msg.setStatus(-1);
            msg.setResult("未查询到快递");
        } else {
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(list2);
        }
        return JSONUtil.toJSON(msg);
    }
}
