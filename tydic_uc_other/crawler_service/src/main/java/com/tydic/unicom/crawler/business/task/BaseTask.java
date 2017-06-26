package com.tydic.unicom.crawler.business.task;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.tydic.unicom.crawler.business.vo.BusRespMessage;
import com.tydic.unicom.crawler.common.BustoMallVo;
import com.tydic.unicom.crawler.common.ProParamVo;
import com.tydic.unicom.crawlerframe.model.CrawlDatum;
import com.tydic.unicom.crawlerframe.model.CrawlDatums;
import com.tydic.unicom.crawlerframe.model.Executor;
import com.tydic.unicom.crawlerframe.model.Links;
import com.tydic.unicom.crawlerframe.model.Page;
import com.tydic.unicom.crawlerframe.model.Visitor;
import com.tydic.unicom.crawlerframe.net.MallHttpRequest;
import com.tydic.unicom.crawlerframe.net.MallHttpResponse;
import com.tydic.unicom.crawlerframe.net.Requester;
import com.tydic.unicom.crawlerframe.util.RegexRule;

/**
 * 单个爬取的爬取基类，不包连续的含业务
 * @author xx
 *
 */
public class BaseTask extends Crawler implements Executor, Visitor, Requester {
	
	public static final Logger LOG = LoggerFactory.getLogger(BaseTask.class);
	/**
	 * 是否自动抽取符合正则的链接并加入后续任务
	 */
	// protected boolean autoParse = true;
	protected boolean parseImg = false;

	protected Visitor visitor;
	// 接口
	protected Requester requester;
	protected MallHttpResponse response;
	// protected MallHttpResponse response;

	BusRespMessage brmsg = new BusRespMessage();
	
	protected String url = new String();

	public BaseTask() {
		// this.autoParse = true;
		this.visitor = this;
		this.requester = this;
	}

	@Override
	public MallHttpResponse getExeResponse(CrawlDatum crawlDatum) throws Exception {
		// 发送链接，得到返回
		MallHttpRequest request = new MallHttpRequest(crawlDatum);
		return request.response();
	}

	/**
	 * URL正则约束
	 */
	protected RegexRule regexRule = new RegexRule();
//	protected BustoMallVo btm;
	protected CrawlDatum datum;
	protected Page page;

	@Override
	public void execute(CrawlDatum datum, CrawlDatums next) throws Exception {
		init(datum);
		response = requester.getExeResponse(datum);
		page = new Page(datum, response);
		visitor.visit(page, next);
		// if (regexRule.isEmpty()) {
		// parseLink(page, next);
		// }
		afterParse(page, next);
	}

	public void execute(CrawlDatum datum) throws Exception {
		if (datum == null)
			datum=(new CrawlDatum(url));
		
		if(datum.url()==null || datum.url().isEmpty()){
			datum.url(url);
		}
		this.execute(datum, null);
	}

//	public void execute(CrawlDatum datum) throws Exception {
////		btm = datum;
//		this.execute(btm,null);
//	}

	protected void afterParse(Page page, CrawlDatums next) {

	}

	protected void parseLink(Page page, CrawlDatums next) {
		String conteType = page.contentType();
		if (conteType != null && conteType.contains("text/html")) {
			Document doc = page.doc();
			if (doc != null) {
				Links links = new Links().addByRegex(doc, regexRule, parseImg);
				next.add(links);
			}
		}

	}

	/**
	 * 添加URL正则约束
	 *
	 * @param urlRegex
	 *            URL正则约束
	 */
	public void addRegex(String urlRegex) {
		regexRule.addRule(urlRegex);
	}

	/**
	 * 获取正则规则
	 *
	 * @return 正则规则
	 */
	public RegexRule getRegexRule() {
		return regexRule;
	}

	/**
	 * 设置正则规则
	 *
	 * @param regexRule
	 *            正则规则
	 */
	public void setRegexRule(RegexRule regexRule) {
		this.regexRule = regexRule;
	}

	/**
	 * 获取Visitor
	 *
	 * @return Visitor
	 */
	public Visitor getVisitor() {
		return visitor;
	}

	/**
	 * 设置Visitor
	 *
	 * @param visitor
	 *            Visitor
	 */
	public void setVisitor(Visitor visitor) {
		this.visitor = visitor;
	}

	// public Requester getExeRequester() {
	// return requester;
	// }

	// public void setRequester(Requester requester) {
	// this.requester = requester;
	// }

	public boolean isParseImg() {
		return parseImg;
	}

	public void setParseImg(boolean parseImg) {
		this.parseImg = parseImg;
	}

	/**
	 * 得到执行完成后的对象
	 * 
	 * @return
	 */
	public final MallHttpResponse getResponse() {
		return response;
	}

	public Page getpage() {
		return page;
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		// TODO Auto-generated method stub

	}

	public BusRespMessage getBrmsg() {
		return brmsg;
	}

	public void setBrmsg(BusRespMessage brmsg) {
		this.brmsg = brmsg;
	}

	@Override
	public void init(CrawlDatum datum) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
