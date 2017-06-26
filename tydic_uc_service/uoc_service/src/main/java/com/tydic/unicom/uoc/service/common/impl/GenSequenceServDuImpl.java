package com.tydic.unicom.uoc.service.common.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.unicom.uoc.base.uoccode.po.SequencesPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.GetIdServ;
import com.tydic.unicom.uoc.service.common.interfaces.GenSequenceServDu;
import com.tydic.unicom.uoc.service.common.vo.SequencesVo;

@Service("GenSequenceServDu")
public class GenSequenceServDuImpl implements GenSequenceServDu{
	Logger logger = Logger.getLogger(GenSequenceServDuImpl.class);

	private static Map<String, SequencesVo> map=new HashMap<String, SequencesVo>();
	private static int count=1;
	private static Map<String,Date> initTimeMap=new HashMap<String, Date>();

	@Autowired
	private GetIdServ getIdServ;

	@Override
	public String getSequenceNum(String seqCode, String provinceCode, String areaCode) throws Exception {
		logger.info("======================获取系统内部seq========================"+seqCode+provinceCode+areaCode);
		String sequenceNum="";
		if(map.isEmpty()){
			map = getSequenceVoMap();
			if(map.containsKey(seqCode+provinceCode+areaCode)){
				sequenceNum = genSequenceNum(map.get(seqCode+provinceCode+areaCode));
			}
			else{
				sequenceNum = genSequenceNum(map.get(seqCode+provinceCode+"*"));
			}
		}else{
			if(map.containsKey(seqCode+provinceCode+areaCode)){
				sequenceNum = genSequenceNum(map.get(seqCode+provinceCode+areaCode));
			}
			else{
				sequenceNum = genSequenceNum(map.get(seqCode+provinceCode+"*"));
			}
		}
		logger.info("==============================>>>seq:"+sequenceNum);
		return sequenceNum;
	}

	private String genSequenceNum(SequencesVo sequencesVo) throws Exception{
		String sequenceNum="";
		String head="";
		String middle="";
		String end="";

		if(sequencesVo.getSeq_tail_type().equals("100")){
			head=StringUtils.isNotEmpty(sequencesVo.getSeq_head())?sequencesVo.getSeq_head():"";
			middle = getTimeStr(sequencesVo.getSeq_time_str());
			end=getFixLenthString(Integer.parseInt(sequencesVo.getSeq_tail_lenth()));
			sequenceNum=head+middle+end;
		}else if(sequencesVo.getSeq_tail_type().equals("101")){
			head=StringUtils.isNotEmpty(sequencesVo.getSeq_head())?sequencesVo.getSeq_head():"";
			middle = getTimeStr(sequencesVo.getSeq_time_str());
			boolean isReset=false;
			if(StringUtils.isNotEmpty(sequencesVo.getSeq_tail_cir_type())){
				if(initTimeMap.isEmpty()){
					initTimeMap.put(sequencesVo.getId(), new Date());
				}else{
					if(initTimeMap.containsKey(sequencesVo.getId())){
						isReset=isReset(sequencesVo);
					}else{
						initTimeMap.put(sequencesVo.getId(), new Date());
					}
				}
				if(isReset){
					logger.info("==============================>>>seq复位");
				}
			}
			end=getOrderedNum(sequencesVo);
			sequenceNum=head+middle+end;
		}

		return sequenceNum;
	}

