package com.demo.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.demo.transaction.MultiDataSourceTransactionFactory;

@Configuration
@MapperScan(basePackages = "com.demo.mapper", sqlSessionFactoryRef = "SqlSessionFactory")
@PropertySource(value = { "classpath:config-base/application.properties" })
public class DataSourceConfig {
	
	/**
	 * 第一个数据库
	 */
	@Value("${spring.datasource1.jdbc-url}")
    private String spring_datasource1_jdbc_url;
    @Value("${spring.datasource1.username}")
    private String spring_datasource1_username;
    @Value("${spring.datasource1.password}")
    private String spring_datasource1_password;
    
    /**
	 * 第二个数据库
	 */
    @Value("${spring.datasource2.jdbc-url}")
    private String spring_datasource2_jdbc_url;
    @Value("${spring.datasource2.username}")
    private String spring_datasource2_username;
    @Value("${spring.datasource2.password}")
    private String spring_datasource2_password;
    
    /**
     * 通用配置
     */
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.auto-commit}")
    private boolean autoCommit;
    @Value("${spring.datasource.default-auto-commit}")
    private boolean defaultAutoCommit;
    @Value("${spring.datasource.default-transaction-isolation}")
    private int defaultTransactionIsolation;
    @Value("${spring.datasource.initialSize}")
    private int initialSize;
    @Value("${spring.datasource.minIdle}")
    private int minIdle;
    @Value("${spring.datasource.maxActive}")
    private int maxActive;

	@Primary
	@Bean(name = "datasource1")
//	@ConfigurationProperties(prefix = "spring.datasource1")
	public DataSource getDateSource1() {
		
		DruidDataSource datasource = new DruidDataSource();
		
		// 基础连接
		datasource.setUrl(spring_datasource1_jdbc_url);
		datasource.setUsername(spring_datasource1_username);
		datasource.setPassword(spring_datasource1_password);

		// 基础配置
		datasource.setDriverClassName(driverClassName);
		datasource.setDefaultAutoCommit(defaultAutoCommit);
		datasource.setDefaultTransactionIsolation(defaultTransactionIsolation);
		datasource.setInitialSize(initialSize);
		datasource.setMinIdle(minIdle);
		datasource.setMaxActive(maxActive);
		
		return datasource;
	}

	@Bean(name = "datasource2")
//	@ConfigurationProperties(prefix = "spring.datasource2")
	public DataSource getDateSource2() {
		
		DruidDataSource datasource = new DruidDataSource();
		
		// 基础连接
		datasource.setUrl(spring_datasource2_jdbc_url);
		datasource.setUsername(spring_datasource2_username);
		datasource.setPassword(spring_datasource2_password);

		// 基础配置
		datasource.setDriverClassName(driverClassName);
		datasource.setDefaultAutoCommit(defaultAutoCommit);
		datasource.setDefaultTransactionIsolation(defaultTransactionIsolation);
		datasource.setInitialSize(initialSize);
		datasource.setMinIdle(minIdle);
		datasource.setMaxActive(maxActive);
		
		return datasource;
	}

	@Bean(name = "dynamicDataSource")
	public DynamicDataSource DataSource(@Qualifier("datasource1") DataSource test1DataSource,
			@Qualifier("datasource2") DataSource test2DataSource) {
		Map<Object, Object> targetDataSource = new HashMap<Object, Object>();
		targetDataSource.put(DataSourceType.DataBaseType.TEST01, test1DataSource);
		targetDataSource.put(DataSourceType.DataBaseType.TEST02, test2DataSource);
		DynamicDataSource dataSource = new DynamicDataSource();
		dataSource.setTargetDataSources(targetDataSource);
		dataSource.setDefaultTargetDataSource(test1DataSource);
		return dataSource;
	}

	@Bean(name = "SqlSessionFactory")
	public SqlSessionFactory test1SqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource)
			throws Exception {
//		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		//如果使用mybatis-plus，请使用 MybatisSqlSessionFactoryBean，如果使用上面则报错 Invalid bound statement (not found)
		MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
		bean.setDataSource(dynamicDataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
		bean.setTransactionFactory(new MultiDataSourceTransactionFactory());
		return bean.getObject();
	}

	/**
	 * 事务管理
	 */
	@Primary
	@Bean(name = "transactionManager1")
	public PlatformTransactionManager oneTransactionManager(@Qualifier("datasource1") DataSource datasource1) {
		return new DataSourceTransactionManager(datasource1);
	}

	@Bean(name = "transactionManager2")
	public PlatformTransactionManager twoTransactionManager(@Qualifier("datasource2") DataSource datasource2) {
		return new DataSourceTransactionManager(datasource2);
	}

}