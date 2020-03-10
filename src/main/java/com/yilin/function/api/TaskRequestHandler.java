//package com.yilin.function.api;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.function.adapter.azure.AzureSpringBootRequestHandler;
//
//import com.microsoft.azure.functions.ExecutionContext;
//import com.microsoft.azure.functions.HttpMethod;
//import com.microsoft.azure.functions.HttpRequestMessage;
//import com.microsoft.azure.functions.HttpResponseMessage;
//import com.microsoft.azure.functions.HttpStatus;
//import com.microsoft.azure.functions.annotation.AuthorizationLevel;
//import com.microsoft.azure.functions.annotation.BindingName;
//import com.microsoft.azure.functions.annotation.FunctionName;
//import com.microsoft.azure.functions.annotation.HttpTrigger;
//import com.microsoft.azure.functions.annotation.QueueTrigger;
//import com.microsoft.azure.functions.annotation.TimerTrigger;
//
//import jp.co.housecom.common.model.BatchInDto;
//import jp.co.housecom.common.model.BatchOutDto;
//import jp.co.housecom.common.util.AzureUtils;
//
//public class TaskRequestHandler extends AzureSpringBootRequestHandler<BatchInDto, BatchOutDto> {
//
//	private static final Logger logger = LoggerFactory.getLogger(TaskRequestHandler.class);
//	
//	@FunctionName("manualTrigger")
//	public HttpResponseMessage HttpTrigger(
//	            @HttpTrigger(name = "req",
//	                         methods = {HttpMethod.GET, HttpMethod.POST},
//	                         authLevel = AuthorizationLevel.ANONYMOUS,
//	                        		 route = "trigger/{shubetsu}/{id}"
//	                         ) HttpRequestMessage<String> request, 
//	            @BindingName("shubetsu") String shubetsu,
//	            @BindingName("id") String id,
//	            final ExecutionContext context) {
//
//		BatchInDto inDto = new BatchInDto();
//		inDto.setBatchId(id);
//		inDto.setShubetsu(shubetsu);
//		BatchOutDto out = handleRequest(inDto, context); 
//	    return request.createResponseBuilder(HttpStatus.OK).body(out).build();
//    }
//	
//	@FunctionName("schedulerTask")
//	public BatchOutDto timerExecute(
//			@TimerTrigger(name = "timerInfo", schedule = AzureUtils.BATCH_TIMER_TRIGGER_CRON) String timerInfo,
//			ExecutionContext context) {
//		logger.info(timerInfo);
//		BatchInDto inDto = new BatchInDto();
//		return handleRequest(inDto, context);
//	}
//	
//	@FunctionName("accQueue")
//	public BatchOutDto accQueueListen(
//			@QueueTrigger(name = "msg", queueName = AzureUtils.ACC_QUEUE_NAME, 
//			connection = "AzureWebJobsStorage") String message, ExecutionContext context) {
//		BatchInDto inDto = new BatchInDto();
//		inDto.setShubetsu("acc");
//		inDto.setBatchId(message);
//		return handleRequest(inDto, context);
//	}
//	
//	@FunctionName("ctmQueue")
//    public BatchOutDto ctmQueueListen(
//    		@QueueTrigger(name = "msg", queueName = AzureUtils.CTM_QUEUE_NAME,
//            connection = "AzureWebJobsStorage") String message, ExecutionContext context) {
//        BatchInDto inDto = new BatchInDto();
//        inDto.setShubetsu("ctm");
//        inDto.setBatchId(message);
//        return handleRequest(inDto, context);
//    }
//}