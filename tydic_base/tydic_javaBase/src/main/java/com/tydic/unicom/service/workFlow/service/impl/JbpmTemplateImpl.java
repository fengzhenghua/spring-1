package com.tydic.unicom.service.workFlow.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.IdentityService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.history.HistoryActivityInstance;
import org.jbpm.api.history.HistoryActivityInstanceQuery;
import org.jbpm.api.identity.Group;
import org.jbpm.api.identity.User;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.identity.impl.IdentitySessionImpl;
import org.jbpm.pvm.internal.identity.spi.IdentitySession;

import com.tydic.unicom.service.workFlow.constants.Constant;
import com.tydic.unicom.service.workFlow.po.ProgressList;
import com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate;
import com.tydic.unicom.util.DateUtil;

/**
 * Created by dell on 2014/6/19.
 */
public class JbpmTemplateImpl implements JbpmTemplate {
    private ProcessEngine processEngine;
    private DataSource dataSource;
    public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public ProcessEngine getProcessEngine() {
        return processEngine;
    }

    public void setProcessEngine(ProcessEngine processEngine) {
        this.processEngine = processEngine;
    }

    /**
     * 
     * @author dell 2014-8-6 下午6:17:00
     * @Method: deployByPath 
     * @Description: TODO 依据xx.jpbl.xml的文件路径发布流程定义
     * @param classPath xx.jpbl.xml的文件路径
     * @return deploymentId 发布后的流程定义ID
     * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#deployByPath(java.lang.String)
     */
    public String deployByPath(String classPath){
        String deploymentId = processEngine.getRepositoryService().createDeployment()
                .addResourceFromClasspath(classPath).deploy();

        return deploymentId;
    }

    /**
     * 
     * @author dell 2014-8-8 下午2:58:34
     * @Method: getSuspenedProcessDefinitions 
     * @Description: TODO 按降序排列缓慢查询发布流程定义列表
     * @param pdKey 流程定义关键字
     * @return 返回发布流程定义列表
     * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#getSuspenedProcessDefinitions(java.lang.String)
     */
    public List<ProcessDefinition> getSuspenedProcessDefinitions(String pdKey) {
        RepositoryService repositoryService = processEngine
                .getRepositoryService();
        
        return repositoryService.createProcessDefinitionQuery().processDefinitionKey(pdKey).orderAsc(ProcessDefinitionQuery.PROPERTY_NAME).list();
    }

    /**
     * 
     * @author dell 2014-8-6 下午6:17:36
     * @Method: removeProcessDefinitionByDeployId 
     * @Description: TODO 按流程定义ID级联删除发布流程定义及其下具体信息
     * @param deploymentId 要删除的流程定义ID
     * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#removeProcessDefinitionByDeployId(java.lang.String)
     */
    public void removeProcessDefinitionByDeployId(String deploymentId) {
        RepositoryService repositoryService = processEngine
                .getRepositoryService();

        repositoryService.deleteDeploymentCascade(deploymentId);
    }

    /**
     * 
     * @author dell 2014-8-6 下午6:17:57
     * @Method: startProcessByKey 
     * @Description: TODO 启动流程实例
     * @param pdKey 流程定义key
     * @return Map<String,Object> 流程实例返回信息，目前包括成功标识、状态等
     * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#startProcessByKey(java.lang.String)
     */
    public Map<String,Object> startProcessByKey(String pdKey) {
    	
    	String sysDate = DateUtil.getSysDateString("yyyyMMddHH24mmss");
        Map<String,Object> vars = new HashMap<String,Object>();
        vars.put("sys_date", sysDate);
        //ExecutionService executionService = processEngine.getExecutionService();
        return startProcessByKey(pdKey,vars);
    }
    
