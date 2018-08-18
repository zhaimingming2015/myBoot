package com.zhai.common.utils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {
	
	@Autowired
	private RedisTemplate<Object, Object> template;
	
	public void set(Object key,Object value){
		template.opsForValue().set(key, value);
	}
	
	public void expire(Object key, long timeout) {
		template.expire(key, timeout, TimeUnit.SECONDS);
	}
	
	public Object getValueByKey(Object key) {
		return  template.opsForValue().get(key);
		//return (String)redisTemplate.opsForValue().get(key);
	}
	
	public void deleteKey(Object key) {
		template.delete(key);
	}

	public Set getKeys(String key) {
		return template.keys(key);
	}

}
