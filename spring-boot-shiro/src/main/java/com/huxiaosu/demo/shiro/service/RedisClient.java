/**
 * Project Name demo
 * File Name RedisClient
 * Package Name com.huxiaosu.demo.shiro.service
 * Create Time 2019/3/27
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.shiro.service;

import com.huxiaosu.demo.shiro.config.RedisConfig;
import com.huxiaosu.demo.shiro.exception.RedisException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.io.Serializable;
import java.util.*;


/**
 * Description
 *
 * @ClassName: RedisClient
 * @author: liujie
 * @date: 2019/3/27 14:15
 */
@Slf4j
@Component
public class RedisClient {
    private static final String POOL_MAX_WAIT_MILLS = "poolMaxWaitMills";
    private static final String POOL_IDLE_SIZE = "poolIdleSize";
    private static final String POOL_MAX_SIZE = "poolMaxSize";
    private static final int MAX_TOTAL = 100;
    private static final long MAX_WAIT_MILLIS = 1000L * 10;
    private static final int MAX_IDLE = 1000 * 60;
    private static final int VAL_INT_ZERO = 0;
    protected Map<String, String> config;
    protected String[] servers;

    private ShardedJedisPool pool;

    private RedisConfig redisConfig;

    @Autowired
    public RedisClient(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;

        String server = redisConfig.getIp() + ":" + redisConfig.getPort();
        this.servers = new String[1];
        this.servers[0] = server;
        initPool();
    }


    public String[] getServer() {
        return this.servers;
    }

    private void initPool() {
        List<JedisShardInfo> jdsInfoList = new ArrayList<JedisShardInfo>(getServer().length);

        for (String server : getServer()) {
            //
            String[] serverInfo = buildConfig(server);

            JedisShardInfo infoA = new JedisShardInfo(serverInfo[0], Integer.parseInt(serverInfo[1]), server);
            jdsInfoList.add(infoA);
        }

        pool = new ShardedJedisPool(configRedisPool(), jdsInfoList, Hashing.MURMUR_HASH,
                Sharded.DEFAULT_KEY_TAG_PATTERN);
    }

    protected String[] buildConfig(String server) {
        isNotBlank("init error, cache server config error.", server);
        String[] serverInfo = server.split(":");
        isNotNull("init error, cache server config error.", serverInfo);
        return serverInfo;
    }
    protected static <T> void isNotEqual(String message, Object val, Object expectV) {
        if (val.equals(expectV)) {
            throw new RedisException(message);
        }
    }
    protected static void isNotBlank(String message, String vals) {
        if (vals == null) {
            throw new RedisException(message);
        }
    }

    protected static void isNotNull(String message, Object val) {
        if (val == null) {
            throw new RedisException(message);
        }
    }

    @SuppressWarnings("unchecked")
    public <C> C getConfigVal(String key, C defValue) {
        String ret = this.getStrConfigVal(key, null);
        if (ret == null) {
            return defValue;
        }
        if (defValue.getClass().isAssignableFrom(Integer.class)) {
            try {
                return (C) Integer.valueOf(ret);
            } catch (NumberFormatException e) {
                log.error("Format config exception.", e);
                return defValue;
            }
        } else if (defValue.getClass().isAssignableFrom(Long.class)) {
            try {
                return (C) Long.valueOf(ret);
            } catch (NumberFormatException e) {
                log.error("Format config exception.", e);
                return defValue;
            }
        }
        return defValue;
    }

    public boolean existsConfig(String key) {
        return config != null && config.containsKey(key);
    }

    public String getStrConfigVal(String key, String defValue) {
        String v = existsConfig(key) ? config.get(key) : null;
        return StringUtils.isNotBlank(v) ? v.trim() : defValue;
    }