    /**
     * 
     * @author dell 2014-8-6 下午6:18:16
     * @Method: startProcessByKey 
     * @Description: TODO 启动流程实例(流程定义key+流程实例key,可组合成流程实例ID)
     * @param pdKey 流程定义key
     * @param piKey 可以用订单id作为此字段传入，这样最终生成的流程实例ID为pdKey.piKey的形式
     * @return Map<String,Object> 流程实例返回信息，目前包括成功标识、状态等
     * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#startProcessByKey(java.lang.String, java.lang.String)
     */
    public Map<String,Object> startProcessByKey(String pdKey,String piKey){
    	String sysDate = DateUtil.getSysDateString("yyyyMMddHH24mmss");
        Map<String,Object> vars = new HashMap<String,Object>();
        vars.put("sys_date", sysDate);
        //ExecutionService executionService = processEngine.getExecutionService();
        return startProcessByKey(pdKey,vars,piKey);
    }
    
    /**
     * 
     * @author dell 2014-8-6 下午6:18:42
     * @Method: startProcessByKey 
     * @Description: TODO 启动流程实例(可设置流程实例中需用到的流程变量)
     * @param pdKey 流程定义key
     * @param variables 流程实例中需用到的流程变量
     * @return Map<String,Object> 流程实例返回信息，目前包括成功标识、状态等
     * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#startProcessByKey(java.lang.String, java.util.Map)
     */
    public Map<String,Object> startProcessByKey(String pdKey,Map<String,Object> variables) {
        ExecutionService executionService = processEngine.getExecutionService();
        ProcessInstance processInstance = executionService.startProcessInstanceByKey(pdKey,variables);
        Map<String,Object> returnInfo = new HashMap<String,Object>();
    	returnInfo.put("ORDER_STATUS", processInstance.findActiveActivityNames().iterator().next());
    	returnInfo.put(Constant.WF_FLAG, Constant.SUCCESS_FLAG);
    	returnInfo.put("sys_date", variables.get("sys_date"));
        return returnInfo;
    }
    
    /**
     * 
     * @author dell 2014-8-6 下午6:19:05
     * @Method: startProcessByKey 
     * @Description: TODO 启动流程实例(流程定义key+流程实例key,可组合成流程实例ID;并可设置流程实例中需用到的流程变量)
     * @param pdKey 流程定义key
     * @param variables 流程实例中需用到的流程变量
     * @param piKey 可以用订单id作为此字段传入，这样最终生成的流程实例ID为pdKey.piKey的形式
     * @return 流程实例返回信息，目前包括成功标识、状态等
     * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#startProcessByKey(java.lang.String, java.util.Map, java.lang.String)
     */
    public Map<String,Object> startProcessByKey(String pdKey,Map<String,Object> variables,String piKey){
    	ExecutionService executionService = processEngine.getExecutionService();
    	
    	ProcessInstance findProcessInstance = executionService.findProcessInstanceById(pdKey+"."+piKey);
    	if(null!=findProcessInstance){
    		throw new RuntimeException("Workflow:"+pdKey+"."+piKey+" is started");
    	}
    	ProcessInstance processInstance =  executionService.startProcessInstanceByKey(pdKey, variables, piKey);
    	Map<String,Object> returnInfo = new HashMap<String,Object>();
    	returnInfo.put("ORDER_STATUS", processInstance.findActiveActivityNames().iterator().next());
    	returnInfo.put(Constant.WF_FLAG, Constant.SUCCESS_FLAG);
    	returnInfo.put("sys_date", variables.get("sys_date"));
    	return returnInfo;
    }
    
