package com.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.datasource.DataSource;
import com.demo.entity.BillInfo;
import com.demo.mapper.BillInfoDao;
import com.demo.service.IBillInfoService;

@Service(value = "billInfoService")
@DataSource(source = "test01")
public class BillInfoServiceImpl extends ServiceImpl<BillInfoDao, BillInfo> implements IBillInfoService {

    @Autowired
    private BillInfoDao dao;

}
