/*
 * Licensed to Jasig under one or more contributor license agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. Jasig licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at the following location: http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable
 * law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package com.tydic.unicom.crm;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.AccountNotFoundException;

import org.jasig.cas.authentication.AuthenticationHandler;
import org.jasig.cas.authentication.BasicCredentialMetaData;
import org.jasig.cas.authentication.Credential;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.springframework.beans.BeanUtils;

import com.tydic.unicom.crm.listener.GlobalConfig;
import com.tydic.unicom.uac.business.common.interfaces.OperRelationServDu;
import com.tydic.unicom.uac.business.common.interfaces.OperServDu;
//import com.tydic.unicom.crm.uss.service.pub.interfaces.OperServDu;
//import com.tydic.unicom.crm.uss.service.pub.interfaces.UniformInfoOperServDu;
import com.tydic.unicom.uac.business.common.interfaces.UniformInfoOperServDu;
import com.tydic.unicom.uac.business.common.vo.OperRelationVo;
import com.tydic.unicom.uac.business.common.vo.UniformInfoOperVo;

public class SSOAuthenticationHandler implements AuthenticationHandler {
    private OperServDu operServDu;
    private UniformInfoOperServDu uniformInfoOperServDu;
    private OperRelationServDu operRelationServDu;

    private String name;

    @Override
    public HandlerResult authenticate(Credential credential2) throws GeneralSecurityException, PreventedException {
        UsernamePasswordVerifyCodeCredential credential = (UsernamePasswordVerifyCodeCredential) credential2;
        UniformInfoOperVo uniformInfoOperVo = new UniformInfoOperVo();

        String province_code = (String) GlobalConfig.newInstance().get("province_code");
        uniformInfoOperVo.setOper_pwd(credential.getPassword());
        uniformInfoOperVo.setUniform_info_oper(credential.getUsername());
        uniformInfoOperVo.setUniform_oper_role_type(credential.getRoleType());
        uniformInfoOperVo.setProvince_code(province_code);
        UniformInfoOperVo vo;

        try {
            vo = uniformInfoOperServDu.findUniformLogin(uniformInfoOperVo);
            if (vo != null && vo.getError_type() == null) {
            	UniformInfoOperVo UniformInfoOperVoRedis = new UniformInfoOperVo();
                BeanUtils.copyProperties(vo, UniformInfoOperVoRedis);
                UniformInfoOperVoRedis.setJsessionid(credential.getJsessionid());
				if (credential.getRoleType().equals("99") || credential.getRoleType().equals("autoLogin")) {
                	UniformInfoOperVoRedis.setUniform_oper_role_type("1");
                } else {
                	UniformInfoOperVoRedis.setUniform_oper_role_type(credential.getRoleType());
                }
                uniformInfoOperServDu.addUniformInfoOperRedis(UniformInfoOperVoRedis);
            }

        } catch (Exception e) {
            throw new AccountNotFoundException(credential.getUsername() + " not found in backing map.");
        }

        if (null == vo) {
            throw new AccountNotFoundException(credential.getUsername() + " not found in backing map.");
        }
        if (null != vo.getError_type() && vo.getError_type().equals("0")) {
            throw new AccountNotFoundException(credential.getUsername());
        }
        if (null != vo.getError_type() && vo.getError_type().equals("1")) {
			throw new AccountNotFoundException(credential.getUsername() + "未查询到统一工号信息");
        }
        if (null != vo.getError_type() && vo.getError_type().equals("2")) {
			throw new AccountNotFoundException(credential.getUsername() + "验证密码失败");
        }
        credential.setUsername(vo.getUniform_info_oper());

        Map<String, Object> attributes = new HashMap<String, Object>();
        String operNo = "";
        OperRelationVo operRelationVo = new OperRelationVo();
        operRelationVo.setUniform_info_oper(credential.getUsername());
        List<OperRelationVo> operList = operRelationServDu.queryOperRelationByUniformInfoOper(operRelationVo);
        if (operList != null && operList.size() > 0) {
            operNo = operList.get(0).getInfo_oper();
        } else {
            operNo = "CE0744"; //查询不到,默认给一个工号回去
        }

        attributes.put("CBS", operNo);
        attributes.put("ESS", operNo);

        Map<String, Object> map = uniformInfoOperServDu.getZbInfoAgentMap(operNo);
        attributes.put("departId", map.get("departId"));
        attributes.put("eparchyCode", map.get("eparchyCode"));
        attributes.put("jsession_id", credential.getJsessionid());

        return new HandlerResult(this, new BasicCredentialMetaData(credential), new SimplePrincipal(credential.getUsername(), attributes), null);
    }

    @Override
    public boolean supports(Credential credential) {
        return true;
    }

    @Override
    public String getName() {
        return this.name != null ? this.name : getClass().getSimpleName();
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

    public OperRelationServDu getOperRelationServDu() {
        return operRelationServDu;
    }

    public void setOperRelationServDu(OperRelationServDu operRelationServDu) {
        this.operRelationServDu = operRelationServDu;
    }
}
