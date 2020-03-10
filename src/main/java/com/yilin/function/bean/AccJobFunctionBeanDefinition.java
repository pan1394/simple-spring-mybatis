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
public class AccJobFunctionBeanDefinition {
    
    private static Logger logger = LoggerFactory.getLogger(AccJobFunctionBeanDefinition.class);
    
    @Bean
    public Function<BatchInDto, BatchOutDto> accbt001Function() {
        return inDto -> {
        	System.out.println("==========================================="); 
//        	Accbt001ServiceInDto accbt001serviceInDto = new Accbt001ServiceInDto();
//            beanMapper.map(inDto, accbt001serviceInDto);
            BatchOutDto out = new BatchOutDto();
            try {
				//accbt001Service.execute(accbt001serviceInDto);
            	Thread.sleep(2000);
            	logger.info("accbt001Service execute...");
			} catch (Exception e) {
				out.setStatus(FunctionResult.FAILURE);
				out.setErrorMessage(e.getLocalizedMessage());
			}
            return out;
        };
    }
    
    @Bean
    public Function<BatchInDto, BatchOutDto> accbt002Function() {
        return inDto -> {
            BatchOutDto out = new BatchOutDto();
            try {
            	logger.info("map inDto to accbt002ServiceInDto...");
            	logger.info("accbt002Service execute...");
            	Thread.sleep(1000);
			} catch (Exception e) {
				out.setStatus(FunctionResult.FAILURE);
				out.setErrorMessage(e.getLocalizedMessage());
			}
            return out;
        };
    }
    
    
    @Bean
    public Function<BatchInDto, BatchOutDto> accbt003Function() {
        return inDto -> {
            BatchOutDto out = new BatchOutDto();
            try {
            	logger.info("map inDto to accbt003ServiceInDto...");
            	logger.info("accbt003Service execute...");
            	Thread.sleep(3000);
			} catch (Exception e) {
				out.setStatus(FunctionResult.FAILURE);
				out.setErrorMessage(e.getLocalizedMessage());
			}
            return out;
        };
    }
    
    @Bean
    public MainManager accMainManager() {
    	MainManager accManager = new MainManager();
    	accManager.add(new FunctionWrapper<BatchInDto, BatchOutDto>(accbt001Function(), "accbt001Function", 0)); 
    	accManager.add(new FunctionWrapper<BatchInDto, BatchOutDto>(accbt002Function(), "accbt002Function", 1));
    	accManager.add(new FunctionWrapper<BatchInDto, BatchOutDto>(accbt003Function(), "accbt003Function", 2));
    	return accManager;
    }
}
