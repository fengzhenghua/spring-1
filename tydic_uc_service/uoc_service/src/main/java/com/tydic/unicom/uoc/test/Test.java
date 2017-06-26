package com.tydic.unicom.uoc.test;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class Test {
	public static void main(String[] args) {
//		JSONObject json = new JSONObject();
//		json.put("abc", "22");
//		json.put("ccc", "333");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("aaa", "333");
		map.put("bbb", "444");
		map.put("ccc", 111);
		map.put("is_ture", false);
		JSONObject jsonObject = new JSONObject(map);
		System.out.println(jsonObject.toJSONString());
		//System.out.println(map.toJSONString());
	}
}
