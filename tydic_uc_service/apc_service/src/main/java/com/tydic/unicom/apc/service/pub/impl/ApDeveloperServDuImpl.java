package com.tydic.unicom.apc.service.pub.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.apc.base.pub.interfaces.ApDeveloperServ;
import com.tydic.unicom.apc.base.pub.po.ApDeveloperPo;
import com.tydic.unicom.apc.business.pub.vo.ApDeveloperVo;
import com.tydic.unicom.apc.service.pub.interfaces.ApDeveloperServDu;
@Service("ApDeveloperServDu")
public class ApDeveloperServDuImpl implements ApDeveloperServDu {

	@Autowired
	ApDeveloperServ apDeveloperServ;

	private static Logger LOGGER = LoggerFactory.getLogger(ApDeveloperServDuImpl.class);

	@Override
	public ApDeveloperVo getApDeveloper(ApDeveloperVo vo) throws Exception {
		ApDeveloperPo apDeveloperPo =new ApDeveloperPo();
		BeanUtils.copyProperties(vo,apDeveloperPo);
		apDeveloperPo =apDeveloperServ.getApDeveloperPo(apDeveloperPo);
		if(apDeveloperPo !=null){
			BeanUtils.copyProperties(apDeveloperPo,vo);
			return vo;
		}else{
			return null;
		}
	}

	@Override
	public boolean addApDeveloper(ApDeveloperVo vo) throws Exception {
		ApDeveloperPo apDeveloperPo =new ApDeveloperPo();
		BeanUtils.copyProperties(vo,apDeveloperPo);
		return apDeveloperServ.addApDeveloperPo(apDeveloperPo);
	}

	@Override
	public boolean deleteApDeveloper(ApDeveloperVo vo) throws Exception {
		ApDeveloperPo apDeveloperPo =new ApDeveloperPo();
		BeanUtils.copyProperties(vo,apDeveloperPo);
		return apDeveloperServ.deleteApDeveloperPo(apDeveloperPo);
	}

	@Override
	public boolean updateApDeveloper(ApDeveloperVo vo) throws Exception {
		ApDeveloperPo apDeveloperPo =new ApDeveloperPo();
		BeanUtils.copyProperties(vo,apDeveloperPo);
		return apDeveloperServ.updateApDeveloperPo(apDeveloperPo);
	}

	@Override
	public List<ApDeveloperVo> queryApDeveloperByVo(ApDeveloperVo vo) throws Exception {
		ApDeveloperPo apDeveloperPo = new ApDeveloperPo();
		BeanUtils.copyProperties(vo, apDeveloperPo);
		List<ApDeveloperPo> poList = apDeveloperServ.queryApDeveloperByPo(apDeveloperPo);
		if (poList != null && poList.size() > 0) {
			List<ApDeveloperVo> list = new ArrayList<ApDeveloperVo>();
			for (ApDeveloperPo po : poList) {
				ApDeveloperVo apDeveloperVo = new ApDeveloperVo();
				BeanUtils.copyProperties(po, apDeveloperVo);
				list.add(apDeveloperVo);
			}
			return list;
		} else {
			return null;
		}
	}

}
