package com.tydic.unicom.service.workFlow;

import java.util.List;

import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tydic.unicom.service.workFlow.service.interfaces.JbpmTemplate;


public class DubboStart {
	public static void main(String[] args) throws InterruptedException
	{
		System.out.println("workflow test begin");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "config/spring/jbpmBeans.xml" });
		System.out.println("workflow test 1 ");
		context.start();
		System.out.println("workflow test 2 ");
		JbpmTemplate jbpmTemplate = (JbpmTemplate)context.getBean("jbpmTemplate");

		//String deploymentId = jbpmTemplate.deployByPath("jbpl/testJbpm1.jpdl.xml");
		//System.out.println("deploymentId="+deploymentId);

		System.out.println("workflow test 2.1 ");
		String piKey = "20001";
		String pdKey = "testState";
		System.out.println("pdKey="+pdKey);
		//				jbpmTemplate.removeProcessDefinitionByDeployId(dId);
		System.out.println("-1-pdKey.key="+pdKey+"."+piKey);
		//jbpmTemplate.startProcessByKey(pdKey, piKey);
		//jbpmTemplate.signalProcessById(pdKey+"."+piKey);
		List<ProcessInstance> processInstanceList = jbpmTemplate.getProcessInstances(pdKey,piKey);
		if(processInstanceList!=null){
			for(ProcessInstance p:processInstanceList){
				System.out.println("ProcessInstanceId="+p.getId());
				System.out.println("ProcessInstanceKey="+p.getKey());
				System.out.println("ProcessInstanceName="+p.getName());
				System.out.println("ProcessInstanceState="+p.getState());
				System.out.println("activityNamesSize="+p.findActiveActivityNames().size());
				System.out.println("activityNamesString="+p.findActiveActivityNames().iterator().next());
				
				
				System.out.println("executionsSize="+p.getExecutions().size());
				//jbpmTemplate.removeProcessInstance(p.getId());

			}
		}

		System.out.println("workflow test 3 ");

		while (true) {
			Thread.sleep(1000000l);
		}		
	}
}
