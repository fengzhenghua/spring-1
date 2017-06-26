package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.ProcInstTaskInstHisPo;

public interface ProcInstTaskInstHisServ {

	/**
	 * 写入人工任务实例表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */

	public boolean createProcInstTaskInstHisPo(ProcInstTaskInstHisPo po)throws Exception;

	public List<ProcInstTaskInstHisPo> queryTaskInstHisByOrderNo1(ProcInstTaskInstHisPo po) throws Exception;

	public ProcInstTaskInstHisPo queryProcInstTaskInstHisByOrderNo(ProcInstTaskInstHisPo po)throws Exception;

	/**
	 * 通过tache_code查询人工任务实例表
	 * @param order_no
	 * @return
	 * @throws Exception
	 */
	public List<ProcInstTaskInstHisPo> queryProcInstTaskInstHisByTacheCode(ProcInstTaskInstHisPo po) throws Exception;

	/**
	 * 查询任务历史表任务总数
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int queryTotalCountTaskHis(ProcInstTaskInstHisPo po,String startTime, String endTime,String state) throws Exception;

	/**
	 * 查询任务历史表未逾期任务数
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int queryCountNotOverdueTaskHis(ProcInstTaskInstHisPo po,String startTime, String endTime,String state) throws Exception;

	/**
	 * 查询任务历史表逾期小于1小时的任务数
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int queryCountOverdueLessOneHourTaskHis(ProcInstTaskInstHisPo po,String startTime, String endTime,String state) throws Exception;

	/**
	 * 查询任务历史表逾期小于2小时的任务数
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int queryCountOverdueOneToTwoHourTaskHis(ProcInstTaskInstHisPo po,String startTime, String endTime,String state) throws Exception;

	/**
	 * 查询任务历史表逾期小于12小时的任务数
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int queryCountOverdueTwoToTwelveHourTaskHis(ProcInstTaskInstHisPo po,String startTime, String endTime,String state) throws Exception;

	/**
	 * 查询任务历史表逾期小于24小时的任务数
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int queryCountOverdueTwelveToTwentyFourHourTaskHis(ProcInstTaskInstHisPo po,String startTime, String endTime,String state) throws Exception;

	/**
	 *  查询任务历史表逾期大于24小时的任务数
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int queryCountOverdueMoreTwentyFourHourTaskHis(ProcInstTaskInstHisPo po,String startTime, String endTime,String state) throws Exception;

	/**
	 * 统计各环节占比
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<ProcInstTaskInstHisPo> queryAllTaskInstHisGroupByTacheCode(ProcInstTaskInstHisPo po) throws Exception;

	/**
	 * 统计环节占总数
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int queryCountAllTaskInstHisGroupByTacheCode(ProcInstTaskInstHisPo po) throws Exception;
}
