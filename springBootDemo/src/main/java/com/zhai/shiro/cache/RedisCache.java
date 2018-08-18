package com.zhai.shiro.cache;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import com.zhai.common.utils.RedisUtil;

/**
 * 
 * @author ZHAIMINGMING
 *
 * @param <K>
 * @param <V>
 */
@Component
public class RedisCache<K, V> implements Cache<K, V>{
	
	private final String CACHE_PREFIX="demo-cache";
	
	@Autowired
	private RedisUtil redisUtil;
	
	private byte[] getKey(K k){
		if(k instanceof String){
			return (CACHE_PREFIX+k).getBytes();
		}
		
		return SerializationUtils.serialize(k);
	}

	@Override
	public void clear() throws CacheException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public V get(K k) throws CacheException {
		System.out.println("from redis get DATA");
		
		
		byte[] value = (byte[]) redisUtil.getValueByKey(getKey(k));
		if(value!=null){
			return (V) SerializationUtils.deserialize(value);
		}
		
		return null;
	}

	@Override
	public Set<K> keys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V put(K k, V v) throws CacheException {
		byte[] key = getKey(k);
		byte[] value = SerializationUtils.serialize(v);
		
		redisUtil.set(key, value);
		redisUtil.expire(key, 600);
		
		return v;
	}

	@Override
	public V remove(K k) throws CacheException {
		
		byte[] key = getKey(k);
		byte[] value = (byte[]) redisUtil.getValueByKey(k);
		redisUtil.deleteKey(key);
		if(value!=null){
			return (V) SerializationUtils.serialize(value);
		}
		
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

}
