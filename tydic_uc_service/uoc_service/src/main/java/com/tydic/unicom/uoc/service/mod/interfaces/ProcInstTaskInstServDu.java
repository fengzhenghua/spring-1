package com.tydic.unicom.uoc.service.mod.interfaces;


import java.util.List;

import com.tydic.unicom.uoc.service.mod.vo.ProcInstTaskInstVo;

public interface ProcInstTaskInstServDu {

	public ProcInstTaskInstVo queryProcInstTaskInstByTacheCodeAndProcInstId(ProcInstTaskInstVo procInstTaskInstVo,String ord) throws Exception;
	
	public ProcInstTaskInstVo queryProcInstTaskInstByOrderNoAndTaskState(ProcInstTaskInstVo procInstTaskInstVo) throws Exception;
	
	public ProcInstTaskInstVo queryProcInstTaskInstByTaskState(ProcInstTaskInstVo procInstTaskInstVo) throws Exception;
	
	public List<ProcInstTaskInstVo> queryProcInstTaskInstByOrderNo(ProcInstTaskInstVo procInstTaskInstVo) throws Exception;
	
	public boolean create(ProcInstTaskInstVo procInstTaskInstVo) throws Exception;
	
	public boolean updateByOrderNo(ProcInstTaskInstVo procInstTaskInstVo) throws Exception;
	
	/**任务逾期小于一小时*/
	public int queryCountOverdueLessOneHour(ProcInstTaskInstVo procInstTaskInstVo,String startTime,String endTime,String state) throws Exception;
	/**
	 * 查询逾期任务1到2小时
	 * */
	public int queryCountOverdueOneToTwoHour(ProcInstTaskInstVo procInstTaskInstVo,String startTime,String endTime,String state) throws Exception;
	/**
	 * 查询逾期任务2到12小时
	 * */
	public int queryCountOverdueTwoToTwelveHour(ProcInstTaskInstVo procInstTaskInstVo,String startTime,String endTime,String state) throws Exception;
	/**
	 * 查询逾期任务12到24小时
	 * */
	public int queryCountOverdueTwelveToTwentyFourHour(ProcInstTaskInstVo procInstTaskInstVo,String startTime,String endTime,String state) throws Exception;
	/**
	 * 查询逾期任务大于24小时
	 * */
	public int queryCountOverdueMoreTwentyFourHour(ProcInstTaskInstVo procInstTaskInstVo,String startTime,String endTime,String state) throws Exception;
	/**
	 * 查询未逾期任务
	 * */
	public int queryCountNotOverdue(ProcInstTaskInstVo procInstTaskInstVo,String startTime,String endTime,String state) throws Exception;
	/**
	 * 查询任务总数
	 * */
	public int queryCountTaskAll(ProcInstTaskInstVo procInstTaskInstVo,String startTime,String endTime,String state) throws Exception;

}
