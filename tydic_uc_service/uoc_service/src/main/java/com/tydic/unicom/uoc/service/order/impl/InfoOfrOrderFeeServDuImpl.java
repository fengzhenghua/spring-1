package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoOfrOrderFeePo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoOfrOrderFeeServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoOfrOrderFeeServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoOfrOrderFeeVo;
@Service("InfoOfrOrderFeeServDu")
public class InfoOfrOrderFeeServDuImpl implements InfoOfrOrderFeeServDu{

	@Autowired
	private InfoOfrOrderFeeServ infoOfrOrderFeeServ;
	@Override
	public List<InfoOfrOrderFeeVo> queryInfoOfrOrderFeeByOrderNo(
			InfoOfrOrderFeeVo vo) throws Exception {
		InfoOfrOrderFeePo infoOfrOrderFeePo = new InfoOfrOrderFeePo();
		BeanUtils.copyProperties(vo, infoOfrOrderFeePo);
		List<InfoOfrOrderFeePo> listPo = infoOfrOrderFeeServ.queryInfoOfrOrderFeeByOrderNo(infoOfrOrderFeePo);
		List<InfoOfrOrderFeeVo> listVo = new ArrayList<InfoOfrOrderFeeVo>();
		if(listPo != null && listPo.size()>0){
			for(InfoOfrOrderFeePo po : listPo){
				InfoOfrOrderFeeVo ordVo = new InfoOfrOrderFeeVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}

}
