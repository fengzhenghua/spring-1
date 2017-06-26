package com.tydic.unicom.crawler.business.task;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class MallLogin extends BaseTask {
	public static final Logger logger = LoggerFactory.getLogger(MallLogin.class);
	
	public MallLogin() {
		super();
	}
	
	@Override
	public MallHttpResponse getExeResponse(CrawlDatum crawlDatum) throws Exception {
		try {
			url = SysPar.ppvo.getBaselink() + SysPar.ppvo.getLogin();
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
			mall.setHeader("Origin", SysPar.ppvo.getBaselink());
			mall.setHeader("Referer", SysPar.ppvo.getBaselink()+"/ManagerLogin/init");
			mall.setHeader("X-Requested-With", "XMLHttpRequest");
			
			mall.setPar("staffCode", SysUtil.getStr(datum.getMetaData().get("staffCode")));
			mall.setPar("passWd", SysUtil.getStr(datum.getMetaData().get("passWd")));
			mall.setPar("safetyCode",SysUtil.getStr(datum.getMetaData().get("safetycode")));
			
			mall.setCookie(datum.getMetaData().get("basecookie"));
			
			logger.debug("staffCode : "+SysUtil.getStr(datum.getMetaData().get("staffCode")));
			logger.debug("passWd : "+SysUtil.getStr(datum.getMetaData().get("passWd")));
			logger.debug("safetyCode : "+SysUtil.getStr(datum.getMetaData().get("safetycode")));
			logger.debug("basecookie : "+datum.getMetaData().get("basecookie"));
			
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
		brmsg = new BusRespMessage();
		String tmp_context = page.html();
		try {
			JSONObject json = JSONObject.fromObject(tmp_context);
			String errCode = (String) json.get("ErrorCode");
			if (errCode.equals("1")) {
				LOG.debug("当前安全码与员工号不匹配，请核实");
				brmsg.setRespCode("9999");
				brmsg.setContent("当前安全码与员工号不匹配，请核实");
			} else if (errCode.equals("2")) {
				brmsg.setRespCode("9999");
				brmsg.setContent("输入的安全码不正确，请重新输入");
				LOG.debug("输入的安全码不正确，请重新输入");
			} else if (errCode.equals("3")) {
				brmsg.setRespCode("9999");
				brmsg.setContent("员工工号或密码不正确");
				LOG.debug("员工工号或密码不正确");
			}else{
				brmsg.setRespCode(BusRespMessage.SUCCESS);
				brmsg.setContent("登录正确");
				LOG.debug("登录正确");
			}
		} catch (
		Exception e) {
			// 提示登录错误，请检查连接地址是否改变或登录页面有改动
			e.printStackTrace();
			LOG.debug("登录错误，请检查连接地址是否改变或登录页面有改动,请求的放回不是登录JSON");
		}
	}

}
