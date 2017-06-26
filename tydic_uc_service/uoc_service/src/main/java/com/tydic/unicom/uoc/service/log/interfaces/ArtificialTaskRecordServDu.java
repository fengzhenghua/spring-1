package com.tydic.unicom.uoc.service.log.interfaces;

import com.tydic.unicom.uoc.service.log.vo.ProcInstTaskAssignRecordVo;

public interface ArtificialTaskRecordServDu {

	/**
	 * 写入人工任务分配日志
	 * @param procInstTaskAssignRecordVo
	 */
	public void insertArtificialTaskRecord(ProcInstTaskAssignRecordVo procInstTaskAssignRecordVo) throws Exception;
}
