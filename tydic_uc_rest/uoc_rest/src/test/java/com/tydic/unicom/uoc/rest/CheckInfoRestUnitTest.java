package com.tydic.unicom.uoc.rest;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.test.spring.SpringContextUnitTestCase;
import com.tydic.unicom.uoc.business.common.interfaces.CheckInfoServDu;
import com.tydic.unicom.uoc.business.common.vo.InfoIDCardVo;
import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.webUtil.UocMessage;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年3月21日 下午4:28:46
 * @ClassName CheckInfoRestUnitTest
 * @Description 检查信息服务单元测试
 * @version V1.0
 */
public class CheckInfoRestUnitTest extends SpringContextUnitTestCase{
	
    private final String BASE_PATH = "/"+ControllerMappings.CHECK_INFO_REST+"/";
    
    @InjectMocks  
    private CheckInfoRest checkInfoRest ;  
  
	@Mock
	private CheckInfoServDu checkInfoServDu;
	
	@Mock
	private MockHttpSession httpSession;
	
    private MockMvc mockMvc;  
	

	@Before
	public void setUp() throws Exception {
		// 初始化mock
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(checkInfoRest).build(); 
	}
	
	@After
	public void tearDown() throws Exception {
		reset(checkInfoServDu);
	}
	
	@Test
	public void testGztValid(){
		UocMessage message = new UocMessage();
		message.setContent("testGztValid");
		message.setRespCode("1000");
		try {
			when(checkInfoServDu.createGztValid(any(InfoIDCardVo.class), anyString())).thenReturn(message);
			ResultActions result =  mockMvc.perform((post(BASE_PATH+UrlsMappings.GZT_VALID ))
					  .param("jsession_id", "111")
					)
					  .andExpect(status().isOk());  
					String returns = result.andReturn().getResponse().getContentAsString();
					assertNotNull(returns);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testGetCertInfo() {
		UocMessage message = new UocMessage();
		message.setContent("testGetCertInfo");
		message.setRespCode("1000");
		try {
			when(checkInfoServDu.getCertInfo(any(ParaVo.class))).thenReturn(message);
			ResultActions result =  mockMvc.perform((post(BASE_PATH+UrlsMappings.GET_CERT_INFO))
			  .param("jsession_id", "111"))
			  .andExpect(status().isOk());  
			String returns = result.andReturn().getResponse().getContentAsString();
			assertNotNull(returns);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
