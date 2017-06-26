package com.tydic.unicom.apc.service.pub.interfaces;

import java.util.List;

import com.tydic.unicom.apc.business.pub.vo.ApDeveloperVo;


public interface ApDeveloperServDu {

	public ApDeveloperVo getApDeveloper(ApDeveloperVo vo) throws Exception;

	public boolean addApDeveloper(ApDeveloperVo vo) throws Exception;

	public boolean deleteApDeveloper(ApDeveloperVo vo) throws Exception;

	public boolean updateApDeveloper(ApDeveloperVo vo) throws Exception;

	public List<ApDeveloperVo> queryApDeveloperByVo(ApDeveloperVo vo) throws Exception;
}
