package com.tydic.unicom.aspect.log;

/**
 * 
 * <p></p>
 * @author heguoyong 2017年4月28日 下午4:03:46
 * @ClassName EnActionStatus
 * @Description 操作标志
 * @version V1.0
 */
public enum EnActionStatus {
	 SUCCESS("成功"),
	    FAILED("失败");

	    private String description;

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    EnActionStatus(String description) {
	        this.description = description;
	    }
}