    /**
     * 
     * @author dell 2014-8-6 下午6:19:43
     * @Method: signalProcessById 
     * @Description: TODO 执行等待的流程实例
     * @param procInstanceId 流程实例ID
     * @return Map<String,Object> 流程实例返回信息，目前包括成功标识、状态等
     * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#signalProcessById(java.lang.String)
     */
    public Map<String,Object> signalProcessById(String procInstanceId) {
        /*RepositoryService repositoryService = processEngine
                .getRepositoryService();*/
        ExecutionService executionService = processEngine.getExecutionService();
        HistoryService historyService = processEngine.getHistoryService();
        ProcessInstance processInstance = executionService.findProcessInstanceById(procInstanceId);

//        System.out.println("processInstance.getState()======="+processInstance.getState());
//        Map<String,Object> returnInfo = new HashMap<String,Object>();
//    	returnInfo.put("ORDER_STATUS", processInstance.findActiveActivityNames().iterator().next());
//    	returnInfo.put(Constant.WF_FLAG, Constant.SUCCESS_FLAG);
//    	String sysDate = DateUtil.getSysDateString("yyyyMMddHH24mmss");
//    	returnInfo.put("sys_date", sysDate);
    	
		String orderStatus = null;
		if(processInstance!=null){
			ProcessInstance signalProcessInstance = executionService.signalExecutionById(procInstanceId);
			if(signalProcessInstance.isEnded()){
				orderStatus = signalProcessInstance.getState();
			}else{
				orderStatus = signalProcessInstance.findActiveActivityNames().iterator().next();
			}
			System.out.println("processInstance:orderStatus="+orderStatus);
		}else{
			System.out.println("processInstance is null");
			orderStatus = historyService.createHistoryProcessInstanceQuery().processInstanceId(procInstanceId).uniqueResult().getState();
		}
		Map<String,Object> returnInfo = new HashMap<String,Object>();
    	returnInfo.put("ORDER_STATUS", orderStatus);
    	returnInfo.put(Constant.WF_FLAG, Constant.SUCCESS_FLAG);
    	String sysDate = DateUtil.getSysDateString("yyyyMMddHH24mmss");
    	returnInfo.put("sys_date", sysDate);
    	//modified by haolong 修正：流程执行后如果为结束节点，map为空
//        Map<String,Object> returnInfo = new HashMap<String,Object>();
//        returnInfo=this.getProcessStatus(procInstanceId);
        return returnInfo;
    }
    
    /**
     * @Description: TODO 执行等待的流程实例   （流程实例ID与流转条件参数(String)联合确定流向）
     * @param procInstanceId
     * @param str
     * @return
     * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#signalProcessByIdAndStr(java.lang.String, java.lang.String)
     */
    public Map<String,Object> signalProcessByIdAndStr(String procInstanceId,String str){
    	ProcessInstance processInstance ;
    	ExecutionService executionService = processEngine.getExecutionService();
    	HistoryService historyService = processEngine.getHistoryService();   	
    	processInstance = executionService.findProcessInstanceById(procInstanceId);
    	String choiceStatus = null;
    	if(processInstance != null){
    		int Name = processInstance.findActiveActivityNames().iterator().next().length();
        	if((str != null && !"".equals(str.trim())) && Name == 1){
        		processInstance = executionService.signalExecutionById(procInstanceId,str.trim());
        		if(processInstance.isEnded()){
        			choiceStatus = processInstance.getState();
        		}else {
        			choiceStatus = processInstance.findActiveActivityNames().iterator().next();
        		}
        	}else {
        		processInstance = executionService.signalExecutionById(procInstanceId);
        		if(processInstance.isEnded()){
        			choiceStatus = processInstance.getState();
        		}else {
        			choiceStatus = processInstance.findActiveActivityNames().iterator().next();
        		}
        	}       	
    	}else {
    		choiceStatus = historyService.createHistoryProcessInstanceQuery().processInstanceId(procInstanceId).uniqueResult().getState();
    	}
    	
    	
        Map<String,Object> returnInfo = new HashMap<String,Object>();
     	returnInfo.put("ORDER_STATUS", choiceStatus);
     	returnInfo.put(Constant.WF_FLAG, Constant.SUCCESS_FLAG);
     	String sysDate = DateUtil.getSysDateString("yyyyMMddHH24mmss");
     	returnInfo.put("sys_date", sysDate);
        return returnInfo;
    }
    
    /**
     * 
     * @author dell 2014-8-6 下午6:20:04
     * @Method: signalProcessByIdAndParam 
     * @Description: TODO 执行等待的流程实例（流程实例ID与流转条件参数联合确定流向）
     * @param procInstanceId 流程实例ID
     * @param parameters 流转条件参数
     * @return Map<String,Object> 流程实例返回信息，目前包括成功标识、状态等
     * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#signalProcessByIdAndParam(java.lang.String, java.util.Map)
     */
    public Map<String,Object> signalProcessByIdAndParam(String procInstanceId,Map<String, Object> parameters) {
        ExecutionService executionService = processEngine.getExecutionService();
        ProcessInstance processInstance = executionService.signalExecutionById(procInstanceId,parameters);
        Map<String,Object> returnInfo = new HashMap<String,Object>();
    	returnInfo.put("ORDER_STATUS", processInstance.findActiveActivityNames().iterator().next());
    	returnInfo.put(Constant.WF_FLAG, Constant.SUCCESS_FLAG);
    	String sysDate = DateUtil.getSysDateString("yyyyMMddHH24mmss");
    	returnInfo.put("sys_date", sysDate);
        return returnInfo;
    }