    protected JedisPoolConfig configRedisPool() {
        JedisPoolConfig redisConfig = new JedisPoolConfig();
        // Jedis池配置
        redisConfig.setMaxTotal(this.<Integer>getConfigVal(POOL_MAX_SIZE, MAX_TOTAL));
        redisConfig.setMaxIdle(this.<Integer>getConfigVal(POOL_IDLE_SIZE, MAX_IDLE));
        redisConfig.setMaxWaitMillis(this.<Long>getConfigVal(POOL_MAX_WAIT_MILLS, MAX_WAIT_MILLIS));

        // 可选配置
        String minIdle = this.getStrConfigVal("poolMinIdle", null);
        if (minIdle != null) {
            redisConfig.setMinIdle(Integer.parseInt(minIdle));
        }
        String testWhileIdle = this.getStrConfigVal("poolTestWhileIdle", null);
        if (testWhileIdle != null) {
            redisConfig.setTestWhileIdle(Boolean.valueOf(testWhileIdle));
        }
        String minEvictableIdleTimeMillis = this.getStrConfigVal("poolMinEvictableIdleTimeMillis", null);
        if (minEvictableIdleTimeMillis != null) {
            redisConfig.setMinEvictableIdleTimeMillis(Integer.parseInt(minEvictableIdleTimeMillis));
        }
        String timeBetweenEvictionRunsMillis = this.getStrConfigVal("poolTimeBetweenEvictionRunsMillis", null);
        if (timeBetweenEvictionRunsMillis != null) {
            redisConfig.setTimeBetweenEvictionRunsMillis(Integer.parseInt(timeBetweenEvictionRunsMillis));
        }
        String numTestsPerEvictionRun = this.getStrConfigVal("poolNumTestsPerEvictionRun", null);
        if (numTestsPerEvictionRun != null) {
            redisConfig.setNumTestsPerEvictionRun(Integer.parseInt(numTestsPerEvictionRun));
        }
        return redisConfig;
    }

    /**
     * Description:
     * 添加缓存数据
     *
     * @param key
     * @param val
     * @return: void
     * @author: liujie
     * @date: 2019/3/5 18:00
     */
    public void put(String key, Object val) throws RedisException {
        // 1. 检查参数
        isNotBlank("key is blank.", key);
        isNotNull("value is null", val);

        ShardedJedis jds = pool.getResource();
        try {
            jds.getShard(key).set(key.getBytes(), SerializationUtils.serialize((Serializable) val));
        } catch (Throwable e) {
            throw new RedisException("Put cache exception.", e);
        } finally {
            if (jds != null) {
                jds.close();
            }
        }
    }

    /**
     * Description:
     * 向左追加
     *
     * @param key
     * @param val
     * @return: void
     * @author: liujie
     * @date: 2019/3/5 18:00
     */
    public void lpush(String key, Object val) throws RedisException {

        // 1. 检查参数
        isNotBlank("key is blank.", key);
        isNotNull("value is null", val);
        ShardedJedis jds = pool.getResource();
        try {
            jds.getShard(key).lpush(key.getBytes(), SerializationUtils.serialize((Serializable) val));

        } catch (Throwable e) {
            throw new RedisException("lpush cache exception.", e);
        } finally {
            if (jds != null) {
                jds.close();
            }
        }
    }

    /**
     * Description:
     * 向右追加
     *
     * @param key
     * @param val
     * @return: void
     * @author: liujie
     * @date: 2019/3/5 18:00
     */
    public void rpush(String key, Object val) throws RedisException {

        // 1. 检查参数
        isNotBlank("key is blank.", key);
        isNotNull("value is null", val);
        ShardedJedis jds = pool.getResource();
        try {
            jds.getShard(key).rpush(key.getBytes(), SerializationUtils.serialize((Serializable) val));

        } catch (Throwable e) {
            throw new RedisException("rpush cache exception.", e);
        } finally {
            if (jds != null) {
                jds.close();
            }
        }
    }

    /**
     * Description:
     * 从左边取
     *
     * @param key
     * @return: Object
     * @author: liujie
     * @date: 2019/3/5 18:00
     */
    public <T> T lpop(String key) throws RedisException {
        // 1. 检查参数
        isNotBlank("key is blank.", key);

        ShardedJedis jds = pool.getResource();
        try {
            byte[] ret = jds.getShard(key).lpop(key.getBytes());

            if (ret == null) {
                return null;
            }

            return SerializationUtils.<T>deserialize(ret);

        } catch (Throwable e) {
            throw new RedisException("lpop cache exception.", e);
        } finally {
            if (jds != null) {
                jds.close();
            }
        }
    }

