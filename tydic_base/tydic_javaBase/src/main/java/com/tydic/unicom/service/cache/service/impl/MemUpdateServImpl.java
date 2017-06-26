package com.tydic.unicom.service.cache.service.impl;

import com.tydic.uda.service.S;
import com.tydic.unicom.service.cache.po.*;
import com.tydic.unicom.service.cache.service.interfaces.MemUpdateServ;

public class MemUpdateServImpl implements MemUpdateServ {
		public String updateCodeType(CodeTypeMem	codeTypeMem){
			
				int flag = S.get(CodeTypeMem.class).update(codeTypeMem);
				
				if(flag == 0){
					return "OK";
				}else{
					return "FAIL";
				}
			
		}
		public String updateCodeList(CodeListMem	codeListMem){
			
			int flag = S.get(CodeListMem.class).update(codeListMem);
			
			if(flag == 0){
				return "OK";
			}else{
				return "FAIL";
			}
		
	}
}
