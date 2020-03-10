package com.yilin.function.bean;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yilin.function.FunctionWrapper;
import com.yilin.function.MainManager;
import com.yilin.function.dto.BatchInDto;
import com.yilin.function.dto.BatchOutDto;
import com.yilin.function.status.FunctionResult;

@Configuration
public class CtmJobFunctionBeanDefinition {
  
    private static Logger logger = LoggerFactory.getLogger(CtmJobFunctionBeanDefinition.class);
    
    @Bean
    public Function<BatchInDto, BatchOutDto> ctmbt001Function() {
        return inDto -> { 
            BatchOutDto out = new BatchOutDto();
            try {
				//accbt001Service.execute(accbt001serviceInDto);
            	Thread.sleep(2000);
            	logger.info("DEMO: ctmbt001Service execute...");
			} catch (Exception e) {
				out.setStatus(FunctionResult.FAILURE);
				out.setErrorMessage(e.getLocalizedMessage());
			}
            return out;
        };
    }
    
    @Bean
    public Function<BatchInDto, BatchOutDto> ctmbt002Function() {
        return inDto -> {
            BatchOutDto out = new BatchOutDto();
            try {
            	logger.info("DEMO: map inDto to ctmbt002ServiceInDto...");
            	logger.info("DEMO: ctmbt002Service execute...");
            	Thread.sleep(1000);
			} catch (Exception e) {
				out.setStatus(FunctionResult.FAILURE);
				out.setErrorMessage(e.getLocalizedMessage());
			}
            return out;
        };
    }
    
    
    @Bean
    public Function<BatchInDto, BatchOutDto> ctmbt003Function() {
        return inDto -> {
            BatchOutDto out = new BatchOutDto();
            try {
            	logger.info("DEMO: map inDto to ctmbt003ServiceInDto...");
            	logger.info("DEMO: ctmbt003Service execute...");
            	Thread.sleep(3000);
			} catch (Exception e) {
				out.setStatus(FunctionResult.FAILURE);
				out.setErrorMessage(e.getLocalizedMessage());
			}
            return out;
        };
    }
    
    @Bean
    public MainManager ctmMainManager() {
    	MainManager ctmManager = new MainManager();
    	ctmManager.add(new FunctionWrapper<BatchInDto, BatchOutDto>(ctmbt001Function(), "ctm001", 0));
    	ctmManager.add(new FunctionWrapper<BatchInDto, BatchOutDto>(ctmbt002Function(), "ctm002", 1));
    	ctmManager.add(new FunctionWrapper<BatchInDto, BatchOutDto>(ctmbt003Function(), "ctm003", 2));
    	return ctmManager;
    }
}
