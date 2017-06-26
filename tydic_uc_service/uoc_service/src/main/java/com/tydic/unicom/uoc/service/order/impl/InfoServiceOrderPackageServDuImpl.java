package com.tydic.unicom.uoc.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPackagePo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderPackageServ;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderPackageServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderPackageVo;
@Service("InfoServiceOrderPackageServDu")
public class InfoServiceOrderPackageServDuImpl implements InfoServiceOrderPackageServDu{

	@Autowired
	private InfoServiceOrderPackageServ infoServiceOrderPackageServ;

//	@Override
//	public List<InfoServiceOrderPackageVo> queryInfoServiceOrderPackageByOfrOrderNo(
//			InfoServiceOrderPackageVo vo) throws Exception {
//		InfoServiceOrderPackagePo InfoServiceOrderPackagePo = new InfoServiceOrderPackagePo();
//		BeanUtils.copyProperties(vo, InfoServiceOrderPackagePo);
//		List<InfoServiceOrderPackageVo> listVo = new ArrayList<InfoServiceOrderPackageVo>();
//		List<InfoServiceOrderPackagePo> listPo = infoServiceOrderPackageServ.queryInfoServiceOrderPackageByOfrOrderNo(InfoServiceOrderPackagePo);
//		if(listPo != null && listPo.size()>0){
//			for(InfoServiceOrderPackagePo po : listPo){
//				InfoServiceOrderPackageVo ordVo = new InfoServiceOrderPackageVo();
//				BeanUtils.copyProperties(po, ordVo);
//				listVo.add(ordVo);
//			}
//			return listVo;
//		}else{
//			return null;
//		}
//	}

	@Override
	public List<InfoServiceOrderPackageVo> queryInfoServiceOrderPackageByOrderNo(
			InfoServiceOrderPackageVo vo) throws Exception {
		InfoServiceOrderPackagePo InfoServiceOrderPackagePo = new InfoServiceOrderPackagePo();
		BeanUtils.copyProperties(vo, InfoServiceOrderPackagePo);
		List<InfoServiceOrderPackageVo> listVo = new ArrayList<InfoServiceOrderPackageVo>();
		List<InfoServiceOrderPackagePo> listPo = infoServiceOrderPackageServ.queryInfoServiceOrderPackageByOrderNo(InfoServiceOrderPackagePo);
		if(listPo != null && listPo.size()>0){
			for(InfoServiceOrderPackagePo po : listPo){
				InfoServiceOrderPackageVo ordVo = new InfoServiceOrderPackageVo();
				BeanUtils.copyProperties(po, ordVo);
				listVo.add(ordVo);
			}
			return listVo;
		}else{
			return null;
		}
	}
}