    /**
     * 
     * @author dell 2014-8-6 下午6:20:28
     * @Method: endProcessInstance 
     * @Description: TODO 终结流程实例
     * @param procInstanceId 流程实例ID
     * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#endProcessInstance(java.lang.String)
     */
    public void endProcessInstance(String procInstanceId) {
        ExecutionService executionService = processEngine.getExecutionService();

        executionService.endProcessInstance(procInstanceId, Execution.STATE_ENDED);
    }

    /**
     * 
     * @author dell 2014-8-6 下午6:20:42
     * @Method: removeProcessInstance 
     * @Description: TODO 删除流程实例
     * @param procInstanceId 流程实例ID
     * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#removeProcessInstance(java.lang.String)
     */
    public void removeProcessInstance(String procInstanceId) {
        ExecutionService executionService = processEngine.getExecutionService();

        executionService.deleteProcessInstance(procInstanceId);
    }

    /**
     * 
     * @author dell 2014-8-6 下午6:20:54
     * @Method: getProcessInstances 
     * @Description: TODO 查询已启动的流程实例
     * @param pdKey 流程定义key
     * @param piKey 可以用订单id作为此字段传入，这样最终生成的流程实例ID为pdKey.piKey的形式
     * @return List<ProcessInstance> 流程实例列表
     * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#getProcessInstances(java.lang.String, java.lang.String)
     */
    public List<ProcessInstance> getProcessInstances(String pdKey,String piKey) {
        ExecutionService executionService = processEngine.getExecutionService();
        
        return executionService.createProcessInstanceQuery()
        		.processInstanceId(pdKey+"."+piKey).list();
    }

    /**
     * 
     * @author dell 2014-8-6 下午6:21:17
     * @Method: getPersonalTasks 
     * @Description: TODO 依据待处理人名称查询个人代办任务
     * @param userName 任务处理人名称
     * @return List<Task> 代办任务列表
     * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#getPersonalTasks(java.lang.String)
     */
    public List<Task> getPersonalTasks(String userName) {
        TaskService taskService = processEngine.getTaskService();
        
        return taskService.findPersonalTasks(userName);
    }

    /**
     * 
     * @author dell 2014-8-6 下午6:21:34
     * @Method: getTask 
     * @Description: TODO 依据任务id查询对应任务信息
     * @param taskId 任务ID
     * @return Task 具体任务
     * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#getTask(java.lang.String)
     */
    public Task getTask(String taskId) {
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.getTask(taskId);
        return task;
    }

    /**
     * 
     * @author dell 2014-8-6 下午6:22:00
     * @Method: getTasks
     * @Description: TODO 查询所有代办任务
     * @return List<Task> 代办任务列表
     * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#getTasks()
     */
    public List<Task> getTasks() {
        TaskService taskService = processEngine.getTaskService();

        return taskService.createTaskQuery().list();
    }
    
    /**
     * 
     * @author dell 2014-8-6 下午6:22:27
     * @Method: getTaskIdByExecId 
     * @Description: TODO 依据流程实例ID查询该流程实例当前代办任务
     * @param procInstanceId 流程实例ID
     * @return String 代办任务ID
     * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#getTaskIdByExecId(java.lang.String)
     */
    public String getTaskIdByExecId(String procInstanceId){
    	String taskId = processEngine.getTaskService().createTaskQuery().executionId(procInstanceId).list().get(0).getId();
    	return taskId;
    }
    
