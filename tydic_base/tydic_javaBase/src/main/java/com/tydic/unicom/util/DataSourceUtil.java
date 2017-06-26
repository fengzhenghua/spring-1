package com.tydic.unicom.util;

import com.tydic.unicom.service.workFlow.dataSource.ChngDataSourceHolder;
import com.tydic.unicom.service.workFlow.dataSource.DbType;

/**
 * 
 * <p></p>
 * @author yangwen 2014-8-28 下午12:54:26
 * @ClassName DataSourceUtil
 * @Description TODO 数据源选择
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-8-28
 * @modify by reason:{方法名}:{原因}
 */
public class DataSourceUtil {
	public static String dataSourceStr = "dataSource";
	
	public static void chngDataSource(String driverId){
		DbType dbType = DbType.valueOf(dataSourceStr+driverId);
		ChngDataSourceHolder.setDbType(dbType);
	}
}
