package com.tydic.unicom.crm.Test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class TestProducer {
	
	public static void main(String[] args) {
		 //加载驱动
		   try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			//DriverManager.registerDriver(new sun.jdbc.odbc.JdbcOdbcDriver());
			//获得连接
			   Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@135.24.252.29:9521:testdb","upc","UPC");


			         //创建存储过程的对象
			         CallableStatement c=conn.prepareCall("{call PAYCENTRAL_TRADE_TIME(?)}");
			        
			         //给存储过程的参数设置值
			        c.setInt(1,20170223);   //将第一个参数的值设置成100
			        
			         //执行存储过程
			         c.execute();
			         conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
