package upcweb.test;

import java.util.HashMap;
import java.util.Map;

import com.tydic.unicom.util.RsaEncodeUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class RequestContentTest {
	

	public static void main(String[] args) {
		
		
//		String publicKey = "支付中心提供的RSA密钥";
//		String signKey = "支付中心提供的签名密钥";
//		
//		String busi_id = "10000000";
//		Map<String, Object> contentMap = new HashMap<>();
//		//支付请求固定是01
//		contentMap.put("req_type", "01");
//		//流水号，根据业务系统自身的定义规则生成
//		contentMap.put("out_order_id", System.currentTimeMillis());
//		//订单支付金额，单位是元
//		contentMap.put("real_fee", 0.01);
//		//订单描述
//		contentMap.put("detail_name", "这是一个测试的订单");	
//		//支付后跳转的地址
//		contentMap.put("redirect_url", "");
//		//备注，原值返回
//		contentMap.put("remark", "备注，原值返回");
//		
//		
//		
//		String content = RsaEncodeUtil.mapToUrlParams(contentMap);
//		
//
//		String xmldata = "<xml><busi_id>BUSI_ID</busi_id><content><![CDATA[CONTENT]]></content></xml>";
//		
//		xmldata = xmldata.replace("BUSI_ID", busi_id).replace("CONTENT",content);
//		
//		//--------------加密及签名-------------//
//		String encodeResult = testPublicEncode(xmldata, publicKey, signKey);
		
		
		
		JSONArray jsonArray = new JSONArray();
		JSONObject goodsJson1 = new JSONObject();
		goodsJson1.put("goods_id", "11111111");
		goodsJson1.put("goods_name", "测试商品1");
		goodsJson1.put("goods_num", 1);
		goodsJson1.put("goods_price", 0.01);
		
		jsonArray.add(goodsJson1);
		
		JSONObject goodsJson2 = new JSONObject();
		goodsJson2.put("goods_id", "22222222");
		goodsJson2.put("goods_name", "测试商品2");
		goodsJson2.put("goods_num", 1);
		goodsJson2.put("goods_price", 0.01);
		
		jsonArray.add(goodsJson2);
		
		System.out.println(jsonArray.toString());
	}

}
