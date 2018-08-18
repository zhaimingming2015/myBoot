package com.zhai.shiro.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SerializationUtils;

import com.zhai.common.utils.RedisOperator;
import com.zhai.common.utils.RedisUtil;

public class RedisSessionDao extends AbstractSessionDAO{
	
	@Autowired
	private RedisUtil redisOperator;
	
	private final String SHIRO_SESSION_PREFIX="demo-session";
	
	private byte[] getKey(String key){
		return (SHIRO_SESSION_PREFIX+key).getBytes();
	}

	private void saveSession(Session session){
		System.out.println("---------saveSession");
		if(session!=null && session.getId()!=null){
			byte[] key = getKey(session.getId().toString());
			byte[] value = SerializationUtils.serialize(session);
			
			redisOperator.set(key, value);
			redisOperator.expire(key, 600);
		}
		

	}
	@Override
	public void update(Session session) throws UnknownSessionException {
		System.out.println("update-----Sessions"+System.currentTimeMillis());
		//saveSession(session);
		
		if(session!=null && session.getId()!=null){
			byte[] key = getKey(session.getId().toString());
			byte[] value = SerializationUtils.serialize(session);
			
			redisOperator.set(key, value);
			redisOperator.expire(key, 600);
		}
		
	}

	@Override
	public void delete(Session session) {
		System.out.println("update-----Sessions");
		if(session==null ||session.getId()==null){
			return;
		}
		byte[] key = getKey(session.getId().toString());
		redisOperator.deleteKey(key);
	}

	/**
	 * 获得指定的存活的session
	 */
	@Override
	public Collection<Session> getActiveSessions() {
		System.out.println("getActiveSessions------"+System.currentTimeMillis());
		 Set<byte[]> keys = redisOperator.getKeys(SHIRO_SESSION_PREFIX+"*");
		 Set<Session> sessions=new HashSet<Session>();
		 if(CollectionUtils.isEmpty(keys)){
			 return sessions;
		 }
		 
		 for(byte[] key:keys){
			 Session session =(Session) SerializationUtils.deserialize((byte[])redisOperator.getValueByKey(key));
			 sessions.add(session);
		 }
		 
		return sessions;
	}

	@Override
	protected Serializable doCreate(Session session) {
		System.out.println("doCreateSessions"+System.currentTimeMillis());
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		System.out.println(sessionId);
		System.out.println(session.getId().toString());
		byte[] key = getKey(session.getId().toString());
		byte[] value = SerializationUtils.serialize(session);
		
		redisOperator.set(key, value);
		redisOperator.expire(key, 600);
		//saveSession(session);
		
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		
		System.out.println("doReadSession"+System.currentTimeMillis());
		if(sessionId==null){
			System.out.println("read session:"+sessionId+":"+System.currentTimeMillis());
			return null;
		}
		
		byte[] key = getKey(sessionId.toString());
		//System.out.println("doReadSession:"+key.toString());
		byte[] value = (byte[]) redisOperator.getValueByKey(key);
		//System.out.println("doReadSession getValue"+value);
		return (Session) SerializationUtils.deserialize(value);
	}

}