    /**
     * 
     * @author dell 2014-8-6 下午6:22:47
     * @Method: completeTask 
     * @Description: TODO 代办任务完成（区分处理分支动作）
     * @param taskId 代办任务ID
     * @param actName 处理分支动作类型(如：申请、驳回等)
     * @param variables 任务申请内容，已map的形式存储
     * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#completeTask(java.lang.String, java.lang.String, java.util.Map)
     */
    public void completeTask(String taskId, String actName, Map<String, Object> variables) {
        TaskService taskService = processEngine.getTaskService();
        taskService.setVariables(taskId, variables);
        taskService.completeTask(taskId, actName);
    }

    /**
     * 
     * @author dell 2014-8-6 下午6:23:12
     * @Method: completeTaskDefault 
     * @Description: TODO 代办任务完成(默认)
     * @param taskId 代办任务ID
     * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#completeTaskDefault(java.lang.String)
     */
    public void completeTaskDefault(String taskId) {
        TaskService taskService = processEngine.getTaskService();
        taskService.completeTask(taskId);
    }
    
    /**
     * 
     * @author dell 2014-8-6 下午6:23:27
     * @Method: getVariableForTask 
     * @Description: TODO 依据任务ID及key获取任务内容变量
     * @param taskId 代办任务ID
     * @param varName 任务内容变量key
     * @return Object 返回任务内容
     * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#getVariableForTask(java.lang.String, java.lang.String)
     */
    public Object getVariableForTask(String taskId,String varName) {
        TaskService taskService = processEngine.getTaskService();
        return taskService.getVariable(taskId, varName);
    }

    /**
     * 
     * @author dell 2014-8-6 下午6:23:52
     * @Method: getVariablesForTask 
     * @Description: TODO 依据任务ID获取返回所有任务内容
     * @param taskId 代办任务ID
     * @return Map<String,Object> 返回任务内容
     * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#getVariablesForTask(java.lang.String)
     */
    public Map<String, Object> getVariablesForTask(String taskId) {
        TaskService taskService = processEngine.getTaskService();
        Set<String> names = taskService.getVariableNames(taskId);

        return taskService.getVariables(taskId, names);
    }

    /**
     * 
     * @author dell 2014-8-6 下午6:24:12
     * @Method: getVariablesForProcess 
     * @Description: TODO 依据流程实例ID获取返回所有流程实例内容
     * @param procInstanceId 流程实例ID
     * @return Map<String,Object> 返回流程实例内容
     * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#getVariablesForProcess(java.lang.String)
     */
    public Map<String, Object> getVariablesForProcess(String procInstanceId) {
        ExecutionService executionService = processEngine.getExecutionService();
        Set<String> names = executionService.getVariableNames(procInstanceId);

        return executionService.getVariables(procInstanceId, names);
    }
    
    /**
     * 
     * @author dell 2014-8-6 下午6:24:31
     * @Method: getTaskUsers 
     * @Description: TODO 获取流程实例任务处理候选人列表
     * @return List<User> 返回任务处理候选人信息列表
     * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#getTaskUsers()
     */
    public List<User> getTaskUsers() {
		IdentityService identityService = processEngine.getIdentityService();

		return identityService.findUsers();
	}

    /**
     * 
     * @author dell 2014-8-6 下午6:24:47
     * @Method: getTaskGroupsByUser 
     * @Description: TODO 依据任务处理候选人ID获取流程实例任务候选人组信息列表
     * @param userId 用户ID
     * @return List<Group> 返回任务处理候选人组信息列表
     * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#getTaskGroupsByUser(java.lang.String)
     */
	public List<Group> getTaskGroupsByUser(String userId) {
		IdentityService identityService = processEngine.getIdentityService();

		return identityService.findGroupsByUser(userId);
	}

