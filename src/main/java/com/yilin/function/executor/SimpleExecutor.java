package com.yilin.function.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yilin.function.FunctionWrapper;
import com.yilin.function.dto.BatchInDto;
import com.yilin.function.dto.BatchOutDto;
 

public class SimpleExecutor extends AbstractExecutor {

	Logger logger = LoggerFactory.getLogger(SimpleExecutor.class);
	
	public SimpleExecutor(FunctionWrapper<BatchInDto, BatchOutDto> fw) {
		this.function = fw.getF();
//		this.canBeExecute = fw.isEnable();
	}

	private boolean canBeExecute;
	 
	public void setCanBeExecute(boolean canBeExecute) {
		this.canBeExecute = canBeExecute;
	}

	@Override
	public boolean canExecuted() {
		return canBeExecute;
	}

	@Override
	public void afterSuccessOperation() {
		logger.info("doing post execution for succeed.");
	}

	@Override
	public void afterFailureOperation() {
		logger.info("doing post execution for failure.");
	}

	@Override
	public void noRunOperation() {
		logger.info("doing nothing.");
	}

	@Override
	public void readyOperation() {
		// TODO Auto-generated method stub
		
	}
}
