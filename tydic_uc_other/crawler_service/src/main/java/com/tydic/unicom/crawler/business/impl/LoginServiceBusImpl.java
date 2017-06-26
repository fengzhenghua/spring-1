package com.tydic.unicom.crawler.business.impl;

import java.util.HashMap;

import org.apache.http.Header;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.tydic.unicom.crawler.business.interfaces.LoginServiceBus;
import com.tydic.unicom.crawler.business.task.MallIsLogin;
import com.tydic.unicom.crawler.business.task.MallLogin;
import com.tydic.unicom.crawler.business.task.OrderManualAccount;
import com.tydic.unicom.crawler.business.vo.BusRespMessage;
import com.tydic.unicom.crawler.business.vo.BusinessResParamVo;
import com.tydic.unicom.crawler.common.BustoMallVo;
import com.tydic.unicom.crawler.common.ProParamVo;
import com.tydic.unicom.crawler.common.SysPar;
import com.tydic.unicom.crawler.dao.po.Crawler_Mall_UserPo;
import com.tydic.unicom.crawler.service.impl.Crawler_Mall_UserServImpl;
import com.tydic.unicom.crawlerframe.model.CrawlDatum;



/**
 * 确认线程安全后在把业务方法封装，现在每个业务方法都有单独的实现，代码过多。
 * @author xx
 */
public class LoginServiceBusImpl extends BusBase implements LoginServiceBus {
	private static Logger logger = Logger.getLogger(LoginServiceBusImpl.class);

	@Autowired
	Crawler_Mall_UserServImpl crawlermalluserservimpl;
	
	@Autowired
	MallLogin malllogin;
	
	@Autowired
	MallIsLogin mallislogin;
	
	BusRespMessage brmsg = new BusRespMessage();
	HashMap<String, String> map = new HashMap();
	
	@Override
	public BusRespMessage loginMethod(BusinessResParamVo businessResParamVo) throws Exception {
		beginlogger(logger,"loginMethod", businessResParamVo);

		Crawler_Mall_UserPo cmu = checkuser(businessResParamVo);
		if(cmu==null){
			brmsg = new BusRespMessage();
			brmsg.setRespCode(BusRespMessage.SUCCESS);
			brmsg.setContent("未找到对应的用户"+businessResParamVo.getUser());
			endloginfo(logger,this.getClass().getName()+"->[loginMethod] 结束参数:"+brmsg.toString());
			return brmsg;
		}
		
		map.put("basecookie", cmu.getCrawmu_basereqcookie() + cmu.getCrawmu_rescookie());
		map.put("staffCode", businessResParamVo.getUser());
		map.put("passWd", businessResParamVo.getPassword());
		map.put("safetycode", businessResParamVo.getSafecode());
		
		CrawlDatum maincrawldatum = new CrawlDatum();
		maincrawldatum.setMetaData(map);
		
		malllogin.execute(maincrawldatum);
		
		if (malllogin.getBrmsg().getRespCode().equals("0")) {
			String servercookie = malllogin.getResponse().cookie;
			logger.info(servercookie);
			Header[] h = malllogin.getResponse().httpResponse.getAllHeaders();
			for (int i = 0; i < h.length; i++) {
				logger.info(h[i].getName() + "   " + h[i].getValue());
			}
			logger.debug("请求返回的COOKIE:" + servercookie + cmu.getCrawmu_basereqcookie() + cmu.getCrawmu_rescookie());
			cmu.setCrawmu_sessioncookie(servercookie + cmu.getCrawmu_basereqcookie() + cmu.getCrawmu_rescookie());
			cmu.setCrawmu_pwd(businessResParamVo.getPassword());
			crawlermalluserservimpl.updatesession(cmu);
			brmsg.setRespCode(BusRespMessage.SUCCESS);
			brmsg.setContent("登录成功");
//			logger.info("登录成功:" + cmu.getCrawmu_name());
			endloginfo(logger,this.getClass().getName()+"->[loginMethod] "+cmu.getCrawmu_name()+"登录成功。结束参数:"+brmsg.toString());

		} else {
			brmsg = malllogin.getBrmsg();
//			logger.info("登录失败:" + brmsg.getContent());
			endloginfo(logger,this.getClass().getName()+"->[loginMethod] 结束参数:"+brmsg.toString());

		}

		return brmsg;
	}

	@Override
	public BusRespMessage isLoginMethod(BusinessResParamVo businessResParamVo) throws Exception {
		beginlogger(logger,"loginMethod", businessResParamVo);

		Crawler_Mall_UserPo cmu = checkuser(businessResParamVo);
		if(cmu==null){
			brmsg = new BusRespMessage();
			brmsg.setRespCode("9999");
			brmsg.setContent("请确认用户名和密码是否正确："+businessResParamVo.getUser());
			endloginfo(logger,this.getClass().getName()+"->[isLoginMethod] 结束参数:"+brmsg.toString());
			return brmsg;
		}
		
//		if(!cmu.getCrawmu_pwd().equals(businessResParamVo.getPassword())){
//			brmsg.setRespCode("9999");
//			brmsg.setContent("用户名和密码错误，请确认输入是否正确");
//			endloginfo(logger,this.getClass().getName()+"->[isLoginMethod] 结束参数:"+brmsg.toString());
//			return brmsg;
//		}
		
		map.put("sessioncookie", cmu.getCrawmu_sessioncookie());
		
		CrawlDatum maincrawldatum = new CrawlDatum();
		maincrawldatum.setMetaData(map);
		
		try{
			mallislogin.execute(maincrawldatum);
		}catch (Exception e) {
			brmsg.setRespCode("9999");
			brmsg.setContent("session失效，请从新登陆！");
			endloginfo(logger,this.getClass().getName()+"->[isLoginMethod] 结束参数:"+brmsg.toString());
			return brmsg;
		}
		
		if (mallislogin.getBrmsg().getRespCode().equals("0")) {
			String servercookie = mallislogin.getResponse().cookie;
			logger.info(servercookie);
			Header[] h = mallislogin.getResponse().httpResponse.getAllHeaders();
			for (int i = 0; i < h.length; i++) {
				logger.info(h[i].getName() + "   " + h[i].getValue());
			}
			logger.debug("请求返回的COOKIE:" + servercookie + cmu.getCrawmu_basereqcookie() + cmu.getCrawmu_rescookie());
			cmu.setCrawmu_sessioncookie(servercookie + cmu.getCrawmu_basereqcookie() + cmu.getCrawmu_rescookie());
			cmu.setCrawmu_pwd(businessResParamVo.getPassword());
//			crawlermalluserservimpl.updatesession(cmu);
			brmsg.setRespCode(BusRespMessage.SUCCESS);
			brmsg.setContent("登录成功");
//			logger.info("登录成功:" + cmu.getCrawmu_name());
		} else {
			brmsg = mallislogin.getBrmsg();
//			logger.info(brmsg.getContent());
		}
		endloginfo(logger,this.getClass().getName()+"->[isLoginMethod] 结束参数:"+brmsg.toString());

		return brmsg;
	}

	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml" });
		SysPar.ppvo = (ProParamVo) context.getBean("proParamVo");
		LoginServiceBusImpl lm = (LoginServiceBusImpl) context.getBean("LoginServiceBus");
		
		BusinessResParamVo bvo = new BusinessResParamVo();
		bvo.setUser("CQCSGZ1");
		bvo.setPassword("ran20070706");
		bvo.setSafecode("215946");
		
		try {
			lm.loginMethod(bvo);
//			lm.isLogin(bvo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
