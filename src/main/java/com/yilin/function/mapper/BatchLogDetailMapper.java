package com.yilin.function.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yilin.function.entity.BatchLogDetails;


public interface BatchLogDetailMapper {
	 
	void insert(@Param("detail")BatchLogDetails detail);
	
	void update(@Param("detail")BatchLogDetails detail);
	
	void insertHist(@Param("detail")BatchLogDetails detail);
	
	List<BatchLogDetails> select(@Param("detail")BatchLogDetails detail);
}
