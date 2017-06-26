package com.tydic.unicom.uoc.base.uochis.service.interfaces;


import com.tydic.unicom.uoc.base.uochis.po.InfoGztHisPo;

public interface InfoGztHisServ {
	
	public boolean createInfoGztHisPo(InfoGztHisPo po)throws Exception;
	
	public InfoGztHisPo queryInfoGztHisByServOrderNo(InfoGztHisPo po)throws Exception;

}
