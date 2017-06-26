package com.tydic.unicom.service.workFlow.dataSource;


public class ChngDataSourceHolder {
	private static final ThreadLocal<Object> holder = new ThreadLocal<Object>();

    public static void setDbType(DbType dbType) {
        holder.set(dbType);
    }

    public static DbType getDbType() {
        return (DbType) holder.get();
    }

    public static void clearDbType() {
        holder.remove();
    }
}
