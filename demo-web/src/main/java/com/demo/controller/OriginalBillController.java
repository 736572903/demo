package com.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.dubbo.config.annotation.Reference;
import com.demo.entity.HbApp;
import com.demo.entity.OriginalBill;
import com.demo.service.IHbAppService;
import com.demo.service.IOriginalBillService;


/**
 * 
 * http://localhost:8081/bill/queryOriginalBill
 * @date 2019年6月24日
 */
@Controller
@RequestMapping("/bill")
public class OriginalBillController {
	
	@Autowired
    private IOriginalBillService originalService;
	
	@Autowired
	//dubbo包的Reference，当springboot和dubbo远程调用服务的时候，需要使用这个
//	@Reference(version = "1.0.0")
	IHbAppService hbAppService;
	
	@GetMapping("/queryOriginalBill")
	@ResponseBody
	public String queryOriginalBill(){
		
		List<OriginalBill> list = originalService.getOriginalBillByUserId(193);
		if(list != null && list.size() > 0){
			OriginalBill bill = list.get(0);
			bill.setEmailAddress("ceshi2");
			originalService.updateOriginalBill(bill);
		}
		
		try {
			HbApp hbApp = new HbApp();
			hbApp.setName("name3");
			hbApp.setCid(1);
			hbApp.setStatus(1);
			hbApp.setContact("contact");
			hbAppService.saveHbApp(hbApp);
		} catch (Exception e) {
			System.out.println(String.format("捕捉：%s", e.getMessage()));
		}
		hbAppService.saveHbAppNoTransaction();
		return list.toString();
		
	}
	
}
