package com.yilin.function.dto;

import com.yilin.function.status.FunctionResult;

public class BatchOutDto {

	//private String status;
	
	private String errorMessage;

	private FunctionResult status = FunctionResult.SUCCESS;

	public String getErrorMessage() {
		return errorMessage;
	}
	
	private String desc;
 
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public FunctionResult getStatus() {
		return status;
	}

	public void setStatus(FunctionResult status) {
		this.status = status;
	} 
}
