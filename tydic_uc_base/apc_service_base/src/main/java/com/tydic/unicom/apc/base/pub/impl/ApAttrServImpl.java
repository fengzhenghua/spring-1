package com.tydic.unicom.apc.base.pub.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.apc.base.pub.interfaces.ApAttrServ;
import com.tydic.unicom.apc.base.pub.po.ApAttrPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

/**
 * 触点属性表数据访问接口实现类
 * @author ZhangCheng
 * @date 2017-04-24
 */
@Service(value = "ApAttrServ")
public class ApAttrServImpl extends BaseServImpl<ApAttrPo> implements ApAttrServ {
	
	private static final Logger logger = LoggerFactory.getLogger(ApAttrServImpl.class);

	@Override
	public ApAttrPo getApAttrById(ApAttrPo apAttrPo) {
		this.printRequestParamToLog("获取",apAttrPo);
		Condition con = Condition.build("queryApAttrById").filter(apAttrPo.convertThis2Map());
		List<ApAttrPo> apAttrPos = query(ApAttrPo.class, con);
		if(apAttrPos != null && apAttrPos.size() > 0){
			return apAttrPos.get(0);
		}
		return null;
	}

	@Override
	public List<ApAttrPo> listApAttrByApAttrPo(ApAttrPo apAttrPo) {
		this.printRequestParamToLog("获取",apAttrPo);
		Condition con = Condition.build("queryApAttrByApAttrPo").filter(apAttrPo.convertThis2Map());
		List<ApAttrPo> apAttrPos = query(ApAttrPo.class, con);
		if(apAttrPos != null && apAttrPos.size() > 0){
			return apAttrPos;
		}
		return null;
	}

	@Override
	public boolean saveApAttr(ApAttrPo apAttrPo) {
		this.printRequestParamToLog("新增",apAttrPo);
		try{
			super.create(ApAttrPo.class,apAttrPo);
		}catch(Exception e){
			this.printExceptionToLog("新增",e);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean saveApAttr(List<ApAttrPo> apAttrPoList) {
		try{
			for(ApAttrPo apAttrPo : apAttrPoList){
				this.printRequestParamToLog("新增",apAttrPo);
				super.create(ApAttrPo.class,apAttrPo);
			}
		}catch(Exception e){
			this.printExceptionToLog("新增",e);
			this.removeApAttr(apAttrPoList.get(0));
			return false;
		}
		return true;
	}
	
	@Override
	public boolean updateApAttr(List<ApAttrPo> apAttrPoList) {
		try{
			this.removeApAttr(apAttrPoList.get(0));
			for(ApAttrPo apAttrPo : apAttrPoList){
				this.printRequestParamToLog("新增",apAttrPo);
				super.create(ApAttrPo.class,apAttrPo);
			}
		}catch(Exception e){
			this.printExceptionToLog("修改",e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateApAttr(ApAttrPo apAttrPo) {
		this.printRequestParamToLog("修改",apAttrPo);
		Integer result;
		try{
			result = super.update(ApAttrPo.class,apAttrPo);
		}catch(Exception e){
			this.printExceptionToLog("修改",e);
			return false;
		}
		if(result != null && result == 1){
			return true;
		}
		return false;
	}

	@Override
	public boolean removeApAttr(ApAttrPo apAttrPo) {
		this.printRequestParamToLog("删除",apAttrPo);
		try{
			super.remove(ApAttrPo.class,apAttrPo);
		}catch(Exception e){
			this.printExceptionToLog("删除",e);
			return false;
		}
		return true;
	}
	
	private void printRequestParamToLog(String operType,ApAttrPo apAttrPo){
		if(logger.isInfoEnabled()){
			logger.info("INFO [{}触点属性]==========>请求参数:{}",operType,apAttrPo.toString());
		}
	}
	private void printExceptionToLog(String operType,Exception ex){
		if(logger.isInfoEnabled()){
			logger.info("INFO [{}触点属性]==========>系统异常:{},异常堆栈:{}",operType,ex.getMessage(),ex.getStackTrace().toString());
		}
	}

	@Override
	public ApAttrPo queryApId(ApAttrPo apAttrPo) {
		Condition con = Condition.build("queryApId").filter(apAttrPo.convertThis2Map());
		List<ApAttrPo> apAttrPos = query(ApAttrPo.class, con);
		if(apAttrPos != null && apAttrPos.size() > 0){
			return apAttrPos.get(0);
		}
		return null;
	}
}
