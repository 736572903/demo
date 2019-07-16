package com.demo.elasticjob;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

/**
 * elastic job  数据流定时任务
 */
public class DataFlowElasticJob implements DataflowJob<String>{
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public List<String> fetchData(ShardingContext context) {
		int item = context.getShardingItem();
		
		List<String> list = new ArrayList<String>();
		
		switch (item) {
		case 0:
			list.add("0");
			list.add("00");
			list.add("000");
			break;
		case 1:
			list.add("1");
			list.add("11");
			list.add("111");
			break;
		case 2:
			list.add("2");
			list.add("22");
			list.add("222");
			break;
		default:
			break;
		}
		
		return list;
	}

	@Override
	public void processData(ShardingContext context, List<String> list) {
		System.out.println("\n"+sdf.format(new Date()));
		System.out.println(String.format("当前分片:%d,list数据:%s,%s,%s",context.getShardingItem() , list.get(0), list.get(1), list.get(2)));
	}

}
