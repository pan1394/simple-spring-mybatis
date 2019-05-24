package com.yilin.batis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.StringUtils;
import com.yilin.batis.domain.Dept;
import com.yilin.batis.mapper.DeptMapper;

@Component
@Transactional
public class DepartmentService {

	@Autowired
	private DeptMapper deptMapper;
	
	public void addDepartment(String dept) {
		if(StringUtils.isNullOrEmpty(dept)){
			throw new IllegalArgumentException("wrong arugments");
		}
		deptMapper.save(new Dept(dept));
		int i = 3/0;  //捕获运行时异常
	}
	
	public List<Dept> listAllDepartments(){ 
		return deptMapper.list();
	}
}
