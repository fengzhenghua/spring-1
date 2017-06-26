package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.uoccode.po.InfoUserGrantPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.InfoUserGrantServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoUserGrantServ")
public class InfoUserGrantServImpl extends BaseServImpl<InfoUserGrantPo> implements InfoUserGrantServ {

	@Override
	public boolean create(InfoUserGrantPo infoUserGrantPo) throws Exception {
		create(InfoUserGrantPo.class,infoUserGrantPo);
		return true;
	}

	@Override
	public boolean update(InfoUserGrantPo infoUserGrantPo) throws Exception {
		update(InfoUserGrantPo.class,infoUserGrantPo);
		return true;
	}

	@Override
	public List<InfoUserGrantPo> queryInfoUserGrantList(InfoUserGrantPo infoUserGrantPo) throws Exception {
		Condition con = Condition. build("queryInfoUserGrantList").filter( infoUserGrantPo.convertThis2Map());
		List<InfoUserGrantPo> list=query(InfoUserGrantPo.class,con);
		if(list == null || list.size() <= 0){
			return null;
		}
		return list ;
	}

	@Override
	public List<InfoUserGrantPo> queryNeedGrantUser() throws Exception {
		InfoUserGrantPo infoUserGrantPoQuery = new InfoUserGrantPo();
		//获取当前年月
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String currentMonth = format.format(date);
		currentMonth = currentMonth.substring(0, currentMonth.length()-2);
		//获取前一天的年月日
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day=cal.get(Calendar.DATE); 
		cal.set(Calendar.DATE,day-2);
        String yesterDate = format.format(cal.getTime());
        String yesterMonth = yesterDate.substring(0, yesterDate.length()-2);
        String yesterDay = yesterDate.substring(yesterDate.length()-2, yesterDate.length());
        
        infoUserGrantPoQuery.setCurrent_month(currentMonth);
        infoUserGrantPoQuery.setYester_month(yesterMonth);
        infoUserGrantPoQuery.setYester_day(yesterDay);
        Condition con = Condition.build("queryNeedGrantUser").filter(infoUserGrantPoQuery.convertThis2Map());
        List<InfoUserGrantPo> list=query(InfoUserGrantPo.class,con);
        if(list != null && list.size()>0){
        	return list;
        }
        else{
        	return null;
        }
	}

}
