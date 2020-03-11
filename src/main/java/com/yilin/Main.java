package com.yilin;

import java.util.function.Function;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.yilin.batis.service.DepartmentService;
import com.yilin.function.dto.BatchInDto;
import com.yilin.function.dto.BatchOutDto;

 
@ComponentScan //spring容器包扫描此包及其子包下的bean
public class Main {

	public static void mainx(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
		
		String[] all = ctx.getBeanDefinitionNames();
		for(String a : all) {
			System.out.println(a);
		}
		
		Function<BatchInDto, BatchOutDto> accQueue = (Function<BatchInDto, BatchOutDto>) ctx.getBean("accQueue");
		BatchInDto input = new BatchInDto();
		input.setBatchId("20200309");
		input.setShubetsu("acc");

		BatchOutDto result = accQueue.apply(input);
		System.out.println(result.getDesc());
	}
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
		DepartmentService service = ctx.getBean(DepartmentService.class);
		try {
			 service.addDepartment("立法部2"); 
		}catch(RuntimeException e) {
			System.out.println(e.getLocalizedMessage() + " 事物回滾。");
		}
		
		
		//上面数据没有被插入数据库
		service.listAllDepartments().stream().forEach(System.out::println);
		
		mainx(args);
	}
}
