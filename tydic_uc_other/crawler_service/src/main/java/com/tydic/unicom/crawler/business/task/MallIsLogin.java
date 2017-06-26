package com.tydic.unicom.crawler.business.task;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.URL;
import com.tydic.unicom.crawler.business.vo.BusRespMessage;
import com.tydic.unicom.crawler.common.SysPar;
import com.tydic.unicom.crawler.common.SysUtil;
import com.tydic.unicom.crawlerframe.model.CrawlDatum;
import com.tydic.unicom.crawlerframe.model.CrawlDatums;
import com.tydic.unicom.crawlerframe.model.Page;
import com.tydic.unicom.crawlerframe.net.MallHttpRequest;
import com.tydic.unicom.crawlerframe.net.MallHttpResponse;

import net.sf.json.JSONObject;

@Service
public class MallIsLogin extends BaseTask {
	public static final Logger logger = LoggerFactory.getLogger(MallIsLogin.class);

	public MallIsLogin() {
		super();
		
	}

	@Override
	public MallHttpResponse getExeResponse(CrawlDatum crawlDatum) throws Exception {
		try {
			// url = SysPar.ppvo.getBaselink() + SysPar.ppvo.getIsLogin();
			url = SysPar.ppvo.getBaselink() + "/ManagerIndex/index";

			this.datum = crawlDatum;
			MallHttpRequest mall = new MallHttpRequest(url);
			mall.setMethod("POST");
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
			mall.setHeader("Referer", SysPar.ppvo.getBaselink() + "/Odm/ManualAccount/init?mId=5250");
			mall.setHeader("X-Requested-With", "XMLHttpRequest");

			mall.setPar("mId", "5250");
			mall.setCookie(datum.getMetaData().get("sessioncookie"));

			logger.debug("sessioncookie : " + datum.getMetaData().get("sessioncookie"));

			MallHttpResponse mhrs = mall.response();
			Page pa = new Page(datum, mhrs);
			return mhrs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		// 请求必定返回JSON ，如果转换对象异常说明登录有问题
		// 非法访问未授权页面
		String tmp_context = page.html();
		brmsg = new BusRespMessage();
		LOG.debug(tmp_context);
		if (page.select("title").text().equals("商城后台主页")) {
			LOG.debug("登录验证成功");
			brmsg.setRespCode(BusRespMessage.SUCCESS);
			brmsg.setContent("登录验证成功");
		} else {
			LOG.debug("登录验证失败");
			brmsg.setRespCode(BusRespMessage.LOGINERR);
			brmsg.setContent("登录验证失败");
		}
	}
}
