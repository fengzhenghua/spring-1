package com.tydic.unicom.upc.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.upc.service.task.interfaces.DownloadPayTransServDu;
import com.tydic.unicom.upc.web.constants.ControllerMappings;
import com.tydic.unicom.webUtil.Message;
import com.tydic.unicom.webUtil.Message.Type;

@Controller
@RequestMapping(value = ControllerMappings.PAY_CHECK_CONTROLLER)
public class PayCheckController {

	@Autowired
	private DownloadPayTransServDu downloadPayTransServDu;
	
	/**
	 * 微信对帐单下载
	 * @param bill_date
	 * @return
	 */
	@RequestMapping(value = "wxpayDownload" , method = RequestMethod.POST)
	@ResponseBody
	public Message wxpayDownload(String bill_date){
		Message message = new Message();
		try{
			if(bill_date == null || "".equals(bill_date)){
				message.setType(Type.error);
				message.setContent("必须传bill_date参数");
				return message;
			}
			
			
			downloadPayTransServDu.commitWxTransDownload(bill_date);
			message.setType(Type.success);
			message.setContent("微信支付"+bill_date+"发起对帐单下载成功!");
			return message;
			
		}catch(Exception e){
			message.setType(Type.error);
			message.setContent("微信对帐单下载失败!"+e.getMessage());
			return message;
		}
	}
	
	@RequestMapping(value = "alipayDownload" , method = RequestMethod.POST)
	@ResponseBody
	public Message alipayDownload(String bill_date){
		Message message = new Message();
		try{
			if(bill_date == null || "".equals(bill_date)){
				message.setType(Type.error);
				message.setContent("必须传bill_date参数");
				return message;
			}
			
			if(bill_date.length() == 8){
				bill_date = bill_date.substring(0,4)+"-"+bill_date.substring(4,6)+"-"+bill_date.substring(6,8);
			}
			
			downloadPayTransServDu.commitAlipayTransDownload(bill_date);
			message.setType(Type.success);
			message.setContent("支付宝"+bill_date+"发起对帐单下载成功!");
			return message;
			
		}catch(Exception e){
			message.setType(Type.error);
			message.setContent("支付宝对帐单下载失败!"+e.getMessage());
			return message;
		}
	}
}
