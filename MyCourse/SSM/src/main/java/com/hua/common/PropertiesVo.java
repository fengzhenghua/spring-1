package com.hua.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by fengzhenghua on
 * 2017-08-21 18:38.
 */
@Component("propertiesVo")
public class PropertiesVo {

    @Value("${test.string}")
    private String testString;

    public String getTestString() {
        return testString;
    }
}
