package com.company.service;

import com.company.bean.Company;
import com.company.dao.CompanyDao;
import com.company.dao.impl.CompanyImpl;

import java.util.List;
import java.util.Map;

public class CompanyService {
    CompanyDao companyImpl = new CompanyImpl();

    public List<Map<String, Integer>> console() {
        return companyImpl.console();
    }

    public List<Company> findAll(boolean limit, int offset, int pageNumber) {
        return companyImpl.findAll(limit, offset, pageNumber);
    }

    public boolean delete(String id) {
        return companyImpl.delete(id);
    }

    public boolean insert(Company company) {
        return companyImpl.insert(company);
    }

    public boolean update(String id, Company company) {
        return companyImpl.updateInformation(id, company);
    }

    public Company findByPhone(String phone) {
        return companyImpl.selectByPhone(phone);
    }

    public Company findByName(String name) {
        return companyImpl.selectByName(name);
    }

    public Company findById(String id) {
        return companyImpl.findById(id);
    }
}
