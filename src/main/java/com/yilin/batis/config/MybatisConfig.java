package com.yilin.batis.config;

import javax.sql.DataSource;

import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 此配置为实验环境配置， 不具备生产环境复杂环境需要。
 * 
 * @author panyl
 *
 */
@Configuration
@EnableTransactionManagement // 开启事务管理
@MapperScan(basePackages = { "com.yilin.batis.mapper"} , sqlSessionFactoryRef="sfPrimary") // 映射器包扫描
public class MybatisConfig {

	/**
	 * spring自带实验用数据源 jar包： spring-jdbc
	 * 
	 * @return
	 */
	@Bean("dsPrimary")
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
//		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");  注意pom文件中mysql的版本问题
//		ds.setUrl("jdbc:mysql://47.104.128.12:3306/tuling-vip");
//		ds.setUsername("root");
//		ds.setPassword("123456");

//		ds.setUrl("jdbc:mysql://94.191.115.206:3306/batch-track");  //ip: 21
//		ds.setUsername("");
//		ds.setPassword("");

 		ds.setUrl("jdbc:mysql://localhost:3306/housecom");   
 		ds.setUsername("root");
 		ds.setPassword("");

//		ds.setUrl("jdbc:mysql://.mysql.database.azure.com:3306/housecompoc?useSSL=true&requireSSL=false&serverTimezone=UTC&autoReconnect=true");
// 		ds.setUsername("");
// 		ds.setPassword("");
		return ds;
	}

	/**
	 *   创建SqlSessionFactory
	 *   使用@Autowired 变量名指定DataSource实体Bean name.
	 * @return
	 * @throws Exception
	 */
	@Bean("sfPrimary")
	public SqlSessionFactory sqlSessionFactory(@Autowired DataSource dsPrimary) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dsPrimary);
		factoryBean.setConfiguration(configuration());
		return factoryBean.getObject();
	}

	@Bean("mybatis-conf")
	public org.apache.ibatis.session.Configuration configuration() {
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		//开启驼峰映射 
		configuration.setMapUnderscoreToCamelCase(true);
		//开启log日志显示sql
		configuration.setLogImpl(StdOutImpl.class);
		return configuration;
	}

	/**
	 *    创建Spring事务管理器
	 *    使用@Qualifier 指定名称注入
	 * @param ds
	 * @return
	 */
	@Bean("tmPrimary")
	public DataSourceTransactionManager transactionManager(@Qualifier("dsPrimary") DataSource ds) {
		return new DataSourceTransactionManager(ds);
	}
}
 
