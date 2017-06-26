package com.tydic.unicom.service.cache.service.impl;


import com.tydic.uda.service.S;
import com.tydic.unicom.service.cache.po.*;
import com.tydic.unicom.service.cache.service.interfaces.MemQueryServ;

public class MemQueryServImpl implements MemQueryServ {
	
		public CodeTypeMem queryCodeType(String keyId){
			
				CodeTypeMem	codeType = S.get(CodeTypeMem.class).get(keyId);
				
				return codeType;
		}
	
		public CodeListMem queryCodeList(String keyId){
		
				CodeListMem	codeList = S.get(CodeListMem.class).get(keyId);
			
				return codeList;
		}
		
		public CodeListMapMem queryCodeListMap(String type_code,String code_id,String system){
			
				String keyId = "code_list_map_"+type_code+"_"+code_id+"_"+system;
				CodeListMapMem colmm = S.get(CodeListMapMem.class).get(keyId);
				
				return colmm;
		}
		public OperMem queryOperMem(String keyId){
			OperMem	operMem = S.get(OperMem.class).get(keyId);
			
			return operMem;
		}
}
