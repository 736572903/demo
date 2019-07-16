package com.demo.service;

import java.util.List;
import com.demo.entity.OriginalBill;

public interface IOriginalBillService {
	
	//查询某一个用户的原始邮件
	List<OriginalBill> getOriginalBillByUserId(long khUserId);
	
	//查询某所有用户的原始邮件
	List<OriginalBill> getAllOriginalBill();
	
	void updateOriginalBill(OriginalBill originalBill);
}
