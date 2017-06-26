package com.tydic.unicom.crawler.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.tydic.unicom.crawler.common.SysUtil;
import com.tydic.unicom.crawler.dao.interfaces.Crawler_InfoDao;
import com.tydic.unicom.crawler.dao.interfaces.Crawler_InfocallbackDao;
import com.tydic.unicom.crawler.dao.po.Crawler_InfoPo;
import com.tydic.unicom.crawler.dao.po.Crawler_InfocallbackPo;
import com.tydic.unicom.crawler.service.interfaces.Crawler_InfoCallbackServ;
import com.tydic.unicom.crawler.service.interfaces.Crawler_InfoServ;

@Service()
public class Crawler_InfoCallbackServImpl implements Crawler_InfoCallbackServ{
	@Autowired
	Crawler_InfocallbackDao cs;// = new CrawlerSerImpl();

	@Override
	public boolean create(Crawler_InfocallbackPo po) throws Exception {
		try {
			Crawler_InfoPo c = cs.get(po);
			if(c ==null){
				cs.create(po);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public boolean delete(Crawler_InfocallbackPo po) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Crawler_InfocallbackPo po) throws Exception {
		try {
			return cs.update(po);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Crawler_InfocallbackPo get(Crawler_InfocallbackPo po) throws Exception {
		try {
			Crawler_InfocallbackPo cp = cs.get(po);
			return cp;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<Crawler_InfocallbackPo> query(Crawler_InfocallbackPo po) throws Exception {
		try {
			String sqlwhere  = "";
			if(!SysUtil.getStr(po.getOrder_id()).isEmpty()){
				sqlwhere = sqlwhere+ " and order_id = '"+ po.getOrder_id() +"'";
			}
			if(!SysUtil.getStr(po.getOrder_malltype()).isEmpty()){
				sqlwhere = sqlwhere + " and Order_malltype = '"+ po.getOrder_malltype() +"'";
			}
			if(!SysUtil.getStr(po.getOrder_status()).isEmpty()){
				sqlwhere =sqlwhere + " and ORDER_STATUS = '"+ po.getOrder_status() +"'";
			}
			po.setSql(sqlwhere);
			List<Crawler_InfocallbackPo> cp = cs.query(po);
			return cp;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	
	
	

	
	public void addOrderinfo(Crawler_InfocallbackPo cpp) throws Exception {
		try {
			Crawler_InfocallbackPo c = cs.get(cpp);
			if(c ==null){
				cs.create(cpp);
			}else{
				System.out.println(c.getOrder_info());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}		
	}
	
	public List<Crawler_InfocallbackPo> querylist(Crawler_InfocallbackPo cpp) throws Exception {
		try {
			String sqlwhere  = "";
			if(!SysUtil.getStr(cpp.getOrder_id()).isEmpty()){
				sqlwhere = sqlwhere+ " and order_id = '"+ cpp.getOrder_id() +"'";
			}
			if(!SysUtil.getStr(cpp.getOrder_malltype()).isEmpty()){
				sqlwhere = sqlwhere + " and Order_malltype = '"+ cpp.getOrder_malltype() +"'";
			}
			if(!SysUtil.getStr(cpp.getOrder_status()).isEmpty()){
				sqlwhere =sqlwhere + " and ORDER_STATUS = '"+ cpp.getOrder_status() +"'";
			}
			cpp.setSql(sqlwhere);
			List<Crawler_InfocallbackPo> cp = cs.query(cpp);
			return cp;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public boolean addCrawlerInfoCallBack(Crawler_InfocallbackPo crawler_InfocallbackPo) throws Exception {
		boolean isok =false;
		Crawler_InfocallbackPo queryReult = cs.queryByCiuuidAndTachecode(crawler_InfocallbackPo);
		if(queryReult != null){
			isok = cs.update(queryReult);
		}
		else{
			isok = cs.create(crawler_InfocallbackPo);
		}
		return isok;
	}

	
}
