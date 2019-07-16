package com.demo.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class OriginalBill implements Serializable{
	
	/**
	 *
	 */
	private static final long serialVersionUID = -1278116642363250822L;

	/**
	 * 主键id
	 */
	private long id;
	
	/**
	 * 用户id
	 */
	private long khUserId;
	
	/**
	 * 邮件地址
	 */
	private String emailAddress;
	
	/**
	 * 下载时间
	 */
	private Timestamp downTime;
	
	/**
	 * 更新人
	 */
	private long lastUpdateUser;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getKhUserId() {
		return khUserId;
	}

	public void setKhUserId(long khUserId) {
		this.khUserId = khUserId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Timestamp getDownTime() {
		return downTime;
	}

	public void setDownTime(Timestamp downTime) {
		this.downTime = downTime;
	}

	public long getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(long lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	
}
