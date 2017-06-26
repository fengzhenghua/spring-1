package com.tydic.unicom.crawler.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.DataService;
import com.tydic.uda.service.S;
import com.tydic.unicom.crawler.dao.interfaces.Crawler_Mall_UserDao;
import com.tydic.unicom.crawler.dao.po.Crawler_Mall_UserPo;

@Service
public class Crawler_Mall_UserDaoImpl implements Crawler_Mall_UserDao {

	private static Logger logger = Logger.getLogger(Crawler_Mall_UserDaoImpl.class);
	private static DataService<Crawler_Mall_UserPo> s = S.get(Crawler_Mall_UserPo.class);

	@Override
	public boolean create(Crawler_Mall_UserPo po) throws Exception {
		s.create(po);
		return false;
	}

	@Override
	public boolean delete(Crawler_Mall_UserPo po) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Crawler_Mall_UserPo po) throws Exception {
		return s.update(po) == 1;
	}

	@Override
	public Crawler_Mall_UserPo get(Crawler_Mall_UserPo po) throws Exception {
		return s.get(po);
	}

	@Override
	public List<Crawler_Mall_UserPo> query(Crawler_Mall_UserPo po) throws Exception {
		Map map = new HashMap();
		Condition condition = Condition.build("getmalluser").filter(map);
		List<Crawler_Mall_UserPo> list = s.query(condition);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml" });
		// SysPar.ppvo = (ProParamVo) context.getBean("proParamVo");
		Crawler_Mall_UserDaoImpl t = (Crawler_Mall_UserDaoImpl) context.getBean("crawler_Mall_UserDaoImpl");
		try {
			Crawler_Mall_UserPo cpp = new Crawler_Mall_UserPo();
			cpp.setCrawmu_name("1");
			cpp.setCrawmu_pwd("1");
			
			cpp = t.get(cpp);
			cpp.setCrawmu_rescookie("111221");
			t.update(cpp);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
