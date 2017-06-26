package com.tydic.unicom.crawlerframe.exception;

/**
 */
public class NoLogonException extends Exception {

    public NoLogonException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public NoLogonException(){
        super("登陆失效，请重新登录！");
    }

    public NoLogonException(String msg){
        super(msg);
    }
}
