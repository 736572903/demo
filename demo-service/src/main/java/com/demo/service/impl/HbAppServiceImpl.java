package com.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
//import com.alibaba.dubbo.config.annotation.Service;
import com.demo.datasource.DataSource;
import com.demo.entity.HbApp;
import com.demo.mapper.HbAppDao;
import com.demo.service.IHbAppService;

//dubbo包的service，当springboot和dubbo远程调用服务的时候，需要使用这个
//@Service(version = "1.0.0", interfaceClass=IHbAppService.class)
@Service(value = "hbAppService")
@DataSource(source = "test02")
public class HbAppServiceImpl implements IHbAppService{
	
	@Autowired
	HbAppDao hbAppDao;
	
	@Override
	@Transactional(transactionManager="transactionManager2", rollbackFor=Exception.class, 
			propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void saveHbApp(HbApp hbApp) {
		
		hbAppDao.saveHbApp(hbApp);
		
		HbApp hbApp1 = new HbApp();
		hbApp1.setName("name4");
		hbApp1.setCid(1);
		hbApp1.setStatus(1);
		hbApp1.setContact("contact");
		hbAppDao.saveHbApp(hbApp1);
		
		//触发回滚
		hbApp = null;
		hbApp.getCid();
//		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}
	
	@Override
	public void saveHbAppNoTransaction() {
		
		HbApp hbApp1 = new HbApp();
		hbApp1.setName("noTransaction");
		hbApp1.setCid(1);
		hbApp1.setStatus(1);
		hbApp1.setContact("contact");
		hbAppDao.saveHbApp(hbApp1);
		
	}

}
