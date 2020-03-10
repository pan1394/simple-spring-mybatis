package com.yilin.function.executor;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yilin.function.dto.BatchInDto;
import com.yilin.function.dto.BatchOutDto;
import com.yilin.function.status.FunctionResult;
 

public abstract class AbstractExecutor implements FunctionExecutor<BatchInDto, BatchOutDto> {

	protected Function<BatchInDto, BatchOutDto> function;
	
	Logger logger = LoggerFactory.getLogger(BatchLogExecutor.class);
	
	@Override
	public BatchOutDto execute(BatchInDto input) {
		BatchOutDto res = new BatchOutDto();
		try {
			if(canExecuted()) {
					readyOperation();
					res = function.apply(input);
					if(res.getStatus() == FunctionResult.SUCCESS) {
						afterSuccessOperation();
					}else {
						afterFailureOperation();
					}
			}else {
				noRunOperation();
			}
		}catch(Exception e) {
			res.setStatus(FunctionResult.FAILURE);
			logger.error(e.getLocalizedMessage(), e);
		}
		return res;
	}

	public abstract boolean canExecuted();
 
	public abstract void readyOperation();
	
	public abstract void afterSuccessOperation();
	
	public abstract void afterFailureOperation();
	
	public abstract void noRunOperation();
	
}
