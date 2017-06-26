package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.common.po.WriteCardResultInPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoWriteCardResultHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoWriteCardResultHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoWriteCardResultHisServ")
public class InfoWriteCardResultHisServImpl extends BaseServImpl<InfoWriteCardResultHisPo> implements InfoWriteCardResultHisServ{
	
	@Override
	public boolean createInfoWriteCardResultHisPo(InfoWriteCardResultHisPo po)throws Exception{
		if(po==null){
			return false;
		}		
		create(InfoWriteCardResultHisPo.class,po);
		return true;
	}
	
	@Override
	public List<InfoWriteCardResultHisPo> queryInfoWriteCardResultHis(WriteCardResultInPo po,int pageNo,int pageSize)throws Exception{
		
		Condition con = Condition. build("queryInfoWriteCardResultHis").filter(po.convertThis2Map());
		List<InfoWriteCardResultHisPo> list = S.get(InfoWriteCardResultHisPo. class).page(con ,pageNo ,pageSize );      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}
	
	@Override
	public int queryInfoWriteCardResultCount(WriteCardResultInPo po)throws Exception{
		Condition con = Condition. build("queryInfoWriteCardResultCount" ).filter( po.convertThis2Map());
		List<InfoWriteCardResultHisPo> list=query(InfoWriteCardResultHisPo.class,con );
		return list.size();
	}
	
	

}
