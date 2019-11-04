package com.demo.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


/**
 * https://mp.baomidou.com/guide/annotation.html#tablefield
 */
@TableName("m_bill_info")//@TableName中的值对应着表名
public class BillInfo implements Cloneable, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8669157146226034267L;
    /**
     * 主键
     *
     * @TableId中可以决定主键的类型,不写会采取默认值,默认值可以在yml中配置 AUTO: 数据库ID自增
     * INPUT: 用户输入ID
     * ID_WORKER: 全局唯一ID，Long类型的主键
     * ID_WORKER_STR: 字符串全局唯一ID
     * UUID: 全局唯一ID，UUID类型的主键
     * NONE: 该类型为未设置主键类型
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 信用卡主键
     */
    @TableField(value = "card_id")
    private Long cardId;

    /**
     * 原始账单表主键
     */
    @TableField(value = "original_bill_id")
    private Long originalBillId;

    /**
     * 逾期金额
     */
    private Double overdue;
    /**
     * 渠道
     */
    private String cid;

    /**
     * 创建时间
     */
    @TableField(value = "crt_time")
    private Timestamp crtTime;

    /**
     * 更新时间
     */
    @TableField(value = "upd_time")
    private Timestamp updateTime;

    /**
     * 账单类型
     */
    @TableField(value = "bill_type")
    private int billType;

    /**
     * 备注
     */
    @TableField(exist = false)
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getOriginalBillId() {
        return originalBillId;
    }

    public void setOriginalBillId(Long originalBillId) {
        this.originalBillId = originalBillId;
    }

    public Double getOverdue() {
        return overdue;
    }

    public void setOverdue(Double overdue) {
        this.overdue = overdue;
    }

    public Timestamp getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Timestamp crtTime) {
        this.crtTime = crtTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public int getBillType() {
        return billType;
    }

    public void setBillType(int billType) {
        this.billType = billType;
    }

    @Override
    public BillInfo clone() {
        try {
            return (BillInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
