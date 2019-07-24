package com.demo.datasource;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.demo.transaction.MultiDataSourceTransactionFactory;

@Configuration
@MapperScan(basePackages = "com.demo.mapper", sqlSessionFactoryRef = "SqlSessionFactory")
@PropertySource(value = { "classpath:config-base/application.properties" })
public class DataSourceConfig {

	@Primary
	@Bean(name = "datasource1")
	@ConfigurationProperties(prefix = "spring.datasource1")
	public DataSource getDateSource1() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "datasource2")
	@ConfigurationProperties(prefix = "spring.datasource2")
	public DataSource getDateSource2() {
		return DataSourceBuilder.create().build();
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
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
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