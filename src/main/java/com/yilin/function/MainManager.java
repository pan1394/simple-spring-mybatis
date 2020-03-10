package com.yilin.function;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yilin.function.dto.BatchInDto;
import com.yilin.function.dto.BatchOutDto;
import com.yilin.function.entity.BatchLog;
import com.yilin.function.executor.BatchLogExecutor;
import com.yilin.function.service.BatchLogService;
import com.yilin.function.status.BatchLogStatus;
import com.yilin.function.status.FunctionResult;

/**
 * 非spring管理Bean! 用于多线程业务处理。
 * @author panyl
 *
 */
public class MainManager {

	private static Logger logger = LoggerFactory.getLogger(MainManager.class);
	  
	/**
	 * managed instance of function jobs defined in spring.
	 */
	private List<FunctionWrapper<BatchInDto, BatchOutDto>> container = new ArrayList<>();
	 
	/**
	 * append function instance/job.
	 * @param f
	 */
	public void add(FunctionWrapper<BatchInDto, BatchOutDto> f) {
 		container.add(f);
	}
	 
   @Autowired
   private BatchLogService batchLogService;
    
	/**
	 * execute logic, how to run all jobs.
	 * @param input
	 */
	public BatchOutDto start(BatchInDto input) {
		BatchOutDto res = new BatchOutDto();
		String errmsg = "";
		FunctionResult fs = FunctionResult.SUCCESS;
		
		String batchNo= input.getShubetsu() + input.getBatchId();
		//query the batch's latest execute status in db.
		BatchLog latest = batchLogService.getLatestBatchLog(input);
		BatchLogStatus logStatus = BatchLogStatus.of(Integer.parseInt(latest.getBatchStatus()));
		//1. newly, not executed yet
		if(logStatus == BatchLogStatus.INIT) {
			// insert a new batchlog object;
			// insert all batchlogDetail objects;
			//init all job execute status to can run.
			BatchLog log = batchLogService.createNewBatchLog(input, container);
			BatchOutDto previous = null;
			int fail = -1;
			for(int i=0; i<container.size(); i++) {
				BatchLogExecutor executor = new BatchLogExecutor(container.get(i), batchLogService);
				if(i != 0) {
					if(previous != null && FunctionResult.SUCCESS == previous.getStatus()) {
						executor.setCanBeExecute(true);
					}else {
						fail = i-1;
						break;
					}
				}
				BatchOutDto out = executor.execute(input);
				previous = out;
			}
			
			BatchLogStatus bStatus = BatchLogStatus.SUCCED;
			if(fail != -1) {
				for(int i=fail+1; i<container.size(); i++) {
					BatchLogExecutor executor = new BatchLogExecutor(container.get(i), batchLogService);
					executor.setCanBeExecute(false); 
					executor.execute(input);
				}
				log.setFailureIndex(fail);
				bStatus = BatchLogStatus.FAILED;
				fs = FunctionResult.FAILURE;
				errmsg = String.format("Batch %s failed at job[%s]", log.getBatchName(), container.get(fail).getName());
			}
			log.setBatchStatus(BatchLogService.getStringStatus(bStatus));
			batchLogService.updateBatchLog(log);
		}
		//2. executed, but failed.
		else if(logStatus == BatchLogStatus.FAILED){
			//get which job is failure, and set the flag of canBeExecute.
			int failureIdx = latest.getFailureIndex(); 
			// insert a new batchlog object;
			// insert all batchlogDetail objects with special status.
			BatchLog log = batchLogService.createBatchLogFailure(input, container, failureIdx);
			BatchOutDto previous = null;
			int fail = -1;
			for(int i=failureIdx; i<container.size(); i++) {
				BatchLogExecutor executor = new BatchLogExecutor(container.get(i), batchLogService);
				if(i != failureIdx) {
					if(previous != null && FunctionResult.SUCCESS == previous.getStatus()) {
						executor.setCanBeExecute(true);
					}else {
						fail = i-1;
						break;
					}
				}
				BatchOutDto out = executor.execute(input);
				previous = out;
			}
			
			BatchLogStatus bStatus = BatchLogStatus.SUCCED;
			if(fail != -1) {
				for(int i=fail+1; i<container.size(); i++) {
					BatchLogExecutor executor = new BatchLogExecutor(container.get(i), batchLogService);
					executor.setCanBeExecute(false); 
					executor.execute(input);
				}
				log.setFailureIndex(fail);
				bStatus = BatchLogStatus.FAILED;
				fs = FunctionResult.FAILURE;
				errmsg = String.format("Batch %s failed at job[%s]", log.getBatchName(), container.get(fail).getName());
			}
			log.setBatchStatus(BatchLogService.getStringStatus(bStatus));
			batchLogService.updateBatchLog(log);
		}
		//3. runing...
		else if(logStatus == BatchLogStatus.RUNNING) {
		    logger.info(String.format("Batch Id %s is running." , batchNo));
		    res.setStatus(FunctionResult.PROCESSING);
		    res.setDesc(String.format("Batch Id %s is running." , batchNo));
		}
		//4. executed, succeed.
		else if(logStatus == BatchLogStatus.SUCCED) {
		    logger.info(String.format("Batch Id %s executed successfully." , batchNo));
		    res.setDesc(String.format("Batch Id %s executed successfully." , batchNo));
		}
		res.setStatus(fs);
		res.setErrorMessage(errmsg);
		return res;
	}
}