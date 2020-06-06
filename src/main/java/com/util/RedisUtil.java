package com.util;


import com.alibaba.fastjson.JSONObject;
import com.entity.WxMaUserInfoExtends;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {

    private static Logger logger =  Logger.getLogger(RedisUtil.class);
    private static JedisPool jedisPool = null;

    public static JedisPool getPool() {
        if (jedisPool == null) {
            // jedispool为null则初始化，
            JedisPoolConfig config = new JedisPoolConfig();
            // 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；

            // 如果赋值为-1，则表示不限制；如果pool已经分配了maxTotal个jedis实例，则此时pool的状态为exhausted(耗尽）.
            config.setMaxTotal(500);

            // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例
            config.setMaxIdle(5);

            // 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWaitMillis(1000 * 10);

            // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);

            jedisPool = new JedisPool(config, "120.26.91.215", 6379, 10000, "123");
            //10000是protocol.timeout 默认值2000
        }

        return jedisPool;

    }


    public static WxMaUserInfoExtends get(String key) {
        String value = null;
        JedisPool pool = null;
        Jedis jedis = null;
        WxMaUserInfoExtends res = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.get(key);
             res = JSONObject.parseObject(value,WxMaUserInfoExtends.class);

        } catch (Exception e) {
            // TODO: handle exception
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            logger.error("jedis error is" + "e.printStackTrace()");
            logger.error("fail to get data from jedis ", e);
        } finally {
            // 返还到连接池
            pool.returnResource(jedis);
        }
        return res;

    }

    /**
     * 给key赋值，并生命周期设置为seconds
     *
     * @param key
     * @param seconds
     *            生命周期 秒为单位
     * @param wxMaUserInfoExtends
     */
    public static void setx(String key, int seconds, WxMaUserInfoExtends wxMaUserInfoExtends) {
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            String value = JSONObject.toJSONString(wxMaUserInfoExtends);
            pool = getPool();
            jedis = pool.getResource();
            jedis.setex(key, seconds, value);
        } catch (Exception e) {
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            logger.error("fail to set key and seconds", e);
        } finally {
            // 返还到连接池
            pool.returnResource(jedis);
        }
    }
    /**
     * 根据key值来删除已经存在的key-value;
     *
     * @param key
     * @return
     */

    public static int removex(String key) {
        int temp = 0;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            temp = jedis.del(key).intValue();
        } catch (Exception e) {
            // TODO: handle exception
            pool.returnBrokenResource(jedis);
            logger.error("fail to delete the key-value according to the key", e);
        } finally {
            //返回redis实例
            pool.returnResource(jedis);
        }
        return temp;
    }
    @Test
    public void test(){
        RedisUtil redisUtil = new RedisUtil();
        RedisUtil.setx("1",3600,new WxMaUserInfoExtends());
        System.out.println(RedisUtil.get("1"));
    }

}
