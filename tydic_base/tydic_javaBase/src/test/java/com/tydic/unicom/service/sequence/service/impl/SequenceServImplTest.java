package com.tydic.unicom.service.sequence.service.impl;

//import org.junit.Before;
//import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tydic.unicom.crm.Test.Spring;
import com.tydic.unicom.service.sequence.service.interfaces.SequenceServ;


public class SequenceServImplTest {
	private ClassPathXmlApplicationContext context;
	
//	@Before
	public void setUp() throws Exception {
		this.context = Spring.init();
	}
	
//	@Test
	public void testGetSequence() {
		SequenceServImpl sequenceServ = (SequenceServImpl)context.getBean("sequenceServ");
		System.out.println(sequenceServ.genSequence("test_seq.test"));
		System.out.println(sequenceServ.genSequence("test_seq.test"));
		System.out.println(sequenceServ.genSequence("test_seq.test"));
		System.out.println(sequenceServ.genSequence("test_seq.test"));		
	}
}
