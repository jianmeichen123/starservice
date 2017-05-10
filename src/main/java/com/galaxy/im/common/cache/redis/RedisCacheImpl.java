package com.galaxy.im.common.cache.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository("cache")
public class RedisCacheImpl<K,V> implements IRedisCache<K,V>{
	//private Logger log = LoggerFactory.getLogger(RedisCacheImpl.class);
	
	@Autowired
	private RedisTemplate<K,V> redisTemplate;

	public boolean hasKey(K key) {
		return redisTemplate.hasKey(key);
	}
	
	public void put(K key, V obj) {
		redisTemplate.opsForValue().set(key, obj);
	}
	
	@Override
	public void put(K key, V obj, long timeout, TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(key, obj, timeout, timeUnit);
	}

	public V get(K key) {
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public void remove(K key) {
		redisTemplate.delete(key);
	}
}
