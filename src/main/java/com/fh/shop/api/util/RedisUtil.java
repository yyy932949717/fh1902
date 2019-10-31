package com.fh.shop.api.util;

import redis.clients.jedis.Jedis;

public class RedisUtil {

    public static void set(String key,String value){
        Jedis redis =null;
        try {
             redis = RedisPool.getRedis();
            redis.set(key,value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            if(redis!=null){
                redis.close();
            }
        }
    }

    public static String get(String key){
        Jedis redis =null;
        String value =null;
        try {
             redis = RedisPool.getRedis();
             value = redis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException(e.getMessage());
        } finally {
            if (redis!=null){
                redis.close();
            }
        }
        return value;
    }
    
    public static Long del(String key){
        Jedis redis =null;
        Long del =0L;
        try {
            redis = RedisPool.getRedis();
             del = redis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException(e.getMessage());
        } finally {
            if(redis!=null){
                redis.close();
            }
        }
        return  del;
    }

    public static void expire(String key,int second){
        Jedis redis =null;
        try {
             redis = RedisPool.getRedis();
            redis.expire(key,second);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException(e.getMessage());
        } finally {
            if(redis!=null){
                redis.close();
            }
        }
    };

    public static void  setEx(String key,String value,int second){
        Jedis redis =null;
        try {
            redis = RedisPool.getRedis();
            redis.setex(key,second,value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            if(redis!=null){
                redis.close();
            }
        }

    }

    public static boolean exists(String key){
        boolean exists= false;
        Jedis redis =null;
        try {
            exists = false;
             redis = RedisPool.getRedis();
            exists = redis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if(null!=redis){
            redis.close();
            }
        }
        return exists;
    }

    public static void hset(String key,String field,String value){
        Jedis redis=null;
        try {
            redis = RedisPool.getRedis();
            redis.hset(key,field,value);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        } finally {
            if (redis!=null){
                redis.close();
            }
        }
    }

    public static String hget(String key,String field){
        Jedis redis =null;
        String value =null;
        try {
             redis = RedisPool.getRedis();
             value = redis.hget(key, field);
        } catch (Exception e) {
            e.printStackTrace();
            throw new  RuntimeException(e);
        } finally {
            if (redis!=null){
                redis.close();
            }
        }
        return value;
    }

    public static void hdel(String key,String field){
        Jedis redis=null;
        try {
            redis = RedisPool.getRedis();
            redis.hdel(key,field);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (redis!=null){
                redis.close();
            }
        }
    }

}
