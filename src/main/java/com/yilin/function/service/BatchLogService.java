package com.yilin.function.service;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yilin.function.FunctionWrapper;
import com.yilin.function.api.DateUtil;
import com.yilin.function.dto.BatchInDto;
import com.yilin.function.dto.BatchOutDto;
import com.yilin.function.entity.BatchLog;
import com.yilin.function.entity.BatchLogDetails;
import com.yilin.function.mapper.BatchLogDetailMapper;
import com.yilin.function.mapper.BatchLogMapper;
import com.yilin.function.status.BatchLogStatus;

@Service
public class BatchLogService {

	@Autowired
	private BatchLogMapper batchLogMapper;
	 
	@Autowired
	private BatchLogDetailMapper batchLogDetailMapper;
	
	public BatchLog getLatestBatchLog(BatchInDto dto) {
		String batchNo= dto.getShubetsu()+dto.getBatchId();
		List<BatchLog> res = batchLogMapper.selectLatest(batchNo);
		if(!CollectionUtils.isEmpty(res)) {
			return res.get(0);
		}
		BatchLog log = new BatchLog();
		log.setStatus(BatchLogStatus.INIT);
		return log;
	}
	
	public BatchLog createNewBatchLog(BatchInDto dto, List<FunctionWrapper<BatchInDto, BatchOutDto>> funs) {
		return createBatchLog(dto, funs, -1);
	}
	
	
	public BatchLog createBatchLogFailure(BatchInDto dto, List<FunctionWrapper<BatchInDto, BatchOutDto>> funs, int failedIndex) {
		String batchNo= dto.getShubetsu()+dto.getBatchId();
		Date updateTime = new Date();
		BatchLog bl = new BatchLog();
		bl.setBatchNo(batchNo);
		bl.setBatchName(dto.getShubetsu());
		bl.setStatus(BatchLogStatus.RUNNING);
		bl.setExecuteDate(DateUtil.format(updateTime.toInstant()
		        .atZone(ZoneId.systemDefault() )
		        .toLocalDateTime(), DateUtil.DATE_FORMAT_YYYYMMDD));
		bl.setStartTime(updateTime);
		bl.setCreateTime(updateTime);
		bl.setUpdateTime(updateTime);
	    batchLogMapper.insert(bl);
        
	    BatchLogDetails detail = new BatchLogDetails();
    	detail.setBatchId(bl.getId());
    	detail.setCreateTime(updateTime);
    	detail.setBatchNo(bl.getBatchNo());
    	batchLogDetailMapper.insertHist(detail);
    	
    	List<BatchLogDetails> all = batchLogDetailMapper.select(detail);
        for(FunctionWrapper<BatchInDto, BatchOutDto> f : funs) {
        	for(BatchLogDetails item : all) {
        		if(item.getJobName().equals(f.getName()) && item.getJobIndex() == f.getIndex()) {
        			f.setDetail(item);
        			break;
        		}
        	}
        }
        return bl;
	}
	
	public BatchLog createBatchLog(BatchInDto dto, List<FunctionWrapper<BatchInDto, BatchOutDto>> funs, int failedIndex) {
		String batchNo= dto.getShubetsu()+dto.getBatchId();
		Date updateTime = new Date();
		BatchLog bl = new BatchLog();
		bl.setBatchNo(batchNo);
		bl.setBatchName(dto.getShubetsu());
		bl.setStatus(BatchLogStatus.RUNNING);
		bl.setExecuteDate(DateUtil.format(updateTime.toInstant()
		        .atZone(ZoneId.systemDefault() )
		        .toLocalDateTime(), DateUtil.DATE_FORMAT_YYYYMMDD));
		bl.setStartTime(updateTime);
		bl.setCreateTime(updateTime);
		bl.setUpdateTime(updateTime);
	    batchLogMapper.insert(bl);
        
        for(FunctionWrapper<BatchInDto, BatchOutDto> f : funs) {
        	BatchLogDetails detail = new BatchLogDetails();
        	detail.setBatchId(bl.getId());
        	detail.setLog(bl);
        	detail.setBatchNo(bl.getBatchNo());
        	detail.setJobName(f.getName());
        	detail.setJobIndex(f.getIndex());
        	if(f.getIndex() < failedIndex) {
        		detail.setStatus(BatchLogStatus.SUCCED);
        	}else if(f.getIndex() == failedIndex) {
        		detail.setStatus(BatchLogStatus.FAILED);
        	}else{
        		detail.setStatus(BatchLogStatus.INIT);
        	}
        	detail.setCreateTime(updateTime);
        	batchLogDetailMapper.insert(detail); 
        	f.setDetail(detail);
        }
        return bl;
	}
	
	public void updateBatchLog(BatchLog log) {
		Date updateTime = new Date(); 
		log.setUpdateTime(updateTime);
		batchLogMapper.update(log);
	}
	
	public void updateBatchLogDetail(BatchLogDetails detail) {
		updateBatchLogDetail(detail, false);
	}
	
	public void updateBatchLogDetail(BatchLogDetails detail, boolean start) {
		Date updateTime = new Date();
		if(start) {
			detail.setStartTime(updateTime);
		} 
		detail.setUpdateTime(updateTime);
		batchLogDetailMapper.update(detail);
	}
	
	public static String getStringStatus(BatchLogStatus status) {
		return status.getValue() + "";
	}
}
