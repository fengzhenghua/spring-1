package com.tydic.unicom.apc.base.pub.interfaces;

import java.util.List;

import com.tydic.unicom.apc.base.pub.po.ApAttrPo;

/**
 * 触点属性表数据访问接口
 * 
 * @author ZhangCheng
 * @date 2017-04-24
 */
public interface ApAttrServ {
	
	/** 获取单个触点属性通过ID */
	ApAttrPo getApAttrById(ApAttrPo apAttrPo);

	/** 获取多个触点属性通过触点属性实体 */
	List<ApAttrPo> listApAttrByApAttrPo(ApAttrPo apAttrPo);
	
	/** 获取单个触点ID */
	ApAttrPo queryApId(ApAttrPo apAttrPo);

	/** 保存多个触点属性 */
	boolean saveApAttr(List<ApAttrPo> apAttrPoList);
	
	/** 保存单个触点属性 */
	boolean saveApAttr(ApAttrPo apAttrPo);
	
	/** 修改多个触点属性 */
	boolean updateApAttr(List<ApAttrPo> apAttrPoList);

	/** 修改单个触点属性 */
	boolean updateApAttr(ApAttrPo apAttrPo);

	/** 删除单个或多个触点属性 */
	boolean removeApAttr(ApAttrPo apAttrPo);

}
