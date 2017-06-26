package com.tydic.unicom.test.spring;

import javax.sql.DataSource;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(JUnit4TestClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:application-test.xml"})
/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年3月24日 下午4:12:22
 * @ClassName SpringTransactionalTestCase
 * @Description 带事务的测试用例基类
 * @version V1.0
 */
public abstract class SpringTransactionalTestCase extends AbstractTransactionalJUnit4SpringContextTests {
	
	protected DataSource dataSource;
	static Logger log = LoggerFactory.getLogger(SpringTransactionalTestCase.class);
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
		this.dataSource = dataSource;
	}
	
	protected void setupDB(String[] tableNames) {
		if (null != tableNames)
			for (String table : tableNames) {
				String filePath = "db/" + table.toLowerCase() + ".sql";
				log.debug("execute sql:" + filePath);
				executeSqlScript(filePath, true);
			}
	}
	
	protected Logger log() {
		return log;
	}
}
