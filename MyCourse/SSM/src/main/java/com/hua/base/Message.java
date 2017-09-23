package com.hua.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fengzhenghua on
 * 2017-08-19 19:22.
 */
public class Message implements Serializable{


    private static final long serialVersionUID = -4350825920030252213L;
    private String Content;
    private String respCode;
    private Map<String,Object> args = new HashMap<String,Object>();

    public Message(){}
    public Message(String content, String respCode) {
        Content = content;
        this.respCode = respCode;
    }

    public Message(String content, String respCode, Map<String, Object> args) {
        Content = content;
        this.respCode = respCode;
        this.args = args;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public void addArgs(String key,Object obj){
        if(args==null){
            args = new HashMap<String,Object>();
        }
        args.put(key,obj);
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public void setArgs(Map<String, Object> args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "Message{" +
                "Content='" + Content + '\'' +
                ", respCode='" + respCode + '\'' +
                ", args=" + String.valueOf(args) +
                '}';
    }
}
