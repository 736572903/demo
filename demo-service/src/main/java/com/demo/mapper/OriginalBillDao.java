package com.demo.mapper;

import java.util.List;

import com.demo.entity.OriginalBill;

//@Mapper
public interface OriginalBillDao {

    //	@Select("SELECT id,email_address,kh_user_id,email_address,down_time FROM m_original_bill WHERE kh_user_id=#{khUserId}")
    List<OriginalBill> getOriginalBillByUserId(long khUserId);

    List<OriginalBill> getAllOriginalBill();

    void updateOriginalBill(OriginalBill originalBill);

}