	/**
	 * 
	 * @author dell 2014-8-6 下午6:25:14
	 * @Method: addTaskUser 
	 * @Description: TODO 添加任务处理候选人信息
	 * @param userId 用户ID
	 * @param givenName 名字
	 * @param familyName 姓氏
	 * @return boolean 添加任务处理候选人信息是否成功标识
	 * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#addTaskUser(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean addTaskUser(String userId, String givenName,
			String familyName) {
		boolean isSuccess = false;
		IdentityService identityService = processEngine.getIdentityService();
		if (identityService.findUserById(userId) == null) {
			identityService.createUser(userId, givenName, familyName);
			isSuccess = true;
		}
		return isSuccess;
	}

	/**
	 * 
	 * @author dell 2014-8-6 下午6:25:39
	 * @Method: removeTaskUser 
	 * @Description: TODO 删除任务处理候选人信息
	 * @param userId 用户ID
	 * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#removeTaskUser(java.lang.String)
	 */
	public void removeTaskUser(String userId) {
		IdentityService identityService = processEngine.getIdentityService();
		identityService.deleteUser(userId);
	}

	/**
	 * 
	 * @author dell 2014-8-6 下午6:25:51
	 * @Method: getTaskGroups 
	 * @Description: TODO 获取流程实例任务处理候选人组列表
	 * @return List<Group> 返回任务分配组信息列表
	 * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#getTaskGroups()
	 */
	public List<Group> getTaskGroups() {
		EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
		EnvironmentImpl env = environmentFactory.openEnvironment();

		try {
			IdentitySession identitySession = env.get(IdentitySession.class);

			return ((IdentitySessionImpl) identitySession).findGroups();
		} finally {
			env.close();
		}
	}

	/**
	 * 
	 * @author dell 2014-8-6 下午6:26:06
	 * @Method: addTaskGroup 
	 * @Description: TODO 添加任务处理候选人组信息
	 * @param name 组名
	 * @param type 组类型
	 * @param parentGroupId 父组ID
	 * @return boolean 添加任务处理候选人组信息是否成功标识
	 * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#addTaskGroup(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean addTaskGroup(String name, String type, String parentGroupId) {
		boolean success = false;
		IdentityService identityService = processEngine.getIdentityService();
		if (identityService.findGroupById(type + "." + name) == null) {
			identityService.createGroup(name, type, parentGroupId);
			success = true;
		}
		return success;
	}

	/**
	 * 
	 * @author dell 2014-8-6 下午6:28:07
	 * @Method: removeTaskGroup 
	 * @Description: TODO 删除任务处理候选人组信息
	 * @param groupId 组ID
	 * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#removeTaskGroup(java.lang.String)
	 */
	public void removeTaskGroup(String groupId) {
		IdentityService identityService = processEngine.getIdentityService();
		identityService.deleteGroup(groupId);
	}

	/**
	 * 
	 * @author dell 2014-8-6 下午6:27:45
	 * @Method: addTaskMemberShip 
	 * @Description: TODO 添加任务处理候选人组及成员关系信息
	 * @param userId 用户ID
	 * @param groupId 组ID
	 * @param role 组与成员间关系
	 * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#addTaskMemberShip(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void addTaskMemberShip(String userId, String groupId, String role) {
		IdentityService identityService = processEngine.getIdentityService();
		String groupType = identityService.findGroupById(groupId).getType();
		List<Group> groups = identityService.findGroupsByUserAndGroupType(
				userId, groupType);
		if (groups.size() == 0) {
			identityService.createMembership(userId, groupId, role);
		}
	}

	/**
	 * 
	 * @author dell 2014-8-6 下午6:27:14
	 * @Method: removeTaskMemberShip 
	 * @Description: TODO 删除任务处理候选人组及成员关系信息
	 * @param userId 用户ID
	 * @param groupId 组ID
	 * @param role 组与成员间关系
	 * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#removeTaskMemberShip(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void removeTaskMemberShip(String userId, String groupId, String role) {
		IdentityService identityService = processEngine.getIdentityService();
		identityService.deleteMembership(userId, groupId, role);
	}
	
	/**
	 * 
	 * @author dell 2014-8-6 下午6:26:58
	 * @Method: getProcessStatus 
	 * @Description: TODO 查询获取当前流程实例步骤
	 * @param procInstanceId 流程实例ID
	 * @return Map<String,Object> 主要返回当前步骤的订单状态，成功标识、系统时间等
	 * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#getProcessStatus(java.lang.String)
	 */
	public Map<String,Object> getProcessStatus(String procInstanceId){
		ExecutionService executionService = processEngine.getExecutionService();
		HistoryService historyService = processEngine.getHistoryService();
		String orderStatus = null;
		ProcessInstance processInstance = executionService.findProcessInstanceById(procInstanceId);
		if(processInstance!=null){
			orderStatus = processInstance.findActiveActivityNames().iterator().next();
			System.out.println("processInstance:orderStatus="+orderStatus);
		}else{
			System.out.println("processInstance is null");
			orderStatus = historyService.createHistoryProcessInstanceQuery().processInstanceId(procInstanceId).uniqueResult().getState();
		}
		Map<String,Object> returnInfo = new HashMap<String,Object>();
    	returnInfo.put("ORDER_STATUS", orderStatus);
    	returnInfo.put(Constant.WF_FLAG, Constant.SUCCESS_FLAG);
    	String sysDate = DateUtil.getSysDateString("yyyyMMddHH24mmss");
    	returnInfo.put("sys_date", sysDate);
        return returnInfo;
	}
	
