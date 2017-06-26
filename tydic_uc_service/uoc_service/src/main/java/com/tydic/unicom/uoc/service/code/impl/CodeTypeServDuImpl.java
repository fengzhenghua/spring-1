package com.tydic.unicom.uoc.service.code.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uoccode.po.CodeTypePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.CodeTypeServ;
import com.tydic.unicom.uoc.service.code.interfaces.CodeTypeServDu;
import com.tydic.unicom.uoc.service.code.vo.CodeTypeVo;

@Service("CodeTypeServDu")
public class CodeTypeServDuImpl implements CodeTypeServDu{

	@Autowired
	private CodeTypeServ codeTypeServ;
	
	@Override
	public List<CodeTypeVo> queryCodeTypeAll() throws Exception {
		List<CodeTypePo> list = codeTypeServ.queryCodeTypeAll();
		if(list != null && list.size()>0){
			List<CodeTypeVo> listOut = new ArrayList<CodeTypeVo>();
			for(int i=0;i<list.size();i++){
				CodeTypeVo codeTypeVo = new CodeTypeVo();
				BeanUtils.copyProperties(list.get(i), codeTypeVo);
				listOut.add(codeTypeVo);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

}
