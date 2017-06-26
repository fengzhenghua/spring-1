package com.tydic.unicom.apc.base.pub.impl;
import java.util.List;

import org.springframework.stereotype.Service;
import com.tydic.uda.core.Condition;
import com.tydic.unicom.apc.base.pub.interfaces.CodeAreaServ;
import com.tydic.unicom.apc.base.pub.po.CodeAreaPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("CodeAreaServ")
public class CodeAreaServImpl extends BaseServImpl<CodeAreaPo> implements CodeAreaServ {
	@Override
	public List<CodeAreaPo> queryCodeAreaByParentAreaCode(CodeAreaPo codeArea) throws Exception{
		Condition con = Condition.build("queryCodeAreaByParentAreaCode").filter(codeArea.convertThis2Map());
		List<CodeAreaPo> list = query(CodeAreaPo.class, con);
		if(list != null && list.size()>0){
			ListSortUtil<CodeAreaPo> sortList = new ListSortUtil<CodeAreaPo>();
			sortList.sort(list, "area_code", "asc");
			return list;
		}
		else{
			return null;
		}
	}

	@Override
	public List<CodeAreaPo> queryCodeAreaAllProvinceInfo(CodeAreaPo codeArea) throws Exception {
		Condition con = Condition.build("queryCodeAreaAllProvinceInfo").filter(codeArea.convertThis2Map());
		List<CodeAreaPo> list = query(CodeAreaPo.class, con);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}
