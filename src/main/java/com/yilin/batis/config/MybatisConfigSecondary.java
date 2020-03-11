package com.yilin.batis.config;

import javax.sql.DataSource;

import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
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
@MapperScan(basePackages ={"com.yilin.function.mapper" }, sqlSessionFactoryRef="sfRemote") // 映射器包扫描
public class MybatisConfigSecondary {
 
	/**
	 * spring自带实验用数据源 jar包： spring-jdbc
	 *   使用多数据源， 对多个DataSource实体bean， 分别命名。
	 * @return
	 */
	@Bean(name = "dsRemote")
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver"); 
		ds.setUrl("jdbc:mysql://94.191.115.206:3306/batch-track");  //ip: 21
		ds.setUsername("");
		ds.setPassword("");
		return ds;
	}

	/**
	 *   创建SqlSessionFactory
	 *   命名SqlSessionFactory实体bean, 用语@MapperScan注解的sqlSessionFactoryRef属性。
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "sfRemote")
	@Autowired
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		/**
		 * 直接调用此类方法， 进行注入。
		 * **/
		factoryBean.setDataSource(dataSource());
		factoryBean.setConfiguration(configuration());
		return factoryBean.getObject();
	}

	/**
	 *   不可以使用相同的Configuration实体Bean, 因为这个Bean带有Mapper.xml相关数据信息，
	 *   如果使用相同Configuration实体Bean, 会去该库中查询相关表信息是否存在。
	 * @return
	 */
	@Bean(name = "confRemote") 
	public org.apache.ibatis.session.Configuration configuration() {
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		//开启驼峰映射 
		configuration.setMapUnderscoreToCamelCase(true);
		//开启log日志显示sql
		configuration.setLogImpl(StdOutImpl.class);
		return configuration;
	}

	/**
	 *   创建Spring事务管理器
	 *   命名事务管理器实体Bean, 用语@Transactional的属性transactionManager
	 *   调用dataSource()， 配合@Autowired 注入
	 * @param ds
	 * @return
	 */
	@Bean("tmRemote")
	@Autowired
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
}
 
