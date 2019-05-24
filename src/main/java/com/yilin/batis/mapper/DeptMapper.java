package com.yilin.batis.mapper;

import java.util.List;

import com.yilin.batis.domain.Dept;

/**
 * 映射器文件xml需使用相同路径和文件名
 * com.yilin.batis.mapper.DeptMapper.java
 * com/yilin/batis/mapper/DeptMapper.xml
 * @author panyl
 *
 */
public interface DeptMapper {

    Dept findOne(Integer id);

    List<Dept> list();

    int save(Dept dept);

}
