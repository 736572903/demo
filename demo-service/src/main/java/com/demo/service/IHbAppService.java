package com.demo.service;

import com.demo.entity.HbApp;

public interface IHbAppService {
	
	void saveHbApp(HbApp hbApp);
	
	void saveHbAppNoTransaction();
	
}
