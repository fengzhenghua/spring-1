package com.tydic.unicom.uoc.service.order.his.interfaces;

import com.tydic.unicom.uoc.service.order.his.vo.ProcInstTaskInstHisVo;

public interface ProcInstTaskInstHisServDu {

	public ProcInstTaskInstHisVo queryProcInstTaskInstHisByOrderNo(ProcInstTaskInstHisVo vo)throws Exception;
	
	/**
	 * 查询任务历史表任务总数
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int queryTotalCountTaskHis(ProcInstTaskInstHisVo vo,String startTime, String endTime,String state) throws Exception;
	
	/**
	 * 查询任务历史表未逾期任务数
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int queryCountNotOverdueTaskHis(ProcInstTaskInstHisVo vo,String startTime, String endTime,String state) throws Exception;
	
	/**
	 * 查询任务历史表逾期小于1小时的任务数
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int queryCountOverdueLessOneHourTaskHis(ProcInstTaskInstHisVo vo,String startTime, String endTime,String state) throws Exception;
	
	/**
	 * 查询任务历史表逾期小于2小时的任务数
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int queryCountOverdueOneToTwoHourTaskHis(ProcInstTaskInstHisVo vo,String startTime, String endTime,String state) throws Exception;
	
	/**
	 * 查询任务历史表逾期小于12小时的任务数
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int queryCountOverdueTwoToTwelveHourTaskHis(ProcInstTaskInstHisVo vo,String startTime, String endTime,String state) throws Exception;
	
	/**
	 * 查询任务历史表逾期小于24小时的任务数
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int queryCountOverdueTwelveToTwentyFourHourTaskHis(ProcInstTaskInstHisVo vo,String startTime, String endTime,String state) throws Exception;
	
	/**
	 *  查询任务历史表逾期大于24小时的任务数
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int queryCountOverdueMoreTwentyFourHourTaskHis(ProcInstTaskInstHisVo vo,String startTime, String endTime,String state) throws Exception;
}
