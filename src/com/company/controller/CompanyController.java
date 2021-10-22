package com.company.controller;

import com.company.bean.Company;
import com.company.service.CompanyService;
import com.courier.bean.Courier;
import com.courier.service.CourierService;
import com.express.bean.ResultData;
import com.express.mvc.ResponseBody;
import com.express.mvc.ResponseView;
import com.express.util.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class CompanyController {
    CompanyService companyService=new CompanyService();
    @ResponseBody("/company/list.do")
    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        int offset = Integer.parseInt(request.getParameter("offset"));
        //获取当前页要查询的数据量 显示几个
        int pangNumber = Integer.parseInt(request.getParameter("pageNumber"));
        //进行查询
        List<Company> list = companyService.findAll(true, offset, pangNumber);
        List<Map<String, Integer>> console = companyService.console();
        Integer total=console.get(0).get("data1_size");
        ResultData<Company> data=new ResultData<>();
        data.setRows(list);
        data.setTotal(total);
        String json=JSONUtil.toJSON(data);
        System.out.println(json);
        return json;
    }
    @ResponseBody("/company/delete.do")
    public String delete(HttpServletRequest request, HttpServletResponse response){
        String id=request.getParameter("id");
        boolean delete = companyService.delete(id);
        if(delete){
            System.out.println("删除成功");
        }else {
            System.out.println("删除失败");
        }
        String json= JSONUtil.toJSON(delete);
        return json;
    }
    @ResponseBody("/company/insert.do")
    public String insert(HttpServletRequest request, HttpServletResponse response){
        String id=request.getParameter("id");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String owner=request.getParameter("owner");
        Company company=new Company(id,name,address,phone,owner);
        Company byId = companyService.findById(id);
        if(byId==null){
            return JSONUtil.toJSON(false);
        }else {
            boolean insert = companyService.insert(company);
            return JSONUtil.toJSON(insert);
        }
    }
    @ResponseBody("/company/findByName.do")
    public String findByName(HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter("name");
        Company byName = companyService.findByName(name);
        if(byName==null){
            return JSONUtil.toJSON(false);
        }else {
            return JSONUtil.toJSON(byName);
        }
    }
    @ResponseBody("/company/findByPhone.do")
    public String findByPhone(HttpServletRequest request, HttpServletResponse response){
        String phone = request.getParameter("phone");
        Company byPhone = companyService.findByPhone(phone);
        if(byPhone==null){
            return JSONUtil.toJSON(false);
        }else {
            return JSONUtil.toJSON(byPhone);
        }
    }
    @ResponseBody("/company/updateInformation.do")
    public String updateInformation(HttpServletRequest request, HttpServletResponse response){
        String id=request.getParameter("id");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String owner=request.getParameter("owner");
        Company company=new Company(id,name,address,phone,owner);
        if(companyService.findById(id)==null){
            return JSONUtil.toJSON(false);
        }else {
            boolean update = companyService.update(id, company);
            return JSONUtil.toJSON(update);
        }
    }
}
