package com.tydic.unicom.apc.base.pub.interfaces;

import java.util.List;

import com.tydic.unicom.apc.base.pub.po.ApDeveloperPo;

public interface ApDeveloperServ {

	public ApDeveloperPo getApDeveloperPo(ApDeveloperPo po) throws Exception;

	public boolean addApDeveloperPo(ApDeveloperPo po) throws Exception;

	public boolean deleteApDeveloperPo(ApDeveloperPo po) throws Exception;

	public boolean updateApDeveloperPo(ApDeveloperPo po) throws Exception;

	public List<ApDeveloperPo> queryApDeveloperByPo(ApDeveloperPo po) throws Exception;
}
