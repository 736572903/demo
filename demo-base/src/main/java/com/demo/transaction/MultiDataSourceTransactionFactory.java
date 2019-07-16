package com.demo.transaction;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
 
import javax.sql.DataSource;

public class MultiDataSourceTransactionFactory extends SpringManagedTransactionFactory {
	
	
	public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
		return new MultiDataSourceTransaction(dataSource);
	}
}
