package com.tydic.unicom.apc.service.pub.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.apc.base.pub.interfaces.ApDefineServ;
import com.tydic.unicom.apc.base.pub.po.ApDefinePo;
import com.tydic.unicom.apc.business.pub.vo.ApDefineVo;
import com.tydic.unicom.apc.service.pub.interfaces.ApDefineServDu;
@Service("ApDefineServDu")
public class ApDefineServDuImpl implements ApDefineServDu {

	@Autowired
	ApDefineServ apDefineServ;

	private static Logger LOGGER = LoggerFactory.getLogger(ApDefineServDuImpl.class);

	@Override
	public ApDefineVo getApDefine(ApDefineVo vo) throws Exception {
		ApDefinePo apDefinePo =new ApDefinePo();
		BeanUtils.copyProperties(vo,apDefinePo);
		apDefinePo =apDefineServ.getApDefinePo(apDefinePo);
		if(apDefinePo !=null){
			BeanUtils.copyProperties(apDefinePo,vo);
			return vo;
		}else{
			return null;
		}
	}

	@Override
	public boolean addApDefine(ApDefineVo vo) throws Exception {
		ApDefinePo apDefinePo =new ApDefinePo();
		BeanUtils.copyProperties(vo,apDefinePo);

		return apDefineServ.addApDefinePo(apDefinePo);
	}

	@Override
	public boolean deleteApDefine(ApDefineVo vo) throws Exception {
		ApDefinePo apDefinePo =new ApDefinePo();
		BeanUtils.copyProperties(vo,apDefinePo);
		return apDefineServ.deleteApDefinePo(apDefinePo);
	}

	@Override
	public boolean updateApDefine(ApDefineVo vo) throws Exception {
		ApDefinePo apDefinePo =new ApDefinePo();
		BeanUtils.copyProperties(vo,apDefinePo);
		return apDefineServ.updateApDefinePo(apDefinePo);
	}

	@Override
	public List<ApDefineVo> queryApDefinePageByVo(ApDefineVo vo, int pageNo, int pageSize) throws Exception {
		ApDefinePo apDefinePo = new ApDefinePo();
		BeanUtils.copyProperties(vo, apDefinePo);
		List<ApDefinePo> poList = apDefineServ.queryApDefinePageByPo(apDefinePo, pageNo, pageNo);
		if (poList != null && poList.size() > 0) {
			List<ApDefineVo> list = new ArrayList<ApDefineVo>();
			for (ApDefinePo po : poList) {
				ApDefineVo apDefineVo = new ApDefineVo();
				BeanUtils.copyProperties(po, apDefineVo);
				list.add(apDefineVo);
			}
			return list;
		} else {
			return null;
		}
	}

	@Override
	public int queryApDefineCount(ApDefineVo vo) throws Exception {
		ApDefinePo apDefinePo = new ApDefinePo();
		BeanUtils.copyProperties(vo, apDefinePo);
		return apDefineServ.queryApDefineCount(apDefinePo);
	}

	@Override
	public List<ApDefineVo> queryEffectiveApDefinePage(ApDefineVo vo,int pageNo, int pageSize) throws Exception {
		ApDefinePo apDefinePo = new ApDefinePo();
		BeanUtils.copyProperties(vo, apDefinePo);
		List<ApDefinePo> poList = apDefineServ.queryEffectiveApDefinePage(apDefinePo, pageNo, pageSize);
		if(poList != null && poList.size()>0){
			List<ApDefineVo> list = new ArrayList<ApDefineVo>();
			for (ApDefinePo po : poList) {
				ApDefineVo apDefineVo = new ApDefineVo();
				BeanUtils.copyProperties(po, apDefineVo);
				list.add(apDefineVo);
			}
			return list;
		}
		else{
			return null;
		}
	}

	@Override
	public List<ApDefineVo> queryInvalidApDefinePage(ApDefineVo vo, int pageNo,int pageSize) throws Exception {
		ApDefinePo apDefinePo = new ApDefinePo();
		BeanUtils.copyProperties(vo, apDefinePo);
		List<ApDefinePo> poList = apDefineServ.queryInvalidApDefinePage(apDefinePo, pageNo, pageSize);
		if(poList != null && poList.size()>0){
			List<ApDefineVo> list = new ArrayList<ApDefineVo>();
			for (ApDefinePo po : poList) {
				ApDefineVo apDefineVo = new ApDefineVo();
				BeanUtils.copyProperties(po, apDefineVo);
				list.add(apDefineVo);
			}
			return list;
		}
		else{
			return null;
		}
	}

	@Override
	public int queryEffectiveApDefineCount(ApDefineVo vo) throws Exception {
		ApDefinePo apDefinePo = new ApDefinePo();
		BeanUtils.copyProperties(vo, apDefinePo);
		return apDefineServ.queryEffectiveApDefineCount(apDefinePo);
	}

	@Override
	public int queryInvalidApDefineCount(ApDefineVo vo) throws Exception {
		ApDefinePo apDefinePo = new ApDefinePo();
		BeanUtils.copyProperties(vo, apDefinePo);
		return apDefineServ.queryInvalidApDefineCount(apDefinePo);
	}

	@Override
	public List<ApDefineVo> queryApDefinePageByPo(ApDefineVo vo)
			throws Exception {
		ApDefinePo apDefinePo = new ApDefinePo();
		BeanUtils.copyProperties(vo, apDefinePo);
		List<ApDefinePo> poList=apDefineServ.queryApDefineByPo(apDefinePo);
		if(poList != null && poList.size()>0){
			List<ApDefineVo> list = new ArrayList<ApDefineVo>();
			for (ApDefinePo po : poList) {
				ApDefineVo apDefineVo = new ApDefineVo();
				BeanUtils.copyProperties(po, apDefineVo);
				list.add(apDefineVo);
			}
			return list;
		}
		else{
			return null;
		}
	}

}
