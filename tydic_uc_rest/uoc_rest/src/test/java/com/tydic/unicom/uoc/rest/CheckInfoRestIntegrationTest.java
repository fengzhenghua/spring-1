package com.tydic.unicom.uoc.rest;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.test.spring.SpringContextTestCase;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年3月21日 下午4:28:46
 * @ClassName CheckInfoRestUnitTest
 * @Description 检查信息服务集成测试
 * @version V1.0
 */
public class CheckInfoRestIntegrationTest extends SpringContextTestCase{
	
	private final String BASE_PATH = "/" + ControllerMappings.CHECK_INFO_REST + "/";
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		// 初始化mock
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void testGztValid() throws Exception {
		ResultActions result = mockMvc.perform((post(BASE_PATH + UrlsMappings.GZT_VALID)).param("jsession_id", "111"))
		        .andExpect(status().isOk());
		String returns = result.andReturn().getResponse().getContentAsString();
		assertNotNull(returns);
	}
}
