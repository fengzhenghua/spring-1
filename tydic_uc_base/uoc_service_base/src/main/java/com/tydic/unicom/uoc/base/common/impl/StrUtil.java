package com.tydic.unicom.uoc.base.common.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class  StrUtil {

	public static Map<String,String> splitStringFromOrderNo(String order_no) throws Exception{
		int area_code_length =order_no.length() -Constant.SUB_ORDER_AREA_LENGTH;
		int part_month_length = 2;

		String area_code =order_no.substring(1, area_code_length);
		String part_month =order_no.substring(area_code_length+Constant.SUB_ORDER_YEAR_LENGTH, area_code_length+Constant.SUB_ORDER_YEAR_LENGTH+part_month_length);
		part_month = String.valueOf(Integer.parseInt(part_month));
		Map<String,String> map =new HashMap<String,String>();
		map.put("area_code", area_code);
		map.put("part_month", part_month);

		return map;
	}

	public static List<String> strToList(String str){
		String[] list =str.split(",");

		return Arrays.asList(list);
	}
}
