package com.sky.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * spring-data-redis操作工具类
 * 
 * @作者 乐此不彼
 * @时间 2017年8月24日
 * @公司 sky工作室
 */
@Component
@Slf4j
public class RedisUtil {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private ValueOperations<String, Object> valueOperations;

	@Autowired
	private HashOperations<String, String, Object> hashOperations;

	@Autowired
	private ListOperations<String, Object> listOperations;

	@Autowired
	private SetOperations<String, Object> setOperations;

	@Autowired
	private ZSetOperations<String, Object> zsetOperations;

	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value) {
		boolean result = false;
		try {
			valueOperations.set(key, value);
			result = true;
		} catch (Exception e) {
			log.error("写入redis异常,key={},value={}", key, value);
		}
		return result;
	}

	/**
	 * 写入缓存设置时效时间
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value, Long expireTime) {
		boolean result = false;
		try {
			valueOperations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			log.error("写入redis异常,key={},value={},expireTime={}", key, value, expireTime);
		}
		return result;
	}

	/**
	 * 重设key的过期时间,单位:秒
	 * 
	 * @param key
	 * @param expireTime
	 * @return
	 */
	public boolean resetExpire(final String key, Long expireTime) {
		return redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
	}

	/**
	 * 批量删除对应的value
	 * 
	 * @param keys
	 */
	public void remove(final String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	/**
	 * 批量删除key
	 * 
	 * @param pattern
	 */
	public void removePattern(final String pattern) {
		Set<String> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0)
			redisTemplate.delete(keys);
	}

	/**
	 * 删除对应的value
	 * 
	 * @param key
	 */
	public void remove(final String key) {
		if (exists(key)) {
			redisTemplate.delete(key);
		}
	}

	/**
	 * 判断缓存中是否有对应的value
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 读取缓存
	 * 
	 * @param key
	 * @return
	 */
	public Object get(final String key) {
		return valueOperations.get(key);
	}

	/**
	 * 哈希 添加
	 * 
	 * @param key
	 * @param hashKey
	 * @param value
	 */
	public void hmSet(String key, String hashKey, Object value) {
		hashOperations.put(key, hashKey, value);
	}

	/**
	 * 哈希获取数据
	 * 
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public Object hmGet(String key, Object hashKey) {
		return hashOperations.get(key, hashKey);
	}

	/**
	 * 列表添加
	 * 
	 * @param k
	 * @param v
	 */
	public void lPush(String k, Object v) {
		listOperations.rightPush(k, v);
	}

	/**
	 * 列表获取
	 * 
	 * @param k
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Object> lRange(String k, long start, long end) {
		return listOperations.range(k, start, end);
	}

	/**
	 * 集合添加
	 * 
	 * @param key
	 * @param value
	 */
	public void add(String key, Object value) {
		setOperations.add(key, value);
	}

	/**
	 * 集合获取
	 * 
	 * @param key
	 * @return
	 */
	public Set<Object> members(String key) {
		return setOperations.members(key);
	}

	/**
	 * 有序集合添加
	 * 
	 * @param key
	 * @param value
	 * @param score
	 */
	public void zAdd(String key, Object value, double score) {
		zsetOperations.add(key, value, score);
	}

	/**
	 * 有序集合获取
	 * 
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Set<Object> rangeByScore(String key, double min, double max) {
		return zsetOperations.rangeByScore(key, min, max);
	}
}