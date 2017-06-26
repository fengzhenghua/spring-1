package com.tydic.unicom.uoc.service.log.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskAssignRecordPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskAssignRecordServ;
import com.tydic.unicom.uoc.service.log.interfaces.ArtificialTaskRecordServDu;
import com.tydic.unicom.uoc.service.log.vo.ProcInstTaskAssignRecordVo;

@Service("ArtificialTaskRecordServDu")
public class ArtificialTaskRecordServDuImpl implements ArtificialTaskRecordServDu{
	Logger logger=Logger.getLogger(ArtificialTaskRecordServDuImpl.class);

	@Autowired
	private ProcInstTaskAssignRecordServ procInstTaskAssignRecordServ;

	@Override
	public void insertArtificialTaskRecord(ProcInstTaskAssignRecordVo procInstTaskAssignRecordVo) throws Exception {
		logger.info("--------开始写入proc_inst_task_assign_record日志---------");
		ProcInstTaskAssignRecordPo procInstTaskAssignRecordPo=new ProcInstTaskAssignRecordPo();
		BeanUtils.copyProperties(procInstTaskAssignRecordVo, procInstTaskAssignRecordPo);
		procInstTaskAssignRecordServ.create(procInstTaskAssignRecordPo);
	}

}
