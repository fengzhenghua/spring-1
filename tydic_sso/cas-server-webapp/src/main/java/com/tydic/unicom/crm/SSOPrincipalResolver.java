/*
 * Licensed to Jasig under one or more contributor license agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. Jasig licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at the following location: http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable
 * law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package com.tydic.unicom.crm;

import java.util.HashMap;
import java.util.Map;

import org.jasig.cas.authentication.Credential;
import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.authentication.principal.PrincipalResolver;
import org.jasig.cas.authentication.principal.SimplePrincipal;

import com.tydic.unicom.uac.business.common.interfaces.OperServDu;
import com.tydic.unicom.uac.business.common.interfaces.UniformInfoOperServDu;

//import com.tydic.unicom.crm.uss.service.pub.interfaces.OperServDu;
//import com.tydic.unicom.crm.uss.service.pub.interfaces.UniformInfoOperServDu;

public class SSOPrincipalResolver implements PrincipalResolver {
	
	private OperServDu operServDu;
	private UniformInfoOperServDu uniformInfoOperServDu;
	
	@Override
	public Principal resolve(Credential credential2) {
		UsernamePasswordVerifyCodeCredential credential = (UsernamePasswordVerifyCodeCredential)credential2;
		// InfoOper infoOper= operServ.loginIn(credential.getUsername(), credential.getPassword());
		// Map attributes = new HashMap();
		// attributes.put("infoOper", infoOper);
		//return new SimplePrincipal(credential.getUsername());
		
		return new SimplePrincipal(credential.getJsessionid());

	}
	
	@Override
	public boolean supports(Credential credential) {
		return true;
	}
	
	public OperServDu getOperServDu() {
		return operServDu;
	}
	
	public void setOperServDu(OperServDu operServDu) {
		this.operServDu = operServDu;
	}
	
	public UniformInfoOperServDu getUniformInfoOperServDu() {
		return uniformInfoOperServDu;
	}
	
	public void setUniformInfoOperServDu(UniformInfoOperServDu uniformInfoOperServDu) {
		this.uniformInfoOperServDu = uniformInfoOperServDu;
	}
	
}
