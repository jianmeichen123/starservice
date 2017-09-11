package com.galaxy.im.common.cache.redis;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.galaxy.im.common.exception.RedisException;

@Repository("cache")
public class RedisCacheImpl<K,V> implements IRedisCache<K,V>{
	private Logger log = LoggerFactory.getLogger(RedisCacheImpl.class);
	
	@Autowired
	private RedisTemplate<K,V> redisTemplate;

	public boolean hasKey(K key) {
		try{
			return redisTemplate.hasKey(key);
		}catch(Exception e){
			log.error(RedisCacheImpl.class.getName() + "_hasKey",e);
			throw new RedisException(e);
		}
	}
	
	public void put(K key, V obj) {
		try{
			redisTemplate.opsForValue().set(key, obj);
		}catch(Exception e){
			log.error(RedisCacheImpl.class.getName() + "_put",e);
			throw new RedisException(e);
		}
	}
	
	@Override
	public void put(K key, V obj, long timeout, TimeUnit timeUnit) {
		try{
			redisTemplate.opsForValue().set(key, obj, timeout, timeUnit);
		}catch(Exception e){
			log.error(RedisCacheImpl.class.getName() + "_put",e);
			throw new RedisException(e);
		}
	}

	public V get(K key) {
		try{
			return redisTemplate.opsForValue().get(key);
		}catch(Exception e){
			log.error(RedisCacheImpl.class.getName() + "_get",e);
			throw new RedisException(e);
		}
	}

	@Override
	public void remove(K key) {
		try{
			redisTemplate.delete(key);
		}catch(Exception e){
			log.error(RedisCacheImpl.class.getName() + "_remove",e);
			throw new RedisException(e);
		}
	}
}
