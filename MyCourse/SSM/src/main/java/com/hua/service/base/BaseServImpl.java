package com.hua.service.base;

import com.hua.base.BasePo;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * Created by fengzhenghua on
 * 2017-08-28 23:15.
 */
public class BaseServImpl<T extends BasePo> extends SqlSessionDaoSupport{

    public int getLists(T t){
        return 1;
    }
}
