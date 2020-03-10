package com.yilin.function.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yilin.function.entity.BatchLog;


public interface BatchLogMapper {
	
	List<BatchLog> selectBatchLog(@Param("batchLog")BatchLog batchLog);
	
	void updateBatchLog(@Param("batchLog")BatchLog batchLog);
	
	void insertBatchLog(@Param("batchLog")BatchLog batchLog);
	
	void insert(@Param("batchLog")BatchLog batchLog);
	
	void update(@Param("batchLog")BatchLog batchLog);
	
	void insertBatchLogWithCheckExist(@Param("batchLog")BatchLog batchLog);
	
	List<BatchLog> selectLatest(String batchNo);
	
	int getCountBy(String status, String date);
	
}
