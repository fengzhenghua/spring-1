package com.tydic.unicom.crawler.business.task.order5270;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tydic.unicom.crawler.business.task.BaseTask;
import com.tydic.unicom.crawler.common.SysPar;
import com.tydic.unicom.crawler.common.SysUtil;
import com.tydic.unicom.crawlerframe.model.CrawlDatum;
import com.tydic.unicom.crawlerframe.model.CrawlDatums;
import com.tydic.unicom.crawlerframe.model.Page;
import com.tydic.unicom.crawlerframe.net.MallHttpRequest;
import com.tydic.unicom.crawlerframe.net.MallHttpResponse;

import net.sf.json.JSONObject;
/**
 * 订单领取 查询列表
 * toDetail
 * @author xx
 *
 */
@Component()
public class OrderToDetails extends BaseTask {
	public static final Logger LOG = LoggerFactory.getLogger(OrderToDetails.class);
	CrawlDatum maincrawldatum = new CrawlDatum();
	
	public String orderid = "";
	
	public OrderToDetails() {
		super();
	}
	@Override
	public void init(CrawlDatum datum) throws Exception {
		super.init(datum);
		url = SysPar.ppvo.getBaselink()+"/OdmQuery/OrderDataQuery/toDetail";
	}
	@Override
	public MallHttpResponse getExeResponse(CrawlDatum crawlDatum) throws Exception {
		try {
			MallHttpRequest mall = new MallHttpRequest(url);
			mall.setMethod("POST");
//			String ss = "RandomCodeCQCSGZ1=h/XDXFbhc+XLHy6bmKKMKQ==; _n3fa_cid=8fadefb240e14400fa2ace9f65af37af; _n3fa_ext=ft=1490470827; _n3fa_lvt_a9e72dfe4a54a20c3d6e671b3bad01d9=1490665157,1490672267,1490680739,1490680851; _n3fa_lpvt_a9e72dfe4a54a20c3d6e671b3bad01d9=1490680948; AdminStaff=qITWpHb/vJa5Bx9l1aQmaZIDzUlNpBjLfcXINni36LodImz0f0xxrr2qYWSwPNHqT5bIeCdgdp/j8HPcplbNXkOWXNJBvs8UICJwpPg4Qp0GlLFzR1vmDLerOQWdJXKRWuNqESrMYU7mCaBy9KRuVv7bOJ9ohQeX86EC0Xs8PEcnxndgWPZhGWc8uRWfZbCaCAv+Qtya06PIBAQpeaWiOxluPSI/qQTCJoRpznFwVGA+bon39UDqoB53INQ/UaiGb9V6AKr0UvJ54QVQULVfaoAdHUMWHtghMTroaVzOI0YiCq7DrO7yNQfWUX6bUoz/Q+5UnMTZedkeg43rqfpYGWhQQW9RSMEnjdihXhyqy/ZE4sDC6AWXU5YVy0EqzkLJn1B035Lol8a2K/Mkp+/tBqXcloTP82QvqigtsbdDgdJ8ha7hvCCuZTFhRmufSeB+F1YQ2IFKKsoY8TAvaClgopNtfLeRI2fcnak2ApJCqthJ9laxKn4bzIB9N7jh+4DXNFqXxhYnoQnQ7tbVD0siRjYRfw6qCyICogWUaam+y69xbsBbek6MfEHdot9Oar2WX2AhmOBl9plRjxSY0yFYq6wt86TRGaZZWm7qG6Q2Z37hNv8Zga7Xbnm6x3xzBVv5Z68ecDxPdGdX6TY72l73N1Aa18XVdgIGVZ25DD6X4eOQW1VVrhg1L/8wvAucpL89Rp3Dvqb+23StN+1+81/G7XErOo4a+cY2UQMswfJ/ekKgWjGL5fRjQ/rCfbXawm0EWQVHhXWlxOAHxfFMRuM2V4Yu/YQvtRlIXUGrud+DlDIS4JvyTslZ2A==; _MSC=fKohZCHWCjW7GvJFL5vsIhzSaTZrayBmpciuu3GQ1Cqo3jOP26JvoKaO0sScsnRmCull4/wUVSw=";
//			mall.setCookie(ss);
//			mall.setCookie(SysPar.getSessionCookie());
//			orderId=8617032721687606&orderType=1
			LOG.debug("===============================  order id " + orderid);
			mall.setPar("orderId", orderid);
			MallHttpResponse mhrs = mall.response();
			return mhrs;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		JSONObject json_main = new JSONObject();
//		System.out.println(page.getHtml());
//		System.out.println(page.select("div.infoBoxTop tr th").text());
//		System.out.println(page.select("").text());
		Elements els = page.select("div.infoBoxTop tr th");

		//得到订单详情 中的基本信息   （订单编号,	订单日期,订单归属）
		JSONObject json_info = new JSONObject();
		for(int i=0; i<els.size();i++){
			String key = els.get(i).text().trim();
			String val = page.select("div.infoBoxTop tr td").get(i).text().trim();
			key = SysUtil.trim(key);
			val = SysUtil.trim(val);
			json_info.put(key, val);
		}
		json_main.put("info", json_info);
		json_info.clear();
//		System.out.println(json_main);
		els = page.select("div.infoBox");
		//记录循环次数
		int ii = 0;
		String jsname = null;
		for(Element el : els){
			//得到名字 （入网信息，商品信息，费用信息，支付信息，物流信息，受理信息，其它信息）
			jsname = SysUtil.getStr(el.select("h2.fontStyle").text().trim());
			Elements tmpels = el.select("th");
			for(int i =0;i<tmpels.size();i++){
				String key = tmpels.get(i).text();
				String val = el.select("td").get(i).text();
				if(key == null || key.isEmpty())
					continue;
				key = SysUtil.trim(key);
				val = SysUtil.trim(val);
				json_info.put(key, val);
			}
			json_main.put(jsname, json_info);
			json_info.clear();
			ii++;
		}
	}

	
//	public static void main(String[] args) {
//		OrderDetails or = new OrderDetails();
//		or.orderid = "9317032721833932";
//		try {
//			or.execute();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
