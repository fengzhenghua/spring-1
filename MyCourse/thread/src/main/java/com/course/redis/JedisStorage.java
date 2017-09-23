package com.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fzh on 2017/8/1.
 */
public class JedisStorage {

    public static void main(String[] args) {

        //Jedis连接池设置
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(10);
        poolConfig.setMaxTotal(30);
        //Jedis连接池
        JedisPool jedisPool = new JedisPool(poolConfig,"47.94.94.39",6379);

        Jedis jedis = null;

        try{
            Map<String,Double> map = new HashMap<String,Double>();
            map.put("zhangsan",78.0);
            map.put("lisi",98.0);
            jedis = jedisPool.getResource();
            jedis.set("xiaoming","22");
            jedis.hset("myhash","魏国","曹操");
            jedis.hset("myhash","蜀国","刘备");
            jedis.lpush("mylist","张飞","关羽","赵云","张飞","许褚","吕布");
            jedis.lpushx("mylist","孙权","周瑜","陆迅");
            jedis.sadd("myset","red","green","yellow");
            jedis.zadd("mysortset",88,"xiaohong");
            jedis.zadd("mysortset",map);

            //获取数据
            System.out.println(jedis.get("xiaoming"));
            System.out.println(jedis.hget("myhash","魏国"));
            System.out.println(jedis.lrange("mylist",0,-1));
            System.out.println(jedis.smembers("myset"));
            System.out.println(jedis.srandmember("myset",2));
            System.out.println(jedis.zrange("mysortset",0,-1));

            //删除数据
            jedis.lrem("mylist",1,"张飞");
            jedis.hdel("myhash","魏国");
            jedis.srem("myset","red");
            jedis.zrem("mysortset","zhangsan");

            System.out.println(jedis.hget("myhash","魏国"));
            System.out.println(jedis.lrange("mylist",0,-1));
            System.out.println(jedis.smembers("myset"));
            System.out.println(jedis.srandmember("myset",2));
            System.out.println(jedis.zrange("mysortset",0,-1));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("添加异常");
        }finally {
            if(jedis!=null){
                jedis.close();
            }
            if(jedisPool!=null){
                jedisPool.close();
            }
        }
    }
}
