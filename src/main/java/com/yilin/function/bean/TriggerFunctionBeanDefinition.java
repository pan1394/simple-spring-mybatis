package com.yilin.function.bean;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yilin.function.MainManager;
import com.yilin.function.api.DateUtil;
import com.yilin.function.dto.BatchInDto;
import com.yilin.function.dto.BatchOutDto;
import com.yilin.function.mapper.BatchLogMapper;
import com.yilin.function.service.BatchLogService;
import com.yilin.function.status.BatchLogStatus;


@Configuration
public class TriggerFunctionBeanDefinition {

	private static final int BATCH_TOTAL_COUNT = 2;
	private static Logger logger = LoggerFactory.getLogger(TriggerFunctionBeanDefinition.class);

	@Autowired
	private BatchLogMapper batchLogMapper;

	@Autowired
	private MainManager accMainManager;

	@Autowired
	private MainManager ctmMainManager;

	@Bean
	public Function<BatchInDto, BatchOutDto> accQueue() {
		return inDto -> {
			return accMainManager.start(inDto);
		};
	}

	@Bean
	public Function<BatchInDto, BatchOutDto> ctmQueue() {
		return inDto -> {
			return ctmMainManager.start(inDto);
		};
	}

	/**
	 * 手动触发Batch
	 * 
	 * @return
	 */
	@Bean
	public Function<BatchInDto, BatchOutDto> manualTrigger() {
		return inDto -> {
			logger.info("shutdown webapp...");
			String type = inDto.getShubetsu();
			BatchOutDto res = null;
			if (type.equals("acc")) {
				res = accMainManager.start(inDto);
			} else if (type.equals("ctm")) {
				res = ctmMainManager.start(inDto);
			}
			logger.info("start webapp...");
			return res;
		};
	}

	/**
	 * 定时任务触发
	 * 
	 * @return
	 */
	@Bean
	public Function<BatchInDto, BatchOutDto> schedulerTask() {
		return inDto -> {
			String date = DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDD);
			// App停止
			logger.info("shutdown webapp...");
			// AzureUtils.getWebApp().stop();

			// ACCQ起動
//			AzureUtils.sendMsg(date, AzureUtils.ACC_QUEUE_NAME);
			// CTMQ起動
//			AzureUtils.sendMsg(date, AzureUtils.CTM_QUEUE_NAME);

			// Monitor the scheduleTask...
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						int cycleTotal = 5;
						int cycle = 0;
						Thread.sleep(3 * 1000);
						int workBatchCnt = batchLogMapper
								.getCountBy(BatchLogService.getStringStatus(BatchLogStatus.RUNNING), date);
						boolean alarm = true;
						boolean timeoutFlg = false;
						// 2 hours
						long timeout = 60 * 60 * 1000 * 2;
						long time1 = System.currentTimeMillis();
						while (cycle < cycleTotal || workBatchCnt > 0) {
							// step-1 sleep for waiting
							Thread.sleep(1000 * 3);
							// step-2 query batchlog list, compare sucessed batch count
							int succeedBatchCnt = batchLogMapper
									.getCountBy(BatchLogService.getStringStatus(BatchLogStatus.SUCCED), date);
							workBatchCnt = batchLogMapper
									.getCountBy(BatchLogService.getStringStatus(BatchLogStatus.RUNNING), date);
							// step-3 update after all batch has sucessed
							if (succeedBatchCnt == BATCH_TOTAL_COUNT) {
								logger.info("ALL SUBSYSTEM EXECUTE SUCCESSFULLY!!");
								logger.info("restart webapp...");
								// AzureUtils.getWebApp().start();
								alarm = false;
								break;
							}

							long time2 = System.currentTimeMillis();
							long consumed = time2 - time1;
							// execeed 2 hours
							if (consumed > timeout) {
								timeoutFlg = true;
								break;
							}
							// step-4 continue next circle
							cycle++;
						}

						// TODO implement alarm sent by email or other IM tools!;
						if (alarm) {
							int failedBatchCnt = batchLogMapper
									.getCountBy(BatchLogService.getStringStatus(BatchLogStatus.FAILED), date);
							logger.error("ALARM: {} batch failed!!", failedBatchCnt);
							logger.error("Email sent to Adminstrator.");
						}
						if (timeoutFlg) {
							int failedBatchCnt = batchLogMapper
									.getCountBy(BatchLogService.getStringStatus(BatchLogStatus.RUNNING), date);
							logger.error("ALARM: still {} batch runing execeed 2 hours!!", failedBatchCnt);
							logger.error("Email sent to Adminstrator.");
						}

					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			t.start();
			return new BatchOutDto();
		};
	}
}
