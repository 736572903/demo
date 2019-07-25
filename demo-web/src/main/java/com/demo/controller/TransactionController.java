package com.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.entity.OriginalBill;
import com.demo.service.IHbAppService;
import com.demo.service.IOriginalBillService;


/**
 * 
 * http://localhost:8081/bill/queryOriginalBill
 */
@Controller
@RequestMapping("")
public class TransactionController {
	
	@Autowired
    private IOriginalBillService originalService;
	
	@Autowired
	//dubbo包的Reference，当springboot和dubbo远程调用服务的时候，需要使用这个
//	@Reference(version = "1.0.0")
	IHbAppService hbAppService;
	
	@GetMapping("/useTransaction")
	@ResponseBody
	public String useTransaction(){
		
		//数据源1 正常查询
		List<OriginalBill> list = originalService.getOriginalBillByUserId(193);
		if(list != null && list.size() > 0){
			OriginalBill bill = list.get(0);
			bill.setEmailAddress("ceshi2");
			originalService.updateOriginalBill(bill);
		}
		
		try {
			//数据源2 只对一个数据源操作并成功回滚
			hbAppService.saveHbAppHasTransaction();
		} catch (Exception e) {
//			System.out.println(String.format("捕捉：%s", e.getMessage()));
		}
		
		//数据源2 正常存储
		hbAppService.saveHbAppNoTransaction();
		return list.toString();
		
	}
	
	
	//两个不同数据源均回滚，事务生效
	@Transactional
	@GetMapping("/testMutiDataRollback")
	@ResponseBody
	public void testMutiDataRollback(){
		
		OriginalBill bill = Optional.ofNullable(originalService.getOriginalBillByUserId(193))
				.map(list -> list.get(0)).orElse(new OriginalBill());
		
		bill.setEmailAddress("ceshi5");
		originalService.updateOriginalBill(bill);
		
		hbAppService.saveHbAppNoTransaction();
		
		double a = 1/0;
		
	}
	
}
