package com.tydic.unicom.apc.base.pub.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.apc.base.pub.interfaces.ApDeveloperServ;
import com.tydic.unicom.apc.base.pub.po.ApDeveloperPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("ApDeveloperServ")
public class ApDeveloperServImpl extends BaseServImpl<ApDeveloperPo> implements ApDeveloperServ {

	@Override
	public ApDeveloperPo getApDeveloperPo(ApDeveloperPo po) throws Exception {

		return get(ApDeveloperPo.class,po);
	}

	@Override
	public boolean addApDeveloperPo(ApDeveloperPo po) throws Exception {
		try{
			create(ApDeveloperPo.class,po);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public boolean deleteApDeveloperPo(ApDeveloperPo po) throws Exception {
		try{
			remove(ApDeveloperPo.class,po);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public boolean updateApDeveloperPo(ApDeveloperPo po) throws Exception {
		try{
			update(ApDeveloperPo.class,po);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public List<ApDeveloperPo> queryApDeveloperByPo(ApDeveloperPo po) throws Exception {
		Condition con = Condition.build("queryApDeveloperByPo").filter(po.convertThis2Map());
		List<ApDeveloperPo> list = query(ApDeveloperPo.class, con);
		return list != null && list.size() > 0 ? list : null;
	}

}
