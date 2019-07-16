package com.demo.elasticjob.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

@Configuration
public class ElasticRegCenterConfig {
    /**
     * 配置zookeeper
     *
     * @param serverList
     * @param namespace
     * @return
     */
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter regCenter(
            @Value("${regCenter.serverList}") final String serverList,
            @Value("${regCenter.namespace}") final String namespace,
            @Value("${regCenter.base-sleep-time-milliseconds}") final int baseSleepTimeMilliseconds,
            @Value("${regCenter.max-sleep-time-milliseconds}") final int maxSleepTimeMilliseconds,
            @Value("${regCenter.connection-timeout-milliseconds}") final int connectionTimeoutMilliseconds,
            @Value("${regCenter.max-retries}") final int maxRetries) {
    	
    	ZookeeperConfiguration zkConfiguration = new ZookeeperConfiguration(serverList, namespace);
    	//最大重试次数
    	zkConfiguration.setMaxRetries(maxRetries);
    	//连接超时时间
    	zkConfiguration.setConnectionTimeoutMilliseconds(connectionTimeoutMilliseconds);
    	//等待重试的间隔时间的初始值  单位：毫秒
    	zkConfiguration.setBaseSleepTimeMilliseconds(baseSleepTimeMilliseconds);
    	//等待重试的间隔时间的最大值
    	zkConfiguration.setMaxSleepTimeMilliseconds(maxSleepTimeMilliseconds);
    	
        return new ZookeeperRegistryCenter(zkConfiguration);
    }
}
