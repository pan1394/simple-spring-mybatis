package com.yilin.function.entity;

import java.util.Date;

import com.yilin.function.service.BatchLogService;
import com.yilin.function.status.BatchLogStatus;

 
public class BatchLogDetails{
	private Integer id;
	
	private Integer batchId;
	
	private String batchNo;
	
	private String jobName;
	
	private String jobStatus;
	
	private Integer jobIndex;
	
	private String msg;
	
	private Date createTime;
	
	private Date startTime;
	
	private Date updateTime;

	private BatchLog log;
	
	private String savePoint;
	
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

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public BatchLogStatus getStatus() {
		return BatchLogStatus.of(Integer.parseInt(jobStatus));
	}
	
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public void setStatus(BatchLogStatus status) {
		this.jobStatus = BatchLogService.getStringStatus(status);
	}
	
	public Integer getJobIndex() {
		return jobIndex;
	}

	public void setJobIndex(Integer jobIndex) {
		this.jobIndex = jobIndex;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public BatchLog getLog() {
		return log;
	}

	public void setLog(BatchLog log) {
		this.log = log;
	}

	public Integer getBatchId() {
		return batchId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	public String getSavePoint() {
		return savePoint;
	}

	public void setSavePoint(String savepoint) {
		this.savePoint = savepoint;
	}
	
	
}
