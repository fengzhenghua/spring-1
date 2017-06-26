package com.tydic.unicom.crawler.business.task.order5250;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
 * 订单管理>手动开户 - 详细信息
 * toDetail
 * @author xx
 *
 */
@Service()
public class ManualAccount_ToDetail extends BaseTask {
	public static final Logger LOG = LoggerFactory.getLogger(ManualAccount_ToDetail.class);
	CrawlDatum maincrawldatum = new CrawlDatum();
	public String orderid = "";
	
	public ManualAccount_ToDetail() {
		super();
	}
	@Override
	public void init(CrawlDatum datum) throws Exception {
		super.init(datum);
		url = SysPar.ppvo.getBaselink()+"/Odm/ManualAccount/toDetail";
	}
	@Override
	public MallHttpResponse getExeResponse(CrawlDatum crawlDatum) throws Exception {
		try {
			MallHttpRequest mall = new MallHttpRequest(url);
			mall.setMethod("POST");
			mall.setCookie(crawlDatum.meta("sessioncookie"));
			LOG.debug("===============================  order id " + orderid);
			mall.setPar("orderId", orderid);
			mall.setPar("orderType", "1");
			MallHttpResponse mhrs = mall.response();
			return mhrs;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	JSONObject json_main;
	@Override
	public void visit(Page page, CrawlDatums next) {
		json_main = new JSONObject();
		JSONObject json_obj = new JSONObject();
		String ordernum = page.select("div.orderStatus.width980").text();
		ordernum = ordernum.substring(ordernum.indexOf("[")+1,ordernum.indexOf("]"));
		LOG.debug("ordernum = "+ordernum);
		json_main.put("ordernum", ordernum);
		json_main.put("orderid", orderid);
		
		/** 买家信息  渠道信息  **/
		Elements els = page.select("p.inforPosition");
		for(Element el : els){
			String f = SysUtil.getStrandtrim(el.text());
			json_main.putAll(SysUtil.getmapfromstr(f));
		}
		LOG.debug("json_main = "+json_main.toString());
		/**
		 * 商品清单
		 */
		els = page.select("[class=listInfor]>li");
		int coni=0;
		for(Element el : els){
			JSONObject json_tmp = new JSONObject();
			Elements els_item = el.select("dl");
			for(Element el_item : els_item){
				String key = SysUtil.trimandcolon(el_item.select("dt").text());
				String val = SysUtil.trimandcolon(el_item.select("dd").text());
				
				if(key.equals("地址")){
					val = SysUtil.trimandcolon(el_item.select("dd span.addressName").html());
				}
				json_tmp.put(key, val);
			}
			LOG.debug("================="+json_tmp.toString());
			json_obj.put(""+coni,json_tmp);
			coni++;
		}
		json_main.put("context", json_obj);
		json_obj.clear();
		LOG.debug("json_main = "+json_main.toString());
		/** 商品清单   **/
		els = page.select("[class=listInfor feeInfor] li.totalAmount div");
		for(int i = 0 ; i < els.size() ; i++){
			String f = SysUtil.getStrandtrim(els.get(i).text());
			f = SysUtil.repaly(f, "[￥+-]", "");
			json_obj.putAll(SysUtil.getmapfromstr(f));
		}
		json_main.put("费用", json_obj);
		json_obj.clear();
		LOG.debug("json_main = "+json_main.toString());
		
		/** 支付及配送信息 发票信息**/
		els = page.select("div.inforPosition");
		for(int i = 0 ; i < els.size() ; i++){
			String f = SysUtil.getStrandtrim(els.get(i).text());
			//f = SysUtil.repaly(f, "[￥+-]", "");
			json_obj.putAll(SysUtil.getmapfromstr(f));
		}
		json_main.put("other", json_obj);
		json_obj.clear();
		LOG.debug("json_main = "+json_main.toString());
		/**  支付及配送信息   **/
		
		
		String orderId = page.select("input#orderId").attr("value");
		json_obj.put("orderId", orderId);
		String hiddenTmplID = page.select("input#hiddenTmplID").attr("value");
		json_obj.put("tmplId", hiddenTmplID);
		String goodInstId = page.select("input[name=goodInstId]").attr("value");
		json_obj.put("goodInstId", goodInstId);
		
		json_obj.put("certType", "");
		String certNum = page.select("span#certNum").text();
		json_obj.put("certNum", certNum);
		
		String manualNetCardNum = page.select("input#manualNetCardNum").attr("value");
		json_obj.put("manualNetCardNum", manualNetCardNum);
		json_main.put("cbinfo", json_obj);
	}

	public String getResult(){
		return json_main.toString();
	}
}
