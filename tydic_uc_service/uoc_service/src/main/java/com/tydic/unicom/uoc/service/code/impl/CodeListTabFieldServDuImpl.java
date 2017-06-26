package com.tydic.unicom.uoc.service.code.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uoccode.po.CodeListTabFieldPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.CodeListTabFieldServ;
import com.tydic.unicom.uoc.service.code.interfaces.CodeListTabFieldServDu;
import com.tydic.unicom.uoc.service.code.vo.CodeListTabFieldVo;

@Service("CodeListTabFieldServDu")
public class CodeListTabFieldServDuImpl implements CodeListTabFieldServDu{

	@Autowired
	private CodeListTabFieldServ codeListTabFieldServ;
	
	@Override
	public List<CodeListTabFieldVo> queryCodeListTabFieldAll() throws Exception {
		List<CodeListTabFieldPo> list = codeListTabFieldServ.queryCodeListTabFieldAll();
		if(list != null && list.size()>0){
			List<CodeListTabFieldVo> listOut = new ArrayList<CodeListTabFieldVo>();
			for(int i=0;i<list.size();i++){
				CodeListTabFieldVo codeListTabFieldVo = new CodeListTabFieldVo();
				BeanUtils.copyProperties(list.get(i), codeListTabFieldVo);
				listOut.add(codeListTabFieldVo);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

}
