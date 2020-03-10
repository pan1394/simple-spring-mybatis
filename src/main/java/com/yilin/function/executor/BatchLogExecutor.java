package com.yilin.function.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yilin.function.FunctionWrapper;
import com.yilin.function.dto.BatchInDto;
import com.yilin.function.dto.BatchOutDto;
import com.yilin.function.entity.BatchLogDetails;
import com.yilin.function.exception.RecoverException;
import com.yilin.function.service.BatchLogService;
import com.yilin.function.status.BatchLogStatus;

public class BatchLogExecutor extends AbstractExecutor {

	Logger logger = LoggerFactory.getLogger(BatchLogExecutor.class);
	
	private BatchLogService batchLogService;
	  
	private BatchLogDetails detail;
	
	public BatchLogExecutor(FunctionWrapper<BatchInDto, BatchOutDto> fw, BatchLogService service) {
		this.function = fw.getF();
		this.detail = fw.getDetail();
		this.batchLogService = service;
	}

	private boolean canBeExecute=true;
	
	
	public void setBatchLogService(BatchLogService batchLogService) {
		this.batchLogService = batchLogService;
	}

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
		detail.setStatus(BatchLogStatus.SUCCED);
		batchLogService.updateBatchLogDetail(detail);
		logger.info("job[{}] execution succeed.", detail.getJobName());

	}

	@Override
	public void afterFailureOperation() {
		logger.info("doing post execution for failure.");
		detail.setStatus(BatchLogStatus.FAILED);
		batchLogService.updateBatchLogDetail(detail);
		logger.info("job[{}] execution failure.", detail.getJobName());
	}

	@Override
	public void noRunOperation() {
		logger.info(String.format("doing nothing for job[%s].", detail.getJobName()));
		detail.setStatus(BatchLogStatus.NORUN);
		batchLogService.updateBatchLogDetail(detail);
		
	}

	@Override
	public void readyOperation() {
		logger.info(String.format("ready for starting job[%s]...", detail.getJobName()));
		//failed last time
		if(detail.getStatus() ==BatchLogStatus.FAILED ) {
			String point = detail.getSavePoint();
			recover(point);
		}else {
			savepoint();
		}
		detail.setStatus(BatchLogStatus.RUNNING);
		batchLogService.updateBatchLogDetail(detail, true);
	}

	// TODO implement to make database save point!;
	public void savepoint() {
		String point = detail.getJobName() + detail.getJobIndex();
		logger.info(String.format("db make save point as %s for job[%s]", point, detail.getJobName()));
		detail.setSavePoint(point);
		batchLogService.updateBatchLogDetail(detail);
	}
	
	public void recover(String point) {
		boolean checked = true;
		// TODO implement check(point);
		// check save point;
		// checked = check(point);
		if(checked){
			try {
				// TODO implement database recover!;
				logger.info(String.format("db recovered with savepoint: <%s> for job[%s]", point, detail.getJobName()));
			}catch(Exception e) {
				logger.error("recover failed");
				detail.setStatus(BatchLogStatus.INIT);
				batchLogService.updateBatchLogDetail(detail, true);
				throw new RecoverException(String.format("Failed during Database recovering back to save point %s on job[%s]", point, detail.getJobName()));
			}
		}else{
			logger.error("recover failed");
			detail.setStatus(BatchLogStatus.INIT);
			batchLogService.updateBatchLogDetail(detail, true);
			throw new RecoverException(String.format("Check save point %s on job[%s] is invalid.",point, detail.getJobName()));
	    }
	}
	
}
