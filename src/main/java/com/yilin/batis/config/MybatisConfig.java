package com.yilin.batis.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement 			//开启事务管理
@MapperScan("com.yilin.batis.mapper") 	//映射器包扫描
public class MybatisConfig {

	/**
	 * spring自带实验用数据源
	 * jar包： spring-jdbc
	 * @return
	 */
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
 		ds.setDriverClassName("com.mysql.jdbc.Driver");
//		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");  注意pom文件中mysql的版本问题
		ds.setUrl("jdbc:mysql://47.104.128.12:3306/tuling-vip");
		ds.setUsername("root");
		ds.setPassword("123456");
		return ds;
	}
	
	/**
	 * 创建SqlSessionFactory
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
	  SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
	  factoryBean.setDataSource(dataSource());
	  return factoryBean.getObject();
	}
	 

	/**
	 * 创建Spring事务管理器
	 * @param ds
	 * @return
	 */
	@Bean
	@Autowired
	public DataSourceTransactionManager transactionManager(DataSource ds) {
		return new DataSourceTransactionManager(ds);
	}
}
 