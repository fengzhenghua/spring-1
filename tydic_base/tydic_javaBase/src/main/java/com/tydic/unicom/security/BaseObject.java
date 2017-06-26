package com.tydic.unicom.security;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * <p></p>
 * @author heguoyong 2017年4月5日 下午4:40:56
 * @ClassName BaseObject
 * @Description 序列化对象
 * @version V1.0
 */
public abstract class BaseObject implements Serializable {
	
	private static final long serialVersionUID = -5051959739612330388L;
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, new String[0]);
	}
	
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, new String[0]);
	}
}
