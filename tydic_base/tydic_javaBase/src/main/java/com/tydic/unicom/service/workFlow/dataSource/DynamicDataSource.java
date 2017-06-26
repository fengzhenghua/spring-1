package com.tydic.unicom.service.workFlow.dataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class DynamicDataSource extends AbstractRoutingDataSource {
	private static Logger logger = Logger
			.getLogger(DynamicDataSource.class);
	
	/**
	 * 
	 * @author yangwen 2014-8-28 上午11:18:31
	 * @Method: determineCurrentLookupKey 
	 * @Description: TODO 取得一个字符串，该字符串将与配置文件中的相应字符串进行匹配以定位数据源
	 * @return 
	 * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		DbType key = ChngDataSourceHolder.getDbType();//获得当前数据源标识符
		logger.info("当前数据源 :" + key);
		
		return key;
	}
	
}