	/**
	 * 
	 * @author dell 2014-8-6 下午6:26:40
	 * @Method: getHistActinst 
	 * @Description: TODO 获取工单已完成和正在进行的状态动作
	 * @param procInstanceId 流程实例ID
	 * @return List<ProgressList> 主要返回当前步骤的订单状态，当前和已完成状态标识、状态起始时间等
	 * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#getHistActinst(java.lang.String)
	 */
	public List<ProgressList> getHistActinst(String procInstanceId){
		HistoryService historyService = processEngine.getHistoryService();
		List<HistoryActivityInstance> hisActInstList = historyService.createHistoryActivityInstanceQuery().processInstanceId(procInstanceId).orderAsc(HistoryActivityInstanceQuery.PROPERTY_STARTTIME).list();		
		List<ProgressList> progressLists = new ArrayList<ProgressList>();
		for(HistoryActivityInstance hisActInst:hisActInstList){
			ProgressList progressList = new ProgressList();
			progressList.setProc_ins_id(hisActInst.getExecutionId());
			progressList.setOrder_status(hisActInst.getActivityName());
			//String startDate = DateUtil.chngDateString(hisActInst.getStartTime(),DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
			progressList.setStart_date(hisActInst.getStartTime());
			if(hisActInst.getEndTime() != null){
				progressList.setNow_proc_flag("old");
			}else{
				progressList.setNow_proc_flag("now");
			}
			progressLists.add(progressList);
		}
		return progressLists;
	}
	
	/**
	 * 
	 * @author dell 2014-8-6 下午5:58:28
	 * @Method: takeTaskForOper 
	 * @Description: TODO 任务接收(工单分组后，组中用户接收任务使用)
	 * @param taskId 任务ID
	 * @param operNo 操作员编号
	 * @return 
	 * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#takeTaskForOper(java.lang.String, java.lang.String)
	 */
	@Override
    public void takeTaskForOper(String taskId, String operNo) {
		TaskService taskService = processEngine.getTaskService();
		taskService.takeTask(taskId, operNo);
    }

	/**
	 * 
	 * @author yangwen 2014-8-19 上午11:09:20
	 * @Method: getProcInstancesByStatus 
	 * @Description: TODO 根据某一流程定义的ID,与对应状态获得相应流程实例列表
	 * @param pdId 流程定义ID(关键字+版本号的形式)
	 * @param status 具体状态
	 * @return List<ProcessInstance> 返回某一状态的流程实例列表
	 * @see com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate#getProcInstancesByStatus(java.lang.String, java.lang.String)
	 */
	@Override
    public List<ProcessInstance> getProcInstancesByStatus(String pdId, String status) {
		ExecutionService executionService = processEngine.getExecutionService();
		List<ProcessInstance> processInstanceList = new ArrayList<ProcessInstance>();
		List<ProcessInstance> allProcessInstances = executionService.createProcessInstanceQuery().processDefinitionId(pdId).list();
		for(ProcessInstance processInstance:allProcessInstances){
			if(status!=null&&status.equals(processInstance.findActiveActivityNames().iterator().next())){
				processInstanceList.add(processInstance);
			}
		}
	    return processInstanceList;
    }

}

