package com.tydic.unicom.crm.Test;

import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.upc.base.database.inst.interfaces.TransAliPayServ;
import org.junit.Test;

public class TestPay extends SpringJunitSupport {

	@Autowired
	private TransAliPayServ transAliPayServ;
	
	
	@Test
    public void testGetCount(){
		String billDate = "20170223";
		String appid = "2015111700819307";
		int cout = transAliPayServ.getCountTransByBillDate(billDate, appid);
		
		assertEquals(cout, 0);
    }
}
