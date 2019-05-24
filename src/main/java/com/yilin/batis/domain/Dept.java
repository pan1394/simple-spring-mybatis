package com.yilin.batis.domain;

/**
 * Created by smlz on 2019/3/22.
 */
public class Dept {

    private Integer id;

    private String departmentName;

    
    public Dept(String departmentName) {
		this.departmentName = departmentName;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

	@Override
	public String toString() {
		return "Dept [id=" + id + ", departmentName=" + departmentName + "]";
	}
     
}
