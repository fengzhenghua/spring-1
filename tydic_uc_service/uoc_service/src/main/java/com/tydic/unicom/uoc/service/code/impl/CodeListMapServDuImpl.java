package com.tydic.unicom.uoc.service.code.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uoccode.po.CodeListMapPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.CodeListMapServ;
import com.tydic.unicom.uoc.service.code.interfaces.CodeListMapServDu;
import com.tydic.unicom.uoc.service.code.vo.CodeListMapVo;

@Service("CodeListMapServDu")
public class CodeListMapServDuImpl implements CodeListMapServDu{

	@Autowired
	private CodeListMapServ codeListMapServ;

	@Override
	public List<CodeListMapVo> queryCodeListMapAll() throws Exception {
		List<CodeListMapPo> list = codeListMapServ.queryCodeListMapAll();
		if(list != null && list.size()>0){
			List<CodeListMapVo> listOut = new ArrayList<CodeListMapVo>();
			for(int i=0;i<list.size();i++){
				CodeListMapVo codeListMapVo = new CodeListMapVo();
				BeanUtils.copyProperties(list.get(i), codeListMapVo);
				listOut.add(codeListMapVo);
			}
			return listOut;
		}
		else{
			return null;
		}
	}
}
