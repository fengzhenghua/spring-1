package com.tydic.unicom.upc.service.task.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tydic.unicom.upc.base.database.inst.interfaces.TransAliPayServ;
import com.tydic.unicom.upc.base.database.inst.interfaces.TransWXPayServ;
import com.tydic.unicom.upc.service.code.interfaces.InfoBusiServDu;
import com.tydic.unicom.upc.service.code.interfaces.InfoPayParaAttrServDu;
import com.tydic.unicom.upc.service.pay.interfaces.AliPayServDu;
import com.tydic.unicom.upc.service.pay.interfaces.WXPayServDu;
import com.tydic.unicom.upc.service.task.interfaces.DownloadPayTransServDu;
import com.tydic.unicom.upc.vo.code.InfoBusiVo;
import com.tydic.unicom.upc.vo.code.InfoPayParaAttrValueVo;

@Component
public class DownloadPayTransServDuImpl implements DownloadPayTransServDu {

	private static final Logger logger = Logger.getLogger(DownloadPayTransServDuImpl.class);
	
	@Autowired
	private InfoBusiServDu infoBusiServDu;
	
	@Autowired
	private InfoPayParaAttrServDu infoPayParaAttrServDu;
	
	@Autowired
	private WXPayServDu wxPayServDu;
	
	@Autowired
	private AliPayServDu aliPayServDu;
	
	@Autowired
	private TransWXPayServ transWXPayServ;
	
	@Autowired
	private TransAliPayServ transAliPayServ;
	
	@Override
	public void commitWxTransDownload(String date) throws Exception {
		
		if(date == null || date.equals("")){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); //设置时间格式
			
			//上一天日期
			date = sdf.format(calendar.getTime());
		}
		
		List<InfoBusiVo> infoBusiList = infoBusiServDu.queryAllInfoBusi();
		
		//因为有一些微信的配置是重复的， 所以要过滤掉
		List<String> wxAppList = new ArrayList<>();
		
    	
    	String deviceInfo = "";
    	String billType = "ALL";
		
		for(InfoBusiVo infoBusi : infoBusiList){
			
			try{
				//微信支付有两个， 10和11
				String pay_type = "10";	
				String busi_id = infoBusi.getBusi_id();
				InfoPayParaAttrValueVo attrValueVo = infoPayParaAttrServDu.getPayParaValueByPayType(busi_id, pay_type);
				if(attrValueVo != null){
					
					String appid=attrValueVo.getAppid();
			    	String mchid = attrValueVo.getMchid();
			    	String key = attrValueVo.getSignkey();
			    	
			    	
			    	
			    	if(!wxAppList.contains(appid)){
			    		
			    		int count = transWXPayServ.getCountTransByBillDate(date, appid, mchid);
			    		if(count > 0){
			    			logger.warn(date + "的" + appid + " 对帐单已经下载过了");
			    			wxAppList.add(appid);
			    		}
			    		else{
			    			wxPayServDu.genDownloadBill(appid, mchid, key, deviceInfo, date, billType, busi_id, pay_type);
			    			wxAppList.add(appid);
			    		}
			    	}
				}
				else{
					pay_type = "11";
					
					attrValueVo = infoPayParaAttrServDu.getPayParaValueByPayType(busi_id, pay_type);
					if(attrValueVo != null){
						
						String appid=attrValueVo.getAppid();
				    	String mchid = attrValueVo.getMchid();
				    	String key = attrValueVo.getSignkey();
				    	
				    	if(!wxAppList.contains(appid)){
				    		
				    		int count = transWXPayServ.getCountTransByBillDate(date, appid, mchid);
				    		if(count > 0){
				    			logger.warn(date + "的" + appid + " 对帐单已经下载过了");
				    			wxAppList.add(appid);
				    		}
				    		else{
				    			wxPayServDu.genDownloadBill(appid, mchid, key, deviceInfo, date, billType, busi_id, pay_type);
				    			wxAppList.add(appid);
				    		}
				    	}
					}
				}
				
			}catch(Exception e){
				logger.error(infoBusi.getBusi_id()+"下载微信"+date+"对帐单失败", e);
			}

		}

	}
	
	
	
	@Override
	public void commitAlipayTransDownload(String date) throws Exception {
		
		if(date == null || date.equals("")){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
			
			//上一天日期
			date = sdf.format(calendar.getTime());
		}
		else if(date.length() == 8){
			date = date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6,8);
		}
		
		List<InfoBusiVo> infoBusiList = infoBusiServDu.queryAllInfoBusi();
		
		//因为有一些配置是重复的， 所以要过滤掉
		List<String> appList = new ArrayList<>();
		
    	
    	String billType = "trade";
		
		for(InfoBusiVo infoBusi : infoBusiList){
			
			try{
				//支付宝支付有两个， 20和21
				String pay_type = "20";	
				String busi_id = infoBusi.getBusi_id();
				InfoPayParaAttrValueVo attrValueVo = infoPayParaAttrServDu.getPayParaValueByPayType(busi_id, pay_type);
				if(attrValueVo != null){
					
					String appid=attrValueVo.getAppid();
			    	String privateKey = attrValueVo.getPrivateKey();
			    	String publicKey = attrValueVo.getPublicKey();
			    	
			    	if(!appList.contains(appid)){
			    		
			    		int count = transAliPayServ.getCountTransByBillDate(date.replace("-", ""), appid);
			    		if(count > 0){
			    			logger.warn(date + "的" + appid + " 对帐单已经下载过了");
			    			appList.add(appid);
			    		}
			    		else{
			    			aliPayServDu.downloadBill(appid, privateKey, publicKey, billType, date);
			    			appList.add(appid);
			    		}
			    	}
				}
				else{
					pay_type = "21";
					
					attrValueVo = infoPayParaAttrServDu.getPayParaValueByPayType(busi_id, pay_type);
					if(attrValueVo != null){
						
						String appid=attrValueVo.getAppid();
				    	String privateKey = attrValueVo.getPrivateKey();
				    	String publicKey = attrValueVo.getPublicKey();
				    	
				    	int count = transAliPayServ.getCountTransByBillDate(date.replace("-", ""), appid);
			    		if(count > 0){
			    			logger.warn(date + "的" + appid + " 对帐单已经下载过了");
			    			appList.add(appid);
			    		}
			    		else{
			    			aliPayServDu.downloadBill(appid, privateKey, publicKey, billType, date);
			    			appList.add(appid);
			    		}
					}
				}
				
			}catch(Exception e){
				logger.error(infoBusi.getBusi_id()+"下载支付宝"+date+"对帐单失败", e);
			}

		}

	}



	@Override
	public void commitWxTransDownload() throws Exception {
		
		commitWxTransDownload(null);
	}



	@Override
	public void commitAlipayTransDownload() throws Exception {
		commitAlipayTransDownload(null);
		
	}

}
