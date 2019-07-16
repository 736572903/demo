package com.demo.elasticjob.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.demo.elasticjob.SimpleElasticJob;
import com.demo.elasticjob.SimpleElasticJob2;

@Configuration
public class SimpleJobConfig {
	@Resource
	private ZookeeperRegistryCenter regCenter;
	
	/**
	 * 第一个simplejob
	 */
	@Bean
	public SimpleElasticJob simpleJob() {
		return new SimpleElasticJob();
	}

	@Bean(initMethod = "init")
	public JobScheduler simpleJobScheduler(final SimpleElasticJob simpleJob, @Value("${simpleJob.cron}") final String cron,
			@Value("${simpleJob.shardingTotalCount}") final int shardingTotalCount) {

		return new SpringJobScheduler(simpleJob, regCenter,
				getSimpleAJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount));
	}
	
	/**
	 * 第二个simplejob
	 */
	@Bean
	public SimpleElasticJob2 simpleJob2() {
		return new SimpleElasticJob2();
	}
	
	@Bean(initMethod = "init")
	public JobScheduler simpleJobScheduler2(final SimpleElasticJob2 simpleJob, @Value("${simpleJob2.cron}") final String cron,
			@Value("${simpleJob2.shardingTotalCount}") final int shardingTotalCount) {

		return new SpringJobScheduler(simpleJob, regCenter,
				getSimpleAJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount));
	}

	/**
	 * 简单定时任务
	 *
	 * @param jobClass
	 * @param cron
	 * @param shardingTotalCount
	 * @return
	 */
	private LiteJobConfiguration getSimpleAJobConfiguration(final Class<? extends SimpleJob> jobClass,
			final String cron, final int shardingTotalCount) {
		return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(
				JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount).build(),
				jobClass.getCanonicalName())).overwrite(true).build();
	}

}
