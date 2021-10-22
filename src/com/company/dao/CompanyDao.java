package com.company.dao;

import com.company.bean.Company;
import java.util.List;
import java.util.Map;

public interface CompanyDao {
    List<Company> findAll(boolean limit, int offset, int pageNumber);
    boolean delete(String id);
    List<Map<String, Integer>> console();
    boolean insert(Company company);
    Company selectByPhone(String phone);
    Company selectByName(String name);
    boolean updateInformation(String id,Company company);
    Company findById(String id);
}
