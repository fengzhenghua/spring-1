package com.tydic.unicom.uoc.service.task.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.tydic.unicom.uoc.base.uoccode.po.InfoUserGrantPo;
import com.tydic.unicom.uoc.base.uoccode.po.UserGrantFeePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.InfoUserGrantServ;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.UserGrantFeeServ;
import com.tydic.unicom.uoc.service.task.interfaces.GrantServiceServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Service("GrantServiceServDu")
public class GrantServiceServDuImpl implements GrantServiceServDu{

	private static Logger logger = Logger.getLogger(GrantServiceServDuImpl.class);
	
	@Autowired
	private InfoUserGrantServ infoUserGrantServ;
	@Autowired
	private UserGrantFeeServ userGrantFeeServ;
	
	@Override
	public UocMessage cardCompare() throws Exception {
		UocMessage uocMessage = new UocMessage();
		boolean result = false;
		int count = 0;
		int countToal = 0;
		List<InfoUserGrantPo> grantList = infoUserGrantServ.queryNeedGrantUser();
		if(grantList != null && grantList.size()>0){
			countToal = grantList.size();
			for(int i=0;i<grantList.size();i++){
				UserGrantFeePo addUserGrantFeePo = new UserGrantFeePo();
				addUserGrantFeePo.setDevice_number(grantList.get(i).getDevice_number());
				addUserGrantFeePo.setActive_flag("1");
				addUserGrantFeePo.setAcct_month(new SimpleDateFormat("yyyyMM").format(new Date()));
				addUserGrantFeePo.setGrant_fee(grantList.get(i).getEach_grant());
				addUserGrantFeePo.setDeal_flag("0");
				addUserGrantFeePo.setCreate_date(new Date());
				result = userGrantFeeServ.create(addUserGrantFeePo);
				if(result){
					count++;
				}
			}
			logger.info("======================机卡比对完成，总需处理的数据条数为:"+countToal+"成功处理的条数为："+count);
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("机卡比对完成，总需处理的数据条数为:"+countToal+"成功处理的条数为："+count);
		}
		else{
			logger.info("======================机卡比对完成，总需处理的数据条数为:"+countToal+"成功处理的条数为："+count);
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("机卡比对完成，总需处理的数据条数为:"+countToal+"成功处理的条数为："+count);
		}
		return uocMessage;
	}

}
