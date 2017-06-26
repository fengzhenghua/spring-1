package com.tydic.unicom.service.cache.service.interfaces;

import com.tydic.unicom.service.cache.po.*;

public interface MemQueryServ {
		public CodeTypeMem queryCodeType(String keyId);
		public CodeListMem queryCodeList(String keyId);
		public CodeListMapMem queryCodeListMap(String type_code,String code_id,String system);
		public OperMem queryOperMem(String keyId);
}
