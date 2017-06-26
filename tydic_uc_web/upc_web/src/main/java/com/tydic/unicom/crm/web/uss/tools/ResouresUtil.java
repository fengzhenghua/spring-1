package com.tydic.unicom.crm.web.uss.tools;

import java.util.HashMap;
import java.util.Map;

public class ResouresUtil {

	private static Map<String, String> _paykeyMap = new HashMap<String, String>();
	
	public static void putPayKey(String pay_order_key, String order_id){
		_paykeyMap.put(pay_order_key, order_id);
	}
	
	public static String getPutKey(String pay_order_key){
		return _paykeyMap.get(pay_order_key);
	}
}
