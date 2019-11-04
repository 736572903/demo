package com.demo.elasticjob;

import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.demo.entity.OriginalBill;
import com.demo.service.IOriginalBillService;

/**
 * elastic job  简单的定时任务
 */
public class SimpleElasticJob implements SimpleJob {

    private static final Logger logger = LoggerFactory.getLogger(SimpleElasticJob.class);

    @Autowired
    private IOriginalBillService originalService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void execute(ShardingContext shardingContext) {
//		logger.info(String.format("日志打印：%s", shardingContext.getShardingItem()));
//		List<OriginalBill> list = originalService.getAllOriginalBill();
//		if(list == null || list.size() == 0){
//			return;
//		}

//		for(int i=0; i<list.size(); i++){
////			try {
////				Thread.sleep(6000);
////			} catch (InterruptedException e) {
////			}
//			OriginalBill bill = list.get(i);
//			switchJob(shardingContext, bill);
//		}

    }

    private void switchJob(ShardingContext shardingContext, OriginalBill bill) {

        int shard = (int) (bill.getId() % 3);
        if (shard == shardingContext.getShardingItem()) {
            System.out.println("当前分片" + shardingContext.getShardingItem());

            bill.setLastUpdateUser(shard);
            originalService.updateOriginalBill(bill);
        }

//		switch (shardingContext.getShardingItem()) {
//        case 0: 
//            System.out.println("当前分片0："+shardingContext.getShardingItem());
//            break;
//        case 1: 
//        	System.out.println("当前分片1："+shardingContext.getShardingItem());
//            break;
//        case 2: 
//        	System.out.println("当前分片2："+shardingContext.getShardingItem());
//            break;
//		}

    }


}
