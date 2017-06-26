package com.tydic.unicom.apc.common.wxpay;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tydic.unicom.apc.service.common.vo.PropertiesParamVo;


/**
 * 统计订单请求对象
 * NATIVE
 * @author Pray
 *
 */
public class UnifiedOrderNativeReqData extends BaseProtocolData {

//	@Autowired
//	@Qualifier("propertiesParamVo")
//	private PropertiesParamVo propertiesParamVo;
	
	//每个字段具体的意思请查看API文档
    private String appid = "";
    private String mch_id = "";
    private String device_info = "";
    private String nonce_str = "";
    private String sign = "";
    private String body = "";
    private String out_trade_no = "";
    private int total_fee = 0;
    private String spbill_create_ip = "";
    private String product_id = "";
    private String notify_url = "";
    private String trade_type = "NATIVE";
    private String time_expire;
    private String openid = "";
    
	
	public UnifiedOrderNativeReqData(String appid, String mchid, String deviceInfo, String body,
			String outTradeNo, int totalFee,String spbillCreateIP, String productId, int expireTime, String key,String trade_type, String openid,String notifyUrl) throws Exception{
		
//		String notifyUrl = "";
		
//		if("1".equals(propertiesParamVo.getPayExpire())){
//			notifyUrl = propertiesParamVo.getNotifyUrlPc();//WXPropertiesUtils.getProperties("notify_url_pc");
//		} else{
//			notifyUrl = propertiesParamVo.getNotifyUrlMobile();//WXPropertiesUtils.getProperties("notify_url_mobile");
//		}
		
		setAppid(appid);
		setMch_id(mchid);
		setDevice_info(deviceInfo);
		setTrade_type(trade_type);
		//必须要转成UTF-8，不然会提示签名错误
		//setBody(new String(body.getBytes(),"UTF-8"));
		setBody(body);
		setOut_trade_no(outTradeNo);
		setTotal_fee(totalFee);
		setProduct_id(productId);
		
		setSpbill_create_ip(spbillCreateIP);
		
		setNotify_url(notifyUrl);
		
		setOpenid(openid);
		
		//订单失效时间
		String time_expire= new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()+expireTime*60*1000);
		setTime_expire(time_expire);
		
		
		//随机字符串，不长于32 位
        setNonce_str(RandomStringGenerator.getRandomStringByLength(32));

        //根据API给的签名规则进行签名
        String sign = Signature.getSign(toMap(), key);
        setSign(sign);//把签名数据设置到Sign这个属性中
	}
	
	

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public int getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}



	public String getTime_expire() {
		return time_expire;
	}



	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}



	public String getOpenid() {
		return openid;
	}



	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	
}
