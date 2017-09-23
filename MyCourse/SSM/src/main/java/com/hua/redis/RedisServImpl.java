package com.hua.redis;

import com.hua.base.BasePo;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by fengzhenghua on
 * 2017-08-28 22:25.
 */
public class RedisServImpl implements RedisServ{

    private String host;         //主机
    private String requirepass;  //密码
    private int port;            //端口号
    private int maxTotal;        //最大连接数
    private int maxIdle;         //最大连接空闲数
    private int timeout;         //连接超时限制

    /**
     * 建立连接,获取jedis对象
     *
     */

     private Jedis getJedisConnect(){
         //redis连接池配置
         JedisPoolConfig poolConfig = new JedisPoolConfig();
         //设置最大连接数
         poolConfig.setMaxTotal(maxTotal);
         //设置最大空闲连接数
         poolConfig.setMaxIdle(maxIdle);
         //Jedis连接池
         JedisPool jedisPool = new JedisPool(poolConfig,host,port,timeout,requirepass);
         //设置Jedis
         Jedis jedis = jedisPool.getResource();
         return jedis;

     }



     /* 根据表插入数据
     * @return
     */
    @Override
    public void insertToRedis(BasePo po) {

        Jedis jedis = getJedisConnect();


    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setRequirepass(String requirepass) {
        this.requirepass = requirepass;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
