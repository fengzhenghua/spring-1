package com.course.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

/**
 * Created by fzh on 2017/7/29.
 */
public class JedisDemo1 {

    public static void main(String[] args) {
        //设置连接
        Jedis jedis = new Jedis("47.94.94.39",6379);
        //保存数据
        jedis.set("name","小明");
        //获取数据
        String name = jedis.get("name");
        System.out.println(name);
        //释放资源
        jedis.close();
    }

    /**
     * 连接池获取redis连接
     * Jedisconfig-->Jedispool-->jedis
     */
    @Test
    public void getRedisPooll(){
        //redis连接池配置
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //设置最大连接数
        poolConfig.setMaxTotal(30);
        //设置最大连接空闲数
        poolConfig.setMaxIdle(10);

        //获得连接池
        JedisPool jedisPool = new JedisPool(poolConfig,"47.94.94.39",6379);

        Jedis jedis = null;

        try{
            jedis = jedisPool.getResource();
            //jedis.set("hair","black");
            jedis.hset("sanguo","weiguo","caochao");
            jedis.hset("sanguo","shuguo","liubei");
            String value = jedis.get("hair");
            Map<String,String> map = jedis.hgetAll("sanguo");
            System.out.println(map.toString());
            System.out.println(map.get("weiguo")+" "+map.get("shuguo"));
            System.out.println(value);
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            if(jedisPool!=null){
                jedisPool.close();
            }
            if(jedis!=null){
                jedis.close();
            }
        }
    }
}
