package com.tydic.unicom.webUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月25日 下午6:17:43
 * @ClassName FastJsonUtils
 * @Description json相关工具
 * @version V1.0
 */
public class FastJsonUtils {
	
	/**
	 * 过滤器
	 */
	private static ValueFilter filter = new ValueFilter() {
		
		@Override
		public Object process(Object obj, String s, Object v) {
			if (v == null)
				return "";
			return v;
		}
	};
	
	/**
	 * 
	 * @Method: parserNullStringAsEmpty
	 * @Description: 转换null为""
	 */
	public static String parserNullStringAsEmpty(String inJson) {
		JSONObject jsonObject = JSONObject.parseObject(inJson);
		return JSON.toJSONString(jsonObject, filter);
	}
	
	/**
	 * 
	 * @Method: parserNullStringAsEmpty
	 * @Description: 转换null为""
	 */
	public static String parserNullStringAsEmpty(JSONObject jsonObject) {
		
		return JSON.toJSONString(jsonObject, filter);
	}
}