	private Map<String, SequencesVo> getSequenceVoMap() throws Exception{
		Map<String, SequencesVo> map = new HashMap<String, SequencesVo>();
		List<SequencesPo> list = getIdServ.query();
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				SequencesVo sequencesVo = new SequencesVo();
				BeanUtils.copyProperties(list.get(i), sequencesVo);
				map.put(sequencesVo.getSeq_code()+sequencesVo.getProvince_code()+sequencesVo.getArea_code(), sequencesVo);
			}
			return map;
		}
		else{
			return null;
		}
	}

	private String getTimeStr(String timeFormat){
		String timeStr="";
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat(timeFormat);
		timeStr = format.format(date);
		return timeStr;
	}

	private String getFixLenthString(int strLength) {
		String endSequenceNum="";
	    Random rm = new Random();
	    double rondomNum = (1 + rm.nextDouble()) * Math.pow(10, strLength);
	    String fixLenthString = String.valueOf(rondomNum);
	    endSequenceNum=fixLenthString.substring(1, strLength + 1);
	    return endSequenceNum;
	}

	private String getOrderedNum(SequencesVo sequencesVo) throws Exception{
		String endSequenceNum="";
		String format="";
		if(sequencesVo.getSeq_tail_rightjustify().equals("1")){
			for(int i=0;i<Integer.parseInt(sequencesVo.getSeq_tail_lenth());i++){
				format=format+"0";
			}
		}else{
			format="0";
		}
		DecimalFormat df=new DecimalFormat(format);
		int step=Integer.parseInt(sequencesVo.getSeq_tail_step());
		if(StringUtils.isNotEmpty(sequencesVo.getSeq_tail_begin())){
			if(count==1){
				endSequenceNum=df.format(sequencesVo.getSeq_tail_begin());
				count++;
			}else if(count<step){
				endSequenceNum=df.format(Integer.parseInt(sequencesVo.getSeq_tail_begin())+count-1);
				count++;
			}else if(count==step){
				endSequenceNum=df.format(Integer.parseInt(sequencesVo.getSeq_tail_begin())+count-1);
				count=1;
				sequencesVo.setSeq_tail_curr(endSequenceNum);
				sequencesVo.setSeq_tail_end(df.format(Integer.parseInt(sequencesVo.getSeq_tail_curr())+step));
				//更新数据库字段
				SequencesPo sequencesPo=new SequencesPo();
				BeanUtils.copyProperties(sequencesVo, sequencesPo);
				getIdServ.updateSequences(sequencesPo);
			}
		}else{
			if(count==1){
				endSequenceNum=df.format(StringUtils.isNotEmpty(sequencesVo.getSeq_tail_curr())?Integer.parseInt(sequencesVo.getSeq_tail_curr())+count:count);
				count++;
			}else if(count<step){
				endSequenceNum=df.format(StringUtils.isNotEmpty(sequencesVo.getSeq_tail_curr())?Integer.parseInt(sequencesVo.getSeq_tail_curr())+count:count);
				count++;
			}else if(count==step){
				endSequenceNum=df.format(StringUtils.isNotEmpty(sequencesVo.getSeq_tail_end())?Integer.parseInt(sequencesVo.getSeq_tail_end()):count);
				count=1;
				sequencesVo.setSeq_tail_curr(endSequenceNum);
				sequencesVo.setSeq_tail_end(df.format(Integer.parseInt(sequencesVo.getSeq_tail_curr())+step));
				//更新数据库字段
				SequencesPo sequencesPo=new SequencesPo();
				BeanUtils.copyProperties(sequencesVo, sequencesPo);
				getIdServ.updateSequences(sequencesPo);
			}
		}

		return endSequenceNum;
	}

	private boolean isReset(SequencesVo sequencesVo) throws Exception{
		boolean isReset=false;
		Date curDate=new Date();
		long interval=curDate.getTime()-initTimeMap.get(sequencesVo.getId()).getTime();
		switch (sequencesVo.getSeq_tail_cir_type()) {
			//每分钟复位
			case "101":
				if(interval/(1000*60)>1){
					initTimeMap.put(sequencesVo.getId(), new Date());
					count=1;
					sequencesVo.setSeq_tail_curr("");
					sequencesVo.setSeq_tail_end("");
					//更新数据库字段
					SequencesPo sequencesPo=new SequencesPo();
					BeanUtils.copyProperties(sequencesVo, sequencesPo);
					getIdServ.updateSequences(sequencesPo);
					isReset=true;
				}
				break;
			//每小时复位
			case "102":
				if(interval/(1000*60*60)>1){
					initTimeMap.put(sequencesVo.getId(), new Date());
					count=1;
					sequencesVo.setSeq_tail_curr("");
					sequencesVo.setSeq_tail_end("");
					//更新数据库字段
					SequencesPo sequencesPo=new SequencesPo();
					BeanUtils.copyProperties(sequencesVo, sequencesPo);
					getIdServ.updateSequences(sequencesPo);
					isReset=true;
				}
				break;
			//每周复位
			case "104":
				if(interval/(1000*60*60)>168){
					initTimeMap.put(sequencesVo.getId(), new Date());
					count=1;
					sequencesVo.setSeq_tail_curr("");
					sequencesVo.setSeq_tail_end("");
					//更新数据库字段
					SequencesPo sequencesPo=new SequencesPo();
					BeanUtils.copyProperties(sequencesVo, sequencesPo);
					getIdServ.updateSequences(sequencesPo);
					isReset=true;
				}
				break;
			default:
				break;
		}

		return isReset;
	}
}
