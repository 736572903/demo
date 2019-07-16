package com.demo.elasticjob.config;

import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.demo.elasticjob.DataFlowElasticJob;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 流式任务配置
 */
@Configuration
public class DataflowJobConfig {
	// 注册中心配置
	@Resource
	private ZookeeperRegistryCenter regCenter;

	// 将作业运行的痕迹进行持久化到DB的操作配置
//	@Resource
//	private JobEventConfiguration jobEventConfiguration;

	@Bean
	public DataFlowElasticJob dataflowJob() {
		return new DataFlowElasticJob();
	}

	@Bean(initMethod = "init")
	public JobScheduler dataflowJobScheduler(final DataFlowElasticJob dataflowJob,
			@Value("${dataflowJob.cron}") final String cron,
			@Value("${dataflowJob.shardingTotalCount}") final int shardingTotalCount,
			@Value("${dataflowJob.shardingItemParameters}") final String shardingItemParameters) {
		return new SpringJobScheduler(dataflowJob, regCenter,
				getLiteJobConfiguration(dataflowJob.getClass(), cron, shardingTotalCount, shardingItemParameters));
//				jobEventConfiguration);
	}

	private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends DataflowJob> jobClass, final String cron,
			final int shardingTotalCount, final String shardingItemParameters) {
		return LiteJobConfiguration
				.newBuilder(new DataflowJobConfiguration(
						JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount)
								.shardingItemParameters(shardingItemParameters).build(),
						jobClass.getCanonicalName(), true))
				.overwrite(true).build();
	}
}
