package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.uoccode.po.CodeTaskPkgDesignPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.CodeTaskPkgDesignServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("CodeTaskPkgDesignServ")
public class CodeTaskPkgDesignServImpl extends BaseServImpl<CodeTaskPkgDesignPo> implements CodeTaskPkgDesignServ {

	@Override
	public List<CodeTaskPkgDesignPo> queryCodeTaskPkgDesignByPo(CodeTaskPkgDesignPo po) throws Exception {
		Condition con = Condition.build("queryCodeTaskPkgDesignByPo").filter(po.convertThis2Map());
		List<CodeTaskPkgDesignPo> list = query(CodeTaskPkgDesignPo.class, con);
		return (list != null && list.size() > 0) ? list : null;
	}

	@Override
	public boolean create(CodeTaskPkgDesignPo po) throws Exception {
		create(CodeTaskPkgDesignPo.class,po);
		return true;
	}

	@Override
	public boolean update(CodeTaskPkgDesignPo po) throws Exception {
		update(CodeTaskPkgDesignPo.class,po);
		return true;
	}

	@Override
	public boolean delete(CodeTaskPkgDesignPo po) throws Exception {
		remove(CodeTaskPkgDesignPo.class,po);
		return true;
	}

	@Override
	public List<CodeTaskPkgDesignPo> queryCodeTaskPkgDesign(
			CodeTaskPkgDesignPo po, int pageNo, int pageSize) throws Exception {
		int number = (pageNo-1)* pageSize;
		List<CodeTaskPkgDesignPo> list = S.get(CodeTaskPkgDesignPo.class).page(Condition.build("queryCodeTaskPkgDesign").filter(po.convertThis2Map()), number, pageSize);
		if(list == null || list.size() <= 0){
			return null;
		}
		return list;
	}

	@Override
	public int queryCodeTaskPkgDesignCount(CodeTaskPkgDesignPo po)
			throws Exception {
		Condition con = Condition.build("queryCodeTaskPkgDesignCount").filter(po.convertThis2Map());
		List<CodeTaskPkgDesignPo> list = query(CodeTaskPkgDesignPo.class, con);
		return list.size();
	}

}
