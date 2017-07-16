package com.hera.rest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hera.message.Message;

@Controller
@RequestMapping(value="TestUrl")
public class TestRest {
	
	private Logger logger = Logger.getLogger(TestRest.class);
	
	@RequestMapping(value="getData",method=RequestMethod.POST)
	@ResponseBody
	public Message getData(String a,String b){
		
		Message message = new Message();
		String c = a + b;
		message.setContent("测试");
		message.setRespCode("测试成功");
		message.addArgs("c", c);
		return message;
	}
}
