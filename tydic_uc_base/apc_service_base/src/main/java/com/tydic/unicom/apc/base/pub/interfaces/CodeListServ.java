package com.tydic.unicom.apc.base.pub.interfaces;

import java.util.List;

import com.tydic.unicom.apc.base.pub.po.CodeListPo;

/**
 * <p></p>
 * @author JinXue 2014-9-15 下午7:20:15
 * @ClassName OperServImpl
 * @Description TODO
 * @version V1.0  
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-9-15
 * @modify by reason:{方法名}:{原因}
 */
public interface CodeListServ {
	

	public List<CodeListPo> queryCodeListByTypeCode( String typeCode );
	
}
