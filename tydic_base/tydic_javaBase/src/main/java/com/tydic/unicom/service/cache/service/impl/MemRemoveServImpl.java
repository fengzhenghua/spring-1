package com.tydic.unicom.service.cache.service.impl;

import com.tydic.uda.service.S;
import com.tydic.unicom.service.cache.po.*;
import com.tydic.unicom.service.cache.service.interfaces.MemRemoveServ;

public class MemRemoveServImpl implements MemRemoveServ {
		public String removeCodeType(String keyId){
			
				int flag = S.get(CodeTypeMem.class).remove(keyId);
				
				if(flag == 0){
					return "OK";
				}else{
					return "FAIL";
				}
			
		}
		public String removeCodeList(String keyId){
			
			int flag = S.get(CodeListMem.class).remove(keyId);
			
			if(flag == 0){
				return "OK";
			}else{
				return "FAIL";
			}
		
	}
}
