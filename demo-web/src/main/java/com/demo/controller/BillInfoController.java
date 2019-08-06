package com.demo.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.demo.entity.BillInfo;
import com.demo.service.IBillInfoService;

/**
 * mybatis plus 的使用
 * https://mp.baomidou.com/guide/crud-interface.html#update官方文档
 *
 */
@RestController
@RequestMapping("")
public class BillInfoController {
	
	@Autowired
	private IBillInfoService billInfoService;
	
	@GetMapping("/queryBillInfo")
	public String queryBillInfo(){
		
		
		BillInfo billInfo = new BillInfo();
		billInfo.setBillType(0);
		billInfo.setOriginalBillId(0L);
		billInfo.setOverdue(0.00);
		billInfo.setCrtTime(new Timestamp(System.currentTimeMillis()));
		billInfo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		billInfo.setCardId(0L);
		BillInfo billInfo1 = billInfo.clone();
		
		//增加
		billInfoService.save(billInfo);
		System.out.println(String.format("插入主键id，%d", billInfo.getId()));
		
		//批量插入 ---- 不生效
		List<BillInfo> batchList = new ArrayList<BillInfo>();
		billInfo1.setCardId(77777L);
		batchList.add(billInfo1);
		boolean save = billInfoService.saveBatch(batchList);//抛出异常，触发回滚，不知道原因，批量插入时用循环插入
		System.out.println(String.format("是否批量插入成功，%s", save));
		
		//更新 根据 ID 选择修改
		billInfo.setOriginalBillId(9999L);
		billInfoService.updateById(billInfo);
		System.out.println(String.format("修改后的OriginalBillId，%d", billInfo.getOriginalBillId()));
		
		UpdateWrapper<BillInfo> userUpdateWrapper = new UpdateWrapper<>();
		userUpdateWrapper.set("card_id", 9999L)//设值
						 .setSql("original_bill_id = 8888")//设值
						 .eq("card_id", 0L).or().eq("card_id", 8888L);//查询条件
		billInfoService.update(userUpdateWrapper);
		
		//查询总条数
		int size = billInfoService.count();
		System.out.println(String.format("查询总条数，%d", size));
		
		//查询所有
		List<BillInfo> list = billInfoService.list();
		
		//条件查询
		QueryWrapper<BillInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("card_id","original_bill_id")//查询字段
					.gt("id", 1700L)//条件
					.between("id", 1600L, 1750L)//条件
					.groupBy("id")//group by
					.orderByDesc("id");//order by
		list = billInfoService.list(queryWrapper);
		System.out.println(String.format("条件查询数量，%d", list.size()));
		
		//自定义sql查询
		queryWrapper = new QueryWrapper<>();
		queryWrapper.exists("select * from m_bill_info where id > 1700");
		list = billInfoService.list(queryWrapper);
		System.out.println(String.format("自定义查询数量，%d", list.size()));
		
		//根据主键id删除
		boolean del = billInfoService.removeById(1810);
		System.out.println(String.format("是否删除，%s", del));
		
		//根据 columnMap 条件，删除记录
		Map<String, Object> columnMap = new HashMap<String, Object>();
		columnMap.put("card_id", 0);
		billInfoService.removeByMap(columnMap);
		
		return size+"";
	}

}
