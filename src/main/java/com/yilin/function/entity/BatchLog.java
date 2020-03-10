package com.yilin.function.entity;

import java.util.Date;

import com.yilin.function.service.BatchLogService;
import com.yilin.function.status.BatchLogStatus;

public class BatchLog {
	
	private Integer id;
	
	private String batchNo;
	
	private String batchStatus;
	
	private String batchName;
	
	private String executeDate;
	
	private String msg;

	private Integer failureIndex;
	
	private Date startTime;
	
	private Date createTime;
	
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getBatchStatus() {
		return batchStatus;
	}

	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}
	
	public void setStatus(BatchLogStatus status) {
		this.batchStatus = BatchLogService.getStringStatus(status);
	}


	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(String executeDate) {
		this.executeDate = executeDate;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getFailureIndex() {
		return failureIndex;
	}

	public void setFailureIndex(Integer failureIndex) {
		this.failureIndex = failureIndex;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	 
}
