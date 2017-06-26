package com.tydic.unicom.apc.service.order.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.tydic.unicom.apc.base.order.interfaces.InfoOrderAttrServ;
import com.tydic.unicom.apc.base.order.po.InfoOrderAttrPo;
import com.tydic.unicom.apc.service.order.interfaces.InfoOrderAttrServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Service("InfoOrderAttrServDu")
public class InfoOrderAttrServDuImpl implements InfoOrderAttrServDu{
	
	private static Logger logger = Logger.getLogger(InfoOrderAttrServDuImpl.class);
	
	@Autowired
	private InfoOrderAttrServ infoOrderAttrServ;

	@Override
	public UocMessage updateInfoOrderAttrList(List<InfoOrderAttrPo> list) throws Exception {
		UocMessage uocMessage =new UocMessage();
		boolean isok = false;
		for(int i=0;i<list.size();i++){
			InfoOrderAttrPo infoOrderAttrPoQuery =infoOrderAttrServ.queryInfoOrderAttrByAttrIdAndOrderId(list.get(i));
			if(infoOrderAttrPoQuery == null){
				isok = infoOrderAttrServ.create(list.get(i));
				logger.info("==============插入订单属性表第"+i+"条数据结果:"+isok);
			}
			else{
				infoOrderAttrPoQuery.setAttr_value(list.get(i).getAttr_value());
				isok = infoOrderAttrServ.update(infoOrderAttrPoQuery);
				logger.info("==============更新订单属性表第"+i+"条数据结果:"+isok);
			}
		}
		
		if(!isok){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("更新订单属性表失败");
			return uocMessage;
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("更新订单属性表成功");
			return uocMessage;
		}
	}

	@Override
	public UocMessage addInfoOrderAttrList(List<InfoOrderAttrPo> list) throws Exception {
		UocMessage uocMessage = new UocMessage();
		boolean isok = false;
		for(int i=0;i<list.size();i++){
			isok = infoOrderAttrServ.create(list.get(i));
			logger.info("==============插入订单属性表第"+i+"条数据结果:"+isok);
		}
		
		if(!isok){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("插入订单属性表失败");
			return uocMessage;
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("插入订单属性表成功");
			return uocMessage;
		}
	}

	@Override
	public UocMessage getApOrderAttrInfo(String apOrderId) throws Exception {
		UocMessage uocMessage = new UocMessage();
		InfoOrderAttrPo infoOrderAttrPoQuery = new InfoOrderAttrPo();
		infoOrderAttrPoQuery.setOrder_id(apOrderId);
		List<InfoOrderAttrPo> list = infoOrderAttrServ.queryInfoOrderAttrByOrderId(infoOrderAttrPoQuery);
		if(list != null && list.size()>0){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("获取触点订单属性参数成功");
			for(int i=0;i<list.size();i++){
				uocMessage.addArg(list.get(i).getAttr_id(), list.get(i).getAttr_value());
			}
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("获取触点订单属性参数失败");
		}
		return uocMessage;
	}

}
