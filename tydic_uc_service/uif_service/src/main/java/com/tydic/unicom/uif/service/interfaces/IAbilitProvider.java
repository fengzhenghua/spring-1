package com.tydic.unicom.uif.service.interfaces;

import com.tydic.unicom.security.BaseObject;

/**
 * 
 * <p></p>
 * @author heguoyong 2017年5月17日 下午6:32:58
 * @ClassName IAbilitProvider
 * @Description 能力提供者接口
 * @version V1.0
 */
public interface IAbilitProvider<T extends BaseObject> {
	
	String callAblit(T requestVo);
}
