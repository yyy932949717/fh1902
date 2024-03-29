package com.fh.shop.api.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
    private static JedisPool jedisPool;
    private RedisPool (){};

    private static void  initPool(){
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1000);
        jedisPoolConfig.setMinIdle(100);
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPool =new JedisPool(jedisPoolConfig,"192.168.131.133",9999);
    }

    static {
        initPool();
    }

    public static Jedis getRedis(){

        return jedisPool.getResource();
    }
}
