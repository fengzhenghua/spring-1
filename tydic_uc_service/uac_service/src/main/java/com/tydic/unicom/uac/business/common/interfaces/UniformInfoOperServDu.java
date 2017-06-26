package com.tydic.unicom.uac.business.common.interfaces;

import java.util.Map;

import com.tydic.unicom.uac.business.common.vo.UniformInfoOperVo;

public interface UniformInfoOperServDu {

	public UniformInfoOperVo findUniformLogin(UniformInfoOperVo uniformInfoOperVo) throws Exception;
	
	public boolean addUniformInfoOperRedis(UniformInfoOperVo uniformInfoOperVo) throws Exception;
	
	public boolean deleteUniformInfoOperRedis(UniformInfoOperVo uniformInfoOperVo) throws Exception;
	
	public Map<String,Object> getZbInfoAgentMap(String operNo);
	
	public UniformInfoOperVo getRedisUniformInfoOper(String jsessionId) throws Exception;
	
	public UniformInfoOperVo findRestUniformLogin(UniformInfoOperVo uniformInfoOperVo) throws Exception;
	
	public boolean deleteRestLoginIn(UniformInfoOperVo uniformInfoOperVo) throws Exception;
}
