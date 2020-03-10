package com.yilin.batis;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.yilin.batis.service.DepartmentService;

@ComponentScan //spring容器包扫描此包及其子包下的bean
public class Main {

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
	}
}
