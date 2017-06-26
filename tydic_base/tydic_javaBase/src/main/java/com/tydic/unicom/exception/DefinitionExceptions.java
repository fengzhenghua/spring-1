package com.tydic.unicom.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年4月5日 下午3:22:16
 * @ClassName Exceptions
 * @Description 定义异常
 * @version V1.0
 */
public class DefinitionExceptions {
	
	public static RuntimeException unchecked(Throwable ex) {
		if ((ex instanceof RuntimeException)) {
			return (RuntimeException)ex;
		}
		return new RuntimeException(ex);
	}
	
	public static String getStackTraceAsString(Throwable ex) {
		StringWriter stringWriter = new StringWriter();
		ex.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}
	
	public static String getErrorMessageWithNestedException(Throwable ex) {
		Throwable nestedException = ex.getCause();
		
		return ex.getMessage() + " nested exception is " + nestedException.getClass().getName() + ":"
		        + nestedException.getMessage();
	}
	
	public static Throwable getRootCause(Throwable ex) {
		Throwable cause;
		while ((cause = ex.getCause()) != null) {
			ex = cause;
		}
		return ex;
	}
	
	public static boolean isCausedBy(Exception ex, Class<? extends Exception>[] causeExceptionClasses) {
		Throwable cause = ex;
		while (cause != null) {
			for (@SuppressWarnings("rawtypes")
			Class causeClass : causeExceptionClasses) {
				if (causeClass.isInstance(cause)) {
					return true;
				}
			}
			cause = cause.getCause();
		}
		return false;
	}
}