    /**
     * Description:
     * 从右边取
     *
     * @param key
     * @return: Object
     * @author: liujie
     * @date: 2019/3/5 18:00
     */
    public <T> T rpop(String key) throws RedisException {
        // 1. 检查参数
        isNotBlank("key is blank.", key);

        ShardedJedis jds = pool.getResource();
        try {
            byte[] ret = jds.getShard(key).rpop(key.getBytes());

            if (ret == null) {
                return null;
            }

            return SerializationUtils.<T>deserialize(ret);

        } catch (Throwable e) {
            throw new RedisException("rpop cache exception.", e);
        } finally {
            if (jds != null) {
                jds.close();
            }
        }
    }

    /**
     * Description:
     * 获取数据
     *
     * @param key
     * @return: Object
     * @author: liujie
     * @date: 2019/3/5 18:00
     */
    public <T> T get(String key) throws RedisException {
        // 1. 检查参数
        isNotBlank("key is blank.", key);

        ShardedJedis jds = pool.getResource();
        try {
            byte[] ret = jds.getShard(key).get(key.getBytes());
            if (ret == null) {
                return null;
            }

            return SerializationUtils.<T>deserialize(ret);

        } catch (Throwable e) {
            throw new RedisException("get cache exception.", e);
        } finally {
            if (jds != null) {
                jds.close();
            }
        }
    }

    /**
     * Description:
     * 获取所有key
     *
     * @param key
     * @return: Object
     * @author: liujie
     * @date: 2019/3/5 18:00
     */
    public Set<String> getKeys(String key) throws RedisException {
        // 1. 检查参数
        isNotBlank("key is blank.", key);

        ShardedJedis jds = pool.getResource();
        try {
            Set<byte[]> keys = jds.getShard(key).keys(key.getBytes());
            Set<String> results = new HashSet<String>();
            if (keys != null) {
                for (byte[] k : keys) {
                    results.add(new String(k));
                }
            }
            return results;
        } catch (Throwable e) {
            throw new RedisException("hget cache exception.", e);
        } finally {
            if (jds != null) {
                jds.close();
            }
        }
    }

    /**
     * Description:
     * 删除数据
     *
     * @param key
     * @return: Object
     * @author: liujie
     * @date: 2019/3/5 18:00
     */
    public void del(String key) throws RedisException {
        // 1. 检查参数
        isNotBlank("key is blank.", key);

        ShardedJedis jds = pool.getResource();
        try {
            jds.getShard(key).del(key);

        } catch (Throwable e) {
            throw new RedisException("delete cache exception.", e);
        } finally {
            if (jds != null) {
                jds.close();
            }
        }
    }


    public List<String> getLrange(String key, Integer start, Integer end) throws RedisException {
        if (start < 0) {
            start = 0;
        }
        if (end == 0) {
            start = -1;
        }
        List<String> results = new ArrayList<String>();
        ShardedJedis jds = pool.getResource();
        try {
            List<byte[]> list = jds.getShard(key).lrange(key.getBytes(), start, end);

            if (list != null) {
                for (byte[] k : list) {
                    results.add((String) SerializationUtils.<String>deserialize(k));
                }
            }
        } catch (Throwable e) {
            throw new RedisException("getLrange cache exception.", e);
        } finally {
            if (jds != null) {
                jds.close();
            }
        }
        return results;
    }

    public void lremValue(String key, String value) throws RedisException {
        ShardedJedis jds = pool.getResource();
        try {
            jds.getShard(key).lrem(key.getBytes(), 0, SerializationUtils.serialize((Serializable) value));
        } catch (Throwable e) {
            throw new RedisException("getLrange cache exception.", e);
        } finally {
            if (jds != null) {
                jds.close();
            }
        }
    }

    public void incr(String key, int by, int expiredTime)
            throws RedisException {

        // 1. 检查参数
        isNotBlank("key is blank.", key);
        isNotEqual("incr value is zero.", by, VAL_INT_ZERO);
        ShardedJedis jds = pool.getResource();
        try {
            jds.incrBy(key, by);
        } catch (Throwable e) {
            throw new RedisException("incr cache exception.", e);
        } finally {
            if (jds != null) {
                jds.close();
            }
        }

    }

    public void incr(String key, int expiredTime) throws RedisException {
        // 1. 检查参数
        isNotBlank("key is blank.", key);

        ShardedJedis jds = pool.getResource();
        try {
            jds.incr(key);
            jds.expire(key, expiredTime);
        } catch (Throwable e) {
            throw new RedisException("incr cache exception.", e);
        } finally {
            if (jds != null) {
                jds.close();
            }
        }

    }
}
