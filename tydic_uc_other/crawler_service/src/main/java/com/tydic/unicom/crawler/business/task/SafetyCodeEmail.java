package com.tydic.unicom.crawler.business.task;

import java.net.MalformedURLException;
import java.util.Set;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tydic.unicom.crawler.common.SysPar;
import com.tydic.unicom.crawler.common.SysUtil;
import com.tydic.unicom.crawlerframe.mail.OpenMailFindPwd;
import com.tydic.unicom.crawlerframe.model.CrawlDatum;
import com.tydic.unicom.crawlerframe.model.CrawlDatums;
import com.tydic.unicom.crawlerframe.model.Page;
import com.tydic.unicom.crawlerframe.net.MallHttpRequest;
import com.tydic.unicom.crawlerframe.net.MallHttpResponse;
@Service
public class SafetyCodeEmail extends BaseTask {
	
	public static final Logger LOG = LoggerFactory.getLogger(SafetyCodeEmail.class);
	public String requestcookie="";
	
	public SafetyCodeEmail() {
		super();
	}

	public void init(){
	}
	
	@Override
	public MallHttpResponse getExeResponse(CrawlDatum crawlDatum)throws Exception {
		try {
			url = SysPar.ppvo.getBaselink() + SysPar.ppvo.getGetsafetycode();
			LOG.info("开始生成动态的客户端COOKIE");
			StringBuilder sb = new StringBuilder();
	        HtmlUnitDriver driver = new HtmlUnitDriver();
	        driver.setJavascriptEnabled(true);
	        driver.get(SysPar.ppvo.getBaselink());
	        WebElement merchantId = driver.findElementByCssSelector("input[id=merchantId]");
	        WebElement email = driver.findElementByCssSelector("input[id=email]");
	        email.click();
	        WebElement submit = driver.findElementByCssSelector("input[id=merchantSafetyCodeBtn]");
//	        submit.click();
	        Set<Cookie> cookieSet = driver.manage().getCookies();
	        
	        for (Cookie cookie : cookieSet) {
	        		 sb.append(cookie.getName()+"="+cookie.getValue()+";");
	        }
	        requestcookie=sb.toString();
	        driver.close();
			
			
//			LOG.info("=============================   kaishi request!");
			MallHttpRequest mall = new MallHttpRequest(url);
			mall.setMethod("POST");
			mall.setCookie(requestcookie);
			String host = "admin.mall.10010.com";
			try {
				if (SysPar.ppvo != null && SysPar.ppvo.getBaselink() != null && !SysPar.ppvo.getBaselink().isEmpty()) {
					java.net.URL url = new java.net.URL(SysPar.ppvo.getBaselink());
					host = url.getHost();
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mall.setHeader("Host", host);
			mall.setHeader("Origin", SysPar.ppvo.getBaselink());
			mall.setHeader("Referer", SysPar.ppvo.getBaselink()+"ManagerLogin/init");
			
			mall.setPar("staffCode",SysUtil.getStr(datum.getPar("CRAWMU_NAME")));
			mall.setPar("sendType","email");
//			mall.setPar("staffCode", SysPar.ppvo.getMallstaffcode());
//			LOG.info("=============================   staffCode   :"+SysPar.ppvo.getMallstaffcode());
//			mall.setPar("sendType",SysPar.ppvo.getMallsendtype());
//			LOG.info("=============================    sendType   :"+SysPar.ppvo.getMallsendtype());
			MallHttpResponse mhrs = mall.response();
//			Page pa = new Page(datum, mhrs);
			return mhrs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		
	}

	public String getSaftcode() {
		OpenMailFindPwd omfp = new OpenMailFindPwd();
		try {
			return omfp.getSaftcode();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
