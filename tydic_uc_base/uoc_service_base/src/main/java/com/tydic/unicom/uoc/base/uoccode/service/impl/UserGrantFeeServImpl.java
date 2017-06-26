package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.uoccode.po.UserGrantFeePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.UserGrantFeeServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("UserGrantFeeServ")
public class UserGrantFeeServImpl extends BaseServImpl<UserGrantFeePo> implements UserGrantFeeServ {

	@Override
	public boolean create(UserGrantFeePo userGrantFeePo) throws Exception {
		create(UserGrantFeePo.class,userGrantFeePo);
		return true;
	}

	@Override
	public boolean update(UserGrantFeePo userGrantFeePo) throws Exception {
		update(UserGrantFeePo.class,userGrantFeePo);
		return true;
	}

	@Override
	public List<UserGrantFeePo> queryUserGrantFeeList(UserGrantFeePo userGrantFeePo)throws Exception {
		Condition con = Condition. build("queryUserGrantFeeList").filter( userGrantFeePo.convertThis2Map());
		List<UserGrantFeePo> list=query(UserGrantFeePo.class,con);
		if(list == null || list.size() <= 0){
			return null;
		}
		return list ;
	}

}
