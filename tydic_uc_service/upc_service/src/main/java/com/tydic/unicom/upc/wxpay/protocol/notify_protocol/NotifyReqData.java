package com.tydic.unicom.upc.wxpay.protocol.notify_protocol;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotifyReqData {

	//协议层
    private String return_code = "";
    private String return_msg = "";

    //协议返回的具体数据（以下字段在return_code 为SUCCESS 的时候有返回）
	private String appid;		//微信分配的公众账号ID
	private String mch_id;		//微信支付分配的商户号
	private String sub_appid;	//微信分配的子商户公众账号ID
	private String sub_mch_id;	//微信支付分配的子商户号
	private String device_info;	//微信支付分配的终端设备号
	private String nonce_str;	//随机字符串，不长于32位
	private String sign;		//签名
	private String result_code;	//SUCCESS/FAIL
	private String err_code;	//详细参见第6节错误列表
	private String err_code_des;	//错误返回的信息描述
	private String openid;		//用户在商户appid下的唯一标识
	private String is_subscribe;	//用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
	private String sub_openid;		//用户在子商户appid下的唯一标识
	private String sub_is_subscribe;	//用户是否关注子公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
	private String trade_type;		//JSAPI、NATIVE、APP
	private String bank_type;		//银行类型，采用字符串类型的银行标识，银行类型见附表
	private int total_fee;			//订单总金额，单位为分
	private String fee_type;		//货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY
	private int cash_fee;			//现金支付金额订单现金支付金额
	private String cash_fee_type;	//货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY
	private int coupon_fee;			//代金券或立减优惠金额<=订单总金额，订单总金额-代金券或立减优惠金额=现金支付金额
	private int coupon_count;		//代金券或立减优惠使用数量

	private List<String> coupon_idList = new ArrayList<>();		//代金券或立减优惠ID, $n为下标，从1开始编号
	private List<Integer> coupon_feeList = new ArrayList<>();
	
	private String transaction_id;	//微信支付订单号
	private String out_trade_no;	//商户系统的订单号，与请求一致
	private String attach;			//商家数据包，原样返回
	private String time_end;		//支付完成时间，格式为yyyyMMddHHmmss
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
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
	public String getSub_appid() {
		return sub_appid;
	}
	public void setSub_appid(String sub_appid) {
		this.sub_appid = sub_appid;
	}
	public String getSub_mch_id() {
		return sub_mch_id;
	}
	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
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
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErr_code_des() {
		return err_code_des;
	}
	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getIs_subscribe() {
		return is_subscribe;
	}
	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}
	public String getSub_openid() {
		return sub_openid;
	}
	public void setSub_openid(String sub_openid) {
		this.sub_openid = sub_openid;
	}
	public String getSub_is_subscribe() {
		return sub_is_subscribe;
	}
	public void setSub_is_subscribe(String sub_is_subscribe) {
		this.sub_is_subscribe = sub_is_subscribe;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getBank_type() {
		return bank_type;
	}
	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}
	public int getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	public int getCash_fee() {
		return cash_fee;
	}
	public void setCash_fee(int cash_fee) {
		this.cash_fee = cash_fee;
	}
	public String getCash_fee_type() {
		return cash_fee_type;
	}
	public void setCash_fee_type(String cash_fee_type) {
		this.cash_fee_type = cash_fee_type;
	}
	public int getCoupon_fee() {
		return coupon_fee;
	}
	public void setCoupon_fee(int coupon_fee) {
		this.coupon_fee = coupon_fee;
	}
	public int getCoupon_count() {
		return coupon_count;
	}
	public void setCoupon_count(int coupon_count) {
		this.coupon_count = coupon_count;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getTime_end() {
		return time_end;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	public List<String> getCoupon_idList() {
		return coupon_idList;
	}
	public void setCoupon_idList(List<String> coupon_idList) {
		this.coupon_idList = coupon_idList;
	}
	public List<Integer> getCoupon_feeList() {
		return coupon_feeList;
	}
	public void setCoupon_feeList(List<Integer> coupon_feeList) {
		this.coupon_feeList = coupon_feeList;
	}
	
	public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            field.setAccessible(true);
            try {
                obj = field.get(this);
                if(obj!=null){
                    map.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
            Object obj;
            field.setAccessible(true);
            try {
                obj = field.get(this);
                if(obj!=null){
                	sb.append("["+field.getName()+":"+obj+"] ");
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
		
		return sb.toString();
	}
}
