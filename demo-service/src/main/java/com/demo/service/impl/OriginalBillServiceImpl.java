 package com.demo.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.datasource.DataSource;
import com.demo.entity.OriginalBill;
import com.demo.mapper.OriginalBillDao;
import com.demo.service.IOriginalBillService;

@Service(value = "originalService")
@DataSource(source = "test01")
public class OriginalBillServiceImpl implements IOriginalBillService {
	
	private static final Logger logger = LoggerFactory.getLogger(OriginalBillServiceImpl.class);
	
	@Autowired
	private OriginalBillDao dao;

	@Override
	public List<OriginalBill> getOriginalBillByUserId(long khUserId) {
		return dao.getOriginalBillByUserId(khUserId);
	}

	@Override
	public List<OriginalBill> getAllOriginalBill() {
		return dao.getAllOriginalBill();
	}

	@Override
	public void updateOriginalBill(OriginalBill originalBill) {
		logger.info(String.format("当前操作的原始账单主键id为：%d", originalBill == null ? 0 : originalBill.getId()));
		dao.updateOriginalBill(originalBill); 
	}
	
}
