/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.tydic.unicom.crm;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jasig.cas.authentication.Credential;
/**
 * Credential for authenticating with a username and password.
 *
 * @author Scott Battaglia
 * @author Marvin S. Addison
 * @since 3.0
 */

public class UsernamePasswordVerifyCodeCredential implements Credential, Serializable{
	/** Unique ID for serialization. */
    private static final long serialVersionUID = -700605081472810939L;

    /** Password suffix appended to username in string representation. */
    private static final String PASSWORD_SUFFIX = "+password";

    /** The username. */
    @NotNull
    @Size(min=1, message = "required.username")
    private String username;

    /** The password. */
    @NotNull
    @Size(min=1, message = "required.password")
    private String password;
    
    /** The verifyCode. */
    @NotNull
    @Size(min=1, message = "required.verifyCode")
    private String verifyCode;
    @NotNull
    @Size(min=1, message = "required.jsessionid")
    private String jsessionid;
    
    @NotNull
    @Size(min=1, message = "required.roleType")
    private String roleType;

    /** Default constructor. */
    public UsernamePasswordVerifyCodeCredential() {}

    /**
     * Creates a new instance with the given username and password.
     *
     * @param userName Non-null user name.
     * @param password Non-null password.
     */
    public UsernamePasswordVerifyCodeCredential(final String userName, final String password,final String verifyCode,final String jsessionid) {
        this.username = userName;
        this.password = password;
        this.verifyCode = verifyCode;
        this.jsessionid = jsessionid;
    }

    public UsernamePasswordVerifyCodeCredential(final String userName, final String password,final String verifyCode,final String jsessionid,final String roleType) {
        this.username = userName;
        this.password = password;
        this.verifyCode = verifyCode;
        this.jsessionid = jsessionid;
        this.roleType=roleType;
    }

    
    public final String getJsessionid() {
    	return jsessionid;
    }

	
    public void setJsessionid(final String jsessionid) {
    	this.jsessionid = jsessionid;
    }

	/**
     * @return Returns the verifyCode.
     */
    public final String getVerifyCode() {
    	return verifyCode;
    }

    /**
     * @param verifyCode The verifyCode to set.
     */
    public final void setVerifyCode(final String verifyCode) {
    	this.verifyCode = verifyCode;
    }

	/**
     * @return Returns the password.
     */
    public final String getPassword() {
        return this.password;
    }

    /**
     * @param password The password to set.
     */
    public final void setPassword(final String password) {
        this.password = password;
    }

    /**
     * @return Returns the userName.
     */
    public final String getUsername() {
        return this.username;
    }

    /**
     * @param userName The userName to set.
     */
    public final void setUsername(final String userName) {
        this.username = userName;
    }

    /** {@inheritDoc} */
    @Override
    public String getId() {
        return this.username;
    }

    @Override
    public String toString() {
        return this.username + PASSWORD_SUFFIX;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UsernamePasswordVerifyCodeCredential that = (UsernamePasswordVerifyCodeCredential) o;

        if (password != null ? !password.equals(that.password) : that.password != null) {
            return false;
        }

        if (username != null ? !username.equals(that.username) : that.username != null) {
            return false;
        }
        
        if (verifyCode != null ? !verifyCode.equals(that.verifyCode) : that.verifyCode != null) {
            return false;
        }
        
        if (jsessionid != null ? !jsessionid.equals(that.jsessionid) : that.jsessionid != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (verifyCode != null ? verifyCode.hashCode() : 0);
        result = 31 * result + (jsessionid != null ? jsessionid.hashCode() : 0);
        
        return result;
    }

	
    public String getRoleType() {
    	return roleType;
    }

	
    public void setRoleType(String roleType) {
    	this.roleType = roleType;
    }
}
