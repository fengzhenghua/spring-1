package com.tydic.unicom.uoc.base.uocinst.service.interfaces;



import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.ArtificialTaskPo;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskInstPo;

public interface ProcInstTaskInstServ {
 /**
  * 操作人工任务实例表
  * @param procInstTaskInstPo
  * @return
  */
	public ProcInstTaskInstPo queryProcInstTaskInstByTacheCodeAndProcInstId(ProcInstTaskInstPo procInstTaskInstPo,String ord)throws Exception;

	public ProcInstTaskInstPo queryProcInstTaskInstByOrderNoAndTaskState(ProcInstTaskInstPo procInstTaskInstPo)throws Exception;

	public ProcInstTaskInstPo queryProcInstTaskInstByTaskState(ProcInstTaskInstPo procInstTaskInstPo)throws Exception;


	public boolean create(ProcInstTaskInstPo procInstTaskInstPo) throws Exception;
	/**
	 * 根据关联订单编码查询
	 * @param procInstTaskInstPo
	 * @return
	 * @throws Exception
	 */

	public List<ProcInstTaskInstPo> queryProcInstTaskInstByOrderNo(ProcInstTaskInstPo procInstTaskInstPo)throws Exception;

	/**
	 * 根据关联订单编码删除记录
	 * @param procInstTaskInstPo
	 * @return
	 * @throws Exception
	 */

	public boolean deleteProcInstTaskInstByOrderNo(ProcInstTaskInstPo po)throws Exception;

	/**
	 * 根据订单号和订单类型更新数据
	 * @param procInstTaskInstPo
	 * @return
	 * @throws Exception
	 */
	public boolean updateByOrderNo(ProcInstTaskInstPo procInstTaskInstPo) throws Exception;

	/**
	 * 通过订单号、订单类型、受理工号、受理工号名称、受理渠道名称、受理渠道、时间段等条件查询人工任务实例表
	 * @param procInstTaskInstPo
	 * @return
	 * @throws Exception
	 */
	public List<ProcInstTaskInstPo> queryProcInstTaskInst(ProcInstTaskInstPo procInstTaskInstPo) throws Exception;
	/**
	 * 查询总数
	 * @param procInstTaskInstPo
	 * @return
	 * @throws Exception
	 */
	public int countProcInstTaskInst(ProcInstTaskInstPo procInstTaskInstPo) throws Exception;

	/**
	 * 通过task_id查询人工任务实例表
	 * @param task_id
	 * @return
	 * @throws Exception
	 */
	public ProcInstTaskInstPo queryProcInstTaskInstByTaskId(String task_id) throws Exception;

	/**
	 * 通过order_no,tache_code查询人工任务实例表
	 * @param order_no
	 * @param tache_code
	 * @return
	 * @throws Exception
	 */
	public List<ProcInstTaskInstPo> querytaskInstByOrderNoAndTacheCode(String order_no,String tache_code) throws Exception;

	/**
	 * 通过tache_code查询人工任务实例表
	 * @param order_no
	 * @return
	 * @throws Exception
	 */
	public List<ProcInstTaskInstPo> queryProcInstTaskInstByTacheCode(ProcInstTaskInstPo po) throws Exception;


	/**
	 * 服务订单人工任务实例表列表查询
	 * @param order_no
	 * @return
	 * @throws Exception
	 */
	public List<ProcInstTaskInstPo> queryProcInstTaskInstListByServOrderNo(ArtificialTaskPo po,int pageNo,int pageSize) throws Exception;

	/**
	 * 服务订单人工任务实例表列表总数查询
	 * @param order_no
	 * @return
	 * @throws Exception
	 */
	public int queryProcInstTaskInstListCountByServOrderNo(ArtificialTaskPo po) throws Exception;

	/**
	 * 销售订单人工任务实例表列表查询
	 * @param order_no
	 * @return
	 * @throws Exception
	 */
	public List<ProcInstTaskInstPo> queryProcInstTaskInstListBySaleOrderNo(ArtificialTaskPo po,int pageNo,int pageSize) throws Exception;

	/**
	 * 销售订单人工任务实例表列表总数查询
	 * @param order_no
	 * @return
	 * @throws Exception
	 */
	public int queryProcInstTaskInstListCountBySaleOrderNo(ArtificialTaskPo po) throws Exception;

	/**
	 * 查询部门任务（tache_code,prod_code,oper_code）
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<ProcInstTaskInstPo> queryDepartTaskInstByCode(ProcInstTaskInstPo po) throws Exception;
	
	/**
	 * 查询逾期任务小于1小时
	 * */
	public int queryCountOverdueLessOneHour(ProcInstTaskInstPo procInstTaskInstPo,String startTime,String endTime,String state) throws Exception;
	
	/**
	 * 查询逾期任务1到2小时
	 * */
	public int queryCountOverdueOneToTwoHour(ProcInstTaskInstPo procInstTaskInstPo,String startTime,String endTime,String state) throws Exception;
	
	/**
	 * 查询逾期任务2到12小时
	 * */
	public int queryCountOverdueTwoToTwelveHour(ProcInstTaskInstPo procInstTaskInstPo,String startTime,String endTime,String state) throws Exception;
	
	/**
	 * 查询逾期任务12到24小时
	 * */
	public int queryCountOverdueTwelveToTwentyFourHour(ProcInstTaskInstPo procInstTaskInstPo,String startTime,String endTime,String state) throws Exception;
	
	/**
	 * 查询逾期任务大于24小时
	 * */
	public int queryCountOverdueMoreTwentyFourHour(ProcInstTaskInstPo procInstTaskInstPo,String startTime,String endTime,String state) throws Exception;
	
	/**
	 * 查询未逾期任务
	 * */
	public int queryCountNotOverdue(ProcInstTaskInstPo procInstTaskInstPo,String startTime,String endTime,String state) throws Exception;
	
	/**
	 * 查询任务总数
	 * */
	public int queryCountTaskAll(ProcInstTaskInstPo procInstTaskInstPo,String startTime,String endTime,String state) throws Exception;

	public List<ProcInstTaskInstPo> queryTaskInstByTask(ProcInstTaskInstPo po) throws Exception;

	/**
	 * 统计不同环节的任务数
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<ProcInstTaskInstPo> queryAllTaskInstGroupByTacheCode(ArtificialTaskPo po) throws Exception;

	/**
	 * 统计所有任务数
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int queryCountAllTaskInstGroupByTacheCode(ArtificialTaskPo po) throws Exception;
	
	public List<ProcInstTaskInstPo> queryTaskInstByOrderNo1(ProcInstTaskInstPo po) throws Exception;
	
	/**
	 * 查询S00017任务，状态=100，创建时间大于25天的数据
	 * */
	public List<ProcInstTaskInstPo> queryTaskInstForCancleOrder(ProcInstTaskInstPo procInstTaskInstPo) throws Exception;

}
