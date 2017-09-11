package com.galaxy.im.common.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.galaxy.im.common.exception.SerializerException;

public class RedisObjectSerializer implements RedisSerializer<Object>{
	private Logger log = LoggerFactory.getLogger(RedisObjectSerializer.class);
	private final byte[] EMPATH_ARRAY = new byte[0];
	private Converter<Object,byte[]> serializer = new SerializingConverter();
	private Converter<byte[], Object> deserializer = new DeserializingConverter();
	
	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		if(isEmpty(bytes)){
			return null;
		}
		try{
			return deserializer.convert(bytes);
		}catch(Exception e){
			log.error("对象反向解析失败",e);
			throw new SerializerException(e);
		}
	}

	@Override
	public byte[] serialize(Object object) throws SerializationException {
		if(object==null){
			return EMPATH_ARRAY;
		}
		try{
			return serializer.convert(object);
		}catch(Exception e){
			log.error("对象解析失败",e);
			return EMPATH_ARRAY;
		}
	}
	
	private boolean isEmpty(byte[] data){
		return (data==null || data.length==0);
	}

	
}
